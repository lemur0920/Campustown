/**
 * 
 */
package egovframework.com.asapro.satisfaction.service;

import java.util.Date;
import java.util.List;

import egovframework.com.asapro.site.service.MultiSiteVO;

/**
 * 메뉴 만족도VO
 * @author yckim
 * @since 2019. 4. 15.
 *
 */
public class Satisfaction extends MultiSiteVO{
	
	private String menuId;	//메뉴 아이디
	private Integer satisScore;	//점수 - 컬럼 없음
	private Integer satisScore5 = 0;	//매우만족 횟수
	private Integer satisScore4 = 0;	//맨족 횟수
	private Integer satisScore3 = 0;	//보통 횟수
	private Integer satisScore2 = 0;	//불만 횟수
	private Integer satisScore1 = 0;	//매우불만 횟수
	private Date satisLastPartiDate;	//마지막 참여 일시
	private Date satisResetDate;	//데이터 리셋 일시
	private SatisOpinion opinion;	//평가의견
	
	private List<SatisOpinion> opinionList;	//평가의견 목록
	
	private int satisTotalCnt;	//총참여수
	private int satisTotalScore;	//만족도 총 점수
	/**
	 * 기본 생성자
	 */
	public Satisfaction(){
		super();
	}
	
	/**
	 * 메뉴아이디를 받는 생성자
	 * @param menuId
	 * @param sitePreFix
	 * @param satisLastPartiDate
	 * @param satisResetDate
	 */
	public Satisfaction(String menuId, String sitePreFix, Date satisLastPartiDate, Date satisResetDate) {
		this.menuId = menuId;
		this.satisLastPartiDate = satisLastPartiDate;
		this.satisResetDate = satisResetDate;
		super.sitePrefix = sitePreFix;
	}
	/**
	 * @return the menuId
	 */
	public String getMenuId() {
		return menuId;
	}
	/**
	 * @param menuId the menuId to set
	 */
	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	/**
	 * @return the satisScore
	 */
	public Integer getSatisScore() {
		return satisScore;
	}

	/**
	 * @param satisScore the satisScore to set
	 */
	public void setSatisScore(Integer satisScore) {
		this.satisScore = satisScore;
	}

	/**
	 * @return the satisScore5
	 */
	public Integer getSatisScore5() {
		return satisScore5;
	}

	/**
	 * @param satisScore5 the satisScore5 to set
	 */
	public void setSatisScore5(Integer satisScore5) {
		this.satisScore5 = satisScore5;
	}

	/**
	 * @return the satisScore4
	 */
	public Integer getSatisScore4() {
		return satisScore4;
	}

	/**
	 * @param satisScore4 the satisScore4 to set
	 */
	public void setSatisScore4(Integer satisScore4) {
		this.satisScore4 = satisScore4;
	}

	/**
	 * @return the satisScore3
	 */
	public Integer getSatisScore3() {
		return satisScore3;
	}

	/**
	 * @param satisScore3 the satisScore3 to set
	 */
	public void setSatisScore3(Integer satisScore3) {
		this.satisScore3 = satisScore3;
	}

	/**
	 * @return the satisScore2
	 */
	public Integer getSatisScore2() {
		return satisScore2;
	}

	/**
	 * @param satisScore2 the satisScore2 to set
	 */
	public void setSatisScore2(Integer satisScore2) {
		this.satisScore2 = satisScore2;
	}

	/**
	 * @return the satisScore1
	 */
	public Integer getSatisScore1() {
		return satisScore1;
	}

	/**
	 * @param satisScore1 the satisScore1 to set
	 */
	public void setSatisScore1(Integer satisScore1) {
		this.satisScore1 = satisScore1;
	}

	/**
	 * @return the satisLastPartiDate
	 */
	public Date getSatisLastPartiDate() {
		return satisLastPartiDate;
	}

	/**
	 * @param satisLastPartiDate the satisLastPartiDate to set
	 */
	public void setSatisLastPartiDate(Date satisLastPartiDate) {
		this.satisLastPartiDate = satisLastPartiDate;
	}

	/**
	 * @return the satisResetDate
	 */
	public Date getSatisResetDate() {
		return satisResetDate;
	}

	/**
	 * @param satisResetDate the satisResetDate to set
	 */
	public void setSatisResetDate(Date satisResetDate) {
		this.satisResetDate = satisResetDate;
	}

	/**
	 * @return the opinion
	 */
	public SatisOpinion getOpinion() {
		return opinion;
	}

	/**
	 * @param opinion the opinion to set
	 */
	public void setOpinion(SatisOpinion opinion) {
		this.opinion = opinion;
	}

	/**
	 * @return the opinionList
	 */
	public List<SatisOpinion> getOpinionList() {
		return opinionList;
	}

	/**
	 * @param opinionList the opinionList to set
	 */
	public void setOpinionList(List<SatisOpinion> opinionList) {
		this.opinionList = opinionList;
	}

	/**
	 * @return the satisTotalCnt
	 */
	public int getSatisTotalCnt() {
		this.satisTotalCnt = this.satisScore5.intValue();
		this.satisTotalCnt += this.satisScore4.intValue();
		this.satisTotalCnt += this.satisScore3.intValue();
		this.satisTotalCnt += this.satisScore2.intValue();
		this.satisTotalCnt += this.satisScore1.intValue();
		return satisTotalCnt;
	}

	/**
	 * @param satisTotalCnt the satisTotalCnt to set
	 */
	public void setSatisTotalCnt(int satisTotalCnt) {
		this.satisTotalCnt = satisTotalCnt;
	}

	/**
	 * @return the satisTotalScore
	 */
	public int getSatisTotalScore() {
		this.satisTotalScore = this.satisScore5.intValue() * 5;
		this.satisTotalScore += this.satisScore4.intValue() * 4;
		this.satisTotalScore += this.satisScore3.intValue() * 3;
		this.satisTotalScore += this.satisScore2.intValue() * 2;
		this.satisTotalScore += this.satisScore1.intValue() * 1;
		return satisTotalScore;
	}

	/**
	 * @param satisTotalScore the satisTotalScore to set
	 */
	public void setSatisTotalScore(int satisTotalScore) {
		this.satisTotalScore = satisTotalScore;
	}
	
}
