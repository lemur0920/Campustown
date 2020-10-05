/**
 * 
 */
package egovframework.com.asapro.archive.customtype.space.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import egovframework.com.asapro.archive.service.ArchiveSearch;
import egovframework.com.asapro.fileinfo.service.FileInfoUploadResult;
import egovframework.com.asapro.support.exception.AsaproException;


/**
 * 공간정보 관리 서비스
 * @author yckim
 * @since 2019. 2. 26.
 *
 */
public interface SpaceService {

	//=============================================================================================================================
	//==========================================  공간정보    ================================================================
	//=============================================================================================================================
	
	/**
	 * 공간정보 목록을 조회한다.
	 * @param spaceSearch
	 * @return 공간정보 목록
	 */
	public List<Space> getSpaceList(SpaceSearch spaceSearch);
	
	/**
	 * 공간정보 목록 토탈을 조회한다.
	 * @param spaceSearch
	 * @return 공간정보 목록 토탈
	 */
	public int getSpaceListTotal(SpaceSearch spaceSearch);

	/**
	 * 공간정보를 등록한다
	 * @param spaceForm
	 * @return 등록결과
	 * @throws IOException 
	 * @throws AsaproException 
	 */
	public Map<String, FileInfoUploadResult> insertSpace(Space spaceForm) throws AsaproException, IOException;

	/**
	 * 공간정보를 조회한다.
	 * @param spaceForm
	 * @return 공간정보
	 */
	public Space getSpace(Space spaceForm);

	/**
	 * 공간정보를 수정한다.
	 * @param spaceForm
	 * @return 수정결과
	 * @throws IOException 
	 * @throws AsaproException 
	 */
	public Map<String, FileInfoUploadResult> updateSpace(Space spaceForm) throws AsaproException, IOException;

	
	
}
