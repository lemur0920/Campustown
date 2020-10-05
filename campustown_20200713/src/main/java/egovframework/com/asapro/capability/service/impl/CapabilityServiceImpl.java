/**
 * 
 */
package egovframework.com.asapro.capability.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//import egovframework.com.asapro.board.service.BoardMapper;
import egovframework.com.asapro.capability.service.Capability;
import egovframework.com.asapro.capability.service.CapabilityMapper;
import egovframework.com.asapro.capability.service.CapabilitySearch;
import egovframework.com.asapro.capability.service.CapabilityService;
//import egovframework.com.asapro.role.service.RoleCap;
//import egovframework.com.asapro.role.service.RoleMapper;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

/**
 * 권한 관리 서비스 구현체
 * @author yckim
 * @since 2018. 5. 17.
 *
 */
@Service
public class CapabilityServiceImpl extends EgovAbstractServiceImpl implements CapabilityService{
	
//	@Autowired
//	private RoleMapper roleMapper;
	
//	@Autowired
//	private BoardMapper boardMapper;
	
	@Autowired
	private CapabilityMapper capabilityMapper;
	
	/**
	 * 권한목록을 조회한다.
	 */
	@Override
	public List<Capability> getCapabilityList(CapabilitySearch capabilitySearch) {
		return capabilityMapper.selectCapabilityList(capabilitySearch);
	}
	
	/**
	 * 권한목록 수량을 반환한다.
	 */
	@Override
	public int getCapabilityListTotal(CapabilitySearch capabilitySearch) {
		return capabilityMapper.selectCapabilityListTotal(capabilitySearch);
	}
	

	//권한 조회
	@Override
	public Capability getCapability(Capability capability) {
		return capabilityMapper.selectCapability(capability);
	}

	//권한 추가
	@Override
	public int insertCapability(Capability capability) {
		capability.setCapRegDate(new Date());
		return capabilityMapper.insertCapability(capability);
	}

	//권한 수정
	@Override
	public int updateCapability(Capability capability) {
		return capabilityMapper.updateCapability(capability);
	}

	//권한 삭제
	@Override
	public int deleteCapability(List<Capability> capabilitiyList) {
		int count = 0;
//		RoleCap roleCap = null;
		for(Capability capability : capabilitiyList){
			//권한-역할 매핑도 삭제
//			roleCap = new RoleCap();
//			roleCap.setCapId(capability.getCapId());
//			roleMapper.deleteRoleCapByCap(roleCap);

			count += capabilityMapper.deleteCapability(capability);
			//게시판 관리자쪽도 같이 손봐주자
			//boardMapper.deleteBcModeratorCap(capability);
		}		
		return count;
	}

	//권한 삭제
	@Override
	public int deleteCapability(String[] capIds) {
		List<Capability> list = new ArrayList<Capability>();  
		Capability capability = null;
		for(String capId : capIds){
			capability = new Capability();
			capability.setCapId(capId);
			list.add(capability);
		}
		return this.deleteCapability(list);
	}

	//역할의 권한목록
	@Override
	public List<Capability> getRoleCapabilityList(CapabilitySearch capabilitySearch) {
		return capabilityMapper.selectRoleCapabilityListByCap(capabilitySearch);
	}


}
