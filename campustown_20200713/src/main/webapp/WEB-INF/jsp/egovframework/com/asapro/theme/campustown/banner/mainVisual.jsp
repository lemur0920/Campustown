<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div id="slider001" class="flexslider">
	<ul class="slides clearfix">
		<c:if test="${not empty list }">
			<c:forEach items="${list }" var="item" varStatus="vs">
				<li>
					<div class="slide-con clearfix">
						<div class="slide-con-lf">
							<img src="${item.thumb.fileServletUrl}" class="slide1_img1" alt="${item.thumb.fileAltText}" />
<%-- 							<c:if test="${not empty item.bnName }">
								<p class="txt2">${item.bnName}</p><br>
							</c:if>
							<c:if test="${not empty item.bnDescription }">
								<p class="txt3 nfq"><mark>&nbsp;${item.bnDescription}&nbsp;</mark></p>
							</c:if>
							<c:if test="${not empty item.bnLink }">
								<a href="${item.bnLink}" class="btn">자세히 보기 ▶</a>
							</c:if> --%>
						</div>
					</div>
				</li>
				
			</c:forEach>
		</c:if>
	</ul>
	<div class="slide-num"><span class="num"></span>/<span class="all"></span></div>
</div>

