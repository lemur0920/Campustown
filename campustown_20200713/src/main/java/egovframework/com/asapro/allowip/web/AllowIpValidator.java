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
import egovframework.com.asapro.support.exception.AsaproException;
import egovframework.com.asapro.support.util.AsaproUtils;
import egovframework.com.asapro.support.validation.AsaproValidator;

/**
 * 접속허용 IP 코드 밸리데이션
 * @author yckim
 * @since 2018. 7. 17.
 *
 */
@Component
public class AllowIpValidator implements AsaproValidator{

	@Autowired
	private AllowIpService allowIpService;
	
	@Override
	public boolean supports(Class<?> clazz) {
		return clazz.isAssignableFrom(AllowIp.class);
	}

	@Override
	public void validate(Object target, Errors errors) {
		throw new AsaproException("[ASAPRO] use other validate method. not this.");
	}

	@Override
	public void validate(Object target, Errors errors, String formMode) {
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "ipType", null, "아이피관리 구분은 필수선택입니다.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "ipRuleName", null, "규칙명입력은 필수입니다.");
		//ValidationUtils.rejectIfEmptyOrWhitespace(errors, "ipStart", null, "입력은 필수입니다.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "ipStart1", null, "입력은 필수입니다.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "ipStart2", null, "입력은 필수입니다.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "ipStart3", null, "입력은 필수입니다.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "ipStart4", null, "입력은 필수입니다.");
		
		AllowIp allowIpForm = (AllowIp)target;
		
		//정규식 체크
		/*
		if(StringUtils.isNotBlank(allowIpForm.getIpStart()) ){
			if( !AsaproUtils.isIPAdress(allowIpForm.getIpStart()) ){
				errors.rejectValue("ipStart", null, "아이피유형에 맞지 않습니다.");
			}
		}*/
		if(StringUtils.isNotBlank(allowIpForm.getIpStart1()) ){
			if( !allowIpForm.getIpStart1().matches("^([01]?\\d\\d?|2[0-4]\\d|25[0-5]|\\*)$") ){
				errors.rejectValue("ipStart1", null, "아이피유형에 맞지 않습니다. 255 이하 수, *만  입력가능합니다.");
			}
			if( allowIpForm.getIpStart1().matches("^([01]?\\d\\d?|2[0-4]\\d|25[0-5])$") ){
				allowIpForm.setIpStart1(String.valueOf(Integer.parseInt(allowIpForm.getIpStart1())) );
			}
		}
		if(StringUtils.isNotBlank(allowIpForm.getIpStart2()) ){
			if( !allowIpForm.getIpStart2().matches("^([01]?\\d\\d?|2[0-4]\\d|25[0-5]|\\*)$") ){
				errors.rejectValue("ipStart2", null, "아이피유형에 맞지 않습니다. 255 이하 수, * 만 입력가능합니다.");
			}
			if( allowIpForm.getIpStart2().matches("^([01]?\\d\\d?|2[0-4]\\d|25[0-5])$") ){
				allowIpForm.setIpStart2(String.valueOf(Integer.parseInt(allowIpForm.getIpStart2())) );
			}
		}
		if(StringUtils.isNotBlank(allowIpForm.getIpStart3()) ){
			if( !allowIpForm.getIpStart3().matches("^([01]?\\d\\d?|2[0-4]\\d|25[0-5]|\\*)$") ){
				errors.rejectValue("ipStart3", null, "아이피유형에 맞지 않습니다. 255 이하 수, * 만 입력가능합니다.");
			}
			if( allowIpForm.getIpStart3().matches("^([01]?\\d\\d?|2[0-4]\\d|25[0-5])$") ){
				allowIpForm.setIpStart3(String.valueOf(Integer.parseInt(allowIpForm.getIpStart3())) );
			}
		}
		if(StringUtils.isNotBlank(allowIpForm.getIpStart4()) ){
			if( !allowIpForm.getIpStart4().matches("^([01]?\\d\\d?|2[0-4]\\d|25[0-5]|\\*)$") ){
				errors.rejectValue("ipStart4", null, "아이피유형에 맞지 않습니다. 255 이하 수, * 만 입력가능합니다.");
			}
			if( allowIpForm.getIpStart4().matches("^([01]?\\d\\d?|2[0-4]\\d|25[0-5])$") ){
				allowIpForm.setIpStart4(String.valueOf(Integer.parseInt(allowIpForm.getIpStart4())) );
			}
		}
		
		if(StringUtils.isNotBlank(allowIpForm.getIpEnd()) ){
			if( !allowIpForm.getIpEnd().matches("^([01]?\\d\\d?|2[0-4]\\d|25[0-5])$") ){
				errors.rejectValue("ipEnd", null, "아이피유형에 맞지 않습니다. 255 이하 수만 입력가능합니다.");
			}
			
			if( allowIpForm.getIpStart4().matches("^(\\*)$") ){
				errors.rejectValue("ipEnd", null, "시작아이피가 * 이면 구간을 지정할 수 없습니다.");
			}
			if( allowIpForm.getIpStart4().matches("^([01]?\\d\\d?|2[0-4]\\d|25[0-5])$") ){
				if(Integer.parseInt(allowIpForm.getIpEnd()) <= Integer.parseInt(allowIpForm.getIpStart4()) ){
					errors.rejectValue("ipEnd", null, "시작아이피보다 큰 값을 입력하세요");
				}
			}
		}
		
		/*
		if(StringUtils.isNotBlank(allowIpForm.getIpEnd1()) ){
			if( !allowIpForm.getIpEnd1().matches("^([01]?\\d\\d?|2[0-4]\\d|25[0-5])$") ){
				errors.rejectValue("ipEnd1", null, "아이피유형에 맞지 않습니다. 255 이하 수만  입력가능합니다.");
			}else{
				allowIpForm.setIpEnd1(String.valueOf(Integer.parseInt(allowIpForm.getIpEnd1())) );
			}
		}
		if(StringUtils.isNotBlank(allowIpForm.getIpEnd2()) ){
			if( !allowIpForm.getIpEnd2().matches("^([01]?\\d\\d?|2[0-4]\\d|25[0-5])$") ){
				errors.rejectValue("ipEnd2", null, "아이피유형에 맞지 않습니다. 255 이하 수만  입력가능합니다.");
			}else{
				allowIpForm.setIpEnd2(String.valueOf(Integer.parseInt(allowIpForm.getIpEnd2())) );
			}
		}
		if(StringUtils.isNotBlank(allowIpForm.getIpEnd3()) ){
			if( !allowIpForm.getIpEnd3().matches("^([01]?\\d\\d?|2[0-4]\\d|25[0-5])$") ){
				errors.rejectValue("ipEnd3", null, "아이피유형에 맞지 않습니다. 255 이하 수만  입력가능합니다.");
			}else{
				allowIpForm.setIpEnd3(String.valueOf(Integer.parseInt(allowIpForm.getIpEnd3())) );
			}
		}
		if(StringUtils.isNotBlank(allowIpForm.getIpEnd4()) ){
			if( !allowIpForm.getIpEnd4().matches("^([01]?\\d\\d?|2[0-4]\\d|25[0-5])$") ){
				errors.rejectValue("ipEnd4", null, "아이피유형에 맞지 않습니다. 255 이하 수만  입력가능합니다.");
			}else{
				allowIpForm.setIpEnd4(String.valueOf(Integer.parseInt(allowIpForm.getIpEnd4())) );
			}
		}
		*/
	}

}
