/**
 * 
 */
package egovframework.com.asapro.member.user.web;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import egovframework.com.asapro.member.user.service.UserTermsAgreement;
import egovframework.com.asapro.support.exception.AsaproException;
import egovframework.com.asapro.support.validation.AsaproValidator;

/**
 * 약관동의 검사
 * @author yckim
 * @since 2020. 2. 28.
 *
 */
@Component
public class UserTermsAgreementValidator implements AsaproValidator{

	@Override
	public boolean supports(Class<?> clazz) {
		return clazz.isAssignableFrom(UserTermsAgreement.class);
	}

	@Override
	public void validate(Object target, Errors errors) {
		throw new AsaproException("[ASAPRO] use other validate method. not this.");
	}
	
	@Override
	public void validate(Object target, Errors errors, String formMode) {
		UserTermsAgreement termsAgreement = (UserTermsAgreement) target;
		
		if( !termsAgreement.isUseTermsAgreement() ){
			errors.rejectValue("useTermsAgreement", "validation.agreement.useTermsAgreement.check", "이용약관에 동의해 주세요.");
		}
		if( !termsAgreement.isPrivacyTermsAgreement() ){
			errors.rejectValue("privacyTermsAgreement", "validation.agreement.privacyTermsAgreement.check", "개인정보 수집 및 이용목적에 동의해 주세요.");
		}
		/*if( !termsAgreement.isLocationTermsAgreement() ){
			errors.rejectValue("locationTermsAgreement", "validation.agreement.locationTermsAgreement.check", "위치정보 이용약관에 동의해 주세요.");
		}
		if( !termsAgreement.isMarketingTermsAgreement() ){
			errors.rejectValue("marketingTermsAgreement", "validation.agreement.marketingTermsAgreement.check", "마케팅 수신에 동의해 주세요.");
		}*/
	}

}
