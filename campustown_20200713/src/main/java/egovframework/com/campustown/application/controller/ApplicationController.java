package egovframework.com.campustown.application.controller;

import egovframework.com.asapro.member.user.service.UserMember;
import egovframework.com.asapro.site.service.Site;
import egovframework.com.asapro.support.Constant;
import egovframework.com.asapro.support.ServerMessage;
import egovframework.com.asapro.support.annotation.CurrentSite;
import egovframework.com.asapro.support.annotation.CurrentUser;
import egovframework.com.campustown.application.domain.XTechApply;
import egovframework.com.campustown.application.service.XTechApplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Controller
@RequestMapping(value= Constant.APP_PATH + Constant.SITE_ID_PATH + "/application")
public class ApplicationController {

    @Autowired
    private XTechApplyService xTechApplyService;

    @RequestMapping(value = "/form", method = RequestMethod.GET)
    public String getXtechApplyForm(Model model) {
        model.addAttribute("XTechApply", "XTechApply");
        return "asapro/theme/campustown/application/form";
    }

    @RequestMapping(value = "/form/save", method = RequestMethod.POST)
    @ResponseBody
    public ServerMessage saveXtechApplyForm(@CurrentUser UserMember currentUser, @CurrentSite Site currentSite, HttpServletRequest req, XTechApply xTechApply) throws IOException {
        xTechApply.setData(currentUser, req);
        xTechApplyService.XtechApplySave(xTechApply);
        ServerMessage serverMessage = new ServerMessage();
        serverMessage.setSuccess(true);

        return serverMessage;
    }
}
