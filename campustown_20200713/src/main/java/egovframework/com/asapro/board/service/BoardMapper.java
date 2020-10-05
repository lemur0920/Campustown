/**
 * 
 */
package egovframework.com.asapro.board.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import egovframework.com.asapro.capability.service.Capability;
import egovframework.com.asapro.fileinfo.service.FileInfo;
import egovframework.com.asapro.board.service.BoardArticle;
import egovframework.com.asapro.board.service.BoardConfig;
import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * 게시판 SQL 매퍼
 * @author yckim
 * @since 2018. 5. 31.
 *
 */
@Mapper
public interface BoardMapper {
	
	/**
	 * 게시판 목록을 조회한다.
	 * @param boardConfigSearch
	 * @return 게시판 목록
	 */
	public List<BoardConfig> selectBoardConfigList(BoardConfigSearch boardConfigSearch);
	
	/**
	 * 게시판 목록 개수를 조회한다.
	 * @param boardConfigSearch
	 * @return 게시판 목록 개수
	 */
	public int selectBoardConfigListTotal(BoardConfigSearch boardConfigSearch);
	
	/**
	 * 게시판 설정을 조회한다.
	 * @param bcId
	 * @return 게시판 설정
	 */
	public BoardConfig selectBoardConfig(BoardConfig boardConfig);
	
	/**
	 * 게시판을 추가한다.
	 * @param boardConfig
	 * @return 추가 결과
	 */
	public int insertBoardConfig(BoardConfig boardConfig);

	/**
	 * 게시판을 수정한다.
	 * @param boardConfig
	 * @return 수정결과
	 */
	public int updateBoardConfig(BoardConfig boardConfig);

	/**
	 * 게시판을 삭제한다.
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
	public List<BoardArticle> selectBoardArticleList(BoardArticleSearch boardArticleSearch);

	/**
	 * 게시물 목록 토탈을 반환한다.
	 * @param boardArticleSearch
	 * @return 게시물 목록 토탈
	 */
	public int selectBoardArticleListTotal(BoardArticleSearch boardArticleSearch);

	/**
	 * 게시물을 추가한다.
	 * @param boardArticle
	 * @return 추가결과
	 */
	public int insertBoardArticle(BoardArticle boardArticle);

	/**
	 * 게시물을 조회한다.
	 * @param boardArticle
	 * @return 게시물
	 */
	public BoardArticle selectBoardArticle(BoardArticle boardArticle);

	/**
	 * 게시물을 수정한다.
	 * @param boardArticle
	 * @return 수정결과
	 */
	public int updateBoardArticle(BoardArticle boardArticle);

	/**
	 * 게시물을 삭제한다.
	 * @param boardArticle
	 * @return 삭제결과
	 */
	public int deleteBoardArticle(BoardArticle boardArticle);
	
	/**
	 * 게시물 첨부파일정보 목록을 조회한다.
	 * @param fileInfo
	 * @return 첨부파일정보 목록
	 */
	public List<FileInfo> selectBoardArticleFileInfoList(FileInfo fileInfo);

	/**
	 * 게시물 조회수 증가시킨다.
	 * @param boardArticle
	 * @return 증가결롸
	 */
	public int updateBoardArticleHit(BoardArticle boardArticle);

	/**
	 * 이전 게시물을 조회하낟.
	 * @param currentBoardArticle
	 * @return 이전 게시물
	 */
	public BoardArticle selectPrevBoardArticle(BoardArticle currentBoardArticle);

	/**
	 * 다음 게시물을 조회한다.
	 * @param currentBoardArticle
	 * @return 다음 게시물
	 */
	public BoardArticle selectNextBoardArticle(BoardArticle currentBoardArticle);

	/**
	 * 게시물 추천수를 증가한다.
	 * @param recommendArticle
	 * @return 게시물 추천수 증가 결과
	 */
	public int updateBoardArticleRecommend(BoardArticle recommendArticle);
	
	/**
	 * 게시물 댓글 수를 업데이트 한다.
	 * @param boardArticle
	 * @return 수정결과
	 */
	public int updateBoardArticleCommentTotal(BoardArticle boardArticle);

}
