<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="egovframework.com.asapro.support.util.AsaproUtils"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<script>
jQuery(function($){
	$("#totSearch").click(function(){
		$("#pQuery").val($("#pQuery_tmp").val());
		$("#frm").submit();		
	});
});
</script>
<div class="func-a clearfix">
	<ul class="member-link clearfix">
		<c:if test="${empty sessionScope.loginUserId}">
			<li style="border:0;">
				<a class="login-bg" href="${APP_PATH}/login?returnUrl=<%= AsaproUtils.getCurrentUrl(request, true, true) %>">로그인</a>
			</li>
		<!-- <li style="display:none;" ><a href="#n">회원가입</a></li> -->
		</c:if>
		<c:if test="${not empty sessionScope.loginUserId}">
			<li class="" style="border:0;">
				<a class="login-bg" href="${APP_PATH}/logout?returnUrl=<%= AsaproUtils.getCurrentUrl(request, true) %>">로그아웃</a>
			</li>
			<li class="" style="border:0;">
				<a class="mypage-bg" href="${APP_PATH}/member/mypage/index">마이페이지</a>
			</li>
		</c:if>
		<li class="" style="border:0;"><a href="/site/eng/home" class="language-bg">English</a></li>
	</ul>
	<%-- 
	<ul class="func-link clearfix">
		<li><span class="op-all-menu" style="cursor:pointer;"><img src="${design.resource}/images/op_all_menu.gif" alt=""></span></li>
		<li>
			<div class="sch-div">
				<input type="text" class="sch-text" title="검색어를 입력하세요" style="width: 0px; margin-right: 0px;">
				<span><img src="${design.resource}/images/search_all2.gif" alt=""></span>
			</div>
		</li>
		<li><a href="#n"><img src="${design.resource}/images/search_all.gif" alt="" style="border-left: 1px solid #cccccc;" ></a></li>
	</ul>
	 --%>
</div>


<!-- <ul id="tnb" class="clearfix">
	<li class="sch_wrap">
		<div class="sch_form">
			<form id="frm" action="//youth.seoul.go.kr/search/search.s" method="post">
				<input type="hidden" name="pQuery" id="pQuery" >
				<input type="text" name="pQuery_tmp" id="pQuery_tmp" class="sch_open" title="검색어를 입력하세요">
				<button id="totSearch" class="btn sch sch_open">검색하기</button>
			</form>
		</div>
	</li>
	<li><button type="button" class="btn all sch_close"><span>전체 메뉴 열기</span></button></li>
</ul> -->