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
		<!-- <li style="border:0;">
			<a href="/site/main/login?returnUrl=https%3A%2F%2Fcampustown.seoul.go.kr%2Fsite%2Fmain%2Fhome"  class="login-bg" >로그인</a>
		</li>
		<li><a href="#n" class="mypage-bg" >마이페이지</a></li> -->
		<li class="" style="border:0;"><a href="/site/main/home" class="language-bg">Korean</a></li>
	</ul>
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