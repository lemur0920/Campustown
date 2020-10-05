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
<%@ page import="egovframework.com.asapro.menu.service.MenuService"%>
<%@ page import="egovframework.com.asapro.menu.service.Menu"%>
<%@ page import="egovframework.com.asapro.config.service.ConfigService"%>
<%@ page import="egovframework.com.asapro.config.service.Config"%>
<%!
/**
 *	메뉴 태그 생성
 *	1차 메뉴부터 받아서 재귀호출로 하위메뉴까지 태그를 생성한다.
 *  마크업, 클래스명이 직접 들어가 있으므로 서비스단으로 내리기보다는 바로 수정 가능하도록 jsp 에 선언해 놓는 것이 효율적임.
 *  마크업 만들때 귀찮다고 그냥 '+'사용해서 문자열 붙이지 말고 왠만하면 append 해주시기 바랍니다.
 */
public static StringBuilder buildMenuMarkup(List<Menu> menuList, Menu menu, List<Menu> currentMenuList, Menu currentMenu, int snbDisplayDepth, StringBuilder sb){
	//메뉴 출력 깊이 설정값 제한 체크
	if( menu.getMenuDepth() > snbDisplayDepth ){
		return sb;
	}
	//메뉴 상태 체크(참고로 메뉴상태에는 public, hidden, locked 가 있음. 표시는 public 만 함)
	if( !"public".equals(menu.getMenuStatus()) ){
		return sb;
	}
	
	if( menu.getMenuDepth() == 1 ){
		sb.append("<ul class=\"depth1 clearfix\">");
		sb.append("<li class=\"home\">");
//		if(menu.getMenuName().length() > 8){
//			sb.append(" " + menu.getMenuName().length());
//			sb.append(" sub_newTitle02");
//		}
		sb.append("<a href=\"/\">홈</a>");
		sb.append("</li>");
		sb.append("<li>");
		sb.append("<button>");
		sb.append(menu.getMenuName());
		sb.append("</button>");
		sb.append("</li>");
	}
	
	if(menu.getMenuDepth() > 1){
		if( "public".equals(menu.getMenuStatus()) ){
			
		}
		if( menu.getMenuPosition(menuList) == Menu.FIRST_CHILD || menu.getMenuPosition(menuList) == Menu.ONLY_CHILD ){
			if(menu.getMenuDepth() == 2){
				sb.append("<li>");
				sb.append("<button>");
				
				for(Menu locationMenu : currentMenuList){
					if(locationMenu.getMenuDepth() == 2){
						sb.append(locationMenu.getMenuName());
					}
				}
				sb.append("</button>");
			}
			sb.append("<ul class=\"depth");
			sb.append(menu.getMenuDepth());
			sb.append("\">");
		}
		
		sb.append("<li>");
		sb.append("<a class=\"");
		if( currentMenuList != null && currentMenuList.contains(menu) ){
			sb.append("active");
		}
		sb.append("\"");
		sb.append(" href=\"");
		sb.append(menu.getMenuLink());
		sb.append("\"");
		if( menu.isMenuNewWin() ){
			sb.append(" title=\"새창\"");
			sb.append(" target=\"_blank\"");
		}
		//sb.append(" class=\"");
		//if(menu.getMenuChildren() != null){
		//	if( currentMenuList != null && currentMenuList.contains(menu) ){
		//		sb.append("on current");
		//	}else{
		//		sb.append("off");
		//	}
		//}else{
		//	if( currentMenuList != null && currentMenuList.contains(menu) ){
		//		sb.append("current");
		//	}
		//}
		//sb.append("\">");
		sb.append(">");
		
		//end : bluebox only
		if( "text".equals(menu.getMenuSnbType()) ){
			sb.append(menu.getMenuName());		
		} else if( "image".equals(menu.getMenuSnbType()) ) {
			sb.append("<img src=\"");
			if( currentMenuList != null && currentMenuList.contains(menu) ){
				sb.append(menu.getMenuSnbImageOnUrl());
			} else {
				sb.append(menu.getMenuSnbImageOffUrl());
			}
			sb.append("\" alt=\"");
			sb.append( menu.getMenuPlainName());
			sb.append("\"");
			if( currentMenuList != null && !currentMenuList.contains(menu) ){
				sb.append(" onmouseover=\"this.src='");
				sb.append(menu.getMenuSnbImageOnUrl());
				sb.append("'\"");
				sb.append(" onmouseout=\"this.src='");
				sb.append(menu.getMenuSnbImageOffUrl());
				sb.append("'\"");
			}
			sb.append("/>");
		}
	
		//end : bluebox only
		sb.append("</a>");
	}
	
	//하위메뉴
	if( menu.getMenuChildren() != null ){
		for(Menu child : menu.getMenuChildren()){
			if( "public".equals(child.getMenuStatus()) ){
				buildMenuMarkup(menuList, child, currentMenuList, currentMenu, snbDisplayDepth, sb);
			}
		}
	}
	
	if(menu.getMenuDepth() > 1){
		sb.append("</li>");
		if( menu.getMenuPosition(menuList) == Menu.LAST_CHILD || menu.getMenuPosition(menuList) == Menu.ONLY_CHILD ){
			sb.append("</ul>");
			if( menu.getMenuDepth() == 2 ){
				sb.append("</li>");
			}
		}
	}
	
	if( menu.getMenuDepth() == 1 ){
		//현제 메뉴가 3뎁스 이상 선택됐을때만 3뎁스 메뉴 테그 생성
		if(currentMenu.getMenuDepth() > 2){
			for(Menu locationMenu : currentMenuList){
				if(locationMenu.getMenuDepth() == 2){
					if(locationMenu.getMenuChildren() != null && !locationMenu.getMenuChildren().isEmpty() ){
						sb.append("<li class=\"active\">");
						sb.append("<button>");
						for(Menu locationMenu3 : currentMenuList){
							if(locationMenu3.getMenuDepth() == 3){
								sb.append(locationMenu3.getMenuName());
							}
						}
						sb.append("</button>");
						sb.append("<ul class=\"depth2\">");
						for(Menu deps3Menu : locationMenu.getMenuChildren() ){
							if( "public".equals(deps3Menu.getMenuStatus()) ){
								sb.append("<li>");
								sb.append("<a class=\"");
								if( currentMenuList != null && currentMenuList.contains(deps3Menu) ){
									sb.append("active");
								}
								sb.append("\"");
								sb.append(" href=\"");
								sb.append(deps3Menu.getMenuLink());
								sb.append("\"");
								if( deps3Menu.isMenuNewWin() ){
									sb.append(" title=\"새창\"");
									sb.append(" target=\"_blank\"");
								}
								sb.append(">");
								sb.append(deps3Menu.getMenuName());
								sb.append("</a>");
								sb.append("</li>");
							}
						}
						sb.append("</ul>");
						sb.append("</li>");
							
					}
				}
			}
		}
		
		sb.append("</ul>");
		
	}
	return sb;
}
%>
<%
/**
	SNB 메뉴 목록 html 태그 생성
	- html 어트리뷰트나 태그변경등 디자인과 관련해서 수시로 변경될 가능성이 높으므로 jsp단에서 생성하도록 처리함.
	- gnb.jsp 와는 대부분 동일한 소스이나 100% 동일하진 않으므로 복붙은 하지말자.
	@since 2018.8.20
	@author yckim
*/
//menuService 빈을 끄집어 내자
ApplicationContext applicationContext = ApplicationContextProvider.getApplicationContext();
MenuService menuService = applicationContext.getBean(MenuService.class);
ConfigService configService = applicationContext.getBean(ConfigService.class);

//리퀘스트에서 메뉴목록을 끄집어 냄
@SuppressWarnings("unchecked")
List<Menu> snbMenuList = (List<Menu>)request.getAttribute("menuList");
//메뉴아이템들은 자식값이 다 비어 있으므로 채워주도록 하자
int maxDepth = 0;
for( Menu menu : snbMenuList ){
	maxDepth = menu.getMenuDepth() > maxDepth ? menu.getMenuDepth() : maxDepth;	
}
Menu parent = null;
//최하위 메뉴부터 루프를 돌면서 부모메뉴와 짝지어 주도록 한다.
for( int depth = maxDepth; depth>1; depth-- ){
	for( Menu menu : snbMenuList ){
		if( menu.getMenuDepth() == depth ){
			parent = menuService.findParentMenu(snbMenuList, menu);
			//if(menu.getMenuParent() == null || menu.getMenuParent().getMenuLink() == null){
			//	menu.setMenuParent(parent);
			//}
			if( parent.getMenuChildren() == null ){
				parent.setMenuChildren(new ArrayList<Menu>());
			}
			//이미 존재하는지 체크하지 않으면 gnb만들때 이미 한번 넣었기 때문에 중복으로 들어가게 된다.
			if( !parent.getMenuChildren().contains(menu) ){
				parent.getMenuChildren().add(menu);
			}
		}
	}
}
//1차메뉴를 추출해서 모음
List<Menu> topMenuList = new ArrayList<Menu>();
for( Menu menu : snbMenuList ){
	if( menu.getMenuDepth() == 1 ){
		topMenuList.add(menu);
	}
}
//태그 생성
Config designConfig = configService.getConfig("design");
StringBuilder sb = new StringBuilder(1000);
int snbDisplayDepth = Integer.parseInt( StringUtils.defaultString(designConfig.getOption("nav_snb_display_depth"), "3") );
Menu currentMenu = (Menu)request.getAttribute("currentMenu");
List<Menu> currentMenuList = menuService.getCurrentMenuList(snbMenuList, currentMenu);
//Menu topAncestorMenu = menuService.getTopAncestorMenu(snbMenuList, currentMenu);
Menu topAncestorMenu = (Menu)request.getAttribute("subTopMenu");

for( Menu m1 : topMenuList ){
	if( topAncestorMenu != null ){
		if( m1.getMenuId().equals(topAncestorMenu.getMenuId()) ){
			buildMenuMarkup(snbMenuList, m1, currentMenuList, currentMenu, snbDisplayDepth, sb);
		}
	}
}
String snbNav = sb.toString();
//서브메뉴끝 </ul> 확인. 태그생성 메서드에서 처리하게엔 좀 애매해서(사실 어딜 고쳐야 할지 모르겠음-_-) 별도로 처리함
if( StringUtils.isNotBlank(snbNav) && !snbNav.substring(snbNav.length() - 5).equals("</ul>") ){
	snbNav += "</ul>";
}
//서브메뉴 데이터가 없을때 레이아웃 찌그러지는것 방지용 
//if( StringUtils.isBlank(snbNav) ){
	//snbNav = "&nbsp;";
//}
out.write(snbNav);
%>