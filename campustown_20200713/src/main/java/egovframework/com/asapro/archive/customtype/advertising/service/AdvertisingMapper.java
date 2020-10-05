/**
 * 
 */
package egovframework.com.asapro.archive.customtype.advertising.service;

import java.util.List;
import java.util.Map;

import egovframework.com.asapro.archive.service.ArchiveCategory;
import egovframework.com.asapro.fileinfo.service.FileInfo;
import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * 광고자료 관리 SQL 매퍼
 * @author yckim
 * @since 2019. 9. 30.
 *
 */
@Mapper
public interface AdvertisingMapper {

	//=============================================================================================================================
	//==========================================  광고자료    ================================================================
	//=============================================================================================================================
	
	/**
	 * 광고자료 목록을 조회한다.
	 * @param advertisingSearch
	 * @return 광고자료 목록
	 */
	public List<Advertising> selectAdvertisingList(AdvertisingSearch advertisingSearch);

	/**
	 * 광고자료 목록 토탈을 조회한다.,
	 * @param advertisingSearch
	 * @return 광고자료 목록 토탈
	 */
	public int selectAdvertisingListTotal(AdvertisingSearch advertisingSearch);

	/**
	 * 광고자료를 등록한다.
	 * @param advertisingForm
	 * @return 등록결과
	 */
	public int insertAdvertising(Advertising advertisingForm);

	/**
	 * 광고자료를 조회한다.
	 * @param advertisingForm
	 * @return 광고자료
	 */
	public Advertising selectAdvertising(Advertising advertisingForm);

	/**
	 * 광고자료를 수정한다.
	 * @param advertisingForm
	 * @return 수정결과
	 */
	public int updateAdvertising(Advertising advertisingForm);

	/**
	 * 광고자료 연도 목록을 반환한다.
	 * @param advertisingSearch
	 * @return 연도목록
	 */
	public List<String> selectYearList(AdvertisingSearch advertisingSearch);


}
