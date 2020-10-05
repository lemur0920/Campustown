/**
 * 
 */
package egovframework.com.asapro.content.web;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;

import egovframework.com.asapro.content.service.Content;
import egovframework.com.asapro.support.exception.AsaproException;
import egovframework.com.asapro.support.validation.AsaproValidator;

/**
 * 콤텐츠 폼 검증
 * @author yckim
 * @since 2018. 7. 20.
 *
 */
@Component
public class ContentValidator implements AsaproValidator{

	@Override
	public boolean supports(Class<?> clazz) {
		return clazz.isAssignableFrom(Content.class);
	}

	@Override
	public void validate(Object target, Errors errors) {
		throw new AsaproException("[ASAPRO] use other validate method. not this.");
	}
	
	@Override
	public void validate(Object target, Errors errors, String formMode) {
		//Content contentForm = (Content)target;
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "contentTitle", null, "콘텐츠는 제목은 필수입력입니다.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "content", null, "콘텐츠는 필수입력입니다.");
	}

}
