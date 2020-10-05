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
<c:if test="${not empty messageCode}"><script>alert('${messageCode}');</script></c:if>

<!-- sidebar -->
<c:import url="${design.themeDir}/layout/sidebar.jsp" charEncoding="UTF-8" />

			<!-- content -->
			<div id="content">
			
				<!-- faq list -->
				<div class="list_faq">
					<dl>
						
						<c:if test="${empty paging.result}">
							<div>등록된 게시글이 없습니다.</div>
						</c:if>
						<c:forEach items="${paging.result}" var="item" varStatus="vs">
							<dt class="clearfix">
								<button class="btn"><span class="nsq">Q</span>${item.baTitle}</button>
							</dt>
							<dd>
								<span class="nsq">A</span>
								<p>
									<c:out value="${item.baContentHtml}" escapeXml="false" />
								</p>
							</dd>
						</c:forEach>
					</dl>
				</div>
				
				<!-- paging -->
				<c:out value="${paging.userTextTag}" escapeXml="false"/>
			
			</div>
			<!-- content end -->

<!-- footer -->
<c:import url="${design.footer}" charEncoding="UTF-8" />