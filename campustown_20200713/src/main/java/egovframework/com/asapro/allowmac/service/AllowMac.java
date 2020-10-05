/**
 * 
 */
package egovframework.com.asapro.allowmac.service;

import java.io.Serializable;
import java.util.Date;

import egovframework.com.asapro.site.service.MultiSiteVO;




/**
 * 접속허용 Mac Address VO
 * @author yckim
 * @since 2019. 3. 26.
 *
 */
public class AllowMac extends MultiSiteVO {
	
	private Integer macId;	//PK
	private String macType;	//허용,차단 구분
	private String macAddress;	//맥주소
	private Date macRegDate;	//등록일시
	private String macRuleName;	//규칙명
	private Boolean macUse = true;	//사용유무
	/**
	 * @return the macId
	 */
	public Integer getMacId() {
		return macId;
	}
	/**
	 * @param macId the macId to set
	 */
	public void setMacId(Integer macId) {
		this.macId = macId;
	}
	/**
	 * @return the macType
	 */
	public String getMacType() {
		return macType;
	}
	/**
	 * @param macType the macType to set
	 */
	public void setMacType(String macType) {
		this.macType = macType;
	}
	/**
	 * @return the macAddress
	 */
	public String getMacAddress() {
		return macAddress;
	}
	/**
	 * @param macAddress the macAddress to set
	 */
	public void setMacAddress(String macAddress) {
		this.macAddress = macAddress;
	}
	/**
	 * @return the macRegDate
	 */
	public Date getMacRegDate() {
		return macRegDate;
	}
	/**
	 * @param macRegDate the macRegDate to set
	 */
	public void setMacRegDate(Date macRegDate) {
		this.macRegDate = macRegDate;
	}
	/**
	 * @return the macRuleName
	 */
	public String getMacRuleName() {
		return macRuleName;
	}
	/**
	 * @param macRuleName the macRuleName to set
	 */
	public void setMacRuleName(String macRuleName) {
		this.macRuleName = macRuleName;
	}
	/**
	 * @return the macUse
	 */
	public Boolean getMacUse() {
		return macUse;
	}
	/**
	 * @param macUse the macUse to set
	 */
	public void setMacUse(Boolean macUse) {
		this.macUse = macUse;
	}
	
	
}
