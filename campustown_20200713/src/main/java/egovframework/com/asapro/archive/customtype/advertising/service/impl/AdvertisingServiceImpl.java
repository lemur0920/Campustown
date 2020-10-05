/**
 * 
 */
package egovframework.com.asapro.archive.customtype.advertising.service.impl;

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
import egovframework.com.asapro.archive.customtype.advertising.service.Advertising;
import egovframework.com.asapro.archive.customtype.advertising.service.AdvertisingMapper;
import egovframework.com.asapro.archive.customtype.advertising.service.AdvertisingSearch;
import egovframework.com.asapro.archive.customtype.advertising.service.AdvertisingService;
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
 * 광고자료 관리 서비스 구현
 * @author yckim
 * @since 2019. 9. 30.
 *
 */
@Service
public class AdvertisingServiceImpl extends EgovAbstractServiceImpl implements AdvertisingService{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AdvertisingServiceImpl.class);
	
	@Autowired
	private ArchiveService archiveService;
	
	@Autowired
	private AdvertisingMapper advertisingMapper;

	@Autowired
	private ArchiveMapper archiveMapper;

	@Autowired
	private HttpServletRequest request;
	
	@Autowired
	private FileInfoService fileInfoService;

		
	//=============================================================================================================================
	//==========================================  광고자료    ================================================================
	//=============================================================================================================================
	
	/**
	 * 광고자료 목록을 조회한다.
	 */
	@Override
	public List<Advertising> getAdvertisingList(AdvertisingSearch advertisingSearch) {
		List<Advertising> list = advertisingMapper.selectAdvertisingList(advertisingSearch);
		return list;
	}
	
	/**
	 * 광고자료 목록 토탈을 조회한다.
	 */
	@Override
	public int getAdvertisingListTotal(AdvertisingSearch advertisingSearch) {
		int total = advertisingMapper.selectAdvertisingListTotal(advertisingSearch);
		return total;
	}

	/**
	 * 광고자료를 등록한다.
	 * @throws IOException 
	 * @throws AsaproException 
	 */
	@Override
	public Map<String, FileInfoUploadResult> insertAdvertising(Advertising advertisingForm) throws AsaproException, IOException {
		// archive 기본데이터 등록
		Map<String, FileInfoUploadResult> fileInfoUploadResultMap = archiveService.insertArchive(advertisingForm);
		
		FileInfoUploadResult videoFileInfoUploadResult = new FileInfoUploadResult();
		FileInfoUploadResult audiofileInfoUploadResult = new FileInfoUploadResult();
		ArchiveCategory archiveCategory = advertisingForm.getArchiveCategory();
		
		//첨부가능한 확장자
		String uploadWhiteList = "";

		//동영상 처리
		if( advertisingForm.getVideoFile() != null && advertisingForm.getVideoFile().getSize() > 0 ){
			
			uploadWhiteList = Constant.UPLOAD_VIDEO_WHITE_LIST; 
			//동영상파일의 정보와 파일을 저장
			FileInfo fileInfo = archiveService.saveMultipartFile(advertisingForm.getVideoFile(), "", archiveCategory, archiveCategory.getCatUploadSizeMax(), videoFileInfoUploadResult, advertisingForm, uploadWhiteList, "video");
			advertisingForm.setVideoFileInfo(fileInfo);
		}
		//오디오 처리
		if( advertisingForm.getRadioFile() != null && advertisingForm.getRadioFile().getSize() > 0 ){
			
			uploadWhiteList = Constant.UPLOAD_AUDIO_WHITE_LIST; 
			//오디오파일의 정보와 파일을 저장
			FileInfo fileInfo = archiveService.saveMultipartFile(advertisingForm.getRadioFile(), "", archiveCategory, archiveCategory.getCatUploadSizeMax(), audiofileInfoUploadResult, advertisingForm, uploadWhiteList, "radeo");
			advertisingForm.setRadioFileInfo(fileInfo);
		}
		
		// 커스텀 아카이브 데이터 advertising 등록
		int inserted = advertisingMapper.insertAdvertising(advertisingForm);
		
		fileInfoUploadResultMap.put("videoFileInfoUploadResult", videoFileInfoUploadResult);
		fileInfoUploadResultMap.put("audiofileInfoUploadResult", audiofileInfoUploadResult);
		return fileInfoUploadResultMap;
	}

	/**
	 * 광고자료를 조회한다.
	 */
	@Override
	public Advertising getAdvertising(Advertising advertisingForm) {
		Advertising advertising = advertisingMapper.selectAdvertising(advertisingForm);
		return advertising;
	}

	/**
	 * 광고자료를 수정한다.
	 * @throws IOException 
	 * @throws AsaproException 
	 */
	@Override
	public Map<String, FileInfoUploadResult> updateAdvertising(Advertising advertisingForm) throws AsaproException, IOException {
		// archive 기본데이터 수정
		Map<String, FileInfoUploadResult> fileInfoUploadResultMap = archiveService.updateArchive(advertisingForm);
		
		FileInfoUploadResult videoFileInfoUploadResult = new FileInfoUploadResult();
		FileInfoUploadResult audiofileInfoUploadResult = new FileInfoUploadResult();
		ArchiveCategory archiveCategory = advertisingForm.getArchiveCategory();
		
		//첨부가능한 확장자
		String uploadWhiteList = "";

		//동영상 처리
		//첨부파일을 삭제 하려고 할경우
		if(advertisingForm.getDeleteVideo() != null && advertisingForm.getDeleteVideo() > 0 ){
			advertisingForm.getVideoFileInfo().setFileId(advertisingForm.getDeleteVideo());
			advertisingForm.getVideoFileInfo().setSitePrefix(advertisingForm.getSitePrefix());
			fileInfoService.deleteFileInfo(advertisingForm.getVideoFileInfo());
		}
		//첨부파일을 변경했을경우
		if( advertisingForm.getVideoFile() != null && advertisingForm.getVideoFile().getSize() > 0 ){
			//기존 동영상파일 삭제
			if(advertisingForm.getVideoFileInfo() != null && advertisingForm.getVideoFileInfo().getFileId() > 0){
				advertisingForm.getVideoFileInfo().setSitePrefix(advertisingForm.getSitePrefix());
				fileInfoService.deleteFileInfo(advertisingForm.getVideoFileInfo());
			}
			
			uploadWhiteList = Constant.UPLOAD_VIDEO_WHITE_LIST; 
			//동영상파일의 정보와 파일을 저장
			FileInfo fileInfo = archiveService.saveMultipartFile(advertisingForm.getVideoFile(), "", archiveCategory, archiveCategory.getCatUploadSizeMax(), videoFileInfoUploadResult, advertisingForm, uploadWhiteList, "video");
			advertisingForm.setVideoFileInfo(fileInfo);
		}
		//오디오 처리
		//첨부파일을 삭제 하려고 할경우
		if(advertisingForm.getDeleteRadio() != null && advertisingForm.getDeleteRadio() > 0 ){
			advertisingForm.getRadioFileInfo().setFileId(advertisingForm.getDeleteRadio());
			advertisingForm.getRadioFileInfo().setSitePrefix(advertisingForm.getSitePrefix());
			fileInfoService.deleteFileInfo(advertisingForm.getRadioFileInfo());
		}
		//첨부파일을 변경했을경우
		if( advertisingForm.getRadioFile() != null && advertisingForm.getRadioFile().getSize() > 0 ){
			//기존 오디오파일 삭제
			if(advertisingForm.getRadioFileInfo() != null && advertisingForm.getRadioFileInfo().getFileId() > 0){
				advertisingForm.getRadioFileInfo().setSitePrefix(advertisingForm.getSitePrefix());
				fileInfoService.deleteFileInfo(advertisingForm.getRadioFileInfo());
			}
			
			uploadWhiteList = Constant.UPLOAD_AUDIO_WHITE_LIST; 
			//오디오파일의 정보와 파일을 저장
			FileInfo fileInfo = archiveService.saveMultipartFile(advertisingForm.getRadioFile(), "", archiveCategory, archiveCategory.getCatUploadSizeMax(), audiofileInfoUploadResult, advertisingForm, uploadWhiteList, "radio");
			advertisingForm.setRadioFileInfo(fileInfo);
		}
		
		// 커스텀 아카이브 데이터 advertising 수정
		int updated = advertisingMapper.updateAdvertising(advertisingForm);
		
		fileInfoUploadResultMap.put("videoFileInfoUploadResult", videoFileInfoUploadResult);
		fileInfoUploadResultMap.put("audiofileInfoUploadResult", audiofileInfoUploadResult);
		return fileInfoUploadResultMap;
	}
	
	/**
	 * 광고자료를 삭제한다.
	 */
	@Override
	public int deleteAdvertising(List<Advertising> AdvertisingList) {
		int deleted = 0;
		for (Advertising advertising : AdvertisingList) {
			deleted += this.deleteAdvertising(advertising);
		}
		return deleted;
	}

	/**
	 * 광고자료를 삭제한다.
	 */
	@Override
	public int deleteAdvertising(Advertising advertising) {
		Advertising fromDB = advertisingMapper.selectAdvertising(advertising);
		fromDB.setSitePrefix(advertising.getSitePrefix());
		
		int deleted = 0;
		deleted = archiveMapper.deleteArchive(advertising);
		
		if(deleted > 0){
			
			//첨부파일 삭제
			for( FileInfo fileInfo : fromDB.getArcFileInfos() ){
				//파일 삭제
				fileInfo.setSitePrefix(advertising.getSitePrefix());
				fileInfoService.deleteFileInfo(fileInfo);
			}
			//대표이미지 삭제
			if(fromDB.getThumb() != null && fromDB.getThumb().getFileId() > 0){
				FileInfo fileInfoImage = fromDB.getThumb();
				fileInfoImage.setSitePrefix(advertising.getSitePrefix());
				fileInfoService.deleteFileInfo(fileInfoImage);
			}
			//동영상파일 삭제
			if(fromDB.getVideoFileInfo() != null && fromDB.getVideoFileInfo().getFileId() > 0){
				FileInfo fileInfoVideo = fromDB.getVideoFileInfo();
				fileInfoVideo.setSitePrefix(advertising.getSitePrefix());
				fileInfoService.deleteFileInfo(fileInfoVideo);
			}
			//오디오파일 삭제
			if(fromDB.getRadioFileInfo() != null && fromDB.getRadioFileInfo().getFileId() > 0){
				FileInfo fileInfoRadio = fromDB.getRadioFileInfo();
				fileInfoRadio.setSitePrefix(advertising.getSitePrefix());
				fileInfoService.deleteFileInfo(fileInfoRadio);
			}
			
		}
		return deleted;
	}

	/**
	 * 광고자료 연도 목록을 반환한다.
	 */
	@Override
	public List<String> getYearList(AdvertisingSearch advertisingSearch) {
		List<String> yearList = advertisingMapper.selectYearList(advertisingSearch);
		return yearList;
	}

}
