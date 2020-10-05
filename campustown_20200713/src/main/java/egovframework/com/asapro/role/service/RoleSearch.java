/**
 * 
 */
package egovframework.com.asapro.role.service;

import org.apache.commons.lang3.StringUtils;

import egovframework.com.asapro.support.pagination.PagingSearch;

/**
 *
 * @author yckim
 * @since 2018. 5. 21.
 *
 */
public class RoleSearch extends PagingSearch{
	
	private String roleCodeTmp;	//결합된 롤 코드 검색대상 (,구분자 )

	/**
	 * @return the roleCodeTmp
	 */
	public String getRoleCodeTmp() {
		return roleCodeTmp;
	}

	/**
	 * @param roleCodeTmp the roleCodeTmp to set
	 */
	public void setRoleCodeTmp(String roleCodeTmp) {
		this.roleCodeTmp = roleCodeTmp;
	}
	
	public String[] getRoleCodeTmpArray(){
		if(StringUtils.isNotBlank(getRoleCodeTmp()) ){
			return getRoleCodeTmp().split(",");
		}
		return null;
	}
	
	@Override
	public String getQueryString() {
		
		StringBuilder sb = new StringBuilder(100);
		sb.append(super.getQueryString());
		
		/*if( this.getRoleCodeTmp() != null ){
			sb.append("&amp;roleCodeTmp=");
			sb.append(this.getRoleCodeTmp());
		}*/
		
		return sb.toString();
	}
	
}
