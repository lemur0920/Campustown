<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="asapro" uri="http://www.asadal.com" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!-- header -->
<c:import url="${design.header}" charEncoding="UTF-8" />

<script>
  $(window).resize(function(){resizeYoutube();});
  $(function(){resizeYoutube();});
  function resizeYoutube(){ $("iframe").each(function(){ if( /^https?:\/\/www.youtube.com\/embed\//g.test($(this).attr("src")) ){ $(this).css("width","100%"); $(this).css("height",Math.ceil( parseInt($(this).css("width")) * 480 / 854 ) + "px");} }); }
</script>

<script>
jQuery(function($){
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
	<div id="subl-center">
		<!-- tab -->
		<c:import url="${design.themeDir}/layout/tab3depth.jsp" charEncoding="UTF-8" />
		<form:form modelAttribute="startHpMngrSearch" action="${APP_PATH}/university/startEmp"  method="get">
			<form:hidden path="univId" value="${univInfo.univId }"/>
		</form:form>
		<%-- 
		<div class="subl-tab">
			<ul class="clearfix">
				<li class="on"><a href="${APP_PATH}/university/intro?univId=${univInfo.univId}">캠퍼스타운 소개 </a></li>
				<li><a href="#none">시설공간정보</a></li>
				<li><a href="${APP_PATH}/university/startups?univId=${univInfo.univId}">창업팀 현황</a></li>
				<li><a href="${APP_PATH}/university/news?univId=${univInfo.univId}">새소식</a></li>
				<li><a href="#none">지원프로그램</a></li>
			</ul>
		</div>
		 --%>
		
		<div class="board-table no-bor-new">
			<table>
				<caption>공지사항</caption>
				<colgroup>
					<col style="width: 9%" />
					<col style="width: auto" />
					<col style="width: 12%" />
					<col style="width: 10%" />
					<col style="width: 25%" />
					<col style="width: 16%" />
				</colgroup>
				<thead>
					<tr>
						<th scope="col">업종</th>
						<th scope="col">창업팀</th>
						<th scope="col">대표</th>
						<th scope="col">창업일자</th>
						<th scope="col">연락처</th>
						<th scope="col">이메일</th>
					</tr>
				</thead>
				<tbody>
					<c:if test="${empty paging.result}">
						<tr>
							<td colspan="6" class="text-center">등록된 정보가 없습니다.</td>
						</tr>
					</c:if>
					<c:forEach items="${paging.result}" var="item" varStatus="vs">
						<tr>
							<td>${asapro:codeName(item.induty, indutyCodeList)}</td>
							<td>${item.compNm }
								<%-- <c:if test="${not empty item.snsHp}"> --%> 
									<a href="${APP_PATH}/startup/intro?compId=${item.compId}" target="_blank">
										<img class="app-icon" src="${design.resource }/images/sub/homepage.gif" alt="">
									</a>
								<%-- </c:if> --%>
							</td>
							<td>${item.empNm } 
							<c:if test="${!item.seoulId and item.seoulId != '' and item.seoulIdMsgExpsrYn eq 'Y'}">
								<a href="${APP_PATH}/mypage/note/insert?noteReceiverId=${item.seoulId}&reply=true" class="noteWrite" title="쪽지쓰기"><img src="${design.resource }/images/sub/sub01_limg33.gif" alt="쪽지"></a>
							</c:if>							
							</td>
							<td>${item.fondDt }</td>
							<td>
								<%-- <c:if test="${item.telExpsrYn eq 'Y'}">${item.tel }</c:if> --%>
								<c:if test="${empty item.compTel or item.compTel eq '--'}">-</c:if>
								<c:if test="${item.compTel ne '--'}">
									${item.compTel }
								</c:if>
								<%-- <c:if test="${not empty item.compTel and item.phoneExpsrYn eq 'Y' and item.phone ne '--'}"> / </c:if> --%>
								<%-- <c:if test="${not empty item.compTel and item.compTel ne '--' and not empty item.phoneExpsrYn and item.phoneExpsrYn eq 'N' }"> / </c:if> --%>
								<c:if test="${not empty item.phone}"> / </c:if>
								
								<c:if test="${not empty item.phone and item.phoneExpsrYn eq 'Y'}">${item.phone }</c:if>
								<c:if test="${not empty item.phone and item.phoneExpsrYn eq 'N'}">비공개</c:if>
							</td>
							
							<td>
								<c:if test="${item.emailExpsrYn eq 'Y'}"><a href="#">${item.email}</a></c:if>	
								<c:if test="${item.emailExpsrYn eq 'N'}">비공개</c:if>	
								
							</td>		
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
		<!-- paging -->
		<c:if test="${not empty paging.result}">
			<div class="paging">
				<c:out value="${paging.userImageTag2}" escapeXml="false"/>
			</div>
		</c:if>
		<%-- 
		<div class="subl-paging clearfix">
			<a class="img" href="#"><img src="${design.resource }/images/sub/sub01_limg28.gif" alt="첫페지"></a>
			<a class="img mr-22" href="#"><img src="${design.resource }/images/sub/sub01_limg29.gif" alt="이전페지"></a>
			<a class="on" href="#">1</a>
			<a href="#">2</a>
			<a href="#">3</a>
			<a href="#">4</a>
			<a href="#">5</a>
			<a class="img ml-22" href="#"><img src="${design.resource }/images/sub/sub01_limg30.gif" alt="다음페지"></a>
			<a class="img" href="#"><img src="${design.resource }/images/sub/sub01_limg31.gif" alt="마지막페지"></a>
		</div>
		 --%>
		
	</div>
</div>   <!-- END #content -->

<!-- footer -->
<c:import url="${design.footer}" charEncoding="UTF-8" />