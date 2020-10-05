/**
 * 
 */
package egovframework.com.asapro.content.web;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import egovframework.com.asapro.code.service.Code;
import egovframework.com.asapro.config.service.Config;
import egovframework.com.asapro.config.service.ConfigService;
import egovframework.com.asapro.content.service.Content;
import egovframework.com.asapro.content.service.ContentSearch;
import egovframework.com.asapro.content.service.ContentService;
import egovframework.com.asapro.member.admin.service.AdminMember;
import egovframework.com.asapro.menu.service.Menu;
import egovframework.com.asapro.menu.service.MenuContentRelation;
import egovframework.com.asapro.menu.service.MenuService;
import egovframework.com.asapro.site.service.Site;
import egovframework.com.asapro.support.Constant;
import egovframework.com.asapro.support.ServerMessage;
import egovframework.com.asapro.support.annotation.CurrentAdmin;
import egovframework.com.asapro.support.annotation.CurrentSite;
import egovframework.com.asapro.support.pagination.Paging;
import egovframework.com.asapro.support.util.AsaproUtils;
import egovframework.com.asapro.support.util.EtcUtil;
import name.fraser.neil.plaintext.diff_match_patch;
import name.fraser.neil.plaintext.diff_match_patch.Diff;


/**
 * 콘텐츠 관리자 컨트롤러
 * @author yckim
 * @since 2018. 7. 20.
 *
 */
@Controller
public class ContentAdminController {
	
	@Autowired
	private ContentService contentService;
	
	@Autowired
	private MenuService menuService;
	
	@Autowired
	private ConfigService configService;
	
	@Autowired
	private ContentValidator contentValidator;
	
	
	/**
	 * 콘텐츠 목록을 반환한다.(published 만)
	 * @param model
	 * @param currentSite
	 * @param contentSearch
	 * @return 콘텐츠 목록
	 */
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/content/list.do", method=RequestMethod.GET)
	public String contentListGet(Model model, @CurrentSite Site currentSite, @ModelAttribute("contentSearch") ContentSearch contentSearch){
		contentSearch.setSitePrefix(currentSite.getSitePrefix());
		contentSearch.fixBrokenSvByDefaultCharsets();
		contentSearch.setContentStatus("publish");//최신버전만
		contentSearch.setPaging(true);
		
		List<Content> list = contentService.getContentList(contentSearch);
		int total = contentService.getContentListTotal(contentSearch);
		
		
		Paging paging = new Paging(list, total, contentSearch);
		model.addAttribute("paging", paging);
		
		return "asapro/admin/content/list";
	}
	
	/**
	 * 콘텐츠 목록을 레이어 팝업창 뷰를 반환한다.
	 * @param model
	 * @param currentSite
	 * @param contentSearch
	 * @return 콘텐츠 목록을 레이어 팝업창 뷰
	 */
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/content/searchLayer.do", method=RequestMethod.GET)
	public String contentListSearchLayerGet(Model model, @CurrentSite Site currentSite, @ModelAttribute("contentSearch") ContentSearch contentSearch){
		contentSearch.setSitePrefix(currentSite.getSitePrefix());
		contentSearch.fixBrokenSvByDefaultCharsets();
		contentSearch.setContentStatus("publish");//최신버전만
		contentSearch.setPaging(true);
		
		List<Content> list = contentService.getContentList(contentSearch);
		int total = contentService.getContentListTotal(contentSearch);
		
		
		Paging paging = new Paging(list, total, contentSearch);
		model.addAttribute("paging", paging);
		
		return "asapro/admin/content/searchLayer";
	}
	
	/**
	 * 콘텐츠 등록 폼 뷰를 반환한다.
	 * @param model
	 * @param currentSite
	 * @param contentForm
	 * @return 추가폼뷰
	 */
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/content/insert.do", method=RequestMethod.GET)
	public String insertContentGet(Model model, @CurrentSite Site currentSite, @ModelAttribute("contentForm") Content contentForm) {
		
		model.addAttribute("formMode", "INSERT");
		return "asapro/admin/content/form";
	}
	
	/**
	 * 콘텐츠를 등록한다..
	 * @param model
	 * @param redirectAttributes
	 * @param currentSite
	 * @param currentAdmin
	 * @param contentForm
	 * @param bindingResult
	 * @return 등록결과뷰
	 */
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/content/insert.do", method=RequestMethod.POST)
	public String insertContentPost(Model model, RedirectAttributes redirectAttributes, @CurrentSite Site currentSite, @CurrentAdmin AdminMember currentAdmin, @ModelAttribute("contentForm") Content contentForm, BindingResult bindingResult){
		contentForm.setSitePrefix(currentSite.getSitePrefix());
		
		contentValidator.validate(contentForm, bindingResult, "INSERT");
		
		if( bindingResult.hasErrors() ){
			model.addAttribute("formMode", "INSERT");
			return "asapro/admin/content/form";
		} else {
			contentForm.setContentLastWorker(currentAdmin);
			contentForm.setContentLastModified(new Date());
			contentForm.setContentRegDate(new Date());
			String plain = Jsoup.clean(contentForm.getContent(), Whitelist.none());
			
			//utf-8 캐릭터 셋 환경을 고려, PreparedStatement 사용고려
			if(StringUtils.isNotBlank(plain) ){
				//문자열을 바이트로 변환
				byte[] plainByte = plain.getBytes();
				//oracle 에서 한글은 utf-8일 경우 3바이트 이므로 4000마이트를 자를 수 없다
				//java 에서는 한글을 2바이트로 계산하기때문에 어쩔 수 없이 일괄적으로 한글 최대 바이트로 자름
				//한글을 체크해서 한글만 3바이트로 계산해서 할 수도 있지만 그정까지 정확히 잘라야 할 필요를 못느낌
				if(plainByte.length > 1340){
					plain = new String(plainByte, 0, 1340);
				}
			}
//			if( plain.length() >= 660 ){
//				plain = plain.substring(0, 660);
//			}
			
			contentForm.setContentPlain( plain );
			contentForm.setContentVer(0);
			contentForm.setContentStatus("publish");
			
			int result = contentService.insertContent(contentForm);
			redirectAttributes.addFlashAttribute("inserted", true);
			return "redirect:" + AsaproUtils.getAdminPath(currentSite, true) + "/content/list.do";
		}
	}
	
	/**
	 * 콘텐츠 수정폼 뷰를 반환한다.
	 * @param model
	 * @param currentSite
	 * @param contentForm
	 * @return 콘텐츠 수정 폼 뷰
	 */
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/content/update.do", method=RequestMethod.GET)
	public String updateContentGet(Model model, @CurrentSite Site currentSite, @ModelAttribute("contentForm") Content contentForm, @ModelAttribute("revisionListSearch") ContentSearch revisionListSearch){
		//설정정보
		Config siteConfig = configService.getConfig("site");
		
		Content content = null;
		//콘텐츠 아이디가 있으면
		if(contentForm.getContentId() != null && contentForm.getContentId() > 0 ){
			content = contentService.getContent(contentForm);
		}
		//콘텐츠 아이디가 없으면 메뉴에서 콘텐츠편집으로 바로 들어온거
		else{
			//메뉴 아이디 있으면
			if(contentForm.getMenu() != null && StringUtils.isNotBlank(contentForm.getMenu().getMenuId()) ){
				MenuContentRelation menuContentRelation = new MenuContentRelation();
				menuContentRelation.setSitePrefix(currentSite.getSitePrefix());
				menuContentRelation.setMenuId(contentForm.getMenu().getMenuId());
				content = contentService.getContentByRel(menuContentRelation);
				
				//메뉴에서 콘텐츠 편집이므로 메뉴 화면목록으로 돌아가기 위해 jsp에서 사용
				model.addAttribute("menuId", contentForm.getMenu().getMenuId());
			}
		}
		
		model.addAttribute("contentForm", content);
		model.addAttribute("currentContentId", content.getContentId());
		
		model.addAttribute("formMode", "UPDATE");
		
		//===== 리비전 정보 =====
		//ContentSearch revisionListSearch = new ContentSearch();
		revisionListSearch.setContentRoot(content.getContentRoot());
		if(contentForm.getMenu() != null && StringUtils.isNotBlank(contentForm.getMenu().getMenuId()) ){
			revisionListSearch.getMenu().setMenuId(contentForm.getMenu().getMenuId());
		}
		//contentSearch.setContentStatus("revision");	//리비전 목록만
		revisionListSearch.setSortOrder("CONTENT_VER");
		revisionListSearch.setSortDirection("DESC");
		//revisionListSearch.setCp(1);
		
		List<Content> revisionList = contentService.getContentList(revisionListSearch);
		int revisionTotal = contentService.getContentListTotal(revisionListSearch);
		Paging paging = new Paging(revisionList, revisionTotal, revisionListSearch);
				
		model.addAttribute("paging", paging);
		model.addAttribute("useContenRevision", siteConfig.getOption("use_content_revision"));

		//===== //리비전 정보 =====
		
		return "asapro/admin/content/form";
	}
	
	/**
	 * 콘텐츠를 수정하고 수정결과뷰를 반환한다.
	 * @param model
	 * @param redirectAttributes
	 * @param currentSite
	 * @param currentAdmin
	 * @param contentForm
	 * @param bindingResult
	 * @return 수정결과뷰
	 */
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/content/update.do", method=RequestMethod.POST)
	public String updateContentPost(Model model, RedirectAttributes redirectAttributes, @CurrentSite Site currentSite, @CurrentAdmin AdminMember currentAdmin, @ModelAttribute("contentForm") Content contentForm, BindingResult bindingResult){
		contentForm.setSitePrefix(currentSite.getSitePrefix());
		Config siteConfig = configService.getConfig("site");
		
		contentValidator.validate(contentForm, bindingResult, "UPDATE");
		
		if( bindingResult.hasErrors() ){
			Content content = contentService.getContent(contentForm);
			
			ContentSearch revisionListSearch = new ContentSearch();
			revisionListSearch.setContentRoot(content.getContentRoot());
			revisionListSearch.getMenu().setMenuId(contentForm.getMenu().getMenuId());
			//contentSearch.setContentStatus("revision");	//리비전 목록만
			revisionListSearch.setSortOrder("CONTENT_VER");
			revisionListSearch.setSortDirection("DESC");
			//revisionListSearch.setCp(1);
			
			int revisionTotal = contentService.getContentListTotal(revisionListSearch);
			List<Content> revisionList = contentService.getContentList(revisionListSearch);
			Paging paging = new Paging(revisionList, revisionTotal, revisionListSearch);
					
			model.addAttribute("paging", paging);
			model.addAttribute("useContenRevision", siteConfig.getOption("use_content_revision"));
			model.addAttribute("formMode", "UPDATE");
			
			return "asapro/admin/content/form";
		} else {
			contentForm.setContentLastWorker(currentAdmin);
			contentForm.setContentLastModified(new Date());
			String plain = Jsoup.clean(contentForm.getContent(), Whitelist.none());
			
			//utf-8 캐릭터 셋 환경을 고려, PreparedStatement 사용고려
			if(StringUtils.isNotBlank(plain) ){
				//문자열을 바이트로 변환
				byte[] plainByte = plain.getBytes();
				//oracle 에서 한글은 utf-8일 경우 3바이트 이므로 4000마이트를 자를 수 없다
				//java 에서는 한글을 2바이트로 계산하기때문에 어쩔 수 없이 일괄적으로 한글 최대 바이트로 자름
				//한글을 체크해서 한글만 3바이트로 계산해서 할 수도 있지만 그정까지 정확히 잘라야 할 필요를 못느낌
				if(plainByte.length > 1340){
					plain = new String(plainByte, 0, 1340);
				}
			}
//			if( plain.length() >= 660 ){
//				plain = plain.substring(0, 660);
//			}
			
			contentForm.setContentPlain( plain );
			int result = contentService.updateContent(contentForm);
			if( result == 99 ){
				//안바뀜
				redirectAttributes.addFlashAttribute("noChange", true);
				return "redirect:" + AsaproUtils.getAdminPath(currentSite, true) + "/content/update.do?contentId=" + contentForm.getContentId();
			}
			redirectAttributes.addFlashAttribute("updated", true);
			return "redirect:" + AsaproUtils.getAdminPath(currentSite, true) + "/content/update.do?contentId=" + contentForm.getContentId();
		}
	}
	
	/**
	 * 현재 콘텐츠를 과거 버전과 비교하는 뷰를 반환한다.
	 * @param model
	 * @param currentSite
	 * @param currentAdmin
	 * @param compareForm
	 * @param cp
	 * @param contentVer
	 * @return 콘텐츠 비교 뷰
	 */
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/content/revision/compare.do", method=RequestMethod.GET)
	public String compareContentGet(Model model, @CurrentSite Site currentSite, @CurrentAdmin AdminMember currentAdmin, @ModelAttribute("compareForm") Content compareForm, @ModelAttribute("revisionListSearch") ContentSearch revisionListSearch){
		
		Config siteConfig = configService.getConfig("site");
		
		compareForm.setContentStatus("publish");
		Content currentContent = contentService.getContent(compareForm);
		model.addAttribute("currentContent", currentContent);
		
		Content revisionContentSearch = new Content();
		revisionContentSearch.setContentRoot(compareForm.getContentRoot());
		revisionContentSearch.setContentStatus("revision");
		revisionContentSearch.setContentVer(compareForm.getContentVer());
		Content revisionContent = contentService.getContent(revisionContentSearch);
		model.addAttribute("revisionContent", revisionContent);

		model.addAttribute("currentContentId", currentContent.getContentId());
		
		//===== 콘텐츠 비교 =====
		diff_match_patch currentDmp = new diff_match_patch();
		LinkedList<Diff> currentDiffs = currentDmp.diff_main(revisionContent.getContent(), currentContent.getContent(), false);
		//dmp.diff_cleanupSemantic(currentDiffs);
		model.addAttribute("currentDiffs", currentDiffs);
		model.addAttribute("currentDiffPrettyHtml", currentDmp.diff_prettyHtml(currentDiffs));
		
		diff_match_patch revisionDmp = new diff_match_patch();
		LinkedList<Diff> revisionDiffs = revisionDmp.diff_main(currentContent.getContent(), revisionContent.getContent(), false);
		//dmp.diff_cleanupSemantic(revisionDiffs);
		model.addAttribute("revisionDiffs", revisionDiffs);
		model.addAttribute("revisionDiffPrettyHtml", revisionDmp.diff_prettyHtml(revisionDiffs));
		//===== //콘텐츠 비교 =====
		
		//===== 리비전 정보 =====
		//ContentSearch revisionListSearch = new ContentSearch();
		revisionListSearch.setContentRoot(compareForm.getContentRoot());
		//contentSearch.setContentStatus("revision");	//리비전 목록만
		revisionListSearch.setSortOrder("CONTENT_VER");
		revisionListSearch.setSortDirection("DESC");
		//revisionListSearch.setCp(cp);
		
		int revisionTotal = contentService.getContentListTotal(revisionListSearch);
		List<Content> revisionList = contentService.getContentList(revisionListSearch);
		Paging paging = new Paging(revisionList, revisionTotal, revisionListSearch);
				
		model.addAttribute("paging", paging);
		model.addAttribute("useContenRevision", siteConfig.getOption("use_content_revision"));
		//===== //리비전 정보 =====
				
		return "asapro/admin/content/compare";
	}
	/**
	 * 콘텐츠 내용을 과거버전으로 복구한다.
	 * @param model
	 * @param redirectAttributes
	 * @param currentSite
	 * @param currentAdmin
	 * @param contentRestoreForm
	 * @return 복구결과 뷰
	 */
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/content/revision/restore.do", method=RequestMethod.POST)
	public String restoreContentPost(Model model, RedirectAttributes redirectAttributes, @CurrentSite Site currentSite, @CurrentAdmin AdminMember currentAdmin, @ModelAttribute("contentRestoreForm") Content contentRestoreForm){
		contentRestoreForm.setSitePrefix(currentSite.getSitePrefix());
		contentRestoreForm.setContentLastWorker(currentAdmin);
		contentService.restoreContent(contentRestoreForm);
		redirectAttributes.addFlashAttribute("restored", true);
		return "redirect:" + AsaproUtils.getAdminPath(currentSite, true) + "/content/update.do?contentId=" + contentRestoreForm.getContentId();
	}
	
	/**
	 * 콘텐츠를 삭제한다.
	 * @param model
	 * @param currentSite
	 * @param contentIds
	 * @param contentRoots
	 * @return 삭제결과 json
	 */
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/content/delete.do", method=RequestMethod.POST)
	@ResponseBody
	public ServerMessage deleteContentPost(Model model, @CurrentSite Site currentSite, @RequestParam(value="contentIds[]", required=false) Integer[] contentIds, @RequestParam(value="contentRoots[]", required=false) Integer[] contentRoots){
		ServerMessage serverMessage = new ServerMessage();
		
		if( ArrayUtils.isEmpty(contentIds) && ArrayUtils.isEmpty(contentRoots) ){
			serverMessage.setSuccess(false);
			serverMessage.setText("삭제할 항목이 없습니다.");
		} else {
			List<Content> contentList = new ArrayList<Content>();
			Content content = null;
			int result = 0;
			
			if(ArrayUtils.isNotEmpty(contentIds) ){
				for( Integer contentId : contentIds ){
					content = new Content();
					content.setContentId(contentId);
					contentList.add(content);
				}
			}
			if(ArrayUtils.isNotEmpty(contentRoots) ){
				for( Integer contentRoot : contentRoots ){
					content = new Content();
					content.setContentRoot(contentRoot);
					contentList.add(content);
				}
			}	
			result = contentService.deleteContents(contentList);
			
			if(result > 0){
				serverMessage.setSuccess(true);
				serverMessage.setSuccessCnt(result);
			} else {
				serverMessage.setSuccess(false);
				serverMessage.setText("삭제하지 못하였습니다.");
			}
		}
		return serverMessage;
	}
}
