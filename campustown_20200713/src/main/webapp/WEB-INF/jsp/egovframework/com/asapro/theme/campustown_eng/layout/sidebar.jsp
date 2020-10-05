<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="egovframework.com.asapro.config.service.Config"%>
<%@ page import="egovframework.com.asapro.support.util.AsaproUtils"%>
<%@ page import="egovframework.com.asapro.menu.service.Menu"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="asapro" uri="http://www.asadal.com" %>

<!-- sidebar 시작 -->

	<!-- sub -->
    <div id="content"> <!--  No End Tag! (연결 페이지에 있음!) -->
		
	<!-- sub top visual -->
		<div class="subtitbox subtitbox${subTopMenu.menuOrder}" >
			<div>
				<h2><span>${subTopMenu.menuName }</span></h2>
				<p>${subTopMenu.menuTitleSubText}</p>
			</div>
		</div>	<!-- div subtitbox end -->
		
		<!-- sub navigation -->
		<div class="nav">
			<div class="nav-in">
				<c:import url="${design.themeDir}/layout/snb.jsp" charEncoding="UTF-8" />
				
				<!-- etc -->
				<jsp:include page="${design.themeDir}/include/sp_etc.jsp" />
				
			</div>
		</div>
	
	<!-- sub navigation mobile -->
	<%-- ${asapro:seoLocation(seo,'>','') } --%>
	<!-- sub navigation end -->

	<!-- sub title -->
		
		<div class="area" id="container"> <!--  No End Tag! (연결 페이지에 있음!) -->
			<div class="gap80"></div>
			
			<c:if test="${'text' eq currentMenu.menuTitleType}">
				<h3 class="title">
					${currentMenu.menuName} <span style="font-size:0.6em; color:#999;">${currentMenu.menuTitleSubText}</span>
				</h3>
			</c:if>
			<c:if test="${'image' eq currentMenu.menuTitleType}">
				<h3 class="title"><img src="${currentMenu.menuTitleImageUrl}" alt="${currentMenu.menuPlainName}" /></h3>
			</c:if>
	
		


			<!-- tab -->
		<%-- 	
			<c:import url="${design.themeDir}/layout/tab.jsp" charEncoding="UTF-8" />
		 --%>

