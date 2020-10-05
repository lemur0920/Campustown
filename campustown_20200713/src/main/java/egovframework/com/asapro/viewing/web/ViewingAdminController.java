/**
 * 
 */
package egovframework.com.asapro.viewing.web;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import egovframework.com.asapro.code.service.Code;
import egovframework.com.asapro.code.service.CodeService;
import egovframework.com.asapro.fileinfo.service.FileInfo;
import egovframework.com.asapro.fileinfo.service.FileInfoUploadResult;
import egovframework.com.asapro.site.service.Site;
import egovframework.com.asapro.support.Constant;
import egovframework.com.asapro.support.ServerMessage;
import egovframework.com.asapro.support.annotation.CurrentSite;
import egovframework.com.asapro.support.annotation.PrivacyHistory;
import egovframework.com.asapro.support.exception.AsaproException;
import egovframework.com.asapro.support.pagination.Paging;
import egovframework.com.asapro.support.util.AsaproUtils;
import egovframework.com.asapro.viewing.service.ViewingService;
import egovframework.com.asapro.viewing.service.ViewingReservation;
import egovframework.com.asapro.viewing.service.ViewingReservationSearch;
import egovframework.com.asapro.viewing.service.ViewingService;

/**
 * 관람정보 관리자 컨트롤러
 * @author yckim
 * @since 2018. 12. 14.
 *
 */
@Controller
public class ViewingAdminController {
	
	@Autowired
	private ViewingService viewingService;
	
	@Autowired
	private ViewingReservationValidator viewingReservationValidator;
	
	@Autowired
	private CodeService codeService;
	
	@Autowired
	private HttpServletRequest request;
	
	
	//=============================================================================================================================
	//==========================================  신청정보   ================================================================
	//=============================================================================================================================
	
	/**
	 * 관람 신청정보를 목록을 조회한다.
	 * @param model
	 * @param currentSite
	 * @param viewingSearch
	 * @return 목록
	 */
	@PrivacyHistory(moduleType="관람", history="신청정보 목록")
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/viewing/reservation/list.do", method=RequestMethod.GET)
	public String viewingReservationListGet(Model model, @CurrentSite Site currentSite, @ModelAttribute("viewingReservationSearch") ViewingReservationSearch viewingReservationSearch){
		viewingReservationSearch.setSitePrefix(currentSite.getSitePrefix());
		viewingReservationSearch.fixBrokenSvByDefaultCharsets();
		viewingReservationSearch.setPaging(true);
		
		List<ViewingReservation> list = viewingService.getViewingReservationList(viewingReservationSearch);
		int total = viewingService.getViewingReservationListTotal(viewingReservationSearch);
		
		String statusTemp = viewingReservationSearch.getReservStatus();
		
		viewingReservationSearch.setReservStatus("reception");
		int receptionCnt = viewingService.getViewingReservationListTotal(viewingReservationSearch);	//접수 수량
		viewingReservationSearch.setReservStatus("admission");
		int admissionCnt = viewingService.getViewingReservationListTotal(viewingReservationSearch);	//승인 수량
		viewingReservationSearch.setReservStatus("cancel");
		int cancelCnt = viewingService.getViewingReservationListTotal(viewingReservationSearch);	//취소 수량
		
		viewingReservationSearch.setReservStatus(statusTemp);
		
		Paging paging = new Paging(list, total, viewingReservationSearch);
		model.addAttribute("paging", paging);
		model.addAttribute("receptionCnt", receptionCnt);
		model.addAttribute("admissionCnt", admissionCnt);
		model.addAttribute("cancelCnt", cancelCnt);
		
		//신청상태 코드목록
		List<Code> statusCodeList = codeService.getCodeList("reservStatus");
		model.addAttribute("statusCodeList", statusCodeList);
		
		return "asapro/admin/viewing/reservList";
	}
	
	/**
	 * 관람 신청정보를 등록 폼 뷰를 반환한다.
	 * @param model
	 * @param currentSite
	 * @param viewingReservationForm
	 * @return 추가폼뷰
	 */
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/viewing/reservation/insert.do", method=RequestMethod.GET)
	public String viewingReservationInsertGet(Model model, @CurrentSite Site currentSite, @ModelAttribute("viewingReservationForm") ViewingReservation viewingReservationForm) {
		
		//휴대폰번호 앞자리
		model.addAttribute("mobile1CodeList", codeService.getCodeList("mobile1_code"));
		
		model.addAttribute("formMode", "INSERT");
		return "asapro/admin/viewing/reservForm";
	}
	
	/**
	 * 관람 신청정보를 등록한다.
	 * @param model
	 * @param redirectAttributes
	 * @param currentSite
	 * @param viewingReservationForm
	 * @param bindingResult
	 * @return 추가결과
	 * @throws AsaproException
	 * @throws IOException
	 */
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/viewing/reservation/insert.do", method=RequestMethod.POST)
	public String viewingReservationInsertPost(Model model, RedirectAttributes redirectAttributes, @CurrentSite Site currentSite, @ModelAttribute("viewingReservationForm") ViewingReservation viewingReservationForm, BindingResult bindingResult) throws AsaproException, IOException {

		viewingReservationForm.setSitePrefix(currentSite.getSitePrefix());
		viewingReservationValidator.validate(viewingReservationForm, bindingResult, "INSERT");
		
		if( bindingResult.hasErrors() ){
			
			//휴대폰번호 앞자리
			model.addAttribute("mobile1CodeList", codeService.getCodeList("mobile1_code"));
			
			model.addAttribute("formMode", "INSERT");
			model.addAttribute("insertFail", true);
			return "asapro/admin/viewing/reservForm";
		} else {
			viewingReservationForm.setReservRegdate(new Date());
			viewingReservationForm.setReservStatus("reception");	//접수 (상태코드)
			viewingReservationForm.setReservAgree(true);	//관리자쪽 등록은 개인정보사용 동의 처리
			viewingReservationForm.setReservHp(viewingReservationForm.getReservHp1() + "-" + viewingReservationForm.getReservHp2() + "-" + viewingReservationForm.getReservHp3());
			
			viewingService.insertViewingReservation(viewingReservationForm);
			redirectAttributes.addFlashAttribute("inserted", true);
			return "redirect:" + AsaproUtils.getAdminPath(currentSite, true) + "/viewing/reservation/list.do?reservId=" + viewingReservationForm.getReservId();
		}
		
	}
	
	/**
	 * 관람 신청정보 수정 폼 뷰를 반환한다.
	 * @param model
	 * @param currentSite
	 * @param viewingReservationForm
	 * @return 수정폼뷰
	 */
	@PrivacyHistory(moduleType="관람", history="신청정보 상세")
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/viewing/reservation/update.do", method=RequestMethod.GET)
	public String viewingReservationUpdateGet(Model model, @CurrentSite Site currentSite, @ModelAttribute("viewingReservationForm") ViewingReservation viewingReservationForm) {
		viewingReservationForm.setSitePrefix(currentSite.getSitePrefix());
		ViewingReservation viewingReservationModel = viewingService.getViewingReservation(viewingReservationForm);
		
		//hp 1,2,3으로 분리 셋
		if(StringUtils.isNotBlank(viewingReservationModel.getReservHp()) ){
			String[] tempHpArray = viewingReservationModel.getReservHp().split("-");
			viewingReservationModel.setReservHp1(tempHpArray[0]);
			viewingReservationModel.setReservHp2(tempHpArray[1]);
			viewingReservationModel.setReservHp3(tempHpArray[2]);
		}
		
		model.addAttribute("viewingReservationForm", viewingReservationModel);
		
		//휴대폰번호 앞자리
		model.addAttribute("mobile1CodeList", codeService.getCodeList("mobile1_code"));
		
		//신청상태 코드목록
		List<Code> statusCodeList = codeService.getCodeList("reservStatus");
		model.addAttribute("statusCodeList", statusCodeList);
		
		model.addAttribute("formMode", "UPDATE");
		return "asapro/admin/viewing/reservForm";
	}
	
	/**
	 * 관람 신청정보를 수정한다.
	 * @param model
	 * @param redirectAttributes
	 * @param currentSite
	 * @param viewingReservationForm
	 * @param bindingResult
	 * @return 수정결과
	 * @throws AsaproException
	 * @throws IOException
	 */
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/viewing/reservation/update.do", method=RequestMethod.POST)
	public String viewingReservationUpdatePost(Model model, RedirectAttributes redirectAttributes, @CurrentSite Site currentSite, @ModelAttribute("viewingReservationForm") ViewingReservation viewingReservationForm, BindingResult bindingResult) throws AsaproException, IOException {

		viewingReservationForm.setSitePrefix(currentSite.getSitePrefix());
		viewingReservationValidator.validate(viewingReservationForm, bindingResult, "UPDATE");
		
		
		if( bindingResult.hasErrors() ){
			
			//휴대폰번호 앞자리
			model.addAttribute("mobile1CodeList", codeService.getCodeList("mobile1_code"));
			
			//신청상태 코드목록
			List<Code> statusCodeList = codeService.getCodeList("reservStatus");
			model.addAttribute("statusCodeList", statusCodeList);
			
			model.addAttribute("formMode", "UPDATE");
			model.addAttribute("updateFail", true);
			return "asapro/admin/viewing/reservForm";
		} else {
			viewingReservationForm.setReservHp(viewingReservationForm.getReservHp1() + "-" + viewingReservationForm.getReservHp2() + "-" + viewingReservationForm.getReservHp3());
			
			viewingService.updateViewingReservation(viewingReservationForm);
			redirectAttributes.addFlashAttribute("updated", true);
			return "redirect:" + AsaproUtils.getAdminPath(currentSite, true) + "/viewing/reservation/update.do?reservId=" + viewingReservationForm.getReservId();
		}
		
	}
	
	/**
	 * 관람 신청정보를 삭제한다.
	 * @param model
	 * @param currentSite
	 * @param reservIds
	 * @return 삭제결과
	 * @throws FileNotFoundException
	 */
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/viewing/reservation/delete.do", method=RequestMethod.POST)
	@ResponseBody
	public ServerMessage deleteViewingPost(Model model, @CurrentSite Site currentSite, @RequestParam(value="reservIds[]", required=true) Integer[] reservIds) throws FileNotFoundException{
		ServerMessage serverMessage = new ServerMessage();
		if( ArrayUtils.isEmpty(reservIds) ){
			serverMessage.setSuccess(false);
			serverMessage.setText("삭제할 항목이 없습니다.");
		} else {
			List<ViewingReservation> viewingReservationList = new ArrayList<ViewingReservation>();
			ViewingReservation viewingReservation = null;
			for( Integer reservId : reservIds ){
				viewingReservation = new ViewingReservation();
				viewingReservation.setSitePrefix(currentSite.getSitePrefix());
				viewingReservation.setReservId(reservId);
				viewingReservationList.add(viewingReservation);
			}
			int result = viewingService.deleteViewingReservation(viewingReservationList);
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
	
}
