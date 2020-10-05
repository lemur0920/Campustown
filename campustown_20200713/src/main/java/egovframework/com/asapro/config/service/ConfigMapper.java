/**
 * 
 */
package egovframework.com.asapro.config.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * 설정 SQL Mapper
 * @author yckim
 * @since 2018. 8. 6.
 *
 */
@Mapper
public interface ConfigMapper {
	
	/**
	 * 설정을 조회한다.
	 * @param key
	 * @return
	 */
	@Cacheable(value="configCache")
	public List<ConfigOption> selectConfigOptionList(@Param("sitePrefix") String sitePrefix, @Param("confId") String confId);
	
	/**
	 * 설정옵션을 조회한다.
	 * @param key
	 * @return
	 */
	//@Cacheable(value="configCache")
	public ConfigOption selectConfigOption(ConfigOption configOptionSearch);
	
	/**
	 * 복수의 옵션을 수정한다.
	 * @param configs
	 * @return 수정 결과
	 */
	@CacheEvict(value="configCache", allEntries=true)
	public int updateConfigOption(ConfigOption configOption);
	
	/**
	 * 옵션을 추가한다.
	 * @param configOption
	 * @return 
	 */
	@CacheEvict(value="configCache", allEntries=true)
	public int insertConfigOption(ConfigOption configOption);
	
	/**
	 * 설정(옵션셋)을 삭제한다.
	 * @param confId
	 * @return 삭제결과
	 */
	@CacheEvict(value="configCache", allEntries=true)
	public int deleteConfig(@Param("sitePrefix") String sitePrefix, @Param("confId") String confId);
	
	/**
	 * 옵션을 삭제한다.
	 * @param optKey
	 * @return 삭제결과
	 */
	@CacheEvict(value="configCache", allEntries=true)
	public int deleteConfigOption(@Param("sitePrefix") String sitePrefix, @Param("optKey") String optKey);

	/**
	 * 텍스트 옵션 데이터를 조회한다.
	 * @param textId
	 * @return 텍스트 옵션 데이터
	 */
	public TextData selectTextData(TextData textDataSearch);

	/**
	 * 텍스트 데이터 목록을 조회한다.
	 * @param textGroup
	 * @return
	 */
	public List<TextData> selectTextDataList(@Param("sitePrefix") String sitePrefix, @Param("textGroup") String textGroup);
	
	/**
	 * 텍스트 데이터를 수정한다.
	 * @param textData
	 * @return
	 */
	public int updateTextData(TextData textData);
}
