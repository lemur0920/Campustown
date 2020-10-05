/**
 * 
 */
package egovframework.com.asapro.banner.service;

import java.util.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.web.multipart.MultipartFile;

import egovframework.com.asapro.fileinfo.service.FileInfo;
import egovframework.com.asapro.site.service.MultiSiteVO;
//import egovframework.com.asapro.watchdog.aop.WatchDog;

/**
 * 배너(배너, 팝업존, 고정배너)
 * @author yckim
 * @since 2018. 7. 26.
 *
 */
public class Banner extends MultiSiteVO{

//	@WatchDog
	private Integer bnId;	//배너 아이디
	private String bnType = "banner";	//banner, popupzone, static, mainVisual, layer, promotion
	private Integer bnOrder;	//순서
//	@WatchDog
	private String bnName;	//배너이름
	private String bnDescription;	//배너 설명
	private String bnLink;	//링크
	private boolean bnNewWin = true;	//링크대상 - 타겟
	private String bnStartDate;	//팝업존 시작일
	private String bnEndDate;	//팝업존 종료일
	private Date bnRegDate;	//등록일
	private boolean bnUse = true;	//개시여부
	private String bnExt;	//파일 확장자
	
	private Integer bnWidth;	//ex) 400
	private Integer bnHeight;	//ex) 300
	private Integer bnTop = 0;	//ex) 
	private Integer bnLeft = 0;	//ex) 
	
	private MultipartFile bnImage;	//이미지파일
	private FileInfo thumb;	//이미지 정보
	
	/**
	 * @return the bnId
	 */
	public Integer getBnId() {
		return bnId;
	}
	/**
	 * @param bnId the bnId to set
	 */
	public void setBnId(Integer bnId) {
		this.bnId = bnId;
	}
	/**
	 * @return the bnType
	 */
	public String getBnType() {
		return bnType;
	}
	/**
	 * @param bnType the bnType to set
	 */
	public void setBnType(String bnType) {
		this.bnType = bnType;
	}
	/**
	 * @return the bnOrder
	 */
	public Integer getBnOrder() {
		return bnOrder;
	}
	/**
	 * @param bnOrder the bnOrder to set
	 */
	public void setBnOrder(Integer bnOrder) {
		this.bnOrder = bnOrder;
	}
	/**
	 * @return the bnName
	 */
	public String getBnName() {
		return bnName;
	}
	/**
	 * @param bnName the bnName to set
	 */
	public void setBnName(String bnName) {
		this.bnName = bnName;
	}
	/**
	 * @return the bnDescription
	 */
	public String getBnDescription() {
		return bnDescription;
	}
	/**
	 * @param bnDescription the bnDescription to set
	 */
	public void setBnDescription(String bnDescription) {
		this.bnDescription = bnDescription;
	}
	/**
	 * @return the bnLink
	 */
	public String getBnLink() {
		return bnLink;
	}
	/**
	 * @param bnLink the bnLink to set
	 */
	public void setBnLink(String bnLink) {
		this.bnLink = bnLink;
	}
	/**
	 * @return the bnNewWin
	 */
	public boolean isBnNewWin() {
		return bnNewWin;
	}
	/**
	 * @param bnNewWin the bnNewWin to set
	 */
	public void setBnNewWin(boolean bnNewWin) {
		this.bnNewWin = bnNewWin;
	}
	/**
	 * @return the bnStartDate
	 */
	public String getBnStartDate() {
		return bnStartDate;
	}
	/**
	 * @param bnStartDate the bnStartDate to set
	 */
	public void setBnStartDate(String bnStartDate) {
		this.bnStartDate = bnStartDate;
	}
	/**
	 * @return the bnEndDate
	 */
	public String getBnEndDate() {
		return bnEndDate;
	}
	/**
	 * @param bnEndDate the bnEndDate to set
	 */
	public void setBnEndDate(String bnEndDate) {
		this.bnEndDate = bnEndDate;
	}
	/**
	 * @return the bnUse
	 */
	public boolean isBnUse() {
		return bnUse;
	}
	/**
	 * @param bnUse the bnUse to set
	 */
	public void setBnUse(boolean bnUse) {
		this.bnUse = bnUse;
	}
	/**
	 * @return the bnRegDate
	 */
	public Date getBnRegDate() {
		return bnRegDate;
	}
	/**
	 * @param bnRegDate the bnRegDate to set
	 */
	public void setBnRegDate(Date bnRegDate) {
		this.bnRegDate = bnRegDate;
	}
	/**
	 * @return the bnImage
	 */
	public MultipartFile getBnImage() {
		return bnImage;
	}
	/**
	 * @param bnImage the bnImage to set
	 */
	public void setBnImage(MultipartFile bnImage) {
		this.bnImage = bnImage;
	}
	/**
	 * @return the bnExt
	 */
	public String getBnExt() {
		return bnExt;
	}
	/**
	 * @param bnExt the bnExt to set
	 */
	public void setBnExt(String bnExt) {
		this.bnExt = bnExt;
	}
	
	/**
	 * @return the bnWidth
	 */
	public Integer getBnWidth() {
		return bnWidth;
	}
	/**
	 * @param bnWidth the bnWidth to set
	 */
	public void setBnWidth(Integer bnWidth) {
		this.bnWidth = bnWidth;
	}
	/**
	 * @return the bnHeight
	 */
	public Integer getBnHeight() {
		return bnHeight;
	}
	/**
	 * @param bnHeight the bnHeight to set
	 */
	public void setBnHeight(Integer bnHeight) {
		this.bnHeight = bnHeight;
	}
	
	/**
	 * @return the bnTop
	 */
	public Integer getBnTop() {
		return bnTop;
	}
	/**
	 * @param bnTop the bnTop to set
	 */
	public void setBnTop(Integer bnTop) {
		this.bnTop = bnTop;
	}
	/**
	 * @return the bnLeft
	 */
	public Integer getBnLeft() {
		return bnLeft;
	}
	/**
	 * @param bnLeft the bnLeft to set
	 */
	public void setBnLeft(Integer bnLeft) {
		this.bnLeft = bnLeft;
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
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	/**
     * toString 메소드를 대치한다.
     */
	@Override
    public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
