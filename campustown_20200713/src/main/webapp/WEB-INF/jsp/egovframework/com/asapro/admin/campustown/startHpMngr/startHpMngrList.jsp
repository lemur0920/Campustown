<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="asapro" uri="http://www.asadal.com" %>

<c:import url="/WEB-INF/jsp/egovframework/com/asapro/admin/layout/header.jsp" charEncoding="UTF-8" />

<script>
jQuery(function($){
	
	// 달력
	$('.datepicker-ui').datepicker();
	
	<c:if test="${teamYn eq 'Y'}">
	if($('#tot').val() == 0){
		alert("창업팀 소속 캠퍼스타운에 등록 요청을 문의하세요.");		
	}
	</c:if>
	
	// 창업팀인 경우만(teamId가 존재하면) 버튼 숨기기
	<c:if test="${teamYn eq 'Y'}">
		$('.insertDiv').hide();
		$('#deleteBtn').hide();
		$('.hidCol').hide();
	</c:if>
	
	// 초기화 버튼 클릭 시
	$("#resetBtn").on("click", function(){
		// selectbox
		$("#pageSize").prop("selectedIndex", 0);
		$("#sortOrder").prop("selectedIndex", 0);
		$("#sortDirection").prop("selectedIndex", 0);
		$("#areaId").prop("selectedIndex", 0);
		$("#univId").prop("selectedIndex", 0);
		
		$("#scDiv").prop("selectedIndex", 0);
		$("#scDiv").trigger("click");
		
		
		// input
		$("#scRegStartDate").val("");
		$("#scRegEndDate").val("");
		$("#sv").val("");
		
	});
	
	
	//삭제 Ajax
    $('#deleteBtn').on('click', function () {
    	var compIds = [];
		$('[name=compIds]:checked').each(function(idx, el){
			compIds.push(el.value);
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
                        if (compIds.length === 0) {
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
				url : '${ADMIN_PATH}/startHpMngr/delete.do'
				, type : 'post'
				, dataType: 'json'
				, data: {
					compIds : compIds
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
					<!-- alert message -->
					<c:import url="/WEB-INF/jsp/egovframework/com/asapro/admin/include/message.jsp" charEncoding="UTF-8" />
					
					<!-- 검색폼 -->
					<div class="row">
					    <div class="col-sm-12">
					        <div class="card m-b-15">
					            <div class="card-body">
					            
						            <form:form modelAttribute="startHpMngrSearch" cssClass="" action="${ADMIN_PATH}/startHpMngr/list.do" method="get">
						            	 <form:hidden path="univId" />
					            	 	 <form:hidden path="compId" />
					            	 	 
							        	<div class="form-row mr-auto mt-2 form-inline">
												<label class="mr-sm-2 mb-2">정렬조건</label>
												<form:select path="pageSize" cssClass="form-control mr-sm-2 mb-2" title="페이지크기" >
													<form:option value="10">10개씩</form:option>
													<form:option value="20">20개씩</form:option>
													<form:option value="40">40개씩</form:option>
												</form:select>
												
												<form:select path="sortOrder" cssClass="form-control mr-sm-2 mb-2" title="정렬기준">
													<form:option value="UPD_DT" label="최근 수정일" />
													<form:option value="UNIV_NM" label="캠퍼스타운" />
													<form:option value="COMP_NM" label="창업팀" />
												</form:select>
												
												<form:select path="sortDirection" cssClass="form-control mr-sm-2 mb-2" title="정렬방향">
													<form:option value="DESC" label="내림차순" />
													<form:option value="ASC" label="오름차순" />
												</form:select>
											</div>
						        	
						        		<div class="form-row mt-2 form-inline" >
											<label for="" class="mr-sm-3 mb-2">등록일</label>
	
											<form:input path="scRegStartDate" cssClass="form-control mr-sm-2 mb-2 datepicker-ui" cssErrorClass="form-control col-sm-1 datepicker-ui is-invalid" autocomplete="off" readonly="true"/>
											<span class="mx-2"> ~ </span>
											<form:input path="scRegEndDate" cssClass="form-control mr-sm-2 mb-2 datepicker-ui" cssErrorClass="form-control col-sm-1 datepicker-ui is-invalid" autocomplete="off" readonly="true"/>
											&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
											
											<label class="mr-sm-2 mb-2">검색조건</label>
											
											<!-- 1. 지역: 서울시 구 25개 뿌리기 -->
											<c:if test="${org eq 'university'}">
												<form:select path="areaId" readonly="true" onFocus="this.initialSelect = this.selectedIndex;"
															 onChange="this.selectedIndex = this.initialSelect;"
															 cssClass="form-control mr-sm-2 mb-2 scArea" title="지역">
													<option value="">지역</option>
													<form:options items="${sigunguCodeList}" itemLabel="codeName" itemValue="codeId"/>
												</form:select>
											</c:if>
											<c:if test="${org ne 'university'}">
												<form:select path="areaId" cssClass="form-control mr-sm-2 mb-2 scArea" title="지역">
													<option value="">지역</option>
													<form:options items="${sigunguCodeList}" itemLabel="codeName" itemValue="codeId"/>
												</form:select>
											</c:if>
											<form:select path="expsrYnId" cssClass="form-control mr-sm-2 mb-2 scArea" title="지역">
													<option value="">노출여부</option>
													<form:option value="Y">Y</form:option>
													<form:option value="N">N</form:option>
											</form:select>
											<form:select path="sc" id="scDiv" class="form-control mr-sm-2 mb-2" title="검색어 구분">
												<form:option value="">전체</form:option>
												<!-- <option value="1" label="지역" /> -->
												<form:option value="compNm" label="창업팀명" />
												<form:option value="univNm" label="캠퍼스타운명" />
											</form:select>
											
											<form:input path="sv" cssClass="form-control mr-sm-2 mb-2" title="검색어" placeholder="Search"/>
											
											<button id="searchBtn" class="btn btn-outline-success waves-effect mr-sm-2 mb-2" type="submit">검색</button>
											<c:if test="${org ne 'university'}">
												<button id="resetBtn" class="btn btn-outline-success waves-effect mr-sm-2 mb-2" type="button">초기화</button>
											</c:if>
										</div>		
						        		
						        		
						        	</form:form>
						        </div>
					        </div>
				        </div>
			        </div>	    
	        		<!-- End 검색폼 -->
	        		
	        		<span class="bg-light font-weight-bold text-info">- 1. 창업팀 정보 2. 임직원> 대표자 정보까지 등록해주세요.</span>
	        		
	        		<!-- 목록 -->
					<div class="row">
						<div class="col-sm-12">
							<div class="card m-b-30">
								<div class="card-body">
								
									<!-- alert message -->
									<c:import url="/WEB-INF/jsp/egovframework/com/asapro/admin/include/message.jsp" charEncoding="UTF-8" />
	        						
	        						<div class="table-responsive">
	        							<table class="table table-striped table-hover ">
											<colgroup>
											</colgroup>
											
											<thead>
												<tr>
													<!-- <th>번호</th> -->
													<th class="text-center hidCol">
														<div class="custom-control custom-checkbox">
															<input type="checkbox" class="custom-control-input" id="selectAll" onclick="jQuery('[name=compIds]').each(function(idx,el){this.checked=jQuery('#selectAll')[0].checked;});"/>
															<label class="custom-control-label" for="selectAll"></label>
														</div>
													</th>
													<th>지역</th>
													<th>캠퍼스타운 명</th>
													<th>창업팀 명</th>
													<th>노출여부</th>
													<!-- <th>등록상태</th> -->
													<th>창업팀 정보</th>
													<th>임직원</th>
													<th>새소식</th>
													<th>제품</th>
													<th>최근 수정일</th>
												</tr>
											</thead>
											
											<tbody>
												<c:if test="${empty paging.result}">
													<tr>
														<td colspan="18" class="text-center">데이터가 없습니다.</td>
													</tr>
												</c:if>
												<c:forEach items="${paging.result}" var="item" varStatus="vs">
													<tr>
														<%-- <td>${paging.rowTop - vs.index}</td> --%>
														<td class="text-center hidCol">
															<div class="custom-control custom-checkbox">
																<input type="checkbox" name="compIds" value="${item.compId}" class="custom-control-input" id="compIds${vs.count }" />
																<label class="custom-control-label" for="compIds${vs.count }"></label>
															</div>
														</td>
														<td>
															${asapro:codeName(item.areaGuCd, sigunguCodeList)}
														</td>
														<td>
															${item.univNm}
														</td>
														<td>
															<a href="${ADMIN_PATH}/startHpMngr/compInfo/update.do?compId=${item.compId}"> <strong>${item.compNm}</strong></a>
														</td>
														<td>
															${item.expsrYn}
														</td>
														<%-- <td>
															<c:if test="${item.rprsntvYn eq 'Y'}">
																완료
															</c:if>
															<c:if test="${item.rprsntvYn ne 'Y'}">
																진행중
															</c:if>
														</td> --%>
														<td>
															<a href="${ADMIN_PATH}/startHpMngr/compInfo/update.do?compId=${item.compId}" class="btn btn-outline-secondary waves-effect btn-sm">관리</a>
														</td>
														<td>
															<a href="${ADMIN_PATH}/startHpMngr/empInfo/divCu.do?compId=${item.compId}" class="btn btn-outline-secondary waves-effect btn-sm">관리</a>
														</td>
														<td>
															<!-- 창업팀 새소식 게시판 관리자 링크로 연결할 것! -->
															<a href="${ADMIN_PATH}/board/article/list.do?teamId=${item.compId}&bcId=startup_news&tempLayout=print&activeUrl=http://localhost:8080/main/asacms/startHpMngr/list.do" class="btn btn-outline-warning waves-effect waves-light btn-sm">관리</a>
														</td>
														<td>
															<!-- 창업 제품 소개 및 장터 링크로 연결할 것! -->
															<a href="${ADMIN_PATH}/board/article/list.do?baCategory2=${item.univId}&baCategory3=${item.compId}&bcId=product&tempLayout=print&activeUrl=http://localhost:8081/main/asacms/board/config/list.do" class="btn btn-outline-warning waves-effect waves-light btn-sm">관리</a>
														</td>
														<td>
															<fmt:parseDate value="${item.updDt}" var="parseDate1" pattern="yyyyMMddHHmmss" scope="page"/>
															<fmt:formatDate value="${parseDate1}" pattern="yyyy-MM-dd"/>
														</td>
													</tr>
												</c:forEach>
											</tbody>
										</table>
	        						</div>
	        						
	        						<div class="form-inline">
										<div class="mr-auto insertDiv">
											<a href="${ADMIN_PATH}/startHpMngr/compInfo/insert.do" class="btn btn-primary waves-effect waves-light">등록</a>
											<button id="deleteBtn" class="btn btn-danger waves-effect waves-light">삭제</button>
										</div>
										
									<c:if test="${teamYn eq 'N'}">
										<div>
											<span>총 ${paging.rowTotal}건의 데이터가 있습니다.</span>
											<input type="hidden" id="tot" value="${paging.rowTotal}" />
										</div>
										</c:if>
									</div>
	        						
	        						<!-- paging -->
	        						<c:if test="${teamYn eq 'N'}">
										<div>
											<c:out value="${paging.adminTextTag}" escapeXml="false"/>
										</div>
									</c:if>
	        		
	        					</div>
        					</div>
       					</div>
     				</div>
	        		<!-- End 목록 -->
	        		
	        	</div> <!-- End container -->
	        
	        
	    	</div> <!-- End Page content Wrapper -->
	    	
	    </div> <!-- End content -->
	</div>    
	<!-- End Right content here -->

<c:import url="/WEB-INF/jsp/egovframework/com/asapro/admin/layout/footer.jsp" charEncoding="UTF-8" />