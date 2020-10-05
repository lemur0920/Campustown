/**
 * 
 */
package egovframework.com.asapro.archive.customtype.research.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import egovframework.com.asapro.archive.service.ArchiveSearch;
import egovframework.com.asapro.fileinfo.service.FileInfoUploadResult;
import egovframework.com.asapro.support.exception.AsaproException;


/**
 * 연구자료 관리 서비스
 * @author yckim
 * @since 2019. 2. 27.
 *
 */
public interface ResearchService {

	//=============================================================================================================================
	//==========================================  연구자료    ================================================================
	//=============================================================================================================================
	
	/**
	 * 연구자료 목록을 조회한다.
	 * @param researchSearch
	 * @return 연구자료 목록
	 */
	public List<Research> getResearchList(ResearchSearch researchSearch);
	
	/**
	 * 연구자료 목록 토탈을 조회한다.
	 * @param researchSearch
	 * @return 연구자료 목록 토탈
	 */
	public int getResearchListTotal(ResearchSearch researchSearch);

	/**
	 * 연구자료를 등록한다
	 * @param researchForm
	 * @return 등록결과
	 * @throws IOException 
	 * @throws AsaproException 
	 */
	public Map<String, FileInfoUploadResult> insertResearch(Research researchForm) throws AsaproException, IOException;

	/**
	 * 연구자료를 조회한다.
	 * @param researchForm
	 * @return 연구자료
	 */
	public Research getResearch(Research researchForm);

	/**
	 * 연구자료를 수정한다.
	 * @param researchForm
	 * @return 수정결과
	 * @throws IOException 
	 * @throws AsaproException 
	 */
	public Map<String, FileInfoUploadResult> updateResearch(Research researchForm) throws AsaproException, IOException;

	
	
}
