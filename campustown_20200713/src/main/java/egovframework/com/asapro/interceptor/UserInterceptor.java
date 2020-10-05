/**
 * 
 */
package egovframework.com.asapro.interceptor;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import egovframework.com.asapro.archive.service.ArchiveCategory;
import egovframework.com.asapro.archive.service.ArchiveCategorySearch;
import egovframework.com.asapro.archive.service.ArchiveService;
import egovframework.com.asapro.site.service.Site;
import egovframework.com.asapro.support.util.AsaproUtils;

/**
 * 사용자단만 관리하는 인터셉터
 * @author yckim
 * @since 2019. 3. 14.
 *
 */
public class UserInterceptor extends HandlerInterceptorAdapter{

	@Autowired
	private ArchiveService archiveService;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		
		String requestUri = AsaproUtils.getFixedRequestUri(request);

		//관리자인 경우 패스
		if( AsaproUtils.isAdminPath(requestUri) ){
			return true;
		}
		
		Site currentSite = (Site)request.getAttribute("currentSite");
		
		//========== 시작 : 로그인 회원영역 체크 ==========
		/*
		 * member uri 구분
		 *  - 로그인 해야만 접근가능 처리
		 * /site/main/member/mypage/* - 마이페이지
		 * /site/[a-zA-Z0-9]+/rental/[a-zA-Z0-9_]+/reserv/.* - 대관예약
		 * */
		if( (requestUri.startsWith( AsaproUtils.getAppPath(currentSite)) && requestUri.contains("/mypage/"))
        || requestUri.contains("/counsel/legal_counsel/write")
		|| requestUri.contains("/counsel/make_counsel/write")
		|| requestUri.matches( "/site/[a-zA-Z0-9]+/rental/[a-zA-Z0-9_]+/reserv/.*") ) {

				if( request.getSession().getAttribute("loginUserId") == null ){
					//원래 보려고 하던 페이지를 returnUrl 로 세팅
					String returnUrl = (String)request.getSession().getAttribute("login.returnUrl");;
					if(StringUtils.isBlank(returnUrl) ){
						returnUrl = AsaproUtils.getCurrentUrl(request, true, false);
						request.getSession().setAttribute("login.returnUrl", returnUrl);
					}
					
					//response.sendRedirect(AsaproUtils.getAppPath(currentSite) + "/member/SSOLogin?returnUrl=" + returnUrl);
					//response.sendRedirect(AsaproUtils.getAppPath(currentSite) + "/login?returnUrl=" + returnUrl);
					response.sendRedirect(AsaproUtils.getAppPath(currentSite) + "/login");
					return false;
				}
		} 
		/*
		 * - 로그인 한 상태에서 접근 불가능
		 * /site/main/member/*
		 * 
		 * /mypage/ 마이페이지 제외
		 */
		else if( requestUri.startsWith( AsaproUtils.getAppPath(currentSite)) && requestUri.contains("/member/") && !requestUri.contains("/mypage/") ){
			//인증번호 발송 - 로그인 상과없이 발송되어야 해서 예외처리
			if(requestUri.contains("/sendCertiNum") || requestUri.contains("/submitCertiNum") ){
				return true;
			}
			
			if( request.getSession().getAttribute("loginUserId") != null ){
				response.sendRedirect(AsaproUtils.getAppPath(currentSite) + "/member/mypage/index");
				return false;
			}
		}
		//========== 끝 : 로그인 회원영역 체크 ==========
		
		return true;
	}

	
}
