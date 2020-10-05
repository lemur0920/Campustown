/**
 * 
 */
package egovframework.com.asapro.rental.service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang3.StringUtils;

import egovframework.com.asapro.support.exception.AsaproException;
import egovframework.com.asapro.support.util.AsaproUtils;
import egovframework.com.asapro.support.util.EtcUtil;

/**
 * 대관/대여 달력
 * @author yckim
 * @since 2018. 12. 10.
 *
 */
public class RentalCalendar {
	
	private Integer calYear;	//해당 년도
	private Integer calMonth;	//해당 월
	
	private List<RentalCalendarDay> days; //해당월의 날짜와 날짜별 데이터
	private List<String> impossibleDateList = null;	//불가일 지정날짜 목록
	private List<String> possibleDayOfWeekList = null;	//예약가능 요일목록
	private Rental rental;	//대관/대여
	private List<RentalReservationInfo> reservList;	//이미 접수,승인된 예약정보 목록
	private List<RentalReservCloseTime> rentalReservCloseTimeList;	//닫음처리 목록
	private RentalReservationSearch rentalReservationSearch;	//예약정보 검색 객체
	private List<String> configTermList = null;	//예약설정 가능시간
	private List<String> configTermListWeekday = new ArrayList<String>();	//예약설정 가능시간 평일
	private List<String> configTermListSat = new ArrayList<String>();	//예약설정 가능시간 토요일
	private List<String> configTermListSun = new ArrayList<String>();	//예약설정 가능시간 일요일
	
	/**
	 * default constructor
	 */
	public RentalCalendar(){
		throw new AsaproException("Use RentalCalendar(Rental rental, List<RentalReservationInfo> reservList, List<RentalReservCloseTime> rentalReservCloseTimeList, RentalReservationSearch rentalReservationSearch) instead...");
	}
	
	/**
	 * 달력 생성자
	 * @param rental
	 * @param reservlist
	 * @param rentalReservationSearch
	 * @throws ParseException 
	 */
	public RentalCalendar(Rental rental, List<RentalReservationInfo> reservList, List<RentalReservCloseTime> rentalReservCloseTimeList, RentalReservationSearch rentalReservationSearch) throws ParseException{
		//대관/대여
		this.rental = rental;
		//이미 접수,승인된 예약정보 목록
		this.reservList = reservList;
		//닫음처리 목록
		this.rentalReservCloseTimeList = rentalReservCloseTimeList;
		//예약정보 검색 객체
		this.rentalReservationSearch = rentalReservationSearch;
		//해당 년도
		calYear = Integer.parseInt(rentalReservationSearch.getReservYear());
		//해당 월
		calMonth = Integer.parseInt(rentalReservationSearch.getReservMonth());
		
		//불가일 지정날짜 목록
		if(StringUtils.isNotBlank(rental.getRentImpossibleDate()) ){
			impossibleDateList = Arrays.asList(rental.getRentImpossibleDate().split(", "));	
		}
		//예약가능 요일목록
		possibleDayOfWeekList = Arrays.asList(rental.getRentPossibleDayOfWeek().split(","));
		
		//설정된 예약가능시간 String  
		if(StringUtils.isNotBlank(rental.getRentReservTime()) ){
			String[] tempTermArray = rental.getRentReservTime().split(",");
			
			for (String tempTerm : tempTermArray) {
				
				String[] term = tempTerm.split(";");
				
				StringBuilder sb = new StringBuilder();
				sb.append(term[0] );
				sb.append("~" );
				sb.append(term[1] );
				configTermListWeekday.add(sb.toString());
			}
			//configTermListWeekday = Arrays.asList(rental.getRentReservTime().split(","));
		}	
		if(StringUtils.isNotBlank(rental.getRentReservTimeSat()) ){
			String[] tempTermArray = rental.getRentReservTimeSat().split(",");
			
			for (String tempTerm : tempTermArray) {
				
				String[] term = tempTerm.split(";");
				
				StringBuilder sb = new StringBuilder();
				sb.append(term[0] );
				sb.append("~" );
				sb.append(term[1] );
				configTermListSat.add(sb.toString());
			}
		}	
		if(StringUtils.isNotBlank(rental.getRentReservTimeSun()) ){
			String[] tempTermArray = rental.getRentReservTimeSun().split(",");
			
			for (String tempTerm : tempTermArray) {
				
				String[] term = tempTerm.split(";");
				
				StringBuilder sb = new StringBuilder();
				sb.append(term[0] );
				sb.append("~" );
				sb.append(term[1] );
				configTermListSun.add(sb.toString());
			}
		}	

		//============================================================================================================		
		
		int year = Integer.parseInt(rentalReservationSearch.getReservYear());
		int month = Integer.parseInt(rentalReservationSearch.getReservMonth()) - 1;
		
		
		//지금 보여줄 달
		Calendar currentMonthCal = Calendar.getInstance(Locale.KOREA);
		currentMonthCal.set(Calendar.YEAR, year);
		currentMonthCal.set(Calendar.MONTH, month);
		currentMonthCal.set(Calendar.DAY_OF_MONTH, 1);
		
		//이전달
		Calendar lastMonthCal = Calendar.getInstance(Locale.KOREA);
		lastMonthCal.set(Calendar.YEAR, year);
		lastMonthCal.set(Calendar.MONTH, month);
		lastMonthCal.add(Calendar.MONTH, -1);
		
		//다음달
		Calendar nextMonthCal = Calendar.getInstance(Locale.KOREA);
		nextMonthCal.set(Calendar.YEAR, year);
		nextMonthCal.set(Calendar.MONTH, month);
		nextMonthCal.add(Calendar.MONTH, 1);
		
		//이번달 마지막 날
		int lastDayOfCurrentMonth = currentMonthCal.getActualMaximum(Calendar.DAY_OF_MONTH);
		
		//1:일요일 ~ 2:화요일...
		//해당월의 1일로 만든 다음 
		currentMonthCal.set(Calendar.DAY_OF_MONTH, 1);
		//1일의 요일을 구한다
		int dayOfWeekStartDay = currentMonthCal.get(Calendar.DAY_OF_WEEK);
		
		//해당월의 마지막날로 만든 다음
		currentMonthCal.set(Calendar.DAY_OF_MONTH, currentMonthCal.getActualMaximum(Calendar.DAY_OF_MONTH));
		//마지막날의 요일을 구한다.
		int dayOfWeekLastDay = currentMonthCal.get(Calendar.DAY_OF_WEEK);
		
		//지난달의 마지막날
		int lastDayOfLastMonth = lastMonthCal.getActualMaximum(Calendar.DAY_OF_MONTH);
		
		//달력에 들어갈 날들을 채운다
		days = new ArrayList<RentalCalendarDay>();
		RentalCalendarDay rentalCalendarDay = null;
		
		//달력에 표시될 지난달 끝부분
		int dayOfWeek = 1;
		int startDayOfLastMonth = lastDayOfLastMonth;
		if( dayOfWeekStartDay == 1 ){
			startDayOfLastMonth = startDayOfLastMonth - 6;
		} else {
			startDayOfLastMonth = startDayOfLastMonth - dayOfWeekStartDay + 2;
		}	
		for( int i = startDayOfLastMonth; i<= lastDayOfLastMonth ; i++ ){
			rentalCalendarDay = new RentalCalendarDay();
			rentalCalendarDay.setYear(lastMonthCal.get(Calendar.YEAR));
			rentalCalendarDay.setMonth(lastMonthCal.get(Calendar.MONTH) + 1);
			rentalCalendarDay.setDay(i);
			rentalCalendarDay.setDayOfWeek(dayOfWeek++);
			days.add(rentalCalendarDay);
		}
				
		//이번달부분
		for( int i = 1; i<= lastDayOfCurrentMonth; i++ ){
			rentalCalendarDay = new RentalCalendarDay();
			rentalCalendarDay.setYear(currentMonthCal.get(Calendar.YEAR));
			rentalCalendarDay.setMonth(currentMonthCal.get(Calendar.MONTH) + 1);
			rentalCalendarDay.setDay(i);
			dayOfWeek = dayOfWeek % 7 == 0 ? 7 : dayOfWeek % 7;
			rentalCalendarDay.setDayOfWeek(dayOfWeek++);
			days.add(rentalCalendarDay);
		}		
				
		//달력에 표시될 다음달 시작부분
		int lastDayOfNextMonth = 1;
		if( dayOfWeekLastDay == 7 ){
			lastDayOfNextMonth = 7;
		} else {
			lastDayOfNextMonth = 7 - dayOfWeekLastDay;
		}
		for( int i = 1; i<= lastDayOfNextMonth; i++ ){
			rentalCalendarDay = new RentalCalendarDay();
			rentalCalendarDay.setYear(nextMonthCal.get(Calendar.YEAR));
			rentalCalendarDay.setMonth(nextMonthCal.get(Calendar.MONTH) + 1);
			rentalCalendarDay.setDay(i);
			dayOfWeek = dayOfWeek % 7 == 0 ? 7 : dayOfWeek % 7;
			rentalCalendarDay.setDayOfWeek(dayOfWeek++);
			days.add(rentalCalendarDay);
		}		
		
		
		//대관 기간과 예약가능 일 이내의 가능목을 만들기 위해 구간시작과 종료일을 구한다.
		//====================================================
		String possibleStartDate = "";
		String possibleEndDate = "";
		
		Date applyStartDate = AsaproUtils.makeCalculDate(rental.getRentReservSdateAfter());
		String applyStartDateStr = AsaproUtils.getFormattedDate(applyStartDate,"yyyy-MM-dd");
		Date applyEndDate = AsaproUtils.makeCalculDate(rental.getRentReservEdateAfter());
		String applyEndDateStr = AsaproUtils.getFormattedDate(applyEndDate,"yyyy-MM-dd");
		
		if("always".equals(rental.getRentPossiblelDayType()) ){//상시
			possibleStartDate = applyStartDateStr;
			possibleEndDate = applyEndDateStr;
		}else if("period".equals(rental.getRentPossiblelDayType()) ){//기간
			//대관시작일 <= 예약가능 시작일 => 예약가능일
			if(rental.getRentPeriodSdate().compareTo(applyStartDateStr) <= 0){
				possibleStartDate = applyStartDateStr;
			}else{
				//대관시작일 > 예약가능 시작일 => 대관시작일
				possibleStartDate = rental.getRentPeriodSdate();
			}
			
			//대관종료일 <= 예약가능 종료일 => 대관종료일
			if(rental.getRentPeriodEdate().compareTo(applyEndDateStr) <= 0){
				possibleEndDate = rental.getRentPeriodEdate();;
			}else{
				//대관종료일 > 예약가능 종료일 => 예약가능 종료일
				possibleEndDate = applyEndDateStr;
			}
		}
		//======================================================================
			
			
		for (RentalCalendarDay day : days) {
			
			String dateStr = String.format("%04d", day.getYear()) + "-" + String.format("%02d", day.getMonth()) + "-" + String.format("%02d", day.getDay());
			
			//1. 대관 기간이내 이면서 예약가능 날짜 이내인지 체크
			if(dateStr.compareTo(possibleStartDate) < 0 || dateStr.compareTo(possibleEndDate) > 0 ){
				day.setPossibleChk(false);
			}
			
			//2. 가능요일인지 확인
			String dayStr = AsaproUtils.getDayOfWeekText(day.getDayOfWeek());
			if(possibleDayOfWeekList != null && !possibleDayOfWeekList.isEmpty()){
				if(!possibleDayOfWeekList.contains(dayStr) ){
					day.setPossibleChk(false);
				}
			}
			
			//3. 예약불가일 목록에 있는지 확인
			if(impossibleDateList != null && !impossibleDateList.isEmpty()){
				if(impossibleDateList.contains(dateStr) ){
					day.setPossibleChk(false);
				}
			}
			
			//4. 이미 예약된 날짜, 또는 시간인지 확인
			List<String> reservTimeSumList = new ArrayList<String>();	//신청정보의 모든 신청시간, 닫음처리한 모든 시간을 담는다.
			//List<String> possibleTimeList = new ArrayList<String>();	//최종 신청 가능한 시간목록
			if(reservList != null && !reservList.isEmpty() ){
				for (RentalReservationInfo rentalReservationInfo : reservList) {
					if(dateStr.equals(rentalReservationInfo.getReservDate()) ){	//같은 날짜의 신청정보만 시간확인

						String[] reservTimeArray = null;
						if(StringUtils.isNotBlank(rentalReservationInfo.getReservTime()) ){
							reservTimeArray = rentalReservationInfo.getReservTime().split(",");
						}
						for (String string : reservTimeArray) {
							reservTimeSumList.add(string);//신청정보의 모든 신청시간을 담는다.
						}
					}
				}
			}
			//5. 닫음처리한 날장, 시간인지 확인
			if(rentalReservCloseTimeList != null && !rentalReservCloseTimeList.isEmpty() ){
				for (RentalReservCloseTime rentalReservCloseTime : rentalReservCloseTimeList) {
					if(dateStr.equals(rentalReservCloseTime.getReservDate()) ){	//같은 날짜의 닫음처리 시간확인
						reservTimeSumList.add(rentalReservCloseTime.getReservCloseTime());//닫음처리의 시간을 담는다.
					}
				}
			}
			
			
			//
			//요일에 따라 가능 시간목록 선택
			if("Sat".equals(dayStr) ){
				configTermList = configTermListSat;
			} else if("Sun".equals(dayStr) ){
				configTermList = configTermListSun;
			} else {
				configTermList = configTermListWeekday;
			}
			
			//신청정보에서 추출한 신청딘 시간의 목록에서 설정정보에 설정된 대관시간이 없으면 가능한것으로 목록에 담는다.
			for(String term : configTermList){
				if(reservTimeSumList != null && !reservTimeSumList.isEmpty() ){
					if(!reservTimeSumList.contains(term) ){
						day.getPossibleTime().add(term);	//최종 신청 가능한 시간목록
					}
				}else{
					day.getPossibleTime().add(term);	//최종 신청 가능한 시간목록
				}
			}
			
			day.setConfigTermList(configTermList);
			
//			if(day.getPossibleTime() == null || day.getPossibleTime().isEmpty() ){//가능 시간이 없으면
//				day.setPossibleChk(false);
//			}
			
		}
		
			
	}

	/**
	 * @return the calYear
	 */
	public Integer getCalYear() {
		return calYear;
	}

	/**
	 * @param calYear the calYear to set
	 */
	public void setCalYear(Integer calYear) {
		this.calYear = calYear;
	}

	/**
	 * @return the calMonth
	 */
	public Integer getCalMonth() {
		return calMonth;
	}

	/**
	 * @param calMonth the calMonth to set
	 */
	public void setCalMonth(Integer calMonth) {
		this.calMonth = calMonth;
	}

	/**
	 * @return the days
	 */
	public List<RentalCalendarDay> getDays() {
		return days;
	}

	/**
	 * @param days the days to set
	 */
	public void setDays(List<RentalCalendarDay> days) {
		this.days = days;
	}

	/**
	 * @return the impossibleDateList
	 */
	public List<String> getImpossibleDateList() {
		return impossibleDateList;
	}

	/**
	 * @param impossibleDateList the impossibleDateList to set
	 */
	public void setImpossibleDateList(List<String> impossibleDateList) {
		this.impossibleDateList = impossibleDateList;
	}

	/**
	 * @return the possibleDayOfWeekList
	 */
	public List<String> getPossibleDayOfWeekList() {
		return possibleDayOfWeekList;
	}

	/**
	 * @param possibleDayOfWeekList the possibleDayOfWeekList to set
	 */
	public void setPossibleDayOfWeekList(List<String> possibleDayOfWeekList) {
		this.possibleDayOfWeekList = possibleDayOfWeekList;
	}

	/**
	 * @return the rental
	 */
	public Rental getRental() {
		return rental;
	}

	/**
	 * @param rental the rental to set
	 */
	public void setRental(Rental rental) {
		this.rental = rental;
	}

	/**
	 * @return the reservList
	 */
	public List<RentalReservationInfo> getReservList() {
		return reservList;
	}

	/**
	 * @param reservList the reservList to set
	 */
	public void setReservList(List<RentalReservationInfo> reservList) {
		this.reservList = reservList;
	}

	/**
	 * @return the rentalReservationSearch
	 */
	public RentalReservationSearch getRentalReservationSearch() {
		return rentalReservationSearch;
	}

	/**
	 * @param rentalReservationSearch the rentalReservationSearch to set
	 */
	public void setRentalReservationSearch(RentalReservationSearch rentalReservationSearch) {
		this.rentalReservationSearch = rentalReservationSearch;
	}

	/**
	 * @return the configTermList
	 */
	public List<String> getConfigTermList() {
		return configTermList;
	}

	/**
	 * @param configTermList the configTermList to set
	 */
	public void setConfigTermList(List<String> configTermList) {
		this.configTermList = configTermList;
	}

	/**
	 * @return the configTermListWeekday
	 */
	public List<String> getConfigTermListWeekday() {
		return configTermListWeekday;
	}

	/**
	 * @param configTermListWeekday the configTermListWeekday to set
	 */
	public void setConfigTermListWeekday(List<String> configTermListWeekday) {
		this.configTermListWeekday = configTermListWeekday;
	}

	/**
	 * @return the configTermListSat
	 */
	public List<String> getConfigTermListSat() {
		return configTermListSat;
	}

	/**
	 * @param configTermListSat the configTermListSat to set
	 */
	public void setConfigTermListSat(List<String> configTermListSat) {
		this.configTermListSat = configTermListSat;
	}

	/**
	 * @return the configTermListSun
	 */
	public List<String> getConfigTermListSun() {
		return configTermListSun;
	}

	/**
	 * @param configTermListSun the configTermListSun to set
	 */
	public void setConfigTermListSun(List<String> configTermListSun) {
		this.configTermListSun = configTermListSun;
	}
	
	
}
