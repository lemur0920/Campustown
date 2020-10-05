/**
 * 
 */
package egovframework.com.asapro.support.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.NoSuchAlgorithmException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 쿠키 SSO 유틸
 * @author yckim
 */
public class AsaproCookieSSO {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AsaproCookieSSO.class); 
	
	private static final int LIFE_LIMIT_SECONDS = 60 * 60 * 12;// 12hours

	/**
	 * 인증쿠키를 세팅한다.
	 * 
	 * <pre>
	 * life 가 -1 이면 세션동안만 생존한다.
	 * </pre>
	 * 
	 * @param response
	 * @param userId
	 * @param currentTimeMillis
	 * @param cookieDomain
	 * @throws NoSuchAlgorithmException
	 */
	public static void setSSOCookie(HttpServletRequest request, HttpServletResponse response, String prefix, String userId, long currentTimeMillis, int life, String cookieDomain, String path) {
		int ctm = (int) (currentTimeMillis / 1000);
		try {
			String ssoCookieKey = prefix + DigestUtils.sha256Hex(cookieDomain);
			int expiry;
			// life 가 -1이면 세션동안만 살아있는 쿠키
			if (life == -1) {
				expiry = -1;
			} else {
				expiry = ctm + life;
			}
			//변조 방지
			String hash = DigestUtils.sha256Hex(userId + (ctm + "") + expiry + DigestUtils.sha256Hex(userId + (ctm + "") + expiry) + AsaproUtils.getRempoteIp(request));
			String ssoCookieValue = userId + "|" + (ctm + "") + "|" + expiry + "|" + hash;
			ssoCookieValue = URLEncoder.encode(ssoCookieValue, "UTF-8");
			AsaproCookieSSO.setCookie(response, ssoCookieKey, ssoCookieValue, life, cookieDomain, path);
		} catch (UnsupportedEncodingException e) {
			LOGGER.error("[UnsupportedEncodingException] Try/Catch... : "+ e.getMessage());
		}
	}

	/**
	 * set sso cookie domain = "/"
	 * 
	 * @param response
	 * @param prefix
	 * @param userId
	 * @param currentTimeMillis
	 * @param life
	 * @param cookieDomainh
	 */
	public static void setSSOCookie(HttpServletRequest request, HttpServletResponse response, String prefix, String userId, long currentTimeMillis, int life, String cookieDomain) {
		AsaproCookieSSO.setSSOCookie(request, response, prefix, userId, currentTimeMillis, life, cookieDomain, "/");
	}

	/**
	 * 인증 쿠키 정보
	 * 
	 * <pre>
	 * userId, expiry
	 * </pre>
	 * 
	 * @param cookie
	 * @return
	 */
	public static String getUserId(HttpServletRequest request, HttpServletResponse response, Cookie cookie, String cookieDomain, String path) {
		if (cookie == null) {
			LOGGER.info("[ASAPRO] AsaproCookieSSO cookie is null.");
			return null;
		}
		String cv = null;
		try {
			if(StringUtils.isNotBlank(cookie.getValue())){
				cv = URLDecoder.decode(cookie.getValue(), "UTF-8");
			}
		} catch (UnsupportedEncodingException e1) {
			LOGGER.error("[UnsupportedEncodingException] Try/Catch... : "+ e1.getMessage());
		}
		String[] temp = cv.split("\\|");
		if (temp.length != 4) {
			AsaproCookieSSO.deleteCookie(response, cookie, cookieDomain, path);
			LOGGER.info("[ASAPRO] AsaproCookieSSO ##### SSO Cookie is modified. Array length does not matched.");
			return null;
		} else {
			// 쿠키추출
			String userId = temp[0];// 사용자 아이디
			String created = temp[1];
			String expiry = temp[2];// 만료일자
			String hmac = temp[3];// hash message authentication code

			String hash = null;
			hash = DigestUtils.sha256Hex(userId + created + expiry + DigestUtils.sha256Hex(userId + created + expiry) + AsaproUtils.getRempoteIp(request));
			// 쿠키의 해시값과 서버에서 생성한 해시값이 일치 하지 않으면 오염된 쿠키라고 판단
			if (!hmac.equals(hash)) {
				AsaproCookieSSO.deleteCookie(response, cookie, cookieDomain, path);
				LOGGER.info("[ASAPRO] AsaproCookieSSO ##### SSO Cookie is modified. Hash value does not matched.");
				return null;
			} else {
				int now = (int) (System.currentTimeMillis() / 1000);
				//System.out.println("AsaproCookieSSO : System.currentTimeMillis() : " + System.currentTimeMillis());
				//System.out.println("AsaproCookieSSO : now     : " + now);
				//System.out.println("AsaproCookieSSO : created : " + created);
				//System.out.println("AsaproCookieSSO : Integer.parseInt(created) + LIFE_LIMIT_SECONDS : " + (Integer.parseInt(created) + LIFE_LIMIT_SECONDS));
				//System.out.println("AsaproCookieSSO : " + (now > Integer.parseInt(created) + LIFE_LIMIT_SECONDS));
				if ("-1".equals(expiry)) {
					// LIFE_LIMIT_MILLISECONDS 지났으면 만료처리
					long sum = Long.parseLong(created) + (long)LIFE_LIMIT_SECONDS;
					if (now > sum) {
						AsaproCookieSSO.deleteCookie(response, cookie, cookieDomain, path);
						LOGGER.info("[ASAPRO] AsaproCookieSSO ##### SSO Cookie is expired. Cookie is older than LIFE_LIMIT_MILLISECONDS[{}].", LIFE_LIMIT_SECONDS);
						return null;
					}
				} else {
					// 만료됨
					if (Integer.parseInt(expiry) < now) {
						AsaproCookieSSO.deleteCookie(response, cookie, cookieDomain, path);
						LOGGER.info("[ASAPRO] AsaproCookieSSO ##### SSO Cookie is expired. Expiry passed.");
						return null;
					}
				}
				return userId;
			}
		}
	}

	/**
	 * 인증 쿠키 정보
	 * 
	 * <pre>
	 * userId, expiry
	 * </pre>
	 * 
	 * @param cookie
	 * @return
	 */
	public static String getUserId(HttpServletRequest request, HttpServletResponse response, String prefix, String cookieDomain) {
		String ssoCookieKey = prefix + DigestUtils.sha256Hex(cookieDomain);
		return getUserId(request, response, AsaproCookieSSO.getCookie(request, ssoCookieKey), cookieDomain, "/");
	}

	/*
	 * Get cookie
	 * 
	 * @param request
	 * @param key
	 * @return
	 */
	private static Cookie getCookie(HttpServletRequest request, String key) {
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (key.equals(cookie.getName())) {
					return cookie;
				}
			}
		}
		return null;
	}
	
	/**
	 * SSO 쿠키를 조회한다.
	 * @param request
	 * @param prefix
	 * @param cookieDomain
	 * @return SSO 쿠키
	 */
	public static Cookie getSSOCookie(HttpServletRequest request, String prefix, String cookieDomain) {
		String ssoCookieKey = prefix + DigestUtils.sha256Hex(cookieDomain);
		return getCookie(request, ssoCookieKey);
	}

	/*
	 * Set cookie
	 * 
	 * @param response
	 * @param key
	 * @param value
	 * @param expiry
	 * @param domain
	 * @param path
	 */
	private static void setCookie(HttpServletResponse response, String key, String value, int expiry, String domain, String path) {
		Cookie cookie = new Cookie(key, value);
		if(expiry > 86400) {
			expiry = 86400;//하루
		}
		cookie.setMaxAge(expiry);
		cookie.setDomain(domain);
		cookie.setPath(path);
		cookie.setHttpOnly(true);
		response.addCookie(cookie);
	}

	/*
	 * Delete cookie.
	 * 
	 * @param response
	 * @param cookie
	 * @param domain
	 * @param path
	 */
	private static void deleteCookie(HttpServletResponse response, Cookie cookie, String domain, String path) {
		if (cookie != null) {
			cookie.setDomain(domain);
			cookie.setPath(path);
			cookie.setMaxAge(0);
			cookie.setHttpOnly(true);
			response.addCookie(cookie);
		}
	}
	
	/**
	 * Delete cookie.
	 * 
	 * @param response
	 * @param cookie
	 * @param domain
	 * @param path
	 */
	public static void deleteSSOCookie(HttpServletResponse response, String prefix, String cookieDomain, String path) {
		String ssoCookieKey = prefix + DigestUtils.sha256Hex(cookieDomain);
		Cookie cookie = new Cookie(ssoCookieKey, "");
		if (cookie != null) {
			cookie.setDomain(cookieDomain);
			cookie.setPath(path);
			cookie.setMaxAge(0);
			cookie.setHttpOnly(true);
			response.addCookie(cookie);
		}
	}

}
