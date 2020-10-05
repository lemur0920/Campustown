/**
 * 
 */
package egovframework.com.asapro.education.web;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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

import egovframework.com.asapro.code.service.Code;
import egovframework.com.asapro.code.service.CodeService;
import egovframework.com.asapro.fileinfo.service.FileInfo;
import egovframework.com.asapro.fileinfo.service.FileInfoUploadResult;
import egovframework.com.asapro.education.service.Education;
import egovframework.com.asapro.education.service.EducationReservation;
import egovframework.com.asapro.education.service.EducationReservationSearch;
import egovframework.com.asapro.education.service.EducationSearch;
import egovframework.com.asapro.education.service.EducationService;
import egovframework.com.asapro.site.service.Site;
import egovframework.com.asapro.support.Constant;
import egovframework.com.asapro.support.SEO;
import egovframework.com.asapro.support.ServerMessage;
import egovframework.com.asapro.support.annotation.CurrentSite;
import egovframework.com.asapro.support.exception.AsaproException;
import egovframework.com.asapro.support.exception.AsaproNotFoundException;
import egovframework.com.asapro.support.pagination.Paging;
import egovframework.com.asapro.support.util.AsaproCalendarUtils;
import egovframework.com.asapro.support.util.AsaproUtils;
import egovframework.com.asapro.support.util.EtcUtil;

/**
 * 교육프로그램 사용자 컨트롤러
 * @author yckim
 * @since 2018. 12. 18.
 *
 */
@Controller
public class EducationUserController {
	
	@Autowired
	private EducationService educationService;
	
	@Autowired
	private EducationReservationValidator educationReservationValidator;
	
	@Autowired
	private HttpServletRequest request;
	
	@Autowired
	private CodeService codeService;
	
	/**
	 * 개인정보 수집및 이용 동의 폼뷰를 반환한다.
	 * @param model
	 * @param currentSite
	 * @param educationReservationForm
	 * @return 개인정보 수집및 이용 동의 폼뷰
	 */
	@RequestMapping(value={Constant.APP_PATH + Constant.SITE_ID_PATH + "/education/agree"}, method=RequestMethod.GET)
	public String educationAgreeGet(Model model, @CurrentSite Site currentSite, @ModelAttribute("educationReservationForm") EducationReservation educationReservationForm) {
		
		return AsaproUtils.getThemePath(request) + "education/agree";
	}

	/**
	 * 신청정보를 등록 폼 뷰를 반환한다.
	 * @param model
	 * @param currentSite
	 * @param educationReservationForm
	 * @param bindingResult
	 * @return 선청폼 뷰
	 * @throws ParseException
	 */
	@RequestMapping(value={Constant.APP_PATH + Constant.SITE_ID_PATH + "/education/reservation/write"}, method=RequestMethod.POST)
	public String educationReservationWritePost(Model model, @CurrentSite Site currentSite, @ModelAttribute("educationReservationForm") EducationReservation educationReservationForm, BindingResult bindingResult) throws ParseException {
		//휴대폰번호 앞자리
		model.addAttribute("mobile1CodeList", codeService.getCodeList("mobile1_code"));

		educationReservationForm.setSitePrefix(currentSite.getSitePrefix());
		educationReservationValidator.validate(educationReservationForm, bindingResult, "AGREE");
		
		if( bindingResult.hasErrors() ){
			return AsaproUtils.getThemePath(request) + "education/agree";
		}else{
			
			model.addAttribute("formMode", "INSERT");
			return AsaproUtils.getThemePath(request) + "education/reservForm";
		}
		
		
	}
	
	/**
	 * 신청정보를 추가한다.
	 * @param model
	 * @param currentSite
	 * @param reservRentId
	 * @param rentalReservationForm
	 * @param bindingResult
	 * @return 추가결과
	 * @throws IOException 
	 * @throws AsaproException 
	 * @throws ParseException 
	 */
	@RequestMapping(value={Constant.APP_PATH + Constant.SITE_ID_PATH + "/education/reservation/insert"}, method=RequestMethod.POST)
	public String educationReservationInsertPost(Model model, RedirectAttributes redirectAttributes, @CurrentSite Site currentSite, @ModelAttribute("educationReservationForm") EducationReservation educationReservationForm, BindingResult bindingResult) throws AsaproException, IOException, ParseException {
		
		//휴대폰번호 앞자리
		model.addAttribute("mobile1CodeList", codeService.getCodeList("mobile1_code"));
		
		educationReservationForm.setSitePrefix(currentSite.getSitePrefix());
		educationReservationValidator.validate(educationReservationForm, bindingResult, "INSERT");
		
		if( bindingResult.hasErrors() ){
			
			model.addAttribute("insertFail", true);
			model.addAttribute("formMode", "INSERT");
			return AsaproUtils.getThemePath(request) + "education/reservForm";
		}else{
			
			educationReservationForm.setReservRegdate(new Date());
			educationReservationForm.setReservStatus("reception");	//접수 (상태코드)
			educationReservationForm.setReservAgree(true);	//관리자쪽 등록은 개인정보사용 동의 처리
			educationReservationForm.setReservHp(educationReservationForm.getReservHp1() + "-" + educationReservationForm.getReservHp2() + "-" + educationReservationForm.getReservHp3());
			
			educationService.insertEducationReservation(educationReservationForm);
			redirectAttributes.addFlashAttribute("inserted", true);
			return "redirect:" + AsaproUtils.getAppPath(currentSite, true) + "/education/agree";
		}
	}
	
	
}
