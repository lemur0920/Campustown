/**
 * 
 */
package egovframework.com.asapro.fileinfo.service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

//import egovframework.com.asapro.media.service.Media;
import egovframework.com.asapro.support.exception.AsaproException;

/**
 * 파일 정보 서비스
 * @author yckim
 * @since 2018. 6. 12.
 *
 */
public interface FileInfoService {
	
	/**
	 * 파일정보를 저장한다.
	 * <pre>
	 * - uploadSizeLimit -1L 이면 업로드 사이즈 제한하지 않는다.
	 * - 워터마크 옵션 추가
	 * </pre>
	 * @param multipartFile
	 * @param savePathRoot
	 * @param fileInfo
	 * @param uploadSizeLimit
	 * @param makeThumb
	 * @param thumbWidth
	 * @param thumbHeight
	 * @param thumbCrop
	 * @param uploadWhiteList
	 * @param printWatermark
	 * @return
	 * @throws IOException
	 * @throws AsaproException
	 */
	public FileInfo saveFile(MultipartFile multipartFile, String savePathRoot, FileInfo fileInfo, long uploadSizeLimit, boolean makeThumb, int thumbWidth, int thumbHeight, boolean thumbCrop, String uploadWhiteList, boolean printWatermark) throws IOException, AsaproException;
	
	/**
	 * 파일정보를 저장한다.
	 * <pre>
	 * - uploadSizeLimit -1L 이면 업로드 사이즈 제한하지 않는다.
	 * - 가능확장자 옵션 추가
	 * </pre>
	 * @param multipartFile
	 * @param savePathRoot
	 * @param fileInfo
	 * @param uploadSizeLimit
	 * @param makeThumb
	 * @param thumbWidth
	 * @param thumbHeight
	 * @param thumbCrop
	 * @param uploadWhiteList
	 * @return
	 * @throws IOException
	 * @throws AsaproException
	 */
	public FileInfo saveFile(MultipartFile multipartFile, String savePathRoot, FileInfo fileInfo, long uploadSizeLimit, boolean makeThumb, int thumbWidth, int thumbHeight, boolean thumbCrop, String uploadWhiteList) throws IOException, AsaproException;
	
	/**
	 * 파일정보를 저장한다.
	 * <pre>
	 * - uploadSizeLimit -1L 이면 업로드 사이즈 제한하지 않는다.
	 * </pre>
	 * @param multipartFile
	 * @param savePathRoot
	 * @param fileInfo
	 * @param uploadSizeLimit
	 * @param makeThumb
	 * @param thumbWidth
	 * @param thumbHeight
	 * @param thumbCrop
	 * @return
	 * @throws IOException
	 * @throws AsaproException
	 */
	public FileInfo saveFile(MultipartFile multipartFile, String savePathRoot, FileInfo fileInfo, long uploadSizeLimit, boolean makeThumb, int thumbWidth, int thumbHeight, boolean thumbCrop) throws IOException, AsaproException;
	
	/**
	 * 이미지 썸네일을 생성하면서 파일 정보를 저장한다.
	 * (관리자 설정의 썸네일 가로세로 기본값을 기준으로 썸네일 생성함)
	 * <pre>
	 * - uploadSizeLimit -1L 이면 업로드 사이즈 제한하지 않는다.
	 * </pre> 
	 * @param multipartFile
	 * @param savePathRoot
	 * @param fileInfo
	 * @param uploadSizeLimit
	 * @return 파일 정보
	 * @throws IOException 
	 * @throws AsaproException 
	 */
	public FileInfo saveFileMakeThumbWithDefaultOption(MultipartFile multipartFile, String savePathRoot, FileInfo fileInfo, long uploadSizeLimit) throws IOException, AsaproException;
	
	/**
	 * 이미지 썸네일을 생성하면서 파일 정보를 저장한다.(생성될 썸네일의 크기를 지정해서 생성함.예)게시판)
	 * @param multipartFile
	 * @param savePathRoot
	 * @param fileInfo
	 * @param uploadSizeLimit
	 * @param thumbWidth
	 * @param thumbHeight
	 * @param thumbCrop
	 * @return
	 * @throws IOException 
	 * @throws AsaproException 
	 */
	public FileInfo saveFileMakeThumbWithCustomOption(MultipartFile multipartFile, String savePathRoot, FileInfo fileInfo, long uploadSizeLimit, int thumbWidth, int thumbHeight, boolean thumbCrop) throws IOException, AsaproException;
	
	/**
	 * 이미지 썸네일을 생성하면서 파일 정보를 저장한다. 가능확장자목록 옵션 추가 (생성될 썸네일의 크기를 지정해서 생성함.예)게시판)
	 * @param multipartFile
	 * @param savePathRoot
	 * @param fileInfo
	 * @param uploadSizeLimit
	 * @param thumbWidth
	 * @param thumbHeight
	 * @param thumbCrop
	 * @param uploadWhiteList
	 * @return
	 * @throws IOException 
	 * @throws AsaproException 
	 */
	public FileInfo saveFileMakeThumbWithCustomOption(MultipartFile multipartFile, String savePathRoot, FileInfo fileInfo, long uploadSizeLimit, int thumbWidth, int thumbHeight, boolean thumbCrop, String uploadWhiteList) throws IOException, AsaproException;
	
	/**
	 * 이미지 썸네일을 생성하면서 파일 정보를 저장한다. 가능확장자목록 옵션 추가. 워터마크 옵션 추가 (생성될 썸네일의 크기를 지정해서 생성함.예)게시판)
	 * @param multipartFile
	 * @param savePathRoot
	 * @param fileInfo
	 * @param uploadSizeLimit
	 * @param thumbWidth
	 * @param thumbHeight
	 * @param thumbCrop
	 * @param uploadWhiteList
	 * @param watermark
	 * @return
	 * @throws IOException
	 * @throws AsaproException
	 */
	public FileInfo saveFileMakeThumbWithCustomOption(MultipartFile multipartFile, String savePathRoot, FileInfo fileInfo, long uploadSizeLimit, int thumbWidth, int thumbHeight, boolean thumbCrop, String uploadWhiteList, boolean printWatermark) throws IOException, AsaproException;
	
	/**
	 * 파일 정보를 저장한다.
	 * <pre>
	 * - 업로드 사이즈 제한하지 않는다.
	 * </pre>
	 * @param multipartFile
	 * @param savePathRoot
	 * @param fileInfo
	 * @return 파일 정보
	 * @throws IOException 
	 * @throws AsaproException
	 */
	public FileInfo saveFileNoThumb(MultipartFile multipartFile, String savePathRoot, FileInfo fileInfo) throws IOException, AsaproException;
	
	/**
	 * 파일 정보를 저장한다.
	 * <pre>
	 * - 업로드 사이즈를 지정해서 크기를 제한할 수 있다.
	 * </pre>
	 * @param multipartFile
	 * @param savePathRoot
	 * @param fileInfo
	 * @param uploadSizeLimit
	 * @return
	 * @throws IOException
	 * @throws AsaproException
	 */
	public FileInfo saveFileNoThumbWithUploadSizeLimit(MultipartFile multipartFile, String savePathRoot, FileInfo fileInfo, long uploadSizeLimit) throws IOException, AsaproException; 
	
	/**
	 * 썸네일을 생성한다.
	 * @param original
	 * @param destWidth
	 * @param destHeight
	 * @param crop
	 * @param suffix
	 * @return
	 * @throws IOException 
	 * @throws AsaproException 
	 */
	public MakeThumbnailResult makeThumnail(File original, int destWidth, int destHeight, boolean crop, String suffix) throws IOException, AsaproException;

	/**
	 * 실제 파일을 삭제한다.
	 * @param file
	 * @return 삭제 결과
	 */
	public boolean deleteFile(File file);
	
	/**
	 * 실제 파일을 삭제한다.
	 * @param filePath
	 * @return
	 */
	public boolean deleteFile(String filePath);
	
	/**
	 * 실제파일 삭제 + 파일정보를 삭제한다.
	 * @param fileId
	 * @return 삭제 결과
	 */
	public int deleteFileInfo(FileInfo fileIdSearch);
	

	/**
	 * 실제파일 삭제 + 파일정보를 삭제한다.
	 * @param fileIds
	 * @return 삭제 결과
	 */
	public int deleteFileInfo(List<FileInfo> fileIdSearchList);

	/**
	 * 파일 정보를 조회한다.
	 * @param fileId
	 * @param fileUUID
	 * @return 파일정보
	 */
	public FileInfo getFileInfo(FileInfo fileIdSearch);

	/**
	 * 파일목록을 조회한다.
	 * @param fileInfoSearch
	 * @return 파일 목록
	 */
	public List<FileInfo> getFileInfoList(FileInfoSearch fileInfoSearch);
	
	/**
	 * 파일목록을 조회한다.(첨부된곳 정보 제외)
	 * @param fileInfoSearch
	 * @return 파일 목록
	 */
	public List<FileInfo> getFileInfoListOnly(FileInfoSearch fileInfoSearch);
	
	/**
	 * 파일목록 개수를 조회한다.
	 * @param statDownloadSearch
	 * @return
	 */
	public int getFileInfoListTotal(FileInfoSearch fileInfoSearch);
	
	/**
	 * 파일 다운로드 카운트를 증가시킨다.
	 * @param fileId
	 * @return 처리결과
	 */
	public int updateFileInfoDownloadCount(FileInfo fileIdSearch);

	/**
	 * 썸네일을 생성하고 원본 파일은 제거한다. 원본파일의 이름은 그대로 사용한다.
	 * @param original
	 * @param destWidth
	 * @param destHeight
	 * @param crop
	 * @return
	 * @throws IOException
	 * @throws AsaproException
	 */
	public MakeThumbnailResult makeThumnailOverrideOriginal(File original, int destWidth, int destHeight, boolean crop) throws IOException, AsaproException;
	
	/**
	 * 썸네일을 생성한다.
	 * @param original
	 * @param destWidth
	 * @param destHeight
	 * @param crop
	 * @param suffix
	 * @param deleteOriginal
	 * @return
	 * @throws IOException
	 * @throws AsaproException
	 */
	public MakeThumbnailResult makeThumnail(File original, int destWidth, int destHeight, boolean crop, String suffix, boolean deleteOriginal) throws IOException, AsaproException;
	
	/**
	 * 파일을 저장한다.
	 * <pre>
	 * - 파일정보를 디비에 저장하지 않고 파일을 디렉토리에 바로 저장한다.
	 * - 파일 변환도 하지 않는다.
	 * </pre>
	 * @param fromFile
	 * @param toFile
	 * @param deleteSameNameFile 같은 이름의 파일을 지울지 여부
	 * @param printWatermark
	 * @return
	 */
	public File saveFileOnly(File fromFile, File toFile, boolean deleteSameNameFile, boolean printWatermark);
	
	/**
	 * 파일을 저장한다.
	 * <pre>
	 * - 파일정보를 디비에 저장하지 않고 파일을 디렉토리에 바로 저장한다.
	 * - 파일 변환도 하지 않는다.
	 * - 워터마크 삽입 안한다.
	 * </pre>
	 * @param fromFile
	 * @param toFile
	 * @param deleteSameNameFile 같은 이름의 파일을 지울지 여부
	 * @return
	 * @deprecated 앞으로는 워터마크 파라메터까지 받는 메소드를 사용하도록 하자.
	 */
	public File saveFileOnly(File fromFile, File toFile, boolean deleteSameNameFile);

	/**
	 * 파일을 저장한다.
	 * <pre>
	 * - 파일정보를 디비에 저장하지 않고 파일을 디렉토리에 바로 저장한다.
	 * - 파일 변환도 하지 않는다.
	 * - 상위 폴더가 존재하지 않으면 생성한다.
	 * </pre>
	 * @param multipartFile
	 * @param toFile
	 * @param deleteSameNameFile 같은 이름의 파일을 지울지 여부
	 * @param printWatermark
	 */
	public File saveFileOnly(MultipartFile multipartFile, File toFile, boolean deleteOldFile, boolean printWatermark);
	
	/**
	 * 파일을 저장한다.
	 * <pre>
	 * - 파일정보를 디비에 저장하지 않고 파일을 디렉토리에 바로 저장한다.
	 * - 파일 변환도 하지 않는다.
	 * - 상위 폴더가 존재하지 않으면 생성한다.
	 * - 워터마크 삽입 하지 않는다.
	 * </pre>
	 * @param multipartFile
	 * @param toFile
	 * @param deleteSameNameFile 같은 이름의 파일을 지울지 여부
	 * @deprecated 앞으로는 워터마크 파라메터까지 받는 메소드를 사용하도록 하자.
	 */
	public File saveFileOnly(MultipartFile multipartFile, File toFile, boolean deleteOldFile);

	/**
	 * 파일정보를 수정한다,
	 * @param fileInfo
	 * @return 수정 결과
	 */
	public int updateFileInfo(FileInfo fileInfo);
	
	/**
	 * 미디어 라이브러리 유형별 서브토탈을 반환한다.
	 * @return 유형별 서브토탈
	 */
	public Map<String, Integer> getMediaCountMap(FileInfoSearch fileInfoSearch);

	/**
	 * 미디어 정보를 수정한다.
	 * @param mediaForm
	 * @return 수정 결과 
	 */
//	public int updateMedia(Media mediaForm);
	
	/**
	 * 파일의 Alt text를 수정한다.
	 * @param fileInfoForm
	 * @return 수정결과
	 */
	public int updateFileInfoAltText(FileInfo fileInfoForm);

	
	/**
	 * 파일 아이디에 해당하는 미디어 정보를 반환한다.
	 * @param fileIds
	 * @return 미디어 정보
	 */
	public List<FileInfo> getFileInfoList(String sitePrefix, List<FileInfo> fileIdSearchList);
	
	/**
	 * 이미지에 워터마크를 삽입한다.
	 * @param originalFile
	 * @param targetFile
	 * @param watermarkImage
	 * @return 워터마크가 추가된 이미지 파일
	 */
	public File printWatermark(File originalFile, File targetFile, File watermarkImage, String watermarkPosition);
	
	/**
	 * 이미지에 워터마크를 삽입한다.
	 * @param inputStream
	 * @param outputStream
	 * @param watermarkImage
	 * @param watermarkPosition
	 * @return
	 */
	public void printWatermark(InputStream inputStream, OutputStream outputStream, File watermarkImage, String watermarkPosition);
	
	/**
	 * 이미지에 워터마크를 삽입한다.
	 * @param inputStream
	 * @param targetFile
	 * @param watermarkImage
	 * @param watermarkPosition
	 * @return
	 */
	public File printWatermark(InputStream inputStream, File targetFile, File watermarkImage, String watermarkPosition);
	
	/**
	 * 이미지에 워터마크를 삽입한다.
	 * @param OriginalFile
	 * @param outputStream
	 * @param watermarkImage
	 * @param watermarkPosition
	 * @return
	 */
	public void printWatermark(File originalFile, OutputStream outputStream, File watermarkImage, String watermarkPosition);
	
	/**
	 * 파일 마이그레이션 메소드... saveFile 메소드 내용 바뀌면 같이 수정해줄것!!
	 * @param file
	 * @param savePathRoot
	 * @param fileInfo
	 * @param uploadSizeLimit
	 * @param makeThumb
	 * @param thumbWidth
	 * @param thumbHeight
	 * @param thumbCrop
	 * @param printWatermark
	 * @return
	 * @throws IOException
	 * @throws AsaproException
	 */
	public FileInfo saveFileForMigration(File file, String savePathRoot, FileInfo fileInfo, long uploadSizeLimit, boolean makeThumb, int thumbWidth, int thumbHeight, boolean thumbCrop, boolean printWatermark) throws IOException, AsaproException;

	
}
