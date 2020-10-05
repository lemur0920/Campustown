<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<script>
<c:forEach items="${list}" var="item" varStatus="vs">
jQuery(function($){
	if( !$.cookie('${currentSite.siteId}_not_today_${item.bnId}') ){
		var html = '<div class="layer-popup" id="layer_popup_${item.bnId}" style="<c:if test="${item.bnWidth > 0}">width: ${item.bnWidth}px;</c:if> z-index : ${99999 - item.bnOrder}; position:absolute; top:${item.bnTop}px; left:${item.bnLeft}px;">';
				//<c:if test="${not empty item.bnLink}"> html += '<a class="layer-link" href="${item.bnLink}" <c:if test="${item.bnNewWin}">target="_blank"</c:if>>';</c:if>		
				html += '<img class="layer-popup-image" src="${item.thumb.fileServletUrl}" alt="${item.bnDescription}" style="<c:if test="${item.bnWidth > 0}">width: ${item.bnWidth}px;</c:if><c:if test="${item.bnHeight > 0}">height: ${item.bnHeight}px;</c:if>"/>';
				//<c:if test="${not empty item.bnLink}"> html += '</a>';</c:if>
				<c:if test="${not empty item.bnLink}">html += '<div class="layer-link-wrap"><a class="layer-link" href="${item.bnLink}" <c:if test="${item.bnNewWin}">target="_blank"</c:if>>바로가기</a></div>';</c:if>
				html += '<div class="not-today">';
					html += '<input class="not-today-${item.bnId}" id="not_today_${item.bnId}" type="checkbox" name="notToday" value="1"/>';
					html += ' <label class="not-today-${item.bnId}" for="not_today_${item.bnId}">오늘 하루 보지 않기</label>';
					html += ' <a href="#layer_close" class="layer-close"><img src="${CONTEXT_PATH }/design/common/images/asset/close.png" alt="닫기"></a>';
				html += '</div>';
			html += '</div>';
		var layer_popup_${item.bnId} = $(html);
		$('body').append(layer_popup_${item.bnId});
		layer_popup_${item.bnId}.show();
		layer_popup_${item.bnId}.drags();
		$('.layer-close').click(function(e){
			$(e.target).closest('.layer-popup').remove();
		});
		$('.not-today-${item.bnId}').click(function(e){
			$.cookie('${currentSite.siteId}_not_today_${item.bnId}', '1', {expires : 1, path : '/'});
			$('#layer_popup_${item.bnId}').remove();
		});
		
		if(${userAgent.operatingSystem.mobileDevice} ){
			$('.layer-popup').css({
				width: (window.innerWidth > 0) ? window.innerWidth : screen.width
				, top: 0
				, left: 0
			});
			$('.layer-popup-image').css({
				width: (window.innerWidth > 0) ? window.innerWidth : screen.width
			});
		}
	}
});
</c:forEach>
</script>