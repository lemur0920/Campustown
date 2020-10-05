/**
 * 
 */
package egovframework.com.asapro.note.web;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.FlashMap;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.ibm.icu.text.SimpleDateFormat;

import egovframework.com.asapro.member.admin.service.AdminMember;
import egovframework.com.asapro.member.admin.service.AdminMemberSearch;
import egovframework.com.asapro.member.admin.service.AdminMemberService;
import egovframework.com.asapro.member.user.service.UserMember;
import egovframework.com.asapro.note.service.Note;
import egovframework.com.asapro.note.service.NoteSearch;
import egovframework.com.asapro.note.service.NoteService;
import egovframework.com.asapro.site.service.Site;
import egovframework.com.asapro.support.Constant;
import egovframework.com.asapro.support.ServerMessage;
import egovframework.com.asapro.support.annotation.CurrentAdmin;
import egovframework.com.asapro.support.annotation.CurrentSite;
import egovframework.com.asapro.support.annotation.CurrentUser;
import egovframework.com.asapro.support.exception.AsaproException;
import egovframework.com.asapro.support.pagination.Paging;
import egovframework.com.asapro.support.util.AsaproUtils;

/**
 * 쪽지 관리자 컨트롤러
 * @author yckim
 * @since 2020. 6. 8.
 *
 */
@Controller
public class NoteUserController {
	
	@Autowired
	private NoteService noteService;
	
	@Autowired
	private HttpServletRequest request;
	
	@Autowired
	private AdminMemberService adminMemberService;
	
	@Autowired
	private NoteValidator noteValidator;
	

	/**
	 * 보낸/받은쪽지 목록을 반환한다.
	 * @param model
	 * @param currentSite
	 * @param currentUser
	 * @param noteSearch
	 * @return 보낸/받은쪽지 목록
	 */
	@RequestMapping(value=Constant.APP_PATH + Constant.SITE_ID_PATH + "/mypage/note/{noteDiv}/list", method=RequestMethod.GET)
	public String noteMypageListGet(Model model, @CurrentSite Site currentSite, @CurrentUser UserMember currentUser, @ModelAttribute("noteSearch") NoteSearch noteSearch){
		noteSearch.setSitePrefix(currentSite.getSitePrefix());
		noteSearch.fixBrokenSvByDefaultCharsets();
		noteSearch.setPaging(true);
		//받은편지함
		if(StringUtils.isNotBlank(noteSearch.getNoteDiv()) && "reception".equals(noteSearch.getNoteDiv())){
			noteSearch.setNoteReceiverId(currentUser.getUserId());
		}
		//보낸편지함
		if(StringUtils.isNotBlank(noteSearch.getNoteDiv()) && "transmit".equals(noteSearch.getNoteDiv())){
			noteSearch.setNoteTransmiterId(currentUser.getUserId());
		}
		
		List<Note> list = noteService.getNoteList(noteSearch);
		int total = noteService.getNoteListTotal(noteSearch);
		Paging paging = new Paging(list, total, noteSearch);
		model.addAttribute("paging", paging);
		
		return AsaproUtils.getThemePath(request) + "note/list";
	}
	
	/**
	 * 쪽지 상세보기 뷰를 반환한다.
	 * @param model
	 * @param currentSite
	 * @param currentUser
	 * @param noteForm
	 * @param noteDiv
	 * @return 상세화면뷰
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	@RequestMapping(value=Constant.APP_PATH + Constant.SITE_ID_PATH + "/mypage/note/{noteDiv}/view", method=RequestMethod.GET)
	public String noteMypageViewListGet(Model model, @CurrentSite Site currentSite, @CurrentUser UserMember currentUser, @ModelAttribute("noteForm") Note noteForm, @PathVariable String noteDiv) throws IllegalAccessException, InvocationTargetException{
		noteForm.setNoteReceiverId(currentUser.getUserId());
		noteForm.setNoteTransmiterId(currentUser.getUserId());
		
		Note noteModel = null;
		
		//받은쪽지
		if("reception".equals(noteDiv) ){
			//수신여부 처리
			noteForm.setNoteOpen(true);
			noteForm.setNoteReceiveDate(new Date());
			noteService.changeNoteOpen(noteForm);
			
			noteModel = noteService.getReceptionNote(noteForm);
		} 
		//보낸쪽지
		else if("transmit".equals(noteDiv) ){
			noteModel = noteService.getTransmitNote(noteForm);
		} 
		
		model.addAttribute("noteDiv", noteDiv);
		model.addAttribute("noteModel", noteModel);
		
		//목록 돌아가기 검색VO
		NoteSearch backListSearch = new NoteSearch();
		BeanUtils.populate(backListSearch, request.getParameterMap());
		backListSearch.fixBrokenSvByDefaultCharsets();
		model.addAttribute("backListSearch", backListSearch);
		
		return AsaproUtils.getThemePath(request) + "note/view";
	}
	
	
	/**
	 * 쪽지쓰기 폼뷰를 반환한다.
	 * @param model
	 * @param currentSite
	 * @param noteForm
	 * @return 폼뷰
	 */
	@RequestMapping(value=Constant.APP_PATH + Constant.SITE_ID_PATH + "/mypage/note/insert", method=RequestMethod.GET)
	public String insertNoteGet(Model model, @CurrentSite Site currentSite, @ModelAttribute("noteForm") Note noteForm){
		
		AdminMember receiver = adminMemberService.getAdminMember(noteForm.getNoteReceiverId());
		noteForm.setReceiver(receiver);
		
		model.addAttribute("formMode", "INSERT");
		return AsaproUtils.getThemePath(request) + "note/form";
	}
	
	/**
	 * 쪽지,보낸쪽지,받은쪽지를 추가한다.
	 * @param model
	 * @param currentUser
	 * @param redirectAttributes
	 * @param currentSite
	 * @param noteForm
	 * @param bindingResult
	 * @return 발송결과
	 */
	@RequestMapping(value=Constant.APP_PATH + Constant.SITE_ID_PATH + "/mypage/note/insert", method=RequestMethod.POST)
	@ResponseBody
	public ServerMessage insertNotePost(Model model, @CurrentUser UserMember currentUser, RedirectAttributes redirectAttributes, @CurrentSite Site currentSite, @ModelAttribute("noteForm") Note noteForm, BindingResult bindingResult) {
		ServerMessage serverMessage = new ServerMessage();
		
		noteValidator.validate(noteForm, bindingResult, "INSERT");
		if( bindingResult.hasErrors() ){
			serverMessage.setSuccess(false);
			serverMessage.setText("수신자 정보나 내용을 확인해 주세요");
		} else {
			noteForm.setNoteRegId(currentUser.getUserId());
			noteForm.setNoteRegDate(new Date());
			noteForm.setNoteTransmiterId(currentUser.getUserId());
			noteForm.setNoteSendDate(new Date());
			noteForm.setNoteOpen(false);
			
			int result = noteService.insertNote(noteForm);
			
			if(result > 0){
				serverMessage.setSuccess(true);
				serverMessage.setSuccessCnt(result);
			} else {
				serverMessage.setSuccess(false);
				serverMessage.setText("쪽지 발송 실패.[Server Error]");
			}
		}
		return serverMessage;
	}
	
	/**
	 * 받은쪽지/보낸쪽지를 삭제처리한다.
	 * @param model
	 * @param currentUser
	 * @param currentSite
	 * @param noteIdsByType
	 * @param noteDiv
	 * @return 삭제처리 결과
	 * @throws FileNotFoundException
	 */
	@RequestMapping(value=Constant.APP_PATH + Constant.SITE_ID_PATH + "/mypage/note/{noteDiv}/delete", method=RequestMethod.POST)
	@ResponseBody
	public ServerMessage deleteNotePost(Model model, @CurrentUser UserMember currentUser, @CurrentSite Site currentSite, @RequestParam(value="noteIdsByType[]", required=true) String[] noteIdsByType, @PathVariable String noteDiv) throws FileNotFoundException{
		ServerMessage serverMessage = new ServerMessage();
		if( ArrayUtils.isEmpty(noteIdsByType) ){
			serverMessage.setSuccess(false);
			serverMessage.setText("삭제할 항목이 없습니다.");
		} else {
			List<Note> noteListByType = new ArrayList<Note>();
			Note note = null;
			for( String noteIdByType : noteIdsByType ){
				note = new Note();
				note.setSitePrefix(currentSite.getSitePrefix());
				note.setNoteReceptionId(noteIdByType);
				note.setNoteTransmitId(noteIdByType);
				note.setNoteReceiverId(currentUser.getUserId());
				note.setNoteTransmiterId(currentUser.getUserId());
				noteListByType.add(note);
			}
			
			int result = 0;
			//받은쪽지 삭제
			if("reception".equals(noteDiv) ){
				result = noteService.deleteReceptionNote(noteListByType);
			} 
			//보낸쪽지 삭제
			else if("transmit".equals(noteDiv) ){
				result = noteService.deleteTransmitNote(noteListByType);
			} 
			
			if(result > 0){
				serverMessage.setSuccess(true);
				serverMessage.setSuccessCnt(result);
			} else {
				serverMessage.setSuccess(false);
				serverMessage.setText("삭제하지 못하였습니다.[Server Error]");
			}
		}
		return serverMessage;
	}
	
	/**
	 * 쪽지발송 취소처리를 한다.
	 * @param model
	 * @param currentUser
	 * @param currentSite
	 * @param noteForm
	 * @return 취소결과
	 */
	@RequestMapping(value=Constant.APP_PATH + Constant.SITE_ID_PATH + "/mypage/note/cancel", method=RequestMethod.POST)
	@ResponseBody
	public ServerMessage cancelNotePost(Model model, @CurrentUser UserMember currentUser, @CurrentSite Site currentSite, @ModelAttribute("noteForm") Note noteForm ){
		ServerMessage serverMessage = new ServerMessage();
		if( StringUtils.isBlank(noteForm.getNoteReceptionId()) ){
			serverMessage.setSuccess(false);
			serverMessage.setText("취소할 항목이 없습니다.");
		} else {
			
			int result = noteService.cancelNote(noteForm);
			
			if(result > 0){
				serverMessage.setSuccess(true);
				serverMessage.setSuccessCnt(result);
			} else {
				serverMessage.setSuccess(false);
				serverMessage.setText("취소하지 못하였습니다.[Server Error]");
			}
		}
		return serverMessage;
	}
	
}
