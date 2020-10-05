package egovframework.com.campustown.counsel.make_counsel.domain;

import egovframework.com.asapro.fileinfo.service.FileInfo;
import egovframework.com.asapro.fileinfo.service.FileInfoService;
import egovframework.com.asapro.member.admin.service.AdminMember;
import egovframework.com.asapro.member.user.service.UserMember;
import egovframework.com.asapro.site.service.Site;
import egovframework.com.asapro.support.annotation.CurrentSite;
import egovframework.com.asapro.support.pagination.PagingSearch;
import egovframework.com.asapro.support.util.AsaproUtils;
import egovframework.com.campustown.counsel.legal_counsel.domain.LegalCounsel;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * The type Make conunsel. 시제품 제작 및 자문
 */
public class MakeCounsel extends PagingSearch {
    private int makeCounselSeq;
    private List<String> idea = new ArrayList<>(); //아이디어
    private List<String> productDevelopment = new ArrayList<String>(); //제품개
    private List<String> prototypeProduct = new ArrayList<>(); //시제품
    private String makeCounselRealm;
    private String startDate;
    private String endDate;
    private String searchDiv;   // 검색어 구분
    private String searchWord;  // 검색어
    private String type;
    private String title;
    private String itemIntroduction; //아이템 한줄 소개
    private String productOverview; //제품 개요 및 주요 기능 소
    private String process;     //진행과정
    private String productDetail; //세부기능 및 제품 스펙
    private MultipartFile file;
    private String regName;
    private Date regDate = new Date();
    private String teamName;
    private String regId;

    public int getMakeCounselSeq() {
        return makeCounselSeq;
    }

    public List<String> getIdea() {
        return idea;
    }

    public List<String> getProductDevelopment() {
        return productDevelopment;
    }

    public List<String> getPrototypeProduct() {
        return prototypeProduct;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getSearchDiv() {
        return searchDiv;
    }

    public void setSearchDiv(String searchDiv) {
        this.searchDiv = searchDiv;
    }

    public String getSearchWord() {
        return searchWord;
    }

    public void setSearchWord(String searchWord) {
        this.searchWord = searchWord;
    }

    public String getType() {
        return type;
    }

    public String getMakeCounselRealm() {
        return makeCounselRealm;
    }

    public String getTitle() {
        return title;
    }

    public String getItemIntroduction() {
        return itemIntroduction;
    }

    public String getProductOverview() {
        return productOverview;
    }

    public String getProcess() {
        return process;
    }

    public String getProductDetail() {
        return productDetail;
    }

    public MultipartFile getFile() {
        return file;
    }

    public String getRegName() {
        return regName;
    }

    public Date getRegDate() {
        return regDate;
    }

    public String getTeamName() {
        return teamName;
    }

    public String getRegId() {
        return regId;
    }

    public void searchCounsel(HttpServletRequest req) {
        this.startDate = req.getParameter("startDate");
        this.endDate = req.getParameter("endDate");
        this.searchDiv = req.getParameter("searchDiv");
        this.searchWord = req.getParameter("searchWord");
        this.setCp(StringUtils.isEmpty(req.getParameter("cp"))?1:Integer.parseInt(req.getParameter("cp")));
    }

    public void searchMyPageCounsel(UserMember currentUser, HttpServletRequest req) {
        this.startDate = req.getParameter("startDate");
        this.endDate = req.getParameter("endDate");
        this.searchDiv = req.getParameter("searchDiv");
        this.searchWord = req.getParameter("searchWord");
        this.regName = currentUser.getUserId();
        this.setCp(StringUtils.isEmpty(req.getParameter("cp"))?1:Integer.parseInt(req.getParameter("cp")));
    }

    public void addUserInfo(UserMember currentUser, HttpServletRequest req) {
        this.title = req.getParameter("title");
        this.itemIntroduction = req.getParameter("itemIntroduction");
        this.productOverview = req.getParameter("productOverview");
        this.process = req.getParameter("process");
        this.productDetail = req.getParameter("productDetail");
        this.regName = currentUser.getUserName();
        this.regId = currentUser.getUserId();

        // TODO 창업팀 이름 넣어야 함 지금은 getTeam이 Null
        this.teamName = (currentUser.getTeam() == null) ? "창업팀" : currentUser.getTeam().getTeamName();

        if(req.getParameter("idea") != null){
            this.idea = Arrays.asList(req.getParameterValues("idea"));
        }
        if(req.getParameter("productDevelopment") != null){
            this.productDevelopment = Arrays.asList(req.getParameterValues("productDevelopment"));
        }
        if(req.getParameter("prototypeProduct") != null){
            this.prototypeProduct = Arrays.asList(req.getParameterValues("prototypeProduct"));
        }

    }

    public void modifyUserInfo(UserMember currentUser, HttpServletRequest req) {
        this.makeCounselSeq = Integer.parseInt(req.getParameter("makeCounselSeq"));
        this.title = req.getParameter("title");
        this.itemIntroduction = req.getParameter("itemIntroduction");
        this.productOverview = req.getParameter("productOverview");
        this.process = req.getParameter("process");
        this.productDetail = req.getParameter("productDetail");
        this.regName = currentUser.getUserId();

        if(req.getParameter("idea") != null){
            this.idea = Arrays.asList(req.getParameterValues("idea"));
        }
        if(req.getParameter("productDevelopment") != null){
            this.productDevelopment = Arrays.asList(req.getParameterValues("productDevelopment"));
        }
        if(req.getParameter("prototypeProduct") != null){
            this.prototypeProduct = Arrays.asList(req.getParameterValues("prototypeProduct"));
        }
    }

    public void addAdminInfo(AdminMember currentUser, HttpServletRequest req) {
        this.title = req.getParameter("title");
        this.itemIntroduction = req.getParameter("itemIntroduction");
        this.productOverview = req.getParameter("productOverview");
        this.process = req.getParameter("process");
        this.productDetail = req.getParameter("productDetail");
        this.regName = currentUser.getAdminId();

        if(req.getParameter("idea") != null){
            this.idea = Arrays.asList(req.getParameterValues("idea"));
        }
        if(req.getParameter("productDevelopment") != null){
            this.productDevelopment = Arrays.asList(req.getParameterValues("productDevelopment"));
        }
        if(req.getParameter("prototypeProduct") != null){
            this.prototypeProduct = Arrays.asList(req.getParameterValues("prototypeProduct"));
        }

    }

    public void modifyAdminInfo(AdminMember currentUser, HttpServletRequest req) {
        this.makeCounselSeq = Integer.parseInt(req.getParameter("makeCounselSeq"));
        this.title = req.getParameter("title");
        this.itemIntroduction = req.getParameter("itemIntroduction");
        this.productOverview = req.getParameter("productOverview");
        this.process = req.getParameter("process");
        this.productDetail = req.getParameter("productDetail");
        this.regName = currentUser.getAdminId();

        if(req.getParameter("idea") != null){
            this.idea = Arrays.asList(req.getParameterValues("idea"));
        }
        if(req.getParameter("productDevelopment") != null){
            this.productDevelopment = Arrays.asList(req.getParameterValues("productDevelopment"));
        }
        if(req.getParameter("prototypeProduct") != null){
            this.prototypeProduct = Arrays.asList(req.getParameterValues("prototypeProduct"));
        }
    }

    /**
     * Add file.
     * 파일 정보 저장 객체
     *
     * @param currentSite     the current site
     * @param req             the req
     * @param saveId          the save id
     * @param file            the file
     * @param fileInfoService the file info service
     * @param makeCounsel     the makeCounsel
     * @throws IOException the io exception
     */
    public void addFile(@CurrentSite Site currentSite, HttpServletRequest req, Integer saveId, MultipartFile file, FileInfoService fileInfoService, MakeCounsel makeCounsel) throws IOException {
        String webRoot = AsaproUtils.getWebRoot(req);

        FileInfo fileInfo = new FileInfo();
        fileInfo.setSitePrefix(currentSite.getSitePrefix());

        fileInfo.setMemberId("");

        fileInfo.setFileModule("counsel");
        fileInfo.setFileModuleId(makeCounsel.getSitePrefix());

        fileInfo.setFileModuleSub("makeCounsel");
        fileInfo.setFileModuleSubId(String.valueOf(saveId));

        //대체텍스트
        fileInfo.setFileAttachmentType("appending");
        fileInfo.setFileAltText(req.getParameter("title"));

        fileInfoService.saveFileNoThumb(file, webRoot,fileInfo);
    }
}
