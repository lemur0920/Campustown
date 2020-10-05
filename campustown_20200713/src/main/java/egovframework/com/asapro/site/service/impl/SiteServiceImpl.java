/**
 * 
 */
package egovframework.com.asapro.site.service.impl;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import egovframework.com.cmm.service.EgovProperties;
import egovframework.com.asapro.banner.service.Banner;
import egovframework.com.asapro.fileinfo.service.FileInfo;
import egovframework.com.asapro.fileinfo.service.FileInfoService;
import egovframework.com.asapro.member.admin.service.AdminMemberMapper;
import egovframework.com.asapro.menu.service.Menu;
import egovframework.com.asapro.menu.service.MenuService;
import egovframework.com.asapro.site.service.Site;
import egovframework.com.asapro.site.service.SiteMapper;
import egovframework.com.asapro.site.service.SiteSearch;
import egovframework.com.asapro.site.service.SiteService;
import egovframework.com.asapro.support.Constant;
import egovframework.com.asapro.support.exception.AsaproException;
import egovframework.com.asapro.support.util.AsaproUtils;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

/**
 * 사이트 서비스 구현
 * @author yckim
 * @since 2018.09.11
 *
 */
@Service
public class SiteServiceImpl extends EgovAbstractServiceImpl implements SiteService{
	
	@Autowired
	private MenuService menuService;
	
	@Autowired
	private SiteMapper siteMapper;
	
	@Autowired
	private FileInfoService fileInfoService;
	
	@Autowired
	private AdminMemberMapper adminMemberMapper;
	
	/**
	 * 사이트 목록을 반환한다.
	 */
	@Override
	public List<Site> getSiteList(SiteSearch siteSearch) {
		return siteMapper.selectSiteList(siteSearch);
	}

	/**
	 * 사이트 목록 수를 반환한다.
	 */
	@Override
	public int getSiteListTotal(SiteSearch siteSearch) {
		return siteMapper.selectSiteListTotal(siteSearch);
	}
	
	/**
	 * 사이트를 조회한다.
	 */
	public Site getSite(Site site){
		return siteMapper.selectSite(site);
	}
	
	/**
	 * 사이트를 조회한다.
	 */
	public Site getSite(String siteId){
		return siteMapper.selectSiteById(siteId);
	}
	
	/**
	 * 사이트를 추가한다.
	 */
	@Override
	public int insertSite(Site site) {
		site.setSiteDomain(site.getSiteDomain().replace(" ", ""));
		int result = siteMapper.insertSite(site);
		
		//인덱스 메뉴 - 없으면 사이트 접속이 안됨.
		Menu indexMenu = new Menu();
		indexMenu.setSitePrefix(site.getSitePrefix());
		indexMenu.setMenuId("home");
		indexMenu.setMenuName("Home");
		indexMenu.setMenuDepth(1);
		indexMenu.setMenuType("program");
		indexMenu.setMenuGnbType("text");
		indexMenu.setMenuGnbExtOn("");
		indexMenu.setMenuGnbExtOff("");
		indexMenu.setMenuSnbType("text");
		indexMenu.setMenuSnbExtOn("");
		indexMenu.setMenuSnbExtOff("");
		indexMenu.setMenuTitleType("text");
		indexMenu.setMenuTitleExt("");
		indexMenu.setMenuTitleSubText("");
		indexMenu.setMenuLink(Constant.APP_PATH + "/" + site.getSiteId() + "/home");
		indexMenu.setMenuNewWin(false);
		indexMenu.setMenuParent(null);
		indexMenu.setMenuOrder(0);
		indexMenu.setMenuHeader("header.jsp");
		indexMenu.setMenuTemplate("content.jsp");
		indexMenu.setMenuFooter("footer.jsp");
		indexMenu.setMenuStatus("public");
		indexMenu.setMenuManager("");
		indexMenu.setMenuDepartment("");
		indexMenu.setMenuPhone("");
		indexMenu.setMenuEmail("");
		indexMenu.setMenuEtc("");
		indexMenu.setMenuRegDate(new Date());
		indexMenu.setMenuUseSatisfaction(false);
		indexMenu.setMenuLastModified(new Date());
		indexMenu.setMenuLocation("Home");
		indexMenu.setSitePrefix(site.getSitePrefix());
		menuService.insertMenu(indexMenu);
		
		String dbType = EgovProperties.getProperty("Globals.DbType");
		/**
		 * 사이트별 별도 테이블을 가져가야 하는 경우를 위해 테이블을생성한다.
		 * - 데이터양이 많거나 테이블이 구분되는것이 효과적인경우 고려
		 * - DB 타입에 따라 생성퀄리가 다르므로 분기하여 처리
		 */
		
		if( "mysql".equals(dbType) ){
			//개별 사이트용 테이블 생성 = 순서 안맞으면 테이블 없어서 SQL에러날수있음.
			
			//파일
			siteMapper.createSiteFileInfoTable(site.getSitePrefix());
			
			//아카이브
			//siteMapper.createSiteArchiveTable(site.getSitePrefix());
			//siteMapper.createSiteArchiveCategoryTable(site.getSitePrefix());
			//siteMapper.createSiteArchiveCategoryRelTable(site.getSitePrefix());
			//siteMapper.createSiteArchiveCategoryRelForeignKey(site.getSitePrefix());
			//siteMapper.createSiteArchiveTagTable(site.getSitePrefix());
			//siteMapper.createSiteArchiveTagRelTable(site.getSitePrefix());
			//siteMapper.createSiteArchiveTagRelForeignKey(site.getSitePrefix());
			//siteMapper.createSiteArchiveMediaRelTable(site.getSitePrefix());
			//siteMapper.createSiteArchiveMediaRelForeignKey(site.getSitePrefix());
			
			//배너
			//siteMapper.createSiteBannerTable(site.getSitePrefix());
			
			//게시판
			siteMapper.createSiteBoardConfigTable(site.getSitePrefix());
			siteMapper.createSiteBoardArticleTable(site.getSitePrefix());
			siteMapper.createSiteBoardArticleForeignKey(site.getSitePrefix());
			
			//대관/대여
			siteMapper.createSiteRentalTable(site.getSitePrefix());
			siteMapper.createSiteRentalReservationTable(site.getSitePrefix());
			
			//댓글
			//siteMapper.createSiteCommentTable(site.getSitePrefix());
			
			//설정
			//테이블생성 하지않고 구분자처리로 변경혀 데이터 카피 인서트
			//siteMapper.createSiteConfigOptionTable(site.getSitePrefix());
			siteMapper.copySiteConfigOptionData(site.getSitePrefix());
			
			//콘텐츠
			//siteMapper.createSiteContentTable(site.getSitePrefix());
			
			//피드
			//siteMapper.createSiteFeedConfigTable(site.getSitePrefix());
			
			//메뉴
			//siteMapper.createSiteMenuTable(site.getSitePrefix());
			
			//포스트
			//siteMapper.createSitePostTable(site.getSitePrefix());
			//siteMapper.createSitePostForeignKey(site.getSitePrefix());
			
			//QNA
			//siteMapper.createSiteQnaTable(site.getSitePrefix());
			
			//만족도
			//siteMapper.createSiteSatisfactionTable(site.getSitePrefix());
			//siteMapper.createSiteSatisfactionForeignKey(site.getSitePrefix());
			
			//일정
			//siteMapper.createSiteScheduleEventTable(site.getSitePrefix());
			//siteMapper.createSiteScheduleHolidayTable(site.getSitePrefix());
			
			//휴일공휴일 입력
			//mysql의 CREATE TABLE IF NOT EXISTS ${value}_SCHEDULE_HOLIDAY SELECT * FROM ASA_SCHEDULE_HOLIDAY 를 사용해서 만들면
			//pk 제약조건이 걸리지 않아서 따로 처리함
			//siteMapper.copyTableData("ASA_SCHEDULE_HOLIDAY", site.getSitePrefix() + "_SCHEDULE_HOLIDAY");
			//siteMapper.createSiteStatTable(site.getSitePrefix());
			//siteMapper.createSiteTextDataTable(site.getSitePrefix());
			
			//투표
			//siteMapper.createSitePollTable(site.getSitePrefix());
			//siteMapper.createSitePollAnswerTable(site.getSitePrefix());
			//siteMapper.createSitePollVoteTable(site.getSitePrefix());
			//siteMapper.createSitePollAnswerForeignKey(site.getSitePrefix());
			//siteMapper.createSitePollVoteForeignKey(site.getSitePrefix());
		} else if( "oracle".equals(dbType) ){
			
			//개별 사이트용 테이블 생성 = 순서 안맞으면 테이블 없어서 SQL에러날수있음.
			//파일
			siteMapper.createSiteFileInfoTable(site.getSitePrefix());
			
			//아카이브
			//siteMapper.createSiteArchiveTable(site.getSitePrefix());
			
			//siteMapper.createSiteArchiveCategoryTable(site.getSitePrefix());
			//siteMapper.createSiteArchiveCategoryRelTable(site.getSitePrefix());
			////siteMapper.createSiteArchiveCategoryRelForeignKey(site.getSitePrefix()); //mysql 전용이라 패스
			//siteMapper.createSiteArchiveCategoryRelIndex1(site.getSitePrefix());//오라클 추가
			//siteMapper.createSiteArchiveCategoryRelIndex2(site.getSitePrefix());//오라클 추가
			
			//siteMapper.createSiteArchiveTagTable(site.getSitePrefix());
			//siteMapper.createSiteArchiveTagRelTable(site.getSitePrefix());
			////siteMapper.createSiteArchiveTagRelForeignKey(site.getSitePrefix()); //mysql 전용이라 패스
			//siteMapper.createSiteArchiveTagRelIndex1(site.getSitePrefix());//오라클 추가
			//siteMapper.createSiteArchiveTagRelIndex2(site.getSitePrefix());//오라클 추가
			
			//siteMapper.createSiteArchiveMediaRelTable(site.getSitePrefix());
			////siteMapper.createSiteArchiveMediaRelForeignKey(site.getSitePrefix()); //mysql 전용이라 패스
			//siteMapper.createSiteArchiveMediaRelIndex1(site.getSitePrefix());//오라클 추가
			//siteMapper.createSiteArchiveMediaRelIndex2(site.getSitePrefix());//오라클 추가
			
			//배너
			//siteMapper.createSiteBannerTable(site.getSitePrefix());
			
			//게시판
			siteMapper.createSiteBoardConfigTable(site.getSitePrefix());
			siteMapper.createSiteBoardArticleTable(site.getSitePrefix());
			//siteMapper.createSiteBoardArticleForeignKey(site.getSitePrefix()); //mysql 용이라 패스
			siteMapper.createSiteBoardArticleIndex1(site.getSitePrefix());//오라클 추가
			
			//대관/대여
			siteMapper.createSiteRentalTable(site.getSitePrefix());
			siteMapper.createSiteRentalReservationTable(site.getSitePrefix());
			siteMapper.createSiteRentalCloseTimeTable(site.getSitePrefix());
			
			//관람
			siteMapper.createSiteViewingReservationTable(site.getSitePrefix());
			
			//교육
			siteMapper.createSiteEducationReservationTable(site.getSitePrefix());
			
			//자원봉사
			siteMapper.createSiteVolunteerReservationTable(site.getSitePrefix());
			
			//아카이브
			siteMapper.createSiteArchiveCategoryTable(site.getSitePrefix());
			siteMapper.createSiteArchiveTable(site.getSitePrefix());
			
			//공간정보
			siteMapper.createSiteSpaceTable(site.getSitePrefix());
			//siteMapper.createSiteSpaceForeignKey(site.getSitePrefix());//mysql 전용이라 패스
			//siteMapper.createSiteSpaceIndex1(site.getSitePrefix());//오라클 추가
			
			//연구자료
			siteMapper.createSiteResearchTable(site.getSitePrefix());
			//siteMapper.createSiteResearchForeignKey(site.getSitePrefix());//mysql 전용이라 패스
			//siteMapper.createSiteResearchIndex1(site.getSitePrefix());//오라클 추가
			
			//광고자료
			siteMapper.createSiteAdvertisingTable(site.getSitePrefix());
			//siteMapper.createSiteAdvertisingForeignKey(site.getSitePrefix());//mysql 전용이라 패스
			//siteMapper.createSiteAdvertisingIndex1(site.getSitePrefix());//오라클 추가

			//정책자료
			siteMapper.createSitePolicyTable(site.getSitePrefix());
			//siteMapper.createSitePolicyForeignKey(site.getSitePrefix());//mysql 전용이라 패스
			//siteMapper.createSitePolicyIndex1(site.getSitePrefix());//오라클 추가
			
			//댓글
			siteMapper.createSiteCommentTable(site.getSitePrefix());
			
			//설정
			//테이블생성 하지않고 구분자처리로 변경혀 데이터 카피 인서트
			//siteMapper.createSiteConfigOptionTable(site.getSitePrefix());
			siteMapper.copySiteConfigOptionData(site.getSitePrefix());
			
			//콘텐츠
			//siteMapper.createSiteContentTable(site.getSitePrefix());
			//siteMapper.createSiteContentIndex(site.getSitePrefix());//오라클 추가
			
			//피드
			//siteMapper.createSiteFeedConfigTable(site.getSitePrefix());
			
			//메뉴
			//siteMapper.createSiteMenuTable(site.getSitePrefix());
			//siteMapper.createSiteMenuIndex(site.getSitePrefix());
			
			//포스트
			//siteMapper.createSitePostTable(site.getSitePrefix());
			////siteMapper.createSitePostForeignKey(site.getSitePrefix());//mysql 전용이라 패스
			////siteMapper.createSitePostIndex1(site.getSitePrefix());//오라클 추가
			
			//QNA
			//siteMapper.createSiteQnaTable(site.getSitePrefix());
			
			//만족도
			//siteMapper.createSiteSatisfactionTable(site.getSitePrefix());
			////siteMapper.createSiteSatisfactionForeignKey(site.getSitePrefix());//mysql 전용이라 패스
			
			//일정
			//siteMapper.createSiteScheduleEventTable(site.getSitePrefix());
			//siteMapper.createSiteScheduleHolidayTable(site.getSitePrefix());
			//휴일공휴일 입력
			////mysql의 CREATE TABLE IF NOT EXISTS ${value}_SCHEDULE_HOLIDAY SELECT * FROM ASA_SCHEDULE_HOLIDAY 를 사용해서 만들면
			////pk 제약조건이 걸리지 않아서 따로 처리함
			//siteMapper.copyTableData("ASA_SCHEDULE_HOLIDAY", site.getSitePrefix() + "_SCHEDULE_HOLIDAY");
			//siteMapper.createSiteStatTable(site.getSitePrefix());
			//siteMapper.createSiteTextDataTable(site.getSitePrefix());
			//siteMapper.copySiteTextData(site.getSitePrefix());
			
			//투표
			//siteMapper.createSitePollTable(site.getSitePrefix());
			//siteMapper.createSitePollAnswerTable(site.getSitePrefix());
			//siteMapper.createSitePollVoteTable(site.getSitePrefix());
			////siteMapper.createSitePollAnswerForeignKey(site.getSitePrefix());//mysql 전용이라 패스
			////siteMapper.createSitePollVoteForeignKey(site.getSitePrefix());//mysql 전용이라 패스
			
			//시퀀스생성
			//siteMapper.createSiteSequence(site.getSitePrefix() + "_ARCHIVE_SEQ", 1);
			//siteMapper.createSiteSequence(site.getSitePrefix() + "_COMMONE_SEQ", 100);
			siteMapper.createSiteSequence(site.getSitePrefix() + "_BOARD_SEQ", 1);
			siteMapper.createSiteSequence(site.getSitePrefix() + "_FILEINFO_SEQ", 1);
			siteMapper.createSiteSequence(site.getSitePrefix() + "_RENTAL_SEQ", 1);
			siteMapper.createSiteSequence(site.getSitePrefix() + "_VOLUNTEER_SEQ", 1);
			siteMapper.createSiteSequence(site.getSitePrefix() + "_VIEWING_SEQ", 1);
			siteMapper.createSiteSequence(site.getSitePrefix() + "_EDUCATION_SEQ", 1);
			siteMapper.createSiteSequence(site.getSitePrefix() + "_ARCHIVE_SEQ", 1);
			//siteMapper.createSiteSequence(site.getSitePrefix() + "_STAT_SEQ", 1);
			siteMapper.createSiteSequence(site.getSitePrefix() + "_COMMENT_SEQ", 1);
		} else {
			throw new AsaproException("Not implemented yet.");
		}
		
		return result;
	}

	/**
	 * 사이트를 수정한다.
	 */
	@Override
	public int updateSite(Site site) {
		if( StringUtils.isNotBlank(site.getSiteDomain()) ){
			site.setSiteDomain( StringUtils.deleteWhitespace(site.getSiteDomain()) );
		} else {
			site.setSiteDomain("");
		}
		return siteMapper.updateSite(site);
	}

	/**
	 * 사이트를 삭제한다.
	 */
	@Override
	public int deleteSite(String siteId) {
		//메인사이트인 경우 삭제할 수 없다.
		Site fromDB = siteMapper.selectSiteById(siteId);

		if( fromDB != null && fromDB.isSiteMain() ){
			throw new AsaproException("WARNING : WE CAN NOT DELETE MAIN SITE!!");
		}
		
		//관리자 사이트별 역할 릴레이션 삭제
		adminMemberMapper.deleteAdminSiteRoleRelBySitePrefix(siteId.toUpperCase());
				
		int result = siteMapper.deleteSite(siteId);
		
		if(result > 0){
			
			//대표이미지 삭제
			if(fromDB.getSiteLogo() != null && fromDB.getSiteLogo().getFileId() > 0){
				FileInfo fileInfoImage = fromDB.getSiteLogo();
				fileInfoImage.setSitePrefix(Constant.MAIN_SITE_PREFIX);
				fileInfoService.deleteFileInfo(fileInfoImage);
			}
		}
		return result;
	}
	
	/**
	 * 사이트를 삭제한다.
	 */
	@Override
	public int deleteSite(String[] siteIds) {
		int result = 0;
		for( String siteId : siteIds ){
			result += this.deleteSite(siteId);
		}
		return result;
	}

	/**
	 * 사이트를 찾는다.
	 */
	@Override
	public Site detectCurrentSite(HttpServletRequest request) {
		String requestUri = AsaproUtils.getFixedRequestUri(request);
		
		List<Site> list = this.getSiteList(null);
		
		String[] temp = requestUri.split("/");
		String siteId = "";
		if(temp.length > 3){
			if(AsaproUtils.isAdminPath(requestUri) ){
				siteId = temp[1].toLowerCase();
			}else{
				siteId = temp[2].toLowerCase();
			}
		}

		if( siteId.equals(Constant.MAIN_SITE_DISPLAY_ID) ){
			for( Site site : list ){
				if( site.isSiteMain() ){
					return site;
				}
			}
		} else {
			for( Site site : list ){
				if( siteId.equals(site.getSiteId()) ){
					return site;
				}
			}
		}	
		
		//사이트 못찾으면 메인 사이트 반환
		for( Site site : list ){
			if( site.isSiteMain() ){
				return site;
			}
		}
		
		return null;
	}

	/**
	 * 메인사이트를 조회한다.
	 */
	@Override
	public Site getMainSite() {
		Site fromDB = siteMapper.selectMainSite();
		return fromDB;
	}

}