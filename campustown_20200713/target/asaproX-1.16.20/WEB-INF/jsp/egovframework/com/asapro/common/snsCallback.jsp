<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html>
<head>
<meta charset="UTF-8" />
<title>SNS ERROR</title>
<link href="/asset/bootstrap/css/bootstrap.min.css" rel="stylesheet" />
<link href="/asset/css/admin.css" rel="stylesheet" />
<style type="text/css">
body{padding-top: 20px;}
</style>
</head>
<body>
<div class="container-fluid" id="container">
	<div class="row-fluid">
		<div class="span12">
			<c:if test="${empty snsError}">
				<c:if test="${sessionScope['sns.mode'] == 'join'}">
					<script>
					opener.location.href = '${APP_PATH}/sns/join/agreement';
					self.close();
					</script>
				</c:if>	
				<c:if test="${snsMode == 'login'}">
					<script>
					opener.location.href = '${APP_PATH}/home';
					self.close();
					</script>
				</c:if>	
			</c:if>
			<c:if test="${not empty snsError}">
				<c:choose>
					<c:when test="${snsError == 'access_denied'}">
						<div class="alert alert-error">
							ACCESS DENIED.
						</div>
						<script>
							alert('접속이 거부되었습니다.');
							self.close();
						</script>
					</c:when>
					<c:when test="${snsError == 'request_token_not_matched'}">
						<div class="alert alert-error">
							TOKEN NOT MATCHED.
						</div>
						<script>
							alert('인증토큰값이 일치하지 않습니다.');
							self.close();
						</script>
					</c:when>
					<c:when test="${snsError == 'already_joined_member'}">
						<div class="alert alert-error">
							ALREADY REGISTERED MEMBER.
						</div>
						<script>
							if( confirm('이미 가입된 회원입니다.\n\n로그인화면으로 이동하시겠습니까?') ) {
								opener.location.href = '${APP_PATH}/member/public/login';
							}
							self.close();
						</script>
					</c:when>
					<c:when test="${snsError == 'not_sns_member'}">
						<div class="alert alert-error">
							NOT SNS MEMBER.
						</div>
						<script>
							if( confirm('가입정보가 없습니다.\n\n회원가입화면으로 이동하시겠습니까?')){
								opener.location.href = '${APP_PATH}/member/public/guide';
							}
							self.close();
						</script>
					</c:when>
				</c:choose>
			</c:if>
		</div>
	</div>
</div>
<div class="navbar navbar-fixed-bottom">
	<span class="muted pull-right">Copyright© <%= new SimpleDateFormat("yyyy").format(new Date()) %> Epart. All Right Reserved.</span>
</div>
</body>
</html>