/**
 * 
 */
package egovframework.com.asapro.comment.service;

import java.util.List;


import egovframework.com.asapro.comment.service.CommentSearch;
import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * 댓글 SQL 매퍼
 * @author yckim
 * @since 2019. 7. 15.
 *
 */
@Mapper
public interface CommentMapper {
	
	/**
	 * 댓글를 추가한다.
	 * @param Comment
	 * @return 추가결과
	 */
	public int insertComment(Comment commentForm);

	/**
	 * 댓글 목록을 조회한다.
	 * @param commentSearch
	 * @return 댓글 목록
	 */
	public List<Comment> selectCommentList(CommentSearch commentSearch);
	
	/**
	 * 댓글 목록 토탈을 조회한다.
	 * @param commentSearch
	 * @return 목록 토탈
	 */
	public int selectCommentListTotal(CommentSearch commentSearch);

	/**
	 * 해당글의 댓글 개수를 조회한다.
	 * @param commentForm
	 * @return 댓글 갯수
	 */
	public int countCommentTotal(Comment commentForm);

	/**
	 * 댓글 상태를 변경한다.
	 * @param comment
	 * @return 수정결과
	 */
	public int updateCommentStatus(Comment comment);

	/**
	 * 댓글를 조회한다.
	 * @param comment
	 * @return 댓글
	 */
	public Comment selectComment(Comment comment);
	
	/**
	 * 부모 댓글을 조회한다.
	 * @param comment
	 * @return 부모댓글
	 */
	public Comment selectCommentParent(Comment comment);

	/**
	 * 상태가 삭제인 댓글을 영구 삭제한다.
	 * @param sitePrefix
	 * @return 삭제결과
	 */
	public int clearDeletedComments(String sitePrefix);

	/**
	 * 댓글의 추천수를 증가시킨다.
	 * @param recommendComment
	 * @return 추천결과
	 */
	public int updateCommentRecommend(Comment recommendComment);


}
