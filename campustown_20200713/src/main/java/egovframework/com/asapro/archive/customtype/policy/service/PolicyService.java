/**
 * 
 */
package egovframework.com.asapro.archive.customtype.policy.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import egovframework.com.asapro.archive.service.ArchiveSearch;
import egovframework.com.asapro.content_statis.service.ContentStatisSearch;
import egovframework.com.asapro.fileinfo.service.FileInfoUploadResult;
import egovframework.com.asapro.support.exception.AsaproException;


/**
 * 정책자료 관리 서비스
 * @author yckim
 * @since 2019. 12. 27.
 *
 */
public interface PolicyService {

	//=============================================================================================================================
	//==========================================  정책자료    ================================================================
	//=============================================================================================================================
	
	/**
	 * 정책자료 목록을 조회한다.
	 * @param policySearch
	 * @return 정책자료 목록
	 */
	public List<Policy> getPolicyList(PolicySearch policySearch);
	
	/**
	 * 정책자료 목록 토탈을 조회한다.
	 * @param policySearch
	 * @return 정책자료 목록 토탈
	 */
	public int getPolicyListTotal(PolicySearch policySearch);

	/**
	 * 정책자료를 등록한다
	 * @param policyForm
	 * @return 등록결과
	 * @throws IOException 
	 * @throws AsaproException 
	 */
	public Map<String, FileInfoUploadResult> insertPolicy(Policy policyForm) throws AsaproException, IOException;

	/**
	 * 정책자료를 조회한다.
	 * @param policyForm
	 * @return 정책자료
	 */
	public Policy getPolicy(Policy policyForm);

	/**
	 * 정책자료를 수정한다.
	 * @param policyForm
	 * @return 수정결과
	 * @throws IOException 
	 * @throws AsaproException 
	 */
	public Map<String, FileInfoUploadResult> updatePolicy(Policy policyForm) throws AsaproException, IOException;

	/**
	 * 추천 기간내 베스트 정책 목록을 반환한다.
	 * @param contentStatisSearch
	 * @return 베스트목록
	 */
	public List<Policy> getRecommendTermBestPolicyList(ContentStatisSearch contentStatisSearch);

	
	
}
