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
	//submit
	$('.search-btn').on('click', function(e){
		e.preventDefault();
		$('#policySearch').submit();
	});
	
	//reset
	$('.bg-zero').on('click', function(e){
		e.preventDefault();
		$('#policySearch')[0].reset();
		$('input[type=checkbox]').prop('checked',false);
	});
	
});
</script>
	
<!-- sidebar -->
<c:import url="${design.themeDir}/layout/sidebar.jsp" charEncoding="UTF-8" />

	<!-- content -->
	<form:form modelAttribute="policySearch" action="${APP_PATH}/archive/policy/${archiveCategory.catId }" method="get">
		<%-- <div class="blue-input">
			<label for="input-01">맞춤검색</label>
			<form:input path="sv" id="input-01" placeholder="검색어를 입력하세요"/>
			<a href="#n" class="search-btn">검색</a>
		</div> --%>
		<div class="gray-wrap">
			<div class="detailed-center">
				<ul>
					<li><p class="title">대상별</p>
						<ul class="clearfix">
							<c:if test="${not empty arcMetaCode1List }">
								<c:forEach items="${arcMetaCode1List }" var="mCode1" varStatus="vs"> 
									<c:set var="chkday" value="" />
									<c:forEach items="${selectMetaCode1Array }" var="meta1" >
										<c:if test="${meta1 eq mCode1.codeId }">
											<c:set var="chkday" value="checked" />
										</c:if>
									</c:forEach>
									<li>
										<input type="checkbox" name="metaCode1" id="metaCode1${vs.count }" value="${mCode1.codeId }" ${chkday } />
										<label for="metaCode1${vs.count }"> ${mCode1.codeName }</label>
									</li>
								</c:forEach>
							</c:if>
						</ul>
					</li>
					<li><p class="title">희망분야</p>
						<ul class="clearfix">
							<c:if test="${not empty arcMetaCode2List }">
								<c:forEach items="${arcMetaCode2List }" var="mCode2" varStatus="vs"> 
									<c:set var="chkday" value="" />
									<c:forEach items="${selectMetaCode2Array }" var="meta2" >
										<c:if test="${meta2 eq mCode2.codeId }">
											<c:set var="chkday" value="checked" />
										</c:if>
									</c:forEach>
									<li>
										<input type="checkbox" name="metaCode2" id="metaCode2${vs.count }" value="${mCode2.codeId }" ${chkday } />
										<label for="metaCode2${vs.count }"> ${mCode2.codeName }</label>
									</li>
								</c:forEach>
							</c:if>
						</ul>
					</li>
					<li><p class="title">지역구분</p>
						<ul class="clearfix">
							<c:if test="${not empty arcMetaCode3List }">
								<c:forEach items="${arcMetaCode3List }" var="mCode3" varStatus="vs"> 
									<c:set var="chkday" value="" />
									<c:forEach items="${selectMetaCode3Array }" var="meta3" >
										<c:if test="${meta3 eq mCode3.codeId }">
											<c:set var="chkday" value="checked" />
										</c:if>
									</c:forEach>
									<li>
										<input type="checkbox" name="metaCode3" id="metaCode3${vs.count }" value="${mCode3.codeId }" ${chkday } />
										<label for="metaCode3${vs.count }"> ${mCode3.codeName }</label>
									</li>
								</c:forEach>
							</c:if>
						</ul>
					</li>
				</ul>
			</div>
			<div class="detailed-search clearfix">
				<a class="open" href="#none">상세검색</a>
				<div class="right clearfix">
					<a class="bg-zero" href="#n">초기화</a>
					<a href="#" class="search-btn">적용</a>
				</div>
			</div>
		</div>
	</form:form>
	
	
	<div class="list-cond">
		<!-- <a href="#n" class="on">취업역량강화순</a> -->
		<a href="#n" class="on">최근등록순</a>
	</div>
	<ul class="policy-list">
		<c:forEach items="${paging.result}" var="item" varStatus="vs">
			<li>
				<h5><a href="${item.permLink }">${item.arcTitle }</a></h5>
				<p>${item.poliShortDescription } </p>
				<a href="${item.permLink }" class="more">더보기</a>
			</li>
		</c:forEach>
		<c:if test="${empty paging.result}">
			<!-- 게시물이 없을 때 -->
			<li class="nodata">게시물이 없습니다.</li>
		</c:if>
	</ul>
	
	<!-- paging -->
	<c:out value="${paging.userImageTag}" escapeXml="false"/>
	
	<!-- content end -->

<!-- footer -->
<c:import url="${design.footer}" charEncoding="UTF-8" />