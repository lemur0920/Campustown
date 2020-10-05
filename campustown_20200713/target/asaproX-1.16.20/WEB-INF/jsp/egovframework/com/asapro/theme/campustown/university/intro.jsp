<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="asapro" uri="http://www.asadal.com" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!-- header -->
<c:import url="${design.header}" charEncoding="UTF-8" />

<script>
jQuery(function($){
	
	/* 	
	var sBtn = $('ul.clearfix > li');
	sBtn.find("a").click(function(){
		//alert("click~!!");
		sBtn.removeClass('on');
		$(this).parent().addClass('on');
	})
 	*/
 	/* 
	$('.aMenu').on('click', function(e){
		e.preventDefault();
		var href= $(this).attr("href");
		alert("href: " + href);
		$("#universityForm").attr("action", $(this).attr("href")).submit();
	});
 	 */
 	 
 	
});
</script>

<!-- sidebar -->
<c:import url="${design.themeDir}/layout/sidebar3.jsp" charEncoding="UTF-8" />
<%-- 
<form:form id="universityForm" modelAttribute="univHpMngrSearch" action="" method="get">
	<form:input path="univId" />
</form:form>
 --%>	
 
	<div id="subl-center">
		<c:import url="${design.themeDir}/layout/tab3depth.jsp" charEncoding="UTF-8" />
		<%-- 
		<div class="subl-tab">
			<ul class="clearfix">
				<li><a href="${APP_PATH}/university/intro?univId=${univInfo.univId}">캠퍼스타운 소개 </a></li>
				<li><a href="#none">시설공간정보</a></li>
				<li><a href="${APP_PATH}/university/startups?univId=${univInfo.univId}">창업팀 현황</a></li>
				<li><a href="#none">새소식</a></li>
				${APP_PATH}/university/news?univId=${univInfo.univId}
				<li><a href="#none">지원프로그램</a></li>
			</ul>
		</div>
		 --%>
		<h3 class="subl-title1">소개</h3>
		<div class="subl-two-table clearfix">
			<div class="subl-table">
				<table>
					<caption>캠퍼스타운소개</caption>
					<colgroup>
						<col style="width:31%;">
						<col style="width:*%;">
					</colgroup>
					<tbody>
						<tr>
							<th scope="row">캠퍼스타운</th>
							<td>${univInfo.univNm }</td>
						</tr>
						<tr>
							<th scope="row">LOGO</th>
							<td class="big-heig">
								<a href="#">
									<img src="${univInfo.file2Info.fileServletUrl }" alt="${univInfo.file2Info.fileAltText }" 
										onerror="this.src='${design.resource}/images/sub/imhtext-list_img02.jpg'"/>
								</a>
							</td>
						</tr>
					</tbody>
				</table>
			</div>
			<div class="subl-table">
				<table>
					<caption>캠퍼스타운소개</caption>
					<colgroup>
						<col style="width:31%;">
						<col style="width:*%;">
					</colgroup>
					<tbody>
						<tr>
							<th scope="row">오시는길</th>
							<td>${asapro:nl2br(univInfo.address, false)}</td>
						</tr>
						<tr>
							<th scope="row">운영 담당자</th>
							<td>${univInfo.chargerNm}</td>
						</tr>
						<tr>
							<th scope="row">email</th>
							<td>${univInfo.email}</td>
						</tr>
						<tr>
							<th scope="row">연락처</th>
							<td>${univInfo.tel}</td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
		<div class="subl-table">
			<table>
				<caption>캠퍼스타운소개</caption>
				<colgroup>
					<col style="width:15%;">
					<col style="width:*%;">
				</colgroup>
				<tbody>
					<tr>
						<th scope="row">Vision</th>
						<td>${univInfo.vsn}</td>
					</tr>
					<tr>
						<th scope="row">소개</th>
						<td>${univInfo.intrcn}</td>
					</tr>
					<tr>
						<th scope="row">홍보</th>
						<td>${univInfo.prMssage}</td>
					</tr>
				</tbody>
			</table>
		</div>
		
		
		<c:if test="${not empty univInfo.snsFace || not empty univInfo.snsTwit || not empty univInfo.snsBlog || not empty univInfo.snsHp || not empty univInfo.snsInsta || not empty univInfo.snsKakao}">
		<h3 class="subl-title1">SNS</h3>
		
		<div class="subl-table">
			<table>
				<caption>SNS</caption>
				<colgroup>
					<col style="width:15%;">
					<col style="width:*%;">
				</colgroup>
				<tbody>
					<c:if test="${not empty univInfo.snsFace}">
						<tr>
							<th scope="row"><img class="app-icon" src="${design.resource }/images/sub/face.gif" alt="페이스북 아이콘" />페이스북</th>
							<td><a href="${univInfo.snsFace }" target="_blank" style="cursor:pointer;">${univInfo.snsFace}</a></td>
						</tr>
					</c:if>
					
					<c:if test="${not empty univInfo.snsTwit}">
						<tr>
							<th scope="row"><img class="app-icon" src="${design.resource }/images/sub/twitor.gif" alt="트위터 아이콘" />트위터</th>
							<td><a href="${univInfo.snsTwit }" target="_blank">${univInfo.snsTwit}</a></td>
						</tr>
					</c:if>
					
					<c:if test="${not empty univInfo.snsBlog}">
						<tr>
							<th scope="row"><img class="app-icon" src="${design.resource }/images/sub/blog.gif" alt="블로그 아이콘" />블로그</th>
							<td><a href="${univInfo.snsBlog }" target="_blank">${univInfo.snsBlog}</a></td>
						</tr>
					</c:if>
					
					<c:if test="${not empty univInfo.snsHp}">
						<tr>
							<th scope="row"><img class="app-icon" src="${design.resource }/images/sub/homepage.gif" alt="홈페이지 아이콘" />홈페이지</th>
							<td><a href="${univInfo.snsHp }" target="_blank">${univInfo.snsHp}</a></td>
						</tr>
					</c:if>
					
					<c:if test="${not empty univInfo.snsInsta }">
						<tr>
							<th scope="row"><img class="app-icon" src="${design.resource }/images/sub/insta.png" alt="인스타 아이콘" />인스타</th>
							<td><a href="${univInfo.snsInsta }" target="_blank">${univInfo.snsInsta }</a></td>
						</tr>
					</c:if>
						
					<c:if test="${not empty univInfo.snsKakao }">
						<tr>
							<th scope="row"><img class="app-icon" src="${design.resource }/images/sub/kakao.png" alt="카카오 아이콘" />카카오</th>
							<td><a href="${univInfo.snsKakao }" target="_blank">${univInfo.snsKakao }</a></td>
						</tr>
					</c:if>
					
				</tbody>
			</table>
		</div>
		
		</c:if>
		
		
		
	</div>

<!-- footer -->
<c:import url="${design.footer}" charEncoding="UTF-8" />