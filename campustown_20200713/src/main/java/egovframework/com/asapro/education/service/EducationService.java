/**
 * 
 */
package egovframework.com.asapro.education.service;

import java.util.List;
import java.util.Map;


/**
 * 교육프로그램 관리 서비스
 * @author yckim
 * @since 2018. 12. 17.
 *
 */
public interface EducationService {

	//=============================================================================================================================
	//==========================================  신청정보   ================================================================
	//=============================================================================================================================
	
	/**
	 * 교육프로그램 신청정보를 목록을 조회한다.
	 * @param educationReservationSearch
	 * @return 신청정보목록
	 */
	public List<EducationReservation> getEducationReservationList(EducationReservationSearch educationReservationSearch);

	/**
	 * 교육프로그램 신청정보 토탈을 조회한다.
	 * @param educationReservationSearch
	 * @return 신청정보 토탈
	 */
	public int getEducationReservationListTotal(EducationReservationSearch educationReservationSearch);

	/**
	 * 교육프로그램 신청정보를 등록한다.
	 * @param educationReservationForm
	 * @return 추가결과
	 */
	public int insertEducationReservation(EducationReservation educationReservationForm);

	/**
	 * 교육프로그램 신청정보를 조회한다.
	 * @param educationReservationForm
	 * @return 교육프로그램 신청정보
	 */
	public EducationReservation getEducationReservation(EducationReservation educationReservationForm);

	/**
	 * 교육프로그램 신청정보를 수정한다.
	 * @param educationReservationForm
	 * @return 수정결과
	 */
	public int updateEducationReservation(EducationReservation educationReservationForm);

	/**
	 * 교육프로그램 신청정보를 삭제한다.
	 * @param educationReservation
	 * @return 삭제결과
	 */
	public int deleteEducationReservation(EducationReservation educationReservation);
	
	/**
	 * 교육프로그램 신청정보를 삭제한다.
	 * @param educationReservationList
	 * @return 삭제결과
	 */
	public int deleteEducationReservation(List<EducationReservation> educationReservationList);
	
	
}
