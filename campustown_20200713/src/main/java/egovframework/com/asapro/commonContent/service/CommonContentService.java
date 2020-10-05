/**
 * 
 */
package egovframework.com.asapro.commonContent.service;

import java.util.List;

/**
 * 공통 콘텐츠 서비스
 * @author yckim
 * @since 2018. 8. 23.
 *
 */
public interface CommonContentService {

	/**
	 * 공통 콘텐츠 목록을 반환한다.
	 * @param commonContentSearch
	 * @return 공통 콘텐츠 목록
	 */
	public List<CommonContent> getCommonContentList(CommonContentSearch commonContentSearch);

	/**
	 * 공통 콘텐츠 목록 토탈 반환한다.
	 * @param commonContentSearch
	 * @return 공통 콘텐츠 목록 토탈
	 */
	public int getCommonContentListTotal(CommonContentSearch commonContentSearch);

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
	public CommonContent getCommonContent(CommonContent commonContentForm);

	/**
	 * 공통 콘텐츠를 수정한다.
	 * @param commonContentForm
	 * @return 수정결과
	 */
	public int updateCommonContent(CommonContent commonContentForm);

	/**
	 * 공통 콘텐츠를 삭제한다.
	 * @param commonContentList
	 * @return 삭제결과
	 */
	public int deleteCommonContent(List<CommonContent> commonContentList);

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
	 * 공통 콘텐츠를 조회한다.(By Relation)
	 * @param commonContentRelation
	 * @return 공통콘텐츠
	 */
	
	public CommonContent getCommonContentByRelation(CommonContentRelation commonContentRelation);

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
