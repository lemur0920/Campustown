/**
 * 
 */
package egovframework.com.asapro.support.util;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author yckim
 * @since 2016. 7. 30.
 */
public class EtcUtil {
	private static final Logger LOGGER = LoggerFactory.getLogger(EtcUtil.class);
	
	/**
     * 변수로 전달된 임의의 도메인객체, VO객체의
     * 멤버변수들을 콘솔에 출력한다.
     * 
     * @param object
     * @throws Exception
     * 
     * @author ykcim
     */
	@SuppressWarnings("rawtypes")
	public static void objectInfo(Object object){
		try {
			synchronized (new Object()) {
				if(object == null){
					System.out.println("-----> [ERROR] passed object is NULL!!");
			        return;
			    }
				Class clazz = object.getClass();
			    if(clazz.isPrimitive()){
			        System.out.println("-----> [ERROR] passed object is PRIMITIVE type!!");
			        return;
			    }
			    Field[] fields = clazz.getDeclaredFields();
			    System.out.println("|---------------> [START] debugging [ object of " + object.getClass().getName() + " ] total member count : " + fields.length + " <---------------");
			                
			    Object obj = null;
			    Class clazzz = null;
			    int longestNameLength = 0, longestClazzNameLength = 0; 
			    //int longestClazzLength = 0;
			    String shortClazzName = "";
			    String[] clazzNames = new String[fields.length];
			    int interval = 0, interval2 = 0;
			    String glue = "", glue2 = "";
			                
			    for(int i = 0; i<fields.length; i++){
			    	if(fields[i].getName().length() > longestNameLength){
			            longestNameLength = fields[i].getName().length();
			        }
			        if(fields[i].getType().getName().indexOf("[") != -1){
			            shortClazzName = "Arrays";
			        }else{
			            shortClazzName = fields[i].getType().getName().split("\\.")[fields[i].getType().getName().split("\\.").length - 1];
			        }
			        clazzNames[i] = shortClazzName;
			    }
			                
			    for(int i = 0; i<clazzNames.length; i++){
			        if(clazzNames[i].length() > longestClazzNameLength){
			            longestClazzNameLength = clazzNames[i].length();
			        }
			    }
			                
			    for(int i = 0; i<fields.length; i++){
			        glue = "";
			        glue2 = "";
			        //멤버변수는 private이므로 접근 가능하도록 수정한다.
			        if(!fields[i].isAccessible()){
			        	fields[i].setAccessible(true);
			        }
			        obj = fields[i].get(object);
			        clazzz = fields[i].getType();
			        interval = longestNameLength - fields[i].getName().length();
			        if(clazzz.getName().indexOf("[") == -1){
			            interval2 = longestClazzNameLength - clazzz.getName().split("\\.")[clazzz.getName().split("\\.").length - 1].length();
			        }else{
			            interval2 = longestClazzNameLength - 6;
			        }
			        for(int j = 0; j<interval; j++){
			            glue += " ";
			        }
			        for(int j = 0; j<interval2; j++){
			        	glue2 += " ";
			        }
			        if(i < 10){
			            System.out.println("|> " + i + "  " + clazzNames[i] + glue2 + " " + fields[i].getName() + glue + " - " + obj);
			        }else{
			            System.out.println("|> " + i + " " + clazzNames[i] + glue2 + " " + fields[i].getName() + glue + " - " + obj);
			        }
			    }
			    System.out.println("|---------------> [END] debugging [ object of " + object.getClass().getName() + " ] <-----------------------------------------");
			}
		} catch (IllegalAccessException e) {
			LOGGER.error("[IllegalAccessException] Try/Catch... : "+ e.getMessage());
		} catch (IllegalArgumentException e) {
			LOGGER.error("[IllegalArgumentException] Try/Catch... : "+ e.getMessage());
		} catch (RuntimeException e) {
			LOGGER.error("[RuntimeException] Try/Catch... : "+ e.getMessage());
		}
	}
	
	/**
	 * 리퀘스트의 파라메터 정보를 출력한다.
	 * 
	 * @param request
	 */
	@SuppressWarnings("rawtypes")
	public static void parameterInfo(HttpServletRequest request){
		//try {
			System.out.println("=> " + "[PARAMETER START]");
			Enumeration ePN = request.getParameterNames();
			while(ePN.hasMoreElements()){
				//try{
					String name = ePN.nextElement().toString();
					System.out.println(name + " => " + request.getParameter(name));
				//}catch (Exception e) {
				//	System.out.println("=> " + "[EXCEPTION OCCURED]");
				//}
			}
			System.out.println("=> " + "[PARAMETER END]\n");
		//} catch (Exception e) {
		//	LOGGER.error("[Exception] Try/Catch... : "+ e.getMessage());
		//}
	}
	
	/**
	 * 현재 리퀘스트객체의 모든 정보를 출력한다.
	 * 파라메터, 세션, 어트리뷰트, 헤더, 멤버 등.
	 * 
	 * @param request
	 * @throws Exception
	 * 
	 * @author ykcim
	 */
	@SuppressWarnings("rawtypes")
	public static void requestInfo(HttpServletRequest request){
		//try {
			synchronized (new Object()) {
				System.out.println("=> " + "[PARAMETER START]");
				Enumeration ePN = request.getParameterNames();
				while(ePN.hasMoreElements()){
					//try{
						String name = ePN.nextElement().toString();
						System.out.println(name + " => " + request.getParameter(name));
					//}catch (Exception e) {
					//	System.out.println("=> " + "[EXCEPTION OCCURED]");
					//}
				}
				System.out.println("=> " + "[PARAMETER END]");
				
				System.out.println("");
				
				System.out.println("=> " + "[COOKIE START]");
				Cookie[] cookies = request.getCookies();
				for(int i = 0; i<cookies.length; i++){
					System.out.println(cookies[i].getName() + " maxAge => " + cookies[i].getMaxAge());
					System.out.println(cookies[i].getName() + " value => " + cookies[i].getValue());
				}
				System.out.println("=> " + "[COOKIE END]");
				
				System.out.println("");
	
				System.out.println("=> " + "[SESSIONATTRIBUTES START]");
				Enumeration eSAN = request.getSession().getAttributeNames();
				while(eSAN.hasMoreElements()){
					//try{
						String name = eSAN.nextElement().toString();
						System.out.println(name + " => " + request.getSession().getAttribute(name));
					//}catch (Exception e) {
					//	System.out.println("=> " + "[EXCEPTION OCCURED]");
					//}
				}
				System.out.println("=> " + "[SESSIONATTRIBUTES END]");
				
				System.out.println("");
	
				System.out.println("=> " + "[ATTRIBUTES START]");
				Enumeration eAN = request.getAttributeNames();
				while(eAN.hasMoreElements()){
						String name = eAN.nextElement().toString();
						System.out.println(name + " => " + request.getAttribute(name));
						//System.out.println("=> " + "[EXCEPTION OCCURED]");
				}
				System.out.println("=> " + "[ATTRIBUTES END]");
				
				System.out.println("");
	
				System.out.println("=> " + "[HEADER START]");
				Enumeration eHN = request.getHeaderNames();
				while(eHN.hasMoreElements()){
					//try{
						String name = eHN.nextElement().toString();
						System.out.println(name + " => " + request.getHeader(name));
					//}catch ( e) {
					//	System.out.println("=> " + "[EXCEPTION OCCURED]");
					//}
				}
				System.out.println("=> " + "[HEADER END]");
				
				System.out.println("");
			
				HttpSession session = request.getSession();
				System.out.println("=> " + "[SESSION START]");
				System.out.println("getCreationTime ========> " + new Date(session.getCreationTime()));
				System.out.println("getId ==================> " + session.getId());
				System.out.println("getLastAccessedTime ====> " + new Date(session.getLastAccessedTime()));
				System.out.println("getMaxInactiveInterval => " + session.getMaxInactiveInterval());
				System.out.println("=> " + "[SESSION END]");
				
				System.out.println("");
	
				System.out.println("=> " + "[SERVLETCONTEXT START]");
				ServletContext sc = session.getServletContext();
				//System.out.println("getContextPath ========> " + sc.getContextPath());
				System.out.println("getMajorVersion =======> " + sc.getMajorVersion());
				System.out.println("getMinorVersion =======> " + sc.getMinorVersion());
				System.out.println("getRealPath ===========> " + sc.getRealPath("/"));
				System.out.println("getServerInfo =========> " + sc.getServerInfo());
				System.out.println("getServletContextName => " + sc.getServletContextName());
				Enumeration eSCN = sc.getAttributeNames();
				while(eSCN.hasMoreElements()){
					//try{
						String name = eSCN.nextElement().toString();
						System.out.println(name + " => " + sc.getAttribute(name));
					//}catch (Exception e) {
					//	System.out.println("=> " + "[EXCEPTION OCCURED]");
					//}
				}
				Enumeration eSCIPN = sc.getInitParameterNames();
				while(eSCIPN.hasMoreElements()){
					//try{
						String name = eSCIPN.nextElement().toString();
						System.out.println(name + " => " + sc.getInitParameter(name));
					//}catch (Exception e) {
					//	System.out.println("=> " + "[EXCEPTION OCCURED]");
					//}
				}
				System.out.println("=> " + "[SERVLETCONTEXT END]");
				
				System.out.println("");
	
				System.out.println("=> " + "[MEMBERS START]");
				System.out.println("getCharacterEncoding ==> " + request.getCharacterEncoding());
				System.out.println("getContentType ========> " + request.getContentType());
				System.out.println("getContextPath ========> " + request.getContextPath());
				//System.out.println("getLocalAddr ==========> " + request.getLocalAddr());//jdk1.6
				//System.out.println("getLocalName ==========> " + request.getLocalName());//jdk1.6
				//System.out.println("getLocalPort ==========> " + request.getLocalPort());//jdk1.6
				System.out.println("getMethod =============> " + request.getMethod());
				System.out.println("getPathInfo ===========> " + request.getPathInfo());
				System.out.println("getPathTranslated =====> " + request.getPathTranslated());
				System.out.println("getProtocol ===========> " + request.getProtocol());
				System.out.println("getQueryString ========> " + request.getQueryString());
				System.out.println("getRemoteAddr =========> " + request.getRemoteAddr());
				//System.out.println("getRemotePort =========> " + request.getRemotePort());//jdk1.6
				System.out.println("getRemoteUser =========> " + request.getRemoteUser());
				System.out.println("getRemoteHost =========> " + request.getRemoteHost());
				System.out.println("getRequestedSessionId => " + request.getRequestedSessionId());
				System.out.println("getRequestURI =========> " + request.getRequestURI());
				System.out.println("getScheme =============> " + request.getScheme());
				System.out.println("getServerName =========> " + request.getServerName());
				System.out.println("getServerPort =========> " + request.getServerPort());
				System.out.println("getServletPath ========> " + request.getServletPath());
				System.out.println("getLocale =============> " + request.getLocale());
				System.out.println("getRequestURL =========> " + request.getRequestURL().toString());
				System.out.println("=> " + "[MEMBERS END]");
			}
		//} catch (Exception e) {
		//	LOGGER.error("[Exception] Try/Catch... : "+ e.getMessage());
		//}
	}
	
	/**
	 * 리스트 객체의 Info 출력
	 * @param objectList
	 */
	@SuppressWarnings("rawtypes")
	public static void listInfo(List objectList){
		if(objectList == null){
			System.out.println("-----> [ERROR] passed List is NULL!!");
	        return;
	    }
		for (int i = 0; i < objectList.size(); i++) {
			System.out.println(" ------------------> No." + i +" ListObject ---------------------------");
			objectInfo(objectList.get(i));
		}
	}
	
	/**
	 * 세선 어트리뷰트를 확인한다.
	 * @param request
	 */
	@SuppressWarnings("rawtypes")
	public static void sessionInfo(HttpServletRequest request) {
		Enumeration eSAN = request.getSession().getAttributeNames();
		while(eSAN.hasMoreElements()){
			//try{
				String name = eSAN.nextElement().toString();
				System.out.println(name + " => " + request.getSession().getAttribute(name));
			//}catch (Exception e) {
			//	System.out.println("=> " + "[EXCEPTION OCCURED]");
			//}
		}
		System.out.println("=> " + "[SESSIONATTRIBUTES END]");
		
	}
	
}
