/**
 * 
 */
package egovframework.com.asapro.organization.service;

import java.io.Serializable;
import java.util.Date;

/**
 * 부서 VO
 * 
 * @author yckim
 * @since 2018. 5. 11.
 */
@SuppressWarnings("serial")
public class Department  implements Serializable{
	private String depId;	//부서코드
	private String orgId;	//기관코드
	private String depName;	//부서명
	private String depNameEn;	//부서명 영문
	private String depTel;	//부서 전화번호
	private String depDescription;	//부서소개
	private boolean depUse = true;	//사용유무
	private Date depRegdate;	//등록일
	private Integer depOrder;	//순서
	
	private Organization organization;	//기관
	/**
	 * @return the depId
	 */
	public String getDepId() {
		return depId;
	}
	/**
	 * @param depId the depId to set
	 */
	public void setDepId(String depId) {
		this.depId = depId;
	}
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
	 * @return the depName
	 */
	public String getDepName() {
		return depName;
	}
	/**
	 * @param depName the depName to set
	 */
	public void setDepName(String depName) {
		this.depName = depName;
	}
	/**
	 * @return the depNameEn
	 */
	public String getDepNameEn() {
		return depNameEn;
	}
	/**
	 * @param depNameEn the depNameEn to set
	 */
	public void setDepNameEn(String depNameEn) {
		this.depNameEn = depNameEn;
	}
	/**
	 * @return the depTel
	 */
	public String getDepTel() {
		return depTel;
	}
	/**
	 * @param depTel the depTel to set
	 */
	public void setDepTel(String depTel) {
		this.depTel = depTel;
	}
	/**
	 * @return the depDescription
	 */
	public String getDepDescription() {
		return depDescription;
	}
	/**
	 * @param depDescription the depDescription to set
	 */
	public void setDepDescription(String depDescription) {
		this.depDescription = depDescription;
	}
	/**
	 * @return the depUse
	 */
	public boolean isDepUse() {
		return depUse;
	}
	/**
	 * @param depUse the depUse to set
	 */
	public void setDepUse(boolean depUse) {
		this.depUse = depUse;
	}
	/**
	 * @return the depRegdate
	 */
	public Date getDepRegdate() {
		return depRegdate;
	}
	/**
	 * @param depRegdate the depRegdate to set
	 */
	public void setDepRegdate(Date depRegdate) {
		this.depRegdate = depRegdate;
	}
	/**
	 * @return the depOrder
	 */
	public Integer getDepOrder() {
		return depOrder;
	}
	/**
	 * @param depOrder the depOrder to set
	 */
	public void setDepOrder(Integer depOrder) {
		this.depOrder = depOrder;
	}
	/**
	 * @return the organization
	 */
	public Organization getOrganization() {
		return organization;
	}
	/**
	 * @param organization the organization to set
	 */
	public void setOrganization(Organization organization) {
		this.organization = organization;
	}

	
}
