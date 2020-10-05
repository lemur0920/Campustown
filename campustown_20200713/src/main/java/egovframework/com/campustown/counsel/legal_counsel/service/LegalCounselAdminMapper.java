package egovframework.com.campustown.counsel.legal_counsel.service;

import egovframework.com.asapro.fileinfo.service.FileInfo;
import egovframework.com.campustown.counsel.legal_counsel.domain.Lawyer;
import egovframework.com.campustown.counsel.legal_counsel.domain.LegalCounsel;
import egovframework.rte.psl.dataaccess.mapper.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface LegalCounselAdminMapper {
    List<LegalCounsel> getAdminLegalCounselList(LegalCounsel legalCounsel);

    int getAdminLegalCounselListTotalCount(LegalCounsel legalCounsel);

    void statusUpdate(Map statusUpdateDataMap);

    LegalCounsel getAdminLegalCounselView(Integer id);

    FileInfo selectFileInfo(FileInfo fileInfo);

    void getUserLegalCounselSave(LegalCounsel legalCounsel);

    Integer getLegalCounselSaveId();
}
