package egovframework.com.campustown.counsel.make_counsel.service;

import egovframework.com.campustown.counsel.make_counsel.domain.MakeCounsel;
import egovframework.rte.psl.dataaccess.mapper.Mapper;

import java.util.List;
import java.util.Map;

/**
 * The interface Make counsel mapper.
 */
@Mapper
public interface MakeCounselMapper {

    /**
     * Gets user make counsel list total count.
     * 유저 리스트 토탈 카운트
     *
     * @param makeCounsel the make counsel
     * @return the user make counsel list total count
     */
    int getUserMakeCounselListTotalCount(MakeCounsel makeCounsel);

    /**
     * Gets user make counsel list.
     * 유저 리스트 목록
     *
     * @param makeCounsel the make counsel
     * @return the user make counsel list
     */
    List<MakeCounsel> getUserMakeCounselList(MakeCounsel makeCounsel);

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
     * @param value the value
     */
    void getUserMakeCounselDetail(Map value);

    /**
     * Gets user make coun sel detail list.
     * 유저 분야 리스트 목록
     *
     * @return the user make coun sel detail list
     */
    List<MakeCounsel> getUserMakeCounSelDetailList();
    

    MakeCounsel getUserMakeCounselView(Integer id);
    List<MakeCounsel> getUserMakeCounSelDetailViewList(Integer id);
    
    void getUserMakeCounselUpdate(MakeCounsel makeCounselForm);

    int getuserFileId(Integer id);
    int getFileCount(Integer id);

    void removeUserMakeCounselDetail(int makeCounselSeq);
}
