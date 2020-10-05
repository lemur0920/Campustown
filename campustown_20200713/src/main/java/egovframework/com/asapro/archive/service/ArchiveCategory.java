/**
 * 
 */
package egovframework.com.asapro.archive.service;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import egovframework.com.asapro.site.service.MultiSiteVO;


/**
 * 아카이브 카테고리 VO
 * @author yckim
 * @since 2018. 12. 19.
 *
 */
public class ArchiveCategory extends MultiSiteVO{
	
	private String catId;	//케테고리 아이디
	private String catName;	//카테고리 이름
	private String catCustomType;	//아카이브 타입
	private String catMetaCode1;	//메타코드1
	private String catMetaCode2;	//메타코드2
	private String catMetaCode3;	//메타코드3
	private String catUserListTemplate;	//사용자 목록 템플릿
	private String catUserViewTemplate;	//사용자 뷰 템플릿
	private String catAdminListTemplate;	//관리자 목록 템플릿
	private String catAdminFormTemplate;	//관리자 폼 템플릿
	private Integer catPageSize = 10;	//페이지 크기
	private String catSortOrder;	//정렬필드
	private String catSortDirection = "DESC";	//정렬방향
	private Date catRegdate;	//등록일시
	
	private Integer catArchiveCount;//아카이브 수량
	
	private boolean catSupportSelect1 = false;	//추출1 기능 사용여부
	private boolean catSupportNuri = false;	//공공누리 사용여부
	private boolean catSupportRecommend = false;	//추천기능 사요여부
	private boolean catSupportFixing = false;	//상단 별도분리 출력 사용여부
	private Integer catFixingNum = 2;	//상단 별도분리 출력 갯수
	
	private boolean catSupportImage = true;	//대표이미지 사용여부
	private boolean catSupportThumbnail = true;	//썸네일 사용여부
	private boolean catThumbnailCrop = true;	//썸네일 생성시 이미지 자르기 여부
	private Integer catThumbnailWidth = 300;	//썸네일 생성시 가로길이
	private Integer catThumbnailHeight = 400;	//썸네일 생성시 세로길이
	
	private Integer catUploadFileNum = 5;	//첨부가능한 파일갯수
	private Integer catUploadSizeMax = 10;	//첨부파일 업로드 사이즈 제한 / MB단위, 서버설정을 오버할 수 없음
	
	/**
	 * @return the catId
	 */
	public String getCatId() {
		return catId;
	}
	/**
	 * @param catId the catId to set
	 */
	public void setCatId(String catId) {
		this.catId = catId;
	}
	/**
	 * @return the catName
	 */
	public String getCatName() {
		return catName;
	}
	/**
	 * @param catName the catName to set
	 */
	public void setCatName(String catName) {
		this.catName = catName;
	}
	/**
	 * @return the catPageSize
	 */
	public Integer getCatPageSize() {
		return catPageSize;
	}
	/**
	 * @param catPageSize the catPageSize to set
	 */
	public void setCatPageSize(Integer catPageSize) {
		this.catPageSize = catPageSize;
	}
	/**
	 * @return the catMetaCode1
	 */
	public String getCatMetaCode1() {
		return catMetaCode1;
	}
	/**
	 * @param catMetaCode1 the catMetaCode1 to set
	 */
	public void setCatMetaCode1(String catMetaCode1) {
		this.catMetaCode1 = catMetaCode1;
	}
	/**
	 * @return the catMetaCode2
	 */
	public String getCatMetaCode2() {
		return catMetaCode2;
	}
	/**
	 * @param catMetaCode2 the catMetaCode2 to set
	 */
	public void setCatMetaCode2(String catMetaCode2) {
		this.catMetaCode2 = catMetaCode2;
	}
	/**
	 * @return the catMetaCode3
	 */
	public String getCatMetaCode3() {
		return catMetaCode3;
	}
	/**
	 * @param catMetaCode3 the catMetaCode3 to set
	 */
	public void setCatMetaCode3(String catMetaCode3) {
		this.catMetaCode3 = catMetaCode3;
	}
	/**
	 * @return the catSortOrder
	 */
	public String getCatSortOrder() {
		return catSortOrder;
	}
	/**
	 * @param catSortOrder the catSortOrder to set
	 */
	public void setCatSortOrder(String catSortOrder) {
		this.catSortOrder = catSortOrder;
	}
	/**
	 * @return the catSortDirection
	 */
	public String getCatSortDirection() {
		return catSortDirection;
	}
	/**
	 * @param catSortDirection the catSortDirection to set
	 */
	public void setCatSortDirection(String catSortDirection) {
		this.catSortDirection = catSortDirection;
	}
	/**
	 * @return the catArchiveCount
	 */
	public Integer getCatArchiveCount() {
		return catArchiveCount;
	}
	/**
	 * @param catArchiveCount the catArchiveCount to set
	 */
	public void setCatArchiveCount(Integer catArchiveCount) {
		this.catArchiveCount = catArchiveCount;
	}
	/**
	 * @return the catRegdate
	 */
	public Date getCatRegdate() {
		return catRegdate;
	}
	/**
	 * @param catRegdate the catRegdate to set
	 */
	public void setCatRegdate(Date catRegdate) {
		this.catRegdate = catRegdate;
	}
	/**
	 * @return the catCustomType
	 */
	public String getCatCustomType() {
		return catCustomType;
	}
	/**
	 * @param catCustomType the catCustomType to set
	 */
	public void setCatCustomType(String catCustomType) {
		this.catCustomType = catCustomType;
	}
	/**
	 * @return the catUserListTemplate
	 */
	public String getCatUserListTemplate() {
		return catUserListTemplate;
	}
	/**
	 * @param catUserListTemplate the catUserListTemplate to set
	 */
	public void setCatUserListTemplate(String catUserListTemplate) {
		this.catUserListTemplate = catUserListTemplate;
	}
	/**
	 * @return the catUserViewTemplate
	 */
	public String getCatUserViewTemplate() {
		return catUserViewTemplate;
	}
	/**
	 * @param catUserViewTemplate the catUserViewTemplate to set
	 */
	public void setCatUserViewTemplate(String catUserViewTemplate) {
		this.catUserViewTemplate = catUserViewTemplate;
	}
	/**
	 * @return the catAdminListTemplate
	 */
	public String getCatAdminListTemplate() {
		return catAdminListTemplate;
	}
	/**
	 * @param catAdminListTemplate the catAdminListTemplate to set
	 */
	public void setCatAdminListTemplate(String catAdminListTemplate) {
		this.catAdminListTemplate = catAdminListTemplate;
	}
	/**
	 * @return the catAdminFormTemplate
	 */
	public String getCatAdminFormTemplate() {
		return catAdminFormTemplate;
	}
	/**
	 * @param catAdminFormTemplate the catAdminFormTemplate to set
	 */
	public void setCatAdminFormTemplate(String catAdminFormTemplate) {
		this.catAdminFormTemplate = catAdminFormTemplate;
	}
	/**
	 * @return the catSupportSelect1
	 */
	public boolean isCatSupportSelect1() {
		return catSupportSelect1;
	}
	/**
	 * @param catSupportSelect1 the catSupportSelect1 to set
	 */
	public void setCatSupportSelect1(boolean catSupportSelect1) {
		this.catSupportSelect1 = catSupportSelect1;
	}
	/**
	 * @return the catSupportNuri
	 */
	public boolean isCatSupportNuri() {
		return catSupportNuri;
	}
	/**
	 * @param catSupportNuri the catSupportNuri to set
	 */
	public void setCatSupportNuri(boolean catSupportNuri) {
		this.catSupportNuri = catSupportNuri;
	}
	
	/**
	 * @return the catSupportRecommend
	 */
	public boolean isCatSupportRecommend() {
		return catSupportRecommend;
	}
	/**
	 * @param catSupportRecommend the catSupportRecommend to set
	 */
	public void setCatSupportRecommend(boolean catSupportRecommend) {
		this.catSupportRecommend = catSupportRecommend;
	}
	/**
	 * @return the catSupportFixing
	 */
	public boolean isCatSupportFixing() {
		return catSupportFixing;
	}
	/**
	 * @param catSupportFixing the catSupportFixing to set
	 */
	public void setCatSupportFixing(boolean catSupportFixing) {
		this.catSupportFixing = catSupportFixing;
	}
	/**
	 * @return the catFixingNum
	 */
	public Integer getCatFixingNum() {
		return catFixingNum;
	}
	/**
	 * @param catFixingNum the catFixingNum to set
	 */
	public void setCatFixingNum(Integer catFixingNum) {
		this.catFixingNum = catFixingNum;
	}
	/**
	 * @return the catSupportImage
	 */
	public boolean isCatSupportImage() {
		return catSupportImage;
	}
	/**
	 * @param catSupportImage the catSupportImage to set
	 */
	public void setCatSupportImage(boolean catSupportImage) {
		this.catSupportImage = catSupportImage;
	}
	/**
	 * @return the catSupportThumbnail
	 */
	public boolean isCatSupportThumbnail() {
		return catSupportThumbnail;
	}
	/**
	 * @param catSupportThumbnail the catSupportThumbnail to set
	 */
	public void setCatSupportThumbnail(boolean catSupportThumbnail) {
		this.catSupportThumbnail = catSupportThumbnail;
	}
	/**
	 * @return the catThumbnailCrop
	 */
	public boolean isCatThumbnailCrop() {
		return catThumbnailCrop;
	}
	/**
	 * @param catThumbnailCrop the catThumbnailCrop to set
	 */
	public void setCatThumbnailCrop(boolean catThumbnailCrop) {
		this.catThumbnailCrop = catThumbnailCrop;
	}
	/**
	 * @return the catThumbnailWidth
	 */
	public Integer getCatThumbnailWidth() {
		return catThumbnailWidth;
	}
	/**
	 * @param catThumbnailWidth the catThumbnailWidth to set
	 */
	public void setCatThumbnailWidth(Integer catThumbnailWidth) {
		this.catThumbnailWidth = catThumbnailWidth;
	}
	/**
	 * @return the catThumbnailHeight
	 */
	public Integer getCatThumbnailHeight() {
		return catThumbnailHeight;
	}
	/**
	 * @param catThumbnailHeight the catThumbnailHeight to set
	 */
	public void setCatThumbnailHeight(Integer catThumbnailHeight) {
		this.catThumbnailHeight = catThumbnailHeight;
	}
	/**
	 * @return the catUploadFileNum
	 */
	public Integer getCatUploadFileNum() {
		return catUploadFileNum;
	}
	/**
	 * @param catUploadFileNum the catUploadFileNum to set
	 */
	public void setCatUploadFileNum(Integer catUploadFileNum) {
		this.catUploadFileNum = catUploadFileNum;
	}
	/**
	 * @return the catUploadSizeMax
	 */
	public Integer getCatUploadSizeMax() {
		return catUploadSizeMax;
	}
	/**
	 * @param catUploadSizeMax the catUploadSizeMax to set
	 */
	public void setCatUploadSizeMax(Integer catUploadSizeMax) {
		this.catUploadSizeMax = catUploadSizeMax;
	}
	
	
	
}
