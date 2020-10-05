package egovframework.com.campustown.univHpMngr.service;

import java.util.List;

import egovframework.com.asapro.organization.service.Department;

/**
 * 창업팀 홈페이지 관리 서비스
 * @author jaseo
 * @since 2020. 02. 12.
 */
public interface UnivHpMngrService {

	/*******************************************************/
	// 대학 홈페이지 관리 CRUD
	/*******************************************************/
	
	/**
	 * 대학 홈페이지 관리 목록을 조회한다.
	 * @param univHpMngrSearch
	 * @return
	 */
	public List<UnivHpMngr> getUnivHpMngrList(UnivHpMngrSearch univHpMngrSearch);
	
	/**
	 * 대학 홈페이지 관리 목록 수를 조회한다.
	 * @param univHpMngrSearch
	 * @return
	 */
	public int getUnivHpMngrListTotal(UnivHpMngrSearch univHpMngrSearch);
	
	
	/**
	 * 대학 홈페이지 관리 정보를 등록한다.
	 * @param univHpMngrForm
	 * @return
	 */
	public int insertUnivHpMngr(UnivHpMngr univHpMngr);

	
	/**
	 * 대학 홈페이지 관리 정보를 가져온다. (단일조회)
	 * @param univHpMngrForm
	 * @return
	 */
	public UnivHpMngr getUnivHpMngrInfo(UnivHpMngrSearch univHpMngrSearch);
	
	/**
	 * 대학 홈페이지 관리 정보를 수정한다.
	 * @param univHpMngrForm
	 * @return
	 */
	public int updateUnivHpMngr(UnivHpMngr univHpMngrForm);

	/**
	 * 대학 홈페이지 정보를 삭제한다. 
	 * @param univHpList
	 * @return
	 */
	public int deleteUnivHpMngr(List<UnivHpMngr> univHpList);
	
	/**
	 * 캠퍼스타운명 변경에 따른 부서명 수정
	 * @param department
	 * @return
	 */
	public int updateDepNm(Department department);
	
}
