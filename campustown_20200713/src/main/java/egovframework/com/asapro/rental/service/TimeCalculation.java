/**
 * 
 */
package egovframework.com.asapro.rental.service;

import java.util.List;


import egovframework.com.asapro.site.service.MultiSiteVO;

/**
 * 예약가능시간대와 소요시간 VO
 * @author yckim
 * @since 2019. 8. 12.
 *
 */
public class TimeCalculation {

	private String time;	//가능시간
	private int timeGap;	//소요시간
	private String timeStatus;	//예약가능상태
	
	/**
	 * @return the time
	 */
	public String getTime() {
		return time;
	}
	/**
	 * @param time the time to set
	 */
	public void setTime(String time) {
		this.time = time;
	}
	/**
	 * @return the timeGap
	 */
	public int getTimeGap() {
		return timeGap;
	}
	/**
	 * @param timeGap the timeGap to set
	 */
	public void setTimeGap(int timeGap) {
		this.timeGap = timeGap;
	}
	/**
	 * @return the timeStatus
	 */
	public String getTimeStatus() {
		return timeStatus;
	}
	/**
	 * @param timeStatus the timeStatus to set
	 */
	public void setTimeStatus(String timeStatus) {
		this.timeStatus = timeStatus;
	}
	
	
}
