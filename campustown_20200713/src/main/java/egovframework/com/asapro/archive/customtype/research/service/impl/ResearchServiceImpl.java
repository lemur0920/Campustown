/**
 * 
 */
package egovframework.com.asapro.archive.customtype.research.service.impl;

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
import egovframework.com.asapro.archive.customtype.research.service.Research;
import egovframework.com.asapro.archive.customtype.research.service.ResearchMapper;
import egovframework.com.asapro.archive.customtype.research.service.ResearchSearch;
import egovframework.com.asapro.archive.customtype.research.service.ResearchService;
import egovframework.com.asapro.archive.service.Archive;
import egovframework.com.asapro.archive.service.ArchiveCategory;
import egovframework.com.asapro.archive.service.ArchiveCategorySearch;
import egovframework.com.asapro.archive.service.ArchiveMapper;
import egovframework.com.asapro.archive.service.ArchiveSearch;
import egovframework.com.asapro.archive.service.ArchiveService;
import egovframework.com.asapro.board.service.BoardArticle;
import egovframework.com.asapro.board.service.BoardConfig;
import egovframework.com.asapro.fileinfo.service.FileInfo;
import egovframework.com.asapro.fileinfo.service.FileInfoService;
import egovframework.com.asapro.fileinfo.service.FileInfoUploadResult;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

/**
 * 연구자료 관리 서비스 구현
 * @author yckim
 * @since 2019. 2. 26.
 *
 */
@Service
public class ResearchServiceImpl extends EgovAbstractServiceImpl implements ResearchService{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ResearchServiceImpl.class);
	
	@Autowired
	private ArchiveService archiveService;
	
	@Autowired
	private ResearchMapper researchMapper;

	@Autowired
	private HttpServletRequest request;
	
	@Autowired
	private FileInfoService fileInfoService;

		
	//=============================================================================================================================
	//==========================================  연구자료    ================================================================
	//=============================================================================================================================
	
	/**
	 * 연구자료 목록을 조회한다.
	 */
	@Override
	public List<Research> getResearchList(ResearchSearch researchSearch) {
		List<Research> list = researchMapper.selectResearchList(researchSearch);
		return list;
	}
	
	/**
	 * 연구자료 목록 토탈을 조회한다.
	 */
	@Override
	public int getResearchListTotal(ResearchSearch researchSearch) {
		int total = researchMapper.selectResearchListTotal(researchSearch);
		return total;
	}

	/**
	 * 연구자료를 등록한다.
	 * @throws IOException 
	 * @throws AsaproException 
	 */
	@Override
	public Map<String, FileInfoUploadResult> insertResearch(Research researchForm) throws AsaproException, IOException {
		// archive 기본데이터 등록
		Map<String, FileInfoUploadResult> fileInfoUploadResultMap = archiveService.insertArchive(researchForm);
		
		// 커스텀 아카이브 데이터 research 등록
		int inserted = researchMapper.insertResearch(researchForm);
		
		
		return fileInfoUploadResultMap;
	}

	/**
	 * 연구자료를 조회한다.
	 */
	@Override
	public Research getResearch(Research researchForm) {
		Research research = researchMapper.selectResearch(researchForm);
		return research;
	}

	/**
	 * 연구자료를 수정한다.
	 * @throws IOException 
	 * @throws AsaproException 
	 */
	@Override
	public Map<String, FileInfoUploadResult> updateResearch(Research researchForm) throws AsaproException, IOException {
		// archive 기본데이터 수정
		Map<String, FileInfoUploadResult> fileInfoUploadResultMap = archiveService.updateArchive(researchForm);
		
		// 커스텀 아카이브 데이터 research 수정
		int updated = researchMapper.updateResearch(researchForm);
		
		
		return fileInfoUploadResultMap;
	}

}
