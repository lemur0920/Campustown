<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="asapro" uri="http://www.asadal.com" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%
	String dep1 = request.getParameter("dep1");
	if(dep1 == null) {
		dep1 = "0";
	};
	String dep2 = request.getParameter("dep2");
	if(dep2 == null){
		dep2 = "1";
	}
	System.out.println("dep1 : " + dep1);
	System.out.println("dep2 : " + dep2);
%>
<c:set var="dep1" value="<%= dep1 %>" />
<c:set var="dep2" value="<%= dep2 %>" />

<!-- header -->
<c:import url="${design.header}" charEncoding="UTF-8" />

<script>
jQuery(function($){
	
	//2뎁스
	if('${dep1}' == '0') {
		$('.list_tip').show();
		if($('.list_tip2').length > 0){
			$('.list_tip2, .sub_tab').hide();
		}
	}else {
		$('.list_tip,.list_tip2').hide();
		$('.tab${dep1}').show();
	}
	
	//3뎁스 
	$('.tit_tip, .list_tip3').hide();
	$('.tip${dep2}').show();
	
	//이전버튼
	$('.mb_15').on('click', function(e){
		e.preventDefault();
		location.href = $('.sub_tab').find('.active a').attr('href');
	});
  	//==================================================================
	
	//레이어 팝업 공통콘텐츠 상세내용 노출
	$('.list_tip3 button ').on('click', function(){
		var comContId = $(this).data('id');
		$.ajax({
			url : '${APP_PATH}/common/content${API_PATH}/view'
			, type : 'get'
			, dataType : 'json'
			, data : {
				comContId : comContId
			}
		}).done(function(result){
			if(result != null){
				// 팝업 포커스 이동   
				$(this).addClass('cfocus')
				$('#popup_con').show();
				$('#popup_con .popup').focus();
				
				//alert(result.content);
				$('#popup_con .con').html(result.comContContent);
				$('#popup_con .pop_tit').html(result.comContTitle);
			}
			
			
		}).fail(function(){
			alert('[해당 콘텐츠가 없습니다.]');
		});
		
	});
	
});
</script>


<!-- sidebar -->
<c:import url="${design.themeDir}/layout/sidebar.jsp" charEncoding="UTF-8" />

			<!-- tab -->
			<div class="sub_tab">
				<ul class="clearfix">
					<li class="<c:if test="${dep1 == 1 or dep1 == 0 }">active</c:if>"><a href="life_tips?dep1=1" title="<c:if test="${dep1 == 1 or dep1 == 0 }">현제페이지</c:if>">취업준비</a></li>
					<li class="<c:if test="${dep1 == 2 }">active</c:if>"><a href="life_tips?dep1=2" title="<c:if test="${dep1 == 2 }">현제페이지</c:if>">사회초년생</a></li>
					<li class="<c:if test="${dep1 == 3 }">active</c:if>"><a href="life_tips?dep1=3" title="<c:if test="${dep1 == 3 }">현제페이지</c:if>">신혼부부</a></li>
					<li class="<c:if test="${dep1 == 4 }">active</c:if>"><a href="life_tips?dep1=4" title="<c:if test="${dep1 == 4 }">현제페이지</c:if>">창업준비</a></li>
				</ul>
				<button type="button" class="btn tab_toggle open">	메뉴 열고 닫기</button>
			</div>

			<!-- content -->
			<div id="content">
			
				<!-- 콘텐츠 내용 -->
				<c:out value="${content.content}" escapeXml="false" />
				
				<!-- 공통컨텐츠 팝업 -->
				<c:import url="${design.themeDir}/commonContent/popupView.jsp" charEncoding="UTF-8" />
				
			</div>
			<!-- content end -->

<!-- footer -->
<c:import url="${design.footer}" charEncoding="UTF-8" />