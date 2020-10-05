/**
 * 
 */
package egovframework.com.asapro.poll.service;

import java.util.List;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * 투표 SQL 매퍼
 * @author yckim
 * @since 2020. 1. 21.
 *
 */
@Mapper
public interface PollMapper {
	
	/**
	 * 투표 목록을 조회한다.
	 * @param pollSearch
	 * @return 투표 목록
	 */
	public List<Poll> selectPollList(PollSearch pollSearch);
	
	/**
	 * 투표 개수를 조회한다.
	 * @param pollSearch
	 * @return 투표 개수
	 */
	public int selectPollListTotal(PollSearch pollSearch);
	
	/**
	 * 투표를 조회한다.
	 * @param poll
	 * @return
	 */
	public Poll selectPoll(Poll poll);
	
	/**
	 * 투표를 추가한다.
	 * @param pollForm
	 * @return
	 */
	public int insertPoll(Poll pollForm);
	
	/**
	 * 투표를 수정한다.
	 * @param pollForm
	 * @return
	 */
	public int updatePoll(Poll pollForm);
	
	/**
	 * 투표를 삭제한다.
	 * @param poll
	 * @return
	 */
	public int deletePoll(Poll poll);
	
	/**
	 * 투표 찬성수를 증가한다.
	 * @param pollForm
	 * @return 증가결과
	 */
	public int updatePollYes(Poll pollForm );
	
	/**
	 * 투표 반대수를 증가한다.
	 * @param pollForm
	 * @return 증가결과
	 */
	public int updatePollNo(Poll pollForm );

	/**
	 * 투표 조회수를 증가한다.
	 * @param pollModel
	 * @return 증가결과
	 */
	public int updatePollHit(Poll pollModel);
}
