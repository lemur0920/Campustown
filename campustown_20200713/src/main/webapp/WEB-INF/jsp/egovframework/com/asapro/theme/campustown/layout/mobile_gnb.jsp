<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="org.apache.commons.lang3.StringUtils"%>
<%@ page import="org.apache.commons.lang3.SystemUtils"%>
<%@ page import="java.util.Collections"%>
<%@ page import="java.util.Collection"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.List"%>
<%@ page import="org.springframework.context.ApplicationContext"%>
<%@ page import="egovframework.com.asapro.support.ApplicationContextProvider"%>
<%@ page import="egovframework.com.asapro.site.service.Site"%>
<%@ page import="egovframework.com.asapro.menu.service.MenuService"%>
<%@ page import="egovframework.com.asapro.menu.service.Menu"%>
<%@ page import="egovframework.com.asapro.menu.service.MenuSearch"%>
<%@ page import="egovframework.com.asapro.config.service.ConfigService"%>
<%@ page import="egovframework.com.asapro.config.service.Config"%>
<%!
/**
 *	메뉴 태그 생성
 *	1차 메뉴부터 받아서 재귀호출로 하위메뉴까지 태그를 생성한다.
 *  마크업, 클래스명이 직접 들어가 있으므로 서비스단으로 내리기보다는 바로 수정 가능하도록 jsp 에 선언해 놓는 것이 효율적임.
 *  마크업 만들때 귀찮다고 그냥 '+'사용해서 문자열 붙이지 말고 왠만하면 append 해주시기 바랍니다. 
 */
public static StringBuilder buildMenuMarkup(List<Menu> menuList, Menu menu, List<Menu> currentMenuList, Menu currentMenu, int mgnbDisplayDepth, StringBuilder sb){
	//메뉴 출력 깊이 설정값 제한 체크
	if( menu.getMenuDepth() > mgnbDisplayDepth ){
		return sb;
	}
	//메뉴 상태 체크(참고로 메뉴상태에는 public, hidden, locked 가 있음. 표시는 public 만 함)
	if( !"public".equals(menu.getMenuStatus()) ){
		return sb;
	}
	if( menu.getMenuPosition(menuList) == Menu.FIRST_CHILD || menu.getMenuPosition(menuList) == Menu.ONLY_CHILD ){
		if(menu.getMenuDepth() == 1){
			sb.append("<ul class=\"mob_mn\">");
		}else if(menu.getMenuDepth() > 1){
			sb.append("<ul class=\"mob_m");
			sb.append(menu.getMenuDepth());
			sb.append("\">");
		}
	}
	sb.append("<li>");
	sb.append("<a href=\"");
	//if( menu.getMenuChildren() != null ){
	//	sb.append("#nn");
	//}else{
		sb.append(menu.getMenuLink());
	//}
	sb.append("\"");
	if( menu.isMenuNewWin() ){
		sb.append(" target=\"_blank\"");
	}
	sb.append(">");
	sb.append(menu.getMenuName());		
	sb.append("</a>");
	
	if( menu.getMenuChildren() != null ){
		for(Menu child : menu.getMenuChildren()){
			buildMenuMarkup(menuList, child, currentMenuList, currentMenu, mgnbDisplayDepth, sb);
		}
	}

	sb.append("</li>");
	if( menu.getMenuPosition(menuList) == Menu.LAST_CHILD || menu.getMenuPosition(menuList) == Menu.ONLY_CHILD ){
		sb.append("</ul>");
	}
	return sb;
}
/**
 * 메뉴에 사용할 css 클래스를 만든다
 * @param menuList
 * @return
 */
public static String makeMenuClassNames(List<Menu> menuList, Menu currentMenu) {
	List<Menu> result = new ArrayList<Menu>();
	result.add(currentMenu);
	StringBuilder sb = new StringBuilder(500);
		if( currentMenu != null ){
		traverseMenuList(result, menuList, currentMenu);
		Collections.reverse(result);
		for( Menu menu : result ){
			sb.append(" ");
			sb.append(menu.getMenuId());
		}
	}
	return sb.toString();
}

//메뉴목록을 뒤져서 부모를 찾는다
//메뉴목록을 뒤져서 부모를 찾는다
private static void traverseMenuList(List<Menu> result, List<Menu> menuList, Menu currentMenu){
	if( menuList != null && currentMenu != null ){
		for( Menu parentCandidate : menuList ){
			if( parentCandidate.equals(currentMenu.getMenuParent()) ){
				Menu findMenu = findMenu(menuList, parentCandidate.getMenuId()); 
				result.add(findMenu);
				if( findMenu.getMenuDepth() > 1 ){
					traverseMenuList(result, menuList, parentCandidate);
				} else {
					break;
				}
			}
		}
	}
}

//메뉴목록에서 메뉴를 찾는다.
private static Menu findMenu( List<Menu> menuList, String menuId ){
	for( Menu menu : menuList ){
		if( menu.getMenuId().equals(menuId) ){
			return menu;
		}
	}
	return null;
}
%>
<%
/**
	GNB 메뉴 목록 html 태그 생성
	- html 어트리뷰트나 태그변경등 디자인과 관련해서 수시로 변경될 가능성이 높으므로 jsp단에서 생성하도록 처리함.
	- snb.jsp 와는 대부분 동일한 소스이나 100% 동일하진 않으므로 복붙은 하지말자.
*/
//menuService 빈을 끄집어 내자
ApplicationContext applicationContext = ApplicationContextProvider.getApplicationContext();
MenuService menuService = applicationContext.getBean(MenuService.class);
ConfigService configService = applicationContext.getBean(ConfigService.class);

//리퀘스트에서 메뉴목록을 끄집어 냄
@SuppressWarnings("unchecked")
List<Menu> gnbMenuList = (List<Menu>)request.getAttribute("menuList");
/*
Site currentSite = (Site) request.getAttribute("currentSite");
MenuSearch menuSearch = new MenuSearch();
menuSearch.setSitePrefix(currentSite.getSitePrefix());
menuSearch.setMenuStatus("public");
List<Menu> gnbMenuList = menuService.getMenuList(menuSearch);
*/
//메뉴아이템들은 자식값이 다 비어 있으므로 채워주도록 하자
int maxDepth = 0;
for( Menu menu : gnbMenuList ){
	maxDepth = menu.getMenuDepth() > maxDepth ? menu.getMenuDepth() : maxDepth;	
}
Menu parent = null;
//최하위 메뉴부터 루프를 돌면서 부모메뉴와 짝지어 주도록 한다.
int i = 0;
for( int depth = maxDepth; depth>1; depth-- ){
	for( Menu menu : gnbMenuList ){
		if( menu.getMenuDepth() == depth ){
			parent = menuService.findParentMenu(gnbMenuList, menu);
			//if(menu.getMenuParent() == null || menu.getMenuParent().getMenuLink() == null){
			//	menu.setMenuParent(parent);
			//}
			if( parent.getMenuChildren() == null ){
				parent.setMenuChildren(new ArrayList<Menu>());
			}
			if( !parent.getMenuChildren().contains(menu) ){
				parent.getMenuChildren().add(menu);
			}
		}
	}
}
//1차메뉴를 추출해서 모음
List<Menu> topMenuList = new ArrayList<Menu>();
for( Menu menu : gnbMenuList ){
	if( menu.getMenuDepth() == 1 ){
		topMenuList.add(menu);
	}
}
//태그 생성
Config designConfig = configService.getConfig("design");
StringBuilder sb = new StringBuilder(1000);
int mgnbDisplayDepth = Integer.parseInt( StringUtils.defaultString(designConfig.getOption("nav_m_gnb_display_depth"), "3") );
Menu currentMenu = (Menu)request.getAttribute("currentMenu");
List<Menu> currentMenuList = menuService.getCurrentMenuList(gnbMenuList, currentMenu); 
for( Menu m1 : topMenuList ){
	buildMenuMarkup(gnbMenuList, m1, currentMenuList, currentMenu, mgnbDisplayDepth, sb);
}
//서브탑메뉴(서브1차메뉴)
//request.setAttribute("subTopMenu", menuService.getTopAncestorMenu(gnbMenuList, currentMenu));
String gnbNav = sb.toString();
out.write(gnbNav);

//메뉴 클래스명 생성 
//String menuClassName = makeMenuClassNames(gnbMenuList, currentMenu);
//request.setAttribute("menuClassNames", menuClassName);
%>