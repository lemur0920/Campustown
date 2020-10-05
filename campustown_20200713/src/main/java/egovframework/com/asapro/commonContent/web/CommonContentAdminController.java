/**
 * 
 */
package egovframework.com.asapro.commonContent.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
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


import egovframework.com.asapro.code.service.Code;
import egovframework.com.asapro.code.service.CodeService;
import egovframework.com.asapro.commonContent.service.CommonContent;
import egovframework.com.asapro.commonContent.service.CommonContentSearch;
import egovframework.com.asapro.commonContent.service.CommonContentService;
import egovframework.com.asapro.config.service.ConfigService;
import egovframework.com.asapro.member.admin.service.AdminMember;
import egovframework.com.asapro.menu.service.MenuService;
import egovframework.com.asapro.site.service.Site;
import egovframework.com.asapro.support.Constant;
import egovframework.com.asapro.support.ServerMessage;
import egovframework.com.asapro.support.annotation.CurrentAdmin;
import egovframework.com.asapro.support.annotation.CurrentSite;
import egovframework.com.asapro.support.exception.AsaproException;
import egovframework.com.asapro.support.pagination.Paging;
import egovframework.com.asapro.support.util.AsaproUtils;

/**
 * 공통 콘텐츠 관리자 컨트롤러
 * @author yckim
 * @since 2018. 8. 23.
 *
 */
@Controller
public class CommonContentAdminController {
	
	@Autowired
	private CommonContentService commonContentService;
	
	@Autowired
	private MenuService menuService;
	
	@Autowired
	private ConfigService configService;
	
	@Autowired
	private CommonContentValidator commonContentValidator;
	
	@Autowired
	private CodeService codeService;
	
	/**
	 * 공통 콘텐츠 목록을 반환한다.
	 * @param model
	 * @param currentSite
	 * @param commonContentSearch
	 * @return 공통 콘텐츠목록
	 */
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/common/content/list.do", method=RequestMethod.GET)
	public String commonContentListGet(Model model, @CurrentSite Site currentSite, @ModelAttribute("commonContentSearch") CommonContentSearch commonContentSearch){
		commonContentSearch.setSitePrefix(currentSite.getSitePrefix());
		commonContentSearch.fixBrokenSvByDefaultCharsets();

		List<CommonContent> list = commonContentService.getCommonContentList(commonContentSearch);
		int total = commonContentService.getCommonContentListTotal(commonContentSearch);
		
		Paging paging = new Paging(list, total, commonContentSearch);
		
		model.addAttribute("paging", paging);
		
		List<Code> moduleCodeList = codeService.getCodeList("moduleCode");	//프로그램 코드목록
		model.addAttribute("moduleCodeList", moduleCodeList);
		
		List<Code> cate1CodeList = codeService.getCodeList("commContentCate1");	//카테고리1 코드목록
		model.addAttribute("cate1CodeList", cate1CodeList);
		List<Code> cate2CodeList = codeService.getCodeList("commContentCate2");	//카테고리2 코드목록
		model.addAttribute("cate2CodeList", cate2CodeList);
		List<Code> cate3CodeList = codeService.getCodeList("commContentCate3");	//카테고리3 코드목록
		model.addAttribute("cate3CodeList", cate3CodeList);
		
		return "asapro/admin/commonContent/list";
	}
	
	/**
	 * 공통 콘텐츠 추가폼 뷰를 반환한다.
	 * @param model
	 * @param currentSite
	 * @param commonContentForm
	 * @return 공통 콘텐츠 추가폼 뷰
	 */
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/common/content/insert.do", method=RequestMethod.GET)
	public String commonContentInsertGet(Model model, @CurrentSite Site currentSite, @ModelAttribute("commonContentForm") CommonContent commonContentForm){
		model.addAttribute("formMode", "INSERT");
		
		List<Code> moduleCodeList = codeService.getCodeList("moduleCode");	//프로그램 코드목록
		model.addAttribute("moduleCodeList", moduleCodeList);
		
		List<Code> cate1CodeList = codeService.getCodeList("commContentCate1");	//카테고리1 코드목록
		model.addAttribute("cate1CodeList", cate1CodeList);
		List<Code> cate2CodeList = codeService.getCodeList("commContentCate2");	//카테고리2 코드목록
		model.addAttribute("cate2CodeList", cate2CodeList);
		List<Code> cate3CodeList = codeService.getCodeList("commContentCate3");	//카테고리3 코드목록
		model.addAttribute("cate3CodeList", cate3CodeList);
		
		return "asapro/admin/commonContent/form";
	}
	
	/**
	 * 공통 콘텐츠 추가 한다.
	 * @param model
	 * @param redirectAttributes
	 * @param currentSite
	 * @param currentAdmin
	 * @param commonContentForm
	 * @param bindingResult
	 * @return 추가결과
	 */
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/common/content/insert.do", method=RequestMethod.POST)
	public String commonContentInsertPost(Model model, RedirectAttributes redirectAttributes, @CurrentSite Site currentSite, @CurrentAdmin AdminMember currentAdmin, @ModelAttribute("commonContentForm") CommonContent commonContentForm, BindingResult bindingResult){
		model.addAttribute("formMode", "INSERT");
		
		commonContentValidator.validate(commonContentForm, bindingResult, "INSERT");
		
		if(bindingResult.hasErrors() ){
			
			List<Code> moduleCodeList = codeService.getCodeList("moduleCode");	//프로그램 코드목록
			model.addAttribute("moduleCodeList", moduleCodeList);
			
			List<Code> cate1CodeList = codeService.getCodeList("commContentCate1");	//카테고리1 코드목록
			model.addAttribute("cate1CodeList", cate1CodeList);
			List<Code> cate2CodeList = codeService.getCodeList("commContentCate2");	//카테고리2 코드목록
			model.addAttribute("cate2CodeList", cate2CodeList);
			List<Code> cate3CodeList = codeService.getCodeList("commContentCate3");	//카테고리3 코드목록
			model.addAttribute("cate3CodeList", cate3CodeList);
			
			//카테고리1 배열에 담는다
			if(StringUtils.isNotBlank(commonContentForm.getComContCate1()) ){
				String[] selectComContCate1Array = commonContentForm.getComContCate1().split(",");
				model.addAttribute("selectComContCate1Array", selectComContCate1Array);
			}
			//카테고리2 배열에 담는다
			if(StringUtils.isNotBlank(commonContentForm.getComContCate2()) ){
				String[] selectComContCate2Array = commonContentForm.getComContCate2().split(",");
				model.addAttribute("selectComContCate2Array", selectComContCate2Array);
			}
			//카테고리3 배열에 담는다
			if(StringUtils.isNotBlank(commonContentForm.getComContCate3()) ){
				String[] selectComContCate3Array = commonContentForm.getComContCate3().split(",");
				model.addAttribute("selectComContCate3Array", selectComContCate3Array);
			}
			
			return "asapro/admin/commonContent/form";
		} else {
			commonContentForm.setSitePrefix(currentSite.getSitePrefix());
			commonContentForm.setComContRegDate(new Date());
			commonContentForm.setComContModifyDate(new Date());
			commonContentForm.setComContWorker(currentAdmin);
			
			commonContentService.insertCommonContent(commonContentForm);
			redirectAttributes.addFlashAttribute("inserted", true);
			
			return "redirect:" + AsaproUtils.getAdminPath(currentSite, true) + "/common/content/list.do?comContModule=" + commonContentForm.getComContModule();
		}
		
	}
	
	/**
	 * 공통 콘텐츠 수정폼 뷰를 반환한다.
	 * @param model
	 * @param currentSite
	 * @param commonContentForm
	 * @return 공통 콘텐츠 수정폼 뷰
	 */
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/common/content/update.do", method=RequestMethod.GET)
	public String commonContentUpdateGet(Model model, @CurrentSite Site currentSite, @ModelAttribute("commonContentForm") CommonContent commonContentForm){
		model.addAttribute("formMode", "UPDATE");
		
		commonContentForm.setSitePrefix(currentSite.getSitePrefix());
		CommonContent commonContent = commonContentService.getCommonContent(commonContentForm);
		model.addAttribute("commonContentForm", commonContent);
		
		//코드 목록을 담는다
		List<Code> moduleCodeList = codeService.getCodeList("moduleCode");	//프로그램 코드목록
		model.addAttribute("moduleCodeList", moduleCodeList);
		
		List<Code> cate1CodeList = codeService.getCodeList("commContentCate1");	//카테고리1 코드목록
		model.addAttribute("cate1CodeList", cate1CodeList);
		List<Code> cate2CodeList = codeService.getCodeList("commContentCate2");	//카테고리2 코드목록
		model.addAttribute("cate2CodeList", cate2CodeList);
		List<Code> cate3CodeList = codeService.getCodeList("commContentCate3");	//카테고리3 코드목록
		model.addAttribute("cate3CodeList", cate3CodeList);
		
		//카테고리1 배열에 담는다
		if(StringUtils.isNotBlank(commonContent.getComContCate1()) ){
			String[] selectComContCate1Array = commonContent.getComContCate1().split(",");
			model.addAttribute("selectComContCate1Array", selectComContCate1Array);
		}
		//카테고리2 배열에 담는다
		if(StringUtils.isNotBlank(commonContent.getComContCate2()) ){
			String[] selectComContCate2Array = commonContent.getComContCate2().split(",");
			model.addAttribute("selectComContCate2Array", selectComContCate2Array);
		}
		//카테고리3 배열에 담는다
		if(StringUtils.isNotBlank(commonContent.getComContCate3()) ){
			String[] selectComContCate3Array = commonContent.getComContCate3().split(",");
			model.addAttribute("selectComContCate3Array", selectComContCate3Array);
		}
		
		return "asapro/admin/commonContent/form";
	}
	
	/**
	 * 공통 콘텐츠 수정 한다.
	 * @param model
	 * @param redirectAttributes
	 * @param currentSite
	 * @param currentAdmin
	 * @param commonContentForm
	 * @param bindingResult
	 * @return 수정결과
	 */
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/common/content/update.do", method=RequestMethod.POST)
	public String commonContentUpdatePost(Model model, RedirectAttributes redirectAttributes, @CurrentSite Site currentSite, @CurrentAdmin AdminMember currentAdmin, @ModelAttribute("commonContentForm") CommonContent commonContentForm, BindingResult bindingResult){
		model.addAttribute("formMode", "UPDATE");
		
		commonContentValidator.validate(commonContentForm, bindingResult, "UPDATE");
		
		if(bindingResult.hasErrors() ){
			
			List<Code> moduleCodeList = codeService.getCodeList("moduleCode");	//프로그램 코드목록
			model.addAttribute("moduleCodeList", moduleCodeList);
			
			List<Code> cate1CodeList = codeService.getCodeList("commContentCate1");	//카테고리1 코드목록
			model.addAttribute("cate1CodeList", cate1CodeList);
			List<Code> cate2CodeList = codeService.getCodeList("commContentCate2");	//카테고리2 코드목록
			model.addAttribute("cate2CodeList", cate2CodeList);
			List<Code> cate3CodeList = codeService.getCodeList("commContentCate3");	//카테고리3 코드목록
			model.addAttribute("cate3CodeList", cate3CodeList);
			
			//카테고리1 배열에 담는다
			if(StringUtils.isNotBlank(commonContentForm.getComContCate1()) ){
				String[] selectComContCate1Array = commonContentForm.getComContCate1().split(",");
				model.addAttribute("selectComContCate1Array", selectComContCate1Array);
			}
			//카테고리2 배열에 담는다
			if(StringUtils.isNotBlank(commonContentForm.getComContCate2()) ){
				String[] selectComContCate2Array = commonContentForm.getComContCate2().split(",");
				model.addAttribute("selectComContCate2Array", selectComContCate2Array);
			}
			//카테고리3 배열에 담는다
			if(StringUtils.isNotBlank(commonContentForm.getComContCate3()) ){
				String[] selectComContCate3Array = commonContentForm.getComContCate3().split(",");
				model.addAttribute("selectComContCate3Array", selectComContCate3Array);
			}
			
			return "asapro/admin/commonContent/form";
		} else {
			commonContentForm.setSitePrefix(currentSite.getSitePrefix());
			commonContentForm.setComContModifyDate(new Date());
			commonContentForm.setComContWorker(currentAdmin);
			
			commonContentService.updateCommonContent(commonContentForm);
			redirectAttributes.addFlashAttribute("updated", true);
			
			return "redirect:" + AsaproUtils.getAdminPath(currentSite, true) + "/common/content/update.do?comContId=" + commonContentForm.getComContId();
		}
		
	}
	
	/**
	 * 공통 콘텐츠를 삭제한다.
	 * @param model
	 * @param currentSite
	 * @param comContIds
	 * @return 삭제결과
	 * @throws AsaproException
	 * @throws IOException
	 */
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/common/content/delete.do", method=RequestMethod.POST)
	@ResponseBody
	public ServerMessage deleteCommonContentPost(Model model, @CurrentSite Site currentSite, @RequestParam(value="comContIds[]", required=true) Integer[] comContIds) throws AsaproException, IOException{
		ServerMessage serverMessage = new ServerMessage();
		if( ArrayUtils.isEmpty(comContIds) ){
			serverMessage.setSuccess(false);
			serverMessage.setText("삭제할 항목이 없습니다.");
		} else {
			List<CommonContent> commonContentList = new ArrayList<CommonContent>();
			CommonContent commonContent = null;
			for( Integer comContId : comContIds ){
				commonContent = new CommonContent();
				commonContent.setSitePrefix(currentSite.getSitePrefix());
				commonContent.setComContId(comContId);
				commonContentList.add(commonContent);
			}
			int result = commonContentService.deleteCommonContent(commonContentList);
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
