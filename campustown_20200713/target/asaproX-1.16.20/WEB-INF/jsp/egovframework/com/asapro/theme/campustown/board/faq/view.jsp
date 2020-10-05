<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="asapro" uri="http://www.asadal.com" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!-- header -->
<c:import url="${design.header}" charEncoding="UTF-8" />
<script>
jQuery(function($){
	// 상세펼침
	$('.list_faq .btn').parent().siblings('dd').slideUp('fast');
    $('.list_faq .btn').removeClass('on');
    $('.list_faq .btn').addClass('on')
    $('.list_faq .btn').parent().next('dd').slideDown('fast');
});
</script>
<c:if test="${not empty messageCode}"><script>alert('${messageCode}');</script></c:if>

<!-- sidebar -->
<c:import url="${design.themeDir}/layout/sidebar.jsp" charEncoding="UTF-8" />

			<!-- content -->
			<div id="content">
				<!-- faq list -->
				<div class="list_faq">
					<dl>
						<dt class="clearfix">
							<button class="btn"><span class="nsq">Q</span>${boardArticle.baTitle}</button>
						</dt>
						<dd>
							<span class="nsq">A</span>
							<p>
								<c:out value="${boardArticle.baContentHtml}" escapeXml="false" />
							</p>
						</dd>
					</dl>
				</div>

				<!-- button -->
				<div class="btn_wrap trt">
					<a class="btn t_wg" href="${APP_PATH}/board/${boardConfig.bcId}/list?cp=${backListSearch.cp}${backListSearch.queryString}">목록</a>
				</div>
				
			</div>
			<!-- content end -->

<!-- footer -->
<c:import url="${design.footer}" charEncoding="UTF-8" />