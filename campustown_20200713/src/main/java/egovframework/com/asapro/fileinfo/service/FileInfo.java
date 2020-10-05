/**
 * 
 */
package egovframework.com.asapro.fileinfo.service;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.springframework.web.multipart.MultipartFile;

//import egovframework.com.asapro.archive.service.Archive;
import egovframework.com.asapro.site.service.MultiSiteVO;
import egovframework.com.asapro.support.Constant;
import egovframework.com.asapro.support.util.AsaproUtils;
//import egovframework.com.asapro.watchdog.aop.WatchDog;

/**
 * 파일정보
 * @author yckim
 * @since 2018. 6. 12.
 *
 */
public class FileInfo extends MultiSiteVO{
	
//	@WatchDog
	private Integer fileId = 0;
	private String memberId;
	private String fileModule;
	private String fileModuleId;
	private String fileModuleSub;
	private String fileModuleSubId;
	private String fileOriginalName;
	private String filePath;
	private String fileThumbnailPath = "";
	private String fileExt;
	private String fileMimeType;
	private String fileMediaType;//IMAGE, DOCUMENT, VIDEO, AUDIO, ETC
	private long fileSize;
	private Date fileRegDate;
	private String fileAltText = "";
	private Integer fileDownloadCount;
	private String fileUUID;	//파일 고유아이디
	private Integer fileOriImageWidth = 0;	//이미지가로길이
	private Integer fileOriImageHeight = 0;	//이미지세로길이
	private String fileAttachmentType = "";	//첨부유형
	private String fileServletUrl = "";	//서블릿 호출 ur
	private String fileServletThumbnailUrl = "";	//썸네일 서블릿 호출 url
	
	//컬럼없음 - 업로드 결과체크용
	private boolean fileUploadSuccess = true;
	private int fileUploadErrorCode;
	
	private String fileSizeString;//1KB, 1MB...
	
	private MultipartFile file;	//파일첨부필드
	
	//첨부된 곳 아카이브만...
//	@JsonIgnore
//	private Set<Archive> fileAttachedArchiveSet;
	
	
	
	/**
	 * @return the fileUploadSuccess
	 */
	public boolean isFileUploadSuccess() {
		return fileUploadSuccess;
	}

	/**
	 * @param fileUploadSuccess the fileUploadSuccess to set
	 */
	public void setFileUploadSuccess(boolean fileUploadSuccess) {
		this.fileUploadSuccess = fileUploadSuccess;
	}

	/**
	 * @return the fileUploadErrorCode
	 */
	public int getFileUploadErrorCode() {
		return fileUploadErrorCode;
	}

	/**
	 * @param fileUploadErrorCode the fileUploadErrorCode to set
	 */
	public void setFileUploadErrorCode(int fileUploadErrorCode) {
		this.fileUploadErrorCode = fileUploadErrorCode;
	}

	/**
	 * @return the fileId
	 */
	public Integer getFileId() {
		return fileId;
	}

	/**
	 * @param fileId the fileId to set
	 */
	public void setFileId(Integer fileId) {
		this.fileId = fileId;
	}

	/**
	 * @return the memberId
	 */
	public String getMemberId() {
		return memberId;
	}

	/**
	 * @param memberId the memberId to set
	 */
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

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
		return fileModuleId == null ? "" : fileModuleId;
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
		return fileModuleSub == null ? "" : fileModuleSub;
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
		return fileModuleSubId == null ? "" : fileModuleSubId;
	}

	/**
	 * @param fileModuleSubId the fileModuleSubId to set
	 */
	public void setFileModuleSubId(String fileModuleSubId) {
		this.fileModuleSubId = fileModuleSubId;
	}

	/**
	 * @return the fileOriginalName
	 */
	public String getFileOriginalName() {
		return fileOriginalName;
	}

	/**
	 * @param fileOriginalName the fileOriginalName to set
	 */
	public void setFileOriginalName(String fileOriginalName) {
		this.fileOriginalName = fileOriginalName;
	}

	/**
	 * @return the filePath
	 */
	public String getFilePath() {
		return filePath;
	}

	/**
	 * @param filePath the filePath to set
	 */
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	/**
	 * @return the fileExt
	 */
	public String getFileExt() {
		return fileExt;
	}

	/**
	 * @param fileExt the fileExt to set
	 */
	public void setFileExt(String fileExt) {
		this.fileExt = fileExt;
	}

	/**
	 * @return the fileMimeType
	 */
	public String getFileMimeType() {
		return fileMimeType;
	}

	/**
	 * @param fileMimeType the fileMimeType to set
	 */
	public void setFileMimeType(String fileMimeType) {
		this.fileMimeType = fileMimeType;
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
	 * @return the fileSize
	 */
	public long getFileSize() {
		return fileSize;
	}

	/**
	 * @param fileSize the fileSize to set
	 */
	public void setFileSize(long fileSize) {
		this.fileSize = fileSize;
		this.fileSizeString = AsaproUtils.getFileSizeString(fileSize, true, true);
	}

	/**
	 * @return the fileRegDate
	 */
	public Date getFileRegDate() {
		return fileRegDate;
	}

	/**
	 * @param fileRegDate the fileRegDate to set
	 */
	public void setFileRegDate(Date fileRegDate) {
		this.fileRegDate = fileRegDate;
	}

	/**
	 * @return the fileAltText
	 */
	public String getFileAltText() {
		return fileAltText;
	}

	/**
	 * @param fileAltText the fileAltText to set
	 */
	public void setFileAltText(String fileAltText) {
		this.fileAltText = fileAltText;
	}

	/**
	 * @return the fileDownloadCount
	 */
	public Integer getFileDownloadCount() {
		return fileDownloadCount;
	}

	/**
	 * @param fileDownloadCount the fileDownloadCount to set
	 */
	public void setFileDownloadCount(Integer fileDownloadCount) {
		this.fileDownloadCount = fileDownloadCount;
	}

	/**
	 * @return the fileSizeString
	 */
	public String getFileSizeString() {
		return fileSizeString;
	}

	/**
	 * @param fileSizeString the fileSizeString to set
	 */
	public void setFileSizeString(String fileSizeString) {
		this.fileSizeString = fileSizeString;
	}

	/**
	 * @return the fileThumbnailPath
	 */
	public String getFileThumbnailPath() {
		return fileThumbnailPath;
	}

	/**
	 * @param fileThumbnailPath the fileThumbnailPath to set
	 */
	public void setFileThumbnailPath(String fileThumbnailPath) {
		this.fileThumbnailPath = fileThumbnailPath;
	}

	/**
	 * @return the fileAttachedArchiveSet
	 */
//	public Set<Archive> getFileAttachedArchiveSet() {
//		return fileAttachedArchiveSet;
//	}

	/**
	 * @param fileAttachedArchiveSet the fileAttachedArchiveSet to set
	 */
//	public void setFileAttachedArchiveSet(Set<Archive> fileAttachedArchiveSet) {
//		this.fileAttachedArchiveSet = fileAttachedArchiveSet;
//	}
	
	/**
	 * @return the fileUUID
	 */
	public String getFileUUID() {
		return fileUUID;
	}

	/**
	 * @param fileUUID the fileUUID to set
	 */
	public void setFileUUID(String fileUUID) {
		this.fileUUID = fileUUID;
	}
	
	/**
	 * @return the fileOriImageWidth
	 */
	public Integer getFileOriImageWidth() {
		return fileOriImageWidth;
	}

	/**
	 * @param fileOriImageWidth the fileOriImageWidth to set
	 */
	public void setFileOriImageWidth(Integer fileOriImageWidth) {
		this.fileOriImageWidth = fileOriImageWidth;
	}

	/**
	 * @return the fileOriImageHeight
	 */
	public Integer getFileOriImageHeight() {
		return fileOriImageHeight;
	}

	/**
	 * @param fileOriImageHeight the fileOriImageHeight to set
	 */
	public void setFileOriImageHeight(Integer fileOriImageHeight) {
		this.fileOriImageHeight = fileOriImageHeight;
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
	 * @return the fileServletUrl
	 */
	public String getFileServletUrl() {
		return Constant.CONTEXT_PATH + fileServletUrl;
	}
	
	/**
	 * @param fileServletUrl the fileServletUrl to set
	 */
	public void setFileServletUrl(String fileServletUrl) {
		this.fileServletUrl = fileServletUrl;
	}
	
	/**
	 * @return the fileServletThumbnailUrl
	 */
	public String getFileServletThumbnailUrl() {
		if(StringUtils.isNotBlank(fileServletThumbnailUrl) ){
			return Constant.CONTEXT_PATH + fileServletThumbnailUrl;
		}else{
			return fileServletThumbnailUrl;
		}
	}
	
	/**
	 * @param fileServletThumbnailUrl the fileServletThumbnailUrl to set
	 */
	public void setFileServletThumbnailUrl(String fileServletThumbnailUrl) {
		this.fileServletThumbnailUrl = fileServletThumbnailUrl;
	}
	
	/**
	 * @return the file
	 */
	public MultipartFile getFile() {
		return file;
	}
	
	/**
	 * @param file the file to set
	 */
	public void setFile(MultipartFile file) {
		this.file = file;
	}
	
	//============================================================================================

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((fileId == null) ? 0 : fileId.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FileInfo other = (FileInfo) obj;
		if (fileId == null) {
			if (other.fileId != null)
				return false;
		} else if (!fileId.equals(other.fileId))
			return false;
		return true;
	}

	/*@Override
	public String toString() {
		return "FileInfo\n[fileId=" + fileId + "\n, memberId=" + memberId + "\n, fileModule=" + fileModule + "\n, fileModuleId=" + fileModuleId + "\n, fileModuleSub=" + fileModuleSub
				+ "\n, fileModuleSubId=" + fileModuleSubId + "\n, fileOriginalName=" + fileOriginalName + "\n, filePath=" + filePath + "\n, fileThumbnailPath=" + fileThumbnailPath + "\n, fileExt="
				+ fileExt + "\n, fileMimeType=" + fileMimeType + "\n, fileMediaType=" + fileMediaType + "\n, fileSize=" + fileSize + "\n, fileRegDate=" + fileRegDate + "\n, fileAltText="
				+ fileAltText + "\n, fileDownloadCount=" + fileDownloadCount + "\n, fileUploadSuccess=" + fileUploadSuccess + "\n, fileUploadErrorCode=" + fileUploadErrorCode + "\n, fileSizeString="
				+ fileSizeString + "\n, fileAttachedArchiveSet=" + fileAttachedArchiveSet + "\n, fileServletUrl=" + fileServletUrl + "\n, fileServletThumbnailUrl=" + fileServletThumbnailUrl + "]\n";
	}*/
	/**
     * toString 메소드를 대치한다.
     */
	@Override
    public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
	
	
}