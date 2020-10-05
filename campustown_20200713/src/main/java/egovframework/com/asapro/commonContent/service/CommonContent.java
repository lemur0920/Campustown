/**
 * 
 */
package egovframework.com.asapro.commonContent.service;

import java.io.Serializable;
import java.util.Date;

import egovframework.com.asapro.member.admin.service.AdminMember;
import egovframework.com.asapro.site.service.MultiSiteVO;

/**
 * 공통 콘텐츠 VO
 * @author yckim
 * @since 2018. 8. 23.
 *
 */
@SuppressWarnings("serial")
public class CommonContent extends MultiSiteVO implements Serializable{
	
	private Integer comContId;	//아이디
	private String comContModule = "";	//적용모듈
	private String comContModuleSub = "";	//적용서브모듈
	private String comContCate1 = "";	//카테고리1
	private String comContCate2 = "";	//카테고리2
	private String comContCate3 = "";	//카테고리3
	private String comContTitle = "";	//제목
	private String comContSubTitle = "";	//간단설명
	private String comContContent = "";	//콘텐츠
	private String comContStatus = "public";	//노출상태 - public, draft
	private Date comContRegDate;	//등록일시
	private Date comContModifyDate;	//마지막 수정일시
	private AdminMember comContWorker;	//마지막 수정자
	/**
	 * @return the comContId
	 */
	public Integer getComContId() {
		return comContId;
	}
	/**
	 * @param comContId the comContId to set
	 */
	public void setComContId(Integer comContId) {
		this.comContId = comContId;
	}
	/**
	 * @return the comContModule
	 */
	public String getComContModule() {
		return comContModule;
	}
	/**
	 * @param comContModule the comContModule to set
	 */
	public void setComContModule(String comContModule) {
		this.comContModule = comContModule;
	}
	/**
	 * @return the comContModuleSub
	 */
	public String getComContModuleSub() {
		return comContModuleSub;
	}
	/**
	 * @param comContModuleSub the comContModuleSub to set
	 */
	public void setComContModuleSub(String comContModuleSub) {
		this.comContModuleSub = comContModuleSub;
	}
	
	/**
	 * @return the comContTitle
	 */
	public String getComContTitle() {
		return comContTitle;
	}
	/**
	 * @param comContTitle the comContTitle to set
	 */
	public void setComContTitle(String comContTitle) {
		this.comContTitle = comContTitle;
	}
	/**
	 * @return the comContContent
	 */
	public String getComContContent() {
		return comContContent;
	}
	/**
	 * @param comContContent the comContContent to set
	 */
	public void setComContContent(String comContContent) {
		this.comContContent = comContContent;
	}
	/**
	 * @return the comContStatus
	 */
	public String getComContStatus() {
		return comContStatus;
	}
	/**
	 * @param comContStatus the comContStatus to set
	 */
	public void setComContStatus(String comContStatus) {
		this.comContStatus = comContStatus;
	}
	/**
	 * @return the comContRegDate
	 */
	public Date getComContRegDate() {
		return comContRegDate;
	}
	/**
	 * @param comContRegDate the comContRegDate to set
	 */
	public void setComContRegDate(Date comContRegDate) {
		this.comContRegDate = comContRegDate;
	}
	/**
	 * @return the comContModifyDate
	 */
	public Date getComContModifyDate() {
		return comContModifyDate;
	}
	/**
	 * @param comContModifyDate the comContModifyDate to set
	 */
	public void setComContModifyDate(Date comContModifyDate) {
		this.comContModifyDate = comContModifyDate;
	}
	/**
	 * @return the comContWorker
	 */
	public AdminMember getComContWorker() {
		return comContWorker;
	}
	/**
	 * @param comContWorker the comContWorker to set
	 */
	public void setComContWorker(AdminMember comContWorker) {
		this.comContWorker = comContWorker;
	}
	/**
	 * @return the comContCate1
	 */
	public String getComContCate1() {
		return comContCate1;
	}
	/**
	 * @param comContCate1 the comContCate1 to set
	 */
	public void setComContCate1(String comContCate1) {
		this.comContCate1 = comContCate1;
	}
	/**
	 * @return the comContCate2
	 */
	public String getComContCate2() {
		return comContCate2;
	}
	/**
	 * @param comContCate2 the comContCate2 to set
	 */
	public void setComContCate2(String comContCate2) {
		this.comContCate2 = comContCate2;
	}
	/**
	 * @return the comContCate3
	 */
	public String getComContCate3() {
		return comContCate3;
	}
	/**
	 * @param comContCate3 the comContCate3 to set
	 */
	public void setComContCate3(String comContCate3) {
		this.comContCate3 = comContCate3;
	}
	/**
	 * @return the comContSubTitle
	 */
	public String getComContSubTitle() {
		return comContSubTitle;
	}
	/**
	 * @param comContSubTitle the comContSubTitle to set
	 */
	public void setComContSubTitle(String comContSubTitle) {
		this.comContSubTitle = comContSubTitle;
	}
	
	
}
