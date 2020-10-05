/**
 * 
 */
package egovframework.com.asapro.note.service;

import java.util.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import egovframework.com.asapro.member.admin.service.AdminMember;
import egovframework.com.asapro.site.service.MultiSiteVO;

/**
 * 쪽지VO
 * @author yckim
 * @since 2020. 6. 8.
 *
 */
public class Note extends MultiSiteVO{

	//쪽지정보
	private String noteId;	//쪽지 아이디
	private String noteTitle;	//쪽지 제목
	private String noteContent;	//쪽지 내용
	private String noteRegId;	//작성자 아이디
	private Date noteRegDate;	//작성일시
	
	//보낸쪽지정보
	private String noteTransmitId;	//보낸쪽지 아이디
	private String noteTransmiterId;	//발신자 아이디
	private Date noteSendDate;	//쪽지 발신일시
	
	//받은쪽지정보
	private String noteReceptionId;	//받은쪽지 아이디
	private String noteReceiverId;	//수신자 아이디
	private Date noteReceiveDate;	//수신일시
	private boolean noteOpen = false;	//수신확인여부
	private String noteCancelYn = "";	//발송취소여부
	
	private AdminMember receiver;	//수신자
	private AdminMember transmiter;	//발신자
	
	/**
	 * @return the noteId
	 */
	public String getNoteId() {
		return noteId;
	}

	/**
	 * @param noteId the noteId to set
	 */
	public void setNoteId(String noteId) {
		this.noteId = noteId;
	}

	/**
	 * @return the noteTitle
	 */
	public String getNoteTitle() {
		return noteTitle;
	}

	/**
	 * @param noteTitle the noteTitle to set
	 */
	public void setNoteTitle(String noteTitle) {
		this.noteTitle = noteTitle;
	}

	/**
	 * @return the noteContent
	 */
	public String getNoteContent() {
		return noteContent;
	}

	/**
	 * @param noteContent the noteContent to set
	 */
	public void setNoteContent(String noteContent) {
		this.noteContent = noteContent;
	}

	/**
	 * @return the noteRegId
	 */
	public String getNoteRegId() {
		return noteRegId;
	}

	/**
	 * @param noteRegId the noteRegId to set
	 */
	public void setNoteRegId(String noteRegId) {
		this.noteRegId = noteRegId;
	}

	/**
	 * @return the noteRegDate
	 */
	public Date getNoteRegDate() {
		return noteRegDate;
	}

	/**
	 * @param noteRegDate the noteRegDate to set
	 */
	public void setNoteRegDate(Date noteRegDate) {
		this.noteRegDate = noteRegDate;
	}

	/**
	 * @return the noteTransmitId
	 */
	public String getNoteTransmitId() {
		return noteTransmitId;
	}

	/**
	 * @param noteTransmitId the noteTransmitId to set
	 */
	public void setNoteTransmitId(String noteTransmitId) {
		this.noteTransmitId = noteTransmitId;
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
	 * @return the noteSendDate
	 */
	public Date getNoteSendDate() {
		return noteSendDate;
	}

	/**
	 * @param noteSendDate the noteSendDate to set
	 */
	public void setNoteSendDate(Date noteSendDate) {
		this.noteSendDate = noteSendDate;
	}

	/**
	 * @return the noteReceptionId
	 */
	public String getNoteReceptionId() {
		return noteReceptionId;
	}

	/**
	 * @param noteReceptionId the noteReceptionId to set
	 */
	public void setNoteReceptionId(String noteReceptionId) {
		this.noteReceptionId = noteReceptionId;
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
	 * @return the noteReceiveDate
	 */
	public Date getNoteReceiveDate() {
		return noteReceiveDate;
	}

	/**
	 * @param noteReceiveDate the noteReceiveDate to set
	 */
	public void setNoteReceiveDate(Date noteReceiveDate) {
		this.noteReceiveDate = noteReceiveDate;
	}

	/**
	 * @return the noteOpen
	 */
	public boolean isNoteOpen() {
		return noteOpen;
	}

	/**
	 * @param noteOpen the noteOpen to set
	 */
	public void setNoteOpen(boolean noteOpen) {
		this.noteOpen = noteOpen;
	}

	/**
	 * @return the receiver
	 */
	public AdminMember getReceiver() {
		return receiver;
	}

	/**
	 * @param receiver the receiver to set
	 */
	public void setReceiver(AdminMember receiver) {
		this.receiver = receiver;
	}

	/**
	 * @return the transmiter
	 */
	public AdminMember getTransmiter() {
		return transmiter;
	}

	/**
	 * @param transmiter the transmiter to set
	 */
	public void setTransmiter(AdminMember transmiter) {
		this.transmiter = transmiter;
	}

	/**
	 * @return the noteCancelYn
	 */
	public String getNoteCancelYn() {
		return noteCancelYn;
	}

	/**
	 * @param noteCancelYn the noteCancelYn to set
	 */
	public void setNoteCancelYn(String noteCancelYn) {
		this.noteCancelYn = noteCancelYn;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	/**
     * toString 메소드를 대치한다.
     */
	@Override
    public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
