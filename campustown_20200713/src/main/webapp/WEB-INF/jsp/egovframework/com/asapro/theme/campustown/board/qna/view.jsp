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
<c:if test="${not empty messageCode}"><script>alert('${messageCode}');</script></c:if>

<!-- sidebar -->
<c:import url="${design.themeDir}/layout/sidebar.jsp" charEncoding="UTF-8" />

			<!-- content -->
			<div id="content">
				<%-- <!-- button -->
				<div class="btn_wrap trt">
					<a class="btn t_bw" href="${APP_PATH}/board/${boardConfig.bcId}/list?cp=${backListSearch.cp}${backListSearch.queryString}">목록</a>
				</div>
				<br> --%>
				<!-- board view -->
				<div class="table tb_1 pd20">
					<!-- alert maeeage -->
					<c:import url="/WEB-INF/jsp/egovframework/com/asapro/admin/include/message.jsp" charEncoding="UTF-8" />
					<table>
						<caption>${boardArticleForm.baTitle }</caption>
						<colgroup>
							<col class="col_1">
							<col class="col_2">
							<col class="col_1">
							<col class="col_2">
						</colgroup>
						<tbody>
							<tr>
								<th>제목</th>
								<td colspan="3" class="tlt">
									<c:if test="${not empty boardConfig.bcCategory1 and not empty boardArticle.baCategory1}">
										<strong>[${asapro:codeName(boardArticle.baCategory1, bcCategory1CodeList) }]</strong>
									</c:if>
									${boardArticle.baTitle}
								</td>
							</tr>
							<tr>
								<th class="col_1">등록일</th>
								<td class="col_2 tlt"><fmt:formatDate value="${boardArticle.baRegDate}" pattern="yyyy-MM-dd" /></td>
								<th class="col_1">비밀글</th>
								<td class="col_2 tlt"><c:if test="${boardArticle.baSecret}">비밀</c:if></td>
							</tr>
							<tr>
								<th class="col_1">작성자</th>
								<td class="col_2 tlt">
									<c:if test="${not empty boardArticle.member}">${boardArticle.member.memberName}</c:if>
									<c:if test="${empty boardArticle.member}">${boardArticle.baGuestName}</c:if>
								</td>
								<th class="col_1">조회수</th>
								<td class="col_2 tlt">${boardArticle.baHit}</td>
							</tr>
							<%--
							<tr>
								<th>첨부파일</th>
								<td colspan="3" class="tlt">
									<c:if test="${not empty boardArticle.baFileInfos}">
										<c:forEach items="${boardArticle.baFileInfos}" var="baFileInfo">
											<div><a href="${APP_PATH}/file/download/uu/${baFileInfo.fileUUID}" title="다운로드" class="dl_file">${baFileInfo.fileOriginalName} (${baFileInfo.fileSizeString})</a></div>
										</c:forEach>
									</c:if>
									<c:if test="${empty boardArticle.baFileInfos}">
										<span>첨부된 파일 없음</span>
									</c:if>
								</td>
							</tr>
							 --%>
							<tr>
								<th class="col_1">질문</th>
								<td colspan="3" class="board_con img_resize tlt">
									<%--
									<c:out value="${boardArticle.baContentHtml}" escapeXml="false" />
									 --%>
									 ${asapro:nl2br(boardArticle.baContentHtml, false) }
								</td>
							</tr>
							<c:if test="${boardConfig.bcSupportAnswer and not empty boardArticle.baAnswer}">
							<tr>
								<th class="col_1"><span class="t_blue">답변</span></th>
								<td colspan="3" class="board_con img_resize tlt">
									${asapro:nl2br(boardArticle.baAnswer, false) }
								</td>
							</tr>
							</c:if>
						</tbody>
					</table>
				</div>

				<!-- button -->
				<div class="btn_wrap trt">
					<c:if test="${isBoardManager || bcAllowForm}">
						<a class="btn t_br" href="${APP_PATH}/board/${boardConfig.bcId}/new">글쓰기</a>
					</c:if>
					<c:if test="${isBoardManager || bcAllowFormEdit}">
						<a class="btn t_bw" href="${APP_PATH}/board/${boardConfig.bcId}/${boardArticle.baId}/edit">글수정</a>
					</c:if>
					<c:if test="${isBoardManager || bcAllowFormDelete}">
						<c:if test="${sessionScope.currentUser.userRole.roleCode eq 'ROLE_GUEST'}">
							<a class="btn t_del" href="${APP_PATH}/board/${boardConfig.bcId}/${boardArticle.baId}/delete/password">글삭제</a>
						</c:if>
						<c:if test="${sessionScope.currentUser.userRole.roleCode ne 'ROLE_GUEST'}">
							<a class="btn t_del" href="${APP_PATH}/board/${boardConfig.bcId}/${boardArticle.baId}/delete/byuser">글삭제</a>
						</c:if>
					</c:if>
					<a class="btn t_wg" href="${APP_PATH}/board/${boardConfig.bcId}/list?cp=${backListSearch.cp}${backListSearch.queryString}">목록</a>
				</div>
				
			</div>
			<!-- content end -->

<!-- footer -->
<c:import url="${design.footer}" charEncoding="UTF-8" />