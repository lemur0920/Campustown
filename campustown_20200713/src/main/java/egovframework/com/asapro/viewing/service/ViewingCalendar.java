/**
 * 
 */
package egovframework.com.asapro.viewing.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Case;
import org.springframework.beans.factory.annotation.Autowired;

import egovframework.com.asapro.support.exception.AsaproException;
import egovframework.com.asapro.support.util.AsaproUtils;
import egovframework.com.asapro.support.util.EtcUtil;

/**
 * 관람 달력
 * @author yckim
 * @since 2018. 12. 24.
 *
 */
public class ViewingCalendar {
	
	private String calYear;	//해당 년도
	private String calMonth;	//해당 월
	private Integer calWeekOfMonth;	//해당 월의 주차
	private String calWeekOfMonthText;	//해당 월의 주차 한글
	private Integer calWeekOfYear;	//해당 년의 주차
	private String calPreYear;	//  전주 연도
	private Integer calPreWeekOfYear;	//  전주 연도의 주차
	private String calNextYear;	//  다음주 연도
	private Integer calNextWeekOfYear;	//  다음주 연도의 주차
	
	private List<ViewingCalendarDay> days; //해당월의 날짜와 날짜별 데이터

	
	/**
	 * default constructor
	 */
	public ViewingCalendar(){
		throw new AsaproException("Use ViewingCalendar(List<RentalReservationInfo> reservList, ViewingReservationSearch viewingReservationSearch) instead...");
	}
	
	/**
	 * 달력 생성자
	 * @param reservlist
	 * @param viewingReservationSearch
	 * @throws ParseException 
	 */
	public ViewingCalendar(ViewingService viewingService, ViewingReservationSearch viewingReservationSearch) throws ParseException{
		
		//해당 년도
		this.calYear = viewingReservationSearch.getReservYear();
		//해당 월
		//calMonth = Integer.parseInt(viewingReservationSearch.getReservMonth());
		//해당 월의 주차
		//calWeekOfMonth = Integer.parseInt(viewingReservationSearch.getReservWeekOfMonth());
		//해당 년의 주차
		this.calWeekOfYear = viewingReservationSearch.getReservWeekOfYear();
		

		//============================================================================================================		
		
		int year = Integer.parseInt(viewingReservationSearch.getReservYear());
		//int month = Integer.parseInt(viewingReservationSearch.getReservMonth()) - 1;
		//int weekOfMonth = Integer.parseInt(viewingReservationSearch.getReservWeekOfMonth());
		int weekOfYear = viewingReservationSearch.getReservWeekOfYear();
		String currentSunday = null;
		String currentSaturday = null;
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		//지금 보여줄 주
		Calendar currentWeekCal = Calendar.getInstance(Locale.KOREA);
		currentWeekCal.set(Calendar.YEAR, year);
		currentWeekCal.set(Calendar.WEEK_OF_YEAR, weekOfYear);
		
		//일요일 날짜
		currentWeekCal.set(Calendar.DAY_OF_WEEK,Calendar.SUNDAY);
		currentSunday = sdf.format(currentWeekCal.getTime());
		//토요일 날짜
		currentWeekCal.set(Calendar.DAY_OF_WEEK,Calendar.SATURDAY);
		currentSaturday = sdf.format(currentWeekCal.getTime());
		
		//이전 주
		Calendar preWeekCal = Calendar.getInstance(Locale.KOREA);
		preWeekCal.set(Calendar.YEAR, year);
		preWeekCal.set(Calendar.WEEK_OF_YEAR, weekOfYear - 1);
		
		//다음 주
		Calendar nextWeekCal = Calendar.getInstance(Locale.KOREA);
		nextWeekCal.set(Calendar.YEAR, year);
		nextWeekCal.set(Calendar.WEEK_OF_YEAR, weekOfYear + 1);
		
		
		//달력에 들어갈 날들을 채운다
		days = new ArrayList<ViewingCalendarDay>();
		ViewingCalendarDay viewingCalendarDay = null;
		
				
		//이번주부분
		for( int i = Calendar.SUNDAY; i<= Calendar.SATURDAY; i++ ){
			currentWeekCal.set(Calendar.DAY_OF_WEEK,i);
			viewingCalendarDay = new ViewingCalendarDay();
			viewingCalendarDay.setYear(String.format("%04d", currentWeekCal.get(Calendar.YEAR)) );
			viewingCalendarDay.setMonth(String.format("%02d", currentWeekCal.get(Calendar.MONTH) + 1));
			viewingCalendarDay.setDay(String.format("%02d", currentWeekCal.get(Calendar.DAY_OF_MONTH)) );
			viewingCalendarDay.setDayOfWeek(i);
			viewingCalendarDay.setDayOfWeekText(AsaproUtils.getDayOfWeekKorText(i));
			viewingCalendarDay.setDate(sdf.format(currentWeekCal.getTime()));
			days.add(viewingCalendarDay);
		}		
				
		
		viewingReservationSearch.setReservStartDate(currentSunday);
		viewingReservationSearch.setReservEndDate(currentSaturday);
		List<ViewingReservation> list = viewingService.getViewingReservationList(viewingReservationSearch);

		for (ViewingCalendarDay day : days) {
			for (ViewingReservation viewingReservation : list) {
				if(day.getDate().equals(viewingReservation.getReservDate()) ){
					day.getViewingReservationList().add(viewingReservation);
				}
			}
		}
		
		this.calMonth = String.format("%02d", currentWeekCal.get(Calendar.MONTH) + 1);
		this.calWeekOfMonth = currentWeekCal.get(Calendar.WEEK_OF_MONTH);
		this.calWeekOfMonthText = this.weekOfMonthToText(currentWeekCal.get(Calendar.WEEK_OF_MONTH));
		this.calPreYear = String.format("%04d", preWeekCal.get(Calendar.YEAR)) ;
		this.calPreWeekOfYear = preWeekCal.get(Calendar.WEEK_OF_YEAR);
		this.calNextYear = String.format("%04d", nextWeekCal.get(Calendar.YEAR));
		this.calNextWeekOfYear = nextWeekCal.get(Calendar.WEEK_OF_YEAR);
	}

	public String weekOfMonthToText(int weekOfMonth){
		String weekOfMonthText = "";
		
		switch (weekOfMonth) {
		case 1:
			weekOfMonthText = "첫째주";
			break;
		case 2:
			weekOfMonthText = "두째주";
			break;
		case 3:
			weekOfMonthText = "셋째주";
			break;
		case 4:
			weekOfMonthText = "넷째주";
			break;
		case 5:
			weekOfMonthText = "다섯째";
			break;
		case 6:
			weekOfMonthText = "여섯째";
			break;

		default:
			break;
		}
		
		return weekOfMonthText;
	}
	/**
	 * @return the calYear
	 */
	public String getCalYear() {
		return calYear;
	}

	/**
	 * @param calYear the calYear to set
	 */
	public void setCalYear(String calYear) {
		this.calYear = calYear;
	}

	/**
	 * @return the calMonth
	 */
	public String getCalMonth() {
		return calMonth;
	}

	/**
	 * @param calMonth the calMonth to set
	 */
	public void setCalMonth(String calMonth) {
		this.calMonth = calMonth;
	}

	/**
	 * @return the calWeekOfMonth
	 */
	public Integer getCalWeekOfMonth() {
		return calWeekOfMonth;
	}

	/**
	 * @param calWeekOfMonth the calWeekOfMonth to set
	 */
	public void setCalWeekOfMonth(Integer calWeekOfMonth) {
		this.calWeekOfMonth = calWeekOfMonth;
	}

	/**
	 * @return the days
	 */
	public List<ViewingCalendarDay> getDays() {
		return days;
	}

	/**
	 * @param days the days to set
	 */
	public void setDays(List<ViewingCalendarDay> days) {
		this.days = days;
	}

	/**
	 * @return the calWeekOfYear
	 */
	public Integer getCalWeekOfYear() {
		return calWeekOfYear;
	}

	/**
	 * @param calWeekOfYear the calWeekOfYear to set
	 */
	public void setCalWeekOfYear(Integer calWeekOfYear) {
		this.calWeekOfYear = calWeekOfYear;
	}

	/**
	 * @return the calPreYear
	 */
	public String getCalPreYear() {
		return calPreYear;
	}

	/**
	 * @param calPreYear the calPreYear to set
	 */
	public void setCalPreYear(String calPreYear) {
		this.calPreYear = calPreYear;
	}

	/**
	 * @return the calPreWeekOfYear
	 */
	public Integer getCalPreWeekOfYear() {
		return calPreWeekOfYear;
	}

	/**
	 * @param calPreWeekOfYear the calPreWeekOfYear to set
	 */
	public void setCalPreWeekOfYear(Integer calPreWeekOfYear) {
		this.calPreWeekOfYear = calPreWeekOfYear;
	}

	/**
	 * @return the calNextYear
	 */
	public String getCalNextYear() {
		return calNextYear;
	}

	/**
	 * @param calNextYear the calNextYear to set
	 */
	public void setCalNextYear(String calNextYear) {
		this.calNextYear = calNextYear;
	}

	/**
	 * @return the calNextWeekOfYear
	 */
	public Integer getCalNextWeekOfYear() {
		return calNextWeekOfYear;
	}

	/**
	 * @param calNextWeekOfYear the calNextWeekOfYear to set
	 */
	public void setCalNextWeekOfYear(Integer calNextWeekOfYear) {
		this.calNextWeekOfYear = calNextWeekOfYear;
	}

	/**
	 * @return the calWeekOfMonthText
	 */
	public String getCalWeekOfMonthText() {
		return calWeekOfMonthText;
	}

	/**
	 * @param calWeekOfMonthText the calWeekOfMonthText to set
	 */
	public void setCalWeekOfMonthText(String calWeekOfMonthText) {
		this.calWeekOfMonthText = calWeekOfMonthText;
	}

	
	
}
