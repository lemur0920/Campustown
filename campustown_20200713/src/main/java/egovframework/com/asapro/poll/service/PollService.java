/**
 * 
 */
package egovframework.com.asapro.poll.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import egovframework.com.asapro.fileinfo.service.FileInfoUploadResult;
import egovframework.com.asapro.support.exception.AsaproException;

/**
 *	튜표 서비스
 * @author yckim
 * @since 2020. 1. 21.
 *
 */
public interface PollService {
	
	/**
	 * 투표 목록을 조회한다.
	 * @param pollSearch
	 * @return 투표 목록
	 */
	public List<Poll> getPollList(PollSearch pollSearch);
	
	/**
	 * 투표 개수를 조회한다.
	 * @param pollSearch
	 * @return 투표 개수
	 */
	public int getPollListTotal(PollSearch pollSearch);
	
	/**
	 * 투표를 조회한다.
	 * @param bnId
	 * @return
	 */
	public Poll getPoll(Poll poll);
	
	/**
	 * 투표를 추가한다.
	 * @param pollForm
	 * @return
	 * @throws FileNotFoundException 
	 * @throws IOException 
	 * @throws AsaproException 
	 */
	public FileInfoUploadResult insertPoll(Poll pollForm) throws FileNotFoundException, AsaproException, IOException;
	
	/**
	 * 투표를 수정한다.
	 * @param pollForm
	 * @return
	 * @throws FileNotFoundException 
	 */
	public FileInfoUploadResult updatePoll(Poll pollForm) throws AsaproException, IOException;
	
	/**
	 * 투표를 삭제한다.
	 * @param bnIds
	 * @return
	 * @throws FileNotFoundException 
	 */
	public int deletePoll(List<Poll> pollList) throws FileNotFoundException;
	
	/**
	 * 투표를 삭제한다.
	 * @param bnId
	 * @return
	 * @throws FileNotFoundException 
	 */
	public int deletPoll(Poll poll) throws FileNotFoundException;

	/**
	 * 투표 조회수를 증가한다.
	 * @param pollModel
	 * @return 증가결과
	 */
	public int updatePollHit(Poll pollModel);

	/**
	 * 투표를 실시한다.
	 * @param poll
	 * @return 투표결과
	 */
	public int updatePollTake(Poll poll);
	
	
}
