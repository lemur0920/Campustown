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
		, minDate: 0	//출력할 최소 날짜 
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
		$('#educationReservationForm').submit();
	});
	
});
</script>

<!-- sidebar -->
<c:import url="${design.themeDir}/layout/sidebar.jsp" charEncoding="UTF-8" />

<%-- ============================= 메뉴별 컨텐츠 ============================ --%>

<div class="RightCWarp">
	
	<div class="s1marg30">
		<!-- alert maeeage -->
		<c:import url="/WEB-INF/jsp/egovframework/com/asapro/common/message.jsp"/>
		
		<c:if test="${formMode eq 'INSERT'}"><c:set var="actionUrl" value="${APP_PATH}/education/reservation/insert" /></c:if>
		<c:if test="${formMode eq 'UPDATE'}"><c:set var="actionUrl" value="${APP_PATH}/education/reservation/update" /></c:if>
		<form:form modelAttribute="educationReservationForm" action="${actionUrl}" method="post" >
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
							<form:textarea path="reservMemo" cols="" rows=""></form:textarea>
						</td>
					</tr>
				</tbody>
			</table>
		</form:form>
	</div>
	<div class="blgg1"><a href="#" class="insertBtn">예약신청</a></div>
	
	<div class="s1marg30 s322porsi">
		<p>"한국방송광고진흥공사에서는 자유학기제 전면시행에 발맞춰 자유학기제 관련 교육을 진행하고 있습니다.자유학기제 관련 교육 희망시에는 광고교육원 자유학기제 담당자(02-2144-0147)에게 문의하시기 바랍니다." </p>
		<p class="mgt10">- 자유학기제 관련 교육</p>
		<ul>
			<li class="s322bostfor">  탐색활동 : 공익광고를 활용한 광고인(카피라이터) 체험 교육
				<ul>
					<li>- 교육은 2~3시간 동안 진행되며, 교육종료 후 박물관 견학도 가능합니다.</li>
				</ul>
			</li>
			<li class="s322bostfor">    자유교과.동아리활동 : 광고의 기본지식 습득 및 공익광고 제작
				<ul>
					<li>- 7차수(14시간) : 1일 2시간씩 7차수로 진행되며, 스토리보드 제작</li>
					<li>- 14차수(28시간) : 1일 2시간씩 14차수로 진행되며, 동영상 광고 제작</li>
				</ul>
			</li>
		</ul>
		<p class="mgt10">※ 교육 일정 및 시간은 학교와 협의하여 진행하며, 사정에 따라 변경될수 있음.</p>
		<div class="blgg1"><a href="https://edu.kobaco.co.kr" target="_blank" class="">광고교육원 바로가기</a></div>
	</div>
</div>


<%-- ============================= 메뉴별 컨텐츠 끝 ============================ --%>

<!-- footer -->
<c:import url="${design.footer}" charEncoding="UTF-8" />