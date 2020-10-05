<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="asapro" uri="http://www.asadal.com" %>

<!-- header -->
<c:import url="${design.header}" charEncoding="UTF-8" />
<script src="${CONTEXT_PATH }/design/common/js/jquery-timepicker/jquery.timepicker.min.js"></script>
<link rel="stylesheet" href="${CONTEXT_PATH }/design/common/js/jquery-timepicker/jquery.timepicker.min.css">

<script>
jQuery(function($){
	
	//달력
	$('.datepicker-ui').datepicker({
		buttonImage: '${CONTEXT_PATH }/design/common/images/asset/dateSbt2.jpg'
		, minDate: 14	//출력할 최소 날짜 (2주전)
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
	
	//예약신청
	$('.insertBtn').on('click', function(){
		$('#volunteerReservationForm').submit();
	});
	
});
</script>

<!-- sidebar -->
<c:import url="${design.themeDir}/layout/sidebar.jsp" charEncoding="UTF-8" />

<%-- ============================= 메뉴별 컨텐츠 ============================ --%>

<div class="RightCWarp">
	<div class="s322porsi">
		<ul>
			<li class="s322bostfor">자원봉사는 일요일과 법정공휴일에는 운영하지 않습니다.
			</li>
			<li class="s322bostfor"> 자원봉사는 1년에 2회(1~6월, 7~12월) 참여할 수 있습니다.</li>
			<li class="s322bostfor">자원봉사 시간은 3시간 또는 4시간만 운영합니다.</li>
			<li class="s322bostfor"> 자원봉사 신청은 아래 신청서를 작성 후 접수하시면 담당자가 연락드립니다.
				<ul>
					<li>단, 자원봉사는 신청일로부터 <span>최소 2주일 전에 해주셔야 합니다.</span></li>
				</ul>
			</li>
			<li class="s322bostfor">봉사인원은 2명까지 가능합니다.</li>
		</ul>
	</div>
	<div class="s1marg30">
		<!-- alert maeeage -->
		<c:import url="/WEB-INF/jsp/egovframework/com/asapro/common/message.jsp"/>
		
		<c:if test="${formMode eq 'INSERT'}"><c:set var="actionUrl" value="${APP_PATH}/volunteer/reservation/insert" /></c:if>
		<c:if test="${formMode eq 'UPDATE'}"><c:set var="actionUrl" value="${APP_PATH}/volunteer/reservation/update" /></c:if>
		<form:form modelAttribute="volunteerReservationForm" action="${actionUrl}" method="post" >
			<form:hidden path="reservId" />
			<form:hidden path="reservAgree" />
			
			<table class="tablest1 btmso">
				<caption>신청</caption>
				<colgroup>
					<col style="width:30%;" />
					<col style="width:70%;" />
				</colgroup>
				<tbody>
					<tr>
						<th>이름 <spen class="red">*</spen></th>
						<td class="lplstud">
							<form:input path="reservName" title="신청자 성명을 입력" cssClass="" cssErrorClass=""  />
							<form:errors path="reservName" element="div" cssClass="red" />
						</td>
					</tr>
					
					<tr>
						<th>학교 <spen class="red">*</spen></th>
						<td class="lplstud">
							<form:input path="reservGroup" title="학교" cssClass="" cssErrorClass=""/>
							<form:errors path="reservGroup" element="div" cssClass="red" />
						</td>
					</tr>
					
					<tr>
						<th>주소 <spen class="red">*</spen></th>
						<td class="lplstud">
							<form:input path="reservAddress" title="주소" cssClass="" cssErrorClass=""/>
							<form:errors path="reservAddress" element="div" cssClass="red" />
						</td>
					</tr>
					
					<tr>
						<th>목적 <spen class="red">*</spen></th>
						<td class="lplstud">
							<form:input path="reservPurpose" title="목적" cssClass="" cssErrorClass=""/>
							<form:errors path="reservPurpose" element="div" cssClass="red" />
						</td>
					</tr>
					
					<tr>
						<th rowspan="2">희망 날짜<br/>및 시간 <spen class="red">*</spen></th>
						<td class="lplstud3 inpswth">
							<label for="s322ips">날짜 : </label>
							<form:input path="reservDate" title="희망날짜" cssClass="posita datepicker-ui" cssErrorClass="posita datepicker-ui " autocomplete="off" readonly="true"/>
							<form:errors path="reservDate" element="div" cssClass="red" />
						</td>
					</tr>
					<tr>
						<td class="lplstud3">
							<label for="s322ips2">시간 : </label> 
							<form:input path="reservStime" cssClass="startTime select posita2" cssErrorClass="startTime select posita2 " maxlength="5" autocomplete="off" />
							~
							<form:input path="reservEtime" cssClass="startTime select posita2" cssErrorClass="startTime select posita2 " maxlength="5" autocomplete="off" />
							<form:errors path="reservStime" element="div" cssClass="red" />
							<form:errors path="reservEtime" element="div" cssClass="red" />
						</td>
					</tr>
					
					<tr>
						<th rowspan="2">신청자<br/>연락처 <spen class="red">*</spen></th>
						<td class="lplstud3">
							<label for="s322ips3">전화 : </label> 
							<form:select path="reservHp1" cssClass="posita2" cssErrorClass="posita2">
								<option value="" label="선택">
								<form:options items="${mobile1CodeList }" itemLabel="codeName" itemValue="codeId"/>
							</form:select>
							 - <form:input path="reservHp2" cssClass="posita2" cssErrorClass="posita2 " maxlength="4" />
							 - <form:input path="reservHp3" cssClass="posita2" cssErrorClass="posita2 " maxlength="4" />
							<form:errors path="reservHp1" element="div" cssClass="red" />
							<form:errors path="reservHp2" element="div" cssClass="red" />
							<form:errors path="reservHp3" element="div" cssClass="red" />
						</td>
					</tr>
					
					<tr>
						<td class="lplstud2">
							<label for="s322ips4">이메일 : </label> 
							<form:input path="reservEmail" cssClass="posita3" cssErrorClass="posita2 "  />
							<form:errors path="reservEmail" element="div" cssClass="red" />
							
						</td>
					</tr>
				
					<tr>
						<th>기타<br/>건의사항</th>
						<td class="lplstud4">
							<form:textarea path="reservSuggest" cols="" rows=""></form:textarea>
							<form:errors path="reservSuggest" element="div" cssClass="red" />
						</td>
					</tr>
				</tbody>
			</table>
		</form:form>
	</div>
	<div class="blgg1"><a href="#" class="insertBtn">예약신청</a></div>
</div>


<%-- ============================= 메뉴별 컨텐츠 끝 ============================ --%>

<!-- footer -->
<c:import url="${design.footer}" charEncoding="UTF-8" />