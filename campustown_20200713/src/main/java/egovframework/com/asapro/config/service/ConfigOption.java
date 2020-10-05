/**
 * 
 */
package egovframework.com.asapro.config.service;

import java.io.Serializable;

import egovframework.com.asapro.site.service.MultiSiteVO;
//import egovframework.com.asapro.watchdog.aop.WatchDog;

/**
 * 설정옵션 VO
 * <pre>
 * - 개발자 아니면 설정 추가할 일 없음으로 설정 추가할때는 데이터베이스에 그냥 직접 입력.
 * </pre>
 * @author yckim
 * @since 2018. 8. 6.
 *
 */
@SuppressWarnings("serial")
public class ConfigOption extends MultiSiteVO implements Serializable {
	
//	@WatchDog
	private String confId;	//설정 아이디 - confId + optKey = unique
	private String optKey;	//설정 키 - confId + optKey = unique
	private String optValue;	//설정 값
	private String optName;	//설정 이름
	private String optType = "text";	//설정유형 text,radio,checkbox,textarea
	private boolean optHidden = false;	//숨김여부
	private String optUnitText;	//단위텍스트
	private String optHelp;	//도움말
	
	public ConfigOption(){
		
	}
	
	public ConfigOption(String confId, String optKey) {
		super();
		this.confId = confId;
		this.optKey = optKey;
	}
	/**
	 * @return the confId
	 */
	public String getConfId() {
		return confId;
	}
	/**
	 * @param confId the confId to set
	 */
	public void setConfId(String confId) {
		this.confId = confId;
	}
	/**
	 * @return the optKey
	 */
	public String getOptKey() {
		return optKey;
	}
	/**
	 * @param optKey the optKey to set
	 */
	public void setOptKey(String optKey) {
		this.optKey = optKey;
	}
	/**
	 * @return the optValue
	 */
	public String getOptValue() {
		return optValue;
	}
	/**
	 * @param optValue the optValue to set
	 */
	public void setOptValue(String optValue) {
		this.optValue = optValue;
	}
	/**
	 * @return the optName
	 */
	public String getOptName() {
		return optName;
	}
	/**
	 * @param optName the optName to set
	 */
	public void setOptName(String optName) {
		this.optName = optName;
	}
	/**
	 * @return the optHelp
	 */
	public String getOptHelp() {
		return optHelp;
	}
	/**
	 * @param optHelp the optHelp to set
	 */
	public void setOptHelp(String optHelp) {
		this.optHelp = optHelp;
	}
	/**
	 * @return the optType
	 */
	public String getOptType() {
		return optType;
	}
	/**
	 * @param optType the optType to set
	 */
	public void setOptType(String optType) {
		this.optType = optType;
	}
	/**
	 * @return the optHidden
	 */
	public boolean isOptHidden() {
		return optHidden;
	}
	/**
	 * @param optHidden the optHidden to set
	 */
	public void setOptHidden(boolean optHidden) {
		this.optHidden = optHidden;
	}

	/**
	 * @return the optUnitText
	 */
	public String getOptUnitText() {
		return optUnitText;
	}

	/**
	 * @param optUnitText the optUnitText to set
	 */
	public void setOptUnitText(String optUnitText) {
		this.optUnitText = optUnitText;
	}
}
