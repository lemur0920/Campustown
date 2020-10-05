<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="asapro" uri="http://www.asadal.com" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page import="java.util.Date"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%
int nowYear = Integer.parseInt(new SimpleDateFormat("yyyy").format(new Date()) );
%>
<c:set var="nowYear" value="<%= nowYear %>" />

<!-- header -->
<c:import url="${design.header}" charEncoding="UTF-8" />

<script>
jQuery(function($){
	
	
});
</script>
	
<!-- sidebar -->
<c:import url="${design.themeDir}/layout/sidebar.jsp" charEncoding="UTF-8" />

		<!-- content -->
		
		<div class="search-box2">
			<div>
				<form:form modelAttribute="policySearch" action="${APP_PATH}/archive/policy/${archiveCategory.catId }" method="get">
					<div class="table tb_3">
						<table>
							<caption>주제별 검색</caption>
							<colgroup>
								<col style="width:110px;">
								<col style="">
							</colgroup>
							<tbody>
								<tr>
									<th rowspan="2">주제선택</th>
									<td>
										<p class="chk_set2 chks clearfix">
										
											<c:if test="${not empty arcMetaCode1List }">
												<c:forEach items="${arcMetaCode1List }" var="mCode1" varStatus="vs"> 
													<c:set var="chkday" value="" />
													<c:forEach items="${selectMetaCode1Array }" var="meta1" >
														<c:if test="${meta1 eq mCode1.codeId }">
															<c:set var="chkday" value="checked" />
														</c:if>
													</c:forEach>
													<input type="checkbox" name="metaCode1" id="metaCode1${vs.count }" value="${mCode1.codeId }" ${chkday } />
													<label for="metaCode1${vs.count }"> ${mCode1.codeName }</label>
												</c:forEach>
											</c:if>
										</p>
									</td>
								</tr>
							</tbody>
						</table>
					</div>
					<p class="tct"><button type="submit" class="btn t_sch cfocus">검색</button></p>
				</form:form>
			</div>
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
								<a href="${item.permLink }?cp=${advertisingSearch.cp}${advertisingSearch.queryString}">
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
							<td>${item.adtAwardType }-${item.adtMedia }</td>
						</tr>
						<tr>
							<th class="no-lb" scope="row">수상자</th>
							<td>${item.adtWinner }</td>
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
								<p>일자 : ${item.adtManufactureYear }년</p>
								<a href="${item.permLink }?cp=${advertisingSearch.cp}${advertisingSearch.queryString}" class="btn white">자세히보기</a>
							</div>
						</li>
					</c:forEach>
				</ul>
			</div>
		</c:if>

		<ul class="list_data">
			<c:forEach items="${paging.result}" var="item" varStatus="vs">
				<li>
					<a href="${item.permLink }?cp=${policySearch.cp}${policySearch.queryString}">
						<strong>주제 : </strong> ${asapro:codeName(item.arcMetaCode1, arcMetaCode1List)}<br>
						<strong>제목 : </strong> ${item.arcTitle }<br>
						<strong>키워드 : </strong> ${item.arcTag }
					</a>
				</li>
			</c:forEach>
			
			<c:if test="${empty paging.result}">
				<!-- 게시물이 없을 때 -->
				<li class="nodata">게시물이 없습니다.</li>
			</c:if>
			
		</ul>
	
		<!-- paging -->
		<c:out value="${paging.userImageTag}" escapeXml="false"/>
		
		<!-- content end -->

<!-- footer -->
<c:import url="${design.footer}" charEncoding="UTF-8" />