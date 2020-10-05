<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="asapro" uri="http://www.asadal.com" %>

<c:import url="/WEB-INF/jsp/egovframework/com/asapro/admin/layout/header.jsp" charEncoding="UTF-8" />
<script>
jQuery(function($){
	
	//삭제 Ajax
    $('#deleteBtn').on('click', function () {
    	var apiSeqs = [];
		$('[name=apiSeqs]:checked').each(function(idx, el){
			apiSeqs.push(el.value);
		});

        swal({
            title: '삭제하시겠습니까?',
            html: '삭제한 항목은 복구할 수 없습니다.',
            showCancelButton: true,
            confirmButtonText: '삭제',
            showLoaderOnConfirm: true,
            cancelButtonText: '취소',
            confirmButtonClass: 'btn btn-success',
            cancelButtonClass: 'btn btn-danger m-l-10',
            preConfirm: function () {
                return new Promise(function (resolve, reject) {
                   // setTimeout(function () {
                        if (apiSeqs.length === 0) {
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
				url : '${ADMIN_PATH}/openapiinfo/delete.do'
				, type : 'post'
				, dataType: 'json'
				, data: {
					apiSeqs : apiSeqs
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
	
	
  //배치 Ajax
    $('.batchBtn').on('click', function () {
    	var apiSeq = $(this).data("seq");;

        swal({
            title: 'Batch 하시겠습니까?',
            html: 'Batch 실행 결과를 꼭 확인하시고 기존 데이터는 모두 삭제됩니다.',
            showCancelButton: true,
            confirmButtonText: 'Batch',
            showLoaderOnConfirm: true,
            cancelButtonText: '취소',
            confirmButtonClass: 'btn btn-success',
            cancelButtonClass: 'btn btn-danger m-l-10',
            preConfirm: function () {
                return new Promise(function (resolve, reject) {
                   // setTimeout(function () {
                        if (apiSeq.length === 0) {
                            reject('Batch할 항목을 선택해 주세요.');
                        } else {
                            resolve();
                        }
                   // }, 1000)
                })
            },
            allowOutsideClick: false
        }).then(function () {
        	$.ajax({
				url : '${ADMIN_PATH}/openapiinfo/batch.do'
				, type : 'post'
				, dataType: 'json'
				, data: {
					apiSeq : apiSeq
				}
			}).done(function(result){
				if(result.success){
					swal({
		                type: 'success',
		                title: 'Batch가 완료되었습니다.',
		                html: 'Batch결과 : ' + result.successCnt + '건 등록완료',
		                //showCancelButton: true,
	                    //confirmButtonClass: 'btn btn-success',
	                    //cancelButtonClass: 'btn btn-danger m-l-10'
		            }).then(function(){
						//location.reload();
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
			                title: 'Batch하지 못하였습니다.[error]'
			            });
					}
				}
			}).fail(function(result){
				swal({
	                type: 'error',
	                title: 'Batch하지 못하였습니다.[fail]'
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
						            <form:form modelAttribute="openApiInfoSearch" cssClass="" action="${ADMIN_PATH}/openapiinfo/list.do" method="get">
										<div class="form-row mr-auto mt-2 form-inline">
											<label class="mr-sm-2 mb-2">정렬조건</label>
											<form:select path="pageSize" cssClass="form-control mr-sm-2 mb-2" title="페이지크기" >
												<form:option value="10">10개씩</form:option>
												<form:option value="20">20개씩</form:option>
												<form:option value="40">40개씩</form:option>
											</form:select>
											<form:select path="sortOrder" cssClass="form-control mr-sm-2 mb-2" title="정렬기준">
												<form:option value="API_REGDATE" label="등록일" />
												<form:option value="API_TITLE" label="API 명" />
											</form:select>
											<form:select path="sortDirection" cssClass="form-control mr-sm-2 mb-2" title="정렬방향">
												<form:option value="DESC" label="내림차순" />
												<form:option value="ASC" label="오름차순" />
											</form:select>
										</div>
										<div class="form-row mt-2 form-inline" >
											<label for="sc" class="mr-sm-2 mb-2">검색조건</label>
											<form:select path="apiProvider" cssClass="form-control mr-sm-2 mb-2" title="API제공사이트">
												<option value="">API제공사이트</option>
												<form:options items="${providerCodeList}" itemLabel="codeName" itemValue="codeId"/>
											</form:select>
											
											<form:select path="sc" cssClass="form-control mr-sm-2 mb-2" title="검색조건">
												<option value="">전체</option>
												<form:option value="API_TITLE" label="API 명" />
												<form:option value="API_CONTENT" label="API 설명" />
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
								
									<div class="form-group form-inline">
										<div class="mr-auto mb-2">
											오픈 API 
										</div>
									</div>
									
									<!-- alert maeeage -->
									<c:import url="/WEB-INF/jsp/egovframework/com/asapro/admin/include/message.jsp" charEncoding="UTF-8" />
									<div class="table-responsive">
										<table class="table table-striped table-hover ">
											<colgroup>
												<col style="width: 30px;" />
												<col style="width: 70px;" /><!-- 번호 -->
												<col style="width: 14%;" /><!-- 서비스아이디 -->
												<col style="width: 12%;" /><!-- 서비스명 -->
												<col /><!-- 도메인 -->
												<col style="width: 8%;" /><!-- 리턴타입 -->
												<col style="width: 12%;" /><!-- 제공사이트 -->
												<col style="width: 8%;" /><!-- 등록일 -->
												<col style="width: 8%;" /><!-- 배치일 -->
												<col style="width: 6%;" /><!-- 배치유형 -->
												<col style="width: 5%;" /><!-- batch -->
											</colgroup>
											<thead>
												<tr>
													<th class="text-center">
														<div class="custom-control custom-checkbox">
															<input type="checkbox" class="custom-control-input" id="selectAll" onclick="jQuery('[name=apiSeqs]').each(function(idx,el){this.checked=jQuery('#selectAll')[0].checked;});"/>
															<label class="custom-control-label" for="selectAll"></label>
														</div>
													</th>
													<th>번호</th>
													<th>서비스아이디</th>
													<th>서비스명</th>
													<th>도메인</th>
													<th>리턴타입</th>
													<th>제공사이트</th>
													<th>등록일</th>
													<th>배치일</th>
													<th>배치유형</th>
													<th>Batch</th>
												</tr>
											</thead>
											<tbody>
												<c:if test="${empty paging.result}">
													<tr>
														<td colspan="11" class="text-center">데이터가 없습니다.</td>
													</tr>
												</c:if>
												<c:forEach items="${paging.result}" var="item" varStatus="vs">
													<tr>
														<td class="text-center">
															<div class="custom-control custom-checkbox">
																<input type="checkbox" name="apiSeqs" value="${item.apiSeq}" class="custom-control-input" id="apiSeqs${vs.count }" />
																<label class="custom-control-label" for="apiSeqs${vs.count }"></label>
															</div>
														</td>
														<td>${paging.rowTop - vs.index}</td>
														<td><a href="${ADMIN_PATH}/openapiinfo/update.do?apiSeq=${item.apiSeq}" class=""><strong>${item.apiId}</strong></a></td>
														<td><a href="${ADMIN_PATH}/openapiinfo/update.do?apiSeq=${item.apiSeq}" class=""><strong>${item.apiTitle}</strong></a></td>
														<td>${item.apiDomain}</td>
														<td>${asapro:codeName(item.apiReturnType, returyTypeCodeList) }</td>
														<td>
															${asapro:codeName(item.apiProvider, providerCodeList) }
														</td>
														<td>
															<fmt:formatDate value="${item.apiRegDate}" pattern="yyyy-MM-dd" />
														</td>
														<td>
															<fmt:formatDate value="${item.apiLastBatch}" pattern="yyyy-MM-dd" />
														</td>
														<td>
															<c:if test="${item.apiInputType eq 'auto' }">자동</c:if>
															<c:if test="${item.apiInputType eq 'manual' }">수동</c:if>
														</td>
														<td>
															<button class="batchBtn btn btn-outline-warning waves-effect waves-light" data-seq="${item.apiSeq}">Batch</button>
														</td>
													</tr>
												</c:forEach>
											</tbody>
										</table>
									</div>
									
									<div class="form-inline">
										<div class="mr-auto">
											<a href="${ADMIN_PATH}/openapiinfo/insert.do" class="btn btn-primary waves-effect waves-light">추가</a>
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