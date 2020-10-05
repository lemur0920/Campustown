<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="asapro" uri="http://www.asadal.com" %>

<!-- header -->
<c:import url="${design.header}" charEncoding="UTF-8" />

<script>
jQuery(function($){
	//폼검색
	$('.searchBtn').on('click', function(e){
		e.preventDefault();
		$('#archiveSearch').submit();
	});
	
});
</script>

<!-- sidebar -->
<c:import url="${design.themeDir}/layout/sidebar.jsp" charEncoding="UTF-8" />

<%-- ============================= 메뉴별 컨텐츠 ============================ --%>

<div class="RightCWarp">
	<div class="pshtroe">
		<p>TV광고, 라디오광고, 인쇄광고, 옥외광고, 인터넷광고 등 4만5천여 점의 한국 근현대 광고를 디지털로 전환하여 아카이브를 구축, 관람자들이 검색할 수 있도록 한 디지털 광고도서관으로써 광고를 전공하는 대학생과 광고산업에 종사하는 전문가들이 광고제작에 활용할 수 있도록 한 디지털 광고자료 센터이다.</p><p>현재 홈페이지에서 제공하는 광고자료(디지털 아카이브)는 TV광고, 라디오광고, 인쇄광고의 세 부분으로 구분하여 각 20편의 광고를 검색할 수 있도록 제공하고 있습니다. 4만5천여점에 대한 좀 더 자세한 광고자료 검색은 박물관으로 직접 방문하셔서 이용해주시면 감사하겠습니다.</p>
	</div>
	<div class="popstor">
		<p class="gdpdsfer">자료현황 : 총 45,810편(1886년부터 현재까지)</p>
	</div>
	<div class="s1marg30">
		<table class="tablest2">
			<caption>광고자료관 자료현황</caption>
			<colgroup>
				<col style="width:34%;" />
				<col style="width:33%;" />
				<col style="width:33%;" />
			</colgroup>
			<thead>
				<tr>
					<th scope="col" colspan="3">광고자료관 자료현황</th>
				</tr>
				<tr>
					<th scope="col">구분</th>
					<th scope="col" colspan="2">수량</th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td>영상광고(국내)</td>
					<td>17,852</td>
					<td rowspan="3">21,377</td>
				</tr>
				<tr>
					<td>영상광고(국외)</td>
					<td>349</td>
				</tr>
				<tr>
					<td>라디오광고</td>
					<td>3,176</td>
				</tr>
				<tr>
					<td>인쇄광고</td>
					<td colspan="2">20,226</td>
				</tr>
				<tr>
					<td>기타광고</td>
					<td colspan="2">4,207</td>
				</tr>
				<tr>
					<td>계</td>
					<td colspan="2">45,810</td>
				</tr>
			</tbody>
		</table>
	</div>
	
	<div class="s43buttn mgt30">
		<ul>
			<li class="<c:if test="${archiveSearch.metaCode1 eq 'video' }">on</c:if>"><a href="?metaCode1=video">TV광고</a></li>
			<li class="<c:if test="${archiveSearch.metaCode1 eq 'print' }">on</c:if>"><a href="?metaCode1=print">인쇄광고</a></li>
			<li class="<c:if test="${archiveSearch.metaCode1 eq 'radio' }">on</c:if>"><a href="?metaCode1=radio">라디오광고</a></li>
		</ul>
		<div class="clear"></div>
	</div>
	
	<c:if test="${archiveSearch.metaCode1 ne 'radio' }">
		<div class="defulstv">
			<ul>
				<c:forEach items="${paging.result }" var="item">
					<li>
						<c:if test="${not empty item.arcFileInfos and not empty item.arcFileInfos[0].fileUUID }">
							<a href="${APP_PATH}/file/download/uu/${item.arcFileInfos[0].fileUUID}">
								<img src="${item.thumb.fileServletThumbnailUrl }" alt="${item.arcYear }${item.arcAdvertiser }" />
								<span>${item.arcYear }<br/>${item.arcAdvertiser }</span>
							</a>
						</c:if>
						<c:if test="${empty item.arcFileInfos or empty item.arcFileInfos[0].fileUUID }">
							<a href="#n">
								<img src="${item.thumb.fileServletThumbnailUrl }" alt="${item.arcYear }${item.arcAdvertiser }" />
								<span>${item.arcYear }<br/>${item.arcAdvertiser }</span>
							</a>
						</c:if>
					</li>
				</c:forEach>
				
			</ul>
			<div class="clear"></div>
		</div>
	</c:if>
	
	<c:if test="${archiveSearch.metaCode1 eq 'radio' }">
		<div class="defulstv">
			<table class="tablest2 pobod">
				<caption>광고자료관 자료현황</caption>
				<colgroup>
					<col style="width:15%;" />
					<col style="width:25%;" />
					<col style="width:20%;" />
					<col style="width:20%;" />
					<col style="width:20%;" />
				</colgroup>
				<tbody>
					<c:forEach items="${paging.result }" var="item">
						<tr>
							<td>${item.arcYear }</td>
							<td>${item.arcProductDiv }</td>
							<td>${item.arcProduct } </td>
							<td>${item.arcAdvertiser }</td>
							<td>
								<c:if test="${not empty item.arcFileInfos and not empty item.arcFileInfos[0].fileUUID }">
									<a href="${APP_PATH}/file/download/uu/${item.arcFileInfos[0].fileUUID}"><img src="${design.resource }/images/sub/s423fasut_03.jpg" alt="시작" /></a>
								</c:if>
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</c:if>
	
</div>
<%-- ============================= 메뉴별 컨텐츠 끝 ============================ --%>


<!-- footer -->
<c:import url="${design.footer}" charEncoding="UTF-8" />