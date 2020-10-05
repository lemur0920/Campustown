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
					            <div class="card-body">
					            
									<!-- alert maeeage -->
									<c:import url="/WEB-INF/jsp/egovframework/com/asapro/admin/include/message.jsp" charEncoding="UTF-8" />
					            	
					            	<!-- info -->
									<div class="p-3 mb-3 bg-light text-info font-weight-bold">
										<div><i class="mdi mdi-information"></i> 오픈 API 정보 등록</div>
									</div>
									
					            	<c:if test="${formMode eq 'INSERT'}"><c:set var="actionUrl" value="${ADMIN_PATH}/openapiinfo/insert.do" /></c:if>
									<c:if test="${formMode eq 'UPDATE'}"><c:set var="actionUrl" value="${ADMIN_PATH}/openapiinfo/update.do" /></c:if>
						            <form:form modelAttribute="openApiInfoForm" cssClass="" action="${actionUrl}" method="post">
										<form:hidden path="apiSeq" />
										
										<div class="form-group row">
											<form:label path="apiId" cssClass="col-sm-2 col-form-label">서비스명(영문) <span class="text-danger">*</span></form:label>
											<div class="col-sm">
												<form:input path="apiId" cssClass="form-control col-sm-4" cssErrorClass="form-control col-sm-4 is-invalid"  />
												<form:errors path="apiId" element="div" cssClass="text-danger col-form-label ml-2" />
											</div>
										</div>
										
										<div class="form-group row">
											<form:label path="apiTitle" cssClass="col-sm-2 col-form-label">서비스명(한글) <span class="text-danger">*</span></form:label>
											<div class="col-sm">
												<form:input path="apiTitle" cssClass="form-control col-sm-4" cssErrorClass="form-control col-sm-4 is-invalid"  />
												<form:errors path="apiTitle" element="div" cssClass="text-danger col-form-label ml-2" />
											</div>
										</div>
										
										<div class="form-group row">
											<form:label path="apiContent" cssClass="col-sm-2 col-form-label">서비스 설명 <span class="text-danger">*</span></form:label>
											<div class="col-sm">
												<form:textarea path="apiContent" cssClass="form-control col-sm-8" rows="3" cssErrorClass="form-control col-sm-8 is-invalid" />
												<form:errors path="apiContent" element="div" cssClass="text-danger" />
											</div>
										</div>
										
										<div class="form-group row">
											<form:label path="apiProvider" cssClass="col-sm-2 col-form-label">API 제공 사이트 <span class="text-danger">*</span></form:label>
											<div class="col-sm">
												<form:select path="apiProvider" cssClass="form-control col-sm-2" cssErrorClass="form-control col-sm-2 is-invalid">
													<option value="" label="선택">
													<form:options items="${providerCodeList }" itemLabel="codeName" itemValue="codeId"/>
												</form:select>
												<form:errors path="apiProvider" element="div" cssClass="text-danger col-form-label ml-2" />
											</div>
										</div>
										
										<div class="form-group row">
											<form:label path="apiDomain" cssClass="col-sm-2 col-form-label">API 도메인 <span class="text-danger">*</span></form:label>
											<div class="col-sm">
												<form:input path="apiDomain" id="address" cssClass="form-control col-sm-8" cssErrorClass="form-control col-sm-8 is-invalid" />
												<form:errors path="apiDomain" element="div" cssClass="text-danger" />
											</div>
										</div>
										
										<div class="form-group row">
											<form:label path="apiKey" cssClass="col-sm-2 col-form-label">API KEY <span class="text-danger">*</span></form:label>
											<div class="col-sm">
												<form:input path="apiKey" id="address" cssClass="form-control col-sm-8" cssErrorClass="form-control col-sm-8 is-invalid" />
												<form:errors path="apiKey" element="div" cssClass="text-danger" />
											</div>
										</div>
										
										<div class="form-group row">
											<form:label path="apiReturnType" cssClass="col-sm-2 col-form-label">API 리턴 유형 <span class="text-danger">*</span></form:label>
											<div class="col-sm">
												<form:select path="apiReturnType" cssClass="form-control col-sm-2" cssErrorClass="form-control col-sm-2 is-invalid">
													<option value="" label="선택">
													<form:options items="${returyTypeCodeList }" itemLabel="codeName" itemValue="codeId"/>
												</form:select>
												<form:errors path="apiReturnType" element="div" cssClass="text-danger col-form-label ml-2" />
											</div>
										</div>
										
										<div class="form-group row">
											<form:label path="apiNumOfRow" cssClass="col-sm-2 col-form-label">한번에 가져올 결과 수 <span class="text-danger">*</span></form:label>
											<div class="col-sm">
												<form:select path="apiNumOfRow" cssClass="form-control col-sm-1" cssErrorClass="form-control col-sm-1 is-invalid">
													<form:option value="50" label="50" />
													<form:option value="100" label="100" />
													<form:option value="500" label="500" />
													<form:option value="1000" label="1000" />
												</form:select>
												<form:errors path="apiNumOfRow" element="div" cssClass="text-danger col-form-label ml-2" />
											</div>
										</div>
										
										<div class="form-group row">
											<form:label path="apiTableName" cssClass="col-sm-2 col-form-label">DB테이블 명 </form:label>
											<div class="col-sm">
												<form:input path="apiTableName" cssClass="form-control col-sm-4" cssErrorClass="form-control col-sm-4 is-invalid"  />
												<form:errors path="apiTableName" element="div" cssClass="text-danger col-form-label ml-2" />
											</div>
										</div>
										
										<div class="form-group">
											<button type="submit" class="btn btn-primary waves-effect waves-light">저장</button>
											<a href="${ADMIN_PATH}/openapiinfo/list.do" class="btn btn-secondary waves-effect m-l-5">목록</a>
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