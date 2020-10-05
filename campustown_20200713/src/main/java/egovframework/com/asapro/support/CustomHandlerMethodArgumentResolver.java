/**
 * 
 */
package egovframework.com.asapro.support;

import java.lang.annotation.Annotation;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebArgumentResolver;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import egovframework.com.asapro.member.admin.service.AdminMember;
import egovframework.com.asapro.member.user.service.UserMember;
//import egovframework.com.asapro.member.service.Member;
import egovframework.com.asapro.menu.service.Menu;
import egovframework.com.asapro.site.service.Site;
import egovframework.com.asapro.support.annotation.CurrentAdmin;
import egovframework.com.asapro.support.annotation.CurrentMenu;
import egovframework.com.asapro.support.annotation.CurrentSite;
import egovframework.com.asapro.support.annotation.CurrentUser;
import egovframework.com.asapro.support.exception.AsaproException;
import egovframework.com.asapro.support.util.EtcUtil;

/**
 * 컨트롤러에서 사용되는 커스텀 어노테이션 바인딩 처리 리졸버
 * <p>
 * - 스프링 3.2 버전업되면서 설정에서 WebArgumentResolver를 사용할수 없게됨
 * </p>
 * @author yckim
 * @since 2018.4.17
 */
public class CustomHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CustomHandlerMethodArgumentResolver.class);
	
	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer container, NativeWebRequest nativeWebRequest, WebDataBinderFactory factory) throws Exception {
		
		if (LOGGER.isDebugEnabled()) {
			Annotation[] annotations = parameter.getParameterAnnotations();
			for (Annotation annotation : annotations) {
				LOGGER.trace("[ASAPRO] CUSTOM ANNOTATION... : {}", annotation.toString());
			}
		}

		HttpServletRequest request = (HttpServletRequest) nativeWebRequest.getNativeRequest();
		//Site currentSite = (Site) request.getAttribute("currentSite");

		//현재사이트 @CurrentSite 어노테이션
		if (parameter.getParameterType().equals(Site.class)) {
			if (parameter.getParameterAnnotation(CurrentSite.class) != null) {
				if(request.getAttribute("currentSite") != null){
					return (Site) request.getAttribute("currentSite");
				} else {
					throw new AsaproException("##### currentSite should not be NULL!!!");
				}
			}
		}
		
		// 현재관리자 @CurrentAdmin 어노테이션
		if (parameter.getParameterType().equals(AdminMember.class)) {
			if (parameter.getParameterAnnotation(CurrentAdmin.class) != null) {
				if(request.getSession().getAttribute("currentAdmin") != null){
					return (AdminMember) request.getSession().getAttribute("currentAdmin");
				} else {
					//throw new AsaproException("##### currentAdmin should not be NULL!!!");
					LOGGER.warn("##### currentAdmin should not be NULL!!!");
					return null;
				}
			}
		}
		
		
		// 현재사용자 @CurrentUser 어노테이션
		if (parameter.getParameterType().equals(UserMember.class)) {
			if (parameter.getParameterAnnotation(CurrentUser.class) != null) {
				if(request.getSession().getAttribute("currentUser") != null){
					return (UserMember) request.getSession().getAttribute("currentUser");
				} else {
					throw new AsaproException("##### currentUser should not be NULL!!!");
				}
			}
		}
		
		//현재메뉴 @CurrentMenu 어노테이션
		if (parameter.getParameterType().equals(Menu.class)) {
			if (parameter.getParameterAnnotation(CurrentMenu.class) != null) {
				if(request.getAttribute("currentMenu") != null){
					return (Menu) request.getAttribute("currentMenu");
				} else {
					//throw new AsaproException("##### currentMenu should not be NULL!!!");
					LOGGER.warn("[asapro] CustomAnnotationResolver - There is no assigned menu to requested url...");
				}
			}
		}
		
		
		return WebArgumentResolver.UNRESOLVED;
		
	}

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		//타겟 타입을 지정해 주지 않으면 컨트롤러 파라메터에서 @ModelAttribute 를 지정해 주지 않고 그냥 커멘드 객체를 사용할 경우 dataBinding 이 안되는 경우가 생김.
		return parameter.getParameterAnnotation(CurrentSite.class) != null
				|| parameter.getParameterAnnotation(CurrentAdmin.class) != null
				|| parameter.getParameterAnnotation(CurrentUser.class) != null
				|| parameter.getParameterAnnotation(CurrentMenu.class) != null;
	}

}
