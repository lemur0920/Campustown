/**
 * 
 */
package egovframework.com.asapro.statistics.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import egovframework.com.asapro.site.service.Site;
import egovframework.com.asapro.site.service.SiteMapper;
import egovframework.com.asapro.site.service.SiteSearch;
import egovframework.com.asapro.statistics.service.StatisticsAdminAccessLog;
import egovframework.com.asapro.statistics.service.StatisticsAdminAccessLogSearch;
import egovframework.com.asapro.statistics.service.StatisticsMemberInquireLog;
import egovframework.com.asapro.statistics.service.StatisticsMemberInquireLogSearch;
import egovframework.com.asapro.statistics.service.StatisticsBrowser;
import egovframework.com.asapro.statistics.service.StatisticsCountry;
import egovframework.com.asapro.statistics.service.StatisticsMapper;
import egovframework.com.asapro.statistics.service.StatisticsMenu;
import egovframework.com.asapro.statistics.service.StatisticsOs;
import egovframework.com.asapro.statistics.service.StatisticsService;
import egovframework.com.asapro.statistics.service.StatisticsSession;
import egovframework.com.asapro.statistics.service.StatisticsUserTemp;
import egovframework.com.asapro.statistics.service.StatisticsSearch;
import egovframework.com.asapro.support.jdbc.AsaproDBUtils;
import egovframework.com.asapro.support.util.AsaproUtils;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

/**
 * 통계 (로그) 서비스 구현
 * @author yckim
 * @since 2019. 01. 02.
 *
 */
@Service
	public class StatisticsServiceImpl extends EgovAbstractServiceImpl implements StatisticsService{

	private static final Logger LOGGER = LoggerFactory.getLogger(StatisticsServiceImpl.class);
	
	@Autowired
	private StatisticsMapper statisticsMapper;
	
	@Autowired
	private SiteMapper siteMapper;
	
	
	//=========================== 접속로그 =================================
	//=========================== 접속로그 =================================
	//=========================== 접속로그 =================================
	
	/**
	 * 관리자 접속로그 저장
	 * @param statAccessLog
	 * @return 
	 */
	@Override
	public int insertStatisticsAdminAccessLog(StatisticsAdminAccessLog statisticsAdminAccessLog) {
		
		int inserted = 0;
		inserted = statisticsMapper.insertStatisticsAdminAccessLog(statisticsAdminAccessLog);
		
		return inserted;
	}
	
	/**
	 * 관리자 접속로그 목록
	 * @param statisticsAdminAccessLogSearch
	 * @return
	 */
	@Override
	public List<StatisticsAdminAccessLog> getStatisticsAdminAccessLogList(StatisticsAdminAccessLogSearch statisticsAdminAccessLogSearch) {
		return statisticsMapper.selectStatisticsAdminAccessLogList(statisticsAdminAccessLogSearch);
	}

	 /**
	  * 관리자 접속로그 목록 토탈
	  * 
	  */
	@Override
	public int getStatisticsAdminAccessLogListTotal(StatisticsAdminAccessLogSearch statisticsAdminAccessLogSearch) {
		return statisticsMapper.selectStatisticsAdminAccessLogListTotal(statisticsAdminAccessLogSearch);
	}

	//=========================== 관리자 정보 관리 로그 crud =================================
	//=========================== 관리자 정보 관리 로그 crud =================================
	//=========================== 관리자 정보 관리 로그 crud =================================
	/**
	 * 관리자 정보 관리 로그를 저장한다.
	 */
	@Override
	public int insertStatisticsAdminInquireLog(StatisticsMemberInquireLog statisticsMemberInquireLog) {
		int inserted = 0;
		inserted = statisticsMapper.insertStatisticsAdminInquireLog(statisticsMemberInquireLog);
		
		return inserted;
	}

	/**
	 * 관리자 정보 관리 로그 목록을 조회한다.
	 */
	@Override
	public List<StatisticsMemberInquireLog> getStatisticsAdminInquireLogList(
			StatisticsMemberInquireLogSearch statisticsMemberInquireLogSearch) {
		return statisticsMapper.selectStatisticsAdminInquireLogList(statisticsMemberInquireLogSearch);
	}

	/**
	 * 관리자 정보 관리 로그 목록 토탈을 조회한다.
	 */
	@Override
	public int getStatisticsAdminInquireLogListTotal(StatisticsMemberInquireLogSearch statisticsMemberInquireLogSearch) {
		return statisticsMapper.selectStatisticsAdminInquireLogListTotal(statisticsMemberInquireLogSearch);
	}

	//=========================== 사용자 정보 조회 로그 crud =================================
	//=========================== 사용자 정보 조회 로그 crud =================================
	//=========================== 사용자 정보 조회 로그 crud =================================
	/**
	 * 사용자 정보 조회 로그를 저장한다.
	 */
	@Override
	public int insertStatisticsUserInquireLog(StatisticsMemberInquireLog statisticsMemberInquireLog) {
		int inserted = 0;
		inserted = statisticsMapper.insertStatisticsUserInquireLog(statisticsMemberInquireLog);
		
		return inserted;
	}
	
	/**
	 * 사용자 정보 조회 로그 목록을 조회한다.
	 */
	@Override
	public List<StatisticsMemberInquireLog> getStatisticsUserInquireLogList(
			StatisticsMemberInquireLogSearch statisticsMemberInquireLogSearch) {
		return statisticsMapper.selectStatisticsUserInquireLogList(statisticsMemberInquireLogSearch);
	}
	
	/**
	 * 사용자 정보 조회 로그 목록 토탈을 조회한다.
	 */
	@Override
	public int getStatisticsUserInquireLogListTotal(StatisticsMemberInquireLogSearch statisticsMemberInquireLogSearch) {
		return statisticsMapper.selectStatisticsUserInquireLogListTotal(statisticsMemberInquireLogSearch);
	}
	
	//=========================== 통계 =================================
	//=========================== 통계 =================================
	//=========================== 통계 =================================
	
	/**
	 * 사용자 접속 통계 임시테이블에 베이스 데이터를 등록한다.
	 */
	@Override
	public int insertStatisUserTemp(StatisticsUserTemp statisticsUserTemp) {
		return statisticsMapper.insertStatisticsUserTemp(statisticsUserTemp);
	}

	/**
	 * 사용자 접속통계 베이스데이터를 일 합산데이터로 배치한다.
	 * - 일별, OS별, 브라우저별, 국가별, 메뉴별
	 */
	@Override
	public int statisticsDailyBatch() {
		//어제 날짜 데이터를 대상
		Date yesterDay = AsaproUtils.makeCalculDate(-1);
		
		int year = Integer.parseInt(AsaproUtils.getFormattedDate(yesterDay, "yyyy"));
		int month = Integer.parseInt(AsaproUtils.getFormattedDate(yesterDay, "MM"));
		int day = Integer.parseInt(AsaproUtils.getFormattedDate(yesterDay, "dd"));
		int lastDayTotal = 0;
		int SessionChkCnt = 0;
		int osChkCnt = 0;
		int browserChkCnt = 0;
		int countryChkCnt = 0;
		int menuChkCnt = 0;
//		System.out.println("year : " + year);
//		System.out.println("month : " + month);
//		System.out.println("day : " + day);
		
		List<Site> siteList = siteMapper.selectSiteList(new SiteSearch());
		
		StatisticsSearch statisticsSearch = new StatisticsSearch();
		statisticsSearch.setPaging(false);
		statisticsSearch.setStatisYear(year);
		statisticsSearch.setStatisMonth(month);
		statisticsSearch.setStatisDay(day);
		
		//사이트별로 처리
		if(siteList != null && !siteList.isEmpty() ){
			for(Site site : siteList) {
				statisticsSearch.setSitePrefix(site.getSitePrefix());
				
				//어제날짜 접속 수량확인 - *** 메뉴통계는 제외
				int lastDayCnt = statisticsMapper.selectStatisticsUserTempListTotal(statisticsSearch);
				lastDayTotal += lastDayCnt;
//				System.out.println(lastDayCnt);
				
				/**
				 * 시간별 SESSION 데이터
				 */
				
				//어제날짜 시간별 접속(세션) 수량확인
				List<Map<String, Object>> lastCntByHourList = statisticsMapper.selectStatisticsUserTempCntByHour(statisticsSearch);

				//시간별 데이터에 어제 날짜데이터가 이미 있는지 확인
				StatisticsSession statisticsSessionSearch = new StatisticsSession();
				statisticsSessionSearch.setStatisYear(year);
				statisticsSessionSearch.setStatisMonth(month);
				statisticsSessionSearch.setStatisDay(day);
				statisticsSessionSearch.setSitePrefix(site.getSitePrefix());
				
				if(lastCntByHourList != null && !lastCntByHourList.isEmpty() ){
					for (Map<String, Object> map : lastCntByHourList) {

						statisticsSessionSearch.setStatisHour(AsaproDBUtils.getIntFromLongOrBigDecimal(map.get("STATIS_HOUR")) );
						statisticsSessionSearch.setStatisSessionCnt(AsaproDBUtils.getIntFromLongOrBigDecimal(map.get("CNT_BY_HOUR")) );
						StatisticsSession statisSession = statisticsMapper.selectStatisSession(statisticsSessionSearch);
						
						//이미 레코드가 있으면
						if(statisSession != null ){
							if(statisticsMapper.updateStatisSession(statisticsSessionSearch) > 0){
								SessionChkCnt += statisticsSessionSearch.getStatisSessionCnt();
							}
						}
						//레코드가 없으면 - 없어야 정상
						else {
							if(statisticsMapper.insertStatisSession(statisticsSessionSearch) > 0){
								SessionChkCnt += statisticsSessionSearch.getStatisSessionCnt();
							}
						}
					}
				}
				
				/**
				 * OS별 데이터
				 */
				//어제날짜 OS별 접속 수량확인
				List<Map<String, Object>> lastCntByOsList = statisticsMapper.selectStatisticsUserTempCntByOs(statisticsSearch);
				
				//OS별데이터에 어제 날짜데이터가 이미 있는지 확인
				StatisticsOs statisticsOsSearch = new StatisticsOs();
				statisticsOsSearch.setStatisYear(year);
				statisticsOsSearch.setStatisMonth(month);
				statisticsOsSearch.setStatisDay(day);
				statisticsOsSearch.setSitePrefix(site.getSitePrefix());
				
				if(lastCntByOsList != null && !lastCntByOsList.isEmpty() ){
					for (Map<String, Object> map : lastCntByOsList) {

						statisticsOsSearch.setStatisOs((String)map.get("STATIS_OS"));
						statisticsOsSearch.setStatisOsCnt(AsaproDBUtils.getIntFromLongOrBigDecimal(map.get("CNT_BY_OS")) );
						StatisticsOs statisOs = statisticsMapper.selectStatisOs(statisticsOsSearch);
						
						//이미 레코드가 있으면
						if(statisOs != null ){
							if(statisticsMapper.updateStatisOs(statisticsOsSearch) > 0){
								osChkCnt += statisticsOsSearch.getStatisOsCnt() ;
							}
						}
						//레코드가 없으면 - 없어야 정상
						else {
							if(statisticsMapper.insertStatisOs(statisticsOsSearch) > 0){
								osChkCnt += statisticsOsSearch.getStatisOsCnt() ;
							}
						}
					}
				}
				
				/**
				 * 브라우저별 데이터
				 */
				//어제날짜 브라우저별 접속 수량확인
				List<Map<String, Object>> lastCntByBrowserList = statisticsMapper.selectStatisticsUserTempCntByBrowser(statisticsSearch);
				
				//Browser별 데이터에 어제 날짜데이터가 이미 있는지 확인
				StatisticsBrowser statisticsBrowserSearch = new StatisticsBrowser();
				statisticsBrowserSearch.setStatisYear(year);
				statisticsBrowserSearch.setStatisMonth(month);
				statisticsBrowserSearch.setStatisDay(day);
				statisticsBrowserSearch.setSitePrefix(site.getSitePrefix());
				
				if(lastCntByBrowserList != null && !lastCntByBrowserList.isEmpty() ){
					for (Map<String, Object> map : lastCntByBrowserList) {

						statisticsBrowserSearch.setStatisBrowser((String)map.get("STATIS_BROWSER"));
						statisticsBrowserSearch.setStatisBrowserCnt(AsaproDBUtils.getIntFromLongOrBigDecimal(map.get("CNT_BY_BROWSER")) );
						StatisticsBrowser statisBrowser = statisticsMapper.selectStatisBrowser(statisticsBrowserSearch);
						
						//이미 레코드가 있으면
						if(statisBrowser != null ){
							if(statisticsMapper.updateStatisBrowser(statisticsBrowserSearch) > 0){
								browserChkCnt += statisticsBrowserSearch.getStatisBrowserCnt();
							}
						}
						//레코드가 없으면 - 없어야 정상
						else {
							if(statisticsMapper.insertStatisBrowser(statisticsBrowserSearch) > 0){
								browserChkCnt += statisticsBrowserSearch.getStatisBrowserCnt();
							}
						}
					}
				}
				
				/**
				 * 국가별 데이터
				 */
				//어제날짜 국가별 접속 수량확인
				List<Map<String, Object>> lastCntByCountryList = statisticsMapper.selectStatisticsUserTempCntByCountry(statisticsSearch);
				
				//국가별 데이터에 어제 날짜데이터가 이미 있는지 확인
				StatisticsCountry statisticsCountrySearch = new StatisticsCountry();
				statisticsCountrySearch.setStatisYear(year);
				statisticsCountrySearch.setStatisMonth(month);
				statisticsCountrySearch.setStatisDay(day);
				statisticsCountrySearch.setSitePrefix(site.getSitePrefix());
				
				if(lastCntByCountryList != null && !lastCntByCountryList.isEmpty() ){
					for (Map<String, Object> map : lastCntByCountryList) {

						statisticsCountrySearch.setStatisCountryKor(StringUtils.defaultString((String)map.get("STATIS_COUNTRY_KOR"), "Unknown"));
						statisticsCountrySearch.setStatisCountryEng(StringUtils.defaultString((String)map.get("STATIS_COUNTRY_ENG"), "Unknown"));
						statisticsCountrySearch.setStatisCountryCnt(AsaproDBUtils.getIntFromLongOrBigDecimal(map.get("CNT_BY_COUNTRY")) );
						StatisticsCountry statisCountry = statisticsMapper.selectStatisCountry(statisticsCountrySearch);
						
						//이미 레코드가 있으면
						if(statisCountry != null ){
							if(statisticsMapper.updateStatisCountry(statisticsCountrySearch) > 0){
								countryChkCnt += statisticsCountrySearch.getStatisCountryCnt();
							}
						}
						//레코드가 없으면 - 없어야 정상
						else {
							if(statisticsMapper.insertStatisCountry(statisticsCountrySearch) > 0){
								countryChkCnt += statisticsCountrySearch.getStatisCountryCnt();
							}
						}
					}
				}
				
				
				/**
				 * 메뉴별 데이터
				 */
				//어제날짜 메뉴별 접속 수량확인
				statisticsSearch.setStatisIsMenu(true);
				List<Map<String, Object>> lastCntByMenuList = statisticsMapper.selectStatisticsUserTempCntByMenu(statisticsSearch);
				
				//메뉴별 데이터에 어제 날짜데이터가 이미 있는지 확인
				StatisticsMenu statisticsMenuSearch = new StatisticsMenu();
				statisticsMenuSearch.setStatisYear(year);
				statisticsMenuSearch.setStatisMonth(month);
				statisticsMenuSearch.setStatisDay(day);
				statisticsMenuSearch.setSitePrefix(site.getSitePrefix());
				
				if(lastCntByMenuList != null && !lastCntByMenuList.isEmpty() ){
					for (Map<String, Object> map : lastCntByMenuList) {

						statisticsMenuSearch.setStatisMenuId((String)map.get("STATIS_MENU_ID"));
						statisticsMenuSearch.setStatisMenuCnt(AsaproDBUtils.getIntFromLongOrBigDecimal(map.get("CNT_BY_MENU")) );
						StatisticsMenu statisMenu = statisticsMapper.selectStatisMenu(statisticsMenuSearch);
						
						//이미 레코드가 있으면
						if(statisMenu != null ){
							if(statisticsMapper.updateStatisMenu(statisticsMenuSearch) > 0){
								menuChkCnt += statisticsMenuSearch.getStatisMenuCnt();
							}
						}
						//레코드가 없으면 - 없어야 정상
						else {
							if(statisticsMapper.insertStatisMenu(statisticsMenuSearch) > 0){
								menuChkCnt += statisticsMenuSearch.getStatisMenuCnt();
							}
						}
					}
				}
				statisticsSearch.setStatisIsMenu(false);
				//List<StatisticsUserTemp> statisticsUserTempList = statisticsMapper.selectStatisticsUserTempList(statisticsSearch);
				//EtcUtil.listInfo(statisticsUserTempList);
			}
		} else {
			LOGGER.info("[ASAPRO] statisticsDailyBatch : siteList is null !!! ");
		}
		
		//모든 통계추출 데이터가 정상 수량만큼 처리 됐을경우만 기본데이터 삭제
		if(lastDayTotal == SessionChkCnt && lastDayTotal == osChkCnt && lastDayTotal == browserChkCnt && lastDayTotal == countryChkCnt ){
			//삭제 처리 
			statisticsMapper.deleteStatisticsUserTemp(statisticsSearch);
			
			System.out.println("정상처리 삭제 : " + lastDayTotal);
			System.out.println("lastDayTotal : " + lastDayTotal);
			System.out.println("dayChkCnt : " + SessionChkCnt);
			System.out.println("osChkCnt : " + osChkCnt);
			System.out.println("browserChkCnt : " + browserChkCnt);
			System.out.println("countryChkCnt : " + countryChkCnt);
			System.out.println("menuChkCnt : " + menuChkCnt);
		}
		return 0;
	}

	/**
	 * 세션 접속통계정보를 반환한다.
	 */
	@Override
	public List<StatisticsSession> getStatisticsSessionList(StatisticsSearch statisticsSearch) {
		List<StatisticsSession> statisticsSessionList = statisticsMapper.selectStatisSessionList(statisticsSearch);
		return statisticsSessionList;
	}
	
	/**
	 * 월, 주, 일, 시별 세션 접속통계정보를 반환한다.
	 */
	@Override
	public List<Map<String, Object>> getStatisSessionMapByDateType(StatisticsSearch statisticsSearch) {
		List<Map<String, Object>> statisticsSessionList = null;
		if("hour".equals(statisticsSearch.getStatisDateType()) ){
			statisticsSessionList = statisticsMapper.selectStatisSessionMapByDateTypeHour(statisticsSearch);
		} else if("day".equals(statisticsSearch.getStatisDateType()) ) {
			statisticsSessionList = statisticsMapper.selectStatisSessionMapByDateTypeDay(statisticsSearch);
		} else if("week".equals(statisticsSearch.getStatisDateType()) ) {
			statisticsSessionList = statisticsMapper.selectStatisSessionMapByDateTypeWeek(statisticsSearch);
		} else if("month".equals(statisticsSearch.getStatisDateType()) ) {
			statisticsSessionList = statisticsMapper.selectStatisSessionMapByDateTypeMonth(statisticsSearch);
		}
		
		return statisticsSessionList;
	}

	/**
	 * 일별 OS별 접속통계정보 map 목록을 반환한다.
	 */
	@Override
	public List<Map<String, Object>> getStatisOsMapList(StatisticsSearch statisticsSearch) {
		return statisticsMapper.selectStatisOsMapList(statisticsSearch);
	}

	/**
	 * 일별 OS별 접속통계정보 목록 반환한다.
	 */
	@Override
	public List<StatisticsOs> getStatisOsList(StatisticsSearch statisticsSearch) {
		return statisticsMapper.selectStatisOsList(statisticsSearch);
	}

	/**
	 * 일별 브라우저별 접속통계정보 map 목록을 반환한다.
	 */
	@Override
	public List<Map<String, Object>> getStatisBrowserMapList(StatisticsSearch statisticsSearch) {
		return statisticsMapper.selectStatisBrowserMapList(statisticsSearch);
	}

	/**
	 * 일별 국가별 접속통계정보 map 목록을 반환한다.
	 */
	@Override
	public List<Map<String, Object>> getStatisCountryMapList(StatisticsSearch statisticsSearch) {
		return statisticsMapper.selectStatisCountryMapList(statisticsSearch);
	}

	/**
	 * 일별 메뉴별 접속통계정보 map 목록을 반환한다.
	 */
	@Override
	public List<Map<String, Object>> getStatisMenuMapList(StatisticsSearch statisticsSearch) {
		return statisticsMapper.selectStatisMenuMapList(statisticsSearch);
	}
	
	
}