<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.util.Calendar"%>
<%@ page import="egovframework.com.asapro.member.admin.service.AdminMember"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="asapro" uri="http://www.asadal.com" %>
<c:import url="/WEB-INF/jsp/egovframework/com/asapro/admin/layout/header.jsp" charEncoding="UTF-8" />

<script>
jQuery(function($){
	
	//삭제 Ajax
	$('#deleteBtn').on('click', function(e){
		var noteIdsByType = [];
		$('[name=noteIdsByType]:checked').each(function(idx, el){
			noteIdsByType.push(el.value);
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
                        if (noteIdsByType.length === 0) {
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
				url : '${ADMIN_PATH}/note/${noteSearch.noteDiv}/delete.do'
				, type : 'post'
				, dataType: 'json'
				, data: {
					noteIdsByType : noteIdsByType
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
	
	
	//발송취소 Ajax
	$('#cancelBtn').on('click', function(e){
		var cancelId = $(this).data('cancelid');
		
		swal({
            title: '발송취소를 하시겠습니까?',
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
                        if (!cancelId ) {
                            reject('취소할 항목을 선택해 주세요.');
                        } else {
                            resolve();
                        }
                   // }, 1000)
                })
            },
            allowOutsideClick: false
        }).then(function () {
        	$.ajax({
				url : '${ADMIN_PATH}/note/cancel.do'
				, type : 'post'
				, dataType: 'json'
				, data: {
					noteReceptionId : cancelId
				}
			}).done(function(result){
				if(result.success){
					swal({
		                type: 'success',
		                title: '발송취소가 완료되었습니다.',
		                //실제 삭제건수는 컨트롤러에서 삭제건수 받아와 뿌려주는게 정상/임시로 해놓음
		                html: '취소결과 : ' + result.successCnt + '건',
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
			                title: '취소하지 못하였습니다.[error]'
			            });
					}
				}
			}).fail(function(result){
				swal({
	                type: 'error',
	                title: '취소하지 못하였습니다.[fail]'
	            });
			});
        });
	});
	
	
	
	//기간 달력
	$('.datepicker-ui').datepicker();
	
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
					<%-- <c:import url="/WEB-INF/jsp/egovframework/com/asapro/admin/include/location.jsp" charEncoding="UTF-8" /> --%>

					
					<!-- ============================= 메뉴별 컨텐츠 ============================ -->
					<!-- 검색폼 -->
					<div class="row">
					    <div class="col-sm-12">
					        <div class="card m-b-15">
					            <div class="card-body">
						            <form:form modelAttribute="noteSearch" cssClass="" action="${ADMIN_PATH}/note/${noteSearch.noteDiv}/list.do" method="get">
										<div class="form-row mr-auto mt-2 form-inline">
											<label class="mr-sm-2 mb-2">정렬조건</label>
											<form:select path="pageSize" cssClass="form-control mr-sm-2 mb-2" title="페이지크기" >
												<form:option value="10">10개씩</form:option>
												<form:option value="20">20개씩</form:option>
												<form:option value="40">40개씩</form:option>
											</form:select>
											<form:select path="sortOrder" cssClass="form-control mr-sm-2 mb-2" title="정렬기준">
												<form:option value="NOTE_SEND_DATE" label="발신일시" />
											</form:select>
											<form:select path="sortDirection" cssClass="form-control mr-sm-2 mb-2" title="정렬방향">
												<form:option value="DESC" label="내림차순" />
												<form:option value="ASC" label="오름차순" />
											</form:select>
										</div>
										<div class="form-row mt-2 form-inline" >
											<label class="mr-sm-3 mb-2">발 송 일</label>
											<form:input path="startDate" cssClass="form-control mr-sm-1 datepicker-ui" cssErrorClass="form-control mr-sm-1 datepicker-ui is-invalid" autocomplete="off"/>
											<span class="mx-2"> ~ </span>
											<form:input path="endDate" cssClass="form-control mr-sm-1 datepicker-ui" cssErrorClass="form-control mr-sm-1 datepicker-ui is-invalid" autocomplete="off"/>
										</div>
										<div class="form-row mt-2 form-inline" >
											<label for="sc" class="mr-sm-2 mb-2">검색조건</label>
											<%-- <form:select path="noteOpen" cssClass="form-control mr-sm-2 mb-2" title="상태">
												<option value="">상태</option>
												<form:option value="true" label="수신" />
												<form:option value="false" label="비수신" />
											</form:select>	 --%>
											<form:select path="sc" cssClass="form-control mr-sm-2 mb-2" title="검색조건">
												<option value="">전체</option>
												<c:if test="${noteSearch.noteDiv eq 'reception'}">
													<form:option value="NOTE_TRANSMITER_ID" label="발신자아이디" />
													<form:option value="ADMIN_NAME" label="발신자이름" />
												</c:if>
												<c:if test="${noteSearch.noteDiv eq 'transmit'}">
													<form:option value="NOTE_RECEIVER_ID" label="수신자아이디" />
													<form:option value="ADMIN_NAME" label="수신자이름" />
												</c:if>
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
									<a href="${ADMIN_PATH}/note/insert.do" id="writeBtn" class="btn btn-primary waves-effect waves-light">쪽지쓰기</a>
								</div>
							
								<div class="card-body">
								
									<!-- 탭메뉴 -->
									<ul class="nav nav-tabs mb-2" role="tablist">
										<li class="nav-item">
											<a class="nav-link text-info<c:if test="${noteSearch.noteDiv eq 'reception'}"> active show</c:if>" href="${ADMIN_PATH}/note/reception/list.do" role="tab" aria-selected="false">
												<img src="${CONTEXT_PATH }/assets/images/note_reception.png" alt="받은쪽지함" class="img-fluid m-r-5" style="height: 20px;" />받은쪽지함</a>
										</li>
										<li class="nav-item">
											<a class="nav-link text-info<c:if test="${noteSearch.noteDiv eq 'transmit'}"> active show</c:if>" href="${ADMIN_PATH}/note/transmit/list.do" role="tab" aria-selected="true">
												<img src="${CONTEXT_PATH }/assets/images/note_transmit.png" alt="보낸쪽지함" class="img-fluid m-r-5" style="height: 20px;" />보낸쪽지함</a>
										</li>
									</ul>
								
									<!-- alert maeeage -->
									<c:import url="/WEB-INF/jsp/egovframework/com/asapro/admin/include/message.jsp" charEncoding="UTF-8" />
									
									<!-- =======  받은편지함 ============ -->
									<!-- =======  받은편지함 ============ -->
									<!-- =======  받은편지함 ============ -->
									<c:if test="${noteSearch.noteDiv eq 'reception'}">
										<div class="table-responsive">
											<table class="table table-striped table-hover ">
												<colgroup>
													<col style="width: 30px;" />
													<col style="width: 5%;" />
													<col style="width: 12%;" />
													<col />
													<col style="width: 12%;" />
													<%-- <col style="width: 12%;" /> --%>
												</colgroup>
												<thead>
													<tr>
														<th class="text-center">
															<div class="custom-control custom-checkbox">
																<input type="checkbox" class="custom-control-input" id="selectAll" onclick="jQuery('[name=noteIdsByType]').each(function(idx,el){this.checked=jQuery('#selectAll')[0].checked;});"/>
																<label class="custom-control-label" for="selectAll"></label>
															</div>
														</th>
														<th></th>
														<th>보낸사람</th>
														<th>내용</th>
														<th>날짜</th>
														<!-- <th>차단</th> -->
													</tr>
												</thead>
												<tbody>
													<c:if test="${empty paging.result}">
														<tr>
															<td colspan="5" class="text-center">데이터가 없습니다.</td>
														</tr>
													</c:if>
													<c:forEach items="${paging.result}" var="item" varStatus="vs">
														<tr>
															<td class="text-center">
																<div class="custom-control custom-checkbox">
																	<input type="checkbox" name="noteIdsByType" value="${item.noteReceptionId}" class="custom-control-input" id="noteReceptionIds${vs.count }" />
																	<label class="custom-control-label" for="noteReceptionIds${vs.count }"></label>
																</div>
															</td>
															<td>
																<a href="${ADMIN_PATH}/note/reception/view.do?noteReceptionId=${item.noteReceptionId}&cp=${noteSearch.cp}${noteSearch.queryString}" class="text-dark">
																	<c:if test="${item.noteOpen }">
																		<img src="${CONTEXT_PATH }/assets/images/note_open.png" alt="오픈쪽지이미지" class="img-fluid m-r-5" style="height: 20px;" />
																	</c:if>
																	<c:if test="${!item.noteOpen }">
																		<img src="${CONTEXT_PATH }/assets/images/note_nonopen.png" alt="미오픈쪽지이미지" class="img-fluid m-r-5" style="height: 20px;" />
																	</c:if>
																</a>
															</td>
															<td>
																<a href="${ADMIN_PATH}/note/insert.do?noteReceiverId=${item.noteTransmiterId}&reply=true" class="text-dark">
																	<c:if test="${!item.noteOpen }"><b>${item.transmiter.adminName}(${item.noteTransmiterId})</b></c:if>
																	<c:if test="${item.noteOpen }">${item.transmiter.adminName}(${item.noteTransmiterId})</c:if>
																</a>
															</td>
															<td>
																<a href="${ADMIN_PATH}/note/reception/view.do?noteReceptionId=${item.noteReceptionId}&cp=${noteSearch.cp}${noteSearch.queryString}" class="text-dark d-inline-block text-truncate" style="max-width: 500px;">
																<c:if test="${!item.noteOpen }"><b>${item.noteContent}</b></c:if>
																<c:if test="${item.noteOpen }">${item.noteContent}</c:if>
																</a>
															</td>
															<td>
																<fmt:formatDate value="${item.noteSendDate}" pattern="yyyy-MM-dd [HH:mm]" />
															</td>
															<!-- <td>
																차단
															</td> -->
														</tr>
													</c:forEach>
												</tbody>
											</table>
										</div>
									</c:if>
									
									<!-- =======  보낸편지함 ============ -->
									<!-- =======  보낸편지함 ============ -->
									<!-- =======  보낸편지함 ============ -->
									<c:if test="${noteSearch.noteDiv eq 'transmit'}">
										<div class="table-responsive">
											<table class="table table-striped table-hover ">
												<colgroup>
													<col style="width: 30px;" />
													<col style="width: 5%;" />
													<col style="width: 12%;" />
													<col />
													<col style="width: 12%;" />
													<col style="width: 12%;" />
													<col style="width: 8%;" />
												</colgroup>
												<thead>
													<tr>
														<th class="text-center">
															<div class="custom-control custom-checkbox">
																<input type="checkbox" class="custom-control-input" id="selectAll" onclick="jQuery('[name=noteIdsByType]').each(function(idx,el){this.checked=jQuery('#selectAll')[0].checked;});"/>
																<label class="custom-control-label" for="selectAll"></label>
															</div>
														</th>
														<th></th>
														<th>받는사람</th>
														<th>내용</th>
														<th>보낸날짜</th>
														<th>받은날짜</th>
														<th>발송취소</th>
													</tr>
												</thead>
												<tbody>
													<c:if test="${empty paging.result}">
														<tr>
															<td colspan="7" class="text-center">데이터가 없습니다.</td>
														</tr>
													</c:if>
													<c:forEach items="${paging.result}" var="item" varStatus="vs">
														<tr>
															<td class="text-center">
																<div class="custom-control custom-checkbox">
																	<input type="checkbox" name="noteIdsByType" value="${item.noteTransmitId}" class="custom-control-input" id="noteTransmitIds${vs.count }" />
																	<label class="custom-control-label" for="noteTransmitIds${vs.count }"></label>
																</div>
															</td>
															<td>
																<a href="${ADMIN_PATH}/note/transmit/view.do?noteTransmitId=${item.noteTransmitId}&cp=${noteSearch.cp}${noteSearch.queryString}" class="text-dark">
																	<img src="${CONTEXT_PATH }/assets/images/note_open.png" alt="쪽지이미지" class="img-fluid m-r-5" style="height: 20px;" />
																</a>
															</td>
															<td>
																<a href="${ADMIN_PATH}/note/insert.do?noteReceiverId=${item.noteReceiverId}&reply=true" class="text-dark">
																	${item.receiver.adminName}(${item.noteReceiverId})
																</a>
															</td>
															<td>
																<a href="${ADMIN_PATH}/note/transmit/view.do?noteTransmitId=${item.noteTransmitId}&cp=${noteSearch.cp}${noteSearch.queryString}" class="text-dark d-inline-block text-truncate" style="max-width: 500px;">${item.noteContent}</a>
															</td>
															<td>
																<fmt:formatDate value="${item.noteSendDate}" pattern="yyyy-MM-dd [HH:mm]" />
															</td>
															<td>
																<c:if test="${item.noteCancelYn eq 'N'}">
																	<c:if test="${item.noteOpen}"><fmt:formatDate value="${item.noteReceiveDate}" pattern="yyyy-MM-dd [HH:mm]" /></c:if>
																	<c:if test="${!item.noteOpen}"><span class="badge badge-secondary">읽지않음</span></c:if>
																</c:if>
																<c:if test="${item.noteCancelYn eq 'Y'}"><span class="badge badge-warning">발송취소</span></c:if>
															</td>
															<td>
																<c:if test="${!item.noteOpen and item.noteCancelYn eq 'N'}"><button id="cancelBtn" class="btn btn-warning waves-effect waves-light btn-sm" data-cancelid="${item.noteReceptionId}"> 발송취소</button></c:if>
																
															</td>
														</tr>
													</c:forEach>
												</tbody>
											</table>
										</div>
									</c:if>
									<div class="form-inline">
										<div class="mr-auto">
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