/**
 * 
 */
package egovframework.com.asapro.member.admin.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Date;
import java.util.List;

import javax.mail.MessagingException;


/**
 * 관리자서비스
 * @author yckim
 * @since 2018. 4. 26.
 *
 */
public interface AdminMemberService {
	
	/**
	 * 관리자정보를 추가한다.
	 * @param adminMember
	 * @return PK 관리자아이디
	 * @throws FileNotFoundException 
	 */
	public String insertAdminMember(AdminMember adminMember);
	
	/**
	 * 관리자정보를 조회한다.
	 * @param adminMember
	 * @return 관리자정보
	 */
	public AdminMember getAdminMember(AdminMember adminMember);
	
	/**
	 * 관리자아이디로 관리자정보를 조회한다.
	 * @param adminId
	 * @return 관리자정보
	 */
	public AdminMember getAdminMember(String adminId);
	
	/**
	 * 관리자목록을 조회한다.
	 * @param adminMemberSearch
	 * @return 관리자 목록
	 */
	public List<AdminMember> getAdminMemberList(AdminMemberSearch adminMemberSearch);
	
	/**
	 * 관리자 목록 총개수를 조회한다.
	 * @param adminMemberSearch
	 * @return 관리자목록 총 개수
	 */
	public int getAdminMemberListTotal(AdminMemberSearch adminMemberSearch);
	
	/**
	 * 관리자정보를 수정한다.
	 * @param adminMember
	 * @return 관리자정보
	 * @throws FileNotFoundException 
	 */
	public int updateAdminMember(AdminMember adminMember);
	
	/**
	 * 관리자정보를 삭제한다.
	 * @param adminId
	 * @return 삭제결과
	 */
	public int deleteAdminMember(String adminId);
	
	/**
	 * 복수의 관리자을 삭제한다.
	 * @param adminIds
	 * @return 삭제결과
	 */
	public int deleteAdminMember(String[] adminIds);

	/**
	 * 비밀번호를 변경한다.
	 * @param passwordForm
	 * @return 변경 결과
	 */
	public int updateAdminMemberPassword(AdminPassword adminPasswordForm);
	
	/**
	 * 마지막 로그인 일시를 업데이트 한다.
	 * @param adminMember
	 * @return 수정 결과
	 */
	public int updateAdminMemberLastLoginDate(AdminMember adminMember);

	/**
	 * 계정의 잠김여부를 반환한다.
	 * @param loginId
	 * @return 잠김여부
	 */
	public boolean isLock(String loginId);
	
	/**
	 * 로그인 실패 누적데이터 리셋
	 * <pre>
	 * - 잠김 여부
	 * - 실패 횟수
	 * - 마지막 실패 일시
	 * </pre>
	 * @param adminMember
	 */
	public int resetLoginFailInfo(AdminMember adminMember);

	/**
	 * 로그인 실패 횟수 증가
	 * @param loginId
	 */
	public int addLoginFailCount(String loginId);

	/**
	 * 로그인 실패 회수 조회
	 * @param loginId
	 * @return 실패횟수
	 */
	public int getLoginFailCount(String loginId);
	
	/**
	 * 계정 잠김 처리, 잠김 일시 등록
	 * @param loginId
	 * @return 수정결과
	 */
	public int updateLockAndFailDate(String loginId);
	
	/**
	 * 로그인 마지막 실패 일시를 조회한다.
	 * @param loginId
	 * @return 실패일시
	 */
	public Date getLoginFailDate(String loginId);
	
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
