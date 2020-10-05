package egovframework.com.campustown.univHpMngr.service;

import org.springframework.web.multipart.MultipartFile;

import egovframework.com.asapro.fileinfo.service.FileInfo;
import egovframework.com.asapro.site.service.MultiSiteVO;

/**
 * 대학 홈페이지 관리 정보 VO
 * @author jaseo
 * @since 2020. 02. 12.
 */
public class UnivHpMngr extends MultiSiteVO{
	
	
	private String univId;
	private String univNm;
	private String fondDt;
	
	private String tel;
	private String tel1; // tel1-tel2-tel3
	private String tel2;
	private String tel3;
	
	private String chargerNm;
	private String email;
	private String email1;
	private String email2;
	
	//private String zipcode= "";
	private String address= "";
	//private String addressDetail= "";
	
	private String fileId1;
	private String fileId2;
	private String fileId3;
	
	private MultipartFile file1;
	private String file1AltText;
	private MultipartFile file2;
	private MultipartFile file3;
	
	private FileInfo file1Info;
	private FileInfo file2Info;
	private FileInfo file3Info;
	
	private String ment;
	
	private String vsn;
	private String intrcn;
	private String prMssage;
	private String sportProgrm;
	
	private String snsFace;
	private String snsTwit;
	private String snsBlog;
	private String snsHp;
	private String snsInsta;	// sns Instagram
	private String snsKakao;	// sns Kakao
	
	private String areaGuCd;
	private String expsrYn;
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
	
	
	
	// 임시 컬럼
	private String oldUnivId;
	private String compCnt;
	
	public String getUnivId() {
		return univId;
	}
	public void setUnivId(String univId) {
		this.univId = univId;
	}
	public String getUnivNm() {
		return univNm;
	}
	public void setUnivNm(String univNm) {
		this.univNm = univNm;
	}
	public String getFondDt() {
		return fondDt;
	}
	public void setFondDt(String fondDt) {
		this.fondDt = fondDt;
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
	public String getChargerNm() {
		return chargerNm;
	}
	public void setChargerNm(String chargerNm) {
		this.chargerNm = chargerNm;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
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
	public String getPrMssage() {
		return prMssage;
	}
	public void setPrMssage(String prMssage) {
		this.prMssage = prMssage;
	}
	public String getSportProgrm() {
		return sportProgrm;
	}
	public void setSportProgrm(String sportProgrm) {
		this.sportProgrm = sportProgrm;
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
	public String getAreaGuCd() {
		return areaGuCd;
	}
	public void setAreaGuCd(String areaGuCd) {
		this.areaGuCd = areaGuCd;
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
	public String getOldUnivId() {
		return oldUnivId;
	}
	public void setOldUnivId(String oldUnivId) {
		this.oldUnivId = oldUnivId;
	}
	public String getCompCnt() {
		return compCnt;
	}
	public void setCompCnt(String compCnt) {
		this.compCnt = compCnt;
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
	
}
