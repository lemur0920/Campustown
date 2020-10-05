<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="asapro" uri="http://www.asadal.com" %>


<c:import url="/WEB-INF/jsp/egovframework/com/asapro/admin/layout/header.jsp" charEncoding="UTF-8" />

<script src="${CONTEXT_PATH }/assets/ckeditor/ckeditor4/ckeditor.js"></script>

<script>
jQuery(function($){
	
	/* 1. 이벤트 처리 */
	
	// 달력
	$('.datepicker-ui').datepicker();
	
	$('#yearSel').prop("selectedIndex", 0);
	
	//첨부파일
	//------------------------------------------
	//------------------------- 첨부파일 등록 Start
	//-------------------------------------------
	<c:if test="${policyCfrncForm.poUploadFileNum > 0}">
	$('.selectFile').multiSelector({
		list_target: 'fileList'
		,fileItemCssSelector: 'file_add'
		,list_delete_fileid: 'deleteFileList'
		,max: ${policyCfrncForm.poUploadFileNum}
	});
	</c:if>
	//------------------------- 첨부파일 등록 End
	
	// 정책협의회 정보 등록
	$('.plicyInsertBtn').on('click', function(){
		//alert("click~!!!");
		
		if(!confirm("정책협의회 관리 정보를 등록 하시겠습니까?")){
			return;
		} else {
			
			var queryString = $("#policyCfrncDtlForm").serialize();
			$.ajax({
				url : '${ADMIN_PATH}/policyCfrnc/dtl/jsonCR.do'
					, type : 'post'
					, dataType: 'json'
					, data:queryString
			}).done(function(result){
				if(result != null){
					alert('등록이 완료됬습니다.');

					var str = '';
					str += '<tr>';
					
					str += '<td>';
					str += '<div class="container">';
					str += '<div class="row">';
					str += '<input type="text" id="prtnDt'+result.dtlSeq+'" name="prtnDt" class="form-control mr-sm-1 mb-1 datepicker-ui" value="'+result.prtnDt+'" readonly />';
					str += '</div>';
					str += '</div>';
					str += '</td>';
					
					str += '<td>';
					str += '<textarea rows="1" cols="20" id="prtnMatter'+result.dtlSeq+'" name="prtnMatter" class="form-control mr-sm-4 mb-4" maxlength="70" >'+result.prtnMatter+'</textarea>';
					str += '</td>';
					
					str += '<td>';
					str += '<textarea rows="1" cols="20" id="rm'+result.dtlSeq+'" name="rm" class="form-control mr-sm-4 mb-4" maxlength="70" >'+result.rm+'</textarea>';
					str += '</td>';
					
					str += '<td>';
					str += '<button type="button" class="btn btn-sm ml-2 btn-primary waves-effect waves-light" data-dtlseq="'+result.dtlSeq+'" onclick="fnUpdate(this);">수정</button>';
					str += '<button type="button" class="btn btn-sm ml-2 btn-primary waves-effect waves-light" data-dtlseq="'+result.dtlSeq+'" onclick="fnDelete(this);">삭제</button>';
					str += '</td>';
					
					str += '</tr>';
					 
					$('#policy_cfrnc_tbody').append(str);
					
					// undefined, 빈 값 처리 (<- 이제 필요 없음!!! [retur값은 success(.done) 밖에 있어야 함!])
					if(trim($('#prtnDt'+result.dtlSeq).val())){
						$('#prtnDt'+result.dtlSeq).val("");
					}
					if(trim($('#prtnMatter'+result.dtlSeq).val())){
						$('#prtnMatter'+result.dtlSeq).val("");
					}
					if(trim($('#rm'+result.dtlSeq).val())){
						$('#rm'+result.dtlSeq).val("");
					}
					
					// 등록 필드 값 비워주기
					$('#prtnDt').val("");
					$('#prtnMatter').val("");
					$('#rm').val("");
					
				}else{
					alert('값을 불러오지 못하였습니다.[fail]');
				}
					
			});
		}
	});
	
	/* 2. 정규식 */
	// 숫자랑 , -
	
	$("#sugestUnivChargerTel, #jobChargerTel, #processChargerTel").on("keyup", function() {
    	$(this).val($(this).val().replace(/[^0-9,\,-]/g,""));
	});
	
});

function trim(ud){
	//alert("오오오니???")
	var rtn = false;
	if(ud == 'undefined'){
		rtn = true;
	}
	return rtn;
}

function fn_save(){
	// 상태
	if($("input:radio[id^=sttus]:checked").length == 0){
		alert("상태는 필수 값 입니다.");
		$("#sttuslo").focus();
		return;
	}
	/* 
	// 완료여부
	if($("input:radio[id^=comptYn]:checked").length == 0){
		alert("완료여부는 필수 값 입니다.");
		$("#comptYnN").focus();
		return;
	}
	 */
	// 제목
	if($("#sj").val() == '' || $("#sj").val() == null){
		alert("제목은 필수 값 입니다.");
		$("#sj").focus();
		return;
	}
	
	// 업무담당자
	
	// 시/구
	
	if($("#processChargerDept").val() == '' || $("#processChargerDept").val() == null){
		alert("시/구 정보는 필수 값 입니다.");
		$("#processChargerDept").focus();
		return;
	}
	
	// 부서
	if($("#jobChargerDept").val() == '' || $("#jobChargerDept").val() == null){
		alert("업무담당자 부서명은 필수 값 입니다.");
		$("#jobChargerDept").focus();
		return;
	}
	
	// 이름
	if($("#jobChargerNm").val() == '' || $("#jobChargerNm").val() == null){
		alert("업무담당자 이름은 필수 값 입니다.");
		$("#jobChargerNm").focus();
		return;
	}
	
	// 연락처
	if($("#jobChargerTel").val() == '' || $("#jobChargerTel").val() == null){
		alert("업무담당자 연락처는 필수 값 입니다.");
		$("#jobChargerTel").focus();
		return;
	}
	
	
	if(!confirm("계속 진행 하시겠습니까?")){
		return;
	}else{
		// 히든값 처리
		$("#policyCfrncForm").submit();
	}	
}

//추진정보 수정
function fnUpdate(th){
	//alert("수정~!!!");
	
	var dtlSeq = th.getAttribute("data-dtlseq");
	
	var prtnDt = $('#prtnDt'+dtlSeq).val();
	var prtnMatter = $('#prtnMatter'+dtlSeq).val();
	var rm = $('#rm'+dtlSeq).val();
	
	if(!confirm("해당사항을 수정하시겠습니까?")){
		return;
	}else{
		$.ajax({
			url : '${ADMIN_PATH}/policyCfrnc/dtl/jsonU.do'
				, type : 'get'
				, dataType: 'json'
				, data:{
					dtlSeq : dtlSeq, 
					prtnDt : prtnDt,
					prtnMatter : prtnMatter,
					rm : rm
				}
		}).done(function(result){
			if(result > 0){
				alert('업데이트가 완료됬습니다.');
				
			}
		}).fail(function(result){
			alert('값을 불러오지 못하였습니다.[fail]');
		});
	}
}

//추진정보 삭제
function fnDelete(th){
	//alert("삭제~!!!");
	
	var dtlSeq = th.getAttribute("data-dtlseq");
	
	var tr = $(th).parent().parent();
	
	if(!confirm("해당사항을 삭제하시겠습니까?")){
		return;
	}else{
		
		$.ajax({
			url : '${ADMIN_PATH}/policyCfrnc/dtl/jsonD.do'
				, type : 'get'
				, dataType: 'json'
				, data:{
					dtlSeq : dtlSeq
				}
		}).done(function(result){
			if(result > 0){
				alert('업데이트가 완료됬습니다.');
				tr.remove();
			}
		}).fail(function(result){
			alert('값을 불러오지 못하였습니다.[fail]');
		});
	}
	
	
}




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
					<!-- 입력폼 -->
		    		<div class="row">
					    <div class="col-sm-12">
					        <div class="card m-b-15">
						            	 	
					            <div class="card-body">
					            
					            	<!-- info -->
					            	<c:if test="${formMode == 'UPDATE'}">
										<div class="p-3 mb-3 bg-light text-info font-weight-bold">
											<div><i class="mdi mdi-information"></i> 정책협의회 관리 - 상세화면 </div>
										</div>
									</c:if>
					            	<c:if test="${formMode == 'INSERT'}">
										<div class="p-3 mb-3 bg-light text-info font-weight-bold">
											<div><i class="mdi mdi-information"></i> 정책협의회 관리 - 등록화면</div>
										</div>
									</c:if>
									
									<div class="card">
					            	 	<div class="card-header">
				            	 		■ 상세내용
					            	 	</div>
							        </div>

									<c:if test="${formMode == 'INSERT'}"><c:set var="actionUrl" value="${ADMIN_PATH}/policyCfrnc/insert.do" /></c:if>
									<c:if test="${formMode == 'UPDATE'}"><c:set var="actionUrl" value="${ADMIN_PATH}/policyCfrnc/update.do" /></c:if>
									
					            	 <form:form modelAttribute="policyCfrncForm" id="policyCfrncForm" cssClass="" action="${actionUrl}" method="post" enctype="multipart/form-data">
					            	 	<form:hidden path="mngSeq" />
					            	 	<c:if test="${formMode == 'UPDATE'}">
					            	 		<%-- 
						            	 	<form:hidden path="manageYear" />
						            	 	<form:hidden path="manageOrdr" />
						            	 	 --%>
						            	 	<form:hidden path="manageNoOrdr" />
					            	 	</c:if>
					            	 	<table class="table table-bordered univHpMngr_table">
											<colgroup>
											 	<col style="width: 15%;">
											 	<col style="width: 85%;">
											</colgroup>
											<tbody>
												<tr>
													<th scope="row" class="align-middle table-active text-center">
														구분 <span class="text-danger">*</span>
													</th>
													
													<td>
														<div class="container">
															<div class="row">
																
																<span class="mt-2">년도&nbsp;: </span>
																
																<c:set var="now" value="<%=new java.util.Date()%>" />
																<fmt:formatDate var="nowYear" value="${now}" pattern="yyyy" />
																
																<c:set var="cnt" value="0" />
																
																<%-- <c:if test="${formMode == 'INSERT'}"> --%>
																	<form:select path="manageYear" class="form-control col-sm-2 ml-2 mr-2" title="년도 선택">
																		<c:forEach var="i" begin="${nowYear-2}" end="${nowYear}">
																			<form:option value="${nowYear-cnt}">${nowYear-cnt}</form:option>
																			<c:set var="cnt" value="${cnt+1 }"/>
																		</c:forEach>
																	</form:select>
																	 <%-- <span class="mt-2">&nbsp;${nowYear }</span> --%>
																	<%-- <form:hidden path="manageYear" value="${nowYear }"/> --%>
																	<%-- </c:if> --%>
																<%-- 
																	<c:if test="${formMode == 'UPDATE'}">
																		<span class="mt-2">&nbsp;${policyCfrncForm.manageYear }</span>
																	</c:if>
																 --%>
																
																<span class="mt-2">반기: </span> 
																<form:select path="cardiNum" class="form-control col-sm-2 ml-2 mr-2" title="반기 선택">
																	<form:option value="1">상반기</form:option>
																	<form:option value="2">하반기</form:option>
																</form:select>
																
																
																<span class="mt-2">회수: </span>
																<form:select path="manageOrdr" class="form-control col-sm-2 ml-2 mr-2" title="년도 선택">
																	<c:forEach var="i" begin="1" end="8">
																		<form:option value="${i }">${i }</form:option>
																	</c:forEach>
																</form:select>
															</div>
														</div>
													</td>
												</tr>
												<%-- <c:if test="${formMode == 'UPDATE'}"> --%>	
													<tr>
														<th scope="row" class="align-middle table-active text-center">
															관리번호
														</th>
														
														<td>
															<div class="container">
																<div class="row">
																	<form:input path="manageNo" cssClass="form-control col-sm-9" cssErrorClass="col-sm-9 is-invalid" maxlength="150" />
																</div>
															</div>
															<%-- ${policyCfrncForm.manageNo } --%>
														</td>
													</tr>	
												<%-- </c:if> --%>
	
												<tr>
													<th scope="row" class="align-middle table-active text-center">
														상태 <span class="text-danger">*</span>
													</th>
													
													<td>
														<div class="container">
															<div class="row">
																<c:forEach items="${sttusCodeList }" var="item" varStatus="status" >
																	<div class="custom-control custom-radio custom-control-inline">
																		<form:radiobutton path="sttus" id="sttus${item.codeId}" class="custom-control-input" value="${item.codeId }"/>
																		<label for="sttus${item.codeId}" class="custom-control-label">${item.codeName }</label>
																	</div>		
																</c:forEach>
															</div>
														</div>
													</td>
												</tr>	
												
												<!-- 
												<tr>
													<th scope="row" class="align-middle table-active text-center">
														완료여부 <span class="text-danger">*</span>
													</th>
													
													<td>
														<div class="container">
															<div class="row">
																<c:forEach items="${comptYnList }" var="item" varStatus="status" >
																	<div class="custom-control custom-radio custom-control-inline">
																		<form:radiobutton path="comptYn" id="comptYn${item.codeId}" class="custom-control-input" value="${item.codeId }"/>
																		<label for="comptYn${item.codeId}" class="custom-control-label">${item.codeName }</label>
																	</div>		
																</c:forEach>
															</div>
														</div>
													</td>
												</tr>
												 -->
												
												
												<tr>
													<th scope="row" class="align-middle table-active text-center">
														건의과제 <span class="text-danger">*</span>
													</th>
													
													<td>
														<div class="container">
															<div class="row">
																<form:input path="sj" cssClass="form-control col-sm-9" cssErrorClass="col-sm-9 is-invalid" maxlength="150" />
															</div>
														</div>
													</td>
												</tr>	
												
												<tr>
													<th scope="row" class="align-middle table-active text-center">
														건의과제 주요내용
													</th>
													
													<td>
														<div class="container">
															<div class="row">
																<form:textarea path="sugestTaskMainCn" cssClass="form-control col-sm-8" rows="3" placeholder="건의과제 주요내용" />
																<form:errors path="sugestTaskMainCn" element="div" cssClass="text-danger" />
															</div>
														</div>	
													</td>
												</tr>
												<%-- 
												<tr>
													<th scope="row" class="align-middle table-active text-center">
														관련 법령
													</th>
													
													<td>
														<div class="container">
															<div class="row">
																<form:textarea path="relateLaword" cssClass="form-control col-sm-8" rows="3" placeholder="관련 법령" />
																<form:errors path="relateLaword" element="div" cssClass="text-danger" />
															</div>
														</div>	
													</td>
												</tr>
												 --%>
												
												<tr>
													<th scope="row" class="align-middle table-active text-center">
														제출 대학
													</th>
													
													<td>
														<div class="container">
															<div class="row">
																<form:input path="sugestUnivChargerDept" cssClass="form-control col-sm-2 mr-2" cssErrorClass="col-sm-2 is-invalid" maxlength="70" />
																<span class="mt-2">대학교</span>
																<form:input path="sugestUnivChargerNm" cssClass="form-control col-sm-2 ml-2 mr-2" cssErrorClass="col-sm-2 is-invalid" maxlength="30" />
																<span class="mt-2"> 총장</span>
																<%-- 
																<span class="mt-2">연락처: </span>
																<form:input path="sugestUnivChargerTel" cssClass="form-control col-sm-2 ml-2" cssErrorClass="col-sm-2 is-invalid" maxlength="15" />
																 --%>
															</div>
														</div>
													</td>
												</tr>
													
												<tr>
													<th scope="row" class="align-middle table-active text-center">
														업무 담당자 <span class="text-danger">*</span>
													</th>
													
													<td>
														<div class="container">
															<div class="row">
																
																<form:input path="processChargerDept" cssClass="form-control col-sm-2 mr-2" cssErrorClass="col-sm-2 is-invalid" maxlength="70" />
																<span class="mt-2">시/구 </span>
																
																<form:input path="jobChargerDept" cssClass="form-control col-sm-2 ml-3 mr-2" cssErrorClass="col-sm-2 is-invalid" maxlength="70" />
																<span class="mt-2">부서</span>
																</div>
																<br/>
																<div class="row">
																<span class="mt-2">담당자: </span>
																<form:input path="jobChargerNm" cssClass="form-control col-sm-2 ml-2 mr-2" cssErrorClass="col-sm-2 is-invalid" maxlength="30" />
																<span class="mt-2">연락처: </span>
																<form:input path="jobChargerTel" cssClass="form-control col-sm-2 ml-2" cssErrorClass="col-sm-2 is-invalid" maxlength="15" />
															</div>
														</div>
													</td>
												</tr>
													
												<%-- 			
												<tr>
													<th scope="row" class="align-middle table-active text-center">
														처리 담당자
													</th>
													
													<td>
														<div class="container">
															<div class="row">
																<span class="mt-2">부서: </span>
																<form:input path="processChargerDept" cssClass="form-control col-sm-2 ml-2" cssErrorClass="col-sm-2 is-invalid" maxlength="70" />
																&nbsp;&nbsp;&nbsp;
																<span class="mt-2">이름: </span>
																<form:input path="processChargerNm" cssClass="form-control col-sm-2 ml-2" cssErrorClass="col-sm-2 is-invalid" maxlength="30" />
																&nbsp;&nbsp;&nbsp;
																<span class="mt-2">연락처: </span>
																<form:input path="processChargerTel" cssClass="form-control col-sm-2 ml-2" cssErrorClass="col-sm-2 is-invalid" maxlength="15" />
															</div>
														</div>
													</td>
												</tr>	
												 --%>
												
												<tr>
													<th scope="row" class="align-middle table-active text-center">
														첨부파일
													</th>
													
													<td>
														<div class="container">
															<div class="row">
															<c:if test="${policyCfrncForm.poUploadFileNum > 0}">
																<div class="col-sm">
																	<input type="file" name="poMultipartFiles[0]" id="poMultipartFiles0" class="selectFile col-form-label"/>
																	<form:errors path="poMultipartFiles" element="div" cssClass="text-danger col-form-label ml-2" />
																	<span class="bg-light font-weight-bold text-info">- 파일첨부는 총${policyCfrncForm.poUploadFileNum}개까지 가능합니다.(파일당${policyCfrncForm.poUploadSizeMax}MB제한)</span>
																	
																	<div class="mt-2" id="fileList">
																		<c:forEach items="${policyCfrncForm.poFileInfos}" var="poFileInfo" varStatus="vs">
																			<div class="file_add">
																				<span>
																					${poFileInfo.fileOriginalName}(${poFileInfo.fileSizeString})
																				</span>
																				<i class="deleteBtn ml-2 fa fa-times text-danger" style="cursor: pointer;" data-fileid="${poFileInfo.fileId}"></i>
																			</div>
																		</c:forEach>
																	</div>
																	
																	<div id="deleteFileList"></div>
																	<c:if test="${not empty fileInfoUploadResult}">
																		<div class="text-danger">
																			<div>게시물은 정상적으로 등록되었으나 다음의 파일을 업로드 하지 못하였습니다.</div>
																			<ul>
																				<c:forEach items="${fileInfoUploadResult.notUploadableFiles}" var="item"><li>${item} 파일이 업로드 금지파일(업로드 금지확장자)에 해당합니다.</li></c:forEach>
																				<c:forEach items="${fileInfoUploadResult.uploadSizeOverFiles}" var="item"><li>${item} 파일이 업로드 파일사이즈를 초과하였습니다.</li></c:forEach>
																				<c:forEach items="${fileInfoUploadResult.altTextMissingFiles}" var="item"><li>${item} 파일의 대체텍스트가 누락되었습니다.</li></c:forEach>
																			</ul>
																		</div>
																	</c:if>
																</div>
															</c:if>
															</div>
														</div>
													</td>
												</tr>
											</tbody>
					            	 	</table>
					            	 	
					            	 	<div class="card">
											<div class="card-body">
												<div class="table-responsive-sm">
													<button type="button" onclick="fn_save()" class="btn btn-primary waves-effect waves-light">저장</button>
													<a href="${ADMIN_PATH}/policyCfrnc/list.do" class="btn btn-secondary waves-effect m-l-5">목록</a>
													<%-- <a href="${ADMIN_PATH}/youthSupport/list.do?${youthSupportSearch.queryString }" class="btn btn-secondary waves-effect m-l-5">목록</a> --%>
												</div>
											</div>
										</div>
					            	 </form:form>
					            	 
					            	 <c:if test="${formMode == 'UPDATE' && policyCfrncForm.sttus eq 'lo'}">
					            	 
						            	 <div class="card">
						            	 	<div class="card-header">
					            	 		■ 추진 정보
						            	 	</div>
								         </div>
								         
								         <div class="card-body">
						            	 	<div class="table-responsive-sm">
												<table class="table table-bordered univHpMngr_table">
													<colgroup>
													</colgroup>
													<thead>
														<tr>
															<th scope="row" class="align-middle table-active text-center">
																추진일시
															</th>
															<th scope="row" class="align-middle table-active text-center">
																추진사항
															</th>
															<th scope="row" class="align-middle table-active text-center">
																비고
															</th>
															<th scope="row" class="align-middle table-active text-center">
																관리
															</th>
														</tr>
													</thead>
													<tbody id="policy_cfrnc_tbody">
														<c:forEach items="${list}" var="item" varStatus="vs">
															<tr>
																<td>
																	<div class="container">
																		<div class="row">
																			<input type="text" id="prtnDt${item.dtlSeq}" name="prtnDt" class="form-control mr-sm-1 mb-1 datepicker-ui" value="${item.prtnDt }" readonly />
																			<%-- <form:input path="fondDt" cssClass="form-control col-sm-2 datepicker-ui" cssErrorClass="form-control col-sm-2 datepicker-ui is-invalid" autocomplete="off" readonly="true"/> --%>
																		</div>
																	</div>	
																</td>
																<td>
																	<textarea rows="1" cols="20" id="prtnMatter${item.dtlSeq}" name="prtnMatter" class="form-control mr-sm-4 mb-4" maxlength="70" >${item.prtnMatter }</textarea>
																</td>
																<td>
																	<textarea rows="1" cols="20" id="rm${item.dtlSeq}" name="rm" class="form-control mr-sm-4 mb-4" maxlength="70" >${item.rm }</textarea>
																</td>
																<td>
																	<button type="button" class="btn btn-sm ml-2 btn-primary waves-effect waves-light idxUpdateBtn" data-dtlseq="${item.dtlSeq}" onclick="fnUpdate(this);">수정</button>
																	<button type="button" class="btn btn-sm ml-2 btn-primary waves-effect waves-light idxDeleteBtn" data-dtlseq="${item.dtlSeq}" onclick="fnDelete(this);">삭제</button>
																</td>
															</tr>
														</c:forEach>
													</tbody>
												
													<form name="policyCfrncDtlForm" id="policyCfrncDtlForm" action="" method="">
										         	 	<input type="hidden" id="mngSeq" name="mngSeq" value="${policyCfrncForm.mngSeq }" />
										         	 	<tbody>
										         	 		<tr>
										         	 			<td>
																	<div class="container">
																		<div class="row">
																			<input type="text" id="prtnDt" name="prtnDt" class="form-control mr-sm-1 mb-1 datepicker-ui" readonly />
																			<%-- <form:input path="fondDt" cssClass="form-control col-sm-2 datepicker-ui" cssErrorClass="form-control col-sm-2 datepicker-ui is-invalid" autocomplete="off" readonly="true"/> --%>
																		</div>
																	</div>	
																</td>
																<td>
																	<textarea rows="1" cols="20" id="prtnMatter" name="prtnMatter" class="form-control mr-sm-4 mb-4" maxlength="70" placeholder="추진사항"></textarea>
																</td>
																<td>
																	<textarea rows="1" cols="20" id="rm" name="rm" class="form-control mr-sm-4 mb-4" maxlength="70" placeholder="비고"></textarea>
																</td>
																<td>
																	<button type="button" class="btn btn-secondary waves-effect m-l-5 ml-2 mr-2 plicyInsertBtn" >추가</button>
																</td>
										         	 		</tr>			
										         	 	</tbody>
								         	 		</form>
							         	 		</table>
											</div>
										 </div>						
							         </c:if>
					            	 
					            </div>
				            
				            
				            
				            </div>
			            </div>
		            </div> <!-- End 입력폼 -->
		    		<!-- ============================= End 메뉴별 컨텐츠 ============================ -->
		            
		            
	    		</div><!-- End container -->
	    	</div> <!-- End Page content Wrapper -->
	    	
	    </div> <!-- End content -->
    </div> <!-- End Right content here -->
<script>
CKEDITOR.replace( 'sugestTaskMainCn',{
	filebrowserImageUploadUrl : GLOBAL.ADMIN_PATH + '/file/editor/upload.do?module=policyCfrnc&moduleId=&moduleSub=&moduleSubId='
	, height : 200
} );
CKEDITOR.replace( 'relateLaword',{
	filebrowserImageUploadUrl : GLOBAL.ADMIN_PATH + '/file/editor/upload.do?module=policyCfrnc&moduleId=&moduleSub=&moduleSubId='
	, height : 200
} );

</script> 
    
<c:import url="/WEB-INF/jsp/egovframework/com/asapro/admin/layout/footer.jsp" charEncoding="UTF-8" />     