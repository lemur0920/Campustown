/**
 * 
 */
package egovframework.com.asapro.fileinfo.service;

import java.io.File;

/**
 * 썸네일 생성 결과
 * 원본이미지 가로 세로 길이 
 * @author yckim
 * @since 2018. 6. 12.
 */
public class MakeThumbnailResult {
	private File resultFile;
	private Integer oriImageWidth;
	private Integer oriImageHeight;
	/**
	 * @return the resultFile
	 */
	public File getResultFile() {
		return resultFile;
	}
	/**
	 * @param resultFile the resultFile to set
	 */
	public void setResultFile(File resultFile) {
		this.resultFile = resultFile;
	}
	/**
	 * @return the oriImageWidth
	 */
	public Integer getOriImageWidth() {
		return oriImageWidth;
	}
	/**
	 * @param oriImageWidth the oriImageWidth to set
	 */
	public void setOriImageWidth(Integer oriImageWidth) {
		this.oriImageWidth = oriImageWidth;
	}
	/**
	 * @return the oriImageHeight
	 */
	public Integer getOriImageHeight() {
		return oriImageHeight;
	}
	/**
	 * @param oriImageHeight the oriImageHeight to set
	 */
	public void setOriImageHeight(Integer oriImageHeight) {
		this.oriImageHeight = oriImageHeight;
	}
}
