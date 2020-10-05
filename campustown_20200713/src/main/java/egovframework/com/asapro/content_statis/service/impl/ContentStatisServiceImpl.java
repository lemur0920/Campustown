/**
 * 
 */
package egovframework.com.asapro.content_statis.service.impl;

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

import egovframework.com.asapro.board.service.BoardArticle;
import egovframework.com.asapro.content_statis.service.ContentStatis30;
import egovframework.com.asapro.content_statis.service.ContentStatisDay;
import egovframework.com.asapro.content_statis.service.ContentStatisMapper;
import egovframework.com.asapro.content_statis.service.ContentStatisSearch;
import egovframework.com.asapro.content_statis.service.ContentStatisService;
import egovframework.com.asapro.site.service.Site;
import egovframework.com.asapro.site.service.SiteMapper;
import egovframework.com.asapro.site.service.SiteSearch;
import egovframework.com.asapro.support.jdbc.AsaproDBUtils;
import egovframework.com.asapro.support.util.AsaproUtils;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

/**
 * 콘텐츠별 추천통계 서비스 구현
 * @author yckim
 * @since 2020. 01. 13.
 *
 */
@Service
	public class ContentStatisServiceImpl extends EgovAbstractServiceImpl implements ContentStatisService{

	private static final Logger LOGGER = LoggerFactory.getLogger(ContentStatisServiceImpl.class);
	
	@Autowired
	private ContentStatisMapper contentStatisMapper;
	
	@Autowired
	private SiteMapper siteMapper;
	
	
	//=========================== 콘텐츠별 추천통계 =================================
	//=========================== 콘텐츠별 추천통계 =================================
	//=========================== 콘텐츠별 추천통계 =================================
	
	/**
	 * 콘텐츠별 추천 통계 데이터를 등록한다.
	 */
	@Override
	public int insertContentStatisDay(ContentStatisDay contentStatisDay) {
		return contentStatisMapper.insertContentStatisDay(contentStatisDay);
	}


	/**
	 * 콘텐츠별 추천수를 증가시킨다.
	 */
	@Override
	public int updateContentStatisDayCnt(ContentStatisDay contentStatisDay) {
		return contentStatisMapper.updateContentStatisDayCnt(contentStatisDay);
	}


	/**
	 * 콘텐츠별 추천 통계 데이터를 DB에 유무에따라 등록 및 증가 처리한다.
	 */
	@Override
	public int mergeContentStatisDay(ContentStatisDay contentStatisDay) {
		Calendar cal = Calendar.getInstance();
		int result = 0;
		
		contentStatisDay.setCsCount(1);
		contentStatisDay.setCsWeekStart(AsaproUtils.getSundayByDate(cal.getTime()) );
		contentStatisDay.setCsWeekEnd(AsaproUtils.getSaturdayByDate(cal.getTime()));
		contentStatisDay.setWeekOfYearIso(String.valueOf(AsaproUtils.getWeekOfYearIso(cal.getTime())) );
		contentStatisDay.setWeekOfMonth(String.valueOf(AsaproUtils.getWeekOfMonth(cal.getTime())) );
		
		ContentStatisDay contentStatisDayModel = contentStatisMapper.selectContentStatisDay(contentStatisDay);
		if(contentStatisDayModel == null){
			result = contentStatisMapper.insertContentStatisDay(contentStatisDay);
		}else{
			result = contentStatisMapper.updateContentStatisDayCnt(contentStatisDay);
		}
		
		return result;
	}

	/**
	 * 게시판 월간 베스트글 목록을 반환한다.
	 */
	@Override
	public List<BoardArticle> getBoardBestInMonthList(ContentStatisSearch contentStatisSearch) {
		List<BoardArticle> boardBestList = null;
		boardBestList = contentStatisMapper.selectBoardBestInMonthList(contentStatisSearch);
		return boardBestList;
	}

	/**
	 * 월통계대상 데이터의 년도 목록맵을 반환한다.
	 */
	@Override
	public List<Map<String, Object>> getYearListBy30(ContentStatisSearch contentStatisSearch) {
		List<Map<String, Object>> yearList = null;
		yearList = contentStatisMapper.selectYearListBy30(contentStatisSearch);
		return yearList;
	}

	/**
	 * 월통계대상 데이터의 월 목록맵을 반환한다.
	 */
	@Override
	public List<Map<String, Object>> getMonthListBy30(ContentStatisSearch contentStatisSearch) {
		List<Map<String, Object>> monthList = contentStatisMapper.selectMonthListBy30(contentStatisSearch);
		return monthList;
	}
	
	//===================================== 배치 ========================================
	//===================================== 배치 ========================================
	//===================================== 배치 ========================================
	
	/**
	 * 콘텐의 이벤트별 통계데이터를 만든다.
	 * - 년,월,주별 콘텐츠의 이벤트별 통계
	 */
	@Override
	public int csDailyBatch() {
		//어제 날짜 기준 월,주의 데이터를 대상
		Date yesterDay = AsaproUtils.makeCalculDate(-1);
		
		int year = Integer.parseInt(AsaproUtils.getFormattedDate(yesterDay, "yyyy"));
		int month = Integer.parseInt(AsaproUtils.getFormattedDate(yesterDay, "MM"));
		
		//사이트별 처리를 위해 사이트목록 조회
		List<Site> siteList = siteMapper.selectSiteList(new SiteSearch());
		
		ContentStatisSearch contentStatisSearch = new ContentStatisSearch();
		contentStatisSearch.setPaging(true);
		contentStatisSearch.setPageSize(10);//상위 10위까지만
		contentStatisSearch.setCsYear(year);
		contentStatisSearch.setCsMonth(month);
		
		//사이트별로 처리
		if(siteList != null && !siteList.isEmpty() ){
			for(Site site : siteList) {
				contentStatisSearch.setSitePrefix(site.getSitePrefix());

				//modul별 처리
				//modulList
				String module = "";
				String subModule = "";
				List<Map<String, Object>> moduleByMonthList = contentStatisMapper.selectModuleMonth(contentStatisSearch);
				
				if(moduleByMonthList != null && !moduleByMonthList.isEmpty() ){
					for (Map<String, Object> moduleMap : moduleByMonthList) {
						module = (String)(moduleMap.get("CS_MODIUL_CODE"));
						contentStatisSearch.setCsModiulCode(module);
						
						//sub modul별 처리
						//subModulList
						List<Map<String, Object>> subModuleByMonthList = contentStatisMapper.selectSubModuleMonth(contentStatisSearch);
						
						if(subModuleByMonthList != null && !subModuleByMonthList.isEmpty() ){
							for (Map<String, Object> subModuleMap : subModuleByMonthList) {
								subModule = (String)(subModuleMap.get("CS_MODIUL_SUB_CODE"));
								contentStatisSearch.setCsModiulSubCode(subModule);
								
								//contentStatisSearch.setCsCateCode("");
								
								//전일기준 해당월 콘텐츠별 이벤트수합
								List<Map<String, Object>> cntByContentMonthList = contentStatisMapper.selectCntContentMonth(contentStatisSearch);
								if(cntByContentMonthList != null && !cntByContentMonthList.isEmpty() ){
									ContentStatis30 contentStatis30 = new ContentStatis30();
									contentStatis30.setSitePrefix(site.getSitePrefix());
									contentStatis30.setCsModiulCode(module);
									contentStatis30.setCsModiulSubCode(subModule);
									contentStatis30.setCsYear(year);
									contentStatis30.setCsMonth(month);
									contentStatis30.setCsCateCode("");
									
									//메일 배치 이므로 기존데이터 삭제
									contentStatisMapper.deleteContentStatis30(contentStatis30);
									
									for (Map<String, Object> cntMap : cntByContentMonthList) {
										contentStatis30.setCsContentId((String)(cntMap.get("CS_CONTENT_ID")));
										contentStatis30.setCsCount(AsaproDBUtils.getIntFromLongOrBigDecimal(cntMap.get("CS_COUNT")));
										
										//새랭킹 데이터 등록
										contentStatisMapper.insertContentStatis30(contentStatis30);
										
										
									}
								}
								
							}
						}
						
					}
				}
				
			}
		}	
		
		return 0;
	}




	
}