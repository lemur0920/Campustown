<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="asapro" uri="http://www.asadal.com" %>


<c:import url="/WEB-INF/jsp/egovframework/com/asapro/admin/layout/header.jsp" charEncoding="UTF-8" />

<script src="${CONTEXT_PATH }/assets/ckeditor/ckeditor4/ckeditor.js"></script>

<!-- Magnific popup -->
	<link href="/assets/plugins/magnific-popup/magnific-popup.css" rel="stylesheet" type="text/css">
<!-- Magnific popup -->
	<script src="/assets/plugins/magnific-popup/jquery.magnific-popup.min.js"></script>
	<script src="/assets/pages/lightbox.js"></script>

<script>
jQuery(function($){
	
	/* 1. 이벤트 처리 */
	
	// 달력
	$('.datepicker-ui').datepicker({
		yearRange: '1900:2050'
	});
	$('#tel1').hide();
	
	// 캠퍼스타운 명칭 수정, 저장 버튼 관련 처리 
	$('#univNmUpdSaveBtn').hide();
	
	$('#univNmUpdCheckBtn').on("click", function(){
		$('#univNmUpdCheckBtn').hide();
		$('#univNmUpdSaveBtn').show();
		$('#univNm').removeAttr("disabled");
	});
	
	$('#univNmUpdSaveBtn').on("click", function(){
		
		var univId = $("#univId").val();
		var univNm = $("#univNm").val();
		
		if(!confirm("계속 진행하시겠습니까?")){
			return;
		} else {
			$.ajax({
				url : '${ADMIN_PATH}/univHpMngr/nmCheck/jsonU.do'
				, type : 'get'
				, dataType: 'json'
				, data: {
					univId : univId,
					univNm : univNm
				}
			}).done(function(result){
				if(result > 0){
					alert('업데이트가 완료됬습니다.');
					$('#univNmUpdSaveBtn').hide();
					$('#univNmUpdCheckBtn').show();
					$('#univNm').attr("disabled", true);
				}else{
					alert('업데이트 실패!');
				}
			}).fail(function(result){
				alert('값을 불러오지 못하였습니다.[fail]');	
			});
			
		}
		
	});
	
	
	// 직접입력 값 초기화		
	$("#selOpt").val($("#tel1").val());
	if($("#selOpt").val() == null){
		$("#selOpt").val("000");
	}
	if($("#selOpt").val() == "000"){
		$('#tel1').show();
	}else{
		$('#tel1').hide();
	}
	

	$("#selOpt").on("click", function(){
		if($("#selOpt").val() == "000"){
			$('#tel1').show();
		}else{
			$('#tel1').hide();
		}
	});
	
	$("#emailSel").find("option").each(function(){
		if($('#email_2').val() != '' && $('#email_2').val() != null){
		    if(this.value == $('#email_2').val()){
		    	$(this).val($('#email_2').val()).prop("selected", true);
		    	//$('#email2').hide();
		    	return;
		    }else{
		    	$("#emailSel option:last").attr("selected", true);
		    	$('#email_2').show();
		    	return;
		    }
		}else{
			$("#emailSel option:first").attr("selected", true);
			//$('#email2').hide();
			return;
		}
	});
	
	if($('#emailSel').val() == "self"){
		$('#email_2').show();
	}else{
		$('#email_2').hide();
	}
	
	// 이메일 주소 selectbox 클릭 시
	$("#emailSel").on("click", function() {
		if($(this).val() == "self"){
			$('#email_2').show();
			$("#email_2").val("");
		} else if($(this).val() == "opt"){
			$('#email_2').hide();
			$('#email_2').val("");
		} else {
			$('#email_2').hide();
			$("#email_2").val($(this).val());
		}
	});
	
	$("#univId").on("change", function() {
		var selTxt = $("#univId option:selected").text();
		//alert(selTxt);
		$("#univNm").val(selTxt);
	});
	
	<c:if test="${org eq 'university'}">
		$("#univId").val($("#srcUnivId").val());
	</c:if>
	
	<c:if test="${org ne 'university'}">
		$("#univNm").on("click", function(){
			$("#univIdCheckBtn").trigger("click");
		});
	</c:if>
	
	/* 
	$("#depTel").on("onchange", function(){
		alert('abab~~');
		if($('#depTel').val != null || $('#depTel').val != ''){
			var depTelArr = $('#depTel').val().split("-");
			$('#depTel1').val(depTelArr[0]);
			$('#depTel2').val(depTelArr[1]);
			$('#depTel3').val(depTelArr[2]);
		}
	});
	 */

	/* 
	$("#univIdCheckBtn").on("click", function(){
		
		<c:if test="${formMode == 'UPDATE'}">
		if($("#oldUnivId").val() == $("#univId").val()){
			alert("동일한 값입니다. 변경 후 확인해 주세요.");
			return;
		}
		</c:if>
		
		var univId = $("#univId").val();
		
		$.ajax({
			url : '${ADMIN_PATH}/univHpMngr/idCheck/jsonResultGet.do'
			, type : 'get'
			, dataType: 'json'
			, data: {univId : univId}
		}).done(function(result){
			if(result > 0){
				alert('기 등록 데이터 입니다.');
				$('#univIdCheckResult').html("<p style='color:red;'>기 등록 데이터 입니다.</p>");
			}else{
				alert('등록가능한 데이터 입니다.');
				$('#univIdCheckResult').html("<p style='color:blue;'>등록가능한 데이터 입니다.</p>");		
			}
		}).fail(function(result){
			alert('값을 불러오지 못하였습니다.[fail]');	
		});

	});
	 */
	
	
	/* 2. 정규식 */
	// 숫자만 입력
	$("#tel1, #tel2") .on("keyup", function() {
    	$(this).val($(this).val().replace(/[^0-9]/g,""));
	});
	$("#tel3") .on("keyup", function() {
	   	$(this).val($(this).val().replace(/[^0-9~]/g,""));
	});
	
	// 숫자랑 , -
	/* 
	$("#fondDt").on("keyup", function() {
    	$(this).val($(this).val().replace(/[^0-9,\,-]/g,""));
	});
	 */
	
});

function fn_save(){
	
	// 대학
	if($("#univId option:selected").index() == 0){
		alert("캠퍼스타운명은 필수 선택사항 입니다.");
		$("#univId").focus();
		return;
	}
	
	// 연락처
	if($("#selOpt option:selected").index() != 0){
		if($("#tel2").val() == '' || $("#tel2").val() == null){
			alert("연락처를 마저 기입해주세요.");
			$("#tel2").focus();
			return;
		}
		
		if($("#tel3").val() == '' || $("#tel3").val() == null){
			alert("연락처를 마저 기입해주세요.");
			$("#tel3").focus();
			return;
		}
	}
	
	// 운영담당자 
	if($("#chargerNm").val() == '' || $("#chargerNm").val() == null){
		alert("운영담당자는 필수값 입니다.");
		$("#chargerNm").focus();
		return;
	}
	// 위치
	/* 
	if($("#zipcode").val() == '' || $("#zipcode").val() == null){
		alert("위치정보는 필수값 입니다.");
		$("#zipcode").focus();
		return;
	}
	 */
	if($("#address").val() == '' || $("#address").val() == null){
		alert("위치정보는 필수값 입니다.");
		$("#address").focus();
		return;
	}
	/* 
	if($("#addressDetail").val() == '' || $("#addressDetail").val() == null){
		alert("위치정보는 필수값 입니다.");
		$("#addressDetail").focus();
		return;
	}
	 */
	
	// 지역(구)
	if($("#sigunguCode option:selected").index() == 0){
		alert("지역(구)는 필수 선택사항 입니다.");
		$("#sigunguCode").focus();
		return;
	}
	
	<c:if test="${(formMode eq 'INSERT') and (org ne 'university')}">
		if($('#univIdCheckResult').text() == '' || $('#univIdCheckResult').text() == null){
			alert("중복확인 체크를 해주세요.");
			$("#univId").focus();
			return;
		}
	</c:if>
	
	if($('#univIdCheckResult').text() == "기 등록 데이터 입니다."){
		alert("기 등록 데이터 입니다. 다른 캠퍼스타운을 선택해주세요.");
		$("#univId").focus();
		return;
	}
	
	if(!confirm("계속 진행 하시겠습니까?")){
		return;
	}else{
		
		// 히든값 처리
		if($("#selOpt").val() != "000"){
			$("#tel1").val($("#selOpt").val());
		}
		$("#tel").val($("#tel1").val()+"-"+$("#tel2").val()+"-"+$("#tel3").val());
		
		//alert("1. "+$("#oldUnivId").val());
		//alert("1. "+$("#univId").val());
		
		// univId값 수정사항 없을 때 
		if($("#oldUnivId").val() == $("#univId").val()){
			$("#oldUnivId").val("");
		}
		//alert("2. "+$("#oldUnivId").val());
		//alert("2. "+$("#univId").val());
		//alert($("#univNm").val());
		// submit
		
		$("#email").val($("#email_1").val()+"@"+$("#email_2").val());
		
		$("#univHpMngrForm").submit();
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
					            	
					            	<!-- alert message -->
									<c:import url="/WEB-INF/jsp/egovframework/com/asapro/admin/include/message.jsp" charEncoding="UTF-8" />
									
					            	<!-- info -->
					            	<c:if test="${formMode == 'UPDATE'}">
										<div class="p-3 mb-3 bg-light text-info font-weight-bold">
											<div><i class="mdi mdi-information"></i> 캠퍼스타운 홈페이지 관리 - 상세화면 </div>
										</div>
									</c:if>
					            	<c:if test="${formMode == 'INSERT'}">
										<div class="p-3 mb-3 bg-light text-info font-weight-bold">
											<div><i class="mdi mdi-information"></i> 캠퍼스타운 홈페이지 관리 - 등록화면</div>
										</div>
									</c:if>
									
									
									<c:if test="${formMode == 'INSERT'}"><c:set var="actionUrl" value="${ADMIN_PATH}/univHpMngr/insert.do" /></c:if>
									<c:if test="${formMode == 'UPDATE'}"><c:set var="actionUrl" value="${ADMIN_PATH}/univHpMngr/update.do" /></c:if>
									
					            	 <form:form modelAttribute="univHpMngrForm" id="univHpMngrForm" cssClass="" action="${actionUrl}" method="post" enctype="multipart/form-data">
										 <%-- <form:hidden path="univNm" value="${univHpMngrForm.univId}"/> --%>
										 <input type="hidden" id="srcUnivId" value="${srcUnivId }"/>
										 <input type="hidden" id="depUse" value="${depUse}" />
										 
					            	 	 <!-- card 1. -->
						            	 <div class="card">
						            	 	<div class="card-header">
					            	 		■ 캠퍼스타운 기본정보
						            	 	</div>
						            	 	
						            	 	<div class="card-body">
							            	 	<div class="table-responsive-sm">
							            	 	
							            	 	<div id="univ" class="mfp-hide">
													<h5 class="modal-title mt-0">캠퍼스타운 목록</h5>
													<hr>
													<div id="univList">
													
													</div>
												</div>
												
												<script>
													jQuery(function($){
														//콘텐츠 검색 폼 및 목록을 가져온다.
														$('#univList').load('${ADMIN_PATH}/univHpMngr/searchUnivLayer.do');									
														
														// From an element with ID #popup
														$('#univIdCheckBtn').magnificPopup({
															
														  items: {
														      src: '#univ',
														      type: 'inline',
														  }
														, callbacks: {
														      close: function(){
														    	  <c:if test="${formMode == 'UPDATE'}">
																	if($("#oldUnivId").val() == $("#univId").val()){
																		alert("동일한 값입니다. 변경 후 확인해 주세요.");
																		return;
																	}
																	</c:if>
																	
																	if($("#depUse").val() == "false"){
																		alert("현재 미사용 중인 부서값 입니다.<br/>'\[조직관리] > 부서 목록'\에서 해당 값을 사용으로 활성화 해주세요.");
																		$("#univNm").val("");
																		$("#univId").val("");
																		return;
																	}	
																	var univId = $("#univId").val();
																	
																	$.ajax({
																		url : '${ADMIN_PATH}/univHpMngr/idCheck/jsonResultGet.do'
																		, type : 'get'
																		, dataType: 'json'
																		, data: {univId : univId}
																	}).done(function(result){
																		if(result > 0){
																			alert('기 등록 데이터 입니다.');
																			$('#univNm').val("");
																			$('#univIdCheckResult').html("<p style='color:red;'>기 등록 데이터 입니다.</p>");
																		}else{
																			alert('등록가능한 데이터 입니다.');
																			$('#univIdCheckResult').html("<p style='color:blue;'>등록가능한 데이터 입니다.</p>");		
																		}
																	}).fail(function(result){
																		alert('값을 불러오지 못하였습니다.[fail]');	
																	});
														       }
														  }
														, closeOnBgClick:false
														//, showCloseBtn:true 
														
														});
														
														
													});
												</script>
												
													<table class="table table-bordered univHpMngr_table">
														<colgroup>
														 	<col style="width: 15%;">
														 	<col style="width: 85%;">
														</colgroup>
														<tbody>
															<c:if test="${formMode eq 'UPDATE'}">
															<tr>
																<th scope="row" class="align-middle table-active text-center">
																	캠퍼스타운ID <span class="text-danger">*</span>
																</th>
																
																<td>
																	<!-- 1. 대학 : 레이어팝업으로 대학 목록에서 원하는 대학 선택해오기 -->
																	<div class="container">
																		<div class="row">
																			${univHpMngrForm.univId}
																		</div>
																	</div>
																</td>
															</tr>
															</c:if>
															<tr>
																<th scope="row" class="align-middle table-active text-center">
																	캠퍼스타운명 <span class="text-danger">*</span>
																</th>
																
																<td>
																	<!-- 1. 대학 : 레이어팝업으로 대학 목록에서 원하는 대학 선택해오기 -->
																	<div class="container">
																		<div class="row">
																			<c:if test="${org eq 'university'}">
																				<form:input path="univNm" cssClass="form-control col-sm-4" readonly="true"/> 
																			</c:if>
																			<c:if test="${org ne 'university'}">
																				<c:if test="${formMode eq 'UPDATE'}">
																					<form:input path="univNm" cssClass="form-control col-sm-4" disabled="true"/>
																					<a href="#n" id="univNmUpdCheckBtn" class="btn btn-secondary waves-effect m-l-5 ml-2 mr-2">수정</a>
																					<a href="#n" id="univNmUpdSaveBtn" class="btn btn-primary waves-effect waves-light ml-2 mr-2">저장</a>
																				</c:if>
																				<c:if test="${formMode eq 'INSERT'}">
																					<form:input path="univNm" cssClass="form-control col-sm-4" readonly="true"/>
																					<a href="#n" id="univIdCheckBtn" class="btn btn-primary waves-effect waves-light ml-2 mr-2">캠퍼스타운 검색</a>
																					<div id="univIdCheckResult"></div>
																				</c:if>
																			</c:if>
																			<form:errors path="univNm" element="div" cssClass="text-danger col-form-label mt-3 ml-3" />
																			
																			<form:hidden path="univId" />
																			<form:hidden path="oldUnivId" value="${univHpMngrForm.univId}" />
																			<%-- 
																			<c:if test="${org eq 'university'}">
																				<form:select path="univId" readonly="true" onFocus="this.initialSelect = this.selectedIndex;"
																							 onChange="this.selectedIndex = this.initialSelect;"
																							 cssClass="form-control col-sm-3" title="대학">
																					<option value="">전체</option>
																					<form:options items="${departmentList}" itemLabel="depName" itemValue="depId"/>
																				</form:select>
																			</c:if>
																			<c:if test="${org ne 'university'}">
																				<form:select path="univId" cssClass="form-control col-sm-3" title="대학">
																					<option value="">전체</option>
																					<form:options items="${departmentList}" itemLabel="depName" itemValue="depId"/>
																				</form:select>
																				<a href="#n" id="univIdCheckBtn" class="btn btn-primary waves-effect waves-light ml-2 mr-2">대학 검색</a>
																				<div id="univIdCheckResult"></div>
																			</c:if>
																			 --%>
																		</div>
																	</div>
																</td>
															</tr>
															<tr>
																<th scope="row" class="align-middle table-active text-center">
																	설립일
																</th>
																
																<td>
																	<div class="container">
																		<div class="row">
																			<form:input path="fondDt" cssClass="form-control col-sm-3 datepicker-ui" cssErrorClass="form-control col-sm-4 datepicker-ui is-invalid" autocomplete="off" readonly="true"/>
																			<form:errors path="fondDt" element="div" cssClass="text-danger col-form-label mt-3 ml-3" />
																		</div>
																	</div>
																</td>
															</tr>
															<tr>
																<th scope="row" class="align-middle table-active text-center">
																	연락처
																</th>
																
																<td>
																	<div class="container">
																		<div class="row">
																			<select id="selOpt" class="form-control col-sm-2 mr-2" title="휴대전화1">
																				<option value="">선택</option>
																				<c:forEach items="${tel1CodeList}" var="item" varStatus="status">
																					<option value="${item.codeId }" >${item.codeName }</option>
																				</c:forEach>
																				<option value="000">직접선택</option>
																			</select>
																			<form:input path="tel1" class="form-control col-sm-2" maxlength="3"/>
																			&nbsp; - &nbsp; <form:input path="tel2" cssClass="form-control col-sm-2" cssErrorClass="form-control col-sm-2 is-invalid" maxlength="4" />
																			&nbsp; - &nbsp; <form:input path="tel3" cssClass="form-control col-sm-2" cssErrorClass="form-control col-sm-2 is-invalid" maxlength="8" />
																			<form:errors path="tel1" element="div" cssClass="text-danger col-form-label ml-2" />
																			<form:hidden path="tel" />
																		</div>
																	</div>
																</td>
															</tr>
															<tr>
																<th scope="row" class="align-middle table-active text-center">
																	운영담당자 <span class="text-danger">*</span>
																</th>
																
																<td>
																	<div class="container">
																		<div class="row">
																			<form:input path="chargerNm" cssClass="form-control col-sm-4" cssErrorClass="col-sm-4 is-invalid" maxlength="70" />
																			<form:errors path="chargerNm" element="div" cssClass="text-danger col-form-label ml-4" />
																		</div>
																	</div>
																</td>
															</tr>
															<tr>
																<th scope="row" class="align-middle table-active text-center">
																	email
																</th>
																
																<td>
																	<div class="container">
																		<div class="row">
																			<form:input path="email1" id="email_1" cssClass="form-control col-sm-2 mr-2" cssErrorClass="form-control col-sm-2 is-invalid" maxlength="100"/> @&nbsp;
																			<form:input path="email2" id="email_2" cssClass="form-control col-sm-2 ml-2 mr-2" cssErrorClass="form-control col-sm-2 is-invalid" maxlength="100" />
																			<select id="emailSel" class="form-control col-sm-2" title="도메인 선택">
																				<option value="opt">선택</option>
																				<c:forEach items="${emailDomainCodeList}" var="item" varStatus="status">
																					<option>${item.codeName}</option>
																				</c:forEach>
																				<option value="self">직접입력</option>
																			</select>
																			<form:hidden path="email" />
																		</div>
																	</div>
																</td>
															</tr>
															<tr>
																<th scope="row" class="align-middle table-active text-center">
																	위치 <span class="text-danger">*</span>
																</th>
																
																<td>
																	<div class="container">
																		<%-- 
																		<div class="row">
																				<form:input path="zipcode" class="zipcode" cssClass="form-control col-sm-2" readonly="true" />
																				<a href="#n" id="zipSearchBtn" class="btn btn-primary waves-effect waves-light ml-2">우편번호 검색</a>
																		</div>
																		 --%>
																		<div class="row">
																			<!-- onchange="inputChk(this)" -->
																			<form:textarea path="address" cssClass="form-control col-sm-10 mt-2" cssErrorClass="form-control col-sm-5 is-invalid" maxlength="1500" placeholder="위치" rows="5" cols="200"/>
																			<form:errors path="address" element="div" cssClass="text-danger col-form-label ml-5" readonly="true"/>
																		</div>
																	</div>
																</td>
																<%-- <c:import url="/WEB-INF/jsp/egovframework/com/asapro/admin/include/zipSearch.jsp" charEncoding="UTF-8" /> --%>
															</tr>
															<tr>
																<th scope="row" class="align-middle table-active text-center">
																	지역(구) <span class="text-danger">*</span>
																</th>
																
																<td>
																	<div class="container">
																		<div class="row">
																			<form:select path="areaGuCd" id="sigunguCode" cssClass="form-control col-sm-2" title="지역(구)">
																				<form:option value="">선택</form:option>
																				<form:options items="${sigunguCodeList}" itemLabel="codeName" itemValue="codeId"/>
																			</form:select>
																		</div>
																		<%-- 
																		<div class="row">
																			<form:select path="areaGuCd" id="sigunguCode" readonly="true" onFocus="this.initialSelect = this.selectedIndex;"
																						 onChange="this.selectedIndex = this.initialSelect;"
																						 cssClass="form-control col-sm-3" title="지역(구)">
																				<form:option value="">선택</form:option>
																				<form:options items="${sigunguCodeList}" itemLabel="codeName" itemValue="codeId"/>
																			</form:select>
																		</div>
																		 --%>
																	</div>
																</td>
															</tr>
															<tr>
																<th scope="row" class="align-middle table-active text-center">
																	대표 이미지
																</th>
																
																<td>
																	<div class="container">
																		<div class="row">
																			<c:if test="${not empty univHpMngrForm.file1Info && univHpMngrForm.file1Info.fileId > 0}">
																				<img class="img-thumbnail" style="max-width: 200px; max-height: 200px;" src="${univHpMngrForm.file1Info.fileServletUrl}" onerror="this.src='/assets/images/noImage300x300.jpg'" />	
																				<br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="${APP_PATH}/file/download/uu/${univHpMngrForm.file1Info.fileUUID}" title="다운로드">${univHpMngrForm.file1Info.fileOriginalName }</a>
																			</c:if>
																		</div>
																	</div>	
																	<div class="container">
																		<div class="row">
																			<input type="file" id="file1" name="file1" class="col-form-label"/>
																			<span class="bg-light font-weight-bold text-info">- jpg, jpeg, gif, png, bmp 파일만 업로드할 수 있습니다.</span> 
																		</div>
																	</div>	
																	<div class="container">
																		<div class="row">
																			<span class="align-middle text-center mr-2">대체텍스트 : </span> 
																			<form:input path="file1AltText" cssClass="form-control col-sm-9" cssErrorClass="col-sm-9 is-invalid" maxlength="500" />
																			<form:errors path="file1AltText" element="div" cssClass="text-danger col-form-label ml-9" />
																		</div>
																	</div>		
																</td>
															</tr>
															<tr>
																<th scope="row" class="align-middle table-active text-center">
																	대학 CI
																</th>
																
																<td>
																	<div class="container">
																		<div class="row">
																			<c:if test="${not empty univHpMngrForm.file2Info && univHpMngrForm.file2Info.fileId > 0}">
																				<img class="img-thumbnail" style="max-width: 200px; max-height: 200px;" src="${univHpMngrForm.file2Info.fileServletUrl}" onerror="this.src='/assets/images/noImage300x300.jpg'" />	
																				<br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="${APP_PATH}/file/download/uu/${univHpMngrForm.file2Info.fileUUID}" title="다운로드">${univHpMngrForm.file2Info.fileOriginalName }</a>
																			</c:if>
																		</div>
																	</div>
																	<div class="container">
																		<div class="row">
																			&nbsp;<input type="file" id="file2" name="file2" />
																			<span class="bg-light font-weight-bold text-info">- jpg, jpeg, gif, png, bmp 파일만 업로드할 수 있습니다.</span>
																		</div>
																	</div>
																</td>
															</tr>
															<tr>
																<th scope="row" class="align-middle table-active text-center">
																	한마디
																</th>
																
																<td>
																	<div class="container">
																		<div class="row">
																			<form:textarea path="ment" cssClass="form-control col-sm-8" maxlength="150"/>
																			<form:errors path="ment" element="div" cssClass="text-danger" />
																		</div>	
																	</div>
																</td>
															</tr>
															<tr>
																<th scope="row" class="align-middle table-active text-center">
																	Vision
																</th>
																
																<td>
																	<div class="container">
																		<div class="row">
																			<form:textarea path="vsn" cssClass="form-control col-sm-8" rows="3" placeholder="Vision" />
																			<form:errors path="vsn" element="div" cssClass="text-danger" />
																		</div>	
																	</div>
																</td>
															</tr>
															<tr>
																<th scope="row" class="align-middle table-active text-center">
																	소개
																</th>
																
																<td>
																	<div class="container">
																		<div class="row">
																			<form:textarea path="intrcn" cssClass="form-control col-sm-8" rows="3" placeholder="소개" />
																			<form:errors path="intrcn" element="div" cssClass="text-danger" />
																		</div>	
																	</div>
																</td>
															</tr>
															<tr>
																<th scope="row" class="align-middle table-active text-center">
																	홍보 메시지
																</th>
																
																<td>
																	<div class="container">
																		<div class="row">
																			<form:textarea path="prMssage" cssClass="form-control col-sm-8" rows="3" placeholder="홍보 메시지" />
																			<form:errors path="prMssage" element="div" cssClass="text-danger" />
																		</div>
																	</div>	
																</td>
															</tr>
															<tr>
																<th scope="row" class="align-middle table-active text-center">
																	지원 프로그램
																</th>
																
																<td>
																	<div class="container">
																		<div class="row">
																			<form:textarea path="sportProgrm" cssClass="form-control col-sm-8" rows="3" placeholder="지원 프로그램" />
																			<form:errors path="sportProgrm" element="div" cssClass="text-danger" />
																		</div>
																	</div>	
																</td>
															</tr>
															<tr>
																<th scope="row" class="align-middle table-active text-center">
																	첨부파일
																</th>
																
																<td>
																	<div class="container">
																		<div class="row">
																			<c:if test="${not empty univHpMngrForm.file3Info && univHpMngrForm.file3Info.fileId > 0}">
																				<%-- <img class="img-thumbnail" style="max-width: 200px; max-height: 200px;" src="${univHpMngrForm.file3Info.fileServletUrl}" onerror="this.src='/assets/images/noImage300x300.jpg'" /> --%>	
																				<a href="${APP_PATH}/file/download/uu/${univHpMngrForm.file3Info.fileUUID}" title="다운로드">${univHpMngrForm.file3Info.fileOriginalName }</a>
																				&nbsp;&nbsp;&nbsp;<input type="file" id="file3" name="file3" />
																			</c:if>
																		</div>
																	</div>
																	
																	<c:if test="${ empty univHpMngrForm.file3Info && empty univHpMngrForm.file3Info.fileId }">
																	<div class="container">
																		<div class="row">
																			&nbsp;<input type="file" id="file3" name="file3" />
																			<span class="bg-light font-weight-bold text-info">- jpg, jpeg, gif, png, bmp 파일만 업로드할 수 있습니다.</span>
																		</div>
																	</div>
																	</c:if>
																	 
																</td>
															</tr>
														</tbody>
													</table>
							            	 	</div>
						            		</div>
							             </div>	
							             <!-- End card 1. -->
							             
							             
							             <!-- card 2. --> 
						            	 <div class="card">
						            	 	<div class="card-header">
						            	 		■ SNS
						            	 	</div>
						            	 	<div class="card-body">
							            	 	<div class="table-responsive-sm">
													<table class="table table-bordered">
														<colgroup>
														 	<col style="width: 15%;">
														 	<col style="width: 85%;">
														</colgroup>
														<tbody>
															<tr>
																<th scope="row" class="align-middle table-active text-center">
																	페이스북
																</th>
																
																<td>
																	<div class="container">
																		<div class="row">
																			<form:input path="snsFace" cssClass="form-control col-sm-11" cssErrorClass="col-sm-11 is-invalid" maxlength="500" placeholder="( ex) https://ko-kr.facebook.com/seoulnational/ )"/>
																			<form:errors path="snsFace" element="div" cssClass="text-danger col-form-label ml-11" />
																		</div>
																	</div>
																</td>
															</tr>
															<tr>
																<th scope="row" class="align-middle table-active text-center">
																	트위터
																</th>
																
																<td>
																	<div class="container">
																		<div class="row">
																			<form:input path="snsTwit" cssClass="form-control col-sm-11" cssErrorClass="col-sm-11 is-invalid" maxlength="500" placeholder="( ex) https://twitter.com/snu_engineering )"/>
																			<form:errors path="snsTwit" element="div" cssClass="text-danger col-form-label ml-11" />
																		</div>
																	</div>
																	
																</td>
															</tr>
															<tr>
																<th scope="row" class="align-middle table-active text-center">
																	블로그
																</th>
																
																<td>
																	<div class="container">
																		<div class="row">
																			<form:input path="snsBlog" cssClass="form-control col-sm-11" cssErrorClass="col-sm-11 is-invalid" maxlength="500" placeholder="( ex) http://convergence.snu.ac.kr/main/archives/category/%EB%B8%94%EB%A1%9C%EA%B7%B8 )"/>
																			<form:errors path="snsBlog" element="div" cssClass="text-danger col-form-label ml-11" />
																		</div>
																	</div>
																</td>
															</tr>
															
															<tr>
																<th scope="row" class="align-middle table-active text-center">
																	홈페이지
																</th>
																
																<td>
																	<div class="container">
																		<div class="row">
																			<form:input path="snsHp" cssClass="form-control col-sm-11" cssErrorClass="col-sm-11 is-invalid" maxlength="500" placeholder="( ex) http://www.snu.ac.kr/ )"/>
																			<form:errors path="snsHp" element="div" cssClass="text-danger col-form-label ml-11" />
																		</div>
																	</div>
																</td>
															</tr>
															
															<tr>
																<th scope="row" class="align-middle table-active text-center">
																	인스타그램
																</th>
																
																<td>
																	<div class="container">
																		<div class="row">
																			<form:input path="snsInsta" cssClass="form-control col-sm-11" cssErrorClass="col-sm-11 is-invalid" maxlength="500" placeholder="( ex) https://www.instagram.com/snu.official/?igshid=1ja3dei440cnw )"/>
																			<form:errors path="snsInsta" element="div" cssClass="text-danger col-form-label ml-11" />
																		</div>
																	</div>
																</td>
															</tr>
															
															<tr>
																<th scope="row" class="align-middle table-active text-center">
																	카카오채널
																</th>
																
																<td>
																	<div class="container">
																		<div class="row">
																			<form:input path="snsKakao" cssClass="form-control col-sm-11" cssErrorClass="col-sm-11 is-invalid" maxlength="500" placeholder="( ex) https://open.kakao.com/o/sRpnx0C )"/>
																			<form:errors path="snsKakao" element="div" cssClass="text-danger col-form-label ml-11" />
																		</div>
																	</div>
																</td>
															</tr>
															
														</tbody>
													</table>
												</div>
											</div>	
						            	 </div>
						            	 <!-- End card 2. -->
						            	 
						            	 <!-- card 3. -->
						            	 <div class="card">
						            	 	<div class="card-header">
						            			■ 기타정보
						            	 	</div>
						            	 	<div class="card-body">
							            	 	<div class="table-responsive-sm">
													<table class="table table-bordered">
														<colgroup>
														 	<col style="width: 15%;">
														 	<col style="width: 85%;">
														</colgroup>
														<tbody>
															
															<tr>
																<th scope="row" class="align-middle table-active text-center">	
																	노출여부
																</th>
																
																<td>
																	<div class="container">
																		<div class="row">
																		<div class="custom-control custom-radio custom-control-inline">
																			<form:radiobutton path="expsrYn" id="expsrYn1" class="custom-control-input" value="Y"/>
																			<label for="expsrYn1" class="custom-control-label"> Y</label>
																		</div>
																		<div class="custom-control custom-radio custom-control-inline">
																			<form:radiobutton path="expsrYn" id="expsrYn0" class="custom-control-input" value="N"/>
																			<label for="expsrYn0" class="custom-control-label"> N</label>
																		</div>
																		<form:errors path="expsrYn" element="div" cssClass="text-danger col-form-label ml-4" />
																		</div>
																	</div>	
																</td>
															</tr>
															
															<c:if test="${formMode eq 'UPDATE'}">
																<tr>
																	<th scope="row" class="align-middle table-active text-center">
																		최근 수정일
																	</th>
																	
																	<td>
																		<fmt:parseDate value="${univHpMngrForm.updDt}" var="parseDate1" pattern="yyyyMMddHHmmss" scope="page"/>
																		<fmt:formatDate value="${parseDate1}" pattern="yyyy-MM-dd HH:mm:ss"/>
																	</td>
																</tr>
															</c:if>
															
														</tbody>
													</table>
												</div>
											</div>
						            	 </div>
						            	 <!-- End card 3. -->
						            	 
						            	 <div class="card">
											<div class="card-body">
												<div class="table-responsive-sm">
													<button type="button" onclick="fn_save()" class="btn btn-primary waves-effect waves-light">저장</button>
													<a href="${ADMIN_PATH}/univHpMngr/list.do?" class="btn btn-secondary waves-effect m-l-5">목록</a>
													<%-- <a href="${ADMIN_PATH}/youthSupport/list.do?${youthSupportSearch.queryString }" class="btn btn-secondary waves-effect m-l-5">목록</a> --%>
												</div>
											</div>
										</div>
					            	 </form:form>
					            </div>
				            </div>
			            </div>
		            </div> <!-- End 입력폼 -->
		    		<!-- ============================= End 메뉴별 컨텐츠 ============================ -->
		            
		            
	    		</div><!-- End container -->
	    	</div> <!-- End Page content Wrapper -->
	    
	    </div> <!-- End content -->
	    
    </div> 
    <!-- End Right content here -->
    
<script>
CKEDITOR.replace( 'vsn',{
	filebrowserImageUploadUrl : GLOBAL.ADMIN_PATH + '/file/editor/upload.do?module=univHpMngr&moduleId=&moduleSub=&moduleSubId='
	, height : 200
} );
CKEDITOR.replace( 'intrcn',{
	filebrowserImageUploadUrl : GLOBAL.ADMIN_PATH + '/file/editor/upload.do?module=univHpMngr&moduleId=&moduleSub=&moduleSubId='
	, height : 200
} );
CKEDITOR.replace( 'prMssage',{
	filebrowserImageUploadUrl : GLOBAL.ADMIN_PATH + '/file/editor/upload.do?module=univHpMngr&moduleId=&moduleSub=&moduleSubId='
	, height : 200
} );
CKEDITOR.replace( 'sportProgrm',{
	filebrowserImageUploadUrl : GLOBAL.ADMIN_PATH + '/file/editor/upload.do?module=univHpMngr&moduleId=&moduleSub=&moduleSubId='
	, height : 200
} );
</script> 

 
<c:import url="/WEB-INF/jsp/egovframework/com/asapro/admin/layout/footer.jsp" charEncoding="UTF-8" />