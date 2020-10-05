/**
 * 
 */
package egovframework.com.asapro.member.admin.service;

import java.util.Date;

//import egovframework.com.asapro.watchdog.aop.WatchDog;

/**
 * 회원탈퇴
 * <pre>
 * - 관리자에서 회원삭제할때 쓰는거 아니니까 헷갈리지 말자.
 * </pre>
 * @author yckim
 * @since 2018. 4. 26.
 *
 */
public class AdminMemberDelete {
	
	private Integer memberDeleteId;
//	@WatchDog
	private String adminId;
	private String adminDeletedBy;
	private Date adminRegDate;
	private Date adminDeleteDate;
	private AdminMember currentAdmin;
	private boolean readAdminMemberDeleteInfo;
	/**
	 * @return the memberDeleteId
	 */
	public Integer getMemberDeleteId() {
		return memberDeleteId;
	}
	/**
	 * @param memberDeleteId the memberDeleteId to set
	 */
	public void setMemberDeleteId(Integer memberDeleteId) {
		this.memberDeleteId = memberDeleteId;
	}
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
	 * @return the adminDeletedBy
	 */
	public String getAdminDeletedBy() {
		return adminDeletedBy;
	}
	/**
	 * @param adminDeletedBy the adminDeletedBy to set
	 */
	public void setAdminDeletedBy(String adminDeletedBy) {
		this.adminDeletedBy = adminDeletedBy;
	}
	/**
	 * @return the adminRegDate
	 */
	public Date getAdminRegDate() {
		return adminRegDate;
	}
	/**
	 * @param adminRegDate the adminRegDate to set
	 */
	public void setAdminRegDate(Date adminRegDate) {
		this.adminRegDate = adminRegDate;
	}
	/**
	 * @return the adminDeleteDate
	 */
	public Date getAdminDeleteDate() {
		return adminDeleteDate;
	}
	/**
	 * @param adminDeleteDate the adminDeleteDate to set
	 */
	public void setAdminDeleteDate(Date adminDeleteDate) {
		this.adminDeleteDate = adminDeleteDate;
	}
	/**
	 * @return the currentAdmin
	 */
	public AdminMember getCurrentAdmin() {
		return currentAdmin;
	}
	/**
	 * @param currentAdmin the currentAdmin to set
	 */
	public void setCurrentAdmin(AdminMember currentAdmin) {
		this.currentAdmin = currentAdmin;
	}
	/**
	 * @return the readAdminMemberDeleteInfo
	 */
	public boolean isReadAdminMemberDeleteInfo() {
		return readAdminMemberDeleteInfo;
	}
	/**
	 * @param readAdminMemberDeleteInfo the readAdminMemberDeleteInfo to set
	 */
	public void setReadAdminMemberDeleteInfo(boolean readAdminMemberDeleteInfo) {
		this.readAdminMemberDeleteInfo = readAdminMemberDeleteInfo;
	}


	
}
