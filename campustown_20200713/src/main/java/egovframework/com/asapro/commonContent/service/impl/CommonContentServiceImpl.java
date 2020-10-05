/**
 * 
 */
package egovframework.com.asapro.commonContent.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import egovframework.com.asapro.board.service.BoardArticle;
import egovframework.com.asapro.commonContent.service.CommonContent;
import egovframework.com.asapro.commonContent.service.CommonContentMapper;
import egovframework.com.asapro.commonContent.service.CommonContentRelation;
import egovframework.com.asapro.commonContent.service.CommonContentSearch;
import egovframework.com.asapro.commonContent.service.CommonContentService;
import egovframework.com.asapro.config.service.ConfigMapper;
import egovframework.com.asapro.config.service.ConfigOption;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

/**
 * 공통 콘텐츠 서비스 구현
 * @author yckim
 * @since 2018. 8. 23.
 *
 */
@Service
public class CommonContentServiceImpl extends EgovAbstractServiceImpl implements CommonContentService {

	@Autowired
	private CommonContentMapper commonContentMapper;
	
	@Autowired
	private ConfigMapper configMapper;

	/**
	 * 공통 콘텐츠 목록을 반환한다.
	 */
	@Override
	public List<CommonContent> getCommonContentList(CommonContentSearch commonContentSearch) {
		return commonContentMapper.selectCommonContentList(commonContentSearch);
	}

	/**
	 * 공통 콘텐츠 목록 토탈 반환한다.
	 */
	@Override
	public int getCommonContentListTotal(CommonContentSearch commonContentSearch) {
		return commonContentMapper.selectCommonContentListTotal(commonContentSearch);
	}

	/**
	 * 공통 콘텐츠를 추가한다.
	 */
	@Override
	public int insertCommonContent(CommonContent commonContentForm) {
		return commonContentMapper.insertCommonContent(commonContentForm);
	}

	/**
	 * 공통 콘텐츠를 조회한다.
	 */
	@Override
	public CommonContent getCommonContent(CommonContent commonContentForm) {
		return commonContentMapper.selectCommonContent(commonContentForm);
	}

	/**
	 * 공통 콘텐츠를 수정한다.
	 */
	@Override
	public int updateCommonContent(CommonContent commonContentForm) {
		return commonContentMapper.updateCommonContent(commonContentForm);
	}

	/**
	 * 공통 콘텐츠를 삭제한다.
	 */
	@Override
	public int deleteCommonContent(List<CommonContent> commonContentList) {
		int result = 0;
		for( CommonContent commonContent : commonContentList ){
			result += this.deleteCommonContent(commonContent);
		}
		return result;
	}
	
	/**
	 * 공통 콘텐츠를 삭제한다.
	 */
	@Override
	public int deleteCommonContent(CommonContent commonContent) {
		return commonContentMapper.deleteCommonContent(commonContent);
	}

	
	//=======================================================================================================
	//============================  릴레이션을 통한 공통콘텐츠 관리    ==================================================
	//=======================================================================================================
	/**
	 * 공통 콘텐츠를 조회한다.(By Relation)
	 */
	@Override
	public CommonContent getCommonContentByRelation(CommonContentRelation commonContentRelation) {
		return commonContentMapper.selectCommonContentByRelation(commonContentRelation);
	}

	/**
	 * 공통 콘텐츠 릴레이션을 추가한다.
	 */
	@Override
	public int insertCommonContentRelation(CommonContentRelation commonContentRelation) {
		return commonContentMapper.insertCommonContentRelation(commonContentRelation);
	}

	/**
	 * 공통 콘텐츠 릴레이션을 삭제한다.
	 */
	@Override
	public int deleteCommonContentRelation(CommonContentRelation commonContentRelation) {
		return commonContentMapper.deleteCommonContentRelation(commonContentRelation);
	}

}
