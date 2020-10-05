/**
 * 
 */
package egovframework.com.asapro.archive.customtype.advertising.web;

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

import egovframework.com.asapro.archive.customtype.advertising.service.Advertising;
import egovframework.com.asapro.archive.customtype.advertising.service.AdvertisingSearch;
import egovframework.com.asapro.archive.customtype.advertising.service.AdvertisingService;
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
import egovframework.com.asapro.site.service.Site;
import egovframework.com.asapro.support.Constant;
import egovframework.com.asapro.support.ServerMessage;
import egovframework.com.asapro.support.annotation.CurrentAdmin;
import egovframework.com.asapro.support.annotation.CurrentSite;
import egovframework.com.asapro.support.exception.AsaproException;
import egovframework.com.asapro.support.pagination.Paging;
import egovframework.com.asapro.support.util.AsaproUtils;
import egovframework.com.asapro.viewing.service.ViewingReservation;

/**
 * 광고자료 관리자 컨트롤러
 * 커스텀 아카이브
 * @author yckim
 * @since 2019. 9. 30.
 *
 */
@Controller
public class AdvertisingAdminController {
	
	@Autowired
	private ArchiveService archiveService;
	
	@Autowired
	private AdvertisingService advertisingService;
	
	@Autowired
	private AdvertisingValidator advertisingValidator;
	
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
	//==========================================  광고자료    ================================================================
	//=============================================================================================================================
	
	
	/**
	 * 광고자료 목록을 조회한다.
	 * @param model
	 * @param currentSite
	 * @param advertisingSearch
	 * @return 광고자료 목록
	 */
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/archive/advertising/list.do", method=RequestMethod.GET)
	public String advertisingListGet(Model model, @CurrentSite Site currentSite, @ModelAttribute("advertisingSearch") AdvertisingSearch advertisingSearch){
		//카테고리 조회
		ArchiveCategory currentCategory = null;
		if( StringUtils.isNotBlank(advertisingSearch.getArcCategory()) ){
			currentCategory = new ArchiveCategory();
			currentCategory.setSitePrefix(currentSite.getSitePrefix());
			currentCategory.setCatId(advertisingSearch.getArcCategory());
			currentCategory = archiveService.getArchiveCategory(currentCategory);
			
			//메타코드 로드
			this.loadCode(model, currentCategory);
			
			//기본 정렬 세팅 - 파라메터에서 넘어온 정렬값이 있으면 적용되지 않는다.
			advertisingSearch.setDefaultSort(currentCategory.getCatSortOrder(), currentCategory.getCatSortDirection());

		}
		model.addAttribute("archiveCategory", currentCategory);
		
		//광고자료 목록 조회
		advertisingSearch.setSitePrefix(currentSite.getSitePrefix());
		advertisingSearch.fixBrokenSvByDefaultCharsets();
		advertisingSearch.setPaging(true);
		
		List<Advertising> list = advertisingService.getAdvertisingList(advertisingSearch);
		int total = advertisingService.getAdvertisingListTotal(advertisingSearch);
		Paging paging = new Paging(list, total, advertisingSearch);
		model.addAttribute("paging", paging);
		
		//코드카테고리
		CodeCategorySearch codeCategorySearch = new CodeCategorySearch();
		codeCategorySearch.setPaging(false);
		model.addAttribute("codeCatList", codeService.getCodeCategoryList(codeCategorySearch));
		

		//메타코드2이 선택 됐으면 해당 코드목록을 메타코드목록 3로 가져간다.
		if(StringUtils.isNotBlank(advertisingSearch.getMetaCode2()) ){
			model.addAttribute("arcMetaCode3List", codeService.getCodeList(advertisingSearch.getMetaCode2()) );
		}
		
		//메타코드2에 해당하는 모든 상세코드목록
		if(currentCategory != null && StringUtils.isNotBlank(currentCategory.getCatMetaCode2()) ) {
			List<Code> arcMetaCode3ListAll = new ArrayList<Code>();
			@SuppressWarnings("unchecked")
			List<Code> tempMeta2List = (List<Code>)model.asMap().get("arcMetaCode2List");
			for (Code code : tempMeta2List) {
				List<Code> codeList = codeService.getCodeList(code.getCodeId());
				arcMetaCode3ListAll.addAll(codeList);
			}
			model.addAttribute("arcMetaCode3ListAll", arcMetaCode3ListAll);
		}

		
		//템플릿 파일 적용
		if(currentCategory != null && StringUtils.isNotBlank(currentCategory.getCatAdminListTemplate()) ){
			return archiveService.getArchiveTemplateName(currentCategory.getCatCustomType(), currentCategory.getCatAdminListTemplate(), "admin");
		}
		return "asapro/admin/archive/advertising/list";
	}
	
	/**
	 * 광고자료 등록 폼 뷰를 반환한다.
	 * @param model
	 * @param currentSite
	 * @param advertisingForm
	 * @return 추가폼뷰
	 */
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/archive/advertising/insert.do", method=RequestMethod.GET)
	public String advertisingInsertGet(Model model, @CurrentSite Site currentSite, @ModelAttribute("advertisingForm") Advertising advertisingForm) {
		
		//카테고리 조회
		ArchiveCategory currentCategory = null;
		if( StringUtils.isNotBlank(advertisingForm.getArcCategory()) ){
			currentCategory = new ArchiveCategory();
			currentCategory.setSitePrefix(currentSite.getSitePrefix());
			currentCategory.setCatId(advertisingForm.getArcCategory());
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
		return "asapro/admin/archive/advertising/form";
	}
	
	/**
	 * 광고자료를 등록한다.
	 * @param model
	 * @param redirectAttributes
	 * @param currentSite
	 * @param advertisingForm
	 * @param bindingResult
	 * @return 등록결과
	 * @throws AsaproException
	 * @throws IOException
	 */
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/archive/advertising/insert.do", method=RequestMethod.POST)
	public String advertisingInsertPost(Model model, RedirectAttributes redirectAttributes, @CurrentAdmin AdminMember currentAdmin, @CurrentSite Site currentSite, @ModelAttribute("advertisingForm") Advertising advertisingForm, BindingResult bindingResult) throws AsaproException, IOException {

		advertisingForm.setSitePrefix(currentSite.getSitePrefix());
		advertisingForm.setArcRegdate(new Date());
		advertisingForm.setMemId(currentAdmin.getAdminId());
		advertisingForm.setArcLastUpdate(new Date());
		
		//카테고리 조회
		ArchiveCategory currentCategory = null;
		if( StringUtils.isNotBlank(advertisingForm.getArcCategory()) ){
			currentCategory = new ArchiveCategory();
			currentCategory.setSitePrefix(currentSite.getSitePrefix());
			currentCategory.setCatId(advertisingForm.getArcCategory());
			currentCategory = archiveService.getArchiveCategory(currentCategory);
			
			//메타코드 로드
			this.loadCode(model, currentCategory);
			
		}
		model.addAttribute("archiveCategory", currentCategory);
		advertisingForm.setArchiveCategory(currentCategory);
		
		if(currentCategory != null){
			//아카이브 타입등록
			advertisingForm.setArcCustomType(currentCategory.getCatCustomType());
		}
		advertisingValidator.validate(advertisingForm, bindingResult, "INSERT");
		
		if( bindingResult.hasErrors() ){
			
			//공공누리코드목록
			model.addAttribute("nuriCodeList", codeService.getCodeList("NURI_TYPE"));
			
			//메타코드2이 선택 됐으면 해당 코드목록을 메타코드목록 3로 가져간다.
			if(StringUtils.isNotBlank(advertisingForm.getArcMetaCode2()) ){
				model.addAttribute("arcMetaCode3List", codeService.getCodeList(advertisingForm.getArcMetaCode2()) );
			}
			
			model.addAttribute("formMode", "INSERT");
			model.addAttribute("insertFail", true);
			
			//템플릿 파일 적용
			if(currentCategory != null && StringUtils.isNotBlank(currentCategory.getCatAdminFormTemplate()) ){
				return archiveService.getArchiveTemplateName(currentCategory.getCatCustomType(), currentCategory.getCatAdminFormTemplate(), "admin");
			}
			return "asapro/admin/archive/advertising/form";
		} else {
			
			Map<String, FileInfoUploadResult> fileInfoUploadResultMap = advertisingService.insertAdvertising(advertisingForm);
			
			//파일 업로드 에러 있으면 바로 수정화면으로 전환 - 에러난 파일 제외하고 글과 다른 파일들은 저장된 상태임.
			if(fileInfoUploadResultMap.get("thumbFileInfoUploadResult").hasErrors() || fileInfoUploadResultMap.get("fileInfoUploadResult").hasErrors() || fileInfoUploadResultMap.get("videoFileInfoUploadResult").hasErrors() || fileInfoUploadResultMap.get("audiofileInfoUploadResult").hasErrors() ){
				FlashMap flashMap = RequestContextUtils.getOutputFlashMap(request);
				
				if(fileInfoUploadResultMap.get("thumbFileInfoUploadResult").hasErrors() ){
					flashMap.put("thumbFileInfoUploadResult", fileInfoUploadResultMap.get("thumbFileInfoUploadResult"));
				}
				if(fileInfoUploadResultMap.get("fileInfoUploadResult").hasErrors() ){
					flashMap.put("fileInfoUploadResult", fileInfoUploadResultMap.get("fileInfoUploadResult"));
				}
				if(fileInfoUploadResultMap.get("videoFileInfoUploadResult").hasErrors() ){
					flashMap.put("videoFileInfoUploadResult", fileInfoUploadResultMap.get("videoFileInfoUploadResult"));
				}
				if(fileInfoUploadResultMap.get("audiofileInfoUploadResult").hasErrors() ){
					flashMap.put("audiofileInfoUploadResult", fileInfoUploadResultMap.get("audiofileInfoUploadResult"));
				}
				return "redirect:" + AsaproUtils.getAdminPath(currentSite, true) +  "/archive/advertising/update.do?arcId=" + advertisingForm.getArcId() + "&arcCategory=" + advertisingForm.getArcCategory();
			}
			
			redirectAttributes.addFlashAttribute("inserted", true);
			return "redirect:" + AsaproUtils.getAdminPath(currentSite, true) + "/archive/advertising/list.do?arcCategory=" + advertisingForm.getArcCategory();
		}
		
	}
	
	/**
	 * 광고자료 수정 폼 뷰를 반환한다.
	 * @param model
	 * @param currentSite
	 * @param advertisingForm
	 * @return 수정폼뷰 
	 */
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/archive/advertising/update.do", method=RequestMethod.GET)
	public String advertisingUpdateGet(Model model, @CurrentSite Site currentSite, @ModelAttribute("advertisingForm") Advertising advertisingForm) {
		//광고자료 조회
		advertisingForm.setSitePrefix(currentSite.getSitePrefix());
		Advertising advertisingModel = advertisingService.getAdvertising(advertisingForm);
		advertisingModel.setSitePrefix(currentSite.getSitePrefix());
		model.addAttribute("advertisingForm", advertisingModel);

		//카테고리 조회
		ArchiveCategory currentCategory = null;
		if( StringUtils.isNotBlank(advertisingModel.getArcCategory()) ){
			currentCategory = new ArchiveCategory();
			currentCategory.setSitePrefix(currentSite.getSitePrefix());
			currentCategory.setCatId(advertisingModel.getArcCategory());
			currentCategory = archiveService.getArchiveCategory(currentCategory);
			
			//메타코드 로드
			this.loadCode(model, currentCategory);
			
		}
		model.addAttribute("archiveCategory", currentCategory);
		
		
		//공공누리코드목록
		model.addAttribute("nuriCodeList", codeService.getCodeList("NURI_TYPE"));
		
		//메타코드2이 선택 됐으면 해당 코드목록을 메타코드목록 3로 가져간다.
		if(StringUtils.isNotBlank(advertisingModel.getArcMetaCode2()) ){
			model.addAttribute("arcMetaCode3List", codeService.getCodeList(advertisingModel.getArcMetaCode2()) );
		}
		
		model.addAttribute("formMode", "UPDATE");
		
		//파일 업로드 에러에 대한 정보가 flashMap에 들어 있는지 확인
		Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);
		if( flashMap != null ){
			if(flashMap.get("fileInfoUploadResult") != null ){
				FileInfoUploadResult fileInfoUploadResult = (FileInfoUploadResult) flashMap.get("fileInfoUploadResult");
				model.addAttribute("fileInfoUploadResult", fileInfoUploadResult);
			}
			if(flashMap.get("thumbFileInfoUploadResult") != null ){
				FileInfoUploadResult thumbFileInfoUploadResult = (FileInfoUploadResult) flashMap.get("thumbFileInfoUploadResult");
				model.addAttribute("thumbFileInfoUploadResult", thumbFileInfoUploadResult);
			}
			if(flashMap.get("videoFileInfoUploadResult") != null ){
				FileInfoUploadResult videoFileInfoUploadResult = (FileInfoUploadResult) flashMap.get("videoFileInfoUploadResult");
				model.addAttribute("videoFileInfoUploadResult", videoFileInfoUploadResult);
			}
			if(flashMap.get("audiofileInfoUploadResult") != null ){
				FileInfoUploadResult audiofileInfoUploadResult = (FileInfoUploadResult) flashMap.get("audiofileInfoUploadResult");
				model.addAttribute("audiofileInfoUploadResult", audiofileInfoUploadResult);
			}
		}
		
		//템플릿 파일 적용
		if(currentCategory != null && StringUtils.isNotBlank(currentCategory.getCatAdminFormTemplate()) ){
			return archiveService.getArchiveTemplateName(currentCategory.getCatCustomType(), currentCategory.getCatAdminFormTemplate(), "admin");
		}
		return "asapro/admin/archive/advertising/form";
	}
	
	/**
	 * 광고자료를 수정한다.
	 * @param model
	 * @param redirectAttributes
	 * @param currentSite
	 * @param advertisingForm
	 * @param bindingResult
	 * @return 수정결과
	 * @throws AsaproException
	 * @throws IOException
	 */
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/archive/advertising/update.do", method=RequestMethod.POST)
	public String advertisingUpdatePost(Model model, RedirectAttributes redirectAttributes, @CurrentAdmin AdminMember currentAdmin, @CurrentSite Site currentSite, @ModelAttribute("advertisingForm") Advertising advertisingForm, BindingResult bindingResult) throws AsaproException, IOException {

		advertisingForm.setSitePrefix(currentSite.getSitePrefix());
		advertisingForm.setArcLastUpdate(new Date());
		
		//카테고리 조회
		ArchiveCategory currentCategory = null;
		if( StringUtils.isNotBlank(advertisingForm.getArcCategory()) ){
			currentCategory = new ArchiveCategory();
			currentCategory.setSitePrefix(currentSite.getSitePrefix());
			currentCategory.setCatId(advertisingForm.getArcCategory());
			currentCategory = archiveService.getArchiveCategory(currentCategory);
			
			//메타코드 로드
			this.loadCode(model, currentCategory);
			
		}
		model.addAttribute("archiveCategory", currentCategory);
		advertisingForm.setArchiveCategory(currentCategory);
		if(currentCategory != null){
			//아카이브 타입등록
			advertisingForm.setArcCustomType(currentCategory.getCatCustomType());
		}

		advertisingValidator.validate(advertisingForm, bindingResult, "UPDATE");
		
		if( bindingResult.hasErrors() ){
			
			
			//공공누리코드목록
			model.addAttribute("nuriCodeList", codeService.getCodeList("NURI_TYPE"));
			
			//메타코드2이 선택 됐으면 해당 코드목록을 메타코드목록 3로 가져간다.
			if(StringUtils.isNotBlank(advertisingForm.getArcMetaCode2()) ){
				model.addAttribute("arcMetaCode3List", codeService.getCodeList(advertisingForm.getArcMetaCode2()) );
			}
			
			model.addAttribute("formMode", "UPDATE");
			model.addAttribute("updateFail", true);
			
			//템플릿 파일 적용
			if(currentCategory != null && StringUtils.isNotBlank(currentCategory.getCatAdminFormTemplate()) ){
				return archiveService.getArchiveTemplateName(currentCategory.getCatCustomType(), currentCategory.getCatAdminFormTemplate(), "admin");
			}
			return "asapro/admin/archive/advertising/form";
		} else {
			
			Map<String, FileInfoUploadResult> fileInfoUploadResultMap = advertisingService.updateAdvertising(advertisingForm);
			
			//파일 업로드 에러 있으면 바로 수정화면으로 전환 - 에러난 파일 제외하고 글과 다른 파일들은 저장된 상태임.
			if(fileInfoUploadResultMap.get("thumbFileInfoUploadResult").hasErrors() || fileInfoUploadResultMap.get("fileInfoUploadResult").hasErrors() || fileInfoUploadResultMap.get("videoFileInfoUploadResult").hasErrors() || fileInfoUploadResultMap.get("audiofileInfoUploadResult").hasErrors() ){
				FlashMap flashMap = RequestContextUtils.getOutputFlashMap(request);
				
				if(fileInfoUploadResultMap.get("thumbFileInfoUploadResult").hasErrors() ){
					flashMap.put("thumbFileInfoUploadResult", fileInfoUploadResultMap.get("thumbFileInfoUploadResult"));
				}
				if(fileInfoUploadResultMap.get("fileInfoUploadResult").hasErrors() ){
					flashMap.put("fileInfoUploadResult", fileInfoUploadResultMap.get("fileInfoUploadResult"));
				}
				if(fileInfoUploadResultMap.get("videoFileInfoUploadResult").hasErrors() ){
					flashMap.put("videoFileInfoUploadResult", fileInfoUploadResultMap.get("videoFileInfoUploadResult"));
				}
				if(fileInfoUploadResultMap.get("audiofileInfoUploadResult").hasErrors() ){
					flashMap.put("audiofileInfoUploadResult", fileInfoUploadResultMap.get("audiofileInfoUploadResult"));
				}
				return "redirect:" + AsaproUtils.getAdminPath(currentSite, true) +  "/archive/advertising/update.do?arcId=" + advertisingForm.getArcId() + "&arcCategory=" + advertisingForm.getArcCategory();
			}
			
			redirectAttributes.addFlashAttribute("updated", true);
			return "redirect:" + AsaproUtils.getAdminPath(currentSite, true) + "/archive/advertising/update.do?arcId=" + advertisingForm.getArcId() + "&arcCategory=" + advertisingForm.getArcCategory();
		}
		
	}
	
	/**
	 * 광고자료를 삭제한다.
	 * <pre>
	 * - 첨부파일, 썸내일 포함
	 * </pre>
	 * @param model
	 * @param currentSite
	 * @param arcIds
	 * @return 삭제결과
	 * @throws FileNotFoundException
	 */
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/archive/advertising/delete.do", method=RequestMethod.POST)
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
