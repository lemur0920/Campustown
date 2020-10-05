/**
 * 
 */
package egovframework.com.asapro.organization.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import egovframework.com.asapro.organization.service.Department;
import egovframework.com.asapro.organization.service.DepartmentSearch;
import egovframework.com.asapro.organization.service.DepartmentView;
import egovframework.com.asapro.organization.service.DepartmentViewSearch;
import egovframework.com.asapro.organization.service.Organization;
import egovframework.com.asapro.organization.service.OrganizationMapper;
import egovframework.com.asapro.organization.service.OrganizationSearch;
import egovframework.com.asapro.organization.service.OrganizationService;
import egovframework.com.asapro.organization.service.Staff;
import egovframework.com.asapro.organization.service.StaffSearch;
import egovframework.com.asapro.organization.service.Team;
import egovframework.com.asapro.organization.service.TeamSearch;
import egovframework.com.asapro.support.util.AsaproUtils;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

/**
 * 조직관리 서비스
 * @author yckim
 * @since 2018. 5. 11.
 */
@Service
public class OrganizationServilceImpl extends EgovAbstractServiceImpl implements OrganizationService{

	@Autowired
	private OrganizationMapper organizationMapper;
	
	
	//=============================================
	/* Organization */
	/* Organization */
	/* Organization */
	//=============================================
	
	/**
	 * 기관 목록을 반환
	 */
	@Override
	public List<Organization> getOrganizationList(OrganizationSearch organizationSearch) {
		return organizationMapper.selectOrganizationList(organizationSearch);
	}
	
	/**
	 * 기관목록 총수량
	 */
	@Override
	public int getOrganizationListTotal(OrganizationSearch organizationSearch) {
		return organizationMapper.selectOrganizationListTotal(organizationSearch);
	}
	
	/**
	 * 기관을 등록한다.
	 */
	@Override
	public int insertOrganization(Organization organizationForm) {
		return organizationMapper.insertOrganization(organizationForm);
	}

	/**
	 * 기관정보를 조회한다.
	 */
	@Override
	public Organization getOrganization(Organization organization) {
		return organizationMapper.selectOrganization(organization);
	}

	/**
	 * 기관정보를 수정한다.
	 */
	@Override
	public int updateOrganization(Organization organizationForm) {
		return organizationMapper.updateOrganization(organizationForm);
	}
	
	/**
	 * 기관을 삭제한다.
	 */
	@Override
	public int deleteOrganizations(String[] orgIds) {
		int result = 0;
		for (String orgId : orgIds) {
			result += this.deleteOrganization(orgId);
			
			//기관에 소속된 부서도 모두 삭제
			//organizationMapper.deleteDepartmentByOrgId(orgId);
			//프로그램으로 처리하지않고 fk cascade on delete 걸어놓음
		}
		return result;
	}

	/**
	 * 기관을 삭제한다.
	 */
	@Override
	public int deleteOrganization(String orgId) {
		return organizationMapper.deleteOrganization(orgId);
	}

	/**
	 * 기관 순서 저장
	 */
	@Override
	public int updateOrganizationOrders(List<Organization> organizations) {
		int updated = 0;
		for( Organization organization : organizations ){
			updated += organizationMapper.updateOrganizationOrder(organization);
		}
		return updated;
	}
	
	//=============================================
	/* Department */
	/* Department */
	/* Department */
	//=============================================
		
	/**
	 * 부서목록을 조회한다.
	 */
	@Override
	public List<Department> getDepartmentList(DepartmentSearch departmentSearch) {
		return organizationMapper.selectDepartmentList(departmentSearch);
	}

	/**
	 * 부서목록 수량을 조회한다.
	 */
	@Override
	public int getDepartmentListTotal(DepartmentSearch departmentSearch) {
		return organizationMapper.selectDepartmentListTotal(departmentSearch);
	}

	
	/**
	 * 부서를 추가한다.
	 */
	@Override
	public int insertDepartment(Department department) {
		return organizationMapper.insertDepartment(department);
	}


	/**
	 * 부서를 조회한다.
	 */
	@Override
	public Department getDepartment(String depId) {
		Department departmentSearch = new Department();
		departmentSearch.setDepId(depId);
		return this.getDepartment(departmentSearch);
	}
	
	/**
	 * 부서를 조회한다.
	 */
	@Override
	public Department getDepartment(Department department){
		return organizationMapper.selectDepartment(department);
	}

	/**
	 * 부서를 수정한다.
	 */
	@Override
	public int updateDepartment(Department department) {
		return organizationMapper.updateDepartment(department);
	}

	/**
	 * 부서를 삭제한다.
	 */
	@Override
	public int deleteDepartment(String depId) {
		return organizationMapper.deleteDepartment(depId);
	}

	/**
	 * 부서 삭제 (복수개)
	 */
	@Override
	public int deleteDepartments(String[] depIds) {
		int deleted = 0;
		for(String depId : depIds){
			deleted += this.deleteDepartment(depId);
		}
		return deleted;
	}

	/**
	 * 부서순서수정
	 */
	@Override
	public int updateDepartmentOrders(List<Department> departmentList) {
		int updated = 0;
		for( Department department : departmentList ){
			updated += organizationMapper.updateDepartmentOrder(department);
		}
		return updated;
	}
	
	
	/**
	 * VIEW 개층구조의 기관,부서,팀의 목록을 반환한다.
	 * -
	 * 부서 목록을 조회한다.
	 */
	@Override
	public List<DepartmentView> getDepartmenListInView(DepartmentViewSearch departmentViewSearch) {
		List<DepartmentView> allDepartmentList = new ArrayList<DepartmentView>();
		
		List<DepartmentView> departmentList = organizationMapper.selectDepartmentListInView(departmentViewSearch);
		List<DepartmentView> startupList = organizationMapper.selectStartupListInView(departmentViewSearch);
		allDepartmentList.addAll(departmentList);
		allDepartmentList.addAll(startupList);
		return allDepartmentList;
	}
	
	
	/* Team */
	/* Team */
	/* Team */
	/* Team */
	
	
	/**
	 * 팀 목록을 반환한다.
	 */
	@Override
	public List<Team> getTeamList(TeamSearch teamSearch) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	
	
	
	
	/* Staff */
	/* Staff */
	/* Staff */
	/* Staff */
	/* Staff */
	
	//직원추가
	@Override
	public int insertStaff(Staff staff) {
		return organizationMapper.insertStaff(staff);
	}

	//직원목록
	@Override
	public List<Staff> getStaffList(StaffSearch staffSearch) {
		return organizationMapper.selectStaffList(staffSearch);
	}
	
	//직원목록 토탈
	@Override
	public int getStaffListTotal(StaffSearch staffSearch){
		return organizationMapper.selectStaffListTotal(staffSearch);
	}

	//직원조회
	@Override
	public Staff getStaff(Integer stIdx) {
		return organizationMapper.selectStaff(stIdx);
	}

	//직원수정
	@Override
	public int updateStaff(Staff staff) {
		return organizationMapper.updateStaff(staff);
	}

	//직원삭제
	@Override
	public int deleteStaff(Integer stIdx) {
		return organizationMapper.deleteStaff(stIdx);
	}

	//직원삭제 - 복수개
	@Override
	public int deleteStaffs(Integer[] stIdxs) {
		int deleted = 0;
		for( Integer stIdx : stIdxs ){
			deleted += this.deleteStaff(stIdx);
		}
		return deleted;
	}

	//직원순서수정
	@Override
	public int updateStaffOrders(List<Staff> staffList) {
		int updated = 0;
		for( Staff staff : staffList ){
			updated += organizationMapper.updateStaffOrder(staff);
		}
		return updated;
	}









}
