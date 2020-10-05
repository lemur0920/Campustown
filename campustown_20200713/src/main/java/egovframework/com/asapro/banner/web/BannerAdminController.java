/**
 * 
 */
package egovframework.com.asapro.banner.web;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;
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

import com.ibm.icu.text.SimpleDateFormat;

import egovframework.com.asapro.banner.service.Banner;
import egovframework.com.asapro.banner.service.BannerSearch;
import egovframework.com.asapro.banner.service.BannerService;
import egovframework.com.asapro.fileinfo.service.FileInfo;
import egovframework.com.asapro.fileinfo.service.FileInfoService;
import egovframework.com.asapro.fileinfo.service.FileInfoUploadResult;
import egovframework.com.asapro.site.service.Site;
import egovframework.com.asapro.support.Constant;
import egovframework.com.asapro.support.ServerMessage;
import egovframework.com.asapro.support.annotation.CurrentSite;
import egovframework.com.asapro.support.exception.AsaproException;
import egovframework.com.asapro.support.pagination.Paging;
import egovframework.com.asapro.support.util.AsaproUtils;
import egovframework.com.asapro.support.util.EtcUtil;

/**
 * 배너/메인비주얼/팝업존/레이어팝업 관리자 컨트롤러
 * @author yckim
 * @since 2018. 7. 26.
 *
 */
@Controller
public class BannerAdminController {
	
	@Autowired
	private BannerService bannerService;
	
	@Autowired
	private BannerValidator bannerValidator;
	
	@Autowired
	private HttpServletRequest request;
	
	@Autowired
	private FileInfoService fileInfoService;
	
	/**
	 * 배너/메인비주얼/팝업존/레이어팝업 목록을 반환한다.
	 * @param model
	 * @param bannerSearch
	 * @return
	 */
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/banner/list.do", method=RequestMethod.GET)
	public String bannerListGet(Model model, @CurrentSite Site currentSite, @ModelAttribute("bannerSearch") BannerSearch bannerSearch){
		bannerSearch.setSitePrefix(currentSite.getSitePrefix());
		bannerSearch.fixBrokenSvByDefaultCharsets();
		bannerSearch.setPaging(false);
		bannerSearch.setDefaultSort("BN_ORDER", "ASC");
		List<Banner> list = bannerService.getBannerList(bannerSearch);
		int total = bannerService.getBannerListTotal(bannerSearch);
		
		Paging paging = new Paging(list, total, bannerSearch);
		
		model.addAttribute("paging", paging);
		
		model.addAttribute("bnToday", new SimpleDateFormat("yyyy-MM-dd", Locale.KOREA).format(new Date()));
		
		return "asapro/admin/banner/list";
	}
	
	/**
	 * 배너/메인비주얼/팝업존/레이어팝업 추가폼 뷰를 반환한다.
	 * @param model
	 * @param bannerForm
	 * @return
	 */
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/banner/insert.do", method=RequestMethod.GET)
	public String bannerInsertGet(Model model, @CurrentSite Site currentSite, @ModelAttribute("bannerForm") Banner bannerForm){
		model.addAttribute("formMode", "INSERT");
		return "asapro/admin/banner/form";
	}
	
	/**
	 * 배너/메인비주얼/팝업존/레이어팝업을 추가한다.
	 * @param model
	 * @param bannerForm
	 * @param bindingResult
	 * @return 추가결과뷰
	 * @throws IOException 
	 * @throws AsaproException 
	 */
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/banner/insert.do", method=RequestMethod.POST)
	public String bannerInsertPost(Model model, RedirectAttributes redirectAttributes, @CurrentSite Site currentSite, @ModelAttribute("bannerForm") Banner bannerForm, BindingResult bindingResult) throws AsaproException, IOException{
		bannerForm.setSitePrefix(currentSite.getSitePrefix());
		bannerValidator.validate(bannerForm, bindingResult, "INSERT");
		if( bindingResult.hasErrors() ){
			model.addAttribute("formMode", "INSERT");
			return "asapro/admin/banner/form";
		} else {
			
			//첨부가능한 확장자
			String uploadWhiteList = "";
			
			FileInfoUploadResult thumbFileInfoUploadResult = new FileInfoUploadResult();
			
			//이미지 처리
			if( bannerForm.getBnImage() != null && bannerForm.getBnImage().getSize() > 0 ){

				uploadWhiteList = Constant.UPLOAD_IMAGE_WHITE_LIST; 
				String webRoot = AsaproUtils.getWebRoot(request);
				
				//첨부된 파일 
				FileInfo fileInfo = new FileInfo();
				fileInfo.setSitePrefix(bannerForm.getSitePrefix());
				//모듈1차pk
				fileInfo.setFileModule("banner");
				fileInfo.setFileModuleId(bannerForm.getBnType());
				//모듈2차pk
				fileInfo.setFileModuleSub("");
				fileInfo.setFileModuleSubId("");
				//멤버
				fileInfo.setMemberId("");
				//첨부유형
				fileInfo.setFileAttachmentType("thumbnail");
				//대체텍스트
				if(StringUtils.isNotBlank(bannerForm.getBnName()) ){
					fileInfo.setFileAltText(bannerForm.getBnName() );
				}else{
					String fileNameTemp = FilenameUtils.getBaseName(bannerForm.getBnImage().getOriginalFilename());
					fileInfo.setFileAltText( Jsoup.clean(fileNameTemp, Whitelist.none()).replace("\"", "") );
				}
				
				//썸네일 파일은 만들지 않는다
				fileInfoService.saveFile(bannerForm.getBnImage(), webRoot, fileInfo, (Constant.MEGA * 1), false, 100, 100, true, uploadWhiteList);
				if( !fileInfo.isFileUploadSuccess() ){
					thumbFileInfoUploadResult.addErrorInfo(fileInfo);
				}
				
				bannerForm.setThumb(fileInfo);
				
			}

			//업로드 에러 있으면 등록화면으로 전환
			if(thumbFileInfoUploadResult.hasErrors() ){
				//FlashMap flashMap = RequestContextUtils.getOutputFlashMap(request);
				//flashMap.put("thumbFileInfoUploadResult", thumbFileInfoUploadResult);
				
				//redirectAttributes.addFlashAttribute("thumbFileInfoUploadResult", thumbFileInfoUploadResult);
				//return "redirect:" + AsaproUtils.getAdminPath(currentSite, true) +  "/banner/update.do?bnType=" + bannerForm.getBnType() + "&bnId=" + bannerForm.getBnId();
				
				model.addAttribute("thumbFileInfoUploadResult", thumbFileInfoUploadResult);
				model.addAttribute("formMode", "INSERT");
				return "asapro/admin/banner/form";
			}
			
			bannerService.insertBanner(bannerForm);
			redirectAttributes.addFlashAttribute("inserted", true);
			
			return "redirect:" + AsaproUtils.getAdminPath(currentSite, true) + "/banner/list.do?bnType=" + bannerForm.getBnType();
		}
	}
	
	/**
	 * 배너/메인비주얼/팝업존/레이어팝업 수정폼 뷰를 반환한다.
	 * @param model
	 * @param bannerForm
	 * @return
	 */
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/banner/update.do", method=RequestMethod.GET)
	public String bannerUpdateGet(Model model, @CurrentSite Site currentSite, @ModelAttribute("bannerForm") Banner bannerForm){
		bannerForm.setSitePrefix(currentSite.getSitePrefix());
		model.addAttribute("formMode", "UPDATE");
		model.addAttribute("bannerForm", bannerService.getBanner(bannerForm));
		return "asapro/admin/banner/form";
	}
	
	/**
	 * 배너/메인비주얼/팝업존/레이어팝업을 수정하고 목록 뷰를 반환한다.
	 * @param model
	 * @param bannerForm
	 * @param bindingResult
	 * @return
	 * @throws IOException 
	 * @throws AsaproException 
	 */
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/banner/update.do", method=RequestMethod.POST)
	public String bannerUpdatePost(Model model, RedirectAttributes redirectAttributes, @CurrentSite Site currentSite, @ModelAttribute("bannerForm") Banner bannerForm, BindingResult bindingResult) throws AsaproException, IOException{
		bannerForm.setSitePrefix(currentSite.getSitePrefix());
		bannerValidator.validate(bannerForm, bindingResult, "UPDATE");
		if( bindingResult.hasErrors() ){
			model.addAttribute("formMode", "UPDATE");
			return "asapro/admin/banner/form";
		} else {
			
			Banner fromDB = bannerService.getBanner(bannerForm);
			bannerForm.setBnExt(fromDB.getBnExt());
			bannerForm.setThumb(fromDB.getThumb());
			
			//첨부가능한 확장자
			String uploadWhiteList = Constant.UPLOAD_IMAGE_WHITE_LIST; 
			
			FileInfoUploadResult thumbFileInfoUploadResult = new FileInfoUploadResult();
			
			//새 이미지 처리
			if( bannerForm.getBnImage() != null && bannerForm.getBnImage().getSize() > 0 ){

				//기존이미지 삭제
				if(fromDB.getThumb() != null && fromDB.getThumb().getFileId() > 0){
					FileInfo fileInfoImage = fromDB.getThumb();
					fileInfoImage.setSitePrefix(bannerForm.getSitePrefix());
					fileInfoService.deleteFileInfo(fileInfoImage);
				}

				String webRoot = AsaproUtils.getWebRoot(request);
				
				//첨부된 파일 
				FileInfo fileInfo = new FileInfo();
				fileInfo.setSitePrefix(bannerForm.getSitePrefix());
				//모듈1차pk
				fileInfo.setFileModule("banner");
				fileInfo.setFileModuleId(bannerForm.getBnType());
				//모듈2차pk
				fileInfo.setFileModuleSub("");
				fileInfo.setFileModuleSubId("");
				//멤버
				fileInfo.setMemberId("");
				//첨부유형
				fileInfo.setFileAttachmentType("thumbnail");
				//대체텍스트
				if(StringUtils.isNotBlank(bannerForm.getBnName()) ){
					fileInfo.setFileAltText(bannerForm.getBnName() );
				}else{
					String fileNameTemp = FilenameUtils.getBaseName(bannerForm.getBnImage().getOriginalFilename());
					fileInfo.setFileAltText( Jsoup.clean(fileNameTemp, Whitelist.none()).replace("\"", "") );
				}
				
				//썸네일 파일은 만들지 않는다
				fileInfoService.saveFile(bannerForm.getBnImage(), webRoot, fileInfo, (Constant.MEGA * 1), false, 100, 100, true, uploadWhiteList);
				if( !fileInfo.isFileUploadSuccess() ){
					thumbFileInfoUploadResult.addErrorInfo(fileInfo);
				}
				
				bannerForm.setThumb(fileInfo);
				
			}

			//업로드 에러 있으면 바로 수정화면으로 전환
			if(thumbFileInfoUploadResult.hasErrors() ){
				//FlashMap flashMap = RequestContextUtils.getOutputFlashMap(request);
				//flashMap.put("thumbFileInfoUploadResult", thumbFileInfoUploadResult);
				
				//redirectAttributes.addFlashAttribute("thumbFileInfoUploadResult", thumbFileInfoUploadResult);
				//return "redirect:" + AsaproUtils.getAdminPath(currentSite, true) +  "/banner/update.do?bnType=" + bannerForm.getBnType() + "&bnId=" + bannerForm.getBnId();
				
				model.addAttribute("thumbFileInfoUploadResult", thumbFileInfoUploadResult);
				model.addAttribute("formMode", "UPDATE");
				return "asapro/admin/banner/form";
			}
			
			bannerService.updateBanner(bannerForm);
			redirectAttributes.addFlashAttribute("updated", true);
			
			return "redirect:" + AsaproUtils.getAdminPath(currentSite, true) + "/banner/update.do?bnType=" + bannerForm.getBnType() + "&bnId=" + bannerForm.getBnId();
		}
	}
	
	/**
	 * 배너/메인비주얼/팝업존/레이어팝업을 삭제한다.
	 * @param model
	 * @param bnIds
	 * @return 삭제결과
	 * @throws FileNotFoundException 
	 */
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/banner/delete.do", method=RequestMethod.POST)
	@ResponseBody
	public ServerMessage deleteBannerPost(Model model, @CurrentSite Site currentSite, @RequestParam(value="bnIds[]", required=true) Integer[] bnIds) throws FileNotFoundException{
		ServerMessage serverMessage = new ServerMessage();
		if( ArrayUtils.isEmpty(bnIds) ){
			serverMessage.setSuccess(false);
			serverMessage.setText("삭제할 항목이 없습니다.");
		} else {
			List<Banner> bannerList = new ArrayList<Banner>();
			Banner banner = null;
			for( Integer bnId : bnIds ){
				banner = new Banner();
				banner.setSitePrefix(currentSite.getSitePrefix());
				banner.setBnId(bnId);
				bannerList.add(banner);
			}
			int result = bannerService.deleteBanner(bannerList);
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
	 * 배너/메인비주얼/팝업존/레이어팝업 정렬 순서를 수정한다.
	 * @param model
	 * @param bnOrders
	 * @return 수정 결과 json
	 */
	/*
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/banner/reOrder.do", method=RequestMethod.POST)
	@ResponseBody
	public ServerMessage reOrderBannerPost(Model model, @CurrentSite Site currentSite, @RequestParam(value="bnOrders", required=true) String bnOrders){
		if(StringUtils.isBlank(bnOrders)){
			return new ServerMessage(false, "수정할 데이터가 없습니다.");
		} else {
			
			List<Banner> banners = new ArrayList<Banner>();
			String[] temps = bnOrders.split(",");
			Banner banner = null;
			for(String str : temps){
				banner = new Banner();
				banner.setSitePrefix(currentSite.getSitePrefix());
				banner.setBnId(Integer.valueOf(str.split("@")[0]));
				banner.setBnOrder(Integer.parseInt(str.split("@")[1]));
				banners.add(banner);
			}
			
			if( bannerService.updateBannerOrder(banners) >= 0 ){
				return new ServerMessage(true, "수정되었습니다.");
			} else {
				return new ServerMessage(false, "수정하지 못하였습니다.[Server Error]");
			}
		}
	}
	*/
	
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/banner/reOrder.do", method=RequestMethod.POST)
	@ResponseBody
	public ServerMessage reOrderBannerPost(Model model, @CurrentSite Site currentSite, @RequestParam(value="bnIds[]", required=true) Integer[] bnIds){
		ServerMessage serverMessage = new ServerMessage();
		
		if(ArrayUtils.isEmpty(bnIds) ){
			serverMessage.setSuccess(false);
			serverMessage.setText("수정할 항목이 없습니다.");
		} else {
			int order = 0;
			
			List<Banner> banners = new ArrayList<Banner>();
			Banner banner = null;
			for(Integer bnId : bnIds){
				order++;
				banner = new Banner();
				banner.setSitePrefix(currentSite.getSitePrefix());
				banner.setBnId(bnId);
				banner.setBnOrder(order);
				banners.add(banner);
			}
			
			int result = bannerService.updateBannerOrder(banners);
			if( result > 0 ){
				serverMessage.setSuccess(true);
				serverMessage.setSuccessCnt(result);
			} else {
				serverMessage.setSuccess(false);
				serverMessage.setText("수정하지 못하였습니다.[Server Error]");
			}
		}
		return serverMessage;
	}
}
