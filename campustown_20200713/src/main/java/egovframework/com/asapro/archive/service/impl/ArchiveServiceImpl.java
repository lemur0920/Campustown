/**
 * 
 */
package egovframework.com.asapro.archive.service.impl;

import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import egovframework.com.asapro.support.Constant;
import egovframework.com.asapro.support.exception.AsaproException;
import egovframework.com.asapro.support.util.AsaproUtils;
import egovframework.com.asapro.support.util.EtcUtil;
import egovframework.com.asapro.support.annotation.ArchiveItem;
import egovframework.com.asapro.archive.service.Archive;
import egovframework.com.asapro.archive.service.ArchiveCategory;
import egovframework.com.asapro.archive.service.ArchiveCategorySearch;
import egovframework.com.asapro.archive.service.ArchiveMapper;
import egovframework.com.asapro.archive.service.ArchiveSearch;
import egovframework.com.asapro.archive.service.ArchiveService;
import egovframework.com.asapro.board.service.BoardArticle;
import egovframework.com.asapro.board.service.BoardConfig;
import egovframework.com.asapro.fileinfo.service.FileInfo;
import egovframework.com.asapro.fileinfo.service.FileInfoService;
import egovframework.com.asapro.fileinfo.service.FileInfoUploadResult;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

/**
 * 아카이브 관리 서비스 구현
 * @author yckim
 * @since 2018. 12. 19.
 *
 */
@Service
public class ArchiveServiceImpl extends EgovAbstractServiceImpl implements ArchiveService{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ArchiveServiceImpl.class);
	
	@Autowired
	private ArchiveMapper archiveMapper;

	@Autowired
	private HttpServletRequest request;
	
	@Autowired
	private FileInfoService fileInfoService;

	//=============================================================================================================================
	//==========================================  아카이브 카테고리   ================================================================
	//=============================================================================================================================
	
	/**
	 * 아카이브 카테고리 목록을 조회
	 */
	@Override
	public List<ArchiveCategory> getArchiveCategoryList(ArchiveCategorySearch archiveCategorySearch) {
		List<ArchiveCategory> archiveCategoryList = archiveMapper.selectArchiveCategoryList(archiveCategorySearch);
		return archiveCategoryList;
	}

	/**
	 * 아카이브 카테고리 목록 토탈 조회
	 */
	@Override
	public int getArchiveCategoryListTotal(ArchiveCategorySearch archiveCategorySearch) {
		int total = archiveMapper.selectArchiveCategoryListTotal(archiveCategorySearch);
		return total;
	}

	/**
	 * 아카이브 카테고리를 추가한다.
	 */
	@Override
	public int insertArchiveCategory(ArchiveCategory archiveCategoryForm) {
		int inserted = archiveMapper.insertArchiveCategory(archiveCategoryForm);
		return inserted;
	}

	/**
	 * 아카이브 카테고리를 조회한다.
	 */
	@Override
	public ArchiveCategory getArchiveCategory(ArchiveCategory archiveCategoryForm) {
		ArchiveCategory archiveCategory = archiveMapper.selectArchiveCategory(archiveCategoryForm);
		return archiveCategory;
	}

	/**
	 * 아카이브 카테고리를 수정한다.
	 */
	@Override
	public int updateArchiveCategory(ArchiveCategory archiveCategoryForm) {
		int updated = archiveMapper.updateArchiveCategory(archiveCategoryForm);
		return updated;
	}

	/**
	 * 아카이브 카테고리를 삭제한다.
	 */
	@Override
	public int deleteArchiveCategory(List<ArchiveCategory> archiveCategoryList) {
		int deleted  = 0;
		for (ArchiveCategory archiveCategory : archiveCategoryList) {
			deleted += this.deleteArchiveCategory(archiveCategory);
		}
		return deleted;
	}

	/**
	 * 아카이브 카테고리를 삭제한다.
	 */
	@Override
	public int deleteArchiveCategory(ArchiveCategory archiveCategory) {
		int deleted = archiveMapper.deleteArchiveCategory(archiveCategory);
		return deleted;
	}

	
	private static List<Map<String, String>> archiveItemList = null;
	/**
	 * ArchiveItem 어노테이션이 붙은 클래스의 정보를 반환한다.
	 * - 아카이브 아이템정보(상속)
	 */
	@Override
	public List<Map<String, String>> getArchiveItemAnnotationedList(){
		
		synchronized (this) {
			if( archiveItemList == null ){
				archiveItemList = new ArrayList<Map<String,String>>(); 
				ClassPathScanningCandidateComponentProvider scanner = new ClassPathScanningCandidateComponentProvider(false);//커스텀 필터를 사용하려면 false로 줘야 함
				scanner.addIncludeFilter(new AnnotationTypeFilter(ArchiveItem.class));
				try {
					Map<String, String> map = null;
					for (BeanDefinition bd : scanner.findCandidateComponents("egovframework.com.asapro.archive.customtype")){
						map = new HashMap<String, String>();
						String customType = Class.forName(bd.getBeanClassName()).getAnnotation(ArchiveItem.class).customType();
						String label = Class.forName(bd.getBeanClassName()).getAnnotation(ArchiveItem.class).label();
						map.put("customType", customType);
						map.put("label", label);
						archiveItemList.add(map);
					}
				} catch (ClassNotFoundException e) {
					LOGGER.error("[ClassNotFoundException] Try/Catch... : "+ e.getMessage());
				}
			}
		}
		return archiveItemList;
	}
		
		
	//=============================================================================================================================
	//==========================================  아카이브    ================================================================
	//=============================================================================================================================
	
	/**
	 * 아카이브 목록을 조회한다.
	 */
	@Override
	public List<Archive> getArchiveList(ArchiveSearch archiveSearch) {
		List<Archive> list = archiveMapper.selectArchiveList(archiveSearch);
		return list;
	}
	
	/**
	 * 아카이브 목록 토탈을 조회한다.
	 */
	@Override
	public int getArchiveListTotal(ArchiveSearch archiveSearch) {
		int total = archiveMapper.selectArchiveListTotal(archiveSearch);
		return total;
	}
	
	/**
	 * 아카이브를 추가한다.
	 */
	@Override
	public Map<String, FileInfoUploadResult> insertArchive(Archive archiveForm) throws AsaproException, IOException{
		
		Map<String, FileInfoUploadResult> fileInfoUploadResultMap = new HashMap<String, FileInfoUploadResult>();
		FileInfoUploadResult thumbFileInfoUploadResult = new FileInfoUploadResult();
		FileInfoUploadResult fileInfoUploadResult = new FileInfoUploadResult();
		ArchiveCategory archiveCategory = archiveForm.getArchiveCategory();
		
		//첨부가능한 확장자
		String uploadWhiteList = "";
		
		//대표이미지 처리
		if( archiveForm.getArcThumbFile() != null && archiveForm.getArcThumbFile().getSize() > 0 ){
			
			uploadWhiteList = Constant.UPLOAD_IMAGE_WHITE_LIST; 
			FileInfo fileInfo = this.saveMultipartFile(archiveForm.getArcThumbFile(), archiveForm.getArcThumbText(), archiveCategory, archiveCategory.getCatUploadSizeMax(), thumbFileInfoUploadResult, archiveForm, uploadWhiteList, "thumbnail");
			archiveForm.setThumb(fileInfo);
		}
		
		//아카이브 정보 등록
		archiveMapper.insertArchive(archiveForm);
		
		//첨부파일 처리
		if( archiveForm.getArcMultipartFiles() != null && !archiveForm.getArcMultipartFiles().isEmpty() ){
			int size = archiveForm.getArcMultipartFiles().size();
			MultipartFile multipartFile = null;
			String arcAltText = "";
			for( int i = 0; i<size; i++ ){
				multipartFile = archiveForm.getArcMultipartFiles().get(i);
				
				if( archiveForm.getArcAltTexts() != null && !archiveForm.getArcAltTexts().isEmpty() ){
					arcAltText = archiveForm.getArcAltTexts().get(i);
				}
				//첨부파일의 정보와 파일을 저장
				this.saveMultipartFile(multipartFile, arcAltText, archiveCategory, archiveCategory.getCatUploadSizeMax(), fileInfoUploadResult, archiveForm, "", "appending");
			}
		}
		
		fileInfoUploadResultMap.put("thumbFileInfoUploadResult", thumbFileInfoUploadResult);
		fileInfoUploadResultMap.put("fileInfoUploadResult", fileInfoUploadResult);
		return fileInfoUploadResultMap;
	}
	
	
	
	/**
	 * 아카이브를 조회한다.
	 */
	@Override
	public Archive getArchive(Archive archiveForm) {
		Archive archive = archiveMapper.selectArchive(archiveForm);
		return archive;
	}

	/**
	 * 아카이브를 수정한다.
	 */
	@Override
	public Map<String, FileInfoUploadResult> updateArchive(Archive archiveForm) throws AsaproException, IOException{
		Map<String, FileInfoUploadResult> fileInfoUploadResultMap = new HashMap<String, FileInfoUploadResult>();
		FileInfoUploadResult thumbFileInfoUploadResult = new FileInfoUploadResult();
		FileInfoUploadResult fileInfoUploadResult = new FileInfoUploadResult();
		ArchiveCategory archiveCategory = archiveForm.getArchiveCategory();
		
		//첨부가능한 확장자
		String uploadWhiteList = "";
		
		//대표이미지 처리
		if( archiveForm.getArcThumbFile() != null && archiveForm.getArcThumbFile().getSize() > 0 ){
			//기존 대표이미지 삭제
			if(archiveForm.getThumb() != null && archiveForm.getThumb().getFileId() > 0){
				archiveForm.getThumb().setSitePrefix(archiveForm.getSitePrefix());
				fileInfoService.deleteFileInfo(archiveForm.getThumb());
			}
			
			uploadWhiteList = Constant.UPLOAD_IMAGE_WHITE_LIST; 
			FileInfo fileInfo = this.saveMultipartFile(archiveForm.getArcThumbFile(), archiveForm.getArcThumbText(), archiveCategory, archiveCategory.getCatUploadSizeMax(), thumbFileInfoUploadResult, archiveForm, uploadWhiteList, "thumbnail");
			archiveForm.setThumb(fileInfo);
		}
		
		//아카이브 정보 등록
		archiveMapper.updateArchive(archiveForm);
		
		//삭제파일 처리
		if( archiveForm.getFileInfoDeleteIds() != null && !archiveForm.getFileInfoDeleteIds().isEmpty() ){
			FileInfo deleteFileInfo = null;
			for( Integer fileId : archiveForm.getFileInfoDeleteIds() ){
				deleteFileInfo = new FileInfo();
				deleteFileInfo.setSitePrefix(archiveForm.getSitePrefix());
				deleteFileInfo.setFileId(fileId);
				fileInfoService.deleteFileInfo(deleteFileInfo);
			}
		}
		
		//첨부파일 처리
		if( archiveForm.getArcMultipartFiles() != null && !archiveForm.getArcMultipartFiles().isEmpty() ){
			int size = archiveForm.getArcMultipartFiles().size();
			MultipartFile multipartFile = null;
			String arcAltText = "";
			for( int i = 0; i<size; i++ ){
				multipartFile = archiveForm.getArcMultipartFiles().get(i);
				
				if( archiveForm.getArcAltTexts() != null && !archiveForm.getArcAltTexts().isEmpty() ){
					arcAltText = archiveForm.getArcAltTexts().get(i);
				}
				//첨부파일의 정보와 파일을 저장
				this.saveMultipartFile(multipartFile, arcAltText, archiveCategory, archiveCategory.getCatUploadSizeMax(), fileInfoUploadResult, archiveForm, "", "appending");
			}
		}
		
		fileInfoUploadResultMap.put("thumbFileInfoUploadResult", thumbFileInfoUploadResult);
		fileInfoUploadResultMap.put("fileInfoUploadResult", fileInfoUploadResult);
		return fileInfoUploadResultMap;
	}

	/**
	 * 아카이브를 삭제한다.
	 */
	@Override
	public int deleteArchive(List<Archive> archiveList) {
		int deleted = 0;
		for (Archive archive : archiveList) {
			deleted += this.deleteArchive(archive);
		}
		return deleted;
	}

	/**
	 * 아카이브를 삭제한다.
	 */
	@Override
	public int deleteArchive(Archive archive) {
		Archive fromDB = archiveMapper.selectArchive(archive);
		fromDB.setSitePrefix(archive.getSitePrefix());
		
		int deleted = 0;
		deleted = archiveMapper.deleteArchive(archive);
		
		if(deleted > 0){
			
			//첨부파일 삭제
			for( FileInfo fileInfo : fromDB.getArcFileInfos() ){
				//파일 삭제
				fileInfo.setSitePrefix(archive.getSitePrefix());
				fileInfoService.deleteFileInfo(fileInfo);
			}
			//대표이미지 삭제
			if(fromDB.getThumb() != null && fromDB.getThumb().getFileId() > 0){
				FileInfo fileInfoImage = fromDB.getThumb();
				fileInfoImage.setSitePrefix(archive.getSitePrefix());
				fileInfoService.deleteFileInfo(fileInfoImage);
			}
			
		}
		return deleted;
	}

	/**
	 * 아카이브 조회수를 증가시킨다.
	 */
	@Override
	public int updateArchiveHit(Archive archiveForm) {
		return archiveMapper.updateArchiveHit(archiveForm);
	}
	

	/**
	 * 아카이브 추천수를 증가시킨다.
	 */
	@Override
	public int updateArchiveRecommend(Archive recommendArchive) {
		return archiveMapper.updateArchiveRecommend(recommendArchive);
	}



	//=============================================================================================================================
	//==========================================      ================================================================
	//=============================================================================================================================
	
	/**
	 * 첨부파일의 정보와 파일을 저장한다.
	 * @param multipartFile
	 * @param arcAltText
	 * @param archiveCategory
	 * @param fileInfoUploadResult
	 * @param archive
	 * @param uploadWhiteList
	 * @param attachmentType
	 * @return 파일정보
	 * @throws AsaproException
	 * @throws IOException
	 */
	@Override
	public FileInfo saveMultipartFile(MultipartFile multipartFile, String arcAltText, ArchiveCategory archiveCategory, FileInfoUploadResult fileInfoUploadResult, Archive archive, String uploadWhiteList, String attachmentType) throws AsaproException, IOException{
		return this.saveMultipartFile(multipartFile, arcAltText, archiveCategory, 0, fileInfoUploadResult, archive, uploadWhiteList, attachmentType);
	}
	
	/**
	 * 첨부파일의 정보와 파일을 저장한다.
	 * @param multipartFile
	 * @param arcAltText
	 * @param archiveCategory
	 * @param fileInfoUploadResult
	 * @param archive
	 * @param uploadWhiteList
	 * @param attachmentType
	 * @return 파일정보
	 * @throws AsaproException
	 * @throws IOException
	 */
	@Override
	public FileInfo saveMultipartFile(MultipartFile multipartFile, String arcAltText, ArchiveCategory archiveCategory, int fileSize, FileInfoUploadResult fileInfoUploadResult, Archive archive, String uploadWhiteList, String attachmentType) throws AsaproException, IOException{
		//설정기능 없으므로 일단 디폴트값으로 처리
		int thumbWidth = 400;	//default
		int thumbHeight = 400;	//default
		if( archiveCategory.getCatThumbnailWidth() != null && archiveCategory.getCatThumbnailWidth().intValue() > 0 ){
			thumbWidth = archiveCategory.getCatThumbnailWidth().intValue();
		}
		if( archiveCategory.getCatThumbnailHeight() != null && archiveCategory.getCatThumbnailHeight().intValue() > 0 ){
			thumbHeight = archiveCategory.getCatThumbnailHeight().intValue();
		}
		
		String webRoot = AsaproUtils.getWebRoot(request);
		
		//첨부된 파일 개수
		FileInfo fileInfo = new FileInfo();
		fileInfo.setSitePrefix(archive.getSitePrefix());
		//모듈1차pk
		fileInfo.setFileModule("archive");
		fileInfo.setFileModuleId(archiveCategory.getCatCustomType());
		//모듈2차pk
		fileInfo.setFileModuleSub(archiveCategory.getCatId().toString());
		fileInfo.setFileModuleSubId(String.valueOf(archive.getArcId()));
		//멤버
		fileInfo.setMemberId(archive.getMemId());
		//첨부유형
		fileInfo.setFileAttachmentType(attachmentType);
		//대체텍스트
		if( StringUtils.isNotBlank(arcAltText) ){
			//혹시 길이 안맞게 입력된 경우 - 아마 없겠지만 - 예외처리해줌.
			try{
				//alt에 "(쌍따옴표)가 들어 있는 경우가 가끔있는데 그런 경우 html이 깨짐 그래서 제거.escape시켜도 되긴 되는데 걍 깔끔하게 제거하겠음.
				fileInfo.setFileAltText( Jsoup.clean(arcAltText, Whitelist.none()).replace("\"", "") );
			} catch (IndexOutOfBoundsException iobe){
				LOGGER.error("[IndexOutOfBoundsException] Try/Catch... : "+ iobe.getMessage());
				fileInfo.setFileAltText( "" );
			}
		}
		
		else{
			String fileNameTemp = FilenameUtils.getBaseName(multipartFile.getOriginalFilename());
			fileInfo.setFileAltText( Jsoup.clean(fileNameTemp, Whitelist.none()).replace("\"", "") );
		}
		
		//파일크기 디폴트 
		if(fileSize == 0 ){
			fileSize = 5;
		}
		fileInfoService.saveFile(multipartFile, webRoot, fileInfo, (Constant.MEGA * fileSize), archiveCategory.isCatSupportThumbnail(), thumbWidth, thumbHeight, archiveCategory.isCatThumbnailCrop(), uploadWhiteList);
		if( !fileInfo.isFileUploadSuccess() ){
			fileInfoUploadResult.addErrorInfo(fileInfo);
		}
		return fileInfo;
	}

	
	
	/**
	 * 템플릿 파일 경로를 반환한다.
	 */
	@Override
	public String getArchiveTemplateName(String catCustomType, String catAdminListTemplate, String gradeMember) {
		String template = StringUtils.removeEndIgnoreCase(catAdminListTemplate, ".jsp");
		template = StringUtils.removeStartIgnoreCase(template, "/");
		if( StringUtils.isNotBlank(template) ){
			LOGGER.info("[ASAPRO] ArchiveAdminController template : {}", template);
		}
		
		if("user".equals(gradeMember) ){
			return AsaproUtils.getThemePath(request) + "archive/" + catCustomType + "/" + template;
		}else{//admin
			return "asapro/admin/archive/" + catCustomType + "/" + template;
		}
	}
	

}
