/**
 * 
 */
package egovframework.com.asapro.poll.service;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;

import egovframework.com.asapro.support.pagination.PagingSearch;
import egovframework.com.asapro.support.util.AsaproUtils;

/**
 * 투표 검색 VO
 * @author yckim
 * @since 2020. 1. 21.
 *
 */
public class PollSearch extends PagingSearch{

	private String poType; //구분
	private Boolean poUse;	//개시여부 true|false
	private String poToday;	//현제날짜
	private String poStatus;	//상태검색필드
	
	
	/**
	 * @return the poType
	 */
	public String getPoType() {
		return poType;
	}

	/**
	 * @param poType the poType to set
	 */
	public void setPoType(String poType) {
		this.poType = poType;
	}

	/**
	 * @return the poUse
	 */
	public Boolean getPoUse() {
		return poUse;
	}

	/**
	 * @param poUse the poUse to set
	 */
	public void setPoUse(Boolean poUse) {
		this.poUse = poUse;
	}

	/**
	 * @return the poToday
	 */
	public String getPoToday() {
		return AsaproUtils.getFormattedDate(new Date());
	}

	/**
	 * @param poToday the poToday to set
	 */
	public void setPoToday(String poToday) {
		this.poToday = poToday;
	}

	/**
	 * @return the poStatus
	 */
	public String getPoStatus() {
		return poStatus;
	}

	/**
	 * @param poStatus the poStatus to set
	 */
	public void setPoStatus(String poStatus) {
		this.poStatus = poStatus;
	}

	/* (non-Javadoc)
	 * @see egovframework.com.asapro.support.pagination.PagingSearch#getQueryString()
	 */
	@Override
	public String getQueryString() {
		
		StringBuilder sb = new StringBuilder(100);
		sb.append(super.getQueryString());
		
		if( StringUtils.isNotBlank(this.getPoType()) ){
			sb.append("&amp;poType=");
			sb.append(this.getPoType());
		}
		
		if( this.getPoUse() != null ){
			sb.append("&amp;poUse=");
			sb.append(this.getPoUse());
		}
		
		if( StringUtils.isNotBlank(this.getPoToday()) ){
			sb.append("&amp;poToday=");
			sb.append(this.getPoToday());
		}
		
		if( StringUtils.isNotBlank(this.getPoStatus()) ){
			sb.append("&amp;poStatus=");
			sb.append(this.getPoStatus());
		}
		
		return sb.toString();
	}
	
}
