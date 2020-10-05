package egovframework.com.campustown.counsel.legal_counsel.service;

import egovframework.com.asapro.fileinfo.service.FileInfo;
import egovframework.com.campustown.counsel.legal_counsel.domain.LegalCounsel;
import egovframework.rte.psl.dataaccess.mapper.Mapper;

import java.util.List;

@Mapper
public interface LegalCounselMapper {
    List<LegalCounsel> getUserLegalCounselList(LegalCounsel legalCounsel);

    int getUserLegalCounselListTotalCount(LegalCounsel legalCounsel);

    LegalCounsel getUserLegalCounselView(Integer id);

    void getUserLegalCounselSave(LegalCounsel legalCounsel);

    Integer getLegalCounselSaveId();

    FileInfo selectFileInfo(FileInfo fileInfo);
}
