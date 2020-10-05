/**
 * 
 */
package egovframework.com.asapro.comment.service;

import java.util.List;

import egovframework.com.asapro.comment.service.Comment;
import egovframework.com.asapro.comment.service.CommentSearch;

/**
 * 댓글 서비스
 * @author yckim
 * @since 2019. 7. 15.
 *
 */
public interface CommentService {
	
	/**
	 * 댓글를 추가한다.
	 * @param commentForm
	 * @return 추가결과
	 */
	public int insertComment(Comment commentForm);

	/**
	 * 댓글기능이 사용된 모듈의 댓글 개수 정보를 업데이트 한다.
	 * @param commentForm
	 * @return 수정결과
	 */
	public int syncModuleCommentTotal(Comment commentForm);
	
	/**
	 * 사용자단에 표시할 댓글 목록을 반환한다.
	 * @param commentSearch
	 * @return 댓글 목록
	 */
	public List<Comment> getFrontCommentList(CommentSearch commentSearch);

	/**
	 * 댓글 목록 토탈를 조회한다.
	 * @param commentSearch
	 * @return 목록 토탈
	 */
	public int getCommentListTotal(CommentSearch commentSearch);

	/**
	 * 댓글 목록을 조회한다.
	 * @param commentSearch
	 * @return 댓글 목록
	 */
	public List<Comment> getCommentList(CommentSearch commentSearch);

	/**
	 * 댓글 상태를 변경한다.
	 * @param commentList
	 * @return 변경 결과
	 */
	public int updateCommentStatus(List<Comment> commentList);
	
	/**
	 * 댓글 상태를 변경한다.
	 * @param comment
	 * @return 변경 결과
	 */
	public int updateCommentStatus(Comment comment);

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
