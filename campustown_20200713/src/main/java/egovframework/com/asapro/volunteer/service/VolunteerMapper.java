/**
 * 
 */
package egovframework.com.asapro.volunteer.service;

import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * 자원봉사 관리 SQL 매퍼
 * @author yckim
 * @since 2018. 12. 12.
 *
 */
@Mapper
public interface VolunteerMapper {

	/**
	 * 자원봉사 신청정보를 목록을 조회한다.
	 * @param volunteerReservationSearch
	 * @return 신청정보 목록
	 */
	public List<VolunteerReservation> selectVolunteerReservationList(VolunteerReservationSearch volunteerReservationSearch);

	/**
	 * 자원봉사 신청정보 토탈을 조회한다.
	 * @param volunteerReservationSearch
	 * @return 신청정보 토탈
	 */
	public int selectVolunteerReservationListTotal(VolunteerReservationSearch volunteerReservationSearch);

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
	public VolunteerReservation selectVolunteerReservation(VolunteerReservation volunteerReservationForm);

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
	
	

}
