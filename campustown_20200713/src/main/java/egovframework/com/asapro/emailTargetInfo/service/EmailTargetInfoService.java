/**
 * 
 */
package egovframework.com.asapro.emailTargetInfo.service;

import java.util.List;

/**
 * 메일대상 서비스
 * @author yckim
 * @since 2019. 12. 11.
 *
 */
public interface EmailTargetInfoService {
	
	/**
	 * 메일대상정보 목록을 조회한다.
	 * @param emailTargetInfoSearch
	 * @return 메일대상 목록
	 */
	public List<EmailTargetInfo> getEmailTargetInfoList(EmailTargetInfoSearch emailTargetInfoSearch);
	
	/**
	 * 메일대상정보 갯수를 조회한다.
	 * @param emailTargetInfoSearch
	 * @return 메일대상 갯수
	 */
	public int getEmailTargetInfoListTotal(EmailTargetInfoSearch emailTargetInfoSearch);

	/**
	 * 메일대상정보를 추가한다.
	 * @param emailTargetInfoForm
	 * @return 등록결과
	 */
	public int insertEmailTargetInfo(EmailTargetInfo emailTargetInfoForm);

	/**
	 * 메일대상정보를 조회한다.
	 * @param emailTargetInfoForm
	 * @return 메일대상정보
	 */
	public EmailTargetInfo getEmailTargetInfo(EmailTargetInfo emailTargetInfoForm);

	/**
	 * 메일대상정보를 수정한다.
	 * @param emailTargetInfoForm
	 * @return 수정결과
	 */
	public int updateEmailTargetInfo(EmailTargetInfo emailTargetInfoForm);

	/**
	 * 메일대상정보를 삭제한다.
	 * @param emailTargetInfoList
	 * @return 삭제결과
	 */
	public int deleteEmailTargetInfo(List<EmailTargetInfo> emailTargetInfoList);
	
	/**
	 * 메일대상정보를 삭제한다.
	 * @param emailTargetInfo
	 * @return 삭제결과
	 */
	public int deleteEmailTargetInfo(EmailTargetInfo emailTargetInfo);
	

}
