<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="egovframework.com.asapro.support.util.AsaproUtils"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="asapro" uri="http://www.asadal.com" %>
<script src="//developers.kakao.com/sdk/js/kakao.min.js"></script>

<script>
<%--   
window.kakaoAsyncInit = function () {
  Kakao.Story.createShareButton({
    container: '#kakaostory-share-button'
  });
};

(function (d, s, id) {
	var js, fjs = d.getElementsByTagName(s)[0];
	if (d.getElementById(id)) return;
	js = d.createElement(s); js.id = id;
	js.src = "//developers.kakao.com/sdk/js/kakao.story.min.js";
	fjs.parentNode.insertBefore(js, fjs);
}(document, 'script', 'kakao-js-sdk'));

function kakao_share(){
	if( GLOBAL.MOBILE ){
		Kakao.Story.open({
			url: '<%= AsaproUtils.getCurrentUrl(request, true) %>',
			text: ''
		});
	} else {
		Kakao.Story.share({
			url: '<%= AsaproUtils.getCurrentUrl(request, true) %>',
			text: ''
		});
	}
}
--%>

/* ======= 페이스북, 트위터, 카톡 연동 ======= */
	jQuery(function(){
		 $('#facebookLink').click(function(e){
			e.preventDefault();
			window.open('//www.facebook.com/sharer/sharer.php?u=<%= AsaproUtils.getCurrentUrl(request, true, true) %>', 'share_popup', 'width=600,height=400');
			return false;
		 });
	
		  $('#twLink').click(function(e){
			e.preventDefault();
			window.open('//twitter.com/home?status=${seo.title} <%= AsaproUtils.getCurrentUrl(request, true, true) %>', 'share_popup', 'width=600,height=400');
			return false;
		 });
	});

	//사용할 앱의 JavaScript 키를 설정
	Kakao.init('f3eb11b3237e6cb130321fac8a4841e8');
	//// 카카오링크 버튼을 생성합니다. 처음 한번만 호출하면 됩니다.
	function sendLink() {
	    Kakao.Link.sendDefault({
	    	 objectType: 'feed',
	         content: {
	           title: '${currentMenu.menuName}',
	           description: '${seo.description}',
	           imageUrl: '<%= AsaproUtils.getSchemeDomainPort(request) %>/${design.resource}/images/logo-200x200.jpg',
	           link: {
	        		mobileWebUrl: '<%= AsaproUtils.getCurrentUrl(request, true) %>',
	        		webUrl: '<%= AsaproUtils.getCurrentUrl(request, true) %>'
	          		<%-- androidExecParams: 'test', --%>
	           }
	         }
	       });
	     }

</script>

	<li><a class="btn twt" href="#" id="twLink" target="_blank" title="새 창으로 열림"><img src="${design.resource }/images/sub/twitter.gif" alt="트위터" /></a></li>
	<li><a class="btn fb" href="#" id="facebookLink" target="_blank" title="새 창으로 열림"><img src="${design.resource }/images/sub/facebook.gif" alt="페이스북" /></a></li>
	<li><a class="btn ct" href="javascript:sendLink()" id="kakao-link-btn" title="새 창으로 열림"><img src="${design.resource }/images/sub/kakao.gif" alt="카카오톡" /></a></li>
	<%--	
	<li><a href="#kakao-share" title="새 창으로 열림" id="kakao-share" onclick="kakao_share();return false;"><img src="https://developers.kakao.com/sdk/js/resources/story/icon_small.png" alt="새 창으로 열림" /></a></li>
	<li><a href="http://www.band.us/plugin/share?body=${asapro:urlEncode(seo.title, 'UTF-8')}&route=<%= request.getServerName() %>" onclick="window.open(this.href, 'share_popup', 'width=410,height=540');return false;" title="네이버 밴드"><img src="/design/theme/sccf/images/sub/sub_sns04.png" alt="네이버 밴드" /></a></li>
	<li><a href="#print" title="인쇄(출력)" onclick="print();return false;"><img src="/design/theme/sccf/images/sub/sub_sns05.png" alt="인쇄(출력)" /></a></li>
	 --%>
