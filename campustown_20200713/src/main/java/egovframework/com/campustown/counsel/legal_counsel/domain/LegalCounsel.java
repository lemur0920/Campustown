package egovframework.com.campustown.counsel.legal_counsel.domain;

import egovframework.com.asapro.fileinfo.service.FileInfo;
import egovframework.com.asapro.fileinfo.service.FileInfoService;
import egovframework.com.asapro.member.admin.service.AdminMember;
import egovframework.com.asapro.member.user.service.UserMember;
import egovframework.com.asapro.site.service.Site;
import egovframework.com.asapro.support.annotation.CurrentSite;
import egovframework.com.asapro.support.pagination.PagingSearch;
import egovframework.com.asapro.support.util.AsaproUtils;
import egovframework.com.campustown.counsel.legal_counsel.service.LegalCounselAdminService;
import egovframework.com.campustown.counsel.legal_counsel.service.LegalCounselService;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

public class LegalCounsel extends PagingSearch {
    private Integer id;
    private String realm;        //분야
    private String title;        //제목
    private int status;          //자문 진행 상태 값
    private String teamName;     //팀 이름
    private String contents;     //내용
    private String regName;      //작성자 이름
    private Date regDate;        //작성일
    private String startDate;
    private String endDate;
    private MultipartFile file;  //첨부파일
    private Lawyer lawyer;
    private String lawyerId;
    private String regId;
    // 2020.07.07. jaseo - 컬럼 추가 (답글)
    private String lawyerCounsel;  // 변호사 답변
    
    
    
    public Integer getId() {
        return id;
    }

    public String getRealm() {
        return realm;
    }

    public String getTitle() {
        return title;
    }

    public String getTeamName() {
        return teamName;
    }

    public String getContents() {
        return contents;
    }

    public String getRegName() {
        return regName;
    }

    public Date getRegDate() {
        return regDate;
    }

    public int getStatus() {
        return status;
    }

    public MultipartFile getFile() {
        return file;
    }

    public Lawyer getLawyer() {
        return lawyer;
    }

    public String getLawyerId() {
        return lawyerId;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public String getRegId() {
        return regId;
    }
    public String getLawyerCounsel() {
		return lawyerCounsel;
	}

	public void setLawyerCounsel(String lawyerCounsel) {
		this.lawyerCounsel = lawyerCounsel;
	}

	@Override
    public String getQueryString() {

        StringBuilder sb = new StringBuilder(100);
        sb.append(super.getQueryString());

        if( StringUtils.isNotBlank(this.getStartDate()) ){
            sb.append("&amp;startDate=");
            sb.append(this.getStartDate());
        }

        if( StringUtils.isNotBlank(this.getEndDate()) ){
            sb.append("&amp;endDate=");
            sb.append(this.getEndDate());
        }

        return sb.toString();
    }
    /**
     * Sets paging value.
     * 페이징 정보를 셋 한다.
     *
     * @param legalCounsel the legal advice
     * @param req         the req
     */
    public static void setPagingValue(LegalCounsel legalCounsel, HttpServletRequest req) {
        Enumeration e = req.getParameterNames();
        while (e.hasMoreElements()) {
            String name = (String) e.nextElement();
            String[] values = req.getParameterValues(name);
            for (String value : values) {
                if(name.equals("cp")) legalCounsel.setCp(Integer.valueOf(value));
                if(name.equals("listType")) legalCounsel.setListType(value);
                if(name.equals("pageSize")) legalCounsel.setPageSize(Integer.valueOf(value));
                if(name.equals("sortOrder")) legalCounsel.setSortOrder(value);
                if(name.equals("sortDirection")) legalCounsel.setSortDirection(value);
                if(name.equals("startDate"))legalCounsel.setStartDate(value);
                if(name.equals("endDate"))legalCounsel.setEndDate(value);
            }

        }
    }

    /**
     * Add object.
     * 도메인 정보 저장 객체
     *
     * @param currentUser the current user
     * @param req         the req
     * @param saveId      the save id
     * @throws IOException the io exception
     */
    public void addObject(UserMember currentUser, HttpServletRequest req, Integer saveId) throws IOException {
        Enumeration e = req.getParameterNames();
        this.id = saveId;
        while (e.hasMoreElements()) {
            String name = (String) e.nextElement();
            String[] values = req.getParameterValues(name);
            for (String value : values) {
                if(name.equals("title")){this.title = value;}
                if(name.equals("realm")){this.realm = value;}
                if(name.equals("contents")){this.contents = value;}
            }
              this.status = Status.findByCodeValue(String.valueOf(Status.A));
//              this.regName = currentUser.getUserName();
                this.regName = currentUser.getUserName();
                this.regId = currentUser.getUserId();
              // TODO 창업팀 이름 넣어야 함 지금은 getTeam이 Null
              // this.teamName = "창업팀";
              this.teamName = (currentUser.getTeam() == null) ? "창업팀" : currentUser.getTeam().getTeamName();
        }
    }

    public Map addStatusUpdate(HttpServletRequest req) {
        //String statusCodeName = Status.findByCodeName(req.getParameter("statusCode"));
        Map statusUpdateDateMap = new HashMap();
        Enumeration<String> e = req.getParameterNames();
        while (e.hasMoreElements()){
            String name = e.nextElement();
            String[] values = req.getParameterValues(name);
            for (String value : values) {
                if (name.equals("id"))statusUpdateDateMap.put("id",value);
                if (name.equals("statusCode"))statusUpdateDateMap.put("statusCode",value);
            }
        }
        return statusUpdateDateMap;
    }

    public FileInfo getFileInfo(Site currentSite, Integer id, LegalCounselAdminService legalCounselAdminService) {
        FileInfo fileInfo = new FileInfo();
            fileInfo.setSitePrefix(currentSite.getSitePrefix());
            fileInfo.setFileModuleSub("legalCounsel");
            fileInfo.setFileModuleSubId(String.valueOf(id));
        return legalCounselAdminService.selectFileInfo(fileInfo);
    }

    // TODO 하나로 합치기
    public FileInfo getFileInfo(Site currentSite, Integer id, LegalCounselService legalCounselService) {
        FileInfo fileInfo = new FileInfo();
            fileInfo.setSitePrefix(currentSite.getSitePrefix());
            fileInfo.setFileModuleSub("legalCounsel");
            fileInfo.setFileModuleSubId(String.valueOf(id));
        return legalCounselService.selectFileInfo(fileInfo);
    }

    public void myPageParamSetting(UserMember currentUser) {
        this.regName = currentUser.getUserName();
    }

//    public FileInfo getFileInfo(@CurrentSite Site currentSite, Integer id, ) {
//        FileInfo fileInfo = new FileInfo();
//        fileInfo.setSitePrefix(currentSite.getSitePrefix());
//        fileInfo.setFileModuleSub("legal_counsel");
//        fileInfo.setFileModuleSubId(String.valueOf(id));
//        return fileInfo;
//    }
}
