/**
 * 
 */
package egovframework.com.asapro.volunteer.web;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import egovframework.com.asapro.volunteer.service.VolunteerReservation;
import egovframework.com.asapro.volunteer.service.VolunteerReservationSearch;
import egovframework.com.asapro.volunteer.service.VolunteerService;

/**
 * 자원봉사관리 관리자 컨트롤러
 * @author yckim
 * @since 2018. 12. 12.
 *
 */
@Controller
public class VolunteerAdminController {
	
	@Autowired
	private VolunteerService volunteerService;
	
	@Autowired
	private VolunteerReservationValidator volunteerReservationValidator;
	
	@Autowired
	private CodeService codeService;
	
	@Autowired
	private HttpServletRequest request;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(VolunteerReservationValidator.class);
	
	//=============================================================================================================================
	//==========================================  신청정보   ================================================================
	//=============================================================================================================================
	
	/**
	 * 자원봉사 신청정보를 목록을 조회한다.
	 * @param model
	 * @param currentSite
	 * @param volunteerReservationSearch
	 * @return 목록
	 */
	@PrivacyHistory(moduleType="자원봉사", history="신청정보 목록")
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/volunteer/reservation/list.do", method=RequestMethod.GET)
	public String volunteerReservationListGet(Model model, @CurrentSite Site currentSite, @ModelAttribute("volunteerReservationSearch") VolunteerReservationSearch volunteerReservationSearch){
		volunteerReservationSearch.setSitePrefix(currentSite.getSitePrefix());
		volunteerReservationSearch.fixBrokenSvByDefaultCharsets();
		volunteerReservationSearch.setPaging(true);
		
		List<VolunteerReservation> list = volunteerService.getVolunteerReservationList(volunteerReservationSearch);
		int total = volunteerService.getVolunteerReservationListTotal(volunteerReservationSearch);
		
		String statusTemp = volunteerReservationSearch.getReservStatus();
		
		volunteerReservationSearch.setReservStatus("reception");
		int receptionCnt = volunteerService.getVolunteerReservationListTotal(volunteerReservationSearch);	//접수 수량
		volunteerReservationSearch.setReservStatus("admission");
		int admissionCnt = volunteerService.getVolunteerReservationListTotal(volunteerReservationSearch);	//승인 수량
		volunteerReservationSearch.setReservStatus("cancel");
		int cancelCnt = volunteerService.getVolunteerReservationListTotal(volunteerReservationSearch);	//취소 수량
		
		volunteerReservationSearch.setReservStatus(statusTemp);
		
		Paging paging = new Paging(list, total, volunteerReservationSearch);
		model.addAttribute("paging", paging);
		model.addAttribute("receptionCnt", receptionCnt);
		model.addAttribute("admissionCnt", admissionCnt);
		model.addAttribute("cancelCnt", cancelCnt);
		
		//신청상태 코드목록
		List<Code> statusCodeList = codeService.getCodeList("reservStatus");
		model.addAttribute("statusCodeList", statusCodeList);
		
		return "asapro/admin/volunteer/reservList";
	}
	
	/**
	 * 자원봉사 신청정보를 등록 폼 뷰를 반환한다.
	 * @param model
	 * @param currentSite
	 * @param volunteerReservationForm
	 * @return 추가폼뷰
	 */
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/volunteer/reservation/insert.do", method=RequestMethod.GET)
	public String volunteerReservationInsertGet(Model model, @CurrentSite Site currentSite, @ModelAttribute("volunteerReservationForm") VolunteerReservation volunteerReservationForm) {
		
		//휴대폰번호 앞자리
		model.addAttribute("mobile1CodeList", codeService.getCodeList("mobile1_code"));
		
		model.addAttribute("formMode", "INSERT");
		return "asapro/admin/volunteer/reservForm";
	}
	
	/**
	 * 자원봉사 신청정보를 등록한다.
	 * @param model
	 * @param redirectAttributes
	 * @param currentSite
	 * @param volunteerReservationForm
	 * @param bindingResult
	 * @return 추가결과
	 * @throws AsaproException
	 * @throws IOException
	 */
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/volunteer/reservation/insert.do", method=RequestMethod.POST)
	public String volunteerReservationInsertPost(Model model, RedirectAttributes redirectAttributes, @CurrentSite Site currentSite, @ModelAttribute("volunteerReservationForm") VolunteerReservation volunteerReservationForm, BindingResult bindingResult) throws AsaproException, IOException {

		volunteerReservationForm.setSitePrefix(currentSite.getSitePrefix());
		volunteerReservationValidator.validate(volunteerReservationForm, bindingResult, "INSERT");
		
		
		if( bindingResult.hasErrors() ){
			
			//휴대폰번호 앞자리
			model.addAttribute("mobile1CodeList", codeService.getCodeList("mobile1_code"));
			
			model.addAttribute("formMode", "INSERT");
			model.addAttribute("insertFail", true);
			return "asapro/admin/volunteer/reservForm";
		} else {
			volunteerReservationForm.setReservRegdate(new Date());
			volunteerReservationForm.setReservStatus("reception");	//접수 (상태코드)
			volunteerReservationForm.setReservAgree(true);	//관리자쪽 등록은 개인정보사용 동의 처리
			volunteerReservationForm.setReservHp(volunteerReservationForm.getReservHp1() + "-" + volunteerReservationForm.getReservHp2() + "-" + volunteerReservationForm.getReservHp3());
			
			volunteerService.insertVolunteerReservation(volunteerReservationForm);
			redirectAttributes.addFlashAttribute("inserted", true);
			return "redirect:" + AsaproUtils.getAdminPath(currentSite, true) + "/volunteer/reservation/list.do?reservId=" + volunteerReservationForm.getReservId();
		}
		
	}
	
	/**
	 * 자원봉사 신청정보 수정 폼 뷰를 반환한다.
	 * @param model
	 * @param currentSite
	 * @param volunteerReservationForm
	 * @return 수정폼뷰
	 */
	@PrivacyHistory(moduleType="자원봉사", history="신청정보 상세")
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/volunteer/reservation/update.do", method=RequestMethod.GET)
	public String volunteerReservationUpdateGet(Model model, @CurrentSite Site currentSite, @ModelAttribute("volunteerReservationForm") VolunteerReservation volunteerReservationForm) {
		volunteerReservationForm.setSitePrefix(currentSite.getSitePrefix());
		VolunteerReservation volunteerReservationModel = volunteerService.getVolunteerReservation(volunteerReservationForm);
		
		//hp 1,2,3으로 분리 셋
		if(StringUtils.isNotBlank(volunteerReservationModel.getReservHp()) ){
			String[] tempHpArray = volunteerReservationModel.getReservHp().split("-");
			volunteerReservationModel.setReservHp1(tempHpArray[0]);
			volunteerReservationModel.setReservHp2(tempHpArray[1]);
			volunteerReservationModel.setReservHp3(tempHpArray[2]);
		}
		
		model.addAttribute("volunteerReservationForm", volunteerReservationModel);
		
		//휴대폰번호 앞자리
		model.addAttribute("mobile1CodeList", codeService.getCodeList("mobile1_code"));
		
		//신청상태 코드목록
		List<Code> statusCodeList = codeService.getCodeList("reservStatus");
		model.addAttribute("statusCodeList", statusCodeList);
		
		model.addAttribute("formMode", "UPDATE");
		return "asapro/admin/volunteer/reservForm";
	}
	
	/**
	 * 자원봉사 신청정보를 수정한다.
	 * @param model
	 * @param redirectAttributes
	 * @param currentSite
	 * @param volunteerReservationForm
	 * @param bindingResult
	 * @return 수정결과
	 * @throws AsaproException
	 * @throws IOException
	 */
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/volunteer/reservation/update.do", method=RequestMethod.POST)
	public String volunteerReservationUpdatePost(Model model, RedirectAttributes redirectAttributes, @CurrentSite Site currentSite, @ModelAttribute("volunteerReservationForm") VolunteerReservation volunteerReservationForm, BindingResult bindingResult) throws AsaproException, IOException {

		volunteerReservationForm.setSitePrefix(currentSite.getSitePrefix());
		volunteerReservationValidator.validate(volunteerReservationForm, bindingResult, "UPDATE");
		
		
		if( bindingResult.hasErrors() ){
			
			//휴대폰번호 앞자리
			model.addAttribute("mobile1CodeList", codeService.getCodeList("mobile1_code"));
			
			//신청상태 코드목록
			List<Code> statusCodeList = codeService.getCodeList("reservStatus");
			model.addAttribute("statusCodeList", statusCodeList);
			
			model.addAttribute("formMode", "UPDATE");
			model.addAttribute("updateFail", true);
			return "asapro/admin/volunteer/reservForm";
		} else {
			volunteerReservationForm.setReservHp(volunteerReservationForm.getReservHp1() + "-" + volunteerReservationForm.getReservHp2() + "-" + volunteerReservationForm.getReservHp3());
			
			volunteerService.updateVolunteerReservation(volunteerReservationForm);
			redirectAttributes.addFlashAttribute("updated", true);
			return "redirect:" + AsaproUtils.getAdminPath(currentSite, true) + "/volunteer/reservation/update.do?reservId=" + volunteerReservationForm.getReservId();
		}
		
	}
	
	/**
	 * 자원봉사 신청정보를 삭제한다.
	 * @param model
	 * @param currentSite
	 * @param reservIds
	 * @return 삭제결과
	 * @throws FileNotFoundException
	 */
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/volunteer/reservation/delete.do", method=RequestMethod.POST)
	@ResponseBody
	public ServerMessage deleteVolunteerPost(Model model, @CurrentSite Site currentSite, @RequestParam(value="reservIds[]", required=true) Integer[] reservIds) throws FileNotFoundException{
		ServerMessage serverMessage = new ServerMessage();
		if( ArrayUtils.isEmpty(reservIds) ){
			serverMessage.setSuccess(false);
			serverMessage.setText("삭제할 항목이 없습니다.");
		} else {
			List<VolunteerReservation> volunteerReservationList = new ArrayList<VolunteerReservation>();
			VolunteerReservation volunteerReservation = null;
			for( Integer reservId : reservIds ){
				volunteerReservation = new VolunteerReservation();
				volunteerReservation.setSitePrefix(currentSite.getSitePrefix());
				volunteerReservation.setReservId(reservId);
				volunteerReservationList.add(volunteerReservation);
			}
			int result = volunteerService.deleteVolunteerReservation(volunteerReservationList);
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
	 * 자원봉사 신청서 미리보기뷰를 반환한다.
	 * @param model
	 * @param currentSite
	 * @param volunteerReservationForm
	 * @return 신청서 미리보기폼뷰
	 * @throws ParseException 
	 */
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/volunteer/reservation/printPreview.do", method=RequestMethod.GET)
	public String volunteerReservationPrintPreviewGet(Model model, @CurrentSite Site currentSite, @ModelAttribute("volunteerReservationForm") VolunteerReservation volunteerReservationForm) throws ParseException {
		volunteerReservationForm.setSitePrefix(currentSite.getSitePrefix());
		VolunteerReservation volunteerReservationModel = volunteerService.getVolunteerReservation(volunteerReservationForm);
		
		model.addAttribute("volunteerReservationForm", volunteerReservationModel);
		
		String reservDayOfWeek;//봉사요일
		
		if(StringUtils.isNotBlank(volunteerReservationModel.getReservDate()) ){
			String[] reservDate = volunteerReservationModel.getReservDate().split("-");
			model.addAttribute("reservDate", reservDate);
		}
		reservDayOfWeek = AsaproUtils.getDayOfWeekKorText(volunteerReservationModel.getReservDate());
		
		String regDateTemp = AsaproUtils.getFormattedDate(volunteerReservationModel.getReservRegdate());
		if(StringUtils.isNotBlank(regDateTemp) ){
			String[] regDate = regDateTemp.split("-");
			model.addAttribute("regDate", regDate);
		}
		
		if(StringUtils.isNotBlank(volunteerReservationModel.getReservStime()) && StringUtils.isNotBlank(volunteerReservationModel.getReservEtime()) ){
			String[] sTime = volunteerReservationModel.getReservStime().split(":");
			String[] eTime = volunteerReservationModel.getReservEtime().split(":");
			model.addAttribute("sTime", sTime);
			model.addAttribute("eTime", eTime);
		}
		
		
		
		SimpleDateFormat format = new SimpleDateFormat("HH:mm");  

        Date d1 = null;
        Date d2 = null;
        try {
            d1 = format.parse(volunteerReservationModel.getReservStime());
            d2 = format.parse(volunteerReservationModel.getReservEtime());
        } catch (ParseException e) {
        	LOGGER.error("[ParseException] Try/Catch... : "+ e.getMessage());
        }    
		double termTime = TimeUnit.MILLISECONDS.toMinutes(d2.getTime() - d1.getTime()) / 60.0;
		
		
		
		model.addAttribute("reservDayOfWeek", reservDayOfWeek);
		model.addAttribute("termTime", termTime);
		
		return "asapro/admin/volunteer/printPreview";
	}
	
}
