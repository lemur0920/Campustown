/**
 * 
 */
package egovframework.com.asapro.statistics.service;

import egovframework.com.asapro.site.service.MultiSiteVO;

/**
 * 사용자 일별 OS 접속통계 VO
 * @author yckim
 * @since 2019. 06. 11.
 *
 */
public class StatisticsOs extends MultiSiteVO{
	
	private Integer statisYear;	//년
	private Integer statisMonth;	//월
	private Integer statisDay;	//일
	private String statisOs;	//OS종류
	private Integer statisOsCnt;	//OS별 접속수
	
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
	 * @return the statisOs
	 */
	public String getStatisOs() {
		return statisOs;
	}
	/**
	 * @param statisOs the statisOs to set
	 */
	public void setStatisOs(String statisOs) {
		this.statisOs = statisOs;
	}
	/**
	 * @return the statisOsCnt
	 */
	public Integer getStatisOsCnt() {
		return statisOsCnt;
	}
	/**
	 * @param statisOsCnt the statisOsCnt to set
	 */
	public void setStatisOsCnt(Integer statisOsCnt) {
		this.statisOsCnt = statisOsCnt;
	}

	
}
