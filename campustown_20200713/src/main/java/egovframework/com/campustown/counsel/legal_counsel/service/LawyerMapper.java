package egovframework.com.campustown.counsel.legal_counsel.service;

import egovframework.com.campustown.counsel.legal_counsel.domain.Lawyer;
import egovframework.rte.psl.dataaccess.mapper.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface LawyerMapper {
    void lawyerCounselSave(Map lawyerCounselData);

    List<Lawyer> getLawyerList(Lawyer lawyer);

    void lawyerAssignSave(Map lawAssingSaveData);

    void lawyerAssignDel(Map lawyerDelData);

    void lawyerAssignUpdate(Map lawyerUpdteData);

    Integer lawyerAssignSaveId();

    Lawyer getLawyerAssignId(Integer id);

    //List<Lawyer> getLawyerAssginList();
}
