<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="asapro" uri="http://www.asadal.com" %>

<script>
jQuery(function($){
	
	//콘텐츠검색 - 검색버튼
	$('.contentSearchBtnLayer').on('click', function(){
		var params = $('#contentSearch').serialize();
		$('#contentList').load('${ADMIN_PATH}/content/searchLayer.do',params);	
	});
	
	//sv 엔터 이벤트 막기
	$('input[type="text"]').keydown(function() {
		if (event.keyCode === 13) {
			event.preventDefault();
		}
	});
	
	//콘텐츠검색 - 페이징 버튼
	$('.contentPasing .page-link').on('click', function(e){
		e.preventDefault();
		$('#contentList').load($(this).attr('href'));	
	});
	
	//콘텐츠 선택시 input 셋팅
	$('.contentSelect').on('click', function(e){
		e.preventDefault();
		$('#contentRoot').val($(this).data('contentroot'));
		$('#contentTitle').val($(this).data('contenttitle'));
		$.magnificPopup.close();
	});

});
</script>

<!-- ============================= 메뉴별 컨텐츠 ============================ -->

<!-- 검색폼 -->
<div class="row">
    <div class="col-sm-12">
        <div class="card m-b-15">
            <div class="card-body">
	            <form:form modelAttribute="contentSearch" cssClass="form-inline" action="${ADMIN_PATH}/content/searchLayer.do" method="get">
					<div class="form-row mr-auto mt-2 ">
						<label class="mr-sm-2 mb-2">정렬조건</label>
						<form:select path="pageSize" cssClass="form-control mr-sm-2 mb-2" title="페이지크기" >
							<form:option value="10">10개씩</form:option>
							<form:option value="20">20개씩</form:option>
							<form:option value="40">40개씩</form:option>
						</form:select>
						<form:select path="sortOrder" cssClass="form-control mr-sm-2 mb-2" title="정렬기준">
							<form:option value="CONTENT_LAST_MODIFIED" label="수정록일" />
							<form:option value="CONTENT_TITLE" label="콘텐츠제목" />
						</form:select>
						<form:select path="sortDirection" cssClass="form-control mr-sm-2 mb-2" title="정렬방향">
							<form:option value="DESC" label="내림차순" />
							<form:option value="ASC" label="오름차순" />
						</form:select>
					</div>
					<div class="form-row mt-2 " >
						<label for="sc" class="mr-sm-2 mb-2">검색조건</label>
						<form:select path="sc" cssClass="form-control mr-sm-2 mb-2" title="검색조건">
							<option value="">전체</option>
							<form:option value="CONTENT_TITLE" label="콘텐츠제목" />
							<form:option value="CONTENT" label="콘텐츠내용" />
						</form:select>	
						<form:input path="sv" cssClass="form-control mr-sm-2 mb-2" title="검색어" placeholder="Search"/>
						<button class="btn btn-outline-success waves-effect mb-2 contentSearchBtnLayer" type="button">검색</button>
					</div>
				</form:form>
            </div>
        </div>
    </div>
</div>
<!-- //검색폼 -->

<!-- 목록 -->
<div class="row">
	<div class="col-sm-12">
		<div class="card m-b-30">
			<div class="card-body">
			
				<!-- alert maeeage -->
				<c:import url="/WEB-INF/jsp/egovframework/com/asapro/admin/include/message.jsp" charEncoding="UTF-8" />
				<div class="table-responsive">
					<table class="table table-striped table-hover ">
						<colgroup>
							<col style="width: 70px;" /><!-- 번호 -->
							<col  /><!-- 콘텐츠명 -->
							<col style="width: 10%;" /><!-- 작업자 -->
							<col style="width: 10%;" /><!-- 수정일 -->
							<col style="width: 8%;" /><!-- 상태 -->
						</colgroup>
						<thead>
							<tr>
								<th>번호</th>
								<th>콘텐츠제목</th>
								<th>작업자</th>
								<th>수정일</th>
								<th>상태</th>
							</tr>
						</thead>
						<tbody>
							<c:if test="${empty paging.result}">
								<tr>
									<td colspan="5" class="text-center">데이터가 없습니다.</td>
								</tr>
							</c:if>
							<c:forEach items="${paging.result}" var="item" varStatus="vs">
								<tr>
									<td>${paging.rowTop - vs.index}</td>
									<td><a href="#non" class="contentSelect" data-contentroot="${item.contentRoot}" data-contenttitle="${item.contentTitle}"><strong>${item.contentTitle}</strong></a></td>
									<td>${item.contentLastWorker.adminId}</td>
									<td>
										<fmt:formatDate value="${item.contentLastModified}" pattern="yyyy-MM-dd" />
									</td>
									<td>
										${item.contentStatus}
									</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
				
				<div class="form-inline">
					<div>
						<span>총 ${paging.rowTotal}건의 데이터가 있습니다.</span>
					</div>
				</div>

				<!-- paging -->
				<div class="contentPasing">
					<c:out value="${paging.adminTextTag}" escapeXml="false"/>
				</div>
				
			</div>
		</div>
	</div>
</div><!-- 목록 -->

<!-- ============================= //메뉴별 컨텐츠 ============================ -->