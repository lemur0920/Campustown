<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="asapro" uri="http://www.asadal.com" %>


<c:import url="/WEB-INF/jsp/egovframework/com/asapro/admin/layout/header.jsp" charEncoding="UTF-8" />

<!-- Magnific popup -->
        <link href="${CONTEXT_PATH }/assets/plugins/magnific-popup/magnific-popup.css" rel="stylesheet" type="text/css">
<!-- Magnific popup -->
        <script src="${CONTEXT_PATH }/assets/plugins/magnific-popup/jquery.magnific-popup.min.js"></script>
        <script src="${CONTEXT_PATH }/assets/pages/lightbox.js"></script>

<script>
jQuery(function($){
	
	/* 1. 이벤트 처리 */
	//alert($('#tel1').val());
	/* 
	$('#selOpt option').each(function(){
		if (this.value = $('#tel1').val()) {
			//alert(this.value);
			$(this).val($('#tel1').val()); 
			return;
		} else {
			//alert(this.value);
			$(this).val("000");
			return;
		} 
	});
	 */
	<c:if test="${rprsntvDataYn eq 'Y'}">
		alert("대표자 등록 정보가 이미 존재합니다.");
	</c:if> 
	
	// 2020.04.07. 대표자 전화번호 숨김 처리
	// (회사 전화번호 => 창업팀 등록 화면으로 이동)
	$('.hidCol').hide();	
	
	 
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


	$("#selOpt2").find("option").each(function(){
		if($('#phone_1').val() != '' && $('#phone_1').val() != null){
		    if($(this).val() == $('#phone_1').val()){
		    	$(this).val($('#phone_1').val()).prop("selected", true);
		    	//$('#phone1').hide();
		    	return;
		    }else{
		    	$("#selOpt2 option:last").attr("selected", true);
		    	$('#phone_1').show();
		    	return;
		    }
		}else{
			$("#selOpt2 option:first").attr("selected", true);
	    	//$('#phone1').hide();
			return;
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
	
	// 초기값 셋팅 후 show, hide 처리
	
	if($('#selOpt').val() == "self"){
		$('#tel1').show();
	}else{
		$('#tel1').hide();
	}
	 
	if($('#selOpt2').val() == "self"){
		$('#phone_1').show();
	}else{
		$('#phone_1').hide();
	}
	
	if($('#emailSel').val() == "self"){
		$('#email_2').show();
	}else{
		$('#email_2').hide();
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
	 
	// 휴대폰 번호 selectbox 클릭 시
	$("#selOpt2").on("click", function() {
		if($(this).val() == "self"){
			$('#phone_1').show();
			$('#phone_1').val("");
		} else if($(this).val() == "opt"){
			$('#phone_1').hide();
			$('#phone_1').val("");
		} else {
			$('#phone_1').hide();
			$('#phone_1').val($(this).val());
		}
	});
	
	
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
	
	// 쪽지 수신 ID 클릭시 해당 회사 소속 계정 select
	
	$("input:text[id^=seoulId]").on('click', function(e) {
		e.preventDefault();
		//alert("click~!!!");
		var attr = $(this).attr('id').trim();
		//alert(attr);
		var compId = $('#compId').val();
		//관리자 회원 검색 폼 및 목록을 가져온다.
		//$('.searchBtn').on('click',function(){
			$('#memberList').load('${ADMIN_PATH}/startHpMngr/searchLayer.do?attr=' + attr+"&compId="+compId);									
		//});
		
		
		// From an element with ID #popup
		$('input:text[id^=seoulId]').magnificPopup({
		  items: {
		      src: '#adminMember',
		      type: 'inline',
		  }
		, closeOnBgClick:false
		});
		
	});
	
	
	
	/* 2. 정규식 */
	// 숫자만 입력
	$("#tel1, #tel2, #phone_1, #phone_2, #phone_3") .on("keyup", function() {
    	$(this).val($(this).val().replace(/[^0-9]/g,""));
	});
	
	$("#tel3") .on("keyup", function() {
    	$(this).val($(this).val().replace(/[^0-9~]/g,""));
	});
	
	
	
	// 서울시ID 정규식
	$("input:text[id^=seoulId]").on("keyup", function() {
    	$(this).val($(this).val().replace(/[^a-zA-z0-9]/g,""))
    	       .css("text-transform", "lowercase");
	});
	
	// 이메일 정규식(대표자 정보)
	$("#email_1, #email_2").on("keyup", function() {
    	$(this).val($(this).val().replace(/[^a-zA-z0-9-_\.]/g,""))
    	       .css("text-transform", "lowercase");
	});
	
	
	// 숫자랑 , -
	$("input:text[id^=phone]").on("keyup", function() {
    	$(this).val($(this).val().replace(/[^0-9,-]/g,""));
	});
	
	// 이메일 정규식(임직원 정보)
	$("input:text[id^=email]").on("keyup", function() {
    	$(this).val($(this).val().replace(/[^a-zA-z0-9-_\.@]/g,""))
    	       .css("text-transform", "lowercase");
	});
	

	$('.empssInsertBtn').on('click', function(){
		//alert("click~!!!");
		
		if($("#empNm0").val() == '' || $("#empNm0").val() == null){
			alert("이름은 필수 값 입니다.");
			$("#empNm0").focus();
			return;
		}
		
		if($("#phone0").val() == '' || $("#phone0").val() == null){
			alert("휴대폰 번호는 필수 값 입니다.");
			$("#phone0").focus();
			return;
		}
		
		if($("#email0").val() == '' || $("#email0").val() == null){
			alert("이메일은 필수 값 입니다.");
			$("#email0").focus();
			return;
		}
		
		
		
		if(!confirm("임직원 정보를 등록 하시겠습니까?")){
			return;
		} else {
			// return undefined 관련
			// return값은 success(.done) 밖에 있어야 함! => 그래도 해결 안됨.., 
			// 참고: http://melonicedlatte.com/web/2017/05/18/185045.html
			var queryString = $("#startHpMngrEmpssForm").serialize();
			var result2 = [];
			var str ='';
			
			$.ajax({
				url : '${ADMIN_PATH}/startHpMngr/empInfo/empss/jsonCR.do'
					, type : 'post'
					, dataType: 'json'
					, data:queryString
					, async: false
			}).done(function(result){
				if(result != null){
					alert('등록이 완료됬습니다.');
					//var str = '';
					str += '<tr>';
					//////////////
					str += '<td><input type="text" name="ofcps" id="ofcps'+result.empId+'" placeholder="직위" class="form-control col-sm-11 mt-4" maxlength="20" value="'+result.ofcps+'" /></td>';
					//////////////
					str += '<td><input type="text" name="empNm" id="empNm'+result.empId+'" placeholder="이름" class="form-control col-sm-11 mt-4" maxlength="40" value="'+result.empNm+'" /></td>';
					//////////////
					str += '<td><input type="text" name="chrgJob" id="chrgJob'+result.empId+'" placeholder="담당 업무" class="form-control col-sm-11 mt-4" maxlength="20" value="'+result.chrgJob+'" /></td>';
					//////////////					
					str += '<td>노출: ';
					
					//var checkedVal1 = result.phoneExpsrYn;
					//alert(checkedVal1);
					//$('input:radio[name=search_type]:input[value=' + st + ']').attr("checked", true);
					//$("input[name=background][value='some value']").prop("checked",true);
					
					
					str += '<div class="custom-control custom-radio custom-control-inline mt-2 mb-2">';
					str += '<input type="radio" name="phoneExpsrYn'+result.empId+'" id="phoneExpsrYnY'+result.empId+'" class="custom-control-input" value="Y">';
					str += '<label for="phoneExpsrYnY'+result.empId+'" class="custom-control-label">Y</label></div>';
					
					str += '<div class="custom-control custom-radio custom-control-inline mt-2 mb-2">';
					str += '<input type="radio" name="phoneExpsrYn'+result.empId+'" id="phoneExpsrYnN'+result.empId+'" class="custom-control-input" value="N">';
					str += '<label for="phoneExpsrYnN'+result.empId+'" class="custom-control-label">N</label></div><br/>';
					
					//$("input:radio[name=phoneExpsrYn"+result.empId+"][value='"+result.phoneExpsrYn+"']").prop("checked", true);
					
					str += '<input type="text" name="phone" id="phone'+result.empId+'" placeholder="\'-\'를 포함해 입력해주세요." class="form-control col-sm-11" maxlength="13" value="'+result.phone+'" />';
					str += '</td>';
					//////////////
					str += '<td>노출: ';
					
					str += '<div class="custom-control custom-radio custom-control-inline mt-2 mb-2"> ';
					str += '<input type="radio" name="emailExpsrYn'+result.empId+'" id="emailExpsrYnY'+result.empId+'" class="custom-control-input" value="Y">';
					str += '<label for="emailExpsrYnY'+result.empId+'" class="custom-control-label">Y</label></div>';
					
					str += '<div class="custom-control custom-radio custom-control-inline mt-2 mb-2">';
					str += '<input type="radio" name="emailExpsrYn'+result.empId+'" id="emailExpsrYnN'+result.empId+'" class="custom-control-input" value="N">';
					str += '<label for="emailExpsrYnN'+result.empId+'" class="custom-control-label">N</label></div><br/>';
					
					str += '<input type="text" name="email" id="email'+result.empId+'" placeholder="\'@\' 를 포함해 입력해주세요." class="form-control col-sm-11" maxlength="100" value="'+result.email+'" />';
					str += '</td>';
					//////////////
					str += '<td>쪽지수신: ';
					
					str += '<div class="custom-control custom-radio custom-control-inline mt-2 mb-2">';
					str += '<input type="radio" name="seoulIdMsgExpsrYn'+result.empId+'" id="seoulIdMsgExpsrYnY'+result.empId+'" class="custom-control-input" value="Y">';
					str += '<label for="seoulIdMsgExpsrYnY'+result.empId+'" class="custom-control-label">Y</label></div>';
					
					str += '<div class="custom-control custom-radio custom-control-inline mt-2 mb-2">';
					str += '<input type="radio" name="seoulIdMsgExpsrYn'+result.empId+'" id="seoulIdMsgExpsrYnN'+result.empId+'" class="custom-control-input" value="N">';
					str += '<label for="seoulIdMsgExpsrYnN'+result.empId+'" class="custom-control-label">N</label></div><br/>';
					
					str += '<input type="text" name="seoulId" id="seoulId'+result.empId+'" placeholder="임직원 ID" class="form-control col-sm-11" maxlength="15" value="'+result.seoulId+'" />';
					str += '</td>';
					//////////////
					str += '<td>사용자 노출: ';
					
					str += '<div class="custom-control custom-radio custom-control-inline mt-2 mb-2">';
					str += '<input type="radio" name="expsrYn'+result.empId+'" id="expsrYnY'+result.empId+'" class="custom-control-input" value="Y">';
					str += '<label for="expsrYnY'+result.empId+'" class="custom-control-label">Y</label></div>';
					
					str += '<div class="custom-control custom-radio custom-control-inline mt-2 mb-2">';
					str += '<input type="radio" name="expsrYn'+result.empId+'" id="expsrYnN'+result.empId+'" class="custom-control-input" value="N">';
					str += '<label for="expsrYnN'+result.empId+'" class="custom-control-label">N</label></div><br/>';
					
					str += '<button type="button" class="btn btn-sm ml-2 btn-primary waves-effect waves-light" data-empid="'+result.empId+'" onclick="fnUpdate(this);" >수정</button>';
					str += '<button type="button" class="btn btn-sm ml-2 btn-primary waves-effect waves-light" data-empid="'+result.empId+'" onclick="fnDelete(this);"">삭제</button>';
					str += '</td></tr>';
					
					result2 = result;
				}else{
					alert('값을 불러오지 못하였습니다.[fail]');
				}
			});
			
			// return undefined 관련
			// return값은 success(.done) 밖에 있어야 함! => 그래도 해결 안됨.., 
			if(str != ""){
				$('#emp_tbody').append(str);
				
				// radiobox check 처리
				$("input:radio[name=phoneExpsrYn"+result2.empId+"][value='"+result2.phoneExpsrYn+"']").prop("checked", true);
				$("input:radio[name=emailExpsrYn"+result2.empId+"][value='"+result2.emailExpsrYn+"']").prop("checked", true);
				$("input:radio[name=seoulIdMsgExpsrYn"+result2.empId+"][value='"+result2.seoulIdMsgExpsrYn+"']").prop("checked", true);
				$("input:radio[name=expsrYn"+result2.empId+"][value='"+result2.expsrYn+"']").prop("checked", true);
				
				
				// undefined, 빈 값 처리 (<- 이제 필요 없음!!! [retur값은 success(.done) 밖에 있어야 함!])
				if(trim($('#ofcps'+result2.empId).val())){
					$('#ofcps'+result2.empId).val("");
				}
				if(trim($('#chrgJob'+result2.empId).val())){
					$('#chrgJob'+result2.empId).val("");
				}
				if(trim($('#seoulId'+result2.empId).val())){
					$('#seoulId'+result2.empId).val("");
				}
				
				// 등록 필드 값 비워주기
				$('#ofcps0').val("");
				$('#empNm0').val("");
				$('#chrgJob0').val("");
				$('#phone0').val("");
				$('#email0').val("");
				$('#seoulId0').val("");
				
				$("#phoneExpsrYnY0").prop('checked', false);
				$("#phoneExpsrYnN0").prop('checked', false);
				
				$("#emailExpsrYnY0").prop('checked', false);
				$("#emailExpsrYnN0").prop('checked', false);
				
				$("#seoulIdMsgExpsrYnY0").prop('checked', false);
				$("#seoulIdMsgExpsrYnN0").prop('checked', false);
				
				$("#expsrYnY0").prop('checked', false);
				$("#expsrYnN0").prop('checked', false);
			}
		}	
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


// 임직원 정보 수정
function fnUpdate(th){
	//alert("수정~!!!");
	//alert(th.getAttribute("data-empid"));
	
	var empId = th.getAttribute("data-empid");
	var compId2 = $('#compId2').val();
	
	var ofcps = $('#ofcps'+empId).val();
	var empNm = $('#empNm'+empId).val();
	var chrgJob = $('#chrgJob'+empId).val();
	
	var phone = $('#phone'+empId).val();
	var email = $('#email'+empId).val();
	var seoulId = $('#seoulId'+empId).val();
	
	var phoneExpsrYn = $('input[name="phoneExpsrYn'+empId+'"]:checked').val();
	var emailExpsrYn  = $('input[name="emailExpsrYn'+empId+'"]:checked').val();
	var seoulIdMsgExpsrYn = $('input[name="seoulIdMsgExpsrYn'+empId+'"]:checked').val();
	var expsrYn = $('input[name="expsrYn'+empId+'"]:checked').val();
	
	//alert("compId2: "+compId2);
	
	if(!confirm("해당사항을 수정하시겠습니까?")){
		return;
	}else{
		$.ajax({
			url : '${ADMIN_PATH}/startHpMngr/empInfo/empss/jsonU.do'
				, type : 'get'
				, dataType: 'json'
				, data:{
					compId2 : compId2,
					empId : empId,
					ofcps : ofcps,
					empNm : empNm,
					chrgJob : chrgJob,
					phone : phone,
					email : email,
					seoulId : seoulId,
					phoneExpsrYn : phoneExpsrYn,
					emailExpsrYn : emailExpsrYn,
					seoulIdMsgExpsrYn : seoulIdMsgExpsrYn,
					expsrYn : expsrYn
				}
		}).done(function(result){
			if(result > 0){
				alert('업데이트가 완료됬습니다.');
				<c:if test="${formMode == 'INSERT'}">
					$("#startHpMngrEmpForm").attr("action","${ADMIN_PATH}/startHpMngr/empInfo/insert.do?compId="+$('#compId').val()).submit();
				</c:if>
				<c:if test="${formMode == 'UPDATE'}">
					$("#startHpMngrEmpForm").attr("action","${ADMIN_PATH}/startHpMngr/empInfo/update.do?compId="+$('#compId').val()).submit();
				</c:if>
				
				
			}
		}).fail(function(result){
			alert('값을 불러오지 못하였습니다.[fail]');
		});
	}
}


// 임직원 정보 삭제
function fnDelete(th){
	//alert("삭제~!!!");
	
	var empId = th.getAttribute("data-empid");
	var compId = $('#compId').val();
	
	//alert("empId: " + empId);
	//alert("compId: " + compId);
	
	
	var tr = $(th).parent().parent();
	
	if(!confirm("해당사항을 삭제하시겠습니까?")){
		return;
	}else{
		
		$.ajax({
			url : '${ADMIN_PATH}/startHpMngr/empInfo/empss/jsonD.do'
				, type : 'get'
				, dataType: 'json'
				, data:{
					compId : compId,
					empId : empId
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
	
	// 대표자 이름 
	if($("#empNm").val() == '' || $("#empNm").val() == null){
		alert("운영담당자는 필수값 입니다.");
		$("#empNm").focus();
		return;
	}
	
	// 회사 전화번호
	if($("#tel1").val() != '' && $("#tel1").val() != null){
		if($("#tel2").val() == '' || $("#tel2").val() == null){
			alert("전화번호를 마저 입력해주세요.");
			$("#tel2").focus();
			return;
		}
		if($("#tel3").val() == '' || $("#tel3").val() == null){
			alert("전화번호를 마저 입력해주세요.");
			$("#tel3").focus();
			return;
		}
	}
	
	// 대표자 휴대폰 번호
	if($("#phone_1").val() != '' && $("#phone_1").val() != null){
		if($("#phone_2").val() == '' || $("#phone_2").val() == null){
			alert("휴대폰 번호를 마저 입력해주세요.");
			$("#phone_2").focus();
			return;
		}
		if($("#phone_3").val() == '' || $("#phone_3").val() == null){
			alert("휴대폰 번호를 마저 입력해주세요.");
			$("#phone_3").focus();
			return;
		}
	}
	
	// 이메일 주소
	if($("#email_1").val() != '' && $("#email_1").val() != null){
		if($("#email_2").val() == '' || $("#email_2").val() == null){
			alert("이메일 주소를 마저 입력해주세요.");
			$("#email_2").focus();
			return;
		}
	}
	
	
	if(!confirm("계속 진행 하시겠습니까?")){
		return;
	}else{
		// 히든값 처리
		$("#tel").val($("#tel1").val()+"-"+$("#tel2").val()+"-"+$("#tel3").val());
		$("#phone").val($("#phone_1").val()+"-"+$("#phone_2").val()+"-"+$("#phone_3").val());
		$("#email").val($("#email_1").val()+"@"+$("#email_2").val());
		
		$("#startHpMngrEmpForm").submit();
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
											<div><i class="mdi mdi-information"></i> 임직원 정보 관리 - 상세화면 </div>
										</div>
									</c:if>
					            	<c:if test="${formMode == 'INSERT'}">
										<div class="p-3 mb-3 bg-light text-info font-weight-bold">
											<div><i class="mdi mdi-information"></i> 임직원 정보 관리 - 등록화면</div>
										</div>
									</c:if>
									<c:if test="${formMode == 'INSERT'}"><c:set var="actionUrl" value="${ADMIN_PATH}/startHpMngr/empInfo/insert.do" /></c:if>
									<c:if test="${formMode == 'UPDATE'}"><c:set var="actionUrl" value="${ADMIN_PATH}/startHpMngr/empInfo/update.do" /></c:if>
									
					            	<form:form modelAttribute="startHpMngrEmpForm" id="startHpMngrEmpForm" cssClass="" action="" method="post" enctype="multipart/form-data">
					            	 	<form:hidden path="empId" />
					            	 	<form:hidden path="rprsntvYn" value="Y" />
					            	 	<input type=hidden id="compId" value="${startHpMngrEmpForm.compId}"/>
					            	 	<input type=hidden id="univId" value="${startHpMngrEmpForm.univId}"/>
					            	 	
					            	 	<%-- <input type=hidden id="compNm" value="${asapro:depName(startHpMngrEmpForm.compId, departmentList)}"/> --%>
					            	 	<span class="bg-light font-weight-bold text-info"><strong class="font-16">&nbsp;&nbsp;&nbsp;&nbsp;${startHpMngrEmpForm.compNm}</strong> (${asapro:depName(startHpMngrEmpForm.univId, departmentList)})</span>
					            	 	<br/><br/>
					            	 	<%-- <form:input path="compNm" value="${startHpMngrEmpForm.compNm}"/> --%>
					            	 	
					            	 	<!-- card 1. -->
							             <div class="card">
						            	 	<div class="card-header">
						            	 		■ 대표자
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
																	대표자 이름 <span class="text-danger">*</span>
																</th>
																
																<td>
																	<div class="container">
																		<div class="row">
																			<form:input path="empNm" id="empNm" cssClass="form-control col-sm-2 mr-2" cssErrorClass="col-sm-2 is-invalid" maxlength="100" />
																			<form:errors path="empNm" element="div" cssClass="text-danger col-form-label ml-2" />
																			<%-- <span class="align-middle text-center mt-2"><strong>( 회사명 : ${startHpMngrEmpForm.compNm}${asapro:depName(startHpMngrEmpForm.compId, departmentList)} )</strong></span> --%>
																			
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
																			<select id="selOpt" class="form-control col-sm-1 mr-1" title="휴대전화1">
																				<option value="opt">선택</option>
																				<c:forEach items="${tel1CodeList}" var="item" varStatus="status">
																					<option value="${item.codeId }" >${item.codeName }</option>
																				</c:forEach>
																				<option value="self">직접입력</option>
																			</select>
																			<form:input path="tel1" class="form-control col-sm-1 mr-1" maxlength="3" />
																			&nbsp; - &nbsp; <form:input path="tel2" cssClass="form-control col-sm-1" cssErrorClass="form-control col-sm-2 is-invalid" maxlength="4" />
																			&nbsp; - &nbsp; <form:input path="tel3" cssClass="form-control col-sm-1 mr-10" cssErrorClass="form-control col-sm-2 is-invalid" maxlength="8" />
																			<form:errors path="tel1" element="div" cssClass="text-danger col-form-label ml-1" />
																			<form:hidden path="tel" />
																			
																			<span class="align-middle text-center mt-2">
																			&nbsp;&nbsp;
																			노출 : &nbsp;&nbsp;</span>
																			<div class="custom-control custom-radio custom-control-inline mt-2">
																				<form:radiobutton path="telExpsrYn" id="telExpsrYnY" class="custom-control-input" value="Y"/>
																				<label for="telExpsrYnY" class="custom-control-label">Y</label>
																			</div>
																			<div class="custom-control custom-radio custom-control-inline mt-2">
																				<form:radiobutton path="telExpsrYn" id="telExpsrYnN" class="custom-control-input" value="N"/>
																				<label for="telExpsrYnN" class="custom-control-label">N</label>
																			</div>
																			<form:errors path="telExpsrYn" element="div" cssClass="text-danger col-form-label ml-4" />
																			
																		</div>
																	</div>
																</td>
															</tr>
															
															
															<tr>
																<th scope="row" class="align-middle table-active text-center">
																	휴대폰번호
																</th>
																
																<td>
																	<div class="container">
																		<div class="row">
																			<select id="selOpt2" class="form-control col-sm-1 mr-1" title="휴대전화1">
																				<option value="opt">선택</option>
																				<c:forEach items="${mobile1CodeList}" var="item" varStatus="status">
																					<option value="${item.codeId }" >${item.codeName }</option>
																				</c:forEach>
																				<option value="self">직접입력</option>
																			</select>
																			<form:input path="phone1" id="phone_1" class="form-control col-sm-1" maxlength="3" />
																			&nbsp; - &nbsp; <form:input path="phone2" id="phone_2" cssClass="form-control col-sm-1" cssErrorClass="form-control col-sm-1 is-invalid" maxlength="4" />
																			&nbsp; - &nbsp; <form:input path="phone3" id="phone_3" cssClass="form-control col-sm-1" cssErrorClass="form-control col-sm-1 is-invalid" maxlength="4" />
																			<form:errors path="phone1" element="div" cssClass="text-danger col-form-label ml-1" />
																			<form:hidden path="phone" />
																			
																			<span class="align-middle text-center mt-2">
																			&nbsp;&nbsp;
																			노출 : &nbsp;&nbsp;</span>
																			<div class="custom-control custom-radio custom-control-inline mt-2">
																				<form:radiobutton path="phoneExpsrYn" id="phoneExpsrYnY" class="custom-control-input" value="Y"/>
																				<label for="phoneExpsrYnY" class="custom-control-label">Y</label>
																			</div>
																			<div class="custom-control custom-radio custom-control-inline mt-2">
																				<form:radiobutton path="phoneExpsrYn" id="phoneExpsrYnN" class="custom-control-input" value="N"/>
																				<label for="phoneExpsrYnN" class="custom-control-label">N</label>
																			</div>
																			<form:errors path="phoneExpsrYn" element="div" cssClass="text-danger col-form-label ml-4" />
																		</div>
																	</div>
																</td>
															</tr>
															
															
															<tr>
																<th scope="row" class="align-middle table-active text-center">
																	이메일주소
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
																			
																			<span class="align-middle text-center mt-2">
																			&nbsp;&nbsp;
																			노출 : &nbsp;&nbsp;</span>
																			<div class="custom-control custom-radio custom-control-inline mt-2">
																				<form:radiobutton path="emailExpsrYn" id="emailExpsrYnY" class="custom-control-input" value="Y"/>
																				<label for="emailExpsrYnY" class="custom-control-label">Y</label>
																			</div>
																			<div class="custom-control custom-radio custom-control-inline mt-2">
																				<form:radiobutton path="emailExpsrYn" id="emailExpsrYnN" class="custom-control-input" value="N"/>
																				<label for="emailExpsrYnN" class="custom-control-label">N</label>
																			</div>
																			<form:errors path="emailExpsrYn" element="div" cssClass="text-danger col-form-label ml-4" />
																		</div>
																	</div>
																</td>
															</tr>
															
															<tr>
																<th scope="row" class="align-middle table-active text-center">
																	대표자 ID
																</th>
																
																<td>
																	<div class="container">
																		<div class="row">
																			<form:input path="seoulId" cssClass="form-control col-sm-2" cssErrorClass="col-sm-2 is-invalid" maxlength="15" readonly="true"/>
																			<form:errors path="seoulId" element="div" cssClass="text-danger col-form-label ml-2" />
																			<span class="align-middle text-center mt-2">
																			&nbsp;&nbsp;
																			쪽지수신 : &nbsp;&nbsp;</span>
																			<div class="custom-control custom-radio custom-control-inline mt-2">
																				<form:radiobutton path="seoulIdMsgExpsrYn" id="seoulIdMsgExpsrYnY" class="custom-control-input" value="Y"/>
																				<label for="seoulIdMsgExpsrYnY" class="custom-control-label">Y</label>
																			</div>
																			<div class="custom-control custom-radio custom-control-inline mt-2">
																				<form:radiobutton path="seoulIdMsgExpsrYn" id="seoulIdMsgExpsrYnN" class="custom-control-input" value="N"/>
																				<label for="seoulIdMsgExpsrYnN" class="custom-control-label">N</label>
																			</div>
																			<form:errors path="seoulIdMsgExpsrYn" element="div" cssClass="text-danger col-form-label ml-4" />
																		</div>
																	</div>
																</td>
															</tr>
															
															
															<tr>
																<th scope="row" class="align-middle table-active text-center">
																	대표자 사진
																</th>
																
																<td>
																	<div class="container">
																		<div class="row">
																			<c:if test="${not empty startHpMngrEmpForm.file3Info && startHpMngrEmpForm.file3Info.fileId > 0}">
																				<img class="img-thumbnail" style="max-width: 200px; max-height: 200px;" src="${startHpMngrEmpForm.file3Info.fileServletUrl}" onerror="this.src='/assets/images/noImage300x300.jpg'" />	
																				<br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="${APP_PATH}/file/download/uu/${startHpMngrEmpForm.file3Info.fileUUID}" title="다운로드">${startHpMngrEmpForm.file3Info.fileOriginalName }</a>
																			</c:if>
																		</div>
																	</div>
																	
																	<div class="container">
																		<div class="row">
																			<%-- <a href="${APP_PATH}/file/download/uu/${startHpMngrForm.file3Info.fileUUID}" title="다운로드">${startHpMngrForm.file2Info.fileOriginalName }</a> --%>
																			<input type="file" id="file3" name="file3" class="col-form-label"/>
																			<span class="bg-light font-weight-bold text-info">- jpg, jpeg, gif, png, bmp 파일만 업로드할 수 있습니다.</span>
																		</div>
																	</div>
																</td>
															</tr>
															
															<tr>
																<th scope="row" class="align-middle table-active text-center">
																	노출 여부
																</th>
																<td>
																	<div class="container">
																		<div class="row">
																		<div class="custom-control custom-radio custom-control-inline">
																			<form:radiobutton path="expsrYn" id="expsrYnY" class="custom-control-input" value="Y"/>
																			<label for="expsrYnY" class="custom-control-label"> Y</label>
																		</div>
																		<div class="custom-control custom-radio custom-control-inline">
																			<form:radiobutton path="expsrYn" id="expsrYnN" class="custom-control-input" value="N"/>
																			<label for="expsrYnN" class="custom-control-label"> N</label>
																		</div>
																		<form:errors path="expsrYn" element="div" cssClass="text-danger col-form-label ml-4" />
																		</div>
																	</div>
																</td>
															</tr>
														</tbody>
													</table>
												</div>
					            	 		</div>
					            	 	</div>
					            	 	<div id="adminMember" class="mfp-hide">
											<h5 class="modal-title mt-0">관리자목록</h5>
											<hr>
											<div id="memberList">
											
											</div>
										</div>
					            	 	<!-- End card 1. -->
					            	 	
					            	 	<!-- End card 2. -->
					            	 	<div class="card">
											<div class="card-body">
							            	 	<div class="table-responsive-sm">
							            	 		<c:if test="${formMode == 'INSERT'}">
														<button type="button" onclick="fn_save()" class="btn btn-primary waves-effect waves-light">저장</button>
													</c:if>
							            	 		<c:if test="${formMode == 'UPDATE'}">
														<button type="button" onclick="fn_save()" class="btn btn-primary waves-effect waves-light">수정</button>
													</c:if>
													<a href="${ADMIN_PATH}/startHpMngr/list.do" class="btn btn-secondary waves-effect m-l-5">목록</a>
												</div>
											</div>
										</div>
					            	</form:form>
					            	
					            	<br/>
					            	 <div class="card">
					            	 	<div class="card-header">
					            			■ 임직원 정보
					            	 	</div>
						            </div>	 
						            <div class="card-body">
					            	 	<div class="table-responsive-sm">
											<table class="table table-bordered">
												<colgroup>
												</colgroup>
												
												<thead>
													<tr>
														<th scope="row" class="align-middle table-active text-center">
															직위
														</th>
														<th scope="row" class="align-middle table-active text-center">
															이름 <span class="text-danger">*</span>
														</th>
														<th scope="row" class="align-middle table-active text-center">
															담당업무
														</th>
														<!-- 
														<th scope="row" class="align-middle table-active text-center">
															회사 전화번호
														</th>
														 -->
														<th scope="row" class="align-middle table-active text-center">
															휴대폰번호
														</th>
														<th scope="row" class="align-middle table-active text-center">
															이메일
														</th>
														<th scope="row" class="align-middle table-active text-center">
															임직원 ID
														</th>
														<th scope="row" class="align-middle table-active text-center">
															관리
														</th>
													</tr>
												</thead>
												
												<tbody id="emp_tbody">
													<c:forEach items="${list2 }" var="item" varStatus="vs">
														<c:if test="${item.rprsntvYn eq 'N' }">
															<tr>
																<td>
																	<input type="text" name="ofcps" id="ofcps${item.empId }" placeholder="직위" class="form-control col-sm-11 mt-4" maxlength="20" value="${item.ofcps }"/>
																</td>
																<td>
																	<input type="text" name="empNm" id="empNm${item.empId }" placeholder="이름" class="form-control col-sm-11 mt-4" maxlength="40" value="${item.empNm }" />
																</td>
																<td>
																	<input type="text" name="chrgJob" id="chrgJob${item.empId }" placeholder="담당 업무" class="form-control col-sm-11 mt-4" maxlength="20" value="${item.chrgJob }" />
																</td>
																<td>
																	노출: 
																	<div class="custom-control custom-radio custom-control-inline mt-2 mb-2">
																		<input type="radio" name="phoneExpsrYn${item.empId }" id="phoneExpsrYnY${item.empId }" class="custom-control-input" value="Y" <c:if test="${item.phoneExpsrYn == 'Y'}">checked</c:if>>
																		<label for="phoneExpsrYnY${item.empId }" class="custom-control-label">Y</label>
																	</div>
																	<div class="custom-control custom-radio custom-control-inline mt-2 mb-2">
																		<input type="radio" name="phoneExpsrYn${item.empId }" id="phoneExpsrYnN${item.empId }" class="custom-control-input" value="N" <c:if test="${item.phoneExpsrYn == 'N'}">checked</c:if>>
																		<label for="phoneExpsrYnN${item.empId }" class="custom-control-label">N</label>
																	</div>
																	<br/>
																	<input type="text" name="phone" id="phone${item.empId }" placeholder="'-'를 포함해 입력해주세요." class="form-control col-sm-11" maxlength="13" value="${item.phone }" />
																</td>
																<td>
																	노출: 
																	<div class="custom-control custom-radio custom-control-inline mt-2 mb-2">
																		<input type="radio" name="emailExpsrYn${item.empId }" id="emailExpsrYnY${item.empId }" class="custom-control-input" value="Y" <c:if test="${item.emailExpsrYn == 'Y'}">checked</c:if>>
																		<label for="emailExpsrYnY${item.empId }" class="custom-control-label">Y</label>
																	</div>
																	<div class="custom-control custom-radio custom-control-inline mt-2 mb-2">
																		<input type="radio" name="emailExpsrYn${item.empId }" id="emailExpsrYnN${item.empId }" class="custom-control-input" value="N" <c:if test="${item.emailExpsrYn == 'N'}">checked</c:if>>
																		<label for="emailExpsrYnN${item.empId }" class="custom-control-label">N</label>
																	</div>
																	<br/>
																	<input type="text" name="email" id="email${item.empId }" placeholder="'@' 를 포함해 입력해주세요." class="form-control col-sm-11" maxlength="maxlength="100" value="${item.email }" />
																</td>
																<td>
																	쪽지수신: 
																	<div class="custom-control custom-radio custom-control-inline mt-2 mb-2">
																		<input type="radio" name="seoulIdMsgExpsrYn${item.empId }" id="seoulIdMsgExpsrYnY${item.empId }" class="custom-control-input" value="Y" <c:if test="${item.seoulIdMsgExpsrYn == 'Y'}">checked</c:if>>
																		<label for="seoulIdMsgExpsrYnY${item.empId }" class="custom-control-label">Y</label>
																	</div>
																	<div class="custom-control custom-radio custom-control-inline mt-2 mb-2">
																		<input type="radio" name="seoulIdMsgExpsrYn${item.empId }" id="seoulIdMsgExpsrYnN${item.empId }" class="custom-control-input" value="N" <c:if test="${item.seoulIdMsgExpsrYn == 'N'}">checked</c:if>>
																		<label for="seoulIdMsgExpsrYnN${item.empId }" class="custom-control-label">N</label>
																	</div>
																	<br/>
																	
																	<input type="text" name="seoulId" id="seoulId${item.empId }" placeholder="임직원 ID" class="form-control col-sm-11" maxlength="15" value="${item.seoulId }" readonly/>
																	<%-- 
																	<select id="seoulId${item.empId }" name="seoulId" class="form-control col-sm-11">
																		<option value="">선택</option>
																	</select>
																	 --%>	
																</td>
																<td>
																	사용자 노출: 
																	<div class="custom-control custom-radio custom-control-inline mt-2 mb-2">
																		<input type="radio" name="expsrYn${item.empId }" id="expsrYnY${item.empId }" class="custom-control-input" value="Y" <c:if test="${item.expsrYn == 'Y'}">checked</c:if>>
																		<label for="expsrYnY${item.empId }" class="custom-control-label">Y</label>
																	</div>
																	<div class="custom-control custom-radio custom-control-inline mt-2 mb-2">
																		<input type="radio" name="expsrYn${item.empId }" id="expsrYnN${item.empId }" class="custom-control-input" value="N" <c:if test="${item.expsrYn == 'N'}">checked</c:if>>
																		<label for="expsrYnN${item.empId }" class="custom-control-label">N</label>
																	</div>
																	<br/>
																	<button type="button" class="btn btn-sm ml-2 btn-primary waves-effect waves-light" data-empid="${item.empId }" onclick="fnUpdate(this);">수정</button>
																	<button type="button" class="btn btn-sm ml-2 btn-primary waves-effect waves-light" data-empid="${item.empId }" onclick="fnDelete(this);">삭제</button>
																</td>
															</tr>
														</c:if>
													</c:forEach>
												</tbody>
												
											<form name="startHpMngrEmpssForm" id="startHpMngrEmpssForm" action="" method="">
												<input type="hidden" id="compId2" name="compId2" value="${startHpMngrEmpForm.compId}" />
												<input type="hidden" id="tel0" name="tel" value="000" />
												<input type="hidden" id="rprsntvYn0" name="rprsntvYn" value="N" />
												<tbody id="emp_tbody">
													<tr>
														<td>
															<input type="text" name="ofcps" id="ofcps0" placeholder="직위" class="form-control col-sm-11 mt-4" maxlength="20" value="${item.ofcps }" />
														</td>
														<td>
															<input type="text" name="empNm" id="empNm0" placeholder="이름" class="form-control col-sm-11 mt-4" maxlength="40" value="${item.empNm }" />
														</td>
														<td>
															<input type="text" name="chrgJob" id="chrgJob0" placeholder="담당 업무" class="form-control col-sm-11 mt-4" maxlength="20" value="${item.chrgJob }" />
														</td>
														<td>
															노출: 
															<div class="custom-control custom-radio custom-control-inline mt-2 mb-2">
																<input type="radio" name="phoneExpsrYn" id="phoneExpsrYnY0" class="custom-control-input" value="Y" >
																<label for="phoneExpsrYnY0" class="custom-control-label">Y</label>
															</div>
															<div class="custom-control custom-radio custom-control-inline mt-2 mb-2">
																<input type="radio" name="phoneExpsrYn" id="phoneExpsrYnN0" class="custom-control-input" value="N">
																<label for="phoneExpsrYnN0" class="custom-control-label">N</label>
															</div>
															<br/>
															<input type="text" name="phone" id="phone0" placeholder="'-' 를 포함해 입력해주세요." class="form-control col-sm-11" maxlength="13" value="${item.phone }" />
														</td>
														<td>
															노출: 
															<div class="custom-control custom-radio custom-control-inline mt-2 mb-2">
																<input type="radio" name="emailExpsrYn" id="emailExpsrYnY0" class="custom-control-input" value="Y">
																<label for="emailExpsrYnY0" class="custom-control-label">Y</label>
															</div>
															<div class="custom-control custom-radio custom-control-inline mt-2 mb-2">
																<input type="radio" name="emailExpsrYn" id="emailExpsrYnN0" class="custom-control-input" value="N">
																<label for="emailExpsrYnN0" class="custom-control-label">N</label>
															</div>
															<br/>
															<input type="text" name="email" id="email0" placeholder="'@' 를 포함해 입력해주세요." class="form-control col-sm-11" maxlength="100" value="${item.email }" />
														</td>
														<td>
															쪽지수신: 
															<div class="custom-control custom-radio custom-control-inline mt-2 mb-2">
																<input type="radio" name="seoulIdMsgExpsrYn" id="seoulIdMsgExpsrYnY0" class="custom-control-input" value="Y">
																<label for="seoulIdMsgExpsrYnY0" class="custom-control-label">Y</label>
															</div>
															<div class="custom-control custom-radio custom-control-inline mt-2 mb-2">
																<input type="radio" name="seoulIdMsgExpsrYn" id="seoulIdMsgExpsrYnN0" class="custom-control-input" value="N">
																<label for="seoulIdMsgExpsrYnN0" class="custom-control-label">N</label>
															</div>
															<br/>
															<input type="text" name="seoulId" id="seoulId0" placeholder="임직원 ID" class="form-control col-sm-11" maxlength="15" value="${item.seoulId }" readonly/>
															<!-- 
															<select id="seoulId0" name="seoulId" class="form-control col-sm-11">
																<option value="">선택</option>
															</select>
															 -->
														</td>
														<td>
															사용자 노출: 
															<div class="custom-control custom-radio custom-control-inline mt-2 mb-2">
																<input type="radio" name="expsrYn" id="expsrYnY0" class="custom-control-input" value="Y">
																<label for="expsrYnY0" class="custom-control-label">Y</label>
															</div>
															<div class="custom-control custom-radio custom-control-inline mt-2 mb-2">
																<input type="radio" name="expsrYn" id="expsrYnN0" class="custom-control-input" value="N">
																<label for="expsrYnN0" class="custom-control-label">N</label>
															</div>
															<br/>
															<button type="button" class="btn btn-secondary waves-effect m-l-5 ml-2 empssInsertBtn" >추가</button>
														</td>
													</tr>
												</tbody>
										</form>
												
												
											</table>
										</div>
									</div>
												
					            	
					            </div>
				            </div>
			            </div>
		            </div> <!-- End 입력폼 -->
		    		<!-- ============================= End 메뉴별 컨텐츠 ============================ -->
		            <br/>
		            
		            
	    		</div><!-- End container -->
	    	</div> <!-- End Page content Wrapper -->
	    	
	    </div> <!-- End content -->
    </div> <!-- End Right content here -->
<c:import url="/WEB-INF/jsp/egovframework/com/asapro/admin/layout/footer.jsp" charEncoding="UTF-8" />     