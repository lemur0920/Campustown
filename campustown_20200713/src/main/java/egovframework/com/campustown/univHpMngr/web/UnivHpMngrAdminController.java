package egovframework.com.campustown.univHpMngr.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import egovframework.com.asapro.code.service.CodeService;
import egovframework.com.asapro.fileinfo.service.FileInfo;
import egovframework.com.asapro.fileinfo.service.FileInfoService;
import egovframework.com.asapro.fileinfo.service.FileInfoUploadResult;
import egovframework.com.asapro.member.admin.service.AdminMember;
import egovframework.com.asapro.organization.service.Department;
import egovframework.com.asapro.organization.service.DepartmentSearch;
import egovframework.com.asapro.organization.service.Organization;
import egovframework.com.asapro.organization.service.OrganizationSearch;
import egovframework.com.asapro.organization.service.OrganizationService;
import egovframework.com.asapro.site.service.Site;
import egovframework.com.asapro.support.Constant;
import egovframework.com.asapro.support.ServerMessage;
import egovframework.com.asapro.support.annotation.CurrentAdmin;
import egovframework.com.asapro.support.annotation.CurrentSite;
import egovframework.com.asapro.support.exception.AsaproException;
import egovframework.com.asapro.support.pagination.Paging;
import egovframework.com.asapro.support.util.AsaproUtils;
import egovframework.com.campustown.startHpMngr.service.StartHpMngr;
import egovframework.com.campustown.startHpMngr.service.StartHpMngrSearch;
import egovframework.com.campustown.startHpMngr.service.StartHpMngrService;
import egovframework.com.campustown.univHpMngr.service.UnivHpMngr;
import egovframework.com.campustown.univHpMngr.service.UnivHpMngrSearch;
import egovframework.com.campustown.univHpMngr.service.UnivHpMngrService;

/**
 * 대학 홈페이지 관리 관리자 컨트롤러
 * @author jaseo
 * @since 2020. 02. 11.
 */
@Controller
public class UnivHpMngrAdminController{
	
	@Autowired
	private MessageSource messageSource;
	
	@Autowired
	private CodeService codeService;
	
	@Autowired
	private OrganizationService organizationService;
	
	@Autowired
	private FileInfoService fileInfoService;
	
	@Autowired
	private HttpServletRequest request;
	
	@Autowired
	private UnivHpMngrService univHpMngrService;
	
	@Autowired
	private UnivHpMngrValidator univHpMngrValidator;
	
	@Autowired
	private StartHpMngrService startHpMngrService;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(UnivHpMngrAdminController.class);
	

	/**
	 * 코드목록 로드
	 * @param model
	 */
	private void loadCodes(Model model) {
		//지역번호
		model.addAttribute("sigunguCodeList", codeService.getCodeList("SIGUNGU_CODE", "CODE_NAME", "ASC"));	//codeOrder ASC
		//휴대폰번호 앞자리
		model.addAttribute("mobile1CodeList", codeService.getCodeList("mobile1_code"));
		//일반전화 앞자리
		model.addAttribute("tel1CodeList", codeService.getCodeList("tel_area_code"));
		//직급/직책 목록
		model.addAttribute("positionList", codeService.getCodeList("position"));
		// 이메일 도메인
		model.addAttribute("emailDomainCodeList", codeService.getCodeList("EMAIL_DOMAIN"));		  
	}
	
	
	/**
	 * 부서목록 로드
	 * @param model
	 * @param orgId
	 */
	private void loadDepartment(Model model, String orgId){
		//부서목록
		DepartmentSearch departmentSearch = new DepartmentSearch();
		departmentSearch.setPaging(false);
		departmentSearch.setDepUse(true);
		departmentSearch.setOrgId(orgId);
		List<Department> departmentList = organizationService.getDepartmentList(departmentSearch);
		//System.out.println("departmentList.size(): " + departmentList.size());
		//System.out.println("departmentList.size(): " + departmentList.get(0).getDepName());
		model.addAttribute("departmentList", departmentList);
	}
	
	
	/**
	 * 대학 홈페이지 관리 목록을 반환한다.
	 * @param model
	 * @param currentSite
	 * @param currentAdmin
	 * @param startHpMngrSearch
	 * @return
	 */
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/univHpMngr/list.do", method=RequestMethod.GET)
	public String univHpMngrInfoListGet(Model model
			, @CurrentSite Site currentSite
			, @CurrentAdmin AdminMember currentAdmin
			, @ModelAttribute("univHpMngrSearch") UnivHpMngrSearch univHpMngrSearch
			) {
		
		univHpMngrSearch.setSitePrefix(currentSite.getSitePrefix());
		univHpMngrSearch.fixBrokenSvByDefaultCharsets();
		univHpMngrSearch.setPaging(true);
		
		/* 0. 코드 목록 로드 */
		this.loadCodes(model);   // sigunguCodeList
		this.loadDepartment(model, "university"); // departmentList
		
		
		/* 1. 지역 셋팅 (서울시 대학의 지역(구) 기준)*/
		
		// 1. 관리자인 경우
		// 역할이 : ROLE_SUPER_ADMIN, ROLE_NORMAL_ADMIN, ROLE_SCT_ADMIN 이면
		// ===> 전체 목록 표출(셋팅 없음!) 
//		if(currentAdmin.getAdminRole().getRoleCode().equals("ROLE_SUPER_ADMIN") ||
//		   currentAdmin.getAdminRole().getRoleCode().equals("ROLE_NORMAL_ADMIN") ||
//		   currentAdmin.getAdminRole().getRoleCode().equals("ROLE_SCT_ADMIN"))
//		{
			
			
		// 2. 대학인 경우
		// 해당 대학의 depId로 데이터 조회해서 지역정보 코드 가져와서 셋팅해주기.
//		}else{
			
			if(currentAdmin.getAdminOrganization().equals("university")){
				String depId = currentAdmin.getAdminDepartment();
				UnivHpMngrSearch univHpMngrSearch2 = new UnivHpMngrSearch();
				univHpMngrSearch2.setUnivId(depId);
				univHpMngrSearch2.setDelYn("N");
				UnivHpMngr univHpMngr = univHpMngrService.getUnivHpMngrInfo(univHpMngrSearch2);
				//List<UnivHpMngr> list2 = univHpMngrService.getUnivHpMngrList(univHpMngrSearch2);
				//System.out.println(list2.size());
				//String srcAreaCode = currentAdmin.getAdminDepartment();
				
				if(univHpMngr != null){
					this.loadCodes(model);   // sigunguCodeList
					univHpMngrSearch.setAreaId(univHpMngr.getAreaGuCd());
					model.addAttribute("srcAreacode", univHpMngr.getAreaGuCd());
				}else{
					this.loadCodes(model);   // sigunguCodeList
				}
			}
//		}
				
		
		/* 2. 대학 셋팅 */
		
		// 1. 관리자인 경우
		// ===> 전체 목록 표출(셋팅 없음!)
//		if(currentAdmin.getAdminRole().getRoleCode().equals("ROLE_SUPER_ADMIN") ||
//		   currentAdmin.getAdminRole().getRoleCode().equals("ROLE_NORMAL_ADMIN") ||
//		   currentAdmin.getAdminRole().getRoleCode().equals("ROLE_SCT_ADMIN"))
//		{
			
		// 2. 대학인 경우
		// 기관이 university 이면 해당 대학의 부서 아이디로 셋팅해주기.
//		}else{
			//this.loadDepartment(model, "university"); // departmentList
			
			if(currentAdmin.getAdminOrganization().equals("university")){
				univHpMngrSearch.setUnivId(currentAdmin.getAdminDepartment());
			}
			
//		}
		model.addAttribute("org", currentAdmin.getAdminOrganization());
		
		List<UnivHpMngr> list = univHpMngrService.getUnivHpMngrList(univHpMngrSearch);
		int total = univHpMngrService.getUnivHpMngrListTotal(univHpMngrSearch); 
		//System.out.println("size : " + list.size());
		//System.out.println("total : " + total);
		
		Paging paging = new Paging(list, total, univHpMngrSearch);
		model.addAttribute("paging", paging);
		
		return "asapro/admin/campustown/univHpMngr/univHpMngrList";
	}
	
	
	/**
	 * 대학 홈페이지 관리 추가폼 뷰를 반환한다.
	 * @param model
	 * @param currentSite
	 * @param currentAdmin
	 * @param univHpMngrSearch
	 * @return
	 */
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/univHpMngr/insert.do", method=RequestMethod.GET)
	public String insertUnivHpMngrInfoGet(Model model
			, @CurrentSite Site currentSite
			, @CurrentAdmin AdminMember currentAdmin
			, @ModelAttribute("univHpMngrForm") UnivHpMngr univHpMngrForm
			, @ModelAttribute("univHpMngrSearch") UnivHpMngrSearch univHpMngrSearch
			) {
		
		model.addAttribute("formMode", "INSERT");
		
		/* 1. 대학 셋팅 */
		/*
		// 1. 관리자인 경우
		// 전체 코드 리스트 표출
		if(currentAdmin.getAdminRole().getRoleCode().equals("ROLE_SUPER_ADMIN") ||
		   currentAdmin.getAdminRole().getRoleCode().equals("ROLE_NORMAL_ADMIN") ||
		   currentAdmin.getAdminRole().getRoleCode().equals("ROLE_SCT_ADMIN"))
		{
			this.loadDepartment(model, "university"); // departmentList
			model.addAttribute("org", currentAdmin.getAdminOrganization());
		// 2. 대학인 경우
		// 기관이 university 이면 해당 대학의 부서 아이디로 셋팅해주기.	
		}else{
			this.loadDepartment(model, "university"); // departmentList
			if(currentAdmin.getAdminOrganization().equals("university")){
				univHpMngrSearch.setUnivId(currentAdmin.getAdminDepartment());
				model.addAttribute("srcUnivId", currentAdmin.getAdminDepartment());
			}
			model.addAttribute("org", currentAdmin.getAdminOrganization());
		}
		*/
		// 로그인 계정 기관정보
		model.addAttribute("org", currentAdmin.getAdminOrganization());
		// 코드목록 로드
		this.loadCodes(model);
		
		return "asapro/admin/campustown/univHpMngr/univHpMngrForm";
	}
	
	/**
	 * 대학 홈페이지 관리 추가폼 뷰를 등록한다.
	 * @param model
	 * @param currentSite
	 * @param currentAdmin
	 * @param univHpMngrSearch
	 * @return
	 */
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/univHpMngr/insert.do", method=RequestMethod.POST)
	public String insertUnivHpMngrInfoPost(Model model
			, @CurrentSite Site currentSite
			, @CurrentAdmin AdminMember currentAdmin
			, @ModelAttribute("univHpMngrForm") UnivHpMngr univHpMngrForm
			, @ModelAttribute("univHpMngrSearch") UnivHpMngrSearch univHpMngrSearch
			, BindingResult bindingResult
			)  throws AsaproException, IOException{
		
		univHpMngrValidator.validate(univHpMngrForm, bindingResult, "INSERT");
		
		if(bindingResult.hasErrors() ){
			
			model.addAttribute("formMode", "INSERT");
			return "asapro/admin/campustown/univHpMngr/univHpMngrForm";
		
		} else {
			
			FileInfoUploadResult fileInfoUploadResult = new FileInfoUploadResult();
			String webRoot = AsaproUtils.getWebRoot(request);
			//첨부된 파일 
			FileInfo fileInfo = new FileInfo();
			fileInfo.setSitePrefix(currentSite.getSitePrefix());
			fileInfo.setFileModuleId(univHpMngrForm.getSitePrefix());
			
			//멤버
			fileInfo.setMemberId(currentAdmin.getAdminId());
			//첨부유형
			fileInfo.setFileAttachmentType("appending");
			fileInfo.setFileModule("univHpMngr");
			
			
			//이미지 처리
			if( univHpMngrForm.getFile1() != null && univHpMngrForm.getFile1().getSize() > 0 ){
				
				//대체텍스트
				if(StringUtils.isNotBlank(univHpMngrForm.getFile1AltText()) ){
					fileInfo.setFileAltText(univHpMngrForm.getFile1AltText() );
				}else{
					//fileInfo.setFileAltText(univHpMngrForm.getFile1().getOriginalFilename() );
					fileInfo.setFileAltText(univHpMngrForm.getUnivNm()+"의 대표 이미지");
					univHpMngrForm.setFile1AltText(univHpMngrForm.getUnivNm()+"의 대표 이미지");
				}
				
				fileInfoService.saveFile(univHpMngrForm.getFile1(), webRoot, fileInfo, (Constant.MEGA * 10), false, 200, 60,false);  //대표 이미지 모두 업로드
				if( !fileInfo.isFileUploadSuccess() ){
					fileInfoUploadResult.addErrorInfo(fileInfo);
				}else{
					univHpMngrForm.setFile1Info(fileInfo);
					univHpMngrForm.setFileId1(String.valueOf(fileInfo.getFileId()));
				}
			}
			
			if( univHpMngrForm.getFile2() != null && univHpMngrForm.getFile2().getSize() > 0 ){
				
				fileInfo.setFileAltText(univHpMngrForm.getUnivNm()+"의 대학 CI");
				fileInfoService.saveFile(univHpMngrForm.getFile2(), webRoot, fileInfo, (Constant.MEGA * 10), false, 200, 60,false);  //CI 이미지 모두 업로드
				if( !fileInfo.isFileUploadSuccess() ){
					fileInfoUploadResult.addErrorInfo(fileInfo);
				}else{
					univHpMngrForm.setFile2Info(fileInfo);
					univHpMngrForm.setFileId2(String.valueOf(fileInfo.getFileId()));
				}
			}
			
			if( univHpMngrForm.getFile3() != null && univHpMngrForm.getFile3().getSize() > 0 ){
				
				fileInfo.setFileAltText(univHpMngrForm.getUnivNm()+"의 지원프로그램 첨부파일");
				fileInfoService.saveFile(univHpMngrForm.getFile3(), webRoot, fileInfo, (Constant.MEGA * 10), false, 200, 60,false);  //CI 이미지 모두 업로드
				if( !fileInfo.isFileUploadSuccess() ){
					fileInfoUploadResult.addErrorInfo(fileInfo);
				}else{
					univHpMngrForm.setFile3Info(fileInfo);
					univHpMngrForm.setFileId3(String.valueOf(fileInfo.getFileId()));
				}
			}
			
			
			// sns 정보 셋팅 (프로토콜 없으면 http:// 붙여준다) 
			if( StringUtils.isNotBlank(univHpMngrForm.getSnsFace()) ){
				if( !univHpMngrForm.getSnsFace().startsWith("http://") && !univHpMngrForm.getSnsFace().startsWith("https://") ){
					univHpMngrForm.setSnsFace("http://" + univHpMngrForm.getSnsFace());
				}
			}
			if( StringUtils.isNotBlank(univHpMngrForm.getSnsTwit()) ){
				if( !univHpMngrForm.getSnsTwit().startsWith("http://") && !univHpMngrForm.getSnsTwit().startsWith("https://") ){
					univHpMngrForm.setSnsTwit("http://" + univHpMngrForm.getSnsTwit());
				}
			}
			if( StringUtils.isNotBlank(univHpMngrForm.getSnsBlog()) ){
				if( !univHpMngrForm.getSnsBlog().startsWith("http://") && !univHpMngrForm.getSnsBlog().startsWith("https://") ){
					univHpMngrForm.setSnsBlog("http://" + univHpMngrForm.getSnsBlog());
				}
			}
			if( StringUtils.isNotBlank(univHpMngrForm.getSnsHp()) ){
				if( !univHpMngrForm.getSnsHp().startsWith("http://") && !univHpMngrForm.getSnsHp().startsWith("https://") ){
					univHpMngrForm.setSnsHp("http://" + univHpMngrForm.getSnsHp());
				}
			}
			if( StringUtils.isNotBlank(univHpMngrForm.getSnsInsta()) ){
				if( !univHpMngrForm.getSnsInsta().startsWith("http://") && !univHpMngrForm.getSnsInsta().startsWith("https://") ){
					univHpMngrForm.setSnsInsta("http://" + univHpMngrForm.getSnsBlog());
				}
			}
			if( StringUtils.isNotBlank(univHpMngrForm.getSnsKakao()) ){
				if( !univHpMngrForm.getSnsKakao().startsWith("http://") && !univHpMngrForm.getSnsKakao().startsWith("https://") ){
					univHpMngrForm.setSnsKakao("http://" + univHpMngrForm.getSnsHp());
				}
			}
			
			
			univHpMngrForm.setRegId(currentAdmin.getAdminId());
			univHpMngrForm.setRegNm(currentAdmin.getAdminName());
			//System.out.println("univId: " + univHpMngrSearch.getUnivId());
			univHpMngrSearch.setDelYn("");
			UnivHpMngr univHpMngr= univHpMngrService.getUnivHpMngrInfo(univHpMngrSearch);
			if(univHpMngr != null){
				univHpMngrForm.setDelYn("N");
				univHpMngrForm.setUpdId("");
				univHpMngrForm.setUpdNm("");
				
				univHpMngrService.updateUnivHpMngr(univHpMngrForm);
			}else{
			
				int result = 0;
				result = univHpMngrService.insertUnivHpMngr(univHpMngrForm);	
			}
			/*
			if(result == 1){
				//System.out.println("insert 성공.");
			} else {
				//System.out.println("insert 실패!");
			}
			*/
			return "redirect:"+AsaproUtils.getAdminPath(currentSite)+"/univHpMngr/list.do";
		}
	}
	
	/**
	 * 대학 홈페이지 관리 상세정보를 조회한다.
	 * @param model
	 * @param currentSite
	 * @param currentAdmin
	 * @param univHpMngrForm
	 * @param univHpMngrSearch
	 * @param bindingResult
	 * @return
	 * @throws AsaproException
	 * @throws IOException
	 */
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/univHpMngr/update.do", method=RequestMethod.GET)
	public String updateUnivHpMngrInfoGet(Model model
			, @CurrentSite Site currentSite
			, @CurrentAdmin AdminMember currentAdmin
			, @ModelAttribute("univHpMngrForm") UnivHpMngr univHpMngrForm
			, @ModelAttribute("univHpMngrSearch") UnivHpMngrSearch univHpMngrSearch
			)  throws AsaproException, IOException{
		
		model.addAttribute("formMode", "UPDATE");
		
		// 해당 대학 등록정보 조회
		//System.out.println("UnivId : "+ univHpMngrSearch.getUnivId());
		univHpMngrSearch.setDelYn("N");
		UnivHpMngr univHpMngrInfo = univHpMngrService.getUnivHpMngrInfo(univHpMngrSearch);
		
		/* 0. 코드 목록 로드 */
		this.loadCodes(model);   // 코드목록 로드
		this.loadDepartment(model, "university"); // departmentList
		
		
		
		/* 1. 대학 셋팅 */
		
		// 1. 관리자인 경우
		// 전체 코드 리스트 표출
//		if(currentAdmin.getAdminRole().getRoleCode().equals("ROLE_SUPER_ADMIN") ||
//				currentAdmin.getAdminRole().getRoleCode().equals("ROLE_NORMAL_ADMIN") ||
//				currentAdmin.getAdminRole().getRoleCode().equals("ROLE_SCT_ADMIN"))
//		{
			
		// 2. 대학인 경우
		// 기관이 university 이면 해당 대학의 부서 아이디로 셋팅해주기.	
//		}else{
			
		if(currentAdmin.getAdminOrganization().equals("university")){
			univHpMngrSearch.setUnivId(currentAdmin.getAdminDepartment());
			model.addAttribute("srcUnivId", currentAdmin.getAdminDepartment());
		}
			//model.addAttribute("org", currentAdmin.getAdminOrganization());
//		}
		
		model.addAttribute("org", currentAdmin.getAdminOrganization());
		
		
		
		//System.out.println("tel : " + univHpMngrInfo.getTel());
		// 필수값 아닌 것 처리
		if(univHpMngrInfo.getTel() != null){
			String[] telArr = univHpMngrInfo.getTel().split("-");
			if(telArr.length == 3){
				univHpMngrInfo.setTel1(telArr[0]);
				univHpMngrInfo.setTel2(telArr[1]);
				univHpMngrInfo.setTel3(telArr[2]);
			}
		}else{
			univHpMngrInfo.setTel1("");
			univHpMngrInfo.setTel2("");
			univHpMngrInfo.setTel3("");
		}
		
		if(univHpMngrInfo.getEmail() != null){
			String[] emailArr = univHpMngrInfo.getEmail().split("@");
			if(emailArr.length == 2){
				univHpMngrInfo.setEmail1(emailArr[0]);
				univHpMngrInfo.setEmail2(emailArr[1]);
			}
		} else{
			univHpMngrInfo.setEmail1("");
			univHpMngrInfo.setEmail2("");
		}
		
		// 이미지 파일정보 셋팅
		if(null != univHpMngrInfo.getFileId1() && !"".equals(univHpMngrInfo.getFileId1())){
			FileInfo fileInfo = new FileInfo();
			fileInfo.setSitePrefix(currentSite.getSitePrefix());
			fileInfo.setFileId(Integer.parseInt(univHpMngrInfo.getFileId1()));
			univHpMngrInfo.setFile1Info(fileInfoService.getFileInfo(fileInfo));
		}
		
		if(null != univHpMngrInfo.getFileId2() && !"".equals(univHpMngrInfo.getFileId2())){
			FileInfo fileInfo = new FileInfo();
			fileInfo.setSitePrefix(currentSite.getSitePrefix());
			fileInfo.setFileId(Integer.parseInt(univHpMngrInfo.getFileId2()));
			univHpMngrInfo.setFile2Info(fileInfoService.getFileInfo(fileInfo));
		}
		
		if(null != univHpMngrInfo.getFileId3() && !"".equals(univHpMngrInfo.getFileId3())){
			FileInfo fileInfo = new FileInfo();
			fileInfo.setSitePrefix(currentSite.getSitePrefix());
			fileInfo.setFileId(Integer.parseInt(univHpMngrInfo.getFileId3()));
			univHpMngrInfo.setFile3Info(fileInfoService.getFileInfo(fileInfo));
		}
		
		
		model.addAttribute("univHpMngrForm", univHpMngrInfo);
		
		return "asapro/admin/campustown/univHpMngr/univHpMngrForm";
	}
	
	
	/**
	 * 대학 홈페이지 관리 상세정보를 수정한다.
	 * @param model
	 * @param currentSite
	 * @param currentAdmin
	 * @param univHpMngrForm
	 * @param univHpMngrSearch
	 * @param bindingResult
	 * @return
	 * @throws AsaproException
	 * @throws IOException
	 */
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/univHpMngr/update.do", method=RequestMethod.POST)
	public String updateUnivHpMngrInfoPost(Model model
			, @CurrentSite Site currentSite
			, @CurrentAdmin AdminMember currentAdmin
			, @ModelAttribute("univHpMngrForm") UnivHpMngr univHpMngrForm
			, @ModelAttribute("univHpMngrSearch") UnivHpMngrSearch univHpMngrSearch
			, RedirectAttributes redirectAttributes
			, BindingResult bindingResult
			)  throws AsaproException, IOException{
		
		
		if(bindingResult.hasErrors() ){
			//System.out.println("bindingResult.hasErrors()!!!");
			return "asapro/admin/campustown/univHpMngr/univHpMngrForm";
			//return "asapro/admin/campustown/startHpMngr/startHpMngrForm";
			
		} else {
			//System.out.println("No ERROR!");
			
			FileInfoUploadResult fileInfoUploadResult = new FileInfoUploadResult();
			String webRoot = AsaproUtils.getWebRoot(request);
			//첨부된 파일 
			FileInfo fileInfo = new FileInfo();
			fileInfo.setSitePrefix(currentSite.getSitePrefix());
			fileInfo.setFileModuleId(univHpMngrForm.getSitePrefix());
			
			//멤버
			fileInfo.setMemberId(currentAdmin.getAdminId());
			//첨부유형
			fileInfo.setFileAttachmentType("appending");
			fileInfo.setFileModule("univHpMngr");
			
			
			//이미지 처리
			if( univHpMngrForm.getFile1() != null && univHpMngrForm.getFile1().getSize() > 0 ){
				
				//대체텍스트
				if(StringUtils.isNotBlank(univHpMngrForm.getFile1AltText()) ){
					fileInfo.setFileAltText(univHpMngrForm.getFile1AltText() );
				}else{
					//fileInfo.setFileAltText(univHpMngrForm.getFile1().getOriginalFilename() );
					fileInfo.setFileAltText(univHpMngrForm.getUnivNm()+"의 대표 이미지");
					univHpMngrForm.setFile1AltText(univHpMngrForm.getUnivNm()+"의 대표 이미지");
				}
				
				fileInfoService.saveFile(univHpMngrForm.getFile1(), webRoot, fileInfo, (Constant.MEGA * 10), false, 200, 60,false);  //대표 이미지 모두 업로드
				if( !fileInfo.isFileUploadSuccess() ){
					fileInfoUploadResult.addErrorInfo(fileInfo);
				}else{
					univHpMngrForm.setFile1Info(fileInfo);
					univHpMngrForm.setFileId1(String.valueOf(fileInfo.getFileId()));
				}
			}
			
			if( univHpMngrForm.getFile2() != null && univHpMngrForm.getFile2().getSize() > 0 ){
				
				fileInfo.setFileAltText(univHpMngrForm.getUnivNm()+"의 대학 CI");
				fileInfoService.saveFile(univHpMngrForm.getFile2(), webRoot, fileInfo, (Constant.MEGA * 10), false, 200, 60,false);  //CI 이미지 모두 업로드
				if( !fileInfo.isFileUploadSuccess() ){
					fileInfoUploadResult.addErrorInfo(fileInfo);
				}else{
					univHpMngrForm.setFile2Info(fileInfo);
					univHpMngrForm.setFileId2(String.valueOf(fileInfo.getFileId()));
				}
			}
			
			if( univHpMngrForm.getFile3() != null && univHpMngrForm.getFile3().getSize() > 0 ){
				
				fileInfo.setFileAltText(univHpMngrForm.getUnivNm()+"의 지원프로그램 첨부파일");
				fileInfoService.saveFile(univHpMngrForm.getFile3(), webRoot, fileInfo, (Constant.MEGA * 10), false, 200, 60,false);  //CI 이미지 모두 업로드
				if( !fileInfo.isFileUploadSuccess() ){
					fileInfoUploadResult.addErrorInfo(fileInfo);
				}else{
					univHpMngrForm.setFile3Info(fileInfo);
					univHpMngrForm.setFileId3(String.valueOf(fileInfo.getFileId()));
				}
			}

			// sns 정보 셋팅 (프로토콜 없으면 http:// 붙여준다) 
			if( StringUtils.isNotBlank(univHpMngrForm.getSnsFace()) ){
				if( !univHpMngrForm.getSnsFace().startsWith("http://") && !univHpMngrForm.getSnsFace().startsWith("https://") ){
					univHpMngrForm.setSnsFace("http://" + univHpMngrForm.getSnsFace());
				}
			}
			if( StringUtils.isNotBlank(univHpMngrForm.getSnsTwit()) ){
				if( !univHpMngrForm.getSnsTwit().startsWith("http://") && !univHpMngrForm.getSnsTwit().startsWith("https://") ){
					univHpMngrForm.setSnsTwit("http://" + univHpMngrForm.getSnsTwit());
				}
			}
			if( StringUtils.isNotBlank(univHpMngrForm.getSnsBlog()) ){
				if( !univHpMngrForm.getSnsBlog().startsWith("http://") && !univHpMngrForm.getSnsBlog().startsWith("https://") ){
					univHpMngrForm.setSnsBlog("http://" + univHpMngrForm.getSnsBlog());
				}
			}
			if( StringUtils.isNotBlank(univHpMngrForm.getSnsHp()) ){
				if( !univHpMngrForm.getSnsHp().startsWith("http://") && !univHpMngrForm.getSnsHp().startsWith("https://") ){
					univHpMngrForm.setSnsHp("http://" + univHpMngrForm.getSnsHp());
				}
			}
			if( StringUtils.isNotBlank(univHpMngrForm.getSnsInsta()) ){
				if( !univHpMngrForm.getSnsInsta().startsWith("http://") && !univHpMngrForm.getSnsInsta().startsWith("https://") ){
					univHpMngrForm.setSnsInsta("http://" + univHpMngrForm.getSnsBlog());
				}
			}
			if( StringUtils.isNotBlank(univHpMngrForm.getSnsKakao()) ){
				if( !univHpMngrForm.getSnsKakao().startsWith("http://") && !univHpMngrForm.getSnsKakao().startsWith("https://") ){
					univHpMngrForm.setSnsKakao("http://" + univHpMngrForm.getSnsHp());
				}
			}
			univHpMngrForm.setUpdId(currentAdmin.getAdminId());
			univHpMngrForm.setUpdNm(currentAdmin.getAdminName());
			
			//System.out.println("oldUnivId: "+ univHpMngrForm.getOldUnivId());
			//System.out.println("univId: "+ univHpMngrForm.getUnivId());
			int result = 0;
			result = univHpMngrService.updateUnivHpMngr(univHpMngrForm);
			
			redirectAttributes.addFlashAttribute("updated", true);
			return "redirect:" + AsaproUtils.getAdminPath(currentSite)+"/univHpMngr/update.do?univId="+univHpMngrForm.getUnivId();
		}
	}
	
	
	/**
	 * 대학 ID 등록 중복 체크 
	 * @param model
	 * @param univId
	 * @return
	 */
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/univHpMngr/idCheck/jsonResultGet.do", method=RequestMethod.GET)
	@ResponseBody
	public Integer univIdCheckJsonResultGet(Model model, @RequestParam(value="univId", required=true) String univId){
		
		//System.out.println("UnivHpMngrAdminController.java.univIdCheckJsonResultGet()");
		//System.out.println("univId: " + univId);
		int result = 0;
		UnivHpMngrSearch univHpMngrSearch = new UnivHpMngrSearch();
		univHpMngrSearch.setUnivId(univId);
		univHpMngrSearch.setDelYn("N");
		UnivHpMngr univHpMngr = univHpMngrService.getUnivHpMngrInfo(univHpMngrSearch);
		
		if(null != univHpMngr){
			result = 1;
			//System.out.println("ID (○) : ID 중복.");
			model.addAttribute("univId", univId);
		}else{
			result = 0;
			//System.out.println("ID (×) : ID 등록 가능!");
		}
		
		return result;
	}	
		
	
	/**
	 * 대학 부서목록을 레이어 팝업창 뷰로 반환한다.
	 * @param model
	 * @param currentSite
	 * @param currentAdmin
	 * @param departmentSearch
	 * @return 부서목록 뷰
	 */
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/univHpMngr/searchUnivLayer.do", method=RequestMethod.GET)
	public String departmentListSearchLayerGet(Model model, @CurrentSite Site currentSite, @CurrentAdmin AdminMember currentAdmin, @ModelAttribute("departmentSearch") DepartmentSearch departmentSearch){
		
		departmentSearch.fixBrokenSvByDefaultCharsets();
		departmentSearch.setPaging(true);
		departmentSearch.setOrgId("university");
		model.addAttribute("srcOrgId", departmentSearch.getOrgId());
		
		List<Department> list = organizationService.getDepartmentList(departmentSearch);
		int total = organizationService.getDepartmentListTotal(departmentSearch);
		Paging paging = new Paging(list, total, departmentSearch);
		
		model.addAttribute("paging", paging);
		
		//사용중인 기관목록
		OrganizationSearch organizationSearch = new OrganizationSearch();
		organizationSearch.setPaging(false);
		//organizationSearch.setOrgUse(true);
		organizationSearch.setDefaultSort("ORG_NAME", "ASC");
		
		List<Organization> organizationList = organizationService.getOrganizationList(organizationSearch);
		
		model.addAttribute("organizationList", organizationList);
		
		return "asapro/admin/campustown/univHpMngr/searchUnivLayer";
	}
	
	/**
	 * 대학 홈페이지 정보를 삭제한다. 
	 * @param model
	 * @param currentSite
	 * @param univIds
	 * @return
	 * @throws AsaproException
	 * @throws IOException
	 */
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/univHpMngr/delete.do", method=RequestMethod.POST)
	@ResponseBody
	public ServerMessage deleteUnivHpMngrPost(Model model, @CurrentSite Site currentSite, @RequestParam(value="univIds[]", required=true) String[] univIds
											  , @CurrentAdmin AdminMember currentAdmin) throws AsaproException, IOException{
		
		ServerMessage serverMessage = new ServerMessage();
		
		if( ArrayUtils.isEmpty(univIds) ){
			serverMessage.setSuccess(false);
			serverMessage.setText("삭제할 항목이 없습니다.");
		} else {
			List<UnivHpMngr> univHpList = new ArrayList<UnivHpMngr>();
			UnivHpMngr univHp = null;
			
			for( String univId : univIds){
				univHp = new UnivHpMngr();
				univHp.setSitePrefix(currentSite.getSitePrefix());
				univHp.setUnivId(univId);
				univHp.setDelId(currentAdmin.getAdminId());
				univHp.setDelNm(currentAdmin.getAdminName());
				univHpList.add(univHp);
				
			}
			
			boolean delFlag = false;
			for(int i =0; i<univHpList.size(); i++){
				StartHpMngrSearch startHpMngrSearch = new StartHpMngrSearch();
				startHpMngrSearch.setUnivId(univHpList.get(i).getUnivId());
				
				List<StartHpMngr> startHpList = startHpMngrService.getStartHpMngrList(startHpMngrSearch);
				
				if(startHpList.size() > 0 ){
					serverMessage.setText("현재 등록되어 있는 창업팀이 있습니다. \n창업팀 홈페이지 정보 삭제후 이용해주세요.");
					break;
				}
				delFlag = true;
			}
			int result = 0;
			if(delFlag == true){
				result = univHpMngrService.deleteUnivHpMngr(univHpList);
				
				if(result > 0){
					serverMessage.setSuccess(true);
					serverMessage.setSuccessCnt(result);
				} else {
					serverMessage.setSuccess(false);
					serverMessage.setText("삭제하지 못하였습니다.");
				}
			}	
		}
		return serverMessage;
	}
	
	/**
	 * 캠퍼스타운명 수정
	 * @param model
	 * @param currentSite
	 * @param currentAdmin
	 * @param univId
	 * @param univNm
	 * @return
	 */
	@RequestMapping(value=Constant.SITE_ID_PATH + Constant.ADMIN_PATH + "/univHpMngr/nmCheck/jsonU.do", method=RequestMethod.GET)
	@ResponseBody
	public Integer updateUnivNmUpdInfo(Model model
			, @CurrentSite Site currentSite
			, @CurrentAdmin AdminMember currentAdmin
			
			, @RequestParam(value="univId", required=true) String univId
			, @RequestParam(value="univNm", required=true) String univNm
			){
		//System.out.println("UnivHpMngrAdminController.updateUnivNmUpdInfo()");
		//System.out.println("univId: " + univId);
		//System.out.println("univNm: " + univNm);
		
		UnivHpMngr univHpMngrInfo = new UnivHpMngr();
		univHpMngrInfo.setUnivId(univId);
		univHpMngrInfo.setUnivNm(univNm);
		univHpMngrInfo.setUpdId(currentAdmin.getAdminId());
		univHpMngrInfo.setUpdNm(currentAdmin.getAdminName());
		int result = 0;
		
		result = univHpMngrService.updateUnivHpMngr(univHpMngrInfo);
		if(result > 0){
			//System.out.println("update 성공!");
			
			Department department = new Department();
			department.setDepId(univId);
			department.setDepName(univNm);
			result = univHpMngrService.updateDepNm(department);
			if(result > 0){
				//System.out.println("두개 다 업뎃 성공~!!");
			}else{
				//System.out.println("하나만 업뎃 성공~!!");
			}
			
		}else{
			//System.out.println("update 실패!");
		}
		
		return result;
	}
}
