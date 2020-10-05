package egovframework.com.campustown.application.service;

import egovframework.com.asapro.archive.service.Archive;
import egovframework.com.asapro.fileinfo.service.FileInfo;
import egovframework.com.campustown.application.domain.XTechApply;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class XTechApplyServiceImpl implements XTechApplyService {

    private XTechApplyMapper xTechApplyMapper;

    @Autowired
    public XTechApplyServiceImpl(XTechApplyMapper xTechApplyMapper) {
        this.xTechApplyMapper = xTechApplyMapper;
    }


    public void XtechApplySave(XTechApply xTechApply){
        xTechApplyMapper.insertXTechApply(xTechApply);
    }

    public void XtechApplyUpdate(XTechApply xTechApply){
        xTechApplyMapper.updateXTechApply(xTechApply);
    }

    @Override
    public XTechApply getXTechApply(int applySeq) {
        return xTechApplyMapper.getXTechApply(applySeq);
    }

    @Override
    public List<XTechApply> getXTechApplyList(XTechApply xTechApply) {
        return xTechApplyMapper.getXTechApplyList(xTechApply);
    }

    @Override
    public int getXTechApplyListTotal(XTechApply xTechApply) {
        return xTechApplyMapper.getXTechApplyListTotal(xTechApply);
    }

    @Override
    public int deleteXTechApply(List<XTechApply> xTechApplyList) {
        int deleted = 0;
        for (XTechApply xTechApply : xTechApplyList) {
            deleted += this.deleteXTechApply(xTechApply);
        }
        return deleted;
    }

    @Override
    public int deleteXTechApply(XTechApply xTechApply) {
        XTechApply fromDB = xTechApplyMapper.getXTechApply(xTechApply.getApplySeq());
        fromDB.setSitePrefix(xTechApply.getSitePrefix());

        int deleted = 0;
        deleted = xTechApplyMapper.deleteArchive(xTechApply);
        return deleted;
    }

//    List<XTechApply> getXTechApplyList(XTechApply xTechApply);

//    int getXTechApplyListTotal(XTechApply xTechApply);
}
