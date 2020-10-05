/**
 * 
 */
package egovframework.com.asapro.archive.customtype.space.service;

import org.apache.commons.lang3.StringUtils;

import egovframework.com.asapro.archive.service.ArchiveSearch;

/**
 * 공간정보 검색
 * @author yckim
 * @since 2019. 2. 26.
 *
 */
public class SpaceSearch extends ArchiveSearch{
	
	

	@Override
	public String getQueryString() {
		
		StringBuilder sb = new StringBuilder(100);
		sb.append(super.getQueryString());
		
		
		return sb.toString();
	}
}
