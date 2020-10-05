<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="asapro" uri="http://www.asadal.com" %>

<c:import url="/WEB-INF/jsp/egovframework/com/asapro/admin/layout/header.jsp" charEncoding="UTF-8" />
<!-- Magnific popup -->
<link href="${CONTEXT_PATH }/assets/plugins/magnific-popup/magnific-popup.css" rel="stylesheet" type="text/css">
<!-- Magnific popup -->
<script src="${CONTEXT_PATH }/assets/plugins/magnific-popup/jquery.magnific-popup.min.js"></script>
<script src="${CONTEXT_PATH }/assets/pages/lightbox.js"></script>
<link rel="stylesheet" href="${CONTEXT_PATH }/assets/plugins/jquery-ui.multidatespicker/jquery-ui.multidatespicker.css">
<script src="${CONTEXT_PATH }/assets/plugins/jquery-ui.multidatespicker/jquery-ui.multidatespicker.js"></script>

<script>
jQuery(function($){
	
	//삭제 Ajax
    $('#deleteBtn').on('click', function () {
    	var rentIds = [];
		$('[name=rentIds]:checked').each(function(idx, el){
			rentIds.push(el.value);
		});

        swal({
            title: '삭제하시겠습니까?',
            html: '신청정보가 있을경우 삭제할 수 없습니다.\n\r삭제한 항목은 복구할 수 없습니다.',
            showCancelButton: true,
            confirmButtonText: '삭제',
            showLoaderOnConfirm: true,
            cancelButtonText: '취소',
            confirmButtonClass: 'btn btn-success',
            cancelButtonClass: 'btn btn-danger m-l-10',
            preConfirm: function () {
                return new Promise(function (resolve, reject) {
                   // setTimeout(function () {
                        if (rentIds.length === 0) {
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
				url : '${ADMIN_PATH}/rental/delete.do'
				, type : 'post'
				, dataType: 'json'
				, data: {
					rentIds : rentIds
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
	//카테고리1이 선택된 목록에서만 
    <c:if test="${not empty rentalSearch.rentCate1}">
	$("table").tableDnD({
        onDragClass: "table-warning"
    });
	</c:if>
	
	
	//상세코드 순서저장
	//카테고리가 선택된 하위 코드목록에서만 
	$('#saveOrderBtn').on('click', function(e){
		var rentIds = [];
		$('[name=rentOrders]').each(function(idx, el){
			rentIds.push($(this).data('rentid'));
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
                        if (rentIds.length === 0) {
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
				url : '${ADMIN_PATH}/rental/reOrder.do'
				, type : 'post'
				, dataType : 'json'
				, data : {
					rentIds : rentIds
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
	
	
	//=======================================================
	//=======================================================
	//시간닫기 레이어 
	//가능시간목록을 레이어에 출력한다.

	$('.searchBtn').on('click',function(){
		var rentId = $(this).data('rentid');
		$('#possibleTimeList').load('${ADMIN_PATH}/rental/reservation/closeTime/Layer.do?rental.rentId=' + rentId);									
	});
	
	// From an element with ID #popup
	$('.searchBtn').magnificPopup({
		items: {
			src: '#possibleTimeWrapper',
			type: 'inline',
			///mainClass: 'mfp-with-zoom', // class to remove default margin from left and right side
			// zoom: {
			//		enabled: true,
			//		duration: 300 // don't foget to change the duration also in CSS
			//	}
		}
		, closeOnBgClick:false
		//, showCloseBtn:true 
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
						            <form:form modelAttribute="rentalSearch" cssClass="form-inline" action="${ADMIN_PATH}/rental/list.do" method="get">
										<div class="form-row mr-auto mt-2">
											<label class="mr-sm-2 mb-2">정렬조건</label>
											<form:select path="pageSize" cssClass="form-control mr-sm-2 mb-2" title="페이지크기" >
												<form:option value="10">10개씩</form:option>
												<form:option value="20">20개씩</form:option>
												<form:option value="40">40개씩</form:option>
											</form:select>
											<form:select path="sortOrder" cssClass="form-control mr-sm-2 mb-2" title="정렬기준">
												<form:option value="RENT_ORDER" label="순서" />
												<form:option value="RENT_REGDATE" label="등록일" />
												<form:option value="RENT_CATE1 ASC, RENT_ORDER" label="구분1+순서" />
												<form:option value="RENT_TITLE" label="대관/대여 제목" />
											</form:select>
											<form:select path="sortDirection" cssClass="form-control mr-sm-2 mb-2" title="정렬방향">
												<form:option value="ASC" label="오름차순" />
												<form:option value="DESC" label="내림차순" />
											</form:select>
										</div>
										<div class="form-row mt-2" >
											<label for="sc" class="mr-sm-2 mb-2">검색조건</label>
											<c:if test="${not empty rentCate1List}">
											<form:select path="rentCate1" cssClass="form-control mr-sm-2 mb-2" title="시설구분1">
												<option value="">캠퍼스타운</option>
												<form:options items="${rentCate1List}" itemLabel="univNm" itemValue="univId"/>
											</form:select>
											</c:if>
											<c:if test="${not empty rentCate2List}">
											<form:select path="rentCate2" cssClass="form-control mr-sm-2 mb-2" title="공간유형">
												<option value="">지역</option>
												<form:options items="${rentCate2List}" itemLabel="codeName" itemValue="codeId"/>
											</form:select>
											</c:if>
											<form:select path="rentUse" cssClass="form-control mr-sm-2 mb-2" title="게시여부">
												<option value="">사용여부</option>
												<form:option value="true" label="게시중" />
												<form:option value="false" label="미게시" />
											</form:select>
											<form:select path="sc" cssClass="form-control mr-sm-2 mb-2" title="검색조건">
												<option value="">전체</option>
												<form:option value="RENT_TITLE" label="제목" />
												<form:option value="RENT_DESCRIPTION" label="설명" />
												<form:option value="RENT_ETC" label="기타정보" />
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
									<c:if test="${empty rentalSearch.rentCate1 }">
									<div class="p-3 mb-2 bg-light text-info font-weight-bold"><i class="mdi mdi-information"></i> 출력순서변경은 시설구분1로 필터링한 후 변경 가능하빈다.</div>
									</c:if>
									<c:if test="${not empty rentalSearch.rentCate1 }">
									<div class="p-3 mb-2 bg-light text-info font-weight-bold"><i class="mdi mdi-information"></i> 순서변경은 드래그하여 이동 후 순서저장버튼</div>
									</c:if>
								
									<!-- alert maeeage -->
									<c:import url="/WEB-INF/jsp/egovframework/com/asapro/admin/include/message.jsp" charEncoding="UTF-8" />
									<div class="table-responsive-lg">
										<table class="table table-striped table-hover ">
											<colgroup>
												<col style="width: 30px;" /><!-- 체크 -->
												<col style="width: 70px;" /><!-- 번호 -->
												<col style="width: 80px;" /><!-- 이미지 -->
												<col style="width: 8%;" /><!-- 시설구분1 -->
												<col style="width: 8%;" /><!-- 지역 -->
												<col /><!-- 대관/대여 제목 -->
												<col style="width: 8%;" /><!-- 대여기간 -->
												<col style="width: 8%;" /><!-- 예약방법 -->
												<col style="width: 8%;" /><!-- 결제방법 -->
												<col style="width: 8%;" /><!-- 대관대여료 -->
												<col style="width: 7%;" /><!-- 신청관리 -->
												<col style="width: 6%;" /><!-- 사용여부 -->
												<col style="width: 8%;" /><!-- 등록일시 -->
											</colgroup>
											<thead>
												<tr>
													<th class="text-center align-middle">
														<div class="custom-control custom-checkbox">
															<input type="checkbox" class="custom-control-input" id="selectAll" onclick="jQuery('[name=rentIds]').each(function(idx,el){this.checked=jQuery('#selectAll')[0].checked;});"/>
															<label class="custom-control-label" for="selectAll"></label>
														</div>
													</th>
													<th class="align-middle">번호</th>
													<th class="align-middle">이미지</th>
													<th class="align-middle">캠퍼스타운</th>
													<th class="align-middle">지역</th>
													<th class="align-middle">대관/대여<br/>제목</th>
													<th class="align-middle">대여기간</th>
													<th class="align-middle">예약방법</th>
													<th class="align-middle">결제방법</th>
													<th class="align-middle">대관/대여<br/>요금</th>
													<th class="align-middle">신청관리</th>
													<th class="align-middle">게시여부</th>
													<th class="align-middle">등록일시</th>
												</tr>
											</thead>
											<tbody>
												<c:if test="${empty paging.result}">
													<tr>
														<td colspan="13" class="text-center">데이터가 없습니다.</td>
													</tr>
												</c:if>
												<c:forEach items="${paging.result}" var="item" varStatus="vs">
													<tr>
														<td class="text-center">
															<div class="custom-control custom-checkbox">
																<input type="checkbox" name="rentIds" value="${item.rentId}" class="custom-control-input" id="rentIds${vs.count }" />
																<label class="custom-control-label" for="rentIds${vs.count }"></label>
															</div>
														</td>
														<td>
															<c:if test="${empty rentalSearch.rentCate1 }">
																${paging.rowTop - vs.index}
															</c:if>
															<c:if test="${not empty rentalSearch.rentCate1 }">
																<input type="text" name="rentOrders" data-rentid="${item.rentId}" value="${item.rentOrder}" readonly class="form-control form-control-sm " />
															</c:if>
														</td>
														<td>
															<c:if test="${not empty item.thumb && item.thumb.fileId > 0}">
																<a href="${ADMIN_PATH}/rental/update.do?rentId=${item.rentId}">
																	<img class="img-thumbnail" style="max-width: 80px; max-height: 80px;" src="${item.thumb.fileServletUrl}" alt="${item.thumb.fileAltText}" onerror="this.src='${CONTEXT_PATH }/assets/images/noImage300x300.jpg'" />
																</a>
															</c:if>
														</td>
														<td>
															<c:forEach items="${rentCate1List }" var="cate1" varStatus="vs">
																<c:if test="${item.rentCate1 eq cate1.univId }">
																	${cate1.univNm }
																</c:if>
															</c:forEach>
															<%-- ${asapro:codeName(item.rentCate1, rentCate1List)} --%>
														</td>
														<td>
															<c:forTokens items="${item.rentCate2 }" delims="," var="spaceType" varStatus="vs">
																${asapro:codeName(spaceType, rentCate2List)}<c:if test="${!vs.last }">,<br/> </c:if>
															</c:forTokens>
														</td>
														<td><a href="${ADMIN_PATH}/rental/update.do?rentId=${item.rentId}" class=""><strong>${item.rentTitle}</strong></a></td>
														<td>
															<c:if test="${item.rentPossiblelDayType eq 'always' }">상시</c:if>
															<c:if test="${item.rentPossiblelDayType eq 'period' }">
																<div>${item.rentPeriodSdate } <br/> ~ ${item.rentPeriodEdate }</div>
															</c:if>
														</td>
														<td>
															<c:forTokens items="${item.rentReservType }" delims="," var="reservType" varStatus="vs">
																${asapro:codeName(reservType, rentReservTypeCodeList)}<c:if test="${!vs.last }">,<br/> </c:if>
															</c:forTokens>
														</td>
														<td>
															<c:forTokens items="${item.rentPaymentType }" delims="," var="paymentType" varStatus="vs">
																${asapro:codeName(paymentType, rentPaymentTypeCodeList)}<c:if test="${!vs.last }">,<br/> </c:if>
															</c:forTokens>
														</td>
														<td>
															<c:if test="${item.rentRentingMethod eq 'time' }"><span class="badge badge-primary">시간단위</span></c:if>
															<c:if test="${item.rentRentingMethod eq 'package' }"><span class="badge badge-primary">패키지단위</span></c:if>
															<br>
															<fmt:formatNumber value="${item.rentCharge}" pattern="#,###" />  원
														</td>
														<td>
															<c:if test="${item.rentStep eq '2' }">
																<a href="${ADMIN_PATH}/rental/reservation/insert.do?rental.rentId=${item.rentId}" class="btn btn-sm btn-outline-primary waves-effect waves-light">신청</a>
																<br>
																<a href="${ADMIN_PATH}/rental/reservation/list.do?reservRentId=${item.rentId}" class="btn btn-sm btn-outline-success waves-effect waves-light mt-2">신청현황</a>
																<br>
																<button type="button" class="btn btn-sm btn-outline-danger waves-effect waves-light mt-2 searchBtn" data-rentid="${item.rentId }">시간닫기</button>
<%-- 																<a href="${ADMIN_PATH}/rental/reservation/timeCloseLayer.do?rental.rentId=${item.rentId}" class="btn btn-sm btn-outline-danger waves-effect waves-light mt-2">시간닫기</a> --%>
															</c:if>
															
															<div>
																<c:if test="${item.rentStep eq '1' }"><span class="badge badge-danger">등록진행중</span></c:if>
																<%--<c:if test="${item.rentStep eq '2' }"><span class="badge badge-info">등록완료</span></c:if> --%>
															</div>
														</td>
														<td>
															<div>
																<c:if test="${item.rentUse }"><span class="badge badge-success">게시</span></c:if>														
																<c:if test="${!item.rentUse }"><span class="badge badge-warning">게시안함</span></c:if>														
															</div>
														</td>
														<td>
															<fmt:formatDate value="${item.rentRegDate}" pattern="yyyy-MM-dd" />
														</td>
													</tr>
												</c:forEach>
											</tbody>
										</table>
									</div>
									
									<div id="possibleTimeWrapper" class="mfp-hide">
										<h5 class="modal-title mt-0">예약가능시간 목록</h5>
										<hr>
										<div id="possibleTimeList">
										
										</div>
									</div>
									
									<div class="form-inline">
										<div class="mr-auto">
											<a href="${ADMIN_PATH}/rental/insert.do" class="btn btn-primary waves-effect waves-light">추가</a>
											<c:if test="${not empty rentalSearch.rentCate1 }">
											<button id="saveOrderBtn" class="btn btn-info waves-effect waves-light" >순서저장</button>
											</c:if>
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