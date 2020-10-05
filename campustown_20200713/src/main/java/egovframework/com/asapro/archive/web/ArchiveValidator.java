/**
 * 
 */
package egovframework.com.asapro.archive.web;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;

import egovframework.com.asapro.support.exception.AsaproException;
import egovframework.com.asapro.support.util.AsaproUtils;
import egovframework.com.asapro.support.validation.AsaproValidator;
import egovframework.com.asapro.archive.service.Archive;
import egovframework.com.asapro.archive.service.ArchiveCategory;
import egovframework.com.asapro.archive.service.ArchiveService;
import egovframework.com.asapro.code.service.CodeCategory;
import egovframework.com.asapro.code.service.CodeService;
import egovframework.com.asapro.organization.service.Department;
import egovframework.com.asapro.organization.service.DepartmentSearch;
import egovframework.com.asapro.organization.service.OrganizationService;

/**
 * 아카이브 정보 검증
 * @author yckim
 * @since 2018. 12. 19.
 *
 */
@Component
public class ArchiveValidator implements AsaproValidator{

	@Autowired
	private ArchiveService archiveService;
	
	@Autowired
	private OrganizationService organizationService;
	
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
		Archive archiveForm = (Archive)target;
		ArchiveCategory archiveCategory = archiveForm.getArchiveCategory(); 
		
		//메타코드 체크
		if( archiveCategory != null ){
			
			//지정된 메타코드1 있을때
			if( StringUtils.isNotBlank(archiveCategory.getCatMetaCode1()) ){
				CodeCategory codeCategory = codeService.getCodeCategory(archiveCategory.getCatMetaCode1() );
				//실제로 코드카테고리 존재하면
				if( codeCategory != null ){
					if( StringUtils.isBlank(archiveForm.getArcMetaCode1()) ){
						errors.rejectValue("arcMetaCode1", null, codeCategory.getCatName() + "은(는) 필수선택입니다.");
					}
				}
			}
			
			//코바코 에서만 해당
			//메타코드1 (광고유형)이 인쇄광고인 경우만 메타코드2,3을 확인
			if(StringUtils.isNotBlank(archiveForm.getArcMetaCode1()) && "print".equals(archiveForm.getArcMetaCode1()) ){
				
				//지정된 메타코드2 있을때
				if( StringUtils.isNotBlank(archiveCategory.getCatMetaCode2()) ){
					CodeCategory codeCategory = codeService.getCodeCategory(archiveCategory.getCatMetaCode2() );
					//실제로 코드카테고리 존재하면
					if( codeCategory != null ){
						if( StringUtils.isBlank(archiveForm.getArcMetaCode2()) ){
							errors.rejectValue("arcMetaCode1", null, codeCategory.getCatName() + "은(는) 필수선택입니다.");
						}
					}
				}
				//지정된 메타코드3 있을때
				if( StringUtils.isNotBlank(archiveCategory.getCatMetaCode3()) ){
					CodeCategory codeCategory = codeService.getCodeCategory(archiveCategory.getCatMetaCode3() );
					//실제로 코드카테고리 존재하면
					if( codeCategory != null ){
						if( StringUtils.isBlank(archiveForm.getArcMetaCode3()) ){
							errors.rejectValue("arcMetaCode1", null, codeCategory.getCatName() + "은(는) 필수선택입니다.");
						}
					}
				}
			}
			
		}
		
		// 대학교 코드값 체크
		DepartmentSearch departmentSearch = new DepartmentSearch();
		departmentSearch.setPaging(false);
		departmentSearch.setDepUse(true);
		departmentSearch.setOrgId("university");
		
		List<Department> departmentList = organizationService.getDepartmentList(departmentSearch);
		boolean rtnChk = false;
		if( StringUtils.isNotBlank(archiveForm.getArcUnivId()) ){
			for(int i=0; i<departmentList.size(); i++){
				if(departmentList.get(i).getDepId().equals(archiveForm.getArcUnivId())){
					rtnChk = true;
					break;
				}
			}
		}
		
		if(rtnChk == false){
			errors.rejectValue("arcUnivId", null, "캠퍼스타운은(는) 필수선택입니다.");
		}
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "arcTitle", null, "입주공간명은 필수입력입니다.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "arcContent", null, "내용은 필수입력입니다.");
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "arcYear", null, "제작년도 필수입력입니다.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "arcAdvertiser", null, "공고주는 필수입력입니다.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "arcProduct", null, "제품명 필수입력입니다.");
		
		//대표이미지 대체텍스트 체크
		if( archiveForm.getArcThumbFile() != null && archiveForm.getArcThumbFile().getSize() > 0 ){
			//대체텍스트 입력체크
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "arcThumbText", null, "대체텍스트는 필수입력입니다.");
		}
			
	}

}
