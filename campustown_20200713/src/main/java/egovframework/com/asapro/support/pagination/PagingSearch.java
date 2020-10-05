/**
 * 
 */
package egovframework.com.asapro.support.pagination;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import egovframework.com.asapro.config.service.Config;
import egovframework.com.asapro.config.service.ConfigService;
import egovframework.com.asapro.site.service.MultiSiteVO;
import egovframework.com.asapro.site.service.Site;
import egovframework.com.asapro.support.ApplicationContextProvider;
import egovframework.com.asapro.support.Constant;

/**
 * 페이징 검색 클래스
 * getQueryString() 을 구현해주어야 한다.<br/>
 * Paging 클래스와 세트. 같이 사용해야함.<br/>
 * @author yckim
 * @since 2018. 4. 16.
 * 
 */
public class PagingSearch extends MultiSiteVO{
	
	private String sc;
	private String sv;
	
	private String pageParamName = "cp";//페이지 파라메터
	private Integer cp;//현재페이지
	private Integer pageSize = Constant.PAGE_SIZE;// 한페이지에 몇개
	private int offset;// for MYSQL
	private int startRow; // for ORACLE
	private int endRow; // for ORACLE
	private boolean paging = true;//페이징 적용 여부 기본값 : true
	private int exclusion; // 제외 수량 - 첫 페이지 공통으로 추출해서 페이징 할경우 사용

	
	private String sortOrder;
	private String sortDirection;
	private String sortOverride;//프로그램단에서 강제로 정렬 덮을때, 파라메터로는 노출되지 않음
	
	private String openerCallback;//팝업 콜백함수 이름 처리용
	private String extra1;//임시 파라메터 다용도로 사용가능
	private String extra2;
	private String url;
	
	private String listType = "list";
	
	private static final Logger LOGGER = LoggerFactory.getLogger(PagingSearch.class);
	/**
	 * JSP 에서 사용될때 request 를 바로 집어넣어서 siteId를 세팅하는 용도
	 * @param request
	 */
	public void setSitePrefix(HttpServletRequest request) {
		Site site = (Site) request.getAttribute("currentSite");
		if( site != null ){
			this.sitePrefix = site.getSitePrefix();
		}
	}

	/**
	 * @return the sc
	 */
	public String getSc() {
		return sc;
	}

	/**
	 * @param sc
	 *            the sc to set
	 */
	public void setSc(String sc) {
		this.sc = sc;
	}

	/**
	 * @return the sv
	 */
	public String getSv() {
		return sv;
	}

	/**
	 * @param sv
	 *            the sv to set
	 */
	public void setSv(String sv) {
		this.sv = sv;
	}

	/**
	 * @return the cp
	 */
	public Integer getCp() {
		return cp == null || cp == 0 ? 1 : cp;
	}

	/**
	 * @param cp
	 *            the cp to set
	 */
	public void setCp(Integer cp) {
		this.cp = cp;
	}

	/**
	 * @return the offset
	 */
	public int getOffset() {
		if (this.getCp() == 1) {
			return 0;
		} else {
			return this.getPageSize() * (this.getCp() - 1);
		}
	}

	/**
	 * @param offset
	 *            the offset to set
	 */
	public void setOffset(int offset) {
		this.offset = offset;
	}

	/**
	 * @return the pageSize
	 */
	public Integer getPageSize() {
		// return pageSize == 0 || pageSize == null ? 10 : pageSize;
		return pageSize;
	}

	/**
	 * @param pageSize
	 *            the pageSize to set
	 */
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	
	/**
	 * @return the pageParamName
	 */
	public String getPageParamName() {
		return pageParamName;
	}

	/**
	 * @param pageParamName the pageParamName to set
	 */
	public void setPageParamName(String pageParamName) {
		this.pageParamName = pageParamName;
	}
	
	/**
	 * @return the startRow
	 */
	public int getStartRow() {
		//return this.startRow;
		//return (this.getCp() - 1) * this.getPageSize() + 1;
		if(this.exclusion == 0 ){
			return (this.getCp() - 1) * this.getPageSize() + 1; 
		}else{
			return (this.getCp() - 1) * this.getPageSize() + 1 + this.getExclusion(); 
		}
	}

	/**
	 * @param startRow the startRow to set
	 */
	public void setStartRow(int startRow) {
		this.startRow = startRow;
	}

	/**
	 * @return the endRow
	 */
	public int getEndRow() {
		//페이징없이 전체 다 가져오도록
		if(!this.isPaging()){
			return Integer.MAX_VALUE;
		}
		//return endRow;
		return this.getStartRow() + this.getPageSize() - 1;
	}

	/**
	 * @param endRow the endRow to set
	 */
	public void setEndRow(int endRow) {
		this.endRow = endRow;
	}
	
	/**
	 * @return the paging
	 */
	public boolean isPaging() {
		return paging;
	}

	/**
	 * @param paging the paging to set
	 */
	public void setPaging(boolean paging) {
		this.paging = paging;
	}
	
	/**
	 * @return the exclusion
	 */
	public int getExclusion() {
		return exclusion;
	}

	/**
	 * @param exclusion the exclusion to set
	 */
	public void setExclusion(int exclusion) {
		this.exclusion = exclusion;
	}

	/**
	 * @return the sortOrder
	 */
	public String getSortOrder() {
		return sortOrder;
	}

	/**
	 * @param sortOrder the sortOrder to set
	 */
	public void setSortOrder(String sortOrder) {
		this.sortOrder = sortOrder;
	}

	/**
	 * @return the sortDirection
	 */
	public String getSortDirection() {
		return sortDirection;
	}

	/**
	 * @param sortDirection the sortDirection to set
	 */
	public void setSortDirection(String sortDirection) {
		this.sortDirection = sortDirection;
	}
	
	/**
	 * @return the openerCallback
	 */
	public String getOpenerCallback() {
		return openerCallback;
	}

	/**
	 * @param openerCallback the openerCallback to set
	 */
	public void setOpenerCallback(String openerCallback) {
		this.openerCallback = openerCallback;
	}
	
	/**
	 * @return the extra1
	 */
	public String getExtra1() {
		return extra1;
	}

	/**
	 * @param extra1 the extra1 to set
	 */
	public void setExtra1(String extra1) {
		this.extra1 = extra1;
	}

	/**
	 * @return the extra2
	 */
	public String getExtra2() {
		return extra2;
	}

	/**
	 * @param extra2 the extra2 to set
	 */
	public void setExtra2(String extra2) {
		this.extra2 = extra2;
	}
	
	/**
	 * @return the sortOverride
	 */
	public String getSortOverride() {
		return sortOverride;
	}

	/**
	 * @param sortOverride the sortOverride to set
	 */
	public void setSortOverride(String sortOverride) {
		this.sortOverride = sortOverride;
	}
	
	/**
	 * @return the listType
	 */
	public String getListType() {
		return listType;
	}

	/**
	 * @param listType the listType to set
	 */
	public void setListType(String listType) {
		this.listType = listType;
	}
	
	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * 페이지 번호를 뺀 쿼리 스트링을 만들어서 반환한다.
	 * 상속받은 클래스는 경우에 따라 재 구현해야함. 
	 * 
	 * @return 쿼리 스트링
	 */
	public String getQueryString(){
		StringBuilder sb = new StringBuilder(100);

		if (StringUtils.isNotBlank(this.getSc())) {
			sb.append("&amp;sc=");
			sb.append(this.getSc());
		}

		if (StringUtils.isNotBlank(this.getSv())) {
			sb.append("&amp;sv=");
			sb.append(this.getSv());
		}
		
		if ( this.getPageSize() != 10 && this.getPageSize() > 0 ) {
			sb.append("&amp;pageSize=");
			sb.append(this.getPageSize());
		}
		
		if (StringUtils.isNotBlank(this.getSortOrder())) {
			sb.append("&amp;sortOrder=");
			sb.append(this.getSortOrder());
		}
		
		if (StringUtils.isNotBlank(this.getSortDirection())) {
			sb.append("&amp;sortDirection=");
			sb.append(this.getSortDirection());
		}
		
		if( StringUtils.isNotBlank(this.getOpenerCallback()) ){
			sb.append("&amp;openerCallback=");
			sb.append(this.getOpenerCallback());
		}
		
		if( StringUtils.isNotBlank(this.getExtra1()) ){
			sb.append("&amp;extra1=");
			sb.append(this.getExtra1());
		}
		
		if( StringUtils.isNotBlank(this.getExtra2()) ){
			sb.append("&amp;extra2=");
			sb.append(this.getExtra2());
		}
		
		if( StringUtils.isNotBlank(this.getListType()) ){
			sb.append("&amp;listType=");
			sb.append(this.getListType());
		}
		
		return sb.toString();
	};

	/*
	 * 한글이 깨질 경우 처리 <br />
	 * WAS에서 Get 파라메터 UTF-8인코딩 설정 안되어 있고. 서버 설정변경도 하기 힘든 경우 임의로 변경해준다.
	 * 
	 * @param readCharSet
	 * @param writeCharSet
	 */
	private void fixBrokenSv(String fromCharSet, String toCharSet) {
		if (StringUtils.isNotBlank(this.getSv())) {
			try {
				this.setSv(new String(this.getSv().getBytes(fromCharSet), toCharSet));
			} catch (UnsupportedEncodingException e) {
				LOGGER.error("[UnsupportedEncodingException] Try/Catch... : "+ e.getMessage());
			}
		}
	};
	
	public static String[] BLACKLIST = {"--",";--",";","/*","*/","@@","@","\"","'",
        "char","nchar","varchar","nvarchar",
         "alter","begin","cast","create","cursor","declare","delete","drop","end",
         "exec","execute","fetch","insert","kill","open",
         "select", "sys","sysobjects","syscolumns",
         "table","update"};
	
	/**
	 * 한글이 깨질 경우의 처리 디폴트로 변환처리
	 * <pre>
	 * - 기본적으로 ASAPRO_CONFIG_OPTION 테이블의 값을 사용해서 한글깨짐을 방지한다.
	 * </pre>
	 */
	public void fixBrokenSvByDefaultCharsets() {
		ApplicationContext context = ApplicationContextProvider.getApplicationContext();
		ConfigService configService = context.getBean(ConfigService.class);
		Config config = configService.getConfig("global");
		if( "true".equalsIgnoreCase(config.getOption("fix_kor_active")) ){
			this.fixBrokenSv( StringUtils.defaultString(config.getOption("fix_kor_from"), "8859_1") , StringUtils.defaultString(config.getOption("fix_kor_to"), "UTF-8") );
		}
		if( StringUtils.isNotBlank(this.getSortOrder()) ){
			for( String black : BLACKLIST ){
				if( this.getSortOrder().contains(black) ){
					this.setSortOrder(null);
					break;
				}
			}
		}
		if( StringUtils.isNotBlank(this.getSortDirection()) ){
			for( String black : BLACKLIST ){
				if( this.getSortDirection().contains(black) ){
					this.setSortDirection(null);
					break;
				}
			}
		}
	}
	
	/**
	 * 기본 정렬 속성을 지정한다.
	 * @param sortOrder
	 * @param sortDirection
	 */
	public void setDefaultSort(String sortOrder, String sortDirection){
		if( StringUtils.isBlank(this.getSortOrder()) ){
			if( StringUtils.isNotBlank(sortOrder) ){
				this.setSortOrder(sortOrder);
			}
		}
		if( StringUtils.isBlank(this.getSortDirection()) ){
			if( StringUtils.isNotBlank(sortDirection) ){
				this.setSortDirection(sortDirection);
			}
		}
	}
	
}
