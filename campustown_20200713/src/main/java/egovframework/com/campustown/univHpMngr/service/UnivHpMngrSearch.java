package egovframework.com.campustown.univHpMngr.service;

import org.apache.commons.lang3.StringUtils;

import egovframework.com.asapro.support.pagination.PagingSearch;

/**
 * 대학 홈페이지 관리 정보 검색 VO
 * @author jaseo
 * @since 2020. 02. 11.
 */
public class UnivHpMngrSearch extends PagingSearch{
	
	// 검색 구분
	private String scRegStartDate;
	private String scRegEndDate;
	private String areaId;
	private String univId;
	private String univNm;
	private String expsrYnId;
	
	// hidden
	private String expsrYn;
	
	// 기타
	private String srcDiv= "move";
	private String delYn;
	
	
	
	public String getScRegStartDate() {
		return scRegStartDate;
	}
	public void setScRegStartDate(String scRegStartDate) {
		this.scRegStartDate = scRegStartDate;
	}
	public String getScRegEndDate() {
		return scRegEndDate;
	}
	public void setScRegEndDate(String scRegEndDate) {
		this.scRegEndDate = scRegEndDate;
	}
	public String getAreaId() {
		return areaId;
	}
	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}
	public String getUnivId() {
		return univId;
	}
	public void setUnivId(String univId) {
		this.univId = univId;
	}
	public String getUnivNm() {
		return univNm;
	}
	public void setUnivNm(String univNm) {
		this.univNm = univNm;
	}
	public String getExpsrYnId() {
		return expsrYnId;
	}
	public void setExpsrYnId(String expsrYnId) {
		this.expsrYnId = expsrYnId;
	}
	public String getExpsrYn() {
		return expsrYn;
	}
	public void setExpsrYn(String expsrYn) {
		this.expsrYn = expsrYn;
	}
	public String getSrcDiv() {
		return srcDiv;
	}
	public void setSrcDiv(String srcDiv) {
		this.srcDiv = srcDiv;
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
		
		if( StringUtils.isNotBlank(this.getUnivId()) ){
			sb.append("&amp;scRegStartDate=");
			sb.append(this.getScRegStartDate());
		}
		if( StringUtils.isNotBlank(this.getUnivId()) ){
			sb.append("&amp;scRegEndDate=");
			sb.append(this.getScRegEndDate());
		}
		if( StringUtils.isNotBlank(this.getAreaId()) ){
			sb.append("&amp;areaId=");
			sb.append(this.getAreaId());
		}
		if( StringUtils.isNotBlank(this.getUnivId()) ){
			sb.append("&amp;univId=");
			sb.append(this.getUnivId());
		}
		if( StringUtils.isNotBlank(this.getUnivNm()) ){
			sb.append("&amp;univNm=");
			sb.append(this.getUnivNm());
		}
		
		if( StringUtils.isNotBlank(this.getSrcDiv()) ){
			sb.append("&amp;srcDiv=");
			sb.append(this.getSrcDiv());
		}
		if( StringUtils.isNotBlank(this.getExpsrYnId()) ){
			sb.append("&amp;expsrYnId=");
			sb.append(this.getExpsrYnId());
		}
		return sb.toString();
	}
}
