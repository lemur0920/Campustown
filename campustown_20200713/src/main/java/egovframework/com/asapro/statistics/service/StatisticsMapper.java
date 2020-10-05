/**
 * 
 */
package egovframework.com.asapro.statistics.service;

import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * 통계 (로그) SQL 매퍼
 * @author yckim
 * @since 2019. 01. 02.
 *
 */
@Mapper
public interface StatisticsMapper {
	
	//=========================== 접속로그 =================================
	//=========================== 접속로그 =================================
	//=========================== 접속로그 =================================

	/**
	 * 관리자 접속 로그를 입력한다.
	 * @param statisticsAdminAccessLog
	 * @return
	 */
	public int insertStatisticsAdminAccessLog(StatisticsAdminAccessLog statisticsAdminAccessLog);
	
	/**
	 * 관리자 접속로그 목록을 조회한다.
	 * @param statisticsAdminAccessLogSearch
	 * @return
	 */
	public List<StatisticsAdminAccessLog> selectStatisticsAdminAccessLogList(StatisticsAdminAccessLogSearch statisticsAdminAccessLogSearch);
	
	/**
	 * 관리자 접속로그 토탈 개수를 조회한다.
	 * @param statisticsAdminAccessLogSearch
	 * @return
	 */
	public int selectStatisticsAdminAccessLogListTotal(StatisticsAdminAccessLogSearch statisticsAdminAccessLogSearch);

	
	//=========================== 관리자 정보 관리 로그 crud =================================
	//=========================== 관리자 정보 관리 로그 crud =================================
	//=========================== 관리자 정보 관리 로그 crud =================================
	/**
	 * 관리자 정보 관리 로그를 저장한다.
	 * @param statisticsMemberInquireLog
	 * @return
	 */
	public int insertStatisticsAdminInquireLog(StatisticsMemberInquireLog statisticsMemberInquireLog);

	/**
	 * 관리자 정보 관리 로그 목록을 조회한다.
	 * @param statisticsMemberInquireLogSearch
	 * @return
	 */
	public List<StatisticsMemberInquireLog> selectStatisticsAdminInquireLogList(
			StatisticsMemberInquireLogSearch statisticsMemberInquireLogSearch);

	/**
	 * 관리자 정보 관리 로그 목록 토탈을 조회한다.
	 * @param statisticsMemberInquireLogSearch
	 */
	public int selectStatisticsAdminInquireLogListTotal(
			StatisticsMemberInquireLogSearch statisticsMemberInquireLogSearch);

	
	//=========================== 사용자 정보 조회 로그 crud =================================
	//=========================== 사용자 정보 조회 로그 crud =================================
	//=========================== 사용자 정보 조회 로그 crud =================================
	/**
	 * 사용자 정보 조회 로그를 저장한다.
	 * @param statisticsMemberInquireLog
	 * @return
	 */
	public int insertStatisticsUserInquireLog(StatisticsMemberInquireLog statisticsMemberInquireLog);
	
	/**
	 * 사용자 정보 조회 로그 목록을 조회한다.
	 * @param statisticsMemberInquireLogSearch
	 * @return
	 */
	public List<StatisticsMemberInquireLog> selectStatisticsUserInquireLogList(
			StatisticsMemberInquireLogSearch statisticsMemberInquireLogSearch);
	
	/**
	 * 사용자 정보 조회 로그 목록 토탈을 조회한다.
	 * @param statisticsMemberInquireLogSearch
	 */
	public int selectStatisticsUserInquireLogListTotal(
			StatisticsMemberInquireLogSearch statisticsMemberInquireLogSearch);
	
	
	//=========================== 통계 =================================
	//=========================== 통계 =================================
	//=========================== 통계 =================================
	
	/**
	 * 사용자 접속 통계 임시테이블에 베이스 데이터를 등록한다.
	 * @param statisticsUserTemp
	 * @return 등록결과
	 */
	public int insertStatisticsUserTemp(StatisticsUserTemp statisticsUserTemp);

	/**
	 * 사용자 접속 통계 임시테이블에 베이스 데이터를 조회한다.
	 * @param statisticsSearch
	 * @return 베이스데이터 목록
	 */
	public List<StatisticsUserTemp> selectStatisticsUserTempList(StatisticsSearch statisticsSearch);

	/**
	 * 사용자 접속 통계 임시테이블에 베이스 데이터를 수량을 조회한다.
	 * @param statisticsSearch
	 * @return 토탈수량
	 */
	public int selectStatisticsUserTempListTotal(StatisticsSearch statisticsSearch);

	/**
	 * 사용자 접속 통계 임시테이블에 베이스 데이터를 삭제한다.
	 * @param statisticsSearch
	 */
	public int deleteStatisticsUserTemp(StatisticsSearch statisticsSearch);
	
	//==================== 일별 ===========================
	/**
	 * 일별 접속통계 정보를 반환한다.
	 * @param statisticsSession
	 * @return 일별 접속통계
	 */
	public StatisticsSession selectStatisSession(StatisticsSession statisticsSessionSearch);

	/**
	 * 시간별 접속 수를 반환한다.
	 * @param statisticsSearch
	 * @return 접속 수
	 */
	public List<Map<String, Object>> selectStatisticsUserTempCntByHour(StatisticsSearch statisticsSearch);
	
	/**
	 * 일별 접속통계 접속수를 수정한다.
	 * @param statisticsSessionSearch
	 * @return 수정결과
	 */
	public int updateStatisSession(StatisticsSession statisticsSessionSearch);

	/**
	 * 일별 접속통계 데이터를 추가한다.
	 * @param statisticsSessionSearch
	 * @return 추가결과
	 */
	public int insertStatisSession(StatisticsSession statisticsSessionSearch);

	/**
	 * 접속통계정보 목록을 반환한다.
	 * @param statisticsSessionSearch
	 * @return 접속통계정보 목록
	 */
	public List<StatisticsSession> selectStatisSessionList(StatisticsSearch statisticsSearch);
	
	/**
	 * 시간별 세션 접속통계정보를 반환한다.
	 * @param statisticsSearch
	 * @return 시간별 세션 접속통계정보
	 */
	public List<Map<String, Object>> selectStatisSessionMapByDateTypeHour(StatisticsSearch statisticsSearch);
	
	/**
	 * 일별 세션 접속통계정보를 반환한다.
	 * @param statisticsSessionSearch
	 * @return 일별 세션 접속통계정보
	 */
	public List<Map<String, Object>> selectStatisSessionMapByDateTypeDay(StatisticsSearch statisticsSearch);
	
	/**
	 * 주별 세션 접속통계정보를 반환한다.
	 * @param statisticsSessionSearch
	 * @return 주별 세션 접속통계정보
	 */
	public List<Map<String, Object>> selectStatisSessionMapByDateTypeWeek(StatisticsSearch statisticsSearch);
	
	/**
	 * 월별 세션 접속통계정보를 반환한다.
	 * @param statisticsSessionSearch
	 * @return 월별 세션 접속통계정보
	 */
	public List<Map<String, Object>> selectStatisSessionMapByDateTypeMonth(StatisticsSearch statisticsSearch);
	
	
	//==================== OS별 ===========================
	
	/**
	 * OS별 접속 수를 반환한다.
	 * @param statisticsSearch
	 * @return 접속 수
	 */
	public List<Map<String, Object>> selectStatisticsUserTempCntByOs(StatisticsSearch statisticsSearch);
	
	/**
	 * OS별 접속통계 데이터를 반환한다.
	 * @param statisticsOsSearch
	 * @return OS별 접속통계
	 */
	public StatisticsOs selectStatisOs(StatisticsOs statisticsOsSearch);

	/**
	 * OS별 접속통계 접속수를 수정한다.
	 * @param statisticsOsSearch
	 * @return 수정결과
	 */
	public int updateStatisOs(StatisticsOs statisticsOsSearch);

	/**
	 * OS별 접속통계 데이터를 추가한다.
	 * @param statisticsOsSearch
	 * @return 추가결과
	 */
	public int insertStatisOs(StatisticsOs statisticsOsSearch);

	/**
	 * 일별 OS별 접속통계정보 map 목록을 반환한다.
	 * @param statisticsSearch
	 * @return OS접속 통계 map 목록
	 */
	public List<Map<String, Object>> selectStatisOsMapList(StatisticsSearch statisticsSearch);
	
	/**
	 * 일별 OS별 접속통계정보 목록 반환한다.
	 * @param statisticsSearch
	 * @return OS별 접속통계정보 목록
	 */
	public List<StatisticsOs> selectStatisOsList(StatisticsSearch statisticsSearch);


	//==================== 브라우저별 ===========================
	
	/**
	 * 브라우저별 접속 수를 반환한다.
	 * @param statisticsSearch
	 * @return 접속 수
	 */
	public List<Map<String, Object>> selectStatisticsUserTempCntByBrowser(StatisticsSearch statisticsSearch);
	
	/**
	 * 브라우저별 접속통계 데이터를 반환한다.
	 * @param statisticsBrowserSearch
	 * @return 브라우저별 접속통계
	 */
	public StatisticsBrowser selectStatisBrowser(StatisticsBrowser statisticsBrowserSearch);

	/**
	 * 브라우저별 접속통계 접속수를 수정한다.
	 * @param statisticsBrowserSearch
	 * @return 수정결과
	 */
	public int updateStatisBrowser(StatisticsBrowser statisticsBrowserSearch);

	/**
	 * 브라우저별 접속통계 데이터를 추가한다.
	 * @param statisticsBrowserSearch
	 * @return 추가결과
	 */
	public int insertStatisBrowser(StatisticsBrowser statisticsBrowserSearch);

	/**
	 * 일별 브라우저별 접속통계정보 map 목록을 반환한다.
	 * @param statisticsSearch
	 * @return 브라우저별 접속 통계 map 목록
	 */
	public List<Map<String, Object>> selectStatisBrowserMapList(StatisticsSearch statisticsSearch);

	
	//==================== 국가별 ===========================
	
	/**
	 * 국가별 접속 수를 반환한다.
	 * @param statisticsSearch
	 * @return 접속 수
	 */
	public List<Map<String, Object>> selectStatisticsUserTempCntByCountry(StatisticsSearch statisticsSearch);
	
	/**
	 * 국가별 접속통계 데이터를 반환한다.
	 * @param statisticsCountrySearch
	 * @return 국가별 접속통계
	 */
	public StatisticsCountry selectStatisCountry(StatisticsCountry statisticsCountrySearch);

	/**
	 * 국가별 접속통계 접속수를 수정한다.
	 * @param statisticsCountrySearch
	 * @return 수정결과
	 */
	public int updateStatisCountry(StatisticsCountry statisticsCountrySearch);

	/**
	 * 국가별 접속통계 데이터를 추가한다.
	 * @param statisticsCountrySearch
	 * @return 추가결과
	 */
	public int insertStatisCountry(StatisticsCountry statisticsCountrySearch);

	/**
	 * 일별 국가별 접속통계정보 map 목록을 반환한다.
	 * @param statisticsSearch
	 * @return 국가별 접속 통계 map 목록
	 */
	public List<Map<String, Object>> selectStatisCountryMapList(StatisticsSearch statisticsSearch);

	
	//==================== 메뉴별 ===========================
	
	/**
	 * 메뉴별 접속 수를 반환한다.
	 * @param statisticsSearch
	 * @return 접속 수
	 */
	public List<Map<String, Object>> selectStatisticsUserTempCntByMenu(StatisticsSearch statisticsSearch);

	/**
	 * 메뉴별 접속통계 데이터를 반환한다.
	 * @param statisticsMenuSearch
	 * @return 메뉴별 접속통계
	 */
	public StatisticsMenu selectStatisMenu(StatisticsMenu statisticsMenuSearch);

	/**
	 * 메뉴별 접속통계 접속수를 수정한다.
	 * @param statisticsMenuSearch
	 * @return 수정결과
	 */
	public int updateStatisMenu(StatisticsMenu statisticsMenuSearch);

	/**
	 * 메뉴별 접속통계 데이터를 추가한다.
	 * @param statisticsMenuSearch
	 * @return 추가결과
	 */
	public int insertStatisMenu(StatisticsMenu statisticsMenuSearch);

	/**
	 * 일별 메뉴별 접속통계정보 map 목록을 반환한다.
	 * @param statisticsSearch
	 * @return 메뉴별 접속 통계 map 목록
	 */
	public List<Map<String, Object>> selectStatisMenuMapList(StatisticsSearch statisticsSearch);

	
	
	
}