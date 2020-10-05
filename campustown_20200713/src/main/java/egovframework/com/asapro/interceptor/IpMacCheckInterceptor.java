/**
 * 
 */
package egovframework.com.asapro.interceptor;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import egovframework.com.asapro.allowip.service.AllowIp;
import egovframework.com.asapro.allowip.service.AllowIpSearch;
import egovframework.com.asapro.allowip.service.AllowIpService;
import egovframework.com.asapro.allowip.service.AllowIpTemp;
import egovframework.com.asapro.allowip.service.AllowIpTempSearch;
import egovframework.com.asapro.allowmac.service.AllowMac;
import egovframework.com.asapro.allowmac.service.AllowMacSearch;
import egovframework.com.asapro.allowmac.service.AllowMacService;
import egovframework.com.asapro.config.service.Config;
import egovframework.com.asapro.config.service.ConfigService;
import egovframework.com.asapro.site.service.Site;
import egovframework.com.asapro.support.AsaproNetworkState;
import egovframework.com.asapro.support.util.AsaproUtils;

/**
 * IP, MAC 체크 인터셉터
 * - 관리자페이지 접속 허용 아이피,맥주초 체킹
 * @author yckim
 * 
 */
public class IpMacCheckInterceptor extends HandlerInterceptorAdapter {

	private static final Logger LOGGER = LoggerFactory.getLogger(IpMacCheckInterceptor.class);

	@Autowired
	private AllowIpService allowIpService;

	@Autowired
	private AllowMacService allowMacService;
	
	@Autowired
	private ConfigService configService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

		String ipCheckUse = configService.getNoCacheGlobalOption("security", "allow_ip").getOptValue();//아이피 체크 기능 사용여부
		String macCheckUse = configService.getNoCacheGlobalOption("security", "allow_mac_address").getOptValue();//맥주소 체크 기능 사용여부

		//아이피,맥주소 체크 기능 사용 안하면 패스
		if("false".equals(ipCheckUse) && "false".equals(macCheckUse) ){
			LOGGER.info("[ASAPRO] IpMacCheckInterceptor Do not use the check function" );
			return true;
		}

		String remoteIp = AsaproUtils.getRempoteIp(request);
		//System.out.println(AsaproNetworkState.getMyIPaddress());
		String remoteMacAddress = "";
		Site currentSite = (Site) request.getAttribute("currentSite");
		
		// ========== 시작 : 관리자단 접속 아이피, 맥주소 설정 확인 및 적용 ==========
		
		boolean isPatternedIp = false;
		boolean isPatternedMac = false;
		
		if("true".equals(ipCheckUse)){
			
			//허용 아이피 목록
			AllowIpSearch allowIpSearch = new AllowIpSearch();
			allowIpSearch.setSitePrefix(currentSite.getSitePrefix());
			allowIpSearch.setPaging(false);
			allowIpSearch.setIpUse(true);
			allowIpSearch.setIpType("allow");	//허용목록검색
			List<AllowIp> allowIpList = allowIpService.getAllowIpList(allowIpSearch);
			
			//임시허용 아이피 목록
			AllowIpTempSearch allowIpTempSearch = new AllowIpTempSearch();
			allowIpTempSearch.setSitePrefix(currentSite.getSitePrefix());
			allowIpTempSearch.setAuthentication(true);
			List<AllowIpTemp> allowIpTempList = allowIpService.getAllowIpTempList(allowIpTempSearch);
			
			//차단 아이피 목록
			allowIpSearch.setIpType("block");	//차단목록검색
			List<AllowIp> blockIpList = allowIpService.getAllowIpList(allowIpSearch);
			
			
			
			/**
			 * 접속허용 아이피 채킹
			 */
			if(allowIpList != null && allowIpList.size() > 0){
				
				for (AllowIp allowIp : allowIpList) {
					//종료 아이피가 없을경우 - 1건, * 을 처리
					if(StringUtils.isBlank(allowIp.getIpEnd()) ){
						if(!allowIp.getIpStart().contains("*")){
							
							if( remoteIp.equals(allowIp.getIpStart()) ){
								LOGGER.info("[ASAPRO] IpMacCheckInterceptor remote ip [{}] is in [ALLOW IP LIST].", remoteIp);
								isPatternedIp = true;
								break;
							}
						}else{
							
							String pattern = allowIp.getIpStart().replace(".", "\\.").replace("*", "\\d+");
							LOGGER.info("[ASAPRO] IpMacCheckInterceptor checking ip address [ALLOW PATTERN] : {}, remote ip : {} ", pattern, remoteIp);
							
							if (remoteIp.matches(pattern)) {
								LOGGER.info("[ASAPRO] IpMacCheckInterceptor remote ip [{}] is in [ALLOW IP LIST].", remoteIp);
								isPatternedIp = true;
								break;
							}
						}
						
					}
					//종료 아이피가 있을경우  - 구간에 해당하는 아이피를 매칭 비교
					else{
						LOGGER.info("[ASAPRO] IpMacCheckInterceptor checking ip address [ALLOW SECTION] : {} ~ {} , remote ip : {} ", allowIp.getIpStart(), allowIp.getIpEnd(), remoteIp);
						String[] tempStartIp = allowIp.getIpStart().split("\\.");
						int end = Integer.parseInt(allowIp.getIpEnd());
						int start = Integer.parseInt(tempStartIp[3]);
						String sectionIpTemp = tempStartIp[0] + "." + tempStartIp[1] + "." + tempStartIp[2] + ".";
						for (int i = start; i <= end; i++) {
							String sectionIp = sectionIpTemp + i;
							if( remoteIp.equals(sectionIp) ){
								LOGGER.info("[ASAPRO] IpMacCheckInterceptor remote ip [{}] is in [ALLOW IP LIST].", remoteIp);
								isPatternedIp = true;
								break;
							}
						}
						//구간 매칭이 true 이면 허용아이피 비교 for문을 빠져나간다.
						if (isPatternedIp) {
							break;
						}
					}
				}
			}else{
				LOGGER.info("[ASAPRO] IpMacCheckInterceptor " + " Admin allow ip is not set...");
			}
			
			
			/**
			 * 임시접속허용 아이피 채킹
			 */
			if(allowIpTempList != null && allowIpTempList.size() > 0){
				for (AllowIpTemp allowIpTemp : allowIpTempList) {
					if( remoteIp.equals(allowIpTemp.getTempIp()) ){
						LOGGER.info("[ASAPRO] IpMacCheckInterceptor remote ip [{}] is in [ALLOW IP TEMP LIST].", remoteIp);
						isPatternedIp = true;
						break;
					}
				}
			}else{
				LOGGER.info("[ASAPRO] IpMacCheckInterceptor " + " Admin allow (Temp) ip is not set...");
			}
			
			
			/**
			 * 접속차단 아이피 채킹
			 * 허용 아이이피에 있다 하더라도 차단목록에 있으면 차단된다.
			 */
			if(blockIpList != null && blockIpList.size() > 0){
				for (AllowIp blockIp : blockIpList) {
					//종료 아이피가 없을경우 - 1건, * 을 처리
					if(StringUtils.isBlank(blockIp.getIpEnd()) ){
						if(!blockIp.getIpStart().contains("*")){
							
							if( remoteIp.equals(blockIp.getIpStart()) ){
								LOGGER.info("[ASAPRO] IpMacCheckInterceptor remote ip [{}] is in [BLOCK IP LIST].", remoteIp);
								isPatternedIp = false;
								break;
							}
						}else{
							
							String pattern = blockIp.getIpStart().replace(".", "\\.").replace("*", "\\d+");
							LOGGER.info("[ASAPRO] IpMacCheckInterceptor checking ip address [BLOCK PATTERN] : {}, remote ip : {} ", pattern, remoteIp);
							
							if (remoteIp.matches(pattern)) {
								LOGGER.info("[ASAPRO] IpMacCheckInterceptor remote ip [{}] is in [BLOCK IP LIST].", remoteIp);
								isPatternedIp = false;
								break;
							}
						}
						
					}
					//종료 아이피가 있을경우  - 구간에 해당하는 아이피를 매칭 비교
					else{
						LOGGER.info("[ASAPRO] IpMacCheckInterceptor checking ip address [BLOCK SECTION] : {} ~ {} , remote ip : {} ", blockIp.getIpStart(), blockIp.getIpEnd(), remoteIp);
						String[] tempStartIp = blockIp.getIpStart().split("\\.");
						int end = Integer.parseInt(blockIp.getIpEnd());
						int start = Integer.parseInt(tempStartIp[3]);
						String sectionIpTemp = tempStartIp[0] + "." + tempStartIp[1] + "." + tempStartIp[2] + ".";
						for (int i = start; i <= end; i++) {
							String sectionIp = sectionIpTemp + i;
							if( remoteIp.equals(sectionIp) ){
								LOGGER.info("[ASAPRO] IpMacCheckInterceptor remote ip [{}] is in [BLOCK IP LIST].", remoteIp);
								isPatternedIp = false;
								break;
							}
						}
						//구간 매칭이 true 이면 허용아이피 비교 for문을 빠져나간다.
						if (isPatternedIp) {
							break;
						}
					}
				}
			}
		}
		
		if("true".equals(macCheckUse)){
			remoteMacAddress = AsaproNetworkState.getMyMACAddress(remoteIp );
			
			//허용 맥주소 목록
			AllowMacSearch allowMacSearch = new AllowMacSearch();
			allowMacSearch.setSitePrefix(currentSite.getSitePrefix());
			allowMacSearch.setPaging(false);
			allowMacSearch.setMacUse(true);
			allowMacSearch.setMacType("allow");	//허용목록검색
			List<AllowMac> allowMacList = allowMacService.getAllowMacList(allowMacSearch);
			
			//차단 맥주소 목록
			allowMacSearch.setMacType("block");	//허용목록검색
			List<AllowMac> blockMacList = allowMacService.getAllowMacList(allowMacSearch);
			
			
			/**
			 * 접속허용 맥주소 채킹
			 */
			if(allowMacList != null && allowMacList.size() > 0){
				
				for (AllowMac allowMac : allowMacList) {
					if( remoteMacAddress.equals(allowMac.getMacAddress()) ){
						LOGGER.info("[ASAPRO] IpMacCheckInterceptor remote Mac Address [{}] is in [ALLOW Mac LIST].", remoteMacAddress);
						isPatternedMac = true;
						break;
					}
					
				}
			}else{
				LOGGER.info("[ASAPRO] IpMacCheckInterceptor " + " Admin allow Mac is not set...");
			}
			
			/**
			 * 접속차단 맥주소 채킹
			 * 허용 목록에 있다 하더라도 차단목록에 있으면 차단된다.
			 */
			if(blockMacList != null && blockMacList.size() > 0){
				for (AllowMac blockMac : blockMacList) {
					if( remoteMacAddress.equals(blockMac.getMacAddress()) ){
						LOGGER.info("[ASAPRO] IpMacCheckInterceptor remote Mac [{}] is in [BLOCK Mac LIST].", remoteMacAddress);
						isPatternedMac = false;
						break;
					}
				}
			}
		}
		
		
		// 접속허용 ip가 아니므로 사용자단으로 리다이렉트
		if (!isPatternedIp && !isPatternedMac) {
			LOGGER.warn("[ASAPRO] IpMacCheckInterceptor remote ip [{}] or mac address [{}] is not allowed to access admin area.", remoteIp, remoteMacAddress);
			response.sendRedirect("/");
			return false;
		}else{
			return true;
		}
		
		
		// ========== 끝 : 관리자단 접속 아이피, 맥주소 설정 확인 및 적용 ==========
		
		
				
	}

}
