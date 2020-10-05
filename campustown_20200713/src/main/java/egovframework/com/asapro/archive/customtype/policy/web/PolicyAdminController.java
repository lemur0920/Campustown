/**
 * 
 */
package egovframework.com.asapro.archive.customtype.policy.web;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.FlashMap;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

import egovframework.com.asapro.archive.customtype.policy.service.Policy;
import egovframework.com.asapro.archive.customtype.policy.service.PolicySearch;
import egovframework.com.asapro.archive.customtype.policy.service.PolicyService;
import egovframework.com.asapro.archive.service.ArchiveCategory;
import egovframework.com.asapro.archive.service.ArchiveService;
import egovframework.com.asapro.code.service.Code;
import egovframework.com.asapro.code.service.CodeCategory;
import egovframework.com.asapro.code.service.CodeService;
import egovframework.com.asapro.fileinfo.service.FileInfoUploadResult;
import egovframework.com.asapro.member.admin.service.AdminMember;
import egovframework.com.asapro.site.service.Site;
import egovframework.com.asapro.support.Constant;
import egovframework.com.asapro.support.annotation.CurrentAdmin;
import egovframework.com.asapro.support.annotation.CurrentSite;
import egovframework.com.asapro.support.exception.AsaproException;
import egovframework.com.asapro.support.pagination.Paging;
import egovframework.com.asapro.support.util.AsaproUtils;

/**
 * 정책자료 관리자 컨트롤러
 * 커스텀 아카이브
 * @author yckim
 * @since 2019. 12. 27.
 *
 */
@Controller
public class PolicyAdminController {
	
	@Autowired
	private ArchiveService archiveService;
	
	@Autowired
	private PolicyService policyService;
	
	@Autowired
	private PolicyValidator policyValidator;
	
	@Autowired
	private CodeService codeService;
	
	@Autowired
	private HttpServletRequest request;
	
	
	
	/**
	 * 메타코드 로드 및 기타 공통코드 로드
	 * @param model
	 * @param archiveCategory
	 */
	private void loadCode(Model model, ArchiveCategory archiveCategory){
		CodeCategory arcMetaCode1Info = null;
		List<Code> arcMetaCode1List = null;
		CodeCategory arcMetaCode2Info = null;
		List<Code> arcMetaCode2List = null;
		CodeCategory arcMetaCode3Info = null;
		List<Code> arcMetaCode3List = null;
		
		if( StringUtils.isNotBlank(archiveCategory.getCatMetaCode1()) ){
			arcMetaCode1Info = codeService.getCodeCategory(archiveCategory.getCatMetaCode1());
			arcMetaCode1List = codeService.getCodeList(archiveCategory.getCatMetaCode1());
			model.addAttribute("arcMetaCode1Info", arcMetaCode1Info );
			model.addAttribute("arcMetaCode1List", arcMetaCode1List );
		}
		if( StringUtils.isNotBlank(archiveCategory.getCatMetaCode2()) ){
			arcMetaCode2Info = codeService.getCodeCategory(archiveCategory.getCatMetaCode2());
			arcMetaCode2List = codeService.getCodeList(archiveCategory.getCatMetaCode2());
			model.addAttribute("arcMetaCode2Info", arcMetaCode2Info );
			model.addAttribute("arcMetaCode2List", arcMetaCode2List );
		}
		if( StringUtils.isNotBlank(archiveCategory.getCatMetaCode3()) ){
			arcMetaCode3Info = codeService.getCodeCategory(archiveCategory.getCatMetaCode3());
			arcMetaCode3List = codeService.getCodeList(archiveCategory.getCatMetaCode3());
			model.addAttribute("arcMetaCode3Info", arcMetaCode3Info );
			model.addAttribute("arcMetaCode3List", arcMetaCode3List );
		}
		
		
	}
	
	//=============================================================================================================================
	//==========================================  정책자료    ================================================================
	//=============================================================================================================================
	
	
	/**
	 * 정책자료 목록을 조회한다.
	 * @param model
	 * @param currentSite
	 * @param policySearch
	 * @return 정책자료 목록
	 */
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/archive/policy/list.do", method=RequestMethod.GET)
	public String policyListGet(Model model, @CurrentSite Site currentSite, @ModelAttribute("policySearch") PolicySearch policySearch){
		//카테고리 조회
		ArchiveCategory currentCategory = null;
		if( StringUtils.isNotBlank(policySearch.getArcCategory()) ){
			currentCategory = new ArchiveCategory();
			currentCategory.setSitePrefix(currentSite.getSitePrefix());
			currentCategory.setCatId(policySearch.getArcCategory());
			currentCategory = archiveService.getArchiveCategory(currentCategory);
			
			//메타코드 로드
			this.loadCode(model, currentCategory);
			
			//기본 정렬 세팅 - 파라메터에서 넘어온 정렬값이 있으면 적용되지 않는다.
			policySearch.setDefaultSort(currentCategory.getCatSortOrder(), currentCategory.getCatSortDirection());

		}
		model.addAttribute("archiveCategory", currentCategory);
		
		//정책자료 목록 조회
		policySearch.setSitePrefix(currentSite.getSitePrefix());
		policySearch.fixBrokenSvByDefaultCharsets();
		policySearch.setPaging(true);
		
		List<Policy> list = policyService.getPolicyList(policySearch);
		int total = policyService.getPolicyListTotal(policySearch);
		Paging paging = new Paging(list, total, policySearch);
		model.addAttribute("paging", paging);
		
		//코드카테고리
		/*CodeCategorySearch codeCategorySearch = new CodeCategorySearch();
		codeCategorySearch.setPaging(false);
		model.addAttribute("codeCatList", codeService.getCodeCategoryList(codeCategorySearch));*/
		
		//템플릿 파일 적용
		if(currentCategory != null && StringUtils.isNotBlank(currentCategory.getCatAdminListTemplate()) ){
			return archiveService.getArchiveTemplateName(currentCategory.getCatCustomType(), currentCategory.getCatAdminListTemplate(), "admin");
		}
		return "asapro/admin/archive/policy/list";
	}
	
	/**
	 * 정책자료 등록 폼 뷰를 반환한다.
	 * @param model
	 * @param currentSite
	 * @param policyForm
	 * @return 추가폼뷰
	 */
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/archive/policy/insert.do", method=RequestMethod.GET)
	public String policyInsertGet(Model model, @CurrentSite Site currentSite, @ModelAttribute("policyForm") Policy policyForm) {
		
		//카테고리 조회
		ArchiveCategory currentCategory = null;
		if( StringUtils.isNotBlank(policyForm.getArcCategory()) ){
			currentCategory = new ArchiveCategory();
			currentCategory.setSitePrefix(currentSite.getSitePrefix());
			currentCategory.setCatId(policyForm.getArcCategory());
			currentCategory = archiveService.getArchiveCategory(currentCategory);
			
			//메타코드 로드
			this.loadCode(model, currentCategory);
			
		}
		model.addAttribute("archiveCategory", currentCategory);
		
		//공공누리코드목록
		//model.addAttribute("nuriCodeList", codeService.getCodeList("NURI_TYPE"));
		
		model.addAttribute("formMode", "INSERT");
		
		//템플릿 파일 적용
		if(currentCategory != null && StringUtils.isNotBlank(currentCategory.getCatAdminFormTemplate()) ){
			return archiveService.getArchiveTemplateName(currentCategory.getCatCustomType(), currentCategory.getCatAdminFormTemplate(), "admin");
		}
		return "asapro/admin/archive/policy/form";
	}
	
	/**
	 * 정책자료를 등록한다.
	 * @param model
	 * @param redirectAttributes
	 * @param currentSite
	 * @param policyForm
	 * @param bindingResult
	 * @return 등록결과
	 * @throws AsaproException
	 * @throws IOException
	 */
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/archive/policy/insert.do", method=RequestMethod.POST)
	public String policyInsertPost(Model model, RedirectAttributes redirectAttributes, @CurrentAdmin AdminMember currentAdmin, @CurrentSite Site currentSite, @ModelAttribute("policyForm") Policy policyForm, BindingResult bindingResult) throws AsaproException, IOException {

		policyForm.setSitePrefix(currentSite.getSitePrefix());
		policyForm.setArcRegdate(new Date());
		policyForm.setMemId(currentAdmin.getAdminId());
		policyForm.setArcLastUpdate(new Date());
		
		//카테고리 조회
		ArchiveCategory currentCategory = null;
		if( StringUtils.isNotBlank(policyForm.getArcCategory()) ){
			currentCategory = new ArchiveCategory();
			currentCategory.setSitePrefix(currentSite.getSitePrefix());
			currentCategory.setCatId(policyForm.getArcCategory());
			currentCategory = archiveService.getArchiveCategory(currentCategory);
			
			//메타코드 로드
			this.loadCode(model, currentCategory);
			
		}
		model.addAttribute("archiveCategory", currentCategory);
		policyForm.setArchiveCategory(currentCategory);
		
		if(currentCategory != null){
			//아카이브 타입등록
			policyForm.setArcCustomType(currentCategory.getCatCustomType());
		}
		policyValidator.validate(policyForm, bindingResult, "INSERT");
		
		if( bindingResult.hasErrors() ){
			
			//공공누리코드목록
			//model.addAttribute("nuriCodeList", codeService.getCodeList("NURI_TYPE"));
			
			model.addAttribute("formMode", "INSERT");
			model.addAttribute("insertFail", true);
			
			//템플릿 파일 적용
			if(currentCategory != null && StringUtils.isNotBlank(currentCategory.getCatAdminFormTemplate()) ){
				return archiveService.getArchiveTemplateName(currentCategory.getCatCustomType(), currentCategory.getCatAdminFormTemplate(), "admin");
			}
			return "asapro/admin/archive/policy/form";
		} else {
			
			Map<String, FileInfoUploadResult> fileInfoUploadResultMap = policyService.insertPolicy(policyForm);
			
			//파일 업로드 에러 있으면 바로 수정화면으로 전환 - 에러난 파일 제외하고 글과 다른 파일들은 저장된 상태임.
			if(fileInfoUploadResultMap.get("thumbFileInfoUploadResult").hasErrors() || fileInfoUploadResultMap.get("fileInfoUploadResult").hasErrors() ){
				FlashMap flashMap = RequestContextUtils.getOutputFlashMap(request);
				
				if(fileInfoUploadResultMap.get("thumbFileInfoUploadResult").hasErrors() ){
					flashMap.put("thumbFileInfoUploadResult", fileInfoUploadResultMap.get("thumbFileInfoUploadResult"));
				}
				if(fileInfoUploadResultMap.get("fileInfoUploadResult").hasErrors() ){
					flashMap.put("fileInfoUploadResult", fileInfoUploadResultMap.get("fileInfoUploadResult"));
				}
				return "redirect:" + AsaproUtils.getAdminPath(currentSite, true) +  "/archive/policy/update.do?arcId=" + policyForm.getArcId() + "&arcCategory=" + policyForm.getArcCategory();
			}
			
			redirectAttributes.addFlashAttribute("inserted", true);
			return "redirect:" + AsaproUtils.getAdminPath(currentSite, true) + "/archive/policy/list.do?arcCategory=" + policyForm.getArcCategory();
		}
		
	}
	
	/**
	 * 정책자료 수정 폼 뷰를 반환한다.
	 * @param model
	 * @param currentSite
	 * @param policyForm
	 * @return 수정폼뷰 
	 */
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/archive/policy/update.do", method=RequestMethod.GET)
	public String policyUpdateGet(Model model, @CurrentSite Site currentSite, @ModelAttribute("policyForm") Policy policyForm) {
		//정책자료 조회
		policyForm.setSitePrefix(currentSite.getSitePrefix());
		Policy policyModel = policyService.getPolicy(policyForm);
		policyModel.setSitePrefix(currentSite.getSitePrefix());
		model.addAttribute("policyForm", policyModel);

		//카테고리 조회
		ArchiveCategory currentCategory = null;
		if( StringUtils.isNotBlank(policyModel.getArcCategory()) ){
			currentCategory = new ArchiveCategory();
			currentCategory.setSitePrefix(currentSite.getSitePrefix());
			currentCategory.setCatId(policyModel.getArcCategory());
			currentCategory = archiveService.getArchiveCategory(currentCategory);
			
			//메타코드 로드
			this.loadCode(model, currentCategory);
			
		}
		model.addAttribute("archiveCategory", currentCategory);
		
		
		//공공누리코드목록
		//model.addAttribute("nuriCodeList", codeService.getCodeList("NURI_TYPE"));
		
		model.addAttribute("formMode", "UPDATE");
		
		//파일 업로드 에러에 대한 정보가 flashMap에 들어 있는지 확인
		Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);
		if( flashMap != null &&  flashMap.get("fileInfoUploadResult") != null ){
			FileInfoUploadResult fileInfoUploadResult = (FileInfoUploadResult) flashMap.get("fileInfoUploadResult");
			model.addAttribute("fileInfoUploadResult", fileInfoUploadResult);
		}
		
		//템플릿 파일 적용
		if(currentCategory != null && StringUtils.isNotBlank(currentCategory.getCatAdminFormTemplate()) ){
			return archiveService.getArchiveTemplateName(currentCategory.getCatCustomType(), currentCategory.getCatAdminFormTemplate(), "admin");
		}
		return "asapro/admin/archive/policy/form";
	}
	
	/**
	 * 정책자료를 수정한다.
	 * @param model
	 * @param redirectAttributes
	 * @param currentSite
	 * @param policyForm
	 * @param bindingResult
	 * @return 수정결과
	 * @throws AsaproException
	 * @throws IOException
	 */
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/archive/policy/update.do", method=RequestMethod.POST)
	public String policyUpdatePost(Model model, RedirectAttributes redirectAttributes, @CurrentAdmin AdminMember currentAdmin, @CurrentSite Site currentSite, @ModelAttribute("policyForm") Policy policyForm, BindingResult bindingResult) throws AsaproException, IOException {

		policyForm.setSitePrefix(currentSite.getSitePrefix());
		policyForm.setArcLastUpdate(new Date());
		
		//카테고리 조회
		ArchiveCategory currentCategory = null;
		if( StringUtils.isNotBlank(policyForm.getArcCategory()) ){
			currentCategory = new ArchiveCategory();
			currentCategory.setSitePrefix(currentSite.getSitePrefix());
			currentCategory.setCatId(policyForm.getArcCategory());
			currentCategory = archiveService.getArchiveCategory(currentCategory);
			
			//메타코드 로드
			this.loadCode(model, currentCategory);
			
		}
		model.addAttribute("archiveCategory", currentCategory);
		policyForm.setArchiveCategory(currentCategory);
		if(currentCategory != null){
			//아카이브 타입등록
			policyForm.setArcCustomType(currentCategory.getCatCustomType());
		}

		policyValidator.validate(policyForm, bindingResult, "UPDATE");
		
		if( bindingResult.hasErrors() ){
			
			
			//공공누리코드목록
			//model.addAttribute("nuriCodeList", codeService.getCodeList("NURI_TYPE"));
			
			model.addAttribute("formMode", "UPDATE");
			model.addAttribute("updateFail", true);
			
			//템플릿 파일 적용
			if(currentCategory != null && StringUtils.isNotBlank(currentCategory.getCatAdminFormTemplate()) ){
				return archiveService.getArchiveTemplateName(currentCategory.getCatCustomType(), currentCategory.getCatAdminFormTemplate(), "admin");
			}
			return "asapro/admin/archive/policy/form";
		} else {
			
			Map<String, FileInfoUploadResult> fileInfoUploadResultMap = policyService.updatePolicy(policyForm);
			
			//파일 업로드 에러 있으면 바로 수정화면으로 전환 - 에러난 파일 제외하고 글과 다른 파일들은 저장된 상태임.
			if(fileInfoUploadResultMap.get("thumbFileInfoUploadResult").hasErrors() || fileInfoUploadResultMap.get("fileInfoUploadResult").hasErrors() ){
				FlashMap flashMap = RequestContextUtils.getOutputFlashMap(request);
				
				if(fileInfoUploadResultMap.get("thumbFileInfoUploadResult").hasErrors() ){
					flashMap.put("thumbFileInfoUploadResult", fileInfoUploadResultMap.get("thumbFileInfoUploadResult"));
				}
				if(fileInfoUploadResultMap.get("fileInfoUploadResult").hasErrors() ){
					flashMap.put("fileInfoUploadResult", fileInfoUploadResultMap.get("fileInfoUploadResult"));
				}
				return "redirect:" + AsaproUtils.getAdminPath(currentSite, true) +  "/archive/policy/update.do?arcId=" + policyForm.getArcId() + "&arcCategory=" + policyForm.getArcCategory();
			}
			
			redirectAttributes.addFlashAttribute("updated", true);
			return "redirect:" + AsaproUtils.getAdminPath(currentSite, true) + "/archive/policy/update.do?arcId=" + policyForm.getArcId() + "&arcCategory=" + policyForm.getArcCategory();
		}
		
	}
	
	/**
	 * 정책자료 삭제는 아카이브 삭제를 사용
	 * DB간 cascade 삭제된다
	 */
	
}
