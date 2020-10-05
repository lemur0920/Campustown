/**
 * 
 */
package egovframework.com.asapro.code.web;

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

import egovframework.com.asapro.code.service.Code;
import egovframework.com.asapro.code.service.CodeCategory;
import egovframework.com.asapro.code.service.CodeCategorySearch;
import egovframework.com.asapro.code.service.CodeSearch;
import egovframework.com.asapro.code.service.CodeService;
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
 * 코드관리 관리자 컨트롤러
 * @author yckim
 * @since 2018. 4. 3.
 *
 */
@Controller
public class CodeAdminController {
	
	@Autowired
	private CodeService codeService;
	
	@Autowired
	private CodeCategoryValidator codeCategoryValidator;
	
	@Autowired
	private CodeValidator codeValidator;
	
	
	//===========================	코드 분류		================
	/**
	 * 코드 분류 목록뷰를 반환한다.
	 * @param model
	 * @param codeCategorySearch
	 * @return 코드 분류 목록 뷰
	 */
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/code/category/list.do", method=RequestMethod.GET)
	public String codeCategoryListGet(Model model, @ModelAttribute("codeCategorySearch") CodeCategorySearch codeCategorySearch){
		codeCategorySearch.fixBrokenSvByDefaultCharsets();
		int total = codeService.getCodeCategoryListTotal(codeCategorySearch);
		List<CodeCategory> list = codeService.getCodeCategoryList(codeCategorySearch);
		
		Paging paging = new Paging(list, total, codeCategorySearch);
		model.addAttribute("paging", paging);
		
		return "asapro/admin/code/categoryList";
	}
	
	/**
	 * 코드 분류 추가 폼 뷰를 반환한다.
	 * @param mode
	 * @param codeCategoryForm
	 * @return 코드 분류 추가 폼
	 */
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/code/category/insert.do", method=RequestMethod.GET)
	public String insertCodeCategoryGet(Model model, @ModelAttribute("codeCategoryForm") CodeCategory codeCategoryForm){
		model.addAttribute("formMode", "INSERT");
		return "asapro/admin/code/categoryForm";
	}
	
	/**
	 * 코드분류를 추가한다.
	 * @param model
	 * @param codeCategoryForm
	 * @param bindingResult
	 * @return 추가 결과 뷰
	 */
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/code/category/insert.do", method=RequestMethod.POST)
	public String insertCodeCategoryPost(Model model, RedirectAttributes redirectAttributes, @CurrentSite Site currentSite, @ModelAttribute("codeCategoryForm") CodeCategory codeCategoryForm, BindingResult bindingResult){
		codeCategoryValidator.validate(codeCategoryForm, bindingResult, "INSERT");
		if(bindingResult.hasErrors()){
			model.addAttribute("formMode", "INSERT");
			return "asapro/admin/code/categoryForm";
		} else {
			codeService.insertCodeCategory(codeCategoryForm);
			redirectAttributes.addFlashAttribute("inserted", true);
			return "redirect:" + AsaproUtils.getAdminPath(currentSite, true) + "/code/category/list.do?sc=CAT_ID&sv=" + codeCategoryForm.getCatId();
		}
	}
	
	/**
	 * 코드 분류 수정폼을 반환한다.
	 * @param model
	 * @param codeCategoryForm
	 * @return 수정폼뷰
	 */
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/code/category/update.do", method=RequestMethod.GET)
	public String updateCodeCategoryGet(Model model, @ModelAttribute("codeCategoryForm") CodeCategory codeCategoryForm){
		model.addAttribute("formMode", "UPDATE");
		CodeCategory codeCategoryFormModel = codeService.getCodeCategory(codeCategoryForm.getCatId());
		model.addAttribute("codeCategoryForm", codeCategoryFormModel);
		return "asapro/admin/code/categoryForm";
	}
	
	/**
	 * 코드 분류를 수정한다.
	 * @param model
	 * @param codeCategoryForm
	 * @param bindingResult
	 * @return 수정 폼 뷰
	 */
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/code/category/update.do", method=RequestMethod.POST)
	public String updateCodeCategoryPost(Model model, RedirectAttributes redirectAttributes, @CurrentSite Site currentSite, @ModelAttribute("codeCategoryForm") CodeCategory codeCategoryForm, BindingResult bindingResult){
		codeCategoryValidator.validate(codeCategoryForm, bindingResult, "UPDATE");
		if(bindingResult.hasErrors()){
			model.addAttribute("formMode", "UPDATE");
			return "asapro/admin/code/categoryForm";
		} else {
			codeService.updateCodeCategory(codeCategoryForm);
			redirectAttributes.addFlashAttribute("updated", true);
			return "redirect:" + AsaproUtils.getAdminPath(currentSite, true) + "/code/category/update.do?catId=" + codeCategoryForm.getCatId();
		}
	}
	
	/**
	 * 코드분류를 삭제한다.
	 * @param model
	 * @param catIds
	 * @return 삭제 결과
	 */
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/code/category/delete.do", method=RequestMethod.POST, produces="application/json")
	@ResponseBody
	public ServerMessage deleteCodeCategoryPost(Model model, @RequestParam(value="catIds[]", required=true) String[] catIds){
		ServerMessage serverMessage = new ServerMessage();
		if( ArrayUtils.isEmpty(catIds) ){
			serverMessage.setSuccess(false);
			serverMessage.setText("삭제할 데이터가 없습니다.");
		} else {
			int result = codeService.deleteCodeCategories(catIds); 
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
	
	//=============================================================================================================
	
	//===========================	상세코드		================
	/**
	 * 상세코드 목록뷰를 반환한다.
	 * @param model
	 * @param codeSearch
	 * @return 상세코드 목록 뷰 
	 */
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/code/item/list.do", method=RequestMethod.GET)
	public String codeListGet(Model model, @ModelAttribute("codeSearch") CodeSearch codeSearch){
		codeSearch.fixBrokenSvByDefaultCharsets();
		
		//카테고리(catId)가 있으면 페이징하지 않는다
		if(StringUtils.isNotBlank(codeSearch.getCatId()) ){
			codeSearch.setPaging(false);
		}
		int total = codeService.getCodeListTotal(codeSearch);
		List<Code> list = codeService.getCodeList(codeSearch);
		Paging paging = new Paging(list, total, codeSearch);
		model.addAttribute("paging", paging);
		return "asapro/admin/code/codeList";
	}
	
	/**
	 * 상세코드 추가 폼 뷰를 반환한다.
	 * @param model
	 * @param codeForm
	 * @return 상세코드 추가 폼 뷰
	 */
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/code/item/insert.do", method=RequestMethod.GET)
	public String insertCodeGet(Model model, @ModelAttribute("codeForm") Code codeForm){
		model.addAttribute("formMode", "INSERT");
		CodeCategorySearch codeCategorySearch = new CodeCategorySearch();
		codeCategorySearch.setPaging(false);
		List<CodeCategory> codeCategoryList = codeService.getCodeCategoryList(codeCategorySearch);
		model.addAttribute("codeCategoryList", codeCategoryList);
		return "asapro/admin/code/codeForm";
	}
	
	/**
	 * 상세코드를 추가한다.
	 * @param model
	 * @param CodeForm
	 * @param bindingResult
	 * @return 추가 결과 뷰
	 */
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/code/item/insert.do", method=RequestMethod.POST)
	public String insertCodePost(Model model, RedirectAttributes redirectAttributes, @CurrentSite Site currentSite, @ModelAttribute("codeForm") Code codeForm, BindingResult bindingResult){
		codeValidator.validate(codeForm, bindingResult, "INSERT");
		if(bindingResult.hasErrors()){
			model.addAttribute("formMode", "INSERT");
			CodeCategorySearch codeCategorySearch = new CodeCategorySearch();
			codeCategorySearch.setPaging(false);
			List<CodeCategory> codeCategoryList = codeService.getCodeCategoryList(codeCategorySearch);
			model.addAttribute("codeCategoryList", codeCategoryList);
			return "asapro/admin/code/codeForm";
		} else {
			//코드 순서를 같은 카테고리의 코드중 마지막 순으로 셋팅한다.
			CodeSearch codeSearch = new CodeSearch();
			codeSearch.setCatId(codeForm.getCodeCategory().getCatId());
			codeForm.setCodeOrder(codeService.getCodeListTotal(codeSearch) + 1);
			
			codeService.insertCode(codeForm);
			redirectAttributes.addFlashAttribute("inserted", true);
			return "redirect:" + AsaproUtils.getAdminPath(currentSite, true) + "/code/item/list.do?catId=" + codeForm.getCodeCategory().getCatId();
		}
	}
	
	/**
	 * 상세코드 수정폼 뷰를 반환한다.
	 * @param model
	 * @param codeForm
	 * @return 상세코드 수정폼 뷰
	 */
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/code/item/update.do", method=RequestMethod.GET)
	public String updateCodeGet(Model model, @ModelAttribute("codeForm") Code codeForm){
		model.addAttribute("formMode", "UPDATE");
		CodeCategorySearch codeCategorySearch = new CodeCategorySearch();
		codeCategorySearch.setPaging(false);
		List<CodeCategory> codeCategoryList = codeService.getCodeCategoryList(codeCategorySearch);
		model.addAttribute("codeCategoryList", codeCategoryList);
		model.addAttribute("codeForm", codeService.getCode(codeForm));
		return "asapro/admin/code/codeForm";
	}
	
	/**
	 * 상세코드를 수정한다.
	 * @param model
	 * @param codeForm
	 * @param bindingResult
	 * @return 수정 폼 뷰
	 */
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/code/item/update.do", method=RequestMethod.POST)
	public String updateCodePost(Model model, RedirectAttributes redirectAttributes, @CurrentSite Site currentSite, @ModelAttribute("codeForm") Code codeForm, BindingResult bindingResult){
		codeValidator.validate(codeForm, bindingResult, "UPDATE");
		if(bindingResult.hasErrors()){
			model.addAttribute("formMode", "UPDATE");
			CodeCategorySearch codeCategorySearch = new CodeCategorySearch();
			codeCategorySearch.setPaging(false);
			List<CodeCategory> codeCategoryList = codeService.getCodeCategoryList(codeCategorySearch);
			model.addAttribute("codeCategoryList", codeCategoryList);
			return "asapro/admin/code/codeForm";
		} else {
			codeService.updateCode(codeForm);
			redirectAttributes.addFlashAttribute("updated", true);
			return "redirect:" + AsaproUtils.getAdminPath(currentSite, true) + "/code/item/update.do?codeId=" + codeForm.getCodeId() + "&codeCategory.catId=" + codeForm.getCodeCategory().getCatId();
		}
	}
	
	/**
	 * 상세코드를 삭제한다.
	 * @param model
	 * @param codeIds
	 * @return 삭제 결과
	 */
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/code/item/delete.do", method=RequestMethod.POST)
	@ResponseBody
	public ServerMessage deleteCodePost(Model model, @RequestParam(value="codeIds[]", required=true) String[] codeIds){
		ServerMessage serverMessage = new ServerMessage();
		if( ArrayUtils.isEmpty(codeIds) ){
			serverMessage.setSuccess(false);
			serverMessage.setText("삭제할 데이터가 없습니다.");
		} else {
			int result = codeService.deleteCodes(codeIds); 
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
	 * 코드 목록을 json 으로 반환한다.
	 * @param model
	 * @param currentSite
	 * @param currentUser
	 * @param catId
	 * @return 코드 목록 json
	 */
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/code" + Constant.API_PATH + "/item/jsonList.do", method=RequestMethod.GET)
	@ResponseBody
	public List<Code> codeJsonListGet(Model model, @RequestParam(value="catId", required=true) String catId){
		List<Code> codeList = codeService.getCodeList(catId);
		return codeList;
	}
	
	/**
	 * 상세코드를 정렬순서를 변경한다.
	 * @param model
	 * @param codeIds
	 * @return 변경 결과
	 */
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/code/item/reOrder.do", method=RequestMethod.POST)
	@ResponseBody
	public ServerMessage updateCodeOrderPost(Model model, @RequestParam(value="codeIds[]", required=true) String[] codeIds){
		//'@'를 구분자로 카테고리 아이디@코드아이디의 형태로 구성되어 있음
		ServerMessage serverMessage = new ServerMessage();
		if( ArrayUtils.isEmpty(codeIds) ){
			serverMessage.setSuccess(false);
			serverMessage.setText("변경할 데이터가 없습니다.");
		} else {
			int order = 0;
			List<Code> codes = new ArrayList<Code>();
			Code code = null;
			String[] temp = null;
			
			for(String codeId : codeIds){
				temp =  codeId.split("@",2);
				if(temp.length != 2){
					continue;
				}
				order ++;
				code = new Code();
				CodeCategory codeCategory = new CodeCategory();
				codeCategory.setCatId(temp[0]);
				code.setCodeCategory(codeCategory);
				code.setCodeId(temp[1]);
				code.setCodeOrder(order);
				codes.add(code);
			}
			
			int result = codeService.updateCodeOrder(codes); 
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
	
}
