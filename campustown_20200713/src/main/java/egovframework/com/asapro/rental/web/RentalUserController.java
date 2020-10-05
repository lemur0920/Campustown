/**
 * 
 */
package egovframework.com.asapro.rental.web;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.BooleanUtils;
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
import org.springframework.web.bind.annotation.PathVariable;
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
import egovframework.com.asapro.member.user.service.UserMember;
import egovframework.com.asapro.organization.service.OrganizationService;
import egovframework.com.asapro.rental.service.PossibleDayTime;
import egovframework.com.asapro.rental.service.Rental;
import egovframework.com.asapro.rental.service.RentalCalendar;
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
import egovframework.com.asapro.support.annotation.CurrentUser;
import egovframework.com.asapro.support.exception.AsaproException;
import egovframework.com.asapro.support.exception.AsaproNoCapabilityException;
import egovframework.com.asapro.support.pagination.Paging;
import egovframework.com.asapro.support.util.AsaproMailUtils;
import egovframework.com.asapro.support.util.AsaproUtils;
import egovframework.com.asapro.support.util.EtcUtil;
import egovframework.com.campustown.univHpMngr.service.UnivHpMngr;
import egovframework.com.campustown.univHpMngr.service.UnivHpMngrSearch;
import egovframework.com.campustown.univHpMngr.service.UnivHpMngrService;

/**
 * 대관/대여 사용자 컨트롤러
 * @author yckim
 * @since 2018. 12. 09.
 *
 */
@Controller
public class RentalUserController {
	
	@Autowired
	private RentalService rentalService;
	
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
	
	@Autowired
	private HttpSession session;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(RentalUserController.class);
	
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
		String[] tempTermArray = null;
		Rental rental = rentalReservationForm.getRental();
		
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
		//예약가능시간 String  => List<RentReservTime> 로 변환
		if(StringUtils.isNotBlank(targetReservTime) ){
			tempTermArray = targetReservTime.split(",");
			//model.addAttribute("termArray", tempTermArray);
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
	//==========================================  신청정보   ================================================================
	//=============================================================================================================================
	
	/**
	 * 대관/대여 목록을 반환한다.
	 * @param model
	 * @param currentSite
	 * @param rentalSearch
	 * @return 대관/대여 목록
	 * @throws ParseException
	 */
	//@RequestMapping(value={Constant.APP_PATH + Constant.SITE_ID_PATH + "/rental/{rentCate1}/list"}, method=RequestMethod.GET)
	@RequestMapping(value={Constant.APP_PATH + Constant.SITE_ID_PATH + "/rental/list"}, method=RequestMethod.GET)
	public String rentalListGet(Model model, @CurrentSite Site currentSite, @ModelAttribute("rentalSearch") RentalSearch rentalSearch) throws ParseException{
		rentalSearch.setSitePrefix(currentSite.getSitePrefix());
		rentalSearch.fixBrokenSvByDefaultCharsets();
		rentalSearch.setPaging(true);
		rentalSearch.setPageSize(8);
		rentalSearch.setRentUse(true);
		
		List<Rental> list = rentalService.getRentalList(rentalSearch);
		int total = rentalService.getRentalListTotal(rentalSearch);
		
		//예약버튼 노출여부 셋트
		for (Rental rental : list) {
			//대관기간이 상시이면 노출 
			//기간이면 예약가능일의 시작일을 오늘을 기준으로 계산하여 계산된 날짜가 대관기간이 이내이면 예약버튼 노출
			
			//예약가능시작일
			String chkDate = AsaproUtils.getFormattedDate(AsaproUtils.makeCalculDate(rental.getRentReservSdateAfter()));
			
			//대관 상시
			if("always".equals(rental.getRentPossiblelDayType()) ){	//상시
				rental.setApplyPossibleBtn(true);
			}else if("period".equals(rental.getRentPossiblelDayType()) ){	//기간
				//대관 기간내 일자인지 확인
				if(chkDate.compareTo(rental.getRentPeriodSdate()) >= 0 && //대관/대여 기간 이전
						chkDate.compareTo(rental.getRentPeriodEdate()) <= 0 ){	//대관/대여기간 이후
					rental.setApplyPossibleBtn(true);
				}
			}
		}
		
		Paging paging = new Paging(list, total, rentalSearch);
		
		//카테고리1 목록
		//카테고리2 목록
		this.loadCate(model);
				
		model.addAttribute("paging", paging);
		
		//카테고리1 목록
		//카테고리2 목록
		return AsaproUtils.getThemePath(request) + "rental/rentalList";
	}
	
	/**
	 * 대관/대여 상세정보 뷰를 반환한다.
	 * @param model
	 * @param currentSite
	 * @param rentalSearch
	 * @return 대관/대여 상세 뷰
	 * @throws ParseException
	 */
	@RequestMapping(value={Constant.APP_PATH + Constant.SITE_ID_PATH + "/rental/view"}, method=RequestMethod.GET)
	public String rentalViewGet(Model model, @CurrentSite Site currentSite, @ModelAttribute("rentalForm") Rental rentalForm) {
		rentalForm.setSitePrefix(currentSite.getSitePrefix());
		Rental rentalModel = rentalService.getRental(rentalForm);
		
		//예약버튼 노출여부 셋트
		
		//대관기간이 상시이면 노출 
		//기간이면 예약가능일의 시작일을 오늘을 기준으로 계산하여 계산된 날짜가 대관기간이 이내이면 예약버튼 노출
		
		//예약가능시작일
		String chkDate = AsaproUtils.getFormattedDate(AsaproUtils.makeCalculDate(rentalModel.getRentReservSdateAfter()));
		
		//대관 상시
		if("always".equals(rentalModel.getRentPossiblelDayType()) ){	//상시
			rentalModel.setApplyPossibleBtn(true);
		}else if("period".equals(rentalModel.getRentPossiblelDayType()) ){	//기간
			//대관 기간내 일자인지 확인
			if(chkDate.compareTo(rentalModel.getRentPeriodSdate()) >= 0 && //대관/대여 기간 이전
					chkDate.compareTo(rentalModel.getRentPeriodEdate()) <= 0 ){	//대관/대여기간 이후
				rentalModel.setApplyPossibleBtn(true);
			}
		}
		
		//카테고리1 목록
		//카테고리2 목록
		//this.loadCate(model);
		
		model.addAttribute("rentalForm", rentalModel);
		
		return AsaproUtils.getThemePath(request) + "rental/rentalView";
	}
	
	/**
	 * 대관/대여 달력 반환한다.
	 * @param model
	 * @param currentSite
	 * @param rentalReservationSearch
	 * @return 달력
	 * @throws ParseException
	 */
	@RequestMapping(value={Constant.APP_PATH + Constant.SITE_ID_PATH + "/rental/{rentCate1}/calendar/{reservRentId}"}, method=RequestMethod.GET)
	public String rentalCalenderGet(Model model, @CurrentSite Site currentSite, @ModelAttribute("rentalReservationSearch") RentalReservationSearch rentalReservationSearch ) throws ParseException{
		Rental rental = new Rental();
		rental.setRentId(rentalReservationSearch.getReservRentId());
		rental.setSitePrefix(currentSite.getSitePrefix());
		Rental rentalModel = rentalService.getRental(rental);
		
		model.addAttribute("rental", rentalModel);
		
		//신청상태 코드목록
		List<Code> statusCodeList = codeService.getCodeList("rentReservStatus");
		model.addAttribute("statusCodeList", statusCodeList);
		
		//카테고리1 목록
		//카테고리2 목록
		this.loadCate(model);
		
		return AsaproUtils.getThemePath(request) + "rental/rentalCalendar";
	}
	
	/**
	 * 대관/대여 inner 달력 반환한다.
	 * @param model
	 * @param currentSite
	 * @param rentalReservationSearch
	 * @return 달력
	 * @throws ParseException
	 */
	@RequestMapping(value={Constant.APP_PATH + Constant.SITE_ID_PATH + "/rental/{rentCate1}/calendar/inner" + Constant.API_PATH + "/{reservRentId}"}, method=RequestMethod.GET)
	public String rentalCalenderInnerGet(Model model, @CurrentSite Site currentSite, @ModelAttribute("rentalReservationSearch") RentalReservationSearch rentalReservationSearch ) throws ParseException{
		Rental rental = new Rental();
		rental.setRentId(rentalReservationSearch.getReservRentId());
		rental.setSitePrefix(currentSite.getSitePrefix());
		Rental rentalModel = rentalService.getRental(rental);
		
		// =======   선택된 대관/대여시설의 승인된 예약신청 목록  =====
		rentalReservationSearch.setSitePrefix(currentSite.getSitePrefix());
		rentalReservationSearch.fixBrokenSvByDefaultCharsets();
		rentalReservationSearch.setPaging(false);
		rentalReservationSearch.setReservRentId(rental.getRentId());
		rentalReservationSearch.setPossibleStatus("impossible");	//취소를 재외한 접수(승인대기),승인완료, 결제대기, 예약완료 목록
		//해당월의 목록만
		//사용일자 기준으로 정렬
		rentalReservationSearch.setSortOrder("RESERV_DATE");
		rentalReservationSearch.setSortDirection("ASC");
		
		//년월일 정보가 없을시 (최초 해당 메뉴를 열었을경우)
		if(StringUtils.isBlank(rentalReservationSearch.getReservYear()) ){
			rentalReservationSearch.setReservYear(String.format("%04d", Calendar.getInstance().get(Calendar.YEAR)) );
		}
		if(StringUtils.isBlank(rentalReservationSearch.getReservMonth()) ){
			rentalReservationSearch.setReservMonth(String.format("%02d", Calendar.getInstance().get(Calendar.MONTH) + 1) );
		}
		
		List<RentalReservationInfo> reservList = rentalService.getRentalReservationList(rentalReservationSearch);
		// ========   //선택된 대관/대여시설의 승인된 예약신청 목록  =====
		
		//  ======   예약가능 일짜의 구간과 대관정보로 예약시간 닫음 정보 목록을 검색한다   ======
		List<RentalReservCloseTime> rentalReservCloseTimeList = null;
		RentalReservCloseTime rentalReservCloseTimeSearch = new RentalReservCloseTime();
		rentalReservCloseTimeSearch.setRentId(rental.getRentId());
		rentalReservCloseTimeSearch.setReservStartDate(rentalReservationSearch.getReservYear() + "-" + rentalReservationSearch.getReservMonth() + "-01");
		rentalReservCloseTimeSearch.setReservEndDate(rentalReservationSearch.getReservYear() + "-" + rentalReservationSearch.getReservMonth() + "-31");
		rentalReservCloseTimeSearch.setSitePrefix(currentSite.getSitePrefix());
		rentalReservCloseTimeList = rentalService.selectRentalReservCloseTimeList(rentalReservCloseTimeSearch);
		
		
		
		//   =====   //예약가능 일짜의 구간과 대관정보로 예약시간 닫음 정보 목록을 검색한다  ======
		
		
		//=========  달력생성  =================
		
		//이전달, 다음달 셋팅
		rentalReservationSearch.setPreNextYearMonthSet();
		
		//달력
		RentalCalendar rentalCalendar = new RentalCalendar(rentalModel, reservList, rentalReservCloseTimeList, rentalReservationSearch);
		model.addAttribute("rentalCalendar", rentalCalendar);
		//=========  //달력생성  =================
		
		return AsaproUtils.getThemePath(request) + "rental/rentalCalendarInner";
	}
	
	/**
	 * 예약가능 시간 목록을 반환한다.
	 * @param model
	 * @param currentSite
	 * @param rentalReservationForm
	 * @param rentId
	 * @return 시간목록
	 * @throws ParseException
	 */
	@RequestMapping(value={Constant.APP_PATH + Constant.SITE_ID_PATH + "/rental/{rentCate1}" + Constant.API_PATH + "/possibleTimeList"}, method=RequestMethod.GET)
	public String rentalCalenderPossibleTimeGet(Model model, @CurrentSite Site currentSite, @ModelAttribute("rentalReservationForm") RentalReservationInfo rentalReservationForm , @RequestParam(value="rentId", required=true) Integer rentId) throws ParseException{
		//대관/대여 정보
		Rental rental = new Rental();
		rental.setRentId(rentId);
		rental.setSitePrefix(currentSite.getSitePrefix());
		Rental rentalModel = rentalService.getRental(rental);
		
		rentalReservationForm.setRental(rentalModel);
		rentalReservationForm.setSitePrefix(currentSite.getSitePrefix());
		
		//예약가능 시간목록
		List<TimeCalculation> possibleTimeList = this.getPossibleTimeList(rentalReservationForm);
		model.addAttribute("possibleTimeList", possibleTimeList);
		
		return AsaproUtils.getThemePath(request) + "rental/rentalCalendarPossibleTime";
	}
	
	/**
	 * 개인정보 수집및 이용 동의 폼뷰를 반환한다.
	 * @param model
	 * @param currentSite
	 * @param reservRentId
	 * @param rentalReservationForm
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping(value={Constant.APP_PATH + Constant.SITE_ID_PATH + "/rental/agree"}, method={RequestMethod.POST})
	public String rentalAgreeGet(Model model, @CurrentSite Site currentSite, @ModelAttribute("rentalReservationForm") RentalReservationInfo rentalReservationForm) {
		//선택한 날짜, 시간의 조작을 막고 뷰에 전달하기위해
		if(StringUtils.isNotBlank(rentalReservationForm.getReservDate()) && StringUtils.isNotBlank(rentalReservationForm.getReservTime()) ){
			session.setAttribute("rental.reserv.reservDate", rentalReservationForm.getReservDate());
			session.setAttribute("rental.reserv.reservTime", rentalReservationForm.getReservTime());
		}
		
		//대관/대여 정보
		Rental rental = new Rental();
		rental.setRentId(rentalReservationForm.getRental().getRentId());
		rental.setSitePrefix(currentSite.getSitePrefix());
		Rental rentalModel = rentalService.getRental(rental);
		
		//rental 셋
		rentalReservationForm.setRental(rentalModel);
		
		//카테고리1 목록
		this.loadCate(model);
		
		//코드목록
		this.loadCodes(model);
		
		return AsaproUtils.getThemePath(request) + "rental/agree";
	}

	
	/**
	 * 대관 신청 폼 뷰를 반환한다.
	 * @param model
	 * @param currentSite
	 * @param rentalReservationForm
	 * @param rentId
	 * @return 신청폼
	 * @throws ParseException
	 */
	@RequestMapping(value={Constant.APP_PATH + Constant.SITE_ID_PATH + "/rental/{rental.rentCate1 }/reserv/{rental.rentId}"}, method={RequestMethod.GET, RequestMethod.POST})
	public String rentalReservGet(Model model, @CurrentSite Site currentSite, @ModelAttribute("rentalReservationForm") RentalReservationInfo rentalReservationForm, BindingResult bindingResult) throws ParseException{
		//예약선택한 날짜, 시간 데이터 확인 
		String reservDate = (String)session.getAttribute("rental.reserv.reservDate");
		String reservTime = (String)session.getAttribute("rental.reserv.reservTime");
		if(StringUtils.isBlank(reservDate) || StringUtils.isBlank(reservTime) ){
			//세션에 담은 임시값 지운다
			AsaproUtils.removeFromSessionStartsWith(request, "rental.reserv");
			
			String redirectUrl = AsaproUtils.getAppPath(currentSite) + "/rental/" + rentalReservationForm.getRental().getRentCate1() + "/calendar/" + rentalReservationForm.getRental().getRentId();
			throw new AsaproNoCapabilityException("예약시간을 선택하지 않았습니다.", redirectUrl);
		}

		//대관/대여 정보
		Rental rental = new Rental();
		rental.setRentId(rentalReservationForm.getRental().getRentId());
		rental.setSitePrefix(currentSite.getSitePrefix());
		Rental rentalModel = rentalService.getRental(rental);
		
		//rental 셋
		rentalReservationForm.setRental(rentalModel);
		
		rentalReservationForm.setReservDate(reservDate);
		rentalReservationForm.setReservTime(reservTime);
		
		rentalReservationForm.setReservPrice(rentalModel.getRentCharge());
		
		String[] tempTerm = rentalReservationForm.getReservTime().split(",");
		int gap = 0;
		for (String term : tempTerm ) {
			String[] timeArray = term.split("~");

			if("time".equals(rentalModel.getRentRentingMethod()) ){
				gap += AsaproUtils.calculTimeOfTwoTimes(timeArray[0], timeArray[1]);
			} else {
				gap += 1;	//package
			}
		}
		rentalReservationForm.setReservUsageTime(gap);
		rentalReservationForm.setReservPaymentAmount(rentalModel.getRentCharge() * gap);
		
		
		//코드목록
		this.loadCodes(model);
		
		//카테고리1 목록
		//카테고리2 목록
		this.loadCate(model);
		
		rentalReservationForm.setSitePrefix(currentSite.getSitePrefix());
		rentalReservationValidator.validate(rentalReservationForm, bindingResult, "AGREE");
		
		if( bindingResult.hasErrors() ){
			return AsaproUtils.getThemePath(request) + "rental/agree";
		}
		
		//개인정보이용동의 조작금지 및 데이터보존
		if(BooleanUtils.isNotFalse(rentalReservationForm.getAgreeChk())  ){
			session.setAttribute("rental.reserv.agree", rentalReservationForm.getAgreeChk());
		}
		
		model.addAttribute("formMode", "INSERT");
		
		return AsaproUtils.getThemePath(request) + "rental/reservForm";
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
	@RequestMapping(value={Constant.APP_PATH + Constant.SITE_ID_PATH + "/rental/{rental.rentCate1 }/reserv/{rental.rentId}/insert"}, method=RequestMethod.POST)
	public String rentalReservInsertPost(Model model, RedirectAttributes redirectAttributes, @CurrentUser UserMember currentUser, @CurrentSite Site currentSite, @ModelAttribute("rentalReservationForm") RentalReservationInfo rentalReservationForm, BindingResult bindingResult) throws AsaproException, IOException, ParseException {
		//예약선택한 날짜, 시간 데이터, 개인정보이용동의 확인 
		String reservDate = (String)session.getAttribute("rental.reserv.reservDate");
		String reservTime = (String)session.getAttribute("rental.reserv.reservTime");
		Boolean agree = (Boolean)session.getAttribute("rental.reserv.agree");
		if(StringUtils.isBlank(reservDate) || StringUtils.isBlank(reservTime) || BooleanUtils.isFalse(agree) ){
			//세션에 담은 임시값 지운다
			AsaproUtils.removeFromSessionStartsWith(request, "rental.reserv");
			
			String redirectUrl = AsaproUtils.getAppPath(currentSite) + "/rental/" + rentalReservationForm.getRental().getRentCate1() + "/calendar/" + rentalReservationForm.getRental().getRentId();
			throw new AsaproNoCapabilityException("예약시간을 선택하지 않았거나 개인정보이용 동의를 하지 않았습니다.", redirectUrl);
		}
		
		//대관/대여 정보
		Rental rental = new Rental();
		rental.setRentId(rentalReservationForm.getRental().getRentId());
		rental.setSitePrefix(currentSite.getSitePrefix());
		Rental rentalModel = rentalService.getRental(rental);
		
		//rental 셋
		rentalReservationForm.setRental(rentalModel);
		
		rentalReservationForm.setAgreeChk(agree);
		rentalReservationForm.setReservDate(reservDate);
		rentalReservationForm.setReservTime(reservTime);
		
		rentalReservationForm.setReservDiscountRate(rentalModel.getRentMemberDiscount());
		
		String[] tempTerm = rentalReservationForm.getReservTime().split(",");
		int hour = 0;
		for (String term : tempTerm ) {
			String[] timeArray = term.split("~");

			if("time".equals(rentalModel.getRentRentingMethod()) ){
				hour += AsaproUtils.calculTimeOfTwoTimes(timeArray[0], timeArray[1]);
			} else {
				hour += 1;	//package
			}
		}
		rentalReservationForm.setReservUsageTime(hour);
		int tempAmount = rentalModel.getRentCharge() * hour;
		rentalReservationForm.setReservPrice(tempAmount);
		int paymentAmount = tempAmount - (tempAmount * rentalReservationForm.getReservDiscountRate() / 100);
		rentalReservationForm.setReservPaymentAmount(paymentAmount);
		
		
		//코드목록
		this.loadCodes(model);
		
		//카테고리1 목록
		this.loadCate(model);
		
		@SuppressWarnings("unchecked")
		//List<Code> rentCate1List = (List<Code>) model.asMap().get("rentCate1List");
		List<UnivHpMngr> rentCate1List = (List<UnivHpMngr>) model.asMap().get("rentCate1List");

		String memo = null;
		String cate1Name = "";
		if(rentCate1List != null && rentCate1List.size() > 0 ){
			for (UnivHpMngr univHpMngr : rentCate1List) {
				if(rentalModel.getRentCate1().equals(univHpMngr.getUnivId()) ){
					cate1Name = univHpMngr.getUnivNm();
				}
			}
		}
		
		StringBuilder sb = new StringBuilder(500);
		sb.append("대관료 산정 : \n");
		//sb.append("\t\t" + AsaproUtils.codeName(rentalModel.getRentCate1(), rentCate1List) + " [" + rentalModel.getRentTitle() + "] ");
		sb.append("\t\t" + cate1Name + " [" + rentalModel.getRentTitle() + "] ");
		if("time".equals(rentalModel.getRentRentingMethod()) ){
			sb.append(hour + "시간");
		}
		sb.append("사용료 : ");
		sb.append(AsaproUtils.toNumFormat(tempAmount) + "원\n");
		if(rentalReservationForm.getReservDiscountRate() > 0){
			sb.append("\t\t할인 " + rentalReservationForm.getReservDiscountRate() + "% : -" + AsaproUtils.toNumFormat(tempAmount * rentalReservationForm.getReservDiscountRate() / 100) + "원\n");
		}
		sb.append("\t\t = " + AsaproUtils.toNumFormat(paymentAmount) + "원\n");
		
		memo = sb.toString();
		rentalReservationForm.setReservMemo(memo);
		
		rentalReservationForm.setSitePrefix(currentSite.getSitePrefix());
		rentalReservationValidator.validate(rentalReservationForm, bindingResult, "INSERT");
		
		if( bindingResult.hasErrors() ){
			
			model.addAttribute("insertFail", true);
			model.addAttribute("formMode", "INSERT");
			return AsaproUtils.getThemePath(request) + "rental/reservForm";
		} else {
			rentalReservationForm.setReservRegDate(new Date());
			rentalReservationForm.setReservUserId(currentUser.getUserId());
			
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
			//7.이미 예약된 날짜, 또는 시간인지 확인
			else if(!this.isPossibleDateTime(rentalReservationForm) ){
				possibleChk = false;
				cause = "overlap";			
			}
			
			if(possibleChk == false){
				model.addAttribute(cause, true);	//예약실패 원인
				
				model.addAttribute("insertFail", true);
				model.addAttribute("formMode", "INSERT");
				
				return AsaproUtils.getThemePath(request) + "rental/reservForm";
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
				
				model.addAttribute("fileInfoUploadResult", fileInfoUploadResult);
				model.addAttribute("insertFail", true);
				model.addAttribute("formMode", "INSERT");
				
				return AsaproUtils.getThemePath(request) + "rental/reservForm";
			}
			//========== 첨부 파일 처리 =====================
			
			rentalService.insertRentalReservation(rentalReservationForm);
			
			//========= 시작 : 문자전송 ==========
			/*JavaMailSender javaMailSender = AsaproMailUtils.getJavaMailSender();
			MimeMessage mimeMessage = javaMailSender.createMimeMessage();
			MimeMessageHelper mimeMessageHelper = null;
			try {
				mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
				try {
					mimeMessageHelper.setFrom("webmaster@kobaco.co.kr", currentSite.getSiteName());
				} catch (UnsupportedEncodingException e) {
					LOGGER.error("[UnsupportedEncodingException] Try/Catch...usingParameters Runing : "+ e.getMessage());
				}
				mimeMessageHelper.setTo(rentalReservationInfoModel.getReservEmail());
				mimeMessageHelper.setSubject(currentSite.getSiteName() + " 시설이용예약 신청이 접수 되었습니다.");
				
				
				String html = null;
				
				StringBuilder sb = new StringBuilder(500);
				sb.append("<p>");
				sb.append("<h2>");
				sb.append(currentSite.getSiteName());
				sb.append("</h2>");
				sb.append("</p>");
				sb.append("<p style=\"font-weight: bold;\">");
				sb.append("안녕하세요. ");sb.append(rentalReservationInfoModel.getReservName());sb.append("(");sb.append(rentalReservationInfoModel.getReservEmail());sb.append(")님.");
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
			}*/
			//========= 끝 : 문자전송 ==========
			
			redirectAttributes.addFlashAttribute("inserted", true);
			//request.setAttribute("reservId", rentalReservationForm.getReservId());
			//model.addAttribute("reservId", rentalReservationForm.getReservId());
			
			//신청등록완료하고 세션에 담은 임시값 지운다
			AsaproUtils.removeFromSessionStartsWith(request, "rental.reserv");
			
			//return "forward:" + AsaproUtils.getAppPath(currentSite) +  "/rental/reservation/confirm";
			//return "redirect:" + AsaproUtils.getAppPath(currentSite, true) +  "/rental/reservation/confirm?reservId=" + rentalReservationForm.getReservId();
			return "redirect:" + AsaproUtils.getAppPath(currentSite, true) +  "/mypage/rental/reserv/list";
		}
	}
	
	/**
	 * 신청정보 목록을 반환한다.
	 * @param model
	 * @param currentSite
	 * @param rentalReservationSearch
	 * @return 신청정보목록
	 */
	@RequestMapping(value=Constant.APP_PATH + Constant.SITE_ID_PATH + "/mypage/rental/reserv/list", method=RequestMethod.GET)
	public String rentalMypageReservListGet(Model model, @CurrentSite Site currentSite, @CurrentUser UserMember currentUser, @ModelAttribute("rentalReservationSearch") RentalReservationSearch rentalReservationSearch){
		rentalReservationSearch.setSitePrefix(currentSite.getSitePrefix());
		rentalReservationSearch.fixBrokenSvByDefaultCharsets();
		rentalReservationSearch.setPaging(true);
		rentalReservationSearch.setReservUserId(currentUser.getUserId());
		
		List<RentalReservationInfo> list = rentalService.getRentalReservationListByUserId(rentalReservationSearch);
		int total = rentalService.getRentalReservationListTotalByUserId(rentalReservationSearch);

		Paging paging = new Paging(list, total, rentalReservationSearch);
		model.addAttribute("paging", paging);
		
		//코드목록
		this.loadCodes(model);
		//카테고리1 목록
		//카테고리2 목록
		this.loadCate(model);
		
		return AsaproUtils.getThemePath(request) + "rental/mypage/reservList";
	}
	
	/**
	 * 진행버튼별 레이어를 반환한다.
	 * @param model
	 * @param currentUser
	 * @param currentSite
	 * @param rentalReservationForm
	 * @return 레이어
	 * @throws ParseException
	 */
	@RequestMapping(value={Constant.APP_PATH + Constant.SITE_ID_PATH + "/mypage/rental/reserv" + Constant.API_PATH + "/{layerType}"}, method=RequestMethod.GET)
	public String rentalReservLayerGet(Model model, @CurrentUser UserMember currentUser, @CurrentSite Site currentSite, @ModelAttribute("rentalReservationForm") RentalReservationInfo rentalReservationForm, @PathVariable String layerType ) throws ParseException{
		
		rentalReservationForm.setSitePrefix(currentSite.getSitePrefix());
		
		//예약가능 시간목록
		RentalReservationInfo rentalReservationModel = rentalService.getRentalReservation(rentalReservationForm);
		model.addAttribute("rentalReservation", rentalReservationModel);
		
		//카테고리1 목록
		//카테고리2 목록
		this.loadCate(model);
		
		return AsaproUtils.getThemePath(request) + "rental/mypage/" + layerType;
	}
	
	/**
	 * 예약신청 취소 처리한다.
	 * @param model
	 * @param currentUser
	 * @param currentSite
	 * @param rentalReservationForm
	 * @return 취소처리결과
	 * @throws ParseException
	 */
	@RequestMapping(value=Constant.APP_PATH + Constant.SITE_ID_PATH + "/mypage/rental/reserv" + Constant.API_PATH + "/cancel", method=RequestMethod.POST)
	@ResponseBody
	public ServerMessage RentalReservCancelPost(Model model, @CurrentUser UserMember currentUser, @CurrentSite Site currentSite, @ModelAttribute("rentalReservationForm") RentalReservationInfo rentalReservationForm  ) throws ParseException{
		ServerMessage serverMessage = new ServerMessage();
		if( StringUtils.isBlank(rentalReservationForm.getReservId())){
			serverMessage.setSuccess(false);
			serverMessage.setText("취소처리할 항목이 없습니다.");
		} else {
			rentalReservationForm.setSitePrefix(currentSite.getSitePrefix());
			RentalReservationInfo rentalReservationInfoModel = rentalService.getRentalReservation(rentalReservationForm);
			
			//본인아이디로 신청한 신청정보인지 검증
			if(rentalReservationInfoModel != null && !rentalReservationInfoModel.getReservUserId().equals(currentUser.getUserId()) ){
				serverMessage.setSuccess(false);
				serverMessage.setText("본인이 신청한 정보가 아니어서 삭제할 수 없습니다. ");
				return serverMessage;
			}
			
			//예약취소할 수 있는 단계의 상태인지 체크 - 승인대기, 결제대기 상태에서만 예약취소 가능
			int status = Integer.parseInt(rentalReservationInfoModel.getReservStatus().substring(2));
			if(status >= 40){ // RS40(예약완료) 이전 상태 체크
				serverMessage.setSuccess(false);
				serverMessage.setText("예약취소할 수 있는 진행단계가 아닙니다.");
				return serverMessage;
			}
			
			
			rentalReservationForm.setReservStatus("RS51");//예약취소(사용자)
			rentalReservationForm.setReservCancelDate(AsaproUtils.getFormattedDate(new Date()) );
			int result = rentalService.updateReservStatus(rentalReservationForm);
			
			//취소처리 완료시 알림메일 발송
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
				*/
				serverMessage.setSuccess(true);
				serverMessage.setSuccessCnt(result);
				serverMessage.setText("예약취소처리를 완료하고 알림문자를 발송했습니다.");
			}else{
				serverMessage.setSuccess(false);
				serverMessage.setText("예약취소처리를 완료하지 못했습니다.[Server Error]");
			}
			
		}
		
		return serverMessage;
	}

	/**
	 * 온라인 결제처리를 한다.
	 * @param model
	 * @param currentUser
	 * @param currentSite
	 * @param rentalReservationForm
	 * @return 결제처리결과
	 * @throws ParseException
	 */
	@RequestMapping(value=Constant.APP_PATH + Constant.SITE_ID_PATH + "/mypage/rental/reserv" + Constant.API_PATH + "/payment", method=RequestMethod.POST)
	@ResponseBody
	public ServerMessage RentalReservPaymentlPost(Model model, @CurrentUser UserMember currentUser, @CurrentSite Site currentSite, @ModelAttribute("rentalReservationForm") RentalReservationInfo rentalReservationForm  ) throws ParseException{
		ServerMessage serverMessage = new ServerMessage();
		if( StringUtils.isBlank(rentalReservationForm.getReservId())){
			serverMessage.setSuccess(false);
			serverMessage.setText("결제처리할 항목이 없습니다.");
		} else {
			rentalReservationForm.setSitePrefix(currentSite.getSitePrefix());
			RentalReservationInfo rentalReservationInfoModel = rentalService.getRentalReservation(rentalReservationForm);
			
			//본인아이디로 신청한 신청정보인지 검증
			if(rentalReservationInfoModel != null && !rentalReservationInfoModel.getReservUserId().equals(currentUser.getUserId()) ){
				serverMessage.setSuccess(false);
				serverMessage.setText("본인이 신청한 정보가 아니어서 결제할 수 없습니다. ");
				return serverMessage;
			}
			
			//예약취소할 수 있는 단계의 상태인지 체크 - 승인대기, 결제대기 상태에서만 예약취소 가능
			int status = Integer.parseInt(rentalReservationInfoModel.getReservStatus().substring(2));
			if(status == 30){ // RS30(결제대기 온라인) 
				serverMessage.setSuccess(false);
				serverMessage.setText("결제할 수 있는 진행단계가 아닙니다.");
				return serverMessage;
			}
			
			/**
			 * 온라인 결제 처리
			 */
			
			
			rentalReservationForm.setReservStatus("RS40");//예약완료
			rentalReservationForm.setReservPaymentDate(AsaproUtils.getFormattedDate(new Date()) );
			//int result = rentalService.updateReservStatus(rentalReservationForm);
			int result = 0;
			
			//취소처리 완료시 알림메일 발송
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
				 */
				serverMessage.setSuccess(true);
				serverMessage.setSuccessCnt(result);
				serverMessage.setText("결제처리를 완료하고 알림문자를 발송했습니다.");
			}else{
				serverMessage.setSuccess(false);
				serverMessage.setText("결제처리를 완료하지 못했습니다.[Server Error]");
			}
			
		}
		
		return serverMessage;
	}
	
	/**
	 * 온라인 결제취소처리를 한다.
	 * @param model
	 * @param currentUser
	 * @param currentSite
	 * @param rentalReservationForm
	 * @return 결제취소처리결과
	 * @throws ParseException
	 */
	@RequestMapping(value=Constant.APP_PATH + Constant.SITE_ID_PATH + "/mypage/rental/reserv" + Constant.API_PATH + "/payCancel", method=RequestMethod.POST)
	@ResponseBody
	public ServerMessage RentalReservPayCancelPost(Model model, @CurrentUser UserMember currentUser, @CurrentSite Site currentSite, @ModelAttribute("rentalReservationForm") RentalReservationInfo rentalReservationForm  ) throws ParseException{
		ServerMessage serverMessage = new ServerMessage();
		if( StringUtils.isBlank(rentalReservationForm.getReservId())){
			serverMessage.setSuccess(false);
			serverMessage.setText("결제취소처리할 항목이 없습니다.");
		} else {
			rentalReservationForm.setSitePrefix(currentSite.getSitePrefix());
			RentalReservationInfo rentalReservationInfoModel = rentalService.getRentalReservation(rentalReservationForm);
			
			//본인아이디로 신청한 신청정보인지 검증
			if(rentalReservationInfoModel != null && !rentalReservationInfoModel.getReservUserId().equals(currentUser.getUserId()) ){
				serverMessage.setSuccess(false);
				serverMessage.setText("본인이 신청한 정보가 아니어서 결제취소할 수 없습니다. ");
				return serverMessage;
			}
			
			//결제취소할 수 있는 단계의 상태인지 체크 - 예약완료인 상태만 취소 가능
			int status = Integer.parseInt(rentalReservationInfoModel.getReservStatus().substring(2));
			if(status == 40){ // RS40(예약완료) 
				serverMessage.setSuccess(false);
				serverMessage.setText("결제취소할 수 있는 진행단계가 아닙니다.");
				return serverMessage;
			}
			
			/**
			 * 온라인 결제취소 처리
			 */
			
			
			rentalReservationForm.setReservStatus("RS51");//예약완료
			rentalReservationForm.setReservPayCancelDate(AsaproUtils.getFormattedDate(new Date()) );
			//int result = rentalService.updateReservStatus(rentalReservationForm);
			int result = 0;
			
			//결제취소처리 완료시 알림메일 발송
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
				 */
				serverMessage.setSuccess(true);
				serverMessage.setSuccessCnt(result);
				serverMessage.setText("결제취소처리를 완료하고 알림문자를 발송했습니다.");
			}else{
				serverMessage.setSuccess(false);
				serverMessage.setText("결제취소처리를 완료하지 못했습니다.[Server Error]");
			}
			
		}
		
		return serverMessage;
	}
	
	
//=================================================================================================================================	
	
	
	/**
	 * 신청정보를 등록 폼 뷰를 반환한다.
	 * @param model
	 * @param currentSite
	 * @param reservRentId
	 * @param rentalReservationForm
	 * @param bindingResult
	 * @return 선청폼 뷰
	 * @throws ParseException 
	 */
/*	@RequestMapping(value={Constant.APP_PATH + Constant.SITE_ID_PATH + "/rental/reservation/write"}, method=RequestMethod.POST)
	public String rentalReservationWritePost(Model model, @CurrentSite Site currentSite, @ModelAttribute("rentalReservationForm") RentalReservationInfo rentalReservationForm, BindingResult bindingResult) throws ParseException {
		//대관/대여 정보
		Rental rental = new Rental();
		rental.setRentId(rentalReservationForm.getRental().getRentId());
		rental.setSitePrefix(currentSite.getSitePrefix());
		Rental rentalModel = rentalService.getRental(rental);
		
		//rental 셋
		rentalReservationForm.setRental(rentalModel);
		//카테고리1 목록
		this.loadCate(model);
		
		//코드목록
		this.loadCodes(model);

		rentalReservationForm.setSitePrefix(currentSite.getSitePrefix());
		rentalReservationValidator.validate(rentalReservationForm, bindingResult, "AGREE");
		
		if( bindingResult.hasErrors() ){
			return AsaproUtils.getThemePath(request) + "rental/agree";
		}else{
			
			//가능시간목록
			List<TimeCalculation> possibleTimeList = this.getPossibleTimeList(rentalReservationForm);
			model.addAttribute("possibleTimeList", possibleTimeList);
			model.addAttribute("formMode", "INSERT");
			return AsaproUtils.getThemePath(request) + "rental/reservForm";
		}
		
		
	}
	*/
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
/*	@RequestMapping(value={Constant.APP_PATH + Constant.SITE_ID_PATH + "/rental/reservation/insert"}, method=RequestMethod.POST)
	public String rentalReservationInsertPost(Model model, RedirectAttributes redirectAttributes, @CurrentSite Site currentSite, @ModelAttribute("rentalReservationForm") RentalReservationInfo rentalReservationForm, BindingResult bindingResult) throws AsaproException, IOException, ParseException {
		//대관/대여 정보
		Rental rental = new Rental();
		rental.setRentId(rentalReservationForm.getRental().getRentId());
		rental.setSitePrefix(currentSite.getSitePrefix());
		Rental rentalModel = rentalService.getRental(rental);
		
		//rental 셋
		rentalReservationForm.setRental(rentalModel);
		
		//코드목록
		this.loadCodes(model);
		
		//카테고리1 목록
		this.loadCate(model);
		
		rentalReservationForm.setSitePrefix(currentSite.getSitePrefix());
		rentalReservationValidator.validate(rentalReservationForm, bindingResult, "INSERT");
		
		if( bindingResult.hasErrors() ){
			
			//가능시간목록
			List<TimeCalculation> possibleTimeList = this.getPossibleTimeList(rentalReservationForm);
			model.addAttribute("possibleTimeList", possibleTimeList);
			model.addAttribute("insertFail", true);
			model.addAttribute("formMode", "INSERT");
			return AsaproUtils.getThemePath(request) + "rental/reservForm";
		}else{
			
			rentalReservationForm.setReservRegDate(new Date());
			rentalReservationForm.setReservStatus("reception");	//접수 (상태코드)
			
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
			//7.이미 예약된 날짜, 또는 시간인지 확인
			else if(!this.isPossibleDateTime(rentalReservationForm) ){
				possibleChk = false;
				cause = "overlap";			
			}
			
			if(possibleChk == false){
				model.addAttribute(cause, true);	//예약실패 원인
				
				//가능시간목록
				List<TimeCalculation> possibleTimeList = this.getPossibleTimeList(rentalReservationForm);
				model.addAttribute("possibleTimeList", possibleTimeList);
				
				model.addAttribute("insertFail", true);
				model.addAttribute("formMode", "INSERT");
				return AsaproUtils.getThemePath(request) + "rental/reservForm";
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
				
				//가능시간목록
				List<TimeCalculation> possibleTimeList = this.getPossibleTimeList(rentalReservationForm);
				model.addAttribute("possibleTimeList", possibleTimeList);
				
				model.addAttribute("fileInfoUploadResult", fileInfoUploadResult);
				model.addAttribute("insertFail", true);
				model.addAttribute("formMode", "INSERT");
				return AsaproUtils.getThemePath(request) + "rental/reservForm";
			}
			//========== 첨부 파일 처리 =====================
			
			rentalService.insertRentalReservation(rentalReservationForm);
			redirectAttributes.addFlashAttribute("inserted", true);
			//request.setAttribute("reservId", rentalReservationForm.getReservId());
			//model.addAttribute("reservId", rentalReservationForm.getReservId());
			
			//return "forward:" + AsaproUtils.getAppPath(currentSite) +  "/rental/reservation/confirm";
			return "redirect:" + AsaproUtils.getAppPath(currentSite, true) +  "/rental/reservation/confirm?reservId=" + rentalReservationForm.getReservId();
		}
	}
	*/	
	
	/**
	 * 신청정보 확인 폼 뷰를 반환한다.
	 * @param model
	 * @param currentSite
	 * @param reservRentId
	 * @param rentalReservationForm
	 * @param bindingResult
	 * @return 확인폼 뷰
	 * @throws ParseException 
	 */
/*	@RequestMapping(value={Constant.APP_PATH + Constant.SITE_ID_PATH + "/rental/reservation/confirm"}, method=RequestMethod.GET)
	public String rentalReservationConfirmPost(Model model, @CurrentSite Site currentSite, @ModelAttribute("rentalReservationForm") RentalReservationInfo rentalReservationForm) throws ParseException {
		rentalReservationForm.setSitePrefix(currentSite.getSitePrefix());
		//rentalReservationForm.setReservId((Integer)request.getAttribute("reservId"));
		RentalReservationInfo rentalReservationInfoModel = rentalService.getRentalReservation(rentalReservationForm);
		rentalReservationInfoModel.setSitePrefix(currentSite.getSitePrefix());
		
		//코드목록
		this.loadCodes(model);
		
		//카테고리1 목록
		this.loadCate(model);
		
			
		//가능시간목록
		List<TimeCalculation> possibleTimeList = this.getPossibleTimeList(rentalReservationInfoModel);
		model.addAttribute("possibleTimeList", possibleTimeList);
		
		model.addAttribute("rentalReservationForm", rentalReservationInfoModel);
		
		return AsaproUtils.getThemePath(request) + "rental/reservConfirm";
	}
*/	

	
	/**
	 * 신청정보 추가결과 뷰를 반환한다.
	 * @param model
	 * @param currentSite
	 * @param reservRentId
	 * @param rentalReservationForm
	 * @param bindingResult
	 * @return 추가결과 뷰
	 * @throws ParseException 
	 */
/*	@RequestMapping(value={Constant.APP_PATH + Constant.SITE_ID_PATH + "/rental/reservation/result"}, method=RequestMethod.POST)
	public String rentalReservationResultPost(Model model, @CurrentSite Site currentSite, @ModelAttribute("rentalReservationForm") RentalReservationInfo rentalReservationForm, BindingResult bindingResult) throws ParseException {
		rentalReservationForm.setSitePrefix(currentSite.getSitePrefix());
		RentalReservationInfo rentalReservationInfoModel = rentalService.getRentalReservation(rentalReservationForm);
		rentalReservationInfoModel.setSitePrefix(currentSite.getSitePrefix());
		
		//신청상태 코드목록
		List<Code> statusCodeList = codeService.getCodeList("reservStatus");
		model.addAttribute("statusCodeList", statusCodeList);
		
		//코드목록
		this.loadCodes(model);
		
		//카테고리1 목록
		this.loadCate(model);
		
		//Rental rental = rentalReservationInfoModel.getRental();
		
		//가능시간목록
		List<TimeCalculation> possibleTimeList = this.getPossibleTimeList(rentalReservationInfoModel);
		model.addAttribute("possibleTimeList", possibleTimeList);
		
		model.addAttribute("rentalReservationForm", rentalReservationInfoModel);
		
		//========= 시작 : 메일전송 ==========
		JavaMailSender javaMailSender = AsaproMailUtils.getJavaMailSender();
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		MimeMessageHelper mimeMessageHelper = null;
		try {
			mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
			try {
				mimeMessageHelper.setFrom("webmaster@kobaco.co.kr", currentSite.getSiteName());
			} catch (UnsupportedEncodingException e) {
				LOGGER.error("[UnsupportedEncodingException] Try/Catch...usingParameters Runing : "+ e.getMessage());
			}
			mimeMessageHelper.setTo(rentalReservationInfoModel.getReservEmail());
			mimeMessageHelper.setSubject(currentSite.getSiteName() + " 시설이용예약 신청이 접수 되었습니다.");
			
			
			String html = null;
			
			StringBuilder sb = new StringBuilder(500);
			sb.append("<p>");
			sb.append("<h2>");
			sb.append(currentSite.getSiteName());
			sb.append("</h2>");
			sb.append("</p>");
			sb.append("<p style=\"font-weight: bold;\">");
			sb.append("안녕하세요. ");sb.append(rentalReservationInfoModel.getReservName());sb.append("(");sb.append(rentalReservationInfoModel.getReservEmail());sb.append(")님.");
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
		
		return AsaproUtils.getThemePath(request) + "rental/reservResult";
	}
*/	
	/**
	 * 신청정보 수정 폼 뷰를 반환한다.
	 * @param model
	 * @param currentSite
	 * @param reservRentId
	 * @param rentalReservationForm
	 * @param bindingResult
	 * @return 수정폼 뷰
	 * @throws ParseException 
	 */
/*	@RequestMapping(value={Constant.APP_PATH + Constant.SITE_ID_PATH + "/rental/reservation/updateForm"}, method=RequestMethod.POST)
	public String rentalReservationUpdateFormPost(Model model, @CurrentSite Site currentSite, @ModelAttribute("rentalReservationForm") RentalReservationInfo rentalReservationForm, BindingResult bindingResult) throws ParseException {
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
		
		//코드목록
		this.loadCodes(model);
		
		//카테고리1 목록
		this.loadCate(model);
		
			
		//가능시간목록
		List<TimeCalculation> possibleTimeList = this.getPossibleTimeList(rentalReservationInfoModel);
		model.addAttribute("possibleTimeList", possibleTimeList);
		
		model.addAttribute("rentalReservationForm", rentalReservationInfoModel);
		model.addAttribute("formMode", "UPDATE");
		return AsaproUtils.getThemePath(request) + "rental/reservForm";
	}
*/	
	/**
	 * 신청정보를 수정한다.
	 * @param model
	 * @param currentSite
	 * @param reservRentId
	 * @param rentalReservationForm
	 * @param bindingResult
	 * @return 수정결과
	 * @throws IOException 
	 * @throws AsaproException 
	 * @throws ParseException 
	 */
/*	@RequestMapping(value={Constant.APP_PATH + Constant.SITE_ID_PATH + "/rental/reservation/update"}, method=RequestMethod.POST)
	public String rentalReservationUpdatePost(Model model, RedirectAttributes redirectAttributes, @CurrentSite Site currentSite, @ModelAttribute("rentalReservationForm") RentalReservationInfo rentalReservationForm, BindingResult bindingResult) throws AsaproException, IOException, ParseException {
		rentalReservationForm.setSitePrefix(currentSite.getSitePrefix());
		RentalReservationInfo rentalReservationInfoModel = rentalService.getRentalReservation(rentalReservationForm);
		rentalReservationInfoModel.setSitePrefix(currentSite.getSitePrefix());
		rentalReservationForm.setReservAppendingFileInfo(rentalReservationInfoModel.getReservAppendingFileInfo());
		Rental rentalModel = rentalReservationInfoModel.getRental();
		rentalReservationForm.setRental(rentalModel);
		
		//코드목록
		this.loadCodes(model);
		
		//카테고리1 목록
		this.loadCate(model);
		
		rentalReservationValidator.validate(rentalReservationForm, bindingResult, "UPDATE_USER");
		
		if( bindingResult.hasErrors() ){
			
			//가능시간목록
			List<TimeCalculation> possibleTimeList = this.getPossibleTimeList(rentalReservationForm);
			model.addAttribute("possibleTimeList", possibleTimeList);
			
			model.addAttribute("updateFail", true);
			model.addAttribute("formMode", "UPDATE");
			
			return AsaproUtils.getThemePath(request) + "rental/reservForm";
		}else{
			rentalReservationForm.setReservRegDate(new Date());
			rentalReservationForm.setReservStatus("reception");	//접수 (상태코드)
			
			//기본주차대수
			rentalReservationForm.setReservParking(rentalModel.getRentParking());
			
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
			//7.이미 예약된 날짜, 또는 시간인지 확인
			else if(!this.isPossibleDateTime(rentalReservationForm) ){
				possibleChk = false;
				cause = "overlap";			
			}
			
			if(possibleChk == false){
				model.addAttribute(cause, true);	//예약실패 원인
				
				//가능시간목록
				List<TimeCalculation> possibleTimeList = this.getPossibleTimeList(rentalReservationForm);
				model.addAttribute("possibleTimeList", possibleTimeList);
				
				model.addAttribute("updateFail", true);
				model.addAttribute("formMode", "UPDATE");
				
				return AsaproUtils.getThemePath(request) + "rental/reservForm";
			}
			//============== //예약가능 날짜, 시간 서버단 검증  ============
			
			//========== 첨부 파일 처리 =====================
			
			//첨부가능한 확장자
			String uploadWhiteList = "";
			
			FileInfoUploadResult fileInfoUploadResult = new FileInfoUploadResult();
			
			//이미지 처리
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
				model.addAttribute("fileInfoUploadResult", fileInfoUploadResult);
				
				//가능시간목록
				List<TimeCalculation> possibleTimeList = this.getPossibleTimeList(rentalReservationForm);
				model.addAttribute("possibleTimeList", possibleTimeList);
				
				model.addAttribute("updateFail", true);
				model.addAttribute("formMode", "UPDATE");
				
				return AsaproUtils.getThemePath(request) + "rental/reservForm";
			}
			//========== 첨부 파일 처리 =====================
			
			rentalService.updateRentalReservation(rentalReservationForm);
			redirectAttributes.addFlashAttribute("updated", true);
			//request.setAttribute("reservId", rentalReservationForm.getReservId());
			
			return "redirect:" + AsaproUtils.getAppPath(currentSite, true) +  "/rental/reservation/confirm?reservId=" + rentalReservationForm.getReservId();
		}
	}
*/	
	/**
	 * 사용 신청서를 뷰를 반환한다.
	 * @param model
	 * @param currentSite
	 * @param rentalReservationForm
	 * @return 사용신청서 뷰
	 * @throws ParseException 
	 */
	@RequestMapping(value=Constant.APP_PATH + Constant.SITE_ID_PATH + "/rental/reservation/printPreview", method=RequestMethod.GET)
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
		//List<Code> statusCodeList = codeService.getCodeList("reservStatus");
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
		
		return AsaproUtils.getThemePath(request) + "rental/email/printPreview";
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
