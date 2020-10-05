package egovframework.com.campustown.counsel.legal_counsel.funtion;

import egovframework.com.asapro.fileinfo.service.FileInfo;
import egovframework.com.asapro.fileinfo.service.FileInfoService;
import egovframework.com.asapro.site.service.Site;
import egovframework.com.asapro.support.annotation.CurrentSite;
import egovframework.com.asapro.support.util.AsaproUtils;
import egovframework.com.campustown.counsel.legal_counsel.domain.Lawyer;
import egovframework.com.campustown.counsel.legal_counsel.domain.LegalCounsel;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * The type Add file.
 * 파일 유틸
 */
public abstract class AddFile {
    /**
     * Add file.
     *
     * @param currentSite     the current site
     * @param req             the req
     * @param saveId          the save id
     * @param file            the file
     * @param fileInfoService the file info service
     * @param fileAltText     the file alt text
     * @param fileModuleSub   the file module sub
     * @throws IOException the io exception
     */
    public static void addFile(@CurrentSite Site currentSite, HttpServletRequest req, Integer saveId, MultipartFile file, FileInfoService fileInfoService, String fileAltText,  String fileModuleSub) throws IOException {

        Lawyer lawyer = new Lawyer();
        String webRoot = AsaproUtils.getWebRoot(req);

        FileInfo fileInfo = new FileInfo();
        fileInfo.setSitePrefix(currentSite.getSitePrefix());

        fileInfo.setMemberId("");

        fileInfo.setFileModule("counsel");
        fileInfo.setFileModuleId(lawyer.getSitePrefix());
        //System.out.println("sitePr"+lawyer.getSitePrefix());

        fileInfo.setFileModuleSub(fileModuleSub);
        fileInfo.setFileModuleSubId(String.valueOf(saveId));

        //대체텍스트
        fileInfo.setFileAttachmentType("appending");
        fileInfo.setFileAltText(fileAltText);

        fileInfoService.saveFileNoThumb(file, webRoot,fileInfo);
    }
}
