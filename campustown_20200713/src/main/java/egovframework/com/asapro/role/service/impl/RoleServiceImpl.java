/**
 * 
 */
package egovframework.com.asapro.role.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import egovframework.com.asapro.capability.service.Capability;
import egovframework.com.asapro.capability.service.CapabilityMapper;
import egovframework.com.asapro.capability.service.CapabilitySearch;
import egovframework.com.asapro.member.admin.service.AdminMember;
import egovframework.com.asapro.role.service.Role;
import egovframework.com.asapro.role.service.RoleCap;
import egovframework.com.asapro.role.service.RoleMapper;
import egovframework.com.asapro.role.service.RoleSearch;
import egovframework.com.asapro.role.service.RoleService;
import egovframework.com.asapro.support.Constant;
import egovframework.com.asapro.support.util.AsaproUtils;
import egovframework.com.asapro.support.util.EtcUtil;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

/**
 * 역할 관리
 * @author yckim
 * @since 2018. 5. 21.
 *
 */
@Service
public class RoleServiceImpl extends EgovAbstractServiceImpl implements RoleService{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(RoleServiceImpl.class);
	
	@Autowired
	private RoleMapper roleMapper;
	
	@Autowired
	private CapabilityMapper capabilityMapper;
	
	/**
	 * 역할 목록 조회
	 */
	@Override
	public List<Role> getRoleList(RoleSearch roleSearch) {
		List<Role> roleList = roleMapper.selectRoleList(roleSearch);
		return roleList;
	}
	
	/**
	 * 역할목록 수량 조회
	 */
	@Override
	public int getRoleListTotal(RoleSearch roleSearch) {
		return roleMapper.selectRoleListTotal(roleSearch);
	}

	/**
	 * 역할 조회
	 */
	@Override
	public Role getRole(Role role) {
		Role roleDB = roleMapper.selectRole(role);
		
		return roleDB;
	}

	/**
	 * 역할 추가
	 */
	@Override
	public int insertRole(Role roleForm) {
		roleForm.setRoleRegDate(new Date());
		//roleForm.setRoleDefault(false);
		roleForm.setRoleAdmin(false);
		
		
		//가입시 역할로 선택되었으면 기존 가입시 역할로 선택된 역할 조회
		Role joinRole = null;
		if(roleForm.isRoleJoin()){
			joinRole = roleMapper.selectJoinRole();
		}
		
		//새로 등록한 역할 추가
		int result = roleMapper.insertRole(roleForm);
		
		//역할추가 등록성공시
		if(result > 0){
			//가입시 역할로 선택되었으면 기존 가입시역할은 무효화
			if(joinRole != null){
				joinRole.setRoleJoin(false);
				roleMapper.updateRole(joinRole);
			}
			
			//롤에 묶여질 권한을 폼에서 넘어온걸로 입력
			RoleCap newRoleCapItem = null;
			List<Capability> list = roleForm.getCapabilityList();

			if(list != null){
				for(Capability capability : list){
					if(StringUtils.isNotBlank(capability.getCapId())){
						newRoleCapItem = new RoleCap();
						newRoleCapItem.setRoleCode(roleForm.getRoleCode());
						newRoleCapItem.setCapId(capability.getCapId());
						roleMapper.insertRoleCap(newRoleCapItem);
					}
				}
			}
		}
		
				
		return result;
	}

	/**
	 * 역할 수정
	 */
	@Override
	public int updateRole(Role roleForm) {
		
		//가입시 역할로 선택되었으면 기존 가입시 역할로 선택된 역할 조회
		Role joinRole = null;
		if(roleForm.isRoleJoin()){
			joinRole = roleMapper.selectJoinRole();
		}
		
		//역할정보 수정
		int result = roleMapper.updateRole(roleForm);
		
		//역할추가 수정 성공시
		if(result > 0){
			
			//가입시 역할로 선택되었으면 기존 가입시역할은 무효화
			if(joinRole != null){
				joinRole.setRoleJoin(false);
				roleMapper.updateRole(joinRole);
			}
			
			//기존 역할-권한 릴레이션 전체 삭제
			RoleCap roleCap = new RoleCap();
			roleCap.setRoleCode(roleForm.getRoleCode());
			roleMapper.deleteRoleCapByRole(roleCap);
			
			//폼에서 넘어온걸로 새로 입력
			RoleCap newRoleCapItem = null;
			List<Capability> list = roleForm.getCapabilityList();
			if(list != null){
				for(Capability capability : list){
					if(StringUtils.isNotBlank(capability.getCapId())){
						newRoleCapItem = new RoleCap();
						newRoleCapItem.setRoleCode(roleForm.getRoleCode());
						newRoleCapItem.setCapId(capability.getCapId());
						roleMapper.insertRoleCap(newRoleCapItem);
					}
				}
			}
			
		}
				
		return result;
	}
	
	/**
	 * 역할 삭제
	 */
	@Override
	public int deleteRole(List<Role> roleList) {
		int count = 0;
		RoleCap roleCap = null;
		Map<String, String> map = null;
		
		//가입시의 역할 조회
		Role joinRole = roleMapper.selectJoinRole();
		String joinRoleCode = null; 
		if(joinRole != null){
			joinRoleCode = joinRole.getRoleCode();
		}
		joinRoleCode = StringUtils.defaultString(joinRoleCode, "ROLE_NORMAL_ADMIN");
		
		for(Role role : roleList){
			//역할없어지는 회원의 역할을 가입시역할로 조정
			map = new HashMap<String, String>();
			map.put("roleCode", role.getRoleCode());
			map.put("joinRoleCode", joinRoleCode);
			roleMapper.updateMemberRolesToJoinRole(map);
			
			//권한-역할 매핑도 삭제
			roleCap = new RoleCap();
			roleCap.setRoleCode(role.getRoleCode());
			roleMapper.deleteRoleCapByRole(roleCap);
			count += roleMapper.deleteRole(role);
		}
		return count;
	}

	/**
	 * 역할 삭제
	 */
	@Override
	public int deleteRole(String[] roleCodes) {
		List<Role> list = new ArrayList<Role>();
		Role role = null;
		for(String roleCode : roleCodes){
			role = new Role();
			role.setRoleCode(roleCode);
			list.add(role);
		}
		return this.deleteRole(list);
	}
	
	/**
	 * 현재 관리자가 접근할 수 있는 리소스인지 체크한다.
	 */
	@Override
	public boolean isCurrentAdminAccessibleResource(AdminMember currentAdmin, String requestUri, Map<String, String[]> parameterMap, String httpMethod) {
		
		boolean result = false;
		
		//api 또는 모듈내의 다른 모듈의 검색을 레이어로 검색하는경우 걍 패스
		if( requestUri.contains(Constant.API_PATH) || requestUri.endsWith("searchLayer.do") ){
			return true;
		}
				
		if( currentAdmin == null ){
			return result;
		}
		
		LOGGER.warn("[ASAPRO] currentAdmin : {}", currentAdmin.getAdminId());
		LOGGER.warn("[ASAPRO] currentAdmin Role : {}", currentAdmin.getAdminRole().getRoleCode());
		LOGGER.warn("[ASAPRO] requestUri : {}", requestUri);
		
		//현재 관리자의 롤이 가지고 있는 권한
		Role currentAdminRole = currentAdmin.getAdminRole();
		
		//최고관리자는 모든 권한 ok
		if( currentAdmin.isSuperAdmin() ){
			LOGGER.info("[ASAPRO] CURRENT ADMIN IS SUPER.");
			return true;
		}
				
		//========== 시작 : 관리자단 접속가능 권한은 우선 체크 ==========
		//우선 관리자단인지 확인
		Capability adminAccessible = new Capability();
		adminAccessible.setCapId("CAP_ADMIN_ACCESS");
		adminAccessible = capabilityMapper.selectCapability(adminAccessible);
		
		//mybatis association 이나 collection 은 1단계까지만 처리하기로 했으므로 유저에서 꺼낸 역할(Role)에는 권한(Capability)목록은 비어있음
		//권한목록은 디비에서 새로 조회해와야 함
		//=> 다중 역할 부여로 바꾸면서 권한을 담고있음
		//currentAdminRole = roleMapper.selectRole(currentAdminRole);
		List<Capability> currentAdminRoleCapList = currentAdminRole.getCapabilityList();
		
		//관리자단 접속가능(CAP_ADMIN_ACCESS) 체크는 따로함.
		if( AsaproUtils.isAdminPath(requestUri) ){	//관리자 uri인지
			//정규식 체크 - capValue값이 정규식임
			if( requestUri.matches(adminAccessible.getCapValue()) ){	//uri가 관리자접속 권한의 value에 매칭되는지
				if( !currentAdminRoleCapList.contains(adminAccessible) ){	//현제관리자의 권한이 접속권한을 가지고 있지 않으면
					LOGGER.debug("[ASAPRO] CAPABILITY NEEDED : CAP_ADMIN_ACCESS");
					return false;
				}
			} else {
				LOGGER.debug("[ASAPRO] CAPABILITY NEEDED : CAP_ADMIN_ACCESS, CAPABILITY VALUE NOT MATCHED : {}", adminAccessible.getCapValue());
				return false;
			}
		}
		//========== 끝 : 관리자단 접속가능 권한은 우선 체크 ==========
		
		//========== 시작 : 현재 접속자가 요청한 주소에 접속 가능한 권한을 가지고 있는지 체크 ==========
		CapabilitySearch capabilitySearch = new CapabilitySearch();
		capabilitySearch.setPaging(false);
		List<Capability> allCaps = capabilityMapper.selectCapabilityList(capabilitySearch);
		
		//capPriority 값이 큰 권한이 앞에 오도록 정렬
		//범위가 작은 권한이 priority값이 더 크므로 먼저 체크된다.
		Collections.sort(allCaps, new Comparator<Capability>() {
			@Override
			public int compare(Capability cap1, Capability cap2) {
				if(cap1 != null && cap2 != null){
					if(cap1.getCapPriority() > cap2.getCapPriority()){
						return -1;
					} else if(cap1.getCapPriority() < cap2.getCapPriority()){
						return 1;
					} else {
						return 0;
					}
				} else {
					return -1;
				}
			}
		});
		
		//현재 주소(리소스)가 어떤 권한(Capability) 에 해당하는지 구한다.
		List<Capability> neededCaps = new ArrayList<Capability>();
		//Capability neededCap = null;
		for(Capability cap : allCaps){
			if( isCapMatched(requestUri, parameterMap, httpMethod, cap) ){
				LOGGER.debug("[ASAPRO] requestUri : {}, REQUIRED CAPABILITY : {}", requestUri, cap.getCapId());
				//neededCap = cap;
				neededCaps.add(cap);
				//break LOOP;
			} 
		}
		
		//if( neededCap == null ){
		if( neededCaps.size() == 0 ){
			//등록안된 자원이다 - 권한 체크 안함 -> 중요
			LOGGER.debug("[ASAPRO] NOT REGISTERED RESOURCE. WELCOME...(ONLY REGISTERED URIs ARE BEING CHECKED!!)");
			return true;
		} else {
			//사용자가 가지고 있는 권한을 capPriority 값이 작은 권한이 앞에 오도록 재정렬해주어야 한다. 
			Collections.sort(currentAdminRoleCapList, new Comparator<Capability>() {
				@Override
				public int compare(Capability cap1, Capability cap2) {
					if(cap1 != null && cap2 != null ){
						if(cap1.getCapPriority() > cap2.getCapPriority()){
							return 1;
						} else if(cap1.getCapPriority() < cap2.getCapPriority()){
							return -1;
						} else {
							return 0;
						}
					} else {
						return -1;
					}
				}
			});
			
			for( Capability cap : currentAdminRoleCapList ){
				if( isCapMatched(requestUri, parameterMap, httpMethod, cap) ){
					LOGGER.debug("[ASAPRO] CURRENT ADMIN HAS MATCHING CAPABILITY [{}]", cap.getCapId());
					result = true;
				}
			}
		}
		//========== 끝 : 현재 접속자가 요청한 주소에 접속 가능한 권한을 가지고 있는지 체크 ==========
		
		if(!result){
			for( Capability neededCap : neededCaps ){
				LOGGER.debug("[ASAPRO] CURRENT ADMIN DOES NOT HAVE THE CAPABILITY[{}].", neededCap.getCapId());
			}
		}
		
		return result;
	}

	/**
	 * 주어진 조건에 권한이 부합하는지 체크
	 * @param requestUri
	 * @param parameterMap
	 * @param httpMethod
	 * @param cap
	 * @return
	 */
	private boolean isCapMatched(String requestUri, Map<String, String[]> parameterMap, String httpMethod, Capability cap) {
		
		//관리자 접속권한은 체크하지 않는다.
		if( "CAP_ADMIN_ACCESS".equals(cap.getCapId()) ){
			return false;
		}
		
		boolean isCapMatched = true;
		
		//http메소드 체크 
		if( !"ALL".equals(cap.getCapHttpMethod()) && StringUtils.isNotBlank(httpMethod) ){
			// GET 이거나 POST 이면
			isCapMatched = httpMethod.trim().equalsIgnoreCase(cap.getCapHttpMethod().trim());
			LOGGER.debug("[ASAPRO] httpMethod : {}, capMethod : {}, httpMethod match ? {}", new Object[]{httpMethod, cap.getCapHttpMethod(), isCapMatched});
		}
		
		//넘겨받은 파라미터는 없는데 권한에 파라미터 조건이 있을경우는 권한을 주지 않는다.
		if(StringUtils.isNotBlank(cap.getCapParam1Key()) || StringUtils.isNotBlank(cap.getCapParam2Key()) ){
			
			if( parameterMap != null && !parameterMap.isEmpty() ){
				if( isCapMatched ){
					
					//추가파라메터2 체크
					if( StringUtils.isNotBlank(cap.getCapParam2Key()) ){
						if( !ArrayUtils.isEmpty( parameterMap.get(cap.getCapParam2Key())) ) {
							if( StringUtils.isNotBlank( ArrayUtils.toString(parameterMap.get(cap.getCapParam2Key())) ) ){
								isCapMatched = isCapMatched & cap.getCapParam2Value().equals( StringUtils.join(parameterMap.get(cap.getCapParam2Key()), "") );
								LOGGER.debug("[ASAPRO] param2 key : {}, param2 Value : {}, param2 match ? {}", new Object[]{cap.getCapParam2Key(), StringUtils.join(parameterMap.get(cap.getCapParam2Key()), ""), isCapMatched});
							}
						} else {
							isCapMatched = false;
							LOGGER.debug("[ASAPRO] param2 key : {}, param2 Value : {}, param2 match ? {}", new Object[]{cap.getCapParam2Key(), StringUtils.join(parameterMap.get(cap.getCapParam2Key()), ""), isCapMatched});
						}
					}
					
					//추가파라메터1 체크
					if( StringUtils.isNotBlank(cap.getCapParam1Key()) ){
						if( !ArrayUtils.isEmpty( parameterMap.get(cap.getCapParam1Key())) ) {
							if( StringUtils.isNotBlank( ArrayUtils.toString(parameterMap.get(cap.getCapParam2Key())) ) ){
								isCapMatched = isCapMatched & cap.getCapParam1Value().equals( StringUtils.join(parameterMap.get(cap.getCapParam1Key()), "") );
								LOGGER.debug("[ASAPRO] param1 key : {}, param1 Value : {}, param1 match ? {}", new Object[]{cap.getCapParam1Key(), StringUtils.join(parameterMap.get(cap.getCapParam1Key()), ""), isCapMatched});
							}
						} else {
							isCapMatched = false;
							LOGGER.debug("[ASAPRO] param1 key : {}, param1 Value : {}, param1 match ? {}", new Object[]{cap.getCapParam1Key(), StringUtils.join(parameterMap.get(cap.getCapParam1Key()), ""), isCapMatched});
						}
					}
				}
				
			} else {
				isCapMatched = false;
			}
		}
		
		//capValue체크(uri체크)
		if( isCapMatched ){
			isCapMatched = isCapMatched & requestUri.matches(cap.getCapValue());
			LOGGER.debug("[ASAPRO] requestUri : {}, capValue : {},  requestUri match ? {}", new Object[]{requestUri, cap.getCapValue(), isCapMatched});
		}
		return isCapMatched;
	}
	
	/**
	 * 가입시 역할 조회 - 설정 안되어 있으면 ROLE_NORMAL_ADMIN로...
	 */
	@Override
	public Role getJoinRole() {
		Role joinRole = roleMapper.selectJoinRole();
		if(joinRole == null){
			LOGGER.warn("[ASAPRO] RoleServiceImpl join role(signup role) was not found, ROLE_NORMAL_ADMIN was set instead...");
			joinRole = new Role();
			joinRole.setRoleCode("ROLE_NORMAL_ADMIN");
		}
		return joinRole; 
	}
	
	/**
	 * 특정 권한을 가진 역할 목록 
	 */
	@Override
	public List<Role> getRoleWithCapList(String capId) {
		return roleMapper.selectRoleWithCapList(capId);
	}

	/**
	 * 해당 역할이 특정 권한을 가지고 있는지 확인한다. 
	 */
	@Override
	public boolean hasCapability(String roleCode, String capId) {
		if(StringUtils.isBlank(roleCode) || StringUtils.isBlank(capId) ){
			return false;
		}
		
		CapabilitySearch capabilitySearch = new CapabilitySearch();
		capabilitySearch.setPaging(false);
		capabilitySearch.setRoleCode(roleCode);
		List<Capability> capList = capabilityMapper.selectRoleCapabilityListByCap(capabilitySearch);
		
		if(capList != null && !capList.isEmpty() ){
			for (Capability capability : capList) {
				if("CAP_MEMBER_ADMIN_BY_ORG".equals(capability.getCapId()) ){
					return true;
				}
			}
		} 
		return false;
	}

}
