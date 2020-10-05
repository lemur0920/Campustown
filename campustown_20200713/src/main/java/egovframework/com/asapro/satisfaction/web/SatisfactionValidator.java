/**
 * 
 */
package egovframework.com.asapro.satisfaction.web;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;

import egovframework.com.asapro.satisfaction.service.Satisfaction;
import egovframework.com.asapro.support.exception.AsaproException;
import egovframework.com.asapro.support.validation.AsaproValidator;

/**
 * 만족도 입력 검사
 * @author yckim
 * @since 2019. 4. 15.
 *
 */
@Component
public class SatisfactionValidator implements AsaproValidator{

	@Override
	public boolean supports(Class<?> clazz) {
		return clazz.isAssignableFrom(Satisfaction.class);
	}

	@Override
	public void validate(Object target, Errors errors) {
		throw new AsaproException("[ASAPRO] use other validate method. not this.");
	}
	
	@Override
	public void validate(Object target, Errors errors, String formMode) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "menuId", null, "메뉴아이디는 필수입력입니다.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "satisScore", null, "평가점수는 필수입력입니다.");
	}

}
