/**
 * 
 */
package egovframework.com.asapro.allowip.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import egovframework.com.asapro.allowip.service.AllowIp;
import egovframework.com.asapro.allowip.service.AllowIpMapper;
import egovframework.com.asapro.allowip.service.AllowIpSearch;
import egovframework.com.asapro.allowip.service.AllowIpService;
import egovframework.com.asapro.allowip.service.AllowIpTemp;
import egovframework.com.asapro.allowip.service.AllowIpTempSearch;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

/**
 * 접속허용 IP 관리 서비스 구현
 * @author yckim
 * @since 2018. 8. 17.
 *
 */
@Service
public class AllowIpServiceImpl extends EgovAbstractServiceImpl implements AllowIpService{
	
	@Autowired
	private AllowIpMapper allowIpMapper;

	/**
	 * 접속허용/차단 IP 목록을 반환한다.
	 */
	@Override
	public List<AllowIp> getAllowIpList(AllowIpSearch allowIpSearch) {
		return allowIpMapper.selectAllowIpList(allowIpSearch);
	}

	/**
	 * 접속허용/차단 IP 토탈을 반환한다.
	 */
	@Override
	public int getAllowIpListTotal(AllowIpSearch allowIpSearch) {
		return allowIpMapper.selectAllowIpListTotal(allowIpSearch);
	}

	/**
	 * 접속허용/차단 IP 를 추가한다.
	 */
	@Override
	public int insertAllowIp(AllowIp allowIpForm) {
		allowIpForm.setIpRegDate(new Date());
		return allowIpMapper.insertAllowIp(allowIpForm);
	}

	/**
	 * 접속허용/차단 IP 를 조회한다.
	 */
	@Override
	public AllowIp getAllowIp(AllowIp allowIpForm) {
		return allowIpMapper.selectAllowIp(allowIpForm);
	}

	/**
	 * 접속허용/차단 IP 를 수정한다.
	 */
	@Override
	public int updateAllowIp(AllowIp allowIpForm) {
		return allowIpMapper.updateAllowIp(allowIpForm);
	}

	/**
	 * 접속허용/차단 IP 를 삭제한다.
	 */
	@Override
	public int deleteAllowIp(List<AllowIp> allowIpLis) {
		int deleted = 0;
		for( AllowIp allowIp : allowIpLis ){
			deleted += this.deleteAllowIp(allowIp);
		}
		return deleted;
	}

	/**
	 * 접속허용/차단 IP 를 삭제한다.
	 */
	@Override
	public int deleteAllowIp(AllowIp allowIp) {
		return allowIpMapper.deleteAllowIp(allowIp);
	}

	//======================================================================================================
	//========================================== 임시 접속허용 IP ===========================================
	//======================================================================================================
	
	/**
	 * 임시접속 허용 아이피정보를 등록한다.
	 */
	@Override
	public int insertAllowIpTemp(AllowIpTemp allowIpTempForm) {
		return allowIpMapper.insertAllowIpTemp(allowIpTempForm);
	}

	/**
	 * 임시접속 허용 아이피정보를 조회한다.
	 */
	@Override
	public AllowIpTemp getAllowIpTemp(AllowIpTemp allowIpTempForm) {
		return allowIpMapper.selectAllowIpTemp(allowIpTempForm);
	}

	/**
	 * 임시접속 허용 아이피정보를 삭제한다.
	 */
	@Override
	public int deleteAllowIpTemp(AllowIpTemp allowIpTempForm) {
		return allowIpMapper.deleteAllowIpTemp(allowIpTempForm);
	}

	/**
	 * 임시접속 허용 인증완료 처리를 한다.
	 */
	@Override
	public int updateAuthentication(AllowIpTemp allowIpTempForm) {
		return allowIpMapper.updateAuthentication(allowIpTempForm);
	}

	/**
	 * 아이피 인증 처리완료여부 확인
	 */
	@Override
	public Boolean isAuthentication(String rempoteIp) {
		return allowIpMapper.selectAuthentication(rempoteIp);
	}

	/**
	 * 임시접속 허용 아이피정보 토탈 수량을 반환한다.
	 */
	@Override
	public int getAllowIpTempListTotal(AllowIpTempSearch allowIpTempSearch) {
		return allowIpMapper.selectAllowIpTempListTotal(allowIpTempSearch);
	}

	/**
	 * 임시접속 허용 아이피정보 토탈 목록을 반환한다.
	 */
	@Override
	public List<AllowIpTemp> getAllowIpTempList(AllowIpTempSearch allowIpTempSearch) {
		return allowIpMapper.selectAllowIpTempList(allowIpTempSearch);
	}
	
	/**
	 * 임시접속 허용 아이피정보 모두 삭제한다.
	 */
	@Override
	public int deleteAllowIpTempAll() {
		return allowIpMapper.deleteAllowIpTempAll();
	}

	/**
	 * 인증번호를 수정한다.
	 */
	@Override
	public int updateCertifiNum(AllowIpTemp allowIpTempForm) {
		return allowIpMapper.updateCertifiNum(allowIpTempForm);
	}

	/**
	 * 인증실패 횟수 증가
	 */
	@Override
	public int updateAuthentiFailCnt(AllowIpTemp allowIpTempForm) {
		return allowIpMapper.updateAuthentiFailCnt(allowIpTempForm);
	}
	

}