/**
 * 
 */
package egovframework.com.asapro.banner.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import egovframework.com.asapro.support.exception.AsaproException;

/**
 *
 * @author yckim
 * @since 2018. 7. 26.
 *
 */
public interface BannerService {
	
	/**
	 * 배너/메인비주얼/팝업존/레이어팝업 목록을 조회한다.
	 * @param bannerSearch
	 * @return 배너/메인비주얼/팝업존/레이어팝업 목록
	 */
	public List<Banner> getBannerList(BannerSearch bannerSearch);
	
	/**
	 * 배너/메인비주얼/팝업존/레이어팝업 개수를 조회한다.
	 * @param bannerSearch
	 * @return 배너/메인비주얼/팝업존/레이어팝업 개수
	 */
	public int getBannerListTotal(BannerSearch bannerSearch);
	
	/**
	 * 배너/메인비주얼/팝업존/레이어팝업를 조회한다.
	 * @param bnId
	 * @return
	 */
	public Banner getBanner(Banner banner);
	
	/**
	 * 배너/메인비주얼/팝업존/레이어팝업를 추가한다.
	 * @param bannerForm
	 * @return
	 * @throws FileNotFoundException 
	 * @throws IOException 
	 * @throws AsaproException 
	 */
	public int insertBanner(Banner bannerForm) throws FileNotFoundException, AsaproException, IOException;
	
	/**
	 * 배너/메인비주얼/팝업존/레이어팝업를 수정한다.
	 * @param bannerForm
	 * @return
	 * @throws FileNotFoundException 
	 */
	public int updateBanner(Banner bannerForm) throws FileNotFoundException;
	
	/**
	 * 배너/메인비주얼/팝업존/레이어팝업를 삭제한다.
	 * @param bnIds
	 * @return
	 * @throws FileNotFoundException 
	 */
	public int deleteBanner(List<Banner> banenrList) throws FileNotFoundException;
	
	/**
	 * 배너/메인비주얼/팝업존/레이어팝업를 삭제한다.
	 * @param bnId
	 * @return
	 * @throws FileNotFoundException 
	 */
	public int deletBanner(Banner banner) throws FileNotFoundException;
	
	/**
	 * 배너/메인비주얼/팝업존/레이어팝업 순서를 수정한다.
	 * @param bannerForm
	 * @return
	 */
	public int updateBannerOrder(List<Banner> bannerList);
	
}
