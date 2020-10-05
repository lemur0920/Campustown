/**
 * 
 */
package egovframework.com.asapro.allowmac.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import egovframework.com.asapro.allowmac.service.AllowMac;
import egovframework.com.asapro.allowmac.service.AllowMacMapper;
import egovframework.com.asapro.allowmac.service.AllowMacSearch;
import egovframework.com.asapro.allowmac.service.AllowMacService;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

/**
 * 접속허용 Mac Address 관리 서비스 구현
 * @author yckim
 * @since 2019. 3. 26.
 *
 */
@Service
public class AllowMacServiceImpl extends EgovAbstractServiceImpl implements AllowMacService{
	
	@Autowired
	private AllowMacMapper allowMacMapper;

	/**
	 * 접속허용/차단 Mac Address 목록을 반환한다.
	 */
	@Override
	public List<AllowMac> getAllowMacList(AllowMacSearch allowMacSearch) {
		return allowMacMapper.selectAllowMacList(allowMacSearch);
	}

	/**
	 * 접속허용/차단 Mac Address 토탈을 반환한다.
	 */
	@Override
	public int getAllowMacListTotal(AllowMacSearch allowMacSearch) {
		return allowMacMapper.selectAllowMacListTotal(allowMacSearch);
	}

	/**
	 * 접속허용/차단 Mac Address 를 추가한다.
	 */
	@Override
	public int insertAllowMac(AllowMac allowMacForm) {
		allowMacForm.setMacRegDate(new Date());
		return allowMacMapper.insertAllowMac(allowMacForm);
	}

	/**
	 * 접속허용/차단 Mac Address 를 조회한다.
	 */
	@Override
	public AllowMac getAllowMac(AllowMac allowMacForm) {
		return allowMacMapper.selectAllowMac(allowMacForm);
	}

	/**
	 * 접속허용/차단 Mac Address 를 수정한다.
	 */
	@Override
	public int updateAllowMac(AllowMac allowMacForm) {
		return allowMacMapper.updateAllowMac(allowMacForm);
	}

	/**
	 * 접속허용/차단 Mac Address 를 삭제한다.
	 */
	@Override
	public int deleteAllowMac(List<AllowMac> allowMacLis) {
		int deleted = 0;
		for( AllowMac allowMac : allowMacLis ){
			deleted += this.deleteAllowMac(allowMac);
		}
		return deleted;
	}

	/**
	 * 접속허용/차단 Mac Address 를 삭제한다.
	 */
	@Override
	public int deleteAllowMac(AllowMac allowMac) {
		return allowMacMapper.deleteAllowMac(allowMac);
	}
	

}