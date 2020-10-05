<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<c:import url="/WEB-INF/jsp/egovframework/com/asapro/admin/layout/header.jsp" charEncoding="UTF-8" />


<script>
jQuery(function($){
		
	//접속일 기간 달력
	$('.datepicker-ui').datepicker();	
	
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

						            <form:form modelAttribute="statisticsSearch" cssClass="form-inline" action="${ADMIN_PATH}/statistics/menu.do?${statisticsSearch.queryString }" method="get">
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
					
					<!-- 목록 -->
					<div class="row">
						<div class="col-sm-12">
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
												<col style="width: 5%;" />
												<col />
												<col style="width: 20%;" />
											</colgroup>
											<thead>
												<tr>
													<th>번호</th>
													<th>메뉴명</th>
													<th>페이지뷰</th>
												</tr>
											</thead>
											<tbody>
												<c:if test="${empty menuList}">
													<tr>
														<td colspan="3" class="text-center">등록된 메뉴가 없습니다.</td>
													</tr>
												</c:if>
												
												<c:set var="i" value="1"/>
												<c:forEach items="${menuList}" var="item" varStatus="vs">
													<c:if test="${item.menuStatus ne 'locked' }">
															<tr>
																<td>${i}</td>
																<td><span <c:if test="${item.menuDepth > 1 }">style="margin-left: ${(item.menuDepth -1) * 20}px;"</c:if>>${item.menuName}</span></td>
																<c:if test="${item.menuType == 'link'}">
																	<td class="text-success">LINK</td>
																</c:if>
																<c:if test="${item.menuType != 'link'}">
																	<c:if test="${not empty statisticsMenuMap[item.menuId]}">
																		<td>${statisticsMenuMap[item.menuId]}</td>
																	</c:if>
																	<c:if test="${empty statisticsMenuMap[item.menuId]}">
																		<td>0</td>
																	</c:if>
																</c:if>
																
																<c:set var="i" value="${i+1 }"/>
															</tr>
													</c:if>
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