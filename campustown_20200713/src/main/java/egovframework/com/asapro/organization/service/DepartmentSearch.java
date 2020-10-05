/**
 * 
 */
package egovframework.com.asapro.organization.service;

import org.apache.commons.lang3.StringUtils;

import egovframework.com.asapro.support.pagination.PagingSearch;

/**
 * 부서 검색 
 * 
 * @author yckim
 * @since 2018. 5. 11.
 */
public class DepartmentSearch extends PagingSearch{

	private Boolean depUse;
	private String orgId;
	/**
	 * @return the depUse
	 */
	public Boolean getDepUse() {
		return depUse;
	}
	/**
	 * @param depUse the depUse to set
	 */
	public void setDepUse(Boolean depUse) {
		this.depUse = depUse;
	}
	/**
	 * @return the orgId
	 */
	public String getOrgId() {
		return orgId;
	}
	/**
	 * @param orgId the orgId to set
	 */
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	
	@Override
	public String getQueryString() {
		
		StringBuilder sb = new StringBuilder(100);
		sb.append(super.getQueryString());
		
		if( this.getDepUse() != null ){
			sb.append("&amp;depUse=");
			sb.append(this.getDepUse());
		}
		if(StringUtils.isNotBlank(this.getOrgId()) ){
			sb.append("&amp;orgId=");
			sb.append(this.getOrgId());
		}
		
		return sb.toString();
	}
	
}
