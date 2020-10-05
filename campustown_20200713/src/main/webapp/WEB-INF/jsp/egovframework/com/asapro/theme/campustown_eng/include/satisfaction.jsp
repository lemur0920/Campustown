<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script>
jQuery(function($){
	$('.satisfactionBtn').on('click', function(e){
		e.preventDefault();
		
		var menuId = $('#satisfactionForm [name=menuId]').val(); 
		if( !menuId || ( menuId != GLOBAL.menuId ) ){
			alert('페이지정보가 없어 평가를 처리할 수 없습니다.');
			return;
		}
		
		if( !$('#satisfactionForm [name=satisScore]:checked').size() ){
			alert('평가점수를 선택해 주세요.');
			return;
		} 
		
		var params = $('#satisfactionForm').serialize();
			
		$.ajax({
			url : $('#satisfactionForm').attr('action')
			, type : 'post'
			, data : params
			, context : this
		}).done(function(data){
			if( data.success ){
				alert('평가해주셔서 감사합니다.');
				location.reload();
			} else {
				alert(data.text);
			}
		}).fail(function(){
			alert('서버오류로 평가를 처리하지 못하였습니다.[failure]');
		});
		
	});
});
</script>
<div class="satisfy_area clear">
	<!-- page manager info -->
	<c:import url="${design.themeDir}/include/pageManagerInfo.jsp" charEncoding="UTF-8" />
	
	<c:if test="${currentMenu.menuUseSatisfaction }">
	<!-- satisfaction form -->
	<form id="satisfactionForm" action="${APP_PATH}/satisfaction${API_PATH}/evaluate" method="post">
		${SECURITY_TOKEN_TAG }
		<input type="hidden" name="menuId" value="${currentMenu.menuId}" />
		<div class="bot">
			<div class="sat">
				<strong>열람하신 정보에 대해 만족하십니까?</strong>
				<span><input type="radio" id="sat5" name="satisScore" value="5"><label for="sat5">아주만족</label></span>
				<span><input type="radio" id="sat4" name="satisScore" value="4"><label for="sat4">만족</label></span>
				<span><input type="radio" id="sat3" name="satisScore" value="3"><label for="sat3">보통</label></span>
				<span><input type="radio" id="sat2" name="satisScore" value="2"><label for="sat2">불만</label></span>
				<span><input type="radio" id="sat1" name="satisScore" value="1"><label for="sat1">아주불만</label></span>
			</div>
			<div class="opi">
				<div class="opi1"><strong>평가의견</strong></div>
				<div class="opi2"><textarea name="opinion.satisOpinion" id="" cols="30" rows="10"></textarea></div>
				<div class="opi3"><a href="#non" class="opi_btn satisfactionBtn">확인</a></div>
			</div>
		</div>
	</form>
	</c:if>
</div>
<%-- 
<div class="satisfaction archive">
	<h3>만족도 조사</h3>
	<div class="satisfaction-info">
		해당 페이지의 만족도 조사에 많은 참여 부탁 드립니다.
	</div>
	<form id="satisfaction_form" action="${APP_PATH}/satisfaction${API_PATH}/evaluate" method="post">
		<input type="hidden" name="menuId" value="${currentMenu.menuId}" />
		<ul class="stasfaction-score sidebar">
			<li><label><input type="radio" name="score" value="50"> 매우 만족</label></li>
			<li><label><input type="radio" name="score" value="40"> 만족</label></li>
			<li><label><input type="radio" name="score" value="30"> 보통</label></li>
			<li><label><input type="radio" name="score" value="20"> 불만족</label></li>
			<li><label><input type="radio" name="score" value="10"> 매우 불만족</label></li>
		</ul>
		<button class="btn btn-white" type="submit">평가하기</button>
	</form>
</div>
--%>