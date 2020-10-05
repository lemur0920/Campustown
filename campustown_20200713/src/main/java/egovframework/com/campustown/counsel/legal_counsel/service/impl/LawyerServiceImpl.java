package egovframework.com.campustown.counsel.legal_counsel.service.impl;

import egovframework.com.campustown.counsel.legal_counsel.domain.Lawyer;
import egovframework.com.campustown.counsel.legal_counsel.service.LawyerMapper;
import egovframework.com.campustown.counsel.legal_counsel.service.LawyerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class LawyerServiceImpl implements LawyerService {
    private LawyerMapper lawyerMapper;

    @Autowired
    public LawyerServiceImpl(LawyerMapper lawyerMapper) {
        this.lawyerMapper = lawyerMapper;
    }

    @Override
    public List<Lawyer> getLawyerList(Lawyer lawyer) {
        return lawyerMapper.getLawyerList(lawyer);
    }

    @Override
    public void lawyerAssignSave(Map lawAssingSaveData) {
        lawyerMapper.lawyerAssignSave(lawAssingSaveData);
    }

    @Override
    public void lawyerAssignDel(Map lawyerDelData) {
        lawyerMapper.lawyerAssignDel(lawyerDelData);
    }

    @Override
    public void lawyerAssignUpdate(Map lawyerUpdteData) {
        lawyerMapper.lawyerAssignUpdate(lawyerUpdteData);
    }

    @Override
    public void lawyerCounselSave(Map lawyerCounselData) {
        lawyerMapper.lawyerCounselSave(lawyerCounselData);
    }

    @Override
    public Integer lawyerAssignSaveId() {
        return lawyerMapper.lawyerAssignSaveId();
    }

    @Override
    public Lawyer getLawyerAssignId(Integer id) {
        return lawyerMapper.getLawyerAssignId(id);
    }

//    @Override
//    public List<Lawyer> getLawyerAssginList() {
//        return lawyerMapper.getLawyerAssginList();
//    }
}
