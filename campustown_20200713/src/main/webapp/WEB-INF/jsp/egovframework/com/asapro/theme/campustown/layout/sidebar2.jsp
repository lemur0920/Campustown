<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="egovframework.com.asapro.config.service.Config"%>
<%@ page import="egovframework.com.asapro.support.util.AsaproUtils"%>
<%@ page import="egovframework.com.asapro.menu.service.Menu"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="asapro" uri="http://www.asadal.com" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<script>
jQuery(function($){
	$('#content').on('click', '.aMenu', function(e){
		e.preventDefault();
		//alert("click~!!!(창업팀)");
		location.href = $(this).attr("href") + '?compId=${startHpMngrSearch.compId}';
	});	
});
</script>

<%-- <form:form id="startupForm" modelAttribute="startHpMngrSearch" method="get" action="">
	<form:hidden path="compId" />
</form:form> --%>
	


<!-- sidebar 시작 -->

	<!-- sub -->
    <div id="content"> <!--  No End Tag! (연결 페이지에 있음!) -->
		
		<!-- sub top visual -->
		<div class="founding-team-top ">
			<div class="founding-team-tit">
				<h2>${startComp.compNm } - ${startComp.univNm }</h2>
				<ul class="clearfix">
					<li class="photo">
						<!--0310s-->
							<div class="slider-sub01-out">
								<ul class="slider-sub01">
									<li class="slide">
										<c:if test="${not empty startComp.file1Info && startComp.file1Info.fileId > 0 }">
											<img src="${startComp.file1Info.fileServletUrl }" alt="${startComp.file1AltText}"
												onerror="this.src='${design.resource}/images/sub/imhtext-list_img02.jpg'">
										</c:if>
										<c:if test="${empty startComp.file1Info}">
											<img src="${design.resource}/images/sub/imhtext-list_img02.jpg" alt="대체 이미지">
										</c:if>
									</li>
									<li class="slide">
										<c:if test="${not empty startComp.file2Info && startComp.file2Info.fileId > 0 }">
											<img src="${startComp.file2Info.fileServletUrl }" alt="${startComp.file2Info.fileAltText }"
											     onerror="this.src='${design.resource}/images/sub/imhtext-list_img02.jpg'">
										</c:if>     
										<c:if test="${empty startComp.file2Info}">
											<img src="${design.resource}/images/sub/imhtext-list_img02.jpg" alt="대체 이미지">
										</c:if>     
										
									</li>
								</ul>
							</div>	
						<!--0310e-->
					</li>
					
					
					<li class="yellow">
						<h3>${startComp.compNm } 한마디</h3>
						<p class="txt">
							<c:set var="ment" value="${asapro:nl2br(startComp.ment, false)}" />
							<c:choose>
								<c:when test="${fn:length(ment) > 129}">
									<c:out value="${fn:substring(ment,0, 130)}" escapeXml="false"/>...
	   							</c:when>
								<c:otherwise>
									<c:out value="${ment}" escapeXml="false"/>
								</c:otherwise>
							</c:choose>
						</p>
					</li>
					<!--0317s-->
					<li class="black style2">
						<h3>SNS</h3>
						<div class="txt list2 bottom-line clearfix">
							<%-- 
							<dl>
								<dt>투자액</dt>
								<dd><span class="orange-font">
								<fmt:formatNumber type="number" maxFractionDigits="3" value="${startComp.invtAmt }" />
								</span> 만원</dd>
							</dl>
							<dl>
								<dt>고용인수</dt>
								<dd><span class="orange-font">${startComp.emplyCnt }</span> 명</dd>
							</dl>
							<dl>
								<dt>매출액</dt>
								<dd><span class="orange-font">
								<fmt:formatNumber type="number" maxFractionDigits="3" value="${startComp.saleAmt }" />
								</span> 만원</dd>
							</dl>
							<dl>
								<dt>지적재산</dt>
								<dd><span class="orange-font">${startComp.intellProp }</span> 건</dd>
							</dl>
							 --%>
							 <div class="sns-box01">
								<ul>
									<c:if test='${not empty startComp.snsFace}'>
										<li>
											<a href="${startComp.snsFace }" class="b1" target="_blank">페이스북</a>
										<%-- 
										<c:if test='${empty startComp.snsFace}'>
											<a href="#" class="b1">페이스북</a>
										</c:if>
										 --%>
										</li>
									</c:if>
									
									<c:if test='${not empty startComp.snsTwit}'>
										<li>
											<a href="${startComp.snsTwit }" class="b2" target="_blank">트위터</a>
											<%-- 
											<c:if test='${empty startComp.snsTwit}'>
												<a href="#" class="b2">트위터</a>
											</c:if>
											 --%>
										</li>
									</c:if>
									
									<c:if test='${not empty startComp.snsBlog}'>
										<li>
											<a href="${startComp.snsBlog }" class="b3" target="_blank">블로그</a>
											<%-- 
											<c:if test='${empty startComp.snsBlog}'>
												<a href="#" class="b3">블로그</a>
											</c:if>
											 --%>
										</li>
									</c:if>
									
									<c:if test='${not empty startComp.snsHp}'>
										<li>
											<a href="${startComp.snsHp }" class="b4" target="_blank">홈페이지</a>
											<%-- 
											<c:if test='${empty startComp.snsHp}'>
												<a href="#" class="b4">홈페이지</a>
											</c:if>
											 --%>
										</li>
									</c:if>
									
									<c:if test='${not empty startComp.snsInsta}'>
										<li>
											<a href="${startComp.snsInsta }" class="b5" target="_blank">인스타</a>
										</li>
									</c:if>
									
									<c:if test='${not empty startComp.snsKakao}'>
										<li>
											<a href="${startComp.snsKakao }" class="b6" target="_blank">카카오</a>
										</li>
									</c:if>
								</ul>
							</div>
						</div>
						
						
						<h3 class="mb20">${month}월 활동랭킹 <span>${startComp2.rn }</span>위 (<span>${startComp2.score }</span>점)</h3>
						<div class="txt list2 clearfix">
							<dl>
								<dt>글작성수</dt>
								<dd><span class="orange-font">${startComp2.baCnt }</span> 건</dd>
							</dl>
							<dl>
								<dt>댓글수</dt>
								<dd><span class="orange-font">${startComp2.cmtCnt }</span> 건</dd>
							</dl>
						</div>
					</li>
					<!--0317e-->
				</ul>
				<div class="btn-box clearfix">
					<div class="left">
						<!--<span>관심 분야 : </span>-->
						<a href="#startup-1" class="btn">회사소개</a>
						<a href="#startup-2" class="btn">주요서비스 및 홍보</a>
						<a href="#startup-3" class="btn">창업활동정보</a>
						<a href="#startup-4" class="btn">임직원현황</a>
					</div>
					<div class="right">Update : 
						<fmt:parseDate value="${startComp.updDt }" var="parseDate1" pattern="yyyyMMddHHmmss" scope="page"/>
						<fmt:formatDate value="${parseDate1}" pattern="yyyy.MM.dd"/>
					</div>
				</div>	
			</div>
		</div>				
