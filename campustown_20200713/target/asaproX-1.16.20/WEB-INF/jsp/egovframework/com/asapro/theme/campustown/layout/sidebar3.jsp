<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="egovframework.com.asapro.config.service.Config"%>
<%@ page import="egovframework.com.asapro.support.util.AsaproUtils"%>
<%@ page import="egovframework.com.asapro.menu.service.Menu"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="asapro" uri="http://www.asadal.com" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>

<script>
jQuery(function($){
	
	$('#content').on('click', '.aMenu', function(e){
		e.preventDefault();
		//alert("click~!!");
		location.href = $(this).attr("href") + '?univId=${univInfo.univId }'; 
	});
	
});
</script>

<!-- sidebar 시작 -->

	<!-- sub -->
    <div id="content"> <!--  No End Tag! (연결 페이지에 있음!) -->
		
		<!-- sub top visual -->
		<div id="subl-top">
			<div class="subl-top">
				<h2>${univInfo.univNm }</h2>
				<ul class="clearfix">
					<li class="photo">
						<c:if test="${not empty univInfo.file1Info && univInfo.file1Info.fileId > 0 }">
							<img src="${univInfo.file1Info.fileServletUrl }" alt="${univInfo.file1AltText}"
								onerror="this.src='${design.resource}/images/main/wt_img04.jpg'">
						</c:if>
						<c:if test="${empty univInfo.file1Info}">
							<img src="${design.resource}/images/main/wt_img04.jpg" alt="대체 이미지">
						</c:if>
						
							
					</li>
					<li class="yellow">
						<h3>${univInfo.univNm } 한마디</h3>
						<ul>
							<li>${asapro:nl2br(univInfo.ment, false)}</li>
						</ul>
					</li>
					<li class="black">
						<h3>창업팀 현황</h3>
						<ul class="txc">
							<li><strong class="orange">${univInfo.compCnt }</strong> 개 창업팀이 활동중입니다.</li>
							<li><a class="a-but bg-grey" href="${APP_PATH}/university/startEmp?univId=${univInfo.univId}">창업팀 현황 보기</a></li>
						</ul>
						<span class="abso-img"><img src="${design.resource }/images/sub/sub01_limg11.png" alt="창업팀 백그라운드 사진"></span>
					</li>
				</ul>
				<p class="txr">Update : 
					<fmt:parseDate value="${univInfo.updDt }" var="parseDate1" pattern="yyyyMMddHHmmss" scope="page"/>
					<fmt:formatDate value="${parseDate1}" pattern="yyyy.MM.dd"/>
				</p>
			</div>
		</div>
