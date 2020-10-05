/**
 * 
 */
package egovframework.com.asapro.allowip.web;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.jcs.utils.net.HostNameUtil;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.utils.URLEncodedUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.FlashMap;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.ibm.icu.text.SimpleDateFormat;

import egovframework.com.asapro.allowip.service.AllowIpService;
import egovframework.com.asapro.allowip.service.AllowIpTemp;
import egovframework.com.asapro.allowip.service.AllowIpTempSearch;
import egovframework.com.asapro.config.service.ConfigService;
import egovframework.com.asapro.login.service.Login;
import egovframework.com.asapro.member.admin.service.AdminMember;
import egovframework.com.asapro.member.admin.service.AdminMemberService;
import egovframework.com.asapro.site.service.Site;
import egovframework.com.asapro.support.Constant;
import egovframework.com.asapro.support.ServerMessage;
import egovframework.com.asapro.support.annotation.CurrentSite;
//import egovframework.com.asapro.support.connector.SmsSend;
import egovframework.com.asapro.support.exception.AsaproException;
import egovframework.com.asapro.support.pagination.Paging;
import egovframework.com.asapro.support.util.AsaproUtils;
import egovframework.com.asapro.support.util.EtcUtil;
import egovframework.rte.fdl.cryptography.EgovPasswordEncoder;

/**
 * 임시아이피 허용 및 인증 컨트롤러
 * @author yckim
 * @since 2019. 5. 21.
 *
 */
@Controller
public class AllowIpTempController {
	private static final Logger LOGGER = LoggerFactory.getLogger(AllowIpTempController.class);
	
	@Autowired
	private AllowIpService allowIpService;
	
	@Autowired
	private AllowIpTempValidator allowIpTempValidator;
	
	@Autowired
	private AdminMemberService adminMemberService;
	
	@Autowired
	private EgovPasswordEncoder egovPasswordEncoder;
	
	@Autowired
	private HttpServletRequest request;
	
	@Autowired
	private ConfigService configService;
	
	/**
	 * 관리자 인증요청 폼 뷰를 반환한다.
	 * @param model
	 * @param currentSite
	 * @param allowIpTempForm
	 * @return 인증요청 폼 뷰
	 */
	@RequestMapping(value=Constant.APP_PATH + Constant.SITE_ID_PATH + "/certificate/request", method=RequestMethod.GET)
	public String certificateRequestGet(Model model, @CurrentSite Site currentSite, @ModelAttribute("allowIpTempForm") AllowIpTemp allowIpTempForm){
		//아이피 체크 기능 사용하고 있지 않으면 관리자 로그인 화면 오픈
		String ipCheckUse = configService.getNoCacheGlobalOption("security", "allow_ip").getOptValue();//아이피 체크 기능 사용여부
		if("false".equals(ipCheckUse) ){
			return "redirect:" + AsaproUtils.getAdminPath(currentSite, true) + "/login.do";
		}
		
		//아이피 인증 처리완료 데이터 유무 확인 후 이미 인증완료이면 관리자 로그인 화면 리다이렉트
		Boolean isAuthentication = allowIpService.isAuthentication(AsaproUtils.getRempoteIp(request));
		if(isAuthentication != null && isAuthentication){
			return "redirect:" + AsaproUtils.getAdminPath(currentSite, true) + "/login.do";
		}
		
		return "asapro/certificate/requestForm";
	}

	/**
	 * 관리자 인증요청을 처리한다.
	 * @param model
	 * @param currentSite
	 * @param allowIpTempForm
	 * @return 처리결과
	 */
	@RequestMapping(value=Constant.APP_PATH + Constant.SITE_ID_PATH + "/certificate/request", method=RequestMethod.POST)
	public String certificateRequestPost(Model model, RedirectAttributes redirectAttributes, @CurrentSite Site currentSite, @ModelAttribute("allowIpTempForm") AllowIpTemp allowIpTempForm, BindingResult bindingResult){
		allowIpTempValidator.validate(allowIpTempForm, bindingResult, "REQUEST");//인증요청 validate
		if( bindingResult.hasErrors() ){
			return "asapro/certificate/requestForm";
		} else {
			//아이디 존재 여부 확인
			AdminMember adminMemberSearch = new AdminMember();
			adminMemberSearch.setSitePrefix(currentSite.getSitePrefix());
			adminMemberSearch.setAdminId(allowIpTempForm.getAdminId());
			AdminMember fromDB = adminMemberService.getAdminMember(adminMemberSearch);
			
			// 1.아이디가 존재하면
			if( fromDB != null ){
				// 2.비밀번호가 일치할경우
				if( egovPasswordEncoder.checkPassword(allowIpTempForm.getAdminPassword(), fromDB.getAdminPassword()) ){
					
					// 3.임시테이블에 이미 인증신청정보가 있으면
					AllowIpTemp allowIpTempDB = allowIpService.getAllowIpTemp(allowIpTempForm);
					if(allowIpTempDB != null ){
						//4. 현제 인증완료인 경우
						if(allowIpTempDB.isAuthentication() ){
							// 5.인증이 유효한 상태인경우
							//유효시간 계산을 따로 하지 않고 테이블의 정보를 매일 03시에 모두 삭제 처리하므로 03시 이후에는 다시 이증을 받아야 한다.
							//따라서 시간계산 프로세스는 생략 - 여기까지 들어왔으면 유효한 인증상태
							
							//인증완료 정보가 현제 접속한 아이피 이면 패스
							if(allowIpTempDB.getCertiNum().equals(AsaproUtils.getRempoteIp(request)) ){
								//관리자페이지 로그인 화면으로 전환
								Login loginForm = new Login();
								loginForm.setLoginId(allowIpTempForm.getAdminId());
								redirectAttributes.addFlashAttribute("loginForm", loginForm);
								return "redirect:" + AsaproUtils.getAdminPath(currentSite, true) + "/login.do";
							}else{
								//인증받은 아이피가 다른 아이피로 접근했으므로 기존데이터를 삭제한다
								allowIpService.deleteAllowIpTemp(allowIpTempForm);
							}
							
						} 
						//인증이 완료되지 않은상태인 경우
						else {
							//기존데이터를 삭제한다
							allowIpService.deleteAllowIpTemp(allowIpTempForm);
						}
					}
					
					if(StringUtils.isNotBlank(fromDB.getAdminMobileJoin()) ){
						String userIp = AsaproUtils.getRempoteIp(request);
						String userHp = fromDB.getAdminMobileJoin();
						userHp = userHp.replaceAll("-", "");
						//sms인증번호 생성
						String certiNum = RandomStringUtils.randomNumeric(6);
						
						allowIpTempForm.setTempIp(userIp);
						allowIpTempForm.setAdminHp(userHp);
						allowIpTempForm.setCertiNum(certiNum);
						allowIpTempForm.setTempRegdate(new Date());
						allowIpTempForm.setCertiRequestDate(new Date());
						
						//관리자 임시허용 아이피 테이블에 정보 저장
						allowIpService.insertAllowIpTemp(allowIpTempForm);
						
						//sms인증번호 발송
						String msg = "[서울시 청년포털]본인확인인증번호[" + certiNum + "]입니다.";

						/**
						 * 각 사이트의 문자발송 api를 사용하여 커스터마이징 하면 됨
						 */
						//=============   서울시 sms 전송 api  ==================================
						/*
						try {
							msg = URLEncoder.encode(msg, "EUC-KR");
						} catch (UnsupportedEncodingException e) {
							LOGGER.error("[" + e.getClass() +"] URLEncoder encode fail : " + e.getMessage());
						}*/
						//SmsSend sms = new SmsSend(userHp, msg, null);
						//String sender = "120";
						//long result = sms.send();
						//=============    서울시 sms 전송 api  ==================================
						
						//LOGGER.info("[sms 인증번호 발송]  발송결과 : {} " , result);
						
						//redirectAttributes.addFlashAttribute("adminId", allowIpTempForm.getAdminId());
						return "forward:" + AsaproUtils.getAppPath(currentSite) + "/certificate/certiNum";
					}else{
						//비밀번호가 등록되어있지 않을 경우
						bindingResult.addError(new FieldError("allowIpTempForm", "adminPassword", "휴대폰 번호가 등록되어 있지 않아 인증절차를 진행할 수 없습니다. 최고관리자에게 계정정보의 휴대폰번호 등록을 확인해주세요."));
					}
					
				}else{
					//비밀번호 일치하지 않을 경우
					bindingResult.addError(new FieldError("allowIpTempForm", "adminPassword", "아이디 또는 비밀번호가 일치하지 않습니다."));
				}
			} else {
				//아이디가 없는 경우
				bindingResult.addError(new FieldError("allowIpTempForm", "adminPassword", "아이디 또는 비밀번호가 일치하지 않습니다."));
			}
		}
		return "asapro/certificate/requestForm";
	}
	
	/**
	 * 휴대폰 인증번호 입력 폼 뷰를 반환한다.
	 * @param model
	 * @param currentSite
	 * @param allowIpTempForm
	 * @return 휴대폰인증번호 입력 폼 뷰
	 */
	@RequestMapping(value=Constant.APP_PATH + Constant.SITE_ID_PATH + "/certificate/certiNum", method={ RequestMethod.POST})
	public String certificateCertiNumGet(Model model, @CurrentSite Site currentSite, @ModelAttribute("allowIpTempForm") AllowIpTemp allowIpTempForm){
		
		
		return "asapro/certificate/certiNumForm";
	}
	
	/**
	 * 휴대폰 인증번호 일치여부를 처리한다..
	 * - resultCode 
	 * - 00 => 성공
	 * - 11 => 입력값 에러
	 * - 12 => 실패
	 * - 21 => 횟수 재한 초과
	 * - 41 => 임시허용 정보 검색 실패
	 * @param model
	 * @param currentSite
	 * @param allowIpTempForm
	 * @return 처리결과
	 */
	@RequestMapping(value=Constant.APP_PATH + Constant.SITE_ID_PATH + "/certificate" + Constant.API_PATH + " /certiNumSubmit", method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> certificateCertiNumPost(Model model, RedirectAttributes redirectAttributes, @CurrentSite Site currentSite, @ModelAttribute("allowIpTempForm") AllowIpTemp allowIpTempForm, BindingResult bindingResult){
		Map<String, Object> map = new HashMap<String, Object>();
		ServerMessage serverMessage = new ServerMessage();
		allowIpTempValidator.validate(allowIpTempForm, bindingResult, "CERIT_NUM");//SMS 인증번호 인증 validate
		if( bindingResult.hasErrors() ){
			serverMessage.setSuccess(false);
			serverMessage.setText(bindingResult.getFieldError("certiNum").getDefaultMessage());
			serverMessage.setResultCode("11");//입력값 에러
			map.put("serverMessage", serverMessage);
			return map;
			//return "asapro/certificate/certiNumForm";
		} else {
			//아이디 존재 여부 확인
			AllowIpTemp fromDB = allowIpService.getAllowIpTemp(allowIpTempForm);
			
			//아이디가 존재하면
			if( fromDB != null ){
				//보안상 너무 많은 시도를 막기위해 인증회숫 재한
				//일단 5회로 재한\
				if(fromDB.getAuthentiFailCnt() >= 5){
					serverMessage.setSuccess(false);
					serverMessage.setText("인증 시도 횟수 초과로 자동취소 되었습니다. 처음부터 다시 시도해 주십시오.");
					serverMessage.setResultCode("21");//인증횟수 재한 초과
					map.put("serverMessage", serverMessage);
					return map;
				}
				
				//SMS인증번호 확인
				if(fromDB.getCertiNum().equals(allowIpTempForm.getCertiNum()) ){
					//인증완료처리
					allowIpTempForm.setAuthentication(true);
					allowIpTempForm.setCertiCompletDate(new Date());
					allowIpService.updateAuthentication(allowIpTempForm);
					
					//관리자페이지 로그인 화면으로 전환
//					Login loginForm = new Login();
//					loginForm.setLoginId(allowIpTempForm.getAdminId());
//					redirectAttributes.addFlashAttribute("loginForm", loginForm);
//					return "redirect:" + AsaproUtils.getAdminPath(currentSite, true) + "/login.do";
					serverMessage.setSuccess(true);
					serverMessage.setText("인증완료되었습니다.");
					serverMessage.setResultCode("00");//인증성공
					map.put("serverMessage", serverMessage);
					return map;
				} else {
					//인증번호 불일치
					
					//인증실패횟수 증가
					allowIpService.updateAuthentiFailCnt(allowIpTempForm);
					
					
					//bindingResult.addError(new FieldError("allowIpTempForm", "certiNum", "인증번호가 일치하지 않습니다."));
					serverMessage.setSuccess(false);
					serverMessage.setText("인증번호가 일치하지 않습니다.");
					serverMessage.setResultCode("12");//인증번호 불일치
					map.put("serverMessage", serverMessage);
				}
				
			}else{
				serverMessage.setSuccess(false);
				serverMessage.setText("임시허용 정보 검색 실패");
				serverMessage.setResultCode("41");//임시허용 정보 검색 실패
				map.put("serverMessage", serverMessage);
			}
		}
		
		return map;
	}
	
	/**
	 * 휴대폰 인증번호 다시발송.
	 * - resultCode 
	 * - 00 => 성공
	 * - 11 => 입력값 에러
	 * - 12 => 실패
	 * - 21 => 횟수 재한 초과
	 * - 41 => 임시허용 정보 검색 실패
	 * @param model
	 * @param currentSite
	 * @param allowIpTempForm
	 * @return 발송결과
	 */
	@RequestMapping(value=Constant.APP_PATH + Constant.SITE_ID_PATH + "/certificate" + Constant.API_PATH + " /reSMSCertiNum", method=RequestMethod.POST)
	@ResponseBody
	public ServerMessage reSMSCertiNumPost(Model model, RedirectAttributes redirectAttributes, @CurrentSite Site currentSite, @ModelAttribute("allowIpTempForm") AllowIpTemp allowIpTempForm, BindingResult bindingResult){
		ServerMessage serverMessage = new ServerMessage();
		allowIpTempValidator.validate(allowIpTempForm, bindingResult, "RE_SMS_CERIT_NUM");//SMS 인증번호 재발송 validate
		if( bindingResult.hasErrors() ){
			serverMessage.setSuccess(false);
			serverMessage.setText(bindingResult.getFieldError("certiNum").getDefaultMessage());
			serverMessage.setResultCode("11");//입력값 에러
		} else {
			//아이디 존재 여부 확인
			AllowIpTemp fromDB = allowIpService.getAllowIpTemp(allowIpTempForm);
			
			//아이디가 존재하면
			if( fromDB != null ){
				//sms발송 횟수를 체크하여 필요이상 재발송 요청했을경우 인증절차를 종료시킨다.
				//일단은 3회로 재한
				if(fromDB.getSmsSendCnt() >= 3){
					serverMessage.setSuccess(false);
					serverMessage.setText("인증 시도 횟수 초과로 자동취소 되었습니다. 처음부터 다시 시도해 주십시오.");
					serverMessage.setResultCode("21");//sms 발송횟수 초과
					return serverMessage;
				}
				
				//sms인증번호 생성
				String certiNum = RandomStringUtils.randomNumeric(6);
				
				//sms인증번호 발송
				String msg = "[서울시 청년포털]본인확인인증번호[" + certiNum + "]입니다.";
				/**
				 * 각 사이트의 문자발송 api를 사용하여 커스터마이징 하면 됨
				 */
				//=============   서울시 sms 전송 api  ==================================
//				SmsSend sms = new SmsSend(fromDB.getAdminHp(), msg, null);
//				long result = sms.send();
				//=============    서울시 sms 전송 api  ==================================
//				LOGGER.info("[sms 인증번호 발송]  발송결과 : {} " , result);
				int result = 1;
				//SMS인증번호 확인
				if(result > 0 ){
					//관리자 임시허용 정보의 인증번호 수정
					allowIpTempForm.setCertiNum(certiNum);
					allowIpService.updateCertifiNum(allowIpTempForm);
					
					serverMessage.setSuccess(true);
					serverMessage.setText("인증번호가 재전송 되었습니다.");
					serverMessage.setResultCode("00");//인증번호가 재전송 성공
				} else {
					serverMessage.setSuccess(false);
					serverMessage.setText("인증번호 재전송 실패");
					serverMessage.setResultCode("12");//sms 발송횟수 초과
				}
				
			}else{
				serverMessage.setSuccess(false);
				serverMessage.setText("임시허용 정보 검색 실패");
				serverMessage.setResultCode("41");//임시허용 정보 검색 실패
			}
		}
		
		return serverMessage;
	}
	
	//===================================================================================================
	//===================================================================================================
	//===================================================================================================
	/**
	 * 관리자 임시접속허용 IP 목록뷰를 반환한다.
	 * @param model
	 * @param allowIpSearch
	 * @return 임시접속허용 IP 목록
	 */
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/allowipTemp/list.do", method=RequestMethod.GET)
	public String allowIpTempListGet(Model model, @CurrentSite Site currentSite, @ModelAttribute("allowIpTempSearch") AllowIpTempSearch allowIpTempSearch){
		allowIpTempSearch.fixBrokenSvByDefaultCharsets();
		allowIpTempSearch.setSitePrefix(currentSite.getSitePrefix());
		
		int total = allowIpService.getAllowIpTempListTotal(allowIpTempSearch);
		List<AllowIpTemp> list = allowIpService.getAllowIpTempList(allowIpTempSearch);
		
		Paging paging = new Paging(list, total, allowIpTempSearch);
		model.addAttribute("paging", paging);
		
		return "asapro/admin/allowip/tempList";
	}
	
}
