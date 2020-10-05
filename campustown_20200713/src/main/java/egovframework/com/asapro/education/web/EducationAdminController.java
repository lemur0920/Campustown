/**
 * 
 */
package egovframework.com.asapro.education.web;

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
import egovframework.com.asapro.education.service.EducationService;
import egovframework.com.asapro.education.service.EducationReservation;
import egovframework.com.asapro.education.service.EducationReservationSearch;
import egovframework.com.asapro.education.service.EducationService;

/**
 * 교육프로그램정보 관리자 컨트롤러
 * @author yckim
 * @since 2018. 12. 17.
 *
 */
@Controller
public class EducationAdminController {
	
	@Autowired
	private EducationService educationService;
	
	@Autowired
	private EducationReservationValidator educationReservationValidator;
	
	@Autowired
	private CodeService codeService;
	
	@Autowired
	private HttpServletRequest request;
	
	
	//=============================================================================================================================
	//==========================================  신청정보   ================================================================
	//=============================================================================================================================
	
	/**
	 * 교육프로그램 신청정보를 목록을 조회한다.
	 * @param model
	 * @param currentSite
	 * @param educationSearch
	 * @return 목록
	 */
	@PrivacyHistory(moduleType="교육프로그램", history="신청정보 목록")
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/education/reservation/list.do", method=RequestMethod.GET)
	public String educationReservationListGet(Model model, @CurrentSite Site currentSite, @ModelAttribute("educationReservationSearch") EducationReservationSearch educationReservationSearch){
		educationReservationSearch.setSitePrefix(currentSite.getSitePrefix());
		educationReservationSearch.fixBrokenSvByDefaultCharsets();
		educationReservationSearch.setPaging(true);
		
		List<EducationReservation> list = educationService.getEducationReservationList(educationReservationSearch);
		int total = educationService.getEducationReservationListTotal(educationReservationSearch);
		
		String statusTemp = educationReservationSearch.getReservStatus();
		
		educationReservationSearch.setReservStatus("reception");
		int receptionCnt = educationService.getEducationReservationListTotal(educationReservationSearch);	//접수 수량
		educationReservationSearch.setReservStatus("admission");
		int admissionCnt = educationService.getEducationReservationListTotal(educationReservationSearch);	//승인 수량
		educationReservationSearch.setReservStatus("cancel");
		int cancelCnt = educationService.getEducationReservationListTotal(educationReservationSearch);	//취소 수량
		
		educationReservationSearch.setReservStatus(statusTemp);
		
		Paging paging = new Paging(list, total, educationReservationSearch);
		model.addAttribute("paging", paging);
		model.addAttribute("receptionCnt", receptionCnt);
		model.addAttribute("admissionCnt", admissionCnt);
		model.addAttribute("cancelCnt", cancelCnt);
		
		//신청상태 코드목록
		List<Code> statusCodeList = codeService.getCodeList("reservStatus");
		model.addAttribute("statusCodeList", statusCodeList);
		
		return "asapro/admin/education/reservList";
	}
	
	/**
	 * 교육프로그램 신청정보를 등록 폼 뷰를 반환한다.
	 * @param model
	 * @param currentSite
	 * @param educationReservationForm
	 * @return 추가폼뷰
	 */
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/education/reservation/insert.do", method=RequestMethod.GET)
	public String educationReservationInsertGet(Model model, @CurrentSite Site currentSite, @ModelAttribute("educationReservationForm") EducationReservation educationReservationForm) {
		
		//휴대폰번호 앞자리
		model.addAttribute("mobile1CodeList", codeService.getCodeList("mobile1_code"));
		
		model.addAttribute("formMode", "INSERT");
		return "asapro/admin/education/reservForm";
	}
	
	/**
	 * 교육프로그램 신청정보를 등록한다.
	 * @param model
	 * @param redirectAttributes
	 * @param currentSite
	 * @param educationReservationForm
	 * @param bindingResult
	 * @return 추가결과
	 * @throws AsaproException
	 * @throws IOException
	 */
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/education/reservation/insert.do", method=RequestMethod.POST)
	public String educationReservationInsertPost(Model model, RedirectAttributes redirectAttributes, @CurrentSite Site currentSite, @ModelAttribute("educationReservationForm") EducationReservation educationReservationForm, BindingResult bindingResult) throws AsaproException, IOException {

		educationReservationForm.setSitePrefix(currentSite.getSitePrefix());
		educationReservationValidator.validate(educationReservationForm, bindingResult, "INSERT");
		
		
		if( bindingResult.hasErrors() ){
			
			//휴대폰번호 앞자리
			model.addAttribute("mobile1CodeList", codeService.getCodeList("mobile1_code"));
			
			model.addAttribute("formMode", "INSERT");
			model.addAttribute("insertFail", true);
			return "asapro/admin/education/reservForm";
		} else {
			educationReservationForm.setReservRegdate(new Date());
			educationReservationForm.setReservStatus("reception");	//접수 (상태코드)
			educationReservationForm.setReservAgree(true);	//관리자쪽 등록은 개인정보사용 동의 처리
			educationReservationForm.setReservHp(educationReservationForm.getReservHp1() + "-" + educationReservationForm.getReservHp2() + "-" + educationReservationForm.getReservHp3());
			
			educationService.insertEducationReservation(educationReservationForm);
			redirectAttributes.addFlashAttribute("inserted", true);
			return "redirect:" + AsaproUtils.getAdminPath(currentSite, true) + "/education/reservation/list.do?reservId=" + educationReservationForm.getReservId();
		}
		
	}
	
	/**
	 * 교육프로그램 신청정보 수정 폼 뷰를 반환한다.
	 * @param model
	 * @param currentSite
	 * @param educationReservationForm
	 * @return 수정폼뷰
	 */
	@PrivacyHistory(moduleType="교육프로그램", history="신청정보 상세")
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/education/reservation/update.do", method=RequestMethod.GET)
	public String educationReservationUpdateGet(Model model, @CurrentSite Site currentSite, @ModelAttribute("educationReservationForm") EducationReservation educationReservationForm) {
		educationReservationForm.setSitePrefix(currentSite.getSitePrefix());
		EducationReservation educationReservationModel = educationService.getEducationReservation(educationReservationForm);
		
		//hp 1,2,3으로 분리 셋
		if(StringUtils.isNotBlank(educationReservationModel.getReservHp()) ){
			String[] tempHpArray = educationReservationModel.getReservHp().split("-");
			educationReservationModel.setReservHp1(tempHpArray[0]);
			educationReservationModel.setReservHp2(tempHpArray[1]);
			educationReservationModel.setReservHp3(tempHpArray[2]);
		}
		
		model.addAttribute("educationReservationForm", educationReservationModel);
		
		//휴대폰번호 앞자리
		model.addAttribute("mobile1CodeList", codeService.getCodeList("mobile1_code"));
		
		//신청상태 코드목록
		List<Code> statusCodeList = codeService.getCodeList("reservStatus");
		model.addAttribute("statusCodeList", statusCodeList);
		
		model.addAttribute("formMode", "UPDATE");
		return "asapro/admin/education/reservForm";
	}
	
	/**
	 * 교육프로그램 신청정보를 수정한다.
	 * @param model
	 * @param redirectAttributes
	 * @param currentSite
	 * @param educationReservationForm
	 * @param bindingResult
	 * @return 수정결과
	 * @throws AsaproException
	 * @throws IOException
	 */
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/education/reservation/update.do", method=RequestMethod.POST)
	public String educationReservationUpdatePost(Model model, RedirectAttributes redirectAttributes, @CurrentSite Site currentSite, @ModelAttribute("educationReservationForm") EducationReservation educationReservationForm, BindingResult bindingResult) throws AsaproException, IOException {

		educationReservationForm.setSitePrefix(currentSite.getSitePrefix());
		educationReservationValidator.validate(educationReservationForm, bindingResult, "UPDATE");
		
		
		if( bindingResult.hasErrors() ){
			
			//휴대폰번호 앞자리
			model.addAttribute("mobile1CodeList", codeService.getCodeList("mobile1_code"));
			
			//신청상태 코드목록
			List<Code> statusCodeList = codeService.getCodeList("reservStatus");
			model.addAttribute("statusCodeList", statusCodeList);
			
			model.addAttribute("formMode", "UPDATE");
			model.addAttribute("updateFail", true);
			return "asapro/admin/education/reservForm";
		} else {
			educationReservationForm.setReservHp(educationReservationForm.getReservHp1() + "-" + educationReservationForm.getReservHp2() + "-" + educationReservationForm.getReservHp3());
			
			educationService.updateEducationReservation(educationReservationForm);
			redirectAttributes.addFlashAttribute("updated", true);
			return "redirect:" + AsaproUtils.getAdminPath(currentSite, true) + "/education/reservation/update.do?reservId=" + educationReservationForm.getReservId();
		}
		
	}
	
	/**
	 * 교육프로그램 신청정보를 삭제한다.
	 * @param model
	 * @param currentSite
	 * @param reservIds
	 * @return 삭제결과
	 * @throws FileNotFoundException
	 */
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/education/reservation/delete.do", method=RequestMethod.POST)
	@ResponseBody
	public ServerMessage deleteEducationPost(Model model, @CurrentSite Site currentSite, @RequestParam(value="reservIds[]", required=true) Integer[] reservIds) throws FileNotFoundException{
		ServerMessage serverMessage = new ServerMessage();
		if( ArrayUtils.isEmpty(reservIds) ){
			serverMessage.setSuccess(false);
			serverMessage.setText("삭제할 항목이 없습니다.");
		} else {
			List<EducationReservation> educationReservationList = new ArrayList<EducationReservation>();
			EducationReservation educationReservation = null;
			for( Integer reservId : reservIds ){
				educationReservation = new EducationReservation();
				educationReservation.setSitePrefix(currentSite.getSitePrefix());
				educationReservation.setReservId(reservId);
				educationReservationList.add(educationReservation);
			}
			int result = educationService.deleteEducationReservation(educationReservationList);
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
