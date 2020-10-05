<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="egovframework.com.asapro.member.admin.service.AdminMember"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="asapro" uri="http://www.asadal.com" %>
<% AdminMember currentAdmin = (AdminMember) request.getSession().getAttribute("currentAdmin"); %>
<c:import url="/WEB-INF/jsp/egovframework/com/asapro/admin/layout/header.jsp" charEncoding="UTF-8" />
<c:if test="${not empty messageCode}"><script>alert('<spring:message code="${messageCode}"></spring:message>');</script></c:if>

<script>
jQuery(function($){
		
	//삭제 Ajax
    $('#deleteBtn').on('click', function () {
    	var baIds = [];
		$('[name=baIds]:checked').each(function(idx, el){
			baIds.push(el.value);
		});

        swal({
            title: '삭제하시겠습니까?',
            html: '삭제한 항목은 복구할 수 없습니다.\r\n댓글도 삭제됩니다.',
            //input: 'email',
            showCancelButton: true,
            confirmButtonText: '삭제',
            showLoaderOnConfirm: true,
            cancelButtonText: '취소',
            confirmButtonClass: 'btn btn-success',
            cancelButtonClass: 'btn btn-danger m-l-10',
            preConfirm: function () {
                return new Promise(function (resolve, reject) {
                   // setTimeout(function () {
                        if (baIds.length === 0) {
                            reject('삭제할 항목을 선택해 주세요.');
                        } else {
                            resolve();
                        }
                   // }, 1000)
                })
            },
            allowOutsideClick: false
        }).then(function () {
        	$.ajax({
				url : '${ADMIN_PATH}/board/article/delete.do'
				, type : 'post'
				, dataType: 'json'
				, data: {
					baIds : baIds,
					bcId  : '${boardConfig.bcId }'
				}
			}).done(function(result){
				if(result.success){
					swal({
		                type: 'success',
		                title: '삭제가 완료되었습니다.',
		                //실제 삭제건수는 컨트롤러에서 삭제건수 받아와 뿌려주는게 정상/임시로 해놓음
		                html: '삭제결과 : ' + result.successCnt + '건',
		                //showCancelButton: true,
	                    //confirmButtonClass: 'btn btn-success',
	                    //cancelButtonClass: 'btn btn-danger m-l-10'
		            }).then(function(){
						location.reload();
		            });
				} else {
					if(result.text){
						swal({
			                type: 'error',
			                title: result.text
			            });
					} else {
						swal({
			                type: 'error',
			                title: '삭제하지 못하였습니다.[error]'
			            });
					}
				}
			}).fail(function(result){
				swal({
	                type: 'error',
	                title: '삭제하지 못하였습니다.[fail]'
	            });
			});
        });
    });
	
  	//기간 달력
	$('.datepicker-ui').datepicker();
	
});
</script>

	<!-- Left Sidebar -->	
	<c:import url="/WEB-INF/jsp/egovframework/com/asapro/admin/layout/left.jsp" charEncoding="UTF-8" />

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

						            <form:form modelAttribute="boardArticleSearch" cssClass="" action="${ADMIN_PATH}/board/article/list.do" method="get">
										<div class="form-row mr-auto mt-2 form-inline">
											<label class="mr-sm-2 mb-2">정렬조건</label>
											<form:select path="pageSize" cssClass="form-control mr-sm-2 mb-2" title="페이지크기" >
												<form:option value="${boardConfig.bcPageSize}">${boardConfig.bcPageSize}개씩</form:option>
												<form:option value="${boardConfig.bcPageSize * 2}">${boardConfig.bcPageSize * 2}개씩</form:option>
												<form:option value="${boardConfig.bcPageSize * 4}">${boardConfig.bcPageSize * 4}개씩</form:option>
											</form:select>
											<form:select path="sortOrder" cssClass="form-control mr-sm-2 mb-2" title="정렬기준">
												<form:option value="BA_REGDATE" label="등록일시" />
												<form:option value="BA_TITLE" label="제목" />
												<form:option value="BA_HIT" label="조회순" />
											</form:select>
											<form:select path="sortDirection" cssClass="form-control mr-sm-2 mb-2" title="정렬방향">
												<form:option value="DESC" label="내림차순" />
												<form:option value="ASC" label="오름차순" />
											</form:select>
										</div>
										<div class="form-row mt-2 form-inline" >
											<label for="sc" class="mr-sm-3 mb-2">작 성 일</label>
											<form:input path="startDate" cssClass="form-control mr-sm-1 datepicker-ui" cssErrorClass="form-control mr-sm-1 datepicker-ui is-invalid" autocomplete="off"/>
											<span class="mx-2"> ~ </span>
											<form:input path="endDate" cssClass="form-control mr-sm-1 datepicker-ui" cssErrorClass="form-control mr-sm-1 datepicker-ui is-invalid" autocomplete="off"/>
										</div>
										<div class="form-row mt-2 form-inline" >
											<label for="sc" class="mr-sm-2 mb-2">검색조건</label>
											<c:if test="${not empty boardConfigList}">
											<form:select path="bcId" cssClass="form-control mr-sm-2 mb-2" title="게시판명">
												<form:options items="${boardConfigList}" itemLabel="bcName" itemValue="bcId"/>
											</form:select>
											</c:if>
											<c:if test="${not empty bcCategory1CodeList || not empty bcCategory2CodeList || not empty bcCategory3CodeList}">
												<c:if test="${not empty boardConfig.bcCategory1 and not empty boardConfig.bcCategory1Name}">
												<form:select path="baCategory1" cssClass="form-control mr-sm-2 mb-2" title="${boardConfig.bcCategory1Name}">
													<option value="">${boardConfig.bcCategory1Name}</option>
													<form:options items="${bcCategory1CodeList}" itemLabel="codeName" itemValue="codeId"/>
												</form:select>
												</c:if>
												<c:if test="${not empty boardConfig.bcCategory2 and not empty boardConfig.bcCategory2Name}">
												<form:select path="baCategory2" cssClass="form-control mr-sm-2 mb-2" title="${boardConfig.bcCategory2Name}">
													<option value="">${boardConfig.bcCategory2Name}</option>
													<form:options items="${bcCategory2CodeList}" itemLabel="codeName" itemValue="codeId"/>
												</form:select>
												</c:if>
												<c:if test="${not empty boardConfig.bcCategory3 and not empty boardConfig.bcCategory3Name}">
												<form:select path="baCategory3" cssClass="form-control mr-sm-2 mb-2" title="${boardConfig.bcCategory3Name}">
													<option value="">${boardConfig.bcCategory3Name}</option>
													<form:options items="${bcCategory3CodeList}" itemLabel="codeName" itemValue="codeId"/>
												</form:select>
												</c:if>
											</c:if>
											<c:if test="${boardConfig.bcSupportMainSelec}">
												<form:select path="baMainSelec" cssClass="form-control mr-sm-2 mb-2" title="메인추출여부">
													<option value="">메인추출여부</option>
													<form:option value="true" label="메인추출" />
												</form:select>
											</c:if>
											<c:if test="${boardConfig.bcSupportCommSelec}">
												<form:select path="baCommSelec" cssClass="form-control mr-sm-2 mb-2" title="공통추출여부">
													<option value="">공통추출여부</option>
													<form:option value="true" label="공통추출" />
												</form:select>
											</c:if>
											<form:select path="baUse" cssClass="form-control mr-sm-2 mb-2" title="게시여부">
												<option value="">게시여부</option>
												<form:option value="true" label="게시" />
												<form:option value="false" label="게시안함" />
											</form:select>
											<form:select path="sc" cssClass="form-control mr-sm-2 mb-2" title="검색조건">
												<option value="">전체</option>
												<form:option value="baTitle" label="제목" />
												<form:option value="baContentPlain" label="내용" />
												<form:option value="baMemberName" label="작성자이름" />
												<form:option value="bsMemberId" label="작성자아이디" />
											</form:select>	
											<form:input path="sv" cssClass="form-control mr-sm-2 mb-2" title="검색어" placeholder="Search"/>
											<button class="btn btn-outline-success waves-effect mb-2 " type="submit">검색</button>
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
								<div class="card-header">
									<h4 class="mt-0 header-title">${boardConfig.bcName }</h4>
								</div>
							
								<div class="card-body">
									
									<!-- alert maeeage -->
									<c:import url="/WEB-INF/jsp/egovframework/com/asapro/admin/include/message.jsp" charEncoding="UTF-8" />
						           
						            <div class="table-responsive">
										<table class="table table-striped table-hover">
											<colgroup>
												<col style="width: 30px;" /><!-- 체크 -->
												<col style="width: 70px;" /><!-- 순서 -->
												<c:if test="${not empty boardConfig.bcCategory1 and not empty boardConfig.bcCategory1Name}">
													<col /><!-- 구분1 -->
												</c:if>
												<c:if test="${not empty boardConfig.bcCategory2 and not empty boardConfig.bcCategory2Name}">
													<col /><!-- 구분2 -->
												</c:if>
												<c:if test="${not empty boardConfig.bcCategory3 and not empty boardConfig.bcCategory3Name}">
													<col /><!-- 구분3 -->
												</c:if>
												<c:if test="${boardConfig.bcSupportImage}">
												<col style="width: 80px;" /><!-- 대표이미지 -->
												</c:if>
												<col /><!-- 제목 -->
												<col /><!-- 첨부 -->
												<col style="width: 8%;" /><!-- 작성자 -->
												<col style="width: 8%;" /><!-- 작성일 -->
												<col style="width: 8%;" /><!-- 게시여부 -->
												<c:if test="${not empty boardConfig.bcStatusCode}">
												<col/><!-- 상태 -->
												</c:if>
												<c:if test="${boardConfig.bcSupportRecommend}">
												<col style="width: 5%;" /><!-- 추천 -->
												</c:if>
												<col style="width: 5%;" /><!-- 조회 -->
												<c:if test="${boardConfig.bcSupportComment}">
												<col style="width: 5%;" /><!-- 댓글보기 -->
												</c:if>
											</colgroup>
											<thead>
												<tr>
													<th class="text-center">
														<div class="custom-control custom-checkbox">
															<input type="checkbox" class="custom-control-input" id="selectAll" onclick="jQuery('[name=baIds]').each(function(idx,el){this.checked=jQuery('#selectAll')[0].checked;});"/>
															<label class="custom-control-label" for="selectAll"></label>
														</div>
													</th>
													<th>순서</th>
													<c:if test="${not empty boardConfig.bcCategory1 and not empty boardConfig.bcCategory1Name}">
														<th>${boardConfig.bcCategory1Name}</th>
													</c:if>
													<c:if test="${not empty boardConfig.bcCategory2 and not empty boardConfig.bcCategory2Name}">
														<th>${boardConfig.bcCategory2Name}</th>
													</c:if>
													<c:if test="${not empty boardConfig.bcCategory3 and not empty boardConfig.bcCategory3Name}">
														<th>${boardConfig.bcCategory3Name}</th>
													</c:if>
													<c:if test="${boardConfig.bcSupportImage}">
														<th>이미지</th>
													</c:if>
													<th>제목</th>
													<th>첨부</th>
													<th>작성자</th>
													<th>작성일</th>
													<th>게시여부</th>
													<c:if test="${not empty boardConfig.bcStatusCode}">
														<th>상태</th>
													</c:if>
													<c:if test="${boardConfig.bcSupportRecommend}">
														<th>추천</th>
													</c:if>
													<th>조회</th>
													<c:if test="${boardConfig.bcSupportComment}">
														<th>댓글보기</th>
													</c:if>
												</tr>
											</thead>
											<tbody>
											
												<c:if test="${not empty noticeList}">
													<c:forEach items="${noticeList}" var="item" varStatus="vs">
														<tr>
															<td class="text-center">
																<div class="custom-control custom-checkbox">
																	<input type="checkbox" name="baIds" value="${item.baId}" class="custom-control-input" id="baIds${item.baId }" />
																	<label class="custom-control-label" for="baIds${item.baId }"></label>
																</div>
															</td>
															<td>
																<span class="badge badge-info">공지</span>
															</td>
															<c:if test="${not empty boardConfig.bcCategory1 and not empty boardConfig.bcCategory1Name}">
																<td>${asapro:codeName(item.baCategory1, bcCategory1CodeList) }</td>
															</c:if>
															<c:if test="${not empty boardConfig.bcCategory2 and not empty boardConfig.bcCategory2Name}">
																<td>${asapro:codeName(item.baCategory2, bcCategory2CodeList) }</td>
															</c:if>
															<c:if test="${not empty boardConfig.bcCategory3 and not empty boardConfig.bcCategory3Name}">
																<td>${asapro:codeName(item.baCategory3, bcCategory3CodeList) }</td>
															</c:if>
															<c:if test="${boardConfig.bcSupportImage}">
																<td>
																	<c:if test="${not empty item.thumb && item.thumb.fileId > 0}">
																		<img class="img-thumbnail" style="max-width: 80px; max-height: 80px;" src="${item.thumb.fileServletThumbnailUrl}" onerror="this.src='${CONTEXT_PATH }/assets/images/noImage300x300.jpg'" />
																	</c:if>
																</td>
															</c:if>
															<td>
																<a href="${ADMIN_PATH}/board/article/update.do?bcId=${item.bcId}&baId=${item.baId}" class="">${item.baTitle}</a>
																<c:if test="${item.baCommentTotal > 0}">[${item.baCommentTotal}]</c:if>
																<c:if test="${not empty currentSite.seperatedSiteDomains}">
																	<a href="http://${currentSite.seperatedSiteDomains[0]}${item.permLink}?baCategory1=${item.baCategory1}&baCategory2=${item.baCategory2}&baCategory3=${item.baCategory3}" class="badge badge-success" target="_blank" title="보기"> 바로가기</a>
																</c:if>
																<c:if test="${empty currentSite.seperatedSiteDomains}">
																	<a href="${item.permLink}?baCategory1=${item.baCategory1}&baCategory2=${item.baCategory2}&baCategory3=${item.baCategory3}" class="badge badge-success" target="_blank" title="보기"> 바로가기</a>
																</c:if>
															</td>
															<td>
																<c:forEach items="${item.baFileInfos}" var="fileItem" varStatus="fvs">
																<a href="${APP_PATH}/file/download/uu/${fileItem.fileUUID}" title="<c:out value="${fileItem.fileOriginalName}" escapeXml="true" />"><asapro:fileIcon fileInfo="${fileItem}" /></a>
																</c:forEach> 
															</td>
															<td>
																<c:if test="${not empty item.member}">${item.member.memberName}</c:if>
																<%--
																<c:if test="${not empty item.depId and not empty departmentList}">
																	<c:forEach items="${departmentList}" var="department" varStatus="dvs">
																		<c:if test="${department.depId eq item.depId}">${department.depName}</c:if>
																	</c:forEach>
																</c:if>
																 --%>
																<c:if test="${empty item.member}">${item.baGuestName}</c:if>
															</td>
															<td>
																<fmt:formatDate value="${item.baRegDate}" pattern="yyyy-MM-dd" />
															</td>
															<td>
																<c:if test="${!item.baUse}"><span class="badge badge-warning">미게시</span></c:if>
															</td>
															<c:if test="${not empty boardConfig.bcStatusCode}">
																<td>${asapro:codeName(item.baStatus, bcStatusCodeList) }</td>
															</c:if>
															<c:if test="${boardConfig.bcSupportRecommend}">
																<td>
																	${item.baRecommend}
																</td>
															</c:if>
															<td>${item.baHit}</td>
															<c:if test="${boardConfig.bcSupportComment}">
																<td><a href="${ADMIN_PATH}/comment/board/list.do?cmtModItemId=${item.baId}" class="btn btn-outline-primary waves-effect waves-light btn-sm">댓글</a></td>
															</c:if>
														</tr>
													</c:forEach>
												</c:if>
											
											
												<c:if test="${empty paging.result}">
													<tr>
														<td colspan="14" class="text-center">등록된 게시글이 없습니다.</td>
													</tr>
												</c:if>
												<c:forEach items="${paging.result}" var="item" varStatus="vs">
													<tr>
															<td class="text-center">
																<div class="custom-control custom-checkbox">
																	<input type="checkbox" name="baIds" value="${item.baId}" class="custom-control-input" id="baIds${item.baId }" />
																	<label class="custom-control-label" for="baIds${item.baId }"></label>
																</div>
															</td>
															<td>${paging.rowTop - vs.index}</td>
															<c:if test="${not empty boardConfig.bcCategory1 and not empty boardConfig.bcCategory1Name}">
																<td>${asapro:codeName(item.baCategory1, bcCategory1CodeList) }</td>
															</c:if>
															<c:if test="${not empty boardConfig.bcCategory2 and not empty boardConfig.bcCategory2Name}">
																<td>${asapro:codeName(item.baCategory2, bcCategory2CodeList) }</td>
															</c:if>
															<c:if test="${not empty boardConfig.bcCategory3 and not empty boardConfig.bcCategory3Name}">
																<td>${asapro:codeName(item.baCategory3, bcCategory3CodeList) }</td>
															</c:if>
															<c:if test="${boardConfig.bcSupportImage}">
																<td>
																	<c:if test="${not empty item.thumb && item.thumb.fileId > 0}">
																		<%--<img class="img-thumbnail" style="max-width: 80px; max-height: 80px;" src="${item.thumb.fileThumbnailPath}" onerror="this.src='${CONTEXT_PATH }/assets/images/noImage300x300.jpg'" /> --%>
																		<img class="img-thumbnail" style="max-width: 80px; max-height: 80px;" src="${item.thumb.fileServletThumbnailUrl}" onerror="this.src='${CONTEXT_PATH }/assets/images/noImage300x300.jpg'" />
																	</c:if>
																</td>
															</c:if>
															<td>
																<a href="${ADMIN_PATH}/board/article/update.do?bcId=${item.bcId}&baId=${item.baId}" class="">${item.baTitle}</a><c:if test="${not empty item.baAnswer }"><span class="badge badge-warning">[답변완료]</span></c:if>
																<c:if test="${item.baCommentTotal > 0}">[${item.baCommentTotal}]</c:if>
																<c:if test="${item.baSecret}"><span class="text-warning"><i class="mdi mdi-lock"></i></span></c:if>
																<c:if test="${not empty currentSite.seperatedSiteDomains}">
																	<a href="http://${currentSite.seperatedSiteDomains[0]}${item.permLink}?baCategory1=${item.baCategory1}&baCategory2=${item.baCategory2}&baCategory3=${item.baCategory3}" class="badge badge-success" target="_blank" title="보기"> 바로가기</a>
																</c:if>
																<c:if test="${empty currentSite.seperatedSiteDomains}">
																	<a href="${item.permLink}?baCategory1=${item.baCategory1}&baCategory2=${item.baCategory2}&baCategory3=${item.baCategory3}" class="badge badge-success" target="_blank" title="보기"> 바로가기</a>
																</c:if>
															</td>
															<td>
																<c:forEach items="${item.baFileInfos}" var="fileItem" varStatus="fvs">
																<a href="${APP_PATH}/file/download/uu/${fileItem.fileUUID}" title="<c:out value="${fileItem.fileOriginalName}" escapeXml="true" />"><asapro:fileIcon fileInfo="${fileItem}" /></a>
																</c:forEach> 
															</td>
															<td>
																<c:if test="${not empty item.member}">${item.member.memberName}</c:if>
																<%--
																<c:if test="${not empty item.depId and not empty departmentList}">
																	<c:forEach items="${departmentList}" var="department" varStatus="dvs">
																		<c:if test="${department.depId eq item.depId}">${department.depName}</c:if>
																	</c:forEach>
																</c:if>
																 --%>
																<c:if test="${empty item.member}">${item.baGuestName}</c:if>
															</td>
															<td>
																<fmt:formatDate value="${item.baRegDate}" pattern="yyyy-MM-dd" />
															</td>
															<td>
																<c:if test="${!item.baUse}"><span class="badge badge-warning">게시안함</span></c:if>
															</td>
															<c:if test="${not empty boardConfig.bcStatusCode}">
																<td>${asapro:codeName(item.baStatus, bcStatusCodeList) }</td>
															</c:if>
															<c:if test="${boardConfig.bcSupportRecommend}">
																<td>
																	${item.baRecommend}
																</td>
															</c:if>
															<td>${item.baHit}</td>
															<c:if test="${boardConfig.bcSupportComment}">
																<td><a href="${ADMIN_PATH}/comment/board/list.do?cmtModItemId=${item.baId}" class="btn btn-outline-primary waves-effect waves-light btn-sm">댓글</a></td>
															</c:if>
														</tr>
												</c:forEach>
											</tbody>
										</table>
									</div>
									<div class="form-inline">
										<div class="mr-auto">
											<a href="${ADMIN_PATH}/board/article/insert.do?bcId=${boardConfig.bcId }" class="btn btn-primary waves-effect waves-light">글추가</a>
											<button id="deleteBtn" class="btn btn-danger waves-effect waves-light">삭제</button>
											<a href="${ADMIN_PATH}/board/config/list.do" class="btn btn-secondary waves-effect m-l-5">게시판목록</a>
										</div>
										<div>
											<span>총 ${paging.rowTotal}개의 게시글이 있습니다.</span>
										</div>
									</div>

									<!-- paging -->
									<div>
										<c:out value="${paging.adminTextTag}" escapeXml="false"/>
									</div>
									
								</div>
							</div>
						</div>
					</div><!-- 목록 -->
					
					<!-- ============================= //메뉴별 컨텐츠 ============================ -->
			    </div><!-- container -->
			
			
			</div> <!-- Page content Wrapper -->
	
	    </div> <!-- content -->
	
	</div>
	<!-- End Right content here -->

<c:import url="/WEB-INF/jsp/egovframework/com/asapro/admin/layout/footer.jsp" charEncoding="UTF-8" />