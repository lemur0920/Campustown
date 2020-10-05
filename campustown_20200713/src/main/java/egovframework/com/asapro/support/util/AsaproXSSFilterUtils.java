/**
 * 
 */
package egovframework.com.asapro.support.util;

import java.lang.reflect.Field;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.safety.Whitelist;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * XSS Filter
 * 
 * @author yckim
 * @since 2020. 2. 24.
 */
public class AsaproXSSFilterUtils {
	private static final Logger LOGGER = LoggerFactory.getLogger(AsaproXSSFilterUtils.class);
	
	/**
	 * 모든 String 멤버변수에서 XSS 제거
	 * @param object
	 */
	public static void cleanStringFields(Object object){
		AsaproXSSFilterUtils.cleanStringFields(object, false);
	} 
	
	/**
	 * 모든 String 멤버변수에서 XSS 제거
	 * @param object
	 * @param allowImageTag
	 */
	public static void cleanStringFields(Object object, boolean allowImageTag){
		try {
			Class<?> clazz = object.getClass();
			Field[] fields = clazz.getDeclaredFields();
			Object fieldValue = null;
			String fieldValueStr = null;
			for( Field field : fields ){
				if( !field.isAccessible() ){
					field.setAccessible(true);
				}
				fieldValue = field.get(object);
				if( fieldValue != null && "java.lang.String".equals(fieldValue.getClass().getName()) ){
					fieldValueStr = (String)fieldValue;
					if( StringUtils.isNotBlank(fieldValueStr) ){
						fieldValueStr = AsaproUtils.getJsoupFilteredText(fieldValueStr, allowImageTag ? Whitelist.basicWithImages() : Whitelist.basic(), true, false);
						field.set(object, fieldValueStr);
					}
				}
			}
		} catch (IllegalArgumentException e) {
			LOGGER.error("[IllegalArgumentException] Try/Catch... : "+ e.getMessage());
		} catch (IllegalAccessException e) {
			LOGGER.error("[IllegalAccessException] Try/Catch... : "+ e.getMessage());
		}
	}
	
}
