/**
 * 
 */
package egovframework.com.asapro.satisfaction.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import egovframework.com.asapro.satisfaction.service.SatisOpinion;
import egovframework.com.asapro.satisfaction.service.SatisOpinionSearch;
import egovframework.com.asapro.satisfaction.service.Satisfaction;
import egovframework.com.asapro.satisfaction.service.SatisfactionMapper;
import egovframework.com.asapro.satisfaction.service.SatisfactionSearch;
import egovframework.com.asapro.satisfaction.service.SatisfactionService;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

/**
 * 만족도 평가 구현
 * @author yckim
 * @since 2019. 4. 15.
 *
 */
@Service
public class SatisfactionServiceImpl extends EgovAbstractServiceImpl implements SatisfactionService{

	@Autowired
	private SatisfactionMapper satisfactionMapper;
	
	/**
	 * 만족도평가 수정
	 */
	@Override
	public int updateSatisfaction(Satisfaction satisfaction) {
		
		Satisfaction fromDB = satisfactionMapper.selectSatisfaction(satisfaction);
		if( fromDB == null ){
			Satisfaction baseSatisfaction = new Satisfaction();
			baseSatisfaction.setSitePrefix(satisfaction.getSitePrefix());
			baseSatisfaction.setMenuId(satisfaction.getMenuId());
			baseSatisfaction.setSatisLastPartiDate(new Date());
			baseSatisfaction.setSatisResetDate(new Date());
			satisfactionMapper.insertSatisfaction(baseSatisfaction);
		}
		satisfaction.setSatisLastPartiDate(new Date());
		int result = satisfactionMapper.updateSatisfaction(satisfaction);
		
		//평가의견 등록
		if(satisfaction.getOpinion() != null && StringUtils.isNotBlank(satisfaction.getOpinion().getSatisOpinion()) ){
			satisfaction.getOpinion().setSatisOpiDate(new Date());
			satisfactionMapper.insertSatisOpinion(satisfaction);
		}
		return result;
	}

	/**
	 * 만족도 평가 맵
	 */
	@Override
	public Map<String, Satisfaction> getSatisfactionMap(SatisfactionSearch satisfactionSearch) {
		List<Satisfaction> list = satisfactionMapper.selectSatisfactionList(satisfactionSearch);
		Map<String, Satisfaction> map = new HashMap<String, Satisfaction>();
		for( Satisfaction satisfaction : list ){
			//satisfaction.setSitePrefix(satisfactionSearch.getSitePrefix());
			map.put(satisfaction.getMenuId(), satisfaction);
		}
		return map;
	}

	/**
	 * 만족도 평가 (단건)
	 */
	@Override
	public Satisfaction getSatisfaction(Satisfaction satisfaction) {
		Satisfaction satisfactionModel = satisfactionMapper.selectSatisfaction(satisfaction);
		return satisfactionModel;
	}
	
	/**
	 * 만족도 평가 리셋
	 */
	@Override
	public int resetSatisfaction(Satisfaction satisfaction) {
		satisfaction.setSatisResetDate(new Date());
		return satisfactionMapper.resetSatisfaction(satisfaction);
	}

	//===========================  평가의견  =====================================
	//===========================  평가의견  =====================================
	/**
	 * 평가의견 목록을 반환한다.
	 */
	@Override
	public List<SatisOpinion> getSatisOpinionList(SatisOpinionSearch satisOpinionSearch) {
		return satisfactionMapper.selectSatisOpinionList(satisOpinionSearch);
	}


}
