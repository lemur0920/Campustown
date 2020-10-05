<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="asapro" uri="http://www.asadal.com" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!-- header -->
<c:import url="${design.header}" charEncoding="UTF-8" />

<%-- <c:if test="${archiveCategory.catSupportRecommend}"><script src="${CONTEXT_PATH }/design/common/js/recommend.js"></script></c:if> --%>

<!-- sidebar -->
<c:import url="${design.themeDir}/layout/sidebar.jsp" charEncoding="UTF-8" />
		
		<!-- content -->
		<p><span class="tit">${spaceForm.arcTitle}</span></p>
		<div class="img-text-box style2 mgt25 mb50 clearfix">
			<div class="left-p">
				<!-- <img src="http://www.campustown.or.kr/img/2bnr_01.png" alt="" /> -->
				<img src="${spaceForm.thumb.fileServletUrl}" alt="${spaceForm.thumb.fileAltText }" 
					onerror="this.src='${design.resource}/images/sub/imhtext-list_img02.jpg'">
			</div>
			<div class="right-p">
				<p class="text-title">${spaceForm.spaComment}</p>
				<div class="text-con">
					<ul>
						<li><b>신청서 접수 이메일 주소 : </b> <a href="#n">${spaceForm.spaEmail}</a></li>
						<li><b>문의사항 연락처 : </b> ${spaceForm.spaTel}</li>
					</ul>
				</div>
			</div>
		</div>
		
		${spaceForm.arcContent }
		
		<!-- button -->
		<div class="btn-area txc clearfix">
			<a href="${APP_PATH}/archive/space/move_space" class="btn02 fc-bg-blue fr">목록</a>
		</div>
		<%-- 					
		<c:if test="${archiveCategory.catSupportRecommend }">
			<!-- 추천하기(좋아요) -->
			<div class="recommend_btn">
				<c:set var="cookiename" value="arc_reco_${currentSite.siteId}_${spaceForm.arcId}" />
				<a href="#n" class="fine-btn archive-recommend <c:if test="${not empty cookie[cookiename]['value'] }">on</c:if>" data-catid="${spaceForm.arcCategory}" data-arcid="${spaceForm.arcId}">좋아요<span>${spaceForm.arcRecommend}</span></a>
			</div>
		</c:if>
		 --%>
		<!-- galery2 view -->
		<%-- 
		<div class="galv_box clearfix">
			<div class="galv_img">
				<c:if test="${not empty spaceForm.thumb && spaceForm.thumb.fileId > 0}">
					<img src="${spaceForm.thumb.fileServletUrl}" alt="${spaceForm.thumb.fileAltText }" onerror="this.src='${CONTEXT_PATH }/design/common/images/asset/noImage300x400.jpg'" />
				</c:if>
			</div>
			<div class="galv_nys">
				<p class="city"><span>
				<c:if test="${not empty archiveCategory.catMetaCode1 }">
					${asapro:codeName(spaceForm.arcMetaCode1, arcMetaCode1List)}
				</c:if>
				<c:if test="${not empty spaceForm.arcMetaCode2 }">
					${asapro:codeName(spaceForm.arcMetaCode2, arcMetaCode2List)}
				</c:if>
				<c:if test="${not empty arcMetaCode2ListAll }">
					${asapro:codeName(spaceForm.arcMetaCode2, arcMetaCode2ListAll)}
				</c:if>
				<c:if test="${not empty archiveCategory.catMetaCode3 }">
					${asapro:codeName(spaceForm.arcMetaCode3, arcMetaCode3List)}
				</c:if>
				
				</span></p>
				<p class="tit">${spaceForm.arcTitle }</p>
				<ul class="ny">
					<li><span>공간유형</span>
						<c:if test="${not empty archiveCategory.catMetaCode3 }">
							${asapro:codeName(spaceForm.arcMetaCode3, arcMetaCode3List)}
						</c:if>
					</li>
					<li><span>위치</span>${spaceForm.spaLocation }</li>
					<li><span>이용시간</span>${spaceForm.spaUseHours }</li>
					<li><span>연락처</span>${spaceForm.spaTel }</li>
					<li><span>업무시간</span>${spaceForm.spaBusinessHours }</li>
					<li><span>운영기관</span>${spaceForm.spaAgency }</li>
					<li><span>홈페이지</span> 
						<c:if test="${not empty spaceForm.spaSiteUrl }">
							<a class="green-btn2" href="${spaceForm.spaSiteUrl }" target="_blank" title="새창 열림">바로가기</a>
							<a class="link" href="${spaceForm.spaSiteUrl }" target="_blank">${spaceForm.spaSiteUrl }</a>
						</c:if>
						<c:if test="${empty spaceForm.spaSiteUrl }">
							-
						</c:if>
					</li>
				</ul>
			</div>
		</div>
		 --%>
		 <%-- 
		<ul class="galv_edit">
			<li><p class="tit">공간소개</p></li>
			<li>
				<div class="nys">
					<c:out value="${spaceForm.arcContent}" escapeXml="false" />
				</div>
			</li>
		</ul>
		 --%>	
		<%-- 
		<div class="ct_btn">
			<a href="${APP_PATH}/archive/${spaceForm.arcCustomType}/${spaceForm.arcCategory }?cp=${backListSearch.cp}${backListSearch.queryString}" class="list">목록</a>
		</div>
		 --%>
		<!-- content end -->
	<!--</div>--> <!--  End area -->		
<!--</div>--> <!--  End content -->
<!-- footer -->
<c:import url="${design.footer}" charEncoding="UTF-8" />