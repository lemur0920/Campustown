/**
 * 
 */
package egovframework.com.asapro.archive.service;

import java.util.List;
import java.util.Map;

import egovframework.com.asapro.fileinfo.service.FileInfo;
import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * 아카이브 관리 SQL 매퍼
 * @author yckim
 * @since 2018. 12. 19.
 *
 */
@Mapper
public interface ArchiveMapper {

	/**
	 * 아카이브 카테고리 목록을 조회
	 * @param archiveCategorySearch
	 * @return 아카이브 카테고리 목록
	 */
	public List<ArchiveCategory> selectArchiveCategoryList(ArchiveCategorySearch archiveCategorySearch);

	/**
	 * 아카이브 카테고리 목록 토탈 조회
	 * @param archiveCategorySearch
	 * @return 아카이브 카테고리 목록 토탈
	 */
	public int selectArchiveCategoryListTotal(ArchiveCategorySearch archiveCategorySearch);

	/**
	 * 아카이브 카테고리를 추가한다.
	 * @param archiveCategoryForm
	 * @return 추가결과
	 */
	public int insertArchiveCategory(ArchiveCategory archiveCategoryForm);

	/**
	 * 아카이브 카테고리를 조회한다.
	 * @param archiveCategoryForm
	 * @return 아카이브 카테고리
	 */
	public ArchiveCategory selectArchiveCategory(ArchiveCategory archiveCategoryForm);

	/**
	 * 아카이브 카테고리를 수정한다.
	 * @param archiveCategoryForm
	 * @return 수정결과
	 */
	public int updateArchiveCategory(ArchiveCategory archiveCategoryForm);

	/**
	 * 아카이브 카테고리를 삭제한다.
	 * @param archiveCategory
	 * @return 삭제결과
	 */
	public int deleteArchiveCategory(ArchiveCategory archiveCategory);

	
	//=============================================================================================================================
	//==========================================  아카이브    ================================================================
	//=============================================================================================================================
	
	/**
	 * 아카이브 목록을 조회한다.
	 * @param archiveSearch
	 * @return 아카이브 목록
	 */
	public List<Archive> selectArchiveList(ArchiveSearch archiveSearch);

	/**
	 * 아카이브 목록 토탈을 조회한다.,
	 * @param archiveSearch
	 * @return 아카이브 목록 토탈
	 */
	public int selectArchiveListTotal(ArchiveSearch archiveSearch);

	/**
	 * 아카이브를 추가한다.
	 * @param archiveForm
	 * @return 추가결과
	 */
	public int insertArchive(Archive archiveForm);

	/**
	 * 아카이브를 조회한다.
	 * @param archiveForm
	 * @return 아카이브
	 */
	public Archive selectArchive(Archive archiveForm);

	/**
	 * 아카이브를 수정한다.
	 * @param archiveForm
	 * @return 수정결과
	 */
	public int updateArchive(Archive archiveForm);

	/**
	 * 아카이브를 삭제한다.
	 * @param archive
	 * @return 삭제결과
	 */
	public int deleteArchive(Archive archive);

	/**
	 * 아카이브를 조회수를 증가시킨다.
	 * @param archive
	 * @return 증가결과
	 */
	public int updateArchiveHit(Archive archive);
	
	/**
	 * 아카이브를 추천수를 증가시킨다.
	 * @param recommendArchive
	 * @return 추천결과
	 */
	public int updateArchiveRecommend(Archive recommendArchive);
	
	
	//===========================================================================================
	
	/**
	 * 아카이브의 첨부 파일 정보를 반환한다.
	 * @param fileInfo
	 * @return
	 */
	public FileInfo selectArchiveFileInfoList(FileInfo fileInfo);

	

}
