/**
 * 
 */
package egovframework.com.asapro.site.web;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

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

import egovframework.com.asapro.fileinfo.service.FileInfo;
import egovframework.com.asapro.fileinfo.service.FileInfoService;
import egovframework.com.asapro.fileinfo.service.FileInfoUploadResult;
import egovframework.com.asapro.site.service.Site;
import egovframework.com.asapro.site.service.SiteSearch;
import egovframework.com.asapro.site.service.SiteService;
import egovframework.com.asapro.support.Constant;
import egovframework.com.asapro.support.ServerMessage;
import egovframework.com.asapro.support.annotation.CurrentSite;
import egovframework.com.asapro.support.exception.AsaproException;
import egovframework.com.asapro.support.pagination.Paging;
import egovframework.com.asapro.support.util.AsaproUtils;
import egovframework.com.asapro.support.util.EtcUtil;
import egovframework.com.asapro.theme.service.ThemeService;

/**
 * 사이트 관리 관리자 컨트롤러
 * @author yckim
 * @since 2018.09.11
 *
 */
@Controller
public class SiteAdminController {
	
	@Autowired
	private SiteService siteService;
	
	@Autowired
	private SiteValidator siteValidator;
	
	@Autowired
	private ThemeService themeService;
	
	@Autowired
	private HttpServletRequest request;
	
	@Autowired
	private FileInfoService fileInfoService;

	/**
	 * 사이트 목록을 반환한다.
	 * @param model
	 * @param siteSearch
	 * @return 사이트 목록
	 */
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/site/list.do", method=RequestMethod.GET )
	public String siteListGet(Model model, @CurrentSite Site currentSite, @ModelAttribute("siteSearch") SiteSearch siteSearch){
		siteSearch.fixBrokenSvByDefaultCharsets();
		siteSearch.setPaging(false);
		//siteSearch.setSitePrefix(currentSite.getSitePrefix());
		List<Site> list = siteService.getSiteList(siteSearch);
		int total = siteService.getSiteListTotal(siteSearch);
		
		Paging paging = new Paging(list, total, siteSearch);
		model.addAttribute("paging", paging);
		
		return "asapro/admin/site/list";
	}

	/**
	 * 사이트 추가 폼 뷰를 반환한다.
	 * @param model
	 * @param siteForm
	 * @return 사이트 추가 폼 뷰
	 * @throws FileNotFoundException
	 */
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/site/insert.do", method=RequestMethod.GET)
	public String siteInsertGet(Model model, @CurrentSite Site currentSite, @ModelAttribute("siteForm") Site siteForm) throws FileNotFoundException{
		//Constant.SITE_ID_PATH({siteId}) 에서 Site 의 siteId 에 매핑되어 바인딩 돼므로 siteId를 초기화해서 폼을 반환한다.
		siteForm.setSiteId("");
		model.addAttribute("formMode", "INSERT");
		model.addAttribute("themeList", themeService.getThemeList());
		return "asapro/admin/site/form";
	}
	
	/**
	 * 사이트를 추가한다.
	 * @param model
	 * @param redirectAttributes
	 * @param currentSite
	 * @param siteForm
	 * @param bindingResult
	 * @return 추가 결과 뷰
	 * @throws IOException 
	 * @throws AsaproException 
	 */
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/site/insert.do", method=RequestMethod.POST)
	public String siteInsertPost(Model model, RedirectAttributes redirectAttributes, @CurrentSite Site currentSite, @ModelAttribute("siteForm") Site siteForm, BindingResult bindingResult) throws AsaproException, IOException{
		siteValidator.validate(siteForm, bindingResult, "INSERT");
		if( bindingResult.hasErrors() ){
			model.addAttribute("formMode", "INSERT");
			model.addAttribute("themeList", themeService.getThemeList());
			return "asapro/admin/site/form";
		} else {
			
			//첨부가능한 확장자
			String uploadWhiteList = "";
			
			FileInfoUploadResult thumbFileInfoUploadResult = new FileInfoUploadResult();
			//이미지 처리
			if( siteForm.getSiteLogoImage() != null && siteForm.getSiteLogoImage().getSize() > 0 ){

				uploadWhiteList = Constant.UPLOAD_IMAGE_WHITE_LIST; 
				String webRoot = AsaproUtils.getWebRoot(request);
				
				//첨부된 파일 
				FileInfo fileInfo = new FileInfo();
				fileInfo.setSitePrefix(currentSite.getSitePrefix());
				//모듈1차pk
				fileInfo.setFileModule("site");
				fileInfo.setFileModuleId(siteForm.getSitePrefix());
				//모듈2차pk
				fileInfo.setFileModuleSub("");
				fileInfo.setFileModuleSubId("");
				//멤버
				fileInfo.setMemberId("");
				//첨부유형
				fileInfo.setFileAttachmentType("thumbnail");
				//대체텍스트
				fileInfo.setFileAltText( siteForm.getSiteName() );
				
				//썸네일 파일 같이 만든다.
				fileInfoService.saveFile(siteForm.getSiteLogoImage(), webRoot, fileInfo, (Constant.MEGA * 1), true, 200, 60, false, uploadWhiteList);
				if( !fileInfo.isFileUploadSuccess() ){
					thumbFileInfoUploadResult.addErrorInfo(fileInfo);
				}
				
				siteForm.setSiteLogo(fileInfo);
				
			}

			//업로드 에러 있으면 등록화면으로 전환
			if(thumbFileInfoUploadResult.hasErrors() ){
				//FlashMap flashMap = RequestContextUtils.getOutputFlashMap(request);
				//flashMap.put("thumbFileInfoUploadResult", thumbFileInfoUploadResult);
				
				//redirectAttributes.addFlashAttribute("thumbFileInfoUploadResult", thumbFileInfoUploadResult);
				//return "redirect:" + AsaproUtils.getAdminPath(currentSite, true) +  "/banner/update.do?bnType=" + bannerForm.getBnType() + "&bnId=" + bannerForm.getBnId();
				
				model.addAttribute("thumbFileInfoUploadResult", thumbFileInfoUploadResult);
				model.addAttribute("formMode", "INSERT");
				model.addAttribute("themeList", themeService.getThemeList());
				return "asapro/admin/site/form";
			}
			
			//siteForm.setSitePrefix(currentSite.getSitePrefix());
			siteForm.setSiteRegDate(new Date());
			siteService.insertSite(siteForm);
			redirectAttributes.addFlashAttribute("inserted", true);
			return "redirect:" + AsaproUtils.getAdminPath(currentSite, true) + "/site/list.do";
		}
	}

	/**
	 * 사이트 수정 폼 뷰를 반환한다.
	 * @param model
	 * @param siteForm
	 * @return 사이트 수정 폼 뷰
	 * @throws FileNotFoundException
	 */
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/site/update.do", method=RequestMethod.GET)
	public String siteUpdateGet(Model model, @ModelAttribute("siteForm") Site siteForm) throws FileNotFoundException{
		model.addAttribute("formMode", "UPDATE");
		Site siteDB = siteService.getSite(siteForm.getSiteId());
		model.addAttribute("siteForm", siteDB);
		model.addAttribute("themeList", themeService.getThemeList());
		return "asapro/admin/site/form";
	}
	
	/**
	 * 사이트를 수정한다.
	 * @param model
	 * @param redirectAttributes
	 * @param currentSite
	 * @param siteForm
	 * @param bindingResult
	 * @return 수정 결과 뷰
	 * @throws IOException 
	 * @throws AsaproException 
	 */
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/site/update.do", method=RequestMethod.POST)
	public String siteUpdatePost(Model model, RedirectAttributes redirectAttributes, @CurrentSite Site currentSite, @ModelAttribute("siteForm") Site siteForm, BindingResult bindingResult) throws AsaproException, IOException{
		Site siteDB = siteService.getSite(siteForm.getSiteId());
		siteForm.setSiteLogo(siteDB.getSiteLogo());

		siteValidator.validate(siteForm, bindingResult, "UPDATE");
		if( bindingResult.hasErrors() ){
			model.addAttribute("formMode", "UPDATE");
			model.addAttribute("themeList", themeService.getThemeList());
			return "asapro/admin/site/form";
		} else {
			
			//첨부가능한 확장자
			String uploadWhiteList = "";
			
			FileInfoUploadResult thumbFileInfoUploadResult = new FileInfoUploadResult();
			
			//새 이미지 처리
			if( siteForm.getSiteLogoImage() != null && siteForm.getSiteLogoImage().getSize() > 0 ){
				
				//기존이미지 삭제
				if(siteForm.getSiteLogo() != null && siteForm.getSiteLogo().getFileId() > 0){
					FileInfo fileInfoImage = siteForm.getSiteLogo();
					fileInfoImage.setSitePrefix(currentSite.getSitePrefix());
					fileInfoService.deleteFileInfo(fileInfoImage);
				}

				uploadWhiteList = Constant.UPLOAD_IMAGE_WHITE_LIST; 
				String webRoot = AsaproUtils.getWebRoot(request);
				
				//첨부된 파일 
				FileInfo fileInfo = new FileInfo();
				fileInfo.setSitePrefix(currentSite.getSitePrefix());
				//모듈1차pk
				fileInfo.setFileModule("site");
				fileInfo.setFileModuleId(siteForm.getSitePrefix());
				//모듈2차pk
				fileInfo.setFileModuleSub("");
				fileInfo.setFileModuleSubId("");
				//멤버
				fileInfo.setMemberId("");
				//첨부유형
				fileInfo.setFileAttachmentType("thumbnail");
				//대체텍스트
				fileInfo.setFileAltText( siteForm.getSiteName() );
				
				//썸네일 파일 같이 만든다.
				fileInfoService.saveFile(siteForm.getSiteLogoImage(), webRoot, fileInfo, (Constant.MEGA * 1), true, 200, 60, false, uploadWhiteList);
				if( !fileInfo.isFileUploadSuccess() ){
					thumbFileInfoUploadResult.addErrorInfo(fileInfo);
				}
				
				siteForm.setSiteLogo(fileInfo);
				
			}

			//업로드 에러 있으면 등록화면으로 전환
			if(thumbFileInfoUploadResult.hasErrors() ){
				//FlashMap flashMap = RequestContextUtils.getOutputFlashMap(request);
				//flashMap.put("thumbFileInfoUploadResult", thumbFileInfoUploadResult);
				
				//redirectAttributes.addFlashAttribute("thumbFileInfoUploadResult", thumbFileInfoUploadResult);
				//return "redirect:" + AsaproUtils.getAdminPath(currentSite, true) +  "/banner/update.do?bnType=" + bannerForm.getBnType() + "&bnId=" + bannerForm.getBnId();
				
				model.addAttribute("thumbFileInfoUploadResult", thumbFileInfoUploadResult);
				model.addAttribute("formMode", "INSERT");
				model.addAttribute("themeList", themeService.getThemeList());
				return "asapro/admin/site/form";
			}
			
			siteService.updateSite(siteForm);
			redirectAttributes.addFlashAttribute("updated", true);
			return "redirect:" + AsaproUtils.getAdminPath(currentSite, true) + "/site/update.do?siteId=" + siteForm.getSiteId();
		}
	}
	
	/**
	 * 사이트를 삭제한다.
	 * @param siteIds
	 * @return 삭제 결과 json
	 */
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/site/delete.do", method=RequestMethod.POST)
	@ResponseBody
	public ServerMessage siteDeletePost(@RequestParam(value="siteIds[]", required=true) String[] siteIds){
		ServerMessage serverMessage = new ServerMessage();
		
		if(ArrayUtils.isEmpty(siteIds) ){
			serverMessage.setSuccess(false);
			serverMessage.setText("삭제할 항목이 없습니다.");
		} else {
			int result = siteService.deleteSite(siteIds);
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
