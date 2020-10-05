/**
 * 
 */
package egovframework.com.asapro.board.service.impl;

import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.text.StringEscapeUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import egovframework.com.asapro.board.service.BoardArticle;
import egovframework.com.asapro.board.service.BoardArticleSearch;
import egovframework.com.asapro.board.service.BoardConfig;
import egovframework.com.asapro.board.service.BoardConfigSearch;
import egovframework.com.asapro.board.service.BoardMapper;
import egovframework.com.asapro.board.service.BoardService;
//import egovframework.com.asapro.comment.service.CommentMapper;
//import egovframework.com.asapro.comment.service.CommentSearch;
import egovframework.com.asapro.fileinfo.service.FileInfo;
import egovframework.com.asapro.fileinfo.service.FileInfoService;
import egovframework.com.asapro.fileinfo.service.FileInfoUploadResult;
import egovframework.com.asapro.support.Constant;
import egovframework.com.asapro.support.exception.AsaproException;
import egovframework.com.asapro.support.util.AsaproUtils;
import egovframework.com.asapro.support.util.EtcUtil;
import egovframework.com.asapro.member.Member;
import egovframework.com.asapro.member.admin.service.AdminMember;
import egovframework.com.asapro.member.user.service.UserMember;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import egovframework.rte.fdl.cryptography.EgovPasswordEncoder;

/**
 * 게시판 서비스 구현
 * @author yckim
 * @since 2018. 5. 31.
 *
 */
@Service
public class BoardServiceImpl extends EgovAbstractServiceImpl implements BoardService{

	@Autowired
	private FileInfoService fileInfoService;
	private static final Logger LOGGER = LoggerFactory.getLogger(BoardServiceImpl.class);
	
//	@Autowired
//	private CommentMapper commentMapper;  
	
	@Autowired
	private BoardMapper boardMapper;
	
	@Autowired
	private HttpServletRequest request;
	
	@Autowired
	private EgovPasswordEncoder egovPasswordEncoder;
	
	
	//======================================================================
	//========================   게시판     ===============================
	//======================================================================
	/**
	 * 게시판 목록
	 */
	@Override
	public List<BoardConfig> getBoardConfigList(BoardConfigSearch boardConfigSearch) {
		return boardMapper.selectBoardConfigList(boardConfigSearch);
	}
	
	/**
	 * 게시판 목록 토탈
	 */
	@Override
	public int getBoardConfigListTotal(BoardConfigSearch boardConfigSearch) {
		return boardMapper.selectBoardConfigListTotal(boardConfigSearch);
	}

	/**
	 * 게시판 설정 조회
	 */
	@Override
	public BoardConfig getBoardConfig(BoardConfig boardConfig) {
		return boardMapper.selectBoardConfig(boardConfig);
	}
	
	/**
	 * 게시판 추가
	 */
	@Override
	public int insertBoardConfig(BoardConfig boardConfig) {
		this.sanitizeStringValue(boardConfig);
		return boardMapper.insertBoardConfig(boardConfig);
	}
	
	/**
	 * 제목, 카테고리명 에서 html 제거 특수분자 이스케이처리
	 * @param boardConfig
	 */
	private void sanitizeStringValue(BoardConfig boardConfig){
		////clean HTML using none whitelist (remove all HTML tags)
		boardConfig.setBcName( Jsoup.clean(boardConfig.getBcName(), Whitelist.none()) );
		if( StringUtils.isNotBlank(boardConfig.getBcCategory1Name()) ){
			boardConfig.setBcCategory1Name( StringEscapeUtils.escapeHtml4(Jsoup.clean(boardConfig.getBcCategory1Name(), Whitelist.none())) );
		}
		if( StringUtils.isNotBlank(boardConfig.getBcCategory2Name()) ){
			boardConfig.setBcCategory2Name( StringEscapeUtils.escapeHtml4(Jsoup.clean(boardConfig.getBcCategory2Name(), Whitelist.none())) );
		}
		if( StringUtils.isNotBlank(boardConfig.getBcCategory3Name()) ){
			boardConfig.setBcCategory3Name( StringEscapeUtils.escapeHtml4(Jsoup.clean(boardConfig.getBcCategory3Name(), Whitelist.none())) );
		}
	}
	
	/**
	 * 게시판 유형 목록
	 */
	@Override
	public List<String> getbcTypeList() throws FileNotFoundException {
		
		String webRoot = AsaproUtils.getWebRoot(request);
		File bcTypeDir = new File(webRoot + Constant.THEME_ROOT + request.getAttribute("theme") + "/board");

		File[] bcTypeDirs = bcTypeDir.listFiles(new FileFilter() {
			@Override
			public boolean accept(File file) {
				return file.isDirectory() && !file.getName().startsWith(".");
			}
		});
		
		List<String> bcTypeList = new ArrayList<String>();
		if(ArrayUtils.isNotEmpty(bcTypeDirs) ){
			for( File f : bcTypeDirs ){
				bcTypeList.add(f.getName());
			}
		}
		
		Collections.sort(bcTypeList, new Comparator<String>() {
			@Override
			public int compare(String s1, String s2) {
				if(s1 != null && s2 != null){
					if( "default".equals(s1) ){
						return -1;
					} else {
						return 1; 
					}
				} else {
					return -1;
				}
			}
		});
		
		return bcTypeList;
	}

	/**
	 * 게시판을 수정한다.
	 */
	@Override
	public int updateBoardConfig(BoardConfig boardConfig) {
		this.sanitizeStringValue(boardConfig);
		return boardMapper.updateBoardConfig(boardConfig);
	}

	/**
	 * 게시판을 삭제한하다.
	 */
	@Override
	public int deleteBoardConfig(List<BoardConfig> boardConfigList) {
		int result = 0;
		for( BoardConfig boardConfig : boardConfigList ){
			result += this.deleteBoardConfig(boardConfig);
		}
		return result;
	}

	/**
	 * 게시판을 삭제한다.
	 */
	@Override
	public int deleteBoardConfig(BoardConfig boardConfig) {
		int result = boardMapper.deleteBoardConfig(boardConfig);
		
		if(result > 0){
			
			//게시물 삭제
			//게시물 삭제할때 첨부파일, 대표이미지, 댓글도 삭제
			BoardArticleSearch boardArticleSearch = new BoardArticleSearch();
			boardArticleSearch.setSitePrefix(boardConfig.getSitePrefix());
			boardArticleSearch.setBcId(boardConfig.getBcId());
			boardArticleSearch.setPaging(false);
			List<BoardArticle> boardArticleList = boardMapper.selectBoardArticleList(boardArticleSearch);
			for( BoardArticle ba : boardArticleList ){
				ba.setSitePrefix(boardConfig.getSitePrefix());
				this.deleteBoardArticle(ba);
			}
			
		}
		return result;
	}
	
	
	//======================================================================
	//========================   게시글     ===============================
	//======================================================================

	/**
	 * 게시물 목록
	 */
	@Override
	public List<BoardArticle> getBoardArticleList(BoardArticleSearch boardArticleSearch) {
		return boardMapper.selectBoardArticleList(boardArticleSearch);
	}

	/**
	 * 게시물 목록 토탈
	 */
	@Override
	public int getBoardArticleListTotal(BoardArticleSearch boardArticleSearch) {
		return boardMapper.selectBoardArticleListTotal(boardArticleSearch);
	}

	/**
	 * 게시물 추가
	 * @throws IOException 
	 * @throws AsaproException 
	 */
	@Override
	public Map<String, FileInfoUploadResult> insertBoardArticle(BoardArticle boardArticle) throws AsaproException, IOException {
		Map<String, FileInfoUploadResult> fileInfoUploadResultMap = new HashMap<String, FileInfoUploadResult>();
		FileInfoUploadResult thumbFileInfoUploadResult = new FileInfoUploadResult();
		FileInfoUploadResult fileInfoUploadResult = new FileInfoUploadResult();
		BoardConfig boardConfig = boardArticle.getBoardConfig();
		if(StringUtils.isBlank(boardArticle.getBaGuestName()) ){
			boardArticle.setBaGuestName(boardArticle.getUserMember().getUserName());
		}
		
		//첨부가능한 확장자
		String uploadWhiteList = "";
		
		//대표이미지 처리
		if( boardArticle.getBaThumbFile() != null && boardArticle.getBaThumbFile().getSize() > 0 ){
			
			uploadWhiteList = Constant.UPLOAD_IMAGE_WHITE_LIST; 
			FileInfo fileInfo = this.saveMultipartFile(boardArticle.getBaThumbFile(), boardArticle.getBaThumbText(), boardConfig, thumbFileInfoUploadResult, boardArticle, uploadWhiteList, "thumbnail");
			boardArticle.setThumb(fileInfo);
		}
		
		//개시글 등록
		boardMapper.insertBoardArticle(boardArticle);
		
		//첨부파일 처리
		if( boardArticle.getBaMultipartFiles() != null && !boardArticle.getBaMultipartFiles().isEmpty() ){
			int size = boardArticle.getBaMultipartFiles().size();
			MultipartFile multipartFile = null;
			String baAltText = "";
			for( int i = 0; i<size; i++ ){
				multipartFile = boardArticle.getBaMultipartFiles().get(i);
				
				if( boardArticle.getBaAltTexts() != null && !boardArticle.getBaAltTexts().isEmpty() ){
					baAltText = boardArticle.getBaAltTexts().get(i);
				}
				//첨부파일의 정보와 파일을 저장
				this.saveMultipartFile(multipartFile, baAltText, boardConfig, fileInfoUploadResult, boardArticle, "", "appending");
			}
		}
		
		fileInfoUploadResultMap.put("thumbFileInfoUploadResult", thumbFileInfoUploadResult);
		fileInfoUploadResultMap.put("fileInfoUploadResult", fileInfoUploadResult);
		return fileInfoUploadResultMap;
	}
	
	/**
	 * 첨부파일의 정보와 파일을 저장한다.
	 * @param multipartFile
	 * @param baAltText
	 * @param boardConfig
	 * @param fileInfoUploadResult
	 * @param boardArticle
	 * @param uploadWhiteList
	 * @throws AsaproException
	 * @throws IOException
	 */
	private FileInfo saveMultipartFile(MultipartFile multipartFile, String baAltText, BoardConfig boardConfig, FileInfoUploadResult fileInfoUploadResult, BoardArticle boardArticle, String uploadWhiteList, String attachmentType) throws AsaproException, IOException{
		//게시판에서 설정된 썸네일 가로 세로
		int bcWidth = 400;	//default
		int bcHeight = 300;	//default
		if( boardConfig.getBcThumbnailWidth() != null && boardConfig.getBcThumbnailWidth().intValue() > 0 ){
			bcWidth = boardConfig.getBcThumbnailWidth().intValue();
		}
		if( boardConfig.getBcThumbnailHeight() != null && boardConfig.getBcThumbnailHeight().intValue() > 0 ){
			bcHeight = boardConfig.getBcThumbnailHeight().intValue();
		}
		String webRoot = AsaproUtils.getWebRoot(request);
		
		//첨부된 파일 개수
		FileInfo fileInfo = new FileInfo();
		fileInfo.setSitePrefix(boardArticle.getSitePrefix());
		//모듈1차pk
		fileInfo.setFileModule("board");
		fileInfo.setFileModuleId(boardConfig.getBcId());
		//모듈2차pk
		fileInfo.setFileModuleSub("article");
		fileInfo.setFileModuleSubId(String.valueOf(boardArticle.getBaId()));
		//멤버
		fileInfo.setMemberId(boardArticle.getMemId());
		//첨부유형
		fileInfo.setFileAttachmentType(attachmentType);
		//대체텍스트
		if( StringUtils.isNotBlank(baAltText) ){
			//혹시 길이 안맞게 입력된 경우 - 아마 없겠지만 - 예외처리해줌.
			try{
				//alt에 "(쌍따옴표)가 들어 있는 경우가 가끔있는데 그런 경우 html이 깨짐 그래서 제거.escape시켜도 되긴 되는데 걍 깔끔하게 제거하겠음.
				fileInfo.setFileAltText( Jsoup.clean(baAltText, Whitelist.none()).replace("\"", "") );
			} catch (IndexOutOfBoundsException iobe){
				LOGGER.error("[IndexOutOfBoundsException] Try/Catch... : "+ iobe.getMessage());
				fileInfo.setFileAltText( "" );
			}
		}
		
		else{
			String fileNameTemp = FilenameUtils.getBaseName(multipartFile.getOriginalFilename());
			fileInfo.setFileAltText( Jsoup.clean(fileNameTemp, Whitelist.none()).replace("\"", "") );
		}

		fileInfoService.saveFile(multipartFile, webRoot, fileInfo, (Constant.MEGA * boardConfig.getBcUploadSizeMax()), boardConfig.isBcSupportThumbnail(), bcWidth, bcHeight, boardConfig.isBcThumbnailCrop(), uploadWhiteList);
		if( !fileInfo.isFileUploadSuccess() ){
			fileInfoUploadResult.addErrorInfo(fileInfo);
		}
		return fileInfo;
	}

	/**
	 * 게시물 조회
	 */
	@Override
	public BoardArticle getBoardArticle(BoardArticle boardArticle) {
		return boardMapper.selectBoardArticle(boardArticle);
	}

	/**
	 * 게시물 수정
	 * @throws IOException 
	 * @throws AsaproException 
	 */
	@Override
	public Map<String, FileInfoUploadResult> updateBoardArticle(BoardArticle boardArticle) throws AsaproException, IOException {
		Map<String, FileInfoUploadResult> fileInfoUploadResultMap = new HashMap<String, FileInfoUploadResult>();
		FileInfoUploadResult thumbFileInfoUploadResult = new FileInfoUploadResult();
		FileInfoUploadResult fileInfoUploadResult = new FileInfoUploadResult();
		BoardConfig boardConfig = boardArticle.getBoardConfig();
		
		String uploadWhiteList = "";
		
		//대표이미지 처리
		if( boardArticle.getBaThumbFile() != null && boardArticle.getBaThumbFile().getSize() > 0 ){
			//기존 대표이미지 삭제
			if(boardArticle.getThumb() != null && boardArticle.getThumb().getFileId() > 0){
				boardArticle.getThumb().setSitePrefix(boardArticle.getSitePrefix());
				fileInfoService.deleteFileInfo(boardArticle.getThumb());
			}

			uploadWhiteList = Constant.UPLOAD_IMAGE_WHITE_LIST;
			FileInfo fileInfo = this.saveMultipartFile(boardArticle.getBaThumbFile(), boardArticle.getBaThumbText(), boardConfig, thumbFileInfoUploadResult, boardArticle, uploadWhiteList, "thumbnail");
			boardArticle.setThumb(fileInfo);
			
		}

		//개시글 수정
		boardMapper.updateBoardArticle(boardArticle);
		
		
		//삭제파일 처리
		if( boardArticle.getFileInfoDeleteIds() != null && !boardArticle.getFileInfoDeleteIds().isEmpty() ){
			FileInfo deleteFileInfo = null;
			for( Integer fileId : boardArticle.getFileInfoDeleteIds() ){
				deleteFileInfo = new FileInfo();
				deleteFileInfo.setSitePrefix(boardArticle.getSitePrefix());
				deleteFileInfo.setFileId(fileId);
				fileInfoService.deleteFileInfo(deleteFileInfo);
			}
		}

		
		
		//첨부파일 처리
		if( boardArticle.getBaMultipartFiles() != null && !boardArticle.getBaMultipartFiles().isEmpty() ){
			int size = boardArticle.getBaMultipartFiles().size();
			MultipartFile multipartFile = null;
			String baAltText = null;
			for( int i = 0; i<size; i++ ){
				multipartFile = boardArticle.getBaMultipartFiles().get(i);
				if( boardArticle.getBaAltTexts() != null && !boardArticle.getBaAltTexts().isEmpty() ){
					baAltText = boardArticle.getBaAltTexts().get(i);
				}
				//첨부파일의 정보와 파일을 저장
				this.saveMultipartFile(multipartFile, baAltText, boardConfig, fileInfoUploadResult, boardArticle, "", "appending");
			}
		}

		fileInfoUploadResultMap.put("thumbFileInfoUploadResult", thumbFileInfoUploadResult);
		fileInfoUploadResultMap.put("fileInfoUploadResult", fileInfoUploadResult);
		return fileInfoUploadResultMap;
	}

	/**
	 * 게시물삭제
	 */
	@Override
	public int deleteBoardArticle(List<BoardArticle> boardArticleList) {
		int result = 0;
		for( BoardArticle boardArticle : boardArticleList ){
			result += this.deleteBoardArticle(boardArticle);
		}
		return result;
	}

	/**
	 * 게시물삭제
	 */
	@Override
	public int deleteBoardArticle(BoardArticle boardArticle) {
		BoardArticle fromDB = boardMapper.selectBoardArticle(boardArticle);
		fromDB.setSitePrefix(boardArticle.getSitePrefix());
		
		int result = 0;
		result = boardMapper.deleteBoardArticle(boardArticle);

		if(result > 0){
			
			//첨부파일 삭제
			for( FileInfo fileInfo : fromDB.getBaFileInfos() ){
				//파일 삭제
				fileInfo.setSitePrefix(boardArticle.getSitePrefix());
				fileInfoService.deleteFileInfo(fileInfo);
			}
			//대표이미지 삭제
			if(fromDB.getThumb() != null && fromDB.getThumb().getFileId() > 0){
				FileInfo fileInfoImage = fromDB.getThumb();
				fileInfoImage.setSitePrefix(boardArticle.getSitePrefix());
				fileInfoService.deleteFileInfo(fileInfoImage);
			}
			
			/*
			//게시물 지울때 댓글도 같이 삭제
			CommentSearch commentSearch = new CommentSearch();
			commentSearch.setSitePrefix(fromDB.getSitePrefix());
			commentSearch.setCmModule("board");
			commentSearch.setCmModuleId(fromDB.getBoardConfig().getBcId());
			commentSearch.setCmModuleSub("article");
			commentSearch.setCmModuleSubId(String.valueOf(fromDB.getBaId()));
			commentMapper.deleteCommentsOfModule(commentSearch);
			*/
		}

		return result;
	}

	/**
	 * 게시판 관리자 확인
	 */
	@Override
	public boolean isBoardManager(BoardConfig boardConfig, Member currentMember) {
		//비회원, 비로그인 체크
		if( "ROLE_GUEST".equals(currentMember.getMemberRole().getRoleCode()) ){
			return false;
		}
		//일반회원 체크
		if( "ROLE_NORMAL_USER".equals(currentMember.getMemberRole().getRoleCode()) ){
			return false;
		}
		//관리자면 무조건 패스
		if( currentMember.isSuperAdmin() ){
			return true;
		}
		//게시판 관리자 권한 체크
		//관리부서 지정하지 않으면 모든부서가 관리권한 가진다.
		//관리자 계정만 게시판 관리자여부를 체크한다.
		AdminMember currentAdmin = (AdminMember) request.getSession().getAttribute("currentAdmin");
		if(currentAdmin != null && (StringUtils.isNotBlank(currentAdmin.getAdminDepartment()) || StringUtils.isNotBlank(currentAdmin.getAdminTeam())) ){
			if(ArrayUtils.isEmpty(boardConfig.getBcDepartmentArray()) ){
				return true;
			}else{
				if(StringUtils.isNotBlank(currentAdmin.getAdminTeam()) ){
					//권한 부여된 팀소속이면
					if( ArrayUtils.contains(boardConfig.getBcDepartmentArray(), currentMember.getMemberTeam()) ){
						return true;
					}
				} else {
					//권한 부여된 부서소속이면
					if( ArrayUtils.contains(boardConfig.getBcDepartmentArray(), currentMember.getMemberDepartment()) ){
						return true;
					}
				}
				/*for (String department : boardConfig.getBcDepartmentArray()) {
					//if( currentMember.getMemberDepartment().equals(department) ){
						return true;
					}
				}*/
			}
		}
		
/*
		if( StringUtils.isNotBlank(boardConfig.getBcModeratorCap()) ){
			if( currentMember.hasCapability(boardConfig.getBcModeratorCap()) ){
				return true;
			}
		}
*/
		return false;
	}

	/**
	 * 조회수 증가
	 */
	@Override
	public int updateBoardArticleHit(BoardArticle boardArticle) {
		return boardMapper.updateBoardArticleHit(boardArticle);
	}

	/**
	 * 이전 게시물 조회
	 */
	@Override
	public BoardArticle getPrevBoardArticle(BoardArticle currentBoardArticle) {
		return boardMapper.selectPrevBoardArticle(currentBoardArticle);
	}

	/**
	 * 다음 게시물 조회
	 */
	@Override
	public BoardArticle getNextBoardArticle(BoardArticle currentBoardArticle) {
		return boardMapper.selectNextBoardArticle(currentBoardArticle);
	}
	
	/**
	 * 비밀번호 확인
	 */
	@Override
	public boolean isPasswordMatch(BoardArticle boardArticle, String formMode, String password) {

		BoardArticle fromDB = boardMapper.selectBoardArticle(boardArticle);
		if( fromDB != null ){
			//비회원작성글
			if( fromDB.isGuestArticle() ){
				if( "edit".equals(formMode) ){
					if( egovPasswordEncoder.checkPassword(password, fromDB.getBaGuestPassword()) ){
						return true;
					}
				} else if( "delete".equals(formMode) ){
					if( egovPasswordEncoder.checkPassword(password, fromDB.getBaGuestPassword()) ){
						return true;
					}
				} else if( "secret".equals(formMode) ){
					if( egovPasswordEncoder.checkPassword(password, fromDB.getBaSecretPassword()) ){
						return true;
					}
				}
			}
			//회원작성글
			else {
				if( "edit".equals(formMode) ){
					return true;
				} else if( "delete".equals(formMode) ){
					//회원 글 삭제는 계정 비밀번호로 체크한다
					if( egovPasswordEncoder.checkPassword(password, fromDB.getMember().getMemberPassword()) ){
						return true;
					}
				} else if( "secret".equals(formMode) ){
					if( egovPasswordEncoder.checkPassword(password, fromDB.getBaSecretPassword()) ){
						return true;
					}
				}
			}
		}
		return false;
	}

	/**
	 * 게시물 추천 수를 증가한다.
	 */
	@Override
	public int updateBoardArticleRecommend(BoardArticle recommendArticle) {
		return boardMapper.updateBoardArticleRecommend(recommendArticle);
	}

}
