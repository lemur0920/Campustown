package egovframework.com.campustown.counsel.legal_counsel.service;

import egovframework.com.asapro.fileinfo.service.FileInfo;
import egovframework.com.campustown.counsel.legal_counsel.domain.LegalCounsel;

import java.util.List;

/**
 * The interface Legal advice service.
 */
public interface LegalCounselService {

    /**
     * Gets user legal advice list.
     * 법률 자문 유저 리스트
     *
     * @param legalCounsel the legal advice
     * @return the user legal advice list
     */
    List<LegalCounsel> getUserLegalCounselList(LegalCounsel legalCounsel);

    /**
     * Gets user legal advice list total count.
     * 법률 자문 유저 리스트 총 글 수
     *
     * @param legalCounsel the legal advice
     * @return the user legal advice list total count
     */
    int getUserLegalCounselListTotalCount(LegalCounsel legalCounsel);

    /**
     * Gets legal advice save.
     * 법률 자문 유저 글 등
     *
     * @param legalCounsel the legal advice
     */
    void getUserLegalCounselSave(LegalCounsel legalCounsel);

    /**
     * Gets user legal advice view.록
     * 법률 자문 유저 글 상세
     *
     * @param id the id
     * @return the user legal advice view
     */
    LegalCounsel getUserLegalCounselView(Integer id);

    /**
     * Gets legal advice save id.세
     * 법률 자문 유저 글 등록 시퀀스 가져오기
     *
     * @return the legal advice save id
     */
    Integer getLegalCounselSaveId();

    FileInfo selectFileInfo(FileInfo fileInfo);
}
