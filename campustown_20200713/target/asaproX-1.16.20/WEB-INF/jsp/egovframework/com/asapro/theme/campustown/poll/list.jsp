<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="asapro" uri="http://www.asadal.com" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!-- header -->

<c:import url="${design.header}" charEncoding="UTF-8" />

<script>
jQuery(function($){
 	
 
});
</script>
	
<!-- sidebar -->
<c:import url="${design.themeDir}/layout/sidebar.jsp" charEncoding="UTF-8" />

	<!-- content -->
	<ul class="stat-btns clearfix">
		<li class="<c:if test="${pollSearch.poStatus eq 'ongoing' }">on</c:if>"><a href="${APP_PATH }/poll/list?poStatus=ongoing">진행중</a></li>
		<li class="<c:if test="${pollSearch.poStatus eq 'complete' }">on</c:if>"><a href="${APP_PATH }/poll/list?poStatus=complete">완료</a></li>
	</ul>
	
	<div class="gap40"></div>
	<div class="pool-out">
		<ul>
			<c:forEach items="${paging.result}" var="item" varStatus="vs">
				<li class="clearfix">
					<div class="pool-left">
						<c:if test="${not empty item.thumb && item.thumb.fileId > 0}">
							<img src="${item.thumb.fileServletThumbnailUrl}" alt="${item.thumb.fileAltText }" onerror="this.src='${CONTEXT_PATH }/design/common/images/asset/noImage400x300.jpg'" />
						</c:if>
					</div>
					<div class="pool-list-ct">
						<p class="plc-tit">${item.poQuestion }</p>
						<p class="plc-tip">
							<!-- <span>
								경기청년 국민연금 지원
							</span> -->
							${asapro:abbreviate(item.poDescription, 160) }
						</p>
						<div class="plc-info">
							<p class="date">참여기간<span class="light">${asapro:formatStringDate(item.poStartDate,'yyyy-MM-dd','yyyy.MM.dd')}  ~${asapro:formatStringDate(item.poEndDate,'yyyy-MM-dd','yyyy.MM.dd') } </span></p>
							<span class="bg"><img src="${design.resource }/images/sub/pool_list_bg03.gif" alt="찬성" /></span>
							<p class="join bg2">찬성<span class="red">${item.poYesCnt }</span>건</p>
							<span class="bg"><img src="${design.resource }/images/sub/pool_list_bg03.gif" alt="반대" /></span>
							<p class="join bg3">반대<span class="red">${item.poNoCnt }</span>건</p>
						</div>
					</div>
					<a href="${APP_PATH }/poll/view?poId=${item.poId }&cp=${pollSearch.cp}${pollSearch.queryString}" class="pool-detail-btn">자세히보기</a>
				</li>
			</c:forEach>
			
			<c:if test="${empty paging.result}">
			<!-- 게시물이 없을 때 -->
			<li class="nodata">게시물이 없습니다.</li>
			</c:if>
		</ul>
	</div>

	<!-- paging -->
	<c:out value="${paging.userImageTag}" escapeXml="false"/>
	
	<!-- content end -->

<!-- footer -->
<c:import url="${design.footer}" charEncoding="UTF-8" />