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
 * 대관/대여 신청정보
 * @author yckim
 * @since 2018. 11. 07.
 *
 */
public class RentalReservationInfo extends MultiSiteVO{

//	@WatchDog
	private String reservId;	//신청정보 아이디
	private Rental rental;	//대관/대여 정보 아이디
	private String reservUserId;	//신청자 아이디
	private String reservName;	//신청자 명
	private String reservPosition;	//신청자 직위/직책
	private String reservTel;	//신청자 전화번호
	private String reservTel1;	//신청자 전화번호1
	private String reservTel2;	//신청자 전화번호2
	private String reservTel3;	//신청자 전화번호3
	private String reservHp;	//신청자 휴대폰번호
	private String reservHp1;	//신청자 휴대폰번호1
	private String reservHp2;	//신청자 휴대폰번호2
	private String reservHp3;	//신청자 휴대폰번호3
	private String reservEmail;	//신청자 이메일주소
	private String reservOrganization;	//신청기관명
	private String reservOrgDivCode;	//신청기관 구분 코드 (광고계, 문화예술계, 입주사, 일반회사 및 개인) (코드)
	private String reservZipcode;	//신청자 우편번호
	private String reservAddress;	//신청자 주소
	private String reservAddressDetail;	//신청자 상세주소
	private Integer reservTotal;	//사용 인원
	private String reservDate;	//예약일
	private String reservTime;	//예약시간
	private Integer reservUsageTime;	//사용시간
	private Integer reservTimeAdd;	//추가시간
	private String reservPaidType;	//결제방법(코드)
	private boolean reservMembership = false;	//멤버쉽 여부
	private Integer reservPrice = 0;	//대관/대여료
	private Integer reservDiscountRate = 0;	//할인률
	private Integer reservPaymentAmount = 0;	//결제예정금액
	private String reservTitle;	//행사명
	private String reservContent;	//행사/전시 내용
	private Integer reservParking = 0;	//주차인원
	private String reservStatus;	//신청상태(코드)
	private String reservMemo;	//비고
	private String reservRequests;	//요청사항
	private Date reservRegDate;	//등록일
	private String reservCancelDate;	//예약취소날짜
	private String reservApprovDate;	//승인날짜
	private String reservPaymentDate;	//결제날짜
	private String reservPayCancelDate;	//결제취소날짜
	private String reservTid;	//온라인 결제 코드
	
	//첨부파일정보
	private MultipartFile reservAppendingFile;	//첨부파일
	private FileInfo reservAppendingFileInfo;	//첨부파일 정보
	private Boolean agreeChk;	//개인정보 수집동의 체크여부

	private Boolean emailTemp = false;	//이메일템플릿 여부
	
	
	/**
	 * @return the reservId
	 */
	public String getReservId() {
		return reservId;
	}
	/**
	 * @param reservId the reservId to set
	 */
	public void setReservId(String reservId) {
		this.reservId = reservId;
	}
	/**
	 * @return the rental
	 */
	public Rental getRental() {
		return rental;
	}
	/**
	 * @param rental the rental to set
	 */
	public void setRental(Rental rental) {
		this.rental = rental;
	}
	/**
	 * @return the reservName
	 */
	public String getReservName() {
		return reservName;
	}
	/**
	 * @param reservName the reservName to set
	 */
	public void setReservName(String reservName) {
		this.reservName = reservName;
	}
	/**
	 * @return the reservPosition
	 */
	public String getReservPosition() {
		return reservPosition;
	}
	/**
	 * @param reservPosition the reservPosition to set
	 */
	public void setReservPosition(String reservPosition) {
		this.reservPosition = reservPosition;
	}
	/**
	 * @return the reservTel
	 */
	public String getReservTel() {
		return reservTel;
	}
	/**
	 * @param reservTel the reservTel to set
	 */
	public void setReservTel(String reservTel) {
		this.reservTel = reservTel;
	}
	/**
	 * @return the reservHp
	 */
	public String getReservHp() {
		return reservHp;
	}
	/**
	 * @param reservHp the reservHp to set
	 */
	public void setReservHp(String reservHp) {
		this.reservHp = reservHp;
	}
	/**
	 * @return the reservEmail
	 */
	public String getReservEmail() {
		return reservEmail;
	}
	/**
	 * @param reservEmail the reservEmail to set
	 */
	public void setReservEmail(String reservEmail) {
		this.reservEmail = reservEmail;
	}
	/**
	 * @return the reservOrganization
	 */
	public String getReservOrganization() {
		return reservOrganization;
	}
	/**
	 * @param reservOrganization the reservOrganization to set
	 */
	public void setReservOrganization(String reservOrganization) {
		this.reservOrganization = reservOrganization;
	}
	/**
	 * @return the reservOrgDivCode
	 */
	public String getReservOrgDivCode() {
		return reservOrgDivCode;
	}
	/**
	 * @param reservOrgDivCode the reservOrgDivCode to set
	 */
	public void setReservOrgDivCode(String reservOrgDivCode) {
		this.reservOrgDivCode = reservOrgDivCode;
	}
	/**
	 * @return the reservZipcode
	 */
	public String getReservZipcode() {
		return reservZipcode;
	}
	/**
	 * @param reservZipcode the reservZipcode to set
	 */
	public void setReservZipcode(String reservZipcode) {
		this.reservZipcode = reservZipcode;
	}
	/**
	 * @return the reservAddress
	 */
	public String getReservAddress() {
		return reservAddress;
	}
	/**
	 * @param reservAddress the reservAddress to set
	 */
	public void setReservAddress(String reservAddress) {
		this.reservAddress = reservAddress;
	}
	/**
	 * @return the reservAddressDetail
	 */
	public String getReservAddressDetail() {
		return reservAddressDetail;
	}
	/**
	 * @param reservAddressDetail the reservAddressDetail to set
	 */
	public void setReservAddressDetail(String reservAddressDetail) {
		this.reservAddressDetail = reservAddressDetail;
	}
	/**
	 * @return the reservTotal
	 */
	public Integer getReservTotal() {
		return reservTotal;
	}
	/**
	 * @param reservTotal the reservTotal to set
	 */
	public void setReservTotal(Integer reservTotal) {
		this.reservTotal = reservTotal;
	}
	/**
	 * @return the reservDate
	 */
	public String getReservDate() {
		return reservDate;
	}
	/**
	 * @param reservDate the reservDate to set
	 */
	public void setReservDate(String reservDate) {
		this.reservDate = reservDate;
	}
	/**
	 * @return the reservTime
	 */
	public String getReservTime() {
		return reservTime;
	}
	/**
	 * @param reservTime the reservTime to set
	 */
	public void setReservTime(String reservTime) {
		this.reservTime = reservTime;
	}
	/**
	 * @return the reservPaidType
	 */
	public String getReservPaidType() {
		return reservPaidType;
	}
	/**
	 * @param reservPaidType the reservPaidType to set
	 */
	public void setReservPaidType(String reservPaidType) {
		this.reservPaidType = reservPaidType;
	}
	/**
	 * @return the reservPrice
	 */
	public Integer getReservPrice() {
		return reservPrice;
	}
	/**
	 * @param reservPrice the reservPrice to set
	 */
	public void setReservPrice(Integer reservPrice) {
		this.reservPrice = reservPrice;
	}
	/**
	 * @return the reservDiscountRate
	 */
	public Integer getReservDiscountRate() {
		return reservDiscountRate;
	}
	/**
	 * @param reservDiscountRate the reservDiscountRate to set
	 */
	public void setReservDiscountRate(Integer reservDiscountRate) {
		this.reservDiscountRate = reservDiscountRate;
	}
	/**
	 * @return the reservPaymentAmount
	 */
	public Integer getReservPaymentAmount() {
		return reservPaymentAmount;
	}
	/**
	 * @param reservPaymentAmount the reservPaymentAmount to set
	 */
	public void setReservPaymentAmount(Integer reservPaymentAmount) {
		this.reservPaymentAmount = reservPaymentAmount;
	}
	/**
	 * @return the reservTitle
	 */
	public String getReservTitle() {
		return reservTitle;
	}
	/**
	 * @param reservTitle the reservTitle to set
	 */
	public void setReservTitle(String reservTitle) {
		this.reservTitle = reservTitle;
	}
	/**
	 * @return the reservContent
	 */
	public String getReservContent() {
		return reservContent;
	}
	/**
	 * @param reservContent the reservContent to set
	 */
	public void setReservContent(String reservContent) {
		this.reservContent = reservContent;
	}
	/**
	 * @return the reservParking
	 */
	public Integer getReservParking() {
		return reservParking;
	}
	/**
	 * @param reservParking the reservParking to set
	 */
	public void setReservParking(Integer reservParking) {
		this.reservParking = reservParking;
	}
	/**
	 * @return the reservStatus
	 */
	public String getReservStatus() {
		return reservStatus;
	}
	/**
	 * @param reservStatus the reservStatus to set
	 */
	public void setReservStatus(String reservStatus) {
		this.reservStatus = reservStatus;
	}
	/**
	 * @return the reservMemo
	 */
	public String getReservMemo() {
		return reservMemo;
	}
	/**
	 * @param reservMemo the reservMemo to set
	 */
	public void setReservMemo(String reservMemo) {
		this.reservMemo = reservMemo;
	}
	/**
	 * @return the reservRegDate
	 */
	public Date getReservRegDate() {
		return reservRegDate;
	}
	/**
	 * @param reservRegDate the reservRegDate to set
	 */
	public void setReservRegDate(Date reservRegDate) {
		this.reservRegDate = reservRegDate;
	}
	/**
	 * @return the reservAppendingFile
	 */
	public MultipartFile getReservAppendingFile() {
		return reservAppendingFile;
	}
	/**
	 * @param reservAppendingFile the reservAppendingFile to set
	 */
	public void setReservAppendingFile(MultipartFile reservAppendingFile) {
		this.reservAppendingFile = reservAppendingFile;
	}
	/**
	 * @return the reservAppendingFileInfo
	 */
	public FileInfo getReservAppendingFileInfo() {
		return reservAppendingFileInfo;
	}
	/**
	 * @param reservAppendingFileInfo the reservAppendingFileInfo to set
	 */
	public void setReservAppendingFileInfo(FileInfo reservAppendingFileInfo) {
		this.reservAppendingFileInfo = reservAppendingFileInfo;
	}
	/**
	 * @return the reservTel1
	 */
	public String getReservTel1() {
		return reservTel1;
	}
	/**
	 * @param reservTel1 the reservTel1 to set
	 */
	public void setReservTel1(String reservTel1) {
		this.reservTel1 = reservTel1;
	}
	/**
	 * @return the reservTel2
	 */
	public String getReservTel2() {
		return reservTel2;
	}
	/**
	 * @param reservTel2 the reservTel2 to set
	 */
	public void setReservTel2(String reservTel2) {
		this.reservTel2 = reservTel2;
	}
	/**
	 * @return the reservTel3
	 */
	public String getReservTel3() {
		return reservTel3;
	}
	/**
	 * @param reservTel3 the reservTel3 to set
	 */
	public void setReservTel3(String reservTel3) {
		this.reservTel3 = reservTel3;
	}
	/**
	 * @return the reservHp1
	 */
	public String getReservHp1() {
		return reservHp1;
	}
	/**
	 * @param reservHp1 the reservHp1 to set
	 */
	public void setReservHp1(String reservHp1) {
		this.reservHp1 = reservHp1;
	}
	/**
	 * @return the reservHp2
	 */
	public String getReservHp2() {
		return reservHp2;
	}
	/**
	 * @param reservHp2 the reservHp2 to set
	 */
	public void setReservHp2(String reservHp2) {
		this.reservHp2 = reservHp2;
	}
	/**
	 * @return the reservHp3
	 */
	public String getReservHp3() {
		return reservHp3;
	}
	/**
	 * @param reservHp3 the reservHp3 to set
	 */
	public void setReservHp3(String reservHp3) {
		this.reservHp3 = reservHp3;
	}
	/**
	 * @return the agreeChk
	 */
	public Boolean getAgreeChk() {
		return agreeChk;
	}
	/**
	 * @param agreeChk the agreeChk to set
	 */
	public void setAgreeChk(Boolean agreeChk) {
		this.agreeChk = agreeChk;
	}
	/**
	 * @return the reservTimeAdd
	 */
	public Integer getReservTimeAdd() {
		return reservTimeAdd;
	}
	/**
	 * @param reservTimeAdd the reservTimeAdd to set
	 */
	public void setReservTimeAdd(Integer reservTimeAdd) {
		this.reservTimeAdd = reservTimeAdd;
	}
	/**
	 * @return the emailTemp
	 */
	public Boolean getEmailTemp() {
		return emailTemp;
	}
	/**
	 * @param emailTemp the emailTemp to set
	 */
	public void setEmailTemp(Boolean emailTemp) {
		this.emailTemp = emailTemp;
	}
	/**
	 * @return the reservMembership
	 */
	public boolean isReservMembership() {
		return reservMembership;
	}
	/**
	 * @param reservMembership the reservMembership to set
	 */
	public void setReservMembership(boolean reservMembership) {
		this.reservMembership = reservMembership;
	}
	/**
	 * @return the reservRequests
	 */
	public String getReservRequests() {
		return reservRequests;
	}
	/**
	 * @param reservRequests the reservRequests to set
	 */
	public void setReservRequests(String reservRequests) {
		this.reservRequests = reservRequests;
	}
	/**
	 * @return the reservUserId
	 */
	public String getReservUserId() {
		return reservUserId;
	}
	/**
	 * @param reservUserId the reservUserId to set
	 */
	public void setReservUserId(String reservUserId) {
		this.reservUserId = reservUserId;
	}
	/**
	 * @return the reservUsageTime
	 */
	public Integer getReservUsageTime() {
		return reservUsageTime;
	}
	/**
	 * @param reservUsageTime the reservUsageTime to set
	 */
	public void setReservUsageTime(Integer reservUsageTime) {
		this.reservUsageTime = reservUsageTime;
	}
	/**
	 * @return the reservCancelDate
	 */
	public String getReservCancelDate() {
		return reservCancelDate;
	}
	/**
	 * @param reservCancelDate the reservCancelDate to set
	 */
	public void setReservCancelDate(String reservCancelDate) {
		this.reservCancelDate = reservCancelDate;
	}
	/**
	 * @return the reservApprovDate
	 */
	public String getReservApprovDate() {
		return reservApprovDate;
	}
	/**
	 * @param reservApprovDate the reservApprovDate to set
	 */
	public void setReservApprovDate(String reservApprovDate) {
		this.reservApprovDate = reservApprovDate;
	}
	/**
	 * @return the reservPaymentDate
	 */
	public String getReservPaymentDate() {
		return reservPaymentDate;
	}
	/**
	 * @param reservPaymentDate the reservPaymentDate to set
	 */
	public void setReservPaymentDate(String reservPaymentDate) {
		this.reservPaymentDate = reservPaymentDate;
	}
	/**
	 * @return the reservPayCancelDate
	 */
	public String getReservPayCancelDate() {
		return reservPayCancelDate;
	}
	/**
	 * @param reservPayCancelDate the reservPayCancelDate to set
	 */
	public void setReservPayCancelDate(String reservPayCancelDate) {
		this.reservPayCancelDate = reservPayCancelDate;
	}
	/**
	 * @return the reservTid
	 */
	public String getReservTid() {
		return reservTid;
	}
	/**
	 * @param reservTid the reservTid to set
	 */
	public void setReservTid(String reservTid) {
		this.reservTid = reservTid;
	}
	
	
}
