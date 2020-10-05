/**
 * 
 */
package egovframework.com.asapro.note.service;

import org.apache.commons.lang3.StringUtils;

import egovframework.com.asapro.support.pagination.PagingSearch;

/**
 * 쪽지검색VO
 * @author yckim
 * @since 2020. 6. 8.
 *
 */
public class NoteSearch extends PagingSearch{

	private Boolean noteOpen;	//수신확인여부
	private String noteDiv;	//쪽지구분
	private String noteTransmiterId;	//발신자 아이디
	private String noteReceiverId;	//수신자 아이디
	
	//발신,수신일 검색을 위해 구간 선택 옵션
	private String startDate;	//검색시작일
	private String endDate;	//검색종료일
	
	/**
	 * @return the noteOpen
	 */
	public Boolean getNoteOpen() {
		return noteOpen;
	}

	/**
	 * @param noteOpen the noteOpen to set
	 */
	public void setNoteOpen(Boolean noteOpen) {
		this.noteOpen = noteOpen;
	}

	/**
	 * @return the noteDiv
	 */
	public String getNoteDiv() {
		return noteDiv;
	}

	/**
	 * @param noteDiv the noteDiv to set
	 */
	public void setNoteDiv(String noteDiv) {
		this.noteDiv = noteDiv;
	}

	/**
	 * @return the noteTransmiterId
	 */
	public String getNoteTransmiterId() {
		return noteTransmiterId;
	}

	/**
	 * @param noteTransmiterId the noteTransmiterId to set
	 */
	public void setNoteTransmiterId(String noteTransmiterId) {
		this.noteTransmiterId = noteTransmiterId;
	}

	/**
	 * @return the noteReceiverId
	 */
	public String getNoteReceiverId() {
		return noteReceiverId;
	}

	/**
	 * @param noteReceiverId the noteReceiverId to set
	 */
	public void setNoteReceiverId(String noteReceiverId) {
		this.noteReceiverId = noteReceiverId;
	}

	/**
	 * @return the startDate
	 */
	public String getStartDate() {
		return startDate;
	}

	/**
	 * @param startDate the startDate to set
	 */
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	/**
	 * @return the endDate
	 */
	public String getEndDate() {
		return endDate;
	}

	/**
	 * @param endDate the endDate to set
	 */
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	/* (non-Javadoc)
	 * @see egovframework.com.asapro.support.pagination.PagingSearch#getQueryString()
	 */
	@Override
	public String getQueryString() {
		
		StringBuilder sb = new StringBuilder(100);
		sb.append(super.getQueryString());
		
		if( this.getNoteOpen() != null ){
			sb.append("&amp;noteOpen=");
			sb.append(this.getNoteOpen());
		}
		
		if( StringUtils.isNotBlank(this.getNoteDiv()) ){
			sb.append("&amp;noteDiv=");
			sb.append(this.getNoteDiv());
		}
		if( StringUtils.isNotBlank(this.getStartDate()) ){
			sb.append("&amp;startDate=");
			sb.append(this.getStartDate());
		}
		if( StringUtils.isNotBlank(this.getEndDate()) ){
			sb.append("&amp;endDate=");
			sb.append(this.getEndDate());
		}
		 
		return sb.toString();
	}
	
}
