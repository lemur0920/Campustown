<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="asapro" uri="http://www.asadal.com" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!-- header -->
<c:import url="${design.header}" charEncoding="UTF-8" />
<script>

jQuery(function($){
	//엔터 서브밋 막기
	$('input[type=password]').on('keydown', function(e){
		if(e.keyCode === 13){
			e.preventDefault();
		}
	});

	
	//비밀번호 확인버튼
	$('#passwordChkBtn').on('click', function(e){
		e.preventDefault();
		
		$.ajax({
			url : GLOBAL.APP_PATH + '/member/gjf/mypage' + GLOBAL.API_PATH + '/updatePasswordCheck'
			, type : 'post'
			, dataType: 'json'
			, data: $('#passwordCheckForm').serialize()
		}).done(function(result){
			if(result.success){
				//$('#oldPasswordErrors').html('<span class="text-success">' + result.text + '</span>');
				location.href =  GLOBAL.APP_PATH + '/member/gjf/mypage/update';
			} else {
				if(result.text ){
					$('#oldPasswordErrors').html('<span class="text-danger">' + result.text + '</span>');
					//alert(result.text);
					
				} else {
					$('#oldPasswordErrors').html('<span class="text-danger">회원정보가 일치하지 않습니다.</span>');
				}
			}
		}).fail(function(result){
			$('#oldPasswordErrors').html('<span class="text-danger">비밀번호 확인 실패 fail</span>');
		});
	});
	
	//취소
	$('.pw_out, .mpop_out').on('click', function(e){
		e.preventDefault();
		
		$('input[name=oldPassword]').val('');
		$('#oldPasswordErrors').html('');
	});
	
});


</script>

<!-- sidebar -->
<c:import url="${design.themeDir}/layout/sidebar.jsp" charEncoding="UTF-8" />

	<!-- content -->
	
<%-- 	
	<div class="mypage-top">
		<p class="font1">${sessionScope.currentUser.userName }(에이올코리아)님의 방문을 환영합니다.</p>
		<p class="font2">songeunhee@campustown.com</p>
		<a href="#none" class="btn">내 정보</a>
	</div>
 --%>	
	<div class="mypage-button">
		<ul>
			<li><a href="${APP_PATH}/counsel/legal_counsel/mypage/list" class="b1">법률자문</a></li>
			<li><a href="${APP_PATH}/counsel/make_counsel/mypage/list" class="b2">시제품 제작 및 자문</a></li>
			<li><a href="${APP_PATH}/mypage/rental/reserv/list" class="b3">공유공간예약</a></li>
			<li><a href="${APP_PATH}/mypage/note/reception/list" class="b4">쪽지</a></li>
		</ul>
	</div>
	
	
	
	
<%-- 	
	
	<ul class="mp_fst clearfix">
		<li>
			<p class="ico"><img src="${design.resource }/images/sub/myico1.jpg" alt="회원정보수정" /></p>
			<div class="nys">
				<p><span>${sessionScope.currentUser.userName }</span>  님 반갑습니다.</p>
			</div>
			<p class="btn"><a class="pwpop_open" href="#n">회원정보수정</a></p>
		</li>
		<li>
			<p class="ico"><img src="${design.resource }/images/sub/myico2.jpg" alt="나의 관심정보관리" /></p>
			<div class="nys">
				<dl>
					<dt>나의 관심정보관리</dt>
					<dd>알림 설정 후 경기도 청년정책을 <br />빠르게 만나보세요.</dd>
				</dl>
			</div>
			<p class="btn"><a href="index3.htm">바로가기</a></p>
		</li>
		<li>
			<p class="ico"><img src="${design.resource }/images/sub/myico3.jpg" alt="나의 정책제안" /></p>
			<div class="nys">
				<dl>
					<dt>나의 정책제안</dt>
					<dd>나의 정책제안이 경기도 정책 <br />활용에 도움이 됩니다.</dd>
				</dl>
			</div>
			<p class="btn"><a href="${APP_PATH }/member/gjf/mypage/board/suggestion">바로가기</a></p>
		</li>
		<li>
			<p class="ico"><img src="${design.resource }/images/sub/myico4.jpg" alt="건의 게시판" /></p>
			<div class="nys">
				<dl>
					<dt>건의 게시판</dt>
					<dd>경기청년포털의 개선점을 <br />남겨주세요</dd>
				</dl>
			</div>
			<p class="btn"><a href="${APP_PATH }/member/gjf/mypage/board/proposal">바로가기</a></p>
		</li>
	</ul>
	<!--내용 끝-->
	
	<!-- 비밀번호 확인 창 -->
	<div class="my_pop my_pop_pw">
		<div class="mpop_top">
			비밀번호 확인
		</div>
		<div class="mpop_back">
			<form id="passwordCheckForm" action="${APP_PATH}/member/gjf/mypage/api/updatePasswordCheck" method="post">
				${SECURITY_TOKEN_TAG}
				<ul class="mptxt_line">
					<li>
						<span>비밀번호</span>
						<input type="hidden" name="userId" value="${currentUser.userId}" />
						<input type="password" name="oldPassword" title="현재비밀번호입력" placeholder="현재 비밀번호 입력" onfocus="this.placeholder=''" onblur="this.placeholder='현재 비밀번호 입력'" />
					</li>
				</ul>
				<div id="oldPasswordErrors" ></div>
				<div class="mp_btn">
					<a class="on" id="passwordChkBtn" href="#n">확인</a>
					<a class="off pw_out" href="#n">취소</a>
				</div>
			</form>
		</div>
		<a class="mpop_out" href="#n"><img src="${design.resource }/images/sub/pout.png" alt="팝업닫기" /></a>
	</div>
	<div class="mpop_bg"></div>
 --%>				
	<!-- content end -->

<!-- footer -->
<c:import url="${design.footer}" charEncoding="UTF-8" />