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
					            	<!-- info -->
									<div class="p-3 mb-2 bg-light text-info font-weight-bold">
										<div><i class="mdi mdi-information"></i> ex) 1C-1B-0D-4D-F8-2D</div>
									</div>
					            
									<!-- alert maeeage -->
									<c:import url="/WEB-INF/jsp/egovframework/com/asapro/admin/include/message.jsp" charEncoding="UTF-8" />
					            	
					            	<c:if test="${formMode == 'INSERT'}"><c:set var="actionUrl" value="${ADMIN_PATH}/allowmac/insert.do" /></c:if>
									<c:if test="${formMode == 'UPDATE'}"><c:set var="actionUrl" value="${ADMIN_PATH}/allowmac/update.do" /></c:if>
						            <form:form modelAttribute="allowMacForm" cssClass="" action="${actionUrl}" method="post">
						            	<form:hidden path="macId"/>
						            	
										<div class="form-group row">
											<form:label path="macType" cssClass="col-sm-2 col-form-label">허용/차단 <span class="text-danger">*</span></form:label>
											<form:select path="macType" cssClass="form-control col-sm-1" cssErrorClass="form-control col-sm-1 is-invalid" >
												<option value="">선택</option>
												<form:option label="허용"  value="allow"/>
												<form:option label="차단"  value="block"/>
											</form:select>
											<form:errors path="macType" element="div" cssClass="text-danger col-form-label ml-2" />
										</div>
										<div class="form-group row">
											<form:label path="macRuleName" cssClass="col-sm-2 col-form-label">규칙명 <span class="text-danger">*</span></form:label>
											<form:input path="macRuleName" cssClass="form-control col-sm-3" cssErrorClass="form-control col-sm-3 is-invalid" placeholder="규칙명" />
											<form:errors path="macRuleName" element="div" cssClass="text-danger col-form-label ml-2" />
										</div>
										<div class="form-group row">
											<form:label path="macAddress" cssClass="col-sm-2 col-form-label">Mac 주소 <span class="text-danger">*</span></form:label>
											<form:input path="macAddress" maxlength="17" cssClass="form-control col-sm-2" cssErrorClass="form-control col-sm-2 is-invalid" />
											<form:errors path="macAddress" element="div" cssClass="text-danger col-form-label ml-2" />
										</div> 
										
										<div class="form-group row">
											<form:label path="macUse" cssClass="col-sm-2 col-form-label">사용여부 <span class="text-danger">*</span></form:label>
											<div class="col-form-label">
												<div class="custom-control custom-radio custom-control-inline">
													<form:radiobutton path="macUse" class="custom-control-input" value="true"/>
													<label for="macUse1" class="custom-control-label"> 사용</label>
												</div>
												<div class="custom-control custom-radio custom-control-inline">
													<form:radiobutton path="macUse" class="custom-control-input" value="false"/>
													<label for="macUse2" class="custom-control-label"> 사용안함</label>
												</div>
											</div>
											<form:errors path="macUse" element="div" cssClass="text-danger col-form-label ml-2" />
										</div>
										<div class="form-group">
											<button type="submit" class="btn btn-primary waves-effect waves-light">저장</button>
											<a href="${ADMIN_PATH}/allowmac/list.do?macType=${allowMacForm.macType}" class="btn btn-secondary waves-effect m-l-5">목록</a>
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