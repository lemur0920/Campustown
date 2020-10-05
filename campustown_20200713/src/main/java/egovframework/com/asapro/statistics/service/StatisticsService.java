/**
 * 
 */
package egovframework.com.asapro.statistics.service;

import java.util.List;
import java.util.Map;

/**
 * 통계 (로그) 서비스
 * @author yckim
 * @since 2019. 01. 03.
 *
 */
public interface StatisticsService {
	
	//=========================== 접속로그 =================================
	//=========================== 접속로그 =================================
	//=========================== 접속로그 =================================
	
	/**
	 * 관리자 접속 로그를 저장한다.
	 * @param statisticsAdminAccessLog
	 * @return 저장결과
	 */
	public int insertStatisticsAdminAccessLog(StatisticsAdminAccessLog statisticsAdminAccessLog);
	
	/**
	 * 관리자 접속로그 목록을 조회한다.
	 * @param statisticsAdminAccessLogSearch
	 * @return 관리자 접속로그 목록
	 */
	public List<StatisticsAdminAccessLog> getStatisticsAdminAccessLogList(StatisticsAdminAccessLogSearch statisticsAdminAccessLogSearch);

	/**
	 * 과리자 접속로그 목록 토탈을 조회한다.
	 * @param statisticsAdminAccessLogSearch
	 * @return 관리자 접속로그 목록 토탈
	 */
	public int getStatisticsAdminAccessLogListTotal(StatisticsAdminAccessLogSearch statisticsAdminAccessLogSearch);
	
	
	//=========================== 관리자 정보 관리 로그 crud =================================
	//=========================== 관리자 정보 관리 로그 crud =================================
	//=========================== 관리자 정보 관리 로그 crud =================================
	

	/**
	 * 관리자 정보 관리 로그를 저장한다.
	 * @param statisticsMemberInquireLog
	 * @return 저장결과
	 */
	public int insertStatisticsAdminInquireLog(StatisticsMemberInquireLog statisticsMemberInquireLog);
	
	/**
	 * 관리자 정보 관리 로그 목록을 조회한다.
	 * @param statisticsMemberInquireLogSearch
	 * @return 관리자 정보 관리 로그 목록
	 */
	public List<StatisticsMemberInquireLog> getStatisticsAdminInquireLogList(StatisticsMemberInquireLogSearch statisticsMemberInquireLogSearch);

	/**
	 * 과리자 정보 관리 로그 목록 토탈을 조회한다.
	 * @param statisticsMemberInquireLogSearch
	 * @return 관리자 정보 관리 로그 목록 토탈
	 */
	public int getStatisticsAdminInquireLogListTotal(StatisticsMemberInquireLogSearch statisticsMemberInquireLogSearch);
	
	//=========================== 사용자 정보 조회 로그 crud =================================
	//=========================== 사용자 정보 조회 로그 crud =================================
	//=========================== 사용자 정보 조회 로그 crud =================================
	
	/**
	 * 사용자 정보 조회 로그를 저장한다.
	 * @param statisticsMemberInquireLog
	 * @return 저장결과
	 */
	public int insertStatisticsUserInquireLog(StatisticsMemberInquireLog statisticsMemberInquireLog);
	
	/**
	 * 사용자 정보 조회 로그 목록을 조회한다.
	 * @param statisticsMemberInquireLogSearch
	 * @return 사용자 정보 관리 로그 목록
	 */
	public List<StatisticsMemberInquireLog> getStatisticsUserInquireLogList(StatisticsMemberInquireLogSearch statisticsMemberInquireLogSearch);
	
	/**
	 * 사용자 정보 조회 로그 목록 토탈을 조회한다.
	 * @param statisticsMemberInquireLogSearch
	 * @return 사용자 정보 관리 로그 목록 토탈
	 */
	public int getStatisticsUserInquireLogListTotal(StatisticsMemberInquireLogSearch statisticsMemberInquireLogSearch);
	
	
	//=========================== 통계 =================================
	//=========================== 통계 =================================
	//=========================== 통계 =================================
	
	/**
	 * 사용자 접속 통계 임시테이블에 베이스 데이터를 등록한다.
	 * @param statisticsUserTemp
	 * @return 등록결과
	 */
	public int insertStatisUserTemp(StatisticsUserTemp statisticsUserTemp);

	/**
	 * 사용자 접속통계 기본데이터를 일자별 타이별 합산데이터로 배치한다.
	 */
	public int statisticsDailyBatch();

	/**
	 * 세션 접속통계정보를 반환한다.
	 * @param statisticsSearch
	 * @return 통계자료
	 */
	public List<StatisticsSession> getStatisticsSessionList(StatisticsSearch statisticsSearch);

	/**
	 * 월, 주, 일, 시별 세션 접속통계정보를 반환한다.
	 * @param statisticsSearch
	 * @return  접속통계정
	 */
	public List<Map<String, Object>> getStatisSessionMapByDateType(StatisticsSearch statisticsSearch);

	/**
	 * 일별 OS별 접속통계정보 map 목록을 반환한다.
	 * @param statisticsSearch
	 * @return OS접속 통계 map 목록
	 */
	public List<Map<String, Object>> getStatisOsMapList(StatisticsSearch statisticsSearch);

	/**
	 * 일별 OS별 접속통계정보 목록 반환한다.
	 * @param statisticsSearch
	 * @return OS별 접속통계정보 목록
	 */
	public List<StatisticsOs> getStatisOsList(StatisticsSearch statisticsSearch);

	/**
	 * 일별 브라우저별 접속통계정보 map 목록을 반환한다.
	 * @param statisticsSearch
	 * @return 브라우저별 접속 통계 map 목록
	 */
	public List<Map<String, Object>> getStatisBrowserMapList(StatisticsSearch statisticsSearch);

	/**
	 * 일별 국가별 접속통계정보 map 목록을 반환한다.
	 * @param statisticsSearch
	 * @return 국가별 접속 통계 map 목록
	 */
	public List<Map<String, Object>> getStatisCountryMapList(StatisticsSearch statisticsSearch);

	/**
	 * 일별 메뉴별 접속통계정보 map 목록을 반환한다.
	 * @param statisticsSearch
	 * @return 메뉴별 접속 통계 map 목록
	 */
	public List<Map<String, Object>> getStatisMenuMapList(StatisticsSearch statisticsSearch);
}