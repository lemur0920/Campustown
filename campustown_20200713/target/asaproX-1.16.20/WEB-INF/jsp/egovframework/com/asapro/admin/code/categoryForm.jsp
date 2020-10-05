<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
									
					            	<c:if test="${formMode == 'INSERT'}"><c:set var="actionUrl" value="${ADMIN_PATH}/code/category/insert.do" /></c:if>
									<c:if test="${formMode == 'UPDATE'}"><c:set var="actionUrl" value="${ADMIN_PATH}/code/category/update.do" /></c:if>
						            <form:form modelAttribute="codeCategoryForm" cssClass="" action="${actionUrl}" method="post">
										<div class="form-group row">
											<form:label path="catId" cssClass="col-sm-2 col-form-label">코드분류아이디 <span class="text-danger">*</span></form:label>
											<c:if test="${formMode == 'INSERT'}">
												<form:input path="catId" cssClass="form-control col-sm-3" cssErrorClass="form-control col-sm-3 is-invalid" maxlength="25" id="thresholdconfig" placeholder="- 영문, 숫자, 언더바(_)만 사용할 수 있습니다." />
											</c:if>
											<c:if test="${formMode == 'UPDATE'}">
												<form:input path="catId" cssClass="form-control col-sm-3" readonly="true" cssErrorClass="form-control col-sm-3 is-invalid" maxlength="25" id="thresholdconfig" placeholder="- 영문, 숫자, 언더바(_)만 사용할 수 있습니다." />
											</c:if>
											<form:errors path="catId" element="div" cssClass="text-danger col-form-label ml-2" />
										</div>
										<div class="form-group row">
											<form:label path="catName" cssClass="col-sm-2 col-form-label">코드분류이름 <span class="text-danger">*</span></form:label>
											<form:input path="catName" cssClass="form-control col-sm-3" cssErrorClass="form-control col-sm-3 is-invalid" placeholder="코드분류이름" />
											<form:errors path="catName" element="div" cssClass="text-danger col-form-label ml-2" />
										</div>
										<div class="form-group row">
											<form:label path="catNameEn" cssClass="col-sm-2 col-form-label">코드분류영문이름 </form:label>
											<form:input path="catNameEn" cssClass="form-control col-sm-3" cssErrorClass="form-control col-sm-3 is-invalid" placeholder="코드분류영문이름" />
											<form:errors path="catNameEn" element="div" cssClass="text-danger" />
										</div>
										<div class="form-group row">
											<form:label path="catDescription" cssClass="col-sm-2 col-form-label">코드분류설명 </form:label>
											<form:textarea path="catDescription" cssClass="form-control col-sm-5" cssErrorClass="form-control col-sm-3 is-invalid" rows="5" placeholder="코드분류설명" />
											<form:errors path="catDescription" element="div" cssClass="text-danger" />
										</div>
										<div class="form-group">
											<button type="submit" class="btn btn-primary waves-effect waves-light">저장</button>
											<a href="${ADMIN_PATH}/code/category/list.do" class="btn btn-secondary waves-effect m-l-5">목록</a>
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