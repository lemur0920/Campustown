/**
 * 
 */
package egovframework.com.asapro.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import egovframework.com.asapro.config.service.ConfigService;
import egovframework.com.asapro.support.util.AsaproUtils;

/**
 * SSL 처리 인터셉터
 * <pre>
 * 1. securityConfig 의 ssl 설정값을 체크해서 요청 url이 ssl 패턴일 경우 리다이렉트 시킨다. 
 * </pre>
 * @author yckim
 *
 */
public class SSLInterceptor extends HandlerInterceptorAdapter{

	private static final Logger LOGGER = LoggerFactory.getLogger(SSLInterceptor.class);
	
	@Autowired
	private ConfigService configService;
	
	/* (non-Javadoc)
	 * @see org.springframework.web.servlet.handler.HandlerInterceptorAdapter#preHandle(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object)
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

		String requestUri = AsaproUtils.getFixedRequestUri(request);
		
		//로컬/개발서버 패스
		if( AsaproUtils.isDevPath(request.getServerName()) || AsaproUtils.isAsadalNet(request) ){
			return true;
		}
				
		//========== 시작 : SSL 리다이렉트 설정 확인 및 적용 ==========
		/*
		 * !! 일반적으로 configService 에서 getConfig로 설정값을 가져올때는 캐싱된 값을 가져오는데
		 * security 관련 설정은 수정시 사이트 접속이 불가한 경우가 생길 수 있으므로
		 * 그럴 경우 디비에서 직접 고쳐도 적용되도록 하기위해 getNoCacheOption 메서드를 이용해서 가져오도록 한다.
		 * */
		// SSL on
		if( "true".equalsIgnoreCase(configService.getNoCacheOption("security", "ssl").getOptValue()) ){
			LOGGER.info("[ASAPRO] SSLInterceptor SSL true in ASAPRO_CONFIG_OPTION table.");
			
			String currentScheme = request.getScheme();
			
			//현재 https 이면
			if( "https".equalsIgnoreCase(currentScheme) ){
				
			}
			//현재 http 이면
			else {
				LOGGER.info("[ASAPRO] SSLInterceptor SSL is applying to all urls. redirect to HTTPS");
				response.sendRedirect(AsaproUtils.getSchemeDomainPort(request, "https", "443") + AsaproUtils.getRequestUriWithParameters(request));
				return false;
			}
			
		}
		//SSL off
		else {
			LOGGER.info("[ASAPRO] SSLInterceptor SSL false in ASAPRO_CONFIG_OPTION table.");
			//http로 또는 https로 들어오거나 말거나 신경 안씀 
			//do nothing...
		}
		//========== 끝 : SSL 리다이렉트 설정 확인 및 적용 ==========
		
		return super.preHandle(request, response, handler);
	}
	
}
