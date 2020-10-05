<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="asapro" uri="http://www.asadal.com" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!-- header -->
<c:import url="${design.header}" charEncoding="UTF-8" />

<script>
jQuery(function($){
	
	//레이어 팝업 공통콘텐츠 상세내용 노출
	$('.list_area_con .clearfix .btn').on('click', function(){
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

			<!-- content -->
			<div id="content">
				<p class="tct mb_30 f20 tct">서울시에서는 청년들의 삶의 질 향상을 위해 다양한 정책 및 서비스를 제공하고 있습니다. <br>서울시에서 제공하는 다양한 서비스를 누려보세요. </p>
				
				<!-- 디자인 탭메뉴 -->
				<c:import url="${design.themeDir}/layout/content_policy_tab.jsp" charEncoding="UTF-8" />
				
				<!-- 콘텐츠 내용 -->
				<c:out value="${content.content}" escapeXml="false" />
				
				<!-- 공통컨텐츠 팝업 -->
				<c:import url="${design.themeDir}/commonContent/popupView.jsp" charEncoding="UTF-8" />
				
			</div>
			<!-- content end -->

<!-- footer -->
<c:import url="${design.footer}" charEncoding="UTF-8" />