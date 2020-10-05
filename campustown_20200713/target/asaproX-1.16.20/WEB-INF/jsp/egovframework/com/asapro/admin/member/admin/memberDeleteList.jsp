<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="egovframework.com.asapro.member.admin.service.AdminMember"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<% AdminMember currentUser = (AdminMember) request.getSession().getAttribute("currentAdmin"); %>
<c:import url="/WEB-INF/jsp/egovframework/com/asapro/admin/header.jsp" charEncoding="UTF-8" />
<div class="page-header">
	<h1><i class="icon-globe"></i> ${currentSite.siteName} &gt; 탈퇴회원목록</h1>
</div>
<%--
<div class="well well-small">
	<ul class="unstyled text-info" style="margin-bottom: 0;">
		<li><i class="icon-info-sign"></i> 이메일계정이 확인되지 않은 회원은 로그인을 할 수 없습니다.</li>
	</ul>
</div>
--%>
<c:import url="/WEB-INF/jsp/egovframework/com/asapro/admin/message.jsp" charEncoding="UTF-8" />
<div class="well well-small" style="text-align: right">
	<form:form modelAttribute="memberDeleteSearch" cssClass="form-search" action="${ADMIN_PATH}/member/memberDelete/list.do" method="get" style="margin-bottom: 0;">
		<form:hidden path="sc" value="memberId" />
		<form:select path="pageSize" cssClass="input-small pull-left" title="페이지크기" cssStyle="margin-right: 3px;">
			<form:option value="10">10개씩</form:option>
			<form:option value="20">20개씩</form:option>
			<form:option value="40">40개씩</form:option>
		</form:select>
		<form:select path="sortOrder" cssClass="input-small pull-left" title="정렬기준" cssStyle="margin-right: 3px;">
			<form:option value="MEM_DELETE_DATE" label="탈퇴일시" />
			<form:option value="MEM_REGDATE" label="등록일시" />
			<form:option value="MEM_ID" label="회원아이디" />
		</form:select>
		<form:select path="sortDirection" cssClass="input-small pull-left" title="정렬방향" cssStyle="margin-right: 3px;">
			<form:option value="DESC" label="내림차순" />
			<form:option value="ASC" label="오름차순" />
		</form:select>
		<form:input path="sv" cssClass="search-query input-medium" title="검색어" />
		<button class="btn btn-mini" type="submit">검색</button>
	</form:form>
</div>
<div class="row-fluid">
	<div class="span12">
		<table class="table table-striped table-condensed table-hover table-bordered">
			<colgroup>
				<col style="width: 30px;" />
				<col />
				<col />
				<col />
			</colgroup>
			<thead>
				<tr>
					<th>No.</th>
					<th>회원아이디</th>
					<th>회원탈퇴일시</th>
					<th>회원가입일시</th>
				</tr>
			</thead>
			<tbody>
				<c:if test="${empty paging.result}">
					<tr>
						<td colspan="4" class="center">표시할 데이터가 없습니다.</td>
					</tr>
				</c:if>
				<c:forEach items="${paging.result}" var="item" varStatus="vs">
				<tr>
					<td>${paging.rowTop - vs.index}</td>
					<td>${item.memberId}</td>
					<td><fmt:formatDate value="${item.memberDeleteDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
					<td><fmt:formatDate value="${item.memberRegDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
</div>
<div class="row-fluid">
	<div class="span12">
		<span class="pull-right">총 ${paging.rowTotal}건의 데이터가 있습니다.</span>
	</div>
</div>
<div class="row-fluid">
	<div class="span12">
		<div class="pagination pagination-centered">
			<c:out value="${paging.adminTextTag}" escapeXml="false"/>
		</div>
	</div>
</div>
<c:import url="/WEB-INF/jsp/egovframework/com/asapro/admin/footer.jsp" charEncoding="UTF-8" />