/**
 * 
 */
package egovframework.com.asapro.member.admin.web;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import egovframework.com.asapro.member.admin.service.TermsAgreement;
import egovframework.com.asapro.support.validation.AsaproValidator;

/**
 * 약관동의 검사
 * @author yckim
 * @since 2018. 4. 27.
 *
 */
@Component
public class TermsAgreementValidator implements AsaproValidator{

	@Override
	public boolean supports(Class<?> clazz) {
		return clazz.isAssignableFrom(TermsAgreement.class);
	}

	@Override
	public void validate(Object target, Errors errors) {
		TermsAgreement termsAgreement = (TermsAgreement) target;
		
		if( !termsAgreement.isUseTermsAgreement() ){
			errors.rejectValue("useTermsAgreement", "validation.agreement.useTermsAgreement.check", "이용약관에 동의해 주세요.");
		}
		if( !termsAgreement.isPrivacyTermsAgreement() ){
			errors.rejectValue("privacyTermsAgreement", "validation.agreement.privacyTermsAgreement.check", "개인정보 수집 및 이용목적에 동의해 주세요.");
		}
		
	}

	@Override
	public void validate(Object target, Errors errors, String formMode) {
		// TODO Auto-generated method stub
	}

}
