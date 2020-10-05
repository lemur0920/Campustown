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

		
			
	<!--popup-1-->
	<div class="popup popup2 popup-1 popup-1-1" tabindex="0">
		<span class="pop_tit nsq">결제취소</span>
		
		<!-- 팝업 상세 -->
		<div class="con con724">
			<p class="p7251">결제를 취소하시겠습니까?</p>
			<div class="d7251">
				<div class="div1">
					<p>
						<span class="span1">결제금액</span>
						<span class="span2"><fmt:formatNumber value="${rentalReservation.reservPaymentAmount}" pattern="#,###" />원</span>
					</p>
					<p>
						<span class="span1">차감금액</span>
						<span class="span2">0원</span>
					</p>
					<p>
						<span class="span1">환불금액</span>
						<span class="span2"><fmt:formatNumber value="${rentalReservation.reservPaymentAmount}" pattern="#,###" />원</span>
					</p>
				</div>
			</div>
			<p class="btbt7252">
				<a href="#non" class="a1 payCancelBtn">결제취소</a>
				<a href="javascript:void(0)" class="a3 closeBtn">닫기</a>
			</p>

		</div>
		<!-- 팝업 상세 end  -->
		<button type="button" class="btn btn_close"><!--닫기 눌렀을 때 팝업창을 띄울 때 누른 button 이나 a 링크에 class="cfocus" 주세요. (접근성)-->닫기</button>
	</div>
	<!--popup-1//-->
