package egovframework.com.campustown.application.service;

import egovframework.com.campustown.application.domain.XTechApply;

import java.util.List;

public interface XTechApplyService {

    void XtechApplySave(XTechApply xTechApply);
    void XtechApplyUpdate(XTechApply xTechApply);

    XTechApply getXTechApply(int applySeq);

    List<XTechApply> getXTechApplyList(XTechApply xTechApply);

    int getXTechApplyListTotal(XTechApply xTechApply);

    int deleteXTechApply(List<XTechApply> xTechApplyList);

    int deleteXTechApply(XTechApply xTechApplyList);

}
