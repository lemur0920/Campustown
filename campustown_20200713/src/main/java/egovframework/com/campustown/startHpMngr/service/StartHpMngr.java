package egovframework.com.campustown.startHpMngr.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import egovframework.com.asapro.fileinfo.service.FileInfo;
import egovframework.com.asapro.site.service.MultiSiteVO;



/**
 * 창업팀 홈페이지 관리 정보 VO
 * @author jaseo
 * @since 2020. 02. 11.
 */
public class StartHpMngr extends MultiSiteVO{
	
	/****************************/
	// 1. 창업_회사 VO
	/****************************/

	private String compId;	// 회사ID
	private String compNm;	// 회사명 
	private String univId;  // 대학ID
	private String compTel=""; // 회사 연락처
	private String induty;	// 업종
	private String bsnsRealm;// 사업분야	
	private String intrstRealm;// 관심분야	
	private String fondDt; // 설립일

	/*private String zipcode= "";*/
	private String address; // 주소
	/*private String addressDetail= "";*/
	private String empCnt;  // 직원수
	
	private String fileId1; // 파일 1 (대표 이미지)
	private String fileId2; // 파일 2 (회사 CI)
	
	private MultipartFile file1;
	private String file1AltText;
	private MultipartFile file2;
	
	private FileInfo file1Info;
	private FileInfo file2Info;
	
	private String ment;		// 한 마디
	private String snsFace;		// sns Facebook
	private String snsTwit;		// sns Twitter
	private String snsBlog;		// sns Blog
	private String snsHp;		// sns Homepage
	private String snsInsta;	// sns Instagram
	private String snsKakao;	// sns Kakao

	private String vsn;			// 비전
	private String intrcn;		// 소개	
	private String mainSvc;		// 주요 서비스
	private String prMssage;	// 홍보 메시지	

	private String areaGuCd;	// 지역 구 코드
	
	//private FileInfo thumb;	//대표이미지
	private Integer stUploadFileNum = 2;			//첨부가능한 파일갯수
	private Integer stUploadSizeMax = 10;			//첨부파일 업로드 사이즈 제한 / MB단위, 서버설정을 오버할 수 없음
	
	private List<String> stAltTexts;				//첨부파일 대체텍스트용
	private List<FileInfo> stFileInfos;				//첨부파일 목록
	
	private List<MultipartFile> stMultipartFiles;	//파일첨부필드
	private List<Integer> fileInfoDeleteIds;		// 파일 삭제필드
	
	
	
	/****************************/
	// 2. 창업_임직원 VO
	/****************************/
	
	private Integer empId;	   // 직원 ID	
	// private String compId;  // 공통
	
	private String empNm= "";  // 직원명
		
	private String ofcps;		// 직위
	private String rprsntvYn;	// 대표자 유무

	private String tel= "";     // 회사 전화번호
	private String tel1;  // tel1-tel2-tel3
	private String tel2;
	private String tel3;
	private String telExpsrYn;	// 전화번호 노출 유무
	
	private String fileId3;		// 파일3 (대표 사진)
	private MultipartFile file3;
	private FileInfo file3Info;
	
	private String chrgJob;		// 담당업무
	private String phone= "";	// 핸드폰 번호
	private String phone1; // phone1-phone2-phone3 
	private String phone2;
	private String phone3;
	private String phoneExpsrYn; // 핸드폰번호 노출 유무
	
	private String email= "";	 // 이메일
	private String email1;
	private String email2;
	
	private String emailExpsrYn; // 이메일 노출 유무 
	
	private String seoulId;		 // 서울시 ID	
	private String seoulIdMsgExpsrYn;// 서울시 ID 노출 유무
	
	
	/****************************/
	// 3. 창업_활동지수 VO
	/****************************/
	
	private Integer indexId;		// 활동지수 ID
	// private String compId;
	private String sesnYear;		// 회기
	private String cardiNum;		// 기수(1:상반기, 2:하반기)
	//private String tmeDiv;			 
	private String saleAmt="";			// 매출 금액
	private String invtAmt="";			// 투자 금액
	private String emplyCnt="";		// 직원 수
	private String intellProp="";		// 지적 재산
	
	
	
	/****************************/
	// 기타. 공통
	/****************************/
	
	private String expsrYn;			// 노출 유무
	private String delYn;			// 삭제 유무
	private String regId;			// 등록 ID
	private String regNm;			// 등록자	
	private String regDt;			// 등록 일자
	private String updId;			// 수정 ID
	private String updNm;			// 수정자
	private String updDt;			// 수정일
	private String delId;			// 삭제 ID
	private String delNm;			// 삭제자
	private String delDt;			// 삭제일
	
	/****************************/
	// 임시 컬럼
	/****************************/

	private String univNm;
	private String oldUnivId;
	private String oldCompId;
	private String compId2;
	
	private String idxYear;
	private String totInvtAmt;
	private String totSaleAmt;
	private String totEmplyCnt;
	private String totIntellProp;
	private String idxUpdDt;
	
	
	private int baCnt;	//게시글 수
	private int cmtCnt;	//댓글 수
	private int score;	//홈페이지활동점수
	
	private int rn; // 랭킹
	
	public String getCompId2() {
		return compId2;
	}
	public void setCompId2(String compId2) {
		this.compId2 = compId2;
	}
	public String getCompId() {
		return compId;
	}
	public void setCompId(String compId) {
		this.compId = compId;
	}
	public String getCompNm() {
		return compNm;
	}
	public void setCompNm(String compNm) {
		this.compNm = compNm;
	}
	public String getUnivId() {
		return univId;
	}
	public void setUnivId(String univId) {
		this.univId = univId;
	}
	public String getCompTel() {
		return compTel;
	}
	public void setCompTel(String compTel) {
		this.compTel = compTel;
	}
	public String getInduty() {
		return induty;
	}
	public void setInduty(String induty) {
		this.induty = induty;
	}
	public String getBsnsRealm() {
		return bsnsRealm;
	}
	public void setBsnsRealm(String bsnsRealm) {
		this.bsnsRealm = bsnsRealm;
	}
	public String getIntrstRealm() {
		return intrstRealm;
	}
	public void setIntrstRealm(String intrstRealm) {
		this.intrstRealm = intrstRealm;
	}
	public String getFondDt() {
		return fondDt;
	}
	public void setFondDt(String fondDt) {
		this.fondDt = fondDt;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getEmpCnt() {
		return empCnt;
	}
	public void setEmpCnt(String empCnt) {
		this.empCnt = empCnt;
	}
	public String getFileId1() {
		return fileId1;
	}
	public void setFileId1(String fileId1) {
		this.fileId1 = fileId1;
	}
	public String getFileId2() {
		return fileId2;
	}
	public void setFileId2(String fileId2) {
		this.fileId2 = fileId2;
	}
	public MultipartFile getFile1() {
		return file1;
	}
	public void setFile1(MultipartFile file1) {
		this.file1 = file1;
	}
	public String getFile1AltText() {
		return file1AltText;
	}
	public void setFile1AltText(String file1AltText) {
		this.file1AltText = file1AltText;
	}
	public MultipartFile getFile2() {
		return file2;
	}
	public void setFile2(MultipartFile file2) {
		this.file2 = file2;
	}
	public FileInfo getFile1Info() {
		return file1Info;
	}
	public void setFile1Info(FileInfo file1Info) {
		this.file1Info = file1Info;
	}
	public FileInfo getFile2Info() {
		return file2Info;
	}
	public void setFile2Info(FileInfo file2Info) {
		this.file2Info = file2Info;
	}
	public String getMent() {
		return ment;
	}
	public void setMent(String ment) {
		this.ment = ment;
	}
	public String getSnsFace() {
		return snsFace;
	}
	public void setSnsFace(String snsFace) {
		this.snsFace = snsFace;
	}
	public String getSnsTwit() {
		return snsTwit;
	}
	public void setSnsTwit(String snsTwit) {
		this.snsTwit = snsTwit;
	}
	public String getSnsBlog() {
		return snsBlog;
	}
	public void setSnsBlog(String snsBlog) {
		this.snsBlog = snsBlog;
	}
	public String getSnsHp() {
		return snsHp;
	}
	public void setSnsHp(String snsHp) {
		this.snsHp = snsHp;
	}
	public String getSnsInsta() {
		return snsInsta;
	}
	public void setSnsInsta(String snsInsta) {
		this.snsInsta = snsInsta;
	}
	public String getSnsKakao() {
		return snsKakao;
	}
	public void setSnsKakao(String snsKakao) {
		this.snsKakao = snsKakao;
	}
	public String getVsn() {
		return vsn;
	}
	public void setVsn(String vsn) {
		this.vsn = vsn;
	}
	public String getIntrcn() {
		return intrcn;
	}
	public void setIntrcn(String intrcn) {
		this.intrcn = intrcn;
	}
	public String getMainSvc() {
		return mainSvc;
	}
	public void setMainSvc(String mainSvc) {
		this.mainSvc = mainSvc;
	}
	public String getPrMssage() {
		return prMssage;
	}
	public void setPrMssage(String prMssage) {
		this.prMssage = prMssage;
	}
	public String getAreaGuCd() {
		return areaGuCd;
	}
	public void setAreaGuCd(String areaGuCd) {
		this.areaGuCd = areaGuCd;
	}
	public Integer getStUploadFileNum() {
		return stUploadFileNum;
	}
	public void setStUploadFileNum(Integer stUploadFileNum) {
		this.stUploadFileNum = stUploadFileNum;
	}
	public Integer getStUploadSizeMax() {
		return stUploadSizeMax;
	}
	public void setStUploadSizeMax(Integer stUploadSizeMax) {
		this.stUploadSizeMax = stUploadSizeMax;
	}
	public List<String> getStAltTexts() {
		return stAltTexts;
	}
	public void setStAltTexts(List<String> stAltTexts) {
		this.stAltTexts = stAltTexts;
	}
	public List<FileInfo> getStFileInfos() {
		return stFileInfos == null ? new ArrayList<FileInfo>() : stFileInfos;
	}
	public void setStFileInfos(List<FileInfo> stFileInfos) {
		this.stFileInfos = stFileInfos;
	}
	public List<MultipartFile> getStMultipartFiles() {
		return stMultipartFiles;
	}
	public void setStMultipartFiles(List<MultipartFile> stMultipartFiles) {
		this.stMultipartFiles = stMultipartFiles;
	}
	public List<Integer> getFileInfoDeleteIds() {
		return fileInfoDeleteIds;
	}
	public void setFileInfoDeleteIds(List<Integer> fileInfoDeleteIds) {
		this.fileInfoDeleteIds = fileInfoDeleteIds;
	}
	public Integer getEmpId() {
		return empId;
	}
	public void setEmpId(Integer empId) {
		this.empId = empId;
	}
	public String getEmpNm() {
		return empNm;
	}
	public void setEmpNm(String empNm) {
		this.empNm = empNm;
	}
	public String getOfcps() {
		return ofcps;
	}
	public void setOfcps(String ofcps) {
		this.ofcps = ofcps;
	}
	public String getRprsntvYn() {
		return rprsntvYn;
	}
	public void setRprsntvYn(String rprsntvYn) {
		this.rprsntvYn = rprsntvYn;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getTel1() {
		return tel1;
	}
	public void setTel1(String tel1) {
		this.tel1 = tel1;
	}
	public String getTel2() {
		return tel2;
	}
	public void setTel2(String tel2) {
		this.tel2 = tel2;
	}
	public String getTel3() {
		return tel3;
	}
	public void setTel3(String tel3) {
		this.tel3 = tel3;
	}
	public String getTelExpsrYn() {
		return telExpsrYn;
	}
	public void setTelExpsrYn(String telExpsrYn) {
		this.telExpsrYn = telExpsrYn;
	}
	public String getFileId3() {
		return fileId3;
	}
	public void setFileId3(String fileId3) {
		this.fileId3 = fileId3;
	}
	public MultipartFile getFile3() {
		return file3;
	}
	public void setFile3(MultipartFile file3) {
		this.file3 = file3;
	}
	public FileInfo getFile3Info() {
		return file3Info;
	}
	public void setFile3Info(FileInfo file3Info) {
		this.file3Info = file3Info;
	}
	public String getChrgJob() {
		return chrgJob;
	}
	public void setChrgJob(String chrgJob) {
		this.chrgJob = chrgJob;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	public String getPhone1() {
		return phone1;
	}
	public void setPhone1(String phone1) {
		this.phone1 = phone1;
	}
	public String getPhone2() {
		return phone2;
	}
	public void setPhone2(String phone2) {
		this.phone2 = phone2;
	}
	public String getPhone3() {
		return phone3;
	}
	public void setPhone3(String phone3) {
		this.phone3 = phone3;
	}
	public String getPhoneExpsrYn() {
		return phoneExpsrYn;
	}
	public void setPhoneExpsrYn(String phoneExpsrYn) {
		this.phoneExpsrYn = phoneExpsrYn;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getEmail1() {
		return email1;
	}
	public void setEmail1(String email1) {
		this.email1 = email1;
	}
	public String getEmail2() {
		return email2;
	}
	public void setEmail2(String email2) {
		this.email2 = email2;
	}
	public String getEmailExpsrYn() {
		return emailExpsrYn;
	}
	public void setEmailExpsrYn(String emailExpsrYn) {
		this.emailExpsrYn = emailExpsrYn;
	}
	public String getSeoulId() {
		return seoulId;
	}
	public void setSeoulId(String seoulId) {
		this.seoulId = seoulId;
	}
	public String getSeoulIdMsgExpsrYn() {
		return seoulIdMsgExpsrYn;
	}
	public void setSeoulIdMsgExpsrYn(String seoulIdMsgExpsrYn) {
		this.seoulIdMsgExpsrYn = seoulIdMsgExpsrYn;
	}
/*	
	public String[] getBsnsRealmArray() {
		if(StringUtils.isNotBlank(this.bsnsRealm) ){
			return this.bsnsRealm.split(",");
		}
		return null;
	}
	public String[] getIntrstRealmArray() {
		if(StringUtils.isNotBlank(this.intrstRealm) ){
			return this.intrstRealm.split(",");
		}
		return null;
	}
*/	
	public Integer getIndexId() {
		return indexId;
	}
	public void setIndexId(Integer indexId) {
		this.indexId = indexId;
	}
	public String getSesnYear() {
		return sesnYear;
	}
	public void setSesnYear(String sesnYear) {
		this.sesnYear = sesnYear;
	}
	public String getCardiNum() {
		return cardiNum;
	}
	public void setCardiNum(String cardiNum) {
		this.cardiNum = cardiNum;
	}
	public String getSaleAmt() {
		return saleAmt;
	}
	public void setSaleAmt(String saleAmt) {
		this.saleAmt = saleAmt;
	}
	public String getInvtAmt() {
		return invtAmt;
	}
	public void setInvtAmt(String invtAmt) {
		this.invtAmt = invtAmt;
	}
	public String getEmplyCnt() {
		return emplyCnt;
	}
	public void setEmplyCnt(String emplyCnt) {
		this.emplyCnt = emplyCnt;
	}
	public String getIntellProp() {
		return intellProp;
	}
	public void setIntellProp(String intellProp) {
		this.intellProp = intellProp;
	}
	public String getExpsrYn() {
		return expsrYn;
	}
	public void setExpsrYn(String expsrYn) {
		this.expsrYn = expsrYn;
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
	public String getUnivNm() {
		return univNm;
	}
	public void setUnivNm(String univNm) {
		this.univNm = univNm;
	}
	public String getOldUnivId() {
		return oldUnivId;
	}
	public void setOldUnivId(String oldUnivId) {
		this.oldUnivId = oldUnivId;
	}
	public String getOldCompId() {
		return oldCompId;
	}
	public void setOldCompId(String oldCompId) {
		this.oldCompId = oldCompId;
	}
	public String getTotInvtAmt() {
		return totInvtAmt;
	}
	public void setTotInvtAmt(String totInvtAmt) {
		this.totInvtAmt = totInvtAmt;
	}
	public String getIdxYear() {
		return idxYear;
	}
	public void setIdxYear(String idxYear) {
		this.idxYear = idxYear;
	}
	public String getTotSaleAmt() {
		return totSaleAmt;
	}
	public void setTotSaleAmt(String totSaleAmt) {
		this.totSaleAmt = totSaleAmt;
	}
	public String getTotEmplyCnt() {
		return totEmplyCnt;
	}
	public void setTotEmplyCnt(String totEmplyCnt) {
		this.totEmplyCnt = totEmplyCnt;
	}
	public String getTotIntellProp() {
		return totIntellProp;
	}
	public void setTotIntellProp(String totIntellProp) {
		this.totIntellProp = totIntellProp;
	}
	public String getIdxUpdDt() {
		return idxUpdDt;
	}
	public void setIdxUpdDt(String idxUpdDt) {
		this.idxUpdDt = idxUpdDt;
	}
	/**
	 * @return the baCnt
	 */
	public int getBaCnt() {
		return baCnt;
	}
	/**
	 * @param baCnt the baCnt to set
	 */
	public void setBaCnt(int baCnt) {
		this.baCnt = baCnt;
	}
	/**
	 * @return the cmtCnt
	 */
	public int getCmtCnt() {
		return cmtCnt;
	}
	/**
	 * @param cmtCnt the cmtCnt to set
	 */
	public void setCmtCnt(int cmtCnt) {
		this.cmtCnt = cmtCnt;
	}
	/**
	 * @return the score
	 */
	public int getScore() {
		return score;
	}
	/**
	 * @param score the score to set
	 */
	public void setScore(int score) {
		this.score = score;
	}
	public int getRn() {
		return rn;
	}
	public void setRn(int rn) {
		this.rn = rn;
	}
	
	
	
}
