package egovframework.com.campustown.startHpMngr.service.impl;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import egovframework.com.campustown.startHpMngr.service.StartHpMngr;
import egovframework.com.campustown.startHpMngr.service.StartHpMngrMapper;
import egovframework.com.campustown.startHpMngr.service.StartHpMngrSearch;
import egovframework.com.campustown.startHpMngr.service.StartHpMngrService;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

/**
 * 창업팀 홈페이지 관리 서비스 구현
 * @author jaseo
 * @since 2020. 02. 11.
 */
@Service
public class StartHpMngrServiceImpl extends EgovAbstractServiceImpl implements StartHpMngrService{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(StartHpMngrServiceImpl.class);
	
	@Autowired
	private StartHpMngrMapper startHpMngrMapper;
	
	/**
	 * 창업팀 홈페이지 관리정보 중 회사 기본정보를 가져온다. (단일조회)
	 */
	@Override
	public StartHpMngr getStartHpMngrCompInfo(StartHpMngrSearch startHpMngrSearch) {
		return startHpMngrMapper.selectStartHpMngrCompInfo(startHpMngrSearch);
	}
	
	/**
	 * 창업팀 홈페이지 관리정보 리스트를 가져온다.
	 */
	@Override
	public List<StartHpMngr> getStartHpMngrList(StartHpMngrSearch startHpMngrSearch) {
		return startHpMngrMapper.selectStartHpMngrList(startHpMngrSearch);
		
	}
	
	/**
	 * 창업팀 홈페이지 관리 목록 수를 조회한다.
	 */
	@Override
	public int getStartHpMngrListTotal(StartHpMngrSearch startHpMngrSearch) {
		return startHpMngrMapper.selectStartHpMngrListTotal(startHpMngrSearch);
	}
	
	
	/**
	 * 창업 홈페이지 관리 추가폼 뷰를 등록한다.
	 * @param startHpMngrForm
	 * @return
	 */
	@Override
	public int insertStartHpCompMngr(StartHpMngr startHpMngr) {
		return startHpMngrMapper.insertStartHpCompMngr(startHpMngr);
	}
	
	/**
	 * 창업팀 홈페이지 관리 정보를 수정한다.
	 */
	@Override
	public int updateStartHpCompMngr(StartHpMngr startHpMngrForm) {
		return startHpMngrMapper.updateStartHpCompMngr(startHpMngrForm);
	}
	
	
	
	
	
	/**
	 * 창업활동지수를 등록한다.
	 */
	@Override
	public int insertActiveIdxInfo(StartHpMngr startHpMngr) {
		return startHpMngrMapper.insertActiveIdxInfo(startHpMngr);
		
	}
	/**
	 * 창업활동지수 리스트를 가져온다.
	 */
	@Override
	public List<StartHpMngr> getActiveIdxInfoList(StartHpMngrSearch startHpMngrSearch) {
		return startHpMngrMapper.selectActiveIdxInfoList(startHpMngrSearch);
	}
	
	/**
	 * 창업활동지수 테이블의 특정 회사의 max seq를 가져온다.
	 */
	@Override
	public int getCompIdxMaxSeq(StartHpMngrSearch startHpMngrSearch) {
		return startHpMngrMapper.selectCompIdxMaxSeq(startHpMngrSearch);
	}

	/**
	 * 	창업활동지수 테이블의 실시간으로 등록된 특정 데이타를 가져온다.
	 */
	@Override
	public StartHpMngr getActiveIdxInfo(StartHpMngrSearch startHpMngrSearch) {
		return startHpMngrMapper.selectActiveIdxInfo(startHpMngrSearch);
	}

	/**
	 * 창업활동지수의 수정사항을 반영한다.
	 */
	@Override
	public int updateActiveIdxInfo(StartHpMngr startHpMngr) {
		return startHpMngrMapper.updateActiveIdxInfo(startHpMngr);
	}

	/**
	 * 창업활동지수의 삭제사항을 반영한다.
	 */
	@Override
	public int deleteActiveIdxInfo(StartHpMngr startHpMngr) {
		return startHpMngrMapper.deleteActiveIdxInfo(startHpMngr);
	}
	
	
	/**
	 * 창업팀 임직원 중 대표자의 정보를 조회한다.
	 * @param startHpMngrSearch
	 * @return
	 */
	@Override
	public StartHpMngr getEmpRprsnInfo(StartHpMngrSearch startHpMngrSearch) {
		return startHpMngrMapper.selectEmpRprsnInfo(startHpMngrSearch);
	}
	
	
	
	/**
	 * 창업팀 임직원 중 대표자의 정보를 등록한다.
	 */
	@Override
	public int insertStartHpEmpMngr(StartHpMngr startHpMngrEmpForm) {
		return startHpMngrMapper.insertStartHpEmpMngr(startHpMngrEmpForm);
	}
	
	/**
	 * 창업팀 임직원 중 대표자의 정보를 수정한다.
	 */
	@Override
	public int updateStartHpCompMngrEmp(StartHpMngr startHpMngrEmpForm) {
		return startHpMngrMapper.updateStartHpCompMngrEmp(startHpMngrEmpForm);
	}
	
	/**
	 * 창업팀 임직원 정보를 조회한다. 
	 */
	@Override
	public List<StartHpMngr> getEmpssList(StartHpMngrSearch startHpMngrSearch) {
		return startHpMngrMapper.selectEmpssList(startHpMngrSearch);
	}

	/**
	 * 창업팀 대표자를 제외한 임직원 리스트 중 max Seq를 가져온다.
	 */
	@Override
	public int getCompEmpMaxSeq(StartHpMngrSearch startHpMngrSearch) {
		return startHpMngrMapper.selectCompEmpMaxSeq(startHpMngrSearch);
	}

	/**
	 * 창업팀 임직원 정보를 조회한다.(단건)
	 */
	@Override
	public StartHpMngr getEmpInfo(StartHpMngrSearch startHpMngrSearch) {
		return startHpMngrMapper.selectEmpInfo(startHpMngrSearch);
	}
	
	/**
	 * 창업팀 임직원 정보를 삭제한다.
	 */
	@Override
	public int deleteStartHpCompMngrEmp(StartHpMngr startHpMngrEmpss) {
		return startHpMngrMapper.deleteStartHpCompMngrEmp(startHpMngrEmpss);
	}
	

	/**
	 * 창업팀 현황 정보를 조회한다.(Summary, 회사 + 활동지수 + 임직원)
	 */
	@Override
	public List<StartHpMngr> getStartHpCompTeamSumList(StartHpMngrSearch startHpMngrSearch) {
		return startHpMngrMapper.selectStartHpCompTeamSumList(startHpMngrSearch);
	}

	/**
	 * 창업팀 현황 정보 수를 조회한다.(Summary, 회사 + 활동지수 + 임직원)
	 */
	@Override
	public int getStartHpCompTeamSumListTotal(StartHpMngrSearch startHpMngrSearch) {
		return startHpMngrMapper.selectStartHpCompTeamSumListTotal(startHpMngrSearch);
	}
	
	/**
	 * 창업팀 참여자 정보를 조회한다.(Summary, 창업팀 + 대학 + 임직원)
	 */
	@Override
	public List<StartHpMngr> getStartHpCompEmpssSumList(StartHpMngrSearch startHpMngrSearch) {
		return startHpMngrMapper.selectStartHpCompEmpssSumList(startHpMngrSearch);
	}
	
	/**
	 * 창업팀 참여자 정보 수를 조회한다.(Summary, 창업팀 + 대학 + 임직원)
	 */
	@Override
	public int getStartHpCompEmpssSumListTotal(StartHpMngrSearch startHpMngrSearch) {
		return startHpMngrMapper.selectStartHpCompEmpssSumListTotal(startHpMngrSearch);
	}
	
	/**
	 * 특정 대학 소속 창업팀 대표자 관련 정보를 조회한다.(특정대학(창업팀s) + 임직원(대표만))
	 */
	@Override
	public List<StartHpMngr> getEmpRprsnList(StartHpMngrSearch startHpMngrSearch) {
		return startHpMngrMapper.selectEmpRprsnList(startHpMngrSearch);
	}
	
	/**
	 * 특정 대학 소속 창업팀 대표자 관련 정보 수를 조회한다.(특정대학(창업팀s) + 임직원(대표만))
	 */
	@Override
	public int getEmpRprsnListTotal(StartHpMngrSearch startHpMngrSearch) {
		return startHpMngrMapper.selectEmpRprsnListTotal(startHpMngrSearch);
	}
	
	/**
	 * 창업활동지수 랭킹 목록을 조회한다.
	 */
	@Override
	public List<StartHpMngr> getStartHpCompActIdxList(StartHpMngrSearch startHpMngrSearch) {
		return startHpMngrMapper.selectStartHpCompActIdxList(startHpMngrSearch);
	}
	
	/**
	 * 창업활동지수 랭킹 목록수를 조회한다.
	 */
	@Override
	public int getStartHpCompActIdxListTotal(StartHpMngrSearch startHpMngrSearch) {
		return startHpMngrMapper.selectStartHpCompActIdxListTotal(startHpMngrSearch);
	}

	/**
	 * 창업팀 홈페이지 정보를 삭제한다.
	 */
	@Override
	public int deleteStartHpMngr(List<StartHpMngr> startHpList) {
		int result = 0;
		for(StartHpMngr startHp : startHpList){
			result += this.deleteStartHpMngr(startHp);
		}
		return result;
	}
	
	/**
	 * 창업팀 홈페이지 정보를 삭제한다.
	 */
	private int deleteStartHpMngr(StartHpMngr startHp) {
		int result = startHpMngrMapper.deleteStartHpMngr(startHp);
		
		return result;
	}

	
	/**
	 * 게시판 글의 게시연도 목록맵을 반환한다.
	 */
	@Override
	public List<Map<String, Object>> getYearListByBoard(StartHpMngrSearch startHpMngrSearch) {
		List<Map<String, Object>> yearList = startHpMngrMapper.selectYearListByBoard(startHpMngrSearch);
		return yearList;
	}

	/**
	 * 게시판 글의 게시월 목록맵을 반환한다.
	 */
	@Override
	public List<Map<String, Object>> getMonthListByBoard(StartHpMngrSearch startHpMngrSearch) {
		List<Map<String, Object>> monthList = startHpMngrMapper.selectMonthListByBoard(startHpMngrSearch);
		return monthList;
	}

	/**
	 * 홈페이지 활동랭킹 목록을 반환한다.
	 */
	@Override
	public List<StartHpMngr> getStartHomepageRankList(StartHpMngrSearch startHpMngrSearch) {
		List<StartHpMngr> startHomepageRankList = startHpMngrMapper.selectStartHomepageRankList(startHpMngrSearch);
		return startHomepageRankList;
	}
	/**
	 * 홈페이지 활동랭킹을 반환한다.
	 */
	@Override
	public StartHpMngr getStartHomepageRank(StartHpMngrSearch startHpMngrSearch) {
		return startHpMngrMapper.selectStartHomepageRank(startHpMngrSearch);
	}
	
	/**
	 * 창업팀 임직원 정보 등록 확인
	 */
	@Override
	public int getCompEmpInfo(StartHpMngrSearch startHpMngrSearch2) {
		return startHpMngrMapper.selectCompEmpInfo(startHpMngrSearch2);
	}
	
	
	/**
	 * 창업팀 기본정보 수정일 변경
	 */
	@Override
	public int updateCompUpdDt(StartHpMngrSearch startHpMngrSearch) {
		return startHpMngrMapper.updateCompUpdDt(startHpMngrSearch);
	}
	
	/**
	 * 임직원 대표정보 최초 등록시, 창업팀 기본정보 노출여부 'Y'로 수정
	 */
	@Override
	public int updateCompExpsr(StartHpMngrSearch startHpMngrSearch3) {
		return startHpMngrMapper.updateCompExpsr(startHpMngrSearch3);
	}
	
}
