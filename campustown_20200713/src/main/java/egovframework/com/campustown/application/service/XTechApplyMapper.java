package egovframework.com.campustown.application.service;

import egovframework.com.campustown.application.domain.XTechApply;
import egovframework.rte.psl.dataaccess.mapper.Mapper;

import java.util.List;

@Mapper
public interface XTechApplyMapper {

    void insertXTechApply(XTechApply xTechApply);

    void updateXTechApply(XTechApply xTechApply);

    XTechApply getXTechApply(int applySeq);

    List<XTechApply> getXTechApplyList(XTechApply xTechApplyList);

    int getXTechApplyListTotal(XTechApply xTechApply);

    int deleteArchive(XTechApply xTechApply);
}
