/**
 * 
 */
package egovframework.com.asapro.emailTargetInfo.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import egovframework.com.asapro.emailTargetInfo.service.EmailTargetInfo;
import egovframework.com.asapro.emailTargetInfo.service.EmailTargetInfoMapper;
import egovframework.com.asapro.emailTargetInfo.service.EmailTargetInfoSearch;
import egovframework.com.asapro.emailTargetInfo.service.EmailTargetInfoService;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

/**
 * 메일대상 서비스 구현
 * @author yckim
 * @since 2019. 12. 11.
 *
 */
@Service
public class EmailTargetInfoServiceImpl extends EgovAbstractServiceImpl implements EmailTargetInfoService{
	
	@Autowired
	private EmailTargetInfoMapper emailTargetInfoMapper;

	/**
	 * 메일대상정보 목록을 조회한다.
	 */
	@Override
	public List<EmailTargetInfo> getEmailTargetInfoList(EmailTargetInfoSearch emailTargetInfoSearch) {
		return emailTargetInfoMapper.selectEmailTargetInfoList(emailTargetInfoSearch);
	}
	
	/**
	 * 메일대상정보 갯수를 조회한다.
	 */
	@Override
	public int getEmailTargetInfoListTotal(EmailTargetInfoSearch emailTargetInfoSearch) {
		return emailTargetInfoMapper.selectEmailTargetInfoListTotal(emailTargetInfoSearch);
	}

	/**
	 * 메일대상정보를 추가한다.
	 */
	@Override
	public int insertEmailTargetInfo(EmailTargetInfo emailTargetInfoForm) {
		return emailTargetInfoMapper.insertEmailTargetInfo(emailTargetInfoForm);
	}

	/**
	 * 메일대상정보를 조회한다.
	 */
	@Override
	public EmailTargetInfo getEmailTargetInfo(EmailTargetInfo emailTargetInfoForm) {
		return emailTargetInfoMapper.selectEmailTargetInfo(emailTargetInfoForm);
	}

	/**
	 * 메일대상정보를 수정한다.
	 */
	@Override
	public int updateEmailTargetInfo(EmailTargetInfo emailTargetInfoForm) {
		return emailTargetInfoMapper.updateEmailTargetInfo(emailTargetInfoForm);
	}

	/**
	 * 메일대상정보를 삭제한다.
	 */
	@Override
	public int deleteEmailTargetInfo(List<EmailTargetInfo> emailTargetInfoList) {
		int deleted = 0;
		for( EmailTargetInfo emailTargetInfo : emailTargetInfoList ){
			deleted += this.deleteEmailTargetInfo(emailTargetInfo);
		}
		return deleted;
	}

	/**
	 * 메일대상정보를 삭제한다.
	 */
	@Override
	public int deleteEmailTargetInfo(EmailTargetInfo emailTargetInfo) {
		return emailTargetInfoMapper.deleteEmailTargetInfo(emailTargetInfo);
	}
	

}