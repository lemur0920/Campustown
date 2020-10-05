/**
 * 
 */
package egovframework.com.asapro.menu.service;

import java.util.List;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

//import egovframework.com.asapro.advancedsearch.service.AdvancedSearch;
import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * 메뉴 SQL 매퍼
 * @author yckim
 * @since 2018. 7. 6.
 *
 */
@Mapper
public interface MenuMapper {
	
	/**
	 * 메뉴 목록을 조회한다.
	 * @param menuSearch
	 * @return 메뉴 목록
	 */
	//@Cacheable(value="menuCache")
	public List<Menu> selectMenuList(MenuSearch menuSearch);
	
	/**
	 * 메뉴 정보를 조회한다.
	 * @param menu
	 * @return 메뉴 정보
	 */
	//@Cacheable(value="menuCache")
	public Menu selectMenu(Menu menu);
	
	/**
	 * 메뉴을 추가한다.
	 * @param menu
	 * @return PK
	 */
	//@CacheEvict(value="menuCache", allEntries=true)
	public int insertMenu(Menu menu);
	
	/**
	 * 메뉴정보를 수정한다.
	 * @param menu
	 * @return 수정결과
	 */
	//@CacheEvict(value="menuCache", allEntries=true)
	public int updateMenu(Menu menu);
	
	/**
	 * 메뉴을 삭제한다.
	 * @param menuId
	 * @return 삭제 결과
	 */
	//@CacheEvict(value="menuCache", allEntries=true)
	public int deleteMenu(Menu menu);

	/**
	 * 메뉴 뎁스를 수정한다.
	 * @param member
	 */
	//@CacheEvict(value="menuCache", allEntries=true)
	public int updateMenuDepth(Menu member);

	/**
	 * 메뉴 정렬 순서를 수정한다.
	 * @param target
	 * @return 수정 결과
	 */
	//@CacheEvict(value="menuCache", allEntries=true)
	public int updateMenuOrder(Menu menu);

	/**
	 * 메뉴 뎁스, 부모메뉴, 정렬 순서를 수정한다.
	 * @param menu
	 * @return 수정결과
	 */
	//@CacheEvict(value="menuCache", allEntries=true)
	public int updateMenuRebuild(Menu menu);

	/**
	 * uri 로메뉴를 찾는다.
	 * @param uri
	 * @return 메뉴
	 */
	//@Cacheable(value="menuCache")
	public Menu findMenuByUri(MenuSearch menuSearch);

	/**
	 * 사이트 검색페이지용 콘텐츠 메뉴목록을 조회한다.
	 * @param menuSearch
	 * @return 메뉴 목록
	 */
	//@Cacheable(value="menuCache")
	public List<Menu> selectContentMenuList(MenuSearch menuSearch);

	/**
	 * 사이트 검색페이지용 콘텐츠 메뉴목록 개수를 조회한다.
	 * @param menuSearch
	 * @return 수량
	 */
	//@Cacheable(value="menuCache")
	public int selectContentMenuListTotal(MenuSearch menuSearch);
	
}
