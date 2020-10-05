/**
 * 
 */
package egovframework.com.asapro.login.web;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

//import com.ksign.access.api.*;
//import com.ksign.access.sso.sso10.SSO10Conf;

import egovframework.com.asapro.config.service.Config;
import egovframework.com.asapro.config.service.ConfigService;

import egovframework.com.asapro.login.service.Login;
import egovframework.com.asapro.login.service.LoginAdminService;
import egovframework.com.asapro.login.service.LoginUserService;
import egovframework.com.asapro.member.admin.service.AdminMember;
import egovframework.com.asapro.member.admin.service.AdminMemberService;
import egovframework.com.asapro.member.user.service.UserMember;
//import egovframework.com.asapro.member.user.service.UserMemberService;
import egovframework.com.asapro.menu.service.Menu;
import egovframework.com.asapro.organization.service.Team;
import egovframework.com.asapro.role.service.Role;
import egovframework.com.asapro.site.service.Site;
import egovframework.com.asapro.support.Constant;
import egovframework.com.asapro.support.SEO;
import egovframework.com.asapro.support.annotation.CurrentMenu;
import egovframework.com.asapro.support.annotation.CurrentSite;
import egovframework.com.asapro.support.util.AsaproCookieSSO;
import egovframework.com.asapro.support.util.AsaproUtils;
import egovframework.com.asapro.support.util.EtcUtil;
import egovframework.com.campustown.startHpMngr.service.StartHpMngr;
import egovframework.com.campustown.startHpMngr.service.StartHpMngrSearch;
import egovframework.com.campustown.startHpMngr.service.StartHpMngrService;

/**
 * 사용자 로그인 컨트롤러
 * @author yckim
 * @since 2019. 3. 14.
 *
 */
@Controller
public class LoginUserController {
	
	@Autowired
	private MessageSource messageSource;
	
	@Autowired
	private LoginUserService loginUserService;
	
//	@Autowired
//	private UserMemberService userMemberService; 
	
	@Autowired
	private ConfigService configService;
	
	@Autowired
	private LoginValidator loginValidator;
	
	@Autowired
	private HttpServletRequest request;
	
	@Autowired
	private LoginAdminService loginAdminService;
	
	@Autowired
	private StartHpMngrService startHpMngrService;
	
	
	//==================================================================================================================================
	//===================================================== SSO 로그인 ======================================================================
	//==================================================================================================================================
/*	
	*//**
	 * SSO 로그인 폼뷰를 반환한다.
	 * @param model
	 * @param loginForm
	 * @return 로그인 폼 뷰
	 *//*
	@RequestMapping(value=Constant.APP_PATH + Constant.SITE_ID_PATH + "/member/SSOLogin", method=RequestMethod.GET)
	public String ssoLoginGet(Model model, HttpSession session, @CurrentSite Site currentSite, @ModelAttribute("userForm") Login loginForm){
		String returnUrl = AsaproUtils.getHtmlStrCnvr(loginForm.getReturnUrl());
		
		//로그인 한 상태로 또 이 페이지를 열 경우
		if( session.getAttribute("loginUserId") != null ){
			if( StringUtils.isBlank(returnUrl) || returnUrl.contains("/member/SSOLogin") ){
				return "redirect:/";
			} else {
				return "redirect:" + returnUrl;
			}
		}
		if(!AsaproUtils.isDevPath(request) ){
			return AsaproUtils.getThemePath(request) + "member/SSOLoginForm";
		}else{
			try {
				return "redirect:" + AsaproUtils.getAppPath(currentSite, true) + "/member/SSOLoginProc?returnUrl=" + URLEncoder.encode(returnUrl, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				return "redirect:" + AsaproUtils.getAppPath(currentSite, true) + "/member/SSOLoginProc?returnUrl=" + returnUrl;
			}
		}
	}
	
	*//**
	 * SSO 로그인 처리후 결과뷰를 반환한다.
	 * @param model
	 * @param loginForm
	 * @return 로그인 처리 결과뷰
	 *//*
	@RequestMapping(value=Constant.APP_PATH + Constant.SITE_ID_PATH + "/member/SSOLoginProc", method={RequestMethod.POST, RequestMethod.GET})
	public String ssoLoginPost(Model model, HttpServletResponse response, @CurrentSite Site currentSite, @ModelAttribute("userForm") Login loginForm){
		String returnUrl = AsaproUtils.getHtmlStrCnvr(loginForm.getReturnUrl());
		//로그인 한 상태로 또 이 페이지를 열 경우
		if( request.getSession().getAttribute("loginUserId") != null ){
			if( StringUtils.isBlank(returnUrl) || returnUrl.contains("/member/SSOLogin") ){
				return "redirect:/";
			} else {
				return "redirect:" + returnUrl;
			}
		}
		
		UserMember currentUser = new UserMember();
		String id = null;
		String name = "";
		
		//if(!AsaproUtils.isDevPath(request)){
			
			//SSORspData rspData = null;
			//SSOService ssoService = SSOService.getInstance();
			//rspData = ssoService.ssoGetLoginData(request);
			
			//if(rspData == null || rspData.getResultCode() == -1) {
			//	System.out.println("SSO로그인 실패!!!");
			//}
			
			//if(rspData != null){
			//	id = rspData.getAttribute(SSO10Conf.UIDKey);
			//	name = rspData.getAttribute("NAME");
				
			//	currentUser.setUserId(id);
			//	currentUser.setUserName(name);
			//currentUser.setUserLoginType("sso");
				
			//	Role guestRole = new Role();
			//	guestRole.setRoleCode("ROLE_NORMAL_USER");
			//	guestRole.setRoleName("사용자 일반회원");
			//	currentUser.setUserRole(guestRole);
				
			//}
		//}else{
			
			id = "member_" + AsaproUtils.getRempoteIp(request);
			name = "회원";
			currentUser.setUserId(id);
			currentUser.setUserName(name);
			currentUser.setUserLoginType("sso");
			
			Role guestRole = new Role();
			guestRole.setRoleCode("ROLE_NORMAL_USER");
			guestRole.setRoleName("사용자 일반회원");
			currentUser.setUserRole(guestRole);
		//}
		
		//관리자 로그인 세션 정보도 없으면 세션 무효화(깨끗하게)
//		AdminMember currentAdmin = (AdminMember) request.getSession().getAttribute("currentAdmin");
//		if(currentAdmin == null){
//			//일단 이전 세션을 제거한다.
//			if( request.getSession(false) != null ){
//				//기존 세션을 우선 무효화한다.
//				request.getSession(false).invalidate();
//			}
//		}
		
		//기존 세션을 우선 무효화한다.
		request.getSession(false).invalidate();
		
		request.getSession().setAttribute("loginUserId", id);
		request.getSession().setAttribute("currentUser", currentUser);
		//redirect to returnUrl or redirect to index
		
		if( StringUtils.isNotBlank(returnUrl) ){
			if( returnUrl.contains("/member/SSOLogin") ){
				return "redirect:/";
			}
			return "redirect:" + returnUrl;
		} else {
			//리턴url없으면 메인화면으로
			return "redirect:/";
		}
			
	}
*/	
	/**
	 * SSO 사용자 로그아웃 처리
	 * <br />
	 * 세션 폐기
	 * @param session
	 * @return 메인화면
	 * @throws IOException 
	 */
	@RequestMapping(value=Constant.APP_PATH + Constant.SITE_ID_PATH + "/logout")
	public String logout(HttpSession session, HttpServletResponse response) throws IOException{
		
//		String returnUrl = request.getParameter("returnUrl");
		String returnUrl = AsaproUtils.getSchemeDomainPort(request);

		loginUserService.logout(request, response);

		//String SSO_SERVER = "";
		//개발서버, 운영서버 는 서울 SSO도 로그아웃 처리
		//if(!AsaproUtils.isDevPath(request) ){
			//SSO_SERVER = SSOService.getInstance().getServerScheme();
		//	return "redirect:" + SSO_SERVER + "/sso/pmi-logout-url.jsp?returl=" + returnUrl;
		//}else{
			
			//redirect to returnUrl or redirect to index
//			if( StringUtils.isNotBlank(returnUrl) ){
//				return "redirect:" + returnUrl;
//			} else {
				return "redirect:/";
//			}
		//}
		
	}
	
	//==================================================================================================================================
	//===================================================== 일반 로그인 ======================================================================
	//==================================================================================================================================
	
	/**
	 * 로그인 폼뷰를 반환한다.
	 * @param model
	 * @param loginForm
	 * @return 로그인 폼 뷰
	 */
	@RequestMapping(value=Constant.APP_PATH + Constant.SITE_ID_PATH + "/login", method=RequestMethod.GET)
	public String loginGet(Model model, HttpSession session, @CurrentSite Site currentSite, @ModelAttribute("loginForm") Login loginForm){
		//로그인 한 상태로 또 이 페이지를 열 경우
		if( session.getAttribute("loginUserId") != null ){
			//String returnUrl = AsaproUtils.getHtmlStrCnvr(loginForm.getReturnUrl());
			String returnUrl = (String)session.getAttribute("login.returnUrl");
			AsaproUtils.removeFromSessionStartsWith(request, "login.returnUrl");
			
			if( StringUtils.isBlank(returnUrl) || returnUrl.contains("/login") ){
				return "redirect:/";
			} else {
				return "redirect:" + returnUrl;
			}
		}
		
		//if(!AsaproUtils.isDevPath(request) && !AsaproUtils.isAsadalNet(request)){
			model.addAttribute("seo", new SEO("로그인 | ", "module", "로그인"));
			return AsaproUtils.getThemePath(request) + "member/loginForm";
		//}else{
		//	return "redirect:" + AsaproUtils.getAppPath(currentSite, false) + "/loginProc";
		//}
	}
	
	/**
	 * 로그인 처리후 결과뷰를 반환한다.
	 * @param model
	 * @param loginForm
	 * @return 로그인 처리 결과뷰
	 */
	@RequestMapping(value=Constant.APP_PATH + Constant.SITE_ID_PATH + "/loginProc", method=RequestMethod.POST)
	public String loginPost(Model model, HttpSession session, RedirectAttributes redirectAttributes, HttpServletResponse response, @CurrentSite Site currentSite, @ModelAttribute("loginForm") Login loginForm, BindingResult bindingResult){
		//String returnUrl = AsaproUtils.getHtmlStrCnvr(loginForm.getReturnUrl());
		String returnUrl = (String)session.getAttribute("login.returnUrl");
		
		//로그인 한 상태로 또 이 페이지를 열 경우
		if( session.getAttribute("loginUserId") != null ){
			AsaproUtils.removeFromSessionStartsWith(request, "login.returnUrl");
			
			if( StringUtils.isBlank(returnUrl) || returnUrl.contains("/login") ){
				return "redirect:/인";
			} else {
				return "redirect:" + returnUrl;
			}
		}
		
		loginValidator.validate(loginForm, bindingResult, "USER");//사용자 로그인 validate
		
		if( bindingResult.hasErrors() ){
			return AsaproUtils.getThemePath(request) + "member/loginForm";
		} else {
			//기존 세션을 우선 무효화한다.
			//request.getSession(false).invalidate();
			
			
			UserMember currentUser = new UserMember();
			String id = null;
			String name = "";
			
			if(!AsaproUtils.isDevPath(request) ){
				/*
				 * 로그인 처리 서비스를 호출 후 정상 개정이면 로그인 처리
				 * 서비스단 개발 후 추가 처리
				 * */
				//id = loginForm.getLoginId();
				//id = "devMember";
				//name = "임시회원";
				
				//currentUser.setUserId(id);
				//currentUser.setUserName(name);
				
				/*Role guestRole = new Role();
				guestRole.setRoleCode("ROLE_NORMAL_USER");
				guestRole.setRoleName("사용자 일반회원");
				currentUser.setUserRole(guestRole);*/
				
/*				currentUser = loginUserService.userLogin(loginForm);
				
				//로그인 인증되면
				if(currentUser != null && StringUtils.isNotBlank(currentUser.getUserId()) ){
					
					id = currentUser.getUserId();
					
					currentUser.setUserLoginType("member");
					
					Role guestRole = new Role();
					guestRole.setRoleCode("ROLE_NORMAL_USER");
					guestRole.setRoleName("사용자 일반회원");
					currentUser.setUserRole(guestRole);
					
					currentUser = loginUserService.userLogin(loginForm);
					
					Role guestRole = new Role();
					guestRole.setRoleCode("ROLE_NORMAL_USER");
					guestRole.setRoleName("사용자 일반회원");
					currentUser.setUserRole(guestRole);*/
				
				AdminMember adminMember = loginAdminService.login(loginForm);
					//로그인 인증되면
				if(adminMember != null && StringUtils.isNotBlank(adminMember.getAdminId()) ){
						
						id = adminMember.getAdminId();
						
						currentUser.setUserLoginType("member");
						currentUser.setUserId(adminMember.getAdminId());
						currentUser.setUserName(adminMember.getAdminName());
						currentUser.setUserMobile1(adminMember.getAdminMobile1());
						currentUser.setUserMobile2(adminMember.getAdminMobile2());
						currentUser.setUserMobile3(adminMember.getAdminMobile3());
						currentUser.setUserOrganization(adminMember.getAdminOrganization());
						currentUser.setUserDepartment(adminMember.getAdminDepartment());
						currentUser.setUserTeam(adminMember.getAdminTeam());
						currentUser.setOrganization(adminMember.getOrganization());
						currentUser.setDepartment(adminMember.getDepartment());
						currentUser.setUserRole(adminMember.getAdminRole());
						
						StartHpMngrSearch startHpMngrSearch = new StartHpMngrSearch();
						startHpMngrSearch.setCompId(adminMember.getAdminTeam());
						StartHpMngr startHpMngrInfo = startHpMngrService.getStartHpMngrCompInfo(startHpMngrSearch);
						
						Team team = new Team();
						team.setTeamId(adminMember.getAdminTeam());
						if(startHpMngrInfo != null ){
							team.setTeamName(startHpMngrInfo.getCompNm());
						}
						currentUser.setTeam(team);
						
				} else {
					
					//로그인 실패시
					bindingResult.addError(new FieldError("loginForm", "loginPassword", "아이디 또는 비밀번호가 일치하지 않습니다."));
					return AsaproUtils.getThemePath(request) + "member/loginForm";
				}
				
			}else{
				
				id = "member_" + AsaproUtils.getRempoteIp(request);
				name = "회원";
				currentUser.setUserId(id);
				currentUser.setUserName(name);
				currentUser.setUserLoginType("dev");
				
				Role guestRole = new Role();
				guestRole.setRoleCode("ROLE_NORMAL_USER");
				guestRole.setRoleName("사용자 일반회원");
				currentUser.setUserRole(guestRole);
			}
			
			//관리자 로그인 세션 정보도 없으면 세션 무효화(깨끗하게)
//		AdminMember currentAdmin = (AdminMember) request.getSession().getAttribute("currentAdmin");
//		if(currentAdmin == null){
//			//일단 이전 세션을 제거한다.
//			if( request.getSession(false) != null ){
//				//기존 세션을 우선 무효화한다.
//				request.getSession(false).invalidate();
//			}
//		}
			
			//기존 세션을 우선 무효화한다.
//			request.getSession(false).invalidate();
			
			//세션에 담은 임시값 지운다 - "rental.reserv"로 시작되는 세션을 제외한
			AsaproUtils.removeFromSessionExcludeStartsWith(request, "rental.reserv");
			
			session.setAttribute("loginUserId", id);
			session.setAttribute("currentUser", currentUser);
			//redirect to returnUrl or redirect to index
			
			if( StringUtils.isNotBlank(returnUrl) ){
				AsaproUtils.removeFromSessionStartsWith(request, "login.returnUrl");
				
				if( returnUrl.contains("/login") ){
					return "redirect:/";
				}
				return "redirect:" + returnUrl;
			} else {
				//리턴url없으면 메인화면으로
				return "redirect:/";
			}
		}
		
		
			
	}
	
	
}