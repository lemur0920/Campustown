package egovframework.com.campustown.policyCfrnc.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import egovframework.com.asapro.fileinfo.service.FileInfo;
import egovframework.com.asapro.site.service.MultiSiteVO;


/**
 * 정책협의회 관리 정보 VO
 * @author jaseo
 * @since 2020. 03. 18.
 */
public class PolicyCfrnc extends MultiSiteVO{
	
	/***********************************/
	// 1. 정책협의회_관리 기본 정보 VO
	/***********************************/

	
	private Integer mngSeq;
	
	private String mngDiv;
	
	private Integer manageYear;
	private Integer cardiNum;
	private Integer manageOrdr;
	
	private String sttus;
	private String comptYn;
	private String manageNo; 
	private Integer manageNoOrdr; 
	private String sj;
	private String sugestTaskMainCn;
	private String relateLaword;
	private String sugestUnivChargerDept;
	private String sugestUnivChargerNm= "";
	private String sugestUnivChargerTel= "";
	private String jobChargerDept;
	private String jobChargerNm= "";
	private String jobChargerTel= "";
	private String processChargerDept;
	private String processChargerNm= "";
	private String processChargerTel= "";
	
	
	private Integer poUploadFileNum = 5;			//첨부가능한 파일갯수
	private Integer poUploadSizeMax = 10;			//첨부파일 업로드 사이즈 제한 / MB단위, 서버설정을 오버할 수 없음
	
	private List<String> poAltTexts;				//첨부파일 대체텍스트용
	private List<FileInfo> poFileInfos;				//첨부파일 목록
	
	private List<MultipartFile> poMultipartFiles;	//파일첨부필드
	private List<Integer> fileInfoDeleteIds;		// 파일 삭제필드
	
	
	/***********************************/
	// 1. 정책협의회_관리 상세 정보 VO
	/***********************************/

	private Integer dtlSeq;
	//private Integer mngSeq; 
	private String prtnDt;
	private String prtnMatter;
	private String rm;

	
	
	/***********************************/
	// 기타. 공통
	/***********************************/
	
	private String delYn;
	private String regId;
	private String regNm;
	private String regDt;
	private String updId;
	private String updNm;
	private String updDt;
	private String delId;
	private String delNm;
	private String delDt;
	
	/***********************************/
	// 2. 정책협의회_관리 통계 정보 VO
	/***********************************/
	//private Integer manageYear;
	//private Integer manageOrdr;
	//private String sttus;
	private Integer totSttus;
	private Integer sttusLo;
	private Integer sttusSo;
	private Integer sttusRe;
	private Integer sttusPr;
	//private String comptYn;
	private Integer comptN;
	private Integer comptY;
	
	
	
	
	
	
	public Integer getMngSeq() {
		return mngSeq;
	}
	public void setMngSeq(Integer mngSeq) {
		this.mngSeq = mngSeq;
	}
	public String getMngDiv() {
		return mngDiv;
	}
	
	public Integer getManageYear() {
		return manageYear;
	}
	public void setManageYear(Integer manageYear) {
		this.manageYear = manageYear;
	}
	public Integer getManageOrdr() {
		return manageOrdr;
	}
	public void setManageOrdr(Integer manageOrdr) {
		this.manageOrdr = manageOrdr;
	}
	public Integer getManageNoOrdr() {
		return manageNoOrdr;
	}
	public void setManageNoOrdr(Integer manageNoOrdr) {
		this.manageNoOrdr = manageNoOrdr;
	}
	public void setMngDiv(String mngDiv) {
		this.mngDiv = mngDiv;
	}
	public String getSttus() {
		return sttus;
	}
	public void setSttus(String sttus) {
		this.sttus = sttus;
	}
	public String getComptYn() {
		return comptYn;
	}
	public void setComptYn(String comptYn) {
		this.comptYn = comptYn;
	}
	public String getManageNo() {
		return manageNo;
	}
	public void setManageNo(String manageNo) {
		this.manageNo = manageNo;
	}
	public String getSj() {
		return sj;
	}
	public void setSj(String sj) {
		this.sj = sj;
	}
	public String getSugestTaskMainCn() {
		return sugestTaskMainCn;
	}
	public void setSugestTaskMainCn(String sugestTaskMainCn) {
		this.sugestTaskMainCn = sugestTaskMainCn;
	}
	public String getRelateLaword() {
		return relateLaword;
	}
	public void setRelateLaword(String relateLaword) {
		this.relateLaword = relateLaword;
	}
	public String getSugestUnivChargerDept() {
		return sugestUnivChargerDept;
	}
	public void setSugestUnivChargerDept(String sugestUnivChargerDept) {
		this.sugestUnivChargerDept = sugestUnivChargerDept;
	}
	public String getSugestUnivChargerNm() {
		return sugestUnivChargerNm;
	}
	public void setSugestUnivChargerNm(String sugestUnivChargerNm) {
		this.sugestUnivChargerNm = sugestUnivChargerNm;
	}
	public String getSugestUnivChargerTel() {
		return sugestUnivChargerTel;
	}
	public void setSugestUnivChargerTel(String sugestUnivChargerTel) {
		this.sugestUnivChargerTel = sugestUnivChargerTel;
	}
	public String getJobChargerDept() {
		return jobChargerDept;
	}
	public void setJobChargerDept(String jobChargerDept) {
		this.jobChargerDept = jobChargerDept;
	}
	public String getJobChargerNm() {
		return jobChargerNm;
	}
	public void setJobChargerNm(String jobChargerNm) {
		this.jobChargerNm = jobChargerNm;
	}
	public String getJobChargerTel() {
		return jobChargerTel;
	}
	public void setJobChargerTel(String jobChargerTel) {
		this.jobChargerTel = jobChargerTel;
	}
	public String getProcessChargerDept() {
		return processChargerDept;
	}
	public void setProcessChargerDept(String processChargerDept) {
		this.processChargerDept = processChargerDept;
	}
	public String getProcessChargerNm() {
		return processChargerNm;
	}
	public void setProcessChargerNm(String processChargerNm) {
		this.processChargerNm = processChargerNm;
	}
	public String getProcessChargerTel() {
		return processChargerTel;
	}
	public void setProcessChargerTel(String processChargerTel) {
		this.processChargerTel = processChargerTel;
	}
	public Integer getCardiNum() {
		return cardiNum;
	}
	public void setCardiNum(Integer cardiNum) {
		this.cardiNum = cardiNum;
	}
	public Integer getPoUploadFileNum() {
		return poUploadFileNum;
	}
	public void setPoUploadFileNum(Integer poUploadFileNum) {
		this.poUploadFileNum = poUploadFileNum;
	}
	public Integer getPoUploadSizeMax() {
		return poUploadSizeMax;
	}
	public void setPoUploadSizeMax(Integer poUploadSizeMax) {
		this.poUploadSizeMax = poUploadSizeMax;
	}
	public List<String> getPoAltTexts() {
		return poAltTexts;
	}
	public void setPoAltTexts(List<String> poAltTexts) {
		this.poAltTexts = poAltTexts;
	}
	public List<FileInfo> getPoFileInfos() {
		return poFileInfos == null ? new ArrayList<FileInfo>() : poFileInfos;
	}
	public void setPoFileInfos(List<FileInfo> poFileInfos) {
		this.poFileInfos = poFileInfos;
	}
	public List<MultipartFile> getPoMultipartFiles() {
		return poMultipartFiles;
	}
	public void setPoMultipartFiles(List<MultipartFile> poMultipartFiles) {
		this.poMultipartFiles = poMultipartFiles;
	}
	
	
	public List<Integer> getFileInfoDeleteIds() {
		return fileInfoDeleteIds;
	}
	public void setFileInfoDeleteIds(List<Integer> fileInfoDeleteIds) {
		this.fileInfoDeleteIds = fileInfoDeleteIds;
	}
	public Integer getDtlSeq() {
		return dtlSeq;
	}
	public void setDtlSeq(Integer dtlSeq) {
		this.dtlSeq = dtlSeq;
	}
	public String getPrtnDt() {
		return prtnDt;
	}
	public void setPrtnDt(String prtnDt) {
		this.prtnDt = prtnDt;
	}
	public String getPrtnMatter() {
		return prtnMatter;
	}
	public void setPrtnMatter(String prtnMatter) {
		this.prtnMatter = prtnMatter;
	}
	public String getRm() {
		return rm;
	}
	public void setRm(String rm) {
		this.rm = rm;
	}
	public String getDelYn() {
		return delYn;
	}
	public void setDelYn(String delYn) {
		this.delYn = delYn;
	}
	public String getRegId() {
		return regId;
	}
	public void setRegId(String regId) {
		this.regId = regId;
	}
	public String getRegNm() {
		return regNm;
	}
	public void setRegNm(String regNm) {
		this.regNm = regNm;
	}
	public String getRegDt() {
		return regDt;
	}
	public void setRegDt(String regDt) {
		this.regDt = regDt;
	}
	public String getUpdId() {
		return updId;
	}
	public void setUpdId(String updId) {
		this.updId = updId;
	}
	public String getUpdNm() {
		return updNm;
	}
	public void setUpdNm(String updNm) {
		this.updNm = updNm;
	}
	public String getUpdDt() {
		return updDt;
	}
	public void setUpdDt(String updDt) {
		this.updDt = updDt;
	}
	public String getDelId() {
		return delId;
	}
	public void setDelId(String delId) {
		this.delId = delId;
	}
	public String getDelNm() {
		return delNm;
	}
	public void setDelNm(String delNm) {
		this.delNm = delNm;
	}
	public String getDelDt() {
		return delDt;
	}
	public void setDelDt(String delDt) {
		this.delDt = delDt;
	}
	
	//----------------------------
	// 통계 getter, setter
	//----------------------------
	
	public Integer getTotSttus() {
		return totSttus;
	}
	public void setTotSttus(Integer totSttus) {
		this.totSttus = totSttus;
	}
	public Integer getSttusLo() {
		return sttusLo;
	}
	public void setSttusLo(Integer sttusLo) {
		this.sttusLo = sttusLo;
	}
	public Integer getSttusSo() {
		return sttusSo;
	}
	public void setSttusSo(Integer sttusSo) {
		this.sttusSo = sttusSo;
	}
	public Integer getSttusRe() {
		return sttusRe;
	}
	public void setSttusRe(Integer sttusRe) {
		this.sttusRe = sttusRe;
	}
	public Integer getSttusPr() {
		return sttusPr;
	}
	public void setSttusPr(Integer sttusPr) {
		this.sttusPr = sttusPr;
	}
	public Integer getComptN() {
		return comptN;
	}
	public void setComptN(Integer comptN) {
		this.comptN = comptN;
	}
	public Integer getComptY() {
		return comptY;
	}
	public void setComptY(Integer comptY) {
		this.comptY = comptY;
	}
	
	
	

}
