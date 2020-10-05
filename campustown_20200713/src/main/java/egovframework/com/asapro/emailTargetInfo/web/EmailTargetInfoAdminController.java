/**
 * 
 */
package egovframework.com.asapro.emailTargetInfo.web;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

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

import egovframework.com.asapro.code.service.CodeService;
import egovframework.com.asapro.emailTargetInfo.service.EmailTargetInfo;
import egovframework.com.asapro.emailTargetInfo.service.EmailTargetInfoSearch;
import egovframework.com.asapro.emailTargetInfo.service.EmailTargetInfoService;
//import egovframework.com.asapro.member.service.Member;
import egovframework.com.asapro.site.service.Site;
import egovframework.com.asapro.support.Constant;
import egovframework.com.asapro.support.ServerMessage;
import egovframework.com.asapro.support.annotation.CurrentSite;
import egovframework.com.asapro.support.annotation.CurrentUser;
import egovframework.com.asapro.support.pagination.Paging;
import egovframework.com.asapro.support.util.AsaproUtils;
import egovframework.com.asapro.support.util.EtcUtil;

/**
 * 메일대상 관리자 컨트롤러
 * @author yckim
 * @since 2019. 12. 11.
 *
 */
@Controller
public class EmailTargetInfoAdminController {
	
	@Autowired
	private EmailTargetInfoService emailTargetInfoService;
	
	@Autowired
	private EmailTargetInfoValidator emailTargetInfoValidator;
	
	@Autowired
	private CodeService codeService;
	
	@Autowired
	private HttpServletRequest request;

	/**
	 * 메일대상 목록뷰를 반환한다.
	 * @param model
	 * @param codeSearch
	 * @return 메일대상 목록 뷰 
	 */
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/emailTarget/list.do", method=RequestMethod.GET)
	public String emailTargetInfoListGet(Model model, @ModelAttribute("emailTargetInfoSearch") EmailTargetInfoSearch emailTargetInfoSearch){
		emailTargetInfoSearch.fixBrokenSvByDefaultCharsets();
		
		int total = emailTargetInfoService.getEmailTargetInfoListTotal(emailTargetInfoSearch);
		List<EmailTargetInfo> list = emailTargetInfoService.getEmailTargetInfoList(emailTargetInfoSearch);
		Paging paging = new Paging(list, total, emailTargetInfoSearch);
		model.addAttribute("paging", paging);
		
		//사용자 게시판 구분에 따라 메일대상 구분을 위해
		model.addAttribute("etCateCodeList", codeService.getCodeList("board_grade"));
		return "asapro/admin/emailTargetInfo/list";
	}
	
	/**
	 * 메일대상 추가폼 뷰를 반환한다.
	 * @param model
	 * @param emailTargetInfoForm
	 * @return 폼뷰
	 * @throws FileNotFoundException 
	 */
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/emailTarget/insert.do", method=RequestMethod.GET)
	public String insertEmailTargetInfoGet(Model model, @ModelAttribute("emailTargetInfoForm") EmailTargetInfo emailTargetInfoForm) throws FileNotFoundException{
		//템플릿 목록
		//webRoot + dirRoot 내 있는 파일목록
		String dirRoot = "/design/email";
		List<String> etFormList = AsaproUtils.getJspFileList(request, dirRoot);
		model.addAttribute("etFormList", etFormList);
		
		model.addAttribute("formMode", "INSERT");
		//사용자 게시판 구분에 따라 메일대상 구분을 위해
		model.addAttribute("etCateCodeList", codeService.getCodeList("board_grade"));
		return "asapro/admin/emailTargetInfo/form";
	}
	
	/**
	 * 메일대상을 추가한다.
	 * @param model
	 * @param redirectAttributes
	 * @param currentSite
	 * @param emailTargetInfoForm
	 * @param bindingResult
	 * @return 추가결과
	 * @throws FileNotFoundException 
	 */
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/emailTarget/insert.do", method=RequestMethod.POST)
	public String insertEmailTargetInfoPost(Model model, RedirectAttributes redirectAttributes, @CurrentSite Site currentSite, @ModelAttribute("emailTargetInfoForm") EmailTargetInfo emailTargetInfoForm, BindingResult bindingResult) throws FileNotFoundException{
		emailTargetInfoValidator.validate(emailTargetInfoForm, bindingResult, "INSERT");
		if(bindingResult.hasErrors()){
			//템플릿 목록
			//webRoot + dirRoot 내 있는 파일목록
			String dirRoot = "/design/email";
			List<String> etFormList = AsaproUtils.getJspFileList(request, dirRoot);
			model.addAttribute("etFormList", etFormList);
			
			model.addAttribute("formMode", "INSERT");
			//사용자 게시판 구분에 따라 메일대상 구분을 위해
			model.addAttribute("etCateCodeList", codeService.getCodeList("board_grade"));
			return "asapro/admin/emailTargetInfo/form";
		} else {
			emailTargetInfoForm.setEtRegDate(new Date());
			emailTargetInfoService.insertEmailTargetInfo(emailTargetInfoForm);
			redirectAttributes.addFlashAttribute("inserted", true);
			return "redirect:" + AsaproUtils.getAdminPath(currentSite, true) + "/emailTarget/list.do";
		}
	}
	
	/**
	 * 메일대상을 조회한다.
	 * @param model
	 * @param emailTargetInfoForm
	 * @return 메일대상 뷰
	 * @throws FileNotFoundException
	 */
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/emailTarget/update.do", method=RequestMethod.GET)
	public String updateEmailTargetInfoGet(Model model, @ModelAttribute("emailTargetInfoForm") EmailTargetInfo emailTargetInfoForm) throws FileNotFoundException{
		model.addAttribute("formMode", "UPDATE");
		
		//템플릿 목록
		//webRoot + dirRoot 내 있는 파일목록
		String dirRoot = "/design/email";
		List<String> etFormList = AsaproUtils.getJspFileList(request, dirRoot);
		model.addAttribute("etFormList", etFormList);
		
		//사용자 게시판 구분에 따라 메일대상 구분을 위해
		model.addAttribute("etCateCodeList", codeService.getCodeList("board_grade"));
		
		model.addAttribute("emailTargetInfoForm", emailTargetInfoService.getEmailTargetInfo(emailTargetInfoForm));
		return "asapro/admin/emailTargetInfo/form";
	}
	
	/**
	 * 메일대상을 수정한다.
	 * @param model
	 * @param redirectAttributes
	 * @param currentSite
	 * @param emailTargetInfoForm
	 * @param bindingResult
	 * @return 
	 * @throws FileNotFoundException
	 */
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/emailTarget/update.do", method=RequestMethod.POST)
	public String updateEmailTargetInfoPost(Model model, RedirectAttributes redirectAttributes, @CurrentSite Site currentSite, @ModelAttribute("emailTargetInfoForm") EmailTargetInfo emailTargetInfoForm, BindingResult bindingResult) throws FileNotFoundException{
		emailTargetInfoValidator.validate(emailTargetInfoForm, bindingResult, "UPDATE");
		if(bindingResult.hasErrors()){
			model.addAttribute("formMode", "UPDATE");
			
			//템플릿 목록
			//webRoot + dirRoot 내 있는 파일목록
			String dirRoot = "/design/email";
			List<String> etFormList = AsaproUtils.getJspFileList(request, dirRoot);
			model.addAttribute("etFormList", etFormList);
			
			//사용자 게시판 구분에 따라 메일대상 구분을 위해
			model.addAttribute("etCateCodeList", codeService.getCodeList("board_grade"));
			
			return "asapro/admin/emailTargetInfo/form";
		} else {
			emailTargetInfoService.updateEmailTargetInfo(emailTargetInfoForm);
			redirectAttributes.addFlashAttribute("updated", true);
			return "redirect:" + AsaproUtils.getAdminPath(currentSite, true) + "/emailTarget/update.do?etId=" + emailTargetInfoForm.getEtId();
		}
	}
	
	/**
	 * 메일대상을 삭제한다.
	 * @param model
	 * @param currentSite
	 * @param etIds
	 * @return 삭제결과
	 */
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/emailTarget/delete.do", method=RequestMethod.POST)
	@ResponseBody
	public ServerMessage deleteEmailTargetInfoPost(Model model, @CurrentSite Site currentSite, @RequestParam(value="etIds[]", required=true) String[] etIds){
		ServerMessage serverMessage = new ServerMessage();
		if( ArrayUtils.isEmpty(etIds) ){
			serverMessage.setSuccess(false);
			serverMessage.setText("삭제할 데이터가 없습니다.");
		} else {
			List<EmailTargetInfo> emailTargetInfoList = new ArrayList<EmailTargetInfo>();
			EmailTargetInfo emailTargetInfo = null;
			for(String etId : etIds ){
				emailTargetInfo = new EmailTargetInfo();
				emailTargetInfo.setSitePrefix(currentSite.getSitePrefix());
				emailTargetInfo.setEtId(etId);
				emailTargetInfoList.add(emailTargetInfo);
			}
			int result = emailTargetInfoService.deleteEmailTargetInfo(emailTargetInfoList); 
			
			if( result > 0 ){
				serverMessage.setSuccess(true);
				serverMessage.setSuccessCnt(result);
			} else {
				serverMessage.setSuccess(false);
				serverMessage.setText("삭제하지 못하였습니다.[Server Error]");
			}
		}
		return serverMessage;
	}
	
}
