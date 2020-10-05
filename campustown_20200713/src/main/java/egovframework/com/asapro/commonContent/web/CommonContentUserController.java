/**
 * 
 */
package egovframework.com.asapro.commonContent.web;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import egovframework.com.asapro.code.service.Code;
import egovframework.com.asapro.code.service.CodeService;
import egovframework.com.asapro.commonContent.service.CommonContent;
import egovframework.com.asapro.commonContent.service.CommonContentSearch;
import egovframework.com.asapro.commonContent.service.CommonContentService;
import egovframework.com.asapro.content.service.Content;
import egovframework.com.asapro.content.service.ContentSearch;
import egovframework.com.asapro.menu.service.Menu;
import egovframework.com.asapro.site.service.Site;
import egovframework.com.asapro.support.Constant;
import egovframework.com.asapro.support.annotation.CurrentMenu;
import egovframework.com.asapro.support.annotation.CurrentSite;
import egovframework.com.asapro.support.pagination.Paging;
import egovframework.com.asapro.support.util.AsaproUtils;
import egovframework.com.asapro.support.util.EtcUtil;

/**
 * 공통 콘텐츠 사용자 컨트롤러
 * @author yckim
 * @since 2019. 2. 25.
 *
 */
@Controller
public class CommonContentUserController {
	
	@Autowired
	private CommonContentService commonContentService;
	
	@Autowired
	private CodeService codeService;
	
	@Autowired
	private HttpServletRequest request;
	
	/**
	 * 공통콘텐츠 뷰를 반환한다.
	 * @param model
	 * @param currentSite
	 * @param commonContentForm
	 * @return 공통콘텐츠 뷰
	 */
	@RequestMapping(value=Constant.APP_PATH + Constant.SITE_ID_PATH + "/common/content/{comContId}", method=RequestMethod.GET)
	public String commonContentGet(Model model, @CurrentSite Site currentSite, @ModelAttribute("commonContentForm") CommonContent commonContentForm){
		
		commonContentForm.setSitePrefix(currentSite.getSitePrefix());
		CommonContent commonContent = commonContentService.getCommonContent(commonContentForm);
		model.addAttribute("commonContent", commonContent);
		
		return  AsaproUtils.getThemePath(request) +  "/commonContent/view";
	}
	
	/**
	 * 공통콘텐츠 뷰를 반환한다.
	 * @param model
	 * @param currentSite
	 * @param commonContentForm
	 * @return 공통콘텐츠 뷰
	 */
	@RequestMapping(value=Constant.APP_PATH + Constant.SITE_ID_PATH + "/common/content/list", method=RequestMethod.GET)
	public String commonContentListGet(Model model, @CurrentSite Site currentSite, @ModelAttribute("commonContentSearch") CommonContentSearch commonContentSearch){
		
		commonContentSearch.setSitePrefix(currentSite.getSitePrefix());
		commonContentSearch.fixBrokenSvByDefaultCharsets();
		List<CommonContent> list = commonContentService.getCommonContentList(commonContentSearch);
		int total = commonContentService.getCommonContentListTotal(commonContentSearch);
		
		Paging paging = new Paging(list, total, commonContentSearch);
		model.addAttribute("paging", paging);
		
		List<Code> moduleCodeList = codeService.getCodeList("moduleCode");	//프로그램 코드목록
		model.addAttribute("moduleCodeList", moduleCodeList);
		
		List<Code> cate1CodeList = codeService.getCodeList("commContentCate1");	//카테고리1 코드목록
		model.addAttribute("cate1CodeList", cate1CodeList);
		List<Code> cate2CodeList = codeService.getCodeList("commContentCate2");	//카테고리2 코드목록
		model.addAttribute("cate2CodeList", cate2CodeList);
		List<Code> cate3CodeList = codeService.getCodeList("commContentCate3");	//카테고리3 코드목록
		model.addAttribute("cate3CodeList", cate3CodeList);
		
		//카테고리1 배열에 담는다
		if(StringUtils.isNotBlank(commonContentSearch.getComContCate1()) ){
			String[] selectComContCate1Array = commonContentSearch.getComContCate1().split(",");
			model.addAttribute("selectComContCate1Array", selectComContCate1Array);
		}
		//카테고리2 배열에 담는다
		if(StringUtils.isNotBlank(commonContentSearch.getComContCate2()) ){
			String[] selectComContCate2Array = commonContentSearch.getComContCate2().split(",");
			model.addAttribute("selectComContCate2Array", selectComContCate2Array);
		}
		//카테고리3 배열에 담는다
		if(StringUtils.isNotBlank(commonContentSearch.getComContCate3()) ){
			String[] selectComContCate3Array = commonContentSearch.getComContCate3().split(",");
			model.addAttribute("selectComContCate3Array", selectComContCate3Array);
		}
		
		return  AsaproUtils.getThemePath(request) +  "/commonContent/list";
	}
	
	/**
	 * 공통콘텐츠를 조회하고 json으로 반환한다.
	 * @param model
	 * @param currentSite
	 * @param commonContentForm
	 * @return 공통콘텐츠 json
	 */
	@RequestMapping(value=Constant.APP_PATH + Constant.SITE_ID_PATH + "/common/content" + Constant.API_PATH + "/view", method=RequestMethod.GET)
	@ResponseBody
	public CommonContent commonContentJsonGet(Model model, @CurrentSite Site currentSite, @ModelAttribute("commonContentForm") CommonContent commonContentForm){
		
		commonContentForm.setSitePrefix(currentSite.getSitePrefix());
		CommonContent commonContent = commonContentService.getCommonContent(commonContentForm);
		
		return  commonContent;
	}
	
}
