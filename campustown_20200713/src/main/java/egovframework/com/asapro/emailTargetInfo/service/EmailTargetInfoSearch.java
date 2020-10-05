/**
 * 
 */
package egovframework.com.asapro.emailTargetInfo.service;

import org.apache.commons.lang3.StringUtils;

import egovframework.com.asapro.support.pagination.PagingSearch;

/**
 * 메일대상 검색 VO
 * @author yckim
 * @since 2019. 12. 11.
 *
 */
public class EmailTargetInfoSearch extends PagingSearch{
	
	private String etId;
	private String etCate;

	/**
	 * @return the etId
	 */
	public String getEtId() {
		return etId;
	}

	/**
	 * @param etId the etId to set
	 */
	public void setEtId(String etId) {
		this.etId = etId;
	}

	/**
	 * @return the etCate
	 */
	public String getEtCate() {
		return etCate;
	}

	/**
	 * @param etCate the etCate to set
	 */
	public void setEtCate(String etCate) {
		this.etCate = etCate;
	}

	/* (non-Javadoc)
	 * @see egovframework.com.asapro.support.pagination.PagingSearch#getQueryString()
	 */
	@Override
	public String getQueryString() {
		
		StringBuilder sb = new StringBuilder(100);
		sb.append(super.getQueryString());
		
		if( StringUtils.isNotBlank(this.getEtId()) ){
			sb.append("&amp;etId=");
			sb.append(this.getEtId());
		}
		if( StringUtils.isNotBlank(this.getEtCate()) ){
			sb.append("&amp;etCate=");
			sb.append(this.getEtCate());
		}
		
		return sb.toString();
	}
	
	
}
