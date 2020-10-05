/**
 * 
 */
package egovframework.com.asapro.capability.service;

import java.util.List;

/**
 * 권한정보 관리 서비스
 * @author yckim
 * @since 2018. 5. 17.
 *
 */
public interface CapabilityService {
	
	/**
	 * 권한정보 목록을 반환한다.
	 * @param capabilitySearch
	 * @return 권한정보 목록
	 */
	public List<Capability> getCapabilityList(CapabilitySearch capabilitySearch);
	
	/**
	 * 권한목록 총 수량을 반환한다.
	 * @param capabilitySearch
	 * @return 권한목록 수량
	 */
	public int getCapabilityListTotal(CapabilitySearch capabilitySearch);
	
	/**
	 * 권한정보를 조회한다.
	 * @param capId
	 * @return 권한
	 */
	public Capability getCapability(Capability capability);
	
	/**
	 * 권한정보를 추가한다.
	 * @param capability
	 * @return
	 */
	public int insertCapability(Capability capability);
	
	/**
	 * 권한정보를 수정한다
	 * @param capability
	 * @return
	 */
	public int updateCapability(Capability capability);
	
	/**
	 * 권한을 삭제한다.
	 * @param capIds
	 * @return
	 */
	public int deleteCapability(List<Capability> capabilityList);
	
	/**
	 * 권한을 삭제한다.
	 * @param capIds
	 * @return
	 */
	public int deleteCapability(String[] capIds);

	/**
	 * 역할의 권한목록을 조회한다.
	 * @param capabilitySearch
	 * @return 역할의 권한 목록
	 */
	public List<Capability> getRoleCapabilityList(CapabilitySearch capabilitySearch);

}
