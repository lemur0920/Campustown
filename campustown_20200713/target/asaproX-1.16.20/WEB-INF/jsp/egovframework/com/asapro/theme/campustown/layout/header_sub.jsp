<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="ko">
<head>
	
	<title>${seo.title2} | ${currentSite.siteName}</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">	
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width">
	<meta name="subject" content="">
	<meta name="keywords" content="">
	<meta name="author" content="">
	<meta name="copyright" content="copyrights Seoul Metropolitan Govement all rights reserved">

	<!-- SEO -->
	<c:import url="/WEB-INF/jsp/egovframework/com/asapro/common/seo.jsp" charEncoding="UTF-8"/>
	<!-- //SEO -->
	
	<!-- css  -->
	<link type="text/css" rel="stylesheet" href="${CONTEXT_PATH }/design/common/css/common.css">
	<link type="text/css" rel="stylesheet" href="${design.resource}/css/common.css">
	<!-- css 서브페이지용 -->
	<link type="text/css" rel="stylesheet" href="${design.resource}/css/sub.css">
	<link type="text/css" rel="stylesheet" href="${design.resource}/css/respon.css">
	<!-- 공통콘텐츠 팝업용 -->
	<link type="text/css" rel="stylesheet" href="${design.resource}/css/pop.css">
	
	
	<!-- js  -->
	<link href="${CONTEXT_PATH }/design/common/js/jquery-ui-1.12.1/jquery-ui.min.css" rel="stylesheet" type="text/css" />
	<%--<link rel="stylesheet" type="text/css" href="${design.resource}/css/print.css" media="print" /> --%>
	
	<!-- 서울 GNB 스크립트 -->
	<script src="//www.seoul.go.kr/seoulgnb/gnb.js"></script>
	
	<script src="${CONTEXT_PATH }/design/common/js/jquery.min.js"></script>
	<script src="${CONTEXT_PATH }/design/common/js/common.js"></script>
	
	<%--<script src="${design.resource}/js/SmoothScroll.js"></script> --%>
	<script src="${design.resource}/js/jquery.rwdImageMaps.min.js"></script>
	<script src="${design.resource}/js/common.js"></script>
	<!-- 개발 js 소스 파일 -->
	<script src="${design.resource}/js/d_js.js"></script>
	<!-- js 서브페이지용 -->
	<script src="${design.resource}/js/jquery.flexslider-min.js"></script>
	<script src="${design.resource}/js/sub.js"></script>
	<script src="${CONTEXT_PATH }/design/common/js/printThis.js"></script>
	<!-- Global -->
	<c:import url="/WEB-INF/jsp/egovframework/com/asapro/common/global.js.jsp" charEncoding="UTF-8" />
	
	<script>
	jQuery(function($){
		
	});
	
	</script>
	
</head>
<body>
<noscript>자바스크립트를 지원하지 않는 브라우저에서는 일부 기능을 사용하실 수 없습니다.</noscript>
<!-- wrap -->
<div id="wrap">
	
	<!-- skip navigation -->
    <div class="skipnav">
		<a href="#content_wrap">본문 바로가기</a>
    	<a href="#gnb">주메뉴 바로가기</a>
    </div>
    
    <!-- seoul common gnb -->
	<div id="seoul-common-gnb" style="height:48px;background-color:#f8f8f8;">

	</div>
	
	<!-- header -->
    <div id="hd">
    	<span class="bg_gnb"></span>
		<div class="container">
			<h1 class="logo"><a href="${APP_PATH}/home"><img src="${design.resource}/images/common/logo.gif" alt="서울청년포털"></a></h1>
			
			<!-- gnb start -->
			<div class="gnb_wrap">
				<div id="gnb">
					<c:import url="${design.themeDir}/layout/gnb.jsp" charEncoding="UTF-8" />
				</div>
			</div>
			<!-- gnb end -->
			
			<!-- tnb (로그인,검색,전체메뉴)-->
			<c:import url="${design.themeDir}/include/tnb.jsp" charEncoding="UTF-8" />
		</div>
		
		<!-- all menu -->
		<c:import url="${design.themeDir}/layout/allMenu.jsp" charEncoding="UTF-8" />
		<!-- all menu end -->
    </div>
