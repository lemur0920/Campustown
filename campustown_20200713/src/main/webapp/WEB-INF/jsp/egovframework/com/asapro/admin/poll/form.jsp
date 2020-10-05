<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="asapro" uri="http://www.asadal.com" %>

<c:import url="/WEB-INF/jsp/egovframework/com/asapro/admin/layout/header.jsp" charEncoding="UTF-8" />

<script>
jQuery(function($){
	
	//달력
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
					<!-- 입력폼 -->
					<div class="row">
					    <div class="col-sm-12">
					        <div class="card m-b-15">
					            <div class="card-body">
					            
									<!-- alert maeeage -->
									<c:import url="/WEB-INF/jsp/egovframework/com/asapro/admin/include/message.jsp" charEncoding="UTF-8" />
									
					            	<c:if test="${formMode == 'INSERT'}"><c:set var="actionUrl" value="${ADMIN_PATH}/poll/insert.do" /></c:if>
									<c:if test="${formMode == 'UPDATE'}"><c:set var="actionUrl" value="${ADMIN_PATH}/poll/update.do" /></c:if>
						            <form:form modelAttribute="pollForm" cssClass="" action="${actionUrl}" method="post" enctype="multipart/form-data">
										<form:hidden path="poId" />
										<form:hidden path="thumb.fileId" />
										
										<%-- <div class="form-group row arcMetaCode3">
											<form:label path="poType" cssClass="col-sm-2 col-form-label">유형 <span class="text-danger">*</span></form:label>
											<div class="col-sm form-inline">
												<form:select path="poType" cssClass="form-control col-sm-3" cssErrorClass="form-control col-sm-3 is-invalid">
													<option value="" label="선택">
													<form:options items="${pollTypeCodeList }" itemLabel="codeName" itemValue="codeId"/>
												</form:select>
												<form:errors path="poType" element="div" cssClass="text-danger col-form-label ml-2" />
											</div>	
										</div>
										 --%>
										<div class="form-group row">
											<form:label path="poImage" cssClass="col-sm-2 col-form-label">대표이미지 <span class="text-danger">*</span></form:label>
											<div class="col-sm">
												<div>
													<c:if test="${not empty pollForm.thumb && pollForm.thumb.fileId > 0}">
														<img class="img-thumbnail" style="max-width: 200px; max-height: 200px;" src="${pollForm.thumb.fileServletThumbnailUrl}" onerror="this.src='${CONTEXT_PATH }/assets/images/noImage300x300.jpg'" />
													</c:if>
												</div>
												<div>
													<input type="file" name="poImage" class="col-form-label" />
													<span class="bg-light font-weight-bold text-info">- jpg, jpeg, gif, png 파일만 업로드할 수 있습니다.</span>
													<form:errors path="poImage" element="div" cssClass="text-danger col-form-label ml-2" />
												</div>
												<div class="form-inline">
													<div class="col-form-label mr-2"><span class="text-danger">*</span> 대체텍스트 : </div>
													<form:input path="poThumbText" cssClass="form-control col-sm-6" cssErrorClass="form-control col-sm-6 is-invalid"  />
													<form:errors path="poThumbText" element="div" cssClass="text-danger col-form-label ml-2" />
												</div>
												<div>
													<c:if test="${not empty thumbFileInfoUploadResult}">
														<div class="text-danger">
															<div>게시물은 정상적으로 등록되었으나 다음의 파일을 업로드 하지 못하였습니다.</div>
															<ul>
																<c:forEach items="${thumbFileInfoUploadResult.notUploadableFiles}" var="item"><li>${item} 파일이 업로드 금지파일(업로드 금지확장자)에 해당합니다.</li></c:forEach>
																<c:forEach items="${thumbFileInfoUploadResult.uploadSizeOverFiles}" var="item"><li>${item} 파일이 업로드 파일사이즈를 초과하였습니다.</li></c:forEach>
																<c:forEach items="${thumbFileInfoUploadResult.altTextMissingFiles}" var="item"><li>${item} 파일의 대체텍스트가 누락되었습니다.</li></c:forEach>
															</ul>
														</div>
													</c:if>
												</div>

											</div>
										</div>
										
										<div class="form-group row">
											<form:label path="poQuestion" cssClass="col-sm-2 col-form-label">질문 <span class="text-danger">*</span></form:label>
											<div class="col-sm">
												<form:input path="poQuestion" cssClass="form-control col-sm-6" cssErrorClass="form-control col-sm-6 is-invalid"  />
												<form:errors path="poQuestion" element="div" cssClass="text-danger col-form-label ml-2" />
											</div>
										</div>
										
										<div class="form-group row">
											<form:label path="poDescription" cssClass="col-sm-2 col-form-label">부가설명 <span class="text-danger">*</span></form:label>
											<div class="col-sm">
												<form:textarea path="poDescription" cssClass="form-control col-sm-8" rows="3" placeholder="부가설명" />
												<form:errors path="poDescription" element="div" cssClass="text-danger" />
											</div>
										</div>
										
										<div class="form-group row date-prop">
											<form:label path="poStartDate" cssClass="col-sm-2 col-form-label">투표 시작일 <span class="text-danger">*</span></form:label>
											<div class="col-sm form-inline">
												<form:input path="poStartDate" cssClass="form-control col-sm-2 datepicker-ui" cssErrorClass="form-control col-sm-2 datepicker-ui is-invalid"/>
												<form:errors path="poStartDate" element="div" cssClass="text-danger col-form-label ml-2" />
											</div>
										</div>
										
										<div class="form-group row date-prop">
											<form:label path="poEndDate" cssClass="col-sm-2 col-form-label">투표 종료일 <span class="text-danger">*</span></form:label>
											<div class="col-sm form-inline">
												<form:input path="poEndDate" cssClass="form-control col-sm-2 datepicker-ui" cssErrorClass="form-control col-sm-2 datepicker-ui is-invalid" />
												<form:errors path="poEndDate" element="div" cssClass="text-danger col-form-label ml-2" />
											</div>
										</div>
										
										<div class="form-group row">
											<form:label path="poUse" cssClass="col-sm-2 col-form-label">게시여부 <span class="text-danger">*</span></form:label>
											<div class="col-sm col-form-label">
												<div class="custom-control custom-radio custom-control-inline">
													<form:radiobutton path="poUse" class="custom-control-input" value="true"/>
													<label for="poUse1" class="custom-control-label"> 게시</label>
												</div>
												<div class="custom-control custom-radio custom-control-inline">
													<form:radiobutton path="poUse" class="custom-control-input" value="false"/>
													<label for="poUse2" class="custom-control-label"> 게시안함</label>
												</div>
												<form:errors path="poUse" element="div" cssClass="text-danger" />
											</div>
										</div>
										
										<div class="form-group">
											<button type="submit" class="btn btn-primary waves-effect waves-light">저장</button>
											<a href="${ADMIN_PATH}/poll/list.do?poType=" class="btn btn-secondary waves-effect m-l-5">목록</a>
										</div>
									</form:form>
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