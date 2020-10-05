/**
 * 
 */
package egovframework.com.asapro.openapiinfo.service;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

import twitter4j.JSONException;



/**
 * 오픈 api 정보 관리 서비스
 * @author yckim
 * @since 2019. 2. 12.
 *
 */
public interface OpenApiInfoService {

	/**
	 * 오픈 API 정보 목록을 반환한다.
	 * @param openApiInfoSearch
	 * @return 오픈 API목록
	 */
	public List<OpenApiInfo> getOpenApiInfoList(OpenApiInfoSearch openApiInfoSearch);

	/**
	 * 오픈 API 정보 목록 토탈을 반환한다.
	 * @param openApiInfoSearch
	 * @return 오픈 API목록 토탈
	 */
	public int getOpenApiInfoListTotal(OpenApiInfoSearch openApiInfoSearch);

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
	public OpenApiInfo getOpenApiInfo(OpenApiInfo openApiInfoForm);

	/**
	 * 오픈 API 정보를 수정한다.
	 * @param openApiInfoForm
	 * @return 수정결과
	 */
	public int updateOpenApiInfo(OpenApiInfo openApiInfoForm);

	/**
	 * 오픈 API 정보를 삭제한다.
	 * @param openApiInfoList
	 * @return 삭제결과
	 */
	public int deleteOpenApiInfo(List<OpenApiInfo> openApiInfoList);

	/**
	 * 오픈 API 정보를 삭제한다.
	 * @param openApiInfo
	 * @return 삭제결과
	 */
	public int deleteOpenApiInfo(OpenApiInfo openApiInfo);

	/**
	 * 오픈 API를 이용하여 DB에 데이터를 배치한다.
	 * @param openApiInfoModel
	 * @return 배치결과
	 * @throws SecurityException 
	 * @throws NoSuchMethodException 
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 * @throws IllegalAccessException 
	 * @throws ClassNotFoundException 
	 * @throws InstantiationException 
	 */
	public int batchOpenApiInfo(OpenApiInfo openApiInfoModel) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, ClassNotFoundException, InstantiationException;

	/**
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 * @throws IllegalAccessException 
	 * @throws SecurityException 
	 * @throws NoSuchMethodException 
	 * 중계서버를이용해 오픈 API를 호출해서 DB에 데이터를 배치한다.
	 * @param openApiInfoModel
	 * @return
	 * @throws IOException 
	 * @throws JSONException 
	 * @throws  
	 */
	public int batchOpenApiInfoByRelay(OpenApiInfo openApiInfoModel) throws IOException, NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException;
	
	/**
	 * 오픈 API 배치정보를 수정한다.
	 * @param openApiInfoModel
	 * @return 수정결과
	 */
	public int updateBatchInfo(OpenApiInfo openApiInfoModel);

	

	
}
