<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="asapro" uri="http://www.asadal.com" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<jsp:useBean id="now" class="java.util.Date" />
<fmt:formatDate value="${now}" pattern="yyyy-MM-dd" var="today" />

<!-- header -->
<c:import url="${design.header}" charEncoding="UTF-8" />

<script>
jQuery(function($){
	
	//예약취소 레이어 출력
	$('#content').on('click', '.cancelLayerBtn, .paymentLayerBtn, .payCancelLayerBtn', function(){
		
 		var reservId = $(this).data('reservid');
 		var layerType = $(this).data('layertype');

 		$('#popup_con').load(GLOBAL.APP_PATH + '/mypage/rental/reserv' + GLOBAL.API_PATH + '/' + layerType + '?reservId=' + reservId);
		$('.cfocus').removeClass('cfocus');
		$(this).addClass('cfocus');

		$('#popup_con').show();
	});
	
	
	//레이어 닫기
	$('#content').on('click', '.closeBtn, .btn_close', function(){
		$('#popup_con').empty();
        $('.cfocus').focus();
        $('#popup_con').hide();
	});
	
	//예약취소처리 - 알림문자발송
	$('#content').on('click', '.cancelBtn', function(e){
		e.preventDefault();

		var reservId = $(this).data('reservid');
		
		if(reservId == ''){
			alert('취소할 항목 정보가 없습니다.');
			return;
		}
		
		if(confirm('예약취소 하시겠습니까?')){
	       	$.ajax({
				url : GLOBAL.APP_PATH + '/mypage/rental/reserv' + GLOBAL.API_PATH + '/cancel'
				, type : 'post'
				, dataType: 'json'
				, data: {
					reservId : reservId
					, ${SECURITY_TOKEN_NAME} : '${SECURITY_TOKEN}'
				}
			}).done(function(result){
				if(result.success){
					alert(result.text);
					location.reload();
				} else {
					alert(result.text);
				}
			}).fail(function(result){
					alert('취소처리를 하지 못하였습니다.[fail]');
			});
		}
	});
	
	//결제처리 - 온라인결제만
	$('#content').on('click', '.paymentBtn', function(e){
		e.preventDefault();
alert('온라인 결제를 준비중 입니다.');
return false;

		var reservId = $(this).data('reservid');
		
		if(reservId == ''){
			alert('결제할 항목 정보가 없습니다.');
			return;
		}
		
		if(confirm('결제를 하시겠습니까?')){
	       	$.ajax({
				url : GLOBAL.APP_PATH + '/mypage/rental/reserv' + GLOBAL.API_PATH + '/payment'
				, type : 'post'
				, dataType: 'json'
				, data: {
					reservId : reservId
					, ${SECURITY_TOKEN_NAME} : '${SECURITY_TOKEN}'
				}
			}).done(function(result){
				if(result.success){
					alert(result.text);
					location.reload();
				} else {
					alert(result.text);
				}
			}).fail(function(result){
					alert('결제를 하지 못하였습니다.[fail]');
			});
		}
	});
	
	//결제취소처리 - 온라인결제만
	$('#content').on('click', '.payCancelBtn', function(e){
		e.preventDefault();
		
alert('온라인 결제취소를 준비중 입니다.');
return false;

		var reservId = $(this).data('reservid');
		
		if(reservId == ''){
			alert('결제취소할 항목 정보가 없습니다.');
			return;
		}
		
		if(confirm('결제취소를 하시겠습니까?')){
	       	$.ajax({
				url : GLOBAL.APP_PATH + '/mypage/rental/reserv' + GLOBAL.API_PATH + '/payCancel'
				, type : 'post'
				, dataType: 'json'
				, data: {
					reservId : reservId
					, ${SECURITY_TOKEN_NAME} : '${SECURITY_TOKEN}'
				}
			}).done(function(result){
				if(result.success){
					alert(result.text);
					location.reload();
				} else {
					alert(result.text);
				}
			}).fail(function(result){
					alert('결제취소를 하지 못하였습니다.[fail]');
			});
		}
	});
});
</script>

<!-- sidebar -->
<c:import url="${design.themeDir}/layout/sidebar.jsp" charEncoding="UTF-8" />


<%-- ============================= 메뉴별 컨텐츠 ============================ --%>
	<!-- content -->
		
	<div class="board-table style2 mtp50">
		<table>
			<caption>공유공간예약</caption>
			<colgroup>
				<col style="width: auto">
				<col style="width: 20%">
				<col style="width: 25%">
				<col style="width: 15%">
				<col style="width: 15%">
			</colgroup>
			<thead>
				<tr>
					<th scope="col">캠퍼스타운명</th>
					<th scope="col">공간명</th>
					<th scope="col">예약날짜</th>
					<th scope="col">상태</th>
					<th scope="col">예약</th>
				</tr>
			</thead>
			<tbody>
				<c:if test="${empty paging.result}">
					<tr>
						<td colspan="5" class="text-center">등록된 대관정보가 없습니다.</td>
					</tr>
				</c:if>
				<c:forEach items="${paging.result}" var="item" varStatus="vs">
					<tr>
						<td>
							<c:forEach items="${rentCate1List}" var="cate1" varStatus="vs">
								<c:if test="${cate1.univId eq item.rental.rentCate1 }">${cate1.univNm }</c:if>
							</c:forEach>
							<%-- ${asapro:codeName(item.rental.rentCate1, rentCate1List) } ${item.rental.rentTitle }  --%>
							
						</td>
						<td>${item.rental.rentTitle }</td>
						<td>${item.reservDate }(${asapro:getDayOfWeekKorText(item.reservDate) }) ${fn:replace(item.reservTime, ',', ' ,')}</td>
						<%-- <td><fmt:formatNumber value="${item.reservPaymentAmount}" pattern="#,###" />원</td> --%>
						<td><span class="span1 ${item.reservStatus }">[ ${asapro:codeName(item.reservStatus,statusCodeList) } ]</span></td>
						<td>
							<c:if test="${item.reservStatus < 'RS40' }">
								<a href="#non" class="a1 yycheck2 cancelLayerBtn cancel-btn" data-reservid="${item.reservId }" data-layertype="cancelLayer">예약취소</a>
							</c:if>
						</td>
						<%-- <td>
							<c:if test="${item.reservStatus eq 'RS30' }">
								<a href="#non" class="a1 a2 yycheck3 paymentLayerBtn" data-reservid="${item.reservId }" data-layertype="paymentLayer">결제하기</a>
							</c:if>
							<c:if test="${item.reservStatus eq 'RS40' and fn:startsWith(item.reservPaidType, 'online')  }">
								<a href="#non" class="a1 a3 yycheck1 payCancelLayerBtn" data-reservid="${item.reservId }" data-layertype="payCancelLayer">결제취소</a>
							</c:if>
						</td> --%>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>

	<!--컨텐츠팝업-->
	<div id="popup_con" class="yycheck-show">
		
		
		
	</div>
	<!--컨텐츠팝업-->


	<!-- paging -->
	<c:out value="${paging.userImageTag}" escapeXml="false"/>


	<!-- content end -->

<!-- footer -->
<c:import url="${design.footer}" charEncoding="UTF-8" />