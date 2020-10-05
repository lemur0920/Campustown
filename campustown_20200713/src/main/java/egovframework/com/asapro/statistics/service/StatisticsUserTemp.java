/**
 * 
 */
package egovframework.com.asapro.statistics.service;

import egovframework.com.asapro.site.service.MultiSiteVO;

/**
 * 사용자 접속 통계 등록 Temp VO
 * @author yckim
 * @since 2019. 06. 07.
 *
 */
public class StatisticsUserTemp extends MultiSiteVO{
	
	private Integer statisYear;	//년
	private Integer statisMonth;	//월
	private Integer statisDay;	//일
	private Integer statisHour;	//시간
	private String statisOs = "";	//os
	private String statisBrowser = "";	//브라우저
	private String statisIp = "";	//접속아이피
	private String statisCountry = "";	//접속지 국가명
	private String statisIsoCode = "";	//접속지 ISO코드
	private String statisMenuId = "";	//메뉴 아이디
	private boolean statisIsMenu = false;	//통계 타입이 메뉴여부 구분
	
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
	 * @return the statisIp
	 */
	public String getStatisIp() {
		return statisIp;
	}
	/**
	 * @param statisIp the statisIp to set
	 */
	public void setStatisIp(String statisIp) {
		this.statisIp = statisIp;
	}
	/**
	 * @return the statisCountry
	 */
	public String getStatisCountry() {
		return statisCountry;
	}
	/**
	 * @param statisCountry the statisCountry to set
	 */
	public void setStatisCountry(String statisCountry) {
		this.statisCountry = statisCountry;
	}
	/**
	 * @return the statisIsoCode
	 */
	public String getStatisIsoCode() {
		return statisIsoCode;
	}
	/**
	 * @param statisIsoCode the statisIsoCode to set
	 */
	public void setStatisIsoCode(String statisIsoCode) {
		this.statisIsoCode = statisIsoCode;
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
	 * @return the statisIsMenu
	 */
	public boolean isStatisIsMenu() {
		return statisIsMenu;
	}
	/**
	 * @param statisIsMenu the statisIsMenu to set
	 */
	public void setStatisIsMenu(boolean statisIsMenu) {
		this.statisIsMenu = statisIsMenu;
	}

	
}
