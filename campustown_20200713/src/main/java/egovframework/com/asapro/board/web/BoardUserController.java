/**
 * 
 */
package egovframework.com.asapro.board.web;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document.OutputSettings;
import org.jsoup.safety.Whitelist;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.FlashMap;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

import egovframework.com.asapro.board.service.BoardArticle;
import egovframework.com.asapro.board.service.BoardArticleSearch;
import egovframework.com.asapro.board.service.BoardConfig;
import egovframework.com.asapro.board.service.BoardService;
import egovframework.com.asapro.code.service.Code;
import egovframework.com.asapro.code.service.CodeService;
import egovframework.com.asapro.config.service.Config;
import egovframework.com.asapro.config.service.ConfigService;
import egovframework.com.asapro.content_statis.service.ContentStatisDay;
import egovframework.com.asapro.content_statis.service.ContentStatisService;
import egovframework.com.asapro.fileinfo.service.FileInfoUploadResult;
import egovframework.com.asapro.member.user.service.UserMember;
import egovframework.com.asapro.organization.service.Department;
import egovframework.com.asapro.organization.service.DepartmentSearch;
import egovframework.com.asapro.organization.service.Organization;
import egovframework.com.asapro.organization.service.OrganizationSearch;
import egovframework.com.asapro.organization.service.OrganizationService;
//import egovframework.com.asapro.member.service.Member;
import egovframework.com.asapro.site.service.Site;
import egovframework.com.asapro.support.Constant;
import egovframework.com.asapro.support.SEO;
import egovframework.com.asapro.support.ServerMessage;
import egovframework.com.asapro.support.annotation.CurrentSite;
import egovframework.com.asapro.support.annotation.CurrentUser;
import egovframework.com.asapro.support.exception.AsaproException;
import egovframework.com.asapro.support.exception.AsaproNotFoundException;
import egovframework.com.asapro.support.pagination.Paging;
import egovframework.com.asapro.support.util.AsaproUtils;
import egovframework.com.campustown.startHpMngr.service.StartHpMngr;
import egovframework.com.campustown.startHpMngr.service.StartHpMngrSearch;
import egovframework.com.campustown.startHpMngr.service.StartHpMngrService;
import egovframework.rte.fdl.cryptography.EgovPasswordEncoder;

/**
 * 게시판 사용자 컨트롤러
 * @author yckim
 * @since 2018. 6. 20.
 */
@Controller
public class BoardUserController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(BoardUserController.class);
	
	@Autowired
	private BoardService boardService;
	
	@Autowired
	private CodeService codeService;
	
	@Autowired
	private ConfigService configService;
	
	@Autowired
	private HttpServletRequest request;
	
	@Autowired
	private EgovPasswordEncoder egovPasswordEncoder;
	
	@Autowired
	private BoardArticleValidator boardArticleValidator;
	
	@Autowired
	private OrganizationService organizationService;
	
	@Autowired
	private StartHpMngrService startHpMngrService;
	
	@Autowired
	private ContentStatisService contentStatisService;
	
	/**
	 * 기관목록 로드
	 * @param model
	 */
	private void loadOrganization(Model model){
		//사용중인 기관목록
		OrganizationSearch organizationSearch = new OrganizationSearch();
		organizationSearch.setPaging(false);
		organizationSearch.setOrgUse(true);
		organizationSearch.setDefaultSort("ORG_NAME", "ASC");
		List<Organization> organizationList = organizationService.getOrganizationList(organizationSearch);
		model.addAttribute("organizationList", organizationList);
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
		model.addAttribute("departmentList", departmentList);
	}
	
	
	/**
	 * 템플릿뷰파일명 반환 중복코드 처리
	 * @param fileName
	 * @return
	 */
	private String getBoardTemplateName(String fileName){
		String template = StringUtils.removeEndIgnoreCase(fileName, ".jsp");
		template = StringUtils.removeStartIgnoreCase(template, "/");
		if( StringUtils.isNotBlank(template) ){
			LOGGER.info("[ASAPRO] BoardUserController template : {}", template);
		}
		return AsaproUtils.getThemePath(request) + "board/" + template;
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
	 * 게시물 본문에 허용할 태그만 추가된 jsoup whitelist
	 * @return
	 */
	private Whitelist getJsoupWhiteListForBoardArticle() {
		Config siteConfig = configService.getConfig("site");
		Whitelist whitelist = Whitelist.none();
		//관리자 설정에서 사용자단에서 입력시 허용태그가 있으면 추가해줌
		if( StringUtils.isNotBlank(siteConfig.getOption("user_form_allow_tag")) ){
			String[] temp = siteConfig.getOption("user_form_allow_tag").split(",");
			for( String allowTag : temp ){
				allowTag = allowTag.toLowerCase();
				if( "a".equals(allowTag) ){
					whitelist = whitelist.addTags("a").addAttributes("a", "href").addEnforcedAttribute("a", "rel", "nofollow").addEnforcedAttribute("a", "target", "_blank");
				} else {
					whitelist.addTags(allowTag);
				}
			}
		}

		return whitelist;
	}
	
//=========================================================================================================================	
	
	/**
	 * 게시물 목록을 반환한다.
	 * @param model
	 * @param bcId
	 * @param boardArticleSearch
	 * @return
	 * @throws ParseException 
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value={Constant.APP_PATH + Constant.SITE_ID_PATH + "/board/{bcId}", Constant.APP_PATH + Constant.SITE_ID_PATH + "/board/{bcId}/list"}, method=RequestMethod.GET)
	public String boardArticleListGet(Model model, @CurrentSite Site currentSite, @ModelAttribute("boardArticleSearch") BoardArticleSearch boardArticleSearch) throws ParseException{
		//---------------------------
		// 게시판 조회
		//---------------------------
		BoardConfig boardConfigSearch = new BoardConfig();
		boardConfigSearch.setBcId(boardArticleSearch.getBcId());
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
		
		// 명예의 전당의 경우 예외 처리
		if("fame".equals(boardConfig.getBcId()) ){
			if(null == boardArticleSearch.getBaCategory1() || ("").equals(boardArticleSearch.getBaCategory1())){
				//boardConfig.getBcCategory1()
				if( StringUtils.isNotBlank(boardConfig.getBcCategory1()) ){
					List<Code> codeList = codeService.getCodeList(boardConfig.getBcCategory1());
					boardArticleSearch.setBaCategory1(codeList.get(0).getCodeId());
				}
			}
		}
		
		
		//---------------------------
		// 게시글
		//---------------------------
		boardArticleSearch.setSitePrefix(currentSite.getSitePrefix());
		boardArticleSearch.setDefaultSort("BA_REGDATE", "DESC");
		boardArticleSearch.setBaNotice(false);
		boardArticleSearch.setBaUse(true);
		boardArticleSearch.setNow(AsaproUtils.getFormattedDate(new Date()));
		boardArticleSearch.setNowTime(AsaproUtils.getFormattedDate(new Date(),"HH:mm"));
		boardArticleSearch.fixBrokenSvByDefaultCharsets();
		boardArticleSearch.setBaOpenDay(boardConfig.isBcSupportOpenDay());
		boardArticleSearch.setBaCommSelec(boardConfig.isBcSupportCommSelec());
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
		
		//팀목록
/*		TeamSearch teamSearch = new TeamSearch();
		teamSearch.setPaging(false);
		teamSearch.setTeamUse(true);
		List<Team> teamList = organizationService.getTeamList(teamSearch);
		model.addAttribute("teamList", teamList);
		*/
		
		//창업팀 목록
		StartHpMngrSearch startHpMngrSearch = new StartHpMngrSearch();
		startHpMngrSearch.setPaging(false);
		List<StartHpMngr> teamList = startHpMngrService.getStartHpMngrList(startHpMngrSearch);
		model.addAttribute("teamList", teamList);
		
		//카테고리1목록의 코드별수량을 담아 뷰에서 사용
		//지식톡인경우면 예외 처리
		if("idea_talk".equals(boardConfig.getBcId()) ){
			
			List<Code> tempCate1CodeList = (List<Code>)model.asMap().get("bcCategory1CodeList");
			if(tempCate1CodeList != null && tempCate1CodeList.size() > 0 ){
				BoardArticleSearch articleCntSearch = new BoardArticleSearch();
				articleCntSearch.setSitePrefix(currentSite.getSitePrefix());
				articleCntSearch.setDefaultSort("BA_REGDATE", "DESC");
				articleCntSearch.setBcId(boardArticleSearch.getBcId());
				articleCntSearch.setStartDate(boardArticleSearch.getStartDate());
				articleCntSearch.setEndDate(boardArticleSearch.getEndDate());
				articleCntSearch.setBaCommSelec(boardArticleSearch.getBaCommSelec());
				articleCntSearch.setBaComSelCat(boardArticleSearch.getBaComSelCat());
				articleCntSearch.setBaMainSelec(boardArticleSearch.getBaMainSelec());
				articleCntSearch.setBaOpenDay(boardArticleSearch.getBaOpenDay());
				articleCntSearch.setBaNotice(null);
				articleCntSearch.setBaUse(true);
				articleCntSearch.setNow(AsaproUtils.getFormattedDate(new Date()));
				articleCntSearch.setNowTime(AsaproUtils.getFormattedDate(new Date(),"HH:mm"));
				
				for (Code searchCode : tempCate1CodeList) {
					int cnt = 0;
					articleCntSearch.setBaCategory1(searchCode.getCodeId());
					cnt = boardService.getBoardArticleListTotal(articleCntSearch);
					searchCode.setCnt(cnt);
				}
			}
		}
		
		
		//콘트롤러에서 리다이렉트 되었을 경우 flashMap에 담겨온게 있는지 확인
		Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);
		if( flashMap != null && flashMap.get("messageCode") != null ){
			model.addAttribute("messageCode", flashMap.get("messageCode"));
		}
		
		//템플릿 파일 적용
		/*if("inner".equals(boardArticleSearch.getListType()) ){
			return AsaproUtils.getThemePath(request) + "board/" + boardConfig.getBcType() + "/list_inner";
		
		}else{*/
			if( StringUtils.isNotBlank(boardConfig.getBcListFile()) ){
				return this.getBoardTemplateName(boardConfig.getBcListFile());
			}else{
				return AsaproUtils.getThemePath(request) + "board/" + boardConfig.getBcType() + "/list";
			}
		/*}*/
		
	}
	
	/**
	 * 게시물 입력 폼 뷰를 반환한다.
	 * @param model
	 * @param bcId
	 * @param boardArticleForm
	 * @return 게시물 입력 폼 뷰
	 */
	@RequestMapping(value=Constant.APP_PATH + Constant.SITE_ID_PATH + "/board/{bcId}/new", method=RequestMethod.GET)
	public String boardInsertGet(Model model, @CurrentSite Site currentSite, @CurrentUser UserMember currentUser, @ModelAttribute("boardArticleForm") BoardArticle boardArticleForm){
		
		BoardConfig boardConfigSearch = new BoardConfig();
		boardConfigSearch.setBcId(boardArticleForm.getBcId());
		boardConfigSearch.setSitePrefix(currentSite.getSitePrefix());
		BoardConfig boardConfig = boardService.getBoardConfig(boardConfigSearch);
		model.addAttribute("boardConfig", boardConfig);
		
		boardArticleForm.setSitePrefix(currentSite.getSitePrefix());
		boardArticleForm.setBoardConfig(boardConfig);
		
		model.addAttribute("baFileInfosSize", boardArticleForm.getBaFileInfos().size());
		model.addAttribute("boardArticleForm", boardArticleForm);
		model.addAttribute("formMode", "INSERT");
		model.addAttribute("actionUrl", AsaproUtils.getAppPath(currentSite) + "/board/" + boardConfig.getBcId() + "/new");
		model.addAttribute("seo", new SEO(boardConfig.getBcName(), "board.new", boardConfig.getBcName()));
		
		//코드데이터
		this.loadCodeData(model, boardConfig);
		List<Code> bcNuriCodeList = codeService.getCodeList("NURI_TYPE");
		model.addAttribute("bcNuriCodeList", bcNuriCodeList);
		
		//템플릿 파일 적용
		if( StringUtils.isNotBlank(boardConfig.getBcFormFile()) ){
			return this.getBoardTemplateName(boardConfig.getBcFormFile());
		}
				
		return AsaproUtils.getThemePath(request) + "board/" + boardConfig.getBcType() + "/form";
	}
	
	
	/**
	 * 게시물을 입력한다.
	 * @param model
	 * @param currentUser
	 * @param bcId
	 * @param boardArticleForm
	 * @param bindingResult
	 * @return 입력결과 뷰
	 * @throws IOException 
	 */
	@RequestMapping(value=Constant.APP_PATH + Constant.SITE_ID_PATH + "/board/{bcId}/new", method=RequestMethod.POST)
	public String boardInsertPost(Model model, RedirectAttributes redirectAttributes, @CurrentSite Site currentSite, @CurrentUser UserMember currentUser, @ModelAttribute("boardArticleForm") BoardArticle boardArticleForm, BindingResult bindingResult) throws IOException{
		
		BoardConfig boardConfigSearch = new BoardConfig();
		boardConfigSearch.setSitePrefix(currentSite.getSitePrefix());
		boardConfigSearch.setBcId(boardArticleForm.getBcId());
		BoardConfig boardConfig = boardService.getBoardConfig(boardConfigSearch);
		
		boardArticleForm.setSitePrefix(currentSite.getSitePrefix());
		boardArticleForm.setBoardConfig(boardConfig);
		boardArticleForm.setUserMember(currentUser);
		boardArticleForm.setMemId(currentUser.getUserId());
		boardArticleForm.setDepId(currentUser.getUserDepartment());
		boardArticleForm.setTeamId(currentUser.getUserTeam());
		boardArticleForm.setBaUpdaterId(currentUser.getUserId());
		
		boardArticleValidator.validate(boardArticleForm, bindingResult, "INSERT");
		if( bindingResult.hasErrors() ){
			
			model.addAttribute("baFileInfosSize", boardArticleForm.getBaFileInfos().size());
			model.addAttribute("boardConfig", boardConfig);
			model.addAttribute("formMode", "INSERT");
			model.addAttribute("actionUrl", AsaproUtils.getAppPath(currentSite) + "/board/" + boardConfig.getBcId() + "/new");
			model.addAttribute("seo", new SEO(boardConfig.getBcName(), "board.new", boardConfig.getBcName()));
			
			//코드데이터
			this.loadCodeData(model, boardConfig);
			List<Code> bcNuriCodeList = codeService.getCodeList("NURI_TYPE");
			model.addAttribute("bcNuriCodeList", bcNuriCodeList);
			
			//템플릿 파일 적용
			if( StringUtils.isNotBlank(boardConfig.getBcFormFile()) ){
				return this.getBoardTemplateName(boardConfig.getBcFormFile());
			}
				
			return AsaproUtils.getThemePath(request) + "board/" + boardConfig.getBcType() + "/form";
			
		} else {
			//게시물 필드값 정리 - XSS 필터링 포함
			//제목은 텍스트만 받음
			boardArticleForm.setBaTitle(Jsoup.clean(boardArticleForm.getBaTitle(), Whitelist.none()));
			Whitelist whitelist = this.getJsoupWhiteListForBoardArticle();
			
			//본문 html 세팅
			//사용자페이지는 테그 제거한다.
			boardArticleForm.setBaContentHtml( Jsoup.clean(boardArticleForm.getBaContentHtml(), "", whitelist , new OutputSettings().prettyPrint(false) ) );
			
			//본문 plain text 세팅
			boardArticleForm.setBaContentPlainFromBaContentHtml();
			
			//oracle 에서 한글은 utf-8일 경우 3바이트 이므로 4000마이트를 자를 수 없다
			//java 에서는 한글을 2바이트로 계산하기때문에 어쩔 수 없이 일괄적으로 한글 최대 바이트로 자름
			//한글을 체크해서 한글만 3바이트로 계산해서 할 수도 있지만 그정까지 정확히 잘라야 할 필요를 못느낌
			if(StringUtils.isNotBlank(boardArticleForm.getBaContentPlain()) ){
				byte[] plain = boardArticleForm.getBaContentPlain().getBytes();
				if(plain.length > 1340 ){
					boardArticleForm.setBaContentPlain(new String(plain, 0, 1340));
				}
			}
			
			if( StringUtils.isNotBlank(boardArticleForm.getBaGuestPassword()) ){
				boardArticleForm.setBaGuestPassword(egovPasswordEncoder.encryptPassword(boardArticleForm.getBaGuestPassword()));
			}
			if( StringUtils.isNotBlank(boardArticleForm.getBaSecretPassword()) ){
				boardArticleForm.setBaSecretPassword(egovPasswordEncoder.encryptPassword(boardArticleForm.getBaSecretPassword()));
			}
			boardArticleForm.setBaIp(AsaproUtils.getRempoteIp(request));
			boardArticleForm.setBaRegDate(new Date());
			boardArticleForm.setBaLastModified(new Date());
			
			//게시물 저장
			Map<String, FileInfoUploadResult> fileInfoUploadResultMap = boardService.insertBoardArticle(boardArticleForm);
			
			//업로드 에러 있으면 바로 수정화면으로 전환 - 에러난 파일 제외 글과 다른 파일들은 저장된 상태임.
			if(fileInfoUploadResultMap.get("thumbFileInfoUploadResult").hasErrors() || fileInfoUploadResultMap.get("fileInfoUploadResult").hasErrors() ){
				FlashMap flashMap = RequestContextUtils.getOutputFlashMap(request);
				flashMap.put("board.article." + boardArticleForm.getBaId() + ".author", Boolean.TRUE);
				
				if(fileInfoUploadResultMap.get("thumbFileInfoUploadResult").hasErrors() ){
					flashMap.put("thumbFileInfoUploadResult", fileInfoUploadResultMap.get("thumbFileInfoUploadResult"));
				}
				if(fileInfoUploadResultMap.get("fileInfoUploadResult").hasErrors() ){
					flashMap.put("fileInfoUploadResult", fileInfoUploadResultMap.get("fileInfoUploadResult"));
				}
				return "redirect:" + AsaproUtils.getAppPath(currentSite, true) +  "/board/" + boardArticleForm.getBcId() + "/" + boardArticleForm.getBaId() + "/edit";
			}
			
			redirectAttributes.addFlashAttribute("inserted", true);
			return "redirect:" + AsaproUtils.getAppPath(currentSite, true) + "/board/" + boardArticleForm.getBcId() + "/list";
		}
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
	@RequestMapping(value=Constant.APP_PATH + Constant.SITE_ID_PATH + "/board/{bcId}/{baId}", method=RequestMethod.GET)
	public String boardArticleGet(HttpServletResponse response, Model model, @CurrentSite Site currentSite, @CurrentUser UserMember currentUser, @PathVariable String bcId, @PathVariable Integer baId, @RequestParam(value="viewType", required = false) String viewType) throws IllegalAccessException, InvocationTargetException{
		
		BoardConfig bcIdSearch = new BoardConfig();
		bcIdSearch.setSitePrefix(currentSite.getSitePrefix());
		bcIdSearch.setBcId(bcId);
		BoardConfig boardConfig = boardService.getBoardConfig(bcIdSearch);
		
		model.addAttribute("boardConfig", boardConfig);
		
		BoardArticle baIdSearch = new BoardArticle();
		baIdSearch.setSitePrefix(currentSite.getSitePrefix());
		baIdSearch.setBaId(baId);
		
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
				baReadArticle.setBcId(bcId);
				//조회수 + 1
				int result = boardService.updateBoardArticleHit(baReadArticle);
				if( result > 0 ){
					
					//========= 추천통계자료 등록 ===================
					contentStatisDay(baReadArticle, currentSite, "hit");
					//========= 추천통계자료 등록 ===================
					
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
			baReadArticle.setBcId(bcId);
			//조회수 + 1
			boardService.updateBoardArticleHit(baReadArticle);
			
			//========= 추천통계자료 등록 ===================
			contentStatisDay(baReadArticle, currentSite, "hit");
			//========= 추천통계자료 등록 ===================
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
		
		/**
		 * 창업 제품 소개 및 장터 게시판 예외처리
		 */
		if("product".equals(boardArticle.getBcId()) ){
			//부서목록
			DepartmentSearch departmentSearch = new DepartmentSearch();
			departmentSearch.setPaging(false);
			departmentSearch.setDepUse(true);
			departmentSearch.setOrgId("university");
			List<Department> departmentList = organizationService.getDepartmentList(departmentSearch);
			model.addAttribute("departmentList", departmentList);
			
			//창업팀 목록
			if(StringUtils.isNotBlank(boardArticle.getBaCategory2()) ){
				StartHpMngrSearch startHpMngrSearch = new StartHpMngrSearch();
				startHpMngrSearch.setPaging(false);
				startHpMngrSearch.setUnivId(boardArticle.getBaCategory2());
				List<StartHpMngr> teamList = startHpMngrService.getStartHpMngrList(startHpMngrSearch);
				model.addAttribute("teamList", teamList);
			}
		}
		
		//목록 돌아가기 검색VO
		BoardArticleSearch backListSearch = new BoardArticleSearch();
		BeanUtils.populate(backListSearch, request.getParameterMap());
		backListSearch.fixBrokenSvByDefaultCharsets();
		model.addAttribute("backListSearch", backListSearch);
		
		
		//템플릿 파일 적용
		if(StringUtils.isNotBlank(viewType) && "layer".equals(viewType) ){
			return AsaproUtils.getThemePath(request) + "board/" + boardConfig.getBcType() + "/view_layer";
		
		}else{
			if( StringUtils.isNotBlank(boardConfig.getBcViewFile()) ){
				return this.getBoardTemplateName(boardConfig.getBcViewFile());
			}else{
				return AsaproUtils.getThemePath(request) + "board/" + boardConfig.getBcType() + "/view";
			}
		}
	}
	
	/**
	 * 게시물 수정뷰를 반환한다.
	 * @param model
	 * @param currentUser
	 * @param bcId
	 * @param baId
	 * @return 게시물 수정 뷰
	 */
	@RequestMapping(value=Constant.APP_PATH + Constant.SITE_ID_PATH + "/board/{bcId}/{baId}/edit", method=RequestMethod.GET)
	public String updateBoardArticleGet(Model model, @CurrentSite Site currentSite, @CurrentUser UserMember currentUser, @PathVariable String bcId, @PathVariable Integer baId){
		
		BoardConfig boardConfigSearch = new BoardConfig();
		boardConfigSearch.setSitePrefix(currentSite.getSitePrefix());
		boardConfigSearch.setBcId(bcId);
		BoardConfig boardConfig = boardService.getBoardConfig(boardConfigSearch);
		model.addAttribute("boardConfig", boardConfig);
		
		BoardArticle baIdSearch = new BoardArticle();
		baIdSearch.setBaId(baId);
		baIdSearch.setSitePrefix(currentSite.getSitePrefix());
		BoardArticle boardArticleForm = boardService.getBoardArticle(baIdSearch);
		boardArticleForm.setSitePrefix(currentSite.getSitePrefix());
		boardArticleForm.setBoardConfig(boardConfig);

		model.addAttribute("boardArticleForm", boardArticleForm);
		model.addAttribute("baFileInfosSize", boardArticleForm.getBaFileInfos().size());
		model.addAttribute("formMode", "UPDATE");
		model.addAttribute("actionUrl", AsaproUtils.getAppPath(currentSite) + "/board/" + boardConfig.getBcId() + "/" + boardArticleForm.getBaId() + "/edit");
		model.addAttribute("seo", new SEO(boardConfig.getBcName(), "board.update", boardConfig.getBcName()));
		
		//파일 업로드 에러에 대한 정보가 flashMap에 들어 있는지 확인
		Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);
		if( flashMap != null &&  flashMap.get("fileInfoUploadResult") != null ){
			FileInfoUploadResult fileInfoUploadResult = (FileInfoUploadResult) flashMap.get("fileInfoUploadResult");
			model.addAttribute("fileInfoUploadResult", fileInfoUploadResult);
		}
		
		//코드데이터
		this.loadCodeData(model, boardConfig);
		List<Code> bcNuriCodeList = codeService.getCodeList("NURI_TYPE");
		model.addAttribute("bcNuriCodeList", bcNuriCodeList);
				
		//템플릿 파일 적용
		if( StringUtils.isNotBlank(boardConfig.getBcFormFile()) ){
			return this.getBoardTemplateName(boardConfig.getBcFormFile());
		}
		
		return AsaproUtils.getThemePath(request) + "board/" + boardConfig.getBcType() + "/form";
		
	}
	
	/**
	 * 게시물을 수정한다.
	 * @param model
	 * @param currentUser
	 * @param bcId
	 * @param baId
	 * @param boardArticleForm
	 * @param bindingResult
	 * @return 수정결과뷰
	 * @throws IOException 
	 * @throws EcmsException 
	 */
	@RequestMapping(value=Constant.APP_PATH + Constant.SITE_ID_PATH + "/board/{bcId}/{baId}/edit", method=RequestMethod.POST)
	public String updateBoardArticlePost(Model model, RedirectAttributes redirectAttributes, @CurrentSite Site currentSite, @CurrentUser UserMember currentUser, 
			@ModelAttribute("boardArticleForm") BoardArticle boardArticleForm, BindingResult bindingResult) throws AsaproException, IOException{
		
		BoardConfig bcIdSearch = new BoardConfig();
		bcIdSearch.setSitePrefix(currentSite.getSitePrefix());
		bcIdSearch.setBcId(boardArticleForm.getBcId());
		BoardConfig boardConfig = boardService.getBoardConfig(bcIdSearch);
		
		boardArticleForm.setSitePrefix(currentSite.getSitePrefix());
		boardArticleForm.setUserMember(currentUser);
		boardArticleForm.setBaUpdaterId(currentUser.getUserId());
		boardArticleForm.setBoardConfig(boardConfig);
		
		boardArticleValidator.validate(boardArticleForm, bindingResult, "UPDATE");
		
		BoardArticle fromDB = boardService.getBoardArticle(boardArticleForm);

		//비회원 글인경우 수정시 비밀번호 확인 후 처리
		if(bindingResult.getFieldError("baGuestPassword") == null){
			if(!boardService.isPasswordMatch(fromDB, "edit", boardArticleForm.getBaGuestPassword()) ){
				bindingResult.addError(new FieldError("boardArticleForm", "baGuestPassword", "본인확인 비밀번호가 일치하지 않습니다."));
			}
		}
		
		if( bindingResult.hasErrors() ){
			
			model.addAttribute("boardConfig", boardConfig);
			model.addAttribute("baFileInfosSize", boardArticleForm.getBaFileInfos().size());
			model.addAttribute("formMode", "UPDATE");
			model.addAttribute("actionUrl", AsaproUtils.getAppPath(currentSite) + "/board/" + boardConfig.getBcId() + "/" + boardArticleForm.getBaId() + "/edit");
			model.addAttribute("seo", new SEO(boardConfig.getBcName(), "board.update", boardConfig.getBcName()));
			
			//코드데이터
			this.loadCodeData(model, boardConfig);
			List<Code> bcNuriCodeList = codeService.getCodeList("NURI_TYPE");
			model.addAttribute("bcNuriCodeList", bcNuriCodeList);
			
			//템플릿 파일 적용
			if( StringUtils.isNotBlank(boardConfig.getBcFormFile()) ){
				return this.getBoardTemplateName(boardConfig.getBcFormFile());
			}
					
			return AsaproUtils.getThemePath(request) + "board/" + boardConfig.getBcType() + "/form";
			
		} else {
			//게시물 필드값 정리 - XSS 필터링 포함
			//제목은 텍스트만 받음
			boardArticleForm.setBaTitle(Jsoup.clean(boardArticleForm.getBaTitle(), Whitelist.none()));
			//본문은 모든 태거 제거, a 태그만 허용
			Whitelist whitelist = this.getJsoupWhiteListForBoardArticle();
			
			//본문 html 세팅
			boardArticleForm.setBaContentHtml( Jsoup.clean(boardArticleForm.getBaContentHtml(), "", whitelist , new OutputSettings().prettyPrint(false) ) );
			
			//본문 plain terxt 세팅
			boardArticleForm.setBaContentPlainFromBaContentHtml();
			//utf-8 캐릭터 셋 환경을 고려, PreparedStatement 사용고려
			//oracle 에서 한글은 utf-8일 경우 3바이트 이므로 4000마이트를 자를 수 없다
			//java 에서는 한글을 2바이트로 계산하기때문에 어쩔 수 없이 일괄적으로 한글 최대 바이트로 자름
			//한글을 체크해서 한글만 3바이트로 계산해서 할 수도 있지만 그정까지 정확히 잘라야 할 필요를 못느낌
			if(StringUtils.isNotBlank(boardArticleForm.getBaContentPlain()) ){
				byte[] plain = boardArticleForm.getBaContentPlain().getBytes();
				if(plain.length > 1340 ){
					boardArticleForm.setBaContentPlain(new String(plain, 0, 1340));
				}
			}
			
			
			//원래 비회원 글이었을 경우만 새 비밀번호 들어오면 바꿔주도록
			if( fromDB.isGuestArticle() ){
				if( StringUtils.isNotBlank(boardArticleForm.getBaGuestPassword()) ){
					boardArticleForm.setBaGuestPassword(egovPasswordEncoder.encryptPassword(boardArticleForm.getBaGuestPassword()));
				} else {
					boardArticleForm.setBaGuestPassword(fromDB.getBaGuestPassword());
				}
			} 
			
			//원래 비밀글이었을 경우
			if( fromDB.getBaSecret() && StringUtils.isNotBlank(fromDB.getBaSecretPassword()) ){
				if( boardArticleForm.getBaSecret() ){
					if( StringUtils.isNotBlank(boardArticleForm.getBaSecretPassword()) ){
						//비밀번호 변경
						boardArticleForm.setBaSecretPassword(egovPasswordEncoder.encryptPassword(boardArticleForm.getBaSecretPassword()));
					} else {
						boardArticleForm.setBaSecretPassword(fromDB.getBaGuestPassword());
					}
				} else {
					//비밀글 -> 공개글전환
					boardArticleForm.setBaSecretPassword("");
				}
			} 
			//원래 비밀글 아니었던 경우
			else {
				if( boardArticleForm.getBaSecret() && StringUtils.isNotBlank(boardArticleForm.getBaSecretPassword()) ){
					boardArticleForm.setBaSecretPassword(egovPasswordEncoder.encryptPassword(boardArticleForm.getBaSecretPassword()));
				}
			}
			
			//if( fromDB.isGuestArticle() ){
				//비회원글인 경우 원래 이름 유지되도록
				//boardArticleForm.setBaName(fromDB.getBaName());
			//}
			boardArticleForm.setBaIp(AsaproUtils.getRempoteIp(request));
			//boardArticleForm.setBaRegDate(new Date());
			boardArticleForm.setBaUpdaterId(currentUser.getUserId());
			boardArticleForm.setBaLastModified(new Date());
			
			//게시물 수정
			Map<String, FileInfoUploadResult> fileInfoUploadResultMap = boardService.updateBoardArticle(boardArticleForm);
			
			//업로드 에러 있으면 바로 수정화면으로 전환 - 에러난 파일 제외 글과 다른 파일들은 저장된 상태임.
			if(fileInfoUploadResultMap.get("thumbFileInfoUploadResult").hasErrors() || fileInfoUploadResultMap.get("fileInfoUploadResult").hasErrors() ){
				//파일업로드 에러 있을 경우 에러내용을 flashMap에 담아서 리다이렉트
				FlashMap flashMap = RequestContextUtils.getOutputFlashMap(request);
				if(fileInfoUploadResultMap.get("thumbFileInfoUploadResult").hasErrors() ){
					flashMap.put("thumbFileInfoUploadResult", fileInfoUploadResultMap.get("thumbFileInfoUploadResult"));
				}
				if(fileInfoUploadResultMap.get("fileInfoUploadResult").hasErrors() ){
					flashMap.put("fileInfoUploadResult", fileInfoUploadResultMap.get("fileInfoUploadResult"));
				}
				return "redirect:" + AsaproUtils.getAppPath(currentSite, true) +  "/board/" + boardArticleForm.getBcId() + "/" + boardArticleForm.getBaId() + "/edit";
			}
			
			redirectAttributes.addFlashAttribute("updated", true);
			return "redirect:" + AsaproUtils.getAppPath(currentSite, true) +  "/board/" + boardArticleForm.getBcId() + "/" + boardArticleForm.getBaId();
		}
	}
	
	/**
	 * 비밀번호 입력폼뷰를 반환한다.
	 * @param model
	 * @param currentUser
	 * @param bcId
	 * @param baId
	 * @param formMode
	 * @return 비밀번호 입력 폼 뷰
	 */
	@RequestMapping(value=Constant.APP_PATH + Constant.SITE_ID_PATH + "/board/{bcId}/{baId}/{formMode}/password", method=RequestMethod.GET)
	public String updateBoardArticlePasswordGet(Model model, @CurrentSite Site currentSite, @CurrentUser UserMember currentUser
			, @PathVariable(value="bcId") String bcId
			, @PathVariable(value="baId") Integer baId
			, @PathVariable(value="formMode") String formMode){

		BoardConfig bcIdSearch = new BoardConfig();
		bcIdSearch.setSitePrefix(currentSite.getSitePrefix());
		bcIdSearch.setBcId(bcId);
		BoardConfig boardConfig = boardService.getBoardConfig(bcIdSearch);
		
		BoardArticle baIdSearch = new BoardArticle();
		baIdSearch.setSitePrefix(currentSite.getSitePrefix());
		baIdSearch.setBaId(baId);
		BoardArticle boardArticle = boardService.getBoardArticle(baIdSearch);
		boardArticle.setSitePrefix(currentSite.getSitePrefix());
		boardArticle.setBoardConfig(boardConfig);
		
		model.addAttribute("boardConfig", boardConfig);
		model.addAttribute("boardArticle", boardArticle);
		model.addAttribute("formMode", formMode);
		model.addAttribute("seo", new SEO(boardArticle.getBaTitle() + " | " + boardConfig.getBcName(), "board.password", boardArticle.getBaTitle() + "::" + boardConfig.getBcName()));
		
		return AsaproUtils.getThemePath(request) + "board/password";
	}
	
	/**
	 * 비밀번호 입력값을 체크하고 결과뷰를 반환한다.
	 * @param model
	 * @param currentUser
	 * @param bcId
	 * @param baId
	 * @param formMode
	 * @return 결과뷰
	 * @throws EcmsException
	 * @throws IOException
	 */
	@RequestMapping(value=Constant.APP_PATH + Constant.SITE_ID_PATH + "/board/{bcId}/{baId}/{formMode}/password", method=RequestMethod.POST)
	public String updateBoardArticlePasswordPost(Model model, @CurrentSite Site currentSite, @CurrentUser UserMember currentUser
			, @PathVariable(value="bcId") String bcId
			, @PathVariable(value="baId") Integer baId
			, @PathVariable(value="formMode") String formMode) throws AsaproException, IOException{
		
		String password = StringUtils.defaultString(request.getParameter("password"), "");
		//게시판 관리자는 비밀번호 없이 삭제할 수 있다.
		Boolean isBoardManager = (Boolean) request.getAttribute("isBoardManager");
		
		BoardArticle baIdSearch = new BoardArticle();
		baIdSearch.setSitePrefix(currentSite.getSitePrefix());
		baIdSearch.setBaId(baId);
		if( isBoardManager || boardService.isPasswordMatch(baIdSearch, formMode, password) ){
			FlashMap flashMap = RequestContextUtils.getOutputFlashMap(request);
			if( "edit".equals(formMode) ){
				flashMap.put("board.article." + baId + ".author", Boolean.TRUE);
				return "redirect:" + AsaproUtils.getAppPath(currentSite, true) + "/board/" + bcId + "/" + baId + "/edit";
			} else if( "delete".equals(formMode) ){
				boardService.deleteBoardArticle(baIdSearch);
				//flashMap.put("messageCode", "board.article.deleted");
				flashMap.put("messageCode", "삭제되었습니다.");
				return "redirect:" + AsaproUtils.getAppPath(currentSite, true) + "/board/" + bcId + "/list";
			} else if( "secret".equals(formMode) ){
				flashMap.put("board.article." + baId + ".secret", Boolean.TRUE);
				return "redirect:" + AsaproUtils.getAppPath(currentSite, true) + "/board/" + bcId + "/" + baId;
			}
		} else {
			BoardConfig bcIdSearch = new BoardConfig();
			bcIdSearch.setSitePrefix(currentSite.getSitePrefix());
			bcIdSearch.setBcId(bcId);
			BoardConfig boardConfig = boardService.getBoardConfig(bcIdSearch);
			model.addAttribute("boardConfig", boardConfig);
			
			BoardArticle boardArticle = boardService.getBoardArticle(baIdSearch);
			boardArticle.setSitePrefix(currentSite.getSitePrefix());
			boardArticle.setBoardConfig(boardConfig);
			model.addAttribute("boardArticle", boardArticle);
			model.addAttribute("passwordNotMatched", "비밀번호가 일치하지 않습니다.");
			model.addAttribute("formMode", formMode);
			model.addAttribute("seo", new SEO(boardArticle.getBaTitle() + " | " + boardConfig.getBcName(), "board.password", boardArticle.getBaTitle() + "::" + boardConfig.getBcName()));
		}
		
		return AsaproUtils.getThemePath(request) + "board/password";
	}
	
	/**
	 * 사용자 회원 아이디와 글 작성자 아이디를 비교하여 삭제처리한다.
	 * @param model
	 * @param currentSite
	 * @param currentUser
	 * @param bcId
	 * @param baId
	 * @return
	 * @throws AsaproException
	 * @throws IOException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	@RequestMapping(value=Constant.APP_PATH + Constant.SITE_ID_PATH + "/board/{bcId}/{baId}/delete/byuser", method=RequestMethod.GET)
	public String deleteBoardArticleByUserIdGet(Model model, @CurrentSite Site currentSite, @CurrentUser UserMember currentUser
			, @PathVariable(value="bcId") String bcId
			, @PathVariable(value="baId") Integer baId ) throws AsaproException, IOException, IllegalAccessException, InvocationTargetException{
		
		//게시판 관리자는 비밀번호 없이 삭제할 수 있다.
		Boolean isBoardManager = (Boolean) request.getAttribute("isBoardManager");
		
		BoardConfig bcIdSearch = new BoardConfig();
		bcIdSearch.setSitePrefix(currentSite.getSitePrefix());
		bcIdSearch.setBcId(bcId);
		BoardConfig boardConfig = boardService.getBoardConfig(bcIdSearch);
		model.addAttribute("boardConfig", boardConfig);
		
		BoardArticle baIdSearch = new BoardArticle();
		baIdSearch.setSitePrefix(currentSite.getSitePrefix());
		baIdSearch.setBaId(baId);
		BoardArticle boardArticle = boardService.getBoardArticle(baIdSearch);
		if( isBoardManager || (boardArticle != null && StringUtils.isNotBlank(boardArticle.getMemId()) && currentUser.getUserId().equals(boardArticle.getMemId()) ) ){
			FlashMap flashMap = RequestContextUtils.getOutputFlashMap(request);
			boardService.deleteBoardArticle(baIdSearch);
			//flashMap.put("messageCode", "board.article.deleted");
			flashMap.put("messageCode", "삭제되었습니다.");
			return "redirect:" + AsaproUtils.getAppPath(currentSite) + "/board/" + bcId + "/list";
		} else {
			
			
			//boardArticle = boardService.getBoardArticle(baIdSearch);
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
			
			//템플릿 파일 적용
			if( StringUtils.isNotBlank(boardConfig.getBcViewFile()) ){
				return this.getBoardTemplateName(boardConfig.getBcViewFile());
			}
			return AsaproUtils.getThemePath(request) + "board/" + boardConfig.getBcType() + "/view";
		}
		
	}
	
	/**
	 * 게시물을 추천한다.
	 * @param response
	 * @param currentSite
	 * @param baId
	 * @return 추천 결과 json
	 */
	@RequestMapping(value=Constant.APP_PATH + Constant.SITE_ID_PATH + "/board/{bcId}" + Constant.API_PATH  + "/recommend" , method=RequestMethod.POST)
	@ResponseBody
	public ServerMessage updateBoardArticleRecommendPost(HttpServletResponse response, @CurrentSite Site currentSite, BoardArticle recommendArticle){
		Cookie recommendCookie = AsaproUtils.getCookie(request.getCookies(), "ba_reco_" + currentSite.getSiteId() + "_" + recommendArticle.getBaId());
		if( recommendCookie == null ){
			//BoardArticle recommendArticle = new BoardArticle();
			recommendArticle.setSitePrefix(currentSite.getSitePrefix());
			//recommendArticle.setBaId(baId);
			if( boardService.updateBoardArticleRecommend(recommendArticle) > 0 ){
				
				//========= 추천통계자료 등록 ===================
				contentStatisDay(recommendArticle, currentSite, "recommend");
				//========= 추천통계자료 등록 ===================
				
				//쿠키생성
				recommendCookie = new Cookie("ba_reco_" + currentSite.getSiteId() + "_" + recommendArticle.getBaId(), System.currentTimeMillis() + "");
				recommendCookie.setDomain(request.getServerName());
				recommendCookie.setPath("/");
				recommendCookie.setHttpOnly(true);
				int t = 60 * 60 * 24 * 365;//1년
				if(t > 31536000){
					t = 31536000;
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
		public int contentStatisDay(BoardArticle boardArticle, Site currentSite, String moduleSub){
			int result = 0;
			
			Calendar cal = Calendar.getInstance();
			ContentStatisDay contentStatisDay = new ContentStatisDay();
			
			contentStatisDay.setSitePrefix(currentSite.getSitePrefix());
			contentStatisDay.setCsModiulCode("boardtype");
			contentStatisDay.setCsModiulSubCode(moduleSub);
			contentStatisDay.setCsCateCode(boardArticle.getBcId());
			contentStatisDay.setCsContentId(boardArticle.getBaId().toString());
			contentStatisDay.setCsYear(cal.get(Calendar.YEAR));
			contentStatisDay.setCsMonth(cal.get(Calendar.MONTH) + 1);
			contentStatisDay.setCsDay(cal.get(Calendar.DAY_OF_MONTH));
			
			result = contentStatisService.mergeContentStatisDay(contentStatisDay);
			return result;
		}

}