<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="asapro" uri="http://www.asadal.com" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!-- header -->
<c:import url="${design.header}" charEncoding="UTF-8" />

<script>
jQuery(function($){
 	 
 	
});
</script>

<!-- sidebar -->
<c:import url="${design.themeDir}/layout/sidebar3.jsp" charEncoding="UTF-8" />
 	
 	<div id="subl-center">
		<c:import url="${design.themeDir}/layout/tab3depth.jsp" charEncoding="UTF-8" />
		<c:if test="${empty univInfo.sportProgrm}">
			현재 지원프로그램이 없습니다.
		</c:if>
		<c:if test="${not empty univInfo.sportProgrm}">
			${univInfo.sportProgrm }
			<h3 class="subl-title1">상세 지원내용 다운로드</h3>
			<c:if test="${not empty univInfo.file3Info}">	
				<a class="add-file" href="#n">
					<span><img href="${APP_PATH}/file/download/uu/${univInfo.file3Info.fileUUID}" alt="첨부파일 다운로드" src="/design/theme/campustown/images/sub/add_file_ico.gif" /> </span>
					${univInfo.file3Info.fileOriginalName }
				</a>
			</c:if>
		</c:if>
		
	</div>
</div>   <!-- END #content -->

<!-- footer -->
<c:import url="${design.footer}" charEncoding="UTF-8" />