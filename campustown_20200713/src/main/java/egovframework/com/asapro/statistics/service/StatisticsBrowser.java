/**
 * 
 */
package egovframework.com.asapro.statistics.service;

import egovframework.com.asapro.site.service.MultiSiteVO;

/**
 * 사용자 일별 브라우저 접속통계 VO
 * @author yckim
 * @since 2019. 06. 12.
 *
 */
public class StatisticsBrowser extends MultiSiteVO{
	
	private Integer statisYear;	//년
	private Integer statisMonth;	//월
	private Integer statisDay;	//일
	private String statisBrowser;	//브라우저 종류
	private Integer statisBrowserCnt;	//브라우저별 접속수
	
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
	 * @return the statisBrowser
	 */
	public String getStatisBrowser() {
		return statisBrowser;
	}
	/**
	 * @param statisBrowser the statisBrowser to set
	 */
	public void setStatisBrowser(String statisBrowser) {
		this.statisBrowser = statisBrowser;
	}
	/**
	 * @return the statisBrowserCnt
	 */
	public Integer getStatisBrowserCnt() {
		return statisBrowserCnt;
	}
	/**
	 * @param statisBrowserCnt the statisBrowserCnt to set
	 */
	public void setStatisBrowserCnt(Integer statisBrowserCnt) {
		this.statisBrowserCnt = statisBrowserCnt;
	}

	
}
