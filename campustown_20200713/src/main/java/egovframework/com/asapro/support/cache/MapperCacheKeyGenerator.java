/**
 * 
 */
package egovframework.com.asapro.support.cache;

import java.lang.reflect.Method;

import org.springframework.cache.interceptor.KeyGenerator;

import egovframework.com.asapro.config.service.ConfigOption;

/**
 * ehcache 캐쉬키 생성 클래스
 * @author yckim
 * @since 2018. 8. 6.
 */
public class MapperCacheKeyGenerator implements KeyGenerator{

	@Override
	public Object generate(Object target, Method method, Object... params) {
		//System.out.println("[asapro] MapperCacheKeyGenerator : key generator entered => " + method.getName());
		String paramPart = "";
		for( Object param : params ){
			//selectConfigOptionList
			if( "selectConfigOptionList".equals(method.getName()) ){
				paramPart += (String)params[0] + ";" + (String)params[1];
				break;
			}
			//selectConfigOption
			else if( "selectConfigOption".equals(method.getName()) ){
				ConfigOption configOption = (ConfigOption) param;
				paramPart += configOption.getSitePrefix() + ";" + configOption.getConfId() + ";" + configOption.getOptKey();
				break;
			}
			else {
				//System.out.println("[asapro] MapperCacheKeyGenerator : " + param);
			}
			/*
			if( param.getClass().equals(java.lang.String.class) || param.getClass().isPrimitive() ){
				paramPart += param + ";";
			}
			//object
			else {
				try {
					Map<String, String> map = BeanUtils.describe(param);
					for( String key : map.keySet() ){
						if( "sitePrefix".equals(key) || key.toLowerCase().endsWith("idx") || key.toLowerCase().endsWith("id") ){
							paramPart += key + "=" + map.get(key) + ";";
						}
					}
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				} catch (NoSuchMethodException e) {
					e.printStackTrace();
				}
			}
			*/
		}
		return method.getName() + ":" + paramPart;
	}

}
