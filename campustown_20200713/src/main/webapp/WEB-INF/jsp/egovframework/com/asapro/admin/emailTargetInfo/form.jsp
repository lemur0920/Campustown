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
									
					            	<c:if test="${formMode == 'INSERT'}"><c:set var="actionUrl" value="${ADMIN_PATH}/emailTarget/insert.do" /></c:if>
									<c:if test="${formMode == 'UPDATE'}"><c:set var="actionUrl" value="${ADMIN_PATH}/emailTarget/update.do" /></c:if>
						            <form:form modelAttribute="emailTargetInfoForm" cssClass="" action="${actionUrl}" method="post">
										<div class="form-group row">
											<form:label path="etId" cssClass="col-sm-2 col-form-label">아이디 <span class="text-danger">*</span></form:label>
											<div class="col-sm">
												<c:if test="${formMode == 'INSERT'}">
													<form:input path="etId" cssClass="form-control col-sm-4" cssErrorClass="form-control col-sm-4 is-invalid" maxlength="25" id="thresholdconfig" placeholder="- 영문, 숫자, 언더바(_)만 사용할 수 있습니다." />
												</c:if>
												<c:if test="${formMode == 'UPDATE'}">
													<form:input path="etId" cssClass="form-control col-sm-4" readonly="true" cssErrorClass="form-control col-sm-4 is-invalid" maxlength="25" id="thresholdconfig" placeholder="- 영문, 숫자, 언더바(_)만 사용할 수 있습니다." />
												</c:if>
												<form:errors path="etId" element="div" cssClass="text-danger col-form-label ml-2" />
											</div>
										</div>
										<div class="form-group row">
											<form:label path="etTitle" cssClass="col-sm-2 col-form-label">제목 <span class="text-danger">*</span></form:label>
											<div class="col-sm">
												<form:input path="etTitle" cssClass="form-control col-sm-8" cssErrorClass="form-control col-sm-8 is-invalid" placeholder="제목" />
												<form:errors path="etTitle" element="div" cssClass="text-danger col-form-label ml-2" />
											</div>
										</div>
										<div class="form-group row">
											<form:label path="etCate" cssClass="col-sm-2 col-form-label">분류 </form:label>
											<div class="col-sm">
												<form:select path="etCate" cssClass="form-control col-sm-2" cssErrorClass="form-control col-sm-2 is-invalid" >
													<form:option value="">선택</form:option>
													<form:options items="${etCateCodeList}" itemLabel="codeName" itemValue="codeId" />
												</form:select>
												<form:errors path="etCate" element="div" cssClass="text-danger col-form-label ml-2" />
											</div>
										</div>
										<div class="form-group row">
											<form:label path="etTarget" cssClass="col-sm-2 col-form-label">수신자 <span class="text-danger">*</span></form:label>
											<div class="col-sm">
												<form:textarea path="etTarget" cssClass="form-control col-sm-3" cssErrorClass="form-control col-sm-3 is-invalid" rows="5" placeholder="수신자" />
												<form:errors path="etTarget" element="div" cssClass="text-danger" />
												<span class="mt-2 bg-light font-weight-bold text-info">- 수신자 구분 ';'</span>
											</div>
										</div>
										<div class="form-group row">
											<form:label path="etContents" cssClass="col-sm-2 col-form-label">내용 <span class="text-danger">*</span></form:label>
											<div class="col-sm">
												<form:textarea path="etContents" cssClass="form-control col-sm-8" cssErrorClass="form-control col-sm-8 is-invalid" rows="5" placeholder="내용" />
												<form:errors path="etContents" element="div" cssClass="text-danger" />
											</div>
										</div>
										<div class="form-group row">
											<form:label path="etForm" cssClass="col-sm-2 col-form-label">발송 폼 </form:label>
											<div class="col-sm ">
												<form:select path="etForm" cssClass="form-control col-sm-3" cssErrorClass="form-control col-sm-3 is-invalid">
													<option value="" label="선택">
													<c:forEach items="${etFormList}" var="template" varStatus="vs">
														<form:option value="${template}" label="${template}"/>
													</c:forEach>
												</form:select>
												<form:errors path="etForm" element="div" cssClass="text-danger col-form-label ml-2" />
											</div>
										</div>
										
										<div class="form-group">
											<button type="submit" class="btn btn-primary waves-effect waves-light">저장</button>
											<a href="${ADMIN_PATH}/emailTarget/list.do" class="btn btn-secondary waves-effect m-l-5">목록</a>
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