package egovframework.com.campustown.counsel.legal_counsel.service.impl;

import egovframework.com.asapro.fileinfo.service.FileInfo;
import egovframework.com.campustown.counsel.legal_counsel.domain.Lawyer;
import egovframework.com.campustown.counsel.legal_counsel.domain.LegalCounsel;
import egovframework.com.campustown.counsel.legal_counsel.service.LegalCounselAdminMapper;
import egovframework.com.campustown.counsel.legal_counsel.service.LegalCounselAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class LegalCounselAdminServiceImpl implements LegalCounselAdminService {
    private LegalCounselAdminMapper legalCounselAdminMapper;

    @Autowired
    public LegalCounselAdminServiceImpl(LegalCounselAdminMapper legalCounselAdminMapper) {
        this.legalCounselAdminMapper = legalCounselAdminMapper;
    }

    @Override
    public List<LegalCounsel> getAdminLegalCounselList(LegalCounsel legalCounsel) {
        return legalCounselAdminMapper.getAdminLegalCounselList(legalCounsel);
    }

    @Override
    public int getAdminLegalCounselListTotalCount(LegalCounsel legalCounsel) {
        return legalCounselAdminMapper.getAdminLegalCounselListTotalCount(legalCounsel);
    }

    @Override
    public void statusUpdate(Map statusUpdateDataMap) {
        legalCounselAdminMapper.statusUpdate(statusUpdateDataMap);
    }

    @Override
    public LegalCounsel getAdminLegalCounselView(Integer id) {
        return legalCounselAdminMapper.getAdminLegalCounselView(id);
    }

    @Override
    public FileInfo selectFileInfo(FileInfo fileInfo) {
        return legalCounselAdminMapper.selectFileInfo(fileInfo);
    }

    @Override
    public void getUserLegalCounselSave(LegalCounsel legalCounsel) {
        legalCounselAdminMapper.getUserLegalCounselSave(legalCounsel);
    }

    @Override
    public Integer getLegalCounselSaveId() {
        return legalCounselAdminMapper.getLegalCounselSaveId();
    }
}
