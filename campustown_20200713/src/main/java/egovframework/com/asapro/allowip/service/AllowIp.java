/**
 * 
 */
package egovframework.com.asapro.allowip.service;

import java.io.Serializable;
import java.util.Date;

import egovframework.com.asapro.site.service.MultiSiteVO;




/**
 * 접속허용 IP VO
 * @author yckim
 * @since 2018. 8. 17.
 *
 */
public class AllowIp extends MultiSiteVO {
	
	private Integer ipId;	//PK
	private String ipType;	//허용,차단 구분
	private String ipStart;	//1건,패턴,구간시작 아이피
	private String ipStart1;	//1건,패턴,구간시작 아이피
	private String ipStart2;	//1건,패턴,구간시작 아이피
	private String ipStart3;	//1건,패턴,구간시작 아이피
	private String ipStart4;	//1건,패턴,구간시작 아이피
	private String ipEnd;	//구간종료 아이피
	private String ipEnd1;	//구간종료 아이피
	private String ipEnd2;	//구간종료 아이피
	private String ipEnd3;	//구간종료 아이피
	private String ipEnd4;	//구간종료 아이피
	private Date ipRegDate;	//등록일시
	private String ipRuleName;	//규칙명
	private Boolean ipUse = true;	//사용유무
	
	/**
	 * @return the ipId
	 */
	public Integer getIpId() {
		return ipId;
	}
	/**
	 * @param ipId the ipId to set
	 */
	public void setIpId(Integer ipId) {
		this.ipId = ipId;
	}
	/**
	 * @return the ipType
	 */
	public String getIpType() {
		return ipType;
	}
	/**
	 * @param ipType the ipType to set
	 */
	public void setIpType(String ipType) {
		this.ipType = ipType;
	}
	/**
	 * @return the ipStart
	 */
	public String getIpStart() {
		return ipStart;
	}
	/**
	 * @param ipStart the ipStart to set
	 */
	public void setIpStart(String ipStart) {
		this.ipStart = ipStart;
	}
	
	/**
	 * @return the ipStart1
	 */
	public String getIpStart1() {
		return ipStart1;
	}
	/**
	 * @param ipStart1 the ipStart1 to set
	 */
	public void setIpStart1(String ipStart1) {
		this.ipStart1 = ipStart1;
	}
	/**
	 * @return the ipStart2
	 */
	public String getIpStart2() {
		return ipStart2;
	}
	/**
	 * @param ipStart2 the ipStart2 to set
	 */
	public void setIpStart2(String ipStart2) {
		this.ipStart2 = ipStart2;
	}
	/**
	 * @return the ipStart3
	 */
	public String getIpStart3() {
		return ipStart3;
	}
	/**
	 * @param ipStart3 the ipStart3 to set
	 */
	public void setIpStart3(String ipStart3) {
		this.ipStart3 = ipStart3;
	}
	/**
	 * @return the ipStart4
	 */
	public String getIpStart4() {
		return ipStart4;
	}
	/**
	 * @param ipStart4 the ipStart4 to set
	 */
	public void setIpStart4(String ipStart4) {
		this.ipStart4 = ipStart4;
	}
	/**
	 * @return the ipEnd
	 */
	public String getIpEnd() {
		return ipEnd;
	}
	/**
	 * @param ipEnd the ipEnd to set
	 */
	public void setIpEnd(String ipEnd) {
		this.ipEnd = ipEnd;
	}
	
	/**
	 * @return the ipEnd1
	 */
	public String getIpEnd1() {
		return ipEnd1;
	}
	/**
	 * @param ipEnd1 the ipEnd1 to set
	 */
	public void setIpEnd1(String ipEnd1) {
		this.ipEnd1 = ipEnd1;
	}
	/**
	 * @return the ipEnd2
	 */
	public String getIpEnd2() {
		return ipEnd2;
	}
	/**
	 * @param ipEnd2 the ipEnd2 to set
	 */
	public void setIpEnd2(String ipEnd2) {
		this.ipEnd2 = ipEnd2;
	}
	/**
	 * @return the ipEnd3
	 */
	public String getIpEnd3() {
		return ipEnd3;
	}
	/**
	 * @param ipEnd3 the ipEnd3 to set
	 */
	public void setIpEnd3(String ipEnd3) {
		this.ipEnd3 = ipEnd3;
	}
	/**
	 * @return the ipEnd4
	 */
	public String getIpEnd4() {
		return ipEnd4;
	}
	/**
	 * @param ipEnd4 the ipEnd4 to set
	 */
	public void setIpEnd4(String ipEnd4) {
		this.ipEnd4 = ipEnd4;
	}
	/**
	 * @return the ipRegDate
	 */
	public Date getIpRegDate() {
		return ipRegDate;
	}
	/**
	 * @param ipRegDate the ipRegDate to set
	 */
	public void setIpRegDate(Date ipRegDate) {
		this.ipRegDate = ipRegDate;
	}
	/**
	 * @return the ipRuleName
	 */
	public String getIpRuleName() {
		return ipRuleName;
	}
	/**
	 * @param ipRuleName the ipRuleName to set
	 */
	public void setIpRuleName(String ipRuleName) {
		this.ipRuleName = ipRuleName;
	}
	/**
	 * @return the ipUse
	 */
	public Boolean getIpUse() {
		return ipUse;
	}
	/**
	 * @param ipUse the ipUse to set
	 */
	public void setIpUse(Boolean ipUse) {
		this.ipUse = ipUse;
	}
	
}
