/**
 * 
 */
package egovframework.com.asapro.interceptor;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.support.RequestContextUtils;

import egovframework.com.asapro.board.service.BoardArticle;
import egovframework.com.asapro.board.service.BoardConfig;
import egovframework.com.asapro.board.service.BoardService;
//import egovframework.com.asapro.group.service.GroupMember;
//import egovframework.com.asapro.group.service.GroupService;
import egovframework.com.asapro.member.Member;
import egovframework.com.asapro.member.admin.service.AdminMember;
import egovframework.com.asapro.member.user.service.UserMember;
import egovframework.com.asapro.site.service.Site;
import egovframework.com.asapro.support.exception.AsaproNoCapabilityException;
import egovframework.com.asapro.support.exception.AsaproNotFoundException;
import egovframework.com.asapro.support.util.AsaproUtils;

/**
 * 게시판 인터셉터
 * <pre> 
 * - 게시판 사용권한 체크는 컨트롤러가 아닌 이곳에서 일괄 처리한다.
 * - !!게시판 컨트롤러에서 권한관련 처리 하지말것
 * </pre>
 * @author yckim
 * @since 2018. 6. 21.
 *
 */
public class BoardInterceptor extends HandlerInterceptorAdapter {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(BoardInterceptor.class);
	
	@Autowired
	private BoardService boardService;
	
//	@Autowired
//	private GroupService groupService;
	
	public static final String BCID_REGEX = "\\/board\\/([a-zA-Z-_0-9]+)\\/?";
	public static final String BAID_REGEX = "\\/board\\/[a-zA-Z-_0-9]+\\/([0-9]+)\\/?";
	
	//주소에서 bcId 추출
	public String getBcIdFromRequestUri(String requestUri){
		Matcher m = Pattern.compile(BCID_REGEX).matcher(requestUri);
		if( m.find() ){
			return m.group(1);
		}
		return null;
	}
	
	//주소에서 baId 추출
	public Integer getBaIdFromRequestUri(String requestUri){
		Matcher m = Pattern.compile(BAID_REGEX).matcher(requestUri);
		if( m.find() ){
			return Integer.parseInt(m.group(1));
		}
		return new Integer(0);
	}
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

		String requestUri = AsaproUtils.getFixedRequestUri(request);
		
		Site currentSite = (Site) request.getAttribute("currentSite");
		
		//SecurityInterceptor에서 세팅된다
		AdminMember currentAdmin = (AdminMember) request.getSession().getAttribute("currentAdmin");
		UserMember currentUser = (UserMember) request.getSession().getAttribute("currentUser");
		Member currentMember = new Member();
		
		String bcId = "";
		Integer baId = 0;
		
		//회원정보 공통 객체에 셋팅
		//관리자 페이지
		if( AsaproUtils.isAdminPath(requestUri) ){
			if(currentAdmin != null){
				currentMember.setMemberFromAdmin(currentAdmin);
			}
			
			bcId = (String) request.getParameter("bcId");
			if(StringUtils.isNotBlank(request.getParameter("baId")) ){
				baId = Integer.parseInt(request.getParameter("baId") ) ;
			}
		}else{
			//사용자 페이지
			if(currentUser != null){
				currentMember.setMemberFromUser(currentUser);
			}
			bcId = this.getBcIdFromRequestUri(requestUri);
			baId = this.getBaIdFromRequestUri(requestUri);
		}
		
		
		//String bcId = currentMenu.getMenuId();
		BoardConfig bcIdSearch = new BoardConfig();
		bcIdSearch.setBcId(bcId);
		bcIdSearch.setSitePrefix(currentSite.getSitePrefix());
		BoardConfig boardConfig = boardService.getBoardConfig(bcIdSearch);
		if( boardConfig == null ){
			throw new AsaproNotFoundException("존재하지 않는 게시판입니다.");
		}
		
		BoardArticle boardArticle = null;
		if( baId != null && baId > 0 ){
			BoardArticle baIdSearch = new BoardArticle();
			baIdSearch.setSitePrefix(currentSite.getSitePrefix());
			baIdSearch.setBaId(baId);
			boardArticle = boardService.getBoardArticle(baIdSearch);
			if( boardArticle == null ){
				throw new AsaproNotFoundException("존재하지 않는 게시물입니다.");
			}else{
				//게시물 객체에 현제 접속자 정보를 셋트한다
				//Member 객체내 셋트 되도록 변경
				/*
				if(boardArticle.getAdminMember() != null){
					writeMember.setMemberFromAdmin(boardArticle.getAdminMember());
				}else if(boardArticle.getUserMember() != null){
					writeMember.setMemberFromUser(boardArticle.getUserMember());
				}
				boardArticle.setMember(writeMember);
				*/
			}
		}
		
		String temp[] = requestUri.split("/");
		
		//Member currentUser = (Member) request.getSession().getAttribute("currentUser");
		//BoardConfig boardConfig = (BoardConfig) modelAndView.getModel().get("boardConfig");
		//if( currentUser != null ){
			//modelAndView.getModelMap().addAttribute("isBoardManager", new Boolean(boardService.isBoardManager(boardConfig, currentUser)));
		boolean isBoardManager = new Boolean(boardService.isBoardManager(boardConfig, currentMember));
		//컨트롤러나 jsp 에서 사용하려고
		request.setAttribute("isBoardManager", isBoardManager);
		//}
		
		if( isBoardManager ){
			LOGGER.info("[ASAPRO] BoardInterceptor isBoardManager ? YES");
			return true;
		} else {

			//그룹 전용으로 지정되어 있으면 우선 체크한다.
			if( StringUtils.isNotBlank(boardConfig.getBcGroup()) ){
//				GroupMember groupMember = new GroupMember();
//				groupMember.setGroupId(boardConfig.getBcGroup());
//				groupMember.setMemberId(currentMember.getMemberId());
//				if( groupService.isGroupMember(groupMember) ) {
//					return true;
//				} else {
//					throw new AsaproNoCapabilityException("사용 권한이 없습니다.", "back");
//				}
			}
			
			//비회원인 경우
			if( "ROLE_GUEST".equals(currentMember.getMemberRole().getRoleCode()) ){
				//jsp용
				if( boardConfig.isBcAllowGuestList() ){
					request.setAttribute("bcAllowList", Boolean.TRUE);
				}
				if( boardConfig.isBcAllowGuestForm() ){
					request.setAttribute("bcAllowForm", Boolean.TRUE);
					//jsp 에서 글수정 버튼 숨기는 용도
					if( boardArticle != null && boardArticle.isGuestArticle() ){
						request.setAttribute("bcAllowFormEdit", Boolean.TRUE);
						request.setAttribute("bcAllowFormDelete", Boolean.TRUE);
					}
				}
				if( boardConfig.isBcAllowGuestView() ){
					request.setAttribute("bcAllowView", Boolean.TRUE);
				}
				
				//목록
				if( (requestUri.endsWith("/board/" + bcId) || requestUri.endsWith("/list")) && boardConfig.isBcAllowGuestList() ){
					return true;
				}
				//글쓰기
				else if( requestUri.endsWith("/new") && boardConfig.isBcAllowGuestForm() ){
					return true;
				}
				//글수정
				else if( requestUri.endsWith("/edit") && boardConfig.isBcAllowGuestForm() ){
					if( boardArticle != null && boardArticle.isGuestArticle() ){
						//BoardUserController updateBoardArticlePasswordPost 메서드에서 값 집어 넣는다.
						Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request); 
						String flashMapAuthorKey = "board.article." + baId + ".author";
						if("GET".equals(request.getMethod().toUpperCase()) ){
							if( flashMap != null && flashMap.get(flashMapAuthorKey) != null && (Boolean)flashMap.get(flashMapAuthorKey) ) {
								return true;
							} else {
								response.sendRedirect( requestUri + "/password" );
								return false;
							}
						}else{
							//method post 인경우 본인확인 비밀번호를 재확인. 컨트롤러에서 확인처리
							return true;
						}
					} else {
						//게시물의 작성아이디가 회원인 경우에 해당함
						throw new AsaproNoCapabilityException("본인이 작성한 글만 수정이 가능합니다.", "back");
					}
				}
				//글삭제
				else if( requestUri.endsWith("/delete/password") && boardConfig.isBcAllowGuestForm() ){
					if( boardArticle.isGuestArticle() ){
						return true;
						//}
					} else {
						//게시물의 작성아이디가 회원인 경우에 해당함
						throw new AsaproNoCapabilityException("본인이 작성한 글만 삭제가 가능합니다.", "back");
					}
				}
				//글보기
				else if( StringUtils.isNumeric(temp[temp.length - 1]) && boardConfig.isBcAllowGuestView() ){
					if( boardArticle.getBaSecret() != null && boardArticle.getBaSecret() ){
						//BoardUserController updateBoardArticlePasswordPost 메서드에서 값 집어 넣는다.
						Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request); 
						String flashMapSecretKey = "board.article." + baId + ".secret";
						if( flashMap != null && flashMap.get(flashMapSecretKey) != null && (Boolean)flashMap.get(flashMapSecretKey) ){
							return true;
						} else {
							response.sendRedirect( requestUri + "/secret/password" );
							return false;
						}
					}
					return true;
				}
				//비밀번호폼
				else if( requestUri.endsWith("/edit/password") ){
					if( boardArticle.isGuestArticle() ){
						return true;
					} else {
						throw new AsaproNoCapabilityException("유효하지 않은 접근입니다.", "back");
					}
				}
				else if( requestUri.endsWith("/secret/password") ){
					if( boardArticle.getBaSecret() != null && boardArticle.getBaSecret() ){
						return true;
					} else {
						throw new AsaproNoCapabilityException("유효하지 않은 접근입니다.", "back");
					}
				}
				//댓글
				//else if( requestUri.contains("/comment") && boardConfig.isBcAllowGuestComment() ){
				//	request.setAttribute("bcAllowComment", Boolean.TRUE);
				//	return true;
				//} 
				else if( requestUri.endsWith("/api/recommend") ){
					return true;
				}
				
				throw new AsaproNoCapabilityException("로그인후 열람하실 수 있습니다.", "/site/main/login");
			}
			//회원인경우
			else {
				//jsp용
				if( boardConfig.isBcAllowMemberList() ){
					request.setAttribute("bcAllowList", Boolean.TRUE);
				}
				if( boardConfig.isBcAllowMemberForm() ){
					request.setAttribute("bcAllowForm", Boolean.TRUE);
					if( boardArticle != null 
							//&& boardArticle.getMember() != null 
							//&& currentMember.getMemberId().equals(boardArticle.getMember().getMemberId()) ){
							&& currentMember.getMemberId().equals(boardArticle.getMemId()) ){
						request.setAttribute("bcAllowFormEdit", Boolean.TRUE);
						request.setAttribute("bcAllowFormDelete", Boolean.TRUE);
					}
				}
				if( boardConfig.isBcAllowMemberView() ){
					request.setAttribute("bcAllowView", Boolean.TRUE);
				}
				
				//목록
				if( (requestUri.endsWith("/list") || requestUri.endsWith("/board/" + bcId)) && boardConfig.isBcAllowMemberList() ){
					return true;
				}
				//글쓰기
				else if( requestUri.endsWith("/new") && boardConfig.isBcAllowMemberForm() ){
					return true;
				}
				//글수정
				else if( requestUri.endsWith("/edit") && boardConfig.isBcAllowMemberForm() ){
					if( boardArticle != null 
							//&& boardArticle.getMember() != null 
							//&& currentMember.getMemberId().equals(boardArticle.getMember().getMemberId()) ){
							&& currentMember.getMemberId().equals(boardArticle.getMemId()) ){
						return true;
					} else {
						throw new AsaproNoCapabilityException("본인이 작성한 글만 수정할 수 있습니다.", "back");
					}
				}
				//글삭제
				else if( requestUri.endsWith("/delete/password") && boardConfig.isBcAllowMemberForm() ){
					if( boardArticle != null 
							//&& boardArticle.getMember() != null 
							//&& currentMember.getMemberId().equals(boardArticle.getMember().getMemberId()) ){
							&& currentMember.getMemberId().equals(boardArticle.getMemId()) ){
						return true;
					} else {
						throw new AsaproNoCapabilityException("본인이 작성한 글만 삭제할 수 있습니다.", "back");
					}
				}
				//글삭제
				else if( requestUri.endsWith("/delete/byuser") && boardConfig.isBcAllowMemberForm() ){
					if( boardArticle != null 
							//&& boardArticle.getMember() != null 
							//&& currentMember.getMemberId().equals(boardArticle.getMember().getMemberId()) ){
							&& currentMember.getMemberId().equals(boardArticle.getMemId()) ){
						return true;
					} else {
						throw new AsaproNoCapabilityException("본인이 작성한 글만 삭제할 수 있습니다.", "back");
					}
				}
				//글보기
				else if( StringUtils.isNumeric(temp[temp.length - 1]) && boardConfig.isBcAllowMemberView() ){
					if( boardArticle.getBaSecret() != null && boardArticle.getBaSecret() ){
						if( //boardArticle.getMember() != null 
								//&& currentMember.getMemberId().equals(boardArticle.getMember().getMemberId())
								currentMember.getMemberId().equals(boardArticle.getMemId()) ){
							return true;
						} else {
							Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request); 
							String flashMapSecretKey = "board.article." + baId + ".secret";
							if( flashMap != null && flashMap.get(flashMapSecretKey) != null && (Boolean)flashMap.get(flashMapSecretKey) ){
								return true;
							} else {
								response.sendRedirect( requestUri + "/secret/password" );
								return false;
							}
						}
					} else {
						return true;
					}
				}
				//비밀번호폼
				/*else if( requestUri.endsWith("/edit/password") ){
					if( boardArticle != null 
							&& boardArticle.getMember() != null 
							&& .currentMember().equals(boardArticle.getMember().getMemberId()) ){
						return true;currentUser
					} else {
						throw new AsaproNoCapabilityException("유효하지 않은 접근입니다.");
					}
				}*/
				else if( requestUri.endsWith("/secret/password") ){
					if( boardArticle.getBaSecret() != null && boardArticle.getBaSecret() ){
						return true;
					} else {
						throw new AsaproNoCapabilityException("유효하지 않은 접근입니다.", "back");
					}
				}else if( requestUri.endsWith("/api/recommend") ){
					return true;
				}
			}
			
		}
		throw new AsaproNoCapabilityException("사용 권한이 없습니다.", "back");
		//return false;
	}
	
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		
		//GET - POST - GET 패턴의 마지막 GET에 해당하는 redirect인 경우는 체크하지 않는다.
		//실제로 GET 을 핸들링하는게 아니라 스프링 리다이렉트 프리픽스를 붙이고 나오는 경우 경우 처리하지 않는다로 이해하는게 나음
		//if( !modelAndView.getViewName().startsWith("redirect:") ){
			
			//Member currentUser = (Member) request.getSession().getAttribute("currentUser");
			//BoardConfig boardConfig = (BoardConfig) modelAndView.getModel().get("boardConfig");
			//if( currentUser != null ){
				//modelAndView.getModelMap().addAttribute("isBoardManager", new Boolean(boardService.isBoardManager(boardConfig, currentUser)));
			//	request.setAttribute("isBoardManager", new Boolean(boardService.isBoardManager(boardConfig, currentUser)));
			//}
		//}
		
	}
	
}
