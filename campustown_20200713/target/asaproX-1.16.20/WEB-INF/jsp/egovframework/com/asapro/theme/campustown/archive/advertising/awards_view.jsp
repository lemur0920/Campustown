<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="asapro" uri="http://www.asadal.com" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!-- header -->
<c:import url="${design.header}" charEncoding="UTF-8" />
<script src="${CONTEXT_PATH }/design/common/js/mediaelement/mediaelement-and-player.js"></script>
<!-- <script src="${CONTEXT_PATH }/design/common/js/mediaelement/mediaelement.js"></script>
<script src="${CONTEXT_PATH }/design/common/js/mediaelement/lang/ko.js"></script> -->
<link type="text/css" rel="stylesheet" href="${CONTEXT_PATH }/design/common/js/mediaelement/mediaelementplayer.css">

<script>
$(document).ready(function(){
	$('video, audio').mediaelementplayer({
		// Do not forget to put a final slash (/)
		//pluginPath: 'https://cdnjs.com/libraries/mediaelement/',
		// this will allow the CDN to use Flash without restrictions
		// (by default, this is set as `sameDomain`)
		//shimScriptAccess: 'always'
		// more configuration
	});
	
	
	//
	document.oncontextmenu = function (e) {//우클릭방지
		//alert("오른쪽버튼을 이용할 수 없습니다.");
		return false;
	}
	document.ondragstart = new Function('return false');     // 드래그 방지
	document.onselectstart = new Function('return false');   // 선택 방지
	
});
</script>

<!-- sidebar -->
<c:import url="${design.themeDir}/layout/sidebar.jsp" charEncoding="UTF-8" />
<%-- ============================= 메뉴별 컨텐츠 ============================ --%>
	<c:if test="${archiveCategory.catSupportRecommend }">
	<!-- 추천하기(좋아요) -->
	<div class="recommend_btn">
		<c:set var="cookiename" value="arc_reco_${currentSite.siteId}_${advertisingForm.arcId}" />
		<a href="#n" class="fine-btn archive-recommend <c:if test="${not empty cookie[cookiename]['value'] }">on</c:if>" data-catid="${advertisingForm.arcCategory}" data-arcid="${advertisingForm.arcId}">좋아요<span>${advertisingForm.arcRecommend}</span></a>
	</div>
	</c:if>
	
	<div class="input-table">
		<table>
			<caption>${advertisingForm.arcTitle }</caption>
			<colgroup>
				<col style="width:100%;">
			</colgroup>
			<tbody>
				<tr>
					<td class="no-lb">
						<strong class="ft-18px fl">${advertisingForm.arcTitle }</strong>
						<p class="fr"><strong>수상연도 :</strong> ${advertisingForm.adtManufactureYear }년 　<strong>수상부문 :</strong> ${advertisingForm.adtAwardType } - ${advertisingForm.adtMedia } 　<strong>수상자  :</strong> ${advertisingForm.adtWinner }</p>
					</td>
				</tr>
				<tr>
					<td class="no-lb td-bigpaid">
						<c:if test="${empty advertisingForm.adtUccUrl }">
							<a class="picture-boost" href="#n"><img src="${advertisingForm.thumb.fileServletUrl }" alt="${advertisingForm.thumb.fileAltText }" style="max-width: 1160px;"></a>
						</c:if>
						<c:if test="${not empty advertisingForm.adtUccUrl }">
							<video class="" width="788" height="443" style="max-width: 100%;" poster="${advertisingForm.thumb.fileServletUrl}" controls="controls" preload="none" >
								<source type="video/youtube" src="${advertisingForm.adtUccUrl}" />
								<object width="788" height="443" style="max-width: 100%;" type="application/x-shockwave-flash" data="${CONTEXT_PATH }/design/common/js/mediaelement/mediaelement-flash-video.swf">
									<param name="movie" value="${CONTEXT_PATH }/design/common/js/mediaelement/mediaelement-flash-video.swf" />
									<param name="flashvars" value="controls=true&amp;poster=${APP_PATH}/file/thumbnail/uu/${advertisingForm.thumb.fileUUID}&amp;file=${advertisingForm.adtUccUrl}" />
									<img src="${advertisingForm.thumb.fileServletUrl}" width="788" height="443" title="No video playback capabilities" />
								</object>
							</video>
						</c:if>
					</td>
				</tr>
			</tbody>
		</table>
	</div>

	<c:if test="${not empty advertisingForm.adtUccUrl }">
		<div class="input-table mt30">
			<table>
				<caption>공익광고제역대수상작</caption>
				<colgroup>
					<col style="width:30%;">
					<col style="width:*%;">
				</colgroup>
				<tbody>
					<tr>
					<tr>
						<th class="no-lb" scope="row">자막</th>
						<td>${asapro:nl2br(advertisingForm.adtTvCf, false) }</td>
					</tr>
				</tbody>
			</table>
		</div>
	</c:if>

					
					<%-- <tr>
						<td colspan="4" class="board_con img_resize tlt">
							<c:out value="${researchForm.arcContent}" escapeXml="false" />
						</td>
					</tr>
					<tr>
						<th class="bg_gray">첨부파일</th>
						<td colspan="3" class="tlt">
							<c:if test="${not empty researchForm.arcFileInfos}">
								<c:forEach items="${researchForm.arcFileInfos}" var="arcFileInfo">
									<div><a href="${APP_PATH}/file/download/uu/${arcFileInfo.fileUUID}" title="다운로드" class="dl_file">${arcFileInfo.fileOriginalName} (${arcFileInfo.fileSizeString})</a></div>
								</c:forEach>
							</c:if>
							<c:if test="${empty researchForm.arcFileInfos}">
								<span>첨부된 파일 없음</span>
							</c:if>
						</td>
					</tr>
					<tr>
						<th class="bg_gray">키워드</th>
						<td colspan="3" class="tlt">
							${researchForm.arcTag }
						
							<!-- <a href="" class="tag">#사회혁신</a>
							<a href="" class="tag">#청년정책</a> -->
							
						</td>
					</tr> --%>
	

	<!-- button -->
	<div class="sub-btn mgt30">
		<a class="btn blueAsh" href="${APP_PATH}/archive/${advertisingForm.arcCustomType}/${advertisingForm.arcCategory }?cp=${backListSearch.cp}${backListSearch.queryString}" >목록</a>
	</div>
	
	
<%-- ============================= 메뉴별 컨텐츠 끝 ============================ --%>

<!-- footer -->
<c:import url="${design.footer}" charEncoding="UTF-8" />