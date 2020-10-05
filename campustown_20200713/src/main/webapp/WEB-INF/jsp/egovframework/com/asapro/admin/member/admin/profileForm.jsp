<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

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
					            	
									<c:if test="${formMode == 'UPDATE'}"><c:set var="actionUrl" value="${ADMIN_PATH}/member/admin/profile/update.do" /></c:if>
						            <form:form modelAttribute="adminMemberForm" cssClass="" action="${actionUrl}" method="post">
										<div class="form-group row">
											<form:label path="adminId" cssClass="col-sm-2 col-form-label">관리자 아이디 <span class="text-danger">*</span></form:label>
											<div class="col-sm">
												<c:if test="${formMode == 'UPDATE'}">
													<form:input path="adminId" readonly="true" cssClass="form-control col-sm-3" cssErrorClass="form-control col-sm-3 is-invalid" placeholder="영문, 숫자만 5~20자" maxlength="20"/>
												</c:if>
												<form:errors path="adminId" element="div" cssClass="text-danger col-form-label ml-2" />
												<div id="adminIdCheckResult" class="col-form-label"></div>
											</div>
										</div>
										
										<div class="form-group row">
											<form:label path="adminName" cssClass="col-sm-2 col-form-label">관리자 이름 <span class="text-danger">*</span></form:label>
											<div class="col-sm">
												<form:input path="adminName" cssClass="form-control col-sm-2" cssErrorClass="form-control col-sm-2 is-invalid" />
												<form:errors path="adminName" element="div" cssClass="text-danger col-form-label ml-2" />
											</div>
										</div>
										<div class="form-group row">
											<form:label path="adminSex" cssClass="col-sm-2 col-form-label">성별 </form:label>
											<div class="col-sm">
												<div class="col-form-label">
													<div class="custom-control custom-radio custom-control-inline">
														<form:radiobutton path="adminSex" class="custom-control-input" value="M"/>
														<label for="adminSex1" class="custom-control-label"> 남</label>
													</div>
													<div class="custom-control custom-radio custom-control-inline">
														<form:radiobutton path="adminSex" class="custom-control-input" value="F"/>
														<label for="adminSex2" class="custom-control-label"> 여</label>
													</div>
												</div>
												<form:errors path="adminSex" element="div" cssClass="text-danger" />
											</div>
										</div>
										<div class="form-group row">
											<form:label path="adminEmail" cssClass="col-sm-2 col-form-label">관리자 이메일 <span class="text-danger">*</span></form:label>
											<div class="col-sm">
												<form:input path="adminEmail" cssClass="form-control col-sm-3" cssErrorClass="form-control col-sm-3 is-invalid" placeholder="ex)admin@cms.com" />
												<form:errors path="adminEmail" element="div" cssClass="text-danger col-form-label ml-2" />
											</div>
										</div>
										<div class="form-group row">
											<form:label path="adminTel1" cssClass="col-sm-2 col-form-label">관리자 전화번호 </form:label>
											<div class="col-sm form-inline">
												<form:select path="adminTel1" cssClass="form-control col-sm-1">
													<option value="" label="선택">
													<form:options items="${areaCodeList }" itemLabel="codeName" itemValue="codeId"/>
												</form:select>
												 - <form:input path="adminTel2" cssClass="form-control col-sm-1" maxlength="4" />
												 - <form:input path="adminTel3" cssClass="form-control col-sm-1" maxlength="4" />
												<form:errors path="adminTel1" element="div" cssClass="text-danger" />
											</div>
										</div>
										<div class="form-group row">
											<form:label path="adminMobile1" cssClass="col-sm-2 col-form-label">관리자 휴대폰번호 <span class="text-danger">*</span></form:label>
											<div class="col-sm form-inline">
												<form:select path="adminMobile1" cssClass="form-control col-sm-1" cssErrorClass="form-control col-sm-1 is-invalid">
													<option value="" label="선택">
													<form:options items="${mobile1CodeList }" itemLabel="codeName" itemValue="codeId"/>
												</form:select>
												 - <form:input path="adminMobile2" cssClass="form-control col-sm-1" maxlength="4" />
												 - <form:input path="adminMobile3" cssClass="form-control col-sm-1" maxlength="4" />
												<form:errors path="adminMobile1" element="div" cssClass="text-danger col-form-label ml-2" />
											</div>
										</div>
										<div class="form-group row">
											<form:label path="adminFax" cssClass="col-sm-2 col-form-label">관리자 팩스번호 </form:label>
											<div class="col-sm">
												<form:input path="adminFax" cssClass="form-control col-sm-2" />
												<form:errors path="adminFax" element="div" cssClass="text-danger" />
											</div>
										</div>
										<div class="form-group row">
											<form:label path="adminZipcode" cssClass="col-sm-2 col-form-label">우편번호 </form:label>
											<div class="col-sm form-inline">
												<form:input path="adminZipcode" id="zipcode" cssClass="form-control col-sm-1 mr-sm-2" placeholder="우편번호" />
												<button id="zipSearchBtn" class="btn btn-primary waves-effect waves-light" >우편번호 찾기</button>
												<form:errors path="adminZipcode" element="div" cssClass="text-danger" />
											</div>
										</div>
										<div class="form-group row">
											<form:label path="adminAddress" cssClass="col-sm-2 col-form-label">주소 </form:label>
											<div class="col-sm">
												<form:input path="adminAddress" id="address" cssClass="form-control col-sm-4" placeholder="주소" />
												<form:errors path="adminAddress" element="div" cssClass="text-danger" />
											</div>
										</div>
										<div class="form-group row">
											<form:label path="adminAddressDetail" cssClass="col-sm-2 col-form-label">상세주소 </form:label>
											<div class="col-sm">
												<form:input path="adminAddressDetail" id="addressDetail" cssClass="form-control col-sm-3" placeholder="상세주소" />
												<form:errors path="adminAddressDetail" element="div" cssClass="text-danger" />
											</div>
										</div>
										<!-- Daum 우편번호 서비스 스크립트  -->
										<c:import url="/WEB-INF/jsp/egovframework/com/asapro/admin/include/zipSearch.jsp" charEncoding="UTF-8" />
										
										<div class="form-group">
											<button type="submit" class="btn btn-primary waves-effect waves-light">저장</button>
											<c:if test="${formMode == 'UPDATE'}">
											<a href="${ADMIN_PATH}/member/admin/profile/password/update.do" class="btn btn-warning waves-effect waves-light">비밀번호 변경</a>
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