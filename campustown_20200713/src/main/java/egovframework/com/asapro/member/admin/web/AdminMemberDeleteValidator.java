/**
 * 
 */
package egovframework.com.asapro.member.admin.web;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import egovframework.com.asapro.member.admin.service.AdminMemberDelete;

/**
 * 관리자 탈퇴 검사
 * @author yckim
 * @since 2018. 4. 27.
 *
 */
@Component
public class AdminMemberDeleteValidator implements Validator{

	@Override
	public boolean supports(Class<?> clazz) {
		return clazz.isAssignableFrom(AdminMemberDelete.class);
	}

	@Override
	public void validate(Object target, Errors errors) {
		AdminMemberDelete memberDelete = (AdminMemberDelete) target;
		
		/*
		if( !memberDelete.isReadMemberDeleteInfo() ){
			errors.rejectValue("readMemberDeleteInfo", "validation.member.deactivate.readMemberDeleteInfo", "회원탈퇴 안내사항을 확인해주세요.");
		}
		//관리자는 탈퇴 불가
		if( memberDelete.getCurrentAdmin().getAdminRole().isRoleAdmin() 
				|| "admin".equals(memberDelete.getCurrentAdmin().getAdminId()) ){
			errors.rejectValue("readMemberDeleteInfo", "validation.member.deactivate.canNotDeleteAdminAccount", "관리자는 탈퇴할 수 없습니다.");
		}*/
	}

}
