/**
 * 
 */
package egovframework.com.asapro.archive.customtype.advertising.service;


import org.springframework.web.multipart.MultipartFile;

import egovframework.com.asapro.archive.service.Archive;
import egovframework.com.asapro.fileinfo.service.FileInfo;
import egovframework.com.asapro.support.annotation.ArchiveItem;


/**
 * 광고자료 VO
 * 아카이브 커스텀타입
 * @author yckim
 * @since 2019. 9. 30.
 *
 */
@ArchiveItem(customType="advertising", label="광고자료")
public class Advertising extends Archive{
	
	private String adtManufactureYear;	//제작년도
	private String adtProducer;	//광고회사/제작사
	private String adtDirector;	//감독
	private String adtBackMusic;	//배경음악
	private String adtPlanIntertion;	//기획의도
	private String adtComposition;	//구성 및 표현
	private String adtProductionReview;	//제작후기
	private String adtTvCf;	//TV CF Copy
	private String adtRadioCf;	//Radio CF Copy
	private Integer adtVideo;	//동영상
	private Integer adtRadio;	//라디오
	private String adtMedia;	//매체
	private String adtAwardType;	//수상종류
	private String adtWinner;	//수상자
	private String adtUccUrl;	//ucc url
	
	private FileInfo videoFileInfo;	//동영상 파일정보
	private FileInfo radioFileInfo;	//라디오 파일정보
	private MultipartFile videoFile;	//동영상 첨부파일
	private MultipartFile radioFile;	//라디오 첨부파일
	private Integer deleteVideo;	//삭제 동영상 파일 아이디
	private Integer deleteRadio;	//삭제 라디오 파일 아이디
	
	/**
	 * @return the adtManufactureYear
	 */
	public String getAdtManufactureYear() {
		return adtManufactureYear;
	}
	/**
	 * @param adtManufactureYear the adtManufactureYear to set
	 */
	public void setAdtManufactureYear(String adtManufactureYear) {
		this.adtManufactureYear = adtManufactureYear;
	}
	/**
	 * @return the adtProducer
	 */
	public String getAdtProducer() {
		return adtProducer;
	}
	/**
	 * @param adtProducer the adtProducer to set
	 */
	public void setAdtProducer(String adtProducer) {
		this.adtProducer = adtProducer;
	}
	/**
	 * @return the adtDirector
	 */
	public String getAdtDirector() {
		return adtDirector;
	}
	/**
	 * @param adtDirector the adtDirector to set
	 */
	public void setAdtDirector(String adtDirector) {
		this.adtDirector = adtDirector;
	}
	/**
	 * @return the adtBackMusic
	 */
	public String getAdtBackMusic() {
		return adtBackMusic;
	}
	/**
	 * @param adtBackMusic the adtBackMusic to set
	 */
	public void setAdtBackMusic(String adtBackMusic) {
		this.adtBackMusic = adtBackMusic;
	}
	/**
	 * @return the adtPlanIntertion
	 */
	public String getAdtPlanIntertion() {
		return adtPlanIntertion;
	}
	/**
	 * @param adtPlanIntertion the adtPlanIntertion to set
	 */
	public void setAdtPlanIntertion(String adtPlanIntertion) {
		this.adtPlanIntertion = adtPlanIntertion;
	}
	/**
	 * @return the adtComposition
	 */
	public String getAdtComposition() {
		return adtComposition;
	}
	/**
	 * @param adtComposition the adtComposition to set
	 */
	public void setAdtComposition(String adtComposition) {
		this.adtComposition = adtComposition;
	}
	/**
	 * @return the adtProductionReview
	 */
	public String getAdtProductionReview() {
		return adtProductionReview;
	}
	/**
	 * @param adtProductionReview the adtProductionReview to set
	 */
	public void setAdtProductionReview(String adtProductionReview) {
		this.adtProductionReview = adtProductionReview;
	}
	/**
	 * @return the adtTvCf
	 */
	public String getAdtTvCf() {
		return adtTvCf;
	}
	/**
	 * @param adtTvCf the adtTvCf to set
	 */
	public void setAdtTvCf(String adtTvCf) {
		this.adtTvCf = adtTvCf;
	}
	/**
	 * @return the adtRadioCf
	 */
	public String getAdtRadioCf() {
		return adtRadioCf;
	}
	/**
	 * @param adtRadioCf the adtRadioCf to set
	 */
	public void setAdtRadioCf(String adtRadioCf) {
		this.adtRadioCf = adtRadioCf;
	}

	/**
	 * @return the adtVideo
	 */
	public Integer getAdtVideo() {
		return adtVideo;
	}
	/**
	 * @param adtVideo the adtVideo to set
	 */
	public void setAdtVideo(Integer adtVideo) {
		this.adtVideo = adtVideo;
	}
	/**
	 * @return the adtRadio
	 */
	public Integer getAdtRadio() {
		return adtRadio;
	}
	/**
	 * @param adtRadio the adtRadio to set
	 */
	public void setAdtRadio(Integer adtRadio) {
		this.adtRadio = adtRadio;
	}
	/**
	 * @return the adtMedia
	 */
	public String getAdtMedia() {
		return adtMedia;
	}
	/**
	 * @param adtMedia the adtMedia to set
	 */
	public void setAdtMedia(String adtMedia) {
		this.adtMedia = adtMedia;
	}
	/**
	 * @return the videoFileInfo
	 */
	public FileInfo getVideoFileInfo() {
		return videoFileInfo;
	}
	/**
	 * @param videoFileInfo the videoFileInfo to set
	 */
	public void setVideoFileInfo(FileInfo videoFileInfo) {
		this.videoFileInfo = videoFileInfo;
	}
	/**
	 * @return the radioFileInfo
	 */
	public FileInfo getRadioFileInfo() {
		return radioFileInfo;
	}
	/**
	 * @param radioFileInfo the radioFileInfo to set
	 */
	public void setRadioFileInfo(FileInfo radioFileInfo) {
		this.radioFileInfo = radioFileInfo;
	}
	/**
	 * @return the videoFile
	 */
	public MultipartFile getVideoFile() {
		return videoFile;
	}
	/**
	 * @param videoFile the videoFile to set
	 */
	public void setVideoFile(MultipartFile videoFile) {
		this.videoFile = videoFile;
	}
	/**
	 * @return the radioFile
	 */
	public MultipartFile getRadioFile() {
		return radioFile;
	}
	/**
	 * @param radioFile the radioFile to set
	 */
	public void setRadioFile(MultipartFile radioFile) {
		this.radioFile = radioFile;
	}
	/**
	 * @return the deleteVideo
	 */
	public Integer getDeleteVideo() {
		return deleteVideo;
	}
	/**
	 * @param deleteVideo the deleteVideo to set
	 */
	public void setDeleteVideo(Integer deleteVideo) {
		this.deleteVideo = deleteVideo;
	}
	/**
	 * @return the deleteRadio
	 */
	public Integer getDeleteRadio() {
		return deleteRadio;
	}
	/**
	 * @param deleteRadio the deleteRadio to set
	 */
	public void setDeleteRadio(Integer deleteRadio) {
		this.deleteRadio = deleteRadio;
	}
	/**
	 * @return the adtAwardType
	 */
	public String getAdtAwardType() {
		return adtAwardType;
	}
	/**
	 * @param adtAwardType the adtAwardType to set
	 */
	public void setAdtAwardType(String adtAwardType) {
		this.adtAwardType = adtAwardType;
	}
	/**
	 * @return the adtWinner
	 */
	public String getAdtWinner() {
		return adtWinner;
	}
	/**
	 * @param adtWinner the adtWinner to set
	 */
	public void setAdtWinner(String adtWinner) {
		this.adtWinner = adtWinner;
	}
	/**
	 * @return the adtUccUrl
	 */
	public String getAdtUccUrl() {
		return adtUccUrl;
	}
	/**
	 * @param adtUccUrl the adtUccUrl to set
	 */
	public void setAdtUccUrl(String adtUccUrl) {
		this.adtUccUrl = adtUccUrl;
	}
	
	
}
