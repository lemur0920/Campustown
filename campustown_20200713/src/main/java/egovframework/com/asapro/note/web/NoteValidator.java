/**
 * 
 */
package egovframework.com.asapro.note.web;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import egovframework.com.asapro.interceptor.AdminInterceptor;
import egovframework.com.asapro.note.service.Note;
import egovframework.com.asapro.support.exception.AsaproException;
import egovframework.com.asapro.support.validation.AsaproValidator;

/**
 * 쪽지 입력값 검사
 * @author yckim
 * @since 2020. 6. 8.
 *
 */
@Component
public class NoteValidator implements AsaproValidator{
	private static final Logger LOGGER = LoggerFactory.getLogger(NoteValidator.class);
	
	@Override
	public boolean supports(Class<?> clazz) {
		return clazz.isAssignableFrom(Note.class);
	}

	@Override
	public void validate(Object target, Errors errors) {
		throw new AsaproException("[ASAPRO] use other validate method. not this.");
	}
	
	@Override
	public void validate(Object target, Errors errors, String formMode) {
		Note noteForm = (Note)target;
		
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "noteReceiverId", null, "받는사람은 필수입력입니다.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "noteContent", null, "내용은 필수입력입니다.");
		
		
	}

}
