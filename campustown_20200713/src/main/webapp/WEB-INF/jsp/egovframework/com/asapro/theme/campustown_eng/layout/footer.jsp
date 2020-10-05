<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

	<!-- satisfaction -->
	<c:import url="${design.themeDir}/include/satisfaction.jsp" charEncoding="UTF-8" />

		</div><!-- .area -->
	</div><!-- #content -->
	
	<!-- footer -->
	<div id="footer" class="clearfix">
		<a href="#none" id="go-top"><img src="${design.resource }/images/top.png" alt="top" /></a>
		<div class="footer-in">
			<h1 id="footerlogo"><a href="#n"><img src="${design.resource }/images/footerlogo-logo.png" alt="서울캠퍼스타운" /></a></h1>
			<%-- <div class="ftc-con">
				<ul class="clearfix">
					<li><a href="${APP_PATH}/content/sct050_010_10">서울캠퍼스타운 소개</a></li>
					<li><a href="http://www.seoul.go.kr/seoul/map.do" target="_blank">찾아오시는길</a></li>
					<li><a href="http://seoul.go.kr/helper/privacy.do?type=www" target="_blank">개인정보처리방침</a></li>
					<li><a href="#n"><img src="${design.resource }/images/footerlogo_icon.png" alt="전화번호" /> 02-2133-4826</a></li>
				</ul>
				<p> 04514 서울특별시 중구 서소문로 124 시티스퀘어빌딩 18층<br />COPYRIGHT © Seoul Metropolitan Gov. ALL RIGHT RESERVED. </p>
			</div> --%>
			<div class="ftc-con">
				<ul class="clearfix">
					<li><a href="${APP_PATH}/content/scte010">What is Seoul Campus Town?</a></li>
					<li><a href="#n"><img src="${design.resource }/images/footerlogo_icon.png" alt="전화번호" /> 0507-349-3318</a></li>
				</ul> 
				<p> 04514 18 Floor, City Square building, 124, Seosomun-ro, Jung-gu, Seoul<br />COPYRIGHT © Seoul Metropolitan Gov. ALL RIGHT RESERVED. </p>
			</div>
			<div class="ftr-con">
				<div class="footer_gs">
					<a href="#none"><span>Family Site</span></a>
					<ul class="family_list">
						<li><a href="http://english.seoul.go.kr/?SSid=101_01" target="_blank" title="새창으로">Seoul Metropoilitan Government</a></li>
						<li><a href="http://www.sba.seoul.kr/eng/main/main/index.jsp" target="_blank" title="새창으로">Seoul Business Agency</a></li>
						<li><a href="http://seoulstartuphub.com/eng/" target="_blank" title="새창으로">Seoul Startup Hub </a></li>
						
					</ul>
				</div>
			</div>
		</div>
	</div>
	
</div> <!-- #wrap -->

<script>
		
	// 서울시 접속로그 스크립트
	// GNB 로딩 스크립트
	// Yjs.Gnb.init('사이트코드', 'GNB 표출영역 ID');
	/* window.onload = function() {
	Yjs.Gnb.init('G221', 'seoul-common-gnb');
	}; */
</script>
<!-- <script src="//weblog.eseoul.go.kr/wlo/js/install.js" ></script> -->

</body>
</html>