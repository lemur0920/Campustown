/**
 * 
 */
package egovframework.com.asapro.role.service;

import java.util.List;
import java.util.Map;

import egovframework.com.asapro.member.admin.service.AdminMember;

/**
 * 역할관리 서비스
 * @author yckim
 * @since 2018. 5. 21.
 *
 */
public interface RoleService {
	
	/**
	 * 역할 목록을 조회한다.
	 * @param roleSearch
	 * @return 역할 목록
	 */
	public List<Role> getRoleList(RoleSearch roleSearch);

	/**
	 * 역할 목록 수를 조회한다.
	 * @param roleSearch
	 * @return 역할목록 수
	 */
	public int getRoleListTotal(RoleSearch roleSearch);
	
	/**
	 * 역할을 조회한다.
	 * @param role
	 * @return 역할
	 */
	public Role getRole(Role role);
	
	/**
	 * 역할을 추가한다.
	 * @param role
	 * @return 추가 로우 개수
	 */
	public int insertRole(Role role);
	
	/**
	 * 역할을 수정한다.
	 * @param role
	 * @return 수정 로우 개스
	 */
	public int updateRole(Role role);
	
	/**
	 * 역할을 삭제한다.
	 * @param roleList
	 * @return 삭제 로우 개수
	 */
	public int deleteRole(List<Role> roleList);
	
	/**
	 * 역할을 삭제한다.
	 * @param roleCodes
	 * @return 삭제 개수
	 */
	public int deleteRole(String[] roleCodes);
	
	//=========================================================================
	//=========================================================================
	//=========================================================================
	
	/**
	 * 현재 사용자가 접속할 수 있는 리소스인지 체크한다.
	 * @param currentAdmin
	 * @param requestUrl
	 * @param method
	 * @return 체크 결과
	 */
	public boolean isCurrentAdminAccessibleResource(AdminMember currentAdmin, String requestUri, Map<String, String[]> parameterMap, String method);
	
	/**
	 * 가입시 역할을 조회한다.
	 * <pre>
	 * - Role 테이블에 가입시 역할이 세팅되어 있지 않으면 ROLE_NORMAL_ADMIN 를 임시로 반환한다.
	 * </pre>
	 */
	public Role getJoinRole();
	
	/**
	 * 특정 권한을 가지고 있는 역할목록을 조회한다.
	 * @param capId
	 * @return 역할 목록
	 */
	public List<Role> getRoleWithCapList(String capId);
	
	/**
	 * 해당 역할이 특정 권한을 가지고 있는지 확인한다. 
	 * @param roleCode
	 * @param capId
	 * @return 권한 여부
	 */
	public boolean hasCapability(String roleCode, String capId);

	
}
