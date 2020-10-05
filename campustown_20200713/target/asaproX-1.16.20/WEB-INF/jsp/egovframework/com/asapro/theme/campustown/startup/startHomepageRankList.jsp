<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="asapro" uri="http://www.asadal.com" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!-- header -->
<c:import url="${design.header}" charEncoding="UTF-8" />
<script>
jQuery(function($){
	
	//폼검색
	$('.search-year').on('click', function(e){
		e.preventDefault();
		$('#searchYear').val($(this).data('year') );
		$('#startHpMngrSearch').submit();
	});
	$('.search-month').on('click', function(e){
		e.preventDefault();
		$('#searchMonth').val($(this).data('month') );
		$('#startHpMngrSearch').submit();
	});
});
</script>

<!-- sidebar -->
<c:import url="${design.themeDir}/layout/sidebar.jsp" charEncoding="UTF-8" />

	<!-- content -->
	<div class="vil-tit4 mb20">서울캠퍼스타운 홈페이지에서 가장 활발히 활동하는 창업팀을 소개합니다!</br>랭킹은 <span>글작성수, 좋아요수</span> 등으로 합산됩니다.</div>
	<p class="fc-blue mb20">※ 글작성수 5점, 좋아요 1점</p>
	
	<form:form modelAttribute="startHpMngrSearch" action="${APP_PATH}/startup/homepageActivity/rankList" method="get">
		<form:hidden path="searchYear"/>
		<form:hidden path="searchMonth"/>
		
		<ul class="sub-tab2 clearfix tab2 bline">
			<c:forEach items="${yearList }" var="year" varStatus="vs">
				<li class="<c:if test="${year.SEARCH_YEAR eq startHpMngrSearch.searchYear }">on</c:if>">
					<a href="#n" class="search-year" data-year="${year.SEARCH_YEAR }">${year.SEARCH_YEAR }년</a>
				</li>
			</c:forEach>
		</ul>
		
		<div class="data-tab">
			<ul class="clearfix">
				<c:forEach items="${monthList }" var="month" varStatus="vs">
					<li class="<c:if test="${month.SEARCH_MONTH eq startHpMngrSearch.searchMonth }">on</c:if>">
						<a href="#n" class="search-month" data-month="${month.SEARCH_MONTH }">${month.SEARCH_MONTH }월</a>
					</li>
				</c:forEach>
			</ul>
		</div>
	</form:form>
	
	<div class="imhtext-list mt20">
		<ul class="clearfix">
			<c:if test="${empty startHomepageRankList }">
				<li>랭크된 창업팀이 없습니다.</li>
			</c:if>
			<c:if test="${not empty startHomepageRankList }">
				<c:forEach items="${startHomepageRankList }" var="item" varStatus="vs">
					<li>
						<dl class="text1">
							<dt>
								<a href="${APP_PATH}/startup/intro?compId=${item.compId}">
									<c:if test="${not empty item.file1Info && item.file1Info.fileId > 0 }">
										<img src="${item.file1Info.fileServletUrl}" alt="${item.file1AltText}" onerror="this.src='${design.resource}/images/sub/imhtext-list_img02.jpg'">
									</c:if>	
									<c:if test="${empty item.file1Info}">
										<img src="${design.resource}/images/sub/imhtext-list_img02.jpg" alt="대체 이미지">
									</c:if>	
								</a>
							</dt>
							<dd>
								<span class="tit">${item.compNm }
									<a href="${APP_PATH}/startup/intro?compId=${item.compId}">
										<img class="app-icon" src="${design.resource}/images/sub/homepage.gif" alt="홈페이지바로가기">
									</a> (${item.empNm })
								</span>
								<p>${item.ment }</p>
							</dd>
							<dd>
								<div class="text2">
									<p>
										<span><b>활동 점수</b> ${item.score }점 </span>
									</p>
									<p>
										<span><b>글작성수</b> ${item.baCnt }개 </span>
										<span><b>댓글수</b> ${item.cmtCnt }개</span>
									</p>
								</div>
								<p class="updata"><b>Update </b>
									<fmt:parseDate value="${item.updDt }" var="parseDate1" pattern="yyyyMMddHHmmss" scope="page"/>
									<fmt:formatDate value="${parseDate1}" pattern="yyyy.MM.dd"/>
								</p>
							</dd>
							<p class="level-div"><img src="${design.resource}/images/sub/num0${vs.count }_img.png" alt="순위" /></p>
						</dl>
					</li>
				</c:forEach>
			</c:if>
		</ul>
	</div>
	
	<!-- paging -->
	<c:out value="${paging.userImageTag}" escapeXml="false"/>
		
	<!-- content end -->

<!-- footer -->
<c:import url="${design.footer}" charEncoding="UTF-8" />