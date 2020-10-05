/**
 * 
 */
package egovframework.com.asapro.capability.service;

import java.util.List;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * 권한 매퍼
 * @author yckim
 * @since 2018. 5. 17.
 *
 */
@Mapper
public interface CapabilityMapper {
	
	/**
	 * 권한 목록을 조회한다.
	 * @param capabilitySearch
	 * @return 권한 목록
	 */
	public List<Capability> selectCapabilityList(CapabilitySearch capabilitySearch);

	/**
	 * 권한목록 수량을 반환한다.
	 * @param capabilitySearch
	 * @return 권한목록 수량
	 */
	public int selectCapabilityListTotal(CapabilitySearch capabilitySearch);
	
	/**
	 * 권한을 조회한다.
	 * @param capId
	 * @return 권한
	 */
	public Capability selectCapability(Capability capability);
	
	/**
	 * 권한을 추가한다.
	 * @param capability
	 * @return 추가 개수
	 */
	public int insertCapability(Capability capability);
	
	/**
	 * 권한을 수정한다.
	 * @param capability
	 * @return 수정 개수
	 */
	public int updateCapability(Capability capability);
	
	/**
	 * 권한을 삭제한다.
	 * @param capId
	 * @return 삭제 개수
	 */
	public int deleteCapability(Capability capability);
	
	/**
	 * 역할의 권한을 조회한다.
	 * @param capabilitySearch
	 * @return 역할의 권한
	 */
	public List<Capability> selectRoleCapabilityListByCap(CapabilitySearch capabilitySearch);

	
}
