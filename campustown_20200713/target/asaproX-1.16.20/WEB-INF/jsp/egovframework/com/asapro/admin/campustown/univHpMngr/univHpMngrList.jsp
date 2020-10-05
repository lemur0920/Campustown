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
	
	// 검색 구분 초기화
	//$(".scArea").hide();
	//$(".scUniv").hide();
	/* 
	// 검색 구분값 클릭 시
	$("#scDiv").on("click", function(){
		if($(this).val() == 0){
			$(".scArea").hide();
			$(".scUniv").hide();
		}	
		if($(this).val() == 1){
			$(".scArea").show();
			$(".scUniv").hide();
			
		}else if($(this).val() == 2){
			$(".scUniv").show();
			$(".scArea").hide();
		}
	});
	 */
	 
	
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
	
	// 검색 버튼 클릭시 
	$("#searchBtn").on("click", function(){
		//alert("검색 버튼 클릭~!!! ");
		$("#univHpMngrSearch").submit();
	});
	
	//삭제 Ajax
    $('#deleteBtn').on('click', function () {
    	var univIds = [];
		$('[name=univIds]:checked').each(function(idx, el){
			univIds.push(el.value);
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
                        if (univIds.length === 0) {
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
				url : '${ADMIN_PATH}/univHpMngr/delete.do'
				, type : 'post'
				, dataType: 'json'
				, data: {
					univIds : univIds
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
					            
					            	
						            <form:form modelAttribute="univHpMngrSearch" cssClass="" action="${ADMIN_PATH}/univHpMngr/list.do" method="get">
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
											</form:select>
											
											<form:select path="sortDirection" cssClass="form-control mr-sm-2 mb-2" title="정렬방향">
												<form:option value="DESC" label="내림차순" />
												<form:option value="ASC" label="오름차순" />
											</form:select>
										</div>
										<!-- 
										<div class="form-row mt-2 form-inline" >
										</div>
										 -->
										
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
											<form:select path="sc" id="scDiv" cssClass="form-control mr-sm-2 mb-2" title="검색조건">
												<form:option value="">전체</form:option>
												<!-- <option value="1" label="지역" /> -->
												<form:option value="univNm" label="캠퍼스타운명" />
											</form:select>
											
											<form:input path="sv" cssClass="form-control mr-sm-2 mb-2" title="검색어" placeholder="Search"/>
											
											
											<!-- 2. 대학 : 현재 등록되어있는 대학 부서코드 정보 뿌리기 -->
											<%-- 
											<c:if test="${org eq 'university'}">
												<form:select path="univId" readonly="true" onFocus="this.initialSelect = this.selectedIndex;"
															 onChange="this.selectedIndex = this.initialSelect;"
															 cssClass="form-control mr-sm-2 mb-2 scUniv" title="대학">
													<option value="">전체</option>
													<form:options items="${departmentList}" itemLabel="depName" itemValue="depId"/>
												</form:select>
											</c:if>
											<c:if test="${org ne 'university'}">
												<form:select path="univId" cssClass="form-control mr-sm-2 mb-2 scUniv" title="대학">
													<option value="">전체</option>
													<form:options items="${departmentList}" itemLabel="depName" itemValue="depId"/>
												</form:select>
											</c:if>
											 --%>
											<button id="searchBtn" class="btn btn-outline-success waves-effect mr-sm-2 mb-2" type="button">검색</button>
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
													<th class="text-center">
														<div class="custom-control custom-checkbox">
															<input type="checkbox" class="custom-control-input" id="selectAll" onclick="jQuery('[name=univIds]').each(function(idx,el){this.checked=jQuery('#selectAll')[0].checked;});"/>
															<label class="custom-control-label" for="selectAll"></label>
														</div>
													</th>
													<th>지역</th>
													<th>캠퍼스타운</th>
													<th>노출여부</th>
													<th>캠퍼스타운 정보</th>
													<th>새소식</th>
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
														<td class="text-center">
															<div class="custom-control custom-checkbox">
																<input type="checkbox" name="univIds" value="${item.univId}" class="custom-control-input" id="univIds${vs.count }" />
																<label class="custom-control-label" for="univIds${vs.count }"></label>
															</div>
														</td>
														<td>
															${asapro:codeName(item.areaGuCd,sigunguCodeList)}
														</td>
														<td>
															<%-- <a href="${ADMIN_PATH}/univHpMngr/update.do?univId=${item.univId}"> <strong>${asapro:depName(item.univId,departmentList)}</strong></a> --%>
															<a href="${ADMIN_PATH}/univHpMngr/update.do?univId=${item.univId}"> <strong>${item.univNm}</strong></a>
														</td>
														<td>
															${item.expsrYn}
														</td>
														
														<td>
															<a href="${ADMIN_PATH}/univHpMngr/update.do?univId=${item.univId}" class="btn btn-outline-secondary waves-effect btn-sm">관리</a>
														</td>
														<td>
															<!-- 캠퍼스타운 새소식 게시판 관리자 링크로 연결할 것! -->
															<a href="${ADMIN_PATH}/board/article/list.do?depId=${item.univId}&bcId=university_news" class="btn btn-outline-warning waves-effect waves-light btn-sm">관리</a>
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
	        							<c:if test="${(org eq 'university' and paging.rowTotal eq 0) or (org ne 'university')}">
										<div class="mr-auto">
											<a href="${ADMIN_PATH}/univHpMngr/insert.do?univId=${item.univId}" class="btn btn-primary waves-effect waves-light">등록</a>
											<button id="deleteBtn" class="btn btn-danger waves-effect waves-light">삭제</button>
										</div>
										</c:if>
										
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
     				</div>
	        		<!-- End 목록 -->
	        		
	        		
	        		
	        	
	        	</div> <!-- End container -->
	        
	        
	    	</div> <!-- End Page content Wrapper -->
	    	
	    </div> <!-- End content -->
	</div>    
	<!-- End Right content here -->
 
<c:import url="/WEB-INF/jsp/egovframework/com/asapro/admin/layout/footer.jsp" charEncoding="UTF-8" />