package egovframework.com.campustown.univHpMngr.service;

import java.util.List;

import egovframework.com.asapro.organization.service.Department;
import egovframework.rte.psl.dataaccess.mapper.Mapper;


/**
 * 대학 홈페이지 관리 SQL 매퍼
 * @author jaseo
 * @since 2020. 02. 11.
 */
@Mapper
public interface UnivHpMngrMapper {
	
	/**
	 * 대학 홈페이지 관리 목록을 조회한다.
	 * @param univHpMngrSearch
	 * @return
	 */
	public List<UnivHpMngr> selectUnivHpMngrList(UnivHpMngrSearch univHpMngrSearch);

	/**
	 * 대학 홈페이지 관리 목록 수를 조회한다.
	 * @param univHpMngrSearch
	 * @return
	 */
	public int selectUnivHpMngrListTotal(UnivHpMngrSearch univHpMngrSearch);
	
	/**
	 * 대학 홈페이지 관리 정보를 등록한다.
	 * @param univHpMngrForm
	 * @return
	 */
	public int insertUnivHpMngr(UnivHpMngr univHpMngrForm);
	
	
	/**
	 * 대학 홈페이지 관리 정보를 가져온다. (단일조회)
	 * @param univHpMngrForm
	 * @return
	 */
	public UnivHpMngr selectUnivHpMngrInfo(UnivHpMngrSearch univHpMngrSearch);
	
	
	/**
	 * 대학 홈페이지 관리 정보를 수정한다.
	 * @param univHpMngrForm
	 * @return
	 */
	public int updateUnivHpMngr(UnivHpMngr univHpMngrForm);
	
	
	/**
	 * 대학 홈페이지 정보를 삭제한다.
	 * @param univHp
	 * @return
	 */
	public int deleteUnivHpMngr(UnivHpMngr univHp);
	
	/**
	 * 캠퍼스타운명 변경에 따른 부서명 수정
	 * @param department
	 * @return
	 */
	public int updateDepNm(Department department);
	

}
