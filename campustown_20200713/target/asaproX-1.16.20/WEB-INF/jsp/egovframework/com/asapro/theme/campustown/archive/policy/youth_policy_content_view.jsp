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
		
		<c:out value="${policyForm.arcContent}" escapeXml="false" />
		
		<div class="gap40">&nbsp;</div>
		<div class="bg-line-box">
			<div class="blb-in ">
				<p class="blb-txt2">본 정보는 제공기관의 사정 및 기타 원인에 따라서 변경될 수 있으며, 자세한 사항은 해당기관의 홈페이지 또는 전화문의를 통하여 확인하시기 바랍니다.</p>
			</div>
		</div>
		<!-- content end -->

<!-- footer -->
<c:import url="${design.footer}" charEncoding="UTF-8" />