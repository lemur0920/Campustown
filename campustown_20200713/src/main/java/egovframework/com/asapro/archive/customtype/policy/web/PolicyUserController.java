/**
 * 
 */
package egovframework.com.asapro.archive.customtype.policy.web;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import egovframework.com.asapro.archive.customtype.policy.service.Policy;
import egovframework.com.asapro.archive.customtype.policy.service.PolicySearch;
import egovframework.com.asapro.archive.customtype.policy.service.PolicyService;
import egovframework.com.asapro.archive.service.ArchiveCategory;
import egovframework.com.asapro.archive.service.ArchiveService;
import egovframework.com.asapro.code.service.Code;
import egovframework.com.asapro.code.service.CodeCategory;
import egovframework.com.asapro.code.service.CodeService;
import egovframework.com.asapro.content_statis.service.ContentStatisSearch;
import egovframework.com.asapro.site.service.Site;
import egovframework.com.asapro.support.Constant;
import egovframework.com.asapro.support.annotation.CurrentSite;
import egovframework.com.asapro.support.pagination.Paging;
import egovframework.com.asapro.support.util.AsaproUtils;

/**
 * 정책자료 사용자 컨트롤러
 * 커스텀 아카이브
 * @author yckim
 * @since 2019. 2. 28.
 *
 */
@Controller
public class PolicyUserController {
	
	@Autowired
	private ArchiveService archiveService;
	
	@Autowired
	private PolicyService policyService;
	
	@Autowired
	private CodeService codeService;
	
	@Autowired
	private HttpServletRequest request;
	
	
	
	/**
	 * 메타코드 로드 및 기타 공통코드 로드
	 * @param model
	 * @param archiveCategory
	 */
	private void loadCode(Model model, ArchiveCategory archiveCategory){
		CodeCategory arcMetaCode1Info = null;
		List<Code> arcMetaCode1List = null;
		CodeCategory arcMetaCode2Info = null;
		List<Code> arcMetaCode2List = null;
		CodeCategory arcMetaCode3Info = null;
		List<Code> arcMetaCode3List = null;
		
		if( StringUtils.isNotBlank(archiveCategory.getCatMetaCode1()) ){
			arcMetaCode1Info = codeService.getCodeCategory(archiveCategory.getCatMetaCode1());
			arcMetaCode1List = codeService.getCodeList(archiveCategory.getCatMetaCode1());
			model.addAttribute("arcMetaCode1Info", arcMetaCode1Info );
			model.addAttribute("arcMetaCode1List", arcMetaCode1List );
		}
		if( StringUtils.isNotBlank(archiveCategory.getCatMetaCode2()) ){
			arcMetaCode2Info = codeService.getCodeCategory(archiveCategory.getCatMetaCode2());
			arcMetaCode2List = codeService.getCodeList(archiveCategory.getCatMetaCode2());
			model.addAttribute("arcMetaCode2Info", arcMetaCode2Info );
			model.addAttribute("arcMetaCode2List", arcMetaCode2List );
		}
		if( StringUtils.isNotBlank(archiveCategory.getCatMetaCode3()) ){
			arcMetaCode3Info = codeService.getCodeCategory(archiveCategory.getCatMetaCode3());
			arcMetaCode3List = codeService.getCodeList(archiveCategory.getCatMetaCode3());
			model.addAttribute("arcMetaCode3Info", arcMetaCode3Info );
			model.addAttribute("arcMetaCode3List", arcMetaCode3List );
		}
		
		
	}
	
	//=============================================================================================================================
	//==========================================  정책자료    ================================================================
	//=============================================================================================================================
	
	
	/**
	 * 정책자료 목록을 조회한다.
	 * @param model
	 * @param currentSite
	 * @param policySearch
	 * @return 정책자료 목록
	 */
	@RequestMapping(value=Constant.APP_PATH + Constant.SITE_ID_PATH + "/archive/policy/{arcCategory}", method=RequestMethod.GET)
	public String policyListGet(Model model, @CurrentSite Site currentSite, @ModelAttribute("policySearch") PolicySearch policySearch){
		//카테고리 조회
		ArchiveCategory currentCategory = null;
		if( StringUtils.isNotBlank(policySearch.getArcCategory()) ){
			currentCategory = new ArchiveCategory();
			currentCategory.setSitePrefix(currentSite.getSitePrefix());
			currentCategory.setCatId(policySearch.getArcCategory());
			currentCategory = archiveService.getArchiveCategory(currentCategory);
		}
		model.addAttribute("archiveCategory", currentCategory);
		
		//정책자료 목록 조회
		policySearch.setSitePrefix(currentSite.getSitePrefix());
		policySearch.fixBrokenSvByDefaultCharsets();
		policySearch.setPaging(true);
		policySearch.setArcUse(true);
		
		if(currentCategory != null){
			
			//메타코드 로드
			this.loadCode(model, currentCategory);
			
			//페이지당 개수 - 페이지 사이즈가 10이 아닐 경우 카테고리 템플릿에서 페이지 사이즈 설정하는 기능 있으면 거기도 같이 맞춰줘야 함
			//if( postSearch.getPageSize().intValue() < archiveCategory.getCatPageSize() ){
			//카테고리 설정사이즈 10 이하도 적용되게 변경
			if(currentCategory.getCatPageSize() >= 10){//카테고리설정값이 10 이상이면
				if( policySearch.getPageSize().intValue() < currentCategory.getCatPageSize() ){
					policySearch.setPageSize(currentCategory.getCatPageSize());
				}
			}else{//카테고리 설정값이 10 미만이면 설정값으로만 적용되게 - 사용자를 탬플릿 페이지 사이즈를 변경할 수 없다.
				policySearch.setPageSize(currentCategory.getCatPageSize());
			}
			
			//기본 정렬 세팅 - 파라메터에서 넘어온 정렬값이 있으면 적용되지 않는다.
			policySearch.setDefaultSort(currentCategory.getCatSortOrder(), currentCategory.getCatSortDirection());
		}
		
		List<Policy> list = policyService.getPolicyList(policySearch);
		int total = policyService.getPolicyListTotal(policySearch);
		Paging paging = new Paging(list, total, policySearch);
		model.addAttribute("paging", paging);
		
		//메타코드를 카테고리로 사용시 검색대상을 다중으로 or검색을 할경우를 대비하여 사용
		//카테고리1 배열에 담는다
		if(StringUtils.isNotBlank(policySearch.getMetaCode1()) ){
			String[] selectMetaCode1Array = policySearch.getMetaCode1().split(",");
			model.addAttribute("selectMetaCode1Array", selectMetaCode1Array);
		}
		//카테고리2 배열에 담는다
		if(StringUtils.isNotBlank(policySearch.getMetaCode2()) ){
			String[] selectMetaCode2Array = policySearch.getMetaCode2().split(",");
			model.addAttribute("selectMetaCode2Array", selectMetaCode2Array);
		}
		//카테고리3 배열에 담는다
		if(StringUtils.isNotBlank(policySearch.getMetaCode3()) ){
			String[] selectMetaCode3Array = policySearch.getMetaCode3().split(",");
			model.addAttribute("selectMetaCode3Array", selectMetaCode3Array);
		}
		
		//템플릿 파일 적용
		if(currentCategory != null && StringUtils.isNotBlank(currentCategory.getCatUserListTemplate()) ){
			return archiveService.getArchiveTemplateName(currentCategory.getCatCustomType(), currentCategory.getCatUserListTemplate(), "user");
		}
		return AsaproUtils.getThemePath(request) + "archive/policy/list";
	}

	/**
	 * 정책자료 조회뷰를 반환한다.
	 * @param model
	 * @param currentSite
	 * @param policyForm
	 * @return 정책자료뷰 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	@RequestMapping(value=Constant.APP_PATH + Constant.SITE_ID_PATH + "/archive/policy/{arcCategory}/{arcId}", method=RequestMethod.GET)
	public String policyViewGet(Model model, @CurrentSite Site currentSite, @ModelAttribute("policyForm") Policy policyForm) throws IllegalAccessException, InvocationTargetException {
		//정책자료 조회
		policyForm.setSitePrefix(currentSite.getSitePrefix());
		Policy policyModel = policyService.getPolicy(policyForm);
		policyModel.setSitePrefix(currentSite.getSitePrefix());
		model.addAttribute("policyForm", policyModel);

		//카테고리 조회
		ArchiveCategory currentCategory = null;
		if( StringUtils.isNotBlank(policyModel.getArcCategory()) ){
			currentCategory = new ArchiveCategory();
			currentCategory.setSitePrefix(currentSite.getSitePrefix());
			currentCategory.setCatId(policyModel.getArcCategory());
			currentCategory = archiveService.getArchiveCategory(currentCategory);
			
			//메타코드 로드
			this.loadCode(model, currentCategory);
			
		}
		model.addAttribute("archiveCategory", currentCategory);
		
		
		//공공누리코드목록
		//model.addAttribute("nuriCodeList", codeService.getCodeList("NURI_TYPE"));
		
		//목록 돌아가기 검색VO
		PolicySearch backListSearch = new PolicySearch();
		BeanUtils.populate(backListSearch, request.getParameterMap());
		backListSearch.fixBrokenSvByDefaultCharsets();
		model.addAttribute("backListSearch", backListSearch);
		
		//템플릿 파일 적용
		if(currentCategory != null && StringUtils.isNotBlank(currentCategory.getCatUserViewTemplate()) ){
			return archiveService.getArchiveTemplateName(currentCategory.getCatCustomType(), currentCategory.getCatUserViewTemplate(), "user");
		}
		return AsaproUtils.getThemePath(request) + "archive/policy/view";
	}
	
	//=================================  베스트 추출  ===============================================
	/**
	 * 추천 베스트 정책자료 목록을 조회한다.
	 * @param model
	 * @param currentSite
	 * @param policySearch
	 * @return 정책자료 목록
	 */
	@RequestMapping(value=Constant.APP_PATH + Constant.SITE_ID_PATH + "/archive/policy/recommendBest/{arcCategory}", method=RequestMethod.GET)
	public String policyRecommendBestListGet(Model model, @CurrentSite Site currentSite, @ModelAttribute("policySearch") PolicySearch policySearch){
		//카테고리 조회
		ArchiveCategory currentCategory = null;
		if( StringUtils.isNotBlank(policySearch.getArcCategory()) ){
			currentCategory = new ArchiveCategory();
			currentCategory.setSitePrefix(currentSite.getSitePrefix());
			currentCategory.setCatId(policySearch.getArcCategory());
			currentCategory = archiveService.getArchiveCategory(currentCategory);
		}
		model.addAttribute("archiveCategory", currentCategory);
		
		//정책자료 목록 조회
		policySearch.setSitePrefix(currentSite.getSitePrefix());
		policySearch.fixBrokenSvByDefaultCharsets();
		policySearch.setPaging(true);
		policySearch.setPageSize(4);
		policySearch.setArcUse(true);
		policySearch.setSortOrder("ARC_RECOMMEND");
		policySearch.setSortDirection("DESC");
		
		if(currentCategory != null){
			
			//메타코드 로드
			this.loadCode(model, currentCategory);
			
			//페이지당 개수 - 페이지 사이즈가 10이 아닐 경우 카테고리 템플릿에서 페이지 사이즈 설정하는 기능 있으면 거기도 같이 맞춰줘야 함
			//if( postSearch.getPageSize().intValue() < archiveCategory.getCatPageSize() ){
			//카테고리 설정사이즈 10 이하도 적용되게 변경
			/*
			if(currentCategory.getCatPageSize() >= 10){//카테고리설정값이 10 이상이면
				if( policySearch.getPageSize().intValue() < currentCategory.getCatPageSize() ){
					policySearch.setPageSize(currentCategory.getCatPageSize());
				}
			}else{//카테고리 설정값이 10 미만이면 설정값으로만 적용되게 - 사용자를 탬플릿 페이지 사이즈를 변경할 수 없다.
				policySearch.setPageSize(currentCategory.getCatPageSize());
			}
			*/
			//기본 정렬 세팅 - 파라메터에서 넘어온 정렬값이 있으면 적용되지 않는다.
			policySearch.setDefaultSort(currentCategory.getCatSortOrder(), currentCategory.getCatSortDirection());
		}
		
		List<Policy> bestList = policyService.getPolicyList(policySearch);
		int total = policyService.getPolicyListTotal(policySearch);
		Paging paging = new Paging(bestList, total, policySearch);
		model.addAttribute("paging", paging);
		
		//메타코드를 카테고리로 사용시 검색대상을 다중으로 or검색을 할경우를 대비하여 사용
		//카테고리1 배열에 담는다
		if(StringUtils.isNotBlank(policySearch.getMetaCode1()) ){
			String[] selectMetaCode1Array = policySearch.getMetaCode1().split(",");
			model.addAttribute("selectMetaCode1Array", selectMetaCode1Array);
		}
		//카테고리2 배열에 담는다
		if(StringUtils.isNotBlank(policySearch.getMetaCode2()) ){
			String[] selectMetaCode2Array = policySearch.getMetaCode2().split(",");
			model.addAttribute("selectMetaCode2Array", selectMetaCode2Array);
		}
		//카테고리3 배열에 담는다
		if(StringUtils.isNotBlank(policySearch.getMetaCode3()) ){
			String[] selectMetaCode3Array = policySearch.getMetaCode3().split(",");
			model.addAttribute("selectMetaCode3Array", selectMetaCode3Array);
		}
		
		//템플릿 파일 적용
		/*if(currentCategory != null && StringUtils.isNotBlank(currentCategory.getCatUserListTemplate()) ){
			return archiveService.getArchiveTemplateName(currentCategory.getCatCustomType(), currentCategory.getCatUserListTemplate(), "user");
		}*/
		return AsaproUtils.getThemePath(request) + "archive/policy/recomBestList";
	}
	

	/**
	 * 정책 주간,월간 베스트 목록을 조회한다.
	 * @param model
	 * @param currentSite
	 * @param archiveSearch
	 * @return 정책 주간,월간 베스트 목록
	 */
	@RequestMapping(value=Constant.APP_PATH + Constant.SITE_ID_PATH + "/archive/policy/recommend/termBest/{arcCategory}", method=RequestMethod.GET)
	public String archiveRecommendBestListGet(Model model, @CurrentSite Site currentSite, @ModelAttribute("policySearch") PolicySearch policySearch){
		Calendar cal = Calendar.getInstance();
		ContentStatisSearch contentStatisSearch = new ContentStatisSearch();
		contentStatisSearch.setPageSize(5);
		
		contentStatisSearch.setSitePrefix(currentSite.getSitePrefix());
		contentStatisSearch.setCsModiulCode("archive");
		contentStatisSearch.setCsModiulSubCode("");
		contentStatisSearch.setCsCateCode(policySearch.getArcCategory());
		
		Date startDate = AsaproUtils.makeCalculDate(0);
		//cal.setTime(startDate);
		contentStatisSearch.setStartDate(AsaproUtils.getFormattedDate(startDate));
		
		Date end7Date = AsaproUtils.makeCalculDate(-6);
		//cal.setTime(end7Date);
		contentStatisSearch.setEndDate(AsaproUtils.getFormattedDate(end7Date));
		List<Policy> term7BestList = policyService.getRecommendTermBestPolicyList(contentStatisSearch);
		model.addAttribute("term7BestList", term7BestList);
		
		Date end30Date = AsaproUtils.makeCalculDate(-29);
		//cal.setTime(end7Date);
		contentStatisSearch.setEndDate(AsaproUtils.getFormattedDate(end30Date));
		List<Policy> term30BestList = policyService.getRecommendTermBestPolicyList(contentStatisSearch);
		model.addAttribute("term30BestList", term30BestList);
		
		//int total = policyService.getPolicyListTotal(policySearch);
		//Paging paging = new Paging(bestList, total, policySearch);
		//model.addAttribute("paging", paging);
		
		return AsaproUtils.getThemePath(request) + "archive/policy/recomTermBestList";
	}
}
