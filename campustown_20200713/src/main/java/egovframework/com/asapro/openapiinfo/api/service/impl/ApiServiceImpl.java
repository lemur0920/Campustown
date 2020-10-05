package egovframework.com.asapro.openapiinfo.api.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import egovframework.com.asapro.education.service.impl.EducationServiceImpl;
import egovframework.com.asapro.openapiinfo.api.service.ApiMapper;
import egovframework.com.asapro.openapiinfo.api.service.JobNewDealBiz;
import egovframework.com.asapro.openapiinfo.api.service.JobNewDealBizSearch;
import egovframework.com.asapro.openapiinfo.api.service.JynEmpSpt;
import egovframework.com.asapro.openapiinfo.api.service.JynEmpSptSearch;
import egovframework.com.asapro.openapiinfo.api.service.PublicEmplInfo;
import egovframework.com.asapro.openapiinfo.api.service.PublicEmplInfoSearch;
import egovframework.com.asapro.openapiinfo.api.service.RegionEventInfo;
import egovframework.com.asapro.openapiinfo.api.service.RegionEventInfoSearch;
import egovframework.com.asapro.openapiinfo.api.service.YouthActSupInfo;
import egovframework.com.asapro.openapiinfo.api.service.YouthActSupInfoSearch;
import egovframework.com.asapro.openapiinfo.api.service.YouthProgram;
import egovframework.com.asapro.openapiinfo.api.service.YouthProgramSearch;
import egovframework.com.asapro.openapiinfo.api.service.ApiService;
import egovframework.com.asapro.openapiinfo.api.service.JobEduCenter;
import egovframework.com.asapro.openapiinfo.api.service.JobEduCenterSearch;

@Service
public class ApiServiceImpl implements ApiService {

	private static final Logger LOGGER = LoggerFactory.getLogger(EducationServiceImpl.class);
	
	@Autowired
	private ApiMapper apiMapper;
	

	/* =================== 뉴딜 일자리 정보 =================== */  
	/* =================== 뉴딜 일자리 정보 =================== */ 
	/**
	 * API 뉴딜 일자리정책 목록을 조회한다. 
	 */
	@Override
	public List<JobNewDealBiz> getJobNewDealBizList(JobNewDealBizSearch jobNewDealBizSearch) {
		return apiMapper.selectJobNewDealBizList(jobNewDealBizSearch);
	}

	/**
	 *  API 뉴딜 일자리정책 전체 개수를 조회한다.
	 */
	@Override
	public int getJobNewDealBizListTotal(JobNewDealBizSearch jobNewDealBizSearch) {
		return apiMapper.selectJobNewDealBizListTotal(jobNewDealBizSearch);
	}

	/**
	 * API 뉴딜 일자리정책 상세보기를 조회한다. 
	 */
	@Override
	public JobNewDealBiz getJobNewDealBiz(JobNewDealBiz jobNewDealBizForm) {
		return apiMapper.selectNewDealBiz(jobNewDealBizForm);
	}
	
	/* =================== 서울일자리센터 교육정보 =================== */
	/* =================== 서울일자리센터 교육정보 =================== */
	/**
	 * 서울일자리센터 교육정보 API 목록 전체 개수를 조회한다.
	 */
	@Override
	public int getJobEduCenterListTotal(JobEduCenterSearch jobEduCenterSearch) {
		return apiMapper.selectJobEduCenterListTotal(jobEduCenterSearch);
	}

	/**
	 * 서울일자리센터 교육정보 API 목록을 조회한다.
	 */
	@Override
	public List<JobEduCenter> getJobEduCenterList(JobEduCenterSearch jobEduCenterSearch) {
		return apiMapper.selectJobEduCenterList(jobEduCenterSearch);
	}

	/**
	 * 서울일자리센터 교육정보 API 상세를 조회한다.
	 */
	@Override
	public JobEduCenter getJobEduCenter(JobEduCenter jobEduCenterForm) {
		return apiMapper.selectJobEduCenter(jobEduCenterForm);
	}

	/* ===================  청년취업정책 정보 =================== */
	/* ===================  청년취업정책 정보 =================== */
	/**
	 * 청년취업정책 정보 API 목록 전체 개수를 조회한다.
	 */
	@Override
	public int getJynEmpSptListTotal(JynEmpSptSearch jynEmpSptSearch) {
		return apiMapper.selectJynEmpSptListTotal(jynEmpSptSearch);
	}

	/**
	 *  청년취업정책 정보 API 목록을 조회한다.
	 */
	@Override
	public List<JynEmpSpt> getJynEmpSptList(JynEmpSptSearch jynEmpSptSearch) {
		return apiMapper.selectJynEmpSptList(jynEmpSptSearch);
	}

	/**
	 * 청년취업정책 정보 API 상세를 조회한다.
	 */
	@Override
	public JynEmpSpt getJynEmpSpt(JynEmpSpt jynEmpSptForm) {
		return apiMapper.selectJynEmpSpt(jynEmpSptForm);
	}

	/* ===================  공공취업정보 =================== */
	/* ===================  공공취업정보 =================== */
	/**
	 * 공공취업정보 목록 전체 개수를 조회한다.
	 */
	@Override
	public int getPublicEmplInfoListTotal(PublicEmplInfoSearch publicEmplInfoSearch) {
		return apiMapper.selectPublicEmplInfoListTotal(publicEmplInfoSearch);
	}

	/**
	 * 공공취업정보 목록을 조회한다.
	 */
	@Override
	public List<PublicEmplInfo> getPublicEmplInfoList(PublicEmplInfoSearch publicEmplInfoSearch) {
		return apiMapper.selectPublicEmplInfoList(publicEmplInfoSearch);
	}

	/**
	 * 공공취업정보 상세보기를 조회한다.
	 */
	@Override
	public PublicEmplInfo getPublicEmplInfo(PublicEmplInfo publicEmplInfoForm) {
		return apiMapper.selectPublicEmplInfo(publicEmplInfoForm);
	}

	/* ===================  지역별행사정보 =================== */
	/* ===================  지역별행사정보 =================== */
	/**
	 * 지역별행사정보 목록 전체 개수를 조회한다.
	 */
	@Override
	public int getRegionEventInfoListTotal(RegionEventInfoSearch regionEventInfoSearch) {
		return apiMapper.selectRegionEventInfoListTotal(regionEventInfoSearch);
	}

	/**
	 * 지역별행사정보 목록을 조회한다.
	 */
	@Override
	public List<RegionEventInfo> getRegionEventInfoList(RegionEventInfoSearch regionEventInfoSearch) {
		return apiMapper.selectRegionEventInfoList(regionEventInfoSearch);
	}

	/**
	 * 지역별행사정보 상세보기를 조회한다.
	 */
	@Override
	public RegionEventInfo getRegionEventInfo(RegionEventInfo regionEventInfoForm) {
		return apiMapper.selectRegionEventInfo(regionEventInfoForm);
	}

	/* ===================  청년활동지원정보 =================== */
	/* ===================  청년활동지원정보 =================== */
	/**
	 * 청년활동지원정보 목록 전체 개수를 조회한다.
	 */
	@Override
	public int getYouthActSupInfoListTotal(YouthActSupInfoSearch youthActSupInfoSearch) {
		return apiMapper.selectYouthActSupInfoListTotal(youthActSupInfoSearch);
	}

	/**
	 * 청년활동지원정보 목록을 조회한다.
	 */
	@Override
	public List<YouthActSupInfo> getYouthActSupInfoList(YouthActSupInfoSearch youthActSupInfoSearch) {
		return apiMapper.selectYouthActSupInfoList(youthActSupInfoSearch);
	}

	/**
	 * 청년활동지원정보 상세보기를 조회한다.
	 */
	@Override
	public YouthActSupInfo getYouthActSupInfo(YouthActSupInfo youthActSupInfoForm) {
		return apiMapper.selectYouthActSupInfo(youthActSupInfoForm);
	}

	/* ===================  청년 프로그램 =================== */
	/* ===================  청년 프로그램 =================== */
	/**
	 * 청년 프로그램 목록 전체 개수를 조회한다.
	 */
	@Override
	public int getYouthProgramListTotal(YouthProgramSearch youthProgramSearch) {
		return apiMapper.selectYouthProgramListTotal(youthProgramSearch);
	}

	/**
	 * 청년 프로그램 목록을 조회한다.
	 */
	@Override
	public List<YouthProgram> getYouthProgramList(YouthProgramSearch youthProgramSearch) {
		return apiMapper.selectYouthProgramList(youthProgramSearch);
	}

	/**
	 * 청년 프로그램 상세보기를 조회한다.
	 */
	@Override
	public YouthProgram getyouthProgram(YouthProgram youthProgramForm) {
		return apiMapper.selectYouthProgram(youthProgramForm);
	}

}
