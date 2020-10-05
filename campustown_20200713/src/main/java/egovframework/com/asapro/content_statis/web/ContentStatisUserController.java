/**
 * 
 */
package egovframework.com.asapro.content_statis.web;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.WebServiceClient;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.model.CountryResponse;
import com.maxmind.geoip2.record.Country;

import egovframework.com.asapro.board.service.BoardArticle;
import egovframework.com.asapro.content_statis.service.ContentStatisSearch;
import egovframework.com.asapro.content_statis.service.ContentStatisService;
import egovframework.com.asapro.menu.service.Menu;
import egovframework.com.asapro.site.service.Site;
import egovframework.com.asapro.support.Constant;
import egovframework.com.asapro.support.annotation.CurrentSite;
import egovframework.com.asapro.support.jdbc.AsaproDBUtils;
import egovframework.com.asapro.support.pagination.Paging;
import egovframework.com.asapro.support.util.AsaproUtils;
import egovframework.com.asapro.support.util.EtcUtil;
import eu.bitwalker.useragentutils.UserAgent;

/**
 * 콘텐츠별 추천통계 사용자 컨트롤러
 * @author yckim
 * @since 2020. 01. 13.
 *
 */
@Controller
public class ContentStatisUserController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ContentStatisUserController.class);
	
	@Autowired
	private ContentStatisService contentStatisService;
	
	@Autowired
	private HttpServletRequest request;
	
	/**
	 * 통계 데이터를 비동기 방식으로 기록한다.
	 * @param request
	 * @param response
	 * @param currentSite
	 * @param statMenu
	 */
	@RequestMapping(value=Constant.APP_PATH + Constant.SITE_ID_PATH + "/contentStatis/{csModiulCode}/{csModiulSubCode}/bestList", method=RequestMethod.GET)
	public String statBestListGet(Model model, @CurrentSite Site currentSite, @ModelAttribute("contentStatisSearch") ContentStatisSearch contentStatisSearch){
		//어제 날짜 기준 월,주의 데이터를 대상
		Date yesterDay = AsaproUtils.makeCalculDate(-1);
		int year = Integer.parseInt(AsaproUtils.getFormattedDate(yesterDay, "yyyy"));
		int month = Integer.parseInt(AsaproUtils.getFormattedDate(yesterDay, "MM"));
		
		contentStatisSearch.setSitePrefix(currentSite.getSitePrefix());
		contentStatisSearch.setPageSize(9);
		if(contentStatisSearch.getCsYear() == null){
			contentStatisSearch.setCsYear(year);
		}
		
		//년도목록
		List<Map<String, Object>> yearList = contentStatisService.getYearListBy30(contentStatisSearch);
		model.addAttribute("yearList", yearList);
		//해당년도의 월목록
		List<Map<String, Object>> monthList = contentStatisService.getMonthListBy30(contentStatisSearch);
		model.addAttribute("monthList", monthList);
		
		//월 정보가 없을경우 해당년도의 데이터중 가장큰 월
		if(contentStatisSearch.getCsMonth() == null){
			if(monthList != null && !monthList.isEmpty() ){
				contentStatisSearch.setCsMonth(AsaproDBUtils.getIntFromLongOrBigDecimal(monthList.get(monthList.size() - 1).get("CS_MONTH")) );
			} else {
				contentStatisSearch.setCsMonth(month);
			}
		}

		//contentStatisSearch.set
		if("boardtype".equals(contentStatisSearch.getCsModiulCode()) ){
			//주별 - 차후 개발
			
			//월별
			List<BoardArticle> boardBestList = contentStatisService.getBoardBestInMonthList(contentStatisSearch);
			model.addAttribute("boardBestList", boardBestList);
			
			
			/*if("hit".equals(contentStatisSearch.getCsModiulSubCode()) ){
				return AsaproUtils.getThemePath(request) + "contentStatis/board/hitBestList";
			} else if("recommend".equals(contentStatisSearch.getCsModiulSubCode()) ){
				return AsaproUtils.getThemePath(request) + "contentStatis/board/recommendBestList";
			}*/
			return AsaproUtils.getThemePath(request) + "contentStatis/board/bestList";
		} else if("archivetype".equals(contentStatisSearch.getCsModiulCode()) ){
			
			
			
			
			
			
			/*if("hit".equals(contentStatisSearch.getCsModiulSubCode()) ){
				return AsaproUtils.getThemePath(request) + "contentStatis/archive/hitBestList";
			} else if("recommend".equals(contentStatisSearch.getCsModiulSubCode()) ){
				return AsaproUtils.getThemePath(request) + "contentStatis/archive/recommendBestList";
			}*/
			return AsaproUtils.getThemePath(request) + "contentStatis/archive/bestList";
		}
		return AsaproUtils.getThemePath(request) + "contentStatis/bestList";
	}
	
	
	
	
	
	
	
	
	
	
/*	
	*//**
	 * 통계 데이터 배치 테스트
	 * @param request
	 * @param response
	 * @param currentSite
	 * @param statMenu
	 *//*
	@RequestMapping(value=Constant.APP_PATH + Constant.SITE_ID_PATH + "/contentStatis/batchtest", method=RequestMethod.GET)
	public void statBatchTestGet(){
		
		// ========== 시작 : 통계 ===============================
		

		// ========== 끝 : 통계 ===============================
		
		
		
		contentStatisService.csDailyBatch();
		
		
	}*/
	
}