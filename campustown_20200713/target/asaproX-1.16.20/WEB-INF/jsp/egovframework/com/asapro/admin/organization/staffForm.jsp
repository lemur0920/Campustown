<%@page import="org.apache.commons.lang3.StringUtils"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:import url="/WEB-INF/jsp/egovframework/com/asapro/admin/header.jsp" charEncoding="UTF-8" />
<div class="page-header">
	<h1><i class="icon-globe"></i> ${currentSite.siteName} &gt; 조직및업무 &gt; 직원<c:if test="${formMode == 'INSERT'}">추가</c:if><c:if test="${formMode == 'UPDATE'}">수정</c:if></h1>
</div>
<c:import url="/WEB-INF/jsp/egovframework/com/asapro/admin/message.jsp" charEncoding="UTF-8" />
<div class="row-fluid">
	<div class="span12">
	<c:if test="${formMode == 'INSERT'}"><c:set var="actionUrl" value="${ADMIN_PATH}/organization/staff/insert.do" /></c:if>
	<c:if test="${formMode == 'UPDATE'}"><c:set var="actionUrl" value="${ADMIN_PATH}/organization/staff/update.do" /></c:if>
	<form:form modelAttribute="staffForm" action="${actionUrl}" method="post" cssClass="form-horizontal">
		<form:hidden path="stIdx"/>
		<div class="control-group<form:errors path="stDepIdx"> error</form:errors>">
			<form:label path="stDepIdx" cssClass="control-label">부서 <span class="text-error">*</span></form:label>
			<div class="controls">
				<form:select path="stDepIdx">
					<option value="">선택</option>
					<form:options items="${departmentList}" itemLabel="depName" itemValue="depIdx"/>
				</form:select>
				<form:errors path="stDepIdx" element="div" cssClass="text-error" />
			</div>			
		</div>
		<form:hidden path="stRankCode"/>
		<%--
		<div class="control-group<form:errors path="staffRankCode"> error</form:errors>">
			<form:label path="staffRankCode" cssClass="control-label">직위 <span class="text-error">*</span></form:label>
			<div class="controls">
				<form:select path="staffRankCode">
					<option value="">선택</option>
					<form:options items="${staffRankCodeList}" itemLabel="codeName" itemValue="codeId"/>
				</form:select>
				<form:errors path="staffRankCode" element="div" cssClass="text-error" />
			</div>			
		</div>
		--%>
		<div class="control-group<form:errors path="stName"> error</form:errors>">
			<form:label path="stName" cssClass="control-label">직원이름 <span class="text-error">*</span></form:label>
			<div class="controls">
				<form:input path="stName" maxlength="50"/>
				<form:errors path="stName" element="div" cssClass="text-error" />
			</div>
		</div>
		<div class="control-group<form:errors path="stWork"> error</form:errors>">
			<form:label path="stWork" cssClass="control-label">업무</form:label>
			<div class="controls">
				<form:textarea path="stWork" cols="50" rows="3" cssClass="input-xxlarge" maxlength="500"/>
			</div> 
		</div>
		<div class="control-group<form:errors path="stTel"> error</form:errors>">
			<form:label path="stTel" cssClass="control-label">전화번호</form:label>
			<div class="controls">
				<form:textarea path="stTel" cols="50" rows="3" cssClass="input-xxlarge" maxlength="500"/>
			</div> 
		</div>
		<div class="control-group<form:errors path="stRemark"> error</form:errors>">
			<form:label path="stRemark" cssClass="control-label">비고</form:label>
			<div class="controls">
				<form:textarea path="stRemark" cols="50" rows="3" cssClass="input-xxlarge" maxlength="500"/>
			</div> 
		</div>
		<div class="form-actions">
			<button type="submit" class="btn btn-mini btn-info">확인</button>
			<a href="${ADMIN_PATH}/organization/staff/list.do" class="btn btn-mini">취소</a>
		</div>
	</form:form>	
	</div>
</div>
<c:import url="/WEB-INF/jsp/egovframework/com/asapro/admin/footer.jsp" charEncoding="UTF-8" />