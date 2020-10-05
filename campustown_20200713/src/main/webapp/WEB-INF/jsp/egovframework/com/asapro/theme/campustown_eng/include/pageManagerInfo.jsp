<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script>
jQuery(function($){
	
});

</script>
<c:if test="${currentMenu.menuUseManagerInfo }">
	<div class="top">
		<span class="t1">제공부서 : ${currentMenu.menuDepartment}</span>
		<span class="t2">문의 : ${currentMenu.menuPhone}</span>
		<span class="t3">작성일 : 
			<c:if test="${currentMenu.menuType eq 'content' }">
				<fmt:formatDate value="${content.contentLastModified}" pattern="yyyy-MM-dd" />
			</c:if>
			<c:if test="${currentMenu.menuType ne 'content' }">
				<fmt:formatDate value="${currentMenu.menuLastModified}" pattern="yyyy-MM-dd" />
			</c:if>
		</span>
	</div>
</c:if>
