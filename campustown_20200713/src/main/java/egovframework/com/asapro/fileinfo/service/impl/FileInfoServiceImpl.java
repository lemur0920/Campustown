/**
 * 
 */
package egovframework.com.asapro.fileinfo.service.impl;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.imageio.IIOException;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

//import com.sun.image.codec.jpeg.JPEGCodec;

//import egovframework.com.asapro.archive.service.ArchiveMapper;
import egovframework.com.asapro.config.service.Config;
import egovframework.com.asapro.config.service.ConfigService;
import egovframework.com.asapro.fileinfo.service.FileInfo;
import egovframework.com.asapro.fileinfo.service.FileInfoMapper;
import egovframework.com.asapro.fileinfo.service.FileInfoSearch;
import egovframework.com.asapro.fileinfo.service.FileInfoService;
import egovframework.com.asapro.fileinfo.service.FileInfoUploadResult;
import egovframework.com.asapro.fileinfo.service.MakeThumbnailResult;
import egovframework.com.asapro.site.service.Site;
//import egovframework.com.asapro.media.service.Media;
import egovframework.com.asapro.support.Constant;
import egovframework.com.asapro.support.MimeTypeCheck;
import egovframework.com.asapro.support.exception.AsaproException;
//import egovframework.com.asapro.support.jdbc.AsaproDBUtils;
import egovframework.com.asapro.support.util.AsaproUtils;
import egovframework.com.cmm.service.EgovProperties;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

/**
 * 파일 정보 서비스 구현
 * @author yckim
 * @since 2018. 6. 12.
 *
 */
//@SuppressWarnings("restriction")
@Service
public class FileInfoServiceImpl extends EgovAbstractServiceImpl implements FileInfoService{

	private static final Logger LOGGER = LoggerFactory.getLogger(FileInfoServiceImpl.class);
	
	@Autowired
	private FileInfoMapper fileInfoMapper;
	
	@Autowired
	private ConfigService configService;
	
	@Autowired
	private HttpServletRequest request;
	
//	@Autowired
//	private ArchiveMapper archiveMapper;
	
	/**
	 * 파일정보를 저장한다. 
	 */
	@Override
	public FileInfo saveFile(MultipartFile multipartFile, String savePathRoot, FileInfo fileInfo, long uploadSizeLimit, boolean makeThumb, int thumbWidth, int thumbHeight, boolean thumbCrop, String uploadWhiteList, boolean printWatermark) throws IOException, AsaproException {
		
		Config siteConfig = configService.getConfig("site");
		Config watermarkConfig = configService.getConfig("watermark");
		
		//설정도 워터마크 true 이고 메소드 파라메터도 true 이어야 워터마크 삽입한다.
		//printWatermark = "true".equals(watermarkConfig.getOption("watermark_use")) && printWatermark;
		if( StringUtils.isBlank(fileInfo.getFileModule()) ){
			throw new IllegalArgumentException("[ASAPRO] fileModule missing..");
		}
		
		if( multipartFile.isEmpty() ){
			//throw new IllegalArgumentException("[ASAPRO] uploaded file is empty..");
			LOGGER.info("[ASAPRO] FileInfoServiceImpl uploaded file is empty..");
			//return null;
			fileInfo.setFileUploadSuccess(false);
			fileInfo.setFileUploadErrorCode(FileInfoUploadResult.ERROR_NO_FILE);
			return fileInfo;
		}
		
		fileInfo.setFileOriginalName(multipartFile.getOriginalFilename());
		
		//파일에 세팅된 regdate 기준으로 우선 생성하도록 개선
		Date today = null;
		if( fileInfo.getFileRegDate() != null ){
			today = fileInfo.getFileRegDate();
		} else {
			today = new Date();
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(today);
		
		String extension = FilenameUtils.getExtension(fileInfo.getFileOriginalName());
		extension = StringUtils.defaultString(extension, "").toLowerCase();
		
		//미디어 타입 체크
		MimeTypeCheck mtc = MimeTypeCheck.getMimeType( extension );
		
		//--------------------------------------------------------------------------
		// START : 업로드 확장자, 사이즈, 대체텍스트 체크
		//--------------------------------------------------------------------------
		//업로드 확장자 검사 시작 - 여러파일 업로드시 여기서 걸리면 이 파일만 빼고 업로드가 처리된다.
		
		if(StringUtils.isBlank(uploadWhiteList)){
//			String uploadWhiteList = "jpg,jpeg,png,gif,bmp,ppt,pptx,xls,xlsx,doc,docx,pdf,txt,hwp,swf,wmv,mp4,flv,ogv,ogg,mp3,m4a,wav,wma,zip,rar,7zip,avi"; 
			uploadWhiteList = siteConfig.getOption("upload_white_list"); 
		}

		boolean isUploadableFile = false;
		for( String ext : uploadWhiteList.split(",") ){
			if( extension.equals(ext.trim().toLowerCase()) ){
				isUploadableFile = true;
				break;
			}
		}
		//업로드 불가 파일인 경우 저장하지 않고 에러정보 담아서 반환
		if( !isUploadableFile ){
			fileInfo.setFileUploadSuccess(false);
			fileInfo.setFileUploadErrorCode(FileInfoUploadResult.ERROR_NOT_UPLOADABLE_FILE);
			return fileInfo;
		}
		//업로드 확장자 검사 끝
		//업로드 사이즈 검사 시작
		if( uploadSizeLimit != -1L ){
			if( multipartFile.getSize() > uploadSizeLimit ){
				fileInfo.setFileUploadSuccess(false);
				fileInfo.setFileUploadErrorCode(FileInfoUploadResult.ERROR_UPLOAD_SIZE_OVER_FILE);
				return fileInfo;
			}
		}
		//업로드 사이즈 검사 끝
		//대체텍스트 검사 시작
		if( mtc.getMediaType().equals(MimeTypeCheck.IMAGE) ){
			if( StringUtils.isBlank(fileInfo.getFileAltText()) ){
				fileInfo.setFileUploadSuccess(false);
				fileInfo.setFileUploadErrorCode(FileInfoUploadResult.ERROR_ALT_TEXT_MISSING_FILE);
				return fileInfo;
			}
		} else {
			fileInfo.setFileAltText("");
		}
		//대체텍스트 검사 끝
		//--------------------------------------------------------------------------
		// END : 업로드 확장자, 사이즈, 대체텍스트 체크
		//--------------------------------------------------------------------------
		
		//저장경로 => Constant.UPLOAD_PATH + /{모듈pk ex)bcId}[/{모듈pk2차 ex)baId}]/년도/월/
		StringBuilder uploadDir = new StringBuilder(100);
		uploadDir.append( Constant.UPLOAD_PATH );
		uploadDir.append( "/" );
		uploadDir.append( fileInfo.getSiteId() );
		uploadDir.append( "/" );
		uploadDir.append( fileInfo.getFileModule() );
		uploadDir.append( "/" );
		if(StringUtils.isNotBlank(fileInfo.getFileModuleId())){
			uploadDir.append( fileInfo.getFileModuleId() );
			uploadDir.append( "/" );
		}
		if(StringUtils.isNotBlank(fileInfo.getFileModuleSub())){
			uploadDir.append( fileInfo.getFileModuleSub() );
			uploadDir.append( "/" );
		}
		uploadDir.append( String.valueOf(cal.get(Calendar.YEAR)) );
		uploadDir.append( "/" );
		uploadDir.append( String.format("%02d", (cal.get(Calendar.MONTH) + 1)) );
		uploadDir.append( "/" );
		
		String webRoot = FilenameUtils.getFullPathNoEndSeparator(savePathRoot).replaceAll("\\\\", "/");
		
		String uploadFolderPathFull = ""; 
		
		
		//EgovProperties.getProperty("Globals.fileStorePath")
		//Globals.fileStorePath 의 값이 있을경우 지정한 경로에 저장되게 하고 값이없을경우 디폴트로 설정된 컨텍스트내 폴더에 저장된다.
		String fileStorePath = EgovProperties.getProperty("Globals.fileStorePath");
		if(StringUtils.isNotBlank(fileStorePath)){
			uploadFolderPathFull = fileStorePath + uploadDir.toString();
		}else{
			 uploadFolderPathFull = webRoot + uploadDir.toString();
		}
		
		LOGGER.info("[ASAPRO] File upload location : uploadFolderPathFull = {}", uploadFolderPathFull);
		String saveName =  DigestUtils.md5Hex(System.currentTimeMillis() + multipartFile.getOriginalFilename() + multipartFile.getSize()) + "." + extension;
		
		//폴더 없으면 부모폴더까지 생성
		File parentPath = new File(uploadFolderPathFull);
		FileUtils.forceMkdir(parentPath);
		parentPath.setReadable(true, false);
		parentPath.setWritable(true, false);
		parentPath.setExecutable(true, false);
		
		File file = new File(uploadFolderPathFull + saveName);
		
		//파일 저장
		FileCopyUtils.copy(multipartFile.getInputStream(), new FileOutputStream(file, false));
		file.setReadable(true, false);
		file.setWritable(true, false);
		file.setExecutable(true, false);
		LOGGER.info("[ASAPRO] File saved...");
		
		//For image dimensions..
		//BufferedImage originalImage = null;
		
		fileInfo.setFileUUID( UUID.randomUUID().toString().replace("-", "") );
		
		//썸네일 생성하라고 플래그가 넘어와도 실제로 이미지일때만 생성한다.
		if( mtc.getMediaType().equals(MimeTypeCheck.IMAGE) ){
			
			fileInfo.setFileServletUrl(AsaproUtils.getAppPath((Site)request.getAttribute("currentSite")) + "/file/image/uu/" + fileInfo.getFileUUID() );
			
			//썸네일 생성할때 워터마크 들어가면 안될거 같아서 썸네일을 일단 먼저 생성함
			if(makeThumb){
				//BufferedImage originalImage = ImageIO.read(file);
				//makes default size
				MakeThumbnailResult makeThumbnailResult = makeThumnail(file, thumbWidth, thumbHeight, thumbCrop, "_thumb");
				fileInfo.setFileOriImageWidth(makeThumbnailResult.getOriImageWidth());
				fileInfo.setFileOriImageHeight(makeThumbnailResult.getOriImageHeight());
				fileInfo.setFileThumbnailPath( FilenameUtils.getFullPath(uploadDir.toString() + saveName) + FilenameUtils.getBaseName(uploadDir.toString() + saveName) + "_thumb." + extension );
				
				fileInfo.setFileServletThumbnailUrl(AsaproUtils.getAppPath((Site)request.getAttribute("currentSite")) + "/file/thumbnail/uu/" + fileInfo.getFileUUID() );
				
				//thumbnail
				LOGGER.info("[ASAPRO] Thumnails generated...");
			}
			
			//워터마크 삽입 - ★워터마크 삽입되면 위에서 세이브된 이미지가 워터마크삽입된 이미지로 교체됨
			if( printWatermark ){
				//워터마크 삽입이 일단 미디어 라이브러리 전반에 걸쳐서 적용됨.
				//배너나 팝업존 처럼 적용되면 안되는 경우는 별도 처리하거나 다른 메소드 이용해야함.
				File watermarkImage = new File(webRoot + watermarkConfig.getOption("watermark_image"));
				this.printWatermark(multipartFile.getInputStream(), file, watermarkImage, watermarkConfig.getOption("watermark_position"));
			}
			
		}
		
		//fileInfo.setFileID(fileId);
		//fileInfo.setFileOriginalName(multipartFile.getOriginalFilename());
		fileInfo.setFileMediaType(mtc.getMediaType());
		fileInfo.setFileMimeType(mtc.getMimeType());
		//fileInfo.setFilePath(file.getParent().replaceAll("\\\\", "/").replace(FilenameUtils.getFullPathNoEndSeparator(savePathRoot).replaceAll("\\\\", "/"), "") + "/" + saveName);
		fileInfo.setFilePath(uploadDir.toString().replaceAll("\\\\", "/") + saveName);
		fileInfo.setFileExt(mtc.getExtension());
		fileInfo.setFileSize(file.length());
		fileInfo.setFileRegDate(today);
		fileInfo.setFileDownloadCount(0);
		
		//If file is image file
		//if(originalImage != null){
			//commonFile.setFileDimensions(originalImage.getWidth() + " × " + originalImage.getHeight());
		//}
		
		
		
		
		fileInfoMapper.insertFileInfo(fileInfo);
		fileInfo.setFileUploadSuccess(true);
		return fileInfo;
	}
	
	/**
	 * 파일 저장
	 */
	@Override
	public FileInfo saveFile(MultipartFile multipartFile, String savePathRoot, FileInfo fileInfo, long uploadSizeLimit, boolean makeThumb, int thumbWidth, int thumbHeight, boolean thumbCrop, String uploadWhiteList) throws IOException, AsaproException {
		return this.saveFile(multipartFile, savePathRoot, fileInfo, uploadSizeLimit, makeThumb, thumbWidth, thumbHeight, thumbCrop, uploadWhiteList, true) ;
			
	}
	
	
	/**
	 * 파일 저장
	 */
	@Override
	public FileInfo saveFile(MultipartFile multipartFile, String savePathRoot, FileInfo fileInfo, long uploadSizeLimit, boolean makeThumb, int thumbWidth, int thumbHeight, boolean thumbCrop) throws IOException, AsaproException {
		//워터마크는 기본적으로 생성 안함
		return this.saveFile(multipartFile, savePathRoot, fileInfo, uploadSizeLimit, makeThumb, thumbWidth, thumbHeight, thumbCrop, "");
		
	}

	/**
	 * 이미지 썸네일을 생성하면서 파일 정보를 저장한다. (관리자 설정의 썸네일 가로세로 기본값을 기준으로 썸네일 생성함) 
	 */
	@Override
	public FileInfo saveFileMakeThumbWithDefaultOption(MultipartFile multipartFile, String savePathRoot, FileInfo fileInfo, long uploadSizeLimit) throws IOException, AsaproException {
		//설정정보에서 가지고 오도록 고쳐야 함 일단은...이렇게
		Config config = configService.getConfig("site");
		int thumbWidth = Integer.parseInt(config.getOption("thumb_default_width"));
		int thumbHeight = Integer.parseInt(config.getOption("thumb_default_height"));
		boolean thumbCrop = false;
		return this.saveFile(multipartFile, savePathRoot, fileInfo, uploadSizeLimit, true, thumbWidth, thumbHeight, thumbCrop);
	}

	/**
	 * 썸네일 크기 지정해서 생성
	 */
	@Override
	public FileInfo saveFileMakeThumbWithCustomOption(MultipartFile multipartFile, String savePathRoot, FileInfo fileInfo, long uploadSizeLimit, int thumbWidth, int thumbHeight, boolean thumbCrop) throws IOException, AsaproException {
		return this.saveFile(multipartFile, savePathRoot, fileInfo, uploadSizeLimit, true, thumbWidth, thumbHeight, thumbCrop);
	}
	
	/**
	 * 썸네일 크기 지정해서 생성 - 첨부가능확장자 목록 추가
	 */
	@Override
	public FileInfo saveFileMakeThumbWithCustomOption(MultipartFile multipartFile, String savePathRoot, FileInfo fileInfo, long uploadSizeLimit, int thumbWidth, int thumbHeight, boolean thumbCrop, String uploadWhiteList) throws IOException, AsaproException {
		return this.saveFile(multipartFile, savePathRoot, fileInfo, uploadSizeLimit, true, thumbWidth, thumbHeight, thumbCrop, uploadWhiteList);
	}
	
	/**
	 * 썸네일 크기 지정해서 생성 - 첨부가능확장자 목록 추가 - 워터마크 옵션 추가
	 */
	@Override
	public FileInfo saveFileMakeThumbWithCustomOption(MultipartFile multipartFile, String savePathRoot, FileInfo fileInfo, long uploadSizeLimit, int thumbWidth, int thumbHeight, boolean thumbCrop, String uploadWhiteList, boolean printWatermark) throws IOException, AsaproException {
		return this.saveFile(multipartFile, savePathRoot, fileInfo, uploadSizeLimit, true, thumbWidth, thumbHeight, thumbCrop, uploadWhiteList, printWatermark);
	}

	/**
	 * 썸네일 안만들고 업로드된 파일 그대로 저장, 파일크기 제한 없음
	 */
	@Override
	public FileInfo saveFileNoThumb(MultipartFile multipartFile, String savePathRoot, FileInfo fileInfo) throws IOException, AsaproException {
		return this.saveFile(multipartFile, savePathRoot, fileInfo, -1L, false, 0, 0, false);
	}
	
	/**
	 * 썸네일 안만들고 바로 저장, 파일 크기 제한 가능
	 */
	@Override
	public FileInfo saveFileNoThumbWithUploadSizeLimit(MultipartFile multipartFile, String savePathRoot, FileInfo fileInfo, long uploadSizeLimit) throws IOException, AsaproException {
		return this.saveFile(multipartFile, savePathRoot, fileInfo, uploadSizeLimit, false, 0, 0, false);
	}
	
	/**
	 * 썸네일만 만들고 오리지널 파일 제거
	 */
	@Override
	public MakeThumbnailResult makeThumnailOverrideOriginal(File original, int destWidth, int destHeight, boolean crop) throws IOException, AsaproException {
		return this.makeThumnail(original, destWidth, destHeight, crop, "", true);
	}
	
	/**
	 * 썸네일 생성
	 */
	@Override
	public MakeThumbnailResult makeThumnail(File original, int destWidth, int destHeight, boolean crop, String suffixParam, boolean deleteOriginal) throws IOException, AsaproException {
		String suffix = suffixParam;
		String extension = FilenameUtils.getExtension(original.getName()).toLowerCase();
		BufferedImage originalImage = null;
		try {
			originalImage = ImageIO.read(original);
		} catch (IllegalArgumentException e) {
			if("Numbers of source Raster bands and source color space components do not match".equals(e.getMessage()) 
					&& ( "jpg".equals(extension) || "jpeg".equals(extension) )){
				//BufferedImage bufferedImage = ImageIO.read(original); //이미지 생성시 색상형식에 따라 읽어들이지 못하는 버그 
				//jdk  1.7.4 이후 버그 수정되었다고 함. 1.6. 을 사용시에는 아래 주석을 풀고 사용
				//originalImage = JPEGCodec.createJPEGDecoder(new FileInputStream(original)).decodeAsBufferedImage();
				throw new AsaproException("이미지 파일의 상태에 오류가 있는것 같습니다. 스캔한 파일일 경우 이미지저장 프로그램으로 가공 후 업로드 해보시기 바랍니다.");
			} else {
				throw new AsaproException("이미지 파일의 상태에 오류가 있는것 같습니다. 스캔한 파일일 경우 이미지저장 프로그램으로 가공 후 업로드 해보시기 바랍니다.");
			}
		} catch (IIOException e) {
			if( "Unsupported Image Type".equals(e.getMessage()) 
					&& ( "jpg".equals(extension) || "jpeg".equals(extension) )){
				//jdk  1.7.4 이후 버그 수정되었다고 함. 1.6. 을 사용시에는 아래 주석을 풀고 사용
				//originalImage = JPEGCodec.createJPEGDecoder(new FileInputStream(original)).decodeAsBufferedImage();
				//throw e;
			} else {
				throw e;
			}
		}
		
		//이미지 아닌데 이미지 확장자 달고 들어오면 여기서 걸러줘야 함
		if( originalImage == null ){
			return null;
		}
				
		int originalImageWidth = originalImage.getWidth();
		int originalImageHeight = originalImage.getHeight();
		if(StringUtils.isBlank(suffix)){
			suffix = "_thumb";
		}
		if(deleteOriginal){
			suffix = "";
		}
		String thumbnailFileName = FilenameUtils.getBaseName(original.getName());
		File file = new File( FilenameUtils.getFullPath( original.getPath()) + thumbnailFileName + suffix + "." + extension);
		if(crop){
			//image crop
			if(originalImageWidth <= destWidth && originalImageHeight > destHeight){
				if( "gif".equals(extension) ){
					Thumbnails.of(originalImage).crop(Positions.CENTER).size(originalImageWidth, destHeight).imageType(BufferedImage.TYPE_INT_ARGB).toFile(file);
				} else {
					Thumbnails.of(originalImage).crop(Positions.CENTER).size(originalImageWidth, destHeight).toFile(file);
				}
			} else if(originalImageHeight <= destHeight && originalImageWidth > destWidth){
				if( "gif".equals(extension) ){
					Thumbnails.of(originalImage).crop(Positions.CENTER).size(destWidth, originalImageHeight).imageType(BufferedImage.TYPE_INT_ARGB).toFile(file);
				} else {
					Thumbnails.of(originalImage).crop(Positions.CENTER).size(destWidth, originalImageHeight).toFile(file);
				}
			} else if(originalImageHeight <= destHeight && originalImageWidth <= destWidth){
				if( "gif".equals(extension) ){
					Thumbnails.of(originalImage).crop(Positions.CENTER).size(originalImageWidth, originalImageHeight).imageType(BufferedImage.TYPE_INT_ARGB).toFile(file);
				} else {
					Thumbnails.of(originalImage).crop(Positions.CENTER).size(originalImageWidth, originalImageHeight).toFile(file);
				}
			} else {
				if( "gif".equals(extension) ){
					Thumbnails.of(originalImage).crop(Positions.CENTER).size(destWidth, destHeight).imageType(BufferedImage.TYPE_INT_ARGB).toFile(file);
				} else {
					Thumbnails.of(originalImage).crop(Positions.CENTER).size(destWidth, destHeight).toFile(file);
				}
			}
		} else {
			//keep ratio of original image
			if( "gif".equals(extension) ){
				Thumbnails.of(originalImage).size(destWidth, destHeight).keepAspectRatio(true).imageType(BufferedImage.TYPE_INT_ARGB).outputQuality(1.0f).toFile(file);
				//Thumbnails.of(originalImage).addFilter(new Canvas(destWidth, destHeight, Positions.CENTER, Color.WHITE)).size(destWidth, destHeight).keepAspectRatio(true).imageType(BufferedImage.TYPE_INT_ARGB).outputQuality(1.0f).toFile(file);
			} else {
				Thumbnails.of(originalImage).size(destWidth, destHeight).keepAspectRatio(true).imageType(BufferedImage.TYPE_INT_ARGB).outputQuality(1.0f).toFile(file);
			}
		}
		file.setWritable(true, false);
		file.setReadable(true, false);
		file.setExecutable(true, false);
		
		MakeThumbnailResult makeThumbnailResult = new MakeThumbnailResult();
		makeThumbnailResult.setResultFile(file);
		makeThumbnailResult.setOriImageWidth(originalImageWidth);
		makeThumbnailResult.setOriImageHeight(originalImageHeight);
		
		return makeThumbnailResult;
	}
	
	/**
	 * 썸네일 생성
	 */
	@Override
	public MakeThumbnailResult makeThumnail(File original, int destWidth, int destHeight, boolean crop, String suffix) throws IOException, AsaproException {
		return this.makeThumnail(original, destWidth, destHeight, crop, suffix, false);
	}
	
	/**
	 * 물리 파일 삭제
	 */
	@Override
	public boolean deleteFile(File file) {
		String extension = FilenameUtils.getExtension(file.getName()).toLowerCase();
		
		if (!file.exists()){
			LOGGER.info("[ASAPRO] Target file [{}] is not exists. Can not delete file...", file.getAbsolutePath());
			return false;
			//throw new FileNotFoundException("Target file is not exists. : " + file.getAbsolutePath());
		}
		if (!file.isFile()){
			LOGGER.info("[ASAPRO] Target [{}] is not a file. Check it please...", file.getAbsolutePath());
			return false;
			//throw new FileNotFoundException("Target is not file. : " + file.getAbsolutePath());
		}
		//if (!file.canWrite()){
			//LOGGER.info("[ASAPRO] Change ownership of [{}] to 707 or else.", file.getAbsolutePath());
			//throw new FileNotFoundException("Target file(or directory) is not manageable for this user. : " + file.getAbsolutePath());
		//}
		
		//if target file is an image file, thumbnails of the target file will be deleted too.
		MimeTypeCheck mtc = MimeTypeCheck.getMimeType(FilenameUtils.getExtension(file.getName())); 
		if( mtc.getMediaType().equals(MimeTypeCheck.IMAGE) ){
			//delete all thumbnails without firing any exceptions.
			FileUtils.deleteQuietly(new File(FilenameUtils.getFullPath(file.getPath()) + FilenameUtils.getBaseName(file.getPath()) + "_thumb." + extension));
			LOGGER.info("[ASAPRO] Deletes thumbnails of File : {}", file.getName());
		}
		LOGGER.info("[ASAPRO] Deletes File : {}", file.getName());
		
		//동영상, 오디오 이면 인코딩관련 파일 삭제
		if( MimeTypeCheck.VIDEO.equals(mtc.getMediaType()) ){
			FileUtils.deleteQuietly(new File(FilenameUtils.getFullPath(file.getPath()) + FilenameUtils.getBaseName(file.getPath()) + ".metadata"));
			FileUtils.deleteQuietly(new File(FilenameUtils.getFullPath(file.getPath()) + FilenameUtils.getBaseName(file.getPath()) + ".progress"));
			FileUtils.deleteQuietly(new File(FilenameUtils.getFullPath(file.getPath()) + FilenameUtils.getBaseName(file.getPath()) + ".mp4"));
		} else if( MimeTypeCheck.AUDIO.equals(mtc.getMediaType()) ){
			FileUtils.deleteQuietly(new File(FilenameUtils.getFullPath(file.getPath()) + FilenameUtils.getBaseName(file.getPath()) + ".metadata"));
			FileUtils.deleteQuietly(new File(FilenameUtils.getFullPath(file.getPath()) + FilenameUtils.getBaseName(file.getPath()) + ".progress"));
			FileUtils.deleteQuietly(new File(FilenameUtils.getFullPath(file.getPath()) + FilenameUtils.getBaseName(file.getPath()) + ".mp3"));
		}
		
		return FileUtils.deleteQuietly(file);
	}

	/**
	 * 물리 파일 삭제
	 */
	@Override
	public boolean deleteFile(String filePath) {
		File file = new File(filePath);
		return deleteFile(file);
	}
	
	/**
	 * 실제파일 삭제 + 파일정보를 삭제한다.
	 */
	@Override
	public int deleteFileInfo(FileInfo fileIdSearch) {
		int result = 0;
		if( fileIdSearch == null || fileIdSearch.getFileId() == 0 ){
			return result;
		}
		FileInfo fileInfo = fileInfoMapper.selectFileInfo(fileIdSearch);
		if( fileInfo == null ){
			return result;
		}
		
		//EgovProperties.getProperty("Globals.fileStorePath")
		//Globals.fileStorePath 의 값이 있을경우 지정한 경로에 저장되게 하고 값이없을경우 디폴트로 설정된 컨텍스트내 폴더에 저장된다.
		String webRoot = EgovProperties.getProperty("Globals.fileStorePath");
		if(StringUtils.isBlank(webRoot)){
			webRoot = AsaproUtils.getWebRoot(request);
		}
		
		File file = new File(webRoot + fileInfo.getFilePath());
		
		//물리파일 삭제
		//Delete fileInfo whether physical file exists or not. If physical file is not exits, just log. 
		if( !this.deleteFile(file) ){
			LOGGER.info("[ASAPRO] FileInfoServiceImpl something wrong with physical file, maybe already deleted or else... ??");
		}
		//파일정보 삭제
//		archiveMapper.updateArchiveThumbFileIdToZero(fileIdSearch);
//		archiveMapper.deleteArchiveMediaRelationByFileId(fileIdSearch);
		result = fileInfoMapper.deleteFileInfo(fileIdSearch);
		return result;
	}
	
	
	/**
	 * 실제파일 삭제 + 파일정보를 삭제한다.
	 * @param fileInfoList
	 * @return
	 */
	@Override
	public int deleteFileInfo(List<FileInfo> fileInfoList){
		int deleted = 0;
		for( FileInfo fileInfo : fileInfoList ){
			deleted += this.deleteFileInfo(fileInfo);
		}
		return deleted;
	}

	/**
	 * 파일정보 조회
	 */
	@Override
	public FileInfo getFileInfo(FileInfo fileIdSearch) {
		return fileInfoMapper.selectFileInfo(fileIdSearch);
	}

	/**
	 * 파일목록 조회
	 */
	@Override
	public List<FileInfo> getFileInfoList(FileInfoSearch fileInfoSearch) {
		List<FileInfo> list = fileInfoMapper.selectFileInfoList(fileInfoSearch);
		//첨부된곳 정보(아카이브만)
		for( FileInfo fileInfo : list ){
			fileInfo.setSitePrefix(fileInfoSearch.getSitePrefix());
			//첨부된 아카이브
//			fileInfo.setFileAttachedArchiveSet(archiveMapper.selectAttachedArchiveSet(fileInfo));
		}
		return list;
	}
	
	/**
	 * 파일목록 조회
	 */
	@Override
	public List<FileInfo> getFileInfoListOnly(FileInfoSearch fileInfoSearch) {
		List<FileInfo> list = fileInfoMapper.selectFileInfoList(fileInfoSearch);
		return list;
	}

	/**
	 * 파일목록 개수
	 */
	@Override
	public int getFileInfoListTotal(FileInfoSearch fileInfoSearch) {
		return fileInfoMapper.selectFileInfoListTotal(fileInfoSearch);
	}
	
	/**
	 * 다운로드 카운트 증가
	 */
	@Override
	public int updateFileInfoDownloadCount(FileInfo fileId) {
		return fileInfoMapper.updateFileInfoDownloadCount(fileId);
	}
	
	/**
	 * 파일 저장 - 디비에 정보저장 안함, 워터마크 삽입 안함
	 */
	@Override
	public File saveFileOnly(File fromFile, File toFile, boolean deleteSameNameFile) {
		return this.saveFileOnly(fromFile, toFile, deleteSameNameFile, false);
	}

	/**
	 * 파일 저장 - 디비에 정보저장 안함, 워터마크 삽입 안함
	 */
	@Override
	public File saveFileOnly(MultipartFile multipartFile, File toFile, boolean deleteOldFile) {
		return this.saveFileOnly(multipartFile, toFile, deleteOldFile, false);
	}
	
	/**
	 * 파일 저장 - 디비에 정보저장 안함
	 */
	@Override
	public File saveFileOnly(File fromFile, File toFile, boolean deleteSameNameFile, boolean printWatermark) {
		
		Config watermarkConfig = configService.getConfig("global");
		String webRoot = AsaproUtils.getWebRoot(request);
		
		String tofilePath = toFile.getAbsolutePath(); 
		String dirPath = FilenameUtils.getFullPathNoEndSeparator(tofilePath);
		
		try {
			File directory = new File(dirPath);
			if( !directory.exists() ){
				FileUtils.forceMkdir(directory);
				directory.setReadable(true, false);
				directory.setWritable(true, false);
				directory.setExecutable(true, false);
			}
			
			if( deleteSameNameFile ){
				FileCopyUtils.copy(fromFile, toFile);
				
				if( printWatermark ){
					File watermarkImage = new File(webRoot + watermarkConfig.getOption("watermark_image"));
					this.printWatermark(fromFile, toFile, watermarkImage, null);
				}
				
			} else {
				//TODO: 같은 이름 파일 있으면 어떻게 처리할거야? 급하니까 일단은 패스
				throw new AsaproException("Not Implemented Yet. All yours!! :-)");
			}
		} catch (IOException e) {
			LOGGER.error("[IOException] Try/Catch... : "+ e.getMessage());
		}
		toFile.setReadable(true, false);
		toFile.setWritable(true, false);
		toFile.setExecutable(true, false);
		return toFile;
	}

	/**
	 * 파일 저장 - 디비에 정보저장 안함
	 */
	@Override
	public File saveFileOnly(MultipartFile multipartFile, File toFile, boolean deleteOldFile, boolean printWatermark) {
		
		String tofilePath = toFile.getAbsolutePath(); 
		String dirPath = FilenameUtils.getFullPathNoEndSeparator(tofilePath);
		
		try {
			File directory = new File(dirPath);
			if( !directory.exists() ){
				FileUtils.forceMkdir(directory);
				directory.setReadable(true, false);
				directory.setWritable(true, false);
				directory.setExecutable(true, false);
			}
			
			if( deleteOldFile ){
				FileCopyUtils.copy(multipartFile.getInputStream(), new FileOutputStream(toFile, false)); 
			} else {
				//TODO: 같은 이름 파일 있으면 어떻게 처리할거야? 급하니까 일단은 패스
				throw new AsaproException("Not Implemented Yet. All yours!! :-)");
			}
		} catch (IOException e) {
			LOGGER.error("[IOException] Try/Catch... : "+ e.getMessage());
		}
		toFile.setWritable(true, false);
		toFile.setReadable(true, false);
		toFile.setExecutable(true, false);
		return toFile;
	}
	
	/**
	 * 파일정보를 수정한다,
	 * @param fileInfo
	 * @return 수정 결과
	 */
	public int updateFileInfo(FileInfo fileInfo){
		return fileInfoMapper.updateFileInfo(fileInfo);
	}

	/**
	 * 미디어 카운트
	 */
	@Override
	public Map<String, Integer> getMediaCountMap(FileInfoSearch fileInfoSearch) {
		Map<String, Integer> result = new HashMap<String, Integer>();
		List<Map<String, Object>> list = fileInfoMapper.selectMediaCountMapList(fileInfoSearch);
		
		for( Map<String, Object> map : list ){
//			result.put((String)map.get("FILE_MEDIA_TYPE"), AsaproDBUtils.getIntFromLongOrBigDecimal(map.get("SUB_TOTAL")) );
		}
		if( result.get("IMAGE") == null ){
			result.put("IMAGE", new Integer(0));
		}
		if( result.get("DOCUMENT") == null ){
			result.put("DOCUMENT", new Integer(0));
		}
		if( result.get("VIDEO") == null ){
			result.put("VIDEO", new Integer(0));
		}
		if( result.get("AUDIO") == null ){
			result.put("AUDIO", new Integer(0));
		}
		if( result.get("ETC") == null ){
			result.put("ETC", new Integer(0));
		}
		
		return result;
	}
	
	//미디어 수정
//	@Override
//	public int updateMedia(Media mediaForm){
//		return fileInfoMapper.updateMedia( mediaForm );
//	}
	
	/**
	 * 파일의 Alt text를 수정한다.
	 */
	@Override
	public int updateFileInfoAltText(FileInfo fileInfoForm) {
		return fileInfoMapper.updateFileInfoAltText( fileInfoForm );
	}

	
	/**
	 * 파일정보 목록
	 */
	@Override
	public List<FileInfo> getFileInfoList(String sitePrefix, List<FileInfo> fileInfoList) {
		return fileInfoMapper.selectFileInfoListByFileIds(sitePrefix, fileInfoList);
	}

	/**
	 * 워터마크 삽입 - 출력스트림에 출력
	 */
	@Override
	public void printWatermark(InputStream inputStream, OutputStream outputStream, File watermarkImage, String watermarkPosition) {
		if( watermarkImage.exists() ){
			try {
				BufferedImage originalImageBI = ImageIO.read(inputStream);
				BufferedImage watermarkImageBI = ImageIO.read(watermarkImage);
				
				if( StringUtils.isBlank(watermarkPosition) ){
					watermarkPosition = "BOTTOM_RIGHT";
				}
				
				Thumbnails.of(originalImageBI)
						.size(originalImageBI.getWidth(), originalImageBI.getHeight())
						.watermark(Positions.valueOf(watermarkPosition), watermarkImageBI, 0.5f)
						.outputQuality(0.8)
						.toOutputStream(outputStream);
				
				LOGGER.info("[asapro] FileInfoServiceImpl - Image has been watermarked.");
			} catch (IOException e) {
				LOGGER.error("[IOException] Try/Catch... : "+ e.getMessage());
			}
		} else {
			LOGGER.info("[asapro] FileInfoServiceImpl - No watermark image.");
		}
	}

	/**
	 * 워터마크 삽입 - 파일에 저장, 이미지 아닌 경우에 에러 난다
	 */
	@Override
	public File printWatermark(InputStream inputStream, File targetFile, File watermarkImage, String watermarkPosition) {
		if( !watermarkImage.exists() ){
			LOGGER.info("[asapro] FileInfoServiceImpl - No watermark image.");
			return targetFile;
		}
		try {
			BufferedImage originalImageBI = ImageIO.read(inputStream);
			BufferedImage watermarkImageBI = ImageIO.read(watermarkImage);
			
			if( StringUtils.isBlank(watermarkPosition) ){
				watermarkPosition = "BOTTOM_RIGHT";
			}
			
			Thumbnails.of(originalImageBI)
					.size(originalImageBI.getWidth(), originalImageBI.getHeight())
					.watermark(Positions.valueOf(watermarkPosition), watermarkImageBI, 0.5f)
					.outputQuality(0.8)
					.toFile(targetFile);
			
			LOGGER.info("[asapro] FileInfoServiceImpl - Image has been watermarked.");
		} catch (IOException e) {
			LOGGER.error("[IOException] Try/Catch... : "+ e.getMessage());
		}
		
		return targetFile;
	}
	
	/**
	 * 워터마크 삽입
	 */
	@Override
	public File printWatermark(File originalFile, File targetFile, File watermarkImage, String watermarkPosition) {
		try {
			//이미지 파일인 경우에만 변환한다.
			MimeTypeCheck mtc = MimeTypeCheck.getMimeType( FilenameUtils.getExtension(originalFile.getName()) );
			if( mtc.getMediaType().equals(MimeTypeCheck.IMAGE) ){
				targetFile = this.printWatermark(new FileInputStream(originalFile), targetFile, watermarkImage, watermarkPosition);
			} else {
				LOGGER.info("[asapro] FileInfoServiceImpl - Can not print watermark. Target file is not image file.");
			}
		} catch (FileNotFoundException e) {
			LOGGER.error("[FileNotFoundException] Try/Catch... : "+ e.getMessage());
		}
		return targetFile;
	}
		
	/**
	 * 워터마크 삽입
	 */
	@Override
	public void printWatermark(File originalFile, OutputStream outputStream, File watermarkImage, String watermarkPosition) {
		try {
			MimeTypeCheck mtc = MimeTypeCheck.getMimeType( FilenameUtils.getExtension(originalFile.getName()) );
			if( mtc.getMediaType().equals(MimeTypeCheck.IMAGE) ){
				this.printWatermark(new FileInputStream(originalFile), outputStream, watermarkImage, watermarkPosition);
			} else {
				LOGGER.info("[asapro] FileInfoServiceImpl - Can not print watermark. Target file is not image file.");
			}
		} catch (FileNotFoundException e) {
			LOGGER.error("[FileNotFoundException] Try/Catch... : "+ e.getMessage());
		}
	}

	/**
	 * 마이그레이션시 파일 저장
	 */
	@Override
	public FileInfo saveFileForMigration(File file, String savePathRoot, FileInfo fileInfo, long uploadSizeLimit, boolean makeThumb, int thumbWidth, int thumbHeight, boolean thumbCrop, boolean printWatermark) throws IOException, AsaproException {
		
		Config siteConfig = configService.getConfig("site");
		Config watermarkConfig = configService.getConfig("watermark");
		
		//설정도 워터마크 true 이고 메소드 파라메터도 true 이어야 워터마크 삽입한다.
		//printWatermark = "true".equals(watermarkConfig.getOption("watermark_use")) && printWatermark;
		
		if( StringUtils.isBlank(fileInfo.getFileModule()) ){
			throw new IllegalArgumentException("[ASAPRO] fileModule missing..");
		}
		
		if( file == null || !file.exists() ){
			//throw new IllegalArgumentException("[ASAPRO] uploaded file is empty..");
			LOGGER.info("[ASAPRO] FileInfoServiceImpl uploaded file is empty..");
			//return null;
			fileInfo.setFileUploadSuccess(false);
			fileInfo.setFileUploadErrorCode(FileInfoUploadResult.ERROR_NO_FILE);
			return fileInfo;
		}
		
		fileInfo.setFileOriginalName(file.getName());
		
		//파일에 세팅된 regdate 기준으로 우선 생성하도록 개선
		Date today = null;
		if( fileInfo.getFileRegDate() != null ){
			today = fileInfo.getFileRegDate();
		} else {
			today = new Date();
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(today);
		
		String extension = FilenameUtils.getExtension(fileInfo.getFileOriginalName());
		extension = StringUtils.defaultString(extension, "").toLowerCase();
		
		//미디어 타입 체크
		MimeTypeCheck mtc = MimeTypeCheck.getMimeType( extension );
		
		//--------------------------------------------------------------------------
		// START : 업로드 확장자, 사이즈, 대체텍스트 체크
		//--------------------------------------------------------------------------
		//업로드 확장자 검사 시작 - 여러파일 업로드시 여기서 걸리면 이 파일만 빼고 업로드가 처리된다.
//		String uploadWhiteList = "jpg,jpeg,png,gif,bmp,ppt,pptx,xls,xlsx,doc,docx,pdf,txt,hwp,swf,wmv,mp4,flv,ogv,ogg,mp3,m4a,wav,wma,zip,rar,7zip,avi"; 
		String uploadWhiteList = siteConfig.getOption("upload_white_list"); 
		boolean isUploadableFile = false;
		for( String ext : uploadWhiteList.split(",") ){
			if( extension.equals(ext.trim().toLowerCase()) ){
				isUploadableFile = true;
				continue;
			}
		}
		//업로드 불가 파일인 경우 저장하지 않고 에러정보 담아서 반환
		if( !isUploadableFile ){
			fileInfo.setFileUploadSuccess(false);
			fileInfo.setFileUploadErrorCode(FileInfoUploadResult.ERROR_NOT_UPLOADABLE_FILE);
			return fileInfo;
		}
		//업로드 확장자 검사 끝
		//업로드 사이즈 검사 시작
		if( uploadSizeLimit != -1L ){
			if( file.length() > uploadSizeLimit ){
				fileInfo.setFileUploadSuccess(false);
				fileInfo.setFileUploadErrorCode(FileInfoUploadResult.ERROR_UPLOAD_SIZE_OVER_FILE);
				return fileInfo;
			}
		}
		//업로드 사이즈 검사 끝
		//대체텍스트 검사 시작
		if( mtc.getMediaType().equals(MimeTypeCheck.IMAGE) ){
			if( StringUtils.isBlank(fileInfo.getFileAltText()) ){
				fileInfo.setFileUploadSuccess(false);
				fileInfo.setFileUploadErrorCode(FileInfoUploadResult.ERROR_ALT_TEXT_MISSING_FILE);
				return fileInfo;
			}
		} else {
			fileInfo.setFileAltText("");
		}
		//대체텍스트 검사 끝
		//--------------------------------------------------------------------------
		// END : 업로드 확장자, 사이즈, 대체텍스트 체크
		//--------------------------------------------------------------------------
		
		//저장경로 => Constant.UPLOAD_PATH + /{모듈pk ex)bcId}[/{모듈pk2차 ex)baId}]/년도/월/
		StringBuilder uploadDir = new StringBuilder(100);
		uploadDir.append( Constant.UPLOAD_PATH );
		uploadDir.append( "/" );
		uploadDir.append( fileInfo.getSiteId() );
		uploadDir.append( "/" );
		uploadDir.append( fileInfo.getFileModule() );
		uploadDir.append( "/" );
		if(StringUtils.isNotBlank(fileInfo.getFileModuleId())){
			uploadDir.append( fileInfo.getFileModuleId() );
			uploadDir.append( "/" );
		}
		if(StringUtils.isNotBlank(fileInfo.getFileModuleSub())){
			uploadDir.append( fileInfo.getFileModuleSub() );
			uploadDir.append( "/" );
		}
		uploadDir.append( String.valueOf(cal.get(Calendar.YEAR)) );
		uploadDir.append( "/" );
		uploadDir.append( String.format("%02d", (cal.get(Calendar.MONTH) + 1)) );
		uploadDir.append( "/" );
		
		//String webRoot = FilenameUtils.getFullPathNoEndSeparator(savePathRoot).replaceAll("\\\\", "/");
		String webRoot = savePathRoot;
		String uploadFolderPathFull = ""; 
		
		//EgovProperties.getProperty("Globals.fileStorePath")
		//Globals.fileStorePath 의 값이 있을경우 지정한 경로에 저장되게 하고 값이없을경우 디폴트로 설정된 컨텍스트내 폴더에 저장된다.
		String fileStorePath = EgovProperties.getProperty("Globals.fileStorePath");
		if(StringUtils.isNotBlank(fileStorePath)){
			uploadFolderPathFull = fileStorePath + uploadDir.toString();
		}else{
			uploadFolderPathFull = webRoot + uploadDir.toString();
		}
		LOGGER.info("[ASAPRO] File upload location : uploadFolderPathFull = {}", uploadFolderPathFull);
		String saveName =  DigestUtils.md5Hex(System.currentTimeMillis() + fileInfo.getFileOriginalName() + file.length()) + "." + extension;
		
		
		
		
		//폴더 없으면 부모폴더까지 생성
		File parentPath = new File(uploadFolderPathFull);
		FileUtils.forceMkdir(parentPath);
		parentPath.setReadable(true, false);
		parentPath.setWritable(true, false);
		parentPath.setExecutable(true, false);
		
		File toFile = new File(uploadFolderPathFull + saveName);
		
		//파일 저장
		FileCopyUtils.copy(new FileInputStream(file), new FileOutputStream(toFile, false));
		toFile.setReadable(true, false);
		toFile.setWritable(true, false);
		toFile.setExecutable(true, false);
		LOGGER.info("[ASAPRO] File saved...");
		
		//For image dimensions..
		//BufferedImage originalImage = null;
		
		//썸네일 생성하라고 플래그가 넘어와도 실제로 이미지일때만 생성한다.
		if( mtc.getMediaType().equals(MimeTypeCheck.IMAGE) ){
			
			//썸네일 생성할때 워터마크 들어가면 안될거 같아서 썸네일을 일단 먼저 생성함
			if(makeThumb){
				//BufferedImage originalImage = ImageIO.read(file);
				//makes default size
				MakeThumbnailResult makeThumbnailResult = makeThumnail(toFile, thumbWidth, thumbHeight, thumbCrop, "_thumb");
				fileInfo.setFileOriImageWidth(makeThumbnailResult.getOriImageWidth());
				fileInfo.setFileOriImageHeight(makeThumbnailResult.getOriImageHeight());
				fileInfo.setFileThumbnailPath( FilenameUtils.getFullPath(uploadDir.toString() + saveName) + FilenameUtils.getBaseName(uploadDir.toString() + saveName) + "_thumb.jpg" );
				//thumbnail
				LOGGER.info("[ASAPRO] Thumnails generated...");
			}
			
			//워터마크 삽입 - ★워터마크 삽입되면 위에서 세이브된 이미지가 워터마크삽입된 이미지로 교체됨
			if( printWatermark ){
				//워터마크 삽입이 일단 미디어 라이브러리 전반에 걸쳐서 적용됨.
				//배너나 팝업존 처럼 적용되면 안되는 경우는 별도 처리하거나 다른 메소드 이용해야함.
				File watermarkImage = new File(webRoot + watermarkConfig.getOption("watermark_image"));
				this.printWatermark(new FileInputStream(file), toFile, watermarkImage, watermarkConfig.getOption("watermark_position"));
			}
			
		}
		
		//fileInfo.setFileID(fileId);
		//fileInfo.setFileOriginalName(multipartFile.getOriginalFilename());
		fileInfo.setFileMediaType(mtc.getMediaType());
		fileInfo.setFileMimeType(mtc.getMimeType());
		//fileInfo.setFilePath(file.getParent().replaceAll("\\\\", "/").replace(FilenameUtils.getFullPathNoEndSeparator(savePathRoot).replaceAll("\\\\", "/"), "") + "/" + saveName);
		fileInfo.setFilePath(uploadDir.toString().replaceAll("\\\\", "/") + saveName);
		fileInfo.setFileExt(mtc.getExtension());
		fileInfo.setFileSize(toFile.length());
		fileInfo.setFileRegDate(today);
		fileInfo.setFileDownloadCount(0);
		
		//If file is image file
		//if(originalImage != null){
			//commonFile.setFileDimensions(originalImage.getWidth() + " × " + originalImage.getHeight());
		//}
		
		fileInfo.setFileUUID( UUID.randomUUID().toString().replace("-", "") );
		fileInfo.setFileServletUrl(AsaproUtils.getAppPath((Site)request.getAttribute("currentSite")) + "/file/image/uu/" + fileInfo.getFileUUID() );
		fileInfo.setFileServletThumbnailUrl(AsaproUtils.getAppPath((Site)request.getAttribute("currentSite")) + "/file/thumbnail/uu/" + fileInfo.getFileUUID() );

		fileInfoMapper.insertFileInfo(fileInfo);
		fileInfo.setFileUploadSuccess(true);
		return fileInfo;
	}

	



}