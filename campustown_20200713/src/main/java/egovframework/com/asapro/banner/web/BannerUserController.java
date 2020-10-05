/**
 * 
 */
package egovframework.com.asapro.banner.web;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.FlashMap;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.ibm.icu.text.SimpleDateFormat;

import egovframework.com.asapro.banner.service.Banner;
import egovframework.com.asapro.banner.service.BannerSearch;
import egovframework.com.asapro.banner.service.BannerService;
import egovframework.com.asapro.fileinfo.service.FileInfo;
import egovframework.com.asapro.fileinfo.service.FileInfoService;
import egovframework.com.asapro.fileinfo.service.FileInfoUploadResult;
import egovframework.com.asapro.site.service.Site;
import egovframework.com.asapro.support.Constant;
import egovframework.com.asapro.support.ServerMessage;
import egovframework.com.asapro.support.annotation.CurrentSite;
import egovframework.com.asapro.support.exception.AsaproException;
import egovframework.com.asapro.support.pagination.Paging;
import egovframework.com.asapro.support.util.AsaproUtils;
import egovframework.com.asapro.support.util.EtcUtil;
import eu.bitwalker.useragentutils.UserAgent;

/**
 * 배너/메인비주얼/팝업존/레이어팝업 관리자 컨트롤러
 * @author yckim
 * @since 2018. 7. 30.
 *
 */
@Controller
public class BannerUserController {
	
	@Autowired
	private BannerService bannerService;
	
	@Autowired
	private HttpServletRequest request;
	
	/**
	 * 배너/메인비주얼/팝업존/레이어팝업 목록을 반환한다.
	 * @param model
	 * @param bannerSearch
	 * @return
	 */
	@RequestMapping(value=Constant.APP_PATH + Constant.SITE_ID_PATH + "/banner/{bnType}", method=RequestMethod.GET)
	public String bannerListGet(Model model, @CurrentSite Site currentSite, @ModelAttribute("bannerSearch") BannerSearch bannerSearch){
		String today = new SimpleDateFormat("yyyy-MM-dd", Locale.KOREA).format(new Date());
		
		//배너, 메인 비주얼, 및 기본 검색조건
		bannerSearch.fixBrokenSvByDefaultCharsets();
		bannerSearch.setBnUse(true);
		bannerSearch.setSitePrefix(currentSite.getSitePrefix());
		bannerSearch.setPaging(false);
		//bannerSearch.setDefaultSort("BN_ORDER", "ASC");
		bannerSearch.setSortOrder("BN_ORDER");
		bannerSearch.setSortDirection("ASC");
		
		if("popupzone".equals(bannerSearch.getBnType()) || "layer".equals(bannerSearch.getBnType()) ){
			bannerSearch.setBnToday(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
		}
		//팝업존 검색조건
		if("popupzone".equals(bannerSearch.getBnType()) ){
			bannerSearch.setPaging(true);
			bannerSearch.setPageSize(10);
		}
		
		List<Banner> list = bannerService.getBannerList(bannerSearch);
		
		
		
		model.addAttribute("list", list);
		
		model.addAttribute("userAgent", (UserAgent)request.getAttribute("userAgent"));
		
		//model.addAttribute("bnToday", today);
		
		return AsaproUtils.getThemePath(request) + "banner/" + bannerSearch.getBnType();
	}
	
}
