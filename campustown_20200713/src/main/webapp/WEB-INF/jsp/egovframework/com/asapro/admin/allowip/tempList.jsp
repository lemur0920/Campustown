<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<c:import url="/WEB-INF/jsp/egovframework/com/asapro/admin/layout/header.jsp" charEncoding="UTF-8" />
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
									<!-- info -->
									<div class="p-3 mb-2 bg-light text-info font-weight-bold">
										<div><i class="mdi mdi-information"></i> 외부관리자가 유동IP를 사용하고 있을경우 SMS인증 후 임시 허용하는 아이피 목록입니다.</div>
										<div><i class="mdi mdi-information"></i> 매일 오전 03시 모든 목록은 삭제됩니다.</div>
									</div>
									
									<!-- alert maeeage -->
									<c:import url="/WEB-INF/jsp/egovframework/com/asapro/admin/include/message.jsp" charEncoding="UTF-8" />
						            <div class="table-responsive">
										<table class="table table-striped table-hover">
											<colgroup>
												<col style="width: 30px;" />
												<col style="width: 25%;" />
												<col style="width: 22%;" />
												<col style="width: 12%;" />
												<col style="width: 12%;" />
												<col style="width: 12%;" />
												<col style="width: 12%;" />
											</colgroup>
											<thead>
												<tr>
													<th>번호</th>
													<th>임시허용IP</th>
													<th>아이디</th>
													<th>등록일시</th>
													<th>인증번호 요청일시</th>
													<th>인증완료여부</th>
													<th>인증완료일시</th>
												</tr>
											</thead>
											<tbody>
												<c:if test="${empty paging.result}">
													<tr>
														<td colspan="7" class="text-center">등록된 임시허용 IP가 없습니다.</td>
													</tr>
												</c:if>
												<c:forEach items="${paging.result}" var="item" varStatus="vs">
													<tr>
														<td>${paging.rowTop - vs.index}</td>
														<td>${item.tempIp}</td>
														<td>${item.adminId}</td>
														<td>
															<fmt:formatDate value="${item.tempRegdate}" pattern="yyyy-MM-dd HH:mm:ss" />
														</td>
														<td>
															<fmt:formatDate value="${item.certiRequestDate}" pattern="yyyy-MM-dd HH:mm:ss" />
														</td>
														<td>
															<c:if test="${item.authentication}"><span class="badge badge-info">인증</span></c:if>
															<c:if test="${!item.authentication}"><span class="badge badge-warning">미인증</span></c:if>
														</td>
														<td>
															<fmt:formatDate value="${item.certiCompletDate}" pattern="yyyy-MM-dd HH:mm:ss" />
														</td>
													</tr>
												</c:forEach>
											</tbody>
										</table>
									</div>
									<div class="form-inline">
										<div>
											<span>총 ${paging.rowTotal}건의 데이터가 있습니다.</span>
										</div>
									</div>

									<!-- paging -->
									<div>
										<c:out value="${paging.adminTextTag}" escapeXml="false"/>
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