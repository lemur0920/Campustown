/**
 * 
 */
package egovframework.com.asapro.viewing.service.impl;

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
import egovframework.com.asapro.viewing.service.ViewingMapper;
import egovframework.com.asapro.viewing.service.ViewingReservation;
import egovframework.com.asapro.viewing.service.ViewingReservationSearch;
import egovframework.com.asapro.viewing.service.ViewingService;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

/**
 * 관람 관리 서비스 구현
 * @author yckim
 * @since 2018. 12. 14.
 *
 */
@Service
public class ViewingServiceImpl extends EgovAbstractServiceImpl implements ViewingService{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ViewingServiceImpl.class);
	
	@Autowired
	private ViewingMapper viewingMapper;

	
	//=============================================================================================================================
	//==========================================  신청정보   ================================================================
	//=============================================================================================================================

	/**
	 * 관람 신청정보를 목록을 조회한다.
	 */
	@Override
	public List<ViewingReservation> getViewingReservationList(
			ViewingReservationSearch viewingReservationSearch) {
		return viewingMapper.selectViewingReservationList(viewingReservationSearch);
	}

	/**
	 * 관람 신청정보 토탈을 조회한다.
	 */
	@Override
	public int getViewingReservationListTotal(ViewingReservationSearch viewingReservationSearch) {
		return viewingMapper.selectViewingReservationListTotal(viewingReservationSearch);
	}

	/**
	 * 관람 신청정보를 등록한다.
	 */
	@Override
	public int insertViewingReservation(ViewingReservation viewingReservationForm) {
		return viewingMapper.insertViewingReservation(viewingReservationForm);
	}

	/**
	 * 관람 신청정보를 조회한다.
	 */
	@Override
	public ViewingReservation getViewingReservation(ViewingReservation viewingReservationForm) {
		return viewingMapper.selectViewingReservation(viewingReservationForm);
	}

	/**
	 * 관람 신청정보를 수정한다.
	 */
	@Override
	public int updateViewingReservation(ViewingReservation viewingReservationForm) {
		return viewingMapper.updateViewingReservation(viewingReservationForm);
	}

	/**
	 * 관람 신청정보를 삭제한다.
	 */
	@Override
	public int deleteViewingReservation(ViewingReservation viewingReservation) {
		int result = viewingMapper.deleteViewingReservation(viewingReservation);
		return result;
	}

	/**
	 * 관람 신청정보를 삭제한다.
	 */
	@Override
	public int deleteViewingReservation(List<ViewingReservation> viewingReservationList) {
		int deleted = 0;
		for( ViewingReservation viewingReservation : viewingReservationList ){
			deleted += this.deleteViewingReservation(viewingReservation);
		}
		return deleted;
	}
	
	

}
