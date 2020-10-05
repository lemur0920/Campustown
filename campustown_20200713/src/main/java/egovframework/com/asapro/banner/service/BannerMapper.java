/**
 * 
 */
package egovframework.com.asapro.banner.service;

import java.util.List;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * 배너 SQL 매퍼
 * @author yckim
 * @since 2018. 7. 26.
 *
 */
@Mapper
public interface BannerMapper {
	
	/**
	 * 배너 목록을 조회한다.
	 * @param bannerSearch
	 * @return 배너 목록
	 */
	public List<Banner> selectBannerList(BannerSearch bannerSearch);
	
	/**
	 * 배너 개수를 조회한다.
	 * @param bannerSearch
	 * @return 배너 개수
	 */
	public int selectBannerListTotal(BannerSearch bannerSearch);
	
	/**
	 * 배너를 조회한다.
	 * @param bnId
	 * @return
	 */
	public Banner selectBanner(Banner banner);
	
	/**
	 * 배너를 추가한다.
	 * @param bannerForm
	 * @return
	 */
	public int insertBanner(Banner bannerForm);
	
	/**
	 * 배너를 수정한다.
	 * @param bannerForm
	 * @return
	 */
	public int updateBanner(Banner bannerForm);
	
	/**
	 * 배너를 삭제한다.
	 * @param bnId
	 * @return
	 */
	public int deleteBanner(Banner banner);
	
	/**
	 * 배너 순서를 수정한다.
	 * @param bannerForm
	 * @return
	 */
	public int updateBannerOrder(Banner bannerForm );
}
