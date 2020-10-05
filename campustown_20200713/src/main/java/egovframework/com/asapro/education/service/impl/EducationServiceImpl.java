/**
 * 
 */
package egovframework.com.asapro.education.service.impl;

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
import egovframework.com.asapro.education.service.EducationMapper;
import egovframework.com.asapro.education.service.EducationReservation;
import egovframework.com.asapro.education.service.EducationReservationSearch;
import egovframework.com.asapro.education.service.EducationService;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

/**
 * 교육프로그램 관리 서비스 구현
 * @author yckim
 * @since 2018. 12. 17.
 *
 */
@Service
public class EducationServiceImpl extends EgovAbstractServiceImpl implements EducationService{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(EducationServiceImpl.class);
	
	@Autowired
	private EducationMapper educationMapper;

	
	//=============================================================================================================================
	//==========================================  신청정보   ================================================================
	//=============================================================================================================================

	/**
	 * 교육프로그램 신청정보를 목록을 조회한다.
	 */
	@Override
	public List<EducationReservation> getEducationReservationList(
			EducationReservationSearch educationReservationSearch) {
		return educationMapper.selectEducationReservationList(educationReservationSearch);
	}

	/**
	 * 교육프로그램 신청정보 토탈을 조회한다.
	 */
	@Override
	public int getEducationReservationListTotal(EducationReservationSearch educationReservationSearch) {
		return educationMapper.selectEducationReservationListTotal(educationReservationSearch);
	}

	/**
	 * 교육프로그램 신청정보를 등록한다.
	 */
	@Override
	public int insertEducationReservation(EducationReservation educationReservationForm) {
		return educationMapper.insertEducationReservation(educationReservationForm);
	}

	/**
	 * 교육프로그램 신청정보를 조회한다.
	 */
	@Override
	public EducationReservation getEducationReservation(EducationReservation educationReservationForm) {
		return educationMapper.selectEducationReservation(educationReservationForm);
	}

	/**
	 * 교육프로그램 신청정보를 수정한다.
	 */
	@Override
	public int updateEducationReservation(EducationReservation educationReservationForm) {
		return educationMapper.updateEducationReservation(educationReservationForm);
	}

	/**
	 * 교육프로그램 신청정보를 삭제한다.
	 */
	@Override
	public int deleteEducationReservation(EducationReservation educationReservation) {
		int result = educationMapper.deleteEducationReservation(educationReservation);
		return result;
	}

	/**
	 * 교육프로그램 신청정보를 삭제한다.
	 */
	@Override
	public int deleteEducationReservation(List<EducationReservation> educationReservationList) {
		int deleted = 0;
		for( EducationReservation educationReservation : educationReservationList ){
			deleted += this.deleteEducationReservation(educationReservation);
		}
		return deleted;
	}
	
	

}
