/**
 * 
 */
package egovframework.com.asapro.content.service;

import java.io.Serializable;
import java.util.Date;

import egovframework.com.asapro.member.admin.service.AdminMember;
import egovframework.com.asapro.menu.service.Menu;
import egovframework.com.asapro.site.service.MultiSiteVO;
//import egovframework.com.asapro.watchdog.aop.WatchDog;

/**
 * 콘텐츠 VO
 * @author yckim
 * @since 2018. 7. 20.
 *
 */
@SuppressWarnings("serial")
public class Content extends MultiSiteVO implements Serializable{
	
//	@WatchDog
	private Menu menu = new Menu();
	private String content = "";	//콘텐츠내용 (html)
	private String contentPlain = "";	//콘텐츠내용 (text)
	private String contentStatus = "publish";	//게시상테
//	@WatchDog
	private Integer contentVer = 0;	//버전
	private Date contentRegDate;	//등록일
	private Date contentLastModified;	//수정일
	private AdminMember contentLastWorker;	//관리자
	private String contentMemo = "";	//비고내용
	private String contentTitle = "";	//콘텐츠 제목
	private Integer contentId;	//	콘텐츠 아이디
	private Integer contentRoot;	//	최초 콘텐츠 아이디
	
	/**
	 * @return the menu
	 */
	public Menu getMenu() {
		return menu;
	}
	/**
	 * @param menu the menu to set
	 */
	public void setMenu(Menu menu) {
		this.menu = menu;
	}
	/**
	 * @return the content
	 */
	public String getContent() {
		return content;
	}
	/**
	 * @param content the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}
	/**
	 * @return the contentPlain
	 */
	public String getContentPlain() {
		return contentPlain;
	}
	/**
	 * @param contentPlain the contentPlain to set
	 */
	public void setContentPlain(String contentPlain) {
		this.contentPlain = contentPlain;
	}
	/**
	 * @return the contentStatus
	 */
	public String getContentStatus() {
		return contentStatus;
	}
	/**
	 * @param contentStatus the contentStatus to set
	 */
	public void setContentStatus(String contentStatus) {
		this.contentStatus = contentStatus;
	}
	/**
	 * @return the contentVer
	 */
	public Integer getContentVer() {
		return contentVer;
	}
	/**
	 * @param contentVer the contentVer to set
	 */
	public void setContentVer(Integer contentVer) {
		this.contentVer = contentVer;
	}
	/**
	 * @return the contentRegDate
	 */
	public Date getContentRegDate() {
		return contentRegDate;
	}
	/**
	 * @param contentRegDate the contentRegDate to set
	 */
	public void setContentRegDate(Date contentRegDate) {
		this.contentRegDate = contentRegDate;
	}
	/**
	 * @return the contentLastModified
	 */
	public Date getContentLastModified() {
		return contentLastModified;
	}
	/**
	 * @param contentLastModified the contentLastModified to set
	 */
	public void setContentLastModified(Date contentLastModified) {
		this.contentLastModified = contentLastModified;
	}
	
	/**
	 * @return the contentLastWorker
	 */
	public AdminMember getContentLastWorker() {
		return contentLastWorker;
	}
	/**
	 * @param contentLastWorker the contentLastWorker to set
	 */
	public void setContentLastWorker(AdminMember contentLastWorker) {
		this.contentLastWorker = contentLastWorker;
	}
	/**
	 * @return the contentMemo
	 */
	public String getContentMemo() {
		return contentMemo;
	}
	/**
	 * @param contentMemo the contentMemo to set
	 */
	public void setContentMemo(String contentMemo) {
		this.contentMemo = contentMemo;
	}
	/**
	 * @return the contentTitle
	 */
	public String getContentTitle() {
		return contentTitle;
	}
	/**
	 * @param contentTitle the contentTitle to set
	 */
	public void setContentTitle(String contentTitle) {
		this.contentTitle = contentTitle;
	}
	/**
	 * @return the contentId
	 */
	public Integer getContentId() {
		return contentId;
	}
	/**
	 * @param contentId the contentId to set
	 */
	public void setContentId(Integer contentId) {
		this.contentId = contentId;
	}
	/**
	 * @return the contentRoot
	 */
	public Integer getContentRoot() {
		return contentRoot;
	}
	/**
	 * @param contentRoot the contentRoot to set
	 */
	public void setContentRoot(Integer contentRoot) {
		this.contentRoot = contentRoot;
	}
	
}
