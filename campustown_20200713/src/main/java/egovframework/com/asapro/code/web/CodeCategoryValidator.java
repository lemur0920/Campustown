/**
 * 
 */
package egovframework.com.asapro.code.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;

import egovframework.com.asapro.code.service.CodeCategory;
import egovframework.com.asapro.code.service.CodeService;
import egovframework.com.asapro.support.exception.AsaproException;
import egovframework.com.asapro.support.validation.AsaproValidator;

/**
 * 코드분류 밸리데이션
 * @author yckim
 * @since 2018. 4. 3.
 *
 */
@Component
public class CodeCategoryValidator implements AsaproValidator{

	@Autowired
	private CodeService codeService;
	
	@Override
	public boolean supports(Class<?> clazz) {
		return clazz.isAssignableFrom(CodeCategory.class);
	}

	@Override
	public void validate(Object target, Errors errors) {
		throw new AsaproException("[ASAPRO] use other validate method. not this.");
	}

	@Override
	public void validate(Object target, Errors errors, String formMode) {
		
		CodeCategory codeCategory = (CodeCategory) target;
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "catId", null, "분류 아이디는 필수입력입니다.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "catName", null, "분류 이름는 필수입력입니다.");
		
		if("INSERT".equalsIgnoreCase(formMode)){
			//분류아이디 중복 방지
			CodeCategory fromDB = codeService.getCodeCategory(codeCategory.getCatId());
			if(fromDB != null){
				errors.rejectValue("catId", null, "이미 사용중인 분류 아이디입니다.");
				return;
			} else {
				//정규식 체크
				if( !codeCategory.getCatId().matches("[0-9a-zA-Z_]*") ){
					errors.rejectValue("catId", null, "분류 아이디는 영소대문자, 숫자 그리고 언더바(_)만 사용가능 합니다.");
					return;
				}
			}
			
		}
		
	}

}
