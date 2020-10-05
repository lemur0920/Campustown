<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

	<!-- satisfaction -->
	<c:import url="${design.themeDir}/include/satisfaction.jsp" charEncoding="UTF-8" />

	<!-- footer -->
	<div id="footer" class="clearfix">
		<a href="#none" id="go-top"><img src="${design.resource }/images/top.png" alt="top" /></a>
		<div class="footer-in">
			<h1 id="footerlogo"><a href="#n"><img src="${design.resource }/images/footerlogo-logo.png" alt="서울캠퍼스타운" /></a></h1>
			<div class="ftc-con">
				<ul class="clearfix">
					<li><a href="${APP_PATH}/content/sct050_010_10">서울캠퍼스타운 소개</a></li>
					<li><a href="http://www.seoul.go.kr/seoul/map.do" target="_blank">찾아오시는길</a></li>
					<li><a href="http://seoul.go.kr/helper/privacy.do?type=www" target="_blank">개인정보처리방침</a></li>
					<li><a href="#n"><img src="${design.resource }/images/footerlogo_icon.png" alt="전화번호" /> 0507-349-3318</a></li>
				</ul>
				<p> 04514 서울특별시 중구 서소문로 124 시티스퀘어빌딩 18층<br />COPYRIGHT © Seoul Metropolitan Gov. ALL RIGHT RESERVED. </p>
			</div>
			<div class="ftr-con">
				<div class="footer_gs">
					<a href="#none"><span>관련기관</span></a>
					<ul class="family_list">
						<li><a href="http://www.seoul.go.kr" target="_blank" title="새창으로">서울특별시</a></li>
						<li><a href="http://www.sba.seoul.kr" target="_blank" title="새창으로">서울산업진흥원</a></li>
						<li><a href="http://seoulstartuphub.com" target="_blank" title="새창으로">서울창업허브</a></li>
						<li><a href="http://www.k-startup.go.kr" target="_blank" title="새창으로">K-스타트업</a></li>
						<li><a href="http://www.bizinfo.go.kr" target="_blank" title="새창으로">기업마당</a></li>
						<li><a href="https://smc.sba.kr/" target="_blank" title="새창으로">서울유통센터</a></li>
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
	window.onload = function() {
	Yjs.Gnb.init('G221', 'seoul-common-gnb');
	};
</script>
<script src="//weblog.eseoul.go.kr/wlo/js/install.js" ></script>

</body>
</html>