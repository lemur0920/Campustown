<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="asapro" uri="http://www.asadal.com" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!-- header -->

<c:import url="${design.header}" charEncoding="UTF-8" />

<script>
jQuery(function($){
	
	
});
</script>
	
<!-- sidebar -->
<c:import url="${design.themeDir}/layout/sidebar.jsp" charEncoding="UTF-8" />

			<!-- content -->
			<div id="content">
			
				<!-- board search type3 -->
				<div class="board_sch sel2">
					<form:form modelAttribute="spaceSearch" action="${APP_PATH}/archive/space/cafe/${archiveCategory.catId }" method="get">
						<form:hidden path="metaCode1"/>
						<div class="w0 btbox clerfix">
							<c:if test="${not empty arcMetaCode1List || not empty arcMetaCode2List || not empty arcMetaCode3List}">
								<%--
								<c:if test="${not empty arcMetaCode1Info }">
									<form:select path="metaCode1" title="${arcMetaCode1Info.catName}">
										<option value="">${arcMetaCode1Info.catName}</option>
										<form:options items="${arcMetaCode1List}" itemLabel="codeName" itemValue="codeId"/>
									</form:select>
								</c:if>
								 --%>
								 <%--
								<c:if test="${not empty arcMetaCode2Info }">
									<form:select path="metaCode2" title="${arcMetaCode2Info.catName}">
										<option value="">${arcMetaCode2Info.catName}</option>
										<form:options items="${arcMetaCode2List}" itemLabel="codeName" itemValue="codeId"/>
									</form:select>
								</c:if> --%>
								
								<form:select path="metaCode2" title="${arcMetaCode2Info.catName}">
									<option value="">${arcMetaCode2Info.catName}</option>
									<form:options items="${arcMetaCode2List}" itemLabel="codeName" itemValue="codeId"/>
								</form:select>
								
								<c:if test="${not empty arcMetaCode3Info }">
									<form:select path="metaCode3" title="${arcMetaCode3Info.catName}">
										<option value="">${arcMetaCode3Info.catName} 선택</option>
										<form:options items="${arcMetaCode3List}" itemLabel="codeName" itemValue="codeId"/>
									</form:select>
								</c:if>
							</c:if>
							<button type="submit" class="btn_sch">검색</button>
						</div>
					</form:form>
				</div>
				
				<c:forEach items="${selectedList }" var="item" varStatus="vs">
					<div class="input-table mt30">
						<table>
							<caption>${item.arcTitle }</caption>
							<colgroup>
								<col style="width:100%;">
							</colgroup>
							<tbody>
								<tr>
									<td class="no-lb"><strong class="ft-18px">${item.arcTitle }</strong></td>
								</tr>
								<tr>
									<td class="no-lb td-bigpaid">
										<a href="${item.permLink }?cp=${spaceSearch.cp}${spaceSearch.queryString}">
											<img src="${item.thumb.fileServletUrl }" alt="${item.thumb.fileAltText }" style="max-width: 780px;">
										</a>
									</td>
								</tr>
							</tbody>
						</table>
					</div>
					<div class="input-table mt30">
						<table>
							<caption>${item.arcTitle }</caption>
							<colgroup>
								<col style="width:30%;">
								<col style="width:*%;">
							</colgroup>
							<tbody>
								<tr>
									<th class="no-lb" scope="row">제목</th>
									<td>${item.arcTitle }</td>
								</tr>
								<tr>
									<th class="no-lb" scope="row">수상부문</th>
									<td><%-- ${item.adtAwardType }-${item.adtMedia } --%></td>
								</tr>
								<tr>
									<th class="no-lb" scope="row">수상자</th>
									<td><%-- ${item.adtWinner } --%></td>
								</tr>
							</tbody>
						</table>
					</div>
				</c:forEach>
				
				<c:if test="${not empty fixingList }">
					<div class="half-picture">
						<ul class="clear">
							<c:forEach items="${fixingList }" var="item" varStatus="vs">
								<li>
									<div class="left"><img src="${item.thumb.fileServletThumbnailUrl }" alt="${item.thumb.fileAltText }" style="max-width: 260px;"></div>
									<div class="right">
										<p><strong>${item.arcTitle }</strong></p>
										<p>일자 : ${item.spaUseHours }년</p>
										<a href="${item.permLink }?cp=${spaceSearch.cp}${spaceSearch.queryString}" class="btn white">자세히보기</a>
									</div>
								</li>
							</c:forEach>
						</ul>
					</div>
				</c:if>


				<p class="total trt">
					<strong>${paging.rowTotal}</strong>건
				</p>

				<!-- gallery list -->
				<div class="list_gallery type2">
					<ul class="clearfix">
						<c:forEach items="${paging.result}" var="item" varStatus="vs">
							<li>
								<a href="${APP_PATH}/archive/space/cafe/${archiveCategory.catId }/${item.arcId }?cp=${spaceSearch.cp}${spaceSearch.queryString}" class="img">
									<c:if test="${not empty item.thumb && item.thumb.fileId > 0}">
										<img src="${item.thumb.fileServletThumbnailUrl}" alt="${item.thumb.fileAltText }" onerror="this.src='${CONTEXT_PATH }/design/common/images/asset/noImage400x300.jpg'" />
									</c:if>
								</a>
								<div class="txt_wrap">
									<a href="${APP_PATH}/archive/space/cafe/${archiveCategory.catId }/${item.arcId }?cp=${spaceSearch.cp}${spaceSearch.queryString}" class="tit">${item.arcTitle }</a>
									<span class="location">
										<c:if test="${not empty archiveCategory.catMetaCode1 }">
											${asapro:codeName(item.arcMetaCode1, arcMetaCode1List)}
										</c:if>
										<c:if test="${not empty arcMetaCode2List }">
											${asapro:codeName(item.arcMetaCode2, arcMetaCode2List)}
										</c:if>
									</span>
									<span class="date">이용시간 : ${item.spaUseHours }</span>
								</div>
							</li>
						</c:forEach>
						
						<c:if test="${empty paging.result}">
						<!-- 게시물이 없을 때 -->
						<li class="nodata">게시물이 없습니다.</li>
						</c:if>
					</ul>
				</div>
			
				<!-- paging -->
				<c:out value="${paging.userTextTag}" escapeXml="false"/>
				
			</div>
			<!-- content end -->

<!-- footer -->
<c:import url="${design.footer}" charEncoding="UTF-8" />