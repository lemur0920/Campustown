/**
 * 
 */
package egovframework.com.asapro.organization.service;

import org.apache.commons.lang3.StringUtils;

import egovframework.com.asapro.support.pagination.PagingSearch;

/**
 * 기관 검색 
 * 
 * @author yckim
 * @since 2018. 5. 11.
 */
public class OrganizationSearch extends PagingSearch{

	private Boolean orgUse;
	
	/**
	 * @return the orgUse
	 */
	public Boolean getOrgUse() {
		return orgUse;
	}

	/**
	 * @param orgUse the orgUse to set
	 */
	public void setOrgUse(Boolean orgUse) {
		this.orgUse = orgUse;
	}



	@Override
	public String getQueryString() {
		
		StringBuilder sb = new StringBuilder(100);
		sb.append(super.getQueryString());
		
		if( this.getOrgUse() != null ){
			sb.append("&amp;orgUse=");
			sb.append(this.getOrgUse());
		}
		
		return sb.toString();
	}
}
