/**
 * 
 */
package egovframework.com.asapro.site.service;

import org.codehaus.jackson.annotate.JsonIgnore;



/**
 * 멀티사이트용 모델
 * <pre>
 * 서브사이트별로 사용되는(개별 테이블이 생성되는) 모듈의 모델은 이 클래스를 상속하도록 한다.
 * ex) 서브사이트별 생성 - Archive(O), 전역으로 관리 - Member(X)
 * </pre>
 * @author yckim
 *
 */
public class MultiSiteVO {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@JsonIgnore
	protected String sitePrefix;

	/**
	 * @return the sitePrefix
	 */
	public String getSitePrefix() {
		return sitePrefix;
	}

	/**
	 * @param sitePrefix the sitePrefix to set
	 */
	public void setSitePrefix(String sitePrefix) {
		this.sitePrefix = sitePrefix;
	}
	
	/**
	 * 사이트 아이디를 반환한다. 프리픽스 대신 아이디가 필요할 경우에 사용(실제 데이터는 프리픽스를 소문자로 만들어서 반환함)
	 * @return
	 */
	@JsonIgnore
	public String getSiteId(){
		return this.sitePrefix.toLowerCase();
	}
	
}
