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
	
});
</script>

<!-- sidebar -->
<c:import url="${design.themeDir}/layout/sidebar.jsp" charEncoding="UTF-8" />

<%-- ============================= 메뉴별 컨텐츠 ============================ --%>
<div class="RightCWarp">

	<h4 class="title10_1">${rentalReservationForm.rental.rentTitle } <span>(${asapro:codeName(rentalReservationForm.rental.rentCate1, rentCate1List) })</span></h4>

	<div class="sub91_11">
		<h5 class="titleOr1 TextCenter13">시설 이용 예약 신청이 완료되었습니다.</h5>
	</div>

	<div class="s1marg30">
		<table class="tablest112">
			<caption>사용신청서표</caption>
			<colgroup>
				<col style="width:25%;" />
				<col />
			</colgroup>
			<tbody>

				<tr>
					<th scope="row">행사명</th>
					<td class="txtLf">${rentalReservationForm.reservTitle }</td>
				</tr>

				<tr>
					<th scope="row">사용날짜 및 시간</th>
					<td class="txtLf">${rentalReservationForm.reservDate } (${rentalReservationForm.reservTime })
						<c:if test="${not empty rentalReservationForm.reservTimeAdd }"><span> + 추가 : ${rentalReservationForm.reservTimeAdd } 시간</span></c:if>
					</td>
				</tr>

				<tr>
					<th scope="row">사용인원</th>
					<td class="txtLf">${rentalReservationForm.reservTotal }명</td>
				</tr>

				<tr>
					<th scope="row">결제방법</th>
					<td class="txtLf">${asapro:codeName(rentalReservationForm.reservPaidType, paidTypeCodeList ) }</td>
				</tr>

				<tr>
					<th scope="row">신청기관명</th>
					<td class="txtLf">${rentalReservationForm.reservOrganization }</td>
				</tr>

				<tr>
					<th scope="row">연락처</th>
					<td class="txtLf">${rentalReservationForm.reservHp }</td>
				</tr>

				<tr>
					<th scope="row">주소</th>
					<td class="txtLf">${rentalReservationForm.reservZipcode } ${rentalReservationForm.reservAddress } ${rentalReservationForm.reservAddressDetail }</td>
				</tr>

				<tr>
					<th scope="row">신청자 성명</th>
					<td class="txtLf">${rentalReservationForm.reservName }</td>
				</tr>

				<tr>
					<th scope="row">${rentalReservationForm.reservPosition }</th>
					<td class="txtLf">과장</td>
				</tr>

				<tr>
					<th scope="row">e-mail</th>
					<td class="txtLf">${rentalReservationForm.reservEmail }</td>
				</tr>

				<tr>
					<th scope="row">결제기한 및 입금계좌</th>
					<td class="txtLf">- 결제기한 : 2018년 5월 8일<br/>- 입금계좌 : 우리은행 1005-903-066128 한국방송광고진흥공사<br/>- 관련문의 : 02-2144-0123</td>
				</tr>

			</tbody>
		</table>
	</div>


	<div class="blgg1 blgg112">
		<a href="javascript:void(0)" class="grbLink">인쇄하기</a>
		<a href="${APP_PATH}/rental/${rentalReservationForm.rental.rentCate1}">확인</a>
	</div>
</div>
<%-- ============================= 메뉴별 컨텐츠 끝 ============================ --%>

<!-- footer -->
<c:import url="${design.footer}" charEncoding="UTF-8" />