/**
 * 
 */
package egovframework.com.asapro.menu.web;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import java.util.Set;


import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import egovframework.com.asapro.code.service.Code;
import egovframework.com.asapro.code.service.CodeService;
import egovframework.com.asapro.config.service.Config;
import egovframework.com.asapro.config.service.ConfigService;
import egovframework.com.asapro.content.service.Content;
import egovframework.com.asapro.content.service.ContentService;
import egovframework.com.asapro.member.admin.service.AdminMember;
import egovframework.com.asapro.menu.service.Menu;
import egovframework.com.asapro.menu.service.MenuContentRelation;
import egovframework.com.asapro.menu.service.MenuIdJsonVO;
import egovframework.com.asapro.menu.service.MenuSearch;
import egovframework.com.asapro.menu.service.MenuService;
import egovframework.com.asapro.site.service.Site;
import egovframework.com.asapro.support.Constant;
import egovframework.com.asapro.support.ServerMessage;
import egovframework.com.asapro.support.annotation.CurrentAdmin;
import egovframework.com.asapro.support.annotation.CurrentSite;
import egovframework.com.asapro.support.util.AsaproUtils;

/**
 * 메뉴관리자 컨트롤러
 * @author yckim
 * @since 2017. 7. 6.
 *
 */
@Controller
public class MenuAdminController {
	
	@Autowired
	private MenuService menuService;
	
	@Autowired
	private MenuValidator menuValidator;
	
	@Autowired
	private ApplicationContext applicationContext;
	
	@Autowired
	private CodeService codeService;
	
	@Autowired
	private HttpServletRequest request;
	
	@Autowired
	private ContentService contentService;
	
	
	/**
	 * 메뉴 목록 화면을 조회한다.
	 * @param model
	 * @param currentSite
	 * @param menuSearch
	 * @return 메뉴 목록 뷰
	 */
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/menu/list.do", method=RequestMethod.GET)
	public String menuListGet(Model model, @CurrentSite Site currentSite, @ModelAttribute("menuSearch") MenuSearch menuSearch){
		menuSearch.setSitePrefix(currentSite.getSitePrefix());
		menuSearch.setPaging(false);
		model.addAttribute("menuList", menuService.getMenuList(menuSearch));
		return "asapro/admin/menu/list";
	}
	
	/**
	 * 리퀘스트 매핑 정보를 반환한다.
	 * <pre>
	 * - @Controller 가 붙은 클래스의 @RequestMapping 을 가진 메서드의 value 를 모아서 반환한다.
	 * </pre>
	 * @return 리퀘스트 매핑 리스트
	 */
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/menu/requestMappingList.do")
	@ResponseBody
	public List<String> requestMappingListGet(@CurrentSite Site currentSite){
		List<String> list = new ArrayList<String>();
		Map<String, Object> map = applicationContext.getBeansWithAnnotation(Controller.class);
		Set<Entry<String, Object>> set = map.entrySet();
		for( Entry<String, Object> entry : set ){
			Class<? extends Object> clazz = entry.getValue().getClass();
			Method[] methods = clazz.getDeclaredMethods();
			
			String apiPath = Constant.API_PATH + "/";
			String adminPath = Constant.ADMIN_PATH;
			String appPath = Constant.APP_PATH + "/";
			for( Method method : methods ){
				if( method.isAnnotationPresent(RequestMapping.class) ){
					if( ArrayUtils.contains(method.getAnnotation(RequestMapping.class).method(), RequestMethod.GET)  ){
						String[] mappings = method.getAnnotation(RequestMapping.class).value();
						for( String mapping : mappings ){
							if( mapping.contains( apiPath ) || mapping.contains( adminPath ) ){
								continue;
							}
							if( mapping.contains( appPath ) ){
								mapping = Constant.CONTEXT_PATH + mapping;
								list.add(mapping.replace(Constant.SITE_ID_PATH, currentSite.isSiteMain() ? "/" + Constant.MAIN_SITE_DISPLAY_ID : "/" + currentSite.getSiteId()));
							}
						}
					}
				}
			}
		}
		return list;
	}
	
	/**
	 * 메뉴 추가 폼뷰를 반환한다.
	 * @param model
	 * @param currentSite
	 * @param menuForm
	 * @return 메뉴 입력 폼
	 * @throws FileNotFoundException
	 */
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/menu/insert.do", method=RequestMethod.GET)
	public String insertMenuGet(Model model, @CurrentSite Site currentSite, @ModelAttribute("menuForm") Menu menuForm) throws FileNotFoundException{
		model.addAttribute("formMode", "INSERT");
		MenuSearch menuSearch = new MenuSearch();
		menuSearch.setSitePrefix(currentSite.getSitePrefix());
		model.addAttribute("menuList", menuService.getMenuList(menuSearch));
		
		//레이아웃 파일 목록
		List<String> layoutFileList = menuService.getLayoutFileList();
		model.addAttribute("layoutFileList", layoutFileList);
		//콘텐츠메뉴 템플릿파일 목록
		List<String> contentTemplateFileList = menuService.getContentTemplateFileList();
		model.addAttribute("contentTemplateFileList", contentTemplateFileList);
		
		//해시태그 코드목록 메뉴설정에 적용된 코드카테고리 추출
		Config designConfig = (Config) request.getAttribute("designConfig");
		String tagCodeCat = designConfig.getOption("menu_hash_tag_code_category");
		//model.addAttribute("menu_hash_tag_code_category", tagCodeCat);
		
		//해시태그 코드목록
		if(StringUtils.isNotBlank(tagCodeCat) ){
			List<Code> menuHashTagCodeList = codeService.getCodeList(tagCodeCat);
			model.addAttribute("menuHashTagCodeList", menuHashTagCodeList);
		}
		
		
		return "asapro/admin/menu/form";
	}
	
	/**
	 * 메뉴를 추가한다.
	 * @param model
	 * @param redirectAttributes
	 * @param currentSite
	 * @param currentAdmin
	 * @param menuForm
	 * @param bindingResult
	 * @return 메뉴 목록
	 * @throws IOException
	 */
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/menu/insert.do", method=RequestMethod.POST)
	public String insertMenuPost(Model model, RedirectAttributes redirectAttributes, @CurrentSite Site currentSite, @CurrentAdmin AdminMember currentAdmin, @ModelAttribute("menuForm") Menu menuForm, BindingResult bindingResult) throws IOException{
		model.addAttribute("formMode", "INSERT");
		menuForm.setSitePrefix(currentSite.getSitePrefix());
		menuValidator.validate(menuForm, bindingResult, "INSERT");
		if(bindingResult.hasErrors()){
			MenuSearch menuSearch = new MenuSearch();
			menuSearch.setSitePrefix(currentSite.getSitePrefix());
			model.addAttribute("menuList", menuService.getMenuList(menuSearch));
			model.addAttribute("menuForm", menuForm);
			
			//레이아웃 파일 목록
			List<String> layoutFileList = menuService.getLayoutFileList();
			model.addAttribute("layoutFileList", layoutFileList);
			//콘텐츠메뉴 템플릿파일 목록
			List<String> contentTemplateFileList = menuService.getContentTemplateFileList();
			model.addAttribute("contentTemplateFileList", contentTemplateFileList);
			
			//해시태그 코드목록 메뉴설정에 적용된 코드카테고리 추출
			Config designConfig = (Config) request.getAttribute("designConfig");
			String tagCodeCat = designConfig.getOption("menu_hash_tag_code_category");
			//model.addAttribute("menu_hash_tag_code_category", tagCodeCat);
			
			//해시태그 코드목록
			if(StringUtils.isNotBlank(tagCodeCat) ){
				List<Code> menuHashTagCodeList = codeService.getCodeList(tagCodeCat);
				model.addAttribute("menuHashTagCodeList", menuHashTagCodeList);
			}
			
			return "asapro/admin/menu/form";
		} else {
			menuForm.setMenuRegDate(new Date());
			menuService.insertMenu(menuForm);
			redirectAttributes.addFlashAttribute("inserted", true);
			return "redirect:" + AsaproUtils.getAdminPath(currentSite, true) + "/menu/list.do";
		}
	}
	
	/**
	 * 메뉴 수정 폼을 반환한다.
	 * @param model
	 * @param currentSite
	 * @param menuForm
	 * @return 메뉴 수정 폼
	 * @throws FileNotFoundException
	 */
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/menu/update.do", method=RequestMethod.GET)
	public String updateMenuGet(Model model, @CurrentSite Site currentSite, @ModelAttribute("menuForm") Menu menuForm) throws FileNotFoundException{
		
		menuForm.setSitePrefix(currentSite.getSitePrefix());
		model.addAttribute("formMode", "UPDATE");
		MenuSearch menuSearch = new MenuSearch();
		menuSearch.setSitePrefix(currentSite.getSitePrefix());
		List<Menu> menuList = menuService.getMenuList(menuSearch);
		model.addAttribute("menuList", menuList);
		Menu menuDB = menuService.getMenu(menuForm);
		
		//레이아웃 파일 목록
		List<String> layoutFileList = menuService.getLayoutFileList();
		model.addAttribute("layoutFileList", layoutFileList);
		//콘텐츠메뉴 템플릿파일 목록
		List<String> contentTemplateFileList = menuService.getContentTemplateFileList();
		model.addAttribute("contentTemplateFileList", contentTemplateFileList);
		
		//해시태그 코드목록 메뉴설정에 적용된 코드카테고리 추출
		Config designConfig = (Config) request.getAttribute("designConfig");
		String tagCodeCat = designConfig.getOption("menu_hash_tag_code_category");
		//model.addAttribute("menu_hash_tag_code_category", tagCodeCat);
		
		//해시태그 코드목록
		if(StringUtils.isNotBlank(tagCodeCat) ){
			List<Code> menuHashTagCodeList = codeService.getCodeList(tagCodeCat);
			model.addAttribute("menuHashTagCodeList", menuHashTagCodeList);
		}
		
		//태그 배열에 담는다
		if(StringUtils.isNotBlank(menuDB.getTagCode()) ){
			String[] hashTagArray = menuDB.getTagCode().split(",");
			model.addAttribute("hashTagArray", hashTagArray);
		}
		
		//콘텐츠메뉴인경우 콘텐츠를 담는다.
		if("content".equals(menuDB.getMenuType()) ){
			MenuContentRelation menuContentRelation = new MenuContentRelation();
			menuContentRelation.setSitePrefix(currentSite.getSitePrefix());
			menuContentRelation.setMenuId(menuDB.getMenuId());
			Content content = contentService.getContentByRel(menuContentRelation);
			menuDB.setContent(content);
		}
		model.addAttribute("menuForm", menuDB);
		return "asapro/admin/menu/form";
	}
	
	/**
	 * 메뉴정보를 수정한다.
	 * @param model
	 * @param redirectAttributes
	 * @param currentSite
	 * @param menuForm
	 * @param bindingResult
	 * @return 메뉴 목록 뷰
	 * @throws IOException
	 */
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/menu/update.do", method=RequestMethod.POST)
	public String updateMenuPost(Model model, RedirectAttributes redirectAttributes, @CurrentSite Site currentSite, @ModelAttribute("menuForm") Menu menuForm, BindingResult bindingResult) throws IOException{
		menuForm.setSitePrefix(currentSite.getSitePrefix());
		model.addAttribute("formMode", "UPDATE");
		menuValidator.validate(menuForm, bindingResult, "UPDATE");
		if(bindingResult.hasErrors()){
			MenuSearch menuSearch = new MenuSearch();
			menuSearch.setSitePrefix(currentSite.getSitePrefix());
			model.addAttribute("menuList", menuService.getMenuList(menuSearch));
			model.addAttribute("menuForm", menuForm);
			
			//레이아웃 파일 목록
			List<String> layoutFileList = menuService.getLayoutFileList();
			model.addAttribute("layoutFileList", layoutFileList);
			//콘텐츠메뉴 템플릿파일 목록
			List<String> contentTemplateFileList = menuService.getContentTemplateFileList();
			model.addAttribute("contentTemplateFileList", contentTemplateFileList);
			
			//해시태그 코드목록 메뉴설정에 적용된 코드카테고리 추출
			Config designConfig = (Config) request.getAttribute("designConfig");
			String tagCodeCat = designConfig.getOption("menu_hash_tag_code_category");
			//model.addAttribute("menu_hash_tag_code_category", tagCodeCat);
			
			//해시태그 코드목록
			if(StringUtils.isNotBlank(tagCodeCat) ){
				List<Code> menuHashTagCodeList = codeService.getCodeList(tagCodeCat);
				model.addAttribute("menuHashTagCodeList", menuHashTagCodeList);
			}
			
			//태그 배열에 담는다
			if(StringUtils.isNotBlank(menuForm.getTagCode()) ){
				String[] hashTagArray = menuForm.getTagCode().split(",");
				model.addAttribute("hashTagArray", hashTagArray);
			}
			
			return "asapro/admin/menu/form";
		} else {

			menuService.updateMenu(menuForm);
			redirectAttributes.addFlashAttribute("updated", true);
			//return "redirect:" + Constant.ADMIN_PATH + "/menu/update.do?menuId=" + menuForm.getMenuId();
			return "redirect:" + AsaproUtils.getAdminPath(currentSite, true) + "/menu/list.do";
		}
	}
	
	/**
	 * 메뉴속성을 수정한다.
	 * @param model
	 * @param currentSite
	 * @param menuForm
	 * @return 수정결과
	 * @throws IOException
	 */
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/menu/updateMenuProperties.do", method=RequestMethod.POST)
	@ResponseBody
	public ServerMessage updateMenuPropertiesPost(Model model, @CurrentSite Site currentSite, @ModelAttribute("menuForm") Menu menuForm) throws IOException{
		menuForm.setSitePrefix(currentSite.getSitePrefix());
		Menu fromDB = menuService.getMenu(menuForm);
		if( fromDB != null ){
			fromDB.setSitePrefix(currentSite.getSitePrefix());
			fromDB.setMenuType(menuForm.getMenuType());
			fromDB.setMenuStatus(menuForm.getMenuStatus());
			fromDB.setMenuLink(menuForm.getMenuLink());
			if( menuService.updateMenu(fromDB) > 0 ){
				return new ServerMessage(true, "수정되었습니다.");
			} else {
				return new ServerMessage(false, "수정하지 못하였습니다.");
			}
		} else {
			return new ServerMessage(false, "존재하지 않는 메뉴입니다.");
		}
	}
	
	/**
	 * 메뉴 정렬 순서를 수정한다.
	 * @param model
	 * @param currentSite
	 * @param menuOrders
	 * @return 수정 결과 json
	 */
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/menu/reOrder.do", method=RequestMethod.POST)
	@ResponseBody
	public ServerMessage reOrderMenuPost(Model model, @CurrentSite Site currentSite, @RequestParam(value="menuOrders", required=true) String menuOrders){
		ServerMessage serverMessage = new ServerMessage();
		if(StringUtils.isBlank(menuOrders)){
			serverMessage.setSuccess(false);
			serverMessage.setText("수정할 데이터가 없습니다.");
		} else {
			
			List<Menu> menus = new ArrayList<Menu>();
			String[] temps = menuOrders.split(",");
			Menu menu = null;
			for(String str : temps){
				menu = new Menu();
				menu.setMenuId(str.split("@")[0]);
				menu.setMenuOrder(Integer.parseInt(str.split("@")[1]));
				menus.add(menu);
			}
			
			if( menuService.updateMenuOrders(menus) >= 0 ){
				serverMessage.setSuccess(true);
			} else {
				serverMessage.setSuccess(false);
				serverMessage.setText("수정하지 못하였습니다.[Server Error]");
			}
		}
		return serverMessage;
	}
	
	/**
	 * 메뉴 트리구조와 정렬 순서를 수정한다.
	 * @param model
	 * @param currentSite
	 * @param dataJson
	 * @return 수정결과
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/menu/reBuild.do", method=RequestMethod.POST)
	@ResponseBody
	public ServerMessage reBuildMenuPost(Model model, @CurrentSite Site currentSite, @RequestParam(value="dataJson", required=true) String dataJson) throws JsonParseException, JsonMappingException, IOException{
		ServerMessage serverMessage = new ServerMessage();
		if(StringUtils.isBlank(dataJson)){
			serverMessage.setSuccess(false);
			serverMessage.setText("수정할 데이터가 없습니다.");
		} else {
			
			ObjectMapper om = new ObjectMapper();
			List<MenuIdJsonVO> jsonToList = om.readValue(dataJson, om.getTypeFactory().constructCollectionType(ArrayList.class, MenuIdJsonVO.class));
			List<Menu> menuList = new ArrayList<Menu>();
			
			jsonStrToList(menuList, jsonToList, 1, null);
			
			
			
			if( menuService.updateMenuRebuild(menuList) > 0 ){
				serverMessage.setSuccess(true);
			} else {
				serverMessage.setSuccess(false);
				serverMessage.setText("수정하지 못하였습니다.[Server Error]");
			}
		}
		return serverMessage;
	}
	
	/**
	 * json 으로 받은 메뉴 아이디로 메뉴 목록을 반환한다.
	 * @param list
	 * @param jsonToList
	 * @param depth
	 * @param pid
	 */
	private void jsonStrToList(List<Menu> list, List<MenuIdJsonVO> jsonToList, int depth, String pid) {
		int order = 0;
		for (MenuIdJsonVO menuIdJsonVO : jsonToList) {
			Menu menu = new Menu();
			menu.setMenuId(menuIdJsonVO.getId());
			menu.setMenuDepth(depth);
			menu.setMenuOrder(order);
			Menu parentMenu = new Menu();
			parentMenu.setMenuId(pid);
			menu.setMenuParent(parentMenu);
			list.add(menu);
			if(menuIdJsonVO.getChildren() != null && menuIdJsonVO.getChildren().size() > 0){
				jsonStrToList(list, menuIdJsonVO.getChildren(), depth + 1, menuIdJsonVO.getId());
			}
			order++;
		}

	}
	
	/**
	 * 메뉴를 삭제한다.
	 * @param model
	 * @param currentSite
	 * @param menuId
	 * @return 삭제결과
	 * @throws FileNotFoundException
	 */
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/menu/delete.do", method=RequestMethod.POST)
	@ResponseBody
	public ServerMessage deleteMenuPost(Model model, @CurrentSite Site currentSite, @RequestParam(value="menuId", required=true) String menuId) throws FileNotFoundException{
		ServerMessage serverMessage = new ServerMessage();
		if(StringUtils.isBlank(menuId)){
			serverMessage.setSuccess(false);
			serverMessage.setText("삭제할 데이터가 없습니다.");
		} else {
			Menu menu = new Menu();
			menu.setMenuId(menuId);
			menu.setSitePrefix(currentSite.getSitePrefix());
			int result = menuService.deleteMenu(menu); 
			if( result > 0 ){
				serverMessage.setSuccess(true);
				serverMessage.setSuccessCnt(result);
			} else if( result == -99 ){
				serverMessage.setSuccess(false);
				serverMessage.setText("하위메뉴가 있어 삭제할 수 없습니다.\n\n하위메뉴을 이동시키거나 제거한 후 다시 삭제해 주세요.");
			} else {
				serverMessage.setSuccess(false);
				serverMessage.setText("수정하지 못하였습니다.[Server Error]");
			}
		}
		return serverMessage;
	}
	
}
