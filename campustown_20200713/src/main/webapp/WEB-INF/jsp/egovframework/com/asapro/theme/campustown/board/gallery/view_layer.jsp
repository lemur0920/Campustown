<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="asapro" uri="http://www.asadal.com" %>

<script>
jQuery(function($){
	
	$(".popup-box").show();
	$(".popup-content").show();
	$(".hidden-popup").css({"overflow":"hidden"});
	
	$("#content").on('click', '.popup-content .popup-header > .close', function(){
		$(".popup-box").hide();
		$(".popup-content").hide();
		$("body").css({"overflow":"auto"});
	});
});
</script>

<!-- layerPop -->
	<div class="popup-content">
		<div class="popup-header">
			<p class="close"><a href="#n"><img src="${design.resource }/images/sub/popup-close.gif" alt="닫기"></a></p>
		</div>
		<div class="popup-body">
			<div class="gullery-popup">
				<i class="img">
					<c:if test="${not empty boardArticle.thumb && boardArticle.thumb.fileId > 0 }">
						<img src="${boardArticle.thumb.fileServletUrl}" alt="${item.thumb.fileAltText }"
							onerror="this.src='${CONTEXT_PATH }/design/common/images/asset/noImage400x300.jpg'">
					</c:if>
					<c:if test="${empty boardArticle.thumb}">
						<img src="${CONTEXT_PATH }/design/common/images/asset/noImage400x300.jpg" alt="대체 이미지">
					</c:if>
				</i>
				<div class="txt-box">
					<h4 class="tit">${boardArticle.baTitle }</h4>
					<%-- <p class="txt"><a href="#n"><c:out value="${boardArticle.baContentHtml}" escapeXml="false" /></a></p> --%>
					<div><c:out value="${boardArticle.baContentHtml}" escapeXml="false" /></div>
					<div class="clearfix">
						<p class="time fl">
							<fmt:formatDate value="${boardArticle.baRegDate}" pattern="yyyy-MM-dd" />
						</p>
						<%--좋아요 --%>
						<c:if test="${boardConfig.bcSupportRecommend}">
							<c:set var="cookiename" value="ba_reco_${currentSite.siteId}_${boardArticle.baId}" />
							<c:set var="className" value="heartWhite" />
							<c:if test="${not empty cookie[cookiename]['value'] }"><c:set var="className" value="heartYellow" /></c:if>
							<p class="btnA ba-recommend fr" id="ba_recommend_${boardArticle.baId}" data-bcid="${boardConfig.bcId}" data-baid="${boardArticle.baId}" style="cursor: pointer;">
								<span class="${className }">${boardArticle.baRecommend}</span>
							</p>
						</c:if>
					</div>
					<div class="but-box">
						<c:if test="${not empty boardArticle.baCustomField1}">
							<p><a href="${boardArticle.baCustomField1}" target="_blank">바로가기</a></p>
						</c:if>
						<%-- 
						<c:set var="displayCnt" value="${fn:length(boardConfig.bcCustomFieldUseToArray)}"/>
						<c:forEach begin="1" end="${displayCnt }" var="i" step="1">
							<c:if test="${boardConfig.bcCustomFieldUseToArray[i-1] eq 'true'}">
							${boardArticle.baCustomField${displayCnt} }
							</c:if>
						</c:forEach>
						 --%>
					</div>
				</div>
			</div>
		</div>
	</div>
<!-- // layerPop -->
