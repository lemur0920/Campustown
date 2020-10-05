/**
 * 
 */
package egovframework.com.asapro.comment.web;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.URLEncoder;
import java.security.SecureRandom;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.jsoup.safety.Whitelist;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import egovframework.com.asapro.comment.service.Comment;
import egovframework.com.asapro.comment.service.CommentSearch;
/*import egovframework.com.asapro.comment.service.CommentProfile;*/
import egovframework.com.asapro.comment.service.CommentService;
import egovframework.com.asapro.config.service.Config;
import egovframework.com.asapro.config.service.ConfigService;
import egovframework.com.asapro.member.user.service.UserMember;
import egovframework.com.asapro.menu.service.Menu;
import egovframework.com.asapro.site.service.Site;
import egovframework.com.asapro.support.Constant;
import egovframework.com.asapro.support.ServerMessage;
import egovframework.com.asapro.support.annotation.CurrentSite;
import egovframework.com.asapro.support.annotation.CurrentUser;
import egovframework.com.asapro.support.exception.AsaproException;
import egovframework.com.asapro.support.pagination.Paging;
import egovframework.com.asapro.support.util.AsaproUtils;
import egovframework.rte.fdl.cryptography.EgovPasswordEncoder;

/**
 * 댓글 사용자 컨트롤러
 * @author yckim
 * @since 2019. 7. 15.
 *
 */
@Controller
public class CommentUserController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CommentUserController.class);
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private HttpServletRequest request;
	
	@Autowired
	private CommentValidator commentValidator;
	
	@Autowired
	private CommentService commentService;
	
	@Autowired
	private ConfigService configService;
	
	@Autowired
	private EgovPasswordEncoder egovPasswordEncoder;
	

	/**
	 * 댓글을 추가한다.
	 * @param model
	 * @param currentSite
	 * @param currentUser
	 * @param commentForm
	 * @param bindingResult
	 * @param menuId
	 * @param cmtParentId
	 * @return 추가결과
	 */
	@RequestMapping(value=Constant.APP_PATH + Constant.SITE_ID_PATH + "/comment" + Constant.API_PATH + "/new", method=RequestMethod.POST)
	@ResponseBody
	public Map<String, String> insertCommentPost(Model model, @CurrentSite Site currentSite, @CurrentUser UserMember currentUser, @ModelAttribute Comment commentForm, BindingResult bindingResult, @RequestParam(value="menuId", required=true) String menuId, @RequestParam(value="cmtParentId", required=false) Integer cmtParentId){
		
		commentForm.setSitePrefix(currentSite.getSitePrefix());
		String cmtUrl = request.getHeader("referer");
		commentForm.setCmtPageUrl(cmtUrl);
//		Config commentConfig = configService.getConfig("comment");
		
		Map<String, String> result = new HashMap<String, String>();
		
		if( currentUser == null || StringUtils.isBlank(currentUser.getUserLoginType()) ){
			result.put("success", "false");
			result.put("error", "CME001");//작성자 정보가 없습니다...
			return result;
		}
		commentValidator.validate(commentForm, bindingResult);
		if( bindingResult.hasErrors() ){
			//폼 상세검증은 사용자단 스크립트로...
			result.put("success", "false");
			result.put("error", AsaproUtils.getAllFieldErrorMessage(bindingResult, "\\n"));
			
		} else {
			
			//구분 sns? 회원?
			commentForm.setCmtLoginType(currentUser.getUserLoginType() );//facebook, twitter, member...
			//로그인한 상태면 회원 정보
			commentForm.setCmtUserId(currentUser.getUserId() );
			
			//sns일 경우 그정보
			commentForm.setCmtSnsUserId(currentUser.getUserId());
			commentForm.setCmtSnsUserName(currentUser.getUserName());
			commentForm.setCmtSnsUserHome(currentUser.getUserSnsHome());
			commentForm.setCmtProfileImage(currentUser.getUserThumbnailImage());
			
			//제목, 내용
			commentForm.setCmtTitle(AsaproUtils.getJsoupFilteredText(commentForm.getCmtTitle(), Whitelist.none(), false, false));
			commentForm.setCmtContent(AsaproUtils.getJsoupFilteredText(commentForm.getCmtContent(), Whitelist.none(), true, true));
			
//			if( "true".equalsIgnoreCase(commentConfig.getOption("use_moderate")) ){
//				commentForm.setCmStatus("WAITING");
//			} else {
				commentForm.setCmtStatus("APPROVED");
//			}
			
			//부모댓글이 있을 경우 그 아이디
			Comment cmtParent = new Comment();
			cmtParent.setSitePrefix(currentSite.getSitePrefix());
			cmtParent.setCmtId(cmtParentId);
			commentForm.setCmtParent(cmtParent);
			
			commentForm.setCmtRegIp(AsaproUtils.getRempoteIp(request));
			commentForm.setCmtRegDate(new Date());
			
			if( commentService.insertComment(commentForm) > 0 ) {
				result.put("success", "true");
				commentService.syncModuleCommentTotal(commentForm);
			} else {
				result.put("success", "false");
				result.put("error", "CME002");//입력 에러...
			}
		}
		
		return result;
	}

	
	/**
	 * 댓글을 목록을 조회한다.
	 * @param model
	 * @param currentSite
	 * @param currentUser
	 * @param commentSearch
	 * @return 댓글목록
	 */
	@RequestMapping(value=Constant.APP_PATH + Constant.SITE_ID_PATH + "/comment/list", method=RequestMethod.GET)
	public String getCommentListGet(Model model, @CurrentSite Site currentSite, @CurrentUser UserMember currentUser, @ModelAttribute CommentSearch commentSearch, @RequestParam(value="direction", required=false) String direction ){
		commentSearch.setSitePrefix(currentSite.getSitePrefix());
		commentSearch.setPageSize(5);
		commentSearch.setCmtStatus("APPROVED");	//승인된 글만 - 현제는 승인 기능이 따로 없으므로 자동승인처리
		commentSearch.setSortOrder("CMT_REGDATE");
		if(StringUtils.isBlank(commentSearch.getSortDirection()) ){
			commentSearch.setSortDirection("DESC");
		}
		if(StringUtils.isNotBlank(direction) ){
			commentSearch.setSortDirection(direction);
		}
		
		int total = commentService.getCommentListTotal(commentSearch);
		List<Comment> commentList = commentService.getFrontCommentList(commentSearch);
		Paging paging = new Paging(commentList, total, commentSearch);
		model.addAttribute("paging", paging);
		
		return AsaproUtils.getThemePath(request) + "comment/comment";
	}
	
	/**
	 * 댓글을 추천한다.
	 * @param response
	 * @param currentSite
	 * @param cmtId
	 * @return 추천 결과 json
	 */
	@RequestMapping(value=Constant.APP_PATH + Constant.SITE_ID_PATH + "/comment/{cmtId}" + Constant.API_PATH  + "/recommend" , method=RequestMethod.POST)
	@ResponseBody
	public ServerMessage updateBoardArticleRecommendPost(HttpServletResponse response, @CurrentSite Site currentSite, @RequestParam(value="cmtId", required=true) Integer cmtId){
		Cookie recommendCookie = AsaproUtils.getCookie(request.getCookies(), "comm_reco_" + currentSite.getSiteId() + "_" + cmtId);
		if( recommendCookie == null ){
			Comment recommendComment = new Comment();
			recommendComment.setSitePrefix(currentSite.getSitePrefix());
			recommendComment.setCmtId(cmtId);
			if( commentService.updateCommentRecommend(recommendComment) > 0 ){
				recommendCookie = new Cookie("comm_reco_" + currentSite.getSiteId() + "_" + cmtId, System.currentTimeMillis() + "");
				recommendCookie.setDomain(request.getServerName());
				recommendCookie.setPath("/");
				recommendCookie.setHttpOnly(true);
				int t = 60 * 60 * 24 * 365;//1년
				if(t > 31536000){
					t = 31536000;
				}
				recommendCookie.setMaxAge(t);
				response.addCookie(recommendCookie);
				return new ServerMessage(true, "추천되었습니다.");
			} else {
				return new ServerMessage(false, "서버오류로 추천하지 못하였습니다.");
			}
		} else {
			return new ServerMessage(false, "이미 추천하셨습니다.");
		}
	}
	
}
