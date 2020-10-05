/**
 * 
 */
package egovframework.com.asapro.volunteer.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;

import egovframework.com.asapro.support.exception.AsaproException;
import egovframework.com.asapro.support.validation.AsaproValidator;
import egovframework.com.asapro.volunteer.service.Volunteer;
import egovframework.com.asapro.volunteer.service.VolunteerService;

/**
 * 자원봉사 설정 검증
 * @author yckim
 * @since 2018. 12. 12.
 *
 */
@Component
public class VolunteerValidator implements AsaproValidator{

	@Autowired
	private VolunteerService volunteerService;
	
	@Override
	public boolean supports(Class<?> clazz) {
		return clazz.isAssignableFrom(Volunteer.class);
	}

	@Override
	public void validate(Object target, Errors errors) {
		throw new AsaproException("[ASAPRO] use other validate method. not this.");
	}

	@Override
	public void validate(Object target, Errors errors, String formMode) {
		
		
	}

}
