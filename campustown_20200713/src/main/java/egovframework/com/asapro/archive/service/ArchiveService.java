/**
 * 
 */
package egovframework.com.asapro.archive.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import egovframework.com.asapro.fileinfo.service.FileInfo;
import egovframework.com.asapro.fileinfo.service.FileInfoUploadResult;
import egovframework.com.asapro.support.exception.AsaproException;


/**
 * 아카이브 관리 서비스
 * @author yckim
 * @since 2018. 12. 19.
 *
 */
public interface ArchiveService {

	//=============================================================================================================================
	//==========================================  아카이브 카테고리   ================================================================
	//=============================================================================================================================
	
	/**
	 * 아카이브 카테고리 목록을 조회한다.
	 * @param archiveCategorySearch
	 * @return 아카이브 카테고리 목록
	 */
	public List<ArchiveCategory> getArchiveCategoryList(ArchiveCategorySearch archiveCategorySearch);

	/**
	 * 아카이브 카테고리 목록 토탈을 반환한다.
	 * @param archiveCategorySearch
	 * @return 아카이브 카테고리 토탈
	 */
	public int getArchiveCategoryListTotal(ArchiveCategorySearch archiveCategorySearch);

	/**
	 * 아카이브 카테고리를 등록한다.
	 * @param archiveCategoryForm
	 * @return 등록결과
	 */
	public int insertArchiveCategory(ArchiveCategory archiveCategoryForm);

	/**
	 * 아카이브 카테고리를 조회한다.
	 * @param archiveCategoryForm
	 * @return 아카이브 카테고리
	 */
	public ArchiveCategory getArchiveCategory(ArchiveCategory archiveCategoryForm);

	/**
	 * 아카이브 카테고리를 수정한다.
	 * @param archiveCategoryForm
	 * @return 수정결과
	 */
	public int updateArchiveCategory(ArchiveCategory archiveCategoryForm);

	/**
	 * 아카이브 카테고리를 삭제한다.
	 * @param archiveCategoryList
	 * @return 삭제결과
	 */
	public int deleteArchiveCategory(List<ArchiveCategory> archiveCategoryList);

	/**
	 * 아카이브 카테고리를 삭제한다.
	 * @param archiveCategory
	 * @return 삭제결과
	 */
	public int deleteArchiveCategory(ArchiveCategory archiveCategory);

	/**
	 * ArchiveItem 어노테이션이 붙은 클래스의 정보를 반환한다.
	 * @return ArchiveItem 어노테이션이 붙은 클래스의 정보
	 */
	public List<Map<String, String>> getArchiveItemAnnotationedList();

	
	//=============================================================================================================================
	//==========================================  아카이브    ================================================================
	//=============================================================================================================================
	
	/**
	 * 아카이브 목록을 조회한다.
	 * @param archiveSearch
	 * @return 아카이브 목록
	 */
	public List<Archive> getArchiveList(ArchiveSearch archiveSearch);

	/**
	 * 아카이브 목록 토탈을 조회한다.
	 * @param archiveSearch
	 * @return 아카이브 목록 토탈
	 */
	public int getArchiveListTotal(ArchiveSearch archiveSearch);
	
	/**
	 * 아카이브를 추가한다.
	 * @param archiveForm
	 * @return 추가결과
	 */
	public Map<String, FileInfoUploadResult> insertArchive(Archive archiveForm) throws AsaproException, IOException;
	
	/**
	 * 아카이브를 조회한다.
	 * @param archiveForm
	 * @return 아카이브 정보
	 */
	public Archive getArchive(Archive archiveForm);

	/**
	 * 아카이브를 수정한다.
	 * @param archiveForm
	 * @return 수정결과
	 */
	public Map<String, FileInfoUploadResult> updateArchive(Archive archiveForm) throws AsaproException, IOException;

	/**
	 * 아카이브를 삭제한다.
	 * @param archiveForm
	 * @return 삭제결과
	 */
	public int deleteArchive(List<Archive> archiveList);

	/**
	 * 아카이브를 삭제한다.
	 * @param archiveForm
	 * @return 삭제결과
	 */
	public int deleteArchive(Archive archiveForm);
	
	/**
	 * 아카이브를 조회수를 증가시킨다.
	 * @param archiveForm
	 * @return 증가결과
	 */
	public int updateArchiveHit(Archive archiveForm);
	
	/**
	 * 템플릿 파일 경로를 반환한다.
	 * @param catCustomType
	 * @param catAdminListTemplate
	 * @param string
	 * @return 템플릿 경로
	 */
	public String getArchiveTemplateName(String catCustomType, String catAdminListTemplate, String string);

	/**
	 * 아카이브 추천수를 증가한다.
	 * @param recommendArchive
	 * @return 추천결과
	 */
	public int updateArchiveRecommend(Archive recommendArchive);
	
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
	public FileInfo saveMultipartFile(MultipartFile multipartFile, String arcAltText, ArchiveCategory archiveCategory, FileInfoUploadResult fileInfoUploadResult, Archive archive, String uploadWhiteList, String attachmentType) throws AsaproException, IOException;
	
	/**
	 * 첨부파일의 정보와 파일을 저장한다.
	 * @param multipartFile
	 * @param arcAltText
	 * @param archiveCategory
	 * @param fileSize
	 * @param fileInfoUploadResult
	 * @param archive
	 * @param uploadWhiteList
	 * @param attachmentType
	 * @return 파일정보
	 * @throws AsaproException
	 * @throws IOException
	 */
	public FileInfo saveMultipartFile(MultipartFile multipartFile, String arcAltText, ArchiveCategory archiveCategory, int fileSize, FileInfoUploadResult fileInfoUploadResult, Archive archive, String uploadWhiteList, String attachmentType) throws AsaproException, IOException;

}
