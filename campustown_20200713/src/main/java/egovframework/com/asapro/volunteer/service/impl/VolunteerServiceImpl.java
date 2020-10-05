/**
 * 
 */
package egovframework.com.asapro.volunteer.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import egovframework.com.asapro.support.Constant;
import egovframework.com.asapro.support.util.AsaproUtils;
import egovframework.com.asapro.support.util.EtcUtil;
import egovframework.com.asapro.volunteer.service.VolunteerMapper;
import egovframework.com.asapro.volunteer.service.VolunteerReservation;
import egovframework.com.asapro.volunteer.service.VolunteerReservationSearch;
import egovframework.com.asapro.volunteer.service.VolunteerService;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

/**
 * 자원봉사 관리 서비스 구현
 * @author yckim
 * @since 2018. 12. 12.
 *
 */
@Service
public class VolunteerServiceImpl extends EgovAbstractServiceImpl implements VolunteerService{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(VolunteerServiceImpl.class);
	
	@Autowired
	private VolunteerMapper volunteerMapper;

	
	//=============================================================================================================================
	//==========================================  신청정보   ================================================================
	//=============================================================================================================================

	/**
	 * 자원봉사 신청정보를 목록을 조회한다.
	 */
	@Override
	public List<VolunteerReservation> getVolunteerReservationList(
			VolunteerReservationSearch volunteerReservationSearch) {
		return volunteerMapper.selectVolunteerReservationList(volunteerReservationSearch);
	}

	/**
	 * 자원봉사 신청정보 토탈을 조회한다.
	 */
	@Override
	public int getVolunteerReservationListTotal(VolunteerReservationSearch volunteerReservationSearch) {
		return volunteerMapper.selectVolunteerReservationListTotal(volunteerReservationSearch);
	}

	/**
	 * 자원봉사 신청정보를 등록한다.
	 */
	@Override
	public int insertVolunteerReservation(VolunteerReservation volunteerReservationForm) {
		return volunteerMapper.insertVolunteerReservation(volunteerReservationForm);
	}

	/**
	 * 자원봉사 신청정보를 조회한다.
	 */
	@Override
	public VolunteerReservation getVolunteerReservation(VolunteerReservation volunteerReservationForm) {
		return volunteerMapper.selectVolunteerReservation(volunteerReservationForm);
	}

	/**
	 * 자원봉사 신청정보를 수정한다.
	 */
	@Override
	public int updateVolunteerReservation(VolunteerReservation volunteerReservationForm) {
		return volunteerMapper.updateVolunteerReservation(volunteerReservationForm);
	}

	/**
	 * 자원봉사 신청정보를 삭제한다.
	 */
	@Override
	public int deleteVolunteerReservation(VolunteerReservation volunteerReservation) {
		int result = volunteerMapper.deleteVolunteerReservation(volunteerReservation);
		return result;
	}

	/**
	 * 자원봉사 신청정보를 삭제한다.
	 */
	@Override
	public int deleteVolunteerReservation(List<VolunteerReservation> volunteerReservationList) {
		int deleted = 0;
		for( VolunteerReservation volunteerReservation : volunteerReservationList ){
			deleted += this.deleteVolunteerReservation(volunteerReservation);
		}
		return deleted;
	}
	
	

}
