package egovframework.com.campustown.startHpMngr.service;

import org.apache.commons.lang3.StringUtils;

import egovframework.com.asapro.support.pagination.PagingSearch;



/**
 * 창업팀 홈페이지 관리 정보 검색 VO
 * @author jaseo
 * @since 2020. 02. 11.
 */
public class StartHpMngrSearch extends PagingSearch{
	
	// 검색 구분 (관리자)
	private String scRegStartDate;
	private String scRegEndDate;
	private String areaId;
	private String univId;
	private String univNm;
	private String compId;
	private String expsrYnId;
	
	// 그외
	private Integer indexId;
	private String rprsntvYn;
	private Integer empId;
	
	private String bsnsRealm;
	private String expsrYn;
	
	private String sesnYear;
	private String cardiNum;
	
	private String searchYear;	//년
	private String searchMonth;	//월
	
	private String updId;			// 수정 ID
	private String updNm;			// 수정자
	private String updDt;			// 수정일
	
	
	
	
	
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
	public String getCompId() {
		return compId;
	}
	public void setCompId(String compId) {
		this.compId = compId;
	}
	public String getExpsrYnId() {
		return expsrYnId;
	}
	public void setExpsrYnId(String expsrYnId) {
		this.expsrYnId = expsrYnId;
	}
	public Integer getIndexId() {
		return indexId;
	}
	public void setIndexId(Integer indexId) {
		this.indexId = indexId;
	}
	public String getRprsntvYn() {
		return rprsntvYn;
	}
	public void setRprsntvYn(String rprsntvYn) {
		this.rprsntvYn = rprsntvYn;
	}
	public Integer getEmpId() {
		return empId;
	}
	public void setEmpId(Integer empId) {
		this.empId = empId;
	}
	public String getBsnsRealm() {
		return bsnsRealm;
	}
	public void setBsnsRealm(String bsnsRealm) {
		this.bsnsRealm = bsnsRealm;
	}
	public String getExpsrYn() {
		return expsrYn;
	}
	public void setExpsrYn(String expsrYn) {
		this.expsrYn = expsrYn;
	}
	public String getSesnYear() {
		return sesnYear;
	}
	public void setSesnYear(String sesnYear) {
		this.sesnYear = sesnYear;
	}
	public String getCardiNum() {
		return cardiNum;
	}
	public void setCardiNum(String cardiNum) {
		this.cardiNum = cardiNum;
	}
	
	
	/**
	 * @return the searchYear
	 */
	public String getSearchYear() {
		return searchYear;
	}
	/**
	 * @param searchYear the searchYear to set
	 */
	public void setSearchYear(String searchYear) {
		this.searchYear = searchYear;
	}
	/**
	 * @return the searchMonth
	 */
	public String getSearchMonth() {
		return searchMonth;
	}
	/**
	 * @param searchMonth the searchMonth to set
	 */
	public void setSearchMonth(String searchMonth) {
		this.searchMonth = searchMonth;
	}
	
	public String getUpdId() {
		return updId;
	}
	public void setUpdId(String updId) {
		this.updId = updId;
	}
	public String getUpdNm() {
		return updNm;
	}
	public void setUpdNm(String updNm) {
		this.updNm = updNm;
	}
	public String getUpdDt() {
		return updDt;
	}
	public void setUpdDt(String updDt) {
		this.updDt = updDt;
	}
	
	
	
	@Override
	public String getQueryString() {
		StringBuilder sb = new StringBuilder(100);
		sb.append(super.getQueryString());
		
		if( StringUtils.isNotBlank(this.getScRegStartDate()) ){
			sb.append("&amp;scRegStartDate=");
			sb.append(this.getScRegStartDate());
		}
		if( StringUtils.isNotBlank(this.getScRegEndDate()) ){
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
		if( StringUtils.isNotBlank(this.getCompId()) ){
			sb.append("&amp;compId=");
			sb.append(this.getCompId());
		}
		if( StringUtils.isNotBlank(this.getExpsrYnId()) ){
			sb.append("&amp;expsrYnId=");
			sb.append(this.getExpsrYnId());
		}
		
		return sb.toString();
	}
}
