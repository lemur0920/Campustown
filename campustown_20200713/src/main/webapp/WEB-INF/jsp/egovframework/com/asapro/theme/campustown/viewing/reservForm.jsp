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
		, minDate: 7	//출력할 최소 날짜 (1주일전)
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
		$('#viewingReservationForm').submit();
	});
	
});
</script>

<!-- sidebar -->
<c:import url="${design.themeDir}/layout/sidebar.jsp" charEncoding="UTF-8" />

<%-- ============================= 메뉴별 컨텐츠 ============================ --%>
<div class="RightCWarp">
	<div class="s322porsi">
		<ul>
			<li class="s322bostfor">  단체관람 예약은 박물관의 원활한 운영과 관람 그리고 관람객의 편의를 위해 필요한 사항이므로 이용자께서는  이점 양지해주시기 바랍니다.
			</li>
			<li class="s322bostfor mgt10">  단체관람예약 확인사항</li>
		</ul>
		<p>- 신청인원 : 10명 이상</p>
		<p>- 관람시간 : 40분 소요</p>
		<p>- 예약일자 : 방문 예정일 1주일 전까지 예약(불가피한 경우 제외)</p>
		<p>- 신청자(개인 ·단체)에 대한 정보(연락처, 방문일자, 목적 등)를 서식에 맞게 작성</p>
	</div>
	
	<div class="s1marg30">
		<!-- alert maeeage -->
		<c:import url="/WEB-INF/jsp/egovframework/com/asapro/common/message.jsp"/>
		
		<c:if test="${formMode eq 'INSERT'}"><c:set var="actionUrl" value="${APP_PATH}/viewing/reservation/insert" /></c:if>
		<c:if test="${formMode eq 'UPDATE'}"><c:set var="actionUrl" value="${APP_PATH}/viewing/reservation/update" /></c:if>
		<form:form modelAttribute="viewingReservationForm" action="${actionUrl}" method="post" >
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
						<th>신청자 <spen class="red">*</spen></th>
						<td class="lplstud">
							<form:input path="reservName" title="신청자 성명을 입력" cssClass="" cssErrorClass=""  />
							<form:errors path="reservName" element="div" cssClass="red" />
						</td>
					</tr>
					<tr>
						<th>신청단체 <spen class="red">*</spen></th>
						<td class="lplstud">
							<form:input path="reservGroup" title="신청단체" cssClass="" cssErrorClass=""/>
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
						<th>인원 <spen class="red">*</spen></th>
						<td class="lplstud2 wthass">
							<form:input path="reservPeople" title="인원" cssClass="" cssErrorClass=""/> 명
							<form:errors path="reservPeople" element="div" cssClass="red" />
						</td>
					</tr>
					<tr>
						<th>관람 날짜 및 시간 <spen class="red">*</spen></th>
						<td class="lplstud3 inpswth">
							<form:input path="reservDate" title="관람날짜" cssClass="posita datepicker-ui" cssErrorClass="posita datepicker-ui " autocomplete="off" readonly="true"/>
							<form:input path="reservStime" cssClass="startTime select posita2" cssErrorClass="startTime select posita2 " maxlength="5" autocomplete="off" />
							~
							<form:input path="reservEtime" cssClass="startTime select posita2" cssErrorClass="startTime select posita2 " maxlength="5" autocomplete="off" />
							<form:errors path="reservDate" element="div" cssClass="red" />
							<form:errors path="reservStime" element="div" cssClass="red" />
							<form:errors path="reservEtime" element="div" cssClass="red" />
						</td>
					</tr>
					<tr>
						<th>연락처 <spen class="red">*</spen></th>
						<td class="lplstud3">
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
						<th>이메일</th>
						<td class="lplstud">
							<form:input path="reservEmail" cssClass="" cssErrorClass=" "  />
							<form:errors path="reservEmail" element="div" cssClass="red" />
							
						</td>
					</tr>
				</tbody>
			</table>
		</form:form>
	</div>
	<div class="blgg1"><a href="#n" class="insertBtn">예약신청</a></div>
</div>


<%-- ============================= 메뉴별 컨텐츠 끝 ============================ --%>

<!-- footer -->
<c:import url="${design.footer}" charEncoding="UTF-8" />