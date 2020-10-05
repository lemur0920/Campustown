<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.util.*"%>
<%@ page import="egovframework.com.asapro.support.util.AsaproUtils"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

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
	<script src="${CONTEXT_PATH }/design/common/js/printThis.js"></script>
		
	<script src="${design.resource}/js/js.js"></script>
	<script src="${design.resource}/js/bx_slider.js"></script>
	<script src="${design.resource}/js/owl.carousel.js"></script>
	
	
	<!-- Global -->
	<c:import url="/WEB-INF/jsp/egovframework/com/asapro/common/global.js.jsp" charEncoding="UTF-8" />
	<script src="${CONTEXT_PATH }/design/common/js/recommend.js"></script>
	<!-- Sweet-Alert  -->
	<script src="${CONTEXT_PATH }/assets/plugins/sweet-alert2/sweetalert2.min.js"></script>

	<!-- Global site tag (gtag.js) - Google Analytics -->
	<script async="" src="https://www.googletagmanager.com/gtag/js?id=UA-140403220-2"></script>
	<script>
		window.dataLayer = window.dataLayer || [];
		function gtag(){dataLayer.push(arguments);}
		gtag('js', new Date());

		gtag('config', 'UA-140403220-2');
	</script>

	<!-- Channel Plugin Scripts -->
	<script>
	(function() {
	var w = window;
	if (w.ChannelIO) {
	return (window.console.error || window.console.log || function(){})('ChannelIO script included twice.');
	}
	var d = window.document;
	var ch = function() {
	ch.c(arguments);
	};
	ch.q = [];
	ch.c = function(args) {
	ch.q.push(args);
	};
	w.ChannelIO = ch;
	function l() {
	if (w.ChannelIOInitialized) {
	return;
	}
	w.ChannelIOInitialized = true;
	var s = document.createElement('script');
	s.type = 'text/javascript';
	s.async = true;
	s.src = 'https://cdn.channel.io/plugin/ch-plugin-web.js';
	s.charset = 'UTF-8';
	var x = document.getElementsByTagName('script')[0];
	x.parentNode.insertBefore(s, x);
	}
	if (document.readyState === 'complete') {
	l();
	} else if (window.attachEvent) {
	window.attachEvent('onload', l);
	} else {
	window.addEventListener('DOMContentLoaded', l, false);
	window.addEventListener('load', l, false);
	}
	})();
	ChannelIO('boot', {
	"pluginKey": "d96ad7d7-34ca-4d92-bb48-ac17547c0b50"
	});
	</script>
	<!-- End Channel Plugin -->
	
	<script>
	$(function(){
		//로그인 상태 체크
		var isLogin = false;
		var loginId = '${sessionScope.loginUserId}';
		if(loginId != null && loginId != '' ){
			isLogin = true;
		}

		$('.new-btn').on('click', function(e){
			//e.preventDefault();
			if(!isLogin){
				alert('로그인 후 작성해 주세요.');
				return false;
			}
		});
		
		
		//쪽지쓰기 레이어
		$('body').on('click', '.noteWrite', function(e){
			e.preventDefault();
			if(!isLogin){
				alert('로그인 후 작성해 주세요.');
				return false;
			}
			$(".popup-box").show(); 
			$('.popup-box').load($(this).attr('href'));
			
		});
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
		 
		<%-- <div class="hc-con">
			<div class="area">
				<a href="#n" class="text-con">
					<div>
						<img class="pc" src="${design.resource}/images/hc-con-text2.png" alt="서울캠퍼스 타운은 서울시/지자체와 함께 창업자에게 최적화 된 다양한 창업 지원 서비스를 제공합니다" />
					</div>
					<p>자세히보기</p>
					<span></span>
				</a>
				<a href="#n" class="close">팝업배너닫기</a>
				<div><input type="checkbox" id="hcc" /> <label for="hcc">오늘 하루 열지 않음</label></div>
			</div>
		</div> --%>
		
		<div class="hb-con clearfix"> 
			<h1 id="logo2"><a href="${APP_PATH}/home"><img src="${design.resource}/images/logo.png" alt="서울캠퍼스타운 로고" /></a></h1> <!-- 기존 logo02.jpg 였음 -->
			
			<!-- tnb (로그인,검색,전체메뉴)-->
			<c:import url="${design.themeDir}/include/tnb.jsp" charEncoding="UTF-8" />
			
			
			<!-- gnb start -->			
			<div class="menu-bg"></div>
			<div id="menu-con">
				
				<!-- 모바일용 -->
				<div class="button-box">
					<ul>
						<c:if test="${empty sessionScope.loginUserId}">
							<li><a href="${APP_PATH}/login?returnUrl=<%= AsaproUtils.getCurrentUrl(request, true, true) %>" class="login-btn">로그인</a></li>
						</c:if>
						<c:if test="${not empty sessionScope.loginUserId}">
							<li><a href="${APP_PATH}/logout?returnUrl=<%= AsaproUtils.getCurrentUrl(request, true) %>" class="login-btn">로그아웃</a></li>
							<li><a href="${APP_PATH}/member/mypage/index" class="mypage-btn">마이페이지</a></li>
						</c:if>
						<li><a href="/site/eng/home" class="language-bg">English</a></li>
					</ul>
				</div>
				
				<c:import url="${design.themeDir}/layout/gnb.jsp" charEncoding="UTF-8" />
			</div>
			<!-- gnb end -->
			
			<a href="#n" id="allmenu"></a>
		</div>
		
	</div><!-- header -->
		
	<!-- all menu -->
	<%-- <c:import url="${design.themeDir}/layout/allMenu.jsp" charEncoding="UTF-8" /> --%>
	<!-- all menu end -->

