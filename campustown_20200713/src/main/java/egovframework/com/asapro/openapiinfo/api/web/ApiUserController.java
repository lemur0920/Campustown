package egovframework.com.asapro.openapiinfo.api.web;

import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import egovframework.com.asapro.site.service.Site;
import egovframework.com.asapro.support.Constant;
import egovframework.com.asapro.support.annotation.CurrentSite;
import egovframework.com.asapro.support.pagination.Paging;
import egovframework.com.asapro.support.util.AsaproUtils;
import egovframework.com.asapro.openapiinfo.api.service.ApiService;
import egovframework.com.asapro.openapiinfo.api.service.JobEduCenter;
import egovframework.com.asapro.openapiinfo.api.service.JobEduCenterSearch;
import egovframework.com.asapro.openapiinfo.api.service.JobNewDealBiz;
import egovframework.com.asapro.openapiinfo.api.service.JobNewDealBizSearch;
import egovframework.com.asapro.openapiinfo.api.service.JynEmpSpt;
import egovframework.com.asapro.openapiinfo.api.service.JynEmpSptSearch;
import egovframework.com.asapro.openapiinfo.api.service.PublicEmplInfo;
import egovframework.com.asapro.openapiinfo.api.service.PublicEmplInfoSearch;
import egovframework.com.asapro.openapiinfo.api.service.RegionEventInfo;
import egovframework.com.asapro.openapiinfo.api.service.RegionEventInfoSearch;
import egovframework.com.asapro.openapiinfo.api.service.YouthActSupInfo;
import egovframework.com.asapro.openapiinfo.api.service.YouthActSupInfoSearch;
import egovframework.com.asapro.openapiinfo.api.service.YouthProgram;
import egovframework.com.asapro.openapiinfo.api.service.YouthProgramSearch;

@Controller
public class ApiUserController {

	@Autowired
	private ApiService apiService;
	
	@Autowired
	private HttpServletRequest request;
	
	/**
	 * 뉴딜일자리 정보 목록을 반환한다.
	 * @param model
	 * @param currentSite
	 * @param jobNewDealBizSearch
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping(value={Constant.APP_PATH + Constant.SITE_ID_PATH + "/youth/jobNewDealBiz/list"}, method=RequestMethod.GET)
	public String jobNewDealBizListGet(Model model, @CurrentSite Site currentSite, @ModelAttribute("jobNewDealBizSearch") JobNewDealBizSearch jobNewDealBizSearch) throws ParseException {
		jobNewDealBizSearch.setSitePrefix(currentSite.getSitePrefix());
		jobNewDealBizSearch.fixBrokenSvByDefaultCharsets();
		jobNewDealBizSearch.setPaging(true);
		
		List<JobNewDealBiz> list = apiService.getJobNewDealBizList(jobNewDealBizSearch);
		int total = apiService.getJobNewDealBizListTotal(jobNewDealBizSearch);
		Paging paging = new Paging(list, total, jobNewDealBizSearch);
		
		model.addAttribute("paging", paging);
		
		return AsaproUtils.getThemePath(request) + "openApi/jobNewDealBiz/list";
	}
	
	/**
	 *  뉴딜일자리 정보 상세보기를 반환한다.
	 * @param model
	 * @param currentSite
	 * @param jobNewDealBizForm
	 * @return
	 * @throws ParseException
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	@RequestMapping(value={Constant.APP_PATH + Constant.SITE_ID_PATH + "/youth/jobNewDealBiz/view"}, method=RequestMethod.GET)
	public String jobNewDealBizViewGet(Model model, @CurrentSite Site currentSite, @ModelAttribute("jobNewDealBizForm") JobNewDealBiz jobNewDealBizForm) throws ParseException, IllegalAccessException, InvocationTargetException {
		JobNewDealBiz jobNewDealBizModel = apiService.getJobNewDealBiz(jobNewDealBizForm);
		model.addAttribute("jobNewDealBizForm", jobNewDealBizModel);
		
		//목록 돌아가기 검색VO
		JobNewDealBizSearch backListSearch = new JobNewDealBizSearch();
		BeanUtils.populate(backListSearch, request.getParameterMap());
		backListSearch.fixBrokenSvByDefaultCharsets();
		model.addAttribute("backListSearch", backListSearch);
		
		return AsaproUtils.getThemePath(request) + "openApi/jobNewDealBiz/view";
	}
	
	
	/**
	 * 서울일자리센터 교육정보 목록을 반환한다.
	 * @param model
	 * @param currentSite
	 * @param jobEduCenterSearch
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping(value={Constant.APP_PATH + Constant.SITE_ID_PATH + "/youth/jobEduCenter/list"}, method=RequestMethod.GET)
	public String jobEduCenterListGet(Model model, @CurrentSite Site currentSite, @ModelAttribute("jobEduCenterSearch") JobEduCenterSearch jobEduCenterSearch) throws ParseException {
		jobEduCenterSearch.setSitePrefix(currentSite.getSitePrefix());
		jobEduCenterSearch.fixBrokenSvByDefaultCharsets();
		jobEduCenterSearch.setPaging(true);
		
		List<JobEduCenter> list = apiService.getJobEduCenterList(jobEduCenterSearch);
		int total = apiService.getJobEduCenterListTotal(jobEduCenterSearch);
		Paging paging = new Paging(list, total, jobEduCenterSearch);
		model.addAttribute("paging", paging);
		
		return AsaproUtils.getThemePath(request) + "openApi/jobEduCenter/list";
	}
	
	/**
	 * 서울일자리센터 교육정보 상세보기를 반환한다.
	 * @param model
	 * @param currentSite
	 * @param jobEduCenterForm
	 * @return
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	@RequestMapping(value=Constant.APP_PATH + Constant.SITE_ID_PATH + "/youth/jobEduCenter/view", method=RequestMethod.GET)
	public String jobEduCenterViewGet(Model model, @CurrentSite Site currentSite, @ModelAttribute("jobEduCenterForm") JobEduCenter jobEduCenterForm) throws IllegalAccessException, InvocationTargetException{
		JobEduCenter jobEduCenterModel = apiService.getJobEduCenter(jobEduCenterForm);
		model.addAttribute("jobEduCenterForm", jobEduCenterModel );
		
		//목록 돌아가기 검색VO
		JobEduCenterSearch backListSearch = new JobEduCenterSearch();
		BeanUtils.populate(backListSearch, request.getParameterMap());
		backListSearch.fixBrokenSvByDefaultCharsets();
		model.addAttribute("backListSearch", backListSearch);
		
		return AsaproUtils.getThemePath(request) + "openApi/jobEduCenter/view";
	}
	
	
	/**
	 * 청년취업정책 정보 목록을 반환한다.
	 * @param model
	 * @param currentSite
	 * @param jynEmpSptSearch
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping(value={Constant.APP_PATH + Constant.SITE_ID_PATH + "/youth/jynEmpSpt/list"}, method=RequestMethod.GET)
	public String jynEmpSptListGet(Model model, @CurrentSite Site currentSite, @ModelAttribute("jynEmpSptSearch") JynEmpSptSearch jynEmpSptSearch) throws ParseException {
		jynEmpSptSearch.setSitePrefix(currentSite.getSitePrefix());
		jynEmpSptSearch.fixBrokenSvByDefaultCharsets();
		jynEmpSptSearch.setPaging(true);
		
		int total = apiService.getJynEmpSptListTotal(jynEmpSptSearch);
		List<JynEmpSpt> list = apiService.getJynEmpSptList(jynEmpSptSearch);
		Paging paging = new Paging(list, total, jynEmpSptSearch);
		
		model.addAttribute("paging", paging);
		
		return AsaproUtils.getThemePath(request) + "openApi/jynEmpSpt/list";
	}
	
	/**
	 * 청년취업정책 정보 상세보기를 반환한다.
	 * @param model
	 * @param currentSite
	 * @param jynEmpSptForm
	 * @return
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	@RequestMapping(value=Constant.APP_PATH + Constant.SITE_ID_PATH + "/youth/jynEmpSpt/view", method=RequestMethod.GET)
	public String jynEmpSptViewGet(Model model, @CurrentSite Site currentSite, @ModelAttribute("jynEmpSptForm") JynEmpSpt jynEmpSptForm) throws IllegalAccessException, InvocationTargetException{
		JynEmpSpt jynEmpSptModel = apiService.getJynEmpSpt(jynEmpSptForm);
		model.addAttribute("jynEmpSptForm", jynEmpSptModel );

		//목록 돌아가기 검색VO
		JynEmpSptSearch backListSearch = new JynEmpSptSearch();
		BeanUtils.populate(backListSearch, request.getParameterMap());
		backListSearch.fixBrokenSvByDefaultCharsets();
		model.addAttribute("backListSearch", backListSearch);		
		
		return  AsaproUtils.getThemePath(request) + "openApi/jynEmpSpt/view";
	}
	
	/**
	 * 공공기관 채용 정보 목록을 반환한다.
	 * @param model
	 * @param currentSite
	 * @param publicEmplInfoSearch
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping(value={Constant.APP_PATH + Constant.SITE_ID_PATH + "/youth/publicEmplInfo/list"}, method=RequestMethod.GET)
	public String publicEmplInfoListGet(Model model, @CurrentSite Site currentSite, @ModelAttribute("publicEmplInfoSearch") PublicEmplInfoSearch publicEmplInfoSearch) throws ParseException {
		publicEmplInfoSearch.setSitePrefix(currentSite.getSitePrefix());
		publicEmplInfoSearch.fixBrokenSvByDefaultCharsets();
		publicEmplInfoSearch.setPaging(true);
		
		int total = apiService.getPublicEmplInfoListTotal(publicEmplInfoSearch);
		List<PublicEmplInfo> list = apiService.getPublicEmplInfoList(publicEmplInfoSearch);
		Paging paging = new Paging(list, total, publicEmplInfoSearch);
		
		model.addAttribute("paging", paging);
		
		return AsaproUtils.getThemePath(request) + "openApi/publicEmplInfo/list";
	}
	
	/**
	 * 공공취업정보 상세보기를 반환한다.
	 * @param model
	 * @param currentSite
	 * @param publicEmplInfoForm
	 * @return
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	@RequestMapping(value=Constant.APP_PATH + Constant.SITE_ID_PATH + "/youth/publicEmplInfo/view", method=RequestMethod.GET)
	public String publicEmplInfoViewGet(Model model, @CurrentSite Site currentSite, @ModelAttribute("publicEmplInfoForm") PublicEmplInfo publicEmplInfoForm) throws IllegalAccessException, InvocationTargetException{
		PublicEmplInfo publicEmplInfoModel = apiService.getPublicEmplInfo(publicEmplInfoForm);
		
		//목록 돌아가기 검색VO
		PublicEmplInfoSearch backListSearch = new PublicEmplInfoSearch();
		BeanUtils.populate(backListSearch, request.getParameterMap());
		backListSearch.fixBrokenSvByDefaultCharsets();
		model.addAttribute("backListSearch", backListSearch);		

		String contents = publicEmplInfoModel.getContents();
		//이스케이프 데이터 치환
		contents.replaceAll("&quot;", "\"");
		contents = contents.replaceAll("&#39;", "\'");
		contents = contents.replaceAll("&lt;", "<");
		contents = contents.replaceAll("&gt;", ">");
		contents = contents.replaceAll("&amp;", "&");
		contents = contents.replaceAll("&nbsp;", " ");

		publicEmplInfoModel.setContents(contents);
		model.addAttribute("publicEmplInfoForm", publicEmplInfoModel );
		return AsaproUtils.getThemePath(request) + "openApi/publicEmplInfo/view";
	}
	
	/**
	 * 지역별행사정보 목록을 반환한다.
	 * @param model
	 * @param currentSite
	 * @param regionEventInfoSearch
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping(value={Constant.APP_PATH + Constant.SITE_ID_PATH + "/youth/regionEventInfo/list"}, method=RequestMethod.GET)
	public String regionEventInfoListGet(Model model, @CurrentSite Site currentSite, @ModelAttribute("regionEventInfoSearch") RegionEventInfoSearch regionEventInfoSearch) throws ParseException {
		regionEventInfoSearch.setSitePrefix(currentSite.getSitePrefix());
		regionEventInfoSearch.fixBrokenSvByDefaultCharsets();
		regionEventInfoSearch.setPaging(true);
		
		int total = apiService.getRegionEventInfoListTotal(regionEventInfoSearch);
		List<RegionEventInfo> list = apiService.getRegionEventInfoList(regionEventInfoSearch);
		Paging paging = new Paging(list, total, regionEventInfoSearch);
		
		model.addAttribute("paging", paging);
		
		return AsaproUtils.getThemePath(request) + "openApi/regionEventInfo/list";
	}
	
	/**
	 * 지역별행사정보 상세보기를 반환한다.
	 * @param model
	 * @param currentSite
	 * @param regionEventInfoForm
	 * @return
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	@RequestMapping(value=Constant.APP_PATH + Constant.SITE_ID_PATH + "/youth/regionEventInfo/view", method=RequestMethod.GET)
	public String regionEventInfoViewGet(Model model, @CurrentSite Site currentSite, @ModelAttribute("regionEventInfoForm") RegionEventInfo regionEventInfoForm) throws IllegalAccessException, InvocationTargetException{
		RegionEventInfo regionEventInfoModel = apiService.getRegionEventInfo(regionEventInfoForm);
		model.addAttribute("regionEventInfoForm", regionEventInfoModel );

		//목록 돌아가기 검색VO
		RegionEventInfoSearch backListSearch = new RegionEventInfoSearch();
		BeanUtils.populate(backListSearch, request.getParameterMap());
		backListSearch.fixBrokenSvByDefaultCharsets();
		model.addAttribute("backListSearch", backListSearch);
		
		return AsaproUtils.getThemePath(request) + "openApi/regionEventInfo/view";
	}
	
	/**
	 * 청년활동지원정보 목록을 반환한다.
	 * @param model
	 * @param currentSite
	 * @param youthActSupInfoSearch
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping(value={Constant.APP_PATH + Constant.SITE_ID_PATH + "/youth/youthActSupInfo/list"}, method=RequestMethod.GET)
	public String youthActSupInfoListGet(Model model, @CurrentSite Site currentSite, @ModelAttribute("youthActSupInfoSearch") YouthActSupInfoSearch youthActSupInfoSearch) throws ParseException {
		youthActSupInfoSearch.setSitePrefix(currentSite.getSitePrefix());
		youthActSupInfoSearch.fixBrokenSvByDefaultCharsets();
		youthActSupInfoSearch.setPaging(true);
		
		int total = apiService.getYouthActSupInfoListTotal(youthActSupInfoSearch);
		List<YouthActSupInfo> list = apiService.getYouthActSupInfoList(youthActSupInfoSearch);
		Paging paging = new Paging(list, total, youthActSupInfoSearch);
		
		model.addAttribute("paging", paging);
		
		return AsaproUtils.getThemePath(request) + "openApi/youthActSupInfo/list";
	}
	
	/**
	 * 청년활동지원정보 상세보기를 반환한다.
	 * @param model
	 * @param currentSite
	 * @param youthActSupInfoForm
	 * @return
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	@RequestMapping(value=Constant.APP_PATH + Constant.SITE_ID_PATH + "/youth/youthActSupInfo/view", method=RequestMethod.GET)
	public String youthActSupInfoViewGet(Model model, @CurrentSite Site currentSite, @ModelAttribute("youthActSupInfoForm") YouthActSupInfo youthActSupInfoForm) throws IllegalAccessException, InvocationTargetException{
		YouthActSupInfo youthActSupInfoModel = apiService.getYouthActSupInfo(youthActSupInfoForm);
		model.addAttribute("youthActSupInfoForm", youthActSupInfoModel );

		//목록 돌아가기 검색VO
		YouthActSupInfoSearch backListSearch = new YouthActSupInfoSearch();
		BeanUtils.populate(backListSearch, request.getParameterMap());
		backListSearch.fixBrokenSvByDefaultCharsets();
		model.addAttribute("backListSearch", backListSearch);		
		
		return  AsaproUtils.getThemePath(request) + "openApi/youthActSupInfo/view";
	}
	
	/**
	 * 청년 프로그램 목록을 반환한다.
	 * @param model
	 * @param currentSite
	 * @param youthProgramSearch
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping(value={Constant.APP_PATH + Constant.SITE_ID_PATH + "/youth/youthProgram/list"}, method=RequestMethod.GET)
	public String youthProgramListGet(Model model, @CurrentSite Site currentSite, @ModelAttribute("youthProgramSearch") YouthProgramSearch youthProgramSearch) throws ParseException {
		youthProgramSearch.fixBrokenSvByDefaultCharsets();
		youthProgramSearch.setSitePrefix(currentSite.getSitePrefix());
		youthProgramSearch.setPaging(true);
		
		int total = apiService.getYouthProgramListTotal(youthProgramSearch);
		List<YouthProgram> list = apiService.getYouthProgramList(youthProgramSearch);
		Paging paging = new Paging(list, total, youthProgramSearch);
		
		model.addAttribute("paging", paging);
		
		return AsaproUtils.getThemePath(request) + "openApi/youthProgram/list";
	}
	
	/**
	 * 청년 프로그램 상세보기를 반환한다.
	 * @param model
	 * @param currentSite
	 * @param youthProgramForm
	 * @return
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	@RequestMapping(value=Constant.APP_PATH + Constant.SITE_ID_PATH + "/youth/youthProgram/view", method=RequestMethod.GET)
	public String youthProgramViewGet(Model model, @CurrentSite Site currentSite, @ModelAttribute("youthProgramForm") YouthProgram youthProgramForm) throws IllegalAccessException, InvocationTargetException{
		YouthProgram youthProgramModel = apiService.getyouthProgram(youthProgramForm);
		model.addAttribute("youthProgramForm", youthProgramModel );

		//목록 돌아가기 검색VO
		YouthProgramSearch backListSearch = new YouthProgramSearch();
		BeanUtils.populate(backListSearch, request.getParameterMap());
		backListSearch.fixBrokenSvByDefaultCharsets();
		model.addAttribute("backListSearch", backListSearch);		
		
		return AsaproUtils.getThemePath(request) + "openApi/youthProgram/view";
	}
}


