/**
 * 
 */
package egovframework.com.asapro.member.admin.service;

import java.util.Date;
import java.util.List;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * 관리자 sql매퍼
 * @author yckim
 * @since 2018. 4. 27.
 *
 */
@Mapper
public interface AdminMemberMapper {
	
	/**
	 * 관리자정보를 추가한다.
	 * @param adminMember
	 * @return 추가 결과
	 */
	public int insertAdminMember(AdminMember adminMember);
	
	/**
	 * 리자정보를 조회한다.
	 * @param adminMember
	 * @return 관리자정보
	 */
	public AdminMember selectAdminMember(AdminMember adminMember);
	
	/**
	 * 관리자정보를 조회한다.
	 * @param adminMemberId
	 * @return 관리자정보
	 */
	public AdminMember selectAdminMemberById(String adminMemberId);
	
	/**
	 * 관리자목록을 조회한다.
	 * @param adminMemberSearch
	 * @return 관리자목록
	 */
	public List<AdminMember> selectAdminMemberList(AdminMemberSearch adminMemberSearch);
	
	/**
	 * 관리자목록 총개수를 조회한다.
	 * @param adminMemberSearch
	 * @return 총 개수
	 */
	public int selectAdminMemberListTotal(AdminMemberSearch adminMemberSearch);
	
	/**
	 * 관리자정보를 수정한다.
	 * <pre>
	 * - 개인식별키값은 수정하지 않는다.=> updateMemberDupInfo 메서드를 별도로 사용해야함
	 * </pre>
	 * @param adminMember
	 * @return 수정 결과
	 */
	public int updateAdminMember(AdminMember adminMember);
	
	/**
	 * 관리자아이디로 관리자정보를 삭제한다.
	 * @param adminId
	 * @return 삭제결과
	 */
	public int deleteAdminMember(String adminId);
	
	/**
	 * 관리자의 비밀번호를 변경한다.
	 * <ul>
	 * 	<li>비밀번호는 평문상태일것.</li>
	 * </ul>
	 * @param password
	 * @return 변경 결과
	 */
	public int updateAdminMemberPassword(AdminPassword adminPpassword);

	/**
	 * 마지막 로그인 일시를 수정한다.
	 * @param adminMember
	 * @return 수정결과
	 */
	public int updateAdminMemberLastLoginDate(AdminMember adminMember);
	
	//================================================================================
	//========================  관리자 사이트 역할 릴레이션 ===============================
	//================================================================================
	
	/**
	 * 관리자 사이트별 역할 릴레이션을 조회한다.
	 * @param adminMemberId
	 * @return 사이트별 역할 릴레이션
	 */
	public AdminSiteRoleRel selectAdminSiteRoleRelationList(String adminMemberId);

	/**
	 * 관리자 사이트별 역할 릴레이션을 추가한다.
	 * @param adminSiteRoleRel
	 * @return 추가결과
	 */
	public int insertAdminSiteRoleRel(AdminSiteRoleRel adminSiteRoleRel);
	
	/**
	 * 관리자 사이트별 역할 릴레이션을 삭제한다.
	 * @param adminMemberId
	 * @return 삭제결과
	 */
	public int deleteAdminSiteRoleRel(String adminMemberId);
	
	/**
	 * 관리자 사이트별 역할 릴레이션을 삭제한다.
	 * @param roleCode
	 * @return 삭제결과
	 */
	public int deleteAdminSiteRoleRelByRoleCode(String roleCode);
	
	/**
	 * 관리자 사이트별 역할 릴레이션을 삭제한다.
	 * @param sitePrefix
	 * @return 삭제결과
	 */
	public int deleteAdminSiteRoleRelBySitePrefix(String sitePrefix);

	/**
	 * 계정의 잠김여부를 반환한다.
	 * @param loginId
	 * @return 잠김여부
	 */
	public boolean selectAdminMemberLock(String loginId);
	
	/**
	 * 로그인 실패 누적데이터 리셋
	 * @param adminMember
	 * @return 리셋결과
	 */
	public int resetLoginFailInfo(AdminMember adminMember);
	
	/**
	 * 로그인 실패 횟수 증가
	 * @param loginId
	 * @return 증가결과
	 */
	public int addLoginFailCount(String loginId);
	
	/**
	 * 로그인 실패 회수 조회
	 * @param loginId
	 * @return 실패횟수
	 */
	public int selectLoginFailCount(String loginId);
	
	/**
	 * 계정 잠김 처리, 잠김 일시 등록
	 * @param adminMember
	 * @return 수정결과
	 */
	public int updateLockAndFailDate(AdminMember adminMember);
	
	/**
	 * 로그인 마지막 실패 일시를 조회한다.
	 * @param loginId
	 * @return 실패일시
	 */
	public Date selectLoginFailDate(String loginId);
	
	/**
	 * 관리자 상태를 수정한다.
	 * @param adminMemberForm
	 * @return 수정결과
	 */
	public int updateAdminMemberStatus(AdminMember adminMemberForm);
	
	
	
	//==============================================================================================================
	//=====================================  관리자 개인정보 수정  =================================================
	//==============================================================================================================
	
	/**
	 * 관리자 프로파일을 수정한다.
	 * @param adminMemberForm
	 * @return 수정결과
	 */
	public int updateAdminMemberProfile(AdminMember adminMemberForm);

}
