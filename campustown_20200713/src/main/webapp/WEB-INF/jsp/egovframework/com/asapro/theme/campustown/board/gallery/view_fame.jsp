<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="asapro" uri="http://www.asadal.com" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!-- header -->
<c:import url="${design.header}" charEncoding="UTF-8" />
<script>
jQuery(function($){
	<c:if test="${boardConfig.bcSupportComment}">
		var cmtModule = 'board';
		var cmtModuleSub = '${boardConfig.bcId}';
		var cmtModCategory = 'article';
		var cmtModItemId = '${boardArticle.baId}';
		var cmtTitle = '<c:out value="${boardArticle.baTitle}" escapeXml="true" />';
		var param = 'cmtModule=' + cmtModule + '&cmtModuleSub=' + cmtModuleSub +'&cmtModCategory=' + cmtModCategory + '&cmtModItemId=' + cmtModItemId + '&cmtTitle=' + cmtTitle;

		$('.commentWrapper').load('${APP_PATH}/comment/list?' + encodeURI(param));
	</c:if>
});
</script>
<style>
.img{text-align:center; }
</style>
<c:if test="${not empty messageCode}"><script>alert('${messageCode}');</script></c:if>

<!-- sidebar -->
<c:import url="${design.themeDir}/layout/sidebar.jsp" charEncoding="UTF-8" />

	<!-- content -->
	
	<!-- alert maeeage -->
	<c:import url="/WEB-INF/jsp/egovframework/com/asapro/admin/include/message.jsp" charEncoding="UTF-8" />
	
	<!-- board view -->
	<div class="table-con">
		<table>
			<caption>${boardArticleForm.baTitle }</caption>
			<colgroup>
				<col style="width: 15%" />
				<col style="width: 35%" />
				<col style="width: 15%" />
				<col style="width: 35%" />
			</colgroup>
			<tbody>
				<c:if test="${not empty boardConfig.bcCategory1 and not empty boardConfig.bcCategory1Name}">
					<tr>
						<th scope="row">${boardConfig.bcCategory1Name}</th>
						<td colspan="3">
							<c:forEach items="${bcCategory1CodeList}" var="code"><c:if test="${code.codeId eq boardArticle.baCategory1}">${code.codeName}</c:if></c:forEach>
						</td>
					</tr>
				</c:if>
				<c:if test="${not empty boardConfig.bcCategory2 and not empty boardConfig.bcCategory2Name}">
					<tr>
						<th scope="row">${boardConfig.bcCategory2Name}</th>
						<td colspan="3">
							<c:forEach items="${bcCategory2CodeList}" var="code"><c:if test="${code.codeId eq boardArticle.baCategory2}">${code.codeName}</c:if></c:forEach>
						</td>
					</tr>
				</c:if>
				<c:if test="${not empty boardConfig.bcCategory3 and not empty boardConfig.bcCategory2Name}">
					<tr>
						<th scope="row">${boardConfig.bcCategory3Name}</th>
						<td colspan="3">
							<c:forEach items="${bcCategory3CodeList}" var="code"><c:if test="${code.codeId eq boardArticle.baCategory3}">${code.codeName}</c:if></c:forEach>
						</td>
					</tr>
				</c:if>
				<c:if test="${not empty boardConfig.bcStatusCode}">
					<tr>
						<th scope="row">상태</th>
						<td colspan="3">
							<c:forEach items="${bcStatusCodeList}" var="code">
								<c:if test="${code.codeId eq boardArticle.baStatus}">${code.codeName}</c:if>			
							</c:forEach>
						</td>
					</tr>
				</c:if>
				<tr>
					<th scope="row">제목</th>
					<td>${boardArticle.baTitle}</td>
					<th scope="row">조회수</th>
					<td>${boardArticle.baHit}</td>
				</tr>
				<tr>
					<th scope="row">등록일</th>
					<td><fmt:formatDate value="${boardArticle.baRegDate}" pattern="yyyy-MM-dd" /></td>
					<c:if test="${boardConfig.bcSupportRecommend}">
						<th scope="row">좋아요</th>
						<td>
							<c:set var="cookiename" value="ba_reco_${currentSite.siteId}_${boardArticle.baId}" />
							<c:set var="className" value="heartWhite" />
							<c:if test="${not empty cookie[cookiename]['value'] }"><c:set var="className" value="heartYellow" /></c:if>
							<p class="btnA ba-recommend" id="ba_recommend_${boardArticle.baId}" data-bcid="${boardConfig.bcId}" data-baid="${boardArticle.baId}" style="cursor: pointer;">
								<span class="${className }">${boardArticle.baRecommend}</span>
							</p>
						</td>
					</c:if>
				</tr>
				<tr>
					<th scope="row">작성자</th>
					<td>
						<c:choose>
							<c:when test="${empty boardArticle.member}">
								${boardArticle.baGuestName}
							</c:when>
							<c:otherwise>
								${boardArticle.member.memberName}
								<a href="${APP_PATH}/mypage/note/insert?noteReceiverId=${boardArticle.member.memberId}&reply=true" class="noteWrite" title="쪽지쓰기"><img src="${design.resource }/images/sub/sub01_limg33.gif" alt="쪽지"></a>
							</c:otherwise>
						</c:choose>
					</td>
					<%-- 
					<th scope="row">${boardConfig.bcCustomField1}</th>
					<td>
						<p><a href="${boardArticle.baCustomField1}" target="_blank">${boardArticle.baCustomField1}</a></p>
					</td>
					 --%>
				</tr>
				<tr>
					<td colspan="4">
						<i class="img">
							<c:if test="${not empty boardArticle.thumb && boardArticle.thumb.fileId > 0 }">
								<img class="c_img" src="${boardArticle.thumb.fileServletThumbnailUrl}" alt="${item.thumb.fileAltText }"
									onerror="this.src='${CONTEXT_PATH }/design/common/images/asset/noImage400x300.jpg'">
							</c:if>
							<c:if test="${empty boardArticle.thumb}">
								<img class="c_img" src="${CONTEXT_PATH }/design/common/images/asset/noImage400x300.jpg" alt="대체 이미지">
							</c:if>
						</i>
					</td>
				</tr>
				<tr>
					<td colspan="4">
						<c:if test="${asapro:countMatches(boardArticle.baContentHtml,'<') > 1}">
							<c:out value="${boardArticle.baContentHtml}" escapeXml="false" />
						</c:if>
						<c:if test="${asapro:countMatches(boardArticle.baContentHtml,'<') <= 1}">
							${asapro:nl2br(boardArticle.baContentHtml, false) }
						</c:if>
					</td>
				</tr>
				
				<c:if test="${not empty boardArticle.baFileInfos}">
				<tr>
					<td colspan="4">
						<c:forEach items="${boardArticle.baFileInfos}" var="baFileInfo">
							<div>
								<a href="${APP_PATH}/file/download/uu/${baFileInfo.fileUUID}" title="다운로드" class="add-file"><span><img src="${design.resource }/images/sub/add_file_ico.gif" alt="첨부파일" /></span>${baFileInfo.fileOriginalName} (${baFileInfo.fileSizeString})</a>
							</div>
						</c:forEach>
					</td>
				</tr>
				</c:if>
			</tbody>
		</table>
	</div>
	

	<!-- button -->
	<div class="btn-area txr clearfix">
		<c:if test="${isBoardManager || bcAllowForm}">
			<a class="btn05" href="${APP_PATH}/board/${boardConfig.bcId}/new">글쓰기</a>
		</c:if>
		<c:if test="${isBoardManager || bcAllowFormEdit}">
			<a class="btn03" href="${APP_PATH}/board/${boardConfig.bcId}/${boardArticle.baId}/edit">글수정</a>
		</c:if>
		<c:if test="${isBoardManager || bcAllowFormDelete}">
			<c:if test="${sessionScope.currentUser.userRole.roleCode eq 'ROLE_GUEST'}">
				<a class="btn04" href="${APP_PATH}/board/${boardConfig.bcId}/${boardArticle.baId}/delete/password">글삭제</a>
			</c:if>
			<c:if test="${sessionScope.currentUser.userRole.roleCode ne 'ROLE_GUEST'}">
				<a class="btn04" href="${APP_PATH}/board/${boardConfig.bcId}/${boardArticle.baId}/delete/byuser">글삭제</a>
			</c:if>
		</c:if>
		<%-- <a class="btn t_wg" href="${APP_PATH}/board/${boardConfig.bcId}/list?cp=${backListSearch.cp}${backListSearch.queryString}">목록</a> --%>
		<a class="btn01" href="${APP_PATH}/board/${boardConfig.bcId}/list?cp=${backListSearch.cp}${backListSearch.queryString}">목록</a>
	</div>
		
	
	<!-- 댓글 노출 wrapper -->
	<div class="commentWrapper"></div>	
	
	
	
	
	
	<%--
	
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
				<th class="col_1">조회수</th>
				<td class="col_2 tlt">${boardArticle.baHit}</td>
			</tr>
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
				<tr>
					<td colspan="4" class="board_con img_resize tlt">
					 	<c:out value="${boardArticle.baContentHtml}" escapeXml="false" />
					</td>
				</tr>
		</tbody>
	</table>
	
	
	
	<tr>
		<c:choose>
			<c:when test="${empty boardArticle.member}">
				<th>작성자</th>
				<td>${boardArticle.baGuestName}</td>
			</c:when>
			<c:otherwise>
				<th>작성자</th>
				<td>${boardArticle.member.memberName}</td>
			</c:otherwise>
		</c:choose>
	</tr>
	<tr>
		<th>이메일</th>
		<td><a href="mailto:${boardArticle.baEmail}">${boardArticle.baEmail}</a></td>
	</tr>
	<c:if test="${boardConfig.bcSupportRecommend}">
		<tr>
			<th>추천</th>
			<td>
				${boardArticle.baRecommend}
				<button class="btn ba-recommend" id="ba_recommend_${boardArticle.baId}" data-bcid="${boardConfig.bcId}" data-baid="${boardArticle.baId}" type="button">추천</button>
			</td>
		</tr>
	</c:if>
	<tr>
	<c:if test="${not empty boardConfig.bcCategory1 and not empty boardConfig.bcCategory1Name}">
		<tr>
			<th>${boardConfig.bcCategory1Name}</th>
			<td>
				<c:forEach items="${bcCategory1CodeList}" var="code"><c:if test="${code.codeId eq boardArticle.baCategory1}">${code.codeName}</c:if></c:forEach>
			</td>
		</tr>
	</c:if>
	<c:if test="${not empty boardConfig.bcCategory2 and not empty boardConfig.bcCategory2Name}">
		<tr>
			<th>${boardConfig.bcCategory2Name}</th>
			<td>
				<c:forEach items="${bcCategory2CodeList}" var="code"><c:if test="${code.codeId eq boardArticle.baCategory2}">${code.codeName}</c:if></c:forEach>
			</td>
		</tr>
	</c:if>
	<c:if test="${not empty boardConfig.bcCategory3 and not empty boardConfig.bcCategory2Name}">
		<tr>
			<th>${boardConfig.bcCategory3Name}</th>
			<td>
				<c:forEach items="${bcCategory3CodeList}" var="code"><c:if test="${code.codeId eq boardArticle.baCategory3}">${code.codeName}</c:if></c:forEach>
			</td>
		</tr>
	</c:if>
	<c:if test="${not empty boardConfig.bcStatusCode}">
		<tr>
			<th>상태</th>
			<td>
				<c:forEach items="${bcStatusCodeList}" var="code">
					<c:if test="${code.codeId eq boardArticle.baStatus}">${code.codeName}</c:if>			
				</c:forEach>
			</td>
		</tr>
	</c:if>
	<c:if test="${boardConfig.bcSupportAnswer and not empty boardArticle.baAnswer}">
		<th>답글</th>
		<td>
			<div class="baContent">
				
				<c:out value="${boardArticle.baAnswer}" escapeXml="false" />
			</div>
		</td>
	</c:if>
	--%>
	
	
	<%--
	<div class="s13margBt30">
		<table class="tablest112 tablest112W">
			<caption></caption>
			<colgroup>
				<col style="width:15%;" />
				<col style="width:35%;" />
				<col style="width:15%;" />
				<col />
			</colgroup>

			<tbody>

				<tr>
					<th scope="row">이전글 <a href="javascript:void(0)"><img class="next-doc15" src="${design.resource }/images/sub/pre-doc.gif" alt="이전글"/></a></th>
					<td colspan="3" class="txtLf">
						<c:if test="${prevBoardArticle == null || prevBoardArticle.baId == null || prevBoardArticle.baId <= 0 }">
							이전글이 없습니다.
						</c:if>
						<c:if test="${prevBoardArticle != null && prevBoardArticle.baId != null && prevBoardArticle.baId > 0 }">
							 <a href="${prevBoardArticle.permLink }">${prevBoardArticle.baTitle }</a>
						</c:if>
					</td>
				</tr>

				<tr>
					<th scope="row">다음글 <a href="javascript:void(0)"><img class="next-doc15" src="${design.resource }/images/sub/next-doc.gif" alt="다음글"/></a></th>
					<td colspan="3" class="txtLf">
						<c:if test="${nextBoardArticle == null || nextBoardArticle.baId == null || nextBoardArticle.baId <= 0 }">
							다음글이 없습니다.
						</c:if>
						<c:if test="${nextBoardArticle != null && nextBoardArticle.baId != null && nextBoardArticle.baId > 0 }">
							 <a href="${nextBoardArticle.permLink }">${nextBoardArticle.baTitle }</a>
						</c:if>
					</td>
				</tr>
				
			</tbody>
		</table>
	</div>
	 --%>
	
	<!-- 공공누리 -->
	<c:import url="${design.themeDir}/board/publicOpen.jsp" charEncoding="UTF-8" />
	
	<!-- content end -->

<!-- footer -->
<c:import url="${design.footer}" charEncoding="UTF-8" />