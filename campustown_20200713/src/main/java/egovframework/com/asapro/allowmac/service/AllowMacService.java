/**
 * 
 */
package egovframework.com.asapro.allowmac.service;

import java.util.List;

/**
 * 접속허용 Mac Address 관리 서비스
 * @author yckim
 * @since 2019. 3. 26.
 *
 */
public interface AllowMacService {

	/**
	 * 접속허용/차단 Mac Address 목록을 반환한다.
	 * @param allowMacSearch
	 * @return 접속허용/차단 Mac Address 목록
	 */
	public List<AllowMac> getAllowMacList(AllowMacSearch allowMacSearch);

	/**
	 * 접속허용/차단 Mac Address 토탈을 반환한다.
	 * @param allowMacSearch
	 * @return 접속허용/차단 Mac Address 토탈
	 */
	public int getAllowMacListTotal(AllowMacSearch allowMacSearch);

	/**
	 * 접속허용/차단 Mac Address 를 추가한다.
	 * @param allowMacForm
	 * @return 추가 결과
	 */
	public int insertAllowMac(AllowMac allowMacForm);

	/**
	 * 접속허용/차단 Mac Address 를 조회한다.
	 * @param macId
	 * @return 접속허용/차단 Mac Address
	 */
	public AllowMac getAllowMac(AllowMac allowMacForm);

	/**
	 * 접속허용/차단 Mac Address 를 수정한다.
	 * @param allowMacForm
	 * @return 수정 결과
	 */
	public int updateAllowMac(AllowMac allowMacForm);

	/**
	 * 접속허용/차단 Mac Address 를 삭제한다.
	 * @param allowMacLis
	 * @return 삭제결과
	 */
	public int deleteAllowMac(List<AllowMac> allowMacLis);
	
	/**
	 * 접속허용/차단 Mac Address 를 삭제한다.
	 * @param allowMac
	 * @return 삭제결과
	 */
	public int deleteAllowMac(AllowMac allowMac);
	

}
