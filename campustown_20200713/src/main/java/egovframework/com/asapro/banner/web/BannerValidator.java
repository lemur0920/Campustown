/**
 * 
 */
package egovframework.com.asapro.banner.web;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import egovframework.com.asapro.banner.service.Banner;
import egovframework.com.asapro.interceptor.AdminInterceptor;
import egovframework.com.asapro.support.exception.AsaproException;
import egovframework.com.asapro.support.validation.AsaproValidator;

/**
 * 배너 입력값 검사
 * @author yckim
 * @since 2018. 7. 26.
 *
 */
@Component
public class BannerValidator implements AsaproValidator{
	private static final Logger LOGGER = LoggerFactory.getLogger(BannerValidator.class);
	
	@Override
	public boolean supports(Class<?> clazz) {
		return clazz.isAssignableFrom(Banner.class);
	}

	@Override
	public void validate(Object target, Errors errors) {
		throw new AsaproException("[ASAPRO] use other validate method. not this.");
	}
	
	@Override
	public void validate(Object target, Errors errors, String formMode) {
		Banner bannerForm = (Banner)target;
		boolean isUpdate = bannerForm.getBnId() != null && bannerForm.getBnId().intValue() > 0;
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "bnType", null, "유형은 필수선택입니다.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "bnName", null, "이름은 필수입력입니다.");
		
		if( !isUpdate &&  !(bannerForm.getThumb() != null && bannerForm.getThumb().getFileId() > 0) ){
			if( bannerForm.getBnImage() == null || bannerForm.getBnImage().isEmpty() ){
				errors.rejectValue("bnImage", null, "이미지는 필수입력입니다.");
			}
		}
		
		if( StringUtils.isNotBlank(bannerForm.getBnStartDate()) && StringUtils.isNotBlank(bannerForm.getBnEndDate()) ){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.KOREA);
			Date startDate = null;
			Date endDate = null;
			try {
				startDate = sdf.parse(bannerForm.getBnStartDate());
				endDate = sdf.parse(bannerForm.getBnEndDate()); 
			} catch (ParseException e) {
				LOGGER.error("[ParseException] Try/Catch... : "+ e.getMessage());
			}
			if( startDate.after(endDate) ){
				errors.rejectValue("bnStartDate", null, "시작일은 종료일 이전이어야 합니다.");
				errors.rejectValue("bnEndDate", null, "종료일은 시작일 이후여야 합니다.");
			}
		}
		
		//ValidationUtils.rejectIfEmptyOrWhitespace(errors, "bnLink", null, "링크는 필수입력입니다.");
		
	}

}
