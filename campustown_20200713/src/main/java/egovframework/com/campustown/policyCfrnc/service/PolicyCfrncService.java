package egovframework.com.campustown.policyCfrnc.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import egovframework.com.asapro.fileinfo.service.FileInfoUploadResult;
import egovframework.com.asapro.support.exception.AsaproException;

/**
 * 정책협의회 관리 관리자 서비스
 * @author jaseo
 * @since 2020. 03. 18.
 */
public interface PolicyCfrncService {
	
	/**
	 * 정책협의회 기본 정보 리스트 조회
	 * @param policyCfrncSearch
	 * @return
	 */
	List<PolicyCfrnc> getPolicyCfrnMngrList(PolicyCfrncSearch policyCfrncSearch);
	
	/**
	 * 정책협의회 기본 정보 리스트 수 조회
	 * @param policyCfrncSearch
	 * @return
	 */
	int getPolicyCfrnMngrListTotal(PolicyCfrncSearch policyCfrncSearch);
	
	/**
	 * 정책협의회 정보 중 구분값 리스트 조회
	 * @return
	 */
	public List<PolicyCfrnc> getPolicyCfrnDiv();

	/**
	 * 구분 최대 차수 가져오기
	 * @return
	 */
	int getMaxMngOrder();
	
	/**
	 * 관리번호 최대 차수 가져오기
	 * @param policyCfrncForm
	 * @return
	 */
	int getMaxMngNoOrder(PolicyCfrnc policyCfrncForm);

	/**
	 * 정책협의회 관리 등록
	 * @param policyCfrncForm
	 * @return
	 * @throws AsaproException
	 * @throws IOException
	 */
	Map<String, FileInfoUploadResult> insertpolicyCfrncMngr(PolicyCfrnc policyCfrncForm) throws AsaproException, IOException;
	
	
	/**
	 * 정책협의회 관리 정보 조회 (단일 조회)
	 * @param policyCfrncSearch
	 * @return
	 */
	public PolicyCfrnc getPolicyCfrnMngrInfo(PolicyCfrncSearch policyCfrncSearch);
	
	/**
	 * 정책협의회 관리 수정
	 * @param policyCfrncForm
	 * @return
	 * @throws IOException 
	 * @throws AsaproException 
	 */
	Map<String, FileInfoUploadResult> updatePolicyMngr(PolicyCfrnc policyCfrncForm) throws AsaproException, IOException;

	/**
	 * MANAGE_ORDR = 1인 MANAGE_YEAR 조회
	 * @return
	 */
	int getMinMngrYear();
	
	/**
	 * 정책협의회 통계 리스트 조회
	 * @param policyCfrncSearch
	 * @return
	 */
	public List<PolicyCfrnc> getPolicyCfrnStatList(PolicyCfrncSearch policyCfrncSearch);
	
	/**
	 * 정책협의회 통계 리스트 수 조회
	 * @param policyCfrncSearch
	 * @return
	 */
	int getPolicyCfrnStatListTotal(PolicyCfrncSearch policyCfrncSearch);
	
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
	public int getPolicyMaxDtlSeq(PolicyCfrncSearch policyCfrncSearch);

	/**
	 * 추진 정보 조회 (단건)	
	 * @param policyCfrncSearch
	 * @return
	 */
	public PolicyCfrnc getPolicyCfrnDtlInfo(PolicyCfrncSearch policyCfrncSearch);
	
	/**
	 * 추진 정보 조회
	 * @param policyCfrncSearch2
	 * @return
	 */
	public List<PolicyCfrnc> getPolicyCfrnDtlInfoList(PolicyCfrncSearch policyCfrncSearch2);

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

	/**
	 * 정책협의회관리 정보를 삭제한다.
	 * @param policyCfrncList
	 * @return
	 */
	public int deletePolicyCfrnc(List<PolicyCfrnc> policyCfrncList);
	
}
