/**
 * 
 */
package egovframework.com.asapro.viewing.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import egovframework.com.asapro.fileinfo.service.FileInfo;

/**
 * 관람 달력 날짜 VO
 * @author yckim
 * @since 2018. 12. 24.
 *
 */
public class ViewingCalendarDay {
	
	private String year;	//년
	private String month;	//월
	private String day;	//일
	private Integer dayOfWeek;	//요일
	private String dayOfWeekText;	//요일한글
	private String date;	//년-월-일
	private List<ViewingReservation> viewingReservationList = new ArrayList<ViewingReservation>();	//관람 신청정보 목록

	/**
	 * @return the year
	 */
	public String getYear() {
		return year;
	}
	/**
	 * @param year the year to set
	 */
	public void setYear(String year) {
		this.year = year;
	}
	/**
	 * @return the month
	 */
	public String getMonth() {
		return month;
	}
	/**
	 * @param month the month to set
	 */
	public void setMonth(String month) {
		this.month = month;
	}
	/**
	 * @return the day
	 */
	public String getDay() {
		return day;
	}
	/**
	 * @param day the day to set
	 */
	public void setDay(String day) {
		this.day = day;
	}
	
	/**
	 * @return the dayOfWeek
	 */
	public Integer getDayOfWeek() {
		return dayOfWeek;
	}
	/**
	 * @param dayOfWeek the dayOfWeek to set
	 */
	public void setDayOfWeek(Integer dayOfWeek) {
		this.dayOfWeek = dayOfWeek;
	}
	/**
	 * @return the viewingReservationList
	 */
	public List<ViewingReservation> getViewingReservationList() {
		return viewingReservationList;
	}
	/**
	 * @param viewingReservationList the viewingReservationList to set
	 */
	public void setViewingReservationList(List<ViewingReservation> viewingReservationList) {
		this.viewingReservationList = viewingReservationList;
	}
	/**
	 * @return the date
	 */
	public String getDate() {
		return date;
	}
	/**
	 * @param date the date to set
	 */
	public void setDate(String date) {
		this.date = date;
	}
	/**
	 * @return the dayOfWeekText
	 */
	public String getDayOfWeekText() {
		return dayOfWeekText;
	}
	/**
	 * @param dayOfWeekText the dayOfWeekText to set
	 */
	public void setDayOfWeekText(String dayOfWeekText) {
		this.dayOfWeekText = dayOfWeekText;
	}
	
	
	
}
