/**
 * 
 */
package egovframework.com.asapro.viewing.service;

import org.apache.commons.lang3.StringUtils;

import egovframework.com.asapro.support.pagination.PagingSearch;

/**
 * 관람 신청정보 검색 VO
 * @author yckim
 * @since 2018. 12. 14.
 *
 */
public class ViewingReservationSearch extends PagingSearch{
	
	private String reservStatus;	//상태
	private String reservStartDate;	//관람 시작
	private String reservEndDate;	//관람 종료
	
	private String reservYear;	//  검색연도-사용자단 달력
	private String reservMonth;	//검색월 -사용자단 달력
	private Integer reservWeekOfMonth;	//검색 월의 주차 -사용자단 달력
	private Integer reservWeekOfYear;	//검색 년의 주차 -사용자단 달력
	private String reservDate;	//관람날짜

	
	
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
	
	/**
	 * @return the reservYear
	 */
	public String getReservYear() {
		return reservYear;
	}
	/**
	 * @param reservYear the reservYear to set
	 */
	public void setReservYear(String reservYear) {
		this.reservYear = reservYear;
	}
	/**
	 * @return the reservMonth
	 */
	public String getReservMonth() {
		return reservMonth;
	}
	/**
	 * @param reservMonth the reservMonth to set
	 */
	public void setReservMonth(String reservMonth) {
		this.reservMonth = reservMonth;
	}

	/**
	 * @return the reservWeekOfMonth
	 */
	public Integer getReservWeekOfMonth() {
		return reservWeekOfMonth;
	}
	/**
	 * @param reservWeekOfMonth the reservWeekOfMonth to set
	 */
	public void setReservWeekOfMonth(Integer reservWeekOfMonth) {
		this.reservWeekOfMonth = reservWeekOfMonth;
	}
	/**
	 * @return the reservWeekOfYear
	 */
	public Integer getReservWeekOfYear() {
		return reservWeekOfYear;
	}
	/**
	 * @param reservWeekOfYear the reservWeekOfYear to set
	 */
	public void setReservWeekOfYear(Integer reservWeekOfYear) {
		this.reservWeekOfYear = reservWeekOfYear;
	}
	
	/**
	 * @return the reservDate
	 */
	public String getReservDate() {
		return reservDate;
	}
	/**
	 * @param reservDate the reservDate to set
	 */
	public void setReservDate(String reservDate) {
		this.reservDate = reservDate;
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
