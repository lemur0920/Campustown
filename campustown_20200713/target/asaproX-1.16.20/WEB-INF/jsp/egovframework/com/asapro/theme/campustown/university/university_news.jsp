<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="asapro" uri="http://www.asadal.com" %>
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
	
	
	//폼검색
	$('.search-btn').on('click', function(e){
		e.preventDefault();
		$('#boardArticleSearch').submit();
	});
});
</script>
<c:if test="${not empty messageCode}"><script>alert('${messageCode}');</script></c:if>

<!-- sidebar -->
<c:import url="${design.themeDir}/layout/sidebar3.jsp" charEncoding="UTF-8" />

	<!-- alert maeeage -->
	<c:import url="/WEB-INF/jsp/egovframework/com/asapro/admin/include/message.jsp" charEncoding="UTF-8" />
			
	<!-- content -->
	<div id="subl-center">
		<c:import url="${design.themeDir}/layout/tab3depth.jsp" charEncoding="UTF-8" />
		<br/>
		<div class="table-con">
			<form:form modelAttribute="boardArticleSearch" action="${APP_PATH}/university/university_news" method="get">
				<form:hidden path="baCommSelec"/>
				<form:hidden path="depId" />
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
									<form:input path="startDate" class="datepicker-ui" title="시작일을 입력하세요 예시)2020-01-01" autocomplete="off"/>
									<a href="#n"><img src="${design.resource}/images/sub/databox-icon.png" alt="달력" id="datepicker-img1"></a>
								</span>
								&nbsp;~&nbsp;
								<span>
									<form:input path="endDate" class="datepicker-ui" title="종료일을 선택하세요 예시)2020-01-01" autocomplete="off"/>
									<a href="#n"><img src="${design.resource}/images/sub/databox-icon.png" alt="달력" id="datepicker-img2"></a>
								</span>
							</td>
						</tr>
						
						<tr>
							<th scope="row">검색어</th>
							<td class="sub03-select-input">
							
								<c:if test="${not empty bcCategory1CodeList || not empty bcCategory2CodeList || not empty bcCategory3CodeList}">
									<c:if test="${not empty boardConfig.bcCategory1 and not empty boardConfig.bcCategory1Name}">
										<form:select path="baCategory1" title="${boardConfig.bcCategory1Name}">
											<option value="">${boardConfig.bcCategory1Name}</option>
											<form:options items="${bcCategory1CodeList}" itemLabel="codeName" itemValue="codeId"/>
										</form:select>
									</c:if>
									<c:if test="${not empty boardConfig.bcCategory2 and not empty boardConfig.bcCategory2Name}">
										<form:select path="baCategory2" title="${boardConfig.bcCategory2Name}">
											<option value="">${boardConfig.bcCategory2Name}</option>
											<form:options items="${bcCategory2CodeList}" itemLabel="codeName" itemValue="codeId"/>
										</form:select>
									</c:if>
									<c:if test="${not empty boardConfig.bcCategory3 and not empty boardConfig.bcCategory3Name}">
										<form:select path="baCategory3" title="${boardConfig.bcCategory3Name}">
											<option value="">${boardConfig.bcCategory3Name}</option>
											<form:options items="${bcCategory3CodeList}" itemLabel="codeName" itemValue="codeId"/>
										</form:select>
									</c:if>
								</c:if>
								
								<form:select path="sc" title="검색어 구분을 선택하세요">
									<option value="">전체</option>
									<form:option value="baTitle" label="제목" />
									<form:option value="baContentPlain" label="내용" />
								</form:select>
								
								<span>
									<form:input path="sv" title="검색어를 입력하세요"/>
									<a href="#n" class="search-btn"><img src="${design.resource}/images/sub/t-searchstyle1-icon01.png" alt="검색"></a>
								</span>
							</td>
						</tr>
					</tbody>
				</table>
			</form:form>
		</div>
		
		<div class="board-table no-bor-new">
			<table>
				<caption>${boardConfig.bcName }</caption>
				<colgroup>
					<col style="width: 7%" class="mb-none">
					<%-- <col style="width: 15%"> --%><!-- 캠퍼스타운명 -->
					<c:if test="${not empty boardConfig.bcCategory1 and not empty boardConfig.bcCategory1Name}">
						<col style="width:11%"/><!-- 구분1 -->
					</c:if>
					<c:if test="${not empty boardConfig.bcCategory2 and not empty boardConfig.bcCategory2Name}">
						<col style="width:10%"/><!-- 구분2 -->
					</c:if>
					<c:if test="${not empty boardConfig.bcCategory3 and not empty boardConfig.bcCategory3Name}">
						<col style="width:10%"/><!-- 구분3 -->
					</c:if>
					
					<col style="width: auto"><!-- 제목 -->
					<col style="width: 11%" class="mb-none"><!-- 등록일 -->
					<c:if test="${not empty boardConfig.bcStatusCode}">
						<col style="width:10%"/><!-- 상태 -->
					</c:if>
					<%-- <col style="width: 10%" /><!-- 작성자 --> --%>
					<col style="width: 8%" class="mb-none"><!-- 조회수 -->
					<c:if test="${boardConfig.bcSupportRecommend}">
						<col style="width: 8%;" class="mb-none"/><!-- 추천 -->
					</c:if>
				</colgroup>
				<thead>
					<tr>
						<th scope="col" class="mb-none">번호</th>
						<!-- <th scope="col">캠퍼스타운</th> -->
						<c:if test="${not empty boardConfig.bcCategory1 and not empty boardConfig.bcCategory1Name}">
							<th scope="col">${boardConfig.bcCategory1Name}</th>
						</c:if>
						<c:if test="${not empty boardConfig.bcCategory2 and not empty boardConfig.bcCategory2Name}">
							<th scope="col">${boardConfig.bcCategory2Name}</th>
						</c:if>
						<c:if test="${not empty boardConfig.bcCategory3 and not empty boardConfig.bcCategory3Name}">
							<th scope="col">${boardConfig.bcCategory3Name}</th>
						</c:if>
						<th scope="col">제목</th>
						<th scope="col" class="mb-none">등록일</th>
						<c:if test="${not empty boardConfig.bcStatusCode}">
							<th scope="col">상태</th>
						</c:if>
						<!-- <th scope="col">작성자</th> -->
						<th scope="col" class="mb-none">조회수</th>
						<c:if test="${boardConfig.bcSupportRecommend}">
							<th scope="col" class="mb-none">좋아요</th>
						</c:if>
					</tr>
				</thead>
				<tbody>
					
					<!-- 상단 고정 -->
					<c:if test="${not empty noticeList}">
						<c:forEach items="${noticeList}" var="item" varStatus="vs">
							<tr>
								<td class="mb-none"><img src="${design.resource}/images/sub/tack.png" alt="상단고정"></td>
								<%-- 
								<td>
									<c:if test="${not empty item.depId and not empty departmentList}">
										<c:forEach items="${departmentList}" var="department" varStatus="dvs">
											<c:if test="${department.depId eq item.depId}">${department.depName}</c:if>
										</c:forEach>
									</c:if>
								</td>
								 --%>
								<c:if test="${not empty boardConfig.bcCategory1 and not empty boardConfig.bcCategory1Name}">
									<td class="">${asapro:codeName(item.baCategory1, bcCategory1CodeList) }</td>
								</c:if>
								<c:if test="${not empty boardConfig.bcCategory2 and not empty boardConfig.bcCategory2Name}">
									<td class="">${asapro:codeName(item.baCategory2, bcCategory2CodeList) }</td>
								</c:if>
								<c:if test="${not empty boardConfig.bcCategory3 and not empty boardConfig.bcCategory3Name}">
									<td class="">${asapro:codeName(item.baCategory3, bcCategory3CodeList) }</td>
								</c:if>
								
								<td class="tb-txl">
									<fmt:formatDate value="${item.baRegDate}" pattern="yyyyMMdd" var="regdate" />
									<%--<img src="${design.resource}/images/main/main_11.png" alt="" class="alim-icon"/> --%>
									<%-- <a class="art" href="${item.permLink}?cp=${boardArticleSearch.cp}${boardArticleSearch.queryString}">${item.baTitle} --%>
									<a class="art" href="${APP_PATH}/university/university_news/${item.baId}?depId=${item.depId}">${item.baTitle}
										<c:if test="${now - regdate <= 5}"><span class="new">새글</span></c:if>
										<c:if test="${item.baSecret}"><span class="lock">비밀글</span></c:if>
									</a>
								</td>
								<td class="mb-none">
									<fmt:formatDate value="${item.baRegDate}" pattern="yyyy-MM-dd" />
								</td>
								<c:if test="${not empty boardConfig.bcStatusCode}">
									<td class="">${asapro:codeName(item.baStatus, bcStatusCodeList) }</td>
								</c:if>
								<td class="mb-none">${item.baHit}</td>
								<c:if test="${boardConfig.bcSupportRecommend}">
									<td class="mb-none">
										${item.baRecommend}
									</td>
								</c:if>
								
							</tr>
						</c:forEach>
					</c:if>
					
					
					<c:set var="userAgentInfo" value="${header['User-Agent']}" />
					<!-- 일반 게시물 -->
					<c:if test="${empty paging.result}">
						<!-- 모바일 환경 -->
					    <c:if test="${!fn:contains(userAgentInfo, 'Windows') && !fn:contains(userAgentInfo, 'iPad')}" >
							<tr>
								<td colspan="3" class="text-center">등록된 게시글이 없습니다.</td>
							</tr>
						</c:if>
						<!-- Pc, 타블렛 환경 -->
					    <c:if test="${fn:contains(userAgentInfo, 'Windows') || fn:contains(userAgentInfo, 'iPad')}" >
							<tr>
								<td colspan="6" class="text-center">등록된 게시글이 없습니다.</td>
							</tr>
						</c:if>	
					</c:if>
					
					<c:forEach items="${paging.result}" var="item" varStatus="vs">
						<tr>
							<td class="mb-none">${paging.rowTop - vs.index}</td>
							<%-- 
							<td>
								<c:if test="${not empty item.depId and not empty departmentList}">
									<c:forEach items="${departmentList}" var="department" varStatus="dvs">
										<c:if test="${department.depId eq item.depId}">${department.depName}</c:if>
									</c:forEach>
								</c:if>
							</td>
							 --%>
							<c:if test="${not empty boardConfig.bcCategory1 and not empty boardConfig.bcCategory1Name}">
								<td class="">${asapro:codeName(item.baCategory1, bcCategory1CodeList) }</td>
							</c:if>
							<c:if test="${not empty boardConfig.bcCategory2 and not empty boardConfig.bcCategory2Name}">
								<td class="">${asapro:codeName(item.baCategory2, bcCategory2CodeList) }</td>
							</c:if>
							<c:if test="${not empty boardConfig.bcCategory3 and not empty boardConfig.bcCategory3Name}">
								<td class="">${asapro:codeName(item.baCategory3, bcCategory3CodeList) }</td>
							</c:if>
							
							<td class="tb-txl">
								<fmt:formatDate value="${item.baRegDate}" pattern="yyyyMMdd" var="regdate" />
								<%-- <a class=art href="${item.permLink}?cp=${boardArticleSearch.cp}${boardArticleSearch.queryString}">${item.baTitle} --%>
								<a class="art" href="${APP_PATH}/university/university_news/${item.baId}?depId=${item.depId}">${item.baTitle}
									<c:if test="${now - regdate <= 5}"><span class="new">새글</span></c:if>
									<c:if test="${item.baSecret}"><span class="lock">비밀글</span></c:if>
								</a>
							</td>
							<td class="mb-none">
								<fmt:formatDate value="${item.baRegDate}" pattern="yyyy-MM-dd" />
							</td>
							<c:if test="${not empty boardConfig.bcStatusCode}">
								<td class="">${asapro:codeName(item.baStatus, bcStatusCodeList) }</td>
							</c:if>
							
							<td class="mb-none">${item.baHit}</td>
							<c:if test="${boardConfig.bcSupportRecommend}">
								<td class="mb-none">
									${item.baRecommend}
								</td>
							</c:if>
							
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	
		<!-- button -->
		<div class="subl-paging clearfix">
			<c:if test="${isBoardManager || bcAllowForm}">
				<a class="btn05" href="${APP_PATH}/board/${boardConfig.bcId}/new">글쓰기</a>
			</c:if>
	
			<!-- paging -->
			<c:out value="${paging.userImageTag}" escapeXml="false"/>
		<!-- </div> -->		
	</div>	
	<!--  end -->

<!-- footer -->
<c:import url="${design.footer}" charEncoding="UTF-8" />