/**
 * 
 */
package egovframework.com.asapro.interceptor;

import java.io.IOException;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

//import egovframework.com.asapro.config.service.Config;
//import egovframework.com.asapro.config.service.ConfigService;
import egovframework.com.asapro.member.Member;
import egovframework.com.asapro.member.admin.service.AdminMember;
import egovframework.com.asapro.member.user.service.UserMember;
import egovframework.com.asapro.role.service.Role;
import egovframework.com.asapro.site.service.Site;
import egovframework.com.asapro.support.Constant;
import egovframework.com.asapro.support.exception.AsaproException;
import egovframework.com.asapro.support.taglib.Functions;
import egovframework.com.asapro.support.util.AsaproUtils;
import egovframework.com.asapro.support.util.EtcUtil;

/**
 * 보안관련 처리 인터셉터
 * <pre>
 * 1. CurrentUser, CurrentAdmin 정보가 최초로 세팅된다.
 * 2. 보안토큰을 처리한다 - (Replay Attack 및 중복 전송 방지를 위한 nonce 체크기능과 CSRF(XSRF) 필터링를 동시에 처리하는 인터셉터)  
 * 3. a_csrf_token 추가됨 - ajax요청시 토큰이 갱신되서 일반 폼 요청이 처리되지 않는 부분 개선, 비동기용 토큰과 일반폼 토큰을 따로 관리한다.
 * 
 * - 사용자단 폼안에서 ${SECURITY_TOKEN_TAG}
 * - 주의 : Ajax사용시, POST 전송시에는 ${SECURITY_TOKEN_NAME}, ${SECURITY_TOKEN} EL변수로 출력해서 전송해야 한다.
 * - 크로스 사이트 요청 변조(Cross Site Request Forgery) 를 막기 위한 토큰 생성및 체크 필터
 * - 매 GET 요청시 마다 새로운 난수를 생성해서 화면으로 보내서 폼 서브밋 시에 받아서 동일한지 체크한다.
 * - 생성된 난수는 쿠키에 저장됨
 * - 관리자단에서는 사용하지 않는다. 
 * - 사용자단에서는 token을 사용하는 것을 원칙으로 한다.(현재댓글부분은 pass처리하게 해놓음...)
 * 
 * - nonce 참고	= http://tyleregeto.com/article/a-guide-to-nonce => 내용 좋으니까 한번씩 읽어보세요.
 * - csrf 참고 	= https://code.google.com/p/csrf-filter/ => 구현 소스 참고함
 * - ajax 참고	= https://docs.djangoproject.com/en/dev/ref/contrib/csrf/ 
 * </pre>
 * 
 * @author yckim
 * @since 2018. 6. 22.
 * 
 */
@Component(value="securityInterceptor")
public class SecurityInterceptor extends HandlerInterceptorAdapter {

	private static final Logger LOGGER = LoggerFactory.getLogger(SecurityInterceptor.class);

	private static final String ONCE_PER_REQUEST_ATTRIBUTE_NAME = SecurityInterceptor.class.getName() + ".ATTR";
	
	
//	@Autowired
//	private ConfigService configService;
	
//	@Autowired
//	private MemberService memberService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

		String requestUri = AsaproUtils.getFixedRequestUri(request);
		Site currentSite = (Site)request.getAttribute("currentSite");
//		Config globalConfig = configService.getConfig("global");
		
		//========== 시작 : 사용자 정보 ==========
		// currentUser 정보 생성 - 이곳이 currentUser 최초 생성 시점이다
		// 로그인 했으면 세션에 사용자 정보가 들어 있다.
		UserMember currentUser = (UserMember) request.getSession().getAttribute("currentUser");
		AdminMember currentAdmin = (AdminMember) request.getSession().getAttribute("currentAdmin");
		
		//========== 시작 : SSO 쿠키 처리 ===========
		
		//========== 끝 : SSO 쿠키 처리 ===========
		
		//로그인 정보가 없다 - SSO 처리했는데도 없으면 비로그인상태의 방문객임.
		if( !AsaproUtils.isAdminPath(requestUri) ){
			if ( currentUser == null ) {
				if(currentAdmin == null){
					
					//일단 이전 세션을 제거한다.
					if( request.getSession(false) != null ){
						request.getSession(false).invalidate();
					}
					request.getSession().setAttribute("loginUserId", null);
				}
				currentUser = new UserMember();
				
				
				currentUser.setUserId("guest_" + AsaproUtils.getRempoteIp(request));
				currentUser.setUserName("비회원손님");
				Role guestRole = new Role();
				guestRole.setRoleCode("ROLE_GUEST");
				guestRole.setRoleName("비회원");
				
				/*
				currentUser.setUserId("admin");
				currentUser.setUserName("관리자");
				Role guestRole = new Role();
				guestRole.setRoleCode("ROLE_SUPER_ADMIN");
				guestRole.setRoleName("최고관리자");
				guestRole.setRoleAdmin(true);
				*/
				
				currentUser.setUserRole(guestRole);
				
				LOGGER.info("[ASAPRO] Setting Guest User To Session.. ");
				request.getSession().setAttribute("currentUser", currentUser);
				
				//reset security token
//				AsaproUtils.deleteCookie(request, response, Constant.SECURITY_ASYNC_COOKIE_NAME);
			}
		}
		//========== 끝 : 사용자 정보 ==========

		//========== 시작 : 보안토큰처리 csrf_token, a_csrf_token ==========
		//관리자쪽 보안토큰 적용 재외
		if( AsaproUtils.isAdminPath(requestUri) ){
//			if(!requestUri.matches("/[a-z0-9]+/asacms/banner/.*") ){
				
				return true;
//			}
		}
		
		/*
		 * 임시처리이므로 차후 처리 필요
		 */
		//사용자 페이지 ajax 호출시 패스 - 통계때문에 일단 패스로 설정
		if( requestUri.contains("/statistics" + Constant.API_PATH + "/") ){
			return true;
		}
		//추천시 패스 - 일단 패스로 설정
		if( requestUri.contains(Constant.API_PATH + "/recommend") ){
			return true;
		}
		//투표 패스 - 일단 패스로 설정
		if( requestUri.contains("/poll/") && requestUri.contains(Constant.API_PATH) ){
			return true;
		}
		
		
		// 보안토큰 적용안할 uri 는 패스1
		if ("/".equals(requestUri) || Constant.APP_PATH.equals(requestUri)) {
			return true;
		}

		// 보안토큰 적용안할 uri 는 패스2 - 관리자 , image 등 제외
		for (String curStart : Constant.SECURITY_EXCLUDE_STARTWITH_URIS) {
			if (requestUri.startsWith(curStart)) {
				return true;
			}
		}
		
		// 보안토큰 적용안할 uri 는 패스3
		for (String curEnd : Constant.SECURITY_EXCLUDE_ENDWITH_URIS) {
			if (requestUri.endsWith(curEnd)) {
				return true;
			}
		}
		
		// 보안토큰 적용안할 uri 는 패스4
		for (String curContain : Constant.SECURITY_EXCLUDE_CONTAINWITH_URIS) {
			if (requestUri.contains(curContain) ) {
				return true;
			}
		}
		
		//=========================================================
		long currentTimeMillis = System.currentTimeMillis();
		String currentMillis = String.valueOf(currentTimeMillis);
		
		if (request.getAttribute(ONCE_PER_REQUEST_ATTRIBUTE_NAME) != null) {
			return true;
		} else {
			request.setAttribute(ONCE_PER_REQUEST_ATTRIBUTE_NAME, Boolean.TRUE);
			try {
				// POST 메서드 일 경우
				if ( "POST".equals(request.getMethod().toUpperCase()) ) {
					
					String tokenRaw = request.getParameter(Constant.SECURITY_TOKEN_NAME);
					
					//forward 된 경우 파라미터로 보안토큰을 담을 수 없어 attribute 에 담았다.
					String tokenRaw2 = (String)request.getAttribute(Constant.SECURITY_TOKEN_NAME);
					if(StringUtils.isNotBlank(tokenRaw2) ){
						tokenRaw = tokenRaw2;
						request.removeAttribute(Constant.SECURITY_TOKEN_NAME);
					}
					
					//일반 폼 요청이다
					
					LOGGER.info("[ASAPRO] SecurityInterceptor tokenRow : {} ", tokenRaw);
					
					if( StringUtils.isBlank(tokenRaw) ){
						LOGGER.info("[ASAPRO] SecurityInterceptor 유효하지 않는 요청입니다.(TOKEN MISSING)");
						throw new AsaproException("유효하지 않는 요청입니다.(TOKEN MISSING)");
					}
					// rebuilding token
					String[] temp = tokenRaw.split("_");
					String recreatedMillis = temp[0];
					String originalToken = temp[1].trim();
					String recreatedToken = this.createSecurityToken(currentUser.getUserId(), recreatedMillis, "form");
					
					// 토큰 값 jsp 호출용
					request.setAttribute("SECURITY_TOKEN", tokenRaw);
					// 토큰이름 세팅 jsp 단에서 호출
					request.setAttribute("SECURITY_TOKEN_NAME", Constant.SECURITY_TOKEN_NAME);
					// 쿼리스트링 형태 jsp 호출용
					request.setAttribute("SECURITY_TOKEN_QS", this.createSecurityTokenQueryString(tokenRaw));
					// hidden input jsp 호출용
					request.setAttribute("SECURITY_TOKEN_TAG", this.createSecurityTokenTag(tokenRaw));
					
					//아래쪽의 postHandle 메서드에서 사용함
					request.setAttribute("recreatedToken", recreatedToken);
					request.setAttribute("recreatedMillis", recreatedMillis);
					
					// nonce 체크
					if (!recreatedToken.equals(originalToken)) {
						if (!response.isCommitted()) {
							response.sendError(400, "유효하지 않은 요청입니다.(SECURITY_TOKEN(NONCE) MISMATCHING " + recreatedToken + " vs. " + originalToken + ")");
							return false;
						}
					}
					// nonce expire 체크
					long nonceMillis = Long.parseLong(recreatedMillis);
					if (currentTimeMillis > nonceMillis + Constant.SECURITY_TOKEN_LIFE) {
						if (!response.isCommitted()) {
							//response.sendError(400, "유효하지 않은 요청입니다.(SECURITY_TOKEN(NONCE) EXPIRED)");
							request.getSession().invalidate();
							//response.sendRedirect("/");
							response.sendRedirect(AsaproUtils.getCurrentUrl(request, true));
							return false;
						}
					}
					// csrf체크
					String sessionToken = (String) request.getSession().getAttribute(Constant.SECURITY_TOKEN_NAME); 
					if (!tokenRaw.equals(sessionToken)) {
						LOGGER.info("mismatched csrf token. expected: {} received: {} path: {}", new Object[] { tokenRaw, sessionToken, requestUri });
						if (!response.isCommitted()) {
							//response.sendError(400, "유효하지 않은 요청입니다.(SECURITY_TOKEN(CSRF) MISMATCHED)");
							//WAS 재시작 했을 경우 이럴 수 있음.
							//세션을 무효화하면 쿠키에 있는 정보로 새로 생성함
							request.getSession().invalidate();
							//response.sendRedirect("/");
							response.sendRedirect(AsaproUtils.getCurrentUrl(request, true));
						}
						return false;
					}
					
				}
				// GET 메서드일경우
				else {
					
					//===== form 용 토큰 =====
					if( !requestUri.contains(Constant.API_PATH + "/") ){
							
						// 폼용 보안토큰 세션에 있는거 확인 후 생성하도록 변경
						String token = null;
						String newToken = null;
						String sessionToken = (String) request.getSession().getAttribute(Constant.SECURITY_TOKEN_NAME); 
						if( sessionToken == null ){
							//System.out.println("[ASAPRO] SecurityInterceptor : " + currentUser.getUserId());
							token = this.createSecurityToken(currentUser.getUserId(), currentMillis, "form");
							newToken = currentMillis + "_" + token;
							LOGGER.info("[ASAPRO] SecurityInterceptor NEW FORM SECURITY TOKEN WAS CREATED : {} ", newToken);
						} else {
							token = sessionToken;
							newToken = token;
							LOGGER.info("[ASAPRO] SecurityInterceptor USING SESSION FORM SECURITY TOKEN : {} ", newToken);
						}
						
						// 토큰 값 jsp 호출용
						request.setAttribute("SECURITY_TOKEN", newToken);
						// 토근이름 세팅 jsp 단에서 호출
						request.setAttribute("SECURITY_TOKEN_NAME", Constant.SECURITY_TOKEN_NAME);
						// 쿼리스트링 형태 jsp 호출용
						request.setAttribute("SECURITY_TOKEN_QS", this.createSecurityTokenQueryString(newToken));
						// hidden input jsp 호출용
						request.setAttribute("SECURITY_TOKEN_TAG", this.createSecurityTokenTag(newToken));
						
						request.getSession().setAttribute(Constant.SECURITY_TOKEN_NAME, newToken);
					}
				}
			} catch (IOException e) {
				LOGGER.error("[IOException] Try/Catch... : "+ e.getMessage());
			} finally {
				request.removeAttribute(ONCE_PER_REQUEST_ATTRIBUTE_NAME);
			}
		}
		//========== 끝 : 보안토큰처리 csrf_token ==========
		
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		
		String requestUri = AsaproUtils.getFixedRequestUri(request);
		
		Site currentSite = (Site)request.getAttribute("currentSite");
		
		//관리자쪽 보안토큰 적용 재외
		if( AsaproUtils.isAdminPath(requestUri) ){
//			if(!requestUri.matches("/[a-z0-9]+/asacms/banner/.*") ){
				
				return;
//			}
		}
		
		/*
		 * 임시처리이므로 차후 처리 필요
		 */
		//사용자 페이지 ajax 호출시 패스 - 통계때문에 일단 패스로 설정
		if( requestUri.contains("/statistics" + Constant.API_PATH + "/") ){
			return;
		}
		//추천시 패스 - 일단 패스로 설정
		if( requestUri.contains(Constant.API_PATH + "/recommend") ){
			return;
		}
		
		// 보안토큰 적용안할 uri 는 패스1
		if ("/".equals(requestUri) || Constant.APP_PATH.equals(requestUri)) {
			return;
		}

		// 보안토큰 적용안할 uri 는 패스2
		for (String curStart : Constant.SECURITY_EXCLUDE_STARTWITH_URIS) {
			if (requestUri.startsWith(curStart)) {
				return;
			}
		}
		
		// 보안토큰 적용안할 uri 는 패스3
		for (String curEnd : Constant.SECURITY_EXCLUDE_ENDWITH_URIS) {
			if (requestUri.endsWith(curEnd)) {
				return;
			}
		}
		
		// 보안토큰 적용안할 uri 는 패스4
		for (String curContain : Constant.SECURITY_EXCLUDE_CONTAINWITH_URIS) {
			if (requestUri.contains(curContain) ) {
				return;
			}
		}
		
		//========== 시작 : 보안토큰처리 csrf_token ==========
		if( "POST".equalsIgnoreCase(request.getMethod()) && modelAndView != null ){
			//postHandle 에서 modelAndView를 검사 => BindingResult가 있을 경우 error 체크 => 에러 없으면 폼이 정상 등록된 것으로 판단하고 nonce를 디비에 넣는다.
			Set<Entry<String, Object>> set = modelAndView.getModel().entrySet();
			boolean isBindingResultClean = true;
			for( Entry<String, Object> e : set ){
				if( e.getKey().startsWith(BindingResult.MODEL_KEY_PREFIX) ){
					if( e.getValue() != null ){
						BindingResult br = (BindingResult) e.getValue();
						if( br.hasErrors() ){
							isBindingResultClean = false;
						}
					}
				}
			}
			//폼처리하다가 에러없이 처리되었을 경우에만 nonce를 디비에 집어 넣음
			if( isBindingResultClean ){
				// 폼용 보안토큰 세션에 있는거 확인 후 생성
				//세션에 있는 폼토큰 제거 - 폼 post 성공시 지운다. - 여기서 지우면 get 요청시 신규로 생성함
				request.getSession().removeAttribute(Constant.SECURITY_TOKEN_NAME);
				
				UserMember currentUser = (UserMember) request.getSession().getAttribute("currentUser");
				//AdminMember currentAdmin = (AdminMember) request.getSession().getAttribute("currentAdmin");
				
				long currentTimeMillis = System.currentTimeMillis();
				String currentMillis = String.valueOf(currentTimeMillis);
				
				String token = null;
				String newToken = null;
				token = this.createSecurityToken(currentUser.getUserId(), currentMillis, "form");
				newToken = currentMillis + "_" + token;
				LOGGER.info("[ASAPRO] SecurityInterceptor NEW FORM SECURITY TOKEN WAS CREATED : {} ", newToken);
				
				// 토큰 값 jsp 호출용
				request.setAttribute("SECURITY_TOKEN", newToken);
				// 토근이름 세팅 jsp 단에서 호출
				request.setAttribute("SECURITY_TOKEN_NAME", Constant.SECURITY_TOKEN_NAME);
				// 쿼리스트링 형태 jsp 호출용
				request.setAttribute("SECURITY_TOKEN_QS", this.createSecurityTokenQueryString(newToken));
				// hidden input jsp 호출용
				request.setAttribute("SECURITY_TOKEN_TAG", this.createSecurityTokenTag(newToken));
				
				request.getSession().setAttribute(Constant.SECURITY_TOKEN_NAME, newToken);
				
				
				/**
				 * 컨트롤러에서 forward 되면 토큰을 form에서 전달받지 못하고 전 화면의 폼에서 전달받은 토큰을 리퀘스트에 담고 있기고 파라미터로는 담을 수 없기때문에 여기서 새로 attribute에 주입해서 전달한다.
				 */
				if( modelAndView.getViewName().startsWith("forward:") ){
					request.setAttribute(Constant.SECURITY_TOKEN_NAME, newToken);
				}
				
			}
			
			request.removeAttribute("recreatedToken");
			request.removeAttribute("recreatedMillis");
			
		}
		//========== 끝 : 보안토큰처리 csrf_token ==========
		
	}

	// generate token
	private String createSecurityToken(String memberId, String millis, String mode) {
		StringBuilder sb = new StringBuilder(100);
		
		if( "form".equals(mode) ){
			sb.append(memberId);
			sb.append(millis);
			sb.append(Constant.SECURITY_TOKEN_SALT);
			sb.append(mode);
		} else if( "async".equals(mode) ) {
			sb.append(memberId);
			sb.append(millis);
			sb.append(Constant.SECURITY_TOKEN_SALT);
			sb.append(mode);
		}

		return DigestUtils.sha256Hex(sb.toString().trim());
	}

	// 토큰 쿼리스트링 생성
	private String createSecurityTokenQueryString(String token) {
		StringBuilder sb = new StringBuilder(100);
		if (StringUtils.isNotBlank(token)) {
			sb.append(Constant.SECURITY_TOKEN_NAME);
			sb.append("=");
			sb.append(token);
		}
		return sb.toString();
	}

	// 토큰 hidden input tag 생성
	private String createSecurityTokenTag(String token) {
		StringBuilder sb = new StringBuilder(200);
		if (StringUtils.isNotBlank(token)) {
			sb.append("<input type=\"hidden\" name=\"");
			sb.append(Constant.SECURITY_TOKEN_NAME);
			sb.append("\" value=\"");
			sb.append(token);
			sb.append("\" />");
		}
		return sb.toString();
	}
}
