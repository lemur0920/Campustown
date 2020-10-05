/**
 * 
 */
package egovframework.com.asapro.code.service;

import org.apache.commons.lang3.StringUtils;

import egovframework.com.asapro.support.pagination.PagingSearch;

/**
 * 코드검색 vO
 * @author yckim
 * @since 2018. 4. 3.
 *
 */
public class CodeSearch extends PagingSearch{
	
	private String codeUse;
	private String catId;
	
	/**
	 * @return the catId
	 */
	public String getCatId() {
		return catId;
	}

	/**
	 * @param catId the catId to set
	 */
	public void setCatId(String catId) {
		this.catId = catId;
	}

	/**
	 * @return the codeUse
	 */
	public String getCodeUse() {
		return codeUse;
	}

	/**
	 * @param codeUse the codeUse to set
	 */
	public void setCodeUse(String codeUse) {
		this.codeUse = codeUse;
	}

	/* (non-Javadoc)
	 * @see egovframework.com.asapro.support.pagination.PagingSearch#getQueryString()
	 */
	@Override
	public String getQueryString() {
		
		StringBuilder sb = new StringBuilder(100);
		sb.append(super.getQueryString());
		
		if( StringUtils.isNotBlank(this.getCodeUse()) ){
			sb.append("&amp;codeUse=");
			sb.append(this.getCodeUse());
		}
		if( StringUtils.isNotBlank(this.getCatId()) ){
			sb.append("&amp;catId=");
			sb.append(this.getCatId());
		}
		
		return sb.toString();
	}
	
	
}
