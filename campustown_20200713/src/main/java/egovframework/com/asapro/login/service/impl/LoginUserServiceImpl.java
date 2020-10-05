/**
 * 
 */
package egovframework.com.asapro.login.service.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import egovframework.com.asapro.capability.service.Capability;
import egovframework.com.asapro.capability.service.CapabilitySearch;
import egovframework.com.asapro.capability.service.CapabilityService;
//import egovframework.com.asapro.config.service.Config;
//import egovframework.com.asapro.config.service.ConfigService;
import egovframework.com.asapro.login.service.Login;
import egovframework.com.asapro.login.service.LoginUserService;
import egovframework.com.asapro.member.user.service.UserMember;
import egovframework.com.asapro.member.user.service.UserMemberService;
import egovframework.com.asapro.site.service.Site;
import egovframework.com.asapro.support.Constant;
import egovframework.com.asapro.support.exception.AsaproNoCapabilityException;
import egovframework.com.asapro.support.util.AsaproCookieSSO;
import egovframework.com.asapro.support.util.AsaproUtils;
import egovframework.com.asapro.support.util.EtcUtil;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import egovframework.rte.fdl.cryptography.EgovPasswordEncoder;

/**
 * 사용자 로그인 서비스 구현
 * @author yckim
 * @since 2020. 2. 28.
 *
 */
@Service
public class LoginUserServiceImpl extends EgovAbstractServiceImpl implements LoginUserService{

	@Autowired
	private UserMemberService userMemberService;
	
	@Autowired
	private CapabilityService capabilityService;
	
//	@Autowired
//	private ConfigService configService;
	
	@Autowired
	private EgovPasswordEncoder egovPasswordEncoder;
	
	@Autowired
	private HttpServletRequest request;
	
	/**
	 * 로그인 처리
	 */
	@Override
	public UserMember userLogin(Login loginForm) {
		Site currentSite = (Site) request.getAttribute("currentSite");
		UserMember userMemberSearch = new UserMember();
		userMemberSearch.setSitePrefix(currentSite.getSitePrefix());
		userMemberSearch.setUserId(loginForm.getLoginId());
/*		UserMember fromDB = userMemberService.getUserMember(userMemberSearch);
		
		if(fromDB != null){
			
			//권한이없는 사이트의 로그인 화면에서 로그인 시도 했을경우
			if(fromDB.getUserRole() == null || StringUtils.isBlank(fromDB.getUserRole().getRoleCode()) ){
				if(fromDB.getUserSiteRoleRelList() != null && !fromDB.getUserSiteRoleRelList().isEmpty() ){
					userMemberSearch.setSitePrefix(fromDB.getUserSiteRoleRelList().get(0).getSitePrefix());
					fromDB = userMemberService.getUserMember(userMemberSearch);
					if("ASA".equals(fromDB.getUserSiteRoleRelList().get(0).getSitePrefix()) ){
						currentSite.setSiteId("main");
						currentSite.setSiteMain(true);
					}else{
						currentSite.setSiteId(fromDB.getUserSiteRoleRelList().get(0).getSitePrefix().toLowerCase());
						currentSite.setSiteMain(false);
					}
				}
			}
			
			//사이트별 권한이 필수이나 만약 릴레이션에 등록된 사이트별 권한이 없는경우
			if(fromDB.getUserRole() == null || StringUtils.isBlank(fromDB.getUserRole().getRoleCode())){
				throw new AsaproNoCapabilityException("권한이 없습니다.","back");
			}
			
			if( egovPasswordEncoder.checkPassword(loginForm.getLoginPassword(), fromDB.getUserPassword()) ){
				CapabilitySearch capabilitySearch = new CapabilitySearch();
				capabilitySearch.setPaging(false);
				capabilitySearch.setRoleCode(fromDB.getUserRole().getRoleCode());
				List<Capability> capList = capabilityService.getRoleCapabilityList(capabilitySearch);
				fromDB.getUserRole().setCapabilityList(capList);
				return fromDB;
			}
		}
*/		
		return null;
	}

	/**
	 * 로그아웃
	 */
	@Override
	public void logout(HttpServletRequest request, HttpServletResponse response) {
		request.getSession().invalidate();
		
//		Config globalConfig = configService.getConfig("global");
//		if( "true".equals(globalConfig.getOption("use_cookie_sso")) ){
//			AsaproCookieSSO.deleteSSOCookie(response, "asapro_", configService.getConfig("global").getOption("cookie_domain"), "/");
//		}
		//reset security token
//		AsaproUtils.deleteCookie(request, response, Constant.SECURITY_ASYNC_COOKIE_NAME);
	}


}
