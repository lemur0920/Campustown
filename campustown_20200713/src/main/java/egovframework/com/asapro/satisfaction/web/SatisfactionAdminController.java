/**
 * 
 */
package egovframework.com.asapro.satisfaction.web;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import egovframework.com.asapro.menu.service.Menu;
import egovframework.com.asapro.menu.service.MenuSearch;
import egovframework.com.asapro.menu.service.MenuService;
import egovframework.com.asapro.satisfaction.service.SatisOpinion;
import egovframework.com.asapro.satisfaction.service.SatisOpinionSearch;
import egovframework.com.asapro.satisfaction.service.Satisfaction;
import egovframework.com.asapro.satisfaction.service.SatisfactionSearch;
import egovframework.com.asapro.satisfaction.service.SatisfactionService;
import egovframework.com.asapro.site.service.Site;
import egovframework.com.asapro.support.Constant;
import egovframework.com.asapro.support.ServerMessage;
import egovframework.com.asapro.support.annotation.CurrentSite;
import egovframework.com.asapro.support.pagination.Paging;

/**
 * 만족도 평가 관리자 컨트롤러
 * @author yckim
 * @since 2019. 4. 15.
 *
 */
@Controller
public class SatisfactionAdminController {
	
	@Autowired
	private SatisfactionService satisfactionService;
	
	@Autowired
	private MenuService menuService;
	
	/**
	 * 메뉴별 만족도 평가 합계
	 * @param model
	 * @param satisfactionSearch
	 * @return
	 */
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/satisfaction/list.do", method=RequestMethod.GET)
	public String statisfactionMenuListGet(Model model, @CurrentSite Site currentSite,  @ModelAttribute SatisfactionSearch satisfactionSearch, @ModelAttribute("menuSearch") MenuSearch menuSearch){
		satisfactionSearch.setSitePrefix(currentSite.getSitePrefix());
		satisfactionSearch.setPaging(false);
		Map<String, Satisfaction> satisfactionMap = satisfactionService.getSatisfactionMap(satisfactionSearch);
		model.addAttribute("satisfaction", satisfactionMap);
		//메뉴 목록
		menuSearch.setSitePrefix(currentSite.getSitePrefix());
		menuSearch.setPaging(false);
		model.addAttribute("menuList", menuService.getMenuList(menuSearch));
		return "asapro/admin/satisfaction/list";
	}
	
	/**
	 * 메뉴별 만족도 평가 상세정보
	 * @param model
	 * @param satisfactionSearch
	 * @return
	 */
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/satisfaction/view.do", method=RequestMethod.GET)
	public String statisfactionMenuViewGet(Model model, @CurrentSite Site currentSite,  @ModelAttribute Satisfaction satisfaction, @ModelAttribute("menu") Menu menu){
		satisfaction.setSitePrefix(currentSite.getSitePrefix());
		Satisfaction satisfactionModel = satisfactionService.getSatisfaction(satisfaction);
		//만족도조사 정보가 없을경우 - 초기 메뉴만 생성되고 만족도 로우가 생성 안된경우만 해당
		if(satisfactionModel == null ){
			satisfactionService.updateSatisfaction(satisfaction);
			satisfactionModel = satisfaction;
		}
		model.addAttribute("satisfaction", satisfactionModel);
		//메뉴 목록
		menu.setSitePrefix(currentSite.getSitePrefix());
		model.addAttribute("menu", menuService.getMenu(menu));
		return "asapro/admin/satisfaction/view";
	}
	
	/**
	 * 만족도 평가 데이터 초기화
	 * @param model
	 * @param menuId
	 * @return
	 */
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/satisfaction/opinionList.do", method=RequestMethod.GET)
	public String satisOpinionListGet(Model model, @CurrentSite Site currentSite, @ModelAttribute SatisOpinionSearch satisOpinionSearch){
		satisOpinionSearch.fixBrokenSvByDefaultCharsets();
		satisOpinionSearch.setSitePrefix(currentSite.getSitePrefix());
		List<SatisOpinion> satisOpinionList = satisfactionService.getSatisOpinionList(satisOpinionSearch);
		
		Paging paging = new Paging(satisOpinionList, satisOpinionList.size(), satisOpinionSearch);
		model.addAttribute("paging", paging);
		return "asapro/admin/satisfaction/opinionList";
	}
	
	/**
	 * 만족도 평가 데이터 초기화
	 * @param model
	 * @param menuId
	 * @return
	 */
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/satisfaction/reset.do", method=RequestMethod.POST)
	@ResponseBody
	public ServerMessage resetSatisfaction(Model model, @CurrentSite Site currentSite, @RequestParam(value="menuId", required=true) String menuId){
		ServerMessage serverMessage = new ServerMessage();
		if( StringUtils.isBlank(menuId) ){
			serverMessage.setSuccess(false);
			serverMessage.setText("메뉴아이디는 필수선택입니다.");
		} else {
			Satisfaction satisfaction = new Satisfaction();
			satisfaction.setSitePrefix(currentSite.getSitePrefix());
			satisfaction.setMenuId(menuId);
			satisfaction.setSatisResetDate(new Date());
			if( satisfactionService.resetSatisfaction(satisfaction) > 0 ){
				serverMessage.setSuccess(true);
			} else {
				serverMessage.setSuccess(false);
				serverMessage.setText("서버에러로 초기화하지 못하였습니다.");
			}
		}
		return serverMessage;
	}
}
