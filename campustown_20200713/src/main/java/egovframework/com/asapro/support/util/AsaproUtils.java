/**
 * 
 */
package egovframework.com.asapro.support.util;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import java.util.StringTokenizer;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
import org.apache.commons.jcs.utils.net.HostNameUtil;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.aspectj.weaver.patterns.ThisOrTargetAnnotationPointcut;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.util.WebUtils;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import com.ibm.icu.util.ChineseCalendar;

import egovframework.com.asapro.code.service.Code;
import egovframework.com.asapro.config.service.Config;
import egovframework.com.asapro.site.service.Site;
import egovframework.com.asapro.support.Constant;
import egovframework.com.cmm.service.EgovProperties;
import eu.bitwalker.useragentutils.Browser;
import eu.bitwalker.useragentutils.UserAgent;

/**
 * asapro 공통유틸 클래스
 * <pre>
 * 가능한 commons 나 스프링이 제공하는 util 을 사용할 수 있도록 작업합니다.
 * 그밖에 딱히 해당 유틸 기능을 찾지 못했을 경우 이 클래스에 static 메서드로 추가합니다.
 * StringUtil, DateUtil 이런거 절대로 개별 클래스로 추가하지 마세요.
 * 단, 프로젝트 단위로 해당 프로젝트에서만 사용되는 특수한 util클래스의 경우는 예외로 합니다.
 * ex) ABC라는 프로젝트 진행시 ABCUtils 클래스는 허용.
 * </pre>
 * @author yckim
 * @since 2018. 3. 30.
 *
 */
public class AsaproUtils {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AsaproUtils.class);
	
	/**
	 * 사용자 원격 IP추출 (웹로직같은 경우 제대로 안주므로 유틸로 뺌)
	 * @param request
	 * @return
	 */
	public static String getRempoteIp(HttpServletRequest request){
		String ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		if( StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip) ){
			ip = request.getRemoteAddr();
		}
		return ip;
	}
	
	//이메일 정규식 패턴
	private static final String EMAIL_PATTERN = "(?:(?:\\r\\n)?[ \\t])*(?:(?:(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\"(?:[^\\\"\\r\\\\]|\\\\.|(?:(?:\\r\\n)?[ \\t]))*\"(?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\"(?:[^\\\"\\r\\\\]|\\\\.|(?:(?:\\r\\n)?[ \\t]))*\"(?:(?:\\r\\n)?[ \\t])*))*@(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*))*|(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\"(?:[^\\\"\\r\\\\]|\\\\.|(?:(?:\\r\\n)?[ \\t]))*\"(?:(?:\\r\\n)?[ \\t])*)*\\<(?:(?:\\r\\n)?[ \\t])*(?:@(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*))*(?:,@(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*))*)*:(?:(?:\\r\\n)?[ \\t])*)?(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\"(?:[^\\\"\\r\\\\]|\\\\.|(?:(?:\\r\\n)?[ \\t]))*\"(?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\"(?:[^\\\"\\r\\\\]|\\\\.|(?:(?:\\r\\n)?[ \\t]))*\"(?:(?:\\r\\n)?[ \\t])*))*@(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*))*\\>(?:(?:\\r\\n)?[ \\t])*)|(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\"(?:[^\\\"\\r\\\\]|\\\\.|(?:(?:\\r\\n)?[ \\t]))*\"(?:(?:\\r\\n)?[ \\t])*)*:(?:(?:\\r\\n)?[ \\t])*(?:(?:(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\"(?:[^\\\"\\r\\\\]|\\\\.|(?:(?:\\r\\n)?[ \\t]))*\"(?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\"(?:[^\\\"\\r\\\\]|\\\\.|(?:(?:\\r\\n)?[ \\t]))*\"(?:(?:\\r\\n)?[ \\t])*))*@(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*))*|(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\"(?:[^\\\"\\r\\\\]|\\\\.|(?:(?:\\r\\n)?[ \\t]))*\"(?:(?:\\r\\n)?[ \\t])*)*\\<(?:(?:\\r\\n)?[ \\t])*(?:@(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*))*(?:,@(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*))*)*:(?:(?:\\r\\n)?[ \\t])*)?(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\"(?:[^\\\"\\r\\\\]|\\\\.|(?:(?:\\r\\n)?[ \\t]))*\"(?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\"(?:[^\\\"\\r\\\\]|\\\\.|(?:(?:\\r\\n)?[ \\t]))*\"(?:(?:\\r\\n)?[ \\t])*))*@(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*))*\\>(?:(?:\\r\\n)?[ \\t])*)(?:,\\s*(?:(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\"(?:[^\\\"\\r\\\\]|\\\\.|(?:(?:\\r\\n)?[ \\t]))*\"(?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\"(?:[^\\\"\\r\\\\]|\\\\.|(?:(?:\\r\\n)?[ \\t]))*\"(?:(?:\\r\\n)?[ \\t])*))*@(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*))*|(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\"(?:[^\\\"\\r\\\\]|\\\\.|(?:(?:\\r\\n)?[ \\t]))*\"(?:(?:\\r\\n)?[ \\t])*)*\\<(?:(?:\\r\\n)?[ \\t])*(?:@(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*))*(?:,@(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*))*)*:(?:(?:\\r\\n)?[ \\t])*)?(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\"(?:[^\\\"\\r\\\\]|\\\\.|(?:(?:\\r\\n)?[ \\t]))*\"(?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\"(?:[^\\\"\\r\\\\]|\\\\.|(?:(?:\\r\\n)?[ \\t]))*\"(?:(?:\\r\\n)?[ \\t])*))*@(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*))*\\>(?:(?:\\r\\n)?[ \\t])*))*)?;\\s*)";
	
	/**
	 * 입력된 문자열이 유효한 이메일 형식인지 확인한다.
	 * @param emailStr
	 * @return 확인 결과
	 */
	public static boolean isEmail(String emailStr){
		
		//중간에 공백 포함되어 있으면 이메일 안됨
		if( StringUtils.containsWhitespace(emailStr) ){
			return false;
		}
		
		//한글포함되어 있으면 안됨..한글 이메일 주소도 있다고는 하는데 일단 막음 -_-
		if( AsaproUtils.hasKoreanChars(emailStr) ){
			return false;
		}
				
		Pattern p = Pattern.compile(EMAIL_PATTERN);
		return p.matcher(emailStr).matches();
	}
	
	/**
	 * 파일 다운로드시 저장파일명이 한글일 경우에 대한 처리를 위한 유틸리티 메서드
	 * <pre>
	 * -영어로 저장하면 잘 저장되므로 이 메서드를 사용할 필요없음
	 * </pre>
	 * @param request
	 * @param fileKoreanName
	 * @return 한글파일명
	 */
	public static String getKoreanFileNameToSave(HttpServletRequest request, String fileKoreanName){
		//User Agent
		//UserAgent userAgent = UserAgent.parseUserAgentString(request.getHeader("User-Agent"));
		UserAgent userAgent = (UserAgent) request.getAttribute("userAgent");
		//LOGGER.info("##### UserAgent Group : {}, Name : {}, VERSION: {}", new Object[]{userAgent.getBrowser().getGroup(), userAgent.getBrowser(), userAgent.getBrowserVersion()});
		String fileNameToSave = null;

		try {
			if(userAgent.getBrowser().getGroup().equals(Browser.IE)){
				fileNameToSave = URLEncoder.encode(fileKoreanName, "UTF8");
			} else if(userAgent.getBrowser().getGroup().equals(Browser.FIREFOX)){
				fileNameToSave = new String(fileKoreanName.getBytes("UTF-8"), "8859_1");
			} else if(userAgent.getBrowser().getGroup().equals(Browser.CHROME)){
				fileNameToSave = new String(fileKoreanName.getBytes("UTF-8"), "8859_1");
			} else if(userAgent.getBrowser().getGroup().equals(Browser.SAFARI)){
				fileNameToSave = new String(fileKoreanName.getBytes("UTF-8"), "8859_1");
			} else {
				fileNameToSave = new String(fileKoreanName.getBytes("UTF-8"), "8859_1");
			}
			fileNameToSave = fileNameToSave.replaceAll("\\+", " ");
		} catch ( UnsupportedEncodingException e ){
			e.printStackTrace();
		}
		
		return fileNameToSave;
	}
	
	public static String getKoreanFileNameToSaveJeus(HttpServletRequest request, String fileKoreanName){
		//User Agent
		//UserAgent userAgent = UserAgent.parseUserAgentString(request.getHeader("User-Agent"));
		UserAgent userAgent = (UserAgent) request.getAttribute("userAgent");
		//LOGGER.info("##### UserAgent Group : {}, Name : {}, VERSION: {}", new Object[]{userAgent.getBrowser().getGroup(), userAgent.getBrowser(), userAgent.getBrowserVersion()});
		String fileNameToSave = null;
		
		try {
			if(userAgent.getBrowser().getGroup().equals(Browser.IE)){
				fileNameToSave = URLEncoder.encode(fileKoreanName, "UTF8");
			} /*else if(userAgent.getBrowser().getGroup().equals(Browser.FIREFOX)){
				fileNameToSave = new String(fileKoreanName.getBytes("UTF-8"), "8859_1");
			} else if(userAgent.getBrowser().getGroup().equals(Browser.CHROME)){
				fileNameToSave = new String(fileKoreanName.getBytes("UTF-8"), "8859_1");
				//System.out.println(fileNameToSave);
				//fileNameToSave = fileKoreanName;
				//System.out.println(fileNameToSave);
			} else if(userAgent.getBrowser().getGroup().equals(Browser.SAFARI)){
				fileNameToSave = new String(fileKoreanName.getBytes("UTF-8"), "8859_1");
			} else {
				fileNameToSave = new String(fileKoreanName.getBytes("UTF-8"), "8859_1");
			}*/
			else{
				fileNameToSave = fileKoreanName;
			}
			fileNameToSave = fileNameToSave.replaceAll("\\+", " ");
		} catch ( UnsupportedEncodingException e ){
			e.printStackTrace();
		}
		
		return fileNameToSave;
	}
	
	public static final int MAP_SORT_ASC = 100;
	public static final int MAP_SORT_DESC = 200;
	
	/**
	 * 맵을 value로 정렬, 단 value는 int, double 같은 숫자형이어야 함
	 * @param map
	 * @param sortDirection 100:ASC, 200:DESC
	 * @return
	 */
	public static <K, V extends Comparable<? super V>> Map<K, V> sortMapByValue(Map<K, V> map, int sortDirection) {
		List<Map.Entry<K, V>> list = new LinkedList<Map.Entry<K, V>>(map.entrySet());
		Collections.sort(list, new Comparator<Map.Entry<K, V>>() {
			public int compare(Map.Entry<K, V> o1, Map.Entry<K, V> o2) {
				if(o1 != null && o2 != null){
					return o1.getValue().compareTo(o2.getValue());
				} else {
					return -1;
				}
			}
		});

		if( sortDirection == MAP_SORT_DESC ){
			Collections.reverse(list);
		}
		
		Map<K, V> result = new LinkedHashMap<K, V>();
		for (Map.Entry<K, V> entry : list) {
			result.put(entry.getKey(), entry.getValue());
		}
		return result;
	}
	
	/**
	 * Date 객체의 시간을 정오(12:00)로 고정시키서 반환한다.
	 * @param date
	 * @return date
	 */
	public static Date makeNoon(Date dateParam){
		
		Calendar cal = Calendar.getInstance(Locale.KOREA);
		cal.setTime(dateParam);
		cal.set(Calendar.HOUR_OF_DAY, 12);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		Date date = cal.getTime();
				
		return date;
	}
	
	/**
	 * Calendar 객체 시간대 오후 12시로 고정
	 * @param cal
	 * @return Calendar 객체
	 */
	public static Calendar makeNoon(Calendar cal){
		
		cal.set(Calendar.HOUR_OF_DAY, 12);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
				
		return cal;
	}
	
	/**
	 * 현재날짜를 기준으로 전달받은 일수로 날자를 계산하여 Date를 반환하다.
	 * @param calculDay
	 * @return date
	 */
	public static Date makeCalculDate(int calculDay){
		
		return makeCalculDate(null, calculDay);
	}
	
	/**
	 * 전달받은 난자와 일수로 날자를 계산하여 Date를 반환하다.
	 * - 전달받은 날짜가 null 인경우 현제 날짜로 일수를 계산하여 반환한다.
	 * @param standardDate
	 * @param calculDay
	 * @return date
	 */
	public static Date makeCalculDate(Date standardDate, int calculDay){
		
		Calendar cal = Calendar.getInstance(Locale.KOREA);
		if(standardDate == null){
			cal.setTime(new Date());
		}else{
			cal.setTime(standardDate);
		}
		cal.add(Calendar.DATE, calculDay);
		Date date = cal.getTime();
		
		return date;
	}
	
	/** 기준Date에 전후 날짜를 계산하고 시간을 새로 셋팅한다.
	 * @param standardDate
	 * @param calculationDay
	 * @param time
	 * @return 계산된 일시
	 */
	public static Date getBeforeAfterDate(Date standardDate, int calculationDay, Integer time){
		Calendar cal = Calendar.getInstance(Locale.KOREA);
		cal.setTime(standardDate);
		cal.add(Calendar.DATE, calculationDay);
		if(time == 24){
			cal.set(Calendar.HOUR_OF_DAY, 23);
			cal.set(Calendar.MINUTE, 59);
			cal.set(Calendar.SECOND, 59);
		}else{
			cal.set(Calendar.HOUR_OF_DAY, time);
			cal.set(Calendar.MINUTE, 00);
			cal.set(Calendar.SECOND, 00);
		}
		Date date = cal.getTime();
		return date;
	}
	
	/**
	 * 기준Date에 시,분,초를 계산하여 Date를 반환한다.
	 * @param standardDate
	 * @param addHour
	 * @param addMinute
	 * @param addSecond
	 * @return 계산된 일시
	 */
	public static Date getAddTimeDate(Date standardDate, int addHour, int addMinute, int addSecond){
		Calendar cal = Calendar.getInstance(Locale.KOREA);
		cal.setTime(standardDate);
		if(addHour > 0){
			cal.add(Calendar.HOUR_OF_DAY, addHour);
		}
		if(addMinute > 0){
			cal.add(Calendar.MINUTE, addMinute);
		}
		if(addMinute > 0){
			cal.add(Calendar.SECOND, addSecond);
		}
		Date date = cal.getTime();
		return date;
	}

	/**
	 * 두 Date 객체 사이의 일수를 계산한다.(절대값)
	 * @param startDate
	 * @param bondScheduled
	 * @return
	 */
	public static int countAbsDaysOfTwoDays(Date date1Param, Date date2Param) {
		Date date1 = makeNoon(date1Param);
		Date date2 = makeNoon(date2Param);
		return (int)(Math.abs((date1.getTime() - date2.getTime())) / (1000 * 60 * 60 * 24f));
	}
	
	/**
	 * 두 시간의 차이를 int로 반환한다.
	 * @param time1Param
	 * @param time2Param
	 * @return 두시간의 차
	 */
	public static int calculTimeOfTwoTimes(String time1Param, String time2Param) {
		
		if(!isHourMinute(time1Param) ){
			LOGGER.info("[ASAPRO] time1 is no pattern!!"); 
			return 0;
		};
		if(!isHourMinute(time2Param) ){
			LOGGER.info("[ASAPRO] time2 is no pattern!!"); 
			return 0;
		};
		
		Date now = new Date();
		String[] time1Array = time1Param.split(":");
		String[] time2Array = time2Param.split(":");
		
		Calendar cal1 = Calendar.getInstance(Locale.KOREA);
		cal1.setTime(now);		
		cal1.set(Calendar.HOUR_OF_DAY, Integer.parseInt(time1Array[0]) );
		cal1.set(Calendar.MINUTE, Integer.parseInt(time1Array[1]));
		cal1.set(Calendar.SECOND, 0);
		cal1.set(Calendar.MILLISECOND, 0);

		Calendar cal2 = Calendar.getInstance(Locale.KOREA);
		cal2.setTime(now);		
		cal2.set(Calendar.HOUR_OF_DAY, Integer.parseInt(time2Array[0]) );
		cal2.set(Calendar.MINUTE, Integer.parseInt(time2Array[1]));
		cal2.set(Calendar.SECOND, 0);
		cal2.set(Calendar.MILLISECOND, 0);
		if(Integer.parseInt(time1Array[0]) > Integer.parseInt(time2Array[0]) ){
			cal2.add(Calendar.DATE, 1);
		}
		
		Date date1 = cal1.getTime();
		Date date2 = cal2.getTime();
		return (int)((date2.getTime() - date1.getTime()) / (1000 * 60 * 60));
	}
	
	/**
	 * 자리수 버림
	 * <pre>
	 * tatgetLevel 에 10을 인자로 주면 10자리 이하 버림
	 * tatgetLevel 에 100을 인자로 주면 100자리 이하 버림
	 * </pre>
	 * @param bigDecimal
	 * @param targetLevel
	 * @return
	 */
	public static BigDecimal floored(BigDecimal bigDecimal, int targetLevel){
		return new BigDecimal(String.valueOf(bigDecimal.longValue() / 10 * 10));
	}
	
	/**
	 * return file size human readable string
	 * @param value
	 * @param useSuffix
	 * @param capitalizeSuffix
	 * @return file size human readable string
	 */
	public static String getFileSizeString(long value, boolean useSuffix, boolean capitalizeSuffix){
		long absValue = Math.abs(value);
		String suffix = "";
		double result = value;
		if (value < 1024) {
			suffix = "bytes";
		} else if (absValue < 1024 * 1024) {
			result = value / 1024.0;
			suffix = "kb";
		} else if (absValue < 1024 * 1024 * 1024) {
			result = value / (1024.0 * 1024);
			suffix = "mb";
		} else {
			result = value / (1024.0 * 1024 * 1024);
			suffix = "gb";
		}
		if (useSuffix) {
			suffix = capitalizeSuffix ? suffix.toUpperCase() : suffix;
		} else {
			suffix = "";
		}
		return new DecimalFormat("0.0").format(result) + suffix;
	}

	/**
	 * Jsoup 라이브러리를 이용해서 텍스트를 필터링한 후 반환한다. 줄바꿈유지옵션추가.
	 * <pre>
	 * - 텍스트에서 태그를 제거한다.
	 * - 허용가능한 태그를 지정가능하다.
	 * - XSS 필터링에도 이용가능.
	 * - JSoup API 를 한번 간단하게 읽어보면 도움됨
	 * - WhiteList 별 허용 태그정보는 http://jsoup.org/apidocs/org/jsoup/safety/Whitelist.html 가서 확인
	 * </pre>
	 * @param src
	 * @param JSoupWhiteList
	 * @param keepCRLF 텍스트에어리어에서 작성시 줄바꿈유지여부
	 * @param treatBRAsCRLF br 태그를 줄바꿈으로 처리할지 여부
	 * @return
	 */
	public static String getJsoupFilteredText(String src, Whitelist jsoupWhiteList, boolean keepCRLF, boolean treatBRAsCRLF) {
		if( StringUtils.isBlank(src) ){
			return src;
		}
		if(jsoupWhiteList == null){
			jsoupWhiteList = Whitelist.none();
		}
		String string = src;
		if( keepCRLF ){
			string = string.replace("\r\n", "_CR_LF_");
			string = string.replace("\r", "_CR_");
			string = string.replace("\n", "_LF_");
			if( treatBRAsCRLF ){
				string.replaceAll("/(<br\\s?\\/?>)+/gi", "_CR_LF_");
			}
			string = Jsoup.clean(string, jsoupWhiteList);
			string = string.replace("_CR_LF_", "\r\n");
			string = string.replace("_CR_", "\r");
			string = string.replace("_LF_", "\n");
			
			return string;
		} else {
			return Jsoup.clean(string, jsoupWhiteList);
		}
	}

	/**
	 * jsp 파일에서 파일 임포트 또는 인클루드 할 경우 포워딩 되서 requestUri가 jsp로 나오는것 경우 대비
	 * <pre>
	 * - 뒤에 붙는 슬래시 제거함
	 * </pre>
	 * @param request
	 * @return requestUri
	 */
	public static String getFixedRequestUri(HttpServletRequest request) {
		String temp = request.getRequestURI();
		
		if( temp.startsWith(Constant.CONTEXT_PATH + "/WEB-INF/") && temp.endsWith(".jsp") ){
			//jsp의 주소창의 url
			//2018.08.08 jsp에서 인클루드된 url로 대처
			temp = (String)request.getAttribute("javax.servlet.include.request_uri"); 
			if(StringUtils.isNotBlank(temp)){
				if( temp.startsWith(Constant.CONTEXT_PATH + "/WEB-INF/") && temp.endsWith(".jsp") ){
					temp = (String)request.getAttribute("javax.servlet.forward.request_uri");  
				}
			}
			
			if(StringUtils.isBlank(temp)){
				temp = (String)request.getAttribute("javax.servlet.forward.request_uri");  
			}
		}
		
		//톰캣 jsessionid
		if( temp.contains(";") ){
			temp = temp.split(";")[0];
		}
		return StringUtils.removeEnd(temp, "/");
	}
	
	/**
	 * 현재페이지 url을 반환한다.(인코딩 하지 않는다.)
	 * @param request
	 * @param appendPort http(s)://www.aaa.com:8080/ 부분을 포함할 것인지 여부
	 * @return 현재 페이지 url
	 */
	public static String getCurrentUrl(HttpServletRequest request, boolean prependDomain){
		return getCurrentUrl(request, prependDomain, false);
	}
	
	/**
	 * 현재페이지 url을 반환한다.
	 * @param request
	 * @param appendPort http(s)://www.aaa.com:8080/ 부분을 포함할 것인지 여부
	 * @param encode 결과를 인코딩할것인지 여부
	 * @return 현재 페이지 url
	 */
	public static String getCurrentUrl(HttpServletRequest request, boolean prependDomain, boolean encode){
		StringBuilder sb = new StringBuilder();
		
		//http://aaa.bbb.com:8080
		if( prependDomain ){
			sb.append(getSchemeDomainPort(request));
		}
		
		String fixedRequestUri = AsaproUtils.getFixedRequestUri(request);
		sb.append( fixedRequestUri );
		
		String result = null;
		
		//예외처리 - 로그인페이지에서 또 로그인 링크 누르는 경우 해당
		//일단은 이렇게 처리해놓고 문제 생길때마다 맞춰서 수정해야 할듯함.
//		String returnUrl = request.getParameter("returnUrl");
//		if( fixedRequestUri.contains("/login") && StringUtils.isNotBlank(returnUrl) ){
//			result = returnUrl;
//		}
		//기본처리
//		else {
			String queryString = request.getQueryString();
			if( StringUtils.isNotBlank(queryString) ){
				sb.append("?");
				sb.append( queryString );
			}
			result = sb.toString();
//		}
		
		if( encode ){
			try {
				result = URLEncoder.encode(result, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		
		return result;
	}
	
	/**
	 * get 파라메터를 포함한 전체 uri 을 반환한다.
	 * @param request
	 * @return url
	 */
	public static String getRequestUriWithParameters(HttpServletRequest request){
		if( StringUtils.isNotBlank(request.getQueryString()) ){
			return AsaproUtils.getFixedRequestUri(request) + "?" + request.getQueryString();
		} else {
			return AsaproUtils.getFixedRequestUri(request);
		}
	}

	/**
	 * http://aaa.bbb.com:8080 까지만 반환한다.
	 * @param request
	 * @return 스킴, 도메인, 포트를 반환한다.
	 */
	public static String getSchemeDomainPort(HttpServletRequest request) {
		return getSchemeDomainPort(request, null, null);
	}
	
	/**
	 * http://aaa.bbb.com:8080 까지만 반환한다.
	 * @param request
	 * @return 스킴, 도메인, 포트를 반환한다.
	 */
	public static String getSchemeDomainPort(HttpServletRequest request, String scheme, String port) {
		StringBuilder sb = new StringBuilder(30);
		if( StringUtils.isNotBlank(scheme) ){
			sb.append(scheme);
		} else {
			sb.append(request.getScheme());
			scheme = request.getScheme();
		}
		sb.append("://");
		sb.append(request.getServerName());
		if( StringUtils.isNotBlank(port) ){
			if( !"80".equals(port) && !"443".equals(port) ){
				sb.append(":");
				sb.append(port);
			}
		} else {
			if( "https".equals(scheme) ){
				if( request.getServerPort() != 443 ){
					sb.append(":");
					sb.append(request.getServerPort());
				}
				//if( StringUtils.isBlank(port) ){
				//	sb.append(":443");
				//}
			} else {
				if( request.getServerPort() != 80 && request.getServerPort() != 443 ){
					sb.append(":");
					sb.append(request.getServerPort());
				}
			}
		}
		return sb.toString();
	}
	
	/**
	 * aaa.bbb.com:8080 까지만 반환한다.
	 * @param request
	 * @return 도메인, 포트를 반환한다.
	 */
	public static String getDomainPort(HttpServletRequest request, String scheme, String port) {
		StringBuilder sb = new StringBuilder(30);
		if( StringUtils.isBlank(scheme) ){
			scheme = request.getScheme();
		}
		sb.append(request.getServerName());
		if( StringUtils.isNotBlank(port) ){
			if( !"80".equals(port) && !"443".equals(port) ){
				sb.append(":");
				sb.append(port);
			}
		} else {
			if( "https".equals(scheme) ){
				if( request.getServerPort() != 443 ){
					sb.append(":");
					sb.append(request.getServerPort());
				}
				//if( StringUtils.isBlank(port) ){
				//	sb.append(":443");
				//}
			} else {
				if( request.getServerPort() != 80 && request.getServerPort() != 443 ){
					sb.append(":");
					sb.append(request.getServerPort());
				}
			}
		}
		return sb.toString();
	}
	
	/**
	 * BindingResult 의 에러메세지를 모두 합쳐서 하나로 만들어 반환한다.
	 * @param bindingResult
	 * @param glue 
	 * @return 전체 에러 메세지
	 */
	public static String getAllFieldErrorMessage(BindingResult bindingResult, String glue) {
		List<FieldError> errorList = bindingResult.getFieldErrors();
		StringBuilder sb = new StringBuilder(100);
		FieldError fieldError = null;
		for( int i = 0; i<errorList.size(); i++ ){
			fieldError = errorList.get(i);
			if( i > 0 ){
				sb.append(glue);
			}
			sb.append(fieldError.getDefaultMessage());
		}
		return sb.toString();
	}
	
	//아이피어드레스 정규식
	private static final String IPADDRESS_PATTERN = "^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";
	
	/**
	 * 문자열이 아피 어드레스형태인지 확인한다.
	 * @param pattern
	 * @return
	 */
	public static boolean isIPAdress(String string) {
		Pattern p = Pattern.compile(IPADDRESS_PATTERN);
		return p.matcher(string).matches();
	}

	/**
	 * 쿠키를 반환한다.
	 * @param cookies
	 * @param cookieName
	 * @return
	 */
	public static Cookie getCookie(Cookie[] cookies, String cookieName) {
		if( cookies != null ){
			for( Cookie cookie : cookies ){
				if( cookieName.equals(cookie.getName()) ){
					return cookie;
				}
			}
		}
		return null;
	}
	
	/**
	 * 쿠키를 삭제한다. path 기본값 / 로 세팅한다.
	 * @param securityAsyncCookieName
	 */
	public static void deleteCookie(HttpServletRequest request, HttpServletResponse response, String cookieName) {
		deleteCookie(request, response, cookieName, "/");
	}
	
	/**
	 * 쿠키를 삭제한다.
	 * @param response
	 * @param cookieName
	 * @param path
	 */
	public static void deleteCookie(HttpServletRequest request, HttpServletResponse response, String cookieName, String path) {
		Cookie cookie = new Cookie(cookieName, null);
		Config globalConfig = (Config) request.getAttribute("globalConfig");
		if(globalConfig != null){
			cookie.setDomain(globalConfig.getOption("cookie_domain"));
		} else {
			LOGGER.info("[ASAPRO] AsaproUtils global config is null. maybe sub sirectory site request?");
		}

		cookie.setPath(path);
		cookie.setMaxAge(0);
		cookie.setHttpOnly(true);
		response.addCookie(cookie);
	}
	
	/**
	 * 음력말일로 처리하기 위한 상수(=32)
	 */
	public static final int LUNAR_LAST_DAY_OF_MONTH = 32;
	
	/**
	 * ICU4J를 이용해서 음력을 양력으로 변환한다.
	 * <pre>
	 * - 음력말일은 AsaproUtils.LUNAR_LAST_DAY_OF_MONTH 상수를 31일 대신 입력한다.
	 * - 참고 : http://blog.daum.net/shoebox/6202375
	 * </pre>
	 * @param lunarCal
	 * @return
	 */
	public static Calendar fromLunarCalToSolarCal( int lunarYear, int lunarMonth, int lunarDay ){
		
		//구정 12월 31일은 전년도로 만들어줘야 해서...이렇게 하는게 맞게하는건지 알수 없음.
		if( lunarMonth == 12 && lunarDay == LUNAR_LAST_DAY_OF_MONTH ){
			lunarYear -= 1;
		}
		ChineseCalendar chineseCalendar = new ChineseCalendar();
		chineseCalendar.set(ChineseCalendar.EXTENDED_YEAR, lunarYear + 2637);
		chineseCalendar.set(ChineseCalendar.MONTH, lunarMonth - 1);
		//구정 12월 31일은 전년도로 만들어줘야 해서...이렇게 하는게 맞게하는건지 알수 없음.
		//음력으로 31일이라고 썼지만 음력 31이 없을 수도 있으므로 다시 구함
		if( lunarMonth == 12 && lunarDay == LUNAR_LAST_DAY_OF_MONTH ){
			lunarDay = chineseCalendar.getActualMaximum(ChineseCalendar.DAY_OF_MONTH);
		}
		chineseCalendar.set(ChineseCalendar.DAY_OF_MONTH, lunarDay);
		chineseCalendar.set(ChineseCalendar.IS_LEAP_MONTH, 0);
		//음력 -> 양력
		Calendar solarCal = Calendar.getInstance();
		solarCal.setTimeInMillis(chineseCalendar.getTimeInMillis());
		
		return solarCal;
	}
	
	/**
	 * 공백을 하이픈으로 교체하고, uri segment로 사용할 문자열을 반환한다.
	 * <pre>
	 * 테스트 문자열
	 * ex) 박지성의 활약으로 QPR 승점 상승···’하지만 그래봤자..’
	 * ex) 英언론 박지성에 평점 6, 레드냅 감독 극찬
	 * </pre>
	 * @param string
	 * @return 슬러그
	 * @throws UnsupportedEncodingException
	 */
	public static String string2Slug(String str){
		return string2Slug(str, true);
	}
	
	/**
	 * 공백을 하이픈으로 교체하고, uri segment로 사용할 문자열을 반환한다.
	 * <pre>
	 * 테스트 문자열
	 * ex) 박지성의 활약으로 QPR 승점 상승···’하지만 그래봤자..’
	 * ex) 英언론 박지성에 평점 6, 레드냅 감독 극찬
	 * </pre>
	 * @param string
	 * @param unCapitalize 영대문자-> 소문자 여부
	 * @return 슬러그
	 * @throws UnsupportedEncodingException
	 */
	public static String string2Slug(String str, boolean unCapitalize){
		//extract text
		str = Jsoup.parse(str).text();
		// 마지막에 있는 . 제거
		str = StringUtils.removeEnd(str, ".");
		// |“|”, |’|‘=> 똑같은거 두개 들어간거 아니니까 하나 지우지 말것. 두개 다른거임. 
		str = str.replaceAll("\\+|;|\\?|\"|'|“|”|’|‘|\\.|『|』|「|」|`|\\!|#|\\$|%|\\^|\\*|@|\\||~|_|［|］", "");
		//공백...등 - 로 치환
		str = str.replaceAll("\\s+|&|=|,|\\[|\\]|\\{|\\}|\\<|\\>|:|\\(|\\)|…|/|：|:|;", "-");
		//--- 같은거 - 로 치환
		str = str.replaceAll("-+|·+", "-");
		//앞에 붙은 - 제거
		str = StringUtils.removeStart(str, "-");
		//뒤에 붙은 - 제거
		str = StringUtils.removeEnd(str, "-");
		if( unCapitalize ){
			//영문은 다 소문자로
			str = str.toLowerCase();
		}
		return str;
	}
	
	/**
	 * 문자열을 태그로 바꾼다.(슬러그랑 동일하나 '-' 대신 공백으로 입력한다)
	 * @param str
	 * @return
	 */
	public static String string2TagName(String str){
		return AsaproUtils.string2Slug(str, false).replace("-", " ");
	}
	
	/**
	 * Json을 출력한다.
	 * <pre> 
	 * 왠만하면 @responseBody Annotation으로 처리하겠지만
	 * 부득이 한 경우 최후의 수단으로 사용하자.
	 * - 현재는 파이어폭스에서 uploadify 사용시만 사용한다.(media/upload.do) 
	 * </pre>
	 * @param response
	 * @param odject
	 * @throws JsonGenerationException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	public static void writeJson(HttpServletResponse response, Object object){
		ObjectMapper mapper = new ObjectMapper();
		response.setContentType("application/json; charset=UTF-8");
		try {
			mapper.writeValue(response.getWriter(), object);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 오브젝트를 json 으로 변환한다.
	 * @param object
	 * @return json
	 */
	public static String toJson(Object object){
		ObjectMapper mapper = new ObjectMapper();
		String json = null;
		try {
			json = mapper.writeValueAsString(object);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return json;
	}
	
	/**
	 * UTF8 로 인코딩되었으면 디코딩해서 반환한다.
	 * @param str
	 * @return 디코딩된 문자열
	 */
	public static String getDecodedIfEncoded(String str){
		if( str.contains("%") ){
			try {
				str = URLDecoder.decode(str, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		return str;
	}
	
	/**
	 * get 파라메터 한글 인코딩을 처리한다.
	 * @param string
	 * @param fromChar
	 * @param toChar
	 * @return get 파라메터 한글인코딩
	 */
	public static String fixKorParameter(String string, Config config){
		if( StringUtils.isNotBlank(string) ){
			try {
				if("true".equals(config.getOption("fix_kor_active"))){
					return new String(string.getBytes(config.getOption("fix_kor_from")), config.getOption("fix_kor_to"));
				} else {
					return string;
				}
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		return "";
	}

	/**
	 * 콘트롤러에서 반환할 뷰파일의 현재테마 jsp 경로를 반환한다.
	 * <pre>
	 * - 전체경로말고 jsp prefix 뒤에 붙는 나머지 부분만 반환함.
	 * - WEB-INF 이하 테마 전체경로는 Constant.THEME_ROOT 이용
	 * </pre>
	 * @param request
	 * @return 테마 jsp 경로
	 */
	public static String getThemePath(HttpServletRequest request) {
		return "asapro/theme/" + request.getAttribute("theme") + "/";
	}

	/**
	 * 사이트별 컨텍스트 내의 업로드 디렉토리를 반환한다.
	 * @param request
	 * @return 사이트별 업로드 디렉토리
	 */
	public static String getSiteContextUploadDirectory(HttpServletRequest request) {
		String webRoot = null;
		webRoot = AsaproUtils.getWebRoot(request);
		webRoot = FilenameUtils.getFullPathNoEndSeparator(webRoot).replaceAll("\\\\", "/");
		Site currentSite = (Site) request.getAttribute("currentSite");
		return webRoot + Constant.UPLOAD_PATH  + "/" + currentSite.getSiteId();
	}
	
	/**
	 * 사이트별 컨텍스트 외부의 업로드 디렉토리를 반환한다.(설정에 정한 디렉토리를 반환)
	 * @param request
	 * @return 사이트별 업로드 디렉토리(설정에 정한 디렉토리를 반환)
	 */
	public static String getSiteUploadDirectory(HttpServletRequest request) {
		String uploadFolderPath = ""; 
		
		String webRoot = null;
		webRoot = AsaproUtils.getWebRoot(request);
		webRoot = FilenameUtils.getFullPathNoEndSeparator(webRoot).replaceAll("\\\\", "/");
		
		//EgovProperties.getProperty("Globals.fileStorePath")
		//Globals.fileStorePath 의 값이 있을경우 지정한 경로에 저장되게 하고 값이없을경우 디폴트로 설정된 컨텍스트내 폴더에 저장된다.
		String fileStorePath = EgovProperties.getProperty("Globals.fileStorePath");
		if(StringUtils.isNotBlank(fileStorePath)){
			uploadFolderPath = fileStorePath;
		}else{
			uploadFolderPath = webRoot;
		}
		
		Site currentSite = (Site) request.getAttribute("currentSite");
		return uploadFolderPath + Constant.UPLOAD_PATH  + "/" + currentSite.getSiteId();
	}

	/**
	 * CR LF 를 &lt;br/> 로 바꿔서 반환한다.
	 * @param html
	 * @return
	 */
	public static String nl2br(String html) {
		//String str = html.replace("\r\n", "<br />");
		//str = str.replace("\r", "<br />");
		//str = str.replace("\n", "<br />");
		String value = html
				.replace("\r\n", "<br />")
				.replace("\n\r", "<br />")
				.replace("\r", "<br />")
				.replace("\n", "<br />");
		//value = value.replaceAll("(\\s*<br \\/>\\s*)+", "<br />");
		value = value
				.replaceAll("(\\s*<br \\/>\\s*){3,}", "<br /><br />");//br 3개이상 연속 나오는건 2개로 바꿈
		
		return value;
	}

	/**
	 * 패턴을 테그로 바꿔서 반환한다.
	 * @param text
	 * @return
	 */
	public static String pattern2tag(String text) {
		//텍스트를 <p><span>   </p>로 감싸고
		//". "를 </span>올 치환
		//엔터값을 </p><p><span> 으로 치환한다.
		
		String value = "<p><span>" + text + "</p>";
		value = value.replaceAll("\\. ", "</span>");
		value = value.replace("\r\n", "</p><p><span>")
				.replace("\r", "</p><p><span>")
				.replace("\n", "</p><p><span>");
		return value;
	}
	
	/**
	 * uri 로부터 슬러그부분울 파싱해서 반환한다.
	 * @param requestUri
	 * @return slug
	 */
	public static String getSlugFromRequestUri(String requestUri) {
		// ${APP_PATH}/archive/post/2015-세계산불총회-종합계획-수립-용역-입찰공고
		String[] temps = requestUri.split("/");
		int size = temps.length;
		String slug = temps[size - 1];
		try {
			slug = URLDecoder.decode(slug, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return slug;
	}
	
	/**
	 * 세션에서 해당 접두어로 시작하는 속성값을 삭제한다.
	 * @param request
	 * @param startsWith
	 */
	public static void removeFromSessionStartsWith(HttpServletRequest request, String startsWith) {
		@SuppressWarnings("rawtypes")
		Enumeration enums = request.getSession().getAttributeNames();
		while( enums.hasMoreElements() ){
			String key = (String) enums.nextElement();
			if( key.startsWith(startsWith) ){
				request.getSession().removeAttribute(key);
			}
		}
	}
	
	/**
	 * 세션에서 해당 접두어로 시작하는 속성값을 제외하고 삭제한다.
	 * @param request
	 * @param startsWith
	 */
	public static void removeFromSessionExcludeStartsWith(HttpServletRequest request, String startsWith) {
		@SuppressWarnings("rawtypes")
		Enumeration enums = request.getSession().getAttributeNames();
		while( enums.hasMoreElements() ){
			String key = (String) enums.nextElement();
			if( !key.startsWith(startsWith) ){
				request.getSession().removeAttribute(key);
			}
		}
	}

	/**
	 * 웹루트 패스를 반환다.
	 * @param request
	 * @return 웹루트 패스
	 */
	public static String getWebRoot(HttpServletRequest request) {
		String webRoot = null;
		try {
			webRoot = WebUtils.getRealPath(request.getSession().getServletContext(), "/");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return webRoot;
	}
	
	/**
	 * 문자열 깨진 경우 복구하기 위한 인코딩을 찾는데 사용한다.
	 * <pre>
	 * - 사용법 예시 : AsaproUtils.guessEncoding("문자열", "EUC-KR", "UTF-8", "8859_1");
	 * </pre>
	 * @param str
	 * @param encodings
	 */
	public static void guessEncoding(String str, String...encodings){
		if( StringUtils.isBlank(str) ){
			System.out.println("AsaproUtils : str is null.");
		}
		try {
			System.out.println("AsaproUtils : START SHALLOW MODE ============================");
			for( String getBytesEnc : encodings ){
				for( String newStringEnc : encodings ){
					System.out.println("AsaproUtils guessEncoding SHALLOW! : " + getBytesEnc + ",	" + newStringEnc + " = " + new String(str.getBytes(getBytesEnc), newStringEnc));
				}
			}
			/*
			System.out.println("AsaproUtils : START DEEP MODE ============================");
			for( String getBytesEnc : encodings ){
				for( String newStringEnc : encodings ){
					for( String getBytesEncDeep : encodings ){
						for( String newStringEncDeep : encodings ){
							System.out.println("AsaproUtils guessEncoding DEEP! : " + getBytesEnc + ", " + newStringEnc + ", " + getBytesEncDeep + ", " + newStringEncDeep + " = " + new String(str.getBytes(getBytesEnc), newStringEnc));
						}
					}
				}
			}
			*/
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 문자열 깨진 경우 복구하기 위한 인코딩을 찾는데 사용한다.
	 * @param str
	 */
	public static void guessEncoding(String str){
		guessEncoding(str, "EUC-KR", "8859_1", "UTF-8");
	}
	
	/**
	 * 한글 문자가 포함되어 있는 체크결과를 반환한다.
	 * @param string
	 * @return 한글문자 포함 여부
	 */
	public static boolean hasKoreanChars(String string){
		if( string == null || "".equals(string) ){
			return false;
		}
		return string.matches(".*[ㄱ-ㅎㅏ-ㅣ가-힣]+.*");
	}
	
	/**
	 * Xpath 를 사용해서 Xml 파일에서 데이터를 가져온다.
	 * <pre>
	 * Xpath 사용법은 검색하면 나옴...
	 * !!! xml 파일 크기가 클때는 사용하지 마세요.
	 * </pre>
	 * @param filePath
	 * @param xPathExpression
	 * @return
	 */
	public static String getXmlData(String filePath, String xPathExpression){
		String data = null;
		try {
			DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document document = documentBuilder.parse(filePath);
			XPath xPath = XPathFactory.newInstance().newXPath();
			data = xPath.evaluate(xPathExpression, document);
		} catch (XPathExpressionException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
		return data;
	}
	
	/**
	 * Xpath 를 사용해서 Xml 파일에서 데이터를 가져온다.
	 * <pre>
	 * Xpath 사용법은 검색하면 나옴...
	 * !!! xml 파일 크기가 클때는 사용하지 마세요.
	 * </pre>
	 * @param document
	 * @param xPathExpression
	 * @return
	 */
	public static String getXmlData(Document document, String xPathExpression){
		String data = null;
		try {
			XPath xPath = XPathFactory.newInstance().newXPath();
			data = xPath.evaluate(xPathExpression, document);
		} catch (XPathExpressionException e) {
			e.printStackTrace();
		}
		return data;
	}
	
	/**
	 * 앱 패스를 반환한다.
	 * @param site
	 * @return 앱 패스
	 */
	public static String getAppPath(Site site){
		return getAppPath(site, false);
		
	}
	
	/**
	 * 앱 패스를 반환한다. 리다이렉트 여부에 따라 콘텍스트 패스를 제외
	 * @param site
	 * @param isRedilect
	 * @return
	 */
	public static String getAppPath(Site site, boolean isRedilect){
		if(isRedilect){//리다이렉트에 사용되는 경우(컨텍스트 패스 제외)
			if( site.isSiteMain() ){
				return Constant.APP_PATH + "/" + Constant.MAIN_SITE_DISPLAY_ID;
			} else {
				return Constant.APP_PATH + "/" + site.getSiteId();
			}
		}else{
			if( site.isSiteMain() ){
				return Constant.CONTEXT_PATH + Constant.APP_PATH + "/" + Constant.MAIN_SITE_DISPLAY_ID;
			} else {
				return Constant.CONTEXT_PATH + Constant.APP_PATH + "/" + site.getSiteId();
			}
		}
	}
	
	
	/**
	 * 관리자 패스를 반환한다.
	 * @param site
	 * @return 관리자 패스
	 */
	public static String getAdminPath(Site site){
		return getAdminPath(site, false);
	}
	
	/**
	 * 관리자 패스를 반환한다. 리다이렉트에 사용되는 경우(컨텍스트 패스 제외)
	 * @param site
	 * @param isRedilect
	 * @return
	 */
	public static String getAdminPath(Site site, boolean isRedilect){
		if(isRedilect){//리다이렉트에 사용되는 경우(컨텍스트 패스 제외)
			if( site.isSiteMain() ){
				return "/" + Constant.MAIN_SITE_DISPLAY_ID + Constant.ADMIN_PATH;
			} else {
				return "/" + site.getSiteId() + Constant.ADMIN_PATH;
			}
		}else{
			if( site.isSiteMain() ){
				return Constant.CONTEXT_PATH + "/" + Constant.MAIN_SITE_DISPLAY_ID + Constant.ADMIN_PATH;
			} else {
				return Constant.CONTEXT_PATH + "/" + site.getSiteId() + Constant.ADMIN_PATH;
			}
		}
	}
	
	/**
	 * 관리자단패스인지 확인
	 * @param requestUri
	 * @return 관리자단 패스 여부
	 */
	public static boolean isAdminPath(String requestUri){
		return (requestUri.contains(Constant.ADMIN_PATH +  "/") && requestUri.endsWith(".do")) || requestUri.contains(Constant.ADMIN_PATH +  ".do");
	}
	
	/**
	 * 사용자단 패스인지 확인
	 * @param requestUri
	 * @return
	 */
	public static boolean isUserPath(String requestUri){
		return requestUri.contains(Constant.APP_PATH +  "/") && !requestUri.endsWith(".do");
	}
	
	/**
	 * API 패스인지 확인
	 * @param requestUri
	 * @return
	 */
	public static boolean isApiPath(String requestUri){
		return requestUri.contains(Constant.API_PATH + "/");
	}
	
	/**
	 * 파일 패스인지 확인
	 * @param requestUri
	 * @return
	 */
	public static boolean isFilePath(String requestUri){
		return requestUri.contains(Constant.FILE_PATH + "/");
	}
	
	/**
	 * FEED 패스인지 확인
	 * @param requestUri
	 * @return
	 */
	public static boolean isFeedPath(String requestUri){
		return requestUri.contains(Constant.FEED_PATH + "/");
	}
	
	/**
	 * 로컬개발 IP또는 localhost체크
	 * @param serverName
	 * @return
	 */
	public static boolean isDevPath(String serverName){
		return serverName.contains("localhost") || serverName.contains("127.0.0.1") || serverName.contains(".dev.");
	}
	
	/**
	 * 로컬개발 IP또는 localhost체크
	 * @param serverName
	 * @return
	 */
	public static boolean isDevPath(HttpServletRequest request){
		return request.getServerName().contains("localhost") || request.getServerName().contains("127.0.0.1") || request.getServerName().contains(".dev.");
	}
	
	/**
	 * 회사 개발서버 확인
	 * @param request
	 * @return
	 */
	public static boolean isAsadalNet(HttpServletRequest request){
		return request.getServerName().contains(".asadal.com") || request.getServerName().contains("127.0.0.1");//아이피 일단 아무거나
	}
	
	/**
	 * 쿼리스트링 형태의 문자열을 파싱해서 Map&lt;String, String[]> 으로 변환해서 반환
	 * - HttpUtils.parseQueryString 가 deprecated 되어서 메소드의 소스를 옮겨 놓은 것임.
	 * @param s
	 * @return
	 */
	public static Map<String, String[]> parseQueryString(String s) {

		String valArray[] = null;

		if (s == null) {
			throw new IllegalArgumentException();
		}
		Map<String, String[]> hm = new HashMap<String, String[]>();
		StringBuffer sb = new StringBuffer();
		StringTokenizer st = new StringTokenizer(s, "&");
		while (st.hasMoreTokens()) {
			String pair = (String) st.nextToken();
			int pos = pair.indexOf('=');
			if (pos == -1) {
				// XXX
				// should give more detail about the illegal argument
				throw new IllegalArgumentException();
			}
			String key = parseName(pair.substring(0, pos), sb);
			String val = parseName(pair.substring(pos + 1, pair.length()), sb);
			if (hm.containsKey(key)) {
				String oldVals[] = (String[]) hm.get(key);
				valArray = new String[oldVals.length + 1];
				for (int i = 0; i < oldVals.length; i++)
					valArray[i] = oldVals[i];
				valArray[oldVals.length] = val;
			} else {
				valArray = new String[1];
				valArray[0] = val;
			}
			hm.put(key, valArray);
		}
		return hm;
	}
	
	//파라메터 파싱
	private static String parseName(String s, StringBuffer sb) {
		sb.setLength(0);
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			switch (c) {
			case '+':
				sb.append(' ');
				break;
			case '%':
				try {
					sb.append((char) Integer.parseInt(s.substring(i + 1, i + 3), 16));
					i += 2;
				} catch (NumberFormatException e) {
					// XXX
					// need to be more specific about illegal arg
					throw new IllegalArgumentException();
				} catch (StringIndexOutOfBoundsException e) {
					String rest = s.substring(i);
					sb.append(rest);
					if (rest.length() == 2)
						i++;
				}

				break;
			default:
				sb.append(c);
				break;
			}
		}
		return sb.toString();
	}
	
	/**
	 * 랜덤 UUID 제공.(-제거)
	 * @return
	 */
	public static String getRandomUUID(){
		return UUID.randomUUID().toString().replace("-", "");
	}
	
	/**
	 * 로그인 url 을 반환한다.
	 * @param returlUrl 리턴url
	 * @return
	 */
	public static String getLoginUrl(Site currentSite, String returlUrl){
		if(StringUtils.isBlank(returlUrl)){
			return AsaproUtils.getAppPath(currentSite) + "/member/public/login";
		} else {
			try {
				return AsaproUtils.getAppPath(currentSite) + "/member/public/login?returnUrl=" + URLEncoder.encode(returlUrl, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
				return AsaproUtils.getAppPath(currentSite) + "/member/public/login?returnUrl=" + returlUrl;
			}
		}
	}
	
	//시분 정규식
	private static final String HOUR_MINUTE_PATTERN = "^(0[0-9]|1[0-9]|2[0-3]):([0-5][0-9])$";
	
	/**
	 * 문자열이 시간:분(00:00) 형태인지 확인한다.
	 * @param pattern
	 * @return
	 */
	public static boolean isHourMinute(String string) {
		Pattern p = Pattern.compile(HOUR_MINUTE_PATTERN);
		return p.matcher(string).matches();
	}
	
	/**
	 * 해당날짜의 요일을 텍스트로 반환한다.
	 * @param dateString
	 * @return 요일(text)
	 * @throws ParseException
	 */
	public static String getDayOfWeekText(String dateString) throws ParseException{
		if( StringUtils.isBlank(dateString) ){
			return null;
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date nDate = sdf.parse(dateString);
		Calendar cal = Calendar.getInstance() ;
	    cal.setTime(nDate);
	    
	    int dayNum = cal.get(Calendar.DAY_OF_WEEK) ;
	    String day = "" ;
	    
	    switch(dayNum){
	        case 1:
	            day = "Sun";
	            break ;
	        case 2:
	            day = "Mon";
	            break ;
	        case 3:
	            day = "Tue";
	            break ;
	        case 4:
	            day = "Wed";
	            break ;
	        case 5:
	            day = "Thu";
	            break ;
	        case 6:
	            day = "Fri";
	            break ;
	        case 7:
	            day = "Sat";
	            break ;
	    }
	     
	    return day ;
	}
	
	/**
	 * 해당날짜의 요일을 한글 텍스트로 반환한다.
	 * @param dateString
	 * @return 요일(text)
	 * @throws ParseException
	 */
	public static String getDayOfWeekKorText(String dateString) throws ParseException{
		if( StringUtils.isBlank(dateString) ){
			return null;
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date nDate = sdf.parse(dateString);
		Calendar cal = Calendar.getInstance() ;
		cal.setTime(nDate);
		
		int dayNum = cal.get(Calendar.DAY_OF_WEEK) ;
		String day = "" ;
		
		switch(dayNum){
		case 1:
			day = "일";
			break ;
		case 2:
			day = "월";
			break ;
		case 3:
			day = "화";
			break ;
		case 4:
			day = "수";
			break ;
		case 5:
			day = "목";
			break ;
		case 6:
			day = "금";
			break ;
		case 7:
			day = "토";
			break ;
		}
		
		return day ;
	}
	
	/**
	 * 해당 요일의 번호를 영문 텍스트로 반환한다.
	 * @param day
	 * @return 요일텍스트
	 * @throws ParseException
	 */
	public static String getDayOfWeekText(int day) throws ParseException{

		String dayStr = "" ;
		
		switch(day){
		case 1:
			dayStr = "Sun";
			break ;
		case 2:
			dayStr = "Mon";
			break ;
		case 3:
			dayStr = "Tue";
			break ;
		case 4:
			dayStr = "Wed";
			break ;
		case 5:
			dayStr = "Thu";
			break ;
		case 6:
			dayStr = "Fri";
			break ;
		case 7:
			dayStr = "Sat";
			break ;
		}
		
		return dayStr ;
	}
	
	/**
	 * 해당 요일의 번호를 한글 텍스트로 반환한다.
	 * @param day
	 * @return 요일텍스트
	 * @throws ParseException
	 */
	public static String getDayOfWeekKorText(int day) throws ParseException{

		String dayStr = "" ;
		
		switch(day){
		case 1:
			dayStr = "일";
			break ;
		case 2:
			dayStr = "월";
			break ;
		case 3:
			dayStr = "화";
			break ;
		case 4:
			dayStr = "수";
			break ;
		case 5:
			dayStr = "목";
			break ;
		case 6:
			dayStr = "금";
			break ;
		case 7:
			dayStr = "토";
			break ;
		}
		
		return dayStr ;
	}
	
	/**
	 * 시분초가 조정된 Date 객체를 반환한다.
	 * @param date
	 * @param hour24
	 * @param min
	 * @param sec
	 * @return
	 */
	public static Date getTimeChangedDate(Date date, int hour24, int min, int sec){
		if( date == null ){
			return null;
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, hour24);
		cal.set(Calendar.MINUTE, min);
		cal.set(Calendar.SECOND, sec);
		
		return cal.getTime();
	}
	
	/**
	 * Date 를 포맷한 문자열을 반환한다.
	 * <pre>
	 * - default format = yyyy-MM-dd
	 * </pre>
	 * @param date
	 * @return
	 */
	public static String getFormattedDate(Date date){
		return getFormattedDate(date, "yyyy-MM-dd");
	}
	
	/**
	 * Date 를 포맷한 문자열을 반환한다.
	 * <pre>
	 * - default format = yyyy-MM-dd
	 * </pre>
	 * @param date
	 * @param format
	 * @return
	 */
	public static String getFormattedDate(Date date, String format){
		if( date == null ){
			return null;
		}
		if( StringUtils.isBlank(format) ){
			format = "yyyy-MM-dd";
		}
		return new SimpleDateFormat(format).format(date);
	}
	
	/**
	 * 문자열을 파싱 후 Date로 변환해서 반환한다.
	 * <pre>
	 * - default pattern = yyyy-MM-dd HH:mm:ss
	 * </pre>
	 * @param dateString
	 * @return
	 */
	public static Date getParsedDate(String dateString){
		return getParsedDate(dateString, "yyyy-MM-dd HH:mm:ss");
	}
	
	/**
	 * 문자열을 파싱 후 Date로 변환해서 반환한다.
	 * <pre>
	 * - default pattern = yyyyMM-dd HH:mm:ss
	 * </pre>
	 * @param dateString
	 * @param pattern
	 * @return
	 */
	public static Date getParsedDate(String dateString, String pattern){
		if( StringUtils.isBlank(dateString) ){
			return null;
		}
		if( StringUtils.isBlank(pattern) ){
			pattern = "yyyy-MM-dd HH:mm:ss";
		}
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		try {
			return sdf.parse(dateString);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 디렉토리 생성하고 권한 확인(필요하다면 부모폴더까지 생성함)
	 * @param directoryPath
	 * @return
	 */
	public static boolean forceMakeDirectory(String directoryPath){
		if( StringUtils.isBlank(directoryPath) ){
			return false;
		}
		File directory = new File(directoryPath);
		//이미 있는데 디렉토리가 아니다
		if( directory.exists() && !directory.isDirectory() ){
			System.out.println("AsaproUtils : " + directoryPath + " is already exists. But target is not a directory.");
			return false;
		} else if( directory.exists() && directory.isDirectory() ){
			// pass
		} else {
			try {
				FileUtils.forceMkdir(directory);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if( directory.exists() ){
			if( directory.canRead() ){ directory.setReadable(true, false); }
			if( directory.canWrite() ){ directory.setWritable(true, false); }
			if( directory.canExecute() ){ directory.setExecutable(true, false); }
		}
		return true;
	}
	
	/**
	 * 파일명 uuid 화 한 후 확장자 다시 붙여서 반환
	 * @param filename
	 * @return
	 */
	public static final String makeUUIDFileSaveNameWithExt(String filename){
		if( StringUtils.isBlank(filename) ){
			return null;
		}
		String ext = FilenameUtils.getExtension(filename);
		String dotExt = "";
		if( !StringUtils.isBlank(ext) ){
			dotExt = "." + ext.toLowerCase();
		}
		return getRandomUUID() + dotExt; 
	}
	
	/**
	 * 만나이계산
	 * @param birthday
	 * @param parsePattern 생일날짜 포맷
	 * @return
	 */
	public static int getFullAge(String birthdayString, String parsePattern){
		if( StringUtils.isBlank(birthdayString) || StringUtils.isBlank(parsePattern) ){
			return 0;
		}
		
		try {
			Date birthday = DateUtils.parseDate(birthdayString, parsePattern);
			
			Calendar todayCal = Calendar.getInstance(Locale.KOREA);
			Date today = todayCal.getTime();
			int todayYear = todayCal.get(Calendar.YEAR);
			
			Calendar birthCal = Calendar.getInstance(Locale.KOREA);
			birthCal.setTime(birthday);
			int birthYear = birthCal.get(Calendar.YEAR);
			
			int age = todayYear - birthYear;
			
			//월일 비교하기위해 년도 맞춰줌
			birthCal.add(Calendar.YEAR, age);
			Date birthday2 = birthCal.getTime();
			//생일 안지남
			if( birthday2.after(today) ){
				age--;
			}
			
			if( age <= 0 ){
				return 0;
			}
			return age;
			
		} catch (ParseException e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	/**
	 * 파일로그를 생성하고 로그를 기록한다.(주목적은 디버깅 용이고, 간단하게 파일로 로그남기는 용도로 사용할것.)
	 * <pre>
	 * - 일별 또는 월별로파일 나뉘도록 할때는 파일명에 날짜는 포함시키지 말것. 
	 * - rollingFileInterval 파라메터로 "day" 또는 "month"를 줄수 있다. 이외의 값은 무시함.
	 * - c:/~~/testlog.log까지만 넣어도 날짜는 자동으로 끝에 붙는다.
	 * - 로그의 기본 포맷은 '[yyyy-MM-dd HH:mm:ss] {message} \n' 형태임. 
	 * </pre>
	 * @param fileAbsolutePath 파일명을 포함한 정래경로
	 * @param message
	 * @param rollingFileInterval 파일분할 주기 "day", "month", 나머지 값인 경우는 파일 하나에 그냥 씀
	 */
	public static void writeSimpleFileLog(String fileAbsolutePath, String message, String rollingFileInterval){
		FileWriter fileWriter = null;
		try {
			Date date = new Date();
			if( "day".equals(rollingFileInterval) ){
				String day = new SimpleDateFormat("yyyy-MM-dd").format(date);
				fileWriter = new FileWriter(new File(fileAbsolutePath + "." + day), true);
			} else if( "month".equals(rollingFileInterval) ){
				String month = new SimpleDateFormat("yyyy-MM").format(date);
				fileWriter = new FileWriter(new File(fileAbsolutePath + "." + month), true);
			} else {
				fileWriter = new FileWriter(new File(fileAbsolutePath), true);
			}
			fileWriter.write("[" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date) + "] ");
			fileWriter.write(message);
			fileWriter.write(System.getProperty("line.separator"));
			fileWriter.flush();
			fileWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 디렉토리에서 특정문자열로 시작하는 파일1개 조회
	 * @param directory
	 * @param prefix
	 * @return
	 */
	public static File getPrefixedFile(String directory, String prefix){
		File dir = new File(directory);
		//폴더자체가 없다
		if( !dir.exists() ){
			return null;
		}
		List<File> files = (List<File>) FileUtils.listFiles(dir, FileFilterUtils.prefixFileFilter(prefix), null);
		if( files != null && files.size() > 0 ){
			if( files.size() == 1 ){
				return files.get(0);
			} else {
				// 원래 무조건 하나만 있어야 하는데 하나 이상 있으면 제일 최신거 반환
				Collections.sort(files, new Comparator<File>() {
					@Override
					public int compare(File o1, File o2) {
						if( o1 != null && o2 != null){
							if( o1.lastModified() < o2.lastModified() ){
								return -1;
							} else {
								return 1;
							}
						} else {
							return -1;
						}
					}
				});
				
				return files.get(0);
			}
		} else {
			// nothing
			return null;
		}
	}
	
	/**
	 * 디렉토리에서 특정문자열로 시작하는 파일 목록 조회
	 * @param directory
	 * @param prefix
	 * @return
	 */
	public static List<File> getPrefixedFiles(String directory, String prefix){
		File dir = new File(directory);
		//폴더자체가 없다
		if( !dir.exists() ){
			return null;
		}
		return (List<File>) FileUtils.listFiles(dir, FileFilterUtils.prefixFileFilter(prefix), null);
	}
	
	/**
	 * 주민등록번호 정규식
	 */
	//private static final String JUMIN_NUM_PATTERN = "(?m)^\\s*.*\\d{2}(0[1-9]|1[0-2])(0[1-9]|[12][0-9]|3[01])[-,\\s]?[012349]\\d{6}.*\\s*$";
	//private static final String JUMIN_NUM_PATTERN = "^\\s*(.*)\\d{2}(0[1-9]|1[0-2])(0[1-9]|[12][0-9]|3[01])[-,\\s]?[012349]\\d{6}(.*)\\s*$";
	private static final String JUMIN_NUM_PATTERN = "^.*\\d{2}(0[1-9]|1[0-2])(0[1-9]|[12][0-9]|3[01])[-,\\s]?[012349]\\d{6}.*$";

	/**
	 * 문자열 안에 주민번호 패턴 포함 체크를 한다.
	 * @param string
	 * @return boolean
	 */
	public static boolean isIncludeJuminNum(String string){
		Pattern p = Pattern.compile(JUMIN_NUM_PATTERN, Pattern.MULTILINE);
		Matcher m = p.matcher(string);
		
		boolean isFound = false;
		while (m.find()) {
			//System.out.println("matching="+m.group());
			isFound = true;
		}
	    
		/*
		if (!isFound) {
			System.out.println("No pattern found");
		}*/
		//return m.matches();
		return isFound;
	}

	/**
	 * request로 전달된 모든 parameter들의 주민번호패턴 체크를 한다.
	 * @param request
	 * @return
	 */
	//@SuppressWarnings("rawtypes")
	public static boolean isIncludeJuminNumInParameter(HttpServletRequest request){
		boolean inJuminNum = false;
		Enumeration<String> ePN = request.getParameterNames();
		while(ePN.hasMoreElements()){
			/*try{*/
				String name = ePN.nextElement().toString();
				//System.out.println(name + " => " + request.getParameter(name));
				
				//ajax get 요청시 파라미터  ex)&_=1501034799786 - 요청한 페이지가 브라우저에 의해 캐시되지 않도록 자동생성되어 넘어오므로 예외처리
				if(name.equals("_")){
					continue;
				}
				
				String value = request.getParameter(name);
				if (StringUtils.isBlank(value)) {
					continue;
				}

				//주민번호 패턴 포함하고있으면
				if(isIncludeJuminNum(value) ){
					return true;
				}
				
			/*}catch (Exception e) {
				System.out.println(ePN.nextElement().toString() + "=> " + "[EXCEPTION OCCURED]");
			}*/
		}
		
		return inJuminNum;
	}
	
	//휴대폰번호 정규식
	private static final String MOBILE2 = "^([0-9][0-9][0-9]|[0-9][0-9][0-9][0-9])$";
	private static final String MOBILE3 = "^([0-9][0-9][0-9][0-9])$";
	
	/**
	 * 후대폰번호 중간자리와 끝자리 숫자3자리, 숫자4자리 인지 확인
	 * @param string
	 * @param location("MOBILE2" or "MOBILE3")
	 * @return
	 */
	public static boolean isMobile(String string, String location) {
		if("MOBILE2".equalsIgnoreCase(location) ){
			Pattern p = Pattern.compile(MOBILE2);
			return p.matcher(string).matches();
		}else if("MOBILE3".equalsIgnoreCase(location)){
			Pattern p = Pattern.compile(MOBILE3);
			return p.matcher(string).matches();
		}
		return false;
	}
	
	 /**
	  * 숫자에 천단위마다 콤마 넣기
	  * @param int
	  * @return String
	  * */
	 public static String toNumFormat(int num) {
	  DecimalFormat df = new DecimalFormat("#,###");
	  return df.format(num);
	 }
	 
	 /**
	  * URL로 스트리밍하여 텍스트를 반환한다.
	  * @param url
	  * @return 결과텍스트
	  */
	 public static String UrlToString(URL url){
		 BufferedInputStream reader = null;
		 StringBuffer buffer = new StringBuffer();
		 try {
			reader = new BufferedInputStream(url.openStream());
			int i;
			byte[] b = new byte[4096];
			while( (i = reader.read(b)) != -1){
				buffer.append(new String(b, 0, i));
			}
			
		} catch (IOException e) {
			LOGGER.error("[" + e.getClass() +"] BufferedInputStream read fail : " + e.getMessage());
		}finally {
	        if (reader != null){
	        	try {
					reader.close();
				} catch (IOException e) {
					LOGGER.error("[" + e.getClass() +"] BufferedInputStream close fail : " + e.getMessage());
				}
	        }
	    }
		 return buffer.toString();
	 }
	 
	 
	 /**
	  * URL 및 URLConnection을  스트리밍하여 텍스트를 반환한다.
	  * @param url
	  * @param HttpURLConnection con
	  * @return 결과텍스트
	  */
	 public static String UrlToString(URL url, HttpURLConnection con){
		 BufferedReader reader = null;
		 StringBuffer buffer = new StringBuffer();
		 try {
			reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String i;
			while( (i = reader.readLine()) != null){
				buffer.append(i);
			}
			
		} catch (IOException e) {
			LOGGER.error("[" + e.getClass() +"] BufferedReader read fail : " + e.getMessage());
		}finally {
	        if (reader != null){
	        	try {
	        		con.disconnect();
					reader.close();
				} catch (IOException e) {
					LOGGER.error("[" + e.getClass() +"] BufferedReader close fail : " + e.getMessage());
				}
	        }
	    }
		 return buffer.toString();
	 }
	 /**
	  * 서버의 아이피를 반환한다.
	  * - os에 상관없이 서버아이피 추출가능
	  * @return 서버아이피 
	  */
	 public static String getServerAddress(){
		String serverIp = "";
		try {
			serverIp = HostNameUtil.getLocalHostAddress();
		} catch (UnknownHostException e) {
			LOGGER.error("[UnknownHostException] Try/Catch... : "+ e.getMessage());
		}
		return serverIp;
	 }
	 
	 /**
	  * 넘겨받은 날짜의 그 주 일요일 날짜 스트링을 반환한다.
	  * @param date
	  * @return 일요일 날짜 텍스트
	  */
	 public static String getSundayByDate(Date date){
		 if( date == null ){
			return null;
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.DATE, cal.get(Calendar.DATE) - cal.get(Calendar.DAY_OF_WEEK) + 1);
		
		return new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
	 }
		 
	 /**
	  * 넘겨받은 날짜의 그 주 일요일 날짜를 반환한다.
	  * @param date
	  * @return 일요일 날짜 (Date)
	  */
	 public static Date getSundayByDate1(Date date){
		 if( date == null ){
			 return null;
		 }
		 Calendar cal = Calendar.getInstance();
		 cal.setTime(date);
		 cal.set(Calendar.DATE, cal.get(Calendar.DATE) - cal.get(Calendar.DAY_OF_WEEK) + 1);
		 
		 return cal.getTime();
	 }
	 
	 /**
	  * 넘겨받은 날짜의 그 주 토요일 날짜 스트링을 반환한다.
	  * @param date
	  * @return 일요일 날짜 텍스트
	  */
	 public static String getSaturdayByDate(Date date){
		 if( date == null ){
			 return null;
		 }
		 Calendar cal = Calendar.getInstance();
		 cal.setTime(date);
		 cal.set(Calendar.DATE, cal.get(Calendar.DATE) - cal.get(Calendar.DAY_OF_WEEK) + 7);
		 
		 return new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
	 }
	 
	 /**
	  * 넘겨받은 날짜의 그 주 토요일 날짜를 반환한다.
	  * @param date
	  * @return 토요일 날짜 (Date)
	  */
	 public static Date getSaturdayByDate1(Date date){
		 if( date == null ){
			 return null;
		 }
		 Calendar cal = Calendar.getInstance();
		 cal.setTime(date);
		 cal.set(Calendar.DATE, cal.get(Calendar.DATE) - cal.get(Calendar.DAY_OF_WEEK) + 7);
		 
		 return cal.getTime();
	 }
	 
	 /**
	  * 넘겨받은 날짜의 년도별 주차를 반환한다.
	  * @param date
	  * @return 년도별 주차
	  */
	 public static int getWeekOfYearIso(Date date){
		 if( date == null ){
			 return 0;
		 }
		 Calendar cal = Calendar.getInstance();
		 cal.setTime(date);
		 
		 
		 return cal.get(Calendar.WEEK_OF_YEAR);
	 }
	 
	 /**
	  * 넘겨받은 날짜의 월별 주차를 반환한다.
	  * @param date
	  * @return 월별 주차
	  */
	 public static int getWeekOfMonth(Date date){
		 if( date == null ){
			 return 0;
		 }
		 Calendar cal = Calendar.getInstance();
		 cal.setTime(date);
		 
		 
		 return cal.get(Calendar.WEEK_OF_MONTH);
	 }
	 
	 /**
	 * 코드값 출력
	 * @param codeId
	 * @param codeList
	 * @return
	 */
	public static String codeName(String codeId, List<Code> codeList){
		if( StringUtils.isNotBlank(codeId) && codeList != null){
			for( Code code : codeList ){
				if( codeId.equals(code.getCodeId()) ){
					return code.getCodeName();
				}
			}
		}
		return codeId;
	}
	
	/**
	  * html의 특수문자를 표현하기 위해
	  *
	  * @param srcString
	  * @return String
	  * @exception Exception
	  * @see
	  */
	public static String getHtmlStrCnvr(String srcString) {
		if(StringUtils.isBlank(srcString) ){
			return "";
		}
		
		String tmpString = srcString;

		tmpString = tmpString.replaceAll("& lt;", "<");
		tmpString = tmpString.replaceAll("& gt;", ">");
		tmpString = tmpString.replaceAll("& amp;", "&");
		tmpString = tmpString.replaceAll("& nbsp;", " ");
		tmpString = tmpString.replaceAll("& apos;", "\'");
		tmpString = tmpString.replaceAll("& quot;", "\"");

		return tmpString;

	}
	 
	 /**
	  * 파일경로를 받아 해당 디렉토리내의 jsp파일의 파일명 목록을 반환한다.
	  * @param request
	  * @param fileRoot
	  * @return 파일명 목록
	  * @throws FileNotFoundException
	  */
	 public static List<String> getJspFileList(HttpServletRequest request, String dirRoot) throws FileNotFoundException {
		String webRoot = AsaproUtils.getWebRoot(request);
			File fileDir = new File(webRoot + dirRoot );

			File[] files = fileDir.listFiles(new FileFilter() {
				@Override
				public boolean accept(File file) {
					return file.isFile() && (file.getName().endsWith(".jsp") || file.getName().endsWith(".htm") || file.getName().endsWith(".html"));
				}
			});
			
			List<String> fileNameList = new ArrayList<String>();
			if(ArrayUtils.isNotEmpty(files) ){
				for( File f : files ){
					fileNameList.add(f.getName());
				}
			}
			
			Collections.sort(fileNameList, new Comparator<String>() {
				@Override
				public int compare(String s1, String s2) {
					if(s1 != null && s2 != null){
						if( "default".equals(s1) ){
							return -1;
						} else {
							return 1; 
						}
					} else {
						return -1;
					}
				}
			});
			
			return fileNameList;
		}		
	 
	 /**
	  * 문자열에서 특정문자의 개수를 반환한다.
	  * @param containText
	  * @param searchText
	  * @return 특정문자 개수
	  * @throws FileNotFoundException
	  */
	 public static int getCountMatches(String containText, String searchText) throws FileNotFoundException {
		 int count = 0;
		 count = StringUtils.countMatches(containText, searchText);
		 return count;
	 }
}
