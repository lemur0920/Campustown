package egovframework.com.asapro.openapiinfo.api.service;

import egovframework.com.asapro.site.service.MultiSiteVO;

public class JobNewDealBiz extends MultiSiteVO{
	
	private Integer seq;	//번호
	private String bizNm;	//사업명
	private String upOrgNm;	//상위기관명
	private String orgNm;	//하위기관명
	private String partNm;	//부서명
	private String bizYear;	//사업년도
	private String upBizNm;	//상위사업분류
	private String bizGrpNm;//하위사업분류
	private String bizTypeNm;	//사업유형
	private String bizTarget;	//참여대상
	private Integer allWrkTime;	//근로시간
	private Integer allPayAmt;	//시급
	private Integer allSlctNumber;	//계획인원
	private Integer allPlanNumber;	//금회선발인원
	private Integer partWrkTime;		//파트근로시간
	private Integer partPayAmt;		//파트시급
	private Integer partSlctNumber;	//파트계획인원
	private Integer partPlanNumber;	//파트금회선발인원
	private String bizStrtDd;		//근로시작일자
	private String bizEndDd;		//근로종료일자
	private String bizSmry;			//사업목적
	private String workSmry;		//업무내용
	private String condSmry;		//근무조건
	private String phonNoCn;		//담당자 번호
	
	/**
	 * @return the seq
	 */
	public Integer getSeq() {
		return seq;
	}
	/**
	 * @param seq the seq to set
	 */
	public void setSeq(Integer seq) {
		this.seq = seq;
	}
	/**
	 * @return the bizNm
	 */
	public String getBizNm() {
		return bizNm;
	}
	/**
	 * @param bizNm the bizNm to set
	 */
	public void setBizNm(String bizNm) {
		this.bizNm = bizNm;
	}
	/**
	 * @return the upOrgNm
	 */
	public String getUpOrgNm() {
		return upOrgNm;
	}
	/**
	 * @param upOrgNm the upOrgNm to set
	 */
	public void setUpOrgNm(String upOrgNm) {
		this.upOrgNm = upOrgNm;
	}
	/**
	 * @return the orgNm
	 */
	public String getOrgNm() {
		return orgNm;
	}
	/**
	 * @param orgNm the orgNm to set
	 */
	public void setOrgNm(String orgNm) {
		this.orgNm = orgNm;
	}
	/**
	 * @return the partNm
	 */
	public String getPartNm() {
		return partNm;
	}
	/**
	 * @param partNm the partNm to set
	 */
	public void setPartNm(String partNm) {
		this.partNm = partNm;
	}
	/**
	 * @return the bizYear
	 */
	public String getBizYear() {
		return bizYear;
	}
	/**
	 * @param bizYear the bizYear to set
	 */
	public void setBizYear(String bizYear) {
		this.bizYear = bizYear;
	}
	/**
	 * @return the upBizNm
	 */
	public String getUpBizNm() {
		return upBizNm;
	}
	/**
	 * @param upBizNm the upBizNm to set
	 */
	public void setUpBizNm(String upBizNm) {
		this.upBizNm = upBizNm;
	}
	/**
	 * @return the bizGrpNm
	 */
	public String getBizGrpNm() {
		return bizGrpNm;
	}
	/**
	 * @param bizGrpNm the bizGrpNm to set
	 */
	public void setBizGrpNm(String bizGrpNm) {
		this.bizGrpNm = bizGrpNm;
	}
	/**
	 * @return the bizTypeNm
	 */
	public String getBizTypeNm() {
		return bizTypeNm;
	}
	/**
	 * @param bizTypeNm the bizTypeNm to set
	 */
	public void setBizTypeNm(String bizTypeNm) {
		this.bizTypeNm = bizTypeNm;
	}
	/**
	 * @return the bizTarget
	 */
	public String getBizTarget() {
		return bizTarget;
	}
	/**
	 * @param bizTarget the bizTarget to set
	 */
	public void setBizTarget(String bizTarget) {
		this.bizTarget = bizTarget;
	}
	/**
	 * @return the allWrkTime
	 */
	public Integer getAllWrkTime() {
		return allWrkTime;
	}
	/**
	 * @param d the allWrkTime to set
	 */
	public void setAllWrkTime(Integer allWrkTime) {
		this.allWrkTime = allWrkTime;
	}
	/**
	 * @return the allPayAmt
	 */
	public Integer getAllPayAmt() {
		return allPayAmt;
	}
	/**
	 * @param d the allPayAmt to set
	 */
	public void setAllPayAmt(Integer allPayAmt) {
		this.allPayAmt = allPayAmt;
	}
	/**
	 * @return the allSlctNumber
	 */
	public Integer getAllSlctNumber() {
		return allSlctNumber;
	}
	/**
	 * @param d the allSlctNumber to set
	 */
	public void setAllSlctNumber(Integer allSlctNumber) {
		this.allSlctNumber = allSlctNumber;
	}
	/**
	 * @return the allPlanNumber
	 */
	public Integer getAllPlanNumber() {
		return allPlanNumber;
	}
	/**
	 * @param d the allPlanNumber to set
	 */
	public void setAllPlanNumber(Integer allPlanNumber) {
		this.allPlanNumber = allPlanNumber;
	}
	/**
	 * @return the partWrkTime
	 */
	public Integer getPartWrkTime() {
		return partWrkTime;
	}
	/**
	 * @param d the partWrkTime to set
	 */
	public void setPartWrkTime(Integer partWrkTime) {
		this.partWrkTime = partWrkTime;
	}
	/**
	 * @return the partPayAmt
	 */
	public Integer getPartPayAmt() {
		return partPayAmt;
	}
	/**
	 * @param d the partPayAmt to set
	 */
	public void setPartPayAmt(Integer partPayAmt) {
		this.partPayAmt = partPayAmt;
	}
	/**
	 * @return the partSlctNumber
	 */
	public Integer getPartSlctNumber() {
		return partSlctNumber;
	}
	/**
	 * @param d the partSlctNumber to set
	 */
	public void setPartSlctNumber(Integer partSlctNumber) {
		this.partSlctNumber = partSlctNumber;
	}
	/**
	 * @return the partPlanNumber
	 */
	public Integer getPartPlanNumber() {
		return partPlanNumber;
	}
	/**
	 * @param d the partPlanNumber to set
	 */
	public void setPartPlanNumber(Integer partPlanNumber) {
		this.partPlanNumber = partPlanNumber;
	}
	/**
	 * @return the bizStrtDd
	 */
	public String getBizStrtDd() {
		return bizStrtDd;
	}
	/**
	 * @param bizStrtDd the bizStrtDd to set
	 */
	public void setBizStrtDd(String bizStrtDd) {
		this.bizStrtDd = bizStrtDd;
	}
	/**
	 * @return the bizEndDd
	 */
	public String getBizEndDd() {
		return bizEndDd;
	}
	/**
	 * @param bizEndDd the bizEndDd to set
	 */
	public void setBizEndDd(String bizEndDd) {
		this.bizEndDd = bizEndDd;
	}
	/**
	 * @return the bizSmry
	 */
	public String getBizSmry() {
		return bizSmry;
	}
	/**
	 * @param bizSmry the bizSmry to set
	 */
	public void setBizSmry(String bizSmry) {
		this.bizSmry = bizSmry;
	}
	/**
	 * @return the workSmry
	 */
	public String getWorkSmry() {
		return workSmry;
	}
	/**
	 * @param workSmry the workSmry to set
	 */
	public void setWorkSmry(String workSmry) {
		this.workSmry = workSmry;
	}
	/**
	 * @return the condSmry
	 */
	public String getCondSmry() {
		return condSmry;
	}
	/**
	 * @param condSmry the condSmry to set
	 */
	public void setCondSmry(String condSmry) {
		this.condSmry = condSmry;
	}
	/**
	 * @return the phonNoCn
	 */
	public String getPhonNoCn() {
		return phonNoCn;
	}
	/**
	 * @param phonNoCn the phonNoCn to set
	 */
	public void setPhonNoCn(String phonNoCn) {
		this.phonNoCn = phonNoCn;
	}
	
	
	
	
}
