/**
 * 
 */
package egovframework.com.asapro.allowmac.web;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;

import egovframework.com.asapro.allowmac.service.AllowMac;
import egovframework.com.asapro.allowmac.service.AllowMacService;
import egovframework.com.asapro.support.exception.AsaproException;
import egovframework.com.asapro.support.util.AsaproUtils;
import egovframework.com.asapro.support.validation.AsaproValidator;

/**
 * 접속허용 Mac Address 코드 밸리데이션
 * @author yckim
 * @since 2018. 7. 17.
 *
 */
@Component
public class AllowMacValidator implements AsaproValidator{

	@Autowired
	private AllowMacService allowMacService;
	
	@Override
	public boolean supports(Class<?> clazz) {
		return clazz.isAssignableFrom(AllowMac.class);
	}

	@Override
	public void validate(Object target, Errors errors) {
		throw new AsaproException("[ASAPRO] use other validate method. not this.");
	}

	@Override
	public void validate(Object target, Errors errors, String formMode) {
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "macType", null, "아이피관리 구분은 필수선택입니다.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "macRuleName", null, "규칙명입력은 필수입니다.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "macAddress", null, "Mac 주소는 필수입니다.");
		
		AllowMac allowMacForm = (AllowMac)target;
		
		//정규식 체크
		/*
		if(StringUtils.isNotBlank(allowMacForm.getMacStart()) ){
			if( !AsaproUtils.isIPAdress(allowMacForm.getMacStart()) ){
				errors.rejectValue("ipStart", null, "아이피유형에 맞지 않습니다.");
			}
		}*/
	}

}
