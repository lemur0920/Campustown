/**
 * 
 */
package egovframework.com.asapro.organization.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;

import egovframework.com.asapro.code.service.Code;
import egovframework.com.asapro.organization.service.Organization;
import egovframework.com.asapro.organization.service.OrganizationService;
import egovframework.com.asapro.support.exception.AsaproException;
import egovframework.com.asapro.support.validation.AsaproValidator;


/**
 * 부서 입력값 검증
 * 
 * @author yckim
 * @since 2018. 5. 11.
 */
@Component
public class OrganizationValidator implements AsaproValidator{

	@Autowired
	private OrganizationService organizationService;
	
	@Override
	public boolean supports(Class<?> clazz) {
		return clazz.isAssignableFrom(Organization.class);
	}

	@Override
	public void validate(Object target, Errors errors) {
		throw new AsaproException("[ASAPRO] use other validate method. not this.");
	}
	
	@Override
	public void validate(Object target, Errors errors, String formMode) {
		
		Organization organizationForm = (Organization)target;
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "orgId", null, "기관코드는 필수입력입니다.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "orgName", null, "기관이름은 필수입력입니다.");
		
		if("INSERT".equalsIgnoreCase(formMode)){
			Organization fromDB = organizationService.getOrganization(organizationForm);
			if( fromDB != null ){
				errors.rejectValue("orgId", null, "이미 사용중인 기관코드 입니다.");
				return;
			} else {
				//정규식 체크
				if( !organizationForm.getOrgId().matches("[0-9a-zA-Z_]*") ){
					errors.rejectValue("orgId", null, "기관아이디는 영소대문자, 숫자, '_'만 사용가능 합니다.");
					return;
				}
			}
		}
	}

}
