/**
 * 
 */
package egovframework.com.asapro.organization.service;

import java.util.List;

/**
 * 조직관리 서비스
 * @author yckim
 * @since 2018. 5. 11.
 */
public interface OrganizationService {
	
	//=============================================
	/* Organization */
	/* Organization */
	/* Organization */
	//=============================================
	
	/**
	 * 기관목록을 반환한다.
	 * @param organizationSearch
	 * @return 기관목록
	 */
	public List<Organization> getOrganizationList(OrganizationSearch organizationSearch);
	

	/**
	 * 기관목록 총 수량을 반환한다.
	 * @param organizationSearch
	 * @return 기관목록 수량
	 */
	public int getOrganizationListTotal(OrganizationSearch organizationSearch);

	/**
	 * 기관을 등록한다.
	 * @param organizationForm
	 * @return 등록결과
	 */
	public int insertOrganization(Organization organizationForm);

	/**
	 * 기관정보를 조회한다.
	 * @param organizationForm
	 * @return 기관정보
	 */
	public Organization getOrganization(Organization organization);

	/**
	 * 기관정보를 수정한다.
	 * @param organizationForm
	 * @return 수정결과
	 */
	public int updateOrganization(Organization organizationForm);

	/**
	 * 기관을 삭제한다.
	 * @param orgIds
	 * @return 삭제결과
	 */
	public int deleteOrganizations(String[] orgIds);
	
	/**
	 * 기관을 삭제한다.
	 * @param orgId
	 * @return 삭제결과
	 */
	public int deleteOrganization(String orgId);
	
	/**
	 * 기관의 정렬순서를 변경한다.
	 * @param organizations
	 * @return 변경결과
	 */
	public int updateOrganizationOrders(List<Organization> organizations);
	
	
	
	//=============================================
	/* Department */
	/* Department */
	/* Department */
	//=============================================
	
	/**
	 * 부서 목록을 조회한다.
	 * @param departmentSearch
	 * @return 부서 목록
	 */
	public List<Department> getDepartmentList(DepartmentSearch departmentSearch);
	
	/**
	 * 부서 목록 수량을 조회한다.
	 * @param departmentSearch
	 * @return 부서목록 수량
	 */
	public int getDepartmentListTotal(DepartmentSearch departmentSearch);
	
	/**
	 * 부서를 추가한다.
	 * @param department
	 * @return 추가 결과
	 */
	public int insertDepartment(Department department);
	
	/**
	 * 부서를 조회한다.
	 * @param depId
	 * @return 부서정보
	 */
	public Department getDepartment(String depId);
	
	/**
	 * 부서를 조회한다.
	 * @param department
	 * @return 부서정보
	 */
	public Department getDepartment(Department department);
	
	/**
	 * 부서를 수정한다.
	 * @param department
	 * @return 수정결과
	 */
	public int updateDepartment(Department department);
	
	/**
	 * 부서를 삭제한다.
	 * @param depId
	 * @return 삭제결과
	 */
	public int deleteDepartment(String depId);
	
	/**
	 * 부서를 삭제한다.(복수개)
	 * @param depIds
	 * @return
	 */
	public int deleteDepartments(String[] depIds);
	
	/**
	 * 부서 순서를 수정한다.(복수개)
	 * @param department
	 * @return
	 */
	public int updateDepartmentOrders(List<Department> departmentList);
	
	
	/**
	 * VIEW 개층구조의 기관,부서,팀의 목록을 반환한다.
	 * -
	 * 부서 목록을 조회한다.
	 * @param departmentViewSearch
	 * @return 부서 목록
	 */
	public List<DepartmentView> getDepartmenListInView(DepartmentViewSearch departmentViewSearch);
	
	
	/* Team */
	/* Team */
	/* Team */
	/* Team */
	
	/**
	 * 팀목록을 반환한다.
	 * @param teamSearch
	 * @return 팀 목록
	 */
	public List<Team> getTeamList(TeamSearch teamSearch);
	
	
	
	
	/* Staff */
	/* Staff */
	/* Staff */
	/* Staff */
	/* Staff */
	
	/**
	 * 직원을 추가한다.
	 * @param staff
	 * @return 추가결과
	 */
	public int insertStaff(Staff staff);
	
	/**
	 * 직원 목록을 조회한다.
	 * @param staffSearch
	 * @return 직원 목록
	 */
	public List<Staff> getStaffList(StaffSearch staffSearch);
	
	/**
	 * 직원 목록 토탈을 조회한다.
	 * @param staffSearch
	 * @return 직원 목록 토탈
	 */
	public int getStaffListTotal(StaffSearch staffSearch);
	
	/**
	 * 직원정보를 조회한다.
	 * @param stIdx
	 * @return 직원정보
	 */
	public Staff getStaff(Integer stIdx);
	
	/**
	 * 직원정보를 수정한다.
	 * @param staff
	 * @return 수정결과
	 */
	public int updateStaff(Staff staff);
	
	/**
	 * 직원정보를 삭제한다.
	 * @param stIdx
	 * @return 삭제결과
	 */
	public int deleteStaff(Integer stIdx);
	
	/**
	 * 직원정보를 삭제한다.(복수개)
	 * @param stIdxs
	 * @return
	 */
	public int deleteStaffs(Integer[] stIdxs);
	
	/**
	 * 직원순서를 수정한다.(복수개)
	 * @param staff
	 * @return
	 */
	public int updateStaffOrders(List<Staff> staffList);


	


	












	
}
