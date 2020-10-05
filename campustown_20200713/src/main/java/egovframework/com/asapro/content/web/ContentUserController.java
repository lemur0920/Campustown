/**
 * 
 */
package egovframework.com.asapro.content.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import egovframework.com.asapro.content.service.Content;
import egovframework.com.asapro.content.service.ContentSearch;
import egovframework.com.asapro.content.service.ContentService;
import egovframework.com.asapro.menu.service.Menu;
import egovframework.com.asapro.menu.service.MenuContentRelation;
import egovframework.com.asapro.site.service.Site;
import egovframework.com.asapro.support.Constant;
import egovframework.com.asapro.support.annotation.CurrentMenu;
import egovframework.com.asapro.support.annotation.CurrentSite;
import egovframework.com.asapro.support.util.AsaproUtils;
import egovframework.com.asapro.support.util.EtcUtil;

/**
 * 메뉴 사용자 컨트롤러
 * @author yckim
 * @since 2018. 7. 20.
 *
 */
@Controller
public class ContentUserController {
	
	@Autowired
	private ContentService contentService;
	
	@Autowired
	private HttpServletRequest request;
	
	/**
	 * 콘텐츠 뷰를 반환한다.
	 * @param model
	 * @param menuId
	 * @return 콘텐츠 뷰
	 * @throws IOException 
	 * @throws ServletException 
	 */
	@RequestMapping(value=Constant.APP_PATH + Constant.SITE_ID_PATH + "/content/{menuId}", method=RequestMethod.GET)
	public String menuGet(Model model, @CurrentSite Site currentSite, @CurrentMenu Menu currentMenu) throws ServletException, IOException{
		
		String template = "content";
		//콘텐츠가 커스텀 뷰파일을 사용하고 있는 경우
		if( StringUtils.isNotBlank(currentMenu.getMenuTemplate()) ){
			template = StringUtils.removeEnd(currentMenu.getMenuTemplate(), ".jsp");
		}
		
		MenuContentRelation menuContentRelation = new MenuContentRelation();
		menuContentRelation.setSitePrefix(currentSite.getSitePrefix());
		menuContentRelation.setMenuId(currentMenu.getMenuId());
		Content content = contentService.getContentByRel(menuContentRelation);
		
		model.addAttribute("content", content);
		
		return  AsaproUtils.getThemePath(request) +  "/content/" + template;
	}
	
}
