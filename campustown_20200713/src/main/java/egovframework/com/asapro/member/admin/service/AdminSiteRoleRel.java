/**
 * 
 */
package egovframework.com.asapro.member.admin.service;

import java.io.Serializable;

/**
 * 관리자 사이트별 역할 릴레이션
 * @author yckim
 * @since 2018. 10. 4.
 *
 */
@SuppressWarnings("serial")
public class AdminSiteRoleRel implements Serializable{
	private String adminId = "";	//관리자 아이디
	private String sitePrefix = "";	//사이트 프리픽스
	private String roleCode = "";	//역할 코드
	/**
	 * @return the adminId
	 */
	public String getAdminId() {
		return adminId;
	}
	/**
	 * @param adminId the adminId to set
	 */
	public void setAdminId(String adminId) {
		this.adminId = adminId;
	}
	/**
	 * @return the sitePrefix
	 */
	public String getSitePrefix() {
		return sitePrefix;
	}
	/**
	 * @param sitePrefix the sitePrefix to set
	 */
	public void setSitePrefix(String sitePrefix) {
		this.sitePrefix = sitePrefix;
	}
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
