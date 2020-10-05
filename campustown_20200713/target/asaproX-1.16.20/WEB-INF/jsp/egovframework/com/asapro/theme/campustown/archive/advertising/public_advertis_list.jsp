<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="asapro" uri="http://www.asadal.com" %>
<%@ page import="java.util.Date"%>
<jsp:useBean id="toDay" class="java.util.Date" />

<!-- header -->
<c:import url="${design.header}" charEncoding="UTF-8" />

<script>
jQuery(function($){
	//폼검색
	$('.search-btn').on('click', function(e){
		e.preventDefault();
		$('#advertisingSearch').submit();
	});
	
	
	//메타코드 2 선택에 따라 메타코드 3 목록 변경
	$('#metaCode2').on('change',function(e){
		e.preventDefault();
		var catId = $('#metaCode2').find(':selected').val();
		
		if(catId == null || catId == ''){
			//alert('선택된 코드가 없습니다.');
			$('#metaCode3').empty();
			$('#metaCode3').append("<option value=''>공익광고 소분류</option>");
			return;
		}

		$.ajax({
			url : '${APP_PATH}/code${API_PATH}/item/jsonList'
			, type : 'get'
			, data : {
				catId : catId
			}
			, dataType: 'json'
		}).done(function(result){
			$('#metaCode3').empty();
			$('#metaCode3').append("<option value=''>공익광고 소분류</option>");
			if(result.length > 0){

				$.each(result, function(index, item){
					//alert(item.codeName);
					$('#metaCode3').append("<option value='"+ item.codeId +"'>"+ item.codeName +"</option>");
				});
			}
			
		}).fail(function(result){
			alert('코드를 불러오지 못하였습니다.[fail]');
		});
	});
	
});
</script>

<!-- sidebar -->
<c:import url="${design.themeDir}/layout/sidebar.jsp" charEncoding="UTF-8" />

<%-- ============================= 메뉴별 컨텐츠 ============================ --%>

	<div class="noticeboard mt30">
		<form:form modelAttribute="advertisingSearch" action="${APP_PATH}/archive/${archiveCategory.catCustomType}/${archiveCategory.catId}" method="get">
			<p class="top">
				<span class="title">주제별 검색</span>
				<form:hidden path="metaCode1"/>
				<c:if test="${not empty arcMetaCode1List || not empty arcMetaCode2List || not empty arcMetaCode3List}">
					<%-- <c:if test="${not empty arcMetaCode1Info }">
						<span class="wid180">
							<form:select path="metaCode1" title="${arcMetaCode1Info.catName}">
								<option value="">${arcMetaCode1Info.catName}</option>
								<form:options items="${arcMetaCode1List}" itemLabel="codeName" itemValue="codeId"/>
							</form:select>
						</span>
					</c:if> --%>
					<c:if test="${not empty arcMetaCode2Info }">
						<span class="wid180">
							<form:select path="metaCode2" title="${arcMetaCode2Info.catName}">
								<option value="">${arcMetaCode2Info.catName}</option>
								<form:options items="${arcMetaCode2List}" itemLabel="codeName" itemValue="codeId"/>
							</form:select>
						</span>
					</c:if>
				</c:if>
				<%-- <c:if test="${not empty arcMetaCode3Info }">
				</c:if> --%>
				<span class="wid180">
					<form:select path="metaCode3" title="공익광고 소분류">
						<option value="">공익광고 소분류</option>
						<form:options items="${arcMetaCode3List}" itemLabel="codeName" itemValue="codeId"/>
					</form:select>
				</span>
			</p>
			<p class="bom">
				<span>
					<form:select path="adtManufactureYear">
						<option label="전체" value=""/>
						<c:forEach items="${yearList }" var="item" varStatus="vs">
							<form:option label="${item } 년" value="${item }"/>
						</c:forEach>
					</form:select>
				</span>
				<span>
					<form:select class="" path="sc" title="검색분류를 선택하세요">
						<option value="">전체</option>
						<form:option value="ARC_TITLE" label="제목" />
						<%-- <form:option value="ARC_CONTENT" label="내용" /> --%>
					</form:select>
				</span>
				<span>
					<form:input path="sv" placeholder="검색어를 입력하세요" title="검색어 입력"/>
					<a href="#n" class="search-btn">검색</a>
				</span>
			</p>
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
	
	<div class="page-box"><b>${paging.currentPage} Page</b> / ${paging.totalPage}Page</div>
	<div class="picture-list">
		<ul class="clear">
			<c:forEach items="${paging.result }" var="item" varStatus="vs">
				<li><a href="${item.permLink }?cp=${advertisingSearch.cp}${advertisingSearch.queryString}">
					<img src="${item.thumb.fileServletThumbnailUrl }" alt="${item.thumb.fileAltText }" <c:if test="${advertisingSearch.metaCode1 eq 'broadcast' }">style="height: 202px;"</c:if> >
						<div class="hover-on"><p><span>${item.arcTitle }</span></p></div>
					</a>
				</li>
			</c:forEach>
		</ul>
	</div>
		
		
	<!-- paging -->
	<c:out value="${paging.userImageTag}" escapeXml="false"/>
		
<%-- ============================= 메뉴별 컨텐츠 끝 ============================ --%>


<!-- footer -->
<c:import url="${design.footer}" charEncoding="UTF-8" />