/**
 * 
 */
package egovframework.com.asapro.dashboard.web;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.ibm.icu.text.SimpleDateFormat;
import com.ibm.icu.util.Calendar;

import egovframework.com.asapro.board.service.BoardArticle;
import egovframework.com.asapro.board.service.BoardArticleSearch;
import egovframework.com.asapro.board.service.BoardConfig;
import egovframework.com.asapro.board.service.BoardService;
import egovframework.com.asapro.code.service.CodeService;
import egovframework.com.asapro.organization.service.Department;
import egovframework.com.asapro.organization.service.DepartmentSearch;
import egovframework.com.asapro.organization.service.OrganizationService;
//import egovframework.com.asapro.comment.service.CommentSearch;
//import egovframework.com.asapro.comment.service.CommentService;
//import egovframework.com.asapro.member.service.MemberSearch;
//import egovframework.com.asapro.member.service.MemberService;
import egovframework.com.asapro.site.service.Site;
//import egovframework.com.asapro.statistics.service.StatSearch;
//import egovframework.com.asapro.statistics.service.StatisticsService;
import egovframework.com.asapro.support.Constant;
import egovframework.com.asapro.support.SEO;
import egovframework.com.asapro.support.annotation.CurrentSite;
import egovframework.com.asapro.support.exception.AsaproNotFoundException;
import egovframework.com.asapro.support.pagination.Paging;
//import egovframework.com.asapro.watchdog.service.WatchDogLog;
//import egovframework.com.asapro.watchdog.service.WatchDogLogSearch;
//import egovframework.com.asapro.watchdog.service.WatchDogService;
import egovframework.com.asapro.support.util.AsaproUtils;
import egovframework.com.campustown.startHpMngr.service.StartHpMngr;
import egovframework.com.campustown.startHpMngr.service.StartHpMngrSearch;
import egovframework.com.campustown.startHpMngr.service.StartHpMngrService;
import egovframework.com.campustown.univHpMngr.service.UnivHpMngrSearch;
import egovframework.com.campustown.univHpMngr.service.UnivHpMngrService;

/**
 * 대시보드 관리자 컨트롤러
 * @author yckim
 * @since 2018. 3. 3.
 *
 */
@Controller
public class DashboardAdminController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(DashboardAdminController.class);
	
	/*
	@Autowired
	private WatchDogService watchDogService;
	
	@Autowired
	private CommentService commentService;
	
	@Autowired
	private MemberService memberService;
	
	@Autowired
	private StatisticsService statisticsService;
	*/
	
	@Autowired
	private HttpServletRequest request;
	
	@Autowired
	private UnivHpMngrService univHpMngrService;
	
	@Autowired
	private StartHpMngrService startHpMngrService;
	
	@Autowired
	private CodeService codeService;
	
	@Autowired
	private BoardService boardService;
	
	@Autowired
	private OrganizationService organizationService;
	
	/**
	 * 공통사용코드목록 로드
	 * @param model
	 * @param boardConfig
	 */
	private void loadCodeData(Model model, BoardConfig boardConfig, BoardConfig boardConfig2, BoardConfig boardConfig3, BoardConfig boardConfig4){

		//게시판 카테고리 1
		if(boardConfig != null){
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
		
		
		//게시판 카테고리 2
		if(boardConfig2 != null){
			if( StringUtils.isNotBlank(boardConfig2.getBcCategory1()) ){
				model.addAttribute("bcCategory1CodeList2", codeService.getCodeList(boardConfig2.getBcCategory1()));
			}
			if( StringUtils.isNotBlank(boardConfig2.getBcCategory2()) ){
				model.addAttribute("bcCategory2CodeList2", codeService.getCodeList(boardConfig2.getBcCategory2()));
			}
			if( StringUtils.isNotBlank(boardConfig2.getBcCategory3()) ){
				model.addAttribute("bcCategory3CodeList2", codeService.getCodeList(boardConfig2.getBcCategory3()));
			}
			//상태코드
			if( StringUtils.isNotBlank(boardConfig2.getBcStatusCode()) ){
				model.addAttribute("bcStatusCodeList2", codeService.getCodeList(boardConfig2.getBcStatusCode()));
			}
		}
		
		//게시판 카테고리 3
		if(boardConfig3 != null){
			if( StringUtils.isNotBlank(boardConfig3.getBcCategory1()) ){
				model.addAttribute("bcCategory1CodeList3", codeService.getCodeList(boardConfig3.getBcCategory1()));
			}
			if( StringUtils.isNotBlank(boardConfig3.getBcCategory2()) ){
				model.addAttribute("bcCategory2CodeList3", codeService.getCodeList(boardConfig3.getBcCategory2()));
			}
			if( StringUtils.isNotBlank(boardConfig3.getBcCategory3()) ){
				model.addAttribute("bcCategory3CodeList3", codeService.getCodeList(boardConfig3.getBcCategory3()));
			}
			//상태코드
			if( StringUtils.isNotBlank(boardConfig3.getBcStatusCode()) ){
				model.addAttribute("bcStatusCodeList3", codeService.getCodeList(boardConfig3.getBcStatusCode()));
			}
		}
		
		//게시판 카테고리 4
		if(boardConfig4 != null){
			if( StringUtils.isNotBlank(boardConfig4.getBcCategory1()) ){
				model.addAttribute("bcCategory1CodeList4", codeService.getCodeList(boardConfig4.getBcCategory1()));
			}
			if( StringUtils.isNotBlank(boardConfig4.getBcCategory2()) ){
				model.addAttribute("bcCategory2CodeList4", codeService.getCodeList(boardConfig4.getBcCategory2()));
			}
			if( StringUtils.isNotBlank(boardConfig4.getBcCategory3()) ){
				model.addAttribute("bcCategory3CodeList4", codeService.getCodeList(boardConfig4.getBcCategory3()));
			}
			//상태코드
			if( StringUtils.isNotBlank(boardConfig4.getBcStatusCode()) ){
				model.addAttribute("bcStatusCodeList4", codeService.getCodeList(boardConfig4.getBcStatusCode()));
			}
		}
		
	}
	
	
	/**
	 * 관리자 대시보드 뷰를 반환한다.
	 * @param model
	 * @param currentSite
	 * @return 대시보드 뷰
	 * @throws ParseException 
	 */
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/dashboard.do", method=RequestMethod.GET)
	public String dashboardGet(Model model, @CurrentSite Site currentSite) throws ParseException{
		Date now = new Date();
		//System.out.println("대쉬보드 진입 (" + request.getRemoteAddr() + ") - " + now);
		
		
		//=========================================================================================
		// 0. 대학 등록 수, 창업팀 등록 수 
		//=========================================================================================
		
		UnivHpMngrSearch univHpMngrSearch = new UnivHpMngrSearch();
		univHpMngrSearch.setExpsrYn("Y");
		model.addAttribute("univListTot", univHpMngrService.getUnivHpMngrListTotal(univHpMngrSearch));
		
		StartHpMngrSearch startHpMngrSearch2 = new StartHpMngrSearch();
		startHpMngrSearch2.setExpsrYn("Y");
		model.addAttribute("startListTot", startHpMngrService.getStartHpMngrListTotal(startHpMngrSearch2));
		
		
		
		
		
		//=========================================================================================
		// 1. 공지사항
		//=========================================================================================

		//---------------------------
		// 게시판 조회
		//---------------------------
		BoardConfig boardConfigSearch = new BoardConfig();
		BoardArticleSearch boardArticleSearch = new BoardArticleSearch();
		boardConfigSearch.setBcId("sct_news");
		boardConfigSearch.setSitePrefix(currentSite.getSitePrefix());
		BoardConfig boardConfig = boardService.getBoardConfig(boardConfigSearch);
		
		if( boardConfig == null ){
			try {
				throw new AsaproNotFoundException("게시판이 존재하지 않습니다.");
			} catch (AsaproNotFoundException e) {
				LOGGER.error("[AsaproNotFoundException] Try/Catch... : "+ e.getMessage());
			}
		}
		if( !boardConfig.isBcUse() ){
			try {
				throw new AsaproNotFoundException("사용하지 않는 게시판입니다.");
			} catch (AsaproNotFoundException e) {
				LOGGER.error("[AsaproNotFoundException] Try/Catch... : "+ e.getMessage());
			}
		}
		model.addAttribute("seo", new SEO(boardConfig.getBcName(), "board.list", boardConfig.getBcName()));
		model.addAttribute("boardConfig", boardConfig);
		
		int totListSize = 5;

		//---------------------------
		// 공지글 - 1페이지거나 게시판 설정에 따라서 출력 on/off
		//---------------------------
		if( boardArticleSearch.getCp() == 1 || boardConfig.isBcNoticeEveryPage() ){
			BoardArticleSearch noticeArticleSearch = new BoardArticleSearch();
			noticeArticleSearch.setSitePrefix(currentSite.getSitePrefix());
			noticeArticleSearch.setDefaultSort("BA_REGDATE", "DESC");
			noticeArticleSearch.setBcId("sct_news");
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
			noticeArticleSearch.setPageSize(totListSize);
			List<BoardArticle> noticeList = boardService.getBoardArticleList(noticeArticleSearch);
			
			model.addAttribute("noticeList", noticeList);
			totListSize = totListSize - noticeList.size();
		}
				
		
		//---------------------------
		// 게시글
		//---------------------------
		boardArticleSearch.setBcId("sct_news");
		boardArticleSearch.setSitePrefix(currentSite.getSitePrefix());
		boardArticleSearch.setDefaultSort("BA_REGDATE", "DESC");
		boardArticleSearch.setBaNotice(false);
		boardArticleSearch.setBaUse(true);
		boardArticleSearch.setNow(AsaproUtils.getFormattedDate(new Date()));
		boardArticleSearch.setNowTime(AsaproUtils.getFormattedDate(new Date(),"HH:mm"));
		boardArticleSearch.fixBrokenSvByDefaultCharsets();
		boardArticleSearch.setBaOpenDay(boardConfig.isBcSupportOpenDay());
		boardArticleSearch.setBaCommSelec(boardConfig.isBcSupportCommSelec());
		boardArticleSearch.setPageSize(totListSize);
		
		int total = boardService.getBoardArticleListTotal(boardArticleSearch);
		List<BoardArticle> list = boardService.getBoardArticleList(boardArticleSearch);
		Paging paging = new Paging(list, total, boardArticleSearch);
		model.addAttribute("paging", paging);
		
		//코드데이터
		this.loadCodeData(model, boardConfig, null, null, null);	

		
		
		
		//=========================================================================================
		// 2. 명예의전당  - 게시판 (// 공지, 비공지 상관없이 최근 등록된 글 하나)
		//=========================================================================================
		
		//---------------------------
		// 게시판 조회
		//---------------------------
		BoardConfig boardConfigSearch4 = new BoardConfig();
		BoardArticleSearch boardArticleSearch4 = new BoardArticleSearch();
		boardConfigSearch4.setBcId("fame");
		boardConfigSearch4.setSitePrefix(currentSite.getSitePrefix());
		BoardConfig boardConfig4 = boardService.getBoardConfig(boardConfigSearch4);
		
		if( boardConfig4 == null ){
			try {
				throw new AsaproNotFoundException("게시판이 존재하지 않습니다.");
			} catch (AsaproNotFoundException e) {
				LOGGER.error("[AsaproNotFoundException] Try/Catch... : "+ e.getMessage());
			}
		}
		if( !boardConfig4.isBcUse() ){
			try {
				throw new AsaproNotFoundException("사용하지 않는 게시판입니다.");
			} catch (AsaproNotFoundException e) {
				LOGGER.error("[AsaproNotFoundException] Try/Catch... : "+ e.getMessage());
			}
		}
		model.addAttribute("seo", new SEO(boardConfig4.getBcName(), "board.list", boardConfig4.getBcName()));
		model.addAttribute("boardConfig4", boardConfig4);
		

		//---------------------------
		// 공지글 - 1페이지거나 게시판 설정에 따라서 출력 on/off
		//---------------------------
		// ===> 없음!!!		
		
		//---------------------------
		// 게시글
		//---------------------------
		boardArticleSearch4.setBcId("fame");
		boardArticleSearch4.setSitePrefix(currentSite.getSitePrefix());
		boardArticleSearch4.setDefaultSort("BA_REGDATE", "DESC");
		boardArticleSearch.setBaNotice(null);	//공지글과 와 공지아닌글 모두 가져온다
		boardArticleSearch.setBaUse(true);	//게시하는 글만
		boardArticleSearch4.setNow(AsaproUtils.getFormattedDate(new Date()));
		boardArticleSearch4.setNowTime(AsaproUtils.getFormattedDate(new Date(),"HH:mm"));
		boardArticleSearch4.fixBrokenSvByDefaultCharsets();
		boardArticleSearch4.setBaOpenDay(boardConfig4.isBcSupportOpenDay());
		boardArticleSearch4.setBaCommSelec(boardConfig4.isBcSupportCommSelec());
		boardArticleSearch4.setPageSize(5);
		
		int total4 = boardService.getBoardArticleListTotal(boardArticleSearch4);
		List<BoardArticle> list4 = boardService.getBoardArticleList(boardArticleSearch4);
		Paging paging4 = new Paging(list4, total4, boardArticleSearch4);
		model.addAttribute("paging4", paging4);

		//코드데이터
		this.loadCodeData(model, null, null, null, boardConfig4);	
		
		
		
		
		//=========================================================================================
		// 3. 캠퍼스타운 공지사항
		//=========================================================================================
		
		//---------------------------
		// 게시판 조회
		//---------------------------
		BoardConfig boardConfigSearch2 = new BoardConfig();
		BoardArticleSearch boardArticleSearch2 = new BoardArticleSearch();
		boardConfigSearch2.setBcId("university_news");
		boardConfigSearch2.setSitePrefix(currentSite.getSitePrefix());
		BoardConfig boardConfig2 = boardService.getBoardConfig(boardConfigSearch2);
		
		if( boardConfig2 == null ){
			try {
				throw new AsaproNotFoundException("게시판이 존재하지 않습니다.");
			} catch (AsaproNotFoundException e) {
				LOGGER.error("[AsaproNotFoundException] Try/Catch... : "+ e.getMessage());
			}
		}
		if( !boardConfig2.isBcUse() ){
			try {
				throw new AsaproNotFoundException("사용하지 않는 게시판입니다.");
			} catch (AsaproNotFoundException e) {
				LOGGER.error("[AsaproNotFoundException] Try/Catch... : "+ e.getMessage());
			}
		}
		model.addAttribute("seo", new SEO(boardConfig2.getBcName(), "board.list", boardConfig2.getBcName()));
		model.addAttribute("boardConfig2", boardConfig2);
		
		int totListSize2 = 5;

		//---------------------------
		// 공지글 - 1페이지거나 게시판 설정에 따라서 출력 on/off
		//---------------------------
		if( boardArticleSearch2.getCp() == 1 || boardConfig2.isBcNoticeEveryPage() ){
			
			BoardArticleSearch noticeArticleSearch2 = new BoardArticleSearch();
			noticeArticleSearch2.setSitePrefix(currentSite.getSitePrefix());
			noticeArticleSearch2.setDefaultSort("BA_REGDATE", "DESC");
			noticeArticleSearch2.setBcId("university_news");
			noticeArticleSearch2.setStartDate(boardArticleSearch2.getStartDate());
			noticeArticleSearch2.setEndDate(boardArticleSearch2.getEndDate());
			noticeArticleSearch2.setBaCommSelec(boardArticleSearch2.getBaCommSelec());
			noticeArticleSearch2.setBaComSelCat(boardArticleSearch2.getBaComSelCat());
			noticeArticleSearch2.setBaMainSelec(boardArticleSearch2.getBaMainSelec());
			noticeArticleSearch2.setBaOpenDay(boardArticleSearch2.getBaOpenDay());
			noticeArticleSearch2.setBaCategory1(boardArticleSearch2.getBaCategory1());
			noticeArticleSearch2.setBaCategory2(boardArticleSearch2.getBaCategory2());
			noticeArticleSearch2.setBaCategory3(boardArticleSearch2.getBaCategory3());
			noticeArticleSearch2.setBaNotice(true);
			noticeArticleSearch2.setBaUse(true);
			noticeArticleSearch2.setNow(AsaproUtils.getFormattedDate(new Date()));
			noticeArticleSearch2.setNowTime(AsaproUtils.getFormattedDate(new Date(),"HH:mm"));
			noticeArticleSearch2.setPageSize(totListSize2);
			List<BoardArticle> noticeList2 = boardService.getBoardArticleList(noticeArticleSearch2);
			
			model.addAttribute("noticeList2", noticeList2);
			totListSize2 = totListSize2 - noticeList2.size();
		}
				
		
		//---------------------------
		// 게시글
		//---------------------------
		boardArticleSearch2.setBcId("university_news");
		boardArticleSearch2.setSitePrefix(currentSite.getSitePrefix());
		boardArticleSearch2.setDefaultSort("BA_REGDATE", "DESC");
		boardArticleSearch2.setBaNotice(false);
		boardArticleSearch2.setBaUse(true);
		boardArticleSearch2.setNow(AsaproUtils.getFormattedDate(new Date()));
		boardArticleSearch2.setNowTime(AsaproUtils.getFormattedDate(new Date(),"HH:mm"));
		boardArticleSearch2.fixBrokenSvByDefaultCharsets();
		boardArticleSearch2.setBaOpenDay(boardConfig2.isBcSupportOpenDay());
		boardArticleSearch2.setBaCommSelec(boardConfig2.isBcSupportCommSelec());
		boardArticleSearch2.setPageSize(totListSize2);
		
		int total2 = boardService.getBoardArticleListTotal(boardArticleSearch2);
		List<BoardArticle> list2 = boardService.getBoardArticleList(boardArticleSearch2);
		Paging paging2 = new Paging(list2, total2, boardArticleSearch2);
		model.addAttribute("paging2", paging2);

		
		//부서목록
		DepartmentSearch departmentSearch = new DepartmentSearch();
		departmentSearch.setPaging(false);
		departmentSearch.setDepUse(true);
		List<Department> departmentList = organizationService.getDepartmentList(departmentSearch);
		model.addAttribute("departmentList", departmentList);
				
		//코드데이터
		this.loadCodeData(model, null, boardConfig2, null, null);	
		
		//콘트롤러에서 리다이렉트 되었을 경우 flashMap에 담겨온게 있는지 확인
		Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);
		if( flashMap != null && flashMap.get("messageCode") != null ){
			model.addAttribute("messageCode", flashMap.get("messageCode"));
		}
		
		
		
		
		
		
		//=========================================================================================
		// 4. 창업팀 공지사항
		//=========================================================================================

		//---------------------------
		// 게시판 조회
		//---------------------------
		BoardConfig boardConfigSearch3 = new BoardConfig();
		BoardArticleSearch boardArticleSearch3 = new BoardArticleSearch();
		boardConfigSearch3.setBcId("startup_news");
		boardConfigSearch3.setSitePrefix(currentSite.getSitePrefix());
		BoardConfig boardConfig3 = boardService.getBoardConfig(boardConfigSearch3);
		
		if( boardConfig3 == null ){
			try {
				throw new AsaproNotFoundException("게시판이 존재하지 않습니다.");
			} catch (AsaproNotFoundException e) {
				LOGGER.error("[AsaproNotFoundException] Try/Catch... : "+ e.getMessage());
			}
		}
		if( !boardConfig3.isBcUse() ){
			try {
				throw new AsaproNotFoundException("사용하지 않는 게시판입니다.");
			} catch (AsaproNotFoundException e) {
				LOGGER.error("[AsaproNotFoundException] Try/Catch... : "+ e.getMessage());
			}
		}
		model.addAttribute("seo", new SEO(boardConfig3.getBcName(), "board.list", boardConfig3.getBcName()));
		model.addAttribute("boardConfig3", boardConfig3);
		
		int totListSize3 = 5;

		//---------------------------
		// 공지글 - 1페이지거나 게시판 설정에 따라서 출력 on/off
		//---------------------------
		if( boardArticleSearch3.getCp() == 1 || boardConfig3.isBcNoticeEveryPage() ){
			
			BoardArticleSearch noticeArticleSearch3 = new BoardArticleSearch();
			noticeArticleSearch3.setSitePrefix(currentSite.getSitePrefix());
			noticeArticleSearch3.setDefaultSort("BA_REGDATE", "DESC");
			noticeArticleSearch3.setBcId("startup_news");
			noticeArticleSearch3.setStartDate(boardArticleSearch3.getStartDate());
			noticeArticleSearch3.setEndDate(boardArticleSearch3.getEndDate());
			noticeArticleSearch3.setBaCommSelec(boardArticleSearch3.getBaCommSelec());
			noticeArticleSearch3.setBaComSelCat(boardArticleSearch3.getBaComSelCat());
			noticeArticleSearch3.setBaMainSelec(boardArticleSearch3.getBaMainSelec());
			noticeArticleSearch3.setBaOpenDay(boardArticleSearch3.getBaOpenDay());
			noticeArticleSearch3.setBaCategory1(boardArticleSearch3.getBaCategory1());
			noticeArticleSearch3.setBaCategory2(boardArticleSearch3.getBaCategory2());
			noticeArticleSearch3.setBaCategory3(boardArticleSearch3.getBaCategory3());
			noticeArticleSearch3.setBaNotice(true);
			noticeArticleSearch3.setBaUse(true);
			noticeArticleSearch3.setNow(AsaproUtils.getFormattedDate(new Date()));
			noticeArticleSearch3.setNowTime(AsaproUtils.getFormattedDate(new Date(),"HH:mm"));
			noticeArticleSearch3.setPageSize(totListSize3);
			List<BoardArticle> noticeList3 = boardService.getBoardArticleList(noticeArticleSearch3);
			
			model.addAttribute("noticeList3", noticeList3);
			totListSize3 = totListSize3 - noticeList3.size();
		}
				
		
		//---------------------------
		// 게시글
		//---------------------------
		boardArticleSearch3.setBcId("startup_news");
		boardArticleSearch3.setSitePrefix(currentSite.getSitePrefix());
		boardArticleSearch3.setDefaultSort("BA_REGDATE", "DESC");
		boardArticleSearch3.setBaNotice(false);
		boardArticleSearch3.setBaUse(true);
		boardArticleSearch3.setNow(AsaproUtils.getFormattedDate(new Date()));
		boardArticleSearch3.setNowTime(AsaproUtils.getFormattedDate(new Date(),"HH:mm"));
		boardArticleSearch3.fixBrokenSvByDefaultCharsets();
		boardArticleSearch3.setBaOpenDay(boardConfig3.isBcSupportOpenDay());
		boardArticleSearch3.setBaCommSelec(boardConfig3.isBcSupportCommSelec());
		boardArticleSearch3.setPageSize(totListSize3);
		
		int total3 = boardService.getBoardArticleListTotal(boardArticleSearch3);
		List<BoardArticle> list3 = boardService.getBoardArticleList(boardArticleSearch3);
		Paging paging3 = new Paging(list3, total3, boardArticleSearch3);
		model.addAttribute("paging3", paging3);

		//코드데이터
		this.loadCodeData(model, null, null, boardConfig3, null);
				
		//창업팀 목록
		StartHpMngrSearch startHpMngrSearch = new StartHpMngrSearch();
		startHpMngrSearch.setPaging(false);
		List<StartHpMngr> teamList = startHpMngrService.getStartHpMngrList(startHpMngrSearch);
		model.addAttribute("teamList", teamList);
				
		
		
		/*
		Map<String, Object> dashboradAlert = new HashMap<String, Object>(); 
		
		//최근로그인기록
		WatchDogLogSearch watchDogLogSearch = new WatchDogLogSearch();
		watchDogLogSearch.setSc("METHOD_NAME");
		watchDogLogSearch.setSv("loginFormPost");
		List<WatchDogLog> loginLogList = watchDogService.getWatchDogLogList(watchDogLogSearch);
		model.addAttribute("loginLogList", loginLogList);
		
		// ===== 시작 : 알림 ==========
		//대기중 댓글
		CommentSearch tabTotalSearch = new CommentSearch();
		tabTotalSearch.setSitePrefix(currentSite.getSitePrefix());
		tabTotalSearch.setIgnoreCmStatus(false);
		tabTotalSearch.setCmStatus("WAITING");
		int commentWatingTotal = commentService.getCommentListTotal(tabTotalSearch);
		if( commentWatingTotal > 0 ){
			dashboradAlert.put("commentWatingTotal", commentWatingTotal);
		}
		//이메일인증 대기중 회원
		MemberSearch signUpWatingMemberSearch = new MemberSearch();
		signUpWatingMemberSearch.setMemberSignUpKeyChecked("false");
		int signUpWaitingMemberTotal = memberService.getMemberListTotal(signUpWatingMemberSearch);
		if( signUpWaitingMemberTotal > 0){
			dashboradAlert.put("signUpWaitingMemberTotal", signUpWaitingMemberTotal);
		}
		model.addAttribute("dashboardAlert", dashboradAlert);
		// ===== 끝 : 알림 ==========
		
		//대시보드 방문자 통계
		StatSearch statSearch = new StatSearch();
		statSearch.setSitePrefix(currentSite.getSitePrefix());
		statSearch.setDashboard(true);
		model.addAttribute("statVisit", statisticsService.getCurrentStatVisitMap(statSearch));
		
		Calendar weekBefore = Calendar.getInstance();
		weekBefore.add(Calendar.DAY_OF_MONTH, -7);
		//최근 가입회원
		model.addAttribute("latestSignUpMembers", memberService.getLatestSignUpMemberList(weekBefore.getTime()));
		//최근 탈퇴한 회원
		model.addAttribute("latestDeleteMembers", memberService.getLatestDeleteMemberList(weekBefore.getTime()));
		*/
		
		
		
		return "asapro/admin/dashboard/dashboard";
	}
	
}
