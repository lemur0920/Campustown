/**
 * 
 */
package egovframework.com.asapro.site.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * 사이트 서비스
 * @author yckim
 * @since 2018.09.11
 *
 */
public interface SiteService {
	
	/**
	 * 사이트 목록을 조회한다.
	 * @param siteSearch
	 * @return 사이트 목록
	 */
	public List<Site> getSiteList(SiteSearch siteSearch);
	
	/**
	 * 사이트 목록 토탈을 조회한다.
	 * @param siteSearch
	 * @return 사이트 목록 토탈
	 */
	public int getSiteListTotal(SiteSearch siteSearch);
	
	/**
	 * 사이트를 조회한다.
	 * @param site
	 * @return 사이트
	 */
	public Site getSite(Site site);
	
	/**
	 * 사이트를 조회한다.
	 * @param siteId
	 * @return 사이트
	 */
	public Site getSite(String siteId);
	
	/**
	 * 사이트를 추가한다.
	 * @param site
	 * @return 추가결과
	 */
	public int insertSite(Site site);
	
	/**
	 * 사이트를 수정한다.
	 * @param site
	 * @return 수정 결과
	 */
	public int updateSite(Site site);
	
	/**
	 * 사이트를 삭제한다.
	 * @param siteIds
	 * @return 삭제 결과
	 */
	public int deleteSite(String[] siteIds);
	
	/**
	 * 사이트를 삭제한다.
	 * @param siteIds
	 * @return 삭제결과
	 */
	public int deleteSite(String siteId);

	/**
	 * 사이트를 찾는다.
	 * @param request
	 * @return 사이트
	 */
	public Site detectCurrentSite(HttpServletRequest request);

	/**
	 * 메인사이트를 조회한다.
	 * @return 메인사이트
	 */
	public Site getMainSite();

	
}
