<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="asapro" uri="http://www.asadal.com" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!-- header -->
<c:import url="${design.header}" charEncoding="UTF-8" />

<!-- sidebar -->
<c:import url="${design.themeDir}/layout/sidebar.jsp" charEncoding="UTF-8" />
		
		<!-- content -->
		<c:if test="${archiveCategory.catSupportRecommend }">
		<!-- 추천하기(좋아요) -->
		<div class="recommend_btn">
			<c:set var="cookiename" value="arc_reco_${currentSite.siteId}_${policyForm.arcId}" />
			<a href="#n" class="fine-btn archive-recommend <c:if test="${not empty cookie[cookiename]['value'] }">on</c:if>" data-catid="${policyForm.arcCategory}" data-arcid="${policyForm.arcId}">좋아요<span>${policyForm.arcRecommend}</span></a>
		</div>
		</c:if>
		
		<div class="board-detail-head">
			<p class="title">${policyForm.arcTitle}</p>
			<p class="date"><fmt:formatDate value="${policyForm.arcRegdate}" pattern="yyyy.MM.dd" /></p>
		</div>
		
		<div class="board-detail-cont">
			<c:out value="${policyForm.arcContent}" escapeXml="false" />
		</div>
		
		<div class="add-file clearfix">
			<div class="af-name">
				<span>첨부파일</span>
			</div>
			<div class="af-cont">
				<c:if test="${not empty policyForm.arcFileInfos}">
					<c:forEach items="${policyForm.arcFileInfos}" var="arcFileInfo">
						<a href="${APP_PATH}/file/download/uu/${arcFileInfo.fileUUID}" title="다운로드" class="">${arcFileInfo.fileOriginalName} (${arcFileInfo.fileSizeString})</a>
					</c:forEach>
				</c:if>
				<c:if test="${empty policyForm.arcFileInfos}">
					<span>첨부된 파일 없음</span>
				</c:if>
			</div>
		</div>
		
		
		<!-- button -->
		<div class="ct_btn">
			<a href="${APP_PATH}/archive/${policyForm.arcCustomType}/${policyForm.arcCategory }?cp=${backListSearch.cp}${backListSearch.queryString}" class="list">목록</a>
		</div>
		
		
		<!-- content end -->

<!-- footer -->
<c:import url="${design.footer}" charEncoding="UTF-8" />