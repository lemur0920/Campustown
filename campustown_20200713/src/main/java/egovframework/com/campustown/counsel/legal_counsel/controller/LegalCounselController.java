package egovframework.com.campustown.counsel.legal_counsel.controller;

import egovframework.com.asapro.fileinfo.service.FileInfo;
import egovframework.com.asapro.fileinfo.service.FileInfoService;
import egovframework.com.asapro.member.admin.service.AdminMember;
import egovframework.com.asapro.member.user.service.UserMember;
import egovframework.com.asapro.site.service.Site;
import egovframework.com.asapro.support.Constant;
import egovframework.com.asapro.support.annotation.CurrentAdmin;
import egovframework.com.asapro.support.annotation.CurrentSite;
import egovframework.com.asapro.support.annotation.CurrentUser;
import egovframework.com.asapro.support.pagination.Paging;
import egovframework.com.asapro.support.util.AsaproUtils;
import egovframework.com.campustown.counsel.legal_counsel.domain.Lawyer;
import egovframework.com.campustown.counsel.legal_counsel.domain.LegalCounsel;
import egovframework.com.campustown.counsel.legal_counsel.domain.Status;
import egovframework.com.campustown.counsel.legal_counsel.funtion.AddFile;
import egovframework.com.campustown.counsel.legal_counsel.service.LawyerService;
import egovframework.com.campustown.counsel.legal_counsel.service.LegalCounselAdminService;
import egovframework.com.campustown.counsel.legal_counsel.service.LegalCounselService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value= Constant.APP_PATH + Constant.SITE_ID_PATH + "/counsel/legal_counsel")
public class LegalCounselController {

    private final LegalCounselService legalCounselService;
    private final FileInfoService fileInfoService;
    private LegalCounselAdminService legalCounselAdminService;
    private LawyerService lawyerService;

    @Autowired
    public LegalCounselController(LegalCounselService legalCounselService, FileInfoService fileInfoService,LegalCounselAdminService legalCounselAdminService,LawyerService lawyerService) {
        this.legalCounselService = legalCounselService;
        this.fileInfoService = fileInfoService;
        this.legalCounselAdminService = legalCounselAdminService;
        this.lawyerService = lawyerService;
    }


    @RequestMapping("/list")
    public String getUserLegalCounselList(Model model, HttpServletRequest req, @CurrentUser UserMember currentUser) {
        LegalCounsel legalCounsel = new LegalCounsel();
        LegalCounsel.setPagingValue(legalCounsel, req);

        List<LegalCounsel> getUserLegalCounselList = legalCounselService.getUserLegalCounselList(legalCounsel);
        int getUserLegalAdviceListTotalCount = legalCounselService.getUserLegalCounselListTotalCount(legalCounsel);
        Paging paging = new Paging(getUserLegalCounselList, getUserLegalAdviceListTotalCount, legalCounsel);
        model.addAttribute("paging", paging);
        model.addAttribute("cu", currentUser);
        return "asapro/theme/campustown/counsel/legal_counsel/list";
    }

    @RequestMapping("/mypage/list")
    public String getUserLegalCounselMypageList(@CurrentUser UserMember currentUser, Model model, HttpServletRequest req) {
        LegalCounsel legalCounsel = new LegalCounsel();
        legalCounsel.myPageParamSetting(currentUser);
        LegalCounsel.setPagingValue(legalCounsel, req);
        List<LegalCounsel> getUserLegalCounselList = legalCounselService.getUserLegalCounselList(legalCounsel);
        int getUserLegalAdviceListTotalCount = legalCounselService.getUserLegalCounselListTotalCount(legalCounsel);
        Paging paging = new Paging(getUserLegalCounselList, getUserLegalAdviceListTotalCount, legalCounsel);
        model.addAttribute("paging", paging);
        return "asapro/theme/campustown/counsel/legal_counsel/list";
    }

    @RequestMapping("/write")
    public String getUserLegalCounselWrite() {
        return "asapro/theme/campustown/counsel/legal_counsel/write";
    }

    @RequestMapping("/save")
    public String getUserLegalCounselSave(@CurrentUser UserMember currentUser, @CurrentSite Site currentSite, HttpServletRequest req, MultipartFile file) throws IOException{
        LegalCounsel legalCounsel = new LegalCounsel();
        Integer saveId = legalCounselService.getLegalCounselSaveId();
        legalCounsel.addObject(currentUser, req, saveId);
        if(file != null && file.getSize() >0) AddFile.addFile(currentSite, req, saveId, file, fileInfoService, "대체텍스트", "legalCounsel");
        legalCounselService.getUserLegalCounselSave(legalCounsel);
        return "redirect:" + AsaproUtils.getAppPath(currentSite, true) + "/counsel/legal_counsel/list";
    }

    @RequestMapping("/view/{id}")
    public String getUserLegalCounselSave(@PathVariable("id") Integer id, @CurrentSite Site currentSite, @CurrentUser UserMember currentUser, Model model){
        LegalCounsel legalCounselViewData = legalCounselService.getUserLegalCounselView(id);
        Lawyer lawyer = lawyerService.getLawyerAssignId(id);

        try{
            FileInfo legalCounselFileInfo = legalCounselViewData.getFileInfo(currentSite, id, legalCounselService);
            model.addAttribute("viewFile", legalCounselFileInfo);

            FileInfo lawyerFileInfo = lawyer.getFileInfo(currentSite, lawyer.getSeq(), legalCounselAdminService);
            model.addAttribute("lawyerFile", lawyerFileInfo);

        }catch (Exception e){
            e.getStackTrace();
        }
        model.addAttribute("cu", currentUser);
        model.addAttribute("view", legalCounselViewData);
        return "asapro/theme/campustown/counsel/legal_counsel/view";
    }

    @RequestMapping("/status")
    public ResponseEntity getUserLegalCounselStatusCode(){
        Map<String, Integer> statusData = Status.findForCodeValue();
        return new ResponseEntity(statusData,HttpStatus.OK);
    }

    @RequestMapping("/deleteFile/{id}")
    public ResponseEntity legalDelFile(@PathVariable("id") Integer id, @CurrentSite Site currentSite, @CurrentUser UserMember currentUser, Model model){
        FileInfo fileInfo = new FileInfo();
        fileInfo.setSitePrefix(currentSite.getSitePrefix());
        fileInfo.setFileId(id);
        fileInfoService.deleteFileInfo(fileInfo);

        Map<String, Integer> statusData = Status.findForCodeValue();
        return new ResponseEntity(statusData, HttpStatus.OK);
    }

}