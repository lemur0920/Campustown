/**
 * 
 */
package egovframework.com.asapro.member.user.service;

import java.util.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 비밀번호 수정 VO
 * @author yckim
 * @since 2020. 2. 24.
 *
 */
public class UserPassword {
	
	private String userId;
	private String oldPassword;
	private String newPassword;
	private String newPasswordCheck;
	private Date userPWLastUpdated;
	
	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}
	/**
	 * @param userId the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
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
	 * @return the userPWLastUpdated
	 */
	public Date getUserPWLastUpdated() {
		return userPWLastUpdated;
	}
	/**
	 * @param userPWLastUpdated the userPWLastUpdated to set
	 */
	public void setUserPWLastUpdated(Date userPWLastUpdated) {
		this.userPWLastUpdated = userPWLastUpdated;
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
