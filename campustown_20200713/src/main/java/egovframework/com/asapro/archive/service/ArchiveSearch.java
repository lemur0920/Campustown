/**
 * 
 */
package egovframework.com.asapro.archive.service;

import org.apache.commons.lang3.StringUtils;

import egovframework.com.asapro.support.pagination.PagingSearch;

/**
 * 아카이브 검색 VO
 * @author yckim
 * @since 2018. 12. 19.
 *
 */
public class ArchiveSearch extends PagingSearch{
	
	private Boolean arcUse;	//게시여부
	private String arcCategory;	//카테고리 아이디
	private String arcTag;	//테그(검색키워드)
	
	private String arcMedia;	//매체
	private String arcYear;	//제작년도
	private String arcAdvertiser;	//광고주
	private String arcProduct;	//제품명
	private String arcActor;	//배우,성우
	
	//등록일 검색을 위해 구간 선택 옵션
	private String startDate;
	private String endDate;
	private String memId;	//작성자 아이디
	
	//Meta Code
	private String metaCode1Cat;
	private String metaCode2Cat;
	private String metaCode3Cat;
	private String metaCode1;
	private String metaCode2;
	private String metaCode3;
	
	private Boolean arcSelected1;	//선택여부1 - 추출 등
	
	private String arcTitle; // 2020.03.30. jaseo - 시설공간명 검색	
	private String univId;   // 2020.03.30. jaseo - 대학 부서목록 검색
	private String srcDiv;   // 2020.04.13. jaseo - 검색조건 추가 
	
	/* (non-Javadoc)
	 * @see egovframework.com.asapro.support.pagination.PagingSearch#getQueryString()
	 */
	@Override
	public String getQueryString() {
		
		StringBuilder sb = new StringBuilder(100);
		sb.append(super.getQueryString());
		
		if( this.getUnivId() != null ){
			sb.append("&amp;univId=");
			sb.append(this.getUnivId());
		}
		
		if( this.getArcUse() != null ){
			sb.append("&amp;arcUse=");
			sb.append(this.getArcUse());
		}
		if( StringUtils.isNotBlank(this.getArcCategory()) ){
			sb.append("&amp;arcCategory=");
			sb.append(this.getArcCategory());
		}
		if( StringUtils.isNotBlank(this.getArcTag()) ){
			sb.append("&amp;arcTag=");
			sb.append(this.getArcTag());
		}
		if( StringUtils.isNotBlank(this.getStartDate()) ){
			sb.append("&amp;startDate=");
			sb.append(this.getStartDate());
		}
		if( StringUtils.isNotBlank(this.getEndDate()) ){
			sb.append("&amp;endDate=");
			sb.append(this.getEndDate());
		}
		if( StringUtils.isNotBlank(this.getMetaCode1()) ){
			sb.append("&amp;metaCode1=");
			sb.append(this.getMetaCode1());
		}
		if( StringUtils.isNotBlank(this.getMetaCode2()) ){
			sb.append("&amp;metaCode2=");
			sb.append(this.getMetaCode2());
		}
		if( StringUtils.isNotBlank(this.getMetaCode3()) ){
			sb.append("&amp;metaCode3=");
			sb.append(this.getMetaCode3());
		}
		if( StringUtils.isNotBlank(this.getArcMedia()) ){
			sb.append("&amp;arcMedia=");
			sb.append(this.getArcMedia());
		}
		if( StringUtils.isNotBlank(this.getArcYear()) ){
			sb.append("&amp;arcYear=");
			sb.append(this.getArcYear());
		}
		if( StringUtils.isNotBlank(this.getArcAdvertiser()) ){
			sb.append("&amp;arcAdvertiser=");
			sb.append(this.getArcAdvertiser());
		}
		if( StringUtils.isNotBlank(this.getArcProduct()) ){
			sb.append("&amp;arcProduct=");
			sb.append(this.getArcProduct());
		}
		if( StringUtils.isNotBlank(this.getArcActor()) ){
			sb.append("&amp;arcActor=");
			sb.append(this.getArcActor());
		}
		if( this.getArcSelected1() != null ){
			sb.append("&amp;arcSelected1=");
			sb.append(this.getArcSelected1());
		}
		if( StringUtils.isNotBlank(this.getSrcDiv()) ){
			sb.append("&amp;srcDiv=");
			sb.append(this.getSrcDiv());
		}
		
		
		return sb.toString();
	}
	
	/**
	 * @return the arcUse
	 */
	public Boolean getArcUse() {
		return arcUse;
	}
	/**
	 * @param arcUse the arcUse to set
	 */
	public void setArcUse(Boolean arcUse) {
		this.arcUse = arcUse;
	}

	/**
	 * @return the arcCategory
	 */
	public String getArcCategory() {
		return arcCategory;
	}

	/**
	 * @param arcCategory the arcCategory to set
	 */
	public void setArcCategory(String arcCategory) {
		this.arcCategory = arcCategory;
	}

	/**
	 * @return the arcYear
	 */
	public String getArcYear() {
		return arcYear;
	}
	/**
	 * @param arcYear the arcYear to set
	 */
	public void setArcYear(String arcYear) {
		this.arcYear = arcYear;
	}
	/**
	 * @return the arcAdvertiser
	 */
	public String getArcAdvertiser() {
		return arcAdvertiser;
	}
	/**
	 * @param arcAdvertiser the arcAdvertiser to set
	 */
	public void setArcAdvertiser(String arcAdvertiser) {
		this.arcAdvertiser = arcAdvertiser;
	}
	/**
	 * @return the arcProduct
	 */
	public String getArcProduct() {
		return arcProduct;
	}
	/**
	 * @param arcProduct the arcProduct to set
	 */
	public void setArcProduct(String arcProduct) {
		this.arcProduct = arcProduct;
	}
	/**
	 * @return the arcActor
	 */
	public String getArcActor() {
		return arcActor;
	}
	/**
	 * @param arcActor the arcActor to set
	 */
	public void setArcActor(String arcActor) {
		this.arcActor = arcActor;
	}
	/**
	 * @return the startDate
	 */
	public String getStartDate() {
		return startDate;
	}
	/**
	 * @param startDate the startDate to set
	 */
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	/**
	 * @return the endDate
	 */
	public String getEndDate() {
		return endDate;
	}
	/**
	 * @param endDate the endDate to set
	 */
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	/**
	 * @return the memId
	 */
	public String getMemId() {
		return memId;
	}
	/**
	 * @param memId the memId to set
	 */
	public void setMemId(String memId) {
		this.memId = memId;
	}
	/**
	 * @return the metaCode1Cat
	 */
	public String getMetaCode1Cat() {
		return metaCode1Cat;
	}
	/**
	 * @param metaCode1Cat the metaCode1Cat to set
	 */
	public void setMetaCode1Cat(String metaCode1Cat) {
		this.metaCode1Cat = metaCode1Cat;
	}
	/**
	 * @return the metaCode2Cat
	 */
	public String getMetaCode2Cat() {
		return metaCode2Cat;
	}
	/**
	 * @param metaCode2Cat the metaCode2Cat to set
	 */
	public void setMetaCode2Cat(String metaCode2Cat) {
		this.metaCode2Cat = metaCode2Cat;
	}
	/**
	 * @return the metaCode3Cat
	 */
	public String getMetaCode3Cat() {
		return metaCode3Cat;
	}
	/**
	 * @param metaCode3Cat the metaCode3Cat to set
	 */
	public void setMetaCode3Cat(String metaCode3Cat) {
		this.metaCode3Cat = metaCode3Cat;
	}
	/**
	 * @return the metaCode1
	 */
	public String getMetaCode1() {
		return metaCode1;
	}
	/**
	 * @param metaCode1 the metaCode1 to set
	 */
	public void setMetaCode1(String metaCode1) {
		this.metaCode1 = metaCode1;
	}
	/**
	 * @return the metaCode2
	 */
	public String getMetaCode2() {
		return metaCode2;
	}
	/**
	 * @param metaCode2 the metaCode2 to set
	 */
	public void setMetaCode2(String metaCode2) {
		this.metaCode2 = metaCode2;
	}
	/**
	 * @return the metaCode3
	 */
	public String getMetaCode3() {
		return metaCode3;
	}
	/**
	 * @param metaCode3 the metaCode3 to set
	 */
	public void setMetaCode3(String metaCode3) {
		this.metaCode3 = metaCode3;
	}

	/**
	 * @return the arcSelected1
	 */
	public Boolean getArcSelected1() {
		return arcSelected1;
	}

	/**
	 * @param arcSelected1 the arcSelected1 to set
	 */
	public void setArcSelected1(Boolean arcSelected1) {
		this.arcSelected1 = arcSelected1;
	}

	/**
	 * @return the arcMedia
	 */
	public String getArcMedia() {
		return arcMedia;
	}

	/**
	 * @param arcMedia the arcMedia to set
	 */
	public void setArcMedia(String arcMedia) {
		this.arcMedia = arcMedia;
	}

	/**
	 * @return the meta1Array
	 */
	public String[] getMeta1Array() {
		if(StringUtils.isNotBlank(this.metaCode1) ){
			return this.metaCode1.split(",");
		}
		return null;
	}

	/**
	 * @return the meta2Array
	 */
	public String[] getMeta2Array() {
		if(StringUtils.isNotBlank(this.metaCode2) ){
			return this.metaCode2.split(",");
		}
		return null;
	}

	/**
	 * @return the meta3Array
	 */
	public String[] getMeta3Array() {
		if(StringUtils.isNotBlank(this.metaCode3) ){
			return this.metaCode3.split(",");
		}
		return null;
	}

	/**
	 * @return the arcTag
	 */
	public String getArcTag() {
		return arcTag;
	}

	/**
	 * @param arcTag the arcTag to set
	 */
	public void setArcTag(String arcTag) {
		this.arcTag = arcTag;
	}
	
	/**
	 * 
	 * @return arcTitle
	 */
	public String getArcTitle() {
		return arcTitle;
	}
	
	/**
	 * 
	 * @param arcTitle
	 */
	public void setArcTitle(String arcTitle) {
		this.arcTitle = arcTitle;
	}

	/**
	 * 
	 * @return the univId
	 */
	public String getUnivId() {
		return univId;
	}
	
	/**
	 * 
	 * @param univId
	 */
	public void setUnivId(String univId) {
		this.univId = univId;
	}
	
	/**
	 * 
	 * @return srcDiv
	 */
	public String getSrcDiv() {
		return srcDiv;
	}
	
	/**
	 * 
	 * @param srcDiv
	 */
	public void setSrcDiv(String srcDiv) {
		this.srcDiv = srcDiv;
	}
	
	
}
