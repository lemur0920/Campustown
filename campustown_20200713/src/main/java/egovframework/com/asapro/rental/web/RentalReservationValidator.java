/**
 * 
 */
package egovframework.com.asapro.rental.web;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;

import egovframework.com.asapro.rental.service.RentReservTime;
import egovframework.com.asapro.rental.service.RentalReservationInfo;
import egovframework.com.asapro.support.exception.AsaproException;
import egovframework.com.asapro.support.util.AsaproUtils;
import egovframework.com.asapro.support.validation.AsaproValidator;

/**
 * 대관/대여 신청정보 입력값 검사
 * @author yckim
 * @since 2018. 12. 03.
 *
 */
@Component
public class RentalReservationValidator implements AsaproValidator{

	@Override
	public boolean supports(Class<?> clazz) {
		return clazz.isAssignableFrom(RentalReservationInfo.class);
	}

	@Override
	public void validate(Object target, Errors errors) {
		throw new AsaproException("[ASAPRO] use other validate method. not this.");
	}
	
	@Override
	public void validate(Object target, Errors errors, String formMode) {
		RentalReservationInfo rentalReservationForm = (RentalReservationInfo)target;
		
		//기본정보 등록/수정
		if("INSERT".equalsIgnoreCase(formMode) || "UPDATE".equalsIgnoreCase(formMode) || "UPDATE_USER".equalsIgnoreCase(formMode) ){
			
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "reservName", null, "신청자명은 필수입력입니다.");
//			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "reservPosition", null, "신청자 직위/직책은 필수입력입니다.");
			
			//신청자 전화번호
//			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "reservTel1", null, "전화번호 1은 필수입력입니다.");
//			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "reservTel2", null, "전화번호 2는 필수입력입니다.");
//			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "reservTel3", null, "전화번호 3는 필수입력입니다.");
//			if( StringUtils.isNotBlank(rentalReservationForm.getReservTel1()) || StringUtils.isNotBlank(rentalReservationForm.getReservTel2()) || StringUtils.isNotBlank(rentalReservationForm.getReservTel3()) ){
//				if( !StringUtils.isNumeric(rentalReservationForm.getReservTel1()) ||  !StringUtils.isNumeric(rentalReservationForm.getReservTel2()) || !StringUtils.isNumeric(rentalReservationForm.getReservTel3()) ){
//					errors.rejectValue("reservTel1", null, "전화번호는 숫자만 입력가능합니다.");
//				}
//			}
			//신청자 휴대폰번호
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "reservHp1", null, "휴대폰번호 1은 필수입력입니다.");
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "reservHp2", null, "휴대폰번호 2는 필수입력입니다.");
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "reservHp3", null, "휴대폰번호 3는 필수입력입니다.");
			if( StringUtils.isNotBlank(rentalReservationForm.getReservHp2()) ){
				if(!AsaproUtils.isMobile(rentalReservationForm.getReservHp2(),"MOBILE2") ){
					errors.rejectValue("reservHp2", null, "휴대폰번호 중간자리는 숫자 3자리 또는 숫자 4자리 형태로 입력해야 합니다.");
				}
			}
			if( StringUtils.isNotBlank(rentalReservationForm.getReservHp3()) ){
				if(!AsaproUtils.isMobile(rentalReservationForm.getReservHp3(),"MOBILE3") ){
					errors.rejectValue("reservHp3", null, "휴대폰번호 끝자리는 숫자 4자리 형태로 입력해야 합니다.");
				}
			}
			
			//이메일 체크
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "reservEmail", null, "이메일 주소는 필수입력입니다.");
			if( StringUtils.isNotBlank(rentalReservationForm.getReservEmail()) ){
				if( !AsaproUtils.isEmail(rentalReservationForm.getReservEmail()) ){
					errors.rejectValue("reservEmail", null, "이메일을 형식에 맞게 입력해 주세요.");
				}
			}
			
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "reservOrganization", null, "신청기관명은 필수입력입니다.");
//			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "reservOrgDivCode", null, "신청기관구분은 필수입력입니다.");
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "reservTotal", null, "사용 인원은 필수입력입니다.");
			//사용인원 최소,최대 체크
			if(rentalReservationForm.getReservTotal() != null ){
				if(rentalReservationForm.getReservTotal() < rentalReservationForm.getRental().getRentResMinNumber()){
					errors.rejectValue("reservTotal", null, "최소인원 이상 예약 가능합니다.");
				}
				if(rentalReservationForm.getReservTotal() > rentalReservationForm.getRental().getRentResMaxNumber()){
					errors.rejectValue("reservTotal", null, "최대 수용인원을 초과했습니다.");
				}
			}
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "reservDate", null, "예약일은 필수입력입니다.");
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "reservTime", null, "예약시간은 필수입력입니다.");
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "reservPaidType", null, "결제방법은 필수입력입니다.");
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "reservTitle", null, "행사명은 필수입력입니다.");
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "reservContent", null, "목적 및 내용은 필수입력입니다.");
			
			
/*			if("INSERT".equalsIgnoreCase(formMode) ){
				if( rentalReservationForm.getAgreeChk() == null || !rentalReservationForm.getAgreeChk() ){
					errors.rejectValue("agreeChk", null, "서비스 이용약관 동의는 필수입니다.");
				}
				
//				if( rentalReservationForm.getReservAppendingFile() == null || rentalReservationForm.getReservAppendingFile().isEmpty() ){
//					errors.rejectValue("reservAppendingFile", null, "사업자등록증는 필수입니다.");
//				}
			}
			else*/ if("UPDATE".equalsIgnoreCase(formMode) ){
				ValidationUtils.rejectIfEmptyOrWhitespace(errors, "reservStatus", null, "신청상태는 필수입력입니다.");
				ValidationUtils.rejectIfEmptyOrWhitespace(errors, "reservPrice", null, "대관료는 필수입력입니다.");
				ValidationUtils.rejectIfEmptyOrWhitespace(errors, "reservDiscountRate", null, "할인률은 필수입력입니다.");
				ValidationUtils.rejectIfEmptyOrWhitespace(errors, "reservPaymentAmount", null, "결제금액은 필수입력입니다.");
				ValidationUtils.rejectIfEmptyOrWhitespace(errors, "reservParking", null, "주차지원은 필수입력입니다.");
				
			}
		} else if("AGREE".equalsIgnoreCase(formMode) ){
			if( rentalReservationForm.getAgreeChk() == null || !rentalReservationForm.getAgreeChk() ){
				errors.rejectValue("agreeChk", null, "개인정보 수집 이용 동의에 모두 동의하셔야 합니다.");
			}
		}
		
	}

}
