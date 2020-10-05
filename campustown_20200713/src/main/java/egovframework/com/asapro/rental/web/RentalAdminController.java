/**
 * 
 */
package egovframework.com.asapro.rental.web;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
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
import egovframework.com.asapro.fileinfo.service.FileInfoService;
import egovframework.com.asapro.fileinfo.service.FileInfoUploadResult;
import egovframework.com.asapro.member.admin.service.AdminMember;
import egovframework.com.asapro.organization.service.Department;
import egovframework.com.asapro.organization.service.DepartmentSearch;
import egovframework.com.asapro.organization.service.OrganizationService;
import egovframework.com.asapro.rental.service.PossibleDayTime;
import egovframework.com.asapro.rental.service.RentReservTime;
import egovframework.com.asapro.rental.service.Rental;
import egovframework.com.asapro.rental.service.RentalReservCloseTime;
import egovframework.com.asapro.rental.service.RentalReservationInfo;
import egovframework.com.asapro.rental.service.RentalReservationSearch;
import egovframework.com.asapro.rental.service.RentalSearch;
import egovframework.com.asapro.rental.service.RentalService;
import egovframework.com.asapro.rental.service.TimeCalculation;
import egovframework.com.asapro.site.service.Site;
import egovframework.com.asapro.support.Constant;
import egovframework.com.asapro.support.ServerMessage;
import egovframework.com.asapro.support.annotation.CurrentAdmin;
import egovframework.com.asapro.support.annotation.CurrentSite;
import egovframework.com.asapro.support.annotation.PrivacyHistory;
import egovframework.com.asapro.support.exception.AsaproException;
import egovframework.com.asapro.support.exception.AsaproNotFoundException;
import egovframework.com.asapro.support.pagination.Paging;
import egovframework.com.asapro.support.util.AsaproMailUtils;
import egovframework.com.asapro.support.util.AsaproUtils;
import egovframework.com.campustown.univHpMngr.service.UnivHpMngr;
import egovframework.com.campustown.univHpMngr.service.UnivHpMngrSearch;
import egovframework.com.campustown.univHpMngr.service.UnivHpMngrService;

/**
 * 대관/대여 관리자 컨트롤러
 * @author yckim
 * @since 2018. 10. 29.
 *
 */
@Controller
public class RentalAdminController {
	
	@Autowired
	private RentalService rentalService;
	
	@Autowired
	private RentalValidator rentalValidator;
	
	@Autowired
	private RentalReservationValidator rentalReservationValidator;
	
	@Autowired
	private HttpServletRequest request;
	
	@Autowired
	private FileInfoService fileInfoService;
	
	@Autowired
	private CodeService codeService;
	
	@Autowired
	private OrganizationService organizationService;
	
	@Autowired
	private UnivHpMngrService univHpMngrService;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(RentalAdminController.class);
	
	/**
	 * 코드목록 로드
	 * @param model
	 */
	private void loadCodes(Model model) {
		//지역번호
		model.addAttribute("areaCodeList", codeService.getCodeList("tel_area_code"));	//codeOrder ASC
		//휴대폰번호 앞자리
		model.addAttribute("mobile1CodeList", codeService.getCodeList("mobile1_code"));
		//직급/직책 목록
		//model.addAttribute("positionList", codeService.getCodeList("position"));	
		
		//신청기관 구분 코드목록
		List<Code> orgDivCodeList = codeService.getCodeList("reservOrgDivCode");
		model.addAttribute("orgDivCodeList", orgDivCodeList);
		
		//접수방법 코드목록
		List<Code> rentReservTypeCodeList = codeService.getCodeList("rentReservType");
		model.addAttribute("rentReservTypeCodeList", rentReservTypeCodeList);
		
		//결제수단 코드목록
		List<Code> rentPaymentTypeCodeList = codeService.getCodeList("rentPaymentType");
		model.addAttribute("rentPaymentTypeCodeList", rentPaymentTypeCodeList);
		
		//신청상태 코드목록
		List<Code> statusCodeList = codeService.getCodeList("rentReservStatus");
		model.addAttribute("statusCodeList", statusCodeList);
		
	}
	
	/**
	 * 카테고리목록 로드
	 * @param model
	 */
	private void loadCate(Model model) {
		//카테고리1 목록
		//시설 구분코드1
		//model.addAttribute("rentCate1List", codeService.getCodeList("rent_cate1"));	//codeOrder ASC
		//부서목록
		/*DepartmentSearch departmentSearch = new DepartmentSearch();
		departmentSearch.setPaging(false);
		departmentSearch.setDepUse(true);
		departmentSearch.setOrgId("university");	//대학에 대한 목록을 위해 하드코딩처리
		List<Department> departmentList = organizationService.getDepartmentList(departmentSearch);
		model.addAttribute("rentCate1List", departmentList);*/
		
		//캠퍼스타운 목록
		UnivHpMngrSearch univHpMngrSearch = new UnivHpMngrSearch();
		univHpMngrSearch.setPaging(false);
		List<UnivHpMngr> univList = univHpMngrService.getUnivHpMngrList(univHpMngrSearch);
		model.addAttribute("rentCate1List", univList);
		
		//카테고리2 목록
		//시설 구분코드2
		//model.addAttribute("rentCate2List", codeService.getCodeList("rent_cate2"));	//codeOrder ASC
		model.addAttribute("rentCate2List", codeService.getCodeList("SIGUNGU_CODE", "CODE_NAME", "ASC"));	//
		
	}
	
	/**
	 * 불가능 날짜 목록과 등록된 가능 시간목록을 모델에 담아준다.
	 * @param model
	 * @param rentalReservationForm
	 * @param currentSite
	 * @throws ParseException 
	 */
	private void impossibleDateArray(Model model, RentalReservationInfo rentalReservationForm, Site currentSite) throws ParseException{
		
		Rental rental = rentalReservationForm.getRental();
		
		//요일별 가능 시간목록
		String[] tempTermArrayWeekday = null;
		String[] tempTermArraySat = null;
		String[] tempTermArraySun = null;
		
		if(StringUtils.isNotBlank(rental.getRentReservTime()) ){
			tempTermArrayWeekday = rental.getRentReservTime().split(",");
		}
		if(StringUtils.isNotBlank(rental.getRentReservTimeSat()) ){
			tempTermArraySat = rental.getRentReservTimeSat().split(",");
		}
		if(StringUtils.isNotBlank(rental.getRentReservTimeSun()) ){
			tempTermArraySun = rental.getRentReservTimeSun().split(",");
		}
		
		//수정인 경우 가능 시간대를 다른 예약에없는 가능한 시간대로만 목록을 만든다.
		if(StringUtils.isNotBlank(rentalReservationForm.getReservId()) ){//수정인 경우
			
		}
		
		
		
		//=====================================================
		//예약불가일 목록(이미 예약시간이 다 차있어 불가한 날짜 목록)
		RentalReservationSearch rentalReservationSearch = new RentalReservationSearch();
		rentalReservationSearch.setSitePrefix(currentSite.getSitePrefix());
		rentalReservationSearch.fixBrokenSvByDefaultCharsets();
		rentalReservationSearch.setPaging(false);
		rentalReservationSearch.setReservRentId(rental.getRentId());
		rentalReservationSearch.setPossibleStatus("impossible");	//취소를 재외한 접수(승인대기),승인완료, 결제대기, 예약완료 목록
		Date startDate = AsaproUtils.makeCalculDate(rental.getRentReservSdateAfter());
		rentalReservationSearch.setReservStartDate(AsaproUtils.getFormattedDate(startDate,"yyyy-MM-dd") );
		Date endDate = AsaproUtils.makeCalculDate(rental.getRentReservEdateAfter());
		rentalReservationSearch.setReservEndDate(AsaproUtils.getFormattedDate(endDate,"yyyy-MM-dd") );
		rentalReservationSearch.setSortOrder("RESERV_DATE");
		rentalReservationSearch.setSortDirection("ASC");
		
		List<RentalReservationInfo> reservlist = rentalService.getRentalReservationList(rentalReservationSearch);
		
		int lastIndex = reservlist.size();
		int index = 1;
		String day = "";	//이전 예약정보 날짜 - 비교위해
		List<String> timeList = new ArrayList<String>();
		PossibleDayTime reservedDayTime = null;
		List<PossibleDayTime> reservedDayTimeList = new ArrayList<PossibleDayTime>();
		for (RentalReservationInfo rentalReservationInfo : reservlist) {
			String[] reservTimeArray = null;
			if(StringUtils.isNotBlank(rentalReservationInfo.getReservTime()) ){
				reservTimeArray = rentalReservationInfo.getReservTime().split(",");
			}
			
			if(!day.equals(rentalReservationInfo.getReservDate()) ){
				//처음음 객체셋트하지 않는다.
				if(index > 1){
					reservedDayTime = new PossibleDayTime(); 
					reservedDayTime.setPossibleDay(day);
					reservedDayTime.setPossibleTime(timeList);
					reservedDayTimeList.add(reservedDayTime);
				}
				
				day = rentalReservationInfo.getReservDate();
				timeList = new ArrayList<String>();
			}				
			for (String time : reservTimeArray) {
				timeList.add(time);
			}
			
			//마지막은 무조건 객체에 셋트한다.
			if(index == lastIndex ){
				reservedDayTime = new PossibleDayTime(); 
				reservedDayTime.setPossibleDay(day);
				reservedDayTime.setPossibleTime(timeList);
				reservedDayTimeList.add(reservedDayTime);
			}
			
			index++;
			
		}
		
		
		
		//=========================================================
		List<String> impossibleDayArray = new ArrayList<String>();
		for (PossibleDayTime reservedDayTime2 : reservedDayTimeList) {
			
			String[] tempTermArray = null;
			
			//예약일의 요일을 구한다.
			String dayOfWeek = AsaproUtils.getDayOfWeekText(reservedDayTime2.getPossibleDay());
			//요일에 따라 가능 시간목록 선택
			if("Sat".equals(dayOfWeek) ){
				tempTermArray = tempTermArraySat;
			} else if("Sun".equals(dayOfWeek) ){
				tempTermArray = tempTermArraySun;
			} else {
				tempTermArray = tempTermArrayWeekday;
			}
			
			if(reservedDayTime2.getPossibleTime() != null && tempTermArray != null 
					&& reservedDayTime2.getPossibleTime().size() >= tempTermArray.length){
				impossibleDayArray.add(reservedDayTime2.getPossibleDay());
			}
			
		}
		model.addAttribute("impossibleDayArray",impossibleDayArray);
		
		
		//========================================================
		//예약가능 일짜의 구간과 대관정보로 예약시간 닫음 정보 목록을 검색한다
		List<RentalReservCloseTime> rentalReservCloseTimeList = null;
		RentalReservCloseTime rentalReservCloseTimeSearch = new RentalReservCloseTime();
		rentalReservCloseTimeSearch.setRentId(rental.getRentId());
		rentalReservCloseTimeSearch.setReservStartDate(rentalReservationSearch.getReservStartDate());
		rentalReservCloseTimeSearch.setReservEndDate(rentalReservationSearch.getReservEndDate());
		rentalReservCloseTimeSearch.setSitePrefix(rentalReservationForm.getSitePrefix());
		rentalReservCloseTimeList = rentalService.selectRentalReservCloseTimeList(rentalReservCloseTimeSearch);
		
		//닫음처리 목록을 PossibleDayTime 객체에 담아 목록으로 만든다
		List<PossibleDayTime> closedDayTimeList = new ArrayList<PossibleDayTime>();//닫음처리목록
		
		if(rentalReservCloseTimeList != null && !rentalReservCloseTimeList.isEmpty() ){
			String chkDay = "";
			PossibleDayTime possibleDayTime = null;
			int lastIndex2 = rentalReservCloseTimeList.size();
			int index2 = 1;
			for (RentalReservCloseTime rentalReservCloseTime : rentalReservCloseTimeList) {//닫음처리목록
				if(!chkDay.equals(rentalReservCloseTime.getReservDate()) ){
					if(index2 > 1){
						closedDayTimeList.add(possibleDayTime);
					}
					possibleDayTime = new PossibleDayTime();
					possibleDayTime.setPossibleDay(rentalReservCloseTime.getReservDate());
					List<String> possibleTime = new ArrayList<String>();
					possibleTime.add(rentalReservCloseTime.getReservCloseTime());
					possibleDayTime.setPossibleTime(possibleTime);
				} else {
					possibleDayTime.getPossibleTime().add(rentalReservCloseTime.getReservCloseTime());
				}
				
				if(index2 == lastIndex2 ){
					closedDayTimeList.add(possibleDayTime);
				}
				
				index2++;
				chkDay = rentalReservCloseTime.getReservDate();
			}
		}
		
		//======================================================================
		//기 예약 목록과 닫음처리 목록을 합친다
		ArrayList<PossibleDayTime> reservedAndClosedDayTimeList = new ArrayList<PossibleDayTime>();
		for (PossibleDayTime reservedDayTimeTemp : reservedDayTimeList) {//기예약
			reservedAndClosedDayTimeList.add(reservedDayTimeTemp);
		}
		
		for (PossibleDayTime closedDayTime : closedDayTimeList) {//닫음처리
			for (PossibleDayTime reservedAndClosedDayTime : reservedAndClosedDayTimeList) {//
				if(closedDayTime.getPossibleDay().equals(reservedAndClosedDayTime.getPossibleDay()) ){
					reservedAndClosedDayTime.getPossibleTime().addAll(closedDayTime.getPossibleTime());
					continue;
				}
			}
			//기예약목록에 없는 닫음처리 날짜이면 목록에 추가
			reservedAndClosedDayTimeList.add(closedDayTime);
			
		}
		
		//=========================================================
		List<String> impossibleDayIncludeCloseArray = new ArrayList<String>();
		for (PossibleDayTime reservedAndClosedDayTime : reservedAndClosedDayTimeList) {
			String[] tempTermArray = null;
			
			//예약일의 요일을 구한다.
			String dayOfWeek = AsaproUtils.getDayOfWeekText(reservedAndClosedDayTime.getPossibleDay());
			//요일에 따라 가능 시간목록 선택
			if("Sat".equals(dayOfWeek) ){
				tempTermArray = tempTermArraySat;
			} else if("Sun".equals(dayOfWeek) ){
				tempTermArray = tempTermArraySun;
			} else {
				tempTermArray = tempTermArrayWeekday;
			}
			
			if(reservedAndClosedDayTime.getPossibleTime() != null && tempTermArray != null 
					&& reservedAndClosedDayTime.getPossibleTime().size() >= tempTermArray.length){
				impossibleDayIncludeCloseArray.add(reservedAndClosedDayTime.getPossibleDay());
			}
			
		}
		model.addAttribute("impossibleDayIncludeCloseArray",impossibleDayIncludeCloseArray);
		
		
	}
	
	/**
	 * 예약 가능한 시간목록을 반환한다
	 * @param rentalReservationForm
	 * @return 예약가능 시간목록
	 * @throws ParseException
	 */
	private List<TimeCalculation> getPossibleTimeList(RentalReservationInfo rentalReservationForm) throws ParseException{
		Rental rental = rentalReservationForm.getRental();
		boolean isUpdate = false;//신규추가가 아닌 수정인경우 구분
		
		//예약일자와 대관정보로 신청정보 목록 검색
		List<RentalReservationInfo> rentalReservationInfoList = null;
		if(StringUtils.isNotBlank(rentalReservationForm.getReservDate()) ){
			rentalReservationInfoList = rentalService.getRentalReservationListByDate(rentalReservationForm);
		}
		
		//예약일자와 대관정보로 예약시간 닫음 정보 목록을 검색한다
		List<RentalReservCloseTime> rentalReservCloseTimeList = null;
		if(StringUtils.isNotBlank(rentalReservationForm.getReservDate()) ){
			RentalReservCloseTime rentalReservCloseTimeSearch = new RentalReservCloseTime();
			rentalReservCloseTimeSearch.setRentId(rental.getRentId());
			rentalReservCloseTimeSearch.setReservDate(rentalReservationForm.getReservDate());
			rentalReservCloseTimeSearch.setSitePrefix(rentalReservationForm.getSitePrefix());
			rentalReservCloseTimeList = rentalService.selectRentalReservCloseTimeList(rentalReservCloseTimeSearch);
		}
		
		//수정일 경우 현제의 예약정보를 가져온다. - 예약일과 시간을 비교하기위해
		RentalReservationInfo currentReservationInfo = null;
		List<String> currentReservTimeList = null;
		if(StringUtils.isNotBlank(rentalReservationForm.getReservId()) ){
			isUpdate = true;
			currentReservationInfo = rentalService.getRentalReservation(rentalReservationForm);
			if(StringUtils.isNotBlank(currentReservationInfo.getReservTime()) ){
				currentReservTimeList = Arrays.asList(currentReservationInfo.getReservTime().split(","));
			}
		}
		
		//예약일의 요일을 구한다.
		String dayOfWeek = AsaproUtils.getDayOfWeekText(rentalReservationForm.getReservDate());
		//요일에 따라 가능 시간목록 선택
		String targetReservTime = "";
		if("Sat".equals(dayOfWeek) ){
			targetReservTime = rental.getRentReservTimeSat();
		} else if("Sun".equals(dayOfWeek) ){
			targetReservTime = rental.getRentReservTimeSun();
		} else {
			targetReservTime = rental.getRentReservTime();
		}

		//예약가능시간 전체 String  
		List<String> termList =  new ArrayList<String>();
		if(StringUtils.isNotBlank(targetReservTime) ){
			String[] tempTermArray = targetReservTime.split(",");
			
			for (String tempTerm : tempTermArray) {
				
				String[] term = tempTerm.split(";");
				
				StringBuilder sb = new StringBuilder();
				sb.append(term[0] );
				sb.append("~" );
				sb.append(term[1] );
				termList.add(sb.toString());
			}
		}
		
		//신청정보의 모든 신청시간을 담는다.
		List<String> reservTimeSumList = new ArrayList<String>();	
		//예약시간 닫힘처리 시간을 담는다.
		List<String> closeTimeSumList = new ArrayList<String>();	
		//최종 신청 가능한 시간과 시간갭 목록
		List<TimeCalculation> possibleTimeList = new ArrayList<TimeCalculation>();	
		
		if(rentalReservationInfoList != null && !rentalReservationInfoList.isEmpty() ){//대관일자로 검색한 예약정보
			for (RentalReservationInfo rentalReservationInfo : rentalReservationInfoList) {
				String[] reservTimeArray = null;
				if(StringUtils.isNotBlank(rentalReservationInfo.getReservTime()) ){
					reservTimeArray = rentalReservationInfo.getReservTime().split(",");
				}
				
				for (String string : reservTimeArray) {
					if(isUpdate){//수정이면서
						if(currentReservationInfo != null && StringUtils.isNotBlank(currentReservationInfo.getReservDate()) ){//원데이터가 있고 원데이터의 신청일자가 있으면
							if(!currentReservationInfo.getReservDate().equals(rentalReservationInfo.getReservDate()) ){//원데이터와 사용일자가 다르면
								reservTimeSumList.add(string);//신청정보의 신청시간을 담는다.
							}else{//원데이터와 사용일자가 같으면
								if(currentReservTimeList != null && !currentReservTimeList.isEmpty() ){
									if(!currentReservTimeList.contains(string) ){//원데이터의 시간은 재외하고 다른것만 담는다
										reservTimeSumList.add(string);//신청정보의 모든 신청시간을 담는다.
									}
								}
							}
						}
					}else{
						reservTimeSumList.add(string);//신청정보의 모든 신청시간을 담는다.
					}
				}
			}
		}
		
		if(rentalReservCloseTimeList != null && !rentalReservCloseTimeList.isEmpty() ){//닫힘처리한 시간목록
			for (RentalReservCloseTime rentalReservCloseTime : rentalReservCloseTimeList) {
				closeTimeSumList.add(rentalReservCloseTime.getReservCloseTime());
			}
		}
		
		for (String term : termList) {
			String[] timeArray = term.split("~");
			TimeCalculation timeCalculation = new TimeCalculation();

			timeCalculation.setTime(term);
			if("time".equals(rental.getRentRentingMethod()) ){
				int gap = AsaproUtils.calculTimeOfTwoTimes(timeArray[0], timeArray[1]);
				timeCalculation.setTimeGap(gap);
			} else {
				timeCalculation.setTimeGap(1);	//package
			}
			
			/**
			 * 상태값 - 가능/완료/닫음 
			 * (possible/complete/close)
			 */
			timeCalculation.setTimeStatus("possible");
			if(reservTimeSumList != null && !reservTimeSumList.isEmpty() ){
				if(reservTimeSumList.contains(term) ){//기존 신청 시간대 이면
					timeCalculation.setTimeStatus("complete");
				}
			}
			if(closeTimeSumList != null && !closeTimeSumList.isEmpty() ){
				if(closeTimeSumList.contains(term) ){//닫음 처리한 시간대 이면
					timeCalculation.setTimeStatus("close");
				}
			}
				
			possibleTimeList.add(timeCalculation);	//최종 신청 가능한 시간목록
		}
		
		//model.addAttribute("possibleTimeList", possibleTimeList) ;
		
		return possibleTimeList;
	}
	

	//=============================================================================================================================
	//==========================================  대관/대여 기본정보, 신청설정정보   ================================================================
	//=============================================================================================================================
	
	/**
	 * 대여/대관 목록을 반환한다.
	 * @param model
	 * @param currentSite
	 * @param rentalSearch
	 * @return 목록
	 */
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/rental/list.do", method=RequestMethod.GET)
	public String rentalListGet(Model model, @CurrentSite Site currentSite, @ModelAttribute("rentalSearch") RentalSearch rentalSearch){
		rentalSearch.setSitePrefix(currentSite.getSitePrefix());
		rentalSearch.fixBrokenSvByDefaultCharsets();
		rentalSearch.setPaging(true);
		
		List<Rental> list = rentalService.getRentalList(rentalSearch);
		int total = rentalService.getRentalListTotal(rentalSearch);
		
		Paging paging = new Paging(list, total, rentalSearch);
		
		model.addAttribute("paging", paging);
		
		this.loadCate(model);
		
		//접수방법 코드목록
		List<Code> rentReservTypeCodeList = codeService.getCodeList("rentReservType");
		model.addAttribute("rentReservTypeCodeList", rentReservTypeCodeList);
		
		//결제수단 코드목록
		List<Code> rentPaymentTypeCodeList = codeService.getCodeList("rentPaymentType");
		model.addAttribute("rentPaymentTypeCodeList", rentPaymentTypeCodeList);
		
		//model.addAttribute("bnToday", new SimpleDateFormat("yyyy-MM-dd", Locale.KOREA).format(new Date()));
		
		return "asapro/admin/rental/list";
	}
	
	/**
	 * 대여/대관 추가폼 뷰를 반환한다.
	 * @param model
	 * @param currentSite
	 * @param rentalForm
	 * @return 추가폼뷰
	 */
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/rental/insert.do", method=RequestMethod.GET)
	public String rentalInsertGet(Model model, @CurrentSite Site currentSite, @ModelAttribute("rentalForm") Rental rentalForm){
		
		this.loadCate(model);
		
		//접수방법 코드목록
		List<Code> rentReservTypeCodeList = codeService.getCodeList("rentReservType");
		model.addAttribute("rentReservTypeCodeList", rentReservTypeCodeList);
		
		//결제수단 코드목록
		List<Code> rentPaymentTypeCodeList = codeService.getCodeList("rentPaymentType");
		model.addAttribute("rentPaymentTypeCodeList", rentPaymentTypeCodeList);
		
		model.addAttribute("formMode", "INSERT");
		return "asapro/admin/rental/form";
	}
	
	/**
	 * 대여/대관을 추가한다.
	 * @param model
	 * @param redirectAttributes
	 * @param currentSite
	 * @param rentalForm
	 * @param bindingResult
	 * @return 추가결과
	 */
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/rental/insert.do", method=RequestMethod.POST)
	public String rentalInsertPost(Model model, RedirectAttributes redirectAttributes, @CurrentSite Site currentSite, @ModelAttribute("rentalForm") Rental rentalForm, BindingResult bindingResult) throws AsaproException, IOException{
		rentalForm.setSitePrefix(currentSite.getSitePrefix());
		rentalValidator.validate(rentalForm, bindingResult, "INSERT");
		
		if( bindingResult.hasErrors() ){
			this.loadCate(model);
			
			//접수방법 코드목록
			List<Code> rentReservTypeCodeList = codeService.getCodeList("rentReservType");
			model.addAttribute("rentReservTypeCodeList", rentReservTypeCodeList);
			
			//결제수단 코드목록
			List<Code> rentPaymentTypeCodeList = codeService.getCodeList("rentPaymentType");
			model.addAttribute("rentPaymentTypeCodeList", rentPaymentTypeCodeList);
			
			model.addAttribute("formMode", "INSERT");
			model.addAttribute("insertFail", true);
			return "asapro/admin/rental/form";
		} else {
			rentalForm.setRentRegDate(new Date());
			rentalForm.setRentStep("1");
			
			//========== 첨부 이미지 처리 =====================
			
			//첨부가능한 확장자
			String uploadWhiteList = "";
			
			FileInfoUploadResult thumbFileInfoUploadResult = new FileInfoUploadResult();
			
			//이미지 처리
			if( rentalForm.getRentImage() != null && rentalForm.getRentImage().getSize() > 0 ){

				uploadWhiteList = Constant.UPLOAD_IMAGE_WHITE_LIST; 
				String webRoot = AsaproUtils.getWebRoot(request);
				
				//첨부된 파일 
				FileInfo fileInfo = new FileInfo();
				fileInfo.setSitePrefix(rentalForm.getSitePrefix());
				//모듈1차pk
				fileInfo.setFileModule("rental");
				fileInfo.setFileModuleId("");
				//모듈2차pk
				fileInfo.setFileModuleSub("");
				fileInfo.setFileModuleSubId("");
				//멤버
				fileInfo.setMemberId("");
				//첨부유형
				fileInfo.setFileAttachmentType("thumbnail");
				//대체텍스트
				fileInfo.setFileAltText( rentalForm.getRentTitle() );
				
				//썸네일 파일은 만든다.
				fileInfoService.saveFile(rentalForm.getRentImage(), webRoot, fileInfo, (Constant.MEGA * 1), true, 400, 280, true, uploadWhiteList);
				if( !fileInfo.isFileUploadSuccess() ){
					thumbFileInfoUploadResult.addErrorInfo(fileInfo);
				}
				
				rentalForm.setThumb(fileInfo);
				
			}

			//업로드 에러 있으면 등록화면으로 전환
			if(thumbFileInfoUploadResult.hasErrors() ){
				this.loadCate(model);
				
				//접수방법 코드목록
				List<Code> rentReservTypeCodeList = codeService.getCodeList("rentReservType");
				model.addAttribute("rentReservTypeCodeList", rentReservTypeCodeList);
				
				//결제수단 코드목록
				List<Code> rentPaymentTypeCodeList = codeService.getCodeList("rentPaymentType");
				model.addAttribute("rentPaymentTypeCodeList", rentPaymentTypeCodeList);
				
				model.addAttribute("thumbFileInfoUploadResult", thumbFileInfoUploadResult);
				model.addAttribute("formMode", "INSERT");
				model.addAttribute("insertFail", true);
				return "asapro/admin/rental/form";
			}
			//========== 첨부 이미지 처리 =====================
			
			rentalService.insertRental(rentalForm);
			redirectAttributes.addFlashAttribute("inserted", true);
			return "redirect:" + AsaproUtils.getAdminPath(currentSite, true) + "/rental/update.do?rentId=" + rentalForm.getRentId() + "&step=applySet";
		}
		
	}
	
	/**
	 * 대여/대관 수정폼 뷰를 반환한다.
	 * @param model
	 * @param currentSite
	 * @param rentalForm
	 * @return 수정폼뷰
	 */
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/rental/update.do", method=RequestMethod.GET)
	public String rentalUpdateGet(Model model, @CurrentSite Site currentSite, @ModelAttribute("rentalForm") Rental rentalForm){
		//step 접보가 없이 수정이 호출되면 디폴트 basic 셋
		if(StringUtils.isBlank(rentalForm.getStep()) ){
			rentalForm.setStep("basic");
		}
		rentalForm.setSitePrefix(currentSite.getSitePrefix());
		Rental rentalModel = rentalService.getRental(rentalForm);
		rentalModel.setStep(rentalForm.getStep());
		
		this.loadCate(model);
		
		//접수방법 코드목록
		List<Code> rentReservTypeCodeList = codeService.getCodeList("rentReservType");
		model.addAttribute("rentReservTypeCodeList", rentReservTypeCodeList);
		
		//결제수단 코드목록
		List<Code> rentPaymentTypeCodeList = codeService.getCodeList("rentPaymentType");
		model.addAttribute("rentPaymentTypeCodeList", rentPaymentTypeCodeList);
		
		//신청설정정보 조회인 경우만
		if("applySet".equals(rentalForm.getStep()) ){
			//요일코드목록
			List<Code> dayOfWeekCodeList = codeService.getCodeList("DAYOFWEEK");
			model.addAttribute("dayOfWeekCodeList", dayOfWeekCodeList);
			
			//가능요일 배열에 담는다
			if(StringUtils.isNotBlank(rentalModel.getRentPossibleDayOfWeek()) ){
				String[] possibleDayOfWeekArray = rentalModel.getRentPossibleDayOfWeek().split(",");
				model.addAttribute("possibleDayOfWeekArray", possibleDayOfWeekArray);
			}
			
			//예약가능시간 String  => List<RentReservTime> 로 변환 (평일)
			if(StringUtils.isNotBlank(rentalModel.getRentReservTime()) ){
				String[] tempTermArray = rentalModel.getRentReservTime().split(",");
				
				RentReservTime reservTime = null;
				for (String tempTerm : tempTermArray) {
					reservTime = new RentReservTime();
					
					String[] term = tempTerm.split(";");
					reservTime.setRentReservStartTime(term[0]);
					reservTime.setRentReservEndTime(term[1]);
					reservTime.setRentIsNextday(Boolean.parseBoolean(term[2]) );

					rentalModel.getRentReservTimeList().add(reservTime);
				}
			}
			//예약가능시간 String  => List<RentReservTime> 로 변환 (토요일)
			if(StringUtils.isNotBlank(rentalModel.getRentReservTimeSat()) ){
				String[] tempTermArray = rentalModel.getRentReservTimeSat().split(",");
				
				RentReservTime reservTime = null;
				for (String tempTerm : tempTermArray) {
					reservTime = new RentReservTime();
					
					String[] term = tempTerm.split(";");
					reservTime.setRentReservStartTime(term[0]);
					reservTime.setRentReservEndTime(term[1]);
					reservTime.setRentIsNextday(Boolean.parseBoolean(term[2]) );
					
					rentalModel.getRentReservTimeSatList().add(reservTime);
				}
			}
			//예약가능시간 String  => List<RentReservTime> 로 변환 (일요일)
			if(StringUtils.isNotBlank(rentalModel.getRentReservTimeSun()) ){
				String[] tempTermArray = rentalModel.getRentReservTimeSun().split(",");
				
				RentReservTime reservTime = null;
				for (String tempTerm : tempTermArray) {
					reservTime = new RentReservTime();
					
					String[] term = tempTerm.split(";");
					reservTime.setRentReservStartTime(term[0]);
					reservTime.setRentReservEndTime(term[1]);
					reservTime.setRentIsNextday(Boolean.parseBoolean(term[2]) );
					
					rentalModel.getRentReservTimeSunList().add(reservTime);
				}
			}
			
			//최초 회차시간 없으면 1개 셋트(평일)
			if(rentalModel.getRentReservTimeList() == null || rentalModel.getRentReservTimeList().isEmpty() ){
				RentReservTime reservTime = new RentReservTime();
				rentalModel.getRentReservTimeList().add(reservTime);
			}
			//최초 회차시간 없으면 1개 셋트(토요일)
			if(rentalModel.getRentReservTimeSatList() == null || rentalModel.getRentReservTimeSatList().isEmpty() ){
				RentReservTime reservTime = new RentReservTime();
				rentalModel.getRentReservTimeSatList().add(reservTime);
			}
			//최초 회차시간 없으면 1개 셋트(일요일)
			if(rentalModel.getRentReservTimeSunList() == null || rentalModel.getRentReservTimeSunList().isEmpty() ){
				RentReservTime reservTime = new RentReservTime();
				rentalModel.getRentReservTimeSunList().add(reservTime);
			}
		}
		
		model.addAttribute("formMode", "UPDATE");
		model.addAttribute("rentalForm", rentalModel);
		return "asapro/admin/rental/form";
	}
	
	/**
	 * 대여/대관을 수정한다.
	 * @param model
	 * @param redirectAttributes
	 * @param currentSite
	 * @param rentalForm
	 * @param bindingResult
	 * @return 수정결과
	 */
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/rental/update.do", method=RequestMethod.POST)
	public String rentalUpdatePost(Model model, RedirectAttributes redirectAttributes, @CurrentSite Site currentSite, @ModelAttribute("rentalForm") Rental rentalForm, BindingResult bindingResult) throws AsaproException, IOException{
		rentalForm.setSitePrefix(currentSite.getSitePrefix());
		//if(StringUtils.isNotBlank(rentalForm.getStep()) ){
		//	rentalForm.setStep("basic");
		//}
		
		Rental rentalModel = rentalService.getRental(rentalForm);
		//rentalForm.setRentTitle(rentalModel.getRentTitle());
		
		//basic/applySet 구분
		rentalValidator.validate(rentalForm, bindingResult, "UPDATE_" + rentalForm.getStep().toUpperCase());
		
		this.loadCate(model);
		
		//접수방법 코드목록
		List<Code> rentReservTypeCodeList = codeService.getCodeList("rentReservType");
		model.addAttribute("rentReservTypeCodeList", rentReservTypeCodeList);
		
		//결제수단 코드목록
		List<Code> rentPaymentTypeCodeList = codeService.getCodeList("rentPaymentType");
		model.addAttribute("rentPaymentTypeCodeList", rentPaymentTypeCodeList);
		
		if( bindingResult.hasErrors() ){
			rentalForm.setRentStep(rentalModel.getRentStep());
			
			//신청설정정보 조회인 경우만
			if("applySet".equals(rentalForm.getStep()) ){
				//요일코드목록
				List<Code> dayOfWeekCodeList = codeService.getCodeList("DAYOFWEEK");
				model.addAttribute("dayOfWeekCodeList", dayOfWeekCodeList);
				
				//가능요일 배열에 담는다
				if(StringUtils.isNotBlank(rentalModel.getRentPossibleDayOfWeek()) ){
					String[] possibleDayOfWeekArray = rentalModel.getRentPossibleDayOfWeek().split(",");
					model.addAttribute("possibleDayOfWeekArray", possibleDayOfWeekArray);
				}

			}
			
			model.addAttribute("formMode", "UPDATE");
			model.addAttribute("updateFail", true);
			return "asapro/admin/rental/form";
		} else {
			
			//기본정보 수정
			if("basic".equals(rentalForm.getStep()) ){
				rentalForm.setThumb(rentalModel.getThumb());
				
				//========== 첨부 이미지 처리 =====================
				
				//첨부가능한 확장자
				String uploadWhiteList = Constant.UPLOAD_IMAGE_WHITE_LIST; 
				
				FileInfoUploadResult thumbFileInfoUploadResult = new FileInfoUploadResult();
				
				//새 이미지 처리
				if( rentalForm.getRentImage() != null && rentalForm.getRentImage().getSize() > 0 ){
					
					//기존이미지 삭제
					if(rentalModel.getThumb() != null && rentalModel.getThumb().getFileId() > 0){
						FileInfo fileInfoImage = rentalModel.getThumb();
						fileInfoImage.setSitePrefix(rentalForm.getSitePrefix());
						fileInfoService.deleteFileInfo(fileInfoImage);
					}
					
					String webRoot = AsaproUtils.getWebRoot(request);
					
					//첨부된 파일 
					FileInfo fileInfo = new FileInfo();
					fileInfo.setSitePrefix(rentalForm.getSitePrefix());
					//모듈1차pk
					fileInfo.setFileModule("rental");
					fileInfo.setFileModuleId("");
					//모듈2차pk
					fileInfo.setFileModuleSub("");
					fileInfo.setFileModuleSubId("");
					//멤버
					fileInfo.setMemberId("");
					//첨부유형
					fileInfo.setFileAttachmentType("thumbnail");
					//대체텍스트
					fileInfo.setFileAltText( rentalForm.getRentTitle() );
					
					//썸네일 파일은 만든다.
					fileInfoService.saveFile(rentalForm.getRentImage(), webRoot, fileInfo, (Constant.MEGA * 1), true, 400, 280, true, uploadWhiteList);
					if( !fileInfo.isFileUploadSuccess() ){
						thumbFileInfoUploadResult.addErrorInfo(fileInfo);
					}
					
					rentalForm.setThumb(fileInfo);
					
				}
				
				//업로드 에러 있으면 바로 수정화면으로 전환
				if(thumbFileInfoUploadResult.hasErrors() ){
					//FlashMap flashMap = RequestContextUtils.getOutputFlashMap(request);
					//flashMap.put("thumbFileInfoUploadResult", thumbFileInfoUploadResult);
					
					//redirectAttributes.addFlashAttribute("thumbFileInfoUploadResult", thumbFileInfoUploadResult);
					//return "redirect:" + AsaproUtils.getAdminPath(currentSite, true) +  "/banner/update.do?bnType=" + bannerForm.getBnType() + "&bnId=" + bannerForm.getBnId();
					
					model.addAttribute("thumbFileInfoUploadResult", thumbFileInfoUploadResult);
					model.addAttribute("formMode", "UPDATE");
					model.addAttribute("updateFail", true);
					return "asapro/admin/rental/form";
				}
				//========== 첨부 이미지 처리 =====================
				
				rentalService.updateRentalBasic(rentalForm);
			}
			//신청설정정보 수정
			else if("applySet".equals(rentalForm.getStep()) ){
				//기본정보만 등록 되어있는 등록진행중인 상태이고 신청설정 정보를 수정하는경우 status를 "2"로 등록 완료 코드 입력
				if("1".equals(rentalModel.getRentStep()) && "applySet".equals(rentalForm.getStep()) ){
					rentalForm.setRentStep("2");
					rentalForm.setRentUse(true);
					
				}else{
					rentalForm.setRentStep(null);
				}
				
				//예약가능시간 텍스트로변환 (평일)
				if(rentalForm.getRentReservTimeList() != null && !rentalForm.getRentReservTimeList().isEmpty() ){
					StringBuilder tempTerm = new StringBuilder();
					boolean firsTerm = true;
					for (RentReservTime rentReservTime : rentalForm.getRentReservTimeList() ) {
						if(!firsTerm){
							tempTerm.append("," );
						}
						
						tempTerm.append(rentReservTime.getRentReservStartTime() );
						tempTerm.append(";" );
						tempTerm.append(rentReservTime.getRentReservEndTime() );
						tempTerm.append(";" );
						tempTerm.append(rentReservTime.isRentIsNextday() );
						
						firsTerm = false;
					}
					rentalForm.setRentReservTime(tempTerm.toString());
				}
				//예약가능시간 텍스트로변환 (토요일)
				if(rentalForm.getRentReservTimeSatList() != null && !rentalForm.getRentReservTimeSatList().isEmpty() ){
					StringBuilder tempTerm = new StringBuilder();
					boolean firsTerm = true;
					for (RentReservTime rentReservTime : rentalForm.getRentReservTimeSatList() ) {
						if(!firsTerm){
							tempTerm.append("," );
						}
						
						tempTerm.append(rentReservTime.getRentReservStartTime() );
						tempTerm.append(";" );
						tempTerm.append(rentReservTime.getRentReservEndTime() );
						tempTerm.append(";" );
						tempTerm.append(rentReservTime.isRentIsNextday() );
						
						firsTerm = false;
					}
					rentalForm.setRentReservTimeSat(tempTerm.toString());
				}
				//예약가능시간 텍스트로변환 (일요일)
				if(rentalForm.getRentReservTimeSunList() != null && !rentalForm.getRentReservTimeSunList().isEmpty() ){
					StringBuilder tempTerm = new StringBuilder();
					boolean firsTerm = true;
					for (RentReservTime rentReservTime : rentalForm.getRentReservTimeSunList() ) {
						if(!firsTerm){
							tempTerm.append("," );
						}
						
						tempTerm.append(rentReservTime.getRentReservStartTime() );
						tempTerm.append(";" );
						tempTerm.append(rentReservTime.getRentReservEndTime() );
						tempTerm.append(";" );
						tempTerm.append(rentReservTime.isRentIsNextday() );
						
						firsTerm = false;
					}
					rentalForm.setRentReservTimeSun(tempTerm.toString());
				}
				
				rentalService.updateRentalApplySet(rentalForm);
			}else{
				throw new AsaproNotFoundException("단계정보가 없어 수정하지 못했습니다.");
			}
			
			
			redirectAttributes.addFlashAttribute("updated", true);
			return "redirect:" + AsaproUtils.getAdminPath(currentSite, true) + "/rental/update.do?rentId=" + rentalForm.getRentId() + "&step=" + rentalForm.getStep();
		}
		
	}
	
	/**
	 * 대여/대관을 삭제한다.
	 * @param model
	 * @param rentIds
	 * @return 삭제결과
	 * @throws FileNotFoundException 
	 */
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/rental/delete.do", method=RequestMethod.POST)
	@ResponseBody
	public ServerMessage deleteRentalPost(Model model, @CurrentSite Site currentSite, @RequestParam(value="rentIds[]", required=true) Integer[] rentIds) throws FileNotFoundException{
		ServerMessage serverMessage = new ServerMessage();
		if( ArrayUtils.isEmpty(rentIds) ){
			serverMessage.setSuccess(false);
			serverMessage.setText("삭제할 항목이 없습니다.");
		} else {
			List<Rental> rentalList = new ArrayList<Rental>();
			Rental rental = null;
			for( Integer rentId : rentIds ){
				rental = new Rental();
				rental.setSitePrefix(currentSite.getSitePrefix());
				rental.setRentId(rentId);
				rentalList.add(rental);
			}
			int result = rentalService.deleteRental(rentalList);
			if(result > 0){
				serverMessage.setSuccess(true);
				serverMessage.setSuccessCnt(result);
			} else {
				serverMessage.setSuccess(false);
				serverMessage.setText("삭제하지 못하였습니다.[Server Error]이거나 신청정보가 이미 있습니다.");
			}
		}
		return serverMessage;
	}
	
	/**
	 * 대관/대여 정렬순서를 변경한다.
	 * @param model
	 * @param rentIds
	 * @return 변경 결과
	 */
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/rental/reOrder.do", method=RequestMethod.POST)
	@ResponseBody
	public ServerMessage updateRentOrderPost(Model model, @CurrentSite Site currentSite, @RequestParam(value="rentIds[]", required=true) Integer[] rentIds){
		
		ServerMessage serverMessage = new ServerMessage();
		if( ArrayUtils.isEmpty(rentIds) ){
			serverMessage.setSuccess(false);
			serverMessage.setText("변경할 항목이 없습니다.");
		} else {
			int order = 0;
			List<Rental> rentalList = new ArrayList<Rental>();
			Rental rental = null;
			for( Integer rentId : rentIds ){
				order ++;
				rental = new Rental();
				rental.setSitePrefix(currentSite.getSitePrefix());
				rental.setRentId(rentId);
				rental.setRentOrder(order);
				rentalList.add(rental);
			}
			int result = rentalService.updateRentalOrder(rentalList);
			if(result > 0){
				serverMessage.setSuccess(true);
				serverMessage.setSuccessCnt(result);
			} else {
				serverMessage.setSuccess(false);
				serverMessage.setText("변경하지 못하였습니다.[Server Error]");
			}
		}
		return serverMessage;
	}
	
	//=============================================================================================================================
	//==========================================  신청정보   ================================================================
	//=============================================================================================================================
	
	/**
	 * 신청정보 목록을 반환한다.
	 * @param model
	 * @param currentSite
	 * @param rentalReservationSearch
	 * @return 신청정보목록
	 */
	@PrivacyHistory(moduleType="대관/대여", history="신청정보 목록")
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/rental/reservation/list.do", method=RequestMethod.GET)
	public String rentalReservationListGet(Model model, @CurrentSite Site currentSite, @ModelAttribute("rentalReservationSearch") RentalReservationSearch rentalReservationSearch){
		rentalReservationSearch.setSitePrefix(currentSite.getSitePrefix());
		rentalReservationSearch.fixBrokenSvByDefaultCharsets();
		rentalReservationSearch.setPaging(true);
		
		List<RentalReservationInfo> list = rentalService.getRentalReservationList(rentalReservationSearch);
		int total = rentalService.getRentalReservationListTotal(rentalReservationSearch);
		
		rentalReservationSearch.setReservStatusPattern("RS1");//승인대기
		int approvalWaitingCnt = rentalService.getRentalReservationListTotal(rentalReservationSearch);	//
		rentalReservationSearch.setReservStatusPattern("RS3");//결제대기
		int paymentWaitingCnt = rentalService.getRentalReservationListTotal(rentalReservationSearch);	//
		rentalReservationSearch.setReservStatusPattern("RS4");//예약완료
		int reservCompleteCnt = rentalService.getRentalReservationListTotal(rentalReservationSearch);	//
		rentalReservationSearch.setReservStatusPattern("RS5");//예약취소
		int reservCancelCnt = rentalService.getRentalReservationListTotal(rentalReservationSearch);	//
		
		rentalReservationSearch.setReservStatusPattern("");
		Paging paging = new Paging(list, total, rentalReservationSearch);
		
		model.addAttribute("paging", paging);
		model.addAttribute("approvalWaitingCnt", approvalWaitingCnt);
		model.addAttribute("paymentWaitingCnt", paymentWaitingCnt);
		model.addAttribute("reservCompleteCnt", reservCompleteCnt);
		model.addAttribute("reservCancelCnt", reservCancelCnt);
		
		//코드목록
		this.loadCodes(model);
		
		//대관/대여 목록
		RentalSearch rentalSearch = new RentalSearch();
		rentalSearch.setSitePrefix(currentSite.getSitePrefix());
		rentalSearch.fixBrokenSvByDefaultCharsets();
		rentalSearch.setPaging(false);
		List<Rental> rentalList = rentalService.getRentalList(rentalSearch);
		model.addAttribute("rentalList", rentalList);
		
		return "asapro/admin/rental/reservList";
	}
	
	
	/**
	 * 신청정보를 등록 폼 뷰를 반환한다.
	 * @param model
	 * @param currentSite
	 * @param rentalReservationForm
	 * @return 추가폼뷰
	 * @throws ParseException 
	 */
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/rental/reservation/insert.do", method=RequestMethod.GET)
	public String rentalReservationInsertGet(Model model, @CurrentSite Site currentSite, @ModelAttribute("rentalReservationForm") RentalReservationInfo rentalReservationForm) throws ParseException{
		//대관/대여 정보
		Rental rental = new Rental();
		rental.setRentId(rentalReservationForm.getRental().getRentId());
		rental.setSitePrefix(currentSite.getSitePrefix());
		Rental rentalModel = rentalService.getRental(rental);
		
		//rental 셋
		rentalReservationForm.setRental(rentalModel);
		
		rentalReservationForm.setReservPrice(rentalModel.getRentCharge());
		//rentalReservationForm.setReservDiscountRate(rentalModel.getRentMemberDiscount());
		rentalReservationForm.setReservParking(rentalModel.getRentParking());
		rentalReservationForm.setSitePrefix(currentSite.getSitePrefix());
		
		//코드목록
		this.loadCodes(model);
		
		//카테고리1 목록
		//카테고리2 목록
		this.loadCate(model);
		
		//불가능 날짜 목록
		this.impossibleDateArray(model, rentalReservationForm, currentSite);
		
		//가능시간목록
		List<TimeCalculation> possibleTimeList = this.getPossibleTimeList(rentalReservationForm);
		model.addAttribute("possibleTimeList", possibleTimeList);
		
		model.addAttribute("formMode", "INSERT");
		return "asapro/admin/rental/reservForm";
	}
	
	/**
	 * 신청정보를 등록한다.
	 * @param model
	 * @param redirectAttributes
	 * @param currentSite
	 * @param rentalReservationForm
	 * @param bindingResult
	 * @return 추가결과
	 * @throws AsaproException
	 * @throws IOException
	 * @throws ParseException 
	 */
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/rental/reservation/insert.do", method=RequestMethod.POST)
	public String rentalReservationInsertPost(Model model, RedirectAttributes redirectAttributes, @CurrentAdmin AdminMember currentAdmin, @CurrentSite Site currentSite, @ModelAttribute("rentalReservationForm") RentalReservationInfo rentalReservationForm, BindingResult bindingResult) throws AsaproException, IOException, ParseException{

		rentalReservationForm.setSitePrefix(currentSite.getSitePrefix());
		
		//대관/대여 정보
		Rental rental = new Rental();
		rental.setRentId(rentalReservationForm.getRental().getRentId());
		rental.setSitePrefix(currentSite.getSitePrefix());
		Rental rentalModel = rentalService.getRental(rental);
		
		//rental 셋
		rentalReservationForm.setRental(rentalModel);
		rentalReservationForm.setAgreeChk(true);

		rentalReservationValidator.validate(rentalReservationForm, bindingResult, "INSERT");
		
		
		if( bindingResult.hasErrors() ){
			//관리자쪽에서 달력 선택시 가능 시간을 선택해서 진행 해야 하므로 날찌 지운다.
			//rentalReservationForm.setReservDate("");
			
			//코드목록
			this.loadCodes(model);
			//카테고리1 목록
			//카테고리2 목록
			this.loadCate(model);
			
			//불가능 날짜 목록
			this.impossibleDateArray(model, rentalReservationForm, currentSite);
			
			//가능시간목록
			List<TimeCalculation> possibleTimeList = this.getPossibleTimeList(rentalReservationForm);
			model.addAttribute("possibleTimeList", possibleTimeList);
			
			model.addAttribute("formMode", "INSERT");
			model.addAttribute("insertFail", true);
			return "asapro/admin/rental/reservForm";
		} else {
			rentalReservationForm.setReservRegDate(new Date());
			rentalReservationForm.setReservDiscountRate(rentalModel.getRentMemberDiscount());
			rentalReservationForm.setReservUserId(currentAdmin.getAdminId());
			
			String[] tempTerm = rentalReservationForm.getReservTime().split(",");
			int gap = 0;
			for (String term : tempTerm ) {
				String[] timeArray = term.split("~");

				if("time".equals(rental.getRentRentingMethod()) ){
					gap += AsaproUtils.calculTimeOfTwoTimes(timeArray[0], timeArray[1]);
				} else {
					gap += 1;	//package
				}
			}
			rentalReservationForm.setReservUsageTime(gap);
			
			if(rentalModel.isRentApprove()){// 승인기능 사용여부
				if(rentalModel.getRentCharge() > 0){
					rentalReservationForm.setReservStatus("RS10");	//승인대기 (상태코드)
				}else{
					rentalReservationForm.setReservStatus("RS10");	//승인대기 (상태코드)
				}
			} else{
				if(rentalModel.getRentCharge() > 0){//유료대관이면
					if(rentalReservationForm.getReservPaidType().startsWith("online_") ){	//온라인 결제인 경우
						rentalReservationForm.setReservStatus("RS30");	//결제대기(온라인)
					} else if(rentalReservationForm.getReservPaidType().startsWith("local_") ) {	//현장결제인 경우 
						rentalReservationForm.setReservStatus("RS31");	//결제대기(현장)
					} else {
						rentalReservationForm.setReservStatus("RS40");	//예약완료
					}
				}else{//무료대관이면
					rentalReservationForm.setReservStatus("RS40");	//예약완료
				}
				
			}
			
			//if(StringUtils.isNotBlank(rentalReservationForm.getReservTel1()) && StringUtils.isNotBlank(rentalReservationForm.getReservTel2()) && StringUtils.isNotBlank(rentalReservationForm.getReservTel3()) ) {
			//}
			if(StringUtils.isNotBlank(rentalReservationForm.getReservTel1()) && StringUtils.isNotBlank(rentalReservationForm.getReservTel2()) && StringUtils.isNotBlank(rentalReservationForm.getReservTel3()) ){
				rentalReservationForm.setReservTel(rentalReservationForm.getReservTel1() + "-" + rentalReservationForm.getReservTel2() + "-" +rentalReservationForm.getReservTel3());
			}
			rentalReservationForm.setReservHp(rentalReservationForm.getReservHp1() + "-" + rentalReservationForm.getReservHp2() + "-" +rentalReservationForm.getReservHp3());
			//기본주차대수
			rentalReservationForm.setReservParking(rentalModel.getRentParking());
			
			Date startDate = AsaproUtils.makeCalculDate(rentalModel.getRentReservSdateAfter());	//예약가능 날짜 (시작)
			Date endDate = AsaproUtils.makeCalculDate(rentalModel.getRentReservEdateAfter());	//예약가능 날짜 (종료)
			
			//============== 예약가능 날짜, 시간 서버단 검증  ============
			
			Boolean possibleChk = true;	//가능여부
			String cause = "";	//불가능이유
			
			String applySdate = AsaproUtils.getFormattedDate(startDate,"yyyy-MM-dd");	//예약가능 날짜 (시작)
			String applyEdate = AsaproUtils.getFormattedDate(endDate,"yyyy-MM-dd");	//예약가능 날짜 (종료)

			//1.대관 게시상태 체크
			if(!rentalModel.isRentUse() ){
				possibleChk = false;
				cause = "notUse";
			}
			//2.대관 기간이 상시여부 체크
			else if("period".equals(rentalModel.getRentPossiblelDayType()) ){	//기간
				//3.대관 기간이 기간이면 기간내 일자인지 확인
				if(rentalReservationForm.getReservDate().compareTo(rentalModel.getRentPeriodSdate()) < 0 || //대관/대여 기간 이전
						rentalReservationForm.getReservDate().compareTo(rentalModel.getRentPeriodEdate()) > 0 ){	//대관/대여기간 이후
					possibleChk = false;
					cause = "notOpen";
				}
			}
			//4.예약가능일 내의 일자인지 확인
			if(rentalReservationForm.getReservDate().compareTo(applySdate) < 0 || 	//예약가능일 보다 이전
						rentalReservationForm.getReservDate().compareTo(applyEdate) > 0 ){	//예약가능일 보다 이후
				possibleChk = false;
				cause = "notApplyTerm";
			}
			//5.가능요일인지 확인
			else if(!this.isPossibleDayOfWeek(rentalReservationForm) ){
				possibleChk = false;
				cause = "notApplyDay";
			}
			//6.예약불가일 목록에 있는지 확인
			else if(this.isImpossibleDate(rentalReservationForm) ){
				possibleChk = false;
				cause = "impossibleDate";
			}
			//7.이미 예약된 날짜, 또는 시간인지 확인, 닫힘처리 시간 확인
			else if(!this.isPossibleDateTime(rentalReservationForm) ){
				possibleChk = false;
				cause = "overlap";			
			}
			
			if(possibleChk == false){
				model.addAttribute(cause, true);	//예약실패 원인
				
				//관리자쪽에서 달력 선택시 가능 시간을 선택해서 진행 해야 하므로 날찌 지운다.
				//rentalReservationForm.setReservDate("");
				
				//코드목록
				this.loadCodes(model);
				//카테고리1 목록
				//카테고리2 목록
				this.loadCate(model);
				
				//불가능 날짜 목록
				this.impossibleDateArray(model, rentalReservationForm, currentSite);
				
				//가능시간목록
				List<TimeCalculation> possibleTimeList = this.getPossibleTimeList(rentalReservationForm);
				model.addAttribute("possibleTimeList", possibleTimeList);
				
				model.addAttribute("formMode", "INSERT");
				model.addAttribute("insertFail", true);
				return "asapro/admin/rental/reservForm";
			}
			//============== //예약가능 날짜, 시간 서버단 검증  ============
			
			
			
			//========== 첨부 파일 처리 =====================
			
			//첨부가능한 확장자
			String uploadWhiteList = "";
			
			FileInfoUploadResult fileInfoUploadResult = new FileInfoUploadResult();
			
			//이미지 처리
			if( rentalReservationForm.getReservAppendingFile() != null && rentalReservationForm.getReservAppendingFile().getSize() > 0 ){

				uploadWhiteList = Constant.UPLOAD_BUSINESS_REGISTRATION_WHITE_LIST; 
				String webRoot = AsaproUtils.getWebRoot(request);
				
				//첨부된 파일 
				FileInfo fileInfo = new FileInfo();
				fileInfo.setSitePrefix(rentalReservationForm.getSitePrefix());
				//모듈1차pk
				fileInfo.setFileModule("rental");
				fileInfo.setFileModuleId("reservation");
				//모듈2차pk
				fileInfo.setFileModuleSub(rentalModel.getRentDiv());
				fileInfo.setFileModuleSubId(rentalReservationForm.getRental().getRentId().toString());
				//멤버
				fileInfo.setMemberId("");
				//첨부유형
				fileInfo.setFileAttachmentType("appending");
				//대체텍스트
				fileInfo.setFileAltText( rentalReservationForm.getReservTitle() );
				
				//썸네일 파일 없이 업로드.
				fileInfoService.saveFile(rentalReservationForm.getReservAppendingFile(), webRoot, fileInfo, (Constant.MEGA * 3), false, 100, 100, true, uploadWhiteList);
				if( !fileInfo.isFileUploadSuccess() ){
					fileInfoUploadResult.addErrorInfo(fileInfo);
				}
				
				rentalReservationForm.setReservAppendingFileInfo(fileInfo);
				
			}

			//업로드 에러 있으면 등록화면으로 전환
			if(fileInfoUploadResult.hasErrors() ){
				
				//코드목록
				this.loadCodes(model);
				//카테고리1 목록
				//카테고리2 목록
				this.loadCate(model);
				
				//불가능 날짜 목록
				this.impossibleDateArray(model, rentalReservationForm, currentSite);
				
				//가능시간목록
				List<TimeCalculation> possibleTimeList = this.getPossibleTimeList(rentalReservationForm);
				model.addAttribute("possibleTimeList", possibleTimeList);
				
				model.addAttribute("fileInfoUploadResult", fileInfoUploadResult);
				model.addAttribute("formMode", "INSERT");
				model.addAttribute("insertFail", true);
				return "asapro/admin/rental/reservForm";
			}
			//========== 첨부 파일 처리 =====================
			
			rentalService.insertRentalReservation(rentalReservationForm);
			
			/*
			//========= 시작 : 메일전송 ==========
			JavaMailSender javaMailSender = AsaproMailUtils.getJavaMailSender();
			MimeMessage mimeMessage = javaMailSender.createMimeMessage();
			MimeMessageHelper mimeMessageHelper = null;
			try {
				mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
				try {
					mimeMessageHelper.setFrom("webmaster@kobaco.co.kr", currentSite.getSiteName());
				} catch (UnsupportedEncodingException e) {
					LOGGER.error("[UnsupportedEncodingException] Try/Catch... : "+ e.getMessage());
				}
				mimeMessageHelper.setTo(rentalReservationForm.getReservEmail());
				mimeMessageHelper.setSubject(currentSite.getSiteName() + " 시설이용예약 신청이 접수 되었습니다.");
				
				
				String html = null;
				
				StringBuilder sb = new StringBuilder(500);
				sb.append("<p>");
				sb.append("<h2>");
				sb.append(currentSite.getSiteName());
				sb.append("</h2>");
				sb.append("</p>");
				sb.append("<p style=\"font-weight: bold;\">");
				sb.append("안녕하세요. ");sb.append(rentalReservationForm.getReservName());sb.append("(");sb.append(rentalReservationForm.getReservEmail());sb.append(")님.");
				sb.append("</p>");
				sb.append("<p>");
				sb.append(currentSite.getSiteName());
				sb.append(" [");
				sb.append(rentalReservationForm.getRental().getRentTitle());
				sb.append("]" );
				sb.append("<br />");
				sb.append("예약일 : ");
				sb.append(rentalReservationForm.getReservDate());
				sb.append("<br />");
				sb.append("시설 이용 예약 신청이 접수되었습니다.");
				sb.append("<br />");
				sb.append("문의 : 인프라 관리파트 02)2144-0201, 02)2144-0123");
				sb.append("<br />");
				sb.append("입금 계좌(현금결제 시) : 우리은행 1005-903-066128 한국방송광고진흥공사 ");
				sb.append("</p>");
				
				html = sb.toString();
				mimeMessageHelper.setText(html, true);		
				if(!AsaproUtils.isDevPath(request) && !AsaproUtils.isAsadalNet(request) ){
					javaMailSender.send(mimeMessage);
				};
			} catch (MessagingException e) {
				LOGGER.error("[MessagingException] Try/Catch... : "+ e.getMessage());
			}
			//========= 끝 : 메일전송 ==========
			*/
			
			
			
			
			redirectAttributes.addFlashAttribute("inserted", true);
			return "redirect:" + AsaproUtils.getAdminPath(currentSite, true) + "/rental/reservation/list.do?reservRentId=" + rentalReservationForm.getRental().getRentId();
		}
		
	}
	
	/**
	 * 신청정보를 수정 폼 뷰를 반환한다.
	 * @param model
	 * @param currentSite
	 * @param rentalReservationForm
	 * @return 수정폼뷰
	 * @throws ParseException 
	 */
	@PrivacyHistory(moduleType="대관/대여", history="신청정보 상세")
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/rental/reservation/update.do", method=RequestMethod.GET)
	public String rentalReservationUpdateGet(Model model, @CurrentSite Site currentSite, @ModelAttribute("rentalReservationForm") RentalReservationInfo rentalReservationForm) throws ParseException{
		rentalReservationForm.setSitePrefix(currentSite.getSitePrefix());
		RentalReservationInfo rentalReservationInfoModel = rentalService.getRentalReservation(rentalReservationForm);
		rentalReservationInfoModel.setSitePrefix(currentSite.getSitePrefix());
		
		//tel 1,2,3으로 분리 셋
		if(StringUtils.isNotBlank(rentalReservationInfoModel.getReservTel()) ){
			String[] tempTelArray = rentalReservationInfoModel.getReservTel().split("-");
			rentalReservationInfoModel.setReservTel1(tempTelArray[0]);
			rentalReservationInfoModel.setReservTel2(tempTelArray[1]);
			rentalReservationInfoModel.setReservTel3(tempTelArray[2]);
		}
		//hp 1,2,3으로 분리 셋
		if(StringUtils.isNotBlank(rentalReservationInfoModel.getReservHp()) ){
			String[] tempHpArray = rentalReservationInfoModel.getReservHp().split("-");
			rentalReservationInfoModel.setReservHp1(tempHpArray[0]);
			rentalReservationInfoModel.setReservHp2(tempHpArray[1]);
			rentalReservationInfoModel.setReservHp3(tempHpArray[2]);
		}
		
		//예약시간 배열로 변환
		if(StringUtils.isNotBlank(rentalReservationInfoModel.getReservTime()) ){
			String[] reservTimeArray = rentalReservationInfoModel.getReservTime().split(",");
			model.addAttribute("reservTimeArray", reservTimeArray);
		}

		//코드목록
		this.loadCodes(model);
		//카테고리1 목록
		//카테고리2 목록
		this.loadCate(model);
		
		//불가능 날짜 목록
		this.impossibleDateArray(model, rentalReservationInfoModel, currentSite);
		
		//가능시간목록
		List<TimeCalculation> possibleTimeList = this.getPossibleTimeList(rentalReservationInfoModel);
		model.addAttribute("possibleTimeList", possibleTimeList);
		
		
		model.addAttribute("formMode", "UPDATE");
		model.addAttribute("rentalReservationForm", rentalReservationInfoModel);
		return "asapro/admin/rental/reservForm";
	}
	
	/**
	 * 신청정보를 수정한다.
	 * @param model
	 * @param redirectAttributes
	 * @param currentSite
	 * @param rentalReservationForm
	 * @param bindingResult
	 * @return 수정결과
	 * @throws AsaproException
	 * @throws IOException
	 * @throws ParseException 
	 */
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/rental/reservation/update.do", method=RequestMethod.POST)
	public String rentalReservationUpdatePost(Model model, RedirectAttributes redirectAttributes, @CurrentSite Site currentSite, @ModelAttribute("rentalReservationForm") RentalReservationInfo rentalReservationForm, BindingResult bindingResult) throws AsaproException, IOException, ParseException{

		rentalReservationForm.setSitePrefix(currentSite.getSitePrefix());
		RentalReservationInfo rentalReservationInfoModel = rentalService.getRentalReservation(rentalReservationForm);
		rentalReservationInfoModel.setSitePrefix(currentSite.getSitePrefix());
		rentalReservationForm.setReservAppendingFileInfo(rentalReservationInfoModel.getReservAppendingFileInfo());
		Rental rentalModel = rentalReservationInfoModel.getRental();
		rentalReservationForm.setRental(rentalModel);
		
		rentalReservationValidator.validate(rentalReservationForm, bindingResult, "UPDATE");
		
		if( bindingResult.hasErrors() ){
			//관리자쪽에서 달력 선택시 가능 시간을 선택해서 진행 해야 하므로 날찌 지운다.
			//rentalReservationForm.setReservDate("");
			
			//예약시간 배열로 변환
			if(StringUtils.isNotBlank(rentalReservationInfoModel.getReservTime()) ){
				String[] reservTimeArray = rentalReservationInfoModel.getReservTime().split(",");
				model.addAttribute("reservTimeArray", reservTimeArray);
			}
			
			//코드목록
			this.loadCodes(model);
			//카테고리1 목록
			//카테고리2 목록
			this.loadCate(model);
			
			//불가능 날짜 목록
			this.impossibleDateArray(model, rentalReservationInfoModel, currentSite);
			
			//가능시간목록
			List<TimeCalculation> possibleTimeList = this.getPossibleTimeList(rentalReservationInfoModel);
			model.addAttribute("possibleTimeList", possibleTimeList);
			
			model.addAttribute("formMode", "UPDATE");
			model.addAttribute("updateFail", true);
			return "asapro/admin/rental/reservForm";
		} else {
			
			if(StringUtils.isNotBlank(rentalReservationForm.getReservTel1()) && StringUtils.isNotBlank(rentalReservationForm.getReservTel2()) && StringUtils.isNotBlank(rentalReservationForm.getReservTel3()) ){
				rentalReservationForm.setReservTel(rentalReservationForm.getReservTel1() + "-" + rentalReservationForm.getReservTel2() + "-" +rentalReservationForm.getReservTel3());
			}
			rentalReservationForm.setReservHp(rentalReservationForm.getReservHp1() + "-" + rentalReservationForm.getReservHp2() + "-" +rentalReservationForm.getReservHp3());
			
			Date startDate = AsaproUtils.makeCalculDate(rentalModel.getRentReservSdateAfter());	//예약가능 날짜 (시작)
			Date endDate = AsaproUtils.makeCalculDate(rentalModel.getRentReservEdateAfter());	//예약가능 날짜 (종료)
			
			//============== 예약가능 날짜, 시간 서버단 검증  ============
			
			Boolean possibleChk = true;	//가능여부
			String cause = "";	//불가능이유
			
			String applySdate = AsaproUtils.getFormattedDate(startDate,"yyyy-MM-dd");	//예약가능 날짜 (시작)
			String applyEdate = AsaproUtils.getFormattedDate(endDate,"yyyy-MM-dd");	//예약가능 날짜 (종료)
			
			//0.수정할 수 있는 단계의 상태인지 체크 - 승인대기, 결제대기 상태에서만 수정 가능
			int status = Integer.parseInt(rentalReservationForm.getReservStatus().substring(2));
			if(status >= 40){ // RS40(예약완료) 이전 상태 체크
				possibleChk = false;
				cause = "impossibleStatus";
			}

			//1.대관 게시상태 체크
			else if(!rentalModel.isRentUse() ){
				possibleChk = false;
				cause = "notUse";
			}
			//2.대관 기간이 상시여부 체크
			else if("period".equals(rentalModel.getRentPossiblelDayType()) ){	//기간
				//3.대관 기간이 기간이면 기간내 일자인지 확인
				if(rentalReservationForm.getReservDate().compareTo(rentalModel.getRentPeriodSdate()) < 0 || //대관/대여 기간 이전
						rentalReservationForm.getReservDate().compareTo(rentalModel.getRentPeriodEdate()) > 0 ){	//대관/대여기간 이후
					possibleChk = false;
					cause = "notOpen";
				}
			}
			//4.예약가능일 내의 일자인지 확인
			if(rentalReservationForm.getReservDate().compareTo(applySdate) < 0 || 	//예약가능일 보다 이전
						rentalReservationForm.getReservDate().compareTo(applyEdate) > 0 ){	//예약가능일 보다 이후
				possibleChk = false;
				cause = "notApplyTerm";
			}
			//5.가능요일인지 확인
			else if(!this.isPossibleDayOfWeek(rentalReservationForm) ){
				possibleChk = false;
				cause = "notApplyDay";
			}
			//6.예약불가일 목록에 있는지 확인
			else if(this.isImpossibleDate(rentalReservationForm) ){
				possibleChk = false;
				cause = "impossibleDate";
			}
			//7.이미 예약된 날짜, 또는 시간인지 확인
			else if(!this.isPossibleDateTime(rentalReservationForm) ){
				possibleChk = false;
				cause = "overlap";			
			}
			
			if(possibleChk == false){
				model.addAttribute(cause, true);	//예약실패 원인
				
				//관리자쪽에서 달력 선택시 가능 시간을 선택해서 진행 해야 하므로 날찌 지운다.
				//rentalReservationForm.setReservDate("");
				
				//코드목록
				this.loadCodes(model);
				//카테고리1 목록
				//카테고리2 목록
				this.loadCate(model);
				
				//불가능 날짜 목록
				this.impossibleDateArray(model, rentalReservationInfoModel, currentSite);
				
				//가능시간목록
				List<TimeCalculation> possibleTimeList = this.getPossibleTimeList(rentalReservationInfoModel);
				model.addAttribute("possibleTimeList", possibleTimeList);
				
				model.addAttribute("formMode", "UPDATE");
				model.addAttribute("updateFail", true);
				return "asapro/admin/rental/reservForm";
			}
			//============== //예약가능 날짜, 시간 서버단 검증  ============
			
			//========== 첨부 파일 처리 =====================
			
			//첨부가능한 확장자
			String uploadWhiteList = "";
			
			FileInfoUploadResult fileInfoUploadResult = new FileInfoUploadResult();
			
			//새 첨부파일 처리
			if( rentalReservationForm.getReservAppendingFile() != null && rentalReservationForm.getReservAppendingFile().getSize() > 0 ){
				//기존첨부파일 삭제
				if(rentalReservationForm.getReservAppendingFileInfo() != null && rentalReservationForm.getReservAppendingFileInfo().getFileId() > 0){
					FileInfo fileInfoImage = rentalReservationForm.getReservAppendingFileInfo();
					fileInfoImage.setSitePrefix(currentSite.getSitePrefix());
					fileInfoService.deleteFileInfo(fileInfoImage);
				}

				uploadWhiteList = Constant.UPLOAD_BUSINESS_REGISTRATION_WHITE_LIST; 
				String webRoot = AsaproUtils.getWebRoot(request);
				
				//첨부된 파일 
				FileInfo fileInfo = new FileInfo();
				fileInfo.setSitePrefix(rentalReservationForm.getSitePrefix());
				//모듈1차pk
				fileInfo.setFileModule("rental");
				fileInfo.setFileModuleId("reservation");
				//모듈2차pk
				fileInfo.setFileModuleSub(rentalModel.getRentDiv());
				fileInfo.setFileModuleSubId(rentalReservationForm.getRental().getRentId().toString());
				//멤버
				fileInfo.setMemberId("");
				//첨부유형
				fileInfo.setFileAttachmentType("appending");
				//대체텍스트
				fileInfo.setFileAltText( rentalReservationForm.getReservTitle() );
				
				//썸네일 파일 없이 업로드.
				fileInfoService.saveFile(rentalReservationForm.getReservAppendingFile(), webRoot, fileInfo, (Constant.MEGA * 3), false, 100, 100, true, uploadWhiteList);
				if( !fileInfo.isFileUploadSuccess() ){
					fileInfoUploadResult.addErrorInfo(fileInfo);
				}
				
				rentalReservationForm.setReservAppendingFileInfo(fileInfo);
				
			}

			//업로드 에러 있으면 등록화면으로 전환
			if(fileInfoUploadResult.hasErrors() ){
				
				//예약시간 배열로 변환
				if(StringUtils.isNotBlank(rentalReservationInfoModel.getReservTime()) ){
					String[] reservTimeArray = rentalReservationInfoModel.getReservTime().split(",");
					model.addAttribute("reservTimeArray", reservTimeArray);
				}
				
				//코드목록
				this.loadCodes(model);
				//카테고리1 목록
				//카테고리2 목록
				this.loadCate(model);
				
				//불가능 날짜 목록
				this.impossibleDateArray(model, rentalReservationInfoModel, currentSite);
				
				//가능시간목록
				List<TimeCalculation> possibleTimeList = this.getPossibleTimeList(rentalReservationInfoModel);
				model.addAttribute("possibleTimeList", possibleTimeList);
				
				model.addAttribute("fileInfoUploadResult", fileInfoUploadResult);
				model.addAttribute("formMode", "UPDATE");
				model.addAttribute("updateFail", true);
				return "asapro/admin/rental/reservForm";
			}
			//========== 첨부 파일 처리 =====================
			
			int result = rentalService.updateRentalReservation(rentalReservationForm);
			
			//수정성공,  상태가 결제대기 상태(온,오프), 수정시의 결제방법에 따라 결제대기코드 변경
			if(result > 0 && rentalReservationForm.getReservStatus().startsWith("RS3") ){
				if(rentalReservationForm.getReservPaidType().startsWith("online") ){
					rentalReservationForm.setReservStatus("RS30");//결제대기(온라인)
				}else{//local
					rentalReservationForm.setReservStatus("RS31");//결제대기(현장)
				}
				
				rentalService.updateReservStatus(rentalReservationForm);
			}
			
			redirectAttributes.addFlashAttribute("updated", true);
			return "redirect:" + AsaproUtils.getAdminPath(currentSite, true) + "/rental/reservation/update.do?reservId=" + rentalReservationForm.getReservId();
		}
		
	}
	
	/**
	 * 신청정보를 삭제한다.
	 * @param model
	 * @param currentSite
	 * @param reservIds
	 * @return 삭제결과
	 */
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/rental/reservation/delete.do", method=RequestMethod.POST)
	@ResponseBody
	public ServerMessage rentalReservationDeletePost(Model model, @CurrentSite Site currentSite, @RequestParam(value="reservIds[]", required=true) String[] reservIds) {
		ServerMessage serverMessage = new ServerMessage();
		if( ArrayUtils.isEmpty(reservIds) ){
			serverMessage.setSuccess(false);
			serverMessage.setText("삭제할 항목이 없습니다.");
		} else {
			List<RentalReservationInfo> rentalReservationInfoList = new ArrayList<RentalReservationInfo>();
			RentalReservationInfo rentalReservationInfo = null;
			for( String reservId : reservIds ){
				rentalReservationInfo = new RentalReservationInfo();
				rentalReservationInfo.setSitePrefix(currentSite.getSitePrefix());
				rentalReservationInfo.setReservId(reservId);
				rentalReservationInfoList.add(rentalReservationInfo);
			}
			
			int result = rentalService.deleteRentalReservationInfo(rentalReservationInfoList);
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
	 * 예약신청을 승인 처리한다.
	 * @param model
	 * @param currentSite
	 * @param rentalReservationForm
	 * @return 승인처리결과
	 * @throws ParseException
	 */
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/rental/reservation/admission.do", method=RequestMethod.POST)
	@ResponseBody
	public ServerMessage admissionRentalReservationPost(Model model, @CurrentSite Site currentSite, @ModelAttribute("rentalReservationForm") RentalReservationInfo rentalReservationForm  ) throws ParseException{
		ServerMessage serverMessage = new ServerMessage();
		if( StringUtils.isBlank(rentalReservationForm.getReservId())){
			serverMessage.setSuccess(false);
			serverMessage.setText("승인처리할 항목이 없습니다.");
		} else {
			rentalReservationForm.setSitePrefix(currentSite.getSitePrefix());
			RentalReservationInfo rentalReservationInfoModel = rentalService.getRentalReservation(rentalReservationForm);
			
			if(rentalReservationInfoModel.getRental().getRentCharge() > 0){//유료대관이면
				if(rentalReservationInfoModel.getReservPaidType().startsWith("online_") ){	//온라인 결제인 경우
					rentalReservationForm.setReservStatus("RS30");//결제대기(온라인)
				}else if(rentalReservationInfoModel.getReservPaidType().startsWith("local_") ){	//현장결제인 경우 
					rentalReservationForm.setReservStatus("RS31");//결제대기(현장)
				}else{
					rentalReservationForm.setReservStatus("RS40");	//예약완료
				}
			}else{//무료이면
				rentalReservationForm.setReservStatus("RS40");	//예약완료
			}
			
			rentalReservationForm.setReservApprovDate(AsaproUtils.getFormattedDate(new Date()) );
			int result = rentalService.updateReservStatus(rentalReservationForm);
			
			//승인처리 완료시 알림메일 발송
			if(result > 0){
				
				/*//========= 시작 : 메일전송 ==========
				JavaMailSender javaMailSender = AsaproMailUtils.getJavaMailSender();
				MimeMessage mimeMessage = javaMailSender.createMimeMessage();
				MimeMessageHelper mimeMessageHelper = null;
				try {
					mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
					try {
						mimeMessageHelper.setFrom("webmaster@kobaco.co.kr", currentSite.getSiteName());
					} catch (UnsupportedEncodingException e) {
						LOGGER.error("[UnsupportedEncodingException] Try/Catch... : "+ e.getMessage());
					}
					mimeMessageHelper.setTo(rentalReservationInfoModel.getReservEmail());
					mimeMessageHelper.setSubject("한국광고문화회관 시설이용예약이 완료되었습니다.");
					
					
					String html = null;
					
					//신청서를 html로 불러오기
					html = reservationMailFormGet(rentalReservationInfoModel);
					
					mimeMessageHelper.setText(html, true);	
					if(!AsaproUtils.isDevPath(request) && !AsaproUtils.isAsadalNet(request) ){
						javaMailSender.send(mimeMessage);
					};
				} catch (MessagingException e) {
					LOGGER.error("[MessagingException] Try/Catch... : "+ e.getMessage());
				}
				//========= 끝 : 메일전송 ==========
*/				
				serverMessage.setSuccess(true);
				serverMessage.setSuccessCnt(result);
				serverMessage.setText("승인처리를 완료하고 알림문자를 발송했습니다.");
			}else{
				serverMessage.setSuccess(false);
				serverMessage.setText("승인처리를 완료하지 못했습니다.[Server Error]");
			}
			
		}
		
		return serverMessage;
	}
	
	/**
	 * 예약신청을 결제 처리한다.
	 * @param model
	 * @param currentSite
	 * @param rentalReservationForm
	 * @return 결제처리결과
	 * @throws ParseException
	 */
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/rental/reservation/payment.do", method=RequestMethod.POST)
	@ResponseBody
	public ServerMessage paymentRentalReservationPost(Model model, @CurrentSite Site currentSite, @ModelAttribute("rentalReservationForm") RentalReservationInfo rentalReservationForm  ) throws ParseException{
		ServerMessage serverMessage = new ServerMessage();
		if( StringUtils.isBlank(rentalReservationForm.getReservId())){
			serverMessage.setSuccess(false);
			serverMessage.setText("결제처리할 항목이 없습니다.");
		} else {
			rentalReservationForm.setSitePrefix(currentSite.getSitePrefix());
			//RentalReservationInfo rentalReservationInfoModel = rentalService.getRentalReservation(rentalReservationForm);
			
			rentalReservationForm.setReservStatus("RS40");	//예약완료
			rentalReservationForm.setReservPaymentDate(AsaproUtils.getFormattedDate(new Date()) );
			int result = rentalService.updateReservStatus(rentalReservationForm);
			
			//결제처리 완료시 알림메일 발송
			if(result > 0){
				
				/*//========= 시작 : 메일전송 ==========
				JavaMailSender javaMailSender = AsaproMailUtils.getJavaMailSender();
				MimeMessage mimeMessage = javaMailSender.createMimeMessage();
				MimeMessageHelper mimeMessageHelper = null;
				try {
					mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
					try {
						mimeMessageHelper.setFrom("webmaster@kobaco.co.kr", currentSite.getSiteName());
					} catch (UnsupportedEncodingException e) {
						LOGGER.error("[UnsupportedEncodingException] Try/Catch... : "+ e.getMessage());
					}
					mimeMessageHelper.setTo(rentalReservationInfoModel.getReservEmail());
					mimeMessageHelper.setSubject("한국광고문화회관 시설이용예약이 완료되었습니다.");
					
					
					String html = null;
					
					//신청서를 html로 불러오기
					html = reservationMailFormGet(rentalReservationInfoModel);
					
					mimeMessageHelper.setText(html, true);	
					if(!AsaproUtils.isDevPath(request) && !AsaproUtils.isAsadalNet(request) ){
						javaMailSender.send(mimeMessage);
					};
				} catch (MessagingException e) {
					LOGGER.error("[MessagingException] Try/Catch... : "+ e.getMessage());
				}
				//========= 끝 : 메일전송 ==========
*/				
				serverMessage.setSuccess(true);
				serverMessage.setSuccessCnt(result);
				serverMessage.setText("결제처리를 완료했습니다.");
			}else{
				serverMessage.setSuccess(false);
				serverMessage.setText("결제처리를 완료하지 못했습니다.[Server Error]");
			}
			
		}
		
		return serverMessage;
	}
	
	/**
	 * 예약신청을 결제취소 처리한다.
	 * @param model
	 * @param currentSite
	 * @param rentalReservationForm
	 * @return 결제취소 처리결과
	 * @throws ParseException
	 */
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/rental/reservation/payCancel.do", method=RequestMethod.POST)
	@ResponseBody
	public ServerMessage payCancelRentalReservationPost(Model model, @CurrentSite Site currentSite, @ModelAttribute("rentalReservationForm") RentalReservationInfo rentalReservationForm  ) throws ParseException{
		ServerMessage serverMessage = new ServerMessage();
		if( StringUtils.isBlank(rentalReservationForm.getReservId())){
			serverMessage.setSuccess(false);
			serverMessage.setText("결제취소처리할 항목이 없습니다.");
		} else {
			rentalReservationForm.setSitePrefix(currentSite.getSitePrefix());
			//RentalReservationInfo rentalReservationInfoModel = rentalService.getRentalReservation(rentalReservationForm);
			
			rentalReservationForm.setReservStatus("RS52");	//예약취소(관리자)
			rentalReservationForm.setReservPayCancelDate(AsaproUtils.getFormattedDate(new Date()) );
			int result = rentalService.updateReservStatus(rentalReservationForm);
			
			//결제치소처리 완료시 알림메일 발송
			if(result > 0){
				
				/*//========= 시작 : 메일전송 ==========
				JavaMailSender javaMailSender = AsaproMailUtils.getJavaMailSender();
				MimeMessage mimeMessage = javaMailSender.createMimeMessage();
				MimeMessageHelper mimeMessageHelper = null;
				try {
					mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
					try {
						mimeMessageHelper.setFrom("webmaster@kobaco.co.kr", currentSite.getSiteName());
					} catch (UnsupportedEncodingException e) {
						LOGGER.error("[UnsupportedEncodingException] Try/Catch... : "+ e.getMessage());
					}
					mimeMessageHelper.setTo(rentalReservationInfoModel.getReservEmail());
					mimeMessageHelper.setSubject("한국광고문화회관 시설이용예약이 완료되었습니다.");
					
					
					String html = null;
					
					//신청서를 html로 불러오기
					html = reservationMailFormGet(rentalReservationInfoModel);
					
					mimeMessageHelper.setText(html, true);	
					if(!AsaproUtils.isDevPath(request) && !AsaproUtils.isAsadalNet(request) ){
						javaMailSender.send(mimeMessage);
					};
				} catch (MessagingException e) {
					LOGGER.error("[MessagingException] Try/Catch... : "+ e.getMessage());
				}
				//========= 끝 : 메일전송 ==========
				 */				
				serverMessage.setSuccess(true);
				serverMessage.setSuccessCnt(result);
				serverMessage.setText("결제취소처리를 완료했습니다.");
			}else{
				serverMessage.setSuccess(false);
				serverMessage.setText("결제취소처리를 완료하지 못했습니다.[Server Error]");
			}
			
		}
		
		return serverMessage;
	}
	
	/**
	 * 대관 신청정보 신청서를 html로 불러온다.
	 * @param rentalReservationInfo
	 * @return html text
	 */
	public String reservationMailFormGet(RentalReservationInfo rentalReservationInfo){
		//신청서를 html로 불러온다.
		String urlPath = AsaproUtils.getSchemeDomainPort(request) + AsaproUtils.getAdminPath((Site)request.getAttribute("currentSite")) + "/rental/reservation/printPreview.do?emailTemp=true&reservId="+rentalReservationInfo.getReservId();
        String pageContents = "";
        StringBuilder contents = new StringBuilder();
 
        try{
 
            URL url = new URL(urlPath);
            URLConnection con = (URLConnection)url.openConnection();
            InputStreamReader reader = new InputStreamReader (con.getInputStream(), "utf-8");
 
            BufferedReader buff = new BufferedReader(reader);
            
           // Boolean appendChk = false;
            while((pageContents = buff.readLine())!=null){
                //System.out.println(pageContents);  
            	/*if(StringUtil.contains(pageContents, "htmlcall_start") ){
            		appendChk = true;
            	}else if(StringUtil.contains(pageContents, "htmlcall_end") ){
            		appendChk = false;
            	}*/
            	//if(appendChk){
            		contents.append(pageContents);
            		contents.append("\r\n");
            	//}
            }
            buff.close();
 
           // System.out.println(contents.toString());
 
        }catch(IOException e){
        	LOGGER.error("[IOException] Try/Catch... : "+ e.getMessage());
        }
        
		return contents.toString();
	}
	
	/**
	 * 예약신청 취소 처리한다.
	 * @param model
	 * @param currentSite
	 * @param rentalReservationForm
	 * @return 취소처리결과
	 * @throws ParseException
	 */
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/rental/reservation/cancel.do", method=RequestMethod.POST)
	@ResponseBody
	public ServerMessage cancelRentalReservationPost(Model model, @CurrentSite Site currentSite, @ModelAttribute("rentalReservationForm") RentalReservationInfo rentalReservationForm  ) throws ParseException{
		ServerMessage serverMessage = new ServerMessage();
		if( StringUtils.isBlank(rentalReservationForm.getReservId())){
			serverMessage.setSuccess(false);
			serverMessage.setText("취소처리할 항목이 없습니다.");
		} else {
			rentalReservationForm.setSitePrefix(currentSite.getSitePrefix());
			RentalReservationInfo rentalReservationInfoModel = rentalService.getRentalReservation(rentalReservationForm);
			
			rentalReservationForm.setReservStatus("RS52");//예약취소(관리자)
			rentalReservationForm.setReservCancelDate(AsaproUtils.getFormattedDate(new Date()) );
			int result = rentalService.updateReservStatus(rentalReservationForm);
			
			//취소처리 완료시 알림메일 발송
			if(result > 0){
				
				//========= 시작 : 메일전송 ==========
				JavaMailSender javaMailSender = AsaproMailUtils.getJavaMailSender();
				MimeMessage mimeMessage = javaMailSender.createMimeMessage();
				MimeMessageHelper mimeMessageHelper = null;
				try {
					mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
					try {
						mimeMessageHelper.setFrom("webmaster@kobaco.co.kr", currentSite.getSiteName());
					} catch (UnsupportedEncodingException e) {
						LOGGER.error("[UnsupportedEncodingException] Try/Catch... : "+ e.getMessage());
					}
					mimeMessageHelper.setTo(rentalReservationInfoModel.getReservEmail());
					mimeMessageHelper.setSubject(currentSite.getSiteName() + "[" + rentalReservationInfoModel.getRental().getRentTitle() + "]" + " 시설이용예약 신청이 취소 되었습니다.");
					
					
					String html = null;
					
					StringBuilder sb = new StringBuilder(500);
					sb.append("<p>");
					sb.append("<h2>");
					sb.append(currentSite.getSiteName());
					sb.append("</h2>");
					sb.append("</p>");
					sb.append("<p style=\"font-weight: bold;\">");
					sb.append("안녕하세요. ");sb.append(rentalReservationForm.getReservName());sb.append("(");sb.append(rentalReservationInfoModel.getReservEmail());sb.append(")님.");
					sb.append("</p>");
					sb.append("<p>");
					sb.append(currentSite.getSiteName());
					sb.append(" [");
					sb.append(rentalReservationInfoModel.getRental().getRentTitle());
					sb.append("]" );
					sb.append("<br />");
					sb.append("예약일 : ");
					sb.append(rentalReservationInfoModel.getReservDate());
					sb.append("<br />");
					sb.append("시설 이용 예약 신청이 취소되었습니다.");
					sb.append("<br />");
					sb.append("문의 : 인프라 관리파트 02)2144-0201, 02)2144-0123");
					sb.append("</p>");
					
					html = sb.toString();
					mimeMessageHelper.setText(html, true);		
					if(!AsaproUtils.isDevPath(request) && !AsaproUtils.isAsadalNet(request) ){
						javaMailSender.send(mimeMessage);
					};
				} catch (MessagingException e) {
					LOGGER.error("[MessagingException] Try/Catch... : "+ e.getMessage());
				}
				//========= 끝 : 메일전송 ==========
				
				serverMessage.setSuccess(true);
				serverMessage.setSuccessCnt(result);
				serverMessage.setText("취소처리를 알림메일을 발송했습니다.");
			}else{
				serverMessage.setSuccess(false);
				serverMessage.setText("취소처리를 완료하지 못했습니다.[Server Error]");
			}
			
		}
		
		return serverMessage;
	}
	
	
	/**
	 * 신청가능 시간목록을 json으로 반환한다.
	 * @param model
	 * @param currentSite
	 * @param rentalReservationForm
	 * @return 신청가능 시간 목록 json
	 * @throws ParseException 
	 */
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/rental/reserv/possibleTimeList" + Constant.API_PATH + "/json.do", method=RequestMethod.GET)
	@ResponseBody
	public List<TimeCalculation> getJsonRentalReservationGet(Model model, @CurrentSite Site currentSite, @ModelAttribute("rentalReservationForm") RentalReservationInfo rentalReservationForm, @RequestParam(value="rentId", required=true) Integer rentId  ) throws ParseException{
		
		//대관/대여 정보
		Rental rental = new Rental();
		rental.setRentId(rentId);
		rental.setSitePrefix(currentSite.getSitePrefix());
		Rental rentalModel = rentalService.getRental(rental);
		
		rentalReservationForm.setRental(rentalModel);
		rentalReservationForm.setSitePrefix(currentSite.getSitePrefix());
		
		//예약가능 시간목록
		List<TimeCalculation> possibleTimeList = this.getPossibleTimeList(rentalReservationForm);
		
		
		return possibleTimeList;
	}
	
	
	/**
	 * 사용 신청서를 뷰를 반환한다.
	 * @param model
	 * @param currentSite
	 * @param rentalReservationForm
	 * @return 사용신청서 뷰
	 * @throws ParseException 
	 */
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/rental/reservation/printPreview.do", method=RequestMethod.GET)
	public String rentalReservationPrintPreviewGet(Model model, @CurrentSite Site currentSite, @ModelAttribute("rentalReservationForm") RentalReservationInfo rentalReservationForm) throws ParseException{
		rentalReservationForm.setSitePrefix(currentSite.getSitePrefix());
		RentalReservationInfo rentalReservationInfoModel = rentalService.getRentalReservation(rentalReservationForm);
		rentalReservationInfoModel.setSitePrefix(currentSite.getSitePrefix());
		
		//예약시간 배열로 변환
		if(StringUtils.isNotBlank(rentalReservationInfoModel.getReservTime()) ){
			String[] reservTimeArray = rentalReservationInfoModel.getReservTime().split(",");
			model.addAttribute("reservTimeArray", reservTimeArray);
		}

		//신청상태 코드목록
		//List<Code> statusCodeList = codeService.getCodeList("rentReservStatus");
		//model.addAttribute("statusCodeList", statusCodeList);
		
		//코드목록
		this.loadCodes(model);
		
		//불가능 날짜 목록
		//this.impossibleDateArray(model, rentalReservationInfoModel, currentSite);
		
		//가능시간목록
		//List<String> possibleTimeList = this.getPossibleTimeList(rentalReservationInfoModel);
		//model.addAttribute("possibleTimeList", possibleTimeList);
		
		
		//model.addAttribute("formMode", "UPDATE");
		model.addAttribute("rentalReservationForm", rentalReservationInfoModel);
		
		String nowTemp = AsaproUtils.getFormattedDate(new Date());
		String[] now = nowTemp.split("-");
		model.addAttribute("now", now);
		
		String reservPrice = AsaproUtils.toNumFormat(rentalReservationInfoModel.getReservPrice());
		String reservPaymentAmount = AsaproUtils.toNumFormat(rentalReservationInfoModel.getReservPaymentAmount());
		model.addAttribute("reservPrice", reservPrice);
		model.addAttribute("reservPaymentAmount", reservPaymentAmount);
		
		//이메일 발송 템플릿 여부 
		if(rentalReservationForm.getEmailTemp() ){
			return "asapro/admin/rental/email/printPreview";
		}else{
			return "asapro/admin/rental/printPreview";
		}
	}
	

	//=======================================================================================
	/**
	 * 신청가능시간 목록을 레이어를 반환한다.
	 * @param model
	 * @param currentSite
	 * @param rentalReservationForm
	 * @return 가능시간 레이어
	 * @throws ParseException 
	 */
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/rental/reservation/closeTime/Layer.do", method=RequestMethod.GET)
	public String rentalReservPossibleTimeListGet(Model model, @CurrentSite Site currentSite, @ModelAttribute("rentalReservationForm") RentalReservationInfo rentalReservationForm) throws ParseException{
		//대관/대여 정보
		Rental rental = new Rental();
		rental.setRentId(rentalReservationForm.getRental().getRentId());
		rental.setSitePrefix(currentSite.getSitePrefix());
		Rental rentalModel = rentalService.getRental(rental);
		
		//rental 셋
		rentalReservationForm.setRental(rentalModel);
		
		rentalReservationForm.setSitePrefix(currentSite.getSitePrefix());
		
		//불가능 날짜 목록
		this.impossibleDateArray(model, rentalReservationForm, currentSite);
		
		//가능시간목록
		List<TimeCalculation> possibleTimeList = this.getPossibleTimeList(rentalReservationForm);
		model.addAttribute("possibleTimeList", possibleTimeList);
		
		model.addAttribute("formMode", "INSERT");
		return "asapro/admin/rental/possibleTimeLayer";
	}
	
	
	/**
	 * 예약시간 닫힘정보를 등록하단.
	 * @param model
	 * @param currentSite
	 * @param rentId
	 * @param reservDate
	 * @param reservTimes
	 * @return 추가결과
	 */
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/rental/reservation/closeTime/insert.do", method=RequestMethod.POST)
	@ResponseBody
	public ServerMessage rentalReservCloseTimeInsertPost(Model model, @CurrentSite Site currentSite, @RequestParam(value="rentId", required=true) Integer rentId, @RequestParam(value="reservDate", required=true) String reservDate, @RequestParam(value="reservTimes[]", required=true) String[] reservTimes) {
		ServerMessage serverMessage = new ServerMessage();
		if( rentId == null || rentId <= 0 || StringUtils.isBlank(reservDate) || ArrayUtils.isEmpty(reservTimes) ){
			serverMessage.setSuccess(false);
			serverMessage.setText("추가할 항목의 데이터가 부족합니다.");
		} else {
			List<RentalReservCloseTime> rentalReservCloseTimeList = new ArrayList<RentalReservCloseTime>();
			RentalReservCloseTime rentalReservCloseTime = null;
			for( String reservTime : reservTimes ){
				rentalReservCloseTime = new RentalReservCloseTime();
				rentalReservCloseTime.setSitePrefix(currentSite.getSitePrefix());
				rentalReservCloseTime.setRentId(rentId);
				rentalReservCloseTime.setReservDate(reservDate);
				rentalReservCloseTime.setReservCloseTime(reservTime);
				rentalReservCloseTimeList.add(rentalReservCloseTime);
			}
			
			int result = rentalService.insertRentalReservCloseTime(rentalReservCloseTimeList);
			if(result > 0){
				serverMessage.setSuccess(true);
				serverMessage.setSuccessCnt(result);
			} else {
				serverMessage.setSuccess(false);
				serverMessage.setText("추가하지 못하였습니다.[Server Error]");
			}
		}
		return serverMessage;
	}
	
	/**
	 * 예약시간 닫힘정보를 삭제한다.
	 * @param model
	 * @param currentSite
	 * @param rentId
	 * @param reservDate
	 * @param reservTimes
	 * @return 삭제결과
	 */
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/rental/reservation/closeTime/delete.do", method=RequestMethod.POST)
	@ResponseBody
	public ServerMessage rentalReservCloseTimeDeletePost(Model model, @CurrentSite Site currentSite, @RequestParam(value="rentId", required=true) Integer rentId, @RequestParam(value="reservDate", required=true) String reservDate, @RequestParam(value="reservTime", required=true) String reservTime) {
		ServerMessage serverMessage = new ServerMessage();
		if( rentId == null || rentId <= 0 || StringUtils.isBlank(reservDate) || StringUtils.isBlank(reservTime) ){
			serverMessage.setSuccess(false);
			serverMessage.setText("삭제할 항목의 데이터가 부족합니다.");
		} else {
			RentalReservCloseTime rentalReservCloseTime = null;
			rentalReservCloseTime = new RentalReservCloseTime();
			rentalReservCloseTime.setSitePrefix(currentSite.getSitePrefix());
			rentalReservCloseTime.setRentId(rentId);
			rentalReservCloseTime.setReservDate(reservDate);
			rentalReservCloseTime.setReservCloseTime(reservTime);
			
			int result = rentalService.deleteRentalReservCloseTime(rentalReservCloseTime);
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
	
	
	//===============================================================================================================
	//===============================================================================================================
	//===============================================================================================================
	/**
	 * 예약가능 요일 여부를 반환한다.
	 * @param rentalReservationForm
	 * @return
	 * @throws ParseException
	 */
	public boolean isPossibleDayOfWeek(RentalReservationInfo rentalReservationForm) throws ParseException{
		boolean isPossible = true;
		Rental rental = rentalReservationForm.getRental();
		
		//예약일의 요일을 구한다.
		String day = AsaproUtils.getDayOfWeekText(rentalReservationForm.getReservDate());
		//가능요일 배열
		String[] possibleDayArray = rental.getRentPossibleDayOfWeek().split(",");
		
		isPossible = Arrays.asList(possibleDayArray).contains(day);
		return isPossible;
	}
	
	/**
	 * 예약불가일에 있는지 여부를 반환한다.
	 * @param rentalReservationForm
	 * @return
	 * @throws ParseException
	 */
	public boolean isImpossibleDate(RentalReservationInfo rentalReservationForm) throws ParseException{
		boolean isImpossible = false;
		Rental rental = rentalReservationForm.getRental();
		
		//불가일 배열
		String[] impossibleDateArray = null;
		if(rental.getRentImpossibleDate() != null){
			impossibleDateArray = rental.getRentImpossibleDate().split(", ");
			isImpossible = Arrays.asList(impossibleDateArray).contains(rentalReservationForm.getReservDate());
		}


		return isImpossible;
	}
	
	/**
	 * 이미 예약되어있어 불가한지 여부를 반환한다.
	 * @param rentalReservationForm
	 * @return
	 * @throws ParseException
	 */
	public boolean isPossibleDateTime(RentalReservationInfo rentalReservationForm) throws ParseException{
		boolean isPossible = true;
		
		//가능시간목록
		List<TimeCalculation> possibleTimeList = this.getPossibleTimeList(rentalReservationForm);
		
		String[] selectTimeArray = rentalReservationForm.getReservTime().split(",");//선택된 시간
		
		if(possibleTimeList == null || possibleTimeList.isEmpty() ){//가능 시간이 없으면
			isPossible = false;
		}else{
			List<String> possibleTimeListOnly = new ArrayList<String>();
			for (TimeCalculation timeCalculation : possibleTimeList) {
				possibleTimeListOnly.add(timeCalculation.getTime());
			}
			for (String time : selectTimeArray) {
				if(!possibleTimeListOnly.contains(time) ){	//선택한 시간이 가능 시간목록에 없으면
					isPossible = false;
				}
			}
		}
		
		return isPossible;
	}
}
