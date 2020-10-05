/**
 * 
 */
package egovframework.com.asapro.member.admin.service;


/**
 * 약관동의
 * @author yckim
 * @since 2018. 4. 26.
 *
 */
public class TermsAgreement {
	
	private boolean useTermsAgreement;//약관동의
	private boolean privacyTermsAgreement;//개인정보 수집 및 이용동의
	
	/**
	 * @return the useTermsAgreement
	 */
	public boolean isUseTermsAgreement() {
		return useTermsAgreement;
	}
	/**
	 * @param useTermsAgreement the useTermsAgreement to set
	 */
	public void setUseTermsAgreement(boolean useTermsAgreement) {
		this.useTermsAgreement = useTermsAgreement;
	}
	/**
	 * @return the privacyTermsAgreement
	 */
	public boolean isPrivacyTermsAgreement() {
		return privacyTermsAgreement;
	}
	/**
	 * @param privacyTermsAgreement the privacyTermsAgreement to set
	 */
	public void setPrivacyTermsAgreement(boolean privacyTermsAgreement) {
		this.privacyTermsAgreement = privacyTermsAgreement;
	}
	
}
