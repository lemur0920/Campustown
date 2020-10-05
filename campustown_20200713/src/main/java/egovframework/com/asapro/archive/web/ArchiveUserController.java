/**
 * 
 */
package egovframework.com.asapro.archive.web;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.FlashMap;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.ibm.icu.text.SimpleDateFormat;

import egovframework.com.asapro.archive.service.Archive;
import egovframework.com.asapro.archive.service.ArchiveCategory;
import egovframework.com.asapro.archive.service.ArchiveSearch;
import egovframework.com.asapro.archive.service.ArchiveService;
import egovframework.com.asapro.code.service.Code;
import egovframework.com.asapro.code.service.CodeCategorySearch;
import egovframework.com.asapro.code.service.CodeService;
import egovframework.com.asapro.content_statis.service.ContentStatisDay;
import egovframework.com.asapro.content_statis.service.ContentStatisService;
import egovframework.com.asapro.fileinfo.service.FileInfo;
import egovframework.com.asapro.fileinfo.service.FileInfoUploadResult;
import egovframework.com.asapro.site.service.Site;
import egovframework.com.asapro.support.Constant;
import egovframework.com.asapro.support.SEO;
import egovframework.com.asapro.support.ServerMessage;
import egovframework.com.asapro.support.annotation.CurrentSite;
import egovframework.com.asapro.support.exception.AsaproException;
import egovframework.com.asapro.support.exception.AsaproNotFoundException;
import egovframework.com.asapro.support.pagination.Paging;
import egovframework.com.asapro.support.util.AsaproCalendarUtils;
import egovframework.com.asapro.support.util.AsaproUtils;
import egovframework.com.asapro.support.util.EtcUtil;

/**
 * 아카이브 사용자 컨트롤러
 * @author yckim
 * @since 2018. 12. 19.
 *
 */
@Controller
public class ArchiveUserController {
	
	@Autowired
	private ArchiveService archiveService;
	
	@Autowired
	private ArchiveValidator archiveValidator;
	
	@Autowired
	private HttpServletRequest request;
	
	@Autowired
	private CodeService codeService;
	
	@Autowired
	private ContentStatisService contentStatisService;
	
	//=============================================================================================================================
	//==========================================  아카이브    ================================================================
	//=============================================================================================================================
	
	/**
	 * 메타코드 로드 및 기타 공통코드 로드
	 * @param model
	 * @param archiveCategory
	 */
	private void loadCode(Model model, ArchiveCategory archiveCategory){
		if( StringUtils.isNotBlank(archiveCategory.getCatMetaCode1()) ){
			model.addAttribute("arcMetaCode1Info", codeService.getCodeCategory(archiveCategory.getCatMetaCode1()) );
			model.addAttribute("arcMetaCode1List", codeService.getCodeList(archiveCategory.getCatMetaCode1()) );
		}
		if( StringUtils.isNotBlank(archiveCategory.getCatMetaCode2()) ){
			model.addAttribute("arcMetaCode2Info", codeService.getCodeCategory(archiveCategory.getCatMetaCode2()) );
			model.addAttribute("arcMetaCode2List", codeService.getCodeList(archiveCategory.getCatMetaCode2()) );
		}
		if( StringUtils.isNotBlank(archiveCategory.getCatMetaCode3()) ){
			model.addAttribute("arcMetaCode3Info", codeService.getCodeCategory(archiveCategory.getCatMetaCode3()) );
			model.addAttribute("arcMetaCode3List", codeService.getCodeList(archiveCategory.getCatMetaCode3()) );
		}
		
		
	}
	
	
	
	/**
	 * 아카이브 목록을 조회한다.
	 * @param model
	 * @param currentSite
	 * @param archiveSearch
	 * @return 아카이브 목록
	 */
	@RequestMapping(value=Constant.APP_PATH + Constant.SITE_ID_PATH + "/archive/{arcCategory}/list", method=RequestMethod.GET)
	public String archiveListGet(Model model, @CurrentSite Site currentSite, @ModelAttribute("archiveSearch") ArchiveSearch archiveSearch){
		//카테고리 조회
		ArchiveCategory currentCategory = null;
		if( StringUtils.isNotBlank(archiveSearch.getArcCategory()) ){
			ArchiveCategory categorySearch = new ArchiveCategory();
			categorySearch.setSitePrefix(currentSite.getSitePrefix());
			categorySearch.setCatId(archiveSearch.getArcCategory());
			currentCategory = archiveService.getArchiveCategory(categorySearch);
		}
		model.addAttribute("archiveCategory", currentCategory);
		
		//아카이브 목록 조회
		archiveSearch.setSitePrefix(currentSite.getSitePrefix());
		archiveSearch.fixBrokenSvByDefaultCharsets();
		archiveSearch.setPaging(true);
		archiveSearch.setArcUse(true);
//		archiveSearch.setSortOrder("ARC_YEAR");
//		archiveSearch.setSortDirection("DESC");
		
		if(currentCategory != null){
			
			//메타코드 로드
			this.loadCode(model, currentCategory);
			
			//페이지당 개수 - 페이지 사이즈가 10이 아닐 경우 카테고리 템플릿에서 페이지 사이즈 설정하는 기능 있으면 거기도 같이 맞춰줘야 함
			//if( postSearch.getPageSize().intValue() < archiveCategory.getCatPageSize() ){
			//카테고리 설정사이즈 10 이하도 적용되게 변경
			if(currentCategory.getCatPageSize() >= 10){//카테고리설정값이 10 이상이면
				if( archiveSearch.getPageSize().intValue() < currentCategory.getCatPageSize() ){
					archiveSearch.setPageSize(currentCategory.getCatPageSize());
				}
			}else{//카테고리 설정값이 10 미만이면 설정값으로만 적용되게 - 사용자를 탬플릿 페이지 사이즈를 변경할 수 없다.
				archiveSearch.setPageSize(currentCategory.getCatPageSize());
			}
			
			//기본 정렬 세팅 - 파라메터에서 넘어온 정렬값이 있으면 적용되지 않는다.
			archiveSearch.setDefaultSort(currentCategory.getCatSortOrder(), currentCategory.getCatSortDirection());
		}
		
		List<Archive> list = archiveService.getArchiveList(archiveSearch);
		int total = archiveService.getArchiveListTotal(archiveSearch);
		Paging paging = new Paging(list, total, archiveSearch);
		model.addAttribute("paging", paging);
		
		//코드카테고리
//		CodeCategorySearch codeCategorySearch = new CodeCategorySearch();
//		codeCategorySearch.setPaging(false);
//		model.addAttribute("codeCatList", codeService.getCodeCategoryList(codeCategorySearch));
		
		//공공누리코드목록
		//model.addAttribute("nuriCodeList", codeService.getCodeList("NURI_TYPE"));
		
		//차후 템플릿 설정 기능 생기면 해당 템플릿으로 뷰를 적용
		
		return AsaproUtils.getThemePath(request) + "archive/list";
	}
	
	
	/**
	 * 아카이브 추출선택된 목록을 조회한다.
	 * @param model
	 * @param currentSite
	 * @param archiveSearch
	 * @return 아카이브 추출 목록
	 */
	@RequestMapping(value=Constant.APP_PATH + Constant.SITE_ID_PATH + "/archive/{arcCategory}/selected", method=RequestMethod.GET)
	public String archiveSelectedListGet(Model model, @CurrentSite Site currentSite, @ModelAttribute("archiveSearch") ArchiveSearch archiveSearch){
		//카테고리 조회
		ArchiveCategory currentCategory = null;
		if( StringUtils.isNotBlank(archiveSearch.getArcCategory()) ){
			ArchiveCategory categorySearch = new ArchiveCategory();
			categorySearch.setSitePrefix(currentSite.getSitePrefix());
			categorySearch.setCatId(archiveSearch.getArcCategory());
			currentCategory = archiveService.getArchiveCategory(categorySearch);
		}
		model.addAttribute("archiveCategory", currentCategory);
		
		//아카이브 목록 조회
		archiveSearch.setSitePrefix(currentSite.getSitePrefix());
		archiveSearch.fixBrokenSvByDefaultCharsets();
		archiveSearch.setPaging(false);
		archiveSearch.setArcUse(true);
		archiveSearch.setSortOrder("ARC_YEAR");
		archiveSearch.setSortDirection("ASC");
		//광고자료관 추출여부
		archiveSearch.setArcSelected1(true);	
		
		if(currentCategory != null){
			
			//메타코드 로드
			this.loadCode(model, currentCategory);
			
			//기본 정렬 세팅 - 파라메터에서 넘어온 정렬값이 있으면 적용되지 않는다.
			archiveSearch.setDefaultSort(currentCategory.getCatSortOrder(), currentCategory.getCatSortDirection());
		}
		
		List<Archive> list = archiveService.getArchiveList(archiveSearch);
		int total = archiveService.getArchiveListTotal(archiveSearch);
		Paging paging = new Paging(list, total, archiveSearch);
		model.addAttribute("paging", paging);
		
		//코드카테고리
//		CodeCategorySearch codeCategorySearch = new CodeCategorySearch();
//		codeCategorySearch.setPaging(false);
//		model.addAttribute("codeCatList", codeService.getCodeCategoryList(codeCategorySearch));
		
		//공공누리코드목록
		//model.addAttribute("nuriCodeList", codeService.getCodeList("NURI_TYPE"));
		
		//차후 템플릿 설정 기능 생기면 해당 템플릿으로 뷰를 적용
		
		return AsaproUtils.getThemePath(request) + "archive/selectedList";
	}
	
	
	/**
	 * 아카이브를 추천한다.
	 * @param arcId
	 * @return 추천 결과 json
	 */
	@RequestMapping(value=Constant.APP_PATH + Constant.SITE_ID_PATH + "/archive/{catId}" + Constant.API_PATH  + "/recommend" , method=RequestMethod.POST)
	@ResponseBody
	public ServerMessage updateArchiveRecommendPost(HttpServletResponse response, @CurrentSite Site currentSite, @PathVariable String catId, @RequestParam(value="arcId", required=true) Integer arcId){
		Cookie recommendCookie = AsaproUtils.getCookie(request.getCookies(), "arc_reco_" + currentSite.getSiteId() + "_" + arcId);
		if( recommendCookie == null ){
			Archive recommendArchive = new Archive();
			recommendArchive.setSitePrefix(currentSite.getSitePrefix());
			recommendArchive.setArcId(arcId);
			if( archiveService.updateArchiveRecommend(recommendArchive) > 0 ){
				
				//========= 추천통계자료 등록 ===================
				contentStatisDay(recommendArchive, currentSite, "recommend");
				//========= 추천통계자료 등록 ===================
				
				//쿠키생성
				recommendCookie = new Cookie("arc_reco_" + currentSite.getSiteId() + "_" + arcId, System.currentTimeMillis() + "");
				//recommendCookie.setDomain(configService.getConfig("global").getOption("cookie_domain"));
				recommendCookie.setDomain(request.getServerName());
				recommendCookie.setPath("/");
				recommendCookie.setHttpOnly(true);
				//int t = 60 * 60 * 24 * 365;//1년
				int t = 60 * 60 * 24;//1일
				if(t > 86400) {
					t = 86400;
				}
				recommendCookie.setMaxAge(t);
				response.addCookie(recommendCookie);
				
				return new ServerMessage(true, "추천되었습니다.");
			} else {
				return new ServerMessage(false, "서버오류로 추천하지 못하였습니다.");
			}
		} else {
			return new ServerMessage(false, "이미 추천하셨습니다.");
		}
	}
	
	//======================================================================================================================================
	
	/**
	 * 콘텐츠의 일별 통계데이터를 등록 및 업데이트 한다.
	 * @param archive
	 * @param currentSite
	 * @return 처리결과
	 */
	public int contentStatisDay(Archive archive, Site currentSite, String moduleSub){
		int result = 0;
		
		Calendar cal = Calendar.getInstance();
		ContentStatisDay contentStatisDay = new ContentStatisDay();
		
		contentStatisDay.setSitePrefix(currentSite.getSitePrefix());
		contentStatisDay.setCsModiulCode("archivetype");
		contentStatisDay.setCsModiulSubCode(moduleSub);
		contentStatisDay.setCsCateCode(archive.getArcCategory());
		contentStatisDay.setCsContentId(archive.getArcId().toString());
		contentStatisDay.setCsYear(cal.get(Calendar.YEAR));
		contentStatisDay.setCsMonth(cal.get(Calendar.MONTH) + 1);
		contentStatisDay.setCsDay(cal.get(Calendar.DAY_OF_MONTH));
		
		result = contentStatisService.mergeContentStatisDay(contentStatisDay);
		return result;
	}
	
}
