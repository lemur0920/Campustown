/**
 * 
 */
package egovframework.com.asapro.support;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

/**
 * 스프링 3.03 버전에서는 FlashMap이나 RedirectAttribute를 지원하지 않으므로 대신 씀.
 * <pre>
 * - GET-POST-GET 패턴에서 사용자단으로의 메세지 전달용으로 사용한다.
 * - 사용후에는 찾아서 지워주는 코드를 컨트롤러에서 추가해야함.(현재는 index, board list, board view 에서 한번씩 호출해서 일괄제거하도록 되어 있음)
 * - jsp 에서 사용은 FlashAttribute.get...
 * - 결국은 세션에 담았다가 사용하고 지우는거임.
 * </pre>
 * @author yckim
 * @since 2014. 1. 10.
 * @deprecated 스프링버전을 3.2로 업그레이드 했기때문에 RedirectAttribute, FlashMap을 대신 쓰도록 하자!!
 * @changeLog 2015. 6. 29 yckim 소스에서 이 클래스를 사용하는 부분 일괄 제거(RedirectAttribute, FlashMap으로 대체함)
 */
public class FlashAttribute {
	
	private static final String PREFIX = "ASAPRO.FLASH_ATTRIBUTE.";
	
	/**
	 * 1회용 속성을 추가한다.
	 * @param request
	 * @param key
	 * @param value
	 */
	public static void setFlashAttribute(HttpServletRequest request, String key, Object value){
		request.getSession().setAttribute(PREFIX + key, value);
	}
	
	/**
	 * 1회용 속성을 조회한다.
	 * @param request
	 * @param key
	 * @return 1회용 속성
	 */
	public static Object getFlashAttribute(HttpServletRequest request, String key){
		return request.getSession().getAttribute(PREFIX + key);
	}
	
	/**
	 * 1회용 속성을 제거한다.
	 * @param request
	 * @param key
	 */
	public static void removeFlashAttribute(HttpServletRequest request, String key){
		request.getSession().removeAttribute(PREFIX + key);
	}
	
	/**
	 * 1회용 속성을 일괄 제거한다.
	 * <pre>
	 * - 현재는 홈페이지 메인화면에서 일괄제거하도록 처리되어 있음
	 * </pre>
	 * @param request
	 */
	public static void clearAll(HttpServletRequest request){
		@SuppressWarnings("rawtypes")
		Enumeration enums = request.getSession().getAttributeNames();
		while( enums.hasMoreElements() ){
			String key = (String) enums.nextElement();
			if( key.startsWith(PREFIX) ){
				request.getSession().removeAttribute(key);
			}
		}
	}
	
}
