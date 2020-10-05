/**
 * 
 */
package egovframework.com.asapro.login.web;

import java.io.IOException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import egovframework.com.asapro.config.service.Config;
import egovframework.com.asapro.config.service.ConfigService;
//import egovframework.com.asapro.config.service.Config;
//import egovframework.com.asapro.config.service.ConfigService;
import egovframework.com.asapro.login.service.Login;
import egovframework.com.asapro.login.service.LoginAdminService;
import egovframework.com.asapro.member.admin.service.AdminMember;
import egovframework.com.asapro.member.admin.service.AdminMemberService;
import egovframework.com.asapro.site.service.Site;
import egovframework.com.asapro.statistics.service.StatisticsAdminAccessLog;
import egovframework.com.asapro.statistics.service.StatisticsService;
import egovframework.com.asapro.support.Constant;
import egovframework.com.asapro.support.ServerMessage;
import egovframework.com.asapro.support.annotation.CurrentSite;
import egovframework.com.asapro.support.util.AsaproCookieSSO;
import egovframework.com.asapro.support.util.AsaproUtils;
import egovframework.com.asapro.support.util.EtcUtil;
import egovframework.com.utl.slm.EgovHttpSessionBindingListener;
import egovframework.com.utl.slm.EgovMultiLoginPreventor;
import egovframework.com.asapro.login.web.LoginValidator;

/**
 * 관리자 로그인 컨트롤러
 * @author yckim
 * @since 2018. 5. 23.
 *
 */
@Controller
public class LoginAdminController {
	
	@Autowired
	private HttpServletRequest request;
	
	@Autowired
	private LoginAdminService loginAdminService;
	
	@Autowired
	private AdminMemberService adminMemberService;
	
	@Autowired
	private LoginValidator loginValidator;
	
	@Autowired
	private ConfigService configService;
	
	@Autowired
	private StatisticsService statisticsService;
	
	/**
	 * 관리자 로그인 폼을 반환한다.
	 * @param model
	 * @param loginForm
	 * @return 
	 */
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/login.do", method=RequestMethod.GET)
	public String loginFormGet(Model model, @ModelAttribute(value="loginForm") Login loginForm){
		return "asapro/admin/login/login";
	}

	/**
	 * 관리자 로그인 처리
	 * @param model
	 * @param loginForm
	 * @param bindingResult
	 * @return 처리 결과
	 */
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/login.do", method=RequestMethod.POST)
	public String loginFormPost(Model model, RedirectAttributes redirectAttributes, HttpServletResponse response, @CurrentSite Site currentSite, @ModelAttribute(value="loginForm") Login loginForm, BindingResult bindingResult){
		loginValidator.validate(loginForm, bindingResult, "ADMIN");//관리자 로그인 validate
		if( bindingResult.hasErrors() ){
			return "asapro/admin/login/login";
		} else {
			//기존 세션을 우선 무효화한다.
			request.getSession(false).invalidate();
			
			//설정에서 제한방법 체크
			Config securityConfig = configService.getConfig("security");
			//로그인 실패 잠김 유형 - db값이 없거나 불러오지 못하는 경우 사이트가 뜨지 못하므로 영구제한을 디폴트로 설정
			String loginFailRestrictionType = StringUtils.defaultString(securityConfig.getOption("login_fail_restriction_type"), "fixed");	
			int loginFailLimitCount = Integer.parseInt(StringUtils.defaultString(securityConfig.getOption("login_fail_limit_count"),"5") );	//로그인 실패 재한 횟수 - 디폴트 5회
			int loginFailTermTime = Integer.parseInt(StringUtils.defaultString(securityConfig.getOption("login_fail_term_time"),"1") );	//로그인 잠김(lock) 해제 시간 텀(분) - 디폴트 1분
			
			//아이디만 존재 여부 확인
			AdminMember adminMemberSearch = new AdminMember();
			adminMemberSearch.setSitePrefix(currentSite.getSitePrefix());
			adminMemberSearch.setAdminId(loginForm.getLoginId());
			AdminMember idChk = adminMemberService.getAdminMember(adminMemberSearch);
			
			//로그인 처리
			AdminMember adminMember = loginAdminService.login(loginForm);
			
			//아이디가 존재하면
			if( idChk != null ){
				
				//입력받은 아이디로 lock 여부 체크
				boolean isLock = adminMemberService.isLock(loginForm.getLoginId());
				
				//계정이 잠김 상태이면
				if(isLock ){
					
					//일시적 제한
					if("temporary".equals(loginFailRestrictionType) ){	
						//로그인 마지막 실패 일시 조회
						Date failDate = adminMemberService.getLoginFailDate(loginForm.getLoginId());
						if(failDate == null){
							failDate = AsaproUtils.getParsedDate("2000-01-01 00:00:00");
						}
						Date addTimeDate = AsaproUtils.getAddTimeDate(failDate, 0, loginFailTermTime, 0);
						
						//잠김처리된 시간 이후 설정의 해제시간을 지났는 체크
						//아직 해지 시간이 지나지 않았으면 
						if(addTimeDate.compareTo(new Date()) >= 0 ){
							String failText = "비밀번호 입력 실패 횟수가 [" + loginFailLimitCount + "회] 초과하였거나 <br/>비정상적인 접속 시도로 고객님의 계정을 안전하게 보호하기 위해, <br/>일시적으로 잠김처리 되었습니다. <br/>" + loginFailTermTime + "분 후 다시 시도하세요." ;
							bindingResult.addError(new FieldError("loginForm", "loginPassword", failText));
							return "asapro/admin/login/login";
						}
						
						//지났으면 로그인 시도
						//로그인 실패 관련 데이터 리셋
						AdminMember adminMemberFail = new AdminMember();
						adminMemberFail.setAdminId(loginForm.getLoginId());
						adminMemberService.resetLoginFailInfo(adminMemberFail);
					}
					//영구제한
					else if("fixed".equals(loginFailRestrictionType) ){	
						//일단 필드에러로 로그인 실패 원인 전달 - 임시
						String failText = "비밀번호 입력 실패 횟수가 [" + loginFailLimitCount + "회] 초과하였거나 <br/>비정상적인 접속 시도로 고객님의 계정을 안전하게 보호하기 위해, <br/>잠김처리 되었습니다. <br/>최고관리자에게 문의하세요.";
						bindingResult.addError(new FieldError("loginForm", "loginPassword", failText));
						return "asapro/admin/login/login";
					}else{
						
					}
					
				}
				
				//잠김상태 아닐경우
				//로그인 인증되면
				if( adminMember != null && StringUtils.isNotBlank(adminMember.getAdminId()) ){
					
					//계정이 상태가 비활성인지 체크
					if(!adminMember.getAdminActive()){
						String failText = "사용이 중지된 계정입니다. <br/>관리자에게 문의하세요.";
						bindingResult.addError(new FieldError("loginForm", "loginPassword", failText));
						return "asapro/admin/login/login";
					}
					
					
					
//				Config globalConfig = configService.getConfig("global");
//				if( "true".equals(globalConfig.getOption("use_cookie_sso")) ){
					//사이트 SSO를 쿠키로 처리한다. 베이스 도메인이 같다고 전제함
//					String cookieDomain = configService.getConfig("global").getOption("cookie_domain"); //Returns .xyz.com
//					AsaproCookieSSO.setSSOCookie(request, response, "asapro_", adminMember.getAdminId(), System.currentTimeMillis(), -1, cookieDomain);
//				}
					
					//@@@ 주의 => 비회원인 상태라도 currentAdmin가 없는건 아니다. 
					//로그인한 유저는 sessionScope.loginAdminId가 null이 아니다. 
					//비회원은 sessionScope.loginAdminId가 null임
					request.getSession().setAttribute("loginAdminId", adminMember.getAdminId());
					request.getSession().setAttribute("currentAdmin", adminMember);
					
					//사이트별 권한 체크하기 위해 로그인한 사이트 프리픽스 세션에 담아두고 사이트 변경시 비교 하여 권한 체크한다.
					request.getSession().setAttribute("authSite", currentSite);
					
					/**
					 * 중복 로그인 방지 기능
					 * 사용자 로그인 ID를 키 값으로 현재 세션에 EgovHttpSessionBindingListener 객체를 바인딩한다.
					 */
					//중복로그인인 경우 알림창을 띄우기 위해 체킹값 전달
					boolean isOverlap = false;
					
					//설정에 중복로그인 허용이 false 인 경우만 실행
					//Config securityConfig = (Config)request.getAttribute("securityConfig");
					if("false".equals(securityConfig.getOption("overlap")) ){
						isOverlap = EgovMultiLoginPreventor.findByLoginId(adminMember.getAdminId());
						EgovHttpSessionBindingListener listener = new EgovHttpSessionBindingListener();
						request.getSession().setAttribute(adminMember.getAdminId(), listener);
					}
					
					//reset security token
//				AsaproUtils.deleteCookie(request, response, Constant.SECURITY_ASYNC_COOKIE_NAME);
					
					//마지막 로그인 일시 업데이트
					adminMemberService.updateAdminMemberLastLoginDate(adminMember);
					
					//로그인 실패 관련 데이터 리셋
					adminMemberService.resetLoginFailInfo(adminMember);
					
					//============================================================================
					/**
					 * 관리자 접속 로그
					 */
					StatisticsAdminAccessLog statisticsAdminAccessLog = new StatisticsAdminAccessLog(request, currentSite, adminMember);
					statisticsService.insertStatisticsAdminAccessLog(statisticsAdminAccessLog);
					//=============================================================================
					
					redirectAttributes.addFlashAttribute("isOverlap", isOverlap);
					
					//비밀번호 사용기간 사용인경우  
					String isPwdPeriodChange = securityConfig.getOption("pwd_periodic_change");
					int pwdChangeDays = Integer.parseInt(securityConfig.getOption("pwd_change_days"));
					if("true".equals(isPwdPeriodChange) ){
						//비밀번호 마지막 수정일자 확인
						Date passwordLastUpdateDate = adminMember.getAdminPWLastUpdated();
						if(AsaproUtils.makeCalculDate(passwordLastUpdateDate, pwdChangeDays).compareTo(new Date()) < 0 ){
							//로그인시 비밀번호 사용기간을 체크 후 변경유도 화면인지를 구분하기위해 
							redirectAttributes.addFlashAttribute("chkPwdPeriod", true);
							return "redirect:" + AsaproUtils.getAdminPath(currentSite, true) + "/member/admin/profile/password/update.do";
						}
					}
					
					return "redirect:" + AsaproUtils.getAdminPath(currentSite, true) + "/dashboard.do";
				} 
				
				//로그인 실패시
				else {
					
					//로그인 실패 횟수 증가
					adminMemberService.addLoginFailCount(loginForm.getLoginId());
					//로그인 실패 회수 조회
					int failCount = adminMemberService.getLoginFailCount(loginForm.getLoginId());
					
					//실패횟수가 제한횟수보다 크거나 같으면
					if(failCount >= loginFailLimitCount ){
						//계정 잠김 처리, 잠김 일시 등록
						adminMemberService.updateLockAndFailDate(loginForm.getLoginId());
					}
					
					//로그인 실패시 관리자 기능 이력관리에서 로깅하지 않도록 하기위해서 임의로 필드에러 추가
					String failText = "아이디 또는 비밀번호가 일치하지 않습니다.	(" + failCount + "/" + loginFailLimitCount + ") <br/>" + loginFailLimitCount + "회 이상 로그인 오류시 계정이 잠김처리 됩니다.";
					bindingResult.addError(new FieldError("loginForm", "loginPassword", failText));
				}
				
			} else {
				
				//아이디, 비밀번호 모두 없는 경우
				bindingResult.addError(new FieldError("loginForm", "loginPassword", "아이디 또는 비밀번호가 일치하지 않습니다."));
			}

			return "asapro/admin/login/login";
		}
	}
	
	/**
	 * 관리자 로그아웃 처리
	 * <br />
	 * 세션 폐기
	 * @param session
	 * @return 메인화면
	 * @throws IOException 
	 */
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/logout.do")
	@ResponseBody
	public ServerMessage logout(HttpSession session, HttpServletResponse response) throws IOException{
		
		/**
		 * 중복 로그인 방지 기능
		 * 사용자가 로그아웃 버튼을 눌렀을 때 명시적으로 세션을 무효화하도록 HttpSession의 invalidate() 메소드를 호출한다. 
		 */
		loginAdminService.logout(request, response);
		
		ServerMessage serverMessage = new ServerMessage(true, "정상적으로 로그아웃되었습니다.");
		
		return serverMessage;
	}
	
}
