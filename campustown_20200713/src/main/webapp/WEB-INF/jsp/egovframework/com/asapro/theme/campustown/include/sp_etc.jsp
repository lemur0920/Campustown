<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="egovframework.com.asapro.support.util.AsaproUtils"%>
<%@ taglib prefix="asapro" uri="http://www.asadal.com" %>
	

<script>

jQuery(function($){
	
	//프린트
	$('.prt').on('click', function(e){
		e.preventDefault();
		//window.print();
		
		$('#container').printThis({
			//canvas: false,  
			//importStyle: true, 
			importCSS: true,
			//loadCSS: "${design.resource }/css/print.css"
		});
		
	});
	
	//url클립보드 복사
	$('.urlClipboard').on('click', function(e){
		e.preventDefault();
		
		var clipboard = new ClipboardJS('.urlClipboard');

		clipboard.on('success', function(e) {
		    /* console.info('Action:', e.action);
		    console.info('Text:', e.text);
		    console.info('Trigger:', e.trigger); */
		    alert("링크 복사가 완료되었습니다.")
		    e.clearSelection();
		});

		clipboard.on('error', function(e) {
		    /* console.error('Action:', e.action);
		    console.error('Trigger:', e.trigger); */
		    alert("Ctrl + C 를 누르면 복사가 완료됩니다.")
		});
	});
});


</script>

<div class="button">
	<%-- <div class="text_pm">
		<a class="lf" href="#n" onclick="plus()"><img src="${design.resource}/images/sub/tp.jpg" alt="클자키우기" /></a>
		글자크기
		<a class="lf" href="#n" onclick="minus()"><img src="${design.resource}/images/sub/tm.jpg" alt="클자줄이기" /></a>
		<!-- <span><a href="#" onclick="reset()">복구</a></span> -->
	</div> --%>
	
	<!-- sns 공유 -->
	<ul class="clearfix">
		<jsp:include page="${design.themeDir}/include/sns-button-list.jsp" />
		<%-- <a href="#n" class="urlClipboard" data-clipboard-text="<%=AsaproUtils.getCurrentUrl(request, true) %>"><img src="${design.resource}/images/sub/url.png" alt="url"/></a> --%>
		<li><a href="#none" class="prt"><img src="${design.resource }/images/sub/print.gif" alt="인쇄" /></a></li>
	</ul>
</div>

