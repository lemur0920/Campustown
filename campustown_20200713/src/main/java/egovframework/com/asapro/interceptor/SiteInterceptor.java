/**
 * 
 */
package egovframework.com.asapro.interceptor;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.LocaleUtils;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import egovframework.com.asapro.config.service.Config;
import egovframework.com.asapro.config.service.ConfigService;
import egovframework.com.asapro.member.admin.service.AdminMember;
import egovframework.com.asapro.member.admin.service.AdminSiteRoleRel;
import egovframework.com.asapro.role.service.Role;
//import egovframework.com.asapro.index.service.IndexService;
//import egovframework.com.asapro.mail.service.impl.MailServiceImpl;
//import egovframework.com.asapro.member.service.MemberService;
import egovframework.com.asapro.site.service.Site;
import egovframework.com.asapro.site.service.SiteService;
import egovframework.com.asapro.support.crypto.GeneralCryptoWorker;
import egovframework.com.asapro.support.util.AsaproUtils;
import egovframework.com.asapro.support.util.EtcUtil;
import eu.bitwalker.useragentutils.DeviceType;
import eu.bitwalker.useragentutils.UserAgent;

/**
 * 사이트 인터셉터
 * <pre>
 * 1. 인터셉터들 중에서 최초로 실행된다.
 * 2. Config 정보가 최초로 세팅된다.
 * 3. siteConfig 의 force_redirect 설정 체크해서 적용및 처리한다.
 * </pre>
 * @author yckim
 *
 */
public class SiteInterceptor extends HandlerInterceptorAdapter{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(SiteInterceptor.class);
	
//	@Autowired
//	private IndexService indexService;
	
	@Autowired
	private SiteService siteService;
	
	@Autowired
	private ConfigService configService;
	
//	@Autowired
//	private MemberService memberService;
	
	/* (non-Javadoc)
	 * @see org.springframework.web.servlet.handler.HandlerInterceptorAdapter#preHandle(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object)
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

		String requestUri = AsaproUtils.getFixedRequestUri(request);
		LOGGER.info("[ASAPRO] SiteInterceptor uri: {}", requestUri);
		
		//========== 시작 : 현재사이트 ==========
		Site currentSite = siteService.detectCurrentSite(request);

		LOGGER.info("[ASAPRO] SiteInterceptor uri: {}, current site : {}", requestUri, currentSite.getSiteName());
		/*
		    톰캣이 한글로된 주소 처리못할때 
		    server.xml 에서 ajp13 Connector 태그에 URIEncoding="UTF8" 추가
			그래도 한글주소 처리못하면 아파치 연동되어 있을 경우
			JkOptions +ForwardURICompatUnparsed 가 아니라
			JkOptions +ForwardURICompat 로 되어 있는지 확인
		 */
		List<Site> allSiteList = siteService.getSiteList(null);
		List<Site> authSiteList = new ArrayList<Site>();
		
		//로그인한 관리자가 권한이 있는 사이트만 접속할 수 있도록 권한 있는 사이트 목록만 담는다.
		//관리자페이지 상단 사이트목록에 사용
		AdminMember currentAdmin = (AdminMember) request.getSession().getAttribute("currentAdmin");
		
		if(currentAdmin != null && !"admin".equals(currentAdmin.getAdminId()) ){
			
			//관리자가 가지고 있는 권한의 사이트 목록
			for (Site site : allSiteList) {
				for (AdminSiteRoleRel rel : currentAdmin.getAdminSiteRoleRelList()) {
					if(site.getSitePrefix().equals(rel.getSitePrefix()) ){
						authSiteList.add(site);
					}
				}
			}
			request.setAttribute("authSiteList", authSiteList);
			
		} else {
			request.setAttribute("authSiteList", allSiteList);
		}
		
		
		request.setAttribute("currentSite", currentSite);
		//========== 끝 : 현재사이트 ==========
		
		//========== 시작 : 브라우저 캐싱 방지 ==========
		//브라우저 html 페이지 캐싱 금지 - 로그아웃하고 뒤로가기 눌렀을 상황에 개인정보 노출안되게 등등
		response.setHeader( "Pragma", "no-cache" );
		response.setHeader( "Cache-Control", "no-cache, no-store, must-revalidate" );
		response.setDateHeader( "Expires", -1 );
		//========== 끝 : 브라우저 캐싱 방지 ==========

		//========== 시작 : 접속국가 한국 아니면 리다이렉트 ============================
		if( !requestUri.endsWith(".do") && !requestUri.startsWith(AsaproUtils.getAppPath(currentSite) + "/file/") ){
			Cookie langManualCookie = AsaproUtils.getCookie(request.getCookies(), "lang");
			if( "manual".equals(request.getParameter("lang")) ){
				langManualCookie = new Cookie("lang", "manual");
				langManualCookie.setPath("/");
				langManualCookie.setHttpOnly(true);
				response.addCookie(langManualCookie);
			}
			
			//언어수동 쿠키 없으면 자동으로 처리한다.
			if( langManualCookie == null ){
				/* ===> 상황에 맞게 커스터마이징 해줘야 함... 
				//로케일에서 국가 구함..-> 리퀘스트헤더 Accept-Language 파싱일걸.-_-?
				String acceptLanguage = request.getLocale().getLanguage();
				
				if( acceptLanguage.toLowerCase().contains("ko") ){
					if( siteList != null ){
						for( Site site : siteList ){
							if( !currentSite.getSiteId().equals(site.getSiteId()) ){
								response.sendRedirect("http://" + site.getSeperatedSiteDomains().get(0));
								return false;
							}
						}
					}
				} else {
					if( siteList != null ){
						for( Site site : siteList ){
							//영문버전주소가진 사이트
							if( "en_US".equals(site.getSiteLocale()) ){
								if( !currentSite.getSiteId().equals(site.getSiteId()) ){
									response.sendRedirect("http://" + site.getSeperatedSiteDomains().get(0));
									return false;
								}
							}
						}
					}
				}
				*/
			}
		}
		//========== 끝 : 접속국가 한국 아니면 리다이렉트 ============================
		
		//========== 시작 : 사이트 언어 ==========
		//사이트 locale ex) ko_KR, en_US 등등
		if( !requestUri.startsWith(AsaproUtils.getAdminPath(currentSite)) ){
			Locale locale = LocaleUtils.toLocale(currentSite.getSiteLocale());
			request.getSession().setAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME, locale);
		}
		//========== 끝 : 사이트 언어 ==========
		
		//========== 시작 : 사이트 옵션 ==========
		if( "true".equals(request.getParameter("flush")) ){
			//?flush=true 파라메터 달고 들어오면 옵션캐시를 비운다. - DB에서 옵션값을 직접 수정했을 경우 해당.
			//configService.refreshCacheAll();
//			indexService.flush();
		}
		
		Config globalConfig = configService.getConfig("global");
		request.setAttribute("globalConfig", globalConfig);
		request.setAttribute("globalConfigMap", globalConfig.getOptionMap());//==> HashMap jsp 단에서 꺼내쓸려고..
		//암호화 기능 적용 여부 - 별도의DB 암호화 솔루션을 사용하는 경우 false로 세팅되어야 한다.
		//(중요) false로 세팅하기 전에 DB의 값은 복호화된 값으로 다 돌려놔야 함.
		if( StringUtils.isNotBlank(globalConfig.getOption("use_db_encryption")) ){
			GeneralCryptoWorker.USE_DB_ENCRYPTION = Boolean.valueOf(globalConfig.getOption("use_db_encryption"));
		}
		
		Config siteConfig = configService.getConfig("site");
		request.setAttribute("siteConfig", siteConfig);
		//siteConfig.updateOption("site_email", memberService.getAdminEmail());//사이트 이메일 옵션값 업데이트
		request.setAttribute("siteConfigMap", siteConfig.getOptionMap());//==> HashMap jsp 단에서 꺼내쓸려고..
		
//		Config snsConfig = configService.getConfig("sns");
//		request.setAttribute("snsConfig", snsConfig);
//		request.setAttribute("snsConfigMap", snsConfig.getOptionMap());//==> HashMap jsp 단에서 꺼내쓸려고..
		
//		Config commentConfig = configService.getConfig("comment");
//		request.setAttribute("commentConfig", commentConfig);
//		request.setAttribute("commentConfigMap", commentConfig.getOptionMap());//==> HashMap jsp 단에서 꺼내쓸려고..
		
		//Config memberConfig = configService.getConfig("member");
		//request.setAttribute("memberConfig", memberConfig);
		//request.setAttribute("memberConfigMap", memberConfig.getOptionMap());//==> HashMap jsp 단에서 꺼내쓸려고..
		
		Config securityConfig = configService.getConfig("security");
		request.setAttribute("securityConfig", securityConfig);
		request.setAttribute("securityConfigMap", securityConfig.getOptionMap());//==> HashMap jsp 단에서 꺼내쓸려고..
		
		Config designConfig = configService.getConfig("design");
		request.setAttribute("designConfig", designConfig);
		request.setAttribute("designConfigMap", designConfig.getOptionMap());//==> HashMap jsp 단에서 꺼내쓸려고..
		
//		Config mediaConfig = configService.getConfig("media");
//		request.setAttribute("mediaConfig", mediaConfig);
//		request.setAttribute("mediaConfigMap", mediaConfig.getOptionMap());//==> HashMap jsp 단에서 꺼내쓸려고..
		
//		Config remotePublishConfig = configService.getConfig("remote_publish");
//		request.setAttribute("remotePublishConfig", remotePublishConfig);
//		request.setAttribute("remotePublishConfigMap", remotePublishConfig.getOptionMap());//==> HashMap jsp 단에서 꺼내쓸려고..
		
		Config watermarkConfig = configService.getConfig("watermark");
		request.setAttribute("watermarkConfig", watermarkConfig);
		request.setAttribute("watermarkConfigMap", watermarkConfig.getOptionMap());//==> HashMap jsp 단에서 꺼내쓸려고..
		
//		Config gaConfig = configService.getConfig("google_analytics");
//		request.setAttribute("gaConfig", gaConfig);
//		request.setAttribute("gaConfigMap", gaConfig.getOptionMap());//==> HashMap jsp 단에서 꺼내쓸려고..
		
		//메일서비스 사이트도메인 스태틱 값...메일 열람 추적때문에 추가..
//		if( StringUtils.isBlank(MailServiceImpl.schemeDomainPort) ){
//			MailServiceImpl.schemeDomainPort = AsaproUtils.getSchemeDomainPort(request);
//		} else {
//			if( !MailServiceImpl.schemeDomainPort.equals(AsaproUtils.getSchemeDomainPort(request)) ){
//				MailServiceImpl.schemeDomainPort = AsaproUtils.getSchemeDomainPort(request);
//			}
//		}
		//========== 끝 : 사이트 옵션 ==========
		
		//========== 시작 : userAgent ========================
		//User Agent(OS 정보, 브라우저 정보 등등)
		//user guide :  https://code.google.com/p/user-agent-utils/
		UserAgent userAgent = UserAgent.parseUserAgentString(request.getHeader("User-Agent"));
		request.setAttribute("userAgent", userAgent);
		//========== 끝 : userAgent ==========================
				
		//========== 시작 : 사이트접속시 강제이동 설정 체크 ==========
//		if( !requestUri.startsWith(AsaproUtils.getAdminPath(currentSite)) && !requestUri.contains("logout.do") ){
//			if( "true".equals(siteConfig.getOption("force_redirect")) ){
//				if( StringUtils.isNotBlank(siteConfig.getOption("force_redirect_url")) ){
//					LOGGER.info("[ASAPRO] SiteInterceptor FORCE REDIRECT : {}", siteConfig.getOption("force_redirect_url"));
//					response.sendRedirect(siteConfig.getOption("force_redirect_url"));
//					return false;
//				}
//			}
//		}
		//========== 끝 : 사이트접속시 강제이동 설정 체크 ==========
				
		//========== 시작 : 모바일 리다이렉트 체크 ==========
		/*
		 * 실제 폰에서 모바일에서 피시로 돌아오는 pc버전 링크를 눌렀을때 pc가 나오게 하려면 /m/?deviceType=pc
		 * 처럼 deviceType=pc 라는 파라메터를 링크에 붙여줘야 됨.
		 */
		if( !AsaproUtils.isFilePath(requestUri) ){
			Cookie deviceType = AsaproUtils.getCookie(request.getCookies(), "deviceType");
			String deviceTypeParam = request.getParameter("deviceType");
			if( StringUtils.isNotBlank(deviceTypeParam) ){
				deviceTypeParam = Jsoup.clean(deviceTypeParam, Whitelist.none()).replaceAll("\"", "").replaceAll("\n", "").replaceAll("\r", "");
				deviceType = new Cookie("deviceType", deviceTypeParam);
				deviceType.setPath("/");
				deviceType.setHttpOnly(true);
				response.addCookie(deviceType);
			}
			if( userAgent.getOperatingSystem().getDeviceType().equals(DeviceType.MOBILE) ){
				if( deviceType == null ){
//					if( StringUtils.isNotBlank(siteConfig.getOption("mobile_home_index")) && "true".equals(siteConfig.getOption("mobile_home_redirect")) ){
//						deviceType = new Cookie("deviceType", "mobile");
//						deviceType.setPath("/");
//						deviceType.setHttpOnly(true);
//						response.addCookie(deviceType);
//						response.sendRedirect(siteConfig.getOption("mobile_home_index"));
//						LOGGER.info("[ASAPRO] SiteInterceptor redirect to mobile index...");
//						return false;
//					}
				} else {
//					if( "mobile".equals(deviceType.getValue()) ){
//						if( StringUtils.isNotBlank(siteConfig.getOption("mobile_home_index")) && "true".equals(siteConfig.getOption("mobile_home_redirect")) ){
//							response.sendRedirect(siteConfig.getOption("mobile_home_index"));
//							LOGGER.info("[ASAPRO] SiteInterceptor redirect to mobile index..");
//							return false;
//						}
//					}
				}
			}
		}
		//========== 끝 : 모바일 리다이렉트 체크 ==========
		
		return super.preHandle(request, response, handler);
	}
	
}
