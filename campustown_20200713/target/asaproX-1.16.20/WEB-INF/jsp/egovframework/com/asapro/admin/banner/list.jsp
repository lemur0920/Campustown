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
		var bnIds = [];
		$('[name=bnIds]:checked').each(function(idx, el){
			bnIds.push(el.value);
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
                        if (bnIds.length === 0) {
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
				url : '${ADMIN_PATH}/banner/delete.do'
				, type : 'post'
				, dataType: 'json'
				, data: {
					bnIds : bnIds
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
	
	//순서저장 Ajax
	$('#saveOrderBtn').on('click', function (e) {
		e.preventDefault();
		var bnIds = [];
		$('[name=bnIds]').each(function(idx, el){
			bnIds.push(el.value);
		});

        swal({
            title: '순서변경',
            html: '출력 순서를 변경하시겠습니까?',
            //input: 'email',
            showCancelButton: true,
            confirmButtonText: '변경',
            showLoaderOnConfirm: true,
            cancelButtonText: '취소',
            confirmButtonClass: 'btn btn-success',
            cancelButtonClass: 'btn btn-danger m-l-10',
            preConfirm: function () {
                return new Promise(function (resolve, reject) {
                   // setTimeout(function () {
                        if (bnIds.length === 0) {
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
				url : '${ADMIN_PATH}/banner/reOrder.do'
				, type : 'post'
				, dataType: 'json'
				, data: {
					bnIds : bnIds
				}
			}).done(function(result){
				if(result.success){
					swal({
		                type: 'success',
		                title: '수정되었습니다.',
		                html: '수정결과 : ' + result.successCnt + '건',
		                //showCancelButton: true,
	                    //confirmButtonClass: 'btn btn-success',
	                    //cancelButtonClass: 'btn btn-danger m-l-10'
		            }).then(function(){
						location.reload();
						//location.href = 'list.do?bnType=${bannerSearch.bnType}';
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
			                title: '수정하지 못하였습니다.[error]'
			            });
					}
				}
			}).fail(function(result){
				swal({
	                type: 'error',
	                title: '수정하지 못하였습니다.[fail]'
	            });
			});
        });
    });
	
	
	//테이블 DnD 순서변경
	$(function(){
		$("table").tableDnD({
	        onDragClass: "table-warning"
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

						            <form:form modelAttribute="bannerSearch" cssClass="form-inline" action="${ADMIN_PATH}/banner/list.do" method="get">
						            	<form:hidden path="bnType"/>
										<div class="form-row mr-auto mt-2">
											<label class="mr-sm-2 mb-2">정렬조건</label>
											<form:select path="sortOrder" cssClass="form-control mr-sm-2 mb-2" title="정렬기준">
												<form:option value="BN_ORDER" label="순서" />
												<form:option value="BN_REGDATE" label="등록일시" />
											</form:select>
											<form:select path="sortDirection" cssClass="form-control mr-sm-2 mb-2" title="정렬방향">
												<form:option value="ASC" label="오름차순" />
												<form:option value="DESC" label="내림차순" />
											</form:select>
										</div>
										
										<div class="form-row mt-2" >
											<label for="sc" class="mr-sm-2 mb-2">검색조건</label>
											<form:select path="bnUse" cssClass="form-control mr-sm-2 mb-2" title="게시여부">
												<option value="">게시여부</option>
												<form:option value="true" label="게시" />
												<form:option value="false" label="게시안함" />
											</form:select>
											<form:select path="sc" cssClass="form-control mr-sm-2 mb-2" title="검색조건">
												<option value="">전체</option>
												<form:option value="bnName" label="이름" />
												<form:option value="bnLink" label="링크" />
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
								<div class="card-body">
									
									<!-- info -->
									<div class="p-3 mb-2 bg-light text-info font-weight-bold"><i class="mdi mdi-information"></i> 순서변경은 드래그하여 이동 후 순서저장버튼</div>
									
									<!-- alert maeeage -->
									<c:import url="/WEB-INF/jsp/egovframework/com/asapro/admin/include/message.jsp" charEncoding="UTF-8" />
						           
						            <div class="table-responsive">
										<table class="table table-striped table-hover">
											<colgroup>
												<col style="width: 30px;" /><!-- 체크 -->
												<col style="width: 60px; "/><!-- 상태 -->
												<c:if test="${bannerSearch.bnType == 'static'}">
													<col style="width: 40px;" /><!-- 아이디 -->
												</c:if>
												<col style="width: 60px;" /><!-- 순서 -->
												<col /><!-- 이름 -->
												<col /><!-- 이미지 -->
												<col /><!-- 링크 -->
												<col style="width: 60px;" /><!-- 새창 -->
												<c:if test="${bannerSearch.bnType == 'popupzone' || bannerSearch.bnType == 'layer'}">
													<col style="width: 140px; "/><!-- 기간 -->
												</c:if>
												<col style="width: 120px;" /><!-- 등록일시 -->
											</colgroup>
											<thead>
												<tr>
													<th class="text-center">
														<div class="custom-control custom-checkbox">
															<input type="checkbox" class="custom-control-input" id="selectAll" onclick="jQuery('[name=bnIds]').each(function(idx,el){this.checked=jQuery('#selectAll')[0].checked;});"/>
															<label class="custom-control-label" for="selectAll"></label>
														</div>
													</th>
													<th>상태</th>
													<c:if test="${bannerSearch.bnType == 'static'}">
														<th>ID</th>
													</c:if>
													<th>순서</th>
													<th>제목</th>
													<th>이미지</th>
													<th>링크</th>
													<th>새창</th>
													<c:if test="${bannerSearch.bnType == 'popupzone' || bannerSearch.bnType == 'layer'}">
														<th>게시기간</th>
													</c:if>
													<th>등록일시</th>
												</tr>
											</thead>
											<tbody>
											
												<c:if test="${empty paging.result}">
													<tr>
														<td colspan="9" class="text-center">등록된 데이터가 없습니다.</td>
													</tr>
												</c:if>
												<c:forEach items="${paging.result}" var="item" varStatus="vs">
													<tr>
														<td class="text-center">
															<div class="custom-control custom-checkbox">
																<input type="checkbox" name="bnIds" value="${item.bnId}" class="custom-control-input" id="bnIds${item.bnId }" />
																<label class="custom-control-label" for="bnIds${item.bnId }"></label>
															</div>
														</td>
														<td>
															<c:choose>
																<c:when test="${item.bnUse}">
																	<span class="badge badge-info">게시</span>
																	<c:if test="${bannerSearch.bnType == 'popupzone' or bannerSearch.bnType == 'layer'}">
																		<c:choose>
																			<c:when test="${not empty item.bnStartDate and item.bnStartDate > bnToday}"><span class="badge badge-success">대기중</span></c:when>
																			<c:when test="${not empty item.bnEndDate and item.bnEndDate < bnToday}"><span class="badge badge-warning">기간종료</span></c:when>
																			<c:otherwise>
																			</c:otherwise>
																		</c:choose>
																	</c:if>
																</c:when>
																<c:when test="${!item.bnUse}"><span class="badge badge-danger">사용안함</span></c:when>
															</c:choose>
														</td>
														<c:if test="${bannerSearch.bnType == 'static'}">
														<td>
															${item.bnId}
														</td>
														</c:if>
														<td>
															<input type="text" name="bnOrders" id="bnId_${item.bnId}" value="${item.bnOrder}" readonly class="form-control form-control-sm " />	
														</td>
														<td>
															<a href="${ADMIN_PATH}/banner/update.do?bnId=${item.bnId}">${item.bnName}</a>
														</td>
														<td>
															<c:if test="${not empty item.thumb && item.thumb.fileId > 0}">
																<a href="${ADMIN_PATH}/banner/update.do?bnId=${item.bnId}">
																	<img class="img-thumbnail" style="max-width: 80px; max-height: 80px;" src="${item.thumb.fileServletUrl}" alt="${item.bnName}" onerror="this.src='${CONTEXT_PATH }/assets/images/noImage300x300.jpg'" />
																</a>
															</c:if>
														</td>
														<td style="word-break: break-all">
															<a href="${item.bnLink}" target="_blank">${item.bnLink}</a>
														</td>
														<td>
															<c:choose>
																<c:when test="${item.bnNewWin}">새창</c:when>
																<c:when test="${!item.bnNewWin}">현재창</c:when>
															</c:choose>
														</td>
														
														<c:if test="${bannerSearch.bnType == 'popupzone' || bannerSearch.bnType == 'layer'}">
															<td>
																<c:if test="${empty item.bnStartDate and empty item.bnEndDate}">상시</c:if>
																<c:if test="${not empty item.bnStartDate}"><div>시작:${item.bnStartDate}</div></c:if>
																<c:if test="${not empty item.bnEndDate}"><div>종료:${item.bnEndDate}</div></c:if>
															</td>
														</c:if>
														<td>
															<fmt:formatDate value="${item.bnRegDate}" pattern="yyyy-MM-dd" />
														</td>
														
													</tr>
												</c:forEach>
											</tbody>
										</table>
									</div>
									<div class="form-inline">
										<div class="mr-auto">
											<a href="${ADMIN_PATH}/banner/insert.do?bnType=${bannerSearch.bnType}" class="btn btn-primary waves-effect waves-light">추가</a>
											<button id="saveOrderBtn" class="btn btn-info waves-effect waves-light" >순서저장</button>
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