/**
 * 
 */
package egovframework.com.asapro.statistics.service;

import org.apache.commons.lang3.StringUtils;

import egovframework.com.asapro.support.pagination.PagingSearch;

/**
 * 관리자 정보 관리 로그 검색 모델
 * @author yckim
 * @since 2019. 07. 10.
 *
 */
public class StatisticsMemberInquireLogSearch extends PagingSearch{
	
	private String inqStartDate;	//검색일 시작
	private String inqEndDate;	//검색일 종료
	
	/**
	 * @return the inqStartDate
	 */
	public String getInqStartDate() {
		return inqStartDate;
	}

	/**
	 * @param inqStartDate the inqStartDate to set
	 */
	public void setInqStartDate(String inqStartDate) {
		this.inqStartDate = inqStartDate;
	}

	/**
	 * @return the inqEndDate
	 */
	public String getInqEndDate() {
		return inqEndDate;
	}

	/**
	 * @param inqEndDate the inqEndDate to set
	 */
	public void setInqEndDate(String inqEndDate) {
		this.inqEndDate = inqEndDate;
	}

	@Override
	public String getQueryString() {
		
		StringBuilder sb = new StringBuilder(100);
		sb.append(super.getQueryString());
		
		if( StringUtils.isNotBlank(this.getInqStartDate()) ){
			sb.append("&amp;inqStartDate=");
			sb.append(this.getInqStartDate());
		}
		
		if( StringUtils.isNotBlank(this.getInqEndDate()) ){
			sb.append("&amp;inqEndDate=");
			sb.append(this.getInqEndDate());
		}
		
		return sb.toString();
	}
	
}
