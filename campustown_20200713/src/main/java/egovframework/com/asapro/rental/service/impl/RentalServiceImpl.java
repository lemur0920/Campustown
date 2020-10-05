/**
 * 
 */
package egovframework.com.asapro.rental.service.impl;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import egovframework.com.asapro.fileinfo.service.FileInfo;
import egovframework.com.asapro.fileinfo.service.FileInfoService;
import egovframework.com.asapro.rental.service.Rental;
import egovframework.com.asapro.rental.service.RentalMapper;
import egovframework.com.asapro.rental.service.RentalReservCloseTime;
import egovframework.com.asapro.rental.service.RentalReservationInfo;
import egovframework.com.asapro.rental.service.RentalReservationSearch;
import egovframework.com.asapro.rental.service.RentalSearch;
import egovframework.com.asapro.rental.service.RentalService;
import egovframework.com.asapro.support.util.AsaproUtils;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

/**
 * 대관/대여 서비스 구현
 * @author yckim
 * @since 2018. 10. 29.
 *
 */
@Service
public class RentalServiceImpl extends EgovAbstractServiceImpl implements RentalService {

	@Autowired
	private RentalMapper rentalMapper;
	
	@Autowired
	private FileInfoService fileInfoService;
	
	@Autowired
	private HttpServletRequest request;
	
	/**
	 * 대여/대관 목록을 조회한다.
	 */
	@Override
	public List<Rental> getRentalList(RentalSearch rentalSearch) {
		return rentalMapper.selectRentalList(rentalSearch);
	}

	/**
	 * 대여/대관 개수를 조회한다.
	 */
	@Override
	public int getRentalListTotal(RentalSearch rentalSearch) {
		return rentalMapper.selectRentalListTotal(rentalSearch);
	}

	/**
	 * 대여/대관 를 추가한다.
	 */
	@Override
	public int insertRental(Rental rentalForm) {
		return rentalMapper.insertRental(rentalForm);
	}

	/**
	 * 대여/대관 를 조회한다.
	 */
	@Override
	public Rental getRental(Rental rentalForm) {
		return rentalMapper.selectRental(rentalForm);
	}

	/**
	 * 대여/대관 - 기본정보를 수정한다.
	 */
	@Override
	public int updateRentalBasic(Rental rentalForm) {
		return rentalMapper.updateRentalBasic(rentalForm);
	}

	/**
	 * 대여/대관 - 신청설정정보를 수정한다.
	 */
	@Override
	public int updateRentalApplySet(Rental rentalForm) {
		return rentalMapper.updateRentalApplySet(rentalForm);		
	}
	
	/**
	 * 대여/대관을 삭제한다.
	 */
	@Override
	public int deleteRental(List<Rental> rentalList) {
		int deleted = 0;
		for( Rental rental : rentalList ){
			deleted += this.deleteRental(rental);
		}
		return deleted;
	}

	/**
	 * 대여/대관을 삭제한다.
	 */
	@Override
	public int deleteRental(Rental rental) {
		Rental fromDB = rentalMapper.selectRental(rental);
		fromDB.setSitePrefix(rental.getSitePrefix());
		
		//삭제하려는 대관/대여에 신청한 신청정보가 있는지 체크하여 있으면 삭제하지 않는다.
		RentalReservationSearch rentalReservationSearch = new RentalReservationSearch();
		rentalReservationSearch.setSitePrefix(fromDB.getSitePrefix());
		rentalReservationSearch.setReservRentId(fromDB.getRentId());
		int reservCnt = 0;
		reservCnt = rentalMapper.selectRentalReservationListTotal(rentalReservationSearch);
		if(reservCnt > 0){
			return 0;
		}
		
		int result = rentalMapper.deleteRental(rental);
		
		if(result > 0){
			
			//대표이미지 삭제
			if(fromDB.getThumb() != null && fromDB.getThumb().getFileId() > 0){
				FileInfo fileInfoImage = fromDB.getThumb();
				fileInfoImage.setSitePrefix(fromDB.getSitePrefix());
				fileInfoService.deleteFileInfo(fileInfoImage);
			}
		}

		return result;
	}

	/**
	 * 대여/대관을 정렬순서를 변경한다.
	 */
	@Override
	public int updateRentalOrder(List<Rental> rentalList) {
		int updated = 0;
		for( Rental rental : rentalList ){
			updated += this.updateRentalOrder(rental);
		}
		return updated;
	}
	
	/**
	 * 대여/대관을 정렬순서를 변경한다.
	 */
	@Override
	public int updateRentalOrder(Rental rental) {
		int result = rentalMapper.updateRentalOrder(rental);
		return result;
	}

	//=============================================================================================================================
	//==========================================  신청정보   ================================================================
	//=============================================================================================================================
	
	/**
	 * 신청정보 목록을 조회한다.
	 */
	@Override
	public List<RentalReservationInfo> getRentalReservationList(RentalReservationSearch rentalReservationSearch) {
		return rentalMapper.selectRentalReservationList(rentalReservationSearch);
	}

	/**
	 * 신청정보 목록 갯수를 조회한다.
	 */
	@Override
	public int getRentalReservationListTotal(RentalReservationSearch rentalReservationSearch) {
		return rentalMapper.selectRentalReservationListTotal(rentalReservationSearch);
	}
	
	/**
	 * 신청정보 목록을 신청자 아이디로 조회한다. - 대관정보 포함
	 */
	@Override
	public List<RentalReservationInfo> getRentalReservationListByUserId(RentalReservationSearch rentalReservationSearch) {
		return rentalMapper.selectRentalReservationListByUserId(rentalReservationSearch);
	}

	/**
	 * 신청정보 목록 갯수를 신청자 아이디로 조회한다.
	 */
	@Override
	public int getRentalReservationListTotalByUserId(RentalReservationSearch rentalReservationSearch) {
		return rentalMapper.selectRentalReservationListTotalByUserId(rentalReservationSearch);
	}
	
	/**
	 * 신청정보를 등록한다.
	 */
	@Override
	public int insertRentalReservation(RentalReservationInfo rentalReservationForm) {
		rentalReservationForm.setReservId("R" + AsaproUtils.getFormattedDate(new Date(), "yyyyMMdd"));
		return rentalMapper.insertRentalReservation(rentalReservationForm);
	}

	/**
	 * 신청정보를 조회한다.
	 */
	@Override
	public RentalReservationInfo getRentalReservation(RentalReservationInfo rentalReservationForm) {
		return rentalMapper.selectRentalReservation(rentalReservationForm);
	}

	/**
	 * 신청정보를 수정한다.
	 */
	@Override
	public int updateRentalReservation(RentalReservationInfo rentalReservationForm) {
		return rentalMapper.updateRentalReservation(rentalReservationForm);
	}
	
	/**
	 * 신청정보를 삭제한다.
	 */
	@Override
	public int deleteRentalReservationInfo(List<RentalReservationInfo> rentalReservationInfoList) {
		int deleted = 0;
		for( RentalReservationInfo rentalReservationInfo : rentalReservationInfoList ){
			deleted += this.deleteRentalReservationInfo(rentalReservationInfo);
		}
		return deleted;
	}
	
	/**
	 * 신청정보를 삭제한다.
	 */
	@Override
	public int deleteRentalReservationInfo(RentalReservationInfo rentalReservationInfo) {
		int deleted = rentalMapper.deleteRentalReservationInfo(rentalReservationInfo);
		return deleted;
	}

	/**
	 * 대관이이디와 대관일자로 신청정보를 조회한다.
	 */
	@Override
	public List<RentalReservationInfo> getRentalReservationListByDate(RentalReservationInfo rentalReservationForm) {
		return rentalMapper.selectRentalReservationListByDate(rentalReservationForm);
	}

	/**
	 * 신청정보의 산청상태값을 변경한다.
	 */
	@Override
	public int updateReservStatus(RentalReservationInfo rentalReservationForm) {
		return rentalMapper.updateReservStatus(rentalReservationForm);
	}

	//================================================
	/**
	 * 예약시간 닫힘 정보를 등록한다.
	 */
	@Override
	public int insertRentalReservCloseTime(List<RentalReservCloseTime> rentalReservCloseTimeList) {
		int inserted = 0;
		for( RentalReservCloseTime rentalReservCloseTime : rentalReservCloseTimeList ){
			inserted += this.insertRentalReservCloseTime(rentalReservCloseTime);
		}
		return inserted;
	}
	
	/**
	 * 예약시간 닫힘 정보를 등록한다.
	 */
	@Override
	public int insertRentalReservCloseTime(RentalReservCloseTime rentalReservCloseTime) {
		int inserted = rentalMapper.insertRentalReservCloseTime(rentalReservCloseTime);
		return inserted;
	}

	/**
	 * 예약시간 닫힘 정보를 삭제한다.
	 */
	@Override
	public int deleteRentalReservCloseTime(RentalReservCloseTime rentalReservCloseTime) {
		return rentalMapper.deleteRentalReservCloseTime(rentalReservCloseTime);
	}

	/**
	 * 예약시간 닫힘 정보를 조회한다.
	 */
	@Override
	public RentalReservCloseTime selectRentalReservCloseTime(RentalReservCloseTime rentalReservCloseTime) {
		return rentalMapper.selectRentalReservCloseTime(rentalReservCloseTime);
	}
	
	/**
	 * 예약시간 닫힘 정보목록을 조회한다.
	 */
	@Override
	public List<RentalReservCloseTime> selectRentalReservCloseTimeList(RentalReservCloseTime rentalReservCloseTime) {
		return rentalMapper.selectRentalReservCloseTimeList(rentalReservCloseTime);
	}

	


}
