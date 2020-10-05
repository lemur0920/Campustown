/**
 * 
 */
package egovframework.com.asapro.rental.web;

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

import egovframework.com.asapro.rental.service.RentReservTime;
import egovframework.com.asapro.rental.service.Rental;
import egovframework.com.asapro.support.exception.AsaproException;
import egovframework.com.asapro.support.util.AsaproUtils;
import egovframework.com.asapro.support.validation.AsaproValidator;

/**
 * 대관/대여 입력값 검사
 * @author yckim
 * @since 2018. 10. 29.
 *
 */
@Component
public class RentalValidator implements AsaproValidator{
	private static final Logger LOGGER = LoggerFactory.getLogger(RentalValidator.class);
	
	@Override
	public boolean supports(Class<?> clazz) {
		return clazz.isAssignableFrom(Rental.class);
	}

	@Override
	public void validate(Object target, Errors errors) {
		throw new AsaproException("[ASAPRO] use other validate method. not this.");
	}
	
	@Override
	public void validate(Object target, Errors errors, String formMode) {
		Rental rentalForm = (Rental)target;
		
		//기본정보 등록/수정
		if("INSERT".equalsIgnoreCase(formMode) || "UPDATE_BASIC".equalsIgnoreCase(formMode)){
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "rentCate1", null, "시설구분1은 필수입니다.");
			//ValidationUtils.rejectIfEmptyOrWhitespace(errors, "rentCate2", null, "시설구분2는 필수입니다.");
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "rentManager", null, "담당자명은 필수입력입니다.");
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "rentManagerTel", null, "담당자 연락처는 필수입력입니다.");
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "rentManagerEmail", null, "담당자 이메일은 필수입력입니다.");
			if( StringUtils.isNotBlank(rentalForm.getRentManagerEmail()) ){
				if( !AsaproUtils.isEmail(rentalForm.getRentManagerEmail()) ){
					errors.rejectValue("rentManagerEmail", null, "이메일을 형식에 맞게 입력해 주세요.");
				}
			}
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "rentTitle", null, "대관/대여 제목은 필수입력입니다.");
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "rentShortDescription", null, "한줄설명은 필수입력입니다.");
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "rentDiv", null, "대관/대여 구분은 필수선택입니다.");
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "rentCharge", null, "대관/대여 요금은 필수입력입니다.");
			//ValidationUtils.rejectIfEmptyOrWhitespace(errors, "rentParking", null, "주차지원대수는 필수입력입니다.");
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "rentReservType", null, "예약 접수방법은 필수선택입니다.");
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "rentPaymentType", null, "결제방법은 필수선택입니다.");
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "rentApprove", null, "승인기능 사용여부는 필수선택입니다.");
			
			//회원할인사용여부와 할인률체크
			if( rentalForm.isRentMembership() ){
				ValidationUtils.rejectIfEmptyOrWhitespace(errors, "rentMemberDiscount", null, "할인률은 필수입니다.");
			} else {
				
			}
			
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "rentDescription", null, "대관/대여 설명은 필수입력입니다.");
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "rentUse", null, "게시여부는 필수선택입니다.");
			
			if("INSERT".equalsIgnoreCase(formMode) ){
				if( rentalForm.getRentImage() == null || rentalForm.getRentImage().isEmpty() ){
					errors.rejectValue("rentImage", null, "이미지는 필수입니다.");
				}
			}
			
		}
		//신청설정정보 수정
		else if("UPDATE_APPLYSET".equalsIgnoreCase(formMode)){
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "rentPossiblelDayType", null, "가능일 구분은 필수선택입니다.");
			
			//가능일 구분이 기간(period) 인경우 기간 체크
			if( "period".equals(rentalForm.getRentPossiblelDayType()) ){
				ValidationUtils.rejectIfEmptyOrWhitespace(errors, "rentPeriodSdate", null, "기간 시작일은 필수입니다.");
				ValidationUtils.rejectIfEmptyOrWhitespace(errors, "rentPeriodEdate", null, "기간 종료일은 필수입니다.");
				
				if( StringUtils.isNotBlank(rentalForm.getRentPeriodSdate()) && StringUtils.isNotBlank(rentalForm.getRentPeriodEdate()) ){
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.KOREA);
					Date startDate = null;
					Date endDate = null;
					try {
						startDate = sdf.parse(rentalForm.getRentPeriodSdate());
						endDate = sdf.parse(rentalForm.getRentPeriodEdate()); 
					} catch (ParseException e) {
						LOGGER.error("[ParseException] Try/Catch... : "+ e.getMessage());
					}
					if( startDate.after(endDate) ){
						//errors.rejectValue("rentPeriodSdate", null, "시작일은 종료일 이전이어야 합니다.");
						errors.rejectValue("rentPeriodEdate", null, "종료일은 시작일 이후여야 합니다.");
					}
				}
			}
			
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "rentReservSdateAfter", null, "예약가능 시작일은 필수입니다.");
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "rentReservEdateAfter", null, "예약가능 종료일은 필수입니다.");
			if( rentalForm.getRentReservSdateAfter() != null && rentalForm.getRentReservEdateAfter() != null && rentalForm.getRentReservSdateAfter() > 0 && rentalForm.getRentReservEdateAfter() > 0 ){
				if(rentalForm.getRentReservSdateAfter() > rentalForm.getRentReservEdateAfter() ){
					errors.rejectValue("rentReservEdateAfter", null, "종료일은 시작일보다 커야합니다.");
				}
			}
			
			//예약가능시간 체크 (평일)
			if(rentalForm.getRentReservTimeList() != null && !rentalForm.getRentReservTimeList().isEmpty() ){
				int i = 0;
				for(RentReservTime rentReservTime : rentalForm.getRentReservTimeList() ) {
					ValidationUtils.rejectIfEmptyOrWhitespace(errors, "rentReservTimeList["+i+"].rentReservStartTime", null, "시작시간 입력은 필수입니다.");
					ValidationUtils.rejectIfEmptyOrWhitespace(errors, "rentReservTimeList["+i+"].rentReservEndTime", null, "종료시간 입력은 필수입니다.");
					if( StringUtils.isNotBlank(rentReservTime.getRentReservStartTime()) && StringUtils.isNotBlank(rentReservTime.getRentReservEndTime()) ){
						if(!AsaproUtils.isHourMinute(rentReservTime.getRentReservStartTime()) || !AsaproUtils.isHourMinute(rentReservTime.getRentReservEndTime())){
							errors.rejectValue("rentReservTimeList["+i+"].rentReservStartTime", null, "시간입력은 오전 10:00, 오후 14:30 형태로 입력해야 합니다.");
						}else{
							if(!rentReservTime.isRentIsNextday() && rentReservTime.getRentReservStartTime().compareTo(rentReservTime.getRentReservEndTime()) > 0 ){
								errors.rejectValue("rentReservTimeList["+i+"].rentReservStartTime", null, "종료시간이 더 늦어야 합니다.");
							}
						}
					}
					i++;
				}
			}
			//예약가능시간 체크 (토요일)
			if(rentalForm.getRentReservTimeSatList() != null && !rentalForm.getRentReservTimeSatList().isEmpty() ){
				int i = 0;
				for(RentReservTime rentReservTime : rentalForm.getRentReservTimeSatList() ) {
					ValidationUtils.rejectIfEmptyOrWhitespace(errors, "rentReservTimeSatList["+i+"].rentReservStartTime", null, "시작시간 입력은 필수입니다.");
					ValidationUtils.rejectIfEmptyOrWhitespace(errors, "rentReservTimeSatList["+i+"].rentReservEndTime", null, "종료시간 입력은 필수입니다.");
					if( StringUtils.isNotBlank(rentReservTime.getRentReservStartTime()) && StringUtils.isNotBlank(rentReservTime.getRentReservEndTime()) ){
						if(!AsaproUtils.isHourMinute(rentReservTime.getRentReservStartTime()) || !AsaproUtils.isHourMinute(rentReservTime.getRentReservEndTime())){
							errors.rejectValue("rentReservTimeSatList["+i+"].rentReservStartTime", null, "시간입력은 오전 10:00, 오후 14:30 형태로 입력해야 합니다.");
						}else{
							if(!rentReservTime.isRentIsNextday() && rentReservTime.getRentReservStartTime().compareTo(rentReservTime.getRentReservEndTime()) > 0 ){
								errors.rejectValue("rentReservTimeSatList["+i+"].rentReservStartTime", null, "종료시간이 더 늦어야 합니다.");
							}
						}
					}
					i++;
				}
			}
			//예약가능시간 체크 (일요일)
			if(rentalForm.getRentReservTimeSunList() != null && !rentalForm.getRentReservTimeSunList().isEmpty() ){
				int i = 0;
				for(RentReservTime rentReservTime : rentalForm.getRentReservTimeSunList() ) {
					ValidationUtils.rejectIfEmptyOrWhitespace(errors, "rentReservTimeSunList["+i+"].rentReservStartTime", null, "시작시간 입력은 필수입니다.");
					ValidationUtils.rejectIfEmptyOrWhitespace(errors, "rentReservTimeSunList["+i+"].rentReservEndTime", null, "종료시간 입력은 필수입니다.");
					if( StringUtils.isNotBlank(rentReservTime.getRentReservStartTime()) && StringUtils.isNotBlank(rentReservTime.getRentReservEndTime()) ){
						if(!AsaproUtils.isHourMinute(rentReservTime.getRentReservStartTime()) || !AsaproUtils.isHourMinute(rentReservTime.getRentReservEndTime())){
							errors.rejectValue("rentReservTimeSunList["+i+"].rentReservStartTime", null, "시간입력은 오전 10:00, 오후 14:30 형태로 입력해야 합니다.");
						}else{
							if(!rentReservTime.isRentIsNextday() && rentReservTime.getRentReservStartTime().compareTo(rentReservTime.getRentReservEndTime()) > 0 ){
								errors.rejectValue("rentReservTimeSunList["+i+"].rentReservStartTime", null, "종료시간이 더 늦어야 합니다.");
							}
						}
					}
					i++;
				}
			}
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "rentResMinTime", null, "최소예약시간은 필수입력입니다.");
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "rentResMinNumber", null, "최소예약인원은 필수입력입니다.");
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "rentResMaxNumber", null, "최대수용인원은 필수입력입니다.");
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "rentRefundDateBefore", null, "환불가능일은 필수입력입니다.");
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "rentRefundRate", null, "환불률은 필수입력입니다.");
			
		}
		
		
		
	}

}
