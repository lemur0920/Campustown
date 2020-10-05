<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="asapro" uri="http://www.asadal.com" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!-- header -->
<c:import url="${design.themeDir}/layout/header_member.jsp" charEncoding="UTF-8" />
<script>

jQuery(function($){
	
	//아이디 찾기 결과
	$('#inquiryBtn').on('click', function(e){
		e.preventDefault();
		
		$.ajax({
			url : GLOBAL.APP_PATH + '/member/gjf' + GLOBAL.API_PATH + '/searchMemberId'
			, type : 'post'
			, dataType: 'json'
			, data: $('#inquiryForm').serialize()
		}).done(function(result){
			if(result.serverMessage.success){
				$('#result').html('');
				$('.listWrapper').show();
				
				var html = '';
				if(result.emplyrList.length > 0){
					$.each(result.emplyrList, function(index, item){
						html += (index + 1) ;
						html += ' . ' ;
						html += item.usrId + ' (';
						html += item.regDt + ' 가입)';
						if(index+1 < result.emplyrList.length){
							html += '<br>';
						}
					});
				} else {
					html += '일치하는 회원 정보가 없습니다.' ;
				}	
				$('#idList').html(html);
			} else {
				if(result.serverMessage.text ){
					$('#result').html('<span class="text-danger">' + result.serverMessage.text + '</span>');
					//alert(result.serverMessage.text);
					
				} else {
					alert('아이디찾기 [error]');
				}
			}
		}).fail(function(result){
			alert('아이디찾기 [fail]');
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
					<form:hidden path="inquiryType" value="ID"/>
					<form:hidden path="certifiType" value="MOBILE"/>
					<div class="find_id_bk">
						<div class="tiz">휴대폰으로 찾기</div>
						<div class="con">
							<dl class="mfi_ipt">
								<dt>이름</dt>
								<dd>
									<div class="iptt_bk">
										<form:input path="userName" title="이름" placeholder="이름을 입력해주세요." autocomplete="off" />
									</div>
								</dd>
								<dt>휴대폰</dt>
								<dd>
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
									<div id="result" class=""></div>
								</dd>
							</dl>
							<div class="btb">
								<a href="#n" class="lgp_btns1 open-mp" id="inquiryBtn">확인</a>
								<a href="${APP_PATH }/gjf/login" class="lgp_btns1 oc">뒤로</a>
							</div>
							<div class="btb listWrapper" style="display:none; text-align: left;">
								
								<div id="idList"></div>
							</div> 
						</div>
					</div>
		
				</form:form>
			</div>
			
			
			<!-- <div class="message-pop">
				<div class="mp-cont">
					<p class="msg-txt">아이디, 비밀번호가 일치하지 않습니다.</p>
					<div class="mp-btns">
						<a href="#n" class="close-mp">확인</a>
					</div>
				</div>
			</div>
			<div class="mp-bg"></div> -->
			
			
	<!-- content end -->

<!-- footer -->
<c:import url="${design.footer}" charEncoding="UTF-8" />