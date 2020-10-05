/**
 * 
 */
package egovframework.com.asapro.organization.service;

import java.io.Serializable;
import java.util.Date;

/**
 * 개층구조 부서 VO
 * 
 * @author yckim
 * @since 2020. 4. 07.
 */
@SuppressWarnings("serial")
public class DepartmentView  implements Serializable{
	private Integer depLevel;	//부서레벨
	private String depId;	//부서코드
	private String depName;	//부서명
	private Integer depOrder;	//출력순서
	private String depTel;	//부서 전화번호
	private String depDescription;	//부서소개
	private String parentId;	//부모부서 아이디
	private String depType;	//조직구분타입 - org,dep,team
	/**
	 * @return the depLevel
	 */
	public Integer getDepLevel() {
		return depLevel;
	}
	/**
	 * @param depLevel the depLevel to set
	 */
	public void setDepLevel(Integer depLevel) {
		this.depLevel = depLevel;
	}
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
	 * @return the parentId
	 */
	public String getParentId() {
		return parentId;
	}
	/**
	 * @param parentId the parentId to set
	 */
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	/**
	 * @return the depType
	 */
	public String getDepType() {
		return depType;
	}
	/**
	 * @param depType the depType to set
	 */
	public void setDepType(String depType) {
		this.depType = depType;
	}
	

	
}
