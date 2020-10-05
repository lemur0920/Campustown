/**
 * 
 */
package egovframework.com.asapro.archive.customtype.space.web;

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

import egovframework.com.asapro.archive.customtype.space.service.Space;
import egovframework.com.asapro.archive.customtype.space.service.SpaceSearch;
import egovframework.com.asapro.archive.customtype.space.service.SpaceService;
import egovframework.com.asapro.archive.service.ArchiveCategory;
import egovframework.com.asapro.archive.service.ArchiveService;
import egovframework.com.asapro.code.service.Code;
import egovframework.com.asapro.code.service.CodeCategory;
import egovframework.com.asapro.code.service.CodeService;
import egovframework.com.asapro.organization.service.Department;
import egovframework.com.asapro.organization.service.DepartmentSearch;
import egovframework.com.asapro.organization.service.OrganizationService;
import egovframework.com.asapro.site.service.Site;
import egovframework.com.asapro.support.Constant;
import egovframework.com.asapro.support.annotation.CurrentSite;
import egovframework.com.asapro.support.pagination.Paging;
import egovframework.com.asapro.support.util.AsaproUtils;

/**
 * 공간정보 사용자 컨트롤러
 * 커스텀 아카이브
 * @author yckim
 * @since 2019. 2. 28.
 *
 */
@Controller
public class SpaceUserController {
	
	@Autowired
	private ArchiveService archiveService;
	
	@Autowired
	private OrganizationService organizationService;
	
	@Autowired
	private SpaceService spaceService;
	
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
			arcMetaCode2List = codeService.getCodeList(archiveCategory.getCatMetaCode2(), "CODE_NAME", "ASC");
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
	
	/**
	 * 부서목록 로드
	 * @param model
	 * @param orgId
	 */
	private void loadDepartment(Model model, String orgId){
		//부서목록
		DepartmentSearch departmentSearch = new DepartmentSearch();
		departmentSearch.setPaging(false);
		departmentSearch.setDepUse(true);
		departmentSearch.setOrgId(orgId);
		List<Department> departmentList = organizationService.getDepartmentList(departmentSearch);
		//System.out.println("departmentList.size(): " + departmentList.size());
		//System.out.println("departmentList.size(): " + departmentList.get(0).getDepName());
		model.addAttribute("departmentList", departmentList);
	}
	
	//=============================================================================================================================
	//==========================================  공간정보    ================================================================
	//=============================================================================================================================
	
	
	/**
	 * 공간정보 목록을 조회한다.
	 * @param model
	 * @param currentSite
	 * @param spaceSearch
	 * @return 공간정보 목록
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value=Constant.APP_PATH + Constant.SITE_ID_PATH + "/archive/space/{arcCategory}", method=RequestMethod.GET)
	public String spaceListGet(Model model, @CurrentSite Site currentSite, @ModelAttribute("spaceSearch") SpaceSearch spaceSearch){
		//카테고리 조회
		ArchiveCategory currentCategory = null;
		if( StringUtils.isNotBlank(spaceSearch.getArcCategory()) ){
			currentCategory = new ArchiveCategory();
			currentCategory.setSitePrefix(currentSite.getSitePrefix());
			currentCategory.setCatId(spaceSearch.getArcCategory());
			currentCategory = archiveService.getArchiveCategory(currentCategory);
		}
		model.addAttribute("archiveCategory", currentCategory);
		
		//공간정보 목록 조회
		spaceSearch.setSitePrefix(currentSite.getSitePrefix());
		spaceSearch.fixBrokenSvByDefaultCharsets();
		spaceSearch.setPaging(true);
		spaceSearch.setArcUse(true);
		
		if(currentCategory != null){
			
			//메타코드 로드
			this.loadCode(model, currentCategory);
			
			//페이지당 개수 - 페이지 사이즈가 10이 아닐 경우 카테고리 템플릿에서 페이지 사이즈 설정하는 기능 있으면 거기도 같이 맞춰줘야 함
			//if( postSearch.getPageSize().intValue() < archiveCategory.getCatPageSize() ){
			//카테고리 설정사이즈 10 이하도 적용되게 변경
			if(currentCategory.getCatPageSize() >= 10){//카테고리설정값이 10 이상이면
				if( spaceSearch.getPageSize().intValue() < currentCategory.getCatPageSize() ){
					spaceSearch.setPageSize(currentCategory.getCatPageSize());
				}
			}else{//카테고리 설정값이 10 미만이면 설정값으로만 적용되게 - 사용자를 탬플릿 페이지 사이즈를 변경할 수 없다.
				spaceSearch.setPageSize(currentCategory.getCatPageSize());
			}
			
			//기본 정렬 세팅 - 파라메터에서 넘어온 정렬값이 있으면 적용되지 않는다.
			spaceSearch.setDefaultSort(currentCategory.getCatSortOrder(), currentCategory.getCatSortDirection());
		}
		
		this.loadDepartment(model, "university"); // 대학 부서목록 로드 (departmentList)
		
		List<Space> list = spaceService.getSpaceList(spaceSearch);
		int total = spaceService.getSpaceListTotal(spaceSearch);
		Paging paging = new Paging(list, total, spaceSearch);
		model.addAttribute("paging", paging);
		
		//메타코드1 (지역(시구)) 를 검색했을경우 - 값이 있을경우 메타코드2 지역(시군구)의 목록을 담아간다.
		if(StringUtils.isNotBlank(spaceSearch.getMetaCode1()) ) {
			model.addAttribute("arcMetaCode2List", codeService.getCodeList(spaceSearch.getMetaCode1()));
		}
		
		//목록에 각 데이터별 지역(시군구)를 매핑시켜주기 위해 모든 시구 별 모든 시군구를 한 리스트에 담아간다.
		List<Code> arcMetaCode2ListAll = new ArrayList<Code>();
		if(currentCategory != null && StringUtils.isNotBlank(currentCategory.getCatMetaCode1()) ) {
			List<Code> tempMeta1List = (List<Code>)model.asMap().get("arcMetaCode1List");
			for (Code code : tempMeta1List) {
				List<Code> codeList = codeService.getCodeList(code.getCodeId());
				arcMetaCode2ListAll.addAll(codeList);
			}
			//codeService.getCodeList(spaceModel.getArcMetaCode1())
		}
		model.addAttribute("arcMetaCode2ListAll", arcMetaCode2ListAll);
		
//		if(StringUtils.isNotBlank(spaceModel.getArcMetaCode1()) ){
//			model.addAttribute("arcMetaCode2List", codeService.getCodeList(spaceModel.getArcMetaCode1()) );
//		}
		
		return AsaproUtils.getThemePath(request) + "archive/space/list";
	}

	/**
	 * 공간정보 조회뷰를 반환한다.
	 * @param model
	 * @param currentSite
	 * @param spaceForm
	 * @return 공간정보뷰 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	@RequestMapping(value=Constant.APP_PATH + Constant.SITE_ID_PATH + "/archive/space/{arcCategory}/{arcId}", method=RequestMethod.GET)
	public String spaceViewGet(Model model, @CurrentSite Site currentSite, @ModelAttribute("spaceForm") Space spaceForm) throws IllegalAccessException, InvocationTargetException {
		//공간정보 조회
		spaceForm.setSitePrefix(currentSite.getSitePrefix());
		Space spaceModel = spaceService.getSpace(spaceForm);
		spaceModel.setSitePrefix(currentSite.getSitePrefix());
		model.addAttribute("spaceForm", spaceModel);

		//카테고리 조회
		ArchiveCategory currentCategory = null;
		if( StringUtils.isNotBlank(spaceModel.getArcCategory()) ){
			currentCategory = new ArchiveCategory();
			currentCategory.setSitePrefix(currentSite.getSitePrefix());
			currentCategory.setCatId(spaceModel.getArcCategory());
			currentCategory = archiveService.getArchiveCategory(currentCategory);
			
			//메타코드 로드
			this.loadCode(model, currentCategory);
			
		}
		model.addAttribute("archiveCategory", currentCategory);
		
		
		//공공누리코드목록
		//model.addAttribute("nuriCodeList", codeService.getCodeList("NURI_TYPE"));
		
		//메타코드1이 선택 됐으면 해당 코드목록을 메타코드목록 2로 가져간다.
		if(StringUtils.isNotBlank(spaceModel.getArcMetaCode1()) ){
			model.addAttribute("arcMetaCode2List", codeService.getCodeList(spaceModel.getArcMetaCode1()) );
		}
		
		//목록 돌아가기 검색VO
		SpaceSearch backListSearch = new SpaceSearch();
		BeanUtils.populate(backListSearch, request.getParameterMap());
		backListSearch.fixBrokenSvByDefaultCharsets();
		model.addAttribute("backListSearch", backListSearch);
		
		return AsaproUtils.getThemePath(request) + "archive/space/view";
	}
	

	//===================================  서울시 일자리 카페  ===============================================
	/**
	 * 공간정보 목록을 조회한다.
	 * @param model
	 * @param currentSite
	 * @param spaceSearch
	 * @return 공간정보 목록
	 */

	@RequestMapping(value=Constant.APP_PATH + Constant.SITE_ID_PATH + "/archive/space/cafe/{arcCategory}", method=RequestMethod.GET)
	public String spaceCafeListGet(Model model, @CurrentSite Site currentSite, @ModelAttribute("spaceSearch") SpaceSearch spaceSearch){
		//카테고리 조회
		ArchiveCategory currentCategory = null;
		if( StringUtils.isNotBlank(spaceSearch.getArcCategory()) ){
			currentCategory = new ArchiveCategory();
			currentCategory.setSitePrefix(currentSite.getSitePrefix());
			currentCategory.setCatId(spaceSearch.getArcCategory());
			currentCategory = archiveService.getArchiveCategory(currentCategory);
		}
		model.addAttribute("archiveCategory", currentCategory);
		
		//공간정보 목록 조회
		spaceSearch.setSitePrefix(currentSite.getSitePrefix());
		spaceSearch.fixBrokenSvByDefaultCharsets();
		spaceSearch.setPaging(true);
		spaceSearch.setArcUse(true);
		//일자리카페 추출여부
		spaceSearch.setArcSelected1(true);
		//메타코드1 은 '서울'로 고정 - 서울시 카페만 검색하는 메뉴
		spaceSearch.setMetaCode1("seoul");
		
		if(currentCategory != null){
			
			//메타코드 로드
			this.loadCode(model, currentCategory);
			
			//페이지당 개수 - 페이지 사이즈가 10이 아닐 경우 카테고리 템플릿에서 페이지 사이즈 설정하는 기능 있으면 거기도 같이 맞춰줘야 함
			//if( postSearch.getPageSize().intValue() < archiveCategory.getCatPageSize() ){
			//카테고리 설정사이즈 10 이하도 적용되게 변경
			if(currentCategory.getCatPageSize() >= 10){//카테고리설정값이 10 이상이면
				if( spaceSearch.getPageSize().intValue() < currentCategory.getCatPageSize() ){
					spaceSearch.setPageSize(currentCategory.getCatPageSize());
				}
			}else{//카테고리 설정값이 10 미만이면 설정값으로만 적용되게 - 사용자를 탬플릿 페이지 사이즈를 변경할 수 없다.
				spaceSearch.setPageSize(currentCategory.getCatPageSize());
			}
			
			//기본 정렬 세팅 - 파라메터에서 넘어온 정렬값이 있으면 적용되지 않는다.
			spaceSearch.setDefaultSort(currentCategory.getCatSortOrder(), currentCategory.getCatSortDirection());
		}
		
		CodeCategory arcMetaCode2Info = null;
		List<Code> arcMetaCode2List = null;
		arcMetaCode2Info = codeService.getCodeCategory("seoul");
		arcMetaCode2List = codeService.getCodeList("seoul");
		model.addAttribute("arcMetaCode2Info", arcMetaCode2Info );
		model.addAttribute("arcMetaCode2List", arcMetaCode2List );
		
		List<Space> list = spaceService.getSpaceList(spaceSearch);
		int total = spaceService.getSpaceListTotal(spaceSearch);
		Paging paging = new Paging(list, total, spaceSearch);
		model.addAttribute("paging", paging);
		
		//템플릿 파일 적용
		if(currentCategory != null && StringUtils.isNotBlank(currentCategory.getCatUserListTemplate()) ){
			return archiveService.getArchiveTemplateName(currentCategory.getCatCustomType(), currentCategory.getCatUserListTemplate(), "user");
		}
		return AsaproUtils.getThemePath(request) + "archive/space/cafeList";
	}
	
	/**
	 * 공간정보 조회뷰를 반환한다.
	 * @param model
	 * @param currentSite
	 * @param spaceForm
	 * @return 공간정보뷰 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	@RequestMapping(value=Constant.APP_PATH + Constant.SITE_ID_PATH + "/archive/space/cafe/{arcCategory}/{arcId}", method=RequestMethod.GET)
	public String spaceCafeViewGet(Model model, @CurrentSite Site currentSite, @ModelAttribute("spaceForm") Space spaceForm) throws IllegalAccessException, InvocationTargetException {
		//공간정보 조회
		spaceForm.setSitePrefix(currentSite.getSitePrefix());
		Space spaceModel = spaceService.getSpace(spaceForm);
		spaceModel.setSitePrefix(currentSite.getSitePrefix());
		model.addAttribute("spaceForm", spaceModel);

		//카테고리 조회
		ArchiveCategory currentCategory = null;
		if( StringUtils.isNotBlank(spaceModel.getArcCategory()) ){
			currentCategory = new ArchiveCategory();
			currentCategory.setSitePrefix(currentSite.getSitePrefix());
			currentCategory.setCatId(spaceModel.getArcCategory());
			currentCategory = archiveService.getArchiveCategory(currentCategory);
			
			//메타코드 로드
			this.loadCode(model, currentCategory);
			
		}
		model.addAttribute("archiveCategory", currentCategory);
		
		
		//공공누리코드목록
		//model.addAttribute("nuriCodeList", codeService.getCodeList("NURI_TYPE"));
		
		//메타코드1이 선택 됐으면 해당 코드목록을 메타코드목록 2로 가져간다.
		if(StringUtils.isNotBlank(spaceModel.getArcMetaCode1()) ){
			model.addAttribute("arcMetaCode2List", codeService.getCodeList(spaceModel.getArcMetaCode1()) );
		}
		
		//목록 돌아가기 검색VO
		SpaceSearch backListSearch = new SpaceSearch();
		BeanUtils.populate(backListSearch, request.getParameterMap());
		backListSearch.fixBrokenSvByDefaultCharsets();
		model.addAttribute("backListSearch", backListSearch);
		
		//템플릿 파일 적용
		if(currentCategory != null && StringUtils.isNotBlank(currentCategory.getCatUserViewTemplate()) ){
			return archiveService.getArchiveTemplateName(currentCategory.getCatCustomType(), currentCategory.getCatUserViewTemplate(), "user");
		}
		return AsaproUtils.getThemePath(request) + "archive/space/cafeView";
	}
}
