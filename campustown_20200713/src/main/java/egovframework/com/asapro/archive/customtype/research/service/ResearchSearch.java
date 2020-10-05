/**
 * 
 */
package egovframework.com.asapro.archive.customtype.research.service;

import org.apache.commons.lang3.StringUtils;

import egovframework.com.asapro.archive.service.ArchiveSearch;

/**
 * 연구자료 검색
 * @author yckim
 * @since 2019. 2. 27.
 *
 */
public class ResearchSearch extends ArchiveSearch{
	
	private String resYear;	//연도
	private String resResearcher;	//연구자,단체명

	/**
	 * @return the resYear
	 */
	public String getResYear() {
		return resYear;
	}

	/**
	 * @param resYear the resYear to set
	 */
	public void setResYear(String resYear) {
		this.resYear = resYear;
	}

	/**
	 * @return the resResearcher
	 */
	public String getResResearcher() {
		return resResearcher;
	}

	/**
	 * @param resResearcher the resResearcher to set
	 */
	public void setResResearcher(String resResearcher) {
		this.resResearcher = resResearcher;
	}

	@Override
	public String getQueryString() {
		
		StringBuilder sb = new StringBuilder(100);
		sb.append(super.getQueryString());
		
		if( StringUtils.isNotBlank(this.getResYear()) ){
			sb.append("&amp;resYear=");
			sb.append(this.getResYear());
		}
		if( StringUtils.isNotBlank(this.getResResearcher()) ){
			sb.append("&amp;resResearcher=");
			sb.append(this.getResResearcher());
		}
		return sb.toString();
	}
}
