/**
 * 
 */
package egovframework.com.asapro.menu.service;

import egovframework.com.asapro.site.service.MultiSiteVO;

/**
 * 메뉴-콘텐츠 릴레이션 VO
 * @author yckim
 * @since 2019. 5. 3.
 *
 */

public class MenuContentRelation extends MultiSiteVO {
	
	private String menuId = "";	//메뉴 아이디
	private String sitePrefix = "";	//사이트 프리픽스
	private Integer contentRoot;	//최초 콘텐츠 아이디
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
	/**
	 * @return the sitePrefix
	 */
	public String getSitePrefix() {
		return sitePrefix;
	}
	/**
	 * @param sitePrefix the sitePrefix to set
	 */
	public void setSitePrefix(String sitePrefix) {
		this.sitePrefix = sitePrefix;
	}
	/**
	 * @return the contentRoot
	 */
	public Integer getContentRoot() {
		return contentRoot;
	}
	/**
	 * @param contentRoot the contentRoot to set
	 */
	public void setContentRoot(Integer contentRoot) {
		this.contentRoot = contentRoot;
	}

}
