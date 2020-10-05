/**
 * 
 */
package egovframework.com.asapro.rental.service;

import java.util.List;


import egovframework.com.asapro.site.service.MultiSiteVO;

/**
 * 예약가능 날짜시간 VO
 * @author yckim
 * @since 2018. 12. 7.
 *
 */
public class PossibleDayTime extends MultiSiteVO{

	private String possibleDay;	//가능날짜
	private List<String> possibleTime;	//가능시간
	/**
	 * @return the possibleDay
	 */
	public String getPossibleDay() {
		return possibleDay;
	}
	/**
	 * @param possibleDay the possibleDay to set
	 */
	public void setPossibleDay(String possibleDay) {
		this.possibleDay = possibleDay;
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

	
	
}
