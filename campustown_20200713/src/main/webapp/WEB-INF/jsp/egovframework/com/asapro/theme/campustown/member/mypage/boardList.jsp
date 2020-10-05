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
	
	<!-- board list -->
	<div class="board-list">
		<table>
			<caption>${boardConfig.bcName }</caption>
			<colgroup>
				<col style="width: 7%" />
				<%--
				<c:if test="${not empty boardConfig.bcCategory1 and not empty boardConfig.bcCategory1Name}">
					<col style="width:20%"/><!-- 구분1 -->
				</c:if>
				<c:if test="${not empty boardConfig.bcCategory2 and not empty boardConfig.bcCategory2Name}">
					<col style="width:20%"/><!-- 구분2 -->
				</c:if>
				<c:if test="${not empty boardConfig.bcCategory3 and not empty boardConfig.bcCategory3Name}">
					<col style="width:20%"/><!-- 구분3 -->
				</c:if>
				 --%>
				<col style="width: auto" />
				<col style="width: 10%" />
				<c:if test="${not empty boardConfig.bcStatusCode}">
					<col style="width:10%"/><!-- 상태 -->
				</c:if>
				<col style="width: 10%" /><!-- 작성자 -->
				<c:if test="${boardConfig.bcSupportRecommend}">
					<col style="width: 6%;" /><!-- 추천 -->
				</c:if>
				<col style="width: 6%" /><!-- 조회수 -->
				<col style="width: 15%" />
			</colgroup>
			<thead>
				<tr>
					<th scope="col">NO.</th>
					<%--
					<c:if test="${not empty boardConfig.bcCategory1 and not empty boardConfig.bcCategory1Name}">
						<th scope="col">${boardConfig.bcCategory1Name}</th>
					</c:if>
					<c:if test="${not empty boardConfig.bcCategory2 and not empty boardConfig.bcCategory2Name}">
						<th scope="col">${boardConfig.bcCategory2Name}</th>
					</c:if>
					<c:if test="${not empty boardConfig.bcCategory3 and not empty boardConfig.bcCategory3Name}">
						<th scope="col">${boardConfig.bcCategory3Name}</th>
					</c:if>
					 --%>
					<th scope="col">제목</th>
					<th scope="col">첨부파일</th>
					<c:if test="${not empty boardConfig.bcStatusCode}">
						<th scope="col">상태</th>
					</c:if>
					<th scope="col">작성자</th>
					<c:if test="${boardConfig.bcSupportRecommend}">
						<th scope="col">추천</th>
					</c:if>
					<th scope="col">조회수</th>
					<th scope="col">작성일</th>
				</tr>
			</thead>
			<tbody>
			
				<!-- 일반 게시물 -->
				<c:if test="${empty paging.result}">
					<tr>
						<td colspan="6" class="text-center">등록된 게시글이 없습니다.</td>
					</tr>
				</c:if>
				<c:forEach items="${paging.result}" var="item" varStatus="vs">
					<tr>
						<td class="num">${paging.rowTop - vs.index}</td>
						<%--
						<c:if test="${not empty boardConfig.bcCategory1 and not empty boardConfig.bcCategory1Name}">
							<td class="b-td2">${asapro:codeName(item.baCategory1, bcCategory1CodeList) }</td>
						</c:if>
						<c:if test="${not empty boardConfig.bcCategory2 and not empty boardConfig.bcCategory2Name}">
							<td class="b-td2">${asapro:codeName(item.baCategory2, bcCategory2CodeList) }</td>
						</c:if>
						<c:if test="${not empty boardConfig.bcCategory3 and not empty boardConfig.bcCategory3Name}">
							<td class="b-td2">${asapro:codeName(item.baCategory3, bcCategory3CodeList) }</td>
						</c:if>
						 --%>
						<td class="txl title">
							<%-- <fmt:formatDate value="${item.baRegDate}" pattern="yyyyMMdd" var="regdate" /> --%>
							<c:if test="${not empty boardConfig.bcCategory1 and not empty item.baCategory1}">
								<strong>[${asapro:codeName(item.baCategory1, bcCategory1CodeList) }]</strong>
							</c:if>
							<a class="board-tit" href="${item.permLink}?cp=${boardArticleSearch.cp}${boardArticleSearch.queryString}">${item.baTitle}
								<%-- <c:if test="${now - regdate <= 5}"><span class="new">새글</span></c:if> --%>
								<c:if test="${item.baSecret}"><span class="lock">비밀글</span></c:if>
							</a>
							<%--<c:if test="${item.baCommentTotal > 0}">[${item.baCommentTotal}]</c:if> --%>
						</td>
						<td class="b-td2">
							<c:if test="${not empty item.baFileInfos }">
								<a href="#n"><img src="${design.resource }/images/sub/add_file.gif" alt="첨부파일" /></a>
							</c:if>
						</td>
						<c:if test="${not empty boardConfig.bcStatusCode}">
							<td class="b-td3">${asapro:codeName(item.baStatus, bcStatusCodeList) }</td>
						</c:if>
						
						<td class="writer">
							<c:if test="${not empty item.depId and not empty departmentList}">
								<c:forEach items="${departmentList}" var="department" varStatus="dvs">
									<c:if test="${department.depId eq item.depId}">${department.depName}</c:if>
								</c:forEach>
							</c:if>
							 
							<c:if test="${not empty item.member}">${item.member.memberName}</c:if>
							<c:if test="${empty item.member}">${item.baGuestName}</c:if>
						</td>
						
						<c:if test="${boardConfig.bcSupportRecommend}">
							<td class="b-td2">
								${item.baRecommend}
							</td>
						</c:if>
						<td class="file">${item.baHit}</td>
						<td class="date2">
							<fmt:formatDate value="${item.baRegDate}" pattern="yyyy-MM-dd" />
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
	
	<!-- paging -->
	<c:out value="${paging.userImageTag}" escapeXml="false"/>
				
	<!-- content end -->

<!-- footer -->
<c:import url="${design.footer}" charEncoding="UTF-8" />