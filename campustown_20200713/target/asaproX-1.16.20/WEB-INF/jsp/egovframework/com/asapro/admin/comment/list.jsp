<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="asapro" uri="http://www.asadal.com" %>
<c:import url="/WEB-INF/jsp/egovframework/com/asapro/admin/layout/header.jsp" charEncoding="UTF-8" />

<script>
jQuery(function($){
		
	//상태변경 Ajax
    $('#cmtStatusBtn').on('click', function (e) {
    	e.preventDefault();
    	var cmtStatus = $('#cmtStatusChange').val();
		if( !cmtStatus ){
			swal({
                title: '상태를 선택해 주세요.'
            });
			return;
		}
		var cmtIds = [];
		$('[name=cmtIds]:checked').each(function(idx, el){
			cmtIds.push(el.value);
		});
		
        swal({
            title: '댓글 상태를 변경하시겠습니까?',
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
                        if (cmtIds.length === 0) {
                            reject('변경할 항목을 선택해 주세요.');
                        } else {
                            resolve();
                        }
                   // }, 1000)
                })
            },
            allowOutsideClick: false
        }).then(function () {
        	$.ajax({
				url : '${ADMIN_PATH}/comment/status.do'
				, type : 'post'
				, dataType: 'json'
				, data: {
					cmtIds : cmtIds
					, cmtStatus : cmtStatus
				}
			}).done(function(result){
				if(result.success){
					swal({
		                type: 'success',
		                title: '변경이 완료되었습니다.',
		                //실제 삭제건수는 컨트롤러에서 삭제건수 받아와 뿌려주는게 정상/임시로 해놓음
		                html: '변경결과 : ' + result.successCnt + '건',
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
			                title: '변경하지 못하였습니다.[error]'
			            });
					}
				}
			}).fail(function(result){
				swal({
	                type: 'error',
	                title: '변경하지 못하였습니다.[fail]'
	            });
			});
        });
    });
	
	//삭제 Ajax
    $('#clearDeletedBtn').on('click', function (e) {
    	e.preventDefault();

        swal({
            title: '영구삭제하시겠습니까?',
            html: '삭제상태인 댓글을 영구 삭제하며 삭제된 댓글은 복구할 수 없습니다.',
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
                        //if (ipIds.length === 0) {
                        //    reject('삭제할 항목을 선택해 주세요.');
                        //} else {
                            resolve();
                        //}
                   // }, 1000)
                })
            },
            allowOutsideClick: false
        }).then(function () {
        	$.ajax({
				url : '${ADMIN_PATH}/comment/delete.do'
				, type : 'post'
				, dataType: 'json'
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

						            <form:form modelAttribute="commentSearch" cssClass="form-inline" action="${ADMIN_PATH}/comment/board/list.do" method="get">
										<div class="form-row mr-auto mt-2">
											<label class="mr-sm-2 mb-2">정렬조건</label>
											<form:select path="sortOrder" cssClass="form-control mr-sm-2 mb-2" title="정렬기준">
												<form:option value="CMT_REGDATE" label="등록일시" />
											</form:select>
											<form:select path="sortDirection" cssClass="form-control mr-sm-2 mb-2" title="정렬방향">
												<form:option value="DESC" label="내림차순" />
												<form:option value="ASC" label="오름차순" />
											</form:select>
										</div>
										
										<div class="form-row mt-2" >
											<label for="sc" class="mr-sm-2 mb-2">검색조건</label>
											<form:select path="cmtStatus" cssClass="form-control mr-sm-2 mb-2" title="댓글상태">
												<option value="">댓글상태</option>
												<form:option value="APPROVED" label="게시" />
												<%-- <form:option value="WAITING" label="대기" /> --%>
												<form:option value="DELETED" label="삭제" />
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
								<div class="card-header">
									<h4 class="mt-0 header-title">댓글목록</h4>
								</div>
								
								<div class="card-body">
									
									<!-- info -->
									<!-- <div class="p-3 mb-2 bg-light text-info font-weight-bold">
										<div><i class="mdi mdi-information"></i> 댓글목록</div>
									</div> -->
									
									<%-- 
									<ul class="nav nav-tabs mb-1" role="tablist">
										<li class="nav-item">
											<a class="nav-link<c:if test="${allowIpSearch.ipType eq 'allow'}"> active show</c:if>" href="${ADMIN_PATH}/allowip/list.do?ipType=allow" role="tab" aria-selected="false">허용</a>
										</li>
										<li class="nav-item">
											<a class="nav-link<c:if test="${allowIpSearch.ipType eq 'block'}"> active show</c:if>" href="${ADMIN_PATH}/allowip/list.do?ipType=block" role="tab" aria-selected="true">차단</a>
										</li>
									</ul> --%>
						           
									<!-- alert maeeage -->
									<c:import url="/WEB-INF/jsp/egovframework/com/asapro/admin/include/message.jsp" charEncoding="UTF-8" />
						            <div class="table-responsive">
										<table class="table table-striped table-hover">
											<colgroup>
												<col style="width: 30px;" />
												<col style="width: 5%;" />
												<col style="width: 60px;" />
												<col style="width: 12%;" />
												<col  />
												<col style="width: 10%;" />
												<col style="width: 8%;" />
											</colgroup>
											<thead>
												<tr>
													<th class="text-center">
														<div class="custom-control custom-checkbox">
															<input type="checkbox" class="custom-control-input" id="selectAll" onclick="jQuery('[name=cmtIds]').each(function(idx,el){this.checked=jQuery('#selectAll')[0].checked;});"/>
															<label class="custom-control-label" for="selectAll"></label>
														</div>
													</th>
													<th>상태</th>
													<th>이미지</th>
													<th>이름/아이디</th>
													<th>댓글</th>
													<th>추천</th>
													<!-- <th>신고</th>
													<th>찬성</th>
													<th>반대</th> -->
													<th>IP</th>
													<th>등록일시</th>
												</tr>
											</thead>
											<tbody>
												<c:if test="${empty paging.result}">
													<tr>
														<td colspan="11" class="text-center">등록된 댓글이 없습니다.</td>
													</tr>
												</c:if>
												<c:forEach items="${paging.result}" var="item" varStatus="vs">
													<tr>
														<td class="text-center">
															<div class="custom-control custom-checkbox">
																<input type="checkbox" name="cmtIds" value="${item.cmtId}" class="custom-control-input" id="cmtIds${vs.count }" />
																<label class="custom-control-label" for="cmtIds${vs.count }"></label>
															</div>
														</td>
														<td>
															<c:if test="${item.cmtStatus eq 'APPROVED'}"><span class="badge badge-success">게시</span></c:if>
															<%-- <c:if test="${item.cmtStatus eq 'DELETED'}"><span class="badge badge-warning">대기</span></c:if> --%>
															<c:if test="${item.cmtStatus eq 'DELETED'}"><span class="badge badge-danger">삭제</span></c:if>
														</td>
														<td>
															<img class="rounded-circle" src="${item.cmtProfileImage }" alt="${item.cmtSnsUserName}" style="width: 50px; height: 50px;" onerror="this.src='${CONTEXT_PATH }/assets/images/users/profile.jpg'" />
														</td>
														<td>
															<%-- <c:set var="loginType" value=""/>
															<c:choose>
																<c:when test="${item.cmtLoginType eq '2'}"><c:set var="loginType" value="facebook"/></c:when>
																<c:when test="${item.cmtLoginType eq '4'}"><c:set var="loginType" value="naver"/></c:when>
																<c:when test="${item.cmtLoginType eq '8'}"><c:set var="loginType" value="cacao"/></c:when>
																<c:when test="${item.cmtLoginType eq '16'}"><c:set var="loginType" value="phone"/></c:when>
																<c:when test="${item.cmtLoginType eq '32'}"><c:set var="loginType" value="ipin"/></c:when>
															</c:choose> --%>
															<%-- <img src="${CONTEXT_PATH }/assets/images/img_reply_thumb_${item.cmtLoginType}.png" alt="${item.cmtLoginType}"> --%>
															${item.cmtSnsUserName} [${item.cmtSnsUserId}]
														</td>
														<td>
															<c:if test="${item.cmtModule == 'board'}">
																<span class="badge badge-info">본문글 : </span><a href="${item.cmtPageUrl}" target="_blank"> ${item.cmtTitle}</a>
															</c:if>
															<p>${asapro:nl2br(item.cmtContent, false)}</p>
														</td>
														<td>
															${item.cmtRecommend}
														</td>
														<td>
															${item.cmtRegIp}
														</td>
														<td>
															<fmt:formatDate value="${item.cmtRegDate}" pattern="yyyy-MM-dd" />
															<br /><fmt:formatDate value="${item.cmtRegDate}" pattern="HH:mm:ss" />
														</td>
														
													</tr>
												</c:forEach>
											</tbody>
										</table>
									</div>
									<div class="form-inline">
										<div class="mr-auto">
											<select id="cmtStatusChange" class="form-control mr-sm-2" title="상태변경">
												<option value="">상태선택</option>
												<option value="APPROVED">게시</option>
												<!-- <option value="WAITING">대기</option> -->
												<option value="DELETED">삭제</option>
											</select>
											<button id="cmtStatusBtn" class="btn btn-outline-warning waves-effect waves-light">상태변경</button>
											<c:if test="${commentSearch.cmtStatus eq 'DELETED' }">
												<button id="clearDeletedBtn" class="btn btn-outline-danger waves-effect waves-light">영구삭제</button>
											</c:if>
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