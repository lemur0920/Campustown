/**
 * 
 */
package egovframework.com.asapro.openapiinfo.service;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import egovframework.com.asapro.support.jdbc.AsaproDBUtils;
import egovframework.com.asapro.support.util.AsaproUtils;


/**
 * 오픈 api 정보 배치 처리
 * @author yckim
 * @since 2019. 2. 13.
 *
 */

public class OpenApiInfoBatch {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(OpenApiInfoBatch.class);
	
	private String apiId;	//api 아이디
	private String apiSubId;	//서비스마다 서브아이디 개념으로 url을 만드는 경우 사용
	private String apiKey;	//api 부여받은 키
	private String provider;	//api제공 사이트 아이디
	private String domain;	//api 사이트 도메인
	private String returnType;	//리턴타입 (json, xml)
	private Integer numOfRow;	//데이터갯수
	private String tableName;	//저장할 테이블 명
	
	private int pageNum = 1;
	private int startIndex = 1;
	private int endIndex = 1;
	
	//============================================================================================================
	//===========================================서울열린데이터광장========================================================
	//============================================================================================================
	
	/**
	 * OpenApiInfo 인자로 받는 생성자
	 * @param openApiInfo
	 */
	public OpenApiInfoBatch(OpenApiInfo openApiInfo){
		this.apiId = openApiInfo.getApiId();
		this.apiKey = openApiInfo.getApiKey();
		this.provider = openApiInfo.getApiProvider();
		this.domain = openApiInfo.getApiDomain();
		this.returnType = openApiInfo.getApiReturnType();
		this.numOfRow = openApiInfo.getApiNumOfRow();
		this.tableName = openApiInfo.getApiTableName();
	}
	
	/**
	 * [서울 뉴딜일자리 사업정보]를 json 탑입으로 리턴받아 배치한다.
	 * @param openApiInfoModel
	 * @return 배치 결과
	 * @throws ParseException 
	 */
	public int jobNewDealBizOpenInfoJson(OpenApiInfo openApiInfoModel) throws ParseException {
		int totalInsert = 0;
		String urlText = "";
		String resultText = "";
		
		//
		
		URL url = null;
		
		//호출 url을 만든다.
		urlText = makeUrlText();
		
		try {
			url = new URL(urlText);
		} catch (MalformedURLException e) {
			LOGGER.error("[" + e.getClass() +"] URL create fail : " + e.getMessage());
		}
		
		//호출결과값을 텍스트로 담는다.
		resultText = AsaproUtils.UrlToString(url);
		
		JSONParser jsonparser = new JSONParser();
	    JSONObject jsonobject = (JSONObject)jsonparser.parse(resultText);
	    JSONObject jobNewDealBizOpenInfo =  (JSONObject) jsonobject.get("jobNewDealBizOpenInfo");
	    String resultCode = (String)((JSONObject)(jobNewDealBizOpenInfo.get("RESULT"))).get("CODE");
	    
	    int listTotalCount = 0;
	    if(resultCode.equals("INFO-000")){	//처음 api 호출시 정상처리 되었을경우만
	    	listTotalCount = ((Long)jobNewDealBizOpenInfo.get("list_total_count")).intValue();
		    //System.out.println("총수량 : "+listTotalCount);
		    
		  //새로 받기전에 모두 삭제
		    String sqlDeleteAll = "delete from " + this.tableName;	
		    AsaproDBUtils.execute(sqlDeleteAll);
		    
		    this.endIndex = this.numOfRow;
		    for(int totalCall = 0;  totalCall < listTotalCount; totalCall += this.numOfRow ){
		    	//int insert = 0;
		    	
		    	urlText = makeUrlText();
		    	//System.out.println(urlText);
		    	try {
					url = new URL(urlText);
				} catch (MalformedURLException e) {
					LOGGER.error("[" + e.getClass() +"] URL create fail : " + e.getMessage());
				}
				
				//호출결과값을 텍스트로 담는다.
				resultText = AsaproUtils.UrlToString(url);
				
		    	jsonobject = (JSONObject)jsonparser.parse(resultText);
		    	jobNewDealBizOpenInfo =  (JSONObject) jsonobject.get("jobNewDealBizOpenInfo");
		        resultCode = (String)((JSONObject)(jobNewDealBizOpenInfo.get("RESULT"))).get("CODE");
		    	
		        if(resultCode.equals("INFO-000")){	//api 호출시 정상처리 되었을경우만
			    	
				    JSONArray array = (JSONArray)jobNewDealBizOpenInfo.get("row");
				   
				    for(int i = 0 ; i < array.size(); i++){
				        
				        JSONObject entity = (JSONObject)array.get(i);
				        
				        int seq = totalInsert + 1;
				        String bizNm = (String) entity.get("BIZ_NM");
				        String upOrgNm = (String) entity.get("UP_ORG_NM");
				        String orgNm = (String) entity.get("ORG_NM");
				        String partNm = (String) entity.get("PART_NM");
				        String bizYear = (String) entity.get("BIZ_YEAR");
				        String upBizNm = (String) entity.get("UP_BIZ_NM");
				        String bizGrpNm = (String) entity.get("BIZ_GRP_NM");
				        String BizTypeNm = (String) entity.get("BIZ_TYPE_NM");
				        String bizTarget = (String) entity.get("BIZ_TARGET");
				        int allWrkTime = ((Double)entity.get("ALL_WRK_TIME")).intValue();
				        int allPayAmt = ((Double)entity.get("ALL_PAY_AMT")).intValue();
				        int allSlctNumber = ((Double)entity.get("ALL_SLCT_NUMBER")).intValue();
				        int allPlanNumber = ((Double)entity.get("ALL_PLAN_NUMBER")).intValue();
				        int partWrkTime = ((Double)entity.get("PART_WRK_TIME")).intValue();
				        int partPayAmt = ((Double)entity.get("PART_PAY_AMT")).intValue();
				        int partSlctNumber = ((Double)entity.get("PART_SLCT_NUMBER")).intValue();
				        int partPlanNumber = ((Double)entity.get("PART_PLAN_NUMBER")).intValue();
				        String bizStrtDd = (String) entity.get("BIZ_STRT_DD");
				        String bizEndDd = (String) entity.get("BIZ_END_DD");
				        String bizSmry = (String) entity.get("BIZ_SMRY");
				        String workSmry = (String) entity.get("WORK_SMRY");
				        String condSmry = (String) entity.get("COND_SMRY");
				        String phonNoCn = (String) entity.get("PHON_NO_CN");
				        
				        //System.out.println(updateYmdhms);
				    	String sql = "insert into " + this.tableName + 
				    			" (SEQ, "
				    			+ "BIZ_NM, "
				    			+ "UP_ORG_NM, "
				    			+ "ORG_NM, "
				    			+ "PART_NM, "
				    			+ "BIZ_YEAR, "
				    			+ "UP_BIZ_NM, "
				    			+ "BIZ_GRP_NM, "
				    			+ "BIZ_TYPE_NM, "
				    			+ "BIZ_TARGET, "
				    			+ "ALL_WRK_TIME, "
				    			+ "ALL_PAY_AMT, "
				    			+ "ALL_SLCT_NUMBER, "
				    			+ "ALL_PLAN_NUMBER, "
				    			+ "PART_WRK_TIME, "
				    			+ "PART_PAY_AMT, "
				    			+ "PART_SLCT_NUMBER, "
				    			+ "PART_PLAN_NUMBER, "
				    			+ "BIZ_STRT_DD, "
				    			+ "BIZ_END_DD, "
				    			+ "BIZ_SMRY, "
				    			+ "WORK_SMRY, "
				    			+ "COND_SMRY, "
				    			+ "PHON_NO_CN) "
				    			+ "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
				    	totalInsert += AsaproDBUtils.execute(sql, seq, bizNm, upOrgNm, orgNm, partNm, bizYear, upBizNm, 
				        		bizGrpNm, BizTypeNm, bizTarget, allWrkTime, allPayAmt, allSlctNumber,
				        		allPlanNumber, partWrkTime, partPayAmt, partSlctNumber, partPlanNumber, bizStrtDd, bizEndDd,
				        		bizSmry, workSmry, condSmry, phonNoCn);
				    	
				    }
					
				    this.startIndex += this.numOfRow;
				    this.endIndex += this.numOfRow;
			    	
		    	}
		    	
		    }
		    
	    }
	    LOGGER.info("[BATCH] " + apiId + " result - 총건수 : {}, 등록데이터 수 : {} ", listTotalCount, totalInsert);
		return totalInsert;
	}

	/**
	 * [서울 뉴딜일자리 사업정보]를 XML 탑입으로 리턴받아 배치한다.
	 * @param openApiInfoModel
	 * @return 배치결과
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 */
	public int jobNewDealBizOpenInfoXml(OpenApiInfo openApiInfoModel) throws ParserConfigurationException, SAXException, IOException  {
		
		int totalInsert = 0;
		String urlText = "";
		
		//호출 url을 만든다.
		urlText = makeUrlText();
		
        //DocumentBuilderFactory 생성
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        DocumentBuilder builder;
        Document doc = null;

        // xml 파싱하기
        //InputSource is = new InputSource(new StringReader(resultText));
        builder = factory.newDocumentBuilder();
        doc = builder.parse(urlText);
        doc.getDocumentElement().normalize();
        // 루트 엘리먼트
        Element jobNewDealBizOpenInfo = doc.getDocumentElement();	
        
         
        String resultCode = (((Element)jobNewDealBizOpenInfo.getElementsByTagName("RESULT").item(0)).getElementsByTagName("CODE").item(0).getTextContent());
        
        int listTotalCount = 0;
	    if(resultCode.equals("INFO-000")){	//처음 api 호출시 정상처리 되었을경우만
	    	listTotalCount = Integer.parseInt(jobNewDealBizOpenInfo.getElementsByTagName("list_total_count").item(0).getTextContent());
	    	
	    	//새로 받기전에 모두 삭제
		    String sqlDeleteAll = "delete from " + this.tableName;	
		    AsaproDBUtils.execute(sqlDeleteAll);
		    
		    this.endIndex = this.numOfRow;
		    for(int totalCall = 0;  totalCall < listTotalCount; totalCall += this.numOfRow ){
		    	//int insert = 0;
		    	
		    	urlText = makeUrlText();
				
				doc = builder.parse(urlText);
		        doc.getDocumentElement().normalize();
		        // 루트 엘리먼트
		        jobNewDealBizOpenInfo = doc.getDocumentElement();	
		        resultCode = ((Element)jobNewDealBizOpenInfo.getElementsByTagName("RESULT").item(0)).getElementsByTagName("CODE").item(0).getTextContent();
		    	
		        if(resultCode.equals("INFO-000")){	//api 호출시 정상처리 되었을경우만
		        	
		        	NodeList array = jobNewDealBizOpenInfo.getElementsByTagName("row");
					   
				    for(int i = 0 ; i < array.getLength(); i++){
				    	
				    	int seq = totalInsert + 1;
				    	String bizNm = "";
				        String upOrgNm = "";
				        String orgNm = "";
				        String partNm = "";
				        String bizYear = "";
				        String upBizNm = "";
				        String bizGrpNm = "";
				        String BizTypeNm = "";
				        String bizTarget = "";
				        int allWrkTime = 0;
				        int allPayAmt = 0;
				        int allSlctNumber = 0;
				        int allPlanNumber = 0;
				        int partWrkTime = 0;
				        int partPayAmt = 0;
				        int partSlctNumber = 0;
				        int partPlanNumber = 0;
				        String bizStrtDd = "";
				        String bizEndDd = "";
				        String bizSmry = "";
				        String workSmry = "";
				        String condSmry = "";
				        String phonNoCn = "";
				    	
				    	NodeList child = array.item(i).getChildNodes();
			            for (int j = 0; j < child.getLength(); j++) {
			            	Node node = child.item(j);
			            	if(node.getNodeType() == 1 ){	//노드 타입 : 1
			            		
			            		if (node.getNodeName().equals("BIZ_NM")) {
			            			bizNm = node.getTextContent();
								} else if (node.getNodeName().equals("UP_ORG_NM")) {
									upOrgNm = node.getTextContent();
								} else if (node.getNodeName().equals("ORG_NM")) {
									orgNm = node.getTextContent();
								} else if (node.getNodeName().equals("PART_NM")) {
									partNm = node.getTextContent();
								} else if (node.getNodeName().equals("BIZ_YEAR")) {
									bizYear = node.getTextContent();
								} else if (node.getNodeName().equals("UP_BIZ_NM")) {
									upBizNm = node.getTextContent();
								} else if (node.getNodeName().equals("BIZ_GRP_NM")) {
									bizGrpNm = node.getTextContent();
								} else if (node.getNodeName().equals("BIZ_TYPE_NM")) {
									BizTypeNm = node.getTextContent();
								} else if (node.getNodeName().equals("BIZ_TARGET")) {
									bizTarget = node.getTextContent();
								} else if (node.getNodeName().equals("ALL_WRK_TIME")) {
									allWrkTime = Integer.parseInt(StringUtils.isNotBlank(node.getTextContent()) ? node.getTextContent() : "0");
								} else if (node.getNodeName().equals("ALL_PAY_AMT")) {
									allPayAmt = Integer.parseInt(StringUtils.isNotBlank(node.getTextContent()) ? node.getTextContent() : "0");
								} else if (node.getNodeName().equals("ALL_SLCT_NUMBER")) {
									allSlctNumber = Integer.parseInt(StringUtils.isNotBlank(node.getTextContent()) ? node.getTextContent() : "0");
								} else if (node.getNodeName().equals("ALL_PLAN_NUMBER")) {
									allPlanNumber = Integer.parseInt(StringUtils.isNotBlank(node.getTextContent()) ? node.getTextContent() : "0");
								} else if (node.getNodeName().equals("PART_WRK_TIME")) {
									partWrkTime = Integer.parseInt(StringUtils.isNotBlank(node.getTextContent()) ? node.getTextContent() : "0");
								} else if (node.getNodeName().equals("PART_PAY_AMT")) {
									partPayAmt = Integer.parseInt(StringUtils.isNotBlank(node.getTextContent()) ? node.getTextContent() : "0");
								} else if (node.getNodeName().equals("PART_SLCT_NUMBER")) {
									partSlctNumber = Integer.parseInt(StringUtils.isNotBlank(node.getTextContent()) ? node.getTextContent() : "0");
								} else if (node.getNodeName().equals("PART_PLAN_NUMBER")) {
									partPlanNumber = Integer.parseInt(StringUtils.isNotBlank(node.getTextContent()) ? node.getTextContent() : "0");
								} else if (node.getNodeName().equals("BIZ_STRT_DD")) {
									bizStrtDd = node.getTextContent();
								} else if (node.getNodeName().equals("BIZ_END_DD")) {
									bizEndDd = node.getTextContent();
								} else if (node.getNodeName().equals("BIZ_SMRY")) {
									bizSmry = node.getTextContent();
								} else if (node.getNodeName().equals("WORK_SMRY")) {
									workSmry = node.getTextContent();
								} else if (node.getNodeName().equals("COND_SMRY")) {
									condSmry = node.getTextContent();
								} else if (node.getNodeName().equals("PHON_NO_CN")) {
									phonNoCn = node.getTextContent();
								}
			            		
			            	}
			            }
			            
			          //System.out.println(updateYmdhms);
				    	String sql = "insert into " + this.tableName + 
				    			" (SEQ, "
				    			+ "BIZ_NM, "
				    			+ "UP_ORG_NM, "
				    			+ "ORG_NM, "
				    			+ "PART_NM, "
				    			+ "BIZ_YEAR, "
				    			+ "UP_BIZ_NM, "
				    			+ "BIZ_GRP_NM, "
				    			+ "BIZ_TYPE_NM, "
				    			+ "BIZ_TARGET, "
				    			+ "ALL_WRK_TIME, "
				    			+ "ALL_PAY_AMT, "
				    			+ "ALL_SLCT_NUMBER, "
				    			+ "ALL_PLAN_NUMBER, "
				    			+ "PART_WRK_TIME, "
				    			+ "PART_PAY_AMT, "
				    			+ "PART_SLCT_NUMBER, "
				    			+ "PART_PLAN_NUMBER, "
				    			+ "BIZ_STRT_DD, "
				    			+ "BIZ_END_DD, "
				    			+ "BIZ_SMRY, "
				    			+ "WORK_SMRY, "
				    			+ "COND_SMRY, "
				    			+ "PHON_NO_CN) "
				    			+ "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
				    	totalInsert += AsaproDBUtils.execute(sql, seq, bizNm, upOrgNm, orgNm, partNm, bizYear, upBizNm, 
				        		bizGrpNm, BizTypeNm, bizTarget, allWrkTime, allPayAmt, allSlctNumber,
				        		allPlanNumber, partWrkTime, partPayAmt, partSlctNumber, partPlanNumber, bizStrtDd, bizEndDd,
				        		bizSmry, workSmry, condSmry, phonNoCn);
				    	
				    }
					
				    this.startIndex += this.numOfRow;
				    this.endIndex += this.numOfRow;
		        	
		        }
				
		    }
	    	
	    }
        
	    LOGGER.info("[BATCH] " + apiId + " result - 총건수 : {}, 등록데이터 수 : {} ", listTotalCount, totalInsert);
		return totalInsert;
	}
	
	/**
	 * [서울일자리센터 교육정보]를 json 탑입으로 리턴받아 배치한다.
	 * @param openApiInfoModel
	 * @return 배치 결과
	 * @throws ParseException 
	 */
	public int jobEduCenterOpenInfoJson(OpenApiInfo openApiInfoModel) throws ParseException {
		
		int totalInsert = 0;
		String urlText = "";
		String resultText = "";
		
		//
		
		URL url = null;
		
		//호출 url을 만든다.
		urlText = makeUrlText();
		
		try {
			url = new URL(urlText);
		} catch (MalformedURLException e) {
			LOGGER.error("[" + e.getClass() +"] URL create fail : " + e.getMessage());
		}
		
		//호출결과값을 텍스트로 담는다.
		resultText = AsaproUtils.UrlToString(url);
		//System.out.println(resultText);
		
		JSONParser jsonparser = new JSONParser();
	    JSONObject jsonobject = (JSONObject)jsonparser.parse(resultText);
	    JSONObject jobEduCenterOpenInfo =  (JSONObject) jsonobject.get("jobEduCenterOpenInfo");
	    String resultCode = (String)((JSONObject)(jobEduCenterOpenInfo.get("RESULT"))).get("CODE");
	    
	    int listTotalCount = 0;
	    if(resultCode.equals("INFO-000")){	//처음 api 호출시 정상처리 되었을경우만
	    	listTotalCount = ((Long)jobEduCenterOpenInfo.get("list_total_count")).intValue();
		    //System.out.println("총수량 : "+listTotalCount);
		    
		  //새로 받기전에 모두 삭제
		    String sqlDeleteAll = "delete from " + this.tableName;	
		    AsaproDBUtils.execute(sqlDeleteAll);
		    
		    this.endIndex = this.numOfRow;
		    for(int totalCall = 0;  totalCall < listTotalCount; totalCall += this.numOfRow ){
		    	//int insert = 0;
		    	
		    	urlText = makeUrlText();
		    	//System.out.println(urlText);
		    	try {
					url = new URL(urlText);
				} catch (MalformedURLException e) {
					LOGGER.error("[" + e.getClass() +"] URL create fail : " + e.getMessage());
				}
				
				//호출결과값을 텍스트로 담는다.
				resultText = AsaproUtils.UrlToString(url);
				
		    	jsonobject = (JSONObject)jsonparser.parse(resultText);
		    	jobEduCenterOpenInfo =  (JSONObject) jsonobject.get("jobEduCenterOpenInfo");
		        resultCode = (String)((JSONObject)(jobEduCenterOpenInfo.get("RESULT"))).get("CODE");
		    	
		        if(resultCode.equals("INFO-000")){	//api 호출시 정상처리 되었을경우만
			    	
				    JSONArray array = (JSONArray)jobEduCenterOpenInfo.get("row");
				   
				    for(int i = 0 ; i < array.size(); i++){
				        
				        JSONObject entity = (JSONObject)array.get(i);
				        
				        int seq = totalInsert + 1;
				        String edcNm = (String) entity.get("EDC_NM");
				        String edcPurpsCn = (String) entity.get("EDC_PURPS_CN");
				        String edcCn = (String) entity.get("EDC_CN");
				        String edcBeginDeDt = (String) entity.get("EDC_BEGIN_DE_DT");
				        String edcEndDeDt = (String) entity.get("EDC_END_DE_DT");
				        String edcTimeHm = (String) entity.get("EDC_TIME_HM");
				        String atnlcQualfCn = (String) entity.get("ATNLC_QUALF_CN");
				        String lctrumInfoCn = (String) entity.get("LCTRUM_INFO_CN");
				        String edcAmountAtNm = (String) entity.get("EDC_AMOUNT_AT_NM");
				        String matrlAmountAtNm = (String) entity.get("MATRL_AMOUNT_AT_NM");
				        int psncpaCo = ((Double)entity.get("PSNCPA_CO")).intValue();
				        String rcritBeginDeDt = (String) entity.get("RCRIT_BEGIN_DE_DT");
				        String rcritEndDeDt = (String) entity.get("RCRIT_END_DE_DT");
				        String ageCoNm = (String) entity.get("AGE_CO_NM");
				        String sexQualfCn = (String) entity.get("SEX_QUALF_CN");
				        String sttusNm = (String) entity.get("STTUS_NM");
				        
				        //System.out.println(updateYmdhms);
				    	String sql = "insert into " + this.tableName + 
				    			" (SEQ, "
				    			+ "EDC_NM, "
				    			+ "EDC_PURPS_CN, "
				    			+ "EDC_CN, "
				    			+ "EDC_BEGIN_DE_DT, "
				    			+ "EDC_END_DE_DT, "
				    			+ "EDC_TIME_HM, "
				    			+ "ATNLC_QUALF_CN, "
				    			+ "LCTRUM_INFO_CN, "
				    			+ "EDC_AMOUNT_AT_NM, "
				    			+ "MATRL_AMOUNT_AT_NM, "
				    			+ "PSNCPA_CO, "
				    			+ "RCRIT_BEGIN_DE_DT, "
				    			+ "RCRIT_END_DE_DT, "
				    			+ "AGE_CO_NM, "
				    			+ "SEX_QUALF_CN, "
				    			+ "STTUS_NM) "
				    			+ "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
				    	totalInsert += AsaproDBUtils.execute(sql, seq, edcNm, edcPurpsCn, edcCn, edcBeginDeDt, edcEndDeDt, edcTimeHm, 
				        		atnlcQualfCn, lctrumInfoCn, edcAmountAtNm, matrlAmountAtNm, psncpaCo, rcritBeginDeDt,
				        		rcritEndDeDt, ageCoNm, sexQualfCn, sttusNm);
				    	
				    }
					
				    this.startIndex += this.numOfRow;
				    this.endIndex += this.numOfRow;
			    	
		    	}
		    	
		    }
		    
	    }
	    LOGGER.info("[BATCH] " + apiId + " result - 총건수 : {}, 등록데이터 수 : {} ", listTotalCount, totalInsert);
		return totalInsert;
	}
	
	/**
	 * [서울일자리센터 교육정보]를 xml 탑입으로 리턴받아 배치한다.
	 * @param openApiInfoModel
	 * @return 배치결과
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 */
	public int jobEduCenterOpenInfoXml(OpenApiInfo openApiInfoModel) throws ParserConfigurationException, SAXException, IOException  {
		
		int totalInsert = 0;
		String urlText = "";
		
		//호출 url을 만든다.
		urlText = makeUrlText();
		
        //DocumentBuilderFactory 생성
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        DocumentBuilder builder;
        Document doc = null;

        // xml 파싱하기
        //InputSource is = new InputSource(new StringReader(resultText));
        builder = factory.newDocumentBuilder();
        doc = builder.parse(urlText);
        doc.getDocumentElement().normalize();
        // 루트 엘리먼트
        Element jobEduCenterOpenInfo = doc.getDocumentElement();	
        
         
        String resultCode = (((Element)jobEduCenterOpenInfo.getElementsByTagName("RESULT").item(0)).getElementsByTagName("CODE").item(0).getTextContent());
        
        int listTotalCount = 0;
	    if(resultCode.equals("INFO-000")){	//처음 api 호출시 정상처리 되었을경우만
	    	listTotalCount = Integer.parseInt(jobEduCenterOpenInfo.getElementsByTagName("list_total_count").item(0).getTextContent());
	    	
	    	//새로 받기전에 모두 삭제
		    String sqlDeleteAll = "delete from " + this.tableName;	
		    AsaproDBUtils.execute(sqlDeleteAll);
		    
		    this.endIndex = this.numOfRow;
		    for(int totalCall = 0;  totalCall < listTotalCount; totalCall += this.numOfRow ){
		    	//int insert = 0;
		    	
		    	urlText = makeUrlText();
				
				doc = builder.parse(urlText);
		        doc.getDocumentElement().normalize();
		        // 루트 엘리먼트
		        jobEduCenterOpenInfo = doc.getDocumentElement();	
		        resultCode = ((Element)jobEduCenterOpenInfo.getElementsByTagName("RESULT").item(0)).getElementsByTagName("CODE").item(0).getTextContent();
		    	
		        if(resultCode.equals("INFO-000")){	//api 호출시 정상처리 되었을경우만
		        	
		        	NodeList array = jobEduCenterOpenInfo.getElementsByTagName("row");
					   
				    for(int i = 0 ; i < array.getLength(); i++){
				    	
				        int seq = totalInsert + 1;
				        String edcNm = "";
				        String edcPurpsCn = "";
				        String edcCn = "";
				        String edcBeginDeDt = "";
				        String edcEndDeDt = "";
				        String edcTimeHm = "";
				        String atnlcQualfCn = "";
				        String lctrumInfoCn = "";
				        String edcAmountAtNm = "";
				        String matrlAmountAtNm = "";
				        int psncpaCo = 0;
				        String rcritBeginDeDt = "";
				        String rcritEndDeDt = "";
				        String ageCoNm = "";
				        String sexQualfCn = "";
				        String sttusNm = "";
				        
				       
				    	NodeList child = array.item(i).getChildNodes();
			            for (int j = 0; j < child.getLength(); j++) {
			            	Node node = child.item(j);
			            	if(node.getNodeType() == 1 ){	//노드 타입 : 1
			            		
			            		if (node.getNodeName().equals("EDC_NM")) {
			            			edcNm = node.getTextContent();
								} else if (node.getNodeName().equals("EDC_PURPS_CN")) {
									edcPurpsCn = node.getTextContent();
								} else if (node.getNodeName().equals("EDC_CN")) {
									edcCn = node.getTextContent();
								} else if (node.getNodeName().equals("EDC_BEGIN_DE_DT")) {
									edcBeginDeDt = node.getTextContent();
								} else if (node.getNodeName().equals("EDC_END_DE_DT")) {
									edcEndDeDt = node.getTextContent();
								} else if (node.getNodeName().equals("EDC_TIME_HM")) {
									edcTimeHm = node.getTextContent();
								} else if (node.getNodeName().equals("ATNLC_QUALF_CN")) {
									atnlcQualfCn = node.getTextContent();
								} else if (node.getNodeName().equals("LCTRUM_INFO_CN")) {
									lctrumInfoCn = node.getTextContent();
								} else if (node.getNodeName().equals("EDC_AMOUNT_AT_NM")) {
									edcAmountAtNm = node.getTextContent();
								} else if (node.getNodeName().equals("MATRL_AMOUNT_AT_NM")) {
									matrlAmountAtNm = node.getTextContent();
								} else if (node.getNodeName().equals("PSNCPA_CO")) {
									psncpaCo = Integer.parseInt(StringUtils.isNotBlank(node.getTextContent()) ? node.getTextContent() : "0");
								} else if (node.getNodeName().equals("RCRIT_BEGIN_DE_DT")) {
									rcritBeginDeDt = node.getTextContent();
								} else if (node.getNodeName().equals("RCRIT_END_DE_DT")) {
									rcritEndDeDt = node.getTextContent();
								} else if (node.getNodeName().equals("AGE_CO_NM")) {
									ageCoNm = node.getTextContent();
								} else if (node.getNodeName().equals("SEX_QUALF_CN")) {
									sexQualfCn = node.getTextContent();
								} else if (node.getNodeName().equals("STTUS_NM")) {
									sttusNm = node.getTextContent();
								} 
			            		
			            	}
			            }
			            
			          //System.out.println(updateYmdhms);
				    	String sql = "insert into " + this.tableName + 
				    			" (SEQ, "
				    			+ "EDC_NM, "
				    			+ "EDC_PURPS_CN, "
				    			+ "EDC_CN, "
				    			+ "EDC_BEGIN_DE_DT, "
				    			+ "EDC_END_DE_DT, "
				    			+ "EDC_TIME_HM, "
				    			+ "ATNLC_QUALF_CN, "
				    			+ "LCTRUM_INFO_CN, "
				    			+ "EDC_AMOUNT_AT_NM, "
				    			+ "MATRL_AMOUNT_AT_NM, "
				    			+ "PSNCPA_CO, "
				    			+ "RCRIT_BEGIN_DE_DT, "
				    			+ "RCRIT_END_DE_DT, "
				    			+ "AGE_CO_NM, "
				    			+ "SEX_QUALF_CN, "
				    			+ "STTUS_NM) "
				    			+ "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
				    	totalInsert += AsaproDBUtils.execute(sql, seq, edcNm, edcPurpsCn, edcCn, edcBeginDeDt, edcEndDeDt, edcTimeHm, 
				        		atnlcQualfCn, lctrumInfoCn, edcAmountAtNm, matrlAmountAtNm, psncpaCo, rcritBeginDeDt,
				        		rcritEndDeDt, ageCoNm, sexQualfCn, sttusNm);
				    	
				    }
					
				    this.startIndex += this.numOfRow;
				    this.endIndex += this.numOfRow;
		        	
		        }
				
		    }
	    	
	    }
        
	    LOGGER.info("[BATCH] " + apiId + " result - 총건수 : {}, 등록데이터 수 : {} ", listTotalCount, totalInsert);
		return totalInsert;
	}
	
	//============================================================================================================
	//===========================================워크넷========================================================
	//============================================================================================================
	
	/**
	 * [청년취업정책]을 json 탑입으로 리턴받아 배치한다.
	 * @param openApiInfoModel
	 * @return -1
	 * @throws ParseException 
	 */
	public int jynEmpSptListAPIJson(OpenApiInfo openApiInfoModel) throws ParseException {
		
	    LOGGER.info("[BATCH] " + apiId + " result - 총건수 : {}, 등록데이터 수 : {} ", 0, 0);
	    LOGGER.info("[BATCH] " + apiId + " 리턴타입은 현제 XML형식만 주고있음...");
		return -1;
	}
	
	/**
	 * [청년취업정책]을 xml 탑입으로 리턴받아 배치한다.
	 * @param openApiInfoModel
	 * @return 배치결과
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 */
	public int jynEmpSptListAPIXml(OpenApiInfo openApiInfoModel) throws ParserConfigurationException, SAXException, IOException  {
		
		int totalInsert = 0;
		String urlText = "";
		
		//호출 url을 만든다.
		urlText = makeUrlText();
		
        //DocumentBuilderFactory 생성
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        DocumentBuilder builder;
        Document doc = null;

        // xml 파싱하기
        //InputSource is = new InputSource(new StringReader(resultText));
        builder = factory.newDocumentBuilder();
        doc = builder.parse(urlText);
        doc.getDocumentElement().normalize();
        // 루트 엘리먼트
        Element jynEmpSptRoot = doc.getDocumentElement();	
        
         
//        String resultCode = (((Element)jynEmpSptRoot.getElementsByTagName("RESULT").item(0)).getElementsByTagName("CODE").item(0).getTextContent());
       
        int listTotalCount = 0;
        listTotalCount = Integer.parseInt(jynEmpSptRoot.getElementsByTagName("total").item(0).getTextContent());
        
	    if(listTotalCount > 0){	//처음 api 호출시 정상처리 되었을경우만
	    	
	    	//새로 받기전에 모두 삭제
		    String sqlDeleteAll = "delete from " + this.tableName;	
		    AsaproDBUtils.execute(sqlDeleteAll);
		    
		    for(int totalCall = 0;  totalCall < listTotalCount; totalCall += this.numOfRow ){
		    	//int insert = 0;
		    	
		    	urlText = makeUrlText();
				
				doc = builder.parse(urlText);
		        doc.getDocumentElement().normalize();
		        // 루트 엘리먼트
		        jynEmpSptRoot = doc.getDocumentElement();	
//		        resultCode = ((Element)jynEmpSptRoot.getElementsByTagName("RESULT").item(0)).getElementsByTagName("CODE").item(0).getTextContent();
		    	
		        if(listTotalCount > 0){	//api 호출시 정상처리 되었을경우만
		        	
		        	NodeList array = jynEmpSptRoot.getElementsByTagName("jynEmpSptList");
					   
				    for(int i = 0 ; i < array.getLength(); i++){
				    	
				        int seq = totalInsert + 1;
				        String busiId = "";
				        String busiNm = "";
				        String subBusiNm = "";
				        String dtlBusiNm = "";
				        String sptScale = "";
				        String busiSum = "";
				        String chargerOrgNm = "";
				        String onlineApplPossYn = "";
				        String applUrl = "";
				        String busiTpCd = "";
				        String detalUrl = "";
				        String ageEtcCont = "";
				        String edubgEtcCont = "";
				        String empEtcCont = "";
				        String relInfoUrl = "";
				        String chargerClcd = "";
				        
				       
				    	NodeList child = array.item(i).getChildNodes();
			            for (int j = 0; j < child.getLength(); j++) {
			            	Node node = child.item(j);
			            	if(node.getNodeType() == 1 ){	//노드 타입 : 1
			            		
			            		if (node.getNodeName().equals("busiId")) {
			            			busiId = node.getTextContent();
								} else if (node.getNodeName().equals("busiNm")) {
									busiNm = node.getTextContent();
								} else if (node.getNodeName().equals("subBusiNm")) {
									subBusiNm = node.getTextContent();
								} else if (node.getNodeName().equals("dtlBusiNm")) {
									dtlBusiNm = node.getTextContent();
								} else if (node.getNodeName().equals("sptScale")) {
									sptScale = node.getTextContent();
								} else if (node.getNodeName().equals("busiSum")) {
									busiSum = node.getTextContent();
								} else if (node.getNodeName().equals("chargerOrgNm")) {
									chargerOrgNm = node.getTextContent();
								} else if (node.getNodeName().equals("onlineApplPossYn")) {
									onlineApplPossYn = node.getTextContent();
								} else if (node.getNodeName().equals("applUrl")) {
									applUrl = node.getTextContent();
								} else if (node.getNodeName().equals("busiTpCd")) {
									busiTpCd = node.getTextContent();
								} else if (node.getNodeName().equals("detalUrl")) {
									detalUrl = node.getTextContent();
								} else if (node.getNodeName().equals("ageEtcCont")) {
									ageEtcCont = node.getTextContent();
								} else if (node.getNodeName().equals("edubgEtcCont")) {
									edubgEtcCont = node.getTextContent();
								} else if (node.getNodeName().equals("empEtcCont")) {
									empEtcCont = node.getTextContent();
								} else if (node.getNodeName().equals("relInfoUrl")) {
									relInfoUrl = node.getTextContent();
								} else if (node.getNodeName().equals("chargerClcd")) {
									chargerClcd = node.getTextContent();
								} 
			            		
			            	}
			            }
			            
			          //System.out.println(updateYmdhms);
				    	String sql = "insert into " + this.tableName + 
				    			" (SEQ, "
				    			+ "BUSI_ID, "
				    			+ "BUSI_NM, "
				    			+ "SUB_BUSI_NM, "
				    			+ "DTL_BUSI_NM, "
				    			+ "SPT_SCALE, "
				    			+ "BUSI_SUM, "
				    			+ "CHARGER_ORG_NM, "
				    			+ "ONLINE_APPL_POSS_YN, "
				    			+ "APPL_URL, "
				    			+ "BUSI_TP_CD, "
				    			+ "DETAL_URL, "
				    			+ "AGE_ETC_CONT, "
				    			+ "EDUBG_ETC_CONT, "
				    			+ "EMP_ETC_CONT, "
				    			+ "RELINFO_URL, "
				    			+ "CHARGER_CLCD) "
				    			+ "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
				    	totalInsert += AsaproDBUtils.execute(sql, seq, busiId, busiNm, subBusiNm, dtlBusiNm, sptScale, busiSum, 
				    			chargerOrgNm, onlineApplPossYn, applUrl, busiTpCd, detalUrl, ageEtcCont,
				    			edubgEtcCont, empEtcCont, relInfoUrl, chargerClcd);
				    	
				    }
					
				    this.pageNum++;
		        	
		        }
				
		    }
	    	
	    }
        
	    LOGGER.info("[BATCH] " + apiId + " result - 총건수 : {}, 등록데이터 수 : {} ", listTotalCount, totalInsert);
		return totalInsert;
	}
	
	//============================================================================================================
	//===========================================공공데이터포털========================================================
	//============================================================================================================
	
	/**
	 * [공공취업정보]를 json 탑입으로 리턴받아 배치한다.
	 * @param openApiInfoModel
	 * @return 배치 결과
	 * @throws ParseException 
	 */
	public int retrievePblinsttEmpmnInfoServiceJson(OpenApiInfo openApiInfoModel) throws ParseException {
		
		LOGGER.info("[BATCH] " + apiId + " result - 총건수 : {}, 등록데이터 수 : {} ", 0, 0);
	    LOGGER.info("[BATCH] " + apiId + " 리턴타입은 현제 XML형식만 주고있음...");
		return -1;
	}
	
	/**
	 * [공공취업정보]를 xml 탑입으로 리턴받아 배치한다.
	 * @param openApiInfoModel
	 * @return 배치결과
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 */
	public int retrievePblinsttEmpmnInfoServiceXml(OpenApiInfo openApiInfoModel) throws ParserConfigurationException, SAXException, IOException  {
		
		int totalInsert = 0;
		String urlText = "";
		String Begin_de = "";
		String End_de = "";
		
		
		this.apiSubId = "getList";	//공공취업정보 목록조회
		//호출 url을 만든다.
		urlText = makeUrlText();
		
		//데이터 수가 많아 6개월 데이터만 가져온다
		Date now = new Date();
		End_de = AsaproUtils.getFormattedDate(now);
		Begin_de = AsaproUtils.getFormattedDate(AsaproUtils.makeCalculDate(-180));
		
		urlText += "&Begin_de=" + Begin_de;
		urlText += "&End_de=" + End_de;
		//System.out.println(urlText);
        //DocumentBuilderFactory 생성
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        DocumentBuilder builder;
        Document doc = null;

        // xml 파싱하기
        //InputSource is = new InputSource(new StringReader(resultText));
        builder = factory.newDocumentBuilder();
        doc = builder.parse(urlText);
        doc.getDocumentElement().normalize();
        // 루트 엘리먼트
        Element response = doc.getDocumentElement();	
        
         
//        String resultCode = (((Element)jynEmpSptRoot.getElementsByTagName("RESULT").item(0)).getElementsByTagName("CODE").item(0).getTextContent());
       
        String resultCode = response.getElementsByTagName("resultCode").item(0).getTextContent();
        int listTotalCount = 0;
        
	    if("00".equals(resultCode)){	//처음 api 호출시 정상처리 되었을경우만
	    	listTotalCount = Integer.parseInt(response.getElementsByTagName("totalCount").item(0).getTextContent());
	    	
	    	//새로 받기전에 모두 삭제
		    String sqlDeleteAll = "delete from " + this.tableName;	
		    AsaproDBUtils.execute(sqlDeleteAll);
		    
		    for(int totalCall = 0;  totalCall < listTotalCount; totalCall += this.numOfRow){
		    	//int insert = 0;
		    	
		    	urlText = makeUrlText();
		    	urlText += "&Begin_de=" + Begin_de;
				urlText += "&End_de=" + End_de;
				
				doc = builder.parse(urlText);
		        doc.getDocumentElement().normalize();
		        // 루트 엘리먼트
		        response = doc.getDocumentElement();	
		        
		        resultCode = response.getElementsByTagName("resultCode").item(0).getTextContent();
		        
		        if("00".equals(resultCode)){	//처음 api 호출시 정상처리 되었을경우만
		        	
		        	NodeList array = response.getElementsByTagName("idx");
					   
		        	String itemUrlText = "";	//상세정보 url
		        	String itemUrlTextTemp = "";
		        	itemUrlTextTemp += this.domain;
		        	itemUrlTextTemp += "/" + this.apiId;
		        	itemUrlTextTemp += "/getItem";
		        	itemUrlTextTemp += "?";
		        	itemUrlTextTemp += "serviceKey=" + apiKey;
			    	
				    for(int i = 0 ; i < array.getLength(); i ++){
				    	String idx = array.item(i).getTextContent();
				    	
				    	itemUrlText = itemUrlTextTemp + "&idx=" + idx;
				    	doc = builder.parse(itemUrlText);
				        doc.getDocumentElement().normalize();
				        Element itemResponse = doc.getDocumentElement();
				        
				        resultCode = itemResponse.getElementsByTagName("resultCode").item(0).getTextContent();
				        
				        int seq = totalInsert + 1;
				        int readnum = 0;
				        String regdate = "";
				        String title = "";
				        String type01 = "";
				        String type02 = "";
				        String typeinfo01 = "";
				        String typeinfo02 = "";
				        String typeinfo03 = "";
				        String userid = "";
				        String username = "";
				        String empmnsn = "";
				        String contents = "";
				        String deptCode = "";
				        String deptName = "";
				        String enddate = "";
				        String link01 = "";
				        String link02 = "";
				        String link03 = "";
				        String moddate = "";
				        
				        if("00".equals(resultCode)){	//처음 api 호출시 정상처리 되었을경우만
				        	
				        	//System.out.println(idx);
						       
					    	NodeList child = itemResponse.getElementsByTagName("item").item(0).getChildNodes();
				            for (int j = 0; j < child.getLength(); j++) {
				            	Node node = child.item(j);
				            	if(node.getNodeType() == 1 ){	//노드 타입 : 1
				            		
				            		if (node.getNodeName().equals("readnum")) {
				            			readnum = Integer.parseInt(StringUtils.isNotBlank(node.getTextContent()) ? node.getTextContent() : "0");
									} else if (node.getNodeName().equals("regdate")) {
										regdate = node.getTextContent();
									} else if (node.getNodeName().equals("title")) {
										title = node.getTextContent();
									} else if (node.getNodeName().equals("type01")) {
										type01 = node.getTextContent();
									} else if (node.getNodeName().equals("type02")) {
										type02 = node.getTextContent();
									} else if (node.getNodeName().equals("typeinfo01")) {
										typeinfo01 = node.getTextContent();
									} else if (node.getNodeName().equals("typeinfo02")) {
										typeinfo02 = node.getTextContent();
									} else if (node.getNodeName().equals("typeinfo03")) {
										typeinfo03 = node.getTextContent();
									} else if (node.getNodeName().equals("userid")) {
										userid = node.getTextContent();
									} else if (node.getNodeName().equals("username")) {
										username = node.getTextContent();
									} else if (node.getNodeName().equals("empmnsn")) {
										empmnsn = node.getTextContent();
									} else if (node.getNodeName().equals("contents")) {
										contents = node.getTextContent();
									} else if (node.getNodeName().equals("deptCode")) {
										deptCode = node.getTextContent();
									} else if (node.getNodeName().equals("deptName")) {
										deptName = node.getTextContent();
									} else if (node.getNodeName().equals("enddate")) {
										enddate = node.getTextContent();
									} else if (node.getNodeName().equals("link01")) {
										link01 = node.getTextContent();
									} else if (node.getNodeName().equals("link02")) {
										link02 = node.getTextContent();
									} else if (node.getNodeName().equals("link03")) {
										link03 = node.getTextContent();
									} else if (node.getNodeName().equals("moddate")) {
										moddate = node.getTextContent();
									} 
				            		
				            	}
				            }
				            
				          //System.out.println(updateYmdhms);
					    	String sql = "insert into " + this.tableName + 
					    			" (SEQ, "
					    			+ "READNUM, "
					    			+ "REGDATE,"
					    			+ "TITLE, "
					    			+ "TYPE01, "
					    			+ "TYPE02, "
					    			+ "TYPEINFO01, "
					    			+ "TYPEINFO02, "
					    			+ "TYPEINFO03, "
					    			+ "USERID, "
					    			+ "USERNAME, "
					    			+ "EMPMNSN, "
					    			+ "CONTENTS, "
					    			+ "DEPT_CODE, "
					    			+ "DEPT_NAME, "
					    			+ "ENDDATE, "
					    			+ "LINK01, "
					    			+ "LINK02, "
					    			+ "LINK03, "
					    			+ "MODDATE) "
					    			+ "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
					    	totalInsert += AsaproDBUtils.execute(sql, seq, readnum, regdate, title, type01, type02, typeinfo01, 
					    			typeinfo02, typeinfo03, userid, username, empmnsn, contents,
					    			deptCode, deptName, enddate, link01, link02, link03, moddate);
				        }else if("99".equals(resultCode)){
				        	LOGGER.info("[BATCH] " + apiId + " 일 트래픽을 초과했습니다. ");
				        	return -99;
				        }
				    }//for array
					
				    this.startIndex += this.numOfRow;
		        	
		        } else if("99".equals(resultCode)){
		        	LOGGER.info("[BATCH] " + apiId + " 일 트래픽을 초과했습니다. ");
		        	return -99;
		        }
				
		    }//for listTotalCount
	    	
	    } else if("99".equals(resultCode)){
        	LOGGER.info("[BATCH] " + apiId + " 일 트래픽을 초과했습니다. ");
        	return -99;
        }
        
	    LOGGER.info("[BATCH] " + apiId + " result - 총건수 : {}, 등록데이터 수 : {} ", listTotalCount, totalInsert);
		return totalInsert;
	}
	
	//============================================================================================================
	//===========================================청년활동지원센터========================================================
	//============================================================================================================
			
	/**
	 * [청년활동지원정보]를 josn 타입으로 리턴받아 배치한다.
	 * @param openApiInfoModel
	 * @return 배치결과
	 * @throws ParseException
	 */
	public int youth_guaranteesJson(OpenApiInfo openApiInfoModel) throws ParseException {
		int totalInsert = 0;
		String urlText = "";
		String resultText = "";
		
		//
		
		URL url = null;
		
		//호출 url을 만든다.
		urlText = makeUrlText();
		
		try {
			url = new URL(urlText);
		} catch (MalformedURLException e) {
			LOGGER.error("[" + e.getClass() +"] URL create fail : " + e.getMessage());
		}
		
		//호출결과값을 텍스트로 담는다.
		resultText = AsaproUtils.UrlToString(url);
		
		JSONParser jsonparser = new JSONParser();
	    JSONObject jsonobject = (JSONObject)jsonparser.parse(resultText);
	    String resultCode =  (String) jsonobject.get("RESULT_CODE");
	    
	    int listTotalCount = 0;
	    if(resultCode.equals("INFO-000")){	//처음 api 호출시 정상처리 되었을경우만
	    	listTotalCount = ((Long)jsonobject.get("LIST_TOTAL_COUNT")).intValue();
		    //System.out.println("총수량 : "+listTotalCount);
		    
		  //새로 받기전에 모두 삭제
		    String sqlDeleteAll = "delete from " + this.tableName;	
		    AsaproDBUtils.execute(sqlDeleteAll);
		    
		    for(int totalCall = 0;  totalCall < listTotalCount; totalCall += this.numOfRow ){
		    	//int insert = 0;
		    	
		    	urlText = makeUrlText();
		    	//System.out.println(urlText);
		    	try {
					url = new URL(urlText);
				} catch (MalformedURLException e) {
					LOGGER.error("[" + e.getClass() +"] URL create fail : " + e.getMessage());
				}
				
				//호출결과값을 텍스트로 담는다.
				resultText = AsaproUtils.UrlToString(url);
				
		    	jsonobject = (JSONObject)jsonparser.parse(resultText);
		    	resultCode =  (String) jsonobject.get("RESULT_CODE");
		    	
		        if(resultCode.equals("INFO-000")){	//api 호출시 정상처리 되었을경우만
			    	
				    JSONArray array = (JSONArray)jsonobject.get("list");
				   
				    for(int i = 0 ; i < array.size(); i++){
				        
				        JSONObject entity = (JSONObject)array.get(i);
				        
				        int sequence = ((Long)entity.get("SEQUENCE")).intValue();
				        String title = (String) entity.get("TITLE");
				        String contents = (String) entity.get("CONTENTS");
				        String fileUrl = null;
				        JSONArray fileArray = (JSONArray) entity.get("FILE_URL");
				        if(fileArray != null && !fileArray.isEmpty() ){
				        	for(int j = 0 ; j < fileArray.size(); j++){
				        		 String file = (String)fileArray.get(j);
				        		fileUrl += file;
				        	}
				        }
				        String delYn = (String) entity.get("DEL_YN");
				        String regName = (String) entity.get("REG_NAME");
				        String regDate = (String) entity.get("REG_DATE");
				        String updName = (String) entity.get("UPD_NAME");
				        String updDate = (String) entity.get("UPD_DATE");
				        
				        
				        //System.out.println(updateYmdhms);
				    	String sql = "insert into " + this.tableName + 
				    			" (SEQUENCE, "
				    			+ "TITLE, "
				    			+ "CONTENTS, "
				    			+ "FILE_URL, "
				    			+ "DEL_YN, "
				    			+ "REG_NAME, "
				    			+ "REG_DATE, "
				    			+ "UPD_NAME, "
				    			+ "UPD_DATE) "
				    			+ "values(?,?,?,?,?,?,?,?,?)";
				    	totalInsert += AsaproDBUtils.execute(sql, sequence, title, contents, fileUrl, delYn, regName, regDate, 
				    			updName, updDate);
				    	
				    }
					
				    this.pageNum++;
			    	
		    	}
		    	
		    }
		    
	    }
	    LOGGER.info("[BATCH] " + apiId + " result - 총건수 : {}, 등록데이터 수 : {} ", listTotalCount, totalInsert);
		return totalInsert;
	}
	
	
	/**
	 * [청년활동지원정보]을 xml 탑입으로 리턴받아 배치한다.
	 * @param openApiInfoModel
	 * @return -1
	 * @throws ParseException 
	 */
	public int youth_guaranteesXml(OpenApiInfo openApiInfoModel) throws ParseException {
		
		LOGGER.info("[BATCH] " + apiId + " result - 총건수 : {}, 등록데이터 수 : {} ", 0, 0);
		LOGGER.info("[BATCH] " + apiId + " 리턴타입은 현제 JSON형식만 주고있음...");
		return -1;
	}
	
	
	
	/**
	 * [지역별청년행사정보]를 josn 타입으로 리턴받아 배치한다.
	 * @param openApiInfoModel
	 * @return 배치결과
	 * @throws ParseException
	 */
	public int neighborhoodsJson(OpenApiInfo openApiInfoModel) throws ParseException {
		int totalInsert = 0;
		String urlText = "";
		String resultText = "";
		
		//
		
		URL url = null;
		
		//호출 url을 만든다.
		urlText = makeUrlText();
		
		try {
			url = new URL(urlText);
		} catch (MalformedURLException e) {
			LOGGER.error("[" + e.getClass() +"] URL create fail : " + e.getMessage());
		}
		
		//호출결과값을 텍스트로 담는다.
		resultText = AsaproUtils.UrlToString(url);
		
		JSONParser jsonparser = new JSONParser();
	    JSONObject jsonobject = (JSONObject)jsonparser.parse(resultText);
	    String resultCode =  (String) jsonobject.get("RESULT_CODE");
	    
	    int listTotalCount = 0;
	    if(resultCode.equals("INFO-000")){	//처음 api 호출시 정상처리 되었을경우만
	    	listTotalCount = ((Long)jsonobject.get("LIST_TOTAL_COUNT")).intValue();
		    //System.out.println("총수량 : "+listTotalCount);
		    
		  //새로 받기전에 모두 삭제
		    String sqlDeleteAll = "delete from " + this.tableName;	
		    AsaproDBUtils.execute(sqlDeleteAll);
		    
		    for(int totalCall = 0;  totalCall < listTotalCount; totalCall += this.numOfRow ){
		    	//int insert = 0;
		    	
		    	urlText = makeUrlText();
		    	//System.out.println(urlText);
		    	try {
					url = new URL(urlText);
				} catch (MalformedURLException e) {
					LOGGER.error("[" + e.getClass() +"] URL create fail : " + e.getMessage());
				}
				
				//호출결과값을 텍스트로 담는다.
				resultText = AsaproUtils.UrlToString(url);
				
		    	jsonobject = (JSONObject)jsonparser.parse(resultText);
		    	resultCode =  (String) jsonobject.get("RESULT_CODE");
		    	
		        if(resultCode.equals("INFO-000")){	//api 호출시 정상처리 되었을경우만
			    	
				    JSONArray array = (JSONArray)jsonobject.get("list");
				   
				    for(int i = 0 ; i < array.size(); i++){
				        
				        JSONObject entity = (JSONObject)array.get(i);
				        
				        int sequence = ((Long)entity.get("SEQUENCE")).intValue();
				        String title = (String) entity.get("TITLE");
				        String contents = (String) entity.get("CONTENTS");
				        String fileUrl = null;
				        JSONArray fileArray = (JSONArray) entity.get("FILE_URL");
				        if(fileArray != null && !fileArray.isEmpty() ){
				        	for(int j = 0 ; j < fileArray.size(); j++){
				        		 String file = (String)fileArray.get(j);
				        		fileUrl += file;
				        	}
				        }
				        String delYn = (String) entity.get("DEL_YN");
				        String regName = (String) entity.get("REG_NAME");
				        String regDate = (String) entity.get("REG_DATE");
				        String updName = (String) entity.get("UPD_NAME");
				        String updDate = (String) entity.get("UPD_DATE");
				        
				        
				        //System.out.println(updateYmdhms);
				    	String sql = "insert into " + this.tableName + 
				    			" (SEQUENCE, "
				    			+ "TITLE, "
				    			+ "CONTENTS, "
				    			+ "FILE_URL, "
				    			+ "DEL_YN, "
				    			+ "REG_NAME, "
				    			+ "REG_DATE, "
				    			+ "UPD_NAME, "
				    			+ "UPD_DATE) "
				    			+ "values(?,?,?,?,?,?,?,?,?)";
				    	totalInsert += AsaproDBUtils.execute(sql, sequence, title, contents, fileUrl, delYn, regName, regDate, 
				    			updName, updDate);
				    	
				    }
					
				    this.pageNum++;
			    	
		    	}
		    	
		    }
		    
	    }
	    LOGGER.info("[BATCH] " + apiId + " result - 총건수 : {}, 등록데이터 수 : {} ", listTotalCount, totalInsert);
		return totalInsert;
	}
	
	/**
	 * [지역별청년행사정보]을 xml 탑입으로 리턴받아 배치한다.
	 * @param openApiInfoModel
	 * @return -1
	 * @throws ParseException 
	 */
	public int neighborhoodsXml(OpenApiInfo openApiInfoModel) throws ParseException {
		
		LOGGER.info("[BATCH] " + apiId + " result - 총건수 : {}, 등록데이터 수 : {} ", 0, 0);
		LOGGER.info("[BATCH] " + apiId + " 리턴타입은 현제 JSON형식만 주고있음...");
		return -1;
	}
	
	
	
	/**
	 * [청년 프로그램]를 josn 타입으로 리턴받아 배치한다.
	 * @param openApiInfoModel
	 * @return 배치결과
	 * @throws ParseException
	 */
	public int programsJson(OpenApiInfo openApiInfoModel) throws ParseException {
		int totalInsert = 0;
		String urlText = "";
		String resultText = "";
		
		//
		
		URL url = null;
		
		//호출 url을 만든다.
		urlText = makeUrlText();
		
		try {
			url = new URL(urlText);
		} catch (MalformedURLException e) {
			LOGGER.error("[" + e.getClass() +"] URL create fail : " + e.getMessage());
		}
		
		//호출결과값을 텍스트로 담는다.
		resultText = AsaproUtils.UrlToString(url);
		
		JSONParser jsonparser = new JSONParser();
	    JSONObject jsonobject = (JSONObject)jsonparser.parse(resultText);
	    String resultCode =  (String) jsonobject.get("RESULT_CODE");
	    
	    int listTotalCount = 0;
	    if(resultCode.equals("INFO-000")){	//처음 api 호출시 정상처리 되었을경우만
	    	listTotalCount = ((Long)jsonobject.get("LIST_TOTAL_COUNT")).intValue();
		    //System.out.println("총수량 : "+listTotalCount);
		    
		  //새로 받기전에 모두 삭제
		    String sqlDeleteAll = "delete from " + this.tableName;	
		    AsaproDBUtils.execute(sqlDeleteAll);
		    
		    for(int totalCall = 0;  totalCall < listTotalCount; totalCall += this.numOfRow ){
		    	//int insert = 0;
		    	
		    	urlText = makeUrlText();
		    	//System.out.println(urlText);
		    	try {
					url = new URL(urlText);
				} catch (MalformedURLException e) {
					LOGGER.error("[" + e.getClass() +"] URL create fail : " + e.getMessage());
				}
				
				//호출결과값을 텍스트로 담는다.
				resultText = AsaproUtils.UrlToString(url);
				
		    	jsonobject = (JSONObject)jsonparser.parse(resultText);
		    	resultCode =  (String) jsonobject.get("RESULT_CODE");
		    	
		        if(resultCode.equals("INFO-000")){	//api 호출시 정상처리 되었을경우만
			    	
				    JSONArray array = (JSONArray)jsonobject.get("list");
				   
				    for(int i = 0 ; i < array.size(); i++){
				        
				        JSONObject entity = (JSONObject)array.get(i);
				        
				        int sequence = ((Long)entity.get("SEQUENCE")).intValue();
				        String supportBizCode = (String) entity.get("SUPPORT_BIZ_CODE");
				        String title = (String) entity.get("TITLE");
				        String contents = (String) entity.get("CONTENTS");
				        String receptionStartDate = (String) entity.get("RECEPTION_START_DATE");
				        String receptionEndDate = (String) entity.get("RECEPTION_END_DATE");
				        String educationStartDate = (String) entity.get("EDUCATION_START_DATE");
				        String educationEndDate = (String) entity.get("EDUCATION_END_DATE");
				        String lineExplanation = (String) entity.get("LINE_EXPLANATION");
				        String fee = (String) entity.get("FEE");
				        int personnel = ((Long)entity.get("PERSONNEL")).intValue();
				        int participantNumber = ((Long)entity.get("PARTICIPANT_NUMBER")).intValue();
				        String applicationLink = (String) entity.get("APPLICATION_LINK");
				        String fileUrl = null;
				        JSONArray fileArray = (JSONArray) entity.get("FILE_URL");
				        if(fileArray != null && !fileArray.isEmpty() ){
				        	for(int j = 0 ; j < fileArray.size(); j++){
				        		 String file = (String)fileArray.get(j);
				        		fileUrl += file;
				        	}
				        }
				        String openYn = (String) entity.get("OPEN_YN");
				        String delYn = (String) entity.get("DEL_YN");
				        String regName = (String) entity.get("REG_NAME");
				        String regDate = (String) entity.get("REG_DATE");
				        String updName = (String) entity.get("UPD_NAME");
				        String updDate = (String) entity.get("UPD_DATE");
				        
				        
				        
				        
				        //System.out.println(updateYmdhms);
				    	String sql = "insert into " + this.tableName + 
				    			" (SEQUENCE, "
								+ "SUPPORT_BIZ_CODE, "
				    			+ "TITLE, "
				    			+ "CONTENTS, "
								+ "RECEPTION_START_DATE, "
								+ "RECEPTION_END_DATE, "
								+ "EDUCATION_START_DATE, "
								+ "EDUCATION_END_DATE, "
								+ "LINE_EXPLANATION, "
								+ "FEE, "
								+ "PERSONNEL, "
								+ "PARTICIPANT_NUMBER, "
								+ "APPLICATION_LINK, "
				    			+ "FILE_URL, "
				    			+ "OPEN_YN, "
				    			+ "DEL_YN, "
				    			+ "REG_NAME, "
				    			+ "REG_DATE, "
				    			+ "UPD_NAME, "
				    			+ "UPD_DATE) "
				    			+ "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
				    	totalInsert += AsaproDBUtils.execute(sql, sequence, supportBizCode, title, contents, receptionStartDate, receptionEndDate, educationStartDate, educationEndDate, 
				    			lineExplanation, fee, personnel,participantNumber, applicationLink,	fileUrl, openYn, delYn, regName, regDate, updName, updDate);
				    	
				    }
					
				    this.pageNum++;
			    	
		    	}
		    	
		    }
		    
	    }
	    LOGGER.info("[BATCH] " + apiId + " result - 총건수 : {}, 등록데이터 수 : {} ", listTotalCount, totalInsert);
		return totalInsert;
	}
	
	/**
	 * [청년 프로그램]을 xml 탑입으로 리턴받아 배치한다.
	 * @param openApiInfoModel
	 * @return -1
	 * @throws ParseException 
	 */
	public int programsXml(OpenApiInfo openApiInfoModel) throws ParseException {
		
		LOGGER.info("[BATCH] " + apiId + " result - 총건수 : {}, 등록데이터 수 : {} ", 0, 0);
		LOGGER.info("[BATCH] " + apiId + " 리턴타입은 현제 JSON형식만 주고있음...");
		return -1;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	//================================================================================================================
	//================================================================================================================
	//================================================================================================================
	/**
	 * api 제공 사이트별로 url 텍스트를 만들어 반환한다.
	 * @return url
	 */
	public String makeUrlText() {
		String urlText = "";
		
		//api 제공 사이트 구분 
		//코드관리에 등록된 코드 (카테고리 provider)
		if("data_seoul".equals(this.provider) ){	//서울열린데이터광장
			urlText += domain;
			urlText += "/" + this.apiKey;
			urlText += "/" + this.returnType;
			urlText += "/" + this.apiId;
			urlText += "/" + this.startIndex;
			urlText += "/" + this.endIndex;
		}else if("worknet".equals(this.provider)) {	//워크넷
			urlText += this.domain;
			urlText += "/" + this.apiId;
			urlText += ".do?";
			urlText += "authKey=" + this.apiKey;
			urlText += "&returnType=" + this.returnType;
			urlText += "&startPage=" + this.pageNum;
			urlText += "&display=" + this.numOfRow;
		}else if("data".equals(this.provider)) {	//공공데이터포털
			urlText += this.domain;
			urlText += "/" + this.apiId;
			if(StringUtils.isNotBlank(this.apiSubId) ){
				urlText += "/" + this.apiSubId;
			}
			urlText += "?";
			urlText += "serviceKey=" + this.apiKey;
			urlText += "&returnType=" + this.returnType;
			urlText += "&pageNo=" + this.startIndex;
			urlText += "&numOfRows=" + this.numOfRow;
			
		}else if("sygc".equals(this.provider)) {	//청년활동지원센터
			urlText += this.domain;
			urlText += "/" + this.apiId;
			urlText += "?page=" + this.pageNum;
			urlText += "&page_per=" + this.numOfRow;
		}
		return urlText;
	}
	
	//==========================================================================

}
