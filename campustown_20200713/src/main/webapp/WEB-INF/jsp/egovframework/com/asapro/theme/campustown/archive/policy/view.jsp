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
		
		<div class="galv_box clearfix">
			<%--<p class="trt mb_10"><button class="btn_share">SNS공유</button></p> --%>

			<h4 class="tct tit_blue3 mb_30">${policyForm.arcTitle }</h4>

			<!-- board view -->
			<div class="table tb_1">
				<form action="">
				<table>
					<caption>게시판명</caption>
					<colgroup>
						<col class="col_1">
						<col class="col_2">
						<col class="col_1">
						<col class="col_2">
					</colgroup>
					<tbody>
						<tr>
							<th>연구주제</th>
							<td colspan="3" class="tlt"><strong>${asapro:codeName(policyForm.arcMetaCode1, arcMetaCode1List)}</strong></td>
						</tr>
						<tr>
							<td colspan="4" class="board_con img_resize tlt">
								<c:out value="${policyForm.arcContent}" escapeXml="false" />
							</td>
						</tr>
						<tr>
							<th class="bg_gray">첨부파일</th>
							<td colspan="3" class="tlt">
								<c:if test="${not empty policyForm.arcFileInfos}">
									<c:forEach items="${policyForm.arcFileInfos}" var="arcFileInfo">
										<div><a href="${APP_PATH}/file/download/uu/${arcFileInfo.fileUUID}" title="다운로드" class="dl_file">${arcFileInfo.fileOriginalName} (${arcFileInfo.fileSizeString})</a></div>
									</c:forEach>
								</c:if>
								<c:if test="${empty policyForm.arcFileInfos}">
									<span>첨부된 파일 없음</span>
								</c:if>
							</td>
						</tr>
						<tr>
							<th class="bg_gray">키워드</th>
							<td colspan="3" class="tlt">
								${policyForm.arcTag }
							<%--
								<a href="" class="tag">#사회혁신</a>
								<a href="" class="tag">#청년정책</a>
								 --%>
							</td>
						</tr>
					</tbody>
				</table>
				</form>
			</div>

		
		</div>
		
		<!-- button -->
		<div class="ct_btn">
			<a href="${APP_PATH}/archive/${policyForm.arcCustomType}/${policyForm.arcCategory }?cp=${backListSearch.cp}${backListSearch.queryString}" class="list">목록</a>
		</div>
		<!-- content end -->

<!-- footer -->
<c:import url="${design.footer}" charEncoding="UTF-8" />