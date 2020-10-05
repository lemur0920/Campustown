<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="asapro" uri="http://www.asadal.com" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!-- header -->
<c:import url="${design.themeDir}/layout/header_member.jsp" charEncoding="UTF-8" />
<script>

jQuery(function($){
	//로그인버튼
	$('.btn_login').on('click', function(e){
		e.preventDefault();
		$('#loginForm').submit();
	});
	
	//아이디저장
	var userInputId = $.cookie("userInputId");//저장된 쿠기값 가져오기
    $("input[name='loginId']").val(userInputId); 
     
    if($("input[name='loginId']").val() != ""){ // 그 전에 ID를 저장해서 처음 페이지 로딩
                                           // 아이디 저장하기 체크되어있을 시,
        $("#idSaveCheck").attr("checked", true); // ID 저장하기를 체크 상태로 두기.
    }
     
    $("#idSaveCheck").change(function(){ // 체크박스에 변화가 발생시
        if($("#idSaveCheck").is(":checked")){ // ID 저장하기 체크했을 때,
            var userInputId = $("input[name='loginId']").val();
            $.cookie("userInputId", userInputId, {expires : 7}); // 7일 동안 쿠키 보관
        }else{ // ID 저장하기 체크 해제 시,
        	 $.cookie("userInputId", '');
        }
    });
     
    // ID 저장하기를 체크한 상태에서 ID를 입력하는 경우, 이럴 때도 쿠키 저장.
    $("input[name='loginId']").keyup(function(){ // ID 입력 칸에 ID를 입력할 때,
        if($("#idSaveCheck").is(":checked")){ // ID 저장하기를 체크한 상태라면,
            var userInputId = $("input[name='loginId']").val();
            $.cookie("userInputId", userInputId, {expires : 7}); // 7일 동안 쿠키 보관
        }
    });
});

</script>


	<!-- content -->
	<div id="content" style="background:#f9f9f9;padding:50px 0;" >
		<div class="area bg-white">

			<div class="login-box-s">
				<a href="#none" class="login-s"><img src="${design.resource}/images/logo.png" alt="" /></a>
				<div class="p-text1">
					<span class="line"></span>
					<p><b>서울캠퍼스타운  홈페이지</b>를  방문해주셔서 감사합니다. <br>로그인하시면 보다 편리하게 서비스 이용이 가능합니다.</p>
				</div>
				<form:form modelAttribute="loginForm" action="${APP_PATH}/loginProc" method="post">
					${SECURITY_TOKEN_TAG}
					<%-- <form:hidden path="returnUrl" /> --%>
					
					<form:input path="loginId" placeholder="아이디" title="아이디" style="color: #717171; " autocomplete="off" class="login-box-input input-id"/>
					<form:errors path="loginId" element="div" cssClass="text-danger" />
					
					<form:password path="loginPassword" placeholder="비밀번호" title="비밀번호" style="color: #717171; " autocomplete="off" class="login-box-input input-pw"/>
					<form:errors path="loginPassword" element="div" cssClass="text-danger" />
				</form:form>		
						
				<div class="checkID">
					<span><input type="checkbox" title="아이디 저장 선택" id="idSaveCheck" class="input-cb" > <label for="idSaveCheck">아이디 저장</label></span>
				</div>
				<a href="#n" class="btn_login button01">로그인</a>
				<!--
				<div class="button02">
					<ul>
						<li><a href="#none">회원가입</a></li>
						<li><a href="#none">비밀번호 찾기</a></li>
					</ul>
				</div>
				-->
				<p class="p-text2">서울캠퍼스타운의 회원 권한은 캠퍼스타운, 창업팀에게만 부여됩니다.<br>회원가입은 해당 대학의 캠퍼스타운 담당자에게 문의하시길 바랍니다. </p>
				<div class="mail-txt-box">
					<p class="mail-txt">ty0223@expertdb.ai</p>
				</div>
				
			</div>
			
		<!-- </div> -->  <!-- END .area -->
	<!-- </div> -->   <!-- END #content -->
	
	<!-- content end -->

<!-- footer -->
<c:import url="${design.footer}" charEncoding="UTF-8" />