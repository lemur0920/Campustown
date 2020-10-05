<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
<div class="bottom">
	<div class="bt_slide2">
		<div class="slider002_date">
			<span class="img_index">1</span> / <span class="img_date"></span>
		</div>
		<div id="slider002" class="flexslider2">
			<ul class="slides2">
				<c:if test="${not empty list }">
					<c:forEach items="${list }" var="item" varStatus="vs">
						<li>
							<c:if test="${not empty item.bnLink}">
								<a href="${item.bnLink }" title="${item.bnName}" <c:if test="${item.bnNewWin}">target="_blank"</c:if>>
							</c:if>
								<img src="${item.thumb.fileServletUrl}" alt="${item.bnName}" />
							<c:if test="${not empty item.bnLink}">
								</a>
							</c:if>
						</li>
					</c:forEach>
				</c:if>
			</ul>
			<div class="clear"></div>
		</div>
	</div>
</div>
 --%>

<ul class="slides">
	<c:if test="${not empty list }">
		<c:forEach items="${list }" var="item" varStatus="vs">
			 <li>
				<c:if test="${not empty item.bnLink}">
					<a href="${item.bnLink }" title="${item.bnName}" <c:if test="${item.bnNewWin}">target="_blank"</c:if>>
				</c:if>
	             	<img src="${item.thumb.fileServletUrl}" alt="${item.bnName}" />
				<c:if test="${not empty item.bnLink}">
					</a>
				</c:if>
	         </li>
		</c:forEach>
	</c:if>
</ul>