/**
 * 
 */
package egovframework.com.asapro.archive.customtype.research.service;

import java.util.List;
import java.util.Map;

import egovframework.com.asapro.fileinfo.service.FileInfo;
import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * 연구자료 관리 SQL 매퍼
 * @author yckim
 * @since 2019. 2. 27.
 *
 */
@Mapper
public interface ResearchMapper {

	//=============================================================================================================================
	//==========================================  연구자료    ================================================================
	//=============================================================================================================================
	
	/**
	 * 연구자료 목록을 조회한다.
	 * @param researchSearch
	 * @return 연구자료 목록
	 */
	public List<Research> selectResearchList(ResearchSearch researchSearch);

	/**
	 * 연구자료 목록 토탈을 조회한다.,
	 * @param researchSearch
	 * @return 연구자료 목록 토탈
	 */
	public int selectResearchListTotal(ResearchSearch researchSearch);

	/**
	 * 연구자료를 등록한다.
	 * @param researchForm
	 * @return 등록결과
	 */
	public int insertResearch(Research researchForm);

	/**
	 * 연구자료를 조회한다.
	 * @param researchForm
	 * @return 연구자료
	 */
	public Research selectResearch(Research researchForm);

	/**
	 * 연구자료를 수정한다.
	 * @param researchForm
	 * @return 수정결과
	 */
	public int updateResearch(Research researchForm);


}
