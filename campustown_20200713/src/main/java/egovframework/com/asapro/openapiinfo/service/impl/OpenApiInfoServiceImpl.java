/**
 * 
 */
package egovframework.com.asapro.openapiinfo.service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.SystemUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

import egovframework.com.asapro.openapiinfo.api.service.ApiMapper;
import egovframework.com.asapro.openapiinfo.service.OpenApiInfo;
import egovframework.com.asapro.openapiinfo.service.OpenApiInfoBatch;
import egovframework.com.asapro.openapiinfo.service.OpenApiInfoMapper;
import egovframework.com.asapro.openapiinfo.service.OpenApiInfoSearch;
import egovframework.com.asapro.openapiinfo.service.OpenApiInfoService;
import egovframework.com.asapro.support.Constant;
import egovframework.com.asapro.support.util.AsaproUtils;
import egovframework.com.asapro.support.util.EtcUtil;
import egovframework.com.cmm.service.EgovProperties;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import twitter4j.JSONArray;
import twitter4j.JSONException;
import twitter4j.JSONObject;

/**
 * 오픈 api 정보 관리 서비스 구현
 * @author yckim
 * @since 2019. 2. 12.
 *
 */
@Service
public class OpenApiInfoServiceImpl extends EgovAbstractServiceImpl implements OpenApiInfoService{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(OpenApiInfoServiceImpl.class);

	@Autowired
	private OpenApiInfoMapper openApiInfoMapper;
	
	@Autowired
	private ApiMapper apiMapper;
	
	@Autowired
	private HttpServletRequest request;
	
	/**
	 * 오픈 API 정보 목록을 조회한다.
	 */
	@Override
	public List<OpenApiInfo> getOpenApiInfoList(OpenApiInfoSearch openApiInfoSearch) {
		return openApiInfoMapper.selectOpenApiInfoList(openApiInfoSearch);
	}

	/**
	 * 오픈 API 정보 목록 토탈을 조회한다.
	 */
	@Override
	public int getOpenApiInfoListTotal(OpenApiInfoSearch openApiInfoSearch) {
		return openApiInfoMapper.selectOpenApiInfoListTotal(openApiInfoSearch);
	}

	/**
	 * 오픈 API 정보를 추가한다.
	 */
	@Override
	public int insertOpenApiInfo(OpenApiInfo openApiInfoForm) {
		return openApiInfoMapper.insertOpenApiInfo(openApiInfoForm);
	}

	/**
	 * 오픈 API 정보를 조회한다.
	 */
	@Override
	public OpenApiInfo getOpenApiInfo(OpenApiInfo openApiInfoForm) {
		return openApiInfoMapper.selectOpenApiInfo(openApiInfoForm);
	}

	/**
	 * 오픈 API 정보를 수정한다.
	 */
	@Override
	public int updateOpenApiInfo(OpenApiInfo openApiInfoForm) {
		return openApiInfoMapper.updateOpenApiInfo(openApiInfoForm);
	}

	/**
	 * 오픈 API 정보를 삭제한다.
	 */
	@Override
	public int deleteOpenApiInfo(List<OpenApiInfo> openApiInfoList) {
		int deleted = 0;
		for( OpenApiInfo openApiInfo : openApiInfoList ){
			deleted += this.deleteOpenApiInfo(openApiInfo);
		}
		return deleted;
	}

	/**
	 * 오픈 API 정보를 삭제한다.
	 */
	@Override
	public int deleteOpenApiInfo(OpenApiInfo openApiInfo) {
		return openApiInfoMapper.deleteOpenApiInfo(openApiInfo);
	}

	/**
	 * 오픈 API를 이용하여 DB에 데이터를 배치한다.
	 * - 서버에서 직접 api를 호출하여 처리방법(was에서 외부 http를 호출 가능한 경우)
	 * @throws SecurityException 
	 * @throws NoSuchMethodException 
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 * @throws IllegalAccessException 
	 * @throws ClassNotFoundException 
	 * @throws InstantiationException 
	 */
	@Override
	public int batchOpenApiInfo(OpenApiInfo openApiInfoModel) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, ClassNotFoundException, InstantiationException {
		int inserted = 0;
		
		String apiId = openApiInfoModel.getApiId();
		String returnType = openApiInfoModel.getApiReturnType();
		
		//함수명 첫자는 소문자, 리턴타입의 첫글자는 대문자로 함수명 조합
		String methodName = apiId.substring(0, 1).toLowerCase() + apiId.substring(1) + returnType.substring(0, 1).toUpperCase() + returnType.substring(1);
		
		Object o = new OpenApiInfoBatch(openApiInfoModel);
		@SuppressWarnings("rawtypes")
		Class[] paramTypes = new Class[1];
        paramTypes[0] = OpenApiInfo.class;
		Method method = OpenApiInfoBatch.class.getMethod(methodName, paramTypes);
		inserted = (Integer) method.invoke(o, openApiInfoModel);
		
		//xml, json 구분
		//데이터 배치 처리
		
		return inserted;
	}
	
	/**
	 * 중계서버를이용해 오픈 API를 호출해서 DB에 데이터를 배치한다.
	 * @throws IOException 
	 * @throws SecurityException 
	 * @throws NoSuchMethodException 
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 * @throws IllegalAccessException 
	 * @throws JSONException 
	 */
	@Override
	public int batchOpenApiInfoByRelay(OpenApiInfo openApiInfoModel) throws IOException, NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		int inserted = 0;
		int total = 0;
		int responseCode = 0;
		String domain = "";
		
		String apiId = openApiInfoModel.getApiId();
		
		//함수명 첫자는 소문자, 아이디는 대문자로 함수명 조합
		String methodName = "insert" + apiId.substring(0, 1).toUpperCase() + apiId.substring(1);
		
		Gson gson = new Gson();
		String paramJson = gson.toJson(openApiInfoModel);
		URL url = null;
		
		//스케줄러에서 실행시 request 를 정상적으로 전달받을 수 없어서 로컬,개발 여부 판별방법 변경
		String serverIp = AsaproUtils.getServerAddress();
        
        if(StringUtils.isNotBlank(serverIp) ){
        	if(serverIp.equals("127.0.0.1") || serverIp.equals("127.0.0.2") ){//개발서버
        		domain = EgovProperties.getProperty("relay.devserver.domain");
        	}else if(serverIp.contains("125.123.123.") || serverIp.contains("218.234.234.") ){//회사 내부 아이피
        		return 0;
        	} else {//
        		domain = EgovProperties.getProperty("relay.server.domain");
        	}
        } else {
        	return 0;
        }
        
        
		//api relay 서버 도메인
//		if(AsaproUtils.isAsadalNet(request) || AsaproUtils.isDevPath(request) ){
//			domain = EgovProperties.getProperty("relay.devserver.doamin");
//		}else{
//			domain = EgovProperties.getProperty("relay.server.doamin");
//		}
		
		//토탈 수량 호출 url
		String totalUrl = domain + "/main/asacms/openapi/callTotal.do";
		
		try {
			LOGGER.debug("REST API Start");
			url = new URL(totalUrl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");
			conn.setRequestProperty("Accept-Charset", "UTF-8"); 
			conn.setConnectTimeout(10000);
			conn.setReadTimeout(10000);
			
			OutputStream os = conn.getOutputStream();
			os.write(paramJson.getBytes("UTF-8"));
			os.flush();
			os.close();
			
			responseCode = conn.getResponseCode();
			
			BufferedReader is = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = is.readLine()) != null) {
				response.append(inputLine);
			}
			is.close();
			
			total = Integer.parseInt(response.toString());
			//System.out.println("total  : " + total );
			
			conn.disconnect();
			LOGGER.debug("REST API End");

			//List<Object> ll = new ArrayList<Object>();
			//JobNewDealBiz kk = (JobNewDealBiz)ll.get(0); 

		} catch (MalformedURLException e) {
			LOGGER.error("[" + e.getClass() +"] URL create fail : " + e.getMessage());
		}
		
		//api호출이 정상 응답하고 데이터 수량이 있으면
		if(responseCode == 200 && total > 0){
			//기존 데이터 삭제
			apiMapper.deleteApiData(openApiInfoModel);
			
			//데이터 목록 호출 url
			String listUrl = domain + "/main/asacms/openapi/callList.do";
			openApiInfoModel.setEndIndex(openApiInfoModel.getApiNumOfRow());
			try {
				int seq = 1;
				for(int totalCall = 0;  totalCall < total; totalCall += openApiInfoModel.getApiNumOfRow() ){
					
					paramJson = gson.toJson(openApiInfoModel);
					
					LOGGER.debug("REST API Start");
					url = new URL(listUrl);
					HttpURLConnection conn = (HttpURLConnection) url.openConnection();
					conn.setDoOutput(true);
					conn.setRequestMethod("POST");
					conn.setRequestProperty("Content-Type", "application/json");
					conn.setRequestProperty("Accept-Charset", "UTF-8"); 
					conn.setConnectTimeout(10000);
					conn.setReadTimeout(10000);
					
					OutputStream os = conn.getOutputStream();
					os.write(paramJson.getBytes("UTF-8"));
					os.flush();
					os.close();
					
					responseCode = conn.getResponseCode();
					
					BufferedReader is = new BufferedReader(new InputStreamReader(conn.getInputStream()));
					String inputLine;
					StringBuffer response = new StringBuffer();
					
					while ((inputLine = is.readLine()) != null) {
						response.append(inputLine);
					}
					is.close();
					
					String listJson = response.toString();
					//System.out.println("listJson  : " + listJson );
					
					conn.disconnect();
					LOGGER.debug("REST API End");
					
					//각 api별 생성한 객체로 받아서 쓰려고 했으나 객체를 생성할때 변수로 할 수 없어 맵으로 대체함
					//List<Object> list = new ArrayList<Object>();
					List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
					
					JSONObject jsonObject = new JSONObject(listJson);
					String resultCode = (String) jsonObject.get("resultCode");
					JSONArray jsonArray = jsonObject.getJSONArray("list");
					
					//일 트래픽 초과일 경우 
					if("-99".equals(resultCode) ){
						return -99;
					}
					
					int index = 0;
					while (index < jsonArray.length()) {
						@SuppressWarnings("unchecked")
						Map<String, Object> item = gson.fromJson(jsonArray.get(index).toString(), Map.class);
						list.add(item);
						index++;
					}
					
					
					for (Map<String, Object> map : list) {
						//EtcUtil.objectInfo(jobNewDealBiz);
						//insert by mapper
						map.put("seq", seq);
						
						@SuppressWarnings("rawtypes")
						Class[] paramTypes = new Class[1];
				        paramTypes[0] = Map.class;
						Method method = ApiMapper.class.getMethod(methodName, paramTypes);
						int insert = (Integer) method.invoke(apiMapper, map);
						
						//apiMapper.insertJobNewDealBiz(map);
						seq++;
						inserted += insert;
					}
					
					//페이징관련 파라메터
					openApiInfoModel.setEndIndex(openApiInfoModel.getEndIndex() + openApiInfoModel.getApiNumOfRow());
					openApiInfoModel.setStartIndex(openApiInfoModel.getStartIndex() + openApiInfoModel.getApiNumOfRow());
					openApiInfoModel.setPageNum(openApiInfoModel.getPageNum() + 1);
				}
				
			} catch (MalformedURLException e) {
				LOGGER.error("[" + e.getClass() +"] URL create fail : " + e.getMessage());
			} catch (JSONException e) {
				LOGGER.error("[" + e.getClass() +"] JSONArray create fail : " + e.getMessage());
			}
		}else{
			return total;
		}
		
		
		return inserted;
	}
	

	/**
	 * 오픈 API 배치정보를 수정한다.
	 */
	@Override
	public int updateBatchInfo(OpenApiInfo openApiInfoModel) {
		return openApiInfoMapper.updateBatchInfo(openApiInfoModel);
	}
	

}
