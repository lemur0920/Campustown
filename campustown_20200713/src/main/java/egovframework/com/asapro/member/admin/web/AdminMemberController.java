/**
 * 
 */
package egovframework.com.asapro.member.admin.web;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import egovframework.com.asapro.code.service.CodeService;
//import egovframework.com.asapro.group.service.Group;
//import egovframework.com.asapro.group.service.GroupService;
import egovframework.com.asapro.member.admin.service.AdminMember;
import egovframework.com.asapro.member.admin.service.AdminMemberSearch;
import egovframework.com.asapro.member.admin.service.AdminMemberService;
import egovframework.com.asapro.member.admin.service.AdminPassword;
import egovframework.com.asapro.member.admin.service.AdminSiteRoleRel;
import egovframework.com.asapro.organization.service.Department;
import egovframework.com.asapro.organization.service.DepartmentSearch;
import egovframework.com.asapro.organization.service.Organization;
import egovframework.com.asapro.organization.service.OrganizationSearch;
import egovframework.com.asapro.organization.service.OrganizationService;
import egovframework.com.asapro.organization.service.Team;
import egovframework.com.asapro.organization.service.TeamSearch;
import egovframework.com.asapro.role.service.Role;
import egovframework.com.asapro.role.service.RoleSearch;
import egovframework.com.asapro.role.service.RoleService;
import egovframework.com.asapro.site.service.Site;
import egovframework.com.asapro.site.service.SiteService;
import egovframework.com.asapro.support.Constant;
import egovframework.com.asapro.support.ServerMessage;
import egovframework.com.asapro.support.annotation.CurrentAdmin;
import egovframework.com.asapro.support.annotation.CurrentSite;
import egovframework.com.asapro.support.annotation.PrivacyHistory;
import egovframework.com.asapro.support.exception.AsaproNoCapabilityException;
import egovframework.com.asapro.support.exception.AsaproNotFoundException;
import egovframework.com.asapro.support.pagination.Paging;
import egovframework.com.asapro.support.util.AsaproUtils;
import egovframework.com.campustown.startHpMngr.service.StartHpMngr;
import egovframework.com.campustown.startHpMngr.service.StartHpMngrSearch;
import egovframework.com.campustown.startHpMngr.service.StartHpMngrService;

/**
 * 관리자 회원 컨트롤러
 * @author yckim
 * @since 2018. 4. 26.
 *
 */
@Controller
public class AdminMemberController {
	private static final Logger LOGGER = LoggerFactory.getLogger(AdminMemberController.class);
	
	@Autowired
	private MessageSource messageSource;
	
	@Autowired
	private AdminMemberService adminMemberService;
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private AdminMemberValidator adminMemberValidator;
	
	@Autowired
	private PasswordValidator passwordValidator;
	
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
	private StartHpMngrService startHpMngrService;
	
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
	 * 팀목록 로드
	 * @param model
	 * @param depId
	 */
	private void loadTeam(Model model, String depId){
		//팀목록
		TeamSearch teamSearch = new TeamSearch();
		teamSearch.setPaging(false);
		teamSearch.setTeamUse(true);
		teamSearch.setDepId(depId);
		List<Team> teamList = organizationService.getTeamList(teamSearch);
		model.addAttribute("teamList", teamList);
	}
	
	//==============================================================================================================================
	
	/**
	 * 관리자목록을 반환한다.
	 * @param model
	 * @param adminMemberSearch
	 * @return 관리자 목록 뷰
	 */
	@PrivacyHistory(moduleType="회원관리", history="관리자 목록 조회")
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/member/admin/list.do", method=RequestMethod.GET)
	public String adminMemberListGet(Model model, @CurrentAdmin AdminMember currentAdmin, @ModelAttribute("adminMemberSearch") AdminMemberSearch adminMemberSearch){
		//기본 정렬 순서
		//if(StringUtils.isBlank(memberSearch.getSortOrder())){
		//	memberSearch.setSortOrder("MEM_REGDATE");
		//	memberSearch.setSortDirection("DESC");
		//}
		
		adminMemberSearch.fixBrokenSvByDefaultCharsets();
		
		//최고관리자 이하의 조직별 관리레벨을 가진 계정의 제한처리
		if(!currentAdmin.isSuperAdmin() ){
			if(currentAdmin.getAdminManagementLevel() != null && currentAdmin.getAdminManagementLevel() > 0 ){
				if(currentAdmin.getAdminManagementLevel() >= 1 ){
					adminMemberSearch.setOrgId(currentAdmin.getAdminOrganization());
				}
				if(currentAdmin.getAdminManagementLevel() >= 2 ){
					adminMemberSearch.setDepId(currentAdmin.getAdminDepartment());
				}
				if(currentAdmin.getAdminManagementLevel() >= 3 ){
					if(StringUtils.isNotBlank(currentAdmin.getAdminTeam()) ){
						adminMemberSearch.setTeamId(currentAdmin.getAdminTeam());
					}else{
						//adminMemberSearch.setTeamId("notauth");
						LOGGER.info("[ASAPRO] AdminMemberController " + " CAP_MEMBER_ADMIN_BY_ORG : No authority !!!");
						throw new AsaproNoCapabilityException("팀레벨 관리 권한이 있으나 팀에 소속되어 있지 않습니다.","back");
					}
				}
			} else {
				//조직별 관리권한이지만 레벨이 설정되지 않은경우 아무런 데이터도 보여주지 않는다
				adminMemberSearch.setOrgId("notauth");
				LOGGER.info("[ASAPRO] AdminMemberController " + " CAP_MEMBER_ADMIN_BY_ORG : No authority !!!");
				throw new AsaproNoCapabilityException("관리 레벨이 부여 되지 않았습니다.","back");
			}
		}
		
		int total = adminMemberService.getAdminMemberListTotal(adminMemberSearch);
		List<AdminMember> list = adminMemberService.getAdminMemberList(adminMemberSearch);
		Paging paging = new Paging(list, total, adminMemberSearch);
		model.addAttribute("paging", paging);
		
		//역할목록
		RoleSearch roleSearch = new RoleSearch();
		roleSearch.setPaging(false);
		model.addAttribute("roleList", roleService.getRoleList(roleSearch));
		
		//모든사이트 목록
		List<Site> allSiteList = siteService.getSiteList(null);
		model.addAttribute("allSiteList", allSiteList);
		
//		model.addAttribute("groupList", groupService.getGroupList(null));
		
		//기관목록
		this.loadOrganization(model);
		//부서목록
		if(StringUtils.isNotBlank(adminMemberSearch.getOrgId()) ){
			this.loadDepartment(model, adminMemberSearch.getOrgId());
		}
		/*//팀목록
		if(StringUtils.isNotBlank(adminMemberSearch.getDepId()) ){
			this.loadTeam(model, adminMemberSearch.getDepId());
		}*/
		//창업팀 목록
		StartHpMngrSearch startHpMngrSearch = new StartHpMngrSearch();
		startHpMngrSearch.setPaging(false);
		List<StartHpMngr> allTeamList = startHpMngrService.getStartHpMngrList(startHpMngrSearch);
		model.addAttribute("allTeamList", allTeamList);
		
		if(StringUtils.isNotBlank(adminMemberSearch.getDepId()) ){
			startHpMngrSearch.setUnivId(adminMemberSearch.getDepId());
			List<StartHpMngr> teamList = startHpMngrService.getStartHpMngrList(startHpMngrSearch);
			model.addAttribute("teamList", teamList);
		}
		
		return "asapro/admin/member/admin/list";
	}
	
	/**
	 * 관리자 추가폼 뷰를 반환한다.
	 * @param model
	 * @param adminMemberForm
	 * @return 과리자 추가폼 뷰
	 */
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/member/admin/insert.do", method=RequestMethod.GET)
	public String insertAdminMemberGet(Model model, @CurrentAdmin AdminMember currentAdmin, @ModelAttribute("adminMemberForm") AdminMember adminMemberForm){
		model.addAttribute("formMode", "INSERT");
		
		//최고관리자 이하의 조직별 관리레벨을 가진 계정의 제한처리
		if(!currentAdmin.isSuperAdmin() ){
			if(currentAdmin.getAdminManagementLevel() != null && currentAdmin.getAdminManagementLevel() > 0 ){
				if(currentAdmin.getAdminManagementLevel() >= 1 ){
					adminMemberForm.setAdminOrganization(currentAdmin.getAdminOrganization());
				}
				if(currentAdmin.getAdminManagementLevel() >= 2 ){
					adminMemberForm.setAdminDepartment(currentAdmin.getAdminDepartment());
				}
				if(currentAdmin.getAdminManagementLevel() >= 3 ){
					if(StringUtils.isNotBlank(currentAdmin.getAdminTeam()) ){
						adminMemberForm.setAdminTeam(currentAdmin.getAdminTeam());
					}else{
						//adminMemberSearch.setTeamId("notauth");
						LOGGER.info("[ASAPRO] AdminMemberController " + " : No authority !!!");
						throw new AsaproNoCapabilityException("팀레벨 관리 권한이 있으나 팀에 소속되어 있지 않습니다.","back");
					}
				}
			} else {
				//조직별 관리권한이지만 레벨이 설정되지 않은경우 아무런 데이터도 보여주지 않는다
				//adminMemberForm.setAdminOrganization("notauth");
				LOGGER.info("[ASAPRO] AdminMemberController " + " : No authority !!!");
				throw new AsaproNoCapabilityException("관리 레벨이 부여 되지 않았습니다.","back");
			}
		}
		
		//가입시 역할 세팅
		//Role joinRole = roleService.getJoinRole();
		//adminMemberForm.setAdminRole(new Role());
		//adminMemberForm.getAdminRole().setRoleCode(joinRole.getRoleCode());
		//회원 추가시 기본 상태는 활성
		adminMemberForm.setAdminActive(true);
		
		model.addAttribute("adminMemberForm", adminMemberForm);
		
		//역할목록
		RoleSearch roleSearch = new RoleSearch();
		roleSearch.setPaging(false);
		List<Role> allRoleList = roleService.getRoleList(roleSearch);
		
		//"admin" 계정만 최고관리자 롤을 부여할 수 있다
		if(!"admin".equals(currentAdmin.getAdminId()) ){
			
			List<Role> tempAllRoleList = new ArrayList<Role>();
			for (Role role : allRoleList) {
				if(!"ROLE_SUPER_ADMIN".equals(role.getRoleCode()) ){
					tempAllRoleList.add(role);
				}
			}
			allRoleList = tempAllRoleList;
		}
		
		//모든사이트 목록
		List<Site> allSiteList = siteService.getSiteList(null);
		
		if(!"admin".equals(currentAdmin.getAdminId()) ){
			List<Site> authSiteList = new ArrayList<Site>();
			
			//관리자가 가지고 있는 권한의 사이트 목록
			for (Site site : allSiteList) {
				for (AdminSiteRoleRel rel : currentAdmin.getAdminSiteRoleRelList()) {
					if(site.getSitePrefix().equals(rel.getSitePrefix()) ){
						authSiteList.add(site);
					}
				}
			}
			model.addAttribute("allSiteList", authSiteList);
		} else {
			model.addAttribute("allSiteList", allSiteList);
		}
		//권한목록
		if(!currentAdmin.isSuperAdmin() ){
			List<Role> authRoleList = new ArrayList<Role>();
			
			//관리자가 가지고 있는 권한 목록
			for (Role role : allRoleList) {
				for (AdminSiteRoleRel rel : currentAdmin.getAdminSiteRoleRelList()) {
					if(StringUtils.isNotBlank(rel.getRoleCode()) ){
						for (String item : rel.getRoleCode().split(",") ) {
							if(role.getRoleCode().equals(item) ){
								authRoleList.add(role);
							}
						}
					}
				}
			}
			model.addAttribute("roleList", authRoleList);
		} else {
			model.addAttribute("roleList", allRoleList);
		}
		
		//코드목록
		this.loadCodes(model);
		//기관목록
		this.loadOrganization(model);
		//부서목록
		if(StringUtils.isNotBlank(adminMemberForm.getAdminOrganization()) ){
			this.loadDepartment(model, adminMemberForm.getAdminOrganization());
		}
		/*//팀목록
		if(StringUtils.isNotBlank(adminMemberForm.getAdminDepartment()) ){
			this.loadTeam(model, adminMemberForm.getAdminDepartment());
		}*/
		//창업팀 목록
		if(StringUtils.isNotBlank(adminMemberForm.getAdminDepartment()) ){
			StartHpMngrSearch startHpMngrSearch = new StartHpMngrSearch();
			startHpMngrSearch.setPaging(false);
			startHpMngrSearch.setUnivId(adminMemberForm.getAdminDepartment());
			List<StartHpMngr> teamList = startHpMngrService.getStartHpMngrList(startHpMngrSearch);
			model.addAttribute("teamList", teamList);
		}
		
		return "asapro/admin/member/admin/form";
	}
	
	/**
	 * 관리자를 추가한다.
	 * @param model
	 * @param redirectAttributes
	 * @param currentSite
	 * @param adminMemberForm
	 * @param bindingResult
	 * @return 목록 뷰
	 * @throws FileNotFoundException 
	 */
	@PrivacyHistory(moduleType="회원관리", history="관리자 추가")
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/member/admin/insert.do", method=RequestMethod.POST)
	public String insertAdminMemberPost(Model model, @CurrentAdmin AdminMember currentAdmin, RedirectAttributes redirectAttributes, @CurrentSite Site currentSite, @ModelAttribute("adminMemberForm") AdminMember adminMemberForm, BindingResult bindingResult) throws FileNotFoundException{
		
		//최고관리자 이하의 조직별 관리레벨을 가진 계정의 제한처리
		if(!currentAdmin.isSuperAdmin() ){
			if(currentAdmin.getAdminManagementLevel() != null && currentAdmin.getAdminManagementLevel() > 0 ){
				if(currentAdmin.getAdminManagementLevel() >= 1 ){
					adminMemberForm.setAdminOrganization(currentAdmin.getAdminOrganization());
				}
				if(currentAdmin.getAdminManagementLevel() >= 2 ){
					adminMemberForm.setAdminDepartment(currentAdmin.getAdminDepartment());
				}
				if(currentAdmin.getAdminManagementLevel() >= 3 ){
					if(StringUtils.isNotBlank(currentAdmin.getAdminTeam()) ){
						adminMemberForm.setAdminTeam(currentAdmin.getAdminTeam());
					}else{
						//adminMemberSearch.setTeamId("notauth");
						LOGGER.info("[ASAPRO] AdminMemberController " + " : No authority !!!");
						throw new AsaproNoCapabilityException("팀레벨 관리 권한이 있으나 팀에 소속되어 있지 않습니다.","back");
					}
				}
			} else {
				//조직별 관리권한이지만 레벨이 설정되지 않은경우 아무런 데이터도 보여주지 않는다
				//adminMemberForm.setAdminOrganization("notauth");
				LOGGER.info("[ASAPRO] AdminMemberController " + " : No authority !!!");
				throw new AsaproNoCapabilityException("관리 레벨이 부여 되지 않았습니다.","back");
			}
		}
		
		adminMemberValidator.validate(adminMemberForm, bindingResult, "INSERT");
		if( bindingResult.hasErrors() ){
			model.addAttribute("formMode", "INSERT");
			
			//역할목록
			RoleSearch roleSearch = new RoleSearch();
			roleSearch.setPaging(false);
			List<Role> allRoleList = roleService.getRoleList(roleSearch);
			
			//"admin" 계정만 최고관리자 롤을 부여할 수 있다
			if(!"admin".equals(currentAdmin.getAdminId()) ){
				
				List<Role> tempAllRoleList = new ArrayList<Role>();
				for (Role role : allRoleList) {
					if(!"ROLE_SUPER_ADMIN".equals(role.getRoleCode()) ){
						tempAllRoleList.add(role);
					}
				}
				allRoleList = tempAllRoleList;
			}
			
			//모든사이트 목록
			List<Site> allSiteList = siteService.getSiteList(null);
			
			if(!"admin".equals(currentAdmin.getAdminId()) ){
				List<Site> authSiteList = new ArrayList<Site>();
				
				//관리자가 가지고 있는 권한의 사이트 목록
				for (Site site : allSiteList) {
					for (AdminSiteRoleRel rel : currentAdmin.getAdminSiteRoleRelList()) {
						if(site.getSitePrefix().equals(rel.getSitePrefix()) ){
							authSiteList.add(site);
						}
					}
				}
				model.addAttribute("allSiteList", authSiteList);
			} else {
				model.addAttribute("allSiteList", allSiteList);
			}
			//권한목록
			if(!currentAdmin.isSuperAdmin() ){
				List<Role> authRoleList = new ArrayList<Role>();
				
				//관리자가 가지고 있는 권한 목록
				for (Role role : allRoleList) {
					for (AdminSiteRoleRel rel : currentAdmin.getAdminSiteRoleRelList()) {
						if(StringUtils.isNotBlank(rel.getRoleCode()) ){
							for (String item : rel.getRoleCode().split(",") ) {
								if(role.getRoleCode().equals(item) ){
									authRoleList.add(role);
								}
							}
						}
					}
				}
				model.addAttribute("roleList", authRoleList);
			} else {
				model.addAttribute("roleList", allRoleList);
			}
			
			//코드목록
			this.loadCodes(model);
			
			//기관목록
			this.loadOrganization(model);
			
			//부서목록
			if(StringUtils.isNotBlank(adminMemberForm.getAdminOrganization()) ){
				this.loadDepartment(model, adminMemberForm.getAdminOrganization());
			}
			//팀목록
			/*if(StringUtils.isNotBlank(adminMemberForm.getAdminDepartment()) ){
				this.loadTeam(model, adminMemberForm.getAdminDepartment());
			}*/
			
			//창업팀 목록
			if(StringUtils.isNotBlank(adminMemberForm.getAdminDepartment()) ){
				StartHpMngrSearch startHpMngrSearch = new StartHpMngrSearch();
				startHpMngrSearch.setPaging(false);
				startHpMngrSearch.setUnivId(adminMemberForm.getAdminDepartment());
				List<StartHpMngr> teamList = startHpMngrService.getStartHpMngrList(startHpMngrSearch);
				model.addAttribute("teamList", teamList);
			}
			
			model.addAttribute("insertFail", true);
			
			return "asapro/admin/member/admin/form";
		} else {
			//비밀번호 마지막 변경일시 - 관리자가 추가할때는 90일 전으로 해서 로그인할때 비밀번호 변경하도록 유도한다.
			//adminMemberForm.setAdminPWLastUpdated(DateUtils.addDays(new Date(), -90));
			
			adminMemberService.insertAdminMember(adminMemberForm);
			redirectAttributes.addFlashAttribute("inserted", true);
			return "redirect:" + AsaproUtils.getAdminPath(currentSite, true) + "/member/admin/list.do";
		}
	}
	
	/**
	 * 관리자 수정폼 뷰를 반환한다.
	 * @param model
	 * @param adminMemberForm
	 * @return 관리자 추가폼 뷰
	 */
	@PrivacyHistory(moduleType="회원관리", history="관리자 조회")
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/member/admin/update.do", method=RequestMethod.GET)
	public String updateAdminMemberGet(Model model, @CurrentAdmin AdminMember currentAdmin, @CurrentSite Site currentSite, @ModelAttribute("adminMemberForm") AdminMember adminMemberForm){
		if(StringUtils.isBlank(adminMemberForm.getAdminId()) ){
			LOGGER.info("[ASAPRO] AdminMemberController " + " : No ADMINID !!!");
			throw new AsaproNotFoundException("아이디가 전달되지 않았습니다.","back");
		}
		if("admin".equals(adminMemberForm.getAdminId()) ){
			LOGGER.info("[ASAPRO] AdminMemberController " + " : No authority !!!");
			throw new AsaproNoCapabilityException("수정할 수 없는 계정입니다.","back");
		}
		
		//가입시 역할 세팅
		//Role joinRole = roleService.getJoinRole();
		//adminMemberForm.setAdminRole(new Role());
		//adminMemberForm.getAdminRole().setRoleCode(joinRole.getRoleCode());
		adminMemberForm.setSitePrefix(currentSite.getSitePrefix());
		AdminMember adminMemberFormModel = adminMemberService.getAdminMember(adminMemberForm);
		
		if(adminMemberFormModel == null ){
			LOGGER.info("[ASAPRO] AdminMemberController " + " : No ADMIN !!!");
			throw new AsaproNotFoundException("존제하지 않는 계정입니다.","back");
		}
		//최고관리자 이하의 조직별 관리레벨을 가진 계정의 제한처리
		if(!currentAdmin.isSuperAdmin() ){
			if(currentAdmin.getAdminManagementLevel() != null && currentAdmin.getAdminManagementLevel() > 0 ){
				if(currentAdmin.getAdminManagementLevel() >= 1 ){
					if(!currentAdmin.getAdminOrganization().equals(adminMemberFormModel.getAdminOrganization()) ){
						LOGGER.info("[ASAPRO] AdminMemberController " + " : Not Same Organization !!!");
						throw new AsaproNoCapabilityException("수정권한이 없는 계정입니다.","back");
					}
				}
				if(currentAdmin.getAdminManagementLevel() >= 2 ){
					if(!currentAdmin.getAdminDepartment().equals(adminMemberFormModel.getAdminDepartment()) ){
						LOGGER.info("[ASAPRO] AdminMemberController " + " : Not Same Department !!!");
						throw new AsaproNoCapabilityException("수정권한이 없는 계정입니다.","back");
					}
				}
				if(currentAdmin.getAdminManagementLevel() >= 3 ){
					if(StringUtils.isNotBlank(currentAdmin.getAdminTeam()) ){
						if(!currentAdmin.getAdminTeam().equals(adminMemberFormModel.getAdminTeam()) ){
							LOGGER.info("[ASAPRO] AdminMemberController " + " : Not Same Team !!!");
							throw new AsaproNoCapabilityException("수정권한이 없는 계정입니다.","back");
						}
					}else{
						LOGGER.info("[ASAPRO] AdminMemberController " + " : No authority !!!");
						throw new AsaproNoCapabilityException("팀레벨 관리 권한이 있으나 팀에 소속되어 있지 않습니다.","back");
					}
				}
			} else {
				//조직별 관리권한이지만 레벨이 설정되지 않은경우 아무런 데이터도 보여주지 않는다
				LOGGER.info("[ASAPRO] AdminMemberController " + " : No authority !!!");
				throw new AsaproNoCapabilityException("관리 레벨이 부여 되지 않았습니다.","back");
			}
		}
		
		model.addAttribute("formMode", "UPDATE");
		model.addAttribute("adminMemberForm", adminMemberFormModel);
		
		//역할목록
		RoleSearch roleSearch = new RoleSearch();
		roleSearch.setPaging(false);
		List<Role> allRoleList = roleService.getRoleList(roleSearch);
		
		if(!"admin".equals(currentAdmin.getAdminId()) ){
			
			List<Role> tempAllRoleList = new ArrayList<Role>();
			for (Role role : allRoleList) {
				if(!"ROLE_SUPER_ADMIN".equals(role.getRoleCode()) ){
					tempAllRoleList.add(role);
				}
			}
			allRoleList = tempAllRoleList;
		}
		
		//모든사이트 목록
		List<Site> allSiteList = siteService.getSiteList(null);
		
		if(!"admin".equals(currentAdmin.getAdminId()) ){
			List<Site> authSiteList = new ArrayList<Site>();
			
			//관리자가 가지고 있는 권한의 사이트 목록
			for (Site site : allSiteList) {
				for (AdminSiteRoleRel rel : currentAdmin.getAdminSiteRoleRelList()) {
					if(site.getSitePrefix().equals(rel.getSitePrefix()) ){
						authSiteList.add(site);
					}
				}
			}
			model.addAttribute("allSiteList", authSiteList);
		} else {
			model.addAttribute("allSiteList", allSiteList);
		}
		//권한목록
		if(!currentAdmin.isSuperAdmin() ){
			List<Role> authRoleList = new ArrayList<Role>();
			
			//관리자가 가지고 있는 권한 목록
			for (Role role : allRoleList) {
				for (AdminSiteRoleRel rel : currentAdmin.getAdminSiteRoleRelList()) {
					if(StringUtils.isNotBlank(rel.getRoleCode()) ){
						for (String item : rel.getRoleCode().split(",") ) {
							if(role.getRoleCode().equals(item) ){
								authRoleList.add(role);
							}
						}
					}
				}
			}
			model.addAttribute("roleList", authRoleList);
		} else {
			model.addAttribute("roleList", allRoleList);
		}
		
		//코드목록
		this.loadCodes(model);		
		//기관목록
		this.loadOrganization(model);
		//부서목록
		if(StringUtils.isNotBlank(adminMemberFormModel.getAdminOrganization()) ){
			this.loadDepartment(model, adminMemberFormModel.getAdminOrganization());
		}
		//팀목록
		/*if(StringUtils.isNotBlank(adminMemberFormModel.getAdminDepartment()) ){
			this.loadTeam(model, adminMemberFormModel.getAdminDepartment());
		}*/
		
		//창업팀 목록
		if(StringUtils.isNotBlank(adminMemberFormModel.getAdminDepartment()) ){
			StartHpMngrSearch startHpMngrSearch = new StartHpMngrSearch();
			startHpMngrSearch.setPaging(false);
			startHpMngrSearch.setUnivId(adminMemberFormModel.getAdminDepartment());
			List<StartHpMngr> teamList = startHpMngrService.getStartHpMngrList(startHpMngrSearch);
			model.addAttribute("teamList", teamList);
		}
		
		return "asapro/admin/member/admin/form";
	}
	
	/**
	 * 관리자정보를 수정한다.
	 * @param model
	 * @param redirectAttributes
	 * @param adminMemberForm
	 * @param bindingResult
	 * @return 목록 뷰
	 * @throws FileNotFoundException 
	 */
	@PrivacyHistory(moduleType="회원관리", history="관리자 수정")
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/member/admin/update.do", method=RequestMethod.POST)
	public String updateAdminMemberPost(Model model, @CurrentAdmin AdminMember currentAdmin, RedirectAttributes redirectAttributes, @CurrentSite Site currentSite, @ModelAttribute("adminMemberForm") AdminMember adminMemberForm, BindingResult bindingResult) throws FileNotFoundException{
		if(StringUtils.isBlank(adminMemberForm.getAdminId()) ){
			LOGGER.info("[ASAPRO] AdminMemberController " + " : No ADMINID !!!");
			throw new AsaproNotFoundException("아이디가 전달되지 않았습니다.","back");
		}
		if("admin".equals(adminMemberForm.getAdminId()) ){
			LOGGER.info("[ASAPRO] AdminMemberController " + " : No authority !!!");
			throw new AsaproNoCapabilityException("수정할 수 없는 계정입니다.","back");
		}
		
		AdminMember adminMemberSearch = new AdminMember();
		adminMemberSearch.setSitePrefix(currentSite.getSitePrefix());
		adminMemberSearch.setAdminId(adminMemberForm.getAdminId());
		AdminMember adminMemberFormModel = adminMemberService.getAdminMember(adminMemberSearch);
		if(adminMemberFormModel == null ){
			LOGGER.info("[ASAPRO] AdminMemberController " + " : No ADMIN !!!");
			throw new AsaproNotFoundException("존제하지 않는 계정입니다.","back");
		}
		
		//최고관리자 이하의 조직별 관리레벨을 가진 계정의 제한처리
		if(!currentAdmin.isSuperAdmin() ){
			if(currentAdmin.getAdminManagementLevel() != null && currentAdmin.getAdminManagementLevel() > 0 ){
				if(currentAdmin.getAdminManagementLevel() >= 1 ){
					adminMemberForm.setAdminOrganization(currentAdmin.getAdminOrganization());
					if(!currentAdmin.getAdminOrganization().equals(adminMemberFormModel.getAdminOrganization()) ){
						LOGGER.info("[ASAPRO] AdminMemberController " + " : Not Same Organization !!!");
						throw new AsaproNoCapabilityException("수정권한이 없는 계정입니다.","back");
					}
				}
				if(currentAdmin.getAdminManagementLevel() >= 2 ){
					adminMemberForm.setAdminDepartment(currentAdmin.getAdminDepartment());
					if(!currentAdmin.getAdminDepartment().equals(adminMemberFormModel.getAdminDepartment()) ){
						LOGGER.info("[ASAPRO] AdminMemberController " + " : Not Same Department !!!");
						throw new AsaproNoCapabilityException("수정권한이 없는 계정입니다.","back");
					}
				}
				if(currentAdmin.getAdminManagementLevel() >= 3 ){
					if(StringUtils.isNotBlank(currentAdmin.getAdminTeam()) ){
						adminMemberForm.setAdminTeam(currentAdmin.getAdminTeam());
						if(!currentAdmin.getAdminTeam().equals(adminMemberFormModel.getAdminTeam()) ){
							LOGGER.info("[ASAPRO] AdminMemberController " + " : Not Same Team !!!");
							throw new AsaproNoCapabilityException("수정권한이 없는 계정입니다.","back");
						}
					}else{
						LOGGER.info("[ASAPRO] AdminMemberController " + " : No authority !!!");
						throw new AsaproNoCapabilityException("팀레벨 관리 권한이 있으나 팀에 소속되어 있지 않습니다.","back");
					}
				}
			} else {
				//조직별 관리권한이지만 레벨이 설정되지 않은경우 아무런 데이터도 보여주지 않는다
				LOGGER.info("[ASAPRO] AdminMemberController " + " : No authority !!!");
				throw new AsaproNoCapabilityException("관리 레벨이 부여 되지 않았습니다.","back");
			}
		}
		
		adminMemberValidator.validate(adminMemberForm, bindingResult, "UPDATE");
		if( bindingResult.hasErrors() ){
			model.addAttribute("formMode", "UPDATE");
			
			//역할목록
			RoleSearch roleSearch = new RoleSearch();
			roleSearch.setPaging(false);
			List<Role> allRoleList = roleService.getRoleList(roleSearch);
			
			if(!"admin".equals(currentAdmin.getAdminId()) ){
				
				List<Role> tempAllRoleList = new ArrayList<Role>();
				for (Role role : allRoleList) {
					if(!"ROLE_SUPER_ADMIN".equals(role.getRoleCode()) ){
						tempAllRoleList.add(role);
					}
				}
				allRoleList = tempAllRoleList;
			}
			
			//모든사이트 목록
			List<Site> allSiteList = siteService.getSiteList(null);
			
			if(!"admin".equals(currentAdmin.getAdminId()) ){
				List<Site> authSiteList = new ArrayList<Site>();
				
				//관리자가 가지고 있는 권한의 사이트 목록
				for (Site site : allSiteList) {
					for (AdminSiteRoleRel rel : currentAdmin.getAdminSiteRoleRelList()) {
						if(site.getSitePrefix().equals(rel.getSitePrefix()) ){
							authSiteList.add(site);
						}
					}
				}
				model.addAttribute("allSiteList", authSiteList);
			} else {
				model.addAttribute("allSiteList", allSiteList);
			}
			//권한목록
			if(!currentAdmin.isSuperAdmin() ){
				List<Role> authRoleList = new ArrayList<Role>();
				
				//관리자가 가지고 있는 권한 목록
				for (Role role : allRoleList) {
					for (AdminSiteRoleRel rel : currentAdmin.getAdminSiteRoleRelList()) {
						if(StringUtils.isNotBlank(rel.getRoleCode()) ){
							for (String item : rel.getRoleCode().split(",") ) {
								if(role.getRoleCode().equals(item) ){
									authRoleList.add(role);
								}
							}
						}
					}
				}
				model.addAttribute("roleList", authRoleList);
			} else {
				model.addAttribute("roleList", allRoleList);
			}
			
			//코드목록
			this.loadCodes(model);
			
			//기관목록
			this.loadOrganization(model);
			
			//부서목록
			if(StringUtils.isNotBlank(adminMemberForm.getAdminOrganization()) ){
				this.loadDepartment(model, adminMemberForm.getAdminOrganization());
			}
			//팀목록
			/*if(StringUtils.isNotBlank(adminMemberForm.getAdminDepartment()) ){
				this.loadTeam(model, adminMemberForm.getAdminDepartment());
			}*/
			
			//창업팀 목록
			if(StringUtils.isNotBlank(adminMemberForm.getAdminDepartment()) ){
				StartHpMngrSearch startHpMngrSearch = new StartHpMngrSearch();
				startHpMngrSearch.setPaging(false);
				startHpMngrSearch.setUnivId(adminMemberForm.getAdminDepartment());
				List<StartHpMngr> teamList = startHpMngrService.getStartHpMngrList(startHpMngrSearch);
				model.addAttribute("teamList", teamList);
			}
			
			model.addAttribute("updateFail", true);
			return "asapro/admin/member/admin/form";
		} else {
			adminMemberService.updateAdminMember(adminMemberForm);
			//return "/asapro/admin/member/admin/form";
			redirectAttributes.addFlashAttribute("updated", true);
			return "redirect:" + AsaproUtils.getAdminPath(currentSite, true) + "/member/admin/update.do?adminId=" + adminMemberForm.getAdminId();
		}
	}
	
	/**
	 * 관리자 비밀번호 변경폼을 반환한다.
	 * @param model
	 * @param adminPasswordForm
	 * @return
	 */
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/member/admin/password/update.do", method=RequestMethod.GET)
	public String updateAdminMemberPasswordGet(Model model, @CurrentAdmin AdminMember currentAdmin, @CurrentSite Site currentSite, @ModelAttribute("adminPasswordForm") AdminPassword adminPasswordForm){
		if(StringUtils.isBlank(adminPasswordForm.getAdminId()) ){
			LOGGER.info("[ASAPRO] AdminMemberController " + " : No ADMINID !!!");
			throw new AsaproNotFoundException("아이디가 전달되지 않았습니다.","back");
		}
		if("admin".equals(adminPasswordForm.getAdminId()) ){
			LOGGER.info("[ASAPRO] AdminMemberController " + " : No authority !!!");
			throw new AsaproNoCapabilityException("수정할 수 없는 계정입니다.","back");
		}
		
		AdminMember adminMemberSearch = new AdminMember();
		adminMemberSearch.setSitePrefix(currentSite.getSitePrefix());
		adminMemberSearch.setAdminId(adminPasswordForm.getAdminId());
		AdminMember adminMemberFormModel = adminMemberService.getAdminMember(adminMemberSearch);
		if(adminMemberFormModel == null ){
			LOGGER.info("[ASAPRO] AdminMemberController " + " : No ADMIN !!!");
			throw new AsaproNotFoundException("존제하지 않는 계정입니다.","back");
		}
		
		//최고관리자 이하의 조직별 관리레벨을 가진 계정의 제한처리
		if(!currentAdmin.isSuperAdmin() ){
			if(currentAdmin.getAdminManagementLevel() != null && currentAdmin.getAdminManagementLevel() > 0 ){
				if(currentAdmin.getAdminManagementLevel() >= 1 ){
					if(!currentAdmin.getAdminOrganization().equals(adminMemberFormModel.getAdminOrganization()) ){
						LOGGER.info("[ASAPRO] AdminMemberController " + " : Not Same Organization !!!");
						throw new AsaproNoCapabilityException("수정권한이 없는 계정입니다.","back");
					}
				}
				if(currentAdmin.getAdminManagementLevel() >= 2 ){
					if(!currentAdmin.getAdminDepartment().equals(adminMemberFormModel.getAdminDepartment()) ){
						LOGGER.info("[ASAPRO] AdminMemberController " + " : Not Same Department !!!");
						throw new AsaproNoCapabilityException("수정권한이 없는 계정입니다.","back");
					}
				}
				if(currentAdmin.getAdminManagementLevel() >= 3 ){
					if(StringUtils.isNotBlank(currentAdmin.getAdminTeam()) ){
						if(!currentAdmin.getAdminTeam().equals(adminMemberFormModel.getAdminTeam()) ){
							LOGGER.info("[ASAPRO] AdminMemberController " + " : Not Same Team !!!");
							throw new AsaproNoCapabilityException("수정권한이 없는 계정입니다.","back");
						}
					}else{
						LOGGER.info("[ASAPRO] AdminMemberController " + " : No authority !!!");
						throw new AsaproNoCapabilityException("팀레벨 관리 권한이 있으나 팀에 소속되어 있지 않습니다.","back");
					}
				}
			} else {
				//조직별 관리권한이지만 레벨이 설정되지 않은경우 아무런 데이터도 보여주지 않는다
				LOGGER.info("[ASAPRO] AdminMemberController " + " : No authority !!!");
				throw new AsaproNoCapabilityException("관리 레벨이 부여 되지 않았습니다.","back");
			}
		}
		
		
		return "asapro/admin/member/admin/passwordForm";
	}
	
	/**
	 * 관리자 비밀번호을 변경한다.
	 * @param model
	 * @param adminPasswordForm
	 * @param bindingResult
	 * @return
	 */
	@PrivacyHistory(moduleType="회원관리", history="관리자 비밀번호번경")
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/member/admin/password/update.do", method=RequestMethod.POST)
	public String updateAdminMemberPasswordPost(Model model, RedirectAttributes redirectAttributes, @CurrentAdmin AdminMember currentAdmin, @CurrentSite Site currentSite, @ModelAttribute("adminPasswordForm") AdminPassword adminPasswordForm, BindingResult bindingResult){
		if(StringUtils.isBlank(adminPasswordForm.getAdminId()) ){
			LOGGER.info("[ASAPRO] AdminMemberController " + " : No ADMINID !!!");
			throw new AsaproNotFoundException("아이디가 전달되지 않았습니다.","back");
		}
		if("admin".equals(adminPasswordForm.getAdminId()) ){
			LOGGER.info("[ASAPRO] AdminMemberController " + " : No authority !!!");
			throw new AsaproNoCapabilityException("수정할 수 없는 계정입니다.","back");
		}
		
		AdminMember adminMemberSearch = new AdminMember();
		adminMemberSearch.setSitePrefix(currentSite.getSitePrefix());
		adminMemberSearch.setAdminId(adminPasswordForm.getAdminId());
		AdminMember adminMemberFormModel = adminMemberService.getAdminMember(adminMemberSearch);
		if(adminMemberFormModel == null ){
			LOGGER.info("[ASAPRO] AdminMemberController " + " : No ADMIN !!!");
			throw new AsaproNotFoundException("존제하지 않는 계정입니다.","back");
		}
		
		//최고관리자 이하의 조직별 관리레벨을 가진 계정의 제한처리
		if(!currentAdmin.isSuperAdmin() ){
			if(currentAdmin.getAdminManagementLevel() != null && currentAdmin.getAdminManagementLevel() > 0 ){
				if(currentAdmin.getAdminManagementLevel() >= 1 ){
					if(!currentAdmin.getAdminOrganization().equals(adminMemberFormModel.getAdminOrganization()) ){
						LOGGER.info("[ASAPRO] AdminMemberController " + " : Not Same Organization !!!");
						throw new AsaproNoCapabilityException("수정권한이 없는 계정입니다.","back");
					}
				}
				if(currentAdmin.getAdminManagementLevel() >= 2 ){
					if(!currentAdmin.getAdminDepartment().equals(adminMemberFormModel.getAdminDepartment()) ){
						LOGGER.info("[ASAPRO] AdminMemberController " + " : Not Same Department !!!");
						throw new AsaproNoCapabilityException("수정권한이 없는 계정입니다.","back");
					}
				}
				if(currentAdmin.getAdminManagementLevel() >= 3 ){
					if(StringUtils.isNotBlank(currentAdmin.getAdminTeam()) ){
						if(!currentAdmin.getAdminTeam().equals(adminMemberFormModel.getAdminTeam()) ){
							LOGGER.info("[ASAPRO] AdminMemberController " + " : Not Same Team !!!");
							throw new AsaproNoCapabilityException("수정권한이 없는 계정입니다.","back");
						}
					}else{
						//adminMemberSearch.setTeamId("notauth");
						LOGGER.info("[ASAPRO] AdminMemberController " + " : No authority !!!");
						throw new AsaproNoCapabilityException("팀레벨 관리 권한이 있으나 팀에 소속되어 있지 않습니다.","back");
					}
				}
			} else {
				//조직별 관리권한이지만 레벨이 설정되지 않은경우 아무런 데이터도 보여주지 않는다
				//adminMemberForm.setAdminOrganization("notauth");
				LOGGER.info("[ASAPRO] AdminMemberController " + " : No authority !!!");
				throw new AsaproNoCapabilityException("관리 레벨이 부여 되지 않았습니다.","back");
			}
		}
		
		passwordValidator.validate(adminPasswordForm, bindingResult, "ADMIN");
		if(bindingResult.hasErrors()){
			return "asapro/admin/member/admin/passwordForm";
		} else {
			//마지막 비밀번호 변경일시 - 관리자가 비밀번호 변경할때는 1년 전으로 지정. 회원이 로그인할떄 비밀번호 바꾸도록 유도
			adminPasswordForm.setAdminPWLastUpdated(DateUtils.addYears(new Date(), -1));
			
			adminMemberService.updateAdminMemberPassword(adminPasswordForm);
			redirectAttributes.addFlashAttribute("updated", true);
			return "redirect:" + AsaproUtils.getAdminPath(currentSite, true) + "/member/admin/update.do?adminId=" + adminPasswordForm.getAdminId();
		}
	}
	
	/**
	 * 관리자를 삭제한다.
	 * @param model
	 * @param adminIds
	 * @return
	 */
	@PrivacyHistory(moduleType="회원관리", history="관리자 삭제")
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/member/admin/delete.do", method=RequestMethod.POST)
	@ResponseBody
	public ServerMessage deleteAdminMemberPost(Model model, @CurrentAdmin AdminMember currentAdmin, @CurrentSite Site currentSite, @RequestParam(value="adminIds[]", required=true) String[] adminIds){
		ServerMessage serverMessage = new ServerMessage();
		if( ArrayUtils.isEmpty(adminIds) ){
			serverMessage.setSuccess(false);
			serverMessage.setText("삭제할 항목이 없습니다.");
		} else {
			for( String adminId : adminIds ){
				//아이디가 admin 이면 최고관리자
				if( "admin".equals(adminId) ){
					return new ServerMessage(false, "최고관리자는 삭제할 수 없습니다.");
				}

				AdminMember adminMemberSearch = new AdminMember();
				adminMemberSearch.setAdminId(adminId);
				adminMemberSearch.setSitePrefix(currentSite.getSitePrefix());
				AdminMember fromDB = adminMemberService.getAdminMember(adminMemberSearch);
				
				
				//최고관리자 롤 계정
				if(fromDB != null && fromDB.isSuperAdmin() ){
					//'admin'계정 이외에는 삭제 권한이 없다.
					if(!"admin".equals(currentAdmin.getAdminId()) ){
						return new ServerMessage(false, "삭제권한이 없는 계정입니다.");
					}
				}
				
				//최고관리자 이하의 조직별 관리레벨을 가진 계정의 제한처리
				if(!currentAdmin.isSuperAdmin() ){
					if(currentAdmin.getAdminManagementLevel() != null && currentAdmin.getAdminManagementLevel() > 0 ){
						if(currentAdmin.getAdminManagementLevel() >= 1 ){
							if(!currentAdmin.getAdminOrganization().equals(fromDB.getAdminOrganization()) ){
								return new ServerMessage(false, "삭제권한이 없는 계정입니다.");
							}
						}
						if(currentAdmin.getAdminManagementLevel() >= 2 ){
							if(!currentAdmin.getAdminDepartment().equals(fromDB.getAdminDepartment()) ){
								return new ServerMessage(false, "삭제권한이 없는 계정입니다.");
							}
						}
						if(currentAdmin.getAdminManagementLevel() >= 3 ){
							if(StringUtils.isNotBlank(currentAdmin.getAdminTeam()) ){
								if(!currentAdmin.getAdminTeam().equals(fromDB.getAdminTeam()) ){
									return new ServerMessage(false, "삭제권한이 없는 계정입니다.");
								}
							}else{
								return new ServerMessage(false, "팀레벨 관리 권한이 있으나 팀에 소속되어 있지 않습니다.");
							}
						}
					} else {
						//조직별 관리권한이지만 레벨이 설정되지 않은경우 아무런 데이터도 보여주지 않는다
						return new ServerMessage(false, "관리 레벨이 부여 되지 않았습니다.");
					}
				}
				
			}
			
			int result = adminMemberService.deleteAdminMember(adminIds);
			if(result > 0){
				serverMessage.setSuccess(true);
				serverMessage.setSuccessCnt(result);
			} else {
				serverMessage.setSuccess(false);
			}
		}
		return serverMessage;
	}
	
	/**
	 * 관리자 아이디 중복을 확인한다.
	 * @param model
	 * @param adminId
	 * @return 중복여부
	 */
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/member/admin/checkAdminId.do", method=RequestMethod.GET)
	@ResponseBody
	public ServerMessage checkAdminIdGet(@RequestParam(value="adminId", required=true) String adminId){
		ServerMessage serverMessage = new ServerMessage();
		if( !StringUtils.isAlphanumeric(adminId) 
				|| !StringUtils.isAsciiPrintable(adminId)
				|| adminId.length() < 5
				|| adminId.length() > 20
				){
			serverMessage.setText(messageSource.getMessage("validation.member.memberId.unavailable", null, (Locale)request.getSession().getAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME)));
			serverMessage.setSuccess(false);
			return serverMessage;
		} 
		AdminMember fromDB = adminMemberService.getAdminMember(adminId);
		
		if(fromDB == null ){
			serverMessage.setSuccess(true);
		} else {
			serverMessage.setText(messageSource.getMessage("validation.member.memberId.duplicated", null, (Locale)request.getSession().getAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME)));
			serverMessage.setSuccess(false);
		}
		return serverMessage;
	}
	
	/**
	 * 관리자 상태를 변경합니다.
	 * @param adminMemberForm
	 * @return 수정결과
	 */
	@PrivacyHistory(moduleType="회원관리", history="관리자 상태변경")
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/member/admin/status/update.do", method=RequestMethod.POST)
	@ResponseBody
	public ServerMessage updateAdminMemberStatusPost(Model model, @CurrentAdmin AdminMember currentAdmin, @CurrentSite Site currentSite, @ModelAttribute("adminMemberForm") AdminMember adminMemberForm){
		ServerMessage serverMessage = new ServerMessage();
		if( StringUtils.isBlank(adminMemberForm.getAdminId()) ){
			serverMessage.setText("수정할 계정정보가 없습니다.");
			serverMessage.setSuccess(false);
			return serverMessage;
		} 
		//아이디가 admin 이면 최고관리자
		if( "admin".equals(adminMemberForm.getAdminId()) ){
			return new ServerMessage(false, "최고관리자는 수정할 수 없습니다.");
		}
		
		AdminMember adminMemberSearch = new AdminMember();
		adminMemberSearch.setAdminId(adminMemberForm.getAdminId());
		adminMemberSearch.setSitePrefix(currentSite.getSitePrefix());
		AdminMember fromDB = adminMemberService.getAdminMember(adminMemberSearch);
		
		//최고관리자 이하의 조직별 관리레벨을 가진 계정의 제한처리
		if(!currentAdmin.isSuperAdmin() ){
			if(currentAdmin.getAdminManagementLevel() != null && currentAdmin.getAdminManagementLevel() > 0 ){
				if(currentAdmin.getAdminManagementLevel() >= 1 ){
					if(!currentAdmin.getAdminOrganization().equals(fromDB.getAdminOrganization()) ){
						return new ServerMessage(false, "수정권한이 없는 계정입니다.");
					}
				}
				if(currentAdmin.getAdminManagementLevel() >= 2 ){
					if(!currentAdmin.getAdminDepartment().equals(fromDB.getAdminDepartment()) ){
						return new ServerMessage(false, "수정권한이 없는 계정입니다.");
					}
				}
				if(currentAdmin.getAdminManagementLevel() >= 3 ){
					if(StringUtils.isNotBlank(currentAdmin.getAdminTeam()) ){
						if(!currentAdmin.getAdminTeam().equals(fromDB.getAdminTeam()) ){
							return new ServerMessage(false, "수정권한이 없는 계정입니다.");
						}
					}else{
						return new ServerMessage(false, "팀레벨 관리 권한이 있으나 팀에 소속되어 있지 않습니다.");
					}
				}
			} else {
				//조직별 관리권한이지만 레벨이 설정되지 않은경우 아무런 데이터도 보여주지 않는다
				return new ServerMessage(false, "관리 레벨이 부여 되지 않았습니다.");
			}
		}
		
		int result = adminMemberService.updateAdminMemberStatus(adminMemberForm);
		
		if(result > 0 ){
			serverMessage.setSuccess(true);
			serverMessage.setSuccessCnt(result);
		} else {
			serverMessage.setText("상태 수정 실패");
			serverMessage.setSuccess(false);
		}
		return serverMessage;
	}
	
	
	/**
	 * 관리자 검색 팝업창 뷰를 반환한다.
	 * @param model
	 * @param adminMemberForm
	 * @return 관리자 검색 팝업 뷰
	 */
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/member/admin/searchLayer.do", method=RequestMethod.GET)
	public String adminMemberSearchLayerGet(Model model, @CurrentSite Site currentSite, @ModelAttribute("adminMemberSearch") AdminMemberSearch adminMemberSearch){
		
		//기본 정렬 순서
		//if(StringUtils.isBlank(memberSearch.getSortOrder())){
		//	memberSearch.setSortOrder("MEM_REGDATE");
		//	memberSearch.setSortDirection("DESC");
		//}
		
		adminMemberSearch.fixBrokenSvByDefaultCharsets();
		
		int total = adminMemberService.getAdminMemberListTotal(adminMemberSearch);
		List<AdminMember> list = adminMemberService.getAdminMemberList(adminMemberSearch);
		Paging paging = new Paging(list, total, adminMemberSearch);
		model.addAttribute("paging", paging);
		
		//역할목록
		RoleSearch roleSearch = new RoleSearch();
		roleSearch.setPaging(false);
		model.addAttribute("roleList", roleService.getRoleList(roleSearch));
		
		//모든사이트 목록
		List<Site> allSiteList = siteService.getSiteList(null);
		model.addAttribute("allSiteList", allSiteList);
		
//				model.addAttribute("groupList", groupService.getGroupList(null));
		
		//기관목록
		this.loadOrganization(model);
		
		//부서목록
		if(StringUtils.isNotBlank(adminMemberSearch.getOrgId()) ){
			this.loadDepartment(model, adminMemberSearch.getOrgId());
		}
		
		//직급/직책 목록
		model.addAttribute("positionList", codeService.getCodeList("position"));
		return "asapro/admin/member/admin/searchLayer";
	}
	
	
	//==============================================================================================================
	//=====================================  관리자 개인정보 수정  =================================================
	//==============================================================================================================
	
	
	/**
	 * 관리자 프로파일 수정폼 뷰를 반환한다.
	 * @param model
	 * @param adminMemberForm
	 * @return 관리자 프로파일 수정폼 뷰
	 */
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/member/admin/profile/update.do", method=RequestMethod.GET)
	public String updateAdminMemberProfileGet(Model model, @CurrentAdmin AdminMember currentAdmin, @CurrentSite Site currentSite, @ModelAttribute("adminMemberForm") AdminMember adminMemberForm){
		model.addAttribute("formMode", "UPDATE");
		
		adminMemberForm.setSitePrefix(currentSite.getSitePrefix());
		//현제 로그인 한 관리자의 아이디를 셋트한다. 파라미터로 넘겨 받을경우 조작의 우려가 있고 별도 확인 절차가 있어야 하므로 서버단에서 셋트
		adminMemberForm.setAdminId(currentAdmin.getAdminId());
		AdminMember adminMemberFormModel = adminMemberService.getAdminMember(adminMemberForm);
		model.addAttribute("adminMemberForm", adminMemberFormModel);
		
		//코드목록
		this.loadCodes(model);		
		
		return "asapro/admin/member/admin/profileForm";
	}
	
	/**
	 * 관리자 프로파일을 수정한다.
	 * @param model
	 * @param redirectAttributes
	 * @param adminMemberForm
	 * @param bindingResult
	 * @return 수정결과
	 * @throws FileNotFoundException 
	 */
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/member/admin/profile/update.do", method=RequestMethod.POST)
	public String updateAdminMemberProfilePost(Model model, @CurrentAdmin AdminMember currentAdmin, RedirectAttributes redirectAttributes, @CurrentSite Site currentSite, @ModelAttribute("adminMemberForm") AdminMember adminMemberForm, BindingResult bindingResult) throws FileNotFoundException{
		//현제 로그인 한 관리자의 아이디를 셋트한다. 파라미터로 넘겨 받을경우 조작의 우려가 있고 별도 확인 절차가 있어야 하므로 서버단에서 셋트
		adminMemberForm.setAdminId(currentAdmin.getAdminId());
		
		adminMemberValidator.validate(adminMemberForm, bindingResult, "PROFILE");
		if( bindingResult.hasErrors() ){
			model.addAttribute("formMode", "UPDATE");
			
			//코드목록
			this.loadCodes(model);
			
			return "asapro/admin/member/admin/profileForm";
		} else {
			adminMemberService.updateAdminMemberProfile(adminMemberForm);
			redirectAttributes.addFlashAttribute("updated", true);
			return "redirect:" + AsaproUtils.getAdminPath(currentSite, true) + "/member/admin/profile/update.do";
		}
	}
	
	/**
	 * 관리자 프로파일 비밀번호 변경폼을 반환한다.
	 * @param model
	 * @param adminPasswordForm
	 * @return 비밀번호 변경폼
	 */
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/member/admin/profile/password/update.do", method=RequestMethod.GET)
	public String updateAdminMemberProfilePasswordGet(Model model, @CurrentAdmin AdminMember currentAdmin, @ModelAttribute("adminPasswordForm") AdminPassword adminPasswordForm){
		//현제 로그인 한 관리자의 아이디를 셋트한다. 파라미터로 넘겨 받을경우 조작의 우려가 있고 별도 확인 절차가 있어야 하므로 서버단에서 셋트
		adminPasswordForm.setAdminId(currentAdmin.getAdminId());
		return "asapro/admin/member/admin/prifilePasswordForm";
	}
	
	/**
	 * 관리자 프로파일 비밀번호을 변경한다.
	 * @param model
	 * @param adminPasswordForm
	 * @param bindingResult
	 * @return 수정결과
	 */
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/member/admin/profile/password/update.do", method=RequestMethod.POST)
	public String updateAdminMemberProfilePasswordPost(Model model, @CurrentAdmin AdminMember currentAdmin, RedirectAttributes redirectAttributes, @CurrentSite Site currentSite, @ModelAttribute("adminPasswordForm") AdminPassword adminPasswordForm, BindingResult bindingResult){
		//현제 로그인 한 관리자의 아이디를 셋트한다. 파라미터로 넘겨 받을경우 조작의 우려가 있고 별도 확인 절차가 있어야 하므로 서버단에서 셋트
		adminPasswordForm.setAdminId(currentAdmin.getAdminId());
		
		passwordValidator.validate(adminPasswordForm, bindingResult, "ADMIN");
		if(bindingResult.hasErrors()){
			return "asapro/admin/member/admin/prifilePasswordForm";
		} else {
			//마지막 비밀번호 변경일시 - 본인이 비밀번호 변경할때는 현제 일시로 지정
			adminPasswordForm.setAdminPWLastUpdated(new Date());
			
			adminMemberService.updateAdminMemberPassword(adminPasswordForm);
			redirectAttributes.addFlashAttribute("updated", true);
			return "redirect:" + AsaproUtils.getAdminPath(currentSite, true) + "/member/admin/profile/update.do";
		}
	}
	
}
