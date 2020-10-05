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
		var siteIds = [];
		$('[name=siteIds]:checked').each(function(idx, el){
			siteIds.push(el.value);
		});

        swal({
            title: '삭제하시겠습니까?',
            html: '삭제한 항목은 복구할 수 없습니다.(생성된 테이블은 삭제되지 않음)',
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
                        if (siteIds.length === 0) {
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
				url : '${ADMIN_PATH}/site/delete.do'
				, type : 'post'
				, dataType: 'json'
				, data: {
					siteIds : siteIds
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

						            <form:form modelAttribute="siteSearch" cssClass="form-inline" action="${ADMIN_PATH}/site/list.do" method="get">
										<div class="form-row mr-auto mt-2">
											<label class="mr-sm-2 mb-2">정렬조건</label>
											<form:select path="sortOrder" cssClass="form-control mr-sm-2 mb-2" title="정렬기준">
												<form:option value="SITE_ID" label="사이트아이디" />
												<form:option value="SITE_NAME" label="사이트명" />
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
												<form:option value="SITE_ID" label="사이트아이디" />
												<form:option value="SITE_NAME" label="사이트명" />
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
									<div class="p-3 mb-2 bg-light text-info font-weight-bold"><i class="mdi mdi-information"></i> 메인사이트는 삭제할 수 없으며 메인사이트의 도메인이 관리자페이지 도메인과 공유되므로 반디시 등록해야 합니다.</div>
									
									<!-- alert maeeage -->
									<c:import url="/WEB-INF/jsp/egovframework/com/asapro/admin/include/message.jsp" charEncoding="UTF-8" />
						           
						            <div class="table-responsive">
										<table class="table table-striped table-hover">
											<colgroup>
												<col style="width: 30px;" /><!-- 체크 -->
												<col style="width: 160px;" /><!-- 로고 -->
												<col style="width: 160px;"/><!-- 사이트명 -->
												<col style="width: 120px;" /><!-- 사이트 아이디 -->
												<col style="width: 120px;" /><!-- 사이트 유형 -->
												<col /><!-- 사이트 도메인 -->
												<col style="width: 140px;" /><!-- 사이트 테마 -->
												<col style="width: 120px;" /><!-- 등록일시 -->
											</colgroup>
											<thead>
												<tr>
													<th class="text-center">
														<div class="custom-control custom-checkbox">
															<input type="checkbox" class="custom-control-input" id="selectAll" onclick="jQuery('[name=siteIds]').each(function(idx,el){this.checked=jQuery('#selectAll')[0].checked;});"/>
															<label class="custom-control-label" for="selectAll"></label>
														</div>
													</th>
													<th>로고</th>
													<th>사이트명</th>
													<th>사이트아이디</th>
													<th>유형</th>
													<th>도메인</th>
													<th>테마</th>
													<th>등록일시</th>
												</tr>
											</thead>
											<tbody>
											
												<c:if test="${empty paging.result}">
													<tr>
														<td colspan="8" class="text-center">등록된 사이트가 없습니다.</td>
													</tr>
												</c:if>
												<c:forEach items="${paging.result}" var="item" varStatus="vs">
													<tr>
														<td class="text-center">
															<c:if test="${item.siteMain}"><span class="badge badge-info">메인</span></c:if>
															<c:if test="${!item.siteMain}">
																<div class="custom-control custom-checkbox">
																	<input type="checkbox" name="siteIds" value="${item.siteId}" class="custom-control-input" id="siteIds${item.siteId }" />
																	<label class="custom-control-label" for="siteIds${item.siteId }"></label>
																</div>
															</c:if>
														</td>
														<td>
															<c:if test="${not empty item.siteLogo && item.siteLogo.fileId > 0}">
																<a href="${ADMIN_PATH}/site/update.do?siteId=${item.siteId}">
																	<img class="img-thumbnail" style="max-width: 120px; max-height: 80px;" src="${item.siteLogo.fileServletUrl}" alt="${item.siteName}" onerror="this.src='${CONTEXT_PATH }/assets/images/noImage300x300.jpg'" />
																</a>
															</c:if>
														</td>
														<td>
															<a href="${ADMIN_PATH}/site/update.do?siteId=${item.siteId}">${item.siteName}</a>
														</td>
														<td>
															${item.siteId}
														</td>
														<td>
															${item.siteType}
														</td>
														<td>
															<c:if test="${not empty item.siteDomain}">
																<c:forEach items="${item.seperatedSiteDomains }" var="domain" varStatus="vs">
																	<a href="//${domain}/site/${item.siteId }/home" target="_blank">${domain}</a>
																	<c:if test="${!vs.last }">, </c:if>
																</c:forEach>
															</c:if>
														</td>
														<td>
															${item.siteTheme}
														</td>
														<td>
															<fmt:formatDate value="${item.siteRegDate}" pattern="yyyy-MM-dd" />
														</td>
														
													</tr>
												</c:forEach>
											</tbody>
										</table>
									</div>
									<div class="form-inline">
										<div class="mr-auto">
											<a href="${ADMIN_PATH}/site/insert.do" class="btn btn-primary waves-effect waves-light">추가</a>
											<button id="deleteBtn" class="btn btn-danger waves-effect waves-light">삭제</button>
										</div>
										<div>
											<span>총 ${paging.rowTotal}개의 사이트가 있습니다.</span>
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