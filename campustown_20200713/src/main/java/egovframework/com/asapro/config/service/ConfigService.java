/**
 * 
 */
package egovframework.com.asapro.config.service;

import egovframework.com.asapro.site.service.Site;

/**
 * 설정 서비스
 * @author yckim
 * @since 2018. 8. 6.
 *
 */
public interface ConfigService {
	
	/**
	 * 설정을 조회한다.
	 * <pre>
	 * confId 가 global 이거나 mail 인 경우는 강제적으로 메인사이트의 옵션을 반환하도록 한다.
	 * - 모드사이트가 독립적으로 설정하도록 변경
	 * </pre>
	 * @param key
	 * @return
	 */
	public Config getConfig(String confId);
		
	/**
	 * 캐시되지 않은 상태의 설정옵션을 조회한다.(캐시무시하고 디비에서 바로 가져옴)
	 * @param key
	 * @return
	 */
	public ConfigOption getNoCacheOption(String confId, String optKey);
	
	/**
	 * 캐시되지 않은 상태의 메인사이트의 설정옵션을 조회한다.(캐시무시하고 디비에서 바로 가져옴)
	 * @param key
	 * @return
	 */
	public ConfigOption getNoCacheGlobalOption(String confId, String optKey);
	
	/**
	 * 복수의 설정을 수정한다.
	 * @param configs
	 * @return 수정 결과
	 */
	public int updateConfig(Config config);

	/**
	 * 설정옵션을 수정한다. 
	 * <br/>
	 * 없는 옵션이면 새로 추가한다.
	 * 
	 * @param configOptionForm
	 * @return 수정 결과
	 */
	public int updateConfigOption(ConfigOption configOptionForm);
	
	/**
	 * 텍스트 데이터를 조회한다.
	 * @param textGroup
	 * @param textId
	 * @return
	 */
	public TextData getTextData(String textGroup, String textId);
	
	/**
	 * 텍스트 데이터 그룹을 조회한다.
	 * @param textGroup
	 * @return
	 */
	public TextDataGroup getTextDataGroup(String textGroup);

	/**
	 * 텍스트 데이터 설정 수정
	 * @param textDataGroupForm
	 * @return
	 */
	public int updateTextDataGroup(TextDataGroup textDataGroupForm);

	/**
	 * 캐쉬 안된 설정 옵션을 조회한다.
	 * @param string
	 * @return 캐쉬안된 설정 옵션
	 */
	public Config getNoCacheConfig(String string);
	
}