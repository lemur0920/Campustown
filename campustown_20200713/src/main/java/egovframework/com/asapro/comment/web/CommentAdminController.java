/**
 * 
 */
package egovframework.com.asapro.comment.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import egovframework.com.asapro.comment.service.Comment;
import egovframework.com.asapro.comment.service.CommentSearch;
import egovframework.com.asapro.comment.service.CommentService;
import egovframework.com.asapro.site.service.Site;
import egovframework.com.asapro.support.Constant;
import egovframework.com.asapro.support.ServerMessage;
import egovframework.com.asapro.support.annotation.CurrentSite;
import egovframework.com.asapro.support.pagination.Paging;

/**
 * 댓글 관리자 컨트롤러
 * @author yckim
 * @since 2019. 7. 22.
 *
 */
@Controller
public class CommentAdminController {
	
	@Autowired
	private CommentService commentService;
	
	/**
	 * 댓글 목록뷰를 반환한다.
	 * @param model
	 * @param commentSearch
	 * @return 댓글 목록 뷰
	 */
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/comment/{cmtModule}/list.do", method=RequestMethod.GET)
	public String commentListGet(Model model, @CurrentSite Site currentSite, @ModelAttribute("commentSearch") CommentSearch commentSearch){
		
		commentSearch.setSitePrefix(currentSite.getSitePrefix());
		commentSearch.fixBrokenSvByDefaultCharsets();
		
		int total = commentService.getCommentListTotal(commentSearch);
		List<Comment> list = commentService.getCommentList(commentSearch);
		Paging paging = new Paging(list, total, commentSearch);
		
		model.addAttribute("paging", paging);
		
		return "asapro/admin/comment/list";
	}
	
	/**
	 * 댓글 상태를 변경한다.
	 * @param model
	 * @param catIds
	 * @return 삭제 결과
	 */
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/comment/status.do", method=RequestMethod.POST)
	@ResponseBody
	public ServerMessage commentStatusPost(Model model, @CurrentSite Site currentSite, @RequestParam(value="cmtIds[]", required=true) String[] cmtIds, @RequestParam(value="cmtStatus", required=true) String cmtStatus){
		ServerMessage serverMessage = new ServerMessage();
		if( ArrayUtils.isEmpty(cmtIds) ){
			serverMessage.setSuccess(false);
			serverMessage.setText("변경할 데이터가 없습니다.");
		} else {
			List<Comment> commentList = new ArrayList<Comment>();
			Comment comment = null;
			for( String cmtId : cmtIds ){
				comment = new Comment();
				comment.setSitePrefix(currentSite.getSitePrefix());
				comment.setCmtId(Integer.parseInt(cmtId));
				comment.setCmtStatus(cmtStatus);
				commentList.add(comment);
			}
			int result = commentService.updateCommentStatus(commentList); 
			if( result > 0 ){
				serverMessage.setSuccess(true);
				serverMessage.setSuccessCnt(result);
			} else {
				serverMessage.setSuccess(false);
				serverMessage.setText("변경하지 못하였습니다.[Server Error]");
			}
		}
		return serverMessage;
	}
	
	/**
	 * 삭제상태인 댓글 영구 삭제
	 * @param model
	 * @return
	 */
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/comment/delete.do", method=RequestMethod.POST)
	@ResponseBody
	public ServerMessage commentClearPost(Model model, @CurrentSite Site currentSite){
		ServerMessage serverMessage = new ServerMessage();
		int result = commentService.clearDeletedComments(currentSite.getSitePrefix()); 
		if( result > 0 ){
			serverMessage.setSuccess(true);
			serverMessage.setSuccessCnt(result);
		} else {
			serverMessage.setSuccess(false);
			serverMessage.setText("영구삭제하지 못하였습니다. 삭제상태의 글인지 확인하세요");
		}
		return serverMessage;
	}
}
