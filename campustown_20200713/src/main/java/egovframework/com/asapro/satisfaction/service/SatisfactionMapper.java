/**
 * 
 */
package egovframework.com.asapro.satisfaction.service;

import java.util.List;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * 만족도 조사 SQL 매퍼
 * @author yckim
 * @since 2019. 4. 15.
 *
 */
@Mapper
public interface SatisfactionMapper {
	
	/**
	 * 만족도 평가를 등록한다. - 메뉴 등록시 호출됨
	 * @param satisfaction
	 * @return 등록결과
	 */
	public int insertSatisfaction(Satisfaction satisfaction);
	
	/**
	 * 만족도 평가 점수를 갱신한다.
	 * @param satisfaction
	 * @return 갱신결과
	 */
	public int updateSatisfaction(Satisfaction satisfaction);
	
	/**
	 * 만족도 평가 결과를 조회한다.
	 * @param satisfactionSearch
	 * @return 조회목록
	 */
	public List<Satisfaction> selectSatisfactionList(SatisfactionSearch satisfactionSearch);
	
	/**
	 * 메뉴의 만족도 평가데이터를 리셋시킨다.(0으로 만든다) 
	 * @param satisfaction
	 * @return 리셋결과
	 */
	public int resetSatisfaction(Satisfaction satisfaction);

	/**
	 * 만족도 정보를 조회한다.
	 * @param menuId
	 * @return 만족도 정보
	 */
	public Satisfaction selectSatisfaction(Satisfaction satisfaction);
	
	/**
	 * 만족도 조사를 삭제한다.
	 * @param satisfaction
	 * @return 삭제결과
	 */
	public int deleteSatisfaction(Satisfaction satisfaction);

	//=================  평가의견  =============================
	//=================  평가의견  =============================
	/**
	 * 평가의견을 추가한다.
	 * @param satisfaction
	 * @return 축라결과
	 */
	public int insertSatisOpinion(Satisfaction satisfaction);

	/**
	 * 평가의견 목록을 반환한다.
	 * @param satisOpinionSearch
	 * @return 평가의견 목록
	 */
	public List<SatisOpinion> selectSatisOpinionList(SatisOpinionSearch satisOpinionSearch);
}
