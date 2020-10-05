/**
 * 
 */
package egovframework.com.asapro.archive.customtype.research.service;


import egovframework.com.asapro.archive.service.Archive;
import egovframework.com.asapro.support.annotation.ArchiveItem;


/**
 * 연구자료 VO
 * 아카이브 커스텀타입
 * @author yckim
 * @since 2019. 2. 26.
 *
 */
@ArchiveItem(customType="research", label="연구자료")
public class Research extends Archive{
	
	private String resResearcher;	//연구자,단체명
	private String resYear;	//연도
	private String resSupport;	//지원처
	private String resSuppProject;	//지원사업명
	/**
	 * @return the resResearcher
	 */
	public String getResResearcher() {
		return resResearcher;
	}
	/**
	 * @param resResearcher the resResearcher to set
	 */
	public void setResResearcher(String resResearcher) {
		this.resResearcher = resResearcher;
	}
	/**
	 * @return the resYear
	 */
	public String getResYear() {
		return resYear;
	}
	/**
	 * @param resYear the resYear to set
	 */
	public void setResYear(String resYear) {
		this.resYear = resYear;
	}
	/**
	 * @return the resSupport
	 */
	public String getResSupport() {
		return resSupport;
	}
	/**
	 * @param resSupport the resSupport to set
	 */
	public void setResSupport(String resSupport) {
		this.resSupport = resSupport;
	}
	/**
	 * @return the resSuppProject
	 */
	public String getResSuppProject() {
		return resSuppProject;
	}
	/**
	 * @param resSuppProject the resSuppProject to set
	 */
	public void setResSuppProject(String resSuppProject) {
		this.resSuppProject = resSuppProject;
	}
	
}
