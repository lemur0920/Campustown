/**
 * 
 */
package egovframework.com.asapro.menu.service;

import egovframework.com.asapro.support.pagination.PagingSearch;

/**
 * 메뉴 검색 VO
 * @author yckim
 * @since 2018. 7. 6.
 *
 */
public class MenuSearch extends PagingSearch{
	
	private Integer menuDepth;
	private String menuStatus;
	
	private String tagCode;	//메뉴 해시태그

	//uri로 메뉴 찾을때 사용
	private String menuLink;
	private String menuLinkSearchCondition;
	private String menuType;
	
	public MenuSearch() {
		super();
		this.setPaging(false);
	}

	/**
	 * @return the menuDepth
	 */
	public Integer getMenuDepth() {
		return menuDepth;
	}

	/**
	 * @param menuDepth the menuDepth to set
	 */
	public void setMenuDepth(Integer menuDepth) {
		this.menuDepth = menuDepth;
	}

	/**
	 * @return the menuStatus
	 */
	public String getMenuStatus() {
		return menuStatus;
	}

	/**
	 * @param menuStatus the menuStatus to set
	 */
	public void setMenuStatus(String menuStatus) {
		this.menuStatus = menuStatus;
	}

	/**
	 * @return the menuLink
	 */
	public String getMenuLink() {
		return menuLink;
	}

	/**
	 * @param menuLink the menuLink to set
	 */
	public void setMenuLink(String menuLink) {
		this.menuLink = menuLink;
	}

	/**
	 * @return the menuLinkSearchCondition
	 */
	public String getMenuLinkSearchCondition() {
		return menuLinkSearchCondition;
	}

	/**
	 * @param menuLinkSearchCondition the menuLinkSearchCondition to set
	 */
	public void setMenuLinkSearchCondition(String menuLinkSearchCondition) {
		this.menuLinkSearchCondition = menuLinkSearchCondition;
	}

	/**
	 * @return the menuType
	 */
	public String getMenuType() {
		return menuType;
	}

	/**
	 * @param menuType the menuType to set
	 */
	public void setMenuType(String menuType) {
		this.menuType = menuType;
	}

	/**
	 * @return the tagCode
	 */
	public String getTagCode() {
		return tagCode;
	}

	/**
	 * @param tagCode the tagCode to set
	 */
	public void setTagCode(String tagCode) {
		this.tagCode = tagCode;
	}
	
}
