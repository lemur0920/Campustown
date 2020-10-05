/**
 * 
 */
package egovframework.com.asapro.note.service;

import java.util.List;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * 쪽지 SQL 매퍼
 * @author yckim
 * @since 2020. 6. 8.
 *
 */
@Mapper
public interface NoteMapper {

	/**
	 * 쪽지정보를 등록한다.
	 * @param noteForm
	 * @return 등록결과
	 */
	public int insertNote(Note noteForm);

	/**
	 * 보낸쪽지함을 등록한다.
	 * @param noteForm
	 * @return 등록결과
	 */
	public int insertTransmitNote(Note noteForm);

	/**
	 * 받은쪽지함을 등록한다.
	 * @param noteForm
	 * @return 등록결과
	 */
	public int insertReceptionNote(Note noteForm);
	
	/**
	 * 보낸쪽지목록을 반환한다.
	 * @param noteSearch
	 * @return 보낸쪽지목록
	 */
	public List<Note> selectTransmitNoteList(NoteSearch noteSearch);
	
	/**
	 * 보낸쪽지목록 총수량을 반환한다.
	 * @param noteSearch
	 * @return 보낸쪽지 총수량
	 */
	public int selectTransmitNoteListTotal(NoteSearch noteSearch);
	
	/**
	 * 받은쪽지목록을 반환한다.
	 * @param noteSearch
	 * @return 받은쪽지목록
	 */
	public List<Note> selectReceptionNoteList(NoteSearch noteSearch);

	/**
	 * 받은쪽지목록 총수량을 반환한다.
	 * @param noteSearch
	 * @return 받은쪽지 총수량
	 */
	public int selectReceptionNoteListTotal(NoteSearch noteSearch);

	/**
	 * 받은쪽지 정보를 반환한다.
	 * @param noteForm
	 * @return 받은쪽지
	 */
	public Note selectReceptionNote(Note noteForm);

	/**
	 * 보낸쪽지 정보를 반환한다.
	 * @param noteForm
	 * @return
	 */
	public Note selectTransmitNote(Note noteForm);

	/**
	 * 수신상태와 수신일시를 변경한다.
	 * @param noteForm
	 * @return 변경결과
	 */
	public int updateNoteOpen(Note noteForm);

	/**
	 * 발송취소처리를 한다.
	 * @param noteForm
	 * @return 취소결과
	 */
	public int updateNoteCancel(Note noteForm);

	/**
	 * 보낸쪽지를 삭제처리 한다.
	 * @param noteForm
	 * @return 삭제처리결과
	 */
	public int deleteTransmitNote(Note noteForm);

	/**
	 * 받은쪽지를 삭제처리한다.
	 * @param noteForm
	 * @return 삭제처리결과
	 */
	public int deleteReceptionNote(Note noteForm);

	/**
	 * 미확인 쪽지 수량을 반환한다.
	 * @param noteSearch
	 * @return 미확인 쪽지 수
	 */
	public int selectNonOpenNoteCnt(NoteSearch noteSearch);

}
