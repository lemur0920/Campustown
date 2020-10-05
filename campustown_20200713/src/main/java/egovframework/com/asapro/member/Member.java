/**
 * 
 */
package egovframework.com.asapro.member;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.safety.Whitelist;
import org.springframework.web.multipart.MultipartFile;

import egovframework.com.asapro.code.service.Code;
import egovframework.com.asapro.member.admin.service.AdminMember;
import egovframework.com.asapro.member.user.service.UserMember;
import egovframework.com.asapro.organization.service.Department;
import egovframework.com.asapro.organization.service.Organization;
import egovframework.com.asapro.role.service.Role;
import egovframework.com.asapro.capability.service.Capability;
//import egovframework.com.asapro.group.service.Group;
import egovframework.com.asapro.support.exception.AsaproException;
import egovframework.com.asapro.support.util.AsaproUtils;
//import egovframework.com.asapro.watchdog.aop.WatchDog;

/**
 * 회원 VO
 *  - 관리자, 사용자가 공통으로 기본정보 받아서 쓰는 용도
 * @author yckim
 * @since 2018. 6. 21.
 *
 */
@SuppressWarnings("serial")
public class Member implements Serializable{
	
	private String memberId = "";	//회원 아이디
	private String memberPassword = "";	//회원 비밀번호
	private String memberName = "";	//회원 성명
	private String memberEmail = "";	//관리자 이메일
	private String memberDepartment = "";	//관리자 소속부서
	private String memberTeam = "";	//관리자 소속팀
	
	private Role memberRole;	//역할
	private Organization organization;
	private Department department;
	private Code position;
	
//	private List<Group> groups;
	
	/**
	 * @return the memberId
	 */
	public String getMemberId() {
		return memberId;
	}

	/**
	 * @param memberId the memberId to set
	 */
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	/**
	 * @return the memberPassword
	 */
	public String getMemberPassword() {
		return memberPassword;
	}

	/**
	 * @param memberPassword the memberPassword to set
	 */
	public void setMemberPassword(String memberPassword) {
		this.memberPassword = memberPassword;
	}

	/**
	 * @return the memberName
	 */
	public String getMemberName() {
		return memberName;
	}

	/**
	 * @param memberName the memberName to set
	 */
	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	/**
	 * @return the memberEmail
	 */
	public String getMemberEmail() {
		return memberEmail;
	}

	/**
	 * @param memberEmail the memberEmail to set
	 */
	public void setMemberEmail(String memberEmail) {
		this.memberEmail = memberEmail;
	}

	/**
	 * @return the memberDepartment
	 */
	public String getMemberDepartment() {
		return memberDepartment;
	}

	/**
	 * @param memberDepartment the memberDepartment to set
	 */
	public void setMemberDepartment(String memberDepartment) {
		this.memberDepartment = memberDepartment;
	}

	/**
	 * @return the memberRole
	 */
	public Role getMemberRole() {
		return memberRole;
	}

	/**
	 * @param memberRole the memberRole to set
	 */
	public void setMemberRole(Role memberRole) {
		this.memberRole = memberRole;
	}

	/**
	 * @return the organization
	 */
	public Organization getOrganization() {
		return organization;
	}

	/**
	 * @param organization the organization to set
	 */
	public void setOrganization(Organization organization) {
		this.organization = organization;
	}

	/**
	 * @return the department
	 */
	public Department getDepartment() {
		return department;
	}

	/**
	 * @param department the department to set
	 */
	public void setDepartment(Department department) {
		this.department = department;
	}

	/**
	 * @return the position
	 */
	public Code getPosition() {
		return position;
	}

	/**
	 * @param position the position to set
	 */
	public void setPosition(Code position) {
		this.position = position;
	}

	/**
	 * @return the memberTeam
	 */
	public String getMemberTeam() {
		return memberTeam;
	}

	/**
	 * @param memberTeam the memberTeam to set
	 */
	public void setMemberTeam(String memberTeam) {
		this.memberTeam = memberTeam;
	}

	//=================================================================================
	/**
	 * 최고관리자 인지 확인하다.
	 * - id : admin
	 * - role : ROLE_SUPER_ADMIN
	 * @return 확인결과
	 */
	public boolean isSuperAdmin(){
		if( StringUtils.isBlank(this.getMemberId()) || this.getMemberRole() == null || StringUtils.isBlank(this.getMemberRole().getRoleCode()) ){
			return false;
		}
		
		if( "admin".equals(this.getMemberId()) || "ROLE_SUPER_ADMIN".equals(this.getMemberRole().getRoleCode()) ){
			return true;
		}
		
		return false;
	}
	
	/**
	 * 비회원인지 확인한다. 롤 코드가 ROLE_GUEST 인지 확인함.
	 * @return
	 */
	
	public boolean isGuest(){
		//회원 아이디 없으면
		if( StringUtils.isBlank(this.getMemberId()) ){
			return true;
		}
		//회원 아이디 있는데 guest_ 로 시작하면
		if( StringUtils.isNotBlank(this.getMemberId()) && this.getMemberId().startsWith("guest_") ){
			return true;
		}
		if( this.getMemberRole() != null ){
			if( "ROLE_GUEST".equals(this.getMemberRole().getRoleCode()) ){
				return true;
			}
		} else {
			throw new AsaproException("[ASAPRO] CurrentAdmin is NULL.");
		}
		return false;
	}
	
	
	/**
	 * 회원이 특정권한(Capability)를 가지고 있는지 체크한다.
	 * @param capId
	 * @return 체크결과
	 */
	public boolean hasCapability(String capId){
		if( this.getMemberRole() != null ){
			if( this.getMemberRole().isRoleAdmin() ){
				return true;
			}
			if( this.getMemberRole().getCapabilityList() != null && !this.getMemberRole().getCapabilityList().isEmpty() ){
				String capIdTarget = capId.trim();
				for( Capability cap : this.getMemberRole().getCapabilityList() ){
					if( capIdTarget.equals(cap.getCapId()) ){
						return true;
					}
				}
			}
		}
		return false;
	}
	
	/**
	 * 회원이 특정권한(Capability)를 가지고 있는지 체크한다.
	 * @param capId
	 * @return 체크결과
	 */
	public boolean hasCapability(Capability capability){
		return this.hasCapability(capability.getCapId());
	}
	
	/**
	 * 관리자 정보를 공통회원객체에 셋팅한다.
	 * @param adminMember
	 */
	public void setMemberFromAdmin(AdminMember adminMember){
		this.memberId = adminMember.getAdminId();
		this.memberPassword = adminMember.getAdminPassword();
		this.memberName = adminMember.getAdminName();
		this.memberEmail = adminMember.getAdminEmail();
		this.memberDepartment = adminMember.getAdminDepartment();
		this.memberTeam = adminMember.getAdminTeam();
		
		this.memberRole = adminMember.getAdminRole();
		this.organization = adminMember.getOrganization();
		this.department = adminMember.getDepartment();
		this.position = adminMember.getPosition();
		
//		this.groups = null;
	}
	
	/**
	 * 관리자 정보를 공통회원객체에 셋팅한다.
	 * @param userMember
	 */
	public void setMemberFromUser(UserMember userMember){
		this.memberId = userMember.getUserId();
		this.memberPassword = userMember.getUserPassword();
		this.memberName = userMember.getUserName();
		this.memberEmail = userMember.getUserEmail();
		this.memberDepartment = userMember.getUserDepartment();
		this.memberTeam = userMember.getUserTeam();
		
		this.memberRole = userMember.getUserRole();
		this.organization = userMember.getOrganization();
		this.department = userMember.getDepartment();
		this.position = userMember.getPosition();
		
//		this.groups = null;
	}

	public void setMemberDefault(HttpServletRequest request){
		this.setMemberId("guest_" + AsaproUtils.getRempoteIp(request));
		this.setMemberName("손님");
		Role guestRole = new Role();
		guestRole.setRoleCode("ROLE_GUEST");
		guestRole.setRoleName("비회원");
		this.setMemberRole(guestRole);
	}

	
}
