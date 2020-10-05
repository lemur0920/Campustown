/**
 * 
 */
package egovframework.com.asapro.board.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import egovframework.com.asapro.fileinfo.service.FileInfoUploadResult;
import egovframework.com.asapro.member.Member;
import egovframework.com.asapro.support.exception.AsaproException;
import egovframework.com.asapro.board.service.BoardArticle;
import egovframework.com.asapro.board.service.BoardConfig;

/**
 * 게시판 서비스
 * @author yckim
 * @since 2018. 5. 31.
 *
 */
public interface BoardService {
	
	//======================================================================
	//========================   게시판     ===============================
	//======================================================================
	
	/**
	 * 게시판 목록을 조회한다.
	 * @param boardConfigSearch
	 * @return 게시판 목록
	 */
	public List<BoardConfig> getBoardConfigList(BoardConfigSearch boardConfigSearch);
	
	/**
	 * 게시판 목록 토탈을 조회한다.
	 * @param boardConfigSearch
	 * @return 게시판 목록 토탈
	 */
	public int getBoardConfigListTotal(BoardConfigSearch boardConfigSearch);
	
	/**
	 * 게시판 설정을 조회한다.
	 * @param bcId
	 * @return 게시판 설정
	 */
	public BoardConfig getBoardConfig(BoardConfig boardConfig);
	
	/**
	 * 게시판을 추가한다.
	 * @param boardConfig
	 * @return 추가 결과
	 */
	public int insertBoardConfig(BoardConfig boardConfig);

	/**
	 * 게시판 유형 목록을 조회한다.
	 * @param realPath
	 * @return 게시판 유형목록
	 * @throws FileNotFoundException 
	 */
	public List<String> getbcTypeList() throws FileNotFoundException;

	/**
	 * 게시판을 수정한다.
	 * @param boardConfig
	 * @return 수정 결과
	 */
	public int updateBoardConfig(BoardConfig boardConfig);

	/**
	 * 게시판을 삭제한다.(게시물, 첨부파일 포함)
	 * @param boardConfigList
	 * @return 삭제결과
	 */
	public int deleteBoardConfig(List<BoardConfig> boardConfigList);
	
	/**
	 * 게시판을 삭제한다.(게시물, 첨부파일 포함)
	 * @param boardConfig
	 * @return 삭제결과
	 */
	public int deleteBoardConfig(BoardConfig boardConfig);

	
	//======================================================================
	//========================   게시글     ===============================
	//======================================================================
	
	/**
	 * 게시물 목록을 반환한다.
	 * @param boardArticleSearch
	 * @return 게시물 목록
	 */
	public List<BoardArticle> getBoardArticleList(BoardArticleSearch boardArticleSearch);

	/**
	 * 게시물 목록 토탈을 반환한다.
	 * @param boardArticleSearch
	 * @return 게시물 목록 토탈
	 */
	public int getBoardArticleListTotal(BoardArticleSearch boardArticleSearch);

	/**
	 * 게시물을 추가한다.
	 * @param boardArticleForm
	 * @return 추가결과
	 */
//	public FileInfoUploadResult insertBoardArticle(BoardArticle boardArticle) throws AsaproException, IOException ;
	
	/**
	 * 게시물을 추가한다.
	 * @param boardArticleForm
	 * @return 추가결과
	 */
	public Map<String, FileInfoUploadResult> insertBoardArticle(BoardArticle boardArticle) throws AsaproException, IOException ;

	/**
	 * 게시물을 조회한다.
	 * @param boardArticleForm
	 * @return 게시물
	 */
	public BoardArticle getBoardArticle(BoardArticle boardArticle);

	/**
	 * 게시물을 수정한다.
	 * @param boardArticleForm
	 * @return 수정결과
	 */
	public Map<String, FileInfoUploadResult> updateBoardArticle(BoardArticle boardArticleForm) throws AsaproException, IOException ;

	/**
	 * 게시물을 삭제한다.
	 * @param boardArticleList
	 * @return 삭제결과
	 */
	public int deleteBoardArticle(List<BoardArticle> boardArticleList);
	
	/**
	 * 게시물을 삭제한다.
	 * @param boardArticle
	 * @return 삭제결과
	 */
	public int deleteBoardArticle(BoardArticle boardArticle);

	/**
	 * 게시판 관리자인지 확인한다.
	 * @param boardConfig
	 * @param currentUser
	 * @return 확인 결과
	 */
	public boolean isBoardManager(BoardConfig boardConfig, Member currentUser);

	/**
	 * 게시물 조회수를 증가시킨다.
	 * @param baId
	 */
	public int updateBoardArticleHit(BoardArticle boardArticle);

	/**
	 * 이전 게시물을 조회한다
	 * @param boardArticle
	 * @return 이전 게시물
	 */
	public BoardArticle getPrevBoardArticle(BoardArticle currentBoardArticle);

	/**
	 * 다음 게시물을 조회한다.
	 * @param boardArticle
	 * @return 다음 게시물
	 */
	public BoardArticle getNextBoardArticle(BoardArticle currentBoardArticle);

	/**
	 * 비회원작성글 비밀번호, 비밀글 비밀번호가 맞는지 확인한다.
	 * @param baIdSearch
	 * @param formMode
	 * @param password
	 * @return 확인결과
	 */
	public boolean isPasswordMatch(BoardArticle baIdSearch, String formMode, String password);

	/**
	 * 게시물 추천 수를 증가한다.
	 * @param recommendArticle
	 * @return 추천증가 결과
	 */
	public int updateBoardArticleRecommend(BoardArticle recommendArticle);

}
