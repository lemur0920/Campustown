/**
 * 
 */
package egovframework.com.asapro.organization.service;

import org.apache.commons.lang3.StringUtils;

import egovframework.com.asapro.support.pagination.PagingSearch;

/**
 * 팀 검색 VO 
 * 
 * @author yckim
 * @since 2020. 4. 7.
 */
public class TeamSearch extends PagingSearch{

	private Boolean teamUse;
	private String depId;
	
	/**
	 * @return the teamUse
	 */
	public Boolean getTeamUse() {
		return teamUse;
	}

	/**
	 * @param teamUse the teamUse to set
	 */
	public void setTeamUse(Boolean teamUse) {
		this.teamUse = teamUse;
	}

	/**
	 * @return the depId
	 */
	public String getDepId() {
		return depId;
	}

	/**
	 * @param depId the depId to set
	 */
	public void setDepId(String depId) {
		this.depId = depId;
	}

	@Override
	public String getQueryString() {
		
		StringBuilder sb = new StringBuilder(100);
		sb.append(super.getQueryString());
		
		if( this.getTeamUse() != null ){
			sb.append("&amp;teamUse=");
			sb.append(this.getTeamUse());
		}
		if(StringUtils.isNotBlank(this.getDepId()) ){
			sb.append("&amp;depId=");
			sb.append(this.getDepId());
		}
		
		return sb.toString();
	}
	
}
