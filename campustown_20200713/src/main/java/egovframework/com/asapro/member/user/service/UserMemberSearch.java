/**
 * 
 */
package egovframework.com.asapro.member.user.service;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;

import egovframework.com.asapro.support.pagination.PagingSearch;

/**
 * 사용자 목록 검색 조건
 * @author yckim
 * @since 2020. 2. 24.
 *
 */
public class UserMemberSearch extends PagingSearch{

	private String userRoleCode;	//사용자 역할 코드
	private String userActive;	//사용자 상태
	private String groupId;	//그룹 아이디
	private String orgId;	//기관 아이디
	private String depId;	//부서 아이디
	
	/**
	 * @return the userRoleCode
	 */
	public String getUserRoleCode() {
		return userRoleCode;
	}

	/**
	 * @param userRoleCode the userRoleCode to set
	 */
	public void setUserRoleCode(String userRoleCode) {
		this.userRoleCode = userRoleCode;
	}

	/**
	 * @return the userActive
	 */
	public String getUserActive() {
		return userActive;
	}

	/**
	 * @param userActive the userActive to set
	 */
	public void setUserActive(String userActive) {
		this.userActive = userActive;
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

	@Override
	public String getQueryString() {
		
		StringBuilder sb = new StringBuilder(100);
		sb.append(super.getQueryString());
		
		if( StringUtils.isNotBlank(this.getUserRoleCode()) ){
			sb.append("&amp;userRoleCode=");
			sb.append(this.getUserRoleCode());
		}
		
		if( StringUtils.isNotBlank(this.getUserActive()) ){
			sb.append("&amp;userActive=");
			sb.append(this.getUserActive());
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
		
		return sb.toString();
	}
	
	
}
