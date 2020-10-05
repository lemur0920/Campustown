/**
 * 
 */
package egovframework.com.asapro.archive.customtype.advertising.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import egovframework.com.asapro.fileinfo.service.FileInfoUploadResult;
import egovframework.com.asapro.support.exception.AsaproException;


/**
 * 광고자료 관리 서비스
 * @author yckim
 * @since 2019. 9. 30.
 *
 */
public interface AdvertisingService {

	//=============================================================================================================================
	//==========================================  광고자료    ================================================================
	//=============================================================================================================================
	
	/**
	 * 광고자료 목록을 조회한다.
	 * @param advertisingSearch
	 * @return 광고자료 목록
	 */
	public List<Advertising> getAdvertisingList(AdvertisingSearch advertisingSearch);
	
	/**
	 * 광고자료 목록 토탈을 조회한다.
	 * @param advertisingSearch
	 * @return 광고자료 목록 토탈
	 */
	public int getAdvertisingListTotal(AdvertisingSearch advertisingSearch);

	/**
	 * 광고자료를 등록한다
	 * @param advertisingForm
	 * @return 등록결과
	 * @throws IOException 
	 * @throws AsaproException 
	 */
	public Map<String, FileInfoUploadResult> insertAdvertising(Advertising advertisingForm) throws AsaproException, IOException;

	/**
	 * 광고자료를 조회한다.
	 * @param advertisingForm
	 * @return 광고자료
	 */
	public Advertising getAdvertising(Advertising advertisingForm);

	/**
	 * 광고자료를 수정한다.
	 * @param advertisingForm
	 * @return 수정결과
	 * @throws IOException 
	 * @throws AsaproException 
	 */
	public Map<String, FileInfoUploadResult> updateAdvertising(Advertising advertisingForm) throws AsaproException, IOException;

	/**
	 * 광고자료를 삭제한다.
	 * @param advertisingList
	 * @return 삭제결과
	 */
	public int deleteAdvertising(List<Advertising> advertisingList);

	/**
	 * 광고자료를 삭제한다.
	 * @param advertisingForm
	 * @return 삭제결과
	 */
	public int deleteAdvertising(Advertising advertisingForm);

	/**
	 * 광고자료 연도 목록을 반환한다.
	 * @param advertisingSearch
	 * @return 연도목록
	 */
	public List<String> getYearList(AdvertisingSearch advertisingSearch);
	
}
