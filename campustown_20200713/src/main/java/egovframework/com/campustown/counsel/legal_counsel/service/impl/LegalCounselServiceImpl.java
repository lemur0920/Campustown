package egovframework.com.campustown.counsel.legal_counsel.service.impl;

import egovframework.com.asapro.fileinfo.service.FileInfo;
import egovframework.com.campustown.counsel.legal_counsel.domain.LegalCounsel;
import egovframework.com.campustown.counsel.legal_counsel.service.LegalCounselMapper;
import egovframework.com.campustown.counsel.legal_counsel.service.LegalCounselService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LegalCounselServiceImpl implements LegalCounselService {

    private LegalCounselMapper legalCounselMapper;

    @Autowired
    public LegalCounselServiceImpl(LegalCounselMapper legalCounselMapper) {
        this.legalCounselMapper = legalCounselMapper;
    }

    @Override
    public List<LegalCounsel> getUserLegalCounselList(LegalCounsel legalCounsel) {
        return legalCounselMapper.getUserLegalCounselList(legalCounsel);
    }

    @Override
    public int getUserLegalCounselListTotalCount(LegalCounsel legalCounsel) {
        return legalCounselMapper.getUserLegalCounselListTotalCount(legalCounsel);
    }


    @Override
    public LegalCounsel getUserLegalCounselView(Integer id) {
        return legalCounselMapper.getUserLegalCounselView(id);
    }

    @Override
    public void getUserLegalCounselSave(LegalCounsel legalCounsel) {
        legalCounselMapper.getUserLegalCounselSave(legalCounsel);
    }

    @Override
    public Integer getLegalCounselSaveId() {
        return legalCounselMapper.getLegalCounselSaveId();
    }

    @Override
    public FileInfo selectFileInfo(FileInfo fileInfo) {
        return legalCounselMapper.selectFileInfo(fileInfo);
    }
}
