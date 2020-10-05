/**
 * 
 */
package egovframework.com.asapro.content_statis.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import egovframework.com.asapro.content_statis.service.ContentStatisService;
import egovframework.com.asapro.menu.service.Menu;
import egovframework.com.asapro.menu.service.MenuSearch;
import egovframework.com.asapro.menu.service.MenuService;
import egovframework.com.asapro.site.service.Site;
import egovframework.com.asapro.support.Constant;
import egovframework.com.asapro.support.annotation.CurrentSite;
import egovframework.com.asapro.support.jdbc.AsaproDBUtils;
import egovframework.com.asapro.support.pagination.Paging;
import egovframework.com.asapro.support.util.AsaproUtils;

/**
 * 콘텐츠별 추천통계 관리자 컨트롤러
 * @author yckim
 * @since 2020. 01. 13.
 *
 */
@Controller
public class ContentStatisAdminController {
	
	@Autowired
	private ContentStatisService contentStatisService;
	
	@Autowired
	private MenuService menuService;
	
	//=====================================================================================================================
	//============================================ 콘텐츠별 추천통계 ==============================================================
	//=====================================================================================================================
/*	
	*//**
	 * 사이트 접속 통계를 반환한다.
	 * - 일자별, OS별, 브라우저별, 접속국가별 등
	 * - session, os, browser, country
	 * @param model
	 * @param 
	 * @return
	 *//*
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/statistics/{statisType}.do", method=RequestMethod.GET)
	public String statisticsGet(Model model, @CurrentSite Site currentSite, @ModelAttribute("statisticsSearch") StatisticsSearch statisticsSearch){
		statisticsSearch.setSitePrefix(currentSite.getSitePrefix());
		//검색 날짜가 없을경우 디폴트 8일전~1일전 1주일간
		if(StringUtils.isBlank(statisticsSearch.getStatisStartDate()) ){
			statisticsSearch.setStatisStartDate(AsaproUtils.getFormattedDate(AsaproUtils.makeCalculDate(-7)));
		}
		if(StringUtils.isBlank(statisticsSearch.getStatisEndDate()) ){
			statisticsSearch.setStatisEndDate(AsaproUtils.getFormattedDate(AsaproUtils.makeCalculDate(-1)));
		}
		//세션통계일때 데이트타입이 없을경우 '일'을 디폴트로 셋트
		//모든통계를 '일'을 디폴트로 변경
		if("session".equals(statisticsSearch.getStatisType()) && StringUtils.isBlank(statisticsSearch.getStatisDateType()) ){
			statisticsSearch.setStatisDateType("day");
		}
		
		Date startDate = AsaproUtils.getParsedDate(statisticsSearch.getStatisStartDate(), "yyyy-MM-dd");
		Date endDate = AsaproUtils.getParsedDate(statisticsSearch.getStatisEndDate(), "yyyy-MM-dd");
		
		if("session".equals(statisticsSearch.getStatisType()) ){
			List<Map<String, Object>> statisticsSessionListTemp = statisticsService.getStatisSessionMapByDateType(statisticsSearch);
			
			Date chkDate = AsaproUtils.getParsedDate(statisticsSearch.getStatisStartDate(), "yyyy-MM-dd");
			
			List<Map<String, Object>> statisticsSessionMapList = new ArrayList<Map<String, Object>>();
			if(statisticsSessionListTemp != null && !statisticsSessionListTemp.isEmpty() ){
				Map<String, Integer> statisticsTempMap = new HashMap<String, Integer>();
				
					
				String dateStr = "";
				if("hour".equals(statisticsSearch.getStatisDateType()) ){
					for (Map<String, Object> map : statisticsSessionListTemp) {
						statisticsTempMap.put((String)map.get("DATE_KEY"), AsaproDBUtils.getIntFromLongOrBigDecimal(map.get("CNT_BY_DAY")));
					}
					while (chkDate.compareTo(endDate) <= 0) {
						for(int i = 0; i <= 23; i++){
							Map<String, Object> statisticsSessionMap = new HashMap<String, Object>();
							dateStr = AsaproUtils.getFormattedDate(chkDate, "yyyy-MM-dd") + " " + String.format("%02d", i) + ":00";
							statisticsSessionMap.put("DATE_KEY", dateStr);
							if(statisticsTempMap.get(dateStr) != null ){
								statisticsSessionMap.put("CNT_BY_DAY", statisticsTempMap.get(dateStr));
							} else {
								statisticsSessionMap.put("CNT_BY_DAY", 0);
							}
							statisticsSessionMapList.add(statisticsSessionMap);
						}
						chkDate = AsaproUtils.makeCalculDate(chkDate, 1);
					}
				} else if("day".equals(statisticsSearch.getStatisDateType()) ) {
					for (Map<String, Object> map : statisticsSessionListTemp) {
						statisticsTempMap.put((String)map.get("DATE_KEY"), AsaproDBUtils.getIntFromLongOrBigDecimal(map.get("CNT_BY_DAY")));
					}
					while (chkDate.compareTo(endDate) <= 0) {
						Map<String, Object> statisticsSessionMap = new HashMap<String, Object>();
						dateStr = AsaproUtils.getFormattedDate(chkDate, "yyyy-MM-dd");
						statisticsSessionMap.put("DATE_KEY", dateStr);
						if(statisticsTempMap.get(dateStr) != null ){
							statisticsSessionMap.put("CNT_BY_DAY", statisticsTempMap.get(dateStr));
						} else {
							statisticsSessionMap.put("CNT_BY_DAY", 0);
						}
						statisticsSessionMapList.add(statisticsSessionMap);
						chkDate = AsaproUtils.makeCalculDate(chkDate, 1);
					}
				} else if("week".equals(statisticsSearch.getStatisDateType()) ) {
					for (Map<String, Object> map : statisticsSessionListTemp) {
						statisticsTempMap.put((String)map.get("DATE_KEY"), AsaproDBUtils.getIntFromLongOrBigDecimal(map.get("CNT_BY_DAY")));
					}
					
					
					String compararDate = "";
					List<Map<String, Object>> weekTermList = new ArrayList<Map<String, Object>>();
					while (chkDate.compareTo(endDate) <= 0) {
						Map<String, Object> weekTermMap = new HashMap<String, Object>();
						Date weekStartTemp = AsaproUtils.getSundayByDate1(chkDate);
						String weekStart = AsaproUtils.getFormattedDate(weekStartTemp, "yyyy-MM-dd");
						String weekEnd = AsaproUtils.getFormattedDate(AsaproUtils.makeCalculDate(weekStartTemp, 6));
						int weekOfYearIso = AsaproUtils.getWeekOfYearIso(weekStartTemp);
						int weekOfMonth = AsaproUtils.getWeekOfMonth(weekStartTemp);
						
						if(weekStart.equals(compararDate) ){
							chkDate = AsaproUtils.makeCalculDate(chkDate, 1);
							continue;
						}
						weekTermMap.put("WEEK_START", weekStart);
						weekTermMap.put("WEEK_END", weekEnd);
						weekTermMap.put("WEEK_OF_YEAR_ISO", weekOfYearIso);
						weekTermMap.put("WEEK_OF_MONTH", weekOfMonth);
						
						weekTermList.add(weekTermMap);
						compararDate = weekStart;
						chkDate = AsaproUtils.makeCalculDate(chkDate, 1);
					}
					if(weekTermList != null && !weekTermList.isEmpty()){
						for (Map<String, Object> map : weekTermList) {
							Map<String, Object> statisticsSessionMap = new HashMap<String, Object>();
							dateStr = (String)map.get("WEEK_START") + "~" + (String)map.get("WEEK_END");
							if((AsaproUtils.getParsedDate((String)map.get("WEEK_START"),"yyyy-MM-dd")).before(startDate) ){
								statisticsSessionMap.put("DATE_KEY", AsaproUtils.getFormattedDate(startDate));
							}else{
								statisticsSessionMap.put("DATE_KEY", (String)map.get("WEEK_START"));
								
							}
							if(statisticsTempMap.get(dateStr) != null ){
								statisticsSessionMap.put("CNT_BY_DAY", statisticsTempMap.get(dateStr));
							} else {
								statisticsSessionMap.put("CNT_BY_DAY", 0);
							}
							statisticsSessionMap.put("WEEK_START", map.get("WEEK_START"));
							statisticsSessionMap.put("WEEK_END", map.get("WEEK_END"));
							statisticsSessionMap.put("WEEK_OF_YEAR_ISO", map.get("WEEK_OF_YEAR_ISO"));
							statisticsSessionMap.put("WEEK_OF_MONTH", map.get("WEEK_OF_MONTH"));
							statisticsSessionMapList.add(statisticsSessionMap);
						}
					}
						
				} else if("month".equals(statisticsSearch.getStatisDateType()) ) {
					for (Map<String, Object> map : statisticsSessionListTemp) {
						statisticsTempMap.put((String)map.get("DATE_KEY"), AsaproDBUtils.getIntFromLongOrBigDecimal(map.get("CNT_BY_DAY")));
					}
					String compararDate = "";
					while (chkDate.compareTo(endDate) <= 0) {
						Map<String, Object> statisticsSessionMap = new HashMap<String, Object>();
						dateStr = AsaproUtils.getFormattedDate(chkDate, "yyyy-MM");
						if(dateStr.equals(compararDate) ){
							chkDate = AsaproUtils.makeCalculDate(chkDate, 1);
							continue;
						}
						statisticsSessionMap.put("DATE_KEY", dateStr);
						if(statisticsTempMap.get(dateStr) != null ){
							statisticsSessionMap.put("CNT_BY_DAY", statisticsTempMap.get(dateStr));
						} else {
							statisticsSessionMap.put("CNT_BY_DAY", 0);
						}
						statisticsSessionMapList.add(statisticsSessionMap);
						compararDate = dateStr;
						chkDate = AsaproUtils.makeCalculDate(chkDate, 1);
					}
				}
				
			}
			model.addAttribute("statisticsSessionMapList", statisticsSessionMapList);
		} else if("os".equals(statisticsSearch.getStatisType()) ){
			
			List<Map<String, Object>> statisticsOsMapList = statisticsService.getStatisOsMapList(statisticsSearch);
			model.addAttribute("statisticsOsMapList", statisticsOsMapList);
			
		} else if("browser".equals(statisticsSearch.getStatisType()) ){
			
			List<Map<String, Object>> statisticsBrowserMapList = statisticsService.getStatisBrowserMapList(statisticsSearch);
			model.addAttribute("statisticsBrowserMapList", statisticsBrowserMapList);
			
		} else if("country".equals(statisticsSearch.getStatisType()) ){
			
			List<Map<String, Object>> statisticsCountryMapList = statisticsService.getStatisCountryMapList(statisticsSearch);
			model.addAttribute("statisticsCountryMapList", statisticsCountryMapList);
			
		} else if("menu".equals(statisticsSearch.getStatisType()) ){
			//메뉴 목록
			MenuSearch menuSearch = new MenuSearch();
			menuSearch.setSitePrefix(currentSite.getSitePrefix());
			menuSearch.setPaging(false);
			List<Menu> menuList = menuService.getMenuList(menuSearch);
			model.addAttribute("menuList", menuList);
			
			List<Map<String, Object>> statisticsMenuMapList = statisticsService.getStatisMenuMapList(statisticsSearch);
			Map<String, Integer> statisticsMenuMap = new HashMap<String, Integer>();
			if(statisticsMenuMapList != null && !statisticsMenuMapList.isEmpty() ){
				
				for (Map<String, Object> map : statisticsMenuMapList) {
					statisticsMenuMap.put((String)map.get("MENU_KEY"), AsaproDBUtils.getIntFromLongOrBigDecimal(map.get("CNT_BY_MENU")));
				}
			}
			model.addAttribute("statisticsMenuMap", statisticsMenuMap);
			
		}

		return "asapro/admin/statistics/" + statisticsSearch.getStatisType();
	}
	*/
}