/**
 * 
 */
package egovframework.com.asapro.menu.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

//import egovframework.com.asapro.advancedsearch.service.AdvancedSearch;

/**
 * 메뉴 서비스
 * @author yckim
 * @since 2018. 7. 6.
 *
 */
public interface MenuService {
	
	/**
	 * 메뉴 목록을 조회한다.
	 * @param menuSearch
	 * @return 메뉴 목록
	 */
	public List<Menu> getMenuList(MenuSearch menuSearch);
	
	/**
	 * 메뉴 정보를 조회한다.
	 * @param menu
	 * @return 메뉴 정보
	 */
	public Menu getMenu(Menu menu);
	
	/**
	 * 메뉴을 추가한다.
	 * @param menu
	 * @return PK
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	public String insertMenu(Menu menu);
	
	/**
	 * 메뉴정보를 수정한다.
	 * @param menu
	 * @return 수정결과
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	public int updateMenu(Menu menu) throws FileNotFoundException, IOException;
	
	/**
	 * 메뉴을 삭제한다.
	 * @param menuIds
	 * @return 삭제 결과
	 * @throws FileNotFoundException 
	 */
	public int deleteMenu(String[] menuIds) throws FileNotFoundException;

	/**
	 * 메뉴 트리구조와 정렬 순서를 수정한다.
	 * @param menuList
	 * @return 수정결과
	 */
	public int updateMenuRebuild(List<Menu> menuList);

	/**
	 * 메뉴 정렬 순서를 수정한다.
	 * @param menus
	 * @return 수정 결과
	 */
	public int updateMenuOrders(List<Menu> menus);

	/**
	 * 메뉴을 삭제한다.
	 * @param menuId
	 * @return 삭제 결과
	 * @throws FileNotFoundException 
	 */
	public int deleteMenu(Menu menu) throws FileNotFoundException;

	//=====================================================================================	
	//=====================================================================================	
	//=====================================================================================	
	
	/**
	 * 레이아웃파일 목록을 반환한다.
	 * @return 레이아웃파일 목록
	 * @throws FileNotFoundException 
	 */
	public List<String> getLayoutFileList() throws FileNotFoundException;
	
	/**
	 * 콘텐츠메뉴 템플릿파일 목록을 반환한다.
	 * @return 템플릿파일 목록
	 * @throws FileNotFoundException 
	 */
	public List<String> getContentTemplateFileList() throws FileNotFoundException;
	
	/**
	 * 부모메뉴를 찾아서 반환한다.
	 * <pre>
	 * -gnb.jsp, snb.jsp 에서 불러서 사용한다.
	 * -수정하면 두곳 다 영향받으므로 확인 후 적용해야함.
	 * </pre>
	 * @param menuList
	 * @param child
	 * @return 부모메뉴
	 */
	public Menu findParentMenu(List<Menu> menuList, Menu child);
	
	/**
	 * 현재메뉴의 부모메뉴를 뒤져서 현재on상태인 메뉴 + 자신 만 걸러서 반환한다.
	 * <pre>
	 * -현재선택된 메뉴부터 최상위 메뉴까지 부모를 거슬러 올라가는 개념이라고 보면 됨
	 * -gnb.jsp, snb.jsp 에서 불러서 사용한다.
	 * -수정하면 두곳 다 영향받으므로 확인 후 적용해야함.
	 * </pre>
	 * @param menuList
	 * @param currentMenu
	 * @return 현재메뉴의 계층별 라인에 해당하는 메뉴들
	 */
	public List<Menu> getCurrentMenuList(List<Menu> menuList, Menu currentMenu);
	
	/**
	 * 현재 메뉴의 최고 조상 메뉴(1차 메뉴)를 구해서 반환한다.
	 * <pre>
	 * - snb.jsp 에서 사용한다.(gnb.jsp 에서는 사용 안함)
	 * </pre>
	 * @param menuList
	 * @param menu
	 * @return
	 */
	public Menu getTopAncestorMenu(List<Menu> menuList, Menu menu);

	/**
	 * url에서 메뉴정보를 추출해 낸다.
	 * @param requestUri
	 * @return
	 */
	public Menu parseMenuInformationFromUrl(String requestUri);
	
	/**
	 * 주소로 현재 메뉴를 구한다.
	 * @param uri
	 * @return
	 */
	public Menu findMenuByUri(String uri);

	/**
	 * 사이트 메뉴를 검색한다.(사이트검색용)
	 * @param menuSearch
	 * @return 콘텐츠 메뉴 목록
	 */
	public List<Menu> getContentMenuList(MenuSearch menuSearch);

	/**
	 * 사이트 메뉴 검색 결과 개수를 반환한다.
	 * @param menuSearch
	 * @return 메뉴 개수
	 */
	public int getContentMenuListTotal(MenuSearch menuSearch);

}
