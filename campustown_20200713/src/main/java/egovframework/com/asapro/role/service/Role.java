/**
 * 
 */
package egovframework.com.asapro.role.service;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import egovframework.com.asapro.capability.service.Capability;
//import egovframework.com.asapro.watchdog.aop.WatchDog;

/**
 * 역할 VO
 * @author yckim
 * @since 2018. 5. 21.
 *
 */
@SuppressWarnings("serial")
public class Role implements Serializable{
	
//	@WatchDog
	private String roleCode;	//역할코드
	private String roleName;	//역할 이름
	private String roleDescription = "";	//역할 설명
	private Date roleRegDate;	//등록일시
	private boolean roleDefault;	//기본역할 여부
	private boolean roleAdmin;	//관리자 역할 여부
	private boolean roleJoin = false;	//가입시 역할 여부
	
	private List<Capability> capabilityList;	//권한 목록
	
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
	 * @return the roleName
	 */
	public String getRoleName() {
		return roleName;
	}
	/**
	 * @param roleName the roleName to set
	 */
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	/**
	 * @return the roleDescription
	 */
	public String getRoleDescription() {
		return roleDescription;
	}
	/**
	 * @param roleDescription the roleDescription to set
	 */
	public void setRoleDescription(String roleDescription) {
		this.roleDescription = roleDescription;
	}
	/**
	 * @return the roleRegDate
	 */
	public Date getRoleRegDate() {
		return roleRegDate;
	}
	/**
	 * @param roleRegDate the roleRegDate to set
	 */
	public void setRoleRegDate(Date roleRegDate) {
		this.roleRegDate = roleRegDate;
	}
	/**
	 * @return the roleDefault
	 */
	public boolean isRoleDefault() {
		return roleDefault;
	}
	/**
	 * @param roleDefault the roleDefault to set
	 */
	public void setRoleDefault(boolean roleDefault) {
		this.roleDefault = roleDefault;
	}
	/**
	 * @return the roleAdmin
	 */
	public boolean isRoleAdmin() {
		return roleAdmin;
	}
	/**
	 * @param roleAdmin the roleAdmin to set
	 */
	public void setRoleAdmin(boolean roleAdmin) {
		this.roleAdmin = roleAdmin;
	}
	/**
	 * @return the roleJoin
	 */
	public boolean isRoleJoin() {
		return roleJoin;
	}
	/**
	 * @param roleJoin the roleJoin to set
	 */
	public void setRoleJoin(boolean roleJoin) {
		this.roleJoin = roleJoin;
	}
	/**
	 * @return the capabilityList
	 */
	public List<Capability> getCapabilityList() {
		return capabilityList;
	}
	/**
	 * @param capabilityList the capabilityList to set
	 */
	public void setCapabilityList(List<Capability> capabilityList) {
		this.capabilityList = capabilityList;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return this.getRoleCode();
	}
	
}
