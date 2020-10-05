/**
 * 
 */
package egovframework.com.asapro.member.user.service;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.jsoup.safety.Whitelist;
import org.springframework.web.multipart.MultipartFile;

import egovframework.com.asapro.code.service.Code;
import egovframework.com.asapro.member.admin.service.AdminSiteRoleRel;
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
 * 사용자 VO
 * @author yckim
 * @since 2020. 2. 24.
 *
 */
@SuppressWarnings("serial")
public class UserMember extends MultiSiteVO implements Serializable{
	
	private String userId = "";	//회원 아이디
	private String userPassword = "";	//회원 비밀번호
	private String userPasswordCheck = "";	//회원 비밀번호 확인 - 컬럼 없음
	private String userLoginType; // 회원 로그인 type
	private String userName = "";	//회원 성명
	private String userThumbnailImage = ""; // 회원 썸네일 이미지
	private String userSex = "";	//회원 성별 - 코드
	private String userEmail = "";	//회원 이메일
	private String userTel1 = "";	//회원 전화번호1
	private String userTel2 = "";	//회원 전화번호2
	private String userTel3 = "";	//회원 전화번호3
	private String userMobile1 = "";	//회원 휴대폰번호1
	private String userMobile2 = "";	//회원 휴대폰번호2
	private String userMobile3 = "";	//회원 휴대폰번호3
	private String userZipcode = "";	//회원 우편번호
	private String userAddress = "";	//회원 주소
	private String userAddressDetail ="";	//회원 상세주소
	private Date userRegdate;	//가입일시
	private boolean userActive = true;	//회원 상태
	private Date userLoginLastDate;	//마지막 로그인 일시
	private Date userPWLastUpdated;	//비밀번호 마지막 변경일시
	private String userOrganization = "";	//소속 기관코드
	private String userDepartment = "";	//소속 부서코드
	private String userTeam = "";	//소속 팀코드
	private String userPosition = "";	//직급코드
	private String dupInfo = "";	//본인인증, ipin 인증의 중복가입여부정부, sns로그인시 access token 값 정보
	private Boolean userMailing;	//메일링 가입여부
	private Boolean userSms;	//문자수진동의여부
	private String userSignUpKey = "";	//가입확인키
	private String userSignUpKeyChecked = "";	//가입확인 완료 구분자
	private String userSns = "";	//SNS회원구분
	private String userSnsId = "";	//SNS아이디
	private String userSnsHome = "";	//SNS홈 URL
	private Date termCheckDate;	//약관재동의 일짜
	private Boolean termCheckEmailSended = false;	//약관재동의 메일 발송여부
	
	private Role userRole;	//역할
	private Organization organization;	//조직
	private Department department;	//부서
	private Team team;	//팀
	private Code position;	//직급/직책
	
	private List<AdminSiteRoleRel> adminSiteRoleRelList;	//관리자 사이트별 역할 릴레이션 목록
	
//	private List<Group> groups;
	




	
	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * @return the userPassword
	 */
	public String getUserPassword() {
		return userPassword;
	}

	/**
	 * @param userPassword the userPassword to set
	 */
	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	/**
	 * @return the userPasswordCheck
	 */
	public String getUserPasswordCheck() {
		return userPasswordCheck;
	}

	/**
	 * @param userPasswordCheck the userPasswordCheck to set
	 */
	public void setUserPasswordCheck(String userPasswordCheck) {
		this.userPasswordCheck = userPasswordCheck;
	}

	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * @return the userSex
	 */
	public String getUserSex() {
		return userSex;
	}

	/**
	 * @param userSex the userSex to set
	 */
	public void setUserSex(String userSex) {
		this.userSex = userSex;
	}

	/**
	 * @return the userEmail
	 */
	public String getUserEmail() {
		return userEmail;
	}

	/**
	 * @param userEmail the userEmail to set
	 */
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	/**
	 * @return the userTel1
	 */
	public String getUserTel1() {
		return userTel1;
	}

	/**
	 * @param userTel1 the userTel1 to set
	 */
	public void setUserTel1(String userTel1) {
		this.userTel1 = userTel1;
	}

	/**
	 * @return the userTel2
	 */
	public String getUserTel2() {
		return userTel2;
	}

	/**
	 * @param userTel2 the userTel2 to set
	 */
	public void setUserTel2(String userTel2) {
		this.userTel2 = userTel2;
	}

	/**
	 * @return the userTel3
	 */
	public String getUserTel3() {
		return userTel3;
	}

	/**
	 * @param userTel3 the userTel3 to set
	 */
	public void setUserTel3(String userTel3) {
		this.userTel3 = userTel3;
	}

	/**
	 * @return the userMobile1
	 */
	public String getUserMobile1() {
		return userMobile1;
	}

	/**
	 * @param userMobile1 the userMobile1 to set
	 */
	public void setUserMobile1(String userMobile1) {
		this.userMobile1 = userMobile1;
	}

	/**
	 * @return the userMobile2
	 */
	public String getUserMobile2() {
		return userMobile2;
	}

	/**
	 * @param userMobile2 the userMobile2 to set
	 */
	public void setUserMobile2(String userMobile2) {
		this.userMobile2 = userMobile2;
	}

	/**
	 * @return the userMobile3
	 */
	public String getUserMobile3() {
		return userMobile3;
	}

	/**
	 * @param userMobile3 the userMobile3 to set
	 */
	public void setUserMobile3(String userMobile3) {
		this.userMobile3 = userMobile3;
	}

	/**
	 * @return the userZipcode
	 */
	public String getUserZipcode() {
		return userZipcode;
	}

	/**
	 * @param userZipcode the userZipcode to set
	 */
	public void setUserZipcode(String userZipcode) {
		this.userZipcode = userZipcode;
	}

	/**
	 * @return the userAddress
	 */
	public String getUserAddress() {
		return userAddress;
	}

	/**
	 * @param userAddress the userAddress to set
	 */
	public void setUserAddress(String userAddress) {
		this.userAddress = userAddress;
	}

	/**
	 * @return the userAddressDetail
	 */
	public String getUserAddressDetail() {
		return userAddressDetail;
	}

	/**
	 * @param userAddressDetail the userAddressDetail to set
	 */
	public void setUserAddressDetail(String userAddressDetail) {
		this.userAddressDetail = userAddressDetail;
	}

	/**
	 * @return the userRegdate
	 */
	public Date getUserRegdate() {
		return userRegdate;
	}

	/**
	 * @param userRegdate the userRegdate to set
	 */
	public void setUserRegdate(Date userRegdate) {
		this.userRegdate = userRegdate;
	}

	/**
	 * @return the userActive
	 */
	public boolean isUserActive() {
		return userActive;
	}

	/**
	 * @param userActive the userActive to set
	 */
	public void setUserActive(boolean userActive) {
		this.userActive = userActive;
	}

	/**
	 * @return the userLoginLastDate
	 */
	public Date getUserLoginLastDate() {
		return userLoginLastDate;
	}

	/**
	 * @param userLoginLastDate the userLoginLastDate to set
	 */
	public void setUserLoginLastDate(Date userLoginLastDate) {
		this.userLoginLastDate = userLoginLastDate;
	}

	/**
	 * @return the userPWLastUpdated
	 */
	public Date getUserPWLastUpdated() {
		return userPWLastUpdated;
	}

	/**
	 * @param userPWLastUpdated the userPWLastUpdated to set
	 */
	public void setUserPWLastUpdated(Date userPWLastUpdated) {
		this.userPWLastUpdated = userPWLastUpdated;
	}

	/**
	 * @return the userOrganization
	 */
	public String getUserOrganization() {
		return userOrganization;
	}

	/**
	 * @param userOrganization the userOrganization to set
	 */
	public void setUserOrganization(String userOrganization) {
		this.userOrganization = userOrganization;
	}

	/**
	 * @return the userDepartment
	 */
	public String getUserDepartment() {
		return userDepartment;
	}

	/**
	 * @param userDepartment the userDepartment to set
	 */
	public void setUserDepartment(String userDepartment) {
		this.userDepartment = userDepartment;
	}

	/**
	 * @return the userPosition
	 */
	public String getUserPosition() {
		return userPosition;
	}

	/**
	 * @param userPosition the userPosition to set
	 */
	public void setUserPosition(String userPosition) {
		this.userPosition = userPosition;
	}

	/**
	 * @return the dupInfo
	 */
	public String getDupInfo() {
		return dupInfo;
	}

	/**
	 * @param dupInfo the dupInfo to set
	 */
	public void setDupInfo(String dupInfo) {
		this.dupInfo = dupInfo;
	}

	/**
	 * @return the userMailing
	 */
	public Boolean getUserMailing() {
		return userMailing;
	}

	/**
	 * @param userMailing the userMailing to set
	 */
	public void setUserMailing(Boolean userMailing) {
		this.userMailing = userMailing;
	}

	/**
	 * @return the userSms
	 */
	public Boolean getUserSms() {
		return userSms;
	}

	/**
	 * @param userSms the userSms to set
	 */
	public void setUserSms(Boolean userSms) {
		this.userSms = userSms;
	}

	/**
	 * @return the userSignUpKey
	 */
	public String getUserSignUpKey() {
		return userSignUpKey;
	}

	/**
	 * @param userSignUpKey the userSignUpKey to set
	 */
	public void setUserSignUpKey(String userSignUpKey) {
		this.userSignUpKey = userSignUpKey;
	}

	/**
	 * @return the userSignUpKeyChecked
	 */
	public String getUserSignUpKeyChecked() {
		return userSignUpKeyChecked;
	}

	/**
	 * @param userSignUpKeyChecked the userSignUpKeyChecked to set
	 */
	public void setUserSignUpKeyChecked(String userSignUpKeyChecked) {
		this.userSignUpKeyChecked = userSignUpKeyChecked;
	}

	/**
	 * @return the userSns
	 */
	public String getUserSns() {
		return userSns;
	}

	/**
	 * @param userSns the userSns to set
	 */
	public void setUserSns(String userSns) {
		this.userSns = userSns;
	}

	/**
	 * @return the userSnsId
	 */
	public String getUserSnsId() {
		return userSnsId;
	}

	/**
	 * @param userSnsId the userSnsId to set
	 */
	public void setUserSnsId(String userSnsId) {
		this.userSnsId = userSnsId;
	}

	/**
	 * @return the userSnsHome
	 */
	public String getUserSnsHome() {
		return userSnsHome;
	}

	/**
	 * @param userSnsHome the userSnsHome to set
	 */
	public void setUserSnsHome(String userSnsHome) {
		this.userSnsHome = userSnsHome;
	}

	/**
	 * @return the termCheckDate
	 */
	public Date getTermCheckDate() {
		return termCheckDate;
	}

	/**
	 * @param termCheckDate the termCheckDate to set
	 */
	public void setTermCheckDate(Date termCheckDate) {
		this.termCheckDate = termCheckDate;
	}

	/**
	 * @return the termCheckEmailSended
	 */
	public Boolean getTermCheckEmailSended() {
		return termCheckEmailSended;
	}

	/**
	 * @param termCheckEmailSended the termCheckEmailSended to set
	 */
	public void setTermCheckEmailSended(Boolean termCheckEmailSended) {
		this.termCheckEmailSended = termCheckEmailSended;
	}

	/**
	 * @return the userRole
	 */
	public Role getUserRole() {
		return userRole;
	}

	/**
	 * @param userRole the userRole to set
	 */
	public void setUserRole(Role userRole) {
		this.userRole = userRole;
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
	 * @return the userLoginType
	 */
	public String getUserLoginType() {
		return userLoginType;
	}

	/**
	 * @param userLoginType the userLoginType to set
	 */
	public void setUserLoginType(String userLoginType) {
		this.userLoginType = userLoginType;
	}

	/**
	 * @return the userThumbnailImage
	 */
	public String getUserThumbnailImage() {
		return userThumbnailImage;
	}

	/**
	 * @param userThumbnailImage the userThumbnailImage to set
	 */
	public void setUserThumbnailImage(String userThumbnailImage) {
		this.userThumbnailImage = userThumbnailImage;
	}
	
	/**
	 * @return the userTeam
	 */
	public String getUserTeam() {
		return userTeam;
	}

	/**
	 * @param userTeam the userTeam to set
	 */
	public void setUserTeam(String userTeam) {
		this.userTeam = userTeam;
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
	 * @return the adminSiteRoleRelList
	 */
	public List<AdminSiteRoleRel> getAdminSiteRoleRelList() {
		return adminSiteRoleRelList;
	}

	/**
	 * @param adminSiteRoleRelList the adminSiteRoleRelList to set
	 */
	public void setAdminSiteRoleRelList(List<AdminSiteRoleRel> adminSiteRoleRelList) {
		this.adminSiteRoleRelList = adminSiteRoleRelList;
	}

	//=====================================================================================	
	/**
	 * 전화번호 결합
	 * @return
	 */
	public String getUserTelJoin(){
		if( StringUtils.isNotBlank(this.getUserTel1()) 
				&& StringUtils.isNotBlank(this.getUserTel2())
				&& StringUtils.isNotBlank(this.getUserTel3()) ){
			return this.getUserTel1() + "-" + this.getUserTel2() + "-" + this.getUserTel3();
		} else {
			return null;
		}
	}
	
	/**
	 * 휴대번호번호 결합
	 * @return
	 */
	public String getUserMobileJoin(){
		if( StringUtils.isNotBlank(this.getUserMobile1()) 
				&& StringUtils.isNotBlank(this.getUserMobile2())
				&& StringUtils.isNotBlank(this.getUserMobile3()) ){
			return this.getUserMobile1() + "-" + this.getUserMobile2() + "-" + this.getUserMobile3();
		} else {
			return null;
		}
	}
	
	/**
	 * 비회원인지 확인한다. 롤 코드가 ROLE_GUEST 인지 확인함.
	 * @return
	 */
	
	public boolean isGuest(){
		//회원 아이디 없으면
		if( StringUtils.isBlank(this.getUserId()) ){
			return true;
		}
		//회원 아이디 있는데 guest_ 로 시작하면
		if( StringUtils.isNotBlank(this.getUserId()) && this.getUserId().startsWith("guest_") ){
			return true;
		}
		if( this.getUserRole() != null ){
			if( "ROLE_GUEST".equals(this.getUserRole().getRoleCode()) ){
				return true;
			}
		} else {
			throw new AsaproException("[ASAPRO] CurrentUser is NULL.");
		}
		return false;
	}
	
	
	/**
	 * 회원이 특정권한(Capability)를 가지고 있는지 체크한다.
	 * @param capId
	 * @return 체크결과
	 */
	public boolean hasCapability(String capId){
		if( this.getUserRole() != null ){
			if( this.getUserRole().isRoleAdmin() ){
				return true;
			}
			if( this.getUserRole().getCapabilityList() != null && !this.getUserRole().getCapabilityList().isEmpty() ){
				String capIdTarget = capId.trim();
				for( Capability cap : this.getUserRole().getCapabilityList() ){
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
		UserMember other = (UserMember) obj;
		if (userId == null) {
			if (other.userId != null)
				return false;
		} else if (!userId.equals(other.userId))
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
		
		this.setUserName( AsaproUtils.getJsoupFilteredText(this.getUserName(), Whitelist.none(), false, false) );
		this.setUserAddress( AsaproUtils.getJsoupFilteredText(this.getUserAddress(), Whitelist.none(), false, false) );
		this.setUserAddressDetail( AsaproUtils.getJsoupFilteredText(this.getUserAddressDetail(), Whitelist.none(), false, false) );
		this.setUserOrganization( AsaproUtils.getJsoupFilteredText(this.getUserOrganization(), Whitelist.none(), false, false) );
		this.setUserDepartment( AsaproUtils.getJsoupFilteredText(this.getUserDepartment(), Whitelist.none(), false, false) );
		this.setUserZipcode( AsaproUtils.getJsoupFilteredText(this.getUserZipcode(), Whitelist.none(), false, false) );
		
	}
	
}
