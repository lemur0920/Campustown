/**
 * 
 */
package egovframework.com.asapro.rental.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.apache.commons.lang3.StringUtils;

import egovframework.com.asapro.support.pagination.PagingSearch;

/**
 * 대관/대여 검색
 * @author yckim
 * @since 2018. 10. 29.
 *
 */
public class RentalReservationSearch extends PagingSearch{

	private Integer reservRentId;	//대관/대여 아이디
	private String reservOrgDivCode;	//신청기관 구분 코드 (광고계, 문화예술계, 입주사, 일반회사 및 개인) (코드)
	private String reservPaidType;	//결제방법(코드)
	private String reservStatus;	//신청상태(코드)
	private String reservStatusPattern;	//신청상태 패턴 - 상태별 묶어서 검색할 경우 사용(ex. 예약취소 -> 사용자,관리자,자동 등을 묶어서)
	private String reservStartDate;	//사용일 시작-관리자단
	private String reservEndDate;	//사용일 종료-관리자단
	private String possibleStatus;	//가능,불가능 상태 
	private String reservUserId;	//신청자 아이디 - 신청자 아이디로 목록을 불러오는 용도 
	
	private String reservYear;	//  검색연도-사용자단 달력
	private String reservMonth;	//검색월 -사용자단 달력
	private String today;	//오늘
	private String todayYear;	//오늘의 년도
	private String todayMonth;	//오늘의 월
	private String preYear;	//이전년도
	private String preMonth;	//이전월
	private String nextYear;	//다음년도
	private String nextMonth;	//다음월
	
	private boolean agreeChk;	//개인정보수집동의 여부
	
	/**
	 * @return the reservRentId
	 */
	public Integer getReservRentId() {
		return reservRentId;
	}
	/**
	 * @param reservRentId the reservRentId to set
	 */
	public void setReservRentId(Integer reservRentId) {
		this.reservRentId = reservRentId;
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
	 * @return the reservStatusPattern
	 */
	public String getReservStatusPattern() {
		return reservStatusPattern;
	}
	/**
	 * @param reservStatusPattern the reservStatusPattern to set
	 */
	public void setReservStatusPattern(String reservStatusPattern) {
		this.reservStatusPattern = reservStatusPattern;
	}
	/**
	 * @return the reservStartDate
	 */
	public String getReservStartDate() {
		return reservStartDate;
	}
	/**
	 * @param reservStartDate the reservStartDate to set
	 */
	public void setReservStartDate(String reservStartDate) {
		this.reservStartDate = reservStartDate;
	}
	/**
	 * @return the reservEndDate
	 */
	public String getReservEndDate() {
		return reservEndDate;
	}
	/**
	 * @param reservEndDate the reservEndDate to set
	 */
	public void setReservEndDate(String reservEndDate) {
		this.reservEndDate = reservEndDate;
	}
	/**
	 * @return the possibleStatus
	 */
	public String getPossibleStatus() {
		return possibleStatus;
	}
	/**
	 * @param possibleStatus the possibleStatus to set
	 */
	public void setPossibleStatus(String possibleStatus) {
		this.possibleStatus = possibleStatus;
	}
	/**
	 * @return the reservYear
	 */
	public String getReservYear() {
		return reservYear;
	}
	/**
	 * @param reservYear the reservYear to set
	 */
	public void setReservYear(String reservYear) {
		this.reservYear = reservYear;
	}
	/**
	 * @return the reservMonth
	 */
	public String getReservMonth() {
		return reservMonth;
	}
	/**
	 * @param reservMonth the reservMonth to set
	 */
	public void setReservMonth(String reservMonth) {
		this.reservMonth = reservMonth;
	}
	/**
	 * @return the today
	 */
	public String getToday() {
		SimpleDateFormat now = new SimpleDateFormat("yyyy-MM-dd", Locale.KOREA);
		today = now.format(new Date());
		return today;
	}
	/**
	 * @param today the today to set
	 */
	public void setToday(String today) {
		this.today = today;
	}
	/**
	 * @return the preYear
	 */
	public String getPreYear() {
		return preYear;
	}
	/**
	 * @param preYear the preYear to set
	 */
	public void setPreYear(String preYear) {
		this.preYear = preYear;
	}
	/**
	 * @return the preMonth
	 */
	public String getPreMonth() {
		return preMonth;
	}
	/**
	 * @param preMonth the preMonth to set
	 */
	public void setPreMonth(String preMonth) {
		this.preMonth = preMonth;
	}
	/**
	 * @return the nextYear
	 */
	public String getNextYear() {
		return nextYear;
	}
	/**
	 * @param nextYear the nextYear to set
	 */
	public void setNextYear(String nextYear) {
		this.nextYear = nextYear;
	}
	/**
	 * @return the nextMonth
	 */
	public String getNextMonth() {
		return nextMonth;
	}
	/**
	 * @param nextMonth the nextMonth to set
	 */
	public void setNextMonth(String nextMonth) {
		this.nextMonth = nextMonth;
	}
	
	/**
	 * @return the todayYear
	 */
	public String getTodayYear() {
		SimpleDateFormat now = new SimpleDateFormat("yyyy", Locale.KOREA);
		todayYear = now.format(new Date());
		return todayYear;
	}
	/**
	 * @param todayYear the todayYear to set
	 */
	public void setTodayYear(String todayYear) {
		this.todayYear = todayYear;
	}
	/**
	 * @return the todayMonth
	 */
	public String getTodayMonth() {
		SimpleDateFormat now = new SimpleDateFormat("MM", Locale.KOREA);
		todayMonth = now.format(new Date());
		return todayMonth;
	}
	/**
	 * @param todayMonth the todayMonth to set
	 */
	public void setTodayMonth(String todayMonth) {
		this.todayMonth = todayMonth;
	}
	
	/**
	 * @return the agreeChk
	 */
	public boolean isAgreeChk() {
		return agreeChk;
	}
	/**
	 * @param agreeChk the agreeChk to set
	 */
	public void setAgreeChk(boolean agreeChk) {
		this.agreeChk = agreeChk;
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
	 * 달력의 이전달, 다음달 셋팅
	 */
	public void setPreNextYearMonthSet(){
		if( this.getReservMonth().equals("01") ){
			this.setPreYear( String.format("%04d", (Integer.parseInt(this.getReservYear()) - 1)) );
			this.setPreMonth("12");
			this.setNextYear(this.getReservYear());
			this.setNextMonth("02");
		} else if( this.getReservMonth().equals("12") ){
			this.setPreYear(this.getReservYear());
			this.setPreMonth("11");
			this.setNextYear(String.format("%04d", (Integer.parseInt(this.getReservYear()) + 1)) );
			this.setNextMonth("01");
		} else {
			this.setPreYear(this.getReservYear());
			this.setPreMonth(String.format("%02d", (Integer.parseInt(this.getReservMonth()) - 1)) );
			this.setNextYear(this.getReservYear());
			this.setNextMonth(String.format("%02d", (Integer.parseInt(this.getReservMonth()) + 1)));
		}
	}

	/* (non-Javadoc)
	 * @see egovframework.com.asapro.support.pagination.PagingSearch#getQueryString()
	 */
	
	
	
	@Override
	public String getQueryString() {
		
		StringBuilder sb = new StringBuilder(100);
		sb.append(super.getQueryString());
		
		if( this.getReservRentId() != null && this.getReservRentId() > 0){
			sb.append("&amp;reservRentId=");
			sb.append(this.getReservRentId());
		}
		
		if( StringUtils.isNotBlank(this.getReservOrgDivCode()) ){
			sb.append("&amp;reservOrgDivCode=");
			sb.append(this.getReservOrgDivCode());
		}
		
		if( StringUtils.isNotBlank(this.getReservPaidType()) ){
			sb.append("&amp;reservPaidType=");
			sb.append(this.getReservPaidType());
		}
		
		if( StringUtils.isNotBlank(this.getReservStatus()) ){
			sb.append("&amp;reservStatus=");
			sb.append(this.getReservStatus());
		}
		
		if( StringUtils.isNotBlank(this.getReservStartDate()) ){
			sb.append("&amp;reservStartDate=");
			sb.append(this.getReservStartDate());
		}
		
		if( StringUtils.isNotBlank(this.getReservEndDate()) ){
			sb.append("&amp;reservEndDate=");
			sb.append(this.getReservEndDate());
		}
		if( StringUtils.isNotBlank(this.getPossibleStatus()) ){
			sb.append("&amp;possibleStatus=");
			sb.append(this.getPossibleStatus());
		}
		if( StringUtils.isNotBlank(this.getReservYear()) ){
			sb.append("&amp;reservYear=");
			sb.append(this.getReservYear());
		}
		if( StringUtils.isNotBlank(this.getReservMonth()) ){
			sb.append("&amp;reservMonth=");
			sb.append(this.getReservMonth());
		}
		
		return sb.toString();
	}
	
	
}
