/**
 * 
 */
package egovframework.com.asapro.organization.service;

import org.apache.commons.lang3.StringUtils;

import egovframework.com.asapro.support.pagination.PagingSearch;

/**
 * view 부서 검색 VO
 * 
 * @author yckim
 * @since 2020. 4. 7.
 */
public class DepartmentViewSearch extends PagingSearch{

	private String parentId;	//부모부서 아이디
	
	/**
	 * @return the parentId
	 */
	public String getParentId() {
		return parentId;
	}

	/**
	 * @param parentId the parentId to set
	 */
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	@Override
	public String getQueryString() {
		
		StringBuilder sb = new StringBuilder(100);
		sb.append(super.getQueryString());
		
		if(StringUtils.isNotBlank(this.getParentId()) ){
			sb.append("&amp;parentId=");
			sb.append(this.getParentId());
		}
		/*if( this.getDepUse() != null ){
			sb.append("&amp;depUse=");
			sb.append(this.getDepUse());
		}
		if(StringUtils.isNotBlank(this.getOrgId()) ){
			sb.append("&amp;orgId=");
			sb.append(this.getOrgId());
		}*/
		
		return sb.toString();
	}
	
}
