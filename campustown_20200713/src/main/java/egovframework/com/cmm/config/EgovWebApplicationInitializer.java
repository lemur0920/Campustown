package egovframework.com.cmm.config;

import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.XmlWebApplicationContext;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.multipart.support.MultipartFilter;
import org.springframework.web.servlet.DispatcherServlet;

import egovframework.com.asapro.support.filter.HttpHeaderSecurityFilter;
import egovframework.com.asapro.support.filter.XSSFilter;
import egovframework.com.asapro.support.session.SessionInfoListener;
import egovframework.com.cmm.service.EgovProperties;

/**
 * EgovWebApplicationInitializer 클래스
 * <Notice>
 * 	   사용자 인증 권한처리를 분리(session, spring security) 하기 위해서 web.xml의 기능을 
 * 	   Servlet3.x WebApplicationInitializer 기능으로 처리
 * <Disclaimer>
 *		N/A
 *
 * @author 장동한
 * @since 2016.06.23
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일        수정자           수정내용
 *  -------      -------------  ----------------------
 *   2016.06.23  장동한           최초 생성
 *   =======asadal============
 *   2018.03.60  김영철           log 및 설정추가
 * </pre>
 */


public class EgovWebApplicationInitializer implements WebApplicationInitializer {

	private static final Logger LOGGER = LoggerFactory.getLogger(EgovWebApplicationInitializer.class);
	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		LOGGER.debug("EgovWebApplicationInitializer START-============================================");
		
		//-------------------------------------------------------------
		// Egov ServletContextListener 설정
		//-------------------------------------------------------------
		LOGGER.debug("Egov ServletContextListener 설정==============");
		servletContext.addListener(new egovframework.com.cmm.context.EgovWebServletContextListener());
		
		//-------------------------------------------------------------
		// SessionInfoListener 설정
		//-------------------------------------------------------------
		LOGGER.debug("SessionInfoListener 설정==============");
		servletContext.addListener(new SessionInfoListener());

		//-------------------------------------------------------------
		// Spring CharacterEncodingFilter 설정
		//-------------------------------------------------------------
		LOGGER.debug("Spring CharacterEncodingFilter 설정==============");
		FilterRegistration.Dynamic characterEncoding = servletContext.addFilter("encodingFilter", new org.springframework.web.filter.CharacterEncodingFilter());
		characterEncoding.setInitParameter("encoding", "UTF-8");
		characterEncoding.setInitParameter("forceEncoding", "true");
		characterEncoding.addMappingForUrlPatterns(null, false, "/*");
		//characterEncoding.addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST), true, "*.do");
		
		//-------------------------------------------------------------
		// MultipartFilter 설정
		//-------------------------------------------------------------
		LOGGER.debug("MultipartFilter 설정==============");
		MultipartFilter multipartFilter = new MultipartFilter();
		FilterRegistration.Dynamic multipart = servletContext.addFilter("multipartFilter", multipartFilter);
		multipart.setInitParameter("multipartResolverBeanName","multipartResolver" );
		multipart.addMappingForUrlPatterns(null, false, "/site/*");
		
		//-------------------------------------------------------------
		// HttpHeaderSecurityFilter 설정
		//-------------------------------------------------------------
		LOGGER.debug("HttpHeaderSecurityFilter 설정==============");
		HttpHeaderSecurityFilter httpHeaderSecurityFilter = new HttpHeaderSecurityFilter();
		httpHeaderSecurityFilter.setAntiClickJackingOption("SAMEORIGIN");
		FilterRegistration.Dynamic httpHeaderSecurity = servletContext.addFilter("httpHeaderSecurityFilter", httpHeaderSecurityFilter);
		//httpHeaderSecurity.setInitParameter("antiClickJackingOption", "SAMEORIGIN");
		httpHeaderSecurity.addMappingForUrlPatterns(null, false, "/*");
		
		//-------------------------------------------------------------
		// XSSFilter 설정
		//-------------------------------------------------------------
		LOGGER.debug("XSSFilter 설정==============");
		XSSFilter xssFilter = new XSSFilter();
		FilterRegistration.Dynamic xss = servletContext.addFilter("XSS", xssFilter);
		xss.addMappingForUrlPatterns(null, false, "/site/*");
		

		
		//-------------------------------------------------------------
		// Spring ServletContextListener 설정
		//-------------------------------------------------------------
		LOGGER.debug("Spring ServletContextListener 설정==============");
		XmlWebApplicationContext rootContext = new XmlWebApplicationContext();
		rootContext.setConfigLocations(new String[] { "classpath*:egovframework/spring/com/**/context-*.xml" });
		rootContext.refresh();
		rootContext.start();
		
		servletContext.addListener(new ContextLoaderListener(rootContext));
		
		//-------------------------------------------------------------
		// Spring ServletContextListener 설정
		//-------------------------------------------------------------
		LOGGER.debug("Spring ServletContextListener 설정==============");
		XmlWebApplicationContext xmlWebApplicationContext = new XmlWebApplicationContext();
		xmlWebApplicationContext.setConfigLocation("/WEB-INF/config/egovframework/springmvc/egov-com-*.xml");
		ServletRegistration.Dynamic dispatcher = servletContext.addServlet("dispatcher", new DispatcherServlet(xmlWebApplicationContext));
		dispatcher.setLoadOnStartup(1);
		dispatcher.addMapping("*.do");
		dispatcher.addMapping("/site/*");
		

		//-------------------------------------------------------------
		// Spring RequestContextListener 설정
		//-------------------------------------------------------------
		LOGGER.debug("Spring RequestContextListener 설정==============");
		servletContext.addListener(new org.springframework.web.context.request.RequestContextListener());
		
		LOGGER.debug("EgovWebApplicationInitializer END-============================================");
	
	}
	
}
