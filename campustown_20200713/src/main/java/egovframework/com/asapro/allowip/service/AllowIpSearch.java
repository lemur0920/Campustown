/**
 * 
 */
package egovframework.com.asapro.allowip.service;

import org.apache.commons.lang3.StringUtils;

import egovframework.com.asapro.support.pagination.PagingSearch;

/**
 * 접속허용 IP 검색 VO
 * @author yckim
 * @since 2018. 8. 17.
 *
 */
public class AllowIpSearch extends PagingSearch{
	
	private String ipType;	//허용,차단 구분
	private Boolean ipUse;	//사용유무
	

	/**
	 * @return the ipType
	 */
	public String getIpType() {
		return ipType;
	}

	/**
	 * @param ipType the ipType to set
	 */
	public void setIpType(String ipType) {
		this.ipType = ipType;
	}
	/**
	 * @return the ipUse
	 */
	public Boolean getIpUse() {
		return ipUse;
	}

	/**
	 * @param ipUse the ipUse to set
	 */
	public void setIpUse(Boolean ipUse) {
		this.ipUse = ipUse;
	}

	/* (non-Javadoc)
	 * @see egovframework.com.asapro.support.pagination.PagingSearch#getQueryString()
	 */
	@Override
	public String getQueryString() {
		
		StringBuilder sb = new StringBuilder(100);
		sb.append(super.getQueryString());
		
		if( StringUtils.isNotBlank(this.getIpType()) ){
			sb.append("&amp;ipType=");
			sb.append(this.getIpType());
		}
		if( this.getIpUse() != null  ){
			sb.append("&amp;ipUse=");
			sb.append(this.getIpUse());
		}
		
		return sb.toString();
	}
	
	
}
