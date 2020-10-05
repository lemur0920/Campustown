/**
 * 
 */
package egovframework.com.asapro.config.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import egovframework.com.asapro.site.service.MultiSiteVO;

/**
 * 텍스트 데이터 그룹
 * @author yckim
 * @since 2018. 6. 6.
 *
 */
public class TextDataGroup extends MultiSiteVO {
	
	private List<TextData> textDataList;
	private Map<String, String> map = null;
	
	/**
	 * @return the textDataList
	 */
	public List<TextData> getTextDataList() {
		return textDataList;
	}

	/**
	 * @param textDataList the textDataList to set
	 */
	public void setTextDataList(List<TextData> textDataList) {
		this.textDataList = textDataList;
	}

	/**
	 * 텍스트 옵션데이터를 맵형태로 반환한다.
	 * @return
	 */
	public Map<String, String> getMap() {
		if( this.map == null ){
			this.map = new HashMap<String, String>();
		}
		if( this.map.isEmpty() ){
			for( TextData textData : textDataList ){
				if( StringUtils.isNotBlank(textData.getTextContent()) ){
					map.put(textData.getTextId(), textData.getTextContent());
				}
			}
		}
		return map;
	}
	
}
