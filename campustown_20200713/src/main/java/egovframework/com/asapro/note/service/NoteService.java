/**
 * 
 */
package egovframework.com.asapro.note.service;

import java.util.List;


/**
 *	쪽지 서비스
 * @author yckim
 * @since 2020. 6. 8.
 *
 */
public interface NoteService {

	/**
	 * 보낸쪽지 목록을 반환한다.
	 * @param noteSearch
	 * @return 보낸쪽지 목록
	 */
	public List<Note> getNoteList(NoteSearch noteSearch);

	/**
	 * 보낸쪽지 목록 토탈 수량을 반환한다.
	 * @param noteSearch
	 * @return 보낸쪽지 토탈 수량
	 */
	public int getNoteListTotal(NoteSearch noteSearch);

	/**
	 * 쪽지,보낸쪽지,받은쪽지를 추가한다.
	 * @param noteForm
	 * @return 추가결과
	 */
	public int insertNote(Note noteForm);

	/**
	 * 받은쪽지 정보를 반환한다.
	 * @param noteForm
	 * @return 받은쪽지
	 */
	public Note getReceptionNote(Note noteForm);

	/**
	 * 보낸쪽지 정보를 반환한다.
	 * @param noteForm
	 * @return 보낸쪽지
	 */
	public Note getTransmitNote(Note noteForm);

	/**
	 * 수신상태와 수신일시를 변경한다.
	 * @param noteForm
	 * @return 변경결과
	 */
	public int changeNoteOpen(Note noteForm);
	
	/**
	 * 쪽지발송 취소처리를 한다.
	 * @param noteForm
	 * @return 취소결과
	 */
	public int cancelNote(Note noteForm);
	
	/**
	 * 보낸쪽지를 삭제처리 한다.
	 * @param noteListByType
	 * @return 삭제처리 결과
	 */
	public int deleteTransmitNote(List<Note> noteListByType);
	
	/**
	 * 보낸쪽지를 삭제처리 한다.
	 * @param noteForm
	 * @return 삭제처리 결과
	 */
	public int deleteTransmitNote(Note noteForm);
	
	/**
	 * 받은쪽지를 삭제처리한다.
	 * @param noteListByType
	 * @return 삭제처리 결과
	 */
	public int deleteReceptionNote(List<Note> noteListByType);
	
	/**
	 * 받은쪽지를 삭제처리한다.
	 * @param noteForm
	 * @return 삭제처리 결과
	 */
	public int deleteReceptionNote(Note noteForm);
	
	/**
	 * 미확인 쪽지 수량을 반환한다.
	 * @param noteSearch
	 * @return 미확인 쪽지 수
	 */
	public int getNonOpenNoteCnt(NoteSearch noteSearch);
	
	
}
