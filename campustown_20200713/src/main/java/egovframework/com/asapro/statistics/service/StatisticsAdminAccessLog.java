/**
 * 
 */
package egovframework.com.asapro.statistics.service;

import java.util.Date;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

import com.ibm.icu.text.SimpleDateFormat;

import egovframework.com.asapro.member.admin.service.AdminMember;
import egovframework.com.asapro.site.service.MultiSiteVO;
import egovframework.com.asapro.site.service.Site;
import egovframework.com.asapro.support.exception.AsaproException;
import egovframework.com.asapro.support.util.AsaproUtils;

/**
 * 관리자 접속로그
 * @author yckim
 * @since 2019. 01. 02.
 *
 */
public class StatisticsAdminAccessLog extends MultiSiteVO{
	
	private Integer logId;
	private String adminId;
	private String adminName; 
	private String logRemoteIp;
	private String siteId;
	private Date logRegDate;
	
	/**
	 * default constructor
	 */
	public StatisticsAdminAccessLog(){
		super();
	}
	/**
	 * HttpServletRequest 를 인자로 받는 생성자
	 * @param request
	 */
	public StatisticsAdminAccessLog(HttpServletRequest request) {
		this(request, null);
	}
	
	/**
	 * request와 currentSite를 받는 생성자
	 * @param request
	 * @param currentSite
	 */
	public StatisticsAdminAccessLog(HttpServletRequest request, Site currentSite){
		this(request, currentSite, null);
	}

	/**
	 * request와 currentSite와 currentAdmin을 받는 생성자
	 * @param request
	 * @param currentSite
	 * @param currentAdmin
	 */
	public StatisticsAdminAccessLog(HttpServletRequest request, Site currentSite, AdminMember currentAdmin){
		if( currentSite == null ){
			currentSite = (Site) request.getAttribute("currentSite");
			if( currentSite == null ){
				throw new AsaproException("current stie should not be null!");
			}
		}
		if( currentAdmin == null ){
			currentAdmin = (AdminMember) request.getSession().getAttribute("currentAdmin");
			if( currentAdmin == null ){
				throw new AsaproException("current admin should not be null!");
			}
		}
		this.adminId = currentAdmin.getAdminId();
		this.adminName = currentAdmin.getAdminName();
		this.logRemoteIp = StringUtils.abbreviate(AsaproUtils.getRempoteIp(request), 990);
		this.siteId = currentSite.getSiteId();
		this.logRegDate = new Date();
	}
	/**
	 * @return the logId
	 */
	public Integer getLogId() {
		return logId;
	}
	/**
	 * @param logId the logId to set
	 */
	public void setLogId(Integer logId) {
		this.logId = logId;
	}
	/**
	 * @return the adminId
	 */
	public String getAdminId() {
		return adminId;
	}
	/**
	 * @param adminId the adminId to set
	 */
	public void setAdminId(String adminId) {
		this.adminId = adminId;
	}
	/**
	 * @return the adminName
	 */
	public String getAdminName() {
		return adminName;
	}
	/**
	 * @param adminName the adminName to set
	 */
	public void setAdminName(String adminName) {
		this.adminName = adminName;
	}
	/**
	 * @return the logRemoteIp
	 */
	public String getLogRemoteIp() {
		return logRemoteIp;
	}
	/**
	 * @param logRemoteIp the logRemoteIp to set
	 */
	public void setLogRemoteIp(String logRemoteIp) {
		this.logRemoteIp = logRemoteIp;
	}
	/**
	 * @return the siteId
	 */
	public String getSiteId() {
		return siteId;
	}
	/**
	 * @param siteId the siteId to set
	 */
	public void setSiteId(String siteId) {
		this.siteId = siteId;
	}
	/**
	 * @return the logRegDate
	 */
	public Date getLogRegDate() {
		return logRegDate;
	}
	/**
	 * @param logRegDate the logRegDate to set
	 */
	public void setLogRegDate(Date logRegDate) {
		this.logRegDate = logRegDate;
	}
	
}
