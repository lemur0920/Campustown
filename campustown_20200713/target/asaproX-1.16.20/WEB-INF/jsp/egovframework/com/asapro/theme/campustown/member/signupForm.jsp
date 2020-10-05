<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="asapro" uri="http://www.asadal.com" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!-- header -->
<c:import url="${design.themeDir}/layout/header_member.jsp" charEncoding="UTF-8" />
<script>

jQuery(function($){
	
	//회원가입
	$('.submitBtn').on('click', function(e){
		e.preventDefault();
		$('#sbcsBeanForm').submit();
	});
	
	//아이디 중복체크
	$('#idChkBtn').on('click', function(e){
		e.preventDefault();
		var usrId = $.trim($("#usrId").val());
		if (usrId == "") {
			$('#userIdCheckResult').html('<span class="text-danger">아이디를 입력하세요.</span>');
			$('#usrId\\.errors').text('');
			//alert("아이디를 입력하세요.");
			$("#usrId").focus();
			return false;
		} else {
			if (!isEmail(usrId)) {
				$('#userIdCheckResult').html('<span class="text-danger">이메일 주소가 유효하지 않습니다.</span>');
				//alert("이메일 주소가 유효하지 않습니다.");
				$("#usrId").focus();
			    return false;
			}
		}
		
		$.ajax({
			url : GLOBAL.APP_PATH + '/member/gjf' + GLOBAL.API_PATH + '/checkUserId'
			, type : 'get'
			, dataType: 'json'
			, data : {
				userId : usrId
			}
		}).done(function(data){
			$('#usrId\\.errors').text('');
			if( data.success ){
				$('#userIdCheckResult').html('<span class="text-success">사용가능한 아이디입니다.</span>');
			} else {
				if( data.text ){
					$('#userIdCheckResult').html('<span class="text-danger">' + data.text + '</span>');
				} else {
					$('#userIdCheckResult').html('<span class="text-danger">사용할 수 없는 아이디입니다.</span>');
				}
			}
		}).fail(function(){
			alert('중복확인을 실패하였습니다.[fail]');
		});
		$("#usrId").focus();
		
	}); 
	
	
	//인증번호 SMS 발송
	$('.btnSendCertiNum').on('click', function(e){
		e.preventDefault();
		
		$('#userMobile1\\.errors').text('');
		$('#userMobile2\\.errors').text('');
		$('#userMobile3\\.errors').text('');

		var mobile1 = $.trim($('#userMobile1').val());
		var mobile2 = $.trim($('#userMobile2').val());
		var mobile3 = $.trim($('#userMobile3').val());
		
		if(mobile1 == null || mobile1 == '' || mobile2 == null || mobile2 == '' || mobile3 == null || mobile3 == '' ){
			$('#sendSmsResult').html('<span class="text-danger">휴대폰번호를 입력해주세요.</span>');
			$("#userMobile2").focus();
		    return false;
		}
		$.ajax({
			url : GLOBAL.APP_PATH + '/member/gjf' + GLOBAL.API_PATH + '/sendCertiNum'
			, type : 'post'
			, dataType: 'json'
			, data: {
				userMobile1 : mobile1
				, userMobile2 : mobile2
				, userMobile3 : mobile3
				, ${SECURITY_TOKEN_NAME} : '${SECURITY_TOKEN}'
			}
		}).done(function(result){
			if(result.success){
				$('#sendSmsResult').html('<span class="text-success">' + result.text + '</span>');
				$('#submitCertiNumResult').html('');
				//alert(result.text);
				$("#certiNum").focus();
				requesterChk = 'click';
				chkOTPLimitTime(180, 'click');
			} else {
				if(result.text ){
					$('#sendSmsResult').html('<span class="text-danger">' + result.text + '</span>');
					//alert(result.text);
					/* if(result.resultCode == '21'){//발송횟수재한
						location.href = '${APP_PATH}/certificate/request';
					} */
				} else {
					alert('인증번호발송 실패 [error]');
				}
			}
		}).fail(function(result){
			alert('인증번호발송 실패 [fail]');
		});
		$("#userMobile2").focus();
	});
	
	//sms 인증번호 제출
	$('.btnSubmitCertiNum').on('click', function(e){
		e.preventDefault();
		var certiNum = $('#certiNum').val();
		if(certiNum == null || certiNum == ''){
			$('#submitCertiNumResult').html('<span class="text-danger">휴대폰으로 받으신 인증번호를 입력하세요</span>');
			return false;
		}
		
		$.ajax({
			url : GLOBAL.APP_PATH + '/member/gjf' + GLOBAL.API_PATH + '/submitCertiNum'
			, type : 'post'
			, dataType: 'json'
			, data: {
				certiNum : certiNum
				, ${SECURITY_TOKEN_NAME} : '${SECURITY_TOKEN}'
			}
		}).done(function(result){
			if(result.success){
				$('#submitCertiNumResult').html('<span class="text-success">' + result.text + '</span>');
				$('#timer').html('');
				requesterChk = 'click';
				$('#smsAouth').val('Y');
			} else {
				if(result.text ){
					$('#submitCertiNumResult').html('<span class="text-danger">' + result.text + '</span>');
				} else {
					$('#submitCertiNumResult').html('<span class="text-danger">인증실패 [error]</span>');
					//alert('인증실패 [error]');
				}
			}
		}).fail(function(result){
			$('#submitCertiNumResult').html('<span class="text-danger">인증실패 [fail]</span>');
			//alert('인증실패 [fail]');
		});
		$("#certiNum").focus();
	});
	
	
	/*
	인증유효시간 타이머
	*/
	var requesterChk = 'click';
	
	function chkOTPLimitTime(limitsec, requester) {
		if(requester == 'auto' && requesterChk == 'click'){
    		return;
    	}
		
	    remain = limitsec - 1;
	
	    if (remain >= -1) {
	        mod = limitsec % (24 * 3600);
	        mod = mod % 3600;
	        min = Math.floor(mod / 60);
	        sec = mod % 60;
	        if (sec < 10) {
	            sec = '0' + sec;
	        }
	
	        $('#timer').html(min + ":" + sec);
	
	        if (remain == -1) {
	        	$('#timer').html("제한시간이 만료되었습니다.");
	            //alert("제한시간이 만료되었습니다.");
	            //location.href = '${APP_PATH}/certificate/request';
	        }
	        else {
	            timerid = setTimeout(function(){
		            	if(requester == 'click' ){
				        	requesterChk = 'auto';
			        	}
		            	chkOTPLimitTime(remain, 'auto')
	            	}
	            , 1000);
	        }
	    }
	}
	
	
	
	
});

//휴대폰 본인인증
function fnPopup(){
	window.open('', 'popupChk', 'width=500, height=550, top=100, left=100, fullscreen=no, menubar=no, status=no, toolbar=no, titlebar=yes, location=no, scrollbar=no');
	document.form_chk.action = "/nice/checkplus_main.jsp";
	document.form_chk.target = "popupChk";
	document.form_chk.submit();
}

</script>
<!-- 휴대폰 본인인증 -->
<form action="" name="form_chk"></form>

	<!-- content -->
	
	<!--  페이지별 내용 -->
			
	<div id="container">
		<div class="">

			<div class="scontents_area">
				
				<!--내용 시작-->
				<div class="logo-head"><a href="#n"><img src="${design.resource }/images/main/nlogo.png" alt="경기청년포털 로고"></a></div>
				
				<form:form modelAttribute="sbcsBeanForm" action="${APP_PATH}/member/gjf/signup" method="post">
					${SECURITY_TOKEN_TAG}
					<form:hidden path="snsSeCd"/>
					<form:hidden path="snsInnb"/>
					
					<div class="jion-box2">
						<div class="jion-table2">
							<table>
								<caption>회원정보 입력</caption>
								<colgroup>
									<col style="width: 25%" />
									<col style="width: auto" />
								</colgroup>
								<tbody>
									<%-- <tr>
										<th scope="row">SNS로 가입하기</th>
										<td>
											<a href="#n" class="sns-btn"><img src="${design.resource }/images/sub/sns_join01.gif" alt="페이스북 로그인" /></a>
											<a href="#n" class="sns-btn"><img src="${design.resource }/images/sub/sns_join02.gif" alt="KAKAO 로그인" /></a>
											<a href="#n" class="sns-btn"><img src="${design.resource }/images/sub/sns_join03.gif" alt="NAVER 로그인" /></a>
										</td>
									</tr> --%>
									<tr>
										<th scope="row"><span>*</span>이름</th>
										<td>
											<form:input path="nm" title="이름을 입력하세요." readonly="true" />
											<a href="#n" onclick="fnPopup();" class="jion-tb-btn02" id="certi_btn">휴대폰 본인인증</a>
											<form:errors path="nm" element="div" cssClass="text-danger" />
										</td>
									</tr>
									<tr>
										<th scope="row"><span>*</span>아이디</th>
										<td>
											<form:input path="usrId" title="아이디 입력하세요." placeholder="이메일 주소 형식으로 입력하세요" />
											<input type="hidden" id="duplCnt" name="duplCnt" value="-1">
											<a href="#n" class="jion-tb-btn01" id="idChkBtn">중복확인</a>
											<form:errors path="usrId" element="div" cssClass="text-danger" />
											<div id="userIdCheckResult" class=""></div>
											<!-- <p class="red-font">*이메일 주소 형식으로 입력하세요</p> -->
										</td>
									</tr>
									<tr>
										<th scope="row"><span>*</span>비밀번호</th>
										<td>
											<form:password path="pswd" title="비밀번호를 입력하세요." maxlength="20" autocomplete="off" placeholder="영문대소,숫자,특수문자 각 1개 이상 포함하며,9자 이상 20자 이내" />
											<form:errors path="pswd" element="div" cssClass="text-danger" />
											<!-- <p class="red-font">*비밀번호는 영문자,숫자,특수문자 가각 1개 이상 포함하며,8자 이상 20자 이내여야 합니다.</p> -->
										</td>
									</tr>
									<tr>
										<th scope="row"><span>*</span>비밀번호 확인</th>
										<td>
											<form:password path="pswdCheck" title="비밀번호 재확인"  maxlength="20" autocomplete="off" />
											<form:errors path="pswdCheck" element="div" cssClass="text-danger" />
										</td>
									</tr>
									<tr>
										<th scope="row"><span>*</span>비밀번호 찾기 이메일</th>
										<td>
											<form:input path="usrEmail" title="비밀번호 분실시 이메일" />
											<form:errors path="usrEmail" element="div" cssClass="text-danger" />
										</td>
									</tr>
									<tr>
										<th scope="row"><span>*</span>휴대폰</th>
										<td>
											<%-- <form:select path="userMobile1" title="전화번호 첫 3자리 입력"  cssClass="p-slct" >
												<form:options items="${mobile1CodeList }" itemLabel="codeName" itemValue="codeId"/>
											</form:select> --%>
											<form:input path="userMobile1" title="전화번호 중간 3자리 입력" class="p-num" maxlength="3" readonly="true"/>
											-
											<form:input path="userMobile2" title="전화번호 중간 4자리 입력" class="p-num" maxlength="4" readonly="true"/>
											-
											<form:input path="userMobile3" title="전화번호 마지막 4자리 입력" class="p-num" maxlength="4" readonly="true"/>
											<!-- <a href="#n" class="jion-tb-btn02 btnSendCertiNum">인증번호 받기</a> -->
											<form:errors path="userMobile1" element="div" cssClass="text-danger" />
											<form:errors path="userMobile2" element="div" cssClass="text-danger" />
											<form:errors path="userMobile3" element="div" cssClass="text-danger" />
											<div id="sendSmsResult" class=""></div>
										</td>
									</tr>
									<!-- <tr>
										<th scope="row">인증번호 입력</th>
										<td>
											<input name="certiNum" id="certiNum" type="text" title="인증번호 입력" maxlength="6"/>
											<a href="#n" class="jion-tb-btn01 btnSubmitCertiNum">확인</a>
											<div id="submitCertiNumResult" class=""></div>
											<div id="timer" class="text-danger"></div>
											<input name="smsAouth" id="smsAouth" type="hidden" />
										</td>
									</tr> -->
									<%-- <tr class="bod">
										<th scope="row">추천인 아이디</th>
										<td>
											<form:input path="invtId" title="추천인 아이디 입력" />
										</td>
									</tr> --%>
								</tbody>
							</table>
						</div>
						<div class="ct_btn">
							<a href="#n" class="jion-next-btn open-mp submitBtn">가입하기</a>
							<a href="${APP_PATH}/member/gjf/agreement" class="jion-next-btn2">뒤로</a>
						</div>
							
					</div>
				</form:form>
				<!--내용 끝-->
			</div>
			
			
	<!-- content end -->

<!-- footer -->
<c:import url="${design.footer}" charEncoding="UTF-8" />