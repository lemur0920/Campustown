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
		$('#csYear').val($(this).data('year') );
		$('#csMonth').val('');
		$('#contentStatisSearch').submit();
	});
	$('.search-month').on('click', function(e){
		e.preventDefault();
		$('#csMonth').val($(this).data('month') );
		$('#contentStatisSearch').submit();
	});
});
</script>

<!-- sidebar -->
<c:import url="${design.themeDir}/layout/sidebar.jsp" charEncoding="UTF-8" />

	<!-- content -->
	<c:choose>
		<c:when test="${contentStatisSearch.csModiulSubCode eq 'hit'}">
			<div class="vil-tit6">전체 게시글 중 <span>조회수</span>가 가장 많은 게시글이 선정됩니다.</div>
		</c:when>
		<c:when test="${contentStatisSearch.csModiulSubCode eq 'recommend'}">
			<div class="vil-tit">전체 게시글 중 <span>좋아요</span>를 가장 많이 받은 게시글이 선정됩니다.</div>
		</c:when>
	</c:choose>
	
	<form:form modelAttribute="contentStatisSearch" action="${APP_PATH}/contentStatis/boardtype/${contentStatisSearch.csModiulSubCode}/bestList" method="get">
		<form:hidden path="csYear"/>
		<form:hidden path="csMonth"/>
		<form:hidden path="csCateCode"/>
		
		<ul class="sub-tab2 clearfix tab2 bline">
			<c:forEach items="${yearList }" var="year" varStatus="vs">
				<li class="<c:if test="${year.CS_YEAR eq contentStatisSearch.csYear }">on</c:if>">
					<a href="#n" class="search-year" data-year="${year.CS_YEAR }">${year.CS_YEAR }년</a>
				</li>
			</c:forEach>
		</ul>
		
		<div class="data-tab">
			<ul class="clearfix">
				<c:forEach items="${monthList }" var="month" varStatus="vs">
					<li class="<c:if test="${month.CS_MONTH eq contentStatisSearch.csMonth }">on</c:if>">
						<a href="#n" class="search-month" data-month="${month.CS_MONTH }">${month.CS_MONTH }월</a>
					</li>
				</c:forEach>
			</ul>
		</div>
	</form:form>
	
	<ul class="gallery-list clearfix mgt40">
		<c:if test="${empty boardBestList }">
			<li>베스트 게시물이 없습니다.</li>
		</c:if>
		<c:if test="${not empty boardBestList }">
			<c:forEach items="${boardBestList }" var="item" varStatus="vs">
				<li>
					<i class="img">
						<a href="${item.permLink}?cp=${contentStatisSearch.cp}${contentStatisSearch.queryString}">
							<c:if test="${not empty item.thumb && item.thumb.fileId > 0}">
								<img src="${item.thumb.fileServletThumbnailUrl}" alt="${item.baThumbText }"
									onerror="this.src='${CONTEXT_PATH }/design/common/images/asset/noImage400x300.jpg'" >
							</c:if>	
							<c:if test="${empty item.thumb }">
								<img src="${CONTEXT_PATH }/design/common/images/asset/noImage400x300.jpg" alt="대체이미지" >
							</c:if>	
						</a>
					</i>
					<div class="txt-box">
						<h4 class="tit"><a href="${item.permLink}?cp=${contentStatisSearch.cp}${contentStatisSearch.queryString}">${item.baTitle}</a></h4>
						<p class="txt"><a href="${item.permLink}?cp=${contentStatisSearch.cp}${contentStatisSearch.queryString}">${asapro:abbreviate(item.baContentPlain,50) }</a></p>
						<p class="time"><fmt:formatDate value="${item.baRegDate}" pattern="yyyy-MM-dd" /></p>
						<div class="info-con">
							<span>${item.baRecommend}</span>
							<span>${item.baHit}</span>
						</div>
					</div>
					<c:if test="${vs.count <= 3 }">
						<p class="level-div"><img src="${design.resource }/images/sub/num0${vs.count }_img.png" alt="순위왕관이미지"></p>
					</c:if>
				</li>
			
			</c:forEach>
		</c:if>
	</ul>

	<!-- paging -->
	<c:out value="${paging.userImageTag}" escapeXml="false"/>
		
	<!-- content end -->

<!-- footer -->
<c:import url="${design.footer}" charEncoding="UTF-8" />