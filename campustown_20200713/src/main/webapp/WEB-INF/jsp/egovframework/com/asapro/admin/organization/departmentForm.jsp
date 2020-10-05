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
					            	
					            	<c:if test="${formMode == 'INSERT'}"><c:set var="actionUrl" value="${ADMIN_PATH}/organization/department/insert.do" /></c:if>
									<c:if test="${formMode == 'UPDATE'}"><c:set var="actionUrl" value="${ADMIN_PATH}/organization/department/update.do" /></c:if>
						            <form:form modelAttribute="departmentForm" cssClass="" action="${actionUrl}" method="post">
										<c:if test="${formMode == 'UPDATE'}">
										<div class="form-group row">
											<form:label path="depId" cssClass="col-sm-2 col-form-label">부서 아이디 <span class="text-danger">*</span></form:label>
												<form:input path="depId" cssClass="form-control col-sm-3" readonly="true" cssErrorClass="form-control col-sm-3 is-invalid" maxlength="25" id="thresholdconfig" placeholder="- 영문, 언더바(_)만 사용할 수 있습니다." />
											<form:errors path="depId" element="div" cssClass="text-danger col-form-label ml-2" />
										</div>
										</c:if>
						            	<div class="form-group row">
											<form:label path="organization.orgId" cssClass="col-sm-2 col-form-label">기관분류 <span class="text-danger">*</span></form:label>
											<form:select path="organization.orgId" cssClass="form-control col-sm-2" cssErrorClass="form-control col-sm-2 is-invalid" >
												<option value="">선택</option>
												<%--<form:options items="${organizationList}" itemLabel="orgName" itemValue="orgId" /> --%>
												<c:forEach items="${organizationList }" var="item" varStatus="vs">
													<form:option value="${item.orgId }" label="" >${item.orgName }<c:if test="${!item.orgUse }"> (미사용)</c:if></form:option>
												</c:forEach>
											</form:select>
											<form:errors path="organization.orgId" element="div" cssClass="text-danger col-form-label ml-2" />
											<%--
											<c:if test="${formMode == 'INSERT'}">
											</c:if>
											<c:if test="${formMode == 'UPDATE'}">
												<form:hidden path="organization.orgId" cssClass="form-control col-sm-3" readonly="true" cssErrorClass="form-control col-sm-3 is-invalid" maxlength="25" />
												<form:input path="organization.orgName" cssClass="form-control col-sm-3" readonly="true" cssErrorClass="form-control col-sm-3 is-invalid" />
											</c:if>
											 --%>
										</div>
										<div class="form-group row">
											<form:label path="depName" cssClass="col-sm-2 col-form-label">부서명 <span class="text-danger">*</span></form:label>
											<form:input path="depName" cssClass="form-control col-sm-3" cssErrorClass="form-control col-sm-3 is-invalid" />
											<form:errors path="depName" element="div" cssClass="text-danger col-form-label ml-2" />
										</div>
										<div class="form-group row">
											<form:label path="depNameEn" cssClass="col-sm-2 col-form-label">부서 영문명 </form:label>
											<form:input path="depNameEn" cssClass="form-control col-sm-3" />
											<form:errors path="depNameEn" element="div" cssClass="text-danger" />
										</div>
										<div class="form-group row">
											<form:label path="depTel" cssClass="col-sm-2 col-form-label">부서 전화번호 </form:label>
											<form:input path="depTel" cssClass="form-control col-sm-3" />
											<form:errors path="depTel" element="div" cssClass="text-danger" />
										</div>
										<div class="form-group row">
											<form:label path="depDescription" cssClass="col-sm-2 col-form-label">부서 소개 </form:label>
											<form:textarea path="depDescription" cssClass="form-control col-sm-5" rows="5" placeholder="부서소개" />
											<form:errors path="depDescription" element="div" cssClass="text-danger" />
										</div>
										<div class="form-group row">
											<form:label path="depUse" cssClass="col-sm-2 col-form-label">사용여부 </form:label>
											<div class="col-form-label">
												<div class="custom-control custom-radio custom-control-inline">
													<form:radiobutton path="depUse" class="custom-control-input" value="true"/>
													<label for="depUse1" class="custom-control-label"> 사용</label>
												</div>
												<div class="custom-control custom-radio custom-control-inline">
													<form:radiobutton path="depUse" class="custom-control-input" value="false"/>
													<label for="depUse2" class="custom-control-label"> 사용안함</label>
												</div>
											</div>
											<form:errors path="depUse" element="div" cssClass="text-danger" />
										</div>
										<div class="form-group">
											<button type="submit" class="btn btn-primary waves-effect waves-light">저장</button>
											<a href="${ADMIN_PATH}/organization/department/list.do?orgId=${departmentForm.organization.orgId}" class="btn btn-secondary waves-effect m-l-5">목록</a>
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