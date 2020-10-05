/**
 * 
 */
package egovframework.com.asapro.emailTargetInfo.service;

import java.util.List;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * 메일대상 SQL매퍼
 * @author yckim
 * @since 2019. 12. 11.
 *
 */
@Mapper
public interface EmailTargetInfoMapper {

	/**
	 * 메일대상정보 목록을 조회한다.
	 * @param emailTargetInfoSearch
	 * @return 메일대상정보 목록
	 */
	public List<EmailTargetInfo> selectEmailTargetInfoList(EmailTargetInfoSearch emailTargetInfoSearch);
	
	/**
	 * 메일대상정보 갯수를 조회한다.
	 * @param emailTargetInfoSearch
	 * @return 메일대상 갯수
	 */
	public int selectEmailTargetInfoListTotal(EmailTargetInfoSearch emailTargetInfoSearch);

	/**
	 * 메일대상정보를 추가한다.
	 * @param emailTargetInfoForm
	 * @return 추가결과
	 */
	public int insertEmailTargetInfo(EmailTargetInfo emailTargetInfoForm);

	/**
	 * 메일대상정보를 조회한다.
	 * @param emailTargetInfoForm
	 * @return 메일대상정보
	 */
	public EmailTargetInfo selectEmailTargetInfo(EmailTargetInfo emailTargetInfoForm);

	/**
	 * 메일대상정보를 수정한다.
	 * @param emailTargetInfoForm
	 * @return 수정결과
	 */
	public int updateEmailTargetInfo(EmailTargetInfo emailTargetInfoForm);

	/**
	 * 메일대상정보를 삭제한다.
	 * @param emailTargetInfo
	 * @return 삭제결과
	 */
	public int deleteEmailTargetInfo(EmailTargetInfo emailTargetInfo);
	

}
