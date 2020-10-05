/**
 * 
 */
package egovframework.com.asapro.rental.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import egovframework.com.asapro.fileinfo.service.FileInfo;
import egovframework.com.asapro.site.service.MultiSiteVO;
//import egovframework.com.asapro.watchdog.aop.WatchDog;

/**
 * 대관/대여 VO
 * @author yckim
 * @since 2018. 10. 26.
 *
 */
public class Rental extends MultiSiteVO{

	//기본정보
//	@WatchDog
	private Integer rentId;	//대관/대여 아이디
	private String rentCate1;	//구분 카테고리 1 (코드)
	private String rentCate2;	//구분 카테고리 2 (코드)
//	@WatchDog
	private String rentManager;	//담당자
	private String rentManagerTel;	//담당자 연락처
	private String rentManagerEmail;	//담당자 이메일주소
	private String rentTitle;	//대관/대여 제목
	private String rentShortDescription;	//한줄소개
	private String rentDiv = "facility";	//대관/대여 구분 (시설/물품 - facility / item)
	private String rentRentingMethod = "time";	//대관료 책정 방식 (시간단위/패키지단위 - time / package)
	private Integer rentCharge = 0;	//대관/대여 요금 - 1시간 기준
	private String rentReservType;	//예약유형 (온라인 접수, 현장 접수... 등) (코드)
	private String rentPaymentType;	//결제방법 (온라인 결제, 현장결제... 등) (코드)
	private String rentDescription;	//공간소개
	private String rentFacilityInfo;	//시설안내
	private String rentPrecautions;	//주의사항
	private String rentRefundPokicy;	//환불규정
	private String rentEtc;	//기타 정보
	private String rentAvailableTime;	//예약가능시간
	private boolean rentUse = false;	//게시여부 - 신청설정정보를 등록할때 까진 false로
	private String rentVR;	//VR폴더명
	private boolean rentApprove = true;	//승인기능사용여부
	private boolean rentMembership = false;	//회원할인사용여부
	private Integer rentMemberDiscount = 0;	//회원 할인률
	private Date rentRegDate;	//등록일
	private Integer rentParking;	//주차지원대수
	private Integer rentOrder;	//정렬순서
	
	//신청설정정보
	private String rentPossiblelDayType;	//대관/대여 가능일 구분 (상시/기간  - always/period)
	private String rentPeriodSdate;	//대관/대여 기간 시작일
	private String rentPeriodEdate;	//대관/대여 기간 종료일
	private Integer rentReservSdateAfter;	//예약가능 시작일(오늘로 부터 x일 이후 시작)
	private Integer rentReservEdateAfter;	//예약가능 종료일(오늘로 부터 x일 이전 종료)
	private String rentPossibleDayOfWeek;	//예약가능 요일
	private String rentReservTime;	//예약가능 시간대(평일)
	private String rentReservTimeSat;	//예약가능 시간대(토요일)
	private String rentReservTimeSun;	//예약가능 시간대(일요일)
	private String rentImpossibleDate;	//예약불가능 날짜
	private Integer rentResMinTime;	//최소예약시간
	private Integer rentResMinNumber;	//최소예약인원
	private Integer rentResMaxNumber;	//최대예약인원
	private Integer rentRefundDateBefore;	//환불가능일
	private Integer rentRefundRate = 100;	//환불률
	private Integer rentPaymentTimeLimit;	//온라인결제 제한시간
	private String rentStep;	//진행단계
	
	//이미지정보
	private MultipartFile rentImage;	//이미지파일
	private FileInfo thumb;	//이미지 정보
	
	//db없음
	private String step;	//진행단계 구분필드
	private List<RentReservTime> rentReservTimeList = new ArrayList<RentReservTime>();	//예약가능 시간대 리스트형태(평일)
	private List<RentReservTime> rentReservTimeSatList = new ArrayList<RentReservTime>();	//예약가능 시간대 리스트형태(토요일)
	private List<RentReservTime> rentReservTimeSunList = new ArrayList<RentReservTime>();	//예약가능 시간대 리스트형태(일요일)
	private boolean applyPossibleBtn = false;	//예약신청 버튼 노출을 위한 체크값
	
	/**
	 * @return the rentId
	 */
	public Integer getRentId() {
		return rentId;
	}
	/**
	 * @param rentId the rentId to set
	 */
	public void setRentId(Integer rentId) {
		this.rentId = rentId;
	}
	/**
	 * @return the rentCate1
	 */
	public String getRentCate1() {
		return rentCate1;
	}
	/**
	 * @param rentCate1 the rentCate1 to set
	 */
	public void setRentCate1(String rentCate1) {
		this.rentCate1 = rentCate1;
	}
	/**
	 * @return the rentCate2
	 */
	public String getRentCate2() {
		return rentCate2;
	}
	/**
	 * @param rentCate2 the rentCate2 to set
	 */
	public void setRentCate2(String rentCate2) {
		this.rentCate2 = rentCate2;
	}
	/**
	 * @return the rentManager
	 */
	public String getRentManager() {
		return rentManager;
	}
	/**
	 * @param rentManager the rentManager to set
	 */
	public void setRentManager(String rentManager) {
		this.rentManager = rentManager;
	}
	/**
	 * @return the rentManagerTel
	 */
	public String getRentManagerTel() {
		return rentManagerTel;
	}
	/**
	 * @param rentManagerTel the rentManagerTel to set
	 */
	public void setRentManagerTel(String rentManagerTel) {
		this.rentManagerTel = rentManagerTel;
	}
	
	/**
	 * @return the rentManagerEmail
	 */
	public String getRentManagerEmail() {
		return rentManagerEmail;
	}
	/**
	 * @param rentManagerEmail the rentManagerEmail to set
	 */
	public void setRentManagerEmail(String rentManagerEmail) {
		this.rentManagerEmail = rentManagerEmail;
	}
	/**
	 * @return the rentTitle
	 */
	public String getRentTitle() {
		return rentTitle;
	}
	/**
	 * @param rentTitle the rentTitle to set
	 */
	public void setRentTitle(String rentTitle) {
		this.rentTitle = rentTitle;
	}
	/**
	 * @return the rentDiv
	 */
	public String getRentDiv() {
		return rentDiv;
	}
	/**
	 * @param rentDiv the rentDiv to set
	 */
	public void setRentDiv(String rentDiv) {
		this.rentDiv = rentDiv;
	}
	
	/**
	 * @return the rentRentingMethod
	 */
	public String getRentRentingMethod() {
		return rentRentingMethod;
	}
	/**
	 * @param rentRentingMethod the rentRentingMethod to set
	 */
	public void setRentRentingMethod(String rentRentingMethod) {
		this.rentRentingMethod = rentRentingMethod;
	}
	/**
	 * @return the rentCharge
	 */
	public Integer getRentCharge() {
		return rentCharge;
	}
	/**
	 * @param rentCharge the rentCharge to set
	 */
	public void setRentCharge(Integer rentCharge) {
		this.rentCharge = rentCharge;
	}
	/**
	 * @return the rentReservType
	 */
	public String getRentReservType() {
		return rentReservType;
	}
	/**
	 * @param rentReservType the rentReservType to set
	 */
	public void setRentReservType(String rentReservType) {
		this.rentReservType = rentReservType;
	}
	/**
	 * @return the rentPaymentType
	 */
	public String getRentPaymentType() {
		return rentPaymentType;
	}
	/**
	 * @param rentPaymentType the rentPaymentType to set
	 */
	public void setRentPaymentType(String rentPaymentType) {
		this.rentPaymentType = rentPaymentType;
	}
	/**
	 * @return the rentDescription
	 */
	public String getRentDescription() {
		return rentDescription;
	}
	/**
	 * @param rentDescription the rentDescription to set
	 */
	public void setRentDescription(String rentDescription) {
		this.rentDescription = rentDescription;
	}
	/**
	 * @return the rentEtc
	 */
	public String getRentEtc() {
		return rentEtc;
	}
	/**
	 * @param rentEtc the rentEtc to set
	 */
	public void setRentEtc(String rentEtc) {
		this.rentEtc = rentEtc;
	}
	/**
	 * @return the rentUse
	 */
	public boolean isRentUse() {
		return rentUse;
	}
	/**
	 * @param rentUse the rentUse to set
	 */
	public void setRentUse(boolean rentUse) {
		this.rentUse = rentUse;
	}
	/**
	 * @return the rentPossiblelDayType
	 */
	public String getRentPossiblelDayType() {
		return rentPossiblelDayType;
	}
	/**
	 * @param rentPossiblelDayType the rentPossiblelDayType to set
	 */
	public void setRentPossiblelDayType(String rentPossiblelDayType) {
		this.rentPossiblelDayType = rentPossiblelDayType;
	}
	/**
	 * @return the rentPeriodSdate
	 */
	public String getRentPeriodSdate() {
		return rentPeriodSdate;
	}
	/**
	 * @param rentPeriodSdate the rentPeriodSdate to set
	 */
	public void setRentPeriodSdate(String rentPeriodSdate) {
		this.rentPeriodSdate = rentPeriodSdate;
	}
	/**
	 * @return the rentPeriodEdate
	 */
	public String getRentPeriodEdate() {
		return rentPeriodEdate;
	}
	/**
	 * @param rentPeriodEdate the rentPeriodEdate to set
	 */
	public void setRentPeriodEdate(String rentPeriodEdate) {
		this.rentPeriodEdate = rentPeriodEdate;
	}
	/**
	 * @return the rentReservSdateAfter
	 */
	public Integer getRentReservSdateAfter() {
		return rentReservSdateAfter;
	}
	/**
	 * @param rentReservSdateAfter the rentReservSdateAfter to set
	 */
	public void setRentReservSdateAfter(Integer rentReservSdateAfter) {
		this.rentReservSdateAfter = rentReservSdateAfter;
	}
	/**
	 * @return the rentReservEdateAfter
	 */
	public Integer getRentReservEdateAfter() {
		return rentReservEdateAfter;
	}
	/**
	 * @param rentReservEdateAfter the rentReservEdateAfter to set
	 */
	public void setRentReservEdateAfter(Integer rentReservEdateAfter) {
		this.rentReservEdateAfter = rentReservEdateAfter;
	}
	/**
	 * @return the rentPossibleDayOfWeek
	 */
	public String getRentPossibleDayOfWeek() {
		return rentPossibleDayOfWeek;
	}
	/**
	 * @param rentPossibleDayOfWeek the rentPossibleDayOfWeek to set
	 */
	public void setRentPossibleDayOfWeek(String rentPossibleDayOfWeek) {
		this.rentPossibleDayOfWeek = rentPossibleDayOfWeek;
	}
	/**
	 * @return the rentReservTime
	 */
	public String getRentReservTime() {
		return rentReservTime;
	}
	/**
	 * @param rentReservTime the rentReservTime to set
	 */
	public void setRentReservTime(String rentReservTime) {
		this.rentReservTime = rentReservTime;
	}
	
	/**
	 * @return the rentReservTimeSat
	 */
	public String getRentReservTimeSat() {
		return rentReservTimeSat;
	}
	/**
	 * @param rentReservTimeSat the rentReservTimeSat to set
	 */
	public void setRentReservTimeSat(String rentReservTimeSat) {
		this.rentReservTimeSat = rentReservTimeSat;
	}
	/**
	 * @return the rentReservTimeSun
	 */
	public String getRentReservTimeSun() {
		return rentReservTimeSun;
	}
	/**
	 * @param rentReservTimeSun the rentReservTimeSun to set
	 */
	public void setRentReservTimeSun(String rentReservTimeSun) {
		this.rentReservTimeSun = rentReservTimeSun;
	}
	/**
	 * @return the rentImpossibleDate
	 */
	public String getRentImpossibleDate() {
		return rentImpossibleDate;
	}
	/**
	 * @param rentImpossibleDate the rentImpossibleDate to set
	 */
	public void setRentImpossibleDate(String rentImpossibleDate) {
		this.rentImpossibleDate = rentImpossibleDate;
	}
	/**
	 * @return the rentRegDate
	 */
	public Date getRentRegDate() {
		return rentRegDate;
	}
	/**
	 * @param rentRegDate the rentRegDate to set
	 */
	public void setRentRegDate(Date rentRegDate) {
		this.rentRegDate = rentRegDate;
	}
	/**
	 * @return the rentImage
	 */
	public MultipartFile getRentImage() {
		return rentImage;
	}
	/**
	 * @param rentImage the rentImage to set
	 */
	public void setRentImage(MultipartFile rentImage) {
		this.rentImage = rentImage;
	}
	/**
	 * @return the thumb
	 */
	public FileInfo getThumb() {
		return thumb;
	}
	/**
	 * @param thumb the thumb to set
	 */
	public void setThumb(FileInfo thumb) {
		this.thumb = thumb;
	}
	/**
	 * @return the step
	 */
	public String getStep() {
		return step;
	}
	/**
	 * @param step the step to set
	 */
	public void setStep(String step) {
		this.step = step;
	}
	
	/**
	 * @return the rentStep
	 */
	public String getRentStep() {
		return rentStep;
	}
	/**
	 * @param rentStep the rentStep to set
	 */
	public void setRentStep(String rentStep) {
		this.rentStep = rentStep;
	}
	/**
	 * @return the rentReservTimeList
	 */
	public List<RentReservTime> getRentReservTimeList() {
		return rentReservTimeList;
	}
	/**
	 * @param rentReservTimeList the rentReservTimeList to set
	 */
	public void setRentReservTimeList(List<RentReservTime> rentReservTimeList) {
		this.rentReservTimeList = rentReservTimeList;
	}
	
	/**
	 * @return the rentReservTimeSatList
	 */
	public List<RentReservTime> getRentReservTimeSatList() {
		return rentReservTimeSatList;
	}
	/**
	 * @param rentReservTimeSatList the rentReservTimeSatList to set
	 */
	public void setRentReservTimeSatList(List<RentReservTime> rentReservTimeSatList) {
		this.rentReservTimeSatList = rentReservTimeSatList;
	}
	/**
	 * @return the rentReservTimeSunList
	 */
	public List<RentReservTime> getRentReservTimeSunList() {
		return rentReservTimeSunList;
	}
	/**
	 * @param rentReservTimeSunList the rentReservTimeSunList to set
	 */
	public void setRentReservTimeSunList(List<RentReservTime> rentReservTimeSunList) {
		this.rentReservTimeSunList = rentReservTimeSunList;
	}
	/**
	 * @return the rentParking
	 */
	public Integer getRentParking() {
		return rentParking;
	}
	/**
	 * @param rentParking the rentParking to set
	 */
	public void setRentParking(Integer rentParking) {
		this.rentParking = rentParking;
	}
	/**
	 * @return the applyPossibleBtn
	 */
	public boolean isApplyPossibleBtn() {
		return applyPossibleBtn;
	}
	/**
	 * @param applyPossibleBtn the applyPossibleBtn to set
	 */
	public void setApplyPossibleBtn(boolean applyPossibleBtn) {
		this.applyPossibleBtn = applyPossibleBtn;
	}
	/**
	 * @return the rentOrder
	 */
	public Integer getRentOrder() {
		return rentOrder;
	}
	/**
	 * @param rentOrder the rentOrder to set
	 */
	public void setRentOrder(Integer rentOrder) {
		this.rentOrder = rentOrder;
	}
	/**
	 * @return the rentVR
	 */
	public String getRentVR() {
		return rentVR;
	}
	/**
	 * @param rentVR the rentVR to set
	 */
	public void setRentVR(String rentVR) {
		this.rentVR = rentVR;
	}
	/**
	 * @return the rentApprove
	 */
	public boolean isRentApprove() {
		return rentApprove;
	}
	/**
	 * @param rentApprove the rentApprove to set
	 */
	public void setRentApprove(boolean rentApprove) {
		this.rentApprove = rentApprove;
	}
	/**
	 * @return the rentMembership
	 */
	public boolean isRentMembership() {
		return rentMembership;
	}
	/**
	 * @param rentMembership the rentMembership to set
	 */
	public void setRentMembership(boolean rentMembership) {
		this.rentMembership = rentMembership;
	}
	/**
	 * @return the rentMemberDiscount
	 */
	public Integer getRentMemberDiscount() {
		return rentMemberDiscount;
	}
	/**
	 * @param rentMemberDiscount the rentMemberDiscount to set
	 */
	public void setRentMemberDiscount(Integer rentMemberDiscount) {
		this.rentMemberDiscount = rentMemberDiscount;
	}
	/**
	 * @return the rentResMinTime
	 */
	public Integer getRentResMinTime() {
		return rentResMinTime;
	}
	/**
	 * @param rentResMinTime the rentResMinTime to set
	 */
	public void setRentResMinTime(Integer rentResMinTime) {
		this.rentResMinTime = rentResMinTime;
	}
	/**
	 * @return the rentResMinNumber
	 */
	public Integer getRentResMinNumber() {
		return rentResMinNumber;
	}
	/**
	 * @param rentResMinNumber the rentResMinNumber to set
	 */
	public void setRentResMinNumber(Integer rentResMinNumber) {
		this.rentResMinNumber = rentResMinNumber;
	}
	/**
	 * @return the rentResMaxNumber
	 */
	public Integer getRentResMaxNumber() {
		return rentResMaxNumber;
	}
	/**
	 * @param rentResMaxNumber the rentResMaxNumber to set
	 */
	public void setRentResMaxNumber(Integer rentResMaxNumber) {
		this.rentResMaxNumber = rentResMaxNumber;
	}
	/**
	 * @return the rentRefundDateBefore
	 */
	public Integer getRentRefundDateBefore() {
		return rentRefundDateBefore;
	}
	/**
	 * @param rentRefundDateBefore the rentRefundDateBefore to set
	 */
	public void setRentRefundDateBefore(Integer rentRefundDateBefore) {
		this.rentRefundDateBefore = rentRefundDateBefore;
	}
	/**
	 * @return the rentRefundRate
	 */
	public Integer getRentRefundRate() {
		return rentRefundRate;
	}
	/**
	 * @param rentRefundRate the rentRefundRate to set
	 */
	public void setRentRefundRate(Integer rentRefundRate) {
		this.rentRefundRate = rentRefundRate;
	}
	/**
	 * @return the rentPaymentTimeLimit
	 */
	public Integer getRentPaymentTimeLimit() {
		return rentPaymentTimeLimit;
	}
	/**
	 * @param rentPaymentTimeLimit the rentPaymentTimeLimit to set
	 */
	public void setRentPaymentTimeLimit(Integer rentPaymentTimeLimit) {
		this.rentPaymentTimeLimit = rentPaymentTimeLimit;
	}
	/**
	 * @return the rentShortDescription
	 */
	public String getRentShortDescription() {
		return rentShortDescription;
	}
	/**
	 * @param rentShortDescription the rentShortDescription to set
	 */
	public void setRentShortDescription(String rentShortDescription) {
		this.rentShortDescription = rentShortDescription;
	}
	/**
	 * @return the rentFacilityInfo
	 */
	public String getRentFacilityInfo() {
		return rentFacilityInfo;
	}
	/**
	 * @param rentFacilityInfo the rentFacilityInfo to set
	 */
	public void setRentFacilityInfo(String rentFacilityInfo) {
		this.rentFacilityInfo = rentFacilityInfo;
	}
	/**
	 * @return the rentPrecautions
	 */
	public String getRentPrecautions() {
		return rentPrecautions;
	}
	/**
	 * @param rentPrecautions the rentPrecautions to set
	 */
	public void setRentPrecautions(String rentPrecautions) {
		this.rentPrecautions = rentPrecautions;
	}
	/**
	 * @return the rentRefundPokicy
	 */
	public String getRentRefundPokicy() {
		return rentRefundPokicy;
	}
	/**
	 * @param rentRefundPokicy the rentRefundPokicy to set
	 */
	public void setRentRefundPokicy(String rentRefundPokicy) {
		this.rentRefundPokicy = rentRefundPokicy;
	}
	/**
	 * @return the rentAvailableTime
	 */
	public String getRentAvailableTime() {
		return rentAvailableTime;
	}
	/**
	 * @param rentAvailableTime the rentAvailableTime to set
	 */
	public void setRentAvailableTime(String rentAvailableTime) {
		this.rentAvailableTime = rentAvailableTime;
	}
	
	
}
