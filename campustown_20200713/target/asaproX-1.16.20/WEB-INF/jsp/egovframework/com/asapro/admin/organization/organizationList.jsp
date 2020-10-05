<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:import url="/WEB-INF/jsp/egovframework/com/asapro/admin/layout/header.jsp" charEncoding="UTF-8" />
<script>
jQuery(function($){
	
	//삭제 Ajax
    $('#deleteBtn').on('click', function () {
    	var orgIds = [];
		$('[name=orgIds]:checked').each(function(idx, el){
			orgIds.push(el.value);
		});

        swal({
            title: '삭제하시겠습니까?',
            html: '기관을 삭제할 경우 기관의 [모든 부서]와 부서에 소속된 [모든 직원]이 함께 삭제됩니다.\n\r삭제한 항목은 복구할 수 없습니다.',
            showCancelButton: true,
            confirmButtonText: '삭제',
            showLoaderOnConfirm: true,
            cancelButtonText: '취소',
            confirmButtonClass: 'btn btn-success',
            cancelButtonClass: 'btn btn-danger m-l-10',
            preConfirm: function () {
                return new Promise(function (resolve, reject) {
                   // setTimeout(function () {
                        if (orgIds.length === 0) {
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
				url : '${ADMIN_PATH}/organization/organization${API_PATH}/delete.do'
				, type : 'post'
				, dataType: 'json'
				, data: {
					orgIds : orgIds
				}
			}).done(function(result){
				if(result.success){
					swal({
		                type: 'success',
		                title: '삭제가 완료되었습니다.',
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
	
	
	//테이블 DnD 순서변경
	//기관 하위 부서목록에서만 
	$("table ").tableDnD({
        onDragClass: "table-warning"
    });
	
	
	//부서 순서저장
	//기관 하위 부서목록에서만 
	$('#saveOrderBtn').on('click', function(e){
		var dataArr = [];
		$('[name=orgOrders]').each(function(idx, el){
			dataArr.push($(this).data('orgid'));
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
				url : '${ADMIN_PATH}/organization/organization/${API_PATH}/reOrder.do'
				, type : 'post'
				, dataType : 'json'
				, data : {
					//codeOrders : dataArr.join()
					orgIds : dataArr
				} 
			}).done(function(result){
				if(result.success){
					swal({
		                type: 'success',
		                title: '순서변경완료',
		                html: '수정되었습니다.',
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
						            <form:form modelAttribute="organizationSearch" cssClass="form-inline" action="${ADMIN_PATH}/organization/organization/list.do" method="get">
										<div class="form-row mr-auto mt-2">
											<label class="mr-sm-2 mb-2">정렬조건</label>
											<form:select path="pageSize" cssClass="form-control mr-sm-2 mb-2" title="페이지크기" >
												<form:option value="10">10개씩</form:option>
												<form:option value="20">20개씩</form:option>
												<form:option value="40">40개씩</form:option>
											</form:select>
											<form:select path="sortOrder" cssClass="form-control mr-sm-2 mb-2" title="정렬기준">
												<form:option value="ORG_ID" label="기관아이디" />
												<form:option value="ORG_NAME" label="기관이름" />
												<form:option value="ORG_NAME_EN" label="기관이름영문" />
												<form:option value="ORG_REGDATE" label="등록일" />
											</form:select>
											<form:select path="sortDirection" cssClass="form-control mr-sm-2 mb-2" title="정렬방향">
												<form:option value="DESC" label="내림차순" />
												<form:option value="ASC" label="오름차순" />
											</form:select>
										</div>
										<div class="form-row mt-2" >
											<label for="sc" class="mr-sm-2 mb-2">검색조건</label>
											<form:select path="orgUse" cssClass="form-control mr-sm-2 mb-2" title="사용여부">
												<option value="">사용여부</option>
												<form:option value="1" label="사용중" />
												<form:option value="0" label="미사용" />
											</form:select>
											<form:select path="sc" cssClass="form-control mr-sm-2 mb-2" title="검색조건">
												<option value="">전체</option>
												<form:option value="ORG_ID" label="기관아이디" />
												<form:option value="ORG_NAME" label="기관이름" />
												<form:option value="ORG_NAME_EN" label="기관이름영문" />
												<form:option value="ORG_DESCRIPTION" label="기관소개" />
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
												<col style="width: 70px;" />
												<col style="width: 20%;" />
												<col />
												<col />
												<col style="width: 8%;" />
												<col style="width: 8%;" />
												<col style="width: 8%;" />
											</colgroup>
											<thead>
												<tr>
													<th class="text-center">
														<div class="custom-control custom-checkbox">
															<input type="checkbox" class="custom-control-input" id="selectAll" onclick="jQuery('[name=orgIds]').each(function(idx,el){this.checked=jQuery('#selectAll')[0].checked;});"/>
															<label class="custom-control-label" for="selectAll"></label>
														</div>
													</th>
													<th>번호</th>
													<th>기관아이디</th>
													<th>기관이름</th>
													<th>기관영문이름</th>
													<th>부서목록</th>
													<th>사용유무</th>
													<th>등록일시</th>
												</tr>
											</thead>
											<tbody>
												<c:if test="${empty paging.result}">
													<tr>
														<td colspan="8" class="text-center">데이터가 없습니다.</td>
													</tr>
												</c:if>
												<c:forEach items="${paging.result}" var="item" varStatus="vs">
													<tr>
														<td class="text-center">
															<div class="custom-control custom-checkbox">
																<input type="checkbox" name="orgIds" value="${item.orgId}" class="custom-control-input" id="orgIds${vs.count }" />
																<label class="custom-control-label" for="orgIds${vs.count }"></label>
															</div>
														</td>
														<%-- <td>${paging.rowTop - vs.index}</td> --%>
														<td>
															<input type="text" name="orgOrders" data-orgid="${item.orgId}" value="${item.orgOrder}" readonly class="form-control form-control-sm " />
														</td>
														<td>${item.orgId}</td>
														<td><a href="${ADMIN_PATH}/organization/organization/update.do?orgId=${item.orgId}" class=""><strong>${item.orgName}</strong></a></td>
														<td>${item.orgNameEn}</td>
														<td>
															<a href="${ADMIN_PATH}/organization/department/list.do?orgId=${item.orgId}" class="text-warning">목록</a>
														</td>
														<td>
															<c:if test="${item.orgUse }"><span class="badge badge-success">사용</span></c:if>														
															<c:if test="${!item.orgUse }"><span class="badge badge-warning">미사용</span></c:if>														
														</td>
														<td>
															<fmt:formatDate value="${item.orgRegdate}" pattern="yyyy-MM-dd" />
														</td>
													</tr>
												</c:forEach>
											</tbody>
										</table>
									</div>
									<div class="form-inline">
										<div class="mr-auto">
											<a href="${ADMIN_PATH}/organization/organization/insert.do" class="btn btn-primary waves-effect waves-light">기관추가</a>
											<button id="deleteBtn" class="btn btn-danger waves-effect waves-light">삭제</button>
											<button id="saveOrderBtn" class="btn btn-info waves-effect waves-light" >순서저장</button>
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