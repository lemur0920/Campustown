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
	
	// 업종> '사업분야' 가이드
	/* 
	$('#induty').on('click', function(){
		$('#bsnsRealmList').html("");
		//alert("val: "+$(this).val());
		var rtn = fn_getBsnsRealm($(this).val());
		//alert("rtn: " + rtn);
		$('#bsnsRealmList').html("예> "+rtn);
	});
	 */
	//첨부파일
	//------------------------------------------
	//------------------------- 첨부파일 등록 Start
	//-------------------------------------------
	/* 
	<c:if test="${startHpMngrForm.stUploadFileNum > 0}">
	$('.selectFile').multiSelector({
		list_target: 'fileList'
		,fileItemCssSelector: 'file_add'
		,list_delete_fileid: 'deleteFileList'
		,max: ${startHpMngrForm.stUploadFileNum}
	});
	</c:if>
	 */
	//------------------------- 첨부파일 등록 End

	//$('#tel1').hide();
	$('#compIdCheckBtn').hide();
	
	<c:if test="${org eq 'university'}">
		if($('#univDataYn').val() == 'N'){
			alert("캠퍼스타운 기본정보 등록내용이 없습니다./n캠퍼스타운 관리에서 먼저 등록해주세요.");
			window.location.href='${ADMIN_PATH}/univHpMngr/list.do';
		}
	</c:if>
	
	<c:if test="${org ne 'university'}">
		$("#univNm").on("click", function(){
			//alert("click~!!");
			$("#univIdCheckBtn").trigger("click");
		});
	</c:if>
	 
	/* 
	<c:if test="${org ne 'startup'}">
		$("#compNm").on("click", function(){
			$("#compIdCheckBtn").trigger("click");
		});
	</c:if>
	 */
	/*  
	// 회사 전화번호 컬럼 추가, 관련 이벤트 처리
	$("#selOpt").find("option").each(function(){
		if($('#tel1').val() != '' && $('#tel1').val() != null){
		    if($(this).val() == $('#tel1').val()){
		    	$(this).val($('#tel1').val()).prop("selected", true);
		    	//$('#tel1').hide();
		    	return;
		    }else{
		    	//$(this).val("self").prop("selected", true);
		    	$("#selOpt option:last").attr("selected", true);
		    	$('#tel1').show();
		    	return;
		    }
		}else{
			$("#selOpt option:first").attr("selected", true);
			//$('#tel1').hide();
			return;
		}
	});
	
	// 초기값 셋팅 후 show, hide 처리
	
	if($('#selOpt').val() == "self"){
		$('#tel1').show();
	}else{
		$('#tel1').hide();
	}
	
	// 전화번호 selectbox 클릭 시
	
	$("#selOpt").on("click", function() {
		if($(this).val() == "self"){
			$('#tel1').show();
			$('#tel1').val("");
		} else if($(this).val() == "opt"){
			$('#tel1').hide();
			$('#tel1').val("");
		} else {
			$('#tel1').hide();
			$('#tel1').val($(this).val());
		}
	});
	 */
	
	
	
	/* 2. 정규식 */
	// 숫자만 입력
	$("#tel1, #tel2, input:text[id^=sesnYear]")
	.on("keyup", function() {
    	$(this).val($(this).val().replace(/[^0-9]/g,""));
	});
	
	// 선행 0 제거 
	$("input:text[id^=saleAmt], input:text[id^=invtAmt], input:text[id^=emplyCnt], input:text[id^=intellProp]")
	.on("keyup", function() {
    	$(this).val($(this).val().replace(/(^0+)/g,""));
	});
	 
	 
	
	$("#tel3") .on("keyup", function() {
    	$(this).val($(this).val().replace(/[^0-9~]/g,""));
	});
	
	
	// 창업활동지수 등록
	$('.idxInsertBtn').on('click', function(){
		//alert("click~!!!");
		//alert("compId: "+$('#compId').val());
		
		if($('#sesnYear').val() == '' ||  $('#sesnYear').val() == null){
			alert("년도는 필수 값 입니다.");
			$('#sesnYear').focus();
			return;
		}
		
		if($("#cardiNum option:selected").index() == 0){
			
			alert("기수는 필수 값 입니다.");
			$("#cardiNum").focus();
			return;
		}
		
		
		if(!confirm("창업활동지수 정보를 등록 하시겠습니까?")){
			return;
		} else {
			
			var queryString = $("#activeIdxForm").serialize();
			
			$.ajax({
				url : '${ADMIN_PATH}/startHpMngr/compInfo/activeIdx/jsonCR.do'
					, type : 'post'
					, dataType: 'json'
					, data:queryString
			}).done(function(result){
				//alert(result);
				if(result != null){
					alert('등록이 완료됬습니다.');
					var str = '';
					str += '<tr>';
					str += '<th scope="row" class="align-middle table-active text-center" id="div'+result.indexId+'"></th>';
					str += '<td><input type="text" name="saleAmt" id="saleAmt'+result.indexId+'" placeholder="매출액 입력(만원)" class="form-control col-sm-11" maxlength="10" value="'+result.saleAmt+'" /></td>';
					str += '<td><input type="text" name="invtAmt" id="invtAmt'+result.indexId+'" placeholder="투자액 입력(만원)" class="form-control col-sm-11" maxlength="10" value="'+result.invtAmt+'" /></td>';
					str += '<td><input type="text" name="emplyCnt" id="emplyCnt'+result.indexId+'" placeholder="고용인수" class="form-control col-sm-11" maxlength="10" value="'+result.emplyCnt+'" /></td>';
					str += '<td><input type="text" name="intellProp" id="intellProp'+result.indexId+'" placeholder="지적재산 입력(만원)" class="form-control col-sm-11" maxlength="10" value="'+result.intellProp+'"/></td>';
					str += '<td><button type="button" class="btn btn-sm ml-2 btn-primary waves-effect waves-light idxUpdateBtn" data-indexid="'+result.indexId+'" onclick="fnUpdate(this);">수정</button>';
					str += '<button type="button" class="btn btn-sm ml-2 btn-primary waves-effect waves-light idxDeleteBtn" data-indexid="'+result.indexId+'" onclick="fnDelete(this);">삭제</button>';
					str += '</td></tr>';
					
					$('#idx_tbody').append(str);
					
					//$('#year').val("");
					if(result.cardiNum == 1){
						$('#div'+result.indexId).html(result.sesnYear+" 상반기");
					} else {
						$('#div'+result.indexId).html(result.sesnYear+" 하반기");
					}
					
					// undefined, 빈 값 처리
					/* 
					if(trim($('#saleAmt'+result.indexId).val())){
						$('#saleAmt'+result.indexId).val("");
					}
					if(trim($('#invtAmt'+result.indexId).val())){
						$('#invtAmt'+result.indexId).val("");
					}
					if(trim($('#emplyCnt'+result.indexId).val())){
						$('#emplyCnt'+result.indexId).val("");
					}
					if(trim($('#intellProp'+result.indexId).val())){
						$('#intellProp'+result.indexId).val("");
					}
					 */
					
					// 등록 필드 값 비워주기
					$('#cardiNum').prop("selectedIndex", 0);
					
					$('#saleAmt').val("");
					$('#invtAmt').val("");
					$('#emplyCnt').val("");
					$('#intellProp').val("");
					
				}else{	
					alert('값을 불러오지 못하였습니다.[fail]');
				}
			}).fail(function(result){
				//alert("null이다!!!!");
				alert("숫자를 입력해주세요.");
			});
			
		}
		
	});
	
	
	// 창업활동지수 수정
	/*
	$('.idxUpdateBtn').on('click', function(){
		// 동적으로 생성한 행은 처리가 안되서 밖으로 뺌.
		
	});
	*/
	
	// 창업활동지수 삭제
	/*
	$('.idxDeleteBtn').on('click', function(){
		// 동적으로 생성한 행은 처리가 안되서 밖으로 뺌.
	});
	*/
	
	
});


//창업활동지수 수정
function fnUpdate(th){
	
	//alert('idx: '+ idx);
	//alert(th.getAttribute("data-indexid"));
	//alert(th.data('indexid'));
	
	//var indexId = $(this).data('indexid');
	var compId = $('#compId2').val();
	var indexId = th.getAttribute("data-indexid");
	var saleAmt = $('#saleAmt'+indexId).val();
	var invtAmt = $('#invtAmt'+indexId).val();
	var emplyCnt = $('#emplyCnt'+indexId).val();
	var intellProp = $('#intellProp'+indexId).val();
	
	//alert('indexId : '+ indexId);
	//alert('saleAmt : '+ saleAmt);
	//alert('invtAmt : '+ invtAmt);
	//alert('emplyCnt : '+ emplyCnt);
	//alert('intellProp : '+ intellProp);
	
	if(!confirm("해당사항을 수정하시겠습니까?")){
		return;
	}else{
		$.ajax({
			url : '${ADMIN_PATH}/startHpMngr/compInfo/activeIdx/jsonU.do'
				, type : 'get'
				, dataType: 'json'
				, data:{
					compId  : compId,
					indexId : indexId, 
					saleAmt : saleAmt,
					invtAmt : invtAmt,
					emplyCnt : emplyCnt,
					intellProp : intellProp
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

//창업활동지수 삭제
function fnDelete(th){
	
	//alert('idx: '+ idx);
	//alert(th.getAttribute("data-indexid"));
	//alert(th.data('indexid'));
	
	//var indexId = $(this).data('indexid');
	var indexId = th.getAttribute("data-indexid");
	//var curr = $(this);
	var tr = $(th).parent().parent();
	
	//alert('indexId: ' + indexId);
	
	if(!confirm("해당사항을 삭제하시겠습니까?")){
		return;
	}else{
		
		$.ajax({
			url : '${ADMIN_PATH}/startHpMngr/compInfo/activeIdx/jsonD.do'
				, type : 'get'
				, dataType: 'json'
				, data:{
					indexId : indexId
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



function fn_save(){
	
	// 대학
	if($("#univId option:selected").index() == 0){
		alert("캠퍼스타운명은 필수 선택사항 입니다.");
		$("#univId").focus();
		return;
	}
	
	// 지역(구)
	if($("#areaGuCd option:selected").index() == 0){
		alert("지역(구)는 필수 선택사항 입니다.");
		$("#areaGuCd").focus();
		return;
	}
	
	
	// 창업팀
	if($("#compId option:selected").index() == 0){
		alert("캠퍼스타운명은 필수 선택사항 입니다.");
		$("#compId").focus();
		return;
	}
	
	// 위치
	/*
	if($("#address").val() == '' || $("#address").val() == null){
		alert("위치정보는 필수값 입니다.");
		$("#address").focus();
		return;
	}
	 	
	if($("#zipcode").val() == '' || $("#zipcode").val() == null){
		alert("위치정보는 필수값 입니다.");
		$("#zipcode").focus();
		return;
	}
	
	if($("#addressDetail").val() == '' || $("#addressDetail").val() == null){
		alert("위치정보는 필수값 입니다.");
		$("#addressDetail").focus();
		return;
	}
	 */
	if(!confirm("계속 진행 하시겠습니까?")){
		return;
	}else{
		// 히든값 처리
		if($("#tel1").val() != 'opt'){
			$("#compTel").val($("#tel1").val()+"-"+$("#tel2").val()+"-"+$("#tel3").val());
		}
		$("#startHpMngrForm").submit();
	}
}

function trim(ud){
	//alert("오오오니???")
	var rtn = false;
	if(ud == 'undefined'){
		rtn = true;
		
	}
	return rtn;
}

// 
function fn_getBsnsRealm(cd){
	
	var jsonObj = 
		{	
		"bsnsRealms":
			[
		        {"cd":"001", "vall": "ICT, 3D프린팅"}, 
		        {"cd":"002", "vall": "IoT"}, 
		        {"cd":"003", "vall": "IT, 서비스, 인테리어, IT 서비스업, IT 제조업, IT(앱), IT 지식서비스, 영상업, IT/소프트웨어, S/W 개발, SW/App, SW개발, 소프트웨어 개발, 소프트웨어 제조, 응용 소프트웨어 개발 및 공급업, 전문ㆍ과학 및 기술서비스업, 정보서비스업, 정보서비스업 외, 정보통신업, 컴퓨터 프로그래밍 서비스업, 코딩교육/레고, 게임 "},
		        {"cd":"004", "vall": "공예"}, 
		        {"cd":"005", "vall": "관광업"}, 
		        {"cd":"006", "vall": "교육, 교육 서비스업, 교육개발, 교육 서비스업/소매업/전문, 과학기술 서비스업"}, 
		        {"cd":"007", "vall": "기획, 공연기획, 연극기획, 컨텐츠"}, 
		        {"cd":"008", "vall": "소매업, 도소매업, 유통시스템"}, 
		        {"cd":"009", "vall": "디자인, 디지털 앱, 시각 디자인업/전자상거래"}, 
		        {"cd":"010", "vall": "산업용 로봇 제조업"}, 
		        {"cd":"011", "vall": "APP, 모바일 어플리케이션, 온라인 모바일 소프트웨어 개발업, 전자상거래 중개업"}, 
		        {"cd":"012", "vall": "문화예술, 음반제작"}, 
		        {"cd":"013", "vall": "반려동물"}, 
		        {"cd":"014", "vall": "부동산업"}, 
		        {"cd":"015", "vall": "뷰티"}, 
		        {"cd":"016", "vall": "서비스, 서비스 외, 서비스(게임소프트웨어 제작 및 판매), 소도매, 서비스업, 서비스업(큐레이션), 지식서비스"}, 
		        {"cd":"017", "vall": "모임, 소셜벤쳐, 지역사회 공유 플랫폼"}, 
		        {"cd":"018", "vall": "스마트 팜"}, 
		        {"cd":"019", "vall": "건강식품, 식품(비건 조미료), 식품/유통, 식품제조가공업"}, 
		        {"cd":"021", "vall": "연구"}, 
		        {"cd":"022", "vall": "예비창업자, 사업자미등록"}, 
		        {"cd":"023", "vall": "요식업, 휴게음식"}, 
		        {"cd":"024", "vall": "인테리어 디자인업, 제품 디자인업, 시각 디자인업, 기타 전문 디자인업, 인테리어 소품 제작"}, 
		        {"cd":"025", "vall": "전자상거래, 전자상거래업"}, 
		        {"cd":"026", "vall": "단미사료제조업, 통신판매업, 수산물 도매및 소매업, 제작 및 판매, 제조, 제조업, 제조업 외, 테라리움 제품 개발 및 제작"}, 
		        {"cd":"027", "vall": "출판, 출판 및 서점, 출판ㆍ영상 외, 출판업, 사회/복지/문화/예술/출판"}, 
		        {"cd":"028", "vall": "Fashion Brand : 필환경 의류 브랜드, 캐쥬얼 패션 브랜드, 패션"}, 
		        {"cd":"029", "vall": "핀테크"}, 
		        {"cd":"030", "vall": "헬스케어, 헬스케어 제품 개발"} 
	        ]
	};
	
	//alert(jsonObj.bsnsRealms.cd[cd].vall);
	//console.log(jsonObj);
	
	var rtn = "";
	$(jsonObj.bsnsRealms).each(function(index, bsnsRealm){
		//console.log(index + " ::: " , bsnsRealm.vall);
		if(bsnsRealm.cd == cd){
			//alert(bsnsRealm.vall);
			rtn = bsnsRealm.vall;
			return false;
		}
	});

	return rtn;
	//obj = JSON.parse(jsonStr);
	 
	//jsonStr = JSON.stringify(jsonObj);
	//alert("cd: "+ cd);
	//alert(jsonStr["cd"][cd].vall);
	
	//alert(jsonObj.cd[cd].vall);
	 
	//alert(obj[cd].vall);
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
											<div><i class="mdi mdi-information"></i> 창업팀 홈페이지 관리 - 회사정보 상세 화면</div>
										</div>
									</c:if>
					            	<c:if test="${formMode == 'INSERT'}">
										<div class="p-3 mb-3 bg-light text-info font-weight-bold">
											<div><i class="mdi mdi-information"></i> 창업팀 홈페이지 관리 - 회사정보 등록 화면</div>
										</div>
									</c:if>
									
									<c:if test="${formMode == 'INSERT'}"><c:set var="actionUrl" value="${ADMIN_PATH}/startHpMngr/compInfo/insert.do" /></c:if>
									<c:if test="${formMode == 'UPDATE'}"><c:set var="actionUrl" value="${ADMIN_PATH}/startHpMngr/compInfo/update.do" /></c:if>
									
					            	 <form:form modelAttribute="startHpMngrForm" id="startHpMngrForm" cssClass="" action="${actionUrl}" method="post" enctype="multipart/form-data">
					            	 	 <!-- <input type="hidden" id="tel"/> -->
					            	 	 <input type="hidden" id="univDataYn" value="${univDataYn}" />
					            	 	 
					            	 	 <input type="hidden" id="depUse" value="${depUse}" />
					            	 	 <input type="hidden" id="depUse2" value="${depUse2}" />
					            	 	 
					            	 	 
					            	 	 <!-- card 1. -->
						            	 <div class="card">
						            	 	<div class="card-header">
						            			■ 소속 캠퍼스타운정보
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
													
													
													<c:if test="${formMode eq 'INSERT'}">
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
																		alert("현재 미사용 중인 부서값 입니다.\n'\[조직관리] > 부서 목록'\에서 해당 값을 사용으로 활성화 해주세요.");
																		$("#univNm").val("");
																		$("#univId").val("");
																		return;
																	}																
																	var univId = $("#univId").val();
																	
																	$.ajax({
																		url : '${ADMIN_PATH}/univHpMngr/idCheck/jsonResultGet.do'
																		, type : 'get'
																		, dataType: 'json'
																		, data: {
																		   univId : univId
																		}
																	}).done(function(result){
																		if(result > 0){
																			alert('등록가능한 데이터 입니다.');
																			$('#univIdCheckResult').html("<p style='color:blue;'>등록가능한 데이터 입니다.</p>");		
																		}else{
																			alert('미등록 데이터 입니다. \n캠퍼스타운 관리에서 캠퍼스타운 정보를 먼저 등록해주세요.');
																			$('#univIdCheckResult').html("<p style='color:red;'>미등록 데이터 입니다. 캠퍼스타운 관리에서 캠퍼스타운 정보를 먼저 등록해주세요.</p>");
																			$("#univNm").val("");
																			$("#univId").val("");
																		}
																	}).fail(function(result){
																		alert('값을 불러오지 못하였습니다.[fail]');	
																	});
														       }
														  }
														, closeOnBgClick:false
														//, showCloseBtn:true 
														});
													</c:if>
													
													
												});
											</script>
							            	 	
													<table class="table table-bordered">
														<colgroup>
														 	<col style="width: 15%;">
														 	<col style="width: 85%;">
														</colgroup>
														<tbody>
															<c:if test="${formMode eq 'UPDATE'}">
																<tr>
																	<th scope="row" class="align-middle table-active text-center">	
																		캠퍼스타운 ID <span class="text-danger">*</span>
																	</th>
																	
																	<td>
																		<div class="container">
																			<div class="row">
																				<%-- <form:input path="univId" cssClass="form-control col-sm-2" readonly="true"/>
																				<form:errors path="univId" element="div" cssClass="text-danger col-form-label mt-3 ml-2" /> --%>
																				${startHpMngrForm.univId }
																				<form:hidden path="oldUnivId" value="${startHpMngrForm.univId}" />
																				<%-- <form:hidden path="univId" /> --%>
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
																	<div class="container">
																		<div class="row">
																			<c:if test="${org eq 'university'}">
																				<%-- <form:input path="univNm" cssClass="form-control col-sm-2" readonly="true"/> --%>
																				${asapro:depName(sUnivId,departmentList)}
																				<form:hidden path="univId" value="${sUnivId}" />
																			</c:if>
																			<c:if test="${org ne 'university'}">
																				<form:input path="univNm" cssClass="form-control col-sm-2" readonly="true" value="${department.depName2}"/>
																				<c:if test="${formMode eq 'INSERT'}">
																					<a href="#n" id="univIdCheckBtn" class="btn btn-primary waves-effect waves-light ml-2 mr-2">캠퍼스타운 검색</a>
																					<div id="univIdCheckResult"></div>
																				</c:if>
																				<form:hidden path="univId" />
																			</c:if>
																			<form:errors path="univNm" element="div" cssClass="text-danger col-form-label mt-3 ml-2" />
																			<%-- <form:input path="oldUnivId" value="${startHpMngrForm.univId}" /> --%>
																		</div>
																	</div>	
																</td>
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
																	</div>
																</td>
															</tr>
														</tbody>
													</table>
												</div>
											</div>
						            	 </div>
						            	 
						            	 
					            	 	 <!-- card 2. -->
						            	 <div class="card">
						            	 	<div class="card-header">
					            	 		■ 회사 기본정보
						            	 	</div>
						            	 	
						            	 	<div class="card-body">
							            	 	<div class="table-responsive-sm">
							            	 	
							            	 	<div id="start" class="mfp-hide">
													<h5 class="modal-title mt-0">창업팀 목록</h5>
													<hr>
													<div id="startList">
													
													</div>
												</div>
												
												<script>
												/* 
													jQuery(function($){
														//콘텐츠 검색 폼 및 목록을 가져온다.
														$('#startList').load('${ADMIN_PATH}/startHpMngr/searchStartLayer.do');									
														
														// From an element with ID #popup
														$('#compIdCheckBtn').magnificPopup({
															
														  items: {
														      src: '#start',
														      type: 'inline',
														  }
														, callbacks: {
														      close: function(){
														    	  <c:if test="${formMode == 'UPDATE'}">
																	if($("#oldCompId").val() == $("#compId").val()){
																		alert("동일한 값입니다. 변경 후 확인해 주세요.");
																		return;
																	}
																	</c:if>
														    	  <c:if test="${formMode == 'INSERT'}">
																	if($("#univNm").val() == '' || $("#univNm").val() == null){
																		alert("캠퍼스타운명을 먼저 선택해주세요.");
																		$("#compNm").val("");
																		$("#compId").val("");
																		return;
																	}
																  </c:if>
																  if($("#depUse2").val() == "false"){
																		alert("현재 미사용 중인 부서값 입니다.\n'\[조직관리] > 부서 목록'\ 에서 해당 값을 사용으로 활성화 해주세요.");
																		$("#compNm").val("");
																		$("#compId").val("");
																		return;
																  }
																	
																  var univId = $("#univId").val();
																  var compId = $("#compId").val();
																	
																	$.ajax({
																		url : '${ADMIN_PATH}/startHpMngr/idCheck/jsonResultGet.do'
																		, type : 'get'
																		, dataType: 'json'
																		, data: {
																			univId : univId,
																			compId : compId
																		}
																	}).done(function(result){
																		if(result > 0){
																			alert('기 등록 데이터 입니다.');
																			$('#compIdCheckResult').html("<p style='color:red;'>기 등록 데이터 입니다.</p>");
																			$("#compNm").val("");
																			$("#compId").val("");
																		}else{
																			alert('등록가능한 데이터 입니다.');
																			$('#compIdCheckResult').html("<p style='color:blue;'>등록가능한 데이터 입니다.</p>");		
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
													 */
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
																		회사 ID <span class="text-danger">*</span>
																	</th>
																	
																	<td>
																		<div class="container">
																			<div class="row">
																				${startHpMngrForm.compId}
																				<form:hidden path="compId" />
																				<%-- <form:input path="oldUnivId" value="${startHpMngrForm.univId}" /> --%>
																			</div>
																		</div>	
																	</td>
																</tr>
															</c:if>
															<tr>
																<th scope="row" class="align-middle table-active text-center">
																	회사명 <span class="text-danger">*</span>
																</th>
																
																<td>
																	<!-- 2. 회사 : 레이어팝업으로 대학 목록에서 원하는 창업팀 선택해오기 -->
																	<div class="container">
																		<div class="row">
																			<%-- 
																			<c:if test="${org eq 'university' || org eq 'startup'}">
																				<form:input path="compNm" cssClass="form-control col-sm-2" readonly="true"/> 
																			</c:if>
																			<c:if test="${org ne 'university' && org ne 'startup'}">
																				<form:input path="compNm" cssClass="form-control col-sm-2" readonly="true"/>
																				<c:if test="${formMode eq 'INSERT'}">
																					<a href="#n" id="compIdCheckBtn" class="btn btn-primary waves-effect waves-light ml-2 mr-2">창업팀 검색</a>
																					<div id="compIdCheckResult"></div>
																				</c:if>
																			</c:if>
																			 --%>
																			<form:input path="compNm" cssClass="form-control col-sm-2" />
																			<form:errors path="compNm" element="div" cssClass="text-danger col-form-label mt-3 ml-2" />
																			
																			<%-- <form:hidden path="compId" /> --%>
																			<form:hidden path="oldCompId" value="${univHpMngrForm.compId}" />
																		</div>
																	</div>
																</td>
															</tr>
															
															<tr class="hidCol">
																<th scope="row" class="align-middle table-active text-center">
																	회사 전화번호
																</th>
																
																<td>
																	<div class="container">
																		<div class="row">
																			<form:select path="tel1" class="form-control col-sm-1 mr-1" title="휴대전화1">
																				<form:option value="">선택</form:option>
																				<c:forEach items="${tel1CodeList}" var="item" varStatus="status">
																					<form:option value="${item.codeId }">${item.codeName }</form:option>
																				</c:forEach>
																				<!-- <option value="self">직접입력</option> -->
																			</form:select>
																			<%-- <form:input path="tel1" class="form-control col-sm-1 mr-1" maxlength="3" /> --%>
																			&nbsp; - &nbsp; <form:input path="tel2" cssClass="form-control col-sm-1" cssErrorClass="form-control col-sm-2 is-invalid" maxlength="4" />
																			&nbsp; - &nbsp; <form:input path="tel3" cssClass="form-control col-sm-1 mr-10" cssErrorClass="form-control col-sm-2 is-invalid" maxlength="8" />
																			<form:errors path="tel1" element="div" cssClass="text-danger col-form-label ml-1" />
																			<input type="hidden" id="tel" />
																			<form:hidden path="compTel" />
																		</div>
																	</div>
																</td>
															</tr>
															
															<tr>
																<th scope="row" class="align-middle table-active text-center">
																	업종
																</th>
																
																<td>
																	<div class="container">
																		<div class="row">
																			<form:select path="induty" cssClass="form-control col-sm-2" title="업종코드">
																				<form:option value="">선택</form:option>
																				<form:options items="${indutyCodeList}" itemLabel="codeName" itemValue="codeId"/>
																			</form:select>
																		</div>
																	</div>
																</td>
															</tr>
															
															<tr>
																<th scope="row" class="align-middle table-active text-center">
																	사업분야
																</th>
																
																<td>
																	<div class="container">
																		<div class="row">
																			<form:input path="bsnsRealm" cssClass="form-control col-sm-9" cssErrorClass="col-sm-9 is-invalid" maxlength="200" />
																			<form:errors path="bsnsRealm" element="div" cssClass="text-danger col-form-label ml-9" />
																			
																			<span class="col-sm-9 mt-2" id="bsnsRealmList"></span>
																			<%-- 
																			<c:forEach items="${bsnsRealmCodeList}" var="bsnsRealmCode" varStatus="vs">
																				<c:set var="chkType" value="" />
																				<c:forTokens items="${startHpMngrForm.bsnsRealm }" delims="," var="typeItem">
																					<c:if test="${typeItem eq bsnsRealmCode.codeId }">
																						<c:set var="chkType" value="checked" />
																					</c:if>
																				</c:forTokens>
																				<div class="custom-control custom-checkbox custom-control-inline">
																					<input type="checkbox" name="bsnsRealm" id="bsnsRealm${vs.count }" class="custom-control-input" value="${bsnsRealmCode.codeId }" ${chkType } />
																					<label for="bsnsRealm${vs.count }" class="custom-control-label"> ${bsnsRealmCode.codeName }</label>
																				</div>
																			</c:forEach>
																			 --%>
																		</div>
																	</div>
																</td>
																
															</tr>
															<%-- 
															<tr>
																<th scope="row" class="align-middle table-active text-center">
																	관심분야
																</th>
																
																<td>
																	<div class="container">
																		<div class="row">
																			<form:input path="intrstRealm" cssClass="form-control col-sm-9" cssErrorClass="col-sm-9 is-invalid" maxlength="200" />
																			<form:errors path="intrstRealm" element="div" cssClass="text-danger col-form-label ml-9" />
																			
																			<!-- 
																			<c:forEach items="${intrstRealmCodeList}" var="intrstRealmCode" varStatus="vs">
																				<c:set var="chkType" value="" />
																				<c:forTokens items="${startHpMngrForm.intrstRealm }" delims="," var="typeItem">
																					<c:if test="${typeItem eq intrstRealmCode.codeId }">
																						<c:set var="chkType" value="checked" />
																					</c:if>
																				</c:forTokens>
																				<div class="custom-control custom-checkbox custom-control-inline">
																					<input type="checkbox" name="intrstRealm" id="intrstRealm${vs.count }" class="custom-control-input" value="${intrstRealmCode.codeId }" ${chkType } />
																					<label for="intrstRealm${vs.count }" class="custom-control-label"> ${intrstRealmCode.codeName }</label>
																				</div>
																			</c:forEach>
																			 --!>
																			 
																		</div>
																	</div>
																</td>
															</tr>
															 --%>
															
															<tr>
																<th scope="row" class="align-middle table-active text-center">
																	설립일
																</th>
																
																<td>
																	<div class="container">
																		<div class="row">
																			<form:input path="fondDt" cssClass="form-control col-sm-2 datepicker-ui" cssErrorClass="form-control col-sm-2 datepicker-ui is-invalid" autocomplete="off" readonly="true"/>
																			<form:errors path="fondDt" element="div" cssClass="text-danger col-form-label mt-2 ml-2" />
																		</div>
																	</div>
																</td>
															</tr>
															
															<tr>
																<th scope="row" class="align-middle table-active text-center">
																	위치
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
																			<form:textarea path="address" cssClass="form-control col-sm-10 mt-2" cssErrorClass="form-control col-sm-5 is-invalid" maxlength="1500" placeholder="주소" rows="5" cols="200"/>
																			<form:errors path="address" element="div" cssClass="text-danger col-form-label ml-5" readonly="true"/>
																			<%-- 
																			<form:input path="addressDetail" cssClass="form-control col-sm-5 ml-2 mt-2" cssErrorClass="form-control col-sm-5 is-invalid" maxlength="100" placeholder="상세 주소"/>
																			<form:errors path="addressDetail" element="div" cssClass="text-danger col-form-label" />
																			 --%>
																		</div>
																	</div>
																</td>
																<%-- <c:import url="/WEB-INF/jsp/egovframework/com/asapro/admin/include/zipSearch.jsp" charEncoding="UTF-8" /> --%>
															</tr>
															<tr>
																<th scope="row" class="align-middle table-active text-center">
																	임직원수
																</th>
																<td>
																	<div class="container">
																		<div class="row">
																			<form:input path="empCnt" cssClass="form-control col-sm-9" cssErrorClass="col-sm-9 is-invalid" maxlength="200" />
																			<form:errors path="empCnt" element="div" cssClass="text-danger col-form-label ml-9" />
																		</div>
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
																			<c:if test="${not empty startHpMngrForm.file1Info && startHpMngrForm.file1Info.fileId > 0}">
																				<img class="img-thumbnail" style="max-width: 200px; max-height: 200px;" src="${startHpMngrForm.file1Info.fileServletUrl}" onerror="this.src='${design.resources }/assets/images/noImage300x300.jpg'" />	
																				<br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="${APP_PATH}/file/download/uu/${startHpMngrForm.file1Info.fileUUID}" title="다운로드">${startHpMngrForm.file1Info.fileOriginalName }</a>
																			</c:if>
																		</div>
																		<%-- <form:hidden path="fileId1" value="${startHpMngrForm.file1Info.fileId}"/> --%>
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
															<%-- 
															<tr>
																<th scope="row" class="align-middle table-active text-center">
																	슬라이드 노출 이미지
																</th>
																
																<td>
																	<div class="container">
																		<div class="row">
																			<div class="col-sm">
																				<c:if test="${startHpMngrForm.stUploadFileNum > 0}">
																					<input type="file" name="stMultipartFiles[0]" id="stMultipartFiles0" class="selectFile col-form-label"/>
																					<form:errors path="stMultipartFiles" element="div" cssClass="text-danger col-form-label ml-2" />
																					<span class="bg-light font-weight-bold text-info">- 파일첨부는 총${startHpMngrForm.stUploadFileNum}개까지 가능합니다.(파일당${startHpMngrForm.stUploadSizeMax}MB제한)</span>
																					<div class="mt-2" id="fileList">
																						<c:forEach items="${startHpMngrForm.stFileInfos}" var="stFileInfo" varStatus="vs">
																							<div class="file_add">
																								<span>${stFileInfo.fileOriginalName}(${stFileInfo.fileSizeString})</span>
																								<i class="deleteBtn ml-2 fa fa-times text-danger" style="cursor: pointer;" data-fileid="${stFileInfo.fileId}"></i>
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
																					
																				</c:if>
																			</div>
																		</div>
																	</div>
																</td>
															</tr>
															 --%>
															<tr>
																<th scope="row" class="align-middle table-active text-center">
																	회사 CI
																</th>
																
																<td>
																	<div class="container">
																		<div class="row">
																			<c:if test="${not empty startHpMngrForm.file2Info && startHpMngrForm.file2Info.fileId > 0}">
																				<img class="img-thumbnail" style="max-width: 200px; max-height: 200px;" src="${startHpMngrForm.file2Info.fileServletUrl}" onerror="this.src='/assets/images/noImage300x300.jpg'" />	
																				<br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="${APP_PATH}/file/download/uu/${startHpMngrForm.file2Info.fileUUID}" title="다운로드">${startHpMngrForm.file2Info.fileOriginalName }</a>
																			</c:if>
																		</div>
																	</div>
																	
																	<div class="container">
																		<div class="row">
																			<%-- <a href="${APP_PATH}/file/download/uu/${startHpMngrForm.file2Info.fileUUID}" title="다운로드">${startHpMngrForm.file2Info.fileOriginalName }</a> --%>
																			<input type="file" id="file2" name="file2" class="col-form-label"/>
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
																			<form:textarea path="ment" id="ment2" cssClass="form-control col-sm-8" maxlength="150"/>
																			<form:errors path="ment" element="div" cssClass="text-danger" />
																		</div>	
																	</div>
																</td>
															</tr>
															
															
															<%-- 
															<tr>
																<th scope="row" class="align-middle table-active text-center">
																	창업활동지수
																</th>
																
																<td>
																	<c:if test="${formMode == 'INSERT'}">
																		<span class="bg-light font-weight-bold text-info">창업팀 정보 등록 후 이용 가능합니다. 등록 후 다시 확인해주세요.</span>
																	</c:if>
																
																<c:if test="${formMode == 'UPDATE'}">
																	<table class="table table-bordered univHpMngr_table">
																	<colgroup>
																	 	<col style="width: 20%;">
																	 	<col style="width: 16%;">
																	 	<col style="width: 16%;">
																	 	<col style="width: 16%;">
																	 	<col style="width: 16%;">
																	 	<col style="width: 16%;">
																	</colgroup>
																	<tbody>
																		<tr>
																			<th scope="row" class="align-middle table-active text-center">
																				구분
																			</th>
																			<th scope="row" class="align-middle table-active text-center">
																				매출액
																			</th>
																			<th scope="row" class="align-middle table-active text-center">
																				투자액
																			</th>
																			<th scope="row" class="align-middle table-active text-center">
																				고용인수
																			</th>
																			<th scope="row" class="align-middle table-active text-center">
																				지적재산
																			</th>
																			<th scope="row" class="align-middle table-active text-center">
																			</th>
																		</tr>
																		<c:forEach items="${list}" var="item" varStatus="vs">
																			<tr class="idxTbl">
																				<th scope="row" class="align-middle table-active text-center">
																					${item.year } ${item.cardiNum }사분기
																				</th>

																				<td>
																					<form:input path="saleAmt" id="saleAmt${item.indexId}" placeholder="매출액 입력(만원)" class="form-control col-sm-11" maxlength="10" />
																				</td>
																				
																				<td>
																					<form:input path="invtAmt" id="invtAmt${item.indexId}" placeholder="투자액 입력(만원)" class="form-control col-sm-11" maxlength="10" />
																				</td>

																				<td>
																					<form:input path="emplyCnt" id="emplyCnt${item.indexId}" placeholder="고용인수" class="form-control col-sm-11" maxlength="10" />
																				</td>

																				<td>
																					<form:input path="intellProp" id="intellProp${item.indexId}" placeholder="지적재산 입력(만원)" class="form-control col-sm-11" maxlength="10" />
																				</td>

																				<td>
																					<button type="button" class="btn btn-sm ml-2 btn-primary waves-effect waves-light idxUpdateBtn" data-indexid="${item.indexId}" data-saleamt="${item.saleAmt}" data-invtamt="${item.invtAmt}" data-emplycnt="${item.emplyCnt}" data-intellprop="${item.intellProp}">수정</button>
																					<button type="button" class="btn btn-sm ml-2 btn-primary waves-effect waves-light idxDeleteBtn" data-indexid="${item.indexId}">삭제</button>
																					<button type="button" class="btn btn-sm ml-2 btn-primary waves-effect waves-light idxDeleteBtn" data-matseq="${item.matSeq}" data-mateldid="${item.matEldId}" >삭제</button>
																				</td>
																			</tr>
																		</c:forEach>
																		
																			<tr>
																				<th scope="row" class="align-middle table-active text-center">
																					<div class="container">
																						<div class="row">
																							<input type="text" id="year" name="year" placeholder="년도 입력" class="form-control col-sm-6 mr-2" maxlength="4">
																							<select id="cardiNum" name="cardiNum" class="form-control col-sm-4">
																								<option value="">선택</option>
																								<option value="1">1</option>
																								<option value="2">2</option>
																								<option value="3">3</option>
																								<option value="4">4</option>
																							</select> 
																						</div>
																					</div>
																				</th>
																				<td>
																					<input type="text" id="saleAmt" name="saleAmt" placeholder="매출액 입력(만원)" class="form-control col-sm-11" maxlength="10">
																				</td>
																				<td>
																					<input type="text" id="invtAmt" name="invtAmt" placeholder="투자액 입력(만원)" class="form-control col-sm-11" maxlength="10">
																				</td>
																				<td>
																					<input type="text" id="emplyCnt" name="emplyCnt" placeholder="고용인수 입력" class="form-control col-sm-11" maxlength="5">
																				</td>
																				<td>
																					<input type="text" id="intellProp" name="intellProp" placeholder="지적재산 입력(만원)" class="form-control col-sm-11" maxlength="10">
																				</td>
																				<td>
																					<button type="button" class="btn btn-secondary waves-effect m-l-5 ml-2 mr-2 idxInsertBtn" >추가</button>
																					<button type="button" class="btn btn-sm ml-2 btn-primary waves-effect waves-light matMemoBtn" data-matseq="${item.matSeq}" data-mateldid="${item.matEldId}" >비고사항 수정</button>
																				</td>
																			</tr>
																		
																	</tbody>
																	</table>
																</c:if>
																
																</td>
															</tr>
															 --%>
															
															
														</tbody>
													</table>
							            	 	</div>
						            		</div>
							             </div>	
							             
							             <!-- card 3. -->
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
						            	 <!-- End card 3. -->
						            	 
						            	 
						            	 <!-- card 4. -->
							             <div class="card">
						            	 	<div class="card-header">
						            	 		■ 회사소개
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
																	주요 서비스
																</th>
																
																<td>
																	<div class="container">
																		<div class="row">
																			<form:textarea path="mainSvc" cssClass="form-control col-sm-8" rows="3" placeholder="주요 서비스" />
																			<form:errors path="mainSvc" element="div" cssClass="text-danger" />
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
														</tbody>
													</table>
												</div>
											</div>
										 </div>			
							             <!-- End card 4. -->

							             
							             <!-- card 5. -->
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
																		
																		<c:if test="${compEmpYn eq 'Y'}">
																			<div class="custom-control custom-radio custom-control-inline">
																				<form:radiobutton path="expsrYn" id="expsrYnY" class="custom-control-input" value="Y"/>
																				<label for="expsrYnY" class="custom-control-label"> Y</label>
																			</div>
																			<div class="custom-control custom-radio custom-control-inline">
																				<form:radiobutton path="expsrYn" id="expsrYnN" class="custom-control-input" value="N"/>
																				<label for="expsrYnN" class="custom-control-label"> N</label>
																			</div>
																			<form:errors path="expsrYn" element="div" cssClass="text-danger col-form-label ml-4" />
																		</c:if>
																		<c:if test="${compEmpYn eq 'N'}">
																			N &nbsp;&nbsp; <span class="bg-light font-weight-bold text-info">[창업팀 관리> 임직원 정보] 대표자 정보를 등록해주세요.</span>
																		</c:if>
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
																		<fmt:parseDate value="${startHpMngrForm.updDt}" var="parseDate1" pattern="yyyyMMddHHmmss" scope="page"/>
																		<fmt:formatDate value="${parseDate1}" pattern="yyyy-MM-dd HH:mm:ss"/>
																	</td>
																</tr>
															</c:if>
															
														</tbody>
													</table>
												</div>
											</div>
						            	 </div>
					            	 	 <!-- End card 5. -->
					            	 	 
					            	 	 <div class="card">
											<div class="card-body">
												<div class="table-responsive-sm">
													<button type="button" onclick="fn_save()" class="btn btn-primary waves-effect waves-light">저장</button>
													<a href="${ADMIN_PATH}/startHpMngr/list.do" class="btn btn-secondary waves-effect m-l-5">목록</a>
													<%-- <a href="${ADMIN_PATH}/youthSupport/list.do?${youthSupportSearch.queryString }" class="btn btn-secondary waves-effect m-l-5">목록</a> --%>
												</div>
											</div>
										</div>	
					            	 </form:form>
					            	 
					            	 <br/>
					            	 	<!-- card 6. -->
					            	 	<div class="p-3 mb-3 bg-light text-info font-weight-bold">
											<div><i class="mdi mdi-information"></i> 창업활동지수 등록</div>
										</div>
												
					            	 	 <div class="card">
						            	 	<!-- 
						            	 	<div class="card-header">
						            			■ 창업활동지수
						            	 	</div>
						            	 	 -->
						            	 	<c:if test="${formMode == 'INSERT'}">
												<!-- <span class="bg-light font-weight-bold text-info">창업팀 정보 등록 후 이용 가능합니다. 등록 후 다시 확인해주세요.</span> -->
												<span class="bg-light font-weight-bold text-danger">&nbsp;&nbsp;&nbsp;창업팀 정보 등록 후 이용 가능합니다. 등록 후 다시 확인해주세요.</span>
											</c:if>
											<c:if test="${formMode == 'UPDATE'}">
										
							            	 	<div class="card-body">
								            	 	<div class="table-responsive-sm">
														<table class="table table-bordered">
															<colgroup>
															 	<col style="width: 20%;">
															 	<col style="width: 16%;">
															 	<col style="width: 16%;">
															 	<col style="width: 16%;">
															 	<col style="width: 16%;">
															 	<col style="width: 16%;">
															</colgroup>
															
															<thead>	
																<tr>
																	<th scope="row" class="align-middle table-active text-center">
																		구분
																	</th>
																	<th scope="row" class="align-middle table-active text-center">
																		매출액
																	</th>
																	<th scope="row" class="align-middle table-active text-center">
																		투자액
																	</th>
																	<th scope="row" class="align-middle table-active text-center">
																		고용인수
																	</th>
																	<th scope="row" class="align-middle table-active text-center">
																		지적재산
																	</th>
																	<th scope="row" class="align-middle table-active text-center">
																	</th>
																</tr>
															</thead>
																
															<tbody id="idx_tbody">
																<c:forEach items="${list}" var="item" varStatus="vs">
																	<tr>
																		<th scope="row" class="align-middle table-active text-center" id="div${item.indexId }">
																			${item.sesnYear } <c:if test="${item.cardiNum eq 1}">상반기</c:if> <c:if test="${item.cardiNum eq 2}">하반기</c:if>
																		</th>

																		<td>
																			<input type="text" name="saleAmt" id="saleAmt${item.indexId}" placeholder="매출액 입력(만원)" class="form-control col-sm-11" maxlength="10" value="${item.saleAmt }" />
																		</td>
																		
																		<td>
																			<input type="text" name="invtAmt" id="invtAmt${item.indexId}" placeholder="투자액 입력(만원)" class="form-control col-sm-11" maxlength="10" value="${item.invtAmt }" />
																		</td>

																		<td>
																			<input type="text" name="emplyCnt" id="emplyCnt${item.indexId}" placeholder="고용인수" class="form-control col-sm-11" maxlength="10" value="${item.emplyCnt }" />
																		</td>

																		<td>
																			<input type="text" name="intellProp" id="intellProp${item.indexId}" placeholder="지적재산 입력(만원)" class="form-control col-sm-11" maxlength="10" value="${item.intellProp }"/>
																		</td>

																		<td>
																			<button type="button" class="btn btn-sm ml-2 btn-primary waves-effect waves-light idxUpdateBtn" data-indexid="${item.indexId}" onclick="fnUpdate(this);">수정</button>
																			<button type="button" class="btn btn-sm ml-2 btn-primary waves-effect waves-light idxDeleteBtn" data-indexid="${item.indexId}" onclick="fnDelete(this);">삭제</button>
																			<%-- <button type="button" class="btn btn-sm ml-2 btn-primary waves-effect waves-light idxDeleteBtn" data-matseq="${item.matSeq}" data-mateldid="${item.matEldId}" >삭제</button> --%>
																		</td>
																	</tr>
																</c:forEach>
																</tbody>
																
																
																
																<%-- <form:form modelAttribute="activeIdxForm" action="" method="post"> --%>
																<%-- <form name="activeIdxForm" id="activeIdxForm" action="" method=""> --%>
																<%-- <form:form modelAttribute="startHpMngrForm" id="startHpMngrForm" cssClass="" action="${actionUrl}" method="post" enctype="multipart/form-data"> --%>
					            	 
					            	 							<form:form modelAttribute="activeIdxForm" id="activeIdxForm">
					            	 								<input type="hidden" id="compId2" name="compId2" value="${startHpMngrForm.compId}" />
																	<tbody>
																		<tr>
																			<th scope="row" class="align-middle table-active text-center">
																				<div class="container">
																					<div class="row">
																						<!-- <input type="text" name="year" id="year" placeholder="년도 입력" class="form-control col-sm-6 mr-2" maxlength="4" /> -->
																						<c:set var="now" value="<%=new java.util.Date()%>" />
																						<fmt:formatDate var="nowYear" value="${now}" pattern="yyyy" />

																						<select id="sesnYear" name="sesnYear" class="form-control col-sm-4 ml-4">
																							<c:forEach var="i" begin="${nowYear-2 }" end="${nowYear}" step="1">
																								<c:if test="${i eq nowYear }">
																									<option value="${i}" selected>${i}</option>
																								</c:if>
																								<c:if test="${i ne nowYear }">	
																									<option value="${i}">${i}</option>
																								</c:if>	
																							</c:forEach>
																						</select>
																						
																						<select id="cardiNum" name="cardiNum" class="form-control col-sm-4 ml-4">
																							<option value="">선택</option>
																							<option value="1">상반기</option>
																							<option value="2">하반기</option>
																						</select> 
																					</div>
																				</div>
																			</th>
																			<td>
																				<form:input path="saleAmt" placeholder="매출액 입력(만원)" class="form-control col-sm-11" maxlength="10" />
																				<form:errors path="saleAmt" element="div" cssClass="text-danger" />
																			</td>
																			<td>
																				<form:input path="invtAmt" placeholder="투자액 입력(만원)" class="form-control col-sm-11" maxlength="10" />
																				<form:errors path="invtAmt" element="div" cssClass="text-danger" />
																			</td>
																			<td>
																				<form:input path="emplyCnt" placeholder="고용인수 입력" class="form-control col-sm-11" maxlength="5" />
																				<form:errors path="emplyCnt" element="div" cssClass="text-danger" />
																			</td>
																			<td>
																				<form:input path="intellProp" placeholder="지적재산 입력(만원)" class="form-control col-sm-11" maxlength="10" />
																				<form:errors path="intellProp" element="div" cssClass="text-danger" />
																			</td>
																			<td>
																				<button type="button" class="btn btn-secondary waves-effect m-l-5 ml-2 mr-2 idxInsertBtn" >추가</button>
																				<%-- <button type="button" class="btn btn-sm ml-2 btn-primary waves-effect waves-light matMemoBtn" data-matseq="${item.matSeq}" data-mateldid="${item.matEldId}" >비고사항 수정</button> --%>
																			</td>
																		</tr>
																	</tbody>
																</form:form>
														</table>
													</div>
												</div>
											</c:if>				
										</div>
					            	 	 <!-- End card 6. -->
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
 //CKEDITOR
CKEDITOR.replace( 'vsn',{
	filebrowserImageUploadUrl : GLOBAL.ADMIN_PATH + '/file/editor/upload.do?module=startHpMngr&moduleId=&moduleSub=&moduleSubId='
	, height : 200		
} );
CKEDITOR.replace( 'intrcn',{
	filebrowserImageUploadUrl : GLOBAL.ADMIN_PATH + '/file/editor/upload.do?module=startHpMngr&moduleId=&moduleSub=&moduleSubId='
	, height : 200		
} );
CKEDITOR.replace( 'mainSvc',{
	filebrowserImageUploadUrl : GLOBAL.ADMIN_PATH + '/file/editor/upload.do?module=startHpMngr&moduleId=&moduleSub=&moduleSubId='
	, height : 200		
} );
CKEDITOR.replace( 'prMssage',{
	filebrowserImageUploadUrl : GLOBAL.ADMIN_PATH + '/file/editor/upload.do?module=startHpMngr&moduleId=&moduleSub=&moduleSubId='
	, height : 200		
} );
</script> 

<c:import url="/WEB-INF/jsp/egovframework/com/asapro/admin/layout/footer.jsp" charEncoding="UTF-8" />     