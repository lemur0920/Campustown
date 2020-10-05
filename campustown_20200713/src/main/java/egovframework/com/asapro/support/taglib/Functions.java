/**
 * 
 */
package egovframework.com.asapro.support.taglib;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.jsp.PageContext;

import org.apache.commons.text.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationContext;

import egovframework.com.asapro.code.service.Code;
import egovframework.com.asapro.support.SEO;
import egovframework.com.asapro.member.admin.service.AdminMember;
import egovframework.com.asapro.organization.service.Department;
import egovframework.com.asapro.role.service.RoleService;
import egovframework.com.asapro.site.service.Site;
import egovframework.com.asapro.support.ApplicationContextProvider;
import egovframework.com.asapro.support.crypto.CryptoWorker;
import egovframework.com.asapro.support.util.AsaproUtils;

/**
 * JSTL asapro 커스텀 함수 
 * <pre>
 * - jsp 에서 taglib asapro 선언 후 asapro:함수명 으로 사용한다.
 * - 함수 추가시 /WEB-INF/tlds/asapro.tld 파일에 함수 선언을 추가해야 함
 * </pre>
 * @author yckim
 *
 */
public class Functions {
	private static final Logger LOGGER = LoggerFactory.getLogger(Functions.class);
	
	/**
	 * 줄바꿈 처리 - php의 nl2br
	 * @param value
	 * @param escapeHtml
	 * @return
	 */
	public static String nl2br(String value, boolean escapeHtml){
		if( StringUtils.isNotBlank(value) ){
			if( escapeHtml ){
				value = StringEscapeUtils.escapeHtml4(value); 
			}
			return AsaproUtils.nl2br(value);
		} else {
			return "";
		}
	}
	
	/**
	 * 텍스트를 태그로 치환
	 * @param value
	 * @param escapeHtml
	 * @return
	 */
	public static String pattern2tag(String value, boolean escapeHtml){
		if( StringUtils.isNotBlank(value) ){
			if( escapeHtml ){
				value = StringEscapeUtils.escapeHtml4(value); 
			}
			return AsaproUtils.pattern2tag(value);
		} else {
			return "";
		}
	}
	
	/**
	 * 문자열을 축약한다.
	 * @param string
	 * @param length
	 * @return 축약된 문자열
	 */
	public static String abbreviate(String string, int length){
		if( StringUtils.isBlank(string) ){
			return "";
		} else {
			if( length <= 4 ){
				return string;
			} else {
				String temp = null;
				temp = StringUtils.abbreviate(string, length);
				return temp;
			}
		}
	}
	
	/**
	 * 문자열을 java.net.URLEncoder 로 인코딩한다.
	 * @param src
	 * @param encoding
	 * @return 인코딩된 문자열
	 */
	public static String urlEncode(String src, String encoding){
		try {
			return URLEncoder.encode(src, encoding);
		} catch (UnsupportedEncodingException e) {
			LOGGER.error("[UnsupportedEncodingException] Try/Catch... : "+ e.getMessage());
			return src;
		}
	}
	
	/**
	 * 문자열을 java.net.URLDecoder 로 인코딩한다.
	 * @param src
	 * @param encoding
	 * @return 디코딩된 문자열
	 */
	public static String urlDecode(String src, String encoding){
		try {
			return URLDecoder.decode(src, encoding);
		} catch (UnsupportedEncodingException e) {
			LOGGER.error("[UnsupportedEncodingException] Try/Catch... : "+ e.getMessage());
			return src;
		}
	}
	
	/**
	 * 암호화 문자열을 반환한다.
	 * @param str
	 * @return
	 */
	public static String encrypt(String str){
		ApplicationContext ctx = ApplicationContextProvider.getApplicationContext();
		CryptoWorker cryptoWorker = ctx.getBean(CryptoWorker.class);
		return cryptoWorker.encrypt(str);
	}
	
	/**
	 * 복호화문자열을 반환한다.
	 * @param str
	 * @return
	 */
	public static String decrypt(String str){
		ApplicationContext ctx = ApplicationContextProvider.getApplicationContext();
		CryptoWorker cryptoWorker = ctx.getBean(CryptoWorker.class);
		return cryptoWorker.decrypt(str);
	}
	
	/**
	 * 관리자 메뉴링크를 현재 사용자의 권한에 맞게 출력한다.
	 * @param currentSite
	 * @param currentAdmin
	 * @param adminMenuName
	 * @param adminMenuLink
	 * @param method
	 * @param className
	 * @return
	 */
	public static String printAdminMenuLink(Site currentSite, AdminMember currentAdmin, String adminMenuName, String adminMenuLink, String method, String className, String depth, String iconClass){
		
		ApplicationContext applicationContext = ApplicationContextProvider.getApplicationContext();
		RoleService roleService = applicationContext.getBean(RoleService.class);
		
		adminMenuLink = AsaproUtils.getAdminPath(currentSite) + adminMenuLink;
		String link = "";
		if( currentSite != null && currentAdmin != null ){
			String[] temp = adminMenuLink.split("\\?");
			Map<String, String[]> paramMap = null;
			if(temp.length > 1){
				String qs = temp[1];
				paramMap = AsaproUtils.parseQueryString(qs);
			}
			if( roleService.isCurrentAdminAccessibleResource(currentAdmin, temp[0], paramMap, method) ){
				if("1".equals(depth) ){	//1뎁스만 있는 메뉴인경우
					link = "<li><a href=\"" + adminMenuLink + "\" class=\""+ className + "\"><i class=\"" + iconClass + "\"></i><span>" + adminMenuName + "</span></a></li>";
				}else{
					link = "<li><a href=\"" + adminMenuLink + "\">" + adminMenuName + "</a></li>";
				}
			}
		}
		return link;
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
	 * 코드값 En 출력
	 * @param codeId
	 * @param codeList
	 * @return
	 */
	public static String codeNameEn(String codeId, List<Code> codeList){
		if( StringUtils.isNotBlank(codeId) && codeList != null){
			for( Code code : codeList ){
				if( codeId.equals(code.getCodeId()) ){
					return code.getCodeNameEn();
				}
			}
		}
		return codeId;
	}
	
	/**
	 * 부서코드값 Name 출력
	 * @param depId
	 * @param depList
	 * @return
	 */
	public static String depName(String depId, List<Department> depList){
		if( StringUtils.isNotBlank(depId) && depList != null){
			for( Department dep : depList ){
				if( depId.equals(dep.getDepId()) ){
					return dep.getDepName();
				}
			}
		}
		return depId;
	}
	
	
	/**
	 * 코드값 코드설명 출력
	 * @param codeId
	 * @param codeList
	 * @return
	 */
	public static String codeDescription(String codeId, List<Code> codeList){
		if( StringUtils.isNotBlank(codeId) && codeList != null){
			for( Code code : codeList ){
				if( codeId.equals(code.getCodeId()) ){
					return code.getCodeDescription();
				}
			}
		}
		return codeId;
	}
	
	/**
	 * 하위구분 코드 출력
	 * @param codeId
	 * @param pgCatCode
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static String catSubCodeName(String pgCatCode, String prefix, String codeId, String suffix, PageContext pageContext){
		
		List<Code> codeList = (List<Code>) pageContext.getRequest().getAttribute(prefix + pgCatCode + suffix);
		
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
	 * 날짜 문자열을 다시 파싱해서 다른 포맷으로 반환한다.
	 * @param dateString
	 * @param parseFormat
	 * @param newFormat
	 * @return
	 */
	public static String formatStringDate(String dateString, String parseFormat, String newFormat){
		
		if( StringUtils.isBlank(dateString) || StringUtils.isBlank(parseFormat) || StringUtils.isBlank(newFormat) ){
			return dateString;
		}
		
		SimpleDateFormat parseFmt = new SimpleDateFormat(parseFormat);
		
		try {
			Date date = parseFmt.parse(dateString);
			return new SimpleDateFormat(newFormat).format(date);
		} catch (ParseException e) {
			LOGGER.error("[ParseException] Try/Catch... : "+ e.getMessage());
		}
		
		return dateString;
	}
	
	/**
	 * seo 인스턴스로부터 메뉴로케이션을 생성해서 출력한다.
	 * @param seo
	 * @param divider 메뉴 단계간 구분 문자 ex) &gt;
	 * @param cssClass 로케이션 ul 에 추가할 css class이름 복수일 경우 공백으로 구분 ex) "class1 class2"
	 * @return
	 */
	
	public static String seoLocation(SEO seo, String divider, String cssClass){
		if( seo == null ){
			return "";
		}
		return seo.getLocation(divider, cssClass);
	}
	
	/**
	 * 이름의 중간자를 마스킹 처리한다.
	 * @param name
	 * @return 마스킹처리된 이름
	 */
	public static String maskingNameCenter(String name){
		if(StringUtils.isBlank(name) ){
			return name;
		}
		
		int end = name.length() - 2;
		String firstName = name.substring(0, 1);
		String lastName = "";
		String maskStr = "";

		if(name.length() <= 2 ){
			lastName = "*";
		}else{
			lastName = name.substring(name.length() - 1, name.length() );
		}
		
		for(int i = 1; i <= end; i++){
			maskStr += "*";
		}
		
		return firstName + maskStr + lastName;
	}
	
	/**
	 * 이름의 성을 재외한 나머지 이름을 마스킹 처리한다.
	 * @param name
	 * @return 마스킹처리된 이름
	 */
	public static String maskingNameRight(String name){
		if(StringUtils.isBlank(name) ){
			return name;
		}
		
		int end = name.length() - 1;
		String firstName = name.substring(0, 1);
		String maskStr = "";
		for(int i = 1; i <= end; i++){
			maskStr += "*";
		}
		
		return firstName + maskStr;
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
