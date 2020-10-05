/**
 * 
 */
package egovframework.com.asapro.allowmac.web;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
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

import egovframework.com.asapro.allowmac.service.AllowMac;
import egovframework.com.asapro.allowmac.service.AllowMacSearch;
import egovframework.com.asapro.allowmac.service.AllowMacService;
import egovframework.com.asapro.site.service.Site;
import egovframework.com.asapro.support.AsaproNetworkState;
import egovframework.com.asapro.support.Constant;
import egovframework.com.asapro.support.ServerMessage;
import egovframework.com.asapro.support.annotation.CurrentSite;
import egovframework.com.asapro.support.annotation.CurrentUser;
import egovframework.com.asapro.support.pagination.Paging;
import egovframework.com.asapro.support.util.AsaproUtils;
import egovframework.com.asapro.support.util.EtcUtil;

/**
 * 접속허용 Mac Address 관리 관리자 컨트롤러
 * @author yckim
 * @since 2019. 03. 26.
 *
 */
@Controller
public class AllowMacAdminController {
	
	@Autowired
	private AllowMacService allowMacService;
	
	@Autowired
	private AllowMacValidator allowMacValidator;
	
	/**
	 * 접속허용/차단 Mac Address 목록뷰를 반환한다.
	 * @param model
	 * @param allowMacSearch
	 * @return 접속허용/차단 Mac Address 목록
	 */
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/allowmac/list.do", method=RequestMethod.GET)
	public String allowMacListGet(Model model, @CurrentSite Site currentSite, @ModelAttribute("allowMacSearch") AllowMacSearch allowMacSearch){
		allowMacSearch.fixBrokenSvByDefaultCharsets();
		allowMacSearch.setSitePrefix(currentSite.getSitePrefix());
		if(StringUtils.isBlank(allowMacSearch.getMacType()) ){
			allowMacSearch.setMacType("allow");
		}
		
		int total = allowMacService.getAllowMacListTotal(allowMacSearch);
		List<AllowMac> list = allowMacService.getAllowMacList(allowMacSearch);
		
		Paging paging = new Paging(list, total, allowMacSearch);
		model.addAttribute("paging", paging);
		
		return "asapro/admin/allowmac/list";
	}
	
	/**
	 * 접속허용/차단 Mac Address 를 추가폼 뷰를 반환한다.
	 * @param model
	 * @param currentSite
	 * @param allowMacForm
	 * @return 접속허용/차단 Mac Address 추가폼
	 */
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/allowmac/insert.do", method=RequestMethod.GET)
	public String insertAllowMacGet(Model model, @CurrentSite Site currentSite, @ModelAttribute("allowMacForm") AllowMac allowMacForm){
		model.addAttribute("formMode", "INSERT");
		
		if(StringUtils.isBlank(allowMacForm.getMacType()) ){
			allowMacForm.setMacType("allow");
		}
		//System.out.println("MAC : " + AsaproNetworkState.getMyMACAddress(AsaproNetworkState.getMyIPaddress()) );
		return "asapro/admin/allowmac/form";
	}
	
	/**
	 * 접속허용/차단 Mac Address 를 추가한다.
	 * @param model
	 * @param redirectAttributes
	 * @param currentSite
	 * @param allowMacForm
	 * @param bindingResult
	 * @return 추가 결과 뷰
	 */
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/allowmac/insert.do", method=RequestMethod.POST)
	public String insertAllowMacPost(Model model, RedirectAttributes redirectAttributes, @CurrentSite Site currentSite, @ModelAttribute("allowMacForm") AllowMac allowMacForm, BindingResult bindingResult){
		allowMacValidator.validate(allowMacForm, bindingResult, "INSERT");
		if(bindingResult.hasErrors()){
			model.addAttribute("formMode", "INSERT");
			if(StringUtils.isBlank(allowMacForm.getMacType()) ){
				allowMacForm.setMacType("allow");
			}
			return "asapro/admin/allowmac/form";
		} else {
			allowMacForm.setSitePrefix(currentSite.getSitePrefix());
			
			//종료 아이피 값이 있을경우만
			/*
			if(StringUtils.isNotBlank(allowMacForm.getMacEnd1()) && StringUtils.isNotBlank(allowMacForm.getMacEnd2()) && StringUtils.isNotBlank(allowMacForm.getMacEnd3()) && StringUtils.isNotBlank(allowMacForm.getMacEnd4()) ){
				String tempMacEnd = allowMacForm.getMacEnd1() + "." + allowMacForm.getMacEnd2() + "." + allowMacForm.getMacEnd3() + "." + allowMacForm.getMacEnd4();
				allowMacForm.setMacEnd(tempMacEnd);
			}*/
			
			allowMacService.insertAllowMac(allowMacForm);
			redirectAttributes.addFlashAttribute("inserted", true);
			return "redirect:" + AsaproUtils.getAdminPath(currentSite, true) + "/allowmac/list.do?macType=" + allowMacForm.getMacType();
		}
		
	}
	
	/**
	 * 접속허용/차단 Mac Address 를 수정폼 뷰를 반환한다.
	 * @param model
	 * @param currentSite
	 * @param allowMacForm
	 * @return 접속허용/차단 Mac Address 수정폼
	 */
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/allowmac/update.do", method=RequestMethod.GET)
	public String updateAllowMacGet(Model model, @CurrentSite Site currentSite, @ModelAttribute("allowMacForm") AllowMac allowMacForm){
		model.addAttribute("formMode", "UPDATE");
		
		allowMacForm.setSitePrefix(currentSite.getSitePrefix());
		AllowMac allowMacFormModel = allowMacService.getAllowMac(allowMacForm);
		
		model.addAttribute("allowMacForm", allowMacFormModel);
		return "asapro/admin/allowmac/form";
	}
	
	/**
	 * 접속허용/차단 Mac Address 를 수정한다.
	 * @param model
	 * @param redirectAttributes
	 * @param currentSite
	 * @param allowMacForm
	 * @param bindingResult
	 * @return 수정 결과
	 */
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/allowmac/update.do", method=RequestMethod.POST)
	public String updateAllowMacPost(Model model, RedirectAttributes redirectAttributes, @CurrentSite Site currentSite, @ModelAttribute("allowMacForm") AllowMac allowMacForm, BindingResult bindingResult){
		allowMacValidator.validate(allowMacForm, bindingResult, "UPDATE");
		if(bindingResult.hasErrors()){
			model.addAttribute("formMode", "UPDATE");
			if(StringUtils.isNotBlank(allowMacForm.getMacType()) ){
				allowMacForm.setMacType("allow");
			}
			return "asapro/admin/allowmac/form";
		} else {
			allowMacForm.setSitePrefix(currentSite.getSitePrefix());
			
			allowMacService.updateAllowMac(allowMacForm);
			redirectAttributes.addFlashAttribute("updated", true);
			return "redirect:" + AsaproUtils.getAdminPath(currentSite, true) + "/allowmac/update.do?macId=" + allowMacForm.getMacId();
		}
		
	}

	/**
	 * 접속허용/차단 Mac Address 를 삭제한다.
	 * @param model
	 * @param currentSite
	 * @param macIds
	 * @return 삭제 결과
	 */
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/allowmac/delete.do", method=RequestMethod.POST)
	@ResponseBody
	public ServerMessage deleteAllowMacPost(Model model, @CurrentSite Site currentSite, @RequestParam(value="macIds[]", required=true) Integer[] macIds){
		ServerMessage serverMessage = new ServerMessage();
		if( ArrayUtils.isEmpty(macIds) ){
			serverMessage.setSuccess(false);
			serverMessage.setText("삭제할 데이터가 없습니다.");
		} else {
			List<AllowMac> allowMacLis = new ArrayList<AllowMac>();
			AllowMac allowMac = null;
			for(Integer macId : macIds){
				allowMac = new AllowMac();
				allowMac.setSitePrefix(currentSite.getSitePrefix());
				allowMac.setMacId(macId);
				allowMacLis.add(allowMac);
			}
			
			int result = allowMacService.deleteAllowMac(allowMacLis); 
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
}
