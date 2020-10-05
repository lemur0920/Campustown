package egovframework.com.asapro.openapiinfo.api.service;

import egovframework.com.asapro.site.service.MultiSiteVO;

public class YouthProgram extends MultiSiteVO {
	private Integer sequence; //테이블 키 값
	private String supportBizCode; //지원_사업 코드
	private String title; //제목
	private String contents; //내용
	private String receptionStartDate; //접수 시작일
	private String receptionEndDate; //접수 종료일
	private String educationStartDate; //교육 시작일
	private String educationEndDate; //교육 종료일
	private String lineExplanation; //한줄 설명
	private String fee; //참가비
	private Integer personnel; //정원
	private Integer participantNumber; //참가자 수
	private String applicationLink; //신청링크
	private String fileUrl; //파일_URL(첨부파일, 이미지)
	private String openYn; //공개_여부
	private String delYn; //삭제_유무
	private String regName; //등록_명
	private String regDate; //등록_일
	private String updName; //수정_명
	private String updDate; //수정_일
	
	/**
	 * @return the sequence
	 */
	public Integer getSequence() {
		return sequence;
	}
	/**
	 * @param sequence the sequence to set
	 */
	public void setSequence(Integer sequence) {
		this.sequence = sequence;
	}
	/**
	 * @return the supportBizCode
	 */
	public String getSupportBizCode() {
		return supportBizCode;
	}
	/**
	 * @param supportBizCode the supportBizCode to set
	 */
	public void setSupportBizCode(String supportBizCode) {
		this.supportBizCode = supportBizCode;
	}
	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * @return the contents
	 */
	public String getContents() {
		return contents;
	}
	/**
	 * @param contents the contents to set
	 */
	public void setContents(String contents) {
		this.contents = contents;
	}
	/**
	 * @return the receptionStartDate
	 */
	public String getReceptionStartDate() {
		return receptionStartDate;
	}
	/**
	 * @param receptionStartDate the receptionStartDate to set
	 */
	public void setReceptionStartDate(String receptionStartDate) {
		this.receptionStartDate = receptionStartDate;
	}
	/**
	 * @return the receptionEndDate
	 */
	public String getReceptionEndDate() {
		return receptionEndDate;
	}
	/**
	 * @param receptionEndDate the receptionEndDate to set
	 */
	public void setReceptionEndDate(String receptionEndDate) {
		this.receptionEndDate = receptionEndDate;
	}
	/**
	 * @return the educationStartDate
	 */
	public String getEducationStartDate() {
		return educationStartDate;
	}
	/**
	 * @param educationStartDate the educationStartDate to set
	 */
	public void setEducationStartDate(String educationStartDate) {
		this.educationStartDate = educationStartDate;
	}
	/**
	 * @return the educationEndDate
	 */
	public String getEducationEndDate() {
		return educationEndDate;
	}
	/**
	 * @param educationEndDate the educationEndDate to set
	 */
	public void setEducationEndDate(String educationEndDate) {
		this.educationEndDate = educationEndDate;
	}
	/**
	 * @return the lineExplanation
	 */
	public String getLineExplanation() {
		return lineExplanation;
	}
	/**
	 * @param lineExplanation the lineExplanation to set
	 */
	public void setLineExplanation(String lineExplanation) {
		this.lineExplanation = lineExplanation;
	}
	/**
	 * @return the fee
	 */
	public String getFee() {
		return fee;
	}
	/**
	 * @param fee the fee to set
	 */
	public void setFee(String fee) {
		this.fee = fee;
	}
	/**
	 * @return the personnel
	 */
	public Integer getPersonnel() {
		return personnel;
	}
	/**
	 * @param personnel the personnel to set
	 */
	public void setPersonnel(Integer personnel) {
		this.personnel = personnel;
	}
	/**
	 * @return the participantNumber
	 */
	public Integer getParticipantNumber() {
		return participantNumber;
	}
	/**
	 * @param participantNumber the participantNumber to set
	 */
	public void setParticipantNumber(Integer participantNumber) {
		this.participantNumber = participantNumber;
	}
	/**
	 * @return the applicationLink
	 */
	public String getApplicationLink() {
		return applicationLink;
	}
	/**
	 * @param applicationLink the applicationLink to set
	 */
	public void setApplicationLink(String applicationLink) {
		this.applicationLink = applicationLink;
	}
	/**
	 * @return the fileUrl
	 */
	public String getFileUrl() {
		return fileUrl;
	}
	/**
	 * @param fileUrl the fileUrl to set
	 */
	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}
	/**
	 * @return the openYn
	 */
	public String getOpenYn() {
		return openYn;
	}
	/**
	 * @param openYn the openYn to set
	 */
	public void setOpenYn(String openYn) {
		this.openYn = openYn;
	}
	/**
	 * @return the delYn
	 */
	public String getDelYn() {
		return delYn;
	}
	/**
	 * @param delYn the delYn to set
	 */
	public void setDelYn(String delYn) {
		this.delYn = delYn;
	}
	/**
	 * @return the regName
	 */
	public String getRegName() {
		return regName;
	}
	/**
	 * @param regName the regName to set
	 */
	public void setRegName(String regName) {
		this.regName = regName;
	}
	/**
	 * @return the regDate
	 */
	public String getRegDate() {
		return regDate;
	}
	/**
	 * @param regDate the regDate to set
	 */
	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}
	/**
	 * @return the updName
	 */
	public String getUpdName() {
		return updName;
	}
	/**
	 * @param updName the updName to set
	 */
	public void setUpdName(String updName) {
		this.updName = updName;
	}
	/**
	 * @return the updDate
	 */
	public String getUpdDate() {
		return updDate;
	}
	/**
	 * @param updDate the updDate to set
	 */
	public void setUpdDate(String updDate) {
		this.updDate = updDate;
	}
	
	
}
