/**
 * 
 */
package egovframework.com.asapro.rental.service;

import egovframework.com.asapro.site.service.MultiSiteVO;

/**
 * 닫힘 시간 VO
 * @author yckim
 * @since 2019. 8. 14.
 *
 */
public class RentalReservCloseTime extends MultiSiteVO{

	private Integer rentId;	//대관아이디
	private String reservDate;	//예약일자
	private String reservCloseTime;	//예약시간
	
	//검색용도
	private String reservStartDate;	//시간날짜
	private String reservEndDate;	//종료날짜
	
	/**
	 * @return the rentId
	 */
	public Integer getRentId() {
		return rentId;
	}
	/**
	 * @param rentId the rentId to set
	 */
	public void setRentId(Integer rentId) {
		this.rentId = rentId;
	}
	/**
	 * @return the reservDate
	 */
	public String getReservDate() {
		return reservDate;
	}
	/**
	 * @param reservDate the reservDate to set
	 */
	public void setReservDate(String reservDate) {
		this.reservDate = reservDate;
	}
	/**
	 * @return the reservCloseTime
	 */
	public String getReservCloseTime() {
		return reservCloseTime;
	}
	/**
	 * @param reservCloseTime the reservCloseTime to set
	 */
	public void setReservCloseTime(String reservCloseTime) {
		this.reservCloseTime = reservCloseTime;
	}
	/**
	 * @return the reservStartDate
	 */
	public String getReservStartDate() {
		return reservStartDate;
	}
	/**
	 * @param reservStartDate the reservStartDate to set
	 */
	public void setReservStartDate(String reservStartDate) {
		this.reservStartDate = reservStartDate;
	}
	/**
	 * @return the reservEndDate
	 */
	public String getReservEndDate() {
		return reservEndDate;
	}
	/**
	 * @param reservEndDate the reservEndDate to set
	 */
	public void setReservEndDate(String reservEndDate) {
		this.reservEndDate = reservEndDate;
	}
	
}
