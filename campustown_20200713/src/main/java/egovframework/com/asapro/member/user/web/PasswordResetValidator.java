/**
 * 
 */
package egovframework.com.asapro.member.user.web;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import egovframework.com.asapro.member.admin.service.AdminPassword;

/**
 * @author yckim
 * @since 2018. 4. 27.
 */
@Component
public class PasswordResetValidator implements Validator{

	@Override
	public boolean supports(Class<?> clazz) {
		return clazz.isAssignableFrom(AdminPassword.class);
	}

	@Override
	public void validate(Object target, Errors errors) {
		
		AdminPassword passwordForm = (AdminPassword) target;
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "newPassword", null, "새 비밀번호는 필수입니다.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "newPasswordCheck", null, "새 비밀번호 확인값은 필수입니다.");
		
		if( StringUtils.isNotBlank(passwordForm.getNewPassword()) && StringUtils.isNotBlank(passwordForm.getNewPasswordCheck()) ){
			if( !passwordForm.getNewPassword().equals(passwordForm.getNewPasswordCheck()) ){
				errors.rejectValue("newPasswordCheck", null, "비밀번호 확인값이 일치 하지 않습니다.");
			}
		}
		
	}

}
