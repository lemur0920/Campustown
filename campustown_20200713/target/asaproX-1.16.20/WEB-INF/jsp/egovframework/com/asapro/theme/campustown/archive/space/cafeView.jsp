<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="asapro" uri="http://www.asadal.com" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!-- header -->
<c:import url="${design.header}" charEncoding="UTF-8" />

<!-- sidebar -->
<c:import url="${design.themeDir}/layout/sidebar.jsp" charEncoding="UTF-8" />
		<c:if test="${archiveCategory.catSupportRecommend }">
		<!-- 추천하기(좋아요) -->
		<div class="recommend_btn">
			<c:set var="cookiename" value="arc_reco_${currentSite.siteId}_${spaceForm.arcId}" />
			<a href="#n" class="fine-btn archive-recommend <c:if test="${not empty cookie[cookiename]['value'] }">on</c:if>" data-catid="${spaceForm.arcCategory}" data-arcid="${spaceForm.arcId}">좋아요<span>${spaceForm.arcRecommend}</span></a>
		</div>
		</c:if>
		
		<!-- content -->
		<div class="galv_box clearfix">
		
			<!-- galery2 view -->
			<div class="view_info">
				<div class="vi_1">
					<h4 class="t_blue nsq">${spaceForm.arcTitle }</h4>
					<p class="img">
						<c:if test="${not empty spaceForm.thumb && spaceForm.thumb.fileId > 0}">
							<img src="${spaceForm.thumb.fileServletUrl}" alt="${spaceForm.thumb.fileAltText }" onerror="this.src='${CONTEXT_PATH }/design/common/images/asset/noImage300x400.jpg'" />
						</c:if>
					</p>
					<div class="info">
						<ul class="list_dot">
							<li>
								<strong>공간유형</strong>
								<span>
									<c:if test="${not empty archiveCategory.catMetaCode3 }">
										${asapro:codeName(spaceForm.arcMetaCode3, arcMetaCode3List)}
									</c:if>
								</span>
							</li>
							<li>
								<strong>위치</strong>
								${spaceForm.spaLocation }
							</li>
							<li>
								<strong>이용시간</strong>
								<span>${spaceForm.spaUseHours }</span>
							</li>
							<li>
								<strong>연락처</strong>
								<span>${spaceForm.spaTel }</span>
							</li>
							<li>
								<strong>업무시간</strong>
								<span>${spaceForm.spaBusinessHours }</span>
							</li>
							<li>
								<strong>운영기관</strong>
								<span>${spaceForm.spaAgency }</span>
							</li>
							<li>
								<strong>홈페이지</strong>
								<a href="${spaceForm.spaSiteUrl }" target="_blank">${spaceForm.spaSiteUrl }</a>
							</li>
						</ul>
					</div>
				</div>
				<p class="tit_backb mb_15">공간소개</p>
				<div class="box_txt mb_20">
					<c:out value="${spaceForm.arcContent}" escapeXml="false" />
				</div>
			</div>
		
		</div>
		
		<!-- button -->
		<div class="ct_btn">
			<a href="${APP_PATH}/archive/${spaceForm.arcCustomType}/cafe/${spaceForm.arcCategory }?cp=${backListSearch.cp}${backListSearch.queryString}" class="list">목록</a>
		</div>
		<!-- content end -->

<!-- footer -->
<c:import url="${design.footer}" charEncoding="UTF-8" />