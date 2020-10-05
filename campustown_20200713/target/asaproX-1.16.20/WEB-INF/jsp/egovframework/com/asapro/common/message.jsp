<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<style>

.alert {
    position: relative;
    padding: .75rem 1.25rem;
    margin-bottom: 1rem;
    border: 1px solid transparent;
    border-radius: .25rem;
}
.alert {
    position: relative;
    border: 0;
}
.alert-success {
    color: #155724;
    background-color: #d4edda;
    border-color: #c3e6cb;
}
.alert-success {
    color: #6fd088;
    background-color: #e2f6e7;
}
.alert-danger {
    color: #721c24;
    background-color: #f8d7da;
    border-color: #f5c6cb;
}
.alert-danger {
    color: #f32f53;
    background-color: #fef0f2;
}

</style>
<c:if test="${param.inserted || inserted}">
	<div class="alert alert-success" role="alert">
		추가되었습니다.
	</div>
</c:if>
<c:if test="${param.insertFail || insertFail}">
	<div class="alert alert-danger" role="alert">
		추가실패했습니다.
	</div>
</c:if>
<c:if test="${param.updated || updated}">
	<div class="alert alert-success" role="alert">
		수정되었습니다.
	</div>
</c:if>
<c:if test="${param.updateFail || updateFail}">
	<div class="alert alert-danger" role="alert">
		수정실패했습니다.
	</div>
</c:if>
<c:if test="${param.noChange}">
	<div class="alert alert-danger">
		수정사항이 없습니다.
	</div>
</c:if>
<c:if test="${param.restored}">
	<div class="alert alert-success">
		복구되었습니다.
	</div>
</c:if>

<c:if test="${param.impossibleStatus || impossibleStatus}">
	<div class="alert alert-danger">
		수정에 실패 했습니다.<br>
		수정이 불가능한 진행단계입니다.
	</div>
</c:if>
<c:if test="${param.notUse || notUse}">
	<div class="alert alert-danger">
		예약에 실패 했습니다.<br>
		게시되지 않은 예약서비스 입니다.
	</div>
</c:if>
<c:if test="${param.notOpen || notOpen}">
	<div class="alert alert-danger">
		예약에 실패 하였습니다.<br>
		대관/대여 기간이 아닙니다.
	</div>
</c:if>
<c:if test="${param.notApplyTerm || notApplyTerm}">
	<div class="alert alert-danger">
		예약에 실패 하였습니다.<br>
		예약신청 가능기간이 아닙니다.
	</div>
</c:if>
<c:if test="${param.notApplyDay || notApplyDay}">
	<div class="alert alert-danger">
		예약에 실패 하였습니다.<br>
		예약신청 가능요일이 아닙니다.
	</div>
</c:if>
<c:if test="${param.notApplyDate || notApplyDate}">
	<div class="alert alert-danger">
		예약에 실패 하였습니다.<br>
		예약신청 가능날짜가 아닙니다.
	</div>
</c:if>
<c:if test="${param.impossibleDate || impossibleDate}">
	<div class="alert alert-danger">
		예약에 실패 하였습니다.<br>
		예약신청 불가능 지정 날짜입니다.
	</div>
</c:if>
<c:if test="${param.overlap || overlap}">
	<div class="alert alert-danger">
		예약에 실패 하였습니다.<br>
		이미 다른 예약이 되어있습니다.
	</div>
</c:if>
<c:if test="${param.excess || excess}">
	<div class="alert alert-danger">
		예약에 실패 하였습니다.<br>
		예약인원이 정원을 초과하였습니다.
	</div>
</c:if>
<c:if test="${param.limitOver || limitOver}">
	<div class="alert alert-danger">
		예약에 실패 하였습니다.<br>
		최대신청가능 인원을 초과하였습니다.
	</div>
</c:if>
<c:if test="${param.notStatus || notStatus}">
	<div class="alert alert-danger">
		수정에 실패 하였습니다.<br>
		현제 신청 진행단계가 수정할 수 없는 단계입니다.
	</div>
</c:if>
<c:if test="${param.applyRedirect || applyRedirect}">
	<div class="alert alert-danger">
		예약설정이 저정되지 않았습니다.<br>
		회차관리를 위해서는 신청설정을 먼저 저장해 주세요.
	</div>
</c:if>
<c:if test="${param.apply || apply}">
	<div class="alert alert-success">
		예약이 완료되었습니다.
	</div>
</c:if>