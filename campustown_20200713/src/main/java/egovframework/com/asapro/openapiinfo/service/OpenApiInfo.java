/**
 * 
 */
package egovframework.com.asapro.openapiinfo.service;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import egovframework.com.asapro.site.service.MultiSiteVO;


/**
 * 오픈 api 정보 VO
 * @author yckim
 * @since 2019. 2. 12.
 *
 */
public class OpenApiInfo extends MultiSiteVO{
	
	private Integer apiSeq;	//연번
	private String apiId;	//api 영문명 (영문 서비스명)
	private String apiTitle;	//api 한글명
	private String apiContent;	//api 설명
	private Date apiRegDate;	//등록일시
	private String apiProvider;	//api 제공 사이트
	private String apiDomain;	//api 도메인
	private String apiKey;	//api 키
	private String apiReturnType;	//api 결과 유형(xml, json)
	private Integer apiNumOfRow;	//한번에 가져올 결과 수
	private String apiTableName;	//배치할 테이블 명
	private Date apiLastBatch;	//마지막 배치일자
	private String apiInputType;	//배치실행구분 (auto, manual)
	
	private int pageNum = 1;
	private int startIndex = 1;
	private int endIndex = 1;
	
	/**
	 * @return the apiSeq
	 */
	public Integer getApiSeq() {
		return apiSeq;
	}
	/**
	 * @param apiSeq the apiSeq to set
	 */
	public void setApiSeq(Integer apiSeq) {
		this.apiSeq = apiSeq;
	}
	/**
	 * @return the apiId
	 */
	public String getApiId() {
		return apiId;
	}
	/**
	 * @param apiId the apiId to set
	 */
	public void setApiId(String apiId) {
		this.apiId = apiId;
	}
	/**
	 * @return the apiTitle
	 */
	public String getApiTitle() {
		return apiTitle;
	}
	/**
	 * @param apiTitle the apiTitle to set
	 */
	public void setApiTitle(String apiTitle) {
		this.apiTitle = apiTitle;
	}
	/**
	 * @return the apiContent
	 */
	public String getApiContent() {
		return apiContent;
	}
	/**
	 * @param apiContent the apiContent to set
	 */
	public void setApiContent(String apiContent) {
		this.apiContent = apiContent;
	}
	/**
	 * @return the apiRegDate
	 */
	public Date getApiRegDate() {
		return apiRegDate;
	}
	/**
	 * @param apiRegDate the apiRegDate to set
	 */
	public void setApiRegDate(Date apiRegDate) {
		this.apiRegDate = apiRegDate;
	}
	/**
	 * @return the apiProvider
	 */
	public String getApiProvider() {
		return apiProvider;
	}
	/**
	 * @param apiProvider the apiProvider to set
	 */
	public void setApiProvider(String apiProvider) {
		this.apiProvider = apiProvider;
	}
	/**
	 * @return the apiDomain
	 */
	public String getApiDomain() {
		return apiDomain;
	}
	/**
	 * @param apiDomain the apiDomain to set
	 */
	public void setApiDomain(String apiDomain) {
		this.apiDomain = apiDomain;
	}
	/**
	 * @return the apiKey
	 */
	public String getApiKey() {
		return apiKey;
	}
	/**
	 * @param apiKey the apiKey to set
	 */
	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}
	/**
	 * @return the apiReturnType
	 */
	public String getApiReturnType() {
		return apiReturnType;
	}
	/**
	 * @param apiReturnType the apiReturnType to set
	 */
	public void setApiReturnType(String apiReturnType) {
		this.apiReturnType = apiReturnType;
	}
	/**
	 * @return the apiNumOfRow
	 */
	public Integer getApiNumOfRow() {
		return apiNumOfRow;
	}
	/**
	 * @param apiNumOfRow the apiNumOfRow to set
	 */
	public void setApiNumOfRow(Integer apiNumOfRow) {
		this.apiNumOfRow = apiNumOfRow;
	}
	/**
	 * @return the apiTableName
	 */
	public String getApiTableName() {
		return apiTableName;
	}
	/**
	 * @param apiTableName the apiTableName to set
	 */
	public void setApiTableName(String apiTableName) {
		this.apiTableName = apiTableName;
	}
	/**
	 * @return the apiLastBatch
	 */
	public Date getApiLastBatch() {
		return apiLastBatch;
	}
	/**
	 * @param apiLastBatch the apiLastBatch to set
	 */
	public void setApiLastBatch(Date apiLastBatch) {
		this.apiLastBatch = apiLastBatch;
	}
	/**
	 * @return the apiInputType
	 */
	public String getApiInputType() {
		return apiInputType;
	}
	/**
	 * @param apiInputType the apiInputType to set
	 */
	public void setApiInputType(String apiInputType) {
		this.apiInputType = apiInputType;
	}
	/**
	 * @return the pageNum
	 */
	public int getPageNum() {
		return pageNum;
	}
	/**
	 * @param pageNum the pageNum to set
	 */
	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}
	/**
	 * @return the startIndex
	 */
	public int getStartIndex() {
		return startIndex;
	}
	/**
	 * @param startIndex the startIndex to set
	 */
	public void setStartIndex(int startIndex) {
		this.startIndex = startIndex;
	}
	/**
	 * @return the endIndex
	 */
	public int getEndIndex() {
		return endIndex;
	}
	/**
	 * @param endIndex the endIndex to set
	 */
	public void setEndIndex(int endIndex) {
		this.endIndex = endIndex;
	}
	
	
	
	
}
