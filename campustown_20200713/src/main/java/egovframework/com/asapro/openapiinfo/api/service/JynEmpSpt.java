package egovframework.com.asapro.openapiinfo.api.service;

import egovframework.com.asapro.site.service.MultiSiteVO;

public class JynEmpSpt extends MultiSiteVO{
	
	private Integer seq;		//번호
	private String busiId;		//사업ID
	private String busiNm;		//사업명
	private String subBusiNm;	//부사업명
	private String dtlBusiNm;	//사업슬로건
	private String sptScale;	//지원규모
	private String busiSum;		//사업개요
	private String chargerOrgNm;	//담당기관명
	private String onlineApplPossYn;	//온라인신청가능여부
	private String applUrl;		//신청URL
	private String busiTpCd;	//사업유형
	private String detalUrl;	//상세URL
	private String ageEtcCont;	//연령
	private String edubgEtcCont;//학력
	private String empEtcCont;	//취업상태
	private String relinfoUrl;	//관련사이트
	private String chargerClcd;	//지자체정책여부
	
	
	/**
	 * @return the seq
	 */
	public Integer getSeq() {
		return seq;
	}
	/**
	 * @param seq the seq to set
	 */
	public void setSeq(Integer seq) {
		this.seq = seq;
	}
	/**
	 * @return the busiId
	 */
	public String getBusiId() {
		return busiId;
	}
	/**
	 * @param busiId the busiId to set
	 */
	public void setBusiId(String busiId) {
		this.busiId = busiId;
	}
	/**
	 * @return the busiNm 
	 */
	public String getBusiNm() {
		return busiNm;
	}
	/**
	 * @param busiNm the busiNm to set
	 */
	public void setBusiNm(String busiNm) {
		this.busiNm = busiNm;
	}
	/**
	 * @return the subBusiNm
	 */
	public String getSubBusiNm() {
		return subBusiNm;
	}
	/**
	 * @param subBusiNm the subBusiNm to set
	 */
	public void setSubBusiNm(String subBusiNm) {
		this.subBusiNm = subBusiNm;
	}
	/**
	 * @return the dtlBusiNm
	 */
	public String getDtlBusiNm() {
		return dtlBusiNm;
	}
	/**
	 * @param dtlBusiNm the dtlBusiNm to set
	 */
	public void setDtlBusiNm(String dtlBusiNm) {
		this.dtlBusiNm = dtlBusiNm;
	}
	/**
	 * @return the sptScale
	 */
	public String getSptScale() {
		return sptScale;
	}
	/**
	 * @param sptScale the sptScale to set
	 */
	public void setSptScale(String sptScale) {
		this.sptScale = sptScale;
	}
	/**
	 * @return the busiSum
	 */
	public String getBusiSum() {
		return busiSum;
	}
	/**
	 * @param busiSum the busiSum to set
	 */
	public void setBusiSum(String busiSum) {
		this.busiSum = busiSum;
	}
	/**
	 * @return the chargerOrgNm
	 */
	public String getChargerOrgNm() {
		return chargerOrgNm;
	}
	/**
	 * @param chargerOrgNm the chargerOrgNm to set
	 */
	public void setChargerOrgNm(String chargerOrgNm) {
		this.chargerOrgNm = chargerOrgNm;
	}
	/**
	 * @return the onlineApplPossYn
	 */
	public String getOnlineApplPossYn() {
		return onlineApplPossYn;
	}
	/**
	 * @param onlineApplPossYn the onlineApplPossYn to set
	 */
	public void setOnlineApplPossYn(String onlineApplPossYn) {
		this.onlineApplPossYn = onlineApplPossYn;
	}
	/**
	 * @return the applUrl
	 */
	public String getApplUrl() {
		return applUrl;
	}
	/**
	 * @param applUrl the applUrl to set
	 */
	public void setApplUrl(String applUrl) {
		this.applUrl = applUrl;
	}
	/**
	 * @return the busiTpCd
	 */
	public String getBusiTpCd() {
		return busiTpCd;
	}
	/**
	 * @param busiTpCd the busiTpCd to set
	 */
	public void setBusiTpCd(String busiTpCd) {
		this.busiTpCd = busiTpCd;
	}
	/**
	 * @return the detalUrl
	 */
	public String getDetalUrl() {
		return detalUrl;
	}
	/**
	 * @param detalUrl the detalUrl to set
	 */
	public void setDetalUrl(String detalUrl) {
		this.detalUrl = detalUrl;
	}
	/**
	 * @return the ageEtcCont
	 */
	public String getAgeEtcCont() {
		return ageEtcCont;
	}
	/**
	 * @param ageEtcCont the ageEtcCont to set
	 */
	public void setAgeEtcCont(String ageEtcCont) {
		this.ageEtcCont = ageEtcCont;
	}
	/**
	 * @return the edubgEtcCont
	 */
	public String getEdubgEtcCont() {
		return edubgEtcCont;
	}
	/**
	 * @param edubgEtcCont the edubgEtcCont to set
	 */
	public void setEdubgEtcCont(String edubgEtcCont) {
		this.edubgEtcCont = edubgEtcCont;
	}
	/**
	 * @return the empEtcCont
	 */
	public String getEmpEtcCont() {
		return empEtcCont;
	}
	/**
	 * @param empEtcCont the empEtcCont to set
	 */
	public void setEmpEtcCont(String empEtcCont) {
		this.empEtcCont = empEtcCont;
	}
	/**
	 * @return the relinfoUrl
	 */
	public String getRelinfoUrl() {
		return relinfoUrl;
	}
	/**
	 * @param relinfoUrl the relinfoUrl to set
	 */
	public void setRelinfoUrl(String relinfoUrl) {
		this.relinfoUrl = relinfoUrl;
	}
	/**
	 * @return the chargerClcd
	 */
	public String getChargerClcd() {
		return chargerClcd;
	}
	/**
	 * @param chargerClcd the chargerClcd to set
	 */
	public void setChargerClcd(String chargerClcd) {
		this.chargerClcd = chargerClcd;
	}
	
	
}
