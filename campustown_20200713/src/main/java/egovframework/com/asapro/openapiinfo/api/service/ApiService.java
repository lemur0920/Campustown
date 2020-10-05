package egovframework.com.asapro.openapiinfo.api.service;

import java.util.List;

public interface ApiService {

	/* =================== 뉴딜 일자리 정보 =================== */  
	/* =================== 뉴딜 일자리 정보 =================== */  
	/**
	 * 뉴딜 일자리 정보 API 목록을 조회한다.
	 * @param jobNewDealBizSearch
	 * @return
	 */
	public List<JobNewDealBiz> getJobNewDealBizList(JobNewDealBizSearch jobNewDealBizSearch);

	/**
	 * 뉴딜 일자리 정보 API 목록 전체 개수를 조회한다.
	 * @param jobNewDealBizSearch
	 * @return
	 */
	public int getJobNewDealBizListTotal(JobNewDealBizSearch jobNewDealBizSearch);

	/**
	 * 뉴딜 일자리 정보 API 상세를 조회한다.
	 * @param jobNewDealBizForm
	 * @return
	 */
	public JobNewDealBiz getJobNewDealBiz(JobNewDealBiz jobNewDealBizForm);

	/* =================== 서울일자리센터 교육정보 =================== */
	/* =================== 서울일자리센터 교육정보 =================== */
	/**
	 * 서울일자리센터 교육정보 API 목록 전체 개수를 조회한다.
	 * @param jobEduCenterSearch
	 * @return
	 */
	public int getJobEduCenterListTotal(JobEduCenterSearch jobEduCenterSearch);

	/**
	 * 서울일자리센터 교육정보 API 목록을 조회한다.
	 * @param jobEduCenterSearch
	 * @return
	 */
	public List<JobEduCenter> getJobEduCenterList(JobEduCenterSearch jobEduCenterSearch);

	/**
	 * 서울일자리센터 교육정보 API 상세를 조회한다.
	 * @param jobEduCenterForm
	 * @return
	 */
	public JobEduCenter getJobEduCenter(JobEduCenter jobEduCenterForm);

	/* ===================  청년취업정책 정보 =================== */
	/* ===================  청년취업정책 정보 =================== */
	/**
	 * 청년취업정책 정보 API 목록 전체 개수를 조회한다.
	 * @param jynEmpSptSearch
	 * @return
	 */
	public int getJynEmpSptListTotal(JynEmpSptSearch jynEmpSptSearch);

	/**
	 * 청년취업정책 정보 API 목록을 조회한다.
	 * @param jynEmpSptSearch
	 * @return
	 */
	public List<JynEmpSpt> getJynEmpSptList(JynEmpSptSearch jynEmpSptSearch);

	/**
	 * 청년취업정책 정보 API 상세를 조회한다.
	 * @param jynEmpSptForm
	 * @return
	 */
	public JynEmpSpt getJynEmpSpt(JynEmpSpt jynEmpSptForm);

	/* ===================  공공취업정보 =================== */
	/* ===================  공공취업정보 =================== */
	/**
	 * 공공취업정보 목록 전체 개수를 조회한다.
	 * @param publicEmplInfoSearch
	 * @return
	 */
	public int getPublicEmplInfoListTotal(PublicEmplInfoSearch publicEmplInfoSearch);

	/**
	 * 공공취업정보 목록을 조회한다.
	 * @param publicEmplInfoSearch
	 * @return
	 */
	public List<PublicEmplInfo> getPublicEmplInfoList(PublicEmplInfoSearch publicEmplInfoSearch);

	/**
	 * 공공취업정보 상세보기를 조회한다.
	 * @param publicEmplInfoForm
	 * @return
	 */
	public PublicEmplInfo getPublicEmplInfo(PublicEmplInfo publicEmplInfoForm);

	/* ===================  지역별행사정보 =================== */
	/* ===================  지역별행사정보 =================== */
	/**
	 * 지역별행사정보 목록 전체 개수를 조회한다.
	 * @param regionEventInfoSearch
	 * @return
	 */
	public int getRegionEventInfoListTotal(RegionEventInfoSearch regionEventInfoSearch);

	/**
	 * 지역별행사정보 목록을 조회한다.
	 * @param regionEventInfoSearch
	 * @return
	 */
	public List<RegionEventInfo> getRegionEventInfoList(RegionEventInfoSearch regionEventInfoSearch);

	/**
	 * 지역별행사정보 상세보기를 조회한다.
	 * @param regionEventInfoForm
	 * @return
	 */
	public RegionEventInfo getRegionEventInfo(RegionEventInfo regionEventInfoForm);

	/* ===================  청년활동지원정보 =================== */
	/* ===================  청년활동지원정보 =================== */
	/**
	 * 청년활동지원정보 목록 전체 개수를 조회한다.
	 * @param youthActSupInfoSearch
	 * @return
	 */
	public int getYouthActSupInfoListTotal(YouthActSupInfoSearch youthActSupInfoSearch);

	/**
	 * 청년활동지원정보 목록을 조회한다.
	 * @param youthActSupInfoSearch
	 * @return
	 */
	public List<YouthActSupInfo> getYouthActSupInfoList(YouthActSupInfoSearch youthActSupInfoSearch);

	/**
	 * 청년활동지원정보 상세보기를 조회한다.
	 * @param youthActSupInfoForm
	 * @return
	 */
	public YouthActSupInfo getYouthActSupInfo(YouthActSupInfo youthActSupInfoForm);

	/* ===================  청년 프로그램 =================== */
	/* ===================  청년 프로그램 =================== */
	/**
	 * 청년 프로그램 목록 전체 개수를 조회한다.
	 * @param youthProgramSearch
	 * @return
	 */
	public int getYouthProgramListTotal(YouthProgramSearch youthProgramSearch);

	/**
	 * 청년 프로그램 목록을 조회한다.
	 * @param youthProgramSearch
	 * @return
	 */
	public List<YouthProgram> getYouthProgramList(YouthProgramSearch youthProgramSearch);

	/**
	 * 청년 프로그램 상세보기를 조회한다.
	 * @param youthProgramForm
	 * @return
	 */
	public YouthProgram getyouthProgram(YouthProgram youthProgramForm);

}
