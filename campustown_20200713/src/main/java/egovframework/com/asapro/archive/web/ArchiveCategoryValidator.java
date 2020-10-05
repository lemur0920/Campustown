/**
 * 
 */
package egovframework.com.asapro.archive.web;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;

import egovframework.com.asapro.support.exception.AsaproException;
import egovframework.com.asapro.support.util.AsaproUtils;
import egovframework.com.asapro.support.validation.AsaproValidator;
import egovframework.com.asapro.archive.service.ArchiveCategory;
import egovframework.com.asapro.archive.service.ArchiveService;

/**
 * 아카이브 카테고리 정보 검증
 * @author yckim
 * @since 2018. 12. 19.
 *
 */
@Component
public class ArchiveCategoryValidator implements AsaproValidator{

	@Autowired
	private ArchiveService archiveService;
	
	@Override
	public boolean supports(Class<?> clazz) {
		return clazz.isAssignableFrom(ArchiveCategory.class);
	}

	@Override
	public void validate(Object target, Errors errors) {
		throw new AsaproException("[ASAPRO] use other validate method. not this.");
	}

	@Override
	public void validate(Object target, Errors errors, String formMode) {
		ArchiveCategory archiveCategoryForm = (ArchiveCategory)target;
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "catId", null, "카테고리 아이디는 필수입력입니다.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "catName", null, "카테고리 이름은 필수입력입니다.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "catCustomType", null, "아카이브 타입은 필수선택입니다.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "catUserListTemplate", null, "템플릿은 필수선택입니다.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "catUserViewTemplate", null, "템플릿은 필수선택입니다.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "catAdminListTemplate", null, "템플릿은 필수선택입니다.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "catAdminFormTemplate", null, "템플릿은 필수선택입니다.");
		
		if( "INSERT".equals(formMode) ){
			//카테고리 아이디 중복 체크
			if( StringUtils.isNotBlank(archiveCategoryForm.getCatId()) ){
				ArchiveCategory fromDB = archiveService.getArchiveCategory(archiveCategoryForm);
				if( fromDB != null ){
					errors.rejectValue("catId", null, "사용중인 카테고리 아이디입니다.");
				}
				if( !archiveCategoryForm.getCatId().matches("[0-9a-z_]*") ){
					errors.rejectValue("catId", null, "카테고리 아이디는 영어소문자, 숫자 그리고 언더바(_)만 사용가능 합니다.");
				}
				if( archiveCategoryForm.getCatId().length() < 4 || archiveCategoryForm.getCatId().length() > 25 ){
					errors.rejectValue("catId", null, "길이는 4자 이상, 25자 이하여야 합니다");
				}
			}
		}
	}

}
