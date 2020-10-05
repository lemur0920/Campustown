package egovframework.com.asapro.openapiinfo.api.service;

import java.util.List;
import java.util.Map;

import egovframework.com.asapro.openapiinfo.service.OpenApiInfo;
import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper
public interface ApiMapper {

	/* =================== 뉴딜 일자리 정보 =================== */  
	/* =================== 뉴딜 일자리 정보 =================== */ 
	/**
	 * 뉴딜api 목록을 조회한다.
	 * @param jobNewDealBizSearch
	 * @return
	 */
	public List<JobNewDealBiz> selectJobNewDealBizList(JobNewDealBizSearch jobNewDealBizSearch);

	/**
	 * 뉴딜api 목록 전체 개수를 조회한다.
	 * @param jobNewDealBizSearch
	 * @return
	 */
	public int selectJobNewDealBizListTotal(JobNewDealBizSearch jobNewDealBizSearch);

	/**
	 * 뉴딜api 상세보기를 조회한다.
	 * @param jobNewDealBizForm
	 * @return
	 */
	public JobNewDealBiz selectNewDealBiz(JobNewDealBiz jobNewDealBizForm);

	/* =================== 서울일자리센터 교육정보 =================== */
	/* =================== 서울일자리센터 교육정보 =================== */
	/**
	 * 서울일자리센터 교육정보 API 목록 전체 개수를 조회한다.
	 * @param jobEduCenterSearch
	 * @return
	 */
	public int selectJobEduCenterListTotal(JobEduCenterSearch jobEduCenterSearch);

	/**
	 * 서울일자리센터 교육정보 API 목록을 조회한다.
	 * @param jobEduCenterSearch
	 * @return
	 */
	public List<JobEduCenter> selectJobEduCenterList(JobEduCenterSearch jobEduCenterSearch);

	/**
	 * 서울일자리센터 교육정보 API 상세를 조회한다.
	 * @param jobEduCenterForm
	 * @return
	 */
	public JobEduCenter selectJobEduCenter(JobEduCenter jobEduCenterForm);

	/* ===================  청년취업정책 정보 =================== */
	/* ===================  청년취업정책 정보 =================== */
	/**
	 * 청년취업정책 정보 API 목록 전체 개수를 조회한다.
	 * @param jynEmpSptSearch
	 * @return
	 */
	public int selectJynEmpSptListTotal(JynEmpSptSearch jynEmpSptSearch);

	/**
	 * 청년취업정책 정보 API 목록을 조회한다.
	 * @param jynEmpSptSearch
	 * @return
	 */
	public List<JynEmpSpt> selectJynEmpSptList(JynEmpSptSearch jynEmpSptSearch);

	/**
	 * 청년취업정책 정보 API 상세를 조회한다.
	 * @param jynEmpSptForm
	 * @return
	 */
	public JynEmpSpt selectJynEmpSpt(JynEmpSpt jynEmpSptForm);

	/* ===================  공공취업정보 =================== */
	/* ===================  공공취업정보 =================== */
	/**
	 * 공공취업정보 목록 전체 개수를 조회한다.
	 * @param publicEmplInfoSearch
	 * @return
	 */

	public int selectPublicEmplInfoListTotal(PublicEmplInfoSearch publicEmplInfoSearch);
	/**
	 * 공공취업정보 목록을 조회한다.
	 * @param publicEmplInfoSearch
	 * @return
	 */
	public List<PublicEmplInfo> selectPublicEmplInfoList(PublicEmplInfoSearch publicEmplInfoSearch);

	/**
	 * 공공취업정보 상세보기를 조회한다.
	 * @param publicEmplInfoForm
	 * @return
	 */
	public PublicEmplInfo selectPublicEmplInfo(PublicEmplInfo publicEmplInfoForm);
	
	/* ===================  지역별행사정보 =================== */
	/* ===================  지역별행사정보 =================== */
	/**
	 * 지역별행사정보 목록 전체 개수를 조회한다.
	 * @param regionEventInfoSearch
	 * @return
	 */
	public int selectRegionEventInfoListTotal(RegionEventInfoSearch regionEventInfoSearch);

	/**
	 * 지역별행사정보 목록을 조회한다.
	 * @param regionEventInfoSearch
	 * @return
	 */
	public List<RegionEventInfo> selectRegionEventInfoList(RegionEventInfoSearch regionEventInfoSearch);

	/**
	 * 지역별행사정보 상세보기를 조회한다.
	 * @param regionEventInfoForm
	 * @return
	 */
	public RegionEventInfo selectRegionEventInfo(RegionEventInfo regionEventInfoForm);

	/* ===================  청년활동지원정보 =================== */
	/* ===================  청년활동지원정보 =================== */
	/**
	 * 청년활동지원정보 목록 전체 개수를 조회한다.
	 * @param youthActSupInfoSearch
	 * @return
	 */
	public int selectYouthActSupInfoListTotal(YouthActSupInfoSearch youthActSupInfoSearch);

	/**
	 * 청년활동지원정보 목록을 조회한다.
	 * @param youthActSupInfoSearch
	 * @return
	 */
	public List<YouthActSupInfo> selectYouthActSupInfoList(YouthActSupInfoSearch youthActSupInfoSearch);

	/**
	 * 청년활동지원정보 상세보기를 조회한다.
	 * @param youthActSupInfoForm
	 * @return
	 */
	public YouthActSupInfo selectYouthActSupInfo(YouthActSupInfo youthActSupInfoForm);

	/* ===================  청년 프로그램 =================== */
	/* ===================  청년 프로그램 =================== */
	/**
	 * 청년 프로그램 목록 전체 개수를 조회한다.
	 * @param youthProgramSearch
	 * @return
	 */
	public int selectYouthProgramListTotal(YouthProgramSearch youthProgramSearch);

	/**
	 * 청년 프로그램 목록을 조회한다.
	 * @param youthProgramSearch
	 * @return
	 */
	public List<YouthProgram> selectYouthProgramList(YouthProgramSearch youthProgramSearch);

	/**
	 * 청년 프로그램 상세보기를 조회한다.
	 * @param youthProgramForm
	 * @return
	 */
	public YouthProgram selectYouthProgram(YouthProgram youthProgramForm);

	//==================================================================================================================================
	//==================================================================================================================================
	//==================================================================================================================================
	
	/**
	 * 배치하고자 하는 테이블의 데이터를 삭제한다.
	 * @param openApiInfoModel
	 * @return 삭제결과
	 */
	public int deleteApiData(OpenApiInfo openApiInfoModel);

	/**
	 * 서울 뉴딜일자리 사업정보를 추가한다.
	 * @param jobNewDealBiz
	 * @return 추가결과
	 */
	public int insertJobNewDealBizOpenInfo(Map<String,Object> jobNewDealBiz);
	
	/**
	 * 서울일자리센터 교육정보를 추가한다.
	 * @param JobEduCenter
	 * @return 추가결과
	 */
	public int insertJobEduCenterOpenInfo(Map<String,Object> JobEduCenter);

	/**
	 * 청년취업정책을 추가한다.
	 * @param JynEmpSpt
	 * @return 추가결과
	 */
	public int insertJynEmpSptListAPI(Map<String,Object> JynEmpSpt);

	/**
	 * 공공취업정보를 추가한다.
	 * @param RetrievePblinsttEmpmnInfo
	 * @return 추가결과
	 */
	public int insertRetrievePblinsttEmpmnInfoService(Map<String,Object> RetrievePblinsttEmpmnInfo);
	
	/**
	 * 청년활동지원정보를 추가한다.
	 * @param youth_guarantees
	 * @return 추가결과
	 */
	public int insertYouth_guarantees(Map<String,Object> youth_guarantees);
	
	/**
	 * 지역별행사정보를 추가한다.
	 * @param neighborhoods
	 * @return 추가결과
	 */
	public int insertNeighborhoods(Map<String,Object> neighborhoods);
	
	/**
	 * 청년 프로그램을 추가한다.
	 * @param programs
	 * @return 추가결과
	 */
	public int insertPrograms(Map<String,Object> programs);
	
}
