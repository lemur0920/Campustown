/**
 * 
 */
package egovframework.com.asapro.allowip.web;

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

import egovframework.com.asapro.allowip.service.AllowIp;
import egovframework.com.asapro.allowip.service.AllowIpSearch;
import egovframework.com.asapro.allowip.service.AllowIpService;
import egovframework.com.asapro.banner.service.Banner;
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
 * 접속허용 IP 관리 관리자 컨트롤러
 * @author yckim
 * @since 2018. 7. 17.
 *
 */
@Controller
public class AllowIpAdminController {
	
	@Autowired
	private AllowIpService allowIpService;
	
	@Autowired
	private AllowIpValidator allowIpValidator;
	
	/**
	 * 접속허용/차단 IP 목록뷰를 반환한다.
	 * @param model
	 * @param allowIpSearch
	 * @return 접속허용/차단 IP 목록
	 */
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/allowip/list.do", method=RequestMethod.GET)
	public String allowIpListGet(Model model, @CurrentSite Site currentSite, @ModelAttribute("allowIpSearch") AllowIpSearch allowIpSearch){
		allowIpSearch.fixBrokenSvByDefaultCharsets();
		allowIpSearch.setSitePrefix(currentSite.getSitePrefix());
		if(StringUtils.isBlank(allowIpSearch.getIpType()) ){
			allowIpSearch.setIpType("allow");
		}
		
		int total = allowIpService.getAllowIpListTotal(allowIpSearch);
		List<AllowIp> list = allowIpService.getAllowIpList(allowIpSearch);
		
		Paging paging = new Paging(list, total, allowIpSearch);
		model.addAttribute("paging", paging);
		
		return "asapro/admin/allowip/list";
	}
	
	/**
	 * 접속허용/차단 IP 를 추가폼 뷰를 반환한다.
	 * @param model
	 * @param currentSite
	 * @param allowIpForm
	 * @return 접속허용/차단 IP 를 추가폼
	 */
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/allowip/insert.do", method=RequestMethod.GET)
	public String insertAllowIpGet(Model model, @CurrentSite Site currentSite, @ModelAttribute("allowIpForm") AllowIp allowIpForm){
		model.addAttribute("formMode", "INSERT");
		
		if(StringUtils.isBlank(allowIpForm.getIpType()) ){
			allowIpForm.setIpType("allow");
		}
		//System.out.println("MAC : " + AsaproNetworkState.getMyMACAddress(AsaproNetworkState.getMyIPaddress()) );
		return "asapro/admin/allowip/form";
	}
	
	/**
	 * 접속허용/차단 IP 를 추가한다.
	 * @param model
	 * @param redirectAttributes
	 * @param currentSite
	 * @param allowIpForm
	 * @param bindingResult
	 * @return 추가 결과 뷰
	 */
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/allowip/insert.do", method=RequestMethod.POST)
	public String insertAllowIpPost(Model model, RedirectAttributes redirectAttributes, @CurrentSite Site currentSite, @ModelAttribute("allowIpForm") AllowIp allowIpForm, BindingResult bindingResult){
		allowIpValidator.validate(allowIpForm, bindingResult, "INSERT");
		if(bindingResult.hasErrors()){
			model.addAttribute("formMode", "INSERT");
			if(StringUtils.isBlank(allowIpForm.getIpType()) ){
				allowIpForm.setIpType("allow");
			}
			return "asapro/admin/allowip/form";
		} else {
			allowIpForm.setSitePrefix(currentSite.getSitePrefix());
			String tempIpStart = allowIpForm.getIpStart1() + "." + allowIpForm.getIpStart2() + "." + allowIpForm.getIpStart3() + "." + allowIpForm.getIpStart4();
			allowIpForm.setIpStart(tempIpStart);
			
			//종료 아이피 값이 있을경우만
			/*
			if(StringUtils.isNotBlank(allowIpForm.getIpEnd1()) && StringUtils.isNotBlank(allowIpForm.getIpEnd2()) && StringUtils.isNotBlank(allowIpForm.getIpEnd3()) && StringUtils.isNotBlank(allowIpForm.getIpEnd4()) ){
				String tempIpEnd = allowIpForm.getIpEnd1() + "." + allowIpForm.getIpEnd2() + "." + allowIpForm.getIpEnd3() + "." + allowIpForm.getIpEnd4();
				allowIpForm.setIpEnd(tempIpEnd);
			}*/
			
			allowIpService.insertAllowIp(allowIpForm);
			redirectAttributes.addFlashAttribute("inserted", true);
			return "redirect:" + AsaproUtils.getAdminPath(currentSite, true) + "/allowip/list.do?ipType=" + allowIpForm.getIpType();
		}
		
	}
	
	/**
	 * 접속허용/차단 IP 를 수정폼 뷰를 반환한다.
	 * @param model
	 * @param currentSite
	 * @param allowIpForm
	 * @return 접속허용/차단 IP 를 수정폼
	 */
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/allowip/update.do", method=RequestMethod.GET)
	public String updateAllowIpGet(Model model, @CurrentSite Site currentSite, @ModelAttribute("allowIpForm") AllowIp allowIpForm){
		model.addAttribute("formMode", "UPDATE");
		
		allowIpForm.setSitePrefix(currentSite.getSitePrefix());
		AllowIp allowIpFormModel = allowIpService.getAllowIp(allowIpForm);
		
		if(allowIpFormModel != null  ){
			if(StringUtils.isNotBlank(allowIpFormModel.getIpStart()) ){
				String[] ipStartArray = allowIpFormModel.getIpStart().split("\\.");
				allowIpFormModel.setIpStart1(ipStartArray[0]);
				allowIpFormModel.setIpStart2(ipStartArray[1]);
				allowIpFormModel.setIpStart3(ipStartArray[2]);
				allowIpFormModel.setIpStart4(ipStartArray[3]);
			}
			/*
			if(StringUtils.isNotBlank(allowIpFormModel.getIpEnd()) ){
				String[] ipEndArray = allowIpFormModel.getIpEnd().split("\\.");
				allowIpFormModel.setIpEnd1(ipEndArray[0]);
				allowIpFormModel.setIpEnd2(ipEndArray[1]);
				allowIpFormModel.setIpEnd3(ipEndArray[2]);
				allowIpFormModel.setIpEnd4(ipEndArray[3]);
			}
			*/
		}
		model.addAttribute("allowIpForm", allowIpFormModel);
		return "asapro/admin/allowip/form";
	}
	
	/**
	 * 접속허용/차단 IP 를 수정한다.
	 * @param model
	 * @param redirectAttributes
	 * @param currentSite
	 * @param allowIpForm
	 * @param bindingResult
	 * @return 수정 결과
	 */
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/allowip/update.do", method=RequestMethod.POST)
	public String updateAllowIpPost(Model model, RedirectAttributes redirectAttributes, @CurrentSite Site currentSite, @ModelAttribute("allowIpForm") AllowIp allowIpForm, BindingResult bindingResult){
		allowIpValidator.validate(allowIpForm, bindingResult, "UPDATE");
		if(bindingResult.hasErrors()){
			model.addAttribute("formMode", "UPDATE");
			if(StringUtils.isNotBlank(allowIpForm.getIpType()) ){
				allowIpForm.setIpType("allow");
			}
			return "asapro/admin/allowip/form";
		} else {
			allowIpForm.setSitePrefix(currentSite.getSitePrefix());
			String tempIpStart = allowIpForm.getIpStart1() + "." + allowIpForm.getIpStart2() + "." + allowIpForm.getIpStart3() + "." + allowIpForm.getIpStart4();
			allowIpForm.setIpStart(tempIpStart);
			
			allowIpService.updateAllowIp(allowIpForm);
			redirectAttributes.addFlashAttribute("updated", true);
			return "redirect:" + AsaproUtils.getAdminPath(currentSite, true) + "/allowip/update.do?ipId=" + allowIpForm.getIpId();
		}
		
	}

	/**
	 * 상세코드를 삭제한다.
	 * @param model
	 * @param currentSite
	 * @param ipIds
	 * @return 삭제 결과
	 */
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/allowip/delete.do", method=RequestMethod.POST)
	@ResponseBody
	public ServerMessage deleteAllowIpPost(Model model, @CurrentSite Site currentSite, @RequestParam(value="ipIds[]", required=true) Integer[] ipIds){
		ServerMessage serverMessage = new ServerMessage();
		if( ArrayUtils.isEmpty(ipIds) ){
			serverMessage.setSuccess(false);
			serverMessage.setText("삭제할 데이터가 없습니다.");
		} else {
			List<AllowIp> allowIpLis = new ArrayList<AllowIp>();
			AllowIp allowIp = null;
			for(Integer ipId : ipIds){
				allowIp = new AllowIp();
				allowIp.setSitePrefix(currentSite.getSitePrefix());
				allowIp.setIpId(ipId);
				allowIpLis.add(allowIp);
			}
			
			int result = allowIpService.deleteAllowIp(allowIpLis); 
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
