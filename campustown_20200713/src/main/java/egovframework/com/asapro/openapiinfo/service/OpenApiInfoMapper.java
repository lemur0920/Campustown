/**
 * 
 */
package egovframework.com.asapro.openapiinfo.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * 오픈 api 정보 관리 SQL 매퍼
 * @author yckim
 * @since 2019. 2. 12.
 *
 */
@Mapper
public interface OpenApiInfoMapper {

	/**
	 * 오픈 API 정보 목록을 조회한다.
	 * @param openApiInfoSearch
	 * @return 오픈 API 정보 목록
	 */
	public List<OpenApiInfo> selectOpenApiInfoList(OpenApiInfoSearch openApiInfoSearch);

	/**
	 * 오픈 API 정보 목록 토탈을 조회한다.
	 * @param openApiInfoSearch
	 * @return 오픈 API 정보 토탈
	 */
	public int selectOpenApiInfoListTotal(OpenApiInfoSearch openApiInfoSearch);

	/**
	 * 오픈 API 정보를 추가한다.
	 * @param openApiInfoForm
	 * @return 추가결과
	 */
	public int insertOpenApiInfo(OpenApiInfo openApiInfoForm);

	/**
	 * 오픈 API 정보를 조회한다.
	 * @param openApiInfoForm
	 * @return 오픈 API 정보
	 */
	public OpenApiInfo selectOpenApiInfo(OpenApiInfo openApiInfoForm);

	/**
	 * 오픈 API 정보를 수정한다.
	 * @param openApiInfoForm
	 * @return 수정결과
	 */
	public int updateOpenApiInfo(OpenApiInfo openApiInfoForm);

	/**
	 * 오픈 API 정보를 삭제한다.
	 * @param openApiInfo
	 * @return 삭제결과
	 */
	public int deleteOpenApiInfo(OpenApiInfo openApiInfo);

	/**
	 * 오픈 API 마지막 배치일 수정
	 * @param openApiInfo
	 * @return 수정결과
	 */
	public int updateBatchInfo(OpenApiInfo openApiInfo);

	
	
	

}
