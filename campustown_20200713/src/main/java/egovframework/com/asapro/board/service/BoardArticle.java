/**
 * 
 */
package egovframework.com.asapro.board.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import egovframework.com.asapro.fileinfo.service.FileInfo;
import egovframework.com.asapro.member.Member;
import egovframework.com.asapro.member.admin.service.AdminMember;
import egovframework.com.asapro.member.user.service.UserMember;
//import egovframework.com.asapro.member.service.Member;
import egovframework.com.asapro.site.service.MultiSiteVO;
import egovframework.com.asapro.site.service.Site;
import egovframework.com.asapro.support.util.AsaproUtils;

/**
 * 게시물 VO
 * @author yckim
 * @since 2018. 6. 7.
 *
 */
public class BoardArticle extends MultiSiteVO{
	
	private Integer baId;	//게시글 아이디
	private String bcId;	//게시판 아이디
	private String baTitle;	//게시글 제목
	private String baContentHtml = "";	//본문 html - CLOB
	private String baContentPlain = "";	//본문 plain text - 4000자 제한
	private Boolean baNotice = false;	//공지글 여부
	private String baNoticeStartDate;	//공지글 시작일
	private String baNoticeEndDate;	//공지글 종료일
	private Boolean baSecret = false;	//비밀글 여부
	private String baSecretPassword = "";	//비밀글 비밀번호
	private String baCategory1 = "";	//분류1(코드)
	private String baCategory2 = "";	//분류2(코드)
	private String baCategory3 = "";	//분류3(코드)
	private String baStatus = "";	//진행상태(코드)
	private String baCustomField1 = "";	//사용자 정의 필드 1
	private String baCustomField2 = "";	//사용자 정의 필드 2
	private String baCustomField3 = "";	//사용자 정의 필드 3
	private String baCustomField4 = "";	//사용자 정의 필드 4
	private String baCustomField5 = "";	//사용자 정의 필드 5
	private String baCustomField6 = "";	//사용자 정의 필드 6
	private String baCustomField7 = "";	//사용자 정의 필드 7
	private String baCustomField8 = "";	//사용자 정의 필드 8
	private String baCustomField9 = "";	//사용자 정의 필드 9
	private String baCustomField10 = "";	//사용자 정의 필드 10
	private String memId="";	//작성자 아이디
	private String depId="";	//작성자 부서코드
	private String teamId="";	//작성자 팀코드
	private String baGuestName = "";	//비회원 작성자 이름
	private String baGuestPassword = "";	//비회원 작성 비밀번호
	private String baEmail = "";	//작성자 이메일
	private String baIp = "";	//작성자 아이피
	private Date baRegDate;	//등록일시
	private Date baLastModified;	//마지막 수정일시
	private String baUpdaterId = "";	//수정자 아이디
	private Integer baHit = 0;	//조회수
	private Integer baRecommend = 0;	//추천수
	private Integer baCommentTotal = 0;	//댓글수
	private String baNuri;	//공공누리
	private Integer baThumb;	//대표이미지 아이디
	private Boolean baUse = true;	//게시여부
	private Boolean baMainSelec = false;	//메인페이지 추출
	private String baComSelCat = "";	//공통추출 대상 분류코드
	private Boolean baCommSelec = false;	//공통게시판 추출
	private String baStartDate;	//게시 시작일
	private String baStartTime;	//게시 시작시간
	private String baEndDate;	//게시 종료일
	
	
	private Member member;	//회원정보 (관리자, 사용자가 공통으로 기본정보 받아서 쓰는 용도)
	private AdminMember adminMember;	//작성자 관리자
	private UserMember userMember;		//작성자 일반 사용자
	private BoardConfig boardConfig;	//게시판 정보
	
	private List<FileInfo> baFileInfos;	//첨부파일 목록
	private List<String> baAltTexts;	//??? 파일정보사용 
	private String baThumbText = "";	//대표이미지 대체텍스트
	private String baAnswer = "";	//답글
	
	private FileInfo thumb;	//대표이미지
	
	
	private MultipartFile baThumbFile;	//대표이미지 첨부파일
	private List<MultipartFile> baMultipartFiles;	//파일첨부필드
	private List<Integer> fileInfoDeleteIds;	// 파일 삭제필드
	
	private Integer csCount = 0;	//통계 취합수

	/**
	 * @return the baId
	 */
	public Integer getBaId() {
		return baId;
	}

	/**
	 * @param baId the baId to set
	 */
	public void setBaId(Integer baId) {
		this.baId = baId;
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
	 * @return the baTitle
	 */
	public String getBaTitle() {
		return baTitle;
	}

	/**
	 * @param baTitle the baTitle to set
	 */
	public void setBaTitle(String baTitle) {
		this.baTitle = baTitle;
	}

	/**
	 * @return the baContentHtml
	 */
	public String getBaContentHtml() {
		return baContentHtml;
	}

	/**
	 * @param baContentHtml the baContentHtml to set
	 */
	public void setBaContentHtml(String baContentHtml) {
		this.baContentHtml = baContentHtml;
	}

	/**
	 * @return the baContentPlain
	 */
	public String getBaContentPlain() {
		return baContentPlain;
	}

	/**
	 * @param baContentPlain the baContentPlain to set
	 */
	public void setBaContentPlain(String baContentPlain) {
		this.baContentPlain = baContentPlain;
	}

	/**
	 * @return the baNotice
	 */
	public Boolean getBaNotice() {
		return baNotice;
	}

	/**
	 * @param baNotice the baNotice to set
	 */
	public void setBaNotice(Boolean baNotice) {
		this.baNotice = baNotice;
	}

	/**
	 * @return the baNoticeStartDate
	 */
	public String getBaNoticeStartDate() {
		return baNoticeStartDate;
	}

	/**
	 * @param baNoticeStartDate the baNoticeStartDate to set
	 */
	public void setBaNoticeStartDate(String baNoticeStartDate) {
		this.baNoticeStartDate = baNoticeStartDate;
	}

	/**
	 * @return the baNoticeEndDate
	 */
	public String getBaNoticeEndDate() {
		return baNoticeEndDate;
	}

	/**
	 * @param baNoticeEndDate the baNoticeEndDate to set
	 */
	public void setBaNoticeEndDate(String baNoticeEndDate) {
		this.baNoticeEndDate = baNoticeEndDate;
	}

	/**
	 * @return the baSecret
	 */
	public Boolean getBaSecret() {
		return baSecret;
	}

	/**
	 * @param baSecret the baSecret to set
	 */
	public void setBaSecret(Boolean baSecret) {
		this.baSecret = baSecret;
	}

	/**
	 * @return the baSecretPassword
	 */
	public String getBaSecretPassword() {
		return baSecretPassword;
	}

	/**
	 * @param baSecretPassword the baSecretPassword to set
	 */
	public void setBaSecretPassword(String baSecretPassword) {
		this.baSecretPassword = baSecretPassword;
	}

	/**
	 * @return the baCategory1
	 */
	public String getBaCategory1() {
		return baCategory1;
	}

	/**
	 * @param baCategory1 the baCategory1 to set
	 */
	public void setBaCategory1(String baCategory1) {
		this.baCategory1 = baCategory1;
	}

	/**
	 * @return the baCategory2
	 */
	public String getBaCategory2() {
		return baCategory2;
	}

	/**
	 * @param baCategory2 the baCategory2 to set
	 */
	public void setBaCategory2(String baCategory2) {
		this.baCategory2 = baCategory2;
	}

	/**
	 * @return the baCategory3
	 */
	public String getBaCategory3() {
		return baCategory3;
	}

	/**
	 * @param baCategory3 the baCategory3 to set
	 */
	public void setBaCategory3(String baCategory3) {
		this.baCategory3 = baCategory3;
	}

	/**
	 * @return the baStatus
	 */
	public String getBaStatus() {
		return baStatus;
	}

	/**
	 * @param baStatus the baStatus to set
	 */
	public void setBaStatus(String baStatus) {
		this.baStatus = baStatus;
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
	 * @return the depId
	 */
	public String getDepId() {
		return depId;
	}

	/**
	 * @param depId the depId to set
	 */
	public void setDepId(String depId) {
		this.depId = depId;
	}

	/**
	 * @return the teamId
	 */
	public String getTeamId() {
		return teamId;
	}

	/**
	 * @param teamId the teamId to set
	 */
	public void setTeamId(String teamId) {
		this.teamId = teamId;
	}

	/**
	 * @return the baGuestName
	 */
	public String getBaGuestName() {
		return baGuestName;
	}

	/**
	 * @param baGuestName the baGuestName to set
	 */
	public void setBaGuestName(String baGuestName) {
		this.baGuestName = baGuestName;
	}

	/**
	 * @return the baGuestPassword
	 */
	public String getBaGuestPassword() {
		return baGuestPassword;
	}

	/**
	 * @param baGuestPassword the baGuestPassword to set
	 */
	public void setBaGuestPassword(String baGuestPassword) {
		this.baGuestPassword = baGuestPassword;
	}

	/**
	 * @return the baEmail
	 */
	public String getBaEmail() {
		return baEmail;
	}

	/**
	 * @param baEmail the baEmail to set
	 */
	public void setBaEmail(String baEmail) {
		this.baEmail = baEmail;
	}

	/**
	 * @return the baIp
	 */
	public String getBaIp() {
		return baIp;
	}

	/**
	 * @param baIp the baIp to set
	 */
	public void setBaIp(String baIp) {
		this.baIp = baIp;
	}

	/**
	 * @return the baRegDate
	 */
	public Date getBaRegDate() {
		return baRegDate;
	}

	/**
	 * @param baRegDate the baRegDate to set
	 */
	public void setBaRegDate(Date baRegDate) {
		this.baRegDate = baRegDate;
	}

	/**
	 * @return the baLastModified
	 */
	public Date getBaLastModified() {
		return baLastModified;
	}

	/**
	 * @param baLastModified the baLastModified to set
	 */
	public void setBaLastModified(Date baLastModified) {
		this.baLastModified = baLastModified;
	}

	/**
	 * @return the baUpdaterId
	 */
	public String getBaUpdaterId() {
		return baUpdaterId;
	}

	/**
	 * @param baUpdaterId the baUpdaterId to set
	 */
	public void setBaUpdaterId(String baUpdaterId) {
		this.baUpdaterId = baUpdaterId;
	}

	/**
	 * @return the baHit
	 */
	public Integer getBaHit() {
		return baHit;
	}

	/**
	 * @param baHit the baHit to set
	 */
	public void setBaHit(Integer baHit) {
		this.baHit = baHit;
	}

	/**
	 * @return the baRecommend
	 */
	public Integer getBaRecommend() {
		return baRecommend;
	}

	/**
	 * @param baRecommend the baRecommend to set
	 */
	public void setBaRecommend(Integer baRecommend) {
		this.baRecommend = baRecommend;
	}

	/**
	 * @return the baCommentTotal
	 */
	public Integer getBaCommentTotal() {
		return baCommentTotal;
	}

	/**
	 * @param baCommentTotal the baCommentTotal to set
	 */
	public void setBaCommentTotal(Integer baCommentTotal) {
		this.baCommentTotal = baCommentTotal;
	}

	/**
	 * @return the baThumb
	 */
	public Integer getBaThumb() {
		return baThumb;
	}

	/**
	 * @param baThumb the baThumb to set
	 */
	public void setBaThumb(Integer baThumb) {
		this.baThumb = baThumb;
	}

	/**
	 * @return the baNuri
	 */
	public String getBaNuri() {
		return baNuri;
	}

	/**
	 * @param baNuri the baNuri to set
	 */
	public void setBaNuri(String baNuri) {
		this.baNuri = baNuri;
	}

	/**
	 * @return the boardConfig
	 */
	public BoardConfig getBoardConfig() {
		return boardConfig;
	}

	/**
	 * @param boardConfig the boardConfig to set
	 */
	public void setBoardConfig(BoardConfig boardConfig) {
		this.boardConfig = boardConfig;
	}

	/**
	 * @return the baAltTexts
	 */
	public List<String> getBaAltTexts() {
		return baAltTexts;
	}

	/**
	 * @param baAltTexts the baAltTexts to set
	 */
	public void setBaAltTexts(List<String> baAltTexts) {
		this.baAltTexts = baAltTexts;
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
	 * @return the baMultipartFiles
	 */
	public List<MultipartFile> getBaMultipartFiles() {
		return baMultipartFiles;
	}

	/**
	 * @param baMultipartFiles the baMultipartFiles to set
	 */
	public void setBaMultipartFiles(List<MultipartFile> baMultipartFiles) {
		this.baMultipartFiles = baMultipartFiles;
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
	 * @return the adminMember
	 */
	public AdminMember getAdminMember() {
		return adminMember;
	}

	/**
	 * @param admin the adminMember to set
	 */
	public void setAdminMember(AdminMember adminMember) {
		this.adminMember = adminMember;
	}

	/**
	 * @return the baFileInfos
	 */
	public List<FileInfo> getBaFileInfos() {
		return baFileInfos == null ? new ArrayList<FileInfo>() : baFileInfos;
	}

	/**
	 * @param baFileInfos the baFileInfos to set
	 */
	public void setBaFileInfos(List<FileInfo> baFileInfos) {
		this.baFileInfos = baFileInfos;
	}

	/**
	 * @return the baThumbFile
	 */
	public MultipartFile getBaThumbFile() {
		return baThumbFile;
	}
	
	/**
	 * @param baThumbFile the baThumbFile to set
	 */
	public void setBaThumbFile(MultipartFile baThumbFile) {
		this.baThumbFile = baThumbFile;
	}

	/**
	 * @return the baUse
	 */
	public Boolean getBaUse() {
		return baUse;
	}
	
	/**
	 * @param baUse the baUse to set
	 */
	public void setBaUse(Boolean baUse) {
		this.baUse = baUse;
	}
	
	/**
	 * @return the baThumbText
	 */
	public String getBaThumbText() {
		return baThumbText;
	}
	
	/**
	 * @param baThumbText the baThumbText to set
	 */
	public void setBaThumbText(String baThumbText) {
		this.baThumbText = baThumbText;
	}
	
	/**
	 * @return the baAnswer
	 */
	public String getBaAnswer() {
		return baAnswer;
	}

	/**
	 * @param baAnswer the baAnswer to set
	 */
	public void setBaAnswer(String baAnswer) {
		this.baAnswer = baAnswer;
	}

	/**
	 * @return the baMainSelec
	 */
	public Boolean getBaMainSelec() {
		return baMainSelec;
	}

	/**
	 * @param baMainSelec the baMainSelec to set
	 */
	public void setBaMainSelec(Boolean baMainSelec) {
		this.baMainSelec = baMainSelec;
	}

	/**
	 * @return the baComSelCat
	 */
	public String getBaComSelCat() {
		return baComSelCat;
	}

	/**
	 * @param baComSelCat the baComSelCat to set
	 */
	public void setBaComSelCat(String baComSelCat) {
		this.baComSelCat = baComSelCat;
	}

	/**
	 * @return the baCommSelec
	 */
	public Boolean getBaCommSelec() {
		return baCommSelec;
	}

	/**
	 * @param baCommSelec the baCommSelec to set
	 */
	public void setBaCommSelec(Boolean baCommSelec) {
		this.baCommSelec = baCommSelec;
	}

	/**
	 * @return the baStartDate
	 */
	public String getBaStartDate() {
		return baStartDate;
	}

	/**
	 * @param baStartDate the baStartDate to set
	 */
	public void setBaStartDate(String baStartDate) {
		this.baStartDate = baStartDate;
	}

	/**
	 * @return the baStartTime
	 */
	public String getBaStartTime() {
		return baStartTime;
	}

	/**
	 * @param baStartTime the baStartTime to set
	 */
	public void setBaStartTime(String baStartTime) {
		this.baStartTime = baStartTime;
	}

	/**
	 * @return the baEndDate
	 */
	public String getBaEndDate() {
		return baEndDate;
	}

	/**
	 * @param baEndDate the baEndDate to set
	 */
	public void setBaEndDate(String baEndDate) {
		this.baEndDate = baEndDate;
	}

	/**
	 * 사용자,관리자 공통 객체 호출시 관리자 운선하여 공통객체를 셋팅 후 반환
	 * @return the member
	 */
	public Member getMember() {
		if(this.getAdminMember() != null){
			member = new Member();
			member.setMemberFromAdmin(this.getAdminMember());
		}
		if(this.getUserMember() != null){
			member = new Member();
			member.setMemberFromUser(this.getUserMember());
		}
		
		return member;
	}
	
	/**
	 * @param member the member to set
	 */
	public void setMember(Member member) {
		this.member = member;
	}
	
	/**
	 * @return the userMember
	 */
	public UserMember getUserMember() {
		return userMember;
	}
	
	/**
	 * @param userMember the userMember to set
	 */
	public void setUserMember(UserMember userMember) {
		this.userMember = userMember;
	}
	
	/**
	 * @return the baCustomField1
	 */
	public String getBaCustomField1() {
		return baCustomField1;
	}
	
	/**
	 * @param baCustomField1 the baCustomField1 to set
	 */
	public void setBaCustomField1(String baCustomField1) {
		this.baCustomField1 = baCustomField1;
	}
	
	/**
	 * @return the baCustomField2
	 */
	public String getBaCustomField2() {
		return baCustomField2;
	}
	
	/**
	 * @param baCustomField2 the baCustomField2 to set
	 */
	public void setBaCustomField2(String baCustomField2) {
		this.baCustomField2 = baCustomField2;
	}
	
	/**
	 * @return the baCustomField3
	 */
	public String getBaCustomField3() {
		return baCustomField3;
	}
	
	/**
	 * @param baCustomField3 the baCustomField3 to set
	 */
	public void setBaCustomField3(String baCustomField3) {
		this.baCustomField3 = baCustomField3;
	}
	
	/**
	 * @return the baCustomField4
	 */
	public String getBaCustomField4() {
		return baCustomField4;
	}
	
	/**
	 * @param baCustomField4 the baCustomField4 to set
	 */
	public void setBaCustomField4(String baCustomField4) {
		this.baCustomField4 = baCustomField4;
	}
	
	/**
	 * @return the baCustomField5
	 */
	public String getBaCustomField5() {
		return baCustomField5;
	}
	
	/**
	 * @param baCustomField5 the baCustomField5 to set
	 */
	public void setBaCustomField5(String baCustomField5) {
		this.baCustomField5 = baCustomField5;
	}
	
	/**
	 * @return the baCustomField6
	 */
	public String getBaCustomField6() {
		return baCustomField6;
	}
	
	/**
	 * @param baCustomField6 the baCustomField6 to set
	 */
	public void setBaCustomField6(String baCustomField6) {
		this.baCustomField6 = baCustomField6;
	}
	
	/**
	 * @return the baCustomField7
	 */
	public String getBaCustomField7() {
		return baCustomField7;
	}
	
	/**
	 * @param baCustomField7 the baCustomField7 to set
	 */
	public void setBaCustomField7(String baCustomField7) {
		this.baCustomField7 = baCustomField7;
	}
	
	/**
	 * @return the baCustomField8
	 */
	public String getBaCustomField8() {
		return baCustomField8;
	}
	
	/**
	 * @param baCustomField8 the baCustomField8 to set
	 */
	public void setBaCustomField8(String baCustomField8) {
		this.baCustomField8 = baCustomField8;
	}
	
	/**
	 * @return the baCustomField9
	 */
	public String getBaCustomField9() {
		return baCustomField9;
	}
	
	/**
	 * @param baCustomField9 the baCustomField9 to set
	 */
	public void setBaCustomField9(String baCustomField9) {
		this.baCustomField9 = baCustomField9;
	}
	
	/**
	 * @return the baCustomField10
	 */
	public String getBaCustomField10() {
		return baCustomField10;
	}
	
	/**
	 * @param baCustomField10 the baCustomField10 to set
	 */
	public void setBaCustomField10(String baCustomField10) {
		this.baCustomField10 = baCustomField10;
	}
	/**
	 * @return the csCount
	 */
	public Integer getCsCount() {
		return csCount;
	}
	
	/**
	 * @param csCount the csCount to set
	 */
	public void setCsCount(Integer csCount) {
		this.csCount = csCount;
	}
	
	//===============================================================================
	

	/**
	 * 게시물 링크를 반환한다.
	 * @return
	 */
	public String getPermLink(){
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		Site currentSite = (Site)request.getAttribute("currentSite"); 
		return AsaproUtils.getAppPath(currentSite) + "/board/" + this.getBcId() + "/" + this.getBaId();
	}
	
	/**
	 * 비회원이 작성한 글인지 체크한다.
	 * @return 비회원 작성글인지 여부
	 */
	public boolean isGuestArticle(){
		if( this.getMember() != null ){
			if( this.getMember().getMemberId().matches("temp_\\d{15}") || this.getMember().getMemberId().matches("guest_\\d{0,3}\\.\\d{0,3}\\.\\d{0,3}\\.\\d{0,3}") ){
				return true;
			}
		}
		return StringUtils.isNotBlank(this.getBaGuestPassword());
	}
	
	/**
	 * 콘텐츠 html 로 부터 plain/text를 추출해 세팅한다.(필요할 때만 임의로 호출하는 메서드.)
	 */
	public void setBaContentPlainFromBaContentHtml() {
		if( StringUtils.isNotBlank(this.getBaContentHtml()) ){
			String content = Jsoup.clean(this.getBaContentHtml(), Whitelist.none());
			if( content.length() > 4000 ){
				content = content.substring(0, 4000);
			}
			//검색용으로 본문의 텍스트만 따로 처리
			this.setBaContentPlain( content );
		}
	}
}
