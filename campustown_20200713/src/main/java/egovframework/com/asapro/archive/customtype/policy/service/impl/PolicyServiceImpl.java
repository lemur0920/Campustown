/**
 * 
 */
package egovframework.com.asapro.archive.customtype.policy.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import egovframework.com.asapro.support.Constant;
import egovframework.com.asapro.support.exception.AsaproException;
import egovframework.com.asapro.support.util.AsaproUtils;
import egovframework.com.asapro.support.util.EtcUtil;
import egovframework.com.asapro.support.annotation.ArchiveItem;
import egovframework.com.asapro.archive.customtype.policy.service.Policy;
import egovframework.com.asapro.archive.customtype.policy.service.PolicyMapper;
import egovframework.com.asapro.archive.customtype.policy.service.PolicySearch;
import egovframework.com.asapro.archive.customtype.policy.service.PolicyService;
import egovframework.com.asapro.archive.service.Archive;
import egovframework.com.asapro.archive.service.ArchiveCategory;
import egovframework.com.asapro.archive.service.ArchiveCategorySearch;
import egovframework.com.asapro.archive.service.ArchiveMapper;
import egovframework.com.asapro.archive.service.ArchiveSearch;
import egovframework.com.asapro.archive.service.ArchiveService;
import egovframework.com.asapro.board.service.BoardArticle;
import egovframework.com.asapro.board.service.BoardConfig;
import egovframework.com.asapro.content_statis.service.ContentStatisSearch;
import egovframework.com.asapro.fileinfo.service.FileInfo;
import egovframework.com.asapro.fileinfo.service.FileInfoService;
import egovframework.com.asapro.fileinfo.service.FileInfoUploadResult;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

/**
 * 정책자료 관리 서비스 구현
 * @author yckim
 * @since 2019. 2. 26.
 *
 */
@Service
public class PolicyServiceImpl extends EgovAbstractServiceImpl implements PolicyService{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(PolicyServiceImpl.class);
	
	@Autowired
	private ArchiveService archiveService;
	
	@Autowired
	private PolicyMapper policyMapper;

	@Autowired
	private HttpServletRequest request;
	
	@Autowired
	private FileInfoService fileInfoService;

		
	//=============================================================================================================================
	//==========================================  정책자료    ================================================================
	//=============================================================================================================================
	
	/**
	 * 정책자료 목록을 조회한다.
	 */
	@Override
	public List<Policy> getPolicyList(PolicySearch policySearch) {
		List<Policy> list = policyMapper.selectPolicyList(policySearch);
		return list;
	}
	
	/**
	 * 정책자료 목록 토탈을 조회한다.
	 */
	@Override
	public int getPolicyListTotal(PolicySearch policySearch) {
		int total = policyMapper.selectPolicyListTotal(policySearch);
		return total;
	}

	/**
	 * 정책자료를 등록한다.
	 * @throws IOException 
	 * @throws AsaproException 
	 */
	@Override
	public Map<String, FileInfoUploadResult> insertPolicy(Policy policyForm) throws AsaproException, IOException {
		// archive 기본데이터 등록
		Map<String, FileInfoUploadResult> fileInfoUploadResultMap = archiveService.insertArchive(policyForm);
		
		// 커스텀 아카이브 데이터 policy 등록
		int inserted = policyMapper.insertPolicy(policyForm);
		
		
		return fileInfoUploadResultMap;
	}

	/**
	 * 정책자료를 조회한다.
	 */
	@Override
	public Policy getPolicy(Policy policyForm) {
		Policy policy = policyMapper.selectPolicy(policyForm);
		return policy;
	}

	/**
	 * 정책자료를 수정한다.
	 * @throws IOException 
	 * @throws AsaproException 
	 */
	@Override
	public Map<String, FileInfoUploadResult> updatePolicy(Policy policyForm) throws AsaproException, IOException {
		// archive 기본데이터 수정
		Map<String, FileInfoUploadResult> fileInfoUploadResultMap = archiveService.updateArchive(policyForm);
		
		// 커스텀 아카이브 데이터 policy 수정
		int updated = policyMapper.updatePolicy(policyForm);
		
		
		return fileInfoUploadResultMap;
	}

	/**
	 * 추천 기간내 베스트 정책 목록을 반환한다.
	 */
	@Override
	public List<Policy> getRecommendTermBestPolicyList(ContentStatisSearch contentStatisSearch) {
		List<Policy> list = policyMapper.selectRecommendTermBestPolicyList(contentStatisSearch);
		return list;
	}

}
