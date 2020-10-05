/**
 * 
 */
package egovframework.com.asapro.poll.web;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.FlashMap;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.ibm.icu.text.SimpleDateFormat;

import egovframework.com.asapro.poll.service.Poll;
import egovframework.com.asapro.poll.service.PollSearch;
import egovframework.com.asapro.poll.service.PollService;
import egovframework.com.asapro.code.service.Code;
import egovframework.com.asapro.code.service.CodeService;
import egovframework.com.asapro.fileinfo.service.FileInfo;
import egovframework.com.asapro.fileinfo.service.FileInfoService;
import egovframework.com.asapro.fileinfo.service.FileInfoUploadResult;
import egovframework.com.asapro.site.service.Site;
import egovframework.com.asapro.support.Constant;
import egovframework.com.asapro.support.ServerMessage;
import egovframework.com.asapro.support.annotation.CurrentSite;
import egovframework.com.asapro.support.exception.AsaproException;
import egovframework.com.asapro.support.pagination.Paging;
import egovframework.com.asapro.support.util.AsaproUtils;
import egovframework.com.asapro.support.util.EtcUtil;

/**
 * 투표 관리자 컨트롤러
 * @author yckim
 * @since 2020. 1. 21.
 *
 */
@Controller
public class PollAdminController {
	
	@Autowired
	private PollService pollService;
	
	@Autowired
	private PollValidator pollValidator;
	
	@Autowired
	private HttpServletRequest request;
	
	@Autowired
	private FileInfoService fileInfoService;
	
	@Autowired
	private CodeService codeService;
	
	/**
	 * 투표 목록을 반환한다.
	 * @param model
	 * @param pollSearch
	 * @return 질문목록
	 */
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/poll/list.do", method=RequestMethod.GET)
	public String pollListGet(Model model, @CurrentSite Site currentSite, @ModelAttribute("pollSearch") PollSearch pollSearch){
		pollSearch.setSitePrefix(currentSite.getSitePrefix());
		pollSearch.fixBrokenSvByDefaultCharsets();
		pollSearch.setPaging(true);
		
		List<Poll> list = pollService.getPollList(pollSearch);
		int total = pollService.getPollListTotal(pollSearch);
		Paging paging = new Paging(list, total, pollSearch);
		model.addAttribute("paging", paging);
		
		return "asapro/admin/poll/list";
	}
	
	/**
	 * 투표 추가폼 뷰를 반환한다.
	 * @param model
	 * @param pollForm
	 * @return 폼뷰
	 */
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/poll/insert.do", method=RequestMethod.GET)
	public String pollInsertGet(Model model, @CurrentSite Site currentSite, @ModelAttribute("pollForm") Poll pollForm){
		model.addAttribute("formMode", "INSERT");
		
		List<Code> pollTypeCodeList = codeService.getCodeList("pollType");
		model.addAttribute("pollTypeCodeList", pollTypeCodeList );
		return "asapro/admin/poll/form";
	}
	
	
	/**
	 * 투표을 추가한다.
	 * @param model
	 * @param pollForm
	 * @param bindingResult
	 * @return 추가결과뷰
	 * @throws IOException 
	 * @throws AsaproException 
	 */
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/poll/insert.do", method=RequestMethod.POST)
	public String pollInsertPost(Model model, RedirectAttributes redirectAttributes, @CurrentSite Site currentSite, @ModelAttribute("pollForm") Poll pollForm, BindingResult bindingResult) throws AsaproException, IOException{
		pollForm.setSitePrefix(currentSite.getSitePrefix());
		pollValidator.validate(pollForm, bindingResult, "INSERT");
		
		if( bindingResult.hasErrors() ){
			model.addAttribute("formMode", "INSERT");
			
			List<Code> pollTypeCodeList = codeService.getCodeList("pollType");
			model.addAttribute("pollTypeCodeList", pollTypeCodeList );
			return "asapro/admin/poll/form";
		} else {

			FileInfoUploadResult thumbFileInfoUploadResult = pollService.insertPoll(pollForm);
			
			//업로드 에러 있으면 등록화면으로 전환
			if(thumbFileInfoUploadResult.hasErrors() ){
				
				model.addAttribute("thumbFileInfoUploadResult", thumbFileInfoUploadResult);
				model.addAttribute("formMode", "INSERT");
				return "asapro/admin/poll/form";
			}
			
			redirectAttributes.addFlashAttribute("inserted", true);
			
			return "redirect:" + AsaproUtils.getAdminPath(currentSite, true) + "/poll/list.do?poType=" + pollForm.getPoType();
		}
	}

	/**
	 * 투표 수정폼 뷰를 반환한다.
	 * @param model
	 * @param pollForm
	 * @return
	 */
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/poll/update.do", method=RequestMethod.GET)
	public String pollUpdateGet(Model model, @CurrentSite Site currentSite, @ModelAttribute("pollForm") Poll pollForm){
		pollForm.setSitePrefix(currentSite.getSitePrefix());
		model.addAttribute("formMode", "UPDATE");
		model.addAttribute("pollForm", pollService.getPoll(pollForm));
		
		List<Code> pollTypeCodeList = codeService.getCodeList("pollType");
		model.addAttribute("pollTypeCodeList", pollTypeCodeList );
		return "asapro/admin/poll/form";
	}
	
	
	/**
	 * 투표을 수정하고 목록 뷰를 반환한다.
	 * @param model
	 * @param pollForm
	 * @param bindingResult
	 * @return
	 * @throws IOException 
	 * @throws AsaproException 
	 */
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/poll/update.do", method=RequestMethod.POST)
	public String pollUpdatePost(Model model, RedirectAttributes redirectAttributes, @CurrentSite Site currentSite, @ModelAttribute("pollForm") Poll pollForm, BindingResult bindingResult) throws AsaproException, IOException{
		pollForm.setSitePrefix(currentSite.getSitePrefix());
		pollValidator.validate(pollForm, bindingResult, "UPDATE");
		if( bindingResult.hasErrors() ){
			model.addAttribute("formMode", "UPDATE");
			
			List<Code> pollTypeCodeList = codeService.getCodeList("pollType");
			model.addAttribute("pollTypeCodeList", pollTypeCodeList );
			return "asapro/admin/poll/form";
		} else {
			
			FileInfoUploadResult thumbFileInfoUploadResult = pollService.updatePoll(pollForm);
			//업로드 에러 있으면 바로 수정화면으로 전환
			if(thumbFileInfoUploadResult.hasErrors() ){
				
				model.addAttribute("thumbFileInfoUploadResult", thumbFileInfoUploadResult);
				model.addAttribute("formMode", "UPDATE");
				return "asapro/admin/poll/form";
			}
			
			redirectAttributes.addFlashAttribute("updated", true);
			
			return "redirect:" + AsaproUtils.getAdminPath(currentSite, true) + "/poll/update.do?poType=" + pollForm.getPoType() + "&poId=" + pollForm.getPoId();
		}
	}
	
		
	/**
	 * 투표을 삭제한다.
	 * @param model
	 * @param poIds
	 * @return 삭제결과
	 * @throws FileNotFoundException 
	 */
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/poll/delete.do", method=RequestMethod.POST)
	@ResponseBody
	public ServerMessage deletePollPost(Model model, @CurrentSite Site currentSite, @RequestParam(value="poIds[]", required=true) Integer[] poIds) throws FileNotFoundException{
		ServerMessage serverMessage = new ServerMessage();
		
		if( ArrayUtils.isEmpty(poIds) ){
			serverMessage.setSuccess(false);
			serverMessage.setText("삭제할 항목이 없습니다.");
		} else {
			List<Poll> pollList = new ArrayList<Poll>();
			Poll poll = null;
			for( Integer poId : poIds ){
				poll = new Poll();
				poll.setSitePrefix(currentSite.getSitePrefix());
				poll.setPoId(poId);
				pollList.add(poll);
			}
			int result = pollService.deletePoll(pollList);
			if(result > 0){
				serverMessage.setSuccess(true);
				serverMessage.setSuccessCnt(result);
			} else {
				serverMessage.setSuccess(false);
				serverMessage.setText("삭제하지 못하였습니다.[Server Error]");
			}
		}
		return serverMessage;
	}

	/*
	*//**
	 * 투표 정렬 순서를 수정한다.
	 * @param model
	 * @param bnOrders
	 * @return 수정 결과 json
	 *//*
	
	
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/poll/reOrder.do", method=RequestMethod.POST)
	@ResponseBody
	public ServerMessage reOrderPollPost(Model model, @CurrentSite Site currentSite, @RequestParam(value="bnIds[]", required=true) Integer[] bnIds){
		ServerMessage serverMessage = new ServerMessage();
		
		if(ArrayUtils.isEmpty(bnIds) ){
			serverMessage.setSuccess(false);
			serverMessage.setText("수정할 항목이 없습니다.");
		} else {
			int order = 0;
			
			List<Poll> polls = new ArrayList<Poll>();
			Poll poll = null;
			for(Integer bnId : bnIds){
				order++;
				poll = new Poll();
				poll.setSitePrefix(currentSite.getSitePrefix());
				poll.setBnId(bnId);
				poll.setBnOrder(order);
				polls.add(poll);
			}
			
			int result = pollService.updatePollOrder(polls);
			if( result > 0 ){
				serverMessage.setSuccess(true);
				serverMessage.setSuccessCnt(result);
			} else {
				serverMessage.setSuccess(false);
				serverMessage.setText("수정하지 못하였습니다.[Server Error]");
			}
		}
		return serverMessage;
	}*/
}
