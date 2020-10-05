<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="asapro" uri="http://www.asadal.com" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!-- header -->
<c:import url="${design.themeDir}/layout/header_member.jsp" charEncoding="UTF-8" />
<script>

jQuery(function($){
	//전송방법선택
	$('.typeList a').on('click', function(e){
		e.preventDefault();
		
		var type = $(this).data('type');
		var par = $(this).parent('li');
		var ind = par.index();
		
		$(".typeList li").removeClass('on');
		par.addClass('on');
		
		$('.fpw_tbc .type').removeClass('on');
		$('.fpw_tbc .' + type).addClass('on');
		
		if(type == 'mail'){
			$('#userMobile1').val('010');
			$('#userMobile2').val('');
			$('#userMobile3').val('');
			$('#certifiType').val('EMAIL');
		}else if(type == 'mobile'){
			$('#userEmail').val('');
			$('#certifiType').val('MOBILE');
		}
		
		$('#result').html('');
	})
	
	
	//임시비밀번호 발송 및 결과
	$('#inquiryBtn').on('click', function(e){
		e.preventDefault();
		
		$.ajax({
			url : GLOBAL.APP_PATH + '/member/gjf' + GLOBAL.API_PATH + '/searchMemberPw'
			, type : 'post'
			, dataType: 'json'
			, data: $('#inquiryForm').serialize()
		}).done(function(result){
			if(result.success){
				$('#result').html('<span class="text-success">' + result.text + '</span>');
				//location.href =  GLOBAL.APP_PATH + '/gjf/login';
			} else {
				if(result.text ){
					$('#result').html('<span class="text-danger">' + result.text + '</span>');
					//alert(result.text);
					
				} else {
					alert('비밀번호 찾기 [error]');
				}
			}
		}).fail(function(result){
			alert('비밀번호 찾기 [fail]');
		});
	});
	
});

</script>


	<!-- content -->
	
	<!--  페이지별 내용 -->
			
	<div id="container">
		<div class="">
		
			<div class="login_wp">
				<div class="logo">
					<a href="#n"><img src="${design.resource}/images/main/nlogo.png" alt="경기청년포털 로고"></a>
				</div>
				<form:form modelAttribute="inquiryForm"  method="post">
					${SECURITY_TOKEN_TAG}
					<form:hidden path="inquiryType" value="PW"/>
					<form:hidden path="certifiType" value="EMAIL"/>
					
					<div class="find_id_bk">
						<div class="tiz">비밀번호 찾기</div>
						<div class="con">
		
							<div class="fpw_tab typeList">
								<ul class="fpw_tls">
									<li class="on"><a href="#n" data-type="mail">이메일로 찾기</a></li>
									<li><a href="#n" data-type="mobile">휴대폰으로 찾기</a></li>
								</ul>
							</div>
							<div class="fpw_tbc">
								<div class="on">
									<div class="fpw_bk">
										<div class="iptt_bk">
											<form:input path="userId" title="아이디" placeholder="아이디 (이메일 주소 형식으로 입력)"/>
										</div>
										<div class="iptt_bk">
											<form:input path="userName" title="이름" placeholder="이름"/>
										</div>
										<div class="iptt_bk mail type on">
											<form:input path="userEmail" title="이메일" placeholder="이메일 주소" />
										</div>
										<div class="iptt_bk mobile type">
											<div class="iptt_bk c3">
												<form:select path="userMobile1" title="휴대폰 첮자리">
													<form:options items="${mobile1CodeList }" itemLabel="codeName" itemValue="codeId"/>
												</form:select>
											</div>
											-
											<div class="iptt_bk c3">
												<form:input path="userMobile2" title="휴대폰 중간자리" placeholder="중간자리 입력" maxlength="4" autocomplete="off" />
											</div>
											-
											<div class="iptt_bk c3">
												<form:input path="userMobile3" title="휴대폰 뒷자리" placeholder="뒷자리 입력" maxlength="4" autocomplete="off" />
											</div>
											<!-- <input type="text" title="휴대폰 번호" placeholder="휴대폰 번호(-없이 입력)" > -->
										</div>
										<div id="result" class=""></div>
									</div>
									<div class="fpw_bt">
										<a href="#n" id="inquiryBtn">임시 비밀번호 전송</a>
									</div>
								</div>
							</div>
		
							<ul class="fpw_atx">
								<li>임시 비밀번호 버튼을 클릭하면 기존 입력된 비밀번호는 초기화됩니다.</li>
								<li>이메일 or 휴대폰으로 전송 받으신 임시비밀번호를 사용하여 로그인하시고, 비밀번호는 반드시 새로 지정하시기 바랍니다.</li>
							</ul>
						</div>
					</div>
				</form:form>
			</div>
		
		
			
			
	<!-- content end -->

<!-- footer -->
<c:import url="${design.footer}" charEncoding="UTF-8" />