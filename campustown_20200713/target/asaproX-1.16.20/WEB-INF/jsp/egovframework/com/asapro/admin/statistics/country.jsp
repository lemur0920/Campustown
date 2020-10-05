<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<c:import url="/WEB-INF/jsp/egovframework/com/asapro/admin/layout/header.jsp" charEncoding="UTF-8" />


<script>
jQuery(function($){
		
	//접속일 기간 달력
	$('.datepicker-ui').datepicker();	
	
	
	 var $data  = [
		<c:if test="${not empty statisticsCountryMapList }">
			<c:forEach items="${statisticsCountryMapList }" var="item" varStatus="vs">
				[ '${item.COUNTRY_KEY}', ${item.CNT_BY_COUNTRY } ]<c:if test="${!vs.last }">,</c:if>
				
			</c:forEach>
		</c:if>
        ];
	 
	//Pie Chart
    var chartPie = c3.generate({
         bindto: '#pie-chart',
        data: {
            columns: $data,
            type : 'pie'
        },
        color: {
            //pattern: ["#0097a7", "#ebeff2","#f32f53","#ffbb44"]
        },
        pie: {
	        label: {
	          show: true
	        }
	    }
    });
	
	//목록에 차트의 색과 동일하게 색처리
    var chartPieColors = chartPie.data.colors();
    <c:if test="${not empty statisticsCountryMapList }">
		<c:forEach items="${statisticsCountryMapList }" var="item" varStatus="vs">
		    $('.item${vs.count}').css("color", chartPieColors['${item.COUNTRY_KEY}']);
		</c:forEach>
	</c:if>
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

						            <form:form modelAttribute="statisticsSearch" cssClass="form-inline" action="${ADMIN_PATH}/statistics/country.do?${statisticsSearch.queryString }" method="get">
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
					
					<div class="row">

						<!-- 목록 -->
						<div class="col-sm-6">
							<div class="card m-b-30">
								<div class="card-header">
									<h4 class="mt-0 header-title">목록</h4>
								</div>
								<div class="card-body">
									<!-- alert maeeage -->
									<c:import url="/WEB-INF/jsp/egovframework/com/asapro/admin/include/message.jsp" charEncoding="UTF-8" />
						           
						            <div class="table-responsive">
										<table class="table table-striped table-hover">
											<colgroup>
												<col style="width: 8%;" />
												<col />
												<col style="width: 20%;" />
											</colgroup>
											<thead>
												<tr>
													<th>번호</th>
													<th>국가</th>
													<th>세션(사용자)</th>
												</tr>
											</thead>
											<tbody>
												<c:if test="${empty statisticsCountryMapList}">
													<tr>
														<td colspan="3" class="text-center">데이터가 없습니다.</td>
													</tr>
												</c:if>
												<c:forEach items="${statisticsCountryMapList}" var="item" varStatus="vs">
													<tr>
														<td>${vs.count}</td>
														<td class="item${vs.count }">${item.COUNTRY_KEY}</td>
														<td>${item.CNT_BY_COUNTRY}</td>
														
													</tr>
												</c:forEach>
											</tbody>
										</table>
									</div>
									
								</div>
							</div>
						</div>
						
						<!-- 차트 -->
						<div class="col-lg-6">
							<div class="card m-b-30">
								<div class="card-header">
									<h4 class="mt-0 header-title">Pie Chart</h4>
								</div>
								<div class="card-body">
                                     <p class="text-muted m-b-30 font-14">국가별 세션통계를 보여줍니다.</p>
                                     
                                     <div id="pie-chart"></div>
								</div>
							</div>
						</div>
						
					</div>
					
					
					<!-- ============================= //메뉴별 컨텐츠 ============================ -->
			    </div><!-- container -->
			
			
			</div> <!-- Page content Wrapper -->
	
	    </div> <!-- content -->
	
	</div>
	<!-- End Right content here -->

<c:import url="/WEB-INF/jsp/egovframework/com/asapro/admin/layout/footer.jsp" charEncoding="UTF-8" />