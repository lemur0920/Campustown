/**
 * 
 */
package egovframework.com.asapro.role.web;

import java.util.ArrayList;
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

import egovframework.com.asapro.capability.service.Capability;
import egovframework.com.asapro.capability.service.CapabilitySearch;
import egovframework.com.asapro.capability.service.CapabilityService;
import egovframework.com.asapro.role.service.Role;
import egovframework.com.asapro.role.service.RoleSearch;
import egovframework.com.asapro.role.service.RoleService;
import egovframework.com.asapro.site.service.Site;
import egovframework.com.asapro.support.Constant;
import egovframework.com.asapro.support.ServerMessage;
import egovframework.com.asapro.support.annotation.CurrentSite;
import egovframework.com.asapro.support.pagination.Paging;
import egovframework.com.asapro.support.util.AsaproUtils;

/**
 * 역할관리 관리자 컨트롤러
 * @author yckim
 * @since 2018. 5. 21.
 *
 */
@Controller
public class RoleAdminController {
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private CapabilityService capabilityService;
	
	@Autowired
	private RoleValidator roleValidator;
	
	/**
	 * 사이트의 권한 정보
	 * @return 권한목록
	 */
	private List<Capability> getAllCapabilityList(){
		CapabilitySearch capabilitySearch = new CapabilitySearch();
		capabilitySearch.setPaging(false);
		return capabilityService.getCapabilityList(capabilitySearch);
	}
	//============================================================
	
	/**
	 * 역할 목록뷰를 반환한다.
	 * @param model
	 * @param roleSearch
	 * @return 역할 목록 뷰
	 */
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH  + "/role/list.do", method=RequestMethod.GET)
	public String roleListGet(Model model, @ModelAttribute("roleSearch") RoleSearch roleSearch){
		roleSearch.fixBrokenSvByDefaultCharsets();
		roleSearch.setPaging(false);
		
		List<Role> list = roleService.getRoleList(roleSearch);
		int total = roleService.getRoleListTotal(roleSearch);
		Paging paging = new Paging(list, total, roleSearch);
		
		model.addAttribute("paging", paging);
		
		return "asapro/admin/role/list";
	}
	
	/**
	 * 역할 입력폼 뷰를 반환한다.
	 * @param model
	 * @param roleForm
	 * @return 역할 입력폼
	 */
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/role/insert.do", method=RequestMethod.GET)
	public String insertRoleGet(Model model, @ModelAttribute("roleForm") Role roleForm){
		model.addAttribute("formMode", "INSERT");
		roleForm.setCapabilityList(new ArrayList<Capability>());
		//권한 정보
		model.addAttribute("allCapabilityList", this.getAllCapabilityList());
		return "asapro/admin/role/form";
	}
	
	/**
	 * 역할을 추가한다.
	 * @param model
	 * @param roleForm
	 * @param bindingResult
	 * @return 목록 뷰
	 */
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/role/insert.do", method=RequestMethod.POST)
	public String insertRolePost(Model model, RedirectAttributes redirectAttributes, @CurrentSite Site currentSite, @ModelAttribute("roleForm") Role roleForm, BindingResult bindingResult){
		
		model.addAttribute("formMode", "INSERT");
		roleValidator.validate(roleForm, bindingResult, "INSERT");
		if(bindingResult.hasErrors()){
			//권한 정보
			model.addAttribute("allCapabilityList", this.getAllCapabilityList());
			return "asapro/admin/role/form";
		} else {
			roleService.insertRole(roleForm);
			redirectAttributes.addFlashAttribute("inserted", true);
			return "redirect:" + AsaproUtils.getAdminPath(currentSite, true) + "/role/list.do";
		}
	}
	
	/**
	 * 역할 수정폼 뷰를 반환한다.
	 * @param model
	 * @param roleForm
	 * @param bindingResult
	 * @return 역할 수정폼 뷰
	 */
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/role/update.do", method=RequestMethod.GET)
	public String updateRoleGet(Model model, @ModelAttribute("roleForm") Role roleForm, BindingResult bindingResult){

		Role roleFormModel = roleService.getRole(roleForm);
		model.addAttribute("formMode", "UPDATE");
		model.addAttribute("roleForm", roleFormModel);
		
		//권한 정보
		model.addAttribute("allCapabilityList", this.getAllCapabilityList());
		
		return "asapro/admin/role/form";
	}
	
	/**
	 * 역할을 수정한다.
	 * @param model
	 * @param roleForm
	 * @param bindingResult
	 * @return 수정폼 뷰
	 */
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/role/update.do", method=RequestMethod.POST)
	public String updateRolePost(Model model, RedirectAttributes redirectAttributes, @CurrentSite Site currentSite, @ModelAttribute("roleForm") Role roleForm, BindingResult bindingResult){
		
		model.addAttribute("formMode", "UPDATE");
		roleValidator.validate(roleForm, bindingResult, "UPDATE");
		if(bindingResult.hasErrors()){
			//권한 정보
			model.addAttribute("allCapabilityList", this.getAllCapabilityList());
			return "asapro/admin/role/form";
		} else {
			roleService.updateRole(roleForm);
			redirectAttributes.addFlashAttribute("updated", true);
			return "redirect:" + AsaproUtils.getAdminPath(currentSite, true) + "/role/list.do";
		}
	}
	
	/**
	 * 역할을 삭제한다.
	 * @param roleCodes[]
	 * @return 삭제 처리 결과
	 */
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/role/delete.do", method=RequestMethod.POST)
	@ResponseBody
	public ServerMessage deleteRolePost(@RequestParam(value="roleCodes[]", required=true) String[] roleCodes){
		ServerMessage serverMessage = new ServerMessage();
		if(ArrayUtils.isEmpty(roleCodes)){
			serverMessage.setSuccess(false);
			serverMessage.setText("삭제할 항목을 선택해 주세요.");
		} else {
			int result = roleService.deleteRole(roleCodes);
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
}
