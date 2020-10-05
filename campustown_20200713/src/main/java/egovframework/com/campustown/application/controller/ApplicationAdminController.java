package egovframework.com.campustown.application.controller;

import egovframework.com.asapro.archive.service.Archive;
import egovframework.com.asapro.archive.service.ArchiveCategory;
import egovframework.com.asapro.archive.service.ArchiveCategorySearch;
import egovframework.com.asapro.code.service.CodeCategorySearch;
import egovframework.com.asapro.fileinfo.service.FileInfoUploadResult;
import egovframework.com.asapro.member.user.service.UserMember;
import egovframework.com.asapro.site.service.Site;
import egovframework.com.asapro.support.Constant;
import egovframework.com.asapro.support.ServerMessage;
import egovframework.com.asapro.support.annotation.CurrentSite;
import egovframework.com.asapro.support.annotation.CurrentUser;
import egovframework.com.asapro.support.pagination.Paging;
import egovframework.com.campustown.application.domain.XTechApply;
import egovframework.com.campustown.application.service.XTechApplyService;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value= Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/application")
public class ApplicationAdminController {
    @Autowired
    private XTechApplyService xTechApplyService;

    @RequestMapping("/form/list.do")
    public String getXTechApplyList(Model model, @CurrentSite Site currentSite,@ModelAttribute("XTechApply") XTechApply xTechApply){
        xTechApply.setSitePrefix(currentSite.getSitePrefix());
        xTechApply.fixBrokenSvByDefaultCharsets();
        xTechApply.setPaging(true);

        List<XTechApply> list = xTechApplyService.getXTechApplyList(xTechApply);
        int total = xTechApplyService.getXTechApplyListTotal(xTechApply);
        Paging paging = new Paging(list, total, xTechApply);
        model.addAttribute("paging", paging);

        return "asapro/admin/application/list";
    }

    @RequestMapping(value= "/form/update.do", method= RequestMethod.POST)
    @ResponseBody
    public ServerMessage updateXtechApplyForm(@CurrentUser UserMember currentUser, @CurrentSite Site currentSite, HttpServletRequest req, XTechApply xTechApply) throws IOException {
        xTechApply.setSitePrefix(currentSite.getSitePrefix());
        xTechApply.fixBrokenSvByDefaultCharsets();

        xTechApply.setData(currentUser, req);
        xTechApplyService.XtechApplyUpdate(xTechApply);

        ServerMessage serverMessage = new ServerMessage();
        serverMessage.setSuccess(true);

        return serverMessage;
    }


    @RequestMapping(value= "/form/update.do", method= RequestMethod.GET)
    public String getXtechApplyForm(Model model, @CurrentSite Site currentSite,int applySeq, @ModelAttribute("xTechApply") XTechApply xTechApply) throws IOException {
        xTechApply = xTechApplyService.getXTechApply(applySeq);
        xTechApply.setSitePrefix(currentSite.getSitePrefix());
        xTechApply.Processing();
        model.addAttribute("item", xTechApply);

        return "asapro/admin/application/form";
    }

    @RequestMapping(value="/form/delete.do", method=RequestMethod.POST)
    @ResponseBody
    public ServerMessage deleteXtechApplyForm(Model model, @CurrentSite Site currentSite,@RequestParam(value="applySeqs[]", required=true) Integer[] applySeqs) throws IOException {
        ServerMessage serverMessage = new ServerMessage();
        if( ArrayUtils.isEmpty(applySeqs) ){
            serverMessage.setSuccess(false);
            serverMessage.setText("삭제할 항목이 없습니다.");
        } else {
            List<XTechApply> xTechApplyList = new ArrayList<XTechApply>();
            XTechApply xTechApply = null;
            for( Integer applySeq : applySeqs ){
                xTechApply = new XTechApply();
                xTechApply.setSitePrefix(currentSite.getSitePrefix());
                xTechApply.setApplySeq(applySeq);
                xTechApplyList.add(xTechApply);
            }
            int result = xTechApplyService.deleteXTechApply(xTechApplyList);
            if(result > 0){
                serverMessage.setSuccess(true);
                serverMessage.setSuccessCnt(result);
            } else {
                serverMessage.setSuccess(false);
                serverMessage.setText("삭제하지 못하였습니다.[Server Error]");
            }
        }
        return serverMessage;
    }


}
