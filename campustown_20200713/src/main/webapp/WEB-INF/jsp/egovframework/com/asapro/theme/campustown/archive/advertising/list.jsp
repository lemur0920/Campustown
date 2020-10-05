<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="asapro" uri="http://www.asadal.com" %>

<!-- header -->
<c:import url="${design.header}" charEncoding="UTF-8" />

<script>
jQuery(function($){
	//폼검색
	$('.searchBtn').on('click', function(e){
		e.preventDefault();
		$('#advertisingSearch').submit();
	});
	
});
</script>

<!-- sidebar -->
<c:import url="${design.themeDir}/layout/sidebar.jsp" charEncoding="UTF-8" />

<%-- ============================= 메뉴별 컨텐츠 ============================ --%>

<div class="RightCWarp">
	<div class="s43buttn wdes33">
		<ul>
			<c:forEach items="${arcMetaCode1List }" var="item">
				<li class="<c:if test="${item.codeId eq advertisingSearch.metaCode1 }">on</c:if>"><a href="?metaCode1=${item.codeId }">${item.codeName }</a></li>
			</c:forEach>
		</ul>
		<div class="clear"></div>
	</div>
	<div class="s1marg30">
		<form:form modelAttribute="advertisingSearch" action="" method="get">
			<form:hidden path="metaCode1"/>
			
			<table class="tablest1 btmso">
				<caption>아카이브 검색</caption>
				<colgroup>
					<col style="width:25%;" />
					<col style="width:25%;" />
					<col style="width:25%;" />
					<col style="width:25%;" />
				</colgroup>
				<tbody>
					<c:if test="${advertisingSearch.metaCode1 ne 'outdoor' }">
					<tr>
						<th>제작년도</th>
						<td class="lplstud"><form:input path="arcYear" /></td>
						<c:choose>
							<c:when test="${advertisingSearch.metaCode1 ne 'radio' }">
								<td></td>
								<td></td>
							</c:when>
							<c:when test="${advertisingSearch.metaCode1 eq 'radio' }">
								<th>성우</th>
								<td class="lplstud"><form:input path="arcActor" /></td>
							</c:when>
						</c:choose>
					</tr>
					</c:if>
					<c:if test="${advertisingSearch.metaCode1 eq 'print' or advertisingSearch.metaCode1 eq 'other' }">
					<tr>
						<c:choose>
							<c:when test="${advertisingSearch.metaCode1 eq 'print' }">
								<th>구분</th>
								<%--<td class="lplstud"><form:input path="metaCode2" /></td> --%>
								<td class="lplstud">
									<form:select path="metaCode2" title="${arcMetaCode2Info.catName}">
										<option value="">${arcMetaCode2Info.catName}</option>
										<form:options items="${arcMetaCode2List}" itemLabel="codeName" itemValue="codeId"/>
									</form:select>
								</td>
								<th>매체</th>
								<td class="lplstud"><form:input path="arcMedia" /></td>
							</c:when>
							<c:when test="${advertisingSearch.metaCode1 eq 'other' }">
								<th>매체</th>
								<td class="lplstud"><form:input path="arcMedia" /></td>
								<td></td>
								<td></td>
							</c:when>
						</c:choose>
					</tr>
					</c:if>
					<tr>
						<th>광고주</th>
						<td class="lplstud"><form:input path="arcAdvertiser" /></td>
						<c:choose>
							<c:when test="${advertisingSearch.metaCode1 ne 'outdoor' }">
								<th>제품명</th>
								<td class="lplstud"><form:input path="arcProduct" /></td>
							</c:when>
							<c:when test="${advertisingSearch.metaCode1 eq 'outdoor' }">
								<td></td>
								<td></td>
							</c:when>
						</c:choose>
					</tr>
					<tr>
						<th>제목+내용</th>
						<td class="lplstud wd99s" colspan="3"><form:input path="sv" /></td>
					</tr>
				</tbody>
			</table>
		</form:form>
	</div>
	<div class="blgg1 searchBtn"><a href="#n">검색</a></div>
	
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
	
	<div class="s1marg30">
		<table class="tablest5">
			<caption>영상광고</caption>
			<colgroup>
				<col style="width:10%;" /><!-- 번호 -->
				<c:if test="${advertisingSearch.metaCode1 ne 'outdoor' }">
					<col style="width:10%;" /><!-- 년도 -->
				</c:if>
				<c:if test="${advertisingSearch.metaCode1 eq 'print' }">
					<col /><!-- 구분 -->
				</c:if>
				<c:if test="${advertisingSearch.metaCode1 eq 'print' or advertisingSearch.metaCode1 eq 'other' }">
					<col /><!-- 매체 -->
				</c:if>
				<c:if test="${advertisingSearch.metaCode1 eq 'video' or advertisingSearch.metaCode1 eq 'radio' or advertisingSearch.metaCode1 eq 'outdoor' }">
					<col /><!-- 제목 -->
				</c:if>
				<col /><!-- 광고주 -->
				<c:if test="${advertisingSearch.metaCode1 ne 'outdoor' }">
					<col /><!-- 제품명 -->
				</c:if>
				<c:if test="${advertisingSearch.metaCode1 eq 'radio' }">
					<col style="width:10%;" /><!-- 성우 -->
				</c:if>
				<c:if test="${advertisingSearch.metaCode1 eq 'print' or advertisingSearch.metaCode1 eq 'outdoor' or advertisingSearch.metaCode1 eq 'media' or advertisingSearch.metaCode1 eq 'other' }">
					<col /><!-- 내용 -->
				</c:if>
				<c:if test="${advertisingSearch.metaCode1 eq 'media' }">
					<col /><!-- 기간 -->
				</c:if>
			</colgroup>
			<thead>
				<tr>
				<th scope="col">번호</th>
				<c:if test="${advertisingSearch.metaCode1 ne 'outdoor' }">
					<th scope="col">년도</th>
				</c:if>
				<c:if test="${advertisingSearch.metaCode1 eq 'print' }">
					<th scope="col">구분</th>
				</c:if>
				<c:if test="${advertisingSearch.metaCode1 eq 'print' or advertisingSearch.metaCode1 eq 'other' }">
					<th scope="col">매체</th>
				</c:if>
				<c:if test="${advertisingSearch.metaCode1 eq 'video' or advertisingSearch.metaCode1 eq 'radio' or advertisingSearch.metaCode1 eq 'outdoor' }">
					<th scope="col">제목</th>
				</c:if>
				<th scope="col">광고주</th>
				<c:if test="${advertisingSearch.metaCode1 ne 'outdoor' }">
					<th scope="col">제품명</th>
				</c:if>
				<c:if test="${advertisingSearch.metaCode1 eq 'radio' }">
					<th scope="col">성우</th>
				</c:if>
				<c:if test="${advertisingSearch.metaCode1 eq 'print' or advertisingSearch.metaCode1 eq 'outdoor' or advertisingSearch.metaCode1 eq 'media' or advertisingSearch.metaCode1 eq 'other' }">
					<th scope="col">내용</th>
				</c:if>
				<c:if test="${advertisingSearch.metaCode1 eq 'media' }">
					<th scope="col">기간</th>
				</c:if>
				</tr>
			</thead>
			<tbody>
				<c:if test="${empty paging.result}">
					<tr>
						<td colspan="10" class="text-center">등록된 자료가 없습니다.</td>
					</tr>
				</c:if>
				
				<c:forEach items="${paging.result }" var="item" varStatus="vs">
					<tr>
						<td>${paging.rowTop - vs.index}</td>
						<c:if test="${advertisingSearch.metaCode1 ne 'outdoor' }">
							<td>${item.arcYear }</td>
						</c:if>
						<c:if test="${advertisingSearch.metaCode1 eq 'print' }">
							<td>${asapro:codeName(item.arcMetaCode2, arcMetaCode2List)}</td>
						</c:if>
						<c:if test="${advertisingSearch.metaCode1 eq 'print' or advertisingSearch.metaCode1 eq 'other' }">
							<td>${item.arcMedia }</td>
						</c:if>
						<c:if test="${advertisingSearch.metaCode1 eq 'video' or advertisingSearch.metaCode1 eq 'radio' or advertisingSearch.metaCode1 eq 'outdoor' }">
							<td>${item.arcTitle }</td>
						</c:if>
						<td>${item.arcAdvertiser }</td>
						<c:if test="${advertisingSearch.metaCode1 ne 'outdoor' }">
							<td>${item.arcProduct }</td>
						</c:if>
						<c:if test="${advertisingSearch.metaCode1 eq 'radio' }">
							<td>${item.arcActor }</td>
						</c:if>
						<c:if test="${advertisingSearch.metaCode1 eq 'print' or advertisingSearch.metaCode1 eq 'outdoor' or advertisingSearch.metaCode1 eq 'media' or advertisingSearch.metaCode1 eq 'other' }">
							<td>${item.arcTitle }</td>
						</c:if>
						<c:if test="${advertisingSearch.metaCode1 eq 'media' }">
							<td>${item.arcPeriod }</td>
						</c:if>
					</tr>
				</c:forEach>
				
			</tbody>
		</table>
		
		<!-- paging -->
		<c:out value="${paging.userImageTag}" escapeXml="false"/>
	</div>
</div>
<%-- ============================= 메뉴별 컨텐츠 끝 ============================ --%>


<!-- footer -->
<c:import url="${design.footer}" charEncoding="UTF-8" />