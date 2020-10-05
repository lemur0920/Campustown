/**
 * 
 */
package egovframework.com.asapro.support;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

//import egovframework.com.asapro.archive.service.ArchiveTag;
import egovframework.com.asapro.menu.service.Menu;

/**
 * 검색엔진 최적화를 위한 정보를 전달한다.
 * @author yckim
 *
 */
public class SEO {
	
	private String title;
	private String type;
	private String keywords = "";	//태그 있으면 생성됨
	private String description;
	private Menu currentMenu;
	private List<Menu> menuList;
	private String location;
	
	/**
	 * 생성자
	 * @param title
	 * @param type
	 * @param description
	 */
	public SEO(String title, String type, String description){
		this.title = title;
		this.type = type;
		this.description = description;
	}
	/**
	 * 생성자
	 * @param seo
	 */
	public SEO(String title, String type, String description, List<Menu> menuList, Menu currentMenu){
		this.title = title;
		this.type = type;
		this.description = description;
		this.menuList = menuList;
		this.currentMenu = currentMenu;
	}
	
	/**
	 * 생성자 키워드(tag)추가
	 * @param seo
	 */
//	public SEO(String title, List<ArchiveTag> tagList, String type, String description){
//		this.title = title;
//		this.type = type;
//		if( tagList != null && !tagList.isEmpty() ){
//			for( ArchiveTag tag : tagList ){
//				this.keywords += "," + tag.getTagName();
//			}
//		}
//		this.description = description;
//		//this.menuList = menuList;
//		//this.currentMenu = currentMenu;
//	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		//String typeTitle = "";
		//if( "category".equals(this.type) ){ typeTitle = "Category&gt;"; }
		//else if( "tag".equals(this.type) ){ typeTitle = "Tag&gt;"; }
		//else if( "search".equals(this.type) ){ typeTitle = "Search&gt;"; }
		//else if( this.type.startsWith("board.") ){ typeTitle = "Board&gt;"; }
		//return typeTitle + title;
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the menuList
	 */
	public List<Menu> getMenuList() {
		return menuList;
	}

	/**
	 * @param menuList the menuList to set
	 */
	public void setMenuList(List<Menu> menuList) {
		this.menuList = menuList;
	}
	
	/**
	 * 메뉴 로케이션 생성 옵션추가로 개선
	 * 
	 * @param divider
	 * @param cssClass
	 * @return
	 */
	/*public String getLocation(String divider, String cssClass) {
		divider = StringUtils.defaultString(divider);
		if( StringUtils.isNotBlank(divider) ){
			divider = "<span class=\"location-divider\">" + divider + "</span>";
		}
		cssClass = StringUtils.defaultString(cssClass);
		if( StringUtils.isNotBlank(divider) ){
			divider = " " + divider;
		}
		Menu home = new Menu();
		home.setMenuName("Home");
		home.setMenuLink("/");
		home.setMenuDepth(1);
		home.setMenuOrder(0);
		home.setMenuId("home");
		if( this.menuList == null || this.menuList.isEmpty() || this.currentMenu == null || this.currentMenu.getMenuParent() == null ){
			return "<ul class=\"menu-location nav-location seo-location breadcrumb" + cssClass + "\"><li><a href=\"/\">" + home.getMenuName() + "</a></li><li>" + divider + this.title + "</li></ul>";
		} 
		for( Menu menu : menuList ){
			if( menu.isHome() ){
				home = menu;
			}
		}
		List<Menu> result = new ArrayList<Menu>();
		result.add(currentMenu);
		this.traverseMenuList(result, menuList, currentMenu);
		result.add(home);
		Collections.reverse(result);
		StringBuilder sb = new StringBuilder(500);
		sb.append("<ul class=\"menu-location nav-location seo-location breadcrumb" + cssClass + "\">");
		for( Menu menu : result ){
			sb.append("<li>");
				if( !menu.isHome() ){
					sb.append(divider);
				}
				sb.append("<a href=\"");
				sb.append(menu.getMenuLink());
				sb.append("\">");
				sb.append(menu.getMenuName());
				sb.append("</a>");
			sb.append("</li>");
		}
		sb.append("</ul>");
		this.location = sb.toString();
		return location;
	}*/
	//사이트별로 다른 디자인인 경우 공통으로 사용할 수 없을경우 함수를 추가하고 taglib 에 추가해서사용
	public String getLocation(String divider, String cssClass) {
		divider = StringUtils.defaultString(divider);
//		if( StringUtils.isNotBlank(divider) ){
//			divider = "<span class=\"location-divider\">" + divider + "</span>";
//		}
		cssClass = StringUtils.defaultString(cssClass);
		if( StringUtils.isNotBlank(divider) ){
			divider = " " + divider + " ";
		}
		Menu home = new Menu();
		home.setMenuName("Home");
		home.setMenuLink("/");
		home.setMenuDepth(1);
		home.setMenuOrder(0);
		home.setMenuId("home");
		if( this.menuList == null || this.menuList.isEmpty() || this.currentMenu == null || this.currentMenu.getMenuParent() == null ){
			return "<div id=\"snb_m\" class=\"" + cssClass + "\"><a href=\"/\">" + home.getMenuName() + "</a>" + divider + this.title + "</div>";
		} 
		for( Menu menu : menuList ){
			if( menu.isHome() ){
				home = menu;
			}
		}
		List<Menu> result = new ArrayList<Menu>();
		result.add(currentMenu);
		this.traverseMenuList(result, menuList, currentMenu);
		result.add(home);
		Collections.reverse(result);
		
		StringBuilder sb = new StringBuilder(500);
		sb.append("<div id=\"snb_m\" class=\"" + cssClass + "\">");
		for( Menu menu : result ){
			//if(!currentMenu.equals(menu) ){
			if( !menu.isHome() ){
				sb.append(divider);
			}
			if(currentMenu.equals(menu) ){
				sb.append("<strong>");
				sb.append(menu.getMenuName());
				sb.append("</strong>");
			}else{
				sb.append("<a href=\"");
				sb.append(menu.getMenuLink());
				sb.append("\"");
				if(menu.isHome() ){
					sb.append(" class=\"home\"");
				}
				sb.append(">");
				sb.append(menu.getMenuName());
				sb.append("</a>");
			}
		}
		sb.append("</div>");
		this.location = sb.toString();
		return location;
	}
	
	/**
	 * 해더 타이틀 표출 전메뉴 경로로 표출
	 * 
	 * @param divider
	 * @param cssClass
	 * @return
	 */
	public String getTitle2() {
		
		if( this.menuList == null || this.menuList.isEmpty() || this.currentMenu == null || this.currentMenu.getMenuParent() == null ){
			if(this.currentMenu != null && "home".equals(this.currentMenu.getMenuId()) ){
				return "";
			}
			return this.title;
		} 
		List<Menu> result = new ArrayList<Menu>();
		result.add(currentMenu);
		this.traverseMenuList(result, menuList, currentMenu);
		//Collections.reverse(result);
		StringBuilder sb = new StringBuilder(500);
		for( Menu menu : result ){
			if(!currentMenu.equals(menu) ){
				sb.append(" | ");
			}
			sb.append(menu.getMenuName());
		}
		if(!currentMenu.getMenuName().equals(this.title) ){
			sb.append(" | ");
			sb.append(this.title);
		}
		sb.append(" | ");
		return sb.toString();
	}
	
	/**
	 * 메뉴 로케이션
	 * - jsp 쪽에 처리하고 싶긴 한데...일단은 가능한 css 로 컨트롤 가능하게 마크업을 만들어 주자..
	 * @return the location
	 */
	public String getLocation() {
		return this.getLocation(null, null);
	}
	
	//메뉴목록을 뒤져서 부모를 찾는다
	private void traverseMenuList(List<Menu> result, List<Menu> menuList, Menu currentMenu){
		for( Menu parentCandidate : menuList ){
			if( currentMenu.getMenuParent() != null && currentMenu.getMenuParent().equals(parentCandidate) ){
				Menu findMenu = this.findMenu(menuList, parentCandidate.getMenuId()); 
				result.add(findMenu);
				if( findMenu.getMenuDepth() > 1 ){
					this.traverseMenuList(result, menuList, parentCandidate);
				} else {
					break;
				}
			}
		}
	}
	
	//메뉴목록에서 메뉴를 찾는다.
	private Menu findMenu( List<Menu> menuList, String menuId ){
		for( Menu menu : menuList ){
			if( menu.getMenuId().equals(menuId) ){
				return menu;
			}
		}
		return null;
	}

	/**
	 * @param location the location to set
	 */
	public void setLocation(String location) {
		this.location = location;
	}

	/**
	 * @return the currentMenu
	 */
	public Menu getCurrentMenu() {
		return currentMenu;
	}

	/**
	 * @param currentMenu the currentMenu to set
	 */
	public void setCurrentMenu(Menu currentMenu) {
		this.currentMenu = currentMenu;
	}
	/**
	 * @return the keywords
	 */
	public String getKeywords() {
		return keywords;
	}
	/**
	 * @param keywords the keywords to set
	 */
	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}
	
}
