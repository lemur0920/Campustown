package egovframework.com.campustown.counsel.legal_counsel.controller;

import egovframework.com.asapro.fileinfo.service.FileInfoService;
import egovframework.com.asapro.site.service.Site;
import egovframework.com.asapro.support.Constant;
import egovframework.com.asapro.support.annotation.CurrentSite;
import egovframework.com.asapro.support.util.AsaproUtils;
import egovframework.com.campustown.counsel.legal_counsel.domain.Lawyer;
import egovframework.com.campustown.counsel.legal_counsel.domain.LegalCounsel;
import egovframework.com.campustown.counsel.legal_counsel.funtion.AddFile;
import egovframework.com.campustown.counsel.legal_counsel.service.LawyerService;
import egovframework.com.campustown.counsel.legal_counsel.service.LegalCounselAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/counsel/legal_counsel/lawyer")
public class LawyerController {

    private LegalCounselAdminService legalCounselAdminService;
    private LawyerService lawyerService;
    private FileInfoService fileInfoService;

    @Autowired
    public LawyerController(LegalCounselAdminService legalCounselAdminService, LawyerService lawyerService, FileInfoService fileInfoService) {
        this.legalCounselAdminService = legalCounselAdminService;
        this.lawyerService = lawyerService;
        this.fileInfoService = fileInfoService;
    }

    @RequestMapping("/list.do")
    public ResponseEntity getLawyerList() {
        Lawyer lawyer = new Lawyer();
        List<Lawyer> lawyersList = lawyerService.getLawyerList(lawyer);
        return new ResponseEntity(lawyersList, HttpStatus.OK);
    }

    @RequestMapping("/save.do")
    public ResponseEntity getLawyerSave(@CurrentSite Site currentSite, HttpServletRequest req){
        LegalCounsel legalCounsel = new LegalCounsel();
        Lawyer lawyer = new Lawyer();

        Integer saveId = lawyerService.lawyerAssignSaveId();

        Map lawAssingSaveData = lawyer.LawyerAssign(req, saveId);
        lawyerService.lawyerAssignSave(lawAssingSaveData);

        Map statusUpdateDataMap = legalCounsel.addStatusUpdate(req);
        legalCounselAdminService.statusUpdate(statusUpdateDataMap);

        //TODO 리다이렉트를 URI 기준으로
       // return "redirect:" + AsaproUtils.getAdminPath(currentSite, true) + "/counsel/legal_counsel/list.do";
        //return "asapro/admin/counsel/legal_counsel/list";
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping("del.do")
    public ResponseEntity getLawyerDel(@CurrentSite Site currentSite, HttpServletRequest req){
        LegalCounsel legalCounsel = new LegalCounsel();
        Lawyer lawyer = new Lawyer();

        Integer seq = 0;
        Map lawyerDelData = lawyer.LawyerAssign(req, seq);
        lawyerService.lawyerAssignDel(lawyerDelData);

        Map statusUpdateDataMap = legalCounsel.addStatusUpdate(req);
        legalCounselAdminService.statusUpdate(statusUpdateDataMap);

        //TODO 리다이렉트를 URI 기준으로
        //return "redirect:" + AsaproUtils.getAdminPath(currentSite, true) + "/counsel/legal_counsel/list.do";
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping("update.do")
    public ResponseEntity getLawyerUpdate(@CurrentSite Site currentSite, HttpServletRequest req){
        LegalCounsel legalCounsel = new LegalCounsel();
        Lawyer lawyer = new Lawyer();
        Integer seq =0;
        Map lawyerUpdteData = lawyer.LawyerAssign(req,seq);
        lawyerService.lawyerAssignUpdate(lawyerUpdteData);

        Map statusUpdateDataMap = legalCounsel.addStatusUpdate(req);
        legalCounselAdminService.statusUpdate(statusUpdateDataMap);

        //TODO 리다이렉트를 URI 기준으로
       // return "redirect:" + AsaproUtils.getAdminPath(currentSite, true) + "/counsel/legal_counsel/list.do";
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping("/lcsave/{seq}.do")
    public String lawyerCounselSave(@CurrentSite Site currentSite, HttpServletRequest req, MultipartFile file, @PathVariable("seq") Integer seq) throws IOException {
        Lawyer lawyer = new Lawyer();
        Map lawyerCounselData = lawyer.addLawyerCounselSave(req, seq);
        lawyerService.lawyerCounselSave(lawyerCounselData);
        if(file != null && file.getSize() >0) AddFile.addFile(currentSite, req, seq, file, fileInfoService,"대체텍스트","lawyerCounsel");

        Map statusUpdateDateMap = new HashMap();
        statusUpdateDateMap.put("id",req.getParameter("legalCounseSeq"));
        String codeValue = req.getParameter("statusCodeValue");
        if(codeValue.equals("0")){
            statusUpdateDateMap.put("statusCode",5);
        } else if(codeValue.equals("1")){
            statusUpdateDateMap.put("statusCode",6);
        }
        legalCounselAdminService.statusUpdate(statusUpdateDateMap);

        return "redirect:" + AsaproUtils.getAdminPath(currentSite, true) + "/counsel/legal_counsel/list.do";
    }



}
