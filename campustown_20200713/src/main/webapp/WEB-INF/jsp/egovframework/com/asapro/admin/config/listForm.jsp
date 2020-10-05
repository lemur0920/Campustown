<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

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
									
									<div class="p-3 mb-2 bg-light text-info font-weight-bold">
										<div><i class="mdi mdi-information"></i> 옵션값은 최대 1000자까지 입력할 수 있습니다.</div>
									</div>
									
									<c:if test="${empty configForm.options}">
										<div>표시할 데이터가 없습니다.</div>
									</c:if>
									
									<c:if test="${not empty configForm.options}">
						            <form:form modelAttribute="configForm" cssClass="" action="${ADMIN_PATH}/config/${confId}/update.do" method="post" enctype="multipart/form-data">
										
										<c:forEach items="${configForm.options}" var="option" varStatus="vs">
											<form:hidden path="options[${vs.index }].confId" />
											<form:hidden path="options[${vs.index }].optKey" />
											<form:hidden path="options[${vs.index }].optName" />
											<form:hidden path="options[${vs.index }].optHelp" />
											<c:if test="${option.optHidden}">
												<form:hidden path="options[${vs.index}].optValue" />
											</c:if>
											
											<!-- 워터마크 이미지 교체가능하도록 처리추가 -->
											<c:if test="${option.optKey == 'watermark_image'}">
												<div class="form-group row">
													<form:label path="watermarkImage" cssClass="col-sm-2 col-form-label">워터마크 이미지 <span class="text-danger">*</span></form:label>
													<div class="col-sm">
														<div>
															<img class="img-thumbnail" style="max-width: 200px; max-height: 200px;" src="${option.optValue}?<%= System.currentTimeMillis() %>" onerror="this.src='${CONTEXT_PATH }/assets/images/noImage300x300.jpg'" />
														</div>
														<div>
															<input type="file" name="watermarkImage" class="col-form-label" />
															<form:errors path="watermarkImage" element="div" cssClass="text-danger col-form-label ml-2" />
															<ul>
																<li>
																	<span class="bg-light font-weight-bold text-info"> jpg, jpeg, gif, png, bmp 파일만 업로드할 수 있습니다.</span>
																</li>
															</ul>	
														</div>
		
													</div>
												</div>
											</c:if>
											
											<c:if test="${!option.optHidden}">
												<div class="form-group row">
													<form:label path="options[${vs.index}].optValue" cssClass="col-sm-2 col-form-label">${option.optName} </form:label>
													<div class="col-sm <c:if test="${option.optType eq 'text'}">form-inline</c:if>">
														<c:choose>
															<c:when test="${option.optType eq 'radio'}">
																<%--로그인실패 잠김유형 --%>
																<c:if test="${option.optKey eq 'login_fail_restriction_type' }">
																<div class="col-form-label">
																	<div class="custom-control custom-radio custom-control-inline">
																		<form:radiobutton path="options[${vs.index}].optValue" class="custom-control-input" value="temporary"/>
																		<label for="options${vs.index}.optValue1" class="custom-control-label"> 일시적제한</label>
																		<span class="ml-2">${option.optUnitText}</span>
																	</div>
																	<div class="custom-control custom-radio custom-control-inline">
																		<form:radiobutton path="options[${vs.index}].optValue" class="custom-control-input" value="fixed"/>
																		<label for="options${vs.index}.optValue2" class="custom-control-label"> 영구적제한</label>
																		<span class="ml-2">${option.optUnitText}</span>
																	</div>
																</div>
																</c:if>
																
																<c:if test="${option.optKey ne 'login_fail_restriction_type' }">
																<div class="col-form-label">
																	<div class="custom-control custom-radio custom-control-inline">
																		<form:radiobutton path="options[${vs.index}].optValue" class="custom-control-input" value="true"/>
																		<label for="options${vs.index}.optValue1" class="custom-control-label"> True</label>
																	</div>
																	<div class="custom-control custom-radio custom-control-inline">
																		<form:radiobutton path="options[${vs.index}].optValue" class="custom-control-input" value="false"/>
																		<label for="options${vs.index}.optValue2" class="custom-control-label"> False</label>
																	</div>
																</div>
																</c:if>
															
															</c:when>
															
															<c:when test="${option.optType eq 'checkbox'}">
															</c:when>
															
															<c:when test="${option.optType eq 'textarea'}">
																<form:textarea path="options[${vs.index}].optValue" cssClass="form-control col-sm-8" rows="3" />
															</c:when>
															
															<c:when test="${option.optType eq 'select'}">
																<%--로그인실패 잠김유형 --%>
																<%--
																<c:if test="${option.optKey eq 'login_fail_restriction_type' }">
																	<form:select path="options[${vs.index}].optValue" cssClass="form-control col-sm-2" >
																		<form:option value="temporary" label="일시적제한"></form:option>
																		<form:option value="fixed" label="영구적제한"></form:option>
																	</form:select>
																	<span class="ml-2">${option.optUnitText}</span>
																</c:if>
																 --%>
																 
																 
															</c:when>
															
															<c:otherwise>
															<%--메뉴의 테그옵션 여부 --%>
																<c:if test="${option.optKey eq 'menu_hash_tag_code_category' }">
																	<form:select path="options[${vs.index}].optValue" cssClass="form-control col-sm-2" >
																		<form:option value="">- 미사용</form:option>
																		<form:options items="${codeCategoryList}" itemLabel="catName" itemValue="catId" />
																	</form:select>
																	<span class="ml-2">${option.optUnitText}</span>
																</c:if>
																<c:if test="${option.optKey ne 'menu_hash_tag_code_category' }">
																	<c:choose>
																		<c:when test="${fn:length(option.optValue) >= 30}"><c:set var="cssClass" value="col-sm-8"/></c:when>
																		<c:when test="${fn:length(option.optValue) >= 20}"><c:set var="cssClass" value="col-sm-4"/></c:when>
																		<c:when test="${fn:length(option.optValue) <= 10}"><c:set var="cssClass" value="col-sm-1"/></c:when>
																		<c:otherwise><c:set var="cssClass" value=""/></c:otherwise>
																	</c:choose>
																	<form:input path="options[${vs.index}].optValue" cssClass="form-control ${cssClass}"/> <span class="ml-2">${option.optUnitText}</span>
																</c:if>
															</c:otherwise>	
															
														</c:choose>
														<c:if test="${not empty option.optHelp}">
															<c:if test="${option.optType eq 'textarea'}"></c:if>
															<span class="ml-2 bg-light font-weight-bold text-info"> ${option.optHelp}</span>
														</c:if>
													</div>
												</div>
											</c:if>
										
										</c:forEach>
										
										<div class="form-group">
											<button type="submit" class="btn btn-primary waves-effect waves-light">저장</button>
										</div>
									</form:form>
									
									</c:if>
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