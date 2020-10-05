/**
 * 
 */
package egovframework.com.asapro.archive.customtype.policy.service;

import org.apache.commons.lang3.StringUtils;

import egovframework.com.asapro.archive.service.ArchiveSearch;

/**
 * 정책자료 검색
 * @author yckim
 * @since 2019. 12. 27.
 *
 */
public class PolicySearch extends ArchiveSearch{
	
	

	@Override
	public String getQueryString() {
		
		StringBuilder sb = new StringBuilder(100);
		sb.append(super.getQueryString());
		
		/*if( StringUtils.isNotBlank(this.getResYear()) ){
			sb.append("&amp;resYear=");
			sb.append(this.getResYear());
		}
		if( StringUtils.isNotBlank(this.getResResearcher()) ){
			sb.append("&amp;resResearcher=");
			sb.append(this.getResResearcher());
		}*/
		return sb.toString();
	}
}
