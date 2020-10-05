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
					            	
					            	<c:if test="${formMode == 'INSERT'}"><c:set var="actionUrl" value="${ADMIN_PATH}/role/insert.do" /></c:if>
									<c:if test="${formMode == 'UPDATE'}"><c:set var="actionUrl" value="${ADMIN_PATH}/role/update.do" /></c:if>
						            <form:form modelAttribute="roleForm" cssClass="" action="${actionUrl}" method="post">
										
										<div class="form-group row">
											<form:label path="roleCode" cssClass="col-sm-2 col-form-label">역할코드 <span class="text-danger">*</span></form:label>
											<c:if test="${formMode == 'INSERT'}">
												<form:input path="roleCode" cssClass="form-control col-sm-3" cssErrorClass="form-control col-sm-3 is-invalid" maxlength="25" id="thresholdconfig" placeholder="- 영문, 숫자, 언더바(_)만 사용할 수 있습니다." />
											</c:if>
											<c:if test="${formMode == 'UPDATE'}">
												<form:input path="roleCode" cssClass="form-control col-sm-3" readonly="true" cssErrorClass="form-control col-sm-3 is-invalid" maxlength="25" id="thresholdconfig" placeholder="- 영문, 숫자, 언더바(_)만 사용할 수 있습니다." />
											</c:if>
											<form:errors path="roleCode" element="div" cssClass="text-danger col-form-label ml-2" />
										</div>
										<div class="form-group row">
											<form:label path="roleName" cssClass="col-sm-2 col-form-label">역할이름 <span class="text-danger">*</span></form:label>
											<form:input path="roleName" cssClass="form-control col-sm-4" cssErrorClass="form-control col-sm-4 is-invalid" maxlength="50" id="thresholdconfig" />
											<form:errors path="roleName" element="div" cssClass="text-danger col-form-label ml-2" />
										</div>
										<div class="form-group row">
											<form:label path="roleDescription" cssClass="col-sm-2 col-form-label">역할설명 </form:label>
											<form:textarea path="roleDescription" cssClass="form-control col-sm-5" maxlength="225" rows="5" id="textarea" placeholder="역할설명" />
											<form:errors path="roleDescription" element="div" cssClass="text-danger" />
										</div>
										
										<%-- 총관리자가 아닌경우만 --%>
										<c:if test="${!roleForm.roleAdmin}">
										<div class="form-group row">
											<form:label path="roleJoin" cssClass="col-sm-2 col-form-label">가입시 역할 </form:label>
											<div class="col-form-label">
												<div class="custom-control custom-checkbox">
													<form:checkbox path="roleJoin" class="custom-control-input" value="true"/>
													<label for="roleJoin1" class="custom-control-label"> 지정</label>
												</div>
											</div>
											<form:errors path="roleJoin" element="div" cssClass="text-danger" />
										</div>
										<div class="form-group row">
											<form:label path="roleDefault" cssClass="col-sm-2 col-form-label">시스템기본역할 여부 </form:label>
											<div class="col-form-label">
												<div class="custom-control custom-checkbox">
													<form:checkbox path="roleDefault" class="custom-control-input" value="true"/>
													<label for="roleDefault1" class="custom-control-label"> 시스템기본</label>
												</div>
												<span class="form-text text-muted text-info">- 시스템기본역할 지정시 목록에서 삭제할 수 없습니다.</span>
											</div>
											<form:errors path="roleDefault" element="div" cssClass="text-danger" />
										</div>
										
										<div class="form-group row">
											<form:label path="capabilityList" cssClass="col-sm-2 col-form-label">권한</form:label>
											<div class="table-responsive">
												<!-- info -->
												<div class="p-3 mb-2 bg-light text-info font-weight-bold">
													<div><i class="mdi mdi-information"></i> 우선순위가 높은(숫자가 큰) 권한이 먼저 체크됩니다.(CAP_ADMIN_ACCESS제외.)</div>
												</div>
												
												<table class="table table-striped table-hover">
													<thead>
														<tr>
															<th class="text-center">부여</th>
															<th>권한아이디</th>
															<th>권한이름</th>
															<th class="text-center">Method</th>
															<th class="text-center">우선순위</th>
														</tr>
													</thead>
													<tbody>
														<c:forEach items="${allCapabilityList}" var="item" varStatus="vs">
														<tr>
															<td class="text-center">
																<c:set var="match" value="false" />
																<c:forEach items="${roleForm.capabilityList}" var="capItem" varStatus="capVs">
																	<c:if test="${capItem.capId == item.capId}">
																		<c:set var="match" value="true" />
																	</c:if>
																</c:forEach>
																<div class="custom-control custom-checkbox">
																	<input type="checkbox" name="capabilityList[${vs.index}].capId" value="${item.capId}" class="custom-control-input" id="capIds${vs.count }" <c:if test="${match}"> checked="checked"</c:if>/>
																	<label class="custom-control-label" for="capIds${vs.count }"></label>
																</div>
															</td>
															<td><label for="cap${vs.count}">${item.capId}</label></td>
															<td><label for="cap${vs.count}">${item.capName}</label></td>
															<td class="text-center"><label for="cap${vs.count}">${item.capHttpMethod}</label></td>
															<td class="text-center"><label for="cap${vs.count}">${item.capPriority}</label></td>
														</tr>
														</c:forEach>
													</tbody>
												</table>
											</div>
										</div>
										</c:if>
										
										<div class="form-group">
											<button type="submit" class="btn btn-primary waves-effect waves-light">저장</button>
											<a href="${ADMIN_PATH}/role/list.do?" class="btn btn-secondary waves-effect m-l-5">목록</a>
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