package egovframework.com.campustown.counsel.legal_counsel.controller;

import egovframework.com.asapro.fileinfo.service.FileInfo;
import egovframework.com.asapro.fileinfo.service.FileInfoService;
import egovframework.com.asapro.member.user.service.UserMember;
import egovframework.com.asapro.site.service.Site;
import egovframework.com.asapro.support.Constant;
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
@RequestMapping(Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/counsel/legal_counsel")
public class LegalCounselAdminController {

    private LegalCounselAdminService legalCounselAdminService;
    private LawyerService lawyerService;
    private FileInfoService fileInfoService;

    @Autowired
    public LegalCounselAdminController(LegalCounselAdminService legalCounselAdminService, LawyerService lawyerService, FileInfoService fileInfoService) {
        this.legalCounselAdminService = legalCounselAdminService;
        this.lawyerService = lawyerService;
        this.fileInfoService = fileInfoService;
    }

    @RequestMapping("/list.do")
    public String getAdminLegalCounselList(Model model, HttpServletRequest req) {
        LegalCounsel legalCounsel = new LegalCounsel();
        LegalCounsel.setPagingValue(legalCounsel, req);
        List<LegalCounsel> getAdminLegalCounselList = legalCounselAdminService.getAdminLegalCounselList(legalCounsel);
        int getAdminLegalAdviceListTotalCount = legalCounselAdminService.getAdminLegalCounselListTotalCount(legalCounsel);
        Paging paging = new Paging(getAdminLegalCounselList, getAdminLegalAdviceListTotalCount, legalCounsel);
        model.addAttribute("paging", paging);
        model.addAttribute("legalSearch", legalCounsel);

        Lawyer lawyer = new Lawyer();
        List<Lawyer> lawyersList = lawyerService.getLawyerList(lawyer);
        model.addAttribute("lawyersList", lawyersList);

        return "asapro/admin/counsel/legal_counsel/list";
    }

    @RequestMapping("/view/{id}.do")
    public String getAdminLegalCounseView(@CurrentSite Site currentSite, @PathVariable("id") Integer id, Model model){
        LegalCounsel legalCounsel = legalCounselAdminService.getAdminLegalCounselView(id);
        Lawyer lawyer = lawyerService.getLawyerAssignId(id);
        try{
            FileInfo legalCounselFileInfo = legalCounsel.getFileInfo(currentSite, id, legalCounselAdminService);
            model.addAttribute("viewFile", legalCounselFileInfo);

            FileInfo lawyerFileInfo = lawyer.getFileInfo(currentSite, lawyer.getSeq(), legalCounselAdminService);
            model.addAttribute("lawyerFile", lawyerFileInfo);
        }catch (Exception e){
            e.getStackTrace();
        }
        model.addAttribute("view", legalCounsel);
        model.addAttribute("lawyerAssingId", lawyer);
        return "asapro/admin/counsel/legal_counsel/view";
    }

    @RequestMapping("/status/update.do")
    public String getAdminLegalCounselStatusUpdate(HttpServletRequest req) {
        LegalCounsel legalCounsel = new LegalCounsel();
        Map statusUpdateDataMap = legalCounsel.addStatusUpdate(req);
        legalCounselAdminService.statusUpdate(statusUpdateDataMap);
        return "asapro/admin/counsel/legal_counsel/list";
    }

    @RequestMapping("/write.do")
    public String writeFome() {
        return "asapro/admin/counsel/legal_counsel/write";
    }

    @RequestMapping("/save.do")
    public String getUserLegalCounselSave(@CurrentUser UserMember currentUser, @CurrentSite Site currentSite, HttpServletRequest req, MultipartFile file) throws IOException {
        LegalCounsel legalCounsel = new LegalCounsel();
        Integer saveId = legalCounselAdminService.getLegalCounselSaveId();
        legalCounsel.addObject(currentUser, req, saveId);
        //Map legalCounseMapDate = new HashMap();
        //Iter.enumIter(req, String.valueOf(Iter.TypeData.STARTUP));
        if(file != null && file.getSize() >0) AddFile.addFile(currentSite, req, saveId, file, fileInfoService, "대체텍스트", "legalCounsel");
        legalCounselAdminService.getUserLegalCounselSave(legalCounsel);
        return "redirect:" + AsaproUtils.getAdminPath(currentSite, true) + "/counsel/legal_counsel/list.do";
    }

    @RequestMapping("/deleteFile/{id}.do")
    public ResponseEntity makeLegalDelFile(@PathVariable("id") Integer id, @CurrentSite Site currentSite){
        FileInfo fileInfo = new FileInfo();
        fileInfo.setSitePrefix(currentSite.getSitePrefix());
        fileInfo.setFileId(id);
        fileInfoService.deleteFileInfo(fileInfo);

        Map<String, Integer> statusData = Status.findForCodeValue();
        return new ResponseEntity(statusData, HttpStatus.OK);
    }

}