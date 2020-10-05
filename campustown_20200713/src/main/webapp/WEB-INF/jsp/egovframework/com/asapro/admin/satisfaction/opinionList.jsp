<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="asapro" uri="http://www.asadal.com" %>

<script>
jQuery(function($){
	//평가의견 - 페이징 버튼
	$('.page-link').on('click', function(e){
		e.preventDefault();
		$('#opinionlist').load($(this).attr('href'));	
	});
});
</script>

					            
<c:if test="${empty paging.result }">
	<div class="p-3 mb-3 bg-light text-info font-weight-bold">
		<div> 등록된 평가의견이 없습니다. </div>
	</div>
</c:if>
<ul class="list-group list-group-flush">
	<c:forEach items="${paging.result}" var="opinion" varStatus="vs">
		<li class="list-group-item">${opinion.satisOpinion } <span class="badge ml-4"><fmt:formatDate value="${opinion.satisOpiDate }" pattern="yyyy-MM-dd" /></span></li>
	</c:forEach>
</ul>
<!-- paging -->
<div class="mt-3 pagination-sm">
	<c:out value="${paging.adminTextTag}" escapeXml="false"/>
</div>					            	
