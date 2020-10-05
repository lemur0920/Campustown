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
					            	
					            	<c:if test="${formMode == 'INSERT'}"><c:set var="actionUrl" value="${ADMIN_PATH}/organization/organization/insert.do" /></c:if>
									<c:if test="${formMode == 'UPDATE'}"><c:set var="actionUrl" value="${ADMIN_PATH}/organization/organization/update.do" /></c:if>
						            <form:form modelAttribute="organizationForm" cssClass="" action="${actionUrl}" method="post">
										<div class="form-group row">
											<form:label path="orgId" cssClass="col-sm-2 col-form-label">기관아이디 <span class="text-danger">*</span></form:label>
											<c:if test="${formMode == 'INSERT'}">
												<form:input path="orgId" cssClass="form-control col-sm-3" cssErrorClass="form-control col-sm-3 is-invalid" maxlength="25" id="thresholdconfig" placeholder="- 영문, 언더바(_)만 사용할 수 있습니다." />
											</c:if>
											<c:if test="${formMode == 'UPDATE'}">
												<form:input path="orgId" cssClass="form-control col-sm-3" readonly="true" cssErrorClass="form-control col-sm-3 is-invalid" maxlength="25" id="thresholdconfig" placeholder="- 영문, 언더바(_)만 사용할 수 있습니다." />
											</c:if>
											<form:errors path="orgId" element="div" cssClass="text-danger col-form-label ml-2" />
										</div>
										<div class="form-group row">
											<form:label path="orgName" cssClass="col-sm-2 col-form-label">기관명 <span class="text-danger">*</span></form:label>
											<form:input path="orgName" cssClass="form-control col-sm-3" cssErrorClass="form-control col-sm-3 is-invalid" />
											<form:errors path="orgName" element="div" cssClass="text-danger col-form-label ml-2" />
										</div>
										<div class="form-group row">
											<form:label path="orgNameEn" cssClass="col-sm-2 col-form-label">기관 영문명 </form:label>
											<form:input path="orgNameEn" cssClass="form-control col-sm-3" />
											<form:errors path="orgNameEn" element="div" cssClass="text-danger" />
										</div>
										<div class="form-group row">
											<form:label path="orgDescription" cssClass="col-sm-2 col-form-label">기관 소개 </form:label>
											<form:textarea path="orgDescription" cssClass="form-control col-sm-5" rows="5" placeholder="기관소개" />
											<form:errors path="orgDescription" element="div" cssClass="text-danger" />
										</div>
										<div class="form-group row">
											<form:label path="orgUse" cssClass="col-sm-2 col-form-label">사용여부 </form:label>
											<div class="col-form-label">
												<div class="custom-control custom-radio custom-control-inline">
													<form:radiobutton path="orgUse" class="custom-control-input" value="true"/>
													<label for="orgUse1" class="custom-control-label"> 사용</label>
												</div>
												<div class="custom-control custom-radio custom-control-inline">
													<form:radiobutton path="orgUse" class="custom-control-input" value="false"/>
													<label for="orgUse2" class="custom-control-label"> 사용안함</label>
												</div>
											</div>
											<form:errors path="orgUse" element="div" cssClass="text-danger" />
										</div>
										<div class="form-group">
											<button type="submit" class="btn btn-primary waves-effect waves-light">저장</button>
											<a href="${ADMIN_PATH}/organization/organization/list.do" class="btn btn-secondary waves-effect m-l-5">목록</a>
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