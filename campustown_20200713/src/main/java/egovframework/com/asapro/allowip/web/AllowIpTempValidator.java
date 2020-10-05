/**
 * 
 */
package egovframework.com.asapro.allowip.web;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;

import egovframework.com.asapro.allowip.service.AllowIp;
import egovframework.com.asapro.allowip.service.AllowIpService;
import egovframework.com.asapro.allowip.service.AllowIpTemp;
import egovframework.com.asapro.support.exception.AsaproException;
import egovframework.com.asapro.support.util.AsaproUtils;
import egovframework.com.asapro.support.validation.AsaproValidator;

/**
 * 관리자 인증요청 밸리데이션
 * @author yckim
 * @since 2019. 5. 21.
 *
 */
@Component
public class AllowIpTempValidator implements AsaproValidator{

	@Autowired
	private AllowIpService allowIpService;
	
	@Override
	public boolean supports(Class<?> clazz) {
		return clazz.isAssignableFrom(AllowIpTemp.class);
	}

	@Override
	public void validate(Object target, Errors errors) {
		throw new AsaproException("[ASAPRO] use other validate method. not this.");
	}

	@Override
	public void validate(Object target, Errors errors, String formMode) {
		AllowIpTemp allowIpTempForm = (AllowIpTemp)target;
		//인증요청
		if("REQUEST".equals(formMode) ){
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "adminId", null, "아이디는 필수입력입니다.");
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "adminPassword", null, "비밀번호는 필수입력입니다.");
		}
		//sms 인증번호 전송
		else if("CERIT_NUM".equals(formMode) ){
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "certiNum", null, "인증번호를 입력하세요");
		}
		//sms 인증번호 재발송
		else if("RE_SMS_CERIT_NUM".equals(formMode) ){
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "adminId", null, "아이디가 없습니다.");
		}
		
		
	}

}
