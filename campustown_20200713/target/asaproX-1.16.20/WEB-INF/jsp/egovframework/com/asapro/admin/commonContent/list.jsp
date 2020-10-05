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
		var comContIds = [];
		$('[name=comContIds]:checked').each(function(idx, el){
			comContIds.push(el.value);
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
                        if (comContIds.length === 0) {
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
				url : '${ADMIN_PATH}/common/content/delete.do'
				, type : 'post'
				, dataType: 'json'
				, data: {
					comContIds : comContIds
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

						            <form:form modelAttribute="commonContentSearch" cssClass="form-inline" action="${ADMIN_PATH}/common/content/list.do" method="get">
										<div class="form-row mr-auto mt-2">
											<label class="mr-sm-2 mb-2">정렬조건</label>
											<form:select path="sortOrder" cssClass="form-control mr-sm-2 mb-2" title="정렬기준">
												<form:option value="COM_CONT_REGDATE" label="등록일시" />
												<form:option value="COM_CONT_TITLE" label="콘텐츠 제목" />
											</form:select>
											<form:select path="sortDirection" cssClass="form-control mr-sm-2 mb-2" title="정렬방향">
												<form:option value="ASC" label="오름차순" />
												<form:option value="DESC" label="내림차순" />
											</form:select>
										</div>
										
										<div class="form-row mt-2" >
											<label for="sc" class="mr-sm-2 mb-2">검색조건</label>
											<c:if test="${not empty moduleCodeList }">
												<form:select path="comContModule" cssClass="form-control mr-sm-2 mb-2" title="프로그램">
													<option value="">프로그램</option>
													<form:options items="${moduleCodeList}" itemLabel="codeName" itemValue="codeId"/>
												</form:select>
											</c:if>
											<c:if test="${not empty cate1CodeList }">
												<form:select path="comContCate1" cssClass="form-control mr-sm-2 mb-2" title="카테고리1">
													<option value="">카테고리1 선택</option>
													<form:options items="${cate1CodeList}" itemLabel="codeName" itemValue="codeId"/>
												</form:select>
											</c:if>
											<c:if test="${not empty cate2CodeList }">
												<form:select path="comContCate2" cssClass="form-control mr-sm-2 mb-2" title="카테고리2">
													<option value="">카테고리2 선택</option>
													<form:options items="${cate2CodeList}" itemLabel="codeName" itemValue="codeId"/>
												</form:select>
											</c:if>
											<c:if test="${not empty cate3CodeList }">
												<form:select path="comContCate3" cssClass="form-control mr-sm-2 mb-2" title="카테고리3">
													<option value="">카테고리3 선택</option>
													<form:options items="${cate3CodeList}" itemLabel="codeName" itemValue="codeId"/>
												</form:select>
											</c:if>
											<form:select path="comContStatus" cssClass="form-control mr-sm-2 mb-2" title="게시상테">
												<option value="">게시상태</option>
												<form:option value="public" label="게시" />
												<form:option value="draft" label="게시안함" />
											</form:select>
											<form:select path="sc" cssClass="form-control mr-sm-2 mb-2" title="검색조건">
												<option value="">전체</option>
												<form:option value="COM_CONT_TITLE" label="제목" />
												<form:option value="COM_CONT_SUB_TITLE" label="간단설명" />
												<form:option value="COM_CONT_CONTENT" label="콘텐트" />
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
									<div class="p-3 mb-2 bg-light text-info font-weight-bold">
										<div><i class="mdi mdi-information"></i> 카테고리는 코드관리(moduleCode)에서 등록하세요.</div>
										<div><i class="mdi mdi-information"></i> 공통콘텐츠 카테고리1,	공통콘텐츠 카테고리2,	공통콘텐츠 카테고리3</div>
									</div>	
																		
									<!-- alert maeeage -->
									<c:import url="/WEB-INF/jsp/egovframework/com/asapro/admin/include/message.jsp" charEncoding="UTF-8" />
						           
						            <div class="table-responsive">
										<table class="table table-striped table-hover">
											<colgroup>
												<col style="width: 30px;" /><!-- 체크 -->
												<col style="width: 80px; "/><!-- 번호 -->
												<c:if test="${not empty moduleCodeList }">
												<col style="width: 180px; "/><!-- 적용프로그램 -->
												</c:if>
												<c:if test="${not empty cate1CodeList }">
												<col /><!-- 카테고리1 -->
												</c:if>
												<c:if test="${not empty cate2CodeList }">
												<col /><!-- 카테고리2 -->
												</c:if>
												<c:if test="${not empty cate3CodeList }">
												<col /><!-- 카테고리3 -->
												</c:if>
												<col /><!-- 제목 -->
												<col style="width: 120px; "/><!-- 게시상태 -->
												<col style="width: 120px;" /><!-- 등록일시 -->
											</colgroup>
											<thead>
												<tr>
													<th class="text-center">
														<div class="custom-control custom-checkbox">
															<input type="checkbox" class="custom-control-input" id="selectAll" onclick="jQuery('[name=comContIds]').each(function(idx,el){this.checked=jQuery('#selectAll')[0].checked;});"/>
															<label class="custom-control-label" for="selectAll"></label>
														</div>
													</th>
													<th>순서</th>
													<c:if test="${not empty moduleCodeList }">
														<th>적용프로그램</th>
													</c:if>
													<c:if test="${not empty cate1CodeList }">
														<th>카테고리1</th>
													</c:if>
													<c:if test="${not empty cate2CodeList }">
														<th>카테고리2</th>
													</c:if>
													<c:if test="${not empty cate3CodeList }">
														<th>카테고리3</th>
													</c:if>
													<th>제목</th>
													<th>게시상태</th>
													<th>등록일시</th>
												</tr>
											</thead>
											<tbody>
											
												<c:if test="${empty paging.result}">
													<tr>
														<td colspan="8" class="text-center">등록된 데이터가 없습니다.</td>
													</tr>
												</c:if>
												<c:forEach items="${paging.result}" var="item" varStatus="vs">
													<tr>
														<td class="text-center">
															<div class="custom-control custom-checkbox">
																<input type="checkbox" name="comContIds" value="${item.comContId}" class="custom-control-input" id="comContIds${item.comContId }" />
																<label class="custom-control-label" for="comContIds${item.comContId }"></label>
															</div>
														</td>
														<td>${paging.rowTop - vs.index}</td>
														<c:if test="${not empty moduleCodeList }">
															<td>${asapro:codeName(item.comContModule, moduleCodeList) }</td>
														</c:if>
														<c:if test="${not empty cate1CodeList }">
															<td>
															<c:forTokens items="${item.comContCate1 }" delims="," var="cate1" varStatus="vs">
																${asapro:codeName(cate1, cate1CodeList) }
																<c:if test="${!vs.last }">,</c:if>
															</c:forTokens>
															</td>
														</c:if>
														<c:if test="${not empty cate2CodeList }">
															<td>
															<c:forTokens items="${item.comContCate2 }" delims="," var="cate2" varStatus="vs">
																${asapro:codeName(cate2, cate2CodeList) }
																<c:if test="${!vs.last }">,</c:if>
															</c:forTokens>
															</td>
														</c:if>
														<c:if test="${not empty cate3CodeList }">
															<td>
															<c:forTokens items="${item.comContCate3 }" delims="," var="cate3" varStatus="vs">
																${asapro:codeName(cate3, cate1CodeList) }
																<c:if test="${!vs.last }">,</c:if>
															</c:forTokens>
															</td>
														</c:if>
														<td>
															<a href="${ADMIN_PATH}/common/content/update.do?comContId=${item.comContId}">${item.comContTitle}</a>
														</td>
														<td>
															<c:choose>
																<c:when test="${item.comContStatus eq 'public'}"><span class="badge badge-info">게시</span></c:when>
																<c:when test="${item.comContStatus eq 'draft'}"><span class="badge badge-danger">게시안함</span></c:when>
															</c:choose>
														</td>
														<td>
															<fmt:formatDate value="${item.comContRegDate}" pattern="yyyy-MM-dd" />
														</td>
														
													</tr>
												</c:forEach>
											</tbody>
										</table>
									</div>
									<div class="form-inline">
										<div class="mr-auto">
											<a href="${ADMIN_PATH}/common/content/insert.do" class="btn btn-primary waves-effect waves-light">추가</a>
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