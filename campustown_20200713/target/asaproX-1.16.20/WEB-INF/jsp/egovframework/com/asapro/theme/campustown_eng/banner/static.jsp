<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
	
<div class="bmc5-img">
	<c:if test="${not empty list }">
		<c:if test="${not empty list[0].bnLink}">
			<a href="${list[0].bnLink }" title="${list[0].bnName}" <c:if test="${list[0].bnNewWin}">target="_blank"</c:if>>
				<img src="${list[0].thumb.fileServletUrl}" alt="${list[0].bnDescription}" />
			</a>
		</c:if>
		<c:if test="${empty list[0].bnLink}">
			<img src="${list[0].thumb.fileServletUrl}" alt="${list[0].bnDescription}" />
		</c:if>
	</c:if>
</div>
	
<%-- 
<ul class="slide-con-rt clearfix">
	<c:if test="${not empty list }">
		<c:forEach items="${list }" var="item" varStatus="vs">
			<li>
				<c:if test="${not empty item.bnLink}">
					<a href="${item.bnLink }" title="${item.bnName}" <c:if test="${item.bnNewWin}">target="_blank"</c:if>>
						<img src="${item.thumb.fileServletUrl}" alt="${item.bnDescription}" />
					</a>
				</c:if>
				<c:if test="${empty item.bnLink}">
					<img src="${item.thumb.fileServletUrl}" alt="${item.bnDescription}" />
				</c:if>
			</li>
		</c:forEach>
	</c:if>
</ul>
 --%>