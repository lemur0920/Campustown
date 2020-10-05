/**
 * 
 */
package egovframework.com.asapro.theme.web;

import java.io.FileNotFoundException;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import egovframework.com.asapro.site.service.Site;
import egovframework.com.asapro.site.service.SiteService;
import egovframework.com.asapro.support.Constant;
import egovframework.com.asapro.support.annotation.CurrentSite;
import egovframework.com.asapro.support.util.AsaproUtils;
import egovframework.com.asapro.theme.service.ThemeService;

/**
 * 테마관리 관리자 컨트롤러
 * @author yckim
 * @since 2018. 9. 13.
 *
 */
@Controller
public class ThemeAdminController {
	
	@Autowired
	private ThemeService themeService;
	
	@Autowired
	private SiteService siteService;
	
	/**
	 * 테마 목록뷰를 반환한다.
	 * @param model
	 * @return 테마 목록 뷰 
	 * @throws FileNotFoundException
	 */
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/theme/list.do", method=RequestMethod.GET)
	public String themeListGet(Model model) throws FileNotFoundException{
		model.addAttribute("themeList", themeService.getThemeList());
		return "admin/theme/list";
	}
	
	/**
	 * 테마를 변경한다.
	 * @param model
	 * @param theme
	 * @return 결과 뷰
	 */
	@RequestMapping(value=Constant.APP_PATH + Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/theme/update.do", method=RequestMethod.POST)
	public String themeSelectPost(Model model, @CurrentSite Site currentSite, @RequestParam(value="theme", required=true) String theme ){
		if( StringUtils.isBlank(theme) ){
			return "redirect:" + AsaproUtils.getAdminPath(currentSite, true) + "/theme/list.do?updated=false";
		} else {
			currentSite.setSiteTheme(theme);
			siteService.updateSite(currentSite);
			return "redirect:" + AsaproUtils.getAdminPath(currentSite, true) + "/theme/list.do?updated=true";
		}
	}
}
