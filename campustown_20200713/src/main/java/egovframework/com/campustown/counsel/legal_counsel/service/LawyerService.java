package egovframework.com.campustown.counsel.legal_counsel.service;

import egovframework.com.campustown.counsel.legal_counsel.domain.Lawyer;

import java.util.List;
import java.util.Map;

public interface LawyerService {
    void lawyerCounselSave(Map lawyerCounselData);

    List<Lawyer> getLawyerList(Lawyer lawyer);

    void lawyerAssignSave(Map lawAssingSaveData);

    void lawyerAssignDel(Map lawyerDelData);

    void lawyerAssignUpdate(Map lawyerUpdteData);

    Integer lawyerAssignSaveId();

    Lawyer getLawyerAssignId(Integer id);

//    List<Lawyer> getLawyerAssginList();
}
