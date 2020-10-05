<%@page import="egovframework.com.asapro.content.service.Content"%>
<%@page import="name.fraser.neil.plaintext.diff_match_patch"%>
<%@page import="name.fraser.neil.plaintext.diff_match_patch.Diff"%>
<%@page import="java.util.LinkedList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="asapro" uri="http://www.asadal.com" %>

<c:import url="/WEB-INF/jsp/egovframework/com/asapro/admin/layout/header.jsp" charEncoding="UTF-8" />

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
					<div class="row">
					
						<!-- alert maeeage -->
						<c:import url="/WEB-INF/jsp/egovframework/com/asapro/admin/include/message.jsp" charEncoding="UTF-8" />
						
						<div class="col-sm-6">
					        <div class="card m-b-30">
					            <div class="card-body">
									<span class="badge badge-success">최신</span> <fmt:formatDate value="${currentContent.contentLastModified}" pattern="yyyy-MM-dd HH:mm:ss"/>
									<div class="form-group">
										<div class="col-sm mt-2 p-2" style="overflow: auto; background-color: #eff3f6;">
										<c:out value="${currentDiffPrettyHtml}"  escapeXml="false"/>
										</div>
									</div>
								</div>
					        </div>
					    </div>
					    
						<div class="col-sm-6">
					        <div class="card m-b-30">
					            <div class="card-body">
									<span class="badge badge-warning">버전 : ${revisionContent.contentVer}</span> <fmt:formatDate value="${revisionContent.contentLastModified}" pattern="yyyy-MM-dd HH:mm:ss"/>
									<div class="form-group">
										<div class="col-sm mt-2 p-2" style="overflow: auto; background-color: #eff3f6;">
										<c:out value="${revisionDiffPrettyHtml}"  escapeXml="false"/>
										</div>
									</div>
								</div>
					        </div>
					    </div>
					    
					</div>
					
					
					<div class="row">
						<div class="col-sm-12">
					        <div class="card m-b-15">
					            <div class="card-body">
					            	<form action="${ADMIN_PATH}/content/revision/restore.do" method="post">
										<input type="hidden" name="contentRoot" value="${currentContent.contentRoot}" />
										<input type="hidden" name="contentVer" value="${revisionContent.contentVer}" />
										
										<div class="form-group">
											<button class="btn btn-primary waves-effect waves-light" type="submit">버전 ${revisionContent.contentVer} (으)로 복구</button>
											<a class="btn btn-secondary waves-effect waves-light" href="${ADMIN_PATH}/content/update.do?contentId=${currentContent.contentId}">취소</a>
										</div>
										
										<c:if test="${useContenRevision}">
											<c:import url="/WEB-INF/jsp/egovframework/com/asapro/admin/content/revision.jsp" charEncoding="UTF-8" />
										</c:if>
									</form>
								</div>
							</div>
						</div>
					</div>
					<!-- ============================= //메뉴별 컨텐츠 ============================ -->
		
		
				</div><!-- container -->
				
				<div class="container-fluid">
					
				</div>
			
			
			</div> <!-- Page content Wrapper -->
	    </div> <!-- content -->
	</div>
	<!-- End Right content here -->

<c:import url="/WEB-INF/jsp/egovframework/com/asapro/admin/layout/footer.jsp" charEncoding="UTF-8" />