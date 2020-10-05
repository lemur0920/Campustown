package egovframework.com.campustown.policyCfrnc.service.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import egovframework.com.asapro.fileinfo.service.FileInfo;
import egovframework.com.asapro.fileinfo.service.FileInfoService;
import egovframework.com.asapro.fileinfo.service.FileInfoUploadResult;
import egovframework.com.asapro.support.Constant;
import egovframework.com.asapro.support.exception.AsaproException;
import egovframework.com.asapro.support.util.AsaproUtils;
import egovframework.com.campustown.policyCfrnc.service.PolicyCfrnc;
import egovframework.com.campustown.policyCfrnc.service.PolicyCfrncMapper;
import egovframework.com.campustown.policyCfrnc.service.PolicyCfrncSearch;
import egovframework.com.campustown.policyCfrnc.service.PolicyCfrncService;
import egovframework.com.campustown.startHpMngr.service.StartHpMngr;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

/**
 * 정책협의회 관리 관리자 서비스 구현
 * @author jaseo
 * @since 2020. 03. 18.
 */
@Service
public class PolicyCfrncServiceImpl extends EgovAbstractServiceImpl implements PolicyCfrncService{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(PolicyCfrncServiceImpl.class);
	
	
	@Autowired
	private PolicyCfrncMapper policyCfrncMapper;
	
	@Autowired
	private HttpServletRequest request;
	
	@Autowired
	private FileInfoService fileInfoService;
	
	
	/**
	 * 정책협의회 기본 정보 조회
	 */
	@Override
	public List<PolicyCfrnc> getPolicyCfrnMngrList(PolicyCfrncSearch policyCfrncSearch) {
		return policyCfrncMapper.selectPolicyCfrnMngrList(policyCfrncSearch);
	}
	
	/**
	 * 정책협의회 기본 정보 수 조회
	 */
	@Override
	public int getPolicyCfrnMngrListTotal(PolicyCfrncSearch policyCfrncSearch) {
		return policyCfrncMapper.selectPolicyCfrnMngrListTotal(policyCfrncSearch);
	}

	/**
	 * 정책협의회 정보 중 구분값 리스트 조회
	 */
	@Override
	public List<PolicyCfrnc> getPolicyCfrnDiv() {
		return policyCfrncMapper.selectPolicyCfrnDiv();
	}
	
	/**
	 * 구분 최대 차수 가져오기
	 */
	@Override
	public int getMaxMngOrder() {
		return policyCfrncMapper.selectMaxMngOrder();
	}
	
	/**
	 * 관리번호 최대 차수 가져오기
	 */
	@Override
	public int getMaxMngNoOrder(PolicyCfrnc policyCfrncForm) {
		return policyCfrncMapper.selectMaxMngNoOrder(policyCfrncForm);
	}

	/**
	 * 정책협의회 관리 등록
	 * @throws IOException 
	 * @throws AsaproException 
	 */
	@Override
	public Map<String, FileInfoUploadResult> insertpolicyCfrncMngr(PolicyCfrnc policyCfrncForm) throws AsaproException, IOException {
		// TODO Auto-generated method stub
		
		//Map<String, FileInfoUploadResult> fileInfoUploadResultMap = archiveService.insertArchive(policyForm);
		
		Map<String, FileInfoUploadResult> fileInfoUploadResultMap = new HashMap<String, FileInfoUploadResult>();
		//FileInfoUploadResult thumbFileInfoUploadResult = new FileInfoUploadResult();
		FileInfoUploadResult fileInfoUploadResult = new FileInfoUploadResult();
		//첨부가능한 확장자
		String uploadWhiteList = "";
		
		// 정책협의회 관리 등록
		policyCfrncMapper.insertpolicyCfrncMngr(policyCfrncForm);
		
		//첨부파일 처리
		if( policyCfrncForm.getPoMultipartFiles() != null && !policyCfrncForm.getPoMultipartFiles().isEmpty() ){
			int size = policyCfrncForm.getPoMultipartFiles().size();
			MultipartFile multipartFile = null;
			String poAltText = "";
			for( int i = 0; i<size; i++ ){
				multipartFile = policyCfrncForm.getPoMultipartFiles().get(i);
				poAltText = policyCfrncForm.getManageNo()+":첨부파일"+"("+i+")";
				//첨부파일의 정보와 파일을 저장
				this.saveMultipartFile(multipartFile, poAltText, policyCfrncForm, fileInfoUploadResult, "", "appending");
			}
		}
				
		fileInfoUploadResultMap.put("fileInfoUploadResult", fileInfoUploadResult);
		return fileInfoUploadResultMap;
	}
	
	/**
	 * 첨부파일의 정보와 파일을 저장한다.
	 * @param multipartFile
	 * @param poAltText
	 * @param policyCfrncForm
	 * @param fileInfoUploadResult
	 * @param policyCfrncForm2
	 * @param string
	 * @param string2
	 */
	private FileInfo saveMultipartFile(MultipartFile multipartFile, String poAltText, PolicyCfrnc policyCfrncForm, FileInfoUploadResult fileInfoUploadResult
			, String uploadWhiteList, String attachmentType) 
			throws AsaproException, IOException	{
		
		String webRoot = AsaproUtils.getWebRoot(request);
		uploadWhiteList = Constant.UPLOAD_IMAGE_WHITE_LIST; 
		
		//첨부된 파일 개수
		FileInfo fileInfo = new FileInfo();
		fileInfo.setSitePrefix(policyCfrncForm.getSitePrefix());
		//모듈1차pk
		fileInfo.setFileModule("policyCfrnc");
		//fileInfo.setFileModuleId(policyCfrncForm.getMngSeq());
		//모듈2차pk
		fileInfo.setFileModuleSub("policyArticle");
		fileInfo.setFileModuleSubId(String.valueOf(policyCfrncForm.getMngSeq()));
		//멤버
		fileInfo.setMemberId(policyCfrncForm.getRegId());
		//첨부유형
		fileInfo.setFileAttachmentType(attachmentType);
		
		//대체텍스트
		if( StringUtils.isNotBlank(poAltText) ){
			//혹시 길이 안맞게 입력된 경우 - 아마 없겠지만 - 예외처리해줌.
			try{
				//alt에 "(쌍따옴표)가 들어 있는 경우가 가끔있는데 그런 경우 html이 깨짐 그래서 제거.escape시켜도 되긴 되는데 걍 깔끔하게 제거하겠음.
				fileInfo.setFileAltText( Jsoup.clean(poAltText, Whitelist.none()).replace("\"", "") );
			} catch (IndexOutOfBoundsException iobe){
				LOGGER.error("[IndexOutOfBoundsException] Try/Catch... : "+ iobe.getMessage());
				//System.out.println("abababab~~~");
				fileInfo.setFileAltText( "" );
			}
		}
		else{
			String fileNameTemp = FilenameUtils.getBaseName(multipartFile.getOriginalFilename());
			fileInfo.setFileAltText( Jsoup.clean(fileNameTemp, Whitelist.none()).replace("\"", "") );
		}

		fileInfoService.saveFile(multipartFile, webRoot, fileInfo, (Constant.MEGA * policyCfrncForm.getPoUploadSizeMax()), false, 400, 300, false, uploadWhiteList);
		if( !fileInfo.isFileUploadSuccess() ){
			fileInfoUploadResult.addErrorInfo(fileInfo);
		}
		return fileInfo;
		
	}

	/**
	 * 정책협의회 관리 정보 조회 (단일 조회)
	 */
	@Override
	public PolicyCfrnc getPolicyCfrnMngrInfo(PolicyCfrncSearch policyCfrncSearch) {
		return policyCfrncMapper.selectPolicyCfrnMngrInfo(policyCfrncSearch);
	}
	
	/**
	 * 정책협의회 관리 정보 수정
	 */
	@Override
	public Map<String, FileInfoUploadResult> updatePolicyMngr(PolicyCfrnc policyCfrncForm) throws AsaproException, IOException{
		
		Map<String, FileInfoUploadResult> fileInfoUploadResultMap = new HashMap<String, FileInfoUploadResult>();
		FileInfoUploadResult fileInfoUploadResult = new FileInfoUploadResult();
		
		//첨부가능한 확장자
		String uploadWhiteList = "";
		
		//policyCfrncForm.setManageNoOrdr(policyCfrncForm.getManageNoOrdr());
		//policyCfrncForm.setManageNo(policyCfrncForm.getManageYear()+"-"+String.format("%03d", policyCfrncForm.getManageOrdr())+"-"+policyCfrncForm.getManageNoOrdr());
		// 정책협의회 관리 수정
		int manageYear = policyCfrncForm.getManageYear();
		policyCfrncForm.setManageYear(manageYear);
		
		int manageOrdr = policyCfrncForm.getManageOrdr();
		policyCfrncForm.setManageOrdr(manageOrdr);
		
		policyCfrncMapper.updatePolicyCfrncMngr(policyCfrncForm);
		
		//삭제파일 처리
		if( policyCfrncForm.getFileInfoDeleteIds() != null && !policyCfrncForm.getFileInfoDeleteIds().isEmpty() ){
			FileInfo deleteFileInfo = null;
			for( Integer fileId : policyCfrncForm.getFileInfoDeleteIds() ){
				deleteFileInfo = new FileInfo();
				deleteFileInfo.setSitePrefix(policyCfrncForm.getSitePrefix());
				deleteFileInfo.setFileId(fileId);
				fileInfoService.deleteFileInfo(deleteFileInfo);
			}
		}
		
		//첨부파일 처리
		if( policyCfrncForm.getPoMultipartFiles() != null && !policyCfrncForm.getPoMultipartFiles().isEmpty() ){
			int size = policyCfrncForm.getPoMultipartFiles().size();
			MultipartFile multipartFile = null;
			String poAltText = "";
			for( int i = 0; i<size; i++ ){
				multipartFile = policyCfrncForm.getPoMultipartFiles().get(i);
				if( policyCfrncForm.getPoAltTexts() != null && !policyCfrncForm.getPoAltTexts().isEmpty() ){
					poAltText = policyCfrncForm.getPoAltTexts().get(i);
				}
				poAltText = policyCfrncForm.getManageNo()+":첨부파일"+"("+i+")";
				//첨부파일의 정보와 파일을 저장
				this.saveMultipartFile(multipartFile, poAltText, policyCfrncForm, fileInfoUploadResult, "", "appending");
			}
		}
				
		fileInfoUploadResultMap.put("fileInfoUploadResult", fileInfoUploadResult);
		return fileInfoUploadResultMap;
	}
	
	/**
	 * MANAGE_ORDR = 1인 MANAGE_YEAR 조회
	 */
	@Override
	public int getMinMngrYear() {
		return policyCfrncMapper.selectMinMngrYear();
	}
	
	/**
	 * 정책협의회 통계 리스트 조회
	 */
	@Override
	public List<PolicyCfrnc> getPolicyCfrnStatList(PolicyCfrncSearch policyCfrncSearch) {
		return policyCfrncMapper.selectPolicyCfrnStatList(policyCfrncSearch);
	}
	
	/**
	 * 정책협의회 통계 리스트 수 조회
	 */
	@Override
	public int getPolicyCfrnStatListTotal(PolicyCfrncSearch policyCfrncSearch) {
		return policyCfrncMapper.selectPolicyCfrnStatListTotal(policyCfrncSearch);
	}
	
	/**
	 * 추진 정보 등록
	 */
	@Override
	public int insertPolicyMngrDtl(PolicyCfrnc policyCfrncDtlForm) {
		return policyCfrncMapper.insertPolicyMngrDtl(policyCfrncDtlForm);
	}

	/**
	 * 정책협의회 상세 테이블에서 정책협의회 MNG_SEQ를 FK로 가지면서 max DTL_SEQ 를 가져온다.
	 */
	@Override
	public int getPolicyMaxDtlSeq(PolicyCfrncSearch policyCfrncSearch) {
		return policyCfrncMapper.selectPolicyMaxDtlSeq(policyCfrncSearch);
	}
	
	/**
	 * 추진 정보 조회 (단건)
	 */
	@Override
	public PolicyCfrnc getPolicyCfrnDtlInfo(PolicyCfrncSearch policyCfrncSearch) {
		return policyCfrncMapper.selectPolicyCfrnDtlInfo(policyCfrncSearch);
	}
	
	/**
	 * 추진 정보 조회
	 */
	@Override
	public List<PolicyCfrnc> getPolicyCfrnDtlInfoList(PolicyCfrncSearch policyCfrncSearch2) {
		return policyCfrncMapper.selectPolicyCfrnDtlInfoList(policyCfrncSearch2);
	}
	
	/**
	 * 추진 정보 수정
	 */
	@Override
	public int updatePolicyCfrnDtlInfo(PolicyCfrnc policyCfrnc) {
		return policyCfrncMapper.updatePolicyCfrnDtlInfo(policyCfrnc);
	}
	
	/**
	 * 추진 정보 삭제
	 */
	@Override
	public int deletePolicyCfrnDtlInfo(PolicyCfrnc policyCfrnc) {
		return policyCfrncMapper.deletePolicyCfrnDtlInfo(policyCfrnc);
	}
	
	/**
	 * 정책협의회관리 정보를 삭제한다.
	 */
	@Override
	public int deletePolicyCfrnc(List<PolicyCfrnc> policyCfrncList) {
		int result = 0;
		for(PolicyCfrnc policyCfrnc : policyCfrncList){
			result += this.deletePolicyCfrnc(policyCfrnc);
		}
		return result;
	}
	
	/**
	 * 정책협의회관리 정보를 삭제한다.
	 * @param policyCfrnc
	 * @return
	 */
	private int deletePolicyCfrnc(PolicyCfrnc policyCfrnc) {
		int result = policyCfrncMapper.deletePolicyCfrnc(policyCfrnc);
		
		return result;
	}
	
	
	/**
	 * 정책협의회 관리 등록
	 */
	/*
	@Override
	public int insertpolicyCfrncMngr(PolicyCfrnc policyCfrncForm) {
		return policyCfrncMapper.insertpolicyCfrncMngr(policyCfrncForm);
	}
	*/
	
	

}
