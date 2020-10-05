/**
 * 
 */
package egovframework.com.asapro.statistics.service;

import org.apache.commons.lang3.StringUtils;

import egovframework.com.asapro.support.pagination.PagingSearch;

/**
 * 관리자 접속로그 검색 모델
 * @author yckim
 * @since 2019. 01. 02.
 *
 */
public class StatisticsAdminAccessLogSearch extends PagingSearch{
	
	private String logStartDate;	//접속일 시작
	private String logEndDate;	//접속일 종료
	/**
	 * @return the logStartDate
	 */
	public String getLogStartDate() {
		return logStartDate;
	}
	/**
	 * @param logStartDate the logStartDate to set
	 */
	public void setLogStartDate(String logStartDate) {
		this.logStartDate = logStartDate;
	}
	/**
	 * @return the logEndDate
	 */
	public String getLogEndDate() {
		return logEndDate;
	}
	/**
	 * @param logEndDate the logEndDate to set
	 */
	public void setLogEndDate(String logEndDate) {
		this.logEndDate = logEndDate;
	}
	
	@Override
	public String getQueryString() {
		
		StringBuilder sb = new StringBuilder(100);
		sb.append(super.getQueryString());
		
		if( StringUtils.isNotBlank(this.getLogStartDate()) ){
			sb.append("&amp;logStartDate=");
			sb.append(this.getLogStartDate());
		}
		
		if( StringUtils.isNotBlank(this.getLogEndDate()) ){
			sb.append("&amp;logEndDate=");
			sb.append(this.getLogEndDate());
		}
		
		return sb.toString();
	}
	
}
