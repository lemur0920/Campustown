<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!doctype html>
<html lang="ko">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimal-ui">
    <meta name="description" content="AsaproX Administrator">
    <meta name="author" content="">
    
	<title>AsaproX Administrator</title>
	<c:import url="/WEB-INF/jsp/egovframework/com/asapro/admin/include/include.jsp" charEncoding="UTF-8" />
	
</head>
<body class="fixed-left">

	<c:if test="${isOverlap == true }">
		<script>
		jQuery(function($){
			swal({
	            type: 'warning',
	            title: '중복로그인!!',
	            html : '접속중인 사용자가 있습니다. 기존 연결을 끊고 로그인 하였습니다.'

	        });
		});
		</script>
	</c:if>
	<script>
		jQuery(function($){
			//url클립보드 복사
			$('.urlClipboard').on('click', function(e){
				e.preventDefault();
				
				var clipboard = new ClipboardJS('.urlClipboard');

				clipboard.on('success', function(e) {
				    /* console.info('Action:', e.action);
				    console.info('Text:', e.text);
				    console.info('Trigger:', e.trigger); */
				    alert("링크 복사가 완료되었습니다.")
				    e.clearSelection();
				});

				clipboard.on('error', function(e) {
				    /* console.error('Action:', e.action);
				    console.error('Trigger:', e.trigger); */
				    alert("Ctrl + C 를 누르면 복사가 완료됩니다.")
				});
			});
		});
	</script>

	<!-- Loader -->
	<div id="preloader"><div id="status"><div class="spinner"></div></div></div>
	
	<!-- Begin page -->
	<div id="wrapper">
