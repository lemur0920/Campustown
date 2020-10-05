/**
 * 
 */
package egovframework.com.asapro.archive.service;

import org.apache.commons.lang3.StringUtils;

import egovframework.com.asapro.support.pagination.PagingSearch;

/**
 * 아카이브 카테고리 검색 VO
 * @author yckim
 * @since 2018. 12. 19.
 *
 */
public class ArchiveCategorySearch extends PagingSearch{
	
	private String reservStatus;	//상태
	private String reservStartDate;	//관람 시작
	private String reservEndDate;	//관람 종료

	/**
	 * @return the reservStatus
	 */
	public String getReservStatus() {
		return reservStatus;
	}
	/**
	 * @param reservStatus the reservStatus to set
	 */
	public void setReservStatus(String reservStatus) {
		this.reservStatus = reservStatus;
	}
	/**
	 * @return the reservStartDate
	 */
	public String getReservStartDate() {
		return reservStartDate;
	}
	/**
	 * @param reservStartDate the reservStartDate to set
	 */
	public void setReservStartDate(String reservStartDate) {
		this.reservStartDate = reservStartDate;
	}
	/**
	 * @return the reservEndDate
	 */
	public String getReservEndDate() {
		return reservEndDate;
	}
	/**
	 * @param reservEndDate the reservEndDate to set
	 */
	public void setReservEndDate(String reservEndDate) {
		this.reservEndDate = reservEndDate;
	}
	
	/* (non-Javadoc)
	 * @see egovframework.com.asapro.support.pagination.PagingSearch#getQueryString()
	 */
	@Override
	public String getQueryString() {
		
		StringBuilder sb = new StringBuilder(100);
		sb.append(super.getQueryString());
		
		if( StringUtils.isNotBlank(this.getReservStatus()) ){
			sb.append("&amp;reservStatus=");
			sb.append(this.getReservStatus());
		}
		
		if( StringUtils.isNotBlank(this.getReservStartDate()) ){
			sb.append("&amp;reservStartDate=");
			sb.append(this.getReservStartDate());
		}
		
		if( StringUtils.isNotBlank(this.getReservEndDate()) ){
			sb.append("&amp;reservEndDate=");
			sb.append(this.getReservEndDate());
		}
		
		return sb.toString();
	}
}
