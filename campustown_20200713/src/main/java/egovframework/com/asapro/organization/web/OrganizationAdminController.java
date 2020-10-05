/**
 * 
 */
package egovframework.com.asapro.organization.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import egovframework.com.asapro.code.service.Code;
import egovframework.com.asapro.code.service.CodeCategory;
import egovframework.com.asapro.code.service.CodeCategorySearch;
import egovframework.com.asapro.code.service.CodeSearch;
import egovframework.com.asapro.code.service.CodeService;
import egovframework.com.asapro.member.admin.service.AdminMember;
import egovframework.com.asapro.organization.service.Department;
import egovframework.com.asapro.organization.service.DepartmentSearch;
import egovframework.com.asapro.organization.service.Organization;
import egovframework.com.asapro.organization.service.OrganizationSearch;
import egovframework.com.asapro.organization.service.OrganizationService;
import egovframework.com.asapro.organization.service.Staff;
import egovframework.com.asapro.organization.service.StaffSearch;
import egovframework.com.asapro.organization.service.Team;
import egovframework.com.asapro.organization.service.TeamSearch;
import egovframework.com.asapro.site.service.Site;
import egovframework.com.asapro.support.Constant;
import egovframework.com.asapro.support.ServerMessage;
import egovframework.com.asapro.support.annotation.CurrentAdmin;
import egovframework.com.asapro.support.annotation.CurrentSite;
import egovframework.com.asapro.support.annotation.CurrentUser;
import egovframework.com.asapro.support.pagination.Paging;
import egovframework.com.asapro.support.util.AsaproUtils;
import egovframework.com.asapro.support.util.EtcUtil;
import egovframework.com.campustown.startHpMngr.service.StartHpMngr;
import egovframework.com.campustown.startHpMngr.service.StartHpMngrSearch;
import egovframework.com.campustown.startHpMngr.service.StartHpMngrService;

/**
 * 조직관리 관리자 컨트롤러
 * 
 * @author yckim
 * @since 2018. 5. 11.
 */
@Controller
public class OrganizationAdminController {
	
	@Autowired
	private OrganizationService organizationService;
	
	@Autowired
	private DepartmentValidator departmentValidator;
	
	@Autowired
	private OrganizationValidator organizationValidator;
	
	@Autowired
	private StaffValidator staffValidator;
	
	@Autowired
	private CodeService codeService;
	
	@Autowired
	private StartHpMngrService startHpMngrService;
	
	//코드 적재
	//당장은 안쓰지만 일단 만들어 놓음
	private void loadCodes(Model model) {
		//직위
		model.addAttribute("staffRankCodeList", codeService.getCodeList("STAFF_RANK", "CODE_ID", "ASC"));
	}
	
	//=============================================
	/* Organization */
	/* Organization */
	/* Organization */
	//=============================================
	
	/**
	 * 기관목록뷰를 반환한다.
	 * @param model
	 * @param currentSite
	 * @param currentAdmin
	 * @param organizationSearch
	 * @return 기관목록 뷰
	 */
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/organization/organization/list.do", method=RequestMethod.GET)
	public String organizationListGet(Model model, @CurrentSite Site currentSite, @CurrentAdmin AdminMember currentAdmin, @ModelAttribute("organizationSearch") OrganizationSearch organizationSearch){
		
		organizationSearch.fixBrokenSvByDefaultCharsets();
		organizationSearch.setPaging(false);
		
		List<Organization> list = organizationService.getOrganizationList(organizationSearch);
		int total = organizationService.getOrganizationListTotal(organizationSearch);
		Paging paging = new Paging(list, total, organizationSearch);
		
		model.addAttribute("paging", paging);
		
		return "asapro/admin/organization/organizationList";
	}
	
	/**
	 * 기관입력 폼 뷰를 반환한다.
	 * @param model
	 * @param currentSite
	 * @param currentAdmin
	 * @param organizationForm
	 * @return 기관목록 뷰
	 */
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/organization/organization/insert.do", method=RequestMethod.GET)
	public String organizationInsertGet(Model model, @CurrentSite Site currentSite, @CurrentAdmin AdminMember currentAdmin, @ModelAttribute("organizationForm") Organization organizationForm){
		
		model.addAttribute("formMode", "INSERT");
		return "asapro/admin/organization/organizationForm";
	}
	
	/**
	 * 기관을 등록한다.
	 * @param model
	 * @param redirectAttributes
	 * @param currentSite
	 * @param currentAdmin
	 * @param organizationForm
	 * @param bindingResult
	 * @return 기관등록결과
	 */
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/organization/organization/insert.do", method=RequestMethod.POST)
	public String organizationInsertPost(Model model, RedirectAttributes redirectAttributes, @CurrentSite Site currentSite, @CurrentAdmin AdminMember currentAdmin, @ModelAttribute("organizationForm") Organization organizationForm, BindingResult bindingResult){
		
		organizationValidator.validate(organizationForm, bindingResult, "INSERT");
		
		if( bindingResult.hasErrors() ){
			model.addAttribute("formMode", "INSERT");
			return "asapro/admin/organization/organizationForm";
		} else {
			organizationForm.setOrgRegdate(new Date());
			organizationService.insertOrganization(organizationForm);
			redirectAttributes.addFlashAttribute("inserted", true);
			return "redirect:" + AsaproUtils.getAdminPath(currentSite, true) + "/organization/organization/list.do";
		}
		
	}
	
	/**
	 * 기관 수정폼뷰를 반환한다.
	 * @param model
	 * @param currentSite
	 * @param currentAdmin
	 * @param organizationForm
	 * @return 기관 수정폼 뷰
	 */
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/organization/organization/update.do", method=RequestMethod.GET)
	public String organizationUpdateGet(Model model, @CurrentSite Site currentSite, @CurrentAdmin AdminMember currentAdmin, @ModelAttribute("organizationForm") Organization organizationForm){
		Organization fromDB = organizationService.getOrganization(organizationForm);
		model.addAttribute("organizationForm", fromDB);
		model.addAttribute("formMode", "UPDATE");
		return "asapro/admin/organization/organizationForm";
	}
	
	/**
	 * 기관정보를 수정한다.
	 * @param model
	 * @param redirectAttributes
	 * @param currentSite
	 * @param currentAdmin
	 * @param organizationForm
	 * @param bindingResult
	 * @return 수정결과뷰
	 */
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/organization/organization/update.do", method=RequestMethod.POST)
	public String organizationUpdatePost(Model model, RedirectAttributes redirectAttributes, @CurrentSite Site currentSite, @CurrentAdmin AdminMember currentAdmin, @ModelAttribute("organizationForm") Organization organizationForm, BindingResult bindingResult){
		
		organizationValidator.validate(organizationForm, bindingResult, "UPDATE");
		
		if( bindingResult.hasErrors() ){
			model.addAttribute("formMode", "UPDATE");
			return "asapro/admin/organization/organizationForm";
		} else {
			organizationService.updateOrganization(organizationForm);
			redirectAttributes.addFlashAttribute("updated", true);
			return "redirect:" + AsaproUtils.getAdminPath(currentSite, true) + "/organization/organization/update.do?orgId=" + organizationForm.getOrgId();
		}
	}
	
	/**
	 * 기관을 삭제한다.
	 * @param model
	 * @param currentSite
	 * @param currentAdmin
	 * @param orgIds
	 * @return 기관 삭제결과 json
	 */
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/organization/organization" + Constant.API_PATH + "/delete.do", method=RequestMethod.POST)
	@ResponseBody
	public ServerMessage organizationDeletePost(Model model, @CurrentSite Site currentSite, @CurrentAdmin AdminMember currentAdmin, @RequestParam(value="orgIds[]", required=true) String[] orgIds){
		ServerMessage serverMessage = new ServerMessage(); 
		if( ArrayUtils.isEmpty(orgIds) ){
			serverMessage.setSuccess(false);
			serverMessage.setText("삭제할 데이터가 없습니다.");
		} else {
			int result = organizationService.deleteOrganizations(orgIds); 
			if( result > 0 ){
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
	 * 기관의 정렬순서를 변경한다.
	 * @param model
	 * @param orgIds
	 * @return 변경 결과
	 */
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/organization/organization" + Constant.API_PATH + "/reOrder.do", method=RequestMethod.POST)
	@ResponseBody
	public ServerMessage organizationOrderupdatePost(Model model, @RequestParam(value="orgIds[]", required=true) String[] orgIds){
		ServerMessage serverMessage = new ServerMessage();
		if( ArrayUtils.isEmpty(orgIds) ){
			serverMessage.setSuccess(false);
			serverMessage.setText("변경할 데이터가 없습니다.");
		} else {
			int order = 0;
			List<Organization> organizations = new ArrayList<Organization>();
			Organization organization = null;
			
			for(String orgId : orgIds){
				order ++;
				organization = new Organization();
				organization.setOrgId(orgId);
				organization.setOrgOrder(order);
				organizations.add(organization);
			}
			
			int result = organizationService.updateOrganizationOrders(organizations); 
			if( result > 0 ){
				serverMessage.setSuccess(true);
				serverMessage.setSuccessCnt(result);
			} else {
				serverMessage.setSuccess(false);
				serverMessage.setText("변경하지 못했습니다.");
			}
		}
		return serverMessage;
	}
	
	
	//=============================================
	/* Department */
	/* Department */
	/* Department */
	//=============================================
	
	/**
	 * 부서목록뷰를 반환한다.
	 * @param model
	 * @param currentSite
	 * @param currentAdmin
	 * @param departmentSearch
	 * @return 부서목록 뷰
	 */
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/organization/department/list.do", method=RequestMethod.GET)
	public String departmentListGet(Model model, @CurrentSite Site currentSite, @CurrentAdmin AdminMember currentAdmin, @ModelAttribute("departmentSearch") DepartmentSearch departmentSearch){
		
		departmentSearch.fixBrokenSvByDefaultCharsets();
		departmentSearch.setPaging(false);
		
		List<Department> list = organizationService.getDepartmentList(departmentSearch);
		int total = organizationService.getDepartmentListTotal(departmentSearch);
		Paging paging = new Paging(list, total, departmentSearch);
		
		model.addAttribute("paging", paging);
		
		//사용중인 기관목록
		OrganizationSearch organizationSearch = new OrganizationSearch();
		organizationSearch.setPaging(false);
		//organizationSearch.setOrgUse(true);
		organizationSearch.setDefaultSort("ORG_NAME", "ASC");
		List<Organization> organizationList = organizationService.getOrganizationList(organizationSearch);
		
		model.addAttribute("organizationList", organizationList);
		
		return "asapro/admin/organization/departmentList";
	}
	
	/**
	 * 부서입력 폼 뷰를 반환한다.
	 * @param model
	 * @param currentSite
	 * @param currentAdmin
	 * @param departmentForm
	 * @return 부서목록 뷰
	 */
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/organization/department/insert.do", method=RequestMethod.GET)
	public String departmentInsertGet(Model model, @CurrentSite Site currentSite, @CurrentAdmin AdminMember currentAdmin, @ModelAttribute("departmentForm") Department departmentForm){
		
		model.addAttribute("formMode", "INSERT");
		
		//사용중인 기관목록
		OrganizationSearch organizationSearch = new OrganizationSearch();
		organizationSearch.setPaging(false);
		//organizationSearch.setOrgUse(true);
		organizationSearch.setDefaultSort("ORG_NAME", "ASC");
		List<Organization> organizationList = organizationService.getOrganizationList(organizationSearch);
		
		model.addAttribute("organizationList", organizationList);
		return "asapro/admin/organization/departmentForm";
	}
	
	/**
	 * 부서를 등록한다.
	 * @param model
	 * @param redirectAttributes
	 * @param currentSite
	 * @param currentAdmin
	 * @param departmentForm
	 * @param bindingResult
	 * @return 부서등록결과
	 */
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/organization/department/insert.do", method=RequestMethod.POST)
	public String departmentInsertPost(Model model, RedirectAttributes redirectAttributes, @CurrentSite Site currentSite, @CurrentAdmin AdminMember currentAdmin, @ModelAttribute("departmentForm") Department departmentForm, BindingResult bindingResult){
		
		departmentValidator.validate(departmentForm, bindingResult, "INSERT");
		
		if( bindingResult.hasErrors() ){
			model.addAttribute("formMode", "INSERT");
			
			//사용중인 기관목록
			OrganizationSearch organizationSearch = new OrganizationSearch();
			organizationSearch.setPaging(false);
			//organizationSearch.setOrgUse(true);
			organizationSearch.setDefaultSort("ORG_NAME", "ASC");
			List<Organization> organizationList = organizationService.getOrganizationList(organizationSearch);
			model.addAttribute("organizationList", organizationList);
			return "asapro/admin/organization/departmentForm";
		} else {
			//부서 순서를 같은 기관중 마지막 순으로 셋팅한다.
			//db에서 처리
			/*DepartmentSearch departmentSearch = new DepartmentSearch();
			departmentSearch.setOrgId(departmentForm.getOrganization().getOrgId());
			departmentForm.setDepOrder(organizationService.getDepartmentListTotal(departmentSearch) + 1);
			*/
			departmentForm.setDepRegdate(new Date());
			
			organizationService.insertDepartment(departmentForm);
			redirectAttributes.addFlashAttribute("inserted", true);
			return "redirect:" + AsaproUtils.getAdminPath(currentSite, true) + "/organization/department/list.do";
		}
		
	}
	
	/**
	 * 부서 수정폼뷰를 반환한다.
	 * @param model
	 * @param currentSite
	 * @param currentAdmin
	 * @param departmentForm
	 * @return 부서 수정폼 뷰
	 */
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/organization/department/update.do", method=RequestMethod.GET)
	public String departmentUpdateGet(Model model, @CurrentSite Site currentSite, @CurrentAdmin AdminMember currentAdmin, @ModelAttribute("departmentForm") Department departmentForm){
		Department fromDB = organizationService.getDepartment(departmentForm);

		//사용중인 기관목록
		OrganizationSearch organizationSearch = new OrganizationSearch();
		organizationSearch.setPaging(false);
		//organizationSearch.setOrgUse(true);
		organizationSearch.setDefaultSort("ORG_NAME", "ASC");
		List<Organization> organizationList = organizationService.getOrganizationList(organizationSearch);
		model.addAttribute("organizationList", organizationList);
		
		model.addAttribute("departmentForm", fromDB);
		model.addAttribute("formMode", "UPDATE");
		return "asapro/admin/organization/departmentForm";
	}
	
	/**
	 * 부서정보를 수정한다.
	 * @param model
	 * @param redirectAttributes
	 * @param currentSite
	 * @param currentAdmin
	 * @param departmentForm
	 * @param bindingResult
	 * @return 수정결과뷰
	 */
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/organization/department/update.do", method=RequestMethod.POST)
	public String departmentUpdatePost(Model model, RedirectAttributes redirectAttributes, @CurrentSite Site currentSite, @CurrentAdmin AdminMember currentAdmin, @ModelAttribute("departmentForm") Department departmentForm, BindingResult bindingResult){
		
		departmentValidator.validate(departmentForm, bindingResult, "UPDATE");
		
		if( bindingResult.hasErrors() ){
			model.addAttribute("formMode", "INSERT");
			
			//사용중인 기관목록
			OrganizationSearch organizationSearch = new OrganizationSearch();
			organizationSearch.setPaging(false);
			//organizationSearch.setOrgUse(true);
			organizationSearch.setDefaultSort("ORG_NAME", "ASC");
			List<Organization> organizationList = organizationService.getOrganizationList(organizationSearch);
			model.addAttribute("organizationList", organizationList);
			return "asapro/admin/organization/departmentForm";
		} else {
			organizationService.updateDepartment(departmentForm);
			redirectAttributes.addFlashAttribute("update", true);
			return "redirect:" + AsaproUtils.getAdminPath(currentSite, true) + "/organization/department/update.do?depId=" + departmentForm.getDepId();
		}
	}
	
	/**
	 * 부서를 삭제한다.
	 * @param model
	 * @param currentSite
	 * @param currentAdmin
	 * @param depIds
	 * @return 부서 삭제결과 json
	 */
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/organization/department" + Constant.API_PATH + "/delete.do", method=RequestMethod.POST)
	@ResponseBody
	public ServerMessage departmentDeletePost(Model model, @CurrentSite Site currentSite, @CurrentAdmin AdminMember currentAdmin, @RequestParam(value="depIds[]", required=true) String[] depIds){
		ServerMessage serverMessage = new ServerMessage(); 
		if( ArrayUtils.isEmpty(depIds) ){
			serverMessage.setSuccess(false);
			serverMessage.setText("삭제할 데이터가 없습니다.");
		} else {
			int result = organizationService.deleteDepartments(depIds); 
			if( result > 0 ){
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
	 * 부서의 정렬순서를 변경한다.
	 * @param model
	 * @param depIds
	 * @return 변경 결과
	 */
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/organization/department" + Constant.API_PATH + "/reOrder.do", method=RequestMethod.POST)
	@ResponseBody
	public ServerMessage departmentOrderupdatePost(Model model, @RequestParam(value="depIds[]", required=true) String[] depIds){
		//'@'를 구분자로 기관아이디@부서아이디의 형태로 구성되어 있음
		ServerMessage serverMessage = new ServerMessage();
		if( ArrayUtils.isEmpty(depIds) ){
			serverMessage.setSuccess(false);
			serverMessage.setText("변경할 데이터가 없습니다.");
		} else {
			int order = 0;
			List<Department> departments = new ArrayList<Department>();
			Department department = null;
			
			for(String depId : depIds){
				order ++;
				department = new Department();
				department.setDepId(depId);
				department.setDepOrder(order);
				departments.add(department);
			}
			
			int result = organizationService.updateDepartmentOrders(departments); 
			if( result > 0 ){
				serverMessage.setSuccess(true);
				serverMessage.setSuccessCnt(result);
			} else {
				serverMessage.setSuccess(false);
				serverMessage.setText("변경하지 못했습니다.");
			}
		}
		return serverMessage;
	}
	
	/**
	 * 부서 목록을 json 으로 반환한다.
	 * @param model
	 * @param orgId
	 * @return 부서 목록 json
	 */
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/organization/department" + Constant.API_PATH + "/jsonList.do", method=RequestMethod.GET)
	@ResponseBody
	public List<Department> departmentJsonListGet(Model model, @RequestParam(value="orgId", required=true) String orgId){
		
		//부서목록
		DepartmentSearch departmentSearch = new DepartmentSearch();
		departmentSearch.setPaging(false);
		departmentSearch.setDepUse(true);
		departmentSearch.setOrgId(orgId);
		List<Department> departmentList = organizationService.getDepartmentList(departmentSearch);
		//model.addAttribute("departmentList", departmentList);
		return departmentList;
	}
	
	
	
	//=============================================
	/* Team */
	/* Team */
	/* Team */
	//=============================================
	
	/*
	*//**
	 * 팀 목록을 json 으로 반환한다.
	 * @param model
	 * @param depId
	 * @return 팀 목록 json
	 *//*
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/organization/team" + Constant.API_PATH + "/jsonList.do", method=RequestMethod.GET)
	@ResponseBody
	public List<Team> teamJsonListGet(Model model, @RequestParam(value="depId", required=true) String depId){
		
		//팀목록
		TeamSearch teamSearch = new TeamSearch();
		teamSearch.setPaging(false);
		teamSearch.setTeamUse(true);
		teamSearch.setDepId(depId);
		List<Team> teamList = organizationService.getTeamList(teamSearch);
		//model.addAttribute("departmentList", departmentList);
		return teamList;
	}*/
	
	/**
	 * 창업팀 목록을 json 으로 반환한다.
	 * @param model
	 * @param depId
	 * @return 창업팀 목록 json
	 */
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/organization/team" + Constant.API_PATH + "/jsonList.do", method=RequestMethod.GET)
	@ResponseBody
	public List<StartHpMngr> teamJsonListGet(Model model, @RequestParam(value="depId", required=true) String depId){
		
		//팀목록
		StartHpMngrSearch startHpMngrSearch = new StartHpMngrSearch();
		startHpMngrSearch.setPaging(false);
		startHpMngrSearch.setUnivId(depId);
		List<StartHpMngr> teamList = startHpMngrService.getStartHpMngrList(startHpMngrSearch);
		return teamList;
	}
	
	
}
