/**
 * 
 */
package egovframework.com.asapro.archive.customtype.policy.web;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;

import egovframework.com.asapro.support.exception.AsaproException;
import egovframework.com.asapro.support.util.AsaproUtils;
import egovframework.com.asapro.support.validation.AsaproValidator;
import egovframework.com.asapro.archive.customtype.policy.service.Policy;
import egovframework.com.asapro.archive.customtype.policy.service.PolicyService;
import egovframework.com.asapro.archive.service.Archive;
import egovframework.com.asapro.archive.service.ArchiveCategory;
import egovframework.com.asapro.code.service.CodeCategory;
import egovframework.com.asapro.code.service.CodeService;

/**
 * 정책자료 정보 검증
 * @author yckim
 * @since 2019. 12. 27.
 *
 */
@Component
public class PolicyValidator implements AsaproValidator{

	@Autowired
	private PolicyService policyService;
	
	@Autowired
	private CodeService codeService;
	
	@Override
	public boolean supports(Class<?> clazz) {
		return clazz.isAssignableFrom(Archive.class);
	}

	@Override
	public void validate(Object target, Errors errors) {
		throw new AsaproException("[ASAPRO] use other validate method. not this.");
	}

	@Override
	public void validate(Object target, Errors errors, String formMode) {
		Policy policyForm = (Policy)target;
		ArchiveCategory archiveCategory = policyForm.getArchiveCategory(); 
		
		//메타코드 체크
		if( archiveCategory != null ){
			
			//지정된 메타코드1 있을때
			if( StringUtils.isNotBlank(archiveCategory.getCatMetaCode1()) ){
				CodeCategory codeCategory = codeService.getCodeCategory(archiveCategory.getCatMetaCode1() );
				//실제로 코드카테고리 존재하면
				if( codeCategory != null ){
					if( StringUtils.isBlank(policyForm.getArcMetaCode1()) ){
						errors.rejectValue("arcMetaCode1", null, codeCategory.getCatName() + "은(는) 필수선택입니다.");
					}
				}
			}
			
			//지정된 메타코드2 있을때
			if( StringUtils.isNotBlank(archiveCategory.getCatMetaCode2()) ){
				CodeCategory codeCategory = codeService.getCodeCategory(archiveCategory.getCatMetaCode2() );
				//실제로 코드카테고리 존재하면
				if( codeCategory != null ){
					if( StringUtils.isBlank(policyForm.getArcMetaCode2()) ){
						errors.rejectValue("arcMetaCode2", null, codeCategory.getCatName() + "은(는) 필수선택입니다.");
					}
				}
			}
			
			//지정된 메타코드3 있을때
			if( StringUtils.isNotBlank(archiveCategory.getCatMetaCode3()) ){
				CodeCategory codeCategory = codeService.getCodeCategory(archiveCategory.getCatMetaCode3() );
				//실제로 코드카테고리 존재하면
				if( codeCategory != null ){
					if( StringUtils.isBlank(policyForm.getArcMetaCode3()) ){
						errors.rejectValue("arcMetaCode3", null, codeCategory.getCatName() + "은(는) 필수선택입니다.");
					}
				}
			}
		}
			
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "arcTitle", null, "제목은 필수입력입니다.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "arcContent", null, "내용은 필수입력입니다.");
		
		
		//대표이미지 대체텍스트 체크
		if( policyForm.getArcThumbFile() != null && policyForm.getArcThumbFile().getSize() > 0 ){
			//대체텍스트 입력체크
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "arcThumbText", null, "대체텍스트는 필수입력입니다.");
		}
			
	}

}
