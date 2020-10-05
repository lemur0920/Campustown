/**
 * 
 */
package egovframework.com.asapro.member.user.service;


/**
 * 약관동의
 * @author yckim
 * @since 2020. 2. 28.
 *
 */
public class UserTermsAgreement {
	
	private boolean useTermsAgreement;	//이용약관동의
	private boolean privacyTermsAgreement;	//개인정보 수집 및 이용동의
	private boolean locationTermsAgreement;	//위치정보 이용약관 동의
	private boolean marketingTermsAgreement;	//마케팅 수신동의
	
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
	/**
	 * @return the locationTermsAgreement
	 */
	public boolean isLocationTermsAgreement() {
		return locationTermsAgreement;
	}
	/**
	 * @param locationTermsAgreement the locationTermsAgreement to set
	 */
	public void setLocationTermsAgreement(boolean locationTermsAgreement) {
		this.locationTermsAgreement = locationTermsAgreement;
	}
	/**
	 * @return the marketingTermsAgreement
	 */
	public boolean isMarketingTermsAgreement() {
		return marketingTermsAgreement;
	}
	/**
	 * @param marketingTermsAgreement the marketingTermsAgreement to set
	 */
	public void setMarketingTermsAgreement(boolean marketingTermsAgreement) {
		this.marketingTermsAgreement = marketingTermsAgreement;
	}
	
}
