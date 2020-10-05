<%@page import="java.util.Calendar"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!doctype html>
<html lang="ko">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">
    
	<title>Administrator Login</title>
	
	<!--Morris Chart CSS -->
	<link rel="stylesheet" href="${CONTEXT_PATH }/assets/plugins/morris/morris.css">
	
	<link href="${CONTEXT_PATH }/assets/css/bootstrap.min.css" rel="stylesheet" type="text/css">
	<link href="${CONTEXT_PATH }/assets/css/icons.css" rel="stylesheet" type="text/css">
	<link href="${CONTEXT_PATH }/assets/css/style.css" rel="stylesheet" type="text/css">
	
	<!-- jQuery  -->
	<script src="${CONTEXT_PATH }/assets/js/jquery.min.js"></script>
	<script src="${CONTEXT_PATH }/assets/js/popper.min.js"></script>
	<script src="${CONTEXT_PATH }/assets/js/bootstrap.min.js"></script>
	<script src="${CONTEXT_PATH }/assets/js/modernizr.min.js"></script>
	<script src="${CONTEXT_PATH }/assets/js/detect.js"></script>
	<script src="${CONTEXT_PATH }/assets/js/fastclick.js"></script>
	<script src="${CONTEXT_PATH }/assets/js/jquery.slimscroll.js"></script>
	<script src="${CONTEXT_PATH }/assets/js/jquery.blockUI.js"></script>
	<script src="${CONTEXT_PATH }/assets/js/waves.js"></script>
	<script src="${CONTEXT_PATH }/assets/js/jquery.nicescroll.js"></script>
	<script src="${CONTEXT_PATH }/assets/js/jquery.scrollTo.min.js"></script>
	
	<!-- App js -->
	<script src="${CONTEXT_PATH }/assets/js/app.js"></script>
	
</head>
<body>
	<!-- Begin page -->
	<div class="accountbg"></div>
	<div class="wrapper-page cms-login">

		<div class="card">
			<div class="card-body">

				<h3 class="text-center mt-0 m-b-15">
					<!-- 업체에 맞게 바꿔주세요 
					<a href="#" class="logo logo-admin"><img src="${CONTEXT_PATH }/assets/images/logo.png" height="30" alt="logo"></a>-->
					<a href="#" class="logo logo-admin"><img src="${currentSite.siteLogo.fileServletUrl}" onerror="this.src='${CONTEXT_PATH }/assets/images/logo.png'"  alt="logo"></a>
				</h3>

				<h4 class="text-muted text-center font-18"><b>CMS 로그인 페이지입니다.</b></h4>

				<div class="p-3">
					<!-- 로그인 처러 모듈 완성후 수정 -->
					<form:form modelAttribute="loginForm" class="form-horizontal m-t-20" action="${ADMIN_PATH}/login.do"  method="post">
					<!--<form class="form-horizontal m-t-20" action="${ADMIN_PATH}/dashboard.do" method="post">-->

						<div class="form-group row">
							<div class="col-12">
								<form:input path="loginId" cssClass="form-control" cssErrorClass="form-control is-invalid" required="" placeholder="아이디" autocomplete="off"/>
								<form:errors path="loginId" element="div" cssClass="text-danger" />
							</div>
						</div>

						<div class="form-group row">
							<div class="col-12">
								<form:password path="loginPassword" cssClass="form-control" cssErrorClass="form-control is-invalid" required="" placeholder="비밀번호" autocomplete="off"/>
								<form:errors path="loginPassword" element="div" htmlEscape="false" cssClass="text-danger" />
							</div>
						</div>

						<div class="form-group row">
							<div class="col-12">
								<div class="custom-control custom-checkbox">
									<input type="checkbox" class="custom-control-input" id="customCheck1">
									<label class="custom-control-label" for="customCheck1">아이디 저장</label>
								</div>
							</div>
						</div>

						<div class="form-group text-center row m-t-20">
							<div class="col-12">
								<button class="btn btn-info btn-block waves-effect waves-light" type="submit">로그인</button>
							</div>
						</div>

						<!-- 
						<div class="form-group m-t-10 mb-0 row">
							<div class="col-sm-7 m-t-20">
								<a href="pages-recoverpw.html" class="text-muted"><i class="mdi mdi-lock    "></i> Forgot your password?</a>
							</div>
							<div class="col-sm-5 m-t-20">
								<a href="pages-register.html" class="text-muted"><i class="mdi mdi-account-circle"></i> Create an account</a>
							</div>
						</div>
						 -->
					</form:form>
				</div>

			</div>
		</div>
	</div>

</body>
</html>