<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<c:import url="/WEB-INF/jsp/egovframework/com/asapro/admin/layout/header.jsp" charEncoding="UTF-8" />

<script>
jQuery(function($){
		
	//삭제 Ajax
    $('#deleteBtn').on('click', function () {
    	var macIds = [];
		$('[name=macIds]:checked').each(function(idx, el){
			macIds.push(el.value);
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
                        if (macIds.length === 0) {
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
				url : '${ADMIN_PATH}/allowmac/delete.do'
				, type : 'post'
				, dataType: 'json'
				, data: {
					macIds : macIds
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
					
					<!-- 목록 -->
					<div class="row">
						<div class="col-sm-12">
							<div class="card m-b-30">
								<div class="card-body">
									<!-- info -->
									<div class="p-3 mb-2 bg-light text-info font-weight-bold">
										<div><i class="mdi mdi-information"></i> 등록된 허용 목록이 없으면 모든 클라이언트가 접속가능합니다.</div>
										<div><i class="mdi mdi-information"></i> 허용목록이 우선적용되고 차단목록이 적용됩니다.</div>
										<div><i class="mdi mdi-information"></i> 허용목록과 차단목록에 모두 적용된경우 차단이 후에 적용되므로 차단됩니다.</div>
									</div>
									
									<%--
									<ul class="nav nav-pills mb-2" role="tablist">
										<li class="nav-item waves-effect waves-light">
											<a class="nav-link active" data-toggle="tab" href="#home-1" role="tab">허용</a>
										</li>
										<li class="nav-item waves-effect waves-light">
											<a class="nav-link" data-toggle="tab" href="#profile-1" role="tab">차단</a>
										</li>
									</ul> --%>
									
									<ul class="nav nav-tabs mb-1" role="tablist">
										<li class="nav-item">
											<a class="nav-link<c:if test="${allowMacSearch.macType eq 'allow'}"> active show</c:if>" href="${ADMIN_PATH}/allowmac/list.do?macType=allow" role="tab" aria-selected="false">허용</a>
										</li>
										<li class="nav-item">
											<a class="nav-link<c:if test="${allowMacSearch.macType eq 'block'}"> active show</c:if>" href="${ADMIN_PATH}/allowmac/list.do?macType=block" role="tab" aria-selected="true">차단</a>
										</li>
									</ul>
						           
									<!-- alert maeeage -->
									<c:import url="/WEB-INF/jsp/egovframework/com/asapro/admin/include/message.jsp" charEncoding="UTF-8" />
						            <div class="table-responsive">
										<table class="table table-striped table-hover">
											<colgroup>
												<col style="width: 30px;" />
												<col style="width: 20%;" />
												<col  />
												<col style="width: 12%;" />
												<col style="width: 12%;" />
												<col style="width: 12%;" />
											</colgroup>
											<thead>
												<tr>
													<th class="text-center">
														<div class="custom-control custom-checkbox">
															<input type="checkbox" class="custom-control-input" id="selectAll" onclick="jQuery('[name=macIds]').each(function(idx,el){this.checked=jQuery('#selectAll')[0].checked;});"/>
															<label class="custom-control-label" for="selectAll"></label>
														</div>
													</th>
													<th>규칙명</th>
													<th>MAC 주소</th>
													<th>허용/차단</th>
													<th>사용여부</th>
													<th>등록일시</th>
												</tr>
											</thead>
											<tbody>
												<c:if test="${empty paging.result}">
													<tr>
														<td colspan="6" class="text-center">등록된 MAC 주소가 없습니다.</td>
													</tr>
												</c:if>
												<c:forEach items="${paging.result}" var="item" varStatus="vs">
													<tr class="<c:if test="${!item.macUse}">delete</c:if>">
														<td class="text-center">
															<div class="custom-control custom-checkbox">
																<input type="checkbox" name="macIds" value="${item.macId}" class="custom-control-input" id="macIds${vs.count }" />
																<label class="custom-control-label" for="macIds${vs.count }"></label>
															</div>
														</td>
														<td><a href="${ADMIN_PATH}/allowmac/update.do?macId=${item.macId}">${item.macRuleName}</a></td>
														<td>
															${item.macAddress}
														</td>
														<td>
															<c:if test="${item.macType eq 'allow'}"><span class="badge badge-success">허용</span></c:if>
															<c:if test="${item.macType eq 'block'}"><span class="badge badge-warning">차단</span></c:if>
														</td>
														<td>
															<c:if test="${item.macUse}"><span class="badge badge-info">사용</span></c:if>
															<c:if test="${!item.macUse}"><span class="badge badge-warning">미사용</span></c:if>
														</td>
														<td>
															<fmt:formatDate value="${item.macRegDate}" pattern="yyyy-MM-dd" />
														</td>
														
													</tr>
												</c:forEach>
											</tbody>
										</table>
									</div>
									<div class="form-inline">
										<div class="mr-auto">
											<a href="${ADMIN_PATH}/allowmac/insert.do?macType=${allowMacSearch.macType}" class="btn btn-primary waves-effect waves-light">추가</a>
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