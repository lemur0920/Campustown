<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<c:import url="/WEB-INF/jsp/egovframework/com/asapro/admin/layout/header.jsp" charEncoding="UTF-8" />


<script>
jQuery(function($){
		
	//접속일 기간 달력
	$('.datepicker-ui').datepicker();	
	
	
	//creates line chart
    createLineChart = function(element, data, xkey, ykeys, labels, lineColors) {
        Morris.Area({
          element: element,
          data: data,
          xkey: xkey,
          ykeys: ykeys,
          labels: labels,
          xLabels: '${statisticsSearch.statisDateType}',	//hour,day,week,month
          //xLabelFormat:function (date) { return date+'~'+(date+2); },
          hideHover: 'auto',
          gridLineColor: '#eef0f2',
          resize: true, //defaulted to true
          lineColors: lineColors
        });
    }

    init = function() {

        //create line chart
        var $data  = [
			<c:if test="${not empty statisticsSessionMapList }">
			<c:forEach items="${statisticsSessionMapList }" var="item" varStatus="vs">
				{ date: '${item.DATE_KEY}', value: ${item.CNT_BY_DAY } }<c:if test="${!vs.last }">,</c:if>
				
			</c:forEach>
			</c:if>
          ];
        createLineChart('morris-line', $data, 'date', ['value'], ['세션수'], ['#0f9cf3']);
        
    }
    
    init();
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
					<div class="row">
					    <div class="col-sm-12">
					        <div class="card m-b-15">
					            <div class="card-body">

						            <form:form modelAttribute="statisticsSearch" cssClass="form-inline" action="${ADMIN_PATH}/statistics/session.do?${statisticsSearch.queryString }" method="get">
										<form:hidden path="statisDateType"/>
										<div class="form-row  mt-2">
											<label class="mr-sm-3 mb-2">검색날짜</label>
											<form:input path="statisStartDate" cssClass="form-control mr-sm-2 mb-2 datepicker-ui" cssErrorClass="form-control mr-sm-2 mb-2 datepicker-ui is-invalid" autocomplete="off"/>
											<span class="mx-2 mb-2"> ~ </span>
											<form:input path="statisEndDate" cssClass="form-control mr-sm-2 mb-2 datepicker-ui" cssErrorClass="form-control mr-sm-2 mb-2 datepicker-ui is-invalid" autocomplete="off"/>
											<button class="btn btn-outline-success waves-effect mb-2 mx-2" type="submit">검색</button>
										</div>
										<div class="form-row mt-2" >
										</div>
									</form:form>
					            </div>
					        </div>
					    </div>
					</div>
					<!-- //검색폼 -->
					
					<!-- 차트 -->
					<div class="row">
						<div class="col-lg-12">
							<div class="card m-b-30">
								<div class="card-header">
									<h4 class="mt-0 header-title">Line Chart</h4>
									<ul class="nav nav-tabs mb-1" role="tablist">
										<li class="nav-item">
											<a class="nav-link<c:if test="${statisticsSearch.statisDateType eq 'hour'}"> active show</c:if>" href="${ADMIN_PATH}/statistics/session.do?statisDateType=hour&statisStartDate=${statisticsSearch.statisStartDate }&statisEndDate=${statisticsSearch.statisEndDate }" role="tab" aria-selected="false"> 시간 </a>
										</li>
										<li class="nav-item">
											<a class="nav-link<c:if test="${statisticsSearch.statisDateType eq 'day'}"> active show</c:if>" href="${ADMIN_PATH}/statistics/session.do?statisDateType=day&statisStartDate=${statisticsSearch.statisStartDate }&statisEndDate=${statisticsSearch.statisEndDate }" role="tab" aria-selected="true"> &nbsp;일&nbsp; </a>
										</li>
										<li class="nav-item">
											<a class="nav-link<c:if test="${statisticsSearch.statisDateType eq 'week'}"> active show</c:if>" href="${ADMIN_PATH}/statistics/session.do?statisDateType=week&statisStartDate=${statisticsSearch.statisStartDate }&statisEndDate=${statisticsSearch.statisEndDate }" role="tab" aria-selected="true"> &nbsp;주&nbsp; </a>
										</li>
										<li class="nav-item">
											<a class="nav-link<c:if test="${statisticsSearch.statisDateType eq 'month'}"> active show</c:if>" href="${ADMIN_PATH}/statistics/session.do?statisDateType=month&statisStartDate=${statisticsSearch.statisStartDate }&statisEndDate=${statisticsSearch.statisEndDate }" role="tab" aria-selected="true"> &nbsp;월&nbsp; </a>
										</li>
									</ul>
								</div>
								<div class="card-body">
                                     <p class="text-muted m-b-30 font-14 d-inline-block text-truncate w-100">시, 일, 주, 월별 세션통계를 보여줍니다.</p>

                                     
                                     <div id="morris-line" style="height: 300px"></div>
								</div>
							</div>
						</div>
					</div>
					
					<!-- 목록 -->
					<div class="row">
						<div class="col-sm-12">
							<div class="card m-b-30">
							
								<div class="card-header">
									<h4 class="mt-0 header-title">목록</h4>
								</div>
								
								<div class="card-body">
								<%-- 
									<!-- info -->
									<div class="p-3 mb-2 bg-light text-info font-weight-bold"><i class="mdi mdi-information"></i> 관리자 접속로그 목록</div>
								--%>
									
									<!-- alert maeeage -->
									<c:import url="/WEB-INF/jsp/egovframework/com/asapro/admin/include/message.jsp" charEncoding="UTF-8" />
						           
						            <div class="table-responsive">
										<table class="table table-striped table-hover">
											<colgroup>
												<col style="width: 5%;" />
												<col />
												<c:if test="${statisticsSearch.statisDateType eq 'week'}">
												<col style="width: 15%;" />
												<col style="width: 15%;" />
												</c:if>
												<col style="width: 15%;" />
											</colgroup>
											<thead>
												<tr>
													<th>번호</th>
													<th>날짜</th>
													<c:if test="${statisticsSearch.statisDateType eq 'week'}">
													<th>년도별 주차</th>
													<th>월별 주차</th>
													</c:if>
													<th>세션</th>
												</tr>
											</thead>
											<tbody>
												<c:if test="${empty statisticsSessionMapList}">
													<tr>
														<td colspan="5" class="text-center">데이터가 없습니다.</td>
													</tr>
												</c:if>
												<c:forEach items="${statisticsSessionMapList}" var="item" varStatus="vs">
													<tr>
														<td>${vs.count}</td>
														<c:if test="${statisticsSearch.statisDateType ne 'week'}">
															<td>${item.DATE_KEY}</td>
														</c:if>
														<c:if test="${statisticsSearch.statisDateType eq 'week'}">
															<td>${item.WEEK_START} ~ ${item.WEEK_END}</td>
															<td>${item.WEEK_OF_YEAR_ISO}</td>
															<td>${item.WEEK_OF_MONTH}</td>
														</c:if>
														<td>${item.CNT_BY_DAY}</td>
														
													</tr>
												</c:forEach>
											</tbody>
										</table>
									</div>
									
								</div>
							</div>
						</div>
					</div><!-- 목록 -->
					
					<!-- ============================= //메뉴별 컨텐츠 ============================ -->
			    </div><!-- container -->
			
			
			</div> <!-- Page content Wrapper -->
	
	    </div> <!-- content -->
	
	</div>
	<!-- End Right content here -->

<c:import url="/WEB-INF/jsp/egovframework/com/asapro/admin/layout/footer.jsp" charEncoding="UTF-8" />