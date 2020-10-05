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
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;
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

import egovframework.com.asapro.banner.service.Banner;
import egovframework.com.asapro.member.admin.service.AdminMember;
import egovframework.com.asapro.member.admin.service.AdminMemberSearch;
import egovframework.com.asapro.member.admin.service.AdminMemberService;
import egovframework.com.asapro.note.service.Note;
import egovframework.com.asapro.note.service.NoteSearch;
import egovframework.com.asapro.note.service.NoteService;
import egovframework.com.asapro.site.service.Site;
import egovframework.com.asapro.support.Constant;
import egovframework.com.asapro.support.ServerMessage;
import egovframework.com.asapro.support.annotation.CurrentAdmin;
import egovframework.com.asapro.support.annotation.CurrentSite;
import egovframework.com.asapro.support.exception.AsaproException;
import egovframework.com.asapro.support.pagination.Paging;
import egovframework.com.asapro.support.util.AsaproUtils;
import egovframework.com.asapro.support.util.EtcUtil;

/**
 * 쪽지 관리자 컨트롤러
 * @author yckim
 * @since 2020. 6. 8.
 *
 */
@Controller
public class NoteAdminController {
	
	@Autowired
	private NoteService noteService;
	
	@Autowired
	private NoteValidator noteValidator;
	
	@Autowired
	private HttpServletRequest request;
	
	@Autowired
	private AdminMemberService adminMemberService;
	

	/**
	 * 보낸/받은쪽지 목록을 반환한다.
	 * @param model
	 * @param currentSite
	 * @param noteSearch
	 * @return 보낸/받은쪽지 목록
	 */
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/note/{noteDiv}/list.do", method=RequestMethod.GET)
	public String noteListGet(Model model, @CurrentAdmin AdminMember currentAdmin, @CurrentSite Site currentSite, @ModelAttribute("noteSearch") NoteSearch noteSearch){
		noteSearch.setSitePrefix(currentSite.getSitePrefix());
		noteSearch.fixBrokenSvByDefaultCharsets();
		noteSearch.setPaging(true);
		//받은편지함
		if(StringUtils.isNotBlank(noteSearch.getNoteDiv()) && "reception".equals(noteSearch.getNoteDiv())){
			noteSearch.setNoteReceiverId(currentAdmin.getAdminId());
		}
		//보낸편지함
		if(StringUtils.isNotBlank(noteSearch.getNoteDiv()) && "transmit".equals(noteSearch.getNoteDiv())){
			noteSearch.setNoteTransmiterId(currentAdmin.getAdminId());
		}
		
		
		
		List<Note> list = noteService.getNoteList(noteSearch);
		int total = noteService.getNoteListTotal(noteSearch);
		Paging paging = new Paging(list, total, noteSearch);
		model.addAttribute("paging", paging);
		
		
		return "asapro/admin/note/list";
	}
	
	/**
	 * 쪽지쓰기 폼뷰를 반환한다.
	 * @param model
	 * @param currentSite
	 * @param noteSearch
	 * @return 폼뷰
	 */
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/note/insert.do", method=RequestMethod.GET)
	public String insertNoteGet(Model model, @CurrentSite Site currentSite, @ModelAttribute("noteForm") Note noteForm){
		AdminMemberSearch adminMemberSearch = new AdminMemberSearch();
		adminMemberSearch.setPaging(false);
		adminMemberSearch.setSortOrder("ADMIN_ORGANIZATION ASC, ADMIN_NAME");
		adminMemberSearch.setSortDirection("ASC");
		List<AdminMember> adminList = adminMemberService.getAdminMemberList(adminMemberSearch);
		model.addAttribute("adminList", adminList);
		
		model.addAttribute("formMode", "INSERT");
		return "asapro/admin/note/form";
	}
	
	/**
	 * 쪽지,보낸쪽지,받은쪽지를 추가한다.
	 * @param model
	 * @param currentAdmin
	 * @param redirectAttributes
	 * @param currentSite
	 * @param noteForm
	 * @param bindingResult
	 * @return 발송결과
	 */
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/note/insert.do", method=RequestMethod.POST)
	public String insertNotePost(Model model, @CurrentAdmin AdminMember currentAdmin, RedirectAttributes redirectAttributes, @CurrentSite Site currentSite, @ModelAttribute("noteForm") Note noteForm, BindingResult bindingResult) {
		noteValidator.validate(noteForm, bindingResult, "INSERT");
		if( bindingResult.hasErrors() ){
			AdminMemberSearch adminMemberSearch = new AdminMemberSearch();
			adminMemberSearch.setPaging(false);
			adminMemberSearch.setSortOrder("ADMIN_ORGANIZATION ASC, ADMIN_NAME");
			adminMemberSearch.setSortDirection("ASC");
			List<AdminMember> adminList = adminMemberService.getAdminMemberList(adminMemberSearch);
			model.addAttribute("adminList", adminList);
			
			model.addAttribute("formMode", "INSERT");
			return "asapro/admin/note/form";
		} else {
			noteForm.setNoteRegId(currentAdmin.getAdminId());
			noteForm.setNoteRegDate(new Date());
			noteForm.setNoteTransmiterId(currentAdmin.getAdminId());
			noteForm.setNoteSendDate(new Date());
			noteForm.setNoteOpen(false);
			
			noteService.insertNote(noteForm);
			redirectAttributes.addFlashAttribute("inserted", true);
			
			return "redirect:" + AsaproUtils.getAdminPath(currentSite, true) + "/note/transmit/list.do";
		}
	}
	
	/**
	 * 쪽지 상세보기 뷰를 반환한다.
	 * @param model
	 * @param currentAdmin
	 * @param currentSite
	 * @param noteForm
	 * @return 상세화면뷰
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/note/{noteDiv}/view.do", method=RequestMethod.GET)
	public String noteViewGet(Model model, @CurrentAdmin AdminMember currentAdmin, @CurrentSite Site currentSite, @ModelAttribute("noteForm") Note noteForm, @PathVariable String noteDiv) throws IllegalAccessException, InvocationTargetException{
		noteForm.setNoteReceiverId(currentAdmin.getAdminId());
		noteForm.setNoteTransmiterId(currentAdmin.getAdminId());
		
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
		
		return "asapro/admin/note/view";
	}
	
	/**
	 * 받은쪽지/보낸쪽지를 삭제처리한다.
	 * @param model
	 * @param currentAdmin
	 * @param currentSite
	 * @param noteIdsByType
	 * @param noteDiv
	 * @return 삭제처리 결과
	 * @throws FileNotFoundException
	 */
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/note/{noteDiv}/delete.do", method=RequestMethod.POST)
	@ResponseBody
	public ServerMessage deleteNotePost(Model model, @CurrentAdmin AdminMember currentAdmin, @CurrentSite Site currentSite, @RequestParam(value="noteIdsByType[]", required=true) String[] noteIdsByType, @PathVariable String noteDiv) throws FileNotFoundException{
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
				note.setNoteReceiverId(currentAdmin.getAdminId());
				note.setNoteTransmiterId(currentAdmin.getAdminId());
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
	 * @param currentAdmin
	 * @param currentSite
	 * @param noteForm
	 * @return 취소결과
	 */
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/note/cancel.do", method=RequestMethod.POST)
	@ResponseBody
	public ServerMessage cancelNotePost(Model model, @CurrentAdmin AdminMember currentAdmin, @CurrentSite Site currentSite, @ModelAttribute("noteForm") Note noteForm ){
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
	
	/**
	 * 미확인 쪽지 수량을 반환한다.
	 * @param model
	 * @param currentAdmin
	 * @param currentSite
	 * @return 미확인 쪽지 수량
	 */
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/note/nonOpenCnt.do", method=RequestMethod.GET)
	@ResponseBody
	public ServerMessage nonOpenNoteCntPost(Model model, @CurrentAdmin AdminMember currentAdmin, @CurrentSite Site currentSite ){
		ServerMessage serverMessage = new ServerMessage();
		NoteSearch noteSearch = new NoteSearch();
		noteSearch.setNoteReceiverId(currentAdmin.getAdminId());
		int cnt = noteService.getNonOpenNoteCnt(noteSearch);
		
		serverMessage.setSuccess(true);
		serverMessage.setSuccessCnt(cnt);

		return serverMessage;
	}
}
