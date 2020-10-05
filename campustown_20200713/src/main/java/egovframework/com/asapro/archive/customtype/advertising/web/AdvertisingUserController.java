/**
 * 
 */
package egovframework.com.asapro.archive.customtype.advertising.web;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import egovframework.com.asapro.archive.customtype.advertising.service.Advertising;
import egovframework.com.asapro.archive.customtype.advertising.service.AdvertisingSearch;
import egovframework.com.asapro.archive.customtype.advertising.service.AdvertisingService;
import egovframework.com.asapro.archive.service.ArchiveCategory;
import egovframework.com.asapro.archive.service.ArchiveService;
import egovframework.com.asapro.code.service.Code;
import egovframework.com.asapro.code.service.CodeCategory;
import egovframework.com.asapro.code.service.CodeService;
import egovframework.com.asapro.site.service.Site;
import egovframework.com.asapro.support.Constant;
import egovframework.com.asapro.support.annotation.CurrentSite;
import egovframework.com.asapro.support.pagination.Paging;
import egovframework.com.asapro.support.util.AsaproUtils;

/**
 * 광고자료 사용자 컨트롤러
 * 커스텀 아카이브
 * @author yckim
 * @since 2019. 2. 28.
 *
 */
@Controller
public class AdvertisingUserController {
	
	@Autowired
	private ArchiveService archiveService;
	
	@Autowired
	private AdvertisingService advertisingService;
	
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
	//==========================================  광고자료    ================================================================
	//=============================================================================================================================
	
	
	/**
	 * 광고자료 목록을 조회한다.
	 * @param model
	 * @param currentSite
	 * @param advertisingSearch
	 * @return 광고자료 목록
	 */
	@RequestMapping(value=Constant.APP_PATH + Constant.SITE_ID_PATH + "/archive/advertising/{arcCategory}", method=RequestMethod.GET)
	public String advertisingListGet(Model model, @CurrentSite Site currentSite, @ModelAttribute("advertisingSearch") AdvertisingSearch advertisingSearch){
		//카테고리 조회
		ArchiveCategory currentCategory = null;
		if( StringUtils.isNotBlank(advertisingSearch.getArcCategory()) ){
			currentCategory = new ArchiveCategory();
			currentCategory.setSitePrefix(currentSite.getSitePrefix());
			currentCategory.setCatId(advertisingSearch.getArcCategory());
			currentCategory = archiveService.getArchiveCategory(currentCategory);
		}
		model.addAttribute("archiveCategory", currentCategory);
		
		//광고자료 목록 조회
		advertisingSearch.setSitePrefix(currentSite.getSitePrefix());
		advertisingSearch.fixBrokenSvByDefaultCharsets();
		advertisingSearch.setPaging(true);
		advertisingSearch.setArcUse(true);
		
		if(currentCategory != null){
			
			//메타코드 로드
			this.loadCode(model, currentCategory);
			
			//페이지당 개수 - 페이지 사이즈가 10이 아닐 경우 카테고리 템플릿에서 페이지 사이즈 설정하는 기능 있으면 거기도 같이 맞춰줘야 함
			//if( advertisingSearch.getPageSize().intValue() < currentCategory.getCatPageSize() ){
			//카테고리 설정사이즈 10 이하도 적용되게 변경
			if(currentCategory.getCatPageSize() >= 10){//카테고리설정값이 10 이상이면
				if( advertisingSearch.getPageSize().intValue() < currentCategory.getCatPageSize() ){
					advertisingSearch.setPageSize(currentCategory.getCatPageSize());
				}
			}else{//카테고리 설정값이 10 미만이면 설정값으로만 적용되게 - 사용자를 탬플릿 페이지 사이즈를 변경할 수 없다.
				advertisingSearch.setPageSize(currentCategory.getCatPageSize());
			}
			
			//기본 정렬 세팅 - 파라메터에서 넘어온 정렬값이 있으면 적용되지 않는다.
			advertisingSearch.setDefaultSort(currentCategory.getCatSortOrder(), currentCategory.getCatSortDirection());
		}
		
		
		//데이터중 연도 목록 추출
		List<String> yearList = advertisingService.getYearList(advertisingSearch);
		model.addAttribute("yearList", yearList );
		
		//최최 연도 파라메터 값이 없을경우 최근년도 셋
		if(advertisingSearch.getAdtDefaultYear() && StringUtils.isBlank(advertisingSearch.getAdtManufactureYear()) ){
			if(yearList != null && yearList.size() > 0){
				advertisingSearch.setAdtManufactureYear(yearList.get(0));
			}
		}
		
		
		int tempPageSize = advertisingSearch.getPageSize();
		
		//상단별도 분리추출
		if(currentCategory.isCatSupportFixing() ){
			if(currentCategory.getCatFixingNum() > 0 ){
				
				//최근 n개 별도로 출력하고 페이징하기위해 셋
				int esclusion = currentCategory.getCatFixingNum();
				
				int tempCp = advertisingSearch.getCp();
				
				//고정목록 - 최근 자료 n개
				advertisingSearch.setCp(1);
				advertisingSearch.setPageSize(esclusion);
				List<Advertising> fixingList = advertisingService.getAdvertisingList(advertisingSearch);
				model.addAttribute("fixingList", fixingList);
				
				advertisingSearch.setExclusion(esclusion);
				advertisingSearch.setCp(tempCp);
				advertisingSearch.setPageSize(tempPageSize);
			}
		}
		
		//추출체크된 데이터 목록
		//코바코만 예외적으로 분리하여 사용
		if(/*"ASA".equals(currentSite.getSitePrefix()) &&*/ currentCategory.isCatSupportSelect1() ){
			String tempMeta = advertisingSearch.getMetaCode1();
			
			advertisingSearch.setMetaCode1("");
			advertisingSearch.setArcSelected1(true);
			advertisingSearch.setPageSize(2);
			List<Advertising> selectedList = advertisingService.getAdvertisingList(advertisingSearch);
			model.addAttribute("selectedList", selectedList);
			
			advertisingSearch.setArcSelected1(false);
			advertisingSearch.setMetaCode1(tempMeta);
		}
		
		advertisingSearch.setPageSize(tempPageSize);
		
		List<Advertising> list = advertisingService.getAdvertisingList(advertisingSearch);
		int total = advertisingService.getAdvertisingListTotal(advertisingSearch) - advertisingSearch.getExclusion();
		Paging paging = new Paging(list, total, advertisingSearch);
		model.addAttribute("paging", paging);
		
/*		//메타코드를 카테고리로 사용시 검색대상을 다중으로 or검색을 할경우를 대비하여 사용
		//카테고리1 배열에 담는다
		if(StringUtils.isNotBlank(advertisingSearch.getMetaCode1()) ){
			String[] selectMetaCode1Array = advertisingSearch.getMetaCode1().split(",");
			model.addAttribute("selectMetaCode1Array", selectMetaCode1Array);
		}
		//카테고리2 배열에 담는다
		if(StringUtils.isNotBlank(advertisingSearch.getMetaCode2()) ){
			String[] selectMetaCode2Array = advertisingSearch.getMetaCode2().split(",");
			model.addAttribute("selectMetaCode2Array", selectMetaCode2Array);
		}
		//카테고리3 배열에 담는다
		if(StringUtils.isNotBlank(advertisingSearch.getMetaCode3()) ){
			String[] selectMetaCode3Array = advertisingSearch.getMetaCode3().split(",");
			model.addAttribute("selectMetaCode3Array", selectMetaCode3Array);
		}
		*/
		
		//메타코드2이 선택 됐으면 해당 코드목록을 메타코드목록 3로 가져간다.
		if(StringUtils.isNotBlank(advertisingSearch.getMetaCode2()) ){
			model.addAttribute("arcMetaCode3List", codeService.getCodeList(advertisingSearch.getMetaCode2()) );
		}
		
		//메타코드2에 해당하는 모든 상세코드목록
		/*if(currentCategory != null && StringUtils.isNotBlank(currentCategory.getCatMetaCode2()) ) {
			List<Code> arcMetaCode3ListAll = new ArrayList<Code>();
			@SuppressWarnings("unchecked")
			List<Code> tempMeta2List = (List<Code>)model.asMap().get("arcMetaCode2List");
			for (Code code : tempMeta2List) {
				List<Code> codeList = codeService.getCodeList(code.getCodeId());
				arcMetaCode3ListAll.addAll(codeList);
			}
			model.addAttribute("arcMetaCode3ListAll", arcMetaCode3ListAll);
		}*/
		
		
		
		//템플릿 파일 적용
		if(currentCategory != null && StringUtils.isNotBlank(currentCategory.getCatUserListTemplate()) ){
			return archiveService.getArchiveTemplateName(currentCategory.getCatCustomType(), currentCategory.getCatUserListTemplate(), "user");
		}
		return AsaproUtils.getThemePath(request) + "archive/advertising/list";
	}

	/**
	 * 광고자료 조회뷰를 반환한다.
	 * @param model
	 * @param currentSite
	 * @param advertisingForm
	 * @return 광고자료뷰 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	@RequestMapping(value=Constant.APP_PATH + Constant.SITE_ID_PATH + "/archive/advertising/{arcCategory}/{arcId}", method=RequestMethod.GET)
	public String advertisingViewGet(Model model, @CurrentSite Site currentSite, @ModelAttribute("advertisingForm") Advertising advertisingForm) throws IllegalAccessException, InvocationTargetException {
		//광고자료 조회
		advertisingForm.setSitePrefix(currentSite.getSitePrefix());
		Advertising advertisingModel = advertisingService.getAdvertising(advertisingForm);
		advertisingModel.setSitePrefix(currentSite.getSitePrefix());
		model.addAttribute("advertisingForm", advertisingModel);

		//카테고리 조회
		ArchiveCategory currentCategory = null;
		if( StringUtils.isNotBlank(advertisingModel.getArcCategory()) ){
			currentCategory = new ArchiveCategory();
			currentCategory.setSitePrefix(currentSite.getSitePrefix());
			currentCategory.setCatId(advertisingModel.getArcCategory());
			currentCategory = archiveService.getArchiveCategory(currentCategory);
			
			//메타코드 로드
			this.loadCode(model, currentCategory);
			
		}
		model.addAttribute("archiveCategory", currentCategory);
		
		
		//공공누리코드목록
		//model.addAttribute("nuriCodeList", codeService.getCodeList("NURI_TYPE"));
		
		//메인페이지 공익광고 별도 처리하기위해 - 다른예외 처리 방법 애매함
		if("ASA".equals(currentSite.getSitePrefix()) ){
			//메타코드2이 선택 됐으면 해당 코드목록을 메타코드목록 3로 가져간다.
			if(StringUtils.isNotBlank(advertisingModel.getArcMetaCode2()) ){
				model.addAttribute("arcMetaCode3List", codeService.getCodeList(advertisingModel.getArcMetaCode2()) );
			}
		}
				
		
		//목록 돌아가기 검색VO
		AdvertisingSearch backListSearch = new AdvertisingSearch();
		BeanUtils.populate(backListSearch, request.getParameterMap());
		backListSearch.fixBrokenSvByDefaultCharsets();
		model.addAttribute("backListSearch", backListSearch);
		
		//템플릿 파일 적용
		if(currentCategory != null && StringUtils.isNotBlank(currentCategory.getCatUserViewTemplate()) ){
			return archiveService.getArchiveTemplateName(currentCategory.getCatCustomType(), currentCategory.getCatUserViewTemplate(), "user");
		}
		return AsaproUtils.getThemePath(request) + "archive/advertising/view";
	}
	
}
