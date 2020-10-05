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
					            	
					            	<c:if test="${formMode == 'INSERT'}"><c:set var="actionUrl" value="${ADMIN_PATH}/capability/insert.do" /></c:if>
									<c:if test="${formMode == 'UPDATE'}"><c:set var="actionUrl" value="${ADMIN_PATH}/capability/update.do" /></c:if>
						            <form:form modelAttribute="capabilityForm" cssClass="" action="${actionUrl}" method="post">
										
										<div class="form-group row">
											<form:label path="capId" cssClass="col-sm-2 col-form-label">권한아이디 <span class="text-danger">*</span></form:label>
											<c:if test="${formMode == 'INSERT'}">
												<form:input path="capId" cssClass="form-control col-sm-3" cssErrorClass="form-control col-sm-3 is-invalid" maxlength="25" id="thresholdconfig" placeholder="- 영문, 숫자, 언더바(_)만 사용할 수 있습니다." />
											</c:if>
											<c:if test="${formMode == 'UPDATE'}">
												<form:input path="capId" cssClass="form-control col-sm-3" readonly="true" cssErrorClass="form-control col-sm-3 is-invalid" maxlength="25" id="thresholdconfig" placeholder="- 영문, 숫자, 언더바(_)만 사용할 수 있습니다." />
											</c:if>
											<form:errors path="capId" element="div" cssClass="text-danger col-form-label ml-2" />
										</div>
										<div class="form-group row">
											<form:label path="capName" cssClass="col-sm-2 col-form-label">권한이름 <span class="text-danger">*</span></form:label>
											<form:input path="capName" cssClass="form-control col-sm-4" cssErrorClass="form-control col-sm-4 is-invalid" maxlength="50" id="thresholdconfig" />
											<form:errors path="capName" element="div" cssClass="text-danger col-form-label ml-2" />
										</div>
										<div class="form-group row">
											<form:label path="capValue" cssClass="col-sm-2 col-form-label">권한값(uri) <span class="text-danger">*</span></form:label>
											<form:input path="capValue" cssClass="form-control col-sm-4" cssErrorClass="form-control col-sm-4 is-invalid" placeholder="정규식 형태로 입력 ex) /[a-z0-9]+/admin/.*" />
											<form:errors path="capValue" element="div" cssClass="text-danger col-form-label ml-2" />
										</div>
										<div class="form-group row">
											<form:label path="capDescription" cssClass="col-sm-2 col-form-label">권한설명 </form:label>
											<form:textarea path="capDescription" cssClass="form-control col-sm-5" maxlength="225" rows="5" id="textarea" placeholder="권한설명" />
											<form:errors path="capDescription" element="div" cssClass="text-danger" />
										</div>
										<div class="form-group row">
											<form:label path="capParam1Key" cssClass="col-sm-2 col-form-label">파라미터 #1 </form:label>
											<span class="col-form-label mr-2"> Key : </span> <form:input path="capParam1Key" cssClass="form-control col-sm-2" cssErrorClass="form-control col-sm-2 is-invalid"  />
											<span class="col-form-label mx-2"> Value : </span> <form:input path="capParam1Value" cssClass="form-control col-sm-2" cssErrorClass="form-control col-sm-2 is-invalid"  />
											<form:errors path="capParam1Key" element="div" cssClass="text-danger col-form-label" />
										</div>
										<div class="form-group row">
											<form:label path="capParam2Key" cssClass="col-sm-2 col-form-label">파라미터 #2 </form:label>
											<span class="col-form-label mr-2"> Key : </span> <form:input path="capParam2Key" cssClass="form-control col-sm-2" cssErrorClass="form-control col-sm-2 is-invalid"  />
											<span class="col-form-label mx-2"> Value : </span> <form:input path="capParam2Value" cssClass="form-control col-sm-2" cssErrorClass="form-control col-sm-2 is-invalid"  />
											<form:errors path="capParam2Key" element="div" cssClass="text-danger col-form-label" />
										</div>
										<div class="form-group row">
											<form:label path="capHttpMethod" cssClass="col-sm-2 col-form-label">권한 Http Method </form:label>
											<form:select path="capHttpMethod" cssClass="form-control col-sm-1" cssErrorClass="form-control col-sm-1 is-invalid" >
												<form:option value="ALL" label="ALL(default)" />
												<form:option value="GET" />
												<form:option value="POST" />
											</form:select>
											<form:errors path="capHttpMethod" element="div" cssClass="text-danger col-form-label" />
										</div>
										<div class="form-group row">
											<form:label path="capPriority" cssClass="col-sm-2 col-form-label">권한 우선순위 </form:label>
											<form:input path="capPriority" cssClass="form-control col-sm-1" cssErrorClass="form-control col-sm-1 is-invalid"  />
											<form:errors path="capPriority" element="div" cssClass="text-danger col-form-label" />
										</div>
										<div class="form-group row">
											<label class="col-sm-2 col-form-label"></label>
											<div class="p-3 bg-light font-weight-bold text-info">
												<div><i class="mdi mdi-information"></i> 우선순위가 높은(숫자가 큰) 권한이 먼저 체크됩니다.(CAP_ADMIN_ACCESS 제외)</div>
												<div><i class="mdi mdi-information"></i> 모듈전체기능 추천기본값: 10, 세부기능 추천기본값 : 20</div>
												<div><i class="mdi mdi-information"></i> 범위가 좁은 기능일수록(세부기능일수록) 우선순위 값이 커야 합니다.</div>
											</div>
										</div>
										<div class="form-group row">
											<form:label path="capDefault" cssClass="col-sm-2 col-form-label">시스템기본권한 여부 </form:label>
											<div class="col-form-label">
												<div class="custom-control custom-checkbox">
													<form:checkbox path="capDefault" class="custom-control-input" value="true"/>
													<label for="capDefault1" class="custom-control-label"> 기본</label>
												</div>
											</div>
											<form:errors path="capDefault" element="div" cssClass="text-danger" />
										</div>
										<div class="form-group">
											<button type="submit" class="btn btn-primary waves-effect waves-light">저장</button>
											<a href="${ADMIN_PATH}/capability/list.do?" class="btn btn-secondary waves-effect m-l-5">목록</a>
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