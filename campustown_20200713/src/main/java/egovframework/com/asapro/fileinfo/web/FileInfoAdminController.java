/**
 * 
 */
package egovframework.com.asapro.fileinfo.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import egovframework.com.asapro.board.service.BoardService;
import egovframework.com.asapro.config.service.Config;
import egovframework.com.asapro.config.service.ConfigService;
import egovframework.com.asapro.fileinfo.service.FileInfo;
import egovframework.com.asapro.fileinfo.service.FileInfoSearch;
import egovframework.com.asapro.fileinfo.service.FileInfoService;
import egovframework.com.asapro.fileinfo.service.FileInfoUploadResult;
import egovframework.com.asapro.member.admin.service.AdminMember;
import egovframework.com.asapro.site.service.Site;
//import egovframework.com.asapro.site.service.SiteService;
import egovframework.com.asapro.support.Constant;
import egovframework.com.asapro.support.MimeTypeCheck;
import egovframework.com.asapro.support.ServerMessage;
import egovframework.com.asapro.support.annotation.CurrentAdmin;
import egovframework.com.asapro.support.annotation.CurrentSite;
import egovframework.com.asapro.support.exception.AsaproException;
import egovframework.com.asapro.support.pagination.Paging;
import egovframework.com.asapro.support.util.AsaproUtils;
import egovframework.com.asapro.support.util.AsaproXSSFilterUtils;
import egovframework.com.cmm.service.EgovProperties;

/**
 * 파일관리시스템 관리자 컨트롤러
 * @author yckim
 * @since 2019. 12. 6.
 *
 */
@Controller
public class FileInfoAdminController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(FileInfoAdminController.class);
	
	@Autowired
	private FileInfoService fileInfoService;
	
	@Autowired
	private BoardService boardService;
	
	@Autowired
	private HttpServletRequest request;
	
	@Autowired
	private ConfigService configService;
	
//	@Autowired
//	private SiteService siteService;
	
	
	/**
	 * 파일정보 목록을 반환한다.
	 * @param model
	 * @param currentSite
	 * @param fileInfoSearch
	 * @return 파일정보 목록
	 */
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/fileinfo/list.do", method=RequestMethod.GET)
	public String fileInfoListGet(Model model, @CurrentSite Site currentSite, @ModelAttribute("fileInfoSearch") FileInfoSearch fileInfoSearch ){
		fileInfoSearch.setSitePrefix(currentSite.getSitePrefix());
		//fileInfoSearch.setFileModule("media");
		fileInfoSearch.fixBrokenSvByDefaultCharsets();
		List<FileInfo> list = fileInfoService.getFileInfoList(fileInfoSearch);
		int total = fileInfoService.getFileInfoListTotal(fileInfoSearch);
		
		Paging paging = new Paging(list, total, fileInfoSearch);
		model.addAttribute("paging", paging);
		
		model.addAttribute("mediaCount", fileInfoService.getMediaCountMap(fileInfoSearch));
		
		return "asapro/admin/fileinfo/list";
	}
	
	/**
	 * 파일정보를 조회한다.
	 * @param model
	 * @param currentSite
	 * @param fileInfoForm
	 * @return 파일정보
	 */
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/fileinfo/update.do", method=RequestMethod.GET)
	public String updateFileInfoGet(Model model, @CurrentSite Site currentSite, @ModelAttribute("fileInfoForm") FileInfo fileInfoForm ){
		model.addAttribute("formMode", "UPDATE");
		fileInfoForm.setSitePrefix(currentSite.getSitePrefix());
		fileInfoForm.setFileId(fileInfoForm.getFileId());
		FileInfo fileInfoModel = fileInfoService.getFileInfo(fileInfoForm);
		model.addAttribute("fileInfoForm", fileInfoModel);
		
		String fileStorePath = EgovProperties.getProperty("Globals.fileStorePath");
		if(StringUtils.isBlank(fileStorePath)){
			fileStorePath = AsaproUtils.getWebRoot(request);
		}
		model.addAttribute("fileStorePath", fileStorePath);
		
		return "asapro/admin/fileinfo/form";
	}
	
	/**
	 * 파일 업로드 폼을 반환환다.
	 * @param model
	 * @param currentSite
	 * @param fileInfoForm
	 * @return 파일 업로드 폼
	 */
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/file/upload.do", method=RequestMethod.GET)
	public String insertFileGet(Model model, @CurrentSite Site currentSite, @ModelAttribute("fileInfoForm") FileInfo fileInfoForm){
		model.addAttribute("formMode", "INSERT");
		return "asapro/admin/fileinfo/uploadForm";
	}
	
	/**
	 * 파일을 업로드 한다.
	 * @param response
	 * @param model
	 * @param currentAdmin
	 * @param currentSite
	 * @param fileInfoForm
	 * @throws IOException
	 * @throws AsaproException
	 */
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/file/upload.do", method=RequestMethod.POST)
	@ResponseBody
	public void insertFilePost(HttpServletResponse response, Model model, @CurrentAdmin AdminMember currentAdmin, @CurrentSite Site currentSite, @ModelAttribute("fileInfoForm") FileInfo fileInfoForm) throws IOException, AsaproException{
		model.addAttribute("formMode", "INSERT");
		
		String webRoot = AsaproUtils.getWebRoot(request);
		
		FileInfo fileInfo = new FileInfo();
		fileInfo.setSitePrefix(currentSite.getSitePrefix());
		//모듈1차pk
		fileInfo.setFileModule("fileManager");
		//fileInfo.setFileModuleId("media");
		//멤버
		fileInfo.setMemberId(currentAdmin.getAdminId());
		//첨부유형
		fileInfo.setFileAttachmentType("fileManager");
		//대체텍스트
		String filename = fileInfoForm.getFile().getOriginalFilename();
		String basename = AsaproUtils.string2TagName(FilenameUtils.getBaseName(filename));
		fileInfo.setFileAltText(basename);
		
		Config mediaConfig = configService.getConfig("media");
		Config siteConfig = configService.getConfig("site");
		Config watermarkConfig = configService.getConfig("watermark");
		
		
		
		MimeTypeCheck mtc = MimeTypeCheck.getMimeType( FilenameUtils.getExtension(filename) );
		//ffmpeg 인코딩 사용중일경우
		if( "true".equals(mediaConfig.getOption("use_ffmpeg")) ){
			//동영상이나 오디오 업로드이면 튕겨낸다
			if( mtc.getMediaType().equals(MimeTypeCheck.VIDEO) || mtc.getMediaType().equals(MimeTypeCheck.AUDIO) ){
				FileInfo uploadResult = new FileInfo();
				uploadResult.setFileUploadSuccess(false);
				uploadResult.setFileUploadErrorCode(FileInfoUploadResult.ERROR_USE_MULTIMEDIA_UPLOADER);
				//AsaproUtils.writeJson(response, uploadResult);
				//return;
			}
		} 
				
		FileInfo uploadResult = fileInfoService.saveFileMakeThumbWithCustomOption(
			fileInfoForm.getFile()
			, webRoot
			, fileInfo
			, Constant.MEGA * Integer.parseInt(mediaConfig.getOption("upload_size_limit"))
			, Integer.parseInt(StringUtils.defaultString(siteConfig.getOption("thumb_default_width"), "400"))
			, Integer.parseInt(StringUtils.defaultString(siteConfig.getOption("thumb_default_height"), "300"))
			, true
			, ""
			, "true".equals(watermarkConfig.getOption("watermark_use"))
		);
		//AsaproUtils.writeJson(response, uploadResult);
		
	}
	
	/**
	 * 실제파일 삭제 + 파일정보를 삭제한다.
	 * @param model
	 * @param currentSite
	 * @param fileIds
	 * @return 삭제결과
	 */
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/fileinfo/delete.do", method=RequestMethod.POST)
	@ResponseBody
	public ServerMessage deleteFileInfoPost(Model model, @CurrentSite Site currentSite, @RequestParam(value="fileIds[]", required=true) Integer[] fileIds){
		ServerMessage serverMessage = new ServerMessage();
		if( ArrayUtils.isEmpty(fileIds) ){
			serverMessage.setSuccess(false);
			serverMessage.setText("삭제할 항목이 없습니다.");
		} else {
			List<FileInfo> fileInfoList = new ArrayList<FileInfo>();
			FileInfo fileInfo = null;
			for(Integer fileId : fileIds){
				fileInfo = new FileInfo();
				fileInfo.setFileId(fileId);
				fileInfo.setSitePrefix(currentSite.getSitePrefix());
				fileInfoList.add(fileInfo);
			}
			int result = fileInfoService.deleteFileInfo(fileInfoList);
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
	
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/fileinfo/altText/update.do", method=RequestMethod.POST)
	@ResponseBody
	public ServerMessage updateFileInfoAltTextPost(Model model, @CurrentSite Site currentSite, @ModelAttribute("fileInfoForm") FileInfo fileInfoForm){
		ServerMessage serverMessage = new ServerMessage();
		if( fileInfoForm.getFileId() == null || fileInfoForm.getFileId() <= 0 ){
			serverMessage.setSuccess(false);
			serverMessage.setText("수정할 항목이 없습니다.");
		} else {
			fileInfoForm.setSitePrefix(currentSite.getSitePrefix());
			
			int result = fileInfoService.updateFileInfoAltText(fileInfoForm);
			
			if(result > 0){
				serverMessage.setSuccess(true);
				serverMessage.setSuccessCnt(result);
			} else {
				serverMessage.setSuccess(false);
				serverMessage.setText("수정하지 못하였습니다.[Server Error]");
			}
		}
		return serverMessage;
	}
	
	/**
	 * 웹에디터에서 첨부한 이미지를 업로드한다.
	 * @param request
	 * @param response
	 * @param model
	 * @param currentSite
	 * @param currentAdmin
	 * @param upload
	 * @param module
	 * @param moduleId
	 * @param moduleSub
	 * @param moduleSubId
	 * @param bindingResult
	 * @throws IOException
	 */
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/file/editor/upload.do", method=RequestMethod.POST)
	public void boardEditerFileUploadPost(HttpServletRequest request, HttpServletResponse response, Model model, @CurrentSite Site currentSite
			, @CurrentAdmin AdminMember currentAdmin, MultipartFile upload
			, @RequestParam(value="module", required=true) String module, @RequestParam(value="moduleId", defaultValue="") String moduleId
			, @RequestParam(value="moduleSub", defaultValue="") String moduleSub, @RequestParam(value="moduleSubId", defaultValue="") String moduleSubId) throws IOException{

		//게시판에서 설정된 썸네일 가로 세로
		int bcWidth = 400;	//default
		int bcHeight = 300;	//default
		
		//EgovProperties.getProperty("Globals.fileStorePath")
		//Globals.fileStorePath 의 값이 있을경우 지정한 경로에 저장되게 하고 값이없을경우 디폴트로 설정된 컨텍스트내 폴더에 저장된다.
		String webRoot = EgovProperties.getProperty("Globals.fileStorePath");
		if(StringUtils.isBlank(webRoot)){
			webRoot = AsaproUtils.getWebRoot(request);
		}
		
		//첨부가능한 확장자
		String uploadWhiteList = Constant.UPLOAD_IMAGE_WHITE_LIST;
		
		//첨부된 파일 개수
		FileInfo fileInfo = new FileInfo();
		fileInfo.setSitePrefix(currentSite.getSitePrefix());
		//모듈1차pk
		fileInfo.setFileModule(module);
		fileInfo.setFileModuleId(moduleId);
		//모듈2차pk
		fileInfo.setFileModuleSub(moduleSub);
		fileInfo.setFileModuleSubId(moduleSubId);
		//멤버
		fileInfo.setMemberId(currentAdmin.getAdminId());
		//첨부유형
		fileInfo.setFileAttachmentType("editor");
		//대체텍스트
		String basename = AsaproUtils.string2TagName(FilenameUtils.getBaseName(upload.getOriginalFilename()));
		fileInfo.setFileAltText( Jsoup.clean(basename, Whitelist.none()).replace("\"", "") );
		
		//파일 저장
		fileInfoService.saveFile(upload, webRoot, fileInfo, (Constant.MEGA * 10), true, bcWidth, bcHeight, true, uploadWhiteList);
		
		//OutputStream out = null;
		PrintWriter printWriter = null;
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		
		//파라메터 추가필터링 처리
		String CKEditorFuncNum = request.getParameter("CKEditorFuncNum");
		AsaproXSSFilterUtils.cleanStringFields(CKEditorFuncNum);

		printWriter = response.getWriter();
		response.flushBuffer();
		//OutputStream out = response.getOutputStream();
		if( fileInfo.isFileUploadSuccess() ){
			if(StringUtils.isNotBlank(EgovProperties.getProperty("Globals.fileStorePath")) ){
				printWriter.println("<script>window.parent.CKEDITOR.tools.callFunction(" + CKEditorFuncNum +", '" + fileInfo.getFileServletUrl() + "', '이미지를 업로드 하였습니다.');</script>");
			}else{
				printWriter.println("<script>window.parent.CKEDITOR.tools.callFunction(" + CKEditorFuncNum +", '" + fileInfo.getFilePath() + "', '이미지를 업로드 하였습니다.');</script>");
			}
		}else{
			printWriter.println("<script>alert('허용되지 않은 파일 유형입니다.');</script>");
		}
		if(printWriter != null){
			printWriter.flush();
			printWriter.close();
		}
		//return fileInfoUploadResult;
	}
	
	/**
	 * 이미지 브라우저를 반환한다.
	 * @param model
	 * @param currentSite
	 * @param fileInfoSearch
	 * @return 이미지브라우저
	 */
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/fileinfo/imageBrowse.do", method=RequestMethod.GET)
	public String imageBrowseGet(Model model, @CurrentSite Site currentSite, @ModelAttribute("fileInfoSearch") FileInfoSearch fileInfoSearch ){
		fileInfoSearch.setSitePrefix(currentSite.getSitePrefix());
		//fileInfoSearch.setFileModule("media");
		fileInfoSearch.fixBrokenSvByDefaultCharsets();
		List<FileInfo> list = fileInfoService.getFileInfoList(fileInfoSearch);
		int total = fileInfoService.getFileInfoListTotal(fileInfoSearch);
		
		Paging paging = new Paging(list, total, fileInfoSearch);
		model.addAttribute("paging", paging);
		
		//model.addAttribute("mediaCount", fileInfoService.getMediaCountMap(fileInfoSearch));
		
		return "asapro/admin/fileinfo/imageBrowse";
	}
}
