/**
 * 
 */
package egovframework.com.asapro.archive.customtype.policy.service;

import java.util.List;
import java.util.Map;

import egovframework.com.asapro.content_statis.service.ContentStatisSearch;
import egovframework.com.asapro.fileinfo.service.FileInfo;
import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * 정책자료 관리 SQL 매퍼
 * @author yckim
 * @since 2019. 12. 27.
 *
 */
@Mapper
public interface PolicyMapper {

	//=============================================================================================================================
	//==========================================  정책자료    ================================================================
	//=============================================================================================================================
	
	/**
	 * 정책자료 목록을 조회한다.
	 * @param policySearch
	 * @return 정책자료 목록
	 */
	public List<Policy> selectPolicyList(PolicySearch policySearch);

	/**
	 * 정책자료 목록 토탈을 조회한다.,
	 * @param policySearch
	 * @return 정책자료 목록 토탈
	 */
	public int selectPolicyListTotal(PolicySearch policySearch);

	/**
	 * 정책자료를 등록한다.
	 * @param policyForm
	 * @return 등록결과
	 */
	public int insertPolicy(Policy policyForm);

	/**
	 * 정책자료를 조회한다.
	 * @param policyForm
	 * @return 정책자료
	 */
	public Policy selectPolicy(Policy policyForm);

	/**
	 * 정책자료를 수정한다.
	 * @param policyForm
	 * @return 수정결과
	 */
	public int updatePolicy(Policy policyForm);

	/**
	 * 추천 기간내 베스트 정책 목록을 반환한다.
	 * @param contentStatisSearch
	 * @return 베스트목록
	 */
	public List<Policy> selectRecommendTermBestPolicyList(ContentStatisSearch contentStatisSearch);


}
