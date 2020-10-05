<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
	<div class="owl-demo owl-carousel">
		<c:if test="${not empty list }">
			<c:forEach items="${list }" var="item" varStatus="vs">
				<div class="item">
					<div class="img">
						<img src="${item.thumb.fileServletUrl}" class="tab-slide1-img" alt="${item.bnDescription}" />
						<div class="state">
							<c:if test="${item.bnRecepStartdate <= NOW and item.bnRecepEnddate >= NOW }"><span class="green">진행</span></c:if>
							<c:if test="${item.bnRecepStartdate > NOW }"><span class="gal">예정</span></c:if>
							<c:if test="${item.bnRecepEnddate < NOW }"><span class="gal">종료</span></c:if>
							<span class="black">${item.bnArea}</span>
						</div>
					</div>
					<div class="item-box">
						<c:if test="${not empty item.bnLink}">
							<a href="${item.bnLink }" title="${item.bnName}" <c:if test="${item.bnNewWin}">target="_blank"</c:if>>
						</c:if>
			             	${item.bnName }
						<c:if test="${not empty item.bnLink}">
							</a>
						</c:if>
						<div>
							<p><strong>유형</strong>${item.bnRecepType }</p>
							<p><strong>기간</strong>${item.bnRecepStartdate } ~ ${item.bnRecepEnddate }</p>
						</div>
					</div>
				</div>
			</c:forEach>
		</c:if>
	</div>


