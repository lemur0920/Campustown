/**
 * 
 */
package egovframework.com.asapro.member.admin.service;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;

import egovframework.com.asapro.support.pagination.PagingSearch;

/**
 * 관리자 목록 검색 조건
 * @author yckim
 * @since 2018. 4. 26.
 *
 */
public class AdminMemberSearch extends PagingSearch{

	private String adminRoleCode;	//관리자 역할 코드
	private String adminActive;	//관리자 상태
	private String groupId;	//그룹 아이디
	private String orgId;	//기관 아이디
	private String depId;	//부서 아이디
	private String teamId;	//팀 아이디
	
	/**
	 * @return the adminRoleCode
	 */
	public String getAdminRoleCode() {
		return adminRoleCode;
	}

	/**
	 * @param adminRoleCode the adminRoleCode to set
	 */
	public void setAdminRoleCode(String adminRoleCode) {
		this.adminRoleCode = adminRoleCode;
	}

	/**
	 * @return the adminActive
	 */
	public String getAdminActive() {
		return adminActive;
	}

	/**
	 * @param adminActive the adminActive to set
	 */
	public void setAdminActive(String adminActive) {
		this.adminActive = adminActive;
	}

	/**
	 * @return the groupId
	 */
	public String getGroupId() {
		return groupId;
	}

	/**
	 * @param groupId the groupId to set
	 */
	public void setGroupId(String groupId) {
		this.groupId = groupId;
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

	/**
	 * @return the orgId
	 */
	public String getOrgId() {
		return orgId;
	}

	/**
	 * @param orgId the orgId to set
	 */
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	/**
	 * @return the teamId
	 */
	public String getTeamId() {
		return teamId;
	}

	/**
	 * @param teamId the teamId to set
	 */
	public void setTeamId(String teamId) {
		this.teamId = teamId;
	}

	@Override
	public String getQueryString() {
		
		StringBuilder sb = new StringBuilder(100);
		sb.append(super.getQueryString());
		
		if( StringUtils.isNotBlank(this.getAdminRoleCode()) ){
			sb.append("&amp;adminRoleCode=");
			sb.append(this.getAdminRoleCode());
		}
		
		if( StringUtils.isNotBlank(this.getAdminActive()) ){
			sb.append("&amp;adminActive=");
			sb.append(this.getAdminActive());
		}
		
		if( StringUtils.isNotBlank(this.getGroupId()) ){
			sb.append("&amp;groupId=");
			sb.append(this.getGroupId());
		}
		
		if( StringUtils.isNotBlank(this.getDepId()) ){
			sb.append("&amp;depId=");
			sb.append(this.getDepId());
		}
		if( StringUtils.isNotBlank(this.getOrgId()) ){
			sb.append("&amp;orgId=");
			sb.append(this.getOrgId());
		}
		if( StringUtils.isNotBlank(this.getTeamId()) ){
			sb.append("&amp;teamId=");
			sb.append(this.getTeamId());
		}
		
		return sb.toString();
	}
	
	
}
