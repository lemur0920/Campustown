/**
 * 
 */
package egovframework.com.asapro.organization.service;

import egovframework.com.asapro.support.pagination.PagingSearch;

/**
 * 직원 검색
 * 
 * @author yckim
 * @since 2018. 5. 11.
 */
public class StaffSearch extends PagingSearch{
	


	/* (non-Javadoc)
	 * @see egovframework.com.asapro.support.pagination.PagingSearch#getQueryString()
	 */
	/*
	@Override
	public String getQueryString() {
		StringBuilder sb = new StringBuilder(100);
		sb.append(super.getQueryString());
		
		if( this.getStDepIdx() != null && this.getStDepIdx() > 0 ){
			sb.append("&amp;stDepIdx=");
			sb.append( String.valueOf(this.getStDepIdx().intValue()) );
		}
		
		return sb.toString();
	}
*/
	
}
