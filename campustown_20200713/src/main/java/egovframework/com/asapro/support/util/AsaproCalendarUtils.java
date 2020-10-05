/**
 * 
 */
package egovframework.com.asapro.support.util;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * 달력유틸
 * @author yckim
 * @since 2015. 5. 15.
 * @update 2015. 6. 26 이전달 다음달 부분이 이번달을 포함하고 있지 않으면 출력하지 않도록 처리
 */
public class AsaproCalendarUtils {
	
	/**
	 * 지정된 년월의 달력데이터를 반환한다.
	 * - year, month, day, day_of_week 
	 * @param year
	 * @param month
	 * @return
	 */
	public static List<Map<String, Integer>> getMonthCalendar(int year, int month){
		
		List<Map<String, Integer>> days = new ArrayList<Map<String,Integer>>();
		
		int calYear = year;
		int calMonth = month - 1;//Calendar API 에서 월은 1작게 표시됨 즉, 0이 1월임
		
		//지금 보여줄 달
		Calendar currentMonthCal = Calendar.getInstance(Locale.KOREA);
		currentMonthCal.set(Calendar.YEAR, calYear);
		currentMonthCal.set(Calendar.MONTH, calMonth);
		currentMonthCal.set(Calendar.DAY_OF_MONTH, 1);
		
		//이전달
		Calendar lastMonthCal = Calendar.getInstance(Locale.KOREA);
		lastMonthCal.set(Calendar.YEAR, calYear);
		lastMonthCal.set(Calendar.MONTH, calMonth);
		lastMonthCal.set(Calendar.DAY_OF_MONTH, 1);
		lastMonthCal.add(Calendar.MONTH, -1);
		
		//다음달
		Calendar nextMonthCal = Calendar.getInstance(Locale.KOREA);
		nextMonthCal.set(Calendar.YEAR, calYear);
		nextMonthCal.set(Calendar.MONTH, calMonth);
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

		//달력에 들어갈 날들을 채우자
		Map<String, Integer> day = null;
		
		//달력에 표시될 지난달 끝부분
		int dayOfWeek = 1;
		int startDayOfLastMonth = lastDayOfLastMonth;
		if( dayOfWeekStartDay == 1 ){
			startDayOfLastMonth = startDayOfLastMonth - 6;
		} else {
			startDayOfLastMonth = startDayOfLastMonth - dayOfWeekStartDay + 2;
		}
		if( lastDayOfLastMonth - startDayOfLastMonth < 6 ){
			for( int i = startDayOfLastMonth; i<= lastDayOfLastMonth ; i++ ){
				day = new HashMap<String, Integer>();
				day.put("year", lastMonthCal.get(Calendar.YEAR));
				day.put("month", lastMonthCal.get(Calendar.MONTH) + 1);
				day.put("day", i);
				day.put("dayOfWeek", dayOfWeek++);
				days.add(day);
			}
		}
		
		//이번달부분
		for( int i = 1; i<= lastDayOfCurrentMonth; i++ ){
			day = new HashMap<String, Integer>();
			day.put("year", currentMonthCal.get(Calendar.YEAR));
			day.put("month", currentMonthCal.get(Calendar.MONTH) + 1);
			day.put("day", i);
			dayOfWeek = dayOfWeek % 7 == 0 ? 7 : dayOfWeek % 7;
			day.put("dayOfWeek", dayOfWeek++);
			days.add(day);
		}
		
		//달력에 표시될 다음달 시작부분
		int lastDayOfNextMonth = 1;
		if( dayOfWeekLastDay == 7 ){
			lastDayOfNextMonth = 7;
		} else {
			lastDayOfNextMonth = 7 - dayOfWeekLastDay;
		}
		if( lastDayOfNextMonth < 7 ){
			for( int i = 1; i<= lastDayOfNextMonth; i++ ){
				day = new HashMap<String, Integer>();
				day.put("year", nextMonthCal.get(Calendar.YEAR));
				day.put("month", nextMonthCal.get(Calendar.MONTH) + 1);
				day.put("day", i);
				dayOfWeek = dayOfWeek % 7 == 0 ? 7 : dayOfWeek % 7;
				day.put("dayOfWeek", dayOfWeek++);
				days.add(day);
			}
		}
		
		return days;
	}
}
