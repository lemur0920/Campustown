/**
 * 
 */
package egovframework.com.asapro.member.user.web;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ibm.icu.text.SimpleDateFormat;

import egovframework.com.asapro.code.service.Code;
import egovframework.com.asapro.code.service.CodeCategory;
import egovframework.com.asapro.code.service.CodeService;
import egovframework.com.asapro.member.user.service.UserTermsAgreement;
import egovframework.com.asapro.member.user.service.UserMember;
import egovframework.com.asapro.member.user.service.UserMemberService;
//import egovframework.com.asapro.group.service.Group;
//import egovframework.com.asapro.group.service.GroupService;
import egovframework.com.asapro.organization.service.Department;
import egovframework.com.asapro.organization.service.DepartmentSearch;
import egovframework.com.asapro.organization.service.Organization;
import egovframework.com.asapro.organization.service.OrganizationSearch;
import egovframework.com.asapro.organization.service.OrganizationService;
import egovframework.com.asapro.role.service.Role;
import egovframework.com.asapro.role.service.RoleSearch;
import egovframework.com.asapro.role.service.RoleService;
import egovframework.com.asapro.site.service.Site;
import egovframework.com.asapro.site.service.SiteService;
import egovframework.com.asapro.support.Constant;
import egovframework.com.asapro.support.SEO;
import egovframework.com.asapro.support.ServerMessage;
import egovframework.com.asapro.support.annotation.CurrentUser;
import egovframework.com.asapro.support.annotation.CurrentSite;
import egovframework.com.asapro.support.annotation.PrivacyHistory;
import egovframework.com.asapro.support.pagination.Paging;
import egovframework.com.asapro.support.util.AsaproUtils;
import egovframework.com.asapro.support.util.EtcUtil;

/**
 * 사용자 회원 컨트롤러
 * @author yckim
 * @since 2020. 2. 24.
 *
 */
@Controller
public class UserMemberController {
	@Autowired
	private MessageSource messageSource;
	
	@Autowired
	private UserMemberService userMemberService;
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private UserMemberValidator userMemberValidator;
	
	@Autowired
	private UserPasswordValidator userPasswordValidator;
	
	@Autowired
	private UserTermsAgreementValidator userTermsAgreementValidator; 
	
	@Autowired
	private CodeService codeService;
	
//	@Autowired
//	private GroupService groupService;
	
	@Autowired
	private OrganizationService organizationService;
	
	@Autowired
	private SiteService siteService;
	
	@Autowired
	private HttpServletRequest request;
	
	@Autowired
	private HttpSession session;
	
	/**
	 * 코드목록 로드
	 * @param model
	 */
	private void loadCodes(Model model) {
		//지역번호
		model.addAttribute("areaCodeList", codeService.getCodeList("tel_area_code"));	//codeOrder ASC
		//휴대폰번호 앞자리
		model.addAttribute("mobile1CodeList", codeService.getCodeList("mobile1_code"));
		//직급/직책 목록
		model.addAttribute("positionList", codeService.getCodeList("position"));	
		
	}
	
	/**
	 * 기관목록 로드
	 * @param model
	 */
	private void loadOrganization(Model model){
		//사용중인 기관목록
		OrganizationSearch organizationSearch = new OrganizationSearch();
		organizationSearch.setPaging(false);
		//organizationSearch.setOrgUse(true);
		organizationSearch.setDefaultSort("ORG_NAME", "ASC");
		List<Organization> organizationList = organizationService.getOrganizationList(organizationSearch);
		model.addAttribute("organizationList", organizationList);
	}
	
	/**
	 * 부서목록 로드
	 * @param model
	 * @param orgId
	 */
	private void loadDepartment(Model model, String orgId){
		//부서목록
		DepartmentSearch departmentSearch = new DepartmentSearch();
		departmentSearch.setPaging(false);
		departmentSearch.setDepUse(true);
		departmentSearch.setOrgId(orgId);
		List<Department> departmentList = organizationService.getDepartmentList(departmentSearch);
		model.addAttribute("departmentList", departmentList);
	}
	
	/**
	 * 약관 동의 폼 뷰를 반환한다.
	 * @param model
	 * @param redirectAttributes
	 * @param currentSite
	 * @param userTermsAgreementForm
	 * @return 약관동의 폼 뷰
	 */
	@RequestMapping(value=Constant.APP_PATH + Constant.SITE_ID_PATH + "/member/agreement", method=RequestMethod.GET)
	public String memberJoinAgreementGet(Model model, @CurrentSite Site currentSite, @ModelAttribute("userTermsAgreementForm") UserTermsAgreement userTermsAgreementForm){
		
		
		
		model.addAttribute("seo", new SEO("약관동의 | ", "mypage", "약관동의"));
		//return "theme/" + theme + "/member/agreement";
		
		return AsaproUtils.getThemePath(request) + "member/agreement";
	}
	
	/**
	 * 약관 동의확인 후 정보입력 뷰로 리다이렉트시킨다.
	 * @param model
	 * @param currentSite
	 * @param userTermsAgreementForm
	 * @param bindingResult
	 * @return 
	 */
	@RequestMapping(value=Constant.APP_PATH + Constant.SITE_ID_PATH + "/member/agreement", method=RequestMethod.POST)
	public String memberJoinAgreementPost(Model model, @CurrentSite Site currentSite, @ModelAttribute("userTermsAgreementForm") UserTermsAgreement userTermsAgreementForm, BindingResult bindingResult){
		
		userTermsAgreementValidator.validate(userTermsAgreementForm, bindingResult, "AGREE");
		
		if( bindingResult.hasErrors() ){
			return AsaproUtils.getThemePath(request) + "member/agreement";
		} else {
			session.setAttribute("member.signup.agreement.result", "true");
			//DB에 저장해야 하는경우 DB 저장 후 처리하는 방식으로 처리
			
			return "redirect:" + AsaproUtils.getAppPath(currentSite) + "/member/signup";
		}
	}
	
	/**
	 * 회원가입 폼 뷰를 반환한다.
	 * @param model
	 * @param redirectAttributes
	 * @param currentSite
	 * @param userMemberForm
	 * @return 회원가입 폼뷰
	 */
	@RequestMapping(value=Constant.APP_PATH + Constant.SITE_ID_PATH + "/member/signup", method=RequestMethod.GET)
	public String memberJoinSignUpGet(Model model, RedirectAttributes redirectAttributes, @CurrentSite Site currentSite, @ModelAttribute("userMemberForm") UserMember userMemberForm){
		
		//약관 동의했는지 체크
		if( !"true".equals((String)session.getAttribute("member.signup.agreement.result")) ){
			return "redirect:" + AsaproUtils.getAppPath(currentSite) + "/member/agreement";
		}
		
		model.addAttribute("formMode", "INSERT");
		//본인인증 결과를 담아놓은 정보
		//userMemberForm.setUserName((String)session.getAttribute("member.signup.identification.name"));
		
		this.loadCodes(model);
		
		model.addAttribute("seo", new SEO("회원가입 | ", "mypage", "회원가입"));
		
		return AsaproUtils.getThemePath(request) + "/member/userForm";
	}
	
	/**
	 * 회원가입을 처리한다.
	 * @param model
	 * @param redirectAttributes
	 * @param currentSite
	 * @param userMemberForm
	 * @param bindingResult
	 * @return 회원가입 결과
	 */
	//@PrivacyHistory(moduleType="회원가입", history="사용자 회원가입")
	@RequestMapping(value=Constant.APP_PATH + Constant.SITE_ID_PATH + "/member/signup", method=RequestMethod.POST)
	public String memberJoinSignUpPost(Model model, RedirectAttributes redirectAttributes, @CurrentSite Site currentSite, @ModelAttribute("userMemberForm") UserMember userMemberForm, BindingResult bindingResult){
		//약관 동의했는지 체크
		if( !"true".equals((String)session.getAttribute("member.signup.agreement.result")) ){
			return "redirect:" + AsaproUtils.getAppPath(currentSite) + "/member/agreement";
		}
		
		/**
		 * 본인 인증 했는지 체크
		 * 본인인증 모듈을 통해 했거나
		 * 문자인증이나 메일 인증방식중 선택적으로 적용
		 */
		//구현

		userMemberValidator.validate(userMemberForm, bindingResult, "INSERT");
		
		if( bindingResult.hasErrors() ){
			model.addAttribute("formMode", "INSERT");
			
			//코드목록
			this.loadCodes(model);
			
			model.addAttribute("seo", new SEO("회원가입 | ", "mypage", "회원가입"));
			
			return AsaproUtils.getThemePath(request) + "/member/userForm";
			
		} else {
			
			redirectAttributes.addFlashAttribute("inserted", true);
			
			//본인인증 결과를 담아놓은 정보
			//userMemberForm.setUserName((String)session.getAttribute("member.signup.identification.name"));
			
			model.addAttribute("seo", new SEO("회원가입 | ", "mypage", "회원가입"));
			
			return "redirect:" + AsaproUtils.getAppPath(currentSite) + "/login";
		}
		
		
	}
	
	/**
	 * 휴대폰 인증번호 발송
	 */
	
	/**
	 * 휴대폰 인증번호 확인
	 */
	
	/**
	 * 중복체크
	 */
	
	
	
	
	
	//==============================================================================================================
	//================================================  마이페이지  =========================================================
	//==============================================================================================================
	
	/**
	 * 마이페이지 인덱스 뷰를 반환한다.
	 * @param model
	 * @return 마이페이지 인덱스 뷰
	 */
	@RequestMapping(value=Constant.APP_PATH + Constant.SITE_ID_PATH + "/member/mypage/index", method=RequestMethod.GET)
	public String mypageIndexGet(Model model, @CurrentSite Site currentSite){
		//model.addAttribute("seo", new SEO("", "mypage", ""));
		
		session.setAttribute("mypage.update.password.check", null);
		return AsaproUtils.getThemePath(request) + "/member/mypage/index";
		//return "redirect:" + AsaproUtils.getAppPath(currentSite) + "/member/mypage/update";
	}
	
	
	
	
	
	
	
}
