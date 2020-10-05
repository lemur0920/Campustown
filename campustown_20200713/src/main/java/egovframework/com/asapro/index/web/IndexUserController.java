/**
 * 
 */
package egovframework.com.asapro.index.web;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import egovframework.com.asapro.archive.customtype.space.service.Space;
import egovframework.com.asapro.archive.customtype.space.service.SpaceSearch;
import egovframework.com.asapro.archive.customtype.space.service.SpaceService;
import egovframework.com.asapro.board.service.BoardArticle;
import egovframework.com.asapro.board.service.BoardArticleSearch;
import egovframework.com.asapro.board.service.BoardConfig;
import egovframework.com.asapro.board.service.BoardConfigSearch;
import egovframework.com.asapro.board.service.BoardService;
import egovframework.com.asapro.code.service.CodeService;
import egovframework.com.asapro.content_statis.service.ContentStatisSearch;
import egovframework.com.asapro.content_statis.service.ContentStatisService;
import egovframework.com.asapro.fileinfo.service.FileInfo;
import egovframework.com.asapro.fileinfo.service.FileInfoService;
import egovframework.com.asapro.organization.service.Department;
import egovframework.com.asapro.organization.service.DepartmentSearch;
import egovframework.com.asapro.organization.service.OrganizationService;
import egovframework.com.asapro.rental.service.Rental;
import egovframework.com.asapro.rental.service.RentalSearch;
import egovframework.com.asapro.rental.service.RentalService;
import egovframework.com.asapro.site.service.Site;
import egovframework.com.asapro.support.Constant;
import egovframework.com.asapro.support.annotation.CurrentSite;
import egovframework.com.asapro.support.pagination.Paging;
import egovframework.com.asapro.support.util.AsaproUtils;
import egovframework.com.asapro.support.util.EtcUtil;
import egovframework.com.campustown.startHpMngr.service.StartHpMngr;
import egovframework.com.campustown.startHpMngr.service.StartHpMngrSearch;
import egovframework.com.campustown.startHpMngr.service.StartHpMngrService;
import egovframework.com.campustown.univHpMngr.service.UnivHpMngrSearch;
import egovframework.com.campustown.univHpMngr.service.UnivHpMngrService;

/**
 * 사용자 메인 컨트롤러
 * @author yckim
 * @since 2018. 4. 18.
 *
 */
@Controller
public class IndexUserController {
	
	@Autowired
	private HttpServletRequest request;
	
	@Autowired
	private CodeService codeService;
	
	@Autowired
	private BoardService boardService;
	
	@Autowired
	private StartHpMngrService startHpMngrService;
	
	@Autowired
	private UnivHpMngrService univHpMngrService;
	
	@Autowired
	private ContentStatisService contentStatisService;
	
	@Autowired
	private SpaceService spaceService;
	
	@Autowired
	private OrganizationService organizationService;
	
	@Autowired
	private RentalService rentalService;
	
	@Autowired
	private FileInfoService fileInfoService;
	
	/**
	 * 메인화면을 반환한다.
	 * @param dummyForm
	 * @param model
	 * @return
	 */
	@RequestMapping(value=Constant.APP_PATH + Constant.SITE_ID_PATH + "/home", method=RequestMethod.GET)
	public String indexGet(Model model, @CurrentSite Site currentSite){
		
		if(currentSite.isSiteMain() ){
			//메인사이트(한국어) 인경우 메인페이지 구현
			//==============  대학 등록 수, 창업팀 등록 수 =========================
			
			UnivHpMngrSearch univHpMngrSearch = new UnivHpMngrSearch();
			univHpMngrSearch.setExpsrYn("Y");
			model.addAttribute("univListTot", univHpMngrService.getUnivHpMngrListTotal(univHpMngrSearch));
			
			StartHpMngrSearch startHpMngrSearch2 = new StartHpMngrSearch();
			startHpMngrSearch2.setExpsrYn("Y");
			model.addAttribute("startListTot", startHpMngrService.getStartHpMngrListTotal(startHpMngrSearch2));
			
			
			//==============  명예의전당  - 게시판 (// 공지, 비공지 상관없이 최근 등록된 글 하나) =========================
			BoardArticleSearch boardArticleSearch = new BoardArticleSearch();
			boardArticleSearch.setSitePrefix(currentSite.getSitePrefix());
			boardArticleSearch.setDefaultSort("BA_REGDATE", "DESC");
			boardArticleSearch.setBaNotice(null);	//공지글과 와 공지아닌글 모두 가져온다 
			boardArticleSearch.setBaUse(true);	//게시하는 글만
			boardArticleSearch.setNow(AsaproUtils.getFormattedDate(new Date()));
			boardArticleSearch.fixBrokenSvByDefaultCharsets();
			boardArticleSearch.setPageSize(8);
			boardArticleSearch.setBcId("fame");

			List<BoardArticle> fameList = boardService.getBoardArticleList(boardArticleSearch);
			model.addAttribute("fameList", fameList);
			
			
			//=============  서울 캠퍼스타운What’s New? - 모든게시판 =========================
			BoardArticleSearch boardArticleAllSearch = new BoardArticleSearch();
			boardArticleAllSearch.setSitePrefix(currentSite.getSitePrefix());
			boardArticleAllSearch.setDefaultSort("BA_REGDATE", "DESC");
			boardArticleAllSearch.setBaNotice(null);	//공지글과 와 공지아닌글 모두 가져온다 
			boardArticleAllSearch.setBaUse(true);	//게시하는 글만
			boardArticleAllSearch.setNow(AsaproUtils.getFormattedDate(new Date()));
			boardArticleAllSearch.fixBrokenSvByDefaultCharsets();
			boardArticleAllSearch.setPageSize(4);
			boardArticleAllSearch.setBcId(null);

			List<BoardArticle> boardAllList = boardService.getBoardArticleList(boardArticleAllSearch);
			model.addAttribute("boardAllList", boardAllList);
			
			//===============  모든 게시판 목록 =========================
			BoardConfigSearch boardConfigSearch = new BoardConfigSearch();
			boardConfigSearch.setSitePrefix(currentSite.getSitePrefix());
			boardConfigSearch.fixBrokenSvByDefaultCharsets();
			boardConfigSearch.setBcUse(true);
			boardConfigSearch.setBcAdmin(null);
			List<BoardConfig> boardConfigList = boardService.getBoardConfigList(boardConfigSearch);
			model.addAttribute("boardConfigList", boardConfigList);
			
			 
			//================  최다 HOT =========================
			//어제 날짜 기준 월,주의 데이터를 대상
			Date yesterDay = AsaproUtils.makeCalculDate(-1);
			int year = Integer.parseInt(AsaproUtils.getFormattedDate(yesterDay, "yyyy"));
			int month = Integer.parseInt(AsaproUtils.getFormattedDate(yesterDay, "MM"));
			
			ContentStatisSearch contentStatisSearch = new ContentStatisSearch();
			contentStatisSearch.setSitePrefix(currentSite.getSitePrefix());
			contentStatisSearch.setPageSize(5);
			contentStatisSearch.setCsYear(year);
			contentStatisSearch.setCsMonth(month);
			contentStatisSearch.setCsModiulCode("boardtype");
			contentStatisSearch.setCsModiulSubCode("recommend");
			List<BoardArticle> boardRecommendBestList = contentStatisService.getBoardBestInMonthList(contentStatisSearch);
			model.addAttribute("boardRecommendBestList", boardRecommendBestList);
			
			//===================  최다 HIT =========================
			contentStatisSearch.setCsModiulSubCode("hit");
			List<BoardArticle> boardHitBestList = contentStatisService.getBoardBestInMonthList(contentStatisSearch);
			model.addAttribute("boardHitBestList", boardHitBestList);
			
			//======================  창업활동랭킹 =========================
			
			//현재 날짜 기준 월,주의 데이터를 대상
			Date today2 = new Date();
			int nowYear = Integer.parseInt(AsaproUtils.getFormattedDate(today2, "yyyy"));
			int nowMonth = Integer.parseInt(AsaproUtils.getFormattedDate(today2, "MM"));
			
			int cardi = 1;
			
			if(nowMonth >= 7 && nowMonth <= 12){
				cardi = 2;
			}
			
			// 현재 년도, 기수 기준으로 매출 실적이 반영되는 기수로 처리
			// ex> 현재 2020.04월(cardi == 1) => 실적은 2019 하반기 실적 나옴.
			//     현재 2020.12월(cardi == 2) => 실적은 202 상반기 실적 나옴.
			
			int curCardi = cardi;
			int curYear = nowYear;
			
			if(cardi == 2){
				curCardi = 1;   // 기수만 -1 처리
			}
			
			if(cardi == 1){
				curYear = nowYear - 1;
				curCardi = 2;
			}
			
			StartHpMngrSearch startHpMngrSearch3 = new StartHpMngrSearch();

			// 투자액(만원) , invest -----------------
			startHpMngrSearch3.setSortOrder("TOT_INVT_AMT");
			startHpMngrSearch3.setSesnYear(String.valueOf(curYear));
			startHpMngrSearch3.setCardiNum(String.valueOf(curCardi));
			startHpMngrSearch3.setPageSize(5);
			//startHpMngrSearch3.setPaging(false);
			
			List<StartHpMngr> compActL1 = startHpMngrService.getStartHpCompActIdxList(startHpMngrSearch3);
			//System.out.println("compActL1 (size) : " + compActL1.size());
			// 이미지 파일정보 셋팅
			if(compActL1.size() > 0){
				if(null != compActL1.get(0).getFileId1() && !"".equals(compActL1.get(0).getFileId1())){
					FileInfo fileInfo = new FileInfo();
					fileInfo.setSitePrefix(currentSite.getSitePrefix());
					fileInfo.setFileId(Integer.parseInt(compActL1.get(0).getFileId1()));
					compActL1.get(0).setFile1Info(fileInfoService.getFileInfo(fileInfo));
				}
			}
			
			model.addAttribute("compActL1", compActL1);
			
			
			// 매출액(만원), sale -----------------
			startHpMngrSearch3.setSortOrder("TOT_SALE_AMT");
			List<StartHpMngr> compActL2 = startHpMngrService.getStartHpCompActIdxList(startHpMngrSearch3);
			//System.out.println("compActL2 (size) : " + compActL2.size());
			// 이미지 파일정보 셋팅
			if(compActL2.size() > 0 ){
				if(null != compActL2.get(0).getFileId1() && !"".equals(compActL2.get(0).getFileId1())){
					FileInfo fileInfo = new FileInfo();
					fileInfo.setSitePrefix(currentSite.getSitePrefix());
					fileInfo.setFileId(Integer.parseInt(compActL2.get(0).getFileId1()));
					compActL2.get(0).setFile1Info(fileInfoService.getFileInfo(fileInfo));
				}
			}
			
			model.addAttribute("compActL2", compActL2);
			
			// 고용인수(명), employees -----------------
			
			startHpMngrSearch3.setSortOrder("TOT_EMPLY_CNT");
			List<StartHpMngr> compActL3 = startHpMngrService.getStartHpCompActIdxList(startHpMngrSearch3);
			//System.out.println("compActL3 (size) : " + compActL3.size());
			// 이미지 파일정보 셋팅
			if(compActL3.size() > 0){
				if(null != compActL3.get(0).getFileId1() && !"".equals(compActL3.get(0).getFileId1())){
					FileInfo fileInfo = new FileInfo();
					fileInfo.setSitePrefix(currentSite.getSitePrefix());
					fileInfo.setFileId(Integer.parseInt(compActL3.get(0).getFileId1()));
					compActL3.get(0).setFile1Info(fileInfoService.getFileInfo(fileInfo));
				}
			}
			
			model.addAttribute("compActL3", compActL3);
			
			// 지적재산(건), intellProp
			
			startHpMngrSearch3.setSortOrder("TOT_INTELL_PROP");
			
			List<StartHpMngr> compActL4 = startHpMngrService.getStartHpCompActIdxList(startHpMngrSearch3);
			//System.out.println("compActL4 (size) : " + compActL4.size());
			// 이미지 파일정보 셋팅
			if(compActL4.size() > 0){
				if(null != compActL4.get(0).getFileId1() && !"".equals(compActL4.get(0).getFileId1())){
					FileInfo fileInfo = new FileInfo();
					fileInfo.setSitePrefix(currentSite.getSitePrefix());
					fileInfo.setFileId(Integer.parseInt(compActL4.get(0).getFileId1()));
					compActL4.get(0).setFile1Info(fileInfoService.getFileInfo(fileInfo));
				}
			}
			
			model.addAttribute("compActL4", compActL4);
			
			
			//======================  홈페이지 활동랭킹 =========================
			//오늘 날짜 기준 월,주의 데이터를 대상
			Date today = new Date();
			String year2 = AsaproUtils.getFormattedDate(today, "yyyy");
			String month2 = AsaproUtils.getFormattedDate(today, "MM");
			
			StartHpMngrSearch startHpMngrSearch = new StartHpMngrSearch();
			startHpMngrSearch.setSitePrefix(currentSite.getSitePrefix());
			startHpMngrSearch.setPageSize(5);
			startHpMngrSearch.setSearchYear(year2);
			startHpMngrSearch.setSearchMonth(month2);
			
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
			
			
			//======================  지식 TALK =========================
			BoardConfig boardConfigSearch2 = new BoardConfig();
			boardConfigSearch2.setBcId("idea_talk");
			boardConfigSearch2.setSitePrefix(currentSite.getSitePrefix());
			BoardConfig boardConfig = boardService.getBoardConfig(boardConfigSearch2);
			//게시판 카테고리
			if( StringUtils.isNotBlank(boardConfig.getBcCategory1()) ){
				model.addAttribute("bcCategory1CodeListIdeaTalk", codeService.getCodeList(boardConfig.getBcCategory1()));
			}
			
			BoardArticleSearch boardArticleIdeaTalkSearch = new BoardArticleSearch();
			boardArticleIdeaTalkSearch.setSitePrefix(currentSite.getSitePrefix());
			boardArticleIdeaTalkSearch.setDefaultSort("BA_REGDATE", "DESC");
			boardArticleIdeaTalkSearch.setBaNotice(null);	//공지글과 와 공지아닌글 모두 가져온다 
			boardArticleIdeaTalkSearch.setBaUse(true);	//게시하는 글만
			boardArticleIdeaTalkSearch.setNow(AsaproUtils.getFormattedDate(new Date()));
			boardArticleIdeaTalkSearch.fixBrokenSvByDefaultCharsets();
			boardArticleIdeaTalkSearch.setPageSize(4);
			boardArticleIdeaTalkSearch.setBcId("idea_talk");

			List<BoardArticle> ideaTalkList = boardService.getBoardArticleList(boardArticleIdeaTalkSearch);
			model.addAttribute("ideaTalkList", ideaTalkList);
			
			//======================  공지사항 =========================
			BoardConfig boardConfigSearch3 = new BoardConfig();
			boardConfigSearch3.setBcId("sct_news");
			boardConfigSearch3.setSitePrefix(currentSite.getSitePrefix());
			BoardConfig boardConfigNotice = boardService.getBoardConfig(boardConfigSearch3);
			//게시판 카테고리
			if( StringUtils.isNotBlank(boardConfigNotice.getBcCategory1()) ){
				model.addAttribute("bcCategory1CodeListNotice", codeService.getCodeList(boardConfigNotice.getBcCategory1()));
			}
			
			BoardArticleSearch boardArticleNoticeSearch = new BoardArticleSearch();
			boardArticleNoticeSearch.setSitePrefix(currentSite.getSitePrefix());
			boardArticleNoticeSearch.setDefaultSort("BA_REGDATE", "DESC");
			boardArticleNoticeSearch.setBaNotice(null);	//공지글과 와 공지아닌글 모두 가져온다 
			boardArticleNoticeSearch.setBaUse(true);	//게시하는 글만
			boardArticleNoticeSearch.setNow(AsaproUtils.getFormattedDate(new Date()));
			boardArticleNoticeSearch.fixBrokenSvByDefaultCharsets();
			boardArticleNoticeSearch.setPageSize(4);
			boardArticleNoticeSearch.setBcId("sct_news");

			List<BoardArticle> noticeList = boardService.getBoardArticleList(boardArticleNoticeSearch);
			model.addAttribute("noticeList", noticeList);
			
			//======================  입주공간  =========================
			//공간정보 목록 조회
			SpaceSearch spaceSearch = new SpaceSearch();
			spaceSearch.setArcCategory("move_space");
			spaceSearch.setSitePrefix(currentSite.getSitePrefix());
			spaceSearch.fixBrokenSvByDefaultCharsets();
			spaceSearch.setPaging(true);
			spaceSearch.setArcUse(true);
			spaceSearch.setPageSize(4);
			
			List<Space> spaceList = spaceService.getSpaceList(spaceSearch);
			model.addAttribute("spaceList", spaceList);
			
			//부서목록
			DepartmentSearch departmentSearch = new DepartmentSearch();
			departmentSearch.setPaging(false);
			departmentSearch.setDepUse(true);
			departmentSearch.setOrgId("university");
			List<Department> departmentList = organizationService.getDepartmentList(departmentSearch);
			model.addAttribute("departmentList", departmentList);
			
			//======================   공유공간  =========================
			RentalSearch rentalSearch = new RentalSearch();
			rentalSearch.setSitePrefix(currentSite.getSitePrefix());
			rentalSearch.fixBrokenSvByDefaultCharsets();
			rentalSearch.setPaging(true);
			rentalSearch.setPageSize(4);
			rentalSearch.setRentUse(true);
			
			List<Rental> rentalList = rentalService.getRentalList(rentalSearch);
			model.addAttribute("rentalList", rentalList);
			
		}else{
			//그외 다국어/기타 사이트 메인페이지 구현 
		}
		String theme = (String) request.getAttribute("theme");
		return "asapro/theme/" + theme + "/index/home";
	}
	
}