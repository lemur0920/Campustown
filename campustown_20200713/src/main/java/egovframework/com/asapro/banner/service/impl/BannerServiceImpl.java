/**
 * 
 */
package egovframework.com.asapro.banner.service.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import egovframework.com.asapro.banner.service.Banner;
import egovframework.com.asapro.banner.service.BannerMapper;
import egovframework.com.asapro.banner.service.BannerSearch;
import egovframework.com.asapro.banner.service.BannerService;
import egovframework.com.asapro.fileinfo.service.FileInfo;
import egovframework.com.asapro.fileinfo.service.FileInfoService;
import egovframework.com.asapro.fileinfo.service.FileInfoUploadResult;
import egovframework.com.asapro.support.Constant;
import egovframework.com.asapro.support.exception.AsaproException;
import egovframework.com.asapro.support.util.AsaproUtils;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

/**
 * 배너/메인비주얼/팝업존/레이어팝업 서비스 구현
 * @author yckim
 * @since 2018. 7. 26.
 *
 */
@Service
public class BannerServiceImpl extends EgovAbstractServiceImpl implements BannerService {

	@Autowired
	private BannerMapper bannerMapper;
	
	@Autowired
	private FileInfoService fileInfoService;
	
	@Autowired
	private HttpServletRequest request;
	
	/**
	 * 배너/메인비주얼/팝업존/레이어팝업 목록
	 */
	@Override
	public List<Banner> getBannerList(BannerSearch bannerSearch) {
		return bannerMapper.selectBannerList(bannerSearch);
	}

	/**
	 * 배너/메인비주얼/팝업존/레이어팝업 개수
	 */
	@Override
	public int getBannerListTotal(BannerSearch bannerSearch) {
		return bannerMapper.selectBannerListTotal(bannerSearch);
	}

	/**
	 * 배너/메인비주얼/팝업존/레이어팝업 조회
	 */
	@Override
	public Banner getBanner(Banner banner) {
		return bannerMapper.selectBanner(banner);
	}

	/**
	 * 배너/메인비주얼/팝업존/레이어팝업 추가
	 * @throws IOException 
	 * @throws AsaproException 
	 * @throws FileNotFoundException 
	 */
	@SuppressWarnings("deprecation")
	@Override
	public int insertBanner(Banner bannerForm) throws FileNotFoundException, AsaproException, IOException {
		bannerForm.setBnRegDate(new Date());
		
		BannerSearch bannerSearch = new BannerSearch();
		bannerSearch.setSitePrefix(bannerForm.getSitePrefix());
		bannerSearch.setBnType(bannerForm.getBnType());
		int order = bannerMapper.selectBannerListTotal(bannerSearch) + 1;
		bannerForm.setBnOrder(order);

		String ext = FilenameUtils.getExtension(bannerForm.getBnImage().getOriginalFilename()).toLowerCase();
		if( StringUtils.isNotBlank(ext) ){
			ext = "." + ext;
		}
		bannerForm.setBnExt(ext);
		
		//프로토콜 없으면 http:// 붙여준다 
		if( StringUtils.isNotBlank(bannerForm.getBnLink()) ){
			if( !bannerForm.getBnLink().startsWith("http://") && !bannerForm.getBnLink().startsWith("https://") ){
				bannerForm.setBnLink("http://" + bannerForm.getBnLink());
			}
		}

		int inserted = bannerMapper.insertBanner(bannerForm);
		//fileInfoService.saveFileOnly(bannerForm.getBnImage(), new File(AsaproUtils.getSiteUploadDirectory(request) + "/banner/" + bannerForm.getBnId() + ext), true);
		
		return inserted;
	}

	/**
	 * 배너/메인비주얼/팝업존/레이어팝업 수정
	 */
	@SuppressWarnings("deprecation")
	@Override
	public int updateBanner(Banner bannerForm) throws FileNotFoundException {
		int updated = 0;

		//프로토콜 없으면 http:// 붙여준다 
		if( StringUtils.isNotBlank(bannerForm.getBnLink()) ){
			if( !bannerForm.getBnLink().startsWith("http://") && !bannerForm.getBnLink().startsWith("https://") ){
				bannerForm.setBnLink("http://" + bannerForm.getBnLink());
			}
		}
		if( bannerForm.getBnImage() != null && !bannerForm.getBnImage().isEmpty() ){
			String ext = FilenameUtils.getExtension(bannerForm.getBnImage().getOriginalFilename()).toLowerCase();
			if( StringUtils.isNotBlank(ext) ){
				ext = "." + ext;
			}
			//파일 확장자 업데이트
			bannerForm.setBnExt(ext);
			//fileInfoService.saveFileOnly(bannerForm.getBnImage(), new File(AsaproUtils.getSiteUploadDirectory(request) + "/banner/" + bannerForm.getBnId() + ext), true);
		} 
		updated = bannerMapper.updateBanner(bannerForm);
		
		return updated;
	}

	/**
	 * 배너/메인비주얼/팝업존/레이어팝업 삭제
	 */
	@Override
	public int deleteBanner(List<Banner> bannerList) throws FileNotFoundException {
		int deleted = 0;
		for( Banner banner : bannerList ){
			deleted += this.deletBanner(banner);
		}
		return deleted;
	}
		
	/**
	 * 배너/메인비주얼/팝업존/레이어팝업 삭제
	 */
	@Override
	public int deletBanner(Banner banner) throws FileNotFoundException {
		Banner fromDB = bannerMapper.selectBanner(banner);
		fromDB.setSitePrefix(banner.getSitePrefix());
		
		int result = bannerMapper.deleteBanner(banner);
		
		if(result > 0){
			
			//대표이미지 삭제
			if(fromDB.getThumb() != null && fromDB.getThumb().getFileId() > 0){
				FileInfo fileInfoImage = fromDB.getThumb();
				fileInfoImage.setSitePrefix(banner.getSitePrefix());
				fileInfoService.deleteFileInfo(fileInfoImage);
			}
		}

		return result;
	}

	/**
	 * 순서저장
	 */
	@Override
	public int updateBannerOrder(List<Banner> bannerList) {
		int updated = 0;
		for( Banner banner : bannerList ){
			updated += bannerMapper.updateBannerOrder(banner);
		}
		return updated;
	}
}
