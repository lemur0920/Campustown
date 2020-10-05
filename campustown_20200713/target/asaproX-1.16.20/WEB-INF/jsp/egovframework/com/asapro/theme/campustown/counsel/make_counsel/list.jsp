<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="asapro" uri="http://www.asadal.com" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page import="java.util.Date"%>
<%
	Date now = new Date();
%>
<fmt:formatDate value="<%= now %>" pattern="yyyyMMdd" var="now"/>
<!-- header -->
<c:import url="${design.header}" charEncoding="UTF-8" />

<script>
jQuery(function($){
	//달력
	$('.datepicker-ui').datepicker({
		showOn: "focus"
	});
	
	$('#datepicker-img1').on("click", function(){
		$("#startDate").focus();
	});
	
	$('#datepicker-img2').on("click", function(){
		$("#endDate").focus();
	});
	
	$("#counselForm").keypress(function(e) {
		if (e.keyCode == 13){
			search();
		}
	});

	$(".search-btn").click(function(){
		search();
	});
});

function search() {
	let startDt = $("input[name='startDate']").val();
	let endDt = $("input[name='endDate']").val();

	if(startDt === ''){
		alert("시작일을 입력하세요.");
		$("input[name=startDate]").focus();
		return false;
	}
	if(endDt === ''){
		alert("종료일을 입력하세요.");
		$("input[name=endDate]").focus();
		return false;
	}

	if(Number(startDt.replace(/-/gi,"")) > Number(endDt.replace(/-/gi,"")) ){
		alert("시작일이 종료일보다 클 수 없습니다.");
		$("input[name=startDate]").focus();
		return false;
	}

	$("#counselForm").submit();
}

function go_detail(seq, name) {
	var loginId = '${cu.userName}';

	if(name === loginId) {
		location.href = "${APP_PATH}/counsel/make_counsel/view/"+seq;
	} else {
		alert("작성자만 조회 가능합니다.")
	}
}
</script>

<!-- sidebar -->
<c:import url="${design.themeDir}/layout/sidebar.jsp" charEncoding="UTF-8" />

	<form id="counselForm" name="counselForm" method="post" action="<c:url value='${APP_PATH}/counsel/make_counsel/list'/>">
	${SECURITY_TOKEN_TAG}
	<div class="area">
		<div class="bottom-d1 mb20">
			<p><span>시제품이 있으신가요?</span><br />캠퍼스타운 공식 제조 파트너 안암동 캠퍼스타운 창업팀 <span>'볼트앤너트'</span>와 함께 양산을 진행해보세요.<br/>(컨설팅은 무료로 제공되며, 제작비용관련하여 볼트앤너트와 별도 협의가 필요합니다.)
			</p>
		</div>

		<div class="table-con">
			<table>
				<caption>검색</caption>
				<colgroup>
					<col style="width: 150px;">
					<col>
				</colgroup>
				<tbody>
				<tr>
					<th scope="row">등록일</th>
					<td class="databox">
						<span>
							<input name="startDate" id="startDate" class="datepicker-ui" title="시작일을 입력하세요 예시)2020-01-01" autocomplete="off" value="${empty domain.startDate?'':domain.startDate}"/>
							<a href="#n"><img src="${design.resource}/images/sub/databox-icon.png" alt="달력" id="datepicker-img1"></a>
						</span>
						&nbsp;~&nbsp;
						<span>
							<input name="endDate" id="endDate" class="datepicker-ui" title="종료일을 선택하세요 예시)2020-01-01" autocomplete="off" value="${empty domain.endDate?'':domain.endDate}">
							<a href="#n"><img src="${design.resource}/images/sub/databox-icon.png" alt="달력" id="datepicker-img2"></a>
						</span>
					</td>
				</tr> 

				<tr>
					<th scope="row">검색어</th>
					<td class="sub03-select-input">

						<select name="searchDiv" title="검색어 구분을 선택하세요">
							<option value="title" label="제목" <c:if test="${domain.searchDiv eq 'title'}">selected</c:if>></option>
							<option value="productDetail" label="내용" <c:if test="${domain.searchDiv eq 'productDetail'}">selected</c:if>></option>
						</select>

						<span>
							<input type="text" name="searchWord" title="검색어를 입력하세요" value="${empty domain.searchWord?'':domain.searchWord}"/>
							<a href="#n" class="search-btn"><img src="${design.resource}/images/sub/t-searchstyle1-icon01.png" alt="검색"></a>
						</span>
					</td>
				</tr>
				</tbody>
			</table>
		</div>

		<div class="board-table style2 m-noT1 m-noT2 m-noT4 ">
			<table>
				<caption>시제품 제작 및 자문</caption>
				<colgroup>
					<col style="width: 7%">
					<col style="width: 25%">
					<col style="width: auto">
					<col style="width: 13%">
					<col style="width: 15%">
					<col style="width: 13%">
				</colgroup>
				<thead>
				<tr>
					<th scope="col">번호</th>
					<th scope="col">분야</th> <!-- 선택된 모든 분야가 표출-->
					<th scope="col">제목</th>
					<th scope="col">창업팀</th>
					<th scope="col">작성자</th>
					<th scope="col">등록일</th>
				</tr>
				</thead>
				<tbody>
				<c:if test="${empty paging.result}">
					<tr>
						<td colspan="5" class="text-center">등록된 게시글이 없습니다.</td>
					</tr>
				</c:if>

					<c:forEach items="${paging.result}" var="item" varStatus="vs">
						<tr>
							<td>${paging.rowTop - vs.index}</td>
							<td class="tb-txl">
								<c:forEach items="${getUserMakeCounSelDetailList}" var="itemDetail" varStatus="dvs">
									<c:if test="${item.makeCounselSeq eq itemDetail.makeCounselSeq}">
										<c:choose>
											<c:when test="${itemDetail.type eq 'idea'}">
												<c:forEach items="${Idea}" var="idea" varStatus="ideaVs">
													<c:if test="${idea.codeValue eq itemDetail.makeCounselRealm}">
														${idea.codeName}
													</c:if>
												</c:forEach>
												<%--												<c:if test="${!ideaVs.last}">,</c:if> --%>
											</c:when>
											<c:when test="${itemDetail.type eq 'prod'}">
												<c:forEach items="${Prod}" var="prod" varStatus="prodVs">
													<c:if test="${prod.codeValue eq itemDetail.makeCounselRealm}">
														${prod.codeName}
													</c:if>
												</c:forEach>
												<%--												<c:if test="${!prodVs.last}">,</c:if> --%>
											</c:when>
											<c:when test="${itemDetail.type eq 'prot'}">
												<c:forEach items="${Prot}" var="prot" varStatus="protVs">
													<c:if test="${prot.codeValue eq itemDetail.makeCounselRealm}">
														${prot.codeName}
													</c:if>
												</c:forEach>
												<%--												<c:if test="${!protVs.last}">,</c:if> --%>
											</c:when>
										</c:choose>
									</c:if>
								</c:forEach>
							</td>
							<td onclick="go_detail(${item.makeCounselSeq},'${item.regName}'); return false;">${item.title}</td>
							<td>${item.teamName}</td>
							<td>${item.regName}
								<c:if test="${not empty item.regId}">
									<a href="${APP_PATH}/mypage/note/insert?noteReceiverId=${item.regId}&reply=true" class="noteWrite" title="쪽지쓰기"><img src="${design.resource }/images/sub/sub01_limg33.gif" alt="쪽지"></a>
								</c:if>
							</td>
							<td><fmt:formatDate value="${item.regDate}" pattern="yyyy.MM.dd"/></td>
						</tr>
					</c:forEach>

				</tbody>
			</table>
		</div>
		<div class="btn-area txr">
			<a href="${APP_PATH}/counsel/make_counsel/write" class="btn02 fc-bg-blue">시제품 제작 및 자문 의뢰</a>
			<!--<a class="go-next style2" href="#">신청하기</a>-->
		</div>

	</div>

	<!-- paging -->
	<div class="paging">
		<c:out value="${paging.userImageTag}" escapeXml="false"/>
	</div>
	</form>

<!-- footer -->
<c:import url="${design.footer}" charEncoding="UTF-8" />