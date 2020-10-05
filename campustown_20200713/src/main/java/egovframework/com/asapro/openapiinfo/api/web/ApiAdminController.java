package egovframework.com.asapro.openapiinfo.api.web;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import egovframework.com.asapro.site.service.Site;
import egovframework.com.asapro.support.Constant;
import egovframework.com.asapro.support.annotation.CurrentSite;
import egovframework.com.asapro.support.pagination.Paging;
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
public class ApiAdminController {

	@Resource
	private ApiService apiService;

	
	/* =================== 뉴딜 일자리 정보 =================== */  
	/* =================== 뉴딜 일자리 정보 =================== */ 
	/**
	 * 뉴딜일자리 정보 목록을 반환한다.
	 * @param model
	 * @param currentSite
	 * @param JobNewDealBizSearch
	 * @return 목록
	 */
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/youth/jobNewDealBiz/list.do", method=RequestMethod.GET)
	public String jobNewDealBizListGet(Model model, @CurrentSite Site currentSite, @ModelAttribute("jobNewDealBizSearch") JobNewDealBizSearch jobNewDealBizSearch){
		jobNewDealBizSearch.fixBrokenSvByDefaultCharsets();
		jobNewDealBizSearch.setSitePrefix(currentSite.getSitePrefix());
		jobNewDealBizSearch.setPaging(true);
		
		int total = apiService.getJobNewDealBizListTotal(jobNewDealBizSearch);
		List<JobNewDealBiz> list = apiService.getJobNewDealBizList(jobNewDealBizSearch);
		Paging paging = new Paging(list, total, jobNewDealBizSearch);

		model.addAttribute("paging", paging);
		
		return "asapro/admin/youth/api/jobNewDealBiz/list";
	}
	
	/**
	 * 뉴딜일자리 정보 상세보기를 반환한다.
	 * @param model
	 * @param currentSite
	 * @param JobNewDealBizForm
	 * @return
	 */
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/youth/jobNewDealBiz/view.do", method=RequestMethod.GET)
	public String jobNewDealBizViewPost(Model model, @CurrentSite Site currentSite, @ModelAttribute("jobNewDealBizForm") JobNewDealBiz jobNewDealBizForm){
		JobNewDealBiz jobNewDealBizModel = apiService.getJobNewDealBiz(jobNewDealBizForm);
		model.addAttribute("jobNewDealBizForm", jobNewDealBizModel );
			return "asapro/admin/youth/api/jobNewDealBiz/view";
		
	}
	
	/* =================== 서울일자리센터 교육정보 =================== */
	/* =================== 서울일자리센터 교육정보 =================== */
	/**
	 * 서울일자리센터 교육정보 목록을 반환한다.
	 * @param model
	 * @param currentSite
	 * @param jobEduCenterSearch
	 * @return
	 */
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/youth/jobEduCenter/list.do", method=RequestMethod.GET)
	public String jobEduCenterListGet(Model model, @CurrentSite Site currentSite, @ModelAttribute("jobEduCenterSearch") JobEduCenterSearch jobEduCenterSearch){
		jobEduCenterSearch.fixBrokenSvByDefaultCharsets();
		jobEduCenterSearch.setSitePrefix(currentSite.getSitePrefix());
		jobEduCenterSearch.setPaging(true);
		
		int total = apiService.getJobEduCenterListTotal(jobEduCenterSearch);
		List<JobEduCenter> list = apiService.getJobEduCenterList(jobEduCenterSearch);
		Paging paging = new Paging(list, total, jobEduCenterSearch);
		
		model.addAttribute("paging", paging);
		
		return "asapro/admin/youth/api/jobEduCenter/list";
	}
	
	/**
	 * 서울일자리센터 교육정보 상세보기를 반환한다.
	 * @param model
	 * @param currentSite
	 * @param jobEduCenterForm
	 * @return
	 */
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/youth/jobEduCenter/view.do", method=RequestMethod.GET)
	public String jobEduCenterViewGet(Model model, @CurrentSite Site currentSite, @ModelAttribute("jobEduCenterForm") JobEduCenter jobEduCenterForm){
		JobEduCenter jobEduCenterModel = apiService.getJobEduCenter(jobEduCenterForm);
		model.addAttribute("jobEduCenterForm", jobEduCenterModel );
			return "asapro/admin/youth/api/jobEduCenter/view";
	}
	
	/* ===================  청년취업정책 정보 =================== */
	/* ===================  청년취업정책 정보 =================== */
	/**
	 * 청년취업정책 정보 목록을 반환한다.
	 * @param model
	 * @param currentSite
	 * @param jynEmpSptSearch
	 * @return
	 */
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/youth/jynEmpSpt/list.do", method=RequestMethod.GET)
	public String jynEmpSptListGet(Model model, @CurrentSite Site currentSite, @ModelAttribute("jynEmpSptSearch") JynEmpSptSearch jynEmpSptSearch){
		jynEmpSptSearch.fixBrokenSvByDefaultCharsets();
		jynEmpSptSearch.setSitePrefix(currentSite.getSitePrefix());
		jynEmpSptSearch.setPaging(true);
		
		int total = apiService.getJynEmpSptListTotal(jynEmpSptSearch);
		List<JynEmpSpt> list = apiService.getJynEmpSptList(jynEmpSptSearch);
		Paging paging = new Paging(list, total, jynEmpSptSearch);
		
		model.addAttribute("paging", paging);
		
		return "asapro/admin/youth/api/jynEmpSpt/list";
	}
	
	/**
	 * 청년취업정책 정보 상세보기를 반환한다.
	 * @param model
	 * @param currentSite
	 * @param jynEmpSptForm
	 * @return
	 */
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/youth/jynEmpSpt/view.do", method=RequestMethod.GET)
	public String jynEmpSptViewGet(Model model, @CurrentSite Site currentSite, @ModelAttribute("jynEmpSptForm") JynEmpSpt jynEmpSptForm){
		JynEmpSpt jynEmpSptModel = apiService.getJynEmpSpt(jynEmpSptForm);
		model.addAttribute("jynEmpSptForm", jynEmpSptModel );
			return "asapro/admin/youth/api/jynEmpSpt/view";
	}
	
	/* ===================  공공취업정보 =================== */
	/* ===================  공공취업정보 =================== */
	/**
	 * 공공취업정보 목록을 반환한다.
	 * @param model
	 * @param currentSite
	 * @param publicEmplInfoSearch
	 * @return
	 */
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/youth/publicEmplInfo/list.do", method=RequestMethod.GET)
	public String publicEmplInfoListGet(Model model, @CurrentSite Site currentSite, @ModelAttribute("publicEmplInfoSearch") PublicEmplInfoSearch publicEmplInfoSearch){
		publicEmplInfoSearch.fixBrokenSvByDefaultCharsets();
		publicEmplInfoSearch.setSitePrefix(currentSite.getSitePrefix());
		publicEmplInfoSearch.setPaging(true);
		
		int total = apiService.getPublicEmplInfoListTotal(publicEmplInfoSearch);
		List<PublicEmplInfo> list = apiService.getPublicEmplInfoList(publicEmplInfoSearch);
		Paging paging = new Paging(list, total, publicEmplInfoSearch);
		
		model.addAttribute("paging", paging);
		
		return "asapro/admin/youth/api/publicEmplInfo/list";
	}

	/**
	 * 공공취업정보 상세보기를 반환한다.
	 * @param model
	 * @param currentSite
	 * @param publicEmplInfoForm
	 * @return
	 */
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/youth/publicEmplInfo/view.do", method=RequestMethod.GET)
	public String publicEmplInfoViewGet(Model model, @CurrentSite Site currentSite, @ModelAttribute("publicEmplInfoForm") PublicEmplInfo publicEmplInfoForm){
		PublicEmplInfo publicEmplInfoModel = apiService.getPublicEmplInfo(publicEmplInfoForm);
		model.addAttribute("publicEmplInfoForm", publicEmplInfoModel );
			return "asapro/admin/youth/api/publicEmplInfo/view";
	}
	
	/* ===================  지역별행사정보 =================== */
	/* ===================  지역별행사정보 =================== */
	/**
	 * 지역별행사정보 목록을 반환한다.
	 * @param model
	 * @param currentSite
	 * @param regionEventInfoSearch
	 * @return
	 */
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/youth/regionEventInfo/list.do", method=RequestMethod.GET)
	public String regionEventInfoListGet(Model model, @CurrentSite Site currentSite, @ModelAttribute("regionEventInfoSearch") RegionEventInfoSearch regionEventInfoSearch){
		regionEventInfoSearch.fixBrokenSvByDefaultCharsets();
		regionEventInfoSearch.setSitePrefix(currentSite.getSitePrefix());
		regionEventInfoSearch.setPaging(true);
		
		int total = apiService.getRegionEventInfoListTotal(regionEventInfoSearch);
		List<RegionEventInfo> list = apiService.getRegionEventInfoList(regionEventInfoSearch);
		Paging paging = new Paging(list, total, regionEventInfoSearch);
		
		model.addAttribute("paging", paging);
		
		return "asapro/admin/youth/api/regionEventInfo/list";
	}

	/**
	 * 지역별행사정보 상세보기를 반환한다.
	 * @param model
	 * @param currentSite
	 * @param regionEventInfoForm
	 * @return
	 */
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/youth/regionEventInfo/view.do", method=RequestMethod.GET)
	public String regionEventInfoViewGet(Model model, @CurrentSite Site currentSite, @ModelAttribute("regionEventInfoForm") RegionEventInfo regionEventInfoForm){
		RegionEventInfo regionEventInfoModel = apiService.getRegionEventInfo(regionEventInfoForm);
		model.addAttribute("regionEventInfoForm", regionEventInfoModel );
			return "asapro/admin/youth/api/regionEventInfo/view";
	}
	
	/* ===================  청년활동지원정보 =================== */
	/* ===================  청년활동지원정보 =================== */
	/**
	 * 청년활동지원정보 목록을 반환한다.
	 * @param model
	 * @param currentSite
	 * @param youthActSupInfoSearch
	 * @return
	 */
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/youth/youthActSupInfo/list.do", method=RequestMethod.GET)
	public String youthActSupInfoListGet(Model model, @CurrentSite Site currentSite, @ModelAttribute("youthActSupInfoSearch") YouthActSupInfoSearch youthActSupInfoSearch){
		youthActSupInfoSearch.fixBrokenSvByDefaultCharsets();
		youthActSupInfoSearch.setSitePrefix(currentSite.getSitePrefix());
		youthActSupInfoSearch.setPaging(true);
		
		int total = apiService.getYouthActSupInfoListTotal(youthActSupInfoSearch);
		List<YouthActSupInfo> list = apiService.getYouthActSupInfoList(youthActSupInfoSearch);
		Paging paging = new Paging(list, total, youthActSupInfoSearch);
		
		model.addAttribute("paging", paging);
		
		return "asapro/admin/youth/api/youthActSupInfo/list";
	}

	/**
	 * 청년활동지원정보 상세보기를 반환한다.
	 * @param model
	 * @param currentSite
	 * @param youthActSupInfoForm
	 * @return
	 */
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/youth/youthActSupInfo/view.do", method=RequestMethod.GET)
	public String youthActSupInfoViewGet(Model model, @CurrentSite Site currentSite, @ModelAttribute("youthActSupInfoForm") YouthActSupInfo youthActSupInfoForm){
		YouthActSupInfo youthActSupInfoModel = apiService.getYouthActSupInfo(youthActSupInfoForm);
		model.addAttribute("youthActSupInfoForm", youthActSupInfoModel );
			return "asapro/admin/youth/api/youthActSupInfo/view";
	}
	
	/* ===================  청년 프로그램 =================== */
	/* ===================  청년 프로그램 =================== */
	/**
	 * 청년 프로그램 목록을 반환한다.
	 * @param model
	 * @param currentSite
	 * @param youthProgramSearch
	 * @return
	 */
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/youth/youthProgram/list.do", method=RequestMethod.GET)
	public String youthProgramListGet(Model model, @CurrentSite Site currentSite, @ModelAttribute("youthProgramSearch") YouthProgramSearch youthProgramSearch){
		youthProgramSearch.fixBrokenSvByDefaultCharsets();
		youthProgramSearch.setSitePrefix(currentSite.getSitePrefix());
		youthProgramSearch.setPaging(true);
		
		int total = apiService.getYouthProgramListTotal(youthProgramSearch);
		List<YouthProgram> list = apiService.getYouthProgramList(youthProgramSearch);
		Paging paging = new Paging(list, total, youthProgramSearch);
		
		model.addAttribute("paging", paging);
		
		return "asapro/admin/youth/api/youthProgram/list";
	}

	/**
	 * 청년 프로그램 상세보기를 반환한다.
	 * @param model
	 * @param currentSite
	 * @param youthProgramForm
	 * @return
	 */
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/youth/youthProgram/view.do", method=RequestMethod.GET)
	public String youthProgramViewGet(Model model, @CurrentSite Site currentSite, @ModelAttribute("youthProgramForm") YouthProgram youthProgramForm){
		YouthProgram youthProgramModel = apiService.getyouthProgram(youthProgramForm);
		model.addAttribute("youthProgramForm", youthProgramModel );
			return "asapro/admin/youth/api/youthProgram/view";
	}
}



