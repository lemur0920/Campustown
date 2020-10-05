/**
 * 
 */
package egovframework.com.asapro.viewing.service;

import java.util.List;
import java.util.Map;


/**
 * 관람정보 관리 서비스
 * @author yckim
 * @since 2018. 12. 14.
 *
 */
public interface ViewingService {

	//=============================================================================================================================
	//==========================================  신청정보   ================================================================
	//=============================================================================================================================
	
	/**
	 * 관람 신청정보를 목록을 조회한다.
	 * @param viewingReservationSearch
	 * @return 신청정보목록
	 */
	public List<ViewingReservation> getViewingReservationList(ViewingReservationSearch viewingReservationSearch);

	/**
	 * 관람 신청정보 토탈을 조회한다.
	 * @param viewingReservationSearch
	 * @return 신청정보 토탈
	 */
	public int getViewingReservationListTotal(ViewingReservationSearch viewingReservationSearch);

	/**
	 * 관람 신청정보를 등록한다.
	 * @param viewingReservationForm
	 * @return 추가결과
	 */
	public int insertViewingReservation(ViewingReservation viewingReservationForm);

	/**
	 * 관람 신청정보를 조회한다.
	 * @param viewingReservationForm
	 * @return 관람 신청정보
	 */
	public ViewingReservation getViewingReservation(ViewingReservation viewingReservationForm);

	/**
	 * 관람 신청정보를 수정한다.
	 * @param viewingReservationForm
	 * @return 수정결과
	 */
	public int updateViewingReservation(ViewingReservation viewingReservationForm);

	/**
	 * 관람 신청정보를 삭제한다.
	 * @param viewingReservation
	 * @return 삭제결과
	 */
	public int deleteViewingReservation(ViewingReservation viewingReservation);
	
	/**
	 * 관람 신청정보를 삭제한다.
	 * @param viewingReservationList
	 * @return 삭제결과
	 */
	public int deleteViewingReservation(List<ViewingReservation> viewingReservationList);
	
	
}
