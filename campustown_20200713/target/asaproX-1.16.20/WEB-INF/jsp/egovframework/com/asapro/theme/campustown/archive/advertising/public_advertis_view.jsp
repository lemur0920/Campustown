<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="asapro" uri="http://www.asadal.com" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!-- header -->
<c:import url="${design.header}" charEncoding="UTF-8" />
<script src="${CONTEXT_PATH }/design/common/js/mediaelement/mediaelement-and-player.js"></script>
<script src="${CONTEXT_PATH }/design/common/js/mediaelement/mediaelement.js"></script>
<script src="${CONTEXT_PATH }/design/common/js/mediaelement/lang/ko.js"></script>
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

<%-- <c:if test="${archiveCategory.catSupportRecommend}"><script src="${CONTEXT_PATH }/design/common/js/recommend.js"></script></c:if> --%>

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
	
	<c:if test="${advertisingForm.arcMetaCode1 eq 'broadcast' }">
		<div class="input-table">
			<table>
				<caption>${advertisingForm.arcTitle }</caption>
				<colgroup>
					<col style="width:100%;">
				</colgroup>
				<tbody>
					<tr>
						<td class="no-lb"><strong class="ft-18px">${advertisingForm.arcTitle }</strong></td>
					</tr>
					<c:if test="${advertisingForm.videoFileInfo != null and advertisingForm.videoFileInfo.fileId != null and advertisingForm.videoFileInfo.fileId > 0 }">
						<tr>
							<td class="no-lb td-bigpaid">
								<div class="video-explain">
									<div class="video">
										<video class="" width="788" height="443" style="max-width: 100%;" poster="${advertisingForm.thumb.fileServletUrl}" controls="controls" preload="none" >
											<source type="video/mp4" src="${APP_PATH}/file/stream/uu/${advertisingForm.videoFileInfo.fileUUID}" />
											<object width="788" height="443" style="max-width: 100%;" type="application/x-shockwave-flash" data="${CONTEXT_PATH }/design/common/js/mediaelement/mediaelement-flash-video.swf">
												<param name="movie" value="${CONTEXT_PATH }/design/common/js/mediaelement/mediaelement-flash-video.swf" />
												<param name="flashvars" value="controls=true&amp;poster=${APP_PATH}/file/thumbnail/uu/${advertisingForm.thumb.fileUUID}&amp;file=${APP_PATH}/file/download/uu/${advertisingForm.videoFileInfo.fileUUID}" />
												<img src="myvideo.jpg" width="320" height="240" title="No video playback capabilities" />
											</object>
										</video>
									</div>
									
									<div class="explain">
										${asapro:nl2br(advertisingForm.adtTvCf, false) }
									</div>
								</div>
							</td>
						</tr>
					</c:if>
					<c:if test="${advertisingForm.radioFileInfo != null and advertisingForm.radioFileInfo.fileId != null and advertisingForm.radioFileInfo.fileId > 0 }">
						<tr>
							<td class="no-lb td-bigpaid">
								<div class="video-explain">
									<div class="video">
										<audio class="mejs__player" id="audioPlayer001" style="max-width:100%; width: 100%;" controls="controls" preload="none">
										     <source src="${APP_PATH}/file/stream/uu/${advertisingForm.radioFileInfo.fileUUID}" type="audio/mp3">
										</audio>
									</div>
									
									<div class="explain">
										${asapro:nl2br(advertisingForm.adtRadioCf, false) }
									</div>
								</div>
							</td>
						</tr>
					</c:if>
				</tbody>
			</table>
		</div>
	
		<div class="input-table mt30">
			<table>
				<caption>방송공익광고</caption>
				<colgroup>
					<col style="width:30%;">
					<col style="width:*%;">
				</colgroup>
				<tbody>
					<tr>
						<th class="no-lb" scope="row">대분류</th>
						<td>${asapro:codeName(advertisingForm.arcMetaCode2, arcMetaCode2List)}</td>
					</tr>
					<tr>
						<th class="no-lb" scope="row">소분류</th>
						<td>${asapro:codeName(advertisingForm.arcMetaCode3, arcMetaCode3List)}</td>
					</tr>
					<tr>
						<th class="no-lb" scope="row">제목</th>
						<td>${advertisingForm.arcTitle }</td>
					</tr>
					<tr>
						<th class="no-lb" scope="row">제작연도</th>
						<td>${advertisingForm.adtManufactureYear }</td>
					</tr>
					<tr>
						<th class="no-lb" scope="row">광고회사 / 제작사</th>
						<td>${advertisingForm.adtProducer }</td>
					</tr>
					<tr>
						<th class="no-lb" scope="row">감독</th>
						<td>${advertisingForm.adtDirector }</td>
					</tr>
					<tr>
						<th class="no-lb" scope="row">배경음악</th>
						<td>${advertisingForm.adtBackMusic }</td>
					</tr>
					<tr>
						<th class="no-lb" scope="row">기획의도</th>
						<td>${advertisingForm.adtPlanIntertion }</td>
					</tr>
					<tr>
						<th class="no-lb" scope="row">구성 및 표현</th>
						<td>${advertisingForm.adtComposition }</td>
					</tr>
					<tr>
						<th class="no-lb" scope="row">기타</th>
						<td>${advertisingForm.adtProductionReview }</td>
					</tr>
					
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
				</tbody>
			</table>
		</div>
	</c:if>
	
	<c:if test="${advertisingForm.arcMetaCode1 eq 'print' || advertisingForm.arcMetaCode1 eq 'webtoon' }">
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
							<p class="fr"><strong>제작연도 :</strong> ${advertisingForm.adtManufactureYear }　<strong>매체종류 :</strong> ${advertisingForm.adtMedia }</p>
						</td>
					</tr>
					<tr>
						<td class="no-lb td-bigpaid">
							<a class="picture-boost<%--  --%>" href="#non">
								<c:if test="${advertisingForm.arcMetaCode1 eq 'print' }">
									<img src="${advertisingForm.thumb.fileServletUrl }" alt="${advertisingForm.thumb.fileAltText }" width="1160">
								</c:if>
								<c:if test="${advertisingForm.arcMetaCode1 eq 'webtoon' }">
									<img src="${advertisingForm.arcFileInfos[0].fileServletUrl }" alt="${advertisingForm.arcFileInfos[0].fileAltText }" width="1160">
								</c:if>
							</a>
						</td>
					</tr>
				</tbody>
			</table>
		</div>
	</c:if>
	
	
	<%-- <div class="movie-score">
		<strong class="title">광고평가하기</strong>
		<span class="number">평점<strong>4.10</strong></span>
		<span class="sub05-radio">
			<span><input type="radio" name="sb3-radio" id="sb3-radio1" checked="checked"><label for="sb3-radio1">1점</label></span>
			<span><input type="radio" name="sb3-radio" id="sb3-radio2"><label for="sb3-radio2">2점</label></span>
			<span><input type="radio" name="sb3-radio" id="sb3-radio3"><label for="sb3-radio3">3점</label></span>
			<span><input type="radio" name="sb3-radio" id="sb3-radio4"><label for="sb3-radio4">4점</label></span>
			<span><input type="radio" name="sb3-radio" id="sb3-radio5"><label for="sb3-radio5">5점</label></span>
		</span>
		<a href="#" class="btn sky">평가하기</a>
	</div>
	<div class="clear mt30">
		<h4 class="h4-tit fl mt0">한줄의견쓰기</h4>
		<a href="#" class="btn red2 fr">의견쓰기</a>
	</div>
	<div class="table-style mgt0">
		<table>
			<caption>한줄의견쓰기</caption>
			<colgroup>
				<col style="width:*%;">
				<col style="width:11%;">
				<col style="width:7%;">
			</colgroup>
			<thead>
				<tr>
					<th scope="col">100자평</th>
					<th scope="col">작성일</th>
					<th scope="col">삭제</th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td class="txt-left"><a href="#">피해자 입장에서, 들을때마다 가슴이 덜컥거려요. 제발 없애주세요</a></td>
					<td>2019/04/06</td>
					<td><a href="#"><img src="${design.resource }/images/sub/delete.png" alt="삭제"></a></td>
				</tr>
				<tr>
					<td class="txt-left"><a href="#">귀가 아파요 부정적인 내용보다는 긍정적인 말도 많은데 바꿨으면합니다</a></td>
					<td>2019/04/06</td>
					<td><a href="#"><img src="${design.resource }/images/sub/delete.png" alt="삭제"></a></td>
				</tr>
				<tr>
					<td class="txt-left"><a href="#">광고 보면볼수록 오싹하고 무섭습니다 데이트폭력의 끝은 여성은 살해, 암매장인데 남자는 잔소리 좀 듣는거네요 그 잔소리랑 살해가 같은 선상에 올려질 수 있는건가요? 방송중단 요청해요</a></td>
					<td>2019/04/06</td>
					<td><a href="#"><img src="${design.resource }/images/sub/delete.png" alt="삭제"></a></td>
				</tr>
				<tr>
					<td class="txt-left"><a href="#">여성을 겨냥한 강간, 스토킹 등의 폭력이 연인 간에는 성립되지 않아서 데이트 폭력이라는 명칭을 만들어낸겁니다. 제대로 알고 광고 만드세요</a></td>
					<td>2019/04/06</td>
					<td><a href="#"><img src="${design.resource }/images/sub/delete.png" alt="삭제"></a></td>
				</tr>
				<tr>
					<td class="txt-left"><a href="#">이광고좀 없애주세요 들을때마다 기분이 나빠집니다. 이왕이면 긍정적인 광고부탁드립니다</a></td>
					<td>2019/04/06</td>
					<td><a href="#"><img src="${design.resource }/images/sub/delete.png" alt="삭제"></a></td>
				</tr>
				<tr>
					<td class="txt-left"><a href="#">남성이 여성에게 헤어지자고 했다고 여자한테 죽거나 다친 남자가 얼마나 있는가... 남자성우만으로 구성한 것도 아니고...<br>기계적인 성별맞춤이 너무 불쾌했다.</a></td>
					<td>2019/04/06</td>
					<td><a href="#"><img src="${design.resource }/images/sub/delete.png" alt="삭제"></a></td>
				</tr>
			</tbody>
		</table>
	</div> --%>

	<!-- button -->
	<div class="sub-btn mgt30">
		<a class="btn blueAsh" href="${APP_PATH}/archive/${advertisingForm.arcCustomType}/${advertisingForm.arcCategory }?cp=${backListSearch.cp}${backListSearch.queryString}" >목록</a>
	</div>
	
	
<%-- ============================= 메뉴별 컨텐츠 끝 ============================ --%>

<!-- footer -->
<c:import url="${design.footer}" charEncoding="UTF-8" />