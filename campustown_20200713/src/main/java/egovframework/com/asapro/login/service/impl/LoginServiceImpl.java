/**
 * 
 */
package egovframework.com.asapro.login.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import egovframework.com.asapro.capability.service.Capability;
import egovframework.com.asapro.capability.service.CapabilitySearch;
import egovframework.com.asapro.capability.service.CapabilityService;
//import egovframework.com.asapro.config.service.Config;
//import egovframework.com.asapro.config.service.ConfigService;
import egovframework.com.asapro.login.service.Login;
import egovframework.com.asapro.login.service.LoginAdminService;
import egovframework.com.asapro.member.admin.service.AdminMember;
import egovframework.com.asapro.member.admin.service.AdminMemberMapper;
import egovframework.com.asapro.member.admin.service.AdminMemberService;
import egovframework.com.asapro.role.service.Role;
import egovframework.com.asapro.site.service.Site;
import egovframework.com.asapro.support.Constant;
import egovframework.com.asapro.support.exception.AsaproNoCapabilityException;
import egovframework.com.asapro.support.util.AsaproCookieSSO;
import egovframework.com.asapro.support.util.AsaproUtils;
import egovframework.com.asapro.support.util.EtcUtil;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import egovframework.rte.fdl.cryptography.EgovPasswordEncoder;

/**
 * 로그인 서비스 구현
 * @author yckim
 * @since 2018. 5. 23.
 *
 */
@Service
public class LoginServiceImpl extends EgovAbstractServiceImpl implements LoginAdminService{

	@Autowired
	private AdminMemberService adminMemberService;
	
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
	public AdminMember login(Login loginForm) {
		Site currentSite = (Site) request.getAttribute("currentSite");
		AdminMember adminMemberSearch = new AdminMember();
		adminMemberSearch.setSitePrefix(currentSite.getSitePrefix());
		adminMemberSearch.setAdminId(loginForm.getLoginId());
		AdminMember fromDB = adminMemberService.getAdminMember(adminMemberSearch);
		
		if(fromDB != null){
			
			//권한이없는 사이트의 로그인 화면에서 로그인 시도 했을경우
			if(fromDB.getAdminRole() == null || StringUtils.isBlank(fromDB.getAdminRole().getRoleCode()) ){
				if(fromDB.getAdminSiteRoleRelList() != null && !fromDB.getAdminSiteRoleRelList().isEmpty() ){
					adminMemberSearch.setSitePrefix(fromDB.getAdminSiteRoleRelList().get(0).getSitePrefix());
					fromDB = adminMemberService.getAdminMember(adminMemberSearch);
					if("ASA".equals(fromDB.getAdminSiteRoleRelList().get(0).getSitePrefix()) ){
						currentSite.setSiteId("main");
						currentSite.setSiteMain(true);
					}else{
						currentSite.setSiteId(fromDB.getAdminSiteRoleRelList().get(0).getSitePrefix().toLowerCase());
						currentSite.setSiteMain(false);
					}
				}
			}
			
			//사이트별 권한이 필수이나 만약 릴레이션에 등록된 사이트별 권한이 없는경우
			if(fromDB.getAdminRole() == null || StringUtils.isBlank(fromDB.getAdminRole().getRoleCode())){
				throw new AsaproNoCapabilityException("권한이 없습니다.","back");
			}
			
			if( egovPasswordEncoder.checkPassword(loginForm.getLoginPassword(), fromDB.getAdminPassword()) ){
				/*CapabilitySearch capabilitySearch = new CapabilitySearch();
				capabilitySearch.setPaging(false);
				capabilitySearch.setRoleCode(fromDB.getAdminRole().getRoleCode());
				List<Capability> capList = capabilityService.getRoleCapabilityList(capabilitySearch);*/
				if(fromDB.getAdminRoleList() != null && !fromDB.getAdminRoleList().isEmpty() ){
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
				
				return fromDB;
			}
		}
		
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
