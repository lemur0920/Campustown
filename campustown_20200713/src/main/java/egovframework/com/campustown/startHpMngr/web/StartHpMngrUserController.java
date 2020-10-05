package egovframework.com.campustown.startHpMngr.web;

import java.lang.reflect.InvocationTargetException;
import java.util.Calendar;
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

import egovframework.com.asapro.board.service.BoardArticle;
import egovframework.com.asapro.board.service.BoardArticleSearch;
import egovframework.com.asapro.board.service.BoardConfig;
import egovframework.com.asapro.board.service.BoardService;
import egovframework.com.asapro.code.service.Code;
import egovframework.com.asapro.code.service.CodeService;
import egovframework.com.asapro.fileinfo.service.FileInfo;
import egovframework.com.asapro.fileinfo.service.FileInfoService;
import egovframework.com.asapro.member.user.service.UserMember;
import egovframework.com.asapro.organization.service.Department;
import egovframework.com.asapro.organization.service.DepartmentSearch;
import egovframework.com.asapro.organization.service.OrganizationService;
import egovframework.com.asapro.site.service.Site;
import egovframework.com.asapro.support.Constant;
import egovframework.com.asapro.support.SEO;
import egovframework.com.asapro.support.annotation.CurrentSite;
import egovframework.com.asapro.support.annotation.CurrentUser;
import egovframework.com.asapro.support.exception.AsaproNotFoundException;
import egovframework.com.asapro.support.jdbc.AsaproDBUtils;
import egovframework.com.asapro.support.pagination.Paging;
import egovframework.com.asapro.support.util.AsaproUtils;
import egovframework.com.campustown.startHpMngr.service.StartHpMngr;
import egovframework.com.campustown.startHpMngr.service.StartHpMngrSearch;
import egovframework.com.campustown.startHpMngr.service.StartHpMngrService;
import egovframework.com.campustown.univHpMngr.service.UnivHpMngrSearch;
import egovframework.com.campustown.univHpMngr.service.UnivHpMngrService;

/**
 * 관리자(창업팀) 홈페이지 관리 사용자 컨트롤러 
 * @author jaseo
 * @since 2020. 02. 11.
 */
@Controller
public class StartHpMngrUserController {
	
	@Autowired
	private CodeService codeService;
	
	@Autowired
	private HttpServletRequest request;
	
	@Autowired
	private StartHpMngrService startHpMngrService;
	
	@Autowired
	private UnivHpMngrService univHpMngrService;
	
	@Autowired
	private StartHpMngrValidator startHpMngrValidator;
	
	@Autowired
	private FileInfoService fileInfoService;
	
	@Autowired
	private BoardService boardService;
	
	@Autowired
	private OrganizationService organizationService;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(StartHpMngrUserController.class);
	
	
	/**
	 * 코드목록 로드
	 * @param model
	 */
	private void loadCodes(Model model){
		
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
	 * 창업팀 서브페이지 정보 get
	*/
	private void getStartupSiteInfo(String compId){

		Site currentSite = (Site) request.getAttribute("currentSite");
		
		// 회사 창업 활동지수 정보 select
		if(compId != null && !compId.equals("")){
			StartHpMngrSearch startHpMngrSearch = new StartHpMngrSearch();
			startHpMngrSearch.setCompId(compId);
			List<StartHpMngr> startComp = startHpMngrService.getStartHpCompTeamSumList(startHpMngrSearch);
			
			
			// 이미지 파일정보 셋팅
			if(null != startComp.get(0).getFileId1() && !"".equals(startComp.get(0).getFileId1())){
				FileInfo fileInfo = new FileInfo();
				fileInfo.setSitePrefix(currentSite.getSitePrefix());
				fileInfo.setFileId(Integer.parseInt(startComp.get(0).getFileId1()));
				startComp.get(0).setFile1Info(fileInfoService.getFileInfo(fileInfo));
			}
			
			if(null != startComp.get(0).getFileId2() && !"".equals(startComp.get(0).getFileId2())){
				FileInfo fileInfo = new FileInfo();
				fileInfo.setSitePrefix(currentSite.getSitePrefix());
				fileInfo.setFileId(Integer.parseInt(startComp.get(0).getFileId2()));
				startComp.get(0).setFile2Info(fileInfoService.getFileInfo(fileInfo));
			}
			
			request.setAttribute("startComp", startComp.get(0));
			/*
			// 창업팀 글 작성수 조회(게시판: startup_news, idea_talk, free_talk)
			BoardArticleSearch boardArticleSearch = new BoardArticleSearch();
			// 1. startup_news
			boardArticleSearch.setSitePrefix(currentSite.getSitePrefix());
			boardArticleSearch.setBcId("startup_news");
			boardArticleSearch.setTeamId(compId);
			int tot1 = boardService.getBoardArticleListTotal(boardArticleSearch);
			System.out.println("tot1: " + tot1);
			
			// 2. idea_talk
			boardArticleSearch.setBcId("idea_talk");
			//boardArticleSearch.setTeamId(compId);
			int tot2 = boardService.getBoardArticleListTotal(boardArticleSearch);
			System.out.println("tot2: " + tot2);
			
			// 3. free_talk
			boardArticleSearch.setBcId("free_talk");
			//boardArticleSearch.setTeamId(compId);
			int tot3 = boardService.getBoardArticleListTotal(boardArticleSearch);
			System.out.println("tot3: " + tot3);
			
			request.setAttribute("article_cnt", (tot1+tot2+tot3) );
			*/
			
			// 창업팀 글 작성후, 댓글 수, 활동점수 조회
			Date today = new Date();
			String year = AsaproUtils.getFormattedDate(today, "yyyy");
			String month = AsaproUtils.getFormattedDate(today, "MM");
			
			startHpMngrSearch.setSitePrefix(currentSite.getSitePrefix());
			startHpMngrSearch.setPaging(false);
			startHpMngrSearch.setSearchYear(year);
			startHpMngrSearch.setSearchMonth(month);
			startHpMngrSearch.setCompId(startComp.get(0).getCompId());
			
			//List<StartHpMngr> startComp2 = startHpMngrService.getStartHomepageRankList(startHpMngrSearch);
			//System.out.println("size: " + startComp2.size());
			StartHpMngr startComp2 = startHpMngrService.getStartHomepageRank(startHpMngrSearch);
			
			request.setAttribute("month", month);
			request.setAttribute("startComp2", startComp2);
		}
	}
	
	
	/**
	 * 창업팀 현황조회
	 * @param model
	 * @param currentSite
	 * @param currentUser
	 * @param startCompForm
	 * @return
	 */
	@RequestMapping(value=Constant.APP_PATH + Constant.SITE_ID_PATH + "/startup/list", method=RequestMethod.GET)
	public String startCompSelectGet(Model model, @CurrentSite Site currentSite
							, @CurrentUser UserMember currentUser
							, @ModelAttribute("startHpMngrSearch") StartHpMngrSearch startHpMngrSearch){
		
		
		/* 창업팀 정보, 창업팀 활동지수 정보 조회 */
		
		// 창업팀 + 창업팀 활동지수 조회
		// 1. 회사정보
		// : 회사 대표 이미지/회사명/대표이름/회사한마디/관심분야
		
		// 2. 활동지수
		
		// 3. 임직원 정보
		// : 대표자 이름
//		System.out.println("BsnsRealm: "+startHpMngrSearch.getBsnsRealm());
		
		startHpMngrSearch.setExpsrYn("Y");
		startHpMngrSearch.setPageSize(8);
		startHpMngrSearch.setSortOrder("UPD_DT");
		startHpMngrSearch.setSortDirection("DESC");
		
		List<StartHpMngr> list = startHpMngrService.getStartHpCompTeamSumList(startHpMngrSearch);
		int total = startHpMngrService.getStartHpCompTeamSumListTotal(startHpMngrSearch);
		
		// 이미지 파일정보 셋팅
		for(int i=0; i<list.size(); i++){
			if(null != list.get(i).getFileId1() && !"".equals(list.get(i).getFileId1())){
				FileInfo fileInfo = new FileInfo();
				fileInfo.setSitePrefix(currentSite.getSitePrefix());
				fileInfo.setFileId(Integer.parseInt(list.get(i).getFileId1()));
				list.get(i).setFile1Info(fileInfoService.getFileInfo(fileInfo));
			}
			//System.out.println(i+".: "+list.get(i).getFile1Info());
		}
		
		System.out.println("list.size(): " + list.size());
		//System.out.println("total: " + total);
		
		// 코드 목록 로드
		this.loadCodes(model);  // 사업분야 코드,  관심분야 코드, 지역코드
		
		// 대학 부서목록 로드
		this.loadDepartment(model, "university"); 

		
		Paging paging = new Paging(list, total, startHpMngrSearch);
		model.addAttribute("paging", paging);
		
		return "asapro/theme/campustown/startup/startList"; 
	}	
	
	/**
	 * 창업팀 참여자 정보 조회
	 * @param model
	 * @param currentSite
	 * @param currentUser
	 * @param startCompForm
	 * @return
	 */
	@RequestMapping(value=Constant.APP_PATH + Constant.SITE_ID_PATH + "/startup/empList", method=RequestMethod.GET)
	public String startEmpSelectGet(Model model, @CurrentSite Site currentSite
							, @CurrentUser UserMember currentUser
							, @ModelAttribute("startHpMngrSearch") StartHpMngrSearch startHpMngrSearch){
		
		
		
		
		
		/* 창업팀 정보, 대학 정보, 창업팀 임직원 정보 조회 */
		
		// 1. 창업팀 정보
		// : 사업분야, 회사명, 창업팀 홈페이지
		
		// 2. 대학 정보
		// : 서울시 지역 구 코드 & 컬럼, 대학명
		
		// 3. 창업팀 임직원 정보
		// : 이름, 연락처& 노출유무, 이메일& 노출유무, 서울시 ID& 노출유무
		
		// 이미지 정보 없음~!!!
		
		// 코드 목록 로드
		this.loadCodes(model);  // 사업분야 코드,  서울시 구 코드
		startHpMngrSearch.setExpsrYn("Y");
		List<StartHpMngr> list = startHpMngrService.getStartHpCompEmpssSumList(startHpMngrSearch);
		int total = startHpMngrService.getStartHpCompEmpssSumListTotal(startHpMngrSearch);
		
//		System.out.println("list.size(): "+list.size());
//		System.out.println("total: " + total);
		
		
		Paging paging = new Paging(list, total, startHpMngrSearch);
		model.addAttribute("paging", paging);
		
		
		return "asapro/theme/campustown/startup/startList2";
	}
	
	/**
	 * 창업팀 회사 소개(intro), 새소식(startup_news)
	 * @param model
	 * @param currentSite
	 * @param currentUser
	 * @param startCompForm
	 * @return
	 */
	@RequestMapping(value=Constant.APP_PATH + Constant.SITE_ID_PATH + "/startup/{startId}", method=RequestMethod.GET)
	public String startIntroSelectGet(Model model, @CurrentSite Site currentSite
							, @PathVariable("startId") String startId
							, @CurrentUser UserMember currentUser
							, @ModelAttribute("startHpMngrSearch") StartHpMngrSearch startHpMngrSearch
							, @ModelAttribute("startupForm") StartHpMngrSearch startupForm
							, @ModelAttribute("boardArticleSearch") BoardArticleSearch boardArticleSearch
							){
		
		
		String compId = (boardArticleSearch.getTeamId() == null) ? startupForm.getCompId() : boardArticleSearch.getTeamId();
		
		model.addAttribute("compId", compId);
		
		if(startId.contains("intro")){ // 회사소개
			model.addAttribute("startId", "intro");
			
		}else if(startId.contains("startup_news")){ // 새소식
			model.addAttribute("startId", "startup_news");
			
			//창업팀 목록
			StartHpMngrSearch startHpMngrSearch2 = new StartHpMngrSearch();
			startHpMngrSearch2.setPaging(false);
			startHpMngrSearch2.setDefaultSort(null, null);
			List<StartHpMngr> teamList = startHpMngrService.getStartHpMngrList(startHpMngrSearch2);
			model.addAttribute("teamList", teamList);
			
			//---------------------------
			// 게시판 조회
			//---------------------------
			BoardConfig boardConfigSearch = new BoardConfig();
			boardConfigSearch.setBcId(startId);
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
			boardArticleSearch.setBcId(startId);
			boardArticleSearch.setDefaultSort("BA_REGDATE", "DESC");
			boardArticleSearch.setBaNotice(false);
			boardArticleSearch.setBaUse(true);
			boardArticleSearch.setNow(AsaproUtils.getFormattedDate(new Date()));
			boardArticleSearch.setNowTime(AsaproUtils.getFormattedDate(new Date(),"HH:mm"));
			boardArticleSearch.fixBrokenSvByDefaultCharsets();
			boardArticleSearch.setBaOpenDay(boardConfig.isBcSupportOpenDay());
			boardArticleSearch.setBaCommSelec(boardConfig.isBcSupportCommSelec());
			boardArticleSearch.setTeamId(compId);
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
				noticeArticleSearch.setBcId(startId);
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
				noticeArticleSearch.setTeamId(compId);
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
			
			
			//콘트롤러에서 리다이렉트 되었을 경우 flashMap에 담겨온게 있는지 확인
			Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);
			if( flashMap != null && flashMap.get("messageCode") != null ){
				model.addAttribute("messageCode", flashMap.get("messageCode"));
			}
			
		}
		
		// 창업팀 sidebar2.jsp 데이타 셋팅
		this.getStartupSiteInfo(compId);
		
		// 코드 목록 로드
		this.loadCodes(model);
		
		// 1. 창업팀 정보 조회
		startHpMngrSearch.setSortOrder(null);
		StartHpMngr startCompInfo = startHpMngrService.getStartHpMngrCompInfo(startHpMngrSearch);
		model.addAttribute("startCompInfo", startCompInfo);
		
		// 2. 임직원 정보 조회
		// 2-1. 대표 정보
		StartHpMngr startEmpInfo = startHpMngrService.getEmpRprsnInfo(startHpMngrSearch);
//		System.out.println("startEmpInfo: " + startEmpInfo);
		// 이미지 파일정보 셋팅
		if(startEmpInfo != null && null != startEmpInfo.getFileId3() && !"".equals(startEmpInfo.getFileId3())){
			FileInfo fileInfo = new FileInfo();
			fileInfo.setSitePrefix(currentSite.getSitePrefix());
			fileInfo.setFileId(Integer.parseInt(startEmpInfo.getFileId3()));
			startEmpInfo.setFile3Info(fileInfoService.getFileInfo(fileInfo));
		}
		model.addAttribute("startEmpInfo", startEmpInfo);
		
		// 2-2_1. 임직원 정보
		startHpMngrSearch.setRprsntvYn("N");
		startHpMngrSearch.setExpsrYn("Y");
		List<StartHpMngr> startEmpList = startHpMngrService.getStartHpCompEmpssSumList(startHpMngrSearch);
		
		// 2-2_2. 노출유무 N 포함 모든 임직원 수 조회
		StartHpMngrSearch startHpMngrSearch3 = new StartHpMngrSearch();
		startHpMngrSearch3.setCompId(startHpMngrSearch.getCompId());
		int total = startHpMngrService.getStartHpCompEmpssSumListTotal(startHpMngrSearch3);
//		System.out.println("total22222: " + total);
		model.addAttribute("startEmpList", startEmpList);
		model.addAttribute("total", total);
		
		
		return "asapro/theme/campustown/startup/"+startId;
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
	@RequestMapping(value=Constant.APP_PATH + Constant.SITE_ID_PATH + "/startup/{bcId}/{baId}", method=RequestMethod.GET)
	public String boardArticleGet(HttpServletResponse response, Model model, @CurrentSite Site currentSite, @CurrentUser UserMember currentUser, @PathVariable String bcId, @PathVariable Integer baId
			, BoardArticle baIdSearch ) throws IllegalAccessException, InvocationTargetException{
		
		BoardConfig bcIdSearch = new BoardConfig();
		bcIdSearch.setSitePrefix(currentSite.getSitePrefix());
		bcIdSearch.setBcId(bcId);
		BoardConfig boardConfig = boardService.getBoardConfig(bcIdSearch);
		
		model.addAttribute("boardConfig", boardConfig);
		
		baIdSearch.setSitePrefix(currentSite.getSitePrefix());
		
		
		String compId = baIdSearch.getTeamId();
//		System.out.println("bcId: " + boardConfig.getBcId());
//		System.out.println("compId: " + compId);

		this.getStartupSiteInfo(compId);
			
		
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
		return "asapro/theme/campustown/startup/startup_view";
	}
	
	
	
	
	/**
	 * 창업활동지수 랭킹 조회
	 * @param model
	 * @param sortId
	 * @param currentSite
	 * @param currentUser
	 * @param startHpMngrSearch
	 * @return
	 */
	@RequestMapping(value=Constant.APP_PATH + Constant.SITE_ID_PATH + "/startup/startActIdxList/{sortId}", method=RequestMethod.GET)
	public String startActIdxSelectGet(Model model
							, @PathVariable("sortId") String sortId
							, @CurrentSite Site currentSite
							, @CurrentUser UserMember currentUser
							, @ModelAttribute("startActIdxForm") StartHpMngrSearch startActIdxForm
							, @ModelAttribute("startHpMngrSearch") StartHpMngrSearch startHpMngrSearch){
		
		// sortOrder 셋팅 (각 메뉴별 랭킹)
		if(sortId.contains("invest")){ // 투자액
			startHpMngrSearch.setSortOrder("TOT_INVT_AMT");
			model.addAttribute("sortId", "invest");
			
		}else if(sortId.contains("sale")){ // 매출액
			startHpMngrSearch.setSortOrder("TOT_SALE_AMT");
			model.addAttribute("sortId", "sale");
			
		}else if(sortId.contains("employees")){ // 고용인 수
			startHpMngrSearch.setSortOrder("TOT_EMPLY_CNT");
			model.addAttribute("sortId", "employees");
			
		}else if(sortId.contains("intellProp")){ // 지적 재산
			startHpMngrSearch.setSortOrder("TOT_INTELL_PROP");
			model.addAttribute("sortId", "intellProp");
		}
		
		// 현재 년도, 월
		Calendar cal = Calendar.getInstance();
		int year = cal.get(cal.YEAR);
		int month = cal.get(cal.MONTH) + 1;
		
		int cardiNum = 1; // 상반기
		if(month >= 7 && month <= 12){
			cardiNum = 2;    // 하반기
		}
		
		
		int curCardi = cardiNum;
		int curYear = year;
		
		if(cardiNum == 2){
			curCardi = 1;   // 기수만 -1 처리
		}
		
		if(cardiNum == 1){
			curYear = year - 1;
			curCardi = 2;
		}
		
		// year, cardiNum 셋팅
		if(null == startActIdxForm.getSesnYear() || ("").equals(startActIdxForm.getSesnYear())){
			startHpMngrSearch.setSesnYear(String.valueOf(curYear));
		}else{
			startHpMngrSearch.setSesnYear(startActIdxForm.getSesnYear());
		}
		
		if(null == startActIdxForm.getCardiNum() || ("").equals(startActIdxForm.getCardiNum())){
			startHpMngrSearch.setCardiNum(String.valueOf(curCardi));
		}else{
			startHpMngrSearch.setCardiNum(startActIdxForm.getCardiNum());
		}
		
		//startHpMngrSearch.setPaging(false);
		startHpMngrSearch.setPageSize(5);
				
		model.addAttribute("startActIdxForm", startHpMngrSearch);
		
		List<StartHpMngr> list = startHpMngrService.getStartHpCompActIdxList(startHpMngrSearch);
		int total = startHpMngrService.getStartHpCompActIdxListTotal(startHpMngrSearch);
		
		// 이미지 파일정보 셋팅
		for(int i=0; i<list.size(); i++){
			if(null != list.get(i).getFileId1() && !"".equals(list.get(i).getFileId1())){
				FileInfo fileInfo = new FileInfo();
				fileInfo.setSitePrefix(currentSite.getSitePrefix());
				fileInfo.setFileId(Integer.parseInt(list.get(i).getFileId1()));
				list.get(i).setFile1Info(fileInfoService.getFileInfo(fileInfo));
			}
		}
		
		startHpMngrSearch.setPaging(true);
		startHpMngrSearch.setPageSize(5);
		Paging paging = new Paging(list, total, startHpMngrSearch);
		model.addAttribute("paging", paging);
		
		return "asapro/theme/campustown/startup/startActIdxList";
	}
	
	
	/**
	 * 홈페이지 활동랭킹 목록을 반환한다.
	 * @param request
	 * @param response
	 * @param currentSite
	 * @param statMenu
	 */
	@RequestMapping(value=Constant.APP_PATH + Constant.SITE_ID_PATH + "/startup/homepageActivity/rankList", method=RequestMethod.GET)
	public String statBestListGet(Model model, @CurrentSite Site currentSite, @ModelAttribute("startHpMngrSearch") StartHpMngrSearch startHpMngrSearch){
		//어제 날짜 기준 월,주의 데이터를 대상
		Date today = new Date();
		String year = AsaproUtils.getFormattedDate(today, "yyyy");
		String month = AsaproUtils.getFormattedDate(today, "MM");
		
		startHpMngrSearch.setSitePrefix(currentSite.getSitePrefix());
		startHpMngrSearch.setPageSize(5);
		if(StringUtils.isBlank(startHpMngrSearch.getSearchYear()) ){
			startHpMngrSearch.setSearchYear(year);
		}
		
		//년도목록
		List<Map<String, Object>> yearList = startHpMngrService.getYearListByBoard(startHpMngrSearch);
		model.addAttribute("yearList", yearList);
		
		//해당년도의 월목록
		List<Map<String, Object>> monthList = startHpMngrService.getMonthListByBoard(startHpMngrSearch);
		model.addAttribute("monthList", monthList);
		
		//월 정보가 없을경우 해당년도의 데이터중 가장큰 월
		if(StringUtils.isBlank(startHpMngrSearch.getSearchMonth()) ){
			if(monthList != null && !monthList.isEmpty() ){
				startHpMngrSearch.setSearchMonth((String)(monthList.get(monthList.size() - 1).get("SEARCH_MONTH")) );
			} else {
				startHpMngrSearch.setSearchMonth(month);
			}
		}

		//월별
		List<StartHpMngr> startHomepageRankList = startHpMngrService.getStartHomepageRankList(startHpMngrSearch);
		
		// 이미지 파일정보 셋팅
		for(int i=0; i<startHomepageRankList.size(); i++){
			if(null != startHomepageRankList.get(i).getFileId1() && !"".equals(startHomepageRankList.get(i).getFileId1())){
				FileInfo fileInfo = new FileInfo();
				fileInfo.setSitePrefix(currentSite.getSitePrefix());
				fileInfo.setFileId(Integer.parseInt(startHomepageRankList.get(i).getFileId1()));
				startHomepageRankList.get(i).setFile1Info(fileInfoService.getFileInfo(fileInfo));
			}
		}
		model.addAttribute("startHomepageRankList", startHomepageRankList);
		
		return AsaproUtils.getThemePath(request) + "startup/startHomepageRankList";
	}
	
	
}
