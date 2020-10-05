/**
 * 
 */
package egovframework.com.asapro.organization.service;

import java.io.Serializable;
import java.util.Date;

/**
 * 팀 VO
 * 
 * @author yckim
 * @since 2020. 4. 7.
 */
@SuppressWarnings("serial")
public class Team  implements Serializable{
	private String teamId;	//팀코드
	private String depId;	//부서코드
	private String teamName;	//팀명
	private String teamNameEn;	//팀명 영문
	private String teamTel;	//팀 전화번호
	private String teamDescription;	//팀소개
	private boolean teamUse = true;	//사용유무
	private Date teamRegdate;	//등록일
	private Integer teamOrder;	//순서
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
	 * @return the teamName
	 */
	public String getTeamName() {
		return teamName;
	}
	/**
	 * @param teamName the teamName to set
	 */
	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}
	/**
	 * @return the teamNameEn
	 */
	public String getTeamNameEn() {
		return teamNameEn;
	}
	/**
	 * @param teamNameEn the teamNameEn to set
	 */
	public void setTeamNameEn(String teamNameEn) {
		this.teamNameEn = teamNameEn;
	}
	/**
	 * @return the teamTel
	 */
	public String getTeamTel() {
		return teamTel;
	}
	/**
	 * @param teamTel the teamTel to set
	 */
	public void setTeamTel(String teamTel) {
		this.teamTel = teamTel;
	}
	/**
	 * @return the teamDescription
	 */
	public String getTeamDescription() {
		return teamDescription;
	}
	/**
	 * @param teamDescription the teamDescription to set
	 */
	public void setTeamDescription(String teamDescription) {
		this.teamDescription = teamDescription;
	}
	/**
	 * @return the teamUse
	 */
	public boolean isTeamUse() {
		return teamUse;
	}
	/**
	 * @param teamUse the teamUse to set
	 */
	public void setTeamUse(boolean teamUse) {
		this.teamUse = teamUse;
	}
	/**
	 * @return the teamRegdate
	 */
	public Date getTeamRegdate() {
		return teamRegdate;
	}
	/**
	 * @param teamRegdate the teamRegdate to set
	 */
	public void setTeamRegdate(Date teamRegdate) {
		this.teamRegdate = teamRegdate;
	}
	/**
	 * @return the teamOrder
	 */
	public Integer getTeamOrder() {
		return teamOrder;
	}
	/**
	 * @param teamOrder the teamOrder to set
	 */
	public void setTeamOrder(Integer teamOrder) {
		this.teamOrder = teamOrder;
	}
	
	

	
}
