<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="asapro" uri="http://www.asadal.com" %>

<!-- header -->
<c:import url="${design.header}" charEncoding="UTF-8" />

<script>
jQuery(function($){
	$('.agreeBtn').on('click', function(e){
		e.preventDefault();
		$('#volunteerReservationForm').submit();
	});
	
});
</script>

<!-- sidebar -->
<c:import url="${design.themeDir}/layout/sidebar.jsp" charEncoding="UTF-8" />

<%-- ============================= 메뉴별 컨텐츠 ============================ --%>

<div class="RightCWarp">
	<div class="pytspl">개인정보 수집 및 이용 동의</div>
	
	<div class="s1marg30">
		<!-- alert maeeage -->
		<c:import url="/WEB-INF/jsp/egovframework/com/asapro/common/message.jsp"/>
		
		<table class="tablest1">
			<caption>개인정보 수집 및 이용 동의</caption>
			<colgroup>
				<col style="width:30%;" />
				<col style="width:70%;" />
			</colgroup>
			<tbody>
				<tr>
					<th>수집항목<span>(필수)</span></th>
					<td>성명, 주소, 연락처, 신청단체</td>
				</tr>
				<tr>
					<th>수집 및 이용 목적</th>
					<td>신청자 확인 및 연락</td>
				</tr>
				<tr>
					<th>보유기간</th>
					<td>자원봉사일·관람일·교육일 기준 한 달 후 파기</td>
				</tr>
				<tr>
					<th>동의 거부 권리 안내</th>
					<td>개인정보 수집·이용 동의를 거부할 수 있습니다.<br/>다만, 이 경우 자원봉사·단체관람·교육프로그램 신청이 제한됩니다.</td>
				</tr>
			</tbody>
		</table>
	</div>
	<div class="s23fors mgt10">위 조건에 따라 개인정보 수집에 동의하십니까?</div>
	<form:form modelAttribute="volunteerReservationForm" action="${APP_PATH}/volunteer/reservation/write" method="post" >
		<div class="s23pschk mgt10">
			<label for="s23erw1">동의함</label><form:radiobutton path="reservAgree" id="s23erw1" value="true" />
			<label for="s23erw2">동의안함</label><form:radiobutton path="reservAgree" id="s23erw2" value="false" />
			<form:errors path="reservAgree" element="div" class="red"/>
		</div>
	</form:form>
	<div class="blgg1"><a href="#n" class="agreeBtn">확인</a></div>
</div>

<%-- ============================= 메뉴별 컨텐츠 끝 ============================ --%>


<!-- footer -->
<c:import url="${design.footer}" charEncoding="UTF-8" />