/**
 * 
 */
package egovframework.com.asapro.capability.web;

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
import egovframework.com.asapro.organization.service.Organization;
import egovframework.com.asapro.site.service.Site;
import egovframework.com.asapro.support.Constant;
import egovframework.com.asapro.support.ServerMessage;
import egovframework.com.asapro.support.annotation.CurrentSite;
import egovframework.com.asapro.support.pagination.Paging;
import egovframework.com.asapro.support.util.AsaproUtils;

/**
 * 권한 관리자 컨트롤러
 * 
 * @author yckim
 * @since 2018. 5. 17.
 * 
 */
@Controller
public class CapabilityAdminController {

	@Autowired
	private CapabilityService capabilityService;

	@Autowired
	private CapabilityValidator capabilityValidator;

	/**
	 * 권한 목록뷰를 반환한다.
	 * 
	 * @param model
	 * @param capabilitySearch
	 * @return 목록 뷰
	 */
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/capability/list.do", method=RequestMethod.GET)
	public String capabilityListGet(Model model, @ModelAttribute("capabilitySearch") CapabilitySearch capabilitySearch) {
		capabilitySearch.fixBrokenSvByDefaultCharsets();
		capabilitySearch.setPaging(false);
		
		List<Capability> list = capabilityService.getCapabilityList(capabilitySearch);
		int total = capabilityService.getCapabilityListTotal(capabilitySearch);
		Paging paging = new Paging(list, total, capabilitySearch);
		
		model.addAttribute("paging", paging);
		
		return "asapro/admin/capability/list";
	}

	/**
	 * 권한 입력 폼 뷰를 반환한다.
	 * 
	 * @param model
	 * @param capabilityForm
	 * @return 일력 폼 뷰
	 */
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/capability/insert.do", method=RequestMethod.GET)
	public String insertCapabilityGet(Model model, @ModelAttribute("capabilityForm") Capability capabilityForm) {
		
		model.addAttribute("formMode", "INSERT");
		return "asapro/admin/capability/form";
	}

	/**
	 * 권한을 입력 후 목록 뷰를 반환한다.
	 * 
	 * @param model
	 * @param capabilityForm
	 * @param bindingResult
	 * @return 목록 뷰
	 */
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/capability/insert.do", method=RequestMethod.POST)
	public String insertCapabilityPost(Model model, RedirectAttributes redirectAttributes, @CurrentSite Site currentSite, @ModelAttribute("capabilityForm") Capability capabilityForm, BindingResult bindingResult) {
		
		capabilityValidator.validate(capabilityForm, bindingResult, "INSERT");
		
		if (bindingResult.hasErrors()) {
			model.addAttribute("formMode", "INSERT");
			return "asapro/admin/capability/form";
		} else {
			capabilityService.insertCapability(capabilityForm);
			redirectAttributes.addFlashAttribute("inserted", true);
			return "redirect:" + AsaproUtils.getAdminPath(currentSite, true) + "/capability/list.do";
		}
	}

	/**
	 * 권한 정보 수정 뷰를 반환한다.
	 * 
	 * @param model
	 * @param capId
	 * @return 권한 수정 뷰
	 */
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/capability/update.do", method=RequestMethod.GET)
	public String updateCapabilityGet(Model model, @ModelAttribute("capabilityForm") Capability capabilityForm) {
		Capability capabilityFormModel = capabilityService.getCapability(capabilityForm);
		model.addAttribute("formMode", "UPDATE");
		model.addAttribute("capabilityForm", capabilityFormModel);
		return "asapro/admin/capability/form";
	}

	/**
	 * 권한 수정 후 다시 수정 뷰를 반환한다.
	 * 
	 * @param model
	 * @param capabilityForm
	 * @param bindingResult
	 * @return 목록 뷰
	 */
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/capability/update.do", method=RequestMethod.POST)
	public String updateCapabilityPost(Model model, RedirectAttributes redirectAttributes, @CurrentSite Site currentSite, @ModelAttribute("capabilityForm") Capability capabilityForm, BindingResult bindingResult) {
		capabilityValidator.validate(capabilityForm, bindingResult, "UPDATE");
		if (bindingResult.hasErrors()) {
			model.addAttribute("formMode", "UPDATE");
			return "admin/capability/form";
		} else {
			capabilityService.updateCapability(capabilityForm);
			return "redirect:" + AsaproUtils.getAdminPath(currentSite, true) + "/capability/list.do";
		}
	}
	
	/**
	 * 권한을 삭제하고 처리결과를 json으로 응답한다.
	 * @param capIds
	 * @return 권한 삭체 처리결과
	 */
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/capability/delete.do", method=RequestMethod.POST)
	@ResponseBody
	public ServerMessage deleteCapabilityPost(@RequestParam(value="capIds[]", required=true) String[] capIds){
		ServerMessage serverMessage = new ServerMessage();
		if(ArrayUtils.isEmpty(capIds)){
			serverMessage.setSuccess(false);
			serverMessage.setText("삭제할 항목을 선택해 주세요.");
		} else {
			int result = capabilityService.deleteCapability(capIds);
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
