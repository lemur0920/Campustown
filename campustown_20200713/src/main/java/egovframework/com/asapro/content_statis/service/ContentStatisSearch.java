/**
 * 
 */
package egovframework.com.asapro.content_statis.service;

import org.apache.commons.lang3.StringUtils;

import egovframework.com.asapro.support.pagination.PagingSearch;

/**
 * 콘텐츠별 추천통계 검색 VO
 * @author yckim
 * @since 2020. 01. 13.
 *
 */
public class ContentStatisSearch extends PagingSearch{
	
	private String csModiulCode;	//모듈 코드
	private String csModiulSubCode;	//모듈 서브코드
	private String csCateCode;	//카테고리 코드
	private String csContentId;	//콘텐츠 아이디
	private Integer csYear;	//년
	private Integer csMonth;	//월
	private Integer csDay;	//일
	private String startDate;	//시작일
	private String endDate;	//종료일

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
	 * @return the startDate
	 */
	public String getStartDate() {
		return startDate;
	}

	/**
	 * @param startDate the startDate to set
	 */
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	/**
	 * @return the endDate
	 */
	public String getEndDate() {
		return endDate;
	}

	/**
	 * @param endDate the endDate to set
	 */
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	@Override
	public String getQueryString() {
		StringBuilder sb = new StringBuilder(100);
		sb.append(super.getQueryString());
		
		if( StringUtils.isNotBlank(this.getCsModiulCode()) ){
			sb.append("&amp;csModiulCode=");
			sb.append(this.getCsModiulCode());
		}
		if( StringUtils.isNotBlank(this.getCsModiulSubCode()) ){
			sb.append("&amp;csModiulSubCode=");
			sb.append(this.getCsModiulSubCode());
		}
		if( StringUtils.isNotBlank(this.getCsCateCode()) ){
			sb.append("&amp;csCateCode=");
			sb.append(this.getCsCateCode());
		}
		if( StringUtils.isNotBlank(this.getCsContentId()) ){
			sb.append("&amp;csContentId=");
			sb.append(this.getCsContentId());
		}
		
		
		
		if( this.getCsYear() != null && this.getCsYear() > 0 ){
			sb.append("&amp;csYear=");
			sb.append(this.getCsYear());
		}
		
		if( this.getCsMonth() != null && this.getCsMonth() > 0 ){
			sb.append("&amp;csMonth=");
			sb.append(this.getCsMonth());
		}
		
		if( this.getCsDay() != null && this.getCsDay() > 0 ){
			sb.append("&amp;csDay=");
			sb.append(this.getCsDay());
		}
		
		return sb.toString();
	}
	
}
