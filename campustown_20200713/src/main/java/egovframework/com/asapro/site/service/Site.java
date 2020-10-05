/**
 * 
 */
package egovframework.com.asapro.site.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import egovframework.com.asapro.fileinfo.service.FileInfo;
import egovframework.com.asapro.support.Constant;
//import egovframework.com.asapro.watchdog.aop.WatchDog;

/**
 * 사이트 VO
 * @author yckim
 * @since 2018. 4. 3.
 *
 */
public class Site extends MultiSiteVO implements Serializable{
	
	private static final String DOMAIN_GLUE = ",";
	
	//@WatchDog
	private String siteId;	//사이트 아이디
	private String siteName;	//사이트명
	private String siteDescription;	//사이트 설명
	private String siteDomain;	//사이트 도메인
	private String siteType = "domain";	//서브사이트 구분유형 domain | dir
	private String siteTheme;	//사이트 테마
	private boolean siteMain;	//사이트 메인여부
	private String siteLocale;	//사이트 언어
	private FileInfo siteLogo;	//이미지 정보
	private Date siteRegDate;	//등록일시
	
	private MultipartFile siteLogoImage;	//이미지파일
	
	
	/**
	 * 사이트 테이블 프리픽스
	 * @return
	 */
	public String getSitePrefix(){
		//메인사이트의 경우 테이블은 ASA로 시작하고 데이터상의 사이트 아이디는 main 임
		if( Constant.MAIN_SITE_DISPLAY_ID.equals(this.getSiteId()) ){
			return Constant.MAIN_SITE_PREFIX;
		}
		return this.getSiteId().toUpperCase();
	}
	
	/**
	 * @return the siteId
	 */
	public String getSiteId() {
		return siteId;
	}
	/**
	 * @param siteId the siteId to set
	 */
	public void setSiteId(String siteId) {
		this.siteId = siteId;
	}
	/**
	 * 도메인 하나가 아니라 ,(콤마)로 구분된 여러개의 도메인일 수 있다. getSeperatedSiteDomains().get(0) 으로 도메인을 받도록 합시다.
	 * @return the siteDomain
	 */
	public String getSiteDomain() {
		return siteDomain;
	}
	/**
	 * @param siteDomain the siteDomain to set
	 */
	public void setSiteDomain(String siteDomain) {
		this.siteDomain = siteDomain;
	}
	/**
	 * @return the siteName
	 */
	public String getSiteName() {
		return siteName;
	}
	/**
	 * @param siteName the siteName to set
	 */
	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}
	/**
	 * @return the siteDescription
	 */
	public String getSiteDescription() {
		return siteDescription;
	}
	/**
	 * @param siteDescription the siteDescription to set
	 */
	public void setSiteDescription(String siteDescription) {
		this.siteDescription = siteDescription;
	}
	/**
	 * @return the siteTheme
	 */
	public String getSiteTheme() {
		return siteTheme;
	}
	/**
	 * @param siteTheme the siteTheme to set
	 */
	public void setSiteTheme(String siteTheme) {
		this.siteTheme = siteTheme;
	}
	/**
	 * @return the siteMain
	 */
	public boolean isSiteMain() {
		return siteMain;
	}
	/**
	 * @param siteMain the siteMain to set
	 */
	public void setSiteMain(boolean siteMain) {
		this.siteMain = siteMain;
	}

	/**
	 * @return the siteLocale
	 */
	public String getSiteLocale() {
		if( StringUtils.isBlank(this.siteLocale) ){
			return "ko_KR";
		}
		return siteLocale;
	}

	/**
	 * @param siteLocale the siteLocale to set
	 */
	public void setSiteLocale(String siteLocale) {
		this.siteLocale = siteLocale;
	}
	
	/**
	 * @return the siteType
	 */
	public String getSiteType() {
		return siteType;
	}

	/**
	 * @param siteType the siteType to set
	 */
	public void setSiteType(String siteType) {
		this.siteType = siteType;
	}

	/**
	 * @return the siteLogo
	 */
	public FileInfo getSiteLogo() {
		return siteLogo;
	}

	/**
	 * @param siteLogo the siteLogo to set
	 */
	public void setSiteLogo(FileInfo siteLogo) {
		this.siteLogo = siteLogo;
	}

	/**
	 * @return the siteLogoImage
	 */
	public MultipartFile getSiteLogoImage() {
		return siteLogoImage;
	}

	/**
	 * @param siteLogoImage the siteLogoImage to set
	 */
	public void setSiteLogoImage(MultipartFile siteLogoImage) {
		this.siteLogoImage = siteLogoImage;
	}

	/**
	 * @return the siteRegDate
	 */
	public Date getSiteRegDate() {
		return siteRegDate;
	}

	/**
	 * @param siteRegDate the siteRegDate to set
	 */
	public void setSiteRegDate(Date siteRegDate) {
		this.siteRegDate = siteRegDate;
	}

	/**
	 * 도메인이 콤마로 구분되어서 복수개 입력되어 있을 경우때문에 추가됨 DB필드 없음 
	 * @return
	 */
	public List<String> getSeperatedSiteDomains(){
		List<String> list = new ArrayList<String>();
		String domains = StringUtils.deleteWhitespace(this.getSiteDomain());
		if( StringUtils.isNotBlank(domains) ){
			if( !domains.contains(DOMAIN_GLUE) ){
				list.add(domains);
			} else {
				String[] temp = domains.split(DOMAIN_GLUE);
				for(String item : temp){
					list.add(item);
				}
			}
		}
		return list;
	}
}