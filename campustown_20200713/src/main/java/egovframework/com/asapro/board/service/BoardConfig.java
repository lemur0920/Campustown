/**
 * 
 */
package egovframework.com.asapro.board.service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.ArrayUtils;

import egovframework.com.asapro.site.service.MultiSiteVO;
//import egovframework.com.asapro.watchdog.aop.WatchDog;

/**
 * 게시판 설정 VO
 * @author yckim
 * @since 2018. 5. 31.
 *
 */
public class BoardConfig extends MultiSiteVO{
	
//	@WatchDog
	private String bcId;	//게시판 아이디
	private String bcName;	//게시판 이름
	private boolean bcUse = true;	//사용유무
	private String bcType = "default";	//뷰파일폴더이름 = 게시판 유형 - default, list, gallery, qna, faq audio, music
	
	private String bcCategory1;	//분류1 code
	private String bcCategory1Name = "분류1";	//분류1 이름
	private String bcCategory2;	//분류2 code
	private String bcCategory2Name = "분류2";	//분류2 이름
	private String bcCategory3;	//분류3 code
	private String bcCategory3Name = "분류3";	//분류3 이름
	private String bcStatusCode;	//진행상태코드
	private String bcCustomField1;	//사용자정의필드명 1
	private String bcCustomField2;	//사용자정의필드명 2
	private String bcCustomField3;	//사용자정의필드명 3
	private String bcCustomField4;	//사용자정의필드명 4
	private String bcCustomField5;	//사용자정의필드명 5
	private String bcCustomField6;	//사용자정의필드명 6
	private String bcCustomField7;	//사용자정의필드명 7
	private String bcCustomField8;	//사용자정의필드명 8
	private String bcCustomField9;	//사용자정의필드명 9
	private String bcCustomField10;	//사용자정의필드명 10
	private String bcCustomFieldUse;	//사용자정의필드명 사용유무 리스트(text)
	private List<String> bcCustomFieldUseArray;	//사용자정의필드명 사용유무 리스트
	
	private String bcListFile;	//목록 템플릿
	private String bcViewFile;	//뷰 템플릿
	private String bcFormFile;	//폼 템플릿
	
	//사용자 부서, 그룹 지정 - 이 값이 있을 경우 일반회원 권한보다 우선한다.
	private String bcOrganization;	//기관 코드
	private String bcDepartment;	//관리부서 코드
	private String bcGroup;	//관리 그룹 아이디
	//권한별처리나 그룹별처리는 뷰파일에서 필요한 경우에만 커스터마이징 하는게 차라리 편하다(소스도 지저분해지지 않고...)
	private boolean bcAllowMemberList = true;	//회원 목록접근 허용여부
	private boolean bcAllowMemberView = true;	//회원 글조회 접근 허용여부
	private boolean bcAllowMemberForm = false;	//회원 글쓰기/수정 접근 허용여부
	private boolean bcAllowMemberDownload = true;	//회원 첨부파일 다운로드 허용여부
	
	private boolean bcAllowGuestList = true;	//비회원 목록접근 허용여부
	private boolean bcAllowGuestView = true;	//비회원 글조회 접근 허용여부
	private boolean bcAllowGuestForm = false;	//비회원 글쓰기/수정 접근 허용여부
	private boolean bcAllowGuestDownload = false;	//비회원 첨부파일 다운로드 허용여부
	
	private boolean bcSupportNotice = false;	//공지글 기능 사용여부
	private boolean bcNoticeEveryPage = false;	//first, every 공지글 표시방법(모든페이지/첫페이지)
	private boolean bcSupportSecret = false;	//비밀글기능 사용여부
	private boolean bcSupportComment = false;	//댓글기능 사용여부
	private boolean bcSupportAnswer = false;	//답글기능 사용여부 => 차후 별도로 QnA 모듈을 개발후 사용
	private boolean bcSupportRecommend = false;	//추천기능 사용여부
	private boolean bcSupportNuri = true;	//공공누리 사용여부
	private boolean bcSupportHitDay = true;	//조회수 증가유형(하루한번/클릭시마다)
	private boolean bcSupportMainSelec = false;	//메인페이지 추출기능 사용여부
	private boolean bcSupportCommSelec = false;	//공통게시판 추출여부
	private boolean bcSupportOpenDay = false;	//게시날짜 설정 사용여부
	
	private boolean bcSupportImage = true;	//대표이미지 사용여부
	private boolean bcSupportThumbnail = true;	//썸네일 사용여부
	private boolean bcThumbnailCrop = true;	//썸네일 생성시 이미지 자르기 여부
	private Integer bcThumbnailWidth = 200;	//썸네일 생성시 가로길이
	private Integer bcThumbnailHeight = 150;	//썸네일 생성시 세로길이
	
	private Integer bcUploadFileNum = 5;	//첨부가능한 파일갯수
	private Integer bcUploadSizeMax = 10;	//첨부파일 업로드 사이즈 제한 / MB단위, 서버설정을 오버할 수 없음
	private Integer bcPageSize = 10;	//목록 페이지 크기
	
	private Date bcRegDate;	//등록일시
	
	private Integer bcArticleTotal;	//글갯수
	
	//private String[] bcDepartmentArray;	//관리부서 코드

	/**
	 * @return the bcDepartmentArray
	 */
	public String[] getBcDepartmentArray() {
		if(StringUtils.isNotBlank(this.bcDepartment) ){
			return this.bcDepartment.split("\\,");
		}
		return null;
	}

	/**
	 * @return the bcId
	 */
	public String getBcId() {
		return bcId;
	}

	/**
	 * @param bcId the bcId to set
	 */
	public void setBcId(String bcId) {
		this.bcId = bcId;
	}

	/**
	 * @return the bcName
	 */
	public String getBcName() {
		return bcName;
	}

	/**
	 * @param bcName the bcName to set
	 */
	public void setBcName(String bcName) {
		this.bcName = bcName;
	}

	/**
	 * @return the bcUse
	 */
	public boolean isBcUse() {
		return bcUse;
	}

	/**
	 * @param bcUse the bcUse to set
	 */
	public void setBcUse(boolean bcUse) {
		this.bcUse = bcUse;
	}

	/**
	 * @return the bcType
	 */
	public String getBcType() {
		return bcType;
	}

	/**
	 * @param bcType the bcType to set
	 */
	public void setBcType(String bcType) {
		this.bcType = bcType;
	}

	/**
	 * @return the bcCategory1
	 */
	public String getBcCategory1() {
		return bcCategory1;
	}

	/**
	 * @param bcCategory1 the bcCategory1 to set
	 */
	public void setBcCategory1(String bcCategory1) {
		this.bcCategory1 = bcCategory1;
	}

	/**
	 * @return the bcCategory1Name
	 */
	public String getBcCategory1Name() {
		return bcCategory1Name;
	}

	/**
	 * @param bcCategory1Name the bcCategory1Name to set
	 */
	public void setBcCategory1Name(String bcCategory1Name) {
		this.bcCategory1Name = bcCategory1Name;
	}

	/**
	 * @return the bcCategory2
	 */
	public String getBcCategory2() {
		return bcCategory2;
	}

	/**
	 * @param bcCategory2 the bcCategory2 to set
	 */
	public void setBcCategory2(String bcCategory2) {
		this.bcCategory2 = bcCategory2;
	}

	/**
	 * @return the bcCategory2Name
	 */
	public String getBcCategory2Name() {
		return bcCategory2Name;
	}

	/**
	 * @param bcCategory2Name the bcCategory2Name to set
	 */
	public void setBcCategory2Name(String bcCategory2Name) {
		this.bcCategory2Name = bcCategory2Name;
	}

	/**
	 * @return the bcCategory3
	 */
	public String getBcCategory3() {
		return bcCategory3;
	}

	/**
	 * @param bcCategory3 the bcCategory3 to set
	 */
	public void setBcCategory3(String bcCategory3) {
		this.bcCategory3 = bcCategory3;
	}

	/**
	 * @return the bcCategory3Name
	 */
	public String getBcCategory3Name() {
		return bcCategory3Name;
	}

	/**
	 * @param bcCategory3Name the bcCategory3Name to set
	 */
	public void setBcCategory3Name(String bcCategory3Name) {
		this.bcCategory3Name = bcCategory3Name;
	}

	/**
	 * @return the bcStatusCode
	 */
	public String getBcStatusCode() {
		return bcStatusCode;
	}

	/**
	 * @param bcStatusCode the bcStatusCode to set
	 */
	public void setBcStatusCode(String bcStatusCode) {
		this.bcStatusCode = bcStatusCode;
	}

	/**
	 * @return the bcListFile
	 */
	public String getBcListFile() {
		return bcListFile;
	}

	/**
	 * @param bcListFile the bcListFile to set
	 */
	public void setBcListFile(String bcListFile) {
		this.bcListFile = bcListFile;
	}

	/**
	 * @return the bcViewFile
	 */
	public String getBcViewFile() {
		return bcViewFile;
	}

	/**
	 * @param bcViewFile the bcViewFile to set
	 */
	public void setBcViewFile(String bcViewFile) {
		this.bcViewFile = bcViewFile;
	}

	/**
	 * @return the bcFormFile
	 */
	public String getBcFormFile() {
		return bcFormFile;
	}

	/**
	 * @param bcFormFile the bcFormFile to set
	 */
	public void setBcFormFile(String bcFormFile) {
		this.bcFormFile = bcFormFile;
	}

	/**
	 * @return the bcDepartment
	 */
	public String getBcDepartment() {
		return bcDepartment;
	}

	/**
	 * @param bcDepartment the bcDepartment to set
	 */
	public void setBcDepartment(String bcDepartment) {
		this.bcDepartment = bcDepartment;
	}

	/**
	 * @return the bcGroup
	 */
	public String getBcGroup() {
		return bcGroup;
	}

	/**
	 * @param bcGroup the bcGroup to set
	 */
	public void setBcGroup(String bcGroup) {
		this.bcGroup = bcGroup;
	}

	/**
	 * @return the bcAllowMemberList
	 */
	public boolean isBcAllowMemberList() {
		return bcAllowMemberList;
	}

	/**
	 * @param bcAllowMemberList the bcAllowMemberList to set
	 */
	public void setBcAllowMemberList(boolean bcAllowMemberList) {
		this.bcAllowMemberList = bcAllowMemberList;
	}

	/**
	 * @return the bcAllowMemberView
	 */
	public boolean isBcAllowMemberView() {
		return bcAllowMemberView;
	}

	/**
	 * @param bcAllowMemberView the bcAllowMemberView to set
	 */
	public void setBcAllowMemberView(boolean bcAllowMemberView) {
		this.bcAllowMemberView = bcAllowMemberView;
	}

	/**
	 * @return the bcAllowMemberForm
	 */
	public boolean isBcAllowMemberForm() {
		return bcAllowMemberForm;
	}

	/**
	 * @param bcAllowMemberForm the bcAllowMemberForm to set
	 */
	public void setBcAllowMemberForm(boolean bcAllowMemberForm) {
		this.bcAllowMemberForm = bcAllowMemberForm;
	}

	/**
	 * @return the bcAllowMemberDownload
	 */
	public boolean isBcAllowMemberDownload() {
		return bcAllowMemberDownload;
	}

	/**
	 * @param bcAllowMemberDownload the bcAllowMemberDownload to set
	 */
	public void setBcAllowMemberDownload(boolean bcAllowMemberDownload) {
		this.bcAllowMemberDownload = bcAllowMemberDownload;
	}

	/**
	 * @return the bcAllowGuestList
	 */
	public boolean isBcAllowGuestList() {
		return bcAllowGuestList;
	}

	/**
	 * @param bcAllowGuestList the bcAllowGuestList to set
	 */
	public void setBcAllowGuestList(boolean bcAllowGuestList) {
		this.bcAllowGuestList = bcAllowGuestList;
	}

	/**
	 * @return the bcAllowGuestView
	 */
	public boolean isBcAllowGuestView() {
		return bcAllowGuestView;
	}

	/**
	 * @param bcAllowGuestView the bcAllowGuestView to set
	 */
	public void setBcAllowGuestView(boolean bcAllowGuestView) {
		this.bcAllowGuestView = bcAllowGuestView;
	}

	/**
	 * @return the bcAllowGuestForm
	 */
	public boolean isBcAllowGuestForm() {
		return bcAllowGuestForm;
	}

	/**
	 * @param bcAllowGuestForm the bcAllowGuestForm to set
	 */
	public void setBcAllowGuestForm(boolean bcAllowGuestForm) {
		this.bcAllowGuestForm = bcAllowGuestForm;
	}

	/**
	 * @return the bcAllowGuestDownload
	 */
	public boolean isBcAllowGuestDownload() {
		return bcAllowGuestDownload;
	}

	/**
	 * @param bcAllowGuestDownload the bcAllowGuestDownload to set
	 */
	public void setBcAllowGuestDownload(boolean bcAllowGuestDownload) {
		this.bcAllowGuestDownload = bcAllowGuestDownload;
	}

	/**
	 * @return the bcSupportNotice
	 */
	public boolean isBcSupportNotice() {
		return bcSupportNotice;
	}

	/**
	 * @param bcSupportNotice the bcSupportNotice to set
	 */
	public void setBcSupportNotice(boolean bcSupportNotice) {
		this.bcSupportNotice = bcSupportNotice;
	}

	/**
	 * @return the bcNoticeEveryPage
	 */
	public boolean isBcNoticeEveryPage() {
		return bcNoticeEveryPage;
	}

	/**
	 * @param bcNoticeEveryPage the bcNoticeEveryPage to set
	 */
	public void setBcNoticeEveryPage(boolean bcNoticeEveryPage) {
		this.bcNoticeEveryPage = bcNoticeEveryPage;
	}

	/**
	 * @return the bcSupportSecret
	 */
	public boolean isBcSupportSecret() {
		return bcSupportSecret;
	}

	/**
	 * @param bcSupportSecret the bcSupportSecret to set
	 */
	public void setBcSupportSecret(boolean bcSupportSecret) {
		this.bcSupportSecret = bcSupportSecret;
	}

	/**
	 * @return the bcSupportComment
	 */
	public boolean isBcSupportComment() {
		return bcSupportComment;
	}

	/**
	 * @param bcSupportComment the bcSupportComment to set
	 */
	public void setBcSupportComment(boolean bcSupportComment) {
		this.bcSupportComment = bcSupportComment;
	}

	/**
	 * @return the bcSupportAnswer
	 */
	public boolean isBcSupportAnswer() {
		return bcSupportAnswer;
	}

	/**
	 * @param bcSupportAnswer the bcSupportAnswer to set
	 */
	public void setBcSupportAnswer(boolean bcSupportAnswer) {
		this.bcSupportAnswer = bcSupportAnswer;
	}

	/**
	 * @return the bcSupportRecommend
	 */
	public boolean isBcSupportRecommend() {
		return bcSupportRecommend;
	}

	/**
	 * @param bcSupportRecommend the bcSupportRecommend to set
	 */
	public void setBcSupportRecommend(boolean bcSupportRecommend) {
		this.bcSupportRecommend = bcSupportRecommend;
	}

	/**
	 * @return the bcSupportThumbnail
	 */
	public boolean isBcSupportThumbnail() {
		return bcSupportThumbnail;
	}

	/**
	 * @param bcSupportThumbnail the bcSupportThumbnail to set
	 */
	public void setBcSupportThumbnail(boolean bcSupportThumbnail) {
		this.bcSupportThumbnail = bcSupportThumbnail;
	}

	/**
	 * @return the bcThumbnailCrop
	 */
	public boolean isBcThumbnailCrop() {
		return bcThumbnailCrop;
	}

	/**
	 * @param bcThumbnailCrop the bcThumbnailCrop to set
	 */
	public void setBcThumbnailCrop(boolean bcThumbnailCrop) {
		this.bcThumbnailCrop = bcThumbnailCrop;
	}

	/**
	 * @return the bcThumbnailWidth
	 */
	public Integer getBcThumbnailWidth() {
		return bcThumbnailWidth;
	}

	/**
	 * @param bcThumbnailWidth the bcThumbnailWidth to set
	 */
	public void setBcThumbnailWidth(Integer bcThumbnailWidth) {
		this.bcThumbnailWidth = bcThumbnailWidth;
	}

	/**
	 * @return the bcThumbnailHeight
	 */
	public Integer getBcThumbnailHeight() {
		return bcThumbnailHeight;
	}

	/**
	 * @param bcThumbnailHeight the bcThumbnailHeight to set
	 */
	public void setBcThumbnailHeight(Integer bcThumbnailHeight) {
		this.bcThumbnailHeight = bcThumbnailHeight;
	}

	/**
	 * @return the bcUploadFileNum
	 */
	public Integer getBcUploadFileNum() {
		return bcUploadFileNum;
	}

	/**
	 * @param bcUploadFileNum the bcUploadFileNum to set
	 */
	public void setBcUploadFileNum(Integer bcUploadFileNum) {
		this.bcUploadFileNum = bcUploadFileNum;
	}

	/**
	 * @return the bcUploadSizeMax
	 */
	public Integer getBcUploadSizeMax() {
		return bcUploadSizeMax;
	}

	/**
	 * @param bcUploadSizeMax the bcUploadSizeMax to set
	 */
	public void setBcUploadSizeMax(Integer bcUploadSizeMax) {
		this.bcUploadSizeMax = bcUploadSizeMax;
	}

	/**
	 * @return the bcPageSize
	 */
	public Integer getBcPageSize() {
		return bcPageSize;
	}

	/**
	 * @param bcPageSize the bcPageSize to set
	 */
	public void setBcPageSize(Integer bcPageSize) {
		this.bcPageSize = bcPageSize;
	}

	/**
	 * @return the bcRegDate
	 */
	public Date getBcRegDate() {
		return bcRegDate;
	}

	/**
	 * @param bcRegDate the bcRegDate to set
	 */
	public void setBcRegDate(Date bcRegDate) {
		this.bcRegDate = bcRegDate;
	}

	/**
	 * @return the bcArticleTotal
	 */
	public Integer getBcArticleTotal() {
		return bcArticleTotal;
	}

	/**
	 * @param bcArticleTotal the bcArticleTotal to set
	 */
	public void setBcArticleTotal(Integer bcArticleTotal) {
		this.bcArticleTotal = bcArticleTotal;
	}

	/**
	 * @return the bcOrganization
	 */
	public String getBcOrganization() {
		return bcOrganization;
	}

	/**
	 * @param bcOrganization the bcOrganization to set
	 */
	public void setBcOrganization(String bcOrganization) {
		this.bcOrganization = bcOrganization;
	}

	/**
	 * @return the bcSupportNuri
	 */
	public boolean isBcSupportNuri() {
		return bcSupportNuri;
	}

	/**
	 * @param bcSupportNuri the bcSupportNuri to set
	 */
	public void setBcSupportNuri(boolean bcSupportNuri) {
		this.bcSupportNuri = bcSupportNuri;
	}

	/**
	 * @return the bcSupportImage
	 */
	public boolean isBcSupportImage() {
		return bcSupportImage;
	}

	/**
	 * @param bcSupportImage the bcSupportImage to set
	 */
	public void setBcSupportImage(boolean bcSupportImage) {
		this.bcSupportImage = bcSupportImage;
	}

	/**
	 * @return the bcSupportHitDay
	 */
	public boolean isBcSupportHitDay() {
		return bcSupportHitDay;
	}

	/**
	 * @param bcSupportHitDay the bcSupportHitDay to set
	 */
	public void setBcSupportHitDay(boolean bcSupportHitDay) {
		this.bcSupportHitDay = bcSupportHitDay;
	}

	/**
	 * @return the bcSupportMainSelec
	 */
	public boolean isBcSupportMainSelec() {
		return bcSupportMainSelec;
	}

	/**
	 * @param bcSupportMainSelec the bcSupportMainSelec to set
	 */
	public void setBcSupportMainSelec(boolean bcSupportMainSelec) {
		this.bcSupportMainSelec = bcSupportMainSelec;
	}

	/**
	 * @return the bcSupportCommSelec
	 */
	public boolean isBcSupportCommSelec() {
		return bcSupportCommSelec;
	}

	/**
	 * @param bcSupportCommSelec the bcSupportCommSelec to set
	 */
	public void setBcSupportCommSelec(boolean bcSupportCommSelec) {
		this.bcSupportCommSelec = bcSupportCommSelec;
	}

	/**
	 * @return the bcSupportOpenDay
	 */
	public boolean isBcSupportOpenDay() {
		return bcSupportOpenDay;
	}

	/**
	 * @param bcSupportOpenDay the bcSupportOpenDay to set
	 */
	public void setBcSupportOpenDay(boolean bcSupportOpenDay) {
		this.bcSupportOpenDay = bcSupportOpenDay;
	}

	/**
	 * @return the bcCustomField1
	 */
	public String getBcCustomField1() {
		return bcCustomField1;
	}

	/**
	 * @param bcCustomField1 the bcCustomField1 to set
	 */
	public void setBcCustomField1(String bcCustomField1) {
		this.bcCustomField1 = bcCustomField1;
	}

	/**
	 * @return the bcCustomField2
	 */
	public String getBcCustomField2() {
		return bcCustomField2;
	}

	/**
	 * @param bcCustomField2 the bcCustomField2 to set
	 */
	public void setBcCustomField2(String bcCustomField2) {
		this.bcCustomField2 = bcCustomField2;
	}

	/**
	 * @return the bcCustomField3
	 */
	public String getBcCustomField3() {
		return bcCustomField3;
	}

	/**
	 * @param bcCustomField3 the bcCustomField3 to set
	 */
	public void setBcCustomField3(String bcCustomField3) {
		this.bcCustomField3 = bcCustomField3;
	}

	/**
	 * @return the bcCustomField4
	 */
	public String getBcCustomField4() {
		return bcCustomField4;
	}

	/**
	 * @param bcCustomField4 the bcCustomField4 to set
	 */
	public void setBcCustomField4(String bcCustomField4) {
		this.bcCustomField4 = bcCustomField4;
	}

	/**
	 * @return the bcCustomField5
	 */
	public String getBcCustomField5() {
		return bcCustomField5;
	}

	/**
	 * @param bcCustomField5 the bcCustomField5 to set
	 */
	public void setBcCustomField5(String bcCustomField5) {
		this.bcCustomField5 = bcCustomField5;
	}

	/**
	 * @return the bcCustomField6
	 */
	public String getBcCustomField6() {
		return bcCustomField6;
	}

	/**
	 * @param bcCustomField6 the bcCustomField6 to set
	 */
	public void setBcCustomField6(String bcCustomField6) {
		this.bcCustomField6 = bcCustomField6;
	}

	/**
	 * @return the bcCustomField7
	 */
	public String getBcCustomField7() {
		return bcCustomField7;
	}

	/**
	 * @param bcCustomField7 the bcCustomField7 to set
	 */
	public void setBcCustomField7(String bcCustomField7) {
		this.bcCustomField7 = bcCustomField7;
	}

	/**
	 * @return the bcCustomField8
	 */
	public String getBcCustomField8() {
		return bcCustomField8;
	}

	/**
	 * @param bcCustomField8 the bcCustomField8 to set
	 */
	public void setBcCustomField8(String bcCustomField8) {
		this.bcCustomField8 = bcCustomField8;
	}

	/**
	 * @return the bcCustomField9
	 */
	public String getBcCustomField9() {
		return bcCustomField9;
	}

	/**
	 * @param bcCustomField9 the bcCustomField9 to set
	 */
	public void setBcCustomField9(String bcCustomField9) {
		this.bcCustomField9 = bcCustomField9;
	}

	/**
	 * @return the bcCustomField10
	 */
	public String getBcCustomField10() {
		return bcCustomField10;
	}

	/**
	 * @param bcCustomField10 the bcCustomField10 to set
	 */
	public void setBcCustomField10(String bcCustomField10) {
		this.bcCustomField10 = bcCustomField10;
	}

	/**
	 * @return the bcCustomFieldUse
	 */
	public String getBcCustomFieldUse() {
		return bcCustomFieldUse;
	}

	/**
	 * @param bcCustomFieldUse the bcCustomFieldUse to set
	 */
	public void setBcCustomFieldUse(String bcCustomFieldUse) {
		this.bcCustomFieldUse = bcCustomFieldUse;
	}

	/**
	 * @return the bcCustomFieldUseArray
	 */
	public List<String> getBcCustomFieldUseArray() {
		return bcCustomFieldUseArray;
	}

	/**
	 * @param bcCustomFieldUseArray the bcCustomFieldUseArray to set
	 */
	public void setBcCustomFieldUseArray(List<String> bcCustomFieldUseArray) {
		this.bcCustomFieldUseArray = bcCustomFieldUseArray;
	}
	
	//=============================================================================
	
	public List<String> getBcCustomFieldUseToArray() {
		List<String> bcCustomFieldUseToArray = null;
		if(this.bcCustomFieldUse != null){
			bcCustomFieldUseToArray = Arrays.asList(this.bcCustomFieldUse.split(","));
		}
		return bcCustomFieldUseToArray;
	}


	
}
