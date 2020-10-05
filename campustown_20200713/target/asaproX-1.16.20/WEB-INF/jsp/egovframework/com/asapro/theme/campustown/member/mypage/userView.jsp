<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="asapro" uri="http://www.asadal.com" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!-- header -->
<c:import url="${design.header}" charEncoding="UTF-8" />
<script>

jQuery(function($){
	
	//init 
	$('.mobileform').hide();
	$('.cancelMobile').hide();
	
	
	
	
	
	//엔터 서브밋 막기
	$('input[type=password]').on('keydown', function(e){
		if(e.keyCode === 13){
			e.preventDefault();
		}
	});

	
	//휴대폰 수정
	$('.modifyMobile').on('click', function(e){
		e.preventDefault();
		
		$('.mobileform').show();
		$('.cancelMobile').show();
		$('.modifyMobile').hide();
	});
	//휴대폰 수정 취소
	$('.cancelMobile').on('click', function(e){
		e.preventDefault();
		
		$('.mobileform').hide();
		$('.cancelMobile').hide();
		$('.modifyMobile').show();
		$('[name=userMobile1]').val('010');
		$('[name=userMobile2]').val('');
		$('[name=userMobile3]').val('');
		$('#sendSmsResult').html('');
		$('#submitCertiNumResult').html('');
	});
	
	var mobile1;
	var mobile2;
	var mobile3;
	//인증번호 SMS 발송
	$('.btnSendCertiNum').on('click', function(e){
		e.preventDefault();
		
		mobile1 = $.trim($('[name=userMobile1]').val());
		mobile2 = $.trim($('[name=userMobile2]').val());
		mobile3 = $.trim($('[name=userMobile3]').val());
		
		if(mobile1 == null || mobile1 == '' || mobile2 == null || mobile2 == '' || mobile3 == null || mobile3 == '' ){
			$('#sendSmsResult').html('<span class="text-danger">휴대폰번호를 입력해주세요.</span>');
			$("[name=userMobile2]").focus();
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
				$(".my_pop_iz").show();
				$(".mpop_bg").show();
				//requesterChk = 'click';
				//chkOTPLimitTime(180, 'click');
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
				updateUserMobile();
					
					//$('#submitCertiNumResult').html('<span class="text-success">' + result.text + '</span>');
					//$('#timer').html('');
				
				//requesterChk = 'click';
				//$('#smsAouth').val('Y');
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
	
	//휴대폰번호 수정처리
	function updateUserMobile(){
		$.ajax({
			url : GLOBAL.APP_PATH + '/member/gjf/mypage' + GLOBAL.API_PATH + '/updateMobile'
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
				swal({
		            type: 'success',
		            title: '수정완료',
		            html : result.text
	
		        }).then(function(){
					location.reload();
		        });
				//$('#submitCertiNumResult').html('<span class="text-success">' + result.text + '</span>');
				//$('#timer').html('');
				//return true;
				//requesterChk = 'click';
				//$('#smsAouth').val('Y');
			} else {
				if(result.resultCode == '51'){
					//비밀번호 확인 세션이 없어서 마이페이지 메인으로 리다이렉트
					location.href = GLOBAL.APP_PATH + '/member/gjf/mypage/index';
				}
				if(result.text ){
					$('#submitCertiNumResult').html('<span class="text-danger">' + result.text + '</span>');
				} else {
					$('#submitCertiNumResult').html('<span class="text-danger">수정실패 [error]</span>');
					//alert('수정실패 [error]');
				}
			}
		}).fail(function(result){
			$('#submitCertiNumResult').html('<span class="text-danger">수정실패 [fail]</span>');
			//alert('수정실패 [fail]');
		});
		//return false;
	} 
	
	
	//비밀번호 수정
	$('.updatePwdBtn').on('click', function(e){
		e.preventDefault();
		
		$.ajax({
			url : GLOBAL.APP_PATH + '/member/gjf/mypage' + GLOBAL.API_PATH + '/updatePassword'
			, type : 'post'
			, dataType: 'json'
			, data: $('#gjfPasswordForm').serialize()
		}).done(function(result){
			if(result.success){
				//$('#pwdError').html('<span class="text-success">' + result.text + '</span>');
				$('#pwdError').html('');
				$('[name=oldPassword]').val('');
				$('[name=newPassword]').val('');
				$('[name=newPasswordCheck]').val('');
				$(".mp_aft_pop").hide();
				$(".mpop_bg").hide();
				
				swal({
		            type: 'success',
		            title: '수정완료',
		            html : result.text
	
		        })
			} else {
				if(result.text ){
					$('#pwdError').html('<span class="text-danger">' + result.text + '</span>');
				} else {
					$('#pwdError').html('<span class="text-danger">변경실패 [error]</span>');
					//alert('변경실패 [error]');
				}
			}
		}).fail(function(result){
			$('#pwdError').html('<span class="text-danger">변경실패 [fail]</span>');
			//alert('변경실패 [fail]');
		});
		
	});
	
	//취소,닫기 버튼
	$('.pw_out, .mpop_out').on('click', function(e){
		e.preventDefault();
		$('#pwdError').html('');
		$('[name=oldPassword]').val('');
		$('[name=newPassword]').val('');
		$('[name=newPasswordCheck]').val('');
	});
	
	//이메일 수정
	$('.updateEmailBtn').on('click', function(e){
		e.preventDefault();
		
		var usrEmail = $.trim($('[name=usrEmail]').val());
		
		$.ajax({
			url : GLOBAL.APP_PATH + '/member/gjf/mypage' + GLOBAL.API_PATH + '/updateEmail'
			, type : 'post'
			, dataType: 'json'
			, data: {
				usrEmail : usrEmail
				, ${SECURITY_TOKEN_NAME} : '${SECURITY_TOKEN}'
			}
		}).done(function(result){
			if(result.success){
				swal({
		            type: 'success',
		            title: '수정완료',
		            html : result.text
	
		        }).then(function(){
					location.reload();
		        });
			} else {
				if(result.text ){
					$('#emailError').html('<span class="text-danger">' + result.text + '</span>');
				} else {
					$('#emailError').html('<span class="text-danger">변경실패 [error]</span>');
					//alert('변경실패 [error]');
				}
			}
		}).fail(function(result){
			$('#emailError').html('<span class="text-danger">변경실패 [fail]</span>');
			//alert('변경실패 [fail]');
		});
		
	});
	
	
});


</script>

<!-- sidebar -->
<c:import url="${design.themeDir}/layout/sidebar.jsp" charEncoding="UTF-8" />

	<!-- content -->
	
	<div class="mpg_tab">
		<table>
			<caption>회원정보수정</caption>
			<colgroup>
				<col style="width: 110px">
				<col style="width: auto">
			</colgroup>
			<thead>
				<tr>
					<th scope="col" colspan="2">회원정보수정</th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<th scope="row">아이디</th>
					<td>${userMemberForm.userId }</td>
				</tr>
				<tr>
					<th scope="row">이름</th>
					<td>
						<div class="mname_box">
							<div class="mname_bf mname_bf1">
								<span>${userMemberForm.userName }</span>
								<!-- <a class="member_bn" href="#n">변경</a> -->
							</div>
							<!-- <div class="mname_aft mname_aft1">
								<span><input type="text" title="이름변경" placeholder="김 ㅇㅇ" onfocus="this.placeholder=''" onblur="this.placeholder='김 ㅇㅇ'" /></span>
								<a class="member_bn" href="#n">저장</a>
							</div> -->
						</div>
					</td>
				</tr>
				<tr>
					<th scope="row">연락처</th>
					<td>
						<div class="mname_box">
							<div class="mname_bf mname_bf1">
								<span>${userMemberForm.userMobileJoin }</span>
								<a class="member_bn modifyMobile" href="#n">수정</a>
								<a class="member_bn cancelMobile" href="#n">수정취소</a>
							</div>
						</div>
						
						
						<div class="mptel_box mobileform">
							<ul class="clearfix">
								<li>
									<select name="userMobile1" title="전화번호 첫 3자리 입력"  >
										<c:forEach items="${mobile1CodeList }" var="item" varStatus="vs">
											<option label="${item.codeName }" value="${item.codeId }"/>
										</c:forEach>
									</select>
								</li>
								<li>
									<input name="userMobile2" type="text" title="전화번호중간자리" maxlength="4" />
								</li>
								<li>
									<input name="userMobile3" type="text" title="전화번호마지막자리" maxlength="4" />
								</li>
							</ul>
							<a class="member_bn tel_open btnSendCertiNum" href="#n">인증</a>
						</div>
						<div id="sendSmsResult"></div>
						<div id="submitCertiNumResult"></div>
						<div class="telcomp_box mobileform">
							<span>
								<input type="text" id="certiNum" title="인증번호 입력" placeholder="인증번호 입력" onfocus="this.placeholder=''" onblur="this.placeholder='인증번호 입력'" />
							</span>
							<a class="member_bn btnSubmitCertiNum" href="#n">수정완료</a>
						</div>
					</td>
				</tr>
				<tr>
					<th scope="row">비밀번호</th>
					<td><a class="member_bn pw_aft_open" href="#n">변경</a></td>
				</tr>
				<tr>
					<th scope="row">비밀번호 찾기 이메일</th>
					<td>
						<div class="mname_box">
							<div class="mname_bf mname_bf2">
								<span class="box">
									${userMemberForm.userEmail }
								</span>
								<a class="member_bn" href="#n">변경</a>
							</div>
							<div class="mname_aft mname_aft2">
								<span class="box"><input type="text" name="usrEmail" title="비밀번호 찾기 이메일" value="${userMemberForm.userEmail }" /></span>
								<a class="member_bn updateEmailBtn" href="#n">저장</a>
							</div>
						</div>
						<div id="emailError"></div>
					</td>
				</tr>
			</tbody>
		</table>
	</div>

	<div class="mybot_btn">
		<!-- <a class="ons" href="#n">가입하기</a> -->
		<a class="offs" href="${APP_PATH }/member/gjf/mypage/index">나가기</a>
	</div>
	<!--내용 끝-->
	
	<div class="my_pop mp_aft_pop">
		<div class="mpop_top">
			비밀번호 변경
		</div>
		<div class="mpop_back2">
			<form name="gjfPasswordForm" id="gjfPasswordForm" action="${APP_PATH }/member/gjf/mypage${API_PATH }/updatePassword">
				${SECURITY_TOKEN_TAG}
				<ul class="mptxt_line2">
					<li>
						<span>비밀번호</span>
						<input type="password" name="oldPassword" title="현재 비밀번호 입력" placeholder="현재 비밀번호 입력" onfocus="this.placeholder=''" onblur="this.placeholder='현재 비밀번호 입력'" />
					</li>
					<li>
						<span>새로운 비밀번호</span>
						<input type="password" name="newPassword" title="새로운 비밀번호 입력" placeholder="새로운 비밀번호 입력" onfocus="this.placeholder=''" onblur="this.placeholder='새로운 비밀번호 입력'" />
					</li>
					<li>
						<span>새로운 비밀번호 확인</span>
						<input type="password" name="newPasswordCheck" title="새로운 비밀번호 입력" placeholder="새로운 비밀번호 입력" onfocus="this.placeholder=''" onblur="this.placeholder='새로운 비밀번호 입력'" />
					</li>
				</ul>
				<div id="pwdError"></div>
				<div class="mp_btn">
					<a class="on npd_cick updatePwdBtn" href="#n">확인</a>
					<a class="off pw_out" href="#n">취소</a>
				</div>
			</form>
		</div>
		<a class="mpop_out" href="#n"><img src="${design.resource }/images/sub/pout.png" alt="팝업닫기" /></a>
	</div>
	
	<div class="my_pop my_pop_sz my_pop_iz">
		<div class="mpop_top">
			알림
		</div>
		<div class="mpop_back_sz">
			<p class="stxt">인증번호를 발송했습니다.</p>

			<div class="mp_btn">
				<a class="on mpop_out" href="#n">확인</a>
			</div>
		</div>
		<a class="mpop_out" href="#n"><img src="${design.resource }/images/sub/pout.png" alt="팝업닫기" /></a>
	</div>

	<%-- <div class="my_pop my_pop_sz my_pop_npw">
		<div class="mpop_top">
			확인
		</div>
		<div class="mpop_back_sz">
			<p class="stxt">비밀번호를 변경하겠습니까?</p>

			<div class="mp_btn">
				<a class="on npw_open" href="#n">확인</a>
				<a class="off npw_out" href="#n">취소</a>
			</div>
		</div>
		<a class="mpop_out" href="#n"><img src="${design.resource }/images/sub/pout.png" alt="팝업닫기" /></a>
	</div> --%>
	<%-- <div class="my_pop my_pop_sz my_pop_npw2">
		<div class="mpop_top">
			알림
		</div>
		<div class="mpop_back_sz">
			<p class="stxt">비밀번호가 변경 되었습니다.</p>

			<div class="mp_btn">
				<a class="on pw_out" href="#n">닫기</a>
			</div>
		</div>
		<a class="mpop_out" href="#n"><img src="${design.resource }/images/sub/pout.png" alt="팝업닫기" /></a>
	</div> --%>

	<div class="mpop_bg"></div>
				
	<!-- content end -->

<!-- footer -->
<c:import url="${design.footer}" charEncoding="UTF-8" />