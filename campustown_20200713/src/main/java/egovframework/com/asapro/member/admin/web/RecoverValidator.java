/**
 * 
 */
package egovframework.com.asapro.member.admin.web;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;

import egovframework.com.asapro.member.admin.service.Recover;
import egovframework.com.asapro.support.util.AsaproUtils;
import egovframework.com.asapro.support.validation.AsaproValidator;

/**
 *
 * @author yckim
 * @since 2018. 4. 27.
 *
 */
@Component
public class RecoverValidator implements AsaproValidator{

	@Override
	public boolean supports(Class<?> clazz) {
		return clazz.isAssignableFrom(Recover.class);
	}

	@Override
	public void validate(Object target, Errors errors) {
		
		Recover recoverForm = (Recover) target;
		
		if("ID".equals(recoverForm.getRecoverMode())){
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "memberName", "validation.member.memberName.blank", "이름은 필수입력입니다.");
		}
		if("PASSWORD".equals(recoverForm.getRecoverMode())){
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "memberId", "validation.member.memberId.blank", "아이디는 필수입력입니다.");
		}
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "memberEmail", "validation.member.memberEmail.blank", "이메일 주소는 필수입력입니다.");
		
		if( StringUtils.isNotBlank(recoverForm.getMemberEmail()) ){
			if( !AsaproUtils.isEmail(recoverForm.getMemberEmail()) ){
				errors.rejectValue("memberEmail", "validation.member.memberEmail.format", "이메일 주소를 형식에 맞게 입력해 주세요.");
			}
		}
		if( StringUtils.isBlank(recoverForm.getMemberMobile1()) || StringUtils.isBlank(recoverForm.getMemberMobile2()) || StringUtils.isBlank(recoverForm.getMemberMobile3())){
			errors.rejectValue("memberMobile1", "validation.member.memberTel.blank", "휴대폰 번호는 필수입력입니다.");
		}
		/*
		if( StringUtils.isBlank(recoverForm.getMemberTelMobile()) ){
			errors.rejectValue("memberTelMobile", "validation.member.memberTelMobile.blank", "전화번호 또는 휴대폰 번호는 필수입력입니다.");
		}
		 */
	}
	
	@Override
	public void validate(Object target, Errors errors, String formMode) {
		//현재로선 처리할게 없음. 그냥 PMD용 주석
	}

}
