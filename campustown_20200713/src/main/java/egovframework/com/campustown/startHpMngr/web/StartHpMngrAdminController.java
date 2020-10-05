package egovframework.com.campustown.startHpMngr.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import egovframework.com.asapro.code.service.CodeService;
import egovframework.com.asapro.fileinfo.service.FileInfo;
import egovframework.com.asapro.fileinfo.service.FileInfoService;
import egovframework.com.asapro.fileinfo.service.FileInfoUploadResult;
import egovframework.com.asapro.member.admin.service.AdminMember;
import egovframework.com.asapro.member.admin.service.AdminMemberSearch;
import egovframework.com.asapro.member.admin.service.AdminMemberService;
import egovframework.com.asapro.organization.service.Department;
import egovframework.com.asapro.organization.service.DepartmentSearch;
import egovframework.com.asapro.organization.service.OrganizationService;
import egovframework.com.asapro.role.service.RoleSearch;
import egovframework.com.asapro.role.service.RoleService;
import egovframework.com.asapro.site.service.Site;
import egovframework.com.asapro.site.service.SiteService;
import egovframework.com.asapro.support.Constant;
import egovframework.com.asapro.support.ServerMessage;
import egovframework.com.asapro.support.annotation.CurrentAdmin;
import egovframework.com.asapro.support.annotation.CurrentSite;
import egovframework.com.asapro.support.exception.AsaproException;
import egovframework.com.asapro.support.pagination.Paging;
import egovframework.com.asapro.support.util.AsaproUtils;
import egovframework.com.campustown.startHpMngr.service.StartHpMngr;
import egovframework.com.campustown.startHpMngr.service.StartHpMngrSearch;
import egovframework.com.campustown.startHpMngr.service.StartHpMngrService;
import egovframework.com.campustown.univHpMngr.service.UnivHpMngr;
import egovframework.com.campustown.univHpMngr.service.UnivHpMngrSearch;
import egovframework.com.campustown.univHpMngr.service.UnivHpMngrService;

/**
 * 창업팀 홈페이지 관리 관리자 컨트롤러 
 * @author jaseo
 * @since 2020. 02. 11.
 */
@Controller
public class StartHpMngrAdminController {
	
	@Autowired
	private CodeService codeService;
	
	@Autowired
	private OrganizationService organizationService;
	
	@Autowired
	private FileInfoService fileInfoService;
	
	@Autowired
	private HttpServletRequest request;
	
	@Autowired
	private StartHpMngrService startHpMngrService;
	
	@Autowired
	private StartHpMngrValidator startHpMngrValidator;
	
	@Autowired
	private UnivHpMngrService univHpMngrService;
	
	@Autowired
	private AdminMemberService adminMemberService;
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private SiteService siteService;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(StartHpMngrAdminController.class);
	
	
	/**
	 * 코드목록 로드
	 * @param model
	 */
	private void loadCodes(Model model, String compId){
		
		model.addAttribute("sigunguCodeList", codeService.getCodeList("SIGUNGU_CODE", "CODE_NAME", "ASC")); // 지역 정보(서울시 구)
		model.addAttribute("indutyCodeList",  codeService.getCodeList("INDUTY_CODE"));			  // 업종 코드
		model.addAttribute("bsnsRealmCodeList",  codeService.getCodeList("BSNS_REALM_CODE"));	  // 사업분야 코드 	
		model.addAttribute("intrstRealmCodeList",  codeService.getCodeList("INTRST_REALM_CODE")); // 관심분야 코드
		model.addAttribute("tel1CodeList", codeService.getCodeList("tel_area_code"));			  // 일반전화 앞자리	
		model.addAttribute("mobile1CodeList", codeService.getCodeList("mobile1_code"));			  // 휴대폰번호 앞자리
		model.addAttribute("emailDomainCodeList", codeService.getCodeList("EMAIL_DOMAIN"));		  // 이메일 도메인
		
		// 관리자목록
		if(compId != null){
			AdminMemberSearch adminMemberSearch = new AdminMemberSearch();
			adminMemberSearch.setPaging(false);
			adminMemberSearch.setTeamId(compId);
			List<AdminMember> adminMemberList = adminMemberService.getAdminMemberList(adminMemberSearch);
			model.addAttribute("adminMemberList", adminMemberList);
		}
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
		//System.out.println("departmentList.size(): " + departmentList.size());
		//System.out.println("departmentList.size(): " + departmentList.get(0).getDepName());
		model.addAttribute("departmentList", departmentList);
	}
	
	
	/**
	 * 부서목록 중 특정 데이타값 로드
	 * @param model
	 * @param orgId
	 * @param depId
	 */
	/*
	private void loadDepartment(Model model, String orgId, String depId){
		//부서목록
		DepartmentSearch departmentSearch = new DepartmentSearch();
		departmentSearch.setPaging(false);
		departmentSearch.setDepUse(true);
		departmentSearch.setOrgId(orgId);
		departmentSearch.setDepId(depId);
		
		List<Department> departmentList = organizationService.getDepartmentList(departmentSearch);
		//System.out.println("departmentList.size(): " + departmentList.size());
		//System.out.println("departmentList.size(): " + departmentList.get(0).getDepName());
		if(departmentList.size() > 0){
			model.addAttribute("department", departmentList.get(0));
		}
	}
	*/
	
	/**
	 * 창업팀 홈페이지 관리 목록을 반환한다.
	 * @param model
	 * @param currentSite
	 * @param currentAdmin
	 * @param startHpMngrSearch
	 * @return
	 */
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/startHpMngr/list.do", method=RequestMethod.GET)
	public String startHpMngrInfoListGet(Model model
			, @CurrentSite Site currentSite
			, @CurrentAdmin AdminMember currentAdmin
			, @ModelAttribute("startHpMngrSearch") StartHpMngrSearch startHpMngrSearch
			) {
		
		startHpMngrSearch.setSitePrefix(currentSite.getSitePrefix());
		startHpMngrSearch.fixBrokenSvByDefaultCharsets();
		startHpMngrSearch.setPaging(true);
		
		
		/* 0. 코드 목록 로드 */
		this.loadCodes(model, null);   // sigunguCodeList
		this.loadDepartment(model, "university"); // departmentList
		
		
		
		/* 1. 지역 셋팅 (서울시 대학의 지역(구) 기준) */
		
		// 1. 관리자인 경우
		// 역할이 : ROLE_SUPER_ADMIN, ROLE_NORMAL_ADMIN, ROLE_SCT_ADMIN 이면
		// ===> 전체 목록 표출(셋팅 없음!) 
//		if(currentAdmin.getAdminRole().getRoleCode().equals("ROLE_SUPER_ADMIN") ||
//		   currentAdmin.getAdminRole().getRoleCode().equals("ROLE_NORMAL_ADMIN") ||
//		   currentAdmin.getAdminRole().getRoleCode().equals("ROLE_SCT_ADMIN"))
//		{
			
		// 2. 대학 관계자인 경우
		// 해당 대학의 depId로 데이터 조회해서 지역정보 코드 가져와서 셋팅해주기.	
//		}else
			
		if(currentAdmin.getAdminOrganization().equals("university")){
		
			String depId = currentAdmin.getAdminDepartment();
			UnivHpMngrSearch univHpMngrSearch = new UnivHpMngrSearch();
			univHpMngrSearch.setUnivId(depId);
			
			UnivHpMngr univHpMngr = univHpMngrService.getUnivHpMngrInfo(univHpMngrSearch);
			
			//System.out.println("univHpMngr == null ? "+univHpMngr);
		
			if(univHpMngr != null){
				// 창업팀 db의 대학 지역(구) 컬럼에 univHpMngr.getAreaGuCd() 값 셋팅 처리!
				startHpMngrSearch.setAreaId(univHpMngr.getAreaGuCd());
				model.addAttribute("srcAreacode", univHpMngr.getAreaGuCd());
			}
			
			
		// 3. 창업팀인 경우
		// 해당 창업팀의 depId로 데이터 조회해서 대학의 지역(구)정보 코드 가져와서 셋팅해주기.	
			if(!"".equals(currentAdmin.getAdminTeam()) && null != currentAdmin.getAdminTeam()){
				String teamId = currentAdmin.getAdminTeam();
				StartHpMngrSearch startHpMngrSearch2 = new StartHpMngrSearch();
				startHpMngrSearch2.setCompId(teamId);
				
				StartHpMngr startHpMngr = startHpMngrService.getStartHpMngrCompInfo(startHpMngrSearch2);
				
				if(startHpMngr != null){
					startHpMngrSearch.setAreaId(startHpMngr.getAreaGuCd());
					model.addAttribute("srcAreacode", startHpMngr.getAreaGuCd());
				} 
				
			}
		}
		
//		else if(currentAdmin.getAdminOrganization().equals("startup")){
//			
//			String depId = currentAdmin.getAdminDepartment();
//			StartHpMngrSearch startHpMngrSearch2 = new StartHpMngrSearch();
//			startHpMngrSearch2.setCompId(depId);
//			
//			StartHpMngr startHpMngr = startHpMngrService.getStartHpMngrCompInfo(startHpMngrSearch2);
//			//System.out.println("startHpMngr == null ? "+startHpMngr);
//			
//			if(startHpMngr != null){
//				this.loadCodes(model);  // sigunguCodelist
//				startHpMngrSearch.setAreaId(startHpMngr.getAreaGuCd());
//				model.addAttribute("srcAreacode", startHpMngr.getAreaGuCd());
//			} else {
//				this.loadCodes(model);  // sigunguCodelists
//			}
//		}
		
		
		
		/* 2. 대학 셋팅 */
		
		// 1. 관리자인 경우
		// ===> 전체 목록 표출(셋팅 없음!)
//		if(currentAdmin.getAdminRole().getRoleCode().equals("ROLE_SUPER_ADMIN") ||
//		   currentAdmin.getAdminRole().getRoleCode().equals("ROLE_NORMAL_ADMIN") ||
//		   currentAdmin.getAdminRole().getRoleCode().equals("ROLE_SCT_ADMIN"))
//		{
//			this.loadDepartment(model, "university"); // 대학 부서목록 로드
			
		// 2. 대학인 경우
		// 기관이 university 이면 해당 대학의 부서 아이디로 셋팅해주기.	
//		}else 
			
		if(currentAdmin.getAdminOrganization().equals("university")){
			startHpMngrSearch.setUnivId(currentAdmin.getAdminDepartment());
			//model.addAttribute("srcUnivId", currentAdmin.getAdminDepartment());
			
			
		// 3. 창업팀인 경우
		// 기관이 startup 이면 현재 접속한 관리자의 부서 아이디로 데이터를 조회해
		// 조회한 데이터에서 대학코드 정보 가져와 셋팅해주기.(univId)	
			if(!"".equals(currentAdmin.getAdminTeam()) && null != currentAdmin.getAdminTeam()){
				String teamId = currentAdmin.getAdminTeam();
				StartHpMngrSearch startHpMngrSearch3 = new StartHpMngrSearch();
				startHpMngrSearch3.setCompId(teamId);
				
				StartHpMngr startHpMngr = startHpMngrService.getStartHpMngrCompInfo(startHpMngrSearch3);
				//System.out.println("startHpMngr == null ? "+startHpMngr);
				
				if(startHpMngr != null){
					startHpMngrSearch.setUnivId(startHpMngr.getUnivId());
					//model.addAttribute("srcUnivId", startHpMngr.getUnivId());
				} 
			}
		}
		
//		else if(currentAdmin.getAdminOrganization().equals("startup")){
//			
//			String depId = currentAdmin.getAdminDepartment();
//			StartHpMngrSearch startHpMngrSearch3 = new StartHpMngrSearch();
//			startHpMngrSearch3.setCompId(depId);
//			
//			StartHpMngr startHpMngr = startHpMngrService.getStartHpMngrCompInfo(startHpMngrSearch3);
//			//System.out.println("startHpMngr == null ? "+startHpMngr);
//			
//			if(startHpMngr != null){
//				this.loadDepartment(model, "university"); // 대학 부서목록 로드
//				startHpMngrSearch.setUnivId(startHpMngr.getUnivId());
//				//model.addAttribute("srcUnivId", startHpMngr.getUnivId());
//			} else {
//				this.loadDepartment(model, "university"); // 대학 부서목록 로드
//			}
//		}
				
		
		
		
		/* 3. 창업팀 셋팅 */
		
		// 1. 관리자인 경우
		// ===> 전체 목록 표출(셋팅 없음!)
//		if(currentAdmin.getAdminRole().getRoleCode().equals("ROLE_SUPER_ADMIN") ||
//		   currentAdmin.getAdminRole().getRoleCode().equals("ROLE_NORMAL_ADMIN") ||
//		   currentAdmin.getAdminRole().getRoleCode().equals("ROLE_SCT_ADMIN"))
//		{
		
		// 2. 대학인 경우
		// 기관이 university 이면 현재 사용자의 대학 부서 ID를 가져온 후
		// 현재 대학 부서 ID로 존재하는 창업팀 리스트 코드정보 가져오기!
//		}else 
		if(currentAdmin.getAdminOrganization().equals("university")){
			String depId = currentAdmin.getAdminDepartment();
			startHpMngrSearch.setUnivId(depId);
				
			
		// 3. 창업팀인 경우
		// 기관이 startup 이면 현재 접속한 관리자의 부서 아이디로 창업팀 셋팅해주기.
			
			if(!"".equals(currentAdmin.getAdminTeam()) && null != currentAdmin.getAdminTeam()){
				String teamId = currentAdmin.getAdminTeam();
				startHpMngrSearch.setCompId(teamId);
			}
		}
		
//		else if(currentAdmin.getAdminOrganization().equals("startup")){
//			
//			this.loadDepartment(model, "startup"); // 창업팀 부서목록 로드
//			startHpMngrSearch.setCompId(currentAdmin.getAdminDepartment());
//			//model.addAttribute("srcCompId", currentAdmin.getAdminDepartment());
//		}
		
		model.addAttribute("org", currentAdmin.getAdminOrganization());
		
		
		// 창업팀 유무
		String teamYn = "N";
		//System.out.println("teamId: " + currentAdmin.getAdminTeam());
		if(null != currentAdmin.getAdminTeam() && !"".equals(currentAdmin.getAdminTeam())){
			teamYn = "Y";
		}
		model.addAttribute("teamYn", teamYn);
		
		List<StartHpMngr> list = startHpMngrService.getStartHpMngrList(startHpMngrSearch);
		int total = startHpMngrService.getStartHpMngrListTotal(startHpMngrSearch);
		
		//System.out.println("size : " + list.size());
		//System.out.println("total : " + total);
		
		Paging paging = new Paging(list, total, startHpMngrSearch);
		model.addAttribute("paging", paging);
		
		return "asapro/admin/campustown/startHpMngr/startHpMngrList";
	}
	
	
	
	
	
	/**
	 * 창업 홈페이지 관리 추가폼 뷰를 반환한다.
	 * @param model
	 * @param currentSite
	 * @param currentAdmin
	 * @param startHpMngrForm
	 * @param startHpMngrSearch
	 * @return
	 */
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/startHpMngr/compInfo/insert.do", method=RequestMethod.GET)
	public String insertStartHpMngrInfoGet(Model model
			, @CurrentSite Site currentSite
			, @CurrentAdmin AdminMember currentAdmin
			, @ModelAttribute("startHpMngrForm") StartHpMngr startHpMngrForm
			, @ModelAttribute("startHpMngrSearch") StartHpMngrSearch startHpMngrSearch
			) {
		
		model.addAttribute("formMode", "INSERT");
		this.loadCodes(model, null);   // sigunguCodeList
		this.loadDepartment(model, "university"); // 대학 부서목록 로드
		
		/* 1. 대학 셋팅 (JSP 화면에서) */
		
		// 1. 관리자인 경우
			// 1) [대학홈페이지 등록 체크] 
			// 1순위가 대학정보 등록이므로 해당 대학이 대학 홈페이지관리에 등록된 정보인지 먼저 체크
			//   => 'Y'인 경우,  창업팀 등록 진행 (UNIVERSITY_INFO.size() > 0) 
		    //   => 'N'인 경우, 다른 대학 선택하게 유도하거나 해당 대학 대학 홈페이지관리에서 등록하도록 유도

		// ★ 2. 대학 관계자인 경우
			// 0) 해당 로그인 계정 대학 정보 셋팅
			// 1) [대학홈페이지 등록 체크]
		    // 1순위가 대학정보 등록이므로 해당 대학이 대학 홈페이지관리에 등록된 정보인지 먼저 체크
			//	 => 'Y'인 경우,  창업팀 등록 진행 (UNIVERSITY_INFO.size() > 0)
			//	 => 'N'인 경우,  대학 홈페이지관리에서 등록 alert 띄운 후 해당 페이지로 redirect 시키기! 
		
		// 3. 창업팀인 경우
			// 1) [대학홈페이지 등록 체크]
			// 1순위가 대학정보 등록이므로 해당 대학이 대학 홈페이지관리에 등록된 정보인지 먼저 체크
			//   => 'Y'인 경우,  창업팀 등록 진행 (UNIVERSITY_INFO.size() > 0)
			//   => 'N'인 경우, 해당 대학에 먼저 대학홈페이지 등록 요청하기!
		
			// 2) [데이터 등록 후 등록버튼 숨기기] 
		    // 데이터 등록되면 더 이상 등록 불가하게 등록 버튼 숨김 처리! (대학:창업팀 <=> 1:N)
		
		
		// 2. 0) 대학 정보 셋팅
		
		String depId = currentAdmin.getAdminDepartment();
		UnivHpMngrSearch univHpMngrSearch = new UnivHpMngrSearch();
		univHpMngrSearch.setUnivId(depId);
		
		if(currentAdmin.getAdminOrganization().equals("university")){
			model.addAttribute("sUnivId" , univHpMngrSearch.getUnivId());
		}
		
		
		// 2. 1) [대학홈페이지 등록 체크] 
		
		
		UnivHpMngr univHpMngr = univHpMngrService.getUnivHpMngrInfo(univHpMngrSearch);
		
		//System.out.println("univHpMngr == null ? "+univHpMngr);
		
		if(univHpMngr != null){
			model.addAttribute("univDataYn", "Y");
		}else{
			
			model.addAttribute("univDataYn", "N");
		}
		
		
		
		
		/* 2. 창업팀 셋팅(회사명) (JSP 화면에서) */
		
		// 1. 관리자인 경우
			// 1) [중복 등록 체크] (대학:창업팀 <=> 1:N)
			// 대학홈페이지 관리에 정보 등록한 대학 선택 후 
		    // 매칭 시키고 싶은 창업팀을 선택했을 때 해당 창업팀이 이미 매칭됬는지 중복 체크!
			//	=> 'Y'인 경우, 다른 창업팀 선택하도록 처리 (STARTUP_COMPANY_INFO.size() > 0)
			//  => 'N'인 경우, 다음 과정 진행

		// 2. 대학 관계자인 경우
			// 1) [중복 등록 체크] (대학:창업팀 <=> 1:N)
			// 대학홈페이지 관리에 정보 등록한 대학 선택 후 
		    // 매칭 시키고 싶은 창업팀을 선택했을 때 해당 창업팀이 이미 매칭됬는지 중복 체크!
			
		
		
		//★ 3. 창업팀인 경우
			// 0) 해당 로그인 계정 창업팀 정보 셋팅
			// 1) [중복 등록 체크] (대학:창업팀 <=> 1:N)
			// 창업팀이 로그인해서 처음 리스트 정보에서 어떤 데이터도 조회되지 않으면 
			// 등록 가능하기 때문에 중복 체크 필요 없음!!!

		
		
		// 로그인 계정 기관정보
		model.addAttribute("org", currentAdmin.getAdminOrganization());
		
		// 로그인 계정 부서 정보 로드
		
		return "asapro/admin/campustown/startHpMngr/startHpMngrForm";
	}
	
	
	
	/**
	 * 창업팀 부서목록을 레이어 팝업창 뷰로 반환한다. => 현재 안 쓰임!
	 * @param model
	 * @param currentSite
	 * @param currentAdmin
	 * @param departmentSearch
	 * @return 부서목록 뷰
	 */
	/*
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/startHpMngr/searchStartLayer.do", method=RequestMethod.GET)
	public String departmentListSearchLayerGet(Model model, @CurrentSite Site currentSite, @CurrentAdmin AdminMember currentAdmin, @ModelAttribute("departmentSearch") DepartmentSearch departmentSearch){
		
		departmentSearch.fixBrokenSvByDefaultCharsets();
		departmentSearch.setPaging(true);
		departmentSearch.setOrgId("startup");
		model.addAttribute("srcOrgId", departmentSearch.getOrgId());
		
		List<Department> list = organizationService.getDepartmentList(departmentSearch);
		int total = organizationService.getDepartmentListTotal(departmentSearch);
		Paging paging = new Paging(list, total, departmentSearch);
		
		model.addAttribute("paging", paging);
		
		//사용중인 기관목록
		OrganizationSearch organizationSearch = new OrganizationSearch();
		organizationSearch.setPaging(true);
		//organizationSearch.setOrgUse(true);
		organizationSearch.setDefaultSort("ORG_NAME", "ASC");
		
		List<Organization> organizationList = organizationService.getOrganizationList(organizationSearch);
		
		model.addAttribute("organizationList", organizationList);
		
		return "asapro/admin/campustown/startHpMngr/searchStartLayer";
	}
	*/
	
	
	/**
	 * 회사 ID 등록 중복 체크 => 현재 안 쓰임!
	 * @param model
	 * @param compId
	 * @return
	 */
	/*
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/startHpMngr/idCheck/jsonResultGet.do", method=RequestMethod.GET)
	@ResponseBody
	public Integer compIdCheckJsonResultGet(Model model
							, @RequestParam(value="compId", required=true) String compId
							, @RequestParam(value="univId", required=true) String univId){
		
		//System.out.println("UnivHpMngrAdminController.java.univIdCheckJsonResultGet()");
		//System.out.println("compId: " + compId);
		int result = 0;
		
		// 대학:창업팀 <=> 1:N 이고
		// 창업팀은 한 대학에만 소속되야 한다. (Unique! compId is Primary Key.)
		// 따라서 창업팀이 이미 대학에 소속되어 있으면 중복 데이타로 처리.
		// => compId로 조회해서 있으면 중복!
		
		StartHpMngrSearch startHpMngrSearch = new StartHpMngrSearch();
		startHpMngrSearch.setCompId(compId);
		//startHpMngrSearch.setUnivId(univId);
		StartHpMngr startHpMngr = startHpMngrService.getStartHpMngrCompInfo(startHpMngrSearch);
		
		 
		
		if(null != startHpMngr){
			result = 1;
			//System.out.println("ID (○) : ID 중복.");
			model.addAttribute("compId", compId);
			// 기존에 univId까지 검색대상으로 넣어서 jsp파일까지 처리해서(실수) 꼬이지 않게 검색 후 set 처리함.
			model.addAttribute("univId", univId);
		}else{
			result = 0;
			//System.out.println("ID (×) : ID 등록 가능!");
		}
		
		return result;
	}	
	*/
	
	/**
	 * 창업 홈페이지 관리 추가폼 뷰를 등록한다.
	 * @param model
	 * @param currentSite
	 * @param currentAdmin
	 * @param startHpMngrForm
	 * @param startHpMngrSearch
	 * @param bindingResult
	 * @return
	 * @throws AsaproException
	 * @throws IOException
	 */
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/startHpMngr/compInfo/insert.do", method=RequestMethod.POST)
	public String insertStartHpMngrInfoPost(Model model
			, @CurrentSite Site currentSite
			, @CurrentAdmin AdminMember currentAdmin
			, @ModelAttribute("startHpMngrForm") StartHpMngr startHpMngrForm
			, @ModelAttribute("startHpMngrSearch") StartHpMngrSearch startHpMngrSearch
			, BindingResult bindingResult
			) throws AsaproException, IOException{
		
		startHpMngrValidator.validate(startHpMngrForm, bindingResult, "INSERT");
		
		if(bindingResult.hasErrors() ){
			model.addAttribute("formMode", "INSERT");
			return "asapro/admin/campustown/startHpMngr/startHpMngrForm";
		} else {
			
			FileInfoUploadResult fileInfoUploadResult = new FileInfoUploadResult();
			
			//첨부가능한 확장자
			String uploadWhiteList = Constant.UPLOAD_IMAGE_WHITE_LIST;
			
			String webRoot = AsaproUtils.getWebRoot(request);
			//첨부된 파일 
			FileInfo fileInfo = new FileInfo();
			fileInfo.setSitePrefix(currentSite.getSitePrefix());
			fileInfo.setFileModuleId(startHpMngrForm.getSitePrefix());
			
			//멤버
			fileInfo.setMemberId(currentAdmin.getAdminId());
			//첨부유형
			fileInfo.setFileAttachmentType("thumbnail");
			fileInfo.setFileModule("startHpMngr");
			
			
			//이미지 처리
			if( startHpMngrForm.getFile1() != null && startHpMngrForm.getFile1().getSize() > 0 ){
				
				//대체텍스트
				if(StringUtils.isNotBlank(startHpMngrForm.getFile1AltText()) ){
					fileInfo.setFileAltText(startHpMngrForm.getFile1AltText() );
				}else{
					//fileInfo.setFileAltText(univHpMngrForm.getFile1().getOriginalFilename() );
					fileInfo.setFileAltText(startHpMngrForm.getCompNm()+"의 대표 이미지");
					startHpMngrForm.setFile1AltText(startHpMngrForm.getCompNm()+"의 대표 이미지");
				}
				
				//썸네일 파일도 만든다
				fileInfoService.saveFile(startHpMngrForm.getFile1(), webRoot, fileInfo, (Constant.MEGA * 10), true, 200, 60, false, uploadWhiteList);  //대표 이미지 모두 업로드
				if( !fileInfo.isFileUploadSuccess() ){
					fileInfoUploadResult.addErrorInfo(fileInfo);
				}else{
					startHpMngrForm.setFile1Info(fileInfo);
					startHpMngrForm.setFileId1(String.valueOf(fileInfo.getFileId()));
				}
			}
			
			if( startHpMngrForm.getFile2() != null && startHpMngrForm.getFile2().getSize() > 0 ){
				
				fileInfo.setFileAltText(startHpMngrForm.getCompNm()+"의 회사 CI");
				
				//썸네일 파일도 만든다
				fileInfoService.saveFile(startHpMngrForm.getFile2(), webRoot, fileInfo, (Constant.MEGA * 10), true, 200, 60, false, uploadWhiteList);  //CI 이미지 모두 업로드
				
				if( !fileInfo.isFileUploadSuccess() ){
					fileInfoUploadResult.addErrorInfo(fileInfo);
				}else{
					startHpMngrForm.setFile2Info(fileInfo);
					startHpMngrForm.setFileId2(String.valueOf(fileInfo.getFileId()));
				}
			}
			
			// sns 정보 셋팅 (프로토콜 없으면 http:// 붙여준다) 
			if( StringUtils.isNotBlank(startHpMngrForm.getSnsFace()) ){
				if( !startHpMngrForm.getSnsFace().startsWith("http://") && !startHpMngrForm.getSnsFace().startsWith("https://") ){
					startHpMngrForm.setSnsFace("http://" + startHpMngrForm.getSnsFace());
				}
			}
			if( StringUtils.isNotBlank(startHpMngrForm.getSnsTwit()) ){
				if( !startHpMngrForm.getSnsTwit().startsWith("http://") && !startHpMngrForm.getSnsTwit().startsWith("https://") ){
					startHpMngrForm.setSnsTwit("http://" + startHpMngrForm.getSnsTwit());
				}
			}
			if( StringUtils.isNotBlank(startHpMngrForm.getSnsBlog()) ){
				if( !startHpMngrForm.getSnsBlog().startsWith("http://") && !startHpMngrForm.getSnsBlog().startsWith("https://") ){
					startHpMngrForm.setSnsBlog("http://" + startHpMngrForm.getSnsBlog());
				}
			}
			if( StringUtils.isNotBlank(startHpMngrForm.getSnsHp()) ){
				if( !startHpMngrForm.getSnsHp().startsWith("http://") && !startHpMngrForm.getSnsHp().startsWith("https://") ){
					startHpMngrForm.setSnsHp("http://" + startHpMngrForm.getSnsHp());
				}
			}
			if( StringUtils.isNotBlank(startHpMngrForm.getSnsInsta()) ){
				if( !startHpMngrForm.getSnsInsta().startsWith("http://") && !startHpMngrForm.getSnsInsta().startsWith("https://") ){
					startHpMngrForm.setSnsInsta("http://" + startHpMngrForm.getSnsInsta());
				}
			}
			if( StringUtils.isNotBlank(startHpMngrForm.getSnsKakao()) ){
				if( !startHpMngrForm.getSnsKakao().startsWith("http://") && !startHpMngrForm.getSnsKakao().startsWith("https://") ){
					startHpMngrForm.setSnsKakao("http://" + startHpMngrForm.getSnsKakao());
				}
			}
			
			
			
			startHpMngrForm.setRegId(currentAdmin.getAdminId());
			startHpMngrForm.setRegNm(currentAdmin.getAdminName());
			
			int result = 0;
			result = startHpMngrService.insertStartHpCompMngr(startHpMngrForm);
			
			
			// 첨부파일 처리
			//첨부된 파일 개수
			FileInfo fileInfo2 = new FileInfo();
			fileInfo2.setSitePrefix(currentSite.getSitePrefix());
			// 모듈1차 pk
			fileInfo2.setFileModule("startHpMngr");
			fileInfo2.setFileModuleId(startHpMngrForm.getSitePrefix());
			// 모듈2차 pk
			fileInfo2.setFileModuleSub("start_comp");
			//System.out.println("compId:::"+startHpMngrForm.getCompId());
			fileInfo2.setFileModuleSubId("team"+String.valueOf(startHpMngrForm.getCompId()));
			//멤버
			fileInfo2.setMemberId(currentAdmin.getAdminId());
			//첨부유형
			fileInfo2.setFileAttachmentType("appending");
			
			
			MultipartFile multipartFile = null;
			
			if( startHpMngrForm.getStMultipartFiles() != null && !startHpMngrForm.getStMultipartFiles().isEmpty() ){
				int size = startHpMngrForm.getStMultipartFiles().size();
				//System.out.println("size: (1)" + size);
				String stAltText = "";
				for( int i = 0; i<size; i++ ){
					//System.out.println("get(i): (2)" + startHpMngrForm.getStMultipartFiles().get(i));
					multipartFile = startHpMngrForm.getStMultipartFiles().get(i);
					
					if( startHpMngrForm.getStAltTexts() != null && !startHpMngrForm.getStAltTexts().isEmpty() ){
						stAltText = startHpMngrForm.getStAltTexts().get(i);
						fileInfo2.setFileAltText(stAltText);
					}
					// 첨부파일의 정보와 파일을 저장
					//System.out.println("multipartFile:::" + multipartFile);
					fileInfoService.saveFile(multipartFile, webRoot, fileInfo2, (Constant.MEGA * 10), true, 200, 60, false, uploadWhiteList);
					//fileInfoService.saveFile(startHpMngrForm.getFile2(), webRoot, fileInfo, (Constant.MEGA * 10), true, 200, 60, false, uploadWhiteList);  //CI 이미지 모두 업로드
					
				}
			}
			
			
			
			if(result == 1){
				//System.out.println("insert 성공.");
			} else {
				//System.out.println("insert 실패!");
			}
			
			return "redirect:"+AsaproUtils.getAdminPath(currentSite)+"/startHpMngr/list.do";
		}
		
	}
		
	/**
	 * 창업팀 홈페이지 관리 상세정보를 조회한다.
	 * @param model
	 * @param currentSite
	 * @param currentAdmin
	 * @param startHpMngrForm
	 * @param startHpMngrSearch
	 * @return
	 */
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/startHpMngr/compInfo/update.do", method=RequestMethod.GET)
	public String updateStartHpMngrInfoGet(Model model
			, @CurrentSite Site currentSite
			, @CurrentAdmin AdminMember currentAdmin
			, @ModelAttribute("startHpMngrForm") StartHpMngrSearch startHpMngrForm
			, @ModelAttribute("activeIdxForm") StartHpMngr activeIdxForm
			, @ModelAttribute("startHpMngrSearch") StartHpMngrSearch startHpMngrSearch
			) throws AsaproException, IOException{
		
		model.addAttribute("formMode", "UPDATE");
		
		// 코드 값 로드
		this.loadCodes(model, null);
		
		// 대학 정보 셋팅
		
		String depId = currentAdmin.getAdminDepartment();
		UnivHpMngrSearch univHpMngrSearch = new UnivHpMngrSearch();
		univHpMngrSearch.setUnivId(depId);
		
		if(currentAdmin.getAdminOrganization().equals("university")){
			model.addAttribute("sUnivId" , univHpMngrSearch.getUnivId());
			this.loadDepartment(model, "university"); // 대학 부서목록 로드
		}
		
		/* 1. 해당 창업팀 등록정보 조회 */
		
		//System.out.println("UnivId : "+ startHpMngrSearch.getUnivId());
		StartHpMngr startHpMngrInfo = startHpMngrService.getStartHpMngrCompInfo(startHpMngrSearch);
		
		model.addAttribute("org", currentAdmin.getAdminOrganization());
		
		
		
		// 이미지 파일정보 셋팅
		if(null != startHpMngrInfo.getFileId1() && !"".equals(startHpMngrInfo.getFileId1())){
			FileInfo fileInfo = new FileInfo();
			fileInfo.setSitePrefix(currentSite.getSitePrefix());
			fileInfo.setFileId(Integer.parseInt(startHpMngrInfo.getFileId1()));
			startHpMngrInfo.setFile1Info(fileInfoService.getFileInfo(fileInfo));
		}
		
		if(null != startHpMngrInfo.getFileId2() && !"".equals(startHpMngrInfo.getFileId2())){
			FileInfo fileInfo = new FileInfo();
			fileInfo.setSitePrefix(currentSite.getSitePrefix());
			fileInfo.setFileId(Integer.parseInt(startHpMngrInfo.getFileId2()));
			startHpMngrInfo.setFile2Info(fileInfoService.getFileInfo(fileInfo));
		}
		
		
		
		// 전화번호 셋팅
		if(startHpMngrInfo.getCompTel() != null && !startHpMngrInfo.getCompTel().equals("")){
			String[] telArr = startHpMngrInfo.getCompTel().split("-");
			if(telArr.length == 3){
				startHpMngrInfo.setTel1(telArr[0]);
				startHpMngrInfo.setTel2(telArr[1]);
				startHpMngrInfo.setTel3(telArr[2]);
			}else{
				startHpMngrInfo.setTel1("");
				startHpMngrInfo.setTel2("");
				startHpMngrInfo.setTel3("");
			}
		}
		/*
		 if(currentAdmin.getAdminRole().getRoleCode().equals("ROLE_SUPER_ADMIN") ||
//		   currentAdmin.getAdminRole().getRoleCode().equals("ROLE_NORMAL_ADMIN") ||
//		   currentAdmin.getAdminRole().getRoleCode().equals("ROLE_SCT_ADMIN"))
//		{
		 
		 */
		
		// (서울시) 관리자 or 대학 관계자 or 창업팀 관계자
		if(currentAdmin.getAdminRole().getRoleCode().equals("ROLE_SUPER_ADMIN") ||    // 1. 슈퍼 관리자
		   currentAdmin.getAdminRole().getRoleCode().equals("ROLE_NORMAL_ADMIN")||
		   currentAdmin.getAdminRole().getRoleCode().equals("ROLE_SCT_ADMIN") 	||    // 2. 서울시 관리자
		   currentAdmin.getAdminDepartment().equals(startHpMngrInfo.getUnivId())||    // 3. 대학 관계자
				currentAdmin.getAdminTeam().equals(startHpMngrInfo.getCompId())){     // 4. 창업팀 관리자
			
			// 코드목록 로드
			this.loadCodes(model, startHpMngrInfo.getCompId());
					
			model.addAttribute("startHpMngrForm", startHpMngrInfo);
			model.addAttribute("activeIdxForm", startHpMngrInfo);
			
			/* 2. 창업활동지수 조회 */
			startHpMngrSearch.setCompId(startHpMngrInfo.getCompId());
			List<StartHpMngr> list = startHpMngrService.getActiveIdxInfoList(startHpMngrSearch);
			//System.out.println("size: " + list.size());
			
			if(list.size() > 0){
				model.addAttribute("list", list);
			}
			
			// 창업팀 임직원 정보 등록 확인
			StartHpMngrSearch startHpMngrSearch2 = new StartHpMngrSearch();
			startHpMngrSearch2.setCompId(startHpMngrForm.getCompId());
			int result = startHpMngrService.getCompEmpInfo(startHpMngrSearch2);
			if(result > 0){
				model.addAttribute("compEmpYn", "Y");
			}else{
				model.addAttribute("compEmpYn", "N");
			}
			
			System.out.println("result: " + result);
			
			
			return "asapro/admin/campustown/startHpMngr/startHpMngrForm";
			
		} else {
			return "redirect:"+AsaproUtils.getAdminPath(currentSite)+"/startHpMngr/list.do";
		}
		
		
	}
	
	
	/**
	 * 창업팀 홈페이지 관리 상세정보를 수정한다.
	 * @param model
	 * @param currentSite
	 * @param currentAdmin
	 * @param startHpMngrForm
	 * @param startHpMngrSearch
	 * @return
	 */
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/startHpMngr/compInfo/update.do", method=RequestMethod.POST)
	public String updateStartHpMngrInfoPost(Model model
			, @CurrentSite Site currentSite
			, @CurrentAdmin AdminMember currentAdmin
			, @ModelAttribute("startHpMngrForm") StartHpMngr startHpMngrForm
			, @ModelAttribute("activeIdxForm") StartHpMngr activeIdxForm
			, @ModelAttribute("startHpMngrSearch") StartHpMngrSearch startHpMngrSearch
			, BindingResult bindingResult
			, RedirectAttributes redirectAttributes
			) throws AsaproException, IOException{
	
		
		//System.out.println("StartHpMngrAdminController.updateStartHpMngrInfoPost()");
		startHpMngrForm.setSitePrefix(currentSite.getSitePrefix());
		startHpMngrValidator.validate(startHpMngrForm, bindingResult, "UPDATE");
		
		
		if(bindingResult.hasErrors() ){
			//System.out.println("bindingResult.hasErrors()!!!");
			
			model.addAttribute("formMode", "UPDATE");
			model.addAttribute("updateFail", true);
			
			//model.addAttribute("startHpMngrForm", startHpMngrForm1);
			return "asapro/admin/campustown/startHpMngr/startHpMngrForm";
			// /asapro/admin/campustown/startHpMngr/startHpMngrForm.js
			
			
		} else {
			//System.out.println("No ERROR!");
			
			FileInfoUploadResult fileInfoUploadResult = new FileInfoUploadResult();
			String webRoot = AsaproUtils.getWebRoot(request);
			//첨부된 파일 
			FileInfo fileInfo = new FileInfo();
			fileInfo.setSitePrefix(currentSite.getSitePrefix());
			fileInfo.setFileModuleId(startHpMngrForm.getSitePrefix());
			
			//멤버
			fileInfo.setMemberId(currentAdmin.getAdminId());
			//첨부유형
			fileInfo.setFileAttachmentType("appending");
			fileInfo.setFileModule("startHpMngr");
			
			
			//이미지 처리
			if( startHpMngrForm.getFile1() != null && startHpMngrForm.getFile1().getSize() > 0 ){
				//기존 이미지 삭제
				
				//대체텍스트
				if(StringUtils.isNotBlank(startHpMngrForm.getFile1AltText()) ){
					fileInfo.setFileAltText(startHpMngrForm.getFile1AltText() );
				}else{
					fileInfo.setFileAltText(startHpMngrForm.getCompNm()+"의 대표 이미지");
					startHpMngrForm.setFile1AltText(startHpMngrForm.getCompNm()+"의 대표 이미지");
				}
				
				fileInfoService.saveFile(startHpMngrForm.getFile1(), webRoot, fileInfo, (Constant.MEGA * 10), false, 200, 60,false);  //대표 이미지 모두 업로드
				if( !fileInfo.isFileUploadSuccess() ){
					fileInfoUploadResult.addErrorInfo(fileInfo);
				}else{
					startHpMngrForm.setFile1Info(fileInfo);
					startHpMngrForm.setFileId1(String.valueOf(fileInfo.getFileId()));
				}
			}else{
				// 대표이미지 대체텍스트만 수정 시
//				fileInfo.setFileAltText(startHpMngrForm.getFile1AltText());
//				fileInfo.setFileId(Integer.parseInt(startHpMngrForm.getFileId1()));
//				fileInfoService.updateFileInfoAltText(fileInfo);
			}
			
			if( startHpMngrForm.getFile2() != null && startHpMngrForm.getFile2().getSize() > 0 ){
				
				fileInfo.setFileAltText(startHpMngrForm.getCompNm()+"의 회사 CI");
				fileInfoService.saveFile(startHpMngrForm.getFile2(), webRoot, fileInfo, (Constant.MEGA * 10), false, 200, 60,false);  //CI 이미지 모두 업로드
				if( !fileInfo.isFileUploadSuccess() ){
					fileInfoUploadResult.addErrorInfo(fileInfo);
				}else{
					startHpMngrForm.setFile2Info(fileInfo);
					startHpMngrForm.setFileId2(String.valueOf(fileInfo.getFileId()));
				}
			}
			
			
			// sns 정보 셋팅 (프로토콜 없으면 http:// 붙여준다) 
			if( StringUtils.isNotBlank(startHpMngrForm.getSnsFace()) ){
				if( !startHpMngrForm.getSnsFace().startsWith("http://") && !startHpMngrForm.getSnsFace().startsWith("https://") ){
					startHpMngrForm.setSnsFace("http://" + startHpMngrForm.getSnsFace());
				}
			}
			if( StringUtils.isNotBlank(startHpMngrForm.getSnsTwit()) ){
				if( !startHpMngrForm.getSnsTwit().startsWith("http://") && !startHpMngrForm.getSnsTwit().startsWith("https://") ){
					startHpMngrForm.setSnsTwit("http://" + startHpMngrForm.getSnsTwit());
				}
			}
			if( StringUtils.isNotBlank(startHpMngrForm.getSnsBlog()) ){
				if( !startHpMngrForm.getSnsBlog().startsWith("http://") && !startHpMngrForm.getSnsBlog().startsWith("https://") ){
					startHpMngrForm.setSnsBlog("http://" + startHpMngrForm.getSnsBlog());
				}
			}
			if( StringUtils.isNotBlank(startHpMngrForm.getSnsHp()) ){
				if( !startHpMngrForm.getSnsHp().startsWith("http://") && !startHpMngrForm.getSnsHp().startsWith("https://") ){
					startHpMngrForm.setSnsHp("http://" + startHpMngrForm.getSnsHp());
				}
			}
			if( StringUtils.isNotBlank(startHpMngrForm.getSnsInsta()) ){
				if( !startHpMngrForm.getSnsInsta().startsWith("http://") && !startHpMngrForm.getSnsInsta().startsWith("https://") ){
					startHpMngrForm.setSnsInsta("http://" + startHpMngrForm.getSnsInsta());
				}
			}
			if( StringUtils.isNotBlank(startHpMngrForm.getSnsKakao()) ){
				if( !startHpMngrForm.getSnsKakao().startsWith("http://") && !startHpMngrForm.getSnsKakao().startsWith("https://") ){
					startHpMngrForm.setSnsKakao("http://" + startHpMngrForm.getSnsKakao());
				}
			}
			startHpMngrForm.setUpdId(currentAdmin.getAdminId());
			startHpMngrForm.setUpdNm(currentAdmin.getAdminName());
			
//			System.out.println("compId: " + startHpMngrForm.getCompId());
//			System.out.println("univId: " + startHpMngrForm.getUnivId());
			
			int result = 0;
			result = startHpMngrService.updateStartHpCompMngr(startHpMngrForm);
			
			
			redirectAttributes.addFlashAttribute("updated", true);
			return "redirect:" + AsaproUtils.getAdminPath(currentSite) + "/startHpMngr/compInfo/update.do?compId="+startHpMngrForm.getCompId();
		}	
	}
	
	
	/**
	 * 창업활동지수를 등록한다.
	 * @param model
	 * @param currentSite
	 * @param currentAdmin
	 * @param startHpMngrForm
	 * @param bindingResult
	 * @return
	 * @throws AsaproException
	 * @throws IOException
	 */
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/startHpMngr/compInfo/activeIdx/jsonCR.do", method=RequestMethod.POST)
	@ResponseBody
	public StartHpMngr insertActiveIdxInfoPost(Model model
			, @CurrentSite Site currentSite
			, @CurrentAdmin AdminMember currentAdmin
			, @ModelAttribute("activeIdxForm") StartHpMngr activeIdxForm
			, BindingResult bindingResult
			) throws AsaproException, IOException{
		
		
		/*
		System.out.println("StartHpMngrAdminController.insertActiveIdxInfoPost()");
		System.out.println("CompId: "+ activeIdxForm.getCompId());
		System.out.println("CompId2: "+activeIdxForm.getCompId2());
		System.out.println("Year: "+activeIdxForm.getSesnYear());
		System.out.println("CardiNum: "+activeIdxForm.getCardiNum());
		*/
		
		//model.addAttribute("formMode", "ACT_IDX_INSERT");
		
		int result = 0;
		activeIdxForm.setRegId(currentAdmin.getAdminId());
		activeIdxForm.setRegNm(currentAdmin.getAdminName());
		StartHpMngr startHpMngr = new StartHpMngr();
		startHpMngr = null;
		startHpMngrValidator.validate(activeIdxForm, bindingResult, "ACT_IDX_INSERT");
		
		if(bindingResult.hasErrors() ){
			model.addAttribute("formMode", "ACT_IDX_INSERT");
			//System.out.println("bindingResult.hasErrors ~!!");
			
		}else{
			result = startHpMngrService.insertActiveIdxInfo(activeIdxForm);
			
			StartHpMngrSearch startHpMngrSearch = new StartHpMngrSearch();
			startHpMngrSearch.setCompId(activeIdxForm.getCompId2());
			
			
			int idxSeq = 0;
			int result2 = 0;
			if(result == 1){
				// 창업팀 기본정보 수정일 변경
				startHpMngrSearch.setUpdId(currentAdmin.getAdminId());
				startHpMngrSearch.setUpdNm(currentAdmin.getAdminName());
				
				result2 = startHpMngrService.updateCompUpdDt(startHpMngrSearch);
				//System.out.println("result2: " + result2);
				idxSeq = startHpMngrService.getCompIdxMaxSeq(startHpMngrSearch);
				//System.out.println("idxSeq: "+ idxSeq);
				if(idxSeq != 0){
					startHpMngrSearch.setIndexId(idxSeq);
					startHpMngr = startHpMngrService.getActiveIdxInfo(startHpMngrSearch);
				}
			}
		}
		//System.out.println("startHpMngr: " + startHpMngr);
		return startHpMngr;
	}
	
	
	/**
	 * 창업활동지수의 수정사항을 반영한다.
	 * @param model
	 * @param currentSite
	 * @param currentAdmin
	 * @param indexId
	 * @param saleAmt
	 * @param invtAmt
	 * @param emplyCnt
	 * @param intellProp
	 * @return
	 */
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/startHpMngr/compInfo/activeIdx/jsonU.do", method=RequestMethod.GET)
	@ResponseBody
	public Integer updateActiveIdxInfoGet(Model model
			, @CurrentSite Site currentSite
			, @CurrentAdmin AdminMember currentAdmin
			, @RequestParam(value="compId", required=true) String compId
			, @RequestParam(value="indexId", required=true) Integer indexId
			, @RequestParam(value="saleAmt", required=true) String saleAmt
			, @RequestParam(value="invtAmt", required=true) String invtAmt
			, @RequestParam(value="emplyCnt", required=true) String emplyCnt
			, @RequestParam(value="intellProp", required=true) String intellProp
			){
		
		//System.out.println("StartHpMngrAdminController.updateActiveIdxInfoGet()");
		
		StartHpMngr startHpMngr = new StartHpMngr();

		startHpMngr.setIndexId(indexId);
		startHpMngr.setSaleAmt(saleAmt);
		startHpMngr.setInvtAmt(invtAmt);
		startHpMngr.setEmplyCnt(emplyCnt);
		startHpMngr.setIntellProp(intellProp);
		
		startHpMngr.setUpdId(currentAdmin.getAdminId());
		startHpMngr.setUpdNm(currentAdmin.getAdminName());
		
		StartHpMngrSearch startHpMngrSearch = new StartHpMngrSearch();
		startHpMngrSearch.setCompId(startHpMngr.getCompId2());
		
		int result = 0;
		result = startHpMngrService.updateActiveIdxInfo(startHpMngr);
		
		int result2 = 0;
		if(result > 0){
			// 창업팀 기본정보 수정일 변경
			startHpMngrSearch.setCompId(compId);
			startHpMngrSearch.setUpdId(currentAdmin.getAdminId());
			startHpMngrSearch.setUpdNm(currentAdmin.getAdminName());
			result2 = startHpMngrService.updateCompUpdDt(startHpMngrSearch);
			//System.out.println("result2: " + result2);
		}
		
		return result;
	}
	
	
	/**
	 * 창업활동지수의 삭제사항을 반영한다.
	 * @param model
	 * @param currentSite
	 * @param currentAdmin
	 * @param indexId
	 * @return
	 */
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/startHpMngr/compInfo/activeIdx/jsonD.do", method=RequestMethod.GET)
	@ResponseBody
	public Integer deleteActiveIdxInfoGet(Model model
			, @CurrentSite Site currentSite
			, @CurrentAdmin AdminMember currentAdmin
			, @RequestParam(value="indexId", required=true) Integer indexId
			){
		
		//System.out.println("StartHpMngrAdminController.deleteActiveIdxInfoGet()");
		
		StartHpMngr startHpMngr = new StartHpMngr();

		startHpMngr.setIndexId(indexId);
		
		startHpMngr.setDelId(currentAdmin.getAdminId());
		startHpMngr.setDelNm(currentAdmin.getAdminName());
		
		int result = 0;
		result = startHpMngrService.deleteActiveIdxInfo(startHpMngr);
		
		
		
		return result;
	}
		
	
	/**
	 * 창업활동지수 리스트를 실시간으로 조회해준다.
	 * @param model
	 * @param currentSite
	 * @param currentAdmin
	 * @param startHpMngrForm
	 * @param bindingResult
	 * @return
	 * @throws AsaproException
	 * @throws IOException
	 */
	/*
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/startHpMngr/compInfo/activeIdx/jsonList.do", method=RequestMethod.GET)
	@ResponseBody
	public List<StartHpMngr> activeIdxInfoListGet(Model model
			, @CurrentSite Site currentSite
			, @CurrentAdmin AdminMember currentAdmin
			//, @ModelAttribute("activeIdxForm") StartHpMngr activeIdxForm
			, @ModelAttribute("startHpMngrSearch") StartHpMngrSearch startHpMngrSearch
			, @RequestParam(value="compId", required=true) String compId
			) throws AsaproException, IOException{
		
		
		startHpMngrSearch.setCompId(compId);
		List<StartHpMngr> list = startHpMn`grService.getActiveIdxInfoList(startHpMngrSearch);
		model.addAttribute("list", list);
		
		return list;
	}
	*/
	
	
	
	/**
	 * 임직원 관리 추가폼 뷰를 해당 상태에 따라 redirect 시켜준다.(CREATE or UPDATE)
	 * @param model
	 * @param currentSite
	 * @param currentAdmin
	 * @param startHpMngrForm
	 * @param startHpMngrSearch
	 * @return
	 */
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/startHpMngr/empInfo/divCu.do", method=RequestMethod.GET)
	public String startHpMngrEmpInfoDiv(Model model
			, @CurrentSite Site currentSite
			, @CurrentAdmin AdminMember currentAdmin
			, @ModelAttribute("startHpMngrForm") StartHpMngr startHpMngrForm
			, @ModelAttribute("startHpMngrSearch") StartHpMngrSearch startHpMngrSearch
			) {
		
		// 대표자 데이터 존재 유무
		startHpMngrSearch.setRprsntvYn("Y");
		startHpMngrSearch.setCompId(startHpMngrForm.getCompId());
		// 임직원 정보 등록 유무 확인(해당 데이타 없으면 등록으로, 있으면 수정으로 넘기게 처리)
		List<StartHpMngr> list = startHpMngrService.getEmpssList(startHpMngrSearch);
		//System.out.println("list.size(): " + list.size());
		
//		System.out.println("univId:::" + startHpMngrForm.getUnivId());
		
		// (서울시) 관리자 or 대학 관계자 or 창업팀 관계자
//		if(currentAdmin.getAdminRole().getRoleCode().equals("ROLE_SUPER_ADMIN") ||    // 1. 슈퍼 관리자
//		   currentAdmin.getAdminRole().getRoleCode().equals("ROLE_NORMAL_ADMIN")||
//		   currentAdmin.getAdminRole().getRoleCode().equals("ROLE_SCT_ADMIN") 	||    // 2. 서울시 관리자
//		   currentAdmin.getAdminDepartment().equals(startHpMngrForm.getUnivId())||    // 3. 대학 관계자
//				currentAdmin.getAdminTeam().equals(startHpMngrForm.getCompId())){     // 4. 창업팀 관리자
				if(list.size() > 0){
					// 대표자 데이터 존재하면
					//model.addAttribute("formMode", "UPDATE");
					return "redirect:" + AsaproUtils.getAdminPath(currentSite) + "/startHpMngr/empInfo/update.do?compId=" 
																			   + startHpMngrForm.getCompId();
					// 대표자 데이터 존재하지 않으면
				}else{
					//model.addAttribute("formMode", "INSERT");
					return "redirect:" + AsaproUtils.getAdminPath(currentSite) + "/startHpMngr/empInfo/insert.do?compId=" 
																			   + startHpMngrForm.getCompId();
				}
//		}else{
//			return "redirect:"+AsaproUtils.getAdminPath(currentSite)+"/startHpMngr/list.do";
//		}
	}
	
	/**
	 * 창업팀 직원 관리 추가폼 뷰를 반환한다.
	 * @param model
	 * @param currentSite
	 * @param currentAdmin
	 * @param startHpMngrForm
	 * @param startHpMngrSearch
	 * @return
	 */
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/startHpMngr/empInfo/insert.do", method=RequestMethod.GET)
	public String insertStartHpMngrEmpInfoGet(Model model
			, @CurrentSite Site currentSite
			, @CurrentAdmin AdminMember currentAdmin
			, @ModelAttribute("startHpMngrEmpForm") StartHpMngr startHpMngrEmpForm
			, @ModelAttribute("startHpMngrSearch") StartHpMngrSearch startHpMngrSearch
			) {
	
		
		model.addAttribute("formMode", "INSERT");
		
		//System.out.println("compId: (abababababab)" + startHpMngrSearch.getCompId());
		
		StartHpMngr startHpMngrCompInfo = startHpMngrService.getStartHpMngrCompInfo(startHpMngrSearch);
		model.addAttribute("startHpMngrEmpForm" , startHpMngrCompInfo);
		
		// 코드목록 로드
		this.loadCodes(model, startHpMngrCompInfo.getCompId());
		
		// 부서목록 로드
		this.loadDepartment(model, "university"); // departmentList
		
		// 2. 임직원 정보 담기
		StartHpMngrSearch startHpMngrSearch2 = new StartHpMngrSearch();
		//System.out.println("compId: " + startHpMngrEmpInfo.getCompId());
		startHpMngrSearch2.setCompId(startHpMngrEmpForm.getCompId());
		List<StartHpMngr> list2 = startHpMngrService.getEmpssList(startHpMngrSearch2);
		//System.out.println("list2.size(): " + list2.size());
		
		model.addAttribute("list2", list2);
		
		return "asapro/admin/campustown/startHpMngr/startHpMngrEmpForm";
	}
		
	
	/**
	 * 창업팀 직원 관리 중 대표자 뷰를 등록한다.
	 * @param model
	 * @param currentSite
	 * @param currentAdmin
	 * @param startHpMngrForm
	 * @param startHpMngrSearch
	 * @return
	 * @throws IOException 
	 * @throws AsaproException 
	 */
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/startHpMngr/empInfo/insert.do", method=RequestMethod.POST)
	public String insertStartHpMngrEmpInfoPost(Model model
			, @CurrentSite Site currentSite
			, @CurrentAdmin AdminMember currentAdmin
			, @ModelAttribute("startHpMngrEmpForm") StartHpMngr startHpMngrEmpForm
			, @ModelAttribute("startHpMngrSearch") StartHpMngrSearch startHpMngrSearch
			, BindingResult bindingResult
			, RedirectAttributes redirectAttributes
			) throws AsaproException, IOException {
		
		startHpMngrValidator.validate(startHpMngrEmpForm, bindingResult, "INSERT");
		
		if(bindingResult.hasErrors() ){
			model.addAttribute("formMode", "INSERT");
			return "asapro/admin/campustown/startHpMngr/startHpMngrForm";
		} else {
			
			FileInfoUploadResult fileInfoUploadResult = new FileInfoUploadResult();
			String webRoot = AsaproUtils.getWebRoot(request);
			//첨부된 파일 
			FileInfo fileInfo = new FileInfo();
			fileInfo.setSitePrefix(currentSite.getSitePrefix());
			fileInfo.setFileModuleId(startHpMngrEmpForm.getSitePrefix());
			
			//멤버
			fileInfo.setMemberId(currentAdmin.getAdminId());
			//첨부유형
			fileInfo.setFileAttachmentType("appending");
			fileInfo.setFileModule("startHpMngrEmp");
			
			//이미지 처리
			if( startHpMngrEmpForm.getFile3() != null && startHpMngrEmpForm.getFile3().getSize() > 0 ){
				
				fileInfo.setFileAltText("창업팀 "+startHpMngrEmpForm.getCompNm()+"의 대표자 사진");
				fileInfoService.saveFile(startHpMngrEmpForm.getFile3(), webRoot, fileInfo, (Constant.MEGA * 10), false, 200, 60,false);  //CI 이미지 모두 업로드
				if( !fileInfo.isFileUploadSuccess() ){
					fileInfoUploadResult.addErrorInfo(fileInfo);
				}else{
					startHpMngrEmpForm.setFile3Info(fileInfo);
					startHpMngrEmpForm.setFileId3(String.valueOf(fileInfo.getFileId()));
				}
			}
			
			startHpMngrEmpForm.setRegId(currentAdmin.getAdminId());
			startHpMngrEmpForm.setRegNm(currentAdmin.getAdminName());
			startHpMngrEmpForm.setOfcps("대표자");
			
			
			// 대표자 데이터 등록유무 체크
			StartHpMngrSearch startHpMngrSearch2 = new StartHpMngrSearch();
			startHpMngrSearch2.setRprsntvYn("Y");
			startHpMngrSearch2.setCompId(startHpMngrEmpForm.getCompId());
			List<StartHpMngr> list = startHpMngrService.getEmpssList(startHpMngrSearch2);
			int result = 0;
			
			if(list.size() > 0){
				// 대표자 데이터 존재하면
				redirectAttributes.addFlashAttribute("rprsntvDataYn", "Y");
				return "redirect:" + AsaproUtils.getAdminPath(currentSite) + "/startHpMngr/empInfo/update.do?compId=" 
				   + startHpMngrEmpForm.getCompId();
			}else{
				result = startHpMngrService.insertStartHpEmpMngr(startHpMngrEmpForm);
				
				if(result == 1){
					//System.out.println("insert 성공.");
					// 창업팀 기본정보 노출여부 'Y'로 수정
					StartHpMngrSearch startHpMngrSearch3 = new StartHpMngrSearch();
					startHpMngrSearch3.setCompId(startHpMngrEmpForm.getCompId());
					int result2 = startHpMngrService.updateCompExpsr(startHpMngrSearch3);
					
					
					
				} else {
					//System.out.println("insert 실패!");
				}
				return "redirect:"+AsaproUtils.getAdminPath(currentSite)+"/startHpMngr/list.do";
			}
			
		}
		
	}
	
	
	/**
	 * 창업팀 직원 관리 중 대표자와 임직원들의 상세정보를 조회한다.
	 * @param model
	 * @param currentSite
	 * @param currentAdmin
	 * @param startHpMngrForm
	 * @param startHpMngrSearch
	 * @return
	 */
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/startHpMngr/empInfo/update.do", method=RequestMethod.GET)
	public String updateStartHpMngrEmpInfoGet(Model model
			, @CurrentSite Site currentSite
			, @CurrentAdmin AdminMember currentAdmin
			, @ModelAttribute("startHpMngrEmpForm") StartHpMngr startHpMngrEmpForm
			, @ModelAttribute("startHpMngrEmpssForm") StartHpMngr startHpMngrEmpssForm
			, @ModelAttribute("startHpMngrSearch") StartHpMngrSearch startHpMngrSearch
			) throws AsaproException, IOException{
		
		model.addAttribute("formMode", "UPDATE");
		
		// 코드목록 로드
		this.loadCodes(model, startHpMngrSearch.getCompId());
		
		// 부서목록 로드
		this.loadDepartment(model, "university"); // departmentList
		
		// 1. 대표자 데이타 담기
		//System.out.println("rprsntvYn: " + startHpMngrEmpForm.getRprsntvYn());
		startHpMngrSearch.setRprsntvYn(startHpMngrEmpForm.getRprsntvYn());
		StartHpMngr startHpMngrEmpInfo =  startHpMngrService.getEmpRprsnInfo(startHpMngrSearch);
		
		// 이미지 파일정보 셋팅
		if(null != startHpMngrEmpInfo && null != startHpMngrEmpInfo.getFileId3() && !"".equals(startHpMngrEmpInfo.getFileId3())){
			FileInfo fileInfo = new FileInfo();
			fileInfo.setSitePrefix(currentSite.getSitePrefix());
			fileInfo.setFileId(Integer.parseInt(startHpMngrEmpInfo.getFileId3()));
			startHpMngrEmpInfo.setFile3Info(fileInfoService.getFileInfo(fileInfo));
		}
		
		// 필수값 아닌 것 처리
		// all or nothing

		if(null != startHpMngrEmpInfo && null != startHpMngrEmpInfo.getTel() && !"".equals(startHpMngrEmpInfo.getTel())){
			String[] telArr = startHpMngrEmpInfo.getTel().split("-");
			if(telArr.length == 3){
				startHpMngrEmpInfo.setTel1(telArr[0]);
				startHpMngrEmpInfo.setTel2(telArr[1]);
				startHpMngrEmpInfo.setTel3(telArr[2]);
			}
		}

		if(null != startHpMngrEmpInfo && null != startHpMngrEmpInfo.getPhone() && !"".equals(startHpMngrEmpInfo.getPhone())){
			String[] phoneArr = startHpMngrEmpInfo.getPhone().split("-");
			if(phoneArr.length == 3){
				startHpMngrEmpInfo.setPhone1(phoneArr[0]);
				startHpMngrEmpInfo.setPhone2(phoneArr[1]);
				startHpMngrEmpInfo.setPhone3(phoneArr[2]);
			}
		}
			
		if(null != startHpMngrEmpInfo && null != startHpMngrEmpInfo.getEmail() && !"".equals(startHpMngrEmpInfo.getEmail())){
			String[] emailArr = startHpMngrEmpInfo.getEmail().split("@");
			if(emailArr.length == 2){
				startHpMngrEmpInfo.setEmail1(emailArr[0]);
				startHpMngrEmpInfo.setEmail2(emailArr[1]);
			}
		}
		
				
		model.addAttribute("startHpMngrEmpForm", startHpMngrEmpInfo);
		
		
		
		// 2. 임직원 정보 담기
		StartHpMngrSearch startHpMngrSearch2 = new StartHpMngrSearch();
		//System.out.println("compId: " + startHpMngrEmpInfo.getCompId());
		startHpMngrSearch2.setCompId(startHpMngrEmpInfo.getCompId());
		List<StartHpMngr> list2 = startHpMngrService.getEmpssList(startHpMngrSearch2);
		//System.out.println("list2.size(): " + list2.size());
		
		model.addAttribute("list2", list2);
		
		return "asapro/admin/campustown/startHpMngr/startHpMngrEmpForm"; 
	}	
	
	
	
	/**
	 * 창업팀 직원 관리 중 대표자의 상세정보를 수정한다.
	 * @param model
	 * @param currentSite
	 * @param currentAdmin
	 * @param startHpMngrForm
	 * @param startHpMngrSearch
	 * @return
	 */
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/startHpMngr/empInfo/update.do", method=RequestMethod.POST)
	public String updateStartHpMngrEmpInfoPost(Model model
			, @CurrentSite Site currentSite
			, @CurrentAdmin AdminMember currentAdmin
			, @ModelAttribute("startHpMngrEmpForm") StartHpMngr startHpMngrEmpForm
		//	, @ModelAttribute("activeIdxForm") StartHpMngr activeIdxForm
			, @ModelAttribute("startHpMngrSearch") StartHpMngrSearch startHpMngrSearch
			, BindingResult bindingResult
			, RedirectAttributes redirectAttributes
			) throws AsaproException, IOException{
		
		//System.out.println("StartHpMngrAdminController.updateStartHpMngrInfoPost()");
		startHpMngrEmpForm.setSitePrefix(currentSite.getSitePrefix());
		startHpMngrValidator.validate(startHpMngrEmpForm, bindingResult, "UPDATE");
		
		if(bindingResult.hasErrors() ){
			//System.out.println("bindingResult.hasErrors()!!!");
			
			model.addAttribute("formMode", "UPDATE");
			model.addAttribute("updateFail", true);
			
			//model.addAttribute("startHpMngrForm", startHpMngrForm1);
			return "asapro/admin/campustown/startHpMngr/startHpMngrEmpForm";
			// /asapro/admin/campustown/startHpMngr/startHpMngrForm.js
			
			
		} else {
			//System.out.println("No ERROR!");
			
			
			FileInfoUploadResult fileInfoUploadResult = new FileInfoUploadResult();
			String webRoot = AsaproUtils.getWebRoot(request);
			//첨부된 파일 
			FileInfo fileInfo = new FileInfo();
			fileInfo.setSitePrefix(currentSite.getSitePrefix());
			fileInfo.setFileModuleId(startHpMngrEmpForm.getSitePrefix());
			
			//멤버
			fileInfo.setMemberId(currentAdmin.getAdminId());
			//첨부유형
			fileInfo.setFileAttachmentType("appending");
			fileInfo.setFileModule("startHpMngrEmp");
			
			
			//이미지 처리
			if( startHpMngrEmpForm.getFile3() != null && startHpMngrEmpForm.getFile3().getSize() > 0 ){
				
				fileInfo.setFileAltText("창업팀 "+startHpMngrEmpForm.getCompNm()+"의 대표자 사진");
				fileInfoService.saveFile(startHpMngrEmpForm.getFile3(), webRoot, fileInfo, (Constant.MEGA * 10), false, 200, 60,false);  //CI 이미지 모두 업로드
				if( !fileInfo.isFileUploadSuccess() ){
					fileInfoUploadResult.addErrorInfo(fileInfo);
				}else{
					startHpMngrEmpForm.setFile3Info(fileInfo);
					startHpMngrEmpForm.setFileId3(String.valueOf(fileInfo.getFileId()));
				}
			}
			
			startHpMngrEmpForm.setUpdId(currentAdmin.getAdminId());
			startHpMngrEmpForm.setUpdNm(currentAdmin.getAdminName());
			//startHpMngrEmpForm.setOfcps("대표자");
			
			int result = 0;
			result = startHpMngrService.updateStartHpCompMngrEmp(startHpMngrEmpForm);
			
			int result2 = 0;
			if(result > 0){
				// 창업팀 기본정보 수정일 변경
				startHpMngrSearch.setUpdId(currentAdmin.getAdminId());
				startHpMngrSearch.setUpdNm(currentAdmin.getAdminName());
				result2 = startHpMngrService.updateCompUpdDt(startHpMngrSearch);
				System.out.println("result2: " + result2);
			}
			//System.out.println("result: " + result);
			/*
			if(result > 0 ){
				//System.out.println("update 성공!!!");
			}else{
				//System.out.println("update 실패!!!");
			}
			*/
			redirectAttributes.addFlashAttribute("updated", true);
			//${ADMIN_PATH}/startHpMngr/compInfo/update.do
			return "redirect:" + AsaproUtils.getAdminPath(currentSite) + "/startHpMngr/empInfo/update.do?compId="+startHpMngrEmpForm.getCompId();
			
		}
		
	}
	
	/**
	 * 창업팀 임직원 정보를 등록후 조회한다.
	 * @param model
	 * @param currentSite
	 * @param currentAdmin
	 * @param startHpMngrForm
	 * @param bindingResult
	 * @return
	 * @throws AsaproException
	 * @throws IOException
	 */
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/startHpMngr/empInfo/empss/jsonCR.do", method=RequestMethod.POST)
	@ResponseBody
	public StartHpMngr insertStartHpMngrEmpssInfoPost(Model model
			, @CurrentSite Site currentSite
			, @CurrentAdmin AdminMember currentAdmin
			, @ModelAttribute("startHpMngrEmpssForm") StartHpMngr startHpMngrEmpssForm
			) throws AsaproException, IOException{
		
		int result = 0;
		
		//System.out.println("StartHpMngrAdminController.insertActiveIdxInfoPost()");
		//System.out.println("CompId: "+startHpMngrEmpssForm.getCompId2());
		//System.out.println("★★★ ExpsrYn: "+startHpMngrEmpssForm.getExpsrYn());
		startHpMngrEmpssForm.setRegId(currentAdmin.getAdminId());
		startHpMngrEmpssForm.setRegNm(currentAdmin.getAdminName());
		startHpMngrEmpssForm.setCompId(startHpMngrEmpssForm.getCompId2());
		
		result = startHpMngrService.insertStartHpEmpMngr(startHpMngrEmpssForm);
		int result2 = 0;
		StartHpMngrSearch startHpMngrSearch = new StartHpMngrSearch();
		startHpMngrSearch.setCompId(startHpMngrEmpssForm.getCompId2());
		
		int empSeq = 0;
		StartHpMngr startHpMngrEmpss = new StartHpMngr();
		
		if(result == 1){
			// 창업팀 기본정보 수정일 변경
			StartHpMngrSearch startHpMngrSearch2 = new StartHpMngrSearch();
			startHpMngrSearch2.setCompId(startHpMngrEmpssForm.getCompId2());
			startHpMngrSearch2.setUpdId(currentAdmin.getAdminId());
			startHpMngrSearch2.setUpdNm(currentAdmin.getAdminName());
			
			result2 = startHpMngrService.updateCompUpdDt(startHpMngrSearch2);
			//System.out.println("result2: " + result2);
			
			empSeq = startHpMngrService.getCompEmpMaxSeq(startHpMngrSearch);
			//System.out.println("empSeq: " + empSeq);
			
			if(empSeq != 0){
				startHpMngrSearch.setEmpId(empSeq);
				startHpMngrEmpss = startHpMngrService.getEmpInfo(startHpMngrSearch);
			}
			
		}
		return startHpMngrEmpss;
	}
	
	
	/**
	 * 창업팀 임직원 정보를 수정한다.
	 * @param model
	 * @param currentSite
	 * @param currentAdmin
	 * @param empId
	 * @param ofcps
	 * @param empNm
	 * @param phoneExpsrYn
	 * @param emailExpsrYn
	 * @param seoulIdMsgExpsrYn
	 * @param expsrYn
	 * @return
	 */
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/startHpMngr/empInfo/empss/jsonU.do", method=RequestMethod.GET)
	@ResponseBody
	public Integer updateStartHpMngrEmpssInfoGet(Model model
			, @CurrentSite Site currentSite
			, @CurrentAdmin AdminMember currentAdmin
			, @RequestParam(value="compId2", required=true) String compId2
			, @RequestParam(value="empId", required=true) Integer empId

			, @RequestParam(value="ofcps", required=true) String ofcps
			, @RequestParam(value="empNm", required=true) String empNm
			, @RequestParam(value="chrgJob", required=true) String chrgJob
			
			, @RequestParam(value="phone", required=true) String phone
			, @RequestParam(value="email", required=true) String email
			, @RequestParam(value="seoulId", required=true) String seoulId
			
			, @RequestParam(value="phoneExpsrYn", required=true) String phoneExpsrYn
			, @RequestParam(value="emailExpsrYn", required=true) String emailExpsrYn
			, @RequestParam(value="seoulIdMsgExpsrYn", required=true) String seoulIdMsgExpsrYn
			, @RequestParam(value="expsrYn", required=true) String expsrYn
			){
		
		
		//System.out.println("StartHpMngrAdminController.updateActiveIdxInfoGet()");
		
		StartHpMngr startHpMngrEmpss = new StartHpMngr();
		
		startHpMngrEmpss.setCompId(compId2); 
		startHpMngrEmpss.setEmpId(empId); 
		
		startHpMngrEmpss.setOfcps(ofcps); 
		startHpMngrEmpss.setEmpNm(empNm);
		startHpMngrEmpss.setChrgJob(chrgJob);
		
		startHpMngrEmpss.setPhone(phone); 
		startHpMngrEmpss.setEmail(email); 
		startHpMngrEmpss.setSeoulId(seoulId); 
		
		startHpMngrEmpss.setPhoneExpsrYn(phoneExpsrYn); 
		startHpMngrEmpss.setEmailExpsrYn(emailExpsrYn); 
		startHpMngrEmpss.setSeoulIdMsgExpsrYn(seoulIdMsgExpsrYn); 
		
		startHpMngrEmpss.setExpsrYn(expsrYn); 
		
		int result = 0;
		result = startHpMngrService.updateStartHpCompMngrEmp(startHpMngrEmpss);
		int result2 = 0;
		StartHpMngrSearch startHpMngrSearch = new StartHpMngrSearch();
		if(result > 0){
			startHpMngrSearch.setCompId(compId2);
			startHpMngrSearch.setUpdId(currentAdmin.getAdminId());
			startHpMngrSearch.setUpdNm(currentAdmin.getAdminName());

			result2 = startHpMngrService.updateCompUpdDt(startHpMngrSearch);
			//System.out.println("result2: " + result2);
		}
		return result;
	}
	
	
	/**
	 * 창업팀 임직원 정보를 삭제한다.
	 * @param model
	 * @param currentSite
	 * @param currentAdmin
	 * @param empId
	 * @return
	 */
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/startHpMngr/empInfo/empss/jsonD.do", method=RequestMethod.GET)
	@ResponseBody
	public Integer deleteStartHpMngrEmpssInfoGet(Model model
			, @CurrentSite Site currentSite
			, @CurrentAdmin AdminMember currentAdmin
			, @RequestParam(value="empId", required=true) Integer empId
			, @RequestParam(value="compId", required=true) String compId
			){
		
		//System.out.println("StartHpMngrAdminController.deleteStartHpMngrEmpssInfoGet()");
		
		StartHpMngr startHpMngrEmpss = new StartHpMngr();
		
		startHpMngrEmpss.setCompId(compId);
		startHpMngrEmpss.setEmpId(empId);
		
		startHpMngrEmpss.setDelId(currentAdmin.getAdminId());
		startHpMngrEmpss.setDelNm(currentAdmin.getAdminName());
		
		int result = 0;
		result = startHpMngrService.deleteStartHpCompMngrEmp(startHpMngrEmpss);
		
		return result;
	}
	
	
	/**
	 * 창업팀 홈페이지 정보를 삭제한다.
	 * @param model
	 * @param currentSite
	 * @param compIds
	 * @return
	 * @throws AsaproException
	 * @throws IOException
	 */
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/startHpMngr/delete.do", method=RequestMethod.POST)
	@ResponseBody
	public ServerMessage deleteStartHpMngrPost(Model model, @CurrentSite Site currentSite
												, @RequestParam(value="compIds[]", required=true) String[] compIds
												, @CurrentAdmin AdminMember currentAdmin) throws AsaproException, IOException{
		
		ServerMessage serverMessage = new ServerMessage();
		
		if( ArrayUtils.isEmpty(compIds) ){
			serverMessage.setSuccess(false);
			serverMessage.setText("삭제할 항목이 없습니다.");
		} else {
			List<StartHpMngr> startHpList = new ArrayList<StartHpMngr>();
			StartHpMngr startHp = null;
			
			for( String compId : compIds){
				startHp = new StartHpMngr();
				startHp.setSitePrefix(currentSite.getSitePrefix());
				startHp.setCompId(compId);
				startHp.setDelId(currentAdmin.getAdminId());
				startHp.setDelNm(currentAdmin.getAdminName());
				startHpList.add(startHp);
			}
			
			int result = 0;
			
			result = startHpMngrService.deleteStartHpMngr(startHpList);
			
			if(result > 0){
				serverMessage.setSuccess(true);
				serverMessage.setSuccessCnt(result);
			} else {
				serverMessage.setSuccess(false);
				serverMessage.setText("삭제하지 못하였습니다.");
			}
		}
		return serverMessage;
	}
	
	
	/**
	 * 관리자 검색 팝업창 뷰를 반환한다.
	 * @param model
	 * @param adminMemberForm
	 * @return 관리자 검색 팝업 뷰
	 */
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/startHpMngr/searchLayer.do", method=RequestMethod.GET)
	public String adminMemberSearchLayerGet2(Model model, @CurrentSite Site currentSite, @ModelAttribute("adminMemberSearch") AdminMemberSearch adminMemberSearch
												, @RequestParam(value="attr", required=true) String attr
												, @RequestParam(value="compId", required=true) String compId){
		
		model.addAttribute("attr", attr);
		adminMemberSearch.fixBrokenSvByDefaultCharsets();
		adminMemberSearch.setTeamId(compId);
		adminMemberSearch.setPaging(false);
		adminMemberSearch.setDefaultSort("ADMIN_NAME", "DESC");
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
		
		return "asapro/admin/campustown/startHpMngr/searchLayer";
	}
}
