/**
 * 
 */
package egovframework.com.asapro.emailTargetInfo.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;

import egovframework.com.asapro.emailTargetInfo.service.EmailTargetInfo;
import egovframework.com.asapro.emailTargetInfo.service.EmailTargetInfoService;
import egovframework.com.asapro.support.exception.AsaproException;
import egovframework.com.asapro.support.validation.AsaproValidator;

/**
 * 메일대상 밸리데이션
 * @author yckim
 * @since 2019. 12. 11.
 *
 */
@Component
public class EmailTargetInfoValidator implements AsaproValidator{

	@Autowired
	private EmailTargetInfoService emailTargetInfoService;
	
	@Override
	public boolean supports(Class<?> clazz) {
		return clazz.isAssignableFrom(EmailTargetInfo.class);
	}

	@Override
	public void validate(Object target, Errors errors) {
		throw new AsaproException("[ASAPRO] use other validate method. not this.");
	}

	@Override
	public void validate(Object target, Errors errors, String formMode) {
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "etId", null, "아이디는 필수입력입니다.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "etTitle", null, "제목은 필수입력입니다.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "etTarget", null, "수신자 메일은 필수입력입니다.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "etContents", null, "내용은 필수입력입니다.");
		
		EmailTargetInfo emailTargetInfoForm = (EmailTargetInfo)target;
		
		if("INSERT".equalsIgnoreCase(formMode)){
			//아이디 중복 방지
			EmailTargetInfo fromDB = emailTargetInfoService.getEmailTargetInfo(emailTargetInfoForm);
			if(fromDB != null){
				errors.rejectValue("etId", null, "이미 사용중인 아이디입니다.");
				return;
			} else {
				//정규식 체크
				if( !emailTargetInfoForm.getEtId().matches("[0-9a-zA-Z_]*") ){
					errors.rejectValue("etId", null, "아이디는 영소대문자, 숫자 그리고 언더바(_)만 사용가능 합니다.");
					return;
				}
			}
		}
	}

}
