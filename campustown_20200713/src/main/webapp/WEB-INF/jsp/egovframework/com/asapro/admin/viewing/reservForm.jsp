<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="asapro" uri="http://www.asadal.com" %>

<c:import url="/WEB-INF/jsp/egovframework/com/asapro/admin/layout/header.jsp" charEncoding="UTF-8" />
<script src="${CONTEXT_PATH }/assets/plugins/jquery-timepicker/jquery.timepicker.min.js"></script>
<link rel="stylesheet" href="${CONTEXT_PATH }/assets/plugins/jquery-timepicker/jquery.timepicker.min.css">
<script>
jQuery(function($){

	//달력
	$('.datepicker-ui').datepicker({
		minDate: 1	//출력할 최소 날짜 (1주일전)
	});
	
	//시간
	$(document).on('focus','.startTime, .endTime', function() {
		$(this).timepicker({
			timeFormat: 'HH:mm',
			interval: 30,
			minTime: '09:30',
			maxTime: '17:30',
			//defaultTime: '11',
			startTime: '09:30',
			dynamic: false,
			dropdown: true,
			scrollbar: true
		});
	});

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
										<div><i class="mdi mdi-information"></i> 관람 신청 </div>
									</div>
									
					            	<c:if test="${formMode eq 'INSERT'}"><c:set var="actionUrl" value="${ADMIN_PATH}/viewing/reservation/insert.do" /></c:if>
									<c:if test="${formMode eq 'UPDATE'}"><c:set var="actionUrl" value="${ADMIN_PATH}/viewing/reservation/update.do" /></c:if>
						            <form:form modelAttribute="viewingReservationForm" cssClass="" action="${actionUrl}" method="post" enctype="multipart/form-data">
										<form:hidden path="reservId" />
										
										<div class="form-group row">
											<form:label path="reservName" cssClass="col-sm-2 col-form-label">신청자 명 <span class="text-danger">*</span></form:label>
											<div class="col-sm">
												<form:input path="reservName" cssClass="form-control col-sm-1" cssErrorClass="form-control col-sm-1 is-invalid"  />
												<form:errors path="reservName" element="div" cssClass="text-danger col-form-label ml-2" />
											</div>
										</div>
										
										<div class="form-group row">
											<form:label path="reservGroup" cssClass="col-sm-2 col-form-label">신청단체 <span class="text-danger">*</span></form:label>
											<div class="col-sm">
												<form:input path="reservGroup" cssClass="form-control col-sm-4" cssErrorClass="form-control col-sm-4 is-invalid"  />
												<form:errors path="reservGroup" element="div" cssClass="text-danger col-form-label ml-2" />
											</div>
										</div>
										
										<div class="form-group row">
											<form:label path="reservAddress" cssClass="col-sm-2 col-form-label">주소 <span class="text-danger">*</span></form:label>
											<div class="col-sm">
												<form:input path="reservAddress" id="address" cssClass="form-control col-sm-8" cssErrorClass="form-control col-sm-8 is-invalid" placeholder="주소" />
												<form:errors path="reservAddress" element="div" cssClass="text-danger" />
											</div>
										</div>
										
										<div class="form-group row">
											<form:label path="reservHp1" cssClass="col-sm-2 col-form-label">신청자 휴대폰번호 <span class="text-danger">*</span></form:label>
											<div class="col-sm form-inline">
												<form:select path="reservHp1" cssClass="form-control col-sm-1" cssErrorClass="form-control col-sm-1 is-invalid">
													<option value="" label="선택">
													<form:options items="${mobile1CodeList }" itemLabel="codeName" itemValue="codeId"/>
												</form:select>
												 - <form:input path="reservHp2" cssClass="form-control col-sm-1" cssErrorClass="form-control col-sm-1 is-invalid" maxlength="4" />
												 - <form:input path="reservHp3" cssClass="form-control col-sm-1" cssErrorClass="form-control col-sm-1 is-invalid" maxlength="4" />
												<form:errors path="reservHp1" element="div" cssClass="text-danger col-form-label ml-2" />
												<form:errors path="reservHp2" element="div" cssClass="text-danger col-form-label ml-2" />
												<form:errors path="reservHp3" element="div" cssClass="text-danger col-form-label ml-2" />
											</div>
										</div>
										
										<div class="form-group row">
											<form:label path="reservEmail" cssClass="col-sm-2 col-form-label">신청자 이메일주소 </form:label>
											<div class="col-sm">
												<form:input path="reservEmail" cssClass="form-control col-sm-6" cssErrorClass="form-control col-sm-6 is-invalid"  />
												<form:errors path="reservEmail" element="div" cssClass="text-danger col-form-label ml-2" />
											</div>
										</div>
										
										<div class="form-group row">
											<form:label path="reservPeople" cssClass="col-sm-2 col-form-label">인원 <span class="text-danger">*</span></form:label>
											<div class="col-sm form-inline">
												<form:input path="reservPeople" cssClass="form-control col-sm-1" cssErrorClass="form-control col-sm-1 is-invalid"  />
												<span class="col-form-label mx-2"> 명</span> 
												<form:errors path="reservPeople" element="div" cssClass="text-danger col-form-label ml-2" />
											</div>
										</div>
										
										<div class="form-group row">
											<form:label path="reservDate" cssClass="col-sm-2 col-form-label">관람날짜 <span class="text-danger">*</span></form:label>
											<div class="col-sm form-inline">
												<form:input path="reservDate" cssClass="form-control col-sm-1 datepicker-ui" cssErrorClass="form-control col-sm-1 is-invalid datepicker-ui" autocomplete="off" readonly="true"/>
												<span class="form-text text-muted text-info mx-2"> - 방문 예정일 1주일 전까지 예약</span> 
												<form:errors path="reservDate" element="div" cssClass="text-danger col-form-label ml-2" />
											</div>
										</div>
										<div class="form-group row">
											<form:label path="reservStime" cssClass="col-sm-2 col-form-label">관람희망시간 <span class="text-danger">*</span></form:label>
											<div class="col-sm form-inline">
												<form:input path="reservStime" cssClass="startTime form-control col-sm-1" cssErrorClass="startTime form-control col-sm-1 is-invalid" maxlength="5" autocomplete="off" />
												<span class="col-form-label mx-2">~</span> 
												<form:input path="reservEtime" cssClass="endTime form-control col-sm-1" cssErrorClass="endTime form-control col-sm-1 is-invalid" maxlength="5" autocomplete="off" />
												<form:errors path="reservStime" element="div" cssClass="text-danger col-form-label ml-2" />
												<form:errors path="reservEtime" element="div" cssClass="text-danger col-form-label ml-2" />
											</div>
										</div>
										
										<div class="form-group row">
											<form:label path="reservMemo" cssClass="col-sm-2 col-form-label">기타사항 <span class="text-danger">*</span></form:label>
											<div class="col-sm">
												<form:textarea path="reservMemo" cssClass="form-control col-sm-8" rows="3"  />
												<form:errors path="reservMemo" element="div" cssClass="text-danger" />
											</div>
										</div>
										
										
										<%--======================================================================================================= --%>
										<%--=======================================   신청내용 확인시, 수정시에만 표출  ================================================ --%>
										<%--======================================================================================================= --%>
										<c:if test="${formMode eq 'UPDATE' }">
										<hr>
										<div class="form-group row">
											<form:label path="reservStatus" cssClass="col-sm-2 col-form-label">신청상태 <span class="text-danger">*</span></form:label>
											<div class="col-sm">
												<form:select path="reservStatus" cssClass="form-control col-sm-1" cssErrorClass="form-control col-sm-1 is-invalid" >
													<form:options items="${statusCodeList}" itemLabel="codeName" itemValue="codeId" />
												</form:select>
												<form:errors path="reservStatus" element="div" cssClass="text-danger col-form-label ml-2" />
											</div>
										</div>

										</c:if>

										<div class="form-group">
											<button type="submit" class="btn btn-primary waves-effect waves-light">저장</button>
											<a href="${ADMIN_PATH}/viewing/reservation/list.do" class="btn btn-secondary waves-effect m-l-5">목록</a>
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