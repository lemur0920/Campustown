/**
 * 
 */
package egovframework.com.asapro.comment.service;

import java.util.Date;
import java.util.List;

import egovframework.com.asapro.site.service.MultiSiteVO;
import egovframework.com.asapro.comment.service.Comment;

/**
 * 댓글 VO
 * @author yckim
 * @since 2019. 7. 12.
 *
 */
public class Comment extends MultiSiteVO {
	private Integer cmtId;	//댓글 아이디
	private Integer cmtParentId;	//부모댓글 아이디
	private String cmtTitle;	//원본글 제목
	private String cmtContent;	//댓글 내용
	private String cmtModule;	//댓글 사용 모듈
	private String cmtModuleSub;	//댓글 사용 하위모듈
	private String cmtModCategory;	//댓글 사용 모듈 카테고리
	private String cmtModItemId;	//댓글 사용 아이템 아이디
	private String cmtPageUrl;	//댓글 작성된 페이지 URL
	private String cmtLoginType;	//로그인 타입 -- 2: FACEBOOK , 4: NAVER, 8: KAKAO  ,16: phone ,32: IPIN
	private String cmtUserId;	//작성자 아이디
	private String cmtSnsUserId;	//SNS 계정 아이디
	private String cmtSnsUserName;	//SNS 계정 이름(닉네임)
	private String cmtSnsUserHome;	//SNS 작성자 홈
	private String cmtProfileImage;	//작성자 프로필 이미지
	private Integer cmtAgree = 0;	//찬성수
	private Integer cmtDisagree = 0;	//반대수
	private Integer cmtRecommend = 0;	//추천수
	private Integer cmtReport = 0;	//신고수
	private String cmtRegIp;	//댓글 작성자 아이피
	private Date cmtRegDate;	//댓글 작성일시
	private String cmtStatus;	//댓글 상태 - // APPROVED : 승인 , DELETED : 삭제 , WAITING : 대기
	
	private Comment cmtParent;	//부모댓글
	private List<Comment> cmtChildren;	//자식댓글
	/**
	 * @return the cmtId
	 */
	public Integer getCmtId() {
		return cmtId;
	}
	/**
	 * @param cmtId the cmtId to set
	 */
	public void setCmtId(Integer cmtId) {
		this.cmtId = cmtId;
	}
	/**
	 * @return the cmtParentId
	 */
	public Integer getCmtParentId() {
		return cmtParentId;
	}
	/**
	 * @param cmtParentId the cmtParentId to set
	 */
	public void setCmtParentId(Integer cmtParentId) {
		this.cmtParentId = cmtParentId;
	}
	/**
	 * @return the cmtTitle
	 */
	public String getCmtTitle() {
		return cmtTitle;
	}
	/**
	 * @param cmtTitle the cmtTitle to set
	 */
	public void setCmtTitle(String cmtTitle) {
		this.cmtTitle = cmtTitle;
	}
	/**
	 * @return the cmtContent
	 */
	public String getCmtContent() {
		return cmtContent;
	}
	/**
	 * @param cmtContent the cmtContent to set
	 */
	public void setCmtContent(String cmtContent) {
		this.cmtContent = cmtContent;
	}
	/**
	 * @return the cmtModule
	 */
	public String getCmtModule() {
		return cmtModule;
	}
	/**
	 * @param cmtModule the cmtModule to set
	 */
	public void setCmtModule(String cmtModule) {
		this.cmtModule = cmtModule;
	}
	/**
	 * @return the cmtModuleSub
	 */
	public String getCmtModuleSub() {
		return cmtModuleSub;
	}
	/**
	 * @param cmtModuleSub the cmtModuleSub to set
	 */
	public void setCmtModuleSub(String cmtModuleSub) {
		this.cmtModuleSub = cmtModuleSub;
	}
	/**
	 * @return the cmtModCategory
	 */
	public String getCmtModCategory() {
		return cmtModCategory;
	}
	/**
	 * @param cmtModCategory the cmtModCategory to set
	 */
	public void setCmtModCategory(String cmtModCategory) {
		this.cmtModCategory = cmtModCategory;
	}
	/**
	 * @return the cmtModItemId
	 */
	public String getCmtModItemId() {
		return cmtModItemId;
	}
	/**
	 * @param cmtModItemId the cmtModItemId to set
	 */
	public void setCmtModItemId(String cmtModItemId) {
		this.cmtModItemId = cmtModItemId;
	}
	/**
	 * @return the cmtPageUrl
	 */
	public String getCmtPageUrl() {
		return cmtPageUrl;
	}
	/**
	 * @param cmtPageUrl the cmtPageUrl to set
	 */
	public void setCmtPageUrl(String cmtPageUrl) {
		this.cmtPageUrl = cmtPageUrl;
	}
	/**
	 * @return the cmtLoginType
	 */
	public String getCmtLoginType() {
		return cmtLoginType;
	}
	/**
	 * @param cmtLoginType the cmtLoginType to set
	 */
	public void setCmtLoginType(String cmtLoginType) {
		this.cmtLoginType = cmtLoginType;
	}
	/**
	 * @return the cmtUserId
	 */
	public String getCmtUserId() {
		return cmtUserId;
	}
	/**
	 * @param cmtUserId the cmtUserId to set
	 */
	public void setCmtUserId(String cmtUserId) {
		this.cmtUserId = cmtUserId;
	}
	/**
	 * @return the cmtSnsUserId
	 */
	public String getCmtSnsUserId() {
		return cmtSnsUserId;
	}
	/**
	 * @param cmtSnsUserId the cmtSnsUserId to set
	 */
	public void setCmtSnsUserId(String cmtSnsUserId) {
		this.cmtSnsUserId = cmtSnsUserId;
	}
	/**
	 * @return the cmtSnsUserName
	 */
	public String getCmtSnsUserName() {
		return cmtSnsUserName;
	}
	/**
	 * @param cmtSnsUserName the cmtSnsUserName to set
	 */
	public void setCmtSnsUserName(String cmtSnsUserName) {
		this.cmtSnsUserName = cmtSnsUserName;
	}
	/**
	 * @return the cmtSnsUserHome
	 */
	public String getCmtSnsUserHome() {
		return cmtSnsUserHome;
	}
	/**
	 * @param cmtSnsUserHome the cmtSnsUserHome to set
	 */
	public void setCmtSnsUserHome(String cmtSnsUserHome) {
		this.cmtSnsUserHome = cmtSnsUserHome;
	}
	/**
	 * @return the cmtProfileImage
	 */
	public String getCmtProfileImage() {
		return cmtProfileImage;
	}
	/**
	 * @param cmtProfileImage the cmtProfileImage to set
	 */
	public void setCmtProfileImage(String cmtProfileImage) {
		this.cmtProfileImage = cmtProfileImage;
	}
	/**
	 * @return the cmtAgree
	 */
	public Integer getCmtAgree() {
		return cmtAgree;
	}
	/**
	 * @param cmtAgree the cmtAgree to set
	 */
	public void setCmtAgree(Integer cmtAgree) {
		this.cmtAgree = cmtAgree;
	}
	/**
	 * @return the cmtDisagree
	 */
	public Integer getCmtDisagree() {
		return cmtDisagree;
	}
	/**
	 * @param cmtDisagree the cmtDisagree to set
	 */
	public void setCmtDisagree(Integer cmtDisagree) {
		this.cmtDisagree = cmtDisagree;
	}
	/**
	 * @return the cmtRecommend
	 */
	public Integer getCmtRecommend() {
		return cmtRecommend;
	}
	/**
	 * @param cmtRecommend the cmtRecommend to set
	 */
	public void setCmtRecommend(Integer cmtRecommend) {
		this.cmtRecommend = cmtRecommend;
	}
	/**
	 * @return the cmtReport
	 */
	public Integer getCmtReport() {
		return cmtReport;
	}
	/**
	 * @param cmtReport the cmtReport to set
	 */
	public void setCmtReport(Integer cmtReport) {
		this.cmtReport = cmtReport;
	}
	/**
	 * @return the cmtRegIp
	 */
	public String getCmtRegIp() {
		return cmtRegIp;
	}
	/**
	 * @param cmtRegIp the cmtRegIp to set
	 */
	public void setCmtRegIp(String cmtRegIp) {
		this.cmtRegIp = cmtRegIp;
	}
	/**
	 * @return the cmtRegDate
	 */
	public Date getCmtRegDate() {
		return cmtRegDate;
	}
	/**
	 * @param cmtRegDate the cmtRegDate to set
	 */
	public void setCmtRegDate(Date cmtRegDate) {
		this.cmtRegDate = cmtRegDate;
	}
	/**
	 * @return the cmtStatus
	 */
	public String getCmtStatus() {
		return cmtStatus;
	}
	/**
	 * @param cmtStatus the cmtStatus to set
	 */
	public void setCmtStatus(String cmtStatus) {
		this.cmtStatus = cmtStatus;
	}
	/**
	 * @return the cmtParent
	 */
	public Comment getCmtParent() {
		return cmtParent;
	}
	/**
	 * @param cmtParent the cmtParent to set
	 */
	public void setCmtParent(Comment cmtParent) {
		this.cmtParent = cmtParent;
	}
	/**
	 * @return the cmtChildren
	 */
	public List<Comment> getCmtChildren() {
		return cmtChildren;
	}
	/**
	 * @param cmtChildren the cmtChildren to set
	 */
	public void setCmtChildren(List<Comment> cmtChildren) {
		this.cmtChildren = cmtChildren;
	}
	
	
	
}
