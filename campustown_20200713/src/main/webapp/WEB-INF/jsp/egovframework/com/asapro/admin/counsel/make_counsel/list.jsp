<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="asapro" uri="http://www.asadal.com" %>

<c:import url="/WEB-INF/jsp/egovframework/com/asapro/admin/layout/header.jsp" charEncoding="UTF-8" />
<script>
	jQuery(function($){
		//달력
		$('.datepicker-ui').datepicker({
			showOn: "focus"
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
</script>


<!-- Left Sidebar -->
<c:import url="/WEB-INF/jsp/egovframework/com/asapro/admin/layout/left.jsp" charEncoding="UTF-8" />

	<form id="counselForm" name="counselForm" method="post" action="<c:url value='${ADMIN_PATH}/counsel/make_counsel/list.do'/>">
		
		<!-- Start right Content here -->
		<div class="content-page">
			<!-- Start content -->
			<div class="content">

				<!-- Top Bar -->
				<c:import url="/WEB-INF/jsp/egovframework/com/asapro/admin/layout/top.jsp" charEncoding="UTF-8" />

				<div class="page-content-wrapper ">
					<div class="container-fluid">

						<!-- location -->
						<c:import url="/WEB-INF/jsp/egovframework/com/asapro/admin/include/location.jsp" charEncoding="UTF-8" />

						<!-- ============================= 메뉴별 컨텐츠 ============================ -->
						<!-- 검색폼 -->
						<div class="row">
							<div class="col-sm-12">
								<div class="card m-b-15">
									<div class="card-body">

										<div class="form-row mt-2 form-inline" >
											<label for="" class="mr-sm-3 mb-2">작성일</label>

											<form:input path="domain.startDate" cssClass="form-control mr-sm-2 mb-2 datepicker-ui" cssErrorClass="form-control col-sm-1 datepicker-ui is-invalid" autocomplete="off" readonly="true"/>
											<span class="mx-2"> ~ </span>
											<form:input path="domain.endDate" cssClass="form-control mr-sm-2 mb-2 datepicker-ui" cssErrorClass="form-control col-sm-1 datepicker-ui is-invalid" autocomplete="off" readonly="true"/>
										</div>

										<div class="form-row mt-2 form-inline" >
											<label class="mr-sm-2 mb-2">검색조건</label>

											<form:select path="domain.searchDiv" cssClass="form-control mr-sm-2 mb-2" title="관리구분">
												<option value="title" label="제목" <c:if test="${domain.searchDiv eq 'title'}">selected</c:if>></option>
												<option value="productDetail" label="내용" <c:if test="${domain.searchDiv eq 'productDetail'}">selected</c:if>></option>
											</form:select>

											<form:input path="domain.searchWord" cssClass="form-control mr-sm-2 mb-2" title="검색어" placeholder="Search" value="${empty domain.searchWord?'':domain.searchWord}" />

											<button id="searchBtn" class="btn btn-outline-success waves-effect mr-sm-2 mb-2" type="submit">검색</button>

										</div>


									</div>
								</div>
							</div>
						</div>
						<!-- End 검색폼 -->



						<!-- 목록 -->
						<div class="row">
							<div class="col-sm-12">
								<div class="card m-b-30">
									<div class="card-body">

										<div class="table-responsive">
											<table class="table table-striped table-hover ">
<%--												<caption>시제품 제작 및 자문</caption>--%>
												<colgroup>
													<col style="width: 7%">
													<col style="width: 25%">
													<col style="width: auto">
													<col style="width: 13%">
													<col style="width: 13%">
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
													<tr onclick="location.href='${ADMIN_PATH}/counsel/make_counsel/view/${item.makeCounselSeq}.do'">
														<td>${paging.rowTop - vs.index}</td>
														<td>
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
														<td>${item.title}</td>
														<td>${item.teamName}</td>
														<td>${item.regName}</td>
														<td><fmt:formatDate value="${item.regDate}" pattern="yyyy.MM.dd"/></td>
													</tr>
												</c:forEach>

												</tbody>
											</table>
										</div>


										<div class="form-inline">
											<div class="mr-auto">
												<a href="${ADMIN_PATH}/counsel/make_counsel/write.do" class="btn btn-primary waves-effect waves-light">시제품 제작 및 자문 의뢰</a>
											</div>
											<div>
												<span>총 ${paging.rowTotal}건의 데이터가 있습니다.</span>
											</div>
										</div>

										<!-- paging -->
										<div>
											<c:out value="${paging.adminTextTag}" escapeXml="false"/>
										</div>

									</div>
								</div>
							</div>
						</div>
						<!-- End 목록 -->




					</div> <!-- End container -->


				</div> <!-- End Page content Wrapper -->

			</div> <!-- End content -->
		</div>
		<!-- End Right content here -->

	<c:import url="/WEB-INF/jsp/egovframework/com/asapro/admin/layout/footer.jsp" charEncoding="UTF-8" />