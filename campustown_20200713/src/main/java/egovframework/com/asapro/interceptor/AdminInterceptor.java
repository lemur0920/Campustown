/**
 * 
 */
package egovframework.com.asapro.interceptor;


import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import egovframework.com.asapro.capability.service.Capability;
import egovframework.com.asapro.capability.service.CapabilitySearch;
import egovframework.com.asapro.capability.service.CapabilityService;
//import egovframework.com.asapro.config.service.ConfigService;
import egovframework.com.asapro.member.admin.service.AdminMember;
import egovframework.com.asapro.member.admin.service.AdminMemberService;
import egovframework.com.asapro.role.service.Role;
import egovframework.com.asapro.role.service.RoleService;
import egovframework.com.asapro.site.service.Site;
import egovframework.com.asapro.site.service.SiteService;
import egovframework.com.asapro.support.Constant;
import egovframework.com.asapro.support.exception.AsaproNoCapabilityException;
import egovframework.com.asapro.support.util.AsaproUtils;
import egovframework.com.asapro.support.util.EtcUtil;
import egovframework.com.cmm.service.EgovProperties;

/**
 * 관리자 인터셉터
 * - 관리자페이지 접속시에만 적용
 * @author yckim
 * 
 */
public class AdminInterceptor extends HandlerInterceptorAdapter {

	private static final Logger LOGGER = LoggerFactory.getLogger(AdminInterceptor.class);

//	@Autowired
//	private ConfigService configService;
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private SiteService siteService;
	
	@Autowired
	private CapabilityService capabilityService;
	
	@Autowired
	private AdminMemberService adminMemberService;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		
		String requestUri = AsaproUtils.getFixedRequestUri(request);
		
		// SecurityInterceptor 에서 최초 생성됨
		Site currentSite = (Site) request.getAttribute("currentSite");
		Site authSite = (Site) request.getSession().getAttribute("authSite");
		AdminMember currentAdmin = (AdminMember) request.getSession().getAttribute("currentAdmin");
		
		//사이트별 권한 체크를 하기위해 로그인 상태이면서 사이트가 변경되면 해당 사이트의 권한을 새로 주입한다.
			
		if( currentAdmin != null && request.getSession().getAttribute("loginAdminId") != null && authSite != null ){
			if(!"admin".equals(currentAdmin.getAdminId()) ){
				if(!authSite.getSitePrefix().equals(currentSite.getSitePrefix()) ){
					AdminMember adminMemberSearch = new AdminMember();
					adminMemberSearch.setSitePrefix(currentSite.getSitePrefix());
					adminMemberSearch.setAdminId(currentAdmin.getAdminId());
					AdminMember fromDB = adminMemberService.getAdminMember(adminMemberSearch);
					
					if(fromDB.getAdminRole() == null || StringUtils.isBlank(fromDB.getAdminRole().getRoleCode()) ){
						LOGGER.info("[ASAPRO] AdminInterceptor " + " adminRole is null !!!");
						throw new AsaproNoCapabilityException("권한이 없습니다.","back");
					} else {
						List<Capability> capList = new ArrayList<Capability>();
						String roleName = "";
						int idx = 0;
						for (Role item : fromDB.getAdminRoleList() ) {
							if(item.getCapabilityList() != null && !item.getCapabilityList().isEmpty() ){
								for (Capability capability : item.getCapabilityList()) {
									if(!capList.contains(capability) ){
										capList.add(capability);
									}
								}
							}
							if(idx > 0){
								roleName += ",";
							}
							roleName += item.getRoleName();
							idx++;
						}
						
						fromDB.getAdminRole().setCapabilityList(capList);
						fromDB.getAdminRole().setRoleName(roleName);
					}
					
					/*CapabilitySearch capabilitySearch = new CapabilitySearch();
					capabilitySearch.setPaging(false);
					capabilitySearch.setRoleCode(fromDB.getAdminRole().getRoleCode());
					List<Capability> capList = capabilityService.getRoleCapabilityList(capabilitySearch);
					fromDB.getAdminRole().setCapabilityList(capList);*/
					
					request.getSession().setAttribute("currentAdmin", fromDB);
					request.getSession().setAttribute("authSite", currentSite);
				}
			}
		}
		//==================================================================
		
		String domainPort = AsaproUtils.getDomainPort(request,null,null);
		Site mainSite = siteService.getMainSite();
		
		//현제 호출된 도메인+포트 가 메인사이트에 등록된 도메인+포트 가 아니면 메인사이트 도메인으로 리다이렉트 시킨다.
		//메인사이트 도메인 으로만 관리자페이지를 열기위해
		boolean checkDomain = false;
		for (String mainSiteDomain : mainSite.getSeperatedSiteDomains() ) {
			if(domainPort.equals(mainSiteDomain) ){
				checkDomain = true;
				break;
			}
		}
		if(!checkDomain ){
			response.sendRedirect("//" + mainSite.getSeperatedSiteDomains().get(0) + AsaproUtils.getAdminPath(currentSite) + ".do");
			return false;
		}
		
		/*
		 * !! 일반적으로 configService 에서 getConfig로 설정값을 가져올때는 캐싱된 값을 가져오는데
		 * security 관련 설정은 수정시 사이트 접속이 불가한 경우가 생길 수 있으므로
		 * 그럴 경우 디비에서 직접 고쳐도 적용되도록 하기위해 getNoCacheOption 메서드를 이용해서 가져오도록 한다.
		 * */
//		String adminAccessWhitelist = configService.getNoCacheGlobalOption("global", "admin_access_whitelist").getOptValue();
		
		//========== 시작 : 관리자단 로그인 주소 ===================
		if( (AsaproUtils.getAdminPath(currentSite) + "/login.do").equals(requestUri) ){
			//로그인 한 상태에서 관리자 로그인 들어오면 관리자 메인으로 리다이렉트
			if( currentAdmin != null && !currentAdmin.isGuest() && request.getSession().getAttribute("loginAdminId") != null ){
				response.sendRedirect(AsaproUtils.getAdminPath(currentSite) + "/dashboard.do");
				return false;
			} else {
				LOGGER.info("[ASAPRO] AdminInterceptor " + " login is null !!!");
				return true;
			}
		}
		//========== 끝 : 관리자단 로그인 주소 =====================
		
		//========== 시작 : 관리자 로그아웃 주소 ===================
		if( (AsaproUtils.getAdminPath(currentSite) + "/logout.do").equals(requestUri) ){
			return true;
		}
		//========== 끝 : 관리자 로그아웃 주소 =====================
		
		//========== 시작 : 메뉴권한체크 =================================
		//관리자단만 권한체크하도록 일단 걸름
		if( request.getSession().getAttribute("loginAdminId") != null ){
			if("admin".equals(request.getSession().getAttribute("loginAdminId")) ){
				return true;
			}
			if(roleService.isCurrentAdminAccessibleResource(currentAdmin, requestUri, request.getParameterMap(), request.getMethod())){
				return true;
			} else {
				//일반사용자가 관리자 로그인 페이지로 이동시 로그아웃되면서, 관리자 로그인 페이지로 이동
				//loginService.logout(request, response);
				//response.sendRedirect(AsaproUtils.getAdminPath(currentSite) + "/login.do");
				throw new AsaproNoCapabilityException("권한이 없습니다.","back");
			}
		} else {
			response.sendRedirect(AsaproUtils.getAdminPath(currentSite) + "/login.do");
			return false;
		}
		//========== 끝 : 메뉴권한체크 =================================
				
	}
	
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		
		/**
		 * 관리자 페이지 메뉴 네비게이션 작동을 위한 activeUrl 파라메터를 전달한다.
		 * redirect: 인 경우만 처리하도록 한다.  나머진 모두 app.js 에서 처리
		 */
		//GET - POST - GET 패턴의 마지막 GET에 해당하는 redirect인 경우
		//실제로 GET 을 핸들링하는게 아니라 스프링 리다이렉트 프리픽스를 붙이고 나오는 경우
		if( StringUtils.isNotBlank(request.getParameter("activeUrl")) && StringUtils.isNotBlank(modelAndView.getViewName()) ){
			if( modelAndView.getViewName().startsWith("redirect:") ){
				
				String activeUel = request.getParameter("activeUrl");
				String viewName = modelAndView.getViewName();
				
				if(viewName.contains("?") ){
					modelAndView.setViewName(viewName + "&activeUrl=" + activeUel);
				}else{
					modelAndView.setViewName(viewName + "?activeUrl=" + activeUel);
				}
			}
		}
	}

}
