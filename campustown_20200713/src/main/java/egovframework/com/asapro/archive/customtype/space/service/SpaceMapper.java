/**
 * 
 */
package egovframework.com.asapro.archive.customtype.space.service;

import java.util.List;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * 공간정보 관리 SQL 매퍼
 * @author yckim
 * @since 2019. 2. 26.
 *
 */
@Mapper
public interface SpaceMapper {

	//=============================================================================================================================
	//==========================================  공간정보    ================================================================
	//=============================================================================================================================
	
	/**
	 * 공간정보 목록을 조회한다.
	 * @param spaceSearch
	 * @return 공간정보 목록
	 */
	public List<Space> selectSpaceList(SpaceSearch spaceSearch);

	/**
	 * 공간정보 목록 토탈을 조회한다.,
	 * @param spaceSearch
	 * @return 공간정보 목록 토탈
	 */
	public int selectSpaceListTotal(SpaceSearch spaceSearch);

	/**
	 * 공간정보를 등록한다.
	 * @param spaceForm
	 * @return 등록결과
	 */
	public int insertSpace(Space spaceForm);

	/**
	 * 공간정보를 조회한다.
	 * @param spaceForm
	 * @return 공간정보
	 */
	public Space selectSpace(Space spaceForm);

	/**
	 * 공간정보를 수정한다.
	 * @param spaceForm
	 * @return 수정결과
	 */
	public int updateSpace(Space spaceForm);


}
