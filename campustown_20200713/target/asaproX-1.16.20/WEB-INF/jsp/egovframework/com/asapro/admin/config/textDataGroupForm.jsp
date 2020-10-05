<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<c:import url="/WEB-INF/jsp/egovframework/com/asapro/admin/layout/header.jsp" charEncoding="UTF-8" />

<script>
jQuery(function($){
	
	
});
</script>

	<!-- Left Sidebar -->	
	<c:import url="/WEB-INF/jsp/egovframework/com/asapro/admin/layout/left.jsp" charEncoding="UTF-8" />

	<!-- Start right Content here -->
	<div class="content-page">
	    <!-- Start content -->
	    <div class="content">
			
			<!-- Top Bar -->
			<c:import url="/WEB-INF/jsp/egovframework/com/asapro/admin/layout/top.jsp" charEncoding="UTF-8" />
			
	        <div class="page-content-wrapper ">

				<div class="container-fluid">
			
					<!-- location -->
					<c:import url="/WEB-INF/jsp/egovframework/com/asapro/admin/include/location.jsp" charEncoding="UTF-8" />

					
					<!-- ============================= 메뉴별 컨텐츠 ============================ -->
<div class="row-fluid">
	<div class="span12">
		<c:if test="${empty textDataGroupForm.textDataList}">
			<div class="center">표시할 데이터가 없습니다.</div>
		</c:if>
		<c:if test="${not empty textDataGroupForm.textDataList}">
			<form:form modelAttribute="textDataGroupForm" action="${ADMIN_PATH}/config/textData/${textGroup}/update.do" class="form-horizontal" style="margin-bottom: 0">
				<c:forEach items="${textDataGroupForm.textDataList}" var="textData" varStatus="vs">
					<form:hidden path="textDataList[${vs.index }].textGroup" />
					<form:hidden path="textDataList[${vs.index }].textId" />
					<div class="control-group<form:errors path="textDataList[${vs.index}].textContent"> error</form:errors>">
						<label class="control-label" for="textData${vs.index}">
							${textData.textName}
						</label>
						<div class="controls">
							<form:textarea rows="5" cols="50" path="textDataList[${vs.index}].textContent" cssClass="input-xxlarge" style="width: 700px; height: 250px;"/>
							<form:errors path="textDataList[${vs.index}].textContent" element="div" cssClass="text-error" />
						</div>
					</div>
				</c:forEach>
				<div class="form-actions">
					<button type="submit" class="btn btn-mini btn-info">확인</button>
				</div>
			</form:form>
		</c:if>
	</div>
</div>
<!-- ============================= //메뉴별 컨텐츠 ============================ -->
		
		
				</div><!-- container -->
			
			
			</div> <!-- Page content Wrapper -->
	
	    </div> <!-- content -->
	
	</div>
	<!-- End Right content here -->

<c:import url="/WEB-INF/jsp/egovframework/com/asapro/admin/layout/footer.jsp" charEncoding="UTF-8" />