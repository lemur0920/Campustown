<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="asapro" uri="http://www.asadal.com" %>

<c:import url="/WEB-INF/jsp/egovframework/com/asapro/admin/layout/header.jsp" charEncoding="UTF-8" />

<script>
jQuery(function($){
	//generating chart
	c3.generate({
        bindto: '#chart',
        data: {
            columns: [
                ['매우만족', ${satisfaction.satisScore5}],
                ['만족', ${satisfaction.satisScore4}],
                ['보통', ${satisfaction.satisScore3}],
                ['불만', ${satisfaction.satisScore2}],
                ['매우불만', ${satisfaction.satisScore1}]
            ],
            type: 'bar'
        }
    });
	
	//Donut Chart
    c3.generate({
         bindto: '#donut-chart',
        data: {
            columns: [
                ['매우만족', ${satisfaction.satisScore5}],
                ['만족', ${satisfaction.satisScore4}],
                ['보통', ${satisfaction.satisScore3}],
                ['불만', ${satisfaction.satisScore2}],
                ['매우불만', ${satisfaction.satisScore1}]
            ],
            type : 'donut'
        },
        donut: {
            title: "만족도 비율",
            width: 50,
			label: { 
				show:false
			}
        },
        color: {
        	pattern: ['#4c84ff', "#ebeff2", '#6fd088', '#f75285', '#be9ef7']
        }
    });
	
	//평가의견 목록 
	$('#opinionlist').load('${ADMIN_PATH}/satisfaction/opinionList.do?menuId=${menu.menuId}');
	
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
					<!-- 입력폼 -->
					<div class="row">
					    <div class="col-sm-6">
					        <div class="card m-b-30">
					        	<h6 class="card-header mt-0">
					        		만족도조사결과
					        	</h6>
					            <div class="card-body">
					            
					            	<!-- info -->
									<div class="p-3 mb-3 bg-light font-weight-bold">
										<div class="p-1">
											${menu.menuName } <span class="badge ml-4">(${menu.menuLocation })</span>
										 </div>
										<div class="p-1">
											<span class="text-info">참여인원 : ${satisfaction.satisTotalCnt  } 명</span>
										</div>
										<div class="p-1">
											<c:if test="${satisfaction.satisTotalCnt <= 0 }">
												<span class="font-18 badge badge-info">0 </span>
			                                    <input type="hidden" class="rating" data-fractions="3" data-filled="mdi mdi-star font-20 text-primary" data-empty="mdi mdi-star-outline font-20 text-muted" data-readonly value="0"/>
											</c:if>
											<c:if test="${satisfaction.satisTotalCnt > 0 }">
												<span class="font-18 badge badge-info"><fmt:formatNumber value="${satisfaction.satisTotalScore / satisfaction.satisTotalCnt  }" pattern=".0"/> </span>
			                                    <input type="hidden" class="rating" data-fractions="3" data-filled="mdi mdi-star font-20 text-primary" data-empty="mdi mdi-star-outline font-20 text-muted" data-readonly value="${satisfaction.satisTotalScore / satisfaction.satisTotalCnt  }"/>
											</c:if>
										</div>
									</div>
					            	<div class="m-5">
					            		<!-- bar chart -->
										<h4 class="mt-0 header-title">만족도별 수</h4>
	                                    <p class="text-muted m-b-30 font-14">Bar Chart.</p>
	                                    <div id="chart" class="m-b-30"></div>
	                                    
					            		<!-- donut chart -->
	                                    <h4 class="mt-0 header-title">만족도별 비율</h4>
	                                    <p class="text-muted m-b-30 font-14">Donut Chart.</p>
	                                    <div id="donut-chart" class="m-b-30"></div>
                                    </div>
					            	
					            </div>
					        </div>
					    </div>
					    <div class="col-sm-6">
					        <div class="card m-b-15">
					        	<h6 class="card-header mt-0">
					        		평가의견
					        	</h6>
					            <div class="card-body" id="opinionlist">
					            	
					            </div>
					        </div>
					    </div>
					</div>
					<!-- //입력폼 -->
					
					<!-- ============================= //메뉴별 컨텐츠 ============================ -->
			    </div><!-- container -->
			
			
			</div> <!-- Page content Wrapper -->
	
	    </div> <!-- content -->
	
	</div>
	<!-- End Right content here -->
	
<c:import url="/WEB-INF/jsp/egovframework/com/asapro/admin/layout/footer.jsp" charEncoding="UTF-8" />