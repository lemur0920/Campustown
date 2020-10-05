/**
 * 
 */
package egovframework.com.asapro.commonContent.web;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import egovframework.com.asapro.commonContent.service.CommonContent;
import egovframework.com.asapro.content.service.Content;
import egovframework.com.asapro.support.exception.AsaproException;
import egovframework.com.asapro.support.validation.AsaproValidator;

/**
 * 공통 콤텐츠 폼 검증
 * @author yckim
 * @since 2018. 8. 23.
 *
 */
@Component
public class CommonContentValidator implements AsaproValidator{

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
		CommonContent commonContentForm = (CommonContent)target;
		
		//ValidationUtils.rejectIfEmptyOrWhitespace(errors, "comContModule", null, "적용프로그램은 필수선택입니다.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "comContTitle", null, "제목은 필수입력입니다.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "comContContent", null, "콘텐츠는 필수입력입니다.");
	}

}
