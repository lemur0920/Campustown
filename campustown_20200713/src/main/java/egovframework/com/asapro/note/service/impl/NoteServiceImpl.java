/**
 * 
 */
package egovframework.com.asapro.note.service.impl;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import egovframework.com.asapro.note.service.Note;
import egovframework.com.asapro.note.service.NoteMapper;
import egovframework.com.asapro.note.service.NoteSearch;
import egovframework.com.asapro.note.service.NoteService;
import egovframework.com.asapro.support.util.AsaproUtils;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

/**
 * 쪽지 서비스 구현
 * @author yckim
 * @since 2020. 6. 8.
 *
 */
@Service
public class NoteServiceImpl extends EgovAbstractServiceImpl implements NoteService {

	@Autowired
	private NoteMapper noteMapper;
	
	@Autowired
	private HttpServletRequest request;

	/**
	 * 보낸쪽지 목록을 반환한다.
	 */
	@Override
	public List<Note> getNoteList(NoteSearch noteSearch) {
		List<Note> list = null;
		
		//받은쪽지 목록
		if("reception".equals(noteSearch.getNoteDiv()) ){
			list = noteMapper.selectReceptionNoteList(noteSearch);
		} 
		//보낸쪽지 목록
		else if("transmit".equals(noteSearch.getNoteDiv()) ){
			list = noteMapper.selectTransmitNoteList(noteSearch);
		} else {
			
		}
		return list;
	}

	/**
	 * 보낸쪽지 목록 토탈 수량을 반환한다.
	 */
	@Override
	public int getNoteListTotal(NoteSearch noteSearch) {
		int total = 0;
		
		//받은쪽지 총수량
		if("reception".equals(noteSearch.getNoteDiv()) ){
			total = noteMapper.selectReceptionNoteListTotal(noteSearch);
		} 
		//보낸쪽지 총수량
		else if("transmit".equals(noteSearch.getNoteDiv()) ){
			total = noteMapper.selectTransmitNoteListTotal(noteSearch);
		} else {
			
		}
		return total;
	}

	/**
	 * 쪽지,보낸쪽지,받은쪽지를 추가한다.
	 */
	@Override
	public int insertNote(Note noteForm) {
		int result = 0;
		int result1 = 0;

		if(StringUtils.isNotBlank(noteForm.getNoteReceiverId()) ){
			String tempReceiver = noteForm.getNoteReceiverId().replaceAll(";", ",");

			/**
			 * 쪽지등록
			 */
			String noteId = AsaproUtils.getRandomUUID();
			noteForm.setNoteId(noteId);
			result1 = noteMapper.insertNote(noteForm);
			
			for (String receiver : tempReceiver.split(",") ) {
				int result2 = 0;
				int result3 = 0;
				
				if(StringUtils.isNotBlank(receiver) ){
					
					noteForm.setNoteReceiverId(receiver);
					
					/**
					 * 보낸쪽지함 등록
					 */
					if(result1 > 0){
						String noteTransmitId = AsaproUtils.getRandomUUID();
						noteForm.setNoteTransmitId(noteTransmitId);
						result2 = noteMapper.insertTransmitNote(noteForm);
					}
					
					
					/**
					 * 받은쪽지함 등록
					 */
					if(result2 > 0){
						String noteReceptionId = AsaproUtils.getRandomUUID();
						noteForm.setNoteReceptionId(noteReceptionId);
						result3 = noteMapper.insertReceptionNote(noteForm);
					}
					
					
				}
				result += result3;
			}
		}
		
		return result;
	}

	/**
	 * 받은쪽지 정보를 반환한다.
	 */
	@Override
	public Note getReceptionNote(Note noteForm) {
		return noteMapper.selectReceptionNote(noteForm);
	}

	/**
	 * 보낸쪽지 정보를 반환한다.
	 */
	@Override
	public Note getTransmitNote(Note noteForm) {
		return noteMapper.selectTransmitNote(noteForm);
	}

	/**
	 * 수신상태와 수신일시를 변경한다.
	 */
	@Override
	public int changeNoteOpen(Note noteForm) {
		return noteMapper.updateNoteOpen(noteForm);
	}
	
	/**
	 * 쪽지발송 취소처리를 한다.
	 */
	@Override
	public int cancelNote(Note noteForm) {
		return noteMapper.updateNoteCancel(noteForm);
	}
	
	/**
	 * 보낸쪽지를 삭제처리 한다.
	 */
	@Override
	public int deleteTransmitNote(List<Note> noteListByType) {
		int result = 0;
		for(Note note : noteListByType){
			result += deleteTransmitNote(note);
		}
		return result;
	}

	/**
	 * 보낸쪽지를 삭제처리 한다.
	 */
	@Override
	public int deleteTransmitNote(Note note) {
		return noteMapper.deleteTransmitNote(note);
	}
	
	/**
	 * 받은쪽지를 삭제처리한다.
	 */
	@Override
	public int deleteReceptionNote(List<Note> noteListByType) {
		int result = 0;
		for(Note note : noteListByType){
			result += deleteReceptionNote(note);
		}
		return result;
	}

	/**
	 * 받은쪽지를 삭제처리한다.
	 */
	@Override
	public int deleteReceptionNote(Note noteForm) {
		return noteMapper.deleteReceptionNote(noteForm);
	}

	/**
	 * 미확인 쪽지 수량을 반환한다.
	 */
	@Override
	public int getNonOpenNoteCnt(NoteSearch noteSearch) {
		return noteMapper.selectNonOpenNoteCnt(noteSearch);
	}

	

}
