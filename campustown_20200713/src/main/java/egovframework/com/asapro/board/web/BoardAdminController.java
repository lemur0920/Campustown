/**
 * 
 */
package egovframework.com.asapro.board.web;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections4.ListUtils;
import org.apache.commons.lang3.ArrayUtils;
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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.FlashMap;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

import egovframework.com.asapro.board.service.BoardArticle;
import egovframework.com.asapro.board.service.BoardArticleSearch;
import egovframework.com.asapro.board.service.BoardConfig;
import egovframework.com.asapro.board.service.BoardConfigSearch;
import egovframework.com.asapro.board.service.BoardService;
import egovframework.com.asapro.capability.service.CapabilityService;
import egovframework.com.asapro.code.service.Code;
import egovframework.com.asapro.code.service.CodeCategorySearch;
import egovframework.com.asapro.code.service.CodeService;
import egovframework.com.asapro.fileinfo.service.FileInfoUploadResult;
import egovframework.com.asapro.member.admin.service.AdminMember;
import egovframework.com.asapro.organization.service.Department;
import egovframework.com.asapro.organization.service.DepartmentSearch;
import egovframework.com.asapro.organization.service.DepartmentView;
import egovframework.com.asapro.organization.service.DepartmentViewSearch;
import egovframework.com.asapro.organization.service.Organization;
import egovframework.com.asapro.organization.service.OrganizationSearch;
import egovframework.com.asapro.organization.service.OrganizationService;
//import egovframework.com.asapro.group.service.GroupService;
import egovframework.com.asapro.site.service.Site;
import egovframework.com.asapro.support.Constant;
import egovframework.com.asapro.support.ServerMessage;
import egovframework.com.asapro.support.annotation.CurrentAdmin;
import egovframework.com.asapro.support.annotation.CurrentSite;
import egovframework.com.asapro.support.exception.AsaproException;
import egovframework.com.asapro.support.exception.AsaproNoCapabilityException;
import egovframework.com.asapro.support.exception.AsaproNotFoundException;
import egovframework.com.asapro.support.pagination.Paging;
import egovframework.com.asapro.support.util.AsaproUtils;
import egovframework.com.asapro.support.util.EtcUtil;
import egovframework.com.campustown.startHpMngr.service.StartHpMngr;
import egovframework.com.campustown.startHpMngr.service.StartHpMngrSearch;
import egovframework.com.campustown.startHpMngr.service.StartHpMngrService;
import egovframework.com.asapro.config.service.Config;
import egovframework.com.asapro.config.service.ConfigService;
import egovframework.rte.fdl.cryptography.EgovPasswordEncoder;

/**
 * 게시판 관리자 컨트롤러
 * @author yckim
 * @since 2018. 5. 31.
 *
 */
@Controller
public class BoardAdminController {
	
	@Autowired
	private BoardService boardService;
	
	@Autowired
	private CodeService codeService;
	
	@Autowired
	private ConfigService configService;
	
	@Autowired
	private CapabilityService capabilityService;
	
	@Autowired
	private OrganizationService organizationService;
	
//	@Autowired
//	private GroupService groupService;
	
	@Autowired
	private BoardConfigValidator boardConfigValidator;
	
	@Autowired
	private BoardArticleValidator boardArticleValidator;
	
	@Autowired
	private EgovPasswordEncoder egovPasswordEncoder;
	
	@Autowired
	private HttpServletRequest request;
	
	@Autowired
	private StartHpMngrService startHpMngrService;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(BoardAdminController.class);
	
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
	
	//============================================================================================================
	//============================================================================================================
	//====================================   게시판 설정  ==============================================
	
	/**
	 * 게시판 설정 목록 뷰를 반환한다.
	 * @param model
	 * @param boardConfigSearch
	 * @return 게시판 설정 목록 뷰
	 */
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/board/config/list.do", method=RequestMethod.GET)
	public String boardConfigListGet(Model model, @CurrentAdmin AdminMember currentAdmin, @CurrentSite Site currentSite, @ModelAttribute("boardConfigSearch") BoardConfigSearch boardConfigSearch){
		boardConfigSearch.setSitePrefix(currentSite.getSitePrefix());
		boardConfigSearch.fixBrokenSvByDefaultCharsets();
		
		//관리부서의 권한 있는 목록만 조회
		if(StringUtils.isNotBlank(currentAdmin.getAdminDepartment()) ){
			boardConfigSearch.setBcDepartment(currentAdmin.getAdminDepartment());
		}
		if(StringUtils.isNotBlank(currentAdmin.getAdminTeam()) ){
			boardConfigSearch.setBcDepartment(currentAdmin.getAdminTeam());
		}
		//슈퍼어디민 인경우
		if(currentAdmin.isSuperAdmin() ){
			boardConfigSearch.setBcAdmin(true);
		}
		
		List<BoardConfig> list = boardService.getBoardConfigList(boardConfigSearch);
		int total = boardService.getBoardConfigListTotal(boardConfigSearch);
		Paging paging = new Paging(list, total, boardConfigSearch);
		
		model.addAttribute("paging", paging);
//		model.addAttribute("groupList", groupService.getGroupList(null));
		
		return "asapro/admin/board/configList";
	}
	
	/**
	 * 게세판 설정 추가폼뷰를 반환한다.
	 * @param model
	 * @param boardConfigForm
	 * @return 게시판 설정 추가폼 뷰
	 * @throws FileNotFoundException
	 */
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/board/config/insert.do", method=RequestMethod.GET)
	public String insertBoardConfigGet(Model model, @CurrentSite Site currentSite, @ModelAttribute("boardConfigForm") BoardConfig boardConfigForm) throws FileNotFoundException{
		model.addAttribute("formMode", "INSERT");
		//코드카테고리 모든 목록
		CodeCategorySearch codeCategorySearch = new CodeCategorySearch();
		codeCategorySearch.setPaging(false);
		codeCategorySearch.setDefaultSort("CAT_NAME", "ASC");
		model.addAttribute("codeCategoryList", codeService.getCodeCategoryList(codeCategorySearch));
		//webRoot + Constant.THEME_ROOT + request.getAttribute("theme") + "/board" 내 있는 폴더목록
		model.addAttribute("bcTypeList", boardService.getbcTypeList());
		//권한 모든 목록
		model.addAttribute("capabilityList", capabilityService.getCapabilityList(null));
		//기관목록
		//this.loadOrganization(model);
		
		//VIEW 개층구조의 조직 목록
		DepartmentViewSearch departmentViewSearch = new DepartmentViewSearch();
		departmentViewSearch.setPaging(false);
		List<DepartmentView> departmentViewList = organizationService.getDepartmenListInView(departmentViewSearch);
		model.addAttribute("departmentList", departmentViewList);
		
		//그룹 모든 목록
		//model.addAttribute("groupList", groupService.getGroupList(null));
		
		return "asapro/admin/board/configForm";
	}
	
	/**
	 * 게시판 설정을 추가한다.
	 * @param model
	 * @param boardConfigForm
	 * @param bindingResult
	 * @return 게시판 설정 추가 결과 뷰
	 * @throws FileNotFoundException 
	 */
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/board/config/insert.do", method=RequestMethod.POST)
	public String insertBoardConfigPost(Model model, RedirectAttributes redirectAttributes, @CurrentSite Site currentSite, @ModelAttribute("boardConfigForm") BoardConfig boardConfigForm, BindingResult bindingResult) throws FileNotFoundException{
		boardConfigForm.setSitePrefix(currentSite.getSitePrefix());
		String tempCustomFieldUse = "";
		if(boardConfigForm.getBcCustomFieldUseArray() != null && boardConfigForm.getBcCustomFieldUseArray().size() > 0 ){
			tempCustomFieldUse = StringUtils.join(boardConfigForm.getBcCustomFieldUseArray().toArray(), ",");
		}
		boardConfigForm.setBcCustomFieldUse(tempCustomFieldUse);
		
		boardConfigValidator.validate(boardConfigForm, bindingResult, "INSERT");
		if( bindingResult.hasErrors() ){
			model.addAttribute("formMode", "INSERT");
			//코드카테고리 모든 목록
			CodeCategorySearch codeCategorySearch = new CodeCategorySearch();
			codeCategorySearch.setPaging(false);
			codeCategorySearch.setDefaultSort("CAT_NAME", "ASC");
			model.addAttribute("codeCategoryList", codeService.getCodeCategoryList(codeCategorySearch));
			//webRoot + Constant.THEME_ROOT + request.getAttribute("theme") + "/board" 내 있는 폴더목록
			model.addAttribute("bcTypeList", boardService.getbcTypeList());
			//권한 모든 목록
			model.addAttribute("capabilityList", capabilityService.getCapabilityList(null));
			/*
			//기관목록
			this.loadOrganization(model);
			//부서목록
			if(StringUtils.isNotBlank(boardConfigForm.getBcOrganization()) ){
				this.loadDepartment(model, boardConfigForm.getBcOrganization());
			}*/
			
			//VIEW 개층구조의 조직 목록
			DepartmentViewSearch departmentViewSearch = new DepartmentViewSearch();
			departmentViewSearch.setPaging(false);
			List<DepartmentView> departmentViewList = organizationService.getDepartmenListInView(departmentViewSearch);
			model.addAttribute("departmentList", departmentViewList);
			
			//그룹 모든 목록
			//model.addAttribute("groupList", groupService.getGroupList(null));
			return "asapro/admin/board/configForm";
		} else {
			boardConfigForm.setBcRegDate(new Date());
			
			boardService.insertBoardConfig(boardConfigForm);
			redirectAttributes.addFlashAttribute("inserted", true);
			return "redirect:" + AsaproUtils.getAdminPath(currentSite, true) + "/board/config/list.do";
		}
	}
	
	/**
	 * 게시판 설정 수정 뷰를 반환한다.
	 * @param model
	 * @param boardConfigForm
	 * @return 게시판 설정 수정뷰
	 * @throws FileNotFoundException
	 */
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/board/config/update.do", method=RequestMethod.GET)
	public String updateBoardConfigGet(Model model, @CurrentSite Site currentSite, @ModelAttribute("boardConfigForm") BoardConfig boardConfigForm) throws FileNotFoundException{
		boardConfigForm.setSitePrefix(currentSite.getSitePrefix());
		BoardConfig boardConfigDB = boardService.getBoardConfig(boardConfigForm);
		model.addAttribute("boardConfigForm", boardConfigDB);
		
		model.addAttribute("formMode", "UPDATE");
		
		//코드카테고리 모든 목록
		CodeCategorySearch codeCategorySearch = new CodeCategorySearch();
		codeCategorySearch.setPaging(false);
		codeCategorySearch.setDefaultSort("CAT_NAME", "ASC");
		model.addAttribute("codeCategoryList", codeService.getCodeCategoryList(codeCategorySearch));
		//webRoot + Constant.THEME_ROOT + request.getAttribute("theme") + "/board" 내 있는 폴더목록
		model.addAttribute("bcTypeList", boardService.getbcTypeList());
		//권한 모든 목록
		model.addAttribute("capabilityList", capabilityService.getCapabilityList(null));
		
		/*//기관목록
		this.loadOrganization(model);
		//부서목록
		if(StringUtils.isNotBlank(boardConfigDB.getBcOrganization()) ){
			this.loadDepartment(model, boardConfigDB.getBcOrganization());
		}*/
		
		//VIEW 개층구조의 조직 목록
		DepartmentViewSearch departmentViewSearch = new DepartmentViewSearch();
		departmentViewSearch.setPaging(false);
		List<DepartmentView> departmentViewList = organizationService.getDepartmenListInView(departmentViewSearch);
		model.addAttribute("departmentList", departmentViewList);
		
		//그룹 모든 목록
		//model.addAttribute("groupList", groupService.getGroupList(null));
		
		return "asapro/admin/board/configForm";
	}
	
	/**
	 * 게시판 설정을 수정한다.
	 * @param model
	 * @param boardConfigForm
	 * @param bindingResult
	 * @return 게시판 설정 수정 결과 뷰
	 * @throws FileNotFoundException
	 */
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/board/config/update.do", method=RequestMethod.POST)
	public String updateBoardConfigPost(Model model, RedirectAttributes redirectAttributes, @CurrentSite Site currentSite, @ModelAttribute("boardConfigForm") BoardConfig boardConfigForm, BindingResult bindingResult) throws FileNotFoundException{
		boardConfigForm.setSitePrefix(currentSite.getSitePrefix());
		String tempCustomFieldUse = "";
		if(boardConfigForm.getBcCustomFieldUseArray() != null && boardConfigForm.getBcCustomFieldUseArray().size() > 0 ){
			tempCustomFieldUse = StringUtils.join(boardConfigForm.getBcCustomFieldUseArray().toArray(), ",");
		}
		boardConfigForm.setBcCustomFieldUse(tempCustomFieldUse);
		
		boardConfigValidator.validate(boardConfigForm, bindingResult, "UPDATE");
		if( bindingResult.hasErrors() ){
			model.addAttribute("formMode", "UPDATE");
			
			//코드카테고리 모든 목록
			CodeCategorySearch codeCategorySearch = new CodeCategorySearch();
			codeCategorySearch.setPaging(false);
			codeCategorySearch.setDefaultSort("CAT_NAME", "ASC");
			model.addAttribute("codeCategoryList", codeService.getCodeCategoryList(codeCategorySearch));
			//webRoot + Constant.THEME_ROOT + request.getAttribute("theme") + "/board" 내 있는 폴더목록
			model.addAttribute("bcTypeList", boardService.getbcTypeList());
			//권한 모든 목록
			model.addAttribute("capabilityList", capabilityService.getCapabilityList(null));
			
			/*//기관목록
			this.loadOrganization(model);
			//부서목록
			if(StringUtils.isNotBlank(boardConfigForm.getBcOrganization()) ){
				this.loadDepartment(model, boardConfigForm.getBcOrganization());
			}*/
			
			//VIEW 개층구조의 조직 목록
			DepartmentViewSearch departmentViewSearch = new DepartmentViewSearch();
			departmentViewSearch.setPaging(false);
			List<DepartmentView> departmentViewList = organizationService.getDepartmenListInView(departmentViewSearch);
			model.addAttribute("departmentList", departmentViewList);
			
			//그룹 모든 목록
			//model.addAttribute("groupList", groupService.getGroupList(null));
			return "asapro/admin/board/configForm";
		} else {
			
			boardService.updateBoardConfig(boardConfigForm);
			redirectAttributes.addFlashAttribute("updated", true);
			return "redirect:" + AsaproUtils.getAdminPath(currentSite, true) + "/board/config/update.do?bcId=" + boardConfigForm.getBcId();
		}
	}
	
	/**
	 * 게시판을 삭제한다.
	 * <pre>
	 * - 게시물, 첨부파일 포함
	 * </pre>
	 * @param model
	 * @param bcIds
	 * @return 삭제결과 뷰
	 * @throws IOException 
	 * @throws AsaproException 
	 */
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/board/config/delete.do", method=RequestMethod.POST)
	@ResponseBody
	public ServerMessage deleteBoardConfigPost(Model model, @CurrentSite Site currentSite, @RequestParam(value="bcIds[]", required=true) String[] bcIds) throws AsaproException, IOException{
		ServerMessage serverMessage = new ServerMessage();
		if( ArrayUtils.isEmpty(bcIds) ){
			serverMessage.setSuccess(false);
			serverMessage.setText("삭제할 항목이 없습니다.");
		} else {
			List<BoardConfig> boardConfigList = new ArrayList<BoardConfig>();
			BoardConfig boardConfig = null;
			for( String bcId : bcIds ){
				boardConfig = new BoardConfig();
				boardConfig.setSitePrefix(currentSite.getSitePrefix());
				boardConfig.setBcId(bcId);
				boardConfigList.add(boardConfig);
			}
			int result = boardService.deleteBoardConfig(boardConfigList);
			if(result > 0){
				serverMessage.setSuccess(true);
				serverMessage.setSuccessCnt(result);
			} else {
				serverMessage.setSuccess(false);
				serverMessage.setText("삭제하지 못하였습니다.");
			}
		}
		return serverMessage;
	}
	
	
	//============================================================================================================
	//============================================================================================================
	//====================================   게시글   ==============================================
	
	/**
	 * 게시물 목록을 반환한다.
	 * @param model
	 * @param currentSite
	 * @param bcId
	 * @param boardArticleSearch
	 * @return
	 */
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/board/article/list.do", method=RequestMethod.GET)
	public String boardArticleListGet(Model model, @CurrentAdmin AdminMember currentAdmin, @CurrentSite Site currentSite, @ModelAttribute("boardArticleSearch") BoardArticleSearch boardArticleSearch) {
		
		//게시판 목록
		BoardConfigSearch boardConfigSearch2 = new BoardConfigSearch();
		boardConfigSearch2.setSitePrefix(currentSite.getSitePrefix());
		boardConfigSearch2.setPaging(false);
		
		//관리부서의 권한 있는 목록만 조회
		if(StringUtils.isNotBlank(currentAdmin.getAdminDepartment()) ){
			boardConfigSearch2.setBcDepartment(currentAdmin.getAdminDepartment());
		}
		if(StringUtils.isNotBlank(currentAdmin.getAdminTeam()) ){
			boardConfigSearch2.setBcDepartment(currentAdmin.getAdminTeam());
		}
		//슈퍼어디민 인경우
		if(currentAdmin.isSuperAdmin() ){
			boardConfigSearch2.setBcAdmin(true);
		}
		
		List<BoardConfig> boardConfigList = boardService.getBoardConfigList(boardConfigSearch2);
		model.addAttribute("boardConfigList", boardConfigList);
		
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
		model.addAttribute("boardConfig", boardConfig);
		
		//---------------------------
		// 게시글
		//---------------------------
		boardArticleSearch.setSitePrefix(currentSite.getSitePrefix());
		boardArticleSearch.setDefaultSort("BA_REGDATE", "DESC");
		boardArticleSearch.setBaNotice(false);
		boardArticleSearch.setNow(AsaproUtils.getFormattedDate(new Date()));
		boardArticleSearch.fixBrokenSvByDefaultCharsets();
		if( boardArticleSearch.getPageSize() == Constant.PAGE_SIZE && boardConfig.getBcPageSize() != Constant.PAGE_SIZE ){
			boardArticleSearch.setPageSize(boardConfig.getBcPageSize());
		}
		
		// 게시판에 관리권한 부서 부여된경우 해당 부서,팀의 글만 검색
		if(ArrayUtils.isNotEmpty(boardConfig.getBcDepartmentArray()) ){
			//슈퍼어디민이 아닌경우만
			if(!currentAdmin.isSuperAdmin() ){
				boardArticleSearch.setDepId(currentAdmin.getAdminDepartment());
				boardArticleSearch.setTeamId(currentAdmin.getAdminTeam());
			}
		}
			
		
		List<BoardArticle> list = boardService.getBoardArticleList(boardArticleSearch);
		int total = boardService.getBoardArticleListTotal(boardArticleSearch);
		Paging paging = new Paging(list, total, boardArticleSearch);
		model.addAttribute("paging", paging);
	
		//boolean isBoardManager = (Boolean) request.getAttribute("isBoardManager"); 
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
			noticeArticleSearch.setBaCategory1(boardArticleSearch.getBaCategory1());
			noticeArticleSearch.setBaCategory2(boardArticleSearch.getBaCategory2());
			noticeArticleSearch.setBaCategory3(boardArticleSearch.getBaCategory3());
			noticeArticleSearch.setBaNotice(true);
			noticeArticleSearch.setBaUse(boardArticleSearch.getBaUse());
			
			// 게시판에 관리권한 부서 부여된경우 해당 부서,팀의 글만 검색
			if(ArrayUtils.isNotEmpty(boardConfig.getBcDepartmentArray()) ){
				//슈퍼어디민이 아닌경우만
				if(!currentAdmin.isSuperAdmin() ){
					noticeArticleSearch.setDepId(currentAdmin.getAdminDepartment());
					noticeArticleSearch.setTeamId(currentAdmin.getAdminTeam());
				}
			}
			
			noticeArticleSearch.setNow(AsaproUtils.getFormattedDate(new Date()));
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
		
		return "asapro/admin/board/articleList";
	}
	
	/**
	 * 게시물 입력 폼 뷰를 반환한다.
	 * @param model
	 * @param currentSite
	 * @param bcId
	 * @param boardArticleForm
	 * @return 게시물 입력 폼 뷰
	 */
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/board/article/insert.do", method=RequestMethod.GET)
	public String boardInsertGet(Model model, @CurrentSite Site currentSite, @ModelAttribute("boardArticleForm") BoardArticle boardArticleForm){
		
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
		//코드데이터
		this.loadCodeData(model, boardConfig);
		List<Code> bcNuriCodeList = codeService.getCodeList("NURI_TYPE");
		model.addAttribute("bcNuriCodeList", bcNuriCodeList);
		
		/**
		 * 창업 제품 소개 및 장터 게시판 예외처리
		 */
		if("product".equals(boardArticleForm.getBcId()) ){
			//부서목록
			DepartmentSearch departmentSearch = new DepartmentSearch();
			departmentSearch.setPaging(false);
			departmentSearch.setDepUse(true);
			departmentSearch.setOrgId("university");
			List<Department> departmentList = organizationService.getDepartmentList(departmentSearch);
			model.addAttribute("departmentList", departmentList);
			
		}
		
		
		return "asapro/admin/board/articleForm";
	}
	
	
	/**
	 * 게시물을 입력한다.
	 * @param model
	 * @param currentSite
	 * @param currentAdmin
	 * @param boardArticleForm
	 * @param bindingResult
	 * @return 등록결과
	 * @throws IOException
	 */
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/board/article/insert.do", method=RequestMethod.POST)
	public String boardInsertPost(Model model, RedirectAttributes redirectAttributes, @CurrentSite Site currentSite, @CurrentAdmin AdminMember currentAdmin, @ModelAttribute("boardArticleForm") BoardArticle boardArticleForm, BindingResult bindingResult) throws IOException{
		
		BoardConfig boardConfigSearch = new BoardConfig();
		boardConfigSearch.setSitePrefix(currentSite.getSitePrefix());
		boardConfigSearch.setBcId(boardArticleForm.getBcId());
		BoardConfig boardConfig = boardService.getBoardConfig(boardConfigSearch);
		
		boardArticleForm.setSitePrefix(currentSite.getSitePrefix());
		boardArticleForm.setBoardConfig(boardConfig);
		boardArticleForm.setAdminMember(currentAdmin);
		boardArticleForm.setMemId(currentAdmin.getAdminId());
		boardArticleForm.setDepId(currentAdmin.getAdminDepartment());
		boardArticleForm.setTeamId(currentAdmin.getAdminTeam());
		boardArticleForm.setBaUpdaterId(currentAdmin.getAdminId());

		boardArticleValidator.validate(boardArticleForm, bindingResult, "INSERT");
		if( bindingResult.hasErrors() ){
			
			model.addAttribute("baFileInfosSize", boardArticleForm.getBaFileInfos().size());
			model.addAttribute("boardConfig", boardConfig);
			model.addAttribute("formMode", "INSERT");
			
			//코드데이터
			this.loadCodeData(model, boardConfig);
			List<Code> bcNuriCodeList = codeService.getCodeList("NURI_TYPE");
			model.addAttribute("bcNuriCodeList", bcNuriCodeList);
			
			/**
			 * 창업 제품 소개 및 장터 게시판 예외처리
			 */
			if("product".equals(boardArticleForm.getBcId()) ){
				//부서목록
				DepartmentSearch departmentSearch = new DepartmentSearch();
				departmentSearch.setPaging(false);
				departmentSearch.setDepUse(true);
				departmentSearch.setOrgId("university");
				List<Department> departmentList = organizationService.getDepartmentList(departmentSearch);
				model.addAttribute("departmentList", departmentList);
				
				//창업팀 목록
				if(StringUtils.isNotBlank(boardArticleForm.getBaCategory2()) ){
					StartHpMngrSearch startHpMngrSearch = new StartHpMngrSearch();
					startHpMngrSearch.setPaging(false);
					startHpMngrSearch.setUnivId(boardArticleForm.getBaCategory2());
					List<StartHpMngr> teamList = startHpMngrService.getStartHpMngrList(startHpMngrSearch);
					model.addAttribute("teamList", teamList);
				}
			}
			
			return "asapro/admin/board/articleForm";
			
		} else {
			//게시물 필드값 정리 - XSS 필터링 포함
			//제목은 텍스트만 받음
			boardArticleForm.setBaTitle(Jsoup.clean(boardArticleForm.getBaTitle(), Whitelist.none()));
			//Whitelist whitelist = this.getJsoupWhiteListForBoardArticle();
			
			//본문 html 세팅
			//관리자페이지는 웹에이터 사용으로 테그 제거하지 않는다..
			//boardArticleForm.setBaContentHtml( Jsoup.clean(boardArticleForm.getBaContentHtml(), "", whitelist , new OutputSettings().prettyPrint(false) ) );
			
			//본문 plain text 세팅
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
			
			if( StringUtils.isNotBlank(boardArticleForm.getBaGuestPassword()) ){
				boardArticleForm.setBaGuestPassword(egovPasswordEncoder.encryptPassword(boardArticleForm.getBaGuestPassword()));
			}
			if( StringUtils.isNotBlank(boardArticleForm.getBaSecretPassword()) ){
				boardArticleForm.setBaSecretPassword(egovPasswordEncoder.encryptPassword(boardArticleForm.getBaSecretPassword()));
			}
			boardArticleForm.setBaIp(AsaproUtils.getRempoteIp(request));
			boardArticleForm.setBaRegDate(new Date());
			boardArticleForm.setBaLastModified(new Date());
			
			// jaseo - 2020.04.08. 명예의 전당 예외 처리 
			// 사용 안함
			/*
			if("fame".equals(boardConfig.getBcId())){
				if( StringUtils.isNotBlank(boardArticleForm.getBaCustomField1()) ){
					if( !boardArticleForm.getBaCustomField1().startsWith("http://") && !boardArticleForm.getBaCustomField1().startsWith("https://") ){
						boardArticleForm.setBaCustomField1("http://" + boardArticleForm.getBaCustomField1());
					}
				}
			}
			*/
			
			/**
			 * jaseo - 2020.07.02. 언론보도 게시판 예외처리
			 */
			//board/article/update.do
			if("journal".equals(boardConfig.getBcId()) ){
						
				if( StringUtils.isNotBlank(boardArticleForm.getBaCustomField1()) ){
					if( !boardArticleForm.getBaCustomField1().startsWith("http://") && !boardArticleForm.getBaCustomField1().startsWith("https://") ){
						boardArticleForm.setBaCustomField1("http://" + boardArticleForm.getBaCustomField1());
					}
				}		
			}
			
			//게시물 저장
			Map<String, FileInfoUploadResult> fileInfoUploadResultMap = boardService.insertBoardArticle(boardArticleForm);
			
			//업로드 에러 있으면 바로 수정화면으로 전환 - 에러난 파일 제외 글과 다른 파일들은 저장된 상태임.
			if(fileInfoUploadResultMap.get("thumbFileInfoUploadResult").hasErrors() || fileInfoUploadResultMap.get("fileInfoUploadResult").hasErrors() ){
				FlashMap flashMap = RequestContextUtils.getOutputFlashMap(request);
				
				if(fileInfoUploadResultMap.get("thumbFileInfoUploadResult").hasErrors() ){
					flashMap.put("thumbFileInfoUploadResult", fileInfoUploadResultMap.get("thumbFileInfoUploadResult"));
				}
				if(fileInfoUploadResultMap.get("fileInfoUploadResult").hasErrors() ){
					flashMap.put("fileInfoUploadResult", fileInfoUploadResultMap.get("fileInfoUploadResult"));
				}
				return "redirect:" + AsaproUtils.getAdminPath(currentSite, true) +  "/board/article/update.do?bcId=" + boardArticleForm.getBcId() + "&baId=" + boardArticleForm.getBaId();
			}
			
			redirectAttributes.addFlashAttribute("inserted", true);
			return "redirect:" + AsaproUtils.getAdminPath(currentSite, true) + "/board/article/list.do?bcId=" + boardArticleForm.getBcId();
		}
	}
	
	
	/**
	 * 게시물 수정뷰를 반환한다.
	 * @param model
	 * @param currentSite
	 * @param currentAdmin
	 * @param boardArticleForm
	 * @return 게시물 수정 뷰
	 */
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/board/article/update.do", method=RequestMethod.GET)
	public String updateBoardArticleGet(Model model, @CurrentSite Site currentSite, @CurrentAdmin AdminMember currentAdmin, @ModelAttribute("boardArticleForm") BoardArticle boardArticleForm){
		
		BoardConfig boardConfigSearch = new BoardConfig();
		boardConfigSearch.setSitePrefix(currentSite.getSitePrefix());
		boardConfigSearch.setBcId(boardArticleForm.getBcId());
		BoardConfig boardConfig = boardService.getBoardConfig(boardConfigSearch);
		model.addAttribute("boardConfig", boardConfig);
		
		boardArticleForm.setSitePrefix(currentSite.getSitePrefix());
		BoardArticle boardArticleModel = boardService.getBoardArticle(boardArticleForm);
		boardArticleModel.setSitePrefix(currentSite.getSitePrefix());
		boardArticleModel.setBoardConfig(boardConfig);

		//board 인터셉터에서 체크한 게시판 권한외 글의 번호를 조작할경우 조작된글이 해당게시판인여부 확인
		if(!boardArticleModel.getBcId().equals(boardConfig.getBcId()) ){
			throw new AsaproNoCapabilityException("유효하지 않은 접근입니다.", "back");
		}
		
		model.addAttribute("boardArticleForm", boardArticleModel);
		model.addAttribute("baFileInfosSize", boardArticleForm.getBaFileInfos().size());
		model.addAttribute("formMode", "UPDATE");
		
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
		
		/**
		 * 창업 제품 소개 및 장터 게시판 예외처리
		 */
		if("product".equals(boardArticleModel.getBcId()) ){
			//부서목록
			DepartmentSearch departmentSearch = new DepartmentSearch();
			departmentSearch.setPaging(false);
			departmentSearch.setDepUse(true);
			departmentSearch.setOrgId("university");
			List<Department> departmentList = organizationService.getDepartmentList(departmentSearch);
			model.addAttribute("departmentList", departmentList);
			
			//창업팀 목록
			if(StringUtils.isNotBlank(boardArticleModel.getBaCategory2()) ){
				StartHpMngrSearch startHpMngrSearch = new StartHpMngrSearch();
				startHpMngrSearch.setPaging(false);
				startHpMngrSearch.setUnivId(boardArticleModel.getBaCategory2());
				List<StartHpMngr> teamList = startHpMngrService.getStartHpMngrList(startHpMngrSearch);
				model.addAttribute("teamList", teamList);
			}
		}
		
		return "asapro/admin/board/articleForm";
		
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
	 * @throws AsaproException 
	 */
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/board/article/update.do", method=RequestMethod.POST)
	public String updateBoardArticlePost(Model model, RedirectAttributes redirectAttributes, @CurrentSite Site currentSite, @CurrentAdmin AdminMember currentAdmin 
			, @ModelAttribute("boardArticleForm") BoardArticle boardArticleForm, BindingResult bindingResult) throws AsaproException, IOException{
		
		BoardConfig bcIdSearch = new BoardConfig();
		bcIdSearch.setSitePrefix(currentSite.getSitePrefix());
		bcIdSearch.setBcId(boardArticleForm.getBcId());
		BoardConfig boardConfig = boardService.getBoardConfig(bcIdSearch);
		
		boardArticleForm.setSitePrefix(currentSite.getSitePrefix());
		boardArticleForm.setAdminMember(currentAdmin);
		//boardArticleForm.setMemId(currentAdmin.getAdminId());
		boardArticleForm.setBaUpdaterId(currentAdmin.getAdminId());
		boardArticleForm.setBoardConfig(boardConfig);
		
		//board 인터셉터에서 체크한 게시판 권한외 글의 번호를 조작할경우 조작된글이 해당게시판인여부 확인
		BoardArticle boardArticleModel = boardService.getBoardArticle(boardArticleForm);
		if(!boardArticleModel.getBcId().equals(boardConfig.getBcId()) ){
			throw new AsaproNoCapabilityException("유효하지 않은 접근입니다.", "back");
		}
		
		boardArticleValidator.validate(boardArticleForm, bindingResult, "UPDATE");
		if( bindingResult.hasErrors() ){
			
			model.addAttribute("boardConfig", boardConfig);
			model.addAttribute("baFileInfosSize", boardArticleForm.getBaFileInfos().size());
			model.addAttribute("formMode", "UPDATE");
			
			//코드데이터
			this.loadCodeData(model, boardConfig);
			List<Code> bcNuriCodeList = codeService.getCodeList("NURI_TYPE");
			model.addAttribute("bcNuriCodeList", bcNuriCodeList);
			
			/**
			 * 창업 제품 소개 및 장터 게시판 예외처리
			 */
			if("product".equals(boardArticleModel.getBcId()) ){
				//부서목록
				DepartmentSearch departmentSearch = new DepartmentSearch();
				departmentSearch.setPaging(false);
				departmentSearch.setDepUse(true);
				departmentSearch.setOrgId("university");
				List<Department> departmentList = organizationService.getDepartmentList(departmentSearch);
				model.addAttribute("departmentList", departmentList);
				
				//창업팀 목록
				if(StringUtils.isNotBlank(boardArticleModel.getBaCategory2()) ){
					StartHpMngrSearch startHpMngrSearch = new StartHpMngrSearch();
					startHpMngrSearch.setPaging(false);
					startHpMngrSearch.setUnivId(boardArticleModel.getBaCategory2());
					List<StartHpMngr> teamList = startHpMngrService.getStartHpMngrList(startHpMngrSearch);
					model.addAttribute("teamList", teamList);
				}
			}
			
			return "asapro/admin/board/articleForm";
			
		} else {
			//게시물 필드값 정리 - XSS 필터링 포함
			//제목은 텍스트만 받음
			boardArticleForm.setBaTitle(Jsoup.clean(boardArticleForm.getBaTitle(), Whitelist.none()));
			//본문은 모든 태거 제거, a 태그만 허용
			//Whitelist whitelist = this.getJsoupWhiteListForBoardArticle();
			
			//본문 html 세팅
			//관리자페이지는 웹에디터 사용으로 테그 제거하지 않는다...
			//boardArticleForm.setBaContentHtml( Jsoup.clean(boardArticleForm.getBaContentHtml(), "", whitelist , new OutputSettings().prettyPrint(false) ) );
			
			//본문 plain text 세팅
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
			
			BoardArticle fromDB = boardService.getBoardArticle(boardArticleForm);
			
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
						boardArticleForm.setBaSecretPassword(fromDB.getBaSecretPassword());
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
			boardArticleForm.setBaUpdaterId(currentAdmin.getAdminId());
			boardArticleForm.setBaLastModified(new Date());
			
			// jaseo - 2020.04.08. 명예의 전당 예외 처리 => 이제 사용 안함
			/*
			if("fame".equals(boardConfig.getBcId())){
				if( StringUtils.isNotBlank(boardArticleForm.getBaCustomField1()) ){
					if( !boardArticleForm.getBaCustomField1().startsWith("http://") && !boardArticleForm.getBaCustomField1().startsWith("https://") ){
						boardArticleForm.setBaCustomField1("http://" + boardArticleForm.getBaCustomField1());
					}
				}
			}
			*/
			
			// jaseo - 2020.07.02. 언론보도 게시판 예외처리
			if("journal".equals(boardArticleModel.getBcId()) ){
						
				if( StringUtils.isNotBlank(boardArticleModel.getBaCustomField1()) ){
					if( !boardArticleForm.getBaCustomField1().startsWith("http://") && !boardArticleForm.getBaCustomField1().startsWith("https://") ){
						boardArticleForm.setBaCustomField1("http://" + boardArticleForm.getBaCustomField1());
					}
				}		
			}
			
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
				return "redirect:" + AsaproUtils.getAdminPath(currentSite, true) +  "/board/article/update.do?bcId=" + boardArticleForm.getBcId() + "&baId=" + boardArticleForm.getBaId();
			}
			redirectAttributes.addFlashAttribute("updated", true);
			return "redirect:" + AsaproUtils.getAdminPath(currentSite, true) +  "/board/article/update.do?bcId=" + boardArticleForm.getBcId() + "&baId=" + boardArticleForm.getBaId();
		}
	}
	
	/**
	 * 게시물을 삭제한다.
	 * <pre>
	 * - 첨부파일, 댓글 포함
	 * </pre>
	 * @param model
	 * @param currentSite
	 * @param bcId
	 * @param baIds
	 * @return 삭제결과
	 * @throws AsaproException
	 * @throws IOException
	 */
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/board/article/delete.do", method=RequestMethod.POST)
	@ResponseBody
	public ServerMessage deleteBoardArticlePost(Model model, @CurrentSite Site currentSite, @RequestParam(value="baIds[]", required=true) Integer[] baIds) throws AsaproException, IOException{
		ServerMessage serverMessage = new ServerMessage();
		if( ArrayUtils.isEmpty(baIds) ){
			serverMessage.setSuccess(false);
			serverMessage.setText("삭제할 항목이 없습니다.");
		} else {
			List<BoardArticle> boardArticleList = new ArrayList<BoardArticle>();
			BoardArticle boardArticle = null;
			for( Integer baId : baIds ){
				boardArticle = new BoardArticle();
				boardArticle.setSitePrefix(currentSite.getSitePrefix());
				boardArticle.setBaId(baId);
				boardArticleList.add(boardArticle);
			}
			int result = boardService.deleteBoardArticle(boardArticleList);
			if(result > 0){
				serverMessage.setSuccess(true);
				serverMessage.setSuccessCnt(result);
			} else {
				serverMessage.setSuccess(false);
				serverMessage.setText("삭제하지 못하였습니다.");
			}
		}
		return serverMessage;
	}
	
	

	
}
