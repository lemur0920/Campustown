/**
 * 
 */
package egovframework.com.asapro.code.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;

import egovframework.com.asapro.code.service.Code;
import egovframework.com.asapro.code.service.CodeService;
import egovframework.com.asapro.support.exception.AsaproException;
import egovframework.com.asapro.support.validation.AsaproValidator;

/**
 * 상세코드 밸리데이션
 * @author yckim
 * @since 2018. 4. 3.
 *
 */
@Component
public class CodeValidator implements AsaproValidator{

	@Autowired
	private CodeService codeService;
	
	@Override
	public boolean supports(Class<?> clazz) {
		return clazz.isAssignableFrom(Code.class);
	}

	@Override
	public void validate(Object target, Errors errors) {
		throw new AsaproException("[ASAPRO] use other validate method. not this.");
	}

	@Override
	public void validate(Object target, Errors errors, String formMode) {
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "codeId", null, "코드아이디는 필수입력입니다.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "codeCategory.catId", null, "코드분류를 선택해 주세요.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "codeName", null, "코드이름은 필수입력입니다.");
		
		Code codeForm = (Code)target;
		
		if("INSERT".equalsIgnoreCase(formMode)){
			Code fromDB = codeService.getCode(codeForm);
			if( fromDB != null ){
				errors.rejectValue("codeId", null, "이미 사용중인 코드 아이디 입니다.");
				return;
			} else {
				//정규식 체크
				if( !codeForm.getCodeId().matches("[0-9a-zA-Z_]*") ){
					errors.rejectValue("codeId", null, "코드아이디는 영소대문자, 숫자 그리고 언더바(_)만 사용가능 합니다.");
					return;
				}
			}
		}
	}

}
