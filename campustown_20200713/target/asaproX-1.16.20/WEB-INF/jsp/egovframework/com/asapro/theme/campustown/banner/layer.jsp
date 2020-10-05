<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<script>
    $(function() {
    if( !$.cookie('not-today-fix-01') ){
        $('body').append("<div class=\"layer-popup fix_layer layer01\" >\n" +
            "    <div>\n" +
            "        <img class=\"top_close_btn\" src=\"${CONTEXT_PATH }/design/common/images/asset/popup_close.png\" alt=\"닫기\">\n" +
            "    </div>\n" +
            "    <div class=\"top_txt\">\n" +
            "        <div class=\"top_txt_box01\">\n" +
            "            <h2>\n" +
            "                법률 및 회계<br/>\n" +
            "                자문 지원사업\n" +
            "            </h2>\n" +
            "        </div>\n" +
            "        <div class=\"top_txt_box02\">\n" +
            "            <img src=\"${CONTEXT_PATH }/design/common/images/asset/popup_img01.png\">\n" +
            "        </div>\n" +
            "    </div>\n" +
            "    <div class=\"bottom_txt\">\n" +
            "        <p class=\"fs_14\">서울 캠퍼스타운은 법률 및 회계문제를  해소해주는 법률 및 회계 자문 서비스를  캠퍼스타운 창업팀에게 제공합니다.</p>\n" +
            "        <br/>\n" +
            "        <p class=\"fs_16\">대상 : 캠퍼스타운 창업팀</p>\n" +
            "        <p class=\"fs_16\">비용 : 무료 </p>\n" +
            "        <br/>\n" +
            "        <br/>\n" +
            "        <p class=\"fs_14\" style=\"font-size: 12px;\">※ 자세한 사항은 신청페이지에서 확인하세요.</p>\n" +
            "    </div>\n" +
            "    <div class=\"btn_box\">\n" +
            "        <button class=\"btn_01 not-today-fix-01\">오늘 하루 보지 않기</button>\n" +
            "        <button class=\"btn_02 move01\">신청하러 GO</button>\n" +
            "    </div>\n" +
            "</div>")
    }
    if( !$.cookie('not-today-fix-02') ){
        $('body').append("<div class=\"layer-popup fix_layer layer02\" >\n" +
            "    <div>\n" +
            "        <img class=\"top_close_btn\" src=\"${CONTEXT_PATH }/design/common/images/asset/popup_close_bk.png\" alt=\"닫기\">\n" +
            "    </div>\n" +
            "    <div class=\"top_txt\">\n" +
            "        <div class=\"top_txt_box01\">\n" +
            "            <h2>\n" +
            "                시제품<br/>\n" +
            "                제작 및 자문\n" +
            "            </h2>\n" +
            "        </div>\n" +
            "        <div class=\"top_txt_box02\">\n" +
            "            <img src=\"${CONTEXT_PATH }/design/common/images/asset/popup_img02.png\">\n" +
            "        </div>\n" +
            "    </div>\n" +
            "    <div class=\"bottom_txt\">\n" +
            "        <h3>시제품이 있으신가요?</h3>\n" +
            "        <p class=\"fs_14\">캠퍼스타운 공식 제조 파트너 안암동  캠퍼스타운 창업팀 '볼트앤너트'와 함께  양산을 진행해보세요</p>\n" +
            "        <p class=\"fs_14\" style=\"padding:10px 0 15px 0;font-size: 13px;\">(컨설팅은 무료로 제공되며, 제작비용관련하여 볼트앤너트와 별도 협의가 필요합니다.)</p>\n" +
            "        <p class=\"fs_14\" style=\"font-size: 12px;\">※ 자세한 사항은 신청페이지에서 확인하세요.</p>\n" +
            "    </div>\n" +
            "    <div class=\"btn_box\">\n" +
            "        <button class=\"btn_01 not-today-fix-02\">오늘 하루 보지 않기</button>\n" +
            "        <button class=\"btn_02 move02\">신청하러 GO</button>\n" +
            "    </div>\n" +
            "</div>")
    }

        if(${userAgent.operatingSystem.mobileDevice} ){
            $('.layer01').css({
                top: 140,
                left: 10
            });

            $('.layer02').css({
                top: 140,
                left: 50
            });
        }


        $(".layer01").drags();
        $(".layer02").drags();

        $(document).on( "click" , ".not-today-fix-01", function() {
            $.cookie('not-today-fix-01', '1', {expires : 1, path : '/'});
            $('.layer01').remove();
        })

        $(document).on( "click" , ".not-today-fix-02", function() {
            $.cookie('not-today-fix-02', '1', {expires : 1, path : '/'});
            $('.layer02').remove();
        })

        $(document).on( "click" , ".move01", function() {
            location.href = "${APP_PATH}/counsel/legal_counsel/list";
        })
        $(document).on( "click" , ".move02", function() {
            location.href = "${APP_PATH}/counsel/make_counsel/list";
        })

        $(document).on( "click" , ".top_close_btn", function(e) {
            $(e.target).closest('.layer-popup').remove();
        })
    })
</script>

<script>
<c:forEach items="${list}" var="item" varStatus="vs">
jQuery(function($){
	if( !$.cookie('${currentSite.siteId}_not_today_${item.bnId}') ){
		var html = '<div class="layer-popup dynamic-popup" id="layer_popup_${item.bnId}" style="<c:if test="${item.bnWidth > 0}">width: ${item.bnWidth}px;</c:if> z-index : ${99999 - item.bnOrder}; position:absolute; top:${item.bnTop}px; left:${item.bnLeft}px;">';
                html += '<img class="top_close_btn top_close_btn_${item.bnId}" src="${CONTEXT_PATH }/design/common/images/asset/popup_close.png" alt="닫기" style="<c:if test="${item.bnWidth > 0}">left: ${item.bnWidth-25}px;</c:if>;position:absolute;margin-top:10px;">';
				//<c:if test="${not empty item.bnLink}"> html += '<a class="layer-link" href="${item.bnLink}" <c:if test="${item.bnNewWin}">target="_blank"</c:if>>';</c:if>		
				html += '<img class="layer-popup-image" src="${item.thumb.fileServletUrl}" alt="${item.bnDescription}" style="<c:if test="${item.bnWidth > 0}">width: ${item.bnWidth}px;</c:if><c:if test="${item.bnHeight > 0}">height: ${item.bnHeight}px;</c:if>"/>';
				//<c:if test="${not empty item.bnLink}"> html += '</a>';</c:if>
				html += '<div class="not-today">';
					<%--html += '<input class="not-today-${item.bnId}" id="not_today_${item.bnId}" type="checkbox" name="notToday" value="1"/>';--%>
					html += ' <a class="not-today-${item.bnId}" for="not_today_${item.bnId}">오늘 하루 보지 않기</a>';
					<%--html += ' <a href="#layer_close" class="layer-close"><img src="${CONTEXT_PATH }/design/common/images/asset/close.png" alt="닫기"></a>';--%>
				html += '</div>';
                <c:if test="${not empty item.bnLink}">html += '<div class="layer-link-wrap"><a class="layer-link" href="${item.bnLink}" <c:if test="${item.bnNewWin}">target="_blank"</c:if>>바로가기</a></div>';</c:if>
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
			$('.dynamic-popup').css({
				// width: (window.innerWidth > 0) ? window.innerWidth : screen.width,
                top: 120
				,left: 0
			});
			$('.layer-popup-image').css({
				// width: (window.innerWidth > 0) ? window.innerWidth : screen.width
			});

            <%--$('.top_close_btn_${item.bnId}').css({--%>
            <%--    left: (window.innerWidth > 0) ? window.innerWidth-25 : screen.width-25--%>
            <%--});--%>
		}
	}
});
</c:forEach>
</script>