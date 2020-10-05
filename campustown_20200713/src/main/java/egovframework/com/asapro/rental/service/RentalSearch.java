/**
 * 
 */
package egovframework.com.asapro.rental.service;

import org.apache.commons.lang3.StringUtils;

import egovframework.com.asapro.support.pagination.PagingSearch;

/**
 * 대관/대여 검색
 * @author yckim
 * @since 2018. 10. 29.
 *
 */
public class RentalSearch extends PagingSearch{

	private String rentCate1;	//구분 카테고리 1 (코드)
	private String rentCate2;	//구분 카테고리 2 (코드)
	private Boolean rentUse;	//게시여부	
	private String step;	//진행단계
	private String srcDiv="share";	// jaseo - 2020.04.28. 공간 구분(입주: move, 공유: share)
	
	
	/**
	 * @return the rentCate1
	 */
	public String getRentCate1() {
		return rentCate1;
	}
	/**
	 * @param rentCate1 the rentCate1 to set
	 */
	public void setRentCate1(String rentCate1) {
		this.rentCate1 = rentCate1;
	}
	/**
	 * @return the rentCate2
	 */
	public String getRentCate2() {
		return rentCate2;
	}
	/**
	 * @param rentCate2 the rentCate2 to set
	 */
	public void setRentCate2(String rentCate2) {
		this.rentCate2 = rentCate2;
	}
	/**
	 * @return the rentUse
	 */
	public Boolean getRentUse() {
		return rentUse;
	}
	/**
	 * @param rentUse the rentUse to set
	 */
	public void setRentUse(Boolean rentUse) {
		this.rentUse = rentUse;
	}
	
	/**
	 * @return the step
	 */
	public String getStep() {
		return step;
	}
	/**
	 * @param step the step to set
	 */
	public void setStep(String step) {
		this.step = step;
	}
	public String getSrcDiv() {
		return srcDiv;
	}
	public void setSrcDiv(String srcDiv) {
		this.srcDiv = srcDiv;
	}
	
	
	/* (non-Javadoc)
	 * @see egovframework.com.asapro.support.pagination.PagingSearch#getQueryString()
	 */
	@Override
	public String getQueryString() {
		
		StringBuilder sb = new StringBuilder(100);
		sb.append(super.getQueryString());
		
		if( StringUtils.isNotBlank(this.getRentCate1()) ){
			sb.append("&amp;rentCate1=");
			sb.append(this.getRentCate1());
		}
		
		if( StringUtils.isNotBlank(this.getRentCate2()) ){
			sb.append("&amp;rentCate2=");
			sb.append(this.getRentCate2());
		}
		
		if( this.getRentUse() != null ){
			sb.append("&amp;rentUse=");
			sb.append(this.getRentUse());
		}
		
		if( StringUtils.isNotBlank(this.getStep()) ){
			sb.append("&amp;step=");
			sb.append(this.getStep());
		}
		
		if( StringUtils.isNotBlank(this.getSrcDiv()) ){
			sb.append("&amp;srcDiv=");
			sb.append(this.getSrcDiv());
		}
		
		return sb.toString();
	}
	
}
