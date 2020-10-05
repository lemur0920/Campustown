/**
 * 
 */
package egovframework.com.asapro.viewing.web;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
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
import egovframework.com.asapro.viewing.service.Viewing;
import egovframework.com.asapro.viewing.service.ViewingCalendar;
import egovframework.com.asapro.viewing.service.ViewingReservation;
import egovframework.com.asapro.viewing.service.ViewingReservationSearch;
import egovframework.com.asapro.viewing.service.ViewingSearch;
import egovframework.com.asapro.viewing.service.ViewingService;
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
 * 관람정보 사용자 컨트롤러
 * @author yckim
 * @since 2018. 12. 17.
 *
 */
@Controller
public class ViewingUserController {
	
	@Autowired
	private ViewingService viewingService;
	
	@Autowired
	private ViewingReservationValidator viewingReservationValidator;
	
	@Autowired
	private HttpServletRequest request;
	
	@Autowired
	private CodeService codeService;
	
	/**
	 * 개인정보 수집및 이용 동의 폼뷰를 반환한다.
	 * @param model
	 * @param currentSite
	 * @param viewingReservationForm
	 * @return 개인정보 수집및 이용 동의 폼뷰
	 */
	@RequestMapping(value={Constant.APP_PATH + Constant.SITE_ID_PATH + "/viewing/agree"}, method=RequestMethod.GET)
	public String viewingAgreeGet(Model model, @CurrentSite Site currentSite, @ModelAttribute("viewingReservationForm") ViewingReservation viewingReservationForm) {
		
		return AsaproUtils.getThemePath(request) + "viewing/agree";
	}

	/**
	 * 신청정보를 등록 폼 뷰를 반환한다.
	 * @param model
	 * @param currentSite
	 * @param viewingReservationForm
	 * @param bindingResult
	 * @return 선청폼 뷰
	 * @throws ParseException
	 */
	@RequestMapping(value={Constant.APP_PATH + Constant.SITE_ID_PATH + "/viewing/reservation/write"}, method=RequestMethod.POST)
	public String viewingReservationWritePost(Model model, @CurrentSite Site currentSite, @ModelAttribute("viewingReservationForm") ViewingReservation viewingReservationForm, BindingResult bindingResult) throws ParseException {
		//휴대폰번호 앞자리
		model.addAttribute("mobile1CodeList", codeService.getCodeList("mobile1_code"));

		viewingReservationForm.setSitePrefix(currentSite.getSitePrefix());
		viewingReservationValidator.validate(viewingReservationForm, bindingResult, "AGREE");
		
		if( bindingResult.hasErrors() ){
			return AsaproUtils.getThemePath(request) + "viewing/agree";
		}else{
			
			model.addAttribute("formMode", "INSERT");
			return AsaproUtils.getThemePath(request) + "viewing/reservForm";
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
	@RequestMapping(value={Constant.APP_PATH + Constant.SITE_ID_PATH + "/viewing/reservation/insert"}, method=RequestMethod.POST)
	public String viewingReservationInsertPost(Model model, RedirectAttributes redirectAttributes, @CurrentSite Site currentSite, @ModelAttribute("viewingReservationForm") ViewingReservation viewingReservationForm, BindingResult bindingResult) throws AsaproException, IOException, ParseException {
		
		//휴대폰번호 앞자리
		model.addAttribute("mobile1CodeList", codeService.getCodeList("mobile1_code"));
		
		viewingReservationForm.setSitePrefix(currentSite.getSitePrefix());
		viewingReservationValidator.validate(viewingReservationForm, bindingResult, "INSERT");
		
		if( bindingResult.hasErrors() ){
			
			model.addAttribute("insertFail", true);
			model.addAttribute("formMode", "INSERT");
			return AsaproUtils.getThemePath(request) + "viewing/reservForm";
		}else{
			
			viewingReservationForm.setReservRegdate(new Date());
			viewingReservationForm.setReservStatus("reception");	//접수 (상태코드)
			viewingReservationForm.setReservAgree(true);	//관리자쪽 등록은 개인정보사용 동의 처리
			viewingReservationForm.setReservHp(viewingReservationForm.getReservHp1() + "-" + viewingReservationForm.getReservHp2() + "-" + viewingReservationForm.getReservHp3());
			
			viewingService.insertViewingReservation(viewingReservationForm);
			redirectAttributes.addFlashAttribute("inserted", true);
			return "redirect:" + AsaproUtils.getAppPath(currentSite, true) + "/viewing/agree";
		}
	}
	
	
	/**
	 * 관람 신청정보를 주간달력을 생성해 반환한다.
	 * @param model
	 * @param currentSite
	 * @param viewingSearch
	 * @return 목록
	 * @throws ParseException 
	 */
	@RequestMapping(value=Constant.APP_PATH + Constant.SITE_ID_PATH + "/viewing/reservation" + Constant.API_PATH + "/calendarWeek", method=RequestMethod.GET)
	@ResponseBody
	public ViewingCalendar viewingReservationCalendarWeekListGet(Model model, @CurrentSite Site currentSite, @ModelAttribute("viewingReservationSearch") ViewingReservationSearch viewingReservationSearch) throws ParseException{
		viewingReservationSearch.setSitePrefix(currentSite.getSitePrefix());
		viewingReservationSearch.fixBrokenSvByDefaultCharsets();
		viewingReservationSearch.setPaging(false);
		viewingReservationSearch.setReservStatus("admission");//승인
		
		//현제의 년과 주차가 없으면 오늘을 기준으로 초기값 셋트
		if(StringUtils.isBlank(viewingReservationSearch.getReservYear()) ){
			viewingReservationSearch.setReservYear(String.format("%04d", Calendar.getInstance().get(Calendar.YEAR)) );
		}
		if(viewingReservationSearch.getReservWeekOfYear() == null || viewingReservationSearch.getReservWeekOfYear() <= 0 ){
			viewingReservationSearch.setReservWeekOfYear(Calendar.getInstance().get(Calendar.WEEK_OF_YEAR) );
		}
		
		ViewingCalendar viewingCalendar = new ViewingCalendar(viewingService, viewingReservationSearch);
		//model.addAttribute("viewingCalender", viewingCalendar);
		
		return viewingCalendar;
	}
	
	/**
	 * 관람 신청정보를 해당일의 목록을 반환한다.
	 * @param model
	 * @param currentSite
	 * @param viewingSearch
	 * @return 해당일의 신청정보 
	 * @throws ParseException 
	 */
	@RequestMapping(value=Constant.APP_PATH + Constant.SITE_ID_PATH + "/viewing/reservation" + Constant.API_PATH + "/calendarDay", method=RequestMethod.GET)
	@ResponseBody
	public Map<String, String> viewingReservationCalendarDayListGet(Model model, @CurrentSite Site currentSite, @ModelAttribute("viewingReservationSearch") ViewingReservationSearch viewingReservationSearch) throws ParseException{
		viewingReservationSearch.setSitePrefix(currentSite.getSitePrefix());
		viewingReservationSearch.fixBrokenSvByDefaultCharsets();
		viewingReservationSearch.setPaging(false);
		viewingReservationSearch.setReservStatus("admission");//승인
		
		List<ViewingReservation> viewingReservationList = viewingService.getViewingReservationList(viewingReservationSearch);
		
		Map<String, String> result = new HashMap<String, String>();
		if(viewingReservationList != null && !viewingReservationList.isEmpty() ){
			String title = "단체관람 " + viewingReservationList.size() + "건";
			title += " (";
			int total = 0;
			String content = "";
			for (ViewingReservation viewingReservation : viewingReservationList) {
				content += viewingReservation.getReservGroup();
				content += "(" + viewingReservation.getReservPeople() + "명)";
				content += viewingReservation.getReservStime() + "~" + viewingReservation.getReservEtime();
				//content += System.getProperty("line.separator");
				content += "<br>";
				
				total += viewingReservation.getReservPeople();
			}
			
			title += total + "명)";
			result.put("title", title);
			result.put("reservDate", viewingReservationSearch.getReservDate());
			result.put("content", content);
		}
		
		return result;
	}
	
}
