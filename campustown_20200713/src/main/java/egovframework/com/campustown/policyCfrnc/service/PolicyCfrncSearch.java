package egovframework.com.campustown.policyCfrnc.service;

import org.apache.commons.lang3.StringUtils;

import egovframework.com.asapro.support.pagination.PagingSearch;

/**
 * 정책협의회 관리 목록 VO
 * @author jaseo
 * @since 2020. 03. 18.
 */
public class PolicyCfrncSearch extends PagingSearch{
	
	private Integer manageOrdr;
	
	// 검색 구분
	private String scRegStartDt;
	private String scRegEndDt;
	private String scDiv;
	private String scSttus;
	private String scComptYn;
	
	private Integer mngSeq;  // 부모 Seq
	private Integer dtlSeq;  // 상세 Seq
	
	private String delYn;
	
	
	
	
	public Integer getManageOrdr() {
		return manageOrdr;
	}
	public void setManageOrdr(Integer manageOrdr) {
		this.manageOrdr = manageOrdr;
	}
	public String getScRegStartDt() {
		return scRegStartDt;
	}
	public void setScRegStartDt(String scRegStartDt) {
		this.scRegStartDt = scRegStartDt;
	}
	public String getScRegEndDt() {
		return scRegEndDt;
	}
	public void setScRegEndDt(String scRegEndDt) {
		this.scRegEndDt = scRegEndDt;
	}
	public String getScDiv() {
		return scDiv;
	}
	public void setScDiv(String scDiv) {
		this.scDiv = scDiv;
	}
	public String getScSttus() {
		return scSttus;
	}
	public void setScSttus(String scSttus) {
		this.scSttus = scSttus;
	}
	public String getScComptYn() {
		return scComptYn;
	}
	public void setScComptYn(String scComptYn) {
		this.scComptYn = scComptYn;
	}
	public Integer getMngSeq() {
		return mngSeq;
	}
	public void setMngSeq(Integer mngSeq) {
		this.mngSeq = mngSeq;
	}
	public Integer getDtlSeq() {
		return dtlSeq;
	}
	public void setDtlSeq(Integer dtlSeq) {
		this.dtlSeq = dtlSeq;
	}
	public String getDelYn() {
		return delYn;
	}
	public void setDelYn(String delYn) {
		this.delYn = delYn;
	}
	
	@Override
	public String getQueryString() {
		StringBuilder sb = new StringBuilder(100);
		sb.append(super.getQueryString());
		
		if( StringUtils.isNotBlank(this.getScRegStartDt()) ){
			sb.append("&amp;scRegStartDt=");
			sb.append(this.getScRegStartDt());
		}
		if( StringUtils.isNotBlank(this.getScRegEndDt()) ){
			sb.append("&amp;scRegEndDt=");
			sb.append(this.getScRegEndDt());
		}
		if( StringUtils.isNotBlank(this.getScDiv()) ){
			sb.append("&amp;scDiv=");
			sb.append(this.getScDiv());
		}
		if( StringUtils.isNotBlank(this.getScSttus()) ){
			sb.append("&amp;scSttus=");
			sb.append(this.getScSttus());
		}
		if( StringUtils.isNotBlank(this.getScComptYn()) ){
			sb.append("&amp;scComptYn=");
			sb.append(this.getScComptYn());
		}
		
		return sb.toString();
	}
	
}
