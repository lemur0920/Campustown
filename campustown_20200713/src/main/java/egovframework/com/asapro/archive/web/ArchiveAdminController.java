/**
 * 
 */
package egovframework.com.asapro.archive.web;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.FlashMap;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

import egovframework.com.asapro.archive.service.Archive;
import egovframework.com.asapro.archive.service.ArchiveCategory;
import egovframework.com.asapro.archive.service.ArchiveCategorySearch;
import egovframework.com.asapro.archive.service.ArchiveSearch;
import egovframework.com.asapro.archive.service.ArchiveService;
import egovframework.com.asapro.code.service.Code;
import egovframework.com.asapro.code.service.CodeCategorySearch;
import egovframework.com.asapro.code.service.CodeService;
import egovframework.com.asapro.fileinfo.service.FileInfo;
import egovframework.com.asapro.fileinfo.service.FileInfoUploadResult;
import egovframework.com.asapro.member.admin.service.AdminMember;
import egovframework.com.asapro.site.service.Site;
import egovframework.com.asapro.support.Constant;
import egovframework.com.asapro.support.ServerMessage;
import egovframework.com.asapro.support.annotation.CurrentAdmin;
import egovframework.com.asapro.support.annotation.CurrentSite;
import egovframework.com.asapro.support.exception.AsaproException;
import egovframework.com.asapro.support.pagination.Paging;
import egovframework.com.asapro.support.util.AsaproUtils;

/**
 * 아카이브 관리자 컨트롤러
 * @author yckim
 * @since 2018. 12. 19.
 *
 */
@Controller
public class ArchiveAdminController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ArchiveAdminController.class);
	
	@Autowired
	private ArchiveService archiveService;
	
	@Autowired
	private ArchiveCategoryValidator archiveCategoryValidator;
	
	@Autowired
	private ArchiveValidator archiveValidator;
	
	@Autowired
	private CodeService codeService;
	
	@Autowired
	private HttpServletRequest request;
	
	
	//=============================================================================================================================
	//==========================================  아카이브 카테고리   ================================================================
	//=============================================================================================================================
	
	/**
	 * 아카이브 카테고리 목록을 조회한다.
	 * @param model
	 * @param currentSite
	 * @param archiveCategorySearch
	 * @return 아카이브 카테고리 목록
	 */
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/archive/category/list.do", method=RequestMethod.GET)
	public String archiveCategoryListGet(Model model, @CurrentSite Site currentSite, @ModelAttribute("archiveCategorySearch") ArchiveCategorySearch archiveCategorySearch){
		archiveCategorySearch.setSitePrefix(currentSite.getSitePrefix());
		archiveCategorySearch.fixBrokenSvByDefaultCharsets();
		archiveCategorySearch.setPaging(true);
		
		List<ArchiveCategory> list = archiveService.getArchiveCategoryList(archiveCategorySearch);
		int total = archiveService.getArchiveCategoryListTotal(archiveCategorySearch);
		Paging paging = new Paging(list, total, archiveCategorySearch);
		model.addAttribute("paging", paging);
		
		//코드카테고리
		CodeCategorySearch codeCategorySearch = new CodeCategorySearch();
		codeCategorySearch.setPaging(false);
		model.addAttribute("codeCatList", codeService.getCodeCategoryList(codeCategorySearch));
		
		//아카이브 타입목록
		List<Map<String, String>> archiveItemList = archiveService.getArchiveItemAnnotationedList();
		model.addAttribute("archiveItemList", archiveItemList);
		
		return "asapro/admin/archive/categoryList";
	}
	
	/**
	 * 아카이브 카테고리 등록 폼 뷰를 반환한다.
	 * @param model
	 * @param currentSite
	 * @param archiveCategoryForm
	 * @return 추가폼뷰
	 * @throws FileNotFoundException 
	 */
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/archive/category/insert.do", method=RequestMethod.GET)
	public String archiveCategoryInsertGet(Model model, @CurrentSite Site currentSite, @ModelAttribute("archiveCategoryForm") ArchiveCategory archiveCategoryForm) throws FileNotFoundException {
		//코드카테고리
		CodeCategorySearch codeCategorySearch = new CodeCategorySearch();
		codeCategorySearch.setPaging(false);
		model.addAttribute("codeCatList", codeService.getCodeCategoryList(codeCategorySearch));
		
		model.addAttribute("formMode", "INSERT");
		
		//아카이브 타입목록
		List<Map<String, String>> archiveItemList = archiveService.getArchiveItemAnnotationedList();
		model.addAttribute("archiveItemList", archiveItemList);
		
		return "asapro/admin/archive/categoryForm";
	}
	
	/**
	 * 아카이브 카테고리를 등록한다.
	 * @param model
	 * @param redirectAttributes
	 * @param currentSite
	 * @param archiveCategoryForm
	 * @param bindingResult
	 * @return 등록결과
	 * @throws AsaproException
	 * @throws IOException
	 */
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/archive/category/insert.do", method=RequestMethod.POST)
	public String archiveCategoryInsertPost(Model model, RedirectAttributes redirectAttributes, @CurrentSite Site currentSite, @ModelAttribute("archiveCategoryForm") ArchiveCategory archiveCategoryForm, BindingResult bindingResult) throws AsaproException, IOException {

		archiveCategoryForm.setSitePrefix(currentSite.getSitePrefix());
		archiveCategoryValidator.validate(archiveCategoryForm, bindingResult, "INSERT");
		
		if( bindingResult.hasErrors() ){
			
			//코드카테고리
			CodeCategorySearch codeCategorySearch = new CodeCategorySearch();
			codeCategorySearch.setPaging(false);
			model.addAttribute("codeCatList", codeService.getCodeCategoryList(codeCategorySearch));
			
			//아카이브 타입목록
			List<Map<String, String>> archiveItemList = archiveService.getArchiveItemAnnotationedList();
			model.addAttribute("archiveItemList", archiveItemList);
			
			//템플릿 목록
			//webRoot + dirRoot 내 있는 파일목록
			String dirRoot = "";
			String customType = archiveCategoryForm.getCatCustomType();
			dirRoot = Constant.THEME_ROOT + request.getAttribute("theme") + "/archive/" + customType;
			List<String> arcUserTemplateList = AsaproUtils.getJspFileList(request, dirRoot);
			dirRoot = Constant.ADMIN_ROOT + "archive/" + customType;
			List<String> arcAdminTemplateList = AsaproUtils.getJspFileList(request, dirRoot);
			model.addAttribute("arcUserTemplateList", arcUserTemplateList);
			model.addAttribute("arcAdminTemplateList", arcAdminTemplateList);
			
			model.addAttribute("formMode", "INSERT");
			model.addAttribute("insertFail", true);
			return "asapro/admin/archive/categoryForm";
		} else {
			archiveCategoryForm.setCatRegdate(new Date());
			
			archiveService.insertArchiveCategory(archiveCategoryForm);
			redirectAttributes.addFlashAttribute("inserted", true);
			return "redirect:" + AsaproUtils.getAdminPath(currentSite, true) + "/archive/category/list.do";
		}
		
	}
	
	/**
	 * 아카이브 카테고리 수정 폼 뷰를 반환한다.
	 * @param model
	 * @param currentSite
	 * @param archiveCategoryForm
	 * @return 수정폼 뷰
	 * @throws FileNotFoundException 
	 */
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/archive/category/update.do", method=RequestMethod.GET)
	public String archiveCategoryUpdateGet(Model model, @CurrentSite Site currentSite, @ModelAttribute("archiveCategoryForm") ArchiveCategory archiveCategoryForm) throws FileNotFoundException {
		
		archiveCategoryForm.setSitePrefix(currentSite.getSitePrefix());
		ArchiveCategory archiveCategory = archiveService.getArchiveCategory(archiveCategoryForm);
		model.addAttribute("archiveCategoryForm", archiveCategory);
		
		//코드카테고리
		CodeCategorySearch codeCategorySearch = new CodeCategorySearch();
		codeCategorySearch.setPaging(false);
		model.addAttribute("codeCatList", codeService.getCodeCategoryList(codeCategorySearch));
		
		//아카이브 타입목록
		List<Map<String, String>> archiveItemList = archiveService.getArchiveItemAnnotationedList();
		model.addAttribute("archiveItemList", archiveItemList);
		
		model.addAttribute("formMode", "UPDATE");
		
		//템플릿 목록
		//webRoot + dirRoot 내 있는 파일목록
		String dirRoot = "";
		String customType = archiveCategory.getCatCustomType();
		
		dirRoot = Constant.THEME_ROOT + request.getAttribute("theme") + "/archive/" + customType;
		List<String> arcUserTemplateList = AsaproUtils.getJspFileList(request, dirRoot);
		dirRoot = Constant.ADMIN_ROOT + "archive/" + customType;
		List<String> arcAdminTemplateList = AsaproUtils.getJspFileList(request, dirRoot);
			
		model.addAttribute("arcUserTemplateList", arcUserTemplateList);
		model.addAttribute("arcAdminTemplateList", arcAdminTemplateList);
		
		return "asapro/admin/archive/categoryForm";
	}
	
	/**
	 * 아카이브 카테고리를 수정한다.
	 * @param model
	 * @param redirectAttributes
	 * @param currentSite
	 * @param archiveCategoryForm
	 * @param bindingResult
	 * @return 수정결과
	 * @throws AsaproException
	 * @throws IOException
	 */
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/archive/category/update.do", method=RequestMethod.POST)
	public String archiveCategoryUpdatePost(Model model, RedirectAttributes redirectAttributes, @CurrentSite Site currentSite, @ModelAttribute("archiveCategoryForm") ArchiveCategory archiveCategoryForm, BindingResult bindingResult) throws AsaproException, IOException {

		archiveCategoryForm.setSitePrefix(currentSite.getSitePrefix());
		archiveCategoryValidator.validate(archiveCategoryForm, bindingResult, "UPDATE");
		
		if( bindingResult.hasErrors() ){
			
			//코드카테고리
			CodeCategorySearch codeCategorySearch = new CodeCategorySearch();
			codeCategorySearch.setPaging(false);
			model.addAttribute("codeCatList", codeService.getCodeCategoryList(codeCategorySearch));
			
			//아카이브 타입목록
			List<Map<String, String>> archiveItemList = archiveService.getArchiveItemAnnotationedList();
			model.addAttribute("archiveItemList", archiveItemList);
			
			//템플릿 목록
			//webRoot + dirRoot 내 있는 파일목록
			String dirRoot = "";
			String customType = archiveCategoryForm.getCatCustomType();
			dirRoot = Constant.THEME_ROOT + request.getAttribute("theme") + "/archive/" + customType;
			List<String> arcUserTemplateList = AsaproUtils.getJspFileList(request, dirRoot);
			dirRoot = Constant.ADMIN_ROOT + "archive/" + customType;
			List<String> arcAdminTemplateList = AsaproUtils.getJspFileList(request, dirRoot);
			model.addAttribute("arcUserTemplateList", arcUserTemplateList);
			model.addAttribute("arcAdminTemplateList", arcAdminTemplateList);
			
			model.addAttribute("formMode", "UPDATE");
			model.addAttribute("updateFail", true);
			return "asapro/admin/archive/categoryForm";
		} else {
			
			archiveService.updateArchiveCategory(archiveCategoryForm);
			redirectAttributes.addFlashAttribute("updated", true);
			return "redirect:" + AsaproUtils.getAdminPath(currentSite, true) + "/archive/category/update.do?catId=" + archiveCategoryForm.getCatId();
		}
		
	}
	
	/**
	 * 아카이브 카테고리를 삭제한다.
	 *  - 카테고리 내 아카이브는 데이터 보존을 위해 삭제하지 않는다.
	 * @param model
	 * @param currentSite
	 * @param catIds
	 * @return 삭제결과
	 * @throws FileNotFoundException
	 */
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/archive/category/delete.do", method=RequestMethod.POST)
	@ResponseBody
	public ServerMessage deleteArchiveCategoryPost(Model model, @CurrentSite Site currentSite, @RequestParam(value="catIds[]", required=true) String[] catIds) throws FileNotFoundException{
		ServerMessage serverMessage = new ServerMessage();
		if( ArrayUtils.isEmpty(catIds) ){
			serverMessage.setSuccess(false);
			serverMessage.setText("삭제할 항목이 없습니다.");
		} else {
			List<ArchiveCategory> archiveCategoryList = new ArrayList<ArchiveCategory>();
			ArchiveCategory archiveCategory = null;
			for( String catId : catIds ){
				archiveCategory = new ArchiveCategory();
				archiveCategory.setSitePrefix(currentSite.getSitePrefix());
				archiveCategory.setCatId(catId);
				archiveCategoryList.add(archiveCategory);
			}
			int result = archiveService.deleteArchiveCategory(archiveCategoryList);
			if(result > 0){
				serverMessage.setSuccess(true);
				serverMessage.setSuccessCnt(result);
			} else {
				serverMessage.setSuccess(false);
				serverMessage.setText("삭제하지 못하였습니다.[Server Error]");
			}
		}
		return serverMessage;
	}
	
	/**
	 * 아카이브 유형별 관리자, 사용자 템플릿 목록을 반환한다.
	 * @param model
	 * @param currentSite
	 * @param customType
	 * @param gradeMember
	 * @return 템플릿 목록
	 * @throws FileNotFoundException
	 */
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/archive" + Constant.API_PATH + "/{gradeMember}/template/jsonList.do", method=RequestMethod.GET)
	@ResponseBody
	public List<String> getAdminTemplateListGet(Model model, @CurrentSite Site currentSite, @RequestParam(value="customType", required=true) String customType, @PathVariable String gradeMember ) throws FileNotFoundException {
		//gradeMember (admin, user)
		
		//webRoot + dirRoot 내 있는 파일목록
		String dirRoot = "";
		
		if("user".equals(gradeMember)){
			dirRoot = Constant.THEME_ROOT + request.getAttribute("theme") + "/archive/" + customType;
		} else {//admin
			dirRoot = Constant.ADMIN_ROOT + "archive/" + customType;
		}
		List<String> arcTemplateList = AsaproUtils.getJspFileList(request, dirRoot);
		return arcTemplateList;
	}
	
	
	
	//=============================================================================================================================
	//==========================================  아카이브    ================================================================
	//=============================================================================================================================
	
	/**
	 * 메타코드 로드 및 기타 공통코드 로드
	 * @param model
	 * @param archiveCategory
	 */
	private void loadCode(Model model, ArchiveCategory archiveCategory){
		if( StringUtils.isNotBlank(archiveCategory.getCatMetaCode1()) ){
			model.addAttribute("arcMetaCode1Info", codeService.getCodeCategory(archiveCategory.getCatMetaCode1()) );
			model.addAttribute("arcMetaCode1List", codeService.getCodeList(archiveCategory.getCatMetaCode1()) );
		}
		if( StringUtils.isNotBlank(archiveCategory.getCatMetaCode2()) ){
			model.addAttribute("arcMetaCode2Info", codeService.getCodeCategory(archiveCategory.getCatMetaCode2()) );
			model.addAttribute("arcMetaCode2List", codeService.getCodeList(archiveCategory.getCatMetaCode2()) );
		}
		if( StringUtils.isNotBlank(archiveCategory.getCatMetaCode3()) ){
			model.addAttribute("arcMetaCode3Info", codeService.getCodeCategory(archiveCategory.getCatMetaCode3()) );
			model.addAttribute("arcMetaCode3List", codeService.getCodeList(archiveCategory.getCatMetaCode3()) );
		}
		
		
	}
	

	
	/**
	 * 아카이브 목록을 조회한다.
	 * @param model
	 * @param currentSite
	 * @param archiveCategorySearch
	 * @return 아카이브 목록
	 */
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/archive/list.do", method=RequestMethod.GET)
	public String archiveListGet(Model model, @CurrentSite Site currentSite, @ModelAttribute("archiveSearch") ArchiveSearch archiveSearch){
		//카테고리 조회
		ArchiveCategory currentCategory = null;
		if( StringUtils.isNoneBlank(archiveSearch.getArcCategory()) ){
			currentCategory = new ArchiveCategory();
			currentCategory.setSitePrefix(currentSite.getSitePrefix());
			currentCategory.setCatId(archiveSearch.getArcCategory());
			currentCategory = archiveService.getArchiveCategory(currentCategory);
			
			//메타코드 로드
			this.loadCode(model, currentCategory);
			
			//기본 정렬 세팅 - 파라메터에서 넘어온 정렬값이 있으면 적용되지 않는다.
			archiveSearch.setDefaultSort(currentCategory.getCatSortOrder(), currentCategory.getCatSortDirection());

		}
		model.addAttribute("archiveCategory", currentCategory);
		
		//아카이브 목록 조회
		archiveSearch.setSitePrefix(currentSite.getSitePrefix());
		archiveSearch.fixBrokenSvByDefaultCharsets();
		archiveSearch.setPaging(true);
		
		List<Archive> list = archiveService.getArchiveList(archiveSearch);
		int total = archiveService.getArchiveListTotal(archiveSearch);
		Paging paging = new Paging(list, total, archiveSearch);
		model.addAttribute("paging", paging);
		
		//코드카테고리
		CodeCategorySearch codeCategorySearch = new CodeCategorySearch();
		codeCategorySearch.setPaging(false);
		model.addAttribute("codeCatList", codeService.getCodeCategoryList(codeCategorySearch));
		
		//공공누리코드목록
		//model.addAttribute("nuriCodeList", codeService.getCodeList("NURI_TYPE"));
		
		//템플릿 파일 적용
		if(currentCategory != null && StringUtils.isNotBlank(currentCategory.getCatAdminListTemplate()) ){
			return archiveService.getArchiveTemplateName(currentCategory.getCatCustomType(), currentCategory.getCatAdminListTemplate(), "admin");
		}
		
		return "asapro/admin/archive/" + currentCategory.getCatCustomType() + "/list";
	}
	
	/**
	 * 아카이브 등록 폼 뷰를 반환한다.
	 * @param model
	 * @param currentSite
	 * @param archiveForm
	 * @return 추가폼뷰
	 */
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/archive/insert.do", method=RequestMethod.GET)
	public String archiveInsertGet(Model model, @CurrentSite Site currentSite, @ModelAttribute("archiveForm") Archive archiveForm) {
		
		//카테고리 조회
		ArchiveCategory currentCategory = null;
		if( StringUtils.isNotBlank(archiveForm.getArcCategory()) ){
			currentCategory = new ArchiveCategory();
			currentCategory.setSitePrefix(currentSite.getSitePrefix());
			currentCategory.setCatId(archiveForm.getArcCategory());
			currentCategory = archiveService.getArchiveCategory(currentCategory);
			
			//메타코드 로드
			this.loadCode(model, currentCategory);
			
		}
		model.addAttribute("archiveCategory", currentCategory);
		
		//공공누리코드목록
		model.addAttribute("nuriCodeList", codeService.getCodeList("NURI_TYPE"));
		
		model.addAttribute("formMode", "INSERT");
		
		//템플릿 파일 적용
		if(currentCategory != null && StringUtils.isNotBlank(currentCategory.getCatAdminFormTemplate()) ){
			return archiveService.getArchiveTemplateName(currentCategory.getCatCustomType(), currentCategory.getCatAdminFormTemplate(), "admin");
		}
		
		return "asapro/admin/archive/" + currentCategory.getCatCustomType() + "/form";
		
	}
	
	/**
	 * 아카이브를 등록한다.
	 * @param model
	 * @param redirectAttributes
	 * @param currentSite
	 * @param archiveCategoryForm
	 * @param bindingResult
	 * @return 등록결과
	 * @throws AsaproException
	 * @throws IOException
	 */
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/archive/insert.do", method=RequestMethod.POST)
	public String archiveInsertPost(Model model, RedirectAttributes redirectAttributes, @CurrentAdmin AdminMember currentAdmin, @CurrentSite Site currentSite, @ModelAttribute("archiveForm") Archive archiveForm, BindingResult bindingResult) throws AsaproException, IOException {

		archiveForm.setSitePrefix(currentSite.getSitePrefix());
		archiveForm.setArcRegdate(new Date());
		archiveForm.setMemId(currentAdmin.getAdminId());
		archiveForm.setArcLastUpdate(new Date());
		
		//카테고리 조회
		ArchiveCategory currentCategory = null;
		if( StringUtils.isNotBlank(archiveForm.getArcCategory()) ){
			currentCategory = new ArchiveCategory();
			currentCategory.setSitePrefix(currentSite.getSitePrefix());
			currentCategory.setCatId(archiveForm.getArcCategory());
			currentCategory = archiveService.getArchiveCategory(currentCategory);
			
			//메타코드 로드
			this.loadCode(model, currentCategory);
			
		}
		model.addAttribute("archiveCategory", currentCategory);
		archiveForm.setArchiveCategory(currentCategory);

		archiveValidator.validate(archiveForm, bindingResult, "INSERT");
		
		if( bindingResult.hasErrors() ){
			
			//공공누리코드목록
			model.addAttribute("nuriCodeList", codeService.getCodeList("NURI_TYPE"));
			
			model.addAttribute("formMode", "INSERT");
			model.addAttribute("insertFail", true);
			
			//템플릿 파일 적용
			if(currentCategory != null && StringUtils.isNotBlank(currentCategory.getCatAdminListTemplate()) ){
				return archiveService.getArchiveTemplateName(currentCategory.getCatCustomType(), currentCategory.getCatAdminFormTemplate(), "admin");
			}
			
			return "asapro/admin/archive/" + currentCategory.getCatCustomType() + "/form";
		} else {
			
			Map<String, FileInfoUploadResult> fileInfoUploadResultMap = archiveService.insertArchive(archiveForm);
			
			//파일 업로드 에러 있으면 바로 수정화면으로 전환 - 에러난 파일 제외하고 글과 다른 파일들은 저장된 상태임.
			if(fileInfoUploadResultMap.get("thumbFileInfoUploadResult").hasErrors() || fileInfoUploadResultMap.get("fileInfoUploadResult").hasErrors() ){
				FlashMap flashMap = RequestContextUtils.getOutputFlashMap(request);
				
				if(fileInfoUploadResultMap.get("thumbFileInfoUploadResult").hasErrors() ){
					flashMap.put("thumbFileInfoUploadResult", fileInfoUploadResultMap.get("thumbFileInfoUploadResult"));
				}
				if(fileInfoUploadResultMap.get("fileInfoUploadResult").hasErrors() ){
					flashMap.put("fileInfoUploadResult", fileInfoUploadResultMap.get("fileInfoUploadResult"));
				}
				return "redirect:" + AsaproUtils.getAdminPath(currentSite, true) +  "/archive/update.do?arcId=" + archiveForm.getArcId() + "&arcCategory=" + archiveForm.getArcCategory();
			}
			
			redirectAttributes.addFlashAttribute("inserted", true);
			return "redirect:" + AsaproUtils.getAdminPath(currentSite, true) + "/archive/list.do?arcCategory=" + archiveForm.getArcCategory();
		}
		
	}
	
	/**
	 * 아카이브 수정 폼 뷰를 반환한다.
	 * @param model
	 * @param currentSite
	 * @param archiveForm
	 * @return 수정폼뷰 
	 */
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/archive/update.do", method=RequestMethod.GET)
	public String archiveUpdateGet(Model model, @CurrentSite Site currentSite, @ModelAttribute("archiveForm") Archive archiveForm) {
		//아카이브 조회
		archiveForm.setSitePrefix(currentSite.getSitePrefix());
		Archive archiveModel = archiveService.getArchive(archiveForm);
		archiveModel.setSitePrefix(currentSite.getSitePrefix());
		model.addAttribute("archiveForm", archiveModel);

		//카테고리 조회
		ArchiveCategory currentCategory = null;
		if( StringUtils.isNotBlank(archiveModel.getArcCategory()) ){
			currentCategory = new ArchiveCategory();
			currentCategory.setSitePrefix(currentSite.getSitePrefix());
			currentCategory.setCatId(archiveModel.getArcCategory());
			currentCategory = archiveService.getArchiveCategory(currentCategory);
			
			//메타코드 로드
			this.loadCode(model, currentCategory);
			
		}
		model.addAttribute("archiveCategory", currentCategory);
		
		
		//공공누리코드목록
		model.addAttribute("nuriCodeList", codeService.getCodeList("NURI_TYPE"));
		
		model.addAttribute("formMode", "UPDATE");
		
		//파일 업로드 에러에 대한 정보가 flashMap에 들어 있는지 확인
		Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);
		if( flashMap != null &&  flashMap.get("fileInfoUploadResult") != null ){
			FileInfoUploadResult fileInfoUploadResult = (FileInfoUploadResult) flashMap.get("fileInfoUploadResult");
			model.addAttribute("fileInfoUploadResult", fileInfoUploadResult);
		}
		
		//템플릿 파일 적용
		if(currentCategory != null && StringUtils.isNotBlank(currentCategory.getCatAdminListTemplate()) ){
			return archiveService.getArchiveTemplateName(currentCategory.getCatCustomType(), currentCategory.getCatAdminFormTemplate(), "admin");
		}
		
		return "asapro/admin/archive/" + currentCategory.getCatCustomType() + "/form";
	}
	
	/**
	 * 아카이브를 수정한다.
	 * @param model
	 * @param redirectAttributes
	 * @param currentSite
	 * @param archiveCategoryForm
	 * @param bindingResult
	 * @return 수정결과
	 * @throws AsaproException
	 * @throws IOException
	 */
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/archive/update.do", method=RequestMethod.POST)
	public String archiveUpdatePost(Model model, RedirectAttributes redirectAttributes, @CurrentAdmin AdminMember currentAdmin, @CurrentSite Site currentSite, @ModelAttribute("archiveForm") Archive archiveForm, BindingResult bindingResult) throws AsaproException, IOException {

		archiveForm.setSitePrefix(currentSite.getSitePrefix());
		archiveForm.setArcLastUpdate(new Date());
		
		//카테고리 조회
		ArchiveCategory currentCategory = null;
		if( StringUtils.isNotBlank(archiveForm.getArcCategory()) ){
			currentCategory = new ArchiveCategory();
			currentCategory.setSitePrefix(currentSite.getSitePrefix());
			currentCategory.setCatId(archiveForm.getArcCategory());
			currentCategory = archiveService.getArchiveCategory(currentCategory);
			
			//메타코드 로드
			this.loadCode(model, currentCategory);
			
		}
		model.addAttribute("archiveCategory", currentCategory);
		archiveForm.setArchiveCategory(currentCategory);

		archiveValidator.validate(archiveForm, bindingResult, "UPDATE");
		
		if( bindingResult.hasErrors() ){
			
			//공공누리코드목록
			model.addAttribute("nuriCodeList", codeService.getCodeList("NURI_TYPE"));
			
			model.addAttribute("formMode", "UPDATE");
			model.addAttribute("updateFail", true);
			
			//템플릿 파일 적용
			if(currentCategory != null && StringUtils.isNotBlank(currentCategory.getCatAdminListTemplate()) ){
				return archiveService.getArchiveTemplateName(currentCategory.getCatCustomType(), currentCategory.getCatAdminFormTemplate(), "admin");
			}
			
			return "asapro/admin/archive/" + currentCategory.getCatCustomType() + "/form";
			
		} else {
			
			Map<String, FileInfoUploadResult> fileInfoUploadResultMap = archiveService.updateArchive(archiveForm);
			
			//파일 업로드 에러 있으면 바로 수정화면으로 전환 - 에러난 파일 제외하고 글과 다른 파일들은 저장된 상태임.
			if(fileInfoUploadResultMap.get("thumbFileInfoUploadResult").hasErrors() || fileInfoUploadResultMap.get("fileInfoUploadResult").hasErrors() ){
				FlashMap flashMap = RequestContextUtils.getOutputFlashMap(request);
				
				if(fileInfoUploadResultMap.get("thumbFileInfoUploadResult").hasErrors() ){
					flashMap.put("thumbFileInfoUploadResult", fileInfoUploadResultMap.get("thumbFileInfoUploadResult"));
				}
				if(fileInfoUploadResultMap.get("fileInfoUploadResult").hasErrors() ){
					flashMap.put("fileInfoUploadResult", fileInfoUploadResultMap.get("fileInfoUploadResult"));
				}
				return "redirect:" + AsaproUtils.getAdminPath(currentSite, true) +  "/archive/update.do?arcId=" + archiveForm.getArcId() + "&arcCategory=" + archiveForm.getArcCategory();
			}
			
			redirectAttributes.addFlashAttribute("updated", true);
			return "redirect:" + AsaproUtils.getAdminPath(currentSite, true) + "/archive/update.do?arcId=" + archiveForm.getArcId() + "&arcCategory=" + archiveForm.getArcCategory();
		}
		
	}
	
	/**
	 * 아카이브를 삭제한다.
	 * <pre>
	 * - 첨부파일, 썸내일 포함
	 * </pre>
	 * @param model
	 * @param currentSite
	 * @param arcIds
	 * @return 삭제결과
	 * @throws FileNotFoundException
	 */
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/archive/delete.do", method=RequestMethod.POST)
	@ResponseBody
	public ServerMessage deleteArchivePost(Model model, @CurrentSite Site currentSite, @RequestParam(value="arcIds[]", required=true) Integer[] arcIds) throws FileNotFoundException{
		ServerMessage serverMessage = new ServerMessage();
		if( ArrayUtils.isEmpty(arcIds) ){
			serverMessage.setSuccess(false);
			serverMessage.setText("삭제할 항목이 없습니다.");
		} else {
			List<Archive> archiveList = new ArrayList<Archive>();
			Archive archive = null;
			for( Integer arcId : arcIds ){
				archive = new Archive();
				archive.setSitePrefix(currentSite.getSitePrefix());
				archive.setArcId(arcId);
				archiveList.add(archive);
			}
			int result = archiveService.deleteArchive(archiveList);
			if(result > 0){
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
