/**
 * 
 */
package egovframework.com.asapro.archive.customtype.space.web;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;

import egovframework.com.asapro.support.exception.AsaproException;
import egovframework.com.asapro.support.util.AsaproUtils;
import egovframework.com.asapro.support.validation.AsaproValidator;
import egovframework.com.asapro.archive.customtype.space.service.Space;
import egovframework.com.asapro.archive.customtype.space.service.SpaceService;
import egovframework.com.asapro.archive.service.Archive;
import egovframework.com.asapro.archive.service.ArchiveCategory;
import egovframework.com.asapro.code.service.CodeCategory;
import egovframework.com.asapro.code.service.CodeService;
import egovframework.com.asapro.organization.service.Department;
import egovframework.com.asapro.organization.service.DepartmentSearch;
import egovframework.com.asapro.organization.service.OrganizationService;

/**
 * 공간정보 정보 검증
 * @author yckim
 * @since 2019. 2. 26.
 *
 */
@Component
public class SpaceValidator implements AsaproValidator{

	@Autowired
	private SpaceService spaceService;
	
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
		Space spaceForm = (Space)target;
		ArchiveCategory archiveCategory = spaceForm.getArchiveCategory(); 
		
		//메타코드 체크
		if( archiveCategory != null ){
			
			//지정된 메타코드1 있을때
			if( StringUtils.isNotBlank(archiveCategory.getCatMetaCode1()) ){
				CodeCategory codeCategory = codeService.getCodeCategory(archiveCategory.getCatMetaCode1() );
				//실제로 코드카테고리 존재하면
				if( codeCategory != null ){
					if( StringUtils.isBlank(spaceForm.getArcMetaCode1()) ){
						errors.rejectValue("arcMetaCode1", null, codeCategory.getCatName() + "은(는) 필수선택입니다.");
					}
				}
			}
			
			//지정된 메타코드2 있을때
			if( StringUtils.isNotBlank(archiveCategory.getCatMetaCode2()) ){
				CodeCategory codeCategory = codeService.getCodeCategory(archiveCategory.getCatMetaCode2() );
				//실제로 코드카테고리 존재하면
				if( codeCategory != null ){
					if( StringUtils.isBlank(spaceForm.getArcMetaCode2()) ){
						errors.rejectValue("arcMetaCode2", null, codeCategory.getCatName() + "은(는) 필수선택입니다.");
					}
				}
			}
			//ValidationUtils.rejectIfEmptyOrWhitespace(errors, "arcMetaCode2", null, "시군구는 필수선택입니다.");
			
			//지정된 메타코드3 있을때
			if( StringUtils.isNotBlank(archiveCategory.getCatMetaCode3()) ){
				CodeCategory codeCategory = codeService.getCodeCategory(archiveCategory.getCatMetaCode3() );
				//실제로 코드카테고리 존재하면
				if( codeCategory != null ){
					if( StringUtils.isBlank(spaceForm.getArcMetaCode3()) ){
						errors.rejectValue("arcMetaCode3", null, codeCategory.getCatName() + "은(는) 필수선택입니다.");
					}
				}
			}
		}
		
		
		
		//이메일 형식 체크
		if( StringUtils.isNotBlank(spaceForm.getSpaEmail()) ){
			if( !AsaproUtils.isEmail(spaceForm.getSpaEmail()) ){
				errors.rejectValue("spaEmail", null, "이메일을 형식에 맞게 입력해 주세요.");
			}
		}
		
		
		//ValidationUtils.rejectIfEmptyOrWhitespace(errors, "arcContent", null, "내용은 필수입력입니다.");
		
		//ValidationUtils.rejectIfEmptyOrWhitespace(errors, "spaLocation", null, "위치는 필수입력입니다.");
		//ValidationUtils.rejectIfEmptyOrWhitespace(errors, "spaTel", null, "연락처는 필수입력입니다.");
		//ValidationUtils.rejectIfEmptyOrWhitespace(errors, "spaUseHours", null, "이용시간은 필수입력입니다.");
		//ValidationUtils.rejectIfEmptyOrWhitespace(errors, "spaBusinessHours", null, "업무시간은 필수입력입니다.");
		//ValidationUtils.rejectIfEmptyOrWhitespace(errors, "spaAgency", null, "운영기관은 필수입력입니다.");
		
		//대표이미지 대체텍스트 체크
		if( spaceForm.getArcThumbFile() != null && spaceForm.getArcThumbFile().getSize() > 0 ){
			//대체텍스트 입력체크
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "arcThumbText", null, "대체텍스트는 필수입력입니다.");
		}
			
	}

}
