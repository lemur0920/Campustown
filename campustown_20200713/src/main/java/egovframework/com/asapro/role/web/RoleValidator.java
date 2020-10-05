/**
 * 
 */
package egovframework.com.asapro.role.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;

import egovframework.com.asapro.role.service.Role;
import egovframework.com.asapro.role.service.RoleService;
import egovframework.com.asapro.support.exception.AsaproException;
import egovframework.com.asapro.support.validation.AsaproValidator;

/**
 * 역할 검증
 * @author yckim
 * @since 2018. 5. 21.
 *
 */
@Component
public class RoleValidator implements AsaproValidator{

	@Autowired
	private RoleService roleService;
	
	@Override
	public boolean supports(Class<?> clazz) {
		return clazz.isAssignableFrom(Role.class);
	}

	@Override
	public void validate(Object target, Errors errors) {
		throw new AsaproException("[ASAPRO] use other validate method. not this.");
	}

	@Override
	public void validate(Object target, Errors errors, String formMode) {
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "roleCode", null, "역할 코드는 필수 입력입니다.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "roleName", null, "역할 이름은 필수 입력입니다.");
		
		//신규역할 입력시에만 체크
		if( "INSERT".equalsIgnoreCase(formMode) ){
			Role roleForm = (Role)target;
			if(!roleForm.getRoleCode().matches("[a-zA-Z0-9_]*")){
				errors.rejectValue("roleCode", null, "영문, 숫자, 언더바(_)만 사용할 수 있습니다.");
				return;
			}
			Role fromDB = roleService.getRole(roleForm);
			if(fromDB != null){
				errors.rejectValue("roleCode", null, "이미 사용중인 역할 코드입니다.");
				return;
			}
		}
	}

}
