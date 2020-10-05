/**
 * 
 */
package egovframework.com.asapro.rental.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import egovframework.com.asapro.fileinfo.service.FileInfo;

/**
 * 대관/대여 달력 날짜 VO
 * @author yckim
 * @since 2018. 12. 10.
 *
 */
public class RentalCalendarDay {
	
	private Integer year;	//년
	private Integer month;	//월
	private Integer day;	//일
	private int dayOfWeek;//요일
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
	private List<String> possibleTime = new ArrayList<String>();	//가능시간
	private List<String> configTermList = new ArrayList<String>();	//가능시간
	private boolean possibleChk = true;	//예약가능여부
	/**
	 * @return the year
	 */
	public Integer getYear() {
		return year;
	}
	/**
	 * @param year the year to set
	 */
	public void setYear(Integer year) {
		this.year = year;
	}
	/**
	 * @return the month
	 */
	public Integer getMonth() {
		return month;
	}
	/**
	 * @param month the month to set
	 */
	public void setMonth(Integer month) {
		this.month = month;
	}
	/**
	 * @return the day
	 */
	public Integer getDay() {
		return day;
	}
	/**
	 * @param day the day to set
	 */
	public void setDay(Integer day) {
		this.day = day;
	}
	/**
	 * @return the dayOfWeek
	 */
	public int getDayOfWeek() {
		return dayOfWeek;
	}
	/**
	 * @param dayOfWeek the dayOfWeek to set
	 */
	public void setDayOfWeek(int dayOfWeek) {
		this.dayOfWeek = dayOfWeek;
	}
	/**
	 * @return the possibleTime
	 */
	public List<String> getPossibleTime() {
		return possibleTime;
	}
	/**
	 * @param possibleTime the possibleTime to set
	 */
	public void setPossibleTime(List<String> possibleTime) {
		this.possibleTime = possibleTime;
	}
	/**
	 * @return the possibleChk
	 */
	public boolean isPossibleChk() {
		return possibleChk;
	}
	/**
	 * @param possibleChk the possibleChk to set
	 */
	public void setPossibleChk(boolean possibleChk) {
		this.possibleChk = possibleChk;
	}
	
	
}
