/**
 * 
 */
package egovframework.com.asapro.fileinfo.service;

import java.util.ArrayList;
import java.util.List;

/**
 * 업로드 결과
 * @author yckim
 * @since 2018. 6. 12.
 *
 */
public class FileInfoUploadResult {
	
	public static final int ERROR_NOT_UPLOADABLE_FILE = 100;
	public static final int ERROR_UPLOAD_SIZE_OVER_FILE = 200;
	public static final int ERROR_ALT_TEXT_MISSING_FILE = 300;
	public static final int ERROR_USE_MULTIMEDIA_UPLOADER = 400;
	public static final int ERROR_USE_NORMAL_UPLOADER = 500;
	public static final int ERROR_NO_FILE = 999;
	
	private List<String> notUploadableFiles = new ArrayList<String>();
	private List<String> uploadSizeOverFiles = new ArrayList<String>();
	private List<String> altTextMissingFiles = new ArrayList<String>();
	/**
	 * @return the notUploadableFiles
	 */
	public List<String> getNotUploadableFiles() {
		return notUploadableFiles;
	}
	/**
	 * @param notUploadableFiles the notUploadableFiles to set
	 */
	public void setNotUploadableFiles(List<String> notUploadableFiles) {
		this.notUploadableFiles = notUploadableFiles;
	}
	/**
	 * @return the uploadSizeOverFiles
	 */
	public List<String> getUploadSizeOverFiles() {
		return uploadSizeOverFiles;
	}
	/**
	 * @param uploadSizeOverFiles the uploadSizeOverFiles to set
	 */
	public void setUploadSizeOverFiles(List<String> uploadSizeOverFiles) {
		this.uploadSizeOverFiles = uploadSizeOverFiles;
	}
	/**
	 * @return the altTextMissingFiles
	 */
	public List<String> getAltTextMissingFiles() {
		return altTextMissingFiles;
	}
	/**
	 * @param altTextMissingFiles the altTextMissingFiles to set
	 */
	public void setAltTextMissingFiles(List<String> altTextMissingFiles) {
		this.altTextMissingFiles = altTextMissingFiles;
	}
	/**
	 * 파일 업로드 에러정보를 추가한다.
	 * @param fileInfo
	 */
	public void addErrorInfo(FileInfo fileInfo) {
		if( !fileInfo.isFileUploadSuccess() ){
			if( fileInfo.getFileUploadErrorCode() == ERROR_NOT_UPLOADABLE_FILE ){
				this.notUploadableFiles.add(fileInfo.getFileOriginalName());
			}
			if( fileInfo.getFileUploadErrorCode() == ERROR_UPLOAD_SIZE_OVER_FILE ){
				this.uploadSizeOverFiles.add(fileInfo.getFileOriginalName());
			}
			if( fileInfo.getFileUploadErrorCode() == ERROR_ALT_TEXT_MISSING_FILE ){
				this.altTextMissingFiles.add(fileInfo.getFileOriginalName());
			}
		}
	}
	
	/**
	 * 업로드 결과에 에러가 있는지 여부를 반환한다.
	 * @return
	 */
	public boolean hasErrors(){
		if( !this.notUploadableFiles.isEmpty() || !this.uploadSizeOverFiles.isEmpty() || !this.altTextMissingFiles.isEmpty() ){
			return true;
		}
		return false;
	}
	
}
