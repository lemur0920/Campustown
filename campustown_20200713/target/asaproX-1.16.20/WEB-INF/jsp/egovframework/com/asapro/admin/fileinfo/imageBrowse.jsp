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
		var fileIds = [];
		$('[name=fileIds]:checked').each(function(idx, el){
			fileIds.push(el.value);
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
                        if (fileIds.length === 0) {
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
				url : '${ADMIN_PATH}/fileinfo/delete.do'
				, type : 'post'
				, dataType: 'json'
				, data: {
					fileIds : fileIds
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


	<!-- Start right Content here -->
	<div class="content-page">
	    <!-- Start content -->
	    <div class="content">
			
	        <div class="page-content-wrapper ">

				<div class="container-fluid">
			
					<!-- ============================= 메뉴별 컨텐츠 ============================ -->
					<!-- 검색폼 -->
					<div class="row">
					    <div class="col-sm-12">
					        <div class="card m-b-15">
					            <div class="card-body">

						            <form:form modelAttribute="fileInfoSearch" cssClass="form-inline" action="${ADMIN_PATH}/fileinfo/imageBrowse.do" method="get">
										<form:hidden path="fileMediaType"/>
										<div class="form-row mr-auto mt-2">
											<label class="mr-sm-2 mb-2">정렬조건</label>
											<form:select path="sortOrder" cssClass="form-control mr-sm-2 mb-2" title="정렬기준">
												<form:option value="FILE_REGDATE" label="등록일시" />
												<form:option value="FILE_ID" label="아이디" />
											</form:select>
											<form:select path="sortDirection" cssClass="form-control mr-sm-2 mb-2" title="정렬방향">
												<form:option value="DESC" label="내림차순" />
												<form:option value="ASC" label="오름차순" />
											</form:select>
										</div>
										
										<div class="form-row mt-2" >
											<label for="sc" class="mr-sm-2 mb-2">검색조건</label>
<%-- 											<form:select path="bnUse" cssClass="form-control mr-sm-2 mb-2" title="게시여부">
												<option value="">게시여부</option>
												<form:option value="true" label="게시" />
												<form:option value="false" label="게시안함" />
											</form:select>
 --%>											<form:select path="sc" cssClass="form-control mr-sm-2 mb-2" title="검색조건">
												<option value="">전체</option>
												<form:option value="FILE_ORIGINAL_NAME" label="파일명" />
												<form:option value="FILE_ALT_TEXT" label="대체텍스트" />
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
										<table class="table table-striped table-hover">
											<colgroup>
												<col style="width: 30px;" /><!-- 체크 -->
												<col style="width: 100px; "/><!-- 이미지 -->
												<col /><!-- 파일명 -->
												<col style="width: 10%;" /><!-- 등록모듈 -->
												<col style="width: 10%;" /><!-- 등록일시 -->
												<col style="width: 8%;" /><!-- 다운로드 -->
											</colgroup>
											<thead>
												<tr>
													<th class="text-center">
														<div class="custom-control custom-checkbox">
															<input type="checkbox" class="custom-control-input" id="selectAll" onclick="jQuery('[name=bnIds]').each(function(idx,el){this.checked=jQuery('#selectAll')[0].checked;});"/>
															<label class="custom-control-label" for="selectAll"></label>
														</div>
													</th>
													<th>이미지</th>
													<th>파일명</th>
													<th>등록모듈</th>
													<th>등록일시</th>
													<th>다운로드</th>
												</tr>
											</thead>
											<tbody>
											
												<c:if test="${empty paging.result}">
													<tr>
														<td colspan="6" class="text-center">등록된 데이터가 없습니다.</td>
													</tr>
												</c:if>
												<c:forEach items="${paging.result}" var="item" varStatus="vs">
													<tr>
														<td class="text-center">
															<div class="custom-control custom-checkbox">
																<input type="checkbox" name="fileIds" value="${item.fileId}" class="custom-control-input" id="fileIds${item.fileId }" />
																<label class="custom-control-label" for="fileIds${item.fileId }"></label>
															</div>
														</td>
														<td>
															<c:choose>
																<c:when test="${item.fileMediaType == 'IMAGE'}">
																	<c:if test="${not empty item.fileServletThumbnailUrl }">
																		<img class="img-thumbnail" src="${item.fileServletThumbnailUrl}" alt="${item.fileOriginalName}" style="max-width: 100px; max-height: 100px;" onerror="this.src='${CONTEXT_PATH }/assets/images/noImage300x300.jpg'"/>
																	</c:if>
																	<c:if test="${empty item.fileServletThumbnailUrl }">
																		<img class="img-thumbnail" src="${item.fileServletUrl}" alt="${item.fileOriginalName}" style="max-width: 100px; max-height: 100px;" onerror="this.src='${CONTEXT_PATH }/assets/images/noImage300x300.jpg'"/>
																	</c:if>
																</c:when>
																<c:when test="${item.fileMediaType == 'DOCUMENT'}">
																	<asapro:fileIcon fileInfo="${item}" />
																</c:when>
																<c:when test="${item.fileMediaType == 'VIDEO'}">
																	<asapro:fileIcon fileInfo="${item}" />
																</c:when>
																<c:when test="${item.fileMediaType == 'AUDIO'}">
																	<asapro:fileIcon fileInfo="${item}" />
																</c:when>
																<c:when test="${item.fileMediaType == 'ETC'}">
																	<asapro:fileIcon fileInfo="${item}" />
																</c:when>
															</c:choose>
														</td>
														<td>
															<a href="${ADMIN_PATH}/fileinfo/update.do?fileId=${item.fileId}">${item.fileOriginalName}</a>
														</td>
														<td>
															${item.fileModule}
														</td>
														<td>
															<fmt:formatDate value="${item.fileRegDate}" pattern="yyyy-MM-dd" />
														</td>
														<td>
															<a href="${APP_PATH}/file/download/uu/${item.fileUUID }" class="btn btn-outline-primary waves-effect waves-light btn-sm">다운로드</a>
														</td>
														
													</tr>
												</c:forEach>
											</tbody>
										</table>
									</div>
									<div class="form-inline">
										<div class="mr-auto">
											<a href="${ADMIN_PATH}/file/upload.do?fileMediaType=${fileInfoSearch.fileMediaType}" class="btn btn-primary waves-effect waves-light">파일등록</a>
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