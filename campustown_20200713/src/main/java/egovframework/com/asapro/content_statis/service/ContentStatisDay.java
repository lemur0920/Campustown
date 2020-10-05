/**
 * 
 */
package egovframework.com.asapro.content_statis.service;

import egovframework.com.asapro.site.service.MultiSiteVO;

/**
 * 콘텐츠별 일별 추천통계 VO
 * @author yckim
 * @since 2020. 01. 13.
 *
 */
public class ContentStatisDay extends MultiSiteVO{
	
	private String csModiulCode;	//모듈 코드
	private String csModiulSubCode;	//모듈 서브코드
	private String csCateCode;	//카테고리 코드
	private String csContentId;	//콘텐츠 아이디
	private Integer csYear;	//년
	private Integer csMonth;	//월
	private Integer csDay;	//일
	private Integer csCount;	//추천 수
	private String csWeekStart;	//해당주의 첫째일자(일요일)
	private String csWeekEnd;	//해당주의 마지막일자(토요일)
	private String weekOfYearIso;	//년도별 주차
	private String weekOfMonth;	//월별 주차
	/**
	 * @return the csModiulCode
	 */
	public String getCsModiulCode() {
		return csModiulCode;
	}
	/**
	 * @param csModiulCode the csModiulCode to set
	 */
	public void setCsModiulCode(String csModiulCode) {
		this.csModiulCode = csModiulCode;
	}
	/**
	 * @return the csModiulSubCode
	 */
	public String getCsModiulSubCode() {
		return csModiulSubCode;
	}
	/**
	 * @param csModiulSubCode the csModiulSubCode to set
	 */
	public void setCsModiulSubCode(String csModiulSubCode) {
		this.csModiulSubCode = csModiulSubCode;
	}
	/**
	 * @return the csCateCode
	 */
	public String getCsCateCode() {
		return csCateCode;
	}
	/**
	 * @param csCateCode the csCateCode to set
	 */
	public void setCsCateCode(String csCateCode) {
		this.csCateCode = csCateCode;
	}
	/**
	 * @return the csContentId
	 */
	public String getCsContentId() {
		return csContentId;
	}
	/**
	 * @param csContentId the csContentId to set
	 */
	public void setCsContentId(String csContentId) {
		this.csContentId = csContentId;
	}
	/**
	 * @return the csYear
	 */
	public Integer getCsYear() {
		return csYear;
	}
	/**
	 * @param csYear the csYear to set
	 */
	public void setCsYear(Integer csYear) {
		this.csYear = csYear;
	}
	/**
	 * @return the csMonth
	 */
	public Integer getCsMonth() {
		return csMonth;
	}
	/**
	 * @param csMonth the csMonth to set
	 */
	public void setCsMonth(Integer csMonth) {
		this.csMonth = csMonth;
	}
	/**
	 * @return the csDay
	 */
	public Integer getCsDay() {
		return csDay;
	}
	/**
	 * @param csDay the csDay to set
	 */
	public void setCsDay(Integer csDay) {
		this.csDay = csDay;
	}
	/**
	 * @return the csCount
	 */
	public Integer getCsCount() {
		return csCount;
	}
	/**
	 * @param csCount the csCount to set
	 */
	public void setCsCount(Integer csCount) {
		this.csCount = csCount;
	}
	/**
	 * @return the csWeekStart
	 */
	public String getCsWeekStart() {
		return csWeekStart;
	}
	/**
	 * @param csWeekStart the csWeekStart to set
	 */
	public void setCsWeekStart(String csWeekStart) {
		this.csWeekStart = csWeekStart;
	}
	/**
	 * @return the csWeekEnd
	 */
	public String getCsWeekEnd() {
		return csWeekEnd;
	}
	/**
	 * @param csWeekEnd the csWeekEnd to set
	 */
	public void setCsWeekEnd(String csWeekEnd) {
		this.csWeekEnd = csWeekEnd;
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
