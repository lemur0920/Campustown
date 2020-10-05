/**
 * 
 */
package egovframework.com.asapro.member.admin.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.jsoup.safety.Whitelist;
import org.springframework.web.multipart.MultipartFile;

import egovframework.com.asapro.code.service.Code;
import egovframework.com.asapro.organization.service.Department;
import egovframework.com.asapro.organization.service.Organization;
import egovframework.com.asapro.organization.service.Team;
import egovframework.com.asapro.role.service.Role;
import egovframework.com.asapro.site.service.MultiSiteVO;
import egovframework.com.asapro.capability.service.Capability;
//import egovframework.com.asapro.group.service.Group;
import egovframework.com.asapro.support.exception.AsaproException;
import egovframework.com.asapro.support.util.AsaproUtils;

/**
 * 관리자 VO
 * @author yckim
 * @since 2018. 4. 26.
 *
 */
@SuppressWarnings("serial")
public class AdminMember extends MultiSiteVO implements Serializable{
	
	private String adminId = "";	//관리자 아이디
	private String adminPassword = "";	//관리자 비밀번호
	private String adminPasswordCheck = "";	//관리자 비밀번호 확인 - 컬럼 없음

	private String adminName = "";	//관리자 성명
	private String adminSex = "";	//관리자 성별 - 코드
	private String adminEmail = "";	//관리자 이메일
	private String adminTel1 = "";	//관리자 전화번호1
	private String adminTel2 = "";	//관리자 전화번호2
	private String adminTel3 = "";	//관리자 전화번호3
	private String adminMobile1 = "";	//관리자 휴대폰번호1
	private String adminMobile2 = "";	//관리자 휴대폰번호2
	private String adminMobile3 = "";	//관리자 휴대폰번호3
	private String adminFax = "";	//관리자 팩스번호
	private String adminZipcode = "";	//관리자 우편번호
	private String adminAddress = "";	//관리자 주소
	private String adminAddressDetail ="";	//관리자 상세주소
	private Date adminRegdate;	//가입일시
	private Boolean adminActive;	//관리자 상태
	private Date adminLoginLastDate;	//마지막 로그인 일시
	private Date adminPWLastUpdated;	//비밀번호 마지막 변경일시
	private String adminOrganization = "";	//소속 기관코드
	private String adminDepartment = "";	//소속 부서코드
	private String adminTeam = "";	//소속 팀코드
	private String adminPosition = "";	//직급코드
	private Integer adminManagementLevel;	//조직원관리레벨
	private Boolean adminLock;	//로그인 제한 (잠김)여부
	private Integer adminLoginFailCount = 0;	//로그인 실패 횟수
	private Date adminLoginFailDate;	//로그인 마지막 실패 일시
	
	private Role adminRole;	//역할
	private List<Role> adminRoleList;	//역할 목록
	private Organization organization;	//조직
	private Department department;	//부서
	private Team team;	//팀
	private Code position;	//직급/직책
	
	private MultipartFile adminProfileImage;	//프로파일 이미지
	private boolean adminProfileImageRemove;
	
	private List<AdminSiteRoleRel> adminSiteRoleRelList;	//관리자 사이트별 역할 릴레이션 목록
	
//	private List<Group> groups;
	

	/**
	 * @return the adminId
	 */
	public String getAdminId() {
		return adminId;
	}

	/**
	 * @param adminId the adminId to set
	 */
	public void setAdminId(String adminId) {
		this.adminId = adminId;
	}

	/**
	 * @return the adminPassword
	 */
	public String getAdminPassword() {
		return adminPassword;
	}

	/**
	 * @param adminPassword the adminPassword to set
	 */
	public void setAdminPassword(String adminPassword) {
		this.adminPassword = adminPassword;
	}

	/**
	 * @return the adminPasswordCheck
	 */
	public String getAdminPasswordCheck() {
		return adminPasswordCheck;
	}

	/**
	 * @param adminPasswordCheck the adminPasswordCheck to set
	 */
	public void setAdminPasswordCheck(String adminPasswordCheck) {
		this.adminPasswordCheck = adminPasswordCheck;
	}

	/**
	 * @return the adminName
	 */
	public String getAdminName() {
		return adminName;
	}

	/**
	 * @param adminName the adminName to set
	 */
	public void setAdminName(String adminName) {
		this.adminName = adminName;
	}

	/**
	 * @return the adminSex
	 */
	public String getAdminSex() {
		return adminSex;
	}

	/**
	 * @param adminSex the adminSex to set
	 */
	public void setAdminSex(String adminSex) {
		this.adminSex = adminSex;
	}

	/**
	 * @return the adminEmail
	 */
	public String getAdminEmail() {
		return adminEmail;
	}

	/**
	 * @param adminEmail the adminEmail to set
	 */
	public void setAdminEmail(String adminEmail) {
		this.adminEmail = adminEmail;
	}

	/**
	 * @return the adminTel1
	 */
	public String getAdminTel1() {
		return adminTel1;
	}

	/**
	 * @param adminTel1 the adminTel1 to set
	 */
	public void setAdminTel1(String adminTel1) {
		this.adminTel1 = adminTel1;
	}

	/**
	 * @return the adminTel2
	 */
	public String getAdminTel2() {
		return adminTel2;
	}

	/**
	 * @param adminTel2 the adminTel2 to set
	 */
	public void setAdminTel2(String adminTel2) {
		this.adminTel2 = adminTel2;
	}

	/**
	 * @return the adminTel3
	 */
	public String getAdminTel3() {
		return adminTel3;
	}

	/**
	 * @param adminTel3 the adminTel3 to set
	 */
	public void setAdminTel3(String adminTel3) {
		this.adminTel3 = adminTel3;
	}

	/**
	 * @return the adminMobile1
	 */
	public String getAdminMobile1() {
		return adminMobile1;
	}

	/**
	 * @param adminMobile1 the adminMobile1 to set
	 */
	public void setAdminMobile1(String adminMobile1) {
		this.adminMobile1 = adminMobile1;
	}

	/**
	 * @return the adminMobile2
	 */
	public String getAdminMobile2() {
		return adminMobile2;
	}

	/**
	 * @param adminMobile2 the adminMobile2 to set
	 */
	public void setAdminMobile2(String adminMobile2) {
		this.adminMobile2 = adminMobile2;
	}

	/**
	 * @return the adminMobile3
	 */
	public String getAdminMobile3() {
		return adminMobile3;
	}

	/**
	 * @param adminMobile3 the adminMobile3 to set
	 */
	public void setAdminMobile3(String adminMobile3) {
		this.adminMobile3 = adminMobile3;
	}

	/**
	 * @return the adminFax
	 */
	public String getAdminFax() {
		return adminFax;
	}

	/**
	 * @param adminFax the adminFax to set
	 */
	public void setAdminFax(String adminFax) {
		this.adminFax = adminFax;
	}

	/**
	 * @return the adminZipcode
	 */
	public String getAdminZipcode() {
		return adminZipcode;
	}

	/**
	 * @param adminZipcode the adminZipcode to set
	 */
	public void setAdminZipcode(String adminZipcode) {
		this.adminZipcode = adminZipcode;
	}

	/**
	 * @return the adminAddress
	 */
	public String getAdminAddress() {
		return adminAddress;
	}

	/**
	 * @param adminAddress the adminAddress to set
	 */
	public void setAdminAddress(String adminAddress) {
		this.adminAddress = adminAddress;
	}

	/**
	 * @return the adminAddressDetail
	 */
	public String getAdminAddressDetail() {
		return adminAddressDetail;
	}

	/**
	 * @param adminAddressDetail the adminAddressDetail to set
	 */
	public void setAdminAddressDetail(String adminAddressDetail) {
		this.adminAddressDetail = adminAddressDetail;
	}

	/**
	 * @return the adminRegdate
	 */
	public Date getAdminRegdate() {
		return adminRegdate;
	}

	/**
	 * @param adminRegdate the adminRegdate to set
	 */
	public void setAdminRegdate(Date adminRegdate) {
		this.adminRegdate = adminRegdate;
	}

	/**
	 * @return the adminActive
	 */
	public Boolean getAdminActive() {
		return adminActive;
	}

	/**
	 * @param adminActive the adminActive to set
	 */
	public void setAdminActive(Boolean adminActive) {
		this.adminActive = adminActive;
	}

	/**
	 * @return the adminLoginLastDate
	 */
	public Date getAdminLoginLastDate() {
		return adminLoginLastDate;
	}

	/**
	 * @param adminLoginLastDate the adminLoginLastDate to set
	 */
	public void setAdminLoginLastDate(Date adminLoginLastDate) {
		this.adminLoginLastDate = adminLoginLastDate;
	}

	/**
	 * @return the adminPWLastUpdated
	 */
	public Date getAdminPWLastUpdated() {
		return adminPWLastUpdated;
	}

	/**
	 * @param adminPWLastUpdated the adminPWLastUpdated to set
	 */
	public void setAdminPWLastUpdated(Date adminPWLastUpdated) {
		this.adminPWLastUpdated = adminPWLastUpdated;
	}

	/**
	 * @return the adminOrganization
	 */
	public String getAdminOrganization() {
		return adminOrganization;
	}

	/**
	 * @param adminOrganization the adminOrganization to set
	 */
	public void setAdminOrganization(String adminOrganization) {
		this.adminOrganization = adminOrganization;
	}

	/**
	 * @return the adminDepartment
	 */
	public String getAdminDepartment() {
		return adminDepartment;
	}

	/**
	 * @param adminDepartment the adminDepartment to set
	 */
	public void setAdminDepartment(String adminDepartment) {
		this.adminDepartment = adminDepartment;
	}

	/**
	 * @return the adminTeam
	 */
	public String getAdminTeam() {
		return adminTeam;
	}

	/**
	 * @param adminTeam the adminTeam to set
	 */
	public void setAdminTeam(String adminTeam) {
		this.adminTeam = adminTeam;
	}

	/**
	 * @return the adminPosition
	 */
	public String getAdminPosition() {
		return adminPosition;
	}

	/**
	 * @param adminPosition the adminPosition to set
	 */
	public void setAdminPosition(String adminPosition) {
		this.adminPosition = adminPosition;
	}

	/**
	 * @return the adminManagementLevel
	 */
	public Integer getAdminManagementLevel() {
		return adminManagementLevel;
	}

	/**
	 * @param adminManagementLevel the adminManagementLevel to set
	 */
	public void setAdminManagementLevel(Integer adminManagementLevel) {
		this.adminManagementLevel = adminManagementLevel;
	}

	/**
	 * @return the adminLock
	 */
	public Boolean getAdminLock() {
		return adminLock;
	}

	/**
	 * @param adminLock the adminLock to set
	 */
	public void setAdminLock(Boolean adminLock) {
		this.adminLock = adminLock;
	}

	/**
	 * @return the adminLoginFailCount
	 */
	public Integer getAdminLoginFailCount() {
		return adminLoginFailCount;
	}

	/**
	 * @param adminLoginFailCount the adminLoginFailCount to set
	 */
	public void setAdminLoginFailCount(Integer adminLoginFailCount) {
		this.adminLoginFailCount = adminLoginFailCount;
	}

	/**
	 * @return the adminLoginFailDate
	 */
	public Date getAdminLoginFailDate() {
		return adminLoginFailDate;
	}

	/**
	 * @param adminLoginFailDate the adminLoginFailDate to set
	 */
	public void setAdminLoginFailDate(Date adminLoginFailDate) {
		this.adminLoginFailDate = adminLoginFailDate;
	}

	/**
	 * @return the adminProfileImage
	 */
	public MultipartFile getAdminProfileImage() {
		return adminProfileImage;
	}

	/**
	 * @param adminProfileImage the adminProfileImage to set
	 */
	public void setAdminProfileImage(MultipartFile adminProfileImage) {
		this.adminProfileImage = adminProfileImage;
	}

	/**
	 * @return the adminProfileImageRemove
	 */
	public boolean isAdminProfileImageRemove() {
		return adminProfileImageRemove;
	}

	/**
	 * @param adminProfileImageRemove the adminProfileImageRemove to set
	 */
	public void setAdminProfileImageRemove(boolean adminProfileImageRemove) {
		this.adminProfileImageRemove = adminProfileImageRemove;
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
	 * @return the team
	 */
	public Team getTeam() {
		return team;
	}

	/**
	 * @param team the team to set
	 */
	public void setTeam(Team team) {
		this.team = team;
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
	 * @return the adminRole
	 */
	public Role getAdminRole() {
		return adminRole;
	}

	/**
	 * @param adminRole the adminRole to set
	 */
	public void setAdminRole(Role adminRole) {
		this.adminRole = adminRole;
	}

	/**
	 * @return the adminRoleList
	 */
	public List<Role> getAdminRoleList() {
		if(adminRoleList != null){
			List<Role> list = new ArrayList<>();
			list.addAll(adminRoleList);
			return list;
		}
		return null;
		//return adminRoleList;
	}

	/**
	 * @param adminRoleList the adminRoleList to set
	 */
	public void setAdminRoleList(List<Role> adminRoleList) {
		if(adminRoleList != null){
			this.adminRoleList = new ArrayList<>();
			this.adminRoleList.addAll(adminRoleList);
		}
		//this.adminRoleList = adminRoleList;
	}

	/**
	 * @return the adminSiteRoleRelList
	 */
	public List<AdminSiteRoleRel> getAdminSiteRoleRelList() {
		/*if(this.adminSiteRoleRelList != null){
			List<AdminSiteRoleRel> list = new ArrayList<AdminSiteRoleRel>();
			list.addAll(this.adminSiteRoleRelList);
			return list;
		}
		return null;*/
		return adminSiteRoleRelList;
	}

	/**
	 * @param adminSiteRoleRelList the adminSiteRoleRelList to set
	 */
	public void setAdminSiteRoleRelList(List<AdminSiteRoleRel> adminSiteRoleRelList) {
		if(adminSiteRoleRelList != null){
			this.adminSiteRoleRelList = new ArrayList<AdminSiteRoleRel>();
			this.adminSiteRoleRelList.addAll(adminSiteRoleRelList);
		}
		//this.adminSiteRoleRelList = adminSiteRoleRelList;
	}

	/**
	 * 전화번호 결합
	 * @return
	 */
	public String getAdminTelJoin(){
		if( StringUtils.isNotBlank(this.getAdminTel1()) 
				&& StringUtils.isNotBlank(this.getAdminTel2())
				&& StringUtils.isNotBlank(this.getAdminTel3()) ){
			return this.getAdminTel1() + "-" + this.getAdminTel2() + "-" + this.getAdminTel3();
		} else {
			return null;
		}
	}
	
	/**
	 * 휴대번호번호 결합
	 * @return
	 */
	public String getAdminMobileJoin(){
		if( StringUtils.isNotBlank(this.getAdminMobile1()) 
				&& StringUtils.isNotBlank(this.getAdminMobile2())
				&& StringUtils.isNotBlank(this.getAdminMobile3()) ){
			return this.getAdminMobile1() + "-" + this.getAdminMobile2() + "-" + this.getAdminMobile3();
		} else {
			return null;
		}
	}
	
	/**
	 * 최고관리자 인지 확인하다.
	 * - id : admin
	 * - role : ROLE_SUPER_ADMIN
	 * @return 확인결과
	 */
	public boolean isSuperAdmin(){
		if( StringUtils.isBlank(this.getAdminId()) || this.getAdminRole() == null || StringUtils.isBlank(this.getAdminRole().getRoleCode()) ){
			return false;
		}
		
		//'admin' 계정인경우
		if( "admin".equals(this.getAdminId()) ){
			return true;
		}
		//role이 'ROLE_SUPER_ADMIN' 인경우
		for(String code : this.getAdminRole().getRoleCode().split(",") ){
			if("ROLE_SUPER_ADMIN".equals(code) ){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 비회원인지 확인한다. 롤 코드가 ROLE_GUEST 인지 확인함.
	 * @return
	 */
	
	public boolean isGuest(){
		//회원 아이디 없으면
		if( StringUtils.isBlank(this.getAdminId()) ){
			return true;
		}
		//회원 아이디 있는데 guest_ 로 시작하면
		if( StringUtils.isNotBlank(this.getAdminId()) && this.getAdminId().startsWith("guest_") ){
			return true;
		}
		if( this.getAdminRole() != null ){
			if( "ROLE_GUEST".equals(this.getAdminRole().getRoleCode()) ){
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
		if( this.getAdminRole() != null ){
			if( this.getAdminRole().isRoleAdmin() ){
				return true;
			}
			if( this.getAdminRole().getCapabilityList() != null && !this.getAdminRole().getCapabilityList().isEmpty() ){
				String capIdTarget = capId.trim();
				for( Capability cap : this.getAdminRole().getCapabilityList() ){
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
	


	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AdminMember other = (AdminMember) obj;
		if (adminId == null) {
			if (other.adminId != null)
				return false;
		} else if (!adminId.equals(other.adminId))
			return false;
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	/**
     * toString 메소드를 대치한다.
     */
	@Override
    public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
	
	/**
	 * XSS 방어용 문자열 필터링
	 */
	public void sanitizeValues() {
		
		this.setAdminName( AsaproUtils.getJsoupFilteredText(this.getAdminName(), Whitelist.none(), false, false) );
		this.setAdminAddress( AsaproUtils.getJsoupFilteredText(this.getAdminAddress(), Whitelist.none(), false, false) );
		this.setAdminAddressDetail( AsaproUtils.getJsoupFilteredText(this.getAdminAddressDetail(), Whitelist.none(), false, false) );
		this.setAdminFax( AsaproUtils.getJsoupFilteredText(this.getAdminFax(), Whitelist.none(), false, false) );
		this.setAdminOrganization( AsaproUtils.getJsoupFilteredText(this.getAdminOrganization(), Whitelist.none(), false, false) );
		this.setAdminDepartment( AsaproUtils.getJsoupFilteredText(this.getAdminDepartment(), Whitelist.none(), false, false) );
		this.setAdminZipcode( AsaproUtils.getJsoupFilteredText(this.getAdminZipcode(), Whitelist.none(), false, false) );
		
	}
	
}
