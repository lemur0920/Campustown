<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<ul class="bot_slide clfix">
	<c:if test="${not empty list }">
		<c:forEach items="${list }" var="item" varStatus="bvs">
			<li>
				<c:if test="${not empty item.bnLink}">
					<a href="${item.bnLink}" title="새창 열림" <c:if test="${item.bnNewWin}"> target="_blank"</c:if>>
						<img src="${item.thumb.fileServletUrl}" alt="${item.bnName}" />
					</a>
				</c:if>
				<c:if test="${empty item.bnLink}">
					<img src="${item.thumb.fileServletUrl}" alt="${item.bnName}" />
				</c:if>
			</li>
		</c:forEach>
	</c:if>
</ul>
<%-- 
	<div class="f_play">
		<div class="main_plbtl prev"><a onclick="main_play_prev();"><img src="${design.resource }/images/main/play_prev_l.gif" border="0" alt=""/></a></div>
		<ul class="main_play" id="main_play">
			<c:if test="${not empty list }">
				<c:forEach items="${list }" var="item" varStatus="bvs">
					<li class="<c:if test="${bvs.first }">first</c:if><c:if test="${bvs.last }">last</c:if>">
						<c:if test="${not empty item.bnLink}">
							<a href="${item.bnLink}"<c:if test="${item.bnNewWin}"> target="_blank"</c:if>>
								<img src="${item.thumb.fileServletUrl}" alt="${item.bnName}" />
							</a>
						</c:if>
						<c:if test="${empty item.bnLink}">
							<img src="${item.thumb.fileServletUrl}" alt="${item.bnName}" />
						</c:if>
					</li>
				</c:forEach>
			</c:if>
		</ul>
		<div class="main_plbtr next"><a onclick="main_play_next();"><img src="${design.resource }/images/main/play_next_l.gif" border="0" alt=""/></a></div>
	</div>
 --%>