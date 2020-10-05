/**
 * 
 */
package egovframework.com.asapro.archive.customtype.advertising.web;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;

import egovframework.com.asapro.support.exception.AsaproException;
import egovframework.com.asapro.support.util.AsaproUtils;
import egovframework.com.asapro.support.validation.AsaproValidator;
import egovframework.com.asapro.archive.customtype.advertising.service.Advertising;
import egovframework.com.asapro.archive.customtype.advertising.service.AdvertisingService;
import egovframework.com.asapro.archive.service.Archive;
import egovframework.com.asapro.archive.service.ArchiveCategory;
import egovframework.com.asapro.code.service.CodeCategory;
import egovframework.com.asapro.code.service.CodeService;

/**
 * 광고자료 정보 검증
 * @author yckim
 * @since 2019. 9. 30.
 *
 */
@Component
public class AdvertisingValidator implements AsaproValidator{

	@Autowired
	private AdvertisingService advertisingService;
	
	@Autowired
	private CodeService codeService;
	
	@Override
	public boolean supports(Class<?> clazz) {
		return clazz.isAssignableFrom(Advertising.class);
	}

	@Override
	public void validate(Object target, Errors errors) {
		throw new AsaproException("[ASAPRO] use other validate method. not this.");
	}

	@Override
	public void validate(Object target, Errors errors, String formMode) {
		Advertising advertisingForm = (Advertising)target;
		ArchiveCategory archiveCategory = advertisingForm.getArchiveCategory(); 
		
		//메타코드 체크
		if( archiveCategory != null ){
			
			//지정된 메타코드1 있을때
			if( StringUtils.isNotBlank(archiveCategory.getCatMetaCode1()) ){
				CodeCategory codeCategory = codeService.getCodeCategory(archiveCategory.getCatMetaCode1() );
				//실제로 코드카테고리 존재하면
				if( codeCategory != null ){
					if( StringUtils.isBlank(advertisingForm.getArcMetaCode1()) ){
						errors.rejectValue("arcMetaCode1", null, codeCategory.getCatName() + "은(는) 필수선택입니다.");
					}
				}
			}
			
			//지정된 메타코드2 있을때
			if( StringUtils.isNotBlank(archiveCategory.getCatMetaCode2()) ){
				CodeCategory codeCategory = codeService.getCodeCategory(archiveCategory.getCatMetaCode2() );
				//실제로 코드카테고리 존재하면
				if( codeCategory != null ){
					if( StringUtils.isBlank(advertisingForm.getArcMetaCode2()) ){
						errors.rejectValue("arcMetaCode2", null, codeCategory.getCatName() + "은(는) 필수선택입니다.");
					}
				}
			}
			
			//지정된 메타코드3 있을때
			/*if( StringUtils.isNotBlank(archiveCategory.getCatMetaCode3()) ){
				CodeCategory codeCategory = codeService.getCodeCategory(archiveCategory.getCatMetaCode3() );
				//실제로 코드카테고리 존재하면
				if( codeCategory != null ){
					if( StringUtils.isBlank(advertisingForm.getArcMetaCode3()) ){
						errors.rejectValue("arcMetaCode3", null, codeCategory.getCatName() + "은(는) 필수선택입니다.");
					}
				}
			}*/
			/*if("ASA".equals(advertisingForm.getSitePrefix()) ){
				if( StringUtils.isNotBlank(archiveCategory.getCatMetaCode2()) ){
					
					if( StringUtils.isBlank(advertisingForm.getArcMetaCode3()) ){
						errors.rejectValue("arcMetaCode3", null, "공익광고 소분류는 필수선택입니다.");
					}
				}
			}*/
			if( StringUtils.isNotBlank(archiveCategory.getCatMetaCode2()) ){
				
				if( StringUtils.isBlank(advertisingForm.getArcMetaCode3()) ){
					errors.rejectValue("arcMetaCode3", null, "공익광고 소분류는 필수선택입니다.");
				}
			}
		}
			
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "arcTitle", null, "제목은 필수입력입니다.");
		//ValidationUtils.rejectIfEmptyOrWhitespace(errors, "arcContent", null, "내용은 필수입력입니다.");
		
		//대표이미지 대체텍스트 체크
		if( advertisingForm.getArcThumbFile() != null && advertisingForm.getArcThumbFile().getSize() > 0 ){
			//대체텍스트 입력체크
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "arcThumbText", null, "대체텍스트는 필수입력입니다.");
		}
		
		//메인사이트의 광고정보만 임시처리 - 다른방법이 없어서서
		if("ASA".equals(advertisingForm.getSitePrefix()) ){
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "adtManufactureYear", null, "제작년도는 필수선택입니다.");
			
			if( advertisingForm.getThumb() == null || advertisingForm.getThumb().getFileId() <= 0 ){
				if( advertisingForm.getArcThumbFile() == null || advertisingForm.getArcThumbFile().getSize() <= 0 ){
					errors.rejectValue("arcThumbFile", null, "이미지첨부는 필수입니다.");
				}
			}
		}
			
			
	}

}
