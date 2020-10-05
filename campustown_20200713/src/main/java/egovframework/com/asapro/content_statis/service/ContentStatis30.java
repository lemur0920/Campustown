/**
 * 
 */
package egovframework.com.asapro.content_statis.service;

import egovframework.com.asapro.site.service.MultiSiteVO;

/**
 * 콘텐츠별 최근30일 추천통계 VO
 * @author yckim
 * @since 2020. 01. 13.
 *
 */
public class ContentStatis30 extends MultiSiteVO{
	
	private String csModiulCode;	//모듈 코드
	private String csModiulSubCode;	//모듈 서브코드
	private String csCateCode;	//카테고리 코드
	private String csContentId;	//콘텐츠 아이디
	private Integer csYear;	//년
	private Integer csMonth;	//월
	private Integer csDay;	//일
	private Integer csCount;	//추천 수
	private String csStartDay;	//30일 시작일
	private String csEndDay;	//30일 종료일
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
	 * @return the csStartDay
	 */
	public String getCsStartDay() {
		return csStartDay;
	}
	/**
	 * @param csStartDay the csStartDay to set
	 */
	public void setCsStartDay(String csStartDay) {
		this.csStartDay = csStartDay;
	}
	/**
	 * @return the csEndDay
	 */
	public String getCsEndDay() {
		return csEndDay;
	}
	/**
	 * @param csEndDay the csEndDay to set
	 */
	public void setCsEndDay(String csEndDay) {
		this.csEndDay = csEndDay;
	}

	
}
