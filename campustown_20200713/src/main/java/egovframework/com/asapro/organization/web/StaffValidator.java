/**
 * 
 */
package egovframework.com.asapro.organization.web;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import egovframework.com.asapro.organization.service.Staff;

/**
 * 직원 입력값 검증
 * 
 * @author yckim
 * @since 2018. 5. 11.
 */
@Component
public class StaffValidator implements Validator{

	@Override
	public boolean supports(Class<?> clazz) {
		return clazz.isAssignableFrom(Staff.class);
	}

	@Override
	public void validate(Object target, Errors errors) {
		
		Staff staff = (Staff)target;
		
		/*
		if( staff.getStDepIdx() == null || staff.getStDepIdx().intValue() == 0 ){
			errors.rejectValue("stDepIdx", null, "부서는 필수선택입니다.");
		}
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "stName", null, "직원이름은 필수입력입니다.");
		*/
	}
	
}
