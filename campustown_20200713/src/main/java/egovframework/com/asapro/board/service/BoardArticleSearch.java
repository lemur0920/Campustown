/**
 * 
 */
package egovframework.com.asapro.board.service;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;

import egovframework.com.asapro.support.pagination.PagingSearch;

/**
 * 게시물 검색 VO
 * @author yckim
 * @since 2018. 6. 7.
 *
 */
public class BoardArticleSearch extends PagingSearch{
	
	private String bcId;	//게시판 아이디
	private String baCategory1;	//분류1
	private String baCategory2;	//분류2
	private String baCategory3;	//분류3
	private Boolean baNotice;	//공지여부
	private Boolean baMainSelec;	//메인페이지 추출
	private String baComSelCat = "";	//공통추출 대상 분류코드
	private Boolean baCommSelec;	//공통게시판 추출
	private Boolean baOpenDay;	//게시날짜 설정 사용여부
	private Boolean baUse;	//게시여부
	private String now;	//현제 날짜
	private String nowTime;	//현제 시간
	private String memberId;	//작성자아이디
	private String depId;	//작성자 부서코드
	private String teamId;	//작성자 팀코드
	
	//등록일 검색을 위해 구간 선택 옵션
	private String startDate;	//검색시작일
	private String endDate;	//검색종료일
	
	/**
	 * @return the baNotice
	 */
	public Boolean getBaNotice() {
		return baNotice;
	}

	/**
	 * @param baNotice the baNotice to set
	 */
	public void setBaNotice(Boolean baNotice) {
		this.baNotice = baNotice;
	}

	/**
	 * @return the baCategory1
	 */
	public String getBaCategory1() {
		return baCategory1;
	}

	/**
	 * @param baCategory1 the baCategory1 to set
	 */
	public void setBaCategory1(String baCategory1) {
		this.baCategory1 = baCategory1;
	}

	/**
	 * @return the baCategory2
	 */
	public String getBaCategory2() {
		return baCategory2;
	}

	/**
	 * @param baCategory2 the baCategory2 to set
	 */
	public void setBaCategory2(String baCategory2) {
		this.baCategory2 = baCategory2;
	}

	/**
	 * @return the baCategory3
	 */
	public String getBaCategory3() {
		return baCategory3;
	}

	/**
	 * @param baCategory3 the baCategory3 to set
	 */
	public void setBaCategory3(String baCategory3) {
		this.baCategory3 = baCategory3;
	}

	/**
	 * @return the bcId
	 */
	public String getBcId() {
		return bcId;
	}

	/**
	 * @param bcId the bcId to set
	 */
	public void setBcId(String bcId) {
		this.bcId = bcId;
	}
	
	/**
	 * @return the baUse
	 */
	public Boolean getBaUse() {
		return baUse;
	}

	/**
	 * @param baUse the baUse to set
	 */
	public void setBaUse(Boolean baUse) {
		this.baUse = baUse;
	}

	/**
	 * @return the now
	 */
	public String getNow() {
		return now;
	}

	/**
	 * @param now the now to set
	 */
	public void setNow(String now) {
		this.now = now;
	}

	/**
	 * @return the startDate
	 */
	public String getStartDate() {
		return startDate;
	}

	/**
	 * @param startDate the startDate to set
	 */
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	/**
	 * @return the endDate
	 */
	public String getEndDate() {
		return endDate;
	}

	/**
	 * @param endDate the endDate to set
	 */
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	/**
	 * @return the baMainSelec
	 */
	public Boolean getBaMainSelec() {
		return baMainSelec;
	}

	/**
	 * @param baMainSelec the baMainSelec to set
	 */
	public void setBaMainSelec(Boolean baMainSelec) {
		this.baMainSelec = baMainSelec;
	}

	/**
	 * @return the baCommSelec
	 */
	public Boolean getBaCommSelec() {
		return baCommSelec;
	}

	/**
	 * @param baCommSelec the baCommSelec to set
	 */
	public void setBaCommSelec(Boolean baCommSelec) {
		this.baCommSelec = baCommSelec;
	}

	/**
	 * @return the baComSelCat
	 */
	public String getBaComSelCat() {
		return baComSelCat;
	}

	/**
	 * @param baComSelCat the baComSelCat to set
	 */
	public void setBaComSelCat(String baComSelCat) {
		this.baComSelCat = baComSelCat;
	}

	/**
	 * @return the baOpenDay
	 */
	public Boolean getBaOpenDay() {
		return baOpenDay;
	}

	/**
	 * @param baOpenDay the baOpenDay to set
	 */
	public void setBaOpenDay(Boolean baOpenDay) {
		this.baOpenDay = baOpenDay;
	}

	/**
	 * @return the nowTime
	 */
	public String getNowTime() {
		return nowTime;
	}

	/**
	 * @param nowTime the nowTime to set
	 */
	public void setNowTime(String nowTime) {
		this.nowTime = nowTime;
	}

	/**
	 * @return the memberId
	 */
	public String getMemberId() {
		return memberId;
	}

	/**
	 * @param memberId the memberId to set
	 */
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	/**
	 * @return the depId
	 */
	public String getDepId() {
		return depId;
	}

	/**
	 * @param depId the depId to set
	 */
	public void setDepId(String depId) {
		this.depId = depId;
	}

	/**
	 * @return the teamId
	 */
	public String getTeamId() {
		return teamId;
	}

	/**
	 * @param teamId the teamId to set
	 */
	public void setTeamId(String teamId) {
		this.teamId = teamId;
	}

	@Override
	public String getQueryString() {
		
		StringBuilder sb = new StringBuilder(100);
		sb.append(super.getQueryString());
		
		if( StringUtils.isNotBlank(this.getBcId()) ){
			sb.append("&amp;bcId=");
			sb.append(this.getBcId());
		}
		if( StringUtils.isNotBlank(this.getBaCategory1()) ){
			sb.append("&amp;baCategory1=");
			sb.append(this.getBaCategory1());
		}
		if( StringUtils.isNotBlank(this.getBaCategory2()) ){
			sb.append("&amp;baCategory2=");
			sb.append(this.getBaCategory2());
		}
		if( StringUtils.isNotBlank(this.getBaCategory3()) ){
			sb.append("&amp;baCategory3=");
			sb.append(this.getBaCategory3());
		}
		if( this.getBaNotice() != null ){
			sb.append("&amp;baNotice=");
			sb.append(this.getBaNotice());
		}
		if( this.getBaMainSelec() != null ){
			sb.append("&amp;baMainSelec=");
			sb.append(this.getBaMainSelec());
		}
		if( StringUtils.isNotBlank(this.getBaComSelCat()) ){
			sb.append("&amp;baComSelCat=");
			sb.append(this.getBaComSelCat());
		}
		if( this.getBaCommSelec() != null ){
			sb.append("&amp;baCommSelec=");
			sb.append(this.getBaCommSelec());
		}
		if( this.getBaOpenDay() != null ){
			sb.append("&amp;baOpenDay=");
			sb.append(this.getBaOpenDay());
		}
		if( this.getBaUse() != null ){
			sb.append("&amp;baUse=");
			sb.append(this.getBaUse());
		}
		if( StringUtils.isNotBlank(this.getStartDate()) ){
			sb.append("&amp;startDate=");
			sb.append(this.getStartDate());
		}
		if( StringUtils.isNotBlank(this.getEndDate()) ){
			sb.append("&amp;endDate=");
			sb.append(this.getEndDate());
		}
		if( StringUtils.isNotBlank(this.getDepId()) ){
			sb.append("&amp;depId=");
			sb.append(this.getDepId());
		}
		if( StringUtils.isNotBlank(this.getTeamId()) ){
			sb.append("&amp;teamId=");
			sb.append(this.getTeamId());
		}
		
		return sb.toString();
	}
}
