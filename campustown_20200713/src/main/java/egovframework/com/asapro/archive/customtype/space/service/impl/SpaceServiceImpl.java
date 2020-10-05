/**
 * 
 */
package egovframework.com.asapro.archive.customtype.space.service.impl;

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
import egovframework.com.asapro.archive.customtype.space.service.Space;
import egovframework.com.asapro.archive.customtype.space.service.SpaceMapper;
import egovframework.com.asapro.archive.customtype.space.service.SpaceSearch;
import egovframework.com.asapro.archive.customtype.space.service.SpaceService;
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
 * 공간정보 관리 서비스 구현
 * @author yckim
 * @since 2019. 2. 26.
 *
 */
@Service
public class SpaceServiceImpl extends EgovAbstractServiceImpl implements SpaceService{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(SpaceServiceImpl.class);
	
	@Autowired
	private ArchiveService archiveService;
	
	@Autowired
	private SpaceMapper spaceMapper;

	@Autowired
	private HttpServletRequest request;
	
	@Autowired
	private FileInfoService fileInfoService;

		
	//=============================================================================================================================
	//==========================================  공간정보    ================================================================
	//=============================================================================================================================
	
	/**
	 * 공간정보 목록을 조회한다.
	 */
	@Override
	public List<Space> getSpaceList(SpaceSearch spaceSearch) {
		List<Space> list = spaceMapper.selectSpaceList(spaceSearch);
		return list;
	}
	
	/**
	 * 공간정보 목록 토탈을 조회한다.
	 */
	@Override
	public int getSpaceListTotal(SpaceSearch spaceSearch) {
		int total = spaceMapper.selectSpaceListTotal(spaceSearch);
		return total;
	}

	/**
	 * 공간정보를 등록한다.
	 * @throws IOException 
	 * @throws AsaproException 
	 */
	@Override
	public Map<String, FileInfoUploadResult> insertSpace(Space spaceForm) throws AsaproException, IOException {
		// archive 기본데이터 등록
		Map<String, FileInfoUploadResult> fileInfoUploadResultMap = archiveService.insertArchive(spaceForm);
		
		// 커스텀 아카이브 데이터 space 등록
		int inserted = spaceMapper.insertSpace(spaceForm);
		
		
		return fileInfoUploadResultMap;
	}

	/**
	 * 공간정보를 조회한다.
	 */
	@Override
	public Space getSpace(Space spaceForm) {
		Space space = spaceMapper.selectSpace(spaceForm);
		return space;
	}

	/**
	 * 공간정보를 수정한다.
	 * @throws IOException 
	 * @throws AsaproException 
	 */
	@Override
	public Map<String, FileInfoUploadResult> updateSpace(Space spaceForm) throws AsaproException, IOException {
		// archive 기본데이터 수정
		Map<String, FileInfoUploadResult> fileInfoUploadResultMap = archiveService.updateArchive(spaceForm);
		
		// 커스텀 아카이브 데이터 space 수정
		int updated = spaceMapper.updateSpace(spaceForm);
		
		
		return fileInfoUploadResultMap;
	}

}
