/**
 * 
 */
package egovframework.com.asapro.member.user.web;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import egovframework.com.asapro.member.admin.service.AdminPassword;

/**
 * 패스워드 변경 들어가기전에 미리 체크시 입력값 검사
 * @author yckim
 * @since 2018. 4. 27.
 */
@Component
public class PasswordCheckValidator implements Validator{

	@Override
	public boolean supports(Class<?> clazz) {
		return clazz.isAssignableFrom(AdminPassword.class);
	}

	@Override
	public void validate(Object target, Errors errors) {
		
		AdminPassword passworCheckForm = (AdminPassword) target;
		String currentUserMemberId = passworCheckForm.getNewPassword();
		
		if( !passworCheckForm.getAdminId().equals(currentUserMemberId) ){
			errors.rejectValue("memberId", null, "회원정보가 일치하지 않습니다.");
		}
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "adminId", null, "아이디는 필수입력입니다.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "oldPassword", null, "비밀번호는 필수입력입니다.");
		
	}

}
