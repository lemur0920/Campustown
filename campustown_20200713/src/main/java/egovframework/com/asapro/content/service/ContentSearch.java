/**
 * 
 */
package egovframework.com.asapro.content.service;

import org.apache.commons.lang3.StringUtils;

import egovframework.com.asapro.menu.service.Menu;
import egovframework.com.asapro.support.pagination.PagingSearch;

/**
 * 콘텐츠 검색 조건
 * @author yckim
 * @since 2018. 7. 20.
 *
 */
public class ContentSearch extends PagingSearch{
	
	private String menuId;
	private String contentStatus;	//상태
	private Integer contentVer = 0;	//버전
	private Integer contentRoot;	//최초 콘텐츠 아이디
	private Integer contentId;	//현제 콘텐츠 아이디
	private Menu menu = new Menu();	//
	
	//삭제할때 임시로 사용
	private String vers;

	/**
	 * @return the vers
	 */
	public String getVers() {
		return vers;
	}

	/**
	 * @param vers the vers to set
	 */
	public void setVers(String vers) {
		this.vers = vers;
	}

	/**
	 * @return the contentVer
	 */
	public Integer getContentVer() {
		return contentVer;
	}

	/**
	 * @param contentVer the contentVer to set
	 */
	public void setContentVer(Integer contentVer) {
		this.contentVer = contentVer;
	}

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
	 * @return the contentStatus
	 */
	public String getContentStatus() {
		return contentStatus;
	}

	/**
	 * @param contentStatus the contentStatus to set
	 */
	public void setContentStatus(String contentStatus) {
		this.contentStatus = contentStatus;
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

	/**
	 * @return the contentId
	 */
	public Integer getContentId() {
		return contentId;
	}

	/**
	 * @param contentId the contentId to set
	 */
	public void setContentId(Integer contentId) {
		this.contentId = contentId;
	}

	/**
	 * @return the menu
	 */
	public Menu getMenu() {
		return menu;
	}

	/**
	 * @param menu the menu to set
	 */
	public void setMenu(Menu menu) {
		this.menu = menu;
	}

	@Override
	public String getQueryString() {
		
		StringBuilder sb = new StringBuilder(100);
		sb.append(super.getQueryString());
		
		if( StringUtils.isNotBlank(this.getMenuId()) ){
			sb.append("&amp;menu.menuId=");
			sb.append(this.getMenuId());
		}
		if( StringUtils.isNotBlank(this.getContentStatus()) ){
			sb.append("&amp;contentStatus=");
			sb.append(this.getContentStatus());
		}
		
		if( this.getContentVer() > 0 ){
			sb.append("&amp;contentVer=");
			sb.append(this.getContentVer());
		}
		if( this.getContentRoot() != null && this.getContentRoot() > 0 ){
			sb.append("&amp;contentRoot=");
			sb.append(this.getContentRoot());
		}
		if( this.getContentId() != null && this.getContentId() > 0 ){
			sb.append("&amp;contentId=");
			sb.append(this.getContentId());
		}
		if( this.getMenu() != null && StringUtils.isNotBlank(this.getMenu().getMenuId()) ){
			sb.append("&amp;menu.menuId=");
			sb.append(this.getMenu().getMenuId());
		}
		
		return sb.toString();
	}
}
