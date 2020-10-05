<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="asapro" uri="http://www.asadal.com" %>

<c:import url="/WEB-INF/jsp/egovframework/com/asapro/admin/layout/header.jsp" charEncoding="UTF-8" />
<!-- <script src="${CONTEXT_PATH }/assets/ckeditor/ckeditor4/ckeditor.js"></script> -->
<script src="${CONTEXT_PATH }/assets/ckeditor/ckeditor4/ckeditor.js"></script>
<!-- <script src="https://cdn.ckeditor.com/ckeditor5/16.0.0/classic/ckeditor.js"></script> -->

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
									
									<c:if test="${formMode eq 'INSERT'}"><c:set var="actionUrl" value="${ADMIN_PATH}/content/insert.do" /></c:if>
									<c:if test="${formMode eq 'UPDATE'}"><c:set var="actionUrl" value="${ADMIN_PATH}/content/update.do" /></c:if>
						            <form:form modelAttribute="contentForm" cssClass="" action="${actionUrl}" method="post">
						            	<form:hidden path="contentId" />
						            	<form:hidden path="contentRoot" />
										<form:hidden path="contentStatus" />
										<form:hidden path="contentVer" />
										<form:hidden path="menu.menuId" />
										
										<div class="form-group row">
											<form:label path="contentTitle" cssClass="col-sm-2 col-form-label">콘텐츠제목 <span class="text-danger">*</span></form:label>
											<div class="col-sm">
												<form:input path="contentTitle" cssClass="form-control col-sm-8" cssErrorClass="form-control col-sm-8 is-invalid" />
												<form:errors path="contentTitle" element="div" cssClass="text-danger col-form-label ml-2" />
											</div>
										</div>
										
										<div class="form-group row">
											<form:label path="content" cssClass="col-sm-2 col-form-label">콘텐츠 <span class="text-danger">*</span></form:label>
											<div class="col-sm">
												<form:textarea path="content" cssClass="form-control col-sm-8" rows="10" placeholder="" />
												<form:errors path="content" element="div" cssClass="text-danger col-form-label ml-2" />
											</div>
										</div>
										
										<div class="form-group row">
											<form:label path="contentMemo" cssClass="col-sm-2 col-form-label">비고 </form:label>
											<div class="col-sm">
												<form:textarea path="contentMemo" cssClass="form-control col-sm-8" rows="4" placeholder="" />
												<form:errors path="contentMemo" element="div" cssClass="text-danger" />
											</div>
										</div>
										
										
										<div class="form-group">
											<button type="submit" class="btn btn-primary waves-effect waves-light">저장</button>
											<a href="${ADMIN_PATH}/content/list.do" class="btn btn-secondary waves-effect m-l-5">목록</a>
											<c:if test="${not empty menuId }">
												<a href="${ADMIN_PATH}/menu/list.do?#${menuId}" class="btn btn-secondary waves-effect m-l-5">메뉴목록</a>
											</c:if>
										</div>
										
										<c:if test="${formMode ne 'INSERT'}">
											<c:if test="${useContenRevision}">
												<c:import url="/WEB-INF/jsp/egovframework/com/asapro/admin/content/revision.jsp" charEncoding="UTF-8" />
											</c:if>
										</c:if>
										
										
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

<script>
//CKEDITOR
  CKEDITOR.replace( 'content',{
	filebrowserImageUploadUrl : GLOBAL.ADMIN_PATH + '/file/editor/upload.do?module=content&moduleId=${contentForm.contentId}&moduleSub=content'
} ); 
</script>
<script>
    /*  ClassicEditor
        .create( document.querySelector( '#content' ),{
        	language: 'ko'
        } )
        .catch( error => {
            console.error( error );
        } );  */
</script>

<c:import url="/WEB-INF/jsp/egovframework/com/asapro/admin/layout/footer.jsp" charEncoding="UTF-8" />