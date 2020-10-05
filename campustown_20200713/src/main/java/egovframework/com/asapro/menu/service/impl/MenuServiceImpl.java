/**
 * 
 */
package egovframework.com.asapro.menu.service.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.SystemUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//import egovframework.com.asapro.advancedsearch.service.AdvancedSearch;
//import egovframework.com.asapro.archive.service.Archive;
//import egovframework.com.asapro.archive.service.ArchiveCategory;
//import egovframework.com.asapro.archive.service.ArchiveService;
//import egovframework.com.asapro.archive.service.ArchiveTag;
//import egovframework.com.asapro.comment.service.CommentMapper;
//import egovframework.com.asapro.comment.service.CommentSearch;
import egovframework.com.asapro.config.service.ConfigService;
import egovframework.com.asapro.content.service.Content;
import egovframework.com.asapro.content.service.ContentMapper;
import egovframework.com.asapro.fileinfo.service.FileInfoService;
import egovframework.com.asapro.member.admin.service.AdminMember;
import egovframework.com.asapro.menu.service.Menu;
import egovframework.com.asapro.menu.service.MenuContentRelation;
import egovframework.com.asapro.menu.service.MenuMapper;
import egovframework.com.asapro.menu.service.MenuSearch;
import egovframework.com.asapro.menu.service.MenuService;
import egovframework.com.asapro.satisfaction.service.Satisfaction;
import egovframework.com.asapro.satisfaction.service.SatisfactionMapper;
import egovframework.com.asapro.site.service.Site;
import egovframework.com.asapro.support.Constant;
import egovframework.com.asapro.support.annotation.CurrentMenu;
import egovframework.com.asapro.support.util.AsaproUtils;
import egovframework.com.asapro.support.util.EtcUtil;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

/**
 * 메뉴 서비스 구현
 * @author yckim
 * @since 2018. 7. 6.
 *
 */
@Service
public class MenuServiceImpl extends EgovAbstractServiceImpl implements MenuService{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(MenuServiceImpl.class);
	
	@Autowired
	private MenuMapper menuMapper;
	
	@Autowired
	private ContentMapper contentMapper;
	
//	@Autowired
//	private CommentMapper commentMapper; 
	
	@Autowired
	private SatisfactionMapper satisfactionMapper;
	
	@Autowired
	private FileInfoService fileInfoService;
	
	@Autowired
	private HttpServletRequest request;
	
	@Autowired
	private ConfigService configService;
	
//	@Autowired
//	private ArchiveService archiveService;
	
	
	/**
	 * 메뉴 목록
	 */
	@Override
	public List<Menu> getMenuList(MenuSearch menuSearch) {
		List<Menu> list = menuMapper.selectMenuList(menuSearch);

		//부모메뉴는 아이디만 있으므로 값을 가진 객체로 교체해 준다.
		for(Menu temp: list){
			//컨텍스트 패스를 붙여준다
			temp.setMenuLink(Constant.CONTEXT_PATH + temp.getMenuLink());
			
			if( temp.getMenuParent() != null ){
				for(Menu temp2: list){
					if( temp2.equals(temp.getMenuParent()) ){
						temp.setMenuParent(temp2);
						break;
					}
				}
			}
		}
		
		//순서 정렬결과 담을 리스트
		List<Menu> sortedList = new ArrayList<Menu>(); 
		
		int size = list.size();
		int maxDepth = 0;
		
		Site currentSite = (Site) request.getAttribute("currentSite");
		//미리보기할 때에도 리퀘스트에 있는 theme 에 theme 값을 세팅하므로 사이트정보에서 불러오면 미리보기가 안됨.
		String theme = (String) request.getAttribute("theme");
		//미리보기면 강제로 텍스트메뉴로 출력
		boolean forceTextMenu = StringUtils.isNotBlank(request.getParameter("themePreview"));
		//메뉴이미지url채워넣음
		for(int i = 0; i< size; i++){
			list.set(i, list.get(i).populateMenuImageUrl(currentSite.getSiteId(), theme, forceTextMenu));
		}
		
		//최대 뎁스를 구하고
		for(int i = 0; i< size; i++){
			if(list.get(i).getMenuDepth() > maxDepth){
				maxDepth = list.get(i).getMenuDepth();
			}
		}
		//최대 뎁스까지 루프
		for(int depth = 1; depth<=maxDepth; depth++){
			for(Menu parent : list){
				//부모부터 차례대로 구해나감 
				if(parent.getMenuDepth() == depth){
					if(!sortedList.contains(parent)){
						sortedList.add(parent);
					}
					//순차적으로 재귀탐색해서 정렬
					menuLoop(list, sortedList, parent);
				}
			}
		}
		
		return sortedList;
	}

	/**
	 * 순차적으로 재귀탐색해서 정렬
	 * @param list
	 * @param sortedList
	 * @param parent
	 */
	private void menuLoop(List<Menu> list, List<Menu> sortedList, Menu parent) {
		for(Menu child : list){
			//1뎁스 메뉴은 pass
			if( child.getMenuParent() == null ){
				continue;
			}
				
			//자식만 골라서
			if( parent.getMenuId().equals(child.getMenuParent().getMenuId()) ){
				if(!sortedList.contains(child)){
					sortedList.add(child);
					menuLoop(list, sortedList, child);
				}
			}
		}
	}

	/**
	 * 메뉴 정보 조회
	 */
	@Override
	public Menu getMenu(Menu menu) {
		//미리보기 상태일 수도 있으니까 리퀘스트에서 받자
		String theme = (String) request.getAttribute("theme");
		boolean forceTextMenu = StringUtils.isNotBlank(request.getParameter("themePreview"));
		Menu fromDB = menuMapper.selectMenu(menu); 
		if( fromDB != null ){
			fromDB = fromDB.populateMenuImageUrl(menu.getSiteId(), theme, forceTextMenu);
			//컨텍스트 패스를 붙여준다
			fromDB.setMenuLink(Constant.CONTEXT_PATH + fromDB.getMenuLink());
		} 
		return fromDB;
	}
	
	/**
	 * 메뉴 추가
	 */
	@SuppressWarnings("deprecation")
	@Override
	public String insertMenu(Menu menuForm){
		Site currentSite = (Site) request.getAttribute("currentSite");
		int menuOrder = 0;
		MenuSearch menuSearch = null;
		
		if( menuForm.getMenuParent() == null || StringUtils.isBlank(menuForm.getMenuParent().getMenuId()) ){
			//부모가 없으면 1뎁스
			menuForm.setMenuDepth(1);
			//부모가 없으면 1뎁스 엘리먼트 개수 카운트
			menuSearch = new MenuSearch();
			menuSearch.setMenuDepth(1);
			menuSearch.setSitePrefix(menuForm.getSitePrefix());
			List<Menu> siblings = menuMapper.selectMenuList(menuSearch);
			menuOrder = siblings.size();
		} else {
			Menu parentSearch = new Menu();
			parentSearch.setSitePrefix(menuForm.getSitePrefix());
			parentSearch.setMenuId(menuForm.getMenuParent().getMenuId());
			Menu parent = menuMapper.selectMenu(parentSearch);
			//부모가 있으면 부모뎁스 + 1
			menuForm.setMenuDepth(parent.getMenuDepth() + 1);
			//부모가 있으면 현재뎁스의 같은 부모인 엘리먼트 개수 카운트
			List<Menu> siblings = parent.getMenuChildren();
			if( siblings != null ){
				menuOrder = siblings.size();
			} else {
				menuOrder = 0;
			}
		}
		
		//메뉴 로케이션 저장 -> 검색엔진 제공위해
		MenuSearch menuTopSearch = new MenuSearch();
		menuTopSearch.setSitePrefix(currentSite.getSitePrefix());
		List<Menu> menuList = getMenuList(menuTopSearch);
		//Menu topMenu = getTopAncestorMenu(menuList, menuForm);
		//menuForm.setMenuTopId(topMenu.getMenuId());
		
		String menuLocation = menuForm.getMenuName();
		Menu thisMenu = menuForm;
		for(int i = 0 ; i < menuForm.getMenuDepth()-1; i++){
			for( Menu parentCandidate : menuList ){
				if( thisMenu.getMenuParent().equals(parentCandidate) ){
					menuLocation = parentCandidate.getMenuName() + " > " + menuLocation;
					thisMenu = parentCandidate;
					break;
				}
			}
		}
		//menuLocation = currentSite.getSiteName() + " > " + menuLocation;
		menuForm.setMenuLocation(menuLocation);
		
		//뒤에 붙는 슬래시 제거
		menuForm.setMenuLink( StringUtils.removeEnd(menuForm.getMenuLink(), "/") );
		//앞에 컨텍스트 패스 제거
		menuForm.setMenuLink(StringUtils.removeStart(menuForm.getMenuLink(), Constant.CONTEXT_PATH) );
		menuForm.setMenuOrder(menuOrder);
		menuForm.setMenuRegDate(new Date());
		menuForm.setMenuLastModified(menuForm.getMenuRegDate());
		
		//===== 메뉴 이미지 =====
		//Member currentUser = (Member) request.getSession().getAttribute("currentUser");
		String themeMenuImageDir = AsaproUtils.getSiteContextUploadDirectory(request) + "/theme/" + currentSite.getSiteTheme() + "/menu";
		//메인메뉴 on 이미지
		if( menuForm.getMenuGnbImageOnFile() != null && !menuForm.getMenuGnbImageOnFile().isEmpty() ){
			String ext = FilenameUtils.getExtension(menuForm.getMenuGnbImageOnFile().getOriginalFilename()).toLowerCase();
			if( StringUtils.isNotBlank(ext) ){
				ext = "." + ext;
			}
			ext = "_gnb_on" + ext;
			fileInfoService.saveFileOnly(menuForm.getMenuGnbImageOnFile(), new File(themeMenuImageDir + "/" + menuForm.getMenuId() + ext), true);
			menuForm.setMenuGnbExtOn(ext);
		}
		//메인메뉴 off 이미지
		if( menuForm.getMenuGnbImageOffFile() != null && !menuForm.getMenuGnbImageOffFile().isEmpty() ){
			String ext = FilenameUtils.getExtension(menuForm.getMenuGnbImageOffFile().getOriginalFilename()).toLowerCase();
			if( StringUtils.isNotBlank(ext) ){
				ext = "." + ext;
			}
			ext = "_gnb_off" + ext;
			fileInfoService.saveFileOnly(menuForm.getMenuGnbImageOffFile(), new File(themeMenuImageDir + "/" + menuForm.getMenuId() + ext), true);
			menuForm.setMenuGnbExtOff(ext);
		}
		//서브메뉴 on 이미지
		if( menuForm.getMenuSnbImageOnFile() != null && !menuForm.getMenuSnbImageOnFile().isEmpty() ){
			String ext = FilenameUtils.getExtension(menuForm.getMenuSnbImageOnFile().getOriginalFilename()).toLowerCase();
			if( StringUtils.isNotBlank(ext) ){
				ext = "." + ext;
			}
			ext = "_snb_on" + ext;
			fileInfoService.saveFileOnly(menuForm.getMenuSnbImageOnFile(), new File(themeMenuImageDir + "/" + menuForm.getMenuId() + ext), true);
			menuForm.setMenuSnbExtOn(ext);
		}
		//서브메뉴 off 이미지
		if( menuForm.getMenuSnbImageOffFile() != null && !menuForm.getMenuSnbImageOffFile().isEmpty() ){
			String ext = FilenameUtils.getExtension(menuForm.getMenuSnbImageOffFile().getOriginalFilename()).toLowerCase();
			if( StringUtils.isNotBlank(ext) ){
				ext = "." + ext;
			}
			ext = "_snb_off" + ext;
			fileInfoService.saveFileOnly(menuForm.getMenuSnbImageOffFile(), new File(themeMenuImageDir + "/" + menuForm.getMenuId() + ext), true);
			menuForm.setMenuSnbExtOff(ext);
		}
		//콘텐츠 타이틀 이미지
		if( menuForm.getMenuTitleImageFile() != null && !menuForm.getMenuTitleImageFile().isEmpty() ){
			String ext = FilenameUtils.getExtension(menuForm.getMenuTitleImageFile().getOriginalFilename()).toLowerCase();
			if( StringUtils.isNotBlank(ext) ){
				ext = "." + ext;
			}
			ext = "_title" + ext;
			fileInfoService.saveFileOnly(menuForm.getMenuTitleImageFile(), new File(themeMenuImageDir + "/" + menuForm.getMenuId() + ext), true);
			menuForm.setMenuTitleExt(ext);
		}
		//===== //메뉴 이미지 =====
		if( menuMapper.insertMenu(menuForm) == 1 ){
			
			/**
			 * 메뉴추가될때 메뉴-콘텐츠 릴레이션 추가
			 * 메뉴 타입이 content 인경우만 처리
			 */
			if("content".equals(menuForm.getMenuType()) ){
				if(menuForm.getContent() == null || menuForm.getContent().getContentRoot() == null){
					menuForm.getContent().setContentRoot(1); //콘텐츠 메뉴 인경우 콘텐츠를 선택하지 않은경우 '편집된지 않은 콘텐츠'를 기본 콘텐츠로 적용
				}
				MenuContentRelation menuContentRelation = new MenuContentRelation();
				menuContentRelation.setMenuId(menuForm.getMenuId());
				menuContentRelation.setSitePrefix(menuForm.getSitePrefix());
				menuContentRelation.setContentRoot(menuForm.getContent().getContentRoot());
				contentMapper.insertMenuContentRel(menuContentRelation);
			}
			
			/**
			 * 메뉴추가될때 만족도 테이블에 메뉴row추가
			 */
			Satisfaction satisfaction = new Satisfaction(menuForm.getMenuId(), menuForm.getSitePrefix(), new Date(), new Date());
			//satisfaction.setSitePrefix(menuForm.getSitePrefix());
			satisfactionMapper.insertSatisfaction(satisfaction);
			
			//구글 사이트맵 Sitemap.xml 생성한다.
			this.generateSitemapXml(menuForm.getSitePrefix());
			return menuForm.getMenuId();
		}
		
		return null;	
	}

	/**
	 * 메뉴 수정
	 */
	@SuppressWarnings("deprecation")
	@Override
	public int updateMenu(Menu menuForm) throws FileNotFoundException, IOException {
		Site currentSite = (Site) request.getAttribute("currentSite");
		int updated = 0;
		menuForm.setMenuLastModified(new Date());
		//뒤에 붙는 슬래시 제거
		menuForm.setMenuLink( StringUtils.removeEnd(menuForm.getMenuLink(), "/") );
		//앞에 컨텍스트 패스 제거
		menuForm.setMenuLink(StringUtils.removeStart(menuForm.getMenuLink(), Constant.CONTEXT_PATH) );
		
		//이동 전 부모 메뉴을 구한다.
		Menu fromDB = menuMapper.selectMenu(menuForm);
		Menu oldParent = fromDB.getMenuParent();
		if(oldParent != null){
			Menu parentSearch = new Menu();
			parentSearch.setSitePrefix(menuForm.getSitePrefix());
			parentSearch.setMenuId(fromDB.getMenuParent().getMenuId());
			oldParent = menuMapper.selectMenu(parentSearch);
		}
		
		//이동 후 부모 메뉴을 구한다.
		Menu newParent = menuForm.getMenuParent();
		if(newParent != null && newParent.getMenuId() != null){
			Menu parentSearch = new Menu();
			parentSearch.setSitePrefix(menuForm.getSitePrefix());
			parentSearch.setMenuId(menuForm.getMenuParent().getMenuId());
			newParent = menuMapper.selectMenu(parentSearch);
		}
		
		boolean isSameDepth = false;
		if( newParent != null && oldParent != null ){
			if( newParent.getMenuDepth() == oldParent.getMenuDepth() ){
				isSameDepth = true;
			}
		}
		
		//같은 뎁스상의 이동이면 뎁스 조정 하지 않는다.
		if( !isSameDepth ){
		
			//모든 메뉴목록을 가져와서
			MenuSearch menuSearch = new MenuSearch();
			menuSearch.setSitePrefix(menuForm.getSitePrefix());
			List<Menu> allMenus = menuMapper.selectMenuList(menuSearch);
			int myDepth = fromDB.getMenuDepth();
			int startDepth = myDepth + 1;
			int maxDepth = 0;
			for(Menu menu : allMenus){
				if(menu.getMenuDepth() > maxDepth){
					maxDepth = menu.getMenuDepth();
				}
			}
			
			//이동되는 전체 노드를 담을 리스트(자신의 자식메뉴 목록)
			List<Menu> movingFamily = new ArrayList<Menu>();
			//일단 자신을 담고
			movingFamily.add(fromDB);
			for(int depth = startDepth; depth <= maxDepth; depth++){
				for(Menu menu : allMenus){
					if(menu.getMenuDepth() == depth){
						//부모체크해서 걸러냄 
						if(movingFamily.contains(menu.getMenuParent())){
							movingFamily.add(menu);
						}
					}
				}
			}
			
			//for( Menu member : movingFamily ){
				//System.out.println("[ASAPRO] MenuServiceImpl memberID : " + member.getMenuId());
			//}
			
			int moved = 0;
			if(newParent == null){
				//최상위로 옮긴 경우
				moved = fromDB.getMenuDepth() - 1;
				moved = -moved;
			} else {
				if(oldParent == null){
					moved = newParent.getMenuDepth();
				} else {
					moved = newParent.getMenuDepth() - oldParent.getMenuDepth(); 
				}
			}
			
			for( Menu member : movingFamily ){
				member.setMenuDepth(member.getMenuDepth() + moved);
				member.setSitePrefix(menuForm.getSitePrefix());
				menuMapper.updateMenuDepth(member);
			}
			
		} else {
			LOGGER.info("[ASAPRO] IT IS SAME DEPTH MOVE...NO MENUS TO CHANGE DEPTH...");
		}
		
		//===== 메뉴 이미지 =====
		//Member currentUser = (Member) request.getSession().getAttribute("currentUser");
		String themeMenuImageDir = AsaproUtils.getSiteUploadDirectory(request) + "/theme/" + currentSite.getSiteTheme() + "/menu";
		
		//메뉴 이미지 삭제 체크박스
		if( menuForm.isMenuGnbImageOnDelete() ){
			fileInfoService.deleteFile(themeMenuImageDir + "/" + menuForm.getMenuId() + fromDB.getMenuGnbExtOn());
			menuForm.setMenuGnbExtOn("");
		}
		if( menuForm.isMenuGnbImageOffDelete() ){
			fileInfoService.deleteFile(themeMenuImageDir + "/" + menuForm.getMenuId() + fromDB.getMenuGnbExtOff());
			menuForm.setMenuGnbExtOff("");
		}
		if( menuForm.isMenuSnbImageOnDelete() ){
			fileInfoService.deleteFile(themeMenuImageDir + "/" + menuForm.getMenuId() + fromDB.getMenuSnbExtOn());
			menuForm.setMenuSnbExtOn("");
		}
		if( menuForm.isMenuSnbImageOffDelete() ){
			fileInfoService.deleteFile(themeMenuImageDir + "/" + menuForm.getMenuId() + fromDB.getMenuSnbExtOff());
			menuForm.setMenuSnbExtOff("");
		}
		if( menuForm.isMenuTitleImageDelete() ){
			fileInfoService.deleteFile(themeMenuImageDir + "/" + menuForm.getMenuId() + fromDB.getMenuTitleExt());
			menuForm.setMenuTitleExt("");
		}
		
		//메인메뉴 on 이미지
		if( menuForm.getMenuGnbImageOnFile() != null && !menuForm.getMenuGnbImageOnFile().isEmpty() ){
			String ext = FilenameUtils.getExtension(menuForm.getMenuGnbImageOnFile().getOriginalFilename()).toLowerCase();
			if( StringUtils.isNotBlank(ext) ){
				ext = "." + ext;
			}
			ext = "_gnb_on" + ext;
			fileInfoService.saveFileOnly(menuForm.getMenuGnbImageOnFile(), new File(themeMenuImageDir + "/" + menuForm.getMenuId() + ext), true);
			menuForm.setMenuGnbExtOn(ext);
		}
		//메인메뉴 off 이미지
		if( menuForm.getMenuGnbImageOffFile() != null && !menuForm.getMenuGnbImageOffFile().isEmpty() ){
			String ext = FilenameUtils.getExtension(menuForm.getMenuGnbImageOffFile().getOriginalFilename()).toLowerCase();
			if( StringUtils.isNotBlank(ext) ){
				ext = "." + ext;
			}
			ext = "_gnb_off" + ext;
			fileInfoService.saveFileOnly(menuForm.getMenuGnbImageOffFile(), new File(themeMenuImageDir + "/" + menuForm.getMenuId() + ext), true);
			menuForm.setMenuGnbExtOff(ext);
		}
		//서브메뉴 on 이미지
		if( menuForm.getMenuSnbImageOnFile() != null && !menuForm.getMenuSnbImageOnFile().isEmpty() ){
			String ext = FilenameUtils.getExtension(menuForm.getMenuSnbImageOnFile().getOriginalFilename()).toLowerCase();
			if( StringUtils.isNotBlank(ext) ){
				ext = "." + ext;
			}
			ext = "_snb_on" + ext;
			fileInfoService.saveFileOnly(menuForm.getMenuSnbImageOnFile(), new File(themeMenuImageDir + "/" + menuForm.getMenuId() + ext), true);
			menuForm.setMenuSnbExtOn(ext);
		}
		//서브메뉴 off 이미지
		if( menuForm.getMenuSnbImageOffFile() != null && !menuForm.getMenuSnbImageOffFile().isEmpty() ){
			String ext = FilenameUtils.getExtension(menuForm.getMenuSnbImageOffFile().getOriginalFilename()).toLowerCase();
			if( StringUtils.isNotBlank(ext) ){
				ext = "." + ext;
			}
			ext = "_snb_off" + ext;
			fileInfoService.saveFileOnly(menuForm.getMenuSnbImageOffFile(), new File(themeMenuImageDir + "/" + menuForm.getMenuId() + ext), true);
			menuForm.setMenuSnbExtOff(ext);
		}
		//콘텐츠 타이틀 이미지
		if( menuForm.getMenuTitleImageFile() != null && !menuForm.getMenuTitleImageFile().isEmpty() ){
			String ext = FilenameUtils.getExtension(menuForm.getMenuTitleImageFile().getOriginalFilename()).toLowerCase();
			if( StringUtils.isNotBlank(ext) ){
				ext = "." + ext;
			}
			ext = "_title" + ext;
			fileInfoService.saveFileOnly(menuForm.getMenuTitleImageFile(), new File(themeMenuImageDir + "/" + menuForm.getMenuId() + ext), true);
			menuForm.setMenuTitleExt(ext);
		}
		//===== //메뉴 이미지 =====
		
		
		//메뉴 로케이션 저장 -> 검색엔진 제공위해
		MenuSearch menuTopSearch = new MenuSearch();
		menuTopSearch.setSitePrefix(currentSite.getSitePrefix());
		List<Menu> menuList = getMenuList(menuTopSearch);
		//Menu topMenu = getTopAncestorMenu(menuList, menuForm);
		//menuForm.setMenuTopId(topMenu.getMenuId());
		
		Menu newFromDB = menuMapper.selectMenu(menuForm);
		String menuLocation = menuForm.getMenuName();
		Menu thisMenu = menuForm;
		for(int i = 0 ; i < newFromDB.getMenuDepth()-1; i++){
			for( Menu parentCandidate : menuList ){
				if( thisMenu.getMenuParent().equals(parentCandidate) ){
					menuLocation = parentCandidate.getMenuName() + " > " + menuLocation;
					thisMenu = parentCandidate;
					break;
				}
			}
		}
		//menuLocation = currentSite.getSiteName() + " > " + menuLocation;
		menuForm.setMenuLocation(menuLocation);
		
		updated = menuMapper.updateMenu(menuForm);
		
		/**
		 * 메뉴수정될때 메뉴-콘텐츠 릴레이션 추가
		 * 메뉴 타입이 content 인경우만 처리
		 */
		MenuContentRelation menuContentRelation = new MenuContentRelation();
		menuContentRelation.setMenuId(menuForm.getMenuId());
		menuContentRelation.setSitePrefix(menuForm.getSitePrefix());
		
		//일단 릴레이션은 삭제
		contentMapper.deleteMenuContentRel(menuContentRelation);
		
		if("content".equals(menuForm.getMenuType()) ){
			if(menuForm.getContent() == null || menuForm.getContent().getContentRoot() == null){
				menuForm.getContent().setContentRoot(1); //콘텐츠 메뉴 인경우 콘텐츠를 선택하지 않은경우 '편집된지 않은 콘텐츠'를 기본 콘텐츠로 적용
			}
			menuContentRelation.setContentRoot(menuForm.getContent().getContentRoot());
			contentMapper.insertMenuContentRel(menuContentRelation);
		}
		
		//구글 사이트맵 Sitemap.xml 생성한다.
		this.generateSitemapXml(menuForm.getSitePrefix());
		
		return updated; 
	}
	
	/**
	 * 구글 사이트맵 Sitemap.xml 생성한다.
	 * @param sitePrefix
	 */
	//참고 : https://support.google.com/webmasters/answer/156184?hl=ko
	//참고 : https://support.google.com/webmasters/answer/183668
	private void generateSitemapXml(String sitePrefix){
		MenuSearch menuSearch = new MenuSearch();
		menuSearch.setSitePrefix(sitePrefix);
		List<Menu> menuList = menuMapper.selectMenuList(menuSearch);
		//SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss Z", Locale.US);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.KOREA);
		SimpleDateFormat sdf2 = new SimpleDateFormat("Z", Locale.KOREA);
		String baseUri = AsaproUtils.getSchemeDomainPort(request);
		StringBuilder sb = new StringBuilder(10000);
		sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		sb.append(SystemUtils.LINE_SEPARATOR);
		sb.append("<urlset xmlns=\"http://www.sitemaps.org/schemas/sitemap/0.9\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.sitemaps.org/schemas/sitemap/0.9 http://www.sitemaps.org/schemas/sitemap/0.9/sitemap.xsd\">");
		sb.append(SystemUtils.LINE_SEPARATOR);
		sb.append("	<url>");
		sb.append(SystemUtils.LINE_SEPARATOR);
			sb.append("		<loc>");
				sb.append(StringUtils.removeEnd(baseUri, "/"));
			sb.append("/</loc> ");
			sb.append(SystemUtils.LINE_SEPARATOR);
		sb.append("	</url>");
		sb.append(SystemUtils.LINE_SEPARATOR);
		//메뉴
		for( Menu menu : menuList ){
			if( menu.getMenuLink().contains("/mypage/") ){
				continue;
			}
			if( "public".equals(menu.getMenuStatus()) ){
				sb.append("	<url>");
				sb.append(SystemUtils.LINE_SEPARATOR);
					sb.append("		<loc>");
						sb.append(baseUri);
						sb.append(Constant.CONTEXT_PATH + menu.getMenuLink());
					sb.append("</loc> ");
					if( menu.getMenuLastModified() != null ){
						sb.append(SystemUtils.LINE_SEPARATOR);
						sb.append("		<lastmod>");
							sb.append(sdf.format(menu.getMenuLastModified()));
							sb.append(sdf2.format(menu.getMenuLastModified()).substring(0, 3));
							sb.append(":");
							sb.append(sdf2.format(menu.getMenuLastModified()).substring(3, 5));
						sb.append("</lastmod>");
					}
					sb.append(SystemUtils.LINE_SEPARATOR);
				sb.append("	</url>");
				sb.append(SystemUtils.LINE_SEPARATOR);
			}
		}
		//TODO:아카이브
		sb.append("</urlset>");
		try {
			FileUtils.writeStringToFile(new File(AsaproUtils.getWebRoot(request) + "sitemap/Sitemap_" + sitePrefix.toLowerCase() + ".xml"), sb.toString(), "UTF-8");
		} catch (FileNotFoundException e) {
			LOGGER.error("[FileNotFoundException] Try/Catch... : "+ e.getMessage());
		} catch (IOException e) {
			LOGGER.error("[IOException] Try/Catch... : "+ e.getMessage());
		}
	}
	
	/**
	 * 메뉴 삭제 - 안씀
	 */
	@Override
	public int deleteMenu(String[] menuIds) throws FileNotFoundException {
		int deleted = 0;
		/*
		for(String menuId : menuIds){
			deleted += menuMapper.deleteMenu(menuId);
			
			if( deleted > 0 ){
				//메뉴 삭제되면 콘텐츠도 같이 지워준다.
				contentMapper.deleteContentByMenuId(menuId);
			}
		}
		*/
		return deleted;
	}

	/**
	 * 메뉴 트리구조와 정렬 순서를 수정한다.
	 */
	@Override
	public int updateMenuRebuild(List<Menu> menuList) {
		int result = 0;
		Site currentSite = (Site) request.getAttribute("currentSite");
		
		//디비 데이터와 비교해서 순서 바뀐것만 수정쿼리 날리도록
		for(Menu menu : menuList){
			menu.setSitePrefix(currentSite.getSitePrefix());
			result += menuMapper.updateMenuRebuild(menu);
		}
		//구글 사이트맵 Sitemap.xml 생성한다.
		//this.generateSitemapXml(currentSite.getSitePrefix());
		
		LOGGER.info("[ASAPRO] menu rebuild update fired ");
		
		return result;
	}

	/**
	 * 순서 저장
	 */
	@Override
	public int updateMenuOrders(List<Menu> menus) {
		int result = 0;
		Site currentSite = (Site) request.getAttribute("currentSite");
		MenuSearch menuSearch = new MenuSearch();
		menuSearch.setSitePrefix(currentSite.getSitePrefix());
		List<Menu> allMenus = menuMapper.selectMenuList(menuSearch);
		
		//디비 데이터와 비교해서 순서 바뀐것만 수정쿼리 날리도록
		for(Menu menu : allMenus){
			for(Menu target : menus){
				if(menu.getMenuId().equals(target.getMenuId())){
					if(menu.getMenuOrder() != target.getMenuOrder()){
						target.setSitePrefix(currentSite.getSitePrefix());
						result += menuMapper.updateMenuOrder(target);
						LOGGER.info("[ASAPRO] menu reorder update fired ");
					}
				}
			}
		}
		
		return result;
	}

	/**
	 * 메뉴 삭제
	 */
	@Override
	public int deleteMenu(Menu menu) throws FileNotFoundException {
		Menu fromDB = menuMapper.selectMenu(menu);
		if(fromDB.getMenuChildren() != null && fromDB.getMenuChildren().size() >= 1){
			return -99;//자식이 있어서 삭제할 수 없다.
		}
		
		int deleted = menuMapper.deleteMenu(menu);
		if( deleted > 0 ){
			
			//메뉴 삭제되면 콘텐츠 릴레이션도 같이 지워준다.
			MenuContentRelation menuContentRelation = new MenuContentRelation();
			menuContentRelation.setMenuId(menu.getMenuId());
			menuContentRelation.setSitePrefix(menu.getSitePrefix());
			contentMapper.deleteMenuContentRel(menuContentRelation);
			
			//===== 메뉴이미지 파일도 지워준다=====
			Site currentSite = (Site) request.getAttribute("currentSite");
			String themeMenuImageDir = AsaproUtils.getSiteUploadDirectory(request) + "/theme/" + currentSite.getSiteTheme() + "/menu";
			
			//메뉴 이미지 삭제 체크박스
			fileInfoService.deleteFile(themeMenuImageDir + "/" + fromDB.getMenuId() + fromDB.getMenuGnbExtOn());
			fileInfoService.deleteFile(themeMenuImageDir + "/" + fromDB.getMenuId() + fromDB.getMenuGnbExtOff());
			fileInfoService.deleteFile(themeMenuImageDir + "/" + fromDB.getMenuId() + fromDB.getMenuSnbExtOn());
			fileInfoService.deleteFile(themeMenuImageDir + "/" + fromDB.getMenuId() + fromDB.getMenuSnbExtOff());
			fileInfoService.deleteFile(themeMenuImageDir + "/" + fromDB.getMenuId() + fromDB.getMenuTitleExt());
			//===== 메뉴이미지 파일도 지워준다=====
			
			//댓글도 지워준다
//			CommentSearch commentSearch = new CommentSearch();
//			commentSearch.setSitePrefix(menu.getSitePrefix());
//			commentSearch.setMenuId(fromDB.getMenuId());
//			commentMapper.deleteCommentsOfMenu(commentSearch);
			
			//만족도도 지워준다
			Satisfaction satisfaction = new Satisfaction();
			satisfaction.setSitePrefix(menu.getSitePrefix());
			satisfaction.setMenuId(fromDB.getMenuId());
			satisfactionMapper.deleteSatisfaction(satisfaction);
		}
		return deleted;
	}

	
	//===========================================================================================
	//===========================================================================================
	//===========================================================================================
	
	/**
	 * 레이아웃파일목록
	 */
	@Override
	public List<String> getLayoutFileList() throws FileNotFoundException {
		Site currentSite = (Site) request.getAttribute("currentSite");
		String webRoot = AsaproUtils.getWebRoot(request);
		
		File layoutDir = new File(webRoot + Constant.THEME_ROOT + currentSite.getSiteTheme() + "/layout");
		String[] layouts = layoutDir.list(new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				return !name.startsWith(".") && name.endsWith(".jsp");
			}
		});
		
		return Arrays.asList(layouts);
	}
	
	/**
	 * 콘텐츠메뉴 템플릿파일 목록을 반환한다.
	 */
	@Override
	public List<String> getContentTemplateFileList() throws FileNotFoundException {
		Site currentSite = (Site) request.getAttribute("currentSite");
		String webRoot = AsaproUtils.getWebRoot(request);
		
		File templateDir = new File(webRoot + Constant.THEME_ROOT + currentSite.getSiteTheme() + "/content");
		String[] templates = templateDir.list(new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				return !name.startsWith(".") && name.endsWith(".jsp");
			}
		});
		
		return Arrays.asList(templates);
	}

	/**
	 * 부모메뉴 찾기
	 */
	@Override
	public Menu findParentMenu(List<Menu> menuList, Menu child) {
		if( child.getMenuParent() == null ){
			return null;
		}
		for( Menu menu : menuList ){
			if( child.getMenuParent().getMenuId().equals(menu.getMenuId()) ){
				return menu;
			}
		}
		return null;
	}

	/**
	 * 현재메뉴에 해당하는 아이들
	 */
	@Override
	public List<Menu> getCurrentMenuList(List<Menu> menuList, Menu currentMenu) {
		List<Menu> currentMenuList = new ArrayList<Menu>();
		if( currentMenu != null && currentMenu.getMenuParent() != null ){
			currentMenuList.add(currentMenu);
			//int currentDepth = currentMenu.getMenuDepth();
			Menu parent = null;
//			Menu grandParent = null;
//			Menu grandGrandParent = null;

			parent = findParentMenu(menuList, currentMenu);//현제메뉴의 부모
			
			while (parent != null && !currentMenuList.contains(parent) ) {
				
				currentMenuList.add(parent);
				parent = findParentMenu(menuList, parent);//부모의 부모
			}
			
			/*if( parent != null && !currentMenuList.contains(parent) ){
				
				//그 위부모
				grandParent = findParentMenu(menuList, parent);
				if( grandParent != null && !currentMenuList.contains(grandParent) ){
					currentMenuList.add(grandParent);
					//그 위위 부모 일단 이까지만..재귀로 해야 하는데 귀찮다...-_-
					grandGrandParent = findParentMenu(menuList, grandParent);
					if( grandGrandParent != null && !currentMenuList.contains(grandGrandParent) ) {
						currentMenuList.add(grandGrandParent);
					}
				}
			}*/
			
		}
		//현재메뉴가 있으면서도 위로직으로는 메뉴추출이 안된 경우 = 자신을 넣어서 반환한다.
		//ex) 자기자신만 해당하는 특수한 경우 -  중간메인페이지 같은
		if( currentMenu != null && currentMenuList.isEmpty() ){
			currentMenuList.add(currentMenu);
		}
		return currentMenuList;
	}
	
	/**
	 * Who is my top ancestor?
	 */
	@Override
	public Menu getTopAncestorMenu(List<Menu> menuList, Menu child) {
		
		if(child == null){
			return null;
		}
		//현재메뉴가 1뎁스인경우 바로 반환
		if( child.getMenuDepth() == 1 ){
			return child;
		}
		Menu parent = findParentMenu(menuList, child);
		if( parent.getMenuDepth() == 1 ){
			return parent;
		} else {
			return getTopAncestorMenu(menuList, parent);
		}
		
	}

	/**
	 * 주소에서 메뉴정보 추출
	 */
	@Override
	public Menu parseMenuInformationFromUrl(String requestUri) {
		requestUri = StringUtils.removeStart(requestUri, Constant.CONTEXT_PATH);
		Site currentSite = (Site)request.getAttribute("currentSite");
		
		//관리자인 경우 메뉴 없음
		if(AsaproUtils.isAdminPath(requestUri)){
			return null;
		}
		
		// ajax요청인 경우 메뉴 없음
		if( requestUri.contains(Constant.API_PATH + "/") || StringUtils.isNotBlank(request.getHeader(Constant.SECURITY_ASYNC_TOKEN_NAME)) ){
			return null;
		}
		
		//----- 현재메뉴 없는 경우 - 예외처리 시작 -----
		// 필요하면 추가하세요
		if( requestUri.startsWith(AsaproUtils.getAppPath(currentSite) + "/file/") ){
			LOGGER.info("[ASAPRO] MenuServiceImpl file url => current menu is null ");
			return null;
		}
		else if( requestUri.startsWith(AsaproUtils.getAppPath(currentSite) + "/logout") ){
			LOGGER.info("[ASAPRO] MenuServiceImpl logout url => current menu is null ");
			return null;
		}
		else if( requestUri.startsWith(AsaproUtils.getAppPath(currentSite) + Constant.FEED_PATH) ){
			LOGGER.info("[ASAPRO] MenuServiceImpl feed url => current menu is null ");
			return null;
		}
		//----- 현재메뉴 없는 경우 - 예외처리 끝 -----
		
		//Map<String, String> result = new HashMap<String, String>();
		
		String[] temps = requestUri.split("/");
		LOGGER.info("[ASAPRO] MenuServiceImpl uri : {}, menu segment legnth : {}", requestUri, temps.length);
		
		//home 예외처리
		if( requestUri.equals(AsaproUtils.getAppPath(currentSite) + "/home") ){
			Menu home = new Menu();
			home.setMenuId("home");
			home.setSitePrefix(currentSite.getSitePrefix());
			return menuMapper.selectMenu(home);
		}
		
		if( temps.length <= 4 ){
			LOGGER.info("[ASAPRO] MenuServiceImpl [{}] is not a handlable uri. too short to parse.", requestUri);
			return null;
		}
		
		// ${APP_PATH}/${siteId}/content/introduction => 콘텐츠 주소의 길이는 무조건 5
		// ${APP_PATH}/${siteId}/board/notice
		// ${APP_PATH}/${siteId}/member/mypage
		// ${APP_PATH}/${siteId}/member/notification
		else if ( temps.length == 5 ){
			//콘텐츠다
			if( "content".equals(temps[3]) ){
				Menu contentMenu = new Menu();
				contentMenu.setSitePrefix(currentSite.getSitePrefix());
				contentMenu.setMenuId(temps[4]);
				return this.getMenu(contentMenu);
			} else {
				//모듈이면
				return this.findMenuByUri(requestUri);
			}
		}
		// ${APP_PATH}/${siteId}/board/notice/list
		// ${APP_PATH}/${siteId}/board/notice/3
		// ${APP_PATH}/${siteId}/board/notice/new
		// ${APP_PATH}/${siteId}/board/notice/password 
		// ${APP_PATH}/${siteId}/member/mypage/update 
		// ${APP_PATH}/${siteId}/member/mypage/event ... 
		// ${APP_PATH}/${siteId}/board/notice/3/edit
		// ${APP_PATH}/${siteId}/board/notice/3/delete
		// ..등등 게시판 같은거
		// ${APP_PATH}/${siteId}/archive/post/xxxx
		// ${APP_PATH}/${siteId}/archive/dvd/xxxx
		// ${APP_PATH}/${siteId}/archive/category/xxxx
		// ${APP_PATH}/${siteId}/archive/tag/xxxx
		// ${APP_PATH}/${siteId}/archive/post/category/xxxx
		else {
			//baCategory1 일치 검색
			if( temps.length >= 6 && StringUtils.isNotBlank(request.getParameter("baCategory1")) ){
				MenuSearch menuSearch = new MenuSearch();
				menuSearch.setSitePrefix(currentSite.getSitePrefix());
				//menuSearch.setMenuType("program");
				menuSearch.setMenuLinkSearchCondition("equals");
				//우선 전체 일치로 검색 -> 없으면 시작
				LOGGER.info("[ASAPRO] MenuServiceImpl AsaproUtils.getDecodedIfEncoded(requestUri) : {}", AsaproUtils.getDecodedIfEncoded(requestUri));
				menuSearch.setMenuLink(AsaproUtils.getDecodedIfEncoded(requestUri) + "?baCategory1=" + request.getParameter("baCategory1"));
				Menu found = menuMapper.findMenuByUri(menuSearch);
				if(found == null){
					menuSearch.setMenuLink(AsaproUtils.getDecodedIfEncoded(requestUri) + "?baCategory1=" + request.getParameter("baCategory1") + "&baCommSelec=true");
					found = menuMapper.findMenuByUri(menuSearch);
				}
				
				//상세보기 대응
				if(found == null){
					if(requestUri.matches("/site/[a-zA-Z0-9]+/board/[a-zA-Z0-9_]+/[0-9]+") ){
						StringBuilder length5Sb = new StringBuilder();
						
						length5Sb.append("/");
						length5Sb.append(temps[1]);
						length5Sb.append("/");
						length5Sb.append(temps[2]);
						length5Sb.append("/");
						length5Sb.append(temps[3]);
						length5Sb.append("/");
						length5Sb.append(temps[4]);
						length5Sb.append("/");
						length5Sb.append("list");
						
						menuSearch.setMenuLink(AsaproUtils.getDecodedIfEncoded(length5Sb.toString()) + "?baCategory1=" + request.getParameter("baCategory1"));
						found = menuMapper.findMenuByUri(menuSearch);
						
						if(found == null){
							menuSearch.setMenuLink(AsaproUtils.getDecodedIfEncoded(length5Sb.toString()) + "?baCategory1=" + request.getParameter("baCategory1") + "&baCommSelec=true");
							found = menuMapper.findMenuByUri(menuSearch);
						}
						
					}
				}
				
				if( found != null ){
					return found;
				}
			}
			
			//baCategory2 일치 검색
			if( temps.length >= 6 && StringUtils.isNotBlank(request.getParameter("baCategory2")) ){
				MenuSearch menuSearch = new MenuSearch();
				menuSearch.setSitePrefix(currentSite.getSitePrefix());
				//menuSearch.setMenuType("program");
				menuSearch.setMenuLinkSearchCondition("equals");
				//우선 전체 일치로 검색 -> 없으면 시작
				LOGGER.info("[ASAPRO] MenuServiceImpl AsaproUtils.getDecodedIfEncoded(requestUri) : {}", AsaproUtils.getDecodedIfEncoded(requestUri));
				menuSearch.setMenuLink(AsaproUtils.getDecodedIfEncoded(requestUri) + "?baCategory2=" + request.getParameter("baCategory2"));
				Menu found = menuMapper.findMenuByUri(menuSearch);
				if(found == null){
					menuSearch.setMenuLink(AsaproUtils.getDecodedIfEncoded(requestUri) + "?baCategory2=" + request.getParameter("baCategory2") + "&baCommSelec=true");
					found = menuMapper.findMenuByUri(menuSearch);
				}
				
				//상세보기 대응
				if(found == null){
					if(requestUri.matches("/site/[a-zA-Z0-9]+/board/[a-zA-Z0-9_]+/[0-9]+") ){
						StringBuilder length5Sb = new StringBuilder();
						
						length5Sb.append("/");
						length5Sb.append(temps[1]);
						length5Sb.append("/");
						length5Sb.append(temps[2]);
						length5Sb.append("/");
						length5Sb.append(temps[3]);
						length5Sb.append("/");
						length5Sb.append(temps[4]);
						length5Sb.append("/");
						length5Sb.append("list");
						
						menuSearch.setMenuLink(AsaproUtils.getDecodedIfEncoded(length5Sb.toString()) + "?baCategory2=" + request.getParameter("baCategory2"));
						found = menuMapper.findMenuByUri(menuSearch);
						
						if(found == null){
							menuSearch.setMenuLink(AsaproUtils.getDecodedIfEncoded(length5Sb.toString()) + "?baCategory2=" + request.getParameter("baCategory2") + "&baCommSelec=true");
							found = menuMapper.findMenuByUri(menuSearch);
						}
						
					}
				}
				
				if( found != null ){
					return found;
				}
			}
			
			//baCategory3 일치 검색
			if( temps.length >= 6 && StringUtils.isNotBlank(request.getParameter("baCategory3")) ){
				MenuSearch menuSearch = new MenuSearch();
				menuSearch.setSitePrefix(currentSite.getSitePrefix());
				//menuSearch.setMenuType("program");
				menuSearch.setMenuLinkSearchCondition("equals");
				//우선 전체 일치로 검색 -> 없으면 시작
				LOGGER.info("[ASAPRO] MenuServiceImpl AsaproUtils.getDecodedIfEncoded(requestUri) : {}", AsaproUtils.getDecodedIfEncoded(requestUri));
				menuSearch.setMenuLink(AsaproUtils.getDecodedIfEncoded(requestUri) + "?baCategory3=" + request.getParameter("baCategory3"));
				Menu found = menuMapper.findMenuByUri(menuSearch);
				if(found == null){
					menuSearch.setMenuLink(AsaproUtils.getDecodedIfEncoded(requestUri) + "?baCategory3=" + request.getParameter("baCategory3") + "&baCommSelec=true");
					found = menuMapper.findMenuByUri(menuSearch);
				}
				
				//상세보기 대응
				if(found == null){
					if(requestUri.matches("/site/[a-zA-Z0-9]+/board/[a-zA-Z0-9_]+/[0-9]+") ){
						StringBuilder length5Sb = new StringBuilder();
						
						length5Sb.append("/");
						length5Sb.append(temps[1]);
						length5Sb.append("/");
						length5Sb.append(temps[2]);
						length5Sb.append("/");
						length5Sb.append(temps[3]);
						length5Sb.append("/");
						length5Sb.append(temps[4]);
						length5Sb.append("/");
						length5Sb.append("list");
						
						menuSearch.setMenuLink(AsaproUtils.getDecodedIfEncoded(length5Sb.toString()) + "?baCategory3=" + request.getParameter("baCategory3"));
						found = menuMapper.findMenuByUri(menuSearch);
						
						if(found == null){
							menuSearch.setMenuLink(AsaproUtils.getDecodedIfEncoded(length5Sb.toString()) + "?baCategory3=" + request.getParameter("baCategory3") + "&baCommSelec=true");
							found = menuMapper.findMenuByUri(menuSearch);
						}
						
					}
				}
				
				if( found != null ){
					return found;
				}
			}
			
			//metaCode1 일치 검색
			if( temps.length >= 6 && StringUtils.isNotBlank(request.getParameter("metaCode1")) ){
				MenuSearch menuSearch = new MenuSearch();
				menuSearch.setSitePrefix(currentSite.getSitePrefix());
				//menuSearch.setMenuType("program");
				menuSearch.setMenuLinkSearchCondition("equals");
				//우선 전체 일치로 검색 -> 없으면 시작
				LOGGER.info("[ASAPRO] MenuServiceImpl AsaproUtils.getDecodedIfEncoded(requestUri) : {}", AsaproUtils.getDecodedIfEncoded(requestUri));
				menuSearch.setMenuLink(AsaproUtils.getDecodedIfEncoded(requestUri) + "?metaCode1=" + request.getParameter("metaCode1"));
				Menu found = menuMapper.findMenuByUri(menuSearch);
				
				//상세보기 대응
				if(found == null){
					if(requestUri.matches("/site/[a-zA-Z0-9]+/archive/[a-zA-Z0-9]+/[0-9]+/[0-9]+") ){
						StringBuilder length5Sb = new StringBuilder();
						
						length5Sb.append("/");
						length5Sb.append(temps[1]);
						length5Sb.append("/");
						length5Sb.append(temps[2]);
						length5Sb.append("/");
						length5Sb.append(temps[3]);
						length5Sb.append("/");
						length5Sb.append(temps[4]);
						length5Sb.append("/");
						length5Sb.append(temps[5]);
						
						menuSearch.setMenuLink(AsaproUtils.getDecodedIfEncoded(length5Sb.toString()) + "?metaCode1=" + request.getParameter("metaCode1"));
						found = menuMapper.findMenuByUri(menuSearch);
					}
				}
				
				if( found != null ){
					return found;
				}
			}
			if( temps.length >= 6 && StringUtils.isNotBlank(request.getParameter("metaCode2")) ){
				MenuSearch menuSearch = new MenuSearch();
				menuSearch.setSitePrefix(currentSite.getSitePrefix());
				//menuSearch.setMenuType("program");
				menuSearch.setMenuLinkSearchCondition("equals");
				//우선 전체 일치로 검색 -> 없으면 시작
				LOGGER.info("[ASAPRO] MenuServiceImpl AsaproUtils.getDecodedIfEncoded(requestUri) : {}", AsaproUtils.getDecodedIfEncoded(requestUri));
				menuSearch.setMenuLink(AsaproUtils.getDecodedIfEncoded(requestUri) + "?metaCode2=" + request.getParameter("metaCode2"));
				Menu found = menuMapper.findMenuByUri(menuSearch);
				
				//상세보기 대응
				if(found == null){
					if(requestUri.matches("/site/[a-zA-Z0-9]+/archive/[a-zA-Z0-9]+/[0-9]+/[0-9]+") ){
						StringBuilder length5Sb = new StringBuilder();
						
						length5Sb.append("/");
						length5Sb.append(temps[1]);
						length5Sb.append("/");
						length5Sb.append(temps[2]);
						length5Sb.append("/");
						length5Sb.append(temps[3]);
						length5Sb.append("/");
						length5Sb.append(temps[4]);
						length5Sb.append("/");
						length5Sb.append(temps[5]);
						
						menuSearch.setMenuLink(AsaproUtils.getDecodedIfEncoded(length5Sb.toString()) + "?metaCode2=" + request.getParameter("metaCode2"));
						found = menuMapper.findMenuByUri(menuSearch);
					}
				}
				
				if( found != null ){
					return found;
				}
			}
			if( temps.length >= 6 && StringUtils.isNotBlank(request.getParameter("metaCode3")) ){
				MenuSearch menuSearch = new MenuSearch();
				menuSearch.setSitePrefix(currentSite.getSitePrefix());
				//menuSearch.setMenuType("program");
				menuSearch.setMenuLinkSearchCondition("equals");
				//우선 전체 일치로 검색 -> 없으면 시작
				LOGGER.info("[ASAPRO] MenuServiceImpl AsaproUtils.getDecodedIfEncoded(requestUri) : {}", AsaproUtils.getDecodedIfEncoded(requestUri));
				menuSearch.setMenuLink(AsaproUtils.getDecodedIfEncoded(requestUri) + "?metaCode3=" + request.getParameter("metaCode3"));
				Menu found = menuMapper.findMenuByUri(menuSearch);
				
				//상세보기 대응
				if(found == null){
					if(requestUri.matches("/site/[a-zA-Z0-9]+/archive/[a-zA-Z0-9]+/[0-9]+/[0-9]+") ){
						StringBuilder length5Sb = new StringBuilder();
						
						length5Sb.append("/");
						length5Sb.append(temps[1]);
						length5Sb.append("/");
						length5Sb.append(temps[2]);
						length5Sb.append("/");
						length5Sb.append(temps[3]);
						length5Sb.append("/");
						length5Sb.append(temps[4]);
						length5Sb.append("/");
						length5Sb.append(temps[5]);
						
						menuSearch.setMenuLink(AsaproUtils.getDecodedIfEncoded(length5Sb.toString()) + "?metaCode3=" + request.getParameter("metaCode3"));
						found = menuMapper.findMenuByUri(menuSearch);
					}
				}
				
				if( found != null ){
					return found;
				}
			}
			
			//포스트 같은 싱글 아이템은 메뉴처리가 애매하다 => 첫번째 카테고리가 있으면 그걸 메뉴라고..치자 
			if( temps.length == 6 ){
				//아카이브 아이템인 경우 - 먼저 검색되는 카테고리를 메뉴로 가정한다.
//				if( !requestUri.startsWith(AsaproUtils.getAppPath(currentSite) + "/archive/tag") 
//						&& !requestUri.startsWith(AsaproUtils.getAppPath(currentSite) + "/archive/search")
//						&& !requestUri.startsWith(AsaproUtils.getAppPath(currentSite) + "/archive/author")
//						&& !requestUri.startsWith(AsaproUtils.getAppPath(currentSite) + "/archive/category")
//						&& requestUri.startsWith(AsaproUtils.getAppPath(currentSite) + "/archive/")
//						){
//					Archive arcSlugSearch = new Archive();
//					arcSlugSearch.setSitePrefix(currentSite.getSitePrefix());
//					String arcSlug = AsaproUtils.getSlugFromRequestUri(requestUri);
					//arcSlug = AsaproUtils.fixKorParameter(arcSlug, configService.getConfig("global"));
//					arcSlugSearch.setArcSlug(arcSlug);
//					Archive archiveItem = archiveService.getArchiveByArcSlug(arcSlugSearch);
//					if( archiveItem != null ){
//						if( archiveItem.getArcCategories() != null && !archiveItem.getArcCategories().isEmpty() ){
							//첫번째 카테고리를 메뉴라고 가정한다. 이건 답이 없다. 원래 포스트가 메뉴랑은 안맞음.
//							ArchiveCategory firstCategory = archiveItem.getArcCategories().get(0); 
//							if( firstCategory != null ){
								//간행물 예외처리
//								if(requestUri.startsWith(AsaproUtils.getAppPath(currentSite) + "/archive/publication/") ){
//									requestUri = AsaproUtils.getAppPath(currentSite) + "/archive/" + archiveItem.getArcCustomType() + "/category/" + firstCategory.getCatSlug() + "/" + archiveItem.getArcMetaCode1();
//								}else{
//									requestUri = AsaproUtils.getAppPath(currentSite) + "/archive/" + archiveItem.getArcCustomType() + "/category/" + firstCategory.getCatSlug();
//								}
//							}
//						}
//					}
//				}
				
			}
			//모듈(프로그램)이름에 매칭되는 경우
			//메뉴없는 아카이브도 이쪽으로 들어온다...
			Menu found = this.findMenuByUri(requestUri);
			return found;
		}
	}
	
	/**
	 * 현재메뉴를 주소로 찾기
	 */
	@Override
	public Menu findMenuByUri(String uriParam) {
		String uri = uriParam;
		Site currentSite = (Site)request.getAttribute("currentSite");
		
		String[] temps = uri.split("/");
		
		StringBuilder length4Sb = new StringBuilder();
		length4Sb.append("/");
		length4Sb.append(temps[1]);
		length4Sb.append("/");
		length4Sb.append(temps[2]);
		length4Sb.append("/");
		length4Sb.append(temps[3]);
		length4Sb.append("/");
		length4Sb.append(temps[4]);
		
		StringBuilder length5Sb = new StringBuilder();
		if( temps.length >= 6 ){
			length5Sb.append("/");
			length5Sb.append(temps[1]);
			length5Sb.append("/");
			length5Sb.append(temps[2]);
			length5Sb.append("/");
			length5Sb.append(temps[3]);
			length5Sb.append("/");
			length5Sb.append(temps[4]);
			length5Sb.append("/");
			length5Sb.append(temps[5]);
		}
		
		MenuSearch menuSearch = new MenuSearch();
		menuSearch.setSitePrefix(currentSite.getSitePrefix());
		menuSearch.setMenuType("program");
		menuSearch.setMenuLinkSearchCondition("equals");
		Menu menu = null;
		
		uri = AsaproUtils.getDecodedIfEncoded(uri);
		//우선 전체 일치로 검색 -> 없으면 시작
		menuSearch.setMenuLink(uri);
		menu = menuMapper.findMenuByUri(menuSearch);
		
		//전체일치로 나온 메뉴가 없으면 시작...
		if( menu == null ){
			// 길이 6 넘으면 5개일치 검색
			if( temps.length >= 6 ){
				menuSearch.setMenuLink(length5Sb.toString());
				menu = menuMapper.findMenuByUri(menuSearch);
				// 파라메터 포함일 경우...대비
				if( menu == null ){
					menuSearch.setMenuLink(length5Sb.toString() + "/");
					menuSearch.setMenuLinkSearchCondition("like");
					menu = menuMapper.findMenuByUri(menuSearch);
				}
				if( menu == null ){
					menuSearch.setMenuLink(length5Sb.toString());
					menuSearch.setMenuLinkSearchCondition("like");
					menu = menuMapper.findMenuByUri(menuSearch);
				}
			} 
			// 아니면 4개 짜리 일치 검색
			else {
				menuSearch.setMenuLink(length4Sb.toString());
				menuSearch.setMenuLinkSearchCondition("equals");
				menu = menuMapper.findMenuByUri(menuSearch);
				// 파라메터 포함일 경우...대비
				if( menu == null ){
					menuSearch.setMenuLinkSearchCondition("like");
					menu = menuMapper.findMenuByUri(menuSearch);
				}
			}
			// 없으면 5개짜리 라이크 검색
			if( menu == null || StringUtils.isBlank(menu.getMenuId()) ){
				Menu archiveTempMenu = new Menu();
				if( temps.length == 5 ){
					if( "archive".equals(temps[3]) && "search".equals(temps[4]) ){
						String sv = request.getParameter("sv");
						sv = AsaproUtils.fixKorParameter(sv, configService.getConfig("global"));
						//PagingSearch pagingSearch = new PagingSearch();
						//pagingSearch.setSv(sv);
						//pagingSearch.fixBrokenSvByDefaultCharsets();
						//sv = pagingSearch.getSv();
						archiveTempMenu.setMenuName(sv);
						//sv = AsaproUtils.getDecodedIfEncoded(sv);
						menu = archiveTempMenu;
						LOGGER.info("[ASAPRO] MenuServiceImpl " + " SEAERCH : Setting Temporary Archive Search Menu...");
					} else if( "archive".equals(temps[3]) && "tag".equals(temps[4]) ){
						archiveTempMenu.setMenuName("Tag Cloud");
						menu = archiveTempMenu;
						LOGGER.info("[ASAPRO] MenuServiceImpl " + " SEAERCH : Setting Temporary Archive Tag Cloud...");
					} 
				}
				else if( temps.length >= 6 ){
					//아카이브 모듈(또는 상속모듈)인데 메뉴에 없으면 임시메뉴정보를 반환한다.
					if( "archive".equals(temps[3]) ){
						//카테고리
						if( "category".equals(temps[4]) && !"tag".equals(temps[5]) // => ${APP_PATH}/${siteId}/archive/category/xxxx
								|| "category".equals(temps[5]) && !"tag".equals(temps[4]) ){
							String catSlug = null;
							// ${APP_PATH}/${siteId}/archive/category/xxxx 인 경우
							if( "category".equals(temps[4]) ){
								catSlug = temps[5];
							}
							// ${APP_PATH}/${siteId}/archive/{customType}/category/xxxx 인 경우
							else {
								catSlug = temps[6];
							}
							catSlug = AsaproUtils.getDecodedIfEncoded(catSlug);
//							ArchiveCategory catSlugSearch = new ArchiveCategory();
//							catSlugSearch.setSitePrefix(currentSite.getSitePrefix());
//							catSlugSearch.setCatSlug(catSlug);
//							ArchiveCategory archiveCategory = archiveService.getArchiveCategoryByCatSlug(catSlugSearch);
//							archiveTempMenu.setMenuName(archiveCategory.getCatName());
							LOGGER.info("[ASAPRO] MenuServiceImpl " + " CATEGORY : Setting Temporary Archive Category Menu...");
						}
						//태그
						else if( !"category".equals(temps[5]) && "tag".equals(temps[4]) ){
							String tagSlug = AsaproUtils.getDecodedIfEncoded(temps[5]);
//							ArchiveTag tagSlugSearch = new ArchiveTag();
//							tagSlugSearch.setSitePrefix(currentSite.getSitePrefix());
//							tagSlugSearch.setTagSlug(tagSlug);
//							ArchiveTag archiveTag = archiveService.getArchiveTagByTagSlug(tagSlugSearch); 
//							archiveTempMenu.setMenuName(archiveTag.getTagName());
							LOGGER.info("[ASAPRO] MenuServiceImpl " + " TAG : Setting Temporary Archive Tag Menu...");
						}
						//저자
						else if( "author".equals(temps[4]) ){
							archiveTempMenu.setMenuName("작성자의 다른 글");
							LOGGER.info("[ASAPRO] MenuServiceImpl " + " AUTHOR : Setting Temporary Archive Author Menu...");
						}
						else {
							LOGGER.info("[ASAPRO] MenuServiceImpl " + " Search Temporary Archive Menu...from ModelNames...");
							archiveTempMenu.setMenuName("Archive");
						}
						archiveTempMenu.setMenuType("text");
						menu = archiveTempMenu;
					}
					//아카이브 모듈이 아니다
					else {
						menuSearch.setMenuLinkSearchCondition("like");
						menu = menuMapper.findMenuByUri(menuSearch);
					}
				}
			}
			// 없으면 4개짜리 라이크 검색
			if( menu == null ){
				menuSearch.setMenuLink(length4Sb.toString() + "/");
				menuSearch.setMenuLinkSearchCondition("like");
				menu = menuMapper.findMenuByUri(menuSearch);
				
				if( menu == null ){
					menuSearch.setMenuLink(length4Sb.toString());
					menuSearch.setMenuLinkSearchCondition("like");
					menu = menuMapper.findMenuByUri(menuSearch);
				}
			}
			// 없으면 3개짜리? - 최후의 수단...
			if( menu == null ){
				StringBuilder length3Sb = new StringBuilder();
				length3Sb.append("/");
				length3Sb.append(temps[1]);
				length3Sb.append("/");
				length3Sb.append(temps[2]);
				length3Sb.append("/");
				length3Sb.append(temps[3]);
				menuSearch.setMenuLink(length3Sb.toString());
				menuSearch.setMenuLinkSearchCondition("like");
				menu = menuMapper.findMenuByUri(menuSearch);
			}
		}
		
		if( menu != null ){
			String theme = (String)request.getAttribute("theme");
			boolean forceTextMenu = StringUtils.isNotBlank(request.getParameter("themePreview"));
			menu = menu.populateMenuImageUrl(currentSite.getSiteId(), theme, forceTextMenu);
		}
		
		return menu;
	}


	/**
	 * 콘텐츠 메뉴 검색
	 */
	@Override
	public List<Menu> getContentMenuList(MenuSearch menuSearch) {
		return menuMapper.selectContentMenuList(menuSearch);
	}

	/**
	 * 콘텐츠 메뉴 검색 개수
	 */
	@Override
	public int getContentMenuListTotal(MenuSearch menuSearch) {
		return menuMapper.selectContentMenuListTotal(menuSearch);
	}
	
}
