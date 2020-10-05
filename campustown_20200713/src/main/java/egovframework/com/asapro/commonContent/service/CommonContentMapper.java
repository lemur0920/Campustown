/**
 * 
 */
package egovframework.com.asapro.commonContent.service;

import java.util.List;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * 공통 콘텐츠 SQL 매퍼
 * @author yckim
 * @since 2018. 8. 23.
 *
 */
@Mapper
public interface CommonContentMapper {

	/**
	 * 공통 콘텐츠 목록을 반환한다.
	 * @param commonContentSearch
	 * @return 공통 콘텐츠 목록
	 */
	public List<CommonContent> selectCommonContentList(CommonContentSearch commonContentSearch);

	/**
	 * 공통 콘텐츠 목록 토탈 반환한다. 
	 * @param commonContentSearch
	 * @return 공통 콘텐츠 목록 토탈
	 */
	public int selectCommonContentListTotal(CommonContentSearch commonContentSearch);

	/**
	 * 공통 콘텐츠를 추가한다. 
	 * @param commonContentForm
	 * @return 추가결과
	 */
	public int insertCommonContent(CommonContent commonContentForm);

	/**
	 * 공통 콘텐츠를 조회한다. 
	 * @param commonContentForm
	 * @return 공통 콘텐츠
	 */
	public CommonContent selectCommonContent(CommonContent commonContentForm);

	/**
	 * 공통 콘텐츠를 수정한다. 
	 * @param commonContentForm
	 * @return 수정결과
	 */
	public int updateCommonContent(CommonContent commonContentForm);

	/**
	 * 공통 콘텐츠를 삭제한다.
	 * @param commonContent
	 * @return 삭제결과
	 */
	public int deleteCommonContent(CommonContent commonContent);

	//=======================================================================================================
	//============================  릴레이션을 통한 공통콘텐츠 관리    ==================================================
	//=======================================================================================================
	
	/**
	 * 공통 콘텐츠를 조회한다.
	 * @param commonContentRelation
	 * @return 공통콘텐츠
	 */
	public CommonContent selectCommonContentByRelation(CommonContentRelation commonContentRelation);

	/**
	 * 공통 콘텐츠 릴레이션을 추가한다.
	 * @param commonContentRelation
	 * @return 추가결과
	 */
	public int insertCommonContentRelation(CommonContentRelation commonContentRelation);

	/**
	 * 공통 콘텐츠 릴레이션을 삭제한다.
	 * @param commonContentRelation
	 * @return 삭제결과
	 */
	public int deleteCommonContentRelation(CommonContentRelation commonContentRelation);



}
