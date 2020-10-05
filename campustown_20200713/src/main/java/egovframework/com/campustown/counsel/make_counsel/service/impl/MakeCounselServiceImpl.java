package egovframework.com.campustown.counsel.make_counsel.service.impl;

import egovframework.com.campustown.counsel.make_counsel.domain.MakeCounsel;
import egovframework.com.campustown.counsel.make_counsel.service.MakeCounselMapper;
import egovframework.com.campustown.counsel.make_counsel.service.MakeCounselService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MakeCounselServiceImpl implements MakeCounselService {

    private final MakeCounselMapper makeCounselMapper;

    @Autowired
    public MakeCounselServiceImpl(MakeCounselMapper makeCounselMapper) {
        this.makeCounselMapper = makeCounselMapper;
    }

    @Override
    public List<MakeCounsel> getMakeCounselList(MakeCounsel makeCounsel) {
        List<MakeCounsel> getMakeCounselList = makeCounselMapper.getUserMakeCounselList(makeCounsel);
        return getMakeCounselList;
    }

    @Override
    public int getMakeCounselListTotalCount(MakeCounsel makeCounsel) {
        return makeCounselMapper.getUserMakeCounselListTotalCount(makeCounsel);
    }

    @Override
    public List<MakeCounsel> getUserMakeCounSelDetailList(MakeCounsel makeCounsel) {
        List<MakeCounsel> counselsDetailList = makeCounselMapper.getUserMakeCounSelDetailList();
        return counselsDetailList;
    }

    @Override
    public List<MakeCounsel> getUserMakeCounSelDetailViewList(int makeCounselSeq) {
        List<MakeCounsel> counselsDetailList = makeCounselMapper.getUserMakeCounSelDetailViewList(makeCounselSeq);
        return counselsDetailList;
    }

    @Override
    public Integer getUserMakeCounselSave(MakeCounsel makeCounselForm) {
        return makeCounselMapper.getUserMakeCounselSave(makeCounselForm);
    }

    @Override
    public void getUserMakeCounselDetail(Integer getMakeCounselSaveId, MakeCounsel makeCounselForm) {
        if(makeCounselForm.getIdea() != null ){
            int size = makeCounselForm.getIdea().size();
            Map value = new HashMap();
            for( int i = 0; i<size; i++ ){
                value.put("makeCounselSeq",getMakeCounselSaveId);
                value.put("type","idea");
                value.put("value" ,makeCounselForm.getIdea().get(i));
                makeCounselMapper.getUserMakeCounselDetail(value);
            }
        }

        if(makeCounselForm.getProductDevelopment() != null || !makeCounselForm.getProductDevelopment().isEmpty()){
            int size = makeCounselForm.getProductDevelopment().size();
            Map value = new HashMap();
            for( int i = 0; i<size; i++ ){
                value.put("makeCounselSeq",getMakeCounselSaveId);
                value.put("type","prod");
                value.put("value" ,makeCounselForm.getProductDevelopment().get(i));
                makeCounselMapper.getUserMakeCounselDetail(value);
            }
        }

        if(makeCounselForm.getPrototypeProduct() != null || !makeCounselForm.getPrototypeProduct().isEmpty()){
            int size = makeCounselForm.getPrototypeProduct().size();
            Map value = new HashMap();
            for( int i = 0; i<size; i++ ){
                value.put("makeCounselSeq",getMakeCounselSaveId);
                value.put("type","prot");
                value.put("value" ,makeCounselForm.getPrototypeProduct().get(i));
                makeCounselMapper.getUserMakeCounselDetail(value);
            }
        }
    }

    @Override
    public MakeCounsel getUserMakeCounselView(Integer id) {
        return makeCounselMapper.getUserMakeCounselView(id);
    }

    @Override
    public void getUserMakeCounselUpdate(MakeCounsel makeCounselForm) {
        makeCounselMapper.getUserMakeCounselUpdate(makeCounselForm);
    }

    @Override
    public int getUserFileId(Integer id) {
        return makeCounselMapper.getuserFileId(id);
    }

    @Override
    public int getFileCount(Integer id) {
        return makeCounselMapper.getFileCount(id);
    }

    @Override
    public void removeUserMakeCounselDetail(int makeCounselSeq) {
        makeCounselMapper.removeUserMakeCounselDetail(makeCounselSeq);
    }
}
