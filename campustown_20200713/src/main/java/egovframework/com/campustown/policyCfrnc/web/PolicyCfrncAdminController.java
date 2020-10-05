package egovframework.com.campustown.policyCfrnc.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.FlashMap;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

import egovframework.com.asapro.code.service.CodeService;
import egovframework.com.asapro.fileinfo.service.FileInfo;
import egovframework.com.asapro.fileinfo.service.FileInfoService;
import egovframework.com.asapro.fileinfo.service.FileInfoUploadResult;
import egovframework.com.asapro.member.admin.service.AdminMember;
import egovframework.com.asapro.site.service.Site;
import egovframework.com.asapro.support.Constant;
import egovframework.com.asapro.support.ServerMessage;
import egovframework.com.asapro.support.annotation.CurrentAdmin;
import egovframework.com.asapro.support.annotation.CurrentSite;
import egovframework.com.asapro.support.exception.AsaproException;
import egovframework.com.asapro.support.pagination.Paging;
import egovframework.com.asapro.support.util.AsaproUtils;
import egovframework.com.campustown.policyCfrnc.service.PolicyCfrnc;
import egovframework.com.campustown.policyCfrnc.service.PolicyCfrncSearch;
import egovframework.com.campustown.policyCfrnc.service.PolicyCfrncService;
import egovframework.com.campustown.startHpMngr.service.StartHpMngr;

/**
 * 정책협의회 관리 관리자 컨트롤러
 * @author jaseo
 * @since 2020. 03. 18.
 */
@Controller
public class PolicyCfrncAdminController {
	
	@Autowired
	private CodeService codeService;
	
	@Autowired
	private PolicyCfrncService policyCfrncService;
	
	@Autowired
	private PolicyCfrncValidator policyCfrncValidator;
	
	@Autowired
	private FileInfoService fileInfoService;
	
	@Autowired
	private HttpServletRequest request;
	
	
	private static final Logger LOGGER = LoggerFactory.getLogger(PolicyCfrncAdminController.class);
	
	/**
	 * 코드목록 로드
	 * @param model
	 */
	private void loadCodes(Model model){
		model.addAttribute("sttusCodeList",  codeService.getCodeList("STTUS_CODE"));	// 상태 코드 [so:수용(부분수용), lo: 장기과제, re: 미수용]
		model.addAttribute("comptYnList",  	 codeService.getCodeList("COMPT_YN_CODE"));	// 완료 여부 Y: 완료, N: 검토중
		model.addAttribute("divList",  	 	 policyCfrncService.getPolicyCfrnDiv());	// 구분 (2020 8차, 2019 7차, 2019 6차, 유동값임!!! db select!)
		
		List<PolicyCfrnc> list = policyCfrncService.getPolicyCfrnDiv();				   
		//System.out.println("list.size(): " + list.size());
	}
	
	/**
	 * 구분 최대 차수 가져오기
	 */
	private int getMaxMngOrdr(){
		//int maxMngOrdr = policyCfrncService.getMaxMngOrder();
		//System.out.println("maxMngOrdr: " + maxMngOrdr);
				
		Date today = new Date();
		int nowYear = Integer.parseInt(AsaproUtils.getFormattedDate(today, "yyyy"));
		int startYear = 2020;
		//int startYear = policyCfrncService.getMinMngrYear();
		//if(startYear == 0){
		//	startYear = nowYear;
		//}
		// ex> 현재년도: 2020 => (2020-2020)+1 = 1 
		//      	   : 2021 => (2021-2020)+1 = 2
		//      	   : 2022 => (2022-2020)+1 = 3
		//										 .
		//										 .
		//										 .
		int maxMngOrdr = (nowYear-startYear)+1; 
		return maxMngOrdr;
	}
	
	/**
	 * 정책협의회 통계 목록을 조회한다.
	 * @param model
	 * @param currentSite
	 * @param currentAdmin
	 * @param policyCfrncSearch
	 * @return
	 */
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/policyCfrnc/stat.do", method=RequestMethod.GET)
	public String policyStatInfoListGet(Model model
			, @CurrentSite Site currentSite
			, @CurrentAdmin AdminMember currentAdmin
			, @ModelAttribute("policyCfrncSearch") PolicyCfrncSearch policyCfrncSearch
			) {
		
		// 코드목록 로드
		this.loadCodes(model);
		
		policyCfrncSearch.setSitePrefix(currentSite.getSitePrefix());
		policyCfrncSearch.fixBrokenSvByDefaultCharsets();
		policyCfrncSearch.setPaging(true);
		
		// 정책협의회 통계 리스트 조회
		List<PolicyCfrnc> list = policyCfrncService.getPolicyCfrnStatList(policyCfrncSearch);
		int total = policyCfrncService.getPolicyCfrnStatListTotal(policyCfrncSearch);
		
		Paging paging = new Paging(list, total, policyCfrncSearch);
		model.addAttribute("paging", paging);
		
		return "asapro/admin/campustown/policyCfrnc/policyCfrncStatList";
	}	
	/**
	 * 정책협의회 목록을 조회한다.
	 * @param model
	 * @param currentSite
	 * @param currentAdmin
	 * @param startHpMngrSearch
	 * @return
	 */
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/policyCfrnc/list.do", method=RequestMethod.GET)
	public String policyMngrInfoListGet(Model model
			, @CurrentSite Site currentSite
			, @CurrentAdmin AdminMember currentAdmin
			, @ModelAttribute("policyCfrncSearch") PolicyCfrncSearch policyCfrncSearch
			) {
		
		policyCfrncSearch.setSitePrefix(currentSite.getSitePrefix());
		policyCfrncSearch.fixBrokenSvByDefaultCharsets();
		policyCfrncSearch.setPaging(true);
		
		// 1. 로그인 권한 체크
		// 최고 관리자이거나 캠퍼스타운 관리자인 경우
		if(currentAdmin.getAdminRole().getRoleCode().equals("ROLE_SUPER_ADMIN") ||
		   currentAdmin.getAdminRole().getRoleCode().equals("ROLE_NORMAL_ADMIN") ||
		   currentAdmin.getAdminRole().getRoleCode().equals("ROLE_SCT_ADMIN") ||
		   currentAdmin.getAdminOrganization().equals("university") && currentAdmin.getAdminTeam() == null)
		{
			model.addAttribute("authYn", "Y"); // CRUD 다 가능
			model.addAttribute("authYn", "N"); // R만 가능
		}
//		System.out.println(currentAdmin.getAdminOrganization());
		
		// 2. 정책협의회 관리 리스트 조회
		List<PolicyCfrnc> list = policyCfrncService.getPolicyCfrnMngrList(policyCfrncSearch);
		int total = policyCfrncService.getPolicyCfrnMngrListTotal(policyCfrncSearch);
		
		Paging paging = new Paging(list, total, policyCfrncSearch);
		model.addAttribute("paging", paging);
		
		// 코드값 조회
		this.loadCodes(model);
		
		return "asapro/admin/campustown/policyCfrnc/policyCfrncMngrList";
	}
	
	
	/**
	 * 정책협의회 관리 추가폼 뷰를 반환한다.
	 * @param model
	 * @param currentSite
	 * @param policyCfrncForm
	 * @param currentAdmin
	 * @return
	 * @throws AsaproException
	 * @throws IOException
	 */
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/policyCfrnc/insert.do", method=RequestMethod.GET)
	public String policyMngrInsertGet(Model model, @CurrentSite Site currentSite
						, @ModelAttribute("policyCfrncForm") PolicyCfrnc policyCfrncForm
						, @CurrentAdmin AdminMember currentAdmin) throws AsaproException, IOException{
		
		model.addAttribute("formMode", "INSERT");
		
		// 코드값 조회
		this.loadCodes(model);
		
		// 구분 최대 차수 가져오기 => 프로그램 수정으로 인해 이제 필요없는 기능임.
		//int maxMngOrdr = this.getMaxMngOrdr();
		//model.addAttribute("maxMngOrdr", maxMngOrdr);
		// 관리번호 최대 차수 가져오기(insert에서는 필요없음)
		
		
		return "asapro/admin/campustown/policyCfrnc/policyCfrncMngrForm";
	}
	
	/**
	 * 정책협의회 관리 등록
	 * @param model
	 * @param currentSite
	 * @param policyCfrncForm
	 * @param currentAdmin
	 * @return
	 * @throws AsaproException
	 * @throws IOException
	 */
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/policyCfrnc/insert.do", method=RequestMethod.POST)
	public String policyMngrInsertPost(Model model, @CurrentSite Site currentSite
						, @ModelAttribute("policyCfrncForm") PolicyCfrnc policyCfrncForm
						//, @ModelAttribute("policyCfrncSearch") PolicyCfrncSearch policyCfrncSearch
						, BindingResult bindingResult
						, @CurrentAdmin AdminMember currentAdmin
						, MultipartFile poMultipartFiles
						, RedirectAttributes redirectAttributes
						) throws AsaproException, IOException{
		
		policyCfrncForm.setSitePrefix(currentSite.getSitePrefix());
		policyCfrncValidator.validate(policyCfrncForm, bindingResult, "INSERT");
		
		if(bindingResult.hasErrors() ){
			model.addAttribute("formMode", "INSERT");
			return "asapro/admin/campustown/policyCfrnc/policyCfrncMngrForm";
		} else {
			/*
			// 2020.06.29. 프로그램 기능 수정. (년도, 기수, 회수, 관리번호(자동생성 시스템 있다고 함) 사용자 수동 관리로 대체) 
			
			// 구분 최대 차수 가져오기
			int maxMngOrdr = this.getMaxMngOrdr();
			model.addAttribute("maxMngOrdr", maxMngOrdr);
			Date today = new Date();
			int nowYear = Integer.parseInt(AsaproUtils.getFormattedDate(today, "yyyy"));
			
			policyCfrncForm.setManageYear(nowYear);
			policyCfrncForm.setManageOrdr(maxMngOrdr);
			
			// 관리번호 최대 차수 가져오기
			int maxMngNoOrdr = policyCfrncService.getMaxMngNoOrder(policyCfrncForm);
			if(maxMngNoOrdr != 0 ){
				policyCfrncForm.setManageNoOrdr(maxMngNoOrdr+1);
			}else{
				policyCfrncForm.setManageNoOrdr(1);
			}
			
			int manageOrdr = maxMngOrdr;

			policyCfrncForm.setManageNo(policyCfrncForm.getManageYear()+"-"+String.format("%03d", manageOrdr)+"-"+policyCfrncForm.getManageNoOrdr());
			policyCfrncForm.setRegId(currentAdmin.getAdminId());
			policyCfrncForm.setRegNm(currentAdmin.getAdminName());
			*/
			
			
			Map<String, FileInfoUploadResult> fileInfoUploadResultMap = policyCfrncService.insertpolicyCfrncMngr(policyCfrncForm);
			
			//System.out.println("mngSeq::: " + policyCfrncForm.getMngSeq());
			
			
			//업로드 에러 있으면 바로 수정화면으로 전환 - 에러난 파일 제외 글과 다른 파일들은 저장된 상태임.
			if(fileInfoUploadResultMap.get("fileInfoUploadResult").hasErrors() ){
				FlashMap flashMap = RequestContextUtils.getOutputFlashMap(request);
				
				if(fileInfoUploadResultMap.get("fileInfoUploadResult").hasErrors() ){
					flashMap.put("fileInfoUploadResult", fileInfoUploadResultMap.get("fileInfoUploadResult"));
				}
				return "redirect:" + AsaproUtils.getAdminPath(currentSite, true) +  "/policyCfrnc/update.do?mngSeq=" + policyCfrncForm.getMngSeq();
			}
			
			redirectAttributes.addFlashAttribute("inserted", true);
			return "redirect:" + AsaproUtils.getAdminPath(currentSite, true) + "/policyCfrnc/list.do";
		}
		
	}	
	
	
	/**
	 * 정책협의회 관리 수정 뷰를 반환한다.
	 * @param model
	 * @param currentSite
	 * @param currentAdmin
	 * @param policyCfrncForm
	 * @param policyCfrncSearch
	 * @return
	 * @throws AsaproException
	 * @throws IOException
	 */
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/policyCfrnc/update.do", method=RequestMethod.GET)
	public String policyMngrUpdateGet(Model model
						, @CurrentSite Site currentSite
						, @CurrentAdmin AdminMember currentAdmin
						, @ModelAttribute("policyCfrncForm") PolicyCfrnc policyCfrncForm
						, @ModelAttribute("policyCfrncSearch") PolicyCfrncSearch policyCfrncSearch
						) throws AsaproException, IOException{
	
		model.addAttribute("formMode", "UPDATE");
		
		this.loadCodes(model); // 코드목록 로드
		
		// 1. 로그인 권한 체크
		// 최고 관리자이거나 캠퍼스타운 관리자인 경우
		if(currentAdmin.getAdminRole().getRoleCode().equals("ROLE_SUPER_ADMIN") ||
		   currentAdmin.getAdminRole().getRoleCode().equals("ROLE_NORMAL_ADMIN") ||
		   currentAdmin.getAdminRole().getRoleCode().equals("ROLE_SCT_ADMIN") ||
		   currentAdmin.getAdminOrganization().equals("university") && currentAdmin.getAdminTeam() == null)
		{
			model.addAttribute("authYn", "Y"); // CRUD 다 가능
		} else {
			model.addAttribute("authYn", "N"); // R만 가능
		}
		policyCfrncSearch.setSitePrefix(currentSite.getSitePrefix());
		policyCfrncSearch.setDelYn("N");
		
		/* 1. 정책 협의회 관리 상세 정보 조회 */
		PolicyCfrnc policyCfrncInfo = policyCfrncService.getPolicyCfrnMngrInfo(policyCfrncSearch);
		//System.out.println("null?" + policyCfrncInfo==null);
		
		model.addAttribute("policyCfrncForm", policyCfrncInfo);
		
		//파일 업로드 에러에 대한 정보가 flashMap에 들어 있는지 확인
		Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);
		if( flashMap != null &&  flashMap.get("fileInfoUploadResult") != null ){
			FileInfoUploadResult fileInfoUploadResult = (FileInfoUploadResult) flashMap.get("fileInfoUploadResult");
			model.addAttribute("fileInfoUploadResult", fileInfoUploadResult);
		}
		
		/* 2. 추진 정보 조회*/
		PolicyCfrncSearch policyCfrncSearch2 = new PolicyCfrncSearch();
		policyCfrncSearch2.setMngSeq(policyCfrncForm.getMngSeq());
		List<PolicyCfrnc> list = policyCfrncService.getPolicyCfrnDtlInfoList(policyCfrncSearch2);
		
		if(list.size() > 0){
			model.addAttribute("list", list);
		}
		
		return "asapro/admin/campustown/policyCfrnc/policyCfrncMngrForm";
	}
	
	/**
	 * 정책협의회 관리를 수정한다.
	 * @param model
	 * @param currentSite
	 * @param currentAdmin
	 * @param policyCfrncForm
	 * @param policyCfrncSearch
	 * @return
	 * @throws AsaproException
	 * @throws IOException
	 */
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/policyCfrnc/update.do", method=RequestMethod.POST)
	public String policyMngrUpdatePost(Model model
						, @CurrentSite Site currentSite
						, @CurrentAdmin AdminMember currentAdmin
						, @ModelAttribute("policyCfrncForm") PolicyCfrnc policyCfrncForm
						, @ModelAttribute("policyCfrncSearch") PolicyCfrncSearch policyCfrncSearch
						, BindingResult bindingResult
						, RedirectAttributes redirectAttributes
						) throws AsaproException, IOException{
	
		//System.out.println("PolicyCfrncAdminController.policyMngrUpdatePost()~!!! ");
		model.addAttribute("formMode", "UPDATE");
		
		policyCfrncForm.setSitePrefix(currentSite.getSitePrefix());
		policyCfrncValidator.validate(policyCfrncForm, bindingResult, "UPDATE");
		
		if(bindingResult.hasErrors() ){
			//System.out.println("bindingResult.hasErrors()!!!");
			
			model.addAttribute("formMode", "UPDATE");
			model.addAttribute("updateFail", true);
			
			return "asapro/admin/campustown/policyCfrnc/policyCfrncMngrForm";
		} else {
			
			//게시물 수정
			Map<String, FileInfoUploadResult> fileInfoUploadResultMap = policyCfrncService.updatePolicyMngr(policyCfrncForm);
			//업로드 에러 있으면 바로 수정화면으로 전환 - 에러난 파일 제외 글과 다른 파일들은 저장된 상태임.
			if(fileInfoUploadResultMap.get("fileInfoUploadResult").hasErrors() ){
				FlashMap flashMap = RequestContextUtils.getOutputFlashMap(request);
				
				if(fileInfoUploadResultMap.get("fileInfoUploadResult").hasErrors() ){
					flashMap.put("fileInfoUploadResult", fileInfoUploadResultMap.get("fileInfoUploadResult"));
				}
				return "redirect:" + AsaproUtils.getAdminPath(currentSite, true) +  "/policyCfrnc/update.do?mngSeq=" + policyCfrncForm.getMngSeq();
			}
			
			redirectAttributes.addFlashAttribute("updated", true);
			return "redirect:" + AsaproUtils.getAdminPath(currentSite, true) +  "/policyCfrnc/update.do?mngSeq=" + policyCfrncForm.getMngSeq();
		}
	}
	
	/**
	 * 정책협의회관리 정보를 삭제한다.
	 * @param model
	 * @param currentSite
	 * @param currentAdmin
	 * @param mngSeqs
	 * @return
	 * @throws AsaproException
	 * @throws IOException
	 */
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/policyCfrnc/delete.do", method=RequestMethod.POST)
	@ResponseBody
	public ServerMessage deletePolicyMngrPost(Model model, @CurrentSite Site currentSite
										, @CurrentAdmin AdminMember currentAdmin
										, @RequestParam(value="mngSeqs[]", required=true) String[] mngSeqs
						) throws AsaproException, IOException{
		
		ServerMessage serverMessage = new ServerMessage();
		
		if( ArrayUtils.isEmpty(mngSeqs) ){
			serverMessage.setSuccess(false);
			serverMessage.setText("삭제할 항목이 없습니다.");
		} else {
			List<PolicyCfrnc> policyCfrncList = new ArrayList<PolicyCfrnc>();
			PolicyCfrnc policyCfrnc = null;
			
			for( String mngSeq : mngSeqs){
				policyCfrnc = new PolicyCfrnc();
				policyCfrnc.setSitePrefix(currentSite.getSitePrefix());
				policyCfrnc.setMngSeq(Integer.parseInt(mngSeq));
				policyCfrnc.setDelId(currentAdmin.getAdminId());
				policyCfrnc.setDelNm(currentAdmin.getAdminName());
				policyCfrncList.add(policyCfrnc);
			}
			
			int result = 0;
			
			result = policyCfrncService.deletePolicyCfrnc(policyCfrncList);
			
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
	
		
	
	
	
	
	
	
	/**********************************************************************************/
	// 추진정보	-------------> POLICY_CFRNC_MANAGE의 STTUS = 'lo' (장기과제) 일 때,
	/**********************************************************************************/
	
	/**
	 * 추진 정보 등록 및 조회
	 * @param model
	 * @param currentSite
	 * @param currentAdmin
	 * @param policyCfrncDtlForm
	 * @return
	 * @throws AsaproException
	 * @throws IOException
	 */
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/policyCfrnc/dtl/jsonCR.do", method=RequestMethod.POST)
	@ResponseBody
	public PolicyCfrnc insertPolicyMngrDtlPost(Model model
						, @CurrentSite Site currentSite
						, @CurrentAdmin AdminMember currentAdmin
						, @ModelAttribute("policyCfrncDtlForm") PolicyCfrnc policyCfrncDtlForm
						) throws AsaproException, IOException{
	
		//System.out.println("PolicyCfrncAdminController.insertPolicyMngrDtlPost()~!!! ");
		
		int result = 0;
		
		policyCfrncDtlForm.setRegId(currentAdmin.getAdminId());
		policyCfrncDtlForm.setRegNm(currentAdmin.getAdminName());
		
		result = policyCfrncService.insertPolicyMngrDtl(policyCfrncDtlForm);
		//System.out.println("result:::" + result);
		
		PolicyCfrncSearch policyCfrncSearch = new PolicyCfrncSearch();
		policyCfrncSearch.setMngSeq(policyCfrncDtlForm.getMngSeq());
		
		int dtlSeq = 0;
		PolicyCfrnc policyCfrnc = new PolicyCfrnc();
		
		if(result == 1){
			dtlSeq = policyCfrncService.getPolicyMaxDtlSeq(policyCfrncSearch);
			
			if(dtlSeq != 0){
				policyCfrncSearch.setDtlSeq(dtlSeq);
				policyCfrnc = policyCfrncService.getPolicyCfrnDtlInfo(policyCfrncSearch);
			}
		}
		return policyCfrnc;
	}	
	
	
	/**
	 * 추진 정보 수정
	 * @param model
	 * @param currentSite
	 * @param currentAdmin
	 * @param dtlSeq
	 * @param prtnDt
	 * @param prtnMatter
	 * @param rm
	 * @return
	 */
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/policyCfrnc/dtl/jsonU.do", method=RequestMethod.GET)
	@ResponseBody
	public Integer updatePolicyMngrDtlPost(Model model
						, @CurrentSite Site currentSite
						, @CurrentAdmin AdminMember currentAdmin
						, @RequestParam(value="dtlSeq", required=true) Integer dtlSeq
						, @RequestParam(value="prtnDt", required=true) String prtnDt
						, @RequestParam(value="prtnMatter", required=true) String prtnMatter
						, @RequestParam(value="rm", required=true) String rm
						){
		
		PolicyCfrnc policyCfrnc = new PolicyCfrnc();
		
		policyCfrnc.setDtlSeq(dtlSeq);
		policyCfrnc.setPrtnDt(prtnDt);
		policyCfrnc.setPrtnMatter(prtnMatter);
		policyCfrnc.setRm(rm);
		
		policyCfrnc.setUpdId(currentAdmin.getAdminId());
		policyCfrnc.setUpdNm(currentAdmin.getAdminName());
		
		int result = 0;
		result = policyCfrncService.updatePolicyCfrnDtlInfo(policyCfrnc);
		
		return result;
	}
	
	
	/**
	 * 추진 정보 삭제
	 * @param model
	 * @param currentSite
	 * @param currentAdmin
	 * @param dtlSeq
	 * @return
	 */
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/policyCfrnc/dtl/jsonD.do", method=RequestMethod.GET)
	@ResponseBody
	public Integer deletePolicyMngrDtlPost(Model model
						, @CurrentSite Site currentSite
						, @CurrentAdmin AdminMember currentAdmin
						, @RequestParam(value="dtlSeq", required=true) Integer dtlSeq
						){
		
		PolicyCfrnc policyCfrnc = new PolicyCfrnc();
		
		policyCfrnc.setDtlSeq(dtlSeq);
		
		policyCfrnc.setDelId(currentAdmin.getAdminId());
		policyCfrnc.setDelNm(currentAdmin.getAdminName());
		
		int result = 0;
		result = policyCfrncService.deletePolicyCfrnDtlInfo(policyCfrnc);
	
		
		return result;
	}
	
	
	

		
	
		
	
	
}
