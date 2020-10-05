<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="asapro" uri="http://www.asadal.com" %>

<!-- header -->
<c:import url="${design.header}" charEncoding="UTF-8" />

<script>
jQuery(function($){
	
	
	//대관 아이디
	var rentId = ${rental.rentId };
	//시설구분 코드
	var rentCate1 = '${rental.rentCate1 }';
	//var reservId = $('#reservId').val();
	var reservDate;
	var reservYear = '';
	var reservMonth ='';

	//달력로드
	function callCalendar(){
		var url = GLOBAL.APP_PATH + '/rental/' + rentCate1 + '/calendar/inner' + GLOBAL.API_PATH + '/' + rentId;
		url += '?reservYear=' + reservYear + '&reservMonth=' + reservMonth;
		$('#calendarWrapper').load(url);		
	}
	
	//최초 달력로딩
	callCalendar();
	
	//달력 이전,다음달 클릭
	$('#content').on('click', '.pre, .next', function(){
		reservYear = $(this).data('year');
		reservMonth = $(this).data('month');
		callCalendar();
	});
	
	//달력 년도선택
	$('#content').on('change', 'select[name=reservYear]', function(){
		reservYear = $(this).val();
		callCalendar();
	});
	
	//달력 월선택
	$('#content').on('change', 'select[name=reservMonth]', function(){
		reservMonth = $(this).val();
		callCalendar();
	});
	
	//예약가능시간 노출
	$('#content').on('click', '.reservDateBtn', function(){
		reservDate = $(this).data('date');
		$('.possibleTimeList').load(GLOBAL.APP_PATH + '/rental/' + rentCate1 + GLOBAL.API_PATH + '/possibleTimeList?rentId=' + rentId + '&reservDate=' + reservDate);
		//$('#possibleTimeList').show();
		$('.possibleTimeList').show();
		$('.cfocus').removeClass('cfocus');
		$(this).addClass('cfocus');
	});
	
	//가능시간 레이어 닫기,취소
	$('#content').on('click', '#popup_con .btn_cencle, #popup_con .btn_close', function(){
		$('.possibleTimeList').empty();
        $('.cfocus').focus();
        $('#popup_con').hide();
    });
	
	//가능시간 선택
	$('#content').on('click', '.possibleTimeList td.able', function(){
		$(this).toggleClass('selected')
		
	});
	
	//예약버튼
	$('#content').on('click', '.possibleTimeList .reservBtn', function(e){
		e.preventDefault();
		var reservTime = '';
		$('.possibleTimeList .selected').each(function(index, item){
			if(index != 0){
				reservTime += ','
			}
			reservTime += $(this).data('time');
			//alert($(this).data('time'));
		});
		
		if(reservTime == ''){
			alert('예약시간을 선택해 주세요');
			return false;
		}

		//location.href = GLOBAL.APP_PATH + '/rental/' + rentCate1 + '/reserv/' + rentId + '?reservDate=' + reservDate + '&reservTime=' + reservTime;
		$('#reservDate').val(reservDate);
		$('#reservTime').val(reservTime);
		$('#dateSelectForm').submit();
	});
	

});
</script>

<!-- sidebar -->
<c:import url="${design.themeDir}/layout/sidebar.jsp" charEncoding="UTF-8" />


<%-- ============================= 메뉴별 컨텐츠 ============================ --%>
	<!-- content -->
	<h4 class="title2 mb45">${rental.rentTitle }</h4>
	<div class="step-box01 mb73">
		<ul class="c4">
			<li class="on">
				<div>
					<div class="con">
						<p class="txt01">Step #1</p>
						<p class="txt02"><span>예약 정보 확인</span></p>
					</div>
				</div>
			</li>
			<li>
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


	<!-- 달력출력 wrapper -->
	<div class="appointmentTime" id="calendarWrapper">

	</div>
	<!-- //달력출력 wrapper -->

	<!-- 예약가능시간목록 팝업 -->
	<div id="popup_con" class="yycheck-show possibleTimeList">

	</div>
	<!-- 예약가능시간목록 팝업// -->
	
	
	<%-- 예약할 날짜와 시간을 신청 페이지로 넘기기 위한 폼 --%>
	<%-- <form action="${APP_PATH }/rental/${rental.rentCate1 }/reserv/${rental.rentId }" name="dateSelectForm" method="get" id="dateSelectForm"> --%>
	<form action="${APP_PATH }/rental/agree" name="dateSelectForm" method="post" id="dateSelectForm">
		${SECURITY_TOKEN_TAG}
		<input type="hidden" name="reservDate" id="reservDate" value="">
		<input type="hidden" name="reservTime" id="reservTime" value="">
		<input type="hidden" name="rental.rentId" id="reservRentId" value="${rental.rentId}">
		<input type="hidden" name="rental.rentCate1" id="reservRentCate1" value="${rental.rentCate1}">
	</form>
	
<%-- ============================= 메뉴별 컨텐츠 끝 ============================ --%>


<!-- footer -->
<c:import url="${design.footer}" charEncoding="UTF-8" />