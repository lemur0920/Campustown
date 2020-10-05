<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="asapro" uri="http://www.asadal.com" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!-- header -->

<c:import url="${design.header}" charEncoding="UTF-8" />

<script>
jQuery(function($){
 	
  //메타코드 1 선택에 따라 메타코드 2 목록 변경
	/* $('#metaCode1').on('change',function(e){
		e.preventDefault();
		var catId = $('#metaCode1').find(':selected').val();
		
		if(catId == null || catId == ''){
			//alert('선택된 코드가 없습니다.');
			$('#metaCode2').empty();
			$('#metaCode2').append("<option value=''>지역(시군구)</option>");
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
			$('#metaCode2').empty();
			$('#metaCode2').append("<option value=''>지역(시군구)</option>");
			if(result.length > 0){

				$.each(result, function(index, item){
					//alert(item.codeName);
					$('#metaCode2').append("<option value='"+ item.codeId +"'>"+ item.codeName +"</option>");
				});
			}
			
		}).fail(function(result){
			alert('코드를 불러오지 못하였습니다.[fail]');
		});
	}); */
	
 // 검색
	$('.searchBtn').on('click', function(e){
		e.preventDefault();
		$('#spaceSearch').submit();
	});
	
});
</script>
	
<!-- sidebar -->
<c:import url="${design.themeDir}/layout/sidebar.jsp" charEncoding="UTF-8" />
	<form:form id="spaceForm" modelAttribute="spaceSearch" action="${APP_PATH}/archive/space/move_space" method="get">
		<%-- 
		<form:input path="metaCode1"/>
		<form:input path="metaCode2"/>
		<form:input path="metaCode3"/>
		<form:input path="arcUse"/>
		 --%>
		<div class="search-line2 inbtn mgt25 clearfix">
			<%-- 
			<p>
				<form:select path="areaId" title="검색조건 선택">
					<option value="">지역</option>
					<form:options items="${sigunguCodeList}" itemLabel="codeName" itemValue="codeId"/>
				</form:select>
			</p>
			 --%>
			<c:if test="${not empty arcMetaCode1Info }">
				<p>
					<form:select path="metaCode1" title="${arcMetaCode1Info.catName}">
						<%-- <option value="">${arcMetaCode2Info.catName}</option> --%>
						<%-- <option value="">${arcMetaCode1Info.catName}</option> --%>
						<option value="">지역</option>
						<form:options items="${arcMetaCode1List}" itemLabel="codeName" itemValue="codeId"/>
					</form:select>
				</p>									
			</c:if>
			
			<c:if test="${not empty arcMetaCode2Info }">
				<p>
					<form:select path="metaCode2" title="${arcMetaCode2Info.catName}">
						<%-- <option value="">${arcMetaCode2Info.catName}</option> --%>
						<form:option value="">지역</form:option>
						<form:options items="${arcMetaCode2List}" itemLabel="codeName" itemValue="codeId"/>
					</form:select>
				</p>									
			</c:if>
			
			<c:if test="${not empty arcMetaCode3Info }">
				<p>
					<form:select path="metaCode3" title="${arcMetaCode3Info.catName}">
						<%-- <option value="">${arcMetaCode2Info.catName}</option> --%>
						<option value="">${arcMetaCode3Info.catName}</option>
						<form:options items="${arcMetaCode3List}" itemLabel="codeName" itemValue="codeId"/>
					</form:select>
				</p>									
			</c:if>
			&nbsp;&nbsp;
			<p>
				<form:select path="univId" title="캠퍼스타운">
					<option value="">캠퍼스타운</option>
					<form:options items="${departmentList}" itemLabel="depName" itemValue="depId"/>
				</form:select>
			</p>
			<p>
				<form:select path="sc" title="검색조건 선택">
					<form:option value="">구분</form:option>
					<form:option value="arcTitle">시설공간 명</form:option>
				</form:select>
			</p>
			&nbsp;&nbsp;
			<p class="clearfix">
				<form:input path="sv" title="검색어 입력" />
				<a href="javascript:{}"
			   		onclick="document.getElementById('spaceForm').submit(); return false;" id="srcBtn">
					<img src="${design.resource }/images/sub/t-searchstyle1-icon01_01.gif" alt="조회">
				</a>
			</p>
		</div>
	</form:form>
	<div class="mgt25 total">
		Total : ${paging.rowTotal}
	</div>
	<!-- startup list -->
	
	<c:if test="${empty paging.result}">
		<tr>
			<td colspan="4" class="text-center">등록된 게시글이 없습니다.</td>
		</tr>
	</c:if>
	
	<div class="img-list">
		<ul class="clearfix">
		<c:forEach items="${paging.result}" var="item" varStatus="vs">
			<li>
				<a href="${item.permLink }?cp=${spaceSearch.cp}${spaceSearch.queryString}">
					<c:if test="${not empty item.thumb && item.thumb.fileId > 0}">
						<img src="${item.thumb.fileServletThumbnailUrl }" alt="${item.thumb.fileAltText }" style="height:143px"
							onerror="this.src='${design.resource}/images/sub/imhtext-list_img02.jpg'">
					</c:if>
					<c:if test="${empty item.thumb}">
						<img src="${design.resource}/images/sub/imhtext-list_img02.jpg" alt="대체 이미지" style="height:143px">
					</c:if>
				</a>
				
				
				<%-- 
				<a href="${item.permLink }?cp=${spaceSearch.cp}${spaceSearch.queryString}">
					<img src="${item.thumb.fileServletUrl }" alt="${item.thumb.fileAltText }" style="max-width: 780px;">
				</a>
				 --%>
				<div>
					<div class="text-p">
						<p><span class="tit">${asapro:codeName(item.arcMetaCode2, arcMetaCode2List)} ${asapro:depName(item.arcUnivId,departmentList)}</span></p>
						<p><span>${item.arcTitle }</span></p>
						<a href="${item.permLink }?cp=${spaceSearch.cp}${spaceSearch.queryString}"><span>자세히보기</span></a>
					</div>
				</div>
			</li>
		</c:forEach>
		</ul>
	</div>	
	<!-- paging -->
	<c:out value="${paging.userImageTag}" escapeXml="false"/>
	
	<!-- content end -->

<!-- footer -->
<c:import url="${design.footer}" charEncoding="UTF-8" />