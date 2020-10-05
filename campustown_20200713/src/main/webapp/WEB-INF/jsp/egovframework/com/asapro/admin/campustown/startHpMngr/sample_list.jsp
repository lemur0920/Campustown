<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="asapro" uri="http://www.asadal.com" %>

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
					<!-- 검색폼 -->
					<div class="row">
					    <div class="col-sm-12">
					        <div class="card m-b-15">
					            <div class="card-body">
						            <form:form modelAttribute="startHpMngrSearch" cssClass="" action="${ADMIN_PATH}/startHpMngr/list.do" method="get">
						        	</form:form>
						        </div>
					        </div>
				        </div>
			        </div>	    
	        		<!-- End 검색폼 -->
	        		
	        		
	        		
	        		<!-- 목록 -->
					<div class="row">
						<div class="col-sm-12">
							<div class="card m-b-30">
								<div class="card-body">
									<!-- alert message -->
									<c:import url="/WEB-INF/jsp/egovframework/com/asapro/admin/include/message.jsp" charEncoding="UTF-8" />
	        						
	        						<div class="table-responsive">
	        							<table class="table table-striped table-hover ">
											<colgroup>
											</colgroup>
											
											<thead>
											</thead>
											
											<tbody>
											</tbody>
										</table>	
	        						</div>
	        						
	        						
	        						<div class="form-inline">
										<div class="mr-auto">
											<a href="${ADMIN_PATH}/startHpMngr/insert.do" class="btn btn-primary waves-effect waves-light">추가</a>
										</div>
										<div>
											<span>총 ${paging.rowTotal}건의 데이터가 있습니다.</span>
										</div>
									</div>
	        						
	        						<!-- paging -->
									<div>
										<c:out value="${paging.adminTextTag}" escapeXml="false"/>
									</div>
	        		
	        					</div>
        					</div>
       					</div>
     				</div>
	        		<!-- End 목록 -->
	        		
	        		
	        		
	        	
	        	</div> <!-- End container -->
	        
	        
	    	</div> <!-- End Page content Wrapper -->
	    	
	    </div> <!-- End content -->
	</div>    
	<!-- End Right content here -->

<c:import url="/WEB-INF/jsp/egovframework/com/asapro/admin/layout/footer.jsp" charEncoding="UTF-8" />