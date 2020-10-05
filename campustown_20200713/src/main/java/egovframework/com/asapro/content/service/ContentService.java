/**
 * 
 */
package egovframework.com.asapro.content.service;

import java.util.List;

import egovframework.com.asapro.menu.service.MenuContentRelation;

/**
 * 콘텐츠 서비스
 * @author yckim
 * @since 2018. 7. 20.
 *
 */
public interface ContentService {
	
	/**
	 * 콘텐츠를 조회한다.(1개)
	 * @param menuId
	 * @return 콘텐츠
	 */
	public Content getContent(Content content);
	
	/**
	 * 콘텐츠 목록을 조회한다.
	 * @param contentSearch
	 * @return 콘텐츠 목록
	 */
	public List<Content> getContentList(ContentSearch contentSearch);
	
	/**
	 * 콘텐츠 목록 토탈를 조회한다.
	 * @param revisionListSearch
	 * @return 목록 토탈
	 */
	public int getContentListTotal(ContentSearch revisionListSearch);
	
	/**
	 * 콘텐츠를 추가한다.
	 * @param content
	 */
	public int insertContent(Content content);

	/**
	 * 콘텐츠를 수정한다.
	 * @param contentForm
	 * @return
	 */
	public int updateContent(Content contentForm);

	/**
	 * 콘텐츠 내용을 과거버전으로 복구한다.
	 * @param contentRestoreForm
	 * @return 복구결과
	 */
	public int restoreContent(Content contentRestoreForm);

	/**
	 * 콘텐츠를 삭제한다.
	 * @param contentList
	 * @return 삭제 결과
	 */
	public int deleteContents(List<Content> contentList);

	/**
	 * 콘텐츠를 삭제한다.
	 * @param content
	 * @return 삭제 결과
	 */
	public int deleteContent(Content content);

	
	//======================================================================================================
	//=================================== 릴레이션을 이용한 콘텐츠 관리 ==========================================================
	//======================================================================================================
	/**
	 * 릴레이션을 이용해 콘텐츠를 조회한다.
	 * @param menuContentRelation
	 * @return 콘텐츠
	 */
	public Content getContentByRel(MenuContentRelation menuContentRelation);
	
	/**
	 * 메뉴-콘텐트 릴레이션을 추가한다.
	 * @param menuContentRelation
	 * @return 추가결과
	 */
	public int insertMenuContentRel(MenuContentRelation menuContentRelation);
	
	/**
	 * 메뉴-콘텐트 릴레이션을 삭제한다.
	 * @param menuContentRelation
	 * @return 삭제결과
	 */
	public int deleteMenuContentRel(MenuContentRelation menuContentRelation);
	
	
}
