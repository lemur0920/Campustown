/**
 * 
 */
package egovframework.com.asapro.content.service;

import java.util.List;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

import egovframework.com.asapro.menu.service.Menu;
import egovframework.com.asapro.menu.service.MenuContentRelation;
import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * 콘텐츠 SQL 매퍼
 * @author yckim
 * @since 2018. 7. 20.
 *
 */
@Mapper
public interface ContentMapper {

	/**
	 * 콘텐츠를 추가한다.
	 * @param content
	 * @return 
	 */
	//@CacheEvict(value="contentCache", allEntries=true)
	public int insertContent(Content content);

	/**
	 * 콘텐츠를 조회한다.
	 * @param menuId
	 * @return 콘텐츠
	 */
	//@Cacheable(value="contentCache")
	public Content selectContent(Content content);

	/**
	 * 콘텐츠 목록을 조회한다.
	 * @param contentSearch
	 * @return 콘텐츠 목록
	 */
	//@Cacheable(value="contentCache")
	public List<Content> selectContentList(ContentSearch contentSearch);

	/**
	 * 콘텐츠 목록 토탈을 조회한다.
	 * @param revisionListSearch
	 * @return 목록 토탈
	 */
	//@Cacheable(value="contentCache")
	public int selectContentListTotal(ContentSearch revisionListSearch);

	/**
	 * 콘텐츠를 수정한다.
	 * @param contentForm
	 * @return 수정 결과
	 */
	//@CacheEvict(value="contentCache", allEntries=true)
	public int updateContent(Content contentForm);

	/**
	 * 콘텐츠를 삭제한다.
	 * @param content
	 * @return 삭제결과
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
	public Content selectContentByRel(MenuContentRelation menuContentRelation);

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
