package egovframework.com.asapro.openapiinfo.api.service;

import egovframework.com.asapro.support.pagination.PagingSearch;

/**
 * 뉴딜일자리 정보 검색
 * @author sypark
 *
 */
public class JobNewDealBizSearch extends PagingSearch {

	@Override
	public String getQueryString() {
		
		StringBuilder sb = new StringBuilder(100);
		sb.append(super.getQueryString());
		
		
		return sb.toString();
	}	
}
