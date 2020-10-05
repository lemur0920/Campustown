/**
 * 
 */
package egovframework.com.asapro.config.service;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

/**
 * CMS 설정 VO
 * @author yckim
 * @since 2018. 8. 6.
 *
 */
public class Config implements Serializable {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(Config.class);
	
	private String confId;	//설정 아이디
	private String sitePrefix;	//사이트 Prefix
	private List<ConfigOption> options;	//설정 옵션 목록
	private Map<String, String> optionMap = new HashMap<String, String>();	//jsp 뷰단에서 꺼내쓰려고 만든 변수임. 자바단에서는 쓸일 없음.
	private MultipartFile watermarkImage;	//워터마그 이미지 파일
	
	/**
	 * 기본 생성자
	 */
	public Config(){
		
	}
	
	/**
	 * 옵션을 인자로 받는 생성자
	 * @param options
	 */
	public Config(List<ConfigOption> options) {
		this.options = options;
	}

	/**
	 * @return the options
	 */
	public List<ConfigOption> getOptions() {
		return options;
	}

	/**
	 * @param options the options to set
	 */
	public void setOptions(List<ConfigOption> options) {
		this.options = options;
	}
	
	/**
	 * 옵션값을 반환한다.
	 * @param optKey
	 * @return 옵션값
	 */
	public String getOption(String optKey){
		for( ConfigOption configOption : options ){
			if( optKey.equals(configOption.getOptKey()) ){
				if( configOption.getOptValue() == null ){
					LOGGER.info("[ASAPRO] Config option value for option key [{}] is null, check option table.", optKey);
					return "";
				} else {
					return configOption.getOptValue();
				}
			}
		}
		return null;
	}

	/**
	 * @return the optionMap
	 */
	public Map<String, String> getOptionMap() {
		if( this.optionMap.isEmpty() ){
			for( ConfigOption configOption : options ){
				if( StringUtils.isNotBlank(configOption.getOptKey()) ){
					optionMap.put(configOption.getOptKey(), configOption.getOptValue());
				}
			}
		}
		return optionMap;
	}

	/**
	 * @param optionMap the optionMap to set
	 */
	public void setOptionMap(Map<String, String> optionMap) {
		this.optionMap = optionMap;
	}
	
	/**
	 * 옵션값을 변경한다.(DB에서 바뀌는 것은 아님.)
	 * <pre>
	 * 없는 옵션이 추가되지은 않음.
	 * </pre>
	 * @param key
	 * @param value
	 */
	public void updateOption(String key, String value){
		for( ConfigOption configOption : options ){
			if( configOption.getOptKey().equals(key) ){
				configOption.setOptValue(value);
				break;
			}
		}
	}

	/**
	 * @return the watermarkImage
	 */
	public MultipartFile getWatermarkImage() {
		return watermarkImage;
	}

	/**
	 * @param watermarkImage the watermarkImage to set
	 */
	public void setWatermarkImage(MultipartFile watermarkImage) {
		this.watermarkImage = watermarkImage;
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
	
}
