/**
 * 
 */
package egovframework.com.asapro.comment.service;

import org.apache.commons.lang3.StringUtils;

import egovframework.com.asapro.support.pagination.PagingSearch;

/**
 * 댓글검색 vO
 * @author yckim
 * @since 2019. 7. 12.
 *
 */
public class CommentSearch extends PagingSearch{
	
	private String cmtModule;	//댓글 사용 모듈
	private String cmtModuleSub;	//댓글 사용 하위모듈
	private String cmtModCategory;	//댓글 사용 모듈 카테고리
	private String cmtModItemId;	//댓글 사용 아이템 아이디
	private String cmtStatus;	//댓글 상태

	/**
	 * @return the cmtModule
	 */
	public String getCmtModule() {
		return cmtModule;
	}
	/**
	 * @param cmtModule the cmtModule to set
	 */
	public void setCmtModule(String cmtModule) {
		this.cmtModule = cmtModule;
	}
	/**
	 * @return the cmtModuleSub
	 */
	public String getCmtModuleSub() {
		return cmtModuleSub;
	}
	/**
	 * @param cmtModuleSub the cmtModuleSub to set
	 */
	public void setCmtModuleSub(String cmtModuleSub) {
		this.cmtModuleSub = cmtModuleSub;
	}
	/**
	 * @return the cmtModCategory
	 */
	public String getCmtModCategory() {
		return cmtModCategory;
	}
	/**
	 * @param cmtModCategory the cmtModCategory to set
	 */
	public void setCmtModCategory(String cmtModCategory) {
		this.cmtModCategory = cmtModCategory;
	}
	/**
	 * @return the cmtModItemId
	 */
	public String getCmtModItemId() {
		return cmtModItemId;
	}
	/**
	 * @param cmtModItemId the cmtModItemId to set
	 */
	public void setCmtModItemId(String cmtModItemId) {
		this.cmtModItemId = cmtModItemId;
	}
	/**
	 * @return the cmtStatus
	 */
	public String getCmtStatus() {
		return cmtStatus;
	}
	/**
	 * @param cmtStatus the cmtStatus to set
	 */
	public void setCmtStatus(String cmtStatus) {
		this.cmtStatus = cmtStatus;
	}

	/* (non-Javadoc)
	 * @see egovframework.com.asapro.support.pagination.PagingSearch#getQueryString()
	 */
	@Override
	public String getQueryString() {
		
		StringBuilder sb = new StringBuilder(100);
		sb.append(super.getQueryString());
		
		if( StringUtils.isNotBlank(this.getCmtModule()) ){
			sb.append("&amp;cmtModule=");
			sb.append(this.getCmtModule());
		}
		if( StringUtils.isNotBlank(this.getCmtModuleSub()) ){
			sb.append("&amp;cmtModuleSub=");
			sb.append(this.getCmtModuleSub());
		}
		if( StringUtils.isNotBlank(this.getCmtModCategory()) ){
			sb.append("&amp;cmtModCategory=");
			sb.append(this.getCmtModCategory());
		}
		if( StringUtils.isNotBlank(this.getCmtModItemId()) ){
			sb.append("&amp;cmtModItemId=");
			sb.append(this.getCmtModItemId());
		}
		if( StringUtils.isNotBlank(this.getCmtStatus()) ){
			sb.append("&amp;cmtStatus=");
			sb.append(this.getCmtStatus());
		}
		
		return sb.toString();
	}
	
	
}
