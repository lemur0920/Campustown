/**
 * 
 */
package egovframework.com.asapro.poll.service;

import java.util.Date;

import org.springframework.web.multipart.MultipartFile;

import egovframework.com.asapro.fileinfo.service.FileInfo;
import egovframework.com.asapro.site.service.MultiSiteVO;

/**
 * 투표 VO (심플타입) - 질문과 질문에 대한 찬반만 결과 수집한다
 * @author yckim
 * @since 2020. 1. 21.
 *
 */
public class Poll extends MultiSiteVO{

	private Integer poId;	//투표 아이디
	private String poQuestion;	//질문
	private String poDescription;	//부가설명
	private String poStartDate;	//시작일
	private String poEndDate;	//종료일
	private String poType="";	//구분
	private Date poRegDate;	//등록일시
	private boolean poUse = true;	//개시여부
	private Integer poYesCnt = 0;	//찬성 수
	private Integer poNoCnt = 0;	//반대 수
	private Integer poHit = 0;	//조회 수

	private String poThumbText = "";	//대표이미지 대체텍스트
	private FileInfo thumb;	//이미지 정보
	private MultipartFile poImage;	//이미지파일
	private String pollData = ""; //투표내용 (yes, no)
	/**
	 * @return the poId
	 */
	public Integer getPoId() {
		return poId;
	}
	/**
	 * @param poId the poId to set
	 */
	public void setPoId(Integer poId) {
		this.poId = poId;
	}
	/**
	 * @return the poQuestion
	 */
	public String getPoQuestion() {
		return poQuestion;
	}
	/**
	 * @param poQuestion the poQuestion to set
	 */
	public void setPoQuestion(String poQuestion) {
		this.poQuestion = poQuestion;
	}
	/**
	 * @return the poDescription
	 */
	public String getPoDescription() {
		return poDescription;
	}
	/**
	 * @param poDescription the poDescription to set
	 */
	public void setPoDescription(String poDescription) {
		this.poDescription = poDescription;
	}
	/**
	 * @return the poStartDate
	 */
	public String getPoStartDate() {
		return poStartDate;
	}
	/**
	 * @param poStartDate the poStartDate to set
	 */
	public void setPoStartDate(String poStartDate) {
		this.poStartDate = poStartDate;
	}
	/**
	 * @return the poEndDate
	 */
	public String getPoEndDate() {
		return poEndDate;
	}
	/**
	 * @param poEndDate the poEndDate to set
	 */
	public void setPoEndDate(String poEndDate) {
		this.poEndDate = poEndDate;
	}
	/**
	 * @return the poType
	 */
	public String getPoType() {
		return poType;
	}
	/**
	 * @param poType the poType to set
	 */
	public void setPoType(String poType) {
		this.poType = poType;
	}
	/**
	 * @return the poRegDate
	 */
	public Date getPoRegDate() {
		return poRegDate;
	}
	/**
	 * @param poRegDate the poRegDate to set
	 */
	public void setPoRegDate(Date poRegDate) {
		this.poRegDate = poRegDate;
	}
	/**
	 * @return the poUse
	 */
	public boolean isPoUse() {
		return poUse;
	}
	/**
	 * @param poUse the poUse to set
	 */
	public void setPoUse(boolean poUse) {
		this.poUse = poUse;
	}
	/**
	 * @return the poYesCnt
	 */
	public Integer getPoYesCnt() {
		return poYesCnt;
	}
	/**
	 * @param poYesCnt the poYesCnt to set
	 */
	public void setPoYesCnt(Integer poYesCnt) {
		this.poYesCnt = poYesCnt;
	}
	/**
	 * @return the poNoCnt
	 */
	public Integer getPoNoCnt() {
		return poNoCnt;
	}
	/**
	 * @param poNoCnt the poNoCnt to set
	 */
	public void setPoNoCnt(Integer poNoCnt) {
		this.poNoCnt = poNoCnt;
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
	 * @return the poImage
	 */
	public MultipartFile getPoImage() {
		return poImage;
	}
	/**
	 * @param poImage the poImage to set
	 */
	public void setPoImage(MultipartFile poImage) {
		this.poImage = poImage;
	}
	/**
	 * @return the poThumbText
	 */
	public String getPoThumbText() {
		return poThumbText;
	}
	/**
	 * @param poThumbText the poThumbText to set
	 */
	public void setPoThumbText(String poThumbText) {
		this.poThumbText = poThumbText;
	}
	/**
	 * @return the poHit
	 */
	public Integer getPoHit() {
		return poHit;
	}
	/**
	 * @param poHit the poHit to set
	 */
	public void setPoHit(Integer poHit) {
		this.poHit = poHit;
	}
	/**
	 * @return the pollData
	 */
	public String getPollData() {
		return pollData;
	}
	/**
	 * @param pollData the pollData to set
	 */
	public void setPollData(String pollData) {
		this.pollData = pollData;
	}
	
	
	
	
}
