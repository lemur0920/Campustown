/**
 * 
 */
package egovframework.com.asapro.organization.service;

import java.io.Serializable;
import java.util.Date;

/**
 * 기관 VO
 * 
 * @author yckim
 * @since 2018. 5. 11.
 */

@SuppressWarnings("serial")
public class Organization implements Serializable {
	private String orgId;	//기관아이디
	private String orgName;	//기관명
	private String orgNameEn;	//기관명 영문
	private String orgDescription;	//기관 소개
	private boolean orgUse = true;	//사용유뮤
	private Date orgRegdate;	//등록일
	private Integer orgOrder;	//순서
	/**
	 * @return the orgId
	 */
	public String getOrgId() {
		return orgId;
	}
	/**
	 * @param orgId the orgId to set
	 */
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	/**
	 * @return the orgName
	 */
	public String getOrgName() {
		return orgName;
	}
	/**
	 * @param orgName the orgName to set
	 */
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	/**
	 * @return the orgNameEn
	 */
	public String getOrgNameEn() {
		return orgNameEn;
	}
	/**
	 * @param orgNameEn the orgNameEn to set
	 */
	public void setOrgNameEn(String orgNameEn) {
		this.orgNameEn = orgNameEn;
	}
	/**
	 * @return the orgDescription
	 */
	public String getOrgDescription() {
		return orgDescription;
	}
	/**
	 * @param orgDescription the orgDescription to set
	 */
	public void setOrgDescription(String orgDescription) {
		this.orgDescription = orgDescription;
	}
	/**
	 * @return the orgUse
	 */
	public boolean isOrgUse() {
		return orgUse;
	}
	/**
	 * @param orgUse the orgUse to set
	 */
	public void setOrgUse(boolean orgUse) {
		this.orgUse = orgUse;
	}
	/**
	 * @return the orgRegdate
	 */
	public Date getOrgRegdate() {
		return orgRegdate;
	}
	/**
	 * @param orgRegdate the orgRegdate to set
	 */
	public void setOrgRegdate(Date orgRegdate) {
		this.orgRegdate = orgRegdate;
	}
	/**
	 * @return the orgOrder
	 */
	public Integer getOrgOrder() {
		return orgOrder;
	}
	/**
	 * @param orgOrder the orgOrder to set
	 */
	public void setOrgOrder(Integer orgOrder) {
		this.orgOrder = orgOrder;
	}


	
	
}
