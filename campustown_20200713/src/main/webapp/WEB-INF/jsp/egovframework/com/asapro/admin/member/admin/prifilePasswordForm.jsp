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
									
									<c:if test="${chkPwdPeriod }">
									<div class="alert alert-primary" role="alert">
										<h4 class="alert-heading">비밀번호 변경</h4>
										<hr>
										<p>회원님의 개인정보를 안전하게 보호하고, 시스템의 안전한 관리를 위해 <strong>${securityConfigMap.pwd_change_days }일 이상</strong> 비밀번호를 변경하지 않을경우 비밀번호변경을 권장하고 있습니다.</p>
										<p class="mb-0">불편하시더라도 비밀번호를 주기적으로 변경하셔서 안전하게 사용하세요.</p>
									</div>
									</c:if>
									
						            <form:form modelAttribute="adminPasswordForm" cssClass="" action="${ADMIN_PATH}/member/admin/profile/password/update.do" method="post">
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
											<button type="submit" class="btn btn-primary waves-effect waves-light">변경하기</button>
											<c:if test="${!chkPwdPeriod }">
											<a href="${ADMIN_PATH}/member/admin/profile/update.do" class="btn btn-secondary waves-effect m-l-5">취소</a>
											</c:if>
											<c:if test="${chkPwdPeriod }">
											<a href="${ADMIN_PATH}/dashboard.do" class="btn btn-secondary waves-effect m-l-5">다음에 변경하기</a>
											</c:if>
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