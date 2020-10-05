/**
 * 
 */
package egovframework.com.asapro.poll.web;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import egovframework.com.asapro.poll.service.Poll;
import egovframework.com.asapro.poll.service.PollSearch;
import egovframework.com.asapro.poll.service.PollService;
import egovframework.com.asapro.board.service.BoardArticle;
import egovframework.com.asapro.code.service.Code;
import egovframework.com.asapro.code.service.CodeService;
import egovframework.com.asapro.site.service.Site;
import egovframework.com.asapro.support.Constant;
import egovframework.com.asapro.support.ServerMessage;
import egovframework.com.asapro.support.annotation.CurrentSite;
import egovframework.com.asapro.support.pagination.Paging;
import egovframework.com.asapro.support.util.AsaproUtils;

/**
 * 투표 관리자 컨트롤러
 * @author yckim
 * @since 2020. 1. 21.
 *
 */
@Controller
public class PollUserController {
	
	@Autowired
	private PollService pollService;
	
	@Autowired
	private HttpServletRequest request;
	
	@Autowired
	private CodeService codeService;
	
	/**
	 * 투표 목록을 반환한다.
	 * @param model
	 * @param currentSite
	 * @param pollSearch
	 * @return 목록
	 */
	@RequestMapping(value=Constant.APP_PATH + Constant.SITE_ID_PATH + "/poll/list", method=RequestMethod.GET)
	public String pollListGet(Model model, @CurrentSite Site currentSite, @ModelAttribute("pollSearch") PollSearch pollSearch){
		
		pollSearch.fixBrokenSvByDefaultCharsets();
		pollSearch.setPoUse(true);//게시중인 글만
		pollSearch.setSitePrefix(currentSite.getSitePrefix());
		pollSearch.setPaging(true);
		if(StringUtils.isBlank(pollSearch.getPoStatus()) ){
			pollSearch.setPoStatus("ongoing");//진행중인 글 디폴트
		}
		
		List<Poll> list = pollService.getPollList(pollSearch);
		int total = pollService.getPollListTotal(pollSearch);
		Paging paging = new Paging(list, total, pollSearch);
		model.addAttribute("paging", paging);
		
		return AsaproUtils.getThemePath(request) + "poll/list";
	}
	
	/**
	 * 투표 상세뷰를 반환한다.
	 * @param model
	 * @param currentSite
	 * @param pollForm
	 * @return 상세뷰
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	@RequestMapping(value=Constant.APP_PATH + Constant.SITE_ID_PATH + "/poll/view", method=RequestMethod.GET)
	public String spaceViewGet(Model model, @CurrentSite Site currentSite, @ModelAttribute("pollForm") Poll pollForm) throws IllegalAccessException, InvocationTargetException {
		pollForm.setSitePrefix(currentSite.getSitePrefix());
		Poll pollModel = pollService.getPoll(pollForm);
		model.addAttribute("pollForm", pollModel);
		
		List<Code> pollTypeCodeList = codeService.getCodeList("pollType");
		model.addAttribute("pollTypeCodeList", pollTypeCodeList );
		
		//클릭시마다 조회수 증가
		if(pollModel != null){
			pollModel.setSitePrefix(currentSite.getSitePrefix());
			
			//조회수 + 1
			int hit = pollService.updatePollHit(pollModel);
		}
		
		//목록 돌아가기 검색VO
		PollSearch backListSearch = new PollSearch();
		BeanUtils.populate(backListSearch, request.getParameterMap());
		backListSearch.fixBrokenSvByDefaultCharsets();
		model.addAttribute("backListSearch", backListSearch);
		return AsaproUtils.getThemePath(request) + "poll/view";
	}
	
	
	/**
	 * 투표를 진행한다..
	 * @param response
	 * @param currentSite
	 * @param poId
	 * @return 투표 결과 json
	 */
	@RequestMapping(value=Constant.APP_PATH + Constant.SITE_ID_PATH + "/poll/{poId}" + Constant.API_PATH  + "/take" , method=RequestMethod.POST)
	@ResponseBody
	public ServerMessage updateBoardArticleRecommendPost(HttpServletResponse response, @CurrentSite Site currentSite, @ModelAttribute("poll") Poll poll){
		Cookie pollCookie = AsaproUtils.getCookie(request.getCookies(), "poll_" + currentSite.getSiteId() + "_" + poll.getPoId());
		if( pollCookie == null ){
			poll.setSitePrefix(currentSite.getSitePrefix());
			if( pollService.updatePollTake(poll) > 0 ){
				pollCookie = new Cookie("poll_" + currentSite.getSiteId() + "_" + poll.getPoId(), System.currentTimeMillis() + "");
				pollCookie.setDomain(request.getServerName());
				pollCookie.setPath("/");
				pollCookie.setHttpOnly(true);
				int t = 60 * 60 * 24 * 365;//1년
				if(t > 31536000) {
					t = 31536000;
				}
				pollCookie.setMaxAge(t);
				response.addCookie(pollCookie);
				return new ServerMessage(true, "투표가 정상적으로 완료되었습니다.");
			} else {
				return new ServerMessage(false, "서버오류로 투표하지 못하였습니다.");
			}
		} else {
			return new ServerMessage(false, "이미 투표에 참여하셨습니다.");
		}
	}
	
}
