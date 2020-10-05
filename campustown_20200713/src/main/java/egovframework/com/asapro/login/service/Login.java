/**
 * 
 */
package egovframework.com.asapro.login.service;


/**
 * 로그인
 * @author yckim
 * @since 2018. 5. 23.
 *
 */
public class Login {
	
	private String loginId;
	private String loginPassword;
	private boolean rememberMe;
	private String returnUrl;
	private boolean signUpKeyCheck;
	
	/**
	 * @return the loginId
	 */
	public String getLoginId() {
		return loginId;
	}
	/**
	 * @param loginId the loginId to set
	 */
	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}
	/**
	 * @return the loginPassword
	 */
	public String getLoginPassword() {
		return loginPassword;
	}
	/**
	 * @param loginPassword the loginPassword to set
	 */
	public void setLoginPassword(String loginPassword) {
		this.loginPassword = loginPassword;
	}
	/**
	 * @return the rememberMe
	 */
	public boolean isRememberMe() {
		return rememberMe;
	}
	/**
	 * @param rememberMe the rememberMe to set
	 */
	public void setRememberMe(boolean rememberMe) {
		this.rememberMe = rememberMe;
	}
	/**
	 * @return the returnUrl
	 */
	public String getReturnUrl() {
		return returnUrl;
	}
	/**
	 * @param returnUrl the returnUrl to set
	 */
	public void setReturnUrl(String returnUrl) {
		this.returnUrl = returnUrl;
	}
	/**
	 * @return the signUpKeyCheck
	 */
	public boolean isSignUpKeyCheck() {
		return signUpKeyCheck;
	}
	/**
	 * @param signUpKeyCheck the signUpKeyCheck to set
	 */
	public void setSignUpKeyCheck(boolean signUpKeyCheck) {
		this.signUpKeyCheck = signUpKeyCheck;
	}
	
}
