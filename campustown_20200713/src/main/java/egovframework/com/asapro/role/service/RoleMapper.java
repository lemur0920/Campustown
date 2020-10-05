/**
 * 
 */
package egovframework.com.asapro.role.service;

import java.util.List;
import java.util.Map;

import egovframework.com.asapro.capability.service.Capability;
import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * 역할 SQL 매퍼
 * @author yckim
 * @since 2018. 5. 21.
 *
 */
@Mapper
public interface RoleMapper {
	
	/**
	 * 역할 목록을 조회한다.
	 * @param roleSearch
	 * @return 역할 목록
	 */
	public List<Role> selectRoleList(RoleSearch roleSearch);

	/**
	 * 역할목록 수량을 조회한다.
	 * @param roleSearch
	 * @return 역할목록 수량
	 */
	public int selectRoleListTotal(RoleSearch roleSearch);
	
	/**
	 * 역할을 조회한다.
	 * @param role
	 * @return 역할
	 */
	public Role selectRole(Role role);
	
	/**
	 * 가입시 역할을 조회한다.(1개만 있어야 함)
	 * @return 가입시 역할
	 */
	public Role selectJoinRole();
	
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
	 * @param role
	 * @return 삭제 로우 개수
	 */
	public int deleteRole(Role role);
	
	/**
	 * 역할 코드로 권한목록을 조회한다.
	 * @param roleCode
	 * @return 권한목록
	 */
	public List<Capability> selectRoleCapabilityList(String roleCode);
	
	//======================================================
	//================  RoleCap ========================
	
	/**
	 * 역할과 권한 관계목록을 조회한다.
	 * @param roleForm
	 * @return 역할, 권한 관계 목록
	 */
	public List<RoleCap> selectRoleCapList(Role roleForm);

	/**
	 * 역할 권한 관계를 삭제한다.
	 * @param roleCap
	 */
	public int deleteRoleCapByRole(RoleCap roleCap);
	
	/**
	 * 역할 권한 관계를 삭제한다.
	 * @param roleCap
	 */
	public int deleteRoleCapByCap(RoleCap roleCap);

	/**
	 * 새 역할-권한 관계를 입력한다.
	 * @param newItem
	 */
	public void insertRoleCap(RoleCap roleCap);

	//===========================================
	//===========================================
	//===========================================
	/**
	 * 롤 삭제시 해당롤을 가지고 있던 회원의 롤을 가입시 롤로 변경한다.
	 * @param roleCode
	 */
	public void updateMemberRolesToJoinRole(Map<String, String> map);
	
	/**
	 * 특정 권한을 가지고 있는 역할 목록을 조회한다.
	 * @param capId
	 * @return 역할 목록
	 */
	public List<Role> selectRoleWithCapList(String capId);


}
