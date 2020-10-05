/**
 * 
 */
package egovframework.com.asapro.poll.web;

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

import egovframework.com.asapro.poll.service.Poll;
import egovframework.com.asapro.interceptor.AdminInterceptor;
import egovframework.com.asapro.support.exception.AsaproException;
import egovframework.com.asapro.support.validation.AsaproValidator;

/**
 * 투표 입력값 검사
 * @author yckim
 * @since 2020. 1. 21.
 *
 */
@Component
public class PollValidator implements AsaproValidator{
	private static final Logger LOGGER = LoggerFactory.getLogger(PollValidator.class);
	
	@Override
	public boolean supports(Class<?> clazz) {
		return clazz.isAssignableFrom(Poll.class);
	}

	@Override
	public void validate(Object target, Errors errors) {
		throw new AsaproException("[ASAPRO] use other validate method. not this.");
	}
	
	@Override
	public void validate(Object target, Errors errors, String formMode) {
		Poll pollForm = (Poll)target;
		
		
		//ValidationUtils.rejectIfEmptyOrWhitespace(errors, "poType", null, "유형은 필수선택입니다.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "poQuestion", null, "질문은 필수입력입니다.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "poDescription", null, "부가설명은 필수입력입니다.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "poStartDate", null, "시작일은 필수입력입니다.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "poEndDate", null, "종료칠은 필수입력입니다.");
		
		if( "INSERT".equals(formMode) ){
			
			if( pollForm.getPoImage() == null || pollForm.getPoImage().isEmpty() ){
				errors.rejectValue("poImage", null, "이미지는 필수입니다.");
			}
		}
		
		//대표이미지 대체텍스트 체크
		if( pollForm.getPoImage() != null && pollForm.getPoImage().getSize() > 0 ){
			//대체텍스트 입력체크
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "poThumbText", null, "대체텍스트는 필수입력입니다.");
		}
		
		if( StringUtils.isNotBlank(pollForm.getPoStartDate()) && StringUtils.isNotBlank(pollForm.getPoEndDate()) ){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.KOREA);
			Date startDate = null;
			Date endDate = null;
			try {
				startDate = sdf.parse(pollForm.getPoStartDate());
				endDate = sdf.parse(pollForm.getPoEndDate()); 
			} catch (ParseException e) {
				LOGGER.error("[ParseException] Try/Catch... : "+ e.getMessage());
			}
			if( startDate.after(endDate) ){
				errors.rejectValue("bnStartDate", null, "시작일은 종료일 이전이어야 합니다.");
				errors.rejectValue("bnEndDate", null, "종료일은 시작일 이후여야 합니다.");
			}
		}
		
		
	}

}
