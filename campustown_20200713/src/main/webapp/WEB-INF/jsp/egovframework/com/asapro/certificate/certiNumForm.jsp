<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!doctype html>
<html lang="ko">
<head>
	<title>SMS 인증</title>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width,initial-scale=1.0,minimum-scale=1.0,maximum-scale=1.0">
	<meta name="subject" content="">
	<meta name="keywords" content="">
	<meta name="description" content="">
	<meta name="author" content="">
	<meta name="copyright" content="copyrights Seoul Metropolitan Govement all rights reserved">

	<!-- css  -->
	<link type="text/css" rel="stylesheet" href="${design.resource }/css/common.css">
	<!-- css 서브페이지용 -->
	<link type="text/css" rel="stylesheet" href="${design.resource }/css/pop_c.css">
	
	<script src="${CONTEXT_PATH }/design/common/js/jquery.min.js"></script>

<script>
jQuery(function($){
	
	/*
	인증유효시간 타이머
	*/
	$("#id01").focus();
	chkOTPLimitTime(180);
	
	function chkOTPLimitTime(limitsec) {
	    remain = limitsec - 1;
	
	    if (remain >= -1) {
	        mod = limitsec % (24 * 3600);
	        mod = mod % 3600;
	        min = Math.floor(mod / 60);
	        sec = mod % 60;
	        if (sec < 10) {
	            sec = '0' + sec;
	        }
	
	        $('#timer').html(min + ":" + sec);
	
	        if (remain == -1) {
	            alert("재한시간이 만료되었습니다.");
	            location.href = '${APP_PATH}/certificate/request';
	        }
	        else {
	            timerid = setTimeout(function(){chkOTPLimitTime(remain)}, 1000);
	        }
	    }
	}
	
	//sms 인증번호 전송
	$('.btnSubmit').on('click', function(e){
		e.preventDefault();
		$.ajax({
			url : $('#allowIpTempForm').attr('action')
			, type : 'post'
			, dataType: 'json'
			, data: $('#allowIpTempForm').serialize()
		}).done(function(result){
			if(result.serverMessage.success){
					//alert(result.serverMessage.text);
					location.href = '${ADMIN_PATH}/login.do';
					
			} else {
				if(result.serverMessage.text ){
					alert(result.serverMessage.text);
					if(result.serverMessage.resultCode == '21'){//인증횟수재한
						location.href = '${APP_PATH}/certificate/request';
					}
					$("#id01").val('');
				} else {
					alert('인증실패 [error]');
				}
			}
		}).fail(function(result){
			alert('인증실패 [fail]');
		});
		$("#id01").focus();
	});
	
	
	//인증번호 SMS 다시발송
	$('.btnReSMS').on('click', function(e){
		e.preventDefault();
		$.ajax({
			url : '${APP_PATH}/certificate${API_PATH}/reSMSCertiNum'
			, type : 'post'
			, dataType: 'json'
			, data: $('#allowIpTempForm').serialize()
		}).done(function(result){
			if(result.success){
					alert(result.text);
					//chkOTPLimitTime(180);
			} else {
				if(result.text ){
					alert(result.text);
					if(result.resultCode == '21'){//발송횟수재한
						location.href = '${APP_PATH}/certificate/request';
					}
					$("#id01").val('');
				} else {
					alert('인증번호발송 실패 [error]');
				}
			}
		}).fail(function(result){
			alert('인증번호발송 실패 [fail]');
		});
		$("#id01").focus();
	});
	
	//입력폼 엔터키 누를경우 submit막기
	$('input').keydown(function() {
	    if (event.keyCode === 13) {
	        event.preventDefault();
	    }
	});
});
</script>
</head>
<body>
<!-- wrap -->
<div id="wrap">
	
	<div class="certify tct">
		<h1 class="logo"><img src="${design.resource }/images/pop_c/logo.gif" alt="서울청년포털"></h1>
		<h2 class="nsq">SMS 인증</h2>
		<p class="f18 mb_30">핸드폰으로 받은 SMS 인증 번호를 입력하세요.</p>
		
		<div class="sms">
			<form:form modelAttribute="allowIpTempForm" action="${APP_PATH}/certificate${API_PATH}/certiNumSubmit" method="post">
				${SECURITY_TOKEN_TAG}
				<form:hidden path="adminId"/>
				<div class="sms_input">
					<form:label path="certiNum" for="id01">SMS 인증번호</form:label>
					<span><form:password path="certiNum" id="id01"  title="남은 시간: 03:00" maxlength="6"/>
					<strong id="timer" class="text-error"></strong></span>
					<form:errors path="certiNum" element="div" cssClass="text-error" />
				</div>
				<div class="btn_wrap">
					<button type="button" class="btn t_white nsq btnReSMS">SMS 다시 받기</button>
					<a href="${APP_PATH }/home" class="btn t_white nsq">닫기</a>
					<button type="button" class="btn t_cf nsq btnSubmit">확인</button>
				</div>
			</form:form>
		</div>
	</div>
	
</div>
</body>
</html>