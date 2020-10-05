/**
 * 
 */
package egovframework.com.asapro.member.admin.service;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 아이디 찾기
 * @author yckim
 * @since 2018. 4. 26.
 *
 */
public class Recover {
	
	private String recoverMode;
	private String memberId;
	private String memberName;
	private String memberMobile1;
	private String memberMobile2;
	private String memberMobile3;
	private String memberEmail;
	private String memberTelMobile;
	
	/**
	 * @return the recoverMode
	 */
	public String getRecoverMode() {
		return recoverMode;
	}
	/**
	 * @param recoverMode the recoverMode to set
	 */
	public void setRecoverMode(String recoverMode) {
		this.recoverMode = recoverMode;
	}
	/**
	 * @return the memberId
	 */
	public String getMemberId() {
		return memberId;
	}
	/**
	 * @param memberId the memberId to set
	 */
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	/**
	 * @return the memberName
	 */
	public String getMemberName() {
		return memberName;
	}
	/**
	 * @param memberName the memberName to set
	 */
	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}
	/**
	 * @return the memberMobile1
	 */
	public String getMemberMobile1() {
		return memberMobile1;
	}
	/**
	 * @param memberMobile1 the memberMobile1 to set
	 */
	public void setMemberMobile1(String memberMobile1) {
		this.memberMobile1 = memberMobile1;
	}
	/**
	 * @return the memberMobile2
	 */
	public String getMemberMobile2() {
		return memberMobile2;
	}
	/**
	 * @param memberMobile2 the memberMobile2 to set
	 */
	public void setMemberMobile2(String memberMobile2) {
		this.memberMobile2 = memberMobile2;
	}
	/**
	 * @return the memberMobile3
	 */
	public String getMemberMobile3() {
		return memberMobile3;
	}
	/**
	 * @param memberMobile3 the memberMobile3 to set
	 */
	public void setMemberMobile3(String memberMobile3) {
		this.memberMobile3 = memberMobile3;
	}
	/**
	 * @return the memberEmail
	 */
	public String getMemberEmail() {
		return memberEmail;
	}
	/**
	 * @param memberEmail the memberEmail to set
	 */
	public void setMemberEmail(String memberEmail) {
		this.memberEmail = memberEmail;
	}
	/**
	 * @return the memberTelMobile
	 */
	public String getMemberTelMobile() {
		return memberTelMobile;
	}
	/**
	 * @param memberTelMobile the memberTelMobile to set
	 */
	public void setMemberTelMobile(String memberTelMobile) {
		this.memberTelMobile = memberTelMobile;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	/*@Override
	public String toString() {
		return "Recover\n[recoverMode=" + recoverMode + "\n, memberId=" + memberId + "\n, memberName=" + memberName + "\n, memberMobile1=" + memberMobile1 + "\n, memberMobile2=" + memberMobile2
				+ "\n, memberMobile3=" + memberMobile3 + "\n, memberEmail=" + memberEmail + "\n, memberTelMobile=" + memberTelMobile + "]\n";
	}*/
	/**
     * toString 메소드를 대치한다.
     */
	@Override
    public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
	
}