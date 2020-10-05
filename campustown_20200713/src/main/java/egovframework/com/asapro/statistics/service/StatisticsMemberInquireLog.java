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
 * 관리자 회원 정보 등록,수정,조회,삭제 작업 로그 VO
 * @author yckim
 * @since 2019. 07. 10.
 *
 */
public class StatisticsMemberInquireLog extends MultiSiteVO{
	
	private Integer inqId;
	private String inqWorkerId;
	private String inqWorkerName; 
	private String inqTargetId;
	private String inqTargetName;
	private String inqUrl;
	private String inqWork;
	private String inqRemoteIp;
	private Date inqRegDate;
	/**
	 * @return the inqId
	 */
	public Integer getInqId() {
		return inqId;
	}
	/**
	 * @param inqId the inqId to set
	 */
	public void setInqId(Integer inqId) {
		this.inqId = inqId;
	}
	/**
	 * @return the inqWorkerId
	 */
	public String getInqWorkerId() {
		return inqWorkerId;
	}
	/**
	 * @param inqWorkerId the inqWorkerId to set
	 */
	public void setInqWorkerId(String inqWorkerId) {
		this.inqWorkerId = inqWorkerId;
	}
	/**
	 * @return the inqWorkerName
	 */
	public String getInqWorkerName() {
		return inqWorkerName;
	}
	/**
	 * @param inqWorkerName the inqWorkerName to set
	 */
	public void setInqWorkerName(String inqWorkerName) {
		this.inqWorkerName = inqWorkerName;
	}
	/**
	 * @return the inqTargetId
	 */
	public String getInqTargetId() {
		return inqTargetId;
	}
	/**
	 * @param inqTargetId the inqTargetId to set
	 */
	public void setInqTargetId(String inqTargetId) {
		this.inqTargetId = inqTargetId;
	}
	/**
	 * @return the inqTargetName
	 */
	public String getInqTargetName() {
		return inqTargetName;
	}
	/**
	 * @param inqTargetName the inqTargetName to set
	 */
	public void setInqTargetName(String inqTargetName) {
		this.inqTargetName = inqTargetName;
	}
	/**
	 * @return the inqUrl
	 */
	public String getInqUrl() {
		return inqUrl;
	}
	/**
	 * @param inqUrl the inqUrl to set
	 */
	public void setInqUrl(String inqUrl) {
		this.inqUrl = inqUrl;
	}
	/**
	 * @return the inqWork
	 */
	public String getInqWork() {
		return inqWork;
	}
	/**
	 * @param inqWork the inqWork to set
	 */
	public void setInqWork(String inqWork) {
		this.inqWork = inqWork;
	}
	/**
	 * @return the inqRemoteIp
	 */
	public String getInqRemoteIp() {
		return inqRemoteIp;
	}
	/**
	 * @param inqRemoteIp the inqRemoteIp to set
	 */
	public void setInqRemoteIp(String inqRemoteIp) {
		this.inqRemoteIp = inqRemoteIp;
	}
	/**
	 * @return the inqRegDate
	 */
	public Date getInqRegDate() {
		return inqRegDate;
	}
	/**
	 * @param inqRegDate the inqRegDate to set
	 */
	public void setInqRegDate(Date inqRegDate) {
		this.inqRegDate = inqRegDate;
	}
	
	
	
}
