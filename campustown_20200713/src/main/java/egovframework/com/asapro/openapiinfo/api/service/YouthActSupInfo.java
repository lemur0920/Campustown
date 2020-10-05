package egovframework.com.asapro.openapiinfo.api.service;

import egovframework.com.asapro.site.service.MultiSiteVO;

public class YouthActSupInfo extends MultiSiteVO {
	private Integer sequence;	//테이블 키 값
	private String title;	//제목
	private String contents;	//내용
	private String fileUrl;	//파일_URL(첨부파일, 이미지)
	private String delYn;	//삭제_유무
	private String regName;	//등록_명
	private String regDate;	//등록_일
	private String updName;	//수정_명
	private String updDate;	//수정_일
	
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
