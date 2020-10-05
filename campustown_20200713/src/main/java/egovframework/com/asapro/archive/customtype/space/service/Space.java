/**
 * 
 */
package egovframework.com.asapro.archive.customtype.space.service;


import egovframework.com.asapro.archive.service.Archive;
import egovframework.com.asapro.support.annotation.ArchiveItem;


/**
 * 공간정보 VO
 * 아카이브 커스텀타입
 * @author yckim
 * @since 2019. 2. 26.
 *
 */
@ArchiveItem(customType="space", label="공간정보")
public class Space extends Archive{
	
	private String spaTel;		//연락처
	private String spaLocation;	//위치
	
	// 현재 안 쓰는 컬럼
	private String spaUseHours;	//이용시간
	private String spaBusinessHours;	//업무시간
	private String spaAgency;	//운영기관
	private String spaSiteUrl;	//홈페이지 url
	
	// jaseo 2020.03.28. 추가 컬럼
	private String spaEmail; 	//이메일 주소
	private String spaComment;  //코멘트 

	
	/**
	 * @return the spaLocation
	 */
	public String getSpaLocation() {
		return spaLocation;
	}
	/**
	 * @param spaLocation the spaLocation to set
	 */
	public void setSpaLocation(String spaLocation) {
		this.spaLocation = spaLocation;
	}
	/**
	 * @return the spaTel
	 */
	public String getSpaTel() {
		return spaTel;
	}
	/**
	 * @param spaTel the spaTel to set
	 */
	public void setSpaTel(String spaTel) {
		this.spaTel = spaTel;
	}
	/**
	 * @return the spaUseHours
	 */
	public String getSpaUseHours() {
		return spaUseHours;
	}
	/**
	 * @param spaUseHours the spaUseHours to set
	 */
	public void setSpaUseHours(String spaUseHours) {
		this.spaUseHours = spaUseHours;
	}
	/**
	 * @return the spaBusinessHours
	 */
	public String getSpaBusinessHours() {
		return spaBusinessHours;
	}
	/**
	 * @param spaBusinessHours the spaBusinessHours to set
	 */
	public void setSpaBusinessHours(String spaBusinessHours) {
		this.spaBusinessHours = spaBusinessHours;
	}
	/**
	 * @return the spaAgency
	 */
	public String getSpaAgency() {
		return spaAgency;
	}
	/**
	 * @param spaAgency the spaAgency to set
	 */
	public void setSpaAgency(String spaAgency) {
		this.spaAgency = spaAgency;
	}
	/**
	 * @return the spaSiteUrl
	 */
	public String getSpaSiteUrl() {
		return spaSiteUrl;
	}
	/**
	 * @param spaSiteUrl the spaSiteUrl to set
	 */
	public void setSpaSiteUrl(String spaSiteUrl) {
		this.spaSiteUrl = spaSiteUrl;
	}
	
	// 추가 컬럼s
	public String getSpaEmail() {
		return spaEmail;
	}
	public void setSpaEmail(String spaEmail) {
		this.spaEmail = spaEmail;
	}
	public String getSpaComment() {
		return spaComment;
	}
	public void setSpaComment(String spaComment) {
		this.spaComment = spaComment;
	}
	
	
	
	
}
