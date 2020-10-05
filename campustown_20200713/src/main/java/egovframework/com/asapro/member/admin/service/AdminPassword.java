/**
 * 
 */
package egovframework.com.asapro.member.admin.service;

import java.util.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 비밀번호 수정 VO
 * @author yckim
 * @since 2018. 5. 8.
 *
 */
public class AdminPassword {
	
	private String adminId;
	private String oldPassword;
	private String newPassword;
	private String newPasswordCheck;
	private Date adminPWLastUpdated;
	
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
	 * @return the oldPassword
	 */
	public String getOldPassword() {
		return oldPassword;
	}
	/**
	 * @param oldPassword the oldPassword to set
	 */
	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}
	/**
	 * @return the newPassword
	 */
	public String getNewPassword() {
		return newPassword;
	}
	/**
	 * @param newPassword the newPassword to set
	 */
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
	/**
	 * @return the newPasswordCheck
	 */
	public String getNewPasswordCheck() {
		return newPasswordCheck;
	}
	/**
	 * @param newPasswordCheck the newPasswordCheck to set
	 */
	public void setNewPasswordCheck(String newPasswordCheck) {
		this.newPasswordCheck = newPasswordCheck;
	}
	
	/**
	 * @return the adminPWLastUpdated
	 */
	public Date getAdminPWLastUpdated() {
		return adminPWLastUpdated;
	}
	/**
	 * @param adminPWLastUpdated the adminPWLastUpdated to set
	 */
	public void setAdminPWLastUpdated(Date adminPWLastUpdated) {
		this.adminPWLastUpdated = adminPWLastUpdated;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	/**
     * toString 메소드를 대치한다.
     */
	@Override
    public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
	
}
