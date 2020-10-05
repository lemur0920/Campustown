<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<c:import url="/WEB-INF/jsp/egovframework/com/asapro/admin/layout/header.jsp" charEncoding="UTF-8" />

<script>
jQuery(function($){
		
	//삭제 Ajax
    $('#deleteBtn').on('click', function () {
    	var codeIds = [];
		$('[name=codeIds]:checked').each(function(idx, el){
			codeIds.push(el.value);
		});

        swal({
            title: '삭제한 항목은 복구할 수 없습니다.\n삭제하시겠습니까?',
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
                        if (codeIds.length === 0) {
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
				url : '${ADMIN_PATH}/code/item/delete.do'
				, type : 'post'
				, dataType: 'json'
				, data: {
					codeIds : codeIds
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
	
	//테이블 DnD 순서변경
	//카테고리가 선택된 하위 코드목록에서만 
    <c:if test="${not empty codeSearch.catId}">
	$("table ").tableDnD({
        onDragClass: "table-warning"
    });
	</c:if>
	
	
	//상세코드 순서저장
	//카테고리가 선택된 하위 코드목록에서만 
	$('#saveOrderBtn').on('click', function(e){
		var dataArr = [];
		$('[name=codeOrders]').each(function(idx, el){
			dataArr.push($(this).data('codeid'));
		});
		swal({
            title: '순서를 변경하시겠습니까?',
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
                        if (dataArr.length === 0) {
                            reject('변경할 데이터가 없습니다.');
                        } else {
                            resolve();
                        }
                   // }, 1000)
                })
            },
            allowOutsideClick: false
        }).then(function () {
			$.ajax({
				url : '${ADMIN_PATH}/code/item/reOrder.do'
				, type : 'post'
				, dataType : 'json'
				, data : {
					//codeOrders : dataArr.join()
					codeIds : dataArr
				} 
			}).done(function(result){
				if(result.success){
					swal({
		                type: 'success',
		                title: '순서변경완료',
		                html: '수정되었습니다.',
		            }).then(function(){
		            	location.reload();
						//location.href = 'list.do?catId=${codeSearch.catId}';
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
			                title: '수정하지 못하였습니다.[Error]'
			            });
					}
				}
			}).fail(function(result){
				swal({
	                type: 'error',
	                title: '수정하지 못하였습니다.[Failure]'
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

						            <form:form modelAttribute="codeSearch" cssClass="form-inline" action="${ADMIN_PATH}/code/item/list.do" method="get">
										<form:hidden path="catId"/>
										<div class="form-row mr-auto mt-2">
											<label class="mr-sm-2 mb-2">정렬조건</label>
											<form:select path="pageSize" cssClass="form-control mr-sm-2 mb-2" title="페이지크기" >
												<form:option value="10">10개씩</form:option>
												<form:option value="20">20개씩</form:option>
												<form:option value="40">40개씩</form:option>
											</form:select>
											<form:select path="sortOrder" cssClass="form-control mr-sm-2 mb-2" title="정렬기준">
												<c:if test="${empty codeSearch.catId }">
												<form:option value="CAT_ID" label="분류아이디" />
												<form:option value="CAT_NAME" label="분류이름" />
												</c:if>
												<form:option value="CODE_ORDER" label="순서" />
												<form:option value="CODE_ID" label="코드아이디" />
												<form:option value="CODE_NAME" label="코드이름" />
												<form:option value="CODE_REGDATE" label="등록일시" />
											</form:select>
											<form:select path="sortDirection" cssClass="form-control mr-sm-2 mb-2" title="정렬방향">
												<form:option value="ASC" label="오름차순" />
												<form:option value="DESC" label="내림차순" />
											</form:select>
										</div>
										<div class="form-row mt-2" >
											<label for="sc" class="mr-sm-2 mb-2">검색조건</label>
											<form:select path="codeUse" cssClass="form-control mr-sm-2 mb-2" title="사용여부">
												<option value="">사용여부</option>
												<form:option value="1" label="사용중" />
												<form:option value="0" label="미사용" />
											</form:select>
											<form:select path="sc" cssClass="form-control mr-sm-2 mb-2" title="검색조건">
												<option value="">전체</option>
												<c:if test="${empty codeSearch.catId }">
												<form:option value="catId" label="분류아이디" />
												<form:option value="catName" label="분류이름" />
												</c:if>
												<form:option value="codeId" label="코드아이디" />
												<form:option value="codeName" label="코드이름" />
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
									<c:if test="${not empty codeSearch.catId }">
									<div class="p-3 mb-2 bg-light text-info font-weight-bold"><i class="mdi mdi-information"></i> 순서변경은 드래그하여 이동 후 순서저장버튼</div>
									</c:if>
									
									<!-- alert maeeage -->
									<c:import url="/WEB-INF/jsp/egovframework/com/asapro/admin/include/message.jsp" charEncoding="UTF-8" />
						           
						            <div class="table-responsive">
										<table class="table table-striped table-hover">
											<colgroup>
												<col style="width: 30px;" />
												<col style="width: 12%;" />
												<col style="width: 12%;" />
												<c:if test="${not empty codeSearch.catId }">
												<col style="width: 70px;" />
												</c:if>
												<col style="width: 15%;" />
												<col />
												<col />
												<col style="width: 8%;" />
												<col style="width: 8%;" />
											</colgroup>
											<thead>
												<tr>
													<th class="text-center">
														<div class="custom-control custom-checkbox">
															<input type="checkbox" class="custom-control-input" id="selectAll" onclick="jQuery('[name=codeIds]').each(function(idx,el){this.checked=jQuery('#selectAll')[0].checked;});"/>
															<label class="custom-control-label" for="selectAll"></label>
														</div>
													</th>
													<th>분류아이디</th>
													<th>분류이름</th>
													<c:if test="${not empty codeSearch.catId }">
													<th style="min-width: 70px;">순서</th>
													</c:if>
													<th>코드아이디</th>
													<th>코드명</th>
													<th>코드영문명</th>
													<th>사용여부</th>
													<th>등록일시</th>
												</tr>
											</thead>
											<tbody>
												<c:if test="${empty paging.result}">
													<tr>
														<c:if test="${not empty codeSearch.catId }"><c:set var="col" value="9"/></c:if>
														<c:if test="${empty codeSearch.catId }"><c:set var="col" value="8"/></c:if>
														<td colspan="${col }" class="text-center">데이터가 없습니다.</td>
													</tr>
												</c:if>
												<c:forEach items="${paging.result}" var="item" varStatus="vs">
													<tr class="<c:if test="${!item.codeUse}">delete</c:if>">
														<td class="text-center">
															<div class="custom-control custom-checkbox">
																<input type="checkbox" name="codeIds" value="${item.codeCategory.catId}@${item.codeId}" class="custom-control-input" id="codeIds${vs.count }" />
																<label class="custom-control-label" for="codeIds${vs.count }"></label>
															</div>
														</td>
														<td>${item.codeCategory.catId}</td>
														<td>${item.codeCategory.catName}</td>
														<c:if test="${not empty codeSearch.catId }">
														<td>
															<input type="text" name="codeOrders" data-codeid="${item.codeCategory.catId}@${item.codeId}" value="${item.codeOrder}" readonly class="form-control form-control-sm " />
														</td>
														</c:if>
														
														<td><a href="${ADMIN_PATH}/code/item/update.do?codeId=${item.codeId}&amp;codeCategory.catId=${item.codeCategory.catId}"><strong>${item.codeId}</strong></a></td>
														<td><a href="${ADMIN_PATH}/code/item/update.do?codeId=${item.codeId}&amp;codeCategory.catId=${item.codeCategory.catId}">${item.codeName}</a></td>
														<td>${item.codeNameEn}</td>
														<td>
															<c:if test="${!item.codeUse}"><span class="badge badge-warning">미사용</span></c:if>
														</td>
														<td>
															<fmt:formatDate value="${item.codeRegDate}" pattern="yyyy-MM-dd" />
														</td>
														
													</tr>
												</c:forEach>
											</tbody>
										</table>
									</div>
									<div class="form-inline">
										<div class="mr-auto">
											<c:choose>
												<c:when test="${not empty codeSearch.catId}">
													<a href="${ADMIN_PATH}/code/item/insert.do?codeCategory.catId=${codeSearch.catId}" class="btn btn-primary waves-effect waves-light">상세코드추가</a>
												</c:when>
												<c:otherwise>
													<a href="${ADMIN_PATH}/code/item/insert.do" class="btn btn-primary waves-effect waves-light">상세코드추가</a>
												</c:otherwise>
											</c:choose>
											<c:if test="${not empty codeSearch.catId }">
												<button id="saveOrderBtn" class="btn btn-info waves-effect waves-light" >순서저장</button>
											</c:if>
											<button id="deleteBtn" class="btn btn-danger waves-effect waves-light">삭제</button>
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
					</div><!-- 목록 -->
					
					<!-- ============================= //메뉴별 컨텐츠 ============================ -->
			    </div><!-- container -->
			
			
			</div> <!-- Page content Wrapper -->
	
	    </div> <!-- content -->
	
	</div>
	<!-- End Right content here -->

<c:import url="/WEB-INF/jsp/egovframework/com/asapro/admin/layout/footer.jsp" charEncoding="UTF-8" />