package egovframework.com.campustown.univHpMngr.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import egovframework.com.asapro.organization.service.Department;
import egovframework.com.campustown.startHpMngr.service.StartHpMngr;
import egovframework.com.campustown.startHpMngr.service.StartHpMngrMapper;
import egovframework.com.campustown.startHpMngr.service.StartHpMngrService;
import egovframework.com.campustown.univHpMngr.service.UnivHpMngr;
import egovframework.com.campustown.univHpMngr.service.UnivHpMngrMapper;
import egovframework.com.campustown.univHpMngr.service.UnivHpMngrSearch;
import egovframework.com.campustown.univHpMngr.service.UnivHpMngrService;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

/**
 * 대학 홈페이지 관리 서비스 구현
 * @author jaseo
 * @since 2020. 02. 11.
 */
@Service
public class UnivHpMngrServiceImpl extends EgovAbstractServiceImpl implements UnivHpMngrService{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(UnivHpMngrServiceImpl.class);
	
	@Autowired
	private UnivHpMngrMapper univHpMngrMapper;
	
	
	
	/**
	 * 대학 홈페이지 관리 목록을 조회한다.
	 * @param univHpMngrSearch
	 * @return
	 */
	@Override
	public List<UnivHpMngr> getUnivHpMngrList(UnivHpMngrSearch univHpMngrSearch) {
		return univHpMngrMapper.selectUnivHpMngrList(univHpMngrSearch);
	}

	
	/**
	 * 대학 홈페이지 관리 목록 수를 조회한다.
	 * @param univHpMngrSearch
	 * @return
	 */
	@Override
	public int getUnivHpMngrListTotal(UnivHpMngrSearch univHpMngrSearch) {
		return univHpMngrMapper.selectUnivHpMngrListTotal(univHpMngrSearch);
	}

	/**
	 * 대학 홈페이지 관리 정보를 등록한다.
	 * @param univHpMngrForm
	 * @return
	 */
	@Override
	public int insertUnivHpMngr(UnivHpMngr univHpMngr) {
		return univHpMngrMapper.insertUnivHpMngr(univHpMngr);
	}

	
	/**
	 * 대학 홈페이지 관리 정보를 가져온다. (단일조회)
	 */
	@Override
	public UnivHpMngr getUnivHpMngrInfo(UnivHpMngrSearch univHpMngrSearch) {
		return univHpMngrMapper.selectUnivHpMngrInfo(univHpMngrSearch);
	}

	/**
	 * 대학 홈페이지 관리 정보를 수정한다.
	 */
	@Override
	public int updateUnivHpMngr(UnivHpMngr univHpMngrForm) {
		// TODO Auto-generated method stub
		return univHpMngrMapper.updateUnivHpMngr(univHpMngrForm);
	}

	/**
	 * 대학 홈페이지 정보를 삭제한다. 
	 */
	@Override
	public int deleteUnivHpMngr(List<UnivHpMngr> univHpList) {
		int result = 0;
		for(UnivHpMngr univHp : univHpList){
			result += this.deleteUnivHpMngr(univHp);
		}
		return result;
	}

	/**
	 * 대학 홈페이지 정보를 삭제한다. 
	 */
	private int deleteUnivHpMngr(UnivHpMngr univHp) {
		int result = univHpMngrMapper.deleteUnivHpMngr(univHp);
		
		return result;
	}

	/**
	 * 캠퍼스타운명 변경에 따른 부서명 수정
	 */
	@Override
	public int updateDepNm(Department department) {
		int result = univHpMngrMapper.updateDepNm(department);
		
		return result;
	}
	
}
