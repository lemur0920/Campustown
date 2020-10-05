<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="asapro" uri="http://www.asadal.com" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!-- header -->
<c:import url="${design.header}" charEncoding="UTF-8" />

<script>
jQuery(function($){
	
	// 탭 선택하게 설정
	//alert($('#baCategory1').val());
	
	$(".lSrc[id="+$('#baCategory1').val()+"]").addClass("on");
	
	//달력
	$('.datepicker-ui').datepicker({
		showOn: "focus"
		
	});
	
	//폼검색
	/* 
	$('.search-btn').on('click', function(e){
		e.preventDefault();
		$('#boardArticleSearch').submit();
	});
	 */
	// 회기 클릭
	$('.lSrc').on('click', function(e){
		
		e.preventDefault();
		//alert("click~!!");
		var srcKey = $(this).attr("id");
		
		$('#baCategory1').val(srcKey);
		$('#boardArticleSearch').submit();
	});
	
	//상세보기 클릭 레이어 로드
	$('.aSrc').on('click', function(e){   //.popup-link a
		e.preventDefault();
		var bcId = $(this).data('bcid');
		var baId = $(this).data('baid');
		
		$('.popup-box').load('${APP_PATH}/board/' + bcId + '/' + baId + '?viewType=layer');
		
	});
	
	$('#content').on('click', '.close a', function(e){
		e.preventDefault();
		$('.popup-box').html('');
	});
	 
});



</script>
<c:if test="${not empty messageCode}"><script>alert('${messageCode}');</script></c:if>

<!-- sidebar -->
<c:import url="${design.themeDir}/layout/sidebar.jsp" charEncoding="UTF-8" />

	<!-- content -->
	<div class="vil-tit2"><span>서울 캠퍼스타운</span>에서 꿈꾸는 우리 모습을 소개합니다.</div>
	
	<ul class="sub-tab2 clearfix tab3">
		<c:forEach items="${bcCategory1CodeList}" var="item" varStatus="status" begin="0" end="2">
			<li class="lSrc" id="${item.codeId }"><a href="#n">${item.codeName }</a></li>
		</c:forEach>
	</ul>
	
	<!-- board search type1 -->
	<form:form modelAttribute="boardArticleSearch" action="${APP_PATH}/board/${boardConfig.bcId}/list" method="get">
		<form:hidden path="baCommSelec"/>
		<form:hidden path="baCategory1"/>
	</form:form>
		
		
	<ul class="gallery-list clearfix">
		<c:if test="${empty paging.result && empty noticeList}">
			<!-- 게시물이 없을 때 -->
			<li class="nodata">게시물이 없습니다.</li>
		</c:if>
				
		<!-- 공지사항 게시물 -->
		<c:if test="${not empty noticeList}">
			<c:forEach items="${noticeList}" var="item" varStatus="vs">
				<li>
					<%-- 
					<c:if test="${not empty item.baCategory1 }">
						<span style="color:#cd4c85;">[${asapro:codeName(item.baCategory1, bcCategory1CodeList) }]</span>
					</c:if>
					 --%>
					<i class="img popup-link">
						<%-- <a class="view-detail" href="${item.permLink}?cp=${boardArticleSearch.cp}${boardArticleSearch.queryString}"
							data-bcid="${item.bcId}" data-baid="${item.baId}"> --%>
						<a href="#n" class="aSrc" data-bcid="${item.bcId}" data-baid="${item.baId}">
							<c:if test="${not empty item.thumb && item.thumb.fileId > 0}">
								<img src="${item.thumb.fileServletThumbnailUrl}" alt="${item.thumb.fileAltText }"
									onerror="this.src='${CONTEXT_PATH }/design/common/images/asset/noImage400x300.jpg'">
							</c:if>	
							<c:if test="${empty item.thumb}">
								<img src="${CONTEXT_PATH }/design/common/images/asset/noImage400x300.jpg" alt="대체 이미지">
							</c:if>	
						</a>
					</i>
					<div class="txt-box">
						<h4 class="tit">
							<a href="#n" class="aSrc" data-bcid="${item.bcId}" data-baid="${item.baId}">
								<c:if test="${not empty item.baCategory1 }">
									<span class="cate">[${asapro:codeName(item.baCategory1, bcCategory1CodeList) }]</span>
								</c:if>
								${item.baTitle }
							</a>
						</h4>
						<p class="txt">
							<a href="#n" class="aSrc" data-bcid="${item.bcId}" data-baid="${item.baId}">
								<c:choose>
									<c:when test="${fn:length(item.baContentPlain) > 49}">
										<c:out value="${fn:substring(item.baContentPlain,0,50)}" />...
	          							</c:when>
									<c:otherwise>
										<c:out value="${item.baContentPlain}" />
									</c:otherwise>
								</c:choose>
							</a>
						</p>
						<p class="time">
							<fmt:formatDate value="${item.baRegDate}" pattern="yyyy-MM-dd" />
						</p>
						<div class="info-con">
							<span>${item.baRecommend}</span>
							<span>${item.baHit}</span>
						</div>	
					</div>
				</li>
			</c:forEach>
		</c:if>
		
		<!-- 일반 게시물 -->
				
		<c:forEach items="${paging.result}" var="item" varStatus="vs">
			<li>
				<%-- 
				<c:if test="${not empty item.baCategory1 }">
					<span class="cate">${asapro:codeName(item.baCategory1, bcCategory1CodeList) }</span>
				</c:if>
				 --%>
				<i class="img popup-link">
					<%-- <a href="${item.permLink}?cp=${boardArticleSearch.cp}${boardArticleSearch.queryString}"
						data-bcid="${item.bcId}" data-baid="${item.baId}"> --%>
					<a href="#n" class="aSrc" data-bcid="${item.bcId}" data-baid="${item.baId}">	
						<c:if test="${not empty item.thumb && item.thumb.fileId > 0}">
							<img src="${item.thumb.fileServletThumbnailUrl}" alt="${item.thumb.fileAltText }"
								onerror="this.src='${CONTEXT_PATH }/design/common/images/asset/noImage400x300.jpg'">
						</c:if>	
						<c:if test="${empty item.thumb}">
							<img src="${CONTEXT_PATH }/design/common/images/asset/noImage400x300.jpg">
						</c:if>	
					</a>
				</i>
				<div class="txt-box">
					<h4 class="tit">
						<a href="#n" class="aSrc" data-bcid="${item.bcId}" data-baid="${item.baId}">
							<c:if test="${not empty item.baCategory1 }">
								<span class="cate">[${asapro:codeName(item.baCategory1, bcCategory1CodeList) }]</span>
							</c:if>
							${item.baTitle }
						</a>
					</h4>
					<p class="txt">
						<a href="#n" class="aSrc" data-bcid="${item.bcId}" data-baid="${item.baId}">
							<c:choose>
								<c:when test="${fn:length(item.baContentPlain) > 49}">
									<c:out value="${fn:substring(item.baContentPlain,0,50)}" />...
	         							</c:when>
								<c:otherwise>
									<c:out value="${item.baContentPlain}" />
								</c:otherwise>
							</c:choose>
						</a>
					</p>
					<p class="time">
						<fmt:formatDate value="${item.baRegDate}" pattern="yyyy-MM-dd" />
					</p>
					<div class="info-con">
						<span>${item.baRecommend}</span>
						<span>${item.baHit}</span>
					</div>	
				</div>
			</li>
		</c:forEach>
	</ul>
	
	<!-- button -->
	<div class="btn-area txr clearfix">
		<c:if test="${isBoardManager || bcAllowForm}">
			<a class="btn05" href="${APP_PATH}/board/${boardConfig.bcId}/new">글쓰기</a>
		</c:if>
	</div>	
	
	<!-- paging -->
	<c:out value="${paging.userImageTag}" escapeXml="false"/>
	
	
	
<!-- footer -->
<c:import url="${design.footer}" charEncoding="UTF-8" />