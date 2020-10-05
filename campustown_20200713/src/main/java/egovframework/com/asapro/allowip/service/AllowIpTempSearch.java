/**
 * 
 */
package egovframework.com.asapro.allowip.service;

import org.apache.commons.lang3.StringUtils;

import egovframework.com.asapro.support.pagination.PagingSearch;

/**
 * 임시접속허용 IP 검색 VO
 * @author yckim
 * @since 2019. 5. 23.
 *
 */
public class AllowIpTempSearch extends PagingSearch{
	
	private Boolean authentication;	//인증완료여부
	
	/**
	 * @return the authentication
	 */
	public Boolean getAuthentication() {
		return authentication;
	}
	/**
	 * @param authentication the authentication to set
	 */
	public void setAuthentication(Boolean authentication) {
		this.authentication = authentication;
	}

	/* (non-Javadoc)
	 * @see egovframework.com.asapro.support.pagination.PagingSearch#getQueryString()
	 */
	@Override
	public String getQueryString() {
		
		StringBuilder sb = new StringBuilder(100);
		sb.append(super.getQueryString());
		
		if( this.getAuthentication() != null  ){
			sb.append("&amp;authentication=");
			sb.append(this.getAuthentication());
		}
		
		return sb.toString();
	}
	
	
}
