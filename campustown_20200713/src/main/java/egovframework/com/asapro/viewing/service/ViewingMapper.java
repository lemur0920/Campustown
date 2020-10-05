/**
 * 
 */
package egovframework.com.asapro.viewing.service;

import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * 관람 관리 SQL 매퍼
 * @author yckim
 * @since 2018. 12. 14.
 *
 */
@Mapper
public interface ViewingMapper {

	/**
	 * 관람 신청정보를 목록을 조회한다.
	 * @param viewingReservationSearch
	 * @return 신청정보 목록
	 */
	public List<ViewingReservation> selectViewingReservationList(ViewingReservationSearch viewingReservationSearch);

	/**
	 * 관람 신청정보 토탈을 조회한다.
	 * @param viewingReservationSearch
	 * @return 신청정보 토탈
	 */
	public int selectViewingReservationListTotal(ViewingReservationSearch viewingReservationSearch);

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
	public ViewingReservation selectViewingReservation(ViewingReservation viewingReservationForm);

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
	
	

}
