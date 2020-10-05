package egovframework.com.campustown.counsel.make_counsel.service;

import egovframework.com.campustown.counsel.make_counsel.domain.MakeCounsel;

import java.util.List;

/**
 * The interface Make counsel service.
 */
public interface MakeCounselService {

    /**
     * Gets make counsel list.
     * 유저 리스트 목록
     *
     * @param makeCounsel the make counsel
     * @return the make counsel list
     */
    List<MakeCounsel> getMakeCounselList(MakeCounsel makeCounsel);

    /**
     * Gets make counsel list total count.
     * 유저 리스트 토탈 카운트
     *
     * @param makeCounsel the make counsel
     * @return the make counsel list total count
     */
    int getMakeCounselListTotalCount(MakeCounsel makeCounsel);

    /**
     * Gets user make counsel save.
     * 유저 글 등록
     *
     * @param makeCounselForm the make counsel form
     * @return the user make counsel save
     */
    Integer getUserMakeCounselSave(MakeCounsel makeCounselForm);

    /**
     * Gets user make counsel detail.
     * 유저 분야 등록
     *
     * @param getMakeCounselSaveId the get make counsel save id
     * @param makeCounselForm      the make counsel form
     */
    void getUserMakeCounselDetail(Integer getMakeCounselSaveId, MakeCounsel makeCounselForm);

    /**
     * Gets user make coun sel detail list.
     * 유저 분야 리스트 목록
     *
     * @param makeCounsel the make counsel
     * @return the user make coun sel detail list
     */
    List<MakeCounsel> getUserMakeCounSelDetailList(MakeCounsel makeCounsel);

    List<MakeCounsel> getUserMakeCounSelDetailViewList(int makeCounselSeq);

    MakeCounsel getUserMakeCounselView(Integer id);

    void getUserMakeCounselUpdate(MakeCounsel makeCounselForm);

    int getUserFileId(Integer id);
    int getFileCount(Integer id);

    void removeUserMakeCounselDetail(int makeCounselSeq);
}
