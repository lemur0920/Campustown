/**
 * 
 */
package egovframework.com.asapro.allowmac.service;

import java.util.List;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * 접속허용 Mac Address 관리 SQL매퍼
 * @author yckim
 * @since 2019. 3. 26.
 *
 */
@Mapper
public interface AllowMacMapper {

	/**
	 * 접속허용/차단 Mac Address 목록을 반환한다.
	 * @param allowMacSearch
	 * @return 접속허용/차단 Mac Address 목록
	 */
	List<AllowMac> selectAllowMacList(AllowMacSearch allowMacSearch);

	/**
	 * 접속허용/차단 Mac Address 토탈을 반환한다.
	 * @param allowMacSearch
	 * @return 접속허용/차단 Mac Address 토탈
	 */
	int selectAllowMacListTotal(AllowMacSearch allowMacSearch);

	/**
	 * 접속허용/차단 Mac Address 를 추가한다.
	 * @param allowMacForm
	 * @return 추가 결과
	 */
	int insertAllowMac(AllowMac allowMacForm);

	/**
	 * 접속허용/차단 Mac Address 를 조회한다.
	 * @param allowMacForm
	 * @return 접속허용/차단 Mac Address
	 */
	AllowMac selectAllowMac(AllowMac allowMacForm);

	/**
	 * 접속허용/차단 Mac Address 를 수정한다.
	 * @param allowMacForm
	 * @return 수정결과
	 */
	int updateAllowMac(AllowMac allowMacForm);

	/**
	 * 접속허용/차단 Mac Address 를 삭제한다.
	 * @param allowMac
	 * @return 삭제결과
	 */
	int deleteAllowMac(AllowMac allowMac);

	

}
