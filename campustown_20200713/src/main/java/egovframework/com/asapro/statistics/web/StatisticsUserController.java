/**
 * 
 */
package egovframework.com.asapro.statistics.web;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.WebServiceClient;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.model.CountryResponse;
import com.maxmind.geoip2.record.Country;

import egovframework.com.asapro.menu.service.Menu;
import egovframework.com.asapro.site.service.Site;
import egovframework.com.asapro.statistics.service.StatisticsService;
import egovframework.com.asapro.statistics.service.StatisticsUserTemp;
import egovframework.com.asapro.support.Constant;
import egovframework.com.asapro.support.annotation.CurrentSite;
import egovframework.com.asapro.support.util.AsaproUtils;
import egovframework.com.asapro.support.util.EtcUtil;
import eu.bitwalker.useragentutils.UserAgent;

/**
 * 통계 사용자 컨트롤러
 * @author yckim
 * @since 2019. 6. 5.
 *
 */
@Controller
public class StatisticsUserController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(StatisticsUserController.class);
	
	@Autowired
	private StatisticsService statisticsService;
	
	/**
	 * 통계 데이터를 비동기 방식으로 기록한다.
	 * @param request
	 * @param response
	 * @param currentSite
	 * @param statMenu
	 */
	@RequestMapping(value=Constant.APP_PATH + Constant.SITE_ID_PATH + "/statistics" + Constant.API_PATH + "/insert", method=RequestMethod.POST)
	public void statUpdatePost(HttpServletRequest request, HttpServletResponse response, @CurrentSite Site currentSite, @ModelAttribute Menu statMenu){
		
		// ========== 시작 : 통계 ===============================
		// 구글 애널리틱스의 기준을 참조하여 개발
		// 사용자 수 통계는 쿠키(2년)을 기준으로 차후 개발예정
		// 방문자 수는 세션 수를 기준으로 함  = > 방문자 수 == 세션 수 
		// 한번 방문한 사람은 세션에 체크해 놓는 방식.
		// 00시가 되면 활성중인 모든 세션의 체크값을 모두 삭제하여 일 카운트를 새로 한다. 
		// 세션 기준이므로 브라우저 닫고 새로 들어오면(새 세션이 생성되면) 방문자수 1 증가한다.
		// 일 배치를 통해 일, os, 국가, 브라우저 별 통계 데이터를 생성한다.
		// 사이트 별로 통계데이터를 등록한다.
		// 메뉴별 접속통계는 세션값과 상관없이 모든페이지에 접속시마다 데이터가 누적된다.
		
		//세션의 체크값 확인 - 사이트별로 체크하여 사이트별로 통계데이터 누적
		String siteVisit = (String) request.getSession().getAttribute("siteVisit_" + currentSite.getSiteId());

		Calendar cal = Calendar.getInstance();

		StatisticsUserTemp StatisticsUserTemp = new StatisticsUserTemp();
		StatisticsUserTemp.setStatisYear(cal.get(Calendar.YEAR));
		StatisticsUserTemp.setStatisMonth(cal.get(Calendar.MONTH) + 1);
		StatisticsUserTemp.setStatisDay(cal.get(Calendar.DAY_OF_MONTH));
		StatisticsUserTemp.setStatisHour(cal.get(Calendar.HOUR_OF_DAY));
		StatisticsUserTemp.setSitePrefix(currentSite.getSitePrefix());
		StatisticsUserTemp.setStatisIp(AsaproUtils.getRempoteIp(request));
		
		if( StringUtils.isBlank(siteVisit) ){
			
			LOGGER.info("[ASAPRO] StatisticsUserController insert statistics base data");
			request.getSession().setAttribute("siteVisit_" + currentSite.getSiteId(), System.currentTimeMillis() + "");
			
			
			UserAgent userAgent = (UserAgent) request.getAttribute("userAgent");
			if( userAgent == null ){
				LOGGER.info("[ASAPRO] StatisticsUserController userAgent newly parsed!!! ");
				userAgent = UserAgent.parseUserAgentString(request.getHeader("User-Agent"));
			}
			
			StatisticsUserTemp.setStatisOs(userAgent.getOperatingSystem().getName());
			StatisticsUserTemp.setStatisBrowser(userAgent.getBrowser().getName());
			
			//IP로 접속국가명, ISO 코드 추출
			// A File object pointing to your GeoIP2 or GeoLite2 database
			File database = new File(request.getSession().getServletContext().getRealPath("/") + "assets/geoip2/GeoLite2-Country.mmdb");

			// This creates the DatabaseReader object. To improve performance, reuse
			// the object across lookups. The object is thread-safe.
			DatabaseReader reader = null;
			try {
				reader = new DatabaseReader.Builder(database).build();
			} catch (IOException e) {
				LOGGER.info("[ASAPRO] StatisticsUserController IOException : {}", e.getMessage());
			}

		    InetAddress ipAddress = null;
			try {
				ipAddress = InetAddress.getByName(AsaproUtils.getRempoteIp(request));
			} catch (UnknownHostException e) {
				LOGGER.info("[ASAPRO] StatisticsUserController UnknownHostException : {}", e.getMessage());
			}

		    // Do the lookup
		    CountryResponse countryResponse = null;
			try {
				countryResponse = reader.country(ipAddress);
			} catch (IOException e) {
				LOGGER.info("[ASAPRO] StatisticsUserController IOException : {}", e.getMessage());
			} catch (GeoIp2Exception e) {
				LOGGER.info("[ASAPRO] StatisticsUserController GeoIp2Exception : {}", e.getMessage());
			}

			//LOGGER.info("[ASAPRO] StatisticsUserController GeoIp2Exception : {}", e.getMessage());
		    Country country = countryResponse.getCountry();
		    
		    if(country != null){
		    	StatisticsUserTemp.setStatisCountry(country.getName());
		    	StatisticsUserTemp.setStatisIsoCode(country.getIsoCode());
		    	//System.out.println(country.getIsoCode());            // 'KR'
		    	//System.out.println(country.getName()); 
		    }
			
			// 방문자 데이터
			statisticsService.insertStatisUserTemp(StatisticsUserTemp);

			
			
		} else {
			LOGGER.info("[ASAPRO] StatisticsUserController siteVisit exists. : {}", siteVisit);
		}

		// 메뉴통계 기초데이터를 입력한다. (현제메뉴가 있는것만 )
		if (statMenu != null && StringUtils.isNotBlank(statMenu.getMenuId())) {
			
			LOGGER.info("[ASAPRO] StatisticsUserController uri : {}, INSERTING STAT MENU...{}", AsaproUtils.getFixedRequestUri(request), statMenu.getMenuId());
			
			StatisticsUserTemp.setStatisIsMenu(true);
			StatisticsUserTemp.setStatisMenuId(statMenu.getMenuId());
			statisticsService.insertStatisUserTemp(StatisticsUserTemp);
		}

		// ========== 끝 : 통계 ===============================
		
		
		
		//statisticsService.statisticsDailyBatch();
		
		
	}
	
}