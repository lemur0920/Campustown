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
		$('#rentalReservationForm').submit();
	});
	
});
</script>

<%--<c:if test="${not empty messageCode}"><script>alert('<spring:message code="${messageCode}"></spring:message>');</script></c:if> --%>
<c:if test="${not empty messageCode}"><script>alert('${messageCode}');</script></c:if>

<!-- sidebar -->
<c:import url="${design.themeDir}/layout/sidebar.jsp" charEncoding="UTF-8" />

<%-- ============================= 메뉴별 컨텐츠 ============================ --%>
	<h4 class="title2 mb45">개인정보 제공 동의</h4>
	<div class="step-box01 mb73">
		<ul class="c4">
			<li>
				<div>
					<div class="con">
						<p class="txt01">Step #1</p>
						<p class="txt02"><span>예약 정보 확인</span></p>
					</div>
				</div>
			</li>
			<li class="on">
				<div>
					<div class="con">
						<p class="txt01">Step #2</p>
						<p class="txt02"><span>개인정보<br>제공동의</span></p>
					</div>
				</div>
			</li>
			<li>
				<div>
					<div class="con">
						<p class="txt01">Step #3</p>
						<p class="txt02"><span>신청서 작성</span></p>
					</div>
				</div>
			</li>
			<li>
				<div>
					<div class="con">
						<p class="txt01">Step #4</p>
						<p class="txt02"><span>담당자 승인</span></p>
					</div>
				</div>
			</li>
		</ul>
	</div>
	
	<div class="table-con style2 mb30">
		<table>
			<caption>개인정보 제공 동의</caption>
			<colgroup>
				<col style="width: 20%">
				<col style="width: auto">
			</colgroup>
			<tbody>
				<tr>
					<th scope="row">수집항목(필수)</th>
					<td>성명,연락처,이메일</td>
				</tr>
				<tr>
					<th scope="row">수집 및 이용 목적</th>
					<td>신청자 확인 및 연락</td>
				</tr>
				<tr>
					<th scope="row">보유기간</th>
					<td>대관일 기준 1개월 후 파기</td>
				</tr>
				<tr>
					<th scope="row">동의 거부 권리 안내</th>
					<td>개인정보 수집, 이용에 대한 동의를 거부할 권리가 있습니다.<br>그러나 동의를 거부할 경우 시설 대관 시청할수 없습니다.</td>
				</tr>
			</tbody>
		</table>
	</div>
	
	<div class="select-case2">
		<p>위와 같이 개인정보를 수집이용하는데 동의하십니까?</p>
		<form:form modelAttribute="rentalReservationForm" action="${APP_PATH}/rental/${rentalReservationForm.rental.rentCate1 }/reserv/${rentalReservationForm.rental.rentId }" method="get" >
<%-- 			${SECURITY_TOKEN_TAG} --%>
<%-- 			<form:hidden path="reservDate"/>
			<form:hidden path="reservTime"/>
 --%>			
			<span>
				<form:checkbox path="agreeChk" value="true" />
				<label for="agreeChk1">동의</label>
			</span>
			<form:errors path="agreeChk" element="p" class="text-error"/>
		</form:form>
	</div>

	<div class="btn-area txc">
		<a href="#n" class="btn02 fc-bg-red agreeBtn">확인</a>
	</div>

<%-- ============================= 메뉴별 컨텐츠 끝 ============================ --%>


<!-- footer -->
<c:import url="${design.footer}" charEncoding="UTF-8" />