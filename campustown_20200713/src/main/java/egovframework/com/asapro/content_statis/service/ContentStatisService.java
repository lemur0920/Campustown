/**
 * 
 */
package egovframework.com.asapro.content_statis.service;

import java.util.List;
import java.util.Map;

import egovframework.com.asapro.board.service.BoardArticle;

/**
 * 콘텐츠별 추천통계 서비스
 * @author yckim
 * @since 2020. 01. 13.
 *
 */
public interface ContentStatisService {
	
	//=========================== 콘텐츠별 추천통계 =================================
	//=========================== 콘텐츠별 추천통계 =================================
	//=========================== 콘텐츠별 추천통계 =================================
	
	/**
	 * 콘텐츠별 추천 통계 데이터를 등록한다.
	 * @param contentStatisDay
	 * @return 등록결과
	 */
	public int insertContentStatisDay(ContentStatisDay contentStatisDay);
	
	/**
	 * 콘텐츠별 추천수를 증가시킨다.
	 * @param contentStatisDay
	 * @return 증가결과
	 */
	public int updateContentStatisDayCnt(ContentStatisDay contentStatisDay);
	
	/**
	 * 콘텐츠별 추천 통계 데이터를 DB에 유무에따라 등록 및 증가 처리한다.
	 * @param contentStatisDay
	 * @return 등록 및 증가 결과
	 */
	public int mergeContentStatisDay(ContentStatisDay contentStatisDay);
	
	
	//===================================== 베스트 콘텐츠 목록 ========================================
	//===================================== 베스트 콘텐츠 목록 ========================================
	//===================================== 베스트 콘텐츠 목록 ========================================
	
	/**
	 * 게시판 월간 베스트글 목록을 반환한다.
	 * @param contentStatisSearch
	 * @return 게시판 글 목록
	 */
	public List<BoardArticle> getBoardBestInMonthList(ContentStatisSearch contentStatisSearch);
	
	/**
	 * 월통계대상 데이터의 년도 목록맵을 반환한다.
	 * @param contentStatisSearch
	 * @return 년도 목록 맵
 	 */
	public List<Map<String, Object>> getYearListBy30(ContentStatisSearch contentStatisSearch);

	/**
	 * 월통계대상 데이터의 월 목록맵을 반환한다.
	 * @param contentStatisSearch
	 * @return 월 목록 맵
	 */
	public List<Map<String, Object>> getMonthListBy30(ContentStatisSearch contentStatisSearch);


	
	//===================================== 배치 ========================================
	//===================================== 배치 ========================================
	//===================================== 배치 ========================================
	
	/**
	 * 콘텐의 이벤트별 통계데이터를 만든다.
	 * - 년,월,주별 콘텐츠의 이벤트별 통계
	 * @return
	 */
	public int csDailyBatch();

}