<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:if test="${(not empty param.inserted && param.inserted) || (not empty inserted && inserted)}">
	<div class="alert alert-success" role="alert">
		추가되었습니다.
	</div>
</c:if>
<c:if test="${(not empty param.insertFail && param.insertFail) || (not empty insertFail && insertFail)}">
	<div class="alert alert-danger" role="alert">
		추가실패했습니다.
	</div>
</c:if>
<c:if test="${(not empty param.updated && param.updated) || (not empty updated && updated)}">
	<div class="alert alert-success" role="alert">
		수정되었습니다.
	</div>
</c:if>
<c:if test="${(not empty param.updateFail && param.updateFail) || (not empty updateFail && updateFail)}">
	<div class="alert alert-danger" role="alert">
		수정실패했습니다.
	</div>
</c:if>
<c:if test="${not empty param.noChange && param.noChange}">
	<div class="alert alert-danger">
		수정사항이 없습니다.
	</div>
</c:if>
<c:if test="${not empty param.restored && param.restored}">
	<div class="alert alert-success">
		복구되었습니다.
	</div>
</c:if>

<c:if test="${(not empty param.impossibleStatus && param.impossibleStatus) || (not empty impossibleStatus && impossibleStatus)}">
	<div class="alert alert-danger">
		수정에 실패 했습니다.<br>
		수정이 불가능한 진행단계입니다.
	</div>
</c:if>
<c:if test="${(not empty param.notUse && param.notUse) || (not empty notUse && notUse)}">
	<div class="alert alert-danger">
		예약에 실패 했습니다.<br>
		게시되지 않은 예약서비스 입니다.
	</div>
</c:if>
<c:if test="${(not empty param.notOpen && param.notOpen) || (not empty notOpen && notOpen)}">
	<div class="alert alert-danger">
		예약에 실패 하였습니다.<br>
		대관/대여 기간이 아닙니다.
	</div>
</c:if>
<c:if test="${(not empty param.notApplyTerm && param.notApplyTerm) || (not empty notApplyTerm && notApplyTerm)}">
	<div class="alert alert-danger">
		예약에 실패 하였습니다.<br>
		예약신청 가능기간이 아닙니다.
	</div>
</c:if>
<c:if test="${(not empty param.notApplyDay && param.notApplyDay) || (not empty notApplyDay && notApplyDay)}">
	<div class="alert alert-danger">
		예약에 실패 하였습니다.<br>
		예약신청 가능요일이 아닙니다.
	</div>
</c:if>
<c:if test="${(not empty param.notApplyDate && param.notApplyDate) || (not empty notApplyDate && notApplyDate)}">
	<div class="alert alert-danger">
		예약에 실패 하였습니다.<br>
		예약신청 가능날짜가 아닙니다.
	</div>
</c:if>
<c:if test="${(not empty param.impossibleDate && param.impossibleDate) || (not empty impossibleDate && impossibleDate)}">
	<div class="alert alert-danger">
		예약에 실패 하였습니다.<br>
		예약신청 불가능 지정 날짜입니다.
	</div>
</c:if>
<c:if test="${(not empty param.overlap && param.overlap) || (not empty overlap && overlap)}">
	<div class="alert alert-danger">
		예약에 실패 하였습니다.<br>
		이미 다른 예약이 되어있습니다.
	</div>
</c:if>
<c:if test="${(not empty param.excess && param.excess) || (not empty excess && excess)}">
	<div class="alert alert-danger">
		예약에 실패 하였습니다.<br>
		예약인원이 정원을 초과하였습니다.
	</div>
</c:if>
<c:if test="${(not empty param.limitOver && param.limitOver) || (not empty limitOver && limitOver)}">
	<div class="alert alert-danger">
		예약에 실패 하였습니다.<br>
		최대신청가능 인원을 초과하였습니다.
	</div>
</c:if>
<c:if test="${(not empty param.notStatus && param.notStatus) || (not empty notStatus && notStatus)}">
	<div class="alert alert-danger">
		수정에 실패 하였습니다.<br>
		현제 신청 진행단계가 수정할 수 없는 단계입니다.
	</div>
</c:if>
<c:if test="${(not empty param.applyRedirect && param.applyRedirect) || (not empty applyRedirect && applyRedirect)}">
	<div class="alert alert-danger">
		예약설정이 저정되지 않았습니다.<br>
		회차관리를 위해서는 신청설정을 먼저 저장해 주세요.
	</div>
</c:if>
<c:if test="${(not empty param.apply && param.apply) || (not empty apply && apply)}">
	<div class="alert alert-success">
		예약이 완료되었습니다.
	</div>
</c:if>