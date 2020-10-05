<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="org.apache.commons.lang3.StringUtils"%>
<%@page import="java.util.List"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<c:import url="/WEB-INF/jsp/egovframework/com/asapro/admin/layout/header.jsp" charEncoding="UTF-8" />
<script>

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
					            	
						            <form:form modelAttribute="adminPasswordForm" cssClass="" action="${ADMIN_PATH}/member/admin/password/update.do" method="post">
						            	<form:hidden path="adminId" />
										<div class="form-group row">
											<form:label path="adminId" cssClass="col-sm-2 col-form-label">관리자 아이디 </form:label>
											<div class="col-sm">
												<strong cssClass="form-control col-sm-3">${adminPasswordForm.adminId } </strong>
											</div>
										</div>
										<div class="form-group row">
											<form:label path="newPassword" cssClass="col-sm-2 col-form-label">관리자 비밀번호 <span class="text-danger">*</span></form:label>
											<div class="col-sm">
												<form:password path="newPassword" cssClass="form-control col-sm-3" cssErrorClass="form-control col-sm-3 is-invalid" placeholder="영문, 숫자, 특수문자만  9~20자 " />
												<form:errors path="newPassword" element="div" cssClass="text-danger col-form-label" />
											</div>
										</div>
										<div class="form-group row">
											<form:label path="newPasswordCheck" cssClass="col-sm-2 col-form-label">관리자 비밀번호 확인 <span class="text-danger">*</span></form:label>
											<div class="col-sm">
												<form:password path="newPasswordCheck" cssClass="form-control col-sm-3" cssErrorClass="form-control col-sm-3 is-invalid" placeholder="영문, 숫자, 특수문자만  9~20자 " />
												<form:errors path="newPasswordCheck" element="div" cssClass="text-danger col-form-label" />
											</div>
										</div>
										<div class="form-group">
											<button type="submit" class="btn btn-primary waves-effect waves-light">저장</button>
											<a href="${ADMIN_PATH}/member/admin/update.do?adminId=${adminPasswordForm.adminId }" class="btn btn-secondary waves-effect m-l-5">취소</a>
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