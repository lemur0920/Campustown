/**
 * 
 */
package egovframework.com.asapro.member.user.service;

import java.util.Date;
import java.util.List;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * 사용자 sql매퍼
 * @author yckim
 * @since 2020. 2. 24.
 *
 */
@Mapper
public interface UserMemberMapper {
	
	/**
	 * 사용자정보를 추가한다.
	 * @param userMember
	 * @return 추가 결과
	 */
	public int insertUserMember(UserMember userMember);
	
	/**
	 * 리자정보를 조회한다.
	 * @param userMember
	 * @return 사용자정보
	 */
	public UserMember selectUserMember(UserMember userMember);
	
	/**
	 * 사용자정보를 조회한다.
	 * @param userMemberId
	 * @return 사용자정보
	 */
	public UserMember selectUserMemberById(String userMemberId);
	
	/**
	 * 사용자목록을 조회한다.
	 * @param userMemberSearch
	 * @return 사용자목록
	 */
	public List<UserMember> selectUserMemberList(UserMemberSearch userMemberSearch);
	
	/**
	 * 사용자목록 총개수를 조회한다.
	 * @param userMemberSearch
	 * @return 총 개수
	 */
	public int selectUserMemberListTotal(UserMemberSearch userMemberSearch);
	
	/**
	 * 사용자정보를 수정한다.
	 * <pre>
	 * - 개인식별키값은 수정하지 않는다.=> updateMemberDupInfo 메서드를 별도로 사용해야함
	 * </pre>
	 * @param userMember
	 * @return 수정 결과
	 */
	public int updateUserMember(UserMember userMember);
	
	/**
	 * 사용자아이디로 사용자정보를 삭제한다.
	 * @param userId
	 * @return 삭제결과
	 */
	public int deleteUserMember(String userId);
	
	/**
	 * 사용자의 비밀번호를 변경한다.
	 * <ul>
	 * 	<li>비밀번호는 평문상태일것.</li>
	 * </ul>
	 * @param password
	 * @return 변경 결과
	 */
	public int updateUserMemberPassword(UserPassword userPpassword);

	/**
	 * 마지막 로그인 일시를 수정한다.
	 * @param userMember
	 * @return 수정결과
	 */
	public int updateUserMemberLastLoginDate(UserMember userMember);
	
	//================================================================================
	//========================  사용자 사이트 역할 릴레이션 ===============================
	//================================================================================
/*	
	*//**
	 * 사용자 사이트별 역할 릴레이션을 조회한다.
	 * @param userMemberId
	 * @return 사이트별 역할 릴레이션
	 *//*
	public UserSiteRoleRel selectUserSiteRoleRelationList(String userMemberId);

	*//**
	 * 사용자 사이트별 역할 릴레이션을 추가한다.
	 * @param userSiteRoleRel
	 * @return 추가결과
	 *//*
	public int insertUserSiteRoleRel(UserSiteRoleRel userSiteRoleRel);
	
*/	/**
	 * 사용자 사이트별 역할 릴레이션을 삭제한다.
	 * @param userMemberId
	 * @return 삭제결과
	 */
	public int deleteUserSiteRoleRel(String userMemberId);
	
	/**
	 * 사용자 사이트별 역할 릴레이션을 삭제한다.
	 * @param roleCode
	 * @return 삭제결과
	 */
	public int deleteUserSiteRoleRelByRoleCode(String roleCode);
	
	/**
	 * 사용자 사이트별 역할 릴레이션을 삭제한다.
	 * @param sitePrefix
	 * @return 삭제결과
	 */
	public int deleteUserSiteRoleRelBySitePrefix(String sitePrefix);

	/**
	 * 계정의 잠김여부를 반환한다.
	 * @param loginId
	 * @return 잠김여부
	 */
	public boolean selectUserMemberLock(String loginId);
	
	/**
	 * 로그인 실패 누적데이터 리셋
	 * @param userMember
	 * @return 리셋결과
	 */
	public int resetLoginFailInfo(UserMember userMember);
	
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
	 * @param userMember
	 * @return 수정결과
	 */
	public int updateLockAndFailDate(UserMember userMember);
	
	/**
	 * 로그인 마지막 실패 일시를 조회한다.
	 * @param loginId
	 * @return 실패일시
	 */
	public Date selectLoginFailDate(String loginId);
	
	/**
	 * 사용자 상태를 수정한다.
	 * @param userMemberForm
	 * @return 수정결과
	 */
	public int updateUserMemberStatus(UserMember userMemberForm);
	
	
	
	//==============================================================================================================
	//=====================================  사용자 개인정보 수정  =================================================
	//==============================================================================================================
	
	/**
	 * 사용자 프로파일을 수정한다.
	 * @param userMemberForm
	 * @return 수정결과
	 */
	public int updateUserMemberProfile(UserMember userMemberForm);

}
