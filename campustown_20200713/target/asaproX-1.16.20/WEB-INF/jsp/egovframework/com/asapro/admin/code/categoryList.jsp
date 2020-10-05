<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:import url="/WEB-INF/jsp/egovframework/com/asapro/admin/layout/header.jsp" charEncoding="UTF-8" />
<script>
jQuery(function($){
	
	//삭제 Ajax
    $('#deleteBtn').on('click', function () {
    	var catIds = [];
		$('[name=catIds]:checked').each(function(idx, el){
			catIds.push(el.value);
		});

        swal({
            title: '삭제하시겠습니까?',
            html: '코드분류를 삭제할 경우 상세코드도 함께 삭제됩니다.\n삭제한 항목은 복구할 수 없습니다.',
            showCancelButton: true,
            confirmButtonText: '삭제',
            showLoaderOnConfirm: true,
            cancelButtonText: '취소',
            confirmButtonClass: 'btn btn-success',
            cancelButtonClass: 'btn btn-danger m-l-10',
            preConfirm: function () {
                return new Promise(function (resolve, reject) {
                   // setTimeout(function () {
                        if (catIds.length === 0) {
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
				url : '${ADMIN_PATH}/code/category/delete.do'
				, type : 'post'
				, dataType: 'json'
				, data: {
					catIds : catIds
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
						//location.href = 'list.do';
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
						            <form:form modelAttribute="codeCategorySearch" cssClass="form-inline" action="${ADMIN_PATH}/code/category/list.do" method="get">
										<div class="form-row mr-auto mt-2">
											<label class="mr-sm-2 mb-2">정렬조건</label>
											<form:select path="pageSize" cssClass="form-control mr-sm-2 mb-2" title="페이지크기" >
												<form:option value="10">10개씩</form:option>
												<form:option value="20">20개씩</form:option>
												<form:option value="40">40개씩</form:option>
											</form:select>
											<form:select path="sortOrder" cssClass="form-control mr-sm-2 mb-2" title="정렬기준">
												<form:option value="CAT_ID" label="분류아이디" />
												<form:option value="CAT_NAME" label="분류이름" />
												<form:option value="CAT_REGDATE" label="등록일시" />
											</form:select>
											<form:select path="sortDirection" cssClass="form-control mr-sm-2 mb-2" title="정렬방향">
												<form:option value="ASC" label="오름차순" />
												<form:option value="DESC" label="내림차순" />
											</form:select>
										</div>
										<div class="form-row mt-2" >
											<label for="sc" class="mr-sm-2 mb-2">검색조건</label>
											<form:select path="sc" cssClass="form-control mr-sm-2 mb-2" title="검색조건">
												<option value="">전체</option>
												<form:option value="CAT_ID" label="분류아이디" />
												<form:option value="CAT_NAME" label="분류이름" />
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
								
									<!-- alert maeeage -->
									<c:import url="/WEB-INF/jsp/egovframework/com/asapro/admin/include/message.jsp" charEncoding="UTF-8" />
									<div class="table-responsive">
										<table class="table table-striped table-hover ">
											<colgroup>
												<col style="width: 30px;" />
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
															<input type="checkbox" class="custom-control-input" id="selectAll" onclick="jQuery('[name=catIds]').each(function(idx,el){this.checked=jQuery('#selectAll')[0].checked;});"/>
															<label class="custom-control-label" for="selectAll"></label>
														</div>
													</th>
													<th>분류아이디</th>
													<th>분류이름</th>
													<th>분류영문이름</th>
													<th>상세코드</th>
													<th>등록일시</th>
												</tr>
											</thead>
											<tbody>
												<c:if test="${empty paging.result}">
													<tr>
														<td colspan="6" class="text-center">데이터가 없습니다.</td>
													</tr>
												</c:if>
												<c:forEach items="${paging.result}" var="item" varStatus="vs">
													<tr>
														<td class="text-center">
															<div class="custom-control custom-checkbox">
																<input type="checkbox" name="catIds" value="${item.catId}" class="custom-control-input" id="catIds${vs.count }" />
																<label class="custom-control-label" for="catIds${vs.count }"></label>
															</div>
														</td>
														<td><a href="${ADMIN_PATH}/code/category/update.do?catId=${item.catId}" class=""><strong>${item.catId}</strong></a></td>
														<td><a href="${ADMIN_PATH}/code/category/update.do?catId=${item.catId}" class="">${item.catName}</a></td>
														<td>${item.catNameEn}</td>
														<td>
															<a href="${ADMIN_PATH}/code/item/list.do?catId=${item.catId}" class="text-warning">목록</a>
														</td>
														<td>
															<fmt:formatDate value="${item.catRegDate}" pattern="yyyy-MM-dd" />
														</td>
													</tr>
												</c:forEach>
											</tbody>
										</table>
									</div>
									<div class="form-inline">
										<div class="mr-auto">
											<a href="${ADMIN_PATH}/code/category/insert.do" class="btn btn-primary waves-effect waves-light">코드분류추가</a>
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