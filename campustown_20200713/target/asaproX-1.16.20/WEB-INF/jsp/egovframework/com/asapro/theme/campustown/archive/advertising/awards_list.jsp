<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="asapro" uri="http://www.asadal.com" %>

<!-- header -->
<c:import url="${design.header}" charEncoding="UTF-8" />

<script>
jQuery(function($){
	//폼검색
	$('.search-btn').on('click', function(e){
		e.preventDefault();
		$('#advertisingSearch').submit();
	});
	
	<%-- 
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
	});--%>
	
});
</script>

<!-- sidebar -->
<c:import url="${design.themeDir}/layout/sidebar.jsp" charEncoding="UTF-8" />

<%-- ============================= 메뉴별 컨텐츠 ============================ --%>

	<div class="noticeboard mt0">
		<form:form modelAttribute="advertisingSearch" action="${APP_PATH}/archive/${archiveCategory.catCustomType}/${archiveCategory.catId}" method="get">
			
			<form:hidden path="metaCode1"/>
			<%-- <span class="title">주제별 검색</span>
			<c:if test="${not empty arcMetaCode1List || not empty arcMetaCode2List || not empty arcMetaCode3List}">
				<c:if test="${not empty arcMetaCode1Info }">
					<span class="wid180">
						<form:select path="metaCode1" title="${arcMetaCode1Info.catName}">
							<option value="">${arcMetaCode1Info.catName}</option>
							<form:options items="${arcMetaCode1List}" itemLabel="codeName" itemValue="codeId"/>
						</form:select>
					</span>
				</c:if>
				<c:if test="${not empty arcMetaCode2Info }">
					<span class="wid180">
						<form:select path="metaCode2" title="${arcMetaCode2Info.catName}">
							<option value="">${arcMetaCode2Info.catName}</option>
							<form:options items="${arcMetaCode2List}" itemLabel="codeName" itemValue="codeId"/>
						</form:select>
					</span>
				</c:if>
				<c:if test="${not empty arcMetaCode3Info }">
					<span class="wid180">
						<form:select path="metaCode3" title="${arcMetaCode3Info.catName}">
							<option value="">${arcMetaCode3Info.catName}</option>
							<form:options items="${arcMetaCode3List}" itemLabel="codeName" itemValue="codeId"/>
						</form:select>
					</span>
				</c:if>
			</c:if> --%>
			<span class="title">연도별 보기</span>
			<span class="wid180">
				<form:select path="adtManufactureYear">
					<c:forEach items="${yearList }" var="item" varStatus="vs">
						<form:option label="${item } 년" value="${item }"/>
					</c:forEach>
				</form:select>
				<a href="#n" class="search-btn">이동</a>
			</span>
			<%-- <span>
				<form:select class="" path="sc" title="검색분류를 선택하세요">
					<option value="">전체</option>
					<form:option value="ARC_TITLE" label="제목" />
					<form:option value="ARC_CONTENT" label="내용" />
				</form:select>
			</span>
			<span>
				<form:input path="sv" placeholder="검색어를 입력하세요" title="검색어 입력"/>
				<a href="#n" class="search-btn">검색</a>
			</span> --%>
		</form:form>
	</div>
	
	<%-- <div class="half-picture">
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
	</div> --%>
	
	<c:forEach items="${selectedList }" var="item" varStatus="vs">
		<div class="input-table mt30">
			<table>
				<caption>${advertisingSearch.adtManufactureYear }년 대한민국 공익광고제 대상</caption>
				<colgroup>
					<col style="width:100%;">
				</colgroup>
				<tbody>
					<tr>
						<td class="no-lb"><strong class="ft-18px">${advertisingSearch.adtManufactureYear }년 대한민국 공익광고제 대상</strong></td>
					</tr>
					<tr>
						<td class="no-lb td-bigpaid">
							<a href="${item.permLink }?cp=${advertisingSearch.cp}${advertisingSearch.queryString}">
								<img src="${item.thumb.fileServletUrl }" alt="${item.thumb.fileAltText }" width="780px">
							</a>
						</td>
					</tr>
				</tbody>
			</table>
		</div>
		<div class="input-table mt30">
			<table>
				<caption>공익광고제역대수상작</caption>
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
						<div class="left"><img src="${item.thumb.fileServletThumbnailUrl }" alt="${item.thumb.fileAltText }" width="206"></div>
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
	
	<div class="sub-tab3 mt30">
		<ul class="clear">
			<c:forEach items="${arcMetaCode1List }" var="code" varStatus="vs">
				<li class="<c:if test="${advertisingSearch.metaCode1 eq code.codeId }">on</c:if>">
					<a href="?metaCode1=${code.codeId }&adtManufactureYear=${advertisingSearch.adtManufactureYear}">${code.codeName }</a>
				</li>
			</c:forEach>
		</ul>
	</div>
	
		
<%-- 	<div class="page-box"><b>${paging.currentPage} Page</b> / ${paging.totalPage}Page</div> --%>

	<div class="list-table">
		<ul class="clear">
		<c:forEach items="${paging.result }" var="item" varStatus="vs">
			<li>
				<div class="left">
					<a href="${item.permLink }?cp=${advertisingSearch.cp}${advertisingSearch.queryString}">
						<img src="${item.thumb.fileServletThumbnailUrl }" alt="${item.thumb.fileAltText }" width="170" height="150">
					</a>
				</div>
				<div class="right">
					<dl>
						<dt>수상부문</dt>
						<dd>${item.adtAwardType }-${item.adtMedia }</dd>
					</dl>
					<dl>
						<dt>제목</dt>
						<dd>${item.arcTitle }</dd>
					</dl>
					<dl>
						<dt>수상자</dt>
						<dd>${item.adtWinner }</dd>
					</dl>
				</div>
			</li>
		</c:forEach>
		</ul>
	</div>

	
		
	<!-- paging -->
	<%-- <c:out value="${paging.userImageTag}" escapeXml="false"/> --%>
		
<%-- ============================= 메뉴별 컨텐츠 끝 ============================ --%>


<!-- footer -->
<c:import url="${design.footer}" charEncoding="UTF-8" />