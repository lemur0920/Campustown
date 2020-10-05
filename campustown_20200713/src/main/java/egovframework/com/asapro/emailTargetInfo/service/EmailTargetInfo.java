/**
 * 
 */
package egovframework.com.asapro.emailTargetInfo.service;

import java.io.Serializable;
import java.util.Date;

import egovframework.com.asapro.site.service.MultiSiteVO;


/**
 * 메일대상 VO
 * @author yckim
 * @since 2019. 12. 11.
 *
 */
public class EmailTargetInfo extends MultiSiteVO {
	private  String etId;	//메일대상 아이디
	private  String etCate;	//구분코드
	private  String etTitle;	//제목
	private  String etTarget;	//수신자
	private  String etContents;	//내용
	private  String etForm;	//발송 폼
	private  Date etRegDate;	//등록일시
	
	/**
	 * @return the etId
	 */
	public String getEtId() {
		return etId;
	}
	/**
	 * @param etId the etId to set
	 */
	public void setEtId(String etId) {
		this.etId = etId;
	}
	/**
	 * @return the etCate
	 */
	public String getEtCate() {
		return etCate;
	}
	/**
	 * @param etCate the etCate to set
	 */
	public void setEtCate(String etCate) {
		this.etCate = etCate;
	}
	/**
	 * @return the etTitle
	 */
	public String getEtTitle() {
		return etTitle;
	}
	/**
	 * @param etTitle the etTitle to set
	 */
	public void setEtTitle(String etTitle) {
		this.etTitle = etTitle;
	}
	/**
	 * @return the etTarget
	 */
	public String getEtTarget() {
		return etTarget;
	}
	/**
	 * @param etTarget the etTarget to set
	 */
	public void setEtTarget(String etTarget) {
		this.etTarget = etTarget;
	}
	/**
	 * @return the etContents
	 */
	public String getEtContents() {
		return etContents;
	}
	/**
	 * @param etContents the etContents to set
	 */
	public void setEtContents(String etContents) {
		this.etContents = etContents;
	}
	/**
	 * @return the etForm
	 */
	public String getEtForm() {
		return etForm;
	}
	/**
	 * @param etForm the etForm to set
	 */
	public void setEtForm(String etForm) {
		this.etForm = etForm;
	}
	/**
	 * @return the etRegDate
	 */
	public Date getEtRegDate() {
		return etRegDate;
	}
	/**
	 * @param etRegDate the etRegDate to set
	 */
	public void setEtRegDate(Date etRegDate) {
		this.etRegDate = etRegDate;
	}
	
}
