<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="asapro" uri="http://www.asadal.com" %>

<c:import url="/WEB-INF/jsp/egovframework/com/asapro/admin/layout/header.jsp" charEncoding="UTF-8" />
<script>
jQuery(function($){
	// 달력
	$('.datepicker-ui').datepicker();
	
	// 초기화 버튼
	$('#resetBtn').on('click', function(){
		$("#pageSize").prop("selectedIndex", 0);
		$("#sortOrder").prop("selectedIndex", 0);
		$("#sortDirection").prop("selectedIndex", 0);
		
		$("#scRegStartDt").val("");
		$("#scRegEndDt").val("");
		
		$("#scDiv").prop("selectedIndex", 0);
		$("#scSttus").prop("selectedIndex", 0);
		$("#scComptYn").prop("selectedIndex", 0);
		
		$("#sv").val("");
		$("#sc").prop("selectedIndex", 0);
	});
	
	//삭제 Ajax
	$('#deleteBtn').on('click', function () {
		//alert("click~!!!");
		var mngSeqs = [];
		$('[name=mngSeqs]:checked').each(function(idx, el){
			mngSeqs.push(el.value);
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
	                    if (mngSeqs.length === 0) {
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
				url : '${ADMIN_PATH}/policyCfrnc/delete.do'
				, type : 'post'
				, dataType: 'json'
				, data: {
					mngSeqs : mngSeqs
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
						            <form:form modelAttribute="policyCfrncSearch" cssClass="" action="${ADMIN_PATH}/policyCfrnc/list.do" method="get">
						        		<div class="form-row mr-auto mt-2 form-inline">
											<label class="mr-sm-2 mb-2">정렬조건</label>
											<form:select path="pageSize" cssClass="form-control mr-sm-2 mb-2" title="페이지크기" >
												<form:option value="10">10개씩</form:option>
												<form:option value="20">20개씩</form:option>
												<form:option value="40">40개씩</form:option>
											</form:select>
											
											<form:select path="sortOrder" cssClass="form-control mr-sm-2 mb-2" title="정렬기준">
												<form:option value="MNG_DIV" label="구분" />
												<form:option value="UPD_DT" label="최근 수정일" />
											</form:select>
											
											<form:select path="sortDirection" cssClass="form-control mr-sm-2 mb-2" title="정렬방향">
												<form:option value="DESC" label="내림차순" />
												<form:option value="ASC" label="오름차순" />
											</form:select>
										</div>
										
										<div class="form-row mt-2 form-inline" >
											<label for="" class="mr-sm-3 mb-2">작성일</label>
											
											<form:input path="scRegStartDt" cssClass="form-control mr-sm-2 mb-2 datepicker-ui" cssErrorClass="form-control col-sm-1 datepicker-ui is-invalid" autocomplete="off" readonly="true"/>
											<span class="mx-2"> ~ </span>
											<form:input path="scRegEndDt" cssClass="form-control mr-sm-2 mb-2 datepicker-ui" cssErrorClass="form-control col-sm-1 datepicker-ui is-invalid" autocomplete="off" readonly="true"/>
										</div>
										
										<div class="form-row mt-2 form-inline" >
											<label class="mr-sm-2 mb-2">검색조건</label>
											
											<form:select path="scDiv" cssClass="form-control mr-sm-2 mb-2" title="관리구분">
												<option value="">구분</option>
												<form:options items="${divList}" itemLabel="mngDiv" itemValue="mngDiv"/>
											</form:select>
											<form:select path="scSttus" cssClass="form-control mr-sm-2 mb-2" title="상태">
												<option value="">상태</option>
												<form:options items="${sttusCodeList}" itemLabel="codeName" itemValue="codeId"/>
											</form:select>
											<%-- 
											<form:select path="scComptYn" cssClass="form-control mr-sm-2 mb-2" title="완료여부">
												<option value="">완료여부</option>
												<form:options items="${comptYnList}" itemLabel="codeName" itemValue="codeId"/>
											</form:select>
											 --%>
											<form:select path="sc" id="scDiv" class="form-control mr-sm-2 mb-2" title="검색어 구분">
												<form:option value="">전체</form:option>
												<!-- <option value="1" label="지역" /> -->
												<form:option value="manageNo" label="관리번호" />
												<form:option value="sj" label="제목" />
												<form:option value="jobChargerDept" label="담당부서" />
												<form:option value="jobChargerNm" label="담당자" />
											</form:select>
											
											<form:input path="sv" cssClass="form-control mr-sm-2 mb-2" title="검색어" placeholder="Search"/>
											
											<button id="searchBtn" class="btn btn-outline-success waves-effect mr-sm-2 mb-2" type="submit">검색</button>
											<c:if test="${org ne 'university' && org ne 'startup'}">
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
													<th class="text-center hidCol">
														<div class="custom-control custom-checkbox">
															<input type="checkbox" class="custom-control-input" id="selectAll" onclick="jQuery('[name=mngSeqs]').each(function(idx,el){this.checked=jQuery('#selectAll')[0].checked;});"/>
															<label class="custom-control-label" for="selectAll"></label>
														</div>
													</th>
													<th>구분</th>
													<!-- <th>완료여부</th> -->
													<th>관리번호</th>
													<th>건의과제</th>
													<th>첨부</th>
													<th>소관부서</th>
													<th>검토결과</th>
													<th>업무담당자</th>
													<th>최근 수정일</th>
												</tr>
											</thead>
											
											<tbody>
												<c:if test="${empty paging.result}">
													<tr>
														<td colspan="8" class="text-center">데이터가 없습니다.</td>
													</tr>
												</c:if>
												
												<c:forEach items="${paging.result}" var="item" varStatus="vs">
													<tr>
														<%-- <td>${paging.rowTop - vs.index}</td> --%>
														<td class="text-center">
															<div class="custom-control custom-checkbox">
																<input type="checkbox" name="mngSeqs" value="${item.mngSeq}" class="custom-control-input" id="mngSeqs${vs.count }" />
																<label class="custom-control-label" for="mngSeqs${vs.count }"></label>
															</div>
														</td>
														<td>${item.mngDiv }</td>
														
														<%-- <td>${asapro:codeName(item.comptYn, comptYnList) }</td> --%>
														<td>${item.manageNo }</td>
														<td>
															<a href="${ADMIN_PATH}/policyCfrnc/update.do?mngSeq=${item.mngSeq}"> <strong>${item.sj}</strong></a>
														</td>
														<td>
															<c:forEach items="${item.poFileInfos}" var="fileItem" varStatus="fvs">
															<a href="${APP_PATH}/file/download/uu/${fileItem.fileUUID}" title="<c:out value="${fileItem.fileOriginalName}" escapeXml="true" />"><asapro:fileIcon fileInfo="${fileItem}" /></a>
															</c:forEach> 
														</td>
														<td>${item.jobChargerDept }</td>
														<td>${asapro:codeName(item.sttus, sttusCodeList) }</td>
														<td>${item.jobChargerNm }</td>
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
										<div class="mr-auto">
											<a href="${ADMIN_PATH}/policyCfrnc/insert.do" class="btn btn-primary waves-effect waves-light">등록</a>
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
     				</div>
	        		<!-- End 목록 -->
	        		
	        	
	        	</div> <!-- End container -->
	        
	        
	    	</div> <!-- End Page content Wrapper -->
	    	
	    </div> <!-- End content -->
	</div>    
	<!-- End Right content here -->

<c:import url="/WEB-INF/jsp/egovframework/com/asapro/admin/layout/footer.jsp" charEncoding="UTF-8" />