/**
 * 
 */
package egovframework.com.asapro.statistics.service;

import egovframework.com.asapro.site.service.MultiSiteVO;

/**
 * 사용자 일별 접속통계 VO
 * @author yckim
 * @since 2019. 06. 11.
 *
 */
public class StatisticsSession extends MultiSiteVO{
	
	private Integer statisYear;	//년
	private Integer statisMonth;	//월
	private Integer statisDay;	//일
	private Integer statisHour;	//시
	private Integer statisSessionCnt;	//접속수
	private String weekStart;	//해당주의 첫째일자(일요일)
	private String weekEnd;	//해당주의 마지막일자
	private String weekOfYearIso;	//년도별 주차
	private String weekOfMonth;	//월별 주차
	
	/**
	 * @return the statisYear
	 */
	public Integer getStatisYear() {
		return statisYear;
	}
	/**
	 * @param statisYear the statisYear to set
	 */
	public void setStatisYear(Integer statisYear) {
		this.statisYear = statisYear;
	}
	/**
	 * @return the statisMonth
	 */
	public Integer getStatisMonth() {
		return statisMonth;
	}
	/**
	 * @param statisMonth the statisMonth to set
	 */
	public void setStatisMonth(Integer statisMonth) {
		this.statisMonth = statisMonth;
	}
	/**
	 * @return the statisDay
	 */
	public Integer getStatisDay() {
		return statisDay;
	}
	/**
	 * @param statisDay the statisDay to set
	 */
	public void setStatisDay(Integer statisDay) {
		this.statisDay = statisDay;
	}
	/**
	 * @return the statisSessionCnt
	 */
	public Integer getStatisSessionCnt() {
		return statisSessionCnt;
	}
	/**
	 * @param statisSessionCnt the statisSessionCnt to set
	 */
	public void setStatisSessionCnt(Integer statisSessionCnt) {
		this.statisSessionCnt = statisSessionCnt;
	}
	/**
	 * @return the statisHour
	 */
	public Integer getStatisHour() {
		return statisHour;
	}
	/**
	 * @param statisHour the statisHour to set
	 */
	public void setStatisHour(Integer statisHour) {
		this.statisHour = statisHour;
	}
	/**
	 * @return the weekStart
	 */
	public String getWeekStart() {
		return weekStart;
	}
	/**
	 * @param weekStart the weekStart to set
	 */
	public void setWeekStart(String weekStart) {
		this.weekStart = weekStart;
	}
	/**
	 * @return the weekEnd
	 */
	public String getWeekEnd() {
		return weekEnd;
	}
	/**
	 * @param weekEnd the weekEnd to set
	 */
	public void setWeekEnd(String weekEnd) {
		this.weekEnd = weekEnd;
	}
	/**
	 * @return the weekOfYearIso
	 */
	public String getWeekOfYearIso() {
		return weekOfYearIso;
	}
	/**
	 * @param weekOfYearIso the weekOfYearIso to set
	 */
	public void setWeekOfYearIso(String weekOfYearIso) {
		this.weekOfYearIso = weekOfYearIso;
	}
	/**
	 * @return the weekOfMonth
	 */
	public String getWeekOfMonth() {
		return weekOfMonth;
	}
	/**
	 * @param weekOfMonth the weekOfMonth to set
	 */
	public void setWeekOfMonth(String weekOfMonth) {
		this.weekOfMonth = weekOfMonth;
	}

	
}
