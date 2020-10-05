package egovframework.com.campustown.counsel.legal_counsel.domain;

import egovframework.com.asapro.fileinfo.service.FileInfo;
import egovframework.com.asapro.fileinfo.service.FileInfoService;
import egovframework.com.asapro.site.service.Site;
import egovframework.com.asapro.support.annotation.CurrentSite;
import egovframework.com.asapro.support.pagination.PagingSearch;
import egovframework.com.asapro.support.util.AsaproUtils;
import egovframework.com.campustown.counsel.legal_counsel.funtion.Iter;
import egovframework.com.campustown.counsel.legal_counsel.service.LegalCounselAdminService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

public class Lawyer extends PagingSearch {
    private String adminId;
    private String adminName;
    private String roleCode;
    private Integer id;
    private Integer seq;
    private String lawyerId;
    private String lawyerCounsel;
    private MultipartFile file;

    public String getAdminId() {
        return adminId;
    }

    public String getAdminName() {
        return adminName;
    }

    public String getRoleCode() {
        return roleCode;
    }

    public Integer getId() {
        return id;
    }

    public Integer getSeq() {
        return seq;
    }

    public String getLawyerId() {
        return lawyerId;
    }

    public String getLawyerCounsel() {
        return lawyerCounsel;
    }

    public MultipartFile getFile() {
        return file;
    }

    public Map LawyerAssign(HttpServletRequest req, Integer saveId){
        Map lawyerMapData = new HashMap();
        //Iter.enumIter(req, lawyerMapData);
        Enumeration<String> e = req.getParameterNames();
        while (e.hasMoreElements()){
            String name = e.nextElement();
            String[] values = req.getParameterValues(name);
            for (String value : values) {
                    if (name.equals("id"))lawyerMapData.put("id",value);
                    if (name.equals("lawyerId"))lawyerMapData.put("lawyerId",value);
                }
            }
        lawyerMapData.put("seq", saveId);
        return lawyerMapData;
    }


    public Map addLawyerCounselSave(HttpServletRequest req, Integer saveId) {
        Map lawyerCounselData = new HashMap();
        Enumeration e = req.getParameterNames();
        while (e.hasMoreElements()) {
            String name = (String) e.nextElement();
            String[] values = req.getParameterValues(name);
            for (String value : values) {
                if (name.equals("id"))lawyerCounselData.put("id",value);
                if (name.equals("lawyerCounsel"))lawyerCounselData.put("lawyerCounsel",value);
            }
        }
        lawyerCounselData.put("seq",saveId);

        return lawyerCounselData;
    }

//    public void addFile(@CurrentSite Site currentSite, HttpServletRequest req, Integer saveId, MultipartFile file, FileInfoService fileInfoService, Lawyer lawyer) throws IOException {
//        String webRoot = AsaproUtils.getWebRoot(req);
//
//        FileInfo fileInfo = new FileInfo();
//        fileInfo.setSitePrefix(currentSite.getSitePrefix());
//
//        fileInfo.setMemberId("");
//
//        fileInfo.setFileModule("site");
//        fileInfo.setFileModuleId(lawyer.getSitePrefix());
//
//        fileInfo.setFileModuleSub("lawyer_counsel");
//        fileInfo.setFileModuleSubId(String.valueOf(saveId));
//
//        //대체텍스트
//        fileInfo.setFileAttachmentType("appending");
//        fileInfo.setFileAltText("-");
//
//        fileInfoService.saveFileNoThumb(file, webRoot,fileInfo);
//    }

    public FileInfo getFileInfo(Site currentSite, Integer id, LegalCounselAdminService legalCounselAdminService) {
        FileInfo fileInfo = new FileInfo();
        fileInfo.setSitePrefix(currentSite.getSitePrefix());
        fileInfo.setFileModuleSub("lawyerCounsel");
        fileInfo.setFileModuleSubId(String.valueOf(id));
        return legalCounselAdminService.selectFileInfo(fileInfo);
    }
}
