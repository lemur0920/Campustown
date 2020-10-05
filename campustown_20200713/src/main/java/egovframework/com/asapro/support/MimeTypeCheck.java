/**
 * 
 */
package egovframework.com.asapro.support;

import org.apache.commons.lang3.ArrayUtils;

/**
 * 파일 확장자로 mime type 체크, 확장자와 실제 mime type 이 다르면 확장자로만 판단한다.(어쩔 수 없당.)
 * @author yckim
 * @since 2012. 11. 20.
 */
public class MimeTypeCheck {
	
	public static final String IMAGE = "IMAGE";
	public static final String DOCUMENT = "DOCUMENT";
	public static final String VIDEO = "VIDEO";
	public static final String AUDIO = "AUDIO";
	public static final String ETC = "ETC";
	
	private String extension;
	private String mimeType;
	private String mediaType;
	
	/**
	 * @param clue
	 * @param mimeType
	 * @param mediaType
	 */
	public MimeTypeCheck(String extension, String mimeType, String mediaType) {
		this.extension = extension;
		this.mimeType = mimeType;
		this.mediaType = mediaType;
	}
	/**
	 * @return the extension
	 */
	public String getExtension() {
		return extension;
	}

	/**
	 * @param extension the extension to set
	 */
	public void setExtension(String extension) {
		this.extension = extension;
	}

	/**
	 * @return the mimeType
	 */
	public String getMimeType() {
		return mimeType;
	}
	/**
	 * @param mimeType the mimeType to set
	 */
	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}
	/**
	 * @return the mediaType
	 */
	public String getMediaType() {
		return mediaType;
	}
	/**
	 * @param mediaType the mediaType to set
	 */
	public void setMediaType(String mediaType) {
		this.mediaType = mediaType;
	}
	/**
	 * MIME 유형 및 미디어 유형 정보를 구해서반환한다.
	 * @param extension
	 * @return
	 */
	public static MimeTypeCheck getMimeType(String extensionStr){
		
		String extension = extensionStr.toLowerCase();
		
		//IMAGE
		if( ArrayUtils.contains(new String[]{"bmp"}, extension) ){
			return new MimeTypeCheck(extension, "image/bmp", IMAGE);
		} 
		else if( ArrayUtils.contains(new String[]{"gif"}, extension) ){
			return new MimeTypeCheck(extension, "image/gif", IMAGE);
		}  
		else if( ArrayUtils.contains(new String[]{"ief"}, extension) ){
			return new MimeTypeCheck(extension, "image/ief", IMAGE);
		} 
		else if( ArrayUtils.contains(new String[]{"jpe", "jpeg", "jpg"}, extension) ){
			return new MimeTypeCheck(extension, "image/jpeg", IMAGE);
		} 
		else if( ArrayUtils.contains(new String[]{"pict"}, extension) ){
			return new MimeTypeCheck(extension, "image/pict", IMAGE);
		}
		else if( ArrayUtils.contains(new String[]{"png"}, extension) ){
			return new MimeTypeCheck(extension, "image/png", IMAGE);
		}
		else if( ArrayUtils.contains(new String[]{"tif", "tiff"}, extension) ){
			return new MimeTypeCheck(extension, "image/tiff", IMAGE);
		}
		else if( ArrayUtils.contains(new String[]{"ico"}, extension) ){
			return new MimeTypeCheck(extension, "image/x-icon", IMAGE);
		}
		
		//DOCUMENT
		else if( ArrayUtils.contains(new String[]{"pdf"}, extension) ){
			return new MimeTypeCheck(extension, "application/pdf", DOCUMENT);
		} 
		else if( ArrayUtils.contains(new String[]{"wri"}, extension) ){
			return new MimeTypeCheck(extension, "application/vnd.ms-write", DOCUMENT);
		} 
		else if( ArrayUtils.contains(new String[]{"doc", "dot", "word", "w6w"}, extension) ){
			return new MimeTypeCheck(extension, "application/msword", DOCUMENT);
		}
		else if( ArrayUtils.contains(new String[]{"docm", "dotm"}, extension) ){
			return new MimeTypeCheck(extension, "application/vnd.ms-word", DOCUMENT);
		}
		else if( ArrayUtils.contains(new String[]{"docx", "dotx"}, extension) ){
			return new MimeTypeCheck(extension, "application/vnd.openxmlformats-officedocument.wordprocessingml", DOCUMENT);
		}
		else if( ArrayUtils.contains(new String[]{"rtf"}, extension) ){
			return new MimeTypeCheck(extension, "application/rtf", DOCUMENT);
		}
		else if( ArrayUtils.contains(new String[]{"xlsx", "xltx"}, extension) ){
			return new MimeTypeCheck(extension, "application/vnd.openxmlformats-officedocument.spreadsheetml", DOCUMENT);
		}
		else if( ArrayUtils.contains(new String[]{"xla", "xlc", "xlm", "xls", "xlt", "xlw", "xlam", "xlsb", "xltm"}, extension) ){
			return new MimeTypeCheck(extension, "application/vnd.ms-excel", DOCUMENT);
		}
		else if( ArrayUtils.contains(new String[]{"msg"}, extension) ){
			return new MimeTypeCheck(extension, "application/vnd.ms-outlook", DOCUMENT);
		}
		else if( ArrayUtils.contains(new String[]{"pot", "pps", "ppt", "ppam", "pptm", "sldm", "ppsm", "potm"}, extension) ){
			return new MimeTypeCheck(extension, "application/vnd.ms-powerpoint", DOCUMENT);
		}
		else if( ArrayUtils.contains(new String[]{"pptx", "sldx", "ppsx", "potx"}, extension) ){
			return new MimeTypeCheck(extension, "application/vnd.openxmlformats-officedocument.presentationml", DOCUMENT);
		}
		else if( ArrayUtils.contains(new String[]{"mpp"}, extension) ){
			return new MimeTypeCheck(extension, "application/vnd.ms-project", DOCUMENT);
		}
		else if( ArrayUtils.contains(new String[]{"csv"}, extension) ){
			return new MimeTypeCheck(extension, "text/csv", DOCUMENT);
		}
		else if( ArrayUtils.contains(new String[]{"txt", "asc", "c", "cc", "h"}, extension) ){
			return new MimeTypeCheck(extension, "text/plain", DOCUMENT);
		}
		else if( ArrayUtils.contains(new String[]{"rtx"}, extension) ){
			return new MimeTypeCheck(extension, "text/richtext", DOCUMENT);
		}
		else if( ArrayUtils.contains(new String[]{"tsv"}, extension) ){
			return new MimeTypeCheck(extension, "text/tab-separated-values", DOCUMENT);
		}
		else if( ArrayUtils.contains(new String[]{"htm", "html"}, extension) ){
			return new MimeTypeCheck(extension, "text/html", DOCUMENT);
		}
		else if( ArrayUtils.contains(new String[]{"onetoc", "onetoc2", "onetmp", "onepkg"}, extension) ){
			return new MimeTypeCheck(extension, "application/onenote", DOCUMENT);
		}
		else if( ArrayUtils.contains(new String[]{"odt"}, extension) ){
			return new MimeTypeCheck(extension, "application/vnd.oasis.opendocument.text", DOCUMENT);
		}
		else if( ArrayUtils.contains(new String[]{"odp"}, extension) ){
			return new MimeTypeCheck(extension, "application/vnd.oasis.opendocument.presentation", DOCUMENT);
		}
		else if( ArrayUtils.contains(new String[]{"ods"}, extension) ){
			return new MimeTypeCheck(extension, "application/vnd.oasis.opendocument.spreadsheet", DOCUMENT);
		}
		else if( ArrayUtils.contains(new String[]{"odg"}, extension) ){
			return new MimeTypeCheck(extension, "application/vnd.oasis.opendocument.graphics", DOCUMENT);
		}
		else if( ArrayUtils.contains(new String[]{"odc"}, extension) ){
			return new MimeTypeCheck(extension, "application/vnd.oasis.opendocument.chart", DOCUMENT);
		}
		else if( ArrayUtils.contains(new String[]{"odb"}, extension) ){
			return new MimeTypeCheck(extension, "application/vnd.oasis.opendocument.database", DOCUMENT);
		}
		else if( ArrayUtils.contains(new String[]{"odf"}, extension) ){
			return new MimeTypeCheck(extension, "application/vnd.oasis.opendocument.formula", DOCUMENT);
		}
		else if( ArrayUtils.contains(new String[]{"wp", "wpd"}, extension) ){
			return new MimeTypeCheck(extension, "application/wordperfect", DOCUMENT);
		}
		else if( ArrayUtils.contains(new String[]{"hwp"}, extension) ){
			return new MimeTypeCheck(extension, "application/x-hwp", DOCUMENT);
		}
		else if( ArrayUtils.contains(new String[]{"srt"}, extension) ){
			return new MimeTypeCheck(extension, "text/srt", DOCUMENT);
		}
		
		//VIDEO
		else if( ArrayUtils.contains(new String[]{"swf"}, extension) ){
			return new MimeTypeCheck(extension, "application/x-shockwave-flash", VIDEO);
		}
		else if( ArrayUtils.contains(new String[]{"divx"}, extension) ){
			return new MimeTypeCheck(extension, "video/divx", VIDEO);
		}
		else if( ArrayUtils.contains(new String[]{"asf", "asx", "wax", "wmv", "wmx"}, extension) ){
			return new MimeTypeCheck(extension, "video/asf", VIDEO);
		}
		else if( ArrayUtils.contains(new String[]{"mpe", "mpeg", "mpg"}, extension) ){
			return new MimeTypeCheck(extension, "video/mpeg", VIDEO);
		}
		else if( ArrayUtils.contains(new String[]{"avi"}, extension) ){
			return new MimeTypeCheck(extension, "video/avi", VIDEO);
		}
		else if( ArrayUtils.contains(new String[]{"mp4", "m4v"}, extension) ){
			return new MimeTypeCheck(extension, "video/mp4", VIDEO);
		}
		else if( ArrayUtils.contains(new String[]{"mov", "qt"}, extension) ){
			return new MimeTypeCheck(extension, "video/quicktime", VIDEO);
		}
		else if( ArrayUtils.contains(new String[]{"flv"}, extension) ){
			return new MimeTypeCheck(extension, "video/x-flv", VIDEO);
		}
		else if( ArrayUtils.contains(new String[]{"movie"}, extension) ){
			return new MimeTypeCheck(extension, "video/x-sgi-movie", VIDEO);
		}
		else if( ArrayUtils.contains(new String[]{"ogv"}, extension) ){
			return new MimeTypeCheck(extension, "video/ogg", VIDEO);
		}
		else if( ArrayUtils.contains(new String[]{"mkv"}, extension) ){
			return new MimeTypeCheck(extension, "video/x-matroska", VIDEO);
		}
		
		//AUDIO
		else if( ArrayUtils.contains(new String[]{"mp3", "m4a", "m4b"}, extension) ){
			return new MimeTypeCheck(extension, "audio/mpeg", AUDIO);
		}
		else if( ArrayUtils.contains(new String[]{"ac3"}, extension) ){
			return new MimeTypeCheck(extension, "audio/ac3", AUDIO);
		}
		else if( ArrayUtils.contains(new String[]{"ogg", "oga"}, extension) ){
			return new MimeTypeCheck(extension, "audio/ogg", AUDIO);
		}
		else if( ArrayUtils.contains(new String[]{"au", "snd"}, extension) ){
			return new MimeTypeCheck(extension, "audio/basic", AUDIO);
		}
		else if( ArrayUtils.contains(new String[]{"mid", "midi"}, extension) ){
			return new MimeTypeCheck(extension, "audio/midi", AUDIO);
		}
		else if( ArrayUtils.contains(new String[]{"mpa"}, extension) ){
			return new MimeTypeCheck(extension, "audio/MPA", AUDIO);
		}
		else if( ArrayUtils.contains(new String[]{"aif", "aidc", "aiff"}, extension) ){
			return new MimeTypeCheck(extension, "audio/x-aiff", AUDIO);
		}
		else if( ArrayUtils.contains(new String[]{"ra", "ram"}, extension) ){
			return new MimeTypeCheck(extension, "audio/x-realaudio", AUDIO);
		}
		else if( ArrayUtils.contains(new String[]{"wav"}, extension) ){
			return new MimeTypeCheck(extension, "audio/wav", AUDIO);
		}
		else if( ArrayUtils.contains(new String[]{"wma"}, extension) ){
			return new MimeTypeCheck(extension, "audio/wma", AUDIO);
		}
		else if( ArrayUtils.contains(new String[]{"mka"}, extension) ){
			return new MimeTypeCheck(extension, "audio/x-matroska", AUDIO);
		}
		
		//ETC
		else if( ArrayUtils.contains(new String[]{"zip"}, extension) ){
			return new MimeTypeCheck(extension, "application/zip", ETC);
		}
		else if( ArrayUtils.contains(new String[]{"rar"}, extension) ){
			return new MimeTypeCheck(extension, "application/rar", ETC);
		}
		else if( ArrayUtils.contains(new String[]{"7z"}, extension) ){
			return new MimeTypeCheck(extension, "application/x-7z-compressed", ETC);
		}
		else if( ArrayUtils.contains(new String[]{"tar"}, extension) ){
			return new MimeTypeCheck(extension, "application/x-tar", ETC);
		}
		else if( ArrayUtils.contains(new String[]{"gtar"}, extension) ){
			return new MimeTypeCheck(extension, "application/x-gtar", ETC);
		}
		else if( ArrayUtils.contains(new String[]{"gz", "gzip"}, extension) ){
			return new MimeTypeCheck(extension, "application/x-gzip", ETC);
		}
		else if( ArrayUtils.contains(new String[]{"js"}, extension) ){
			return new MimeTypeCheck(extension, "application/javascript", ETC);
		}
		else if( ArrayUtils.contains(new String[]{"mdb"}, extension) ){
			return new MimeTypeCheck(extension, "application/vnd.ms-access", ETC);
		}
		else if( ArrayUtils.contains(new String[]{"zip"}, extension) ){
			return new MimeTypeCheck(extension, "multipart/x-zip", ETC);
		}
		else if( ArrayUtils.contains(new String[]{"ics", "ifb"}, extension) ){
			return new MimeTypeCheck(extension, "text/calendar", ETC);
		}
		else if( ArrayUtils.contains(new String[]{"css"}, extension) ){
			return new MimeTypeCheck(extension, "text/css", ETC);
		}
		else if( ArrayUtils.contains(new String[]{"class"}, extension) ){
			return new MimeTypeCheck(extension, "application/java", ETC);
		}
		else if( ArrayUtils.contains(new String[]{"exe"}, extension) ){
			return new MimeTypeCheck(extension, "application/x-msdownload", ETC);
		}
		//나도 몰라-_-
		else{
			return new MimeTypeCheck(extension, "unknown", ETC);
		}
	}
}
