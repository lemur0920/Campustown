package egovframework.com.campustown.univHpMngr.web;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.support.RequestContextUtils;

import egovframework.com.asapro.archive.customtype.space.service.Space;
import egovframework.com.asapro.archive.customtype.space.service.SpaceSearch;
import egovframework.com.asapro.archive.customtype.space.service.SpaceService;
import egovframework.com.asapro.archive.service.ArchiveCategory;
import egovframework.com.asapro.archive.service.ArchiveService;
import egovframework.com.asapro.board.service.BoardArticle;
import egovframework.com.asapro.board.service.BoardArticleSearch;
import egovframework.com.asapro.board.service.BoardConfig;
import egovframework.com.asapro.board.service.BoardService;
import egovframework.com.asapro.code.service.Code;
import egovframework.com.asapro.code.service.CodeCategory;
import egovframework.com.asapro.code.service.CodeService;
import egovframework.com.asapro.fileinfo.service.FileInfo;
import egovframework.com.asapro.fileinfo.service.FileInfoService;
import egovframework.com.asapro.member.user.service.UserMember;
import egovframework.com.asapro.organization.service.Department;
import egovframework.com.asapro.organization.service.DepartmentSearch;
import egovframework.com.asapro.organization.service.OrganizationService;
import egovframework.com.asapro.rental.service.Rental;
import egovframework.com.asapro.rental.service.RentalSearch;
import egovframework.com.asapro.rental.service.RentalService;
import egovframework.com.asapro.site.service.Site;
import egovframework.com.asapro.support.Constant;
import egovframework.com.asapro.support.SEO;
import egovframework.com.asapro.support.annotation.CurrentSite;
import egovframework.com.asapro.support.annotation.CurrentUser;
import egovframework.com.asapro.support.exception.AsaproNotFoundException;
import egovframework.com.asapro.support.pagination.Paging;
import egovframework.com.asapro.support.util.AsaproUtils;
import egovframework.com.campustown.startHpMngr.service.StartHpMngr;
import egovframework.com.campustown.startHpMngr.service.StartHpMngrSearch;
import egovframework.com.campustown.startHpMngr.service.StartHpMngrService;
import egovframework.com.campustown.univHpMngr.service.UnivHpMngr;
import egovframework.com.campustown.univHpMngr.service.UnivHpMngrSearch;
import egovframework.com.campustown.univHpMngr.service.UnivHpMngrService;

/**
 * 관리자(대학) 홈페이지 관리 사용자 컨트롤러
 * @author jaseo
 * @since 2020. 02. 11.
 */
@Controller
public class UnivHpMngrUserController{
	
	@Autowired
	private CodeService codeService;
	
	@Autowired
	private HttpServletRequest request;
	
	@Autowired
	private UnivHpMngrService univHpMngrService;
	
	@Autowired
	private SpaceService spaceService;
	
	@Autowired
	private RentalService rentalService;
	
	@Autowired
	private ArchiveService archiveService;
	
	@Autowired
	private OrganizationService organizationService;
	
	@Autowired
	private FileInfoService fileInfoService;
	
	@Autowired
	private StartHpMngrService startHpMngrService; 
	
	@Autowired
	private BoardService boardService;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(UnivHpMngrUserController.class);
	
	
	/**
	 * 코드목록 로드
	 * @param model
	 */
	private void loadCodes(Model model) {
		
		model.addAttribute("sigunguCodeList", codeService.getCodeList("SIGUNGU_CODE", "CODE_NAME", "ASC")); // 지역 정보(서울시 구)
		model.addAttribute("indutyCodeList",  codeService.getCodeList("INDUTY_CODE"));			  // 업종 코드
		model.addAttribute("bsnsRealmCodeList",  codeService.getCodeList("BSNS_REALM_CODE"));	  // 사업분야 코드 	
		model.addAttribute("intrstRealmCodeList",  codeService.getCodeList("INTRST_REALM_CODE")); // 관심분야 코드
		model.addAttribute("tel1CodeList", codeService.getCodeList("tel_area_code"));			  // 일반전화 앞자리	
		model.addAttribute("mobile1CodeList", codeService.getCodeList("mobile1_code"));			  // 휴대폰번호 앞자리
		model.addAttribute("emailDomainCodeList", codeService.getCodeList("EMAIL_DOMAIN"));		  // 이메일 도메인
		
		// 대학 등록 수
		UnivHpMngrSearch univHpMngrSearch = new UnivHpMngrSearch();
		univHpMngrSearch.setExpsrYn("Y");
		model.addAttribute("univListTot", univHpMngrService.getUnivHpMngrListTotal(univHpMngrSearch));
		
		// 창업팀 등록 수
		StartHpMngrSearch startHpMngrSearch = new StartHpMngrSearch();
		startHpMngrSearch.setExpsrYn("Y");
		model.addAttribute("startListTot", startHpMngrService.getStartHpMngrListTotal(startHpMngrSearch));
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
	 * 공통사용코드목록 로드
	 * @param model
	 * @param boardConfig
	 */
	private void loadCodeData(Model model, BoardConfig boardConfig){
		//게시판 카테고리
		if( StringUtils.isNotBlank(boardConfig.getBcCategory1()) ){
			model.addAttribute("bcCategory1CodeList", codeService.getCodeList(boardConfig.getBcCategory1()));
		}
		if( StringUtils.isNotBlank(boardConfig.getBcCategory2()) ){
			model.addAttribute("bcCategory2CodeList", codeService.getCodeList(boardConfig.getBcCategory2()));
		}
		if( StringUtils.isNotBlank(boardConfig.getBcCategory3()) ){
			model.addAttribute("bcCategory3CodeList", codeService.getCodeList(boardConfig.getBcCategory3()));
		}
		//상태코드
		if( StringUtils.isNotBlank(boardConfig.getBcStatusCode()) ){
			model.addAttribute("bcStatusCodeList", codeService.getCodeList(boardConfig.getBcStatusCode()));
		}
	}
	
	
	/**
	 * 대학 서브페이지 정보 get
	 */
	private void getUnivSiteInfo(String univId){

		//String requestUri = AsaproUtils.getFixedRequestUri(request);
		
		Site currentSite = (Site) request.getAttribute("currentSite");
		
		// 대학 기본 정보 select
		if(univId != null && !univId.equals("")){
			UnivHpMngrSearch univHpMngrSearch = new UnivHpMngrSearch();
			univHpMngrSearch.setUnivId(univId);
			univHpMngrSearch.setExpsrYn("Y");
			univHpMngrSearch.setDelYn("N");
			//System.out.println("univId: " + univId);
			//System.out.println("expsrYn: " + univHpMngrSearch.getExpsrYn());
			UnivHpMngr univInfo = univHpMngrService.getUnivHpMngrInfo(univHpMngrSearch);
			
			// 이미지 파일정보 셋팅
			if(null != univInfo.getFileId1() && !"".equals(univInfo.getFileId1())){
				FileInfo fileInfo = new FileInfo();
				fileInfo.setSitePrefix(currentSite.getSitePrefix());
				fileInfo.setFileId(Integer.parseInt(univInfo.getFileId1()));
				univInfo.setFile1Info(fileInfoService.getFileInfo(fileInfo));
			}
			if(null != univInfo.getFileId2() && !"".equals(univInfo.getFileId2())){
				FileInfo fileInfo = new FileInfo();
				fileInfo.setSitePrefix(currentSite.getSitePrefix());
				fileInfo.setFileId(Integer.parseInt(univInfo.getFileId2()));
				univInfo.setFile2Info(fileInfoService.getFileInfo(fileInfo));
			}
			if(null != univInfo.getFileId3() && !"".equals(univInfo.getFileId3())){
				FileInfo fileInfo = new FileInfo();
				fileInfo.setSitePrefix(currentSite.getSitePrefix());
				fileInfo.setFileId(Integer.parseInt(univInfo.getFileId3()));
				univInfo.setFile3Info(fileInfoService.getFileInfo(fileInfo));
			}
			
			
			request.setAttribute("univInfo", univInfo);
		}
			
	}
	
	
	
	
	/**
	 * 대학 현황조회 
	 * @param model
	 * @param currentSite
	 * @param currentUser
	 * @param univHpMngrSearch
	 * @return
	 */
	@RequestMapping(value=Constant.APP_PATH + Constant.SITE_ID_PATH + "/university/list", method=RequestMethod.GET)
	public String univSelectGet(Model model, @CurrentSite Site currentSite
							, @CurrentUser UserMember currentUser
							, @ModelAttribute("univHpMngrSearch") UnivHpMngrSearch univHpMngrSearch
							) {
		
		
		
		// 코드 목록 로드
		this.loadCodes(model);  // 지역코드
		
		/* 대학 정보 조회 */
		
		// : 대표이미지, 학교로고,  학교명(지역명), 한마디, 창업팀 수, 대학교 홈페이지 링크, 최근수정일
		
		
		univHpMngrSearch.setExpsrYn("Y");
		univHpMngrSearch.setPageSize(8);
		univHpMngrSearch.setSortOrder("UPD_DT");
		univHpMngrSearch.setSortDirection("DESC");
		
		List<UnivHpMngr> list = univHpMngrService.getUnivHpMngrList(univHpMngrSearch);
		int total = univHpMngrService.getUnivHpMngrListTotal(univHpMngrSearch);
		
		// 이미지 파일정보 셋팅
		for(int i=0; i<list.size(); i++){
			if(null != list.get(i).getFileId1() && !"".equals(list.get(i).getFileId1())){
				FileInfo fileInfo = new FileInfo();
				fileInfo.setSitePrefix(currentSite.getSitePrefix());
				fileInfo.setFileId(Integer.parseInt(list.get(i).getFileId1()));
				list.get(i).setFile1Info(fileInfoService.getFileInfo(fileInfo));
			}
			
			if(null != list.get(i).getFileId2() && !"".equals(list.get(i).getFileId2())){
				FileInfo fileInfo = new FileInfo();
				fileInfo.setSitePrefix(currentSite.getSitePrefix());
				fileInfo.setFileId(Integer.parseInt(list.get(i).getFileId2()));
				list.get(i).setFile2Info(fileInfoService.getFileInfo(fileInfo));
			}
		}
		
		//System.out.println("list.size(): " + list.size());
		
		Paging paging = new Paging(list, total, univHpMngrSearch);
		model.addAttribute("paging", paging);
				
		return "asapro/theme/campustown/university/univList";
	}
	
	
	/**
	 * 대학 정보 조회 
	 * (1.새소식(university_news) 2.시설공간정보(spaceInfo) 3.창업팀현황(startEmp) 
	 *  4.지원프로그램(sct030_035_40=> sportProgrm) 5.캠퍼스타운소개(intro))
	 * @param model
	 * @param currentSite
	 * @param currentUser
	 * @param univHpMngrSearch
	 * @return
	 */
	//@RequestMapping(value={Constant.APP_PATH + Constant.SITE_ID_PATH + "/university/{univvId}", Constant.APP_PATH + Constant.SITE_ID_PATH + "/content/university/{univvId}"}, method=RequestMethod.GET)
	@RequestMapping(value=Constant.APP_PATH + Constant.SITE_ID_PATH + "/university/{univvId}", method=RequestMethod.GET)
	public String univIntroSelectGet(Model model, @CurrentSite Site currentSite
							, @PathVariable("univvId") String univvId
							, @CurrentUser UserMember currentUser
							, @ModelAttribute("universityForm") UnivHpMngrSearch universityForm
							, @ModelAttribute("univHpMngrSearch") UnivHpMngrSearch univHpMngrSearch
							, @ModelAttribute("spaceForm") UnivHpMngrSearch spaceForm
							, @ModelAttribute("startHpMngrSearch") StartHpMngrSearch startHpMngrSearch
							, @ModelAttribute("boardArticleSearch") BoardArticleSearch boardArticleSearch
							, RentalSearch rentalSearch
							) {
		
		
		String univId = "";
		
		if(null == rentalSearch.getRentCate1() || "".equals(rentalSearch.getRentCate1())){
			univId = (boardArticleSearch.getDepId() == null) ? universityForm.getUnivId() : boardArticleSearch.getDepId();
		}else{
			univId = rentalSearch.getRentCate1();
			//rentalSearch.setSrcDiv("share");
			spaceForm.setUnivId(univId);
			//spaceForm.setSrcDiv("share");
		}
		
		
		// 대학 sidebar3.jsp 데이타 셋팅
		this.getUnivSiteInfo(univId);
		
		// 코드 목록 로드
		this.loadCodes(model);  // 업종
		
		// 대학 부서목록 로드
		this.loadDepartment(model, "university"); 
		
		//boolean pgYn = true;
		
		/* ========= 1. 새소식 (캠퍼스타운 공지사항 게시판) ========= */
		if(univvId.contains("university_news")){ 
			model.addAttribute("univvId", "university_news");
		
			//---------------------------
			// 게시판 조회
			//---------------------------
			BoardConfig boardConfigSearch = new BoardConfig();
			boardConfigSearch.setBcId(univvId);
			boardConfigSearch.setSitePrefix(currentSite.getSitePrefix());
			BoardConfig boardConfig = boardService.getBoardConfig(boardConfigSearch);
			
			if( boardConfig == null ){
				throw new AsaproNotFoundException("게시판이 존재하지 않습니다.");
			}
			if( !boardConfig.isBcUse() ){
				throw new AsaproNotFoundException("사용하지 않는 게시판입니다.");
			}
			model.addAttribute("seo", new SEO(boardConfig.getBcName(), "board.list", boardConfig.getBcName()));
			model.addAttribute("boardConfig", boardConfig);
			
			
			//---------------------------
			// 게시글
			//---------------------------
			boardArticleSearch.setSitePrefix(currentSite.getSitePrefix());
			boardArticleSearch.setBcId(univvId);
			boardArticleSearch.setDefaultSort("BA_REGDATE", "DESC");
			boardArticleSearch.setBaNotice(false);
			boardArticleSearch.setBaUse(true);
			boardArticleSearch.setNow(AsaproUtils.getFormattedDate(new Date()));
			boardArticleSearch.setNowTime(AsaproUtils.getFormattedDate(new Date(),"HH:mm"));
			boardArticleSearch.fixBrokenSvByDefaultCharsets();
			boardArticleSearch.setBaOpenDay(boardConfig.isBcSupportOpenDay());
			boardArticleSearch.setBaCommSelec(boardConfig.isBcSupportCommSelec());
			boardArticleSearch.setDepId(univId);
			if( boardArticleSearch.getPageSize() == Constant.PAGE_SIZE && boardConfig.getBcPageSize() != Constant.PAGE_SIZE ){
				boardArticleSearch.setPageSize(boardConfig.getBcPageSize());
			}

			int total = boardService.getBoardArticleListTotal(boardArticleSearch);
			List<BoardArticle> list = boardService.getBoardArticleList(boardArticleSearch);
			Paging paging = new Paging(list, total, boardArticleSearch);
			model.addAttribute("paging", paging);
			

			//---------------------------
			// 공지글 - 1페이지거나 게시판 설정에 따라서 출력 on/off
			//---------------------------
			if( boardArticleSearch.getCp() == 1 || boardConfig.isBcNoticeEveryPage() ){
				BoardArticleSearch noticeArticleSearch = new BoardArticleSearch();
				noticeArticleSearch.setSitePrefix(currentSite.getSitePrefix());
				noticeArticleSearch.setBcId(univvId);
				noticeArticleSearch.setDefaultSort("BA_REGDATE", "DESC");
				noticeArticleSearch.setBcId(boardArticleSearch.getBcId());
				noticeArticleSearch.setStartDate(boardArticleSearch.getStartDate());
				noticeArticleSearch.setEndDate(boardArticleSearch.getEndDate());
				noticeArticleSearch.setBaCommSelec(boardArticleSearch.getBaCommSelec());
				noticeArticleSearch.setBaComSelCat(boardArticleSearch.getBaComSelCat());
				noticeArticleSearch.setBaMainSelec(boardArticleSearch.getBaMainSelec());
				noticeArticleSearch.setBaOpenDay(boardArticleSearch.getBaOpenDay());
				noticeArticleSearch.setBaCategory1(boardArticleSearch.getBaCategory1());
				noticeArticleSearch.setBaCategory2(boardArticleSearch.getBaCategory2());
				noticeArticleSearch.setBaCategory3(boardArticleSearch.getBaCategory3());
				noticeArticleSearch.setBaNotice(true);
				noticeArticleSearch.setBaUse(true);
				noticeArticleSearch.setNow(AsaproUtils.getFormattedDate(new Date()));
				noticeArticleSearch.setNowTime(AsaproUtils.getFormattedDate(new Date(),"HH:mm"));
				noticeArticleSearch.setDepId(univId);
				List<BoardArticle> noticeList = boardService.getBoardArticleList(noticeArticleSearch);
				model.addAttribute("noticeList", noticeList);
			}
			
			//코드데이터
			this.loadCodeData(model, boardConfig);
			
			//부서목록
			DepartmentSearch departmentSearch = new DepartmentSearch();
			departmentSearch.setPaging(false);
			departmentSearch.setDepUse(true);
			List<Department> departmentList = organizationService.getDepartmentList(departmentSearch);
			model.addAttribute("departmentList", departmentList);
			
			//창업팀 목록
			StartHpMngrSearch startHpMngrSearch2 = new StartHpMngrSearch();
			startHpMngrSearch2.setPaging(false);
			List<StartHpMngr> teamList = startHpMngrService.getStartHpMngrList(startHpMngrSearch2);
			model.addAttribute("teamList", teamList);
			
			//콘트롤러에서 리다이렉트 되었을 경우 flashMap에 담겨온게 있는지 확인
			Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);
			if( flashMap != null && flashMap.get("messageCode") != null ){
				model.addAttribute("messageCode", flashMap.get("messageCode"));
			}
			
		/* ========= 2. 시설공간정보 ========= */
		}else if(univvId.contains("spaceInfo")){
			
			model.addAttribute("univvId", "spaceInfo");
			
			//System.out.println("시설공간 정보 ~~~");
			//System.out.println("srcDiv: " + spaceForm.getSrcDiv());
			
			String srcDiv = spaceForm.getSrcDiv();
			
			//model.addAttribute("srcDiv", srcDiv);
			
			// 1) 입주공간정보 리스트 조회
			if("move".equals(srcDiv) || null == srcDiv){
				
				SpaceSearch spaceSearch = new SpaceSearch();
				spaceSearch.setUnivId(spaceForm.getUnivId());
				spaceSearch.setSitePrefix(currentSite.getSitePrefix());
				spaceSearch.setSrcDiv(srcDiv);
				spaceSearch.fixBrokenSvByDefaultCharsets();
				spaceSearch.setPageSize(4);
				spaceSearch.setPaging(true);
				spaceSearch.setArcUse(true);
				
				List<Space> list = spaceService.getSpaceList(spaceSearch);
				int total = spaceService.getSpaceListTotal(spaceSearch);
				Paging paging = new Paging(list, total, spaceSearch);
				model.addAttribute("paging1", paging);
				
				if(total != 0){
					
					spaceSearch.setArcCategory(list.get(0).getArcCategory());
					ArchiveCategory currentCategory = null;
					
					currentCategory = new ArchiveCategory();
					currentCategory.setSitePrefix(currentSite.getSitePrefix());
					currentCategory.setCatId(spaceSearch.getArcCategory());
					currentCategory = archiveService.getArchiveCategory(currentCategory);
					
					model.addAttribute("archiveCategory", currentCategory);
					if(currentCategory != null){
						//메타코드 로드
						this.loadCode(model, currentCategory);
					}	
				}
				
			// 2) 공유공간정보 리스트 조회
			}else if("share".equals(srcDiv)){
				rentalSearch.setSitePrefix(currentSite.getSitePrefix());
				rentalSearch.fixBrokenSvByDefaultCharsets();
				rentalSearch.setPaging(true);
				rentalSearch.setPageSize(8);
				rentalSearch.setRentUse(true);
				rentalSearch.setRentCate1(spaceForm.getUnivId());
				
				List<Rental> list = rentalService.getRentalList(rentalSearch);
				int total = rentalService.getRentalListTotal(rentalSearch);
				//System.out.println("total: " + total);
				Paging paging = new Paging(list, total, rentalSearch);
				model.addAttribute("paging2", paging);
				
			}
			
			
		/* ========= 3. 창업팀 현황 ========= */	
		}else if(univvId.contains("startEmp")){ 
			
			model.addAttribute("univvId", "startEmp");
			startHpMngrSearch.setUnivId(univId);
			startHpMngrSearch.fixBrokenSvByDefaultCharsets();
			startHpMngrSearch.setPaging(true);
			startHpMngrSearch.setPageSize(5);
			// 해당 대학 소속 창업팀 대표 리스트를 가져온다.
			//List<StartHpMngr> startEmpList = startHpMngrService.getEmpRprsnList(startHpMngrSearch);
			List<StartHpMngr> startEmpList = startHpMngrService.getEmpRprsnList(startHpMngrSearch);
			//System.out.println("startEmpList.size() : " + startEmpList.size());
			int total = startHpMngrService.getEmpRprsnListTotal(startHpMngrSearch);
			//System.out.println("total: " + total);
			Paging paging = new Paging(startEmpList, total, startHpMngrSearch);
			model.addAttribute("paging", paging);
		
			
		/* ========= 4. 지원프로그램 ========= */
		}else if(univvId.contains("sportProgrm")){
			
			model.addAttribute("univvId", "sportProgrm");
			//model.addAttribute("univvId", "sct030_035_40");
			//pgYn = false;
			
			
		/* ========= 5. 캠퍼스타운 소개 ========= */	
		}else if(univvId.contains("intro")){
			
			model.addAttribute("univvId", "intro");
			
		}
		
		/*
		if(pgYn == true){
			return "asapro/theme/campustown/university/"+univvId;
		}else{
			return "asapro/theme/campustown/content/content_campus";
		}
		*/
		return "asapro/theme/campustown/university/"+univvId;
	}
	
	
	/**
	 * 게시물 조회뷰를 반환한다.
	 * @param model
	 * @param currentUser
	 * @param bcId
	 * @param baId
	 * @return 게시물 조회 뷰
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	@RequestMapping(value=Constant.APP_PATH + Constant.SITE_ID_PATH + "/university/{bcId}/{baId}", method=RequestMethod.GET)
	public String boardArticleGet(HttpServletResponse response, Model model, @CurrentSite Site currentSite, @CurrentUser UserMember currentUser, @PathVariable String bcId, @PathVariable Integer baId
			, BoardArticle baIdSearch) throws IllegalAccessException, InvocationTargetException{
		
		BoardConfig bcIdSearch = new BoardConfig();
		bcIdSearch.setSitePrefix(currentSite.getSitePrefix());
		bcIdSearch.setBcId(bcId);
		BoardConfig boardConfig = boardService.getBoardConfig(bcIdSearch);
		
		model.addAttribute("boardConfig", boardConfig);
		
		baIdSearch.setSitePrefix(currentSite.getSitePrefix());
		
		this.getUnivSiteInfo(baIdSearch.getDepId());
			
		
		//------------------------------------------
		// START : 게시판 조회수 처리
		//------------------------------------------
		// 하루한번 조회수 증가 설정시
		if(boardConfig.isBcSupportHitDay() ){
			
			String baReadCookieName = "ba_read_" + currentSite.getSiteId() + "_" + baId;
			if( AsaproUtils.getCookie(request.getCookies(), baReadCookieName) == null ){
				BoardArticle baReadArticle = new BoardArticle();
				baReadArticle.setSitePrefix(currentSite.getSitePrefix());
				baReadArticle.setBaId(baId);
				//조회수 + 1
				int result = boardService.updateBoardArticleHit(baReadArticle);
				if( result > 0 ){
					//쿠키에 기록
					Cookie cookie = new Cookie(baReadCookieName, "ba"+System.currentTimeMillis());
					//cookie.setPath("/");
					cookie.setHttpOnly(true);
					int t = 60 * 60 * 24;//하루 86400
					if(t > 86400) {
						t = 86400;
					}
					cookie.setMaxAge(t);
					response.addCookie(cookie);
				}
			}
		}else{
			//클릭시마다 조회수 증가
			BoardArticle baReadArticle = new BoardArticle();
			baReadArticle.setSitePrefix(currentSite.getSitePrefix());
			baReadArticle.setBaId(baId);
			//조회수 + 1
			boardService.updateBoardArticleHit(baReadArticle);
		}
		//------------------------------------------
		// END : 게시판 조회수 처리
		//------------------------------------------
		
		BoardArticle boardArticle = boardService.getBoardArticle(baIdSearch);
		boardArticle.setSitePrefix(currentSite.getSitePrefix());
		boardArticle.setBoardConfig(boardConfig);
		model.addAttribute("boardArticle", boardArticle);
		model.addAttribute("seo", new SEO(boardArticle.getBaTitle() + " | " + boardConfig.getBcName(), "board.view", StringUtils.abbreviate(boardArticle.getBaContentPlain(), 200)));
		
		//이전글, 다음글
		model.addAttribute("prevBoardArticle", boardService.getPrevBoardArticle(boardArticle));
		model.addAttribute("nextBoardArticle", boardService.getNextBoardArticle(boardArticle));
		
		//코드데이터
		this.loadCodeData(model, boardConfig);
		List<Code> bcNuriCodeList = codeService.getCodeList("NURI_TYPE");
		model.addAttribute("bcNuriCodeList", bcNuriCodeList);
		
		//목록 돌아가기 검색VO
		BoardArticleSearch backListSearch = new BoardArticleSearch();
		BeanUtils.populate(backListSearch, request.getParameterMap());
		backListSearch.fixBrokenSvByDefaultCharsets();
		model.addAttribute("backListSearch", backListSearch);
		
			//return AsaproUtils.getThemePath(request) + "startup/startup_view";
		return "asapro/theme/campustown/university/university_view";
	}
	
	/**
	 * 대학 창업팀 정보 조회
	 * @param model
	 * @param currentSite
	 * @param currentUser
	 * @param univHpMngrSearch
	 * @return
	 */
	/*
	@RequestMapping(value=Constant.APP_PATH + Constant.SITE_ID_PATH + "/university/startups", method=RequestMethod.GET)
	public String univStartupsSelectGet(Model model, @CurrentSite Site currentSite
			, @CurrentUser UserMember currentUser
			, @ModelAttribute("univHpMngrSearch") UnivHpMngrSearch univHpMngrSearch
			, @ModelAttribute("startHpMngrSearch") StartHpMngrSearch startHpMngrSearch
			) {
		
		
		System.out.println("UnivId: "+univHpMngrSearch.getUnivId());
		
		// 대학 sidebar3.jsp 데이타 셋팅
		this.getUnivSiteInfo();
				
		// 코드 목록 로드
		this.loadCodes(model);  // 업종
				
				
		startHpMngrSearch.setUnivId(univHpMngrSearch.getUnivId());
		// 해당 대학 소속 창업팀 대표 리스트를 가져온다.
		//List<StartHpMngr> startEmpList = startHpMngrService.getEmpRprsnList(startHpMngrSearch);
		List<StartHpMngr> startEmpList = startHpMngrService.getEmpRprsnList(startHpMngrSearch);
		System.out.println("startEmpList.size() : " + startEmpList.size());
		int total = startHpMngrService.getEmpRprsnListTotal(startHpMngrSearch);
		System.out.println("total: " + total);
		Paging paging = new Paging(startEmpList, total, startHpMngrSearch);
		model.addAttribute("paging", paging);
		
		return "asapro/theme/campustown/university/univList4";
	}
	*/
	
	
	
}
