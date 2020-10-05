package egovframework.com.asapro.openapiinfo.api.service;

import org.apache.commons.lang3.StringUtils;

import egovframework.com.asapro.support.pagination.PagingSearch;

public class YouthActSupInfoSearch extends PagingSearch {

	private String startDate;	//검색시작일
	private String endDate;	//검색종료일
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
		
		if( StringUtils.isNotBlank(this.getStartDate()) ){
			sb.append("&amp;startDate=");
			sb.append(this.getStartDate());
		}
		if( StringUtils.isNotBlank(this.getEndDate()) ){
			sb.append("&amp;endDate=");
			sb.append(this.getEndDate());
		}
		
		return sb.toString();
	}
	
}
