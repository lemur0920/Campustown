/**
 * 
 */
package egovframework.com.asapro.support.taglib;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import org.apache.commons.lang3.ArrayUtils;

import egovframework.com.asapro.fileinfo.service.FileInfo;

/**
 * 첨부파일 아이콘 표시 커스텀 태그
 * @author yckim
 * @since 2019. 4. 25.
 *
 */
public class FileIcon extends SimpleTagSupport {

	private FileInfo fileInfo;
	
	// /design/images/icon/ 디렉토리에 있는 아이콘 gif 목록임
	private static final String[] EXTENTIONS = {
		"asf"
		, "asx"
		, "avi"
		, "bmp"
		, "chm"
		, "dcr"
		, "dir"
		, "doc"
		, "docx"
		, "dvi"
		, "exe"
		, "gif"
		, "htm"
		, "html"
		, "hwp"
		, "jpeg"
		, "jpg"
		, "mid"
		, "mov"
		, "mp3"
		, "mp4"
		, "mpeg"
		, "mpg"
		, "pdf"
		, "php"
		, "png"
		, "ppt"
		, "pptx"
		, "qt"
		, "ram"
		, "rar"
		, "rm"
		, "rtf"
		, "smi"
		, "smil"
		, "sql"
		, "srt"
		, "swf"
		, "tar"
		, "txt"
		, "url"
		, "wav"
		, "wma"
		, "wmv"
		, "xls"
		, "xlsx"
		, "xml"
		, "zip"
	};
	
	
	/**
	 * @param fileInfo the fileInfo to set
	 */

	public void setFileInfo(FileInfo fileInfo) {
		this.fileInfo = fileInfo;
	}

	@Override
	public void doTag() throws JspException, IOException {
		StringBuilder sb = new StringBuilder(100);
		sb.append("<img src=\"");
		
		sb.append("/design/common/images/fileicon/");
		if( ArrayUtils.contains(EXTENTIONS, fileInfo.getFileExt().toLowerCase() ) ){
			sb.append(fileInfo.getFileExt().toLowerCase());
		} else {
			sb.append("etc");
		}
		sb.append(".png");
		
		sb.append("\" alt=\"");
		sb.append(fileInfo.getFileOriginalName());
		sb.append("\" class=\"file-icon\" style=\"width: 26px;height: 26px;\" />");
		getJspContext().getOut().write(sb.toString());
	}
	
	
}
