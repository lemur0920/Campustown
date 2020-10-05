<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="asapro" uri="http://www.asadal.com" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!-- 컨텐츠 팝업 -->
<div id="popup_con" style="display:none;">
	<div class="popup" tabindex="0">
		<span class="pop_tit nsq">팝업 컨텐츠 타이틀</span>
		<%--
		<ul class="sns clearfix">
			<li><a href="" class="fb" target="_blank" title="새 창으로 열림">페이스북</a></li>
			<li><a href="" class="cs" target="_blank" title="새 창으로 열림">카카오스토리</a></li> 
			<li><a href="" class="twt" target="_blank" title="새 창으로 열림">트위터</a></li>
			<li><a href="" class="nb" target="_blank" title="새 창으로 열림">네이버블로그</a></li> 
		</ul>
		--%>
		<!-- 팝업 상세 -->
		<div class="con">
			팝업 상세 내용이 없습니다.
		</div>
		<!-- 팝업 상세 end  -->
		<button type="button" class="btn btn_close"><!--닫기 눌렀을 때 팝업창을 띄울 때 누른 button 이나 a 링크에 class="cfocus" 주세요. (접근성)-->닫기</button>
	</div>
</div>