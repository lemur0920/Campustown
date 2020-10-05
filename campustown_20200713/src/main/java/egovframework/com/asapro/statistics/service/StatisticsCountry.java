/**
 * 
 */
package egovframework.com.asapro.statistics.service;

import egovframework.com.asapro.site.service.MultiSiteVO;

/**
 * 사용자 일별 국가 접속통계 VO
 * @author yckim
 * @since 2019. 06. 12.
 *
 */
public class StatisticsCountry extends MultiSiteVO{
	
	private Integer statisYear;	//년
	private Integer statisMonth;	//월
	private Integer statisDay;	//일
	private String statisCountryKor;	//국가명 - 한글
	private String statisCountryEng;	//국가명 - 영문
	private Integer statisCountryCnt;	//국가별 접속수
	
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
	 * @return the statisCountryKor
	 */
	public String getStatisCountryKor() {
		return statisCountryKor;
	}
	/**
	 * @param statisCountryKor the statisCountryKor to set
	 */
	public void setStatisCountryKor(String statisCountryKor) {
		this.statisCountryKor = statisCountryKor;
	}
	/**
	 * @return the statisCountryEng
	 */
	public String getStatisCountryEng() {
		return statisCountryEng;
	}
	/**
	 * @param statisCountryEng the statisCountryEng to set
	 */
	public void setStatisCountryEng(String statisCountryEng) {
		this.statisCountryEng = statisCountryEng;
	}
	/**
	 * @return the statisCountryCnt
	 */
	public Integer getStatisCountryCnt() {
		return statisCountryCnt;
	}
	/**
	 * @param statisCountryCnt the statisCountryCnt to set
	 */
	public void setStatisCountryCnt(Integer statisCountryCnt) {
		this.statisCountryCnt = statisCountryCnt;
	}

	
}
