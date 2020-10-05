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
    	var reservIds = [];
		$('[name=reservIds]:checked').each(function(idx, el){
			reservIds.push(el.value);
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
                        if (reservIds.length === 0) {
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
				url : '${ADMIN_PATH}/rental/reservation/delete.do'
				, type : 'post'
				, dataType: 'json'
				, data: {
					reservIds : reservIds
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
	
	
	//대관/대여 기간 달력
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
					<c:import url="/WEB-INF/jsp/egovframework/com/asapro/admin/include/location.jsp" charEncoding="UTF-8" />

					
					<!-- ============================= 메뉴별 컨텐츠 ============================ -->
					<!-- 검색폼 -->
					<div class="row">
					    <div class="col-sm-12">
					        <div class="card m-b-15">
					            <div class="card-body">
						            <form:form modelAttribute="rentalReservationSearch" cssClass="" action="${ADMIN_PATH}/rental/reservation/list.do" method="get">
										<div class="form-row mr-auto mt-2 form-inline">
											<label class="mr-sm-2 mb-2">정렬조건</label>
											<form:select path="pageSize" cssClass="form-control mr-sm-2 mb-2" title="페이지크기" >
												<form:option value="10">10개씩</form:option>
												<form:option value="20">20개씩</form:option>
												<form:option value="40">40개씩</form:option>
											</form:select>
											<form:select path="sortOrder" cssClass="form-control mr-sm-2 mb-2" title="정렬기준">
												<form:option value="RESERV_REGDATE" label="등록일" />
												<form:option value="RESERV_NAME" label="신청자명" />
											</form:select>
											<form:select path="sortDirection" cssClass="form-control mr-sm-2 mb-2" title="정렬방향">
												<form:option value="DESC" label="내림차순" />
												<form:option value="ASC" label="오름차순" />
											</form:select>
										</div>
										<div class="form-row mt-2 form-inline" >
											<label for="" class="mr-sm-3 mb-2">사 용 일</label>
											<form:input path="reservStartDate" cssClass="form-control col-sm-1 datepicker-ui" cssErrorClass="form-control col-sm-1 datepicker-ui is-invalid" autocomplete="off"/>
											<span class="mx-2"> ~ </span>
											<form:input path="reservEndDate" cssClass="form-control col-sm-1 datepicker-ui" cssErrorClass="form-control col-sm-1 datepicker-ui is-invalid" autocomplete="off"/>
										</div>
										<div class="form-row mt-2 form-inline" >
											<label for="sc" class="mr-sm-2 mb-2">검색조건</label>
											<c:if test="${not empty rentalList}">
											<form:select path="reservRentId" cssClass="form-control mr-sm-2 mb-2" title="대관/대여명">
												<option value="">대관/대여 시설</option>
												<form:options items="${rentalList}" itemLabel="rentTitle" itemValue="rentId"/>
											</form:select>
											</c:if>
											<%-- <form:select path="reservOrgDivCode" cssClass="form-control mr-sm-2 mb-2" title="신청기관구분">
												<option value="">신청기관구분</option>
												<form:options items="${orgDivCodeList}" itemLabel="codeName" itemValue="codeId"/>
											</form:select> --%>
											<form:select path="reservPaidType" cssClass="form-control mr-sm-2 mb-2" title="결제수단">
												<option value="">결제수단</option>
												<form:options items="${rentPaymentTypeCodeList}" itemLabel="codeName" itemValue="codeId"/>
											</form:select>
											<form:select path="reservStatus" cssClass="form-control mr-sm-2 mb-2" title="신청상태">
												<option value="">신청상태</option>
												<form:options items="${statusCodeList}" itemLabel="codeName" itemValue="codeId"/>
											</form:select>
											
											<form:select path="sc" cssClass="form-control mr-sm-2 mb-2" title="검색조건">
												<option value="">전체</option>
												<form:option value="RESERV_NAME" label="신청자명" />
												<form:option value="RESERV_ORGANIZATION" label="신청기관명" />
												<form:option value="RESERV_TITLE" label="행사명" />
												<form:option value="RESERV_CONTENT" label="행사 내용" />
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
											전체 <span class="text-danger">${paging.rowTotal}</span>개 <span class="font-weight-bold">|</span>
											<span>승인대기 ${approvalWaitingCnt}명</span> <span class="font-weight-bold">|</span>
											<span>결제대기 ${paymentWaitingCnt}명</span> <span class="font-weight-bold">|</span>
											<span>예약완료 ${reservCompleteCnt}명</span> <span class="font-weight-bold">|</span>
											<span>예약취소 ${reservCancelCnt}명</span>
										</div>
										<div class="">
											<a href="#" class="h3 text-info"><i class="fa fa-list"></i></a>
											<a href="#" class="h3 text-info mx-2"><i class="fa fa-calendar"></i></a>
										</div>
									</div>
									
									<!-- alert maeeage -->
									<c:import url="/WEB-INF/jsp/egovframework/com/asapro/admin/include/message.jsp" charEncoding="UTF-8" />
									<div class="table-responsive">
										<table class="table table-striped table-hover ">
											<colgroup>
												<col style="width: 30px;" /><!-- 체크 -->
												<col style="width: 70px;" /><!-- 번호 -->
												<col /><!-- 대관/대여 실설명 -->
												<col style="width: 8%;" /><!-- 신청자명 -->
												<col style="width: 12%;" /><!-- 신청기관명 -->
												<col style="width: 10%;" /><!-- 결제수단 -->
												<col style="width: 8%;" /><!-- 사용일 -->
												<col style="width: 8%;" /><!-- 사용인원 -->
												<col style="width: 8%;" /><!-- 등록일 -->
												<col style="width: 8%;" /><!-- 신청상태 -->
											</colgroup>
											<thead>
												<tr>
													<th class="text-center">
														<div class="custom-control custom-checkbox">
															<input type="checkbox" class="custom-control-input" id="selectAll" onclick="jQuery('[name=reservIds]').each(function(idx,el){this.checked=jQuery('#selectAll')[0].checked;});"/>
															<label class="custom-control-label" for="selectAll"></label>
														</div>
													</th>
													<th>번호</th>
													<th>대관/대여 실설명</th>
													<th>신청자명</th>
													<th>신청기관명</th>
													<th>결제수단</th>
													<th>사용일</th>
													<th>사용인원</th>
													<th>등록일</th>
													<th>신청상태</th>
												</tr>
											</thead>
											<tbody>
												<c:if test="${empty paging.result}">
													<tr>
														<td colspan="10" class="text-center">데이터가 없습니다.</td>
													</tr>
												</c:if>
												<c:forEach items="${paging.result}" var="item" varStatus="vs">
													<tr>
														<td class="text-center">
															<div class="custom-control custom-checkbox">
																<input type="checkbox" name="reservIds" value="${item.reservId}" class="custom-control-input" id="reservIds${vs.count }" />
																<label class="custom-control-label" for="reservIds${vs.count }"></label>
															</div>
														</td>
														<td>${paging.rowTop - vs.index}</td>
														<td>
															<c:if test="${not empty rentalList}">
																<c:forEach items="${rentalList }" var="rent" varStatus="vs">
																	<c:if test="${rent.rentId eq item.rental.rentId }">${rent.rentTitle }</c:if>
																</c:forEach>
															</c:if>
														</td>
														<td><a href="${ADMIN_PATH}/rental/reservation/update.do?reservId=${item.reservId}" class=""><strong>${item.reservName}</strong></a></td>
														<td><a href="${ADMIN_PATH}/rental/reservation/update.do?reservId=${item.reservId}" class=""><strong>${item.reservOrganization}</strong></a></td>
														<td>
															${asapro:codeName(item.reservPaidType, rentPaymentTypeCodeList) }
														</td>
														<td>${item.reservDate}</td>
														<td>${item.reservTotal}</td>
														<td>
															<fmt:formatDate value="${item.reservRegDate}" pattern="yyyy-MM-dd" />
														</td>
														<td>
															${asapro:codeName(item.reservStatus, statusCodeList) }
														</td>
													</tr>
												</c:forEach>
											</tbody>
										</table>
									</div>
									<div class="form-inline">
										<div class="mr-auto">
											<button id="deleteBtn" class="btn btn-outline-danger waves-effect waves-light">삭제</button>
										</div>
									</div>
										<%--
										<div class="mb-2">
											<a href="${ADMIN_PATH}/rental/reservation/insert.do" class="btn btn-outline-primary waves-effect waves-light">접수등록</a>
											<button id="deleteBtn" class="btn btn-outline-danger waves-effect waves-light">삭제</button>
										</div>
										 --%>

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