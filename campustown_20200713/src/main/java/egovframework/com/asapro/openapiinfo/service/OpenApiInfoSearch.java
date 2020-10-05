/**
 * 
 */
package egovframework.com.asapro.openapiinfo.service;

import org.apache.commons.lang3.StringUtils;

import egovframework.com.asapro.support.pagination.PagingSearch;

/**
 * 오픈 api 정보 검색 VO
 * @author yckim
 * @since 2019. 2. 12.
 *
 */
public class OpenApiInfoSearch extends PagingSearch{
	private String apiProvider;	//api 제공 사이트
	
	/**
	 * @return the apiProvider
	 */
	public String getApiProvider() {
		return apiProvider;
	}
	/**
	 * @param apiProvider the apiProvider to set
	 */
	public void setApiProvider(String apiProvider) {
		this.apiProvider = apiProvider;
	}
	
	/* (non-Javadoc)
	 * @see egovframework.com.asapro.support.pagination.PagingSearch#getQueryString()
	 */
	@Override
	public String getQueryString() {
		
		StringBuilder sb = new StringBuilder(100);
		sb.append(super.getQueryString());
		
		if( StringUtils.isNotBlank(this.getApiProvider()) ){
			sb.append("&amp;apiProvider=");
			sb.append(this.getApiProvider());
		}
		
		return sb.toString();
	}
	
}
