/**
 * 
 */
package egovframework.com.asapro.code.service;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

//import egovframework.com.asapro.watchdog.aop.WatchDog;

/**
 * 코드 분류
 * @author yckim
 * @since 2018. 4. 3.
 *
 */
@SuppressWarnings("serial")
public class CodeCategory implements Serializable{
	
	//@WatchDog
	private String catId;
	private String catName;
	private String catNameEn;
	private String catDescription;
	private Date catRegDate;
	private List<Code> codeList;
	
	/**
	 * @return the catId
	 */
	public String getCatId() {
		return catId;
	}
	/**
	 * @param catId the catId to set
	 */
	public void setCatId(String catId) {
		this.catId = catId;
	}
	/**
	 * @return the catName
	 */
	public String getCatName() {
		return catName;
	}
	/**
	 * @param catName the catName to set
	 */
	public void setCatName(String catName) {
		this.catName = catName;
	}
	/**
	 * @return the catDescription
	 */
	public String getCatDescription() {
		return catDescription;
	}
	/**
	 * @param catDescription the catDescription to set
	 */
	public void setCatDescription(String catDescription) {
		this.catDescription = catDescription;
	}
	/**
	 * @return the catRegDate
	 */
	public Date getCatRegDate() {
		return catRegDate;
	}
	/**
	 * @param catRegDate the catRegDate to set
	 */
	public void setCatRegDate(Date catRegDate) {
		this.catRegDate = catRegDate;
	}
	/**
	 * @return the codeList
	 */
	public List<Code> getCodeList() {
		return codeList;
	}
	/**
	 * @param codeList the codeList to set
	 */
	public void setCodeList(List<Code> codeList) {
		this.codeList = codeList;
	}
	/**
	 * @return the catNameEn
	 */
	public String getCatNameEn() {
		return catNameEn;
	}
	/**
	 * @param catNameEn the catNameEn to set
	 */
	public void setCatNameEn(String catNameEn) {
		this.catNameEn = catNameEn;
	}
	
}
