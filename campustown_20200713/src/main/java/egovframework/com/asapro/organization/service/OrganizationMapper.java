/**
 * 
 */
package egovframework.com.asapro.organization.service;

import java.util.List;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * 조직관리 SQL 매퍼
 * 
 * @author yckim
 * @since 2018. 5. 14.
 */
@Mapper
public interface OrganizationMapper {

	//=============================================
	/* Organization */
	/* Organization */
	/* Organization */
	//=============================================
	/**
	 * 기관 목록을 반환을 반환한다.
	 * @param organizationSearch
	 * @return 기관목록
	 */
	public List<Organization> selectOrganizationList(OrganizationSearch organizationSearch);
	
	/**
	 * 기관 목록 총 수량을 반환한다.
	 * @param organizationSearch
	 * @return 기관목록 총 수량
	 */
	public int selectOrganizationListTotal(OrganizationSearch organizationSearch);
	
	/**
	 * 기관을 등록한다.
	 * @param organizationForm
	 * @return 기관들록 결과
	 */
	public int insertOrganization(Organization organizationForm);

	/**
	 * 기관정보를 조회한다.
	 * @param organization
	 * @return 기관정보
	 */
	public Organization selectOrganization(Organization organization);

	/**
	 * 기관정보를 수정한다.
	 * @param organizationForm
	 * @return 수정결과
	 */
	public int updateOrganization(Organization organizationForm);

	/**
	 * 기관을 삭제한다.
	 * @param orgId
	 * @return 삭제결과
	 */
	public int deleteOrganization(String orgId);
	
	/**
	 * 기관 수서를 변경한다.
	 * @param organization
	 * @return 변경결과
	 */
	public int updateOrganizationOrder(Organization organization);
	
	
	
	/* Department */
	/* Department */
	/* Department */
	/* Department */
	/* Department */
	
	/**
	 * 부서 목록을 조회한다.
	 * @param departmentSearch
	 * @return 부서 목록
	 */
	public List<Department> selectDepartmentList(DepartmentSearch departmentSearch);
	
	/**
	 * 부서목록 수량을 조회한다.
	 * @param departmentSearch
	 * @return 부서목록 수량
	 */
	public int selectDepartmentListTotal(DepartmentSearch departmentSearch);
	
	/**
	 * 부서를 추가한다.
	 * @param department
	 * @return 추가 결과
	 */
	public int insertDepartment(Department department);
	
	/**
	 * 부서를 조회한다.
	 * @param department
	 * @return 부서정보
	 */
	public Department selectDepartment(Department department);
	
	/**
	 * 부서를 수정한다.
	 * @param department
	 * @return 수정결과
	 */
	public int updateDepartment(Department department);
	
	/**
	 * 부서를 삭제한다. - (부서원 같이 삭제됨 fk cascade on delete 걸려있음)
	 * @param depId
	 * @return 삭제결과
	 */
	public int deleteDepartment(String depId);
	
	/**
	 * 부서 순서를 수정한다.
	 * @param department
	 * @return
	 */
	public int updateDepartmentOrder(Department department);
	
	
	/**
	 * VIEW 개층구조의 기관,부서,팀의 목록을 반환한다.
	 * -
	 * 부서 목록을 조회한다.
	 * @param departmentViewSearch
	 * @return 부서 목록
	 */
	public List<DepartmentView> selectDepartmentListInView(DepartmentViewSearch departmentViewSearch);
	
	/**
	 * VIEW 개층구조의 기관,부서,팀의 목록을 반환한다.
	 * -
	 * 창업팀 목록을 조회한다.
	 * @param departmentViewSearch
	 * @return 창업팀 목록
	 */
	public List<DepartmentView> selectStartupListInView(DepartmentViewSearch departmentViewSearch);
	
	
	
	
	
	
	
	
	
	
	
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
	public List<Staff> selectStaffList(StaffSearch staffSearch);
	
	/**
	 * 직원목록토탈을 조회한다.
	 * @param staffSearch
	 * @return
	 */
	public int selectStaffListTotal(StaffSearch staffSearch);
	
	/**
	 * 직원정보를 조회한다.
	 * @param stIdx
	 * @return 직원정보
	 */
	public Staff selectStaff(Integer stIdx);
	
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
	 * 직원순서를 수정한다.
	 * @param staff
	 * @return
	 */
	public int updateStaffOrder(Staff staff);


	








	


	
}
