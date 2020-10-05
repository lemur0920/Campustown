/**
 * 
 */
package egovframework.com.asapro.support.pagination;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import egovframework.com.asapro.site.service.Site;
import egovframework.com.asapro.support.Constant;
import egovframework.com.asapro.support.util.AsaproUtils;
import eu.bitwalker.useragentutils.DeviceType;
import eu.bitwalker.useragentutils.UserAgent;

/**
 * 페이징 태그 생성
 * @author yckim
 *
 */
public class Paging {

	private int startPage;
	private int endPage;
	private int currentPage;
	private int prevPage;//버그 수정
	private int nextPage;//버그 수정
	private int totalPage;
	private int rowTop;
	private int rowTotal;
	private int pageSize;
	private int blockSize;
	private String userTextTag;
	private String userImageTag;
	private String userImageTag2;
	
	private String adminTextTag;
	private String adminImageTag;
	private String pageParamName;
	private List<?> result;
	
	private String firstText;
	private String prevText;
	private String nextText;
	private String lastText;
	
	private String queryString;
	private String uri;
	
	/**
	 * PagingSearch 를 인자로 받는 생성자
	 * @param result
	 * @param rowTotal
	 * @param pagingSearch
	 */
	public Paging(List<?> result, int rowTotal, PagingSearch pagingSearch){
		this(result, rowTotal, pagingSearch.getPageSize(), pagingSearch.getCp(), pagingSearch.getQueryString(), pagingSearch.getPageParamName(), pagingSearch.isPaging(), pagingSearch.getUrl());
	}
	
	/**
	 * 페이징 태그 오버로딩 생성자 - 게시판등
	 * @param result
	 * @param rowTotal
	 * @param pageSize
	 * @param currentPage
	 * @param queryString
	 * @param pageParamName
	 * @param isPaging
	 * @param uri
	 */
	public Paging(List<?> result, int rowTotal, int pageSize, int currentPage, String queryString, String pageParamName, boolean isPaging, String uri) {
		this(result, rowTotal, pageSize, currentPage, queryString, pageParamName, null, null, null, null, isPaging, uri);
	}
	
	/**
	 * 페이징 태그 오버로딩 생성자 - 페이징 태그 링크 블록 사이즈 를 추가 인자로 받는 생성자
	 * @param result
	 * @param rowTotal
	 * @param pageSize
	 * @param currentPage
	 * @param queryString
	 * @param pageParamName
	 * @param blockSize
	 */
	public Paging(List<?> result, int rowTotal, int pageSize, int currentPage, String queryString, String pageParamName, int blockSize) {
		this(result, rowTotal, pageSize, currentPage, queryString, pageParamName, blockSize, null, null, null, null, true, null);
	}
	
	/**
	 * 페이징 태그 오버로딩 생성자 - text 부분에 img 태그를 넣으면 이미지 태그를 사용할 수 있다. 
	 * @param result
	 * @param rowTotal
	 * @param pageSize
	 * @param currentPage
	 * @param queryString
	 * @param pageParamName
	 * @param firstText
	 * @param prevText
	 * @param nextText
	 * @param lastText
	 * @param isPaging
	 */
	public Paging(List<?> result, int rowTotal, int pageSize, int currentPage, String queryString, String pageParamName, String firstText, String prevText, String nextText, String lastText, boolean isPaging, String uri) {
		this(result, rowTotal, pageSize, currentPage, queryString, pageParamName, 10, firstText, prevText, nextText, lastText, isPaging, uri);
	}
	
	/**
	 * 페이징 태그 오버로딩 생성자 - text 와 blockSize를 인자로 받는 생성자.  
	 * @param result
	 * @param rowTotal
	 * @param pageSize
	 * @param currentPage
	 * @param queryString
	 * @param pageParamName
	 * @param blockSize
	 * @param firstText
	 * @param prevText
	 * @param nextText
	 * @param lastText
	 * @param isPaging
	 * @param uri
	 */
	public Paging(List<?> result, int rowTotal, int pageSize, int currentPage, String queryString, String pageParamName, int blockSize, String firstText, String prevText, String nextText, String lastText, boolean isPaging, String uri) {
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		
		this.pageParamName = pageParamName;
		this.result = result;
		this.currentPage = currentPage;
		this.pageSize = pageSize;
		this.totalPage = 0;
		this.rowTotal = rowTotal;
		this.queryString = queryString;
		if(StringUtils.isNotBlank(uri) ){
			this.uri = uri;
		}else{
			this.uri = AsaproUtils.getFixedRequestUri(request);
		}
		
		this.firstText = firstText;
		this.prevText = prevText;
		this.nextText = nextText;
		this.lastText = lastText;
		
		if((double)this.rowTotal / (double)this.pageSize <= 1){
			totalPage = 1;
		} else {
			totalPage = (int) Math.ceil((double)rowTotal / (double)this.pageSize); 
		}
		
		UserAgent userAgent = (UserAgent)request.getAttribute("userAgent");
		if( userAgent.getOperatingSystem().getDeviceType().equals(DeviceType.MOBILE) ){
			blockSize = 3;
		}
		this.blockSize = blockSize;//default
		
		rowTop = rowTotal - (currentPage - 1) * pageSize;

		// 시작페이지와 종료페이지의 값을 구한다.
		startPage = (int) ((currentPage - 1) / blockSize) * blockSize + 1;
		//startPage = startPage - 1 < 1 ? 1 : startPage - 1;
		startPage = startPage - 1 < 1 ? 1 : startPage;
		endPage = startPage + blockSize - 1;
		
		// 마지막 페이지 값이 전체 페이지값보다 클경우 강제 조정
		if (endPage > totalPage) {
			endPage = totalPage;
		}
		
		//버그 수정
		prevPage = startPage == 1 ? 1 : startPage - 1;
		//nextPage = startPage == 1 ? blockSize + 1 : ( startPage + blockSize >= totalPage ? totalPage : startPage + blockSize ); 
		nextPage = endPage >= totalPage ? endPage : endPage + 1; 
		
		if("?".equals(this.queryString)){
			this.queryString = "";
		}
		
		if(isPaging){
			userTextTag = buildUserTextTag(this.queryString);
			userImageTag = buildUserImageTag(this.queryString);
			userImageTag2 = buildUserImageTag2(this.queryString);
			adminTextTag = buildAdminTextTag(this.queryString);
			adminImageTag = buildAdminImageTag(this.queryString);
		}
	}

	//페이징 태그 생성 - 사용자단 텍스트링크
	private String buildUserTextTag(String qs) {
		
		String firstText = StringUtils.isBlank(this.firstText) ? "&lt;&lt;" : this.firstText;
		String prevText = StringUtils.isBlank(this.prevText) ? "&lt;" : this.prevText;
		String nextText = StringUtils.isBlank(this.nextText) ? "&gt;" : this.nextText;
		String lastText = StringUtils.isBlank(this.lastText) ? "&gt;&gt;" : this.lastText;
		
		String tag = "<div class=\"paging2 clearfix\">";
		tag += "<a href=\"" + this.uri + "?" + pageParamName + "=1" + qs + "\" class=\"img\" title=\"첫 페이지\">" + firstText + "</a>";
		tag += "<a href=\"" + this.uri + "?" + pageParamName + "=" + this.prevPage + qs + "\" class=\"img\" title=\"이전 페이지\">" + prevText + "</a>";
		for (int i = startPage; i < (endPage + 1); i++) {
			if (currentPage == i) {
				tag += "<a href=\"" + this.uri + "?" + pageParamName + "=" + i + qs + "\" class=\"on\" title=\"현재 페이지\">" + i + "</a>";
			} else {
				tag += "<a href=\"" + this.uri + "?" + pageParamName + "=" + i + qs + "\" >" + i + "</a>";
			}
		}
		tag += "<a href=\"" + this.uri + "?" + pageParamName + "=" + this.nextPage + qs + "\" class=\"img\" title=\"다음 페이지\">" + nextText + "</a>";
		tag += "<a href=\"" + this.uri + "?" + pageParamName + "=" + this.totalPage + qs + "\" class=\"img\" title=\"마지막 페이지\">" + lastText + "</a>";

		return tag + "</div>";
	}
	
	//페이징 태그 생성 - 사용자단 이미지링크
	/*private String buildUserImageTag(String qs) {
		
		String firstText = StringUtils.isBlank(this.firstText) ? "&lt;&lt;" : this.firstText;
		String prevText = StringUtils.isBlank(this.prevText) ? "&lt;" : this.prevText;
		String nextText = StringUtils.isBlank(this.nextText) ? "&gt;" : this.nextText;
		String lastText = StringUtils.isBlank(this.lastText) ? "&gt;&gt;" : this.lastText;
		
		String tag = "<ul class=\"pagination\">";
		tag += "<li><a href=\"?" + pageParamName + "=1" + qs + "\" class=\"page_first\" title=\"첫 페이지\"><span>" + firstText + "</span></a></li>";
		tag += "<li><a href=\"?" + pageParamName + "=" + this.prevPage + qs + "\" class=\"page_prev\" title=\"이전 페이지\"><span>" + prevText + "</span></a></li>";
		for (int i = startPage; i < (endPage + 1); i++) {
			if (currentPage == i) {
				tag += "<li class=\"active\"><a href=\"?" + pageParamName + "=" + i + qs + "\" class=\"page_current\" title=\"" + i + " 페이지\"><span>" + i + "</span></a></li>";
			} else {
				tag += "<li><a href=\"?" + pageParamName + "=" + i + qs + "\" class=\"page_num\" title=\"" + i + " 페이지\"><span>" + i + "</span></a></li>";
			}
		}
		tag += "<li><a href=\"?" + pageParamName + "=" + this.nextPage + qs + "\" class=\"page_next\" title=\"다음 페이지\"><span>" + nextText + "</span></a></li>";
		tag += "<li><a href=\"?" + pageParamName + "=" + this.totalPage + qs + "\" class=\"page_last\" title=\"마지막 페이지\"><span>" + lastText + "</span></a></li>";

		return tag + "</ul>";
	}*/
	private String buildUserImageTag(String qs) {
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		String theme = (String) request.getAttribute("theme");
		String designResource = Constant.CONTEXT_PATH + "/design/theme/" + theme;
		
		String firstText = StringUtils.isBlank(this.firstText) ? designResource + "/images/sub/first_page.gif" : this.firstText;
		String prevText = StringUtils.isBlank(this.prevText) ? designResource + "/images/sub/prev_page.gif" : this.prevText;
		String nextText = StringUtils.isBlank(this.nextText) ? designResource + "/images/sub/next_page.gif" : this.nextText;
		String lastText = StringUtils.isBlank(this.lastText) ? designResource + "/images/sub/last_page.gif" : this.lastText;
		
		String tag = "<div class=\"paging\">";
		tag += "<a href=\"" + this.uri + "?" + pageParamName + "=1" + qs + "\" title=\"첫 페이지\" class=\"img\">" + "<img src=\"" + firstText + "\" alt=\"처음\"></a> ";
		tag += "<a href=\"" + this.uri + "?" + pageParamName + "=" + this.prevPage + qs + "\" title=\"이전 페이지\" class=\"img\">" + "<img src=\""  + prevText + "\" alt=\"이전\"></a> ";
		for (int i = startPage; i < (endPage + 1); i++) {
			if (currentPage == i) {
				tag += "<a href=\"" + this.uri + "?" + pageParamName + "=" + i + qs + "\" class=\"on txt\" title=\"현제페이지\">" + i + "</a> ";
			} else {
				tag += "<a href=\"" + this.uri + "?" + pageParamName + "=" + i + qs + "\" class=\"txt\">" + i + "</a> ";
			}
		}
		tag += "<a href=\"" + this.uri + "?" + pageParamName + "=" + this.nextPage + qs + "\" title=\"다음 페이지\" class=\"img\">" + "<img src=\"" + nextText + "\" alt=\"다음\"></a> ";
		tag += "<a href=\"" + this.uri + "?" + pageParamName + "=" + this.totalPage + qs + "\" title=\"마지막 페이지\" class=\"img\">" + "<img src=\"" + lastText + "\" alt=\"마지막\"></a> ";
		tag += "</div>";
		
		return tag ;
	}
	
	private String buildUserImageTag2(String qs) {
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		String theme = (String) request.getAttribute("theme");
		String designResource = "/design/theme/" + theme;
		
		String firstText = StringUtils.isBlank(this.firstText) ? designResource + "/images/sub/sub01_limg28.gif" : this.firstText;
		String prevText = StringUtils.isBlank(this.prevText) ? designResource + "/images/sub/sub01_limg29.gif" : this.prevText;
		String nextText = StringUtils.isBlank(this.nextText) ? designResource + "/images/sub/sub01_limg30.gif" : this.nextText;
		String lastText = StringUtils.isBlank(this.lastText) ? designResource + "/images/sub/sub01_limg31.gif" : this.lastText;
		
		String tag = "<div class=\"subl-paging clearfix\">";
		tag += "<a href=\"" + this.uri + "?" + pageParamName + "=1" + qs + "\" class=\"img\">" + "<img src=\"" + firstText + "\" alt=\"처음\"></a>";
		tag += "<a href=\"" + this.uri + "?" + pageParamName + "=" + this.prevPage + qs + "\" class=\"img mr-22\">" + "<img src=\""  + prevText + "\" alt=\"이전\"></a>";
		for (int i = startPage; i < (endPage + 1); i++) {
			if (currentPage == i) {
				tag += "<a href=\"" + this.uri + "?" + pageParamName + "=" + i + qs + "\" class=\"on txt\" title=\"현제페이지\">" + i + "</a>";
			} else {
				tag += "<a href=\"" + this.uri + "?" + pageParamName + "=" + i + qs + "\" class=\"txt\">" + i + "</a>";
			}
		}
		tag += "<a href=\"" + this.uri + "?" + pageParamName + "=" + this.nextPage + qs + "\" class=\"img ml-22\">" + "<img src=\"" + nextText + "\" alt=\"다음\"></a>";
		tag += "<a href=\"" + this.uri + "?" + pageParamName + "=" + this.totalPage+ qs + "\" class=\"img\">" + "<img src=\"" + lastText + "\" alt=\"마지막\"></a>";
		tag += "</div>";
		
		return tag ;
	}
	//페이징 태그 생성 - 관리자 텍스트링크
	private String buildAdminTextTag(String qs) {
			
		String firstText = StringUtils.isBlank(this.firstText) ? "&lt;&lt;" : this.firstText;
		String prevText = StringUtils.isBlank(this.prevText) ? "&lt;" : this.prevText;
		String nextText = StringUtils.isBlank(this.nextText) ? "&gt;" : this.nextText;
		String lastText = StringUtils.isBlank(this.lastText) ? "&gt;&gt;" : this.lastText;
		
		String tag = "<nav aria-label=\"Page navigation\">";
		tag += "<ul class=\"pagination justify-content-center\">";
		tag += "<li class=\"page-item\"><a href=\"" + this.uri + "?" + pageParamName + "=1" + qs + "\" class=\"page-link\" aria-label=\"처음\">" + firstText + "</a></li>";
		tag += "<li class=\"page-item\"><a href=\"" + this.uri + "?" + pageParamName + "=" + this.prevPage + qs + "\" class=\"page-link\" aria-label=\"이전\">" + prevText + "</a></li>";
		for (int i = startPage; i < (endPage + 1); i++) {
			if (currentPage == i) {
				tag += "<li class=\"page-item active\"><a href=\"" + this.uri + "?" + pageParamName + "=" + i + qs + "\" class=\"page-link\">" + i + "<span class=\"sr-only\">(current)</span></a></li>";
			} else {
				tag += "<li class=\"page-item\"><a href=\"" + this.uri + "?" + pageParamName + "=" + i + qs + "\" class=\"page-link\">" + i + "</a></li>";
			}
		}
		tag += "<li class=\"page-item\"><a href=\"" + this.uri + "?" + pageParamName + "=" + this.nextPage + qs + "\" class=\"page-link\" aria-label=\"다음\">" + nextText + "</a></li>";
		tag += "<li class=\"page-item\"><a href=\"" + this.uri + "?" + pageParamName + "=" + this.totalPage + qs + "\" class=\"page-link\" aria-label=\"마지막\">" + lastText + "</a></li>";
		tag += "</ul>";
		tag += "</nav>";
		return tag;
	}
		
	//페이징 태그 생성 - 관리자 이미지링크
	private String buildAdminImageTag(String qs) {
			
		String firstText = StringUtils.isBlank(this.firstText) ? "&lt;&lt;" : this.firstText;
		String prevText = StringUtils.isBlank(this.prevText) ? "&lt;" : this.prevText;
		String nextText = StringUtils.isBlank(this.nextText) ? "&gt;" : this.nextText;
		String lastText = StringUtils.isBlank(this.lastText) ? "&gt;&gt;" : this.lastText;
		
		String tag = "<nav aria-label=\"Page navigation\">";
		tag += "<ul class=\"pagination justify-content-center\">";
		tag += "<li class=\"page-item\"><a href=\"" + this.uri + "?" + pageParamName + "=1" + qs + "\" class=\"page-link\" aria-label=\"처음\">" + firstText + "</a></li>";
		tag += "<li class=\"page-item\"><a href=\"" + this.uri + "?" + pageParamName + "=" + this.prevPage + qs + "\" class=\"page-link\" aria-label=\"이전\">" + prevText + "</a></li>";
		for (int i = startPage; i < (endPage + 1); i++) {
			if (currentPage == i) {
				tag += "<li class=\"page-item active\"><a href=\"" + this.uri + "?" + pageParamName + "=" + i + qs + "\" class=\"page-link\">" + i + "<span class=\"sr-only\"(current)</span></a></li>";
			} else {
				tag += "<li class=\"page-item\"><a href=\"" + this.uri + "?" + pageParamName + "=" + i + qs + "\" class=\"page-link\">" + i + "</a></li>";
			}
		}
		tag += "<li class=\"page-item\"><a href=\"" + this.uri + "?" + pageParamName + "=" + this.nextPage + qs + "\" class=\"page-link\" aria-label=\"다음\">" + nextText + "</a></li>";
		tag += "<li class=\"page-item\"><a href=\"" + this.uri + "?" + pageParamName + "=" + this.totalPage + qs + "\" class=\"page-link\" aria-label=\"마지막\">" + lastText + "</a></li>";
		tag += "</ul>";
		tag += "</nav>";
		return tag;
	}

	/**
	 * @return the rowTop
	 */
	public int getRowTop() {
		return rowTop;
	}

	/**
	 * @return the rowTotal
	 */
	public int getRowTotal() {
		return rowTotal;
	}

	/**
	 * @return the result
	 */
	public List<?> getResult() {
		return result;
	}

	/**
	 * @return the totalPage
	 */
	public int getTotalPage() {
		return totalPage;
	}

	/**
	 * @return the queryString
	 */
	public String getQueryString() {
		return queryString;
	}
	
	/**
	 * @return the currentPage
	 */
	public int getCurrentPage() {
		return currentPage;
	}

	/**
	 * @param currentPage the currentPage to set
	 */
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	/**
	 * @return the userTextTag
	 */
	public String getUserTextTag() {
		return userTextTag;
	}

	/**
	 * @param userTextTag the userTextTag to set
	 */
	public void setUserTextTag(String userTextTag) {
		this.userTextTag = userTextTag;
	}

	/**
	 * @return the userImageTag
	 */
	public String getUserImageTag() {
		return userImageTag;
	}

	/**
	 * @param userImageTag the userImageTag to set
	 */
	public void setUserImageTag(String userImageTag) {
		this.userImageTag = userImageTag;
	}
	
	/**
	 * @return the userImageTag2
	 */
	public String getUserImageTag2() {
		return userImageTag2;
	}
	/**
	 * 
	 * @param userImageTag2
	 */
	public void setUserImageTag2(String userImageTag2) {
		this.userImageTag2 = userImageTag2;
	}
	
	/**
	 * @return the adminTextTag
	 */
	public String getAdminTextTag() {
		return adminTextTag;
	}

	/**
	 * @param adminTextTag the adminTextTag to set
	 */
	public void setAdminTextTag(String adminTextTag) {
		this.adminTextTag = adminTextTag;
	}

	/**
	 * @return the adminImageTag
	 */
	public String getAdminImageTag() {
		return adminImageTag;
	}

	/**
	 * @param adminImageTag the adminImageTag to set
	 */
	public void setAdminImageTag(String adminImageTag) {
		this.adminImageTag = adminImageTag;
	}

	/**
	 * @return the blockSize
	 */
	public int getBlockSize() {
		return blockSize;
	}

	/**
	 * @param blockSize the blockSize to set
	 */
	public void setBlockSize(int blockSize) {
		this.blockSize = blockSize;
	}

	@Override
	public String toString() {
		return "Paging{" +
				"startPage=" + startPage +
				", endPage=" + endPage +
				", currentPage=" + currentPage +
				", prevPage=" + prevPage +
				", nextPage=" + nextPage +
				", totalPage=" + totalPage +
				", rowTop=" + rowTop +
				", rowTotal=" + rowTotal +
				", pageSize=" + pageSize +
				", blockSize=" + blockSize +
				", userTextTag='" + userTextTag + '\'' +
				", userImageTag='" + userImageTag + '\'' +
				", userImageTag2='" + userImageTag2 + '\'' +
				", adminTextTag='" + adminTextTag + '\'' +
				", adminImageTag='" + adminImageTag + '\'' +
				", pageParamName='" + pageParamName + '\'' +
				", result=" + result +
				", firstText='" + firstText + '\'' +
				", prevText='" + prevText + '\'' +
				", nextText='" + nextText + '\'' +
				", lastText='" + lastText + '\'' +
				", queryString='" + queryString + '\'' +
				", uri='" + uri + '\'' +
				'}';
	}
}
