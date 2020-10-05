/**
 * 
 */
package egovframework.com.asapro.education.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;

import egovframework.com.asapro.support.exception.AsaproException;
import egovframework.com.asapro.support.validation.AsaproValidator;
import egovframework.com.asapro.education.service.Education;
import egovframework.com.asapro.education.service.EducationService;

/**
 * 교육프로그램 정보 검증
 * @author yckim
 * @since 2018. 12. 17.
 *
 */
@Component
public class EducationValidator implements AsaproValidator{

	@Autowired
	private EducationService educationService;
	
	@Override
	public boolean supports(Class<?> clazz) {
		return clazz.isAssignableFrom(Education.class);
	}

	@Override
	public void validate(Object target, Errors errors) {
		throw new AsaproException("[ASAPRO] use other validate method. not this.");
	}

	@Override
	public void validate(Object target, Errors errors, String formMode) {
		
		
	}

}
