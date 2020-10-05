/**
 * 
 */
package egovframework.com.asapro.config.service;

import java.io.Serializable;

//import egovframework.com.asapro.watchdog.aop.WatchDog;

/**
 * 텍스트 옵션 데이터
 * <pre>
 * - 1000자 넘는 대용량 옵션같은 텍스트 데이터 저장용으로 사용한다.
 * - ASA_CONFIG_OPTION 테이블에 같이 넣으면 좋겠지만 오라클은 CLOB여야 하므로 별도의 테이블을 사용하도록처리함.
 * </pre>
 * 
 * @author yckim
 * @since 2018. 8. 6.
 *
 */
@SuppressWarnings("serial")
public class TextData implements Serializable{
	
//	@WatchDog
	private String textGroup;
	private String textId;
	private String textName;
	private String textContent;
	private String sitePrefix;
	
	/**
	 * @return the sitePrefix
	 */
	public String getSitePrefix() {
		return sitePrefix;
	}
	/**
	 * @param sitePrefix the sitePrefix to set
	 */
	public void setSitePrefix(String sitePrefix) {
		this.sitePrefix = sitePrefix;
	}
	/**
	 * @return the textGroup
	 */
	public String getTextGroup() {
		return textGroup;
	}
	/**
	 * @param textGroup the textGroup to set
	 */
	public void setTextGroup(String textGroup) {
		this.textGroup = textGroup;
	}
	/**
	 * @return the textId
	 */
	public String getTextId() {
		return textId;
	}
	/**
	 * @param textId the textId to set
	 */
	public void setTextId(String textId) {
		this.textId = textId;
	}
	/**
	 * @return the textName
	 */
	public String getTextName() {
		return textName;
	}
	/**
	 * @param textName the textName to set
	 */
	public void setTextName(String textName) {
		this.textName = textName;
	}
	/**
	 * @return the textContent
	 */
	public String getTextContent() {
		return textContent;
	}
	/**
	 * @param textContent the textContent to set
	 */
	public void setTextContent(String textContent) {
		this.textContent = textContent;
	}
	
}
