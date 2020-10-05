/**
 * 
 */
package egovframework.com.asapro.statistics.service;

import org.apache.commons.lang3.StringUtils;

import egovframework.com.asapro.support.pagination.PagingSearch;

/**
 * 사용자 접속 통계 공통 Search VO
 * @author yckim
 * @since 2019. 06. 10.
 *
 */
public class StatisticsSearch extends PagingSearch{
	
	private Integer statisYear;	//년
	private Integer statisMonth;	//월
	private Integer statisDay;	//일
	private String statisType;	//session, os, browser, country
	private String statisStartDate;	//통계대상 시작일
	private String statisEndDate;	//통계대상 종료일
	private String statisDateType;	//월주일시 통계타입(month, week, day, hour)
	private Boolean statisIsMenu = false;	//통계 타입이 메뉴여부 구분
		
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
	 * @return the statisType
	 */
	public String getStatisType() {
		return statisType;
	}

	/**
	 * @param statisType the statisType to set
	 */
	public void setStatisType(String statisType) {
		this.statisType = statisType;
	}

	/**
	 * @return the statisStartDate
	 */
	public String getStatisStartDate() {
		return statisStartDate;
	}

	/**
	 * @param statisStartDate the statisStartDate to set
	 */
	public void setStatisStartDate(String statisStartDate) {
		this.statisStartDate = statisStartDate;
	}

	/**
	 * @return the statisEndDate
	 */
	public String getStatisEndDate() {
		return statisEndDate;
	}

	/**
	 * @param statisEndDate the statisEndDate to set
	 */
	public void setStatisEndDate(String statisEndDate) {
		this.statisEndDate = statisEndDate;
	}

	/**
	 * @return the statisDateType
	 */
	public String getStatisDateType() {
		return statisDateType;
	}

	/**
	 * @param statisDateType the statisDateType to set
	 */
	public void setStatisDateType(String statisDateType) {
		this.statisDateType = statisDateType;
	}

	/**
	 * @return the statisIsMenu
	 */
	public Boolean getStatisIsMenu() {
		return statisIsMenu;
	}

	/**
	 * @param statisIsMenu the statisIsMenu to set
	 */
	public void setStatisIsMenu(Boolean statisIsMenu) {
		this.statisIsMenu = statisIsMenu;
	}

	@Override
	public String getQueryString() {
		StringBuilder sb = new StringBuilder(100);
		sb.append(super.getQueryString());
		
		if( this.getStatisYear() != null && this.getStatisYear() > 0 ){
			sb.append("&amp;statisYear=");
			sb.append(this.getStatisYear());
		}
		
		if( this.getStatisMonth() != null && this.getStatisMonth() > 0 ){
			sb.append("&amp;statisMonth=");
			sb.append(this.getStatisMonth());
		}
		
		if( this.getStatisDay() != null && this.getStatisDay() > 0 ){
			sb.append("&amp;statisDay=");
			sb.append(this.getStatisDay());
		}
		
		if( StringUtils.isNotBlank(this.getStatisType()) ){
			sb.append("&amp;statisType=");
			sb.append(this.getStatisType());
		}
		
		if( StringUtils.isNotBlank(this.getStatisStartDate()) ){
			sb.append("&amp;statisStartDate=");
			sb.append(this.getStatisStartDate());
		}
		
		if( StringUtils.isNotBlank(this.getStatisEndDate()) ){
			sb.append("&amp;statisEndDate=");
			sb.append(this.getStatisEndDate());
		}
		
		if( StringUtils.isNotBlank(this.getStatisDateType()) ){
			sb.append("&amp;statisDateType=");
			sb.append(this.getStatisDateType());
		}
		if( this.getStatisIsMenu() != null ){
			sb.append("&amp;statisMonth=");
			sb.append(this.getStatisIsMenu());
		}
		return sb.toString();
	}
	
}
