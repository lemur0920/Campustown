/**
 * 
 */
package egovframework.com.asapro.interceptor;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import egovframework.com.asapro.content.service.Content;
import egovframework.com.asapro.content.service.ContentService;
import egovframework.com.asapro.member.user.service.UserMember;
import egovframework.com.asapro.support.SEO;
//import egovframework.com.asapro.member.service.Member;
import egovframework.com.asapro.menu.service.Menu;
import egovframework.com.asapro.menu.service.MenuContentRelation;
import egovframework.com.asapro.menu.service.MenuSearch;
import egovframework.com.asapro.menu.service.MenuService;
import egovframework.com.asapro.site.service.Site;
import egovframework.com.asapro.support.Constant;
import egovframework.com.asapro.support.exception.AsaproNotFoundException;
import egovframework.com.asapro.support.util.AsaproUtils;
import egovframework.com.asapro.support.util.EtcUtil;

/**
 * 디자인(메뉴) 인터셉터(실행순서는 SecurityInterceptor다음)
 * <ul>
 * 	<li>주로 메뉴와 관련된 설정들을 처리한다.</li>
 * </ul>
 * 
 * @author yckim
 * 
 */
public class DesignInterceptor extends HandlerInterceptorAdapter {

	private static final Logger LOGGER = LoggerFactory.getLogger(DesignInterceptor.class);
	
	@Autowired
	private MenuService menuService;
	
	@Autowired
	private ContentService contentService;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

		String requestUri = AsaproUtils.getFixedRequestUri(request);
		// SecurityInterceptor 에서 최초 생성됨
		UserMember currentUser = (UserMember) request.getSession().getAttribute("currentUser");
		Site currentSite = (Site) request.getAttribute("currentSite");
		
		//사용자단에서 .do 달고 들어오면 
		if( !requestUri.startsWith(AsaproUtils.getAdminPath(currentSite)) && requestUri.endsWith(".do") ){
			//404던짐
			LOGGER.warn("[asapro] DesignInterceptor - request uri is not valid. 404 returned.");
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return false;
		}
		
		//기본 JSP 변수
		//관리자 패스
		request.setAttribute("ADMIN_PATH", AsaproUtils.getAdminPath(currentSite));
		//사용자단 기본패스
		request.setAttribute("APP_PATH", AsaproUtils.getAppPath(currentSite));
		//사용자단 import시 사용 기본패스
		request.setAttribute("IMPORT_APP_PATH", AsaproUtils.getAppPath(currentSite,true));
		//컨택스트 패스
		request.setAttribute("CONTEXT_PATH", Constant.CONTEXT_PATH);
		//사용자단 API패스
		request.setAttribute("API_PATH", Constant.API_PATH);
		//업로드 폴더 패스
		request.setAttribute("UPLOAD_PATH", Constant.UPLOAD_PATH);
		//사이트별 업로드 폴더 패스
		request.setAttribute("SITE_UPLOAD_PATH", Constant.UPLOAD_PATH  + "/" + currentSite.getSiteId());
		//현제날짜
		request.setAttribute("NOW", AsaproUtils.getFormattedDate(new Date()));
		//현제날짜
		request.setAttribute("NOWDate", new Date());
		
		//========== 시작 : 테마 & 미리보기 ===============================
		//사용자단이면
		if( AsaproUtils.isUserPath(requestUri) && StringUtils.isNotBlank(request.getParameter("themePreview")) ){
			request.setAttribute("theme", request.getParameter("themePreview"));
		} else {
			request.setAttribute("theme", currentSite.getSiteTheme());
		}
		//========== 끝 : 테마 & 미리보기 ===============================
		
		//========== 시작 : 현재메뉴 ==========
		Menu currentMenu = null;
		//사용자 단이면
		if( AsaproUtils.isUserPath(requestUri) ){
			currentMenu = menuService.parseMenuInformationFromUrl(requestUri);
			request.setAttribute("currentMenu", currentMenu);
		} else {
			// APP_PATH 로 시작되지 않는 주소다-> 주소 처리하지 않는다.
			LOGGER.debug("[ASAPRO] DesignInterceptor uri does not start with APP_PATH. not finding current menu...");
		}
		if( currentMenu != null ){
			LOGGER.info("[ASAPRO] DesignInterceptor menuName : {}, menuId : {} ", currentMenu.getMenuName(), currentMenu.getMenuId());
		} else {
			if( isDesignApplyUri(requestUri, currentSite) ){
				LOGGER.info("[ASAPRO] DesignInterceptor Current Menu is NULL...");
			}
		}
		//========== 끝 : 현재메뉴 ==========
		
		//========== 시작 : 메뉴상태체크 ===============================
		//잠김메뉴일 경우
		if( currentMenu != null && "locked".equals(currentMenu.getMenuStatus()) ){
			//관리자, 메뉴관리자가 아니면 못들어감
			if( currentUser.getUserRole().isRoleAdmin() || currentUser.hasCapability("CAP_MENU") ){
				//pass
				return true;
			} else {
				//404던짐
				//response.setStatus(HttpServletResponse.SC_NOT_FOUND);
				//return false;
				throw new AsaproNotFoundException("N/A", "/");
			}
		}
		//========== 끝 : 메뉴상태체크 ===============================
		
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		
		String requestUri = AsaproUtils.getFixedRequestUri(request);
		
		Site currentSite = (Site) request.getAttribute("currentSite");
				
		//디자인 정보는 사용자단에만
		if( this.isDesignApplyUri(requestUri, currentSite) ){
			
			//========== 시작 : 테마 ===============================
			String theme = (String) request.getAttribute("theme");
			//========== 끝 : 테마 ===============================
			
			Menu currentMenu = (Menu)request.getAttribute("currentMenu");
			
			//========== 시작 : 메뉴리스트 ========================
			MenuSearch menuSearch = new MenuSearch();
			menuSearch.setSitePrefix(currentSite.getSitePrefix());
			List<Menu> menuList = menuService.getMenuList(menuSearch);
			request.setAttribute("menuList", menuList);
			//현제메뉴의 부모메뉴 세트
			if(currentMenu != null){
				Menu parentMenu = menuService.findParentMenu(menuList, currentMenu);
				currentMenu.setMenuParent(parentMenu);
				
				//현제메뉴의 전체 부모메뉴 목록
				List<Menu> currentMenuList = menuService.getCurrentMenuList(menuList, currentMenu);
				request.setAttribute("currentMenuList", currentMenuList);
				
				//현제메뉴의 최상위 부모메뉴 (1deps)
				Menu subTopMenu = menuService.getTopAncestorMenu(menuList, currentMenu);
				request.setAttribute("subTopMenu", subTopMenu);
				
				//현제메뉴뎁스가 4이상이면 3뎁스 메뉴 
				if(currentMenu.getMenuDepth() > 3){
					for (Menu menu : currentMenuList) {
						if(menu.getMenuDepth() == 3){
							request.setAttribute("current3DepthMenu", menu);
							break;
						}
					}
				}
			}
			
			//========== 끝 : 메뉴리스트 ========================
			
			//========== 시작 : 디자인요소 ========================
			Map<String, String> design = new HashMap<String, String>(); 
			
			//themeDir
			StringBuilder themeDir = new StringBuilder(50);
			themeDir.append(Constant.THEME_ROOT);
			design.put("themeRoot", Constant.THEME_ROOT);
			themeDir.append(theme);
			design.put("themeDir", themeDir.toString());
			
			//for image, css, js
			StringBuilder resource = new StringBuilder(50);
			resource.append(Constant.CONTEXT_PATH);
			resource.append("/design/theme/");
			resource.append(theme);
			design.put("resource", resource.toString());
			request.setAttribute("design", design);
			
			//헤더 & 푸터 - 현재메뉴가 있는 경우 메뉴설정에서 입력된 헤더푸터값을 사용
			if( currentMenu != null ){
				design.put("header", themeDir.toString() + "/layout/" + StringUtils.defaultString(currentMenu.getMenuHeader(), "header.jsp"));
				design.put("footer", themeDir.toString() + "/layout/" + StringUtils.defaultString(currentMenu.getMenuFooter(), "footer.jsp"));
			}
			//현재메뉴가 없는 경우 고정값 처리
			else {
				design.put("header", themeDir.toString() + "/layout/header.jsp");
				design.put("footer", themeDir.toString() + "/layout/footer.jsp");
			}
			//========== 끝 : 디자인요소 ========================
			
			//========== 시작 : skin 적용기능 ========================
			/**
			 * skin=xxx 파라메타를 추가할 경우 동일한 폴더에 있는 xxx.jsp 파일을 기본뷰대신 사용한다.
			 */
			if( StringUtils.isNotBlank(request.getParameter("skin")) && StringUtils.isNotBlank(modelAndView.getViewName()) ){
				String viewNameFull = modelAndView.getViewName();
				String viewPath = FilenameUtils.getPath(viewNameFull);
				String skinName = request.getParameter("skin").trim();
				modelAndView.setViewName(viewPath + skinName);
				LOGGER.info("[ASAPRO] DesignInterceptor - design skin was applied : skinName - {}", skinName);
			}
			//========== 끝 : skin 적용기능 ========================
			
			//========== 시작 : tempLayout 파라메터 적용 header & footer ===============================
			/**
			 * tempLayout=xxx 파라메터를 추가할 경우 메뉴설정에서 지정되어 있는 헤더, 푸터 대신
			 * header_xxx.jsp, footer_xxx.jsp 파일이 적용된다.
			 */
			if( AsaproUtils.isUserPath(requestUri) ){
				String tempLayout = null;
				//파라메터 체크해서
				if( StringUtils.isNotBlank(request.getParameter("tempLayout")) ){
					tempLayout = request.getParameter("tempLayout");
					//post -> get 하는 경우에도 적용되게 하기 위해 세션에 넣는다
					request.getSession().setAttribute("tempLayout", tempLayout);
				}
				//파라메터 없으면 세션에 있는걸 사용 post -> get 해당
				if( tempLayout == null && request.getSession().getAttribute("tempLayout") != null ){
					tempLayout = (String)request.getSession().getAttribute("tempLayout");
				}
				//헤더, 푸터 교체
				if( StringUtils.isNotBlank(tempLayout) ){
					design.put("header", themeDir.toString() + "/layout/header_" + tempLayout + ".jsp");
					design.put("footer", themeDir.toString() + "/layout/footer_" + tempLayout + ".jsp");
					request.setAttribute("tempLayout", tempLayout);
				}
				//파라메터에 없는데 세션에 있으면 세션에서 제거
				if( StringUtils.isBlank(request.getParameter("tempLayout")) && request.getSession().getAttribute("tempLayout") != null ){
					request.getSession().removeAttribute("tempLayout");
				}
			} 
			//========== 끝 : tempLayout 파라메터 적용 header & footer ===============================
			
			if( currentMenu != null && modelAndView != null ){
				
				//========== 시작 : SEO ========================
				//게시판, 아카이브 같은 경우 seo 을 컨트롤러에서 직접 리퀘스트에 태우는 방식으로 처리
				//리퀘스트에 seo 이 없더라도 현재메뉴를 seo로 바꿔서 처리한다.
				SEO seo = (SEO) modelAndView.getModel().get("seo");
				if( seo == null ){
					String description = currentMenu.getMenuName();
					if( "content".equals(currentMenu.getMenuType()) ){
						MenuContentRelation menuContentRelation = new MenuContentRelation();
						menuContentRelation.setSitePrefix(currentSite.getSitePrefix());
						menuContentRelation.setMenuId(currentMenu.getMenuId());
						Content content = contentService.getContentByRel(menuContentRelation);
						
						if( content != null ){
							description = StringUtils.abbreviate(content.getContentPlain(), 200);
						}
					} else {
						if( StringUtils.isNotBlank(currentSite.getSiteDescription()) ){
							description += ", " + currentSite.getSiteDescription(); 
						}
					}
					seo = new SEO(currentMenu.getMenuName(), "currentMenu", description, menuList, currentMenu); 
				} else {
					seo.setMenuList(menuList);
					seo.setCurrentMenu(currentMenu);
				}
				modelAndView.getModel().put("seo", seo);
				//========== 끝 : SEO ========================
			} else {
				LOGGER.info("[ASAPRO] DesignInterceptor Current menu is null. returns default design model...");
				//========== 시작 : SEO ========================
				//게시판, 아카이브 같은 경우 seo 을 컨트롤러에서 직접 리퀘스트에 태우는 방식으로 처리
				//리퀘스트에 seo 이 없더라도 현재메뉴를 seo로 바꿔서 처리한다.
				if( modelAndView != null ){
					SEO seo = (SEO) modelAndView.getModel().get("seo");
					if( seo != null ){
						seo.setMenuList(menuList);
						seo.setCurrentMenu(currentMenu);
					} else {
						seo = new SEO(requestUri, null, null, null, null);
						LOGGER.info("[ASAPRO] DesignInterceptor SEO is null, specify by module...");
					}
					modelAndView.getModel().put("seo", seo);
				}
				//========== 끝 : SEO ========================
			}
			
			LOGGER.info("[ASAPRO] DesignInterceptor " + " SETTING DESIGN MODEL...");
			
		} 
		
		if( (AsaproUtils.getAdminPath(currentSite) + "/refreshDummy.do").equals(requestUri) ) {
			///admin/refreshDummy.do 호출되서 이 부분이 계속 실행되어야 사용자단으로 돌아갔을때 원래 레이아웃이 적용됨.
			request.getSession().removeAttribute("tempLayout");
			LOGGER.info("[ASAPRO] DesignInterceptor - tempLayout has been removed from session!");
		}
	
	}
	
	//디자인 변수를 적용할 uri인지 체크
	//이렇게 걸러주지 않으면 getOutputStream() already 어쩌고 하는 에러 남..
	private boolean isDesignApplyUri(String requestUri, Site currentSite){
		return 
				// 사용자 단에서만
				AsaproUtils.isUserPath(requestUri)
				// 파일 다운로드나 이미지 호출 경로 패스
				&& !AsaproUtils.isFilePath(requestUri)
				// RSS 피드경로  패스 
				&& !AsaproUtils.isFeedPath(requestUri)
				// API 경로 패스
				&& !AsaproUtils.isApiPath(requestUri);
	}
}