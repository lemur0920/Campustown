<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="asapro" uri="http://www.asadal.com" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page import="java.util.Date"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%
int nowYear = Integer.parseInt(new SimpleDateFormat("yyyy").format(new Date()) );
%>
<c:set var="nowYear" value="<%= nowYear %>" />

<!-- header -->
<c:import url="${design.header}" charEncoding="UTF-8" />

<script>
jQuery(function($){
	
	
});
</script>
	
<!-- sidebar -->
<c:import url="${design.themeDir}/layout/sidebar.jsp" charEncoding="UTF-8" />

		<!-- content -->
		<div class="help-vote-box ">
			<div class="clearfix">
				<div class="help-vote">
					<h3 class="h3-tit"><span>주간 베스트 5</span></h3>
					<ul>
						<c:forEach items="${term7BestList }" var="item" varStatus="vs">
							<li class="<c:if test="${vs.count eq 1 }">on</c:if>">
								<div class="left">
									<span class="num">${vs.count }</span>
									<p class="title"><a href="${item.permLink }">${item.arcTitle }</a></p>
								</div>
								<div class="right">
									<a class="love" href="#">${item.csSumCount }</a>
									<span class="date"><fmt:formatDate value="${item.arcRegdate}" pattern="yyyy.MM.dd" /></span>
								</div>
							</li>
						</c:forEach>
					</ul>
				</div>
				<div class="help-vote">
					<h3 class="h3-tit"><span>월간 베스트  5</span></h3>
					<ul>
						<c:forEach items="${term30BestList }" var="item" varStatus="vs">
							<li class="<c:if test="${vs.count eq 1 }">on</c:if>">
								<div class="left">
									<span class="num">${vs.count }</span>
									<p class="title"><a href="${item.permLink }">${item.arcTitle }</a></p>
								</div>
								<div class="right">
									<a class="love" href="#">${item.csSumCount }</a>
									<span class="date"><fmt:formatDate value="${item.arcRegdate}" pattern="yyyy.MM.dd" /></span>
								</div>
							</li>
						</c:forEach>
					</ul>
				</div>
			</div>
		</div>
		
	
		<!-- paging -->
		<%-- <c:out value="${paging.userImageTag}" escapeXml="false"/> --%>
		
		<!-- content end -->

<!-- footer -->
<c:import url="${design.footer}" charEncoding="UTF-8" />