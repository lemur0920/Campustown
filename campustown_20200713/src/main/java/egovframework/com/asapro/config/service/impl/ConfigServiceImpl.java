/**
 * 
 */
package egovframework.com.asapro.config.service.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import egovframework.com.asapro.config.service.Config;
import egovframework.com.asapro.config.service.ConfigMapper;
import egovframework.com.asapro.config.service.ConfigOption;
import egovframework.com.asapro.config.service.ConfigService;
import egovframework.com.asapro.config.service.TextData;
import egovframework.com.asapro.config.service.TextDataGroup;
import egovframework.com.asapro.site.service.Site;
import egovframework.com.asapro.support.Constant;
import egovframework.com.asapro.support.jdbc.AsaproDBUtils;
import egovframework.com.asapro.support.util.AsaproUtils;
import egovframework.com.asapro.support.util.EtcUtil;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

/**
 * 설정 서비스 구현
 * @author yckim
 * @since 2018. 8. 6.
 *
 */
@Service
public class ConfigServiceImpl extends EgovAbstractServiceImpl implements ConfigService{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ConfigServiceImpl.class); 
	
	@Autowired
	private ConfigMapper configMapper;
	
	@Autowired
	private HttpServletRequest request;
	
	@Autowired
	private CacheManager cacheManager;
	
	/**
	 * 설정(옵션셋)조회
	 */
	@Override
	public Config getConfig(String confId) {
		String sitePrefix = null;
		//사이트별 설정이 아니 메인에서만 설정을 사용할 옵션 구분
		if( "global".equals(confId) || "security".equals(confId)  ){
			//siteId = Constant.MAIN_SITE_ID;
			sitePrefix = Constant.MAIN_SITE_PREFIX;
		} else {
			Site currentSite = null;
			
			try {
				currentSite = (Site) request.getAttribute("currentSite");
			} catch (IllegalStateException e) {
				//스케쥴러에서 호출할때 이 예외 떨어짐
				currentSite = new Site();
				currentSite.setSiteId(Constant.MAIN_SITE_DISPLAY_ID);
			}
			//Site currentSite = (Site) request.getAttribute("currentSite");
			//siteId = currentSite.getSiteId();
			
			sitePrefix = currentSite.getSitePrefix();
		}
		return new Config(configMapper.selectConfigOptionList(sitePrefix, confId));
	}

	/**
	 * 디비에서 직접 옵션조회(캐시 무시)
	 */
	@Override
	public ConfigOption getNoCacheOption(String confId, String optKey) {
		Site currentSite = (Site) request.getAttribute("currentSite");
		ConfigOption configOptionSearch = new ConfigOption();
		configOptionSearch.setConfId(confId);
		configOptionSearch.setOptKey(optKey);
		configOptionSearch.setSitePrefix(currentSite.getSitePrefix());
		return configMapper.selectConfigOption(configOptionSearch);
		//Site currentSite = (Site) request.getAttribute("currentSite");
		//return AsaproDBUtils.selectBean("SELECT * FROM " + currentSite.getSitePrefix() + "_CONFIG_OPTION WHERE CONF_ID = ? AND OPT_KEY = ?", ConfigOption.class, confId, optKey);
	}
	
	/**
	 * 메인사이트의 디비에서 직접 옵션조회(캐시 무시)
	 */
	@Override
	public ConfigOption getNoCacheGlobalOption(String confId, String optKey) {
		//Site mainSite = siteMapper.selectMainSite();
		ConfigOption configOptionSearch = new ConfigOption();
		configOptionSearch.setConfId(confId);
		configOptionSearch.setOptKey(optKey);
		configOptionSearch.setSitePrefix(Constant.MAIN_SITE_PREFIX);
		return configMapper.selectConfigOption(configOptionSearch);
		//return AsaproDBUtils.selectBean("SELECT * FROM ASA_CONFIG_OPTION WHERE CONF_ID = ? AND OPT_KEY = ?", ConfigOption.class, confId, optKey);
	}

	/**
	 * 수정
	 */
	@Override
	public int updateConfig(Config config) {
		int result = 0;
		for( ConfigOption configOption : config.getOptions() ){
			configOption.setSitePrefix(config.getSitePrefix());
			result += configMapper.updateConfigOption(configOption);
		}
		
		//워터마크 이미지 변경
		if( config.getWatermarkImage() != null && !config.getWatermarkImage().isEmpty() ){
			String webRoot = AsaproUtils.getWebRoot(request);
			//FileInfoService 랑 서로 인젝팅 하려고하다가 예외떨어져서 그냥 저장 처리함
			try {
				FileCopyUtils.copy(config.getWatermarkImage().getInputStream(), new FileOutputStream(new File(webRoot + config.getOption("watermark_image")), false));
			} catch (FileNotFoundException e) {
				LOGGER.error("[SQLException] Try/Catch... : "+ e.getMessage());
			} catch (IOException e) {
				LOGGER.error("[IOException] Try/Catch... : "+ e.getMessage());
			} 
		}
		
		//============== START : ehcache 수정된 경우 수동 업데이트 ============================
		cacheManager.getCache("configCache").put("getConfig:" + config.getConfId(), new Config(configMapper.selectConfigOptionList(config.getSitePrefix(), config.getConfId())));
		//============== END : ehcache 수정된 경우 수동 업데이트 ============================
		
		return result;
	}
	
	/**
	 * 옵션하나수정
	 */
	@Override
	public int updateConfigOption(ConfigOption configOptionForm) {
		Site currentSite = (Site) request.getAttribute("currentSite");
		configOptionForm.setSitePrefix(currentSite.getSitePrefix());
		
		int updated = 0;
		//웁션 수정하기전에 select해보고 없으면 입력한다.
		ConfigOption fromDB = configMapper.selectConfigOption(configOptionForm);
		if( fromDB == null ){
			//키는 소문자로
			configOptionForm.setOptKey( configOptionForm.getOptKey().toLowerCase() );
			updated = configMapper.insertConfigOption(configOptionForm);
			
			//============== START : ehcache 추가된 경우 수동 업데이트 ============================
			if( updated > 0 ){
				List<ConfigOption> cachedOptionList = cacheManager.getCache("configCache").get("getConfig:" + configOptionForm.getConfId(), Config.class).getOptions();
				cachedOptionList.add(configOptionForm);
				cacheManager.getCache("configCache").put("getConfig:" + configOptionForm.getConfId(), cachedOptionList);
			}
			//============== END : ehcache 추가된 경우 수동 업데이트 ============================
			
		} else {
			updated = configMapper.updateConfigOption(configOptionForm);
			
			//============== START : ehcache 수정된 경우 수동 업데이트 ============================
			if( updated > 0 ){
				List<ConfigOption> cachedOptionList = cacheManager.getCache("configCache").get("getConfig:" + configOptionForm.getConfId(), Config.class).getOptions();
				for( int i = 0; i<cachedOptionList.size(); i++ ){
					if( cachedOptionList.get(i).getOptKey().equals(configOptionForm.getOptKey()) ){
						cachedOptionList.set(i, configOptionForm);
						break;
					}
				}
				cacheManager.getCache("configCache").put("getConfig:" + configOptionForm.getConfId(), cachedOptionList);
			}
			//============== END : ehcache 수정된 경우 수동 업데이트 ============================
		}
		
		
		return updated; 
	}

	/**
	 * 텍스트 옵션 데이터 조회
	 */
	@Override
	public TextData getTextData(String textGroup, String textId) {
		String sitePrefix = null;
		if( "robots_txt".equals(textId) ){
			sitePrefix = Constant.MAIN_SITE_PREFIX; 
		} else {
			try {
				Site currentSite = (Site) request.getAttribute("currentSite");
				sitePrefix = currentSite.getSitePrefix();
			} catch(IllegalStateException e){
				LOGGER.info("[namu] ConfigServiceImpl - " + e.getMessage());
				sitePrefix = Constant.MAIN_SITE_PREFIX;
			}
		}
		TextData textDataSearch = new TextData();
		textDataSearch.setSitePrefix(sitePrefix);
		textDataSearch.setTextGroup(textGroup);
		textDataSearch.setTextId(textId);
		return configMapper.selectTextData(textDataSearch);
	}

	/**
	 * 텍스트 데이터 그룹
	 */
	@Override
	public TextDataGroup getTextDataGroup(String textGroup) {
		Site currentSite = (Site) request.getAttribute("currentSite");
		TextDataGroup textDataGroup = new TextDataGroup();
		textDataGroup.setTextDataList(configMapper.selectTextDataList(currentSite.getSitePrefix(), textGroup));
		return textDataGroup;
	}
	
	/**
	 * 텍스트 데이터 수정
	 */
	@Override
	public int updateTextDataGroup(TextDataGroup textDataGroupForm) {
		Site currentSite = (Site) request.getAttribute("currentSite");
		int updated = 0;
		for( TextData td : textDataGroupForm.getTextDataList()  ){
			td.setSitePrefix(currentSite.getSitePrefix());
			updated += configMapper.updateTextData(td);
			
			//robots.txt 는 추가 처리가 필요하다...
			if( "robots_txt".equals(td.getTextId()) ){
				try {
					FileUtils.writeStringToFile(new File(AsaproUtils.getWebRoot(request) + "robots.txt"), td.getTextContent(), "UTF-8");
				} catch (FileNotFoundException e) {
					LOGGER.error("[FileNotFoundException] Try/Catch... : "+ e.getMessage());
				} catch (IOException e) {
					LOGGER.error("[IOException] Try/Catch... : "+ e.getMessage());
				}
			}
		}
		return updated;
	}

	/**
	 * 캐시안된 정보 직접 가져온다
	 */
	@Override
	public Config getNoCacheConfig(String confId) {
		//String siteId = null;
		String sitePrefix = null;
		if( "global".equals(confId) || "mail".equals(confId) ){
			//siteId = Constant.MAIN_SITE_ID;
			sitePrefix = Constant.MAIN_SITE_PREFIX;
		} else {
			Site currentSite = (Site) request.getAttribute("currentSite");
			//siteId = currentSite.getSiteId();
			sitePrefix = currentSite.getSitePrefix();
		}
		
		// DB캐시 사용하도록 변경 
		// 기본적으로 DB캐시를 사용하도록 했기때문에 no cache config 는 다른 방법으로 가져오게 함
		StringBuilder q = new StringBuilder(500);
		q.append(" SELECT * FROM ");
		q.append(sitePrefix);
		q.append("_CONFIG_OPTION WHERE CONF_ID = ? ORDER BY OPT_KEY ASC");
		List<ConfigOption> notCachedOptionList = AsaproDBUtils.selectBeanList(q.toString(), ConfigOption.class, confId);
		
		return new Config(notCachedOptionList);
	}

}
