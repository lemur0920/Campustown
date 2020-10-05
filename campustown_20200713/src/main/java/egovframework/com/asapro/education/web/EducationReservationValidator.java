/**
 * 
 */
package egovframework.com.asapro.education.web;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;

import egovframework.com.asapro.support.exception.AsaproException;
import egovframework.com.asapro.support.util.AsaproUtils;
import egovframework.com.asapro.support.validation.AsaproValidator;
import egovframework.com.asapro.education.service.EducationReservation;
import egovframework.com.asapro.education.service.EducationService;

/**
 * 교육프로그램 신청정보 검증
 * @author yckim
 * @since 2018. 12. 17.
 *
 */
@Component
public class EducationReservationValidator implements AsaproValidator{

	@Autowired
	private EducationService educationService;
	
	@Override
	public boolean supports(Class<?> clazz) {
		return clazz.isAssignableFrom(EducationReservation.class);
	}

	@Override
	public void validate(Object target, Errors errors) {
		throw new AsaproException("[ASAPRO] use other validate method. not this.");
	}

	@Override
	public void validate(Object target, Errors errors, String formMode) {
		EducationReservation educationReservationForm = (EducationReservation)target;
		
		if("INSERT".equalsIgnoreCase(formMode) || "UPDATE".equalsIgnoreCase(formMode) ){
			
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "reservName", null, "신청자명은 필수입력입니다.");
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "reservGroup", null, "신청단체명 필수입력입니다.");
			
			//신청자 휴대폰번호
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "reservHp1", null, "휴대폰번호 1은 필수입력입니다.");
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "reservHp2", null, "휴대폰번호 2는 필수입력입니다.");
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "reservHp3", null, "휴대폰번호 3는 필수입력입니다.");
			if( StringUtils.isNotBlank(educationReservationForm.getReservHp2()) ){
				if(!AsaproUtils.isMobile(educationReservationForm.getReservHp2(),"MOBILE2") ){
					errors.rejectValue("reservHp2", null, "휴대폰번호 중간자리는 숫자 3자리 또는 숫자 4자리 형태로 입력해야 합니다.");
				}
			}
			if( StringUtils.isNotBlank(educationReservationForm.getReservHp3()) ){
				if(!AsaproUtils.isMobile(educationReservationForm.getReservHp3(),"MOBILE3") ){
					errors.rejectValue("reservHp3", null, "휴대폰번호 끝자리는 숫자 4자리 형태로 입력해야 합니다.");
				}
			}
			
			//이메일 체크
			//ValidationUtils.rejectIfEmptyOrWhitespace(errors, "reservEmail", null, "이메일 주소는 필수입력입니다.");
			if( StringUtils.isNotBlank(educationReservationForm.getReservEmail()) ){
				if( !AsaproUtils.isEmail(educationReservationForm.getReservEmail()) ){
					errors.rejectValue("reservEmail", null, "이메일을 형식에 맞게 입력해 주세요.");
				}
			}
			
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "reservAddress", null, "주소는 필수입력입니다.");
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "reservPeople", null, "인원은 필수입력입니다.");
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "reservDate", null, "교육프로그램일은 필수입력입니다.");
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "reservStime", null, "교육프로그램시작시간은 필수입력입니다.");
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "reservEtime", null, "교육프로그램종료시간은 필수입력입니다.");
			
			if("UPDATE".equalsIgnoreCase(formMode) ){
				ValidationUtils.rejectIfEmptyOrWhitespace(errors, "reservStatus", null, "신청상태는 필수입력입니다.");
				
			}
			
		}else if("AGREE".equalsIgnoreCase(formMode) ){
			if( educationReservationForm.getReservAgree() == null || !educationReservationForm.getReservAgree() ){
				errors.rejectValue("reservAgree", null, "개인정보 수집 이용 동의에 모두 동의하셔야 합니다.");
			}
		}
	}

}
