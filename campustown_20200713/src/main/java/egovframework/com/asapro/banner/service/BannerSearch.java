/**
 * 
 */
package egovframework.com.asapro.banner.service;

import org.apache.commons.lang3.StringUtils;

import egovframework.com.asapro.support.pagination.PagingSearch;

/**
 * 배너검색
 * @author yckim
 * @since 2018. 7. 26.
 *
 */
public class BannerSearch extends PagingSearch{

	private String bnType = "mainVisual";	//유형
	private Boolean bnUse;	//true|false
	private String bnToday;	//
	/**
	 * @return the bnType
	 */
	public String getBnType() {
		return bnType;
	}
	/**
	 * @param bnType the bnType to set
	 */
	public void setBnType(String bnType) {
		this.bnType = bnType;
	}
	
	/**
	 * @return the bnUse
	 */
	public Boolean getBnUse() {
		return bnUse;
	}
	/**
	 * @param bnUse the bnUse to set
	 */
	public void setBnUse(Boolean bnUse) {
		this.bnUse = bnUse;
	}
	/**
	 * @return the bnToday
	 */
	public String getBnToday() {
		return bnToday;
	}
	/**
	 * @param bnToday the bnToday to set
	 */
	public void setBnToday(String bnToday) {
		this.bnToday = bnToday;
	}
	/* (non-Javadoc)
	 * @see egovframework.com.asapro.support.pagination.PagingSearch#getQueryString()
	 */
	@Override
	public String getQueryString() {
		
		StringBuilder sb = new StringBuilder(100);
		sb.append(super.getQueryString());
		
		if( StringUtils.isNotBlank(this.getBnType()) ){
			sb.append("&amp;bnType=");
			sb.append(this.getBnType());
		}
		
		if( this.getBnUse() != null ){
			sb.append("&amp;bnUse=");
			sb.append(this.getBnUse());
		}
		
		return sb.toString();
	}
	
}
