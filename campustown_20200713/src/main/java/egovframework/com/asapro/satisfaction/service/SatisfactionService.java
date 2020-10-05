/**
 * 
 */
package egovframework.com.asapro.satisfaction.service;

import java.util.List;
import java.util.Map;

/**
 * 만족도 조사 서비스 인터페이스
 * @author yckim
 * @since 2019. 4. 15.
 *
 */
public interface SatisfactionService {
	
	/**
	 * 만족도 조사를 수정한다.
	 * @param satisfaction
	 * @return 수정결과
	 */
	public int updateSatisfaction(Satisfaction satisfaction);
	
	/**
	 * 만족도 조사결과 맵을 조회한다.
	 * @param satisfactionSearch
	 * @return 조회결과 맵
	 */
	public Map<String, Satisfaction> getSatisfactionMap(SatisfactionSearch satisfactionSearch);

	/**
	 * 만족도 조사결과를 조회한다.(단건)
	 * @param satisfaction
	 * @return 조회결과
	 */
	public Satisfaction getSatisfaction(Satisfaction satisfaction);
	
	/**
	 * 메뉴의 만족도 평가데이터를 리셋시킨다.(0으로 만든다) 
	 * @param satisfaction
	 * @return 리셋결과
	 */
	public int resetSatisfaction(Satisfaction satisfaction);

	//========================  평가의견  ============================================
	//========================  평가의견  ============================================
	/**
	 * 평가의견 목록을 반환한다.
	 * @param satisOpinionSearch
	 * @return 평가의견목록
	 */
	public List<SatisOpinion> getSatisOpinionList(SatisOpinionSearch satisOpinionSearch);

	
}
