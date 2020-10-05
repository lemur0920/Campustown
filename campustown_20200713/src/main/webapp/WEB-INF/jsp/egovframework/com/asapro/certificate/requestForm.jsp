<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!doctype html>
<html lang="ko">
<head>
	<title>관리 인증 신청</title>
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

<script>

</script>
</head>
<body>
<!-- wrap -->
<div id="wrap">



	<div class="certify tct">
		<h1 class="logo"><img src="${design.resource }/images/pop_c/logo.gif" alt="서울청년포털"></h1>
		<h2 class="nsq admin">관리 인증 신청</h2>
		<ul class="list_dot f16">
			<li>허용된 PC 가 아닌, 일회성으로 로그인 하기 위한 인증 사이트입니다.</li>
			<li>기존 관리자로 등록된 사용자만 SMS 인증을 통해 일회성으로 청년포털 관리시스템에 로그인 할 수 있습니다. </li>
			<li>PC를 끄지 않고, 계속 사용하면 허용된 시간 동안 계속 사용이 가능합니다.</li>
			<li>IP가 변경되었을 경우 다시 임시 로그인 인증을 받아야 합니다.</li>
			<li>03:00 에 리셋되니, 다시 인증을 받아야 됩니다.</li>
		</ul>
		<div class="admin">
			<p class="guide">ID / PW를 입력하면 등록된 휴대폰으로 SMS 인증 번호를 보내드립니다.</p>
			<form:form modelAttribute="allowIpTempForm" action="${APP_PATH}/certificate/request" method="post">
				${SECURITY_TOKEN_TAG}
				<div class="admin_input">
					<p class="ip_id">
						<form:label path="adminId" for="id01">ID</form:label>
						<form:input path="adminId" id="id01" autocomplete="off"/>
					</p>
					<form:errors path="adminId" element="div" cssClass="text-error" />
					
					<p class="ip_pw">
						<form:label path="adminPassword" for="id02">PW</form:label>
						<form:password path="adminPassword" id="id02"/>
					</p>
					<form:errors path="adminPassword" element="div" cssClass="text-error" />
				</div>
				<div class="btn_wrap">
					<a href="${APP_PATH }/home" class="btn t_white nsq">닫기</a>
					<button type="submit" class="btn t_cf nsq">확인</button>
				</div>
			</form:form>
		</div>
	</div>
</div>
</body>
</html>