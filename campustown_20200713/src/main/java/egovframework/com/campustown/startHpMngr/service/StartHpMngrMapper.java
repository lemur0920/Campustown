package egovframework.com.campustown.startHpMngr.service;

import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;


/**
 * 창업팀 홈페이지 관리 SQL 매퍼
 * @author jaseo
 * @since 2020. 02. 11.
 */
@Mapper
public interface StartHpMngrMapper {
	
	
	/**
	 * 창업팀 홈페이지 관리정보 중 회사 기본정보를 가져온다. (단일조회)
	 * @param startHpMngrSearch
	 * @return
	 */
	public StartHpMngr selectStartHpMngrCompInfo(StartHpMngrSearch startHpMngrSearch);

	/**
	 * 창업팀 홈페이지 관리정보 리스트를 가져온다.
	 * @param startHpMngrSearch
	 * @return
	 */
	public List<StartHpMngr> selectStartHpMngrList(StartHpMngrSearch startHpMngrSearch);

	
	/**
	 * 창업팀 홈페이지 관리 목록 수를 조회한다.
	 * @param startHpMngrSearch
	 * @return
	 */
	public int selectStartHpMngrListTotal(StartHpMngrSearch startHpMngrSearch);
	
	
	/**
	 * 창업 홈페이지 관리 추가폼 뷰를 등록한다.
	 * @param startHpMngr
	 * @return
	 */
	public int insertStartHpCompMngr(StartHpMngr startHpMngr);
	
	/**
	 * 창업활동지수를 등록한다.
	 * @param startHpMngr
	 * @return
	 */
	public int insertActiveIdxInfo(StartHpMngr startHpMngr);
	
	/**
	 * 창업활동지수 리스트를 실시간으로 조회해준다.
	 * @param startHpMngr
	 * @return
	 */
	public List<StartHpMngr> selectActiveIdxInfoList(StartHpMngrSearch startHpMngrSearch);
	
	
	/**
	 * 창업활동지수 테이블의 특정 회사의 max seq를 가져온다.
	 * @param startHpMngrSearch
	 * @return
	 */
	public int selectCompIdxMaxSeq(StartHpMngrSearch startHpMngrSearch);
	
	
	/**
	 * 창업활동지수 테이블의 실시간으로 등록된 특정 데이타를 가져온다.
	 * @param startHpMngrSearch
	 * @return
	 */
	public StartHpMngr selectActiveIdxInfo(StartHpMngrSearch startHpMngrSearch);
	
	
	/**
	 * 창업활동지수의 수정사항을 반영한다.
	 * @param startHpMngr
	 * @return
	 */
	public int updateActiveIdxInfo(StartHpMngr startHpMngr);
	
	
	/**
	 * 창업활동지수의 삭제사항을 반영한다.
	 * @param startHpMngr
	 * @return
	 */
	public int deleteActiveIdxInfo(StartHpMngr startHpMngr);

	/**
	 * 창업팀 홈페이지 관리 정보를 수정한다.
	 * @param startHpMngrForm
	 * @return
	 */
	public int updateStartHpCompMngr(StartHpMngr startHpMngrForm);
	
	/**
	 * 창업팀 임직원 중 대표자의 정보를 조회한다.
	 * @param startHpMngrSearch
	 * @return
	 */
	public StartHpMngr selectEmpRprsnInfo(StartHpMngrSearch startHpMngrSearch);
	

	/**
	 * 창업팀 임직원 중 대표자의 정보를 등록한다.
	 * @param startHpMngrEmpForm
	 * @return
	 */
	public int insertStartHpEmpMngr(StartHpMngr startHpMngrEmpForm);
	
	/**
	 * 창업팀 임직원 중 대표자의 정보를 수정한다.
	 * @param startHpMngrEmpForm
	 * @return
	 */
	public int updateStartHpCompMngrEmp(StartHpMngr startHpMngrEmpForm);
	
	/**
	 * 창업팀 임직원 정보를 조회한다.
	 * @param startHpMngrSearch
	 * @return
	 */
	public List<StartHpMngr> selectEmpssList(StartHpMngrSearch startHpMngrSearch);
	
	/**
	 * 창업팀 대표자를 제외한 임직원 리스트 중 max Seq를 가져온다.
	 * @param startHpMngrSearch
	 * @return
	 */
	public int selectCompEmpMaxSeq(StartHpMngrSearch startHpMngrSearch);
	
	/**
	 * 창업팀 임직원 정보를 조회한다.(단건)
	 * @param startHpMngrSearch
	 * @return
	 */
	public StartHpMngr selectEmpInfo(StartHpMngrSearch startHpMngrSearch);
	
	/**
	 * 창업팀 임직원 정보를 삭제한다.
	 * @param startHpMngrEmpss
	 * @return
	 */
	public int deleteStartHpCompMngrEmp(StartHpMngr startHpMngrEmpss);
	
	
	/**
	 * 창업팀 현황 정보를 조회한다.(Summary, 회사 + 활동지수 + 임직원)
	 * @param startHpMngrSearch
	 * @return
	 */
	public List<StartHpMngr> selectStartHpCompTeamSumList(StartHpMngrSearch startHpMngrSearch);
	
	/**
	 * 창업팀 현황 정보 수를 조회한다.(Summary, 회사 + 활동지수 + 임직원)
	 * @param startHpMngrSearch
	 * @return
	 */
	public int selectStartHpCompTeamSumListTotal(StartHpMngrSearch startHpMngrSearch);
	
	/**
	 * 창업팀 참여자 정보를 조회한다.(Summary, 창업팀 + 대학 + 임직원)
	 * @param startHpMngrSearch
	 * @return
	 */
	public List<StartHpMngr> selectStartHpCompEmpssSumList(StartHpMngrSearch startHpMngrSearch);
	
	/**
	 * 창업팀 참여자 정보 수를 조회한다.(Summary, 창업팀 + 대학 + 임직원)
	 * @param startHpMngrSearch
	 * @return
	 */
	public int selectStartHpCompEmpssSumListTotal(StartHpMngrSearch startHpMngrSearch);
	
	
	/**
	 * 특정 대학 소속 창업팀 대표자 관련 정보를 조회한다.(특정대학(창업팀s) + 임직원(대표만))
	 * @param startHpMngrSearch
	 * @return
	 */
	public List<StartHpMngr> selectEmpRprsnList(StartHpMngrSearch startHpMngrSearch);

	/**
	 * 특정 대학 소속 창업팀 대표자 관련 정보 수를 조회한다.(특정대학(창업팀s) + 임직원(대표만))
	 * @param startHpMngrSearch
	 * @return
	 */
	public int selectEmpRprsnListTotal(StartHpMngrSearch startHpMngrSearch);
	
	/**
	 * 창업활동지수 랭킹 목록을 조회한다.
	 * @param startHpMngrSearch
	 * @return
	 */
	public List<StartHpMngr> selectStartHpCompActIdxList(StartHpMngrSearch startHpMngrSearch);
	
	/**
	 * 창업활동지수 랭킹 목록 수를 조회한다.
	 * @param startHpMngrSearch
	 * @return
	 */
	public int selectStartHpCompActIdxListTotal(StartHpMngrSearch startHpMngrSearch);
	
	/**
	 * 창업팀 홈페이지 정보를 삭제한다.
	 * @param startHp
	 * @return
	 */
	public int deleteStartHpMngr(StartHpMngr startHp);
	
	/**
	 * 홈페이지 활동 랭킹 목록을 조회한다.
	 * @param startHpMngrSearch
	 * @return 홈페이지 활동 랭킹 목록
	 */
	public List<StartHpMngr> selectStartHomepageRankList(StartHpMngrSearch startHpMngrSearch);

	/**
	 * 게시판 글의 게시연도 목록맵을 반환한다.
	 * @param startHpMngrSearch
	 * @return 게시연도 목록맵
	 */
	public List<Map<String, Object>> selectYearListByBoard(StartHpMngrSearch startHpMngrSearch);

	/**
	 * 게시판 글의 게시월 목록맵을 반환한다.
	 * @param startHpMngrSearch
	 * @return 게시월 목록맵
	 */ 
	public List<Map<String, Object>> selectMonthListByBoard(StartHpMngrSearch startHpMngrSearch);

	/**
	 * 홈페이지 활동랭킹을 반환한다.
	 * @param startHpMngrSearch
	 * @return
	 */
	public StartHpMngr selectStartHomepageRank(StartHpMngrSearch startHpMngrSearch);
	
	
	/**
	 * 창업팀 임직원 정보 등록 확인
	 * @param startHpMngrSearch2
	 * @return
	 */
	public int selectCompEmpInfo(StartHpMngrSearch startHpMngrSearch2);
	
	/**
	 * 창업팀 기본정보 수정일 변경
	 * @param startHpMngrSearch
	 * @return
	 */
	public int updateCompUpdDt(StartHpMngrSearch startHpMngrSearch);
	
	/**
	 * 임직원 대표정보 최초 등록시, 창업팀 기본정보 노출여부 'Y'로 수정
	 * @param startHpMngrSearch3
	 * @return
	 */
	public int updateCompExpsr(StartHpMngrSearch startHpMngrSearch3);

}
