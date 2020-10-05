/**
 * 
 */
package egovframework.com.asapro.allowip.service;

import java.io.Serializable;
import java.util.Date;

import egovframework.com.asapro.site.service.MultiSiteVO;




/**
 * 임시 접속허용 IP VO
 * @author yckim
 * @since 2019. 5. 21.
 *
 */
public class AllowIpTemp extends MultiSiteVO {
	
	private String adminId;	//관리자 아이디
	private String tempIp;	//임시허용 아이피
	private String adminHp;	//관리자 휴대폰번호
	private String certiNum;	//인증번호
	private Date tempRegdate;	//등록일시
	private Date certiRequestDate;	//인증번호 요청일시
	private Date certiCompletDate;	//인증완료일시
	private boolean authentication;	//인증완료여부
	private int smsSendCnt = 0;	//sms 발송 횟수
	private int authentiFailCnt = 0;	//인증실패 횟수
	
	private String adminPassword = "";	//관리자 비번 - 컬럼 없음
	
	/**
	 * @return the adminId
	 */
	public String getAdminId() {
		return adminId;
	}
	/**
	 * @param adminId the adminId to set
	 */
	public void setAdminId(String adminId) {
		this.adminId = adminId;
	}
	/**
	 * @return the tempIp
	 */
	public String getTempIp() {
		return tempIp;
	}
	/**
	 * @param tempIp the tempIp to set
	 */
	public void setTempIp(String tempIp) {
		this.tempIp = tempIp;
	}
	/**
	 * @return the adminHp
	 */
	public String getAdminHp() {
		return adminHp;
	}
	/**
	 * @param adminHp the adminHp to set
	 */
	public void setAdminHp(String adminHp) {
		this.adminHp = adminHp;
	}
	/**
	 * @return the certiNum
	 */
	public String getCertiNum() {
		return certiNum;
	}
	/**
	 * @param certiNum the certiNum to set
	 */
	public void setCertiNum(String certiNum) {
		this.certiNum = certiNum;
	}
	
	/**
	 * @return the tempRegdate
	 */
	public Date getTempRegdate() {
		return tempRegdate;
	}
	/**
	 * @param tempRegdate the tempRegdate to set
	 */
	public void setTempRegdate(Date tempRegdate) {
		this.tempRegdate = tempRegdate;
	}
	/**
	 * @return the certiRequestDate
	 */
	public Date getCertiRequestDate() {
		return certiRequestDate;
	}
	/**
	 * @param certiRequestDate the certiRequestDate to set
	 */
	public void setCertiRequestDate(Date certiRequestDate) {
		this.certiRequestDate = certiRequestDate;
	}
	/**
	 * @return the certiCompletDate
	 */
	public Date getCertiCompletDate() {
		return certiCompletDate;
	}
	/**
	 * @param certiCompletDate the certiCompletDate to set
	 */
	public void setCertiCompletDate(Date certiCompletDate) {
		this.certiCompletDate = certiCompletDate;
	}
	/**
	 * @return the authentication
	 */
	public boolean isAuthentication() {
		return authentication;
	}
	/**
	 * @param authentication the authentication to set
	 */
	public void setAuthentication(boolean authentication) {
		this.authentication = authentication;
	}
	/**
	 * @return the adminPassword
	 */
	public String getAdminPassword() {
		return adminPassword;
	}
	/**
	 * @param adminPassword the adminPassword to set
	 */
	public void setAdminPassword(String adminPassword) {
		this.adminPassword = adminPassword;
	}
	/**
	 * @return the smsSendCnt
	 */
	public int getSmsSendCnt() {
		return smsSendCnt;
	}
	/**
	 * @param smsSendCnt the smsSendCnt to set
	 */
	public void setSmsSendCnt(int smsSendCnt) {
		this.smsSendCnt = smsSendCnt;
	}
	/**
	 * @return the authentiFailCnt
	 */
	public int getAuthentiFailCnt() {
		return authentiFailCnt;
	}
	/**
	 * @param authentiFailCnt the authentiFailCnt to set
	 */
	public void setAuthentiFailCnt(int authentiFailCnt) {
		this.authentiFailCnt = authentiFailCnt;
	}
	
}
