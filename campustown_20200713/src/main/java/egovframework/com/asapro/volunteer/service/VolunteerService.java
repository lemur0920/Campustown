/**
 * 
 */
package egovframework.com.asapro.volunteer.service;

import java.util.List;
import java.util.Map;


/**
 * 자원봉사 관리 서비스
 * @author yckim
 * @since 2018. 12. 12.
 *
 */
public interface VolunteerService {

	//=============================================================================================================================
	//==========================================  신청정보   ================================================================
	//=============================================================================================================================
	
	/**
	 * 자원봉사 신청정보를 목록을 조회한다.
	 * @param volunteerReservationSearch
	 * @return 신청정보목록
	 */
	public List<VolunteerReservation> getVolunteerReservationList(VolunteerReservationSearch volunteerReservationSearch);

	/**
	 * 자원봉사 신청정보 토탈을 조회한다.
	 * @param volunteerReservationSearch
	 * @return 신청정보 토탈
	 */
	public int getVolunteerReservationListTotal(VolunteerReservationSearch volunteerReservationSearch);

	/**
	 * 자원봉사 신청정보를 등록한다.
	 * @param volunteerReservationForm
	 * @return 추가결과
	 */
	public int insertVolunteerReservation(VolunteerReservation volunteerReservationForm);

	/**
	 * 자원봉사 신청정보를 조회한다.
	 * @param volunteerReservationForm
	 * @return 자원봉사 신청정보
	 */
	public VolunteerReservation getVolunteerReservation(VolunteerReservation volunteerReservationForm);

	/**
	 * 자원봉사 신청정보를 수정한다.
	 * @param volunteerReservationForm
	 * @return 수정결과
	 */
	public int updateVolunteerReservation(VolunteerReservation volunteerReservationForm);

	/**
	 * 자원봉사 신청정보를 삭제한다.
	 * @param volunteerReservation
	 * @return 삭제결과
	 */
	public int deleteVolunteerReservation(VolunteerReservation volunteerReservation);
	
	/**
	 * 자원봉사 신청정보를 삭제한다.
	 * @param volunteerReservationList
	 * @return 삭제결과
	 */
	public int deleteVolunteerReservation(List<VolunteerReservation> volunteerReservationList);
	
	
}
