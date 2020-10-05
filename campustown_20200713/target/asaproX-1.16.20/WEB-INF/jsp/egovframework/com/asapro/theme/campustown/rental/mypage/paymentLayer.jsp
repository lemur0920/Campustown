<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="asapro" uri="http://www.asadal.com" %>

<script>
jQuery(function($){
	
});
</script>

		
			
	<!--popup-3-->
	<div class="popup popup2 popup-1 popup-1-3" tabindex="0">
		<span class="pop_tit nsq">결제하기</span>
		
		<!-- 팝업 상세 -->
		<div class="con con724">
			<p class="p7251">결제 정보 확인</p>
			<div class="d7251">
				<div>
					<p>
						<span class="span1">예약공간</span>
						<span>${asapro:codeName(rentalReservation.rental.rentCate1, rentCate1List) } [${rentalReservation.rental.rentTitle }]</span>
					</p>
					<p>
						<span class="span1">예약날짜</span>
						<span>${rentalReservation.reservDate }(${asapro:getDayOfWeekKorText(rentalReservation.reservDate) })</span>
					</p>
					<p>
						<span class="span1">예약시간</span>
						<span>${fn:replace(rentalReservation.reservTime, ',', '<br>')}</span>
					</p>
					<p>
						<span class="span1">결제예정금액</span>
						<span><fmt:formatNumber value="${rentalReservation.reservPaymentAmount}" pattern="#,###" />원</span>
					</p>
				</div>
			</div>
			<p class="org7251">결제 전에, 환불 기준과 예약내용을 반드시 확인해 주세요</p>
			<p class="btbt7252">
				<a href="#non" class="a1 paymentBtn">결제</a>
				<a href="javascript:void(0)" class="a3 closeBtn">닫기</a>
			</p>

		</div>
		<!-- 팝업 상세 end  -->
		<button type="button" class="btn btn_close"><!--닫기 눌렀을 때 팝업창을 띄울 때 누른 button 이나 a 링크에 class="cfocus" 주세요. (접근성)-->닫기</button>
	</div>
	<!--popup-3//-->