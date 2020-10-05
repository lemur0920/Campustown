/**
 * 
 */
package egovframework.com.asapro.viewing.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;

import egovframework.com.asapro.support.exception.AsaproException;
import egovframework.com.asapro.support.validation.AsaproValidator;
import egovframework.com.asapro.viewing.service.Viewing;
import egovframework.com.asapro.viewing.service.ViewingService;

/**
 * 관람정보 검증
 * @author yckim
 * @since 2018. 12. 14.
 *
 */
@Component
public class ViewingValidator implements AsaproValidator{

	@Autowired
	private ViewingService viewingService;
	
	@Override
	public boolean supports(Class<?> clazz) {
		return clazz.isAssignableFrom(Viewing.class);
	}

	@Override
	public void validate(Object target, Errors errors) {
		throw new AsaproException("[ASAPRO] use other validate method. not this.");
	}

	@Override
	public void validate(Object target, Errors errors, String formMode) {
		
		
	}

}
