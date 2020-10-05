<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="asapro" uri="http://www.asadal.com" %>

<!-- header -->
<c:import url="${design.header}" charEncoding="UTF-8" />
<script src="${CONTEXT_PATH }/design/common/js/jquery-timepicker/jquery.timepicker.min.js"></script>
<link rel="stylesheet" href="${CONTEXT_PATH }/design/common/js/jquery-timepicker/jquery.timepicker.min.css">

<script>
jQuery(function($){
	
	
});
</script>

<!-- sidebar -->
<c:import url="${design.themeDir}/layout/sidebar.jsp" charEncoding="UTF-8" />

<%-- ============================= 메뉴별 컨텐츠 ============================ --%>

<div class="RightCWarp">
	<div class="s322porsi">
		<ul>
			<li class="s322bostfor">만 14세 미만의 아동인 경우 개인정보 수집·이용에 대한 법정대리인의 별도 동의가 필요하므로 박물관 담당자(02-2144-0136)에게 연락주셔서 별도 동의 절차를 거친 후 자원봉사 신청을 해주시기 바랍니다.</li>
			<li class="s322bostfor">자원봉사 시간은 3시간 또는 4시간만 운영합니다.</li>
			<li class="s322bostfor"><span>신청자 본인은 만 14세 이상의 신청자입니까?</span></li>
		</ul>
	</div>
	<div class="blgg1"><a href="${APP_PATH }/volunteer/agree" >예약신청</a></div>
</div>


<%-- ============================= 메뉴별 컨텐츠 끝 ============================ --%>

<!-- footer -->
<c:import url="${design.footer}" charEncoding="UTF-8" />