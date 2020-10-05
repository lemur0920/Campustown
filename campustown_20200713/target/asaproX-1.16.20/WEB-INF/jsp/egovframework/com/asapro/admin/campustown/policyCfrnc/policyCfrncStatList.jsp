<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="asapro" uri="http://www.asadal.com" %>

<c:import url="/WEB-INF/jsp/egovframework/com/asapro/admin/layout/header.jsp" charEncoding="UTF-8" />

<style>
.thTitle1 {
	background-color:#e3f2fd;
}
.thTitle2 {
	background-color:#bbdefb;
}
</style>

<script>
jQuery(function($){
	// 달력
	$('.datepicker-ui').datepicker();
	
	// 초기화 버튼
	$('#resetBtn').on('click', function(){
		$("#pageSize").prop("selectedIndex", 0);
		$("#sortOrder").prop("selectedIndex", 0);
		$("#sortDirection").prop("selectedIndex", 0);
		
		$("#scRegStartDt").val("");
		$("#scRegEndDt").val("");
		
		$("#scDiv").prop("selectedIndex", 0);
		$("#scSttus").prop("selectedIndex", 0);
		$("#scComptYn").prop("selectedIndex", 0);
		
		$("#sv").val("");
		$("#sc").prop("selectedIndex", 0);
	});
});
</script>
	<!-- Left Sidebar -->	
	<c:import url="/WEB-INF/jsp/egovframework/com/asapro/admin/layout/left.jsp" charEncoding="UTF-8" />
	
	<!-- Start right Content here -->
	<div class="content-page">
	    <!-- Start content -->
	    <div class="content">
	    
	    	<!-- Top Bar -->
			<c:import url="/WEB-INF/jsp/egovframework/com/asapro/admin/layout/top.jsp" charEncoding="UTF-8" />
			
	        <div class="page-content-wrapper ">
	        	<div class="container-fluid">
			
					<!-- location -->
					<c:import url="/WEB-INF/jsp/egovframework/com/asapro/admin/include/location.jsp" charEncoding="UTF-8" />
	        		
	        		<!-- ============================= 메뉴별 컨텐츠 ============================ -->
					<!-- 검색폼 -->
					<%-- 
					<div class="row">
					    <div class="col-sm-12">
					        <div class="card m-b-15">
					            <div class="card-body">
						            <form:form modelAttribute="policyCfrncSearch" cssClass="" action="${ADMIN_PATH}/policyCfrnc/list.do" method="get">
						        		<div class="form-row mr-auto mt-2 form-inline">
											<label class="mr-sm-2 mb-2">정렬조건</label>
											<form:select path="pageSize" cssClass="form-control mr-sm-2 mb-2" title="페이지크기" >
												<form:option value="10">10개씩</form:option>
												<form:option value="20">20개씩</form:option>
												<form:option value="40">40개씩</form:option>
											</form:select>
											
											<form:select path="sortOrder" cssClass="form-control mr-sm-2 mb-2" title="정렬기준">
												<form:option value="REG_DT" label="작성일시" />
												<form:option value="DIV" label="구분" />
											</form:select>
											
											<form:select path="sortDirection" cssClass="form-control mr-sm-2 mb-2" title="정렬방향">
												<form:option value="DESC" label="내림차순" />
												<form:option value="ASC" label="오름차순" />
											</form:select>
										</div>
										
										<div class="form-row mt-2 form-inline" >
											<label for="" class="mr-sm-3 mb-2">작성일</label>
											
											<form:input path="scRegStartDt" cssClass="form-control mr-sm-2 mb-2 datepicker-ui" cssErrorClass="form-control col-sm-1 datepicker-ui is-invalid" autocomplete="off" readonly="true"/>
											<span class="mx-2"> ~ </span>
											<form:input path="scRegEndDt" cssClass="form-control mr-sm-2 mb-2 datepicker-ui" cssErrorClass="form-control col-sm-1 datepicker-ui is-invalid" autocomplete="off" readonly="true"/>
										</div>
										
										<div class="form-row mt-2 form-inline" >
											<label class="mr-sm-2 mb-2">검색조건</label>
											
											<form:select path="scDiv" cssClass="form-control mr-sm-2 mb-2" title="관리구분">
												<option value="">구분</option>
												<form:options items="${divList}" itemLabel="mngDiv" itemValue="mngDiv"/>
											</form:select>
											<form:select path="scSttus" cssClass="form-control mr-sm-2 mb-2" title="상태">
												<option value="">상태</option>
												<form:options items="${sttusCodeList}" itemLabel="codeName" itemValue="codeId"/>
											</form:select>
											<form:select path="scComptYn" cssClass="form-control mr-sm-2 mb-2" title="완료여부">
												<option value="">완료여부</option>
												<form:options items="${comptYnList}" itemLabel="codeName" itemValue="codeId"/>
											</form:select>
											
											<form:select path="sc" id="scDiv" class="form-control mr-sm-2 mb-2" title="검색어 구분">
												<option value="">전체</option>
												<!-- <option value="1" label="지역" /> -->
												<option value="manageNo" label="관리번호" />
												<option value="sj" label="제목" />
												<option value="jobChargerDept" label="담당부서" />
												<option value="jobChargerNm" label="담당자" />
											</form:select>
											
											<form:input path="sv" cssClass="form-control mr-sm-2 mb-2" title="검색어" placeholder="Search"/>
											
											<button id="searchBtn" class="btn btn-outline-success waves-effect mr-sm-2 mb-2" type="submit">검색</button>
											<c:if test="${org ne 'university' && org ne 'startup'}">
												<button id="resetBtn" class="btn btn-outline-success waves-effect mr-sm-2 mb-2" type="button">초기화</button>
											</c:if>
											
										</div>
										
						        	</form:form>
						        </div>
					        </div>
				        </div>
			        </div>	  
			         --%>  
	        		<!-- End 검색폼 -->
	        		
	        		
	        		
	        		<!-- 목록 -->
					<div class="row">
						<div class="col-sm-12">
							<div class="card m-b-30">
								<div class="card-body">
									<!-- alert message -->
									<c:import url="/WEB-INF/jsp/egovframework/com/asapro/admin/include/message.jsp" charEncoding="UTF-8" />
	        						
	        						<div class="table-responsive">
	        							<table class="table table-striped table-hover ">
											<colgroup>
											</colgroup>
											
											<thead>
												<tr class="align-middle text-center">
													<th rowspan="2">구분</th>
													<th rowspan="2" class="thTitle1">합계</th>
													<th colspan="4" class="thTitle1">상태</th>
													<!-- <th colspan="2" class="thTitle2">완료여부</th> -->
												</tr>
												<tr class="align-middle text-center">
													<th class="thTitle1">${asapro:codeName('lo', sttusCodeList) }</th>
													<th class="thTitle1">${asapro:codeName('so', sttusCodeList) }</th>
													<th class="thTitle1">${asapro:codeName('re', sttusCodeList) }</th>
													<th class="thTitle1">${asapro:codeName('pr', sttusCodeList) }</th>
													<!-- 
													<th class="thTitle2">검토중</th>
													<th class="thTitle2">완료</th>
													 -->
												</tr>
												
											</thead>
											
											<tbody>
												<c:if test="${empty paging.result}">
													<tr>
														<td colspan="10" class="text-center">데이터가 없습니다.</td>
													</tr>
												</c:if>
												<!-- scDiv=2020+하반기+1회 -->
												<c:forEach items="${paging.result}" var="item" varStatus="vs">
													<c:set var="cardiStr" value="상반기" />
													<c:if test="${item.cardiNum eq 2}">
														<c:set var="cardiStr" value="하반기" />
													</c:if>
													<tr class="align-middle text-center">
														<td>${item.mngDiv}</td>
														<td>
															<a href="${ADMIN_PATH}/policyCfrnc/list.do?scDiv=${item.manageYear}+${cardiStr}+${item.manageOrdr}회" target="_blank">
																${item.totSttus }
															</a>
														</td>
														<td>
															<a href="${ADMIN_PATH}/policyCfrnc/list.do?scDiv=${item.manageYear }+${cardiStr}+${item.manageOrdr }회&scSttus=lo" target="_blank">
																${item.sttusLo }
															</a>
														</td>
														<td>
															<a href="${ADMIN_PATH}/policyCfrnc/list.do?scDiv=${item.manageYear }+${cardiStr}+${item.manageOrdr }회&scSttus=so" target="_blank">
																${item.sttusSo }
															</a>
														</td>
														<td>
															<a href="${ADMIN_PATH}/policyCfrnc/list.do?scDiv=${item.manageYear }+${cardiStr}+${item.manageOrdr }회&scSttus=re" target="_blank">
																${item.sttusRe }
															</a>
														</td>
														<td>
															<a href="${ADMIN_PATH}/policyCfrnc/list.do?scDiv=${item.manageYear }+${cardiStr}+${item.manageOrdr }회&scSttus=pr" target="_blank">
																${item.sttusPr }
															</a>	
														</td>
														<%-- 
														<td>
															<a href="${ADMIN_PATH}/policyCfrnc/list.do?scDiv=${item.manageYear }+${item.manageOrdr }차&scComptYn=N" target="_blank">
																${item.comptN }
															</a>
														</td>
														
														<td>
															<a href="${ADMIN_PATH}/policyCfrnc/list.do?scDiv=${item.manageYear }+${item.manageOrdr }차&scComptYn=Y" target="_blank">
																${item.comptY }
															</a>
														</td>
														 --%>
													</tr>
												</c:forEach>
												
											</tbody>
										</table>	
	        						</div>
	        						
	        						
	        						<div class="form-inline">
										<%-- 
										<div class="mr-auto">
											<a href="${ADMIN_PATH}/policyCfrnc/insert.do" class="btn btn-primary waves-effect waves-light">등록</a>
										</div>
										 --%>
										<div>
											<span>총 ${paging.rowTotal}건의 데이터가 있습니다.</span>
										</div>
									</div>
	        						
	        						<!-- paging -->
									<%-- 
									<div>
										<c:out value="${paging.adminTextTag}" escapeXml="false"/>
									</div>
	        		 				--%>
	        					</div>
        					</div>
       					</div>
     				</div>
	        		<!-- End 목록 -->
	        		
	        		
	        		
	        	
	        	</div> <!-- End container -->
	        
	        
	    	</div> <!-- End Page content Wrapper -->
	    	
	    </div> <!-- End content -->
	</div>    
	<!-- End Right content here -->

<c:import url="/WEB-INF/jsp/egovframework/com/asapro/admin/layout/footer.jsp" charEncoding="UTF-8" />