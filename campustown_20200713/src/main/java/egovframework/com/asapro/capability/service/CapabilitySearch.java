/**
 * 
 */
package egovframework.com.asapro.capability.service;

import egovframework.com.asapro.support.pagination.PagingSearch;

/**
 * 권한 검색
 * @author yckim
 * @since 2018. 5. 17.
 *
 */
public class CapabilitySearch extends PagingSearch{
	
	private String roleCode;
	
	/**
	 * @return the roleCode
	 */
	public String getRoleCode() {
		return roleCode;
	}

	/**
	 * @param roleCode the roleCode to set
	 */
	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}
	
}
