/**
 * 
 */
package egovframework.com.asapro.volunteer.service;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import egovframework.com.asapro.site.service.MultiSiteVO;


/**
 * 자원봉사 신청정보 VO
 * @author yckim
 * @since 2018. 12. 12.
 *
 */
public class VolunteerReservation extends MultiSiteVO{
	
	private Integer reservId;	//신청정보 아이디
	private String reservName;	//신청자 이름
	private String reservGroup;	//신청자 그룹
	private String reservAddress;	//신청자 주소
	private String reservEmail;	//신청자 이메일
	private Integer reservPeople;	//인원
	private String reservPurpose;	//목적
	private String reservDate;	//봉사일
	private String reservStime;	//봉사시작시간
	private String reservEtime;	//봉사종료시간
	private String reservHp;	//신청자 연락처
	private String reservHp1;	//신청자 연락처1
	private String reservHp2;	//신청자 연락처2
	private String reservHp3;	//신청자 연락처3
	private String reservSuggest;	//건의사항
	private String reservStatus;	//상태
	private Date reservRegdate;	//등록일시
	private Boolean reservAgree;	//개인정보이용동의
	
	/**
	 * @return the reservId
	 */
	public Integer getReservId() {
		return reservId;
	}
	/**
	 * @param reservId the reservId to set
	 */
	public void setReservId(Integer reservId) {
		this.reservId = reservId;
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
	 * @return the reservGroup
	 */
	public String getReservGroup() {
		return reservGroup;
	}
	/**
	 * @param reservGroup the reservGroup to set
	 */
	public void setReservGroup(String reservGroup) {
		this.reservGroup = reservGroup;
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
	 * @return the reservPeople
	 */
	public Integer getReservPeople() {
		return reservPeople;
	}
	/**
	 * @param reservPeople the reservPeople to set
	 */
	public void setReservPeople(Integer reservPeople) {
		this.reservPeople = reservPeople;
	}
	/**
	 * @return the reservPurpose
	 */
	public String getReservPurpose() {
		return reservPurpose;
	}
	/**
	 * @param reservPurpose the reservPurpose to set
	 */
	public void setReservPurpose(String reservPurpose) {
		this.reservPurpose = reservPurpose;
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
	 * @return the reservStime
	 */
	public String getReservStime() {
		return reservStime;
	}
	/**
	 * @param reservStime the reservStime to set
	 */
	public void setReservStime(String reservStime) {
		this.reservStime = reservStime;
	}
	/**
	 * @return the reservEtime
	 */
	public String getReservEtime() {
		return reservEtime;
	}
	/**
	 * @param reservEtime the reservEtime to set
	 */
	public void setReservEtime(String reservEtime) {
		this.reservEtime = reservEtime;
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
	 * @return the reservSuggest
	 */
	public String getReservSuggest() {
		return reservSuggest;
	}
	/**
	 * @param reservSuggest the reservSuggest to set
	 */
	public void setReservSuggest(String reservSuggest) {
		this.reservSuggest = reservSuggest;
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
	 * @return the reservRegdate
	 */
	public Date getReservRegdate() {
		return reservRegdate;
	}
	/**
	 * @param reservRegdate the reservRegdate to set
	 */
	public void setReservRegdate(Date reservRegdate) {
		this.reservRegdate = reservRegdate;
	}
	/**
	 * @return the reservAgree
	 */
	public Boolean getReservAgree() {
		return reservAgree;
	}
	/**
	 * @param reservAgree the reservAgree to set
	 */
	public void setReservAgree(Boolean reservAgree) {
		this.reservAgree = reservAgree;
	}
	
	
	
	
	
}
