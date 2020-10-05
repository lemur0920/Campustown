<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
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
					<!-- 검색폼 -->
					<div class="row">
					    <div class="col-sm-12">
					        <div class="card m-b-15">
					            <div class="card-body">

						            <form:form modelAttribute="jobabaFSearch" cssClass="form-inline" action="${ADMIN_PATH}/openapi/jobabaF/list.do" method="get">
										<div class="form-row mr-auto mt-2">
											<label class="mr-sm-2 mb-2">정렬조건</label>
											<form:select path="sortOrder" cssClass="form-control mr-sm-2 mb-2" title="정렬기준">
												<form:option value="SEQ" label="seq" />
											</form:select>
											<form:select path="sortDirection" cssClass="form-control mr-sm-2 mb-2" title="정렬방향">
												<form:option value="ASC" label="오름차순" />
												<form:option value="DESC" label="내림차순" />
											</form:select>
										</div>
										
										<div class="form-row mt-2" >
											<label for="sc" class="mr-sm-2 mb-2">검색조건</label>
											<form:select path="clCd" cssClass="form-control mr-sm-2 mb-2" title="카테고리">
												<option value="">카테고리</option>
												<form:options items="${jobabaCateCodeList}" itemLabel="codeName" itemValue="codeId"/>
											</form:select>
											<form:select path="region" cssClass="form-control mr-sm-2 mb-2" title="지역">
												<option value="">지역</option>
												<form:options items="${gyeonggiCodeList}" itemLabel="codeName" itemValue="codeName"/>
											</form:select>
											<form:select path="recruitYn" cssClass="form-control mr-sm-2 mb-2" title="채용중여부">
												<option value="">진행상태</option>
												<form:option value="Y" label="채용중" />
												<form:option value="N" label="종료" />
											</form:select>
											<form:select path="sc" cssClass="form-control mr-sm-2 mb-2" title="검색조건">
												<option value="">전체</option>
												<form:option value="COMPANY_NM" label="회사명" />
												<form:option value="COMMENT" label="기업소개글" />
											</form:select>	
											<form:input path="sv" cssClass="form-control mr-sm-2 mb-2" title="검색어" placeholder="Search"/>
											<button class="btn btn-outline-success waves-effect mb-2 " type="submit">검색</button>
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
									<h4 class="mt-0 header-title">탐나는 기업정보 목록</h4>
								</div>
								<div class="card-body">
									
									<!-- info -->
									<!-- <div class="p-3 mb-2 bg-light text-info font-weight-bold"><i class="mdi mdi-information"></i> 순서변경은 드래그하여 이동 후 순서저장버튼</div> -->
									
									<!-- alert maeeage -->
									<c:import url="/WEB-INF/jsp/egovframework/com/asapro/admin/include/message.jsp" charEncoding="UTF-8" />
						           
						            <div class="table-responsive">
										<table class="table table-striped table-hover">
											<colgroup>
												<col style="width: 40px;" /><!-- 순서 -->
												<col style="width: 120px; "/><!-- 이미지 -->
												<col style="width: 15%; "/><!-- 카테고리 -->
												<col style="width: 10%; "/><!-- 지역 -->
												<col /><!-- 회사명 -->
												<col style="width: 10%; "/><!-- 채용중여부 -->
											</colgroup>
											<thead>
												<tr>
													<th class="text-center">
														번호
													</th>
													<th>이미지</th>
													<th>카테고리</th>
													<th>지역</th>
													<th>회사명</th>
													<th>채용중여부</th>
												</tr>
											</thead>
											<tbody>
											
												<c:if test="${empty paging.result}">
													<tr>
														<td colspan="6" class="text-center">등록된 데이터가 없습니다.</td>
													</tr>
												</c:if>
												<c:forEach items="${paging.result}" var="item" varStatus="vs">
													<tr>
														<td class="text-center">
															${paging.rowTop - vs.index}
														</td>
														<td>
															<img class="img-thumbnail" style="max-width: 90px; max-height: 90px;" src="${item.imageUrl}" alt="${item.companyNm}" onerror="this.src='${CONTEXT_PATH }/assets/images/noImage300x300.jpg'" />
														</td>
														<td>
															${asapro:codeName(item.clCd, jobabaCateCodeList)}
														</td>
														<td>
															${item.region}
														</td>
														<td style="word-break: break-all">
															<a href="${ADMIN_PATH}/openapi/jobabaF/view.do?seq=${item.seq}">${item.companyNm}</a>
														</td>
														<td>
															<c:if test="${item.recruitYn eq 'Y'}"><span class="badge badge-success">채용중</span></c:if>
															<c:if test="${item.recruitYn eq 'N'}"><span class="badge badge-warning">종료</span></c:if>
														</td>
														
													</tr>
												</c:forEach>
											</tbody>
										</table>
									</div>
									<div class="form-inline">
										<div class="mr-auto">
											<%-- <a href="${ADMIN_PATH}/openapi/jobabaF/insert.do" class="btn btn-primary waves-effect waves-light">추가</a>
											<button id="deleteBtn" class="btn btn-danger waves-effect waves-light">삭제</button> --%>
										</div>
										<div>
											<span>총 ${paging.rowTotal}개의 게시물이 있습니다.</span>
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