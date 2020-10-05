/**
 * 
 */
package egovframework.com.asapro.role.service;

/**
 * 역할, 권한 N:N 관계
 * @author yckim
 * @since 2018. 5. 21.
 *
 */
public class RoleCap {
	
	private String roleCode;
	private String capId;

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
	/**
	 * @return the capId
	 */
	public String getCapId() {
		return capId;
	}
	/**
	 * @param capId the capId to set
	 */
	public void setCapId(String capId) {
		this.capId = capId;
	}
	
}
