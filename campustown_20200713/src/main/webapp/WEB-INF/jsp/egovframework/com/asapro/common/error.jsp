<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="egovframework.com.asapro.support.Constant"%>
<%@ page import="egovframework.com.asapro.support.util.AsaproUtils"%>
<%@ page import="egovframework.com.asapro.config.service.Config"%>
<%@ page import="egovframework.com.asapro.config.service.ConfigService"%>
<%@ page import="egovframework.com.asapro.support.ApplicationContextProvider"%>
<%@ page import="egovframework.com.asapro.site.service.Site"%>
<%@ page import="egovframework.com.asapro.site.service.SiteService"%>
<%@ page import="org.springframework.context.ApplicationContext"%>
<%@ page import="java.util.Date"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"  %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
out.clear();
out.flush();
/*
site config 테이블의 display_error_detail 값이 false 이면 사용자단 에러는 표시되지 않습니다.
디버깅할때는 true 로 설정해 놓고 작업하세요.
*/
ApplicationContext applicationContext = ApplicationContextProvider.getApplicationContext();
ConfigService configService = applicationContext.getBean(ConfigService.class);
SiteService siteService = applicationContext.getBean(SiteService.class);

Site currentSite = siteService.detectCurrentSite(request);
request.setAttribute("currentSite", currentSite);
Config siteConfig = configService.getConfig("global"); 
String requestUri = AsaproUtils.getFixedRequestUri(request);

%>
<!doctype html>
<html lang="ko">
<head>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width,initial-scale=1.0,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no">
<title>ERROR</title>

<link href="/assets/plugins/sweet-alert2/sweetalert2.min.css" rel="stylesheet" type="text/css">
<link href="/assets/css/bootstrap.min.css" rel="stylesheet" type="text/css">
<!-- <link href="/assets/css/style.css" rel="stylesheet" type="text/css"> -->

<!-- Custom CSS -->

<script src="/assets/js/jquery.min.js"></script>

<!-- Sweet-Alert  -->
<script src="/assets/plugins/sweet-alert2/sweetalert2.min.js"></script>

<!-- Minified version of `es6-promise-auto` below. -->
<script src="/assets/js/es6-promise.min.js"></script>
<script src="/assets/js/es6-promise.auto.min.js"></script> 

<!-- css 메인페이지용-->
<link type="text/css" rel="stylesheet" href="/design/theme/campustown/css/common.css">

<!-- css 서브페이지용 -->
<link type="text/css" rel="stylesheet" href="/design/theme/campustown/css/sub.css">	

<style type="text/css">
body{padding-top: 20px;}
.status-code {
	height: 15em;
	text-align: center;
}
.status-code h1{
	font-size: 15em;
	line-height: 1em;
	color: #F0F0F0;
	text-shadow: 1px 1px 6px #AAA;
}
</style>
</head>

<c:choose>
	<c:when test="${fn:substring(pageContext.exception['class'].simpleName, 0, 6) eq 'Asapro'}">
	
		<script>
		
		jQuery(function($){
				<c:if test="${not empty pageContext.exception.locationHref}">
				swal({
			        //type: 'success',
			        title: '<%= (String)request.getAttribute("javax.servlet.error.message") %>',
			    }).then(function(){
					<c:if test="${pageContext.exception.locationHref == 'back'}">
						history.back();
					</c:if>
					<c:if test="${pageContext.exception.locationHref != 'back'}">
						location.href = '${pageContext.exception.locationHref}';
					</c:if>
			    });
				</c:if>
		});
		</script>
	</c:when>
	<c:otherwise>
		<body>
			<div class="row-fluid">
				<div class="span6 offset4" >
					<%--<form action="<%= Constant.APP_PATH %>/<%= Constant.MAIN_SITE_DISPLAY_ID %>/archive/search" method="get" class="form-search">
						<div>
							<label>${currentSite.siteName}</label>
						</div>
						<div>
							<input type="text" name="sv" value="" />
							<button type="submit" class="btn btn-mini">Search</button>
							or <a href="/" class="btn btn-mini">Go to index</a>
						</div>
					</form> --%>
				</div>
			</div>
			<% if( "true".equals(siteConfig.getOption("display_error_detail")) ){ %>
				<%-- 에러내용을 상세하게 표시한다. --%>
				<div id="wrap">
					<div class="error-wrap">
						<div class="alert alert-error">
							ERROR INFORMATION (AsaproX)
							
						</div>
						<table class="table table-condensed">
							<tr>
								<th style="width: 130px;">ERROR</th>
								<td style="word-break: break-all;">${pageContext.exception}</td>
							</tr>
							<tr>
								<th>MESSAGE</th>
								<td>${pageContext.exception.message}<%= (String)request.getAttribute("javax.servlet.error.message") %></td>
							</tr>
							<tr>
								<th>REQUEST_URI</th>
								<td>${pageContext.errorData.requestURI}</td>
							</tr>
							<tr>
								<th>STATUS_CODE</th>
								<td>${pageContext.errorData.statusCode}</td>
							</tr>
							<tr>
								<th>STACKTRACE</th>
								<td>
									<c:forEach var="st" items="${pageContext.errorData.throwable.stackTrace}">
										${st}<br />
									</c:forEach>
								</td>
							</tr>
						</table>
					</div>
				</div>
				
			<% } else { %>
				<%-- 에러내용을 간단하게 표시한다. --%>
				<%-- <c:import url="/WEB-INF/jsp/egovframework/com/asapro/common/customizedError.jsp" charEncoding="UTF-8" /> --%>
				<%-- 
				<div class="status-code">
					<h1>${pageContext.errorData.statusCode}</h1>
					${pageContext.errorData.statusCode} - ${pageContext.errorData.requestURI}
					<% if( request.getAttribute("javax.servlet.error.message") != null ) { %>
						<p>
							<%= (String)request.getAttribute("javax.servlet.error.message") %>
						</p>
					<% } %>
				</div>
				 --%>
			 	<!-- wrap -->
				<div id="wrap">
					<div class="error-wrap">
						<div class="error-head">
							<span><img src="/design/theme/campustown/images/logo.png" alt="" /></span>
						</div>
						<p class="ep-slogan">창업준비에 필요한 법률, 세무, 정책, 창업자금지원까지 캠퍼스타운이 성공적인 창업의 동반자가 되겠습니다.</p>
						<div class="ep-img-out"><img src="/design/theme/campustown/images/sub/error_txt.gif" alt="" /></div>
						<p class="error-explain">링크가 잘못되어 있군요!</p>
						<div class="ep-btns">
							<a href="/" class="ep-btn">서울캠퍼스타운 바로가기 &gt;&gt;</a>
						</div>
					</div>
				</div>
			<% } %>
				
		<%-- 
		<div class="navbar navbar-fixed-bottom">
			<span class="muted pull-right">Copyright© <%= new SimpleDateFormat("yyyy").format(new Date()) %> Asadal. All Right Reserved.</span>
		</div>
		 --%>
		</body>
	</c:otherwise>
</c:choose>



</html>