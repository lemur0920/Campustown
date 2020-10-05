/**
 * 
 */
package egovframework.com.asapro.education.service;

import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * 교육프로그램 관리 SQL 매퍼
 * @author yckim
 * @since 2018. 12. 17.
 *
 */
@Mapper
public interface EducationMapper {

	/**
	 * 교육프로그램 신청정보를 목록을 조회한다.
	 * @param viewingReservationSearch
	 * @return 신청정보 목록
	 */
	public List<EducationReservation> selectEducationReservationList(EducationReservationSearch viewingReservationSearch);

	/**
	 * 교육프로그램 신청정보 토탈을 조회한다.
	 * @param viewingReservationSearch
	 * @return 신청정보 토탈
	 */
	public int selectEducationReservationListTotal(EducationReservationSearch viewingReservationSearch);

	/**
	 * 교육프로그램 신청정보를 등록한다.
	 * @param viewingReservationForm
	 * @return 추가결과
	 */
	public int insertEducationReservation(EducationReservation viewingReservationForm);

	/**
	 * 교육프로그램 신청정보를 조회한다.
	 * @param viewingReservationForm
	 * @return 교육프로그램 신청정보
	 */
	public EducationReservation selectEducationReservation(EducationReservation viewingReservationForm);

	/**
	 * 교육프로그램 신청정보를 수정한다.
	 * @param viewingReservationForm
	 * @return 수정결과
	 */
	public int updateEducationReservation(EducationReservation viewingReservationForm);

	/**
	 * 교육프로그램 신청정보를 삭제한다.
	 * @param viewingReservation
	 * @return 삭제결과
	 */
	public int deleteEducationReservation(EducationReservation viewingReservation);
	
	

}
