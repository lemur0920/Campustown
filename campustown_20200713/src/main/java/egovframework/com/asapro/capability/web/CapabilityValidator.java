/**
 * 
 */
package egovframework.com.asapro.capability.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;

import egovframework.com.asapro.capability.service.Capability;
import egovframework.com.asapro.capability.service.CapabilityService;
import egovframework.com.asapro.support.exception.AsaproException;
import egovframework.com.asapro.support.validation.AsaproValidator;

/**
 * 권한 입력값 검증
 * @author yckim
 * @since 2018. 7. 17.
 *
 */
@Component
public class CapabilityValidator implements AsaproValidator{

	@Autowired
	private CapabilityService capabilityService;
	
	@Override
	public boolean supports(Class<?> clazz) {
		return clazz.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		throw new AsaproException("[ASAPRO] use other validate method. not this.");
	}

	@Override
	public void validate(Object target, Errors errors, String formMode) {
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "capId", null, "권한 아이디는 필수 입력입니다.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "capName", null, "권한 이름은 필수 입력입니다.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "capValue", null, "권한 값은 필수 입력입니다.");
		
		Capability capabilityForm = (Capability) target;
		
		if("INSERT".equalsIgnoreCase(formMode)){
			if(!capabilityForm.getCapId().matches("[a-zA-Z0-9_]*")){
				errors.rejectValue("capId", null, "영문, 숫자, 언더바(_)만 사용할 수 있습니다.");
				return;
			}
			
			Capability fromDB = capabilityService.getCapability(capabilityForm);
			if(fromDB != null){
				errors.rejectValue("capId", null, "이미 사용중인 권한 아이디 입니다.");
				return;
			}
		}
	}

}
