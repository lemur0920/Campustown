/**
 * 
 */
package egovframework.com.asapro.support.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.util.WebUtils;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import com.ibm.icu.util.ChineseCalendar;

import egovframework.com.asapro.config.service.Config;
import egovframework.com.asapro.site.service.Site;
import egovframework.com.asapro.support.Constant;
import egovframework.com.cmm.service.EgovProperties;
import egovframework.com.asapro.support.ApplicationContextProvider;
import eu.bitwalker.useragentutils.Browser;
import eu.bitwalker.useragentutils.UserAgent;

/**
 * asapro 메일발송 유틸
 * @author yckim
 * @since 2019. 1. 31.
 *
 */
public class AsaproMailUtils {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AsaproMailUtils.class);
	
	private String default_encoding = "UTF-8";
	private String host = "localhost";
	private String userName = "관리자";
	private String password = "";
	private String protocol = "smtp";
	private String port = "25";
	
	private String mailSmtpAuth = "";
	private String mailSmtpStarttlsEnable = "";
	private String mailSmtpSslTrust = "";
	private String mailSmtpSslEnable = "";
	private String mailSmtpTimeout = "";
	private String mailSmtpConnectiontimeout = "";
	
	
	/**
	 * JavaMailSender 를 반환한다.
	 * @return
	 */
	public static JavaMailSender getJavaMailSender(){

//		ApplicationContext applicationContext = ApplicationContextProvider.getApplicationContext();
		
		JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
		
		
//		if( StringUtils.isNotBlank(mailConfig.getOption("default_encoding")) ){
//			javaMailSender.setDefaultEncoding( mailConfig.getOption("default_encoding") );
//		}
		javaMailSender.setDefaultEncoding( "UTF-8" );
		javaMailSender.setHost("mail.kobaco.co.kr");
		javaMailSender.setUsername("관리자");
		javaMailSender.setProtocol("smtp");
//		if( StringUtils.isNotBlank(mailConfig.getOption("username")) ){
//			javaMailSender.setUsername(mailConfig.getOption("username"));
//		}
//		if( StringUtils.isNotBlank(mailConfig.getOption("password")) ){
//			javaMailSender.setPassword(mailConfig.getOption("password"));
//		}
//		if( StringUtils.isNotBlank(mailConfig.getOption("port")) ){
//			javaMailSender.setPort(Integer.parseInt(mailConfig.getOption("port")));
//		}
//		if( StringUtils.isNotBlank(mailConfig.getOption("protocol")) ){
//			javaMailSender.setProtocol(mailConfig.getOption("protocol"));
//		}
		
		Properties javaMailProperties = new Properties();
//		if( StringUtils.isNotBlank(mailConfig.getOption("mail.smtp.auth")) ){
//			javaMailProperties.setProperty("mail.smtp.auth", mailConfig.getOption("mail.smtp.auth"));
//		}
//		if( StringUtils.isNotBlank(mailConfig.getOption("mail.smtp.starttls.enable")) ){
//			javaMailProperties.setProperty("mail.smtp.starttls.enable", mailConfig.getOption("mail.smtp.starttls.enable"));
//			if( "true".equals(mailConfig.getOption("mail.smtp.ssl.trust")) ){
//				javaMailProperties.setProperty("mail.smtp.ssl.trust", mailConfig.getOption("host"));
//			}
//		}
		/*
		if( StringUtils.isNotBlank(mailConfig.getOption("mail.smtp.ssl.trust")) ){
			javaMailProperties.setProperty("mail.smtp.ssl.trust", mailConfig.getOption("host"));
		}
		*/
//		if( StringUtils.isNotBlank(mailConfig.getOption("protocal")) ){
//			javaMailProperties.setProperty("mail.transport.protocol", mailConfig.getOption("protocal"));
//		}
//		if( StringUtils.isNotBlank(mailConfig.getOption("mail.smtp.ssl.enable")) ){
//			javaMailProperties.setProperty("mail.smtp.ssl.enable", mailConfig.getOption("mail.smtp.ssl.enable"));
//		}
//		if( StringUtils.isNotBlank(mailConfig.getOption("mail.smtp.timeout")) ){
//			javaMailProperties.setProperty("mail.smtp.timeout", mailConfig.getOption("mail.smtp.timeout"));
//			javaMailProperties.setProperty("mail.smtp.connectiontimeout", mailConfig.getOption("mail.smtp.timeout"));
//		}
		javaMailSender.setJavaMailProperties(javaMailProperties);
		
		return javaMailSender;
	}
	
	/**
	 * 메일을 발송한다.
	 * @param toEmail
	 * @param subject
	 * @param fromEmail
	 * @param fromName
	 * @param contents
	 * @return 발송결과
	 */
	public static boolean sendMail(String toEmail, String subject, String fromEmail, String fromName, String contents){
		boolean result = false;
//		ApplicationContext applicationContext = ApplicationContextProvider.getApplicationContext();
		
		JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
		
		javaMailSender.setDefaultEncoding( "UTF-8" );
		javaMailSender.setHost("mail.kobaco.co.kr");
		javaMailSender.setUsername("관리자");
		javaMailSender.setProtocol("smtp");
		
//		if( StringUtils.isNotBlank(mailConfig.getOption("default_encoding")) ){
//			javaMailSender.setDefaultEncoding( mailConfig.getOption("default_encoding") );
//		}
		//javaMailSender.setHost("mail.kobaco.co.kr");
//		if( StringUtils.isNotBlank(mailConfig.getOption("username")) ){
//			javaMailSender.setUsername(mailConfig.getOption("username"));
//		}
//		if( StringUtils.isNotBlank(mailConfig.getOption("password")) ){
//			javaMailSender.setPassword(mailConfig.getOption("password"));
//		}
//		if( StringUtils.isNotBlank(mailConfig.getOption("port")) ){
//			javaMailSender.setPort(Integer.parseInt(mailConfig.getOption("port")));
//		}
//		if( StringUtils.isNotBlank(mailConfig.getOption("protocol")) ){
//			javaMailSender.setProtocol(mailConfig.getOption("protocol"));
//		}
		
		Properties javaMailProperties = new Properties();
//		if( StringUtils.isNotBlank(mailConfig.getOption("mail.smtp.auth")) ){
//			javaMailProperties.setProperty("mail.smtp.auth", mailConfig.getOption("mail.smtp.auth"));
//		}
//		if( StringUtils.isNotBlank(mailConfig.getOption("mail.smtp.starttls.enable")) ){
//			javaMailProperties.setProperty("mail.smtp.starttls.enable", mailConfig.getOption("mail.smtp.starttls.enable"));
//			if( "true".equals(mailConfig.getOption("mail.smtp.ssl.trust")) ){
//				javaMailProperties.setProperty("mail.smtp.ssl.trust", mailConfig.getOption("host"));
				//javaMailProperties.setProperty("mail.smtp.ssl.trust", "smpt.gmail.com");
//			}
//		}
		/*
		if( StringUtils.isNotBlank(mailConfig.getOption("mail.smtp.ssl.trust")) ){
			javaMailProperties.setProperty("mail.smtp.ssl.trust", mailConfig.getOption("host"));
		}
		 */
//		if( StringUtils.isNotBlank(mailConfig.getOption("protocal")) ){
//			javaMailProperties.setProperty("mail.transport.protocol", mailConfig.getOption("protocal"));
//		}
//		if( StringUtils.isNotBlank(mailConfig.getOption("mail.smtp.ssl.enable")) ){
//			javaMailProperties.setProperty("mail.smtp.ssl.enable", mailConfig.getOption("mail.smtp.ssl.enable"));
//		}
//		if( StringUtils.isNotBlank(mailConfig.getOption("mail.smtp.timeout")) ){
//			javaMailProperties.setProperty("mail.smtp.timeout", mailConfig.getOption("mail.smtp.timeout"));
//			javaMailProperties.setProperty("mail.smtp.connectiontimeout", mailConfig.getOption("mail.smtp.timeout"));
//		}
		javaMailSender.setJavaMailProperties(javaMailProperties);
		
		
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		MimeMessageHelper mimeMessageHelper = null;
		try {
			HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();

			mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
			try {
				mimeMessageHelper.setFrom(fromEmail, fromName);
			} catch (UnsupportedEncodingException e) {
				LOGGER.error("[UnsupportedEncodingException] Try/Catch... : "+ e.getMessage());
			}
			
			mimeMessageHelper.setSubject(subject);
			mimeMessageHelper.setText(contents, true);	

			String[] toEmailArray = toEmail.split(";");
			for (String email : toEmailArray) {
				mimeMessageHelper.setTo(email);
				
				if(!AsaproUtils.isDevPath(request) && !AsaproUtils.isAsadalNet(request) ){
					javaMailSender.send(mimeMessage);
					result = true;
				};
			}
			
		} catch (MessagingException e) {
			LOGGER.error("[MessagingException] Try/Catch... : "+ e.getMessage());
		}
		
		return result;
	}
	
	/**
	 * 메일을 발송한다.
	 * @param toEmail
	 * @param subject
	 * @param fromEmail
	 * @param fromName
	 * @param contents
	 * @return 발송결과
	 */
	public static boolean sendDevMail(String toEmail, String subject, String fromEmail, String fromName, String contents){
		boolean result = false;
//		ApplicationContext applicationContext = ApplicationContextProvider.getApplicationContext();
		
		JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
		
		
		
//		if( StringUtils.isNotBlank(mailConfig.getOption("default_encoding")) ){
//			javaMailSender.setDefaultEncoding( mailConfig.getOption("default_encoding") );
		javaMailSender.setDefaultEncoding( "UTF-8" );
//		}
		//javaMailSender.setHost("mail.kobaco.co.kr");
		javaMailSender.setHost("smtp.gmail.com");
//		if( StringUtils.isNotBlank(mailConfig.getOption("username")) ){
//			javaMailSender.setUsername(mailConfig.getOption("username"));
		//javaMailSender.setUsername("관리자");
		javaMailSender.setUsername("jhlkyc@gmail.com");
//		}
//		if( StringUtils.isNotBlank(mailConfig.getOption("password")) ){
//			javaMailSender.setPassword(mailConfig.getOption("password"));
		javaMailSender.setPassword("jk3274jk3274!");
//		}
//		if( StringUtils.isNotBlank(mailConfig.getOption("port")) ){
//			javaMailSender.setPort(Integer.parseInt(mailConfig.getOption("port")));
		javaMailSender.setPort(465);
//		}
//		if( StringUtils.isNotBlank(mailConfig.getOption("protocol")) ){
//			javaMailSender.setProtocol(mailConfig.getOption("protocol"));
		javaMailSender.setProtocol("smtp");
//		}
		
		Properties javaMailProperties = new Properties();
//		if( StringUtils.isNotBlank(mailConfig.getOption("mail.smtp.auth")) ){
//			javaMailProperties.setProperty("mail.smtp.auth", mailConfig.getOption("mail.smtp.auth"));
		javaMailProperties.setProperty("mail.smtp.auth", "true");
//		}
//		if( StringUtils.isNotBlank(mailConfig.getOption("mail.smtp.starttls.enable")) ){
//			javaMailProperties.setProperty("mail.smtp.starttls.enable", mailConfig.getOption("mail.smtp.starttls.enable"));
		javaMailProperties.setProperty("mail.smtp.starttls.enable", "false");
//			if( "true".equals(mailConfig.getOption("mail.smtp.ssl.trust")) ){
//				javaMailProperties.setProperty("mail.smtp.ssl.trust", mailConfig.getOption("host"));
		//javaMailProperties.setProperty("mail.smtp.ssl.trust", "smpt.gmail.com");
//			}
//		}
		/*
		if( StringUtils.isNotBlank(mailConfig.getOption("mail.smtp.ssl.trust")) ){
			javaMailProperties.setProperty("mail.smtp.ssl.trust", mailConfig.getOption("host"));
		}
		 */
//		if( StringUtils.isNotBlank(mailConfig.getOption("protocal")) ){
//			javaMailProperties.setProperty("mail.transport.protocol", mailConfig.getOption("protocal"));
		javaMailProperties.setProperty("mail.transport.protocol", "smtp");
//		}
//		if( StringUtils.isNotBlank(mailConfig.getOption("mail.smtp.ssl.enable")) ){
//			javaMailProperties.setProperty("mail.smtp.ssl.enable", mailConfig.getOption("mail.smtp.ssl.enable"));
		javaMailProperties.setProperty("mail.smtp.ssl.enable", "true");
//		}
//		if( StringUtils.isNotBlank(mailConfig.getOption("mail.smtp.timeout")) ){
//			javaMailProperties.setProperty("mail.smtp.timeout", mailConfig.getOption("mail.smtp.timeout"));
//			javaMailProperties.setProperty("mail.smtp.connectiontimeout", mailConfig.getOption("mail.smtp.timeout"));
//		}
		javaMailSender.setJavaMailProperties(javaMailProperties);
		
		
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		MimeMessageHelper mimeMessageHelper = null;
		try {
			HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
			
			mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
			try {
				mimeMessageHelper.setFrom(fromEmail, fromName);
			} catch (UnsupportedEncodingException e) {
				LOGGER.error("[UnsupportedEncodingException] Try/Catch... : "+ e.getMessage());
			}
			
			mimeMessageHelper.setSubject(subject);
			mimeMessageHelper.setText(contents, true);	
			
			String[] toEmailArray = toEmail.split(";");
			for (String email : toEmailArray) {
				mimeMessageHelper.setTo(email);
				
				//			if(!AsaproUtils.isDevPath(request) && !AsaproUtils.isAsadalNet(request) ){
				javaMailSender.send(mimeMessage);
				result = true;
				//			};
			}
			
		} catch (MessagingException e) {
			LOGGER.error("[MessagingException] Try/Catch... : "+ e.getMessage());
		}
		
		return result;
	}
	
}
