<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="asapro" uri="http://www.asadal.com" %>

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
					<!-- 입력폼 -->
					<div class="row">
					    <div class="col-sm-12">
					        <div class="card m-b-15">
					        	<div class="card-header">
									<div><i class="mdi mdi-information"></i> 탐나는 기업 정보 </div>
					        	</div>
					            <div class="card-body">
					            
									<!-- alert maeeage -->
									<c:import url="/WEB-INF/jsp/egovframework/com/asapro/admin/include/message.jsp" charEncoding="UTF-8" />
					            	
									<div class="table-responsive-lg">
										<table class="table table-bordered">
											<colgroup>
												<col style="width: 14%;" />
												<col style="width: 12%;" />
												<col style="width: 30%;"/>
												<col style="width: 12%;"/>
												<col style="width: 30%;" />
											</colgroup>
											<tbody>
												<tr>
													<th class="text-center bg-light align-middle" rowspan="5" scope="row">기업정보</th>
													<th class="text-center bg-light align-middle" scope="row">회사명</th>
													<td class="text-center align-middle" colspan="4"><h4 class="header-title">${jobabaFForm.companyNm }</h4></td>
												</tr>
												<tr>
													<th class="text-center bg-light align-middle" scope="row">카테고리</th>
													<td class="text-center align-middle">${asapro:codeName(jobabaFForm.clCd, jobabaCateCodeList) }</td>
													<th class="text-center bg-light align-middle" scope="row">지역</th>
													<td class="text-center align-middle">${jobabaFForm.region }</td>
												</tr>
												<tr>
													<th class="text-center bg-light align-middle" scope="row">이미지</th>
													<td class="text-center align-middle">
														<img class="img-thumbnail" style="max-width: 120px; max-height: 120px;" src="${jobabaFForm.imageUrl}" alt="${jobabaFForm.companyNm}" onerror="this.src='${CONTEXT_PATH }/assets/images/noImage300x300.jpg'" />
													</td>
													<th class="text-center bg-light align-middle" scope="row">채용중여부</th>
													<td class="text-center align-middle">
														<c:if test="${jobabaFForm.recruitYn eq 'Y'}"><span class="badge badge-success">채용중</span></c:if>
														<c:if test="${jobabaFForm.recruitYn eq 'N'}"><span class="badge badge-warning">종료</span></c:if>
													</td>
												</tr>
												<tr>
													<th class="text-center bg-light align-middle" scope="row">주소</th>
													<td class=" align-middle" colspan="4">${jobabaFForm.address }</td>
												</tr>
												<tr>
													<th class="text-center bg-light align-middle" scope="row">상세주소</th>
													<td class=" align-middle" colspan="4">${jobabaFForm.addressDtl }</td>
												</tr>
												<tr>
													<th class="text-center bg-light align-middle" scope="row">내용</th>
													<td class=" align-middle" colspan="4">${asapro:nl2br(jobabaFForm.comment, false) }</td>
												</tr>
												<tr>
													<th class="text-center bg-light align-middle" scope="row">처리상태</th>
													<td class=" align-middle" colspan="4">
														<a href="${jobabaFForm.jobabaUrl }" target="_blank" class="btn btn-primary waves-effect waves-light">상세정보 바로가기</a>
													</td>
												</tr>
											</tbody>
										</table>
									
									</div>
									
									<div class="form-group">
										<!-- <button type="submit" class="btn btn-primary waves-effect waves-light">저장</button> -->
										<a href="${ADMIN_PATH}/openapi/jobabaF/list.do" class="btn btn-secondary waves-effect m-l-5">목록</a>
									</div>
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