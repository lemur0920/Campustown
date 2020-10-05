/**
 * 
 */
package egovframework.com.asapro.fileinfo.service;

import org.apache.commons.lang3.StringUtils;

import egovframework.com.asapro.support.pagination.PagingSearch;
import egovframework.com.cmm.service.EgovProperties;

/**
 *
 * @author yckim
 * @since 2018. 6. 12.
 *
 */
public class FileInfoSearch extends PagingSearch{
	
	private String fileModule;
	private String fileModuleId;
	private String fileModuleSub;
	private String fileModuleSubId;
	private String fileMediaType;
	private String fileAttachmentType;
	//파일관리시스템 목록에 개인정보등의 노출이 필요하지 않는 모듈을 재외하기위해사용
	private String fileExceptModule = EgovProperties.getProperty("Globals.fileExceptModule").trim();
	
	/**
	 * @return the fileModule
	 */
	public String getFileModule() {
		return fileModule;
	}
	/**
	 * @param fileModule the fileModule to set
	 */
	public void setFileModule(String fileModule) {
		this.fileModule = fileModule;
	}
	/**
	 * @return the fileModuleId
	 */
	public String getFileModuleId() {
		return fileModuleId;
	}
	/**
	 * @param fileModuleId the fileModuleId to set
	 */
	public void setFileModuleId(String fileModuleId) {
		this.fileModuleId = fileModuleId;
	}
	/**
	 * @return the fileModuleSub
	 */
	public String getFileModuleSub() {
		return fileModuleSub;
	}
	/**
	 * @param fileModuleSub the fileModuleSub to set
	 */
	public void setFileModuleSub(String fileModuleSub) {
		this.fileModuleSub = fileModuleSub;
	}
	/**
	 * @return the fileModuleSubId
	 */
	public String getFileModuleSubId() {
		return fileModuleSubId;
	}
	/**
	 * @param fileModuleSubId the fileModuleSubId to set
	 */
	public void setFileModuleSubId(String fileModuleSubId) {
		this.fileModuleSubId = fileModuleSubId;
	}
	/**
	 * @return the fileMediaType
	 */
	public String getFileMediaType() {
		return fileMediaType;
	}
	/**
	 * @param fileMediaType the fileMediaType to set
	 */
	public void setFileMediaType(String fileMediaType) {
		this.fileMediaType = fileMediaType;
	}
	
	/**
	 * @return the fileAttachmentType
	 */
	public String getFileAttachmentType() {
		return fileAttachmentType;
	}
	/**
	 * @param fileAttachmentType the fileAttachmentType to set
	 */
	public void setFileAttachmentType(String fileAttachmentType) {
		this.fileAttachmentType = fileAttachmentType;
	}
	
	/**
	 * @return the fileExceptModule
	 */
	public String getFileExceptModule() {
		return fileExceptModule;
	}
	/**
	 * @param fileExceptModule the fileExceptModule to set
	 */
	public void setFileExceptModule(String fileExceptModule) {
		this.fileExceptModule = fileExceptModule;
	}
	/**
	 * @return the fileExceptModuleArray
	 */
	public String[] getFileExceptModuleArray() {
		if(StringUtils.isNotBlank(this.fileExceptModule) ){
			return this.fileExceptModule.split(",");
		}
		return null;
	}
	@Override
	public String getQueryString() {
		StringBuilder sb = new StringBuilder(100);
		sb.append(super.getQueryString());
		
		if( StringUtils.isNotBlank(this.getFileMediaType()) ){
			sb.append("&amp;fileMediaType=");
			sb.append(this.getFileMediaType());
		}
		if( StringUtils.isNotBlank(this.getFileModule()) ){
			sb.append("&amp;fileModule=");
			sb.append(this.getFileModule());
		}
		if( StringUtils.isNotBlank(this.getFileModuleId()) ){
			sb.append("&amp;fileModuleId=");
			sb.append(this.getFileModuleId());
		}
		if( StringUtils.isNotBlank(this.getFileModuleSub()) ){
			sb.append("&amp;fileModuleSub=");
			sb.append(this.getFileModuleSub());
		}
		if( StringUtils.isNotBlank(this.getFileModuleSubId()) ){
			sb.append("&amp;fileModuleSubId=");
			sb.append(this.getFileModuleSubId());
		}
		if( StringUtils.isNotBlank(this.getFileAttachmentType()) ){
			sb.append("&amp;fileAttachmentType=");
			sb.append(this.getFileAttachmentType());
		}
		if( StringUtils.isNotBlank(this.getFileExceptModule()) ){
			sb.append("&amp;fileExceptModule=");
			sb.append(this.getFileExceptModule());
		}
		
		return sb.toString();
	}
	
}
