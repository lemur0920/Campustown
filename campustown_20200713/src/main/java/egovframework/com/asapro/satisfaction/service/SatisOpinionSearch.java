/**
 * 
 */
package egovframework.com.asapro.satisfaction.service;

import org.apache.commons.lang3.StringUtils;

import egovframework.com.asapro.support.pagination.PagingSearch;

/**
 * 메뉴 평가의견 조회VO
 * @author yckim
 * @since 2019. 4. 16.
 *
 */
public class SatisOpinionSearch extends PagingSearch{
	private String menuId;	//메뉴 아이디

	/**
	 * @return the menuId
	 */
	public String getMenuId() {
		return menuId;
	}

	/**
	 * @param menuId the menuId to set
	 */
	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}
	
	/* (non-Javadoc)
	 * @see egovframework.com.asapro.support.pagination.PagingSearch#getQueryString()
	 */
	@Override
	public String getQueryString() {
		
		StringBuilder sb = new StringBuilder(100);
		sb.append(super.getQueryString());
		
		if( StringUtils.isNotBlank(this.getMenuId()) ){
			sb.append("&amp;menuId=");
			sb.append(this.getMenuId());
		}
		
		return sb.toString();
	}
}
