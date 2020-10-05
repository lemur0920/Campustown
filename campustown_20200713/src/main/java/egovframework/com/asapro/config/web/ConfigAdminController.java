/**
 * 
 */
package egovframework.com.asapro.config.web;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import egovframework.com.asapro.code.service.CodeCategorySearch;
import egovframework.com.asapro.code.service.CodeService;
import egovframework.com.asapro.config.service.Config;
import egovframework.com.asapro.config.service.ConfigService;
import egovframework.com.asapro.config.service.TextDataGroup;
import egovframework.com.asapro.site.service.Site;
import egovframework.com.asapro.support.Constant;
import egovframework.com.asapro.support.annotation.CurrentSite;
import egovframework.com.asapro.support.util.AsaproUtils;

/**
 * 설정 관리자 컨트롤러
 * @author yckim
 * @since 2018. 8. 6.
 *
 */
@Controller
public class ConfigAdminController {
	
	@Autowired
	private ConfigService configService;
	
	@Autowired
	private ConfigValidator configValidator;
	
	@Autowired
	private CodeService codeService;
	
	/**
	 * 설정목록폼 뷰를 반환한다.
	 * @param model
	 * @param configForm
	 * @return 설정목록 폼 뷰
	 */
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/config/{confId}/list.do", method=RequestMethod.GET)
	public String configListGet(Model model, @CurrentSite Site currentSite, @ModelAttribute("configForm") Config configForm, @PathVariable(value="confId") String confId){
		Config configFormModel = configService.getConfig(confId);
		model.addAttribute("configForm", configFormModel);
		model.addAttribute("confId", confId);
		
		CodeCategorySearch codeCategorySearch = new CodeCategorySearch();
		codeCategorySearch.setPaging(false);
		codeCategorySearch.setDefaultSort("CAT_NAME", "ASC");
		model.addAttribute("codeCategoryList", codeService.getCodeCategoryList(codeCategorySearch));
		
		return "asapro/admin/config/listForm";
	}
	
	/**
	 * 설정을 수정한다.
	 * @param model
	 * @param configForm
	 * @param bindingResult
	 * @return 수정 결과 뷰
	 */
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/config/{confId}/update.do", method=RequestMethod.POST)
	public String updateConfigPost(Model model, RedirectAttributes redirectAttributes, @CurrentSite Site currentSite, @ModelAttribute("configForm") Config configForm, @PathVariable(value="confId") String confId, BindingResult bindingResult){
		configValidator.validate(configForm, bindingResult);
		if( bindingResult.hasErrors() ){
			model.addAttribute("confId", confId);
			return "asapro/admin/config/listForm";
		} else {
			//ehcache적용하면서 추가
			configForm.setSitePrefix( StringUtils.isBlank(currentSite.getSitePrefix()) ? Constant.MAIN_SITE_PREFIX : currentSite.getSitePrefix() );
			configForm.setConfId(confId);
			configService.updateConfig(configForm);
			redirectAttributes.addFlashAttribute("updated", true);
			
			return "redirect:" + AsaproUtils.getAdminPath(currentSite, true) + "/config/" + confId + "/list.do";
		}
	}
	
	/**
	 * 텍스트데이터 폼 뷰를 반환한다.
	 * @param model
	 * @param configForm
	 * @return 설정목록 폼 뷰
	 */
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/config/textData/{textGroup}/list.do", method=RequestMethod.GET)
	public String textDataFormGet(Model model, @ModelAttribute("textDataForm") TextDataGroup textDataGroupForm, @PathVariable(value="textGroup") String textGroup){
		TextDataGroup textDataGroupFormModel = configService.getTextDataGroup(textGroup);
		model.addAttribute("textDataGroupForm", textDataGroupFormModel);
		model.addAttribute("textGroup", textGroup);
		return "asapro/admin/config/textDataGroupForm";
	}
	
	/**
	 * 텍스트 데이터 설정을 수정한다.
	 * @param model
	 * @param textDataGroupForm
	 * @param bindingResult
	 * @param textGroup
	 * @return
	 */
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/config/textData/{textGroup}/update.do", method=RequestMethod.POST)
	public String updateTextDataGroupPost(Model model, RedirectAttributes redirectAttributes, @CurrentSite Site currentSite, @ModelAttribute("textDataForm") TextDataGroup textDataGroupForm, BindingResult bindingResult, @PathVariable(value="textGroup") String textGroup){
		configService.updateTextDataGroup(textDataGroupForm);
		redirectAttributes.addFlashAttribute("updated", true);
		return "redirect:" + AsaproUtils.getAdminPath(currentSite, true) + "/config/textData/" + textGroup + "/list.do";
	}
}
