/**
 * 
 */
package egovframework.com.asapro.capability.service;

import java.io.Serializable;
import java.util.Date;

import egovframework.com.asapro.site.service.MultiSiteVO;
//import egovframework.com.asapro.watchdog.aop.WatchDog;

/**
 * 권한 url과 파라메터 정보
 * @author yckim
 * @since 2018. 5. 17.
 *
 */

@SuppressWarnings("serial")
public class Capability extends MultiSiteVO implements Serializable{
	
//	@WatchDog
	private String capId;	//권한 아이디
	private String capName;	//권한 이름
	private String capDescription;	//권한 설명
	private String capValue;	//권한 값
	private String capParam1Key;	//추가 파라메터1 키
	private String capParam1Value;	//추가 파라메터1 값
	private String capParam2Key;	//추가 파라메터2 키
	private String capParam2Value;	//추가 파라케너2 값
	private String capHttpMethod;	//http 메소드 - ALL or GET or POST
	private Date capRegDate;	//등록일시
	private boolean capDefault;	//시스템기본권한 여부
	private int capPriority = 10;	//우선순위-값이 큰 권한이 먼저 체크됨 기본값 10, 범위가 큰 권한일수록 값이 작음 ex) /admin/.* => 1 VS /admin/aaa/bbb.do => 20
	private boolean capHidden = false;	//숨김여부 - 보여줄필요 없는 권한의 경우 디비에서 컬럼값 수정하는 방식으로 처리

	
	/**
	 * @return the capId
	 */
	public String getCapId() {
		return capId;
	}
	/**
	 * @param capId the capId to set
	 */
	public void setCapId(String capId) {
		this.capId = capId;
	}
	/**
	 * @return the capName
	 */
	public String getCapName() {
		return capName;
	}
	/**
	 * @param capName the capName to set
	 */
	public void setCapName(String capName) {
		this.capName = capName;
	}
	/**
	 * @return the capValue
	 */
	public String getCapValue() {
		return capValue;
	}
	/**
	 * @param capValue the capValue to set
	 */
	public void setCapValue(String capValue) {
		this.capValue = capValue;
	}
	/**
	 * @return the capDescription
	 */
	public String getCapDescription() {
		return capDescription;
	}
	/**
	 * @param capDescription the capDescription to set
	 */
	public void setCapDescription(String capDescription) {
		this.capDescription = capDescription;
	}
	/**
	 * @return the capRegDate
	 */
	public Date getCapRegDate() {
		return capRegDate;
	}
	/**
	 * @param capRegDate the capRegDate to set
	 */
	public void setCapRegDate(Date capRegDate) {
		this.capRegDate = capRegDate;
	}
	/**
	 * @return the capParam1Key
	 */
	public String getCapParam1Key() {
		return capParam1Key;
	}
	/**
	 * @param capParam1Key the capParam1Key to set
	 */
	public void setCapParam1Key(String capParam1Key) {
		this.capParam1Key = capParam1Key;
	}
	/**
	 * @return the capParam1Value
	 */
	public String getCapParam1Value() {
		return capParam1Value;
	}
	/**
	 * @param capParam1Value the capParam1Value to set
	 */
	public void setCapParam1Value(String capParam1Value) {
		this.capParam1Value = capParam1Value;
	}
	/**
	 * @return the capParam2Key
	 */
	public String getCapParam2Key() {
		return capParam2Key;
	}
	/**
	 * @param capParam2Key the capParam2Key to set
	 */
	public void setCapParam2Key(String capParam2Key) {
		this.capParam2Key = capParam2Key;
	}
	/**
	 * @return the capParam2Value
	 */
	public String getCapParam2Value() {
		return capParam2Value;
	}
	/**
	 * @param capParam2Value the capParam2Value to set
	 */
	public void setCapParam2Value(String capParam2Value) {
		this.capParam2Value = capParam2Value;
	}
	/**
	 * @return the capHttpMethod
	 */
	public String getCapHttpMethod() {
		return capHttpMethod;
	}
	/**
	 * @param capHttpMethod the capHttpMethod to set
	 */
	public void setCapHttpMethod(String capHttpMethod) {
		this.capHttpMethod = capHttpMethod;
	}
	/**
	 * @return the capDefault
	 */
	public boolean isCapDefault() {
		return capDefault;
	}
	/**
	 * @param capDefault the capDefault to set
	 */
	public void setCapDefault(boolean capDefault) {
		this.capDefault = capDefault;
	}
	/**
	 * @return the capPriority
	 */
	public int getCapPriority() {
		return capPriority;
	}
	/**
	 * @param capPriority the capPriority to set
	 */
	public void setCapPriority(int capPriority) {
		this.capPriority = capPriority;
	}
	/**
	 * @return the capHidden
	 */
	public boolean isCapHidden() {
		return capHidden;
	}
	/**
	 * @param capHidden the capHidden to set
	 */
	public void setCapHidden(boolean capHidden) {
		this.capHidden = capHidden;
	}
	
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((capId == null) ? 0 : capId.hashCode());
		return result;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Capability other = (Capability) obj;
		if (capId == null) {
			if (other.capId != null)
				return false;
		} else if (!capId.equals(other.capId))
			return false;
		return true;
	}
}
