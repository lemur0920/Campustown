/**
 * 
 */
package egovframework.com.asapro.login.web;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;

import egovframework.com.asapro.login.service.Login;
import egovframework.com.asapro.login.service.LoginAdminService;
import egovframework.com.asapro.member.admin.service.AdminMember;
import egovframework.com.asapro.support.exception.AsaproException;
import egovframework.com.asapro.support.validation.AsaproValidator;

/**
 * 로그인 폼 검증
 * @author yckim
 * @since 2018. 5. 23.
 *
 */
@Component
public class LoginValidator implements AsaproValidator{
	
	@Autowired
	private LoginAdminService loginAdminService;
	
	@Override
	public boolean supports(Class<?> clazz) {
		return clazz.isAssignableFrom(Login.class);
	}
	
	@Override
	public void validate(Object target, Errors errors) {
		throw new AsaproException("[ASAPRO] use other validate method. not this.");
		
	}
	
	@Override
	public void validate(Object target, Errors errors, String formMode) {
		
		if("ADMIN".equals(formMode) ){
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "loginId", "validation.login.loginId.blank", "아이디는 필수입력입니다.");
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "loginPassword", "validation.login.loginPassword.blank", "비밀번호는 필수입력입니다.");
		}
		if("USER".equals(formMode)){
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "loginId", "validation.login.loginId.blank", "아이디는 필수입력입니다.");
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "loginPassword", "validation.login.loginPassword.blank", "비밀번호는 필수입력입니다.");
		}
		
		//로그인 실패 검증
//		Login loginForm = (Login)target;
//		if( StringUtils.isNotBlank(loginForm.getLoginId()) && StringUtils.isNotBlank(loginForm.getLoginPassword()) ){
//			AdminMember adminMember = loginService.login(loginForm);
//			if( adminMember == null ){
//				errors.rejectValue("loginPassword", "validation.login.loginPassword.error", "아이디 또는 비밀번호가 일치하지 않습니다.");
//			}
//		}
	}


}
