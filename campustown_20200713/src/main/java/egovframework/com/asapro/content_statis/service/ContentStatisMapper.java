/**
 * 
 */
package egovframework.com.asapro.content_statis.service;

import java.util.List;
import java.util.Map;

import egovframework.com.asapro.board.service.BoardArticle;
import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * 콘텐츠별 추천통계  SQL 매퍼
 * @author yckim
 * @since 2020. 01. 13.
 *
 */
@Mapper
public interface ContentStatisMapper {
	
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
	 * 콘텐츠의 추천 통계 데이터를 조회한다.
	 * @param contentStatisDay
	 * @return 조회결과
	 */
	public ContentStatisDay selectContentStatisDay(ContentStatisDay contentStatisDay);

	
	//===================================== 베스트 콘텐츠 목록 ========================================
	//===================================== 베스트 콘텐츠 목록 ========================================
	//===================================== 베스트 콘텐츠 목록 ========================================
	
	/**
	 * 게시판 월간 베스트글 목록을 반환한다.
	 * @param contentStatisSearch
	 * @return 게시판 글 목록
	 */
	public List<BoardArticle> selectBoardBestInMonthList(ContentStatisSearch contentStatisSearch);
	
	/**
	 * 월통계대상 데이터의 년도 목록맵을 반환한다.
	 * @param contentStatisSearch
	 * @return 년도 목록 맵
	 */
	public List<Map<String, Object>> selectYearListBy30(ContentStatisSearch contentStatisSearch);

	/**
	 * 월통계대상 데이터의 월 목록맵을 반환한다.
	 * @param contentStatisSearch
	 * @return 월 목록 맵
	 */
	public List<Map<String, Object>> selectMonthListBy30(ContentStatisSearch contentStatisSearch);

	//===================================== 배치 ========================================
	//===================================== 배치 ========================================
	//===================================== 배치 ========================================
	
	/**
	 * 해당월 콘텐츠별 이벤트수합 맵을 반환한다.
	 * @param contentStatisSearch
	 * @return 콘텐츠별 이벤트수합 맵
	 */
	public List<Map<String, Object>> selectCntContentMonth(ContentStatisSearch contentStatisSearch);
	
	/**
	 * 해당월 콘텐츠별 모듈목록 맵을 반환한다.
	 * @param contentStatisSearch
	 * @return 모듈목록 맵
	 */
	public List<Map<String, Object>> selectModuleMonth(ContentStatisSearch contentStatisSearch);
	
	/**
	 * 해당월 콘텐츠별 서브모듈목록 맵을 반환한다.
	 * @param contentStatisSearch
	 * @return 서브모듈목록 맵
	 */
	public List<Map<String, Object>> selectSubModuleMonth(ContentStatisSearch contentStatisSearch);
	
	/**
	 * 월별 콘텐츠 통계데이터를 추가한다.
	 * @param contentStatis30
	 * @return 추가결과
	 */
	public int insertContentStatis30(ContentStatis30 contentStatis30);

	/**
	 * 월별 콘텐츠 통계데이터를 삭제한다.
	 * @param contentStatis30
	 * @return 삭제결과
	 */
	public int deleteContentStatis30(ContentStatis30 contentStatis30);

	
	
}