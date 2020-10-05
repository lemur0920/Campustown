/**
 * 
 */
package egovframework.com.asapro.commonContent.service;

import org.apache.commons.lang3.StringUtils;

import egovframework.com.asapro.support.pagination.PagingSearch;

/**
 * 공통 콘텐츠 검색 조건
 * @author yckim
 * @since 2018. 8. 23.
 *
 */
public class CommonContentSearch extends PagingSearch{
	
	private String comContModule = "";	//적용모듈
	private String comContModuleSub = "";	//적용서브모듈
	private String comContStatus = "";	//노출상태 - public, draft
	private String comContCate1 = "";	//카테고리1
	private String comContCate2 = "";	//카테고리2
	private String comContCate3 = "";	//카테고리3
	/**
	 * @return the comContModule
	 */
	public String getComContModule() {
		return comContModule;
	}
	/**
	 * @param comContModule the comContModule to set
	 */
	public void setComContModule(String comContModule) {
		this.comContModule = comContModule;
	}
	/**
	 * @return the comContModuleSub
	 */
	public String getComContModuleSub() {
		return comContModuleSub;
	}
	/**
	 * @param comContModuleSub the comContModuleSub to set
	 */
	public void setComContModuleSub(String comContModuleSub) {
		this.comContModuleSub = comContModuleSub;
	}
	/**
	 * @return the comContStatus
	 */
	public String getComContStatus() {
		return comContStatus;
	}
	/**
	 * @param comContStatus the comContStatus to set
	 */
	public void setComContStatus(String comContStatus) {
		this.comContStatus = comContStatus;
	}

	/**
	 * @return the comContCate1
	 */
	public String getComContCate1() {
		return comContCate1;
	}
	/**
	 * @param comContCate1 the comContCate1 to set
	 */
	public void setComContCate1(String comContCate1) {
		this.comContCate1 = comContCate1;
	}
	/**
	 * @return the comContCate2
	 */
	public String getComContCate2() {
		return comContCate2;
	}
	/**
	 * @param comContCate2 the comContCate2 to set
	 */
	public void setComContCate2(String comContCate2) {
		this.comContCate2 = comContCate2;
	}
	/**
	 * @return the comContCate3
	 */
	public String getComContCate3() {
		return comContCate3;
	}
	/**
	 * @param comContCate3 the comContCate3 to set
	 */
	public void setComContCate3(String comContCate3) {
		this.comContCate3 = comContCate3;
	}
	
	/**
	 * @return the cate1Array
	 */
	public String[] getCate1Array() {
		if(StringUtils.isNotBlank(this.comContCate1) ){
			return this.comContCate1.split(",");
		}
		return null;
	}
	/**
	 * @return the cate2Array
	 */
	public String[] getCate2Array() {
		if(StringUtils.isNotBlank(this.comContCate2) ){
			return this.comContCate2.split(",");
		}
		return null;
	}
	/**
	 * @return the cate3Array
	 */
	public String[] getCate3Array() {
		if(StringUtils.isNotBlank(this.comContCate3) ){
			return this.comContCate3.split(",");
		}
		return null;
	}
	@Override
	public String getQueryString() {
		
		StringBuilder sb = new StringBuilder(100);
		sb.append(super.getQueryString());
		
		if( StringUtils.isNotBlank(this.getComContModule()) ){
			sb.append("&amp;comContModule=");
			sb.append(this.getComContModule());
		}
		
		if( StringUtils.isNotBlank(this.getComContModuleSub()) ){
			sb.append("&amp;comContModuleSub=");
			sb.append(this.getComContModuleSub());
		}
		
		if( StringUtils.isNotBlank(this.getComContStatus()) ){
			sb.append("&amp;comContStatus=");
			sb.append(this.getComContStatus());
		}
		
		if( StringUtils.isNotBlank(this.getComContCate1()) ){
			sb.append("&amp;comContCate1=");
			sb.append(this.getComContCate1());
		}
		
		if( StringUtils.isNotBlank(this.getComContCate2()) ){
			sb.append("&amp;comContCate2=");
			sb.append(this.getComContCate2());
		}
		
		if( StringUtils.isNotBlank(this.getComContCate3()) ){
			sb.append("&amp;comContCate3=");
			sb.append(this.getComContCate3());
		}
		
		return sb.toString();
	}
}
