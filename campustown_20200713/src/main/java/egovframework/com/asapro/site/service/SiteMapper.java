/**
 * 
 */
package egovframework.com.asapro.site.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import egovframework.rte.psl.dataaccess.mapper.Mapper;


/**
 * 사이트 SQL 매퍼
 * @author yckim
 * @since 2018.09.11
 *
 */
@Mapper
public interface SiteMapper {
	
	/**
	 * 사이트 목록을 조회한다.
	 * @param siteSearch
	 * @return 사이트 목록
	 */
	public List<Site> selectSiteList(SiteSearch siteSearch);
	
	/**
	 * 사이트 목록 토탈을 조회한다.
	 * @param siteSearch
	 * @return 사이트 목록 토탈
	 */
	public int selectSiteListTotal(SiteSearch siteSearch);
	
	/**
	 * 사이트를 조회한다.
	 * @param site
	 * @return 사이트
	 */
	public Site selectSite(Site site);
	
	/**
	 * 사이트를 조회한다.
	 * @param siteId
	 * @return 사이트
	 */
	public Site selectSiteById(String siteId);
	
	/**
	 * 메인 사이트를 조회한다.
	 * @return 메인 사이트
	 */
	public Site selectMainSite();
	
	/**
	 * 사이트를 추가한다.
	 * @param site
	 * @return 추가결과
	 */
	public int insertSite(Site site);
	
	/**
	 * 사이트를 수정한다.
	 * @param site
	 * @return 수정 결과
	 */
	public int updateSite(Site site);
	
	/**
	 * 사이트를 삭제한다.
	 * @param siteId
	 * @return 삭제 결과
	 */
	public int deleteSite(String siteId);

	//============ 사이트 테이블 쿼리 ==============
	
	/**
	 * 사이트 아카이브 테이블을 생성한다.
	 * @param sitePrefix
	 * @return 생성 결과
	 */
	public int createSiteArchiveTable(String sitePrefix);
	
	/**
	 * 사이트 아카이브 카테고리 테이블을 생성한다.
	 * @param sitePrefix
	 * @return 생성결과
	 */
	public int createSiteArchiveCategoryTable(String sitePrefix);
	
	/**
	 * 사이트 아카이브 카테고리 매핑 테이블을 생성한다.
	 * @param sitePrefix
	 * @return
	 */
	public int createSiteArchiveCategoryRelTable(String sitePrefix);
	
	/**
	 * (mysql)사이트 아카이브 매핑 테이블 외래키를 추가한다.
	 * @param sitePrefix
	 * @return
	 */
	public int createSiteArchiveCategoryRelForeignKey(String sitePrefix);
	
	/**
	 * (oracle)사이트 아카이브 매핑 테이블 인덱스를 추가한다. 
	 * @param sitePrefix
	 * @return
	 */
	public int createSiteArchiveCategoryRelIndex1(String sitePrefix);

	/**
	 * (oracle)사이트 아카이브 매핑 테이블 인덱스를 추가한다.
	 * @param sitePrefix
	 * @return
	 */
	public int createSiteArchiveCategoryRelIndex2(String sitePrefix);
	
	/**
	 * 사이트 아카이브 태그 테이블을 생성한다.
	 * @param sitePrefix
	 * @return
	 */
	public int createSiteArchiveTagTable(String sitePrefix);
	
	/**
	 * 사이트 아카이브 태그 매핑 테이블을 생성한다.
	 * @param sitePrefix
	 * @return
	 */
	public int createSiteArchiveTagRelTable(String sitePrefix);
	
	/**
	 * (mysql)아카이브 태그 매핑 테이블 외래키를 추가한다.
	 * @param sitePrefix
	 * @return
	 */
	public int createSiteArchiveTagRelForeignKey(String sitePrefix);
	
	/**
	 * (oracle)사이트 아카이브 태그 테이블 인덱스를 추가한다. 
	 * @param sitePrefix
	 * @return
	 */
	public int createSiteArchiveTagRelIndex1(String sitePrefix);
	
	/**
	 * (oracle)사이트 아카이브 태그 테이블 인덱스를 추가한다.
	 * @param sitePrefix
	 * @return
	 */
	public int createSiteArchiveTagRelIndex2(String sitePrefix);
	
	/**
	 * 사이트 아카이브 미디어 매핑 테이블을 생성한다.
	 * @param sitePrefix
	 * @return
	 */
	public int createSiteArchiveMediaRelTable(String sitePrefix);
	
	/**
	 * (mysql)아카이브 미디어 매핑 테이블 외래키를 추가한다.
	 * @param sitePrefix
	 * @return
	 */
	public int createSiteArchiveMediaRelForeignKey(String sitePrefix);
	
	/**
	 * (oracle)아카이브 미디어 매핑 테이블 인덱스를 추가한다.
	 * @param sitePrefix
	 * @return
	 */
	public int createSiteArchiveMediaRelIndex1(String sitePrefix);
	
	/**
	 * (oracle)아카이브 미디어 매핑 테이블 인덱스를 추가한다.
	 * @param sitePrefix
	 * @return
	 */
	public int createSiteArchiveMediaRelIndex2(String sitePrefix);
	
	/**
	 * 사이트의 배너 테이블을 생성한다.
	 * @param upperCase
	 * @return 생성 결과
	 */
	public int createSiteBannerTable(String sitePrefix);
	
	/**
	 * 사이트 게시물 테이블을 생성한다.
	 * @param sitePrefix
	 * @return 
	 */
	public int createSiteBoardArticleTable(String sitePrefix);
	
	/**
	 * 사이트 게시판 설정 테이블을 생성한다.
	 * @param sitePrefix
	 * @return
	 */
	public int createSiteBoardConfigTable(String sitePrefix);
	
	/**
	 * 게시물 테이블 외래키를 추가한다.
	 * @param sitePrefix
	 * @return
	 */
	public int createSiteBoardArticleForeignKey(String sitePrefix);
	
	/**
	 * (oracle)사이트 게시물 테이블 인덱스를 추가한다.
	 * @param sitePrefix
	 * @return
	 */
	public int createSiteBoardArticleIndex1(String sitePrefix);
	
	/**
	 * (mysql)사이트 댓글 테이블을 생성한다.
	 * @param sitePrefix
	 * @return
	 */
	public int createSiteCommentTable(String sitePrefix);
	
	/**
	 * 사이트 옵션 테이블을 생성한다.
	 * @param sitePrefix
	 * @return
	 */
	public int createSiteConfigOptionTable(String sitePrefix);
	
	/**
	 * (oracle)사이트 옵션 데이터를 복사한다.
	 * @param sitePrefix
	 * @return
	 */
	public int copySiteConfigOptionData(String sitePrefix);
	
	/**
	 * 사이트 메뉴 콘텐츠 테이블을 생성한다.
	 * @param sitePrefix
	 * @return
	 */
	public int createSiteContentTable(String sitePrefix);
	
	/**
	 * (oracle) 사이트 콘텐츠 인덱스를 생성한다.
	 * @param sitePrefix
	 * @return
	 */
	public int createSiteContentIndex(String sitePrefix);
	
	/**
	 * 사이트 피드설정 테이블을 생성한다.
	 * @param sitePrefix
	 * @return
	 */
	public int createSiteFeedConfigTable(String sitePrefix);
	
	/**
	 * 사이트 파일정보 테이블을 생성한다.
	 * @param sitePrefix
	 * @return
	 */
	public int createSiteFileInfoTable(String sitePrefix);
	
	/**
	 * 사이트 메뉴테이블을 생성한다.
	 * @param sitePrefix
	 * @return
	 */
	public int createSiteMenuTable(String sitePrefix);
	
	/**
	 * (oracle)사이트 메뉴테이블 인덱스를 생성한다.
	 * @param sitePrefix
	 * @return
	 */
	public int createSiteMenuIndex(String sitePrefix);
	
	/**
	 * 사이트 포스트 테이블을 생성한다.
	 * @param sitePrefix
	 * @return
	 */
	public int createSitePostTable(String sitePrefix);
	
	/**
	 * 사이트 포스트 테이블 외래키를 추가한다.
	 * @param sitePrefix
	 * @return
	 */
	public int createSitePostForeignKey(String sitePrefix);
	
	/**
	 * (oracle)사이트 포스트 테이블 인덱스를 생성한다.
	 * @param sitePrefix
	 * @return
	 */
	public int createSitePostIndex1(String sitePrefix);
	
	/**
	 * 사이트 QNA 테이블을 생성한다.
	 * @param sitePrefix
	 * @return
	 */
	public int createSiteQnaTable(String sitePrefix);	
	
	/**
	 * 사이트 만족도 조사 테이블을 생성한다.
	 * @param sitePrefix
	 * @return
	 */
	public int createSiteSatisfactionTable(String sitePrefix);
	
	/**
	 * 사이트 만족도 조사 테이브렝 외래키를 추가한다.
	 * @param sitePrefix
	 * @return
	 */
	public int createSiteSatisfactionForeignKey(String sitePrefix);
	
	/**
	 * 사이트 일정 이벤트 테이블을 생성한다.
	 * @param sitePrefix
	 * @return 일정 이벤트 테이블
	 */
	public int createSiteScheduleEventTable(String sitePrefix);
	
	/**
	 * 사이트 일정 휴일/공휴일 테이블을 생성한다. 
	 * @param sitePrefix
	 * @return
	 */
	public int createSiteScheduleHolidayTable(String sitePrefix);
	
	/**
	 * 사이트 브라우저 통계 테이블을 생성한다.
	 * @param sitePrefix
	 * @return
	 */
	public int createSiteStatTable(String sitePrefix);
	
	/**
	 * 사이트 텍스트 옵션 테이블을 생성한다.
	 * @param sitePrefix
	 * @return
	 */
	public int createSiteTextDataTable(String sitePrefix);
	
	/**
	 * 사이트 텍스트 데이터 복사 
	 * @param sitePrefix
	 * @return
	 */
	public int copySiteTextData(String sitePrefix);
	
	//copy data
	
	/**
	 * 테이블 데이터를 카피한다.
	 * @param originalTable
	 * @param copyTable
	 */
	public void copyTableData(@Param("originalTable") String originalTable, @Param("copyTable") String copyTable);
	
	/**
	 * 투표 테이블을 생성한다.
	 * @param sitePrefix
	 * @return
	 */
	public int createSitePollTable(String sitePrefix);
	
	/**
	 * 투표 보기문항 테이블을 생성한다.
	 * @param sitePrefix
	 * @return
	 */
	public int createSitePollAnswerTable(String sitePrefix);
	
	/**
	 * 투표 참여데이터 테이블을 생성한다.
	 * @param sitePrefix
	 * @return
	 */
	public int createSitePollVoteTable(String sitePrefix);
	
	/**
	 * (mysql)투표 보기문항 테이블 FK제약조건을 생성한다.
	 * @param sitePrefix
	 * @return
	 */
	public int createSitePollAnswerForeignKey(String sitePrefix);
	
	/**
	 * (mysql)투표 참여데이터 테이블 FK제약조건을 생성한다.
	 * @param sitePrefix
	 * @return
	 */
	public int createSitePollVoteForeignKey(String sitePrefix);

	/**
	 * (oracle) 시퀀스를 생성한다.
	 * @param seqName
	 * @param startSeq
	 * @return
	 */
	public void createSiteSequence(@Param("seqName") String seqName, @Param("startSeq") Integer startSeq);

	/**
	 * 대관/대여 테이블을 생성한다.
	 * @param sitePrefix
	 * @return
	 */
	public int createSiteRentalTable(String sitePrefix);

	/**
	 * 대관/대여 예약신청정보 테이블을 생성한다.
	 * @param sitePrefix
	 * @return
	 */
	public int createSiteRentalReservationTable(String sitePrefix);

	/**
	 * 대관/대여 대관시간 막음처리정보 테이블을 생성한다.
	 * @param sitePrefix
	 * @return
	 */
	public int createSiteRentalCloseTimeTable(String sitePrefix);
	
	/**
	 * 관람 예약신청저보 테이블을 생성한다.
	 * @param sitePrefix
	 * @return
	 */
	public int createSiteViewingReservationTable(String sitePrefix);

	/**
	 * 교육 신청정보 테이블을 생성한다.
	 * @param sitePrefix
	 * @return
	 */
	public int createSiteEducationReservationTable(String sitePrefix);

	/**
	 * 자원봉사 신청정보 테이블을 생성한다.
	 * @param sitePrefix
	 * @return
	 */
	public int createSiteVolunteerReservationTable(String sitePrefix);

	/**
	 * 사이트 공간정보 테이블을 생성한다.
	 * @param sitePrefix
	 * @return
	 */
	public int createSiteSpaceTable(String sitePrefix);
	
	/**
	 * 사이트 공간정보 테이블 외래키를 추가한다.
	 * @param sitePrefix
	 * @return
	 */
	public int createSiteSpaceForeignKey(String sitePrefix);
	
	/**
	 * (oracle)사이트 공간정보 테이블 인덱스를 생성한다.
	 * @param sitePrefix
	 * @return
	 */
	public int createSiteSpaceIndex1(String sitePrefix);
	
	/**
	 * 사이트 연구자료 테이블을 생성한다.
	 * @param sitePrefix
	 * @return
	 */
	public int createSiteResearchTable(String sitePrefix);
	
	/**
	 * 사이트 연구자료 테이블 외래키를 추가한다.
	 * @param sitePrefix
	 * @return
	 */
	public int createSiteResearchForeignKey(String sitePrefix);
	
	/**
	 * (oracle)사이트 연구자료 테이블 인덱스를 생성한다.
	 * @param sitePrefix
	 * @return
	 */
	public int createSiteResearchIndex1(String sitePrefix);
	
	/**
	 * 사이트 광고자료 테이블을 생성한다.
	 * @param sitePrefix
	 * @return
	 */
	public int createSiteAdvertisingTable(String sitePrefix);
	
	/**
	 * 사이트 광고자료 테이블 외래키를 추가한다.
	 * @param sitePrefix
	 * @return
	 */
	public int createSiteAdvertisingForeignKey(String sitePrefix);
	
	/**
	 * (oracle)사이트 광고자료 테이블 인덱스를 생성한다.
	 * @param sitePrefix
	 * @return
	 */
	public int createSiteAdvertisingIndex1(String sitePrefix);

	/**
	 * 사이트 정책자료 테이블을 생성한다.
	 * @param sitePrefix
	 * @return
	 */
	public int createSitePolicyTable(String sitePrefix);
	
	/**
	 * 사이트 정책자료 테이블 외래키를 추가한다.
	 * @param sitePrefix
	 * @return
	 */
	public int createSitePolicyForeignKey(String sitePrefix);
	
	/**
	 * (oracle)사이트 정책자료 테이블 인덱스를 생성한다.
	 * @param sitePrefix
	 * @return
	 */
	public int createSitePolicyIndex1(String sitePrefix);
	

	
}
