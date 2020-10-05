/**
 * 
 */
package egovframework.com.asapro.organization.web;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;

import egovframework.com.asapro.organization.service.Department;
import egovframework.com.asapro.support.exception.AsaproException;
import egovframework.com.asapro.support.validation.AsaproValidator;

/**
 * 부서 입력값 검증
 * 
 * @author yckim
 * @since 2018. 5. 11.
 */
@Component
public class DepartmentValidator implements AsaproValidator{

	@Override
	public boolean supports(Class<?> clazz) {
		return clazz.isAssignableFrom(Department.class);
	}

	@Override
	public void validate(Object target, Errors errors) {
		throw new AsaproException("[ASAPRO] use other validate method. not this.");
	}
	
	@Override
	public void validate(Object target, Errors errors, String formMode) {
		
		Department departmentForm = (Department)target;
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "organization.orgId", null, "기관선택은 필수입니다.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "depName", null, "부서이름은 필수입력입니다.");
	}
	
}
