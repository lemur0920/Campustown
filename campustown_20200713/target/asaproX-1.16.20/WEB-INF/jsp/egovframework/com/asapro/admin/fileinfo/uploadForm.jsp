<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="asapro" uri="http://www.asadal.com" %>

<c:import url="/WEB-INF/jsp/egovframework/com/asapro/admin/layout/header.jsp" charEncoding="UTF-8" />
<script src="${CONTEXT_PATH }/assets/plugins/dropzone/dist/dropzone.js"></script>
<link type="text/css" rel="stylesheet" href="${CONTEXT_PATH }/assets/plugins/dropzone/dist/dropzone.css">
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
					            <div class="card-body">
					            
									<!-- alert maeeage -->
									<c:import url="/WEB-INF/jsp/egovframework/com/asapro/admin/include/message.jsp" charEncoding="UTF-8" />
									
									<h4 class="mt-0 header-title">Dropzone</h4>
									<p class="text-muted m-b-30 font-14">업로드 파일을 선택하거나 드래그 드랍하세요.
									</p>

									<div class="m-b-30">
					            		<form:form modelAttribute="fileInfoForm" cssClass="dropzone" action="${ADMIN_PATH}/file/upload.do" method="post" enctype="multipart/form-data">
											<div class="fallback">
												<input name=file type="file" multiple="multiple">
											</div>
										</form:form>
									</div>
													
									<div class="form-group">
										<!-- <button type="button" class="uploadBtn btn btn-primary waves-effect waves-light">업로드</button> -->
										<a href="${ADMIN_PATH}/fileinfo/list.do" class="btn btn-secondary waves-effect m-l-5">목록</a>
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