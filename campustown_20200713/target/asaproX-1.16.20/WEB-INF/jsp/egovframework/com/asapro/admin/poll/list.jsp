<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="asapro" uri="http://www.asadal.com" %>

<c:import url="/WEB-INF/jsp/egovframework/com/asapro/admin/layout/header.jsp" charEncoding="UTF-8" />

<script>
jQuery(function($){
		
	//삭제 Ajax
    $('#deleteBtn').on('click', function (e) {
    	e.preventDefault();
		var poIds = [];
		$('[name=poIds]:checked').each(function(idx, el){
			poIds.push(el.value);
		});

        swal({
            title: '삭제하시겠습니까?',
            html: '삭제한 항목은 복구할 수 없습니다.',
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
                        if (poIds.length === 0) {
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
				url : '${ADMIN_PATH}/poll/delete.do'
				, type : 'post'
				, dataType: 'json'
				, data: {
					poIds : poIds
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

						            <form:form modelAttribute="pollSearch" cssClass="form-inline" action="${ADMIN_PATH}/poll/list.do" method="get">
						            	<form:hidden path="poType"/>
										<div class="form-row mr-auto mt-2">
											<label class="mr-sm-2 mb-2">정렬조건</label>
											<form:select path="sortOrder" cssClass="form-control mr-sm-2 mb-2" title="정렬기준">
												<form:option value="PO_REGDATE" label="등록일시" />
												<form:option value="PO_QUESTION" label="질문" />
											</form:select>
											<form:select path="sortDirection" cssClass="form-control mr-sm-2 mb-2" title="정렬방향">
												<form:option value="DESC" label="내림차순" />
												<form:option value="ASC" label="오름차순" />
											</form:select>
										</div>
										
										<div class="form-row mt-2" >
											<label for="sc" class="mr-sm-2 mb-2">검색조건</label>
											<form:select path="poUse" cssClass="form-control mr-sm-2 mb-2" title="게시여부">
												<option value="">게시여부</option>
												<form:option value="true" label="게시" />
												<form:option value="false" label="게시안함" />
											</form:select>
											<form:select path="poStatus" cssClass="form-control mr-sm-2 mb-2" title="진행여부">
												<option value="">진행상태</option>
												<form:option value="ongoing" label="진행중" />
												<form:option value="complete" label="종료" />
											</form:select>
											<form:select path="sc" cssClass="form-control mr-sm-2 mb-2" title="검색조건">
												<option value="">전체</option>
												<form:option value="PO_QUESTION" label="질문" />
												<form:option value="PO_DESCRIPTION" label="설명" />
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
									<h4 class="mt-0 header-title">질문목록</h4>
								</div>
								<div class="card-body">
									
									<!-- info -->
									<!-- <div class="p-3 mb-2 bg-light text-info font-weight-bold"><i class="mdi mdi-information"></i> 순서변경은 드래그하여 이동 후 순서저장버튼</div> -->
									
									<!-- alert maeeage -->
									<c:import url="/WEB-INF/jsp/egovframework/com/asapro/admin/include/message.jsp" charEncoding="UTF-8" />
						           
						            <div class="table-responsive">
										<table class="table table-striped table-hover">
											<colgroup>
												<col style="width: 30px;" /><!-- 체크 -->
												<col style="width: 60px; "/><!-- 상태 -->
												<col style="width: 10%; "/><!-- 구분 -->
												<col style="width: 120px; "/><!-- 이미지 -->
												<col /><!-- 질문 -->
												<col style="width: 6%;" /><!-- 찬성 -->
												<col style="width: 6%;" /><!-- 반대 -->
												<col style="width: 10%; "/><!-- 기간 -->
												<col style="width: 6%; "/><!-- 진행상태 -->
												<col style="width: 10%;" /><!-- 등록일시 -->
											</colgroup>
											<thead>
												<tr>
													<th class="text-center">
														<div class="custom-control custom-checkbox">
															<input type="checkbox" class="custom-control-input" id="selectAll" onclick="jQuery('[name=poIds]').each(function(idx,el){this.checked=jQuery('#selectAll')[0].checked;});"/>
															<label class="custom-control-label" for="selectAll"></label>
														</div>
													</th>
													<th>상태</th>
													<th>구분</th>
													<th>이미지</th>
													<th>제목</th>
													<th>찬성</th>
													<th>반대</th>
													<th>투표기간</th>
													<th>진행상태</th>
													<th>등록일시</th>
												</tr>
											</thead>
											<tbody>
											
												<c:if test="${empty paging.result}">
													<tr>
														<td colspan="10" class="text-center">등록된 데이터가 없습니다.</td>
													</tr>
												</c:if>
												<c:forEach items="${paging.result}" var="item" varStatus="vs">
													<tr>
														<td class="text-center">
															<div class="custom-control custom-checkbox">
																<input type="checkbox" name="poIds" value="${item.poId}" class="custom-control-input" id="poIds${item.poId }" />
																<label class="custom-control-label" for="poIds${item.poId }"></label>
															</div>
														</td>
														<td>
															<c:choose>
																<c:when test="${item.poUse}">
																	<span class="badge badge-info">게시</span>
																		<%-- <c:choose>
																			<c:when test="${not empty item.poStartDate and item.poStartDate > pollSearch.poToday}"><span class="badge badge-success">대기중</span></c:when>
																			<c:when test="${not empty item.poEndDate and item.poEndDate < pollSearch.poToday}"><span class="badge badge-warning">기간종료</span></c:when>
																			<c:otherwise>
																			</c:otherwise>
																		</c:choose> --%>
																</c:when>
																<c:when test="${!item.poUse}"><span class="badge badge-danger">게시안함</span></c:when>
															</c:choose>
														</td>
														<td>
															${item.poType}
														</td>
														<td>
															<c:if test="${not empty item.thumb && item.thumb.fileId > 0}">
																<a href="${ADMIN_PATH}/poll/update.do?poId=${item.poId}">
																	<img class="img-thumbnail" style="max-width: 80px; max-height: 80px;" src="${item.thumb.fileServletUrl}" alt="${item.thumb.fileAltText}" onerror="this.src='${CONTEXT_PATH }/assets/images/noImage300x300.jpg'" />
																</a>
															</c:if>
														</td>
														<td style="word-break: break-all">
															<a href="${ADMIN_PATH}/poll/update.do?poId=${item.poId}">${item.poQuestion}</a>
														</td>
														<td>
															<i class="ti-thumb-up"></i>
															${item.poYesCnt}
														</td>
														<td>
															<i class="ti-thumb-down"></i>
															${item.poNoCnt}
														</td>
														<td>
															<c:if test="${not empty item.poStartDate}"><div>시작:${asapro:formatStringDate(item.poStartDate,'yyyy-MM-dd','yyyy.MM.dd')}</div></c:if>
															<c:if test="${not empty item.poEndDate}"><div>종료:${asapro:formatStringDate(item.poEndDate,'yyyy-MM-dd','yyyy.MM.dd') }</div></c:if>
														</td>
														<td>
															<c:choose>
																<c:when test="${item.poEndDate >= pollSearch.poToday}">
																		<c:choose>
																			<c:when test="${not empty item.poStartDate and item.poStartDate > pollSearch.poToday}"><span class="badge badge-success">대기중</span></c:when>
																			<c:when test="${not empty item.poStartDate and item.poStartDate <= pollSearch.poToday}"><span class="badge badge-warning">진행중</span></c:when>
																			<c:otherwise>
																			</c:otherwise>
																		</c:choose>
																</c:when>
																<c:when test="${item.poEndDate < pollSearch.poToday}">
																	<span class="badge badge-secondary">기간종료</span>
																</c:when>
															</c:choose>
														</td>
														<td>
															<fmt:formatDate value="${item.poRegDate}" pattern="yyyy-MM-dd" />
														</td>
														
													</tr>
												</c:forEach>
											</tbody>
										</table>
									</div>
									<div class="form-inline">
										<div class="mr-auto">
											<a href="${ADMIN_PATH}/poll/insert.do?poType=${pollSearch.poType}" class="btn btn-primary waves-effect waves-light">추가</a>
											<button id="deleteBtn" class="btn btn-danger waves-effect waves-light">삭제</button>
										</div>
										<div>
											<span>총 ${paging.rowTotal}개의 게시물이 있습니다.</span>
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