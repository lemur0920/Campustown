/**
 * 
 */
package egovframework.com.asapro.openapiinfo.web;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.IntegerValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;

import egovframework.com.asapro.openapiinfo.service.OpenApiInfo;
import egovframework.com.asapro.openapiinfo.service.OpenApiInfoService;
import egovframework.com.asapro.support.exception.AsaproException;
import egovframework.com.asapro.support.util.AsaproUtils;
import egovframework.com.asapro.support.validation.AsaproValidator;

/**
 * 오픈 api 정보 검증
 * @author yckim
 * @since 2019. 2. 12.
 *
 */
@Component
public class OpenApiInfoValidator implements AsaproValidator{

	@Autowired
	private OpenApiInfoService openApiInfoService;
	
	@Override
	public boolean supports(Class<?> clazz) {
		return clazz.isAssignableFrom(OpenApiInfo.class);
	}

	@Override
	public void validate(Object target, Errors errors) {
		throw new AsaproException("[ASAPRO] use other validate method. not this.");
	}

	@Override
	public void validate(Object target, Errors errors, String formMode) {
		OpenApiInfo openApiInfoForm = (OpenApiInfo)target;
		
		if("INSERT".equalsIgnoreCase(formMode) || "UPDATE".equalsIgnoreCase(formMode) ){
			
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "apiId", null, "서비스 영문명은 필수입력입니다.");
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "apiTitle", null, "서비스 한글명은 필수입력입니다.");
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "apiContent", null, "서비스 설명은 필수입력입니다.");
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "apiProvider", null, "api 제공 사이트는 필수입력입니다.");
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "apiDomain", null, "api 도메인은 필수입력입니다.");
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "apiKey", null, "api 키는 필수입력입니다.");
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "apiReturnType", null, "api 리턴 유형은 필수입력입니다.");
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "apiNumOfRow", null, "한번에 가져올 결과수는 필수입력입니다.");
			
		}
	}

}
