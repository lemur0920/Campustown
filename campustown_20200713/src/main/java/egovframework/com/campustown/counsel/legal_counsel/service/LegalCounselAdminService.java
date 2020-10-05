package egovframework.com.campustown.counsel.legal_counsel.service;

import egovframework.com.asapro.fileinfo.service.FileInfo;
import egovframework.com.campustown.counsel.legal_counsel.domain.Lawyer;
import egovframework.com.campustown.counsel.legal_counsel.domain.LegalCounsel;

import java.util.List;
import java.util.Map;

public interface LegalCounselAdminService {

    List<LegalCounsel> getAdminLegalCounselList(LegalCounsel legalCounsel);

    int getAdminLegalCounselListTotalCount(LegalCounsel legalCounsel);

    void statusUpdate(Map statusUpdateDataMap);

    LegalCounsel getAdminLegalCounselView(Integer id);

    FileInfo selectFileInfo(FileInfo fileInfo);

    Integer getLegalCounselSaveId();

    void getUserLegalCounselSave(LegalCounsel legalCounsel);
}
