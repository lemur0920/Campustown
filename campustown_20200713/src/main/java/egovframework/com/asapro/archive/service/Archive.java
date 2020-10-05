/**
 * 
 */
package egovframework.com.asapro.archive.service;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import egovframework.com.asapro.fileinfo.service.FileInfo;
import egovframework.com.asapro.member.admin.service.AdminMember;
import egovframework.com.asapro.site.service.MultiSiteVO;
import egovframework.com.asapro.site.service.Site;
import egovframework.com.asapro.support.util.AsaproUtils;


/**
 * 아카이브 VO
 * @author yckim
 * @since 2018. 12. 19.
 *
 */
public class Archive extends MultiSiteVO{
	
	private Integer arcId;	//아카이브 아이디
	private String arcCategory;	//카테고리 아이디
	private String arcTitle;	//제목
	private String arcCustomType;	//아카이브 타입
	private String arcContent;	//내용
	private String memId;	//작성자 아이디
	private Date arcRegdate;	//등록일자
	private Date arcLastUpdate;	//마지막 수정일자
	private Integer arcHit = 0;	//조회수
	private Integer arcRecommend = 0;	//추천수
	private String arcNuri;	//공공누리 코드
	private Boolean arcUse = true;	//게시여부
	private Integer arcThumb;	//썸내일 파일번호
	private String arcMetaCode1;	//메타코드1
	private String arcMetaCode2;	//메타코드2
	private String arcMetaCode3;	//메타코드3
	
	private String arcUnivId;       //jaseo(2020.03.30. ) - 대학코드 
	private String arcTag;	//테그(검색키워드)
	
	//광고정보 - 초기 만든값으로 차후 제거예정
	private String arcMedia;	//매체
	private String arcYear;	//제작년도
	private String arcAdvertiser;	//광고주
	private String arcProduct;	//제품명
	private String arcProductDiv;	//제품분류
	private String arcActor;	//배우,성우
	private String arcPeriod;	//기간
	//=====================================
	
	private String arcThumbText = "";	//대표이미지 대체텍스트
	private Boolean arcSelected1 = false;//선택여부1 - 추출 등
	private Integer csSumCount = 0;
	
	private AdminMember adminMember;	//작성자 관리자
	private ArchiveCategory archiveCategory;	//아카이브 카테고리
	private FileInfo thumb;	//대표이미지
	private List<String> arcAltTexts;	//첨부파일 대체텍스트용 
	private List<FileInfo> arcFileInfos;	//첨부파일 목록
	
	private MultipartFile arcThumbFile;	//대표이미지 첨부파일
	private List<MultipartFile> arcMultipartFiles;	//파일첨부필드
	private List<Integer> fileInfoDeleteIds;	// 파일 삭제필드
	/**
	 * @return the arcId
	 */
	public Integer getArcId() {
		return arcId;
	}
	/**
	 * @param arcId the arcId to set
	 */
	public void setArcId(Integer arcId) {
		this.arcId = arcId;
	}
	
	/**
	 * @return the arcCategory
	 */
	public String getArcCategory() {
		return arcCategory;
	}
	/**
	 * @param arcCategory the arcCategory to set
	 */
	public void setArcCategory(String arcCategory) {
		this.arcCategory = arcCategory;
	}
	/**
	 * @return the arcTitle
	 */
	public String getArcTitle() {
		return arcTitle;
	}
	/**
	 * @param arcTitle the arcTitle to set
	 */
	public void setArcTitle(String arcTitle) {
		this.arcTitle = arcTitle;
	}
	/**
	 * @return the arcContent
	 */
	public String getArcContent() {
		return arcContent;
	}
	/**
	 * @param arcContent the arcContent to set
	 */
	public void setArcContent(String arcContent) {
		this.arcContent = arcContent;
	}
	/**
	 * @return the memId
	 */
	public String getMemId() {
		return memId;
	}
	/**
	 * @param memId the memId to set
	 */
	public void setMemId(String memId) {
		this.memId = memId;
	}
	/**
	 * @return the arcRegdate
	 */
	public Date getArcRegdate() {
		return arcRegdate;
	}
	/**
	 * @param arcRegdate the arcRegdate to set
	 */
	public void setArcRegdate(Date arcRegdate) {
		this.arcRegdate = arcRegdate;
	}
	/**
	 * @return the arcLastUpdate
	 */
	public Date getArcLastUpdate() {
		return arcLastUpdate;
	}
	/**
	 * @param arcLastUpdate the arcLastUpdate to set
	 */
	public void setArcLastUpdate(Date arcLastUpdate) {
		this.arcLastUpdate = arcLastUpdate;
	}
	/**
	 * @return the arcHit
	 */
	public Integer getArcHit() {
		return arcHit;
	}
	/**
	 * @param arcHit the arcHit to set
	 */
	public void setArcHit(Integer arcHit) {
		this.arcHit = arcHit;
	}
	/**
	 * @return the arcRecommend
	 */
	public Integer getArcRecommend() {
		return arcRecommend;
	}
	/**
	 * @param arcRecommend the arcRecommend to set
	 */
	public void setArcRecommend(Integer arcRecommend) {
		this.arcRecommend = arcRecommend;
	}
	/**
	 * @return the arcNuri
	 */
	public String getArcNuri() {
		return arcNuri;
	}
	/**
	 * @param arcNuri the arcNuri to set
	 */
	public void setArcNuri(String arcNuri) {
		this.arcNuri = arcNuri;
	}
	/**
	 * @return the arcUse
	 */
	public Boolean getArcUse() {
		return arcUse;
	}
	/**
	 * @param arcUse the arcUse to set
	 */
	public void setArcUse(Boolean arcUse) {
		this.arcUse = arcUse;
	}
	/**
	 * @return the arcThumb
	 */
	public Integer getArcThumb() {
		return arcThumb;
	}
	/**
	 * @param arcThumb the arcThumb to set
	 */
	public void setArcThumb(Integer arcThumb) {
		this.arcThumb = arcThumb;
	}
	/**
	 * @return the arcMetaCode1
	 */
	public String getArcMetaCode1() {
		return arcMetaCode1;
	}
	/**
	 * @param arcMetaCode1 the arcMetaCode1 to set
	 */
	public void setArcMetaCode1(String arcMetaCode1) {
		this.arcMetaCode1 = arcMetaCode1;
	}
	/**
	 * @return the arcMetaCode2
	 */
	public String getArcMetaCode2() {
		return arcMetaCode2;
	}
	/**
	 * @param arcMetaCode2 the arcMetaCode2 to set
	 */
	public void setArcMetaCode2(String arcMetaCode2) {
		this.arcMetaCode2 = arcMetaCode2;
	}
	/**
	 * @return the arcMetaCode3
	 */
	public String getArcMetaCode3() {
		return arcMetaCode3;
	}
	/**
	 * @param arcMetaCode3 the arcMetaCode3 to set
	 */
	public void setArcMetaCode3(String arcMetaCode3) {
		this.arcMetaCode3 = arcMetaCode3;
	}
	public String getArcUnivId() {
		return arcUnivId;
	}
	public void setArcUnivId(String arcUnivId) {
		this.arcUnivId = arcUnivId;
	}
	/**
	 * @return the arcYear
	 */
	public String getArcYear() {
		return arcYear;
	}
	/**
	 * @param arcYear the arcYear to set
	 */
	public void setArcYear(String arcYear) {
		this.arcYear = arcYear;
	}
	/**
	 * @return the arcAdvertiser
	 */
	public String getArcAdvertiser() {
		return arcAdvertiser;
	}
	/**
	 * @param arcAdvertiser the arcAdvertiser to set
	 */
	public void setArcAdvertiser(String arcAdvertiser) {
		this.arcAdvertiser = arcAdvertiser;
	}
	/**
	 * @return the arcProduct
	 */
	public String getArcProduct() {
		return arcProduct;
	}
	/**
	 * @param arcProduct the arcProduct to set
	 */
	public void setArcProduct(String arcProduct) {
		this.arcProduct = arcProduct;
	}
	/**
	 * @return the arcActor
	 */
	public String getArcActor() {
		return arcActor;
	}
	/**
	 * @param arcActor the arcActor to set
	 */
	public void setArcActor(String arcActor) {
		this.arcActor = arcActor;
	}
	/**
	 * @return the arcPeriod
	 */
	public String getArcPeriod() {
		return arcPeriod;
	}
	/**
	 * @param arcPeriod the arcPeriod to set
	 */
	public void setArcPeriod(String arcPeriod) {
		this.arcPeriod = arcPeriod;
	}
	/**
	 * @return the adminMember
	 */
	public AdminMember getAdminMember() {
		return adminMember;
	}
	/**
	 * @param adminMember the adminMember to set
	 */
	public void setAdminMember(AdminMember adminMember) {
		this.adminMember = adminMember;
	}
	/**
	 * @return the thumb
	 */
	public FileInfo getThumb() {
		return thumb;
	}
	/**
	 * @param thumb the thumb to set
	 */
	public void setThumb(FileInfo thumb) {
		this.thumb = thumb;
	}
	/**
	 * @return the arcThumbText
	 */
	public String getArcThumbText() {
		return arcThumbText;
	}
	/**
	 * @param arcThumbText the arcThumbText to set
	 */
	public void setArcThumbText(String arcThumbText) {
		this.arcThumbText = arcThumbText;
	}
	/**
	 * @return the arcFileInfos
	 */
	public List<FileInfo> getArcFileInfos() {
		return arcFileInfos;
	}
	/**
	 * @param arcFileInfos the arcFileInfos to set
	 */
	public void setArcFileInfos(List<FileInfo> arcFileInfos) {
		this.arcFileInfos = arcFileInfos;
	}
	/**
	 * @return the arcThumbFile
	 */
	public MultipartFile getArcThumbFile() {
		return arcThumbFile;
	}
	/**
	 * @param arcThumbFile the arcThumbFile to set
	 */
	public void setArcThumbFile(MultipartFile arcThumbFile) {
		this.arcThumbFile = arcThumbFile;
	}
	/**
	 * @return the arcMultipartFiles
	 */
	public List<MultipartFile> getArcMultipartFiles() {
		return arcMultipartFiles;
	}
	/**
	 * @param arcMultipartFiles the arcMultipartFiles to set
	 */
	public void setArcMultipartFiles(List<MultipartFile> arcMultipartFiles) {
		this.arcMultipartFiles = arcMultipartFiles;
	}
	
	/**
	 * @return the fileInfoDeleteIds
	 */
	public List<Integer> getFileInfoDeleteIds() {
		return fileInfoDeleteIds;
	}
	/**
	 * @param fileInfoDeleteIds the fileInfoDeleteIds to set
	 */
	public void setFileInfoDeleteIds(List<Integer> fileInfoDeleteIds) {
		this.fileInfoDeleteIds = fileInfoDeleteIds;
	}
	/**
	 * @return the archiveCategory
	 */
	public ArchiveCategory getArchiveCategory() {
		return archiveCategory;
	}
	/**
	 * @param archiveCategory the archiveCategory to set
	 */
	public void setArchiveCategory(ArchiveCategory archiveCategory) {
		this.archiveCategory = archiveCategory;
	}
	/**
	 * @return the arcAltTexts
	 */
	public List<String> getArcAltTexts() {
		return arcAltTexts;
	}
	/**
	 * @param arcAltTexts the arcAltTexts to set
	 */
	public void setArcAltTexts(List<String> arcAltTexts) {
		this.arcAltTexts = arcAltTexts;
	}
	/**
	 * @return the arcSelected1
	 */
	public Boolean getArcSelected1() {
		return arcSelected1;
	}
	/**
	 * @param arcSelected1 the arcSelected1 to set
	 */
	public void setArcSelected1(Boolean arcSelected1) {
		this.arcSelected1 = arcSelected1;
	}
	/**
	 * @return the arcMedia
	 */
	public String getArcMedia() {
		return arcMedia;
	}
	/**
	 * @param arcMedia the arcMedia to set
	 */
	public void setArcMedia(String arcMedia) {
		this.arcMedia = arcMedia;
	}
	/**
	 * @return the arcProductDiv
	 */
	public String getArcProductDiv() {
		return arcProductDiv;
	}
	/**
	 * @param arcProductDiv the arcProductDiv to set
	 */
	public void setArcProductDiv(String arcProductDiv) {
		this.arcProductDiv = arcProductDiv;
	}
	/**
	 * @return the arcCustomType
	 */
	public String getArcCustomType() {
		return arcCustomType;
	}
	/**
	 * @param arcCustomType the arcCustomType to set
	 */
	public void setArcCustomType(String arcCustomType) {
		this.arcCustomType = arcCustomType;
	}
	/**
	 * @return the arcTag
	 */
	public String getArcTag() {
		return arcTag;
	}
	/**
	 * @param arcTag the arcTag to set
	 */
	public void setArcTag(String arcTag) {
		this.arcTag = arcTag;
	}
	
	/**
	 * @return the csSumCount
	 */
	public Integer getCsSumCount() {
		return csSumCount;
	}
	/**
	 * @param csSumCount the csSumCount to set
	 */
	public void setCsSumCount(Integer csSumCount) {
		this.csSumCount = csSumCount;
	}
	/**
	 * 고정 링크를 반환한다.
	 * @return
	 */
	public String getPermLink(){
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		Site currentSite = (Site)request.getAttribute("currentSite"); 
		if( this.getArcCustomType() == null || "archive".equals(this.getArcCustomType()) ){
			return AsaproUtils.getAppPath(currentSite) + "/archive/" + this.getArcCategory() + "/" + this.getArcId();
		} else {
			return AsaproUtils.getAppPath(currentSite) + "/archive/" + this.getArcCustomType() + "/"  + this.getArcCategory() + "/" + this.getArcId();
		}
	}
}
