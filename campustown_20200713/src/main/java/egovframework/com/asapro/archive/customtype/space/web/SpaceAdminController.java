/**
 * 
 */
package egovframework.com.asapro.archive.customtype.space.web;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.FlashMap;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

import egovframework.com.asapro.archive.customtype.space.service.Space;
import egovframework.com.asapro.archive.customtype.space.service.SpaceSearch;
import egovframework.com.asapro.archive.customtype.space.service.SpaceService;
import egovframework.com.asapro.archive.service.Archive;
import egovframework.com.asapro.archive.service.ArchiveCategory;
import egovframework.com.asapro.archive.service.ArchiveCategorySearch;
import egovframework.com.asapro.archive.service.ArchiveSearch;
import egovframework.com.asapro.archive.service.ArchiveService;
import egovframework.com.asapro.archive.web.ArchiveValidator;
import egovframework.com.asapro.code.service.Code;
import egovframework.com.asapro.code.service.CodeCategory;
import egovframework.com.asapro.code.service.CodeCategorySearch;
import egovframework.com.asapro.code.service.CodeService;
import egovframework.com.asapro.fileinfo.service.FileInfo;
import egovframework.com.asapro.fileinfo.service.FileInfoUploadResult;
import egovframework.com.asapro.member.admin.service.AdminMember;
import egovframework.com.asapro.organization.service.Department;
import egovframework.com.asapro.organization.service.DepartmentSearch;
import egovframework.com.asapro.organization.service.OrganizationService;
import egovframework.com.asapro.site.service.Site;
import egovframework.com.asapro.support.Constant;
import egovframework.com.asapro.support.ServerMessage;
import egovframework.com.asapro.support.annotation.CurrentAdmin;
import egovframework.com.asapro.support.annotation.CurrentSite;
import egovframework.com.asapro.support.exception.AsaproException;
import egovframework.com.asapro.support.pagination.Paging;
import egovframework.com.asapro.support.util.AsaproUtils;

/**
 * 공간정보 관리자 컨트롤러
 * 커스텀 아카이브
 * @author yckim
 * @since 2019. 2. 26.
 *
 */
@Controller
public class SpaceAdminController {
	
	@Autowired
	private ArchiveService archiveService;
	
	@Autowired
	private SpaceService spaceService;
	
	@Autowired
	private SpaceValidator spaceValidator;
	
	@Autowired
	private OrganizationService organizationService;
	
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
			arcMetaCode1List = codeService.getCodeList(archiveCategory.getCatMetaCode1(), "CODE_NAME", "ASC");
			model.addAttribute("arcMetaCode1Info", arcMetaCode1Info );
			model.addAttribute("arcMetaCode1List", arcMetaCode1List );
		}
		if( StringUtils.isNotBlank(archiveCategory.getCatMetaCode2()) ){
			arcMetaCode2Info = codeService.getCodeCategory(archiveCategory.getCatMetaCode2());
			arcMetaCode2List = codeService.getCodeList(archiveCategory.getCatMetaCode2(), "CODE_NAME", "ASC");
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
	
	
	/**
	 * 부서목록 로드
	 * @param model
	 * @param orgId
	 */
	private void loadDepartment(Model model, String orgId){
		//부서목록
		DepartmentSearch departmentSearch = new DepartmentSearch();
		departmentSearch.setPaging(false);
		departmentSearch.setDepUse(true);
		departmentSearch.setOrgId(orgId);
		List<Department> departmentList = organizationService.getDepartmentList(departmentSearch);
		//System.out.println("departmentList.size(): " + departmentList.size());
		//System.out.println("departmentList.size(): " + departmentList.get(0).getDepName());
		model.addAttribute("departmentList", departmentList);
	}
	
	//=============================================================================================================================
	//==========================================  공간정보    ================================================================
	//=============================================================================================================================
	
	
	/**
	 * 공간정보 목록을 조회한다.
	 * @param model
	 * @param currentSite
	 * @param spaceSearch
	 * @return 공간정보 목록
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/archive/space/list.do", method=RequestMethod.GET)
	public String spaceListGet(Model model, @CurrentSite Site currentSite, @ModelAttribute("spaceSearch") SpaceSearch spaceSearch){
		//카테고리 조회
		ArchiveCategory currentCategory = null;
		if( StringUtils.isNotBlank(spaceSearch.getArcCategory()) ){
			currentCategory = new ArchiveCategory();
			currentCategory.setSitePrefix(currentSite.getSitePrefix());
			currentCategory.setCatId(spaceSearch.getArcCategory());
			currentCategory = archiveService.getArchiveCategory(currentCategory);
			
			//메타코드 로드
			this.loadCode(model, currentCategory);
			
			//기본 정렬 세팅 - 파라메터에서 넘어온 정렬값이 있으면 적용되지 않는다.
			spaceSearch.setDefaultSort(currentCategory.getCatSortOrder(), currentCategory.getCatSortDirection());

		}
		model.addAttribute("archiveCategory", currentCategory);
		this.loadDepartment(model, "university"); // 대학 부서목록 로드 (departmentList)
		
		
		//공간정보 목록 조회
		spaceSearch.setSitePrefix(currentSite.getSitePrefix());
		spaceSearch.fixBrokenSvByDefaultCharsets();
		spaceSearch.setPaging(true);
		
		List<Space> list = spaceService.getSpaceList(spaceSearch);
		int total = spaceService.getSpaceListTotal(spaceSearch);
		Paging paging = new Paging(list, total, spaceSearch);
		model.addAttribute("paging", paging);
		
		List<Code> arcMetaCode2ListAll = new ArrayList<Code>();
		if(currentCategory != null && StringUtils.isNotBlank(currentCategory.getCatMetaCode1()) ) {
			List<Code> tempMeta1List = (List<Code>)model.asMap().get("arcMetaCode1List");
			for (Code code : tempMeta1List) {
				List<Code> codeList = codeService.getCodeList(code.getCodeId(), "CODE_NAME", "ASC");
				arcMetaCode2ListAll.addAll(codeList);
			}
			//codeService.getCodeList(spaceModel.getArcMetaCode1())
		}
		model.addAttribute("arcMetaCode2ListAll", arcMetaCode2ListAll);
		
//		if(StringUtils.isNotBlank(spaceModel.getArcMetaCode1()) ){
//			model.addAttribute("arcMetaCode2List", codeService.getCodeList(spaceModel.getArcMetaCode1()) );
//		}
		
		//템플릿 파일 적용
		if(currentCategory != null && StringUtils.isNotBlank(currentCategory.getCatAdminListTemplate()) ){
			return archiveService.getArchiveTemplateName(currentCategory.getCatCustomType(), currentCategory.getCatAdminListTemplate(), "admin");
		}
		return "asapro/admin/archive/space/list";
	}
	
	/**
	 * 공간정보 등록 폼 뷰를 반환한다.
	 * @param model
	 * @param currentSite
	 * @param spaceForm
	 * @return 추가폼뷰
	 */
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/archive/space/insert.do", method=RequestMethod.GET)
	public String spaceInsertGet(Model model, @CurrentSite Site currentSite, @ModelAttribute("spaceForm") Space spaceForm) {
		
		//카테고리 조회
		ArchiveCategory currentCategory = null;
		if( StringUtils.isNotBlank(spaceForm.getArcCategory()) ){
			currentCategory = new ArchiveCategory();
			currentCategory.setSitePrefix(currentSite.getSitePrefix());
			currentCategory.setCatId(spaceForm.getArcCategory());
			currentCategory = archiveService.getArchiveCategory(currentCategory);
			
			//메타코드 로드
			this.loadCode(model, currentCategory);
			
		}
		model.addAttribute("archiveCategory", currentCategory);
		
		this.loadDepartment(model, "university"); // 대학 부서목록 로드 (departmentList)
		
		//공공누리코드목록
		//model.addAttribute("nuriCodeList", codeService.getCodeList("NURI_TYPE"));
		
		model.addAttribute("formMode", "INSERT");
		
		//템플릿 파일 적용
		if(currentCategory != null && StringUtils.isNotBlank(currentCategory.getCatAdminFormTemplate()) ){
			return archiveService.getArchiveTemplateName(currentCategory.getCatCustomType(), currentCategory.getCatAdminFormTemplate(), "admin");
		}
		return "asapro/admin/archive/space/form";
	}
	
	/**
	 * 공간정보를 등록한다.
	 * @param model
	 * @param redirectAttributes
	 * @param currentSite
	 * @param spaceForm
	 * @param bindingResult
	 * @return 등록결과
	 * @throws AsaproException
	 * @throws IOException
	 */
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/archive/space/insert.do", method=RequestMethod.POST)
	public String spaceInsertPost(Model model, RedirectAttributes redirectAttributes, @CurrentAdmin AdminMember currentAdmin, @CurrentSite Site currentSite, @ModelAttribute("spaceForm") Space spaceForm, BindingResult bindingResult) throws AsaproException, IOException {

		spaceForm.setSitePrefix(currentSite.getSitePrefix());
		spaceForm.setArcRegdate(new Date());
		spaceForm.setMemId(currentAdmin.getAdminId());
		spaceForm.setArcLastUpdate(new Date());
		
		//카테고리 조회
		ArchiveCategory currentCategory = null;
		if( StringUtils.isNotBlank(spaceForm.getArcCategory()) ){
			currentCategory = new ArchiveCategory();
			currentCategory.setSitePrefix(currentSite.getSitePrefix());
			currentCategory.setCatId(spaceForm.getArcCategory());
			currentCategory = archiveService.getArchiveCategory(currentCategory);
			
			//메타코드 로드
			this.loadCode(model, currentCategory);
			
		}
		model.addAttribute("archiveCategory", currentCategory);
		spaceForm.setArchiveCategory(currentCategory);
		if(currentCategory != null){
			//아카이브 타입등록
			spaceForm.setArcCustomType(currentCategory.getCatCustomType());
		}
		
		spaceValidator.validate(spaceForm, bindingResult, "INSERT");
		
		if( bindingResult.hasErrors() ){
			
			//공공누리코드목록
			//model.addAttribute("nuriCodeList", codeService.getCodeList("NURI_TYPE"));
			
			//메타코드1이 선택 됐으면 해당 코드목록을 메타코드목록 2로 가져간다.
			if(StringUtils.isNotBlank(spaceForm.getArcMetaCode1()) ){
				model.addAttribute("arcMetaCode2List", codeService.getCodeList(spaceForm.getArcMetaCode1()) );
			}
			
			this.loadDepartment(model, "university"); // 대학 부서목록 로드 (departmentList)
			
			model.addAttribute("formMode", "INSERT");
			model.addAttribute("insertFail", true);
			
			//템플릿 파일 적용
			if(currentCategory != null && StringUtils.isNotBlank(currentCategory.getCatAdminFormTemplate()) ){
				return archiveService.getArchiveTemplateName(currentCategory.getCatCustomType(), currentCategory.getCatAdminFormTemplate(), "admin");
			}
			return "asapro/admin/archive/space/form";
		} else {
			
			Map<String, FileInfoUploadResult> fileInfoUploadResultMap = spaceService.insertSpace(spaceForm);
			
			//파일 업로드 에러 있으면 바로 수정화면으로 전환 - 에러난 파일 제외하고 글과 다른 파일들은 저장된 상태임.
			if(fileInfoUploadResultMap.get("thumbFileInfoUploadResult").hasErrors() || fileInfoUploadResultMap.get("fileInfoUploadResult").hasErrors() ){
				FlashMap flashMap = RequestContextUtils.getOutputFlashMap(request);
				
				if(fileInfoUploadResultMap.get("thumbFileInfoUploadResult").hasErrors() ){
					flashMap.put("thumbFileInfoUploadResult", fileInfoUploadResultMap.get("thumbFileInfoUploadResult"));
				}
				if(fileInfoUploadResultMap.get("fileInfoUploadResult").hasErrors() ){
					flashMap.put("fileInfoUploadResult", fileInfoUploadResultMap.get("fileInfoUploadResult"));
				}
				return "redirect:" + AsaproUtils.getAdminPath(currentSite, true) +  "/archive/space/update.do?arcId=" + spaceForm.getArcId() + "&arcCategory=" + spaceForm.getArcCategory();
			}
			
			redirectAttributes.addFlashAttribute("inserted", true);
			return "redirect:" + AsaproUtils.getAdminPath(currentSite, true) + "/archive/space/list.do?arcCategory=" + spaceForm.getArcCategory();
		}
		
	}
	
	/**
	 * 공간정보 수정 폼 뷰를 반환한다.
	 * @param model
	 * @param currentSite
	 * @param spaceForm
	 * @return 수정폼뷰 
	 */
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/archive/space/update.do", method=RequestMethod.GET)
	public String spaceUpdateGet(Model model, @CurrentSite Site currentSite, @ModelAttribute("spaceForm") Space spaceForm) {
		//공간정보 조회
		spaceForm.setSitePrefix(currentSite.getSitePrefix());
		Space spaceModel = spaceService.getSpace(spaceForm);
		spaceModel.setSitePrefix(currentSite.getSitePrefix());
		model.addAttribute("spaceForm", spaceModel);

		//카테고리 조회
		ArchiveCategory currentCategory = null;
		if( StringUtils.isNotBlank(spaceModel.getArcCategory()) ){
			currentCategory = new ArchiveCategory();
			currentCategory.setSitePrefix(currentSite.getSitePrefix());
			currentCategory.setCatId(spaceModel.getArcCategory());
			currentCategory = archiveService.getArchiveCategory(currentCategory);
			
			//메타코드 로드
			this.loadCode(model, currentCategory);
			
		}
		this.loadDepartment(model, "university"); // 대학 부서목록 로드 (departmentList)
		
		model.addAttribute("archiveCategory", currentCategory);
		
		
		//공공누리코드목록
		//model.addAttribute("nuriCodeList", codeService.getCodeList("NURI_TYPE"));
		
		//메타코드1이 선택 됐으면 해당 코드목록을 메타코드목록 2로 가져간다.
		if(StringUtils.isNotBlank(spaceModel.getArcMetaCode1()) ){
			model.addAttribute("arcMetaCode2List", codeService.getCodeList(spaceModel.getArcMetaCode1()) );
		}
		
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
		return "asapro/admin/archive/space/form";
	}
	
	/**
	 * 공간정보를 수정한다.
	 * @param model
	 * @param redirectAttributes
	 * @param currentSite
	 * @param spaceForm
	 * @param bindingResult
	 * @return 수정결과
	 * @throws AsaproException
	 * @throws IOException
	 */
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/archive/space/update.do", method=RequestMethod.POST)
	public String spaceUpdatePost(Model model, RedirectAttributes redirectAttributes, @CurrentAdmin AdminMember currentAdmin, @CurrentSite Site currentSite, @ModelAttribute("spaceForm") Space spaceForm, BindingResult bindingResult) throws AsaproException, IOException {

		spaceForm.setSitePrefix(currentSite.getSitePrefix());
		spaceForm.setArcLastUpdate(new Date());
		
		//카테고리 조회
		ArchiveCategory currentCategory = null;
		if( StringUtils.isNotBlank(spaceForm.getArcCategory()) ){
			currentCategory = new ArchiveCategory();
			currentCategory.setSitePrefix(currentSite.getSitePrefix());
			currentCategory.setCatId(spaceForm.getArcCategory());
			currentCategory = archiveService.getArchiveCategory(currentCategory);
			
			//메타코드 로드
			this.loadCode(model, currentCategory);
			
		}
		model.addAttribute("archiveCategory", currentCategory);
		spaceForm.setArchiveCategory(currentCategory);
		if(currentCategory != null){
			//아카이브 타입등록
			spaceForm.setArcCustomType(currentCategory.getCatCustomType());
		}

		spaceValidator.validate(spaceForm, bindingResult, "UPDATE");
		
		if( bindingResult.hasErrors() ){
			
			
			//공공누리코드목록
			//model.addAttribute("nuriCodeList", codeService.getCodeList("NURI_TYPE"));
			
			//메타코드1이 선택 됐으면 해당 코드목록을 메타코드목록 2로 가져간다.
			if(StringUtils.isNotBlank(spaceForm.getArcMetaCode1()) ){
				model.addAttribute("arcMetaCode2List", codeService.getCodeList(spaceForm.getArcMetaCode1()) );
			}
			
			model.addAttribute("formMode", "UPDATE");
			model.addAttribute("updateFail", true);
			
			//템플릿 파일 적용
			if(currentCategory != null && StringUtils.isNotBlank(currentCategory.getCatAdminFormTemplate()) ){
				return archiveService.getArchiveTemplateName(currentCategory.getCatCustomType(), currentCategory.getCatAdminFormTemplate(), "admin");
			}
			return "asapro/admin/archive/space/form";
		} else {
			
			Map<String, FileInfoUploadResult> fileInfoUploadResultMap = spaceService.updateSpace(spaceForm);
			
			//파일 업로드 에러 있으면 바로 수정화면으로 전환 - 에러난 파일 제외하고 글과 다른 파일들은 저장된 상태임.
			if(fileInfoUploadResultMap.get("thumbFileInfoUploadResult").hasErrors() || fileInfoUploadResultMap.get("fileInfoUploadResult").hasErrors() ){
				FlashMap flashMap = RequestContextUtils.getOutputFlashMap(request);
				
				if(fileInfoUploadResultMap.get("thumbFileInfoUploadResult").hasErrors() ){
					flashMap.put("thumbFileInfoUploadResult", fileInfoUploadResultMap.get("thumbFileInfoUploadResult"));
				}
				if(fileInfoUploadResultMap.get("fileInfoUploadResult").hasErrors() ){
					flashMap.put("fileInfoUploadResult", fileInfoUploadResultMap.get("fileInfoUploadResult"));
				}
				return "redirect:" + AsaproUtils.getAdminPath(currentSite, true) +  "/archive/space/update.do?arcId=" + spaceForm.getArcId() + "&arcCategory=" + spaceForm.getArcCategory();
			}
			
			redirectAttributes.addFlashAttribute("updated", true);
			return "redirect:" + AsaproUtils.getAdminPath(currentSite, true) + "/archive/space/update.do?arcId=" + spaceForm.getArcId() + "&arcCategory=" + spaceForm.getArcCategory();
		}
		
	}
	
	/**
	 * 공간정보 삭제는 아카이브 삭제를 사용
	 * DB간 cascade 삭제된다
	 */
	
}
