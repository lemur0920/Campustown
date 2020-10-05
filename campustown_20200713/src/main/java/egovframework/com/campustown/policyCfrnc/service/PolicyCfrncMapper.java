package egovframework.com.campustown.policyCfrnc.service;

import java.util.List;

import egovframework.com.asapro.fileinfo.service.FileInfo;
import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * 정책협의회 관리 SQL 매퍼
 * @author jaseo
 * @since 2020. 03. 18.
 */
@Mapper
public interface PolicyCfrncMapper {
	
	/**
	 * 정책협의회 기본 정보 조회
	 * @param policyCfrncSearch
	 * @return
	 */
	public List<PolicyCfrnc> selectPolicyCfrnMngrList(PolicyCfrncSearch policyCfrncSearch);
	
	/**
	 * 정책협의회 기본 정보 수 조회
	 * @param policyCfrncSearch
	 * @return
	 */
	public int selectPolicyCfrnMngrListTotal(PolicyCfrncSearch policyCfrncSearch);

	/**
	 * 정책협의회 정보 중 구분값 리스트 조회
	 * @return
	 */
	public List<PolicyCfrnc> selectPolicyCfrnDiv();
	
	/**
	 * 구분 최대 차수 가져오기
	 * @return
	 */
	public int selectMaxMngOrder();
	
	/**
	 * 관리번호 최대 차수 가져오기
	 * @param policyCfrncForm
	 * @return
	 */
	public int selectMaxMngNoOrder(PolicyCfrnc policyCfrncForm);

	/**
	 * 정책협의회 관리 등록
	 * @param policyCfrncForm
	 * @return
	 */
	public int insertpolicyCfrncMngr(PolicyCfrnc policyCfrncForm);
	
	/**
	 * 정책협의회 관리 정보 조회 (단일 조회)
	 * @param policyCfrncSearch
	 * @return
	 */
	public PolicyCfrnc selectPolicyCfrnMngrInfo(PolicyCfrncSearch policyCfrncSearch);
	
	/**
	 * 정책협의회 관리 첨부파일정보 목록을 조회한다.
	 * @param fileInfo
	 * @return
	 */
	public List<FileInfo> selectPolicyCfrncFileInfoList(FileInfo fileInfo);

	/**
	 * 정책협의회 관리 수정
	 * @param policyCfrncForm
	 */
	public int updatePolicyCfrncMngr(PolicyCfrnc policyCfrncForm);
	
	/**
	 * MANAGE_ORDR = 1인 MANAGE_YEAR 조회
	 * @return
	 */
	public int selectMinMngrYear();
	
	/**
	 * 정책협의회 통계 리스트 조회
	 * @param policyCfrncSearch
	 * @return
	 */
	public List<PolicyCfrnc> selectPolicyCfrnStatList(PolicyCfrncSearch policyCfrncSearch);

	/**
	 * 정책협의회 통계 리스트 수 조회	
	 * @param policyCfrncSearch
	 * @return
	 */
	public int selectPolicyCfrnStatListTotal(PolicyCfrncSearch policyCfrncSearch);
	
	/**
	 * 추진 정보 등록
	 * @param policyCfrncDtlForm
	 * @return
	 */
	public int insertPolicyMngrDtl(PolicyCfrnc policyCfrncDtlForm);
	
	/**
	 * 정책협의회 상세 테이블에서 정책협의회 MNG_SEQ를 FK로 가지면서 max DTL_SEQ 를 가져온다.
	 * @param policyCfrncSearch
	 * @return
	 */
	public int selectPolicyMaxDtlSeq(PolicyCfrncSearch policyCfrncSearch);
	
	/**
	 * 정책협의회관리 정보를 삭제한다.
	 * @param policyCfrnc
	 * @return
	 */
	public int deletePolicyCfrnc(PolicyCfrnc policyCfrnc);
	
	/*************************************************************************/
	//  추진정보 Mapper
	/*************************************************************************/
	
	/**
	 * 추진 정보 조회 (단건)
	 * @param policyCfrncSearch
	 * @return
	 */
	public PolicyCfrnc selectPolicyCfrnDtlInfo(PolicyCfrncSearch policyCfrncSearch);
	
	/**
	 * 추진 정보 조회
	 * @param policyCfrncSearch2
	 * @return
	 */
	public List<PolicyCfrnc> selectPolicyCfrnDtlInfoList(PolicyCfrncSearch policyCfrncSearch2);
	
	/**
	 * 추진 정보 수정
	 * @param policyCfrnc
	 * @return
	 */
	public int updatePolicyCfrnDtlInfo(PolicyCfrnc policyCfrnc);
	
	/**
	 * 추진 정보 삭제
	 * @param policyCfrnc
	 * @return
	 */
	public int deletePolicyCfrnDtlInfo(PolicyCfrnc policyCfrnc);
	
	
	
	
}
