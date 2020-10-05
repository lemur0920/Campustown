/**
 * 
 */
package egovframework.com.asapro.commonContent.service;

import java.io.Serializable;

import egovframework.com.asapro.site.service.MultiSiteVO;

/**
 * 공통 콘텐츠 릴레이션 VO
 * @author yckim
 * @since 2018. 8. 27.
 *
 */
@SuppressWarnings("serial")
public class CommonContentRelation extends MultiSiteVO implements Serializable{
	
	private Integer comContId;	//공통콘텐츠 아이디
	private String programId = "";	//적용프로그램 아이디
	private String moduleCode = "";	//적용모듈 코드
	/**
	 * @return the comContId
	 */
	public Integer getComContId() {
		return comContId;
	}
	/**
	 * @param comContId the comContId to set
	 */
	public void setComContId(Integer comContId) {
		this.comContId = comContId;
	}
	/**
	 * @return the programId
	 */
	public String getProgramId() {
		return programId;
	}
	/**
	 * @param programId the programId to set
	 */
	public void setProgramId(String programId) {
		this.programId = programId;
	}
	/**
	 * @return the moduleCode
	 */
	public String getModuleCode() {
		return moduleCode;
	}
	/**
	 * @param moduleCode the moduleCode to set
	 */
	public void setModuleCode(String moduleCode) {
		this.moduleCode = moduleCode;
	}
	
}
