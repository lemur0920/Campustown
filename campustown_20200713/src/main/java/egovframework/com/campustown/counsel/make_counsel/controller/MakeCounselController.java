package egovframework.com.campustown.counsel.make_counsel.controller;

import egovframework.com.asapro.fileinfo.service.FileInfo;
import egovframework.com.asapro.fileinfo.service.FileInfoService;
import egovframework.com.asapro.member.user.service.UserMember;
import egovframework.com.asapro.site.service.Site;
import egovframework.com.asapro.support.Constant;
import egovframework.com.asapro.support.annotation.CurrentSite;
import egovframework.com.asapro.support.annotation.CurrentUser;
import egovframework.com.asapro.support.pagination.Paging;
import egovframework.com.asapro.support.util.AsaproUtils;
import egovframework.com.campustown.counsel.legal_counsel.domain.Status;
import egovframework.com.campustown.counsel.make_counsel.domain.MakeCounsel;
import egovframework.com.campustown.counsel.make_counsel.domain.MakeCounselCodeType;
import egovframework.com.campustown.counsel.make_counsel.service.MakeCounselService;
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
@RequestMapping(value=Constant.APP_PATH + Constant.SITE_ID_PATH + "/counsel/make_counsel")
public class MakeCounselController {

    private final MakeCounselService makeCounselService;
    private final FileInfoService fileInfoService;

    @Autowired
    public MakeCounselController(MakeCounselService makeCounselService, FileInfoService fileInfoService) {
        this.makeCounselService = makeCounselService;
        this.fileInfoService = fileInfoService;
    }

    @RequestMapping(value = "/list")
    public String getMakeCounselList(Model model, HttpServletRequest req, @CurrentUser UserMember currentUser){
        MakeCounsel makeCounsel = new MakeCounsel();
        checkList(model);

        makeCounsel.searchCounsel(req);

        List<MakeCounsel> getMakeCounselList = makeCounselService.getMakeCounselList(makeCounsel);
        int getMakeCounselListTotalCount = makeCounselService.getMakeCounselListTotalCount(makeCounsel);
        List<MakeCounsel> getUserMakeCounSelDetailList = makeCounselService.getUserMakeCounSelDetailList(makeCounsel);

        Paging paging = new Paging(getMakeCounselList, getMakeCounselListTotalCount, makeCounsel);

        model.addAttribute("cu", currentUser);
        model.addAttribute("domain", makeCounsel);
        model.addAttribute("paging", paging);
        model.addAttribute("getUserMakeCounSelDetailList", getUserMakeCounSelDetailList);

        return "asapro/theme/campustown/counsel/make_counsel/list";
    }

    @RequestMapping(value = "/mypage/list")
    public String getMakeCounselMypageList(Model model, HttpServletRequest req, @CurrentUser UserMember currentUser){
        MakeCounsel makeCounsel = new MakeCounsel();
        checkList(model);

        makeCounsel.searchMyPageCounsel(currentUser, req);

        List<MakeCounsel> getMakeCounselList = makeCounselService.getMakeCounselList(makeCounsel);
        int getMakeCounselListTotalCount = makeCounselService.getMakeCounselListTotalCount(makeCounsel);
        List<MakeCounsel> getUserMakeCounSelDetailList = makeCounselService.getUserMakeCounSelDetailList(makeCounsel);

        Paging paging = new Paging(getMakeCounselList, getMakeCounselListTotalCount, makeCounsel);

        model.addAttribute("cu", currentUser);
        model.addAttribute("domain", makeCounsel);
        model.addAttribute("paging", paging);
        model.addAttribute("getUserMakeCounSelDetailList", getUserMakeCounSelDetailList);

        return "asapro/theme/campustown/counsel/make_counsel/list";
    }

    @RequestMapping("/view/{id}")
    public String getUserMakeCounselSave(@PathVariable("id") Integer id, @CurrentSite Site currentSite, @CurrentUser UserMember currentUser, Model model){
        MakeCounsel makeCounsel = new MakeCounsel();

        checkList(model);
        MakeCounsel makeCounselViewData = makeCounselService.getUserMakeCounselView(id);
        List<MakeCounsel> getUserMakeCounSelDetailList = makeCounselService.getUserMakeCounSelDetailViewList(id);
        int fileCount = makeCounselService.getFileCount(id);
        model.addAttribute("fileCount", fileCount);

        if(fileCount > 0) {
            FileInfo fileInfo = new FileInfo();
            fileInfo.setSitePrefix(currentSite.getSitePrefix());
            fileInfo.setFileId(makeCounselService.getUserFileId(id));
            model.addAttribute("fileInfo", fileInfoService.getFileInfo(fileInfo));
        }

        model.addAttribute("cu", currentUser);
        model.addAttribute("makeCounselSeq", id);
        model.addAttribute("view", makeCounselViewData);
        model.addAttribute("detailList", getUserMakeCounSelDetailList);
        return "asapro/theme/campustown/counsel/make_counsel/view";
    }

    @RequestMapping(value = "/write")
    public String getMakeCounselSaveForm(Model model){
        checkList(model);
        return "asapro/theme/campustown/counsel/make_counsel/write";
    }

    @RequestMapping(value = "/save")
    public String getMakeCounselSave(Model model, @CurrentSite Site currentSite, @CurrentUser UserMember currentUser, HttpServletRequest req, MultipartFile file) throws IOException {

        MakeCounsel makeCounselForm = new MakeCounsel();

        checkList(model);

        makeCounselForm.addUserInfo(currentUser, req);

        makeCounselService.getUserMakeCounselSave(makeCounselForm);

        if(makeCounselForm.getIdea() != null || makeCounselForm.getProductDevelopment() != null || makeCounselForm.getPrototypeProduct() != null){
            makeCounselService.getUserMakeCounselDetail(makeCounselForm.getMakeCounselSeq(), makeCounselForm);
        }
        if(file != null && file.getSize() >0) makeCounselForm.addFile(currentSite, req, makeCounselForm.getMakeCounselSeq(), file, fileInfoService, makeCounselForm);

        //return "asapro/theme/campustown/make_counsel/list";
        return "redirect:" + AsaproUtils.getAppPath(currentSite, true) + "/counsel/make_counsel/list";
    }

    @RequestMapping(value = "/update")
    public String getMakeCounselUpdate(Model model, @CurrentSite Site currentSite, @CurrentUser UserMember currentUser, HttpServletRequest req, MultipartFile file) throws IOException {

        MakeCounsel makeCounselForm = new MakeCounsel();

        checkList(model);

        makeCounselForm.modifyUserInfo(currentUser, req);

        makeCounselService.getUserMakeCounselUpdate(makeCounselForm);

        // detail delete
        makeCounselService.removeUserMakeCounselDetail(makeCounselForm.getMakeCounselSeq());

        if(makeCounselForm.getIdea() != null || makeCounselForm.getProductDevelopment() != null || makeCounselForm.getPrototypeProduct() != null){
            makeCounselService.getUserMakeCounselDetail(makeCounselForm.getMakeCounselSeq(), makeCounselForm);
        }
        if(file != null && file.getSize() >0) makeCounselForm.addFile(currentSite, req, makeCounselForm.getMakeCounselSeq(), file, fileInfoService, makeCounselForm);

        //return "asapro/theme/campustown/make_counsel/list";
        return "redirect:" + AsaproUtils.getAppPath(currentSite, true) + "/counsel/make_counsel/list";
    }

    @RequestMapping("/deleteFile/{id}")
    public ResponseEntity makeCounselDelFile(@PathVariable("id") Integer id, @CurrentSite Site currentSite, @CurrentUser UserMember currentUser, Model model){
        FileInfo fileInfo = new FileInfo();
        fileInfo.setSitePrefix(currentSite.getSitePrefix());
        fileInfo.setFileId(id);
        fileInfoService.deleteFileInfo(fileInfo);

        Map<String, Integer> statusData = Status.findForCodeValue();
        return new ResponseEntity(statusData, HttpStatus.OK);
    }

    /**
     * Get checkList string.
     * 분야 코드 값 가져오기
     *
     * @param model       the model
     * @return the void
     */
    private void checkList(Model model) {
        model.addAttribute("Idea", MakeCounselCodeType.MakeCounselIdea.values());
        model.addAttribute("Prod", MakeCounselCodeType.MakeCounselProductDevelopment.values());
        model.addAttribute("Prot", MakeCounselCodeType.MakeCounselPrototypeProduct.values());
    }
}
