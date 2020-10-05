/**
 * 
 */
package egovframework.com.asapro.poll.service.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;
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

import egovframework.com.asapro.poll.service.Poll;
import egovframework.com.asapro.poll.service.PollMapper;
import egovframework.com.asapro.poll.service.PollSearch;
import egovframework.com.asapro.poll.service.PollService;
import egovframework.com.asapro.fileinfo.service.FileInfo;
import egovframework.com.asapro.fileinfo.service.FileInfoService;
import egovframework.com.asapro.fileinfo.service.FileInfoUploadResult;
import egovframework.com.asapro.support.Constant;
import egovframework.com.asapro.support.exception.AsaproException;
import egovframework.com.asapro.support.util.AsaproUtils;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

/**
 * 투표 서비스 구현
 * @author yckim
 * @since 2020. 1. 21.
 *
 */
@Service
public class PollServiceImpl extends EgovAbstractServiceImpl implements PollService {
	private static final Logger LOGGER = LoggerFactory.getLogger(PollServiceImpl.class);
	@Autowired
	private PollMapper pollMapper;
	
	@Autowired
	private FileInfoService fileInfoService;
	
	@Autowired
	private HttpServletRequest request;
	
	/**
	 * 투표 목록
	 */
	@Override
	public List<Poll> getPollList(PollSearch pollSearch) {
		return pollMapper.selectPollList(pollSearch);
	}

	/**
	 * 투표 개수
	 */
	@Override
	public int getPollListTotal(PollSearch pollSearch) {
		return pollMapper.selectPollListTotal(pollSearch);
	}

	/**
	 * 투표 조회
	 */
	@Override
	public Poll getPoll(Poll poll) {
		return pollMapper.selectPoll(poll);
	}

	/**
	 * 투표 추가
	 * @throws IOException 
	 * @throws AsaproException 
	 * @throws FileNotFoundException 
	 */
	@Override
	public FileInfoUploadResult insertPoll(Poll pollForm) throws FileNotFoundException, AsaproException, IOException {
		FileInfoUploadResult thumbFileInfoUploadResult = new FileInfoUploadResult();
		
		pollForm.setPoRegDate(new Date());
		
		//설정기능 없으므로 일단 디폴트값으로 처리
		int thumbWidth = 320;	//default
		int thumbHeight = 200;	//default
		
		//첨부가능한 확장자
		String uploadWhiteList = "";
		
		//이미지 처리
		if( pollForm.getPoImage() != null && pollForm.getPoImage().getSize() > 0 ){

			uploadWhiteList = Constant.UPLOAD_IMAGE_WHITE_LIST; 
			String webRoot = AsaproUtils.getWebRoot(request);
			
			//첨부된 파일 
			FileInfo fileInfo = new FileInfo();
			fileInfo.setSitePrefix(pollForm.getSitePrefix());
			//모듈1차pk
			fileInfo.setFileModule("poll");
			fileInfo.setFileModuleId(pollForm.getPoType());
			//모듈2차pk
			fileInfo.setFileModuleSub("");
			fileInfo.setFileModuleSubId("");
			//멤버
			fileInfo.setMemberId("");
			//첨부유형
			fileInfo.setFileAttachmentType("thumbnail");
			//대체텍스트
			if(StringUtils.isNotBlank(pollForm.getPoThumbText()) ){
				try{
					fileInfo.setFileAltText(Jsoup.clean(pollForm.getPoThumbText(), Whitelist.none()).replace("\"", "") );
				}catch (IndexOutOfBoundsException iobe){
					LOGGER.error("[IndexOutOfBoundsException] Try/Catch... : "+ iobe.getMessage());
					fileInfo.setFileAltText( "" );
				}
			}else{
				String fileNameTemp = FilenameUtils.getBaseName(pollForm.getPoImage().getOriginalFilename());
				fileInfo.setFileAltText( Jsoup.clean(fileNameTemp, Whitelist.none()).replace("\"", "") );
			}
			
			//썸네일 파일도 만든다
			fileInfoService.saveFile(pollForm.getPoImage(), webRoot, fileInfo, (Constant.MEGA * 10), true, thumbWidth, thumbHeight, true, uploadWhiteList);
			if( !fileInfo.isFileUploadSuccess() ){
				thumbFileInfoUploadResult.addErrorInfo(fileInfo);
			}
			
			pollForm.setThumb(fileInfo);
			
		}

		int inserted = pollMapper.insertPoll(pollForm);
		//fileInfoService.saveFileOnly(pollForm.getBnImage(), new File(AsaproUtils.getSiteUploadDirectory(request) + "/poll/" + pollForm.getBnId() + ext), true);
		
		return thumbFileInfoUploadResult;
	}

	/**
	 * 투표 수정
	 * @throws IOException 
	 * @throws AsaproException 
	 */
	@Override
	public FileInfoUploadResult updatePoll(Poll pollForm) throws AsaproException, IOException {
		FileInfoUploadResult thumbFileInfoUploadResult = new FileInfoUploadResult();

		//설정기능 없으므로 일단 디폴트값으로 처리
		int thumbWidth = 320;	//default
		int thumbHeight = 200;	//default
		
		//첨부가능한 확장자
		String uploadWhiteList = Constant.UPLOAD_IMAGE_WHITE_LIST; 
		
		//새 이미지 처리
		if( pollForm.getPoImage() != null && pollForm.getPoImage().getSize() > 0 ){

			//기존이미지 삭제
			if(pollForm.getThumb() != null && pollForm.getThumb().getFileId() > 0){
				FileInfo fileInfoImage = pollForm.getThumb();
				fileInfoImage.setSitePrefix(pollForm.getSitePrefix());
				fileInfoService.deleteFileInfo(fileInfoImage);
			}

			String webRoot = AsaproUtils.getWebRoot(request);
			
			//첨부된 파일 
			FileInfo fileInfo = new FileInfo();
			fileInfo.setSitePrefix(pollForm.getSitePrefix());
			//모듈1차pk
			fileInfo.setFileModule("poll");
			fileInfo.setFileModuleId(pollForm.getPoType());
			//모듈2차pk
			fileInfo.setFileModuleSub("");
			fileInfo.setFileModuleSubId("");
			//멤버
			fileInfo.setMemberId("");
			//첨부유형
			fileInfo.setFileAttachmentType("thumbnail");
			//대체텍스트
			if(StringUtils.isNotBlank(pollForm.getPoThumbText()) ){
				try{
					fileInfo.setFileAltText(Jsoup.clean(pollForm.getPoThumbText(), Whitelist.none()).replace("\"", "") );
				}catch (IndexOutOfBoundsException iobe){
					LOGGER.error("[IndexOutOfBoundsException] Try/Catch... : "+ iobe.getMessage());
					fileInfo.setFileAltText( "" );
				}
			}else{
				String fileNameTemp = FilenameUtils.getBaseName(pollForm.getPoImage().getOriginalFilename());
				fileInfo.setFileAltText( Jsoup.clean(fileNameTemp, Whitelist.none()).replace("\"", "") );
			}
			
			//썸네일 파일도 만든다
			fileInfoService.saveFile(pollForm.getPoImage(), webRoot, fileInfo, (Constant.MEGA * 10), true, thumbWidth, thumbHeight, true, uploadWhiteList);
			if( !fileInfo.isFileUploadSuccess() ){
				thumbFileInfoUploadResult.addErrorInfo(fileInfo);
			}
			
			pollForm.setThumb(fileInfo);
			
		}
		
		int updated = pollMapper.updatePoll(pollForm);
		
		return thumbFileInfoUploadResult;
	}

	/**
	 * 투표 삭제
	 */
	@Override
	public int deletePoll(List<Poll> pollList) throws FileNotFoundException {
		int deleted = 0;
		for( Poll poll : pollList ){
			deleted += this.deletPoll(poll);
		}
		return deleted;
	}
		
	/**
	 * 투표 삭제
	 */
	@Override
	public int deletPoll(Poll poll) throws FileNotFoundException {
		Poll fromDB = pollMapper.selectPoll(poll);
		fromDB.setSitePrefix(poll.getSitePrefix());
		
		int result = pollMapper.deletePoll(poll);
		
		if(result > 0){
			
			//대표이미지 삭제
			if(fromDB.getThumb() != null && fromDB.getThumb().getFileId() > 0){
				FileInfo fileInfoImage = fromDB.getThumb();
				fileInfoImage.setSitePrefix(poll.getSitePrefix());
				fileInfoService.deleteFileInfo(fileInfoImage);
			}
		}

		return result;
	}

	/**
	 * 투표 조회수를 증가한다.
	 */
	@Override
	public int updatePollHit(Poll pollModel) {
		int hitResult = pollMapper.updatePollHit(pollModel);
		return hitResult;
	}

	/**
	 * 투표를 실시한다.
	 */
	@Override
	public int updatePollTake(Poll poll) {
		int result = 0;
		String data = poll.getPollData();
		if(StringUtils.isNotBlank(data) ){
			if("yes".equals(data) ){
				result = pollMapper.updatePollYes(poll);
			}else{//no
				result = pollMapper.updatePollNo(poll);
			}
			
		}
		
		return result;
	}

}
