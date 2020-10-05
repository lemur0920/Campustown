/**
 * 
 */
package egovframework.com.asapro.content.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import egovframework.com.asapro.config.service.ConfigMapper;
import egovframework.com.asapro.config.service.ConfigOption;
import egovframework.com.asapro.content.service.Content;
import egovframework.com.asapro.content.service.ContentMapper;
import egovframework.com.asapro.content.service.ContentSearch;
import egovframework.com.asapro.content.service.ContentService;
import egovframework.com.asapro.menu.service.MenuContentRelation;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

/**
 * 콘텐츠 서비스 구현
 * @author yckim
 * @since 2018. 7. 20.
 *
 */
@Service
public class ContentServiceImpl extends EgovAbstractServiceImpl implements ContentService {

	@Autowired
	private ContentMapper contentMapper;
	
	@Autowired
	private ConfigMapper configMapper;
	
	/**
	 * 콘텐츠 조회
	 */
	@Override
	public Content getContent(Content content) {
		return contentMapper.selectContent(content);
	}
	
	/**
	 * 목록 조회
	 */
	@Override
	public List<Content> getContentList(ContentSearch contentSearch){
		return contentMapper.selectContentList(contentSearch);
	}
	
	/**
	 * 토탈
	 */
	@Override
	public int getContentListTotal(ContentSearch revisionListSearch) {
		return contentMapper.selectContentListTotal(revisionListSearch);
	}
	
	/**
	 * 콘텐츠 추가
	 */
	@Override
	public int insertContent(Content content) {
		return contentMapper.insertContent(content);
	}

	/**
	 * 콘텐츠 수정
	 */
	@Override
	public int updateContent(Content contentForm) {
		
		Content currentContent = contentMapper.selectContent(contentForm);
		//내용이나 비고가 바뀐거 없으면 
		if( currentContent.getContentTitle().trim().equals(contentForm.getContentTitle().trim()) 
				&& currentContent.getContent().trim().equals(contentForm.getContent().trim()) 
				&& (currentContent.getContentMemo() != null && currentContent.getContentMemo().trim().equals(contentForm.getContentMemo().trim())) ){
			//패스
			return 99;
		} else {
			
			//	콘텐츠 개정이력 사용여부
			ConfigOption configOption =	new ConfigOption("site", "use_content_revision");
			configOption.setSitePrefix(contentForm.getSitePrefix());
			ConfigOption useContentRevision = configMapper.selectConfigOption(configOption);
			
			//개정이력 옵션 사용 여부에 따라 저장 방식 다름
			if( "true".equals(useContentRevision.getOptValue()) ){
				//내용이 바뀌었으면
				//원래 콘텐츠는 리비전으로 전환하고
				currentContent.setContentStatus("revision");
				int result = contentMapper.updateContent(currentContent);
				//새로 입력
				contentForm.setContentVer(currentContent.getContentVer() + 1);
				contentForm.setContentRegDate(new Date());
				result += contentMapper.insertContent(contentForm);
				return result;
			} else {
				//리비전 사용하지 않으므로 바로 수정한다.
				contentForm.setContentStatus("publish");
				return contentMapper.updateContent(contentForm);
			}
		}
		
	}

	/**
	 * 콘텐츠 복구
	 */
	@Override
	public int restoreContent(Content contentRestoreForm) {
		int result = 0;
		
		//마지막 콘텐츠 - 원래 한개 있음
		Content contentSearch = new Content();
		//contentSearch.setSitePrefix(contentRestoreForm.getSitePrefix());
		contentSearch.setContentRoot(contentRestoreForm.getContentRoot());
		contentSearch.setContentStatus("publish");
		Content currentContent = contentMapper.selectContent(contentSearch);
		//현재 콘텐츠는 리비전으로 변경하고
		//currentContent.setSitePrefix(contentRestoreForm.getSitePrefix());
		currentContent.setContentStatus("revision");
		result = contentMapper.updateContent(currentContent);
		
		contentSearch.setContentStatus("revision");
		contentSearch.setContentVer(contentRestoreForm.getContentVer());
		Content revisionContent = contentMapper.selectContent(contentSearch);
		
		//리비전 콘텐츠의 내용을 카피해서 새로 하나 만들어 준다.
		contentRestoreForm.setContentTitle(revisionContent.getContentTitle());
		contentRestoreForm.setContent(revisionContent.getContent());
		contentRestoreForm.setContentPlain(revisionContent.getContentPlain());
		contentRestoreForm.setContentLastModified(new Date());
		contentRestoreForm.setContentMemo(revisionContent.getContentMemo());
		contentRestoreForm.setContentRegDate(revisionContent.getContentRegDate());
		contentRestoreForm.setContentVer(currentContent.getContentVer() + 1);
		contentRestoreForm.setContentStatus("publish");
		result += contentMapper.insertContent(contentRestoreForm);
		
		return result;
	}

	/**
	 * 콘텐츠를 삭제한다.
	 */
	@Override
	public int deleteContents(List<Content> contentList) {
		int deleted = 0;
		for( Content content : contentList ){
			deleted += this.deleteContent(content);
		}
		return deleted;
	}

	/**
	 * 콘텐츠를 삭제한다.
	 */
	@Override
	public int deleteContent(Content content) {
		int result = contentMapper.deleteContent(content);
		return result;
	}

	//======================================================================================================
	//=================================== 릴레이션을 이용한 콘텐츠 관리 ==========================================================
	//======================================================================================================
	/**
	 * 릴레이션을 이용해 콘텐츠를 조회한다.
	 */
	@Override
	public Content getContentByRel(MenuContentRelation menuContentRelation) {
		return contentMapper.selectContentByRel(menuContentRelation);
	}

	/**
	 * 메뉴-콘텐트 릴레이션을 추가한다.
	 */
	@Override
	public int insertMenuContentRel(MenuContentRelation menuContentRelation) {
		return contentMapper.insertMenuContentRel(menuContentRelation);
	}

	/**
	 * 메뉴-콘텐트 릴레이션을 삭제한다.
	 */
	@Override
	public int deleteMenuContentRel(MenuContentRelation menuContentRelation) {
		return contentMapper.deleteMenuContentRel(menuContentRelation);
	}

	
		

}
