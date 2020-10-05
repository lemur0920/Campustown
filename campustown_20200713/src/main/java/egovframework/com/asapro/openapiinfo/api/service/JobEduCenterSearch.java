package egovframework.com.asapro.openapiinfo.api.service;

import egovframework.com.asapro.support.pagination.PagingSearch;

public class JobEduCenterSearch extends PagingSearch {
	
	@Override
	public String getQueryString() {
		
		StringBuilder sb = new StringBuilder(100);
		sb.append(super.getQueryString());
		
		
		return sb.toString();
	}
}
