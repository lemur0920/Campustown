/**
 * 
 */
package egovframework.com.asapro.comment.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import egovframework.com.asapro.archive.service.Archive;
import egovframework.com.asapro.archive.service.ArchiveMapper;
import egovframework.com.asapro.board.service.BoardArticle;
import egovframework.com.asapro.board.service.BoardMapper;
import egovframework.com.asapro.comment.service.Comment;
import egovframework.com.asapro.comment.service.CommentMapper;
import egovframework.com.asapro.comment.service.CommentSearch;
import egovframework.com.asapro.comment.service.CommentService;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import egovframework.rte.fdl.cryptography.EgovPasswordEncoder;

/**
 * 댓글 서비스 구현
 * @author yckim
 * @since 2019. 7. 15.
 *
 */
@Service
public class CommentServiceImpl extends EgovAbstractServiceImpl implements CommentService{

	private static final Logger LOGGER = LoggerFactory.getLogger(CommentServiceImpl.class);
	
	@Autowired
	private CommentMapper commentMapper;
	
	@Autowired
	private BoardMapper boardMapper;
	
	@Autowired
	private ArchiveMapper archiveMapper;
	
	@Autowired
	private EgovPasswordEncoder egovPasswordEncoder;
	

	/**
	 * 댓글 추가
	 */
	@Override
	public int insertComment(Comment commentForm) {
		
		int inserted = commentMapper.insertComment(commentForm);
		return inserted; 
	}

	/**
	 * 댓글기능이 사용된 모듈의 댓글 개수 정보를 업데이트 한다.
	 */
	@Override
	public int syncModuleCommentTotal(Comment commentForm) {
		//게시판 인 경우
		if( "board".equals(commentForm.getCmtModule()) ){
			commentForm.setCmtStatus("APPROVED");
			int approvedCommenetTotal = commentMapper.countCommentTotal(commentForm);
			
			BoardArticle boardArticle = new BoardArticle();
			boardArticle.setSitePrefix(commentForm.getSitePrefix());
			boardArticle.setBaId(Integer.parseInt(commentForm.getCmtModItemId()) );
			boardArticle.setBaCommentTotal(approvedCommenetTotal);
			boardMapper.updateBoardArticleCommentTotal(boardArticle);
			LOGGER.info("[ASAPRO] CommentServiceImpl board comment total updated baId : {}, baCommentTotal : {}", boardArticle.getBaId(), boardArticle.getBaCommentTotal());
		}
		return 0;
	}

	/**
	 * 사용자단에 표시할 댓글 목록을 반환한다.
	 */
	@Override
	public List<Comment> getFrontCommentList(CommentSearch commentSearch) {
		List<Comment> commentList = commentMapper.selectCommentList(commentSearch);
		List<Comment> parents = new ArrayList<Comment>();
		List<Comment> children = new ArrayList<Comment>();
		
		for( Comment comment : commentList ){
			if( comment.getCmtParent() == null || comment.getCmtParent().getCmtId().intValue() == 0 ){
				parents.add(comment);
			} else {
				children.add(comment);
			}
		}
		
		Collections.reverse(children);
		
		for( Comment parent : parents ){
			if( parent.getCmtChildren() == null ){
				parent.setCmtChildren(new ArrayList<Comment>());
			}
			for( Comment child : children ){
				if( parent.getCmtId().intValue() == child.getCmtParent().getCmtId().intValue() ){
					parent.getCmtChildren().add(child);
				}
			}
		}
		
		return parents;
	}

	/**
	 * 댓글 목록 토탈를 조회한다.
	 */
	@Override
	public int getCommentListTotal(CommentSearch commentSearch) {
		return commentMapper.selectCommentListTotal(commentSearch);
	}

	/**
	 * 댓글 목록을 조회한다.
	 */
	@Override
	public List<Comment> getCommentList(CommentSearch commentSearch) {
		return commentMapper.selectCommentList(commentSearch);
	}

	/**
	 * 댓글 상태를 변경한다.
	 */
	@Override
	public int updateCommentStatus(List<Comment> commentList) {
		int updated = 0;
		for( Comment comment : commentList ){
			updated += this.updateCommentStatus(comment);
		}
		return updated;
	}

	/**
	 * 댓글 상태를 변경한다.
	 */
	@Override
	public int updateCommentStatus(Comment comment) {
		int updatedCnt = commentMapper.updateCommentStatus(comment);
		
		//댓글 개수를 게시물정보에 동기화 한다.
		Comment fromDB = commentMapper.selectComment(comment);
		fromDB.setSitePrefix(comment.getSitePrefix());
		this.syncModuleCommentTotal(fromDB);
		
		return updatedCnt;
	}

	/**
	 * 상태가 삭제인 댓글을 영구 삭제한다.
	 */
	@Override
	public int clearDeletedComments(String sitePrefix) {
		return commentMapper.clearDeletedComments(sitePrefix);
	}

	/**
	 * 댓글의 추천수를 증가시킨다.
	 */
	@Override
	public int updateCommentRecommend(Comment recommendComment) {
		return commentMapper.updateCommentRecommend(recommendComment);
	}

	
}