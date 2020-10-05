/**
 * 
 */
package egovframework.com.asapro.fileinfo.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

//import egovframework.com.asapro.media.service.Media;
import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * 파일정보 SQL 매퍼
 * @author yckim
 * @since 2018. 6. 12.
 *
 */
@Mapper
public interface FileInfoMapper {
	
	/**
	 * 파일정보 목록을 조회한다.
	 * @param fileInfoSearch
	 * @return 파일 정보 목록
	 */
	public List<FileInfo> selectFileInfoList(FileInfoSearch fileInfoSearch);
	
	/**
	 * 파일 정보 목록 토탈을 조회한다.
	 * @param fileInfoSearch
	 * @return 파일정보 목록 토탈
	 */
	public int selectFileInfoListTotal(FileInfoSearch fileInfoSearch);
	
	/**
	 * 파일 정보를 조회한다.
	 * @param fileId
	 * @return 파일정보
	 */
	public FileInfo selectFileInfo(FileInfo fileIdSearch);
	
	/**
	 * 파일 정보를 입력한다.
	 * @param fileInfo
	 * @return PK
	 */
	public int insertFileInfo(FileInfo fileInfo);
	
	/**
	 * 파일정보를 삭제한다.
	 * @param fileId
	 * @return 삭제 결과
	 */
	public int deleteFileInfo(FileInfo fileIdSearch);

	/**
	 * 파일정보 다운로드 카운트를 증가시킨다.
	 * @param fileId
	 * @return 처리결과
	 */
	public int updateFileInfoDownloadCount(FileInfo fileIdSearch);

	/**
	 * 파일정보를 수정한다,
	 * @param fileInfo
	 * @return 수정 결과
	 */
	public int updateFileInfo(FileInfo fileInfo);
	
	/**
	 * 미디어 유형 카운트 맵리스트를 반환한다.
	 * @return
	 */
	public List<Map<String, Object>> selectMediaCountMapList(FileInfoSearch fileInfoSearch);

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
	 * 미디어 목록을 반환한다.
	 * @param sitePrefix 
	 * @param fileIds
	 * @return 미디어 목록
	 */ 
	public List<FileInfo> selectFileInfoListByFileIds(@Param("sitePrefix") String sitePrefix, @Param("fileInfoList") List<FileInfo> fileInfoList);

	
	
}
