<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="asapro" uri="http://www.asadal.com" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
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
});
</script>
<c:if test="${not empty messageCode}"><script>alert('${messageCode}');</script></c:if>

<!-- sidebar -->
<c:import url="${design.themeDir}/layout/sidebar.jsp" charEncoding="UTF-8" />

			<!-- content -->
			<div id="content">
			
			<!-- board search type1 -->
				<div class="table tb_1">
					<!-- alert maeeage -->
					<c:import url="/WEB-INF/jsp/egovframework/com/asapro/admin/include/message.jsp" charEncoding="UTF-8" />
					
					<form:form modelAttribute="boardArticleSearch" action="${APP_PATH}/board/${boardConfig.bcId}/list" method="get">
					<form:hidden path="baCommSelec"/>
					<table>
						<caption>검색하기</caption>
						<colgroup>
							<col class="col_1">
							<col>
						</colgroup>
						<tbody>
							<tr>
								<th>등록일</th>
								<td>
									<ul class="set_date clearfix">
										<li>
											<form:input path="startDate" class="datepicker-ui" title="시작일을 입력하세요 예시)2019-01-01" placeholder="2019-01-01" autocomplete="off"/>
											<button type="button">날짜 선택</button>
										</li>
										<li>~</li>
										<li>
											<form:input path="endDate" class="datepicker-ui" title="종료일을 선택하세요 예시)2019-01-01" placeholder="2019-01-01" autocomplete="off"/>
											<button type="button">날짜 선택</button>
										</li>
									</ul>
								</td>
							</tr>
							<!-- select + input + button  -->
							<tr>
								<th>검색</th>
								<td>
									<ul class="set_seltxtbt  clearfix">
										<c:if test="${not empty bcCategory1CodeList || not empty bcCategory2CodeList || not empty bcCategory3CodeList}">
											<c:if test="${not empty boardConfig.bcCategory1 and not empty boardConfig.bcCategory1Name}">
												<li class="w20 pr_5">
													<form:select path="baCategory1" title="${boardConfig.bcCategory1Name}">
														<option value="">${boardConfig.bcCategory1Name}</option>
														<form:options items="${bcCategory1CodeList}" itemLabel="codeName" itemValue="codeId"/>
													</form:select>
												</li>
											</c:if>
											<c:if test="${not empty boardConfig.bcCategory2 and not empty boardConfig.bcCategory2Name}">
												<li class="w20 pr_5">
													<form:select path="baCategory2" title="${boardConfig.bcCategory2Name}">
														<option value="">${boardConfig.bcCategory2Name}</option>
														<form:options items="${bcCategory2CodeList}" itemLabel="codeName" itemValue="codeId"/>
													</form:select>
												</li>
											</c:if>
											<c:if test="${not empty boardConfig.bcCategory3 and not empty boardConfig.bcCategory3Name}">
												<li class="w20 pr_5">
													<form:select path="baCategory3" title="${boardConfig.bcCategory3Name}">
														<option value="">${boardConfig.bcCategory3Name}</option>
														<form:options items="${bcCategory3CodeList}" itemLabel="codeName" itemValue="codeId"/>
													</form:select>
												</li>
											</c:if>
										</c:if>
									
										<li class="w20 pr_5">
											<form:select path="sc" title="검색어 구분을 선택하세요">
												<option value="">전체</option>
												<form:option value="baTitle" label="제목" />
												<form:option value="baContentPlain" label="내용" />
												<form:option value="baMemberName" label="작성자이름" />
											</form:select>
										</li>
										<li class="w60 btbox">
											<form:input path="sv" title="검색어를 입력하세요"/>
											<button type="submit" class="btn_sch">검색</button>
										</li>
									</ul>
								</td>
							</tr>
						</tbody>
					</table>
					</form:form>
				</div>


				<!-- board list -->
				<div class="table tb_2">
					<table>
						<caption>${boardConfig.bcName }</caption>
						<colgroup>
							<col style="width:100px">
							<c:if test="${not empty boardConfig.bcCategory1 and not empty boardConfig.bcCategory1Name}">
								<col style="width:12%"/><!-- 구분1 -->
							</c:if>
							<c:if test="${not empty boardConfig.bcCategory2 and not empty boardConfig.bcCategory2Name}">
								<col style="width:12%"/><!-- 구분2 -->
							</c:if>
							<c:if test="${not empty boardConfig.bcCategory3 and not empty boardConfig.bcCategory3Name}">
								<col style="width:12%"/><!-- 구분3 -->
							</c:if>
							<col style="">
							<%--
							 --%>
							<c:if test="${not empty boardConfig.bcStatusCode}">
								<col style="width:10%"/><!-- 상태 -->
							</c:if>
							<c:if test="${boardConfig.bcSupportRecommend}">
								<col style="width: 5%;" /><!-- 추천 -->
							</c:if>
							<col style="width:140px" class="pc">
							<col style="width:140px" class="pc">
							<col style="width:100px" class="pc">
							<col>
						</colgroup>
						<thead>
							<tr>
								<th>번호</th>
								<c:if test="${not empty boardConfig.bcCategory1 and not empty boardConfig.bcCategory1Name}">
									<th class="pc">${boardConfig.bcCategory1Name}</th>
								</c:if>
								<c:if test="${not empty boardConfig.bcCategory2 and not empty boardConfig.bcCategory2Name}">
									<th class="pc">${boardConfig.bcCategory2Name}</th>
								</c:if>
								<c:if test="${not empty boardConfig.bcCategory3 and not empty boardConfig.bcCategory3Name}">
									<th class="pc">${boardConfig.bcCategory3Name}</th>
								</c:if>
								<th>제목</th>
								<%--
								 --%>
								<c:if test="${not empty boardConfig.bcStatusCode}">
									<th class="pc">상태</th>
								</c:if>
								<c:if test="${boardConfig.bcSupportRecommend}">
									<th class="pc">추천</th>
								</c:if>
								<th class="pc">작성자</th>
								<th class="pc">등록일</th>
								<th class="pc">조회수</th>
							</tr>
						</thead>
						<tbody>
							<!-- 상단 고정 -->
							<c:if test="${not empty noticeList}">
								<c:forEach items="${noticeList}" var="item" varStatus="vs">
									<tr class="top">
										<td>상단고정</td>
										<c:if test="${not empty boardConfig.bcCategory1 and not empty boardConfig.bcCategory1Name}">
											<td>${asapro:codeName(item.baCategory1, bcCategory1CodeList) }</td>
										</c:if>
										<c:if test="${not empty boardConfig.bcCategory2 and not empty boardConfig.bcCategory2Name}">
											<td>${asapro:codeName(item.baCategory2, bcCategory2CodeList) }</td>
										</c:if>
										<c:if test="${not empty boardConfig.bcCategory3 and not empty boardConfig.bcCategory3Name}">
											<td>${asapro:codeName(item.baCategory3, bcCategory3CodeList) }</td>
										</c:if>
										<%--
										 --%>
										<td class="tlt">
										<%--
											<c:if test="${not empty boardConfig.bcCategory1 and not empty item.baCategory1}">
												<strong>[${asapro:codeName(item.baCategory1, bcCategory1CodeList) }]</strong>
											</c:if>
											 --%>
											<%--<img src="${design.resource}/images/main/main_11.png" alt="" class="alim-icon"/> --%>
											<a href="${item.permLink}?cp=${boardArticleSearch.cp}${boardArticleSearch.queryString}">${item.baTitle}
												<fmt:formatDate value="${item.baRegDate}" pattern="yyyyMMdd" var="regdate" />
												<c:if test="${now - regdate <= 5}"><span class="new">새글</span></c:if>
												<c:if test="${item.baSecret}"><span class="lock">비밀글</span></c:if>
												<c:if test="${not empty item.baAnswer }"><span class="reply">답변</span></c:if>
												
											</a>
											
											<%--<c:if test="${item.baCommentTotal > 0}">[${item.baCommentTotal}]</c:if> --%>
										</td>
										<c:if test="${not empty boardConfig.bcStatusCode}">
											<td class="pc">${asapro:codeName(item.baStatus, bcStatusCodeList) }</td>
										</c:if>
										<c:if test="${boardConfig.bcSupportRecommend}">
											<td class="pc">
												${item.baRecommend}
											</td>
										</c:if>
										
										<td class="pc">
											<%--
											<c:if test="${not empty item.depId and not empty departmentList}">
												<c:forEach items="${departmentList}" var="department" varStatus="dvs">
													<c:if test="${department.depId eq item.depId}">${department.depName}</c:if>
												</c:forEach>
											</c:if>
											 --%>
											 <%--
											<c:if test="${not empty item.member}">${item.member.memberName}</c:if>
											<c:if test="${empty item.member}">${item.baGuestName}</c:if>
											 --%>
											 관리자
										</td>
										<%--
										<td class="pc">
											<c:if test="${not empty item.baFileInfos }">
												<img src="/design/theme/bucheon/images/sub/s6_07.gif" alt="첨부파일">
											</c:if>
										</td>--%>
										<td class="pc"><fmt:formatDate value="${item.baRegDate}" pattern="yyyy-MM-dd" /></td>
										<td class="pc">${item.baHit}</td>
									</tr>
								</c:forEach>
							</c:if>
							
							
							
							<!-- 일반 게시물 -->
							<c:if test="${empty paging.result}">
								<tr>
									<td colspan="4" class="text-center">등록된 게시글이 없습니다.</td>
								</tr>
							</c:if>
							<c:forEach items="${paging.result}" var="item" varStatus="vs">
								<tr>
									<td>${paging.rowTop - vs.index}</td>
									<c:if test="${not empty boardConfig.bcCategory1 and not empty boardConfig.bcCategory1Name}">
										<td class="pc">${asapro:codeName(item.baCategory1, bcCategory1CodeList) }</td>
									</c:if>
									<c:if test="${not empty boardConfig.bcCategory2 and not empty boardConfig.bcCategory2Name}">
										<td class="pc">${asapro:codeName(item.baCategory2, bcCategory2CodeList) }</td>
									</c:if>
									<c:if test="${not empty boardConfig.bcCategory3 and not empty boardConfig.bcCategory3Name}">
										<td class="pc">${asapro:codeName(item.baCategory3, bcCategory3CodeList) }</td>
									</c:if>
									<%--
									 --%>
									<td class="tlt">
									<%--
										<c:if test="${not empty boardConfig.bcCategory1 and not empty item.baCategory1}">
											<strong>[${asapro:codeName(item.baCategory1, bcCategory1CodeList) }]</strong>
										</c:if>
										 --%>
										<%--<img src="${design.resource}/images/main/main_11.png" alt="" class="alim-icon"/> --%>
										<a href="${item.permLink}?cp=${boardArticleSearch.cp}${boardArticleSearch.queryString}">${item.baTitle}
											<fmt:formatDate value="${item.baRegDate}" pattern="yyyyMMdd" var="regdate" />
											<c:if test="${now - regdate <= 5}"><span class="new">새글</span></c:if>
											<c:if test="${item.baSecret}"><span class="lock">비밀글</span></c:if>
											<c:if test="${not empty item.baAnswer }"><span class="reply">답변</span></c:if>
										</a>
										
										<%--<c:if test="${item.baCommentTotal > 0}">[${item.baCommentTotal}]</c:if> --%>
									</td>
									<c:if test="${not empty boardConfig.bcStatusCode}">
										<td class="pc">${asapro:codeName(item.baStatus, bcStatusCodeList) }</td>
									</c:if>
									<c:if test="${boardConfig.bcSupportRecommend}">
										<td class="pc">
											${item.baRecommend}
										</td>
									</c:if>
									
									<td class="pc">
										<%--
										<c:if test="${not empty item.depId and not empty departmentList}">
											<c:forEach items="${departmentList}" var="department" varStatus="dvs">
												<c:if test="${department.depId eq item.depId}">${department.depName}</c:if>
											</c:forEach>
										</c:if>
										  --%>
										<c:if test="${not empty item.member}">${asapro:maskingNameCenter(item.member.memberName) }</c:if>
										<c:if test="${empty item.member}">${asapro:maskingNameCenter(item.baGuestName) }</c:if>
									</td>
									<%--
									<td class="pc">
										<c:if test="${not empty item.baFileInfos }">
											<img src="/design/theme/bucheon/images/sub/s6_07.gif" alt="첨부파일">
										</c:if>
									</td>--%>
									<td class="pc"><fmt:formatDate value="${item.baRegDate}" pattern="yyyy-MM-dd" /></td>
									<td class="pc">${item.baHit}</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			
				<!-- button -->
				<div class="btn_wrap trt">
					<c:if test="${isBoardManager || bcAllowForm}">
						<a class="btn t_br" href="${APP_PATH}/board/${boardConfig.bcId}/new">글쓰기</a>
					</c:if>
				</div>	

				<!-- paging -->
				<c:out value="${paging.userTextTag}" escapeXml="false"/>
			
				
			</div>
			<!-- content end -->

<!-- footer -->
<c:import url="${design.footer}" charEncoding="UTF-8" />