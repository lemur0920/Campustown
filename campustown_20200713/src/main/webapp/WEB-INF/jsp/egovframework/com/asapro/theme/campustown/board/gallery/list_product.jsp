<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="asapro" uri="http://www.asadal.com" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

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
<c:import url="${design.themeDir}/layout/sidebar.jsp" charEncoding="UTF-8" />

	<!-- content -->
	<!-- board search type1 -->
	<div class="table-con">
		<form:form modelAttribute="boardArticleSearch" action="${APP_PATH}/board/${boardConfig.bcId}/list" method="get">
		<form:hidden path="baCommSelec"/>
		
		<table>
			<caption>검색하기</caption>
			<colgroup>
				<col style="width: 150px;">
				<col>
			</colgroup>
			
			<tbody>
				<tr>
					<th scope="row">등록일</th>
					<td class="databox">
						<span>
							<form:input path="startDate" class="datepicker-ui" title="시작일을 입력하세요 예시)2019-01-01" autocomplete="off"/>
							<a href="#n"><img src="${design.resource }/images/sub/databox-icon.png" alt="시작일선택" id="datepicker-img1"></a>
						</span>
						<span>
							<form:input path="endDate" class="datepicker-ui" title="종료일을 선택하세요 예시)2019-01-01" autocomplete="off"/>
							<a href="#n"><img src="${design.resource }/images/sub/databox-icon.png" alt="종료일선택" id="datepicker-img2"></a>
						</span>
					</td>
				</tr>
				
				<!-- select + input + button  -->
				<tr>
					<th scope="row">검색어</th>
					<td class="sub03-select-input">
						<c:if test="${not empty bcCategory1CodeList || not empty bcCategory2CodeList || not empty bcCategory3CodeList}">
						
							<c:if test="${not empty boardConfig.bcCategory1 and not empty boardConfig.bcCategory1Name}">
								<form:select path="baCategory1" title="${boardConfig.bcCategory1Name}">
									<option value="">구분<%-- ${boardConfig.bcCategory1Name} --%></option>
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
							<option value="">검색어구분</option>
							<form:option value="baTitle" label="제목" />
							<form:option value="baContentPlain" label="내용" />
						</form:select>
						
						<span>
							<form:input path="sv" title="검색어를 입력하세요"/>
							<!-- <button type="submit" class="btn_sch">검색</button> -->
							<a href="#n" class="search-btn"><img src="${design.resource }/images/sub/t-searchstyle1-icon01.png" alt="검색"></a>
						</span>
						
					</td>
				</tr>
			</tbody>
		</table>
		</form:form>
	</div>
		
		
	<ul class="gallery-list clearfix mgt40">
		<!-- 공지사항 게시물 -->
		<c:if test="${empty paging.result && empty noticeList}">
			<!-- 게시물이 없을 때 -->
			<li class="nodata">게시물이 없습니다.</li>
		</c:if>
		
		<c:if test="${not empty noticeList}">
			<c:forEach items="${noticeList}" var="item" varStatus="vs">
				<li>
					
					<i class="img">
						<a href="${item.permLink}?cp=${boardArticleSearch.cp}${boardArticleSearch.queryString}">
							<c:if test="${not empty item.thumb && item.thumb.fileId > 0}">
								<img src="${item.thumb.fileServletThumbnailUrl}" alt="${item.thumb.fileAltText }"
									onerror="this.src='${CONTEXT_PATH }/design/common/images/asset/noImage400x300.jpg'">
							</c:if>	
							<c:if test="${empty item.thumb }">
								<img src="${CONTEXT_PATH }/design/common/images/asset/noImage400x300.jpg" alt="대체이미지">
							</c:if>	
						</a>
					</i>
					<div class="txt-box">
						<h4 class="tit">
							<a href="${item.permLink}?cp=${boardArticleSearch.cp}${boardArticleSearch.queryString}">
								<%-- <c:if test="${not empty item.baCategory1 }">
									<span class="cate">[${asapro:codeName(item.baCategory1, bcCategory1CodeList) }]</span>
								</c:if> --%>
								<c:if test="${not empty item.baCategory3 }">
									<span class="cate">
										[
										<c:forEach items="${teamList }" var="team" varStatus="vs">
											<c:if test="${team.compId eq item.baCategory3 }">${team.compNm }</c:if>
										</c:forEach>
										]
									</span>
								</c:if>
								${item.baTitle }
							</a>
						</h4>
						<p class="txt">
							<a href="${item.permLink}?cp=${boardArticleSearch.cp}${boardArticleSearch.queryString}">
								${asapro:abbreviate(item.baCustomField3, 50) }
							</a>
						</p>
						<p class="time">
							<fmt:formatDate value="${item.baRegDate}" pattern="yyyy-MM-dd" />
						</p>
						<div class="info-con">
							<span>${item.baRecommend}</span>
							<span>${item.baHit}</span>
						</div>	
					</div>
				</li>
			</c:forEach>
		</c:if>
		
		<!-- 일반 게시물 -->
		<c:forEach items="${paging.result}" var="item" varStatus="vs">
			<li>
				<i class="img">
					<a href="${item.permLink}?cp=${boardArticleSearch.cp}${boardArticleSearch.queryString}">
						<c:if test="${not empty item.thumb && item.thumb.fileId > 0}">
							<img src="${item.thumb.fileServletThumbnailUrl}" alt="${item.thumb.fileAltText }"
								onerror="this.src='${CONTEXT_PATH }/design/common/images/asset/noImage400x300.jpg'">
						</c:if>	
						<c:if test="${empty item.thumb }">
							<img src="${CONTEXT_PATH }/design/common/images/asset/noImage400x300.jpg" alt="대체이미지">
						</c:if>	
					</a>
				</i>
				<div class="txt-box">
					<h4 class="tit">
						
						<a href="${item.permLink}?cp=${boardArticleSearch.cp}${boardArticleSearch.queryString}">
							<%-- <c:if test="${not empty item.baCategory1 }">
								<span>[${asapro:codeName(item.baCategory1, bcCategory1CodeList) }]</span>
							</c:if> --%>
							<c:if test="${not empty item.baCategory3 }">
								<span class="cate">
									[
									<c:forEach items="${teamList }" var="team" varStatus="vs">
										<c:if test="${team.compId eq item.baCategory3 }">${team.compNm }</c:if>
									</c:forEach>
									]
								</span>
							</c:if>
							${item.baTitle }
						</a>
					</h4>
					<p class="txt">
						<a href="${item.permLink}?cp=${boardArticleSearch.cp}${boardArticleSearch.queryString}">
							${asapro:abbreviate(item.baCustomField3, 50) }
						</a>
					</p>
					<p class="time">
						<fmt:formatDate value="${item.baRegDate}" pattern="yyyy-MM-dd" />
					</p>	
					<div class="info-con">
						<span>${item.baRecommend}</span>
						<span>${item.baHit}</span>
					</div>
				</div>
			</li>
		</c:forEach>
			
	</ul>

	<!-- button -->
	<div class="btn-area txr clearfix">
		<c:if test="${isBoardManager || bcAllowForm}">
			<a class="btn05" href="${APP_PATH}/board/${boardConfig.bcId}/new">글쓰기</a>
		</c:if>
	</div>	
	
	<!-- paging -->
	<c:out value="${paging.userImageTag}" escapeXml="false"/>
	<!-- content end -->

<!-- footer -->
<c:import url="${design.footer}" charEncoding="UTF-8" />