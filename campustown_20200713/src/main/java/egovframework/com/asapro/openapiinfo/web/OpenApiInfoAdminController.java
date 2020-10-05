/**
 * 
 */
package egovframework.com.asapro.openapiinfo.web;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import egovframework.com.asapro.code.service.Code;
import egovframework.com.asapro.code.service.CodeService;
import egovframework.com.asapro.fileinfo.service.FileInfo;
import egovframework.com.asapro.fileinfo.service.FileInfoUploadResult;
import egovframework.com.asapro.openapiinfo.service.OpenApiInfo;
import egovframework.com.asapro.openapiinfo.service.OpenApiInfoSearch;
import egovframework.com.asapro.openapiinfo.service.OpenApiInfoService;
import egovframework.com.asapro.site.service.Site;
import egovframework.com.asapro.support.Constant;
import egovframework.com.asapro.support.ServerMessage;
import egovframework.com.asapro.support.annotation.CurrentSite;
import egovframework.com.asapro.support.exception.AsaproException;
import egovframework.com.asapro.support.jdbc.AsaproDBUtils;
import egovframework.com.asapro.support.pagination.Paging;
import egovframework.com.asapro.support.util.AsaproUtils;
import twitter4j.JSONException;

/**
 * 오픈 api 정보 관리자 컨트롤러
 * @author yckim
 * @since 2019. 2. 12.
 *
 */
@Controller
public class OpenApiInfoAdminController {
	
	@Autowired
	private OpenApiInfoService openApiInfoService;
	
	@Autowired
	private OpenApiInfoValidator openApiInfoValidator;
	
	@Autowired
	private CodeService codeService;
	
	@Autowired
	private HttpServletRequest request;
	
	/**
	 * 오픈 api 정보 목록을 조회한다.
	 * @param model
	 * @param currentSite
	 * @param openApiInfoSearch
	 * @return 오픈 api 정보 목록
	 */
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/openapiinfo/list.do", method=RequestMethod.GET)
	public String openApiInfoListGet(Model model, @CurrentSite Site currentSite, @ModelAttribute("openApiInfoSearch") OpenApiInfoSearch openApiInfoSearch){
		openApiInfoSearch.fixBrokenSvByDefaultCharsets();
		openApiInfoSearch.setPaging(true);
		
		List<OpenApiInfo> list = openApiInfoService.getOpenApiInfoList(openApiInfoSearch);
		int total = openApiInfoService.getOpenApiInfoListTotal(openApiInfoSearch);
		
		Paging paging = new Paging(list, total, openApiInfoSearch);
		model.addAttribute("paging", paging);
		
		//제공사이트 코드목록
		List<Code> providerCodeList = codeService.getCodeList("provider");
		model.addAttribute("providerCodeList", providerCodeList);
		//결과 유형 코드목록
		List<Code> returyTypeCodeList = codeService.getCodeList("returnType");
		model.addAttribute("returyTypeCodeList", returyTypeCodeList);
				
		return "asapro/admin/openapiinfo/list";
	}
	
	/**
	 * 오픈 api 정보 등록 폼 뷰를 반환한다.
	 * @param model
	 * @param currentSite
	 * @param openApiInfoForm
	 * @return 추가폼뷰
	 */
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/openapiinfo/insert.do", method=RequestMethod.GET)
	public String openApiInfoInsertGet(Model model, @CurrentSite Site currentSite, @ModelAttribute("openApiInfoForm") OpenApiInfo openApiInfoForm) {
		
		//제공사이트 코드목록
		List<Code> providerCodeList = codeService.getCodeList("provider");
		model.addAttribute("providerCodeList", providerCodeList);
		//결과 유형 코드목록
		List<Code> returyTypeCodeList = codeService.getCodeList("returnType");
		model.addAttribute("returyTypeCodeList", returyTypeCodeList);
		
		model.addAttribute("formMode", "INSERT");
		return "asapro/admin/openapiinfo/form";
	}
	
	/**
	 * 오픈 api 정보를 추가한다.
	 * @param model
	 * @param redirectAttributes
	 * @param currentSite
	 * @param openApiInfoForm
	 * @param bindingResult
	 * @return 추가결과
	 * @throws AsaproException
	 * @throws IOException
	 */
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/openapiinfo/insert.do", method=RequestMethod.POST)
	public String openApiInfoInsertPost(Model model, RedirectAttributes redirectAttributes, @CurrentSite Site currentSite, @ModelAttribute("openApiInfoForm") OpenApiInfo openApiInfoForm, BindingResult bindingResult) throws AsaproException, IOException {

		openApiInfoValidator.validate(openApiInfoForm, bindingResult, "INSERT");
		
		
		if( bindingResult.hasErrors() ){
			
			//제공사이트 코드목록
			List<Code> providerCodeList = codeService.getCodeList("provider");
			model.addAttribute("providerCodeList", providerCodeList);
			//결과 유형 코드목록
			List<Code> returyTypeCodeList = codeService.getCodeList("returnType");
			model.addAttribute("returyTypeCodeList", returyTypeCodeList);
			
			model.addAttribute("formMode", "INSERT");
			model.addAttribute("insertFail", true);
			return "asapro/admin/openapiinfo/form";
		} else {
			openApiInfoForm.setApiRegDate(new Date());
			
			openApiInfoService.insertOpenApiInfo(openApiInfoForm);
			redirectAttributes.addFlashAttribute("inserted", true);
			return "redirect:" + AsaproUtils.getAdminPath(currentSite, true) + "/openapiinfo/list.do";
		}
		
	}
	
	/**
	 * 오픈 api 정보 수정 폼 뷰를 반환한다.
	 * @param model
	 * @param currentSite
	 * @param openApiInfoForm
	 * @return 수정폼뷰
	 */
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/openapiinfo/update.do", method=RequestMethod.GET)
	public String openApiInfoUpdateGet(Model model, @CurrentSite Site currentSite, @ModelAttribute("openApiInfoForm") OpenApiInfo openApiInfoForm) {
		
		OpenApiInfo openApiInfoModel = openApiInfoService.getOpenApiInfo(openApiInfoForm);
		
		//제공사이트 코드목록
		List<Code> providerCodeList = codeService.getCodeList("provider");
		model.addAttribute("providerCodeList", providerCodeList);
		//결과 유형 코드목록
		List<Code> returyTypeCodeList = codeService.getCodeList("returnType");
		model.addAttribute("returyTypeCodeList", returyTypeCodeList);
		
		model.addAttribute("formMode", "UPDATE");
		model.addAttribute("openApiInfoForm", openApiInfoModel);
		return "asapro/admin/openapiinfo/form";
	}
	
	/**
	 * 오픈 api 정보를 수정한다.
	 * @param model
	 * @param redirectAttributes
	 * @param currentSite
	 * @param openApiInfoForm
	 * @param bindingResult
	 * @return 수정결과
	 * @throws AsaproException
	 * @throws IOException
	 */
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/openapiinfo/update.do", method=RequestMethod.POST)
	public String openApiInfoUpdatePost(Model model, RedirectAttributes redirectAttributes, @CurrentSite Site currentSite, @ModelAttribute("openApiInfoForm") OpenApiInfo openApiInfoForm, BindingResult bindingResult) throws AsaproException, IOException {

		openApiInfoValidator.validate(openApiInfoForm, bindingResult, "UPDATE");
		
		
		if( bindingResult.hasErrors() ){
			
			//제공사이트 코드목록
			List<Code> providerCodeList = codeService.getCodeList("provider");
			model.addAttribute("providerCodeList", providerCodeList);
			//결과 유형 코드목록
			List<Code> returyTypeCodeList = codeService.getCodeList("returnType");
			model.addAttribute("returyTypeCodeList", returyTypeCodeList);
			
			model.addAttribute("formMode", "INSERT");
			model.addAttribute("updateFail", true);
			return "asapro/admin/openapiinfo/form";
		} else {
			
			openApiInfoService.updateOpenApiInfo(openApiInfoForm);
			redirectAttributes.addFlashAttribute("updated", true);
			return "redirect:" + AsaproUtils.getAdminPath(currentSite, true) + "/openapiinfo/update.do?apiSeq=" + openApiInfoForm.getApiSeq();
		}
		
	}
	
	/**
	 * 오픈 api 정보를 삭제한다.
	 * @param model
	 * @param currentSite
	 * @param apiSeqs
	 * @return 삭제결과
	 * @throws FileNotFoundException
	 */
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/openapiinfo/delete.do", method=RequestMethod.POST)
	@ResponseBody
	public ServerMessage deleteOpenApiInfoPost(Model model, @CurrentSite Site currentSite, @RequestParam(value="apiSeqs[]", required=true) Integer[] apiSeqs) throws FileNotFoundException{
		ServerMessage serverMessage = new ServerMessage();
		if( ArrayUtils.isEmpty(apiSeqs) ){
			serverMessage.setSuccess(false);
			serverMessage.setText("삭제할 항목이 없습니다.");
		} else {
			List<OpenApiInfo> openApiInfoList = new ArrayList<OpenApiInfo>();
			OpenApiInfo openApiInfo = null;
			for( Integer apiSeq : apiSeqs ){
				openApiInfo = new OpenApiInfo();
				openApiInfo.setApiSeq(apiSeq);
				openApiInfoList.add(openApiInfo);
			}
			int result = openApiInfoService.deleteOpenApiInfo(openApiInfoList);
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
	
	/**
	 * 오픈 api 정보를 배치한다.
	 * @param model
	 * @param currentSite
	 * @param apiSeq
	 * @return 배치결과
	 * @throws SecurityException 
	 * @throws NoSuchMethodException 
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 * @throws IllegalAccessException 
	 * @throws ClassNotFoundException 
	 * @throws InstantiationException 
	 * @throws IOException 
	 * @throws JSONException 
	 */
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/openapiinfo/batch.do", method=RequestMethod.POST)
	@ResponseBody
	public ServerMessage deleteOpenApiInfoPost(Model model, @CurrentSite Site currentSite, @RequestParam(value="apiSeq", required=true) Integer apiSeq) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, ClassNotFoundException, InstantiationException, IOException {
		ServerMessage serverMessage = new ServerMessage();
		if( apiSeq == null ){
			serverMessage.setSuccess(false);
			serverMessage.setText("배치할 서비스가 없습니다.");
		} else {
			OpenApiInfo openApiInfoSearch = new OpenApiInfo();
			openApiInfoSearch.setApiSeq(apiSeq);
			OpenApiInfo openApiInfoModel = openApiInfoService.getOpenApiInfo(openApiInfoSearch);
			
			//api 정보에 배치할 테이블명이 등록되지 않았을때
			if(StringUtils.isBlank(openApiInfoModel.getApiTableName()) ){
				serverMessage.setSuccess(false);
				serverMessage.setText("배치할 테이블명을 등록하지 않았습니다. 테이블명을 등록해 주세요.");
				return serverMessage;
			}

			//배치할 테이블이 있는지 확인
			// 오라클만 - 다른 DB대응은 나중에 하자
			String tableChkSql = "SELECT count(*) FROM tabs WHERE table_name='" + openApiInfoModel.getApiTableName() + "'";
			int chkResult = AsaproDBUtils.selectScalar(tableChkSql);
			if(chkResult <= 0){
				serverMessage.setSuccess(false);
				serverMessage.setText("등록한 배치 테이블이 존재하지 않습니다. 테이블명 또는 테이블 생성유무를 확인하세요.");
				return serverMessage;
			}
			
			int inserted = 0;	//최종 인서트한 데이터 수
			
			//내부에서 open api 호출
			//inserted = openApiInfoService.batchOpenApiInfo(openApiInfoModel);
			
			//중계서버를 두고 api를 호출
			inserted = openApiInfoService.batchOpenApiInfoByRelay(openApiInfoModel);
			
			
			if(inserted > 0){
				serverMessage.setSuccess(true);
				serverMessage.setSuccessCnt(inserted);
				
				//배치정보 수정
				openApiInfoModel.setApiLastBatch(new Date());
				openApiInfoModel.setApiInputType("manual");//수동
				openApiInfoService.updateBatchInfo(openApiInfoModel);
				
			} else if(inserted == -1) {
				serverMessage.setSuccess(false);
				serverMessage.setText("지원하지 않는 서비스 입니다. API 서비스를 확인 후 설정 정보를 수정하세요.");
			} else if(inserted == -99) {
					serverMessage.setSuccess(false);
					serverMessage.setText("API의 일 트래픽 제한을 초과했습니다.");
			} else {
				serverMessage.setSuccess(false);
				serverMessage.setText("배치하지 못하였습니다.[Server Error]");
			}
		}
		return serverMessage;
	}
	
}
