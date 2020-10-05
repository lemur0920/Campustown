/**
 * 
 */
package egovframework.com.asapro.rental.service;

import java.util.List;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * 대관/대여 SQL 매퍼
 * @author yckim
 * @since 2018. 10. 29.
 *
 */
@Mapper
public interface RentalMapper {

	/**
	 * 대여/대관 목록을 조회한다.
	 * @param rentalSearch
	 * @return 대여/대관 목록
	 */
	public List<Rental> selectRentalList(RentalSearch rentalSearch);

	/**
	 * 대여/대관 개수를 조회한다.
	 * @param rentalSearch
	 * @return 대여/대관 개수
	 */
	public int selectRentalListTotal(RentalSearch rentalSearch);

	/**
	 * 대여/대관 를 추가한다.
	 * @param rentalForm
	 * @return 추가결과
	 */
	public int insertRental(Rental rentalForm);

	/**
	 * 대여/대관를 조회한다.
	 * @param rentalForm
	 * @return 대관/대여
	 */
	public Rental selectRental(Rental rentalForm);

	/**
	 * 대여/대관 - 기본정보를 수정한다.
	 * @param rentalForm
	 * @return 수정결과
	 */
	public int updateRentalBasic(Rental rentalForm);

	/**
	 * 대여/대관 - 신청설정정보를 수정한다.
	 * @param rentalForm
	 * @return 수정결과
	 */
	public int updateRentalApplySet(Rental rentalForm);

	/**
	 * 대여/대관을 삭제한다.
	 * @param rental
	 * @return 삭제결과
	 */
	public int deleteRental(Rental rental);
	
	/**
	 * 대여/대관을 정렬순서를 변경한다.
	 * @param rental
	 * @return 변경결과
	 */
	public int updateRentalOrder(Rental rental);

	//=============================================================================================================================
	//==========================================  신청정보   ================================================================
	//=============================================================================================================================
	
	/**
	 * 신청정보 목록을 조회한다.
	 * @param rentalReservationSearch
	 * @return 신청정보목록
	 */
	public List<RentalReservationInfo> selectRentalReservationList(RentalReservationSearch rentalReservationSearch);

	/**
	 * 신청정보 목록 갯수를 조회한다.
	 * @param rentalReservationSearch
	 * @return 신청정보 갯수
	 */
	public int selectRentalReservationListTotal(RentalReservationSearch rentalReservationSearch);
	
	/**
	 * 신청정보 목록을 신청자 아이디로 조회한다. - 대관정보 포함
	 * @param rentalReservationSearch
	 * @return 신청정보 목록
	 */
	public List<RentalReservationInfo> selectRentalReservationListByUserId(RentalReservationSearch rentalReservationSearch);

	/**
	 * 신청정보 목록 갯수를 신청자 아이디로 조회한다.
	 * @param rentalReservationSearch
	 * @return 신정정보 목록 갯수
	 */
	public int selectRentalReservationListTotalByUserId(RentalReservationSearch rentalReservationSearch);
	
	/**
	 * 신청정보를 조회한다.
	 * @param rentalReservationInfo
	 * @return 신청정보
	 */
	public RentalReservationInfo selectRentalReservation(RentalReservationInfo rentalReservationInfo);

	/**
	 * 신청정보를 등록한다.
	 * @param rentalReservationForm
	 * @return 추가결과
	 */
	public int insertRentalReservation(RentalReservationInfo rentalReservationForm);

	/**
	 * 신청정보를 수정한다.
	 * @param rentalReservationForm
	 * @return 수정결과
	 */
	public int updateRentalReservation(RentalReservationInfo rentalReservationForm);

	/**
	 * 대관이이디와 대관일자로 신청정보를 조회한다.
	 * @param rentalReservationForm
	 * @return 신청정보 목록
	 */
	public List<RentalReservationInfo> selectRentalReservationListByDate(RentalReservationInfo rentalReservationForm);

	/**
	 * 신청정보를 삭제한다.
	 * @param rentalReservationInfo
	 * @return 삭제결과
	 */
	public int deleteRentalReservationInfo(RentalReservationInfo rentalReservationInfo);

	/**
	 * 신청정보의 산청상태값을 변경한다.
	 * @param rentalReservationForm
	 * @return 변경결과
	 */
	public int updateReservStatus(RentalReservationInfo rentalReservationForm);

	//======================================================================
	
	/**
	 * 예약시간 닫힘 정보를 등록한다.
	 * @param rentalReservCloseTime
	 * @return 추가결과
	 */
	public int insertRentalReservCloseTime(RentalReservCloseTime rentalReservCloseTime);
	
	/**
	 * 예약시간 닫힘 정보를 삭제한다.
	 * @param rentalReservCloseTime
	 * @return 삭제결과
	 */
	public int deleteRentalReservCloseTime(RentalReservCloseTime rentalReservCloseTime);
	
	/**
	 * 예약시간 닫힘 정보를 조회한다.
	 * @param rentalReservCloseTime
	 * @return 예약시간 닫힘 정보
	 */
	public RentalReservCloseTime selectRentalReservCloseTime(RentalReservCloseTime rentalReservCloseTime);
	
	/**
	 * 예약시간 닫힘 정보를 목록을 조회한다.
	 * @param rentalReservCloseTime
	 * @return 목록
	 */
	public List<RentalReservCloseTime> selectRentalReservCloseTimeList(RentalReservCloseTime rentalReservCloseTime);

	
	
}
