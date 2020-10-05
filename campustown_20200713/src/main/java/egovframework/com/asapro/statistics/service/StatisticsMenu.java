/**
 * 
 */
package egovframework.com.asapro.statistics.service;

import egovframework.com.asapro.site.service.MultiSiteVO;

/**
 * 사용자 일별 메뉴 접속통계 VO
 * @author yckim
 * @since 2019. 06. 27.
 *
 */
public class StatisticsMenu extends MultiSiteVO{
	
	private Integer statisYear;	//년
	private Integer statisMonth;	//월
	private Integer statisDay;	//일
	private String statisMenuId;	//메뉴 아이디
	private Integer statisMenuCnt;	//메뉴별 접속수
	
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
	 * @return the statisMenuId
	 */
	public String getStatisMenuId() {
		return statisMenuId;
	}
	/**
	 * @param statisMenuId the statisMenuId to set
	 */
	public void setStatisMenuId(String statisMenuId) {
		this.statisMenuId = statisMenuId;
	}
	/**
	 * @return the statisMenuCnt
	 */
	public Integer getStatisMenuCnt() {
		return statisMenuCnt;
	}
	/**
	 * @param statisMenuCnt the statisMenuCnt to set
	 */
	public void setStatisMenuCnt(Integer statisMenuCnt) {
		this.statisMenuCnt = statisMenuCnt;
	}

	
}
