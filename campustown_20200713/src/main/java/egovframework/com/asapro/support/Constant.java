/**
 * 
 */
package egovframework.com.asapro.support;

import java.text.SimpleDateFormat;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.support.RequestContext;

import egovframework.com.cmm.service.EgovProperties;

/**
 * 설정 상수
 * 
 * @author yckim
 * @since 2018. 3. 28.
 * 
 */
public class Constant {
	
	// ------------------------------------
	// 메인 사이트 테이블 접두어 - 바뀌지도 않을꺼..쿼리하는니 걍 박아 놓는게 나을듯
	// ------------------------------------
	/**
	 * 메인사이트 테이블 프리픽스
	 */
	public static final String MAIN_SITE_PREFIX = "ASA";
	/**
	 * 메인사이트 아이디
	 */
	public static final String MAIN_SITE_ID = "kor";
	public static final String MAIN_SITE_DISPLAY_ID = "main";
	
	// ------------------------------------
	// path 관련
	// ------------------------------------


	/**
	 * 사용자 기본 패스. 수정시 web.xml, dispatcher-servlet.xml 도 동일한 값으로 수정해주어야 함
	 */
	public static final String APP_PATH = "/site";
	public static final String SITE_ID_PATH = "/{siteId}";
	/**
	 * 서버 Context Path 설정시 CONTEXT PATH 적용
	 */
	static HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
	public static final String CONTEXT_PATH = request.getContextPath();
	//public static final String CONTEXT_PATH = EgovProperties.getProperty("Globals.contextPath").trim();
	//public static final String CONTEXT_PATH = "/ggyouth";
	
	/**
	 * 관리자 기본 패스. 이 값을 바꾸면 관리자 경로가 바뀐다. 
	 * 이 변수와 /WEB-INF/config/egovframework/springmvc/egov-com-interceptor.xml 에서
	 * [mvc:mapping path="/x/admin/x.do" /] path값도 함께 변경해 주어야 함.
	 */
	//public static final String ADMIN_PATH = EgovProperties.getProperty("Globals.adminPath").trim();
	//글로벌 프로퍼티에서 설정한 adminPath를 사용하려 했으나 컨트롤러의 RequestMapping 의 value 는 상수만 올 수 있어서 받아서 쓸 수없다.
	//***  부편하지만 globals.properties의 adminPath 와 Contnant ADMIN_PATH의 값 둘다 변경해야만 관리자 url을 일괄 변경 할 수 있다.  ***
	public static final String ADMIN_PATH= "/asacms";
	
	public static final String API_PATH = "/api";
	
	public static final String FEED_PATH = "/feed";

	public static final String UPLOAD_PATH = "/uploadfile";
	
	public static final String FILE_PATH = "/file";
	
	// ------------------------------------
	// 보안토큰 관련
	// ------------------------------------

	/**
	 * 보안 토큰 파라메터이름
	 */
	public static final String SECURITY_TOKEN_NAME = "csrf_token";
	public static final String SECURITY_ASYNC_TOKEN_NAME = "X-ASAPRO-SECURITY-TOKEN";//ajax 요청시 헤더에 토큰 넣을때 키
	public static final String SECURITY_ASYNC_COOKIE_NAME = "a_csrf_token";

	/**
	 * 보안토큰 유효시간 12시간
	 */
	public static final int SECURITY_TOKEN_LIFE = 60 * 60 * 12 * 1000;

	/**
	 * SecurityToken secret salt
	 */
	public static final String SECURITY_TOKEN_SALT = "asadal!@#$";

	/**
	 * 보안토큰 체크 제외 uri들 #1
	 */
	public static final String[] SECURITY_EXCLUDE_STARTWITH_URIS = new String[] { 
		"/index.jsp" 
		, "/asset/"		/* 정적자원은 웹서버에서 처리하겠지만 혹시 모르니 추가해둠 */
		, "/design/"	/* 정적자원은 웹서버에서 처리하겠지만 혹시 모르니 추가해둠 */
		, "/images/"	/* 정적자원은 웹서버에서 처리하겠지만 혹시 모르니 추가해둠 */
		, "/css/"		/* 정적자원은 웹서버에서 처리하겠지만 혹시 모르니 추가해둠 */
		, "/js/"		/* 정적자원은 웹서버에서 처리하겠지만 혹시 모르니 추가해둠 */
		, UPLOAD_PATH + "/"
		, APP_PATH + "/comment"//댓글 APP_PATH + /comment 제외 추가 
		, APP_PATH + "/sns"//SNS APP_PATH + /sns 제외 추가 
		, APP_PATH + FEED_PATH//피드
		//, ADMIN_PATH
		, APP_PATH + "/main/file/thumbnail/"
	};

	/**
	 * 보안토큰 체크 제외 uri들 #2
	 */
	public static final String[] SECURITY_EXCLUDE_ENDWITH_URIS = new String[] { 
		"/password" 
		, "/SSOLogin" 
		, "/SSOLoginProc" 
//		, "login" 
//		, "Login" 
	};
	
	/**
	 * 보안토큰 체크 제외 uri들 #3
	 */
	public static final String[] SECURITY_EXCLUDE_CONTAINWITH_URIS = new String[] { 
			"/SSOLogin" 
			, "/SSOLoginProc" 
//			"login" 
//			, "Login" 
	};

	/**
	 * 댓글 작성자 기본 이미지 - 바꿀일 없을거 같으니 걍 박음...
	 */
	public static final String COMMENT_DEFAULT_PROFILE = CONTEXT_PATH + "/design/common/images/comment/anonymous.jpg";
	
	/**
	 * 1메가(바이트환산)
	 */
	public static final long MEGA = 1048576L;
	
	/**
	 * 텍스트 에어리어 엔터 구분자
	 */
	public static final String TEXTAREA_ENTER = "\\r?\\n";
	
	/**
	 * 시각표현
	 */
	public static final SimpleDateFormat YYYYMMDDHHMMSS = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.KOREA);
	
	/**
	 * 테마위치
	 */
	public static final String THEME_ROOT = "/WEB-INF/jsp/egovframework/com/asapro/theme/";
	
	/**
	 * admin위치
	 */
	public static final String ADMIN_ROOT = "/WEB-INF/jsp/egovframework/com/asapro/admin/";
	
	/**
	 * 페이징 기본 페이지 사이즈
	 */
	public static final int PAGE_SIZE = 10;

	/**
	 * 이미지 업로드 가능 확장자
	 */
	public static final String UPLOAD_IMAGE_WHITE_LIST = "jpg,jpeg,png,gif,bmp";
	
	/**
	 * 동영상 업로드 가능 확장자
	 */
	public static final String UPLOAD_VIDEO_WHITE_LIST = "wmv,mp4,flv,ogv";
	
	/**
	 * 음성파일 업로드 가능 확장자
	 */
	public static final String UPLOAD_AUDIO_WHITE_LIST = "ogg,mp3,m4a,wav,wma";
	
	/**
	 * 사업자등록증 업로드 가능 확장자
	 */
	public static final String UPLOAD_BUSINESS_REGISTRATION_WHITE_LIST = "hwp,doc,docx,txt,rtf,ppt,pptx,xls,xlsx,pdf,jpg,jpeg,gif,png,bmp";
}
