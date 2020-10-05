/**
 * 
 */
package egovframework.com.asapro.allowip.service;

import java.util.List;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * 접속허용 IP 관리 SQL매퍼
 * @author yckim
 * @since 2018. 4. 3.
 *
 */
@Mapper
public interface AllowIpMapper {

	/**
	 * 접속허용/차단 IP 목록을 반환한다.
	 * @param allowIpSearch
	 * @return 접속허용/차단 IP 목록
	 */
	public List<AllowIp> selectAllowIpList(AllowIpSearch allowIpSearch);

	/**
	 * 접속허용/차단 IP 토탈을 반환한다.
	 * @param allowIpSearch
	 * @return 접속허용/차단 IP 토탈
	 */
	public int selectAllowIpListTotal(AllowIpSearch allowIpSearch);

	/**
	 * 접속허용/차단 IP 를 추가한다.
	 * @param allowIpForm
	 * @return 추가 결과
	 */
	public int insertAllowIp(AllowIp allowIpForm);

	/**
	 * 접속허용/차단 IP 를 조회한다.
	 * @param allowIpForm
	 * @return 접속허용/차단 IP
	 */
	public AllowIp selectAllowIp(AllowIp allowIpForm);

	/**
	 * 접속허용/차단 IP 를 수정한다.
	 * @param allowIpForm
	 * @return 수정결과
	 */
	public int updateAllowIp(AllowIp allowIpForm);

	/**
	 * 접속허용/차단 IP 를 삭제한다.
	 * @param allowIp
	 * @return 삭제결과
	 */
	public int deleteAllowIp(AllowIp allowIp);

	//======================================================================================================
	//========================================== 임시 접속허용 IP ===========================================
	//======================================================================================================
	
	/**
	 * 임시접속 허용 아이피정보를 등록한다.
	 * @param allowIpTempForm
	 * @return 등록결과
	 */
	public int insertAllowIpTemp(AllowIpTemp allowIpTempForm);

	/**
	 * 임시접속 허용 아이피정보를 조회한다.
	 * @param allowIpTempForm
	 * @return 조회결과
	 */
	public AllowIpTemp selectAllowIpTemp(AllowIpTemp allowIpTempForm);

	/**
	 * 임시접속 허용 아이피정보를 삭제한다.
	 * @param allowIpTempForm
	 * @return 삭제결과
	 */
	public int deleteAllowIpTemp(AllowIpTemp allowIpTempForm);

	/**
	 * 임시접속 허용 인증완료 처리를 한다.
	 * @param allowIpTempForm
	 * @return 인증처리결과
	 */
	public int updateAuthentication(AllowIpTemp allowIpTempForm);

	/**
	 * 아이피 인증 처리완료여부 확인
	 * @param rempoteIp
	 * @return 인증여부
	 */
	public Boolean selectAuthentication(String rempoteIp);

	/**
	 * 임시접속 허용 아이피정보 토탈 수량을 반환한다
	 * @param allowIpTempSearch
	 * @return 토탈수량
	 */
	public int selectAllowIpTempListTotal(AllowIpTempSearch allowIpTempSearch);

	/**
	 * 임시접속 허용 아이피정보 토탈 목록을 반환한다.
	 * @param allowIpTempSearch
	 * @return 목록
	 */
	public List<AllowIpTemp> selectAllowIpTempList(AllowIpTempSearch allowIpTempSearch);

	/**
	 * 임시접속 허용 아이피정보 모두 삭제한다.
	 * @return 삭제결과
	 */
	public int deleteAllowIpTempAll();

	/**
	 * 인증번호를 수정한다.
	 * @param allowIpTempForm
	 * @return 수정결과
	 */
	public int updateCertifiNum(AllowIpTemp allowIpTempForm);

	/**
	 * 인증실패 횟수 증가
	 * @param allowIpTempForm
	 * @return 증가결과
	 */
	public int updateAuthentiFailCnt(AllowIpTemp allowIpTempForm);



	

}
