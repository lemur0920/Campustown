<%@page import="egovframework.com.asapro.member.admin.service.AdminMember"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="asapro" uri="http://www.asadal.com" %>

<c:import url="/WEB-INF/jsp/egovframework/com/asapro/admin/layout/header.jsp" charEncoding="UTF-8" />

<!-- Nestable css -->
<link href="${CONTEXT_PATH }/assets/plugins/nestable/jquery.nestable.css" rel="stylesheet" />

<script>
jQuery(function($){
		
	
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
					
					<!-- 목록 -->
					<div class="row">
						<div class="col-sm-12">
							<div class="card m-b-30">
								<div class="card-body">
									
									<!-- alert maeeage -->
									<c:import url="/WEB-INF/jsp/egovframework/com/asapro/admin/include/message.jsp" charEncoding="UTF-8" />
									<!-- info -->
									<div class="p-3 mb-2 bg-light text-info font-weight-bold">
										<div><i class="mdi mdi-information"></i> 메뉴별 만족도 입니다.</div>
										<div><i class="mdi mdi-information"></i> 상세정보를 통해 참여정보와 평가의견을 확인할 수 있습니다.</div>
									</div>
									
									<div class="custom-dd-empty dd" id="nestable_list_1">
										<c:if test="${empty menuList}">
											등록된 메뉴가 없습니다.
										</c:if>
										
										<ol class="dd-list">
											<c:forEach items="${menuList}" var="item" varStatus="vs">
												<%--<c:if test="${item.menuStatus eq 'public' and item.menuUseSatisfaction }"> --%>
												<c:if test="${item.menuStatus ne 'locked' and item.menuUseSatisfaction }">
													<li class="dd-item dd3-item" data-id="${item.menuId}">
														<div class="dd3-content">
															<a href="${ADMIN_PATH}/satisfaction/view.do?menuId=${item.menuId}" class="" data-toggle="tooltip" data-placement="left" title="${item.menuLocation}"> ${item.menuName} </a> 
	                                                        <span class="mx-3 badge">${item.menuLocation}</span>
															<c:if test="${not empty satisfaction[item.menuId]}">
																<span class="text-success">(참여인원 : ${satisfaction[item.menuId].satisTotalCnt  } 명)</span>
		                                                        <span class="pull-right m-r-10">
		                                                        	<span class="m-r-10">만족도</span>
		                                                        	<c:if test="${satisfaction[item.menuId].satisTotalCnt <= 0 }">
																		<span class="font-18 badge badge-info">0 </span>
				                                                        <input type="hidden" class="rating" data-fractions="3" data-filled="mdi mdi-star font-20 text-primary" data-empty="mdi mdi-star-outline font-20 text-muted" data-readonly value="0"/>
		                                                        	</c:if>
		                                                        	<c:if test="${satisfaction[item.menuId].satisTotalCnt > 0 }">
				                                                        <span class="font-18 badge badge-info"><fmt:formatNumber value="${satisfaction[item.menuId].satisTotalScore / satisfaction[item.menuId].satisTotalCnt  }" pattern=".0"/> </span>
			                                                        	<input type="hidden" class="rating" data-fractions="3" data-filled="mdi mdi-star font-20 text-primary" data-empty="mdi mdi-star-outline font-20 text-muted" data-readonly value="${satisfaction[item.menuId].satisTotalScore / satisfaction[item.menuId].satisTotalCnt  }"/>
		                                                        	</c:if>
		                                                        </span>
															</c:if>
															<c:if test="${empty satisfaction[item.menuId]}">
																<span class="text-success">(참여인원 : 0 명)</span>
																<div class=" pull-right m-r-10">
																	<span class="m-r-10">만족도</span>
																	<span class="font-18 badge badge-info">0 </span>
			                                                        <input type="hidden" class="rating" data-fractions="3" data-filled="mdi mdi-star font-20 text-primary" data-empty="mdi mdi-star-outline font-20 text-muted" data-readonly value="0"/>
																</div>
															</c:if>
														</div>
													</li>
												</c:if>
											</c:forEach>
										</ol>
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
<script src="${CONTEXT_PATH }/assets/plugins/nestable/jquery.nestable.js"></script>

<c:import url="/WEB-INF/jsp/egovframework/com/asapro/admin/layout/footer.jsp" charEncoding="UTF-8" />