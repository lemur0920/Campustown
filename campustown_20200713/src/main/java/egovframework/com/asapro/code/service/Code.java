/**
 * 
 */
package egovframework.com.asapro.code.service;

import java.io.Serializable;
import java.util.Date;

//import egovframework.com.asapro.watchdog.aop.WatchDog;



/**
 * 상세코드 VO
 * @author yckim
 * @since 2018. 4. 3.
 *
 */
@SuppressWarnings("serial")
public class Code implements Serializable {
	
	//@WatchDog
	private String codeId;	//코드 아이디
	//@WatchDog
	private CodeCategory codeCategory;	//코드 분류 아이디
	private String codeName;	//코드 이름
	private String codeNameEn;	 //코드 영문 이름
	private String codeDescription;	// 코드 설명
	private Date codeRegDate;	//등록일시
	private boolean codeUse = true; //코드 사용유뮤 - 기본값 사용
	private Integer codeOrder;	//코드 정렬순서
	
	private int cnt;	//수량
	/**
	 * @return the codeId
	 */
	public String getCodeId() {
		return codeId;
	}
	/**
	 * @param codeId the codeId to set
	 */
	public void setCodeId(String codeId) {
		this.codeId = codeId;
	}
	/**
	 * @return the codeCategory
	 */
	public CodeCategory getCodeCategory() {
		return codeCategory;
	}
	/**
	 * @param codeCategory the codeCategory to set
	 */
	public void setCodeCategory(CodeCategory codeCategory) {
		this.codeCategory = codeCategory;
	}
	/**
	 * @return the codeName
	 */
	public String getCodeName() {
		return codeName;
	}
	/**
	 * @param codeName the codeName to set
	 */
	public void setCodeName(String codeName) {
		this.codeName = codeName;
	}
	/**
	 * @return the codeDescription
	 */
	public String getCodeDescription() {
		return codeDescription;
	}
	/**
	 * @param codeDescription the codeDescription to set
	 */
	public void setCodeDescription(String codeDescription) {
		this.codeDescription = codeDescription;
	}
	/**
	 * @return the codeRegDate
	 */
	public Date getCodeRegDate() {
		return codeRegDate;
	}
	/**
	 * @param codeRegDate the codeRegDate to set
	 */
	public void setCodeRegDate(Date codeRegDate) {
		this.codeRegDate = codeRegDate;
	}
	/**
	 * @return the codeUse
	 */
	public boolean isCodeUse() {
		return codeUse;
	}
	/**
	 * @param codeUse the codeUse to set
	 */
	public void setCodeUse(boolean codeUse) {
		this.codeUse = codeUse;
	}
	/**
	 * @return the codeNameEn
	 */
	public String getCodeNameEn() {
		return codeNameEn;
	}
	/**
	 * @param codeNameEn the codeNameEn to set
	 */
	public void setCodeNameEn(String codeNameEn) {
		this.codeNameEn = codeNameEn;
	}
	/**
	 * @return the codeOrder
	 */
	public Integer getCodeOrder() {
		return codeOrder;
	}
	/**
	 * @param codeOrder the codeOrder to set
	 */
	public void setCodeOrder(Integer codeOrder) {
		this.codeOrder = codeOrder;
	}
	/**
	 * @return the cnt
	 */
	public int getCnt() {
		return cnt;
	}
	/**
	 * @param cnt the cnt to set
	 */
	public void setCnt(int cnt) {
		this.cnt = cnt;
	}
	
}
