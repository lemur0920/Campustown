/**
 * 
 */
package egovframework.com.asapro.allowmac.service;

import org.apache.commons.lang3.StringUtils;

import egovframework.com.asapro.support.pagination.PagingSearch;

/**
 * 접속허용 Mac Address 검색 VO
 * @author yckim
 * @since 2019. 3. 26.
 *
 */
public class AllowMacSearch extends PagingSearch{
	
	private String macType;	//허용,차단 구분
	private Boolean macUse;	//사용유무
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

	/* (non-Javadoc)
	 * @see egovframework.com.asapro.support.pagination.PagingSearch#getQueryString()
	 */
	@Override
	public String getQueryString() {
		
		StringBuilder sb = new StringBuilder(100);
		sb.append(super.getQueryString());
		
		if( StringUtils.isNotBlank(this.getMacType()) ){
			sb.append("&amp;macType=");
			sb.append(this.getMacType());
		}
		if( this.getMacUse() != null  ){
			sb.append("&amp;macUse=");
			sb.append(this.getMacUse());
		}
		
		return sb.toString();
	}
	
	
}
