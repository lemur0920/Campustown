/**
 * 
 */
package egovframework.com.asapro.rental.service;


/**
 * 대관/대여 예약가능 시간 VO
 * @author yckim
 * @since 2018.11.05.
 *
 */
public class RentReservTime {
	
	private String rentReservStartTime;	//시작시간
	private String rentReservEndTime;	//종료시간
	private boolean rentIsNextday = false;	//익일여부
	
	/**
	 * @return the rentReservStartTime
	 */
	public String getRentReservStartTime() {
		return rentReservStartTime;
	}
	/**
	 * @param rentReservStartTime the rentReservStartTime to set
	 */
	public void setRentReservStartTime(String rentReservStartTime) {
		this.rentReservStartTime = rentReservStartTime;
	}
	/**
	 * @return the rentReservEndTime
	 */
	public String getRentReservEndTime() {
		return rentReservEndTime;
	}
	/**
	 * @param rentReservEndTime the rentReservEndTime to set
	 */
	public void setRentReservEndTime(String rentReservEndTime) {
		this.rentReservEndTime = rentReservEndTime;
	}
	/**
	 * @return the rentIsNextday
	 */
	public boolean isRentIsNextday() {
		return rentIsNextday;
	}
	/**
	 * @param rentIsNextday the rentIsNextday to set
	 */
	public void setRentIsNextday(boolean rentIsNextday) {
		this.rentIsNextday = rentIsNextday;
	}
	
}
