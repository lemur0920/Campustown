/**
 * 
 */
package egovframework.com.asapro.login.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import egovframework.com.asapro.member.user.service.UserMember;

/**
 * 사용자 로그인 서비스
 * @author yckim
 * @since 2020. 2. 28.
 *
 */
public interface LoginUserService {
	
	/**
	 * 로그인을 시도한다.
	 * @param loginForm
	 * @return 로그인 결과
	 */
	public UserMember userLogin(Login loginForm);
	
	/**
	 * 로그아웃시킨다.
	 * @param request
	 */
	public void logout(HttpServletRequest request, HttpServletResponse response);

}
