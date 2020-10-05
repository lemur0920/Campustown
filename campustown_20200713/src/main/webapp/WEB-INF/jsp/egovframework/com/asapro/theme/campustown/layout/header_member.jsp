<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="ko">
<head>
	
	<title>${seo.title2}${currentSite.siteName}</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">	
	<meta name="viewport" content="width=device-width">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="subject" content="">
	<meta name="author" content="">

 	<!-- SEO -->
	<c:import url="/WEB-INF/jsp/egovframework/com/asapro/common/seo.jsp" charEncoding="UTF-8"/>
	<!-- //SEO -->
	
	<!-- css  -->
	<link type="text/css" rel="stylesheet" href="${CONTEXT_PATH }/design/common/css/common.css">
	<link href="${CONTEXT_PATH }/design/common/js/jquery-ui-1.12.1/jquery-ui.min.css" rel="stylesheet" type="text/css" />
	<!-- Sweet Alert -->
	<link href="${CONTEXT_PATH }/assets/plugins/sweet-alert2/sweetalert2.min.css" rel="stylesheet">
	
	<link type="text/css" rel="stylesheet" href="${design.resource}/css/common.css">
	<link type="text/css" rel="stylesheet" href="${design.resource}/css/main.css">
	
	<link type="text/css" rel="stylesheet" href="${design.resource}/css/sub.css">
	<link type="text/css" rel="stylesheet" href="${design.resource}/css/sub1.css">
	<link type="text/css" rel="stylesheet" href="${design.resource}/css/sub2.css">
	<link type="text/css" rel="stylesheet" href="${design.resource}/css/sub3.css">
	<link type="text/css" rel="stylesheet" href="${design.resource}/css/sub4.css">
	<link type="text/css" rel="stylesheet" href="${design.resource}/css/owl.carousel.css">
	
	
	<!-- 서울 GNB 스크립트 -->
	<script src="//www.seoul.go.kr/seoulgnb/gnb.js"></script>
	
	<script src="${CONTEXT_PATH }/design/common/js/jquery.min.js"></script>
	<script src="${CONTEXT_PATH }/design/common/js/common.js"></script>
	<script src="${CONTEXT_PATH }/design/common/js/jquery.cookie.js"></script>
	<script src="${CONTEXT_PATH }/design/common/js/jquery.drags.js"></script>
	<script src="${CONTEXT_PATH }/design/common/js/jquery-ui-1.12.1/jquery-ui.min.js"></script>
	<script src="${CONTEXT_PATH }/design/common/js/jquery-ui-1.12.1/jquery.ui.datepicker-ko.js"></script>
	<script src="${CONTEXT_PATH }/design/common/js/clipboard/clipboard.min.js"></script>
		
	<script src="${design.resource}/js/js.js"></script>
	<script src="${design.resource}/js/bx_slider.js"></script>
	<script src="${design.resource}/js/owl.carousel.js"></script>
	
	<!-- <script src="${CONTEXT_PATH }/design/common/js/printThis.js"></script> -->
	
	<!-- Global -->
	<c:import url="/WEB-INF/jsp/egovframework/com/asapro/common/global.js.jsp" charEncoding="UTF-8" />
	<script src="${CONTEXT_PATH }/design/common/js/recommend.js"></script>
	<!-- Sweet-Alert  -->
	<script src="${CONTEXT_PATH }/assets/plugins/sweet-alert2/sweetalert2.min.js"></script>
	
	<script>
	jQuery(function($){
		<c:if test="${error_msg ne null }">
			swal({
	            type: 'warning',
	            title: '${error_msg }',
	            html : '${error_msg }'

	        });
		</c:if>
		<c:if test="${success_msg ne null }">
			swal({
	            type: 'success',
	            title: '${success_msg }',
	            html : '${success_msg }'

	        });
		</c:if>
	});
	</script>
	
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
    <div class="skip_nav">
		<a href="#content">본문 바로가기</a>
    	<a href="#menu-con">주메뉴 바로가기</a>
    </div>
    
    <!-- seoul common gnb -->
	<!-- <div id="seoul-common-gnb" style="height:48px;background-color:#f8f8f8;"></div> -->
	<div id="seoul-common-gnb"></div>
	
	<!-- header -->
    <div id="header">
		 
		<div class="hb-con clearfix"> 
			<h1 id="logo2"><a href="${APP_PATH}/home"><img src="${design.resource}/images/logo.png" alt="서울캠퍼스타운 로고" /></a></h1> <!-- 기존 logo02.jpg 였음 -->
			
			<!-- tnb (로그인,검색,전체메뉴)-->
			<c:import url="${design.themeDir}/include/tnb.jsp" charEncoding="UTF-8" />
			
			<a href="#n" id="allmenu"></a>
		</div>
		
	</div><!-- header -->
		
	<!-- all menu -->
	<%-- <c:import url="${design.themeDir}/layout/allMenu.jsp" charEncoding="UTF-8" /> --%>
	<!-- all menu end -->
